package kit.edi.HanbaiJiseki;

import static fb.com.IKitComConstHM.*;
import static kit.edi.HanbaiJiseki.IEdiHanbaiJiseki.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitFunction;
import fb.inf.exception.KitException;

/**
 * 99903_1_1　販売実績受信
 *
 * @author Tuneduka
 *
 */
public class EdiHanbaiJiseki_InitFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = 1069153774081757473L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = EdiHanbaiJiseki_InitFunction.class.getName();

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
	public EdiHanbaiJiseki_InitFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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

		EdiHanbaiJiseki_SearchForm searchForm = new EdiHanbaiJiseki_SearchForm();

		// 検索フォームをセッションに格納する
		setSession(KEY_SS_SEARCHFORM, searchForm);

		// 初期画面表示モードのセット
		EdiHanbaiJiseki_EditForm editForm = new EdiHanbaiJiseki_EditForm();
		editForm.setMode(MODE_EDIT);
		editForm.setPreMode(MODE_SEARCH);
		editForm.setEditable(IS_EDITABLE_TRUE);

		setRequest(KEY_SS_EDITFORM, editForm);

		// 次画面へ遷移する
		return sForwardPage;
	}

}