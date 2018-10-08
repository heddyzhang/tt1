package kit.jiseki.AriaList;

import static kit.jiseki.AriaList.IJisekiAriaList.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComEigyoCalendarUtil;
import fb.com.exception.KitComException;
import fb.com.exception.NoDataException;
import fb.inf.KitFunction;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 検索部からのリクエストを処理する実装クラスです
 */
public class JisekiAriaList_SearchFunction extends KitFunction {

	/**
	 *
	 */
	private static final long serialVersionUID = -8482996347447423315L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JisekiAriaList_SearchFunction.class.getName();

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
	public JisekiAriaList_SearchFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		super(mapping, form, request, response);
	}

	/**
	 * メインメソッドです。 リクエストタイプの判別,検索条件のチェック,検索および検索結果のビーンへのセットを行います。
	 *
	 * @return 処理結果を表す文字列(FORWARD_SUCCESS,FORWARD_FAIL)
	 * @throws KitComException
	 * @throws KitException
	 */
	@Override
	protected String execute_() throws KitComException, KitException {
		String sForwardPage = FORWARD_SUCCESS;

		JisekiAriaList_SearchForm searchForm = (JisekiAriaList_SearchForm) form_;

		// ----------------------------------------------------
		// 呼出 on init
		// ----------------------------------------------------
		if (REQ_TYPE_SEARCH.equals(searchForm.getReqType())) {
			search(searchForm);
		}

		return sForwardPage;
	}

	/**
	 * 「呼出」処理を行う
	 *
	 * @param searchForm
	 *            エリア実績検索フォーム
	 * @throws NoDataException
	 */
	private void search(JisekiAriaList_SearchForm searchForm) throws NoDataException, KitException {
		// --------------------------
		// Listビーンを初期化(削除)する。
		// --------------------------
		removeSession(KEY_SS_SEARCHEDJGYOSYOLIST);
		removeSession(KEY_SS_SEARCHEDKALIST);

		try {
			// 検索日時に現在時刻（日付＋時刻）を設定
			searchForm.setKensakuNiji(PbsUtil.getCurrentDateTime());

			//入力パラメータを設定
			ComEigyoCalendarUtil calUtil = new ComEigyoCalendarUtil(getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
			//指定日
			String shiteibi = searchForm.getShiteibi();
			// 指定月
			String shiteiGestu = shiteibi.substring(0, 6);

			// 指定日月初
			searchForm.setShiteibiGetsusyo(shiteiGestu + FIRST_DAY_OF_MONTH );
			// 前年同日
			searchForm.setZennenDojitsu(PbsUtil.getDateAddYear(shiteibi, -1));
			// 前年同日月初
			searchForm.setZennenDojitsuGetsusyo(PbsUtil.getDateAddYear(shiteibi, -1).substring(0, 6)  + FIRST_DAY_OF_MONTH);
			// 前年同日月末
			searchForm.setZennenDojitsuGetsumatsu(calUtil.getCalEndMonthAYearAgoDt(shiteiGestu));
			// 前々年同日月初
			searchForm.setZenzennenDojitsuGetsusyo(PbsUtil.getDateAddYear(shiteibi, -2).substring(0, 6) + FIRST_DAY_OF_MONTH);
			// 前々年同日月末
			searchForm.setZenzennenDojitsuGetsumatsu(calUtil.getCalEndMonthTwoYearAgoDt(shiteiGestu));
			// 今期初
			searchForm.setKonkisyo(calUtil.getBeginningOfFiscalYear(shiteibi));
			// 前期初
			searchForm.setZenkisyo(calUtil.getBeginningOfFiscalAYearAgo(shiteibi));
			// 今年初
			searchForm.setKotoshisyo(shiteibi.substring(0, 4) + FIRST_DAY_OF_ICHIGATSU);
			// 前年初
			searchForm.setZennensyo(PbsUtil.getDateAddYear(shiteibi, -1).substring(0, 4) + FIRST_DAY_OF_ICHIGATSU);

			// 検索実行
			JisekiAriaList_SearchService searchServ = new JisekiAriaList_SearchService(
					getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
			// 事業所レベル検索
			searchForm.setSearchKbn(SEARCH_KBN_SITEN);
			PbsRecord[] sitenRecords = searchServ.executeShiten(searchForm);

			// 検索結果をリストクラスに変換
			JisekiAriaList searchedSitenList = new JisekiAriaList(sitenRecords);
			// リストクラスを実績用の合計リストに変換
			JisekiAriaList jisekiAreaSitenDispList = searchServ.convertToGokeiList(searchedSitenList);

			// 検索結果をセッションへ、明細部をリクエストへ設定する
			setSession(KEY_SS_SEARCHEDJGYOSYOLIST, jisekiAreaSitenDispList);

			// 課レベル検索
			searchForm.setSearchKbn(SEARCH_KBN_KA);
			PbsRecord[] kaRecords = searchServ.executeKa(searchForm);
			// 課情報がある場合
			if (kaRecords != null) {
				// 検索結果をリストクラスに変換
				JisekiAriaList searchedKaList = new JisekiAriaList(kaRecords);
				// リストクラスを実績用の表示リストに変換
				JisekiAriaList jisekiAriaKaDispList = searchServ.convertToKaDispList(searchedKaList);

				// 検索結果をセッションへ、明細部をリクエストへ設定する
				setSession(KEY_SS_SEARCHEDKALIST, jisekiAriaKaDispList);
			}
		}
		catch (DataNotFoundException e) {
			// XXX：検索結果を取得できなかったときは…
			throw new NoDataException();
		}
	}

} // -- class
