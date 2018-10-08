package kit.jiseki.ESienList;

import static fb.com.IKitComConst.AVAILABLE_KB_RIYO_KA;
import static fb.com.IKitComConstHM.IS_DELETED_HELD_TRUE;
import static fb.com.IKitComConstHM.IS_MESSAGE_OUTPUT_FALSE;
import static fb.com.IKitComConstHM.IS_MESSAGE_OUTPUT_TRUE;
import static fb.com.IKitComConstHM.IS_REQUIRED_TRUE;
import static fb.com.IKitComConstHM.MST_REC_EXISTENCE_DELETED;
import static fb.com.IKitComConstHM.MST_REC_EXISTENCE_MULTI;
import static fb.com.IKitComConstHM.MST_REC_NOT_EXISTENCE;
import static fb.com.IKitComConstHM.STYLE_CLASS_ERROR;
import static fb.com.IKitComErrorConst.ERRORS_CALL_ADMINISTRATOR;
import static fb.com.IKitComErrorConst.ERRORS_DATE_ERROR;
import static fb.com.IKitComErrorConst.ERRORS_DELETED;
import static fb.com.IKitComErrorConst.ERRORS_MULTI_RECS;
import static fb.com.IKitComErrorConst.ERRORS_NOT_EXISTENCE;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_COUNTRY;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_JIGYOSYO;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_KA;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_SOUNISAKI;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_SYUHANTEN;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_TANTO;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_TOKUYAKU;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_ZENKOKU;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SAKADANE;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SAKESITU;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_SYOHIN;
import static kit.jiseki.ESienList.IJisekiESienList.TAIHI_UMU_KB_YOSAN_TAIHI_ON;
import static kit.jiseki.ESienList.IJisekiESienList.TAISYO_HYOJI_KIKAN_KB_DATE;
import static kit.jiseki.ESienList.IJisekiESienList.TAISYO_HYOJI_KIKAN_KB_MONTH;
import static kit.jiseki.ESienList.IJisekiESienList.TAISYO_HYOJI_KIKAN_KB_TOUGETU;
import static kit.jiseki.ESienList.IJisekiESienList.TAISYO_HYOJI_KIKAN_KB_YEAR;
import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.TOROKU_MASTER_SAKETANE;
import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.TOROKU_MASTER_SYOHIN;
import static kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroup.TOROKU_MASTER_SYUSITU;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import fb.com.ComRecordUtil;
import fb.com.ComUserSession;
import fb.com.ComUtil;
import fb.com.ComValidateUtil;
import fb.com.Records.FbMastrKbnHanbaiBunruiRecord;
import fb.com.Records.FbMastrKbnTaneRecord;
import fb.com.Records.FbMastrOrositenRecord;
import fb.com.Records.FbMastrSeihinRecord;
import fb.com.Records.FbMastrSiirehinRecord;
import fb.com.Records.FbMastrSyohinRecord;
import fb.com.Records.FbMastrSyuhantenRecord;
import fb.inf.KitFbRecord;
import fb.inf.KitService;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;
import kit.jiseki.ESienList.torokuGroup.IJisekiESienListTorokuGroupMaster;
import kit.jiseki.ESienList.torokuGroup.JisekiESienListTorokuGroupList;
import kit.jiseki.ESienList.torokuGroup.JisekiESienListTorokuGroupMaster1;
import kit.jiseki.ESienList.torokuGroup.JisekiESienListTorokuGroupMaster2;
import kit.jiseki.ESienList.torokuGroup.JisekiESienListTorokuGroupMaster3;
import kit.jiseki.ESienList.torokuGroup.JisekiESienListTorokuGroupRecord;

/**
 * 営業支援実績一覧のチェックサービス
 * @author t_kimura
 *
 */
public class JisekiESienList_CheckService extends KitService {

	/** serialVersionUID */
	private static final long serialVersionUID = 6470279398644242524L;

	/** クラス名. */
	private static String className__ = JisekiESienList_CheckService.class.getName();

	/** カテゴリ. */
	private static Category category__ = Category.getInstance(className__);

	/** 存在チェックユーティリティー */
	protected ComRecordUtil recordUtil;

	/** チェックユーティリティー */
	protected ComValidateUtil validateUtil;

	/**
	 * コンストラクタ.
	 *
	 * @param cus getComUserSession()を渡すこと。
	 * @param db_ 呼び出すときにはgetDatabase()を渡すこと。
	 * @param isTran isTransaction()を渡すこと。
	 * @param ae getActionErrors()を渡すこと。
	 */
	public JisekiESienList_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
		this.validateUtil = new ComValidateUtil(ae);
		this.recordUtil = new ComRecordUtil(db_);

	}

	/**
	 * 検索時の入力チェック処理
	 * @param searchForm 検索フォーム
	 * @return チェック結果
	 * @throws Exception
	 */
	public boolean validateSearch(JisekiESienList_SearchForm searchForm) throws Exception {
		// エラークリア
		searchForm.clearError();
		// ユーザセッション
		ComUserSession comUserSession = getComUserSession();

		// 名称設定
		setNmsForAjax(comUserSession, searchForm);

		// 抽出条件関連チェック
		validateForCyusyutuJyoken(comUserSession, searchForm);
		// 対象表示期間関連チェック
		validateForTaisyoHyojiKikan(searchForm);
		// 登録グループ関連チェック
		validateForTorokuGroup(comUserSession, searchForm);

		return !searchForm.hasError();
	}

	/**
	 * 名称設定処理
	 * @param comUserSession ユーザセッション
	 * @param searchForm 検索フォーム
	 */
	private void setNmsForAjax(ComUserSession comUserSession, JisekiESienList_SearchForm searchForm) {
		// postされなかった未設定の名称のみ設定する
		// 特約コード
		if(judgeGetNm(searchForm.getTokuyakuCd(), searchForm.getTokuyakuNm())) {
			FbMastrOrositenRecord record = getMastrOrositenRecord(comUserSession.getKaisyaCd(), searchForm.getTokuyakuCd());
			searchForm.setTokuyakuNm(getMstExistValue(record, record.getTenNm2Jisya()));
		}
		// 送荷先コード
		if(judgeGetNm(searchForm.getSounisakiCd(), searchForm.getSounisakiNm())) {
			FbMastrOrositenRecord record = getMastrOrositenRecord(comUserSession.getKaisyaCd(), searchForm.getSounisakiCd());
			searchForm.setSounisakiNm(getMstExistValue(record, record.getTenNm2Jisya()));
		}
		// 酒販店コード
		if(judgeGetNm(searchForm.getSyuhantenCd(), searchForm.getSyuhantenNm())) {
			FbMastrSyuhantenRecord record = getMastrSyuhantenRecord(searchForm.getSyuhantenCd());
			searchForm.setSyuhantenNm(getMstExistValue(record, record.getTenNmYago()));
		}
		// 酒種
		(new JisekiESienListTorokuGroupMaster1()).setTorokuNms(this, comUserSession, searchForm.getSakadaneList());
		// 酒質
		(new JisekiESienListTorokuGroupMaster2()).setTorokuNms(this, comUserSession, searchForm.getSakesituList());
		// 商品
		(new JisekiESienListTorokuGroupMaster3()).setTorokuNms(this, comUserSession, searchForm.getSyohinList());
	}

	/**
	 * 抽出条件関連チェック
	 * @param comUserSession ユーザセッション
	 * @param searchForm 検索フォーム
	 */
	private void validateForCyusyutuJyoken(ComUserSession comUserSession, JisekiESienList_SearchForm searchForm) {
		// 抽出条件
		String cyusyutuJyoken = searchForm.getCyusyutuJyoken();
		if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_JIGYOSYO)) {
			// 事業所コード必須
			if(!validateUtil.validateRequired(searchForm.getJigyosyoCd(), "com.text.zigyousyoCd")){
				searchForm.setJigyosyoCdError(true);
			}
		} else if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_KA)) {
			// 課コード必須
			if(!validateUtil.validateRequired(searchForm.getKaCd(), "com.text.kaCd")){
				searchForm.setKaCdError(true);
			}
		} else if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_TANTO)) {
			// 担当コード必須
			if(!validateUtil.validateRequired(searchForm.getTantosyaCd(), "com.text.tantoCd")){
				searchForm.setTantosyaCdError(true);
			}
		} else if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_COUNTRY)) {
			// 都道府県コード必須
			if(!validateUtil.validateRequired(searchForm.getCountryCd(), "com.text.todofuknCd")){
				searchForm.setCountryCdError(true);
			}
		} else if(PbsUtil.isEqual(cyusyutuJyoken,CYUSYUTU_JYOKEN_KB_ZENKOKU)) {
			// 全国卸コード必須
			if(!validateUtil.validateRequired(searchForm.getZenkokuOrosiCd(), "com.text.zenkokuOrosiCd")){
				searchForm.setZenkokuOrosiCdError(true);
			}
		} else  if(PbsUtil.isEqual(cyusyutuJyoken, CYUSYUTU_JYOKEN_KB_TOKUYAKU)){
			// 特約レベルの場合、特約コード必須
			if(!validateUtil.validateRequired(searchForm.getTokuyakuCd(), "com.text.tokuyakuCd")){
				searchForm.setTokuyakuCdError(true);
			} else {
				// マスタ存在チェック
				if(!isExistMastrOrositen(comUserSession.getKaisyaCd(), searchForm.getTokuyakuCd(), "com.text.tokuyakuCd")) {
					searchForm.setTokuyakuCdError(true);
				}
			}
		} else if(PbsUtil.isEqual(cyusyutuJyoken, CYUSYUTU_JYOKEN_KB_SOUNISAKI)){
			// 送荷先レベルの場合、送荷先コード必須
			if(!validateUtil.validateRequired(searchForm.getSounisakiCd(), "com.text.sounisakiCd")){
				searchForm.setSounisakiCdError(true);
			} else {
				// マスタ存在チェック
				if(!isExistMastrOrositen(comUserSession.getKaisyaCd(), searchForm.getSounisakiCd(), "com.text.sounisakiCd")) {
					searchForm.setSounisakiCdError(true);
				}
			}
		} else if(PbsUtil.isEqual(cyusyutuJyoken, CYUSYUTU_JYOKEN_KB_SYUHANTEN)){
			// 酒販店レベルの場合、酒販店コード必須
			if(!validateUtil.validateRequired(searchForm.getSyuhantenCd(), "com.text.syuhantenCd")){
				searchForm.setSyuhantenCdError(true);
			} else {
				// マスタ存在チェック
				if(!isExistMastrSyuhanten(searchForm.getSyuhantenCd())) {
					searchForm.setSyuhantenCdError(true);
				}
			}
		}
	}

	/**
	 * 対象表示期間関連チェック
	 * @param searchForm 検索フォーム
	 * @throws Exception
	 */
	private void validateForTaisyoHyojiKikan(JisekiESienList_SearchForm searchForm) throws Exception{
		if(PbsUtil.isEqual(searchForm.getTaisyoHyojiKikan(), TAISYO_HYOJI_KIKAN_KB_TOUGETU)) {
			// 当月・10～当月・1～当月の場合、対象日必須
			if(!validateUtil.validateDateSimple(searchForm.getTaisyoDate(), IS_REQUIRED_TRUE, "com.text.siteibi")){
				searchForm.setTaisyoDateError(true);
			}
		} else if(PbsUtil.isEqual(searchForm.getTaisyoHyojiKikan(), TAISYO_HYOJI_KIKAN_KB_YEAR)) {

			// 指定年の場合、対象年From,To必須
			if(!validateUtil.validateRequired(searchForm.getTaisyoYearFrom(), "com.text.siteiNenStart")){
				searchForm.setTaisyoYearFromError(true);
			} else {
				// 範囲チェック
				if(!validateUtil.validateRangeNumber(searchForm.getTaisyoYearFrom(), "1900", "9999", "com.text.siteiNenStart")){
					searchForm.setTaisyoYearFromError(true);
				}
			}
			if(!validateUtil.validateRequired(searchForm.getTaisyoYearTo(), "com.text.siteiNenEnd")){
				searchForm.setTaisyoYearToError(true);
			} else {
				// 範囲チェック
				if(!validateUtil.validateRangeNumber(searchForm.getTaisyoYearTo(), "1900", "9999", "com.text.siteiNenEnd")){
					searchForm.setTaisyoYearToError(true);
				}
			}
			if(!searchForm.isTaisyoYearFromError() && !searchForm.isTaisyoYearToError()) {
				// From <= Toチェック
				if (PbsUtil.toint(searchForm.getTaisyoYearFrom()) > PbsUtil.toint(searchForm.getTaisyoYearTo())) {
					// エラーメッセージ
					setErrorMessageId("errors.input.after",
						PbsUtil.getMessageResourceString("com.text.siteiNenEnd"),
						PbsUtil.getMessageResourceString("com.text.siteiNenStart"));
					searchForm.setTaisyoYearFromError(true);
					searchForm.setTaisyoYearToError(true);
				}
			}
			if(!searchForm.isTaisyoYearFromError() && !searchForm.isTaisyoYearToError()) {
				// 5年以内チェック
				if (PbsUtil.toint(searchForm.getTaisyoYearTo()) - PbsUtil.toint(searchForm.getTaisyoYearFrom()) > 5) {
					// エラーメッセージ
					setErrorMessageId("errors.kikanYearMax5");
					searchForm.setTaisyoYearFromError(true);
					searchForm.setTaisyoYearToError(true);
				}
			}

		} else if(PbsUtil.isEqual(searchForm.getTaisyoHyojiKikan(), TAISYO_HYOJI_KIKAN_KB_MONTH)) {
			// 指定月の場合、対象月From,To必須
			if(!validateUtil.validateRequired(searchForm.getTaisyoMonthFrom(), "com.text.siteiTukiStart")){
				searchForm.setTaisyoMonthFromError(true);
			} else {
				if(!PbsUtil.isYYYYMM(searchForm.getTaisyoMonthFrom())) {
					// エラーメッセージ
					setErrorMessageId(ERRORS_DATE_ERROR,
						PbsUtil.getMessageResourceString("com.text.siteiTukiStart"));
					searchForm.setTaisyoMonthFromError(true);
				}
			}
			if(!validateUtil.validateRequired(searchForm.getTaisyoMonthTo(), "com.text.siteiTukiEnd")){
				searchForm.setTaisyoMonthToError(true);
			} else {
				if(!PbsUtil.isYYYYMM(searchForm.getTaisyoMonthTo())) {
					// エラーメッセージ
					setErrorMessageId(ERRORS_DATE_ERROR,
						PbsUtil.getMessageResourceString("com.text.siteiTukiEnd"));
					searchForm.setTaisyoMonthToError(true);
				}
			}

			if(!searchForm.isTaisyoMonthFromError() && !searchForm.isTaisyoMonthToError()) {
				// From <= Toチェック
				if (!PbsUtil.isFutureYm(searchForm.getTaisyoMonthFrom(), searchForm.getTaisyoMonthTo(), true)) {
					// エラーメッセージ
					setErrorMessageId("errors.input.after",
						PbsUtil.getMessageResourceString("com.text.siteiTukiEnd"),
						PbsUtil.getMessageResourceString("com.text.siteiTukiStart"));
					searchForm.setTaisyoMonthFromError(true);
					searchForm.setTaisyoMonthToError(true);
				}
			}
			if(!searchForm.isTaisyoMonthFromError() && !searchForm.isTaisyoMonthToError()) {
				// 12ヶ月以内チェック
				int diff = ComUtil.differenceMonth(PbsUtil.String2DateString(searchForm.getTaisyoMonthTo() + "01"), PbsUtil.String2DateString(searchForm.getTaisyoMonthFrom() + "01")) + 1;
				if (ComUtil.decimalCompareTo(String.valueOf(diff),"12") > 0) {
					// エラーメッセージ
					setErrorMessageId("errors.kikanMax12");
					searchForm.setTaisyoMonthFromError(true);
					searchForm.setTaisyoMonthToError(true);
				}
			}
		} else if(PbsUtil.isEqual(searchForm.getTaisyoHyojiKikan(), TAISYO_HYOJI_KIKAN_KB_DATE)) {
			// 指定日の場合、対象日From,To必須

			// 予算対比の場合はチェックしない
			if(!PbsUtil.isEqual(searchForm.getYosanTaihi(), TAIHI_UMU_KB_YOSAN_TAIHI_ON)) {
				if(!validateUtil.validateDateSimple(searchForm.getTaisyoDateFrom(), IS_REQUIRED_TRUE, "com.text.siteiDtStart")){
					searchForm.setTaisyoDateFromError(true);
				}
				if(!validateUtil.validateDateSimple(searchForm.getTaisyoDateTo(), IS_REQUIRED_TRUE, "com.text.siteiDtEnd")){
					searchForm.setTaisyoDateToError(true);
				}

				if(!searchForm.isTaisyoDateFromError() && !searchForm.isTaisyoDateToError()) {
					// From <= Toチェック
					if (!PbsUtil.isFutureDate(searchForm.getTaisyoDateFrom(), searchForm.getTaisyoDateTo(), true)) {
						// エラーメッセージ
						setErrorMessageId("errors.input.after",
							PbsUtil.getMessageResourceString("com.text.siteiDtEnd"),
							PbsUtil.getMessageResourceString("com.text.siteiDtStart"));
						searchForm.setTaisyoDateFromError(true);
						searchForm.setTaisyoDateToError(true);
					}
				}
				if(!searchForm.isTaisyoDateFromError() && !searchForm.isTaisyoDateToError()) {
					// 月跨ぎチェック
					if(PbsUtil.toint(PbsUtil.getYYYY(searchForm.getTaisyoDateFrom())) != PbsUtil.toint(PbsUtil.getYYYY(searchForm.getTaisyoDateTo())) ||
							PbsUtil.toint(PbsUtil.getMM(searchForm.getTaisyoDateFrom())) != PbsUtil.toint(PbsUtil.getMM(searchForm.getTaisyoDateTo()))) {
						// エラーメッセージ
						setErrorMessageId("errors.siteibikikanNenTuki");
						searchForm.setTaisyoDateFromError(true);
						searchForm.setTaisyoDateToError(true);
					}

//					// 31日以内チェック
//					int diff = ComUtil.defferenceDays(searchForm.getTaisyoDateFrom(), searchForm.getTaisyoDateTo()) + 1;
//					if (ComUtil.decimalCompareTo(String.valueOf(diff),"31") > 0) {
//						// エラーメッセージ
//						setErrorMessageId("errors.kikanDayMax31");
//						searchForm.setTaisyoDateFromError(true);
//						searchForm.setTaisyoDateToError(true);
//					}
				}
			}
		}
	}

	/**
	 * 登録グループ関連チェック
	 * @param comUserSession ユーザセッション
	 * @param searchForm 検索フォーム
	 */
	private void validateForTorokuGroup(ComUserSession comUserSession, JisekiESienList_SearchForm searchForm) {
		// 集計内容が酒種、酒質、商品の場合チェックする
		JisekiESienListTorokuGroupList torokuGroupList = searchForm.getTorokuGroupList();
		if(torokuGroupList != null) {
			// 登録グループマスタクラス取得
			IJisekiESienListTorokuGroupMaster torokuGroupMaster = getTorokuGroupMasterBySyukeiNaiyo(searchForm.getSyukeiNaiyo());
			// 登録コードチェック
			checkTorokuCdList(comUserSession, torokuGroupMaster, torokuGroupList, true);
		}
	}

	/**
	 * 登録コードチェック処理
	 * @param comUserSession ログインユーザセッション情報
	 * @param torokuGroupMaster 登録グループマスタ
	 * @param torokuGroupList登録グループリスト
	 * @param parent 親画面か子画面かのフラグ
	 * @return チェック結果
	 */
	public boolean checkTorokuCdList(ComUserSession comUserSession, IJisekiESienListTorokuGroupMaster torokuGroupMaster, JisekiESienListTorokuGroupList torokuGroupList, boolean parent) {
		boolean ret = true;

		int index=0;
		// 登録コード件数分繰り返す
		for (PbsRecord pbsRecord : torokuGroupList) {
			index++;
			JisekiESienListTorokuGroupRecord record = (JisekiESienListTorokuGroupRecord)pbsRecord;
			String torokuCd = record.getTorokuCd();
			// 未指定時はスキップ
			if(PbsUtil.isEmpty(torokuCd)) continue;
			// 各種マスタのチェック
			if(!torokuGroupMaster.checkTorokuCd(this, comUserSession, torokuCd, index, parent)) {
				// エラーの場合、エラーを設定
				record.setHasError(true);
				if(!parent) {
					record.setTorokuCdClass(STYLE_CLASS_ERROR);
				}
				ret = false;
			} else {
				// 編集済みクラス定義設定
				record.setModifiedClass();
			}
		}
		return ret;
	}

	/**
	 * 卸店マスタ存在チェック
	 * @param kaisyaCd 会社コード
	 * @param orositenCd 卸店コード
	 * @param msgKey メッセージキー
	 * @return チェック結果
	 */
	public boolean isExistMastrOrositen(String kaisyaCd, String orositenCd, String msgKey) {
		FbMastrOrositenRecord record = getMastrOrositenRecord(kaisyaCd, orositenCd);
		return validateUtil.validateMstExistence(record, IS_DELETED_HELD_TRUE, msgKey, IS_MESSAGE_OUTPUT_TRUE);

	}
	/**
	 * 卸店マスタ取得
	 * @param kaisyaCd 会社コード
	 * @param orositenCd 卸店コード
	 * @return 卸店マスタ
	 */
	public FbMastrOrositenRecord getMastrOrositenRecord(String kaisyaCd, String orositenCd) {
		return recordUtil.getRecMastrOrositen(IS_DELETED_HELD_TRUE, kaisyaCd, orositenCd);
	}

	/**
	 * 酒販店マスタ存在チェック
	 * @param syuhantenCd 酒販店コード
	 * @return チェック結果
	 */
	public boolean isExistMastrSyuhanten(String syuhantenCd) {
		FbMastrSyuhantenRecord record = getMastrSyuhantenRecord(syuhantenCd);
		return validateUtil.validateMstExistence(record, IS_DELETED_HELD_TRUE, "com.text.syuhantenCd", IS_MESSAGE_OUTPUT_TRUE);

	}
	/**
	 * 酒販店マスタ取得
	 * @param syuhantenCd 酒販店コード
	 * @return 酒販店マスタ
	 */
	public FbMastrSyuhantenRecord getMastrSyuhantenRecord(String syuhantenCd) {
		return recordUtil.getRecMastrSyuhanten(IS_DELETED_HELD_TRUE, syuhantenCd);
	}


	/**
	 * 種区分マスタ取得
	 * @param kaisyaCd 会社コード
	 * @param taneCd  種コード
	 * @return 種区分マスタ
	 */
	public FbMastrKbnTaneRecord getMastrKbnTaneRecord(String kaisyaCd, String taneCd) {
		return recordUtil.getRecMastrKbnTane(IS_DELETED_HELD_TRUE, kaisyaCd, taneCd);
	}

	/**
	 * 種区分マスタ存在チェック
	 * @param kaisyaCd 会社コード
	 * @param taneCd 種コード
	 * @param index インデックス
	 * @param parent 親画面か子画面かのフラグ
	 * @return チェック結果
	 */
	public boolean isExistMastrKbnTane(String kaisyaCd, String taneCd, int index, boolean parent) {
		FbMastrKbnTaneRecord record = getMastrKbnTaneRecord(kaisyaCd, taneCd);
		if(!parent) return validateUtil.validateMstExistence(record, IS_DELETED_HELD_TRUE, index, "com.text.sakadaneCd", IS_MESSAGE_OUTPUT_TRUE);
		boolean ret = validateUtil.validateMstExistence(record, IS_DELETED_HELD_TRUE, index, null, IS_MESSAGE_OUTPUT_FALSE);
		if(ret) return true;
		setErrorMessageId(record, index, "com.text.sakadaneCd");
		return false;
	}

	/**
	 * 販売分類マスタ取得
	 * @param hanbaiBunruiCd 販売分類コード
	 * @return 販売分類マスタ
	 */
	public FbMastrKbnHanbaiBunruiRecord getMastrKbnHanbaiBunruiRecord(String hanbaiBunruiCd) {
		return recordUtil.getRecMastrKbnHanbaiBunruiRec(IS_DELETED_HELD_TRUE, hanbaiBunruiCd);
	}

	/**
	 * 販売分類マスタ存在チェック
	 * @param hanbaiBunruiCd 販売分類コード
	 * @param index インデックス
	 * @param parent 親画面か子画面かのフラグ
	 * @return チェック結果
	 */
	public boolean isExistMastrKbnHanbaiBunrui(String hanbaiBunruiCd, int index, boolean parent) {
		FbMastrKbnHanbaiBunruiRecord record = getMastrKbnHanbaiBunruiRecord(hanbaiBunruiCd);
		if(!parent) return validateUtil.validateMstExistence(record, IS_DELETED_HELD_TRUE, index, "com.text.sakesituCd", IS_MESSAGE_OUTPUT_TRUE);
		boolean ret = validateUtil.validateMstExistence(record, IS_DELETED_HELD_TRUE, index, null, IS_MESSAGE_OUTPUT_FALSE);
		if(ret) return true;
		setErrorMessageId(record, index, "com.text.sakesituCd");
		return false;
	}

	/**
	 * 商品マスタ取得
	 * @param kaisyaCd 会社コード
	 * @param ktksyCd 寄託者コード
	 * @param shohinCd 商品コード
	 * @return 商品マスタ
	 */
	public FbMastrSyohinRecord getMastrSyohinRecord(String kaisyaCd, String ktksyCd, String shohinCd) {
		return recordUtil.getRecMastrSyohin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, shohinCd);
	}

	/**
	 * 商品マスタ存在チェック
	 * @param kaisyaCd 会社コード
	 * @param ktksyCd 寄託者コード
	 * @param shohinCd 商品コード
	 * @param index インデックス
	 * @param parent 親画面か子画面かのフラグ
	 * @return チェック結果
	 */
	public boolean isExistMastrSyohin(String kaisyaCd, String ktksyCd, String shohinCd, int index, boolean parent) {
		FbMastrSyohinRecord record =  getMastrSyohinRecord(kaisyaCd, ktksyCd, shohinCd);
		if(!parent) return validateUtil.validateMstExistence(record, IS_DELETED_HELD_TRUE, index, "com.text.syohinCd", IS_MESSAGE_OUTPUT_TRUE);
		boolean ret = validateUtil.validateMstExistence(record, IS_DELETED_HELD_TRUE, index, null, IS_MESSAGE_OUTPUT_FALSE);
		if(ret) return true;
		setErrorMessageId(record, index, "com.text.syohinCd");
		return false;
	}

	/**
	 * 製品マスタ取得
	 * @param kaisyaCd 会社コード
	 * @param ktksyCd 寄託者コード
	 * @param seihinCd 製品コード
	 * @return 製品マスタ
	 */
	public FbMastrSeihinRecord getMastrSeihinnRecord(String kaisyaCd, String ktksyCd, String seihinCd) {
		return recordUtil.getRecMastrSeihin(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, seihinCd);
	}

	/**
	 * 仕入品マスタ取得
	 * @param kaisyaCd 会社コード
	 * @param ktksyCd 寄託者コード
	 * @param siiresakiCd 仕入先コード
	 * @param siirehinCd 仕入品コード
	 * @return 仕入品マスタ
	 */
	public FbMastrSiirehinRecord getMastrSiirehinRecord(String kaisyaCd, String ktksyCd, String siiresakiCd, String siirehinCd) {
		return recordUtil.getRecMastrSiirehinRecord(IS_DELETED_HELD_TRUE, kaisyaCd, ktksyCd, siiresakiCd, siirehinCd);
	}

	/**
	 * エラーメッセージ設定
	 * @param record Record
	 * @param index インデックス
	 * @param msgKey メッセージキー
	 */
	private void setErrorMessageId(KitFbRecord record, int index, String msgKey) {
		String resultKey = record.getResultFlg();

		if(MST_REC_EXISTENCE_MULTI.equals(resultKey)){
			// 複数明細のとき
			// 存在チェックで複数明細取得エラーが発生しました。情報システム室に連絡して下さい。
			setErrorMessageId(new ActionError(ERRORS_CALL_ADMINISTRATOR
					, PbsUtil.getMessageResourceString(ERRORS_MULTI_RECS)));
		} else if (MST_REC_NOT_EXISTENCE.equals(resultKey)) {
			// 無し
			// {0}番目:○○は、△△にありません。
			setErrorMessageId(new ActionError("errors.record.index.item"
					,index , PbsUtil.getMessageResourceString(ERRORS_NOT_EXISTENCE
							,PbsUtil.getMessageResourceString(msgKey))));


		} else if (MST_REC_EXISTENCE_DELETED.equals(resultKey)) {
			// 削除済
			// {0}番目:○○は、△△で削除済です
			setErrorMessageId(new ActionError("errors.record.index.item"
					,index , PbsUtil.getMessageResourceString(ERRORS_DELETED
							,PbsUtil.getMessageResourceString(msgKey))));

		}
	}

	/**
	 * 登録グループマスタクラスを取得する
	 * @param torokuMaster 登録マスタ
	 * @return 登録グループマスタクラス
	 */
	public IJisekiESienListTorokuGroupMaster getTorokuGroupMaster(String torokuMaster) {
		if(PbsUtil.isEqual(TOROKU_MASTER_SAKETANE, torokuMaster)) {
			return new JisekiESienListTorokuGroupMaster1();
		} else if(PbsUtil.isEqual(TOROKU_MASTER_SYUSITU, torokuMaster)) {
			return new JisekiESienListTorokuGroupMaster2();
		} else if(PbsUtil.isEqual(TOROKU_MASTER_SYOHIN, torokuMaster)) {
			return new JisekiESienListTorokuGroupMaster3();
		}
		throw new IllegalArgumentException();
	}

	/**
	 * 登録グループマスタクラスを取得する
	 * @param syukeiNaiyo 集計内容
	 * @return 登録グループマスタクラス
	 */
	public IJisekiESienListTorokuGroupMaster getTorokuGroupMasterBySyukeiNaiyo(String syukeiNaiyo) {
		return getTorokuGroupMaster(getTorokuMasterBySyukeiNaiyo(syukeiNaiyo));
	}

	/**
	 * 登録グループマスタ取得
	 * @param syukeiNaiyo 集計内容
	 * @return 登録グループマスタ
	 */
	public String getTorokuMasterBySyukeiNaiyo(String syukeiNaiyo) {
		if(PbsUtil.isEqual(syukeiNaiyo, SYUKEI_NAIYO_KB_SAKADANE)) {
			return TOROKU_MASTER_SAKETANE;
		} else if(PbsUtil.isEqual(syukeiNaiyo, SYUKEI_NAIYO_KB_SAKESITU)) {
			return TOROKU_MASTER_SYUSITU;
		} else if(PbsUtil.isEqual(syukeiNaiyo, SYUKEI_NAIYO_KB_SYOHIN)) {
			return TOROKU_MASTER_SYOHIN;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * マスタが存在した場合は引数の値を、存在しない場合はnoValueを返す
	 * @param record レコード
	 * @param existValue 存在する場合の値
	 * @return 値
	 */
	public String getMstExistValue(KitFbRecord record, String existValue) {
		// マスタ存在チェック
		if (ComUtil.isMstExistence(record, MST_REC_NOT_EXISTENCE)) {
			return PbsUtil.getMessageResourceString("com.text.noValue");
		} else {
			return existValue == null ? "" : existValue;
		}
	}

	/**
	 * マスタが利用可の場合は引数の値を、存在しない場合はnoValue、資料不可の場合はdontUseを返す
	 * @param record レコード
	 * @param riyokaValue 存在する場合の値
	 * @return 値
	 */
	public String getMstRiyoKaValue(KitFbRecord record, String riyokaValue) {
		if (ComUtil.isMstExistence(record, MST_REC_NOT_EXISTENCE)) {
			return PbsUtil.getMessageResourceString("com.text.noValue");
		} else {
			if (AVAILABLE_KB_RIYO_KA.equals(record.getRiyouKbn())) {
				return riyokaValue;
			} else {
				return PbsUtil.getMessageResourceString("com.text.dontUse");
			}
		}
	}

	/**
	 * 名称取得判定
	 * @param cd コード
	 * @param nm 名称
	 * @return 判定結果
	 */
	public boolean judgeGetNm(String cd, String nm) {
		return !PbsUtil.isEmpty(cd) && PbsUtil.isNumeric(cd) && PbsUtil.isEmpty(nm);
	}
}
