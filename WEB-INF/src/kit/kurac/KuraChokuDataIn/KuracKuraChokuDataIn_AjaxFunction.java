package kit.kurac.KuraChokuDataIn;

import static kit.kurac.KuraChokuDataIn.IKuracKuraChokuDataIn.*;

import java.io.PrintWriter;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.mastr.TatesenOrositen.MastrTatesenOrositenRecord;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComUserSession;
import fb.com.Records.FbMastrSyuhantenRecord;
import fb.inf.KitFunction;
import fb.inf.pbs.PbsUtil;

/**
 * Ajax処理の実装クラスです
 *
 */
public class KuracKuraChokuDataIn_AjaxFunction extends KitFunction {

	/**
	 *
	 */

	/** シリアルID */
	private static final long serialVersionUID = -5139455189296828009L;

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

	/**
	 * コンストラクタ。
	 *
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 */
	public KuracKuraChokuDataIn_AjaxFunction(ActionMapping mapping, ActionForm form,
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
			String objCd = (String) request_.getParameter("objCd"); 			// key値
			String objNm = (String) request_.getParameter("objNm"); 			// key名

			// チェックサービス
			KuracKuraChokuDataIn_CheckService checkServ = new KuracKuraChokuDataIn_CheckService(
					getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
			// ユーザーセッション
			ComUserSession cus = getComUserSession();
			String kaisyaCd = cus.getKaisyaCd(); // 会社CD

			StringBuilder xml = new StringBuilder();

			xml.append("<" + XML_TAG + ">\n");

			// xmlタグ作成
			// 縦線の場合
			if ("tatesenCd".equals(objNm)) {
				xml.append(createXmlTagForTatesen(checkServ, kaisyaCd, objCd));
			}
			// 運送店の場合
			if ("unsotenCd".equals(objNm)) {
				xml.append(createXmlTagForUnsoten(checkServ, kaisyaCd, objCd));
			}
			// 酒販店の場合
			if ("syuhantenCd".equals(objNm)) {
				xml.append(createXmlTagForSyuhanten(checkServ, kaisyaCd, objCd));
			}
			// 販売単価の場合
			if ("kingaku".equals(objNm)) {
				xml.append(createXmlTagForHanbaiGaku(objCd));
			}
			// 商品ｸﾞﾙｰﾌの場合
			if ("shohinGrp".equals(objNm)) {
				//xml.append(createXmlTagForShohinGrp(checkServ, objCd));
				xml.append(createXmlTagForDtInfo(checkServ, objCd));
			}

			xml.append("</" + XML_TAG + ">\n");

			write(xml.toString());
		} catch (Exception e) {
		}

		return FORWARD_SUCCESS;
	}

	/**
	 * 運送店XML出力用文字列生成
	 * @param checkService
	 * @param kaisyaCd
	 * @return
	 */
	public String createXmlTagForUnsoten(KuracKuraChokuDataIn_CheckService checkService, String kaisyaCd, String objCd) {
		StringBuilder tag = new StringBuilder();
		String usontenNm = checkService.getUnsotenNmByCd(objCd, kaisyaCd, true, false);

		tag.append("<unsotenNm>" + usontenNm + "</unsotenNm>\n");

		return tag.toString();
	}

	/**
	 * 酒販店XML出力用文字列生成
	 * @param checkService
	 * @param kaisyaCd
	 * @param objCd
	 * @return
	 */
	public String createXmlTagForSyuhanten(KuracKuraChokuDataIn_CheckService checkService, String kaisyaCd, String objCd) {
		StringBuilder tag = new StringBuilder();
		FbMastrSyuhantenRecord syuhantenRecord = checkService.getSyuhantenInfoByCd(objCd, false);

		tag.append("<syuhantenNm>" + checkService.nullToString(syuhantenRecord.getTenNmYago()) + "</syuhantenNm>\n");
		tag.append("<syuhantenTel>" + checkService.nullToString(syuhantenRecord.getTel()) + "</syuhantenTel>\n");
		tag.append("<syuhantenAddress>" + checkService.nullToString(syuhantenRecord.getAddress()) + "</syuhantenAddress>\n");
		tag.append("<syuhantenZip>" + checkService.nullToString(syuhantenRecord.getZip()) + "</syuhantenZip>\n");

		return tag.toString();
	}

	/**
	 * 縦線情報XML出力用文字列生成
	 * @param checkService
	 * @param kaisyaCd
	 * @param objCd
	 * @return
	 */
	public String createXmlTagForTatesen(KuracKuraChokuDataIn_CheckService checkService, String kaisyaCd, String objCd) {
		StringBuilder tag = new StringBuilder();

		MastrTatesenOrositenRecord orosisyosaiHdRecord = checkService.getOrositenInfoByTatesnCd(objCd, kaisyaCd);

		// 特約店CD
		tag.append("<tokuyakutenCd>" + checkService.nullToString(orosisyosaiHdRecord.getOrositenCd1Jiten()) + "</tokuyakutenCd>\n");
		//  特約店名
		tag.append("<tokuyakutenNm>" + checkService.nullToString(orosisyosaiHdRecord.getOrositenNm1JitenRyaku()) + "</tokuyakutenNm>\n");
		// デポ店CD
		tag.append("<depoCd>" + checkService.nullToString(orosisyosaiHdRecord.getOrositenCdDepo()) + "</depoCd>\n");
		// デポ店名
		tag.append("<depoNm>" + checkService.nullToString(orosisyosaiHdRecord.getOrositenNmDepoRyaku()) + "</depoNm>\n");
		// 二次店CD
		tag.append("<nijitenCd>" + checkService.nullToString(orosisyosaiHdRecord.getOrositenCd2Jiten()) + "</nijitenCd>\n");
		// 二次店名
		tag.append("<nijitenNm>" + checkService.nullToString(orosisyosaiHdRecord.getOrositenNm2JitenRyaku()) + "</nijitenNm>\n");
		// 三次店CD
		tag.append("<sanjitenCd>" + checkService.nullToString(orosisyosaiHdRecord.getOrositenCd3Jiten()) + "</sanjitenCd>\n");
		// 三次店名
		tag.append("<sanjitenNm>" + checkService.nullToString(orosisyosaiHdRecord.getOrositenNm3JitenRyaku()) + "</sanjitenNm>\n");

		return tag.toString();
	}

	/**
	 * 販売額XML出力用文字列生成
	 * @param objCd
	 * @return
	 */
	public String createXmlTagForHanbaiGaku(String objCd) {

		String objCds[] = objCd.split(",");
		// セット数
		String setSu = objCds[0];
		// 販売単価
		String hanbaiTanka = objCds[1].replace(",","");
		// 販売額
		BigDecimal decHanbaiGaku = BigDecimal.ZERO;
		String hanbaiGaku = "";
		StringBuilder tag = new StringBuilder();

		// 販売額の計算
		if (!PbsUtil.isEmpty(hanbaiTanka) && !PbsUtil.isEmpty(setSu)) {
			// 販売額
			decHanbaiGaku = new BigDecimal(hanbaiTanka).multiply(new BigDecimal(setSu));
			hanbaiGaku = decHanbaiGaku.setScale(2, BigDecimal.ROUND_DOWN).toString();
		}

		tag.append("<hanbaiGaku>" + hanbaiGaku + "</hanbaiGaku>\n");

		return tag.toString();
	}

//	/**
//	 * 商品区分出力用文字列生成
//	 * @param objCd
//	 * @return
//	 */
//	public String createXmlTagForShohinGrp(KuracKuraChokuDataIn_CheckService checkService, String objCd) {
//		StringBuilder tag = new StringBuilder();
//
//		String objCds[] = objCd.split(",");
//		// lineNo
//		String lineNo = objCds[0];
//		// 商品グループ
//		String shohinGrp = objCds[1];
//		// 事業所区分
//		String jigyosyoKbn = objCds[2];
//		// 販売額
//		//BigDecimal decHanbaiGaku = BigDecimal.ZERO;
//
//		KuracKuraChokuDataIn_SearchService searchServ = new KuracKuraChokuDataIn_SearchService(
//				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
//
//		KuracKuraChokuDataInDtRecord rec = searchServ.getKurachokuDataInDtListByKbnLine(jigyosyoKbn, shohinGrp, lineNo);
//		// 商品CD
//		tag.append("<shohinCd>" + checkService.nullToString(rec.getShohinCd()) + "</shohinCd>\n");
//		// 商品名
//		tag.append("<shohinNm>" + checkService.nullToString(rec.getShohinNm()) + "</shohinNm>\n");
//		// 容量名
//		tag.append("<youkiKigoNm>" + checkService.nullToString(rec.getYoukiKigoNm()) + "</youkiKigoNm>\n");
//		// セット数
//		tag.append("<shohinSet>" + checkService.nullToString(rec.getShohinSet()) + "</shohinSet>\n");
//		// 販売単価
//		tag.append("<hanbaiTanka>" + checkService.nullToString(rec.getHanbaiTanka()) + "</hanbaiTanka>\n");
////		// 販売額の計算
////		if (!PbsUtil.isEmpty(hanbaiTanka)) {
////			decHanbaiGaku = new BigDecimal(hanbaiTanka).multiply(new BigDecimal(setSu));
////		}
//		// 販売額
//		tag.append("<hanbaiGaku>" + checkService.nullToString(rec.getHanbaiGaku()) + "</hanbaiGaku>\n");
//		// 蔵直商品ｸﾞﾙｰﾌﾟCD
//		tag.append("<kuradenLineNo>" + checkService.nullToString(rec.getKuradenLineNo()) + "</kuradenLineNo>\n");
//
//		return tag.toString();
//	}


	/**
	 * 明細出力用文字列生成
	 * @param objCd
	 * @return
	 */
	public String createXmlTagForDtInfo(KuracKuraChokuDataIn_CheckService checkService, String objCd) {
		StringBuilder tag = new StringBuilder();

		String objCds[] = objCd.split(",");
		// 商品グループ
		String shohinGrp = objCds[0];
		// 事業所区分
		String jigyosyoKbn = objCds[1];

		KuracKuraChokuDataIn_SearchService searchServ = new KuracKuraChokuDataIn_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		KuracKuraChokuDataInDtList dtInfoList = searchServ.getKurachokuDataInDtListByKbn(jigyosyoKbn, shohinGrp);

		for (int i = 0; i < dtInfoList.size(); i ++){
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord)dtInfoList.get(i);
			tag.append("<shohin>\n");

			// 商品CD
			tag.append("<shohinCd>" + checkService.nullToString(rec.getShohinCd()) + "</shohinCd>\n");
			// 商品名
			tag.append("<shohinNm>" + checkService.nullToString(rec.getShohinNm()) + "</shohinNm>\n");
			// 容量名
			tag.append("<youkiKigoNm>" + checkService.nullToString(rec.getYoukiKigoNm()) + "</youkiKigoNm>\n");
			// 販売単価
			BigDecimal hanbaiTanka = new BigDecimal(rec.getHanbaiTanka()).setScale(2, BigDecimal.ROUND_DOWN);
			tag.append("<hanbaiTanka>" + hanbaiTanka.toString() + "</hanbaiTanka>\n");

			// 申込用紙表記順位
			tag.append("<kuradenLineNo>" + checkService.nullToString(rec.getKuradenLineNo()) + "</kuradenLineNo>\n");
			// ｷｬﾝﾍﾟｰﾝ対象区分
			tag.append("<cpKbn>" + checkService.nullToString(rec.getCpKbn()) + "</cpKbn>\n");

			tag.append("</shohin>\n");
		}

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

}