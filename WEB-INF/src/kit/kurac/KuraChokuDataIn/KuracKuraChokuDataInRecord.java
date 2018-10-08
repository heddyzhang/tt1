package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConstHM.*;

import java.util.HashMap;
import java.util.Map;

import fb.inf.KitRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 蔵元入力（蔵元データ／ヘッダー部）のレコードクラス
 */
public class KuracKuraChokuDataInRecord extends KitRecord {

	/**
	 *
	 */
	/** シリアルID */
	private static final long serialVersionUID = -2451288206118253406L;

	/** デバッグ */
	boolean isDebug_ = false;


	/** コンストラクタ */
	public KuracKuraChokuDataInRecord() {
		super();
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input Map
	 */
	public KuracKuraChokuDataInRecord(Map<String, Object> input) {
		super(input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input HashMap
	 */
	public KuracKuraChokuDataInRecord(HashMap<String, Object> input) {
		this((Map<String, Object>) input);
		// 親に主キーのありかをセットする
		setPrimaryKeys();
	}

	/**
	 * コンストラクタ。
	 *
	 * @param input KuracKuraChokuDataInRecord
	 */
	public KuracKuraChokuDataInRecord(KuracKuraChokuDataInRecord input) {
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
		KuracKuraChokuDataInRecord newRec = (KuracKuraChokuDataInRecord) reco;
		// 追加モード
		if (isAddMode()) {
			setJigyosyoKbn(newRec.getJigyosyoKbn()); 					// 黄桜事業所区分
			setShoninGrpCd(newRec.getShoninGrpCd());					// 蔵直商品ｸﾞﾙｰﾌﾟCD
			setTodokesakiLineNo(newRec.getTodokesakiLineNo()); 			// 届先No
			setKuradenNo(newRec.getKuradenNo()); 						// 整理No
		}

		setUketukeDt(newRec.getUketukeDt()); 							// 申込み受付日
		setTakuhaiBillKbn(newRec.getTakuhaiBillKbn()); 					// 宅配伝票区分
		setHacyuNo(newRec.getHacyuNo()); 								// 発注NO
		setHasoYoteiDt(newRec.getHasoYoteiDt()); 						// 発送予定日
		setCheckListPrtKbn(newRec.getCheckListPrtKbn()); 				// 酒販店ﾁｪｯｸﾘｽﾄ打出区分
		setUnchinKbn(newRec.getUnchinKbn()); 							// 運賃区分
		setYoutoKbn(newRec.getYoutoKbn()); 								// 用途区分

		setTatesnCd(newRec.getTatesnCd()); 								// 縦線CD
		setTokuyakutenCd(newRec.getTokuyakutenCd()); 					// 特約店CD
		setTokuyakutenNm(newRec.getTokuyakutenNm()); 					// 特約店名(略)
		setDepoCd(newRec.getDepoCd()); 									// デポ（支店）デポCD
		setDepoNm(newRec.getDepoNm()); 									// デポ（支店）デポ名
		setNijitenCd(newRec.getNijitenCd()); 							// 二次店CD
		setNijitenNm(newRec.getNijitenNm()); 							// 二次店名
		setSanjitenCd(newRec.getSanjitenCd());							// 三次店CD
		setSanjitenNm(newRec.getSanjitenNm());							// 三次店名

		setSyuhantenCd(newRec.getSyuhantenCd()); 						// 酒販店（統一）CD
		setSyuhantenNm(newRec.getSyuhantenNm()); 						// 酒販店名
		setSyuhantenZip(newRec.getSyuhantenZip());						// 酒販店 郵便番号
		setSyuhantenAddress(newRec.getSyuhantenAddress());				// 酒販店 住所
		setSyuhantenTel(newRec.getSyuhantenTel());						// 酒販店 TEL

		setUnsotenCd(newRec.getUnsotenCd());							// 運送店CD
		setUnsotenNm(newRec.getUnsotenNm());							// 運送店名

		setTodokesakiNm(newRec.getTodokesakiNm());						// 届け先 届け先名
		setTodokesakiTel(newRec.getTodokesakiTel());					// 届け先 TEL
		setTodokesakiAddress(newRec.getTodokesakiAddress());			// 届け先 住所
		setTodokesakiZip(newRec.getTodokesakiZip());					// 届け先 郵便番号
		setIrainusiNm(newRec.getIrainusiNm());							// 依頼主 依頼主名
		setIrainusiTel(newRec.getIrainusiTel());						// 依頼主 TEL
		setIrainusiAddress(newRec.getIrainusiAddress());				// 依頼主 住所
		setIrainusiZip(newRec.getIrainusiZip());						// 依頼主 郵便番号

		setNosiKbn(newRec.getNosiKbn());								// 【のし】名前記載有無区分
		setNosiComment1(newRec.getNosiComment1());						// のし内容 1行目
		setNosiComment2(newRec.getNosiComment2());						// のし内容 2行目
		setNosiComment3(newRec.getNosiComment3());						// のし内容 3行目
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

			// 黄桜事業所区分
			if (!PbsUtil.isEmpty(getJigyosyoKbn())) {
				bEmpty = false;
			}

			// 蔵直商品ｸﾞﾙｰﾌﾟCD
			if (!PbsUtil.isEmpty(getShoninGrpCd())) {
				bEmpty = false;
			}

			// 届先ﾗｲﾝNo
			if (!PbsUtil.isEmpty(getTodokesakiLineNo())) {
				bEmpty = false;
			}

			// 申込み受付日
			if (!PbsUtil.isEmpty(getUketukeDt())) {
				bEmpty = false;
			}

			// 宅配伝票区分
			if (!PbsUtil.isEmpty(getTakuhaiBillKbn())) {
				bEmpty = false;
			}

			// 発注NO
			if (!PbsUtil.isEmpty(getHacyuNo())) {
				bEmpty = false;
			}

			// 発送予定日
			if (!PbsUtil.isEmpty(getHasoYoteiDt())) {
				bEmpty = false;
			}

			// 酒販店ﾁｪｯｸﾘｽﾄ打出区分
			if (!PbsUtil.isEmpty(getCheckListPrtKbn())) {
				bEmpty = false;
			}

			// 整理No
			if (!PbsUtil.isEmpty(getKuradenNo())) {
				bEmpty = false;
			}

			// 運賃区分
			if (!PbsUtil.isEmpty(getUnchinKbn())) {
				bEmpty = false;
			}

			// 用途区分
			if (!PbsUtil.isEmpty(getYoutoKbn())) {
				bEmpty = false;
			}


			// 縦線CD
			if (!PbsUtil.isEmpty(getTatesnCd())) {
				bEmpty = false;
			}
/**
			// 特約店CD
			if (!PbsUtil.isEmpty(getTokuyakutenCd())) {
				bEmpty = false;
			}

			// 特約店名(略)
			if (!PbsUtil.isEmpty(getTokuyakutenNm())) {
				bEmpty = false;
			}

			// デポ（支店）デポCD
			if (!PbsUtil.isEmpty(getDepoCd())) {
				bEmpty = false;
			}

			// デポ（支店）デポ名
			if (!PbsUtil.isEmpty(getDepoNm())) {
				bEmpty = false;
			}

			// 二次店CD
			if (!PbsUtil.isEmpty(getNijitenCd())) {
				bEmpty = false;
			}

			// 二次店名
			if (!PbsUtil.isEmpty(getNijitenNm())) {
				bEmpty = false;
			}

			// 三次店CD
			if (!PbsUtil.isEmpty(getSanjitenCd())) {
				bEmpty = false;
			}

			// 三次店名
			if (!PbsUtil.isEmpty(getSanjitenNm())) {
				bEmpty = false;
			}
**/
			// 酒販店（統一）CD
			if (!PbsUtil.isEmpty(getSyuhantenCd())) {
				bEmpty = false;
			}
/**
			// 酒販店名
			if (!PbsUtil.isEmpty(getSyuhantenNm())) {
				bEmpty = false;
			}

			// 酒販店 郵便番号
			if (!PbsUtil.isEmpty(getSyuhantenZip())) {
				bEmpty = false;
			}

			// 酒販店 住所
			if (!PbsUtil.isEmpty(getSyuhantenAddress())) {
				bEmpty = false;
			}

			// 酒販店 TEL
			if (!PbsUtil.isEmpty(getSyuhantenTel())) {
				bEmpty = false;
			}
**/


			// 運送店CD
			if (!PbsUtil.isEmpty(getUnsotenCd())) {
				bEmpty = false;
			}
/**
			// 黄桜受注No
			if (!PbsUtil.isEmpty(getJyucyuNo())) {
				bEmpty = false;
			}

**/

			// 届け先 届け先名
			if (!PbsUtil.isEmpty(getTodokesakiNm())) {
				bEmpty = false;
			}

			// 届け先 郵便番号
			if (!PbsUtil.isEmpty(getTodokesakiZip())) {
				bEmpty = false;
			}

			// 届け先 住所
			if (!PbsUtil.isEmpty(getTodokesakiAddress())) {
				bEmpty = false;
			}

			// 届け先 TEL
			if (!PbsUtil.isEmpty(getTodokesakiTel())) {
				bEmpty = false;
			}

			// 依頼主 依頼主名
			if (!PbsUtil.isEmpty(getIrainusiNm())) {
				bEmpty = false;
			}

			// 依頼主 郵便番号
			if (!PbsUtil.isEmpty(getIrainusiZip())) {
				bEmpty = false;
			}

			// 依頼主 住所
			if (!PbsUtil.isEmpty(getIrainusiAddress())) {
				bEmpty = false;
			}

			// 依頼主 TEL
			if (!PbsUtil.isEmpty(getIrainusiTel())) {
				bEmpty = false;
			}

			// 【のし】名前記載有無区分
			if (!PbsUtil.isEmpty(getNosiKbn())) {
				bEmpty = false;
			}

			// のし内容 1行目
			if (!PbsUtil.isEmpty(getNosiComment1())) {
				bEmpty = false;
			}

			// のし内容 2行目
			if (!PbsUtil.isEmpty(getNosiComment2())) {
				bEmpty = false;
			}

			// のし内容 3行目
			if (!PbsUtil.isEmpty(getNosiComment3())) {
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
		KuracKuraChokuDataInRecord target = (KuracKuraChokuDataInRecord) reco;
		// 追加モードの場合（入力項目）
		if (this.isAddMode()) {
			// 黄桜事業所区分
			if (!PbsUtil.isEqual(getJigyosyoKbn(), target.getJigyosyoKbn())) {
				bEquals = false;
			}

			// 蔵直商品ｸﾞﾙｰﾌﾟCD
			if (!PbsUtil.isEqual(getShoninGrpCd(), target.getShoninGrpCd())) {
				bEquals = false;
			}

			// 届先ﾗｲﾝNo
			if (!PbsUtil.isEqual(getTodokesakiLineNo(), target.getTodokesakiLineNo())) {
				bEquals = false;
			}

			// 申込み受付日
			if (!PbsUtil.isEqual(getUketukeDt(), target.getUketukeDt())) {
				bEquals = false;
			}

			// 宅配伝票区分
			if (!PbsUtil.isEqual(getTakuhaiBillKbn(), target.getTakuhaiBillKbn())) {
				bEquals = false;
			}

			// 発注No
			if (!PbsUtil.isEqual(getHacyuNo(), target.getHacyuNo())) {
				bEquals = false;
			}
			// 発送予定日
			if (!PbsUtil.isEqual(getHasoYoteiDt(), target.getHasoYoteiDt())) {
				bEquals = false;
			}

			// 酒販店ﾁｪｯｸﾘｽﾄ打出区分
			if (!PbsUtil.isEqual(getCheckListPrtKbn(), target.getCheckListPrtKbn())) {
				bEquals = false;
			}

			// 整理No
			if (!PbsUtil.isEqual(getKuradenNo(), target.getKuradenNo())) {
				bEquals = false;
			}

			// 運賃区分
			if (!PbsUtil.isEqual(getUnchinKbn(), target.getUnchinKbn())) {
				bEquals = false;
			}

			// 用途区分
			if (!PbsUtil.isEqual(getYoutoKbn(), target.getYoutoKbn())) {
				bEquals = false;
			}

			// 縦線CD
			if (!PbsUtil.isEqual(getTatesnCd(), target.getTatesnCd())) {
				bEquals = false;
			}

			// 酒販店（統一）CD
			if (!PbsUtil.isEqual(getSyuhantenCd(), target.getSyuhantenCd())) {
				bEquals = false;
			}

			// 運送店CD
			if (!PbsUtil.isEqual(getUnsotenCd(), target.getUnsotenCd())) {
				bEquals = false;
			}

			// 届け先 届け先名
			if (!PbsUtil.isEqual(getTodokesakiNm(), target.getTodokesakiNm())) {
				bEquals = false;
			}

			// 届け先 郵便番号
			if (!PbsUtil.isEqual(getTodokesakiZip(), target.getTodokesakiZip())) {
				bEquals = false;
			}

			// 届け先 住所
			if (!PbsUtil.isEqual(getTodokesakiAddress(), target.getTodokesakiAddress())) {
				bEquals = false;
			}

			// 届け先 TEL
			if (!PbsUtil.isEqual(getTodokesakiTel(), target.getTodokesakiTel())) {
				bEquals = false;
			}

			// 依頼主 依頼主名
			if (!PbsUtil.isEqual(getIrainusiNm(), target.getIrainusiNm())) {
				bEquals = false;
			}

			// 依頼主 郵便番号
			if (!PbsUtil.isEqual(getIrainusiZip(), target.getIrainusiZip())) {
				bEquals = false;
			}

			// 依頼主 住所
			if (!PbsUtil.isEqual(getIrainusiAddress(), target.getIrainusiAddress())) {
				bEquals = false;
			}

			// 依頼主 TEL
			if (!PbsUtil.isEqual(getIrainusiTel(), target.getIrainusiTel())) {
				bEquals = false;
			}

			// 【のし】名前記載有無区分
			if (!PbsUtil.isEqual(getNosiKbn(), target.getNosiKbn())) {
				bEquals = false;
			}

			// のし内容 1行目
			if (!PbsUtil.isEqual(getNosiComment1(), target.getNosiComment1())) {
				bEquals = false;
			}

			// のし内容 2行目
			if (!PbsUtil.isEqual(getNosiComment2(), target.getNosiComment2())) {
				bEquals = false;
			}

			// のし内容 3行目
			if (!PbsUtil.isEqual(getNosiComment3(), target.getNosiComment3())) {
				bEquals = false;
			}

		// 変更モードの場合（主キー項目以外）
		} else {
			// 申込み受付日
			if (!PbsUtil.isEqual(getUketukeDt(), target.getUketukeDt())) {
				setUketukeDtClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setUketukeDtClass(STYLE_CLASS_NO_EDIT);
			}

			// 宅配伝票区分
			if (!PbsUtil.isEqual(getTakuhaiBillKbn(), target.getTakuhaiBillKbn())) {
				setTakuhaiBillKbnClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTakuhaiBillKbnClass(STYLE_CLASS_NO_EDIT);
			}

			// 発注No
			if (!PbsUtil.isEqual(getHacyuNo(), target.getHacyuNo())) {
				setHacyuNoClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setHacyuNoClass(STYLE_CLASS_NO_EDIT);
			}

			// 発送予定日
			if (!PbsUtil.isEqual(getHasoYoteiDt(), target.getHasoYoteiDt())) {
				setHasoYoteiDtClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setHasoYoteiDtClass(STYLE_CLASS_NO_EDIT);
			}

			// 酒販店ﾁｪｯｸﾘｽﾄ打出区分
			if (!PbsUtil.isEqual(getCheckListPrtKbn(), target.getCheckListPrtKbn())) {
				setCheckListPrtKbnClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setCheckListPrtKbnClass(STYLE_CLASS_NO_EDIT);
			}

			// 整理No
			if (!PbsUtil.isEqual(getKuradenNo(), target.getKuradenNo())) {
				setKuradenNoClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setKuradenNoClass(STYLE_CLASS_NO_EDIT);
			}

			// 運賃区分
			if (!PbsUtil.isEqual(getUnchinKbn(), target.getUnchinKbn())) {
				setUnchinKbnClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setUnchinKbnClass(STYLE_CLASS_NO_EDIT);
			}

			// 用途区分
			if (!PbsUtil.isEqual(getYoutoKbn(), target.getYoutoKbn())) {
				setYoutoKbnClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setYoutoKbnClass(STYLE_CLASS_NO_EDIT);
			}

			// 縦線CD
			if (!PbsUtil.isEqual(getTatesnCd(), target.getTatesnCd())) {
				setTatesnCdClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTatesnCdClass(STYLE_CLASS_NO_EDIT);
			}

			// 酒販店（統一）CD
			if (!PbsUtil.isEqual(getSyuhantenCd(), target.getSyuhantenCd())) {
				setSyuhantenCdClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setSyuhantenCdClass(STYLE_CLASS_NO_EDIT);
			}

			// 運送店CD
			if (!PbsUtil.isEqual(getUnsotenCd(), target.getUnsotenCd())) {
				setUnsotenCdClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setUnsotenCdClass(STYLE_CLASS_NO_EDIT);
			}

			// 届け先 届け先名
			if (!PbsUtil.isEqual(getTodokesakiNm(), target.getTodokesakiNm())) {
				setTodokesakiNmClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTodokesakiNmClass(STYLE_CLASS_NO_EDIT);
			}

			// 届け先 TEL
			if (!PbsUtil.isEqual(getTodokesakiTel(), target.getTodokesakiTel())) {
				setTodokesakiTelClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTodokesakiTelClass(STYLE_CLASS_NO_EDIT);
			}

			// 届け先 住所
			if (!PbsUtil.isEqual(getTodokesakiAddress(), target.getTodokesakiAddress())) {
				setTodokesakiAddressClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTodokesakiAddressClass(STYLE_CLASS_NO_EDIT);
			}

			// 届け先 郵便番号
			if (!PbsUtil.isEqual(getTodokesakiZip(), target.getTodokesakiZip())) {
				setTodokesakiZipClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setTodokesakiZipClass(STYLE_CLASS_NO_EDIT);
			}

			// 依頼主 依頼主名
			if (!PbsUtil.isEqual(getIrainusiNm(), target.getIrainusiNm())) {
				setIrainusiNmClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setIrainusiNmClass(STYLE_CLASS_NO_EDIT);
			}

			// 依頼主 TEL
			if (!PbsUtil.isEqual(getIrainusiTel(), target.getIrainusiTel())) {
				setIrainusiTelClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setIrainusiTelClass(STYLE_CLASS_NO_EDIT);
			}

			// 依頼主 住所
			if (!PbsUtil.isEqual(getIrainusiAddress(), target.getIrainusiAddress())) {
				setIrainusiAddressClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setIrainusiAddressClass(STYLE_CLASS_NO_EDIT);
			}

			// 依頼主 郵便番号
			if (!PbsUtil.isEqual(getIrainusiZip(), target.getIrainusiZip())) {
				setIrainusiZipClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setIrainusiZipClass(STYLE_CLASS_NO_EDIT);
			}

			// 【のし】名前記載有無区分
			if (!PbsUtil.isEqual(getNosiKbn(), target.getNosiKbn())) {
				setNosikbnClass(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setNosikbnClass(STYLE_CLASS_NO_EDIT);
			}

			// のし内容 1行目
			if (!PbsUtil.isEqual(getNosiComment1(), target.getNosiComment1())) {
				setNosiComment1Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setNosiComment1Class(STYLE_CLASS_NO_EDIT);
			}

			// のし内容 2行目
			if (!PbsUtil.isEqual(getNosiComment2(), target.getNosiComment2())) {
				setNosiComment2Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setNosiComment2Class(STYLE_CLASS_NO_EDIT);
			}

			// のし内容 3行目
			if (!PbsUtil.isEqual(getNosiComment3(), target.getNosiComment3())) {
				setNosiComment3Class(STYLE_CLASS_MODIFIED);
				bEquals = false;
			} else if (!getModified()) {
				setNosiComment3Class(STYLE_CLASS_NO_EDIT);
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
				,"JIGYOSYO_KBN_CLASS"
				,"SHOHIN_GRP_CD_CLASS"
				,"KURADEN_NO_CLASS"
				,"TODOKESAKI_LINE_NO_CLASS"
				,"TAKUHAI_BILL_KBN_CLASS"
				,"UKETUKE_DT_CLASS"
				,"HASO_YOTEI_DT_CLASS"
				,"TATESN_CD_CLASS"
				,"SYUHANTEN_CD_CLASS"
				,"UNCHIN_KBN_CLASS"
				,"UNSOTEN_CD_CLASS"
				,"HACYU_NO_CLASS"
				,"IRAINUSI_NM_CLASS"
				,"IRAINUSI_ZIP_CLASS"
				,"IRAINUSI_ADDRESS_CLASS"
				,"IRAINUSI_TEL_CLASS"
				,"TODOKESAKI_NM_CLASS"
				,"TODOKESAKI_ZIP_CLASS"
				,"TODOKESAKI_ADDRESS_CLASS"
				,"TODOKESAKI_TEL_CLASS"
				,"YOUTO_KBN_CLASS"
				,"NOSI_KBN_CLASS"
				,"NOSI_COMMENT1_CLASS"
				,"NOSI_COMMENT2_CLASS"
				,"NOSI_COMMENT3_CLASS"
				,"CHECK_LIST_PRT_KBN_CLASS"
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
	 * @param rec kuracKuraChokuDataInRecord
	 * @return TRUE:主キー項目が重複している / FALSE:重複無し
	 */
	public boolean isDuplicated(KuracKuraChokuDataInRecord rec){
		boolean ret = true;

		// 会社CD + 蔵直ﾃﾞｰﾀ連番
		if (!PbsUtil.isEqualIgnoreZero(getKaisyaCd()+getKuradataNo(), rec.getKaisyaCd()+rec.getKuradataNo())) {
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
				,"JIGYOSYO_KBN_CLASS"
				,"SHOHIN_GRP_CD_CLASS"
				,"KURADEN_NO_CLASS"
				,"TODOKESAKI_LINE_NO_CLASS"
				,"TAKUHAI_BILL_KBN_CLASS"
				,"UKETUKE_DT_CLASS"
				,"HASO_YOTEI_DT_CLASS"
				,"TATESN_CD_CLASS"
				,"SYUHANTEN_CD_CLASS"
				,"UNCHIN_KBN_CLASS"
				,"UNSOTEN_CD_CLASS"
				,"HACYU_NO_CLASS"
				,"IRAINUSI_NM_CLASS"
				,"IRAINUSI_ZIP_CLASS"
				,"IRAINUSI_ADDRESS_CLASS"
				,"IRAINUSI_TEL_CLASS"
				,"TODOKESAKI_NM_CLASS"
				,"TODOKESAKI_ZIP_CLASS"
				,"TODOKESAKI_ADDRESS_CLASS"
				,"TODOKESAKI_TEL_CLASS"
				,"YOUTO_KBN_CLASS"
				,"NOSI_KBN_CLASS"
				,"NOSI_COMMENT1_CLASS"
				,"NOSI_COMMENT2_CLASS"
				,"NOSI_COMMENT3_CLASS"
				,"CHECK_LIST_PRT_KBN_CLASS"
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
		// 会社CD、蔵直ﾃﾞｰﾀ連番
		super.setPrimaryKeys("KAISYA_CD", "KURADATA_NO");
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

	/**
	 * 申込受付Noを返します。
	 *
	 * @return 利用区分
	 */
	public String getMousikomiNo() {
		return (String) get("MOUSIKOMI_NO");
	}

	/**
	 *  申込受付Noをレコードに格納します。
	 *
	 * @param mousikomiNo 申込受付No
	 */
	public void setMousikomiNo(String mousikomiNo) {
		put("MOUSIKOMI_NO", mousikomiNo);
	}


	/**
	 * 更新日時を返します。
	 *
	 * @return 更新日時
	 */
	public String getKousinDt() {
		return (String) get("KOUSIN_DT");
	}

	/**
	 *  更新日時をレコードに格納します。
	 *
	 * @param kousinDt 更新日時
	 */
	public void setKousinDt(String kousinDt) {
		put("KOUSIN_DT", kousinDt);
	}

	/**
	 * 更新者名を返します。
	 *
	 * @return 更新者名
	 */
	public String getSgyosyaNm() {
		return (String) get("SGYOSYA_NM");
	}

	/**
	 *  更新者名をレコードに格納します。
	 *
	 * @param sgyosyaNm 更新者
	 */
	public void setSgyosyaNm(String sgyosyaNm) {
		put("SGYOSYA_NM", sgyosyaNm);
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
	 * 黄桜事業所区分を返します。
	 *
	 * @return 黄桜事業所区分
	 */
	public String getJigyosyoKbn() {
		return (String) get("JIGYOSYO_KBN");
	}

	/**
	 * 黄桜事業所区分をレコードに格納します。
	 *
	 * @param jigyosyoKbn 黄桜事業所区分
	 */
	public void setJigyosyoKbn(String jigyosyoKbn) {
		put("JIGYOSYO_KBN", jigyosyoKbn);
	}


	/**
	 * 蔵直商品ｸﾞﾙｰﾌﾟCDを返します。
	 *
	 * @return 蔵直商品ｸﾞﾙｰﾌﾟCD
	 */
	public String getShoninGrpCd() {
		return (String) get("SHOHIN_GRP_CD");
	}

	/**
	 * 蔵直商品ｸﾞﾙｰﾌﾟCDをレコードに格納します。
	 *
	 * @param shoninGrpCd 蔵直商品ｸﾞﾙｰﾌﾟCD
	 */
	public void setShoninGrpCd(String shoninGrpCd) {
		put("SHOHIN_GRP_CD", shoninGrpCd);
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
	 * 整理Noを返します。
	 *
	 * @return 整理No
	 */
	public String getKuradenNo() {
		return (String) get("KURADEN_NO");
	}

	/**
	 * 整理Noをレコードに格納します。
	 *
	 * @param kuradenNo 整理No
	 */
	public void setKuradenNo(String kuradenNo) {
		put("KURADEN_NO", kuradenNo);
	}


	/**
	 * 届先ﾗｲﾝNOを返します。
	 *
	 * @return 届先ﾗｲﾝNO
	 */
	public String getTodokesakiLineNo() {
		return (String) get("TODOKESAKI_LINE_NO");
	}

	/**
	 * 届先ﾗｲﾝNOをレコードに格納します。
	 *
	 * @param todokesakiLineNo 届先ﾗｲﾝNO
	 */
	public void setTodokesakiLineNo(String todokesakiLineNo) {
		put("TODOKESAKI_LINE_NO", todokesakiLineNo);
	}


	/**
	 * 訂正時訂正元蔵直ﾃﾞｰﾀ連番を返します。
	 *
	 * @return 訂正時訂正元蔵直ﾃﾞｰﾀ連番
	 */
	public String getTeiseiKuradataNo() {
		return (String) get("TEISEI_KURADATA_NO");
	}

	/**
	 * 訂正時訂正元蔵直ﾃﾞｰﾀ連番をレコードに格納します。
	 *
	 * @param syukaDt 訂正時訂正元蔵直ﾃﾞｰﾀ連番
	 */
	public void setTeiseiKuradataNo(String teiseiKuradataNo) {
		put("TEISEI_KURADATA_NO", teiseiKuradataNo);
	}


	/**
	 * 宅配伝票区分を返します。
	 *
	 * @return 宅配伝票区分
	 */
	public String getTakuhaiBillKbn() {
		return (String) get("TAKUHAI_BILL_KBN");
	}

	/**
	 * 宅配伝票区分をレコードに格納します。
	 *
	 * @param takuhaiBillKbn 宅配伝票区分
	 */
	public void setTakuhaiBillKbn(String takuhaiBillKbn) {
		put("TAKUHAI_BILL_KBN", takuhaiBillKbn);
	}


	/**
	 * 申込み受付日を返します。
	 *
	 * @return 申込み受付日
	 */
	public String getUketukeDt() {
		return (String) get("UKETUKE_DT");
	}

	/**
	 * 申込み受付日をレコードに格納します。
	 *
	 * @param uketukeDt 申込み受付日
	 */
	public void setUketukeDt(String uketukeDt) {
		put("UKETUKE_DT", uketukeDt);
	}


	/**
	 * 発送予定日を返します。
	 *
	 * @return 発送予定日
	 */
	public String getHasoYoteiDt() {
		return (String) get("HASO_YOTEI_DT");
	}

	/**
	 * 発送予定日をレコードに格納します。
	 *
	 * @param hasoToteiDt 発送予定日
	 */
	public void setHasoYoteiDt(String hasoYoteiDt) {
		put("HASO_YOTEI_DT", hasoYoteiDt);
	}


	/**
	 * 縦線CDを返します。
	 *
	 * @return 縦線CD
	 */
	public String getTatesnCd() {
		return (String) get("TATESN_CD");
	}

	/**
	 * 縦線CDをレコードに格納します。
	 *
	 * @param tatesenCd 縦線CD
	 */
	public void setTatesnCd(String tatesnCd) {
		put("TATESN_CD", tatesnCd);
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
	 * 酒販店 郵便番号を返します。
	 *
	 * @return 酒販店 郵便番号
	 */
	public String getSyuhantenZip() {
		return (String) get("SYUHANTEN_ZIP");
	}

	/**
	 * 酒販店 郵便番号をレコードに格納します。
	 *
	 * @param syuhantenZip 酒販店 郵便番号
	 */
	public void setSyuhantenZip(String syuhantenZip) {
		put("SYUHANTEN_ZIP", syuhantenZip);
	}


	/**
	 * 酒販店 住所を返します。
	 *
	 * @return 酒販店 住所
	 */
	public String getSyuhantenAddress() {
		return (String) get("SYUHANTEN_ADDRESS");
	}

	/**
	 * 酒販店 住所をレコードに格納します。
	 *
	 * @param SyuhantenAddress 酒販店 住所
	 */
	public void setSyuhantenAddress(String SyuhantenAddress) {
		put("SYUHANTEN_ADDRESS", SyuhantenAddress);
	}


	/**
	 * 酒販店 TELを返します。
	 *
	 * @return 酒販店 TEL
	 */
	public String getSyuhantenTel() {
		return (String) get("SYUHANTEN_TEL");
	}

	/**
	 * 酒販店 TELをレコードに格納します。
	 *
	 * @param syuhantenTel 酒販店 TEL
	 */
	public void setSyuhantenTel(String syuhantenTel) {
		put("SYUHANTEN_TEL", syuhantenTel);
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
	 * 黄桜受注Noを返します。
	 *
	 * @return 黄桜受注No
	 */
	public String getJyucyuNo() {
		return (String) get("JYUCYU_NO");
	}

	/**
	 * 黄桜受注Noをレコードに格納します。
	 *
	 * @param jyucyuNo 黄桜受注No
	 */
	public void setJyucyuNo(String jyucyuNo) {
		put("JYUCYU_NO", jyucyuNo);
	}


	/**
	 * 発注NOを返します。
	 *
	 * @return 発注NO
	 */
	public String getHacyuNo() {
		return (String) get("HACYU_NO");
	}

	/**
	 * 発注NOをレコードに格納します。
	 *
	 * @param hacyuNo 発注NO
	 */
	public void setHacyuNo(String hacyuNo) {
		put("HACYU_NO", hacyuNo);
	}


	/**
	 * 依頼主 依頼主名を返します。
	 *
	 * @return 依頼主 依頼主名
	 */
	public String getIrainusiNm() {
		return (String) get("IRAINUSI_NM");
	}

	/**
	 * 依頼主 依頼主名をレコードに格納します。
	 *
	 * @param irainusiNm 依頼主 依頼主名
	 */
	public void setIrainusiNm(String irainusiNm) {
		put("IRAINUSI_NM", irainusiNm);
	}


	/**
	 * 依頼主 郵便番号を返します。
	 *
	 * @return 依頼主 郵便番号
	 */
	public String getIrainusiZip() {
		return (String) get("IRAINUSI_ZIP");
	}

	/**
	 * 依頼主 郵便番号をレコードに格納します。
	 *
	 * @param irainusiZip 依頼主 郵便番号
	 */
	public void setIrainusiZip(String irainusiZip) {
		put("IRAINUSI_ZIP", irainusiZip);
	}


	/**
	 * 依頼主 住所を返します。
	 *
	 * @return 依頼主 住所
	 */
	public String getIrainusiAddress() {
		return (String) get("IRAINUSI_ADDRESS");
	}

	/**
	 * 依頼主 住所をレコードに格納します。
	 *
	 * @param irainusiZip 依頼主 住所
	 */
	public void setIrainusiAddress(String irainusiAddress) {
		put("IRAINUSI_ADDRESS", irainusiAddress);
	}


	/**
	 * 依頼主 TELを返します。
	 *
	 * @return 依頼主 TEL
	 */
	public String getIrainusiTel() {
		return (String) get("IRAINUSI_TEL");
	}

	/**
	 * 依頼主 TELをレコードに格納します。
	 *
	 * @param irainusiTel 依頼主 TEL
	 */
	public void setIrainusiTel(String irainusiTel) {
		put("IRAINUSI_TEL", irainusiTel);
	}


	/**
	 * 届け先 届け先名を返します。
	 *
	 * @return 届け先 届け先名
	 */
	public String getTodokesakiNm() {
		return (String) get("TODOKESAKI_NM");
	}

	/**
	 * 届け先 届け先名をレコードに格納します。
	 *
	 * @param todokesakiNm 届け先 届け先名
	 */
	public void setTodokesakiNm(String todokesakiNm) {
		put("TODOKESAKI_NM", todokesakiNm);
	}


	/**
	 * 届け先 郵便番号を返します。
	 *
	 * @return 届け先 郵便番号
	 */
	public String getTodokesakiZip() {
		return (String) get("TODOKESAKI_ZIP");
	}

	/**
	 * 届け先 郵便番号をレコードに格納します。
	 *
	 * @param todokesakiZip 届け先 郵便番号
	 */
	public void setTodokesakiZip(String todokesakiZip) {
		put("TODOKESAKI_ZIP", todokesakiZip);
	}


	/**
	 * 届け先 住所を返します。
	 *
	 * @return 届け先 住所
	 */
	public String getTodokesakiAddress() {
		return (String) get("TODOKESAKI_ADDRESS");
	}

	/**
	 * 届け先 住所をレコードに格納します。
	 *
	 * @param todokesakiZip 届け先 住所
	 */
	public void setTodokesakiAddress(String todokesakiAddress) {
		put("TODOKESAKI_ADDRESS", todokesakiAddress);
	}


	/**
	 * 届け先 TELを返します。
	 *
	 * @return 届け先 TEL
	 */
	public String getTodokesakiTel() {
		return (String) get("TODOKESAKI_TEL");
	}

	/**
	 * 届け先 TELをレコードに格納します。
	 *
	 * @param todokesakiTel 届け先 TEL
	 */
	public void setTodokesakiTel(String todokesakiTel) {
		put("TODOKESAKI_TEL", todokesakiTel);
	}


	/**
	 * 用途区分を返します。
	 *
	 * @return 用途区分
	 */
	public String getYoutoKbn() {
		return (String) get("YOUTO_KBN");
	}

	/**
	 * 用途区分をレコードに格納します。
	 *
	 * @param youtoKbn 用途区分
	 */
	public void setYoutoKbn(String youtoKbn) {
		put("YOUTO_KBN", youtoKbn);
	}


	/**
	 * 【のし】名前記載有無区分を返します。
	 *
	 * @return 【のし】名前記載有無区分
	 */
	public String getNosiKbn() {
		return (String) get("NOSI_KBN");
	}

	/**
	 * 【のし】名前記載有無区分をレコードに格納します。
	 *
	 * @param nosiKbn 【のし】名前記載有無区分
	 */
	public void setNosiKbn(String nosiKbn) {
		put("NOSI_KBN", nosiKbn);
	}


	/**
	 * のし内容 1行目を返します。
	 *
	 * @return のし内容 1行目
	 */
	public String getNosiComment1() {
		return (String) get("NOSI_COMMENT1");
	}

	/**
	 * のし内容 1行目をレコードに格納します。
	 *
	 * @param nosiComment1 のし内容 1行目
	 */
	public void setNosiComment1(String nosiComment1) {
		put("NOSI_COMMENT1", nosiComment1);
	}


	/**
	 * のし内容 2行目を返します。
	 *
	 * @return のし内容 2行目
	 */
	public String getNosiComment2() {
		return (String) get("NOSI_COMMENT2");
	}

	/**
	 * のし内容 2行目をレコードに格納します。
	 *
	 * @param nosiComment2 のし内容 2行目
	 */
	public void setNosiComment2(String nosiComment2) {
		put("NOSI_COMMENT2", nosiComment2);
	}


	/**
	 * のし内容 3行目を返します。
	 *
	 * @return のし内容 3行目
	 */
	public String getNosiComment3() {
		return (String) get("NOSI_COMMENT3");
	}

	/**
	 * のし内容 3行目をレコードに格納します。
	 *
	 * @param nosiComment3 のし内容 3行目
	 */
	public void setNosiComment3(String nosiComment3) {
		put("NOSI_COMMENT3", nosiComment3);
	}


	/**
	 * 酒販店ﾁｪｯｸﾘｽﾄ打出区分を返します。
	 *
	 * @return 酒販店ﾁｪｯｸﾘｽﾄ打出区分
	 */
	public String getCheckListPrtKbn() {
		return (String) get("CHECK_LIST_PRT_KBN");
	}

	/**
	 * 酒販店ﾁｪｯｸﾘｽﾄ打出区分をレコードに格納します。
	 *
	 * @param checkListPrtKbn 酒販店ﾁｪｯｸﾘｽﾄ打出区分
	 */
	public void setCheckListPrtKbn(String checkListPrtKbn) {
		put("CHECK_LIST_PRT_KBN", checkListPrtKbn);
	}

	/**
	 * 入力情報区分をレコードに格納します。
	 *
	 * @param nyuryokusyaCd 入力情報区分
	 */
	public void setInputKbn(String inputKbn) {
		put("INPUT_KBN", inputKbn);
	}


	/**
	 * 入力情報区分を返します。
	 *
	 * @return 入力情報区分
	 */
	public String getInputKbn() {
		return (String) get("INPUT_KBN");
	}

	/**
	 * メーカNoをレコードに格納します。
	 *
	 * @param nyuryokusyaCd 入力情報区分
	 */
	public void setMakerNo(String makerNo) {
		put("MAKER_NO", makerNo);
	}


	/**
	 * メーカNoを返します。
	 *
	 * @return 入力情報区分
	 */
	public String getMakerNo() {
		return (String) get("MAKER_NO");
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
	 * 黄桜事業所区分を返します。
	 *
	 * @return 黄桜事業所区分
	 */
	public String getJigyosyoKbnView() {
		return (String) get("JIGYOSYO_KBN_VIEW");
	}

	/**
	 * 黄桜事業所区分をレコードに格納します。
	 *
	 * @param jigyosyoKbnView 黄桜事業所区分
	 */
	public void setJigyosyoKbnView(String jigyosyoKbnView) {
		put("JIGYOSYO_KBN_VIEW", jigyosyoKbnView);
	}


	/**
	 * 蔵直商品ｸﾞﾙｰﾌﾟCDを返します。
	 *
	 * @return 蔵直商品ｸﾞﾙｰﾌﾟCD
	 */
	public String getShoninGrpCdView() {
		return (String) get("SHOHIN_GRP_CD_VIEW");
	}

	/**
	 * 蔵直商品ｸﾞﾙｰﾌﾟCDをレコードに格納します。
	 *
	 * @param shoninGrpCdView 蔵直商品ｸﾞﾙｰﾌﾟCD
	 */
	public void setShoninGrpCdView(String shoninGrpCdView) {
		put("SHOHIN_GRP_CD_VIEW", shoninGrpCdView);
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
	 * 整理Noを返します。
	 *
	 * @return 整理No
	 */
	public String getKuradenNoView() {
		return (String) get("KURADEN_NO_VIEW");
	}

	/**
	 * 整理Noをレコードに格納します。
	 *
	 * @param kuradenNoView 整理No
	 */
	public void setKuradenNoView(String kuradenNoView) {
		put("KURADEN_NO_VIEW", kuradenNoView);
	}


	/**
	 * 届先ﾗｲﾝNOを返します。
	 *
	 * @return 届先ﾗｲﾝNO
	 */
	public String getTodokesakiLineNoView() {
		return (String) get("TODOKESAKI_LINE_NO_VIEW");
	}

	/**
	 * 届先ﾗｲﾝNOをレコードに格納します。
	 *
	 * @param todokesakiLineNoView 届先ﾗｲﾝNO
	 */
	public void setTodokesakiLineNoView(String todokesakiLineNoView) {
		put("TODOKESAKI_LINE_NO_VIEW", todokesakiLineNoView);
	}


	/**
	 * 訂正時訂正元蔵直ﾃﾞｰﾀ連番を返します。
	 *
	 * @return 訂正時訂正元蔵直ﾃﾞｰﾀ連番
	 */
	public String getTeiseiKuradataNoView() {
		return (String) get("TEISEI_KURADATA_NO_VIEW");
	}

	/**
	 * 訂正時訂正元蔵直ﾃﾞｰﾀ連番をレコードに格納します。
	 *
	 * @param teiseiKuradataNoView 訂正時訂正元蔵直ﾃﾞｰﾀ連番
	 */
	public void setTeiseiKuradataNoView(String teiseiKuradataNoView) {
		put("TEISEI_KURADATA_NO_VIEW", teiseiKuradataNoView);
	}


	/**
	 * 宅配伝票区分を返します。
	 *
	 * @return 宅配伝票区分
	 */
	public String getTakuhaiBillKbnView() {
		return (String) get("TAKUHAI_BILL_KBN_VIEW");
	}

	/**
	 * 宅配伝票区分をレコードに格納します。
	 *
	 * @param takuhaiBillKbnView 宅配伝票区分
	 */
	public void setTakuhaiBillKbnView(String takuhaiBillKbnView) {
		put("TAKUHAI_BILL_KBN_VIEW", takuhaiBillKbnView);
	}


	/**
	 * 申込み受付日を返します。
	 *
	 * @return 申込み受付日
	 */
	public String getUketukeDtView() {
		return (String) get("UKETUKE_DT_VIEW");
	}

	/**
	 * 申込み受付日をレコードに格納します。
	 *
	 * @param uketukeDtView 申込み受付日
	 */
	public void setUketukeDtView(String uketukeDtView) {
		put("UKETUKE_DT_VIEW", uketukeDtView);
	}


	/**
	 * 発送予定日を返します。
	 *
	 * @return 発送予定日
	 */
	public String getHasoYoteiDtView() {
		return (String) get("HASO_YOTEI_DT_VIEW");
	}

	/**
	 * 発送予定日をレコードに格納します。
	 *
	 * @param hasoToteiDtView 発送予定日
	 */
	public void setHasoYoteiDtView(String hasoYoteiDtView) {
		put("HASO_YOTEI_DT_VIEW", hasoYoteiDtView);
	}


	/**
	 * 縦線CDを返します。
	 *
	 * @return 縦線CD
	 */
	public String getTatesnCdView() {
		return (String) get("TATESN_CD_VIEW");
	}

	/**
	 * 縦線CDをレコードに格納します。
	 *
	 * @param tatesenCdView 縦線CD
	 */
	public void setTatesnCdView(String tatesnCdView) {
		put("TATESN_CD_VIEW", tatesnCdView);
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
	 * 酒販店 郵便番号を返します。
	 *
	 * @return 酒販店 郵便番号
	 */
	public String getSyuhantenZipView() {
		return (String) get("SYUHANTEN_ZIP_VIEW");
	}

	/**
	 * 酒販店 郵便番号をレコードに格納します。
	 *
	 * @param syuhantenZipView 酒販店 郵便番号
	 */
	public void setSyuhantenZipView(String syuhantenZipView) {
		put("SYUHANTEN_ZIP_VIEW", syuhantenZipView);
	}


	/**
	 * 酒販店 住所を返します。
	 *
	 * @return 酒販店 住所
	 */
	public String getSyuhantenAddressView() {
		return (String) get("SYUHANTEN_ADDRESS_VIEW");
	}

	/**
	 * 酒販店 住所をレコードに格納します。
	 *
	 * @param SyuhantenAddressView 酒販店 住所
	 */
	public void setSyuhantenAddressView(String SyuhantenAddressView) {
		put("SYUHANTEN_ADDRESS_VIEW", SyuhantenAddressView);
	}


	/**
	 * 酒販店 TELを返します。
	 *
	 * @return 酒販店 TEL
	 */
	public String getSyuhantenTelView() {
		return (String) get("SYUHANTEN_TEL_VIEW");
	}

	/**
	 * 酒販店 TELをレコードに格納します。
	 *
	 * @param syuhantenTelView 酒販店 TEL
	 */
	public void setSyuhantenTelView(String syuhantenTelView) {
		put("SYUHANTEN_TEL_VIEW", syuhantenTelView);
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
	 * 黄桜受注Noを返します。
	 *
	 * @return 黄桜受注No
	 */
	public String getJyucyuNoView() {
		return (String) get("JYUCYU_NO_VIEW");
	}

	/**
	 * 黄桜受注Noをレコードに格納します。
	 *
	 * @param jyucyuNoView 黄桜受注No
	 */
	public void setJyucyuNoView(String jyucyuNoView) {
		put("JYUCYU_NO_VIEW", jyucyuNoView);
	}


	/**
	 * 発注NOを返します。
	 *
	 * @return 発注NO
	 */
	public String getHacyuNoView() {
		return (String) get("HACYU_NO_VIEW");
	}

	/**
	 * 発注NOをレコードに格納します。
	 *
	 * @param hacyuNoView 発注NO
	 */
	public void setHacyuNoView(String hacyuNoView) {
		put("HACYU_NO_VIEW", hacyuNoView);
	}


	/**
	 * 依頼主 依頼主名を返します。
	 *
	 * @return 依頼主 依頼主名
	 */
	public String getIrainusiNmView() {
		return (String) get("IRAINUSI_NM_VIEW");
	}

	/**
	 * 依頼主 依頼主名をレコードに格納します。
	 *
	 * @param irainusiNmView 依頼主 依頼主名
	 */
	public void setIrainusiNmView(String irainusiNmView) {
		put("IRAINUSI_NM_VIEW", irainusiNmView);
	}


	/**
	 * 依頼主 郵便番号を返します。
	 *
	 * @return 依頼主 郵便番号
	 */
	public String getIrainusiZipView() {
		return (String) get("IRAINUSI_ZIP_VIEW");
	}

	/**
	 * 依頼主 郵便番号をレコードに格納します。
	 *
	 * @param irainusiZipView 依頼主 郵便番号
	 */
	public void setIrainusiZipView(String irainusiZipView) {
		put("IRAINUSI_ZIP_VIEW", irainusiZipView);
	}


	/**
	 * 依頼主 住所を返します。
	 *
	 * @return 依頼主 住所
	 */
	public String getIrainusiAddressView() {
		return (String) get("IRAINUSI_ADDRESS_VIEW");
	}

	/**
	 * 依頼主 住所をレコードに格納します。
	 *
	 * @param irainusiZipView 依頼主 住所
	 */
	public void setIrainusiAddressView(String irainusiAddressView) {
		put("IRAINUSI_ADDRESS_VIEW", irainusiAddressView);
	}


	/**
	 * 依頼主 TELを返します。
	 *
	 * @return 依頼主 TEL
	 */
	public String getIrainusiTelView() {
		return (String) get("IRAINUSI_TEL_VIEW");
	}

	/**
	 * 依頼主 TELをレコードに格納します。
	 *
	 * @param irainusiTelView 依頼主 TEL
	 */
	public void setIrainusiTelView(String irainusiTelView) {
		put("IRAINUSI_TEL_VIEW", irainusiTelView);
	}


	/**
	 * 届け先 届け先名を返します。
	 *
	 * @return 届け先 届け先名
	 */
	public String getTodokesakiNmView() {
		return (String) get("TODOKESAKI_NM_VIEW");
	}

	/**
	 * 届け先 届け先名をレコードに格納します。
	 *
	 * @param todokesakiNmView 届け先 届け先名
	 */
	public void setTodokesakiNmView(String todokesakiNmView) {
		put("TODOKESAKI_NM_VIEW", todokesakiNmView);
	}


	/**
	 * 届け先 郵便番号を返します。
	 *
	 * @return 届け先 郵便番号
	 */
	public String getTodokesakiZipView() {
		return (String) get("TODOKESAKI_ZIP_VIEW");
	}

	/**
	 * 届け先 郵便番号をレコードに格納します。
	 *
	 * @param todokesakiZipView 届け先 郵便番号
	 */
	public void setTodokesakiZipView(String todokesakiZipView) {
		put("TODOKESAKI_ZIP_VIEW", todokesakiZipView);
	}


	/**
	 * 届け先 住所を返します。
	 *
	 * @return 届け先 住所
	 */
	public String getTodokesakiAddressView() {
		return (String) get("TODOKESAKI_ADDRESS_VIEW");
	}

	/**
	 * 届け先 住所をレコードに格納します。
	 *
	 * @param todokesakiZipView 届け先 住所
	 */
	public void setTodokesakiAddressView(String todokesakiAddressView) {
		put("TODOKESAKI_ADDRESS_VIEW", todokesakiAddressView);
	}


	/**
	 * 届け先 TELを返します。
	 *
	 * @return 届け先 TEL
	 */
	public String getTodokesakiTelView() {
		return (String) get("TODOKESAKI_TEL_VIEW");
	}

	/**
	 * 届け先 TELをレコードに格納します。
	 *
	 * @param todokesakiTelView 届け先 TEL
	 */
	public void setTodokesakiTelView(String todokesakiTelView) {
		put("TODOKESAKI_TEL_VIEW", todokesakiTelView);
	}


	/**
	 * 用途区分を返します。
	 *
	 * @return 用途区分
	 */
	public String getYoutoKbnView() {
		return (String) get("YOUTO_KBN_VIEW");
	}

	/**
	 * 用途区分をレコードに格納します。
	 *
	 * @param youtoKbnView 用途区分
	 */
	public void setYoutoKbnView(String youtoKbnView) {
		put("YOUTO_KBN_VIEW", youtoKbnView);
	}


	/**
	 * 【のし】名前記載有無区分を返します。
	 *
	 * @return 【のし】名前記載有無区分
	 */
	public String getNosiKbnView() {
		return (String) get("NOSI_KBN_VIEW");
	}

	/**
	 * 【のし】名前記載有無区分をレコードに格納します。
	 *
	 * @param nosiKbnView 【のし】名前記載有無区分
	 */
	public void setNosiKbnView(String nosiKbnView) {
		put("NOSI_KBN_VIEW", nosiKbnView);
	}


	/**
	 * のし内容 1行目を返します。
	 *
	 * @return のし内容 1行目
	 */
	public String getNosiComment1View() {
		return (String) get("NOSI_COMMENT1_VIEW");
	}

	/**
	 * のし内容 1行目をレコードに格納します。
	 *
	 * @param nosiComment1View のし内容 1行目
	 */
	public void setNosiComment1View(String nosiComment1View) {
		put("NOSI_COMMENT1_VIEW", nosiComment1View);
	}


	/**
	 * のし内容 2行目を返します。
	 *
	 * @return のし内容 2行目
	 */
	public String getNosiComment2View() {
		return (String) get("NOSI_COMMENT2_VIEW");
	}

	/**
	 * のし内容 2行目をレコードに格納します。
	 *
	 * @param nosiComment2View のし内容 2行目
	 */
	public void setNosiComment2View(String nosiComment2View) {
		put("NOSI_COMMENT2_VIEW", nosiComment2View);
	}


	/**
	 * のし内容 3行目を返します。
	 *
	 * @return のし内容 3行目
	 */
	public String getNosiComment3View() {
		return (String) get("NOSI_COMMENT3_VIEW");
	}

	/**
	 * のし内容 3行目をレコードに格納します。
	 *
	 * @param nosiComment3 のし内容 3行目
	 */
	public void setNosiComment3View(String nosiComment3View) {
		put("NOSI_COMMENT3_VIEW", nosiComment3View);
	}


	/**
	 * 酒販店ﾁｪｯｸﾘｽﾄ打出区分を返します。
	 *
	 * @return 酒販店ﾁｪｯｸﾘｽﾄ打出区分
	 */
	public String getCheckListPrtKbnView() {
		return (String) get("CHECK_LIST_PRT_KBN_VIEW");
	}

	/**
	 * 酒販店ﾁｪｯｸﾘｽﾄ打出区分をレコードに格納します。
	 *
	 * @param checkListPrtKbnView 酒販店ﾁｪｯｸﾘｽﾄ打出区分
	 */
	public void setCheckListPrtKbnView(String checkListPrtKbnView) {
		put("CHECK_LIST_PRT_KBN_VIEW", checkListPrtKbnView);
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
	 * 縦線CD のエラー、警告表示フラグを返します。
	 *
	 * @return 縦線CD のエラー、警告表示フラグ
	 */
	public String getTatesnCdClass() {
		return (String) get("TATESN_CD_CLASS");
	}

	/**
	 * 縦線CD のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tatesnCdClass 縦線CD のエラー、警告表示フラグ
	 */
	public void setTatesnCdClass(String tatesnCdClass) {
		put("TATESN_CD_CLASS", tatesnCdClass);
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
	 *  事業所区分のエラー、警告表示フラグを返します。
	 *
	 * @return 事業所区分フラグ
	 */
	public String getJigyosyoKbnClass() {
		return (String) get("JGYOSYO_KBN_CLASS");
	}

	/**
	 * 事業所区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param jgyosyoKbnClass 事業所区分フラグ
	 */
	public void setJigyosyoKbnClass(String jgyosyoKbnClass) {
		put("JGYOSYO_KBN_CLASS", jgyosyoKbnClass);
	}


	/**
	 * のし名前記載有無区分のエラー、警告表示フラグを返します。
	 *
	 * @return のし名前記載有無区分のエラー、警告表示フラグを返します。
	 *
	 */
	public String getNosikbnClass() {
		return (String) get("NOSI_KBN_CLASS");
	}

	/**
	 * のし名前記載のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param nosikbnClass nosikbnClassのエラー、警告表示フラグ
	 */
	public void setNosikbnClass(String nosikbnClass) {
		put("NOSI_KBN_CLASS", nosikbnClass);
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
	 * 発注No表示フラグを返します。
	 *
	 * @return 小ロット出荷承認申請NOのエラー、警告表示フラグ
	 */
	public String getHacyuNoClass() {
		return (String) get("HACYU_NO_CLASS");
	}

	/**
	 * 発注Noのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param hacyuNoClass 発注Noのエラー、警告表示フラグ
	 */
	public void setHacyuNoClass(String hacyuNoClass) {
		put("HACYU_NO_CLASS", hacyuNoClass);
	}

	/**
	 * 依頼主 住所のエラー、警告を返します。
	 *
	 * @return 依頼主 住所のエラー、警告
	 */
	public String getIrainusiAddressClass() {
		return (String) get("IRAINUSI_ADDRESS_CLASS");
	}

	/**
	 * 依頼主 住所のエラー、警告をレコードに格納します。
	 *
	 * @param irainusiAddressClass 依頼主 住所のエラー、警告
	 */
	public void setIrainusiAddressClass(String irainusiAddressClass) {
		put("IRAINUSI_ADDRESS_CLASS", irainusiAddressClass);
	}


	/**
	 * 依頼主 TELのエラー、警告表示フラグを返します。
	 *
	 * @return 依頼主 TELのエラー、警告表示フラグ
	 */
	public String getIrainusiTelClass() {
		return (String) get("IRAINUSI_TEL_CLASS");
	}

	/**
	 * 依頼主 TELのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param irainusiTelClass 依頼主 TELのエラー、警告表示フラグ
	 */
	public void setIrainusiTelClass(String irainusiTelClass) {
		put("IRAINUSI_TEL_CLASS", irainusiTelClass);
	}

	/**
	 * 依頼主 郵便番号のエラー、警告表示フラグを返します。
	 *
	 * @return 依頼主 郵便番号のエラー、警告表示フラグ
	 */
	public String getIrainusiZipClass() {
		return (String) get("IRAINUSI_ZIP_CLASS");
	}

	/**
	 * 依頼主 郵便番号のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param minasiDtClass 依頼主 郵便番号のエラー、警告表示フラグ
	 */
	public void setIrainusiZipClass(String irainusiZipClass) {
		put("IRAINUSI_ZIP_CLASS", irainusiZipClass);
	}

	/**
	 * 申込受付日のエラー、警告表示フラグを返します。
	 *
	 * @return 申込受付日のエラー、警告表示フラグ
	 */
	public String getUketukeDtClass() {
		return (String) get("UKETUKE_DT_CLASS");
	}

	/**
	 * 申込受付日のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param uketukeDtClass 申込受付日
	 */
	public void setUketukeDtClass(String uketukeDtClass) {
		put("UKETUKE_DT_CLASS", uketukeDtClass);
	}

	/**
	 * 用途区分のエラー、警告表示フラグを返します。
	 *
	 * @return 用途区分のエラー、警告表示フラグ
	 */
	public String getYoutoKbnClass() {
		return (String) get("YOUTO_KBN_CLASS");
	}

	/**
	 * 用途区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param youtoKbn 用途区分
	 */
	public void setYoutoKbnClass(String youtoKbnClass) {
		put("YOUTO_KBN_CLASS", youtoKbnClass);
	}

	/**
	 * のし内容 1行目のエラー、警告表示フラグを返します。
	 *
	 * @return のし内容 1行目のエラー、警告表示フラグ
	 */
	public String getNosiComment1Class() {
		return (String) get("NOSI_COMMENT1_CLASS");
	}

	/**
	 * のし内容 1行目のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param nosiComment1class のし内容 1行目のエラー、警告表示フラグ
	 */
	public void setNosiComment1Class(String nosiComment1class) {
		put("NOSI_COMMENT1_CLASS", nosiComment1class);
	}


	/**
	 * のし内容 2行目のエラー、警告表示フラグを返します。
	 *
	 * @return のし内容 2行目のエラー、警告表示フラグ
	 */
	public String getNosiComment2Class() {
		return (String) get("NOSI_COMMENT2_CLASS");
	}

	/**
	 * のし内容 2行目のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param nosiComment2class のし内容 2行目のエラー、警告表示フラグ
	 */
	public void setNosiComment2Class(String nosiComment2class) {
		put("NOSI_COMMENT2_CLASS", nosiComment2class);
	}

	/**
	 * のし内容 3行目のエラー、警告表示フラグを返します。
	 *
	 * @return のし内容 3行目のエラー、警告表示フラグ
	 */
	public String getNosiComment3Class() {
		return (String) get("NOSI_COMMENT3_CLASS");
	}

	/**
	 * のし内容 3行目のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param nosiComment3class のし内容 2行目のエラー、警告表示フラグ
	 */
	public void setNosiComment3Class(String nosiComment3class) {
		put("NOSI_COMMENT3_CLASS", nosiComment3class);
	}

	/**
	 * 整理Noのエラー、警告表示フラグを返します。
	 *
	 * @return 整理Noのエラー、警告表示フラグ
	 */
	public String getKuradenNoClass() {
		return (String) get("KURADEN_NO_CLASS");
	}

	/**
	 * 整理Noのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param tekiyoKbn5Class 整理Noのエラー、警告表示フラグ
	 */
	public void setKuradenNoClass(String kuradenNoClass) {
		put("KURADEN_NO_CLASS", kuradenNoClass);
	}


	/**
	 * 届先ﾗｲﾝNOのエラー、警告表示フラグを返します。
	 *
	 * @return 届先ﾗｲﾝNO
	 */
	public String getTodokesakiLineNoClass() {
		return (String) get("TODOKESAKI_LINE_NO_CLASS");
	}

	/**
	 * 届先ﾗｲﾝNOのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param todokesakiLineNo 届先ﾗｲﾝNO
	 */
	public void setTodokesakiLineNoClass(String todokesakiLineNo) {
		put("TODOKESAKI_LINE_NO_CLASS", todokesakiLineNo);
	}

	/**
	 * 酒販店ﾁｪｯｸﾘｽﾄ打出区分のエラー、警告表示フラグを返します。
	 *
	 * @return 酒販店ﾁｪｯｸﾘｽﾄ打出区分のエラー、警告表示フラグ
	 */
	public String getCheckListPrtKbnClass() {
		return (String) get("CHECK_LIST_PRT_KBN_CLASS");
	}

	/**
	 * 酒販店ﾁｪｯｸﾘｽﾄ打出区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param checkListPrtKbnClass 酒販店ﾁｪｯｸﾘｽﾄ打出区分のエラー、警告表示フラグ
	 */
	public void setCheckListPrtKbnClass(String checkListPrtKbnClass) {
		put("CHECK_LIST_PRT_KBN_CLASS", checkListPrtKbnClass);
	}


	/**
	 * 発送予定日のエラー、警告表示フラグを返します。
	 *
	 * @return 発送予定日のエラー、警告表示フラグ
	 */
	public String getHasoYoteiDtClass() {
		return (String) get("HASO_YOTEI_DT_CLASS");
	}

	/**
	 * 発送予定日のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param hasoYoteiDtClass 発送予定日のエラー、警告表示フラグ
	 */
	public void setHasoYoteiDtClass(String hasoYoteiDtClass) {
		put("HASO_YOTEI_DT_CLASS", hasoYoteiDtClass);
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
	 * 依頼主名のエラー、警告表示フラグを返します。
	 *
	 * @return 依頼主名のエラー、警告表示フラグ
	 */
	public String getIrainusiNmClass() {
		return (String) get("IRAINUSI_NM_CLASS");
	}

	/**
	 * 依頼主名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param irainusiNmClass 依頼主名のエラー、警告表示フラグ
	 */
	public void setIrainusiNmClass(String irainusiNmClass) {
		put("IRAINUSI_NM_CLASS", irainusiNmClass);
	}


	/**
	 * 宅配伝票区分のエラー、警告表示フラグを返します。
	 *
	 * @return 宅配伝票区分のエラー、警告表示フラグ
	 */
	public String getTakuhaiBillKbnClass() {
		return (String) get("TAKUHAI_BILL_KBN_CLASS");
	}

	/**
	 * 宅配伝票区分のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param takuhaiBillClass 宅配伝票区分のエラー、警告表示フラグ
	 */
	public void setTakuhaiBillKbnClass(String takuhaiBillClass) {
		put("TAKUHAI_BILL_KBN_CLASS", takuhaiBillClass);
	}

	/**
	 * 届け先 住所のエラー、警告表示フラグを返します。
	 *
	 * @return  届け先 住所のエラー、警告表示フラグ
	 */
	public String getTodokesakiAddressClass() {
		return (String) get("TODOKESAKI_ADDRESS_CLASS");
	}

	/**
	 * 届け先 住所のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param todokesakiAddress 届け先 住所のエラー、警告表示フラグ
	 */
	public void setTodokesakiAddressClass(String todokesakiAddress) {
		put("TODOKESAKI_ADDRESS_CLASS", todokesakiAddress);
	}

	/**
	 *届け先 TELのエラー、警告表示フラグを返します。
	 *
	 * @return 届け先 TELのエラー、警告表示フラグ
	 */
	public String getTodokesakiTelClass() {
		return (String) get("TODOKESAKI_TEL_CLASS");
	}

	/**
	 * 届け先 TELのエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param todokesakiTelClass 届け先 TELのエラー、警告表示フラグ
	 */
	public void setTodokesakiTelClass(String todokesakiTelClass) {
		put("TODOKESAKI_TEL_CLASS", todokesakiTelClass);
	}


	/**
	 * 届け先 郵便番号のエラー、警告表示フラグを返します。
	 *
	 * @return 届け先 郵便番号のエラー、警告表示フラグ
	 */
	public String getTodokesakiZipClass() {
		return (String) get("TODOKESAKI_ZIP_CLASS");
	}

	/**
	 * 届け先 郵便番号のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param todokesakiZipClass 届け先 郵便番号のエラー、警告表示フラグ
	 */
	public void setTodokesakiZipClass(String todokesakiZipClass) {
		put("TODOKESAKI_ZIP_CLASS", todokesakiZipClass);
	}

	/**
	 * 届け先名のエラー、警告表示フラグを返します。
	 *
	 * @return 届け先名のエラー、警告表示フラグ
	 */
	public String getTodokesakiNmClass() {
		return (String) get("TODOKESAKI_NM_CLASS");
	}

	/**
	 * 届け先 届け先名のエラー、警告表示フラグをレコードに格納します。
	 *
	 * @param todokesakiNmClass 届け先名のエラー、警告表示フラグ
	 */
	public void setTodokesakiNmClass(String todokesakiNmClass) {
		put("TODOKESAKI_NM_CLASS", todokesakiNmClass);
	}

}	// -- class
