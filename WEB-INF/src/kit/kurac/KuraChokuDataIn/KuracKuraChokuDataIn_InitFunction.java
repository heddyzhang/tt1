package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConst.*;
import static kit.kurac.KuraChokuDataIn.IKuracKuraChokuDataIn.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.MstSearch.MstCdmeisyMaster_SearchService;
import fb.inf.KitFunction;
import fb.inf.KitSelectList;
import fb.inf.exception.KitException;

/**
 * 初期表示処理の実装クラスです
 *
 */
public class KuracKuraChokuDataIn_InitFunction extends KitFunction {

	/**
	 *
	 */

	/** シリアルID */
	private static final long serialVersionUID = -7532638047832547153L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = KuracKuraChokuDataIn_InitFunction.class.getName();

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
	public KuracKuraChokuDataIn_InitFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		super(mapping, form, request, response);
	}

	/**
	 * 初期処理を実行する。<br>
	 * 初期リストを取得する
	 *
	 * @return Forward情報
	 * @throws KitException
	 */
	@Override
	public String execute_() throws KitException {

		String sForwardPage = FORWARD_SUCCESS;

		// 編集リスト情報をセッションより削除する
		removeSession(KEY_SS_SEARCHEDLIST);

		// 空のeditFormをセットする
		setRequest(KEY_SS_EDITFORM, new KuracKuraChokuDataIn_EditForm());

		// ***選択リストの設定***
		MstCdmeisyMaster_SearchService searchServ = new MstCdmeisyMaster_SearchService(
				getComUserSession(), getDatabase(),isTransaction(), getActionErrors());

		// 利用状態区分（フラグ）リストを作成する
		searchServ.search(KBN_DATA_STATUS);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_RIYOU_JOUTAI_KBN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 利用区分リストを作成する
		searchServ.search(KBN_AVAILABLE);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_RIYOU_KBN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 事業所リストを作成する
		searchServ.search(KBN_KURAC_SITEN);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KURAC_SITEN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 商品ｸﾞﾙｰﾌﾟリストを作成する
		searchServ.search(KBN_KURAC_SHOHINGRP);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KURAC_SHOHINGRP, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 運賃リストを作成する
		searchServ.search(KBN_UNTIN_SEQ);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_UNTIN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 宅配伝票区分リストを作成する
		searchServ.search(KBN_KURAC_BILL);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KURAC_BILL, new KitSelectList(searchServ.getPbsRecords()));
		}

		// ﾃﾞｰﾀ種別リストを作成する
		searchServ.search(KBN_KURAC_DATA);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KURAC_DATA, new KitSelectList(searchServ.getPbsRecords()));
		}

		// のし用途区分リストを作成する
		searchServ.search(KBN_KURAC_NOSIYOUTO);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KURAC_NOSIYOUTO, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 【のし】名前記載有無区分リストを作成する
		searchServ.search(KBN_KURAC_NOSINAME_YN);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KURAC_NOSINAME_YN, new KitSelectList(searchServ.getPbsRecords()));
		}

		//  酒販店chkﾘｽﾄ打出区分リストを作成する
		searchServ.search(KBN_KURAC_CHKSLIST_YN);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KURAC_CHKSLIST_YN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 届先Noリストを作成する
		searchServ.search(KBN_KURAC_UPLOAD_FLG);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KURAC_UPLOAD_FLG, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 初期値設定
		KuracKuraChokuDataIn_SearchForm searchForm = new KuracKuraChokuDataIn_SearchForm();
		searchForm.initialize();
		setSession(KEY_SS_SEARCHFORM, searchForm);
		// 次画面へ遷移する
		return sForwardPage;
	}

}