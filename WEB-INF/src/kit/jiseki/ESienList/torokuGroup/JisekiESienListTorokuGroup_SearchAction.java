package kit.jiseki.ESienList.torokuGroup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitAction;

/**
 * 各種登録グループの検索アクション
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroup_SearchAction extends KitAction {

	/* (非 Javadoc)
	 * @see fb.inf.KitAction#execute_(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String execute_(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		JisekiESienListTorokuGroup_SearchFunction func_ = new JisekiESienListTorokuGroup_SearchFunction(mapping, form, request, response);
		return func_.execute();
	}
}
