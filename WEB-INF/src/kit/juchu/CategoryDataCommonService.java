package kit.juchu;

import static fb.com.IKitComConstHM.*;

import java.util.ArrayList;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComRecordUtil;
import fb.com.ComSeihinUtil;
import fb.com.ComUserSession;
import fb.com.ComValidateUtil;
import fb.com.Records.FbMastrKbnHanbaiBumonRecord;
import fb.com.Records.FbMastrKbnHanbaiBunruiRecord;
import fb.com.Records.FbMastrKbnHanbaiSyubetuRecord;
import fb.com.Records.FbMastrKbnSaketaxBunruiRecord;
import fb.com.Records.FbMastrKbnTaneRecord;
import fb.com.Records.FbMastrSeihinRecord;
import fb.inf.KitService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * データベースへ問い合わせを実行するビジネスロジッククラスです
 */
public class CategoryDataCommonService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = 2168015670639759396L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = CategoryDataCommonService.class.getName();
	private static Category category__ = Category.getInstance(className__);

	// ============================================
	// 必要な変数はここに作成する。
	// ============================================
	/** データベースオブジェクト */
	private PbsDatabase db_ = null;

	/** 存在チェックユーティリティー */
	private ComRecordUtil rUtil = null;

	/** チェックユーティリティー */
	private ComValidateUtil vUtil = null;

	/** 製品関係ユーティリティー */
	private ComSeihinUtil sUtil = null;

	/**
	 * コンストラクタ.
	 *
	 * @param cus getComUserSession()を渡すこと。
	 * @param db_ 呼び出すときにはgetDatabase()を渡すこと。
	 * @param isTran isTransaction()を渡すこと。
	 * @param ae getActionErrors()を渡すこと。
	 */
	public CategoryDataCommonService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
		_reset();

		// 存在チェックユーティリティーの初期化
		this.rUtil = new ComRecordUtil(db_);

		// 評価ユーティリティーを初期化する
		this.vUtil = new ComValidateUtil(ae);

		// 製品関係ユーティリティーの初期化
		this.sUtil = new ComSeihinUtil(db_);

		// データベースオブジェクトの初期化
		this.db_ = db_;
	}

	/**
	 * プロパティをリセットします.
	 */
	protected void _reset() {
		db_ = null;
	}

	/**
	 * トラン系カテゴリデータ共通部データを検索する
	 *
	 * @param riyouKbn 利用区分
	 * @param kaisyaCd 会社CD
	 * @param ktksyCd 寄託者CD
	 * @param seihinCd 製品CD
	 * @return PbsRecord[] 検索結果
	 * @throws KitException
	 * @throws DataNotFoundException
	 */
	public PbsRecord[] getListCommonCategoryData(String riyouKbn, String kaisyaCd, String ktksyCd, String seihinCd) throws DataNotFoundException, KitException {
		// カテゴリデータ共通部リスト
		ArrayList<CategoryDataCommonRecord> catList = new ArrayList<CategoryDataCommonRecord>();

		// 検索結果を格納するレコード
		PbsRecord[] records_ = null;

		// 製品関係ユーティリティーにパラメーターを設定
		sUtil = new ComSeihinUtil(db_); // DBオブジェクト
		sUtil.setKaisyaCd(kaisyaCd); // 会社CD
		sUtil.setKtksyCd(ktksyCd); // 寄託者CD

		// --------------------------
		// 製品構成マスター（リスト）
		// --------------------------
		PbsRecord[] zRecsSeihinGrp = null;
		try {
			sUtil.setSeihinCd_(seihinCd); // 製品CD
			sUtil.searchSeihinGrpRecs();
			zRecsSeihinGrp = sUtil.getPbsRecords();
		} catch (DataNotFoundException e) {
			// 製品構成データがない場合はエラー
			setErrorMessageId("errors.data.ng",
					PbsUtil.getMessageResourceString("com.text.seihinKoseiMst")
					+ PbsUtil.getMessageResourceString("com.text.leftKakko")
					+ PbsUtil.getMessageResourceString("com.text.seihinCd")
					+ PbsUtil.getMessageResourceString("com.text.colon")
					+ seihinCd
					+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
			throw e;
		} catch (KitException e) {
			// その他エラー
			setErrorMessageId("errors.data.ng",
					PbsUtil.getMessageResourceString("com.text.seihinKoseiMst")
					+ PbsUtil.getMessageResourceString("com.text.leftKakko")
					+ PbsUtil.getMessageResourceString("com.text.seihinCd")
					+ PbsUtil.getMessageResourceString("com.text.colon")
					+ seihinCd
					+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
			throw e;
		}

		for (PbsRecord tmp : zRecsSeihinGrp) {
			// カテゴリデータ共通部レコード
			CategoryDataCommonRecord catRec = new CategoryDataCommonRecord();

			// 利用区分
			catRec.setRiyouKbn(riyouKbn);

			// 会社CD
			catRec.setKaisyaCd(kaisyaCd);

			// 寄託者CD
			catRec.setKtksyCd(ktksyCd);

			// ----------------
			// 製品構成マスター
			// ----------------
			if (tmp != null) {
				// 製品CD
				catRec.setSeihinCd(tmp.getString("SEIHIN_CD"));

				// 容量（L)
				catRec.setVol(tmp.getString("KOSEI_VOL"));

				// 単価（円）
				catRec.setTanka(tmp.getString("KOSEI_TANKA"));

				// 利用定数
				catRec.setRiyouTeisu(tmp.getString("KOSEI_SEIHIN_RIYOU_TEISU"));

				// ------------
				// 製品マスター
				// ------------
				FbMastrSeihinRecord zRecSeihin = rUtil.getRecMastrSeihin(IS_DELETED_HELD_FALSE, kaisyaCd, ktksyCd, tmp.getString("SEIHIN_CD"));
				if (!vUtil.validateMstExistence(zRecSeihin, "", IS_MESSAGE_OUTPUT_FALSE)) {
					setErrorMessageId("errors.data.ng",
							PbsUtil.getMessageResourceString("com.text.seihinMst")
							+ PbsUtil.getMessageResourceString("com.text.leftKakko")
							+ PbsUtil.getMessageResourceString("com.text.seihinCd")
							+ PbsUtil.getMessageResourceString("com.text.colon")
							+ tmp.getString("SEIHIN_CD")
							+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
					throw new KitException(PbsUtil.getMessageResourceString("errors.create.categoryData"));
				} else {

					// --------------
					// 種区分マスター
					// --------------
					FbMastrKbnTaneRecord zRecTane = rUtil.getRecMastrKbnTane(IS_DELETED_HELD_FALSE, kaisyaCd, zRecSeihin.getTaneCd());
					if (!vUtil.validateMstExistence(zRecTane, "", IS_MESSAGE_OUTPUT_FALSE)) {
						setErrorMessageId("errors.data.ng",
								PbsUtil.getMessageResourceString("com.text.taneKbMst")
								+ PbsUtil.getMessageResourceString("com.text.leftKakko")
								+ PbsUtil.getMessageResourceString("com.text.taneCd")
								+ PbsUtil.getMessageResourceString("com.text.colon")
								+ zRecSeihin.getTaneCd()
								+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
						throw new KitException(PbsUtil.getMessageResourceString("errors.create.categoryData"));
					} else {

						// 種CD
						catRec.setTaneCd(zRecTane.getTaneCd());
						// 種名称（略式）
						catRec.setTaneNmRyaku(zRecTane.getTaneNmRyaku());

						// --------------------
						// 販売分類区分マスター
						// --------------------
						FbMastrKbnHanbaiBunruiRecord zRecHanbaiBunrui = rUtil.getRecMastrKbnHanbaiBunruiRec(IS_DELETED_HELD_FALSE, zRecTane.getHanbaiBunruiCd());
						if (!vUtil.validateMstExistence(zRecHanbaiBunrui, "", IS_MESSAGE_OUTPUT_FALSE)) {
							setErrorMessageId("errors.data.ng",
									PbsUtil.getMessageResourceString("com.text.hanbaiBunruiKbMst")
									+ PbsUtil.getMessageResourceString("com.text.leftKakko")
									+ PbsUtil.getMessageResourceString("com.text.hanbaiBunruiCd")
									+ PbsUtil.getMessageResourceString("com.text.colon")
									+ zRecTane.getHanbaiBunruiCd()
									+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
							throw new KitException(PbsUtil.getMessageResourceString("errors.create.categoryData"));
						} else {

							// 販売分類CD
							catRec.setHanbaiBunruiCd(zRecHanbaiBunrui.getHanbaiBunruiCd());
							// 販売分類名（略式）
							catRec.setHanbaiBunruiRnm(zRecHanbaiBunrui.getHanbaiBunruiRnm());

							// --------------------
							// 販売種別区分マスター
							// --------------------
							FbMastrKbnHanbaiSyubetuRecord zRecHanbaiSyubetu = rUtil.getMastrKbnHanbaiSyubetuRec(IS_DELETED_HELD_FALSE, zRecHanbaiBunrui.getHanbaiSyubetuCd());
							if (!vUtil.validateMstExistence(zRecHanbaiSyubetu, "", IS_MESSAGE_OUTPUT_FALSE)) {
								setErrorMessageId("errors.data.ng",
										PbsUtil.getMessageResourceString("com.text.hanbaiSyubetuKbMst")
										+ PbsUtil.getMessageResourceString("com.text.leftKakko")
										+ PbsUtil.getMessageResourceString("com.text.hanbaiSyubetuCd2")
										+ PbsUtil.getMessageResourceString("com.text.colon")
										+ zRecHanbaiBunrui.getHanbaiSyubetuCd()
										+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
								throw new KitException(PbsUtil.getMessageResourceString("errors.create.categoryData"));
							} else {

								// 販売種別CD
								catRec.setHanbaiSyubetuCd(zRecHanbaiSyubetu.getHanbaiSyubetuCd());

								// 販売種別名（略式）
								catRec.setHanbaiSyubetuRnm(zRecHanbaiSyubetu.getHanbaiSyubetuRnm());

								// --------------------
								// 販売部門区分マスター
								// --------------------
								FbMastrKbnHanbaiBumonRecord zRecHanbaiBumon = rUtil.getRecMastrKbnHanbaiBumon(IS_DELETED_HELD_FALSE, zRecHanbaiSyubetu.getHanbaiBumonCd());
								if (!vUtil.validateMstExistence(zRecHanbaiBumon, "", IS_MESSAGE_OUTPUT_FALSE)) {
									setErrorMessageId("errors.data.ng",
											PbsUtil.getMessageResourceString("com.text.hanbaiBumonKbMst")
											+ PbsUtil.getMessageResourceString("com.text.leftKakko")
											+ PbsUtil.getMessageResourceString("com.text.hanbaiBumonCd2")
											+ PbsUtil.getMessageResourceString("com.text.colon")
											+ zRecHanbaiSyubetu.getHanbaiBumonCd()
											+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
									throw new KitException(PbsUtil.getMessageResourceString("errors.create.categoryData"));
								} else {

									// 販売部門CD
									catRec.setHanbaiBumonCd(zRecHanbaiBumon.getHanbaiBumonCd());

									// 販売部門名（略式）
									catRec.setHanbaiBumonRnm(zRecHanbaiBumon.getHanbaiBumonRnm());
								}
							}
						}
					}

					// --------------------
					// 酒税分類区分マスター
					// --------------------
					FbMastrKbnSaketaxBunruiRecord zRecSaketaxBunrui = rUtil.getRecMastrKbnSaketaxBunrui(IS_DELETED_HELD_FALSE, zRecSeihin.getSyuzeiCd());
					if (!vUtil.validateMstExistence(zRecSaketaxBunrui, "", IS_MESSAGE_OUTPUT_FALSE)) {
						setErrorMessageId("errors.data.ng",
								PbsUtil.getMessageResourceString("com.text.syuzeiKbMst")
								+ PbsUtil.getMessageResourceString("com.text.leftKakko")
								+ PbsUtil.getMessageResourceString("com.text.syuzeiCd")
								+ PbsUtil.getMessageResourceString("com.text.colon")
								+ zRecSeihin.getSyuzeiCd()
								+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
						throw new KitException(PbsUtil.getMessageResourceString("errors.create.categoryData"));
					} else {

						// 酒税分類CD
						catRec.setSyuzeiCd(zRecSaketaxBunrui.getSyuzeiCd());
						// 酒税分類名（略式）
						catRec.setSyuzeiNmRyaku(zRecSaketaxBunrui.getSyuzeiNmRyaku());
					}

					// ----------------
					// 製品容器マスター
					// ----------------
					PbsRecord zRecSeihinyouk = null;
					try {
						sUtil.setSeihinCd_(zRecSeihin.getSeihinCd()); // 製品CD
						sUtil.searchYoukiRec();
						zRecSeihinyouk = sUtil.getPbsRecord();
					} catch (DataNotFoundException e) {
						// 製品容器データがない場合はエラー
						setErrorMessageId("errors.data.ng",
								PbsUtil.getMessageResourceString("com.text.seihinYoukiMst")
								+ PbsUtil.getMessageResourceString("com.text.leftKakko")
								+ PbsUtil.getMessageResourceString("com.text.seihinCd")
								+ PbsUtil.getMessageResourceString("com.text.colon")
								+ zRecSeihin.getSeihinCd()
								+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
						throw e;
					} catch (KitException e) {
						// その他エラー
						setErrorMessageId("errors.data.ng",
								PbsUtil.getMessageResourceString("com.text.seihinYoukiMst")
								+ PbsUtil.getMessageResourceString("com.text.leftKakko")
								+ PbsUtil.getMessageResourceString("com.text.seihinCd")
								+ PbsUtil.getMessageResourceString("com.text.colon")
								+ zRecSeihin.getSeihinCd()
								+ PbsUtil.getMessageResourceString("com.text.rightKakko"));
						throw e;
					}

					if (zRecSeihinyouk != null) {
						// 素材区分
						catRec.setSozaiKbn(zRecSeihinyouk.getString("SOZAI_KBN"));
						// 色区分
						catRec.setColorKbn(zRecSeihinyouk.getString("COLOR_KBN"));
						// 販売実績分類区分
						catRec.setHjisekiBunruiKbn(zRecSeihinyouk.getString("HJISEKI_BUNRUI_KBN"));
					}
				}

				// リストに追加
				catList.add(catRec);
			}
		}

		CategoryDataCommonRecord[] catRecs = new CategoryDataCommonRecord[catList.size()];
		records_ = (CategoryDataCommonRecord[]) catList.toArray(catRecs);

		return records_;
	}
}
