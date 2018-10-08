package kit.jiseki.ESienList;

import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_EDIT;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_SEARCHLIST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.exception.NoDataException;
import fb.inf.KitFunction;
import fb.inf.exception.DataNotFoundException;

/**
 * 営業支援実績一覧の検索ファンクション
 * @author t_kimura
 *
 */
public class JisekiESienList_SearchFunction extends KitFunction {

	/** serialVersionUID  */
	private static final long serialVersionUID = -1257636888655508562L;

	/** クラス名 */
	private static String className__ = JisekiESienList_SearchFunction.class.getName();

	/**
	 * コンストラクタ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 */
	public JisekiESienList_SearchFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		super(mapping, form, request, response);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#execute_()
	 */
	@Override
	protected String execute_() throws Exception {
		//セッションクリア
		removeSession(KEY_SS_SEARCHLIST);
		removeSession(KEY_SS_EDIT);

		// 検索フォーム
		JisekiESienList_SearchForm searchForm = (JisekiESienList_SearchForm) form_;
		// モード初期化
		searchForm.setMode(MODE_INIT);

		// チェックサービス
		JisekiESienList_CheckService checkService = new JisekiESienList_CheckService(getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// チェック処理
		if(!checkService.validateSearch(searchForm)) {
			return FORWARD_FAIL;
		}

		// 検索サービス
		JisekiESienList_SearchService searchService = new JisekiESienList_SearchService(getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		JisekiESienListList list;
		try {
			// 検索
			list = searchService.execute(searchForm);
		} catch (DataNotFoundException e) {
			// 検索結果0件
			throw new NoDataException();
		}

		// モード変更
		searchForm.setMode(MODE_SEARCH);

		// セッションへ検索結果を設定
		setSession(KEY_SS_SEARCHLIST, list);
		return FORWARD_SUCCESS;
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#isTransactionalFunction()
	 */
	@Override
	protected boolean isTransactionalFunction() {
		return false;
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#getFunctionName()
	 */
	@Override
	protected String getFunctionName() {
		return className__;
	}
}

