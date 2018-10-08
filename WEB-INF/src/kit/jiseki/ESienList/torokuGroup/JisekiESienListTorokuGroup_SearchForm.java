package kit.jiseki.ESienList.torokuGroup;

import static fb.inf.IKitConst.MODE_INIT;

import fb.inf.KitSearchForm;
import fb.inf.pbs.PbsUtil;

/**
 * 各種登録グループの検索フォーム
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroup_SearchForm extends KitSearchForm {

	/** serialVersionUID */
	private static final long serialVersionUID = 3318143065557534712L;

	/** 作業者コード */
	private String sgyosyaCd;
	/** 作業者名 */
	private String sgyosyaNm;
	/** 登録パターン */
	private String torokuPtn;
	/** 登録マスタ */
	private String torokuMaster;
	/** 集計内容 */
	private String syukeiNaiyo;
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
	 * @return sgyosyaNm
	 */
	public String getSgyosyaNm() {
		return sgyosyaNm;
	}
	/**
	 * @param sgyosyaNm セットする sgyosyaNm
	 */
	public void setSgyosyaNm(String sgyosyaNm) {
		this.sgyosyaNm = sgyosyaNm;
	}
	/**
	 * @return torokuPtn
	 */
	public String getTorokuPtn() {
		return torokuPtn;
	}
	/**
	 * @param torokuPtn セットする torokuPtn
	 */
	public void setTorokuPtn(String torokuPtn) {
		this.torokuPtn = torokuPtn;
	}
	/**
	 * @return torokuMaster
	 */
	public String getTorokuMaster() {
		return torokuMaster;
	}
	/**
	 * @param torokuMaster セットする torokuMaster
	 */
	public void setTorokuMaster(String torokuMaster) {
		this.torokuMaster = torokuMaster;
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

	public boolean isNotInit() {
		return !PbsUtil.isEqual(getMode(), MODE_INIT);
	}
}
