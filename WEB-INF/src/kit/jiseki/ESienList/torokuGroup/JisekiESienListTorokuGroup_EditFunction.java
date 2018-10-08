package kit.jiseki.ESienList.torokuGroup;

import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.KEY_SS_SEARCH;
import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.KEY_SS_TOROKU_GROUP_MASTER;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComUserSession;
import fb.inf.KitFunction;

/**
 * 各種登録グループの編集ファンクション
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroup_EditFunction extends KitFunction {

	/** serialVersionUID  */
	private static final long serialVersionUID = 8174159742797643383L;

	/** クラス名 */
	private static String className__ = JisekiESienListTorokuGroup_EditFunction.class.getName();

	/**
	 * コンストラクタ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 */
	public JisekiESienListTorokuGroup_EditFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super(mapping, form, request, response);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#execute_()
	 */
	@Override
	protected String execute_() throws Exception {

		ComUserSession comUserSession = getComUserSession();

		// フォーム取得
		JisekiESienListTorokuGroup_EditForm editForm = (JisekiESienListTorokuGroup_EditForm) form_;
		JisekiESienListTorokuGroup_SearchForm searchForm = (JisekiESienListTorokuGroup_SearchForm) getSession(KEY_SS_SEARCH);
		// レコードのエラークリア
		editForm.getTorokuGroupList().clearRecordError();

		// チェック処理
		if(!validate(comUserSession, editForm)) {
			return FORWARD_FAIL;
		}

		JisekiESienListTorokuGroup_UpdateService updateService = new JisekiESienListTorokuGroup_UpdateService(comUserSession, getDatabase(),isTransaction(), getActionErrors());

		// 更新
		// コミット・ロールバックは基底側が実施
		updateService.execute(searchForm, editForm);

		return FORWARD_SUCCESS;
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#isTransactionalFunction()
	 */
	@Override
	protected boolean isTransactionalFunction() {
		return true;
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#getFunctionName()
	 */
	@Override
	protected String getFunctionName() {
		return className__;
	}

	/**
	 * チェック処理
	 * @param comUserSession ログインユーザセッション情報
	 * @param editForm 編集フォーム
	 * @return チェック結果
	 */
	private boolean validate(ComUserSession comUserSession, JisekiESienListTorokuGroup_EditForm editForm) {
		JisekiESienListTorokuGroup_CheckService checkService = new JisekiESienListTorokuGroup_CheckService(
				comUserSession, getDatabase(),isTransaction(), getActionErrors());
		IJisekiESienListTorokuGroupMaster torokuGroupMaster = (IJisekiESienListTorokuGroupMaster)getSession(KEY_SS_TOROKU_GROUP_MASTER);
		// 登録名設定
		torokuGroupMaster.setTorokuNms(checkService, comUserSession, editForm.getTorokuGroupList());
		// 明細の登録コードチェック
		return checkService.checkTorokuCdList(comUserSession, torokuGroupMaster, editForm.getTorokuGroupList(), false);
	}
}
