package kit.edi.JuchuDataJusin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitAction;

/**
 * 99905_1_1　受注データ受信
 *
 * @author Tuneduka
 *
 */
public final class EdiJuchuDataJusin_InitAction
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
		EdiJuchuDataJusin_InitFunction func_ = new EdiJuchuDataJusin_InitFunction(mapping, form, request, response);
		return func_.execute();
	}
}
