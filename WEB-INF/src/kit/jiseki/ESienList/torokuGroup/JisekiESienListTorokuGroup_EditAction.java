package kit.jiseki.ESienList.torokuGroup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.inf.KitAction;
import fb.inf.pbs.PbsUtil;

/**
 * 各種登録グループの編集アクション
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroup_EditAction extends KitAction {

	/* (非 Javadoc)
	 * @see fb.inf.KitAction#execute_(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String execute_(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// 編集
		JisekiESienListTorokuGroup_EditFunction editFunc_ = new JisekiESienListTorokuGroup_EditFunction(mapping, form, request, response);
		String ret = editFunc_.execute();
		// 正常終了時は再検索
		if(PbsUtil.isEqual(FORWARD_SUCCESS, ret)) {
			JisekiESienListTorokuGroup_SearchFunction searchFunc_ = new JisekiESienListTorokuGroup_SearchFunction(mapping, form, request, response);
			return searchFunc_.execute();
		} else {
			return ret;
		}
	}
}
