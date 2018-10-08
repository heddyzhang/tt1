package kit.edi.EdiJuchuMainte;

import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.edi.EdiJuchuMainte.IEdiJuchuMainte.*;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComBatchUtil;
import fb.com.ComHaitaService;
import fb.com.ComKitUtil;
import fb.com.exception.KitComException;
import fb.com.exception.NoDataException;
import fb.com.exception.OverMaxDataException;
import fb.inf.KitFunction;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.KitException;
import fb.inf.exception.MaxRowsException;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;
/**
 * 検索部からのリクエストを処理する実装クラスです
 */
public class EdiJuchuMainte_SearchFunction  extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = -1171172876858887061L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = EdiJuchuMainte_SearchFunction.class.getName();

	/** 明細部 */
	private EdiJuchuMainte_EditForm editForm;

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
	public EdiJuchuMainte_SearchFunction(ActionMapping mapping, ActionForm form,
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
	protected String execute_() throws SQLException, NoDataException, KitException {
		EdiJuchuMainte_SearchForm searchForm = (EdiJuchuMainte_SearchForm) form_;


		// ----------------------------------------------------
		// 呼出 on init
		// ----------------------------------------------------
        if (REQ_TYPE_SEARCH.equals(searchForm.getReqType())) {
			search(searchForm);

		// ----------------------------------------------------
		// 修正 on selected
		// ----------------------------------------------------
		} else if (PbsUtil.isIncluded(searchForm.getReqType(), REQ_TYPE_EDIT)) {
			manipulateEdit(searchForm);

		// ----------------------------------------------------
		// CSV出力
		// ----------------------------------------------------
		}else if (REQ_TYPE_CSVMAKE.equals(searchForm.getReqType())) {

			setEditForm();
			csvMake(searchForm);

			return FORWARD_SUCCESS;

		// ----------------------------------------------------
		// 受注追加
		// ----------------------------------------------------
		}else if  (REQ_TYPE_JYUCYUDATA_KOSIN.equals(searchForm.getReqType())) {
			// バッチ起動共通クラス
			ComBatchUtil comBatchUtil = new ComBatchUtil(getComUserSession(), getDatabase(), isTransactionalFunction(), getActionErrors());

			try {
				setEditForm();
				//ジョブネット起動 受注追加
				comBatchUtil.startJobnet(JOB_TYPE_JYUCYUDATA_KOSIN_JOBNET, JOB_TYPE_JYUCYUDATA_KOSIN_MSG);

				return FORWARD_SUCCESS;

			}  catch (Exception e) {

				return FORWARD_FAIL;

			}

		}

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
	private void search(EdiJuchuMainte_SearchForm searchForm) throws NoDataException {

		// --------------------------
		// EditListビーンを初期化(削除)する。
		// --------------------------
		removeSession(KEY_SS_INITLIST);
		removeSession(KEY_SS_EDITLIST);
		removeSession(KEY_SS_INITLIST_DT);
		removeSession(KEY_SS_EDITLIST_DT);
		removeSession(KEY_SS_SEARCHEDLIST);
		searchForm.setCsvBtnFlg(BUTTON_DISABLED_FALSE);	// ファイル出力ボタンを非活性化

		// チェックサービスを初期化
		EdiJuchuMainte_CheckService checkServ = new EdiJuchuMainte_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 呼出時チェックを実行
		if (!checkServ.validateSearch(searchForm)) {
			// リクエストにEditFormをセット
			// エラー検知時(mode:null, preMode:null, 編集フォーム可)
			editForm = new EdiJuchuMainte_EditForm();

			return;
		}

		try {
			// 検索実行
			EdiJuchuMainte_SearchService searchServ = new EdiJuchuMainte_SearchService(
					getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
			PbsRecord[] records = searchServ.execute(searchForm);

			// 検索結果をリストクラスに変換
			EdiJuchuMainteList searchedList = new EdiJuchuMainteList(records);

			// 結果出力ボタンを活性化
			if (!searchedList.isEmpty()) {
				searchForm.setCsvBtnFlg(BUTTON_AVAILABLE_TRUE);
			}

			// １ページ目指定＆ページあたり行数指定
			searchedList.setOffset(0);
			searchedList.setRecordsParPage(searchForm.getMaxSu());

			// リクエストにEditFormをセット(mode:edit or delete or result, preMode:null,
			// 編集フォーム設定可)
			editForm = new EdiJuchuMainte_EditForm();
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
			editForm = new EdiJuchuMainte_EditForm();

			throw new NoDataException();
		}

	}

	/**
	 * 操作フェーズ画面(修正)へ遷移させる処理
	 * →チェック後InitList作成
	 *
	 * @param searchForm KuracKuraChokuDataIn_SearchForm
	 */
	private void manipulateEdit(EdiJuchuMainte_SearchForm searchForm) throws KitException {

		// フォームを初期化
		this.editForm = new EdiJuchuMainte_EditForm();

		// モードを取得
		String nextMode = ComKitUtil.getModeByReqTypeOnSearchForm(searchForm.getReqType());

		// EditListを取得する
		EdiJuchuMainteList editList = (EdiJuchuMainteList) getSession(KEY_SS_EDITLIST); // ヘッダー部
		EdiJuchuMainteDtList editDtList = (EdiJuchuMainteDtList) getSession(KEY_SS_EDITLIST_DT); // ディテール部
		EdiJuchuMainteList searchedList = (EdiJuchuMainteList) getSession(KEY_SS_SEARCHEDLIST);

		// 排他サービスを生成する
		ComHaitaService haitaServ = new ComHaitaService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// モードをセットする ･･･ 排他を取得するときに必須
		haitaServ.setMode(nextMode);

		// チェックサービス
		EdiJuchuMainte_CheckService checkServ = new EdiJuchuMainte_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// チェックを実施する
		// 排他を先に実行する
		if (PbsUtil.isEqual(searchForm.getReqType(), REQ_TYPE_EDIT)) {
			if (!haitaServ.lock(editList)
					|| !checkServ.validateManipulationEdit(editList, editDtList, searchedList)) {
				// チェックエラー時、排他解除する
                haitaServ.unLock(editList, false);
				// 呪文
				editForm.setMode(MODE_SELECTED);
				editForm.setPreMode(MODE_EMPTY);
				editForm.setEditable(IS_EDITABLE_TRUE);
				return;
			}
		}

		// 初期リストを準備する
		// ヘッダー部
		EdiJuchuMainteList initList = (EdiJuchuMainteList) editList.copy();
		setSession(KEY_SS_INITLIST, initList);
		// ディテール部
		EdiJuchuMainteDtList initDtList = (EdiJuchuMainteDtList) editDtList.copy();
		setSession(KEY_SS_INITLIST_DT, initDtList);

		// 操作系画面へ
		this.editForm.setMode(nextMode);
		this.editForm.setPreMode(MODE_EMPTY);
		this.editForm.setEditable(IS_EDITABLE_TRUE);

		// 要注意ポイント
		// １明細編集なので<kit:iterate>で必要となる
		// ※ jspのhiddenにも記載すること！
		this.editForm.setMaxLineSu(INT_EDIT_ONE_RECORD.toString());

		// 確認、戻るボタンのTABINDEXを設定する
		setTabindexes(searchForm.getReqType(), editList, editDtList);

	}

	/**
	 * CSV出力
	 * @param searchForm 検索Form
	 */
	private void csvMake(EdiJuchuMainte_SearchForm searchForm) throws SQLException, KitComException {

		// チェックサービスを初期化
		EdiJuchuMainte_CheckService checkServ = new EdiJuchuMainte_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		//  CSV出力チェックを実行
		if (!checkServ.validateSearch(searchForm)) {
			return;
		}

		try {
			EdiJuchuMainte_SearchService searchServ = new EdiJuchuMainte_SearchService(
					getComUserSession(), getDatabase(), isTransaction(),
					getActionErrors());

			searchServ.executeCsvDownload(searchForm);

			searchForm.setMode(MODE_RESULT);
			setRequest(ONLOAD_POPUP_URL, CSV_DOWNLOAD_POPUP_URL);

		} catch (DataNotFoundException e) {
			throw new NoDataException();

		} catch (MaxRowsException e) {
			throw new OverMaxDataException();

		}
	}


	/**
	 * 確認、戻るボタンにタブインデックスを設定する
	 *
	 * @param reqType
	 * @param editList
	 * @param editDtList
	 */
	private void setTabindexes(String reqType, EdiJuchuMainteList editList, EdiJuchuMainteDtList editDtList) {

		String idx = TAB_INDEX_HEAD;

		switch (reqType) {
		case REQ_TYPE_EDIT: // 修正
			editList.setEditableElementsParRecord(ELEMENTS_ON_EDIT);
			editDtList.setEditableElementsParRecord(ELEMENTS_ON_EDIT_DT);
			idx = Integer.toString(Integer.parseInt(editList.getConfirmBtnTabIndex())
					+ Integer.parseInt(editDtList.getConfirmBtnTabIndex()) - 1);
			break;

		default: // 抹消
			break;
		}

		editForm.setTabIndexConfirmAndBack(idx);

	}

	/**
	 * EditFormを設定
	 */
	private void setEditForm() {
		editForm = new EdiJuchuMainte_EditForm();

		if 	(getSession(KEY_SS_SEARCHEDLIST) != null) {
			editForm.setEditable(IS_EDITABLE_TRUE);
		}
		editForm.setMode(MODE_SEARCH);
		editForm.setPreMode(MODE_EMPTY);

		setRequest(KEY_SS_EDITFORM, editForm);
	}
}
