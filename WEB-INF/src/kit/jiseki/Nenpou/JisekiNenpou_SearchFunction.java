package kit.jiseki.Nenpou;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.pbs.IPbsConst.*;
import static kit.jiseki.Nenpou.IJisekiNenpou.*;

import java.text.ParseException;

import kit.jiseki.Nenpou.JisekiNenpou_EditForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComHaitaService;
import fb.com.ComKitUtil;
import fb.com.ComUtil;
import fb.com.exception.NoDataException;
import fb.inf.KitFunction;
import fb.inf.exception.DataNotFoundException;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 検索部からのリクエストを処理する実装クラスです
 */
public class JisekiNenpou_SearchFunction extends KitFunction {



	/** シリアルID */
	private static final long serialVersionUID = 2946516221580753744L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JisekiNenpou_SearchFunction.class.getName();

	/** 明細部 */
	private JisekiNenpou_EditForm editForm;


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
	 * @param mapping
	 *            paramName
	 * @param form
	 *            paramName
	 * @param request
	 *            paramName
	 * @param response
	 *            paramName
	 */
	public JisekiNenpou_SearchFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		super(mapping, form, request, response);
	}

	/**
	 * メインメソッドです。 リクエストタイプの判別,検索条件のチェック,検索および検索結果のビーンへのセットを行います。
	 *
	 * @return 処理結果を表す文字列(FORWARD_SUCCESS,FORWARD_FAIL)
	 * @throws NoDataException
	 * @throws ParseException
	 */
	@Override
	protected String execute_() throws NoDataException, ParseException {


		// 検索部を取得する
		JisekiNenpou_SearchForm searchForm = (JisekiNenpou_SearchForm) form_;


		// ----------------------------------------------------
		// 新規 on init
		// ----------------------------------------------------
//		if (REQ_TYPE_ADD.equals(searchForm.getReqType())) {
//			add(searchForm);

		// ----------------------------------------------------
		// 呼出 on init
		// ----------------------------------------------------
		 if (REQ_TYPE_SEARCH.equals(searchForm.getReqType())) {
			search(searchForm);
		 }

		// ----------------------------------------------------
		// 修正、参照追加 on selected
		// ----------------------------------------------------
//		} else if (PbsUtil.isIncluded(searchForm.getReqType(),
//				REQ_TYPE_EDIT, REQ_TYPE_REFERENCE)) {
//			manipulateEdit(searchForm);

		// ----------------------------------------------------
		// 抹消、復活 on selected
		// ----------------------------------------------------
//		} else if (PbsUtil.isIncluded(searchForm.getReqType(),
//				REQ_TYPE_DELETE, REQ_TYPE_REBIRTH)) {
//			manipulateView(searchForm);
//		}

		// 呪文
		setRequest(KEY_SS_EDITFORM, editForm);

		// 宛先を返す
		return ComKitUtil.getForwardForMaster(editForm);

	}


	/**
	 * 「呼出」処理を行う
	 *
	 * @param searchForm
	 *            年報検索フォーム
	 * @throws NoDataException
	 * @throws ParseException
	 */
	private void search(JisekiNenpou_SearchForm searchForm) throws NoDataException, ParseException {

		// --------------------------
		// EditListビーンを初期化(削除)する。
		// --------------------------
		removeSession(KEY_SS_INITLIST);
		removeSession(KEY_SS_EDITLIST);
		removeSession(KEY_SS_SEARCHEDLIST);

		// チェックサービスを初期化
		JisekiNenpou_CheckService checkServ = new JisekiNenpou_CheckService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());

		// 名称検索キーを前詰めする
		searchForm.reOrder();


		// 呼出時チェックを実行
		if (!checkServ.validateSearch(searchForm)) {
			// リクエストにEditFormをセット
			// エラー検知時(mode:null, preMode:null, 編集フォーム可)
			editForm = new JisekiNenpou_EditForm();
			return;
		}

		try {
			// 検索実行
			JisekiNenpou_SearchService searchServ = new JisekiNenpou_SearchService(
					getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
			PbsRecord[] records = searchServ.execute(searchForm);

			// 検索結果をリストクラスに変換
			JisekiNenpouList searchedList = new JisekiNenpouList(records);

			// 呪文 ・・・ １ページ目指定＆ページあたり行数指定
			searchedList.setOffset(0);
			searchedList.setRecordsParPage(searchForm.getMaxSu());

			// リクエストにEditFormをセット(mode:edit or delete or result, preMode:null,
			// 編集フォーム設定可)
			editForm = new JisekiNenpou_EditForm();
			editForm.setEditable(IS_EDITABLE_TRUE);

			/* **********************************************
			 * XXX:要注意ポイント
			 * MODE_RESULTは、使わない。新たにMODE_SEARCHを新設
			 * また、BOOLEANに替り、所定のMODE、PREMODEをセット
			 * することでFORWARD先を共通関数で指定する。
			 * MODEの対応表は、別途参照
			 */

			// 呼出モード時
			editForm.setMode(MODE_SEARCH);
			editForm.setPreMode(MODE_EMPTY);

			// 期間から表示月数を取得する
			String kikanStart = searchForm.getKikanStart(); // 期間開始日（当年）
			String kikanEnd = searchForm.getKikanEnd(); // 期間終了日（当年）

			editForm.setKikanMonth(searchServ.getKikanMonth(kikanStart, kikanEnd)); // 表示月数
			setMonthItem(editForm, kikanStart); // 表示項目名

			// ViewJSPで検索部のボタンを非活性にするために検索部の内容をリクエストに設定する
			setSession(KEY_SS_SEARCHFORM, searchForm);

			// 呪文・・・検索結果をセッションへ、明細部をリクエストへ設定する
			setSession(KEY_SS_SEARCHEDLIST, searchedList);

		} catch (DataNotFoundException e) {
			// XXX：検索結果を取得できなかったときは…
			// リクエストにEditFormをセット(mode:null, preMode:null)
			editForm = new JisekiNenpou_EditForm();
			throw new NoDataException();
		}

	}

	/**
	 * 表示項目名をセットする
	 *
	 * @param editForm
	 * @param kikanStart
	 */
	private void setMonthItem(JisekiNenpou_EditForm editForm, String kikanStart) {
		String[] monthItem = new String[12]; // 表示項目名
		monthItem[0] = kikanStart;
		for (int i = 1; i < 12; i++) {
			monthItem[i] = PbsUtil.getNextMonth(monthItem[i-1]);
		}
		editForm.setMonthItem(monthItem);
	}



} // -- class
