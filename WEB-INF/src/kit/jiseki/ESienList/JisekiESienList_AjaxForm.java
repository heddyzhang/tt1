package kit.jiseki.ESienList;

import fb.inf.KitSearchForm;

/**
 * 営業支援実績一覧のAjaxフォーム
 * @author t_kimura
 *
 */
public class JisekiESienList_AjaxForm  extends KitSearchForm {

	/** serialVersionUID */
	private static final long serialVersionUID = 2118105909966151052L;
	/** 特約 */
	private String tokuyakuCd;
	/** 送荷先 */
	private String sounisakiCd;
	/** 酒販店 */
	private String syuhantenCd;
	/** 登録コード */
	private String torokuCd;
	/** 集計内容 */
	private String syukeiNaiyo;

	/**
	 * @return tokuyakuCd
	 */
	public String getTokuyakuCd() {
		return tokuyakuCd;
	}
	/**
	 * @param tokuyakuCd セットする tokuyakuCd
	 */
	public void setTokuyakuCd(String tokuyakuCd) {
		this.tokuyakuCd = tokuyakuCd;
	}
	/**
	 * @return sounisakiCd
	 */
	public String getSounisakiCd() {
		return sounisakiCd;
	}
	/**
	 * @param sounisakiCd セットする sounisakiCd
	 */
	public void setSounisakiCd(String sounisakiCd) {
		this.sounisakiCd = sounisakiCd;
	}
	/**
	 * @return syuhantenCd
	 */
	public String getSyuhantenCd() {
		return syuhantenCd;
	}
	/**
	 * @param syuhantenCd セットする syuhantenCd
	 */
	public void setSyuhantenCd(String syuhantenCd) {
		this.syuhantenCd = syuhantenCd;
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
	/**
	 * @return syukeiNaiyo
	 */
	public String getSyukeiNaiyo() {
		return syukeiNaiyo;
	}
	/**
	 * @param syukeiNaiyo セットする syukeiNaiyo
	 */
	public void setSyukeiNaiyo(String syukeiNaiyo) {
		this.syukeiNaiyo = syukeiNaiyo;
	}
}
