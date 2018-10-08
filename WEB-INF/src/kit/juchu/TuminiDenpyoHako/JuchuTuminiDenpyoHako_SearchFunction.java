package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConstHM.*;
import static kit.juchu.TuminiDenpyoHako.IJuchuTuminiDenpyoHako.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.keiri.SeikyusyoHakko.KeiriSeikyusyoHakkoDtRecord;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComKitUtil;
import fb.com.Records.FbMastrSyohinRecord;
import fb.com.exception.NoDataException;
import fb.inf.KitFunction;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 検索部からのリクエストを処理する実装クラスです
 */
public class JuchuTuminiDenpyoHako_SearchFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = -6766112539331805443L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JuchuTuminiDenpyoHako_SearchFunction.class.getName();

	/** 明細部 */
	private JuchuTuminiDenpyoHako_EditForm editForm;

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
	public JuchuTuminiDenpyoHako_SearchFunction(ActionMapping mapping, ActionForm form,
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

		JuchuTuminiDenpyoHako_SearchForm searchForm = (JuchuTuminiDenpyoHako_SearchForm) form_;

		// ----------------------------------------------------
		// 呼出 on init
		// ----------------------------------------------------
		if (REQ_TYPE_SEARCH.equals(searchForm.getReqType())) {
			search(searchForm);
		}

		// 呪文
		setRequest(KEY_SS_EDITFORM, editForm);

		// 宛先を返す
		return ComKitUtil.getForwardForMaster(editForm);
	}

	/**
	 * 「呼出」処理を行う
	 *
	 * @param searchForm JuchuTuminiDenpyoHako_SearchForm
	 * @throws NoDataException
	 */
	private void search(JuchuTuminiDenpyoHako_SearchForm searchForm) throws NoDataException {

		// --------------------------
		// EditListビーンを初期化(削除)する。
		// --------------------------
		removeSession(KEY_SS_SEARCHEDLIST);
		removeSession(KEY_SS_EDITLIST_HD);
		removeSession(KEY_SS_EDITLIST_DT);

		// チェックサービスを初期化
		JuchuTuminiDenpyoHako_CheckService checkServ = new JuchuTuminiDenpyoHako_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 名称検索キーを前詰めする
		searchForm.reOrder();

		// 呼出時チェックを実行
		if (!checkServ.validateSearch(searchForm)) {
			// リクエストにEditFormをセット
			// エラー検知時(mode:null, preMode:null, 編集フォーム可)
			editForm = new JuchuTuminiDenpyoHako_EditForm();
			return;
		}

		try {
			// 検索実行
			JuchuTuminiDenpyoHako_SearchService searchServ = new JuchuTuminiDenpyoHako_SearchService(
					getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
			PbsRecord[] records = searchServ.execute(searchForm);

			// 検索結果をリストクラスに変換
			JuchuTuminiDenpyoHakoList searchedList = new JuchuTuminiDenpyoHakoList(records);

			JuchuTuminiDenpyoHako_UpdateService updateServ = new JuchuTuminiDenpyoHako_UpdateService(
					getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
			// 摘要区分の積荷伝票記載フラグの取得処理
			updateServ.setTumidenKisaiFlgs(searchedList);
			// 摘要区分の成形処理
			updateServ.constTekiyouKbn(searchedList);

			// 受注NOを整形
			searchedList.setFormatJyucyuNo();

			// 呪文 ・・・ １ページ目指定＆ページあたり行数指定
			searchedList.setOffset(0);
			searchedList.setRecordsParPage(searchForm.getMaxSu());
			searchedList.setEditableElementsParRecord(ELEMENTS_ON_SEARCH);

			// リクエストにEditFormをセット(mode:edit or delete or result, preMode:null,
			// 編集フォーム設定可)
			editForm = new JuchuTuminiDenpyoHako_EditForm();
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

			// 呪文・・・検索結果をセッションへ、明細部をリクエストへ設定する
			setSession(KEY_SS_SEARCHEDLIST, searchedList);

		} catch (DataNotFoundException e) {
			// XXX：検索結果を取得できなかったときは…
			// リクエストにEditFormをセット(mode:null, preMode:null)
			editForm = new JuchuTuminiDenpyoHako_EditForm();
			throw new NoDataException();
		}

	}

} // -- class
