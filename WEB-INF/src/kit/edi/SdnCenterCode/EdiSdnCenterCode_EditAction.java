package kit.edi.SdnCenterCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitAction;

/**
 * 99911_1_1　SDNｾﾝﾀｰｺｰﾄﾞ送受信
 *
 * @author Tuneduka
 *
 */
public final class EdiSdnCenterCode_EditAction
	extends KitAction{

	/**
	 * Function処理を実行する。
	 *
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 * @return Forward情報
	 */
	@Override
	protected String execute_(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		EdiSdnCenterCode_EditFunction func_ = new EdiSdnCenterCode_EditFunction(mapping, form, request, response);
		return func_.execute();
	}
}
