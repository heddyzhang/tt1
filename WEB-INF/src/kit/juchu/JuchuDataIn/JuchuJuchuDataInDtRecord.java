package kit.juchu.JuchuDataIn;

import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.CHAR_BLANK;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 受注入力（受注データ／ディテール部）のレコードクラス
 */
public class JuchuJuchuDataInDtRecord extends KitRecord {

	/** シリアルID */
	private static final long serialVersionUID = 3845746008250164827L;

	/** デバッグ */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public JuchuJuchuDataInDtRecord() {
		super();
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input Map
	 */
	public JuchuJuchuDataInDtRecord(Map<String, Object> input) {
		super(input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input HashMap
	 */
	public JuchuJuchuDataInDtRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input JuchuJuchuDataInDtRecord
	 */
	public JuchuJuchuDataInDtRecord(JuchuJuchuDataInDtRecord input) {
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
		JuchuJuchuDataInDtRecord newRec = (JuchuJuchuDataInDtRecord) reco;
		// 追加モード
		if (isAddMode()) {
			setRiyouKbn(newRec.getRiyouKbn()); // 利用区分
			setKaisyaCd(newRec.getKaisyaCd()); // 会社CD
			setJyucyuNo(newRec.getJyucyuNo()); // 受注NO
			setInputLineNo(newRec.getInputLineNo()); // 受注行NO
			setShohinCd(newRec.getShohinCd()); // 商品CD
			setSyukaSuCase(newRec.getSyukaSuCase()); // 出荷数量_箱数
			setSyukaSuBara(newRec.getSyukaSuBara()); // 出荷数量_セット数
			setHanbaiTanka(newRec.getHanbaiTanka()); // 販売単価
			setKuraCd(newRec.getKuraCd()); // 蔵CD
			setSoukoCd(newRec.getSoukoCd()); // 倉庫CD
			setKtksyCd(newRec.getKtksyCd()); // 寄託者CD
			setSeihinCd(newRec.getSeihinCd()); // 製品CD
			setSiiresakiCd(newRec.getSiiresakiCd()); // 仕入先CD
			setSiirehinCd(newRec.getSiirehinCd()); // 仕入品CD
			setSeihinDt(newRec.getSeihinDt()); // 製品日付
			setTumidenLineNo(newRec.getTumidenLineNo()); // 積荷伝票用ラインNO
			setCaseIrisu(newRec.getCaseIrisu()); // ケース入数
			setBaraYouryo(newRec.getBaraYouryo()); // バラ容量(L)
			setWeightCase(newRec.getWeightCase()); // 重量(KG)_ケース
			setWeightBara(newRec.getWeightBara()); // 重量(KG)_バラ
			setUnchinKakeritu(newRec.getUnchinKakeritu()); // 運賃掛率(%)
			setSyukaAllBara(newRec.getSyukaAllBara()); // 出荷数量_換算総セット数
			setSyukaAllWeigth(newRec.getSyukaAllWeigth()); // 出荷重量（KG)
			setSyukaHanbaiKingaku(newRec.getSyukaHanbaiKingaku()); // 販売額
			setSyukaTaioKbn(newRec.getSyukaTaioKbn()); // 出荷対応区分
			setAtukaiKbn(newRec.getAtukaiKbn()); // 扱い区分
			setBupinKbn(newRec.getBupinKbn()); // 物品区分
			setHtankaChgFlg(newRec.getHtankaChgFlg()); // 販売単価変更フラグ
			setSyukaSouyouryo(newRec.getSyukaSouyouryo()); // 容量（L）_出荷総容量
			setRebate1Souyouryo(newRec.getRebate1Souyouryo()); // 容量（L）_リベートⅠ類対象総容量
			setRebate2Souyouryo(newRec.getRebate2Souyouryo()); // 容量（L）_リベートⅡ類対象総容量
			setRebate3Souyouryo(newRec.getRebate3Souyouryo()); // 容量（L）_リベートⅢ類対象総容量
			setRebate4Souyouryo(newRec.getRebate4Souyouryo()); // 容量（L）_リベートⅣ類対象総容量
			setRebate5Souyouryo(newRec.getRebate5Souyouryo()); // 容量（L）_リベートⅤ類対象総容量
			setRebateoSouyouryo(newRec.getRebateoSouyouryo()); // 容量（L）_リベート対象外総容量
			setPbTokucyuKbn(newRec.getPbTokucyuKbn()); // 特注指示区分
			setPbTokucyu(newRec.getPbTokucyu()); // PB OR 特注指示内容
			setHanbaiBumonCd(newRec.getHanbaiBumonCd()); // 販売部門CD
			setHanbaiBumonRnm(newRec.getHanbaiBumonRnm()); // 販売部門名（略式）
			setHanbaiSyubetuCd(newRec.getHanbaiSyubetuCd()); // 販売種別CD
			setHanbaiSyubetuRnm(newRec.getHanbaiSyubetuRnm()); // 販売種別名（略式）
			setHanbaiBunruiCd(newRec.getHanbaiBunruiCd()); // 販売分類CD
			setHanbaiBunruiRnm(newRec.getHanbaiBunruiRnm()); // 販売分類名（略式）

		// 変更モード（主キー項目以外）
		} else {
			setRiyouKbn(newRec.getRiyouKbn()); // 利用区分
//主キー項目			setKaisyaCd(newRec.getKaisyaCd()); // 会社CD
//主キー項目			setJyucyuNo(newRec.getJyucyuNo()); // 受注NO
//主キー項目			setInputLineNo(newRec.getInputLineNo()); // 受注行NO
			setShohinCd(newRec.getShohinCd()); // 商品CD
			setSyukaSuCase(newRec.getSyukaSuCase()); // 出荷数量_箱数
			setSyukaSuBara(newRec.getSyukaSuBara()); // 出荷数量_セット数
			setHanbaiTanka(newRec.getHanbaiTanka()); // 販売単価
			setKuraCd(newRec.getKuraCd()); // 蔵CD
			setSoukoCd(newRec.getSoukoCd()); // 倉庫CD
			setKtksyCd(newRec.getKtksyCd()); // 寄託者CD
			setSeihinCd(newRec.getSeihinCd()); // 製品CD
			setSiiresakiCd(newRec.getSiiresakiCd()); // 仕入先CD
			setSiirehinCd(newRec.getSiirehinCd()); // 仕入品CD
			setSeihinDt(newRec.getSeihinDt()); // 製品日付
			setTumidenLineNo(newRec.getTumidenLineNo()); // 積荷伝票用ラインNO
			setCaseIrisu(newRec.getCaseIrisu()); // ケース入数
			setBaraYouryo(newRec.getBaraYouryo()); // バラ容量(L)
			setWeightCase(newRec.getWeightCase()); // 重量(KG)_ケース
			setWeightBara(newRec.getWeightBara()); // 重量(KG)_バラ
			setUnchinKakeritu(newRec.getUnchinKakeritu()); // 運賃掛率(%)
			setSyukaAllBara(newRec.getSyukaAllBara()); // 出荷数量_換算総セット数
			setSyukaAllWeigth(newRec.getSyukaAllWeigth()); // 出荷重量（KG)
			setSyukaHanbaiKingaku(newRec.getSyukaHanbaiKingaku()); // 販売額
			setSyukaTaioKbn(newRec.getSyukaTaioKbn()); // 出荷対応区分
			setAtukaiKbn(newRec.getAtukaiKbn()); // 扱い区分
			setBupinKbn(newRec.getBupinKbn()); // 物品区分
			setHtankaChgFlg(newRec.getHtankaChgFlg()); // 販売単価変更フラグ
			setSyukaSouyouryo(newRec.getSyukaSouyouryo()); // 容量（L）_出荷総容量
			setRebate1Souyouryo(newRec.getRebate1Souyouryo()); // 容量（L）_リベートⅠ類対象総容量
			setRebate2Souyouryo(newRec.getRebate2Souyouryo()); // 容量（L）_リベートⅡ類対象総容量
			setRebate3Souyouryo(newRec.getRebate3Souyouryo()); // 容量（L）_リベートⅢ類対象総容量
			setRebate4Souyouryo(newRec.getRebate4Souyouryo()); // 容量（L）_リベートⅣ類対象総容量
			setRebate5Souyouryo(newRec.getRebate5Souyouryo()); // 容量（L）_リベートⅤ類対象総容量
			setRebateoSouyouryo(newRec.getRebateoSouyouryo()); // 容量（L）_リベート対象外総容量
			setPbTokucyuKbn(newRec.getPbTokucyuKbn()); // 特注指示区分
			setPbTokucyu(newRec.getPbTokucyu()); // PB OR 特注指示内容
			setHanbaiBumonCd(newRec.getHanbaiBumonCd()); // 販売部門CD
			setHanbaiBumonRnm(newRec.getHanbaiBumonRnm()); // 販売部門名（略式）
			setHanbaiSyubetuCd(newRec.getHanbaiSyubetuCd()); // 販売種別CD
			setHanbaiSyubetuRnm(newRec.getHanbaiSyubetuRnm()); // 販売種別名（略式）
			setHanbaiBunruiCd(newRec.getHanbaiBunruiCd()); // 販売分類CD
			setHanbaiBunruiRnm(newRec.getHanbaiBunruiRnm()); // 販売分類名（略式）

		}
	}

	/**
	 * 空白かどうか判定
	 *
	 * @return true
	 */
	public boolean isEmpty() {
		boolean bEmpty = true;
		// 追加モードの場合（入力項目）
		if (this.isAddMode()) {
			// 利用区分
			if (!PbsUtil.isEmpty(getRiyouKbn())) {
				bEmpty = false;
			}

			// 商品CD
			if (!PbsUtil.isEmpty(getShohinCd())) {
				bEmpty = false;
			}

			// 出荷数量_箱数
			if (!PbsUtil.isEmpty(getSyukaSuCase())) {
				bEmpty = false;
			}

			// 出荷数量_セット数
			if (!PbsUtil.isEmpty(getSyukaSuBara())) {
				bEmpty = false;
			}

			// 販売単価
			if (!PbsUtil.isEmpty(getHanbaiTanka())) {
				bEmpty = false;
			}

		// 変更モードの場合（UPDATEをDELETE/INSERTで行うための既存データチェックで使用）
		} else {
			// 商品CD
			if (!PbsUtil.isEmpty(getShohinCd())) {
				bEmpty = false;
			}

			// 出荷数量_箱数
			if (!PbsUtil.isEmpty(getSyukaSuCase())) {
				bEmpty = false;
			}

			// 出荷数量_セット数
			if (!PbsUtil.isEmpty(getSyukaSuBara())) {
				bEmpty = false;
			}

			// 販売単価
			if (!PbsUtil.isEmpty(getHanbaiTanka())) {
				bEmpty = false;
			}

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
		JuchuJuchuDataInDtRecord target = (JuchuJuchuDataInDtRecord) reco;
		// 追加モードの場合（入力項目）
		if (this.isAddMode()) {
			// 商品CD
			if (!PbsUtil.isEqual(getShohinCd(), target.getShohinCd())) {
				bEquals = false;
			}

			// 出荷数量_箱数
			if (!PbsUtil.isEqual(getSyukaSuCase(), target.getSyukaSuCase())) {
				bEquals = false;
			}

			// 出荷数量_セット数
			if (!PbsUtil.isEqual(getSyukaSuBara(), target.getSyukaSuBara())) {
				bEquals = false;
			}

			// 販売単価
			if (!PbsUtil.isEqualForDec(getHanbaiTanka(), target.getHanbaiTanka())) {
				bEquals = false;
			}

		// 変更モードの場合（主キー項目以外）
		} else {
			// 商品CD（行全体）
			if (!PbsUtil.isEqual(getShohinCd(), target.getShohinCd())) {
				setInputLineNoClass(STYLE_CLASS_MODIFIED);
				setShohinCdClass(STYLE_CLASS_MODIFIED);
				setShohinNmClass(STYLE_CLASS_MODIFIED);
				setTnpnVolMlClass(STYLE_CLASS_MODIFIED);
				setCaseIrisuClass(STYLE_CLASS_MODIFIED);
				setAtukaiKbnClass(STYLE_CLASS_MODIFIED);
				setSyukaSuCaseClass(STYLE_CLASS_MODIFIED);
				setSyukaSuBaraClass(STYLE_CLASS_MODIFIED);
				setHanbaiTankaClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else {
				setInputLineNoClass(STYLE_CLASS_NO_EDIT);
				setShohinCdClass(STYLE_CLASS_NO_EDIT);
				setShohinNmClass(STYLE_CLASS_NO_EDIT);
				setTnpnVolMlClass(STYLE_CLASS_NO_EDIT);
				setCaseIrisuClass(STYLE_CLASS_NO_EDIT);
				setAtukaiKbnClass(STYLE_CLASS_NO_EDIT);
				setSyukaSuCaseClass(STYLE_CLASS_NO_EDIT);
				setSyukaSuBaraClass(STYLE_CLASS_NO_EDIT);
				setHanbaiTankaClass(STYLE_CLASS_NO_EDIT);

				// 出荷数量_箱数
				if (!PbsUtil.isEqual(getSyukaSuCase(), target.getSyukaSuCase())) {
					setSyukaSuCaseClass(STYLE_CLASS_MODIFIED);
					bEquals = false;
				} else if (!getModified()) {
					setSyukaSuCaseClass(STYLE_CLASS_NO_EDIT);
				}

				// 出荷数量_セット数
				if (!PbsUtil.isEqual(getSyukaSuBara(), target.getSyukaSuBara())) {
					setSyukaSuBaraClass(STYLE_CLASS_MODIFIED);
					bEquals = false;
				} else if (!getModified()) {
					setSyukaSuBaraClass(STYLE_CLASS_NO_EDIT);
				}

				// 販売単価
				if (!PbsUtil.isEqualForDec(getHanbaiTanka(), target.getHanbaiTanka())) {
					setHanbaiTankaClass(STYLE_CLASS_MODIFIED);
					bEquals = false;
				} else if (!getModified()) {
					setHanbaiTankaClass(STYLE_CLASS_NO_EDIT);
				}
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
			,"JYUCYU_NO_CLASS"
			,"INPUT_LINE_NO_CLASS"
			,"SHOHIN_CD_CLASS"
			,"SHOHIN_NM_CLASS"
			,"SYUKA_SU_CASE_CLASS"
			,"SYUKA_SU_BARA_CLASS"
			,"HANBAI_TANKA_CLASS"
			,"KURA_CD_CLASS"
			,"SOUKO_CD_CLASS"
			,"KTKSY_CD_CLASS"
			,"SEIHIN_CD_CLASS"
			,"SIIRESAKI_CD_CLASS"
			,"SIIREHIN_CD_CLASS"
			,"SEIHIN_DT_CLASS"
			,"TUMIDEN_LINE_NO_CLASS"
			,"CASE_IRISU_CLASS"
			,"BARA_YOURYO_CLASS"
			,"WEIGHT_CASE_CLASS"
			,"WEIGHT_BARA_CLASS"
			,"UNCHIN_KAKERITU_CLASS"
			,"SYUKA_ALL_BARA_CLASS"
			,"SYUKA_ALL_WEIGTH_CLASS"
			,"SYUKA_HANBAI_KINGAKU_CLASS"
			,"SYUKA_TAIO_KBN_CLASS"
			,"ATUKAI_KBN_CLASS"
			,"BUPIN_KBN_CLASS"
			,"HTANKA_CHG_FLG_CLASS"
			,"SYUKA_SOUYOURYO_CLASS"
			,"REBATE1_SOUYOURYO_CLASS"
			,"REBATE2_SOUYOURYO_CLASS"
			,"REBATE3_SOUYOURYO_CLASS"
			,"REBATE4_SOUYOURYO_CLASS"
			,"REBATE5_SOUYOURYO_CLASS"
			,"REBATEO_SOUYOURYO_CLASS"
			,"PB_TOKUCYU_KBN_CLASS"
			,"PB_TOKUCYU_CLASS"
			,"HANBAI_BUMON_CD_CLASS"
			,"HANBAI_BUMON_RNM_CLASS"
			,"HANBAI_SYUBETU_CD_CLASS"
			,"HANBAI_SYUBETU_RNM_CLASS"
			,"HANBAI_BUNRUI_CD_CLASS"
			,"HANBAI_BUNRUI_RNM_CLASS"
			,"BIKOU_1_TX_CLASS"
			,"BIKOU_2_TX_CLASS"
			,"BIKOU_3_TX_CLASS"
			,"BIKOU_4_TX_CLASS"
			,"BIKOU_5_TX_CLASS"
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
	public boolean isDuplicated(JuchuJuchuDataInDtRecord rec){
		boolean ret = true;

		// 会社CD + 受注NO + 受注行NO
		if (!PbsUtil.isEqualIgnoreZero(getKaisyaCd()+getJyucyuNo()+getInputLineNo(), rec.getKaisyaCd()+rec.getJyucyuNo()+rec.getInputLineNo())) {
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
			,"JYUCYU_NO_CLASS"
			,"INPUT_LINE_NO_CLASS"
			,"SHOHIN_CD_CLASS"
			,"SHOHIN_NM_CLASS"
			,"SYUKA_SU_CASE_CLASS"
			,"SYUKA_SU_BARA_CLASS"
			,"HANBAI_TANKA_CLASS"
			,"KURA_CD_CLASS"
			,"SOUKO_CD_CLASS"
			,"KTKSY_CD_CLASS"
			,"SEIHIN_CD_CLASS"
			,"SIIRESAKI_CD_CLASS"
			,"SIIREHIN_CD_CLASS"
			,"SEIHIN_DT_CLASS"
			,"TUMIDEN_LINE_NO_CLASS"
			,"CASE_IRISU_CLASS"
			,"BARA_YOURYO_CLASS"
			,"WEIGHT_CASE_CLASS"
			,"WEIGHT_BARA_CLASS"
			,"UNCHIN_KAKERITU_CLASS"
			,"SYUKA_ALL_BARA_CLASS"
			,"SYUKA_ALL_WEIGTH_CLASS"
			,"SYUKA_HANBAI_KINGAKU_CLASS"
			,"SYUKA_TAIO_KBN_CLASS"
			,"ATUKAI_KBN_CLASS"
			,"BUPIN_KBN_CLASS"
			,"HTANKA_CHG_FLG_CLASS"
			,"SYUKA_SOUYOURYO_CLASS"
			,"REBATE1_SOUYOURYO_CLASS"
			,"REBATE2_SOUYOURYO_CLASS"
			,"REBATE3_SOUYOURYO_CLASS"
			,"REBATE4_SOUYOURYO_CLASS"
			,"REBATE5_SOUYOURYO_CLASS"
			,"REBATEO_SOUYOURYO_CLASS"
			,"PB_TOKUCYU_KBN_CLASS"
			,"PB_TOKUCYU_CLASS"
			,"HANBAI_BUMON_CD_CLASS"
			,"HANBAI_BUMON_RNM_CLASS"
			,"HANBAI_SYUBETU_CD_CLASS"
			,"HANBAI_SYUBETU_RNM_CLASS"
			,"HANBAI_BUNRUI_CD_CLASS"
			,"HANBAI_BUNRUI_RNM_CLASS"
			,"BIKOU_1_TX_CLASS"
			,"BIKOU_2_TX_CLASS"
			,"BIKOU_3_TX_CLASS"
			,"BIKOU_4_TX_CLASS"
			,"BIKOU_5_TX_CLASS"
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
		// 会社CD、受注NO、受注行NO
		super.setPrimaryKeys("KAISYA_CD", "JYUCYU_NO", "INPUT_LINE_NO");
	}

	/**
	 * 入力値と名称をリセット
	 */
	public void reset() {
		this.modifyRecord(new JuchuJuchuDataInDtRecord());
		this.setShohinCd(CHAR_BLANK); // 商品CD
		this.setShohinNm(CHAR_BLANK); // 商品名
		this.setTnpnVolMl(CHAR_BLANK); // 容量(ml)
		this.setCaseIrisu(CHAR_BLANK); // 入数
		this.setAtukaiKbn(CHAR_BLANK); // 扱い区分
		this.setSyukaSuCase(CHAR_BLANK); // 出荷数量_箱数
		this.setSyukaSuBara(CHAR_BLANK); // 出荷数量_セット数
		this.setHanbaiTanka(CHAR_BLANK); // 単価
		this.setZaikoZansuCase(CHAR_BLANK); // 在庫残数_箱数
		this.setZaikoZansuBara(CHAR_BLANK); // 在庫残数_セット数
		this.setZaikoZansu(CHAR_BLANK); // 在庫残数
		this.setZaikoGetDtTm(CHAR_BLANK); // 在庫情報取得日時
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
	 * 蔵CDを返します。
	 *
	 * @return 蔵CD
	 */
	public String getKuraCd() {
		return (String) get("KURA_CD");
	}

	/**
	 * 蔵CDをレコードに格納します。
	 *
	 * @param kuraCd 蔵CD
	 */
	public void setKuraCd(String kuraCd) {
		put("KURA_CD", kuraCd);
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
	 * 仕入先CDを返します。
	 *
	 * @return 仕入先CD
	 */
	public String getSiiresakiCd() {
		return (String) get("SIIRESAKI_CD");
	}

	/**
	 * 仕入先CDをレコードに格納します。
	 *
	 * @param siiresakiCd 仕入先CD
	 */
	public void setSiiresakiCd(String siiresakiCd) {
		put("SIIRESAKI_CD", siiresakiCd);
	}


	/**
	 * 仕入品CDを返します。
	 *
	 * @return 仕入品CD
	 */
	public String getSiirehinCd() {
		return (String) get("SIIREHIN_CD");
	}

	/**
	 * 仕入品CDをレコードに格納します。
	 *
	 * @param siirehinCd 仕入品CD
	 */
	public void setSiirehinCd(String siirehinCd) {
		put("SIIREHIN_CD", siirehinCd);
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
	 * バラ容量(L)を返します。
	 *
	 * @return バラ容量(L)
	 */
	public String getBaraYouryo() {
		return (String) get("BARA_YOURYO");
	}

	/**
	 * バラ容量(L)をレコードに格納します。
	 *
	 * @param baraYouryo バラ容量(L)
	 */
	public void setBaraYouryo(String baraYouryo) {
		put("BARA_YOURYO", baraYouryo);
	}


	/**
	 * 重量(KG)_ケースを返します。
	 *
	 * @return 重量(KG)_ケース
	 */
	public String getWeightCase() {
		return (String) get("WEIGHT_CASE");
	}

	/**
	 * 重量(KG)_ケースをレコードに格納します。
	 *
	 * @param weightCase 重量(KG)_ケース
	 */
	public void setWeightCase(String weightCase) {
		put("WEIGHT_CASE", weightCase);
	}


	/**
	 * 重量(KG)_バラを返します。
	 *
	 * @return 重量(KG)_バラ
	 */
	public String getWeightBara() {
		return (String) get("WEIGHT_BARA");
	}

	/**
	 * 重量(KG)_バラをレコードに格納します。
	 *
	 * @param weightBara 重量(KG)_バラ
	 */
	public void setWeightBara(String weightBara) {
		put("WEIGHT_BARA", weightBara);
	}


	/**
	 * 運賃掛率(%)を返します。
	 *
	 * @return 運賃掛率(%)
	 */
	public String getUnchinKakeritu() {
		return (String) get("UNCHIN_KAKERITU");
	}

	/**
	 * 運賃掛率(%)をレコードに格納します。
	 *
	 * @param unchinKakeritu 運賃掛率(%)
	 */
	public void setUnchinKakeritu(String unchinKakeritu) {
		put("UNCHIN_KAKERITU", unchinKakeritu);
	}


	/**
	 * 出荷数量_換算総セット数を返します。
	 *
	 * @return 出荷数量_換算総セット数
	 */
	public String getSyukaAllBara() {
		return (String) get("SYUKA_ALL_BARA");
	}

	/**
	 * 出荷数量_換算総セット数をレコードに格納します。
	 *
	 * @param syukaAllBara 出荷数量_換算総セット数
	 */
	public void setSyukaAllBara(String syukaAllBara) {
		put("SYUKA_ALL_BARA", syukaAllBara);
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


	/**
	 * 販売額を返します。
	 *
	 * @return 販売額
	 */
	public String getSyukaHanbaiKingaku() {
		return (String) get("SYUKA_HANBAI_KINGAKU");
	}

	/**
	 * 販売額をレコードに格納します。
	 *
	 * @param syukaHanbaiKingaku 販売額
	 */
	public void setSyukaHanbaiKingaku(String syukaHanbaiKingaku) {
		put("SYUKA_HANBAI_KINGAKU", syukaHanbaiKingaku);
	}


	/**
	 * 出荷対応区分を返します。
	 *
	 * @return 出荷対応区分
	 */
	public String getSyukaTaioKbn() {
		return (String) get("SYUKA_TAIO_KBN");
	}

	/**
	 * 出荷対応区分をレコードに格納します。
	 *
	 * @param syukaTaioKbn 出荷対応区分
	 */
	public void setSyukaTaioKbn(String syukaTaioKbn) {
		put("SYUKA_TAIO_KBN", syukaTaioKbn);
	}


	/**
	 * 扱い区分を返します。
	 *
	 * @return 扱い区分
	 */
	public String getAtukaiKbn() {
		return (String) get("ATUKAI_KBN");
	}

	/**
	 * 扱い区分をレコードに格納します。
	 *
	 * @param atukaiKbn 扱い区分
	 */
	public void setAtukaiKbn(String atukaiKbn) {
		put("ATUKAI_KBN", atukaiKbn);
	}


	/**
	 * 物品区分を返します。
	 *
	 * @return 物品区分
	 */
	public String getBupinKbn() {
		return (String) get("BUPIN_KBN");
	}

	/**
	 * 物品区分をレコードに格納します。
	 *
	 * @param bupinKbn 物品区分
	 */
	public void setBupinKbn(String bupinKbn) {
		put("BUPIN_KBN", bupinKbn);
	}


	/**
	 * 販売単価変更フラグを返します。
	 *
	 * @return 販売単価変更フラグ
	 */
	public String getHtankaChgFlg() {
		return (String) get("HTANKA_CHG_FLG");
	}

	/**
	 * 販売単価変更フラグをレコードに格納します。
	 *
	 * @param htankaChgFlg 販売単価変更フラグ
	 */
	public void setHtankaChgFlg(String htankaChgFlg) {
		put("HTANKA_CHG_FLG", htankaChgFlg);
	}


	/**
	 * 容量（L）_出荷総容量を返します。
	 *
	 * @return 容量（L）_出荷総容量
	 */
	public String getSyukaSouyouryo() {
		return (String) get("SYUKA_SOUYOURYO");
	}

	/**
	 * 容量（L）_出荷総容量をレコードに格納します。
	 *
	 * @param syukaSouyouryo 容量（L）_出荷総容量
	 */
	public void setSyukaSouyouryo(String syukaSouyouryo) {
		put("SYUKA_SOUYOURYO", syukaSouyouryo);
	}


	/**
	 * 容量（L）_リベートⅠ類対象総容量を返します。
	 *
	 * @return 容量（L）_リベートⅠ類対象総容量
	 */
	public String getRebate1Souyouryo() {
		return (String) get("REBATE1_SOUYOURYO");
	}

	/**
	 * 容量（L）_リベートⅠ類対象総容量をレコードに格納します。
	 *
	 * @param rebate1Souyouryo 容量（L）_リベートⅠ類対象総容量
	 */
	public void setRebate1Souyouryo(String rebate1Souyouryo) {
		put("REBATE1_SOUYOURYO", rebate1Souyouryo);
	}


	/**
	 * 容量（L）_リベートⅡ類対象総容量を返します。
	 *
	 * @return 容量（L）_リベートⅡ類対象総容量
	 */
	public String getRebate2Souyouryo() {
		return (String) get("REBATE2_SOUYOURYO");
	}

	/**
	 * 容量（L）_リベートⅡ類対象総容量をレコードに格納します。
	 *
	 * @param rebate2Souyouryo 容量（L）_リベートⅡ類対象総容量
	 */
	public void setRebate2Souyouryo(String rebate2Souyouryo) {
		put("REBATE2_SOUYOURYO", rebate2Souyouryo);
	}


	/**
	 * 容量（L）_リベートⅢ類対象総容量を返します。
	 *
	 * @return 容量（L）_リベートⅢ類対象総容量
	 */
	public String getRebate3Souyouryo() {
		return (String) get("REBATE3_SOUYOURYO");
	}

	/**
	 * 容量（L）_リベートⅢ類対象総容量をレコードに格納します。
	 *
	 * @param rebate3Souyouryo 容量（L）_リベートⅢ類対象総容量
	 */
	public void setRebate3Souyouryo(String rebate3Souyouryo) {
		put("REBATE3_SOUYOURYO", rebate3Souyouryo);
	}


	/**
	 * 容量（L）_リベートⅣ類対象総容量を返します。
	 *
	 * @return 容量（L）_リベートⅣ類対象総容量
	 */
	public String getRebate4Souyouryo() {
		return (String) get("REBATE4_SOUYOURYO");
	}

	/**
	 * 容量（L）_リベートⅣ類対象総容量をレコードに格納します。
	 *
	 * @param rebate4Souyouryo 容量（L）_リベートⅣ類対象総容量
	 */
	public void setRebate4Souyouryo(String rebate4Souyouryo) {
		put("REBATE4_SOUYOURYO", rebate4Souyouryo);
	}


	/**
	 * 容量（L）_リベートⅤ類対象総容量を返します。
	 *
	 * @return 容量（L）_リベートⅤ類対象総容量
	 */
	public String getRebate5Souyouryo() {
		return (String) get("REBATE5_SOUYOURYO");
	}

	/**
	 * 容量（L）_リベートⅤ類対象総容量をレコードに格納します。
	 *
	 * @param rebate5Souyouryo 容量（L）_リベートⅤ類対象総容量
	 */
	public void setRebate5Souyouryo(String rebate5Souyouryo) {
		put("REBATE5_SOUYOURYO", rebate5Souyouryo);
	}


	/**
	 * 容量（L）_リベート対象外総容量を返します。
	 *
	 * @return 容量（L）_リベート対象外総容量
	 */
	public String getRebateoSouyouryo() {
		return (String) get("REBATEO_SOUYOURYO");
	}

	/**
	 * 容量（L）_リベート対象外総容量をレコードに格納します。
	 *
	 * @param rebateoSouyouryo 容量（L）_リベート対象外総容量
	 */
	public void setRebateoSouyouryo(String rebateoSouyouryo) {
		put("REBATEO_SOUYOURYO", rebateoSouyouryo);
	}


	/**
	 * 特注指示区分を返します。
	 *
	 * @return 特注指示区分
	 */
	public String getPbTokucyuKbn() {
		return (String) get("PB_TOKUCYU_KBN");
	}

	/**
	 * 特注指示区分をレコードに格納します。
	 *
	 * @param pbTokucyuKbn 特注指示区分
	 */
	public void setPbTokucyuKbn(String pbTokucyuKbn) {
		put("PB_TOKUCYU_KBN", pbTokucyuKbn);
	}


	/**
	 * PB OR 特注指示内容を返します。
	 *
	 * @return PB OR 特注指示内容
	 */
	public String getPbTokucyu() {
		return (String) get("PB_TOKUCYU");
	}

	/**
	 * PB OR 特注指示内容をレコードに格納します。
	 *
	 * @param pbTokucyu PB OR 特注指示内容
	 */
	public void setPbTokucyu(String pbTokucyu) {
		put("PB_TOKUCYU", pbTokucyu);
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
	 * SDN受注ディテールエラー区分を返します。
	 *
	 * @return SDN受注ディテールエラー区分
	 */
	public String getSdnDterrKbn() {
		return (String) get("SDN_DTERR_KBN");
	}

	/**
	 * SDN受注ディテールエラー区分をレコードに格納します。
	 *
	 * @param sdnDterrKbn SDN受注ディテールエラー区分
	 */
	public void setSdnDterrKbn(String sdnDterrKbn) {
		put("SDN_DTERR_KBN", sdnDterrKbn);
	}


	/**
	 * EDI配送依頼(集約)送信区分を返します。
	 *
	 * @return EDI配送依頼(集約)送信区分
	 */
	public String getEdiHaisougSendKb() {
		return (String) get("EDI_HAISOUG_SEND_KB");
	}

	/**
	 * EDI配送依頼(集約)送信区分をレコードに格納します。
	 *
	 * @param ediHaisougSendKb EDI配送依頼(集約)送信区分
	 */
	public void setEdiHaisougSendKb(String ediHaisougSendKb) {
		put("EDI_HAISOUG_SEND_KB", ediHaisougSendKb);
	}


	/**
	 * 倉庫CDを返します。
	 *
	 * @return 倉庫CD
	 */
	public String getSoukoCd() {
		return (String) get("SOUKO_CD");
	}

	/**
	 * 倉庫CDをレコードに格納します。
	 *
	 * @param soukoCd 倉庫CD
	 */
	public void setSoukoCd(String soukoCd) {
		put("SOUKO_CD", soukoCd);
	}


	/**
	 * 製品日付を返します。
	 *
	 * @return 製品日付
	 */
	public String getSeihinDt() {
		return (String) get("SEIHIN_DT");
	}

	/**
	 * 製品日付をレコードに格納します。
	 *
	 * @param seihinDt 製品日付
	 */
	public void setSeihinDt(String seihinDt) {
		put("SEIHIN_DT", seihinDt);
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
	 * 補足欄を返します。
	 *
	 * @return 補足欄
	 */
	public String getHosokuran() {
		return (String) get("HOSOKURAN");
	}

	/**
	 * 補足欄をレコードに格納します。
	 *
	 * @param hosokuran 補足欄
	 */
	public void setHosokuran(String hosokuran) {
		put("HOSOKURAN", hosokuran);
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
	 * 在庫残数_箱数を返します。
	 *
	 * @return 在庫残数_箱数
	 */
	public String getZaikoZansuCase() {
		return (String) get("ZAIKO_ZANSU_CASE");
	}

	/**
	 * 在庫残数_箱数をレコードに格納します。
	 *
	 * @param zaikoZansuCase 在庫残数_箱数
	 */
	public void setZaikoZansuCase(String zaikoZansuCase) {
		put("ZAIKO_ZANSU_CASE", zaikoZansuCase);
	}


	/**
	 * 在庫残数_セット数を返します。
	 *
	 * @return 在庫残数_セット数
	 */
	public String getZaikoZansuBara() {
		return (String) get("ZAIKO_ZANSU_BARA");
	}

	/**
	 * 在庫残数_セット数をレコードに格納します。
	 *
	 * @param zaikoZansuBara 在庫残数_セット数
	 */
	public void setZaikoZansuBara(String zaikoZansuBara) {
		put("ZAIKO_ZANSU_BARA", zaikoZansuBara);
	}


	/**
	 * 単品総容量(ML)@1バラ当りを返します。
	 *
	 * @return 単品総容量(ML)@1バラ当り
	 */
	public String getTnpnVolMl() {
		return (String) get("TNPN_VOL_ML");
	}

	/**
	 * 単品総容量(ML)@1バラ当りをレコードに格納します。
	 *
	 * @param tnpnVolMl 単品総容量(ML)@1バラ当り
	 */
	public void setTnpnVolMl(String tnpnVolMl) {
		put("TNPN_VOL_ML", tnpnVolMl);
	}


	/**
	 * 出荷対応名を返します。
	 *
	 * @return 出荷対応名
	 */
	public String getSyukaTaioNm() {
		return (String) get("SYUKA_TAIO_NM");
	}

	/**
	 * 出荷対応名をレコードに格納します。
	 *
	 * @param syukaTaioNm 出荷対応名
	 */
	public void setSyukaTaioNm(String syukaTaioNm) {
		put("SYUKA_TAIO_NM", syukaTaioNm);
	}


	/**
	 * 扱い区分名を返します。
	 *
	 * @return 扱い区分名
	 */
	public String getAtukaiKbnNm() {
		return (String) get("ATUKAI_KBN_NM");
	}

	/**
	 * 扱い区分名をレコードに格納します。
	 *
	 * @param atukaiKbnNm 扱い区分名
	 */
	public void setAtukaiKbnNm(String atukaiKbnNm) {
		put("ATUKAI_KBN_NM", atukaiKbnNm);
	}


	/**
	 * 在庫残数を返します。
	 *
	 * @return 在庫残数
	 */
	public String getZaikoZansu() {
		return (String) get("ZAIKO_ZANSU");
	}

	/**
	 * 在庫残数をレコードに格納します。
	 *
	 * @param zaikoZansu 在庫残数
	 */
	public void setZaikoZansu(String zaikoZansu) {
		put("ZAIKO_ZANSU", zaikoZansu);
	}


	/**
	 * 在庫情報取得日時を返します。
	 *
	 * @return 在庫情報取得日時
	 */
	public String getZaikoGetDtTm() {
		return (String) get("ZAIKO_GET_DT_TM");
	}

	/**
	 * 在庫情報取得日時をレコードに格納します。
	 *
	 * @param zaikoGetDtTm 在庫情報取得日時
	 */
	public void setZaikoGetDtTm(String zaikoGetDtTm) {
		put("ZAIKO_GET_DT_TM", zaikoGetDtTm);
	}


	/**
	 * 商品名称_自社各帳票用(1)を返します。
	 *
	 * @return 商品名称_自社各帳票用(1)
	 */
	public String getShnnmReport1() {
		return (String) get("SHNNM_REPORT1");
	}

	/**
	 * 商品名称_自社各帳票用(1)をレコードに格納します。
	 *
	 * @param shnnmReport1 商品名称_自社各帳票用(1)
	 */
	public void setShnnmReport1(String shnnmReport1) {
		put("SHNNM_REPORT1", shnnmReport1);
	}


	/**
	 * 容器名称_自社各帳票用を返します。
	 *
	 * @return 容器名称_自社各帳票用
	 */
	public String getYoukiNmReport() {
		return (String) get("YOUKI_NM_REPORT");
	}

	/**
	 * 容器名称_自社各帳票用をレコードに格納します。
	 *
	 * @param youkiNmReport 容器名称_自社各帳票用
	 */
	public void setYoukiNmReport(String youkiNmReport) {
		put("YOUKI_NM_REPORT", youkiNmReport);
	}


	/**
	 * 補償金徴収対象区分を返します。
	 *
	 * @return 補償金徴収対象区分
	 */
	public String getIndemnityKbn() {
		return (String) get("INDEMNITY_KBN");
	}

	/**
	 * 補償金徴収対象区分をレコードに格納します。
	 *
	 * @param indemnityKbn 補償金徴収対象区分
	 */
	public void setIndemnityKbn(String indemnityKbn) {
		put("INDEMNITY_KBN", indemnityKbn);
	}


	/**
	 * 引上手数料対象区分を返します。
	 *
	 * @return 引上手数料対象区分
	 */
	public String getServiceKbn() {
		return (String) get("SERVICE_KBN");
	}

	/**
	 * 引上手数料対象区分をレコードに格納します。
	 *
	 * @param serviceKbn 引上手数料対象区分
	 */
	public void setServiceKbn(String serviceKbn) {
		put("SERVICE_KBN", serviceKbn);
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
	 * 蔵CDのエラー、警告表示フラグを返します。
	 *
	 * @return 蔵CDのエラー、警告表示フラグ
	 */
	public String getKuraCdClass() {
		return (String) get("KURA_CD_CLASS");
	}

	/**
	 * 蔵CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param kuraCdClass 蔵CDのエラー、警告表示フラグ
	 */
	public void setKuraCdClass(String kuraCdClass) {
		put("KURA_CD_CLASS", kuraCdClass);
	}


	/**
	 * 寄託者CDのエラー、警告表示フラグを返します。
	 *
	 * @return 寄託者CDのエラー、警告表示フラグ
	 */
	public String getKtksyCdClass() {
		return (String) get("KTKSY_CD_CLASS");
	}

	/**
	 * 寄託者CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param ktksyCdClass 寄託者CDのエラー、警告表示フラグ
	 */
	public void setKtksyCdClass(String ktksyCdClass) {
		put("KTKSY_CD_CLASS", ktksyCdClass);
	}


	/**
	 * 製品CDのエラー、警告表示フラグを返します。
	 *
	 * @return 製品CDのエラー、警告表示フラグ
	 */
	public String getSeihinCdClass() {
		return (String) get("SEIHIN_CD_CLASS");
	}

	/**
	 * 製品CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param seihinCdClass 製品CDのエラー、警告表示フラグ
	 */
	public void setSeihinCdClass(String seihinCdClass) {
		put("SEIHIN_CD_CLASS", seihinCdClass);
	}


	/**
	 * 仕入先CDのエラー、警告表示フラグを返します。
	 *
	 * @return 仕入先CDのエラー、警告表示フラグ
	 */
	public String getSiiresakiCdClass() {
		return (String) get("SIIRESAKI_CD_CLASS");
	}

	/**
	 * 仕入先CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param siiresakiCdClass 仕入先CDのエラー、警告表示フラグ
	 */
	public void setSiiresakiCdClass(String siiresakiCdClass) {
		put("SIIRESAKI_CD_CLASS", siiresakiCdClass);
	}


	/**
	 * 仕入品CDのエラー、警告表示フラグを返します。
	 *
	 * @return 仕入品CDのエラー、警告表示フラグ
	 */
	public String getSiirehinCdClass() {
		return (String) get("SIIREHIN_CD_CLASS");
	}

	/**
	 * 仕入品CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param siirehinCdClass 仕入品CDのエラー、警告表示フラグ
	 */
	public void setSiirehinCdClass(String siirehinCdClass) {
		put("SIIREHIN_CD_CLASS", siirehinCdClass);
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
	 * バラ容量(L)のエラー、警告表示フラグを返します。
	 *
	 * @return バラ容量(L)のエラー、警告表示フラグ
	 */
	public String getBaraYouryoClass() {
		return (String) get("BARA_YOURYO_CLASS");
	}

	/**
	 * バラ容量(L)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param baraYouryoClass バラ容量(L)のエラー、警告表示フラグ
	 */
	public void setBaraYouryoClass(String baraYouryoClass) {
		put("BARA_YOURYO_CLASS", baraYouryoClass);
	}


	/**
	 * 重量(KG)_ケースのエラー、警告表示フラグを返します。
	 *
	 * @return 重量(KG)_ケースのエラー、警告表示フラグ
	 */
	public String getWeightCaseClass() {
		return (String) get("WEIGHT_CASE_CLASS");
	}

	/**
	 * 重量(KG)_ケースのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param weightCaseClass 重量(KG)_ケースのエラー、警告表示フラグ
	 */
	public void setWeightCaseClass(String weightCaseClass) {
		put("WEIGHT_CASE_CLASS", weightCaseClass);
	}


	/**
	 * 重量(KG)_バラのエラー、警告表示フラグを返します。
	 *
	 * @return 重量(KG)_バラのエラー、警告表示フラグ
	 */
	public String getWeightBaraClass() {
		return (String) get("WEIGHT_BARA_CLASS");
	}

	/**
	 * 重量(KG)_バラのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param weightBaraClass 重量(KG)_バラのエラー、警告表示フラグ
	 */
	public void setWeightBaraClass(String weightBaraClass) {
		put("WEIGHT_BARA_CLASS", weightBaraClass);
	}


	/**
	 * 運賃掛率(%)のエラー、警告表示フラグを返します。
	 *
	 * @return 運賃掛率(%)のエラー、警告表示フラグ
	 */
	public String getUnchinKakerituClass() {
		return (String) get("UNCHIN_KAKERITU_CLASS");
	}

	/**
	 * 運賃掛率(%)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param unchinKakerituClass 運賃掛率(%)のエラー、警告表示フラグ
	 */
	public void setUnchinKakerituClass(String unchinKakerituClass) {
		put("UNCHIN_KAKERITU_CLASS", unchinKakerituClass);
	}


	/**
	 * 出荷数量_換算総セット数のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷数量_換算総セット数のエラー、警告表示フラグ
	 */
	public String getSyukaAllBaraClass() {
		return (String) get("SYUKA_ALL_BARA_CLASS");
	}

	/**
	 * 出荷数量_換算総セット数のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaAllBaraClass 出荷数量_換算総セット数のエラー、警告表示フラグ
	 */
	public void setSyukaAllBaraClass(String syukaAllBaraClass) {
		put("SYUKA_ALL_BARA_CLASS", syukaAllBaraClass);
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


	/**
	 * 販売額のエラー、警告表示フラグを返します。
	 *
	 * @return 販売額のエラー、警告表示フラグ
	 */
	public String getSyukaHanbaiKingakuClass() {
		return (String) get("SYUKA_HANBAI_KINGAKU_CLASS");
	}

	/**
	 * 販売額のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaHanbaiKingakuClass 販売額のエラー、警告表示フラグ
	 */
	public void setSyukaHanbaiKingakuClass(String syukaHanbaiKingakuClass) {
		put("SYUKA_HANBAI_KINGAKU_CLASS", syukaHanbaiKingakuClass);
	}


	/**
	 * 出荷対応区分のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷対応区分のエラー、警告表示フラグ
	 */
	public String getSyukaTaioKbnClass() {
		return (String) get("SYUKA_TAIO_KBN_CLASS");
	}

	/**
	 * 出荷対応区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaTaioKbnClass 出荷対応区分のエラー、警告表示フラグ
	 */
	public void setSyukaTaioKbnClass(String syukaTaioKbnClass) {
		put("SYUKA_TAIO_KBN_CLASS", syukaTaioKbnClass);
	}


	/**
	 * 扱い区分のエラー、警告表示フラグを返します。
	 *
	 * @return 扱い区分のエラー、警告表示フラグ
	 */
	public String getAtukaiKbnClass() {
		return (String) get("ATUKAI_KBN_CLASS");
	}

	/**
	 * 扱い区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param atukaiKbnClass 扱い区分のエラー、警告表示フラグ
	 */
	public void setAtukaiKbnClass(String atukaiKbnClass) {
		put("ATUKAI_KBN_CLASS", atukaiKbnClass);
	}


	/**
	 * 物品区分のエラー、警告表示フラグを返します。
	 *
	 * @return 物品区分のエラー、警告表示フラグ
	 */
	public String getBupinKbnClass() {
		return (String) get("BUPIN_KBN_CLASS");
	}

	/**
	 * 物品区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param bupinKbnClass 物品区分のエラー、警告表示フラグ
	 */
	public void setBupinKbnClass(String bupinKbnClass) {
		put("BUPIN_KBN_CLASS", bupinKbnClass);
	}


	/**
	 * 販売単価変更フラグのエラー、警告表示フラグを返します。
	 *
	 * @return 販売単価変更フラグのエラー、警告表示フラグ
	 */
	public String getHtankaChgFlgClass() {
		return (String) get("HTANKA_CHG_FLG_CLASS");
	}

	/**
	 * 販売単価変更フラグのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param htankaChgFlgClass 販売単価変更フラグのエラー、警告表示フラグ
	 */
	public void setHtankaChgFlgClass(String htankaChgFlgClass) {
		put("HTANKA_CHG_FLG_CLASS", htankaChgFlgClass);
	}


	/**
	 * 容量（L）_出荷総容量のエラー、警告表示フラグを返します。
	 *
	 * @return 容量（L）_出荷総容量のエラー、警告表示フラグ
	 */
	public String getSyukaSouyouryoClass() {
		return (String) get("SYUKA_SOUYOURYO_CLASS");
	}

	/**
	 * 容量（L）_出荷総容量のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaSouyouryoClass 容量（L）_出荷総容量のエラー、警告表示フラグ
	 */
	public void setSyukaSouyouryoClass(String syukaSouyouryoClass) {
		put("SYUKA_SOUYOURYO_CLASS", syukaSouyouryoClass);
	}


	/**
	 * 容量（L）_リベートⅠ類対象総容量のエラー、警告表示フラグを返します。
	 *
	 * @return 容量（L）_リベートⅠ類対象総容量のエラー、警告表示フラグ
	 */
	public String getRebate1SouyouryoClass() {
		return (String) get("REBATE1_SOUYOURYO_CLASS");
	}

	/**
	 * 容量（L）_リベートⅠ類対象総容量のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param rebate1SouyouryoClass 容量（L）_リベートⅠ類対象総容量のエラー、警告表示フラグ
	 */
	public void setRebate1SouyouryoClass(String rebate1SouyouryoClass) {
		put("REBATE1_SOUYOURYO_CLASS", rebate1SouyouryoClass);
	}


	/**
	 * 容量（L）_リベートⅡ類対象総容量のエラー、警告表示フラグを返します。
	 *
	 * @return 容量（L）_リベートⅡ類対象総容量のエラー、警告表示フラグ
	 */
	public String getRebate2SouyouryoClass() {
		return (String) get("REBATE2_SOUYOURYO_CLASS");
	}

	/**
	 * 容量（L）_リベートⅡ類対象総容量のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param rebate2SouyouryoClass 容量（L）_リベートⅡ類対象総容量のエラー、警告表示フラグ
	 */
	public void setRebate2SouyouryoClass(String rebate2SouyouryoClass) {
		put("REBATE2_SOUYOURYO_CLASS", rebate2SouyouryoClass);
	}


	/**
	 * 容量（L）_リベートⅢ類対象総容量のエラー、警告表示フラグを返します。
	 *
	 * @return 容量（L）_リベートⅢ類対象総容量のエラー、警告表示フラグ
	 */
	public String getRebate3SouyouryoClass() {
		return (String) get("REBATE3_SOUYOURYO_CLASS");
	}

	/**
	 * 容量（L）_リベートⅢ類対象総容量のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param rebate3SouyouryoClass 容量（L）_リベートⅢ類対象総容量のエラー、警告表示フラグ
	 */
	public void setRebate3SouyouryoClass(String rebate3SouyouryoClass) {
		put("REBATE3_SOUYOURYO_CLASS", rebate3SouyouryoClass);
	}


	/**
	 * 容量（L）_リベートⅣ類対象総容量のエラー、警告表示フラグを返します。
	 *
	 * @return 容量（L）_リベートⅣ類対象総容量のエラー、警告表示フラグ
	 */
	public String getRebate4SouyouryoClass() {
		return (String) get("REBATE4_SOUYOURYO_CLASS");
	}

	/**
	 * 容量（L）_リベートⅣ類対象総容量のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param rebate4SouyouryoClass 容量（L）_リベートⅣ類対象総容量のエラー、警告表示フラグ
	 */
	public void setRebate4SouyouryoClass(String rebate4SouyouryoClass) {
		put("REBATE4_SOUYOURYO_CLASS", rebate4SouyouryoClass);
	}


	/**
	 * 容量（L）_リベートⅤ類対象総容量のエラー、警告表示フラグを返します。
	 *
	 * @return 容量（L）_リベートⅤ類対象総容量のエラー、警告表示フラグ
	 */
	public String getRebate5SouyouryoClass() {
		return (String) get("REBATE5_SOUYOURYO_CLASS");
	}

	/**
	 * 容量（L）_リベートⅤ類対象総容量のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param rebate5SouyouryoClass 容量（L）_リベートⅤ類対象総容量のエラー、警告表示フラグ
	 */
	public void setRebate5SouyouryoClass(String rebate5SouyouryoClass) {
		put("REBATE5_SOUYOURYO_CLASS", rebate5SouyouryoClass);
	}


	/**
	 * 容量（L）_リベート対象外総容量のエラー、警告表示フラグを返します。
	 *
	 * @return 容量（L）_リベート対象外総容量のエラー、警告表示フラグ
	 */
	public String getRebateoSouyouryoClass() {
		return (String) get("REBATEO_SOUYOURYO_CLASS");
	}

	/**
	 * 容量（L）_リベート対象外総容量のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param rebateoSouyouryoClass 容量（L）_リベート対象外総容量のエラー、警告表示フラグ
	 */
	public void setRebateoSouyouryoClass(String rebateoSouyouryoClass) {
		put("REBATEO_SOUYOURYO_CLASS", rebateoSouyouryoClass);
	}


	/**
	 * 特注指示区分のエラー、警告表示フラグを返します。
	 *
	 * @return 特注指示区分のエラー、警告表示フラグ
	 */
	public String getPbTokucyuKbnClass() {
		return (String) get("PB_TOKUCYU_KBN_CLASS");
	}

	/**
	 * 特注指示区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param pbTokucyuKbnClass 特注指示区分のエラー、警告表示フラグ
	 */
	public void setPbTokucyuKbnClass(String pbTokucyuKbnClass) {
		put("PB_TOKUCYU_KBN_CLASS", pbTokucyuKbnClass);
	}


	/**
	 * PB OR 特注指示内容のエラー、警告表示フラグを返します。
	 *
	 * @return PB OR 特注指示内容のエラー、警告表示フラグ
	 */
	public String getPbTokucyuClass() {
		return (String) get("PB_TOKUCYU_CLASS");
	}

	/**
	 * PB OR 特注指示内容のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param pbTokucyuClass PB OR 特注指示内容のエラー、警告表示フラグ
	 */
	public void setPbTokucyuClass(String pbTokucyuClass) {
		put("PB_TOKUCYU_CLASS", pbTokucyuClass);
	}


	/**
	 * 販売部門CDのエラー、警告表示フラグを返します。
	 *
	 * @return 販売部門CDのエラー、警告表示フラグ
	 */
	public String getHanbaiBumonCdClass() {
		return (String) get("HANBAI_BUMON_CD_CLASS");
	}

	/**
	 * 販売部門CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param hanbaiBumonCdClass 販売部門CDのエラー、警告表示フラグ
	 */
	public void setHanbaiBumonCdClass(String hanbaiBumonCdClass) {
		put("HANBAI_BUMON_CD_CLASS", hanbaiBumonCdClass);
	}


	/**
	 * 販売部門名（略式）のエラー、警告表示フラグを返します。
	 *
	 * @return 販売部門名（略式）のエラー、警告表示フラグ
	 */
	public String getHanbaiBumonRnmClass() {
		return (String) get("HANBAI_BUMON_RNM_CLASS");
	}

	/**
	 * 販売部門名（略式）のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param hanbaiBumonRnmClass 販売部門名（略式）のエラー、警告表示フラグ
	 */
	public void setHanbaiBumonRnmClass(String hanbaiBumonRnmClass) {
		put("HANBAI_BUMON_RNM_CLASS", hanbaiBumonRnmClass);
	}


	/**
	 * 販売種別CDのエラー、警告表示フラグを返します。
	 *
	 * @return 販売種別CDのエラー、警告表示フラグ
	 */
	public String getHanbaiSyubetuCdClass() {
		return (String) get("HANBAI_SYUBETU_CD_CLASS");
	}

	/**
	 * 販売種別CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param hanbaiSyubetuCdClass 販売種別CDのエラー、警告表示フラグ
	 */
	public void setHanbaiSyubetuCdClass(String hanbaiSyubetuCdClass) {
		put("HANBAI_SYUBETU_CD_CLASS", hanbaiSyubetuCdClass);
	}


	/**
	 * 販売種別名（略式）のエラー、警告表示フラグを返します。
	 *
	 * @return 販売種別名（略式）のエラー、警告表示フラグ
	 */
	public String getHanbaiSyubetuRnmClass() {
		return (String) get("HANBAI_SYUBETU_RNM_CLASS");
	}

	/**
	 * 販売種別名（略式）のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param hanbaiSyubetuRnmClass 販売種別名（略式）のエラー、警告表示フラグ
	 */
	public void setHanbaiSyubetuRnmClass(String hanbaiSyubetuRnmClass) {
		put("HANBAI_SYUBETU_RNM_CLASS", hanbaiSyubetuRnmClass);
	}


	/**
	 * 販売分類CDのエラー、警告表示フラグを返します。
	 *
	 * @return 販売分類CDのエラー、警告表示フラグ
	 */
	public String getHanbaiBunruiCdClass() {
		return (String) get("HANBAI_BUNRUI_CD_CLASS");
	}

	/**
	 * 販売分類CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param hanbaiBunruiCdClass 販売分類CDのエラー、警告表示フラグ
	 */
	public void setHanbaiBunruiCdClass(String hanbaiBunruiCdClass) {
		put("HANBAI_BUNRUI_CD_CLASS", hanbaiBunruiCdClass);
	}


	/**
	 * 販売分類名（略式）のエラー、警告表示フラグを返します。
	 *
	 * @return 販売分類名（略式）のエラー、警告表示フラグ
	 */
	public String getHanbaiBunruiRnmClass() {
		return (String) get("HANBAI_BUNRUI_RNM_CLASS");
	}

	/**
	 * 販売分類名（略式）のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param hanbaiBunruiRnmClass 販売分類名（略式）のエラー、警告表示フラグ
	 */
	public void setHanbaiBunruiRnmClass(String hanbaiBunruiRnmClass) {
		put("HANBAI_BUNRUI_RNM_CLASS", hanbaiBunruiRnmClass);
	}


	/**
	 * SDN受注ディテールエラー区分のエラー、警告表示フラグを返します。
	 *
	 * @return SDN受注ディテールエラー区分のエラー、警告表示フラグ
	 */
	public String getSdnDterrKbnClass() {
		return (String) get("SDN_DTERR_KBN_CLASS");
	}

	/**
	 * SDN受注ディテールエラー区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param sdnDterrKbnClass SDN受注ディテールエラー区分のエラー、警告表示フラグ
	 */
	public void setSdnDterrKbnClass(String sdnDterrKbnClass) {
		put("SDN_DTERR_KBN_CLASS", sdnDterrKbnClass);
	}


	/**
	 * EDI配送依頼(集約)送信区分のエラー、警告表示フラグを返します。
	 *
	 * @return EDI配送依頼(集約)送信区分のエラー、警告表示フラグ
	 */
	public String getEdiHaisougSendKbClass() {
		return (String) get("EDI_HAISOUG_SEND_KB_CLASS");
	}

	/**
	 * EDI配送依頼(集約)送信区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param ediHaisougSendKbClass EDI配送依頼(集約)送信区分のエラー、警告表示フラグ
	 */
	public void setEdiHaisougSendKbClass(String ediHaisougSendKbClass) {
		put("EDI_HAISOUG_SEND_KB_CLASS", ediHaisougSendKbClass);
	}


	/**
	 * 倉庫CDのエラー、警告表示フラグを返します。
	 *
	 * @return 倉庫CDのエラー、警告表示フラグ
	 */
	public String getSoukoCdClass() {
		return (String) get("SOUKO_CD_CLASS");
	}

	/**
	 * 倉庫CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param soukoCdClass 倉庫CDのエラー、警告表示フラグ
	 */
	public void setSoukoCdClass(String soukoCdClass) {
		put("SOUKO_CD_CLASS", soukoCdClass);
	}


	/**
	 * 製品日付のエラー、警告表示フラグを返します。
	 *
	 * @return 製品日付のエラー、警告表示フラグ
	 */
	public String getSeihinDtClass() {
		return (String) get("SEIHIN_DT_CLASS");
	}

	/**
	 * 製品日付のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param seihinDtClass 製品日付のエラー、警告表示フラグ
	 */
	public void setSeihinDtClass(String seihinDtClass) {
		put("SEIHIN_DT_CLASS", seihinDtClass);
	}


	/**
	 * 積荷伝票用ラインNOのエラー、警告表示フラグを返します。
	 *
	 * @return 積荷伝票用ラインNOのエラー、警告表示フラグ
	 */
	public String getTumidenLineNoClass() {
		return (String) get("TUMIDEN_LINE_NO_CLASS");
	}

	/**
	 * 積荷伝票用ラインNOのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tumidenLineNoClass 積荷伝票用ラインNOのエラー、警告表示フラグ
	 */
	public void setTumidenLineNoClass(String tumidenLineNoClass) {
		put("TUMIDEN_LINE_NO_CLASS", tumidenLineNoClass);
	}


	/**
	 * 補足欄のエラー、警告表示フラグを返します。
	 *
	 * @return 補足欄のエラー、警告表示フラグ
	 */
	public String getHosokuranClass() {
		return (String) get("HOSOKURAN_CLASS");
	}

	/**
	 * 補足欄のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param hosokuranClass 補足欄のエラー、警告表示フラグ
	 */
	public void setHosokuranClass(String hosokuranClass) {
		put("HOSOKURAN_CLASS", hosokuranClass);
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
	 * 在庫残数_箱数のエラー、警告表示フラグを返します。
	 *
	 * @return 在庫残数_箱数のエラー、警告表示フラグ
	 */
	public String getZaikoZansuCaseClass() {
		return (String) get("ZAIKO_ZANSU_CASE_CLASS");
	}

	/**
	 * 在庫残数_箱数のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param zaikoZansuCaseClass 在庫残数_箱数のエラー、警告表示フラグ
	 */
	public void setZaikoZansuCaseClass(String zaikoZansuCaseClass) {
		put("ZAIKO_ZANSU_CASE_CLASS", zaikoZansuCaseClass);
	}


	/**
	 * 在庫残数_セット数のエラー、警告表示フラグを返します。
	 *
	 * @return 在庫残数_セット数のエラー、警告表示フラグ
	 */
	public String getZaikoZansuBaraClass() {
		return (String) get("ZAIKO_ZANSU_BARA_CLASS");
	}

	/**
	 * 在庫残数_セット数のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param zaikoZansuBaraClass 在庫残数_セット数のエラー、警告表示フラグ
	 */
	public void setZaikoZansuBaraClass(String zaikoZansuBaraClass) {
		put("ZAIKO_ZANSU_BARA_CLASS", zaikoZansuBaraClass);
	}


	/**
	 * 単品総容量(ML)@1バラ当りのエラー、警告表示フラグを返します。
	 *
	 * @return 単品総容量(ML)@1バラ当りのエラー、警告表示フラグ
	 */
	public String getTnpnVolMlClass() {
		return (String) get("TNPN_VOL_ML_CLASS");
	}

	/**
	 * 単品総容量(ML)@1バラ当りのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tnpnVolMlClass 単品総容量(ML)@1バラ当りのエラー、警告表示フラグ
	 */
	public void setTnpnVolMlClass(String tnpnVolMlClass) {
		put("TNPN_VOL_ML_CLASS", tnpnVolMlClass);
	}


	/**
	 * 出荷対応名のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷対応名のエラー、警告表示フラグ
	 */
	public String getSyukaTaioNmClass() {
		return (String) get("SYUKA_TAIO_NM_CLASS");
	}

	/**
	 * 出荷対応名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaTaioNmClass 出荷対応名のエラー、警告表示フラグ
	 */
	public void setSyukaTaioNmClass(String syukaTaioNmClass) {
		put("SYUKA_TAIO_NM_CLASS", syukaTaioNmClass);
	}


	/**
	 * 扱い区分名のエラー、警告表示フラグを返します。
	 *
	 * @return 扱い区分名のエラー、警告表示フラグ
	 */
	public String getAtukaiKbnNmClass() {
		return (String) get("ATUKAI_KBN_NM_CLASS");
	}

	/**
	 * 扱い区分名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param atukaiKbnNmClass 扱い区分名のエラー、警告表示フラグ
	 */
	public void setAtukaiKbnNmClass(String atukaiKbnNmClass) {
		put("ATUKAI_KBN_NM_CLASS", atukaiKbnNmClass);
	}


	/**
	 * 在庫残数のエラー、警告表示フラグを返します。
	 *
	 * @return 在庫残数のエラー、警告表示フラグ
	 */
	public String getZaikoZansuClass() {
		return (String) get("ZAIKO_ZANSU_CLASS");
	}

	/**
	 * 在庫残数のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param zaikoZansuClass 在庫残数のエラー、警告表示フラグ
	 */
	public void setZaikoZansuClass(String zaikoZansuClass) {
		put("ZAIKO_ZANSU_CLASS", zaikoZansuClass);
	}


	/**
	 * 在庫情報取得日時のエラー、警告表示フラグを返します。
	 *
	 * @return 在庫情報取得日時のエラー、警告表示フラグ
	 */
	public String getZaikoGetDtTmClass() {
		return (String) get("ZAIKO_GET_DT_TM_CLASS");
	}

	/**
	 * 在庫情報取得日時のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param zaikoGetDtTmClass 在庫情報取得日時のエラー、警告表示フラグ
	 */
	public void setZaikoGetDtTmClass(String zaikoGetDtTmClass) {
		put("ZAIKO_GET_DT_TM_CLASS", zaikoGetDtTmClass);
	}


	/**
	 * 商品名称_自社各帳票用(1)のエラー、警告表示フラグを返します。
	 *
	 * @return 商品名称_自社各帳票用(1)のエラー、警告表示フラグ
	 */
	public String getShnnmReport1Class() {
		return (String) get("SHNNM_REPORT1_CLASS");
	}

	/**
	 * 商品名称_自社各帳票用(1)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param shnnmReport1Class 商品名称_自社各帳票用(1)のエラー、警告表示フラグ
	 */
	public void setShnnmReport1Class(String shnnmReport1Class) {
		put("SHNNM_REPORT1_CLASS", shnnmReport1Class);
	}


	/**
	 * 容器名称_自社各帳票用のエラー、警告表示フラグを返します。
	 *
	 * @return 容器名称_自社各帳票用のエラー、警告表示フラグ
	 */
	public String getYoukiNmReportClass() {
		return (String) get("YOUKI_NM_REPORT_CLASS");
	}

	/**
	 * 容器名称_自社各帳票用のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param youkiNmReportClass 容器名称_自社各帳票用のエラー、警告表示フラグ
	 */
	public void setYoukiNmReportClass(String youkiNmReportClass) {
		put("YOUKI_NM_REPORT_CLASS", youkiNmReportClass);
	}


	/**
	 * 補償金徴収対象区分のエラー、警告表示フラグを返します。
	 *
	 * @return 補償金徴収対象区分のエラー、警告表示フラグ
	 */
	public String getIndemnityKbnClass() {
		return (String) get("INDEMNITY_KBN_CLASS");
	}

	/**
	 * 補償金徴収対象区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param indemnityKbnClass 補償金徴収対象区分のエラー、警告表示フラグ
	 */
	public void setIndemnityKbnClass(String indemnityKbnClass) {
		put("INDEMNITY_KBN_CLASS", indemnityKbnClass);
	}


	/**
	 * 引上手数料対象区分のエラー、警告表示フラグを返します。
	 *
	 * @return 引上手数料対象区分のエラー、警告表示フラグ
	 */
	public String getServiceKbnClass() {
		return (String) get("SERVICE_KBN_CLASS");
	}

	/**
	 * 引上手数料対象区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param serviceKbnClass 引上手数料対象区分のエラー、警告表示フラグ
	 */
	public void setServiceKbnClass(String serviceKbnClass) {
		put("SERVICE_KBN_CLASS", serviceKbnClass);
	}


	/**
	 * 備考1のエラー、警告表示フラグを返します。
	 *
	 * @return 備考1のエラー、警告表示フラグ
	 */
	public String getBikou1TxClass() {
		return (String) get("BIKOU_1_TX_CLASS");
	}

	/**
	 * 備考1のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param bikou1TxClass 備考1のエラー、警告表示フラグ
	 */
	public void setBikou1TxClass(String bikou1TxClass) {
		put("BIKOU_1_TX_CLASS", bikou1TxClass);
	}


	/**
	 * 備考2のエラー、警告表示フラグを返します。
	 *
	 * @return 備考2のエラー、警告表示フラグ
	 */
	public String getBikou2TxClass() {
		return (String) get("BIKOU_2_TX_CLASS");
	}

	/**
	 * 備考2のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param bikou2TxClass 備考2のエラー、警告表示フラグ
	 */
	public void setBikou2TxClass(String bikou2TxClass) {
		put("BIKOU_2_TX_CLASS", bikou2TxClass);
	}


	/**
	 * 備考3のエラー、警告表示フラグを返します。
	 *
	 * @return 備考3のエラー、警告表示フラグ
	 */
	public String getBikou3TxClass() {
		return (String) get("BIKOU_3_TX_CLASS");
	}

	/**
	 * 備考3のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param bikou3TxClass 備考3のエラー、警告表示フラグ
	 */
	public void setBikou3TxClass(String bikou3TxClass) {
		put("BIKOU_3_TX_CLASS", bikou3TxClass);
	}


	/**
	 * 備考4のエラー、警告表示フラグを返します。
	 *
	 * @return 備考4のエラー、警告表示フラグ
	 */
	public String getBikou4TxClass() {
		return (String) get("BIKOU_4_TX_CLASS");
	}

	/**
	 * 備考4のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param bikou4TxClass 備考4のエラー、警告表示フラグ
	 */
	public void setBikou4TxClass(String bikou4TxClass) {
		put("BIKOU_4_TX_CLASS", bikou4TxClass);
	}


	/**
	 * 備考5のエラー、警告表示フラグを返します。
	 *
	 * @return 備考5のエラー、警告表示フラグ
	 */
	public String getBikou5TxClass() {
		return (String) get("BIKOU_5_TX_CLASS");
	}

	/**
	 * 備考5のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param bikou5TxClass 備考5のエラー、警告表示フラグ
	 */
	public void setBikou5TxClass(String bikou5TxClass) {
		put("BIKOU_5_TX_CLASS", bikou5TxClass);
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
	 * 受注NOを返します。
	 *
	 * @return 受注NO
	 */
	public String getJyucyuNoView() {
		return (String) get("JYUCYU_NO_VIEW");
	}

	/**
	 * 受注NOをレコードに格納します。
	 *
	 * @param jyucyuNoView 受注NO
	 */
	public void setJyucyuNoView(String jyucyuNoView) {
		put("JYUCYU_NO_VIEW", jyucyuNoView);
	}


	/**
	 * 受注行NOを返します。
	 *
	 * @return 受注行NO
	 */
	public String getInputLineNoView() {
		return (String) get("INPUT_LINE_NO_VIEW");
	}

	/**
	 * 受注行NOをレコードに格納します。
	 *
	 * @param inputLineNoView 受注行NO
	 */
	public void setInputLineNoView(String inputLineNoView) {
		put("INPUT_LINE_NO_VIEW", inputLineNoView);
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
	 * 出荷数量_箱数を返します。
	 *
	 * @return 出荷数量_箱数
	 */
	public String getSyukaSuCaseView() {
		return (String) get("SYUKA_SU_CASE_VIEW");
	}

	/**
	 * 出荷数量_箱数をレコードに格納します。
	 *
	 * @param syukaSuCaseView 出荷数量_箱数
	 */
	public void setSyukaSuCaseView(String syukaSuCaseView) {
		put("SYUKA_SU_CASE_VIEW", syukaSuCaseView);
	}


	/**
	 * 出荷数量_セット数を返します。
	 *
	 * @return 出荷数量_セット数
	 */
	public String getSyukaSuBaraView() {
		return (String) get("SYUKA_SU_BARA_VIEW");
	}

	/**
	 * 出荷数量_セット数をレコードに格納します。
	 *
	 * @param syukaSuBaraView 出荷数量_セット数
	 */
	public void setSyukaSuBaraView(String syukaSuBaraView) {
		put("SYUKA_SU_BARA_VIEW", syukaSuBaraView);
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
	 * 蔵CDを返します。
	 *
	 * @return 蔵CD
	 */
	public String getKuraCdView() {
		return (String) get("KURA_CD_VIEW");
	}

	/**
	 * 蔵CDをレコードに格納します。
	 *
	 * @param kuraCdView 蔵CD
	 */
	public void setKuraCdView(String kuraCdView) {
		put("KURA_CD_VIEW", kuraCdView);
	}


	/**
	 * 寄託者CDを返します。
	 *
	 * @return 寄託者CD
	 */
	public String getKtksyCdView() {
		return (String) get("KTKSY_CD_VIEW");
	}

	/**
	 * 寄託者CDをレコードに格納します。
	 *
	 * @param ktksyCdView 寄託者CD
	 */
	public void setKtksyCdView(String ktksyCdView) {
		put("KTKSY_CD_VIEW", ktksyCdView);
	}


	/**
	 * 製品CDを返します。
	 *
	 * @return 製品CD
	 */
	public String getSeihinCdView() {
		return (String) get("SEIHIN_CD_VIEW");
	}

	/**
	 * 製品CDをレコードに格納します。
	 *
	 * @param seihinCdView 製品CD
	 */
	public void setSeihinCdView(String seihinCdView) {
		put("SEIHIN_CD_VIEW", seihinCdView);
	}


	/**
	 * 仕入先CDを返します。
	 *
	 * @return 仕入先CD
	 */
	public String getSiiresakiCdView() {
		return (String) get("SIIRESAKI_CD_VIEW");
	}

	/**
	 * 仕入先CDをレコードに格納します。
	 *
	 * @param siiresakiCdView 仕入先CD
	 */
	public void setSiiresakiCdView(String siiresakiCdView) {
		put("SIIRESAKI_CD_VIEW", siiresakiCdView);
	}


	/**
	 * 仕入品CDを返します。
	 *
	 * @return 仕入品CD
	 */
	public String getSiirehinCdView() {
		return (String) get("SIIREHIN_CD_VIEW");
	}

	/**
	 * 仕入品CDをレコードに格納します。
	 *
	 * @param siirehinCdView 仕入品CD
	 */
	public void setSiirehinCdView(String siirehinCdView) {
		put("SIIREHIN_CD_VIEW", siirehinCdView);
	}


	/**
	 * ケース入数を返します。
	 *
	 * @return ケース入数
	 */
	public String getCaseIrisuView() {
		return (String) get("CASE_IRISU_VIEW");
	}

	/**
	 * ケース入数をレコードに格納します。
	 *
	 * @param caseIrisuView ケース入数
	 */
	public void setCaseIrisuView(String caseIrisuView) {
		put("CASE_IRISU_VIEW", caseIrisuView);
	}


	/**
	 * バラ容量(L)を返します。
	 *
	 * @return バラ容量(L)
	 */
	public String getBaraYouryoView() {
		return (String) get("BARA_YOURYO_VIEW");
	}

	/**
	 * バラ容量(L)をレコードに格納します。
	 *
	 * @param baraYouryoView バラ容量(L)
	 */
	public void setBaraYouryoView(String baraYouryoView) {
		put("BARA_YOURYO_VIEW", baraYouryoView);
	}


	/**
	 * 重量(KG)_ケースを返します。
	 *
	 * @return 重量(KG)_ケース
	 */
	public String getWeightCaseView() {
		return (String) get("WEIGHT_CASE_VIEW");
	}

	/**
	 * 重量(KG)_ケースをレコードに格納します。
	 *
	 * @param weightCaseView 重量(KG)_ケース
	 */
	public void setWeightCaseView(String weightCaseView) {
		put("WEIGHT_CASE_VIEW", weightCaseView);
	}


	/**
	 * 重量(KG)_バラを返します。
	 *
	 * @return 重量(KG)_バラ
	 */
	public String getWeightBaraView() {
		return (String) get("WEIGHT_BARA_VIEW");
	}

	/**
	 * 重量(KG)_バラをレコードに格納します。
	 *
	 * @param weightBaraView 重量(KG)_バラ
	 */
	public void setWeightBaraView(String weightBaraView) {
		put("WEIGHT_BARA_VIEW", weightBaraView);
	}


	/**
	 * 運賃掛率(%)を返します。
	 *
	 * @return 運賃掛率(%)
	 */
	public String getUnchinKakerituView() {
		return (String) get("UNCHIN_KAKERITU_VIEW");
	}

	/**
	 * 運賃掛率(%)をレコードに格納します。
	 *
	 * @param unchinKakerituView 運賃掛率(%)
	 */
	public void setUnchinKakerituView(String unchinKakerituView) {
		put("UNCHIN_KAKERITU_VIEW", unchinKakerituView);
	}


	/**
	 * 出荷数量_換算総セット数を返します。
	 *
	 * @return 出荷数量_換算総セット数
	 */
	public String getSyukaAllBaraView() {
		return (String) get("SYUKA_ALL_BARA_VIEW");
	}

	/**
	 * 出荷数量_換算総セット数をレコードに格納します。
	 *
	 * @param syukaAllBaraView 出荷数量_換算総セット数
	 */
	public void setSyukaAllBaraView(String syukaAllBaraView) {
		put("SYUKA_ALL_BARA_VIEW", syukaAllBaraView);
	}


	/**
	 * 出荷重量（KG)を返します。
	 *
	 * @return 出荷重量（KG)
	 */
	public String getSyukaAllWeigthView() {
		return (String) get("SYUKA_ALL_WEIGTH_VIEW");
	}

	/**
	 * 出荷重量（KG)をレコードに格納します。
	 *
	 * @param syukaAllWeigthView 出荷重量（KG)
	 */
	public void setSyukaAllWeigthView(String syukaAllWeigthView) {
		put("SYUKA_ALL_WEIGTH_VIEW", syukaAllWeigthView);
	}


	/**
	 * 販売額を返します。
	 *
	 * @return 販売額
	 */
	public String getSyukaHanbaiKingakuView() {
		return (String) get("SYUKA_HANBAI_KINGAKU_VIEW");
	}

	/**
	 * 販売額をレコードに格納します。
	 *
	 * @param syukaHanbaiKingakuView 販売額
	 */
	public void setSyukaHanbaiKingakuView(String syukaHanbaiKingakuView) {
		put("SYUKA_HANBAI_KINGAKU_VIEW", syukaHanbaiKingakuView);
	}


	/**
	 * 出荷対応区分を返します。
	 *
	 * @return 出荷対応区分
	 */
	public String getSyukaTaioKbnView() {
		return (String) get("SYUKA_TAIO_KBN_VIEW");
	}

	/**
	 * 出荷対応区分をレコードに格納します。
	 *
	 * @param syukaTaioKbnView 出荷対応区分
	 */
	public void setSyukaTaioKbnView(String syukaTaioKbnView) {
		put("SYUKA_TAIO_KBN_VIEW", syukaTaioKbnView);
	}


	/**
	 * 扱い区分を返します。
	 *
	 * @return 扱い区分
	 */
	public String getAtukaiKbnView() {
		return (String) get("ATUKAI_KBN_VIEW");
	}

	/**
	 * 扱い区分をレコードに格納します。
	 *
	 * @param atukaiKbnView 扱い区分
	 */
	public void setAtukaiKbnView(String atukaiKbnView) {
		put("ATUKAI_KBN_VIEW", atukaiKbnView);
	}


	/**
	 * 物品区分を返します。
	 *
	 * @return 物品区分
	 */
	public String getBupinKbnView() {
		return (String) get("BUPIN_KBN_VIEW");
	}

	/**
	 * 物品区分をレコードに格納します。
	 *
	 * @param bupinKbnView 物品区分
	 */
	public void setBupinKbnView(String bupinKbnView) {
		put("BUPIN_KBN_VIEW", bupinKbnView);
	}


	/**
	 * 販売単価変更フラグを返します。
	 *
	 * @return 販売単価変更フラグ
	 */
	public String getHtankaChgFlgView() {
		return (String) get("HTANKA_CHG_FLG_VIEW");
	}

	/**
	 * 販売単価変更フラグをレコードに格納します。
	 *
	 * @param htankaChgFlgView 販売単価変更フラグ
	 */
	public void setHtankaChgFlgView(String htankaChgFlgView) {
		put("HTANKA_CHG_FLG_VIEW", htankaChgFlgView);
	}


	/**
	 * 容量（L）_出荷総容量を返します。
	 *
	 * @return 容量（L）_出荷総容量
	 */
	public String getSyukaSouyouryoView() {
		return (String) get("SYUKA_SOUYOURYO_VIEW");
	}

	/**
	 * 容量（L）_出荷総容量をレコードに格納します。
	 *
	 * @param syukaSouyouryoView 容量（L）_出荷総容量
	 */
	public void setSyukaSouyouryoView(String syukaSouyouryoView) {
		put("SYUKA_SOUYOURYO_VIEW", syukaSouyouryoView);
	}


	/**
	 * 容量（L）_リベートⅠ類対象総容量を返します。
	 *
	 * @return 容量（L）_リベートⅠ類対象総容量
	 */
	public String getRebate1SouyouryoView() {
		return (String) get("REBATE1_SOUYOURYO_VIEW");
	}

	/**
	 * 容量（L）_リベートⅠ類対象総容量をレコードに格納します。
	 *
	 * @param rebate1SouyouryoView 容量（L）_リベートⅠ類対象総容量
	 */
	public void setRebate1SouyouryoView(String rebate1SouyouryoView) {
		put("REBATE1_SOUYOURYO_VIEW", rebate1SouyouryoView);
	}


	/**
	 * 容量（L）_リベートⅡ類対象総容量を返します。
	 *
	 * @return 容量（L）_リベートⅡ類対象総容量
	 */
	public String getRebate2SouyouryoView() {
		return (String) get("REBATE2_SOUYOURYO_VIEW");
	}

	/**
	 * 容量（L）_リベートⅡ類対象総容量をレコードに格納します。
	 *
	 * @param rebate2SouyouryoView 容量（L）_リベートⅡ類対象総容量
	 */
	public void setRebate2SouyouryoView(String rebate2SouyouryoView) {
		put("REBATE2_SOUYOURYO_VIEW", rebate2SouyouryoView);
	}


	/**
	 * 容量（L）_リベートⅢ類対象総容量を返します。
	 *
	 * @return 容量（L）_リベートⅢ類対象総容量
	 */
	public String getRebate3SouyouryoView() {
		return (String) get("REBATE3_SOUYOURYO_VIEW");
	}

	/**
	 * 容量（L）_リベートⅢ類対象総容量をレコードに格納します。
	 *
	 * @param rebate3SouyouryoView 容量（L）_リベートⅢ類対象総容量
	 */
	public void setRebate3SouyouryoView(String rebate3SouyouryoView) {
		put("REBATE3_SOUYOURYO_VIEW", rebate3SouyouryoView);
	}


	/**
	 * 容量（L）_リベートⅣ類対象総容量を返します。
	 *
	 * @return 容量（L）_リベートⅣ類対象総容量
	 */
	public String getRebate4SouyouryoView() {
		return (String) get("REBATE4_SOUYOURYO_VIEW");
	}

	/**
	 * 容量（L）_リベートⅣ類対象総容量をレコードに格納します。
	 *
	 * @param rebate4SouyouryoView 容量（L）_リベートⅣ類対象総容量
	 */
	public void setRebate4SouyouryoView(String rebate4SouyouryoView) {
		put("REBATE4_SOUYOURYO_VIEW", rebate4SouyouryoView);
	}


	/**
	 * 容量（L）_リベートⅤ類対象総容量を返します。
	 *
	 * @return 容量（L）_リベートⅤ類対象総容量
	 */
	public String getRebate5SouyouryoView() {
		return (String) get("REBATE5_SOUYOURYO_VIEW");
	}

	/**
	 * 容量（L）_リベートⅤ類対象総容量をレコードに格納します。
	 *
	 * @param rebate5SouyouryoView 容量（L）_リベートⅤ類対象総容量
	 */
	public void setRebate5SouyouryoView(String rebate5SouyouryoView) {
		put("REBATE5_SOUYOURYO_VIEW", rebate5SouyouryoView);
	}


	/**
	 * 容量（L）_リベート対象外総容量を返します。
	 *
	 * @return 容量（L）_リベート対象外総容量
	 */
	public String getRebateoSouyouryoView() {
		return (String) get("REBATEO_SOUYOURYO_VIEW");
	}

	/**
	 * 容量（L）_リベート対象外総容量をレコードに格納します。
	 *
	 * @param rebateoSouyouryoView 容量（L）_リベート対象外総容量
	 */
	public void setRebateoSouyouryoView(String rebateoSouyouryoView) {
		put("REBATEO_SOUYOURYO_VIEW", rebateoSouyouryoView);
	}


	/**
	 * 特注指示区分を返します。
	 *
	 * @return 特注指示区分
	 */
	public String getPbTokucyuKbnView() {
		return (String) get("PB_TOKUCYU_KBN_VIEW");
	}

	/**
	 * 特注指示区分をレコードに格納します。
	 *
	 * @param pbTokucyuKbnView 特注指示区分
	 */
	public void setPbTokucyuKbnView(String pbTokucyuKbnView) {
		put("PB_TOKUCYU_KBN_VIEW", pbTokucyuKbnView);
	}


	/**
	 * PB OR 特注指示内容を返します。
	 *
	 * @return PB OR 特注指示内容
	 */
	public String getPbTokucyuView() {
		return (String) get("PB_TOKUCYU_VIEW");
	}

	/**
	 * PB OR 特注指示内容をレコードに格納します。
	 *
	 * @param pbTokucyuView PB OR 特注指示内容
	 */
	public void setPbTokucyuView(String pbTokucyuView) {
		put("PB_TOKUCYU_VIEW", pbTokucyuView);
	}


	/**
	 * 販売部門CDを返します。
	 *
	 * @return 販売部門CD
	 */
	public String getHanbaiBumonCdView() {
		return (String) get("HANBAI_BUMON_CD_VIEW");
	}

	/**
	 * 販売部門CDをレコードに格納します。
	 *
	 * @param hanbaiBumonCdView 販売部門CD
	 */
	public void setHanbaiBumonCdView(String hanbaiBumonCdView) {
		put("HANBAI_BUMON_CD_VIEW", hanbaiBumonCdView);
	}


	/**
	 * 販売部門名（略式）を返します。
	 *
	 * @return 販売部門名（略式）
	 */
	public String getHanbaiBumonRnmView() {
		return (String) get("HANBAI_BUMON_RNM_VIEW");
	}

	/**
	 * 販売部門名（略式）をレコードに格納します。
	 *
	 * @param hanbaiBumonRnmView 販売部門名（略式）
	 */
	public void setHanbaiBumonRnmView(String hanbaiBumonRnmView) {
		put("HANBAI_BUMON_RNM_VIEW", hanbaiBumonRnmView);
	}


	/**
	 * 販売種別CDを返します。
	 *
	 * @return 販売種別CD
	 */
	public String getHanbaiSyubetuCdView() {
		return (String) get("HANBAI_SYUBETU_CD_VIEW");
	}

	/**
	 * 販売種別CDをレコードに格納します。
	 *
	 * @param hanbaiSyubetuCdView 販売種別CD
	 */
	public void setHanbaiSyubetuCdView(String hanbaiSyubetuCdView) {
		put("HANBAI_SYUBETU_CD_VIEW", hanbaiSyubetuCdView);
	}


	/**
	 * 販売種別名（略式）を返します。
	 *
	 * @return 販売種別名（略式）
	 */
	public String getHanbaiSyubetuRnmView() {
		return (String) get("HANBAI_SYUBETU_RNM_VIEW");
	}

	/**
	 * 販売種別名（略式）をレコードに格納します。
	 *
	 * @param hanbaiSyubetuRnmView 販売種別名（略式）
	 */
	public void setHanbaiSyubetuRnmView(String hanbaiSyubetuRnmView) {
		put("HANBAI_SYUBETU_RNM_VIEW", hanbaiSyubetuRnmView);
	}


	/**
	 * 販売分類CDを返します。
	 *
	 * @return 販売分類CD
	 */
	public String getHanbaiBunruiCdView() {
		return (String) get("HANBAI_BUNRUI_CD_VIEW");
	}

	/**
	 * 販売分類CDをレコードに格納します。
	 *
	 * @param hanbaiBunruiCdView 販売分類CD
	 */
	public void setHanbaiBunruiCdView(String hanbaiBunruiCdView) {
		put("HANBAI_BUNRUI_CD_VIEW", hanbaiBunruiCdView);
	}


	/**
	 * 販売分類名（略式）を返します。
	 *
	 * @return 販売分類名（略式）
	 */
	public String getHanbaiBunruiRnmView() {
		return (String) get("HANBAI_BUNRUI_RNM_VIEW");
	}

	/**
	 * 販売分類名（略式）をレコードに格納します。
	 *
	 * @param hanbaiBunruiRnmView 販売分類名（略式）
	 */
	public void setHanbaiBunruiRnmView(String hanbaiBunruiRnmView) {
		put("HANBAI_BUNRUI_RNM_VIEW", hanbaiBunruiRnmView);
	}


	/**
	 * SDN受注ディテールエラー区分を返します。
	 *
	 * @return SDN受注ディテールエラー区分
	 */
	public String getSdnDterrKbnView() {
		return (String) get("SDN_DTERR_KBN_VIEW");
	}

	/**
	 * SDN受注ディテールエラー区分をレコードに格納します。
	 *
	 * @param sdnDterrKbnView SDN受注ディテールエラー区分
	 */
	public void setSdnDterrKbnView(String sdnDterrKbnView) {
		put("SDN_DTERR_KBN_VIEW", sdnDterrKbnView);
	}


	/**
	 * EDI配送依頼(集約)送信区分を返します。
	 *
	 * @return EDI配送依頼(集約)送信区分
	 */
	public String getEdiHaisougSendKbView() {
		return (String) get("EDI_HAISOUG_SEND_KB_VIEW");
	}

	/**
	 * EDI配送依頼(集約)送信区分をレコードに格納します。
	 *
	 * @param ediHaisougSendKbView EDI配送依頼(集約)送信区分
	 */
	public void setEdiHaisougSendKbView(String ediHaisougSendKbView) {
		put("EDI_HAISOUG_SEND_KB_VIEW", ediHaisougSendKbView);
	}


	/**
	 * 倉庫CDを返します。
	 *
	 * @return 倉庫CD
	 */
	public String getSoukoCdView() {
		return (String) get("SOUKO_CD_VIEW");
	}

	/**
	 * 倉庫CDをレコードに格納します。
	 *
	 * @param soukoCdView 倉庫CD
	 */
	public void setSoukoCdView(String soukoCdView) {
		put("SOUKO_CD_VIEW", soukoCdView);
	}


	/**
	 * 製品日付を返します。
	 *
	 * @return 製品日付
	 */
	public String getSeihinDtView() {
		return (String) get("SEIHIN_DT_VIEW");
	}

	/**
	 * 製品日付をレコードに格納します。
	 *
	 * @param seihinDtView 製品日付
	 */
	public void setSeihinDtView(String seihinDtView) {
		put("SEIHIN_DT_VIEW", seihinDtView);
	}


	/**
	 * 積荷伝票用ラインNOを返します。
	 *
	 * @return 積荷伝票用ラインNO
	 */
	public String getTumidenLineNoView() {
		return (String) get("TUMIDEN_LINE_NO_VIEW");
	}

	/**
	 * 積荷伝票用ラインNOをレコードに格納します。
	 *
	 * @param tumidenLineNoView 積荷伝票用ラインNO
	 */
	public void setTumidenLineNoView(String tumidenLineNoView) {
		put("TUMIDEN_LINE_NO_VIEW", tumidenLineNoView);
	}


	/**
	 * 補足欄を返します。
	 *
	 * @return 補足欄
	 */
	public String getHosokuranView() {
		return (String) get("HOSOKURAN_VIEW");
	}

	/**
	 * 補足欄をレコードに格納します。
	 *
	 * @param hosokuranView 補足欄
	 */
	public void setHosokuranView(String hosokuranView) {
		put("HOSOKURAN_VIEW", hosokuranView);
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
	 * 在庫残数_箱数を返します。
	 *
	 * @return 在庫残数_箱数
	 */
	public String getZaikoZansuCaseView() {
		return (String) get("ZAIKO_ZANSU_CASE_VIEW");
	}

	/**
	 * 在庫残数_箱数をレコードに格納します。
	 *
	 * @param zaikoZansuCaseView 在庫残数_箱数
	 */
	public void setZaikoZansuCaseView(String zaikoZansuCaseView) {
		put("ZAIKO_ZANSU_CASE_VIEW", zaikoZansuCaseView);
	}


	/**
	 * 在庫残数_セット数を返します。
	 *
	 * @return 在庫残数_セット数
	 */
	public String getZaikoZansuBaraView() {
		return (String) get("ZAIKO_ZANSU_BARA_VIEW");
	}

	/**
	 * 在庫残数_セット数をレコードに格納します。
	 *
	 * @param zaikoZansuBaraView 在庫残数_セット数
	 */
	public void setZaikoZansuBaraView(String zaikoZansuBaraView) {
		put("ZAIKO_ZANSU_BARA_VIEW", zaikoZansuBaraView);
	}


	/**
	 * 単品総容量(ML)@1バラ当りを返します。
	 *
	 * @return 単品総容量(ML)@1バラ当り
	 */
	public String getTnpnVolMlView() {
		return (String) get("TNPN_VOL_ML_VIEW");
	}

	/**
	 * 単品総容量(ML)@1バラ当りをレコードに格納します。
	 *
	 * @param tnpnVolMlView 単品総容量(ML)@1バラ当り
	 */
	public void setTnpnVolMlView(String tnpnVolMlView) {
		put("TNPN_VOL_ML_VIEW", tnpnVolMlView);
	}


	/**
	 * 出荷対応名を返します。
	 *
	 * @return 出荷対応名
	 */
	public String getSyukaTaioNmView() {
		return (String) get("SYUKA_TAIO_NM_VIEW");
	}

	/**
	 * 出荷対応名をレコードに格納します。
	 *
	 * @param syukaTaioNmView 出荷対応名
	 */
	public void setSyukaTaioNmView(String syukaTaioNmView) {
		put("SYUKA_TAIO_NM_VIEW", syukaTaioNmView);
	}


	/**
	 * 扱い区分名を返します。
	 *
	 * @return 扱い区分名
	 */
	public String getAtukaiKbnNmView() {
		return (String) get("ATUKAI_KBN_NM_VIEW");
	}

	/**
	 * 扱い区分名をレコードに格納します。
	 *
	 * @param atukaiKbnNmView 扱い区分名
	 */
	public void setAtukaiKbnNmView(String atukaiKbnNmView) {
		put("ATUKAI_KBN_NM_VIEW", atukaiKbnNmView);
	}


	/**
	 * 在庫残数を返します。
	 *
	 * @return 在庫残数
	 */
	public String getZaikoZansuView() {
		return (String) get("ZAIKO_ZANSU_VIEW");
	}

	/**
	 * 在庫残数をレコードに格納します。
	 *
	 * @param zaikoZansuView 在庫残数
	 */
	public void setZaikoZansuView(String zaikoZansuView) {
		put("ZAIKO_ZANSU_VIEW", zaikoZansuView);
	}


	/**
	 * 在庫情報取得日時を返します。
	 *
	 * @return 在庫情報取得日時
	 */
	public String getZaikoGetDtTmView() {
		return (String) get("ZAIKO_GET_DT_TM_VIEW");
	}

	/**
	 * 在庫情報取得日時をレコードに格納します。
	 *
	 * @param zaikoGetDtTmView 在庫情報取得日時
	 */
	public void setZaikoGetDtTmView(String zaikoGetDtTmView) {
		put("ZAIKO_GET_DT_TM_VIEW", zaikoGetDtTmView);
	}


	/**
	 * 商品名称_自社各帳票用(1)を返します。
	 *
	 * @return 商品名称_自社各帳票用(1)
	 */
	public String getShnnmReport1View() {
		return (String) get("SHNNM_REPORT1_VIEW");
	}

	/**
	 * 商品名称_自社各帳票用(1)をレコードに格納します。
	 *
	 * @param shnnmReport1View 商品名称_自社各帳票用(1)
	 */
	public void setShnnmReport1View(String shnnmReport1View) {
		put("SHNNM_REPORT1_VIEW", shnnmReport1View);
	}


	/**
	 * 容器名称_自社各帳票用を返します。
	 *
	 * @return 容器名称_自社各帳票用
	 */
	public String getYoukiNmReportView() {
		return (String) get("YOUKI_NM_REPORT_VIEW");
	}

	/**
	 * 容器名称_自社各帳票用をレコードに格納します。
	 *
	 * @param youkiNmReportView 容器名称_自社各帳票用
	 */
	public void setYoukiNmReportView(String youkiNmReportView) {
		put("YOUKI_NM_REPORT_VIEW", youkiNmReportView);
	}


	/**
	 * 補償金徴収対象区分を返します。
	 *
	 * @return 補償金徴収対象区分
	 */
	public String getIndemnityKbnView() {
		return (String) get("INDEMNITY_KBN_VIEW");
	}

	/**
	 * 補償金徴収対象区分をレコードに格納します。
	 *
	 * @param indemnityKbnView 補償金徴収対象区分
	 */
	public void setIndemnityKbnView(String indemnityKbnView) {
		put("INDEMNITY_KBN_VIEW", indemnityKbnView);
	}


	/**
	 * 引上手数料対象区分を返します。
	 *
	 * @return 引上手数料対象区分
	 */
	public String getServiceKbnView() {
		return (String) get("SERVICE_KBN_VIEW");
	}

	/**
	 * 引上手数料対象区分をレコードに格納します。
	 *
	 * @param serviceKbnView 引上手数料対象区分
	 */
	public void setServiceKbnView(String serviceKbnView) {
		put("SERVICE_KBN_VIEW", serviceKbnView);
	}


	/**
	 * 備考1を返します。
	 *
	 * @return 備考1
	 */
	public String getBikou1TxView() {
		return (String) get("BIKOU_1_TX_VIEW");
	}

	/**
	 * 備考1をレコードに格納します。
	 *
	 * @param bikou1TxView 備考1
	 */
	public void setBikou1TxView(String bikou1TxView) {
		put("BIKOU_1_TX_VIEW", bikou1TxView);
	}


	/**
	 * 備考2を返します。
	 *
	 * @return 備考2
	 */
	public String getBikou2TxView() {
		return (String) get("BIKOU_2_TX_VIEW");
	}

	/**
	 * 備考2をレコードに格納します。
	 *
	 * @param bikou2TxView 備考2
	 */
	public void setBikou2TxView(String bikou2TxView) {
		put("BIKOU_2_TX_VIEW", bikou2TxView);
	}


	/**
	 * 備考3を返します。
	 *
	 * @return 備考3
	 */
	public String getBikou3TxView() {
		return (String) get("BIKOU_3_TX_VIEW");
	}

	/**
	 * 備考3をレコードに格納します。
	 *
	 * @param bikou3TxView 備考3
	 */
	public void setBikou3TxView(String bikou3TxView) {
		put("BIKOU_3_TX_VIEW", bikou3TxView);
	}


	/**
	 * 備考4を返します。
	 *
	 * @return 備考4
	 */
	public String getBikou4TxView() {
		return (String) get("BIKOU_4_TX_VIEW");
	}

	/**
	 * 備考4をレコードに格納します。
	 *
	 * @param bikou4TxView 備考4
	 */
	public void setBikou4TxView(String bikou4TxView) {
		put("BIKOU_4_TX_VIEW", bikou4TxView);
	}


	/**
	 * 備考5を返します。
	 *
	 * @return 備考5
	 */
	public String getBikou5TxView() {
		return (String) get("BIKOU_5_TX_VIEW");
	}

	/**
	 * 備考5をレコードに格納します。
	 *
	 * @param bikou5TxView 備考5
	 */
	public void setBikou5TxView(String bikou5TxView) {
		put("BIKOU_5_TX_VIEW", bikou5TxView);
	}

}	// -- class
