
////////////////////////////////////////////////////////////////////////////////
// 更新履歴
//  Ver.     日付                      担当者                            変更内容
//  1.0      2002/05/21    竹村正義                         新規作成
//  1.1      2002/09/17    Masayoshi.Takemura 予算執行申請からiPro経由と変わったためキーが
//                                            ReferenceNoと(ReqNoとReqLineNo)の2種類のデータを考慮
//  1.2      2002/09/19    Masayoshi.Takemura 予算合計を表示するように変更
//  1.3      2003/02/04    Masayoshi.Takemura フル桁表示対応
//  1.4      2003/06/06    Masayoshi.Takemura Done非表示か否か
//  1.5      2004/03/23    Masayoshi.Takemura ForecastStatus Vのみ表示対応(ソリューション推進部)
//  C00012   2006/02/16    Atsuko.Sashimura   表示列選択項目変更のため、Listのカラムも変更
//  C00016   2006/06/08    Atsuko Sashimura   プロジェクト組織の表示をとりあえずコード表示に
//  C00019   2006/11/15    Atsuko Sashimura   OracleSalesのデータを表示
//  C00057   2008/01/21    Atsuko.Sashimura   Siebel対応
//  C00066   2008/05/15    Atsuko.Sashimura   コンサル期間で絞る条件を5/31から8/31にする
//           2013/05/22    Mari.Suzuki        OppoTypeカラム分割対応
//           2013/06/07    Mari.Suzuki        組織変更対応
//           2013/07/17    Mari.Suzuki        レベトラ非表示検索対応
//           2013/07/18    Mari.Suzuki        締結QTR追加
//           2013/11/15    Mari.Suzuki        サービス分類（Tech）追加対応
//           2014/03/18    Mari.Suzuki        CLASSIFICATION4の文言変更
//           2014/03/26    Mari.Suzuki        Booking集計対象外追加
//           2014/05/01    Mari.Suzuki        Forcastが入力されないと計上残が表示されない不具合対応
//           2014/10/22    Minako.Uenaka      項目名英語表記対応
//           2016/02/15    Mari.Suzuki        表示期間18ヶ月対応
////////////////////////////////////////////////////////////////////////////////

package contents;

import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import contents.common.SendExportAlertMail;
import contentsServ.common.CommonUtil;
import contentsServ.sql.SqlManager;
import contentsServ.sql.SqlQuery;
import contentsServ.sql.SqlRow;


/**
 * Forecast一覧
 *
 */
public class ForecastList extends ContentsBase {
	// unit_idを使う最後のHistoryIDを入れておく。FY13を境に、PJのCOSTCENTERはproject_list_v.unit_idからproj_org_listに保持するように変更
	String BASE_HISTORY_ID = "H000389";

	private String csvExContId = null;
	private String csvSubcontractor = null;
	private String csvSubcontractInfo = null;
	private String csvPoNumber = null;
	private String csvSubcontractType = null;
	private int resultCount = 0; // データ件数

	/**
	 * 処理実行メソッド.
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response, Connection con) throws Exception {

		SqlManager sqlm = new SqlManager();

		// データをセット
		HashMap querysMain = getQueryMain(request);
		logDebug(" SQL qProject=" + (String) querysMain.get("PROJECT") + "\n SQL qExCont="
				+ (String) querysMain.get("EX_CONT"));
		SqlQuery qProject = sqlm.executeQuery(con, (String) querysMain.get("PROJECT"));
		SqlQuery qExCont = sqlm.executeQuery(con, (String) querysMain.get("EX_CONT"));

		HashSet selectedItem = (HashSet) querysMain.get("selectedItem");

		// パラメータ
		request.setAttribute("fy", request.getParameter("c_fy"));
		request.setAttribute("startMonth", request.getParameter("c_start_month"));
		request.setAttribute("showLength", request.getParameter("c_show_length"));
		request.setAttribute("forecastReportFlg", request.getParameter("forecastReport_flg"));
		request.setAttribute("historyId", request.getParameter("c_history_id"));
		request.setAttribute("o_ex_cont", getParameter(request, "o_ex_cont", ""));
		request.setAttribute("o_ex_cont_id", getParameter(request, "o_ex_cont_id", ""));
		request.setAttribute("cont_meisai_flg", getParameter(request, "cont_meisai_flg"));
		request.setAttribute("csv", getParameter(request, "csv", ""));
		request.setAttribute("noRev", getParameter(request, "noRev", ""));

		HttpSession session = request.getSession(true);
		Locale locale = request.getLocale();
		// sessionにロケールが入っていればsessionからロケール取得、入っていなければブラウザのロケール
		if (session.getAttribute("propertyLanguage") != null) {
			locale = (Locale) session.getAttribute("propertyLanguage");
		}

		ResourceBundle bundle = null;
		try {
			// プロパティファイル読み込み
			bundle = ResourceBundle.getBundle(PROPERTY_BASENAME, locale);
			// 検索結果の項目名をプロパティファイルより取得
			csvExContId = bundle.getString("ExContId"); // 業務委託ID(csv用)
			csvSubcontractor = bundle.getString("L_SUBCONTRACTOR"); // 業務委託先（csv用）
			csvSubcontractInfo = bundle.getString("L_SUBCONTRACT_INFO"); // 業務委託Info（csv用）
			csvSubcontractType = bundle.getString("L_SUBCONTRACT_TYPE"); // 業務委託契約形態（csv用）
			csvPoNumber = bundle.getString("L_PO_NUMBER");
			request.setAttribute("L_REVENUE_TOTAL", bundle.getString("L_REVENUE_TOTAL")); // 売上の合計
			request.setAttribute("L_SUBCONTRACTOR_TOTAL", bundle.getString("L_SUBCONTRACTOR_TOTAL")); // 業務委託合計
			request.setAttribute("L_OFFSHORE_TOTAL", bundle.getString("L_OFFSHORE_TOTAL")); // OSSI合計
			request.setAttribute("L_INTERBU_TOTAL", bundle.getString("L_INTERBU_TOTAL")); // InterBU(外コン)合計
			request.setAttribute("L_EX_CONT_ID", csvExContId); // 業務委託ID
			request.setAttribute("L_SUBCONTRACTOR", csvSubcontractor); // 業務委託先
			request.setAttribute("L_SUBCONTRACT_INFO", csvSubcontractInfo); // 業務委託Info
			request.setAttribute("L_PO_NUMBER", csvPoNumber);// PO番号
			request.setAttribute("L_SUBCONTRACT_TYPE", csvSubcontractType); // 業務委託契約形態

		} catch (MissingResourceException e) {
			log("PropertyFileGet execute:プロパティファイルが見つかりませんでした。");
			throw e;
		}

		if ("yes".equals(getParameter(request, "csv", ""))) {
			exportCsv(request, response, selectedItem, qProject, qExCont);
			Statement stmt = con.createStatement();
			String emp_id = getCookie("USERID");
			SendExportAlertMail.sendExportMail(emp_id, request, con, stmt);
			if (resultCount >= 1000) {
				String sql = "insert into err_table(ERR_DATE,ERR_CODE,ERR_MSG,ERR_DETAIL) "
						+ "values (SYSDATE,null,'Alert:社員番号: " + emp_id + " のアカウントから" + resultCount
						+ "件のデータが出力されました。','Forecast検索')";
				log("sql;" + sql);
				stmt.executeUpdate(sql);
			}
		} else {
			// カラムタイトル
			request.setAttribute("pjTitleList", getPjTitleList(request));
			// 順番を保持したItem一覧
			request.setAttribute("pjItemList", getPjItemList());
			// プロジェクトデータ
			request.setAttribute("qProject", qProject);
			request.setAttribute("qExCont", qExCont);
			// 選択されたキー
			request.setAttribute("selectedItem", selectedItem);
			// その他
			request.setAttribute("isFull", getParameter(request, "isFull", ""));
			request.setAttribute("noDone", getParameter(request, "noDone", ""));

			// JSPにforward
			forward(request, response, "/jsp/forecast/ForecastList.jsp");
		}

	}

	/**
	 * クエリー取得メソッド.
	 *
	 */
	protected Hashtable getQuery(HttpServletRequest request) {
		Hashtable querys = new Hashtable();
		return querys;
	}

	/**
	 * プロジェクトの項目と表示順を保持。 valueにはSELECT文のカラム名と同じものを入れる。 表示順に並べておく。
	 */
	private ArrayList<String> getPjItemList() {
		ArrayList<String> pjItemList = new ArrayList<String>();
		pjItemList.add("PRD_ORG_NAME"); // PRJ管理組織
		pjItemList.add("ORG_NAME"); // MP組織
		pjItemList.add("LATEST_MPORG"); // 最新MP組織
		pjItemList.add("PA_CC_ID"); // PAコストセンタ
		pjItemList.add("ADMI"); // 担当アドミ
		pjItemList.add("FPAUTO"); // FPA
		pjItemList.add("WORK_STATUS"); // W
		pjItemList.add("WIN_PROB"); // P
		pjItemList.add("BOOK_STATUS"); // Book
		pjItemList.add("REV_STATUS"); // Rev
		pjItemList.add("OPPO_STATUS"); // OppoStatus
		pjItemList.add("PROGRESS"); // SalesStage
		pjItemList.add("PROJ_CODE"); // TempNumber
		pjItemList.add("BUNRUI"); // SubIndustry
		pjItemList.add("CONS_CATEGORYS"); // SubIndustry
		pjItemList.add("OPPO_NUMBER"); // OppoID
		pjItemList.add("GSI_PROJ_CODE"); // ProjectNumber
		pjItemList.add("CONTRACT_NUMBER"); // Contract Number
		pjItemList.add("CLIENT_NAME"); // 契約先
		pjItemList.add("END_USER_NAME"); // エンドユーザ
		pjItemList.add("EU_OFFICIAL_NAME"); // エンドユーザ正式
		pjItemList.add("KEY_ACNT"); // KEYアカウント
		pjItemList.add("TL_ACNT"); // TLアカウント
		pjItemList.add("PROJ_NAME"); // ProjectName
		pjItemList.add("MODULE"); // Module
		pjItemList.add("CONS_CATEGORY"); // ConsCategory
		// 20190816 zhangt SIの項目「Campaign Name」を取り込みForecastに追加 start
		pjItemList.add("CAMPAIGN_NAME"); // Campaign Name
		// 20190816 zhangt SIの項目「Campaign Name」を取り込みForecastに追加 end
		pjItemList.add("PRODUCT_NAME"); // ProductName
		pjItemList.add("INDUSTRY"); // インダストリ
		pjItemList.add("CONSULTING_TYPE"); // Consulting Type
		pjItemList.add("REVENUE_TYPE"); // Revenue Type
		pjItemList.add("CLASSIFICATION"); // Classification
		pjItemList.add("BOOKING_ORG"); // BOOKING管理組織
		pjItemList.add("PROJECT_TOTAL"); // 案件総額
		pjItemList.add("CLASSIFICATION_4"); // Classitication4
		pjItemList.add("SA_INFO"); // SA
		pjItemList.add("SERVICE_CLASSIFICATION"); // サービス分類（Tech）
		pjItemList.add("SUM_BUDGET"); // 予算合計
		pjItemList.add("PROJ_START"); // プロジェクト開始日
		pjItemList.add("PROJ_END"); // プロジェクト終了日
		pjItemList.add("SKU_NO"); // SKU型番
		pjItemList.add("SKU_SPAN"); // SKUサービス期間
		pjItemList.add("SKU_REV_REC_RULE"); // SKU RevRecRule
		pjItemList.add("SALES"); // PrimarySales
		pjItemList.add("BID"); // Bid
		pjItemList.add("PO_PM"); // POPM
		pjItemList.add("CONTRACT_DATE"); // 契約締結(予定)日
		pjItemList.add("CONTRACT_QUARTER"); // 締結QTR
		pjItemList.add("OPTY_CREATION_DATE"); // 案件登録日
		pjItemList.add("CONTRACT_AMOUNT"); // 契約金額
		pjItemList.add("OWNCONTRACT_AMOUNT"); // 契約金額 自組織
		pjItemList.add("KJZ"); // 計上残
		pjItemList.add("NOT_BOOK_SUM"); // Booking集計対象外
		pjItemList.add("LIC_OPPO_ID"); // LIC. OPPO ID
		pjItemList.add("REASON_FOR_CHANGE"); // 変更理由
		pjItemList.add("CONTRACT_TYPE"); // 契約形態
		pjItemList.add("BTN_CONT"); // 業務委託(button)
		pjItemList.add("BTN_FCST"); // Forecast(button)

		return pjItemList;
	}

	/**
	 * プロジェクトの項目に表示する文字列を保持。
	 * 表示する/しないに関わらず値をセットする。NAMEはSELECT文のカラム名と同じにする。VALUEに表示文字列をセットする。
	 *
	 * @return
	 */
	private HashMap getPjTitleList(HttpServletRequest request) throws Exception {
		String o_po = getParameter(request, "o_proj_owner_name", null);
		String o_pm = getParameter(request, "o_proj_mgr_name", null);

		HttpSession session = request.getSession(true);
		Locale locale = request.getLocale();
		// sessionにロケールが入っていればsessionからロケール取得、入っていなければブラウザのロケール取得
		if (session.getAttribute("propertyLanguage") != null) {
			locale = (Locale) session.getAttribute("propertyLanguage");
		}

		ResourceBundle bundle = null;
		HashMap<String, String> hmPjTitle = new HashMap<String, String>();
		try {
			// プロパティファイル読み込み
			bundle = ResourceBundle.getBundle(PROPERTY_BASENAME, locale);

			// 検索結果の項目名を格納
			hmPjTitle.put("PRD_ORG_NAME", bundle.getString("L_PJ_ORG"));
			hmPjTitle.put("ORG_NAME", bundle.getString("L_MP_ORG"));
			hmPjTitle.put("LATEST_MPORG", bundle.getString("L_LATEST_MP_ORG"));
			hmPjTitle.put("PA_CC_ID", bundle.getString("L_PA_CC"));
			hmPjTitle.put("ADMI", bundle.getString("L_ADMI"));
			hmPjTitle.put("FPAUTO", bundle.getString("L_FPA"));
			hmPjTitle.put("WORK_STATUS", bundle.getString("L_W"));
			hmPjTitle.put("WIN_PROB", bundle.getString("L_P"));
			hmPjTitle.put("BOOK_STATUS", bundle.getString("L_BOOK"));
			hmPjTitle.put("REV_STATUS", bundle.getString("L_REV"));
			hmPjTitle.put("OPPO_STATUS", bundle.getString("L_OPTY_STATUS"));
			hmPjTitle.put("PROGRESS", bundle.getString("L_SALES_STAGE"));
			hmPjTitle.put("PROJ_CODE", bundle.getString("L_TEMP_NUMBER"));
			hmPjTitle.put("BUNRUI", bundle.getString("L_SUBIND"));
			hmPjTitle.put("OPPO_NUMBER", bundle.getString("L_OPTY_NUMBER"));
			hmPjTitle.put("GSI_PROJ_CODE", bundle.getString("L_PJ_NUMBER"));
			hmPjTitle.put("CONTRACT_NUMBER", bundle.getString("CONTRACT_NUMBER"));
			hmPjTitle.put("CLIENT_NAME", bundle.getString("L_CUSTOMER"));
			hmPjTitle.put("END_USER_NAME", bundle.getString("L_ENDUSER"));
			hmPjTitle.put("EU_OFFICIAL_NAME", bundle.getString("L_ENDUSER_CORRECTED"));
			hmPjTitle.put("KEY_ACNT", bundle.getString("L_KEY"));
			hmPjTitle.put("TL_ACNT", bundle.getString("L_TL"));
			hmPjTitle.put("PROJ_NAME", bundle.getString("L_PJ_NAME"));
			hmPjTitle.put("MODULE", bundle.getString("L_MODULE"));
			hmPjTitle.put("CONS_CATEGORY", bundle.getString("CONS_CATEGORY"));
			// 20190816 zhangt SIの項目「Campaign Name」を取り込みForecastに追加 start
			hmPjTitle.put("CAMPAIGN_NAME", bundle.getString("CAMPAIGN_NAME"));
			// 20190816 zhangt SIの項目「Campaign Name」を取り込みForecastに追加 end
			hmPjTitle.put("PRODUCT_NAME", bundle.getString("PRODUCT_NAME"));
			hmPjTitle.put("INDUSTRY", bundle.getString("L_INDUSTRY"));
			hmPjTitle.put("CONSULTING_TYPE", bundle.getString("L_CONSULTING_TYPE"));
			hmPjTitle.put("REVENUE_TYPE", bundle.getString("L_REVENUE_TYPE"));
			hmPjTitle.put("CLASSIFICATION", bundle.getString("L_CLASSIFICATION"));
			hmPjTitle.put("BOOKING_ORG", bundle.getString("L_BOOKING_ORG"));
			hmPjTitle.put("PROJECT_TOTAL", bundle.getString("L_SCALE"));
			hmPjTitle.put("CLASSIFICATION_4", bundle.getString("L_SECTORAL_ANALYSIS"));
			hmPjTitle.put("SA_INFO", bundle.getString("L_SA"));
			hmPjTitle.put("SERVICE_CLASSIFICATION", bundle.getString("L_SERVICE_TYPE"));
			hmPjTitle.put("SUM_BUDGET", bundle.getString("L_BUDGET"));
			hmPjTitle.put("PROJ_START", bundle.getString("L_PJ_START"));
			hmPjTitle.put("PROJ_END", bundle.getString("L_PJ_END"));
			hmPjTitle.put("SKU_NO", bundle.getString("SKU_NO"));
			hmPjTitle.put("SKU_SPAN", bundle.getString("SKU_SPAN"));
			hmPjTitle.put("SKU_REV_REC_RULE", bundle.getString("SKU_REV_REC_RULE"));
			hmPjTitle.put("SALES", bundle.getString("L_OPTY_OWNER"));
			hmPjTitle.put("BID", bundle.getString("L_BID"));
			if (o_po != null && o_pm != null) {
				hmPjTitle.put("PO_PM", bundle.getString("L_PO_PM"));
			} else if (o_po != null) {
				hmPjTitle.put("PO_PM", bundle.getString("L_PO"));
			} else {
				hmPjTitle.put("PO_PM", bundle.getString("L_PM"));
			}
			hmPjTitle.put("CONTRACT_DATE", bundle.getString("L_CLOSE_DATE"));
			hmPjTitle.put("CONTRACT_QUARTER", bundle.getString("L_BOOKING_QTR"));
			hmPjTitle.put("OPTY_CREATION_DATE", bundle.getString("OPTY_CREATION_DATE"));
			hmPjTitle.put("CONTRACT_AMOUNT", bundle.getString("L_BOOKING_BEFORE_CCXFER"));
			hmPjTitle.put("OWNCONTRACT_AMOUNT", bundle.getString("L_BOOKING_AMOUNT"));
			hmPjTitle.put("KJZ", bundle.getString("L_REMAINING_AMOUNT"));
			hmPjTitle.put("NOT_BOOK_SUM", bundle.getString("L_BOOK_EXCL"));
			hmPjTitle.put("LIC_OPPO_ID", bundle.getString("LIC_OPPO_ID"));
			hmPjTitle.put("REASON_FOR_CHANGE", bundle.getString("L_CHANGE_REASON"));
			hmPjTitle.put("CONTRACT_TYPE", bundle.getString("L_CONTRACT_TYPE"));
			hmPjTitle.put("BTN_CONT", bundle.getString("L_SUBCONTRACT"));
			hmPjTitle.put("BTN_FCST", bundle.getString("L_FORECAST"));

		} catch (MissingResourceException e) {
			log("PropertyFileGet execute:プロパティファイルが見つかりませんでした。");
			throw e;
		}
		return hmPjTitle;

	}

	/**
	 * Main SQL
	 *
	 */
	private HashMap getQueryMain(HttpServletRequest request) {
		HashMap querys = new HashMap();
		HashSet selectedItem = new HashSet();
		// 条件項目
		String c_proj_code = setFormatLikeSqlString(request.getParameter("c_proj_code"));
		String c_gsi_proj_code = setFormatLikeSqlString(request.getParameter("c_gsi_proj_code"));
		String c_proj_name = setFormatLikeSqlString(request.getParameter("c_proj_name"));
		String c_end_user_name = setFormatLikeSqlString(request.getParameter("c_end_user_name"));
		String c_client_name = setFormatLikeSqlString(request.getParameter("c_client_name"));
		String c_bunrui_code = setFormatLikeSqlString(request.getParameter("c_bunrui_code"));
		String c_category_id = setFormatLikeSqlString(request.getParameter("c_category_id"));
		String c_pa_cc_id = setFormatLikeSqlString(request.getParameter("c_pa_cc_id"));
		String c_oppo_number = setFormatLikeSqlString(request.getParameter("c_oppo_number"));
		String c_admi = setFormatLikeSqlString(request.getParameter("c_admi"));
		String admiIsnull = getParameter(request, "c_admi_isnull", "off");
		String skuOnly = getParameter(request, "skuOnly", "off");
		String c_org_id = request.getParameter("c_org_id");
		String c_prj_org = request.getParameter("c_prj_org");
		String c_contract_id = request.getParameter("c_contract_id");
		String c_sales = request.getParameter("c_sales");
		String c_proj_owner_name = setFormatLikeSqlString(request.getParameter("c_proj_owner_name"));
		String c_proj_mgr_name = setFormatLikeSqlString(request.getParameter("c_proj_mgr_name"));
		String c_work_status = request.getParameter("c_work_status");
		String c_classification = setFormatLikeSqlString(request.getParameter("c_classification"));
		String c_booking_org = setFormatLikeSqlString(request.getParameter("c_booking_org"));
		String c_revenue_ci = request.getParameter("c_revenue_ci");
		String c_history_id = request.getParameter("c_history_id");
		String c_fy = request.getParameter("c_fy");
		int c_start_month = Integer.parseInt(request.getParameter("c_start_month"));
		int c_show_length = Integer.parseInt(request.getParameter("c_show_length"));

		// 表示項目
		String o_proj_code = request.getParameter("o_proj_code");
		String o_gsi_proj_code = request.getParameter("o_gsi_proj_code");
		String o_oppo_number = request.getParameter("o_oppo_number");
		String o_proj_name = request.getParameter("o_proj_name");
		String o_end_user_name = request.getParameter("o_end_user_name");
		String o_eu_official_name = request.getParameter("o_eu_official_name");
		String o_key_acnt = request.getParameter("o_key_acnt");
		String o_tl_acnt = request.getParameter("o_tl_acnt");
		String o_client_name = request.getParameter("o_client_name");
		String o_org_name = request.getParameter("o_org_name");
		String o_pa_cc_id = request.getParameter("o_pa_cc_id");
		String o_admi = request.getParameter("o_admi");
		String o_latest_mporg = request.getParameter("o_latest_mporg");
		String o_fpa = request.getParameter("o_fpa");
		String o_cont_amount = request.getParameter("o_cont_amount");
		String o_own_cont_amount = request.getParameter("o_own_cont_amount");
		String o_contract_type = request.getParameter("o_contract_type");
		String o_proj_owner_name = request.getParameter("o_proj_owner_name");
		String o_proj_mgr_name = request.getParameter("o_proj_mgr_name");
		String o_book_status = request.getParameter("o_book_status");
		String o_rev_status = request.getParameter("o_rev_status");
		String o_work_status = request.getParameter("o_work_status");
		String o_proj_start = request.getParameter("o_proj_start");
		String o_proj_end = request.getParameter("o_proj_end");
		String o_classification = request.getParameter("o_classification");
		String o_booking_org = request.getParameter("o_booking_org");
		String o_classification_4 = request.getParameter("o_classification_4");
		String o_service_classification = request.getParameter("o_service_classification");
		String o_project_total = request.getParameter("o_project_total");
		String o_sa_info = request.getParameter("o_sa_info");
		String o_ex_cont = request.getParameter("o_ex_cont");
		String o_not_book_sum = request.getParameter("o_not_book_sum");
		String o_lic_oppo_id = request.getParameter("o_lic_oppo_id");
		String o_reason_for_change = request.getParameter("o_reason_for_change");
		String o_update_button = request.getParameter("o_update_button");

		String o_sum_budget = request.getParameter("o_sum_budget");
		String o_sales = request.getParameter("o_sales");
		String o_bid = request.getParameter("o_bid");
		String o_win_prob = request.getParameter("o_win_prob");
		String o_contract_date = request.getParameter("o_contract_date");
		String o_contract_quarter = request.getParameter("o_contract_quarter");
		String o_opty_creation_date = request.getParameter("o_opty_creation_date");
		String o_oppo_status = request.getParameter("o_oppo_status");
		String o_progress = request.getParameter("o_progress");
		String o_module = request.getParameter("o_module");
		String o_product_name = request.getParameter("o_product_name");
		String o_industry = request.getParameter("o_industry");
		String o_bunrui = request.getParameter("o_bunrui");
		String o_consul_type = request.getParameter("o_consul_type");
		String o_revenue_type = request.getParameter("o_revenue_type");
		String o_cons_category = request.getParameter("o_cons_category");
		// PRD組織
		String o_prd_org_name = request.getParameter("o_prd_org_name");
		// Contract_Number
		String o_contract_number = request.getParameter("o_contract_number");
		// SKU
		String o_sku_no = request.getParameter("o_sku_no");
		String o_sku_span = request.getParameter("o_sku_span");
		String o_sku_rev_rec_rule = request.getParameter("o_sku_rev_rec_rule");
		// 20190816 zhangt SIの項目「Campaign Name」を取り込みForecastに追加 start
		String o_campaign_name = request.getParameter("o_campaign_name");
		// 20190816 zhangt SIの項目「Campaign Name」を取り込みForecastに追加 end

		// プロジェクト用
		StringBuffer pjtSelect = new StringBuffer("SELECT NULL");
		StringBuffer pjtFrom = new StringBuffer(" FROM");
		StringBuffer pjtWhere = new StringBuffer(" WHERE 1=1");
		StringBuffer orderby = new StringBuffer(" ORDER BY ");

		// 業務委託用
		StringBuffer pjtSelect_ex = new StringBuffer("SELECT NULL");
		StringBuffer pjtFrom_ex = new StringBuffer(" FROM");
		StringBuffer pjtWhere_ex = new StringBuffer(" WHERE 1=1");
		StringBuffer pjtGroupby_ex = new StringBuffer(" GROUP BY NULL");
		StringBuffer orderby_ex = new StringBuffer(" ORDER BY NULL");

		// 契約金額算出用
		StringBuffer contAmntSelect = new StringBuffer("");
		// 計上残算出用
		StringBuffer kjzSelect = new StringBuffer("");

		DecimalFormat MM = new DecimalFormat("00");

		// 必須条件
		if (!getParameter(request, "c_history_id", "").equals("")
				&& !getParameter(request, "forecastReport_flg", "").equals("yes")) {
			pjtFrom.append("\n (select * from project_list_history_v pjt where history_id = '" + c_history_id + "' "
					+ getConsulPeriod(c_fy) + ") PJT, proj_oppo_bunrui_list ob");
			pjtFrom_ex.append("\n (select * from project_list_history_v pjt where history_id = '" + c_history_id + "' "
					+ getConsulPeriod(c_fy) + ") PJT");
			pjtFrom_ex.append(
					"\n,(select * from ex_contract_list_history where history_id = '" + c_history_id + "') EX_CONT");
			pjtWhere.append("\n AND PJT.BUNRUI_CODE = OB.BUNRUI_CODE(+) ");
		} else {
			pjtFrom.append("\n project_list_v PJT, proj_oppo_bunrui_list ob");
			pjtFrom_ex.append("\n project_list_v PJT");
			pjtFrom_ex.append("\n,ex_contract_list EX_CONT");
			pjtWhere.append("\n AND PJT.BUNRUI_CODE = OB.BUNRUI_CODE(+) ");
		}

		// report画面で履歴が選択されている場合
		if (!getParameter(request, "c_history_id", "").equals("")
				&& getParameter(request, "forecastReport_flg", "").equals("yes")) {
			pjtSelect.append(
					"\n , NVL(PJT_HIS.WIN_PROB,NULL) HIS_WIN_PROB, NVL(PJT_HIS.BOOK_STATUS,' ') HIS_BOOK_STATUS, NVL(PJT_HIS.REV_STATUS,' ') HIS_REV_STATUS ");
			pjtFrom.append(
					"\n, (select inpjthis.proj_code, inpjthis.win_prob, booksthis.status_name book_status, revsthis.status_name rev_status "
							+ "\n from (select * from project_list_history_v pjt where history_id = '" + c_history_id
							+ "' " + getConsulPeriod(c_fy)
							+ ") inpjthis, fcst_status_list booksthis, fcst_status_list revsthis "
							+ "\n where  inpjthis.book_status = booksthis.status_id(+) and inpjthis.rev_status = revsthis.status_id(+) ) PJT_HIS ");
			pjtWhere.append("\n and pjt.proj_code = pjt_his.proj_code(+) ");
		}

		// レベトラ非表示がONの場合
		if (!getParameter(request, "noRev", "").equals("") && getParameter(request, "noRev", "").equals("yes")) {
			pjtWhere.append("\n AND NVL(PJT.GSI_PROJ_CODE, '000000000') NOT LIKE '%#_R%' ESCAPE '#'");
		}

		// 表示項目(業務委託詳細)が選択されていなくても業務委託用にreference_noを確保
		pjtSelect_ex.append("\n,EX_CONT.reference_no reference_no");
		pjtGroupby_ex.append("\n,EX_CONT.reference_no");
		orderby_ex.append("\n,OPJT.ex_cont_name");

		// 表示項目に選択されていなくても業務委託との結び付き用に
		// プロジェクトコードを確保しておく(PROJ_CODEとは区別してCODEとしておく)
		pjtSelect.append("\n,PJT.proj_code CODE");
		// 表示項目に選択されていなくてもDone別表示用に
		pjtSelect.append("\n,PJT.WORK_STATUS WS_CODE");

		// コンサル期間が指定されたFYにかかっているという条件
		// 契約締結日と終了日の遅いほうに1ヶ月足した年月と比べる
		pjtWhere.append(getConsulPeriod(c_fy));
		pjtWhere_ex.append(getConsulPeriod(c_fy));

		// RevenueかCIのどっち？
		if (c_revenue_ci != null) {
			if (c_revenue_ci.equals("0")) {
				// Revenue
				pjtWhere.append("\n and PJT.contract_id in (1,2,5,6,7,9,11,13,14)");
				pjtWhere_ex.append("\n and PJT.contract_id in (1,2,5,6,7,9,11,13,14)");
			} else {
				// CI
				pjtWhere.append("\n and PJT.contract_id in (3,4,8,12)");
				pjtWhere_ex.append("\n and PJT.contract_id in (3,4,8,12)");
			}
		}

		int _fy = Integer.parseInt(c_fy);

		// 組織名
		if (o_org_name != null) {
			pjtSelect.append(
					"\n, NVL(DECODE(SUBSTR(ORG.CC_ORG_ID,0,6),'CC0000',SUBSTR(ORG.CC_ORG_ID,7),SUBSTR(ORG.CC_ORG_ID,5)),' ') ORG_NAME");
			selectedItem.add("ORG_NAME");
		}

		// PRD組織
		if (o_prd_org_name != null) {
			pjtSelect.append(",ORG.ORG_NAME_SHORT PRD_ORG_NAME ");
			selectedItem.add("PRD_ORG_NAME");
		}
		int fy = Integer.parseInt(c_fy);
		// PAコストセンタ
		if (o_pa_cc_id != null) {
			if (fy <= 2017) {
				// FY17以前の場合旧コストセンタ(4桁)を表示する
				pjtSelect.append("\n,PJT.old_pa_cc_id PA_CC_ID");
			} else {
				pjtSelect.append("\n,PJT.pa_cc_id PA_CC_ID");
			}
			selectedItem.add("PA_CC_ID");
		}
		if (c_pa_cc_id != null && !c_pa_cc_id.equals("")) {
			if (fy <= 2017) {
				// FY17以前の場合旧コストセンタ(4桁)で検索する
				pjtWhere.append("\n and pjt.old_pa_cc_id = '" + c_pa_cc_id + "'");
				pjtWhere_ex.append("\n and pjt.old_pa_cc_id = '" + c_pa_cc_id + "'");
			} else {
				pjtWhere.append("\n and pjt.pa_cc_id = '" + c_pa_cc_id + "'");
				pjtWhere_ex.append("\n and pjt.pa_cc_id = '" + c_pa_cc_id + "'");
			}
		}

		// ■■組織系
		StringBuffer orgSelect = new StringBuffer(" SELECT DISTINCT PROJ_CODE ");
		StringBuffer orgFrom = new StringBuffer("");
		StringBuffer orgWhere = new StringBuffer("\n WHERE 1=1");
		if (!getParameter(request, "c_history_id", "").equals("")
				&& !getParameter(request, "forecastReport_flg", "").equals("yes")) {
			// HistoryIdがあり、Reportでない場合
			if (BASE_HISTORY_ID.compareTo(c_history_id) >= 0) {
				// unit_id時代のやつ
				orgFrom.append("\n FROM PROJ_ORG_LIST_HISTORY PORG");
				orgWhere.append(
						"\n AND PORG.HISTORY_ID = (SELECT MIN(HISTORY_ID) FROM HISTORY_LIST WHERE HISTORY_ID > '"
								+ BASE_HISTORY_ID + "' )");

			} else {
				// proj_org_list時代のやつ
				orgFrom.append("\n FROM PROJ_ORG_LIST_HISTORY PORG");
				orgWhere.append("\n AND PORG.HISTORY_ID = '" + c_history_id + "' ");

			}
		} else {
			// それ以外は組織Historyは見ない
			orgFrom.append("\n FROM PROJ_ORG_LIST PORG ");
		}

		if (o_org_name != null) {
			orgSelect.append(", CC_ORG_ID");
			pjtSelect.append(", ORG.CC_ORG_ID");
			// ソート用
			pjtFrom.append(",	(select org_id, sort_number " + "\n from cost_center_list "
					+ "\n where (org_id, org_start_date) in "
					+ "\n 	(select org_id, max(org_start_date) org_start_date " + "\n 	from cost_center_list "
					+ "\n 	where org_id like 'CC00%' " + "\n 	and   sort_number is not null "
					+ "\n 	group by org_id " + "\n 	) " + "\n ) CCSORT ");
			pjtSelect.append("\n, ccsort.sort_number CCSORT ");
			pjtWhere.append("\n AND org.cc_org_id = ccsort.org_id(+) ");
		}
		if (o_prd_org_name != null) {
			pjtSelect.append(", ORG.PROD_ORG_ID ");
			pjtSelect.append(", ORG.SORT_NUMBER PDORG_SORT");
			orgSelect.append(", PROD_ORG_ID, PDORG.ORG_NAME_SHORT "); // 名前も追加
			orgSelect.append(", PDORG.SORT_NUMBER ");
			orgFrom.append(", PRODUCT_ORG_LIST PDORG ");
			orgWhere.append("\n AND PORG.PROD_ORG_ID = PDORG.ORG_ID");
		}
		// c_org_id条件がある場合
		if ((c_org_id != null) && (!c_org_id.equals(""))) {
			// fy12のやつだったら
			if (getParameter(request, "fy12_flg") != null) {
				orgFrom.append("\n,PRODUCT_ORG_LIST PRD2");
				orgWhere.append("\n AND PORG.PROD_ORG_ID = PRD2.ORG_ID(+)");
				orgWhere.append("\n AND DECODE((SELECT DISTINCT ORG_LEVEL FROM PRODUCT_ORG_LIST WHERE ORG_ID = '"
						+ c_org_id + "'" + getPrdOrgPeriod(_fy, ""));
				orgWhere.append("\n ),1,PRD2.LEVEL1,2,PRD2.LEVEL2,3,PRD2.LEVEL3,4,PRD2.LEVEL4,5,PRD2.LEVEL5) = '"
						+ c_org_id + "'");
				orgWhere.append(
						"\n AND PRD2.ORG_START_DATE = (SELECT MAX(ORG_START_DATE) FROM PRODUCT_ORG_LIST WHERE ORG_ID = PRD2.ORG_ID");
				orgWhere.append(getOrgPeriod2(_fy, ""));
				orgWhere.append("\n )");
				orgWhere.append("\n AND PORG.PROD_ORG_ID = PRD2.ORG_ID ");
			} else {
				orgFrom.append("\n,cost_center_list CC2");
				orgWhere.append("\n and PORG.CC_ORG_ID = CC2.org_id(+)");
				orgWhere.append("\n and DECODE((SELECT distinct org_level FROM cost_center_list where org_id = '"
						+ c_org_id + "'" + getOrgPeriod(_fy, ""));
				orgWhere.append(
						"\n ),1,CC2.level1,2,CC2.level2,3,CC2.level3,4,CC2.level4,5,CC2.level5) = '" + c_org_id + "'");
				orgWhere.append(
						"\n and CC2.org_start_date = (select max(org_start_date) from cost_center_list where org_id = CC2.org_id");
				orgWhere.append(getOrgPeriod2(_fy, ""));
				orgWhere.append("\n )");
			}
		}
		if (c_prj_org != null && !c_prj_org.equals("")) {
			orgFrom.append("\n,PRODUCT_ORG_LIST PRD2");
			orgWhere.append("\n AND PORG.PROD_ORG_ID = PRD2.ORG_ID(+)");
			orgWhere.append("\n AND DECODE((SELECT DISTINCT ORG_LEVEL FROM PRODUCT_ORG_LIST WHERE ORG_ID = '"
					+ c_prj_org + "'" + getPrdOrgPeriod(_fy, ""));
			orgWhere.append(
					"\n ),1,PRD2.LEVEL1,2,PRD2.LEVEL2,3,PRD2.LEVEL3,4,PRD2.LEVEL4,5,PRD2.LEVEL5,PRD2.LEVEL6) = '"
							+ c_prj_org + "'");
			orgWhere.append(
					"\n AND PRD2.ORG_START_DATE = (SELECT MAX(ORG_START_DATE) FROM PRODUCT_ORG_LIST WHERE ORG_ID = PRD2.ORG_ID");
			orgWhere.append(getOrgPeriod2(_fy, ""));
			orgWhere.append("\n )");
			orgWhere.append("\n AND PORG.PROD_ORG_ID = PRD2.ORG_ID ");
		}

		if (c_start_month >= 6) {
			// FYの5末と、表示期間の最後の月の遅い方まで表示
			orgWhere.append(
					"\n AND PORG.START_DATE < GREATEST(TO_DATE('" + _fy + "/06/01','yyyy/mm/dd'),ADD_MONTHS(TO_DATE('"
							+ (_fy - 1) + "/" + c_start_month + "/01','YYYY/MM/DD')," + c_show_length + ")  )  ");
		} else {
			orgWhere.append(
					"\n AND PORG.START_DATE < GREATEST(TO_DATE('" + _fy + "/06/01','yyyy/mm/dd'),ADD_MONTHS(TO_DATE('"
							+ (_fy) + "/" + c_start_month + "/01','YYYY/MM/DD')," + c_show_length + ")  )  ");
		}
		orgWhere.append("\n AND TO_DATE('" + (_fy - 1)
				+ "/06/01','yyyy/mm/dd') <= nvl(PORG.END_DATE, TO_DATE('9999/12/31','YYYY/MM/DD')) ");

		pjtFrom.append("\n\t,( " + orgSelect.toString() + orgFrom.toString() + orgWhere.toString() + ") ORG");
		pjtFrom_ex.append("\n\t,( " + orgSelect.toString() + orgFrom.toString() + orgWhere.toString() + ") ORG");
		pjtWhere.append("\n AND PJT.PROJ_CODE = ORG.PROJ_CODE ");
		pjtWhere_ex.append("\n AND PJT.PROJ_CODE = ORG.PROJ_CODE ");

		// ■■組織系END
		// 最新MP組織
		if (o_latest_mporg != null) {
			pjtFrom.append(
					"\n ,(SELECT PROJ_CODE LMO_PROJ_CODE, DECODE(SUBSTR(CC_ORG_ID,0,6),'CC0000',SUBSTR(CC_ORG_ID,7),SUBSTR(CC_ORG_ID,5)) LATEST_MPORG "
							+ "\n 	FROM PROJ_ORG_LIST PJORG " + "\n 	WHERE (PJORG.PROJ_CODE, PJORG.START_DATE) IN "
							+ "\n 	(SELECT PJORG.PROJ_CODE, MAX(START_DATE) START_DATE "
							+ "\n 	    FROM   PROJ_ORG_LIST PJORG, PROJECT_LIST PJT "
							+ "\n 	    WHERE  PJT.PROJ_CODE = PJORG.PROJ_CODE " + getConsulPeriod(c_fy)
							+ "\n 	    GROUP BY PJORG.PROJ_CODE  " + "\n 	)) LATEST_MPORG");
			pjtSelect.append(",LATEST_MPORG.LATEST_MPORG");
			pjtWhere.append("\n AND PJT.PROJ_CODE = LATEST_MPORG.LMO_PROJ_CODE(+) ");
			selectedItem.add("LATEST_MPORG");
		}

		// proj_code(PROJ_CODEはソートで使うので取得)
		pjtSelect.append("\n,PJT.proj_code PROJ_CODE");
		if (o_proj_code != null) {
			selectedItem.add("PROJ_CODE");
		}
		if (c_proj_code != null && !c_proj_code.equals("")) {
			// 全角空白は半角空白に置換する
			c_proj_code = c_proj_code.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String projCodeArr[] = c_proj_code.split("\\s");
			for (int i = 0; i < projCodeArr.length; i++) {
				pjtWhere.append("\n AND LOWER(PJT.PROJ_CODE) LIKE '%' || LOWER('" + projCodeArr[i] + "') || '%' "
						+ SQL_ESC_STR);
				pjtWhere_ex.append("\n AND LOWER(PJT.PROJ_CODE) LIKE '%' || LOWER('" + projCodeArr[i] + "') || '%' "
						+ SQL_ESC_STR);
			}
		}

		// gsi_proj_code(gsi_proj_codeはソートで使うので取得)
		pjtSelect.append("\n,PJT.gsi_proj_code GSI_PROJ_CODE");
		if (o_gsi_proj_code != null) {
			selectedItem.add("GSI_PROJ_CODE");
		}
		if (c_gsi_proj_code != null && !c_gsi_proj_code.equals("")) {
			// 全角空白は半角空白に置換する
			c_gsi_proj_code = c_gsi_proj_code.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String gsiProjCodeArr[] = c_gsi_proj_code.split("\\s");
			for (int i = 0; i < gsiProjCodeArr.length; i++) {
				pjtWhere.append("\n AND LOWER(PJT.GSI_PROJ_CODE) LIKE '%' || LOWER('" + gsiProjCodeArr[i] + "') || '%' "
						+ SQL_ESC_STR);
				pjtWhere_ex.append("\n AND LOWER(PJT.GSI_PROJ_CODE) LIKE '%' || LOWER('" + gsiProjCodeArr[i]
						+ "') || '%' " + SQL_ESC_STR);
			}
		}

		// oppo_number
		if (o_oppo_number != null) {
			pjtSelect.append("\n,PJT.oppo_number OPPO_NUMBER");
			selectedItem.add("OPPO_NUMBER");
		}
		if (c_oppo_number != null && !c_oppo_number.equals("")) {
			// 全角空白は半角空白に置換する
			c_oppo_number = c_oppo_number.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String oppoNumberArr[] = c_oppo_number.split("\\s");
			for (int i = 0; i < oppoNumberArr.length; i++) {
				pjtWhere.append("\n AND LOWER(PJT.OPPO_NUMBER) LIKE '%' || LOWER('" + oppoNumberArr[i] + "') || '%' "
						+ SQL_ESC_STR);
				pjtWhere_ex.append("\n AND LOWER(PJT.OPPO_NUMBER) LIKE '%' || LOWER('" + oppoNumberArr[i] + "') || '%' "
						+ SQL_ESC_STR);
			}
		}

		// プロジェクト名(プロジェクト名はソートで使うので取得)
		pjtSelect.append("\n,PJT.proj_name PROJ_NAME");
		if (o_proj_name != null) {
			selectedItem.add("PROJ_NAME");
		}
		if (c_proj_name != null && !c_proj_name.equals("")) {
			// 全角空白は半角空白に置換する
			c_proj_name = c_proj_name.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String projNameArr[] = c_proj_name.split("\\s");
			for (int i = 0; i < projNameArr.length; i++) {
				pjtWhere.append("\n AND LOWER(PJT.PROJ_NAME) LIKE '%' || LOWER('" + projNameArr[i] + "') || '%' "
						+ SQL_ESC_STR);
				pjtWhere_ex.append("\n AND LOWER(PJT.PROJ_NAME) LIKE '%' || LOWER('" + projNameArr[i] + "') || '%' "
						+ SQL_ESC_STR);
			}
		}

		// エンドユーザ(エンドユーザはソートで使うので取得)
		pjtSelect.append("\n,PJT.end_user_name END_USER_NAME");
		if (o_end_user_name != null) {
			selectedItem.add("END_USER_NAME");
		}
		if (o_eu_official_name != null) {
			pjtSelect.append("\n,PJT.EU_OFFICIAL_NAME EU_OFFICIAL_NAME");
			selectedItem.add("EU_OFFICIAL_NAME");
		}
		if (c_end_user_name != null && !c_end_user_name.equals("")) {
			// 全角空白は半角空白に置換する
			c_proj_name = c_end_user_name.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String endUserNameArr[] = c_end_user_name.split("\\s");
			for (int i = 0; i < endUserNameArr.length; i++) {
				pjtWhere.append("\n AND LOWER(PJT.END_USER_NAME) LIKE '%' || LOWER('" + endUserNameArr[i] + "') || '%' "
						+ SQL_ESC_STR);
				pjtWhere_ex.append("\n AND LOWER(PJT.END_USER_NAME) LIKE '%' || LOWER('" + endUserNameArr[i]
						+ "') || '%' " + SQL_ESC_STR);
			}
		}
		// KEYアカウント,TLアカウント
		if (o_key_acnt != null) {
			selectedItem.add("KEY_ACNT");
			selectedItem.add("TL_ACNT");
			pjtSelect.append(
					"\n,DECODE(PJT.KEY_ACNT,0,NULL,1,'Yes',PJT.KEY_ACNT) KEY_ACNT, DECODE(PJT.TL_ACNT,0,NULL,1,'Yes',PJT.TL_ACNT) TL_ACNT ");
		}

		// 契約先社名(契約先はソートで使うので取得)
		pjtSelect.append("\n,PJT.client_name CLIENT_NAME");
		if (o_client_name != null) {
			selectedItem.add("CLIENT_NAME");
		}
		if (c_client_name != null && !c_client_name.equals("")) {
			// 全角空白は半角空白に置換する
			c_client_name = c_client_name.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String clientNameArr[] = c_client_name.split("\\s");
			for (int i = 0; i < clientNameArr.length; i++) {
				pjtWhere.append("\n AND LOWER(PJT.CLIENT_NAME) LIKE '%' || LOWER('" + clientNameArr[i] + "') || '%' "
						+ SQL_ESC_STR);
				pjtWhere_ex.append("\n AND LOWER(PJT.CLIENT_NAME) LIKE '%' || LOWER('" + clientNameArr[i] + "') || '%' "
						+ SQL_ESC_STR);
			}
		}

		// 担当アドミ
		if (o_admi != null) {
			pjtSelect.append("\n,PJT.ADMI");
			selectedItem.add("ADMI");
		}
		if (admiIsnull != null && "on".equals(admiIsnull)) {
			// 担当アドミがNULLのもののみ検索
			pjtWhere.append("\n AND PJT.ADMI IS NULL ");
			pjtWhere_ex.append("\n AND PJT.ADMI IS NULL ");
		} else if (c_admi != null && !c_admi.equals("")) {
			// 全角空白は半角空白に置換する
			c_admi = c_admi.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String admiArr[] = c_admi.split("\\s");
			for (int i = 0; i < admiArr.length; i++) {
				pjtWhere.append("\n AND LOWER(PJT.ADMI) LIKE '%' || LOWER('" + setFormatQuot(admiArr[i]) + "') || '%' "
						+ SQL_ESC_STR);
				pjtWhere_ex.append("\n AND LOWER(PJT.ADMI) LIKE '%' || LOWER('" + setFormatQuot(admiArr[i])
						+ "') || '%' " + SQL_ESC_STR);
			}
		}
		// SKUフラグ
		if (skuOnly != null && "on".equals(skuOnly)) {
			// class_codeにSKUが含まれるもののみ表示
			pjtWhere.append("\n AND PJT.CLASS_CODE like '%SKU%' ");
			pjtWhere_ex.append("\n AND PJT.CLASS_CODE like '%SKU%' ");
		}

		// FPAフラグ
		if (o_fpa != null) {
			pjtSelect.append("\n,PJT.FPAUTO FPAUTO");
			selectedItem.add("FPAUTO");
		}

		// 契約金額(契約金額はソートで使うので取得)
		pjtSelect.append("\n,PJT.cont_amount CONTRACT_AMOUNT");
		if (o_cont_amount != null) {
			selectedItem.add("CONTRACT_AMOUNT");
			selectedItem.add("KJZ");
		}
		if (o_own_cont_amount != null) {
			selectedItem.add("OWNCONTRACT_AMOUNT");
			selectedItem.add("KJZ");
		}

		if (!getParameter(request, "c_history_id", "").equals("")
				&& !getParameter(request, "forecastReport_flg", "").equals("yes")) {
			contAmntSelect.append(
					"(WITH P AS (SELECT PROJ_CODE,CONT_AMOUNT,START_DATE FROM PROJECT_LIST_HISTORY_V PT,FY_TERM FT");
			contAmntSelect.append("\n WHERE FT.FY = '20' || SUBSTR(PT.CONTRACT_QUARTER,3,2)");
			contAmntSelect.append("\n AND FT.QUARTER = SUBSTR(PT.CONTRACT_QUARTER,6)");
			contAmntSelect.append("\n AND HISTORY_ID = '");
			contAmntSelect.append(c_history_id);
			contAmntSelect.append("')");
			contAmntSelect.append("\n SELECT P.PROJ_CODE, P.CONT_AMOUNT, PO.CC_ORG_ID, PO.PROD_ORG_ID ");
			contAmntSelect.append("\n FROM P,PROJ_ORG_LIST_HISTORY PO ");
			contAmntSelect.append("\n WHERE P.PROJ_CODE = PO.PROJ_CODE ");
			if (BASE_HISTORY_ID.compareTo(c_history_id) >= 0) {
				// unit_id時代のやつ
				contAmntSelect
						.append("\n AND PO.HISTORY_ID = (SELECT MIN(HISTORY_ID) FROM HISTORY_LIST WHERE HISTORY_ID > '"
								+ BASE_HISTORY_ID + "' )");
			} else {
				// proj_org_list時代のやつ
				contAmntSelect.append("\n AND PO.HISTORY_ID = '" + c_history_id + "' ");
			}
			contAmntSelect.append(
					"\n AND   P.START_DATE BETWEEN PO.START_DATE AND NVL(END_DATE,TO_DATE('2999/12/31','YYYY/MM/DD')) ");
			contAmntSelect.append(") CONTORG");
		} else {
			contAmntSelect.append("(WITH P AS (SELECT PT.*,START_DATE,END_DATE");
			contAmntSelect.append("\n            FROM PROJECT_LIST_V PT,FY_TERM FT");
			contAmntSelect.append(
					"\n            WHERE FT.FY = '20' || SUBSTR(PT.CONTRACT_QUARTER,3,2) AND FT.QUARTER = SUBSTR(PT.CONTRACT_QUARTER,6))");
			contAmntSelect.append("\n SELECT P.PROJ_CODE, P.CONT_AMOUNT, PO.CC_ORG_ID, PO.PROD_ORG_ID ");
			contAmntSelect.append("\n FROM P, PROJ_ORG_LIST PO ");
			contAmntSelect.append("\n WHERE P.PROJ_CODE = PO.PROJ_CODE ");
			contAmntSelect.append(
					"\n AND   P.START_DATE BETWEEN PO.START_DATE AND NVL(PO.END_DATE,TO_DATE('2999/12/31','YYYY/MM/DD')) ");
			contAmntSelect.append(") CONTORG");
		}

		// 計上残(work_status = 2の時のみ)
		// 契約金額と計上残は常に一緒に表示に変更
		kjzSelect.append("\n (select inp.proj_code proj_code, inp.cont_amount "
				+ "\n ,inp.cont_amount - (sum(nvl(F.forecast,0))*1000000) kjz ");

		if (!getParameter(request, "c_history_id", "").equals("")
				&& !getParameter(request, "forecastReport_flg", "").equals("yes")) {
			kjzSelect.append(
					"\n from( select * from project_list_history_v where history_id = '" + c_history_id + "') inp");
		} else {
			kjzSelect.append("\n from project_list_v inp");
		}
		// 履歴対応
		if ((c_history_id == null) || (c_history_id.equals(""))) {
			kjzSelect.append("\n,forecast F");
		} else {
			kjzSelect.append("\n,(select * from forecast_history where history_id = '" + c_history_id + "') F");
		}
		kjzSelect.append("\n   where inp.work_status = 2 " + "\n  and inp.proj_code = f.proj_code(+) "
				+ "\n  group by inp.proj_code,inp.cont_amount " + "\n ) KJZ");

		// 契約形態(契約形態はソートで使うので取得)
		pjtSelect.append("\n,PJT.CONTRACT_ID, CONTRACT.SORT_NUMBER CONT_SORT_NUMBER");
		pjtFrom.append("\n,contract_list CONTRACT");
		pjtFrom_ex.append("\n,contract_list CONTRACT");
		pjtWhere.append("\n and PJT.contract_id = CONTRACT.contract_id(+)");
		pjtWhere_ex.append("\n and PJT.contract_id = CONTRACT.contract_id(+)");
		if (o_contract_type != null) {
			pjtSelect.append("\n,CONTRACT.contract_name CONTRACT_TYPE");
			selectedItem.add("CONTRACT_TYPE");
		}

		if ((c_contract_id != null) && (!c_contract_id.equals(""))) {
			pjtWhere.append("\n and PJT.contract_id = " + c_contract_id);
			pjtWhere_ex.append("\n and PJT.contract_id = " + c_contract_id);
		}

		// POPM
		if (o_proj_owner_name != null && o_proj_mgr_name != null) {
			// 両方チェック
			pjtSelect.append("\n,PO.emp_name||'/'||PM.emp_name PO_PM");
			selectedItem.add("PO_PM");
		} else if (o_proj_owner_name != null) {
			// POのみチェック
			pjtSelect.append("\n,PO.emp_name PO_PM");
			selectedItem.add("PO_PM");
		} else if (o_proj_mgr_name != null) {
			// PMのみチェック
			pjtSelect.append("\n,PM.emp_name PO_PM");
			selectedItem.add("PO_PM");
		}
		// PO(POはソートで使うので取得)
		pjtSelect.append(",PJT.PROJ_OWNER_ID ");
		if (o_proj_owner_name != null) {
			pjtFrom.append("\n,emp_list PO");
			pjtFrom_ex.append("\n,emp_list PO");
			pjtWhere.append("\n and PJT.proj_owner_id = PO.emp_id(+)");
			pjtWhere_ex.append("\n and PJT.proj_owner_id = PO.emp_id(+)");
		}
		if ((c_proj_owner_name != null) && (!c_proj_owner_name.equals(""))) {

			// 全角空白は半角空白に置換する
			c_proj_owner_name = c_proj_owner_name.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String poNameArr[] = c_proj_owner_name.split("\\s");
			for (int i = 0; i < poNameArr.length; i++) {
				pjtWhere.append("\n AND (PJT.PROJ_OWNER_ID IN (SELECT EMP_ID FROM EMP_LIST INE WHERE ");
				pjtWhere.append(
						"\n  (LOWER(INE.EMP_NAME) LIKE '%' || LOWER('" + poNameArr[i] + "') || '%' " + SQL_ESC_STR);
				pjtWhere.append("\n OR LOWER(INE.EMP_NAME_KANA) LIKE '%' || LOWER('" + poNameArr[i] + "') || '%' "
						+ SQL_ESC_STR + ")))");
				pjtWhere_ex.append("\n AND (PJT.PROJ_OWNER_ID IN (SELECT EMP_ID FROM EMP_LIST INE WHERE ");
				pjtWhere_ex.append(
						"\n  (LOWER(INE.EMP_NAME) LIKE '%' || LOWER('" + poNameArr[i] + "') || '%' " + SQL_ESC_STR);
				pjtWhere_ex.append("\n OR LOWER(INE.EMP_NAME_KANA) LIKE '%' || LOWER('" + poNameArr[i] + "') || '%' "
						+ SQL_ESC_STR + ")))");
			}
		}

		// PM(POはソートで使うので取得)
		pjtSelect.append(",PJT.PROJ_MGR_ID ");
		if (o_proj_mgr_name != null) {
			pjtFrom.append("\n,emp_list PM");
			pjtFrom_ex.append("\n,emp_list PM");
			pjtWhere.append("\n and PJT.proj_mgr_id = PM.emp_id(+)");
			pjtWhere_ex.append("\n and PJT.proj_mgr_id = PM.emp_id(+)");
		}
		if ((c_proj_mgr_name != null) && (!c_proj_mgr_name.equals(""))) {
			// 全角空白は半角空白に置換する
			c_proj_mgr_name = c_proj_mgr_name.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String pmNameArr[] = c_proj_mgr_name.split("\\s");
			for (int i = 0; i < pmNameArr.length; i++) {
				pjtWhere.append("\n AND (PJT.PROJ_MGR_ID IN (SELECT EMP_ID FROM EMP_LIST INE WHERE ");
				pjtWhere.append(
						"\n  (LOWER(INE.EMP_NAME) LIKE '%' || LOWER('" + pmNameArr[i] + "') || '%' " + SQL_ESC_STR);
				pjtWhere.append("\n OR LOWER(INE.EMP_NAME_KANA) LIKE '%' || LOWER('" + pmNameArr[i] + "') || '%' "
						+ SQL_ESC_STR + ")))");
				pjtWhere_ex.append("\n AND (PJT.PROJ_MGR_ID IN (SELECT EMP_ID FROM EMP_LIST INE WHERE ");
				pjtWhere_ex.append(
						"\n  (LOWER(INE.EMP_NAME) LIKE '%' || LOWER('" + pmNameArr[i] + "') || '%' " + SQL_ESC_STR);
				pjtWhere_ex.append("\n OR LOWER(INE.EMP_NAME_KANA) LIKE '%' || LOWER('" + pmNameArr[i] + "') || '%' "
						+ SQL_ESC_STR + ")))");
			}
		}

		// BOOKステータス(BOOKStatusはソートで使うので取得)
		pjtSelect.append("\n,PJT.BOOK_STATUS BOOK_STATUS_ID");
		if (o_book_status != null) {
			pjtSelect.append("\n,BST.STATUS_NAME BOOK_STATUS");
			pjtFrom.append("\n,fcst_status_list BST");
			pjtFrom_ex.append("\n,fcst_status_list BST");
			pjtWhere.append("\n and PJT.book_status = BST.status_id(+)");
			pjtWhere_ex.append("\n and PJT.book_status = BST.status_id(+)");
			selectedItem.add("BOOK_STATUS");
		}

		// REVステータス(REVStatusはソートで使うので取得)
		pjtSelect.append("\n,PJT.REV_STATUS REV_STATUS_ID, PJT.NO_UPD_REV_STATUS");
		if (o_rev_status != null) {
			pjtSelect.append("\n,RST.STATUS_NAME REV_STATUS ");
			pjtFrom.append("\n,fcst_status_list RST");
			pjtFrom_ex.append("\n,fcst_status_list RST");
			pjtWhere.append("\n and PJT.rev_status = RST.status_id(+)");
			pjtWhere_ex.append("\n and PJT.rev_status = RST.status_id(+)");
			selectedItem.add("REV_STATUS");
		}

		// Vに変更一覧表示する場合 履歴プルダウン選択 && ForecastMeeting画面でない && Vに変更一覧チェックあり
		if (!getParameter(request, "c_history_id", "").equals("")
				&& !getParameter(request, "forecastReport_flg", "").equals("yes")
				&& "on".equals(getParameter(request, "to_v_list", ""))) {

			pjtFrom.append("\n, PROJECT_LIST_V NOWP ");
			pjtFrom_ex.append("\n, PROJECT_LIST_V NOWP ");
			pjtWhere.append(
					"\n AND PJT.PROJ_CODE = NOWP.PROJ_CODE AND PJT.BOOK_STATUS != 99 AND NOWP.BOOK_STATUS = 99 ");
			pjtWhere_ex.append(
					"\n AND PJT.PROJ_CODE = NOWP.PROJ_CODE AND PJT.BOOK_STATUS != 99 AND NOWP.BOOK_STATUS = 99 ");

		} else
		// フォーキャストステータス検索か、WinProb検索か
		if ("2".equals(getParameter(request, "c_fstatus_winprob", ""))) {
			// WinProb%
			String winProbCond = getWinProb(request);
			if (!winProbCond.equals("")) {
				pjtWhere.append("\n and pjt.win_prob in (" + winProbCond + ")");
				pjtWhere_ex.append("\n and pjt.win_prob in (" + winProbCond + ")");
			}
		} else if ("0".equals(getParameter(request, "c_fstatus_winprob", ""))) {
			// BOOKステータス指定
			String projStatusCond = getProjStatus(request, "book");
			if (!projStatusCond.equals("")) {
				pjtWhere.append("\n and PJT.book_status in (" + projStatusCond + ")");
				pjtWhere_ex.append("\n and PJT.book_status in (" + projStatusCond + ")");
			}
		} else {
			// REVステータス指定
			String projStatusCond = getProjStatus(request, "rev");
			if (!projStatusCond.equals("")) {
				pjtWhere.append("\n and PJT.rev_status in (" + projStatusCond + ")");
				pjtWhere_ex.append("\n and PJT.rev_status in (" + projStatusCond + ")");
			}
		}

		// ワークステータス
		if (o_work_status != null) {
			pjtSelect.append("\n,WORK_STATUS.s_code WORK_STATUS");
			pjtFrom.append("\n,work_status_list WORK_STATUS");
			pjtFrom_ex.append("\n,work_status_list WORK_STATUS");
			pjtWhere.append("\n and PJT.work_status = WORK_STATUS.status_id(+)");
			pjtWhere_ex.append("\n and PJT.work_status = WORK_STATUS.status_id(+)");
			selectedItem.add("WORK_STATUS");
		}
		if ((c_work_status != null) && (!c_work_status.equals(""))) {
			pjtWhere.append("\n and PJT.work_status = " + c_work_status);
			pjtWhere_ex.append("\n and PJT.work_status = " + c_work_status);
		}

		// 契約未締結かどうか調べるため
		pjtSelect.append("\n,PJT.work_status WORK_STATUS_ID");

		// プロジェクト期間開始(開始日はソートで使うので取得)
		pjtSelect.append("\n,to_char(PJT.proj_start,'yyyy/mm/dd') PROJ_START");
		if (o_proj_start != null) {
			selectedItem.add("PROJ_START");
		}
		// プロジェクト期間終了(終了日はソートで使うので取得)
		pjtSelect.append("\n,to_char(PJT.proj_end,'yyyy/mm/dd') PROJ_END");
		if (o_proj_end != null) {
			selectedItem.add("PROJ_END");
		}

		// Classification
		if (o_classification != null) {
			pjtSelect.append("\n,CLS.classification CLASSIFICATION");
			selectedItem.add("CLASSIFICATION");
		}

		// from
		pjtFrom.append("\n,(select SYS_OUTLINE.proj_code PROJ_CODE,CLS_DESC.DESC_ID,CLS_DESC.description CLASSIFICATION"
				+ " from gbl_class_description CLS_DESC,gbl_class_category CLS_CATEGORY,gbl_system_outline SYS_OUTLINE");

		pjtFrom.append(
				" where SYS_OUTLINE.category_id = CLS_DESC.category_id" + " and SYS_OUTLINE.desc_id = CLS_DESC.desc_id"
						+ " and SYS_OUTLINE.category_id = CLS_CATEGORY.category_id"
						+ " and CLS_CATEGORY.category = 'CLASSIFICATION') CLS");

		// from_ex
		pjtFrom_ex.append("\n,(select SYS_OUTLINE.proj_code PROJ_CODE,CLS_DESC.description CLASSIFICATION"
				+ " from gbl_class_description CLS_DESC,gbl_class_category CLS_CATEGORY,gbl_system_outline SYS_OUTLINE");
		pjtFrom_ex.append(
				" where SYS_OUTLINE.category_id = CLS_DESC.category_id" + " and SYS_OUTLINE.desc_id = CLS_DESC.desc_id"
						+ " and SYS_OUTLINE.category_id = CLS_CATEGORY.category_id"
						+ " and CLS_CATEGORY.category = 'CLASSIFICATION') CLS");
		pjtWhere.append("\n and PJT.proj_code = CLS.proj_code(+)");
		pjtWhere_ex.append("\n and PJT.proj_code = CLS.proj_code(+)");

		if ((c_classification != null) && (!c_classification.equals(""))) {
			pjtFrom.append("\n,gbl_system_outline SYS_OUTLINE2");
			pjtFrom_ex.append("\n,gbl_system_outline SYS_OUTLINE2");
			pjtWhere.append("\n and PJT.proj_code = SYS_OUTLINE2.proj_code(+)");
			pjtWhere.append("\n and SYS_OUTLINE2.category_id = 8");
			pjtWhere.append("\n and SYS_OUTLINE2.desc_id = " + c_classification);
			pjtWhere_ex.append("\n and PJT.proj_code = SYS_OUTLINE2.proj_code(+)");
			pjtWhere_ex.append("\n and SYS_OUTLINE2.category_id = 8");
			pjtWhere_ex.append("\n and SYS_OUTLINE2.desc_id = " + c_classification);
		}

		// BOOKING管理組織
		if (o_booking_org != null) {
			pjtSelect.append("\n,PJT.BOOKING_ORG BOOKING_ORG");
			selectedItem.add("BOOKING_ORG");
		}

		if (c_booking_org != null && !c_booking_org.equals("")) {
			pjtWhere.append("\n and PJT.BOOKING_ORG = '" + c_booking_org + "'");
			pjtWhere_ex.append("\n and PJT.BOOKING_ORG = '" + c_booking_org + "'");
		}

		// Classification_4
		if (o_classification_4 != null) {
			pjtSelect.append("\n,PJT.Classification_4 CLASSIFICATION_4");
			selectedItem.add("CLASSIFICATION_4");
		}

		// 案件総額
		if (o_project_total != null) {
			pjtSelect.append("\n,PJT.PROJECT_TOTAL PROJECT_TOTAL");
			selectedItem.add("PROJECT_TOTAL");
		}

		// SA
		if (o_sa_info != null) {
			pjtSelect.append("\n,PJT.SA_INFO SA_INFO");
			selectedItem.add("SA_INFO");
		}

		// サービス分類（Tech）
		if (o_service_classification != null) {
			pjtFrom.append("\n, SERVICE_CLASSIFICATION SCL");
			pjtWhere.append("\n and PJT.SERVICE_CLASSIFICATION = SCL.ID(+)");
			pjtSelect.append("\n,NVL(SCL.SERVICE_NAME,pjt.SERVICE_PORTFOLIO) SERVICE_CLASSIFICATION");
			selectedItem.add("SERVICE_CLASSIFICATION");
		}

		if (o_sum_budget != null) {
			pjtSelect.append("\n,BUDGET.sum_budget SUM_BUDGET");
			pjtFrom.append(
					"\n,(select gsi_proj_code,sum(nvl(application_amount,0)) sum_budget from bis_budget where application_status != 'Cancelled' and application_status != 'Rejected' and del_flg = 0 group by gsi_proj_code) BUDGET");
			pjtWhere.append("\n and BUDGET.gsi_proj_code(+) = PJT.gsi_proj_code");
			selectedItem.add("SUM_BUDGET");

		}

		// SALES
		if (c_sales != null && !c_sales.equals("")) {
			// 全角空白は半角空白に置換する
			c_sales = c_sales.replace(FULL_SPC, HALF_SPC);
			// 空白区切りの場合、区切って条件に付加
			String salesArr[] = c_sales.split("\\s");
			for (int i = 0; i < salesArr.length; i++) {
				pjtWhere.append("\n AND LOWER(PJT.CONSUL_SALES) LIKE '%' || LOWER('" + salesArr[i] + "') || '%' "
						+ SQL_ESC_STR);
				pjtWhere_ex.append("\n AND LOWER(PJT.CONSUL_SALES) LIKE '%' || LOWER('" + salesArr[i] + "') || '%' "
						+ SQL_ESC_STR);
			}
		}
		if (o_sales != null) {
			pjtSelect.append("\n,pjt.consul_sales SALES");
			selectedItem.add("SALES");
		}

		// BID
		if (o_bid != null) {
			pjtSelect.append("\n,pjt.bid");
			selectedItem.add("BID");
		}

		// WIN_PROB(WIN_PROBはソートで使うので取得)
		pjtSelect.append("\n,pjt.win_prob WIN_PROB");
		if (o_win_prob != null) {
			selectedItem.add("WIN_PROB");
		}
		// 契約締結予定日
		if (o_contract_date != null) {
			pjtSelect.append("\n,to_char(PJT.contract_date,'YYYY/MM/DD') CONTRACT_DATE");
			selectedItem.add("CONTRACT_DATE");
		}
		// 締結QTR
		if (o_contract_quarter != null) {
			pjtSelect.append("\n,PJT.contract_quarter CONTRACT_QUARTER");
			selectedItem.add("CONTRACT_QUARTER");
		}
		// 案件登録日
		if (o_opty_creation_date != null) {
			pjtSelect.append("\n,TO_CHAR(PJT.OPTY_CREATION_DATE, 'YYYY/MM/DD') OPTY_CREATION_DATE");
			selectedItem.add("OPTY_CREATION_DATE");
		}
		// OppoStatus
		if (o_oppo_status != null) {
			pjtSelect.append("\n,pjt.status OPPO_STATUS");
			selectedItem.add("OPPO_STATUS");
		}
		// Progress
		if (o_progress != null) {
			pjtSelect.append("\n,pjt.progress PROGRESS");
			selectedItem.add("PROGRESS");
		}
		// Module
		if (o_module != null) {
			pjtSelect.append("\n,pjt.module MODULE");
			selectedItem.add("MODULE");
		}
		// ProductName
		if (o_product_name != null) {
			pjtSelect.append("\n,pjt.product_name PRODUCT_NAME");
			selectedItem.add("PRODUCT_NAME");
		}
		// インダストリ
		if (o_industry != null) {
			// インダストリは一時的にTechのみの運用
			pjtSelect.append(
					"\n,(case when pjt.unit_id in (select cc.org_id from cost_center_list cc where cc.level3 = 'CC00005031') then pjt.industry else null end) INDUSTRY");
			selectedItem.add("INDUSTRY");
		}

		// 分類
		pjtSelect.append("\n, ob.sort_number bunrui_sort_number");
		if (o_bunrui != null) {
			pjtSelect.append("\n,pjt.bunrui_name BUNRUI");
			selectedItem.add("BUNRUI");
		}
		if (c_bunrui_code != null && !c_bunrui_code.equals("")) {
			pjtWhere.append("\n and pjt.bunrui_code = " + c_bunrui_code);
			pjtWhere_ex.append("\n and pjt.bunrui_code = " + c_bunrui_code);
		}

		if (o_consul_type != null) {
			pjtSelect.append("\n,pjt.consulting_type CONSULTING_TYPE");
			selectedItem.add("CONSULTING_TYPE");
		}
		if (o_revenue_type != null) {
			pjtSelect.append("\n,pjt.revenue_type REVENUE_TYPE");
			selectedItem.add("REVENUE_TYPE");
		}

		// Booking集計対象外
		if (o_not_book_sum != null) {
			pjtSelect.append("\n,pjt.not_book_sum NOT_BOOK_SUM");
			selectedItem.add("NOT_BOOK_SUM");
		}

		// LIC. OPPO ID
		if (o_lic_oppo_id != null) {
			pjtSelect.append("\n,pjt.lic_oppo_id LIC_OPPO_ID");
			selectedItem.add("LIC_OPPO_ID");
		}

		// CONTRACT NUMBER
		if (o_contract_number != null) {
			pjtSelect.append("\n,pjt.contract_number CONTRACT_NUMBER");
			selectedItem.add("CONTRACT_NUMBER");
		}
		// SKU型番
		if (o_sku_no != null) {
			pjtSelect.append("\n,pjt.sku_no SKU_NO");
			selectedItem.add("SKU_NO");
		}
		// SKUサービス期間
		if (o_sku_span != null) {
			pjtSelect.append("\n,pjt.sku_span SKU_SPAN");
			selectedItem.add("SKU_SPAN");
		}
		// SKU RevRec
		if (o_sku_rev_rec_rule != null) {
			pjtSelect.append("\n,pjt.sku_rev_rec_rule SKU_REV_REC_RULE");
			selectedItem.add("SKU_REV_REC_RULE");
		}

		// 20190816 zhangt SIの項目「Campaign Name」を取り込みForecastに追加 start
		// Campaign Name
		if (o_campaign_name != null) {
			pjtSelect.append("\n,pjt.campaign_name CAMPAIGN_NAME");
			selectedItem.add("CAMPAIGN_NAME");
		}
		// 20190816 zhangt SIの項目「Campaign Name」を取り込みForecastに追加 end

		// ConsCategory
		if (c_category_id != null && !c_category_id.equals("")) {
			pjtWhere.append("\n and pjt.cons_category = '" + c_category_id + "'");
			pjtWhere_ex.append("\n and pjt.cons_category= '" + c_category_id + "'");
		}

		if (o_cons_category != null) {
			pjtSelect.append("\n,pjt.cons_category CONS_CATEGORY");
			selectedItem.add("CONS_CATEGORY");
		}

		// 業務委託
		if (o_ex_cont != null) {
			// 業務委託ID
			pjtSelect_ex.append("\n,EX_CONT.ex_cont_id EX_CONT_ID");
			pjtWhere_ex.append("\n AND PJT.PROJ_CODE = EX_CONT.PROJ_CODE");
			pjtGroupby_ex.append("\n,EX_CONT.ex_cont_id");

			// 業務委託用プロジェクトコード
			pjtSelect_ex.append("\n,EX_CONT.proj_code PROJ_CODE");
			pjtGroupby_ex.append("\n,EX_CONT.proj_code");
			pjtSelect_ex.append("\n,EX_CONT.proj_code CODE");
			pjtGroupby_ex.append("\n,EX_CONT.code");

			if (o_org_name != null) {
				pjtSelect_ex.append(",ORG.CC_ORG_ID EX_CC_ORG_ID");
			}
			if (o_prd_org_name != null) {
				pjtSelect_ex.append(",ORG.PROD_ORG_ID EX_PROD_ORG_ID");
			}
			// 業務委託先名
			pjtSelect_ex.append("\n,EX_CONT.ex_cont_name EX_CONT_NAME");
			pjtGroupby_ex.append("\n,EX_CONT.ex_cont_name");

			// Ref.No
			pjtSelect_ex.append("\n,EX_CONT.reference_no REFNO");
			pjtGroupby_ex.append("\n,EX_CONT.reference_no");

			// コメント
			pjtSelect_ex.append("\n,EX_CONT.comments COMMENTS");
			pjtGroupby_ex.append("\n,EX_CONT.comments");

			// PO番号
			pjtSelect_ex.append("\n,EX_CONT.PO_NUMBER PO_NUMBER");
			pjtGroupby_ex.append("\n,EX_CONT.PO_NUMBER");

			// 業務委託先契約形態
			pjtSelect_ex.append("\n,EX_CONT_TYPE.contract_name EX_CONT_TYPE");
			pjtGroupby_ex.append("\n,EX_CONT_TYPE.contract_name");

			pjtSelect_ex.append(
					"\n,(case when EX_CONT_TYPE.contract_id in(5,6,7,8) then 'OSSI' when EX_CONT_TYPE.contract_id in(9) then 'GlobalRsc' else 'CONT' end)  EX_CONT_TYPE_CLS");
			pjtGroupby_ex.append("\n,EX_CONT_TYPE.contract_id");

			pjtFrom_ex.append("\n,ex_contract_type_list EX_CONT_TYPE");
			pjtWhere_ex.append("\n and EX_CONT.contract_id = EX_CONT_TYPE.contract_id(+)");

			if (o_org_name != null) {
				pjtSelect_ex.append(", ORG.CC_ORG_ID");
				pjtGroupby_ex.append(", ORG.CC_ORG_ID ");
			}
			if (o_prd_org_name != null) {
				pjtSelect_ex.append(", ORG.PROD_ORG_ID ");
				pjtGroupby_ex.append(", ORG.PROD_ORG_ID ");
			}
		}

		// ReasonForChange
		if (o_reason_for_change != null) {
			// ReasonForChange
			pjtSelect.append("\n,nvl(prfc.reason_for_change,' - ') REASON_FOR_CHANGE");
			pjtSelect.append("\n,decode(prfc.reason_for_change,null,1,0) REASON_FOR_CHANGE_ISNULL");
			pjtSelect.append("\n,pjt.proj_code PROJ_CODE_REASON_FOR_CHANGE");

			if (!getParameter(request, "c_history_id", "").equals("")
					&& !getParameter(request, "forecastReport_flg", "").equals("yes")) {
				pjtFrom.append(" ,proj_reason_for_change_history prfc");
				pjtWhere.append(" and prfc.history_id(+) = '" + c_history_id + "'");
			} else {
				pjtFrom.append(" ,proj_reason_for_change prfc");
			}
			pjtWhere.append(" \n and pjt.proj_code = prfc.proj_code(+) ");
			selectedItem.add("REASON_FOR_CHANGE");

		}

		// ■■ Forecast
		StringBuffer fcstSelect = new StringBuffer();
		StringBuffer fcstSelect_ex = new StringBuffer();
		StringBuffer fcstFrom = new StringBuffer();
		StringBuffer fcstFrom_ex = new StringBuffer();
		StringBuffer fcstGroupby_ex = new StringBuffer();
		for (int cnt = 1; cnt < c_show_length + 1; cnt++) {
			// PROJECT用
			fcstSelect.append("\n,decode(F.N" + MM.format(cnt) + ",0,null,F.F" + MM.format(cnt) + ") FCST" + cnt);
			fcstSelect.append("\n,F.C" + MM.format(cnt) + " COMMIT_FLG" + cnt);
			fcstSelect.append("\n,F.A" + MM.format(cnt) + " ACTUAL_FLG" + cnt);

			// 業務委託用
			fcstSelect_ex.append("\n,decode(F.N" + MM.format(cnt) + ",0,null,F.F" + MM.format(cnt) + ") FCST" + cnt);
			fcstSelect_ex.append("\n,F.C" + MM.format(cnt) + " COMMIT_FLG" + cnt);
			fcstSelect_ex.append("\n,F.A" + MM.format(cnt) + " ACTUAL_FLG" + cnt);
			fcstGroupby_ex.append("\n,decode(F.N" + MM.format(cnt) + ",0,null,F.F" + MM.format(cnt) + ")");
			fcstGroupby_ex.append("\n,F.C" + MM.format(cnt));
			fcstGroupby_ex.append("\n,F.A" + MM.format(cnt));

			// ForecstReport履歴表示用
			if (!getParameter(request, "c_history_id", "").equals("")
					&& getParameter(request, "forecastReport_flg", "").equals("yes")) {
				// PROJECT用
				fcstSelect.append(
						"\n,decode(F_P.N" + MM.format(cnt) + ",0,null,F_P.F" + MM.format(cnt) + ") FCST_P" + cnt);
				fcstSelect.append("\n,F_P.C" + MM.format(cnt) + " COMMIT_FLG_P" + cnt);
				fcstSelect.append("\n,F_P.A" + MM.format(cnt) + " ACTUAL_FLG_P" + cnt);

				// 業務委託用
				fcstSelect_ex.append(
						"\n,decode(F_P.N" + MM.format(cnt) + ",0,null,F_P.F" + MM.format(cnt) + ") FCST_P" + cnt);
				fcstSelect_ex.append("\n,F_P.C" + MM.format(cnt) + " COMMIT_FLG_P" + cnt);
				fcstSelect_ex.append("\n,F_P.A" + MM.format(cnt) + " ACTUAL_FLG_P" + cnt);
				fcstGroupby_ex.append("\n,decode(F_P.N" + MM.format(cnt) + ",0,null,F_P.F" + MM.format(cnt) + ")");
				fcstGroupby_ex.append("\n,F_P.C" + MM.format(cnt));
				fcstGroupby_ex.append("\n,F_P.A" + MM.format(cnt));
			}
		}

		String table = "";
		String tableOrg = "";
		// (現在の値)
		if (((c_history_id == null) || (c_history_id.equals("")))
				|| (getParameter(request, "forecastReport_flg", "").equals("yes"))) {
			table = "forecast F";
			tableOrg = "proj_org_list porg";
		}
		// (Forecast一覧履歴対応)
		else {
			table = "(select * from forecast_history where history_id = '" + c_history_id + "') F";
			if (BASE_HISTORY_ID.compareTo(c_history_id) >= 0) {
				tableOrg = "(select * from proj_org_list_history where HISTORY_ID = (SELECT MIN(HISTORY_ID) FROM HISTORY_LIST WHERE HISTORY_ID > '"
						+ BASE_HISTORY_ID + "' )) PORG ";
			} else {
				tableOrg = "(select * from proj_org_list_history where history_id = '" + c_history_id + "') PORG";
			}
		}

		// 合計用
		fcstSelect.append(
				"\n,nvl(S.Q1,0) Q1,nvl(S.Q2,0) Q2,nvl(S.Q3,0) Q3,nvl(S.Q4,0) Q4,nvl(S.H1,0) H1,nvl(S.H2,0) H2,nvl(S.FY,0) FY,nvl(S.TOTAL,0) TOTAL");
		fcstSelect_ex.append(
				"\n,nvl(S.Q1,0) Q1,nvl(S.Q2,0) Q2,nvl(S.Q3,0) Q3,nvl(S.Q4,0) Q4,nvl(S.H1,0) H1,nvl(S.H2,0) H2,nvl(S.FY,0) FY,nvl(S.TOTAL,0) TOTAL");
		fcstGroupby_ex.append(
				"\n,nvl(S.Q1,0),nvl(S.Q2,0),nvl(S.Q3,0),nvl(S.Q4,0),nvl(S.H1,0),nvl(S.H2,0),nvl(S.FY,0),nvl(S.TOTAL,0) TOTAL");

		// PROJECT用
		// インデックスのために3,A,C,Tの頭文字を検索
		// なぜかよく分からんがA%を入れるとインデックスが掛からないのでFY02以前のみ入れる
		if (_fy <= 2002) {
			fcstFrom.append("\n,(" + getSQL_Forecast(request, table, tableOrg,
					"(F.proj_code like '3%' or F.proj_code like 'A%' or F.proj_code like 'C%' or F.proj_code like 'T%')",
					"FORECAST") + ") F");
		} else {
			fcstFrom.append("\n,("
					+ getSQL_Forecast(request, table, tableOrg,
							"(F.proj_code like '3%' or F.proj_code like 'C%' or F.proj_code like 'T%')", "FORECAST")
					+ ") F");
		}

		if (_fy <= 2002) {
			fcstFrom.append("\n,("
					+ getSQL_Sum(request, table, tableOrg,
							"(F.proj_code like '3%' or F.proj_code like 'A%' or F.proj_code like 'C%' or F.proj_code like 'T%')")
					+ ") S");
		} else {
			fcstFrom.append("\n,(" + getSQL_Sum(request, table, tableOrg,
					"(F.proj_code like '3%' or F.proj_code like 'C%' or F.proj_code like 'T%')") + ") S");
		}

		// 業務委託用
		// インデックスのためにEの頭文字を検索
		fcstFrom_ex
				.append("\n,(" + getSQL_Forecast(request, table, tableOrg, "F.proj_code like 'E%'", "EXCONT") + ") F");
		fcstFrom_ex.append("\n,(" + getSQL_SumEx(request, table, tableOrg, "F.proj_code like 'E%'  ") + ") S");

		// ForecstReport履歴表示用
		if (!getParameter(request, "c_history_id", "").equals("")
				&& getParameter(request, "forecastReport_flg", "").equals("yes")) {
			table = "(select * from forecast_history where history_id = '" + c_history_id + "') F";

			fcstSelect.append("\n,nvl(S_P.Q1,0) Q1_P,nvl(S_P.Q2,0) Q2_P,nvl(S_P.Q3,0) Q3_P,nvl(S_P.Q4,0) Q4_P");
			fcstSelect.append("\n,nvl(S_P.H1,0) H1_P,nvl(S_P.H2,0) H2_P,nvl(S_P.FY,0) FY_P,nvl(S_P.TOTAL,0) TOTAL_P");
			fcstSelect_ex.append("\n,nvl(S_P.Q1,0) Q1_P,nvl(S_P.Q2,0) Q2_P,nvl(S_P.Q3,0) Q3_P,nvl(S_P.Q4,0) Q4_P");
			fcstSelect_ex
					.append("\n,nvl(S_P.H1,0) H1_P,nvl(S_P.H2,0) H2_P,nvl(S_P.FY,0) FY_P,nvl(S_P.TOTAL,0) TOTAL_P");
			fcstGroupby_ex.append("\n,nvl(S_P.Q1,0),nvl(S_P.Q2,0),nvl(S_P.Q3,0),nvl(S_P.Q4,0)");
			fcstGroupby_ex.append("\n,nvl(S_P.H1,0),nvl(S_P.H2,0),nvl(S_P.FY,0),nvl(S_P.TOTAL,0)");

			// インデックスのために3,A,C,Tの頭文字を検索
			// なぜかよく分からんがA%を入れるとインデックスが掛からないのでFY02以前のみ入れる
			if (_fy <= 2002) {
				fcstFrom.append("\n,(" + getSQL_Forecast(request, table, tableOrg,
						"(F.proj_code like '3%' or F.proj_code like 'A%' or F.proj_code like 'C%' or F.proj_code like 'T%')",
						"FORECAST") + ") F_P");
			} else {
				fcstFrom.append("\n,("
						+ getSQL_Forecast(request, table, tableOrg,
								"(F.proj_code like '3%' or F.proj_code like 'C%' or F.proj_code like 'T%')", "FORECAST")
						+ ") F_P");
			}

			if (_fy <= 2002) {
				fcstFrom.append("\n,("
						+ getSQL_Sum(request, table, tableOrg,
								"(F.proj_code like '3%' or F.proj_code like 'A%' or F.proj_code like 'C%' or F.proj_code like 'T%')")
						+ ") S_P");
			} else {
				fcstFrom.append("\n,(" + getSQL_Sum(request, table, tableOrg,
						"(F.proj_code like '3%' or F.proj_code like 'C%' or F.proj_code like 'T%')") + ") S_P");
			}

			// インデックスのためにEの頭文字を検索
			fcstFrom_ex.append(
					"\n,(" + getSQL_Forecast(request, table, tableOrg, "F.proj_code like 'E%'", "EXCONT") + ") F_P");
			fcstFrom_ex.append("\n,(" + getSQL_SumEx(request, table, tableOrg, "F.proj_code like 'E%'") + ") S_P");
		}

		// ソート
		Hashtable sort = new Hashtable();
		sort.put("0", "NULL"); // MP組織
		sort.put("1", "NULL"); // PRJ管理組織
		if (o_org_name != null) {
			sort.put("0", "OPJT.CCSORT NULLS LAST "); // MP組織
		}
		if (o_prd_org_name != null) {
			sort.put("1", "OPJT.PDORG_SORT nulls last"); // PRJ管理組織
		}

		sort.put("2", "OPJT.book_status_id"); // Booking Status
		sort.put("3", "OPJT.rev_status_id"); // Revenue Status
		sort.put("4", "OPJT.WS_CODE"); // Work Status
		sort.put("5", "opjt.win_prob"); // WinProb
		sort.put("6", "OPJT.bunrui_sort_number nulls last "); // SubIndustry
		sort.put("7", "OPJT.proj_code"); // Temp Number
		sort.put("8", "OPJT.gsi_proj_code nulls last"); // Project Number
		sort.put("9", "OPJT.proj_name nulls last"); // Project Name
		sort.put("10", "OPJT.end_user_name nulls last"); // エンドユーザ
		sort.put("11", "OPJT.client_name nulls last"); // 契約先社名
		sort.put("12", "OPJT.PROJ_OWNER_ID nulls last"); // PO
		sort.put("13", "OPJT.PROJ_MGR_ID nulls last"); // PM
		sort.put("14", "CONTORG.cont_amount nulls last"); // 契約金額
		sort.put("15", "OPJT.CONT_SORT_NUMBER"); // 契約形態
		sort.put("16", "OPJT.contract_date"); // 契約締結日
		sort.put("17", "OPJT.proj_start nulls last"); // 開始日
		sort.put("18", "OPJT.proj_end nulls last"); // 終了日

		// ソートプルダウンの選択項目のID取得
		String sort1 = (String) sort.get(getParameter(request, "c_sort1_id"));
		String sort2 = (String) sort.get(getParameter(request, "c_sort2_id"));
		String sort3 = (String) sort.get(getParameter(request, "c_sort3_id"));

		orderby.append("\n " + sort1 + "\n," + sort2 + "\n," + sort3);

		orderby.append("\n,OPJT.proj_code");// 必ず最終的にはtemp numberでソート

		// プロジェクト用SQL作成
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT OPJT.* ,KJZ.KJZ, CONTORG.CONT_AMOUNT OWNCONTRACT_AMOUNT " + fcstSelect + "\n FROM (");
		sql.append(pjtSelect.toString());
		sql.append('\n');
		sql.append(pjtFrom.toString());
		sql.append('\n');
		sql.append(pjtWhere.toString());
		sql.append('\n');
		sql.append(" ) OPJT ");
		// 契約金額出す場合
		sql.append("," + contAmntSelect.toString());
		sql.append("," + kjzSelect.toString());

		sql.append(fcstFrom.toString());
		sql.append("\n WHERE OPJT.CODE = F.PROJ_CODE(+) ");
		sql.append("\n AND   OPJT.CODE = S.PROJ_CODE(+) ");
		sql.append("\n AND   OPJT.CODE = CONTORG.PROJ_CODE(+) ");
		sql.append("\n AND   OPJT.CODE = KJZ.PROJ_CODE(+) ");
		// レポートで履歴の場合
		if (!getParameter(request, "c_history_id", "").equals("")
				&& getParameter(request, "forecastReport_flg", "").equals("yes")) {
			sql.append("\n AND   OPJT.CODE = F_P.PROJ_CODE(+) ");
			sql.append("\n AND   OPJT.CODE = S_P.PROJ_CODE(+) ");
		}
		// 出力するものによって結合条件を変える
		if (o_org_name != null) {
			sql.append("\n AND OPJT.CC_ORG_ID = F.CC_ORG_ID(+) ");
			sql.append("\n AND OPJT.CC_ORG_ID = S.CC_ORG_ID(+) ");
			sql.append("\n AND OPJT.CC_ORG_ID = CONTORG.CC_ORG_ID(+) ");
			// レポートで履歴の場合
			if (!getParameter(request, "c_history_id", "").equals("")
					&& getParameter(request, "forecastReport_flg", "").equals("yes")) {
				sql.append("\n AND OPJT.CC_ORG_ID = F_P.CC_ORG_ID(+) ");
				sql.append("\n AND OPJT.CC_ORG_ID = S_P.CC_ORG_ID(+) ");
			}
		}
		if (o_prd_org_name != null) {
			sql.append("\n AND OPJT.PROD_ORG_ID = F.PROD_ORG_ID(+) ");
			sql.append("\n AND OPJT.PROD_ORG_ID = S.PROD_ORG_ID(+) ");
			sql.append("\n AND OPJT.PROD_ORG_ID = CONTORG.PROD_ORG_ID(+) ");
			// レポートで履歴の場合
			if (!getParameter(request, "c_history_id", "").equals("")
					&& getParameter(request, "forecastReport_flg", "").equals("yes")) {
				sql.append("\n AND OPJT.PROD_ORG_ID = F_P.PROD_ORG_ID(+) ");
				sql.append("\n AND OPJT.PROD_ORG_ID = S_P.PROD_ORG_ID(+) ");
			}
		}

		// ソート
		sql.append(orderby);
		querys.put("PROJECT", sql.toString());

		// 業務委託用SQL作成
		StringBuffer sql_ex = new StringBuffer();
		sql_ex.append("SELECT OPJT.* " + fcstSelect_ex + "\n FROM (");
		sql_ex.append(pjtSelect_ex.toString());
		sql_ex.append('\n');
		sql_ex.append(pjtFrom_ex.toString());
		sql_ex.append('\n');
		sql_ex.append(pjtWhere_ex.toString());
		sql_ex.append('\n');
		sql_ex.append(" ) OPJT ");
		sql_ex.append(fcstFrom_ex.toString());
		sql_ex.append("\n WHERE OPJT.EX_CONT_ID = F.PROJ_CODE(+) ");
		sql_ex.append("\n AND   OPJT.EX_CONT_ID = S.PROJ_CODE(+) ");
		// レポートで履歴の場合
		if (!getParameter(request, "c_history_id", "").equals("")
				&& getParameter(request, "forecastReport_flg", "").equals("yes")) {
			sql_ex.append("\n AND   OPJT.EX_CONT_ID = F_P.PROJ_CODE(+) ");
			sql_ex.append("\n AND   OPJT.EX_CONT_ID = S_P.PROJ_CODE(+) ");
		}
		// 出力するものによって結合条件を変える
		if (o_org_name != null) {
			sql_ex.append("\n AND OPJT.EX_CC_ORG_ID = F.CC_ORG_ID(+) ");
			sql_ex.append("\n AND OPJT.EX_CC_ORG_ID = S.CC_ORG_ID(+) ");
			// レポートで履歴の場合
			if (!getParameter(request, "c_history_id", "").equals("")
					&& getParameter(request, "forecastReport_flg", "").equals("yes")) {
				sql_ex.append("\n AND OPJT.EX_CC_ORG_ID = F_P.CC_ORG_ID(+) ");
				sql_ex.append("\n AND OPJT.EX_CC_ORG_ID = S_P.CC_ORG_ID(+) ");
			}
		}
		if (o_prd_org_name != null) {
			sql_ex.append("\n AND OPJT.EX_PROD_ORG_ID = F.PROD_ORG_ID(+) ");
			sql_ex.append("\n AND OPJT.EX_PROD_ORG_ID = S.PROD_ORG_ID(+) ");
			// レポートで履歴の場合
			if (!getParameter(request, "c_history_id", "").equals("")
					&& getParameter(request, "forecastReport_flg", "").equals("yes")) {
				sql_ex.append("\n AND OPJT.EX_PROD_ORG_ID = F_P.PROD_ORG_ID(+) ");
				sql_ex.append("\n AND OPJT.EX_PROD_ORG_ID = S_P.PROD_ORG_ID(+) ");
			}
		}
		// ソート
		sql_ex.append(orderby_ex);

		if (o_ex_cont != null) {
			querys.put("EX_CONT", sql_ex.toString());
		} else {
			querys.put("EX_CONT", "SELECT NULL FROM DUAL WHERE 1=2 ");
		}
		if (o_update_button != null) {
			selectedItem.add("BTN_CONT");
			selectedItem.add("BTN_FCST");
		}

		querys.put("selectedItem", selectedItem);

		return querys;
	}

	/**
	 * 画面でチェックされたフォーキャストステータスのコードをカンマ区切のStringにして返す。
	 *
	 * 画面からパラメータで渡されたフォーキャストステータスのcheckboxを取得して、
	 * チェックされていたもののコードをカンマ区切のStringにして返す。SQLで使用するために作成。
	 */
	private String getProjStatus(HttpServletRequest request, String fcstCategory) {
		StringBuffer projStatusStr = new StringBuffer("");
		Vector projStatusVec = new Vector();

		// チェックされたフォーキャストステータスを一度Vectorに入れる
		if (getParameter(request, "c_" + fcstCategory + "_status_check1") != null) {
			projStatusVec.add("1");
		}
		if (getParameter(request, "c_" + fcstCategory + "_status_check2") != null) {
			projStatusVec.add("2");
		}
		if (getParameter(request, "c_" + fcstCategory + "_status_check3") != null) {
			projStatusVec.add("3");
		}
		if (getParameter(request, "c_" + fcstCategory + "_status_check4") != null) {
			projStatusVec.add("4");
		}
		if (getParameter(request, "c_" + fcstCategory + "_status_check5") != null) {
			projStatusVec.add("5");
		}
		if (getParameter(request, "c_" + fcstCategory + "_status_check99") != null) {
			projStatusVec.add("99");
		}
		// Vectorに入っているものをカンマ区切のStringにする
		if (projStatusVec.size() > 0) {
			for (int i = 0; i < projStatusVec.size(); i++) {
				if (i > 0) {
					projStatusStr.append(",");
				}
				projStatusStr.append((String) projStatusVec.get(i));
			}
		}

		return projStatusStr.toString();
	}

	/**
	 * 画面でチェックされたWinProb%のコードをカンマ区切のStringにして返す。
	 *
	 * 画面からパラメータで渡されたWinProb%のcheckboxを取得して、
	 * チェックされていたもののコードをカンマ区切のStringにして返す。SQLで使用するために作成。
	 */
	private String getWinProb(HttpServletRequest request) {
		StringBuffer winProbStr = new StringBuffer("");
		Vector winProbVec = new Vector();

		// チェックされたWinProbステータスを一度Vectorに入れる
		if (getParameter(request, "c_win_prob_check0") != null) {
			winProbVec.add("0");
		}
		if (getParameter(request, "c_win_prob_check10") != null) {
			winProbVec.add("10");
		}
		if (getParameter(request, "c_win_prob_check20") != null) {
			winProbVec.add("20");
		}
		if (getParameter(request, "c_win_prob_check30") != null) {
			winProbVec.add("30");
		}
		if (getParameter(request, "c_win_prob_check40") != null) {
			winProbVec.add("40");
		}
		if (getParameter(request, "c_win_prob_check50") != null) {
			winProbVec.add("50");
		}
		if (getParameter(request, "c_win_prob_check60") != null) {
			winProbVec.add("60");
		}
		if (getParameter(request, "c_win_prob_check70") != null) {
			winProbVec.add("70");
		}
		if (getParameter(request, "c_win_prob_check80") != null) {
			winProbVec.add("80");
		}
		if (getParameter(request, "c_win_prob_check90") != null) {
			winProbVec.add("90");
		}
		if (getParameter(request, "c_win_prob_check100") != null) {
			winProbVec.add("100");
		}
		// Vectorに入っているものをカンマ区切のStringにする
		if (winProbVec.size() > 0) {
			for (int i = 0; i < winProbVec.size(); i++) {
				if (i > 0) {
					winProbStr.append(",");
				}
				winProbStr.append((String) winProbVec.get(i));
			}
		}

		return winProbStr.toString();
	}

	/**
	 * Condition
	 *
	 * コンサル期間が指定されたFYにかかっているという条件 契約締結日と終了日の遅いほうに1ヶ月足した年月と比べる
	 */
	private String getConsulPeriod(String c_fy) {
		int fy = Integer.parseInt(c_fy);
		String displayConsEnd = "/11/30";

		String ret = "\n and (nvl(PJT.cons_start,to_date('0001/01/01','yyyy/mm/dd'))" + " <= to_date('" + c_fy
				+ displayConsEnd + "','yyyy/mm/dd')" + "\n and  to_date('" + (fy - 1) + "/06/01','yyyy/mm/dd')"
				+ " <= add_months(nvl(PJT.cons_end,to_date('0001/01/01','yyyy/mm/dd')),1))";

		return ret;
	}

	/**
	 * Q1～FY用
	 *
	 * @conditionとはforecast.proj_codeの最初の文字(Projectの場合A,C,T,3,業務委託の場合E)を<br>
	 * 																		indexに引っ掛けるため指定している
	 */
	private String getSQL_Sum(HttpServletRequest request, String table, String tableOrg, String condition) {
		int fy = Integer.parseInt(request.getParameter("c_fy"));
		String o_org_name = request.getParameter("o_org_name");
		String o_prd_org_name = request.getParameter("o_prd_org_name");
		String c_org_id = request.getParameter("c_org_id");
		StringBuffer addColumnOrgIn = new StringBuffer();
		StringBuffer addColumnOrgOut = new StringBuffer();
		StringBuffer addConditionFrom = new StringBuffer();
		StringBuffer addConditionWhere = new StringBuffer();
		addColumnOrgIn.append(", PORG.CC_ORG_ID ");
		addColumnOrgIn.append(", PORG.PROD_ORG_ID ");

		if (o_org_name != null) {
			addColumnOrgOut.append(", FALL.CC_ORG_ID ");
		}
		if (o_prd_org_name != null) {
			addColumnOrgOut.append(", FALL.PROD_ORG_ID ");
		}
		if (c_org_id != null && !"".equals(c_org_id)) {
			// fy12のやつだったら
			if (getParameter(request, "fy12_flg") != null) {
				addConditionFrom.append(",PRODUCT_ORG_LIST PRD2");
				addConditionWhere.append("\n AND PORG.PROD_ORG_ID = PRD2.ORG_ID(+)"
						+ "\n AND DECODE((SELECT DISTINCT ORG_LEVEL FROM PRODUCT_ORG_LIST WHERE ORG_ID = '" + c_org_id
						+ "'" + getPrdOrgPeriod(fy, "")
						+ "\n ),1,PRD2.LEVEL1,2,PRD2.LEVEL2,3,PRD2.LEVEL3,4,PRD2.LEVEL4,5,PRD2.LEVEL5) = '" + c_org_id
						+ "'"
						+ "\n AND PRD2.ORG_START_DATE = (SELECT MAX(ORG_START_DATE) FROM PRODUCT_ORG_LIST WHERE ORG_ID = PRD2.ORG_ID"
						+ getPrdOrgPeriod(fy, "") + "\n )" + "\n AND PORG.PROD_ORG_ID = PRD2.ORG_ID ");
			} else {
				addConditionFrom.append(", cost_center_list CC2");
				addConditionWhere.append("\n and PORG.CC_ORG_ID = CC2.org_id(+)"
						+ "\n and DECODE((SELECT distinct org_level FROM cost_center_list where org_id = '" + c_org_id
						+ "'" + getOrgPeriod(fy, "")
						+ "\n ),1,CC2.level1,2,CC2.level2,3,CC2.level3,4,CC2.level4,5,CC2.level5) = '" + c_org_id + "'"
						+ "\n and CC2.org_start_date = (select max(org_start_date) from cost_center_list where org_id = CC2.org_id"
						+ getOrgPeriod2(fy, "") + "\n )");
			}
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select  FALL.proj_code " + addColumnOrgOut + "\n\t,SUM(case when fy.target_month between to_date('"
				+ fy + "/06/01','yyyy/mm/dd') and to_date('" + fy
				+ "/8/31','yyyy/mm/dd') then nvl(fy.fy,0) else 0 end) q1"
				+ "\n\t,SUM(case when fy.target_month between to_date('" + fy + "/09/01','yyyy/mm/dd') and to_date('"
				+ fy + "/11/30','yyyy/mm/dd') then nvl(fy.fy,0) else 0 end) q2"
				+ "\n\t,SUM(case when fy.target_month between to_date('" + fy + "/03/01','yyyy/mm/dd') and to_date('"
				+ fy + "/11/30','yyyy/mm/dd') then 0 else nvl(fy.fy,0) end) q3"
				+ "\n\t,SUM(case when fy.target_month between to_date('" + fy + "/03/01','yyyy/mm/dd') and to_date('"
				+ fy + "/05/31','yyyy/mm/dd') then nvl(fy.fy,0) else 0 end) q4"
				+ "\n\t,SUM(case when fy.target_month between to_date('" + fy + "/06/01','yyyy/mm/dd') and to_date('"
				+ fy + "/11/30','yyyy/mm/dd') then nvl(fy.fy,0) else 0 end) h1"
				+ "\n\t,SUM(case when fy.target_month between to_date('" + fy + "/06/01','yyyy/mm/dd') and to_date('"
				+ fy + "/11/30','yyyy/mm/dd') then 0 else nvl(fy.fy,0) end) h2"
				+ "\n\t,SUM(nvl(fy.fy,0)) fy,nvl(total.total,0) total" + "\n from"
				+ "\n\t (SELECT DISTINCT F.PROJ_CODE, PORG.CC_ORG_ID, PORG.PROD_ORG_ID " + "\n\t FROM " + tableOrg
				+ ", " + table + "\n\t WHERE F.PROJ_CODE = PORG.PROJ_CODE \n\t ) FALL" + "\n\t,("
				+ "\n\t select F.proj_code " + addColumnOrgIn + ",nvl(F.forecast,F.last_forecast) fy,F.target_month"
				+ "\n\t from " + tableOrg + addConditionFrom + ", " + table + "\n\t where " + condition
				+ "\n\t AND  F.PROJ_CODE = PORG.PROJ_CODE"
				+ "\n\t AND   (CASE WHEN EXTRACT(MONTH FROM F.TARGET_MONTH ) < 6 THEN F.TARGET_MONTH "
				+ "\n\t         ELSE ADD_MONTHS(F.TARGET_MONTH,-12) END) "
				+ "\n\t     BETWEEN NVL(PORG.START_DATE, TO_DATE('19990101','YYYYMMDD')) AND NVL(PORG.END_DATE,TO_DATE('29990101','YYYYMMDD'))"
				+ "\n\t AND   F.TARGET_MONTH " + "\n\t     BETWEEN TO_DATE('" + fy + "0101','YYYYMMDD') AND TO_DATE('"
				+ fy + "1231','YYYYMMDD')" + addConditionWhere + "\n\t) FY" + "\n\t,("
				+ "\n\t select F.proj_code,sum(nvl(nvl(F.forecast,F.last_forecast),0)) total" + "\n\t from " + table
				+ "\n\t group by F.proj_code " + "\n\t) total" + "\n where 1=1"
				+ "\n\t and FALL.proj_code = fy.proj_code(+)" + "\n\t and FALL.proj_code = total.proj_code(+)");

		sql.append("\n\t AND FALL.CC_ORG_ID = fy.CC_ORG_ID(+) ");
		sql.append("\n\t AND FALL.PROD_ORG_ID = fy.PROD_ORG_ID(+) ");

		sql.append("\n group by" + "\n\t fall.proj_code" + "\n\t,nvl(total.total,0)" + addColumnOrgOut);
		return sql.toString();
	}

	/**
	 * Q1～FY用 業務委託用
	 *
	 * @conditionとはforecast.proj_codeの最初の文字(Projectの場合A,C,T,3,業務委託の場合E)を<br>
	 * 																		indexに引っ掛けるため指定している
	 */
	private String getSQL_SumEx(HttpServletRequest request, String table, String tableOrg, String condition) {
		int fy = Integer.parseInt(request.getParameter("c_fy"));
		String o_org_name = request.getParameter("o_org_name");
		String o_prd_org_name = request.getParameter("o_prd_org_name");
		String c_org_id = request.getParameter("c_org_id");
		StringBuffer addColumnOrgIn = new StringBuffer();
		StringBuffer addColumnOrgOut = new StringBuffer();
		StringBuffer addConditionFrom = new StringBuffer();
		StringBuffer addConditionWhere = new StringBuffer();
		addColumnOrgIn.append(", PORG.CC_ORG_ID ");
		addColumnOrgIn.append(", PORG.PROD_ORG_ID ");
		if (o_org_name != null) {
			addColumnOrgOut.append(", FALL.CC_ORG_ID ");
		}
		if (o_prd_org_name != null) {
			addColumnOrgOut.append(", FALL.PROD_ORG_ID ");
		}
		if (c_org_id != null && !"".equals(c_org_id)) {
			// fy12のやつだったら
			if (getParameter(request, "fy12_flg") != null) {
				addConditionFrom.append(",PRODUCT_ORG_LIST PRD2");
				addConditionWhere.append("\n AND PORG.PROD_ORG_ID = PRD2.ORG_ID(+)"
						+ "\n AND DECODE((SELECT DISTINCT ORG_LEVEL FROM PRODUCT_ORG_LIST WHERE ORG_ID = '" + c_org_id
						+ "'" + getPrdOrgPeriod(fy, "")
						+ "\n ),1,PRD2.LEVEL1,2,PRD2.LEVEL2,3,PRD2.LEVEL3,4,PRD2.LEVEL4,5,PRD2.LEVEL5) = '" + c_org_id
						+ "'"
						+ "\n AND PRD2.ORG_START_DATE = (SELECT MAX(ORG_START_DATE) FROM PRODUCT_ORG_LIST WHERE ORG_ID = PRD2.ORG_ID"
						+ getPrdOrgPeriod(fy, "") + "\n )" + "\n AND PORG.PROD_ORG_ID = PRD2.ORG_ID ");
			} else {
				addConditionFrom.append(", cost_center_list CC2");
				addConditionWhere.append("\n and PORG.CC_ORG_ID = CC2.org_id(+)"
						+ "\n and DECODE((SELECT distinct org_level FROM cost_center_list where org_id = '" + c_org_id
						+ "'" + getOrgPeriod(fy, "")
						+ "\n ),1,CC2.level1,2,CC2.level2,3,CC2.level3,4,CC2.level4,5,CC2.level5) = '" + c_org_id + "'"
						+ "\n and CC2.org_start_date = (select max(org_start_date) from cost_center_list where org_id = CC2.org_id"
						+ getOrgPeriod2(fy, "") + "\n )");
			}
		}

		StringBuffer sql = new StringBuffer();
		sql.append("select  fall.proj_code " + addColumnOrgOut + "\n\t,SUM(case when fy.target_month between to_date('"
				+ fy + "/06/01','yyyy/mm/dd') " + "and to_date('" + fy
				+ "/8/31','yyyy/mm/dd') then nvl(fy.fy,0) else 0 end) q1"
				+ "\n\t,SUM(case when fy.target_month between to_date('" + fy + "/09/01','yyyy/mm/dd') and to_date('"
				+ fy
				+ "/11/30','yyyy/mm/dd') then nvl(fy.fy,0) else 0 end) q2 \n\t,SUM(case when fy.target_month between to_date('"
				+ fy + "/03/01','yyyy/mm/dd') and to_date('" + fy
				+ "/11/30','yyyy/mm/dd') then 0 else nvl(fy.fy,0) end) q3 \n\t,SUM(case when fy.target_month between to_date('"
				+ fy + "/03/01','yyyy/mm/dd') and to_date('" + fy
				+ "/05/31','yyyy/mm/dd') then nvl(fy.fy,0) else 0 end) q4 \n\t,SUM(case when fy.target_month between to_date('"
				+ fy + "/06/01','yyyy/mm/dd') and to_date('" + fy
				+ "/11/30','yyyy/mm/dd') then nvl(fy.fy,0) else 0 end) h1 \n\t,SUM(case when fy.target_month between to_date('"
				+ fy + "/06/01','yyyy/mm/dd') and to_date('" + fy
				+ "/11/30','yyyy/mm/dd') then 0 else nvl(fy.fy,0) end) h2 \n\t,SUM(nvl(fy.fy,0)) fy,nvl(total.total,0) total "
				+ "\n from \n\t (SELECT DISTINCT F.PROJ_CODE, PORG.CC_ORG_ID, PORG.PROD_ORG_ID \n\t FROM " + tableOrg
				+ ", EX_CONTRACT_LIST EXC, " + table + "\n\t WHERE EXC.PROJ_CODE = PORG.PROJ_CODE "
				+ "\n\t AND   EXC.EX_CONT_ID = F.PROJ_CODE" + "\n\t ) FALL \n\t,( \n\t select F.proj_code "
				+ addColumnOrgIn + ",nvl(F.forecast,F.last_forecast) fy,F.target_month \n\t from " + tableOrg
				+ ", EX_CONTRACT_LIST EXC " + addConditionFrom + ", " + table + "\n\t where " + condition
				+ "\n\t AND   F.TARGET_MONTH BETWEEN TO_DATE('" + fy + "0101','YYYYMMDD') AND TO_DATE('" + fy
				+ "1231','YYYYMMDD') \n\t AND  F.PROJ_CODE = EXC.EX_CONT_ID \n\t AND  EXC.PROJ_CODE = PORG.PROJ_CODE "
				+ addConditionWhere + "\n\t AND (CASE WHEN EXTRACT(MONTH FROM F.TARGET_MONTH ) < 6 THEN F.TARGET_MONTH "
				+ "\n\t ELSE ADD_MONTHS(F.TARGET_MONTH,-12) END) "
				+ "\n\t BETWEEN NVL(PORG.START_DATE, TO_DATE('19990101','YYYYMMDD')) AND NVL(PORG.END_DATE,TO_DATE('29990101','YYYYMMDD')) "
				+ "\n\t) fy \n\t,( \n\t select f.proj_code ,sum(nvl(nvl(F.forecast,F.last_forecast),0)) total \n\t from "
				+ "EX_CONTRACT_LIST EXC  , " + table + "\n\t where  F.PROJ_CODE = EXC.EX_CONT_ID "
				+ "\n\t group by f.proj_code  \n\t) total" + "\n where 1=1 \n\t and fall.proj_code = fy.proj_code(+)"
				+ "\n\t and fall.proj_code = total.proj_code(+)");
		sql.append("\n\t AND FALL.CC_ORG_ID = fy.CC_ORG_ID(+) ");
		sql.append("\n\t AND FALL.PROD_ORG_ID = fy.PROD_ORG_ID(+) ");
		sql.append("\n group by \n\t fall.proj_code \n\t,nvl(total.total,0)" + addColumnOrgOut);

		return sql.toString();
	}

	/**
	 * 各月Forecast用
	 *
	 * @conditionとはforecast.proj_codeの最初の文字(Projectの場合T,業務委託の場合E)<br>
	 * 																indexに引っ掛けるため指定している
	 */
	private String getSQL_Forecast(HttpServletRequest request, String table, String tableOrg, String condition,
			String type) {
		// fy
		int target_year = Integer.parseInt(request.getParameter("c_fy"));
		// 表示月
		int target_month = Integer.parseInt(request.getParameter("c_start_month"));
		// 表示期間
		int show_length = Integer.parseInt(request.getParameter("c_show_length"));

		// フォーマット
		DecimalFormat YYYY = new DecimalFormat("0000");
		DecimalFormat MM = new DecimalFormat("00");

		int forecastYear = target_year;
		int forecastMonth = target_month;
		StringBuffer whereSb = new StringBuffer();
		whereSb.append("\n\t and (");
		for (int i = 0; i < show_length; i++) {
			if (12 < forecastMonth) {
				forecastMonth = forecastMonth - 12;
			}
			if (0 < i && forecastMonth == 6) {
				forecastYear = forecastYear + 1;
			}
			if (0 < i) {
				whereSb.append("\n\t or ");
			}
			whereSb.append("f.target_month = to_date('");
			whereSb.append(YYYY.format(forecastYear));
			whereSb.append(MM.format(forecastMonth));
			whereSb.append("01', 'YYYYMMDD')");
			forecastMonth++;
		}
		whereSb.append(")");

		String where = whereSb.toString();

		// GROUP BYを動的にする
		String o_org_name = request.getParameter("o_org_name");
		String o_prd_org_name = request.getParameter("o_prd_org_name");
		String c_org_id = request.getParameter("c_org_id");

		String selectOrgColumn;
		if (o_org_name != null && o_prd_org_name != null) {
			selectOrgColumn = " cc_org_id, prod_org_id  ";
		} else if (o_org_name != null && o_prd_org_name == null) {
			selectOrgColumn = " cc_org_id  ";
		} else if (o_org_name == null && o_prd_org_name != null) {
			selectOrgColumn = " prod_org_id  ";
		} else {
			selectOrgColumn = " null ";
		}

		StringBuffer from_f = new StringBuffer("\n from" + "\n\t\t (select" + selectOrgColumn + "\n\t\t ,f.proj_code"
				+ "\n\t\t,to_char(f.target_month,'yyyymm') target_month"
				+ "\n\t\t,sum(nvl(nvl(f.forecast,f.last_forecast),0)) F" + "\n\t\t,sum(nvl(f.commit_flag,0)) C"
				+ "\n\t\t,sum(decode(f.forecast,NULL,0,1)) A"
				+ "\n\t\t,sum(decode(nvl(f.forecast,f.last_forecast),NULL,0,1)) N" + "\n\t from" + "\n\t " + tableOrg);
		if ("EXCONT".equals(type)) {
			from_f.append(", ex_contract_list ex  ");
		}
		StringBuffer from_w = new StringBuffer("," + table + "\n\t where " + condition + where
				+ "\n\t and case when (to_char(f.target_month,'MM') >= '06') then add_months(f.target_month,-12) else f.target_month end between porg.start_date and nvl(porg.end_date,TO_DATE('29991231','YYYYMMDD')) ");
		if ("EXCONT".equals(type)) {
			from_w.append("and porg.proj_code = ex.proj_code " + "\n\t  and ex.ex_cont_id = f.proj_code ");
		} else {
			from_w.append("\n\t and   f.proj_code = porg.proj_code");
		}

		// c_org_idパラメータで絞る
		if ((c_org_id != null) && (!c_org_id.equals(""))) {
			// fy12のやつだったら
			if (getParameter(request, "fy12_flg") != null) {

				from_f.append("\n,PRODUCT_ORG_LIST PRD2");

				from_w.append("\n AND PORG.PROD_ORG_ID = PRD2.ORG_ID(+)");

				from_w.append("\n AND DECODE((SELECT DISTINCT ORG_LEVEL FROM PRODUCT_ORG_LIST WHERE ORG_ID = '"
						+ c_org_id + "'" + getPrdOrgPeriod(target_year, ""));
				from_w.append("\n ),1,PRD2.LEVEL1,2,PRD2.LEVEL2,3,PRD2.LEVEL3,4,PRD2.LEVEL4,5,PRD2.LEVEL5) = '"
						+ c_org_id + "'");
				from_w.append(
						"\n AND PRD2.ORG_START_DATE = (SELECT MAX(ORG_START_DATE) FROM PRODUCT_ORG_LIST WHERE ORG_ID = PRD2.ORG_ID");
				from_w.append(getPrdOrgPeriod(target_year, ""));
				from_w.append("\n )");

			} else {

				from_f.append("\n,cost_center_list CC2");

				from_w.append("\n and PORG.CC_ORG_ID = CC2.org_id(+)");

				from_w.append("\n and DECODE((SELECT distinct org_level FROM cost_center_list where org_id = '"
						+ c_org_id + "'" + getOrgPeriod(target_year, ""));
				from_w.append(
						"\n ),1,CC2.level1,2,CC2.level2,3,CC2.level3,4,CC2.level4,5,CC2.level5) = '" + c_org_id + "'");
				from_w.append(
						"\n and CC2.org_start_date = (select max(org_start_date) from cost_center_list where org_id = CC2.org_id");
				from_w.append(getOrgPeriod2(target_year, ""));
				from_w.append("\n )");
			}
		}
		String from = from_f.toString() + from_w.toString() + "";

		// CCを出す場合はCCごと、PRODを出す場合はPRODごとにGroupBy
		StringBuffer select = new StringBuffer("select proj_code ");

		StringBuffer groupBy = new StringBuffer(" ");
		if (o_org_name != null && o_prd_org_name != null) {
			select.append(", cc_org_id, prod_org_id ");
			groupBy.append(", cc_org_id, prod_org_id ");
		} else if (o_org_name != null && o_prd_org_name == null) {
			select.append(", cc_org_id  ");
			groupBy.append(", cc_org_id ");
		} else if (o_org_name == null && o_prd_org_name != null) {
			select.append(", prod_org_id  ");
			groupBy.append(", prod_org_id  ");
		} else {
			select.append(" ");
		}

		for (int count = 1; count < show_length + 1; count++) {
			if (12 < target_month) {
				target_month = target_month - 12;
			}
			if (1 < count && target_month == 6) {
				target_year++;
			}

			select.append("\n\t,sum(decode(target_month,'" + YYYY.format(target_year) + MM.format(target_month)
					+ "',F)) F" + MM.format(count));
			select.append("\n\t,sum(decode(target_month,'" + YYYY.format(target_year) + MM.format(target_month)
					+ "',C)) C" + MM.format(count));
			select.append("\n\t,sum(decode(target_month,'" + YYYY.format(target_year) + MM.format(target_month)
					+ "',A)) A" + MM.format(count));
			select.append("\n\t,sum(decode(target_month,'" + YYYY.format(target_year) + MM.format(target_month)
					+ "',N)) N" + MM.format(count));
			target_month++;
		}

		String sql = select.toString() + from + "\n\t group by f.proj_code, f.target_month " + groupBy.toString()
				+ ")F \n\t group by f.proj_code " + groupBy.toString();

		return sql;
	}

	/**
	 * データを取得してCSVファイルを返す
	 *
	 * @param con
	 * @param response
	 * @throws Exception
	 */
	private void exportCsv(HttpServletRequest request, HttpServletResponse response, HashSet selectedItem,
			SqlQuery qProject, SqlQuery qExCont) throws Exception {
		boolean isFull = false;
		boolean noDone = false;
		boolean isContMeisai = false;

		if ("yes".equals((String) request.getParameter("isFull")))
			isFull = true;
		if ("yes".equals((String) request.getParameter("noDone")))
			noDone = true;
		if ("yes".equals((String) request.getParameter("cont_meisai_flg")))
			isContMeisai = true;

		response.setContentType("application/octet-stream; charset=Shift_JIS");
		String strFileName = java.net.URLEncoder.encode("RevForecast.csv", "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + strFileName + "\"");

		// パラメータ取得
		int startMonth = Integer.parseInt(getParameter(request, "c_start_month"));
		int showLength = Integer.parseInt(getParameter(request, "c_show_length"));

		// カラムタイトル
		HashMap pjTitleList = getPjTitleList(request);
		// 順番を保持したItem一覧
		ArrayList pjItemList = getPjItemList();

		// PrintWriter
		PrintWriter out = response.getWriter();
		if (qProject.isEmpty()) {
			out.println("データはありません");
		} else {
			resultCount = 0;
			// TITLE
			// 全ての項目分(pjItemList)LOOPし、selectedItemにあれば、項目名(pjTitleList)表示する
			for (int i = 0; i < pjItemList.size(); i++) {
				if (selectedItem.contains(pjItemList.get(i))) {
					String outTitle = (String) pjTitleList.get((String) pjItemList.get(i));
					out.print(outTitle.replaceAll("<br/>", "") + ",");
				}
			}
			// 業務委託TITLE
			if (getParameter(request, "o_ex_cont") != null) {
				if (getParameter(request, "o_ex_cont_id") != null) {
					out.print(csvExContId + ",");
				}
				// "業務委託先,業務委託Info,業務委託契約形態,"
				out.print(csvSubcontractor + "," + csvSubcontractInfo + "," + csvPoNumber + ","
						+ csvSubcontractType.replaceAll("<br/>", "") + ",");
			}

			// HEADER出力 Forecast 月ごと
			int f_mon = startMonth;
			for (int cnt = 0; cnt < showLength; cnt++) {
				// 1月に戻す
				if (f_mon > 12) {
					f_mon = f_mon - 12;
				}
				out.print(String.valueOf(f_mon) + ",");
				f_mon++;
			}
			out.print("Q1,Q2,Q3,Q4,H1,H2,FY,TOTAL");
			// HEADER出力 commitflag Forecast 月ごと
			f_mon = startMonth;
			for (int cnt = 0; cnt < showLength; cnt++) {
				// 1月に戻す
				if (f_mon > 12) {
					f_mon = f_mon - 12;
				}
				out.print(",commitflag" + String.valueOf(f_mon));
				f_mon++;
			}
			out.println("");

			// データ
			// プロジェクト分LOOPする
			while (qProject.hasNext()) {
				SqlRow rProject = qProject.next();
				// Done別表示チェック 条件でDone別をチェックされていて、このPJのWSがDoneだったら何もしない
				if (!(noDone && "3".equals(rProject.getString("WS_CODE")))) {

					// 項目分出力する 全ての項目分(pjItemList)LOOPし、selectedItemにあれば、値を表示する
					for (int i = 0; i < pjItemList.size(); i++) {
						if (selectedItem.contains(pjItemList.get(i))) {
							// ボタンは不要
							if ("BTN_CONT".equals(pjItemList.get(i))) {
								out.print("\"\",");
							} else if ("BTN_FCST".equals(pjItemList.get(i))) {
								out.print("\"\",");
							} else if ("CONTRACT_AMOUNT".equals(pjItemList.get(i))) {
								// 契約金額の場合
								out.print("\"" + CommonUtil.formatKingaku2(
										rProject.getString((String) pjItemList.get(i)), isFull) + "\",");
							} else if ("OWNCONTRACT_AMOUNT".equals(pjItemList.get(i))) {
								// 自組織契約金額の場合
								out.print("\"" + CommonUtil.formatKingaku2(
										rProject.getString((String) pjItemList.get(i)), isFull) + "\",");
							} else if ("KJZ".equals(pjItemList.get(i))) {
								// 計上残の場合
								out.print("\"" + CommonUtil.formatKingaku2(
										rProject.getString((String) pjItemList.get(i)), isFull) + "\",");
							} else {
								out.print("\"" + rProject.getStringWithoutNull((String) pjItemList.get(i)) + "\",");
							}
						}
					}
					// 業務委託がチェックされていれば空で出力
					if (getParameter(request, "o_ex_cont") != null) {
						if (getParameter(request, "o_ex_cont_id") != null) {
							out.print("\"\",");
						}
						out.print("\"\",\"\",\"\",\"\",");
					}

					// Forecast 月ごと
					for (int cnt = 1; cnt < showLength + 1; cnt++) {
						// actualは出さない。色は画面をコピペして使ってもらう commitflagは下で出す
						String fcstval = rProject.getString("FCST" + String.valueOf(cnt));
						if (fcstval != null) {
							out.print("\"" + CommonUtil.formatKingaku(Double.parseDouble(fcstval), isFull) + "\",");
						} else {
							out.print("\"\",");
						}
					}
					// ForecastQ,H,FY
					out.print("\"" + CommonUtil.formatKingaku(rProject.getDouble("Q1"), isFull) + "\",");
					out.print("\"" + CommonUtil.formatKingaku(rProject.getDouble("Q2"), isFull) + "\",");
					out.print("\"" + CommonUtil.formatKingaku(rProject.getDouble("Q3"), isFull) + "\",");
					out.print("\"" + CommonUtil.formatKingaku(rProject.getDouble("Q4"), isFull) + "\",");
					out.print("\"" + CommonUtil.formatKingaku(rProject.getDouble("H1"), isFull) + "\",");
					out.print("\"" + CommonUtil.formatKingaku(rProject.getDouble("H2"), isFull) + "\",");
					out.print("\"" + CommonUtil.formatKingaku(rProject.getDouble("FY"), isFull) + "\",");
					out.print("\"" + CommonUtil.formatKingaku(rProject.getDouble("TOTAL"), isFull) + "\",");

					// Forecast commitflag 月ごと
					for (int cnt = 1; cnt < showLength + 1; cnt++) {
						// commit_flg
						String cmtflg = rProject.getString("COMMIT_FLG" + String.valueOf(cnt));
						if (cmtflg != null) {
							out.print("\"" + CommonUtil.zerovl(cmtflg, "") + "\",");
						} else {
							out.print("\"\",");
						}
					}

					out.println("");
					resultCount++;

					// 業務委託
					if (getParameter(request, "o_ex_cont", null) != null) {
						qExCont.resetCurrent();
						while (qExCont.hasNext()) {
							SqlRow rExCont = qExCont.next();
							ArrayList colsName = rExCont.getAllColumnsName();
							String pjCode = CommonUtil.nvl(rProject.getString("CODE"));
							String exContPjCode = CommonUtil.nvl(rExCont.getString("PROJ_CODE"));

							// MP組織
							String pjCcOrg = "";
							String exContPjCcOrg = "";
							if (colsName.contains("EX_CC_ORG_ID")) {
								pjCcOrg = CommonUtil.nvl(rProject.getString("CC_ORG_ID"));
								exContPjCcOrg = CommonUtil.nvl(rExCont.getString("EX_CC_ORG_ID"));
							}
							// PJ管理組織
							String pjPdOrg = "";
							String exContPjPdOrg = "";
							if (colsName.contains("EX_PROD_ORG_ID")) {
								pjPdOrg = CommonUtil.nvl(rProject.getString("PROD_ORG_ID"));
								exContPjPdOrg = CommonUtil.nvl(rExCont.getString("EX_PROD_ORG_ID"));
							}
							// CC組織とPRD組織が同じ時に出力。組織違いで2つ以上同じPROJ_CODEが取れていることがあるので。
							if (pjCode.equals(exContPjCode) && pjCcOrg.equals(exContPjCcOrg)
									&& pjPdOrg.equals(exContPjPdOrg)) {
								// PJ情報を出力項目分出力する
								// 全ての項目分(pjItemList)LOOPし、selectedItemにあれば、値を表示する
								for (int i = 0; i < pjItemList.size(); i++) {
									// 業務委託明細を出す場合
									if (selectedItem.contains(pjItemList.get(i))) {
										if (isContMeisai) {
											// ボタンは不要
											if ("BTN_CONT".equals(pjItemList.get(i))) {
												out.print("\"\",");
											} else if ("BTN_FCST".equals(pjItemList.get(i))) {
												out.print("\"\",");
											} else if ("CONTRACT_AMOUNT".equals(pjItemList.get(i))) {
												// 契約金額の場合
												out.print("\""
														+ CommonUtil.formatKingaku2(
																rProject.getString((String) pjItemList.get(i)), isFull)
														+ "\",");
											} else if ("OWNCONTRACT_AMOUNT".equals(pjItemList.get(i))) {
												// 自組織契約金額の場合
												out.print("\""
														+ CommonUtil.formatKingaku2(
																rProject.getString((String) pjItemList.get(i)), isFull)
														+ "\",");
											} else if ("KJZ".equals(pjItemList.get(i))) {
												// 計上残の場合
												out.print("\""
														+ CommonUtil.formatKingaku2(
																rProject.getString((String) pjItemList.get(i)), isFull)
														+ "\",");
											} else {
												out.print(
														"\"" + rProject.getStringWithoutNull((String) pjItemList.get(i))
																+ "\",");
											}
										} else {
											out.print("\"\",");
										}
									}
								}
								if (getParameter(request, "o_ex_cont_id") != null) {
									out.print("\"" + rExCont.getStringWithoutNull("EX_CONT_ID") + "\",");
								}
								out.print("\"" + rExCont.getStringWithoutNull("EX_CONT_NAME") + "\",\""
										+ rExCont.getStringWithoutNull("COMMENTS") + "\",\""
										+ rExCont.getStringWithoutNull("PO_NUMBER") + "\",\""
										+ rExCont.getStringWithoutNull("EX_CONT_TYPE") + "\",");

								// Forecast 月ごと
								for (int cnt = 1; cnt < showLength + 1; cnt++) {
									String exContval = rExCont.getString("FCST" + String.valueOf(cnt));
									// actualは出さない。色は画面をコピペして使って頂く
									// commit_flgは後ろで出す
									if (exContval != null) {
										out.print("\"" + CommonUtil.formatKingaku(Double.parseDouble(exContval), isFull)
												+ "\",");
									} else {
										out.print("\"\",");
									}
								}
								// ForecastQ,H,FY
								out.print("\"" + CommonUtil.formatKingaku(rExCont.getDouble("Q1"), isFull) + "\",");
								out.print("\"" + CommonUtil.formatKingaku(rExCont.getDouble("Q2"), isFull) + "\",");
								out.print("\"" + CommonUtil.formatKingaku(rExCont.getDouble("Q3"), isFull) + "\",");
								out.print("\"" + CommonUtil.formatKingaku(rExCont.getDouble("Q4"), isFull) + "\",");
								out.print("\"" + CommonUtil.formatKingaku(rExCont.getDouble("H1"), isFull) + "\",");
								out.print("\"" + CommonUtil.formatKingaku(rExCont.getDouble("H2"), isFull) + "\",");
								out.print("\"" + CommonUtil.formatKingaku(rExCont.getDouble("FY"), isFull) + "\",");
								out.print("\"" + CommonUtil.formatKingaku(rExCont.getDouble("TOTAL"), isFull) + "\",");

								// Forecast commitflag 月ごと
								for (int cnt = 1; cnt < showLength + 1; cnt++) {
									String exContval = rExCont.getString("COMMIT_FLG" + String.valueOf(cnt));
									// commit_flgを出す
									if (exContval != null) {
										out.print("\"" + CommonUtil.zerovl(exContval, "") + "\",");
									} else {
										out.print("\"\",");
									}
								}
								out.println("");
								resultCount++;
							}
						}
					}
				}
			}
		}
		out.close();

	}

	/**
	 * スタイルシートURL取得メソッド.
	 *
	 *
	 */
	protected URL getXSLURL(HttpServletRequest request) throws Exception {
		return getClass().getResource("");
	}

	/**
	 * メニューコード取得メソッド.
	 *
	 * @return メニューコード
	 */
	protected String getMenuCode(HttpServletRequest request) {
		String code = "";

		if (request.getParameter("forecastReport_flg").equals("yes")) {
			// ForecastReport一覧
			code = "20602";
		} else {
			// Forecast一覧
			code = "20105";
		}

		return code;
	}

	/**
	 * DOM取得後処理メソッド.
	 *
	 *
	 */
	protected void actionAfterGetDOM(String key, Element rowset, Document x_doc, HttpServletRequest request)
			throws Exception {
	}



}
