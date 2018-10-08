package kit.jiseki.ESienList.torokuGroup;

import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.KEY_SS_TOROKU_GROUP_MASTER;
import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.XML_TAG_TOROKU_GROUP;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComUserSession;
import fb.com.Records.FbMastrSgyosyaRecord;
import fb.inf.pbs.PbsUtil;
import kit.jiseki.ESienList.JisekiESienList_AjaxFunction;

/**
 * 各種登録グループのAjaxファンクション
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroup_AjaxFunction extends JisekiESienList_AjaxFunction {

	/** serialVersionUID  */
	private static final long serialVersionUID = 2570904836052218262L;

	/** クラス名 */
	private static String className__ = JisekiESienListTorokuGroup_AjaxFunction.class.getName();

	/**
	 * コンストラクタ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 */
	public JisekiESienListTorokuGroup_AjaxFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		super(mapping, form, request, response);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#execute_()
	 */
	@Override
	public String execute_() throws Exception {

		JisekiESienListTorokuGroup_AjaxForm ajaxForm = (JisekiESienListTorokuGroup_AjaxForm)form_;

		// XMLタグ出力
		writeXml(ajaxForm);

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
	 * レスポンスへXMLを出力する
	 * @param ajaxForm Ajaxフォーム
	 * @throws Exception
	 */
	private void writeXml(JisekiESienListTorokuGroup_AjaxForm ajaxForm) throws Exception {
		StringBuilder xml = new StringBuilder();

		xml.append("<" + XML_TAG_TOROKU_GROUP + ">\n");

		// xmlタグ作成
		xml.append(createXmlTag(ajaxForm));

		xml.append("</" + XML_TAG_TOROKU_GROUP + ">\n");

		write(xml.toString());
	}

	/**
	 * xmlタグ作成
	 * @param ajaxForm Ajaxフォーム
	 * @return xmlタグ
	 */
	private String createXmlTag(JisekiESienListTorokuGroup_AjaxForm ajaxForm) {
		ComUserSession comUserSession = getComUserSession();

		// チェックサービス
		JisekiESienListTorokuGroup_CheckService checkService = new JisekiESienListTorokuGroup_CheckService(
				comUserSession, getDatabase(),isTransaction(), getActionErrors());

		if(!PbsUtil.isEmpty(ajaxForm.getSgyosyaCd())) {
			// 作業者コードが設定されている場合は作業者用タグを設定
			return getTagBySgyosyaCd(checkService, comUserSession, ajaxForm.getSgyosyaCd());
		} else {
			// 登録コードが設定されている場合は各種マスタ用タグを設定
			IJisekiESienListTorokuGroupMaster torokuGroupMaster = (IJisekiESienListTorokuGroupMaster)getSession(KEY_SS_TOROKU_GROUP_MASTER);
			return getTagByTorokuCd(checkService, comUserSession, torokuGroupMaster, ajaxForm.getTorokuCd());
		}
	}

	/**
	 * 作業者タグを取得する
	 * @param checkService チェックサービス
	 * @param comUserSession ログインユーザセッション情報
	 * @param sgyosyaCd 作業者コード
	 * @return 作業者タグ
	 */
	private String getTagBySgyosyaCd(JisekiESienListTorokuGroup_CheckService checkService, ComUserSession comUserSession, String sgyosyaCd) {
		// 認証マスター取得
		FbMastrSgyosyaRecord record = checkService.getMastrSgyosyaRecord(comUserSession.getKaisyaCd(), sgyosyaCd);
		// マスタ存在チェック
		return "<sgyosyaNm>" + checkService.getMstExistValue(record, record.getSgyosyaMeiRn()) + "</sgyosyaNm>\n";
	}

	/**
	 * 各種登録グループタグを取得する
	 * @param checkService チェックサービス
	 * @param comUserSession ログインユーザセッション情報
	 * @param torokuGroupMaster 登録グループマスタ
	 * @param torokuCd 登録コード
	 * @return 登録グループタグ
	 */
	private String getTagByTorokuCd(JisekiESienListTorokuGroup_CheckService checkService, ComUserSession comUserSession, IJisekiESienListTorokuGroupMaster torokuGroupMaster, String torokuCd) {
		return torokuGroupMaster.createXmlTagByTorokuCd(checkService, comUserSession, torokuCd);
	}
}
