package kit.jiseki.ESienList;

import static fb.com.IKitComConstHM.IS_EDITABLE_TRUE;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_EDIT;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_SEARCHLIST;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitFunction;
import fb.inf.pbs.PbsUtil;

/**
 * 営業支援実績一覧の検索結果ファンクション
 * @author t_kimura
 *
 */
public class JisekiESienList_SearchResultFunction extends KitFunction {

	/** serialVersionUID  */
	private static final long serialVersionUID = 4849184362989725148L;

	/** クラス名 */
	private static String className__ = JisekiESienList_SearchResultFunction.class.getName();

	/** ページ毎のレコード数 */
	private static final String MAX_SU = PbsUtil.getMessageResourceString("com.jisekiESienList.maxSu");

	/**
	 * コンストラクタ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 */
	public JisekiESienList_SearchResultFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		super(mapping, form, request, response);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#execute_()
	 */
	@Override
	protected String execute_() throws Exception {
		// セッションから検索結果を取得
		JisekiESienListList list = (JisekiESienListList)getSession(KEY_SS_SEARCHLIST);

		// ページ設定
		list.setOffset(0);
		list.setRecordsParPage(Integer.parseInt(MAX_SU));

		// EditForm
		JisekiESienList_EditForm editForm = new JisekiESienList_EditForm();
		editForm.setEditable(IS_EDITABLE_TRUE);
		// 検索日時設定
		editForm.setSysDt(PbsUtil.getCurrentDateTime());

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
