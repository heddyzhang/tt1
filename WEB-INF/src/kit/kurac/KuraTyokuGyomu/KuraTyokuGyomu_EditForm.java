package kit.kurac.KuraTyokuGyomu;

import fb.inf.KitEditForm;

/**
 * 96105_1_1 蔵直業務
 *
 * @author kurosaki
 *
 */
public class KuraTyokuGyomu_EditForm extends KitEditForm {

	/** シリアルID */
	private static final long serialVersionUID = -6954660319291150243L;

	private String fromDt = null;
	private String toDt = null;
	private String hassouDt = null;

	/**
	 * @return fromDt
	 */
	public String getFromDt() {
		return fromDt;
	}

	/**
	 * @param fromDt セットする fromDt
	 */
	public void setFromDt(String fromDt) {
		this.fromDt = fromDt;
	}

	/**
	 * @return toDt
	 */
	public String getToDt() {
		return toDt;
	}

	/**
	 * @param toDt セットする toDt
	 */
	public void setToDt(String toDt) {
		this.toDt = toDt;
	}

	/**
	 * @return hassouDt
	 */
	public String getHassouDt() {
		return hassouDt;
	}

	/**
	 * @param hassouDt セットする hassouDt
	 */
	public void setHassouDt(String hassouDt) {
		this.hassouDt = hassouDt;
	}

}
