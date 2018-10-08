package kit.jiseki.CategoryList;

import static kit.jiseki.CategoryList.IJisekiCategoryList.*;

import java.text.ParseException;

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
public class JisekiCategoryList_SearchFunction extends KitFunction {

	/**
	 *
	 */
	private static final long serialVersionUID = -8482996347447423315L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JisekiCategoryList_SearchFunction.class.getName();

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
	public JisekiCategoryList_SearchFunction(ActionMapping mapping, ActionForm form,
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
	protected String execute_() throws KitComException, KitException  {
		String sForwardPage = FORWARD_SUCCESS;

		JisekiCategoryList_SearchForm searchForm = (JisekiCategoryList_SearchForm) form_;

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
	 *            カテゴリ実績検索フォーム
	 * @throws NoDataException
	 */
	private void search(JisekiCategoryList_SearchForm searchForm) throws NoDataException, KitException {
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

			// 前年同日月末
			searchForm.setZennenDojitsuGetsumatsu(calUtil.getCalEndMonthAYearAgoDt(shiteiGestu));
			// 前年同日
			searchForm.setZennenDojitsu(PbsUtil.getDateAddYear(shiteibi, -1));

			// 集計期間が当月の場合
			if (RDO_SYUKEI_KIKAN_TOGETSU.equals(searchForm.getSyukeiKikan())) {
				// 前年同日月初
				searchForm.setZennenDojitsuGetsusyo(PbsUtil.getDateAddYear(shiteibi, -1).substring(0, 6)  + FIRST_DAY_OF_MONTH);
				// 指定日月初
				searchForm.setShiteibiGetsusyo(shiteiGestu + FIRST_DAY_OF_MONTH );

			} else if (RDO_SYUKEI_KIKAN_JYUGATSU.equals(searchForm.getSyukeiKikan())) {
				// 集計期間が10月の場合
				// 前期初
				searchForm.setZenkisyo(calUtil.getBeginningOfFiscalAYearAgo(shiteibi));
				// 今期初
				searchForm.setKonkisyo(calUtil.getBeginningOfFiscalYear(shiteibi));

			} else {
				// 集計期間が1月の場合
				// 前年初
				searchForm.setZennensyo(PbsUtil.getDateAddYear(shiteibi, -1).substring(0, 4) + FIRST_DAY_OF_ICHIGATSU);
				// 今年初
				searchForm.setKotoshisyo(shiteibi.substring(0, 4) + FIRST_DAY_OF_ICHIGATSU);

			}

			// 検索実行
			JisekiCategoryList_SearchService searchServ = new JisekiCategoryList_SearchService(
					getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
			// 事業所レベル検索
			searchForm.setSearchKbn(SEARCH_KBN_SITEN);
			PbsRecord[] sitenRecords = searchServ.executeShiten(searchForm);

			// 検索結果をリストクラスに変換
			JisekiCategoryList searchedSitenList = new JisekiCategoryList(sitenRecords);
			// リストクラスを実績用の合計リストに変換
			JisekiCategoryList jisekiCategorySitenDispList = searchServ.convertToGokeiList(searchedSitenList);

			// 検索結果をセッションへ、明細部をリクエストへ設定する
			setSession(KEY_SS_SEARCHEDJGYOSYOLIST, jisekiCategorySitenDispList);

			// 課レベル検索
			searchForm.setSearchKbn(SEARCH_KBN_KA);
			PbsRecord[] kaRecords = searchServ.executeKa(searchForm);
			if (kaRecords != null) {
				// 検索結果をリストクラスに変換
				JisekiCategoryList searchedKaList = new JisekiCategoryList(kaRecords);

				// リストクラスを実績用の表示リストに変換
				JisekiCategoryList jisekiCategoryKaDispList = searchServ.convertToKaDispList(searchedKaList);
				// 検索結果をセッションへ、明細部をリクエストへ設定する
				setSession(KEY_SS_SEARCHEDKALIST, jisekiCategoryKaDispList);
			}
		}
		catch (DataNotFoundException e) {
			// XXX：検索結果を取得できなかったときは…
			throw new NoDataException();
		}

	}

} // -- class
