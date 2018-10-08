package kit.jiseki.AriaList;

import static kit.jiseki.AriaList.IJisekiAriaList.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitFunction;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsUtil;

/**
 * 初期表示処理の実装クラスです
 */
public class JisekiAriaList_InitFunction extends KitFunction {

	/**
	 *
	 */
	private static final long serialVersionUID = 4603217942167239281L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JisekiAriaList_InitFunction.class.getName();

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
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpサーブレットRequest
	 * @param response
	 *            HttpサーブレットResponse
	 */
	public JisekiAriaList_InitFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		super(mapping, form, request, response);
	}

	/**
	 * 初期処理を実行する。<br>
	 * 初期リストを取得する
	 *
	 * @return Forward情報
	 * @throws KitException
	 * @Override
	 */

	public String execute_() throws KitException {

		String sForwardPage = FORWARD_SUCCESS;

		// 画面を初期化
		JisekiAriaList_SearchForm searchForm = new JisekiAriaList_SearchForm();
		// 日付を初期化
		searchForm.setShiteibi(PbsUtil.getCurrentDate());
		// 表示単位
		searchForm.setHyoujiTanyi(RDO_TANI_KINGAKU);
		// 検索日時
		searchForm.setKensakuNiji("");

		// 検索フォームをセッションに格納する
		setSession(KEY_SS_SEARCHFORM, searchForm);

		// 次画面へ遷移する
		return sForwardPage;
	}

}