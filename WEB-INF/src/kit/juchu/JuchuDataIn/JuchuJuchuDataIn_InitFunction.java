package kit.juchu.JuchuDataIn;

import static fb.com.IKitComConst.*;
import static kit.juchu.JuchuDataIn.IJuchuJuchuDataIn.*;

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
public class JuchuJuchuDataIn_InitFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = 1208271458628292581L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JuchuJuchuDataIn_InitFunction.class.getName();

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
	public JuchuJuchuDataIn_InitFunction(ActionMapping mapping, ActionForm form,
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
		setRequest(KEY_SS_EDITFORM, new JuchuJuchuDataIn_EditForm());

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

		// 都道府県リストを作成する
		searchServ.search(KBN_COUNTY_CD);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_TODOFUKN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 識別（データ種別CD）リストを作成する
		searchServ.search(KBN_JDATAKIND);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_SIKIBETU, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 運賃リストを作成する
		searchServ.search(KBN_UNTIN_SEQ);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_UNTIN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 出荷場所リストを作成する
		searchServ.search(KBN_KURA);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_SYUKA_BASYO, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 荷受区分リストを作成する
		searchServ.search(KBN_NIUKE_TIME);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_NIUKE_KBN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 倉庫種類区分リストを作成する
		searchServ.search(KBN_SOUKO_SYURUI);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_SOUKO_SYURUI_KBN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 倉入・直送区分リストを作成する
		searchServ.search(KBN_DELIVERY2);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_DELIVERY_KBN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 略称を使用する
	    searchServ.setForSelectListShort(true);

		// 出荷先区分（略称）リストを作成する
		searchServ.search(KBN_SHIPMENT);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_SYUKA_KBN_SHORT, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 出荷対応（略称）リストを作成する
		searchServ.search(KBN_SHN_CARRY);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_SYUKA_TAIO_SHORT, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 扱い区分（略称）リストを作成する
		searchServ.search(KBN_SHN_DEAL);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_ATUKAI_KBN_SHORT, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 正式名称を使用する
	    searchServ.setForSelectListShort(false);

		// 出荷先区分（正式）リストを作成する
		searchServ.search(KBN_SHIPMENT);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_SYUKA_KBN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 出荷対応（正式）リストを作成する
		searchServ.search(KBN_SHN_CARRY);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_SYUKA_TAIO, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 扱い区分（正式）リストを作成する
		searchServ.search(KBN_SHN_DEAL);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_ATUKAI_KBN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 初期値設定
		JuchuJuchuDataIn_SearchForm searchForm = new JuchuJuchuDataIn_SearchForm();
		searchForm.initialize();
		setSession(KEY_SS_SEARCHFORM, searchForm);
		// 次画面へ遷移する
		return sForwardPage;
	}

}