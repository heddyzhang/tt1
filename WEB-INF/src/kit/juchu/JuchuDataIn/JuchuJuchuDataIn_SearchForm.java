package kit.juchu.JuchuDataIn;

import static fb.com.IKitComConstHM.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import fb.inf.KitSearchForm;
import fb.inf.pbs.PbsUtil;

/**
 * 検索部の構造を提供するデータオブジェクトクラスです
 */
public class JuchuJuchuDataIn_SearchForm extends KitSearchForm {

	/** シリアルID */
	private static final long serialVersionUID = 6590680175970601655L;

	// -----------------------------------------
	// 画面固有のインスタンス変数・アクセサメソッドを用意してください。
	// また、resetメソッドにリセット処理を記述します。
	// -----------------------------------------
	// 画面固有インスタンス変数
	private String orositenCdLast; // 最終送荷先卸
	private String orositenNmLast; // 最終送荷先卸名
	private String orositenCdLastClass; // 最終送荷先卸フラグ
	private String orsTatesnCd; // 縦線CD
	private String syubetuCd; // 識別
	private String jyucyuNo; // 受注NO
	private String syukaDt; // 出荷日
	private String unsotenCd; // 運送店
	private String unsotenNm; // 運送店名
	private String shohinCd; // 商品
	private String shohinNm; // 商品名
	private String todofuknCd; // 都道府県
	private String kuracFlg; // 蔵直フラグ


	// 画面固有アクセサメソッド
	/**
	 * 最終送荷先卸を取得する。
	 *
	 * @return 最終送荷先卸
	 */
	public String getOrositenCdLast() {
		return this.orositenCdLast;
	}

	/**
	 * 最終送荷先卸を設定する。
	 *
	 * @param orositenCdLast 最終送荷先卸
	 */
	public void setOrositenCdLast(String orositenCdLast) {
		this.orositenCdLast = orositenCdLast;
	}


	/**
	 * 最終送荷先卸名を取得する。
	 *
	 * @return 最終送荷先卸名
	 */
	public String getOrositenNmLast() {
		return this.orositenNmLast;
	}

	/**
	 * 最終送荷先卸名を設定する。
	 *
	 * @param orositenNmLast 最終送荷先卸名
	 */
	public void setOrositenNmLast(String orositenNmLast) {
		this.orositenNmLast = orositenNmLast;
	}


	/**
	 * 最終送荷先卸フラグを取得する。
	 *
	 * @return 最終送荷先卸フラグ
	 */
	public String getOrositenCdLastClass() {
		return this.orositenCdLastClass;
	}

	/**
	 * 最終送荷先卸フラグを設定する。
	 *
	 * @param orositenCdLastClass 最終送荷先卸フラグ
	 */
	public void setOrositenCdLastClass(String orositenCdLastClass) {
		this.orositenCdLastClass = orositenCdLastClass;
	}


	/**
	 * 縦線CDを取得する。
	 *
	 * @return 縦線CD
	 */
	public String getOrsTatesnCd() {
		return this.orsTatesnCd;
	}

	/**
	 * 縦線CDを設定する。
	 *
	 * @param orsTatesnCd 縦線CD
	 */
	public void setOrsTatesnCd(String orsTatesnCd) {
		this.orsTatesnCd = orsTatesnCd;
	}


	/**
	 * 識別を取得する。
	 *
	 * @return 識別
	 */
	public String getSyubetuCd() {
		return this.syubetuCd;
	}

	/**
	 * 識別を設定する。
	 *
	 * @param syubetuCd 識別
	 */
	public void setSyubetuCd(String syubetuCd) {
		this.syubetuCd = syubetuCd;
	}


	/**
	 * 受注NOを取得する。
	 *
	 * @return 受注NO
	 */
	public String getJyucyuNo() {
		return this.jyucyuNo;
	}

	/**
	 * 受注NOを設定する。
	 *
	 * @param jyucyuNo 受注NO
	 */
	public void setJyucyuNo(String jyucyuNo) {
		this.jyucyuNo = jyucyuNo;
	}


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
	 * 運送店を取得する。
	 *
	 * @return 運送店
	 */
	public String getUnsotenCd() {
		return this.unsotenCd;
	}

	/**
	 * 運送店を設定する。
	 *
	 * @param unsotenCd 運送店
	 */
	public void setUnsotenCd(String unsotenCd) {
		this.unsotenCd = unsotenCd;
	}


	/**
	 * 運送店名を取得する。
	 *
	 * @return 運送店名
	 */
	public String getUnsotenNm() {
		return this.unsotenNm;
	}

	/**
	 * 運送店名を設定する。
	 *
	 * @param unsotenNm 運送店名
	 */
	public void setUnsotenNm(String unsotenNm) {
		this.unsotenNm = unsotenNm;
	}


	/**
	 * 商品を取得する。
	 *
	 * @return 商品
	 */
	public String getShohinCd() {
		return this.shohinCd;
	}

	/**
	 * 商品を設定する。
	 *
	 * @param shohinCd 商品
	 */
	public void setShohinCd(String shohinCd) {
		this.shohinCd = shohinCd;
	}


	/**
	 * 商品名を取得する。
	 *
	 * @return 商品名
	 */
	public String getShohinNm() {
		return this.shohinNm;
	}

	/**
	 * 商品名を設定する。
	 *
	 * @param shohinNm 商品名
	 */
	public void setShohinNm(String shohinNm) {
		this.shohinNm = shohinNm;
	}


	/**
	 * 都道府県を取得する。
	 *
	 * @return 都道府県
	 */
	public String getTodofuknCd() {
		return this.todofuknCd;
	}

	/**
	 * 都道府県を設定する。
	 *
	 * @param todofuknCd 都道府県
	 */
	public void setTodofuknCd(String todofuknCd) {
		this.todofuknCd = todofuknCd;
	}


	/**
	 * 蔵直フラグを取得する。
	 *
	 * @return 蔵直フラグ
	 */
	public String getKuracFlg() {
		return this.kuracFlg;
	}

	/**
	 * 蔵直フラグを設定する。
	 *
	 * @param kuracFlg 蔵直フラグ
	 */
	public void setKuracFlg(String kuracFlg) {
		this.kuracFlg = kuracFlg;
	}


	/**
	 * リセット処理をする。
	 *
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.orositenCdLast = null; // 最終送荷先卸
		this.orositenNmLast = null; // 最終送荷先卸名
		this.orositenCdLastClass = null; // 最終送荷先卸フラグ
		this.orsTatesnCd = null; // 縦線CD
		this.syubetuCd = null; // 識別
		this.jyucyuNo = null; // 受注NO
		this.syukaDt = null; // 出荷日
		this.unsotenCd = null; // 運送店
		this.unsotenNm = null; // 運送店名
		this.shohinCd = null; // 商品
		this.shohinNm = null; // 商品名
		this.todofuknCd = null; // 都道府県
		this.kuracFlg = null; // 蔵直フラグ
	}


	/**
	 * ページ内最大行数を取得する。
	 *
	 * @return ページ内最大行数
	 */
	@Override
	public int getMaxSu() {

		String value = PbsUtil.getMessageResourceString("com.maxSu");

		return Integer.parseInt(value);
	}


	/**
	 * ページ内最大行数を取得する。
	 *
	 * @return ページ内最大行数
	 */
	public int getMaxSuDt() {

		String value = PbsUtil.getMessageResourceString("com.juchuJuchuDataIn.maxSu");

		if (value == null) {
			value = PbsUtil.getMessageResourceString("com.maxSu");
		}

		return Integer.parseInt(value);
	}

	/**
	 * 検索部の表示を初期化する
	 */
	public void initialize() {
//		this.setSyubetuCd(JDATAKIND_KB_TUJO); // 通常伝票
		this.setCsvBtnAvailFlg(BUTTON_DISABLED_FALSE); // ファイル出力（CSV）ボタンを非活性化
		this.setExcelBtnAvailFlg(BUTTON_DISABLED_FALSE); // ファイル出力（EXCEL）ボタンを非活性化
	}

}
