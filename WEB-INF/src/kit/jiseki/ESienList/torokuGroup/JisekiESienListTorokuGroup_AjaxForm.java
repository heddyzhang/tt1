package kit.jiseki.ESienList.torokuGroup;

import fb.inf.KitSearchForm;

/**
 * 各種登録グループのAjaxフォーム
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroup_AjaxForm extends KitSearchForm {

	/** serialVersionUID */
	private static final long serialVersionUID = 2093132715253114531L;

	/** 作業者コード */
	private String sgyosyaCd;

	/** 登録コード */
	private String torokuCd;

	/**
	 * @return sgyosyaCd
	 */
	public String getSgyosyaCd() {
		return sgyosyaCd;
	}

	/**
	 * @param sgyosyaCd セットする sgyosyaCd
	 */
	public void setSgyosyaCd(String sgyosyaCd) {
		this.sgyosyaCd = sgyosyaCd;
	}

	/**
	 * @return torokuCd
	 */
	public String getTorokuCd() {
		return torokuCd;
	}

	/**
	 * @param torokuCd セットする torokuCd
	 */
	public void setTorokuCd(String torokuCd) {
		this.torokuCd = torokuCd;
	}
}
