package kit.juchu;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;

/**
 * トラン系カテゴリデータ共通部のレコードクラス
 */
public class CategoryDataCommonRecord extends KitRecord {

	/** シリアルID */
	private static final long serialVersionUID = 1152221343082565919L;

	/** デバッグ */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public CategoryDataCommonRecord() {
		super();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input Map
	 */
	public CategoryDataCommonRecord(Map<String, Object> input) {
		super(input);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input HashMap
	 */
	public CategoryDataCommonRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input CategoryDataCommonRecord
	 */
	public CategoryDataCommonRecord(CategoryDataCommonRecord input) {
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
	 * レコードの変更フラグを取得する
	 *
	 * @return レコードの変更フラグ
	 */
	public boolean getModified() {
		return this.isModified_;
	}

	/**
	 * 画面入力値で編集リストを更新する
	 *
	 * @param reco KZRecordオブジェクト
	 */
	public void modifyRecord(KitRecord reco) {
	}

	/**
	 * 空白かどうか判定
	 *
	 * @return true
	 */
	public boolean isEmpty() {
		return false;
	}

	/**
	 * レコードが同じかどうか判定する。
	 *
	 * @param reco paramName
	 *
	 * @return 判定
	 */
	public boolean equals(KitRecord reco) {
		return false;
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
	 */
	public void initClasses(){
		// 親クラスに保持しているフラグを初期化
		setCheckClass(STYLE_CLASS_INIT);
		setDeleteClass(STYLE_CLASS_INIT);
	}

	// -----------------------------------------
	// Getter / Setter

	/**
	 * 削除フラグを返します。
	 *
	 * @return 削除フラグ
	 */
	public String getDeleteFlg() {
		return (String) get("DELETE_FLG");
	}

	/**
	 * 削除フラグをレコードに格納します。
	 *
	 * @param deleteFlg 削除フラグ
	 */
	public void setDeleteFlg(String deleteFlg) {
		put("DELETE_FLG", deleteFlg);
	}


	// ====編集用====
	/**
	 * 利用区分を返します。
	 *
	 * @return 利用区分
	 */
	public String getRiyouKbn() {
		return (String) get("RIYOU_KBN");
	}

	/**
	 * 利用区分をレコードに格納します。
	 *
	 * @param riyouKbn 利用区分
	 */
	public void setRiyouKbn(String riyouKbn) {
		put("RIYOU_KBN", riyouKbn);
	}


	/**
	 * 会社CDを返します。
	 *
	 * @return 会社CD
	 */
	public String getKaisyaCd() {
		return (String) get("KAISYA_CD");
	}

	/**
	 * 会社CDをレコードに格納します。
	 *
	 * @param kaisyaCd 会社CD
	 */
	public void setKaisyaCd(String kaisyaCd) {
		put("KAISYA_CD", kaisyaCd);
	}


	/**
	 * 寄託者CDを返します。
	 *
	 * @return 寄託者CD
	 */
	public String getKtksyCd() {
		return (String) get("KTKSY_CD");
	}

	/**
	 * 寄託者CDをレコードに格納します。
	 *
	 * @param ktksyCd 寄託者CD
	 */
	public void setKtksyCd(String ktksyCd) {
		put("KTKSY_CD", ktksyCd);
	}


	/**
	 * 製品CDを返します。
	 *
	 * @return 製品CD
	 */
	public String getSeihinCd() {
		return (String) get("SEIHIN_CD");
	}

	/**
	 * 製品CDをレコードに格納します。
	 *
	 * @param seihinCd 製品CD
	 */
	public void setSeihinCd(String seihinCd) {
		put("SEIHIN_CD", seihinCd);
	}


	/**
	 * 販売部門CDを返します。
	 *
	 * @return 販売部門CD
	 */
	public String getHanbaiBumonCd() {
		return (String) get("HANBAI_BUMON_CD");
	}

	/**
	 * 販売部門CDをレコードに格納します。
	 *
	 * @param hanbaiBumonCd 販売部門CD
	 */
	public void setHanbaiBumonCd(String hanbaiBumonCd) {
		put("HANBAI_BUMON_CD", hanbaiBumonCd);
	}


	/**
	 * 販売部門名（略式）を返します。
	 *
	 * @return 販売部門名（略式）
	 */
	public String getHanbaiBumonRnm() {
		return (String) get("HANBAI_BUMON_RNM");
	}

	/**
	 * 販売部門名（略式）をレコードに格納します。
	 *
	 * @param hanbaiBumonRnm 販売部門名（略式）
	 */
	public void setHanbaiBumonRnm(String hanbaiBumonRnm) {
		put("HANBAI_BUMON_RNM", hanbaiBumonRnm);
	}


	/**
	 * 販売種別CDを返します。
	 *
	 * @return 販売種別CD
	 */
	public String getHanbaiSyubetuCd() {
		return (String) get("HANBAI_SYUBETU_CD");
	}

	/**
	 * 販売種別CDをレコードに格納します。
	 *
	 * @param hanbaiSyubetuCd 販売種別CD
	 */
	public void setHanbaiSyubetuCd(String hanbaiSyubetuCd) {
		put("HANBAI_SYUBETU_CD", hanbaiSyubetuCd);
	}


	/**
	 * 販売種別名（略式）を返します。
	 *
	 * @return 販売種別名（略式）
	 */
	public String getHanbaiSyubetuRnm() {
		return (String) get("HANBAI_SYUBETU_RNM");
	}

	/**
	 * 販売種別名（略式）をレコードに格納します。
	 *
	 * @param hanbaiSyubetuRnm 販売種別名（略式）
	 */
	public void setHanbaiSyubetuRnm(String hanbaiSyubetuRnm) {
		put("HANBAI_SYUBETU_RNM", hanbaiSyubetuRnm);
	}


	/**
	 * 販売分類CDを返します。
	 *
	 * @return 販売分類CD
	 */
	public String getHanbaiBunruiCd() {
		return (String) get("HANBAI_BUNRUI_CD");
	}

	/**
	 * 販売分類CDをレコードに格納します。
	 *
	 * @param hanbaiBunruiCd 販売分類CD
	 */
	public void setHanbaiBunruiCd(String hanbaiBunruiCd) {
		put("HANBAI_BUNRUI_CD", hanbaiBunruiCd);
	}


	/**
	 * 販売分類名（略式）を返します。
	 *
	 * @return 販売分類名（略式）
	 */
	public String getHanbaiBunruiRnm() {
		return (String) get("HANBAI_BUNRUI_RNM");
	}

	/**
	 * 販売分類名（略式）をレコードに格納します。
	 *
	 * @param hanbaiBunruiRnm 販売分類名（略式）
	 */
	public void setHanbaiBunruiRnm(String hanbaiBunruiRnm) {
		put("HANBAI_BUNRUI_RNM", hanbaiBunruiRnm);
	}


	/**
	 * 酒税分類CDを返します。
	 *
	 * @return 酒税分類CD
	 */
	public String getSyuzeiCd() {
		return (String) get("SYUZEI_CD");
	}

	/**
	 * 酒税分類CDをレコードに格納します。
	 *
	 * @param syuzeiCd 酒税分類CD
	 */
	public void setSyuzeiCd(String syuzeiCd) {
		put("SYUZEI_CD", syuzeiCd);
	}


	/**
	 * 酒税分類名（略式）を返します。
	 *
	 * @return 酒税分類名（略式）
	 */
	public String getSyuzeiNmRyaku() {
		return (String) get("SYUZEI_NM_RYAKU");
	}

	/**
	 * 酒税分類名（略式）をレコードに格納します。
	 *
	 * @param syuzeiNmRyaku 酒税分類名（略式）
	 */
	public void setSyuzeiNmRyaku(String syuzeiNmRyaku) {
		put("SYUZEI_NM_RYAKU", syuzeiNmRyaku);
	}


	/**
	 * 種CDを返します。
	 *
	 * @return 種CD
	 */
	public String getTaneCd() {
		return (String) get("TANE_CD");
	}

	/**
	 * 種CDをレコードに格納します。
	 *
	 * @param taneCd 種CD
	 */
	public void setTaneCd(String taneCd) {
		put("TANE_CD", taneCd);
	}


	/**
	 * 種名称（略式）を返します。
	 *
	 * @return 種名称（略式）
	 */
	public String getTaneNmRyaku() {
		return (String) get("TANE_NM_RYAKU");
	}

	/**
	 * 種名称（略式）をレコードに格納します。
	 *
	 * @param taneNmRyaku 種名称（略式）
	 */
	public void setTaneNmRyaku(String taneNmRyaku) {
		put("TANE_NM_RYAKU", taneNmRyaku);
	}


	/**
	 * 素材区分を返します。
	 *
	 * @return 素材区分
	 */
	public String getSozaiKbn() {
		return (String) get("SOZAI_KBN");
	}

	/**
	 * 素材区分をレコードに格納します。
	 *
	 * @param sozaiKbn 素材区分
	 */
	public void setSozaiKbn(String sozaiKbn) {
		put("SOZAI_KBN", sozaiKbn);
	}


	/**
	 * 色区分を返します。
	 *
	 * @return 色区分
	 */
	public String getColorKbn() {
		return (String) get("COLOR_KBN");
	}

	/**
	 * 色区分をレコードに格納します。
	 *
	 * @param colorKbn 色区分
	 */
	public void setColorKbn(String colorKbn) {
		put("COLOR_KBN", colorKbn);
	}


	/**
	 * 販売実績分類区分を返します。
	 *
	 * @return 販売実績分類区分
	 */
	public String getHjisekiBunruiKbn() {
		return (String) get("HJISEKI_BUNRUI_KBN");
	}

	/**
	 * 販売実績分類区分をレコードに格納します。
	 *
	 * @param hjisekiBunruiKbn 販売実績分類区分
	 */
	public void setHjisekiBunruiKbn(String hjisekiBunruiKbn) {
		put("HJISEKI_BUNRUI_KBN", hjisekiBunruiKbn);
	}


	/**
	 * 容量（L）を返します。
	 *
	 * @return 容量（L）
	 */
	public String getVol() {
		return (String) get("VOL");
	}

	/**
	 * 容量（L）をレコードに格納します。
	 *
	 * @param vol 容量（L）
	 */
	public void setVol(String vol) {
		put("VOL", vol);
	}


	/**
	 * 単価（円）を返します。
	 *
	 * @return 単価（円）
	 */
	public String getTanka() {
		return (String) get("TANKA");
	}

	/**
	 * 単価（円）をレコードに格納します。
	 *
	 * @param tanka 単価（円）
	 */
	public void setTanka(String tanka) {
		put("TANKA", tanka);
	}


	/**
	 * 利用定数を返します。
	 *
	 * @return 利用定数
	 */
	public String getRiyouTeisu() {
		return (String) get("RIYOU_TEISU");
	}

	/**
	 * 利用定数をレコードに格納します。
	 *
	 * @param riyouTeisu 利用定数
	 */
	public void setRiyouTeisu(String riyouTeisu) {
		put("RIYOU_TEISU", riyouTeisu);
	}

}	// -- class
