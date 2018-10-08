package kit.jiseki.ESienList;

import fb.inf.KitEditForm;

/**
 * 営業支援実績一覧の明細フォーム
 * @author t_kimura
 *
 */
public class JisekiESienList_EditForm extends KitEditForm {

	/** シリアルID */
	private static final long serialVersionUID = -1960432749777864429L;

	/** システム日時 */
	private String sysDt;

	/**
	 * @return sysDt
	 */
	public String getSysDt() {
		return sysDt;
	}

	/**
	 * @param sysDt セットする sysDt
	 */
	public void setSysDt(String sysDt) {
		this.sysDt = sysDt;
	}

}
