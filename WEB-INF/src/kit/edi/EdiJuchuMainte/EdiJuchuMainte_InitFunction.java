package kit.edi.EdiJuchuMainte;

import static fb.com.IKitComConst.*;
import static kit.edi.EdiJuchuMainte.IEdiJuchuMainte.*;

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
public class EdiJuchuMainte_InitFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = -3241662549560589469L;


	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = EdiJuchuMainte_InitFunction.class.getName();

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
	public EdiJuchuMainte_InitFunction(ActionMapping mapping, ActionForm form,
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
	protected String execute_() throws Exception {
		String sForwardPage = FORWARD_SUCCESS;

		// 編集リスト情報をセッションより削除する
		removeSession(KEY_SS_SEARCHEDLIST);

		// ***選択リストの設定***
		MstCdmeisyMaster_SearchService searchServ = new MstCdmeisyMaster_SearchService(
				getComUserSession(), getDatabase(),isTransaction(), getActionErrors());

		// 処理区分リストを作成する
		searchServ.search(KBN_EDI_SHORI);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_SHORI_KBN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 出荷日注意リストを作成する
		searchServ.search(KBN_SYUK_CAREF);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_SYUK_CAREF, new KitSelectList(searchServ.getPbsRecords()));
		}

		// データ有無リストを作成する
		searchServ.search(KBN_EDI_DATA_ARINASHI);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_DATA_ARINASHI, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 納品時間区分リストを作成する
		searchServ.search(KBN_EDI_NOHIN);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_NOHIN_JIKAN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 蔵入/直送区分リストを作成する
		searchServ.search(KBN_EDI_KURACYOKUSO);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KURA_CYOKUSO, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 重複区分リストを作成する
		searchServ.search(KBN_ARI_NASI_KB);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_ARI_NASI, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 単位リストを作成する
		searchServ.search(KBN_EDI_TANI);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_EDI_TANI, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 初期値設定
		EdiJuchuMainte_SearchForm searchForm = new EdiJuchuMainte_SearchForm();
		searchForm.initialize();

		setSession(KEY_SS_SEARCHFORM, searchForm);
		// 空のeditFormをセットする
		setRequest(KEY_SS_EDITFORM, new EdiJuchuMainte_EditForm());

		// 次画面へ遷移する
		return sForwardPage;
	}
}

