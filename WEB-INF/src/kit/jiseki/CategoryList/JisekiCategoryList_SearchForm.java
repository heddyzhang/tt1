package kit.jiseki.CategoryList;

import fb.inf.KitSearchForm;

/**
 * 検索部の構造を提供するデータオブジェクトクラスです
 */
public class JisekiCategoryList_SearchForm extends KitSearchForm {

	/**
	 *
	 */
	private static final long serialVersionUID = 4309011533334611811L;

	// -----------------------------------------
	// 画面固有のインスタンス変数・アクセサメソッドを用意してください。
	// また、resetメソッドにリセット処理を記述します。
	// -----------------------------------------
	// 画面固有インスタンス変数
	private String shiteibi = null;		// 日付
	private String hyoujiTanyi = null;	// 表示単位
	private String syukeiKikan = null;	// 集計期間
	private String kensakuNiji = null;	// 検索日時
	private String searchKbn = null;	// 検索区分

	///検索条件用変数
	private String shiteibiGetsusyo = null;				// 指定日月初
	private String zennenDojitsu = null;				// 前年同日
	private String zennenDojitsuGetsusyo = null;		// 前年同日月初
	private String zennenDojitsuGetsumatsu = null;		// 前年同日月末
	private String konkisyo = null;						// 今期初
	private String zenkisyo = null;						// 前期初
	private String kotoshisyo = null;					// 今年初
	private String zennensyo = null;					// 前年初

	// 画面固有アクセサメソッド

	/**
	 * 指定日を返します。
	 * @return shiteibi
	 */
	public String getShiteibi() {
		return shiteibi;
	}

	/**
	 * 指定日を格納します。
	 * @param shiteibi 指定日
	 */
	public void setShiteibi(String shiteibi) {
		this.shiteibi = shiteibi;
	}

	/**
	 * 表示単位を返します。
	 * @return hyoujiTanyi
	 */
	public String getHyoujiTanyi() {
		return hyoujiTanyi;
	}

	/**
	 * 表示単位を格納します。
	 * @param hyoujiTanyi 表示単位
	 */
	public void setHyoujiTanyi(String hyoujiTanyi) {
		this.hyoujiTanyi = hyoujiTanyi;
	}

	/**
	 * 集計期間を返します。
	 * @return syukeiKikan
	 */
	public String getSyukeiKikan() {
		return syukeiKikan;
	}

	/**
	 * 集計期間を格納します。
	 * @param syukeiKikan 集計期間
	 */
	public void setSyukeiKikan(String syukeiKikan) {
		this.syukeiKikan = syukeiKikan;
	}

	/**
	 * 検索日時を返します。
	 * @return kensakuNiji
	 */
	public String getKensakuNiji() {
		return kensakuNiji;
	}

	/**
	 * 検索日時を格納します。
	 * @param kensakuNiji 検索日時
	 */
	public void setKensakuNiji(String kensakuNiji) {
		this.kensakuNiji = kensakuNiji;
	}

	/**
	 * 検索区分を返します。
	 * @return searchKbn
	 */
	public String getSearchKbn() {
		return searchKbn;
	}

	/**
	 * 検索区分を格納します。
	 * @param searchKbn 検索区分
	 */
	public void setSearchKbn(String searchKbn) {
		this.searchKbn = searchKbn;
	}

	/**
	 * 指定日月初を返します。
	 * @return shiteibiGetsusyo
	 */
	public String getShiteibiGetsusyo() {
		return shiteibiGetsusyo;
	}

	/**
	 * 指定日月初を格納します。
	 * @param shiteibiGetsusyo 指定日月初
	 */
	public void setShiteibiGetsusyo(String shiteibiGetsusyo) {
		this.shiteibiGetsusyo = shiteibiGetsusyo;
	}

	/**
	 * 前年同日を返します。
	 * @return zennenDojitsu
	 */
	public String getZennenDojitsu() {
		return zennenDojitsu;
	}

	/**
	 * 前年同日を格納します。
	 * @param zennenDojitsu 前年同日
	 */
	public void setZennenDojitsu(String zennenDojitsu) {
		this.zennenDojitsu = zennenDojitsu;
	}

	/**
	 * 前年同日月初を返します。
	 * @return zennenDojitsuGetsusyo
	 */
	public String getZennenDojitsuGetsusyo() {
		return zennenDojitsuGetsusyo;
	}

	/**
	 * 前年同日月初を格納します。
	 * @param zennenDojitsuGetsusyo 前年同日月初
	 */
	public void setZennenDojitsuGetsusyo(String zennenDojitsuGetsusyo) {
		this.zennenDojitsuGetsusyo = zennenDojitsuGetsusyo;
	}

	/**
	 * 前年同日月末を返します。
	 * @return zennenDojitsuGetsumatsu
	 */
	public String getZennenDojitsuGetsumatsu() {
		return zennenDojitsuGetsumatsu;
	}

	/**
	 * 前年同日月末を格納します。
	 * @param zennenDojitsuGetsumatsu 前年同日月末
	 */
	public void setZennenDojitsuGetsumatsu(String zennenDojitsuGetsumatsu) {
		this.zennenDojitsuGetsumatsu = zennenDojitsuGetsumatsu;
	}

	/**
	 * 今期初を返します。
	 * @return konkisyo
	 */
	public String getKonkisyo() {
		return konkisyo;
	}

	/**
	 * 今期初を格納します。
	 * @param konkisyo 今期初
	 */
	public void setKonkisyo(String konkisyo) {
		this.konkisyo = konkisyo;
	}

	/**
	 * 前期初を返します。
	 * @return zenkisyo
	 */
	public String getZenkisyo() {
		return zenkisyo;
	}

	/**
	 * 前期初を格納します。
	 * @param zenkisyo 前期初
	 */
	public void setZenkisyo(String zenkisyo) {
		this.zenkisyo = zenkisyo;
	}

	/**
	 * 今年初を返します。
	 * @return kotoshisyo
	 */
	public String getKotoshisyo() {
		return kotoshisyo;
	}

	/**
	 * 今年初を格納します。
	 * @param kotoshisyo 今年初
	 */
	public void setKotoshisyo(String kotoshisyo) {
		this.kotoshisyo = kotoshisyo;
	}

	/**
	 * 前年初を返します。
	 * @return zennensyo
	 */
	public String getZennensyo() {
		return zennensyo;
	}

	/**
	 * 前年初を格納します。
	 * @param zennensyo 前年初
	 */
	public void setZennensyo(String zennensyo) {
		this.zennensyo = zennensyo;
	}

}
