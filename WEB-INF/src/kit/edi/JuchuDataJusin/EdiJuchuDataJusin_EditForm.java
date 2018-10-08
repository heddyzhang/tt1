package kit.edi.JuchuDataJusin;

import static fb.inf.IKitConst.REQ_TYPE_EMPTY;

import fb.inf.KitEditForm;

/**
 *99905_1_1　受注データ受信
 *
 * @author Tuneduka
 *
 */
public class EdiJuchuDataJusin_EditForm extends KitEditForm {

	/**シリアルID */
	private static final long serialVersionUID = 4175451710717435615L;

	/*
	 * 画面固有インスタンス変数
	 *

	 */
	private String jyusinsakiKbn = null;

	/* --------------------------------------------------------
	 * 各フィールドのアクセサを作成する
	 ------------------------------------------------------- */
	/**
	 * 受信先区分を返します。
	 * @return riyouKbn
	 */
	public String getJyusinsakiKbn() {
		return jyusinsakiKbn;
	}

	/**
	 * 受信先区分をレコードに格納します。
	 * @param riyouKbn 利用区分
	 */
	public void setJyusinsakiKbn(String jyusinsakiKbn) {
		this.jyusinsakiKbn = jyusinsakiKbn;
	}

	/**
	 * 初期画面用にセットする
	 */
	public void init() {
		// リクエストタイプを初期化
		this.setReqType(REQ_TYPE_EMPTY);

	}


}
