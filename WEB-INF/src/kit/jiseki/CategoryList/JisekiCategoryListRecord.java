package kit.jiseki.CategoryList;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;

/**
 * カテゴリ別販売実績のレコードクラス
 */
public class JisekiCategoryListRecord extends KitRecord {

	/**
	 *
	 */
	private static final long serialVersionUID = -8544179218619480521L;
	/** デバッグ. */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public JisekiCategoryListRecord() {
		super();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input
	 *            Map
	 */
	public JisekiCategoryListRecord(Map<String, Object> input) {
		super(input);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input
	 *            HashMap
	 */
	public JisekiCategoryListRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input
	 *            JisekiAriaListRecord
	 */
	public JisekiCategoryListRecord(JisekiCategoryListRecord input) {
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
	 * 前期を返します。
	 *
	 * @return 前期
	 */
	public String getZenki() {
		return (String) get("ZENKI");
	}

	/**
	 * 前期をレコードに格納します。
	 *
	 * @param zenki 前期
	 */
	public void setZenki(String zenki) {
		put("ZENKI", zenki);
	}

	/**
	 * 前期同日を返します。
	 *
	 * @return 前期同日
	 */
	public String getZenkiDoji() {
		return (String) get("ZENKI_DOUJI");
	}

	/**
	 * 前期同日をレコードに格納します。
	 *
	 * @param zenkiDoji 前期同日
	 */
	public void setZenkiDoji(String zenkiDoji) {
		put("ZENKI_DOUJI", zenkiDoji);
	}

	/**
	 * 当期を返します。
	 *
	 * @return 当期
	 */
	public String getToki() {
		return (String) get("TOKI");
	}

	/**
	 * 当期をレコードに格納します。
	 *
	 * @param toki 当期
	 */
	public void setToki(String toki) {
		put("TOKI", toki);
	}

	/**
	 * 同日比を返します。
	 *
	 * @return 同日比
	 */
	public String getDojitsuhi() {
		return (String) get("DOJITSUHI");
	}

	/**
	 * 同日比をレコードに格納します。
	 *
	 * @param dojitsuhi 同日比
	 */
	public void setDojitsuhi(String dojitsuhi) {
		put("DOJITSUHI", dojitsuhi);
	}

	/**
	 * 進捗率を返します。
	 *
	 * @return 進捗率
	 */
	public String getShincyoku() {
		return (String) get("SHINCYOKU");
	}

	/**
	 * 進捗率をレコードに格納します。
	 *
	 * @param shincyoku 進捗率
	 */
	public void setShincyoku(String shincyoku) {
		put("SHINCYOKU", shincyoku);
	}

	/**
	 * 清酒当期を返します。
	 *
	 * @return 清酒当期
	 */
	public String getSeisyuToki() {
		return (String) get("SEISYU_TOKI");
	}

	/**
	 * 清酒当期をレコードに格納します。
	 *
	 * @param seisyuToki 清酒当期
	 */
	public void setSeisyuToki(String seisyuToki) {
		put("SEISYU_TOKI", seisyuToki);
	}

	/**
	 * 清酒前期を返します。
	 *
	 * @return 清酒前期
	 */
	public String getSeisyuZenki() {
		return (String) get("SEISYU_ZENKI");
	}

	/**
	 * 清酒前期をレコードに格納します。
	 *
	 * @param seisyuZenki 清酒前期
	 */
	public void setSeisyuZenki(String seisyuZenki) {
		put("SEISYU_ZENKI", seisyuZenki);
	}

	/**
	 * 清酒対比を返します。
	 *
	 * @return 清酒対比
	 */
	public String getSeisyuTaihi() {
		return (String) get("SEISYU_TAIHI");
	}

	/**
	 * 清酒対比をレコードに格納します。
	 *
	 * @param seisyuTaihi清酒対比
	 */
	public void setSeisyuTaihi(String seisyuTaihi) {
		put("SEISYU_TAIHI", seisyuTaihi);
	}

	/**
	 * 麦酒・発泡酒当期を返します。
	 *
	 * @return 麦酒・発泡酒当期
	 */
	public String getBiruToki() {
		return (String) get("BIRU_TOKI");
	}

	/**
	 * 麦酒・発泡酒当期をレコードに格納します。
	 *
	 * @param biruToki 麦酒・発泡酒当期
	 */
	public void setBiruToki(String biruToki) {
		put("BIRU_TOKI", biruToki);
	}

	/**
	 * 麦酒・発泡酒前期を返します。
	 *
	 * @return 麦酒・発泡酒前期
	 */
	public String getBiruZenki() {
		return (String) get("BIRU_ZENKI");
	}

	/**
	 * 麦酒・発泡酒前期をレコードに格納します。
	 *
	 * @param biruZenki 麦酒・発泡酒前期
	 */
	public void setBiruZenki(String biruZenki) {
		put("BIRU_ZENKI", biruZenki);
	}

	/**
	 * 麦酒・発泡酒対比を返します。
	 *
	 * @return 清酒対比
	 */
	public String getBiruTaihi() {
		return (String) get("BIRU_TAIHI");
	}

	/**
	 * 麦酒・発泡酒対比をレコードに格納します。
	 *
	 * @param biruTaihi 麦酒・発泡酒対比
	 */
	public void setBiruTaihi(String biruTaihi) {
		put("BIRU_TAIHI", biruTaihi);
	}

	/**
	 * 焼酎当期を返します。
	 *
	 * @return 焼酎当期
	 */
	public String getSyocyuToki() {
		return (String) get("SYOCYU_TOKI");
	}

	/**
	 * 焼酎当期をレコードに格納します。
	 *
	 * @param syocyuToki 焼酎当期
	 */
	public void setSyocyuToki(String syocyuToki) {
		put("SYOCYU_TOKI", syocyuToki);
	}

	/**
	 * 焼酎前期を返します。
	 *
	 * @return 焼酎前期
	 */
	public String getSyocyuZenki() {
		return (String) get("SYOCYU_ZENKI");
	}

	/**
	 * 焼酎前期をレコードに格納します。
	 *
	 * @param syocyuZenki 焼酎前期
	 */
	public void setSyocyuZenki(String syocyuZenki) {
		put("SYOCYU_ZENKI", syocyuZenki);
	}

	/**
	 * 焼酎対比を返します。
	 *
	 * @return 焼酎対比
	 */
	public String getSyocyuTaihi() {
		return (String) get("SYOCYU_TAIHI");
	}

	/**
	 * 焼酎対比をレコードに格納します。
	 *
	 * @param syocyuTaihi 焼酎対比
	 */
	public void setSyocyuTaihi(String syocyuTaihi) {
		put("SYOCYU_TAIHI", syocyuTaihi);
	}

	/**
	 * リキュール当期を返します。
	 *
	 * @return リキュール当期
	 */
	public String getRikyuruToki() {
		return (String) get("RIKYURU_TOKI");
	}

	/**
	 * リキュール当期をレコードに格納します。
	 *
	 * @param rikyuruToki リキュール当期
	 */
	public void setRikyuruToki(String rikyuruToki) {
		put("RIKYURU_TOKI", rikyuruToki);
	}

	/**
	 * リキュール前期を返します。
	 *
	 * @return リキュール前期
	 */
	public String getRikyuruZenki() {
		return (String) get("RIKYURU_ZENKI");
	}

	/**
	 *リキュール前期をレコードに格納します。
	 *
	 * @param ｒikyuruZenki リキュール前期
	 */
	public void setRikyuruZenki(String ｒikyuruZenki) {
		put("RIKYURU_ZENKI", ｒikyuruZenki);
	}

	/**
	 * リキュール対比を返します。
	 *
	 * @return リキュール対比
	 */
	public String getRikyuruTaihi() {
		return (String) get("RIKYURU_TAIHI");
	}

	/**
	 * リキュール対比をレコードに格納します。
	 *
	 * @param ｒikyuruTaihi リキュール対比
	 */
	public void setRikyuruTaihi(String ｒikyuruTaihi) {
		put("RIKYURU_TAIHI", ｒikyuruTaihi);
	}

	/**
	 * 調味料当期を返します。
	 *
	 * @return 調味料当期
	 */
	public String getCyomiryoToki() {
		return (String) get("CYOMIRYO_TOKI");
	}

	/**
	 * 調味料当期をレコードに格納します。
	 *
	 * @param cyomiryoToki 調味料当期
	 */
	public void setCyomiryoToki(String cyomiryoToki) {
		put("CYOMIRYO_TOKI", cyomiryoToki);
	}

	/**
	 * 調味料前期を返します。
	 *
	 * @return 調味料前期
	 */
	public String getCyomiryoZenki() {
		return (String) get("CYOMIRYO_ZENKI");
	}

	/**
	 * 調味料前期をレコードに格納します。
	 *
	 * @param ｒikyuruZenki 調味料前期
	 */
	public void setCyomiryoZenki(String cyomiryoZenki) {
		put("CYOMIRYO_ZENKI", cyomiryoZenki);
	}

	/**
	 * 調味料対比を返します。
	 *
	 * @return 調味料対比
	 */
	public String getCyomiryoTaihi() {
		return (String) get("CYOMIRYO_TAIHI");
	}

	/**
	 * 調味料対比をレコードに格納します。
	 *
	 * @param cyomiryo 調味料対比
	 */
	public void setCyomiryoTaihi(String cyomiryoTaihi) {
		put("CYOMIRYO_TAIHI", cyomiryoTaihi);
	}

	/**
	 * 伏水当期を返します。
	 *
	 * @return 伏水当期
	 */
	public String getFusuiToki() {
		return (String) get("FUSUI_TOKI");
	}

	/**
	 * 伏水当期をレコードに格納します。
	 *
	 * @param fusuiToki 伏水当期
	 */
	public void setFusuiToki(String fusuiToki) {
		put("FUSUI_TOKI", fusuiToki);
	}

	/**
	 * 伏水前期を返します。
	 *
	 * @return 伏水前期
	 */
	public String getFusuiZenki() {
		return (String) get("FUSUI_ZENKI");
	}

	/**
	 * 伏水前期をレコードに格納します。
	 *
	 * @param fusuiZenki 伏水前期
	 */
	public void setFusuiZenki(String fusuiZenki) {
		put("FUSUI_ZENKI", fusuiZenki);
	}

	/**
	 * 伏水対比を返します。
	 *
	 * @return 伏水対比
	 */
	public String getFusuiTaihi() {
		return (String) get("FUSUI_TAIHI");
	}

	/**
	 * 伏水対比をレコードに格納します。
	 *
	 * @param fusuiTaihi 伏水対比
	 */
	public void setFusuiTaihi(String fusuiTaihi) {
		put("FUSUI_TAIHI", fusuiTaihi);
	}

	/**
	 * 化粧品当期を返します。
	 *
	 * @return 化粧品当期
	 */
	public String getKesyohinToki() {
		return (String) get("KESYOHIN_TOKI");
	}

	/**
	 * 化粧品当期をレコードに格納します。
	 *
	 * @param kesyohinToki 化粧品当期
	 */
	public void setKesyohinToki(String kesyohinToki) {
		put("KESYOHIN_TOKI", kesyohinToki);
	}

	/**
	 * 化粧品前期を返します。
	 *
	 * @return 化粧品前期
	 */
	public String getKesyohinZenki() {
		return (String) get("KESYOHIN_ZENKI");
	}

	/**
	 * 化粧品前期をレコードに格納します。
	 *
	 * @param kesyohinZenki 化粧品前期
	 */
	public void setKesyohinZenki(String kesyohinZenki) {
		put("KESYOHIN_ZENKI",kesyohinZenki);
	}

	/**
	 * 化粧品対比を返します。
	 *
	 * @return 化粧品対比
	 */
	public String getKesyohinTaihi() {
		return (String) get("KESYOHIN_TAIHI");
	}

	/**
	 * 化粧品対比をレコードに格納します。
	 *
	 * @param kesyohinTaihi 化粧品対比
	 */
	public void setKesyohinTaihi(String kesyohinTaihi) {
		put("KESYOHIN_TAIHI", kesyohinTaihi);
	}

	/**
	 * その他当期を返します。
	 *
	 * @return その他当期
	 */
	public String getSonohokaToki() {
		return (String) get("SONOHOKA_TOKI");
	}

	/**
	 * その他当期をレコードに格納します。
	 *
	 * @param sonohokaToki その他当期
	 */
	public void setSonohokaToki(String sonohokaToki) {
		put("SONOHOKA_TOKI", sonohokaToki);
	}

	/**
	 * その他前期を返します。
	 *
	 * @return その他前期
	 */
	public String getSonohokaZenki() {
		return (String) get("SONOHOKA_ZENKI");
	}

	/**
	 * その他前期をレコードに格納します。
	 *
	 * @param sonohokaZenki その他前期
	 */
	public void setSonohokaZenki(String sonohokaZenki) {
		put("SONOHOKA_ZENKI",sonohokaZenki);
	}

	/**
	 * その他対比を返します。
	 *
	 * @return その他対比
	 */
	public String getSonohokaTaihi() {
		return (String) get("SONOHOKA_TAIHI");
	}

	/**
	 * その他対比をレコードに格納します。
	 *
	 * @param sonohokaTaihi その他対比
	 */
	public void setSonohokaTaihi(String sonohokaTaihi) {
		put("SONOHOKA_TAIHI", sonohokaTaihi);
	}

}	// -- class
