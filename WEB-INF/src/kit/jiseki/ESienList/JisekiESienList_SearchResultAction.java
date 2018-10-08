package kit.jiseki.ESienList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitAction;

/**
 * 営業支援実績一覧の検索結果アクション
 * @author t_kimura
 *
 */
public class JisekiESienList_SearchResultAction extends KitAction {

	/* (非 Javadoc)
	 * @see fb.inf.KitAction#execute_(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String execute_(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		JisekiESienList_SearchResultFunction func_ = new JisekiESienList_SearchResultFunction(mapping, form, request, response);
		return func_.execute();
	}
}
