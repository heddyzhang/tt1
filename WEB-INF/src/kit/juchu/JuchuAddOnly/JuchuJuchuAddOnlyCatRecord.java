package kit.juchu.JuchuAddOnly;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;
import java.util.Map;

import kit.juchu.CategoryDataCommonRecord;
import fb.inf.KitRecord;

/**
 * 受付専用受注追加入力（予定出荷先別商品カテゴリデータ）のレコードクラス
 */
public class JuchuJuchuAddOnlyCatRecord extends CategoryDataCommonRecord {

	/** シリアルID */
	private static final long serialVersionUID = 426777981183926679L;

	/** デバッグ */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public JuchuJuchuAddOnlyCatRecord() {
		super();
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input Map
	 */
	public JuchuJuchuAddOnlyCatRecord(Map<String, Object> input) {
		super(input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input HashMap
	 */
	public JuchuJuchuAddOnlyCatRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input JuchuJuchuAddOnlyCatRecord
	 */
	public JuchuJuchuAddOnlyCatRecord(JuchuJuchuAddOnlyCatRecord input) {
		super((CategoryDataCommonRecord) input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();

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

	/**
	 * 親に主キーのありかを指示する(マスタ系必須メソッド)
	 * setの引数に主キーを指定すること
	 */
	private void setPrimaryKeys(){
		// 会社CD、受注NO、受注カテゴリ行NO
		super.setPrimaryKeys("KAISYA_CD", "JYUCYU_NO", "SHN_CTGR_LINE_NO");
	}

	// -----------------------------------------
	// 固有の処理

	// -----------------------------------------
	// Getter / Setter
	// ※ ジェネレーターで作成する

	// 削除フラグ
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


	// ====編集用（共通部以外）====
	/**
	 * 受注NOを返します。
	 *
	 * @return 受注NO
	 */
	public String getJyucyuNo() {
		return (String) get("JYUCYU_NO");
	}

	/**
	 * 受注NOをレコードに格納します。
	 *
	 * @param jyucyuNo 受注NO
	 */
	public void setJyucyuNo(String jyucyuNo) {
		put("JYUCYU_NO", jyucyuNo);
	}


	/**
	 * 受注カテゴリ行NOを返します。
	 *
	 * @return 受注カテゴリ行NO
	 */
	public String getShnCtgrLineNo() {
		return (String) get("SHN_CTGR_LINE_NO");
	}

	/**
	 * 受注カテゴリ行NOをレコードに格納します。
	 *
	 * @param shnCtgrLineNo 受注カテゴリ行NO
	 */
	public void setShnCtgrLineNo(String shnCtgrLineNo) {
		put("SHN_CTGR_LINE_NO", shnCtgrLineNo);
	}


	/**
	 * 出荷本数計を返します。
	 *
	 * @return 出荷本数計
	 */
	public String getSyukaHon() {
		return (String) get("SYUKA_HON");
	}

	/**
	 * 出荷本数計をレコードに格納します。
	 *
	 * @param syukaHon 出荷本数計
	 */
	public void setSyukaHon(String syukaHon) {
		put("SYUKA_HON", syukaHon);
	}


	/**
	 * 出荷容量計（L)を返します。
	 *
	 * @return 出荷容量計（L)
	 */
	public String getSyukaVol() {
		return (String) get("SYUKA_VOL");
	}

	/**
	 * 出荷容量計（L)をレコードに格納します。
	 *
	 * @param syukaVol 出荷容量計（L)
	 */
	public void setSyukaVol(String syukaVol) {
		put("SYUKA_VOL", syukaVol);
	}


	/**
	 * 販売金額計（円）を返します。
	 *
	 * @return 販売金額計（円）
	 */
	public String getHanbaiKingaku() {
		return (String) get("HANBAI_KINGAKU");
	}

	/**
	 * 販売金額計（円）をレコードに格納します。
	 *
	 * @param hanbaiKingaku 販売金額計（円）
	 */
	public void setHanbaiKingaku(String hanbaiKingaku) {
		put("HANBAI_KINGAKU", hanbaiKingaku);
	}


	/**
	 * 備考1を返します。
	 *
	 * @return 備考1
	 */
	public String getBikou1Tx() {
		return (String) get("BIKOU_1_TX");
	}

	/**
	 * 備考1をレコードに格納します。
	 *
	 * @param bikou1Tx 備考1
	 */
	public void setBikou1Tx(String bikou1Tx) {
		put("BIKOU_1_TX", bikou1Tx);
	}


	/**
	 * 備考2を返します。
	 *
	 * @return 備考2
	 */
	public String getBikou2Tx() {
		return (String) get("BIKOU_2_TX");
	}

	/**
	 * 備考2をレコードに格納します。
	 *
	 * @param bikou2Tx 備考2
	 */
	public void setBikou2Tx(String bikou2Tx) {
		put("BIKOU_2_TX", bikou2Tx);
	}


	/**
	 * 備考3を返します。
	 *
	 * @return 備考3
	 */
	public String getBikou3Tx() {
		return (String) get("BIKOU_3_TX");
	}

	/**
	 * 備考3をレコードに格納します。
	 *
	 * @param bikou3Tx 備考3
	 */
	public void setBikou3Tx(String bikou3Tx) {
		put("BIKOU_3_TX", bikou3Tx);
	}


	/**
	 * 備考4を返します。
	 *
	 * @return 備考4
	 */
	public String getBikou4Tx() {
		return (String) get("BIKOU_4_TX");
	}

	/**
	 * 備考4をレコードに格納します。
	 *
	 * @param bikou4Tx 備考4
	 */
	public void setBikou4Tx(String bikou4Tx) {
		put("BIKOU_4_TX", bikou4Tx);
	}


	/**
	 * 備考5を返します。
	 *
	 * @return 備考5
	 */
	public String getBikou5Tx() {
		return (String) get("BIKOU_5_TX");
	}

	/**
	 * 備考5をレコードに格納します。
	 *
	 * @param bikou5Tx 備考5
	 */
	public void setBikou5Tx(String bikou5Tx) {
		put("BIKOU_5_TX", bikou5Tx);
	}

}	// -- class
