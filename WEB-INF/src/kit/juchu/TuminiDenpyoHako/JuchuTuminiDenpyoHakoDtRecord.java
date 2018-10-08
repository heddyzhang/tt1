package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 積荷データ／DT部のレコードクラス
 */
public class JuchuTuminiDenpyoHakoDtRecord extends KitRecord {

	/** シリアルID */
	private static final long serialVersionUID = -3741693069652086766L;

	/** デバッグ */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public JuchuTuminiDenpyoHakoDtRecord() {
		super();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input Map
	 */
	public JuchuTuminiDenpyoHakoDtRecord(Map<String, Object> input) {
		super(input);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input HashMap
	 */
	public JuchuTuminiDenpyoHakoDtRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input JuchuTuminiDenpyoHakoDtRecord
	 */
	public JuchuTuminiDenpyoHakoDtRecord(JuchuTuminiDenpyoHakoDtRecord input) {
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
		JuchuTuminiDenpyoHakoDtRecord target = (JuchuTuminiDenpyoHakoDtRecord) reco;
		setTuminokosiFlg(target.getTuminokosiFlg()); // 積み残しフラグ
	}

	/**
	 * 空白かどうか判定
	 *
	 * @return true
	 */
	public boolean isEmpty() {
		boolean bEmpty = true;

		// 積み残しフラグ
		if (!PbsUtil.isEmpty(getTuminokosiFlg())) {
			bEmpty = false;
		}

		return bEmpty;
	}

	/**
	 * レコードが同じかどうか判定する。
	 *
	 * @param reco paramName
	 *
	 * @return 判定
	 */
	public boolean equals(KitRecord reco) {
		boolean bEquals = true;
		JuchuTuminiDenpyoHakoDtRecord target = (JuchuTuminiDenpyoHakoDtRecord) reco;

		// 積み残しフラグ
		if (!PbsUtil.isEqual(getTuminokosiFlg(), target.getTuminokosiFlg())) {
			bEquals = false;
		}

		return bEquals;
	}

	/**
	 * 項目別にエラー有無フラグ<br/>
	 * エラーチェック対象項目のキーを配列化して評価する
	 */
	public boolean isHasErrorByElements() {
		// レコードクラスに保持しているフラグを初期化
		String[] flgs = {
				 "RIYOU_KBN_CLASS"
				,"TUMIDEN_NO_CLASS"
				,"INPUT_LINE_NO_CLASS"
				,"TUMINOKOSI_FLG_CLASS"
				,"JYUCYU_NO_CLASS"
				,"SYUKA_SURYO_CASE_CLASS"
				,"SYUKA_SURYO_BARA_CLASS"
				,"JYURYO_TOT_CLASS"
		};
		for (String flg : flgs) {
			if (STYLE_CLASS_ERROR.equals(getString(flg))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * エラー、警告等表示用フラグ初期化
	 */
	public void initClasses(){
		// 親クラスに保持しているフラグを初期化
		setCheckClass(STYLE_CLASS_INIT);
		setDeleteClass(STYLE_CLASS_INIT);

		// レコードクラスに保持しているフラグを初期化
		String[] flgs = {
				 "RIYOU_KBN_CLASS"
				,"TUMIDEN_NO_CLASS"
				,"INPUT_LINE_NO_CLASS"
				,"TUMINOKOSI_FLG_CLASS"
				,"JYUCYU_NO_CLASS"
				,"SYUKA_SURYO_CASE_CLASS"
				,"SYUKA_SURYO_BARA_CLASS"
				,"JYURYO_TOT_CLASS"
		};
		for (String flg : flgs) {
			put(flg, STYLE_CLASS_INIT);
		}
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
	 * 積荷伝票NOを返します。
	 *
	 * @return 積荷伝票NO
	 */
	public String getTumidenNo() {
		return (String) get("TUMIDEN_NO");
	}

	/**
	 * 積荷伝票NOをレコードに格納します。
	 *
	 * @param tumidenNo 積荷伝票NO
	 */
	public void setTumidenNo(String tumidenNo) {
		put("TUMIDEN_NO", tumidenNo);
	}


	/**
	 * レコードNOを返します。
	 *
	 * @return レコードNO
	 */
	public String getInputLineNo() {
		return (String) get("INPUT_LINE_NO");
	}

	/**
	 * レコードNOをレコードに格納します。
	 *
	 * @param inputLineNo レコードNO
	 */
	public void setInputLineNo(String inputLineNo) {
		put("INPUT_LINE_NO", inputLineNo);
	}


	/**
	 * 積み残しフラグを返します。
	 *
	 * @return 積み残しフラグ
	 */
	public String getTuminokosiFlg() {
		return (String) get("TUMINOKOSI_FLG");
	}

	/**
	 * 積み残しフラグをレコードに格納します。
	 *
	 * @param tuminokosiFlg 積み残しフラグ
	 */
	public void setTuminokosiFlg(String tuminokosiFlg) {
		put("TUMINOKOSI_FLG", tuminokosiFlg);
	}


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
	 * 商品CDを返します。
	 *
	 * @return 商品CD
	 */
	public String getShohinCd() {
		return (String) get("SHOHIN_CD");
	}

	/**
	 * 商品CDをレコードに格納します。
	 *
	 * @param shohinCd 商品CD
	 */
	public void setShohinCd(String shohinCd) {
		put("SHOHIN_CD", shohinCd);
	}


	/**
	 * 出荷数量 ケースを返します。
	 *
	 * @return 出荷数量 ケース
	 */
	public String getSyukaSuryoCase() {
		return (String) get("SYUKA_SURYO_CASE");
	}

	/**
	 * 出荷数量 ケースをレコードに格納します。
	 *
	 * @param syukaSuryoCase 出荷数量 ケース
	 */
	public void setSyukaSuryoCase(String syukaSuryoCase) {
		put("SYUKA_SURYO_CASE", syukaSuryoCase);
	}


	/**
	 * 出荷数量 バラを返します。
	 *
	 * @return 出荷数量 バラ
	 */
	public String getSyukaSuryoBara() {
		return (String) get("SYUKA_SURYO_BARA");
	}

	/**
	 * 出荷数量 バラをレコードに格納します。
	 *
	 * @param syukaSuryoBara 出荷数量 バラ
	 */
	public void setSyukaSuryoBara(String syukaSuryoBara) {
		put("SYUKA_SURYO_BARA", syukaSuryoBara);
	}


	/**
	 * 重量計(KG)を返します。
	 *
	 * @return 重量計(KG)
	 */
	public String getJyuryoTot() {
		return (String) get("JYURYO_TOT");
	}

	/**
	 * 重量計(KG)をレコードに格納します。
	 *
	 * @param jyuryoTot 重量計(KG)
	 */
	public void setJyuryoTot(String jyuryoTot) {
		put("JYURYO_TOT", jyuryoTot);
	}


	// ====エラー用====
	/**
	 * 利用区分のエラー、警告表示フラグを返します。
	 *
	 * @return 利用区分のエラー、警告表示フラグ
	 */
	public String getRiyouKbnClass() {
		return (String) get("RIYOU_KBN_CLASS");
	}

	/**
	 * 利用区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param riyouKbnClass 利用区分のエラー、警告表示フラグ
	 */
	public void setRiyouKbnClass(String riyouKbnClass) {
		put("RIYOU_KBN_CLASS", riyouKbnClass);
	}


	/**
	 * 会社CDのエラー、警告表示フラグを返します。
	 *
	 * @return 会社CDのエラー、警告表示フラグ
	 */
	public String getKaisyaCdClass() {
		return (String) get("KAISYA_CD_CLASS");
	}

	/**
	 * 会社CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param kaisyaCdClass 会社CDのエラー、警告表示フラグ
	 */
	public void setKaisyaCdClass(String kaisyaCdClass) {
		put("KAISYA_CD_CLASS", kaisyaCdClass);
	}


	/**
	 * 積荷伝票NOのエラー、警告表示フラグを返します。
	 *
	 * @return 積荷伝票NOのエラー、警告表示フラグ
	 */
	public String getTumidenNoClass() {
		return (String) get("TUMIDEN_NO_CLASS");
	}

	/**
	 * 積荷伝票NOのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tumidenNoClass 積荷伝票NOのエラー、警告表示フラグ
	 */
	public void setTumidenNoClass(String tumidenNoClass) {
		put("TUMIDEN_NO_CLASS", tumidenNoClass);
	}


	/**
	 * レコードNOのエラー、警告表示フラグを返します。
	 *
	 * @return レコードNOのエラー、警告表示フラグ
	 */
	public String getInputLineNoClass() {
		return (String) get("INPUT_LINE_NO_CLASS");
	}

	/**
	 * レコードNOのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param inputLineNoClass レコードNOのエラー、警告表示フラグ
	 */
	public void setInputLineNoClass(String inputLineNoClass) {
		put("INPUT_LINE_NO_CLASS", inputLineNoClass);
	}


	/**
	 * 積み残しフラグのエラー、警告表示フラグを返します。
	 *
	 * @return 積み残しフラグのエラー、警告表示フラグ
	 */
	public String getTuminokosiFlgClass() {
		return (String) get("TUMINOKOSI_FLG_CLASS");
	}

	/**
	 * 積み残しフラグのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tuminokosiFlgClass 積み残しフラグのエラー、警告表示フラグ
	 */
	public void setTuminokosiFlgClass(String tuminokosiFlgClass) {
		put("TUMINOKOSI_FLG_CLASS", tuminokosiFlgClass);
	}


	/**
	 * 受注NOのエラー、警告表示フラグを返します。
	 *
	 * @return 受注NOのエラー、警告表示フラグ
	 */
	public String getJyucyuNoClass() {
		return (String) get("JYUCYU_NO_CLASS");
	}

	/**
	 * 受注NOのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param jyucyuNoClass 受注NOのエラー、警告表示フラグ
	 */
	public void setJyucyuNoClass(String jyucyuNoClass) {
		put("JYUCYU_NO_CLASS", jyucyuNoClass);
	}


	/**
	 * 商品CDのエラー、警告表示フラグを返します。
	 *
	 * @return 商品CDのエラー、警告表示フラグ
	 */
	public String getShohinCdClass() {
		return (String) get("SHOHIN_CD_CLASS");
	}

	/**
	 * 商品CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param shohinCdClass 商品CDのエラー、警告表示フラグ
	 */
	public void setShohinCdClass(String shohinCdClass) {
		put("SHOHIN_CD_CLASS", shohinCdClass);
	}


	/**
	 * 出荷数量 ケースのエラー、警告表示フラグを返します。
	 *
	 * @return 出荷数量 ケースのエラー、警告表示フラグ
	 */
	public String getSyukaSuryoCaseClass() {
		return (String) get("SYUKA_SURYO_CASE_CLASS");
	}

	/**
	 * 出荷数量 ケースのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaSuryoCaseClass 出荷数量 ケースのエラー、警告表示フラグ
	 */
	public void setSyukaSuryoCaseClass(String syukaSuryoCaseClass) {
		put("SYUKA_SURYO_CASE_CLASS", syukaSuryoCaseClass);
	}


	/**
	 * 出荷数量 バラのエラー、警告表示フラグを返します。
	 *
	 * @return 出荷数量 バラのエラー、警告表示フラグ
	 */
	public String getSyukaSuryoBaraClass() {
		return (String) get("SYUKA_SURYO_BARA_CLASS");
	}

	/**
	 * 出荷数量 バラのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaSuryoBaraClass 出荷数量 バラのエラー、警告表示フラグ
	 */
	public void setSyukaSuryoBaraClass(String syukaSuryoBaraClass) {
		put("SYUKA_SURYO_BARA_CLASS", syukaSuryoBaraClass);
	}


	/**
	 * 重量計(KG)のエラー、警告表示フラグを返します。
	 *
	 * @return 重量計(KG)のエラー、警告表示フラグ
	 */
	public String getJyuryoTotClass() {
		return (String) get("JYURYO_TOT_CLASS");
	}

	/**
	 * 重量計(KG)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param jyuryoTotClass 重量計(KG)のエラー、警告表示フラグ
	 */
	public void setJyuryoTotClass(String jyuryoTotClass) {
		put("JYURYO_TOT_CLASS", jyuryoTotClass);
	}

}	// -- class
