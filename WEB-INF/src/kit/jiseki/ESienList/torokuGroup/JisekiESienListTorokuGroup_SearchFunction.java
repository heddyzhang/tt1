package kit.jiseki.ESienList.torokuGroup;

import static fb.com.IKitComConstHM.IS_EDITABLE_TRUE;
import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.KEY_SS_EDIT;
import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.KEY_SS_SEARCH;
import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.KEY_SS_TOROKU_GROUP_MASTER;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComUserSession;
import fb.com.Records.FbMastrSgyosyaRecord;
import fb.inf.KitFunction;
import fb.inf.pbs.PbsUtil;

/**
 * 各種登録グループの検索ファンクション
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroup_SearchFunction extends KitFunction {

	/** serialVersionUID  */
	private static final long serialVersionUID = 646240858346279101L;

	/** クラス名 */
	private static String className__ = JisekiESienListTorokuGroup_SearchFunction.class.getName();

	/**
	 * コンストラクタ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 */
	public JisekiESienListTorokuGroup_SearchFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super(mapping, form, request, response);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#execute_()
	 */
	@Override
	protected String execute_() throws Exception {
		//セッションクリア
		removeSession(KEY_SS_EDIT);

		ComUserSession comUserSession = getComUserSession();

		JisekiESienListTorokuGroup_SearchForm searchForm;

		if(form_ instanceof JisekiESienListTorokuGroup_SearchForm) {
			// 検索処理時
			searchForm = (JisekiESienListTorokuGroup_SearchForm) form_;
			// モードを初期状態へ
			searchForm.setMode(MODE_INIT);
			// チェック
			if(!validate(comUserSession, searchForm)){
				return FORWARD_FAIL;
			}
		} else {
			// 更新後の再検索時はセッションから取得する
			searchForm = (JisekiESienListTorokuGroup_SearchForm) getSession(KEY_SS_SEARCH);
		}

		// モードを検索へ設定

		// 検索
		searchForm.setMode(MODE_SEARCH);
		JisekiESienListTorokuGroup_SearchService searchService = new JisekiESienListTorokuGroup_SearchService(comUserSession, getDatabase(),isTransaction(), getActionErrors());
		JisekiESienListTorokuGroupList torokuGroupList = searchService.execute(searchForm, (IJisekiESienListTorokuGroupMaster)getSession(KEY_SS_TOROKU_GROUP_MASTER));

		// 検索結果を編集フォームへ設定
		JisekiESienListTorokuGroup_EditForm editForm = new JisekiESienListTorokuGroup_EditForm();
		editForm.setEditable(IS_EDITABLE_TRUE);
		editForm.setTorokuGroupList(torokuGroupList);

		// セッションへ設定
		setSession(KEY_SS_SEARCH, searchForm);
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

	/**
	 * チェック処理
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchForm 検索フォーム
	 * @return チェック結果
	 */
	private boolean validate(ComUserSession comUserSession, JisekiESienListTorokuGroup_SearchForm searchForm) {
		JisekiESienListTorokuGroup_CheckService checkService = new JisekiESienListTorokuGroup_CheckService(comUserSession, getDatabase(),isTransaction(), getActionErrors());

		// 作業者コードの名称が取得できなかった場合に、設定する
		if(!PbsUtil.isEmpty(searchForm.getSgyosyaCd()) && PbsUtil.isEmpty(searchForm.getSgyosyaNm())) {
			FbMastrSgyosyaRecord record = checkService.getMastrSgyosyaRecord(comUserSession.getKaisyaCd(), searchForm.getSgyosyaCd());
			searchForm.setSgyosyaNm(checkService.getMstExistValue(record, record.getSgyosyaMeiRn()));
		}

		// 作業者コードのチェック
		if(!checkService.checkSgyosyaCd(comUserSession.getKaisyaCd(), searchForm.getSgyosyaCd())) {
			return false;
		}
		return true;
	}
}
