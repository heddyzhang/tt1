package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 積荷データ／HD部のレコードクラス
 */
public class JuchuTuminiDenpyoHakoHdRecord extends KitRecord {

	/** シリアルID */
	private static final long serialVersionUID = -8602239392023747204L;

	/** デバッグ */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public JuchuTuminiDenpyoHakoHdRecord() {
		super();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input Map
	 */
	public JuchuTuminiDenpyoHakoHdRecord(Map<String, Object> input) {
		super(input);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input HashMap
	 */
	public JuchuTuminiDenpyoHakoHdRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input JuchuTuminiDenpyoHakoHdRecord
	 */
	public JuchuTuminiDenpyoHakoHdRecord(JuchuTuminiDenpyoHakoHdRecord input) {
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
		JuchuTuminiDenpyoHakoHdRecord target = (JuchuTuminiDenpyoHakoHdRecord) reco;
		setSyuyakuKbn(target.getSyuyakuKbn()); // 集約区分
	}

	/**
	 * 空白かどうか判定
	 *
	 * @return true
	 */
	public boolean isEmpty() {
		boolean bEmpty = true;

		// 集約区分
		if (!PbsUtil.isEmpty(getSyuyakuKbn())) {
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
		JuchuTuminiDenpyoHakoHdRecord target = (JuchuTuminiDenpyoHakoHdRecord) reco;

		// 集約区分
		if (!PbsUtil.isEqual(getSyuyakuKbn(), target.getSyuyakuKbn())) {
			bEquals = false;
		}

		return bEquals;
	}

	/**
	 * 項目別にエラー有無フラグ<br/>
	 * エラーチェック対象項目のキーを配列化して評価する
	 */
	public boolean isHasErrorByElements() {
		String[] flgs = {
				 "RIYOU_KBN_CLASS"
				,"TUMIDEN_NO_CLASS"
				,"UNSOTEN_CD_CLASS"
				,"SYUYAKU_KBN_CLASS"
				,"SYUKA_DT_CLASS"
				,"DENPYO_HAKO_DT_CLASS"
				,"DENPYO_HAKO_TM_CLASS"
				,"SOU_JYURYO_CLASS"
				,"SOU_SURYO_CASE_CLASS"
				,"SOU_SURYO_BARA_CLASS"
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
				,"UNSOTEN_CD_CLASS"
				,"SYUYAKU_KBN_CLASS"
				,"SYUKA_DT_CLASS"
				,"DENPYO_HAKO_DT_CLASS"
				,"DENPYO_HAKO_TM_CLASS"
				,"SOU_JYURYO_CLASS"
				,"SOU_SURYO_CASE_CLASS"
				,"SOU_SURYO_BARA_CLASS"
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
	 * 運送店CDを返します。
	 *
	 * @return 運送店CD
	 */
	public String getUnsotenCd() {
		return (String) get("UNSOTEN_CD");
	}

	/**
	 * 運送店CDをレコードに格納します。
	 *
	 * @param unsotenCd 運送店CD
	 */
	public void setUnsotenCd(String unsotenCd) {
		put("UNSOTEN_CD", unsotenCd);
	}


	/**
	 * 集約区分を返します。
	 *
	 * @return 集約区分
	 */
	public String getSyuyakuKbn() {
		return (String) get("SYUYAKU_KBN");
	}

	/**
	 * 集約区分をレコードに格納します。
	 *
	 * @param syuyakuKbn 集約区分
	 */
	public void setSyuyakuKbn(String syuyakuKbn) {
		put("SYUYAKU_KBN", syuyakuKbn);
	}


	/**
	 * 出荷日を返します。
	 *
	 * @return 出荷日
	 */
	public String getSyukaDt() {
		return (String) get("SYUKA_DT");
	}

	/**
	 * 出荷日をレコードに格納します。
	 *
	 * @param syukaDt 出荷日
	 */
	public void setSyukaDt(String syukaDt) {
		put("SYUKA_DT", syukaDt);
	}


	/**
	 * 伝票発行日を返します。
	 *
	 * @return 伝票発行日
	 */
	public String getDenpyoHakoDt() {
		return (String) get("DENPYO_HAKO_DT");
	}

	/**
	 * 伝票発行日をレコードに格納します。
	 *
	 * @param denpyoHakoDt 伝票発行日
	 */
	public void setDenpyoHakoDt(String denpyoHakoDt) {
		put("DENPYO_HAKO_DT", denpyoHakoDt);
	}


	/**
	 * 伝票発行時刻を返します。
	 *
	 * @return 伝票発行時刻
	 */
	public String getDenpyoHakoTm() {
		return (String) get("DENPYO_HAKO_TM");
	}

	/**
	 * 伝票発行時刻をレコードに格納します。
	 *
	 * @param denpyoHakoTm 伝票発行時刻
	 */
	public void setDenpyoHakoTm(String denpyoHakoTm) {
		put("DENPYO_HAKO_TM", denpyoHakoTm);
	}


	/**
	 * 総重量(KG)を返します。
	 *
	 * @return 総重量(KG)
	 */
	public String getSouJyuryo() {
		return (String) get("SOU_JYURYO");
	}

	/**
	 * 総重量(KG)をレコードに格納します。
	 *
	 * @param souJyuryo 総重量(KG)
	 */
	public void setSouJyuryo(String souJyuryo) {
		put("SOU_JYURYO", souJyuryo);
	}


	/**
	 * 総数量 ケースを返します。
	 *
	 * @return 総数量 ケース
	 */
	public String getSouSuryoCase() {
		return (String) get("SOU_SURYO_CASE");
	}

	/**
	 * 総数量 ケースをレコードに格納します。
	 *
	 * @param souSuryoCase 総数量 ケース
	 */
	public void setSouSuryoCase(String souSuryoCase) {
		put("SOU_SURYO_CASE", souSuryoCase);
	}


	/**
	 * 総数量 バラを返します。
	 *
	 * @return 総数量 バラ
	 */
	public String getSouSuryoBara() {
		return (String) get("SOU_SURYO_BARA");
	}

	/**
	 * 総数量 バラをレコードに格納します。
	 *
	 * @param souSuryoBara 総数量 バラ
	 */
	public void setSouSuryoBara(String souSuryoBara) {
		put("SOU_SURYO_BARA", souSuryoBara);
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
	 * 運送店CDのエラー、警告表示フラグを返します。
	 *
	 * @return 運送店CDのエラー、警告表示フラグ
	 */
	public String getUnsotenCdClass() {
		return (String) get("UNSOTEN_CD_CLASS");
	}

	/**
	 * 運送店CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param unsotenCdClass 運送店CDのエラー、警告表示フラグ
	 */
	public void setUnsotenCdClass(String unsotenCdClass) {
		put("UNSOTEN_CD_CLASS", unsotenCdClass);
	}


	/**
	 * 集約区分のエラー、警告表示フラグを返します。
	 *
	 * @return 集約区分のエラー、警告表示フラグ
	 */
	public String getSyuyakuKbnClass() {
		return (String) get("SYUYAKU_KBN_CLASS");
	}

	/**
	 * 集約区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syuyakuKbnClass 集約区分のエラー、警告表示フラグ
	 */
	public void setSyuyakuKbnClass(String syuyakuKbnClass) {
		put("SYUYAKU_KBN_CLASS", syuyakuKbnClass);
	}


	/**
	 * 出荷日のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷日のエラー、警告表示フラグ
	 */
	public String getSyukaDtClass() {
		return (String) get("SYUKA_DT_CLASS");
	}

	/**
	 * 出荷日のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaDtClass 出荷日のエラー、警告表示フラグ
	 */
	public void setSyukaDtClass(String syukaDtClass) {
		put("SYUKA_DT_CLASS", syukaDtClass);
	}


	/**
	 * 伝票発行日のエラー、警告表示フラグを返します。
	 *
	 * @return 伝票発行日のエラー、警告表示フラグ
	 */
	public String getDenpyoHakoDtClass() {
		return (String) get("DENPYO_HAKO_DT_CLASS");
	}

	/**
	 * 伝票発行日のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param denpyoHakoDtClass 伝票発行日のエラー、警告表示フラグ
	 */
	public void setDenpyoHakoDtClass(String denpyoHakoDtClass) {
		put("DENPYO_HAKO_DT_CLASS", denpyoHakoDtClass);
	}


	/**
	 * 伝票発行時刻のエラー、警告表示フラグを返します。
	 *
	 * @return 伝票発行時刻のエラー、警告表示フラグ
	 */
	public String getDenpyoHakoTmClass() {
		return (String) get("DENPYO_HAKO_TM_CLASS");
	}

	/**
	 * 伝票発行時刻のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param denpyoHakoTmClass 伝票発行時刻のエラー、警告表示フラグ
	 */
	public void setDenpyoHakoTmClass(String denpyoHakoTmClass) {
		put("DENPYO_HAKO_TM_CLASS", denpyoHakoTmClass);
	}


	/**
	 * 総重量(KG)のエラー、警告表示フラグを返します。
	 *
	 * @return 総重量(KG)のエラー、警告表示フラグ
	 */
	public String getSouJyuryoClass() {
		return (String) get("SOU_JYURYO_CLASS");
	}

	/**
	 * 総重量(KG)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param souJyuryoClass 総重量(KG)のエラー、警告表示フラグ
	 */
	public void setSouJyuryoClass(String souJyuryoClass) {
		put("SOU_JYURYO_CLASS", souJyuryoClass);
	}


	/**
	 * 総数量 ケースのエラー、警告表示フラグを返します。
	 *
	 * @return 総数量 ケースのエラー、警告表示フラグ
	 */
	public String getSouSuryoCaseClass() {
		return (String) get("SOU_SURYO_CASE_CLASS");
	}

	/**
	 * 総数量 ケースのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param souSuryoCaseClass 総数量 ケースのエラー、警告表示フラグ
	 */
	public void setSouSuryoCaseClass(String souSuryoCaseClass) {
		put("SOU_SURYO_CASE_CLASS", souSuryoCaseClass);
	}


	/**
	 * 総数量 バラのエラー、警告表示フラグを返します。
	 *
	 * @return 総数量 バラのエラー、警告表示フラグ
	 */
	public String getSouSuryoBaraClass() {
		return (String) get("SOU_SURYO_BARA_CLASS");
	}

	/**
	 * 総数量 バラのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param souSuryoBaraClass 総数量 バラのエラー、警告表示フラグ
	 */
	public void setSouSuryoBaraClass(String souSuryoBaraClass) {
		put("SOU_SURYO_BARA_CLASS", souSuryoBaraClass);
	}

}	// -- class
