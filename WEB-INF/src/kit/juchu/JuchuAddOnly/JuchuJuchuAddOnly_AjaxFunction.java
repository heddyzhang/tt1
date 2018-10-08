package kit.juchu.JuchuAddOnly;

import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.juchu.JuchuAddOnly.IJuchuJuchuAddOnly.*;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComUserSession;
import fb.inf.KitFunction;
import fb.inf.KitSelectList;
import fb.inf.pbs.PbsUtil;

/**
 * Ajax処理の実装クラスです
 *
 */
public class JuchuJuchuAddOnly_AjaxFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = -3702298749321256063L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JuchuJuchuAddOnly_AjaxFunction.class.getName();

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
	public JuchuJuchuAddOnly_AjaxFunction(ActionMapping mapping, ActionForm form,
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
			String objNm = (String) request_.getParameter("objNm"); // ターゲット
			String objCd = (String) request_.getParameter("objCd"); // コード値
			String shortNm = (String) request_.getParameter("shortNm"); // 略称フラグ
			boolean isShortName = Boolean.valueOf(shortNm); // 略称フラグ

			// 値がない場合はダミー値
			if (PbsUtil.isEmpty(objNm)) { objNm = "dummy"; }
			if (PbsUtil.isEmpty(objCd)) { objCd = "dummy"; }
			if (PbsUtil.isEmpty(shortNm)) { isShortName = false; }

			// XML親タグ名
			String tagNm = "juchuJuchuAddOnly";

			// 作業用
			String tmpNm = "";
			StringBuilder tmpSb = new StringBuilder();

			// XML出力用文字列生成
			String[] objNms = objNm.split(",");
			tmpSb.append("<" + tagNm + ">\n");
			for (String tmpObjNm:objNms) {
				if (!PbsUtil.isEmpty(tmpObjNm)) {
					tmpNm = getValue(tmpObjNm, objCd, isShortName);
					if ("ZAIKO".equals(tmpObjNm)) {
						String[] tmpNms = tmpNm.split(",");
						String[] tmpObjNms = {"zaikoZansuCase", "zaikoZansuBara", "zaikoZansu", "zaikoGetDtTm"}; // 在庫残数（箱数、セット数、総バラ数、取得日時）
						tmpSb.append("<" + tmpObjNms[0] + ">" + tmpNms[0] + "</" + tmpObjNms[0] + ">\n"); // 箱数
						tmpSb.append("<" + tmpObjNms[1] + ">" + tmpNms[1] + "</" + tmpObjNms[1] + ">\n"); // セット数
						tmpSb.append("<" + tmpObjNms[2] + ">" + tmpNms[2] + "</" + tmpObjNms[2] + ">\n"); // 総バラ数
						tmpSb.append("<" + tmpObjNms[3] + ">" + tmpNms[3] + "</" + tmpObjNms[3] + ">\n"); // 取得日時
					} else {
						tmpSb.append("<" + tmpObjNm + ">" + tmpNm + "</" + tmpObjNm + ">\n");
					}
				}
			}
			tmpSb.append("</" + tagNm + ">\n");

			// XML出力
			response_.setContentType("text/xml;charset=UTF-8");
			// キャッシュしない
			response_.addHeader("Pragma", "no-cache");
			response_.addHeader("Cache-Control", "no-cache");
			response_.addDateHeader("Expires", 0);
			PrintWriter out = response_.getWriter();
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
			out.println(tmpSb.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
		}

		return FORWARD_SUCCESS;
	}

	/**
	 * マスターから検索した値を返します。
	 *
	 * @param objNm
	 * @param objCd
	 * @param isShortName
	 * @return
	 */
	private String getValue(String objNm, String objCd, boolean isShortName) {
		String ret = "";

		// チェックサービス
		JuchuJuchuAddOnly_CheckService checkServ = new JuchuJuchuAddOnly_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// ユーザーセッション
		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		// キー（[0]呼び出し元,[1]蔵CD,[2]出荷日）
		String[] objCds = objCd.split(",");
		objCd = objCds[0];

		// 最終送荷先卸CDから最終送荷先卸名を検索
		if ("orositenNmLast".equals(objNm)) {
			ret = checkServ.getOrositenNmByCd(objCd, kaisyaCd, isShortName, false);

		// 酒販店CDから酒販店名を検索
		} else if ("syuhantenNm".equals(objNm)) {
			ret = checkServ.getSyuhantenNmByCd(objCd, false);

		// 運送店CDから運送店名を検索
		} else if ("unsotenNm".equals(objNm)) {
			ret = checkServ.getUnsotenNmByCd(objCd, kaisyaCd, isShortName, false);

		// 摘要区分から摘要内容を検索
		} else if (PbsUtil.isIncluded(objNm, "tekiyoNm1", "tekiyoNm2", "tekiyoNm3", "tekiyoNm4", "tekiyoNm5")) {
			ret = checkServ.getTekiyoNmByKbn(objCd, false);

		// 商品CDから商品名を検索
		} else if ("shohinNm".equals(objNm)) {
			ret = checkServ.getShohinNmByCd(objCd, kaisyaCd, ktksyCd, isShortName, false);

		// 商品CDから容量(ml)を検索
		} else if ("tnpnVolMl".equals(objNm)) {
			ret = checkServ.getTnpnVolMlByShohinCd(objCd, kaisyaCd, ktksyCd);
			if (PbsUtil.isEqual(YOURYO_ZERO, ret)) { ret = CHAR_BLANK; }

		// 商品CDから入数を検索
		} else if ("caseIrisu".equals(objNm)) {
			ret = checkServ.getCaseIrisuByShohinCd(objCd, kaisyaCd, ktksyCd);
			if (PbsUtil.isEqual(SU_ZERO, ret)) { ret = CHAR_BLANK; }

		// 商品CDから扱い区分を検索
		} else if ("atukaiKbnNm".equals(objNm)) {
			// 扱い区分
			String tmpCd = checkServ.getAtukaiKbnByShohinCd(objCd, kaisyaCd, ktksyCd);
			// 扱い区分名
			KitSelectList decodeList = (KitSelectList) getSession(KEY_SS_ATUKAI_KBN_SHORT);
			String tmpNm = decodeList.decodeCode(tmpCd);
			if (PbsUtil.isEmpty(tmpNm)) {
				tmpNm = " ";
			}
			ret = tmpNm;

		// 商品CDから販売単価を検索
		} else if ("hanbaiTanka".equals(objNm)) {
			ret = checkServ.getHanbaiTankaByShohinCd(objCd, kaisyaCd, ktksyCd);

		// 商品CDと蔵CDと出荷日から在庫残数（箱数、セット数、総バラ数、取得日時）を検索
		} else if ("ZAIKO".equals(objNm)) {
			objCds[2] = objCds[2].replaceAll("/", "");
			ret = checkServ.getZaikoZansu(objCd, objCds[1], objCds[2], kaisyaCd, ktksyCd);

		}

		return ret;
	}
}