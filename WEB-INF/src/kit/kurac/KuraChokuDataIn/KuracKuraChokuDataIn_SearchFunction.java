package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.kurac.KuraChokuDataIn.IKuracKuraChokuDataIn.*;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComHaitaService;
import fb.com.ComKitUtil;
import fb.com.exception.NoDataException;
import fb.inf.KitFunction;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 検索部からのリクエストを処理する実装クラスです
 */
public class KuracKuraChokuDataIn_SearchFunction extends KitFunction {

	/**
	 *
	 */

	/** シリアルID */
	private static final long serialVersionUID = -996965913117250516L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = KuracKuraChokuDataIn_SearchFunction.class.getName();

	/** 明細部 */
	private KuracKuraChokuDataIn_EditForm editForm;

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
	public KuracKuraChokuDataIn_SearchFunction(ActionMapping mapping, ActionForm form,
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

		KuracKuraChokuDataIn_SearchForm searchForm = (KuracKuraChokuDataIn_SearchForm) form_;

		// ----------------------------------------------------
		// 新規 on init
		// ----------------------------------------------------
		if (REQ_TYPE_ADD.equals(searchForm.getReqType())) {
			add(searchForm);

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
		// 受注追加 on init
		// ----------------------------------------------------
		} else if (REQ_TYPE_JYUCYUADD.equals(searchForm.getReqType())) {
			jyucyuAdd(searchForm);

		// ----------------------------------------------------
		// 出荷追加 on init
		// ----------------------------------------------------
		} else if (REQ_TYPE_SYUKAADD.equals(searchForm.getReqType())) {
			syukaAdd(searchForm);

		// ----------------------------------------------------
		// 修正、参照追加、売上訂正(+)、売上訂正(-) on selected
		// ----------------------------------------------------
		} else if (PbsUtil.isIncluded(searchForm.getReqType(),
				REQ_TYPE_EDIT, REQ_TYPE_REFERENCE, REQ_TYPE_REFPLUS, REQ_TYPE_REFMINUS)) {
			manipulateEdit(searchForm);

		// ----------------------------------------------------
		// 抹消 on selected
		// ----------------------------------------------------
		} else if (REQ_TYPE_DELETE.equals(searchForm.getReqType())) {

			manipulateView(searchForm);

			// 確認画面を表示せずに確定処理を行う
			KuracKuraChokuDataIn_EditForm editForm_ = (KuracKuraChokuDataIn_EditForm) getSession(KEY_SS_EDITFORM);
			editForm_.setReqType(REQ_TYPE_COMMIT); // リクエストタイプ
			editForm_.setMode(searchForm.getReqType()); // モード
			editForm_.setPreMode(MODE_EMPTY); // プレモード
			KuracKuraChokuDataIn_EditFunction callFunc = new KuracKuraChokuDataIn_EditFunction(mapping_, editForm_, request_, response_);
			return callFunc.execute_();

		}

		//
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
	private void search(KuracKuraChokuDataIn_SearchForm searchForm) throws NoDataException {

		// --------------------------
		// EditListビーンを初期化(削除)する。
		// --------------------------
		removeSession(KEY_SS_INITLIST);
		removeSession(KEY_SS_EDITLIST);
		removeSession(KEY_SS_INITLIST_DT);
		removeSession(KEY_SS_EDITLIST_DT);
		removeSession(KEY_SS_SEARCHEDLIST);

		// チェックサービスを初期化
		KuracKuraChokuDataIn_CheckService checkServ = new KuracKuraChokuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 呼出時チェックを実行
		if (!checkServ.validateSearch(searchForm)) {
			// リクエストにEditFormをセット
			// エラー検知時(mode:null, preMode:null, 編集フォーム可)
			editForm = new KuracKuraChokuDataIn_EditForm();
			return;
		}

		try {
			// 検索実行
			KuracKuraChokuDataIn_SearchService searchServ = new KuracKuraChokuDataIn_SearchService(
					getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
			PbsRecord[] records = searchServ.execute(searchForm);

			// 検索結果をリストクラスに変換
			KuracKuraChokuDataInList searchedList = new KuracKuraChokuDataInList(records);

			// ソート指定を初期化
			searchForm.sortInitialize("MOUSIKOMI_NO", SORT_DESC);

			// 呪文 ・・・ １ページ目指定＆ページあたり行数指定
			searchedList.setOffset(0);
			searchedList.setRecordsParPage(searchForm.getMaxSu());

			// リクエストにEditFormをセット(mode:edit or delete or result, preMode:null,
			// 編集フォーム設定可)
			editForm = new KuracKuraChokuDataIn_EditForm();
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
			// リクエストにEditFormをセット(mode:null, preMode:null)
			editForm = new KuracKuraChokuDataIn_EditForm();
			throw new NoDataException();
		}

	}

	/**
	 * 「ソート」処理を行う
	 *
	 * @param searchForm KuracKuraChokuDataIn_SearchForm
	 */
	private void sort(KuracKuraChokuDataIn_SearchForm searchForm) {

		// 検索結果は呼出時に取得したものを使う
		KuracKuraChokuDataInList searchedList = (KuracKuraChokuDataInList) getSession(KEY_SS_SEARCHEDLIST);

		// 呪文 ・・・ １ページ目指定＆ページあたり行数指定
		searchedList.setOffset(0);
		searchedList.setRecordsParPage(searchForm.getMaxSu());

		// リクエストにEditFormをセット(mode:edit or delete or result, preMode:null,
		// 編集フォーム設定可)
		editForm = new KuracKuraChokuDataIn_EditForm();
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
		KuracKuraChokuDataInList sortedList = searchedList;

		// 文字列としてソート
		if (SORT_ASC.equals(searchForm.getSortOrder())) { // 昇順
			sortedList = (KuracKuraChokuDataInList) ComKitUtil.sortPbsRecordListAsc(searchedList, searchForm.getSortItem());
		} else if (SORT_DESC.equals(searchForm.getSortOrder())) { // 降順
			sortedList = (KuracKuraChokuDataInList) ComKitUtil.sortPbsRecordListDesc(searchedList, searchForm.getSortItem());
		}

		// 検索結果をセッションへ、明細部をリクエストへ設定する
		setSession(KEY_SS_SEARCHEDLIST, sortedList);
	}

	/**
	 * 「受注追加」処理を行う
	 *
	 * @param searchForm KuracKuraChokuDataIn_SearchForm
	 */
	private void jyucyuAdd(KuracKuraChokuDataIn_SearchForm searchForm) {

		KuracKuraChokuDataIn_JyucyuService Jyucyu_Service= new KuracKuraChokuDataIn_JyucyuService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		Jyucyu_Service.executeJyucyu();

		// --------------------------
		// EditListビーンを初期化(削除)する。
		// --------------------------
		removeSession(KEY_SS_INITLIST);
		removeSession(KEY_SS_EDITLIST);
		removeSession(KEY_SS_INITLIST_DT);
		removeSession(KEY_SS_EDITLIST_DT);
		removeSession(KEY_SS_SEARCHEDLIST);

		editForm = new KuracKuraChokuDataIn_EditForm();

	}


	/**
	 * 「出荷追加」処理を行う
	 *
	 * @param searchForm KuracKuraChokuDataIn_SearchForm
	 */
	private void syukaAdd(KuracKuraChokuDataIn_SearchForm searchForm) {

		KuracKuraChokuDataIn_SyukaService Syuka_Service= new KuracKuraChokuDataIn_SyukaService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		Syuka_Service.executeSyuka();

		// --------------------------
		// EditListビーンを初期化(削除)する。
		// --------------------------
		removeSession(KEY_SS_INITLIST);
		removeSession(KEY_SS_EDITLIST);
		removeSession(KEY_SS_INITLIST_DT);
		removeSession(KEY_SS_EDITLIST_DT);
		removeSession(KEY_SS_SEARCHEDLIST);

		editForm = new KuracKuraChokuDataIn_EditForm();

	}


	/**
	 * 「新規」処理の準備作業を行う.
	 *
	 * @param searchForm JuchuJuchuDataIn_SearchForm
	 */
	private void add(KuracKuraChokuDataIn_SearchForm searchForm) {

		// --------------------------
		// EditListビーンを初期化(削除)する。
		// --------------------------
		removeSession(KEY_SS_INITLIST);
		removeSession(KEY_SS_EDITLIST);
		removeSession(KEY_SS_INITLIST_DT);
		removeSession(KEY_SS_EDITLIST_DT);
		removeSession(KEY_SS_SEARCHEDLIST);

		// 明細フォームを初期化する
		editForm = new KuracKuraChokuDataIn_EditForm();

		// =================================================================
		// 明細ヘッダ項目をチェックする
		KuracKuraChokuDataIn_CheckService checkServ = new KuracKuraChokuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 追加時チェック
		if (!checkServ.validateSearchForAdd(searchForm)) {
			// リクエストにEditFormをセット
			// エラー検知時(mode:null, preMode:null, 編集フォーム可)
			editForm = new KuracKuraChokuDataIn_EditForm();
			return;
		}

		// =================================================================
		// ・空のリストを作成
		// =================================================================
		// ヘッダー部
		KuracKuraChokuDataInList editList = new KuracKuraChokuDataInList(INT_EDIT_ONE_RECORD);
		editList.setRecordsParPage(searchForm.getMaxSu());
		editList.setAddMode();

		// ヘッダレコード
		KuracKuraChokuDataInRecord editRec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();
		// ヘッダのデフォルト値をセット
		this.setHdDefaultForAdd(editRec);

		// ディテールのデフォルト情報を取得
		KuracKuraChokuDataInDtList editDtList = this.getDtDefaultInfoByKbn(searchForm.getJigyousyo(), searchForm.getShohinGrp());
		// maxSuまで空レコードで埋める
		editDtList.initListToMax(editDtList.size(), editForm.getMaxSuDt());
		// ==================================
		// ディテール部の表示用情報を設定
		// ==================================
		editDtList.setDtInfo();
		// 共通情報を設定
		editDtList.format(searchForm.getReqType());
		editDtList.setRecordsParPage(searchForm.getMaxSuDt());
		//editDtList.setAddMode();

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
		editForm.setMaxLineSu1(String.valueOf(searchForm.getMaxSuDt()));// ディテール部

		// 行ごとの編集項目数を設定する
		editList.setEditableElementsParRecord(ELEMENTS_ON_ADD);
		editDtList.setEditableElementsParRecord(ELEMENTS_ON_ADD_DT);

		// 確認、戻るボタンのTabIndexを設定する
		setTabindexes(searchForm.getReqType(), editList, editDtList);
	}

	/**
	 * 新規開始時のヘッダのデフォルト値をセット
	 *
	 * @param editRec KuracKuraChokuDataInRecord
	 */
	private void setHdDefaultForAdd(KuracKuraChokuDataInRecord editRec) {
		KuracKuraChokuDataIn_SearchForm searchForm =  (KuracKuraChokuDataIn_SearchForm) getSession(KEY_SS_SEARCHFORM); // 検索部
		// 利用区分
		editRec.setRiyouKbn(AVAILABLE_KB_RIYO_KA); // 利用可

		// 事業所区分
		editRec.setJigyosyoKbn(searchForm.getJigyousyo());
		// 商品区分
		editRec.setShoninGrpCd(searchForm.getShohinGrp());
		// 申込受付日
		editRec.setUketukeDt(PbsUtil.getYMD(searchForm.getMousikomibi()));
		// 発送予定日
		editRec.setHasoYoteiDt(PbsUtil.getYMD(searchForm.getHasoyoteibi()));
		// データ種別コード
		editRec.setSyubetuCd(KURAC_DATA_KB_TUJYO);
	}

	/**
	 * ディテール部のデフォルト値をセット
	 * @param jigyouKbn 事業所区分
	 * @param ShohinGrp	商品グループ区分
	 * @return
	 */
	private KuracKuraChokuDataInDtList getDtDefaultInfoByKbn(String jigyouKbn, String ShohinGrp) {
		KuracKuraChokuDataIn_SearchService searchServ = new KuracKuraChokuDataIn_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// ディテール部のデフォルト値を取得
		KuracKuraChokuDataInDtList editDtList = searchServ.getKurachokuDataInDtListByKbn(jigyouKbn, ShohinGrp);

		return editDtList;
	}


	/**
	 * 参照追加時ディテールのデフォルト値をセット
	 *
	 * @param editRec KuracKuraChokuDataInRecord
	 */
	private void setDtDefaultForRef(KuracKuraChokuDataInDtList editDtList, KuracKuraChokuDataInDtList dtDefaultLst) {

		// 元の値をクリア
		for (int i = 0; i < editDtList.size(); i++) {
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 未入力行は無視する
			if (rec.isEmpty()) {
				continue;
			}

			rec.setShohinCd(CHAR_BLANK);
			rec.setShohinNm(CHAR_BLANK);
			rec.setYoukiKigoNm(CHAR_BLANK);
			rec.setShohinSet(CHAR_BLANK);
			rec.setHanbaiTanka(CHAR_BLANK);
			rec.setHanbaiGaku(CHAR_BLANK);
			rec.setKuradenLineNo(CHAR_BLANK);
			rec.setCpKbn(CHAR_BLANK);
		}

		// ディテールのデフォルト値をセット
		for (int i = 0; i < dtDefaultLst.size(); i++) {
			KuracKuraChokuDataInDtRecord oRec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);
			KuracKuraChokuDataInDtRecord nRec = (KuracKuraChokuDataInDtRecord) dtDefaultLst.get(i);

			// 商品コード
			oRec.setShohinCd(nRec.getShohinCd());
			// 商品名
			oRec.setShohinNm(nRec.getShohinNm());
			// 容器名
			oRec.setYoukiKigoNm(nRec.getYoukiKigoNm());
			// 販売単価
			oRec.setHanbaiTanka(new BigDecimal(nRec.getHanbaiTanka()).setScale(2, BigDecimal.ROUND_DOWN).toString());
			// 申込用紙表記順位
			oRec.setKuradenLineNo(nRec.getKuradenLineNo());
			// ｷｬﾝﾍﾟｰﾝ対象区分
			oRec.setCpKbn(nRec.getCpKbn());
		}
	}
	/**
	 * 操作フェーズ画面(修正、参照追加)へ遷移させる処理
	 * →チェック後InitList作成
	 *
	 * @param searchForm KuracKuraChokuDataIn_SearchForm
	 */
	private void manipulateEdit(KuracKuraChokuDataIn_SearchForm searchForm) throws KitException {

		// フォームを初期化
		this.editForm = new KuracKuraChokuDataIn_EditForm();

		// モードを取得
		String nextMode = ComKitUtil.getModeByReqTypeOnSearchForm(searchForm.getReqType());

		// EditListを取得する
		KuracKuraChokuDataInList editList = (KuracKuraChokuDataInList) getSession(KEY_SS_EDITLIST); // ヘッダー部
		KuracKuraChokuDataInDtList editDtList = (KuracKuraChokuDataInDtList) getSession(KEY_SS_EDITLIST_DT); // ディテール部
		KuracKuraChokuDataInList searchedList = (KuracKuraChokuDataInList) getSession(KEY_SS_SEARCHEDLIST);

		// 排他サービスを生成する
		ComHaitaService haitaServ = new ComHaitaService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// モードをセットする ･･･ 排他を取得するときに必須
		haitaServ.setMode(nextMode);

		// チェックサービス
		KuracKuraChokuDataIn_CheckService checkServ = new KuracKuraChokuDataIn_CheckService(
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
		} else if (PbsUtil.isEqual(searchForm.getReqType(), REQ_TYPE_REFPLUS)) {
			if (!checkServ.validateManipulationRefplus(editForm, editList, editDtList, searchedList)) {
				// 呪文
				editForm.setMode(MODE_SELECTED);
				editForm.setPreMode(MODE_EMPTY);
				editForm.setEditable(IS_EDITABLE_TRUE);
				return;
			}
		} else if (PbsUtil.isEqual(searchForm.getReqType(), REQ_TYPE_REFMINUS)) {
			if (!checkServ.validateManipulationRefplus(editForm, editList, editDtList, searchedList)) {
				// 呪文
				editForm.setMode(MODE_SELECTED);
				editForm.setPreMode(MODE_EMPTY);
				editForm.setEditable(IS_EDITABLE_TRUE);
				return;
			}
		}

		// 初期リストを準備する
		// ヘッダー部
		KuracKuraChokuDataInList initList = (KuracKuraChokuDataInList) editList.copy();
		setSession(KEY_SS_INITLIST, initList);
		// ディテール部
		KuracKuraChokuDataInDtList initDtList = (KuracKuraChokuDataInDtList) editDtList.copy();
		setSession(KEY_SS_INITLIST_DT, initDtList);

		// 参照追加の場合
		if (PbsUtil.isIncluded(searchForm.getReqType(), REQ_TYPE_REFERENCE)) {
			KuracKuraChokuDataInRecord hdRec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();
			// ディテールのデフォルト情報を取得
			KuracKuraChokuDataInDtList dtDefaultList = this.getDtDefaultInfoByKbn(hdRec.getJigyosyoKbn(), hdRec.getShoninGrpCd());
			this.setDtDefaultForRef(editDtList, dtDefaultList);
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
	 * 操作フェーズ画面(抹消)へ遷移させる処理
	 * →チェック前InitList作成
	 *
	 * @param searchForm KuracKuraChokuDataIn_SearchForm
	 */
	private void manipulateView(KuracKuraChokuDataIn_SearchForm searchForm) {

//		// フォームを初期化
//		this.editForm = new KuracKuraChokuDataIn_EditForm();
//
//		// モードを取得
		String nextMode = ComKitUtil.getModeByReqTypeOnSearchForm(searchForm.getReqType());

		// EditListを取得する
		KuracKuraChokuDataInList editList = (KuracKuraChokuDataInList) getSession(KEY_SS_EDITLIST); // ヘッダー部
		KuracKuraChokuDataInDtList editDtList = (KuracKuraChokuDataInDtList) getSession(KEY_SS_EDITLIST_DT); // ディテール部
		KuracKuraChokuDataInList searchedList = (KuracKuraChokuDataInList) getSession(KEY_SS_SEARCHEDLIST);

		// 排他サービスを生成する
		ComHaitaService haitaServ = new ComHaitaService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// モードをセットする ･･･ 排他を取得するときに必須
		haitaServ.setMode(nextMode);

//		// 初期リストを準備する
//		// ヘッダー部
//		KuracKuraChokuDataInList initList = (KuracKuraChokuDataInList) editList.copy();
//		setSession(KEY_SS_INITLIST, initList);
//		// ディテール部
//		KuracKuraChokuDataInDtList initDtList = (KuracKuraChokuDataInDtList) editDtList.copy();
//		setSession(KEY_SS_INITLIST_DT, initDtList);
//		// カテゴリデータ
//		KuracKuraChokuDataIn_SearchService searchServ = new KuracKuraChokuDataIn_SearchService(
//				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
//		KuracKuraChokuDataInCatList initCatList = searchServ.getListJuchuJuchuDataInCat(editForm.getMousikomiNo());
//		setSession(KEY_SS_INITLIST_CAT, initCatList);
//
		// チェックサービス
		KuracKuraChokuDataIn_CheckService checkServ = new KuracKuraChokuDataIn_CheckService(
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
		}
//		else if (PbsUtil.isEqual(searchForm.getReqType(), REQ_TYPE_REBIRTH)) {
//			if (!haitaServ.lock(editList)
//					|| !checkServ.validateManipulationRebirth(editForm, editList, editDtList, searchedList)) {
//				// チェックエラー時、排他解除する
//                haitaServ.unLock(editList, false);
//				// 呪文
//				editForm.setMode(MODE_SELECTED);
//				editForm.setPreMode(MODE_EMPTY);
//				editForm.setEditable(IS_EDITABLE_TRUE);
//				return;
//			}
//		}
//
		/* *************************************
		 * 要注意ポイント
		 * 呼出し後に検索部の様子が変わり、その後のボタン
		 * アクション先を共通関数で取得する 呪文の一種
		 ************************************ */

//		// 操作系画面へ
//		this.editForm.setMode(nextMode);
//		this.editForm.setPreMode(MODE_EMPTY);
//		this.editForm.setEditable(IS_EDITABLE_TRUE);

//		// 要注意ポイント
//		// １明細編集なので<kit:iterate>で必要となる
//		// ※ jspのhiddenにも記載すること！
//		this.editForm.setMaxLineSu(INT_EDIT_ONE_RECORD.toString());

		// 編集リストを成型する
		editList.format(searchForm.getReqType()); // ヘッダー部
		editDtList.format(searchForm.getReqType()); // ディテール部

//		// 確認、戻るボタンのTABINDEXを設定する
//		setTabindexes(searchForm.getReqType(), editList, editDtList);

	}

	/**
	 * 確認、戻るボタンにタブインデックスを設定する
	 *
	 * @param reqType
	 * @param editList
	 * @param editDtList
	 */
	private void setTabindexes(String reqType, KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList) {

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

		case REQ_TYPE_REFPLUS: // 訂正(+)
			editList.setEditableElementsParRecord(ELEMENTS_ON_REFPLUS);
			editDtList.setEditableElementsParRecord(ELEMENTS_ON_REFPLUS_DT);
			idx = Integer.toString(Integer.parseInt(editList.getConfirmBtnTabIndex())
					+ Integer.parseInt(editDtList.getConfirmBtnTabIndex()) - 1);
			break;

		case REQ_TYPE_REFMINUS: // 訂正(-)
			editList.setEditableElementsParRecord(ELEMENTS_ON_REFMINUS);
			editDtList.setEditableElementsParRecord(ELEMENTS_ON_REFMINUS_DT);
			idx = Integer.toString(Integer.parseInt(editList.getConfirmBtnTabIndex())
					+ Integer.parseInt(editDtList.getConfirmBtnTabIndex()) - 1);
			break;

		default: // 抹消
			break;
		}

		editForm.setTabIndexConfirmAndBack(idx);

	}

} // -- class
