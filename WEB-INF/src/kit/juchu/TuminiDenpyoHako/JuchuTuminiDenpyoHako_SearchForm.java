package kit.juchu.TuminiDenpyoHako;

import static kit.juchu.TuminiDenpyoHako.IJuchuTuminiDenpyoHako.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import fb.inf.KitSearchForm;
import fb.inf.pbs.PbsUtil;

/**
 * 検索部の構造を提供するデータオブジェクトクラスです
 */
public class JuchuTuminiDenpyoHako_SearchForm extends KitSearchForm {

	/** シリアルID */
	private static final long serialVersionUID = 8924660992011876340L;

	// -----------------------------------------
	// 画面固有のインスタンス変数・アクセサメソッドを用意してください。
	// また、resetメソッドにリセット処理を記述します。
	// -----------------------------------------
	// 画面固有インスタンス変数
	private String syukaDt; // 出荷日
	private String syukaDtClass; // 出荷日フラグ
	private String unsotenCd; // 運送店CD


	// 画面固有アクセサメソッド
	/**
	 * 出荷日を取得する。
	 *
	 * @return 出荷日
	 */
	public String getSyukaDt() {
		return this.syukaDt;
	}

	/**
	 * 出荷日を設定する。
	 *
	 * @param syukaDt 出荷日
	 */
	public void setSyukaDt(String syukaDt) {
		this.syukaDt = syukaDt;
	}


	/**
	 * 出荷日フラグを取得する。
	 *
	 * @return 出荷日フラグ
	 */
	public String getSyukaDtClass() {
		return this.syukaDtClass;
	}

	/**
	 * 出荷日フラグを設定する。
	 *
	 * @param syukaDtClass 出荷日フラグ
	 */
	public void setSyukaDtClass(String syukaDtClass) {
		this.syukaDtClass = syukaDtClass;
	}


	/**
	 * 運送店CDを取得する。
	 *
	 * @return 運送店CD
	 */
	public String getUnsotenCd() {
		return this.unsotenCd;
	}

	/**
	 * 運送店CDを設定する。
	 *
	 * @param unsotenCd 運送店CD
	 */
	public void setUnsotenCd(String unsotenCd) {
		this.unsotenCd = unsotenCd;
	}


	/**
	 * リセット処理をする。
	 *
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.syukaDt = null; // 出荷日
		this.syukaDtClass = null; // 出荷日フラグ
		this.unsotenCd = null; // 運送店CD
	}


	/**
	 * ページ内最大行数を取得する。
	 *
	 * @return ページ内最大行数
	 */
	@Override
	public int getMaxSu() {

		String value = PbsUtil.getMessageResourceString(KEY_MAX_SU);

		if (value == null) {
			value = PbsUtil.getMessageResourceString("com.maxSu");
		}

		return Integer.parseInt(value);
	}

	/**
	 * 検索部の表示を初期化する
	 */
	public void initialize() {
		this.setSyukaDt(PbsUtil.getTodayYYYYMMDD()); // 出荷日＝本日
	}

}
