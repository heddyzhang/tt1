package kit.juchu.TuminiDenpyoHako;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitAction;

public final class JuchuTuminiDenpyoHako_ModalAction extends KitAction {

	/**
	 * Function処理を実行する。
	 *
	 * @param mapping  ActionMapping
	 * @param form     ActionForm
	 * @param request  HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 *
	 * @return Forward情報
	 */
	@Override
	protected String execute_(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		JuchuTuminiDenpyoHako_ModalFunction func_ = new JuchuTuminiDenpyoHako_ModalFunction(mapping, form, request, response);

		return func_.execute();
	}
}
