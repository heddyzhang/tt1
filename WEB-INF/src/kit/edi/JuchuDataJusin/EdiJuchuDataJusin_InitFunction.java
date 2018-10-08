package kit.edi.JuchuDataJusin;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.IS_EDITABLE_TRUE;
import static kit.edi.JuchuDataJusin.IEdiJuchuDataJusin.*;
import static kit.edi.ReinyuAnnai.IEdiReinyuAnnai.KEY_SS_SEARCHFORM;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.edi.ReinyuAnnai.EdiReinyuAnnai_SearchForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.MstSearch.MstCdmeisyMaster_SearchService;
import fb.inf.KitFunction;
import fb.inf.KitSelectList;
import fb.inf.exception.KitException;

/**
 * 99905_1_1　受注データ受信
 *
 * @author Tuneduka
 *
 */
public class EdiJuchuDataJusin_InitFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = 3952985396286152258L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = EdiJuchuDataJusin_InitFunction.class.getName();

	// -----------------------------------------
	// define variable
	// -----------------------------------------
	private static final boolean isTransactionalFunction = false;

	/**
	 * Transaction制御フラグを返却する。
	 * @return Transaction制御フラグ
	 */
	@Override
	protected boolean isTransactionalFunction() {
		return isTransactionalFunction;
	}

	/**
	 * クラス名を返却する。
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
	public EdiJuchuDataJusin_InitFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		super(mapping, form, request, response);
	}

	/**
	 * 初期処理を実行する。<br />
	 *
	 * @return Forward情報
	 * @throws KitException
	 */
	@Override
	public String execute_() throws KitException {

		String sForwardPage = FORWARD_SUCCESS;

		/* ---------------------------------------------------
		 * XXX:業務画面で使用する選択リストを
		 * ここで作成してセッションに格納する
		 * ※ セッションキーは、Interfaceで管理すること。
		 -------------------------------------------------- */

		// ★★デフォルトフラグリストを作成する
		MstCdmeisyMaster_SearchService searchServ = new MstCdmeisyMaster_SearchService(
				getComUserSession(), getDatabase(),isTransaction(), getActionErrors());
		// -->import static fb.com.IKitComConst.*; 内で定義されている区分名を指定

		searchServ.search(KBN_DEFAULT_FL);
		// -->import.javaで指定したデフォルトフラグリストのキーをセットセッションで使う
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_DEFAULT_FL, new KitSelectList(searchServ.getPbsRecords()));
		}

		// ★★受信先区分リストを作成する
		searchServ.search(KBN_EDI_JYUCYU_PARTNER);
		if (searchServ.getPbsRecords() != null) {
				setSession(KEY_SS_KBN_EDI_JYUCYU_PARTNER , new KitSelectList(searchServ.getPbsRecords()));
		}


		/* ---------------------------------------------------
		 * XXX:検索部の項目に初期値を指定する場合、
		 * ここで指定する
		 -------------------------------------------------- */

		EdiJuchuDataJusin_SearchForm searchForm = new EdiJuchuDataJusin_SearchForm();

		// 検索フォームに細工をしたら、セッションに格納する
		setSession(KEY_SS_SEARCHFORM, searchForm);

		EdiJuchuDataJusin_EditForm editForm = new EdiJuchuDataJusin_EditForm();
		editForm.setMode(MODE_EDIT);
		editForm.setPreMode(MODE_SEARCH);
		editForm.setEditable(IS_EDITABLE_TRUE);


		setRequest(KEY_SS_EDITFORM, editForm);

		// 次画面へ遷移する
		return sForwardPage;
	}

}