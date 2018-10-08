package kit.jiseki.Nenpou;

import static fb.com.IKitComConst.*;
import static kit.jiseki.Nenpou.IJisekiNenpou.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kit.jiseki.Nenpou.JisekiNenpou_SearchForm;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.MstSearch.MstCdmeisyMaster_SearchService;
import fb.inf.KitFunction;
import fb.inf.KitSelectList;
import fb.inf.exception.KitException;
import fb.inf.pbs.PbsRecord;

/**
 * 初期表示処理の実装クラスです
 *
 * @author nishikawa@kz
 *
 */
public class JisekiNenpou_InitFunction extends KitFunction {


	/** シリアルID */
	private static final long serialVersionUID = -316976168106270926L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JisekiNenpou_InitFunction.class.getName();

	// -----------------------------------------
	// define variable
	// -----------------------------------------
	private static final boolean isTransactionalFunction = false;

	/**
	 * Transaction制御フラグを返却する。
	 *
	 * @return Transaction制御フラグ
	 */
	@Override
	protected boolean isTransactionalFunction() {

		return isTransactionalFunction;
	}

	/**
	 * クラス名を返却する。
	 *
	 * @return クラス名
	 */
	@Override
	protected String getFunctionName() {

		return className__;
	}

	/**
	 * コンストラクタ。
	 *
	 * @param mapping
	 *            ActionMapping
	 * @param form
	 *            ActionForm
	 * @param request
	 *            HttpサーブレットRequest
	 * @param response
	 *            HttpサーブレットResponse
	 */
	public JisekiNenpou_InitFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		super(mapping, form, request, response);
	}

	/**
	 * 初期処理を実行する。<br>
	 * 初期リストを取得する
	 *
	 * @return Forward情報
	 * @throws KitException
	 * @Override
	 */

	public String execute_() throws KitException {

		String sForwardPage = FORWARD_SUCCESS;


		// ***セレクトリストの設定*****
		MstCdmeisyMaster_SearchService searchServ = new MstCdmeisyMaster_SearchService(
				getComUserSession(), getDatabase(),isTransaction(), getActionErrors());

		//searchServ.setForSelectListShort(true);  //略称名を取得
		//searchServ.setForSelectListShort(false);  //正式名を取得
		//searchServ.setUseCodeFlg(false); 			//コード無しパターン


	    // 通常パターン
		// ★★事業所
		searchServ.setForSelectListShort(false);  //正式名を取得
		searchServ.search(KBN_HJS_SITEN);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KBN_HJS_SITEN, new KitSelectList(searchServ.getPbsRecords()));
		}
		// ★★課
		searchServ.setForSelectListShort(true);  //略称名を取得
		searchServ.search(KBN_HJS_KA);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KBN_HJS_KA, new KitSelectList(searchServ.getPbsRecords()));
		}
		// ★★担当者　
		searchServ.setForSelectListShort(false);  //正式名を取得
		searchServ.search(KBN_JISEKI_TANTO_CD);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KBN_TANTO_CD, new KitSelectList(searchServ.getPbsRecords()));
		}
		// ★★都道府県
		searchServ.setForSelectListShort(false);  //正式名を取得
		searchServ.search(KBN_COUNTY_CD);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KBN_COUNTY_CD, new KitSelectList(searchServ.getPbsRecords()));
		}
		// ★★出荷先国
		searchServ.setForSelectListShort(true);  //略称名を取得
		searchServ.search(KBN_SYUKA_SAKI_COUNTRY);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KBN_SYUKA_SAKI_COUNTRY, new KitSelectList(searchServ.getPbsRecords()));
		}
		// ★★全国卸
		searchServ.setForSelectListShort(true);  //略称名を取得
		searchServ.search(KBN_TOKUYAKU_ZENKOKU);
		if (searchServ.getPbsRecords() != null) {
			setSession(KEY_SS_KBN_TOKUYAKU_ZENKOKU, new KitSelectList(searchServ.getPbsRecords()));
		}

		// ★★期間（画面固有）
		String[] selNen ={"1,当年のみ","2,当年前年"};
		setSession(KEY_SS_KBN_SEL_NEN,createSelList(selNen));

		// ★★データ（画面固有）
		String[] selData ={"1,出荷","2,戻入"};
		setSession(KEY_SS_KBN_SEL_DATA,createSelList(selData));

		// ★★集計単位（画面固有）
		String[] selSyukei ={"1,金額","2,本数","3,容量"};
		setSession(KEY_SS_KBN_SEL_SYUKEI,createSelList(selSyukei));


		// 次画面へ遷移する
		return sForwardPage;
	}

	/**
	 * 画面固有の選択リストを作成する
	 *
	 */
	private static KitSelectList createSelList (String[] selStr){
		PbsRecord[] recs = null;
		List<PbsRecord> list = new ArrayList<PbsRecord>();

		for (int i = 0; i<selStr.length; i++){
			String[] cdNm = selStr[i].split(",");
			PbsRecord rec = new PbsRecord();
			rec.put("CODE", cdNm[0]);
			rec.put("NAME", cdNm[1]);
			rec.put("LABEL", cdNm[0] + " : " + cdNm[1]);
		//	rec.put("LABEL", cdNm[1]);
			list.add(rec);

		}

		recs = (PbsRecord[])list.toArray(new PbsRecord[list.size()]);

		KitSelectList selectList = new KitSelectList();
		selectList = new KitSelectList(recs);

		return selectList;

	}


}