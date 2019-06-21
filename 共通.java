
////////////////////////////////////////////////////////////////////////////////
//
//
// create 2002-02-12
//
//  2002/08/15    竹村正義        更新するSQL文に'が入っている時エラーが出ないようにチェック
//  2002/11/06    竹村正義        かなり頻繁に使うのでようやく共通化へ
//  2004/10/26    指村敦子        SQL like検索時のエスケープ文字列変換メソッド追加
//  2014/10/15    上中美奈子     プロパティファイル取得・出力処理追加
//							 言語変更ボタンが押された際に読み込むプロパティファイル変更
////////////////////////////////////////////////////////////////////////////////

package contents;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//DOM classes
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLElement;
import oracle.xml.parser.v2.XSLProcessor;
import oracle.xml.parser.v2.XSLStylesheet;
//XUS classes
import oracle.xml.sql.query.OracleXMLQuery;

//classes

/**
 * 画面基底抽象クラス.
 *
 *
 *
 */
public abstract class ContentsBase extends AuthenticateController {
	// 改行・タブ文字
	public static final String CRLF = "\r\n";
	public static final String LF = "\n";
	public static final String TAB = "    ";
	// 全角、半角空白
	public static final char HALF_SPC = ' ';
	public static final char FULL_SPC = '　';
	// その他
	public static final char COMMA = ',';

	public static final String PROPERTY_BASENAME = "ForecastSearch";
	public static final String JAPANESE = "JAPANESE";
	public static final String ENGLISH = "ENGLISH";

	// パースフラグ
	public static String PARAM_NAME_PARSEFLAG = "parse_flag";
	public static String PARAM_VALUE_ON = "on";
	public static String PARAM_VALUE_OFF = "off";

	public static String SQL_ESC_STR = " ESCAPE '\\' ";
	public static String ERROR_UPD_MSG = "更新に失敗しました。必要な場合は管理者に連絡してください。";
	public static String ERROR_RGST_MSG = "登録に失敗しました。必要な場合は管理者に連絡してください。";
	public static String ERROR_DEL_MSG = "削除に失敗しました。必要な場合は管理者に連絡してください。";
	public static String ERROR_SELECT_MSG = "データの取得に失敗しました。必要な場合は管理者に連絡してください。";
	public static String ERROR_MAIL_MSG = "メールの送信に失敗しました。必要な場合は管理者に連絡してください。";
	public static String ERROR_RESOURCE_MSG = "プロパティファイルが見つかりません。管理者に連絡してください。";

	private int resultCount = 0; //データ件数

	/**
	 * クエリー取得メソッド.
	 *
	 *
	 */
	protected abstract Hashtable getQuery(HttpServletRequest request);

	/**
	 * スタイルシートURL取得メソッド.
	 *
	 *
	 */
	protected abstract URL getXSLURL(HttpServletRequest request) throws Exception;

	/**
	 * DOM取得後処理メソッド.
	 *
	 * @param query_name
	 *            クエリー名
	 * @param rowset
	 *            クエリーで取得されたエレメント
	 * @param doc
	 *            ドキュメント
	 * @param request
	 *            サーブレットリクエスト
	 *
	 */
	protected abstract void actionAfterGetDOM(String query_name, Element rowset, Document doc,
			HttpServletRequest request) throws Exception;

	/**
	 * 処理実行メソッド.
	 *
	 * @param request
	 *            サーブレットリクエスト
	 * @param response
	 *            サーブレットレスポンス
	 * @exception Exception
	 *                予期せぬ事態が発生した場合にスローされます。
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection con) throws Exception {
		if (DEBUG) {
			log("ContentsBase: execute");
		}

		// XMLDocumentの生成
		XMLDocument x_doc = new XMLDocument();

		// for IE5.5?
		x_doc.setEncoding(ENCODE);

		// ルートノード
		Element doc_root = appendElement(x_doc, x_doc, "doc-root");

		// ヘッダー部(リソースファイルから取得した共通URI等)
		Element header = appendElement(x_doc, doc_root, "header");
		appendTextElement(x_doc, header, "uri", URI);
		appendTextElement(x_doc, header, "code_name", CODE_NAME);
		appendTextElement(x_doc, header, "css_uri", CSS_URI);
		appendTextElement(x_doc, header, "javascript_uri", JAVASCRIPT_URI);
		appendTextElement(x_doc, header, "encode", ENCODE);
		appendTextElement(x_doc, header, "top_page_url", TOP_PAGE_URL);
		appendTextElement(x_doc, header, "rms_url", RMS_URL);

		// 画面固有のヘッダー情報設定処理
		actionAfterGetDOM("header", header, x_doc, request);

		// SQLクエリーの取得
		Hashtable query = getQuery(request);
		if (query == null) {
			query = new Hashtable();
		}

		// DBからデータ取得＆XML化
		setXMLQuery(con, query, x_doc, doc_root, request);
		// ブラウザのロケール取得（初期表示はブラウザのロケール）
		Locale locale = request.getLocale();

		HttpSession session = request.getSession(true);
		// 切り替え後の言語情報取得
		String afterChangeLanguage = getParameter(request, "after_change_language", "");
		Element elmProperty = appendElement(x_doc, doc_root, "property");

		if (afterChangeLanguage.equals(ENGLISH)) {
			// 変更後言語が英語なら、英語に切り替え
			locale = Locale.ENGLISH;
		} else if (afterChangeLanguage.equals(JAPANESE)) {
			// 変更後言語が日本語なら、日本語に切り替え
			locale = Locale.JAPANESE;
		} else if (afterChangeLanguage.equals("false")) {
			// 言語切り替えボタンが押されず再読み込みされたとき = sessionから現在のロケール取得
			if (session.getAttribute("propertyLanguage") != null) {
				locale = (Locale) session.getAttribute("propertyLanguage");
			}
		}
		// sessionに現在のロケール登録
		session.setAttribute("propertyLanguage", locale);

		// プロパティファイルを読み込みXML書き出すメソッド呼び出し
		outputPropertyToXML(elmProperty, x_doc, locale);
		// 現在の言語情報をXML化
		appendTextElement(x_doc, elmProperty, "now_language", locale.toString());

		// ソートのプルダウンの値書き出しメソッド呼び出し
		outputSortsetToXML(doc_root, x_doc, locale);

		// デバッグモード時、XMLドキュメントをログ出力
		if (DEBUG) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			x_doc.print(baos);
			log("  XMLDocument\r\n" + baos.toString(ENCODE));
		}

		// パースしない
		String pos = request.getParameter(PARAM_NAME_PARSEFLAG);
		if (PARAM_VALUE_OFF.equals(pos)) {
			// キャラセットの指定
			response.setContentType("text/xml; charset=\"" + ENCODE + "\"");
			// パースなし
			x_doc.print(response.getWriter());

		} else
		// バイナリダウンロード
		if (request.getParameter("binary") != null && request.getParameter("binary").equals("yes")) {
			// バイナリ指定
			response.setContentType("application/octet-stream; charset=\"" + ENCODE + "\"; name=\""
					+ request.getParameter("filename") + "\"");
			response.setHeader("Content-disposition",
					"attachment; filename=\"" + request.getParameter("filename") + "\"");

			try {
				ServletContext scontext = getServletContext();
				// 画像の情報を設定
				String sJgp = scontext.getRealPath("/uploadFile/" + request.getParameter("filename"));
				InputStream in = new FileInputStream(sJgp);
				ServletOutputStream sout = response.getOutputStream();
				// ストリームを素直に書き出し
				byte buff[] = new byte[1024 * 1024 * 10];
				int readByte;
				while (true) {
					readByte = in.read(buff);
					if (readByte <= 0)
						break;
					sout.write(buff, 0, readByte);
				}
				in.close();
			} catch (Exception e) {
				log(e.getMessage());
			}
		} else
		// パースする
		{
			if (request.getParameter("excel") != null && request.getParameter("excel").equals("yes")) {
				// エクセル指定
				response.setContentType("application/vnd.ms-excel; charset=\"" + ENCODE + "\"");
				Date today = new Date();
				SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
				String fileName = "MoneyPlan_download_" + date.format(today.getTime()) + ".xls";
				response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
				if(resultCount >= 1000){
					Statement stmt = con.createStatement();
					String emp_id = getCookie("USERID");
					String sql = "insert into err_table(ERR_DATE,ERR_CODE,ERR_MSG,ERR_DETAIL) "
							+ "values (SYSDATE,null,'Alert:社員番号: "
							+ emp_id
							+ " のアカウントから"
							+ resultCount
							+"件のデータが出力されました。','MoneyPlan')";
					log("sql;"+sql);
					stmt.executeUpdate( sql );
				}
			} else {
				// キャラセットの指定
				response.setContentType("text/html; charset=\"" + ENCODE + "\"");
			}

			// サーバサイド・パース
			URL xsl_url = getXSLURL(request);
			printParsedXML(response, x_doc, xsl_url);
		}
	}

	/**
	 * エレメントノード生成追加メソッド
	 */
	protected XMLElement appendElement(Document doc, Node parent, String element_name) throws Exception {
		XMLElement elm = (XMLElement) doc.createElement(element_name);
		parent.appendChild(elm);
		return elm;
	}

	/**
	 * テキストノード生成追加メソッド
	 */
	protected Text appendText(Document doc, Element parent, String node_text) throws Exception {
		Text text = doc.createTextNode(node_text);
		parent.appendChild(text);
		return text;
	}

	/**
	 * テキストノード付エレメントノード生成追加メソッド
	 */
	protected Element appendTextElement(Document doc, Element parent, String element_name, String node_text)
			throws Exception {
		Element elm = appendElement(doc, parent, element_name);
		appendText(doc, elm, node_text);
		return elm;
	}

	/**
	 * DBからデータ取得＆XML化
	 */
	protected void setXMLQuery(Connection con, Hashtable querys, Document x_doc, Element doc_root,
			HttpServletRequest request) throws Exception {
		// データエレメントの生成と追加
		OracleXMLQuery xsu = null;
		Element rowset = null;
		Enumeration enm = querys.keys();
		String key = null;

		// クエリー毎に繰り返し
		while (enm.hasMoreElements()) {
			// キー名を取得
			key = (String) enm.nextElement();
			// キー名のエレメント作成
			rowset = x_doc.createElement(key + "SET");
			// ＸＭＬドキュメントにエレメントを追加
			doc_root.appendChild(rowset);
			// クエリーの取得
			String query = (String) querys.get(key);

			// DEBUGモード時、クエリーをログに吐く
			if (DEBUG) {
				log("SQL\r\n" + query);
			}
			// クエリーを設定
			xsu = new OracleXMLQuery(con, query);
			xsu.setRowsetTag(null);
			xsu.setRowTag(key);
			// 空要素を返す
			xsu.useNullAttributeIndicator(true);
			// ＤＯＭツリーをエレメントに取得
			xsu.getXMLDOM(rowset);
			// エレメントに対して処理
			actionAfterGetDOM(key, rowset, x_doc, request);
			xsu.close();
			if (key.equals("PROJECT")) {
				resultCount = doc_root.getChildNodes().item(4).getChildNodes().getLength();
			}
		}

		return;
	}

	/**
	 * サーバサイドパース
	 *
	 *
	 *
	 *
	 */
	private void printParsedXML(HttpServletResponse response, XMLDocument x_doc, URL xsl_url) throws Exception {

		// XSLプロセッサの生成
		XSLProcessor processor = new XSLProcessor();
		XSLStylesheet xsl = processor.newXSLStylesheet(xsl_url);

		// 変換と出力
		processor.processXSL(xsl, x_doc, response.getWriter());
		return;
	}

	/**
	 * リクエストパラメータ取得メソッド. リクエストオブジェクトから指定されたパラメータの値を返します。<br>
	 * 値の両端の空白は除去されます。null文字の場合はnullとして返されます。
	 *
	 * @param request
	 *            リクエストオブジェクト
	 * @param name
	 *            取得するパラメータ名
	 * @return 取得された値
	 */
	protected String getParameter(HttpServletRequest request, String name) {
		// 取得した値
		String value;

		// 値の取得
		value = request.getParameter(name);

		// null文字をnullに変換
		if (value == null || value.equals("")) {
			value = null;
		} else {
			value = value.trim();
		}

		// 値を返す
		return value;
	}

	/**
	 * リクエストパラメータ取得メソッド. リクエストオブジェクトから指定されたパラメータの値を返します。<br>
	 * 値がnull及びnull文字の場合はreplaceに指定した文字列を返します。
	 *
	 * @param request
	 *            リクエストオブジェクト
	 * @param name
	 *            取得するパラメータ名
	 * @param replace
	 *            置換する文字列
	 * @return 取得された値
	 */
	protected String getParameter(HttpServletRequest request, String name, String replace) {
		// 値の取得
		return NVL(getParameter(request, name), replace);
	}

	/**
	 * リクエストパラメータよりフラグを取得します。 null及びnull文字の場合ならばOFF、それ以外の場合はON を返します。
	 *
	 * @param request
	 *            リクエストオブジェクト
	 * @param name
	 *            取得するフラグ名
	 * @return フラグ値 ON:1 OFF:0
	 */
	protected String getFlag(HttpServletRequest request, String name) {
		// 取得した値がnullの場合
		if (getParameter(request, name) == null) {
			// フラグOFF
			return "0";
		}
		// null以外の場合
		else {
			// フラグON
			return "1";
		}
	}

	/**
	 * NULLの変換 n がnullの場合は、e を返す。n がnullでない場合は n を返す。
	 *
	 * @param n
	 * @param e
	 * @return n か e
	 */
	protected String NVL(String n, String e) {
		// n が null の場合
		if (n == null) {
			// e を返す
			return e;
		}
		// n が null でない場合
		else {
			// n を返す
			return n;
		}
	}

	/**
	 * NULLの変換 n がnullか空文字の場合は、e を返す。n がnullか空文字でない場合は n を返す。
	 *
	 * @param n
	 * @param e
	 * @return n か e
	 */
	protected String NVL2(String n, String e) {
		// n が null の場合
		if (n == null || n.equals("")) {
			// e を返す
			return e;
		}
		// n が null でない場合
		else {
			// n を返す
			return n;
		}
	}

	/**
	 * リクエストパラメータ取得メソッド. リクエストオブジェクトから指定されたパラメータの値を返します。<br>
	 * 値の両端の空白は除去されます。 nullの場合はvalue1,nullでない場合はvalue2を返す
	 *
	 * @param request
	 *            リクエストオブジェクト
	 * @param name
	 *            取得するパラメータ名
	 * @return 取得された値
	 */
	protected String getParameter(HttpServletRequest request, String name, String value1, String value2) {
		// 取得した値
		String value = getParameter(request, name);

		// null文字をvalue1に変換
		if (value == null || value.equals("")) {
			value = value1;
		} else {
			value = value2;
		}

		// 値を返す
		return value;
	}

	/**
	 * DB追加時チェックメソッド. '(シングルクォーテーション)を2つにする
	 *
	 *
	 * @return 変換された値
	 */
	protected String setFormatQuot(String in) {

		StringBuffer out = new StringBuffer();
		char ch;

		if (in != null) {
			for (int i = 0; i < in.length(); i++) {
				ch = in.charAt(i);

				if (ch == '\'') {
					out.append("''");
				} else {
					out.append(ch);
				}
			}
		}

		return out.toString();
	}

	/**
	 * DB like検索時エスケープ文字列取得メソッド. '(シングルクォーテーション)を2つにする
	 * 更に、_(アンダースコア)、%、\、は\でエスケープする
	 *
	 * @param フォーマット対象文字列
	 * @return エスケープされた値
	 */
	protected String setFormatLikeSqlString(String in) {
		/** SQLエスケープ文字(LIKE用) */
		String[][] escChrs = new String[][] { { "\\", "\\\\" }, { "%", "\\%" }, { "_", "\\_" }, };

		// inがnullならreturn
		if (in == null) {
			return null;
		}

		// シングルクォーテーションをエスケープする。
		String val = setFormatQuot(in);

		// 入力文字列をバッファへ変換。
		StringBuffer valueBuff = new StringBuffer(val);

		// バッファ内を１文字ずつチェック
		for (int idxStr = 0; idxStr < valueBuff.length(); idxStr++) {
			// 1文字づつ取得
			String chr = valueBuff.substring(idxStr, idxStr + 1);

			// エスケープ文字列分ループ
			for (int idxEsc = 0; idxEsc < escChrs.length; idxEsc++) {
				// 合致すれば置換
				if (chr.equals(escChrs[idxEsc][0])) {
					valueBuff.replace(idxStr, idxStr + 1, escChrs[idxEsc][1]);
					idxStr += (escChrs[idxEsc][1].length() - 1);
				}
			}
		}
		return valueBuff.toString();
	}

	/**
	 * Condition
	 *
	 * 組織の日付条件(指定FYにかかっているかどうか)
	 */
	public static String getOrgPeriod(int fy, String ORG) {
		if (!ORG.equals("")) {
			ORG = ORG + ".";
		} else {
			ORG = "";
		}

		String ret = "\n and " + ORG + "org_start_date <= to_date('" + fy + "/05/31','yyyy/mm/dd')" + "\n and to_date('"
				+ (fy - 1) + "/06/01','yyyy/mm/dd') <= nvl(" + ORG + "org_end_date,to_date('9999/12/31','yyyy/mm/dd'))";

		return ret;
	}

	/**
	 * Condition
	 *
	 * 組織の日付条件(指定FYにかかっているかどうか) 上のgetOrgPeriodでは指定FYに存在しない組織が表示されなくなってしまう
	 */
	public static String getOrgPeriod2(int fy, String ORG) {
		if (!ORG.equals("")) {
			ORG = ORG + ".";
		} else {
			ORG = "";
		}

		String ret = "\n and " + ORG + "org_start_date <= to_date('" + fy + "/05/31','yyyy/mm/dd')" + "\n and to_date('"
				+ (fy - 1) + "/06/01','yyyy/mm/dd') <= nvl(" + ORG
				+ "org_end_date, to_date('9999/12/31','yyyy/mm/dd')) ";

		return ret;
	}

	/**
	 * Condition
	 *
	 * PRODUCT組織の日付条件(指定FYにかかっているかどうか)
	 */
	public static String getPrdOrgPeriod(int fy, String ORG) {
		if (!ORG.equals("")) {
			ORG = ORG + ".";
		} else {
			ORG = "";
		}

		String ret = "\n AND (" + "\n TO_DATE('" + (fy - 1) + "/06/01','YYYY/MM/DD') <= NVL(" + ORG
				+ "ORG_END_DATE,TO_DATE('9999/12/31','YYYY/MM/DD')) " + "\n AND " + ORG + "ORG_START_DATE < TO_DATE('"
				+ fy + "/06/01','YYYY/MM/DD') " + "\n )";

		return ret;
	}

	/**
	 * PROJ_PRODUCT_ORG_LISTの情報のうち、指定FYに存在する最新の組織名を取得するSQLを返す
	 *
	 * @param fy
	 * @param projCode
	 *            指定したくない場合はNULLとする
	 * @return SQL文字列
	 */
	public static String getProdOrgFy(int fy, String projCode) {

		StringBuffer sqlb = new StringBuffer("\n\t ( SELECT INPPDORG.PROJ_CODE PROJ_CODE, MAX(START_DATE) START_DATE "
				+ "\n\t FROM  PROJ_PRODUCT_ORG_LIST INPPDORG " + "\n\t WHERE TO_DATE('" + (fy - 1)
				+ "/06/01','YYYY/MM/DD') <= NVL(INPPDORG.END_DATE, TO_DATE('2999/12/31','YYYY/MM/DD') ) "
				+ "\n\t AND   INPPDORG.START_DATE < TO_DATE('" + fy + "/06/01','YYYY/MM/DD')  ");

		if (projCode != null && !"".equals(projCode)) {
			sqlb.append("\n\t AND   INPPDORG.PROJ_CODE = '" + projCode + "' ");
		}

		sqlb.append("\n\t GROUP BY INPPDORG.PROJ_CODE )");
		;
		return sqlb.toString();
	}

	/**
	 * 項目名読み込み、XMLに書き出しメソッド 渡されたlocaleからプロパティファイルを読み込み、XMLで書き出す
	 *
	 * @param elmProperty
	 *            ノードの名称
	 * @param x_doc
	 *            書き出すxmlドキュメント
	 * @param request
	 * @param locale
	 *            読み込む言語
	 * @param PROPERTY_BASENAME
	 *            読み込むプロパティファイル名
	 */
	private void outputPropertyToXML(Element elmProperty, Document x_doc, Locale locale) throws Exception {

		ResourceBundle bundle = null;
		try {
			// プロパティファイル読み込み
			bundle = ResourceBundle.getBundle(PROPERTY_BASENAME, locale);

			// プロパティファイルから一行ずつXMLファイルに追加
			for (Enumeration e = bundle.getKeys(); e.hasMoreElements();) {
				String keyName = (String) e.nextElement();
				appendTextElement(x_doc, elmProperty, keyName, bundle.getString(keyName));
			}

		} catch (MissingResourceException e) {
			log("PropertyFileGet execute:プロパティファイルが見つかりませんでした。");
			throw new ContentsException(ERROR_RESOURCE_MSG, e);
		}
	}

	/**
	 * ソートのプルダウンの値をXMLに書き出すメソッド 渡されたlocaleからプロパティファイルを読み込み、XMLで書き出す
	 *
	 * @param doc_root
	 *            ノードの名称
	 * @param x_doc
	 *            書き出すxmlドキュメント
	 * @param request
	 * @param locale
	 *            読み込む言語
	 * @param PROPERTY_BASENAME
	 *            読み込むプロパティファイル名
	 */
	private void outputSortsetToXML(Element doc_root, Document x_doc, Locale locale) throws Exception {

		ResourceBundle bundle = null;
		try {
			// プロパティファイル読み込み
			bundle = ResourceBundle.getBundle(PROPERTY_BASENAME, locale);

			// ソートプルダウンの値をプロパティファイルから読み込み配列に格納
			String[] sort_item = { bundle.getString("MP_ORG_CHECK"), bundle.getString("PJ_ORG_CHECK"),
					bundle.getString("BOOKING_STATUS"), bundle.getString("REVENUE_STATUS"),
					bundle.getString("WORK_STATUS_CHECK"), bundle.getString("PROBABILITY_CHECK"),
					bundle.getString("SUB_INDUSTRY_CHECK"), bundle.getString("TEMP_NUMBER_CHECK"),
					bundle.getString("PROJECT_NUMBER_CHECK"), bundle.getString("PROJECTNAME_CHECK"),
					bundle.getString("END_USER_SORT"), bundle.getString("CUSTOMER_SORT"), bundle.getString("PO_SORT"),
					bundle.getString("PM_SORT"), bundle.getString("BOOKING_AMOUNT_CHECK"),
					bundle.getString("CONTRACT_TYPE_CHECK"), bundle.getString("CLOSE_DATE_SORT"),
					bundle.getString("PROJECT_START_DATE_SORT"), bundle.getString("PROJECT_END_DATE_SORT") };

			Element sortset = appendElement(x_doc, doc_root, "SORTSET");
			// 配列から一個ずつXMLファイルに追加
			for (int cnt = 0; cnt < sort_item.length; cnt++) {
				Element sort = appendElement(x_doc, sortset, "SORT");
				appendTextElement(x_doc, sort, "ITEM", sort_item[cnt]);
			}

		} catch (MissingResourceException e) {
			log("PropertyFileGet execute:プロパティファイルが見つかりませんでした。");
			throw new ContentsException(ERROR_RESOURCE_MSG, e);
		}
	}

}
