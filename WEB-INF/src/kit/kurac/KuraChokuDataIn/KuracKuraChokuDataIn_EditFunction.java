package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.kurac.KuraChokuDataIn.IKuracKuraChokuDataIn.*;

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
public class KuracKuraChokuDataIn_EditFunction extends KitFunction {

	/**
	 *
	 */

	/** シリアルID */
	private static final long serialVersionUID = 3794791643573976457L;

	/** クラス名. */
	private static String className__ = KuracKuraChokuDataIn_EditFunction.class.getName();

	/**
	 *  トランザクション制御を行うかどうかを示すフラグ. trueの場合トランザクション制御を行う.
	 * falseの場合トランザクション制御を行なわない.
	 *
	 * @see #isTransactionalFunction ()
	 */
	private static final boolean isTransactionalFunction = true;

	/** 編集フォーム */
	private KuracKuraChokuDataIn_EditForm editForm = null;
	/** 編集リスト */
	private KuracKuraChokuDataInList editList = null; // ヘッダー部
	private KuracKuraChokuDataInDtList editDtList = null; // ディテール部

	/**
	 * コンストラクタです.
	 *
	 * @param mapping paramName
	 * @param form paramName
	 * @param request paramName
	 * @param response paramName
	 */
	public KuracKuraChokuDataIn_EditFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		super(mapping, form, request, response);
	}

	// -----------------------------------------
	// search & set page data
	// -----------------------------------------
	@Override
	public String execute_() throws KitException {

		// オブジェクトを取得
		editForm = (KuracKuraChokuDataIn_EditForm) form_;

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

		// 遷移リクエストが「取消(cancel)」の場合
		} else if (REQ_TYPE_CANCEL.equals(editForm.getReqType())) {
			cancel();

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
	 * 「戻る」ボタン押下時の処理(Any フェーズ)
	 *
	 */
	private void back() {

		// 編集リストを取得する
		editList = (KuracKuraChokuDataInList) getSession(KEY_SS_EDITLIST); // ヘッダー部
		editDtList = (KuracKuraChokuDataInDtList) getSession(KEY_SS_EDITLIST_DT); // ディテール部
		// maxSuまで空レコードで埋める
		editDtList.initListToMax(editDtList.size(), editForm.getMaxSuDt());

		// 排他解除
		releaseHaita();

		/* 表示（閲覧）、修正、参照追加 → 一覧 */
		if (PbsUtil.isIncluded(editForm.getMode(), MODE_SELECTED, MODE_EDIT, MODE_REFERENCE, MODE_REFPLUS , MODE_REFMINUS)) {

			// セッションから、呼出し結果を取得する
			KuracKuraChokuDataInList searchedList = (KuracKuraChokuDataInList) getSession(KEY_SS_SEARCHEDLIST);

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

			// 選択した蔵直ﾃﾞｰﾀ連番を設定する
			KuracKuraChokuDataInRecord rec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();
			editForm.setKuradataNo(rec.getKuradataNo());

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

		/* 抹消(確認) → 表示 */
		} else if (PbsUtil.isIncluded(editForm.getMode(), MODE_DELETE)) {

			// 初期リストを呼出し
			KuracKuraChokuDataInList initList = (KuracKuraChokuDataInList) getSession(KEY_SS_INITLIST); // ヘッダー部
			KuracKuraChokuDataInDtList initDtList = (KuracKuraChokuDataInDtList) getSession(KEY_SS_INITLIST_DT); // ディテール部

			// 編集リストを再生する
			editList = (KuracKuraChokuDataInList) initList.copy(); // ヘッダー部
			editDtList = (KuracKuraChokuDataInDtList) initDtList.copy(); // ディテール部

			//
			// ヘッダー部
			editList.setOffset(0);
			editList.setRecordsParPage(editForm.getMaxSu());
			setSession(KEY_SS_EDITLIST, editList);
			// ディテール部
			editDtList.setOffset(0);
			editDtList.setRecordsParPage(editForm.getMaxSuDt());
			setSession(KEY_SS_EDITLIST_DT, editDtList);

		/* 新規 → 初期 */
		} else if (PbsUtil.isEqual(editForm.getMode(), MODE_ADD)) {

			// リスト系セッションを開放する
			removeSession(KEY_SS_SEARCHEDLIST);
			// 選択済みリストを開放する
			removeSession(KEY_SS_EDITLIST);
			removeSession(KEY_SS_EDITLIST_DT);
			// 初期リストを開放する
			removeSession(KEY_SS_INITLIST);
			removeSession(KEY_SS_INITLIST_DT);

		}

		// 各種モードを引き継ぐ
		ComKitUtil.setModeOnBackForMaster(editForm);

	}

	/**
	 * 「取消」ボタン押下時の処理(操作フェーズ)
	 */
	private void cancel() {

		// 初期リストを呼出し
		KuracKuraChokuDataInList initList = (KuracKuraChokuDataInList) getSession(KEY_SS_INITLIST); // ヘッダー部
		KuracKuraChokuDataInDtList initDtList = (KuracKuraChokuDataInDtList) getSession(KEY_SS_INITLIST_DT); // ディテール部

		// 編集リストを再生する
		editList = (KuracKuraChokuDataInList) initList.copy(); // ヘッダー部
		editDtList = (KuracKuraChokuDataInDtList) initDtList.copy(); // ディテール部

		//
		// ヘッダー部
		editList.setOffset(0);
		editList.setRecordsParPage(editForm.getMaxSu());
		setSession(KEY_SS_EDITLIST, editList);
		// ディテール部
		editDtList.setOffset(0);
		editDtList.setRecordsParPage(editForm.getMaxSuDt());
		setSession(KEY_SS_EDITLIST_DT, editDtList);

		// 排他解除
		releaseHaita();

		// モードを設定する
		ComKitUtil.setModeOnCancelForMaster(editForm);

	}

	/**
	 * 「確定」ボタン押下時の処理(確認フェーズ)
	 *
	 */
	private void commit() {

		// 編集リストを取得する
		editList = (KuracKuraChokuDataInList) getSession(KEY_SS_EDITLIST); // ヘッダー部
		editDtList = (KuracKuraChokuDataInDtList) getSession(KEY_SS_EDITLIST_DT); // ディテール部

		// 遷移リクエスト情報が「新規(add)」の場合
		if (MODE_ADD.equals(editForm.getPreMode())) {
			commitAdd();

		// 遷移リクエスト情報が「修正(edit)」の場合
		} else if (MODE_EDIT.equals(editForm.getPreMode())) {
			commitUpdate();

		// 遷移リクエスト情報が「参照追加(reference)」の場合
		} else if (MODE_REFERENCE.equals(editForm.getPreMode())) {
			commitReference();

		// 遷移リクエスト情報が「訂正(+)」の場合
		} else if (PbsUtil.isIncluded(MODE_REFPLUS, editForm.getPreMode())) {
			commitRefPlus();

		// 遷移リクエスト情報が「訂正(-)」の場合
		} else if (PbsUtil.isIncluded(MODE_REFMINUS, editForm.getMode(), editForm.getPreMode())) {
			commitRefMinus();

		// 遷移リクエスト情報が「抹消(delete)」の場合
		} else if (PbsUtil.isIncluded(MODE_DELETE, editForm.getMode(), editForm.getPreMode())) {
			commitDelete();

		}
	}

	/**
	 * 新規時の確定処理(確定フェーズ)
	 */
	private void commitAdd() {

		// ========================================================================
		// （チェックサービス呼び出し結果が正常の場合）
		// ・更新サービス呼び出し
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ========================================================================
		KuracKuraChokuDataIn_UpdateService updateServ = new KuracKuraChokuDataIn_UpdateService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 登録用のヘッダ情報に変換
		convertToInsHdInfo(editList);
		// 登録用のディテール情報に変換
		convertToInsDtInfo((KuracKuraChokuDataInRecord)editList.getFirstRecord(), editDtList);

		if (!updateServ.insert(editList, editDtList)) {

			// 各種モードを設定
			editForm.setMode(MODE_ADD);
			editForm.setPreMode(MODE_EMPTY);
			editForm.setEditable(IS_EDITABLE_TRUE);
			// タブインデックスを設定する
			setTabindexConfirmAndBack(REQ_TYPE_ADD, editList, editDtList);
			return;
		}

		// 成功 ⇒ 初期画面へ戻る
		goStartPage();

		return;
	}

	/**
	 * 削除時の確定処理(確定フェーズ)
	 */
	private void commitDelete() {

		// ========================================================================
		// ・更新サービス呼び出し
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ========================================================================
		KuracKuraChokuDataIn_UpdateService updateServ = new KuracKuraChokuDataIn_UpdateService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 排他を失っていたら、前画面へ戻る
		// UpDate処理をしてエラーだったら、前画面へ戻る
		if (!hasHaita() || !updateServ.erasure(editList, editDtList)) {

			// 各種モードを設定する
			editForm.setMode(MODE_DELETE);
			editForm.setPreMode(MODE_EMPTY);
			editForm.setEditable(IS_EDITABLE_TRUE);
			// タブインデックスを設定する
			setTabindexConfirmAndBack(REQ_TYPE_DELETE, editList, editDtList);

			return;
		}

		// 成功 ⇒ 初期画面へ戻る
		goStartPage();

		return;
	}


	/**
	 * 参照追加時の確定処理(確定フェーズ)
	 */
	private void commitReference() {

		// ========================================================================
		// （チェックサービス呼び出し結果が正常の場合）
		// ・更新サービス呼び出し
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ========================================================================
		KuracKuraChokuDataIn_UpdateService updateServ = new KuracKuraChokuDataIn_UpdateService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 登録用のヘッダ情報に変換
		convertToInsHdInfo(editList);
		// 登録用のディテール情報に変換
		convertToInsDtInfo((KuracKuraChokuDataInRecord)editList.getFirstRecord(), editDtList);


		if (!updateServ.insert(editList, editDtList)) {

			// 各種モードを設定
			editForm.setMode(MODE_REFERENCE);
			editForm.setPreMode(MODE_EMPTY);
			editForm.setEditable(IS_EDITABLE_TRUE);
			// タブインデックスを設定する
			setTabindexConfirmAndBack(REQ_TYPE_REFERENCE, editList, editDtList);
			return;
		}

		// 成功 ⇒ 初期画面へ戻る
		goStartPage();

		return;
	}

	/**
	 * 修正時の確定処理(確定フェーズ)
	 */
	private void commitUpdate() {

		// 初期リストを取得する
		KuracKuraChokuDataInDtList initDtList = (KuracKuraChokuDataInDtList) getSession(KEY_SS_INITLIST_DT); // ディテール部

		// ========================================================================
		// ・更新サービス呼び出し
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ========================================================================
		KuracKuraChokuDataIn_UpdateService updateServ = new KuracKuraChokuDataIn_UpdateService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 排他を失っていたら、前画面へ戻る
		// UpDate処理をしてエラーだったら、前画面へ戻る
		if (!hasHaita() || !updateServ.update(editList, editDtList, initDtList)) {

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
	 * 訂正(+)時の確定処理(確定フェーズ)
	 */
	private void commitRefPlus() {

		// ========================================================================
		// （チェックサービス呼び出し結果が正常の場合）
		// ・更新サービス呼び出し
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ========================================================================
		KuracKuraChokuDataIn_UpdateService updateServ = new KuracKuraChokuDataIn_UpdateService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 登録用のヘッダ情報に変換
		convertToInsHdInfo(editList);
		// 登録用のディテール情報に変換
		convertToInsDtInfo((KuracKuraChokuDataInRecord)editList.getFirstRecord(), editDtList);


		if (!updateServ.insert(editList, editDtList)) {

			// 各種モードを設定
			editForm.setMode(MODE_REFPLUS);
			editForm.setPreMode(MODE_EMPTY);
			editForm.setEditable(IS_EDITABLE_TRUE);
			// タブインデックスを設定する
			setTabindexConfirmAndBack(REQ_TYPE_REFPLUS, editList, editDtList);
			return;
		}

		// 成功 ⇒ 初期画面へ戻る
		goStartPage();

		return;
	}

	/**
	 * 訂正(-)時の確定処理(確定フェーズ)
	 */
	private void commitRefMinus() {

		// ========================================================================
		// （チェックサービス呼び出し結果が正常の場合）
		// ・更新サービス呼び出し
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ========================================================================
		KuracKuraChokuDataIn_UpdateService updateServ = new KuracKuraChokuDataIn_UpdateService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 登録用のヘッダ情報に変換
		convertToInsHdInfo(editList);
		// 登録用のディテール情報に変換
		convertToInsDtInfo((KuracKuraChokuDataInRecord)editList.getFirstRecord(), editDtList);


		if (!updateServ.insert(editList, editDtList)) {

			// 各種モードを設定
			editForm.setMode(MODE_REFMINUS);
			editForm.setPreMode(MODE_EMPTY);
			editForm.setEditable(IS_EDITABLE_TRUE);
			// タブインデックスを設定する
			setTabindexConfirmAndBack(REQ_TYPE_REFMINUS, editList, editDtList);
			return;
		}

		// 成功 ⇒ 初期画面へ戻る
		goStartPage();

		return;
	}

	/**
	 * 「確認」ボタン押下時の処理(操作フェーズ)
	 *
	 * @throws KitException
	 */
	private void confirm() throws KitException {

		// 編集リストを取得する
		editList = (KuracKuraChokuDataInList) getSession(KEY_SS_EDITLIST); // ヘッダー部
		editDtList = (KuracKuraChokuDataInDtList) getSession(KEY_SS_EDITLIST_DT); // ディテール部

		// 新規モード
		if (MODE_ADD.equals(editForm.getMode())) {
			confirmAdd();

		// 修正モード
		} else if (MODE_EDIT.equals(editForm.getMode())) {
			confirmUpdate();

		// 参照追加モード
		} else if (MODE_REFERENCE.equals(editForm.getMode())) {
			confirmReference();

		}
		// 訂正（＋）モード
		if (MODE_REFPLUS.equals(editForm.getMode())) {
			confirmRefPlus();
		// 訂正（－）モード
		} else if (MODE_REFMINUS.equals(editForm.getMode())) {
			confirmRefMinus();
		}
		// 抹消モードには確認ボタンが表示される画面がない
		// 復活モードには確認ボタンが表示される画面がない

	}

	/**
	 * 新規時「確認」ボタン押下時の処理(操作フェーズ)
	 *
	 * @throws KitException
	 */
	private void confirmAdd() throws KitException {

		// =============================================
		// ・チェックサービス呼び出しの呼び出し
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ・セッションにリスト入力情報をセット
		// =============================================
		KuracKuraChokuDataIn_CheckService checkServ = new KuracKuraChokuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// =============================================
		// editFormの値をeditListにセットする。
		// （登録情報をコピーする。）
		// =============================================
		// editLitsの修正用編集を行う
		// =============================================
		// ヘッダー部
		// =============================================
		editList.setPageList(editForm.getKuracKuraChokuDataInList());
		KuracKuraChokuDataInRecord hdRec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();
		// =============================================
		// ディテール部
		// =============================================
		this.reCreateList(checkServ, hdRec);

		// チェックを実行する
		if (!checkServ.validateConfirmAsInsert(editForm, editList, editDtList)) {

			// リクエストにEditFormをセット(mode:add, preMode:null, 編集フォーム設定可)
			editForm.setMode(MODE_ADD);
			editForm.setEditable(IS_EDITABLE_TRUE);
			// タブインデックスを設定する
			setTabindexConfirmAndBack(REQ_TYPE_ADD, editList, editDtList);
			return ;
		}

		// 確認画面へ遷移させる
		editForm.setMode(MODE_CONFIRM);
		editForm.setPreMode(MODE_ADD);
		editForm.setEditable(IS_EDITABLE_TRUE);
		editForm.setTabIndexCommitAndBack();

		return ;
	}

	/**
	 * 参照追加時のチェックを実行する(操作フェーズ)
	 *
	 * @throws KitException
	 */
	private void confirmReference() throws KitException {

		// 初期リストを呼出し
		KuracKuraChokuDataInList initList = (KuracKuraChokuDataInList) getSession(KEY_SS_INITLIST); // ヘッダー部
		KuracKuraChokuDataInDtList initDtList = (KuracKuraChokuDataInDtList) getSession(KEY_SS_INITLIST_DT); // ディテール部

		// =============================================
		// ・チェックサービス呼び出しの呼び出し
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ・セッションにリスト入力情報をセット
		// =============================================
		KuracKuraChokuDataIn_CheckService checkServ = new KuracKuraChokuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// =============================================
		// editFormの値をeditListにセットする。
		// （登録情報をコピーする。）
		// =============================================
		// editLitsの修正用編集を行う
		// =============================================
		// ヘッダー部
		// =============================================
		editList.setPageList(editForm.getKuracKuraChokuDataInList());
		KuracKuraChokuDataInRecord hdRec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();
		// =============================================
		// ディテール部
		// =============================================
		this.reCreateList(checkServ, hdRec);

		// チェックを実行する
		if (!checkServ.validateConfirmAsReference(editForm, initList, initDtList, editList, editDtList)) {

			// リクエストにEditFormをセット(mode:add, preMode:null, 編集フォーム設定可)
			editForm.setMode(MODE_REFERENCE);
			editForm.setEditable(IS_EDITABLE_TRUE);
			// タブインデックスを設定する
			setTabindexConfirmAndBack(REQ_TYPE_REFERENCE, editList, editDtList);
			return ;
		}

		// 確認画面へ遷移させる
		editForm.setMode(MODE_CONFIRM);
		editForm.setPreMode(MODE_REFERENCE);
		editForm.setEditable(IS_EDITABLE_TRUE);
		editForm.setTabIndexCommitAndBack();

		return ;
	}

	/**
	 * 修正時「確認」ボタン押下時の処理(操作フェーズ)
	 *
	 * @throws KitException
	 */
	private void confirmUpdate() throws KitException {

		// 初期リストを取得する
		KuracKuraChokuDataInList initList = (KuracKuraChokuDataInList) getSession(KEY_SS_INITLIST); // ヘッダー部
		KuracKuraChokuDataInDtList initDtList = (KuracKuraChokuDataInDtList) getSession(KEY_SS_INITLIST_DT); // ディテール部

		// =============================================
		// ・チェックサービス呼び出しの呼び出し）
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ・セッションにリスト入力情報をセット
		// =============================================
		KuracKuraChokuDataIn_CheckService checkServ = new KuracKuraChokuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// =============================================
		// editFormの値をeditListにセットする。
		// （登録情報をコピーする。）
		// =============================================
		// editLitsの修正用編集を行う
		// =============================================
		// ヘッダー部
		// =============================================
		editList.setPageList(editForm.getKuracKuraChokuDataInList());
		KuracKuraChokuDataInRecord hdRec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();
		// =============================================
		// ディテール部
		// =============================================
		this.reCreateList(checkServ, hdRec);

		// 排他を失っていたら、前画面へ戻る
		// チェックを実行する
		if (!hasHaita() || !checkServ.validateConfirmAsUpdate(editForm, initList, initDtList, editList, editDtList)) {

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
	 * 訂正(+)時のチェックを実行する(操作フェーズ)
	 *
	 * @throws KitException
	 */
	private void confirmRefPlus() throws KitException {

		// 初期リストを呼出し
		KuracKuraChokuDataInList initList = (KuracKuraChokuDataInList) getSession(KEY_SS_INITLIST); // ヘッダー部
		KuracKuraChokuDataInDtList initDtList = (KuracKuraChokuDataInDtList) getSession(KEY_SS_INITLIST_DT); // ディテール部

		// =============================================
		// ・チェックサービス呼び出しの呼び出し
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ・セッションにリスト入力情報をセット
		// =============================================
		KuracKuraChokuDataIn_CheckService checkServ = new KuracKuraChokuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// =============================================
		// editFormの値をeditListにセットする。
		// （登録情報をコピーする。）
		// =============================================
		// editLitsの修正用編集を行う
		// =============================================
		// ヘッダー部
		// =============================================
		editList.setPageList(editForm.getKuracKuraChokuDataInList());
		KuracKuraChokuDataInRecord hdRec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();
		// =============================================
		// ディテール部
		// =============================================
		this.reCreateList(checkServ, hdRec);

		// チェックを実行する
		if (!checkServ.validateConfirmAsRefPlusMinus(editForm, initList, initDtList, editList, editDtList)) {

			// リクエストにEditFormをセット(mode:add, preMode:null, 編集フォーム設定可)
			editForm.setMode(MODE_REFPLUS);
			editForm.setEditable(IS_EDITABLE_TRUE);
			// タブインデックスを設定する
			setTabindexConfirmAndBack(MODE_REFPLUS, editList, editDtList);
			return ;
		}

		// 確認画面へ遷移させる
		editForm.setMode(MODE_CONFIRM);
		editForm.setPreMode(MODE_REFPLUS);
		editForm.setEditable(IS_EDITABLE_TRUE);
		editForm.setTabIndexCommitAndBack();

		return ;
	}

	/**
	 * 訂正(-)時のチェックを実行する(操作フェーズ)
	 *
	 * @throws KitException
	 */
	private void confirmRefMinus() throws KitException {

		// 初期リストを呼出し
		KuracKuraChokuDataInList initList = (KuracKuraChokuDataInList) getSession(KEY_SS_INITLIST); // ヘッダー部
		KuracKuraChokuDataInDtList initDtList = (KuracKuraChokuDataInDtList) getSession(KEY_SS_INITLIST_DT); // ディテール部

		// =============================================
		// ・チェックサービス呼び出しの呼び出し
		// ・戻り値を取得し、結果を判定
		// ・例外catch処理記述
		// ・セッションにリスト入力情報をセット
		// =============================================
		KuracKuraChokuDataIn_CheckService checkServ = new KuracKuraChokuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// =============================================
		// editFormの値をeditListにセットする。
		// （登録情報をコピーする。）
		// =============================================
		// editLitsの修正用編集を行う
		// =============================================
		// ヘッダー部
		// =============================================
		editList.setPageList(editForm.getKuracKuraChokuDataInList());
		KuracKuraChokuDataInRecord hdRec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();
		// =============================================
		// ディテール部
		// =============================================
		this.reCreateList(checkServ, hdRec);

		// チェックを実行する
		if (!checkServ.validateConfirmAsRefPlusMinus(editForm, initList, initDtList, editList, editDtList)) {

			// リクエストにEditFormをセット(mode:add, preMode:null, 編集フォーム設定可)
			editForm.setMode(MODE_REFMINUS);
			editForm.setEditable(IS_EDITABLE_TRUE);
			// タブインデックスを設定する
			setTabindexConfirmAndBack(MODE_REFMINUS, editList, editDtList);
			return ;
		}

		// 確認画面へ遷移させる
		editForm.setMode(MODE_CONFIRM);
		editForm.setPreMode(MODE_REFMINUS);
		editForm.setEditable(IS_EDITABLE_TRUE);
		editForm.setTabIndexCommitAndBack();

		return ;
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

		editForm = new KuracKuraChokuDataIn_EditForm();

	}

	/**
	 * 改ページ処理(検索フェーズ)
	 *
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean move() throws KitException {

		// セッションから、呼出リストを取得
		KuracKuraChokuDataInList searchedList = (KuracKuraChokuDataInList) getSession(KEY_SS_SEARCHEDLIST);

		/*
		 * 選択行にフラグを立てる
		 *  - セット欄に値が入っているとき(優先)
		 *  - クリックで行を選択しているとき
		 */
		for (;;) {
			// コードでフラグをセット
			if (!PbsUtil.isEmpty(editForm.getKuradataNo())) {
				searchedList.setModifiedByCd(editForm.getKuradataNo());
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
	 * 「セット」ボタン押下時の処理(検索フェーズ)
	 */
	private void select() throws KitException {

		// 選択リストを初期化する
		removeSession(KEY_SS_INITLIST);
		removeSession(KEY_SS_INITLIST_DT);
		removeSession(KEY_SS_EDITLIST);
		removeSession(KEY_SS_EDITLIST_DT);

		// ViewJSPで検索部のボタンを非活性にするために フォームの情報を取得
		KuracKuraChokuDataIn_SearchForm searchForm = (KuracKuraChokuDataIn_SearchForm) getSession(KEY_SS_SEARCHFORM);

		// セッションから、画面で入力した登録情報を取得
		KuracKuraChokuDataInList searchedList = (KuracKuraChokuDataInList) getSession(KEY_SS_SEARCHEDLIST);

		KuracKuraChokuDataIn_CheckService checkServ = new KuracKuraChokuDataIn_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 詳細表示させるときのチェック
		if (!checkServ.validateSelect(editForm, searchedList)) {

			// 一覧を表示させる
			editForm.setMode(MODE_SEARCH);
			editForm.setPreMode(MODE_EMPTY);
			editForm.setEditable(IS_EDITABLE_TRUE);
			return;
		}

		// チェックOK
		// ⇒ 一覧から選択できた
		editForm.setMode(MODE_SELECTED);
		editForm.setPreMode(MODE_EMPTY);
		editForm.setEditable(IS_EDITABLE_TRUE);
		editForm.setSelectedRowId(null);

		// 戻るボタンのタブインデックスを設定する
		editForm.setTabIndexBack(TAB_INDEX_HEAD);

		// 選択結果を取得
		// ヘッダー部
		KuracKuraChokuDataInRecord selectedRec = checkServ.getRecSelected();
		KuracKuraChokuDataInList editList = new KuracKuraChokuDataInList(selectedRec);
		// ==================================
		// ヘッダー部の表示情報を設定
		// ==================================
		editList.setHdInfo();

		// 検索実行
		// ディテール部
		KuracKuraChokuDataIn_SearchService searchServ = new KuracKuraChokuDataIn_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		KuracKuraChokuDataInDtList editDtList = searchServ.getKurachokuDataInDtList(editForm);
		// maxSuまで空レコードで埋める
		editDtList.initListToMax(editDtList.size(), editForm.getMaxSuDt());
		// ==================================
		// ディテール部の表示情報を設定
		// ==================================
		editDtList.setDtInfo();

		// レコードの状態をフォームにセットする
		editForm.setDeletedRecord(selectedRec);

		// ヘッダー部
		editList.setOffset(0);
		editList.setRecordsParPage(editForm.getMaxSu());
		// ディテール部
		editDtList.setOffset(0);
		editDtList.setRecordsParPage(editForm.getMaxSuDt());

		// ViewJSPで検索部のボタンを非活性にするためにチェックを実行
		checkServ.validateSelectBtnAvailFlg(editList, editDtList, searchForm);

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
	 * 確認、戻るボタンにタブインデックスを設定する
	 *
	 * @param reqType
	 * @param editList
	 * @param editDtList
	 */
	private void setTabindexConfirmAndBack(String reqType, KuracKuraChokuDataInList editList, KuracKuraChokuDataInDtList editDtList) {

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
			editDtList.setEditableElementsParRecord(ELEMENTS_ON_REFERENCE_DT);
			idx = Integer.toString(Integer.parseInt(editList.getConfirmBtnTabIndex())
					+ Integer.parseInt(editDtList.getConfirmBtnTabIndex()) - 1);
			break;

		case REQ_TYPE_REFMINUS: // 訂正(-)
			editList.setEditableElementsParRecord(ELEMENTS_ON_REFERENCE);
			editDtList.setEditableElementsParRecord(ELEMENTS_ON_REFERENCE_DT);
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
	 * クラス名を取得.
	 *
	 * @return クラス名
	 */
	@Override
	protected String getFunctionName() {

		return className__;
	}

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
	private void reCreateList(KuracKuraChokuDataIn_CheckService checkServ, KuracKuraChokuDataInRecord hdRec) throws KitException {

		// リストを入力行だけに変換
		boolean currentAddMode = editDtList.isAddMode();
		editDtList.setAddMode();
		editDtList.setPageList(editForm.getKuracKuraChokuDataInDtList());

		// モードを元に戻す
		editDtList.setAddMode(currentAddMode);

		// ==================================
		// ディテール部の付加情報を設定
		// ==================================
		for (PbsRecord tmp : editDtList) {
			KuracKuraChokuDataInDtRecord dtRec = (KuracKuraChokuDataInDtRecord) tmp;
			dtRec.setAddMode(currentAddMode);
		}

		// maxSuまで空レコードで埋める
		editDtList.initListToMax(editDtList.size(), editForm.getMaxSuDt());
	}

	/*
	 * ヘッダー部情報を登録用に変換
	 */
	private void convertToInsHdInfo(KuracKuraChokuDataInList editList) {
		KuracKuraChokuDataInRecord hdRec = (KuracKuraChokuDataInRecord) editList.getFirstRecord();

		// 蔵直ﾃﾞｰﾀ連番自動採番
		KuracKuraChokuDataIn_NumberClient numberClient = KuracKuraChokuDataIn_NumberClient.getInstance();
		String kuradataNO = numberClient.getNextKuradataNO(getDatabase(), SEQ_T_KURADATA_NO, MAX_LEN_KURADATA_NO);

		// 蔵直ﾃﾞｰﾀ連番
		hdRec.setKuradataNo(kuradataNO);

		// 新規、参照追加の場合
		if (editList.isAddMode()) {
			// 申込み受付NO
			hdRec.setMousikomiNo(getMousikomiNo(hdRec));
		}
		// 申込み受付日
		hdRec.setUketukeDt(PbsUtil.getYMD(hdRec.getUketukeDt()));
		// 発送予定日
		hdRec.setHasoYoteiDt(PbsUtil.getYMD(hdRec.getHasoYoteiDt()));

		// 入力情報区分
		hdRec.setInputKbn(KURAC_INPUT_KB_SEIKI_GAICYU);

	}


	/*
	 * ヘッダー部情報を登録用に変換
	 */
	private void convertToInsDtInfo(KuracKuraChokuDataInRecord hdRec, KuracKuraChokuDataInDtList editDtList) {

		String kuradataNO = hdRec.getKuradataNo();

		for (int i = 0; i < editDtList.size(); i++) {
			KuracKuraChokuDataInDtRecord rec = (KuracKuraChokuDataInDtRecord) editDtList.get(i);

			// 未入力行は無視する
			if (rec.isEmpty()) {
				continue;
			}

			// 蔵直ﾃﾞｰﾀ連番
			rec.setKuradataNo(kuradataNO);
			// 利用区分
			rec.setRiyouKbn(AVAILABLE_KB_RIYO_KA);
		}
	}

	/**
	 * 申込み受付NOを作成
	 * @param editList
	 * @return
	 */
	private String getMousikomiNo(KuracKuraChokuDataInRecord hdRec) {

		// 申込み受付NO
		StringBuffer mousikomiNo = new StringBuffer();

		// 黄桜事業所区分
		mousikomiNo.append(hdRec.getJigyosyoKbn());
		// 蔵直商品ｸﾞﾙｰﾌﾟCD
		mousikomiNo.append(hdRec.getShoninGrpCd());
		// ﾃﾞｰﾀ種別CD
		mousikomiNo.append(hdRec.getSyubetuCd());
		// 整理No
		mousikomiNo.append(hdRec.getKuradenNo());
		// 届先No
		mousikomiNo.append(hdRec.getTodokesakiLineNo());

		// [黄桜事業所区分]+[蔵直商品ｸﾞﾙｰﾌﾟCD]+【ﾃﾞｰﾀ種別CD】+[整理No]+[届先No]
		return mousikomiNo.toString();
	}

} // -- class
