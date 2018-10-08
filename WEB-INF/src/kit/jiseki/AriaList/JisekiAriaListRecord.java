package kit.jiseki.AriaList;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;

/**
 * エリア別販売実績のレコードクラス
 */
public class JisekiAriaListRecord extends KitRecord {

	/**
	 *
	 */
	private static final long serialVersionUID = -8544179218619480521L;
	/** デバッグ. */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public JisekiAriaListRecord() {
		super();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input
	 *            Map
	 */
	public JisekiAriaListRecord(Map<String, Object> input) {
		super(input);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input
	 *            HashMap
	 */
	public JisekiAriaListRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input
	 *            JisekiAriaListRecord
	 */
	public JisekiAriaListRecord(JisekiAriaListRecord input) {
		super((KitRecord) input);

		isDebug_ = input.isDebug_;
		sDelete_ = input.sDelete_;
		sCheckClass_ = input.sCheckClass_;
		sDeleteClass_ = input.sDeleteClass_;
	}

	/**
	 * レコードの変更フラグを設定する
	 *
	 * @param b
	 */
	public void setModified(boolean b) {
		this.isModified_ = b;
	}


	/**
	 * XXX:画面入力値で編集リストを更新する
	 *
	 * @param reco
	 *            KZRecordオブジェクト
	 */
	public void modifyRecord(KitRecord reco) {

	}

	/**
	 * XXX:空白かどうか判定.
	 *
	 * @return true
	 */
	public boolean isEmpty() {
		boolean bEmpty = true;

		return bEmpty;
	}

	/**
	 * XXX:レコードが同じかどうか判定する。
	 *
	 * @param reco
	 *            KZRecordオブジェクト
	 * @return 判定
	 */
	public boolean equals(KitRecord reco) {
		boolean bEquals = true;

 		return bEquals;

	}

	/**
	 * 項目別にエラー有無フラグ<br/>
	 * エラーチェック対象項目のキーを配列化して評価する
	 */
	public boolean isHasErrorByElements() {

		return false;
	}


	/**
	 * エラー、警告等表示用フラグ初期化
	 * <br>※ ジェネレーターで作成するメソッドを初期化する
	 */
	public void initClasses(){

	}

	// ====================================
	// 変更前の情報を保持させるGetter / Setter （VIEW）

	/**
	 * エリア名を返します。
	 *
	 * @return エリア名
	 */
	public String getAriaNm() {
		return (String) get("CD_NM");
	}

	/**
	 * エリア名をレコードに格納します。
	 *
	 * @param cdNm エリア名
	 */
	public void setAriaNm(String cdNm) {
		put("CD_NM", cdNm);
	}

	/**
	 * 備考Bを返します。
	 *
	 * @return 備考B
	 */
	public String getBikouB() {
		return (String) get("BIKOU_B");
	}


	/**
	 * 備考Bをレコードに格納します。
	 *
	 * @param bikouB 備考B
	 */
	public void setBikouB(String bikouB) {
		put("BIKOU_B", bikouB);
	}

	/**
	 * エリアコードを返します。
	 *
	 * @return エリアコード
	 */
	public String getCodeCd() {
		return (String) get("CODE_CD");
	}

	/**
	 * エリアコードをレコードに格納します。
	 *
	 * @param codeCd エリアコード
	 */
	public void setCodeCd(String codeCd) {
		put("CODE_CD", codeCd);
	}

	/**
	 * 日計を返します。
	 *
	 * @return 日計
	 */
	public String getNikei() {
		return (String) get("NIKEI");
	}

	/**
	 * 日計をレコードに格納します。
	 *
	 * @param nikei 日計
	 */
	public void setNikei(String nikei) {
		put("NIKEI", nikei);
	}

	/**
	 * 当月累計を返します。
	 *
	 * @return 当月累計
	 */
	public String getTogetsuRuikei() {
		return (String) get("TOGETSU_RUIKEI");
	}

	/**
	 * 当月累計をレコードに格納します。
	 *
	 * @param togetsuRuikei 当月累計
	 */
	public void setTogetsuRuikei(String togetsuRuikei) {
		put("TOGETSU_RUIKEI", togetsuRuikei);
	}

	/**
	 * 前年同日を返します。
	 *
	 * @return 前年同日
	 */
	public String getZennenDojitsu() {
		return (String) get("ZENNEN_DOJITSU");
	}

	/**
	 * 前年同日をレコードに格納します。
	 *
	 * @param zennenDojitsu 前年同日
	 */
	public void setZennenDojitsu(String zennenDojitsu) {
		put("ZENNEN_DOJITSU", zennenDojitsu);
	}

	/**
	 * 前年月末を返します。
	 *
	 * @return 前年月末
	 */
	public String getZennenGetsumatsu() {
		return (String) get("ZENNEN_GETSUMATSU");
	}

	/**
	 * 前年月末をレコードに格納します。
	 *
	 * @param zennenGetsumatsu 前年月末
	 */
	public void setZennenGetsumatsu(String zennenGetsumatsu) {
		put("ZENNEN_GETSUMATSU", zennenGetsumatsu);
	}

	/**
	 * 前々年月末を返します。
	 *
	 * @return 前々年月末
	 */
	public String getZenzennenGetsumatsu() {
		return (String) get("ZENZENNEN_GETSUMATSU");
	}

	/**
	 * 前々年月末をレコードに格納します。
	 *
	 * @param zenzennenGetsumatsu 前々年月末
	 */
	public void setZenzennenGetsumatsu(String zenzennenGetsumatsu) {
		put("ZENZENNEN_GETSUMATSU", zenzennenGetsumatsu);
	}

	/**
	 * 当期（10月）を返します。
	 *
	 * @return 当期（10月）
	 */
	public String getJyugatsuToki() {
		return (String) get("JYUGATSU_TOKI");
	}

	/**
	 * 当期（10月）をレコードに格納します。
	 *
	 * @param jyugatsuToki 当期（10月）
	 */
	public void setJyugatsuToki(String jyugatsuToki) {
		put("JYUGATSU_TOKI", jyugatsuToki);
	}

	/**
	 * 前期（10月）を返します。を返します。
	 *
	 * @return 前期（10月）
	 */
	public String getJyugatsuZenki() {
		return (String) get("JYUGATSU_ZENKI");
	}

	/**
	 * 前期（10月）をレコードに格納します。
	 *
	 * @param jyugatsuZenki 前期（10月）
	 */
	public void setJyugatsuZenki(String jyugatsuZenki) {
		put("JYUGATSU_ZENKI", jyugatsuZenki);
	}
	/**
	 * 当期（1月）を返します。
	 *
	 * @return 当期（1月）
	 */
	public String getIchigatsuToki() {
		return (String) get("ICHIGATSU_TOKI");
	}

	/**
	 * 当期（1月）をレコードに格納します。
	 *
	 * @param ichigatsuToki 当期（1月）
	 */
	public void setIchigatsuToki(String ichigatsuToki) {
		put("ICHIGATSU_TOKI", ichigatsuToki);
	}

	/**
	 * 前期（1月）を返します。
	 *
	 * @return 前期（1月）
	 */
	public String getIchigatsuZenki() {
		return (String) get("ICHIGATSU_ZENKI");
	}

	/**
	 * 前期（1月）をレコードに格納します。
	 *
	 * @param ichigatsuZenki 前期（1月）
	 */
	public void setIchigatsuZenki(String ichigatsuZenki) {
		put("ICHIGATSU_ZENKI", ichigatsuZenki);
	}

	/**
	 * 同日比を返します。
	 *
	 * @return 同日比
	 */
	public String getDoujitsuhi() {
		return (String) get("DOJITSUHI");
	}

	/**
	 * 同日比をレコードに格納します。
	 *
	 * @param doujitsuhi 同日比
	 */
	public void setDoujitsuhi(String doujitsuhi) {
		put("DOJITSUHI", doujitsuhi);
	}

	/**
	 * 当月進捗率を返します。
	 *
	 * @return 当月進捗率
	 */
	public String getTogetsuShincyoku() {
		return (String) get("TOGETSUSHINCYOKU");
	}

	/**
	 * 当月進捗率をレコードに格納します。
	 *
	 * @param togetsuShincyoku 当月進捗率
	 */
	public void setTogetsuShincyoku(String togetsuShincyoku) {
		put("TOGETSUSHINCYOKU", togetsuShincyoku);
	}

	/**
	 * 10月進捗率を返します。
	 *
	 * @return 10月進捗率
	 */
	public String getJyugatsuShincyoku() {
		return (String) get("JYOUGATSUSHINCYOKU");
	}

	/**
	 * 10月進捗率をレコードに格納します。
	 *
	 * @param jyougatsuShincyoku 10月進捗率
	 */
	public void setJyugatsuShincyoku(String jyougatsuShincyoku) {
		put("JYOUGATSUSHINCYOKU", jyougatsuShincyoku);
	}

	/**
	 * 1月進捗率を返します。
	 *
	 * @return 1月進捗率
	 */
	public String getIchigatsuShincyoku() {
		return (String) get("ICHIGATSUSHINCYOKU");
	}

	/**
	 * 1月進捗率をレコードに格納します。
	 *
	 * @param ichigatsuShincyoku 1月進捗率
	 */
	public void setIchigatsuShincyoku(String ichigatsuShincyoku) {
		put("ICHIGATSUSHINCYOKU", ichigatsuShincyoku);
	}

}
