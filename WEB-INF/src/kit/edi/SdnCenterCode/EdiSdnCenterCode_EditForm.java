package kit.edi.SdnCenterCode;

import fb.inf.KitEditForm;

/**
 *99911_1_1　SDNｾﾝﾀｰｺｰﾄﾞ送受信
 *
 * @author Tuneduka
 *
 */
public class EdiSdnCenterCode_EditForm extends KitEditForm {

	/**シリアルID */
	private static final long serialVersionUID = 6444276710779005679L;

	private String orosiCenterCode = null;
	/**
	 * @return orosiCenterCcode
	 */
	public String getOrosiCenterCode() {
			return orosiCenterCode;
	}

	/**
	 * @param orosiCenterCcode
	 */
	public void setOrosiCenterCode(String orosiCenterCode) {
			this.orosiCenterCode = orosiCenterCode;
	}

}
