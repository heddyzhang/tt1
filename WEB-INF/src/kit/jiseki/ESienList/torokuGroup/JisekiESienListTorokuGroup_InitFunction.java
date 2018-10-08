package kit.jiseki.ESienList.torokuGroup;

import static fb.com.IKitComConstHM.IS_EDITABLE_TRUE;
import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.KEY_SS_EDIT;
import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.KEY_SS_SEARCH;
import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.KEY_SS_TOROKU_GROUP_MASTER;
import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.TOROKU_PTN_A;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComUserSession;
import fb.inf.KitFunction;

/**
 * 各種登録グループの初期表示ファンクション
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroup_InitFunction extends KitFunction {

	/** serialVersionUID  */
	private static final long serialVersionUID = 180086702036696958L;

	/** クラス名 */
	private static String className__ = JisekiESienListTorokuGroup_InitFunction.class.getName();

	/**
	 * コンストラクタ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 */
	public JisekiESienListTorokuGroup_InitFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		super(mapping, form, request, response);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#execute_()
	 */
	@Override
	protected String execute_() throws Exception {
		//セッションクリア
		removeSession(KEY_SS_TOROKU_GROUP_MASTER);
		removeSession(KEY_SS_SEARCH);
		removeSession(KEY_SS_EDIT);

		ComUserSession comUserSession = getComUserSession();

		JisekiESienListTorokuGroup_SearchForm searchForm = (JisekiESienListTorokuGroup_SearchForm) form_;
		// 検索部を初期化する
		searchForm.setSgyosyaCd(comUserSession.getSgyosyaCd());
		searchForm.setSgyosyaNm(comUserSession.getSgyosyaMeiRn());
		searchForm.setTorokuPtn(TOROKU_PTN_A);
		searchForm.setEditable(IS_EDITABLE_TRUE);
		searchForm.setMode(MODE_INIT);

		// セッションへ設定
		setSession(KEY_SS_TOROKU_GROUP_MASTER, getTorokuGroupMaster(comUserSession, searchForm));
		setSession(KEY_SS_SEARCH, searchForm);
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

	/**
	 * 登録グループマスタクラスを取得する
	 * @param comUserSession ログインユーザのセッション情報
	 * @param searchForm 検索フォーム
	 * @return 登録グループマスタクラス
	 */
	private IJisekiESienListTorokuGroupMaster getTorokuGroupMaster(ComUserSession comUserSession, JisekiESienListTorokuGroup_SearchForm searchForm) {
		// チェックサービス
		JisekiESienListTorokuGroup_CheckService checkService = new JisekiESienListTorokuGroup_CheckService(
				comUserSession, getDatabase(),isTransaction(), getActionErrors());
		// 集計内容から登録マスタの値を設定する。
		searchForm.setTorokuMaster(checkService.getTorokuMasterBySyukeiNaiyo(searchForm.getSyukeiNaiyo()));
		return checkService.getTorokuGroupMaster(searchForm.getTorokuMaster());
	}
}
