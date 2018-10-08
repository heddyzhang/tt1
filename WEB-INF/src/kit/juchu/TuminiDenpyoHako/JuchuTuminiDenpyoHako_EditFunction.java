package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConstHM.*;
import static kit.juchu.TuminiDenpyoHako.IJuchuTuminiDenpyoHako.*;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComHaitaService;
import fb.com.ComKitUtil;
import fb.inf.KitFunction;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.KitException;

/**
 * 明細部からのリクエスト処理の実装クラスです
 */
public class JuchuTuminiDenpyoHako_EditFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = 4962088092090455936L;

	/** クラス名. */
	private static String className__ = JuchuTuminiDenpyoHako_EditFunction.class.getName();

	/**
	 * 呪文 トランザクション制御を行うかどうかを示すフラグ. trueの場合トランザクション制御を行う.
	 * falseの場合トランザクション制御を行なわない.
	 *
	 * @see #isTransactionalFunction ()
	 */
	private static final boolean isTransactionalFunction = true;

	/** 編集フォーム */
	private JuchuTuminiDenpyoHako_EditForm editForm = null;
	/** 編集リスト */
	private JuchuTuminiDenpyoHakoList editList = null; 			// 受注データ／HD部

	private JuchuTuminiDenpyoHakoList kobetuList = null; 		// 受注データ／HD部（個別）
	private JuchuTuminiDenpyoHakoList syuyakuList = null; 	// 受注データ／HD部（集約）
//	private JuchuTuminiDenpyoHakoList syuyakuUnsotenCdList = null; 	// 集約済受注データ／HD部の運送店CDリスト
//	private JuchuTuminiDenpyoHakoList syuyakuListByUnsotenCd = null; 	// 集約済受注データ／HD部を指定した運送店CDで再検索したリスト

	/**
	 * コンストラクタです.
	 *
	 * @param mapping paramName
	 * @param form paramName
	 * @param request paramName
	 * @param response paramName
	 */
	public JuchuTuminiDenpyoHako_EditFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		super(mapping, form, request, response);
	}

	// -----------------------------------------
	// search & set page data
	// -----------------------------------------
	@Override
	public String execute_() throws KitException, DataNotFoundException, SQLException {

		// オブジェクトを取得
		editForm = (JuchuTuminiDenpyoHako_EditForm) form_;

		// 「伝票発行」の場合
		if (REQ_TYPE_PRINT.equals(editForm.getReqType())) {
			print();

		// 「重量確認」の場合
		} else if (REQ_TYPE_CHECK.equals(editForm.getReqType())) {
			check();

		// 改ページ処理
		} else {
			move();
		}

		// 呪文
		setRequest(KEY_SS_EDITFORM, editForm);

		// 宛先取得
		return ComKitUtil.getForwardForMaster(editForm);
	}

	/**
	 * 改ページ処理(検索フェーズ)
	 *
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean move() throws KitException {

		// セッションから、呼出リストを取得
		JuchuTuminiDenpyoHakoList searchedList = (JuchuTuminiDenpyoHakoList) getSession(KEY_SS_SEARCHEDLIST);

		// editListを画面入力値で更新
		searchedList.setPageList(this.editForm.getJuchuTuminiDenpyoHakoList());

		// チェック状態からフラグを更新
		searchedList.setModifiedByChk();

		// ページ移動
		searchedList.setPageRequest(this.editForm.getReqType());

		return true;
	}


	private void check() throws KitException {

		// 選択リストを初期化する
		removeSession(KEY_SS_EDITLIST);

		// セッションから、画面で入力した登録情報を取得
		JuchuTuminiDenpyoHakoList searchedList = (JuchuTuminiDenpyoHakoList) getSession(KEY_SS_SEARCHEDLIST);

		// 伝票発行対象リストを作成
		if (!(this.editForm.getJuchuTuminiDenpyoHakoList()).isEmpty()) {
			// editListを画面入力値で更新
			searchedList.setPageList(this.editForm.getJuchuTuminiDenpyoHakoList());

			// チェック状態からフラグを更新
			searchedList.setModifiedByChk();

			// 伝票発行対象リストを作成
			editList = searchedList.getDenpyoHakoList();
			// 個別発行対象リストを作成
			kobetuList = editList.getKobetuHakoList();
			// 集約発行対象リストを作成
			syuyakuList = editList.getSyuyakuHakoList();
		}

		JuchuTuminiDenpyoHako_CheckService checkServ = new JuchuTuminiDenpyoHako_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 伝票発行前のチェック
		if (!checkServ.validatePrint(editForm, editList, syuyakuList)) {
			// 一覧を表示させる
			editForm.setMode(MODE_SEARCH);
			editForm.setPreMode(MODE_EMPTY);
			editForm.setEditable(IS_EDITABLE_TRUE);
			return;
		}

//		// 個別発行対象リストを作成
//		kobetuList = editList.getKobetuHakoList();
//		// 集約発行対象リストを作成
//		syuyakuList = editList.getSyuyakuHakoList();

		// 重量チェック
		JuchuTuminiDenpyoHakoList wList = checkServ.calcWeight(kobetuList, syuyakuList);

		setSession(KEY_SS_WEIGHT_LIST, wList);

		editForm.setMode(MODE_CHECK);

	}


	/**
	 * 「伝票発行」ボタン押下時の処理
	 * @throws SQLException
	 * @throws DataNotFoundException
	 */
	private void print() throws KitException, DataNotFoundException, SQLException {

		// 選択リストを初期化する
		removeSession(KEY_SS_EDITLIST);

		// セッションから、画面で入力した登録情報を取得
		JuchuTuminiDenpyoHakoList searchedList = (JuchuTuminiDenpyoHakoList) getSession(KEY_SS_SEARCHEDLIST);

		// 伝票発行対象リストを作成
		if (!(this.editForm.getJuchuTuminiDenpyoHakoList()).isEmpty()) {
			// editListを画面入力値で更新
			searchedList.setPageList(this.editForm.getJuchuTuminiDenpyoHakoList());

			// チェック状態からフラグを更新
			searchedList.setModifiedByChk();

			// 伝票発行対象リストを作成
			editList = searchedList.getDenpyoHakoList();
		}

		// 排他サービスを生成する
		ComHaitaService haitaServ = new ComHaitaService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// モードをセットする ･･･ 排他を取得するときに必須
		haitaServ.setMode(MODE_EDIT);

//		JuchuTuminiDenpyoHako_CheckService checkServ = new JuchuTuminiDenpyoHako_CheckService(
//				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 伝票発行前のチェック
//		if (!haitaServ.lock(editList)
//				|| !checkServ.validatePrint(editForm, editList)) {
		if (!haitaServ.lock(editList)) {
			// チェックエラー時、排他解除する
            haitaServ.unLock(editList, false);

			// 一覧を表示させる
			editForm.setMode(MODE_SEARCH);
			editForm.setPreMode(MODE_EMPTY);
			editForm.setEditable(IS_EDITABLE_TRUE);
			return;
		}

		// 個別発行対象リストを作成
		kobetuList = editList.getKobetuHakoList();
		// 集約発行対象リストを作成
		syuyakuList = editList.getSyuyakuHakoList();

		JuchuTuminiDenpyoHako_UpdateService updateServ = new JuchuTuminiDenpyoHako_UpdateService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		if (!hasHaita()
				|| !updateServ.print(editForm, kobetuList, editList, syuyakuList)) {

			// 各種モードを設定
			editForm.setMode(MODE_PRINT);
			editForm.setPreMode(MODE_EMPTY);
			editForm.setEditable(IS_EDITABLE_TRUE);
			return;
		}
		// 成功 ⇒ 初期画面へ戻る
		goStartPage();

		return;
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
	 * 先頭ページへ戻るときの定型処理
	 */
	private void goStartPage() {
		// セッション情報をクリア
		removeSession(KEY_SS_EDITLIST_HD);
		removeSession(KEY_SS_EDITLIST_DT);
		removeSession(KEY_SS_SEARCHEDLIST);
		//removeSession(KEY_SS_SEARCHFORM);
		editForm = new JuchuTuminiDenpyoHako_EditForm();

	}




} // -- class
