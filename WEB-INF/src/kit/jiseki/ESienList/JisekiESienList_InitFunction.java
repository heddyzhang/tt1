package kit.jiseki.ESienList;

import static fb.com.IKitComConst.COUNTY_CD_KB_HOKKAIDO;
import static fb.com.IKitComConst.HJS_KA_KB_NAGOYA_1KA;
import static fb.com.IKitComConst.HJS_SITEN_KB_NAGOYA;
import static fb.com.IKitComConst.JSK_HYOJI_TANI_KB_YEN;
import static fb.com.IKitComConst.JSK_KEISAN_DATE_KB_HAKKO_DATE;
import static fb.com.IKitComConst.JSK_OP1_UMU_KB_NONE;
import static fb.com.IKitComConst.JSK_SEL_DATA_KB_SYUKKA_NOMI;
import static fb.com.IKitComConst.KBN_COUNTY_CD;
import static fb.com.IKitComConst.KBN_HJS_KA;
import static fb.com.IKitComConst.KBN_HJS_SITEN;
import static fb.com.IKitComConst.KBN_JISEKI_TANTO_CD;
import static fb.com.IKitComConst.KBN_JSK_HYOJI_TANI;
import static fb.com.IKitComConst.KBN_JSK_KEISAN_DATE;
import static fb.com.IKitComConst.KBN_JSK_OP1_UMU;
import static fb.com.IKitComConst.KBN_JSK_SEL_DATA;
import static fb.com.IKitComConst.KBN_TOKUYAKU_ZENKOKU;
import static fb.com.IKitComConstHM.IS_EDITABLE_TRUE;
import static kit.jiseki.ESienList.IJisekiESienList.CYUSYUTU_JYOKEN_KB_ZENSYA;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_EDIT;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_COUNTY_CD;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_HJS_KA;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_HJS_SITEN;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_JISEKI_TANTO_CD;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_JSK_HYOJI_TANI;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_JSK_KEISAN_DATE;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_JSK_OP1_UMU;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_JSK_SEL_DATA;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_KBN_TOKUYAKU_ZENKOKU;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_SEARCH;
import static kit.jiseki.ESienList.IJisekiESienList.KEY_SS_SEARCHLIST;
import static kit.jiseki.ESienList.IJisekiESienList.SYUKEI_NAIYO_KB_JIGYOSYO;
import static kit.jiseki.ESienList.IJisekiESienList.TAIHI_UMU_KB_SYUKAREINYU;
import static kit.jiseki.ESienList.IJisekiESienList.TAISYO_HYOJI_KIKAN_KB_TOUGETU;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.ComUserSession;
import fb.com.MstSearch.MstCdmeisyMaster_SearchService;
import fb.inf.KitFunction;
import fb.inf.KitSelectList;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsUtil;

/**
 * 営業支援実績一覧の初期表示ファンクション
 * @author t_kimura
 *
 */
public class JisekiESienList_InitFunction extends KitFunction {

	/** serialVersionUID  */
	private static final long serialVersionUID = -7365281229390368137L;

	/** クラス名 */
	private static String className__ = JisekiESienList_InitFunction.class.getName();

	/**
	 * コンストラクタ
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 */
	public JisekiESienList_InitFunction(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		super(mapping, form, request, response);
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#execute_()
	 */
	public String execute_() throws KitException {
		//セッションクリア
		removeSession(KEY_SS_SEARCH);
		removeSession(KEY_SS_SEARCHLIST);
		removeSession(KEY_SS_EDIT);

		ComUserSession comUserSession = getComUserSession();

		// プルダウンの情報を設定
		searchListForPulldown();

		// 検索フォーム
		JisekiESienList_SearchForm searchForm = new JisekiESienList_SearchForm();
		searchForm.setEditable(IS_EDITABLE_TRUE);
		searchForm.setMode(MODE_INIT);
		// 初期値設定
		setInitValue(comUserSession, searchForm);

		// 検索フォームをセッションに格納する
		setSession(KEY_SS_SEARCH, searchForm);

		// 次画面へ遷移する
		return FORWARD_SUCCESS;
	}

	/**
	 * プルダウン情報設定
	 * @throws KitException
	 */
	private void searchListForPulldown() throws KitException {
		MstCdmeisyMaster_SearchService searchService = new MstCdmeisyMaster_SearchService(
				getComUserSession(), getDatabase(), isTransaction(), getActionErrors());
		// 対象データ
		searchList(searchService, true, KBN_JSK_SEL_DATA, KEY_SS_KBN_JSK_SEL_DATA);
		// 計算日付基準
		searchList(searchService, true, KBN_JSK_KEISAN_DATE, KEY_SS_KBN_JSK_KEISAN_DATE);
		// 自家用・小売有無
		searchList(searchService, true, KBN_JSK_OP1_UMU, KEY_SS_KBN_JSK_OP1_UMU);
		// 表示単位
		searchList(searchService, true, KBN_JSK_HYOJI_TANI, KEY_SS_KBN_JSK_HYOJI_TANI);
		// 事業所
		searchList(searchService, true, KBN_HJS_SITEN, KEY_SS_KBN_HJS_SITEN);
		// 課
		searchList(searchService, true, KBN_HJS_KA, KEY_SS_KBN_HJS_KA);
		// 担当者
		searchList(searchService, true, KBN_JISEKI_TANTO_CD, KEY_SS_KBN_JISEKI_TANTO_CD);
		// 都道府県
		searchList(searchService, true, KBN_COUNTY_CD, KEY_SS_KBN_COUNTY_CD);
		// 全国卸ﾚﾍﾞﾙ
		searchList(searchService, true, KBN_TOKUYAKU_ZENKOKU, KEY_SS_KBN_TOKUYAKU_ZENKOKU);
	}

	/**
	 * プルダウン情報設定
	 * @param searchService コード名称マスタ検索サービス
	 * @param isSelectListShort 略称使用フラグ
	 * @param searchKey 検索キー
	 * @param sessionKeyセッションキー
	 * @throws KitException
	 */
	private void searchList(MstCdmeisyMaster_SearchService searchService, boolean isSelectListShort, String searchKey, String sessionKey) throws KitException {
		searchService.setForSelectListShort(isSelectListShort);
		searchService.search(searchKey);
		if (searchService.getPbsRecords() != null) {
			setSession(sessionKey, new KitSelectList(searchService.getPbsRecords()));
		}
	}

	/**
	 * 初期値設定
	 * @param comUserSession ログインユーザセッション情報
	 * @param searchForm 検索フォーム
	 */
	private void setInitValue(ComUserSession comUserSession, JisekiESienList_SearchForm searchForm) {
		// 対象データ
		searchForm.setTaisyoData(JSK_SEL_DATA_KB_SYUKKA_NOMI);
		// 計算日付基準
		searchForm.setKeisanDateKijun(JSK_KEISAN_DATE_KB_HAKKO_DATE);
		// 自家用・小売有無
		searchForm.setJikakouriUmu(JSK_OP1_UMU_KB_NONE);
		// 表示単位
		searchForm.setHyojiTani(JSK_HYOJI_TANI_KB_YEN);
		// 抽出条件
		searchForm.setCyusyutuJyoken(CYUSYUTU_JYOKEN_KB_ZENSYA);
		// 事業所
		searchForm.setJigyosyoCd(HJS_SITEN_KB_NAGOYA);
		// 課
		searchForm.setKaCd(HJS_KA_KB_NAGOYA_1KA);
		// 担当者
		searchForm.setTantosyaCd("010101");
		// 都道府県
		searchForm.setCountryCd(COUNTY_CD_KB_HOKKAIDO);
		// 全国卸 1件目を選択するため未設定
		//searchForm.setZenkokuOrosiCd();
		// 対象表示期間
		searchForm.setTaisyoHyojiKikan(TAISYO_HYOJI_KIKAN_KB_TOUGETU);
		// 対比有無
		searchForm.setTaihiUmu(TAIHI_UMU_KB_SYUKAREINYU);
		// 集計内容
		searchForm.setSyukeiNaiyo(SYUKEI_NAIYO_KB_JIGYOSYO);
		// 対象日
		searchForm.setTaisyoDate(comUserSession.getGymDate());

		// 業務日付の月の1日
		Calendar gymMonthDate = Calendar.getInstance();
		gymMonthDate.set(PbsUtil.toint(PbsUtil.getYYYY(comUserSession.getGymDate())), PbsUtil.toint(PbsUtil.getMM(comUserSession.getGymDate())) - 1, 1);
		searchForm.setGymMonthDateFrom(PbsUtil.getYMD(gymMonthDate));
		// 業務日付の月の末日
		gymMonthDate.set(Calendar.DAY_OF_MONTH,gymMonthDate.getActualMaximum(Calendar.DATE));
		searchForm.setGymMonthDateTo(PbsUtil.getYMD(gymMonthDate));
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#isTransactionalFunction()
	 */
	@Override
	protected boolean isTransactionalFunction() {
		return false;
	}

	/* (非 Javadoc)
	 * @see fb.inf.KitFunction#getFunctionName()
	 */
	@Override
	protected String getFunctionName() {
		return className__;
	}
}