package kit.juchu.JuchuDataIn;

import static fb.com.IKitComConstHM.*;
import static fb.com.IKitComConst.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.juchu.JuchuDataIn.IJuchuJuchuDataIn.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.mastr.OrositenSyosai.MastrOrositenSyosaiDtList;
import kit.mastr.TatesenOrositen.MastrTatesenOrositenList;
import kit.mastr.TatesenOrositen.MastrTatesenOrositenRecord;
import kit.mastr.TatesenOrositen.MastrTatesenOrositen_SearchService;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComHaitaService;
import fb.com.ComKitUtil;
import fb.com.ComUserSession;
import fb.com.exception.NoDataException;
import fb.inf.KitFunction;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 検索部からのリクエストを処理する実装クラスです
 */
public class JuchuJuchuDataIn_SearchFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = 5922423826650551893L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JuchuJuchuDataIn_SearchFunction.class.getName();

	/** 明細部 */
	private JuchuJuchuDataIn_EditForm editForm;

	/**
	 * トランザクション管理を行うかどうかを示すフラグです。
	 * trueの場合トランザクション管理を行います。falseの場合トランザクション管理を行いません。
	 *
	 * @see #isTransactionalFunction ()
	 */
	private final boolean isTransactionalFunction = false;

	@Override
	protected boolean isTransactionalFunction() {

		return isTransactionalFunction;
	}

	@Override
	protected String getFunctionName() {

		return className__;
	}

	/**
	 * コンストラクタです。
	 *
	 * @param mapping paramName
	 * @param form paramName
	 * @param request paramName
	 * @param response paramName
	 */
	public JuchuJuchuDataIn_SearchFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		super(mapping, form, request, response);
	}

	/**
	 * メインメソッドです。 リクエストタイプの判別,検索条件のチェック,検索および検索結果のビーンへのセットを行います。
	 *
	 * @return 処理結果を表す文字列(FORWARD_SUCCESS,FORWARD_FAIL)
	 * @throws NoDataException
	 */
	@Override
	protected String execute_() throws NoDataException, KitException {

		JuchuJuchuDataIn_SearchForm searchForm = (JuchuJuchuDataIn_SearchForm) form_;

		// ----------------------------------------------------
		// 新規 on init
		// ----------------------------------------------------
		if (REQ_TYPE_ADD.equals(searchForm.getReqType())) {
			add(searchForm);

		// ----------------------------------------------------
		// 帳合選択 on init
		// ----------------------------------------------------
		} else if (REQ_TYPE_CHOICE.equals(searchForm.getReqType())) {
			choice(searchForm);

		// ----------------------------------------------------
		// 呼出 on init
		// ----------------------------------------------------
		} else if (REQ_TYPE_SEARCH.equals(searchForm.getReqType())) {
			search(searchForm);

		// ----------------------------------------------------
		// ソート on init
		// ----------------------------------------------------
		} else if (REQ_TYPE_SORT.equals(searchForm.getReqType())) {
			sort(searchForm);

		// ----------------------------------------------------
		// 修正、参照追加 on selected
		// ----------------------------------------------------
		} else if (PbsUtil.isIncluded(searchForm.getReqType(),
				REQ_TYPE_EDIT, REQ_TYPE_REFERENCE)) {
			manipulateEdit(searchForm);

		// ----------------------------------------------------
		// 抹消、復活 on selected
		// ----------------------------------------------------
		} else if (PbsUtil.isIncluded(searchForm.getReqType(),
				REQ_TYPE_DELETE, REQ_TYPE_REBIRTH)) {
			manipulateView(searchForm);

			// 確認画面を表示せずに確定処理を行う
			JuchuJuchuDataIn_EditForm editForm_ = (JuchuJuchuDataIn_EditForm) getSession(KEY_SS_EDITFORM);
			editForm_.setReqType(REQ_TYPE_COMMIT); // リクエストタイプ
			editForm_.setMode(searchForm.getReqType()); // モード
			editForm_.setPreMode(MODE_EMPTY); // プレモード
			JuchuJuchuDataIn_EditFunction callFunc = new JuchuJuchuDataIn_EditFunction(mapping_, editForm_, request_, response_);
			return callFunc.execute_();

		}

		// 呪文
		setRequest(KEY_SS_EDITFORM, editForm);

		// 宛先を返す
		return ComKitUtil.getForwardForMaster(editForm);
	}

	/**
	 * 「呼出」処理を行う
	 *
	 * @param searchForm JuchuJuchuDataIn_SearchForm
	 * @throws NoDataException
	 */
	private void search(JuchuJuchuDataIn_SearchForm searchForm) throws NoDataException {

		ComUserSession cus = getComUserSession();

		// --------------------------
		// EditListビーンを初期化(削除)する。
		// --------------------------
		removeSession(KEY_SS_INITLIST);
		removeSession(KEY_SS_EDITLIST);
		removeSession(KEY_SS_INITLIST_DT);
		removeSession(KEY_SS_EDITLIST_DT);
		removeSession(KEY_SS_SEARCHEDLIST);

		// チェックサービスを初期化
		JuchuJuchuDataIn_CheckService checkServ = new JuchuJuchuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 呼出時チェックを実行
		if (!checkServ.validateSearch(searchForm)) {
			// リクエストにEditFormをセット
			// エラー検知時(mode:null, preMode:null, 編集フォーム可)
			editForm = new JuchuJuchuDataIn_EditForm();
			return;
		}

		try {
			// 検索実行
			JuchuJuchuDataIn_SearchService searchServ = new JuchuJuchuDataIn_SearchService(
					getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
			PbsRecord[] records = searchServ.execute(searchForm);

			// 検索結果をリストクラスに変換
			JuchuJuchuDataInList searchedList = new JuchuJuchuDataInList(records);

			// 結果出力ボタンを活性化
			if (!searchedList.isEmpty()) {
				searchForm.setCsvBtnAvailFlg(BUTTON_AVAILABLE_TRUE);
				searchForm.setExcelBtnAvailFlg(BUTTON_AVAILABLE_TRUE);
			}

			// ==================================
			// ヘッダー部の付加情報を設定
			// ==================================
			searchedList.setHdInfo(getDatabase(), checkServ, cus.getKaisyaCd(), true);

			// ソート指定を初期化
			searchForm.sortInitialize("SYUKA_DT", SORT_DESC);

			// 呪文 ・・・ １ページ目指定＆ページあたり行数指定
			searchedList.setOffset(0);
			searchedList.setRecordsParPage(searchForm.getMaxSu());

			// リクエストにEditFormをセット(mode:edit or delete or result, preMode:null,
			// 編集フォーム設定可)
			editForm = new JuchuJuchuDataIn_EditForm();
			editForm.setEditable(IS_EDITABLE_TRUE);

			/* **********************************************
			 * 要注意ポイント
			 * MODE_RESULTは、使わない。新たにMODE_SEARCHを新設
			 * また、BOOLEANに替り、所定のMODE、PREMODEをセット
			 * することでFORWARD先を共通関数で指定する。
			 * MODEの対応表は、別途参照
			 */

			// 呼出モード時
			editForm.setMode(MODE_SEARCH);
			editForm.setPreMode(MODE_EMPTY);

			// ViewJSPで検索部のボタンを非活性にするために検索部の内容をリクエストに設定する
			setSession(KEY_SS_SEARCHFORM, searchForm);

			// 呪文・・・検索結果をセッションへ、明細部をリクエストへ設定する
			setSession(KEY_SS_SEARCHEDLIST, searchedList);

		} catch (DataNotFoundException e) {
			// XXX：検索結果を取得できなかったときは…
			// リクエストにEditFormをセット(mode:null, preMode:null)
			editForm = new JuchuJuchuDataIn_EditForm();
			throw new NoDataException();
		}

	}

	/**
	 * 「ソート」処理を行う
	 *
	 * @param searchForm JuchuJuchuDataIn_SearchForm
	 */
	private void sort(JuchuJuchuDataIn_SearchForm searchForm) {

		// 検索結果は呼出時に取得したものを使う
		JuchuJuchuDataInList searchedList = (JuchuJuchuDataInList) getSession(KEY_SS_SEARCHEDLIST);

		// 呪文 ・・・ １ページ目指定＆ページあたり行数指定
		searchedList.setOffset(0);
		searchedList.setRecordsParPage(searchForm.getMaxSu());

		// リクエストにEditFormをセット(mode:edit or delete or result, preMode:null,
		// 編集フォーム設定可)
		editForm = new JuchuJuchuDataIn_EditForm();
		editForm.setEditable(IS_EDITABLE_TRUE);

		/* **********************************************
		 * 要注意ポイント
		 * MODE_RESULTは、使わない。新たにMODE_SEARCHを新設
		 * また、BOOLEANに替り、所定のMODE、PREMODEをセット
		 * することでFORWARD先を共通関数で指定する。
		 * MODEの対応表は、別途参照
		 */

		// 呼出モード時
		editForm.setMode(MODE_SEARCH);
		editForm.setPreMode(MODE_EMPTY);

		// ViewJSPで検索部のボタンを非活性にするために検索部の内容をリクエストに設定する
		setSession(KEY_SS_SEARCHFORM, searchForm);

		// 指定項目でソート
		JuchuJuchuDataInList sortedList = searchedList;
		// 数値としてソート（ケース計/バラ計/重量計）
		if (PbsUtil.isIncluded(searchForm.getSortItem(), "SYUKA_SURYO_BOX", "SYUKA_SURYO_SET", "JYURYO_TOT")) {
			if (SORT_ASC.equals(searchForm.getSortOrder())) { // 昇順
				sortedList = (JuchuJuchuDataInList) ComKitUtil.sortPbsRecordListDecimalAsc(searchedList, searchForm.getSortItem());
			} else if (SORT_DESC.equals(searchForm.getSortOrder())) { // 降順
				sortedList = (JuchuJuchuDataInList) ComKitUtil.sortPbsRecordListDecimalDesc(searchedList, searchForm.getSortItem());
			}
		// 文字列としてソート
		} else {
			if (SORT_ASC.equals(searchForm.getSortOrder())) { // 昇順
				sortedList = (JuchuJuchuDataInList) ComKitUtil.sortPbsRecordListAsc(searchedList, searchForm.getSortItem());
			} else if (SORT_DESC.equals(searchForm.getSortOrder())) { // 降順
				sortedList = (JuchuJuchuDataInList) ComKitUtil.sortPbsRecordListDesc(searchedList, searchForm.getSortItem());
			}
		}

		// 検索結果をセッションへ、明細部をリクエストへ設定する
		setSession(KEY_SS_SEARCHEDLIST, sortedList);
	}

	/**
	 * 新規開始前に帳合の指定を行う.
	 *
	 * @param searchForm JuchuJuchuDataIn_SearchForm
	 */
	private void choice(JuchuJuchuDataIn_SearchForm searchForm) {

		// --------------------------
		// EditListビーンを初期化(削除)する。
		// --------------------------
		removeSession(KEY_SS_INITLIST);
		removeSession(KEY_SS_EDITLIST);
		removeSession(KEY_SS_INITLIST_DT);
		removeSession(KEY_SS_EDITLIST_DT);
		removeSession(KEY_SS_SEARCHEDLIST);

		// 明細フォームを初期化する
		editForm = new JuchuJuchuDataIn_EditForm();

		// =================================================================
		// 明細ヘッダ項目をチェックする
		JuchuJuchuDataIn_CheckService checkServ = new JuchuJuchuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 追加時チェック
		if (!checkServ.validateSearchForAdd(searchForm)) {
			// リクエストにEditFormをセット
			// エラー検知時(mode:null, preMode:null, 編集フォーム可)
			editForm = new JuchuJuchuDataIn_EditForm();
			return;
		}

		// 縦線卸店検索サービス
		MastrTatesenOrositen_SearchService mstSearchServ = new MastrTatesenOrositen_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// 縦線卸店リストを取得（利用状態は仮登録・利用可能＝納品可能期間）
		MastrTatesenOrositenList tatesenList = mstSearchServ.getListMastrTatesenOrositen(searchForm.getOrositenCdLast(),
				DATA_STATUS_KB_KARI_TOROKU, DATA_STATUS_KB_RIYO_KA);
		// 帳合先がない場合はエラー
		if (tatesenList.size() < 1) {
			// エラーメッセージを出力
			setErrorMessageId("errors.noTatesen");
			editForm = new JuchuJuchuDataIn_EditForm();
			return;
		}

		// デフォルト値として一件目の縦線CDをセット
		searchForm.setOrsTatesnCd(((MastrTatesenOrositenRecord) tatesenList.getFirstRecord()).getOrsTatesnCd());
		// 縦線リストを格納
		setSession(KEY_SS_ORS_TATESEN, tatesenList);

		// 呪文：リクエストにEditFormをセット(mode:add, preMode:null, 編集フォーム設定可)
		editForm.setMode(MODE_ADD);
		editForm.setPreMode(MODE_EMPTY);
		editForm.setEditable(IS_EDITABLE_TRUE);

		// 要注意ポイント
		// １明細編集なので<kit:iterate>で必要となる
		// ※ jspのhiddenにも記載すること！
		editForm.setMaxLineSu(INT_EDIT_ONE_RECORD.toString());

		// 帳合先が一つの場合は新規処理へ移行
		if (tatesenList.size() == 1) {
			this.add(searchForm);
		// 帳合先が複数の場合は選択画面へ遷移
		} else {
			editForm.setMode(MODE_CHOICE);
		}
	}

	/**
	 * 「新規」処理の準備作業を行う.
	 *
	 * @param searchForm JuchuJuchuDataIn_SearchForm
	 */
	private void add(JuchuJuchuDataIn_SearchForm searchForm) {

		// --------------------------
		// EditListビーンを初期化(削除)する。
		// --------------------------
		removeSession(KEY_SS_INITLIST);
		removeSession(KEY_SS_EDITLIST);
		removeSession(KEY_SS_INITLIST_DT);
		removeSession(KEY_SS_EDITLIST_DT);
		removeSession(KEY_SS_SEARCHEDLIST);
		removeSession(KEY_SS_CAUTIONLIST);

		// 明細フォームを初期化する
		editForm = new JuchuJuchuDataIn_EditForm();

		// =================================================================
		// 明細ヘッダ項目をチェックする
		JuchuJuchuDataIn_CheckService checkServ = new JuchuJuchuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 追加時チェック
		if (!checkServ.validateSearchForAdd(searchForm)) {
			// リクエストにEditFormをセット
			// エラー検知時(mode:null, preMode:null, 編集フォーム可)
			editForm = new JuchuJuchuDataIn_EditForm();
			return;
		}

		// =================================================================
		// ・空のリストを作成
		// =================================================================
		// ヘッダー部
		JuchuJuchuDataInList editList = new JuchuJuchuDataInList(INT_EDIT_ONE_RECORD);
		editList.setRecordsParPage(searchForm.getMaxSu());
		editList.setAddMode();
		// ディテール部
		JuchuJuchuDataInDtList editDtList = new JuchuJuchuDataInDtList(searchForm.getMaxSuDt());
		editDtList.setRecordsParPage(searchForm.getMaxSuDt());
		editDtList.setAddMode();

		// 縦線卸店検索サービス
		MastrTatesenOrositen_SearchService mstSearchServ = new MastrTatesenOrositen_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// 縦線卸店情報を取得
		MastrTatesenOrositenRecord tatesenRec = mstSearchServ.getRecMastrTatesenOrositen(searchForm.getOrsTatesnCd(), false);

		// ヘッダー部レコード
		JuchuJuchuDataInRecord editRec = (JuchuJuchuDataInRecord) editList.getFirstRecord();

		// 縦線CDをセット
		editRec.setOrsTatesnCd(searchForm.getOrsTatesnCd());

		// デフォルト値をセット
		this.setDefaultForAdd(editRec, tatesenRec);

		// 注意商品リストを取得
		MastrOrositenSyosaiDtList cautionList = (MastrOrositenSyosaiDtList) mstSearchServ.getListMastrOrositenSyosaiDt(editRec.getOrsTatesnCd());
		// maxSuまで空レコードで埋める
		cautionList.initListToMax(cautionList.size(), editForm.getMaxSu());
		cautionList.setRecordsParPage(editForm.getMaxSu());
		setSession(KEY_SS_CAUTIONLIST, cautionList);

		// リストをセッションへ設定する
		setSession(KEY_SS_EDITLIST, editList);
		setSession(KEY_SS_EDITLIST_DT, editDtList);

		// 呪文：リクエストにEditFormをセット(mode:add, preMode:null, 編集フォーム設定可)
		editForm.setMode(MODE_ADD);
		editForm.setPreMode(MODE_EMPTY);
		editForm.setEditable(IS_EDITABLE_TRUE);

		// 要注意ポイント
		// １明細編集なので<kit:iterate>で必要となる
		// ※ jspのhiddenにも記載すること！
		editForm.setMaxLineSu(INT_EDIT_ONE_RECORD.toString()); // ヘッダー部
		editForm.setMaxLineSu1(Integer.toString(editForm.getMaxSuDt())); // ディテール部

		// 行ごとの編集項目数を設定する
		editList.setEditableElementsParRecord(ELEMENTS_ON_ADD);
		editDtList.setEditableElementsParRecord(ELEMENTS_ON_ADD_DT);

		// 確認、戻るボタンのTabIndexを設定する
		setTabindexes(searchForm.getReqType(), editList, editDtList);
	}

	/**
	 * 新規開始時のデフォルト値をマスターから取得してセット
	 *
	 * @param editRec JuchuJuchuDataInRecord
	 * @param tatesenRec MastrTatesenOrositenRecord
	 */
	private void setDefaultForAdd(JuchuJuchuDataInRecord editRec, MastrTatesenOrositenRecord tatesenRec) {
		// 利用区分
		editRec.setRiyouKbn(AVAILABLE_KB_RIYO_KA); // 利用可

		// 倉庫種類区分
		editRec.setSoukoSyuruiKbn(tatesenRec.getSoukoSyuruiKbn());

		// 出荷先区分
		// 【課税倉庫】倉庫種類区分＝課税倉庫 or 自社倉庫(課税)　かつ　国際エリア区分＝日本の場合
		if (PbsUtil.isIncluded(tatesenRec.getSoukoSyuruiKbn(), SOUKO_SYURUI_KB_KAZEI, SOUKO_SYURUI_KB_JISYA_KAZEI)
				&& PbsUtil.isEqual(tatesenRec.getWorldAreaKbn(), WORLD_AREA_KB_NIHON)) {
			editRec.setSyukaKbn(SHIPMENT_KB_KAZEI);

		// 【未納税倉庫】倉庫種類区分＝未納税倉庫 or 自社倉庫(未納税)　かつ　国際エリア区分＝日本の場合
		} else if (PbsUtil.isIncluded(tatesenRec.getSoukoSyuruiKbn(), SOUKO_SYURUI_KB_MINOZEI, SOUKO_SYURUI_KB_JISYA_MINOZEI)
				&& PbsUtil.isEqual(tatesenRec.getWorldAreaKbn(), WORLD_AREA_KB_NIHON)) {
			editRec.setSyukaKbn(SHIPMENT_KB_MINOZEI);

		// 【未納税（輸出）】倉庫種類区分＝未納税倉庫　かつ　国際エリア区分≠日本の場合
		} else if (PbsUtil.isIncluded(tatesenRec.getSoukoSyuruiKbn(), SOUKO_SYURUI_KB_MINOZEI)
				&& !PbsUtil.isEqual(tatesenRec.getWorldAreaKbn(), WORLD_AREA_KB_NIHON)) {
			editRec.setSyukaKbn(SHIPMENT_KB_YUSYUTU);

		// 【課税倉庫】上記以外
		} else {
			editRec.setSyukaKbn(SHIPMENT_KB_KAZEI);
		}

		// 倉入・直送区分
		editRec.setDeliveryKbn(tatesenRec.getDeliveryKbn());

		// 蔵CD
		editRec.setKuraCd("01"); // 三栖or本店

		// 運賃区分
		if (PbsUtil.isEqual(tatesenRec.getHaisoCoolKbn(), YN_04_KB_TAIO)) {
			editRec.setUnchinKbn(UNTIN_SEQ_KB_MOTO_YOREI); // 元払（要冷）
		} else {
			editRec.setUnchinKbn(UNTIN_SEQ_KB_MOTO_JYOON); // 元払（常温）
		}

		// 運送店CD
		editRec.setUnsotenCd(tatesenRec.getUnsotenCd());

		// 荷受時間区分
		editRec.setNiukeTimeKbn(tatesenRec.getNiukeTimeKbn());

		// 荷受時間_開始
		editRec.setNiukeBiginTime(tatesenRec.getNiukeBiginTime());

		// 荷受時間_終了
		editRec.setNiukeEndTime(tatesenRec.getNiukeEndTime());

		// 担当者CD
		editRec.setTantosyaCd(tatesenRec.getTantosyaCd());

		// 特約店CD
		editRec.setTokuyakutenCd(tatesenRec.getOrositenCd1Jiten());

		// デポCD
		editRec.setDepoCd(tatesenRec.getOrositenCdDepo());

		// 二次店CD
		editRec.setNijitenCd(tatesenRec.getOrositenCd2Jiten());

		// 三次店CD
		editRec.setSanjitenCd(tatesenRec.getOrositenCd3Jiten());

		// 出荷先国CD
		editRec.setSyukaSakiCountryCd(tatesenRec.getSyukaSakiCountryCd());

		// JISCD
		editRec.setJisCd(tatesenRec.getJisCd());

		// 引取運賃換算単価
		editRec.setUnchinCnvTanka(tatesenRec.getUnchinCnvTanka());

		// 摘要区分（01）
		editRec.setTekiyoKbn1(tatesenRec.getTekiyoKbn1());

		// 摘要内容（01）
		if (PbsUtil.isEqual(TEKIYO_KBN_TEGAKI, tatesenRec.getTekiyoKbn1())) {
			editRec.setTekiyoNm1(CHAR_BLANK);
		} else {
			editRec.setTekiyoNm1(tatesenRec.getTekiyoNm1());
		}

		// 摘要区分（02）
		editRec.setTekiyoKbn2(tatesenRec.getTekiyoKbn2());

		// 摘要内容（02）
		if (PbsUtil.isEqual(TEKIYO_KBN_TEGAKI, tatesenRec.getTekiyoKbn2())) {
			editRec.setTekiyoNm2(CHAR_BLANK);
		} else {
			editRec.setTekiyoNm2(tatesenRec.getTekiyoNm2());
		}

		// 摘要区分（03）
		editRec.setTekiyoKbn3(tatesenRec.getTekiyoKbn3());

		// 摘要内容（03）
		if (PbsUtil.isEqual(TEKIYO_KBN_TEGAKI, tatesenRec.getTekiyoKbn3())) {
			editRec.setTekiyoNm3(CHAR_BLANK);
		} else {
			editRec.setTekiyoNm3(tatesenRec.getTekiyoNm3());
		}

		// 摘要区分（04）
		editRec.setTekiyoKbn4(tatesenRec.getTekiyoKbn4());

		// 摘要内容（04）
		if (PbsUtil.isEqual(TEKIYO_KBN_TEGAKI, tatesenRec.getTekiyoKbn4())) {
			editRec.setTekiyoNm4(CHAR_BLANK);
		} else {
			editRec.setTekiyoNm4(tatesenRec.getTekiyoNm4());
		}

		// 摘要区分（05）
		editRec.setTekiyoKbn5(tatesenRec.getTekiyoKbn5());

		// 摘要内容（05）
		if (PbsUtil.isEqual(TEKIYO_KBN_TEGAKI, tatesenRec.getTekiyoKbn5())) {
			editRec.setTekiyoNm5(CHAR_BLANK);
		} else {
			editRec.setTekiyoNm5(tatesenRec.getTekiyoNm5());
		}

		// 最低配送ロット数
		editRec.setLowHaisoLot(tatesenRec.getLowHaisoLot());

		// 運送店名
		editRec.setUnsotenNm(tatesenRec.getUnsotenNm());

		// 最終送荷先卸CD
		editRec.setOrositenCdLast(tatesenRec.getOrositenCdLast());

		// 最終送荷先卸名
		editRec.setOrositenNmLast(tatesenRec.getOrositenNmLast());

		// 担当者名
		editRec.setTantosyaNm(tatesenRec.getTantosyaNm());

		// 特約店名
		editRec.setTokuyakutenNm(tatesenRec.getOrositenNm1Jiten());

		// デポ名
		editRec.setDepoNm(tatesenRec.getOrositenNmDepo());

		// 二次店名
		editRec.setNijitenNm(tatesenRec.getOrositenNm2Jiten());

		// 三次店名
		editRec.setSanjitenNm(tatesenRec.getOrositenNm3Jiten());

		// 単価変更入力フラグ
		editRec.setHtankaChgInpFlg(HTANKA_CHG_INP_FALSE); // 単価は変更しない

		// 当日出荷可能区分
		editRec.setToujituSyukkaKbn(tatesenRec.getToujituSyukkaKbn());

		// 出荷日注意区分
		editRec.setSyukaDtCyuiKbn(tatesenRec.getSyukaDtCyuiKbn());

		// 荷受不可曜日_月
		editRec.setNiukeFukaMon(tatesenRec.getNiukeFukaMon());

		// 荷受不可曜日_火
		editRec.setNiukeFukaTue(tatesenRec.getNiukeFukaTue());

		// 荷受不可曜日_水
		editRec.setNiukeFukaWed(tatesenRec.getNiukeFukaWed());

		// 荷受不可曜日_木
		editRec.setNiukeFukaThu(tatesenRec.getNiukeFukaThu());

		// 荷受不可曜日_金
		editRec.setNiukeFukaFri(tatesenRec.getNiukeFukaFri());

		// 荷受不可曜日_土
		editRec.setNiukeFukaSat(tatesenRec.getNiukeFukaSat());

		// 荷受不可曜日_日
		editRec.setNiukeFukaSun(tatesenRec.getNiukeFukaSun());

		// 荷受不可曜日_祝
		editRec.setNiukeFukaHol(tatesenRec.getNiukeFukaHol());

		// 運送店CD（通常）
		editRec.setUnsotenCdTujyo(tatesenRec.getUnsotenCdTujyo());

		// 運送店名（通常）（略称）
		editRec.setUnsotenNmTujyo(tatesenRec.getUnsotenNmTujyo());

		// 運送店CD（当日）
		editRec.setUnsotenCdToujitu(tatesenRec.getUnsotenCdToujitu());

		// 運送店名（当日）（略称）
		editRec.setUnsotenNmToujitu(tatesenRec.getUnsotenNmToujitu());

		// 運送店CD（引取）
		editRec.setUnsotenCdHikitori(tatesenRec.getUnsotenCdHikitori());

		// 運送店名（引取）（略称）
		editRec.setUnsotenNmHikitori(tatesenRec.getUnsotenNmHikitori());

		// 請求明細グループCD_特約店CD
		editRec.setSmgpTokuyakutenCd(tatesenRec.getTokuyakutenCdSmgp());

		// 与信要注意区分
		editRec.setYosinCyuiKbn(tatesenRec.getYosinCyuiKbn());

		// 与信限度額
		editRec.setYosinGendoGaku(tatesenRec.getYosinGendoGaku());

		// 売掛未入金額
		editRec.setUrikakeMinyukinGaku(tatesenRec.getUrikakeMinyukinGaku());
	}


	/**
	 * 操作フェーズ画面(修正、参照追加)へ遷移させる処理
	 * →チェック後InitList作成
	 *
	 * @param searchForm JuchuJuchuDataIn_SearchForm
	 */
	private void manipulateEdit(JuchuJuchuDataIn_SearchForm searchForm) throws KitException {

		ComUserSession cus = getComUserSession();
		String kaisyaCd = cus.getKaisyaCd(); // 会社CD
		String ktksyCd = cus.getKtksyCd(); // 寄託者CD

		// フォームを初期化
		this.editForm = new JuchuJuchuDataIn_EditForm();

		// モードを取得
		String nextMode = ComKitUtil.getModeByReqTypeOnSearchForm(searchForm.getReqType());

		// EditListを取得する
		JuchuJuchuDataInList editList = (JuchuJuchuDataInList) getSession(KEY_SS_EDITLIST); // ヘッダー部
		JuchuJuchuDataInDtList editDtList = (JuchuJuchuDataInDtList) getSession(KEY_SS_EDITLIST_DT); // ディテール部
		JuchuJuchuDataInList searchedList = (JuchuJuchuDataInList) getSession(KEY_SS_SEARCHEDLIST);

		// 排他サービスを生成する
		ComHaitaService haitaServ = new ComHaitaService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// モードをセットする ･･･ 排他を取得するときに必須
		haitaServ.setMode(nextMode);

		// チェックサービス
		JuchuJuchuDataIn_CheckService checkServ = new JuchuJuchuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// チェックを実施する
		// 排他を先に実行する
		// モードによってチェックの内容が異なる
		if (PbsUtil.isIncluded(searchForm.getReqType(), REQ_TYPE_REFERENCE)) {
			if (!checkServ.validateManipulationReference(editForm, editList, editDtList, searchedList)) {
				// 呪文
				editForm.setMode(MODE_SELECTED);
				editForm.setPreMode(MODE_EMPTY);
				editForm.setEditable(IS_EDITABLE_TRUE);
				return;
			}
		} else if (PbsUtil.isEqual(searchForm.getReqType(), REQ_TYPE_EDIT)) {
			if (!haitaServ.lock(editList)
					|| !checkServ.validateManipulationEdit(editForm, editList, editDtList, searchedList)) {
				// チェックエラー時、排他解除する
                haitaServ.unLock(editList, false);
				// 呪文
				editForm.setMode(MODE_SELECTED);
				editForm.setPreMode(MODE_EMPTY);
				editForm.setEditable(IS_EDITABLE_TRUE);
				return;
			}
		}

		// ヘッダー部レコード
		JuchuJuchuDataInRecord editRec = (JuchuJuchuDataInRecord) editList.getFirstRecord();

		// 初期リストを準備する
		// ヘッダー部
		JuchuJuchuDataInList initList = (JuchuJuchuDataInList) editList.copy();
		setSession(KEY_SS_INITLIST, initList);
		// ディテール部
		JuchuJuchuDataInDtList initDtList = (JuchuJuchuDataInDtList) editDtList.copy();
		setSession(KEY_SS_INITLIST_DT, initDtList);
		// カテゴリデータ
		JuchuJuchuDataIn_SearchService searchServ = new JuchuJuchuDataIn_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		JuchuJuchuDataInCatList initCatList = searchServ.getListJuchuJuchuDataInCat(editRec.getJyucyuNo());
		setSession(KEY_SS_INITLIST_CAT, initCatList);

		// 単価変更入力フラグ
		editRec.setHtankaChgInpFlg(HTANKA_CHG_INP_FALSE); // 単価は変更しない

		// View用
		// ヘッダー部
		checkServ.setCdToNmView(editRec);
		// ディテール部
		checkServ.setViews(initDtList, editDtList);

		// 在庫情報をセット
		editDtList.setZaikoZansu(checkServ, editRec.getKuraCd(), editRec.getSyukaDt(), kaisyaCd, ktksyCd);

		/* *************************************
		 * 要注意ポイント
		 * 呼出し後に検索部の様子が変わり、その後のボタン
		 * アクション先を共通関数で取得する 呪文の一種
		 ************************************ */

		// 操作系画面へ
		this.editForm.setMode(nextMode);
		this.editForm.setPreMode(MODE_EMPTY);
		this.editForm.setEditable(IS_EDITABLE_TRUE);

		// 要注意ポイント
		// １明細編集なので<kit:iterate>で必要となる
		// ※ jspのhiddenにも記載すること！
		this.editForm.setMaxLineSu(INT_EDIT_ONE_RECORD.toString());

		// 編集リストを成型する
		editList.format(searchForm.getReqType()); // ヘッダー部
		editDtList.format(searchForm.getReqType()); // ディテール部

		// 注意商品リストを取得
		MastrTatesenOrositen_SearchService mstSearchServ = new MastrTatesenOrositen_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		MastrOrositenSyosaiDtList cautionList = (MastrOrositenSyosaiDtList) mstSearchServ.getListMastrOrositenSyosaiDt(editRec.getOrsTatesnCd());
		// maxSuまで空レコードで埋める
		cautionList.initListToMax(cautionList.size(), editForm.getMaxSu());
		cautionList.setRecordsParPage(editForm.getMaxSu());
		setSession(KEY_SS_CAUTIONLIST, cautionList);

		// 確認、戻るボタンのTABINDEXを設定する
		setTabindexes(searchForm.getReqType(), editList, editDtList);

	}

	/**
	 * 操作フェーズ画面(抹消)へ遷移させる処理
	 * →チェック前InitList作成
	 *
	 * @param searchForm JuchuJuchuDataIn_SearchForm
	 */
	private void manipulateView(JuchuJuchuDataIn_SearchForm searchForm) {

		// フォームを初期化
		this.editForm = new JuchuJuchuDataIn_EditForm();

		// モードを取得
		String nextMode = ComKitUtil.getModeByReqTypeOnSearchForm(searchForm.getReqType());

		// EditListを取得する
		JuchuJuchuDataInList editList = (JuchuJuchuDataInList) getSession(KEY_SS_EDITLIST); // ヘッダー部
		JuchuJuchuDataInDtList editDtList = (JuchuJuchuDataInDtList) getSession(KEY_SS_EDITLIST_DT); // ディテール部
		JuchuJuchuDataInList searchedList = (JuchuJuchuDataInList) getSession(KEY_SS_SEARCHEDLIST);

		// 排他サービスを生成する
		ComHaitaService haitaServ = new ComHaitaService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// モードをセットする ･･･ 排他を取得するときに必須
		haitaServ.setMode(nextMode);

		// 初期リストを準備する
		// ヘッダー部
		JuchuJuchuDataInList initList = (JuchuJuchuDataInList) editList.copy();
		setSession(KEY_SS_INITLIST, initList);
		// ディテール部
		JuchuJuchuDataInDtList initDtList = (JuchuJuchuDataInDtList) editDtList.copy();
		setSession(KEY_SS_INITLIST_DT, initDtList);
		// カテゴリデータ
		JuchuJuchuDataIn_SearchService searchServ = new JuchuJuchuDataIn_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		JuchuJuchuDataInCatList initCatList = searchServ.getListJuchuJuchuDataInCat(editForm.getJyucyuNo());
		setSession(KEY_SS_INITLIST_CAT, initCatList);

		// チェックサービス
		JuchuJuchuDataIn_CheckService checkServ = new JuchuJuchuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// チェックを実施する
		// 排他を先に実行する
		// モードによってチェックの内容が異なる
		if (PbsUtil.isEqual(searchForm.getReqType(), REQ_TYPE_DELETE)) {
			if (!haitaServ.lock(editList)
					|| !checkServ.validateManipulationDelete(editForm, editList, editDtList, searchedList)) {
				// チェックエラー時、排他解除する
                haitaServ.unLock(editList, false);
                // 呪文
				editForm.setMode(MODE_SELECTED);
				editForm.setPreMode(MODE_EMPTY);
				editForm.setEditable(IS_EDITABLE_TRUE);
				return;
			}
		} else if (PbsUtil.isEqual(searchForm.getReqType(), REQ_TYPE_REBIRTH)) {
			if (!haitaServ.lock(editList)
					|| !checkServ.validateManipulationRebirth(editForm, editList, editDtList, searchedList)) {
				// チェックエラー時、排他解除する
                haitaServ.unLock(editList, false);
				// 呪文
				editForm.setMode(MODE_SELECTED);
				editForm.setPreMode(MODE_EMPTY);
				editForm.setEditable(IS_EDITABLE_TRUE);
				return;
			}
		}

		/* *************************************
		 * 要注意ポイント
		 * 呼出し後に検索部の様子が変わり、その後のボタン
		 * アクション先を共通関数で取得する 呪文の一種
		 ************************************ */

		// 操作系画面へ
		this.editForm.setMode(nextMode);
		this.editForm.setPreMode(MODE_EMPTY);
		this.editForm.setEditable(IS_EDITABLE_TRUE);

		// 要注意ポイント
		// １明細編集なので<kit:iterate>で必要となる
		// ※ jspのhiddenにも記載すること！
		this.editForm.setMaxLineSu(INT_EDIT_ONE_RECORD.toString());

		// 編集リストを成型する
		editList.format(searchForm.getReqType()); // ヘッダー部
		editDtList.format(searchForm.getReqType()); // ディテール部

		// 確認、戻るボタンのTABINDEXを設定する
		setTabindexes(searchForm.getReqType(), editList, editDtList);

	}

	/**
	 * 確認、戻るボタンにタブインデックスを設定する
	 *
	 * @param reqType
	 * @param editList
	 * @param editDtList
	 */
	private void setTabindexes(String reqType, JuchuJuchuDataInList editList, JuchuJuchuDataInDtList editDtList) {

		String idx = TAB_INDEX_HEAD;

		switch (reqType) {
		case REQ_TYPE_EDIT: // 修正
			editList.setEditableElementsParRecord(ELEMENTS_ON_EDIT);
			editDtList.setEditableElementsParRecord(ELEMENTS_ON_EDIT_DT);
			idx = Integer.toString(Integer.parseInt(editList.getConfirmBtnTabIndex())
					+ Integer.parseInt(editDtList.getConfirmBtnTabIndex()) - 1);
			break;

		case REQ_TYPE_ADD: // 新規
			editList.setEditableElementsParRecord(ELEMENTS_ON_ADD);
			editDtList.setEditableElementsParRecord(ELEMENTS_ON_ADD_DT);
			idx = Integer.toString(Integer.parseInt(editList.getConfirmBtnTabIndex())
					+ Integer.parseInt(editDtList.getConfirmBtnTabIndex()) - 1);
			break;

		case REQ_TYPE_REFERENCE: // 参照追加
			editList.setEditableElementsParRecord(ELEMENTS_ON_REFERENCE);
			editDtList.setEditableElementsParRecord(ELEMENTS_ON_REFERENCE_DT);
			idx = Integer.toString(Integer.parseInt(editList.getConfirmBtnTabIndex())
					+ Integer.parseInt(editDtList.getConfirmBtnTabIndex()) - 1);
			break;

		default: // 抹消
			break;
		}

		editForm.setTabIndexConfirmAndBack(idx);

	}

} // -- class
