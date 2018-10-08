package kit.edi.SyuhantenReceiveUpdate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitAction;

/**
 * 99915_1_1　酒販店ﾏｽﾀｰ受信更新
 *
 * @author Tuneduka
 *
 */
public final class EdiSyuhantenReceiveUpdate_InitAction
	extends KitAction {

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
		EdiSyuhantenReceiveUpdate_InitFunction func_ = new EdiSyuhantenReceiveUpdate_InitFunction(mapping, form, request, response);
		return func_.execute();
	}
}
