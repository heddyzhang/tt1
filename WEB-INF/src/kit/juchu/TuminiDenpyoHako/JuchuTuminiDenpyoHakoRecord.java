package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 積荷伝票発行
 * 　受注データ／HD部のレコードクラス
 */
public class JuchuTuminiDenpyoHakoRecord extends KitRecord {

	/** シリアルID */
	private static final long serialVersionUID = 2363765855938171912L;

	/** デバッグ */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public JuchuTuminiDenpyoHakoRecord() {
		super();
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input Map
	 */
	public JuchuTuminiDenpyoHakoRecord(Map<String, Object> input) {
		super(input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input HashMap
	 */
	public JuchuTuminiDenpyoHakoRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input JuchuTuminiDenpyoHakoRecord
	 */
	public JuchuTuminiDenpyoHakoRecord(JuchuTuminiDenpyoHakoRecord input) {
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
	public void modifyRecord(KitRecord reco) {
		JuchuTuminiDenpyoHakoRecord target = (JuchuTuminiDenpyoHakoRecord) reco;
		setDenpyoHakoFlg(target.getDenpyoHakoFlg()); 	// 伝票発行フラグ
		setSyuyakuKbn(target.getSyuyakuKbn()); 				// 集約区分
		setTuminokosiFlg(target.getTuminokosiFlg()); 		// 積み残しフラグ
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
		boolean bEquals = true;
		JuchuTuminiDenpyoHakoRecord target = (JuchuTuminiDenpyoHakoRecord) reco;

		// 伝票発行フラグ
		if (!PbsUtil.isEqual(getDenpyoHakoFlg(), target.getDenpyoHakoFlg())) {
			bEquals = false;
		}

		// 集約区分
		if (!PbsUtil.isEqual(getSyuyakuKbn(), target.getSyuyakuKbn())) {
			bEquals = false;
		}

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
				,"JYUCYU_NO_CLASS"
				,"UNSOTEN_CD_CLASS"
				,"UNSOTEN_NM_CLASS"
				,"SYUKA_DT_CLASS"
				,"ATUKAI_KBN_CLASS"
				,"SYUKA_SURYO_CASE_CLASS"
				,"SYUKA_SURYO_BARA_CLASS"
				,"JYURYO_TOT_CLASS"
				,"SYUYAKU_KBN_CLASS"
				,"TUMINOKOSI_FLG_CLASS"
				,"DENPYO_HAKO_FLG_CLASS"
				,"TUMIDEN_NO_CLASS"
				,"TUMIDEN_HAKO_DT_CLASS"
				,"TUMIDEN_HAKO_TM_CLASS"
				,"TUMIDEN_HAKO_CNT_CLASS"
				,"TUMIDEN_HAKOSYA_CLASS"
				,"JYUCYU_NO_FORMATTED_CLASS"
				,"ORS_TATESN_CD_CLASS"
				,"SYUHANTEN_CD_CLASS"
				,"SYUHANTEN_NM_CLASS"
				,"OROSITEN_CD_LAST_CLASS"
				,"OROSITEN_NM_LAST_CLASS"
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
	public boolean isDuplicated(JuchuTuminiDenpyoHakoRecord rec){
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
				,"UNSOTEN_CD_CLASS"
				,"UNSOTEN_NM_CLASS"
				,"SYUKA_DT_CLASS"
				,"ATUKAI_KBN_CLASS"
				,"SYUKA_SURYO_CASE_CLASS"
				,"SYUKA_SURYO_BARA_CLASS"
				,"JYURYO_TOT_CLASS"
				,"SYUYAKU_KBN_CLASS"
				,"TUMINOKOSI_FLG_CLASS"
				,"DENPYO_HAKO_FLG_CLASS"
				,"TUMIDEN_NO_CLASS"
				,"TUMIDEN_HAKO_DT_CLASS"
				,"TUMIDEN_HAKO_TM_CLASS"
				,"TUMIDEN_HAKO_CNT_CLASS"
				,"TUMIDEN_HAKOSYA_CLASS"
				,"JYUCYU_NO_FORMATTED_CLASS"
				,"ORS_TATESN_CD_CLASS"
				,"SYUHANTEN_CD_CLASS"
				,"SYUHANTEN_NM_CLASS"
				,"OROSITEN_CD_LAST_CLASS"
				,"OROSITEN_NM_LAST_CLASS"
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
		// 会社CD、受注NO
		super.setPrimaryKeys("KAISYA_CD", "JYUCYU_NO");
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
	 * 運送店名を返します。
	 *
	 * @return 運送店名
	 */
	public String getUnsotenNm() {
		return (String) get("UNSOTEN_NM");
	}

	/**
	 * 運送店名をレコードに格納します。
	 *
	 * @param unsotenNm 運送店名
	 */
	public void setUnsotenNm(String unsotenNm) {
		put("UNSOTEN_NM", unsotenNm);
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
	 * 出荷対応区分（扱い区分）を返します。
	 *
	 * @return 出荷対応区分（扱い区分）
	 */
	public String getSyukaTaioKbn() {
		return (String) get("SYUKA_TAIO_KBN");
	}

	/**
	 * 出荷対応区分（扱い区分）をレコードに格納します。
	 *
	 * @param syukaTaioKbn 出荷対応区分（扱い区分）
	 */
	public void setSyukaTaioKbn(String syukaTaioKbn) {
		put("SYUKA_TAIO_KBN", syukaTaioKbn);
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
	 * 伝票発行フラグを返します。
	 *
	 * @return 伝票発行フラグ
	 */
	public String getDenpyoHakoFlg() {
		return (String) get("DENPYO_HAKO_FLG");
	}

	/**
	 * 伝票発行フラグをレコードに格納します。
	 *
	 * @param denpyoHakoFlg 伝票発行フラグ
	 */
	public void setDenpyoHakoFlg(String denpyoHakoFlg) {
		put("DENPYO_HAKO_FLG", denpyoHakoFlg);
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
	 * 積荷伝票発行日を返します。
	 *
	 * @return 積荷伝票発行日
	 */
	public String getTumidenHakoDt() {
		return (String) get("TUMIDEN_HAKO_DT");
	}

	/**
	 * 積荷伝票発行日をレコードに格納します。
	 *
	 * @param tumidenHakoDt 積荷伝票発行日
	 */
	public void setTumidenHakoDt(String tumidenHakoDt) {
		put("TUMIDEN_HAKO_DT", tumidenHakoDt);
	}


	/**
	 * 積荷伝票発行時刻を返します。
	 *
	 * @return 積荷伝票発行時刻
	 */
	public String getTumidenHakoTm() {
		return (String) get("TUMIDEN_HAKO_TM");
	}

	/**
	 * 積荷伝票発行時刻をレコードに格納します。
	 *
	 * @param tumidenHakoTm 積荷伝票発行時刻
	 */
	public void setTumidenHakoTm(String tumidenHakoTm) {
		put("TUMIDEN_HAKO_TM", tumidenHakoTm);
	}


	/**
	 * 積荷伝票発行回数を返します。
	 *
	 * @return 積荷伝票発行回数
	 */
	public String getTumidenHakoCnt() {
		return (String) get("TUMIDEN_HAKO_CNT");
	}

	/**
	 * 積荷伝票発行回数をレコードに格納します。
	 *
	 * @param tumidenHakoCnt 積荷伝票発行回数
	 */
	public void setTumidenHakoCnt(String tumidenHakoCnt) {
		put("TUMIDEN_HAKO_CNT", tumidenHakoCnt);
	}


	/**
	 * 積荷伝票発行者IDを返します。
	 *
	 * @return 積荷伝票発行者ID
	 */
	public String getTumidenHakosya() {
		return (String) get("TUMIDEN_HAKOSYA");
	}

	/**
	 * 積荷伝票発行者IDをレコードに格納します。
	 *
	 * @param tumidenHakosya 積荷伝票発行者ID
	 */
	public void setTumidenHakosya(String tumidenHakosya) {
		put("TUMIDEN_HAKOSYA", tumidenHakosya);
	}


	/**
	 * 整形済み受注NOを返します。
	 *
	 * @return 整形済み受注NO
	 */
	public String getJyucyuNoFormatted() {
		return (String) get("JYUCYU_NO_FORMATTED");
	}

	/**
	 * 整形済み受注NOをレコードに格納します。
	 *
	 * @param jyucyuNoFormatted 整形済み受注NO
	 */
	public void setJyucyuNoFormatted(String jyucyuNoFormatted) {
		put("JYUCYU_NO_FORMATTED", jyucyuNoFormatted);
	}


	/**
	 * 縦線CDを返します。
	 *
	 * @return 縦線CD
	 */
	public String getOrsTatesnCd() {
		return (String) get("ORS_TATESN_CD");
	}

	/**
	 * 縦線CDをレコードに格納します。
	 *
	 * @param orsTatesnCd 縦線CD
	 */
	public void setOrsTatesnCd(String orsTatesnCd) {
		put("ORS_TATESN_CD", orsTatesnCd);
	}


	/**
	 * 酒販店（統一）CDを返します。
	 *
	 * @return 酒販店（統一）CD
	 */
	public String getSyuhantenCd() {
		return (String) get("SYUHANTEN_CD");
	}

	/**
	 * 酒販店（統一）CDをレコードに格納します。
	 *
	 * @param syuhantenCd 酒販店（統一）CD
	 */
	public void setSyuhantenCd(String syuhantenCd) {
		put("SYUHANTEN_CD", syuhantenCd);
	}


	/**
	 * 酒販店名を返します。
	 *
	 * @return 酒販店名
	 */
	public String getSyuhantenNm() {
		return (String) get("SYUHANTEN_NM");
	}

	/**
	 * 酒販店名をレコードに格納します。
	 *
	 * @param syuhantenNm 酒販店名
	 */
	public void setSyuhantenNm(String syuhantenNm) {
		put("SYUHANTEN_NM", syuhantenNm);
	}


	/**
	 * 最終送荷先卸CDを返します。
	 *
	 * @return 最終送荷先卸CD
	 */
	public String getOrositenCdLast() {
		return (String) get("OROSITEN_CD_LAST");
	}

	/**
	 * 最終送荷先卸CDをレコードに格納します。
	 *
	 * @param orositenCdLast 最終送荷先卸CD
	 */
	public void setOrositenCdLast(String orositenCdLast) {
		put("OROSITEN_CD_LAST", orositenCdLast);
	}


	/**
	 * 最終送荷先卸名を返します。
	 *
	 * @return 最終送荷先卸名
	 */
	public String getOrositenNmLast() {
		return (String) get("OROSITEN_NM_LAST");
	}

	/**
	 * 最終送荷先卸名をレコードに格納します。
	 *
	 * @param orositenNmLast 最終送荷先卸名
	 */
	public void setOrositenNmLast(String orositenNmLast) {
		put("OROSITEN_NM_LAST", orositenNmLast);
	}


	/**
	 * 摘要区分(01)を返します。
	 *
	 * @return 摘要区分(01)
	 */
	public String getTekiyoKbn1() {
		return (String) get("TEKIYO_KBN1");
	}

	/**
	 * 摘要区分(01)をレコードに格納します。
	 *
	 * @param tekiyoKbn1 摘要区分(01)
	 */
	public void setTekiyoKbn1(String tekiyoKbn1) {
		put("TEKIYO_KBN1", tekiyoKbn1);
	}


	/**
	 * 摘要内容(01)を返します。
	 *
	 * @return 摘要内容(01)
	 */
	public String getTekiyoNm1() {
		return (String) get("TEKIYO_NM1");
	}

	/**
	 * 摘要内容(01)をレコードに格納します。
	 *
	 * @param tekiyoNm1 摘要内容(01)
	 */
	public void setTekiyoNm1(String tekiyoNm1) {
		put("TEKIYO_NM1", tekiyoNm1);
	}


	/**
	 * 積荷伝票記載ﾌﾗｸﾞ(01)を返します。
	 *
	 * @return 積荷伝票記載ﾌﾗｸﾞ(01)
	 */
	public String getTumidenKisaiFlg1() {
		return (String) get("TUMIDEN_KISAI_FLG1");
	}

	/**
	 * 積荷伝票記載ﾌﾗｸﾞ(01)をレコードに格納します。
	 *
	 * @param tumidenKisaiFlg1 積荷伝票記載ﾌﾗｸﾞ(01)
	 */
	public void setTumidenKisaiFlg1(String tumidenKisaiFlg1) {
		put("TUMIDEN_KISAI_FLG1", tumidenKisaiFlg1);
	}


	/**
	 * 摘要区分(02)を返します。
	 *
	 * @return 摘要区分(02)
	 */
	public String getTekiyoKbn2() {
		return (String) get("TEKIYO_KBN2");
	}

	/**
	 * 摘要区分(02)をレコードに格納します。
	 *
	 * @param tekiyoKbn2 摘要区分(02)
	 */
	public void setTekiyoKbn2(String tekiyoKbn2) {
		put("TEKIYO_KBN2", tekiyoKbn2);
	}


	/**
	 * 摘要内容(02)を返します。
	 *
	 * @return 摘要内容(02)
	 */
	public String getTekiyoNm2() {
		return (String) get("TEKIYO_NM2");
	}

	/**
	 * 摘要内容(02)をレコードに格納します。
	 *
	 * @param tekiyoNm2 摘要内容(02)
	 */
	public void setTekiyoNm2(String tekiyoNm2) {
		put("TEKIYO_NM2", tekiyoNm2);
	}


	/**
	 * 積荷伝票記載ﾌﾗｸﾞ(02)を返します。
	 *
	 * @return 積荷伝票記載ﾌﾗｸﾞ(02)
	 */
	public String getTumidenKisaiFlg2() {
		return (String) get("TUMIDEN_KISAI_FLG2");
	}

	/**
	 * 積荷伝票記載ﾌﾗｸﾞ(02)をレコードに格納します。
	 *
	 * @param tumidenKisaiFlg2 積荷伝票記載ﾌﾗｸﾞ(02)
	 */
	public void setTumidenKisaiFlg2(String tumidenKisaiFlg2) {
		put("TUMIDEN_KISAI_FLG2", tumidenKisaiFlg2);
	}


	/**
	 * 摘要区分(03)を返します。
	 *
	 * @return 摘要区分(03)
	 */
	public String getTekiyoKbn3() {
		return (String) get("TEKIYO_KBN3");
	}

	/**
	 * 摘要区分(03)をレコードに格納します。
	 *
	 * @param tekiyoKbn3 摘要区分(03)
	 */
	public void setTekiyoKbn3(String tekiyoKbn3) {
		put("TEKIYO_KBN3", tekiyoKbn3);
	}


	/**
	 * 摘要内容(03)を返します。
	 *
	 * @return 摘要内容(03)
	 */
	public String getTekiyoNm3() {
		return (String) get("TEKIYO_NM3");
	}

	/**
	 * 摘要内容(03)をレコードに格納します。
	 *
	 * @param tekiyoNm3 摘要内容(03)
	 */
	public void setTekiyoNm3(String tekiyoNm3) {
		put("TEKIYO_NM3", tekiyoNm3);
	}


	/**
	 * 積荷伝票記載ﾌﾗｸﾞ(03)を返します。
	 *
	 * @return 積荷伝票記載ﾌﾗｸﾞ(03)
	 */
	public String getTumidenKisaiFlg3() {
		return (String) get("TUMIDEN_KISAI_FLG3");
	}

	/**
	 * 積荷伝票記載ﾌﾗｸﾞ(03)をレコードに格納します。
	 *
	 * @param tumidenKisaiFlg3 積荷伝票記載ﾌﾗｸﾞ(03)
	 */
	public void setTumidenKisaiFlg3(String tumidenKisaiFlg3) {
		put("TUMIDEN_KISAI_FLG3", tumidenKisaiFlg3);
	}


	/**
	 * 摘要区分(04)を返します。
	 *
	 * @return 摘要区分(04)
	 */
	public String getTekiyoKbn4() {
		return (String) get("TEKIYO_KBN4");
	}

	/**
	 * 摘要区分(04)をレコードに格納します。
	 *
	 * @param tekiyoKbn4 摘要区分(04)
	 */
	public void setTekiyoKbn4(String tekiyoKbn4) {
		put("TEKIYO_KBN4", tekiyoKbn4);
	}


	/**
	 * 摘要内容(04)を返します。
	 *
	 * @return 摘要内容(04)
	 */
	public String getTekiyoNm4() {
		return (String) get("TEKIYO_NM4");
	}

	/**
	 * 摘要内容(04)をレコードに格納します。
	 *
	 * @param tekiyoNm4 摘要内容(04)
	 */
	public void setTekiyoNm4(String tekiyoNm4) {
		put("TEKIYO_NM4", tekiyoNm4);
	}


	/**
	 * 積荷伝票記載ﾌﾗｸﾞ(04)を返します。
	 *
	 * @return 積荷伝票記載ﾌﾗｸﾞ(04)
	 */
	public String getTumidenKisaiFlg4() {
		return (String) get("TUMIDEN_KISAI_FLG4");
	}

	/**
	 * 積荷伝票記載ﾌﾗｸﾞ(04)をレコードに格納します。
	 *
	 * @param tumidenKisaiFlg4 積荷伝票記載ﾌﾗｸﾞ(04)
	 */
	public void setTumidenKisaiFlg4(String tumidenKisaiFlg4) {
		put("TUMIDEN_KISAI_FLG4", tumidenKisaiFlg4);
	}


	/**
	 * 摘要区分(05)を返します。
	 *
	 * @return 摘要区分(05)
	 */
	public String getTekiyoKbn5() {
		return (String) get("TEKIYO_KBN5");
	}

	/**
	 * 摘要区分(05)をレコードに格納します。
	 *
	 * @param tekiyoKbn5 摘要区分(05)
	 */
	public void setTekiyoKbn5(String tekiyoKbn5) {
		put("TEKIYO_KBN5", tekiyoKbn5);
	}


	/**
	 * 摘要内容(05)を返します。
	 *
	 * @return 摘要内容(05)
	 */
	public String getTekiyoNm5() {
		return (String) get("TEKIYO_NM5");
	}

	/**
	 * 摘要内容(05)をレコードに格納します。
	 *
	 * @param tekiyoNm5 摘要内容(05)
	 */
	public void setTekiyoNm5(String tekiyoNm5) {
		put("TEKIYO_NM5", tekiyoNm5);
	}


	/**
	 * 積荷伝票記載ﾌﾗｸﾞ(05)を返します。
	 *
	 * @return 積荷伝票記載ﾌﾗｸﾞ(05)
	 */
	public String getTumidenKisaiFlg5() {
		return (String) get("TUMIDEN_KISAI_FLG5");
	}

	/**
	 * 積荷伝票記載ﾌﾗｸﾞ(05)をレコードに格納します。
	 *
	 * @param tumidenKisaiFlg5 積荷伝票記載ﾌﾗｸﾞ(05)
	 */
	public void setTumidenKisaiFlg5(String tumidenKisaiFlg5) {
		put("TUMIDEN_KISAI_FLG5", tumidenKisaiFlg5);
	}


	/**
	 * 成形した摘要内容を返します。
	 *
	 * @return 成形した摘要内容
	 */
	public String getTekiyoNmAll() {
		return (String) get("TEKIYO_NM_ALL");
	}

	/**
	 * 成形した摘要内容をレコードに格納します。
	 *
	 * @param tekiyoNmAll 成形した摘要内容
	 */
	public void setTekiyoNmAll(String tekiyoNmAll) {
		put("TEKIYO_NM_ALL", tekiyoNmAll);
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
	 * 運送店名のエラー、警告表示フラグを返します。
	 *
	 * @return 運送店名のエラー、警告表示フラグ
	 */
	public String getUnsotenNmClass() {
		return (String) get("UNSOTEN_NM_CLASS");
	}

	/**
	 * 運送店名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param unsotenNmClass 運送店名のエラー、警告表示フラグ
	 */
	public void setUnsotenNmClass(String unsotenNmClass) {
		put("UNSOTEN_NM_CLASS", unsotenNmClass);
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
	 * 出荷対応区分（扱い区分）のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷対応区分（扱い区分）のエラー、警告表示フラグ
	 */
	public String getSyukaTaioKbnClass() {
		return (String) get("SYUKA_TAIO_KBN_CLASS");
	}

	/**
	 * 出荷対応区分（扱い区分）のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaTaioKbnClass 出荷対応区分（扱い区分）のエラー、警告表示フラグ
	 */
	public void setSyukaTaioKbnClass(String syukaTaioKbnClass) {
		put("SYUKA_TAIO_KBN_CLASS", syukaTaioKbnClass);
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
	 * 伝票発行フラグのエラー、警告表示フラグを返します。
	 *
	 * @return 伝票発行フラグのエラー、警告表示フラグ
	 */
	public String getDenpyoHakoFlgClass() {
		return (String) get("DENPYO_HAKO_FLG_CLASS");
	}

	/**
	 * 伝票発行フラグのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param denpyoHakoFlgClass 伝票発行フラグのエラー、警告表示フラグ
	 */
	public void setDenpyoHakoFlgClass(String denpyoHakoFlgClass) {
		put("DENPYO_HAKO_FLG_CLASS", denpyoHakoFlgClass);
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
	 * 積荷伝票発行日のエラー、警告表示フラグを返します。
	 *
	 * @return 積荷伝票発行日のエラー、警告表示フラグ
	 */
	public String getTumidenHakoDtClass() {
		return (String) get("TUMIDEN_HAKO_DT_CLASS");
	}

	/**
	 * 積荷伝票発行日のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tumidenHakoDtClass 積荷伝票発行日のエラー、警告表示フラグ
	 */
	public void setTumidenHakoDtClass(String tumidenHakoDtClass) {
		put("TUMIDEN_HAKO_DT_CLASS", tumidenHakoDtClass);
	}


	/**
	 * 積荷伝票発行時刻のエラー、警告表示フラグを返します。
	 *
	 * @return 積荷伝票発行時刻のエラー、警告表示フラグ
	 */
	public String getTumidenHakoTmClass() {
		return (String) get("TUMIDEN_HAKO_TM_CLASS");
	}

	/**
	 * 積荷伝票発行時刻のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tumidenHakoTmClass 積荷伝票発行時刻のエラー、警告表示フラグ
	 */
	public void setTumidenHakoTmClass(String tumidenHakoTmClass) {
		put("TUMIDEN_HAKO_TM_CLASS", tumidenHakoTmClass);
	}


	/**
	 * 積荷伝票発行回数のエラー、警告表示フラグを返します。
	 *
	 * @return 積荷伝票発行回数のエラー、警告表示フラグ
	 */
	public String getTumidenHakoCntClass() {
		return (String) get("TUMIDEN_HAKO_CNT_CLASS");
	}

	/**
	 * 積荷伝票発行回数のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tumidenHakoCntClass 積荷伝票発行回数のエラー、警告表示フラグ
	 */
	public void setTumidenHakoCntClass(String tumidenHakoCntClass) {
		put("TUMIDEN_HAKO_CNT_CLASS", tumidenHakoCntClass);
	}


	/**
	 * 積荷伝票発行者IDのエラー、警告表示フラグを返します。
	 *
	 * @return 積荷伝票発行者IDのエラー、警告表示フラグ
	 */
	public String getTumidenHakosyaClass() {
		return (String) get("TUMIDEN_HAKOSYA_CLASS");
	}

	/**
	 * 積荷伝票発行者IDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tumidenHakosyaClass 積荷伝票発行者IDのエラー、警告表示フラグ
	 */
	public void setTumidenHakosyaClass(String tumidenHakosyaClass) {
		put("TUMIDEN_HAKOSYA_CLASS", tumidenHakosyaClass);
	}


	/**
	 * 整形済み受注NOのエラー、警告表示フラグを返します。
	 *
	 * @return 整形済み受注NOのエラー、警告表示フラグ
	 */
	public String getJyucyuNoFormattedClass() {
		return (String) get("JYUCYU_NO_FORMATTED_CLASS");
	}

	/**
	 * 整形済み受注NOのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param jyucyuNoFormattedClass 整形済み受注NOのエラー、警告表示フラグ
	 */
	public void setJyucyuNoFormattedClass(String jyucyuNoFormattedClass) {
		put("JYUCYU_NO_FORMATTED_CLASS", jyucyuNoFormattedClass);
	}


	/**
	 * 縦線CDのエラー、警告表示フラグを返します。
	 *
	 * @return 縦線CDのエラー、警告表示フラグ
	 */
	public String getOrsTatesnCdClass() {
		return (String) get("ORS_TATESN_CD_CLASS");
	}

	/**
	 * 縦線CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param orsTatesnCdClass 縦線CDのエラー、警告表示フラグ
	 */
	public void setOrsTatesnCdClass(String orsTatesnCdClass) {
		put("ORS_TATESN_CD_CLASS", orsTatesnCdClass);
	}


	/**
	 * 酒販店（統一）CDのエラー、警告表示フラグを返します。
	 *
	 * @return 酒販店（統一）CDのエラー、警告表示フラグ
	 */
	public String getSyuhantenCdClass() {
		return (String) get("SYUHANTEN_CD_CLASS");
	}

	/**
	 * 酒販店（統一）CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syuhantenCdClass 酒販店（統一）CDのエラー、警告表示フラグ
	 */
	public void setSyuhantenCdClass(String syuhantenCdClass) {
		put("SYUHANTEN_CD_CLASS", syuhantenCdClass);
	}


	/**
	 * 酒販店名のエラー、警告表示フラグを返します。
	 *
	 * @return 酒販店名のエラー、警告表示フラグ
	 */
	public String getSyuhantenNmClass() {
		return (String) get("SYUHANTEN_NM_CLASS");
	}

	/**
	 * 酒販店名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syuhantenNmClass 酒販店名のエラー、警告表示フラグ
	 */
	public void setSyuhantenNmClass(String syuhantenNmClass) {
		put("SYUHANTEN_NM_CLASS", syuhantenNmClass);
	}


	/**
	 * 最終送荷先卸CDのエラー、警告表示フラグを返します。
	 *
	 * @return 最終送荷先卸CDのエラー、警告表示フラグ
	 */
	public String getOrositenCdLastClass() {
		return (String) get("OROSITEN_CD_LAST_CLASS");
	}

	/**
	 * 最終送荷先卸CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param orositenCdLastClass 最終送荷先卸CDのエラー、警告表示フラグ
	 */
	public void setOrositenCdLastClass(String orositenCdLastClass) {
		put("OROSITEN_CD_LAST_CLASS", orositenCdLastClass);
	}


	/**
	 * 最終送荷先卸名のエラー、警告表示フラグを返します。
	 *
	 * @return 最終送荷先卸名のエラー、警告表示フラグ
	 */
	public String getOrositenNmLastClass() {
		return (String) get("OROSITEN_NM_LAST_CLASS");
	}

	/**
	 * 最終送荷先卸名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param orositenNmLastClass 最終送荷先卸名のエラー、警告表示フラグ
	 */
	public void setOrositenNmLastClass(String orositenNmLastClass) {
		put("OROSITEN_NM_LAST_CLASS", orositenNmLastClass);
	}

}	// -- class
