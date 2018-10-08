package kit.edi.EdiJuchuMainte;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;
import fb.inf.pbs.PbsUtil;

/**
 * EDI受発注受信データ処理（EDI受発注データ／ﾍｯﾀﾞｰ部）のレコードクラス
 */
public class EdiJuchuMainteRecord extends KitRecord {

	/** シリアルID */
	private static final long serialVersionUID = -2451288206118253406L;

	/** デバッグ */
	boolean isDebug_ = false;


	/** コンストラクタ */
	public EdiJuchuMainteRecord() {
		super();
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input Map
	 */
	public EdiJuchuMainteRecord(Map<String, Object> input) {
		super(input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input HashMap
	 */
	public EdiJuchuMainteRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input KuracKuraChokuDataInRecord
	 */
	public EdiJuchuMainteRecord(EdiJuchuMainteRecord input) {
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
		EdiJuchuMainteRecord newRec = (EdiJuchuMainteRecord) reco;

		setSyoriKbn(newRec.getSyoriKbn()); // 処理区分
		setSyukaYoteiDt(newRec.getSyukaYoteiDt()); // kz出荷予定日
		//setMinasiDt(newRec.getMinasiDt()); // Kzミナシ日
		setTatesnCd(newRec.getTatesnCd()); // Kz縦線CD
		setOrositenCd1Jiten(newRec.getOrositenCd1Jiten()); // 特約店CD
		setOrositenNm1JitenRyaku(newRec.getOrositenNm1JitenRyaku()); // 特約店名
		setOrositenCdDepo(newRec.getOrositenCdDepo()); // デポ店CD
		setOrositenNmDepoRyaku(newRec.getOrositenNmDepoRyaku()); // デポ店名
		setOrositenCd2Jiten(newRec.getOrositenCd2Jiten()); // 2次店CD
		setOrositenNm2JitenRyaku(newRec.getOrositenNm2JitenRyaku()); // 2次店名
		setOrositenCd3Jiten(newRec.getOrositenCd3Jiten()); // 3次店CD
		setOrositenNm3JitenRyaku(newRec.getOrositenNm3JitenRyaku()); // 3次店名
		//setOrositenCdLast(newRec.getOrositenCdLast());// 最終送荷先卸CD
		setSyukaDtCyuiKbn(newRec.getSyukaDtCyuiKbn()); // 出荷日注意
		setHacyuTantosyaNm(newRec.getHacyuTantosyaNm()); // 発注担当者
		setHacyumotoTel(newRec.getHacyumotoTel()); // 発注元TEL
		setAddress1Jiten(newRec.getAddress1Jiten());// 特約店住所
		setTel1Jiten(newRec.getTel1Jiten());// 特約店Tel
		setAddressDepo(newRec.getAddressDepo());// デポ店住所
		setTelDepo(newRec.getTelDepo());// デポ店Tel
		setAddress2Jiten(newRec.getAddress2Jiten());// 2次店住所
		setTel2Jiten(newRec.getTel2Jiten());//  2次店Tel
		setAddress3Jiten(newRec.getAddress3Jiten());// 3次店住所
		setTel3Jiten(newRec.getTel3Jiten());// 3次店Tel
		//setEdiJsyohinTekiyochekKbn(newRec.getEdiJsyohinTekiyochekKbn());//EDI受発注商品摘要欄確認区分
	}

	/**
	 * 空白かどうか判定
	 *
	 * @return true
	 */
	public boolean isEmpty() {
		boolean bEmpty = false;

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

		EdiJuchuMainteRecord target = (EdiJuchuMainteRecord) reco;

		// 処理区分
		if (!PbsUtil.isEqual(getSyoriKbn(), target.getSyoriKbn())) {
			setSyoriKbnClass(STYLE_CLASS_MODIFIED);
			bEquals = false;
		} else if (!getModified()) {
			setSyoriKbnClass(STYLE_CLASS_NO_EDIT);
		}

		// 【変換後】kz出荷予定日
		if (!PbsUtil.isEqual(getSyukaYoteiDt(), target.getSyukaYoteiDt())) {
			setSyukaYoteiDtClass(STYLE_CLASS_MODIFIED);
			bEquals = false;
		} else if (!getModified()) {
			setSyukaYoteiDtClass(STYLE_CLASS_NO_EDIT);
		}

//		// 【変換後】ﾐﾅｼ日付
//		if (!PbsUtil.isEqual(getMinasiDt(), target.getMinasiDt())) {
//			setMinasiDtClass(STYLE_CLASS_MODIFIED);
//			bEquals = false;
//		} else if (!getModified()) {
//			setMinasiDtClass(STYLE_CLASS_NO_EDIT);
//		}

		// 【変換後】kz縦線CD
		if (!PbsUtil.isEqual(getTatesnCd(), target.getTatesnCd())) {
			setTatesnCdClass(STYLE_CLASS_MODIFIED);
			bEquals = false;
		} else if (!getModified()) {
			setTatesnCdClass(STYLE_CLASS_NO_EDIT);
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
	 * @param rec ZaikoSeihinMoveDataInRecord
	 * @return TRUE:主キー項目が重複している / FALSE:重複無し
	 */
	public boolean isDuplicated(EdiJuchuMainteRecord rec){
		boolean ret = true;

		// 入力日付 + 入力連番
		//TODO


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
			 "SYORI_KBN_CLASS"
			,"SYUKA_YOTEI_DT_CLASS"
			,"MINASI_DT_CLASS"
			,"TATESN_CD_CLASS"
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
		// ID
		super.setPrimaryKeys("T_EDI_JYUHACYU_ID");
	}

	/**
	 * 入力値と名称をリセット
	 */
	public void reset() {
		this.modifyRecord(new EdiJuchuMainteRecord());
		// TODO
	}

	// -----------------------------------------
	// 固有の処理

	// -----------------------------------------
	// Getter / Setter
	// ※ ジェネレーターで作成する

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
	 * 受信日時を返します。
	 *
	 * @return 受信日時
	 */
	public String getJyusinDatetime() {
		return (String) get("JYUSIN_DATETIME");
	}

	/**
	 * 受信日時をレコードに格納します。
	 *
	 * @param jyusinDatetime 受信日時
	 */
	public void setJyusinDatetime(String jyusinDatetime) {
		put("JYUSIN_DATETIME", jyusinDatetime);
	}



	/**
	 * 更新者を返します。
	 *
	 * @return 更新者
	 */
	public String getSgyosyaNm() {
		return (String) get("SGYOSYA_NM");
	}

	/**
	 * 更新者をレコードに格納します。
	 *
	 * @param sgyosyaNm 更新者
	 */
	public void setSgyosyaNm(String sgyosyaNm) {
		put("SGYOSYA_NM", sgyosyaNm);
	}


	/**
	 * 最終送荷先を返します。
	 *
	 * @return 最終送荷先
	 */
	public String getLastSouniSaki() {
		return (String) get("LAST_SOUNI_SAKI");
	}

	/**
	 * 最終送荷先をレコードに格納します。
	 *
	 * @param lastSouniSaki 最終送荷先
	 */
	public void setLastSouniSaki(String lastSouniSaki) {
		put("LAST_SOUNI_SAKI", lastSouniSaki);
	}


	/**
	 * ﾃﾞｰﾀ発信元を返します。
	 *
	 * @return ﾃﾞｰﾀ発信元
	 */
	public String getDataHasinMoto() {
		return (String) get("DATA_HASIN_MOTO");
	}

	/**
	 * ﾃﾞｰﾀ発信元をレコードに格納します。
	 *
	 * @param dataHasinMoto ﾃﾞｰﾀ発信元
	 */
	public void setDataHasinMoto(String dataHasinMoto) {
		put("DATA_HASIN_MOTO", dataHasinMoto);
	}


	/**
	 * IDを返します。
	 *
	 * @return ID
	 */
	public String getEdiJyuhacyuId() {
		return (String) get("T_EDI_JYUHACYU_ID");
	}

	/**
	 * IDをレコードに格納します。
	 *
	 * @param tEdiJyuhacyuId ID
	 */
	public void setEdiJyuhacyuId(String tEdiJyuhacyuId) {
		put("T_EDI_JYUHACYU_ID", tEdiJyuhacyuId);
	}


	/**
	 * 出荷日注意を返します。
	 *
	 * @return 出荷日注意
	 */
	public String getSyukaDtCyuiKbn() {
		return (String) get("SYUKA_DT_CYUI_KBN");
	}

	/**
	 * 出荷日注意をレコードに格納します。
	 *
	 * @param syukaDtCyuiKbn 出荷日注意
	 */
	public void setSyukaDtCyuiKbn(String syukaDtCyuiKbn) {
		put("SYUKA_DT_CYUI_KBN", syukaDtCyuiKbn);
	}


	/**
	 * 発注担当者を返します。
	 *
	 * @return 発注担当者
	 */
	public String getHacyuTantosyaNm() {
		return (String) get("HACYU_TANTOSYA_NM");
	}

	/**
	 * 発注担当者をレコードに格納します。
	 *
	 * @param hacyuTantosyaNm 発注担当者
	 */
	public void setHacyuTantosyaNm(String hacyuTantosyaNm) {
		put("HACYU_TANTOSYA_NM", hacyuTantosyaNm);
	}


	/**
	 * 発注元TELを返します。
	 *
	 * @return 発注元TEL
	 */
	public String getHacyumotoTel() {
		return (String) get("HACYUMOTO_TEL");
	}

	/**
	 * 発注元TELをレコードに格納します。
	 *
	 * @param hacyumotoTel 発注元TEL
	 */
	public void setHacyumotoTel(String hacyumotoTel) {
		put("HACYUMOTO_TEL", hacyumotoTel);
	}


	/**
	 * 特約店CDを返します。
	 *
	 * @return 特約店CD
	 */
	public String getOrositenCd1Jiten() {
		return (String) get("OROSITEN_CD_1JITEN");
	}

	/**
	 * 特約店CDをレコードに格納します。
	 *
	 * @param orositenCd1Jiten 特約店CD
	 */
	public void setOrositenCd1Jiten(String orositenCd1Jiten) {
		put("OROSITEN_CD_1JITEN", orositenCd1Jiten);
	}


	/**
	 * 特約店名を返します。
	 *
	 * @return 特約店名
	 */
	public String getOrositenNm1JitenRyaku() {
		return (String) get("OROSITEN_NM_1JITEN_RYAKU");
	}

	/**
	 * 特約店名をレコードに格納します。
	 *
	 * @param orositenNm1JitenRyaku 特約店名
	 */
	public void setOrositenNm1JitenRyaku(String orositenNm1JitenRyaku) {
		put("OROSITEN_NM_1JITEN_RYAKU", orositenNm1JitenRyaku);
	}


	/**
	 * デポ店CDを返します。
	 *
	 * @return デポ店CD
	 */
	public String getOrositenCdDepo() {
		return (String) get("OROSITEN_CD_DEPO");
	}

	/**
	 * デポ店CDをレコードに格納します。
	 *
	 * @param orositenCdDepo デポ店CD
	 */
	public void setOrositenCdDepo(String orositenCdDepo) {
		put("OROSITEN_CD_DEPO", orositenCdDepo);
	}


	/**
	 * デポ店名を返します。
	 *
	 * @return デポ店名
	 */
	public String getOrositenNmDepoRyaku() {
		return (String) get("OROSITEN_NM_DEPO_RYAKU");
	}

	/**
	 * デポ店名をレコードに格納します。
	 *
	 * @param orositenNmDepoRyaku デポ店名
	 */
	public void setOrositenNmDepoRyaku(String orositenNmDepoRyaku) {
		put("OROSITEN_NM_DEPO_RYAKU", orositenNmDepoRyaku);
	}


	/**
	 * 2次店CDを返します。
	 *
	 * @return 2次店CD
	 */
	public String getOrositenCd2Jiten() {
		return (String) get("OROSITEN_CD_2JITEN");
	}

	/**
	 * 2次店CDをレコードに格納します。
	 *
	 * @param orositenCd2Jiten 2次店CD
	 */
	public void setOrositenCd2Jiten(String orositenCd2Jiten) {
		put("OROSITEN_CD_2JITEN", orositenCd2Jiten);
	}


	/**
	 * 2次店名を返します。
	 *
	 * @return 2次店名
	 */
	public String getOrositenNm2JitenRyaku() {
		return (String) get("OROSITEN_NM_2JITEN_RYAKU");
	}

	/**
	 * 2次店名をレコードに格納します。
	 *
	 * @param orositenNm2JitenRyaku 2次店名
	 */
	public void setOrositenNm2JitenRyaku(String orositenNm2JitenRyaku) {
		put("OROSITEN_NM_2JITEN_RYAKU", orositenNm2JitenRyaku);
	}


	/**
	 * 3次店CDを返します。
	 *
	 * @return 3次店CD
	 */
	public String getOrositenCd3Jiten() {
		return (String) get("OROSITEN_CD_3JITEN");
	}

	/**
	 * 3次店CDをレコードに格納します。
	 *
	 * @param orositenCd3Jiten 3次店CD
	 */
	public void setOrositenCd3Jiten(String orositenCd3Jiten) {
		put("OROSITEN_CD_3JITEN", orositenCd3Jiten);
	}


	/**
	 * 3次店名を返します。
	 *
	 * @return 3次店名
	 */
	public String getOrositenNm3JitenRyaku() {
		return (String) get("OROSITEN_NM_3JITEN_RYAKU");
	}

	/**
	 * 3次店名をレコードに格納します。
	 *
	 * @param orositenNm3JitenRyaku 3次店名
	 */
	public void setOrositenNm3JitenRyaku(String orositenNm3JitenRyaku) {
		put("OROSITEN_NM_3JITEN_RYAKU", orositenNm3JitenRyaku);
	}


	/**
	 * 特約店住所を返します。
	 *
	 * @return 特約店住所
	 */
	public String getAddress1Jiten() {
		return (String) get("ADDRESS_1JITEN");
	}

	/**
	 * 特約店住所をレコードに格納します。
	 *
	 * @param address1Jiten 特約店住所
	 */
	public void setAddress1Jiten(String address1Jiten) {
		put("ADDRESS_1JITEN", address1Jiten);
	}


	/**
	 * 特約店TELを返します。
	 *
	 * @return 特約店TEL
	 */
	public String getTel1Jiten() {
		return (String) get("TEL_1JITEN");
	}

	/**
	 * 特約店TELをレコードに格納します。
	 *
	 * @param tel1Jiten 特約店TEL
	 */
	public void setTel1Jiten(String tel1Jiten) {
		put("TEL_1JITEN", tel1Jiten);
	}


	/**
	 * デポ店住所を返します。
	 *
	 * @return デポ店住所
	 */
	public String getAddressDepo() {
		return (String) get("ADDRESS_DEPO");
	}

	/**
	 * デポ店住所をレコードに格納します。
	 *
	 * @param addressDepo デポ店住所
	 */
	public void setAddressDepo(String addressDepo) {
		put("ADDRESS_DEPO", addressDepo);
	}


	/**
	 * デポ店TELを返します。
	 *
	 * @return デポ店TEL
	 */
	public String getTelDepo() {
		return (String) get("TEL_DEPO");
	}

	/**
	 * デポ店TELをレコードに格納します。
	 *
	 * @param telDepo デポ店TEL
	 */
	public void setTelDepo(String telDepo) {
		put("TEL_DEPO", telDepo);
	}


	/**
	 * 二次店住所を返します。
	 *
	 * @return 二次店住所
	 */
	public String getAddress2Jiten() {
		return (String) get("ADDRESS_2JITEN");
	}

	/**
	 * 二次店住所をレコードに格納します。
	 *
	 * @param address2Jiten 二次店住所
	 */
	public void setAddress2Jiten(String address2Jiten) {
		put("ADDRESS_2JITEN", address2Jiten);
	}


	/**
	 * 二次店TELを返します。
	 *
	 * @return 二次店TEL
	 */
	public String getTel2Jiten() {
		return (String) get("TEL_2JITEN");
	}

	/**
	 * 二次店TELをレコードに格納します。
	 *
	 * @param tel2Jiten 二次店TEL
	 */
	public void setTel2Jiten(String tel2Jiten) {
		put("TEL_2JITEN", tel2Jiten);
	}


	/**
	 * 三次店住所を返します。
	 *
	 * @return 三次店住所
	 */
	public String getAddress3Jiten() {
		return (String) get("ADDRESS_3JITEN");
	}

	/**
	 * 三次店住所をレコードに格納します。
	 *
	 * @param address3Jiten 三次店住所
	 */
	public void setAddress3Jiten(String address3Jiten) {
		put("ADDRESS_3JITEN", address3Jiten);
	}


	/**
	 * 三次店TELを返します。
	 *
	 * @return 三次店TEL
	 */
	public String getTel3Jiten() {
		return (String) get("TEL_3JITEN");
	}

	/**
	 * 三次店TELをレコードに格納します。
	 *
	 * @param tel3Jiten 三次店TEL
	 */
	public void setTel3Jiten(String tel3Jiten) {
		put("TEL_3JITEN", tel3Jiten);
	}

	/**
	 * ｵﾝﾗｲﾝ受信回数を返します。
	 *
	 * @return ｵﾝﾗｲﾝ受信回数
	 */
	public String getOnlineJyusinKaisu() {
		return (String) get("ONLINE_JYUSIN_KAISU");
	}

	/**
	 * ｵﾝﾗｲﾝ受信回数をレコードに格納します。
	 *
	 * @param onlineJyusinKaisu ｵﾝﾗｲﾝ受信回数
	 */
	public void setOnlineJyusinKaisu(String onlineJyusinKaisu) {
		put("ONLINE_JYUSIN_KAISU", onlineJyusinKaisu);
	}


	/**
	 * 受信日を返します。
	 *
	 * @return 受信日
	 */
	public String getJyusinDt() {
		return (String) get("JYUSIN_DT");
	}

	/**
	 * 受信日をレコードに格納します。
	 *
	 * @param jyusinDt 受信日
	 */
	public void setJyusinDt(String jyusinDt) {
		put("JYUSIN_DT", jyusinDt);
	}


	/**
	 * 受信時間を返します。
	 *
	 * @return 受信時間
	 */
	public String getJyusinTm() {
		return (String) get("JYUSIN_TM");
	}

	/**
	 * 受信時間をレコードに格納します。
	 *
	 * @param jyusinTm 受信時間
	 */
	public void setJyusinTm(String jyusinTm) {
		put("JYUSIN_TM", jyusinTm);
	}


	/**
	 * 入力伝票グループNOを返します。
	 *
	 * @return 入力伝票グループNO
	 */
	public String getDenpyoGroupNo() {
		return (String) get("DENPYO_GROUP_NO");
	}

	/**
	 * 入力伝票グループNOをレコードに格納します。
	 *
	 * @param denpyoGroupNo 入力伝票グループNO
	 */
	public void setDenpyoGroupNo(String denpyoGroupNo) {
		put("DENPYO_GROUP_NO", denpyoGroupNo);
	}


	/**
	 * ファイルヘッダーレコード：レコード区分を返します。
	 *
	 * @return ファイルヘッダーレコード：レコード区分
	 */
	public String getFhRecordKbn() {
		return (String) get("FH_RECORD_KBN");
	}

	/**
	 * ファイルヘッダーレコード：レコード区分をレコードに格納します。
	 *
	 * @param fhRecordKbn ファイルヘッダーレコード：レコード区分
	 */
	public void setFhRecordKbn(String fhRecordKbn) {
		put("FH_RECORD_KBN", fhRecordKbn);
	}


	/**
	 * ファイルヘッダーレコード：データシリアルNoを返します。
	 *
	 * @return ファイルヘッダーレコード：データシリアルNo
	 */
	public String getFhDataSerialNo() {
		return (String) get("FH_DATA_SERIAL_NO");
	}

	/**
	 * ファイルヘッダーレコード：データシリアルNoをレコードに格納します。
	 *
	 * @param fhDataSerialNo ファイルヘッダーレコード：データシリアルNo
	 */
	public void setFhDataSerialNo(String fhDataSerialNo) {
		put("FH_DATA_SERIAL_NO", fhDataSerialNo);
	}


	/**
	 * ファイルヘッダーレコード：データ種別を返します。
	 *
	 * @return ファイルヘッダーレコード：データ種別
	 */
	public String getFhDateSyubetu() {
		return (String) get("FH_DATE_SYUBETU");
	}

	/**
	 * ファイルヘッダーレコード：データ種別をレコードに格納します。
	 *
	 * @param fhDateSyubetu ファイルヘッダーレコード：データ種別
	 */
	public void setFhDateSyubetu(String fhDateSyubetu) {
		put("FH_DATE_SYUBETU", fhDateSyubetu);
	}


	/**
	 * ファイルヘッダーレコード：データ作成日を返します。
	 *
	 * @return ファイルヘッダーレコード：データ作成日
	 */
	public String getFhDataSakuseiDt() {
		return (String) get("FH_DATA_SAKUSEI_DT");
	}

	/**
	 * ファイルヘッダーレコード：データ作成日をレコードに格納します。
	 *
	 * @param fhDataSakuseiDt ファイルヘッダーレコード：データ作成日
	 */
	public void setFhDataSakuseiDt(String fhDataSakuseiDt) {
		put("FH_DATA_SAKUSEI_DT", fhDataSakuseiDt);
	}


	/**
	 * ファイルヘッダーレコード：データ作成時刻を返します。
	 *
	 * @return ファイルヘッダーレコード：データ作成時刻
	 */
	public String getFhDataSakuseiTm() {
		return (String) get("FH_DATA_SAKUSEI_TM");
	}

	/**
	 * ファイルヘッダーレコード：データ作成時刻をレコードに格納します。
	 *
	 * @param fhDataSakuseiTm ファイルヘッダーレコード：データ作成時刻
	 */
	public void setFhDataSakuseiTm(String fhDataSakuseiTm) {
		put("FH_DATA_SAKUSEI_TM", fhDataSakuseiTm);
	}


	/**
	 * ファイルヘッダーレコード：ファイルNOを返します。
	 *
	 * @return ファイルヘッダーレコード：ファイルNO
	 */
	public String getFhFileNo() {
		return (String) get("FH_FILE_NO");
	}

	/**
	 * ファイルヘッダーレコード：ファイルNOをレコードに格納します。
	 *
	 * @param fhFileNo ファイルヘッダーレコード：ファイルNO
	 */
	public void setFhFileNo(String fhFileNo) {
		put("FH_FILE_NO", fhFileNo);
	}


	/**
	 * ファイルヘッダーレコード：データ処理日を返します。
	 *
	 * @return ファイルヘッダーレコード：データ処理日
	 */
	public String getFhDataSyoriDt() {
		return (String) get("FH_DATA_SYORI_DT");
	}

	/**
	 * ファイルヘッダーレコード：データ処理日をレコードに格納します。
	 *
	 * @param fhDataSyoriDt ファイルヘッダーレコード：データ処理日
	 */
	public void setFhDataSyoriDt(String fhDataSyoriDt) {
		put("FH_DATA_SYORI_DT", fhDataSyoriDt);
	}


	/**
	 * ファイルヘッダーレコード：利用者企業コード（受け手）荷主を返します。
	 *
	 * @return ファイルヘッダーレコード：利用者企業コード（受け手）荷主
	 */
	public String getFhRiyouKigyoCd() {
		return (String) get("FH_RIYOU_KIGYO_CD");
	}

	/**
	 * ファイルヘッダーレコード：利用者企業コード（受け手）荷主をレコードに格納します。
	 *
	 * @param fhRiyouKigyoCd ファイルヘッダーレコード：利用者企業コード（受け手）荷主
	 */
	public void setFhRiyouKigyoCd(String fhRiyouKigyoCd) {
		put("FH_RIYOU_KIGYO_CD", fhRiyouKigyoCd);
	}


	/**
	 * ファイルヘッダーレコード：データ送信元センターコードを返します。
	 *
	 * @return ファイルヘッダーレコード：データ送信元センターコード
	 */
	public String getFhCenterCd() {
		return (String) get("FH_CENTER_CD");
	}

	/**
	 * ファイルヘッダーレコード：データ送信元センターコードをレコードに格納します。
	 *
	 * @param fhCenterCd ファイルヘッダーレコード：データ送信元センターコード
	 */
	public void setFhCenterCd(String fhCenterCd) {
		put("FH_CENTER_CD", fhCenterCd);
	}


	/**
	 * ファイルヘッダーレコード：データ送信元センターコード（予備）を返します。
	 *
	 * @return ファイルヘッダーレコード：データ送信元センターコード（予備）
	 */
	public String getFhCenterCdYobi() {
		return (String) get("FH_CENTER_CD_YOBI");
	}

	/**
	 * ファイルヘッダーレコード：データ送信元センターコード（予備）をレコードに格納します。
	 *
	 * @param fhCenterCdYobi ファイルヘッダーレコード：データ送信元センターコード（予備）
	 */
	public void setFhCenterCdYobi(String fhCenterCdYobi) {
		put("FH_CENTER_CD_YOBI", fhCenterCdYobi);
	}


	/**
	 * ファイルヘッダーレコード：最終送信先コードを返します。
	 *
	 * @return ファイルヘッダーレコード：最終送信先コード
	 */
	public String getFhLastSendCd() {
		return (String) get("FH_LAST_SEND_CD");
	}

	/**
	 * ファイルヘッダーレコード：最終送信先コードをレコードに格納します。
	 *
	 * @param fhLastSendCd ファイルヘッダーレコード：最終送信先コード
	 */
	public void setFhLastSendCd(String fhLastSendCd) {
		put("FH_LAST_SEND_CD", fhLastSendCd);
	}


	/**
	 * ファイルヘッダーレコード：最終ステーションアドレスを返します。
	 *
	 * @return ファイルヘッダーレコード：最終ステーションアドレス
	 */
	public String getFhLastStationAddr() {
		return (String) get("FH_LAST_STATION_ADDR");
	}

	/**
	 * ファイルヘッダーレコード：最終ステーションアドレスをレコードに格納します。
	 *
	 * @param fhLastStationAddr ファイルヘッダーレコード：最終ステーションアドレス
	 */
	public void setFhLastStationAddr(String fhLastStationAddr) {
		put("FH_LAST_STATION_ADDR", fhLastStationAddr);
	}


	/**
	 * ファイルヘッダーレコード：直接送信先コードを返します。
	 *
	 * @return ファイルヘッダーレコード：直接送信先コード
	 */
	public String getFhDirectSendCd() {
		return (String) get("FH_DIRECT_SEND_CD");
	}

	/**
	 * ファイルヘッダーレコード：直接送信先コードをレコードに格納します。
	 *
	 * @param fhDirectSendCd ファイルヘッダーレコード：直接送信先コード
	 */
	public void setFhDirectSendCd(String fhDirectSendCd) {
		put("FH_DIRECT_SEND_CD", fhDirectSendCd);
	}


	/**
	 * ファイルヘッダーレコード：直接ステーションアドレスを返します。
	 *
	 * @return ファイルヘッダーレコード：直接ステーションアドレス
	 */
	public String getFhDirectStationAddr() {
		return (String) get("FH_DIRECT_STATION_ADDR");
	}

	/**
	 * ファイルヘッダーレコード：直接ステーションアドレスをレコードに格納します。
	 *
	 * @param fhDirectStationAddr ファイルヘッダーレコード：直接ステーションアドレス
	 */
	public void setFhDirectStationAddr(String fhDirectStationAddr) {
		put("FH_DIRECT_STATION_ADDR", fhDirectStationAddr);
	}


	/**
	 * ファイルヘッダーレコード：提供企業コードを返します。
	 *
	 * @return ファイルヘッダーレコード：提供企業コード
	 */
	public String getFhTeikyoKigyoCd() {
		return (String) get("FH_TEIKYO_KIGYO_CD");
	}

	/**
	 * ファイルヘッダーレコード：提供企業コードをレコードに格納します。
	 *
	 * @param fhTeikyoKigyoCd ファイルヘッダーレコード：提供企業コード
	 */
	public void setFhTeikyoKigyoCd(String fhTeikyoKigyoCd) {
		put("FH_TEIKYO_KIGYO_CD", fhTeikyoKigyoCd);
	}


	/**
	 * ファイルヘッダーレコード：提供企業事業所コードを返します。
	 *
	 * @return ファイルヘッダーレコード：提供企業事業所コード
	 */
	public String getFhTeikyoJigyosyoCd() {
		return (String) get("FH_TEIKYO_JIGYOSYO_CD");
	}

	/**
	 * ファイルヘッダーレコード：提供企業事業所コードをレコードに格納します。
	 *
	 * @param fhTeikyoJigyosyoCd ファイルヘッダーレコード：提供企業事業所コード
	 */
	public void setFhTeikyoJigyosyoCd(String fhTeikyoJigyosyoCd) {
		put("FH_TEIKYO_JIGYOSYO_CD", fhTeikyoJigyosyoCd);
	}


	/**
	 * ファイルヘッダーレコード：提供企業名を返します。
	 *
	 * @return ファイルヘッダーレコード：提供企業名
	 */
	public String getFhTeikyouKigyoNm() {
		return (String) get("FH_TEIKYOU_KIGYO_NM");
	}

	/**
	 * ファイルヘッダーレコード：提供企業名をレコードに格納します。
	 *
	 * @param fhTeikyouKigyoNm ファイルヘッダーレコード：提供企業名
	 */
	public void setFhTeikyouKigyoNm(String fhTeikyouKigyoNm) {
		put("FH_TEIKYOU_KIGYO_NM", fhTeikyouKigyoNm);
	}


	/**
	 * ファイルヘッダーレコード：提供企業照会部署名を返します。
	 *
	 * @return ファイルヘッダーレコード：提供企業照会部署名
	 */
	public String getFhTeikyouBusyoNm() {
		return (String) get("FH_TEIKYOU_BUSYO_NM");
	}

	/**
	 * ファイルヘッダーレコード：提供企業照会部署名をレコードに格納します。
	 *
	 * @param fhTeikyouBusyoNm ファイルヘッダーレコード：提供企業照会部署名
	 */
	public void setFhTeikyouBusyoNm(String fhTeikyouBusyoNm) {
		put("FH_TEIKYOU_BUSYO_NM", fhTeikyouBusyoNm);
	}


	/**
	 * ファイルヘッダーレコード：送信データ件数を返します。
	 *
	 * @return ファイルヘッダーレコード：送信データ件数
	 */
	public String getFhDataCount() {
		return (String) get("FH_DATA_COUNT");
	}

	/**
	 * ファイルヘッダーレコード：送信データ件数をレコードに格納します。
	 *
	 * @param fhDataCount ファイルヘッダーレコード：送信データ件数
	 */
	public void setFhDataCount(String fhDataCount) {
		put("FH_DATA_COUNT", fhDataCount);
	}


	/**
	 * ファイルヘッダーレコード：レコードサイズを返します。
	 *
	 * @return ファイルヘッダーレコード：レコードサイズ
	 */
	public String getFhRecordSize() {
		return (String) get("FH_RECORD_SIZE");
	}

	/**
	 * ファイルヘッダーレコード：レコードサイズをレコードに格納します。
	 *
	 * @param fhRecordSize ファイルヘッダーレコード：レコードサイズ
	 */
	public void setFhRecordSize(String fhRecordSize) {
		put("FH_RECORD_SIZE", fhRecordSize);
	}


	/**
	 * ファイルヘッダーレコード：データ有無サインを返します。
	 *
	 * @return ファイルヘッダーレコード：データ有無サイン
	 */
	public String getFhDataUmu() {
		return (String) get("FH_DATA_UMU");
	}

	/**
	 * ファイルヘッダーレコード：データ有無サインをレコードに格納します。
	 *
	 * @param fhDataUmu ファイルヘッダーレコード：データ有無サイン
	 */
	public void setFhDataUmu(String fhDataUmu) {
		put("FH_DATA_UMU", fhDataUmu);
	}


	/**
	 * ファイルヘッダーレコード：フォーマットバージョンを返します。
	 *
	 * @return ファイルヘッダーレコード：フォーマットバージョン
	 */
	public String getFhFormatVersion() {
		return (String) get("FH_FORMAT_VERSION");
	}

	/**
	 * ファイルヘッダーレコード：フォーマットバージョンをレコードに格納します。
	 *
	 * @param fhFormatVersion ファイルヘッダーレコード：フォーマットバージョン
	 */
	public void setFhFormatVersion(String fhFormatVersion) {
		put("FH_FORMAT_VERSION", fhFormatVersion);
	}


	/**
	 * ファイルヘッダーレコード：余白を返します。
	 *
	 * @return ファイルヘッダーレコード：余白
	 */
	public String getFhYohaku() {
		return (String) get("FH_YOHAKU");
	}

	/**
	 * ファイルヘッダーレコード：余白をレコードに格納します。
	 *
	 * @param fhYohaku ファイルヘッダーレコード：余白
	 */
	public void setFhYohaku(String fhYohaku) {
		put("FH_YOHAKU", fhYohaku);
	}


	/**
	 * 伝票ヘッダーレコード：レコード区分を返します。
	 *
	 * @return 伝票ヘッダーレコード：レコード区分
	 */
	public String getDhRecordKbn() {
		return (String) get("DH_RECORD_KBN");
	}

	/**
	 * 伝票ヘッダーレコード：レコード区分をレコードに格納します。
	 *
	 * @param dhRecordKbn 伝票ヘッダーレコード：レコード区分
	 */
	public void setDhRecordKbn(String dhRecordKbn) {
		put("DH_RECORD_KBN", dhRecordKbn);
	}


	/**
	 * 伝票ヘッダーレコード：データシリアルNoを返します。
	 *
	 * @return 伝票ヘッダーレコード：データシリアルNo
	 */
	public String getDhDataSerialNo() {
		return (String) get("DH_DATA_SERIAL_NO");
	}

	/**
	 * 伝票ヘッダーレコード：データシリアルNoをレコードに格納します。
	 *
	 * @param dhDataSerialNo 伝票ヘッダーレコード：データシリアルNo
	 */
	public void setDhDataSerialNo(String dhDataSerialNo) {
		put("DH_DATA_SERIAL_NO", dhDataSerialNo);
	}


	/**
	 * 伝票ヘッダーレコード：データ区分を返します。
	 *
	 * @return 伝票ヘッダーレコード：データ区分
	 */
	public String getDhDataKbn() {
		return (String) get("DH_DATA_KBN");
	}

	/**
	 * 伝票ヘッダーレコード：データ区分をレコードに格納します。
	 *
	 * @param dhDataKbn 伝票ヘッダーレコード：データ区分
	 */
	public void setDhDataKbn(String dhDataKbn) {
		put("DH_DATA_KBN", dhDataKbn);
	}


	/**
	 * 伝票ヘッダーレコード：伝票番号を返します。
	 *
	 * @return 伝票ヘッダーレコード：伝票番号
	 */
	public String getDhDenpyoNo() {
		return (String) get("DH_DENPYO_NO");
	}

	/**
	 * 伝票ヘッダーレコード：伝票番号をレコードに格納します。
	 *
	 * @param dhDenpyoNo 伝票ヘッダーレコード：伝票番号
	 */
	public void setDhDenpyoNo(String dhDenpyoNo) {
		put("DH_DENPYO_NO", dhDenpyoNo);
	}


	/**
	 * 伝票ヘッダーレコード：発注日を返します。
	 *
	 * @return 伝票ヘッダーレコード：発注日
	 */
	public String getDhHachuDt() {
		return (String) get("DH_HACHU_DT");
	}

	/**
	 * 伝票ヘッダーレコード：発注日をレコードに格納します。
	 *
	 * @param dhHachuDt 伝票ヘッダーレコード：発注日
	 */
	public void setDhHachuDt(String dhHachuDt) {
		put("DH_HACHU_DT", dhHachuDt);
	}


	/**
	 * 伝票ヘッダーレコード：一次店コードを返します。
	 *
	 * @return 伝票ヘッダーレコード：一次店コード
	 */
	public String getDhIchijitenCd() {
		return (String) get("DH_ICHIJITEN_CD");
	}

	/**
	 * 伝票ヘッダーレコード：一次店コードをレコードに格納します。
	 *
	 * @param dhIchijitenCd 伝票ヘッダーレコード：一次店コード
	 */
	public void setDhIchijitenCd(String dhIchijitenCd) {
		put("DH_ICHIJITEN_CD", dhIchijitenCd);
	}


	/**
	 * 伝票ヘッダーレコード：二次店コードを返します。
	 *
	 * @return 伝票ヘッダーレコード：二次店コード
	 */
	public String getDhNijitenCd() {
		return (String) get("DH_NIJITEN_CD");
	}

	/**
	 * 伝票ヘッダーレコード：二次店コードをレコードに格納します。
	 *
	 * @param dhNijitenCd 伝票ヘッダーレコード：二次店コード
	 */
	public void setDhNijitenCd(String dhNijitenCd) {
		put("DH_NIJITEN_CD", dhNijitenCd);
	}


	/**
	 * 伝票ヘッダーレコード：三次店コードを返します。
	 *
	 * @return 伝票ヘッダーレコード：三次店コード
	 */
	public String getDhSanjitenCd() {
		return (String) get("DH_SANJITEN_CD");
	}

	/**
	 * 伝票ヘッダーレコード：三次店コードをレコードに格納します。
	 *
	 * @param dhSanjitenCd 伝票ヘッダーレコード：三次店コード
	 */
	public void setDhSanjitenCd(String dhSanjitenCd) {
		put("DH_SANJITEN_CD", dhSanjitenCd);
	}


	/**
	 * 伝票ヘッダーレコード：四次店コードを返します。
	 *
	 * @return 伝票ヘッダーレコード：四次店コード
	 */
	public String getDhYojitenCd() {
		return (String) get("DH_YOJITEN_CD");
	}

	/**
	 * 伝票ヘッダーレコード：四次店コードをレコードに格納します。
	 *
	 * @param dhYojitenCd 伝票ヘッダーレコード：四次店コード
	 */
	public void setDhYojitenCd(String dhYojitenCd) {
		put("DH_YOJITEN_CD", dhYojitenCd);
	}


	/**
	 * 伝票ヘッダーレコード：五次店コードを返します。
	 *
	 * @return 伝票ヘッダーレコード：五次店コード
	 */
	public String getDhGojitenCd() {
		return (String) get("DH_GOJITEN_CD");
	}

	/**
	 * 伝票ヘッダーレコード：五次店コードをレコードに格納します。
	 *
	 * @param dhGojitenCd 伝票ヘッダーレコード：五次店コード
	 */
	public void setDhGojitenCd(String dhGojitenCd) {
		put("DH_GOJITEN_CD", dhGojitenCd);
	}


	/**
	 * 伝票ヘッダーレコード：納入日又は取引日を返します。
	 *
	 * @return 伝票ヘッダーレコード：納入日又は取引日
	 */
	public String getDhNonyuDt() {
		return (String) get("DH_NONYU_DT");
	}

	/**
	 * 伝票ヘッダーレコード：納入日又は取引日をレコードに格納します。
	 *
	 * @param dhNonyuDt 伝票ヘッダーレコード：納入日又は取引日
	 */
	public void setDhNonyuDt(String dhNonyuDt) {
		put("DH_NONYU_DT", dhNonyuDt);
	}


	/**
	 * 伝票ヘッダーレコード：手形情報を返します。
	 *
	 * @return 伝票ヘッダーレコード：手形情報
	 */
	public String getDhTegata() {
		return (String) get("DH_TEGATA");
	}

	/**
	 * 伝票ヘッダーレコード：手形情報をレコードに格納します。
	 *
	 * @param dhTegata 伝票ヘッダーレコード：手形情報
	 */
	public void setDhTegata(String dhTegata) {
		put("DH_TEGATA", dhTegata);
	}


	/**
	 * 伝票ヘッダーレコード：蔵直区分を返します。
	 *
	 * @return 伝票ヘッダーレコード：蔵直区分
	 */
	public String getDhKuraKbn() {
		return (String) get("DH_KURA_KBN");
	}

	/**
	 * 伝票ヘッダーレコード：蔵直区分をレコードに格納します。
	 *
	 * @param dhKuraKbn 伝票ヘッダーレコード：蔵直区分
	 */
	public void setDhKuraKbn(String dhKuraKbn) {
		put("DH_KURA_KBN", dhKuraKbn);
	}


	/**
	 * 伝票ヘッダーレコード：定期発注取消サインを返します。
	 *
	 * @return 伝票ヘッダーレコード：定期発注取消サイン
	 */
	public String getDhTorikesiSign() {
		return (String) get("DH_TORIKESI_SIGN");
	}

	/**
	 * 伝票ヘッダーレコード：定期発注取消サインをレコードに格納します。
	 *
	 * @param dhTorikesiSign 伝票ヘッダーレコード：定期発注取消サイン
	 */
	public void setDhTorikesiSign(String dhTorikesiSign) {
		put("DH_TORIKESI_SIGN", dhTorikesiSign);
	}


	/**
	 * 伝票ヘッダーレコード：納入希望時間を返します。
	 *
	 * @return 伝票ヘッダーレコード：納入希望時間
	 */
	public String getDhNonyuTm() {
		return (String) get("DH_NONYU_TM");
	}

	/**
	 * 伝票ヘッダーレコード：納入希望時間をレコードに格納します。
	 *
	 * @param dhNonyuTm 伝票ヘッダーレコード：納入希望時間
	 */
	public void setDhNonyuTm(String dhNonyuTm) {
		put("DH_NONYU_TM", dhNonyuTm);
	}


	/**
	 * 伝票ヘッダーレコード：備考を返します。
	 *
	 * @return 伝票ヘッダーレコード：備考
	 */
	public String getDhBikou() {
		return (String) get("DH_BIKOU");
	}

	/**
	 * 伝票ヘッダーレコード：備考をレコードに格納します。
	 *
	 * @param dhBikou 伝票ヘッダーレコード：備考
	 */
	public void setDhBikou(String dhBikou) {
		put("DH_BIKOU", dhBikou);
	}


	/**
	 * 伝票ヘッダーオプションレコード：レコード区分1を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：レコード区分1
	 */
	public String getHoRecordKbn1() {
		return (String) get("HO_RECORD_KBN1");
	}

	/**
	 * 伝票ヘッダーオプションレコード：レコード区分1をレコードに格納します。
	 *
	 * @param hoRecordKbn1 伝票ヘッダーオプションレコード：レコード区分1
	 */
	public void setHoRecordKbn1(String hoRecordKbn1) {
		put("HO_RECORD_KBN1", hoRecordKbn1);
	}


	/**
	 * 伝票ヘッダーオプションレコード：データシリアルNo1を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：データシリアルNo1
	 */
	public String getHoDataSerialNo1() {
		return (String) get("HO_DATA_SERIAL_NO1");
	}

	/**
	 * 伝票ヘッダーオプションレコード：データシリアルNo1をレコードに格納します。
	 *
	 * @param hoDataSerialNo1 伝票ヘッダーオプションレコード：データシリアルNo1
	 */
	public void setHoDataSerialNo1(String hoDataSerialNo1) {
		put("HO_DATA_SERIAL_NO1", hoDataSerialNo1);
	}


	/**
	 * 伝票ヘッダーオプションレコード：伝票ヘッダー参照№1を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：伝票ヘッダー参照№1
	 */
	public String getHoViewNo1() {
		return (String) get("HO_VIEW_NO1");
	}

	/**
	 * 伝票ヘッダーオプションレコード：伝票ヘッダー参照№1をレコードに格納します。
	 *
	 * @param hoViewNo1 伝票ヘッダーオプションレコード：伝票ヘッダー参照№1
	 */
	public void setHoViewNo1(String hoViewNo1) {
		put("HO_VIEW_NO1", hoViewNo1);
	}


	/**
	 * 伝票ヘッダーオプションレコード：社名、店名、取引先名1を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：社名、店名、取引先名1
	 */
	public String getHoKaisyaNm1() {
		return (String) get("HO_KAISYA_NM1");
	}

	/**
	 * 伝票ヘッダーオプションレコード：社名、店名、取引先名1をレコードに格納します。
	 *
	 * @param hoKaisyaNm1 伝票ヘッダーオプションレコード：社名、店名、取引先名1
	 */
	public void setHoKaisyaNm1(String hoKaisyaNm1) {
		put("HO_KAISYA_NM1", hoKaisyaNm1);
	}


	/**
	 * 伝票ヘッダーオプションレコード：住所1を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：住所1
	 */
	public String getHoKaisyaAddr1() {
		return (String) get("HO_KAISYA_ADDR1");
	}

	/**
	 * 伝票ヘッダーオプションレコード：住所1をレコードに格納します。
	 *
	 * @param hoKaisyaAddr1 伝票ヘッダーオプションレコード：住所1
	 */
	public void setHoKaisyaAddr1(String hoKaisyaAddr1) {
		put("HO_KAISYA_ADDR1", hoKaisyaAddr1);
	}


	/**
	 * 伝票ヘッダーオプションレコード：電話番号1を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：電話番号1
	 */
	public String getHoKaisyaTel1() {
		return (String) get("HO_KAISYA_TEL1");
	}

	/**
	 * 伝票ヘッダーオプションレコード：電話番号1をレコードに格納します。
	 *
	 * @param hoKaisyaTel1 伝票ヘッダーオプションレコード：電話番号1
	 */
	public void setHoKaisyaTel1(String hoKaisyaTel1) {
		put("HO_KAISYA_TEL1", hoKaisyaTel1);
	}


	/**
	 * 伝票ヘッダーオプションレコード：余白1を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：余白1
	 */
	public String getHoYohaku1() {
		return (String) get("HO_YOHAKU1");
	}

	/**
	 * 伝票ヘッダーオプションレコード：余白1をレコードに格納します。
	 *
	 * @param hoYohaku1 伝票ヘッダーオプションレコード：余白1
	 */
	public void setHoYohaku1(String hoYohaku1) {
		put("HO_YOHAKU1", hoYohaku1);
	}


	/**
	 * 伝票ヘッダーオプションレコード：取引先対応コード1を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：取引先対応コード1
	 */
	public String getHoTorihikiCd1() {
		return (String) get("HO_TORIHIKI_CD1");
	}

	/**
	 * 伝票ヘッダーオプションレコード：取引先対応コード1をレコードに格納します。
	 *
	 * @param hoTorihikiCd1 伝票ヘッダーオプションレコード：取引先対応コード1
	 */
	public void setHoTorihikiCd1(String hoTorihikiCd1) {
		put("HO_TORIHIKI_CD1", hoTorihikiCd1);
	}


	/**
	 * 伝票ヘッダーオプションレコード：日本語区分1を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：日本語区分1
	 */
	public String getHoNihongoKbn1() {
		return (String) get("HO_NIHONGO_KBN1");
	}

	/**
	 * 伝票ヘッダーオプションレコード：日本語区分1をレコードに格納します。
	 *
	 * @param hoNihongoKbn1 伝票ヘッダーオプションレコード：日本語区分1
	 */
	public void setHoNihongoKbn1(String hoNihongoKbn1) {
		put("HO_NIHONGO_KBN1", hoNihongoKbn1);
	}


	/**
	 * 伝票ヘッダーオプションレコード：レコード区分2を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：レコード区分2
	 */
	public String getHoRecordKbn2() {
		return (String) get("HO_RECORD_KBN2");
	}

	/**
	 * 伝票ヘッダーオプションレコード：レコード区分2をレコードに格納します。
	 *
	 * @param hoRecordKbn2 伝票ヘッダーオプションレコード：レコード区分2
	 */
	public void setHoRecordKbn2(String hoRecordKbn2) {
		put("HO_RECORD_KBN2", hoRecordKbn2);
	}


	/**
	 * 伝票ヘッダーオプションレコード：データシリアルNo2を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：データシリアルNo2
	 */
	public String getHoDataSerialNo2() {
		return (String) get("HO_DATA_SERIAL_NO2");
	}

	/**
	 * 伝票ヘッダーオプションレコード：データシリアルNo2をレコードに格納します。
	 *
	 * @param hoDataSerialNo2 伝票ヘッダーオプションレコード：データシリアルNo2
	 */
	public void setHoDataSerialNo2(String hoDataSerialNo2) {
		put("HO_DATA_SERIAL_NO2", hoDataSerialNo2);
	}


	/**
	 * 伝票ヘッダーオプションレコード：伝票ヘッダー参照№2を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：伝票ヘッダー参照№2
	 */
	public String getHoViewNo2() {
		return (String) get("HO_VIEW_NO2");
	}

	/**
	 * 伝票ヘッダーオプションレコード：伝票ヘッダー参照№2をレコードに格納します。
	 *
	 * @param hoViewNo2 伝票ヘッダーオプションレコード：伝票ヘッダー参照№2
	 */
	public void setHoViewNo2(String hoViewNo2) {
		put("HO_VIEW_NO2", hoViewNo2);
	}


	/**
	 * 伝票ヘッダーオプションレコード：社名、店名、取引先名2を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：社名、店名、取引先名2
	 */
	public String getHoKaisyaNm2() {
		return (String) get("HO_KAISYA_NM2");
	}

	/**
	 * 伝票ヘッダーオプションレコード：社名、店名、取引先名2をレコードに格納します。
	 *
	 * @param hoKaisyaNm2 伝票ヘッダーオプションレコード：社名、店名、取引先名2
	 */
	public void setHoKaisyaNm2(String hoKaisyaNm2) {
		put("HO_KAISYA_NM2", hoKaisyaNm2);
	}


	/**
	 * 伝票ヘッダーオプションレコード：住所2を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：住所2
	 */
	public String getHoKaisyaAddr2() {
		return (String) get("HO_KAISYA_ADDR2");
	}

	/**
	 * 伝票ヘッダーオプションレコード：住所2をレコードに格納します。
	 *
	 * @param hoKaisyaAddr2 伝票ヘッダーオプションレコード：住所2
	 */
	public void setHoKaisyaAddr2(String hoKaisyaAddr2) {
		put("HO_KAISYA_ADDR2", hoKaisyaAddr2);
	}


	/**
	 * 伝票ヘッダーオプションレコード：電話番号2を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：電話番号2
	 */
	public String getHoKaisyaTel2() {
		return (String) get("HO_KAISYA_TEL2");
	}

	/**
	 * 伝票ヘッダーオプションレコード：電話番号2をレコードに格納します。
	 *
	 * @param hoKaisyaTel2 伝票ヘッダーオプションレコード：電話番号2
	 */
	public void setHoKaisyaTel2(String hoKaisyaTel2) {
		put("HO_KAISYA_TEL2", hoKaisyaTel2);
	}


	/**
	 * 伝票ヘッダーオプションレコード：余白2を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：余白2
	 */
	public String getHoYohaku2() {
		return (String) get("HO_YOHAKU2");
	}

	/**
	 * 伝票ヘッダーオプションレコード：余白2をレコードに格納します。
	 *
	 * @param hoYohaku2 伝票ヘッダーオプションレコード：余白2
	 */
	public void setHoYohaku2(String hoYohaku2) {
		put("HO_YOHAKU2", hoYohaku2);
	}


	/**
	 * 伝票ヘッダーオプションレコード：取引先対応コード2を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：取引先対応コード2
	 */
	public String getHoTorihikiCd2() {
		return (String) get("HO_TORIHIKI_CD2");
	}

	/**
	 * 伝票ヘッダーオプションレコード：取引先対応コード2をレコードに格納します。
	 *
	 * @param hoTorihikiCd2 伝票ヘッダーオプションレコード：取引先対応コード2
	 */
	public void setHoTorihikiCd2(String hoTorihikiCd2) {
		put("HO_TORIHIKI_CD2", hoTorihikiCd2);
	}


	/**
	 * 伝票ヘッダーオプションレコード：日本語区分2を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：日本語区分2
	 */
	public String getHoNihongoKbn2() {
		return (String) get("HO_NIHONGO_KBN2");
	}

	/**
	 * 伝票ヘッダーオプションレコード：日本語区分2をレコードに格納します。
	 *
	 * @param hoNihongoKbn2 伝票ヘッダーオプションレコード：日本語区分2
	 */
	public void setHoNihongoKbn2(String hoNihongoKbn2) {
		put("HO_NIHONGO_KBN2", hoNihongoKbn2);
	}


	/**
	 * 伝票ヘッダーオプションレコード：レコード区分3を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：レコード区分3
	 */
	public String getHoRecordKbn3() {
		return (String) get("HO_RECORD_KBN3");
	}

	/**
	 * 伝票ヘッダーオプションレコード：レコード区分3をレコードに格納します。
	 *
	 * @param hoRecordKbn3 伝票ヘッダーオプションレコード：レコード区分3
	 */
	public void setHoRecordKbn3(String hoRecordKbn3) {
		put("HO_RECORD_KBN3", hoRecordKbn3);
	}


	/**
	 * 伝票ヘッダーオプションレコード：データシリアルNo3を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：データシリアルNo3
	 */
	public String getHoDataSerialNo3() {
		return (String) get("HO_DATA_SERIAL_NO3");
	}

	/**
	 * 伝票ヘッダーオプションレコード：データシリアルNo3をレコードに格納します。
	 *
	 * @param hoDataSerialNo3 伝票ヘッダーオプションレコード：データシリアルNo3
	 */
	public void setHoDataSerialNo3(String hoDataSerialNo3) {
		put("HO_DATA_SERIAL_NO3", hoDataSerialNo3);
	}


	/**
	 * 伝票ヘッダーオプションレコード：伝票ヘッダー参照№3を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：伝票ヘッダー参照№3
	 */
	public String getHoViewNo3() {
		return (String) get("HO_VIEW_NO3");
	}

	/**
	 * 伝票ヘッダーオプションレコード：伝票ヘッダー参照№3をレコードに格納します。
	 *
	 * @param hoViewNo3 伝票ヘッダーオプションレコード：伝票ヘッダー参照№3
	 */
	public void setHoViewNo3(String hoViewNo3) {
		put("HO_VIEW_NO3", hoViewNo3);
	}


	/**
	 * 伝票ヘッダーオプションレコード：社名、店名、取引先名3を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：社名、店名、取引先名3
	 */
	public String getHoKaisyaNm3() {
		return (String) get("HO_KAISYA_NM3");
	}

	/**
	 * 伝票ヘッダーオプションレコード：社名、店名、取引先名3をレコードに格納します。
	 *
	 * @param hoKaisyaNm3 伝票ヘッダーオプションレコード：社名、店名、取引先名3
	 */
	public void setHoKaisyaNm3(String hoKaisyaNm3) {
		put("HO_KAISYA_NM3", hoKaisyaNm3);
	}


	/**
	 * 伝票ヘッダーオプションレコード：住所3を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：住所3
	 */
	public String getHoKaisyaAddr3() {
		return (String) get("HO_KAISYA_ADDR3");
	}

	/**
	 * 伝票ヘッダーオプションレコード：住所3をレコードに格納します。
	 *
	 * @param hoKaisyaAddr3 伝票ヘッダーオプションレコード：住所3
	 */
	public void setHoKaisyaAddr3(String hoKaisyaAddr3) {
		put("HO_KAISYA_ADDR3", hoKaisyaAddr3);
	}


	/**
	 * 伝票ヘッダーオプションレコード：電話番号3を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：電話番号3
	 */
	public String getHoKaisyaTel3() {
		return (String) get("HO_KAISYA_TEL3");
	}

	/**
	 * 伝票ヘッダーオプションレコード：電話番号3をレコードに格納します。
	 *
	 * @param hoKaisyaTel3 伝票ヘッダーオプションレコード：電話番号3
	 */
	public void setHoKaisyaTel3(String hoKaisyaTel3) {
		put("HO_KAISYA_TEL3", hoKaisyaTel3);
	}


	/**
	 * 伝票ヘッダーオプションレコード：余白3を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：余白3
	 */
	public String getHoYohaku3() {
		return (String) get("HO_YOHAKU3");
	}

	/**
	 * 伝票ヘッダーオプションレコード：余白3をレコードに格納します。
	 *
	 * @param hoYohaku3 伝票ヘッダーオプションレコード：余白3
	 */
	public void setHoYohaku3(String hoYohaku3) {
		put("HO_YOHAKU3", hoYohaku3);
	}


	/**
	 * 伝票ヘッダーオプションレコード：取引先対応コード3を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：取引先対応コード3
	 */
	public String getHoTorihikiCd3() {
		return (String) get("HO_TORIHIKI_CD3");
	}

	/**
	 * 伝票ヘッダーオプションレコード：取引先対応コード3をレコードに格納します。
	 *
	 * @param hoTorihikiCd3 伝票ヘッダーオプションレコード：取引先対応コード3
	 */
	public void setHoTorihikiCd3(String hoTorihikiCd3) {
		put("HO_TORIHIKI_CD3", hoTorihikiCd3);
	}


	/**
	 * 伝票ヘッダーオプションレコード：日本語区分3を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：日本語区分3
	 */
	public String getHoNihongoKbn3() {
		return (String) get("HO_NIHONGO_KBN3");
	}

	/**
	 * 伝票ヘッダーオプションレコード：日本語区分3をレコードに格納します。
	 *
	 * @param hoNihongoKbn3 伝票ヘッダーオプションレコード：日本語区分3
	 */
	public void setHoNihongoKbn3(String hoNihongoKbn3) {
		put("HO_NIHONGO_KBN3", hoNihongoKbn3);
	}


	/**
	 * 伝票ヘッダーオプションレコード：レコード区分4を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：レコード区分4
	 */
	public String getHoRecordKbn4() {
		return (String) get("HO_RECORD_KBN4");
	}

	/**
	 * 伝票ヘッダーオプションレコード：レコード区分4をレコードに格納します。
	 *
	 * @param hoRecordKbn4 伝票ヘッダーオプションレコード：レコード区分4
	 */
	public void setHoRecordKbn4(String hoRecordKbn4) {
		put("HO_RECORD_KBN4", hoRecordKbn4);
	}


	/**
	 * 伝票ヘッダーオプションレコード：データシリアルNo4を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：データシリアルNo4
	 */
	public String getHoDataSerialNo4() {
		return (String) get("HO_DATA_SERIAL_NO4");
	}

	/**
	 * 伝票ヘッダーオプションレコード：データシリアルNo4をレコードに格納します。
	 *
	 * @param hoDataSerialNo4 伝票ヘッダーオプションレコード：データシリアルNo4
	 */
	public void setHoDataSerialNo4(String hoDataSerialNo4) {
		put("HO_DATA_SERIAL_NO4", hoDataSerialNo4);
	}


	/**
	 * 伝票ヘッダーオプションレコード：伝票ヘッダー参照№4を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：伝票ヘッダー参照№4
	 */
	public String getHoViewNo4() {
		return (String) get("HO_VIEW_NO4");
	}

	/**
	 * 伝票ヘッダーオプションレコード：伝票ヘッダー参照№4をレコードに格納します。
	 *
	 * @param hoViewNo4 伝票ヘッダーオプションレコード：伝票ヘッダー参照№4
	 */
	public void setHoViewNo4(String hoViewNo4) {
		put("HO_VIEW_NO4", hoViewNo4);
	}


	/**
	 * 伝票ヘッダーオプションレコード：社名、店名、取引先名4を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：社名、店名、取引先名4
	 */
	public String getHoKaisyaNm4() {
		return (String) get("HO_KAISYA_NM4");
	}

	/**
	 * 伝票ヘッダーオプションレコード：社名、店名、取引先名4をレコードに格納します。
	 *
	 * @param hoKaisyaNm4 伝票ヘッダーオプションレコード：社名、店名、取引先名4
	 */
	public void setHoKaisyaNm4(String hoKaisyaNm4) {
		put("HO_KAISYA_NM4", hoKaisyaNm4);
	}


	/**
	 * 伝票ヘッダーオプションレコード：住所4を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：住所4
	 */
	public String getHoKaisyaAddr4() {
		return (String) get("HO_KAISYA_ADDR4");
	}

	/**
	 * 伝票ヘッダーオプションレコード：住所4をレコードに格納します。
	 *
	 * @param hoKaisyaAddr4 伝票ヘッダーオプションレコード：住所4
	 */
	public void setHoKaisyaAddr4(String hoKaisyaAddr4) {
		put("HO_KAISYA_ADDR4", hoKaisyaAddr4);
	}


	/**
	 * 伝票ヘッダーオプションレコード：電話番号4を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：電話番号4
	 */
	public String getHoKaisyaTel4() {
		return (String) get("HO_KAISYA_TEL4");
	}

	/**
	 * 伝票ヘッダーオプションレコード：電話番号4をレコードに格納します。
	 *
	 * @param hoKaisyaTel4 伝票ヘッダーオプションレコード：電話番号4
	 */
	public void setHoKaisyaTel4(String hoKaisyaTel4) {
		put("HO_KAISYA_TEL4", hoKaisyaTel4);
	}


	/**
	 * 伝票ヘッダーオプションレコード：余白4を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：余白4
	 */
	public String getHoYohaku4() {
		return (String) get("HO_YOHAKU4");
	}

	/**
	 * 伝票ヘッダーオプションレコード：余白4をレコードに格納します。
	 *
	 * @param hoYohaku4 伝票ヘッダーオプションレコード：余白4
	 */
	public void setHoYohaku4(String hoYohaku4) {
		put("HO_YOHAKU4", hoYohaku4);
	}


	/**
	 * 伝票ヘッダーオプションレコード：取引先対応コード4を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：取引先対応コード4
	 */
	public String getHoTorihikiCd4() {
		return (String) get("HO_TORIHIKI_CD4");
	}

	/**
	 * 伝票ヘッダーオプションレコード：取引先対応コード4をレコードに格納します。
	 *
	 * @param hoTorihikiCd4 伝票ヘッダーオプションレコード：取引先対応コード4
	 */
	public void setHoTorihikiCd4(String hoTorihikiCd4) {
		put("HO_TORIHIKI_CD4", hoTorihikiCd4);
	}


	/**
	 * 伝票ヘッダーオプションレコード：日本語区分4を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：日本語区分4
	 */
	public String getHoNihongoKbn4() {
		return (String) get("HO_NIHONGO_KBN4");
	}

	/**
	 * 伝票ヘッダーオプションレコード：日本語区分4をレコードに格納します。
	 *
	 * @param hoNihongoKbn4 伝票ヘッダーオプションレコード：日本語区分4
	 */
	public void setHoNihongoKbn4(String hoNihongoKbn4) {
		put("HO_NIHONGO_KBN4", hoNihongoKbn4);
	}


	/**
	 * 伝票ヘッダーオプションレコード：レコード区分5を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：レコード区分5
	 */
	public String getHoRecordKbn5() {
		return (String) get("HO_RECORD_KBN5");
	}

	/**
	 * 伝票ヘッダーオプションレコード：レコード区分5をレコードに格納します。
	 *
	 * @param hoRecordKbn5 伝票ヘッダーオプションレコード：レコード区分5
	 */
	public void setHoRecordKbn5(String hoRecordKbn5) {
		put("HO_RECORD_KBN5", hoRecordKbn5);
	}


	/**
	 * 伝票ヘッダーオプションレコード：データシリアルNo5を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：データシリアルNo5
	 */
	public String getHoDataSerialNo5() {
		return (String) get("HO_DATA_SERIAL_NO5");
	}

	/**
	 * 伝票ヘッダーオプションレコード：データシリアルNo5をレコードに格納します。
	 *
	 * @param hoDataSerialNo5 伝票ヘッダーオプションレコード：データシリアルNo5
	 */
	public void setHoDataSerialNo5(String hoDataSerialNo5) {
		put("HO_DATA_SERIAL_NO5", hoDataSerialNo5);
	}


	/**
	 * 伝票ヘッダーオプションレコード：伝票ヘッダー参照№5を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：伝票ヘッダー参照№5
	 */
	public String getHoViewNo5() {
		return (String) get("HO_VIEW_NO5");
	}

	/**
	 * 伝票ヘッダーオプションレコード：伝票ヘッダー参照№5をレコードに格納します。
	 *
	 * @param hoViewNo5 伝票ヘッダーオプションレコード：伝票ヘッダー参照№5
	 */
	public void setHoViewNo5(String hoViewNo5) {
		put("HO_VIEW_NO5", hoViewNo5);
	}


	/**
	 * 伝票ヘッダーオプションレコード：社名、店名、取引先名5を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：社名、店名、取引先名5
	 */
	public String getHoKaisyaNm5() {
		return (String) get("HO_KAISYA_NM5");
	}

	/**
	 * 伝票ヘッダーオプションレコード：社名、店名、取引先名5をレコードに格納します。
	 *
	 * @param hoKaisyaNm5 伝票ヘッダーオプションレコード：社名、店名、取引先名5
	 */
	public void setHoKaisyaNm5(String hoKaisyaNm5) {
		put("HO_KAISYA_NM5", hoKaisyaNm5);
	}


	/**
	 * 伝票ヘッダーオプションレコード：住所5を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：住所5
	 */
	public String getHoKaisyaAddr5() {
		return (String) get("HO_KAISYA_ADDR5");
	}

	/**
	 * 伝票ヘッダーオプションレコード：住所5をレコードに格納します。
	 *
	 * @param hoKaisyaAddr5 伝票ヘッダーオプションレコード：住所5
	 */
	public void setHoKaisyaAddr5(String hoKaisyaAddr5) {
		put("HO_KAISYA_ADDR5", hoKaisyaAddr5);
	}


	/**
	 * 伝票ヘッダーオプションレコード：電話番号5を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：電話番号5
	 */
	public String getHoKaisyaTel5() {
		return (String) get("HO_KAISYA_TEL5");
	}

	/**
	 * 伝票ヘッダーオプションレコード：電話番号5をレコードに格納します。
	 *
	 * @param hoKaisyaTel5 伝票ヘッダーオプションレコード：電話番号5
	 */
	public void setHoKaisyaTel5(String hoKaisyaTel5) {
		put("HO_KAISYA_TEL5", hoKaisyaTel5);
	}


	/**
	 * 伝票ヘッダーオプションレコード：余白5を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：余白5
	 */
	public String getHoYohaku5() {
		return (String) get("HO_YOHAKU5");
	}

	/**
	 * 伝票ヘッダーオプションレコード：余白5をレコードに格納します。
	 *
	 * @param hoYohaku5 伝票ヘッダーオプションレコード：余白5
	 */
	public void setHoYohaku5(String hoYohaku5) {
		put("HO_YOHAKU5", hoYohaku5);
	}


	/**
	 * 伝票ヘッダーオプションレコード：取引先対応コード5を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：取引先対応コード5
	 */
	public String getHoTorihikiCd5() {
		return (String) get("HO_TORIHIKI_CD5");
	}

	/**
	 * 伝票ヘッダーオプションレコード：取引先対応コード5をレコードに格納します。
	 *
	 * @param hoTorihikiCd5 伝票ヘッダーオプションレコード：取引先対応コード5
	 */
	public void setHoTorihikiCd5(String hoTorihikiCd5) {
		put("HO_TORIHIKI_CD5", hoTorihikiCd5);
	}


	/**
	 * 伝票ヘッダーオプションレコード：日本語区分5を返します。
	 *
	 * @return 伝票ヘッダーオプションレコード：日本語区分5
	 */
	public String getHoNihongoKbn5() {
		return (String) get("HO_NIHONGO_KBN5");
	}

	/**
	 * 伝票ヘッダーオプションレコード：日本語区分5をレコードに格納します。
	 *
	 * @param hoNihongoKbn5 伝票ヘッダーオプションレコード：日本語区分5
	 */
	public void setHoNihongoKbn5(String hoNihongoKbn5) {
		put("HO_NIHONGO_KBN5", hoNihongoKbn5);
	}


	/**
	 * エンドレコード：レコード区分を返します。
	 *
	 * @return エンドレコード：レコード区分
	 */
	public String getEdRecordKbn() {
		return (String) get("ED_RECORD_KBN");
	}

	/**
	 * エンドレコード：レコード区分をレコードに格納します。
	 *
	 * @param edRecordKbn エンドレコード：レコード区分
	 */
	public void setEdRecordKbn(String edRecordKbn) {
		put("ED_RECORD_KBN", edRecordKbn);
	}


	/**
	 * エンドレコード：データシリアルNoを返します。
	 *
	 * @return エンドレコード：データシリアルNo
	 */
	public String getEdDataSerialNo() {
		return (String) get("ED_DATA_SERIAL_NO");
	}

	/**
	 * エンドレコード：データシリアルNoをレコードに格納します。
	 *
	 * @param edDataSerialNo エンドレコード：データシリアルNo
	 */
	public void setEdDataSerialNo(String edDataSerialNo) {
		put("ED_DATA_SERIAL_NO", edDataSerialNo);
	}


	/**
	 * エンドレコード：レコード件数を返します。
	 *
	 * @return エンドレコード：レコード件数
	 */
	public String getEdLineCount() {
		return (String) get("ED_LINE_COUNT");
	}

	/**
	 * エンドレコード：レコード件数をレコードに格納します。
	 *
	 * @param edLineCount エンドレコード：レコード件数
	 */
	public void setEdLineCount(String edLineCount) {
		put("ED_LINE_COUNT", edLineCount);
	}


	/**
	 * エンドレコード：余白を返します。
	 *
	 * @return エンドレコード：余白
	 */
	public String getEdYohaku() {
		return (String) get("ED_YOHAKU");
	}

	/**
	 * エンドレコード：余白をレコードに格納します。
	 *
	 * @param edYohaku エンドレコード：余白
	 */
	public void setEdYohaku(String edYohaku) {
		put("ED_YOHAKU", edYohaku);
	}


	/**
	 * 【変換後】kz出荷予定日を返します。
	 *
	 * @return 【変換後】kz出荷予定日
	 */
	public String getSyukaYoteiDt() {
		return (String) get("SYUKA_YOTEI_DT");
	}

	/**
	 * 【変換後】kz出荷予定日をレコードに格納します。
	 *
	 * @param syukaYoteiDt 【変換後】kz出荷予定日
	 */
	public void setSyukaYoteiDt(String syukaYoteiDt) {
		put("SYUKA_YOTEI_DT", syukaYoteiDt);
	}


	/**
	 * 【変換後】ﾐﾅｼ日付を返します。
	 *
	 * @return 【変換後】ﾐﾅｼ日付
	 */
	public String getMinasiDt() {
		return (String) get("MINASI_DT");
	}

	/**
	 * 【変換後】ﾐﾅｼ日付をレコードに格納します。
	 *
	 * @param minasiDt 【変換後】ﾐﾅｼ日付
	 */
	public void setMinasiDt(String minasiDt) {
		put("MINASI_DT", minasiDt);
	}


	/**
	 * 【変換後】kz縦線CDを返します。
	 *
	 * @return 【変換後】kz縦線CD
	 */
	public String getTatesnCd() {
		return (String) get("TATESN_CD");
	}

	/**
	 * 【変換後】kz縦線CDをレコードに格納します。
	 *
	 * @param tatesnCd 【変換後】kz縦線CD
	 */
	public void setTatesnCd(String tatesnCd) {
		put("TATESN_CD", tatesnCd);
	}


	/**
	 * 【変換後】KZ最終送荷先卸CDを返します。
	 *
	 * @return 【変換後】KZ最終送荷先卸CD
	 */
	public String getOrositenCdLast() {
		return (String) get("OROSITEN_CD_LAST");
	}

	/**
	 * 【変換後】KZ最終送荷先卸CDをレコードに格納します。
	 *
	 * @param orositenCdLast 【変換後】KZ最終送荷先卸CD
	 */
	public void setOrositenCdLast(String orositenCdLast) {
		put("OROSITEN_CD_LAST", orositenCdLast);
	}


	/**
	 * 【変換後】kz酒販店CDを返します。
	 *
	 * @return 【変換後】kz酒販店CD
	 */
	public String getSyuhantenCd() {
		return (String) get("SYUHANTEN_CD");
	}

	/**
	 * 【変換後】kz酒販店CDをレコードに格納します。
	 *
	 * @param syuhantenCd 【変換後】kz酒販店CD
	 */
	public void setSyuhantenCd(String syuhantenCd) {
		put("SYUHANTEN_CD", syuhantenCd);
	}


	/**
	 * 【変換後】KZ受注NOを返します。
	 *
	 * @return 【変換後】KZ受注NO
	 */
	public String getJyucyuNo() {
		return (String) get("JYUCYU_NO");
	}

	/**
	 * 【変換後】KZ受注NOをレコードに格納します。
	 *
	 * @param jyucyuNo 【変換後】KZ受注NO
	 */
	public void setJyucyuNo(String jyucyuNo) {
		put("JYUCYU_NO", jyucyuNo);
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
	 * 重複区分を返します。
	 *
	 * @return 重複区分
	 */
	public String getCyofukuKbn() {
		return (String) get("CYOFUKU_KBN");
	}

	/**
	 * 重複区分をレコードに格納します。
	 *
	 * @param cyofukuKbn 重複区分
	 */
	public void setCyofukuKbn(String cyofukuKbn) {
		put("CYOFUKU_KBN", cyofukuKbn);
	}

	/**
	 * データ有無区分を返します。
	 *
	 * @return データ有無区分
	 */
	public String getDataUmuKbn() {
		return (String) get("DATA_UMU_KBN");
	}

	/**
	 * データ有無区分をレコードに格納します。
	 *
	 * @param dataUmuKbn データ有無区分
	 */
	public void setDataUmuKbn(String dataUmuKbn) {
		put("DATA_UMU_KBN", dataUmuKbn);
	}

	/**
	 * EDI受発注商品摘要欄確認区分を返します。
	 *
	 * @return EDI受発注商品摘要欄確認区分
	 */
	public String getEdiJsyohinTekiyochekKbn() {
		return (String) get("EDI_JSYOHIN_TEKIYOCHEK_KBN");
	}

	/**
	 * EDI受発注商品摘要欄確認区分をレコードに格納します。
	 *
	 * @param ediJsyohinTekiyochekKbn EDI受発注商品摘要欄確認区分
	 */
	public void setEdiJsyohinTekiyochekKbn(String ediJsyohinTekiyochekKbn) {
		put("EDI_JSYOHIN_TEKIYOCHEK_KBN", ediJsyohinTekiyochekKbn);
	}

	// ====変更前表示用====
	/**
	 * kz出荷予定日を返します。
	 *
	 * @return kz出荷予定日
	 */
	public String getSyukaYoteiDtView() {
		return (String) get("SYUKA_YOTEI_DT_VIEW");
	}

	/**
	 * kz出荷予定日をレコードに格納します。
	 *
	 * @param syukaYoteiDtView kz出荷予定日
	 */
	public void setSyukaYoteiDtView(String syukaYoteiDtView) {
		put("SYUKA_YOTEI_DT_VIEW", syukaYoteiDtView);
	}


	/**
	 * Kzミナシ日を返します。
	 *
	 * @return Kzミナシ日
	 */
	public String getMinasiDtView() {
		return (String) get("MINASI_DT_VIEW");
	}

	/**
	 * Kzミナシ日をレコードに格納します。
	 *
	 * @param minasiDtView Kzミナシ日
	 */
	public void setMinasiDtView(String minasiDtView) {
		put("MINASI_DT_VIEW", minasiDtView);
	}


	/**
	 * Kz縦線CDを返します。
	 *
	 * @return Kz縦線CD
	 */
	public String getTatesnCdView() {
		return (String) get("TATESN_CD_VIEW");
	}

	/**
	 * Kz縦線CDをレコードに格納します。
	 *
	 * @param tatesnCdView Kz縦線CD
	 */
	public void setTatesnCdView(String tatesnCdView) {
		put("TATESN_CD_VIEW", tatesnCdView);
	}

	/**
	 * 処理区分を返します。
	 *
	 * @return 処理区分
	 */
	public String getSyoriKbnView() {
		return (String) get("SYORI_KBN_VIEW");
	}

	/**
	 * 処理区分をレコードに格納します。
	 *
	 * @param syoriKbnViewView 処理区分
	 */
	public void setSyoriKbnView(String syoriKbnView) {
		put("SYORI_KBN_VIEW", syoriKbnView);
	}


	// ====エラー用====
	/**
	 * kz出荷予定日のエラー、警告表示フラグを返します。
	 *
	 * @return kz出荷予定日のエラー、警告表示フラグ
	 */
	public String getSyukaYoteiDtClass() {
		return (String) get("SYUKA_YOTEI_DT_CLASS");
	}

	/**
	 * kz出荷予定日のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaYoteiDtClass kz出荷予定日のエラー、警告表示フラグ
	 */
	public void setSyukaYoteiDtClass(String syukaYoteiDtClass) {
		put("SYUKA_YOTEI_DT_CLASS", syukaYoteiDtClass);
	}


	/**
	 * Kzミナシ日のエラー、警告表示フラグを返します。
	 *
	 * @return Kzミナシ日のエラー、警告表示フラグ
	 */
	public String getMinasiDtClass() {
		return (String) get("MINASI_DT_CLASS");
	}

	/**
	 * Kzミナシ日のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param minasiDtClass Kzミナシ日のエラー、警告表示フラグ
	 */
	public void setMinasiDtClass(String minasiDtClass) {
		put("MINASI_DT_CLASS", minasiDtClass);
	}


	/**
	 * Kz縦線CDのエラー、警告表示フラグを返します。
	 *
	 * @return Kz縦線CDのエラー、警告表示フラグ
	 */
	public String getTatesnCdClass() {
		return (String) get("TATESN_CD_CLASS");
	}

	/**
	 * Kz縦線CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tatesnCdClass Kz縦線CDのエラー、警告表示フラグ
	 */
	public void setTatesnCdClass(String tatesnCdClass) {
		put("TATESN_CD_CLASS", tatesnCdClass);
	}


	/**
	 * 処理区分のエラー、警告表示フラグを返します。
	 *
	 * @return 処理区分のエラー、警告表示フラグ
	 */
	public String getSyoriKbnClass() {
		return (String) get("SYORI_KBN_CLASS");
	}

	/**
	 * 処理区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syoriKbnClass 処理区分のエラー、警告表示フラグ
	 */
	public void setSyoriKbnClass(String syoriKbnClass) {
		put("SYORI_KBN_CLASS", syoriKbnClass);
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
