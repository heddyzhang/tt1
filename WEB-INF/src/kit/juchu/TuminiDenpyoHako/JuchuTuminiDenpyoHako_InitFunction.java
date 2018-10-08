package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComCsvConst.*;
import static kit.juchu.TuminiDenpyoHako.IJuchuTuminiDenpyoHako.*;

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
public class JuchuTuminiDenpyoHako_InitFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = 1862724120381111032L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JuchuTuminiDenpyoHako_InitFunction.class.getName();

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
	public JuchuTuminiDenpyoHako_InitFunction(ActionMapping mapping, ActionForm form,
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
		setRequest(KEY_SS_EDITFORM, new JuchuTuminiDenpyoHako_EditForm());

		// ***選択リストの設定***
		MstCdmeisyMaster_SearchService searchServ = new MstCdmeisyMaster_SearchService(
				getComUserSession(), getDatabase(),isTransaction(), getActionErrors());

		// 利用区分リストを作成する
		searchServ.search(KBN_AVAILABLE);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_RIYOU_KBN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// データ種別CDリストを作成する
		searchServ.search(KBN_JDATAKIND);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_SYUBETU_CD, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 集約区分リストを作成する
		searchServ.search(KBN_YN_07);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_SYUYAKU_KBN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 積み残しフラグリストを作成する
		searchServ.search(KBN_YN_06);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_TUMINOKOSI_FLG, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 略称を使用する
	    searchServ.setForSelectListShort(true);
		// 扱い区分（略称）リストを作成する
		searchServ.search(KBN_SHN_DEAL);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_ATUKAI_KBN_SHORT, new KitSelectList(searchServ.getPbsRecords()));
		}

		// 正式名称を使用する
	    searchServ.setForSelectListShort(false);
		// 扱い区分（正式）リストを作成する
		searchServ.search(KBN_SHN_DEAL);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_ATUKAI_KBN, new KitSelectList(searchServ.getPbsRecords()));
		}

		// プリンター選択リストを作成する
//		searchServ.setUseCodeFlg(false); // CODEを使わずLABELを生成（NAMEを使う）
		searchServ.setCodeCd(CSV_NO_LS_SYUKA_TUMINI_DENPYO_HAKO);
		searchServ.search(KBN_PRINTER_ID);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_TARGET_PRINTER, new KitSelectList(searchServ.getPbsRecords()));
		} else {
			setErrorMessageId("errors.noPrinter");
		}
		searchServ.setCodeCd(null);

		// 初期値設定
		JuchuTuminiDenpyoHako_SearchForm searchForm = new JuchuTuminiDenpyoHako_SearchForm();
		searchForm.initialize();
		setSession(KEY_SS_SEARCHFORM, searchForm);

		// 次画面へ遷移する
		return sForwardPage;
	}

}