package kit.edi.EdiJuchuMainte;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;
import fb.inf.pbs.PbsUtil;

/**
 * EDI受発注受信データ処理（EDI受発注データ／ﾃﾞｨﾃｰﾙ部）のレコードクラス
 */
public class EdiJuchuMainteDtRecord extends KitRecord {

	/** シリアルID */
	private static final long serialVersionUID = 9074440277197866229L;

	/** デバッグ */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public EdiJuchuMainteDtRecord() {
		super();
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input Map
	 */
	public EdiJuchuMainteDtRecord(Map<String, Object> input) {
		super(input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input HashMap
	 */
	public EdiJuchuMainteDtRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input JuchuJuchuDataInDtRecord
	 */
	public EdiJuchuMainteDtRecord(EdiJuchuMainteDtRecord input) {
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
		EdiJuchuMainteDtRecord newRec = (EdiJuchuMainteDtRecord) reco;

		setShohinCd(newRec.getShohinCd()); // Kz商品CD
		setShohinNmUriden(newRec.getShohinNmUriden()); // Kz商品名
		setYoukiKigoNm1(newRec.getYoukiKigoNm1()); // 容器名
		setSyukaSuCase(newRec.getSyukaSuCase()); // ｹｰｽ
		setSyukaSuBara(newRec.getSyukaSuBara()); // ﾊﾞﾗ
		setKtksyCd(newRec.getKtksyCd()); // 【変換後】KZ寄託者

	}

	/**
	 * 空白かどうか判定
	 *
	 * @return true
	 */
	public boolean isEmpty() {
//		boolean bEmpty = true;
//
//		// 【変換後】KZ商品CD
//		if (!PbsUtil.isEmpty(getShohinCd())) {
//			bEmpty = false;
//		}
//
//		// 【変換後】KZ発注数（ｹｰｽ）
//		if (!PbsUtil.isEmpty(getSyukaSuCase())) {
//			bEmpty = false;
//		}
//
//		// 【変換後】kz発注数（ﾊﾞﾗ）
//		if (!PbsUtil.isEmpty(getSyukaSuBara())) {
//			bEmpty = false;
//		}
//
//		return bEmpty;

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
		boolean bEquals = true;
		EdiJuchuMainteDtRecord target = (EdiJuchuMainteDtRecord) reco;
		// Kz商品CD
		if (!PbsUtil.isEqual(getShohinCd(), target.getShohinCd())) {
			setShohinCdClass(STYLE_CLASS_MODIFIED);
			bEquals = false;
		} else if (!getModified()) {
			setShohinCdClass(STYLE_CLASS_NO_EDIT);
		}

		// ｹｰｽ
		if (!PbsUtil.isEqual(getSyukaSuCase(), target.getSyukaSuCase())) {
			setSyukaSuCaseClass(STYLE_CLASS_MODIFIED);
			bEquals = false;
		} else if (!getModified()) {
			setSyukaSuCaseClass(STYLE_CLASS_NO_EDIT);
		}

		// ﾊﾞﾗ
		if (!PbsUtil.isEqual(getSyukaSuBara(), target.getSyukaSuBara())) {
			setSyukaSuBaraClass(STYLE_CLASS_MODIFIED);
			bEquals = false;
		} else if (!getModified()) {
			setSyukaSuBaraClass(STYLE_CLASS_NO_EDIT);
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
	 * @param rec JuchuJuchuDataInDtRecord
	 * @return TRUE:主キー項目が重複している / FALSE:重複無し
	 */
	public boolean isDuplicated(EdiJuchuMainteDtRecord rec){
		boolean ret = true;

		return ret;
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
			 "SHOHIN_CD_CLASS"
			,"SYUKA_SU_CASE_CLASS"
			,"SYUKA_SU_BARA_CLASS"
		};
		for (String flg : flgs) {
			put(flg, STYLE_CLASS_INIT);
		}
	}

	/**
	 * 親に主キーのありかを指示する(マスタ系必須メソッド)
	 * setの引数に主キーを指定すること
	 */
	private void setPrimaryKeys(){
		// ID、受発注行NO
		super.setPrimaryKeys("T_EDI_JYUHACYU_ID", "JYUHACYU_LINE_NO");
	}

	/**
	 * 入力値と名称をリセット
	 */
	public void reset() {
		this.modifyRecord(new EdiJuchuMainteDtRecord());

		// TODO
	}

	// -----------------------------------------
	// 固有の処理

	// -----------------------------------------
	// Getter / Setter
	// ※ ジェネレーターで作成する

	// ====編集用====
	// ====編集用====
	/**
	 * 処理区分を返します。
	 *
	 * @return 処理区分
	 */
	public String getSyoriKbn() {
		return (String) get("SYORI_KBN");
	}

	/**
	 * 処理区分をレコードに格納します。
	 *
	 * @param syoriKbn 処理区分
	 */
	public void setSyoriKbn(String syoriKbn) {
		put("SYORI_KBN", syoriKbn);
	}


	/**
	 * IDを返します。
	 *
	 * @return ID
	 */
	public String getTEdiJyuhacyuId() {
		return (String) get("T_EDI_JYUHACYU_ID");
	}

	/**
	 * IDをレコードに格納します。
	 *
	 * @param tEdiJyuhacyuId ID
	 */
	public void setTEdiJyuhacyuId(String tEdiJyuhacyuId) {
		put("T_EDI_JYUHACYU_ID", tEdiJyuhacyuId);
	}


	/**
	 * 受発注行NOを返します。
	 *
	 * @return 受発注行NO
	 */
	public String getJyuhacyuLineNo() {
		return (String) get("JYUHACYU_LINE_NO");
	}

	/**
	 * 受発注行NOをレコードに格納します。
	 *
	 * @param jyuhacyuLineNo 受発注行NO
	 */
	public void setJyuhacyuLineNo(String jyuhacyuLineNo) {
		put("JYUHACYU_LINE_NO", jyuhacyuLineNo);
	}


	/**
	 * 明細行レコード：レコード区分を返します。
	 *
	 * @return 明細行レコード：レコード区分
	 */
	public String getMrRecordKbn() {
		return (String) get("MR_RECORD_KBN");
	}

	/**
	 * 明細行レコード：レコード区分をレコードに格納します。
	 *
	 * @param mrRecordKbn 明細行レコード：レコード区分
	 */
	public void setMrRecordKbn(String mrRecordKbn) {
		put("MR_RECORD_KBN", mrRecordKbn);
	}


	/**
	 * 明細行レコード：データシリアルNoを返します。
	 *
	 * @return 明細行レコード：データシリアルNo
	 */
	public String getMrDataSerialNo() {
		return (String) get("MR_DATA_SERIAL_NO");
	}

	/**
	 * 明細行レコード：データシリアルNoをレコードに格納します。
	 *
	 * @param mrDataSerialNo 明細行レコード：データシリアルNo
	 */
	public void setMrDataSerialNo(String mrDataSerialNo) {
		put("MR_DATA_SERIAL_NO", mrDataSerialNo);
	}


	/**
	 * 明細行レコード：伝票行№を返します。
	 *
	 * @return 明細行レコード：伝票行№
	 */
	public String getMrLineNo() {
		return (String) get("MR_LINE_NO");
	}

	/**
	 * 明細行レコード：伝票行№をレコードに格納します。
	 *
	 * @param mrLineNo 明細行レコード：伝票行№
	 */
	public void setMrLineNo(String mrLineNo) {
		put("MR_LINE_NO", mrLineNo);
	}


	/**
	 * 明細行レコード：商品コードを返します。
	 *
	 * @return 明細行レコード：商品コード
	 */
	public String getMrSyohinCd() {
		return (String) get("MR_SYOHIN_CD");
	}

	/**
	 * 明細行レコード：商品コードをレコードに格納します。
	 *
	 * @param mrSyohinCd 明細行レコード：商品コード
	 */
	public void setMrSyohinCd(String mrSyohinCd) {
		put("MR_SYOHIN_CD", mrSyohinCd);
	}


	/**
	 * 明細行レコード：入数を返します。
	 *
	 * @return 明細行レコード：入数
	 */
	public String getMrIrisu() {
		return (String) get("MR_IRISU");
	}

	/**
	 * 明細行レコード：入数をレコードに格納します。
	 *
	 * @param mrIrisu 明細行レコード：入数
	 */
	public void setMrIrisu(String mrIrisu) {
		put("MR_IRISU", mrIrisu);
	}


	/**
	 * 明細行レコード：数量を返します。
	 *
	 * @return 明細行レコード：数量
	 */
	public String getMrSuryo() {
		return (String) get("MR_SURYO");
	}

	/**
	 * 明細行レコード：数量をレコードに格納します。
	 *
	 * @param mrSuryo 明細行レコード：数量
	 */
	public void setMrSuryo(String mrSuryo) {
		put("MR_SURYO", mrSuryo);
	}


	/**
	 * 明細行レコード：単位を返します。
	 *
	 * @return 明細行レコード：単位
	 */
	public String getMrTani() {
		return (String) get("MR_TANI");
	}

	/**
	 * 明細行レコード：単位をレコードに格納します。
	 *
	 * @param mrTani 明細行レコード：単位
	 */
	public void setMrTani(String mrTani) {
		put("MR_TANI", mrTani);
	}


	/**
	 * 明細行レコード：摘要を返します。
	 *
	 * @return 明細行レコード：摘要
	 */
	public String getMrTekiyou() {
		return (String) get("MR_TEKIYOU");
	}

	/**
	 * 明細行レコード：摘要をレコードに格納します。
	 *
	 * @param mrTekiyou 明細行レコード：摘要
	 */
	public void setMrTekiyou(String mrTekiyou) {
		put("MR_TEKIYOU", mrTekiyou);
	}


	/**
	 * 明細行レコード：余白を返します。
	 *
	 * @return 明細行レコード：余白
	 */
	public String getMrYohaku() {
		return (String) get("MR_YOHAKU");
	}

	/**
	 * 明細行レコード：余白をレコードに格納します。
	 *
	 * @param mrYohaku 明細行レコード：余白
	 */
	public void setMrYohaku(String mrYohaku) {
		put("MR_YOHAKU", mrYohaku);
	}


	/**
	 * 明細行オプションレコード：レコード区分を返します。
	 *
	 * @return 明細行オプションレコード：レコード区分
	 */
	public String getMoRecordKbn() {
		return (String) get("MO_RECORD_KBN");
	}

	/**
	 * 明細行オプションレコード：レコード区分をレコードに格納します。
	 *
	 * @param moRecordKbn 明細行オプションレコード：レコード区分
	 */
	public void setMoRecordKbn(String moRecordKbn) {
		put("MO_RECORD_KBN", moRecordKbn);
	}


	/**
	 * 明細行オプションレコード：データシリアルNoを返します。
	 *
	 * @return 明細行オプションレコード：データシリアルNo
	 */
	public String getMoDataSerialNo() {
		return (String) get("MO_DATA_SERIAL_NO");
	}

	/**
	 * 明細行オプションレコード：データシリアルNoをレコードに格納します。
	 *
	 * @param moDataSerialNo 明細行オプションレコード：データシリアルNo
	 */
	public void setMoDataSerialNo(String moDataSerialNo) {
		put("MO_DATA_SERIAL_NO", moDataSerialNo);
	}


	/**
	 * 明細行オプションレコード：伝票行№を返します。
	 *
	 * @return 明細行オプションレコード：伝票行№
	 */
	public String getMoLineNo() {
		return (String) get("MO_LINE_NO");
	}

	/**
	 * 明細行オプションレコード：伝票行№をレコードに格納します。
	 *
	 * @param moLineNo 明細行オプションレコード：伝票行№
	 */
	public void setMoLineNo(String moLineNo) {
		put("MO_LINE_NO", moLineNo);
	}


	/**
	 * 明細行オプションレコード：商品名を返します。
	 *
	 * @return 明細行オプションレコード：商品名
	 */
	public String getMoSyohinNm() {
		return (String) get("MO_SYOHIN_NM");
	}

	/**
	 * 明細行オプションレコード：商品名をレコードに格納します。
	 *
	 * @param moSyohinNm 明細行オプションレコード：商品名
	 */
	public void setMoSyohinNm(String moSyohinNm) {
		put("MO_SYOHIN_NM", moSyohinNm);
	}


	/**
	 * 明細行オプションレコード：余白を返します。
	 *
	 * @return 明細行オプションレコード：余白
	 */
	public String getMoYohaku() {
		return (String) get("MO_YOHAKU");
	}

	/**
	 * 明細行オプションレコード：余白をレコードに格納します。
	 *
	 * @param moYohaku 明細行オプションレコード：余白
	 */
	public void setMoYohaku(String moYohaku) {
		put("MO_YOHAKU", moYohaku);
	}


	/**
	 * 明細行オプションレコード：日本語区分を返します。
	 *
	 * @return 明細行オプションレコード：日本語区分
	 */
	public String getMoNihongoKbn() {
		return (String) get("MO_NIHONGO_KBN");
	}

	/**
	 * 明細行オプションレコード：日本語区分をレコードに格納します。
	 *
	 * @param moNihongoKbn 明細行オプションレコード：日本語区分
	 */
	public void setMoNihongoKbn(String moNihongoKbn) {
		put("MO_NIHONGO_KBN", moNihongoKbn);
	}


	/**
	 * 【変換後】Kz商品CDを返します。
	 *
	 * @return 【変換後】Kz商品CD
	 */
	public String getShohinCd() {
		return (String) get("SHOHIN_CD");
	}

	/**
	 * 【変換後】Kz商品CDをレコードに格納します。
	 *
	 * @param shohinCd 【変換後】Kz商品CD
	 */
	public void setShohinCd(String shohinCd) {
		put("SHOHIN_CD", shohinCd);
	}


	/**
	 * Kz商品名を返します。
	 *
	 * @return Kz商品名
	 */
	public String getShohinNmUriden() {
		return (String) get("SHOHIN_NM_URIDEN");
	}

	/**
	 * Kz商品名をレコードに格納します。
	 *
	 * @param shohinNmUriden Kz商品名
	 */
	public void setShohinNmUriden(String shohinNmUriden) {
		put("SHOHIN_NM_URIDEN", shohinNmUriden);
	}


	/**
	 * 容器名を返します。
	 *
	 * @return 容器名
	 */
	public String getYoukiKigoNm1() {
		return (String) get("YOUKI_KIGO_NM1");
	}

	/**
	 * 容器名をレコードに格納します。
	 *
	 * @param youkiKigoNm1 容器名
	 */
	public void setYoukiKigoNm1(String youkiKigoNm1) {
		put("YOUKI_KIGO_NM1", youkiKigoNm1);
	}


	/**
	 * 【変換後】Kz寄託者CDを返します。
	 *
	 * @return 【変換後】Kz寄託者CD
	 */
	public String getKtksyCd() {
		return (String) get("KTKSY_CD");
	}

	/**
	 * 【変換後】Kz寄託者CDをレコードに格納します。
	 *
	 * @param ktksyCd 【変換後】Kz寄託者CD
	 */
	public void setKtksyCd(String ktksyCd) {
		put("KTKSY_CD", ktksyCd);
	}


	/**
	 * 【変換後】KZ発注数（ｹｰｽ）を返します。
	 *
	 * @return 【変換後】KZ発注数（ｹｰｽ）
	 */
	public String getSyukaSuCase() {
		return (String) get("SYUKA_SU_CASE");
	}

	/**
	 * 【変換後】KZ発注数（ｹｰｽ）をレコードに格納します。
	 *
	 * @param syukaSuCase 【変換後】KZ発注数（ｹｰｽ）
	 */
	public void setSyukaSuCase(String syukaSuCase) {
		put("SYUKA_SU_CASE", syukaSuCase);
	}


	/**
	 * 【変換後】kz発注数（ﾊﾞﾗ）を返します。
	 *
	 * @return 【変換後】kz発注数（ﾊﾞﾗ）
	 */
	public String getSyukaSuBara() {
		return (String) get("SYUKA_SU_BARA");
	}

	/**
	 * 【変換後】kz発注数（ﾊﾞﾗ）をレコードに格納します。
	 *
	 * @param syukaSuBara 【変換後】kz発注数（ﾊﾞﾗ）
	 */
	public void setSyukaSuBara(String syukaSuBara) {
		put("SYUKA_SU_BARA", syukaSuBara);
	}


	/**
	 * エラー区分を返します。
	 *
	 * @return エラー区分
	 */
	public String getErrorKbn() {
		return (String) get("ERROR_KBN");
	}

	/**
	 * エラー区分をレコードに格納します。
	 *
	 * @param errorKbn エラー区分
	 */
	public void setErrorKbn(String errorKbn) {
		put("ERROR_KBN", errorKbn);
	}


	/**
	 * 許容日を返します。
	 *
	 * @return 許容日
	 */
	public String getKyoyobi() {
		return (String) get("KYOYOBI");
	}

	/**
	 * 許容日をレコードに格納します。
	 *
	 * @param kyoyobi 許容日
	 */
	public void setKyoyobi(String kyoyobi) {
		put("KYOYOBI", kyoyobi);
	}


	// ====変更前表示用====
	/**
	 * 摘要を返します。
	 *
	 * @return 摘要
	 */
	public String getMrTekiyouView() {
		return (String) get("MR_TEKIYOU_VIEW");
	}

	/**
	 * 摘要をレコードに格納します。
	 *
	 * @param mrTekiyouView 摘要
	 */
	public void setMrTekiyouView(String mrTekiyouView) {
		put("MR_TEKIYOU_VIEW", mrTekiyouView);
	}


	// ====エラー用====
	/**
	 * Kz商品CDのエラー、警告表示フラグを返します。
	 *
	 * @return Kz商品CDのエラー、警告表示フラグ
	 */
	public String getShohinCdClass() {
		return (String) get("SHOHIN_CD_CLASS");
	}

	/**
	 * Kz商品CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param shohinCdClass Kz商品CDのエラー、警告表示フラグ
	 */
	public void setShohinCdClass(String shohinCdClass) {
		put("SHOHIN_CD_CLASS", shohinCdClass);
	}


	/**
	 * ｹｰｽのエラー、警告表示フラグを返します。
	 *
	 * @return ｹｰｽのエラー、警告表示フラグ
	 */
	public String getSyukaSuCaseClass() {
		return (String) get("SYUKA_SU_CASE_CLASS");
	}

	/**
	 * ｹｰｽのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaSuCaseClass ｹｰｽのエラー、警告表示フラグ
	 */
	public void setSyukaSuCaseClass(String syukaSuCaseClass) {
		put("SYUKA_SU_CASE_CLASS", syukaSuCaseClass);
	}


	/**
	 * ﾊﾞﾗのエラー、警告表示フラグを返します。
	 *
	 * @return ﾊﾞﾗのエラー、警告表示フラグ
	 */
	public String getSyukaSuBaraClass() {
		return (String) get("SYUKA_SU_BARA_CLASS");
	}

	/**
	 * ﾊﾞﾗのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaSuBaraClass ﾊﾞﾗのエラー、警告表示フラグ
	 */
	public void setSyukaSuBaraClass(String syukaSuBaraClass) {
		put("SYUKA_SU_BARA_CLASS", syukaSuBaraClass);
	}

	/**
	 * エラー区分のエラー、警告表示フラグを返します。
	 *
	 * @return エラー区分のエラー、警告表示フラグ
	 */
	public String getErrorKbnClass() {
		return (String) get("ERROR_KBN_CLASS");
	}

	/**
	 * エラー区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param errorKbnClass エラー区分のエラー、警告表示フラグ
	 */
	public void setErrorKbnClass(String errorKbnClass) {
		put("ERROR_KBN_CLASS", errorKbnClass);
	}

}	// -- class
