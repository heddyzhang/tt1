package kit.jiseki.CategoryList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitAction;

public final class JisekiCategoryList_InitAction extends KitAction {

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

		JisekiCategoryList_InitFunction func_ = new JisekiCategoryList_InitFunction(mapping, form,
				request, response);

		return func_.execute();
	}
}
