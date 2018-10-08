package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;

/**
 * 積荷伝票発行
 * 　受注データ／DT部のレコードクラス
 */
public class JuchuTuminiDenpyoHakoItemRecord extends KitRecord {

	/** シリアルID */
	private static final long serialVersionUID = -5705883620948422576L;

	/** デバッグ */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public JuchuTuminiDenpyoHakoItemRecord() {
		super();
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input Map
	 */
	public JuchuTuminiDenpyoHakoItemRecord(Map<String, Object> input) {
		super(input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input HashMap
	 */
	public JuchuTuminiDenpyoHakoItemRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input JuchuTuminiDenpyoHakoRecord
	 */
	public JuchuTuminiDenpyoHakoItemRecord(JuchuTuminiDenpyoHakoItemRecord input) {
		super((KitRecord) input);
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
	 * 画面入力値で編集リストを更新する
	 *
	 * @param reco KZRecordオブジェクト
	 */
	public void modifyRecord(KitRecord record) {
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
		// レコードクラスに保持しているフラグを初期化
		String[] flgs = {
				 "RIYOU_KBN_CLASS"
				,"JYUCYU_NO_CLASS"
				,"SYUKA_SU_CASE_CLASS"
				,"SYUKA_SU_BARA_CLASS"
				,"SYUKA_ALL_WEIGTH_CLASS"
		};
		for (String flg : flgs) {
			if (STYLE_CLASS_ERROR.equals(getString(flg))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 入力重複チェック
	 *
	 * @param rec JuchuTuminiDenpyoHakoRecord
	 * @return TRUE:主キー項目が重複している / FALSE:重複無し
	 */
	public boolean isDuplicated(JuchuTuminiDenpyoHakoItemRecord rec){
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
				,"JYUCYU_NO_CLASS"
				,"SYUKA_SU_CASE_CLASS"
				,"SYUKA_SU_BARA_CLASS"
				,"SYUKA_ALL_WEIGTH_CLASS"
		};
		for (String flg : flgs) {
			put(flg, STYLE_CLASS_INIT);
		}
	}

	/**
	 * 親に主キーのありかを指示する
	 * setの引数に主キーを指定すること
	 */
	private void setPrimaryKeys(){
		// 会社CD、受注NO、受注行NO
		super.setPrimaryKeys("KAISYA_CD", "JYUCYU_NO", "INPUT_LINE_NO");
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
	 * 受注行NOを返します。
	 *
	 * @return 受注行NO
	 */
	public String getInputLineNo() {
		return (String) get("INPUT_LINE_NO");
	}

	/**
	 * 受注行NOをレコードに格納します。
	 *
	 * @param inputLineNo 受注行NO
	 */
	public void setInputLineNo(String inputLineNo) {
		put("INPUT_LINE_NO", inputLineNo);
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
	 * 積荷伝票用ラインNOを返します。
	 *
	 * @return 積荷伝票用ラインNO
	 */
	public String getTumidenLineNo() {
		return (String) get("TUMIDEN_LINE_NO");
	}

	/**
	 * 積荷伝票用ラインNOをレコードに格納します。
	 *
	 * @param tumidenLineNo 積荷伝票用ラインNO
	 */
	public void setTumidenLineNo(String tumidenLineNo) {
		put("TUMIDEN_LINE_NO", tumidenLineNo);
	}


	/**
	 * 商品名_積荷伝票用を返します。
	 *
	 * @return 商品名_積荷伝票用
	 */
	public String getShohinNmTumiden() {
		return (String) get("SHOHIN_NM_TUMIDEN");
	}

	/**
	 * 商品名_積荷伝票用をレコードに格納します。
	 *
	 * @param shohinNmTumiden 商品名_積荷伝票用
	 */
	public void setShohinNmTumiden(String shohinNmTumiden) {
		put("SHOHIN_NM_TUMIDEN", shohinNmTumiden);
	}


	/**
	 * 積伝用 ｾｯﾄ記号/容量名(2)を返します。
	 *
	 * @return 積伝用 ｾｯﾄ記号/容量名(2)
	 */
	public String getYoukiKigoNm2() {
		return (String) get("YOUKI_KIGO_NM2");
	}

	/**
	 * 積伝用 ｾｯﾄ記号/容量名(2)をレコードに格納します。
	 *
	 * @param youkiKigoNm2 積伝用 ｾｯﾄ記号/容量名(2)
	 */
	public void setYoukiKigoNm2(String youkiKigoNm2) {
		put("YOUKI_KIGO_NM2", youkiKigoNm2);
	}


	/**
	 * ケース入数を返します。
	 *
	 * @return ケース入数
	 */
	public String getCaseIrisu() {
		return (String) get("CASE_IRISU");
	}

	/**
	 * ケース入数をレコードに格納します。
	 *
	 * @param caseIrisu ケース入数
	 */
	public void setCaseIrisu(String caseIrisu) {
		put("CASE_IRISU", caseIrisu);
	}


	/**
	 * 出荷数量_箱数を返します。
	 *
	 * @return 出荷数量_箱数
	 */
	public String getSyukaSuCase() {
		return (String) get("SYUKA_SU_CASE");
	}

	/**
	 * 出荷数量_箱数をレコードに格納します。
	 *
	 * @param syukaSuCase 出荷数量_箱数
	 */
	public void setSyukaSuCase(String syukaSuCase) {
		put("SYUKA_SU_CASE", syukaSuCase);
	}


	/**
	 * 出荷数量_セット数を返します。
	 *
	 * @return 出荷数量_セット数
	 */
	public String getSyukaSuBara() {
		return (String) get("SYUKA_SU_BARA");
	}

	/**
	 * 出荷数量_セット数をレコードに格納します。
	 *
	 * @param syukaSuBara 出荷数量_セット数
	 */
	public void setSyukaSuBara(String syukaSuBara) {
		put("SYUKA_SU_BARA", syukaSuBara);
	}


	/**
	 * 出荷重量（KG)を返します。
	 *
	 * @return 出荷重量（KG)
	 */
	public String getSyukaAllWeigth() {
		return (String) get("SYUKA_ALL_WEIGTH");
	}

	/**
	 * 出荷重量（KG)をレコードに格納します。
	 *
	 * @param syukaAllWeigth 出荷重量（KG)
	 */
	public void setSyukaAllWeigth(String syukaAllWeigth) {
		put("SYUKA_ALL_WEIGTH", syukaAllWeigth);
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
	 * 受注行NOのエラー、警告表示フラグを返します。
	 *
	 * @return 受注行NOのエラー、警告表示フラグ
	 */
	public String getInputLineNoClass() {
		return (String) get("INPUT_LINE_NO_CLASS");
	}

	/**
	 * 受注行NOのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param inputLineNoClass 受注行NOのエラー、警告表示フラグ
	 */
	public void setInputLineNoClass(String inputLineNoClass) {
		put("INPUT_LINE_NO_CLASS", inputLineNoClass);
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
	 * 商品名_積荷伝票用のエラー、警告表示フラグを返します。
	 *
	 * @return 商品名_積荷伝票用のエラー、警告表示フラグ
	 */
	public String getShohinNmTumidenClass() {
		return (String) get("SHOHIN_NM_TUMIDEN_CLASS");
	}

	/**
	 * 商品名_積荷伝票用のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param shohinNmTumidenClass 商品名_積荷伝票用のエラー、警告表示フラグ
	 */
	public void setShohinNmTumidenClass(String shohinNmTumidenClass) {
		put("SHOHIN_NM_TUMIDEN_CLASS", shohinNmTumidenClass);
	}


	/**
	 * 積伝用 ｾｯﾄ記号/容量名(2)のエラー、警告表示フラグを返します。
	 *
	 * @return 積伝用 ｾｯﾄ記号/容量名(2)のエラー、警告表示フラグ
	 */
	public String getYoukiKigoNm2Class() {
		return (String) get("YOUKI_KIGO_NM2_CLASS");
	}

	/**
	 * 積伝用 ｾｯﾄ記号/容量名(2)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param youkiKigoNm2Class 積伝用 ｾｯﾄ記号/容量名(2)のエラー、警告表示フラグ
	 */
	public void setYoukiKigoNm2Class(String youkiKigoNm2Class) {
		put("YOUKI_KIGO_NM2_CLASS", youkiKigoNm2Class);
	}


	/**
	 * ケース入数のエラー、警告表示フラグを返します。
	 *
	 * @return ケース入数のエラー、警告表示フラグ
	 */
	public String getCaseIrisuClass() {
		return (String) get("CASE_IRISU_CLASS");
	}

	/**
	 * ケース入数のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param caseIrisuClass ケース入数のエラー、警告表示フラグ
	 */
	public void setCaseIrisuClass(String caseIrisuClass) {
		put("CASE_IRISU_CLASS", caseIrisuClass);
	}


	/**
	 * 出荷数量_箱数のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷数量_箱数のエラー、警告表示フラグ
	 */
	public String getSyukaSuCaseClass() {
		return (String) get("SYUKA_SU_CASE_CLASS");
	}

	/**
	 * 出荷数量_箱数のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaSuCaseClass 出荷数量_箱数のエラー、警告表示フラグ
	 */
	public void setSyukaSuCaseClass(String syukaSuCaseClass) {
		put("SYUKA_SU_CASE_CLASS", syukaSuCaseClass);
	}


	/**
	 * 出荷数量_セット数のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷数量_セット数のエラー、警告表示フラグ
	 */
	public String getSyukaSuBaraClass() {
		return (String) get("SYUKA_SU_BARA_CLASS");
	}

	/**
	 * 出荷数量_セット数のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaSuBaraClass 出荷数量_セット数のエラー、警告表示フラグ
	 */
	public void setSyukaSuBaraClass(String syukaSuBaraClass) {
		put("SYUKA_SU_BARA_CLASS", syukaSuBaraClass);
	}


	/**
	 * 出荷重量（KG)のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷重量（KG)のエラー、警告表示フラグ
	 */
	public String getSyukaAllWeigthClass() {
		return (String) get("SYUKA_ALL_WEIGTH_CLASS");
	}

	/**
	 * 出荷重量（KG)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaAllWeigthClass 出荷重量（KG)のエラー、警告表示フラグ
	 */
	public void setSyukaAllWeigthClass(String syukaAllWeigthClass) {
		put("SYUKA_ALL_WEIGTH_CLASS", syukaAllWeigthClass);
	}



}	// -- class
