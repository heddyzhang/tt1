package kit.edi.SyukkaAnnai;

import static fb.inf.IKitConst.REQ_TYPE_EMPTY;
import fb.inf.KitEditForm;

/**
 * 99901_1_1　出荷案内送信
 *
 * @author Tuneduka
 *
 */
public class EdiSyukkaAnnai_EditForm extends KitEditForm {

	/**シリアルID */
	private static final long serialVersionUID = -4762322492672493302L;

	/*
	 * 画面固有インスタンス変数
	 *

	 */
	private String fromDt = null;
	private String toDt = null;
	private String sousinsakiKbn = null;
	private String sakuseitaisyouKbn = null;

	/* --------------------------------------------------------
	 * 各フィールドのアクセサを作成する
	 ------------------------------------------------------- */
	/**
	 * 送信先区分を返します。
	 * @return sousinsakiKbn
	 */
	public String getSousinsakiKbn() {
		return sousinsakiKbn;
	}

	/**
	 * 送信先区分をレコードに格納します。
	 * @param sousinsakiKbn
	 */
	public void setSousinsakiKbn(String sousinsakiKbn) {
		this.sousinsakiKbn = sousinsakiKbn;
	}


	/**
	 * 作成対象先を返します。
	 * @return sakuseitaisyouKbn
	 */
	public String getSakuseitaisyouKbn() {
		return sakuseitaisyouKbn;
	}

	/**
	 * 作成対象先をレコードに格納します。
	 * @param sakuseitaisyouKbn
	 */
	public void setSakuseitaisyouKbn(String sakuseitaisyouKbn) {
		this.sakuseitaisyouKbn = sakuseitaisyouKbn;
	}

	/**
	 * 初期画面用にセットする
	 */
	public void init() {
		// リクエストタイプを初期化
		this.setReqType(REQ_TYPE_EMPTY);

	}

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
	 * @return fromDt
	 */
	public String getToDt() {
		return toDt;
	}

	/**
	 * @param fromDt セットする fromDt
	 */
	public void setToDt(String toDt) {
		this.toDt = toDt;
	}


}
