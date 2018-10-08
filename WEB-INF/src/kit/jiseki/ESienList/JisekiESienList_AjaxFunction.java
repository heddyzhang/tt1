package kit.jiseki.ESienList;


import static kit.jiseki.ESienList.IJisekiESienList.XML_TAG;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComUserSession;
import fb.com.Records.FbMastrOrositenRecord;
import fb.com.Records.FbMastrSyuhantenRecord;
import fb.inf.KitFunction;
import fb.inf.pbs.PbsUtil;
import kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroupMaster;

/**
 * 営業支援実績一覧のAjaxファンクション
 * @author t_kimura
 *
 */
public class JisekiESienList_AjaxFunction extends KitFunction {

	/** serialVersionUID  */
	private static final long serialVersionUID = 8409650159505697099L;

	/** クラス名 */
	private static String className__ = JisekiESienList_AjaxFunction.class.getName();

	/**
	 * コンストラクタ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 */
	public JisekiESienList_AjaxFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		super(mapping, form, request, response);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#execute_()
	 */
	@Override
	public String execute_() throws Exception {

		JisekiESienList_AjaxForm ajaxForm = (JisekiESienList_AjaxForm)form_;

		// XML出力
		writeXml(ajaxForm);

		return FORWARD_SUCCESS;
	}

	/**
	 * レスポンスへXMLを出力する
	 * @param ajaxForm Ajaxフォーム
	 * @throws Exception
	 */
	private void writeXml(JisekiESienList_AjaxForm ajaxForm) throws Exception {
		StringBuilder xml = new StringBuilder();

		xml.append("<" + XML_TAG + ">\n");

		// xmlタグ作成
		xml.append(createXmlTag(ajaxForm));

		xml.append("</" + XML_TAG + ">\n");

		write(xml.toString());
	}

	/**
	 * xmlタグ作成
	 * @param ajaxForm Ajaxフォーム
	 * @return xmlタグ
	 */
	private String createXmlTag(JisekiESienList_AjaxForm ajaxForm) {
		// ユーザセッション
		ComUserSession comUserSession = getComUserSession();

		// チェックサービス
		JisekiESienList_CheckService checkService = new JisekiESienList_CheckService(
				comUserSession, getDatabase(), isTransaction(), getActionErrors());

		if(!PbsUtil.isEmpty(ajaxForm.getTokuyakuCd())) {
			// 特約用xmlタグ作成
			return createXmlTagForTokuyakuCd(checkService, comUserSession, ajaxForm.getTokuyakuCd());
		} else if(!PbsUtil.isEmpty(ajaxForm.getSounisakiCd())) {
			// 送荷先用xmlタグ作成
			return createXmlTagForSounisakiCd(checkService, comUserSession, ajaxForm.getSounisakiCd());
		} else if(!PbsUtil.isEmpty(ajaxForm.getSyuhantenCd())) {
			// 酒販店用xmlタグ作成
			return createXmlTagForSyuhantenCd(checkService, comUserSession, ajaxForm.getSyuhantenCd());
		} else if(!PbsUtil.isEmpty(ajaxForm.getTorokuCd())) {
			// 登録コード用xmlタグ作成
			return createXmlTagForTorokuCd(checkService, comUserSession, ajaxForm.getTorokuCd(), ajaxForm.getSyukeiNaiyo());
		} else {
			throw new RuntimeException();
		}
	}

	/**
	 * 特約用xmlタグ作成
	 * @param checkService チェックサービス
	 * @param comUserSession ユーザセッション
	 * @param tokuyakuCd 特約CD
	 * @return xmlタグ
	 */
	private String createXmlTagForTokuyakuCd(JisekiESienList_CheckService checkService, ComUserSession comUserSession, String tokuyakuCd) {
		// 卸店マスタ取得
		FbMastrOrositenRecord record = checkService.getMastrOrositenRecord(comUserSession.getKaisyaCd(), tokuyakuCd);
		// マスタ存在チェック
		return "<tokuyakuNm>" + checkService.getMstExistValue(record, record.getTenNm2Jisya()) + "</tokuyakuNm>\n";
	}

	/**
	 * 送荷先用xmlタグ作成
	 * @param checkService チェックサービス
	 * @param comUserSession ユーザセッション
	 * @param sounisakiCd 送荷先CD
	 * @return xmlタグ
	 */
	private String createXmlTagForSounisakiCd(JisekiESienList_CheckService checkService, ComUserSession comUserSession, String sounisakiCd) {
		// 卸店マスタ取得
		FbMastrOrositenRecord record = checkService.getMastrOrositenRecord(comUserSession.getKaisyaCd(), sounisakiCd);
		// マスタ存在チェック
		return "<sounisakiNm>" + checkService.getMstExistValue(record, record.getTenNm2Jisya()) + "</sounisakiNm>\n";
	}

	/**
	 * 酒販店用xmlタグ作成
	 * @param checkService チェックサービス
	 * @param comUserSession ユーザセッション
	 * @param syuhantenCd 酒販店CD
	 * @return xmlタグ
	 */
	private String createXmlTagForSyuhantenCd(JisekiESienList_CheckService checkService, ComUserSession comUserSession, String syuhantenCd) {
		// 酒販店マスタ
		FbMastrSyuhantenRecord record = checkService.getMastrSyuhantenRecord(syuhantenCd);
		// マスタ存在チェック
		return "<syuhantenNm>" + checkService.getMstExistValue(record, record.getTenNmYago()) + "</syuhantenNm>\n";
	}

	/**
	 * 登録コード用xmlタグ作成
	 * @param checkService チェックサービス
	 * @param comUserSession ユーザセッション
	 * @param torokuCd 登録CD
	 * @param syukeiNaiyo 集計内容
	 * @return xmlタグ
	 */
	private String createXmlTagForTorokuCd(JisekiESienList_CheckService checkService, ComUserSession comUserSession, String torokuCd, String syukeiNaiyo) {
		IJisekiESienListTorokuGroupMaster torokuGroupMaster = checkService.getTorokuGroupMasterBySyukeiNaiyo((syukeiNaiyo));
		return torokuGroupMaster.createXmlTagByTorokuCd(checkService, comUserSession, torokuCd);
	}

	/**
	 * レスポンスへXMLを出力する
	 * @param xml xml
	 * @throws Exception
	 */
	protected void write(String xml) throws Exception {
		response_.setContentType("text/xml;charset=UTF-8");
		response_.addHeader("Pragma", "no-cache");
		response_.addHeader("Cache-Control", "no-cache");
		response_.addDateHeader("Expires", 0);

		PrintWriter out = response_.getWriter();
		out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		out.println(xml.toString());
		out.flush();
		out.close();
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