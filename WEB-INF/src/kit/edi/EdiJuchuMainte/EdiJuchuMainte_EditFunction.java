package kit.edi.EdiJuchuMainte;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.edi.EdiJuchuMainte.IEdiJuchuMainte.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComHaitaService;
import fb.com.ComKitUtil;
import fb.inf.KitFunction;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;
/**
 * 明細部からのリクエスト処理の実装クラスです
 */
public class EdiJuchuMainte_EditFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = -4101766358190464761L;

	/** クラス名. */
	private static String className__ = EdiJuchuMainte_EditFunction.class.getName();

	/**
	 *  トランザクション制御を行うかどうかを示すフラグ. trueの場合トランザクション制御を行う.
	 * falseの場合トランザクション制御を行なわない.
	 *
	 * @see #isTransactionalFunction ()
	 */
	private static final boolean isTransactionalFunction = true;

	/** 編集フォーム */
	private EdiJuchuMainte_EditForm editForm = null;
	/** 編集リスト */
	private EdiJuchuMainteList editList = null; // ヘッダー部
	private EdiJuchuMainteDtList editDtList = null; // ディテール部
	/**
	 * Transaction制御フラグを返す.
	 *
	 * @return true(制御を行う) or false(制御を行わない)
	 */
	@Override
	protected boolean isTransactionalFunction() {

		return isTransactionalFunction;
	}

	/**
	 * クラス名を取得.
	 *
	 * @return クラス名
	 */
	@Override
	protected String getFunctionName() {

		return className__;
	}

	/**
	 * コンストラクタです.
	 *
	 * @param mapping paramName
	 * @param form paramName
	 * @param request paramName
	 * @param response paramName
	 */
	public EdiJuchuMainte_EditFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		super(mapping, form, request, response);
	}

	// -----------------------------------------
	// search & set page data
	// -----------------------------------------
	@Override
	public String execute_() throws KitException {

		// オブジェクトを取得
		editForm = (EdiJuchuMainte_EditForm) form_;

		// 「セット(明細選択/select)」の場合
		if (REQ_TYPE_SELECT.equals(editForm.getReqType())) {
			select();

		// 遷移リクエスト情報が「確認(confirm)」の場合
		} else if (REQ_TYPE_CONFIRM.equals(editForm.getReqType())) {
			confirm();

		// 遷移リクエスト情報が「確定(commit)」の場合
		} else if (REQ_TYPE_COMMIT.equals(editForm.getReqType())) {
			commit();

		// 遷移リクエスト情報が「戻る(back)」の場合
		} else if (REQ_TYPE_BACK.equals(editForm.getReqType())) {
			back();

		// 改ページ処理
		} else {
			move();
		}

		//
		setRequest(KEY_SS_EDITFORM, editForm);

		// 宛先取得
		return ComKitUtil.getForwardForMaster(editForm);
	}

	/**
	 * 「セット」ボタン押下時の処理(検索フェーズ)
	 */
	private void select() throws KitException {

		// 選択リストを初期化する
		removeSession(KEY_SS_INITLIST);
		removeSession(KEY_SS_INITLIST_DT);
		removeSession(KEY_SS_EDITLIST);
		removeSession(KEY_SS_EDITLIST_DT);

		// ViewJSPで検索部のボタンを非活性にするために フォームの情報を取得
		EdiJuchuMainte_SearchForm searchForm = (EdiJuchuMainte_SearchForm) getSession(KEY_SS_SEARCHFORM);

		// セッションから、画面で入力した登録情報を取得
		EdiJuchuMainteList searchedList = (EdiJuchuMainteList) getSession(KEY_SS_SEARCHEDLIST);

		EdiJuchuMainte_CheckService checkServ = new EdiJuchuMainte_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 詳細表示させるときのチェック
		if (!checkServ.validateSelect(editForm, searchedList)) {

			// 一覧を表示させる
			editForm.setMode(MODE_SEARCH);
			editForm.setPreMode(MODE_EMPTY);
			editForm.setEditable(IS_EDITABLE_TRUE);
			return;
		}

		// 選択結果を取得
		// ヘッダー部
		EdiJuchuMainteRecord selectedRec = checkServ.getRecSelected();
		EdiJuchuMainteList editList = new EdiJuchuMainteList(selectedRec);

		// 検索実行
		// ディテール部
		EdiJuchuMainte_SearchService searchServ = new EdiJuchuMainte_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		EdiJuchuMainteDtList editDtList = searchServ.getEdiJyuhacyuDtList(editForm);

		// ==================================
		// ヘッダー部の表示情報を設定
		// ==================================
		EdiJuchuMainteRecord hdRec = (EdiJuchuMainteRecord) editList.getFirstRecord();
		convertToDispHd(hdRec, editDtList);
		// ==================================
		// ディテール部の表示情報を設定
		// ==================================
		convertToDispDt(hdRec, editDtList);

		// ヘッダー部
		editList.setOffset(0);
		editList.setRecordsParPage(editForm.getMaxSu());
		// ディテール部
		editDtList.setOffset(0);
		editDtList.setRecordsParPage(editForm.getMaxSuDt());

		// ViewJSPで検索部のボタンを非活性にするためにチェックを実行
		checkServ.validateSelectBtnAvailFlg(editList, searchForm);

		// チェックOK
		// ⇒ 一覧から選択できた
		editForm.setMode(MODE_SELECTED);
		editForm.setPreMode(MODE_EMPTY);
		editForm.setEditable(IS_EDITABLE_TRUE);
		editForm.setSelectedRowId(null);

		// 戻るボタンのタブインデックスを設定する
		editForm.setTabIndexBack(TAB_INDEX_HEAD);
		// 要注意ポイント
		// １明細編集なので<kit:iterate>で必要となる
		// ※ jspのhiddenにも記載すること！
		editForm.setMaxLineSu(INT_EDIT_ONE_RECORD.toString());

		// セッションへ格納
		setSession(KEY_SS_EDITLIST, editList); // ヘッダー部
		setSession(KEY_SS_EDITLIST_DT, editDtList); // ディテール部
		setSession(KEY_SS_EDITFORM, editForm); // 編集フォーム

	}

	/**
	 * 先頭ページへ戻るときの定型処理
	 */
	private void goStartPage() {
		// セッション情報をクリア
		removeSession(KEY_SS_INITLIST);
		removeSession(KEY_SS_INITLIST_DT);
		removeSession(KEY_SS_EDITLIST);
		removeSession(KEY_SS_EDITLIST_DT);
		removeSession(KEY_SS_SEARCHEDLIST);

		editForm = new EdiJuchuMainte_EditForm();

		// セッションから、検索部ﾌｫｰﾑを取得する
		EdiJuchuMainte_SearchForm searchForm = (EdiJuchuMainte_SearchForm) getSession(KEY_SS_SEARCHFORM);
		searchForm.setCsvBtnFlg(BUTTON_DISABLED_FALSE); // ファイル出力ボタンを活性化

	}

	/**
	 * 改ページ処理(検索フェーズ)
	 *
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean move() throws KitException {

		// セッションから、呼出リストを取得
		EdiJuchuMainteList searchedList = (EdiJuchuMainteList) getSession(KEY_SS_SEARCHEDLIST);

		/*
		 * 選択行にフラグを立てる
		 *  - セット欄に値が入っているとき(優先)
		 *  - クリックで行を選択しているとき
		 */
		for (;;) {
			// コードでフラグをセット
			if (!PbsUtil.isEmpty(editForm.getEdiJyuhacyuId())) {
				searchedList.setModifiedByCd(editForm.getEdiJyuhacyuId());
				break;
			}

			// IDでフラグをセット
			if (!PbsUtil.isEmpty(editForm.getSelectedRowId())) {
				searchedList.setModifiedById(editForm.getSelectedRowId());
				break;
			}

			break;
		}

		// 別ページへ遷移するのでIDを空にする
		editForm.setSelectedRowId(null);

		// ページ移動
		searchedList.setPageRequest(editForm.getReqType());

		return true;
	}

	/**
	 * 「戻る」ボタン押下時の処理(Any フェーズ)
	 *
	 */
	private void back() {

		// 編集リストを取得する
		editList = (EdiJuchuMainteList) getSession(KEY_SS_EDITLIST); // ヘッダー部
		//editDtList = (EdiJuchuMainteDtList) getSession(KEY_SS_EDITLIST_DT); // ディテール部

		// セッションから、検索部ﾌｫｰﾑを取得する
		//EdiJuchuMainte_SearchForm searchForm = (EdiJuchuMainte_SearchForm) getSession(KEY_SS_SEARCHFORM);

		// 排他解除
		releaseHaita();

		/* 表示（閲覧）、修正 → 一覧 */
		if (PbsUtil.isIncluded(editForm.getMode(), MODE_SELECTED, MODE_EDIT)) {

			// セッションから、呼出し結果を取得する
			EdiJuchuMainteList searchedList = (EdiJuchuMainteList) getSession(KEY_SS_SEARCHEDLIST);

			/* ==============================
			 * 要注意ポイント(一覧へ戻るとき)
			 * setPageList()をsearchedListでは回していないので、
			 * 戻るときに、getSelectedRowId()を使い、元の選択
			 * 位置を取り戻す。
			 * → これをしないと戻ったまま、再セットされるときに
			 * 　不具合が発生する
			 * 併せて、基盤内でフラグを設定する
			 * → 基盤内のフラグ(aModifiedList)が空のままだと
			 * 　setPage2FirstModified()が機能しない。
			 ============================== */

			// 選択したデータNoを設定する
			EdiJuchuMainteRecord rec = (EdiJuchuMainteRecord) editList.getFirstRecord();
			editForm.setEdiJyuhacyuId(rec.getEdiJyuhacyuId());

			// 元の位置にフラグを設定する
			editForm.setSelectedRowId(searchedList.getSelectedRowId());

			// 選択レコードを含むページを表示させる
			searchedList.setPage2FirstModified();

			// 選択済みリストを開放する
			removeSession(KEY_SS_EDITLIST);
			removeSession(KEY_SS_EDITLIST_DT);
			// 初期リストを開放する
			removeSession(KEY_SS_INITLIST);
			removeSession(KEY_SS_INITLIST_DT);

			//searchForm.setCsvBtnFlg(BUTTON_AVAILABLE_TRUE); // ファイル出力ボタンを活性化

		}

		// 各種モードを引き継ぐ
		ComKitUtil.setModeOnBackForMaster(editForm);

	}

	/**
	 * 「確認」ボタン押下時の処理(操作フェーズ)
	 *
	 * @throws KitException
	 */
	private void confirm() throws KitException {

		// 編集リストを取得する
		editList = (EdiJuchuMainteList) getSession(KEY_SS_EDITLIST); // ヘッダー部
		editDtList = (EdiJuchuMainteDtList) getSession(KEY_SS_EDITLIST_DT); // ディテール部

		// 修正モード
		if (MODE_EDIT.equals(editForm.getMode())) {
			confirmUpdate();
		}
	}

	/**
	 * 「確定」ボタン押下時の処理(確認フェーズ)
	 *
	 */
	private void commit() {

		// 編集リストを取得する
		editList = (EdiJuchuMainteList) getSession(KEY_SS_EDITLIST); // ヘッダー部
		editDtList = (EdiJuchuMainteDtList) getSession(KEY_SS_EDITLIST_DT); // ディテール部

		// 遷移リクエスト情報が「修正(edit)」の場合
		if (MODE_EDIT.equals(editForm.getPreMode())) {
			commitUpdate();

		}
	}

	/**
	 * 修正時「確認」ボタン押下時の処理(操作フェーズ)
	 *
	 * @throws KitException
	 */
	private void confirmUpdate() throws KitException {

		// 初期リストを取得する
		EdiJuchuMainteList initList = (EdiJuchuMainteList) getSession(KEY_SS_INITLIST); // ヘッダー部
		EdiJuchuMainteDtList initDtList = (EdiJuchuMainteDtList) getSession(KEY_SS_INITLIST_DT); // ディテール部

		// =============================================
		// ・チェックサービス呼び出しの呼び出し）
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ・セッションにリスト入力情報をセット
		// =============================================
		EdiJuchuMainte_CheckService checkServ = new EdiJuchuMainte_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// =============================================
		// editFormの値をeditListにセットする。
		// （登録情報をコピーする。）
		// =============================================
		// editLitsの修正用編集を行う
		// =============================================
		// ヘッダー部
		// =============================================
		editList.setPageList(editForm.getEdiJuchuMainteList());
		EdiJuchuMainteRecord hdRec = (EdiJuchuMainteRecord) editList.getFirstRecord();
		// =============================================
		// ディテール部
		// =============================================
		this.reCreateList(checkServ, hdRec);

		// 排他を失っていたら、前画面へ戻る
		// チェックを実行する
		if (!hasHaita() || !checkServ.validateConfirmAsUpdate(editList, editDtList, initList, initDtList)) {

			// エラーが発生したページへ遷移
			editList.setPage2FirstError();

			// リクエストにEditFormをセット(mode:edit, preMode:null, 編集フォーム設定可)
			editForm.setMode(MODE_EDIT);
			editForm.setPreMode(MODE_EMPTY);
			editForm.setEditable(IS_EDITABLE_TRUE);
			// タブインデックスを設定する
			setTabindexConfirmAndBack(REQ_TYPE_EDIT, editList, editDtList);
			return ;
		}

		// 確認モード
		editForm.setMode(MODE_CONFIRM);
		editForm.setPreMode(MODE_EDIT);
		editForm.setEditable(IS_EDITABLE_TRUE);

		// 確定、戻るボタンのタブインデックスを設定する
		editForm.setTabIndexCommitAndBack();

		return ;
	}

	/**
	 * 修正時の確定処理(確定フェーズ)
	 */
	private void commitUpdate() {

		// ========================================================================
		// ・更新サービス呼び出し
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ========================================================================
		EdiJuchuMainte_UpdateService updateServ = new EdiJuchuMainte_UpdateService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 排他を失っていたら、前画面へ戻る
		// UpDate処理をしてエラーだったら、前画面へ戻る
		if (!hasHaita() || !updateServ.update((EdiJuchuMainteRecord)editList.getFirstRecord(), editDtList)) {

			// 各種モードを設定する
			editForm.setMode(MODE_EDIT);
			editForm.setPreMode(MODE_EMPTY);
			editForm.setEditable(IS_EDITABLE_TRUE);
			// タブインデックスを設定する
			setTabindexConfirmAndBack(REQ_TYPE_EDIT, editList, editDtList);
			return;
		}

		// 成功 ⇒ 初期画面へ戻る
		goStartPage();

		return;
	}

	/**
	 * 確認、確定時に排他(編集優先権)を持っているかどうか判定する
	 * @return true / false
	 */
	private boolean hasHaita(){

		// 排他サービスを生成する
		ComHaitaService haitaServ = new ComHaitaService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 判定する
		if(!haitaServ.checkHaita(editList)){
			return false;
		}

		return true;
	}

	/**
	 * 戻る、取消時に排他を開放する
	 */
	private void releaseHaita(){

		// 排他が必要か判定する
		if (!ComKitUtil.mustHaitaReliease(editForm)) {
			return;
		}

		// 排他サービスを生成
		ComHaitaService haitaServ = new ComHaitaService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// モードをセットする
		haitaServ.setMode(editForm.getMode());
		// 排他解除する
		haitaServ.unLock(editList);

	}


	/**
	 * リストを入力行だけに変換（前詰め）後にmaxSuまで空レコードで埋める
	 *
	 * @param checkServ
	 * @param hdRec
	 * @throws KitException
	 */
	private void reCreateList(EdiJuchuMainte_CheckService checkServ, EdiJuchuMainteRecord hdRec) throws KitException {

		// リストを入力行だけに変換
		boolean currentAddMode = editDtList.isAddMode();
		editDtList.setAddMode();
		editDtList.setPageList(editForm.getEdiJuchuMainteDtList());

		// モードを元に戻す
		editDtList.setAddMode(currentAddMode);

		// ==================================
		// ディテール部の付加情報を設定
		// ==================================
		for (PbsRecord tmp : editDtList) {
			EdiJuchuMainteDtRecord dtRec = (EdiJuchuMainteDtRecord) tmp;
			dtRec.setAddMode(currentAddMode);
		}

		// maxSuまで空レコードで埋める
		//editDtList.initListToMax(editDtList.size(), editForm.getMaxSuDt());
	}

	/**
	 * 確認、戻るボタンにタブインデックスを設定する
	 *
	 * @param reqType
	 * @param editList
	 * @param editDtList
	 */
	private void setTabindexConfirmAndBack(String reqType, EdiJuchuMainteList editList, EdiJuchuMainteDtList editDtList) {

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

		// 確認、戻るボタンのタブインデックスを設定する
		editForm.setTabIndexConfirmAndBack(idx);

	}

	/**
	 * DBから取得したヘッダー部の情報を表示用のヘッダー部に変換
	 * @param hdRec
	 */
	private void convertToDispHd(EdiJuchuMainteRecord hdRec, EdiJuchuMainteDtList editDtList) {

		// ﾃﾞｰﾀ作成日
		String sakseibi = hdRec.getFhDataSakuseiDt();
		if (!PbsUtil.isEmpty(sakseibi) && sakseibi.length() == 6) {
			hdRec.setFhDataSakuseiDt("20" + sakseibi);
		}

		// ﾃﾞｰﾀ送信日
		String sosinbi = hdRec.getFhDataSyoriDt();
		if (!PbsUtil.isEmpty(sosinbi) && sosinbi.length() == 6) {
			hdRec.setFhDataSyoriDt("20" + sosinbi);
		}

		// 納品/引取日
		String nouyuDt = hdRec.getDhNonyuDt();
		if (!PbsUtil.isEmpty(nouyuDt) && nouyuDt.length() == 6) {
			hdRec.setDhNonyuDt("20" + nouyuDt);
		}

		// ディテール部に1件もない場合
		if (editDtList.size() == 0) {
			hdRec.setDataUmuKbn(EDI_DATA_ARINASHI_KB_NASI);
		} else {
			hdRec.setDataUmuKbn(EDI_DATA_ARINASHI_KB_ARI);
		}

		// エラー区分2桁毎にスペースをセット
		String errorKbn = formatErrKbn(hdRec.getErrorKbn());
		hdRec.setErrorKbn(errorKbn);
	}

	/**
	 * DBから取得したディテール部のリスト情報を表示用のディテール部に変換
	 * @param hdRec
	 */
	public void convertToDispDt(EdiJuchuMainteRecord hdRec, EdiJuchuMainteDtList editDtList) {

		// チェックサービス
		EdiJuchuMainte_CheckService checkServ = new EdiJuchuMainte_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// 許容日に関するディテール部の情報を更新
		checkServ.updKyobiLifeDt(hdRec, editDtList);

		// エラー区分に関するディテール部の情報を更新
		for (int i = 0; i < editDtList.size(); i++) {

			EdiJuchuMainteDtRecord rec = (EdiJuchuMainteDtRecord) editDtList.get(i);

			// エラー区分2桁毎にスペースをセット
			rec.setErrorKbn(formatErrKbn(rec.getErrorKbn()));
		}
	}

	/**
	 * フォーマットしたエラー区分文字列を返す
	 * @param errorKbn
	 * @return
	 */
	private String formatErrKbn(String errorKbn) {

		StringBuffer formatErrKbn = new StringBuffer();

		if (PbsUtil.isEmpty(errorKbn)) {
			return errorKbn;
		}

		// エラー区分を2桁毎にスペースをセット
		for (int i = 0 ; i < errorKbn.length() / 2 ; i++) {

			formatErrKbn.append(errorKbn.substring(i * 2, i * 2 + 2));
			formatErrKbn.append(CHAR_SPACE);
		}

		return formatErrKbn.toString();
	}
}
