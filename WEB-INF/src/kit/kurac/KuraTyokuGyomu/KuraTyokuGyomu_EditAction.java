package kit.kurac.KuraTyokuGyomu;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitAction;

/**
 * 96105_1_1 蔵直業務
 *
 * @author kurosaki
 *
 */
public final class KuraTyokuGyomu_EditAction
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
		KuraTyokuGyomu_EditFunction func_ = new KuraTyokuGyomu_EditFunction(mapping, form, request, response);
		return func_.execute();
	}
}
