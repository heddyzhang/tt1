package kit.jiseki.ESienList;

import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_EDIT;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_SEARCHLIST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitFunction;

/**
 * 営業支援実績一覧の明細ファンクション
 * @author t_kimura
 *
 */
public class JisekiESienList_EditFunction extends KitFunction {

	/** serialVersionUID  */
	private static final long serialVersionUID = -7859813789728402129L;
	/** クラス名 */
	private static String className__ = JisekiESienList_EditFunction.class.getName();

	/**
	 * コンストラクタ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 */
	public JisekiESienList_EditFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		super(mapping, form, request, response);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#execute_()
	 */
	@Override
	protected String execute_() throws Exception {
		// フォーム取得
		JisekiESienList_EditForm editForm = (JisekiESienList_EditForm) form_;
		// セッションから検索結果を取得
		JisekiESienListList list = (JisekiESienListList)getSession(KEY_SS_SEARCHLIST);
		// ページ設定
		list.setPageRequest(editForm.getReqType());
		// セッションへ設定
		setSession(KEY_SS_EDIT, editForm);
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
