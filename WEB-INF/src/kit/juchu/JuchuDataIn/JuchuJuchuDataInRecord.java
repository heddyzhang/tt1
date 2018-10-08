package kit.juchu.JuchuDataIn;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 受注入力（受注データ／ヘッダー部）のレコードクラス
 */
public class JuchuJuchuDataInRecord extends KitRecord {

	/** シリアルID */
	private static final long serialVersionUID = 6826820930172859943L;

	/** デバッグ */
	boolean isDebug_ = false;

	/** コンストラクタ */
	public JuchuJuchuDataInRecord() {
		super();
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input Map
	 */
	public JuchuJuchuDataInRecord(Map<String, Object> input) {
		super(input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input HashMap
	 */
	public JuchuJuchuDataInRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input JuchuJuchuDataInRecord
	 */
	public JuchuJuchuDataInRecord(JuchuJuchuDataInRecord input) {
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
		JuchuJuchuDataInRecord newRec = (JuchuJuchuDataInRecord) reco;
		// 追加モード
		if (isAddMode()) {
			setRiyouKbn(newRec.getRiyouKbn()); // 利用区分
			setKaisyaCd(newRec.getKaisyaCd()); // 会社CD (FK)
			setJyucyuNo(newRec.getJyucyuNo()); // 受注NO
			setOrsTatesnCd(newRec.getOrsTatesnCd()); // 縦線CD (FK)
			setTantosyaCd(newRec.getTantosyaCd()); // 担当者CD
			setSenpoHacyuNo(newRec.getSenpoHacyuNo()); // 先方発注NO
			setSyukaDt(newRec.getSyukaDt()); // 出荷日(売上伝票発行予定日)
			setChacuniYoteiDt(newRec.getChacuniYoteiDt()); // 着荷予定日
			setSyuhantenCd(newRec.getSyuhantenCd()); // 酒販店（統一）CD
			setUnchinKbn(newRec.getUnchinKbn()); // 運賃区分
			setUnsotenCd(newRec.getUnsotenCd()); // 運送店CD
			setSyukaSyoninNo(newRec.getSyukaSyoninNo()); // 小ロット出荷承認申請NO
			setSyukaKbn(newRec.getSyukaKbn()); // 出荷先区分
			setKuraCd(newRec.getKuraCd()); // 蔵CD (FK)
			setMinasiDt(newRec.getMinasiDt()); // ミナシ日付
			setNiukeTimeKbn(newRec.getNiukeTimeKbn()); // 荷受時間区分
			setNiukeBiginTime(newRec.getNiukeBiginTime()); // 荷受時間_開始
			setNiukeEndTime(newRec.getNiukeEndTime()); // 荷受時間_終了
			setTekiyoKbn1(newRec.getTekiyoKbn1()); // 摘要区分 (01)
			setTekiyoNm1(newRec.getTekiyoNm1()); // 摘要内容 (01)
			setTekiyoKbn2(newRec.getTekiyoKbn2()); // 摘要区分 (02)
			setTekiyoNm2(newRec.getTekiyoNm2()); // 摘要内容 (02)
			setTekiyoKbn3(newRec.getTekiyoKbn3()); // 摘要区分 (03)
			setTekiyoNm3(newRec.getTekiyoNm3()); // 摘要内容 (03)
			setTekiyoKbn4(newRec.getTekiyoKbn4()); // 摘要区分 (04)
			setTekiyoNm4(newRec.getTekiyoNm4()); // 摘要内容 (04)
			setTekiyoKbn5(newRec.getTekiyoKbn5()); // 摘要区分 (05)
			setTekiyoNm5(newRec.getTekiyoNm5()); // 摘要内容 (05)
			setAtukaiKbn(newRec.getAtukaiKbn()); // 扱い区分
			setJyuryoTot(newRec.getJyuryoTot()); // 重量計(KG)
			setSyubetuCd(newRec.getSyubetuCd()); // データ種別CD
			setTokuyakutenCd(newRec.getTokuyakutenCd()); // 特約店CD
			setDepoCd(newRec.getDepoCd()); // デポCD
			setNijitenCd(newRec.getNijitenCd()); // 二次店CD
			setSanjitenCd(newRec.getSanjitenCd()); // 三次店CD
			setSyukaSakiCountryCd(newRec.getSyukaSakiCountryCd()); // 出荷先国CD
			setJisCd(newRec.getJisCd()); // JISCD
			setUnchinCnvTanka(newRec.getUnchinCnvTanka()); // 引取運賃換算単価
			setLowHaisoLot(newRec.getLowHaisoLot()); // 最低配送ロット数
			setSyukaSuryoBox(newRec.getSyukaSuryoBox()); // 出荷数量計_箱数
			setSyukaSuryoSet(newRec.getSyukaSuryoSet()); // 出荷数量計_セット数
			setSyukaKingakuTot(newRec.getSyukaKingakuTot()); // 出荷金額計
			setSyukaYouryoTot(newRec.getSyukaYouryoTot()); // 容量計（L）_出荷容量計
			setRebate1YouryoTot(newRec.getRebate1YouryoTot()); // 容量計（L）_リベートⅠ類対象容量計
			setRebate2YouryoTot(newRec.getRebate2YouryoTot()); // 容量計（L）_リベートⅡ類対象容量計
			setRebate3YouryoTot(newRec.getRebate3YouryoTot()); // 容量計（L）_リベートⅢ類対象容量計
			setRebate4YouryoTot(newRec.getRebate4YouryoTot()); // 容量計（L）_リベートⅣ類対象容量計
			setRebate5YouryoTot(newRec.getRebate5YouryoTot()); // 容量計（L）_リベートⅤ類対象容量計
			setRebateoYouryoTot(newRec.getRebateoYouryoTot()); // 容量計（L）_リベート対象外容量計

		// 変更モード（主キー項目以外）
		} else {
			setRiyouKbn(newRec.getRiyouKbn()); // 利用区分
//主キー項目			setKaisyaCd(newRec.getKaisyaCd()); // 会社CD (FK)
//主キー項目			setJyucyuNo(newRec.getJyucyuNo()); // 受注NO
			setOrsTatesnCd(newRec.getOrsTatesnCd()); // 縦線CD (FK)
			setTantosyaCd(newRec.getTantosyaCd()); // 担当者CD
			setSenpoHacyuNo(newRec.getSenpoHacyuNo()); // 先方発注NO
			setSyukaDt(newRec.getSyukaDt()); // 出荷日(売上伝票発行予定日)
			setChacuniYoteiDt(newRec.getChacuniYoteiDt()); // 着荷予定日
			setSyuhantenCd(newRec.getSyuhantenCd()); // 酒販店（統一）CD
			setUnchinKbn(newRec.getUnchinKbn()); // 運賃区分
			setUnsotenCd(newRec.getUnsotenCd()); // 運送店CD
			setSyukaSyoninNo(newRec.getSyukaSyoninNo()); // 小ロット出荷承認申請NO
			setSyukaKbn(newRec.getSyukaKbn()); // 出荷先区分
			setKuraCd(newRec.getKuraCd()); // 蔵CD (FK)
			setMinasiDt(newRec.getMinasiDt()); // ミナシ日付
			setNiukeTimeKbn(newRec.getNiukeTimeKbn()); // 荷受時間区分
			setNiukeBiginTime(newRec.getNiukeBiginTime()); // 荷受時間_開始
			setNiukeEndTime(newRec.getNiukeEndTime()); // 荷受時間_終了
			setTekiyoKbn1(newRec.getTekiyoKbn1()); // 摘要区分 (01)
			setTekiyoNm1(newRec.getTekiyoNm1()); // 摘要内容 (01)
			setTekiyoKbn2(newRec.getTekiyoKbn2()); // 摘要区分 (02)
			setTekiyoNm2(newRec.getTekiyoNm2()); // 摘要内容 (02)
			setTekiyoKbn3(newRec.getTekiyoKbn3()); // 摘要区分 (03)
			setTekiyoNm3(newRec.getTekiyoNm3()); // 摘要内容 (03)
			setTekiyoKbn4(newRec.getTekiyoKbn4()); // 摘要区分 (04)
			setTekiyoNm4(newRec.getTekiyoNm4()); // 摘要内容 (04)
			setTekiyoKbn5(newRec.getTekiyoKbn5()); // 摘要区分 (05)
			setTekiyoNm5(newRec.getTekiyoNm5()); // 摘要内容 (05)
			setAtukaiKbn(newRec.getAtukaiKbn()); // 扱い区分
			setJyuryoTot(newRec.getJyuryoTot()); // 重量計(KG)
			setSyubetuCd(newRec.getSyubetuCd()); // データ種別CD
			setTokuyakutenCd(newRec.getTokuyakutenCd()); // 特約店CD
			setDepoCd(newRec.getDepoCd()); // デポCD
			setNijitenCd(newRec.getNijitenCd()); // 二次店CD
			setSanjitenCd(newRec.getSanjitenCd()); // 三次店CD
			setSyukaSakiCountryCd(newRec.getSyukaSakiCountryCd()); // 出荷先国CD
			setJisCd(newRec.getJisCd()); // JISCD
			setUnchinCnvTanka(newRec.getUnchinCnvTanka()); // 引取運賃換算単価
			setLowHaisoLot(newRec.getLowHaisoLot()); // 最低配送ロット数
			setSyukaSuryoBox(newRec.getSyukaSuryoBox()); // 出荷数量計_箱数
			setSyukaSuryoSet(newRec.getSyukaSuryoSet()); // 出荷数量計_セット数
			setSyukaKingakuTot(newRec.getSyukaKingakuTot()); // 出荷金額計
			setSyukaYouryoTot(newRec.getSyukaYouryoTot()); // 容量計（L）_出荷容量計
			setRebate1YouryoTot(newRec.getRebate1YouryoTot()); // 容量計（L）_リベートⅠ類対象容量計
			setRebate2YouryoTot(newRec.getRebate2YouryoTot()); // 容量計（L）_リベートⅡ類対象容量計
			setRebate3YouryoTot(newRec.getRebate3YouryoTot()); // 容量計（L）_リベートⅢ類対象容量計
			setRebate4YouryoTot(newRec.getRebate4YouryoTot()); // 容量計（L）_リベートⅣ類対象容量計
			setRebate5YouryoTot(newRec.getRebate5YouryoTot()); // 容量計（L）_リベートⅤ類対象容量計
			setRebateoYouryoTot(newRec.getRebateoYouryoTot()); // 容量計（L）_リベート対象外容量計

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

			// 縦線CD (FK)
			if (!PbsUtil.isEmpty(getOrsTatesnCd())) {
				bEmpty = false;
			}

			// 先方発注NO
			if (!PbsUtil.isEmpty(getSenpoHacyuNo())) {
				bEmpty = false;
			}

			// 出荷日(売上伝票発行予定日)
			if (!PbsUtil.isEmpty(getSyukaDt())) {
				bEmpty = false;
			}

			// 着荷予定日
			if (!PbsUtil.isEmpty(getChacuniYoteiDt())) {
				bEmpty = false;
			}

			// 酒販店（統一）CD
			if (!PbsUtil.isEmpty(getSyuhantenCd())) {
				bEmpty = false;
			}

			// 運賃区分
			if (!PbsUtil.isEmpty(getUnchinKbn())) {
				bEmpty = false;
			}

			// 運送店CD
			if (!PbsUtil.isEmpty(getUnsotenCd())) {
				bEmpty = false;
			}

			// 小ロット出荷承認申請NO
			if (!PbsUtil.isEmpty(getSyukaSyoninNo())) {
				bEmpty = false;
			}

			// 蔵CD (FK)
			if (!PbsUtil.isEmpty(getKuraCd())) {
				bEmpty = false;
			}

			// ミナシ日付
			if (!PbsUtil.isEmpty(getMinasiDt())) {
				bEmpty = false;
			}

			// 荷受時間区分
			if (!PbsUtil.isEmpty(getNiukeTimeKbn())) {
				bEmpty = false;
			}

			// 荷受時間_開始
			if (!PbsUtil.isEmpty(getNiukeBiginTime())) {
				bEmpty = false;
			}

			// 荷受時間_終了
			if (!PbsUtil.isEmpty(getNiukeEndTime())) {
				bEmpty = false;
			}

			// 摘要区分 (01)
			if (!PbsUtil.isEmpty(getTekiyoKbn1())) {
				bEmpty = false;
			}

			// 摘要内容 (01)
			if (!PbsUtil.isEmpty(getTekiyoNm1())) {
				bEmpty = false;
			}

			// 摘要区分 (02)
			if (!PbsUtil.isEmpty(getTekiyoKbn2())) {
				bEmpty = false;
			}

			// 摘要内容 (02)
			if (!PbsUtil.isEmpty(getTekiyoNm2())) {
				bEmpty = false;
			}

			// 摘要区分 (03)
			if (!PbsUtil.isEmpty(getTekiyoKbn3())) {
				bEmpty = false;
			}

			// 摘要内容 (03)
			if (!PbsUtil.isEmpty(getTekiyoNm3())) {
				bEmpty = false;
			}

			// 摘要区分 (04)
			if (!PbsUtil.isEmpty(getTekiyoKbn4())) {
				bEmpty = false;
			}

			// 摘要内容 (04)
			if (!PbsUtil.isEmpty(getTekiyoNm4())) {
				bEmpty = false;
			}

			// 摘要区分 (05)
			if (!PbsUtil.isEmpty(getTekiyoKbn5())) {
				bEmpty = false;
			}

			// 摘要内容 (05)
			if (!PbsUtil.isEmpty(getTekiyoNm5())) {
				bEmpty = false;
			}

		// 変更モードの場合
		} else {
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
		JuchuJuchuDataInRecord target = (JuchuJuchuDataInRecord) reco;
		// 追加モードの場合（入力項目）
		if (this.isAddMode()) {
			// 縦線CD (FK)
			if (!PbsUtil.isEqual(getOrsTatesnCd(), target.getOrsTatesnCd())) {
				bEquals = false;
			}

			// 先方発注NO
			if (!PbsUtil.isEqual(getSenpoHacyuNo(), target.getSenpoHacyuNo())) {
				bEquals = false;
			}

			// 出荷日(売上伝票発行予定日)
			if (!PbsUtil.isEqual(getSyukaDt(), target.getSyukaDt())) {
				bEquals = false;
			}

			// 着荷予定日
			if (!PbsUtil.isEqual(getChacuniYoteiDt(), target.getChacuniYoteiDt())) {
				bEquals = false;
			}

			// 酒販店（統一）CD
			if (!PbsUtil.isEqual(getSyuhantenCd(), target.getSyuhantenCd())) {
				bEquals = false;
			}

			// 運賃区分
			if (!PbsUtil.isEqual(getUnchinKbn(), target.getUnchinKbn())) {
				bEquals = false;
			}

			// 運送店CD
			if (!PbsUtil.isEqual(getUnsotenCd(), target.getUnsotenCd())) {
				bEquals = false;
			}

			// 小ロット出荷承認申請NO
			if (!PbsUtil.isEqual(getSyukaSyoninNo(), target.getSyukaSyoninNo())) {
				bEquals = false;
			}

			// 蔵CD (FK)
			if (!PbsUtil.isEqual(getKuraCd(), target.getKuraCd())) {
				bEquals = false;
			}

			// ミナシ日付
			if (!PbsUtil.isEqual(getMinasiDt(), target.getMinasiDt())) {
				bEquals = false;
			}

			// 荷受時間区分
			if (!PbsUtil.isEqual(getNiukeTimeKbn(), target.getNiukeTimeKbn())) {
				bEquals = false;
			}

			// 荷受時間_開始
			if (!PbsUtil.isEqual(getNiukeBiginTime(), target.getNiukeBiginTime())) {
				bEquals = false;
			}

			// 荷受時間_終了
			if (!PbsUtil.isEqual(getNiukeEndTime(), target.getNiukeEndTime())) {
				bEquals = false;
			}

			// 摘要区分 (01)
			if (!PbsUtil.isEqual(getTekiyoKbn1(), target.getTekiyoKbn1())) {
				bEquals = false;
			}

			// 摘要内容 (01)
			if (!PbsUtil.isEqual(getTekiyoNm1(), target.getTekiyoNm1())) {
				bEquals = false;
			}

			// 摘要区分 (02)
			if (!PbsUtil.isEqual(getTekiyoKbn2(), target.getTekiyoKbn2())) {
				bEquals = false;
			}

			// 摘要内容 (02)
			if (!PbsUtil.isEqual(getTekiyoNm2(), target.getTekiyoNm2())) {
				bEquals = false;
			}

			// 摘要区分 (03)
			if (!PbsUtil.isEqual(getTekiyoKbn3(), target.getTekiyoKbn3())) {
				bEquals = false;
			}

			// 摘要内容 (03)
			if (!PbsUtil.isEqual(getTekiyoNm3(), target.getTekiyoNm3())) {
				bEquals = false;
			}

			// 摘要区分 (04)
			if (!PbsUtil.isEqual(getTekiyoKbn4(), target.getTekiyoKbn4())) {
				bEquals = false;
			}

			// 摘要内容 (04)
			if (!PbsUtil.isEqual(getTekiyoNm4(), target.getTekiyoNm4())) {
				bEquals = false;
			}

			// 摘要区分 (05)
			if (!PbsUtil.isEqual(getTekiyoKbn5(), target.getTekiyoKbn5())) {
				bEquals = false;
			}

			// 摘要内容 (05)
			if (!PbsUtil.isEqual(getTekiyoNm5(), target.getTekiyoNm5())) {
				bEquals = false;
			}

		// 変更モードの場合（主キー項目以外）
		} else {
			// 利用区分
			if (!PbsUtil.isEqual(getRiyouKbn(), target.getRiyouKbn())) {
				setRiyouKbnClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setRiyouKbnClass(STYLE_CLASS_NO_EDIT);
			}

			// 縦線CD (FK)
			if (!PbsUtil.isEqual(getOrsTatesnCd(), target.getOrsTatesnCd())) {
				setOrsTatesnCdClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setOrsTatesnCdClass(STYLE_CLASS_NO_EDIT);
			}

			// 先方発注NO
			if (!PbsUtil.isEqual(getSenpoHacyuNo(), target.getSenpoHacyuNo())) {
				setSenpoHacyuNoClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setSenpoHacyuNoClass(STYLE_CLASS_NO_EDIT);
			}

			// 出荷日(売上伝票発行予定日)
			if (!PbsUtil.isEqual(getSyukaDt(), target.getSyukaDt())) {
				setSyukaDtClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setSyukaDtClass(STYLE_CLASS_NO_EDIT);
			}

			// 着荷予定日
			if (!PbsUtil.isEqual(getChacuniYoteiDt(), target.getChacuniYoteiDt())) {
				setChacuniYoteiDtClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setChacuniYoteiDtClass(STYLE_CLASS_NO_EDIT);
			}

			// 酒販店（統一）CD
			if (!PbsUtil.isEqual(getSyuhantenCd(), target.getSyuhantenCd())) {
				setSyuhantenCdClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setSyuhantenCdClass(STYLE_CLASS_NO_EDIT);
			}

			// 運賃区分
			if (!PbsUtil.isEqual(getUnchinKbn(), target.getUnchinKbn())) {
				setUnchinKbnClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setUnchinKbnClass(STYLE_CLASS_NO_EDIT);
			}

			// 運送店CD
			if (!PbsUtil.isEqual(getUnsotenCd(), target.getUnsotenCd())) {
				setUnsotenCdClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setUnsotenCdClass(STYLE_CLASS_NO_EDIT);
			}

			// 小ロット出荷承認申請NO
			if (!PbsUtil.isEqual(getSyukaSyoninNo(), target.getSyukaSyoninNo())) {
				setSyukaSyoninNoClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setSyukaSyoninNoClass(STYLE_CLASS_NO_EDIT);
			}

			// 蔵CD (FK)
			if (!PbsUtil.isEqual(getKuraCd(), target.getKuraCd())) {
				setKuraCdClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setKuraCdClass(STYLE_CLASS_NO_EDIT);
			}

			// ミナシ日付
			if (!PbsUtil.isEqual(getMinasiDt(), target.getMinasiDt())) {
				setMinasiDtClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setMinasiDtClass(STYLE_CLASS_NO_EDIT);
			}

			// 荷受時間区分
			if (!PbsUtil.isEqual(getNiukeTimeKbn(), target.getNiukeTimeKbn())) {
				setNiukeTimeKbnClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setNiukeTimeKbnClass(STYLE_CLASS_NO_EDIT);
			}

			// 荷受時間_開始
			if (!PbsUtil.isEqual(getNiukeBiginTime(), target.getNiukeBiginTime())) {
				setNiukeBiginTimeClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setNiukeBiginTimeClass(STYLE_CLASS_NO_EDIT);
			}

			// 荷受時間_終了
			if (!PbsUtil.isEqual(getNiukeEndTime(), target.getNiukeEndTime())) {
				setNiukeEndTimeClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setNiukeEndTimeClass(STYLE_CLASS_NO_EDIT);
			}

			// 摘要区分 (01)
			if (!PbsUtil.isEqual(getTekiyoKbn1(), target.getTekiyoKbn1())) {
				setTekiyoKbn1Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTekiyoKbn1Class(STYLE_CLASS_NO_EDIT);
			}

			// 摘要内容 (01)
			if (!PbsUtil.isEqual(getTekiyoNm1(), target.getTekiyoNm1())) {
				setTekiyoNm1Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTekiyoNm1Class(STYLE_CLASS_NO_EDIT);
			}

			// 摘要区分 (02)
			if (!PbsUtil.isEqual(getTekiyoKbn2(), target.getTekiyoKbn2())) {
				setTekiyoKbn2Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTekiyoKbn2Class(STYLE_CLASS_NO_EDIT);
			}

			// 摘要内容 (02)
			if (!PbsUtil.isEqual(getTekiyoNm2(), target.getTekiyoNm2())) {
				setTekiyoNm2Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTekiyoNm2Class(STYLE_CLASS_NO_EDIT);
			}

			// 摘要区分 (03)
			if (!PbsUtil.isEqual(getTekiyoKbn3(), target.getTekiyoKbn3())) {
				setTekiyoKbn3Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTekiyoKbn3Class(STYLE_CLASS_NO_EDIT);
			}

			// 摘要内容 (03)
			if (!PbsUtil.isEqual(getTekiyoNm3(), target.getTekiyoNm3())) {
				setTekiyoNm3Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTekiyoNm3Class(STYLE_CLASS_NO_EDIT);
			}

			// 摘要区分 (04)
			if (!PbsUtil.isEqual(getTekiyoKbn4(), target.getTekiyoKbn4())) {
				setTekiyoKbn4Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTekiyoKbn4Class(STYLE_CLASS_NO_EDIT);
			}

			// 摘要内容 (04)
			if (!PbsUtil.isEqual(getTekiyoNm4(), target.getTekiyoNm4())) {
				setTekiyoNm4Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTekiyoNm4Class(STYLE_CLASS_NO_EDIT);
			}

			// 摘要区分 (05)
			if (!PbsUtil.isEqual(getTekiyoKbn5(), target.getTekiyoKbn5())) {
				setTekiyoKbn5Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTekiyoKbn5Class(STYLE_CLASS_NO_EDIT);
			}

			// 摘要内容 (05)
			if (!PbsUtil.isEqual(getTekiyoNm5(), target.getTekiyoNm5())) {
				setTekiyoNm5Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTekiyoNm5Class(STYLE_CLASS_NO_EDIT);
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
				,"ORS_TATESN_CD_CLASS"
				,"TANTOSYA_CD_CLASS"
				,"SENPO_HACYU_NO_CLASS"
				,"SYUKA_DT_CLASS"
				,"CHACUNI_YOTEI_DT_CLASS"
				,"SYUHANTEN_CD_CLASS"
				,"UNCHIN_KBN_CLASS"
				,"UNSOTEN_CD_CLASS"
				,"SYUKA_SYONIN_NO_CLASS"
				,"SYUKA_KBN_CLASS"
				,"KURA_CD_CLASS"
				,"MINASI_DT_CLASS"
				,"NIUKE_TIME_KBN_CLASS"
				,"NIUKE_BIGIN_TIME_CLASS"
				,"NIUKE_END_TIME_CLASS"
				,"TEKIYO_KBN1_CLASS"
				,"TEKIYO_NM1_CLASS"
				,"TEKIYO_KBN2_CLASS"
				,"TEKIYO_NM2_CLASS"
				,"TEKIYO_KBN3_CLASS"
				,"TEKIYO_NM3_CLASS"
				,"TEKIYO_KBN4_CLASS"
				,"TEKIYO_NM4_CLASS"
				,"TEKIYO_KBN5_CLASS"
				,"TEKIYO_NM5_CLASS"
				,"ATUKAI_KBN_CLASS"
				,"JYURYO_TOT_CLASS"
				,"SYUBETU_CD_CLASS"
				,"TOKUYAKUTEN_CD_CLASS"
				,"DEPO_CD_CLASS"
				,"NIJITEN_CD_CLASS"
				,"SANJITEN_CD_CLASS"
				,"SYUKA_SAKI_COUNTRY_CD_CLASS"
				,"JIS_CD_CLASS"
				,"UNCHIN_CNV_TANKA_CLASS"
				,"LOW_HAISO_LOT_CLASS"
				,"SYUKA_SURYO_BOX_CLASS"
				,"SYUKA_SURYO_SET_CLASS"
				,"SYUKA_KINGAKU_TOT_CLASS"
				,"SYUKA_YOURYO_TOT_CLASS"
				,"REBATE1_YOURYO_TOT_CLASS"
				,"REBATE2_YOURYO_TOT_CLASS"
				,"REBATE3_YOURYO_TOT_CLASS"
				,"REBATE4_YOURYO_TOT_CLASS"
				,"REBATE5_YOURYO_TOT_CLASS"
				,"REBATEO_YOURYO_TOT_CLASS"
				,"SOUKO_SYURUI_KBN_CLASS"
				,"DELIVERY_KBN_CLASS"
				,"TOUJITU_SYUKKA_KBN_CLASS"
				,"SYUKA_DT_CYUI_KBN_CLASS"
				,"NIUKE_FUKA_MON_CLASS"
				,"NIUKE_FUKA_TUE_CLASS"
				,"NIUKE_FUKA_WED_CLASS"
				,"NIUKE_FUKA_THU_CLASS"
				,"NIUKE_FUKA_FRI_CLASS"
				,"NIUKE_FUKA_SAT_CLASS"
				,"NIUKE_FUKA_SUN_CLASS"
				,"NIUKE_FUKA_HOL_CLASS"
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
	 * @param rec JuchuJuchuDataInRecord
	 * @return TRUE:主キー項目が重複している / FALSE:重複無し
	 */
	public boolean isDuplicated(JuchuJuchuDataInRecord rec){
		boolean ret = true;

		// 会社CD + 受注NO
		if (!PbsUtil.isEqualIgnoreZero(getKaisyaCd()+getJyucyuNo(), rec.getKaisyaCd()+rec.getJyucyuNo())) {
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
			,"ORS_TATESN_CD_CLASS"
			,"TANTOSYA_CD_CLASS"
			,"SENPO_HACYU_NO_CLASS"
			,"SYUKA_DT_CLASS"
			,"CHACUNI_YOTEI_DT_CLASS"
			,"SYUHANTEN_CD_CLASS"
			,"UNCHIN_KBN_CLASS"
			,"UNSOTEN_CD_CLASS"
			,"SYUKA_SYONIN_NO_CLASS"
			,"SYUKA_KBN_CLASS"
			,"KURA_CD_CLASS"
			,"MINASI_DT_CLASS"
			,"NIUKE_TIME_KBN_CLASS"
			,"NIUKE_BIGIN_TIME_CLASS"
			,"NIUKE_END_TIME_CLASS"
			,"TEKIYO_KBN1_CLASS"
			,"TEKIYO_NM1_CLASS"
			,"TEKIYO_KBN2_CLASS"
			,"TEKIYO_NM2_CLASS"
			,"TEKIYO_KBN3_CLASS"
			,"TEKIYO_NM3_CLASS"
			,"TEKIYO_KBN4_CLASS"
			,"TEKIYO_NM4_CLASS"
			,"TEKIYO_KBN5_CLASS"
			,"TEKIYO_NM5_CLASS"
			,"ATUKAI_KBN_CLASS"
			,"JYURYO_TOT_CLASS"
			,"SYUBETU_CD_CLASS"
			,"TOKUYAKUTEN_CD_CLASS"
			,"DEPO_CD_CLASS"
			,"NIJITEN_CD_CLASS"
			,"SANJITEN_CD_CLASS"
			,"SYUKA_SAKI_COUNTRY_CD_CLASS"
			,"JIS_CD_CLASS"
			,"UNCHIN_CNV_TANKA_CLASS"
			,"LOW_HAISO_LOT_CLASS"
			,"SYUKA_SURYO_BOX_CLASS"
			,"SYUKA_SURYO_SET_CLASS"
			,"SYUKA_KINGAKU_TOT_CLASS"
			,"SYUKA_YOURYO_TOT_CLASS"
			,"REBATE1_YOURYO_TOT_CLASS"
			,"REBATE2_YOURYO_TOT_CLASS"
			,"REBATE3_YOURYO_TOT_CLASS"
			,"REBATE4_YOURYO_TOT_CLASS"
			,"REBATE5_YOURYO_TOT_CLASS"
			,"REBATEO_YOURYO_TOT_CLASS"
			,"SOUKO_SYURUI_KBN_CLASS"
			,"DELIVERY_KBN_CLASS"
			,"TOUJITU_SYUKKA_KBN_CLASS"
			,"SYUKA_DT_CYUI_KBN_CLASS"
			,"NIUKE_FUKA_MON_CLASS"
			,"NIUKE_FUKA_TUE_CLASS"
			,"NIUKE_FUKA_WED_CLASS"
			,"NIUKE_FUKA_THU_CLASS"
			,"NIUKE_FUKA_FRI_CLASS"
			,"NIUKE_FUKA_SAT_CLASS"
			,"NIUKE_FUKA_SUN_CLASS"
			,"NIUKE_FUKA_HOL_CLASS"
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


	// マスター上の運送店情報
	/**
	 * 運送店CD（通常）を返します。
	 *
	 * @return 運送店CD（通常）
	 */
	public String getUnsotenCdTujyo() {
		return (String) get("UNSOTEN_CD_TUJYO");
	}

	/**
	 * 運送店CD（通常）をレコードに格納します。
	 *
	 * @param unsotenCdTujyo 運送店CD（通常）
	 */
	public void setUnsotenCdTujyo(String unsotenCdTujyo) {
		put("UNSOTEN_CD_TUJYO", unsotenCdTujyo);
	}


	/**
	 * 運送店名（通常）を返します。
	 *
	 * @return 運送店名（通常）
	 */
	public String getUnsotenNmTujyo() {
		return (String) get("UNSOTEN_NM_TUJYO");
	}

	/**
	 * 運送店名（通常）をレコードに格納します。
	 *
	 * @param unsotenNmTujyo 運送店名（通常）
	 */
	public void setUnsotenNmTujyo(String unsotenNmTujyo) {
		put("UNSOTEN_NM_TUJYO", unsotenNmTujyo);
	}


	/**
	 * 運送店CD（当日）を返します。
	 *
	 * @return 運送店CD（当日）
	 */
	public String getUnsotenCdToujitu() {
		return (String) get("UNSOTEN_CD_TOUJITU");
	}

	/**
	 * 運送店CD（当日）をレコードに格納します。
	 *
	 * @param unsotenCdToujitu 運送店CD（当日）
	 */
	public void setUnsotenCdToujitu(String unsotenCdToujitu) {
		put("UNSOTEN_CD_TOUJITU", unsotenCdToujitu);
	}


	/**
	 * 運送店名（当日）を返します。
	 *
	 * @return 運送店名（当日）
	 */
	public String getUnsotenNmToujitu() {
		return (String) get("UNSOTEN_NM_TOUJITU");
	}

	/**
	 * 運送店名（当日）をレコードに格納します。
	 *
	 * @param unsotenNmToujitu 運送店名（当日）
	 */
	public void setUnsotenNmToujitu(String unsotenNmToujitu) {
		put("UNSOTEN_NM_TOUJITU", unsotenNmToujitu);
	}


	/**
	 * 運送店CD（引取）を返します。
	 *
	 * @return 運送店CD（引取）
	 */
	public String getUnsotenCdHikitori() {
		return (String) get("UNSOTEN_CD_HIKITORI");
	}

	/**
	 * 運送店CD（引取）をレコードに格納します。
	 *
	 * @param unsotenCdHikitori 運送店CD（引取）
	 */
	public void setUnsotenCdHikitori(String unsotenCdHikitori) {
		put("UNSOTEN_CD_HIKITORI", unsotenCdHikitori);
	}


	/**
	 * 運送店名（引取）を返します。
	 *
	 * @return 運送店名（引取）
	 */
	public String getUnsotenNmHikitori() {
		return (String) get("UNSOTEN_NM_HIKITORI");
	}

	/**
	 * 運送店名（引取）をレコードに格納します。
	 *
	 * @param unsotenNmHikitori 運送店名（引取）
	 */
	public void setUnsotenNmHikitori(String unsotenNmHikitori) {
		put("UNSOTEN_NM_HIKITORI", unsotenNmHikitori);
	}


	// メッセージ表示用フラグ
	/**
	 * 単価変更フラグを返します。
	 *
	 * @return 単価変更フラグ
	 */
	public String getDiffHtankaFlg() {
		return (String) get("DIFF_HTANKA_FLG");
	}

	/**
	 * 単価変更フラグをレコードに格納します。
	 *
	 * @param diffHtankaFlg 単価変更フラグ
	 */
	public void setDiffHtankaFlg(String diffHtankaFlg) {
		put("DIFF_HTANKA_FLG", diffHtankaFlg);
	}


	/**
	 * 注意商品含むフラグを返します。
	 *
	 * @return 注意商品含むフラグ
	 */
	public String getDiffCautionItemFlg() {
		return (String) get("DIFF_CAUTION_ITEM_FLG");
	}

	/**
	 * 注意商品含むフラグをレコードに格納します。
	 *
	 * @param diffCautionItemFlg 注意商品含むフラグ
	 */
	public void setDiffCautionItemFlg(String diffCautionItemFlg) {
		put("DIFF_CAUTION_ITEM_FLG", diffCautionItemFlg);
	}


	/**
	 * 当日出荷可否フラグを返します。
	 *
	 * @return 当日出荷可否フラグ
	 */
	public String getDiffTojituSyukaFlg() {
		return (String) get("DIFF_TOJITU_SYUKA_FLG");
	}

	/**
	 * 当日出荷可否フラグをレコードに格納します。
	 *
	 * @param diffTojituSyukaFlg 当日出荷可否フラグ
	 */
	public void setDiffTojituSyukaFlg(String diffTojituSyukaFlg) {
		put("DIFF_TOJITU_SYUKA_FLG", diffTojituSyukaFlg);
	}


	/**
	 * 運送店運賃差異フラグを返します。
	 *
	 * @return 運送店運賃差異フラグ
	 */
	public String getDiffUnsotenUnchinFlg() {
		return (String) get("DIFF_UNSOTEN_UNCHIN_FLG");
	}

	/**
	 * 運送店運賃差異フラグをレコードに格納します。
	 *
	 * @param diffUnsotenUnchinFlg 運送店運賃差異フラグ
	 */
	public void setDiffUnsotenUnchinFlg(String diffUnsotenUnchinFlg) {
		put("DIFF_UNSOTEN_UNCHIN_FLG", diffUnsotenUnchinFlg);
	}


	/**
	 * 運送店運賃差異メッセージを返します。
	 *
	 * @return 運送店運賃差異メッセージ
	 */
	public String getDiffUnsotenUnchinMsg() {
		return (String) get("DIFF_UNSOTEN_UNCHIN_MSG");
	}

	/**
	 * 運送店運賃差異メッセージをレコードに格納します。
	 *
	 * @param diffUnsotenUnchinMsg 運送店運賃差異メッセージ
	 */
	public void setDiffUnsotenUnchinMsg(String diffUnsotenUnchinMsg) {
		put("DIFF_UNSOTEN_UNCHIN_MSG", diffUnsotenUnchinMsg);
	}


	/**
	 * 荷受曜日差異フラグを返します。
	 *
	 * @return 荷受曜日差異フラグ
	 */
	public String getDiffNiukeYoubiFlg() {
		return (String) get("DIFF_NIUKE_YOUBI_FLG");
	}

	/**
	 * 荷受曜日差異フラグをレコードに格納します。
	 *
	 * @param diffNiukeYoubiFlg 荷受曜日差異フラグ
	 */
	public void setDiffNiukeYoubiFlg(String diffNiukeYoubiFlg) {
		put("DIFF_NIUKE_YOUBI_FLG", diffNiukeYoubiFlg);
	}


	/**
	 * 荷受曜日差異メッセージを返します。
	 *
	 * @return 荷受曜日差異メッセージ
	 */
	public String getDiffNiukeYoubiMsg() {
		return (String) get("DIFF_NIUKE_YOUBI_MSG");
	}

	/**
	 * 荷受曜日差異メッセージをレコードに格納します。
	 *
	 * @param diffNiukeYoubiMsg 荷受曜日差異メッセージ
	 */
	public void setDiffNiukeYoubiMsg(String diffNiukeYoubiMsg) {
		put("DIFF_NIUKE_YOUBI_MSG", diffNiukeYoubiMsg);
	}


	/**
	 * 要冷配送可否（商品）フラグを返します。
	 *
	 * @return 要冷配送可否（商品）フラグ
	 */
	public String getDiffYoreiHaisoSyohinFlg() {
		return (String) get("DIFF_YOREI_HAISO_SYOHIN_FLG");
	}

	/**
	 * 要冷配送可否（商品）フラグをレコードに格納します。
	 *
	 * @param diffYoreiHaisoSyohinFlg 要冷配送可否（商品）フラグ
	 */
	public void setDiffYoreiHaisoSyohinFlg(String diffYoreiHaisoSyohinFlg) {
		put("DIFF_YOREI_HAISO_SYOHIN_FLG", diffYoreiHaisoSyohinFlg);
	}


	/**
	 * 要冷運賃商品差異フラグを返します。
	 *
	 * @return 要冷運賃商品差異フラグ
	 */
	public String getDiffYoreiUnchinSyohinFlg() {
		return (String) get("DIFF_YOREI_UNCHIN_SYOHIN_FLG");
	}

	/**
	 * 要冷運賃商品差異フラグをレコードに格納します。
	 *
	 * @param diffYoreiUnchinSyohinFlg 要冷運賃商品差異フラグ
	 */
	public void setDiffYoreiUnchinSyohinFlg(String diffYoreiUnchinSyohinFlg) {
		put("DIFF_YOREI_UNCHIN_SYOHIN_FLG", diffYoreiUnchinSyohinFlg);
	}


	/**
	 * 要冷配送可否（運賃）フラグを返します。
	 *
	 * @return 要冷配送可否（運賃）フラグ
	 */
	public String getDiffYoreiHaisoUnchinFlg() {
		return (String) get("DIFF_YOREI_HAISO_UNCHIN_FLG");
	}

	/**
	 * 要冷配送可否（運賃）フラグをレコードに格納します。
	 *
	 * @param diffYoreiHaisoUnchinFlg 要冷配送可否（運賃）フラグ
	 */
	public void setDiffYoreiHaisoUnchinFlg(String diffYoreiHaisoUnchinFlg) {
		put("DIFF_YOREI_HAISO_UNCHIN_FLG", diffYoreiHaisoUnchinFlg);
	}


	/**
	 * 在庫不足フラグを返します。
	 *
	 * @return 在庫不足フラグ
	 */
	public String getOverZaikoFlg() {
		return (String) get("OVER_ZAIKO_FLG");
	}

	/**
	 * 在庫不足フラグをレコードに格納します。
	 *
	 * @param overZaikoFlg 在庫不足フラグ
	 */
	public void setOverZaikoFlg(String overZaikoFlg) {
		put("OVER_ZAIKO_FLG", overZaikoFlg);
	}


	/**
	 * 受注可能金額超過フラグを返します。
	 *
	 * @return 受注可能金額超過フラグ
	 */
	public String getOverKingakuFlg() {
		return (String) get("OVER_KINGAKU_FLG");
	}

	/**
	 * 受注可能金額超過フラグをレコードに格納します。
	 *
	 * @param overKingakuFlg 受注可能金額超過フラグ
	 */
	public void setOverKingakuFlg(String overKingakuFlg) {
		put("OVER_KINGAKU_FLG", overKingakuFlg);
	}


	/**
	 * 保証金差異フラグを返します。
	 *
	 * @return 保証金差異フラグ
	 */
	public String getDiffTaruHosyoKinFlg() {
		return (String) get("DIFF_TARU_HOSYO_KIN_FLG");
	}

	/**
	 * 保証金差異フラグをレコードに格納します。
	 *
	 * @param diffTaruHosyoKinFlg 保証金差異フラグ
	 */
	public void setDiffTaruHosyoKinFlg(String diffTaruHosyoKinFlg) {
		put("DIFF_TARU_HOSYO_KIN_FLG", diffTaruHosyoKinFlg);
	}


	/**
	 * 手数料差異フラグを返します。
	 *
	 * @return 手数料差異フラグ
	 */
	public String getDiffTaruTesuryoFlg() {
		return (String) get("DIFF_TARU_TESURYO_FLG");
	}

	/**
	 * 手数料差異フラグをレコードに格納します。
	 *
	 * @param diffTaruTesuryoFlg 手数料差異フラグ
	 */
	public void setDiffTaruTesuryoFlg(String diffTaruTesuryoFlg) {
		put("DIFF_TARU_TESURYO_FLG", diffTaruTesuryoFlg);
	}


	// 販売単価変更可否の制御用
	/**
	 * 販売単価変更入力フラグを返します。
	 *
	 * @return 販売単価変更入力フラグ
	 */
	public String getHtankaChgInpFlg() {
		return (String) get("HTANKA_CHG_INP_FLG");
	}

	/**
	 * 販売単価変更入力フラグをレコードに格納します。
	 *
	 * @param htankaChgInpFlg 販売単価変更入力フラグ
	 */
	public void setHtankaChgInpFlg(String htankaChgInpFlg) {
		put("HTANKA_CHG_INP_FLG", htankaChgInpFlg);
	}


	// 与信情報
	/**
	 * 与信要注意区分を返します。
	 *
	 * @return 与信要注意区分
	 */
	public String getYosinCyuiKbn() {
		return (String) get("YOSIN_CYUI_KBN");
	}

	/**
	 * 与信要注意区分をレコードに格納します。
	 *
	 * @param yosinCyuiKbn 与信要注意区分
	 */
	public void setYosinCyuiKbn(String yosinCyuiKbn) {
		put("YOSIN_CYUI_KBN", yosinCyuiKbn);
	}


	/**
	 * 与信限度額を返します。
	 *
	 * @return 与信限度額
	 */
	public String getYosinGendoGaku() {
		return (String) get("YOSIN_GENDO_GAKU");
	}

	/**
	 * 与信限度額をレコードに格納します。
	 *
	 * @param yosinGendoGaku 与信限度額
	 */
	public void setYosinGendoGaku(String yosinGendoGaku) {
		put("YOSIN_GENDO_GAKU", yosinGendoGaku);
	}


	/**
	 * 売掛未入金額を返します。
	 *
	 * @return 売掛未入金額
	 */
	public String getUrikakeMinyukinGaku() {
		return (String) get("URIKAKE_MINYUKIN_GAKU");
	}

	/**
	 * 売掛未入金額をレコードに格納します。
	 *
	 * @param urikakeMinyukinGaku 売掛未入金額
	 */
	public void setUrikakeMinyukinGaku(String urikakeMinyukinGaku) {
		put("URIKAKE_MINYUKIN_GAKU", urikakeMinyukinGaku);
	}


	/**
	 * 受注可能金額計を返します。
	 *
	 * @return 受注可能金額計
	 */
	public String getJyucyuKanouKingakuKei() {
		return (String) get("JYUCYU_KANOU_KINGAKU_KEI");
	}

	/**
	 * 受注可能金額計をレコードに格納します。
	 *
	 * @param jyucyuKanouKingakuKei 受注可能金額計
	 */
	public void setJyucyuKanouKingakuKei(String jyucyuKanouKingakuKei) {
		put("JYUCYU_KANOU_KINGAKU_KEI", jyucyuKanouKingakuKei);
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
	 * 会社CD (FK)を返します。
	 *
	 * @return 会社CD (FK)
	 */
	public String getKaisyaCd() {
		return (String) get("KAISYA_CD");
	}

	/**
	 * 会社CD (FK)をレコードに格納します。
	 *
	 * @param kaisyaCd 会社CD (FK)
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
	 * 縦線CD (FK)を返します。
	 *
	 * @return 縦線CD (FK)
	 */
	public String getOrsTatesnCd() {
		return (String) get("ORS_TATESN_CD");
	}

	/**
	 * 縦線CD (FK)をレコードに格納します。
	 *
	 * @param orsTatesnCd 縦線CD (FK)
	 */
	public void setOrsTatesnCd(String orsTatesnCd) {
		put("ORS_TATESN_CD", orsTatesnCd);
	}


	/**
	 * 担当者CDを返します。
	 *
	 * @return 担当者CD
	 */
	public String getTantosyaCd() {
		return (String) get("TANTOSYA_CD");
	}

	/**
	 * 担当者CDをレコードに格納します。
	 *
	 * @param tantosyaCd 担当者CD
	 */
	public void setTantosyaCd(String tantosyaCd) {
		put("TANTOSYA_CD", tantosyaCd);
	}


	/**
	 * 先方発注NOを返します。
	 *
	 * @return 先方発注NO
	 */
	public String getSenpoHacyuNo() {
		return (String) get("SENPO_HACYU_NO");
	}

	/**
	 * 先方発注NOをレコードに格納します。
	 *
	 * @param senpoHacyuNo 先方発注NO
	 */
	public void setSenpoHacyuNo(String senpoHacyuNo) {
		put("SENPO_HACYU_NO", senpoHacyuNo);
	}


	/**
	 * 出荷日(売上伝票発行予定日)を返します。
	 *
	 * @return 出荷日(売上伝票発行予定日)
	 */
	public String getSyukaDt() {
		return (String) get("SYUKA_DT");
	}

	/**
	 * 出荷日(売上伝票発行予定日)をレコードに格納します。
	 *
	 * @param syukaDt 出荷日(売上伝票発行予定日)
	 */
	public void setSyukaDt(String syukaDt) {
		put("SYUKA_DT", syukaDt);
	}


	/**
	 * 着荷予定日を返します。
	 *
	 * @return 着荷予定日
	 */
	public String getChacuniYoteiDt() {
		return (String) get("CHACUNI_YOTEI_DT");
	}

	/**
	 * 着荷予定日をレコードに格納します。
	 *
	 * @param chacuniYoteiDt 着荷予定日
	 */
	public void setChacuniYoteiDt(String chacuniYoteiDt) {
		put("CHACUNI_YOTEI_DT", chacuniYoteiDt);
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
	 * 運賃区分を返します。
	 *
	 * @return 運賃区分
	 */
	public String getUnchinKbn() {
		return (String) get("UNCHIN_KBN");
	}

	/**
	 * 運賃区分をレコードに格納します。
	 *
	 * @param unchinKbn 運賃区分
	 */
	public void setUnchinKbn(String unchinKbn) {
		put("UNCHIN_KBN", unchinKbn);
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
	 * 小ロット出荷承認申請NOを返します。
	 *
	 * @return 小ロット出荷承認申請NO
	 */
	public String getSyukaSyoninNo() {
		return (String) get("SYUKA_SYONIN_NO");
	}

	/**
	 * 小ロット出荷承認申請NOをレコードに格納します。
	 *
	 * @param syukaSyoninNo 小ロット出荷承認申請NO
	 */
	public void setSyukaSyoninNo(String syukaSyoninNo) {
		put("SYUKA_SYONIN_NO", syukaSyoninNo);
	}


	/**
	 * 出荷区分を返します。
	 *
	 * @return 出荷区分
	 */
	public String getSyukaKbn() {
		return (String) get("SYUKA_KBN");
	}

	/**
	 * 出荷区分をレコードに格納します。
	 *
	 * @param syukaKbn 出荷区分
	 */
	public void setSyukaKbn(String syukaKbn) {
		put("SYUKA_KBN", syukaKbn);
	}


	/**
	 * 蔵CD (FK)を返します。
	 *
	 * @return 蔵CD (FK)
	 */
	public String getKuraCd() {
		return (String) get("KURA_CD");
	}

	/**
	 * 蔵CD (FK)をレコードに格納します。
	 *
	 * @param kuraCd 蔵CD (FK)
	 */
	public void setKuraCd(String kuraCd) {
		put("KURA_CD", kuraCd);
	}


	/**
	 * ミナシ日付を返します。
	 *
	 * @return ミナシ日付
	 */
	public String getMinasiDt() {
		return (String) get("MINASI_DT");
	}

	/**
	 * ミナシ日付をレコードに格納します。
	 *
	 * @param minasiDt ミナシ日付
	 */
	public void setMinasiDt(String minasiDt) {
		put("MINASI_DT", minasiDt);
	}


	/**
	 * 荷受時間区分を返します。
	 *
	 * @return 荷受時間区分
	 */
	public String getNiukeTimeKbn() {
		return (String) get("NIUKE_TIME_KBN");
	}

	/**
	 * 荷受時間区分をレコードに格納します。
	 *
	 * @param niukeTimeKbn 荷受時間区分
	 */
	public void setNiukeTimeKbn(String niukeTimeKbn) {
		put("NIUKE_TIME_KBN", niukeTimeKbn);
	}


	/**
	 * 荷受時間_開始を返します。
	 *
	 * @return 荷受時間_開始
	 */
	public String getNiukeBiginTime() {
		return (String) get("NIUKE_BIGIN_TIME");
	}

	/**
	 * 荷受時間_開始をレコードに格納します。
	 *
	 * @param niukeBiginTime 荷受時間_開始
	 */
	public void setNiukeBiginTime(String niukeBiginTime) {
		put("NIUKE_BIGIN_TIME", niukeBiginTime);
	}


	/**
	 * 荷受時間_終了を返します。
	 *
	 * @return 荷受時間_終了
	 */
	public String getNiukeEndTime() {
		return (String) get("NIUKE_END_TIME");
	}

	/**
	 * 荷受時間_終了をレコードに格納します。
	 *
	 * @param niukeEndTime 荷受時間_終了
	 */
	public void setNiukeEndTime(String niukeEndTime) {
		put("NIUKE_END_TIME", niukeEndTime);
	}


	/**
	 * 摘要区分 (01)を返します。
	 *
	 * @return 摘要区分 (01)
	 */
	public String getTekiyoKbn1() {
		return (String) get("TEKIYO_KBN1");
	}

	/**
	 * 摘要区分 (01)をレコードに格納します。
	 *
	 * @param tekiyoKbn1 摘要区分 (01)
	 */
	public void setTekiyoKbn1(String tekiyoKbn1) {
		put("TEKIYO_KBN1", tekiyoKbn1);
	}


	/**
	 * 摘要内容 (01)を返します。
	 *
	 * @return 摘要内容 (01)
	 */
	public String getTekiyoNm1() {
		return (String) get("TEKIYO_NM1");
	}

	/**
	 * 摘要内容 (01)をレコードに格納します。
	 *
	 * @param tekiyoNm1 摘要内容 (01)
	 */
	public void setTekiyoNm1(String tekiyoNm1) {
		put("TEKIYO_NM1", tekiyoNm1);
	}


	/**
	 * 摘要区分 (02)を返します。
	 *
	 * @return 摘要区分 (02)
	 */
	public String getTekiyoKbn2() {
		return (String) get("TEKIYO_KBN2");
	}

	/**
	 * 摘要区分 (02)をレコードに格納します。
	 *
	 * @param tekiyoKbn2 摘要区分 (02)
	 */
	public void setTekiyoKbn2(String tekiyoKbn2) {
		put("TEKIYO_KBN2", tekiyoKbn2);
	}


	/**
	 * 摘要内容 (02)を返します。
	 *
	 * @return 摘要内容 (02)
	 */
	public String getTekiyoNm2() {
		return (String) get("TEKIYO_NM2");
	}

	/**
	 * 摘要内容 (02)をレコードに格納します。
	 *
	 * @param tekiyoNm2 摘要内容 (02)
	 */
	public void setTekiyoNm2(String tekiyoNm2) {
		put("TEKIYO_NM2", tekiyoNm2);
	}


	/**
	 * 摘要区分 (03)を返します。
	 *
	 * @return 摘要区分 (03)
	 */
	public String getTekiyoKbn3() {
		return (String) get("TEKIYO_KBN3");
	}

	/**
	 * 摘要区分 (03)をレコードに格納します。
	 *
	 * @param tekiyoKbn3 摘要区分 (03)
	 */
	public void setTekiyoKbn3(String tekiyoKbn3) {
		put("TEKIYO_KBN3", tekiyoKbn3);
	}


	/**
	 * 摘要内容 (03)を返します。
	 *
	 * @return 摘要内容 (03)
	 */
	public String getTekiyoNm3() {
		return (String) get("TEKIYO_NM3");
	}

	/**
	 * 摘要内容 (03)をレコードに格納します。
	 *
	 * @param tekiyoNm3 摘要内容 (03)
	 */
	public void setTekiyoNm3(String tekiyoNm3) {
		put("TEKIYO_NM3", tekiyoNm3);
	}


	/**
	 * 摘要区分 (04)を返します。
	 *
	 * @return 摘要区分 (04)
	 */
	public String getTekiyoKbn4() {
		return (String) get("TEKIYO_KBN4");
	}

	/**
	 * 摘要区分 (04)をレコードに格納します。
	 *
	 * @param tekiyoKbn4 摘要区分 (04)
	 */
	public void setTekiyoKbn4(String tekiyoKbn4) {
		put("TEKIYO_KBN4", tekiyoKbn4);
	}


	/**
	 * 摘要内容 (04)を返します。
	 *
	 * @return 摘要内容 (04)
	 */
	public String getTekiyoNm4() {
		return (String) get("TEKIYO_NM4");
	}

	/**
	 * 摘要内容 (04)をレコードに格納します。
	 *
	 * @param tekiyoNm4 摘要内容 (04)
	 */
	public void setTekiyoNm4(String tekiyoNm4) {
		put("TEKIYO_NM4", tekiyoNm4);
	}


	/**
	 * 摘要区分 (05)を返します。
	 *
	 * @return 摘要区分 (05)
	 */
	public String getTekiyoKbn5() {
		return (String) get("TEKIYO_KBN5");
	}

	/**
	 * 摘要区分 (05)をレコードに格納します。
	 *
	 * @param tekiyoKbn5 摘要区分 (05)
	 */
	public void setTekiyoKbn5(String tekiyoKbn5) {
		put("TEKIYO_KBN5", tekiyoKbn5);
	}


	/**
	 * 摘要内容 (05)を返します。
	 *
	 * @return 摘要内容 (05)
	 */
	public String getTekiyoNm5() {
		return (String) get("TEKIYO_NM5");
	}

	/**
	 * 摘要内容 (05)をレコードに格納します。
	 *
	 * @param tekiyoNm5 摘要内容 (05)
	 */
	public void setTekiyoNm5(String tekiyoNm5) {
		put("TEKIYO_NM5", tekiyoNm5);
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
	 * データ種別CDを返します。
	 *
	 * @return データ種別CD
	 */
	public String getSyubetuCd() {
		return (String) get("SYUBETU_CD");
	}

	/**
	 * データ種別CDをレコードに格納します。
	 *
	 * @param syubetuCd データ種別CD
	 */
	public void setSyubetuCd(String syubetuCd) {
		put("SYUBETU_CD", syubetuCd);
	}


	/**
	 * 特約店CDを返します。
	 *
	 * @return 特約店CD
	 */
	public String getTokuyakutenCd() {
		return (String) get("TOKUYAKUTEN_CD");
	}

	/**
	 * 特約店CDをレコードに格納します。
	 *
	 * @param tokuyakutenCd 特約店CD
	 */
	public void setTokuyakutenCd(String tokuyakutenCd) {
		put("TOKUYAKUTEN_CD", tokuyakutenCd);
	}


	/**
	 * デポCDを返します。
	 *
	 * @return デポCD
	 */
	public String getDepoCd() {
		return (String) get("DEPO_CD");
	}

	/**
	 * デポCDをレコードに格納します。
	 *
	 * @param depoCd デポCD
	 */
	public void setDepoCd(String depoCd) {
		put("DEPO_CD", depoCd);
	}


	/**
	 * 二次店CDを返します。
	 *
	 * @return 二次店CD
	 */
	public String getNijitenCd() {
		return (String) get("NIJITEN_CD");
	}

	/**
	 * 二次店CDをレコードに格納します。
	 *
	 * @param nijitenCd 二次店CD
	 */
	public void setNijitenCd(String nijitenCd) {
		put("NIJITEN_CD", nijitenCd);
	}


	/**
	 * 三次店CDを返します。
	 *
	 * @return 三次店CD
	 */
	public String getSanjitenCd() {
		return (String) get("SANJITEN_CD");
	}

	/**
	 * 三次店CDをレコードに格納します。
	 *
	 * @param sanjitenCd 三次店CD
	 */
	public void setSanjitenCd(String sanjitenCd) {
		put("SANJITEN_CD", sanjitenCd);
	}


	/**
	 * 出荷先国CDを返します。
	 *
	 * @return 出荷先国CD
	 */
	public String getSyukaSakiCountryCd() {
		return (String) get("SYUKA_SAKI_COUNTRY_CD");
	}

	/**
	 * 出荷先国CDをレコードに格納します。
	 *
	 * @param syukaSakiCountryCd 出荷先国CD
	 */
	public void setSyukaSakiCountryCd(String syukaSakiCountryCd) {
		put("SYUKA_SAKI_COUNTRY_CD", syukaSakiCountryCd);
	}


	/**
	 * JISCDを返します。
	 *
	 * @return JISCD
	 */
	public String getJisCd() {
		return (String) get("JIS_CD");
	}

	/**
	 * JISCDをレコードに格納します。
	 *
	 * @param jisCd JISCD
	 */
	public void setJisCd(String jisCd) {
		put("JIS_CD", jisCd);
	}


	/**
	 * 引取運賃換算単価を返します。
	 *
	 * @return 引取運賃換算単価
	 */
	public String getUnchinCnvTanka() {
		return (String) get("UNCHIN_CNV_TANKA");
	}

	/**
	 * 引取運賃換算単価をレコードに格納します。
	 *
	 * @param unchinCnvTanka 引取運賃換算単価
	 */
	public void setUnchinCnvTanka(String unchinCnvTanka) {
		put("UNCHIN_CNV_TANKA", unchinCnvTanka);
	}


	/**
	 * 最低配送ロット数を返します。
	 *
	 * @return 最低配送ロット数
	 */
	public String getLowHaisoLot() {
		return (String) get("LOW_HAISO_LOT");
	}

	/**
	 * 最低配送ロット数をレコードに格納します。
	 *
	 * @param lowHaisoLot 最低配送ロット数
	 */
	public void setLowHaisoLot(String lowHaisoLot) {
		put("LOW_HAISO_LOT", lowHaisoLot);
	}


	/**
	 * 出荷数量計_箱数を返します。
	 *
	 * @return 出荷数量計_箱数
	 */
	public String getSyukaSuryoBox() {
		return (String) get("SYUKA_SURYO_BOX");
	}

	/**
	 * 出荷数量計_箱数をレコードに格納します。
	 *
	 * @param syukaSuryoBox 出荷数量計_箱数
	 */
	public void setSyukaSuryoBox(String syukaSuryoBox) {
		put("SYUKA_SURYO_BOX", syukaSuryoBox);
	}


	/**
	 * 出荷数量計_セット数を返します。
	 *
	 * @return 出荷数量計_セット数
	 */
	public String getSyukaSuryoSet() {
		return (String) get("SYUKA_SURYO_SET");
	}

	/**
	 * 出荷数量計_セット数をレコードに格納します。
	 *
	 * @param syukaSuryoSet 出荷数量計_セット数
	 */
	public void setSyukaSuryoSet(String syukaSuryoSet) {
		put("SYUKA_SURYO_SET", syukaSuryoSet);
	}


	/**
	 * 出荷金額計を返します。
	 *
	 * @return 出荷金額計
	 */
	public String getSyukaKingakuTot() {
		return (String) get("SYUKA_KINGAKU_TOT");
	}

	/**
	 * 出荷金額計をレコードに格納します。
	 *
	 * @param syukaKingakuTot 出荷金額計
	 */
	public void setSyukaKingakuTot(String syukaKingakuTot) {
		put("SYUKA_KINGAKU_TOT", syukaKingakuTot);
	}


	/**
	 * 容量計（L）_出荷容量計を返します。
	 *
	 * @return 容量計（L）_出荷容量計
	 */
	public String getSyukaYouryoTot() {
		return (String) get("SYUKA_YOURYO_TOT");
	}

	/**
	 * 容量計（L）_出荷容量計をレコードに格納します。
	 *
	 * @param syukaYouryoTot 容量計（L）_出荷容量計
	 */
	public void setSyukaYouryoTot(String syukaYouryoTot) {
		put("SYUKA_YOURYO_TOT", syukaYouryoTot);
	}


	/**
	 * 容量計（L）_リベートⅠ類対象容量計を返します。
	 *
	 * @return 容量計（L）_リベートⅠ類対象容量計
	 */
	public String getRebate1YouryoTot() {
		return (String) get("REBATE1_YOURYO_TOT");
	}

	/**
	 * 容量計（L）_リベートⅠ類対象容量計をレコードに格納します。
	 *
	 * @param rebate1YouryoTot 容量計（L）_リベートⅠ類対象容量計
	 */
	public void setRebate1YouryoTot(String rebate1YouryoTot) {
		put("REBATE1_YOURYO_TOT", rebate1YouryoTot);
	}


	/**
	 * 容量計（L）_リベートⅡ類対象容量計を返します。
	 *
	 * @return 容量計（L）_リベートⅡ類対象容量計
	 */
	public String getRebate2YouryoTot() {
		return (String) get("REBATE2_YOURYO_TOT");
	}

	/**
	 * 容量計（L）_リベートⅡ類対象容量計をレコードに格納します。
	 *
	 * @param rebate2YouryoTot 容量計（L）_リベートⅡ類対象容量計
	 */
	public void setRebate2YouryoTot(String rebate2YouryoTot) {
		put("REBATE2_YOURYO_TOT", rebate2YouryoTot);
	}


	/**
	 * 容量計（L）_リベートⅢ類対象容量計を返します。
	 *
	 * @return 容量計（L）_リベートⅢ類対象容量計
	 */
	public String getRebate3YouryoTot() {
		return (String) get("REBATE3_YOURYO_TOT");
	}

	/**
	 * 容量計（L）_リベートⅢ類対象容量計をレコードに格納します。
	 *
	 * @param rebate3YouryoTot 容量計（L）_リベートⅢ類対象容量計
	 */
	public void setRebate3YouryoTot(String rebate3YouryoTot) {
		put("REBATE3_YOURYO_TOT", rebate3YouryoTot);
	}


	/**
	 * 容量計（L）_リベートⅣ類対象容量計を返します。
	 *
	 * @return 容量計（L）_リベートⅣ類対象容量計
	 */
	public String getRebate4YouryoTot() {
		return (String) get("REBATE4_YOURYO_TOT");
	}

	/**
	 * 容量計（L）_リベートⅣ類対象容量計をレコードに格納します。
	 *
	 * @param rebate4YouryoTot 容量計（L）_リベートⅣ類対象容量計
	 */
	public void setRebate4YouryoTot(String rebate4YouryoTot) {
		put("REBATE4_YOURYO_TOT", rebate4YouryoTot);
	}


	/**
	 * 容量計（L）_リベートⅤ類対象容量計を返します。
	 *
	 * @return 容量計（L）_リベートⅤ類対象容量計
	 */
	public String getRebate5YouryoTot() {
		return (String) get("REBATE5_YOURYO_TOT");
	}

	/**
	 * 容量計（L）_リベートⅤ類対象容量計をレコードに格納します。
	 *
	 * @param rebate5YouryoTot 容量計（L）_リベートⅤ類対象容量計
	 */
	public void setRebate5YouryoTot(String rebate5YouryoTot) {
		put("REBATE5_YOURYO_TOT", rebate5YouryoTot);
	}


	/**
	 * 容量計（L）_リベート対象外容量計を返します。
	 *
	 * @return 容量計（L）_リベート対象外容量計
	 */
	public String getRebateoYouryoTot() {
		return (String) get("REBATEO_YOURYO_TOT");
	}

	/**
	 * 容量計（L）_リベート対象外容量計をレコードに格納します。
	 *
	 * @param rebateoYouryoTot 容量計（L）_リベート対象外容量計
	 */
	public void setRebateoYouryoTot(String rebateoYouryoTot) {
		put("REBATEO_YOURYO_TOT", rebateoYouryoTot);
	}


	/**
	 * SDN受注ヘッダーエラー区分を返します。
	 *
	 * @return SDN受注ヘッダーエラー区分
	 */
	public String getSdnHderrKbn() {
		return (String) get("SDN_HDERR_KBN");
	}

	/**
	 * SDN受注ヘッダーエラー区分をレコードに格納します。
	 *
	 * @param sdnHderrKbn SDN受注ヘッダーエラー区分
	 */
	public void setSdnHderrKbn(String sdnHderrKbn) {
		put("SDN_HDERR_KBN", sdnHderrKbn);
	}


	/**
	 * 積荷伝票NO (FK)を返します。
	 *
	 * @return 積荷伝票NO (FK)
	 */
	public String getTumidenNo() {
		return (String) get("TUMIDEN_NO");
	}

	/**
	 * 積荷伝票NO (FK)をレコードに格納します。
	 *
	 * @param tumidenNo 積荷伝票NO (FK)
	 */
	public void setTumidenNo(String tumidenNo) {
		put("TUMIDEN_NO", tumidenNo);
	}


	/**
	 * 積荷累積対象区分を返します。
	 *
	 * @return 積荷累積対象区分
	 */
	public String getTumidenSmKbn() {
		return (String) get("TUMIDEN_SM_KBN");
	}

	/**
	 * 積荷累積対象区分をレコードに格納します。
	 *
	 * @param tumidenSmKbn 積荷累積対象区分
	 */
	public void setTumidenSmKbn(String tumidenSmKbn) {
		put("TUMIDEN_SM_KBN", tumidenSmKbn);
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
	 * 担当者名を返します。
	 *
	 * @return 担当者名
	 */
	public String getTantosyaNm() {
		return (String) get("TANTOSYA_NM");
	}

	/**
	 * 担当者名をレコードに格納します。
	 *
	 * @param tantosyaNm 担当者名
	 */
	public void setTantosyaNm(String tantosyaNm) {
		put("TANTOSYA_NM", tantosyaNm);
	}


	/**
	 * 特約店名を返します。
	 *
	 * @return 特約店名
	 */
	public String getTokuyakutenNm() {
		return (String) get("TOKUYAKUTEN_NM");
	}

	/**
	 * 特約店名をレコードに格納します。
	 *
	 * @param tokuyakutenNm 特約店名
	 */
	public void setTokuyakutenNm(String tokuyakutenNm) {
		put("TOKUYAKUTEN_NM", tokuyakutenNm);
	}


	/**
	 * デポ名を返します。
	 *
	 * @return デポ名
	 */
	public String getDepoNm() {
		return (String) get("DEPO_NM");
	}

	/**
	 * デポ名をレコードに格納します。
	 *
	 * @param depoNm デポ名
	 */
	public void setDepoNm(String depoNm) {
		put("DEPO_NM", depoNm);
	}


	/**
	 * 二次店名を返します。
	 *
	 * @return 二次店名
	 */
	public String getNijitenNm() {
		return (String) get("NIJITEN_NM");
	}

	/**
	 * 二次店名をレコードに格納します。
	 *
	 * @param nijitenNm 二次店名
	 */
	public void setNijitenNm(String nijitenNm) {
		put("NIJITEN_NM", nijitenNm);
	}


	/**
	 * 三次店名を返します。
	 *
	 * @return 三次店名
	 */
	public String getSanjitenNm() {
		return (String) get("SANJITEN_NM");
	}

	/**
	 * 三次店名をレコードに格納します。
	 *
	 * @param sanjitenNm 三次店名
	 */
	public void setSanjitenNm(String sanjitenNm) {
		put("SANJITEN_NM", sanjitenNm);
	}


	/**
	 * 入力者CDを返します。
	 *
	 * @return 入力者CD
	 */
	public String getNyuryokusyaCd() {
		return (String) get("NYURYOKUSYA_CD");
	}

	/**
	 * 入力者CDをレコードに格納します。
	 *
	 * @param nyuryokusyaCd 入力者CD
	 */
	public void setNyuryokusyaCd(String nyuryokusyaCd) {
		put("NYURYOKUSYA_CD", nyuryokusyaCd);
	}


	/**
	 * 入力者名を返します。
	 *
	 * @return 入力者名
	 */
	public String getNyuryokusyaNm() {
		return (String) get("NYURYOKUSYA_NM");
	}

	/**
	 * 入力者名をレコードに格納します。
	 *
	 * @param nyuryokusyaNm 入力者名
	 */
	public void setNyuryokusyaNm(String nyuryokusyaNm) {
		put("NYURYOKUSYA_NM", nyuryokusyaNm);
	}


	/**
	 * 都道府県CDを返します。
	 *
	 * @return 都道府県CD
	 */
	public String getTodofuknCd() {
		return (String) get("TODOFUKN_CD");
	}

	/**
	 * 都道府県CDをレコードに格納します。
	 *
	 * @param todofuknCd 都道府県CD
	 */
	public void setTodofuknCd(String todofuknCd) {
		put("TODOFUKN_CD", todofuknCd);
	}


	/**
	 * 倉庫種類区分を返します。
	 *
	 * @return 倉庫種類区分
	 */
	public String getSoukoSyuruiKbn() {
		return (String) get("SOUKO_SYURUI_KBN");
	}

	/**
	 * 倉庫種類区分をレコードに格納します。
	 *
	 * @param soukoSyuruiKbn 倉庫種類区分
	 */
	public void setSoukoSyuruiKbn(String soukoSyuruiKbn) {
		put("SOUKO_SYURUI_KBN", soukoSyuruiKbn);
	}


	/**
	 * 倉入・直送区分を返します。
	 *
	 * @return 倉入・直送区分
	 */
	public String getDeliveryKbn() {
		return (String) get("DELIVERY_KBN");
	}

	/**
	 * 倉入・直送区分をレコードに格納します。
	 *
	 * @param deliveryKbn 倉入・直送区分
	 */
	public void setDeliveryKbn(String deliveryKbn) {
		put("DELIVERY_KBN", deliveryKbn);
	}


	/**
	 * 当日出荷可能区分を返します。
	 *
	 * @return 当日出荷可能区分
	 */
	public String getToujituSyukkaKbn() {
		return (String) get("TOUJITU_SYUKKA_KBN");
	}

	/**
	 * 当日出荷可能区分をレコードに格納します。
	 *
	 * @param toujituSyukkaKbn 当日出荷可能区分
	 */
	public void setToujituSyukkaKbn(String toujituSyukkaKbn) {
		put("TOUJITU_SYUKKA_KBN", toujituSyukkaKbn);
	}


	/**
	 * 出荷日注意区分を返します。
	 *
	 * @return 出荷日注意区分
	 */
	public String getSyukaDtCyuiKbn() {
		return (String) get("SYUKA_DT_CYUI_KBN");
	}

	/**
	 * 出荷日注意区分をレコードに格納します。
	 *
	 * @param syukaDtCyuiKbn 出荷日注意区分
	 */
	public void setSyukaDtCyuiKbn(String syukaDtCyuiKbn) {
		put("SYUKA_DT_CYUI_KBN", syukaDtCyuiKbn);
	}


	/**
	 * 荷受不可曜日_月を返します。
	 *
	 * @return 荷受不可曜日_月
	 */
	public String getNiukeFukaMon() {
		return (String) get("NIUKE_FUKA_MON");
	}

	/**
	 * 荷受不可曜日_月をレコードに格納します。
	 *
	 * @param niukeFukaMon 荷受不可曜日_月
	 */
	public void setNiukeFukaMon(String niukeFukaMon) {
		put("NIUKE_FUKA_MON", niukeFukaMon);
	}


	/**
	 * 荷受不可曜日_火を返します。
	 *
	 * @return 荷受不可曜日_火
	 */
	public String getNiukeFukaTue() {
		return (String) get("NIUKE_FUKA_TUE");
	}

	/**
	 * 荷受不可曜日_火をレコードに格納します。
	 *
	 * @param niukeFukaTue 荷受不可曜日_火
	 */
	public void setNiukeFukaTue(String niukeFukaTue) {
		put("NIUKE_FUKA_TUE", niukeFukaTue);
	}


	/**
	 * 荷受不可曜日_水を返します。
	 *
	 * @return 荷受不可曜日_水
	 */
	public String getNiukeFukaWed() {
		return (String) get("NIUKE_FUKA_WED");
	}

	/**
	 * 荷受不可曜日_水をレコードに格納します。
	 *
	 * @param niukeFukaWed 荷受不可曜日_水
	 */
	public void setNiukeFukaWed(String niukeFukaWed) {
		put("NIUKE_FUKA_WED", niukeFukaWed);
	}


	/**
	 * 荷受不可曜日_木を返します。
	 *
	 * @return 荷受不可曜日_木
	 */
	public String getNiukeFukaThu() {
		return (String) get("NIUKE_FUKA_THU");
	}

	/**
	 * 荷受不可曜日_木をレコードに格納します。
	 *
	 * @param niukeFukaThu 荷受不可曜日_木
	 */
	public void setNiukeFukaThu(String niukeFukaThu) {
		put("NIUKE_FUKA_THU", niukeFukaThu);
	}


	/**
	 * 荷受不可曜日_金を返します。
	 *
	 * @return 荷受不可曜日_金
	 */
	public String getNiukeFukaFri() {
		return (String) get("NIUKE_FUKA_FRI");
	}

	/**
	 * 荷受不可曜日_金をレコードに格納します。
	 *
	 * @param niukeFukaFri 荷受不可曜日_金
	 */
	public void setNiukeFukaFri(String niukeFukaFri) {
		put("NIUKE_FUKA_FRI", niukeFukaFri);
	}


	/**
	 * 荷受不可曜日_土を返します。
	 *
	 * @return 荷受不可曜日_土
	 */
	public String getNiukeFukaSat() {
		return (String) get("NIUKE_FUKA_SAT");
	}

	/**
	 * 荷受不可曜日_土をレコードに格納します。
	 *
	 * @param niukeFukaSat 荷受不可曜日_土
	 */
	public void setNiukeFukaSat(String niukeFukaSat) {
		put("NIUKE_FUKA_SAT", niukeFukaSat);
	}


	/**
	 * 荷受不可曜日_日を返します。
	 *
	 * @return 荷受不可曜日_日
	 */
	public String getNiukeFukaSun() {
		return (String) get("NIUKE_FUKA_SUN");
	}

	/**
	 * 荷受不可曜日_日をレコードに格納します。
	 *
	 * @param niukeFukaSun 荷受不可曜日_日
	 */
	public void setNiukeFukaSun(String niukeFukaSun) {
		put("NIUKE_FUKA_SUN", niukeFukaSun);
	}


	/**
	 * 荷受不可曜日_祝を返します。
	 *
	 * @return 荷受不可曜日_祝
	 */
	public String getNiukeFukaHol() {
		return (String) get("NIUKE_FUKA_HOL");
	}

	/**
	 * 荷受不可曜日_祝をレコードに格納します。
	 *
	 * @param niukeFukaHol 荷受不可曜日_祝
	 */
	public void setNiukeFukaHol(String niukeFukaHol) {
		put("NIUKE_FUKA_HOL", niukeFukaHol);
	}


	/**
	 * 請求先名称(正) 1/2を返します。
	 *
	 * @return 請求先名称(正) 1/2
	 */
	public String getSeikyusakiNm01() {
		return (String) get("SEIKYUSAKI_NM01");
	}

	/**
	 * 請求先名称(正) 1/2をレコードに格納します。
	 *
	 * @param seikyusakiNm01 請求先名称(正) 1/2
	 */
	public void setSeikyusakiNm01(String seikyusakiNm01) {
		put("SEIKYUSAKI_NM01", seikyusakiNm01);
	}


	/**
	 * 請求先名称(正) 2/2を返します。
	 *
	 * @return 請求先名称(正) 2/2
	 */
	public String getSeikyusakiNm02() {
		return (String) get("SEIKYUSAKI_NM02");
	}

	/**
	 * 請求先名称(正) 2/2をレコードに格納します。
	 *
	 * @param seikyusakiNm02 請求先名称(正) 2/2
	 */
	public void setSeikyusakiNm02(String seikyusakiNm02) {
		put("SEIKYUSAKI_NM02", seikyusakiNm02);
	}


	/**
	 * 請求明細グループCD_特約店CDを返します。
	 *
	 * @return 請求明細グループCD_特約店CD
	 */
	public String getSmgpTokuyakutenCd() {
		return (String) get("SMGP_TOKUYAKUTEN_CD");
	}

	/**
	 * 請求明細グループCD_特約店CDをレコードに格納します。
	 *
	 * @param smgpTokuyakutenCd 請求明細グループCD_特約店CD
	 */
	public void setSmgpTokuyakutenCd(String smgpTokuyakutenCd) {
		put("SMGP_TOKUYAKUTEN_CD", smgpTokuyakutenCd);
	}


	/**
	 * 請求明細グループCD_デポCDを返します。
	 *
	 * @return 請求明細グループCD_デポCD
	 */
	public String getSmgpDepoCd() {
		return (String) get("SMGP_DEPO_CD");
	}

	/**
	 * 請求明細グループCD_デポCDをレコードに格納します。
	 *
	 * @param smgpDepoCd 請求明細グループCD_デポCD
	 */
	public void setSmgpDepoCd(String smgpDepoCd) {
		put("SMGP_DEPO_CD", smgpDepoCd);
	}


	/**
	 * 請求明細グループCD_二次店CDを返します。
	 *
	 * @return 請求明細グループCD_二次店CD
	 */
	public String getSmgpNijitenCd() {
		return (String) get("SMGP_NIJITEN_CD");
	}

	/**
	 * 請求明細グループCD_二次店CDをレコードに格納します。
	 *
	 * @param smgpNijitenCd 請求明細グループCD_二次店CD
	 */
	public void setSmgpNijitenCd(String smgpNijitenCd) {
		put("SMGP_NIJITEN_CD", smgpNijitenCd);
	}


	/**
	 * 請求明細グループCD_三次店CDを返します。
	 *
	 * @return 請求明細グループCD_三次店CD
	 */
	public String getSmgpSanjitenCd() {
		return (String) get("SMGP_SANJITEN_CD");
	}

	/**
	 * 請求明細グループCD_三次店CDをレコードに格納します。
	 *
	 * @param smgpSanjitenCd 請求明細グループCD_三次店CD
	 */
	public void setSmgpSanjitenCd(String smgpSanjitenCd) {
		put("SMGP_SANJITEN_CD", smgpSanjitenCd);
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
	 * 会社CD (FK)のエラー、警告表示フラグを返します。
	 *
	 * @return 会社CD (FK)のエラー、警告表示フラグ
	 */
	public String getKaisyaCdClass() {
		return (String) get("KAISYA_CD_CLASS");
	}

	/**
	 * 会社CD (FK)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param kaisyaCdClass 会社CD (FK)のエラー、警告表示フラグ
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
	 * 縦線CD (FK)のエラー、警告表示フラグを返します。
	 *
	 * @return 縦線CD (FK)のエラー、警告表示フラグ
	 */
	public String getOrsTatesnCdClass() {
		return (String) get("ORS_TATESN_CD_CLASS");
	}

	/**
	 * 縦線CD (FK)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param orsTatesnCdClass 縦線CD (FK)のエラー、警告表示フラグ
	 */
	public void setOrsTatesnCdClass(String orsTatesnCdClass) {
		put("ORS_TATESN_CD_CLASS", orsTatesnCdClass);
	}


	/**
	 * 担当者CDのエラー、警告表示フラグを返します。
	 *
	 * @return 担当者CDのエラー、警告表示フラグ
	 */
	public String getTantosyaCdClass() {
		return (String) get("TANTOSYA_CD_CLASS");
	}

	/**
	 * 担当者CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tantosyaCdClass 担当者CDのエラー、警告表示フラグ
	 */
	public void setTantosyaCdClass(String tantosyaCdClass) {
		put("TANTOSYA_CD_CLASS", tantosyaCdClass);
	}


	/**
	 * 先方発注NOのエラー、警告表示フラグを返します。
	 *
	 * @return 先方発注NOのエラー、警告表示フラグ
	 */
	public String getSenpoHacyuNoClass() {
		return (String) get("SENPO_HACYU_NO_CLASS");
	}

	/**
	 * 先方発注NOのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param senpoHacyuNoClass 先方発注NOのエラー、警告表示フラグ
	 */
	public void setSenpoHacyuNoClass(String senpoHacyuNoClass) {
		put("SENPO_HACYU_NO_CLASS", senpoHacyuNoClass);
	}


	/**
	 * 出荷日(売上伝票発行予定日)のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷日(売上伝票発行予定日)のエラー、警告表示フラグ
	 */
	public String getSyukaDtClass() {
		return (String) get("SYUKA_DT_CLASS");
	}

	/**
	 * 出荷日(売上伝票発行予定日)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaDtClass 出荷日(売上伝票発行予定日)のエラー、警告表示フラグ
	 */
	public void setSyukaDtClass(String syukaDtClass) {
		put("SYUKA_DT_CLASS", syukaDtClass);
	}


	/**
	 * 着荷予定日のエラー、警告表示フラグを返します。
	 *
	 * @return 着荷予定日のエラー、警告表示フラグ
	 */
	public String getChacuniYoteiDtClass() {
		return (String) get("CHACUNI_YOTEI_DT_CLASS");
	}

	/**
	 * 着荷予定日のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param chacuniYoteiDtClass 着荷予定日のエラー、警告表示フラグ
	 */
	public void setChacuniYoteiDtClass(String chacuniYoteiDtClass) {
		put("CHACUNI_YOTEI_DT_CLASS", chacuniYoteiDtClass);
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
	 * 運賃区分のエラー、警告表示フラグを返します。
	 *
	 * @return 運賃区分のエラー、警告表示フラグ
	 */
	public String getUnchinKbnClass() {
		return (String) get("UNCHIN_KBN_CLASS");
	}

	/**
	 * 運賃区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param unchinKbnClass 運賃区分のエラー、警告表示フラグ
	 */
	public void setUnchinKbnClass(String unchinKbnClass) {
		put("UNCHIN_KBN_CLASS", unchinKbnClass);
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
	 * 小ロット出荷承認申請NOのエラー、警告表示フラグを返します。
	 *
	 * @return 小ロット出荷承認申請NOのエラー、警告表示フラグ
	 */
	public String getSyukaSyoninNoClass() {
		return (String) get("SYUKA_SYONIN_NO_CLASS");
	}

	/**
	 * 小ロット出荷承認申請NOのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaSyoninNoClass 小ロット出荷承認申請NOのエラー、警告表示フラグ
	 */
	public void setSyukaSyoninNoClass(String syukaSyoninNoClass) {
		put("SYUKA_SYONIN_NO_CLASS", syukaSyoninNoClass);
	}


	/**
	 * 出荷区分のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷区分のエラー、警告表示フラグ
	 */
	public String getSyukaKbnClass() {
		return (String) get("SYUKA_KBN_CLASS");
	}

	/**
	 * 出荷区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaKbnClass 出荷区分のエラー、警告表示フラグ
	 */
	public void setSyukaKbnClass(String syukaKbnClass) {
		put("SYUKA_KBN_CLASS", syukaKbnClass);
	}


	/**
	 * 蔵CD (FK)のエラー、警告表示フラグを返します。
	 *
	 * @return 蔵CD (FK)のエラー、警告表示フラグ
	 */
	public String getKuraCdClass() {
		return (String) get("KURA_CD_CLASS");
	}

	/**
	 * 蔵CD (FK)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param kuraCdClass 蔵CD (FK)のエラー、警告表示フラグ
	 */
	public void setKuraCdClass(String kuraCdClass) {
		put("KURA_CD_CLASS", kuraCdClass);
	}


	/**
	 * ミナシ日付のエラー、警告表示フラグを返します。
	 *
	 * @return ミナシ日付のエラー、警告表示フラグ
	 */
	public String getMinasiDtClass() {
		return (String) get("MINASI_DT_CLASS");
	}

	/**
	 * ミナシ日付のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param minasiDtClass ミナシ日付のエラー、警告表示フラグ
	 */
	public void setMinasiDtClass(String minasiDtClass) {
		put("MINASI_DT_CLASS", minasiDtClass);
	}


	/**
	 * 荷受時間区分のエラー、警告表示フラグを返します。
	 *
	 * @return 荷受時間区分のエラー、警告表示フラグ
	 */
	public String getNiukeTimeKbnClass() {
		return (String) get("NIUKE_TIME_KBN_CLASS");
	}

	/**
	 * 荷受時間区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param niukeTimeKbnClass 荷受時間区分のエラー、警告表示フラグ
	 */
	public void setNiukeTimeKbnClass(String niukeTimeKbnClass) {
		put("NIUKE_TIME_KBN_CLASS", niukeTimeKbnClass);
	}


	/**
	 * 荷受時間_開始のエラー、警告表示フラグを返します。
	 *
	 * @return 荷受時間_開始のエラー、警告表示フラグ
	 */
	public String getNiukeBiginTimeClass() {
		return (String) get("NIUKE_BIGIN_TIME_CLASS");
	}

	/**
	 * 荷受時間_開始のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param niukeBiginTimeClass 荷受時間_開始のエラー、警告表示フラグ
	 */
	public void setNiukeBiginTimeClass(String niukeBiginTimeClass) {
		put("NIUKE_BIGIN_TIME_CLASS", niukeBiginTimeClass);
	}


	/**
	 * 荷受時間_終了のエラー、警告表示フラグを返します。
	 *
	 * @return 荷受時間_終了のエラー、警告表示フラグ
	 */
	public String getNiukeEndTimeClass() {
		return (String) get("NIUKE_END_TIME_CLASS");
	}

	/**
	 * 荷受時間_終了のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param niukeEndTimeClass 荷受時間_終了のエラー、警告表示フラグ
	 */
	public void setNiukeEndTimeClass(String niukeEndTimeClass) {
		put("NIUKE_END_TIME_CLASS", niukeEndTimeClass);
	}


	/**
	 * 摘要区分 (01)のエラー、警告表示フラグを返します。
	 *
	 * @return 摘要区分 (01)のエラー、警告表示フラグ
	 */
	public String getTekiyoKbn1Class() {
		return (String) get("TEKIYO_KBN1_CLASS");
	}

	/**
	 * 摘要区分 (01)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tekiyoKbn1Class 摘要区分 (01)のエラー、警告表示フラグ
	 */
	public void setTekiyoKbn1Class(String tekiyoKbn1Class) {
		put("TEKIYO_KBN1_CLASS", tekiyoKbn1Class);
	}


	/**
	 * 摘要内容 (01)のエラー、警告表示フラグを返します。
	 *
	 * @return 摘要内容 (01)のエラー、警告表示フラグ
	 */
	public String getTekiyoNm1Class() {
		return (String) get("TEKIYO_NM1_CLASS");
	}

	/**
	 * 摘要内容 (01)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tekiyoNm1Class 摘要内容 (01)のエラー、警告表示フラグ
	 */
	public void setTekiyoNm1Class(String tekiyoNm1Class) {
		put("TEKIYO_NM1_CLASS", tekiyoNm1Class);
	}


	/**
	 * 摘要区分 (02)のエラー、警告表示フラグを返します。
	 *
	 * @return 摘要区分 (02)のエラー、警告表示フラグ
	 */
	public String getTekiyoKbn2Class() {
		return (String) get("TEKIYO_KBN2_CLASS");
	}

	/**
	 * 摘要区分 (02)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tekiyoKbn2Class 摘要区分 (02)のエラー、警告表示フラグ
	 */
	public void setTekiyoKbn2Class(String tekiyoKbn2Class) {
		put("TEKIYO_KBN2_CLASS", tekiyoKbn2Class);
	}


	/**
	 * 摘要内容 (02)のエラー、警告表示フラグを返します。
	 *
	 * @return 摘要内容 (02)のエラー、警告表示フラグ
	 */
	public String getTekiyoNm2Class() {
		return (String) get("TEKIYO_NM2_CLASS");
	}

	/**
	 * 摘要内容 (02)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tekiyoNm2Class 摘要内容 (02)のエラー、警告表示フラグ
	 */
	public void setTekiyoNm2Class(String tekiyoNm2Class) {
		put("TEKIYO_NM2_CLASS", tekiyoNm2Class);
	}


	/**
	 * 摘要区分 (03)のエラー、警告表示フラグを返します。
	 *
	 * @return 摘要区分 (03)のエラー、警告表示フラグ
	 */
	public String getTekiyoKbn3Class() {
		return (String) get("TEKIYO_KBN3_CLASS");
	}

	/**
	 * 摘要区分 (03)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tekiyoKbn3Class 摘要区分 (03)のエラー、警告表示フラグ
	 */
	public void setTekiyoKbn3Class(String tekiyoKbn3Class) {
		put("TEKIYO_KBN3_CLASS", tekiyoKbn3Class);
	}


	/**
	 * 摘要内容 (03)のエラー、警告表示フラグを返します。
	 *
	 * @return 摘要内容 (03)のエラー、警告表示フラグ
	 */
	public String getTekiyoNm3Class() {
		return (String) get("TEKIYO_NM3_CLASS");
	}

	/**
	 * 摘要内容 (03)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tekiyoNm3Class 摘要内容 (03)のエラー、警告表示フラグ
	 */
	public void setTekiyoNm3Class(String tekiyoNm3Class) {
		put("TEKIYO_NM3_CLASS", tekiyoNm3Class);
	}


	/**
	 * 摘要区分 (04)のエラー、警告表示フラグを返します。
	 *
	 * @return 摘要区分 (04)のエラー、警告表示フラグ
	 */
	public String getTekiyoKbn4Class() {
		return (String) get("TEKIYO_KBN4_CLASS");
	}

	/**
	 * 摘要区分 (04)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tekiyoKbn4Class 摘要区分 (04)のエラー、警告表示フラグ
	 */
	public void setTekiyoKbn4Class(String tekiyoKbn4Class) {
		put("TEKIYO_KBN4_CLASS", tekiyoKbn4Class);
	}


	/**
	 * 摘要内容 (04)のエラー、警告表示フラグを返します。
	 *
	 * @return 摘要内容 (04)のエラー、警告表示フラグ
	 */
	public String getTekiyoNm4Class() {
		return (String) get("TEKIYO_NM4_CLASS");
	}

	/**
	 * 摘要内容 (04)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tekiyoNm4Class 摘要内容 (04)のエラー、警告表示フラグ
	 */
	public void setTekiyoNm4Class(String tekiyoNm4Class) {
		put("TEKIYO_NM4_CLASS", tekiyoNm4Class);
	}


	/**
	 * 摘要区分 (05)のエラー、警告表示フラグを返します。
	 *
	 * @return 摘要区分 (05)のエラー、警告表示フラグ
	 */
	public String getTekiyoKbn5Class() {
		return (String) get("TEKIYO_KBN5_CLASS");
	}

	/**
	 * 摘要区分 (05)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tekiyoKbn5Class 摘要区分 (05)のエラー、警告表示フラグ
	 */
	public void setTekiyoKbn5Class(String tekiyoKbn5Class) {
		put("TEKIYO_KBN5_CLASS", tekiyoKbn5Class);
	}


	/**
	 * 摘要内容 (05)のエラー、警告表示フラグを返します。
	 *
	 * @return 摘要内容 (05)のエラー、警告表示フラグ
	 */
	public String getTekiyoNm5Class() {
		return (String) get("TEKIYO_NM5_CLASS");
	}

	/**
	 * 摘要内容 (05)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tekiyoNm5Class 摘要内容 (05)のエラー、警告表示フラグ
	 */
	public void setTekiyoNm5Class(String tekiyoNm5Class) {
		put("TEKIYO_NM5_CLASS", tekiyoNm5Class);
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
	 * データ種別CDのエラー、警告表示フラグを返します。
	 *
	 * @return データ種別CDのエラー、警告表示フラグ
	 */
	public String getSyubetuCdClass() {
		return (String) get("SYUBETU_CD_CLASS");
	}

	/**
	 * データ種別CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syubetuCdClass データ種別CDのエラー、警告表示フラグ
	 */
	public void setSyubetuCdClass(String syubetuCdClass) {
		put("SYUBETU_CD_CLASS", syubetuCdClass);
	}


	/**
	 * 特約店CDのエラー、警告表示フラグを返します。
	 *
	 * @return 特約店CDのエラー、警告表示フラグ
	 */
	public String getTokuyakutenCdClass() {
		return (String) get("TOKUYAKUTEN_CD_CLASS");
	}

	/**
	 * 特約店CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tokuyakutenCdClass 特約店CDのエラー、警告表示フラグ
	 */
	public void setTokuyakutenCdClass(String tokuyakutenCdClass) {
		put("TOKUYAKUTEN_CD_CLASS", tokuyakutenCdClass);
	}


	/**
	 * デポCDのエラー、警告表示フラグを返します。
	 *
	 * @return デポCDのエラー、警告表示フラグ
	 */
	public String getDepoCdClass() {
		return (String) get("DEPO_CD_CLASS");
	}

	/**
	 * デポCDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param depoCdClass デポCDのエラー、警告表示フラグ
	 */
	public void setDepoCdClass(String depoCdClass) {
		put("DEPO_CD_CLASS", depoCdClass);
	}


	/**
	 * 二次店CDのエラー、警告表示フラグを返します。
	 *
	 * @return 二次店CDのエラー、警告表示フラグ
	 */
	public String getNijitenCdClass() {
		return (String) get("NIJITEN_CD_CLASS");
	}

	/**
	 * 二次店CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param nijitenCdClass 二次店CDのエラー、警告表示フラグ
	 */
	public void setNijitenCdClass(String nijitenCdClass) {
		put("NIJITEN_CD_CLASS", nijitenCdClass);
	}


	/**
	 * 三次店CDのエラー、警告表示フラグを返します。
	 *
	 * @return 三次店CDのエラー、警告表示フラグ
	 */
	public String getSanjitenCdClass() {
		return (String) get("SANJITEN_CD_CLASS");
	}

	/**
	 * 三次店CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param sanjitenCdClass 三次店CDのエラー、警告表示フラグ
	 */
	public void setSanjitenCdClass(String sanjitenCdClass) {
		put("SANJITEN_CD_CLASS", sanjitenCdClass);
	}


	/**
	 * 出荷先国CDのエラー、警告表示フラグを返します。
	 *
	 * @return 出荷先国CDのエラー、警告表示フラグ
	 */
	public String getSyukaSakiCountryCdClass() {
		return (String) get("SYUKA_SAKI_COUNTRY_CD_CLASS");
	}

	/**
	 * 出荷先国CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaSakiCountryCdClass 出荷先国CDのエラー、警告表示フラグ
	 */
	public void setSyukaSakiCountryCdClass(String syukaSakiCountryCdClass) {
		put("SYUKA_SAKI_COUNTRY_CD_CLASS", syukaSakiCountryCdClass);
	}


	/**
	 * JISCDのエラー、警告表示フラグを返します。
	 *
	 * @return JISCDのエラー、警告表示フラグ
	 */
	public String getJisCdClass() {
		return (String) get("JIS_CD_CLASS");
	}

	/**
	 * JISCDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param jisCdClass JISCDのエラー、警告表示フラグ
	 */
	public void setJisCdClass(String jisCdClass) {
		put("JIS_CD_CLASS", jisCdClass);
	}


	/**
	 * 引取運賃換算単価のエラー、警告表示フラグを返します。
	 *
	 * @return 引取運賃換算単価のエラー、警告表示フラグ
	 */
	public String getUnchinCnvTankaClass() {
		return (String) get("UNCHIN_CNV_TANKA_CLASS");
	}

	/**
	 * 引取運賃換算単価のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param unchinCnvTankaClass 引取運賃換算単価のエラー、警告表示フラグ
	 */
	public void setUnchinCnvTankaClass(String unchinCnvTankaClass) {
		put("UNCHIN_CNV_TANKA_CLASS", unchinCnvTankaClass);
	}


	/**
	 * 最低配送ロット数のエラー、警告表示フラグを返します。
	 *
	 * @return 最低配送ロット数のエラー、警告表示フラグ
	 */
	public String getLowHaisoLotClass() {
		return (String) get("LOW_HAISO_LOT_CLASS");
	}

	/**
	 * 最低配送ロット数のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param lowHaisoLotClass 最低配送ロット数のエラー、警告表示フラグ
	 */
	public void setLowHaisoLotClass(String lowHaisoLotClass) {
		put("LOW_HAISO_LOT_CLASS", lowHaisoLotClass);
	}


	/**
	 * 出荷数量計_箱数のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷数量計_箱数のエラー、警告表示フラグ
	 */
	public String getSyukaSuryoBoxClass() {
		return (String) get("SYUKA_SURYO_BOX_CLASS");
	}

	/**
	 * 出荷数量計_箱数のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaSuryoBoxClass 出荷数量計_箱数のエラー、警告表示フラグ
	 */
	public void setSyukaSuryoBoxClass(String syukaSuryoBoxClass) {
		put("SYUKA_SURYO_BOX_CLASS", syukaSuryoBoxClass);
	}


	/**
	 * 出荷数量計_セット数のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷数量計_セット数のエラー、警告表示フラグ
	 */
	public String getSyukaSuryoSetClass() {
		return (String) get("SYUKA_SURYO_SET_CLASS");
	}

	/**
	 * 出荷数量計_セット数のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaSuryoSetClass 出荷数量計_セット数のエラー、警告表示フラグ
	 */
	public void setSyukaSuryoSetClass(String syukaSuryoSetClass) {
		put("SYUKA_SURYO_SET_CLASS", syukaSuryoSetClass);
	}


	/**
	 * 出荷金額計のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷金額計のエラー、警告表示フラグ
	 */
	public String getSyukaKingakuTotClass() {
		return (String) get("SYUKA_KINGAKU_TOT_CLASS");
	}

	/**
	 * 出荷金額計のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaKingakuTotClass 出荷金額計のエラー、警告表示フラグ
	 */
	public void setSyukaKingakuTotClass(String syukaKingakuTotClass) {
		put("SYUKA_KINGAKU_TOT_CLASS", syukaKingakuTotClass);
	}


	/**
	 * 容量計（L）_出荷容量計のエラー、警告表示フラグを返します。
	 *
	 * @return 容量計（L）_出荷容量計のエラー、警告表示フラグ
	 */
	public String getSyukaYouryoTotClass() {
		return (String) get("SYUKA_YOURYO_TOT_CLASS");
	}

	/**
	 * 容量計（L）_出荷容量計のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaYouryoTotClass 容量計（L）_出荷容量計のエラー、警告表示フラグ
	 */
	public void setSyukaYouryoTotClass(String syukaYouryoTotClass) {
		put("SYUKA_YOURYO_TOT_CLASS", syukaYouryoTotClass);
	}


	/**
	 * 容量計（L）_リベートⅠ類対象容量計のエラー、警告表示フラグを返します。
	 *
	 * @return 容量計（L）_リベートⅠ類対象容量計のエラー、警告表示フラグ
	 */
	public String getRebate1YouryoTotClass() {
		return (String) get("REBATE1_YOURYO_TOT_CLASS");
	}

	/**
	 * 容量計（L）_リベートⅠ類対象容量計のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param rebate1YouryoTotClass 容量計（L）_リベートⅠ類対象容量計のエラー、警告表示フラグ
	 */
	public void setRebate1YouryoTotClass(String rebate1YouryoTotClass) {
		put("REBATE1_YOURYO_TOT_CLASS", rebate1YouryoTotClass);
	}


	/**
	 * 容量計（L）_リベートⅡ類対象容量計のエラー、警告表示フラグを返します。
	 *
	 * @return 容量計（L）_リベートⅡ類対象容量計のエラー、警告表示フラグ
	 */
	public String getRebate2YouryoTotClass() {
		return (String) get("REBATE2_YOURYO_TOT_CLASS");
	}

	/**
	 * 容量計（L）_リベートⅡ類対象容量計のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param rebate2YouryoTotClass 容量計（L）_リベートⅡ類対象容量計のエラー、警告表示フラグ
	 */
	public void setRebate2YouryoTotClass(String rebate2YouryoTotClass) {
		put("REBATE2_YOURYO_TOT_CLASS", rebate2YouryoTotClass);
	}


	/**
	 * 容量計（L）_リベートⅢ類対象容量計のエラー、警告表示フラグを返します。
	 *
	 * @return 容量計（L）_リベートⅢ類対象容量計のエラー、警告表示フラグ
	 */
	public String getRebate3YouryoTotClass() {
		return (String) get("REBATE3_YOURYO_TOT_CLASS");
	}

	/**
	 * 容量計（L）_リベートⅢ類対象容量計のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param rebate3YouryoTotClass 容量計（L）_リベートⅢ類対象容量計のエラー、警告表示フラグ
	 */
	public void setRebate3YouryoTotClass(String rebate3YouryoTotClass) {
		put("REBATE3_YOURYO_TOT_CLASS", rebate3YouryoTotClass);
	}


	/**
	 * 容量計（L）_リベートⅣ類対象容量計のエラー、警告表示フラグを返します。
	 *
	 * @return 容量計（L）_リベートⅣ類対象容量計のエラー、警告表示フラグ
	 */
	public String getRebate4YouryoTotClass() {
		return (String) get("REBATE4_YOURYO_TOT_CLASS");
	}

	/**
	 * 容量計（L）_リベートⅣ類対象容量計のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param rebate4YouryoTotClass 容量計（L）_リベートⅣ類対象容量計のエラー、警告表示フラグ
	 */
	public void setRebate4YouryoTotClass(String rebate4YouryoTotClass) {
		put("REBATE4_YOURYO_TOT_CLASS", rebate4YouryoTotClass);
	}


	/**
	 * 容量計（L）_リベートⅤ類対象容量計のエラー、警告表示フラグを返します。
	 *
	 * @return 容量計（L）_リベートⅤ類対象容量計のエラー、警告表示フラグ
	 */
	public String getRebate5YouryoTotClass() {
		return (String) get("REBATE5_YOURYO_TOT_CLASS");
	}

	/**
	 * 容量計（L）_リベートⅤ類対象容量計のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param rebate5YouryoTotClass 容量計（L）_リベートⅤ類対象容量計のエラー、警告表示フラグ
	 */
	public void setRebate5YouryoTotClass(String rebate5YouryoTotClass) {
		put("REBATE5_YOURYO_TOT_CLASS", rebate5YouryoTotClass);
	}


	/**
	 * 容量計（L）_リベート対象外容量計のエラー、警告表示フラグを返します。
	 *
	 * @return 容量計（L）_リベート対象外容量計のエラー、警告表示フラグ
	 */
	public String getRebateoYouryoTotClass() {
		return (String) get("REBATEO_YOURYO_TOT_CLASS");
	}

	/**
	 * 容量計（L）_リベート対象外容量計のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param rebateoYouryoTotClass 容量計（L）_リベート対象外容量計のエラー、警告表示フラグ
	 */
	public void setRebateoYouryoTotClass(String rebateoYouryoTotClass) {
		put("REBATEO_YOURYO_TOT_CLASS", rebateoYouryoTotClass);
	}


	/**
	 * SDN受注ヘッダーエラー区分のエラー、警告表示フラグを返します。
	 *
	 * @return SDN受注ヘッダーエラー区分のエラー、警告表示フラグ
	 */
	public String getSdnHderrKbnClass() {
		return (String) get("SDN_HDERR_KBN_CLASS");
	}

	/**
	 * SDN受注ヘッダーエラー区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param sdnHderrKbnClass SDN受注ヘッダーエラー区分のエラー、警告表示フラグ
	 */
	public void setSdnHderrKbnClass(String sdnHderrKbnClass) {
		put("SDN_HDERR_KBN_CLASS", sdnHderrKbnClass);
	}


	/**
	 * 積荷伝票NO (FK)のエラー、警告表示フラグを返します。
	 *
	 * @return 積荷伝票NO (FK)のエラー、警告表示フラグ
	 */
	public String getTumidenNoClass() {
		return (String) get("TUMIDEN_NO_CLASS");
	}

	/**
	 * 積荷伝票NO (FK)のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tumidenNoClass 積荷伝票NO (FK)のエラー、警告表示フラグ
	 */
	public void setTumidenNoClass(String tumidenNoClass) {
		put("TUMIDEN_NO_CLASS", tumidenNoClass);
	}


	/**
	 * 積荷累積対象区分のエラー、警告表示フラグを返します。
	 *
	 * @return 積荷累積対象区分のエラー、警告表示フラグ
	 */
	public String getTumidenSmKbnClass() {
		return (String) get("TUMIDEN_SM_KBN_CLASS");
	}

	/**
	 * 積荷累積対象区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tumidenSmKbnClass 積荷累積対象区分のエラー、警告表示フラグ
	 */
	public void setTumidenSmKbnClass(String tumidenSmKbnClass) {
		put("TUMIDEN_SM_KBN_CLASS", tumidenSmKbnClass);
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


	/**
	 * 担当者名のエラー、警告表示フラグを返します。
	 *
	 * @return 担当者名のエラー、警告表示フラグ
	 */
	public String getTantosyaNmClass() {
		return (String) get("TANTOSYA_NM_CLASS");
	}

	/**
	 * 担当者名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tantosyaNmClass 担当者名のエラー、警告表示フラグ
	 */
	public void setTantosyaNmClass(String tantosyaNmClass) {
		put("TANTOSYA_NM_CLASS", tantosyaNmClass);
	}


	/**
	 * 特約店名のエラー、警告表示フラグを返します。
	 *
	 * @return 特約店名のエラー、警告表示フラグ
	 */
	public String getTokuyakutenNmClass() {
		return (String) get("TOKUYAKUTEN_NM_CLASS");
	}

	/**
	 * 特約店名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tokuyakutenNmClass 特約店名のエラー、警告表示フラグ
	 */
	public void setTokuyakutenNmClass(String tokuyakutenNmClass) {
		put("TOKUYAKUTEN_NM_CLASS", tokuyakutenNmClass);
	}


	/**
	 * デポ名のエラー、警告表示フラグを返します。
	 *
	 * @return デポ名のエラー、警告表示フラグ
	 */
	public String getDepoNmClass() {
		return (String) get("DEPO_NM_CLASS");
	}

	/**
	 * デポ名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param depoNmClass デポ名のエラー、警告表示フラグ
	 */
	public void setDepoNmClass(String depoNmClass) {
		put("DEPO_NM_CLASS", depoNmClass);
	}


	/**
	 * 二次店名のエラー、警告表示フラグを返します。
	 *
	 * @return 二次店名のエラー、警告表示フラグ
	 */
	public String getNijitenNmClass() {
		return (String) get("NIJITEN_NM_CLASS");
	}

	/**
	 * 二次店名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param nijitenNmClass 二次店名のエラー、警告表示フラグ
	 */
	public void setNijitenNmClass(String nijitenNmClass) {
		put("NIJITEN_NM_CLASS", nijitenNmClass);
	}


	/**
	 * 三次店名のエラー、警告表示フラグを返します。
	 *
	 * @return 三次店名のエラー、警告表示フラグ
	 */
	public String getSanjitenNmClass() {
		return (String) get("SANJITEN_NM_CLASS");
	}

	/**
	 * 三次店名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param sanjitenNmClass 三次店名のエラー、警告表示フラグ
	 */
	public void setSanjitenNmClass(String sanjitenNmClass) {
		put("SANJITEN_NM_CLASS", sanjitenNmClass);
	}


	/**
	 * 入力者CDのエラー、警告表示フラグを返します。
	 *
	 * @return 入力者CDのエラー、警告表示フラグ
	 */
	public String getNyuryokusyaCdClass() {
		return (String) get("NYURYOKUSYA_CD_CLASS");
	}

	/**
	 * 入力者CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param nyuryokusyaCdClass 入力者CDのエラー、警告表示フラグ
	 */
	public void setNyuryokusyaCdClass(String nyuryokusyaCdClass) {
		put("NYURYOKUSYA_CD_CLASS", nyuryokusyaCdClass);
	}


	/**
	 * 入力者名のエラー、警告表示フラグを返します。
	 *
	 * @return 入力者名のエラー、警告表示フラグ
	 */
	public String getNyuryokusyaNmClass() {
		return (String) get("NYURYOKUSYA_NM_CLASS");
	}

	/**
	 * 入力者名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param nyuryokusyaNmClass 入力者名のエラー、警告表示フラグ
	 */
	public void setNyuryokusyaNmClass(String nyuryokusyaNmClass) {
		put("NYURYOKUSYA_NM_CLASS", nyuryokusyaNmClass);
	}


	/**
	 * 都道府県CDのエラー、警告表示フラグを返します。
	 *
	 * @return 都道府県CDのエラー、警告表示フラグ
	 */
	public String getTodofuknCdClass() {
		return (String) get("TODOFUKN_CD_CLASS");
	}

	/**
	 * 都道府県CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param todofuknCdClass 都道府県CDのエラー、警告表示フラグ
	 */
	public void setTodofuknCdClass(String todofuknCdClass) {
		put("TODOFUKN_CD_CLASS", todofuknCdClass);
	}


	/**
	 * 倉庫種類区分のエラー、警告表示フラグを返します。
	 *
	 * @return 倉庫種類区分のエラー、警告表示フラグ
	 */
	public String getSoukoSyuruiKbnClass() {
		return (String) get("SOUKO_SYURUI_KBN_CLASS");
	}

	/**
	 * 倉庫種類区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param soukoSyuruiKbnClass 倉庫種類区分のエラー、警告表示フラグ
	 */
	public void setSoukoSyuruiKbnClass(String soukoSyuruiKbnClass) {
		put("SOUKO_SYURUI_KBN_CLASS", soukoSyuruiKbnClass);
	}


	/**
	 * 倉入・直送区分のエラー、警告表示フラグを返します。
	 *
	 * @return 倉入・直送区分のエラー、警告表示フラグ
	 */
	public String getDeliveryKbnClass() {
		return (String) get("DELIVERY_KBN_CLASS");
	}

	/**
	 * 倉入・直送区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param deliveryKbnClass 倉入・直送区分のエラー、警告表示フラグ
	 */
	public void setDeliveryKbnClass(String deliveryKbnClass) {
		put("DELIVERY_KBN_CLASS", deliveryKbnClass);
	}


	/**
	 * 当日出荷可能区分のエラー、警告表示フラグを返します。
	 *
	 * @return 当日出荷可能区分のエラー、警告表示フラグ
	 */
	public String getToujituSyukkaKbnClass() {
		return (String) get("TOUJITU_SYUKKA_KBN_CLASS");
	}

	/**
	 * 当日出荷可能区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param toujituSyukkaKbnClass 当日出荷可能区分のエラー、警告表示フラグ
	 */
	public void setToujituSyukkaKbnClass(String toujituSyukkaKbnClass) {
		put("TOUJITU_SYUKKA_KBN_CLASS", toujituSyukkaKbnClass);
	}


	/**
	 * 出荷日注意区分のエラー、警告表示フラグを返します。
	 *
	 * @return 出荷日注意区分のエラー、警告表示フラグ
	 */
	public String getSyukaDtCyuiKbnClass() {
		return (String) get("SYUKA_DT_CYUI_KBN_CLASS");
	}

	/**
	 * 出荷日注意区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param syukaDtCyuiKbnClass 出荷日注意区分のエラー、警告表示フラグ
	 */
	public void setSyukaDtCyuiKbnClass(String syukaDtCyuiKbnClass) {
		put("SYUKA_DT_CYUI_KBN_CLASS", syukaDtCyuiKbnClass);
	}


	/**
	 * 荷受不可曜日_月のエラー、警告表示フラグを返します。
	 *
	 * @return 荷受不可曜日_月のエラー、警告表示フラグ
	 */
	public String getNiukeFukaMonClass() {
		return (String) get("NIUKE_FUKA_MON_CLASS");
	}

	/**
	 * 荷受不可曜日_月のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param niukeFukaMonClass 荷受不可曜日_月のエラー、警告表示フラグ
	 */
	public void setNiukeFukaMonClass(String niukeFukaMonClass) {
		put("NIUKE_FUKA_MON_CLASS", niukeFukaMonClass);
	}


	/**
	 * 荷受不可曜日_火のエラー、警告表示フラグを返します。
	 *
	 * @return 荷受不可曜日_火のエラー、警告表示フラグ
	 */
	public String getNiukeFukaTueClass() {
		return (String) get("NIUKE_FUKA_TUE_CLASS");
	}

	/**
	 * 荷受不可曜日_火のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param niukeFukaTueClass 荷受不可曜日_火のエラー、警告表示フラグ
	 */
	public void setNiukeFukaTueClass(String niukeFukaTueClass) {
		put("NIUKE_FUKA_TUE_CLASS", niukeFukaTueClass);
	}


	/**
	 * 荷受不可曜日_水のエラー、警告表示フラグを返します。
	 *
	 * @return 荷受不可曜日_水のエラー、警告表示フラグ
	 */
	public String getNiukeFukaWedClass() {
		return (String) get("NIUKE_FUKA_WED_CLASS");
	}

	/**
	 * 荷受不可曜日_水のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param niukeFukaWedClass 荷受不可曜日_水のエラー、警告表示フラグ
	 */
	public void setNiukeFukaWedClass(String niukeFukaWedClass) {
		put("NIUKE_FUKA_WED_CLASS", niukeFukaWedClass);
	}


	/**
	 * 荷受不可曜日_木のエラー、警告表示フラグを返します。
	 *
	 * @return 荷受不可曜日_木のエラー、警告表示フラグ
	 */
	public String getNiukeFukaThuClass() {
		return (String) get("NIUKE_FUKA_THU_CLASS");
	}

	/**
	 * 荷受不可曜日_木のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param niukeFukaThuClass 荷受不可曜日_木のエラー、警告表示フラグ
	 */
	public void setNiukeFukaThuClass(String niukeFukaThuClass) {
		put("NIUKE_FUKA_THU_CLASS", niukeFukaThuClass);
	}


	/**
	 * 荷受不可曜日_金のエラー、警告表示フラグを返します。
	 *
	 * @return 荷受不可曜日_金のエラー、警告表示フラグ
	 */
	public String getNiukeFukaFriClass() {
		return (String) get("NIUKE_FUKA_FRI_CLASS");
	}

	/**
	 * 荷受不可曜日_金のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param niukeFukaFriClass 荷受不可曜日_金のエラー、警告表示フラグ
	 */
	public void setNiukeFukaFriClass(String niukeFukaFriClass) {
		put("NIUKE_FUKA_FRI_CLASS", niukeFukaFriClass);
	}


	/**
	 * 荷受不可曜日_土のエラー、警告表示フラグを返します。
	 *
	 * @return 荷受不可曜日_土のエラー、警告表示フラグ
	 */
	public String getNiukeFukaSatClass() {
		return (String) get("NIUKE_FUKA_SAT_CLASS");
	}

	/**
	 * 荷受不可曜日_土のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param niukeFukaSatClass 荷受不可曜日_土のエラー、警告表示フラグ
	 */
	public void setNiukeFukaSatClass(String niukeFukaSatClass) {
		put("NIUKE_FUKA_SAT_CLASS", niukeFukaSatClass);
	}


	/**
	 * 荷受不可曜日_日のエラー、警告表示フラグを返します。
	 *
	 * @return 荷受不可曜日_日のエラー、警告表示フラグ
	 */
	public String getNiukeFukaSunClass() {
		return (String) get("NIUKE_FUKA_SUN_CLASS");
	}

	/**
	 * 荷受不可曜日_日のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param niukeFukaSunClass 荷受不可曜日_日のエラー、警告表示フラグ
	 */
	public void setNiukeFukaSunClass(String niukeFukaSunClass) {
		put("NIUKE_FUKA_SUN_CLASS", niukeFukaSunClass);
	}


	/**
	 * 荷受不可曜日_祝のエラー、警告表示フラグを返します。
	 *
	 * @return 荷受不可曜日_祝のエラー、警告表示フラグ
	 */
	public String getNiukeFukaHolClass() {
		return (String) get("NIUKE_FUKA_HOL_CLASS");
	}

	/**
	 * 荷受不可曜日_祝のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param niukeFukaHolClass 荷受不可曜日_祝のエラー、警告表示フラグ
	 */
	public void setNiukeFukaHolClass(String niukeFukaHolClass) {
		put("NIUKE_FUKA_HOL_CLASS", niukeFukaHolClass);
	}


	/**
	 * 請求先名称(正) 1/2のエラー、警告表示フラグを返します。
	 *
	 * @return 請求先名称(正) 1/2のエラー、警告表示フラグ
	 */
	public String getSeikyusakiNm01Class() {
		return (String) get("SEIKYUSAKI_NM01_CLASS");
	}

	/**
	 * 請求先名称(正) 1/2のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param seikyusakiNm01Class 請求先名称(正) 1/2のエラー、警告表示フラグ
	 */
	public void setSeikyusakiNm01Class(String seikyusakiNm01Class) {
		put("SEIKYUSAKI_NM01_CLASS", seikyusakiNm01Class);
	}


	/**
	 * 請求先名称(正) 2/2のエラー、警告表示フラグを返します。
	 *
	 * @return 請求先名称(正) 2/2のエラー、警告表示フラグ
	 */
	public String getSeikyusakiNm02Class() {
		return (String) get("SEIKYUSAKI_NM02_CLASS");
	}

	/**
	 * 請求先名称(正) 2/2のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param seikyusakiNm02Class 請求先名称(正) 2/2のエラー、警告表示フラグ
	 */
	public void setSeikyusakiNm02Class(String seikyusakiNm02Class) {
		put("SEIKYUSAKI_NM02_CLASS", seikyusakiNm02Class);
	}


	/**
	 * 請求明細グループCD_特約店CDのエラー、警告表示フラグを返します。
	 *
	 * @return 請求明細グループCD_特約店CDのエラー、警告表示フラグ
	 */
	public String getSmgpTokuyakutenCdClass() {
		return (String) get("SMGP_TOKUYAKUTEN_CD_CLASS");
	}

	/**
	 * 請求明細グループCD_特約店CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param smgpTokuyakutenCdClass 請求明細グループCD_特約店CDのエラー、警告表示フラグ
	 */
	public void setSmgpTokuyakutenCdClass(String smgpTokuyakutenCdClass) {
		put("SMGP_TOKUYAKUTEN_CD_CLASS", smgpTokuyakutenCdClass);
	}


	/**
	 * 請求明細グループCD_デポCDのエラー、警告表示フラグを返します。
	 *
	 * @return 請求明細グループCD_デポCDのエラー、警告表示フラグ
	 */
	public String getSmgpDepoCdClass() {
		return (String) get("SMGP_DEPO_CD_CLASS");
	}

	/**
	 * 請求明細グループCD_デポCDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param smgpDepoCdClass 請求明細グループCD_デポCDのエラー、警告表示フラグ
	 */
	public void setSmgpDepoCdClass(String smgpDepoCdClass) {
		put("SMGP_DEPO_CD_CLASS", smgpDepoCdClass);
	}


	/**
	 * 請求明細グループCD_二次店CDのエラー、警告表示フラグを返します。
	 *
	 * @return 請求明細グループCD_二次店CDのエラー、警告表示フラグ
	 */
	public String getSmgpNijitenCdClass() {
		return (String) get("SMGP_NIJITEN_CD_CLASS");
	}

	/**
	 * 請求明細グループCD_二次店CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param smgpNijitenCdClass 請求明細グループCD_二次店CDのエラー、警告表示フラグ
	 */
	public void setSmgpNijitenCdClass(String smgpNijitenCdClass) {
		put("SMGP_NIJITEN_CD_CLASS", smgpNijitenCdClass);
	}


	/**
	 * 請求明細グループCD_三次店CDのエラー、警告表示フラグを返します。
	 *
	 * @return 請求明細グループCD_三次店CDのエラー、警告表示フラグ
	 */
	public String getSmgpSanjitenCdClass() {
		return (String) get("SMGP_SANJITEN_CD_CLASS");
	}

	/**
	 * 請求明細グループCD_三次店CDのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param smgpSanjitenCdClass 請求明細グループCD_三次店CDのエラー、警告表示フラグ
	 */
	public void setSmgpSanjitenCdClass(String smgpSanjitenCdClass) {
		put("SMGP_SANJITEN_CD_CLASS", smgpSanjitenCdClass);
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
	 * 会社CD (FK)を返します。
	 *
	 * @return 会社CD (FK)
	 */
	public String getKaisyaCdView() {
		return (String) get("KAISYA_CD_VIEW");
	}

	/**
	 * 会社CD (FK)をレコードに格納します。
	 *
	 * @param kaisyaCdView 会社CD (FK)
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
	 * 整形済み受注NOを返します。
	 *
	 * @return 整形済み受注NO
	 */
	public String getJyucyuNoFormattedView() {
		return (String) get("JYUCYU_NO_FORMATTED_VIEW");
	}

	/**
	 * 整形済み受注NOをレコードに格納します。
	 *
	 * @param jyucyuNoFormattedView 整形済み受注NO
	 */
	public void setJyucyuNoFormattedView(String jyucyuNoFormattedView) {
		put("JYUCYU_NO_FORMATTED_VIEW", jyucyuNoFormattedView);
	}


	/**
	 * 縦線CD (FK)を返します。
	 *
	 * @return 縦線CD (FK)
	 */
	public String getOrsTatesnCdView() {
		return (String) get("ORS_TATESN_CD_VIEW");
	}

	/**
	 * 縦線CD (FK)をレコードに格納します。
	 *
	 * @param orsTatesnCdView 縦線CD (FK)
	 */
	public void setOrsTatesnCdView(String orsTatesnCdView) {
		put("ORS_TATESN_CD_VIEW", orsTatesnCdView);
	}


	/**
	 * 担当者CDを返します。
	 *
	 * @return 担当者CD
	 */
	public String getTantosyaCdView() {
		return (String) get("TANTOSYA_CD_VIEW");
	}

	/**
	 * 担当者CDをレコードに格納します。
	 *
	 * @param tantosyaCdView 担当者CD
	 */
	public void setTantosyaCdView(String tantosyaCdView) {
		put("TANTOSYA_CD_VIEW", tantosyaCdView);
	}


	/**
	 * 先方発注NOを返します。
	 *
	 * @return 先方発注NO
	 */
	public String getSenpoHacyuNoView() {
		return (String) get("SENPO_HACYU_NO_VIEW");
	}

	/**
	 * 先方発注NOをレコードに格納します。
	 *
	 * @param senpoHacyuNoView 先方発注NO
	 */
	public void setSenpoHacyuNoView(String senpoHacyuNoView) {
		put("SENPO_HACYU_NO_VIEW", senpoHacyuNoView);
	}


	/**
	 * 出荷日(売上伝票発行予定日)を返します。
	 *
	 * @return 出荷日(売上伝票発行予定日)
	 */
	public String getSyukaDtView() {
		return (String) get("SYUKA_DT_VIEW");
	}

	/**
	 * 出荷日(売上伝票発行予定日)をレコードに格納します。
	 *
	 * @param syukaDtView 出荷日(売上伝票発行予定日)
	 */
	public void setSyukaDtView(String syukaDtView) {
		put("SYUKA_DT_VIEW", syukaDtView);
	}


	/**
	 * 着荷予定日を返します。
	 *
	 * @return 着荷予定日
	 */
	public String getChacuniYoteiDtView() {
		return (String) get("CHACUNI_YOTEI_DT_VIEW");
	}

	/**
	 * 着荷予定日をレコードに格納します。
	 *
	 * @param chacuniYoteiDtView 着荷予定日
	 */
	public void setChacuniYoteiDtView(String chacuniYoteiDtView) {
		put("CHACUNI_YOTEI_DT_VIEW", chacuniYoteiDtView);
	}


	/**
	 * 酒販店（統一）CDを返します。
	 *
	 * @return 酒販店（統一）CD
	 */
	public String getSyuhantenCdView() {
		return (String) get("SYUHANTEN_CD_VIEW");
	}

	/**
	 * 酒販店（統一）CDをレコードに格納します。
	 *
	 * @param syuhantenCdView 酒販店（統一）CD
	 */
	public void setSyuhantenCdView(String syuhantenCdView) {
		put("SYUHANTEN_CD_VIEW", syuhantenCdView);
	}


	/**
	 * 運賃区分を返します。
	 *
	 * @return 運賃区分
	 */
	public String getUnchinKbnView() {
		return (String) get("UNCHIN_KBN_VIEW");
	}

	/**
	 * 運賃区分をレコードに格納します。
	 *
	 * @param unchinKbnView 運賃区分
	 */
	public void setUnchinKbnView(String unchinKbnView) {
		put("UNCHIN_KBN_VIEW", unchinKbnView);
	}


	/**
	 * 運送店CDを返します。
	 *
	 * @return 運送店CD
	 */
	public String getUnsotenCdView() {
		return (String) get("UNSOTEN_CD_VIEW");
	}

	/**
	 * 運送店CDをレコードに格納します。
	 *
	 * @param unsotenCdView 運送店CD
	 */
	public void setUnsotenCdView(String unsotenCdView) {
		put("UNSOTEN_CD_VIEW", unsotenCdView);
	}


	/**
	 * 小ロット出荷承認申請NOを返します。
	 *
	 * @return 小ロット出荷承認申請NO
	 */
	public String getSyukaSyoninNoView() {
		return (String) get("SYUKA_SYONIN_NO_VIEW");
	}

	/**
	 * 小ロット出荷承認申請NOをレコードに格納します。
	 *
	 * @param syukaSyoninNoView 小ロット出荷承認申請NO
	 */
	public void setSyukaSyoninNoView(String syukaSyoninNoView) {
		put("SYUKA_SYONIN_NO_VIEW", syukaSyoninNoView);
	}


	/**
	 * 出荷区分を返します。
	 *
	 * @return 出荷区分
	 */
	public String getSyukaKbnView() {
		return (String) get("SYUKA_KBN_VIEW");
	}

	/**
	 * 出荷区分をレコードに格納します。
	 *
	 * @param syukaKbnView 出荷区分
	 */
	public void setSyukaKbnView(String syukaKbnView) {
		put("SYUKA_KBN_VIEW", syukaKbnView);
	}


	/**
	 * 蔵CD (FK)を返します。
	 *
	 * @return 蔵CD (FK)
	 */
	public String getKuraCdView() {
		return (String) get("KURA_CD_VIEW");
	}

	/**
	 * 蔵CD (FK)をレコードに格納します。
	 *
	 * @param kuraCdView 蔵CD (FK)
	 */
	public void setKuraCdView(String kuraCdView) {
		put("KURA_CD_VIEW", kuraCdView);
	}


	/**
	 * ミナシ日付を返します。
	 *
	 * @return ミナシ日付
	 */
	public String getMinasiDtView() {
		return (String) get("MINASI_DT_VIEW");
	}

	/**
	 * ミナシ日付をレコードに格納します。
	 *
	 * @param minasiDtView ミナシ日付
	 */
	public void setMinasiDtView(String minasiDtView) {
		put("MINASI_DT_VIEW", minasiDtView);
	}


	/**
	 * 荷受時間区分を返します。
	 *
	 * @return 荷受時間区分
	 */
	public String getNiukeTimeKbnView() {
		return (String) get("NIUKE_TIME_KBN_VIEW");
	}

	/**
	 * 荷受時間区分をレコードに格納します。
	 *
	 * @param niukeTimeKbnView 荷受時間区分
	 */
	public void setNiukeTimeKbnView(String niukeTimeKbnView) {
		put("NIUKE_TIME_KBN_VIEW", niukeTimeKbnView);
	}


	/**
	 * 荷受時間_開始を返します。
	 *
	 * @return 荷受時間_開始
	 */
	public String getNiukeBiginTimeView() {
		return (String) get("NIUKE_BIGIN_TIME_VIEW");
	}

	/**
	 * 荷受時間_開始をレコードに格納します。
	 *
	 * @param niukeBiginTimeView 荷受時間_開始
	 */
	public void setNiukeBiginTimeView(String niukeBiginTimeView) {
		put("NIUKE_BIGIN_TIME_VIEW", niukeBiginTimeView);
	}


	/**
	 * 荷受時間_終了を返します。
	 *
	 * @return 荷受時間_終了
	 */
	public String getNiukeEndTimeView() {
		return (String) get("NIUKE_END_TIME_VIEW");
	}

	/**
	 * 荷受時間_終了をレコードに格納します。
	 *
	 * @param niukeEndTimeView 荷受時間_終了
	 */
	public void setNiukeEndTimeView(String niukeEndTimeView) {
		put("NIUKE_END_TIME_VIEW", niukeEndTimeView);
	}


	/**
	 * 摘要区分 (01)を返します。
	 *
	 * @return 摘要区分 (01)
	 */
	public String getTekiyoKbn1View() {
		return (String) get("TEKIYO_KBN1_VIEW");
	}

	/**
	 * 摘要区分 (01)をレコードに格納します。
	 *
	 * @param tekiyoKbn1View 摘要区分 (01)
	 */
	public void setTekiyoKbn1View(String tekiyoKbn1View) {
		put("TEKIYO_KBN1_VIEW", tekiyoKbn1View);
	}


	/**
	 * 摘要内容 (01)を返します。
	 *
	 * @return 摘要内容 (01)
	 */
	public String getTekiyoNm1View() {
		return (String) get("TEKIYO_NM1_VIEW");
	}

	/**
	 * 摘要内容 (01)をレコードに格納します。
	 *
	 * @param tekiyoNm1View 摘要内容 (01)
	 */
	public void setTekiyoNm1View(String tekiyoNm1View) {
		put("TEKIYO_NM1_VIEW", tekiyoNm1View);
	}


	/**
	 * 摘要区分 (02)を返します。
	 *
	 * @return 摘要区分 (02)
	 */
	public String getTekiyoKbn2View() {
		return (String) get("TEKIYO_KBN2_VIEW");
	}

	/**
	 * 摘要区分 (02)をレコードに格納します。
	 *
	 * @param tekiyoKbn2View 摘要区分 (02)
	 */
	public void setTekiyoKbn2View(String tekiyoKbn2View) {
		put("TEKIYO_KBN2_VIEW", tekiyoKbn2View);
	}


	/**
	 * 摘要内容 (02)を返します。
	 *
	 * @return 摘要内容 (02)
	 */
	public String getTekiyoNm2View() {
		return (String) get("TEKIYO_NM2_VIEW");
	}

	/**
	 * 摘要内容 (02)をレコードに格納します。
	 *
	 * @param tekiyoNm2View 摘要内容 (02)
	 */
	public void setTekiyoNm2View(String tekiyoNm2View) {
		put("TEKIYO_NM2_VIEW", tekiyoNm2View);
	}


	/**
	 * 摘要区分 (03)を返します。
	 *
	 * @return 摘要区分 (03)
	 */
	public String getTekiyoKbn3View() {
		return (String) get("TEKIYO_KBN3_VIEW");
	}

	/**
	 * 摘要区分 (03)をレコードに格納します。
	 *
	 * @param tekiyoKbn3View 摘要区分 (03)
	 */
	public void setTekiyoKbn3View(String tekiyoKbn3View) {
		put("TEKIYO_KBN3_VIEW", tekiyoKbn3View);
	}


	/**
	 * 摘要内容 (03)を返します。
	 *
	 * @return 摘要内容 (03)
	 */
	public String getTekiyoNm3View() {
		return (String) get("TEKIYO_NM3_VIEW");
	}

	/**
	 * 摘要内容 (03)をレコードに格納します。
	 *
	 * @param tekiyoNm3View 摘要内容 (03)
	 */
	public void setTekiyoNm3View(String tekiyoNm3View) {
		put("TEKIYO_NM3_VIEW", tekiyoNm3View);
	}


	/**
	 * 摘要区分 (04)を返します。
	 *
	 * @return 摘要区分 (04)
	 */
	public String getTekiyoKbn4View() {
		return (String) get("TEKIYO_KBN4_VIEW");
	}

	/**
	 * 摘要区分 (04)をレコードに格納します。
	 *
	 * @param tekiyoKbn4View 摘要区分 (04)
	 */
	public void setTekiyoKbn4View(String tekiyoKbn4View) {
		put("TEKIYO_KBN4_VIEW", tekiyoKbn4View);
	}


	/**
	 * 摘要内容 (04)を返します。
	 *
	 * @return 摘要内容 (04)
	 */
	public String getTekiyoNm4View() {
		return (String) get("TEKIYO_NM4_VIEW");
	}

	/**
	 * 摘要内容 (04)をレコードに格納します。
	 *
	 * @param tekiyoNm4View 摘要内容 (04)
	 */
	public void setTekiyoNm4View(String tekiyoNm4View) {
		put("TEKIYO_NM4_VIEW", tekiyoNm4View);
	}


	/**
	 * 摘要区分 (05)を返します。
	 *
	 * @return 摘要区分 (05)
	 */
	public String getTekiyoKbn5View() {
		return (String) get("TEKIYO_KBN5_VIEW");
	}

	/**
	 * 摘要区分 (05)をレコードに格納します。
	 *
	 * @param tekiyoKbn5View 摘要区分 (05)
	 */
	public void setTekiyoKbn5View(String tekiyoKbn5View) {
		put("TEKIYO_KBN5_VIEW", tekiyoKbn5View);
	}


	/**
	 * 摘要内容 (05)を返します。
	 *
	 * @return 摘要内容 (05)
	 */
	public String getTekiyoNm5View() {
		return (String) get("TEKIYO_NM5_VIEW");
	}

	/**
	 * 摘要内容 (05)をレコードに格納します。
	 *
	 * @param tekiyoNm5View 摘要内容 (05)
	 */
	public void setTekiyoNm5View(String tekiyoNm5View) {
		put("TEKIYO_NM5_VIEW", tekiyoNm5View);
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
	 * 重量計(KG)を返します。
	 *
	 * @return 重量計(KG)
	 */
	public String getJyuryoTotView() {
		return (String) get("JYURYO_TOT_VIEW");
	}

	/**
	 * 重量計(KG)をレコードに格納します。
	 *
	 * @param jyuryoTotView 重量計(KG)
	 */
	public void setJyuryoTotView(String jyuryoTotView) {
		put("JYURYO_TOT_VIEW", jyuryoTotView);
	}


	/**
	 * データ種別CDを返します。
	 *
	 * @return データ種別CD
	 */
	public String getSyubetuCdView() {
		return (String) get("SYUBETU_CD_VIEW");
	}

	/**
	 * データ種別CDをレコードに格納します。
	 *
	 * @param syubetuCdView データ種別CD
	 */
	public void setSyubetuCdView(String syubetuCdView) {
		put("SYUBETU_CD_VIEW", syubetuCdView);
	}


	/**
	 * 特約店CDを返します。
	 *
	 * @return 特約店CD
	 */
	public String getTokuyakutenCdView() {
		return (String) get("TOKUYAKUTEN_CD_VIEW");
	}

	/**
	 * 特約店CDをレコードに格納します。
	 *
	 * @param tokuyakutenCdView 特約店CD
	 */
	public void setTokuyakutenCdView(String tokuyakutenCdView) {
		put("TOKUYAKUTEN_CD_VIEW", tokuyakutenCdView);
	}


	/**
	 * デポCDを返します。
	 *
	 * @return デポCD
	 */
	public String getDepoCdView() {
		return (String) get("DEPO_CD_VIEW");
	}

	/**
	 * デポCDをレコードに格納します。
	 *
	 * @param depoCdView デポCD
	 */
	public void setDepoCdView(String depoCdView) {
		put("DEPO_CD_VIEW", depoCdView);
	}


	/**
	 * 二次店CDを返します。
	 *
	 * @return 二次店CD
	 */
	public String getNijitenCdView() {
		return (String) get("NIJITEN_CD_VIEW");
	}

	/**
	 * 二次店CDをレコードに格納します。
	 *
	 * @param nijitenCdView 二次店CD
	 */
	public void setNijitenCdView(String nijitenCdView) {
		put("NIJITEN_CD_VIEW", nijitenCdView);
	}


	/**
	 * 三次店CDを返します。
	 *
	 * @return 三次店CD
	 */
	public String getSanjitenCdView() {
		return (String) get("SANJITEN_CD_VIEW");
	}

	/**
	 * 三次店CDをレコードに格納します。
	 *
	 * @param sanjitenCdView 三次店CD
	 */
	public void setSanjitenCdView(String sanjitenCdView) {
		put("SANJITEN_CD_VIEW", sanjitenCdView);
	}


	/**
	 * 出荷先国CDを返します。
	 *
	 * @return 出荷先国CD
	 */
	public String getSyukaSakiCountryCdView() {
		return (String) get("SYUKA_SAKI_COUNTRY_CD_VIEW");
	}

	/**
	 * 出荷先国CDをレコードに格納します。
	 *
	 * @param syukaSakiCountryCdView 出荷先国CD
	 */
	public void setSyukaSakiCountryCdView(String syukaSakiCountryCdView) {
		put("SYUKA_SAKI_COUNTRY_CD_VIEW", syukaSakiCountryCdView);
	}


	/**
	 * JISCDを返します。
	 *
	 * @return JISCD
	 */
	public String getJisCdView() {
		return (String) get("JIS_CD_VIEW");
	}

	/**
	 * JISCDをレコードに格納します。
	 *
	 * @param jisCdView JISCD
	 */
	public void setJisCdView(String jisCdView) {
		put("JIS_CD_VIEW", jisCdView);
	}


	/**
	 * 引取運賃換算単価を返します。
	 *
	 * @return 引取運賃換算単価
	 */
	public String getUnchinCnvTankaView() {
		return (String) get("UNCHIN_CNV_TANKA_VIEW");
	}

	/**
	 * 引取運賃換算単価をレコードに格納します。
	 *
	 * @param unchinCnvTankaView 引取運賃換算単価
	 */
	public void setUnchinCnvTankaView(String unchinCnvTankaView) {
		put("UNCHIN_CNV_TANKA_VIEW", unchinCnvTankaView);
	}


	/**
	 * 最低配送ロット数を返します。
	 *
	 * @return 最低配送ロット数
	 */
	public String getLowHaisoLotView() {
		return (String) get("LOW_HAISO_LOT_VIEW");
	}

	/**
	 * 最低配送ロット数をレコードに格納します。
	 *
	 * @param lowHaisoLotView 最低配送ロット数
	 */
	public void setLowHaisoLotView(String lowHaisoLotView) {
		put("LOW_HAISO_LOT_VIEW", lowHaisoLotView);
	}


	/**
	 * 出荷数量計_箱数を返します。
	 *
	 * @return 出荷数量計_箱数
	 */
	public String getSyukaSuryoBoxView() {
		return (String) get("SYUKA_SURYO_BOX_VIEW");
	}

	/**
	 * 出荷数量計_箱数をレコードに格納します。
	 *
	 * @param syukaSuryoBoxView 出荷数量計_箱数
	 */
	public void setSyukaSuryoBoxView(String syukaSuryoBoxView) {
		put("SYUKA_SURYO_BOX_VIEW", syukaSuryoBoxView);
	}


	/**
	 * 出荷数量計_セット数を返します。
	 *
	 * @return 出荷数量計_セット数
	 */
	public String getSyukaSuryoSetView() {
		return (String) get("SYUKA_SURYO_SET_VIEW");
	}

	/**
	 * 出荷数量計_セット数をレコードに格納します。
	 *
	 * @param syukaSuryoSetView 出荷数量計_セット数
	 */
	public void setSyukaSuryoSetView(String syukaSuryoSetView) {
		put("SYUKA_SURYO_SET_VIEW", syukaSuryoSetView);
	}


	/**
	 * 出荷金額計を返します。
	 *
	 * @return 出荷金額計
	 */
	public String getSyukaKingakuTotView() {
		return (String) get("SYUKA_KINGAKU_TOT_VIEW");
	}

	/**
	 * 出荷金額計をレコードに格納します。
	 *
	 * @param syukaKingakuTotView 出荷金額計
	 */
	public void setSyukaKingakuTotView(String syukaKingakuTotView) {
		put("SYUKA_KINGAKU_TOT_VIEW", syukaKingakuTotView);
	}


	/**
	 * 容量計（L）_出荷容量計を返します。
	 *
	 * @return 容量計（L）_出荷容量計
	 */
	public String getSyukaYouryoTotView() {
		return (String) get("SYUKA_YOURYO_TOT_VIEW");
	}

	/**
	 * 容量計（L）_出荷容量計をレコードに格納します。
	 *
	 * @param syukaYouryoTotView 容量計（L）_出荷容量計
	 */
	public void setSyukaYouryoTotView(String syukaYouryoTotView) {
		put("SYUKA_YOURYO_TOT_VIEW", syukaYouryoTotView);
	}


	/**
	 * 容量計（L）_リベートⅠ類対象容量計を返します。
	 *
	 * @return 容量計（L）_リベートⅠ類対象容量計
	 */
	public String getRebate1YouryoTotView() {
		return (String) get("REBATE1_YOURYO_TOT_VIEW");
	}

	/**
	 * 容量計（L）_リベートⅠ類対象容量計をレコードに格納します。
	 *
	 * @param rebate1YouryoTotView 容量計（L）_リベートⅠ類対象容量計
	 */
	public void setRebate1YouryoTotView(String rebate1YouryoTotView) {
		put("REBATE1_YOURYO_TOT_VIEW", rebate1YouryoTotView);
	}


	/**
	 * 容量計（L）_リベートⅡ類対象容量計を返します。
	 *
	 * @return 容量計（L）_リベートⅡ類対象容量計
	 */
	public String getRebate2YouryoTotView() {
		return (String) get("REBATE2_YOURYO_TOT_VIEW");
	}

	/**
	 * 容量計（L）_リベートⅡ類対象容量計をレコードに格納します。
	 *
	 * @param rebate2YouryoTotView 容量計（L）_リベートⅡ類対象容量計
	 */
	public void setRebate2YouryoTotView(String rebate2YouryoTotView) {
		put("REBATE2_YOURYO_TOT_VIEW", rebate2YouryoTotView);
	}


	/**
	 * 容量計（L）_リベートⅢ類対象容量計を返します。
	 *
	 * @return 容量計（L）_リベートⅢ類対象容量計
	 */
	public String getRebate3YouryoTotView() {
		return (String) get("REBATE3_YOURYO_TOT_VIEW");
	}

	/**
	 * 容量計（L）_リベートⅢ類対象容量計をレコードに格納します。
	 *
	 * @param rebate3YouryoTotView 容量計（L）_リベートⅢ類対象容量計
	 */
	public void setRebate3YouryoTotView(String rebate3YouryoTotView) {
		put("REBATE3_YOURYO_TOT_VIEW", rebate3YouryoTotView);
	}


	/**
	 * 容量計（L）_リベートⅣ類対象容量計を返します。
	 *
	 * @return 容量計（L）_リベートⅣ類対象容量計
	 */
	public String getRebate4YouryoTotView() {
		return (String) get("REBATE4_YOURYO_TOT_VIEW");
	}

	/**
	 * 容量計（L）_リベートⅣ類対象容量計をレコードに格納します。
	 *
	 * @param rebate4YouryoTotView 容量計（L）_リベートⅣ類対象容量計
	 */
	public void setRebate4YouryoTotView(String rebate4YouryoTotView) {
		put("REBATE4_YOURYO_TOT_VIEW", rebate4YouryoTotView);
	}


	/**
	 * 容量計（L）_リベートⅤ類対象容量計を返します。
	 *
	 * @return 容量計（L）_リベートⅤ類対象容量計
	 */
	public String getRebate5YouryoTotView() {
		return (String) get("REBATE5_YOURYO_TOT_VIEW");
	}

	/**
	 * 容量計（L）_リベートⅤ類対象容量計をレコードに格納します。
	 *
	 * @param rebate5YouryoTotView 容量計（L）_リベートⅤ類対象容量計
	 */
	public void setRebate5YouryoTotView(String rebate5YouryoTotView) {
		put("REBATE5_YOURYO_TOT_VIEW", rebate5YouryoTotView);
	}


	/**
	 * 容量計（L）_リベート対象外容量計を返します。
	 *
	 * @return 容量計（L）_リベート対象外容量計
	 */
	public String getRebateoYouryoTotView() {
		return (String) get("REBATEO_YOURYO_TOT_VIEW");
	}

	/**
	 * 容量計（L）_リベート対象外容量計をレコードに格納します。
	 *
	 * @param rebateoYouryoTotView 容量計（L）_リベート対象外容量計
	 */
	public void setRebateoYouryoTotView(String rebateoYouryoTotView) {
		put("REBATEO_YOURYO_TOT_VIEW", rebateoYouryoTotView);
	}


	/**
	 * SDN受注ヘッダーエラー区分を返します。
	 *
	 * @return SDN受注ヘッダーエラー区分
	 */
	public String getSdnHderrKbnView() {
		return (String) get("SDN_HDERR_KBN_VIEW");
	}

	/**
	 * SDN受注ヘッダーエラー区分をレコードに格納します。
	 *
	 * @param sdnHderrKbnView SDN受注ヘッダーエラー区分
	 */
	public void setSdnHderrKbnView(String sdnHderrKbnView) {
		put("SDN_HDERR_KBN_VIEW", sdnHderrKbnView);
	}


	/**
	 * 積荷伝票NO (FK)を返します。
	 *
	 * @return 積荷伝票NO (FK)
	 */
	public String getTumidenNoView() {
		return (String) get("TUMIDEN_NO_VIEW");
	}

	/**
	 * 積荷伝票NO (FK)をレコードに格納します。
	 *
	 * @param tumidenNoView 積荷伝票NO (FK)
	 */
	public void setTumidenNoView(String tumidenNoView) {
		put("TUMIDEN_NO_VIEW", tumidenNoView);
	}


	/**
	 * 積荷累積対象区分を返します。
	 *
	 * @return 積荷累積対象区分
	 */
	public String getTumidenSmKbnView() {
		return (String) get("TUMIDEN_SM_KBN_VIEW");
	}

	/**
	 * 積荷累積対象区分をレコードに格納します。
	 *
	 * @param tumidenSmKbnView 積荷累積対象区分
	 */
	public void setTumidenSmKbnView(String tumidenSmKbnView) {
		put("TUMIDEN_SM_KBN_VIEW", tumidenSmKbnView);
	}


	/**
	 * 積荷伝票発行日を返します。
	 *
	 * @return 積荷伝票発行日
	 */
	public String getTumidenHakoDtView() {
		return (String) get("TUMIDEN_HAKO_DT_VIEW");
	}

	/**
	 * 積荷伝票発行日をレコードに格納します。
	 *
	 * @param tumidenHakoDtView 積荷伝票発行日
	 */
	public void setTumidenHakoDtView(String tumidenHakoDtView) {
		put("TUMIDEN_HAKO_DT_VIEW", tumidenHakoDtView);
	}


	/**
	 * 積荷伝票発行時刻を返します。
	 *
	 * @return 積荷伝票発行時刻
	 */
	public String getTumidenHakoTmView() {
		return (String) get("TUMIDEN_HAKO_TM_VIEW");
	}

	/**
	 * 積荷伝票発行時刻をレコードに格納します。
	 *
	 * @param tumidenHakoTmView 積荷伝票発行時刻
	 */
	public void setTumidenHakoTmView(String tumidenHakoTmView) {
		put("TUMIDEN_HAKO_TM_VIEW", tumidenHakoTmView);
	}


	/**
	 * 積荷伝票発行回数を返します。
	 *
	 * @return 積荷伝票発行回数
	 */
	public String getTumidenHakoCntView() {
		return (String) get("TUMIDEN_HAKO_CNT_VIEW");
	}

	/**
	 * 積荷伝票発行回数をレコードに格納します。
	 *
	 * @param tumidenHakoCntView 積荷伝票発行回数
	 */
	public void setTumidenHakoCntView(String tumidenHakoCntView) {
		put("TUMIDEN_HAKO_CNT_VIEW", tumidenHakoCntView);
	}


	/**
	 * 積荷伝票発行者IDを返します。
	 *
	 * @return 積荷伝票発行者ID
	 */
	public String getTumidenHakosyaView() {
		return (String) get("TUMIDEN_HAKOSYA_VIEW");
	}

	/**
	 * 積荷伝票発行者IDをレコードに格納します。
	 *
	 * @param tumidenHakosyaView 積荷伝票発行者ID
	 */
	public void setTumidenHakosyaView(String tumidenHakosyaView) {
		put("TUMIDEN_HAKOSYA_VIEW", tumidenHakosyaView);
	}


	/**
	 * 酒販店名を返します。
	 *
	 * @return 酒販店名
	 */
	public String getSyuhantenNmView() {
		return (String) get("SYUHANTEN_NM_VIEW");
	}

	/**
	 * 酒販店名をレコードに格納します。
	 *
	 * @param syuhantenNmView 酒販店名
	 */
	public void setSyuhantenNmView(String syuhantenNmView) {
		put("SYUHANTEN_NM_VIEW", syuhantenNmView);
	}


	/**
	 * 運送店名を返します。
	 *
	 * @return 運送店名
	 */
	public String getUnsotenNmView() {
		return (String) get("UNSOTEN_NM_VIEW");
	}

	/**
	 * 運送店名をレコードに格納します。
	 *
	 * @param unsotenNmView 運送店名
	 */
	public void setUnsotenNmView(String unsotenNmView) {
		put("UNSOTEN_NM_VIEW", unsotenNmView);
	}


	/**
	 * 最終送荷先卸CDを返します。
	 *
	 * @return 最終送荷先卸CD
	 */
	public String getOrositenCdLastView() {
		return (String) get("OROSITEN_CD_LAST_VIEW");
	}

	/**
	 * 最終送荷先卸CDをレコードに格納します。
	 *
	 * @param orositenCdLastView 最終送荷先卸CD
	 */
	public void setOrositenCdLastView(String orositenCdLastView) {
		put("OROSITEN_CD_LAST_VIEW", orositenCdLastView);
	}


	/**
	 * 最終送荷先卸名を返します。
	 *
	 * @return 最終送荷先卸名
	 */
	public String getOrositenNmLastView() {
		return (String) get("OROSITEN_NM_LAST_VIEW");
	}

	/**
	 * 最終送荷先卸名をレコードに格納します。
	 *
	 * @param orositenNmLastView 最終送荷先卸名
	 */
	public void setOrositenNmLastView(String orositenNmLastView) {
		put("OROSITEN_NM_LAST_VIEW", orositenNmLastView);
	}


	/**
	 * 担当者名を返します。
	 *
	 * @return 担当者名
	 */
	public String getTantosyaNmView() {
		return (String) get("TANTOSYA_NM_VIEW");
	}

	/**
	 * 担当者名をレコードに格納します。
	 *
	 * @param tantosyaNmView 担当者名
	 */
	public void setTantosyaNmView(String tantosyaNmView) {
		put("TANTOSYA_NM_VIEW", tantosyaNmView);
	}


	/**
	 * 特約店名を返します。
	 *
	 * @return 特約店名
	 */
	public String getTokuyakutenNmView() {
		return (String) get("TOKUYAKUTEN_NM_VIEW");
	}

	/**
	 * 特約店名をレコードに格納します。
	 *
	 * @param tokuyakutenNmView 特約店名
	 */
	public void setTokuyakutenNmView(String tokuyakutenNmView) {
		put("TOKUYAKUTEN_NM_VIEW", tokuyakutenNmView);
	}


	/**
	 * デポ名を返します。
	 *
	 * @return デポ名
	 */
	public String getDepoNmView() {
		return (String) get("DEPO_NM_VIEW");
	}

	/**
	 * デポ名をレコードに格納します。
	 *
	 * @param depoNmView デポ名
	 */
	public void setDepoNmView(String depoNmView) {
		put("DEPO_NM_VIEW", depoNmView);
	}


	/**
	 * 二次店名を返します。
	 *
	 * @return 二次店名
	 */
	public String getNijitenNmView() {
		return (String) get("NIJITEN_NM_VIEW");
	}

	/**
	 * 二次店名をレコードに格納します。
	 *
	 * @param nijitenNmView 二次店名
	 */
	public void setNijitenNmView(String nijitenNmView) {
		put("NIJITEN_NM_VIEW", nijitenNmView);
	}


	/**
	 * 三次店名を返します。
	 *
	 * @return 三次店名
	 */
	public String getSanjitenNmView() {
		return (String) get("SANJITEN_NM_VIEW");
	}

	/**
	 * 三次店名をレコードに格納します。
	 *
	 * @param sanjitenNmView 三次店名
	 */
	public void setSanjitenNmView(String sanjitenNmView) {
		put("SANJITEN_NM_VIEW", sanjitenNmView);
	}


	/**
	 * 入力者CDを返します。
	 *
	 * @return 入力者CD
	 */
	public String getNyuryokusyaCdView() {
		return (String) get("NYURYOKUSYA_CD_VIEW");
	}

	/**
	 * 入力者CDをレコードに格納します。
	 *
	 * @param nyuryokusyaCdView 入力者CD
	 */
	public void setNyuryokusyaCdView(String nyuryokusyaCdView) {
		put("NYURYOKUSYA_CD_VIEW", nyuryokusyaCdView);
	}


	/**
	 * 入力者名を返します。
	 *
	 * @return 入力者名
	 */
	public String getNyuryokusyaNmView() {
		return (String) get("NYURYOKUSYA_NM_VIEW");
	}

	/**
	 * 入力者名をレコードに格納します。
	 *
	 * @param nyuryokusyaNmView 入力者名
	 */
	public void setNyuryokusyaNmView(String nyuryokusyaNmView) {
		put("NYURYOKUSYA_NM_VIEW", nyuryokusyaNmView);
	}


	/**
	 * 都道府県CDを返します。
	 *
	 * @return 都道府県CD
	 */
	public String getTodofuknCdView() {
		return (String) get("TODOFUKN_CD_VIEW");
	}

	/**
	 * 都道府県CDをレコードに格納します。
	 *
	 * @param todofuknCdView 都道府県CD
	 */
	public void setTodofuknCdView(String todofuknCdView) {
		put("TODOFUKN_CD_VIEW", todofuknCdView);
	}


	/**
	 * 倉庫種類区分を返します。
	 *
	 * @return 倉庫種類区分
	 */
	public String getSoukoSyuruiKbnView() {
		return (String) get("SOUKO_SYURUI_KBN_VIEW");
	}

	/**
	 * 倉庫種類区分をレコードに格納します。
	 *
	 * @param soukoSyuruiKbnView 倉庫種類区分
	 */
	public void setSoukoSyuruiKbnView(String soukoSyuruiKbnView) {
		put("SOUKO_SYURUI_KBN_VIEW", soukoSyuruiKbnView);
	}


	/**
	 * 倉入・直送区分を返します。
	 *
	 * @return 倉入・直送区分
	 */
	public String getDeliveryKbnView() {
		return (String) get("DELIVERY_KBN_VIEW");
	}

	/**
	 * 倉入・直送区分をレコードに格納します。
	 *
	 * @param deliveryKbnView 倉入・直送区分
	 */
	public void setDeliveryKbnView(String deliveryKbnView) {
		put("DELIVERY_KBN_VIEW", deliveryKbnView);
	}


	/**
	 * 当日出荷可能区分を返します。
	 *
	 * @return 当日出荷可能区分
	 */
	public String getToujituSyukkaKbnView() {
		return (String) get("TOUJITU_SYUKKA_KBN_VIEW");
	}

	/**
	 * 当日出荷可能区分をレコードに格納します。
	 *
	 * @param toujituSyukkaKbnView 当日出荷可能区分
	 */
	public void setToujituSyukkaKbnView(String toujituSyukkaKbnView) {
		put("TOUJITU_SYUKKA_KBN_VIEW", toujituSyukkaKbnView);
	}


	/**
	 * 出荷日注意区分を返します。
	 *
	 * @return 出荷日注意区分
	 */
	public String getSyukaDtCyuiKbnView() {
		return (String) get("SYUKA_DT_CYUI_KBN_VIEW");
	}

	/**
	 * 出荷日注意区分をレコードに格納します。
	 *
	 * @param syukaDtCyuiKbnView 出荷日注意区分
	 */
	public void setSyukaDtCyuiKbnView(String syukaDtCyuiKbnView) {
		put("SYUKA_DT_CYUI_KBN_VIEW", syukaDtCyuiKbnView);
	}


	/**
	 * 荷受不可曜日_月を返します。
	 *
	 * @return 荷受不可曜日_月
	 */
	public String getNiukeFukaMonView() {
		return (String) get("NIUKE_FUKA_MON_VIEW");
	}

	/**
	 * 荷受不可曜日_月をレコードに格納します。
	 *
	 * @param niukeFukaMonView 荷受不可曜日_月
	 */
	public void setNiukeFukaMonView(String niukeFukaMonView) {
		put("NIUKE_FUKA_MON_VIEW", niukeFukaMonView);
	}


	/**
	 * 荷受不可曜日_火を返します。
	 *
	 * @return 荷受不可曜日_火
	 */
	public String getNiukeFukaTueView() {
		return (String) get("NIUKE_FUKA_TUE_VIEW");
	}

	/**
	 * 荷受不可曜日_火をレコードに格納します。
	 *
	 * @param niukeFukaTueView 荷受不可曜日_火
	 */
	public void setNiukeFukaTueView(String niukeFukaTueView) {
		put("NIUKE_FUKA_TUE_VIEW", niukeFukaTueView);
	}


	/**
	 * 荷受不可曜日_水を返します。
	 *
	 * @return 荷受不可曜日_水
	 */
	public String getNiukeFukaWedView() {
		return (String) get("NIUKE_FUKA_WED_VIEW");
	}

	/**
	 * 荷受不可曜日_水をレコードに格納します。
	 *
	 * @param niukeFukaWedView 荷受不可曜日_水
	 */
	public void setNiukeFukaWedView(String niukeFukaWedView) {
		put("NIUKE_FUKA_WED_VIEW", niukeFukaWedView);
	}


	/**
	 * 荷受不可曜日_木を返します。
	 *
	 * @return 荷受不可曜日_木
	 */
	public String getNiukeFukaThuView() {
		return (String) get("NIUKE_FUKA_THU_VIEW");
	}

	/**
	 * 荷受不可曜日_木をレコードに格納します。
	 *
	 * @param niukeFukaThuView 荷受不可曜日_木
	 */
	public void setNiukeFukaThuView(String niukeFukaThuView) {
		put("NIUKE_FUKA_THU_VIEW", niukeFukaThuView);
	}


	/**
	 * 荷受不可曜日_金を返します。
	 *
	 * @return 荷受不可曜日_金
	 */
	public String getNiukeFukaFriView() {
		return (String) get("NIUKE_FUKA_FRI_VIEW");
	}

	/**
	 * 荷受不可曜日_金をレコードに格納します。
	 *
	 * @param niukeFukaFriView 荷受不可曜日_金
	 */
	public void setNiukeFukaFriView(String niukeFukaFriView) {
		put("NIUKE_FUKA_FRI_VIEW", niukeFukaFriView);
	}


	/**
	 * 荷受不可曜日_土を返します。
	 *
	 * @return 荷受不可曜日_土
	 */
	public String getNiukeFukaSatView() {
		return (String) get("NIUKE_FUKA_SAT_VIEW");
	}

	/**
	 * 荷受不可曜日_土をレコードに格納します。
	 *
	 * @param niukeFukaSatView 荷受不可曜日_土
	 */
	public void setNiukeFukaSatView(String niukeFukaSatView) {
		put("NIUKE_FUKA_SAT_VIEW", niukeFukaSatView);
	}


	/**
	 * 荷受不可曜日_日を返します。
	 *
	 * @return 荷受不可曜日_日
	 */
	public String getNiukeFukaSunView() {
		return (String) get("NIUKE_FUKA_SUN_VIEW");
	}

	/**
	 * 荷受不可曜日_日をレコードに格納します。
	 *
	 * @param niukeFukaSunView 荷受不可曜日_日
	 */
	public void setNiukeFukaSunView(String niukeFukaSunView) {
		put("NIUKE_FUKA_SUN_VIEW", niukeFukaSunView);
	}


	/**
	 * 荷受不可曜日_祝を返します。
	 *
	 * @return 荷受不可曜日_祝
	 */
	public String getNiukeFukaHolView() {
		return (String) get("NIUKE_FUKA_HOL_VIEW");
	}

	/**
	 * 荷受不可曜日_祝をレコードに格納します。
	 *
	 * @param niukeFukaHolView 荷受不可曜日_祝
	 */
	public void setNiukeFukaHolView(String niukeFukaHolView) {
		put("NIUKE_FUKA_HOL_VIEW", niukeFukaHolView);
	}


	/**
	 * 請求先名称(正) 1/2を返します。
	 *
	 * @return 請求先名称(正) 1/2
	 */
	public String getSeikyusakiNm01View() {
		return (String) get("SEIKYUSAKI_NM01_VIEW");
	}

	/**
	 * 請求先名称(正) 1/2をレコードに格納します。
	 *
	 * @param seikyusakiNm01View 請求先名称(正) 1/2
	 */
	public void setSeikyusakiNm01View(String seikyusakiNm01View) {
		put("SEIKYUSAKI_NM01_VIEW", seikyusakiNm01View);
	}


	/**
	 * 請求先名称(正) 2/2を返します。
	 *
	 * @return 請求先名称(正) 2/2
	 */
	public String getSeikyusakiNm02View() {
		return (String) get("SEIKYUSAKI_NM02_VIEW");
	}

	/**
	 * 請求先名称(正) 2/2をレコードに格納します。
	 *
	 * @param seikyusakiNm02View 請求先名称(正) 2/2
	 */
	public void setSeikyusakiNm02View(String seikyusakiNm02View) {
		put("SEIKYUSAKI_NM02_VIEW", seikyusakiNm02View);
	}


	/**
	 * 請求明細グループCD_特約店CDを返します。
	 *
	 * @return 請求明細グループCD_特約店CD
	 */
	public String getSmgpTokuyakutenCdView() {
		return (String) get("SMGP_TOKUYAKUTEN_CD_VIEW");
	}

	/**
	 * 請求明細グループCD_特約店CDをレコードに格納します。
	 *
	 * @param smgpTokuyakutenCdView 請求明細グループCD_特約店CD
	 */
	public void setSmgpTokuyakutenCdView(String smgpTokuyakutenCdView) {
		put("SMGP_TOKUYAKUTEN_CD_VIEW", smgpTokuyakutenCdView);
	}


	/**
	 * 請求明細グループCD_デポCDを返します。
	 *
	 * @return 請求明細グループCD_デポCD
	 */
	public String getSmgpDepoCdView() {
		return (String) get("SMGP_DEPO_CD_VIEW");
	}

	/**
	 * 請求明細グループCD_デポCDをレコードに格納します。
	 *
	 * @param smgpDepoCdView 請求明細グループCD_デポCD
	 */
	public void setSmgpDepoCdView(String smgpDepoCdView) {
		put("SMGP_DEPO_CD_VIEW", smgpDepoCdView);
	}


	/**
	 * 請求明細グループCD_二次店CDを返します。
	 *
	 * @return 請求明細グループCD_二次店CD
	 */
	public String getSmgpNijitenCdView() {
		return (String) get("SMGP_NIJITEN_CD_VIEW");
	}

	/**
	 * 請求明細グループCD_二次店CDをレコードに格納します。
	 *
	 * @param smgpNijitenCdView 請求明細グループCD_二次店CD
	 */
	public void setSmgpNijitenCdView(String smgpNijitenCdView) {
		put("SMGP_NIJITEN_CD_VIEW", smgpNijitenCdView);
	}


	/**
	 * 請求明細グループCD_三次店CDを返します。
	 *
	 * @return 請求明細グループCD_三次店CD
	 */
	public String getSmgpSanjitenCdView() {
		return (String) get("SMGP_SANJITEN_CD_VIEW");
	}

	/**
	 * 請求明細グループCD_三次店CDをレコードに格納します。
	 *
	 * @param smgpSanjitenCdView 請求明細グループCD_三次店CD
	 */
	public void setSmgpSanjitenCdView(String smgpSanjitenCdView) {
		put("SMGP_SANJITEN_CD_VIEW", smgpSanjitenCdView);
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
