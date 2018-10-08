package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 受注入力（受注データ／ディテール部）のレコードクラス
 */
public class KuracKuraChokuDataInDtRecord extends KitRecord {

	/**
	 *
	 */
	/** シリアルID */
	private static final long serialVersionUID = 9074440277197866229L;

	/** デバッグ */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public KuracKuraChokuDataInDtRecord() {
		super();
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input Map
	 */
	public KuracKuraChokuDataInDtRecord(Map<String, Object> input) {
		super(input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input HashMap
	 */
	public KuracKuraChokuDataInDtRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input JuchuJuchuDataInDtRecord
	 */
	public KuracKuraChokuDataInDtRecord(KuracKuraChokuDataInDtRecord input) {
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
		KuracKuraChokuDataInDtRecord newRec = (KuracKuraChokuDataInDtRecord) reco;
		// 追加モード
		if (isAddMode()) {
			setKuradenLineNo(newRec.getKuradenLineNo());	// 商品行No
			setCpKbn(newRec.getCpKbn()); 					// ｷｬﾝﾍﾟｰﾝ対象区分
		}

		setShohinCd(newRec.getShohinCd()); 					// 商品CD
		setShohinNm(newRec.getShohinNm()); 					// 商品名
		setYoukiKigoNm(newRec.getYoukiKigoNm()); 			// 容器名
		setShohinSet(newRec.getShohinSet()); 				// 商品申込ｾｯﾄ数
		setHanbaiTanka(newRec.getHanbaiTanka()); 			// 販売単価
		setHanbaiGaku(newRec.getHanbaiGaku()); 				// 販売額
	}

	/**
	 * 空白かどうか判定
	 *
	 * @return true
	 */
	public boolean isEmpty() {
		boolean bEmpty = true;

		// 商品コード
		if (!PbsUtil.isEmpty(getShohinCd())) {
			bEmpty = false;
		}
		// 商品申込ｾｯﾄ数
		if (!PbsUtil.isEmpty(getShohinSet())) {
			bEmpty = false;
		}

		// 販売単価
		if (!PbsUtil.isEmpty(getHanbaiTanka())) {
			bEmpty = false;
		}

		// 商品コードView
		if (!PbsUtil.isEmpty(getShohinCdView())) {
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
		KuracKuraChokuDataInDtRecord target = (KuracKuraChokuDataInDtRecord) reco;
		// 追加モードの場合（入力項目）
		if (this.isAddMode()) {

			// 商品コード
			if (!PbsUtil.isEqual(getShohinCd(), target.getShohinCd())) {
				bEquals = false;
			}

			// 商品申込ｾｯﾄ数
			if (!PbsUtil.isEqual(getShohinSet(), target.getShohinSet())) {
				bEquals = false;
			}

			// 販売単価
			if (!PbsUtil.isEqualForDec(getHanbaiTanka(), target.getHanbaiTanka())) {
				bEquals = false;
			}

		// 変更モードの場合（主キー項目以外）
		} else {

			// 商品コード
			if (!PbsUtil.isEqual(getShohinCd(), target.getShohinCd())) {
				setShohinCdClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setShohinCdClass(STYLE_CLASS_NO_EDIT);
			}

			// 商品申込ｾｯﾄ数
			if (!PbsUtil.isEqual(getShohinSet(), target.getShohinSet())) {
				setShohinSetClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setShohinSetClass(STYLE_CLASS_NO_EDIT);
			}

			// 販売単価
			if (!PbsUtil.isEqualForDec(getHanbaiTanka(), target.getHanbaiTanka())) {
				setHanbaiTankaClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setHanbaiTankaClass(STYLE_CLASS_NO_EDIT);
			}

			// 販売額
			if (!PbsUtil.isEqualForDec(getHanbaiGaku(), target.getHanbaiGaku())) {
				setHanbaiGakuClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setHanbaiGakuClass(STYLE_CLASS_NO_EDIT);
			}
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
			,"KURADATA_NO_CLASS"
			,"KURADEN_LINE_NO_CLASS"
			,"SHOHIN_CD_CLASS"
			,"SHOHIN_SET_CLASS"
			,"HANBAI_TANKA_CLASS"
			,"CP_KBN_CLASS"
			,"SYUKADEN_NO_CLASS"
			,"TEISEI_SYUKA_DT_CLASS"
			,"TEISEI_URIDEN_NO_CLASS"
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
	public boolean isDuplicated(KuracKuraChokuDataInDtRecord rec){
		boolean ret = true;

		// 会社CD + 蔵直ﾃﾞｰﾀ連番 +商品行No
		if (!PbsUtil.isEqualIgnoreZero(getKaisyaCd()+getKuradataNo()+getKuradenLineNo(), rec.getKaisyaCd()+rec.getKuradenLineNo()+rec.getKaisyaCd())) {
			ret = false;
		}

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
				 "RIYOU_KBN_CLASS"
				,"KURADATA_NO_CLASS"
				,"KURADEN_LINE_NO_CLASS"
				,"SHOHIN_CD_CLASS"
				,"SHOHIN_SET_CLASS"
				,"HANBAI_TANKA_CLASS"
				,"CP_KBN_CLASS"
				,"SYUKADEN_NO_CLASS"
				,"TEISEI_SYUKA_DT_CLASS"
				,"TEISEI_URIDEN_NO_CLASS"
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
		// 会社CD、蔵直ﾃﾞｰﾀ連番、商品行NO
		super.setPrimaryKeys("KAISYA_CD", "KURADATA_NO", "KURADEN_LINE_NO");
	}

	/**
	 * 入力値と名称をリセット
	 */
	public void reset() {
		this.modifyRecord(new KuracKuraChokuDataInDtRecord());
		this.setShohinCd(CHAR_BLANK); // 商品CD
		this.setShohinNm(CHAR_BLANK); // 商品名
		this.setHanbaiTanka(CHAR_BLANK); // 単価
		this.setShohinSet(CHAR_BLANK); // 商品申込ｾｯﾄ数
		this.setHanbaiTanka(CHAR_BLANK); // 販売単価
		this.setCpKbn(CHAR_BLANK); // ｷｬﾝﾍﾟｰﾝ対象区分
		this.setSyukadenNo(CHAR_BLANK); // 黄桜出荷伝票NO
		this.setTeiseiSyukaDt(CHAR_BLANK); // 訂正時訂正元出荷日
		this.setTeiseiUridenNo(CHAR_BLANK); // 訂正時訂正元売上伝票NO
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
	 * 蔵直ﾃﾞｰﾀ連番を返します。
	 *
	 * @return 蔵直ﾃﾞｰﾀ連番
	 */
	public String getKuradataNo() {
		return (String) get("KURADATA_NO");
	}

	/**
	 * 蔵直ﾃﾞｰﾀ連番をレコードに格納します。
	 *
	 * @param kuradataNo 蔵直ﾃﾞｰﾀ連番
	 */
	public void setKuradataNo(String kuradataNo) {
		put("KURADATA_NO", kuradataNo);
	}


	/**
	 * 商品行Noを返します。
	 *
	 * @return 商品行No
	 */
	public String getKuradenLineNo() {
		return (String) get("KURADEN_LINE_NO");
	}

	/**
	 * 商品行Noをレコードに格納します。
	 *
	 * @param kuradenLineNo 商品行No
	 */
	public void setKuradenLineNo(String kuradenLineNo) {
		put("KURADEN_LINE_NO", kuradenLineNo);
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
	 * 商品申込ｾｯﾄ数を返します。
	 *
	 * @return 商品申込ｾｯﾄ数
	 */
	public String getShohinSet() {
		return (String) get("SHOHIN_SET");
	}

	/**
	 * 商品申込ｾｯﾄ数をレコードに格納します。
	 *
	 * @param shohinSet 商品申込ｾｯﾄ数
	 */
	public void setShohinSet(String shohinSet) {
		put("SHOHIN_SET", shohinSet);
	}


	/**
	 * 販売単価を返します。
	 *
	 * @return 販売単価
	 */
	public String getHanbaiTanka() {
		return (String) get("HANBAI_TANKA");
	}

	/**
	 * 販売単価をレコードに格納します。
	 *
	 * @param hanbaiTanka 販売単価
	 */
	public void setHanbaiTanka(String hanbaiTanka) {
		put("HANBAI_TANKA", hanbaiTanka);
	}

	/**
	 * 販売額を返します。
	 *
	 * @return 販売額
	 */
	public String getHanbaiGaku() {
		return (String) get("HANBAI_GAKU");
	}

	/**
	 * 販売額をレコードに格納します。
	 *
	 * @param hanbaiTanka 販売額
	 */
	public void setHanbaiGaku(String hanbaiGaku) {
		put("HANBAI_GAKU", hanbaiGaku);
	}

	/**
	 * ｷｬﾝﾍﾟｰﾝ対象区分を返します。
	 *
	 * @return ｷｬﾝﾍﾟｰﾝ対象区分
	 */
	public String getCpKbn() {
		return (String) get("CP_KBN");
	}

	/**
	 * ｷｬﾝﾍﾟｰﾝ対象区分をレコードに格納します。
	 *
	 * @param cpKbn ｷｬﾝﾍﾟｰﾝ対象区分
	 */
	public void setCpKbn(String cpKbn) {
		put("CP_KBN", cpKbn);
	}


	/**
	 * 黄桜出荷伝票NOを返します。
	 *
	 * @return 黄桜出荷伝票NO
	 */
	public String getSyukadenNo() {
		return (String) get("SYUKADEN_NO");
	}

	/**
	 * 黄桜出荷伝票NOをレコードに格納します。
	 *
	 * @param syukadenNo 黄桜出荷伝票NO
	 */
	public void setSyukadenNo(String syukadenNo) {
		put("SYUKADEN_NO", syukadenNo);
	}


	/**
	 *訂正時訂正元出荷日を返します。
	 *
	 * @return 訂正時訂正元出荷日
	 */
	public String getTeiseiSyukaDt() {
		return (String) get("TEISEI_SYUKA_DT");
	}

	/**
	 * 訂正時訂正元出荷日をレコードに格納します。
	 *
	 * @param teiseiSyukaDt 訂正時訂正元出荷日
	 */
	public void setTeiseiSyukaDt(String teiseiSyukaDt) {
		put("TEISEI_SYUKA_DT", teiseiSyukaDt);
	}


	/**
	 * 訂正時訂正元売上伝票NOを返します。
	 *
	 * @return 訂正時訂正元売上伝票NO
	 */
	public String getTeiseiUridenNo() {
		return (String) get("TEISEI_URIDEN_NO");
	}

	/**
	 * 訂正時訂正元売上伝票NOをレコードに格納します。
	 *
	 * @param teiseiUridenNo 訂正時訂正元売上伝票NO
	 */
	public void setTeiseiUridenNo(String teiseiUridenNo) {
		put("TEISEI_URIDEN_NO", teiseiUridenNo);
	}

	/**
	 * 商品名を返します。
	 *
	 * @return 商品名
	 */
	public String getShohinNm() {
		return (String) get("SHOHIN_NM");
	}

	/**
	 * 商品名をレコードに格納します。
	 *
	 * @param shohinNm 商品名
	 */
	public void setShohinNm(String shohinNm) {
		put("SHOHIN_NM", shohinNm);
	}

	/**
	 * 行Noを返します。
	 *
	 * @return 商品名
	 */
	public String getIdxNo() {
		return (String) get("IDX_NO");
	}

	/**
	 * 行Noをレコードに格納します。
	 *
	 * @param shohinNm 商品名
	 */
	public void setIdxNo(String idxNo) {
		put("IDX_NO", idxNo);
	}

	/**
	 * 売伝用 ｾｯﾄ記号/容量名(1)を返します。
	 *
	 * @return 売伝用 ｾｯﾄ記号/容量名(1)
	 */
	public String getYoukiKigoNm() {
		return (String) get("YOUKI_KIGO_NM");
	}

	/**
	 * 売伝用 ｾｯﾄ記号/容量名(1)をレコードに格納します。
	 *
	 * @param youkiKigoNm1 売伝用 ｾｯﾄ記号/容量名(1)
	 */
	public void setYoukiKigoNm(String youkiKigoNm1) {
		put("YOUKI_KIGO_NM", youkiKigoNm1);
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
	 * 蔵直ﾃﾞｰﾀ連番のエラー、警告表示フラグを返します。
	 *
	 * @return 蔵直ﾃﾞｰﾀ連番のエラー、警告表示フラグ
	 */
	public String getKuradataNoClass() {
		return (String) get("KURADATA_NO_CLASS");
	}

	/**
	 *蔵直ﾃﾞｰﾀ連番のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param kuradataNoClass 蔵直ﾃﾞｰﾀ連番のエラー、警告表示フラグ
	 */
	public void setKuradataNoClass(String kuradataNoClass) {
		put("KURADATA_NO_CLASS", kuradataNoClass);
	}


	/**
	 * 商品行NOのエラー、警告表示フラグを返します。
	 *
	 * @return 商品行NOのエラー、警告表示フラグ
	 */
	public String getKudadenLineNoClass() {
		return (String) get("KURADEN_LINE_NO_CLASS");
	}

	/**
	 * 商品行NOのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param kudadenLineNoClass 商品行NOのエラー、警告表示フラグ
	 */
	public void setKudadenLineNoClass(String kudadenLineNoClass) {
		put("KURADEN_LINE_NO_CLASS", kudadenLineNoClass);
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
	 * 商品申込ｾｯﾄ数のエラー、警告表示フラグを返します。
	 *
	 * @return 商品申込ｾｯﾄ数のエラー、警告表示フラグ
	 */
	public String getShohinSetClass() {
		return (String) get("SHOHIN_SET_CLASS");
	}

	/**
	 * 商品申込ｾｯﾄ数のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param shohinSetClass 商品申込ｾｯﾄ数のエラー、警告表示フラグ
	 */
	public void setShohinSetClass(String shohinSetClass) {
		put("SHOHIN_SET_CLASS", shohinSetClass);
	}


	/**
	 * 販売単価のエラー、警告表示フラグを返します。
	 *
	 * @return 販売単価のエラー、警告表示フラグ
	 */
	public String getHanbaiTankaClass() {
		return (String) get("HANBAI_TANKA_CLASS");
	}

	/**
	 * 販売単価のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param hanbaiTankaClass 販売単価のエラー、警告表示フラグ
	 */
	public void setHanbaiTankaClass(String hanbaiTankaClass) {
		put("HANBAI_TANKA_CLASS", hanbaiTankaClass);
	}

	/**
	 * 販売額のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @return  販売額のエラー、警告表示フラグをレコードに格納します。
	 */
	public String getHanbaiGakuClass() {
		return (String) get("HANBAI_GAKU_CLASS");
	}

	/**
	 * 販売額のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param hanbaiTankaView 販売単価
	 */
	public void setHanbaiGakuClass(String hanbaiGakuClass) {
		put("HANBAI_GAKU_CLASS", hanbaiGakuClass);
	}

	/**
	 * 商品名のエラー、警告表示フラグを返します。
	 *
	 * @return 商品名のエラー、警告表示フラグ
	 */
	public String getShohinNmClass() {
		return (String) get("SHOHIN_NM_CLASS");
	}

	/**
	 * 商品名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param shohinNmClass 商品名のエラー、警告表示フラグ
	 */
	public void setShohinNmClass(String shohinNmClass) {
		put("SHOHIN_NM_CLASS", shohinNmClass);
	}


	/**
	 * 売伝用 ｾｯﾄ記号/容量名(1)のエラー、警告表示フラグを返します。
	 *
	 * @return 売伝用 ｾｯﾄ記号/容量名(1)のエラー、警告表示フラグ
	 */
	public String getYoukiKigoNmClass() {
		return (String) get("YOUKI_KIGO_NM_CLASS");
	}

	/**
	 * 売伝用 ｾｯﾄ記号/容量名(1)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param youkiKigoNm1Class 売伝用 ｾｯﾄ記号/容量名(1)のエラー、警告表示フラグ
	 */
	public void setYoukiKigoNmClass(String youkiKigoNmClass) {
		put("YOUKI_KIGO_NM_CLASS", youkiKigoNmClass);
	}


	// ====変更前表示用====
	/**
	 * 利用区分を返します。
	 *
	 * @return 利用区分
	 */
	public String getRiyouKbnView() {
		return (String) get("RIYOU_KBN_VIEW");
	}

	/**
	 * 利用区分をレコードに格納します。
	 *
	 * @param riyouKbnView 利用区分
	 */
	public void setRiyouKbnView(String riyouKbnView) {
		put("RIYOU_KBN_VIEW", riyouKbnView);
	}


	/**
	 * 会社CDを返します。
	 *
	 * @return 会社CD
	 */
	public String getKaisyaCdView() {
		return (String) get("KAISYA_CD_VIEW");
	}

	/**
	 * 会社CDをレコードに格納します。
	 *
	 * @param kaisyaCdView 会社CD
	 */
	public void setKaisyaCdView(String kaisyaCdView) {
		put("KAISYA_CD_VIEW", kaisyaCdView);
	}


	/**
	 * 蔵直ﾃﾞｰﾀ連番を返します。
	 *
	 * @return 蔵直ﾃﾞｰﾀ連番
	 */
	public String getKuradataNoView() {
		return (String) get("KURADATA_NO_VIEW");
	}

	/**
	 * 蔵直ﾃﾞｰﾀ連番をレコードに格納します。
	 *
	 * @param kuradataNoView 蔵直ﾃﾞｰﾀ連番
	 */
	public void setKuradataNoView(String kuradataNoView) {
		put("KURADATA_NO_VIEW", kuradataNoView);
	}


	/**
	 * 商品行NOを返します。
	 *
	 * @return 商品行NO
	 */
	public String getKuradenLineNoView() {
		return (String) get("KURADEN_LINE_NO_VIEW");
	}

	/**
	 * 商品行NOをレコードに格納します。
	 *
	 * @param kuradenLineNoView 商品行NO
	 */
	public void setKuradenLineNoView(String kuradenLineNoView) {
		put("KURADEN_LINE_NO_VIEW", kuradenLineNoView);
	}


	/**
	 * 商品CDを返します。
	 *
	 * @return 商品CD
	 */
	public String getShohinCdView() {
		return (String) get("SHOHIN_CD_VIEW");
	}

	/**
	 * 商品CDをレコードに格納します。
	 *
	 * @param shohinCdView 商品CD
	 */
	public void setShohinCdView(String shohinCdView) {
		put("SHOHIN_CD_VIEW", shohinCdView);
	}


	/**
	 * 商品申込ｾｯﾄ数を返します。
	 *
	 * @return 商品申込ｾｯﾄ数
	 */
	public String getShohinSetView() {
		return (String) get("SHOHIN_SET_VIEW");
	}

	/**
	 * 商品申込ｾｯﾄ数をレコードに格納します。
	 *
	 * @param shohinSetView 商品申込ｾｯﾄ数
	 */
	public void setShohinSetView(String shohinSetView) {
		put("SHOHIN_SET_VIEW", shohinSetView);
	}


	/**
	 * 販売単価を返します。
	 *
	 * @return 販売単価
	 */
	public String getHanbaiTankaView() {
		return (String) get("HANBAI_TANKA_VIEW");
	}

	/**
	 * 販売単価をレコードに格納します。
	 *
	 * @param hanbaiTankaView 販売単価
	 */
	public void setHanbaiTankaView(String hanbaiTankaView) {
		put("HANBAI_TANKA_VIEW", hanbaiTankaView);
	}


	/**
	 * 販売額を返します。
	 *
	 * @return 販売額
	 */
	public String getHanbaiGakuView() {
		return (String) get("HANBAI_GAKU_VIEW");
	}

	/**
	 * 販売額をレコードに格納します。
	 *
	 * @param hanbaiTanka 販売額
	 */
	public void setHanbaiGakuView(String hanbaiGakuView) {
		put("HANBAI_GAKU_VIEW", hanbaiGakuView);
	}

	/**
	 * 商品名を返します。
	 *
	 * @return 商品名
	 */
	public String getShohinNmView() {
		return (String) get("SHOHIN_NM_VIEW");
	}

	/**
	 * 商品名をレコードに格納します。
	 *
	 * @param shohinNmView 商品名
	 */
	public void setShohinNmView(String shohinNmView) {
		put("SHOHIN_NM_VIEW", shohinNmView);
	}


	/**
	 * 売伝用 ｾｯﾄ記号/容量名(1)を返します。
	 *
	 * @return 売伝用 ｾｯﾄ記号/容量名(1)
	 */
	public String getYoukiKigoNmView() {
		return (String) get("YOUKI_KIGO_NM_VIEW");
	}

	/**
	 * 売伝用 ｾｯﾄ記号/容量名(1)をレコードに格納します。
	 *
	 * @param youkiKigoNm1View 売伝用 ｾｯﾄ記号/容量名(1)
	 */
	public void setYoukiKigoNmView(String youkiKigoNm1View) {
		put("YOUKI_KIGO_NM_VIEW", youkiKigoNm1View);
	}

	/**
	 * 黄桜出荷伝票NOを返します。
	 *
	 * @return 黄桜出荷伝票NO
	 */
	public String getSyukadenNoView() {
		return (String) get("SYUKADEN_NO_VIEW");
	}

	/**
	 * 黄桜出荷伝票NOをレコードに格納します。
	 *
	 * @param syukadenNo 黄桜出荷伝票NO
	 */
	public void setSyukadenNoView(String syukadenNoView) {
		put("SYUKADEN_NO_VIEW", syukadenNoView);
	}

	/**
	 * 訂正時訂正元売上伝票NOを返します。
	 *
	 * @return ケース入数
	 */
	public String getTeiseiUridenNoView() {
		return (String) get("TEISEI_URIDEN_NO_VIEW");
	}

	/**
	 * 訂正時訂正元売上伝票NOをレコードに格納します。
	 *
	 * @param teiseiUridenNo 訂正時訂正元売上伝票NO
	 */
	public void setTeiseiUridenNoView(String teiseiUridenNoView) {
		put("TEISEI_URIDEN_NO_VIEW", teiseiUridenNoView);
	}

	/**
	 *訂正時訂正元出荷日を返します。
	 *
	 * @return 訂正時訂正元出荷日
	 */
	public String getTeiseiSyukaDtView() {
		return (String) get("TEISEI_SYUKA_DT_VIEW");
	}

	/**
	 * 訂正時訂正元出荷日をレコードに格納します。
	 *
	 * @param teiseiSyukaDt 訂正時訂正元出荷日
	 */
	public void setTeiseiSyukaDtView(String teiseiSyukaDtView) {
		put("TEISEI_SYUKA_DT_VIEW", teiseiSyukaDtView);
	}
}	// -- class
