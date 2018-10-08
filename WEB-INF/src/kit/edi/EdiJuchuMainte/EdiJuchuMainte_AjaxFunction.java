package kit.edi.EdiJuchuMainte;

import static kit.edi.EdiJuchuMainte.IEdiJuchuMainte.*;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.kurac.KuraChokuDataIn.KuracKuraChokuDataIn_AjaxFunction;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.Records.FbMastrSyohinRecord;
import fb.inf.KitFunction;

/**
 * Ajax処理の実装クラスです
 *
 */
public class EdiJuchuMainte_AjaxFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = 3143161960498691428L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = KuracKuraChokuDataIn_AjaxFunction.class.getName();

	// -----------------------------------------
	// define variable
	// -----------------------------------------
	private static final boolean isTransactionalFunction = false;

	/**
	 * Transaction制御フラグを返却する。
	 *
	 * @return Transaction制御フラグ
	 */
	@Override
	protected boolean isTransactionalFunction() {

		return isTransactionalFunction;
	}

	/**
	 * クラス名を返却する。
	 *
	 * @return クラス名
	 */
	@Override
	protected String getFunctionName() {

		return className__;
	}

	public EdiJuchuMainte_AjaxFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		super(mapping, form, request, response);
	}

	/**
	 * Ajax処理を実行する。
	 *
	 * @return Forward情報
	 */
	@Override
	public String execute_() {
		try {
			// リクエストパラメータを取得
			String objCd = (String) request_.getParameter("objCd"); 	// key値
			String objNm = (String) request_.getParameter("objNm"); 	// key名

			// 検索サービス
			EdiJuchuMainte_SearchService searchServ = new EdiJuchuMainte_SearchService(
					getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

			StringBuilder xml = new StringBuilder();

			xml.append("<" + XML_TAG + ">\n");

			// xmlタグ作成
			// 商品CDの場合
			if ("shohinCd".equals(objNm)) {
				xml.append(createXmlTagForShohinCd(searchServ, objCd));
			}
			// ケースバラの場合
			if ("tatesenCd".equals(objNm)) {
				xml.append(createXmlTagForTatesenCd(searchServ, objCd));
			}

			xml.append("</" + XML_TAG + ">\n");

			write(xml.toString());

		}catch (Exception e) {
		}

		return FORWARD_SUCCESS;
	}

	/**
	 * 商品情報XML出力用文字列生成
	 * @param checkService
	 * @param kaisyaCd
	 * @param objCd
	 * @return
	 */
	public String createXmlTagForShohinCd(EdiJuchuMainte_SearchService searchServ, String shohinCd) {

		StringBuilder tag = new StringBuilder();

		// 商品CDから商品情報を取得
		FbMastrSyohinRecord zRecSeihin = searchServ.getMastrShohinInfoByCd(shohinCd);

		// XMLを作成
		tag.append("<shohinNmUriden>" + zRecSeihin.getShohinNmUriden() + "</shohinNmUriden>\n");
		tag.append("<youkiKigoNm1>" + zRecSeihin.getYoukiKigoNm1() + "</youkiKigoNm1>\n");
		tag.append("<ktksyCd>" + zRecSeihin.getKtksyCd() + "</ktksyCd>\n");

		return tag.toString();
	}


	/**
	 * 縦線情報XML出力用文字列生成
	 * @param checkService
	 * @param kaisyaCd
	 * @param objCd
	 * @return
	 */
	public String createXmlTagForTatesenCd(EdiJuchuMainte_SearchService searchServ, String tatesenCd) {
		StringBuilder tag = new StringBuilder();

		EdiJuchuMainteRecord orosiRec = searchServ.getTatesenInfoByTatesnCd(tatesenCd);

		// 特約店CD
		tag.append("<orositenCd1Jiten>" + nullToString(orosiRec.getOrositenCd1Jiten()) + "</orositenCd1Jiten>\n");
		//  特約店名
		tag.append("<orositenNm1JitenRyaku>" + nullToString(orosiRec.getOrositenNm1JitenRyaku()) + "</orositenNm1JitenRyaku>\n");
		// 特約店住所
		tag.append("<address1Jiten>" + nullToString(orosiRec.getAddress1Jiten()) + "</address1Jiten>\n");
		// 特約店TEL
		tag.append("<tel1Jiten>" + nullToString(orosiRec.getTel1Jiten()) + "</tel1Jiten>\n");
		// デポ店CD
		tag.append("<orositenCdDepo>" + nullToString(orosiRec.getOrositenCdDepo()) + "</orositenCdDepo>\n");
		// デポ店名
		tag.append("<orositenNmDepoRyaku>" + nullToString(orosiRec.getOrositenNmDepoRyaku()) + "</orositenNmDepoRyaku>\n");
		// デポ店住所
		tag.append("<addressDepo>" + nullToString(orosiRec.getAddressDepo()) + "</addressDepo>\n");
		// デポ店TEL
		tag.append("<telDepo>" + nullToString(orosiRec.getTelDepo()) + "</telDepo>\n");
		// 二次店CD
		tag.append("<orositenCd2Jiten>" + nullToString(orosiRec.getOrositenCd2Jiten()) + "</orositenCd2Jiten>\n");
		// 二次店名
		tag.append("<orositenNm2JitenRyaku>" + nullToString(orosiRec.getOrositenNm2JitenRyaku()) + "</orositenNm2JitenRyaku>\n");
		// 二次店住所
		tag.append("<address2Jiten>" + nullToString(orosiRec.getAddress2Jiten()) + "</address2Jiten>\n");
		// 二次店TEL
		tag.append("<tel2Jiten>" + nullToString(orosiRec.getTel2Jiten()) + "</tel2Jiten>\n");
		// 三次店CD
		tag.append("<orositenCd3Jiten>" + nullToString(orosiRec.getOrositenCd3Jiten()) + "</orositenCd3Jiten>\n");
		// 三次店名
		tag.append("<orositenNm3JitenRyaku>" + nullToString(orosiRec.getOrositenNm3JitenRyaku()) + "</orositenNm3JitenRyaku>\n");
		// 三次店住所
		tag.append("<address3Jiten>" + nullToString(orosiRec.getAddress3Jiten()) + "</address3Jiten>\n");
		// 三次店TEL
		tag.append("<tel3Jiten>" + nullToString(orosiRec.getTel3Jiten()) + "</tel3Jiten>\n");
		// 出荷日注意
		tag.append("<syukaDtCyuiKbn>" + nullToString(orosiRec.getSyukaDtCyuiKbn()) + "</syukaDtCyuiKbn>\n");
		// 発注担当者
		tag.append("<hacyuTantosyaNm>" + nullToString(orosiRec.getHacyuTantosyaNm()) + "</hacyuTantosyaNm>\n");
		// 発注元TEL
		tag.append("<hacyumotoTel>" + nullToString(orosiRec.getHacyumotoTel()) + "</hacyumotoTel>\n");

//		// kz最終送荷先卸CD
//		tag.append("<orositenCdLast>" + nullToString(orosiRec.getOrositenCdLast()) + "</orositenCdLast>\n");
//		// EDI受発注商品摘要欄確認区分
//		tag.append("<ediJsyohinTekiyochekKbn>" + nullToString(orosiRec.getEdiJsyohinTekiyochekKbn()) + "</ediJsyohinTekiyochekKbn>\n");

		return tag.toString();
	}


	/**
	 * レスポンスへXMLを出力する
	 * @param xml xml
	 * @throws Exception
	 */
	protected void write(String xml) throws Exception {
		response_.setContentType("text/xml;charset=UTF-8");
		response_.addHeader("Pragma", "no-cache");
		response_.addHeader("Cache-Control", "no-cache");
		response_.addDateHeader("Expires", 0);

		PrintWriter out = response_.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		out.println(xml.toString());
		out.flush();
		out.close();
	}

	/**
	 * nullを空文字に変換
	 * @param obj
	 * @return
	 */
	public String nullToString(String obj) {
		return obj == null ? "" :  obj;
	}

}
