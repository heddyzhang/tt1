package kit.jiseki.AriaList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import fb.inf.KitAction;

public final class JisekiAriaList_SearchAction extends KitAction {

	/**
	 * Function処理を実行する。
	 *
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpサーブレットRequest
	 * @param response
	 *            HttpサーブレットResponse
	 *
	 * @return Forward情報
	 */
	@Override
	protected String execute_(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		JisekiAriaList_SearchFunction func_ = new JisekiAriaList_SearchFunction(mapping, form,
				request, response);

		return func_.execute();
	}
}
