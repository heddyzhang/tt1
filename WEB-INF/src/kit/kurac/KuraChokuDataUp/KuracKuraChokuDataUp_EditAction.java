package kit.kurac.KuraChokuDataUp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitAction;

/**
 * 99901_1_1　出荷案内送信
 *
 * @author Tuneduka
 *
 */
public final class KuracKuraChokuDataUp_EditAction
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
		KuracKuraChokuDataUp_EditFunction func_ = new KuracKuraChokuDataUp_EditFunction(mapping, form, request, response);
		return func_.execute();
	}
}
