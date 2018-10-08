package kit.jiseki.Nenpou;

import static fb.com.IKitComConstHM.*;
import static fb.inf.IKitConst.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import fb.inf.KitSearchForm;
import fb.inf.pbs.PbsUtil;

/**
 * 検索部の構造を提供するデータオブジェクトクラスです
 */
public class JisekiNenpou_SearchForm extends KitSearchForm {


	/** シリアルID */
	private static final long serialVersionUID = 6689752052107131875L;

	// -----------------------------------------
	// 画面固有のインスタンス変数・アクセサメソッドを用意してください。
	// また、resetメソッドにリセット処理を記述します。
	// -----------------------------------------
	// 画面固有インスタンス変数
	private String syukaDt;  	// 出荷日
	private String ukeireDt;  	// 受入日付
	private String kikanStart;  // 期間　開始年月
	private String kikanEnd;    // 期間　終了年月
	private String kikanNen;    // 期間　当年・当年前年
	private String kbnData;     // データ区分
	private String syuukeiTani; // 集計単位
	private String jigyosyoCd;  // 事業所区分
	private String kaCd;        // 課区分
	private String tantosyaCd;     // 担当区分
	private String countryCd;   // 都道府県
	private String jisCd;   // 都道府県
	private String syukaSakiCountryCd; // 出荷先国
	private String zenkokuOrosi;       // 全国卸
	private String hjisekiSyukeiCd;       // 販売実績集計CD
	private String tokuyakutenCd;       // 特約店
	private String orositenCd;          // 卸店
	private String orositenCdLast;      // 最終送荷先
	private String tatesnCd;      // 縦線CD
	private String selNen; 			//期間(固有選択リスト)
	private String selData; 		//データ(固有選択リスト)
	private String selSyukei; 		//集計単位(固有選択リスト)
	private String tokuyakutenFlg; // 特約店フラグ


	// 画面固有アクセサメソッド


	/**
	 * 出荷日を取得する。
	 *
	 * @return 出荷日
	 */
	public String getSyukaDt() {
		return this.syukaDt;
	}

	/**
	 * 出荷日を設定する。
	 *
	 * @param syukaDt 出荷日
	 */
	public void setSyukaDt(String SyukaDt) {
		this.syukaDt = syukaDt;
	}

	/**
	 * 受入日付を取得する。
	 *
	 * @return 受入日付
	 */
	public String getUkeireDt() {
		return this.ukeireDt;
	}

	/**
	 * 受入日付を設定する。
	 *
	 * @param syukaDt 受入日付
	 */
	public void setUkeireDt(String UkeireDt) {
		this.ukeireDt = ukeireDt;
	}

	/**
	 * 開始期間を取得する。
	 *
	 * @return 開始期間
	 */
	public String getKikanStart() {
		return this.kikanStart;
	}

	/**
	 * 開始期間を設定する。
	 *
	 * @param kikanStart 開始期間
	 */
	public void setKikanStart(String kikanStart) {
		this.kikanStart = kikanStart;
	}


	/**
	 * 終了期間を取得する。
	 *
	 * @return 終了期間
	 */
	public String getKikanEnd() {
		return this.kikanEnd;
	}

	/**
	 * 終了期間を設定する。
	 *
	 * @param kikanEnd 終了期間
	 */
	public void setKikanEnd(String kikanEnd) {
		this.kikanEnd = kikanEnd;
	}




	/**
	 * 期間年を取得する。
	 *
	 * @return 期間年
	 */
	public String getKikanNen() {
		return this.kikanNen;
	}

	/**
	 * 期間年を設定する。
	 *
	 * @param kikanNen 期間年
	 */
	public void setKikanNen(String kikanNen) {
		this.kikanNen = kikanNen;
	}


	/**
	 * データ区分を取得する。
	 *
	 * @return データ区分
	 */
	public String getKbnData() {
		return this.kbnData;
	}

	/**
	 * データ区分を設定する。
	 *
	 * @param kbnData データ区分
	 */
	public void setKbnData(String kbnData) {
		this.kbnData = kbnData;
	}


	/**
	 * 集計単位を取得する。
	 *
	 * @return 集計単位
	 */
	public String getSyuukeiTani() {
		return this.syuukeiTani;
	}

	/**
	 * 集計単位を設定する。
	 *
	 * @param syuukeiTani 集計単位
	 */
	public void setSyuukeiTani(String syuukeiTani) {
		this.syuukeiTani = syuukeiTani;
	}


	/**
	 * 事業所区分を取得する。
	 *
	 * @return 事業所区分
	 */
	public String getJigyosyoCd() {

		return this.jigyosyoCd;
	}

	/**
	 * 事業所区分を設定する。
	 *
	 * @param jigyosyoCd
	 *            事業所区分
	 */
	public void setJigyosyoCd(String jigyosyoCd) {

		this.jigyosyoCd = jigyosyoCd;
	}


	/**
	 * 課を取得する。
	 *
	 * @return 課
	 */
	public String getKaCd() {
		return this.kaCd;
	}

	/**
	 * 課を設定する。
	 *
	 * @param kaCd 課
	 */
	public void setKaCd(String kaCd) {
		this.kaCd = kaCd;
	}


	/**
	 * 担当者を取得する。
	 *
	 * @return 担当者
	 */
	public String getTantosyaCd() {
		return this.tantosyaCd;
	}

	/**
	 * 担当者を設定する。
	 *
	 * @param tantosyaCd 担当者
	 */
	public void setTantosyaCd(String tantosyaCd) {
		this.tantosyaCd = tantosyaCd;
	}


	/**
	 * 都道府県を取得する。
	 *
	 * @return 都道府県
	 */
	public String getCountryCd() {
		return this.countryCd;
	}

	/**
	 * 都道府県を設定する。
	 *
	 * @param countryCd 都道府県
	 */
	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}

	/**
	 * JISCDを取得する。
	 *
	 * @return JISCD
	 */
	public String getJisCd() {
		return this.jisCd;
	}

	/**
	 * JISCDを設定する。
	 *
	 * @param jisCd JISCD
	 */
	public void setJisCd(String jisCd) {
		this.jisCd = jisCd;
	}


	/**
	 * 輸出国を取得する。
	 *
	 * @return 輸出国
	 */
	public String getSyukaSakiCountryCd() {
		return this.syukaSakiCountryCd;
	}

	/**
	 * 輸出国を設定する。
	 *
	 * @param syukaSakiCountryCd 輸出国
	 */
	public void setSyukaSakiCountryCd(String syukaSakiCountryCd) {
		this.syukaSakiCountryCd = syukaSakiCountryCd;
	}


	/**
	 * 全国卸を取得する。
	 *
	 * @return 全国卸
	 */
	public String getZenkokuOrosi() {
		return this.zenkokuOrosi;
	}

	/**
	 * 全国卸を設定する。
	 *
	 * @param zenkokuOrosi 全国卸
	 */
	public void setZenkokuOrosi(String zenkokuOrosi) {
		this.zenkokuOrosi = zenkokuOrosi;
	}

	/**
	 * 販売実績集計CDを取得する。
	 *
	 * @return 販売実績集計CD
	 */
	public String getHjisekiSyukeiCd() {
		return this.hjisekiSyukeiCd;
	}

	/**
	 * 販売実績集計CDを設定する。
	 *
	 * @param hjisekiSyukeiCd 販売実績集計CD
	 */
	public void setHjisekiSyukeiCd(String hjisekiSyukeiCd) {
		this.hjisekiSyukeiCd = hjisekiSyukeiCd;
	}


	/**
	 * 特約店コードを取得する。
	 *
	 * @return 特約店コード
	 */
	public String getTokuyakutenCd() {

		return this.tokuyakutenCd;
	}


	/**
	 * 特約店コードを設定する。
	 *
	 * @param tokuyakutenCd
	 *           特約店コード
	 */
	public void setTokuyakutenCd(String tokuyakutenCd) {

		this.tokuyakutenCd = tokuyakutenCd;
	}


	/**
	 * 特約店フラグを取得する。
	 *
	 * @return 特約店フラグ
	 */
	public String getTokuyakutenFlg() {
		return this.tokuyakutenFlg;
	}

	/**
	 * 特約店フラグを設定する。
	 *
	 * @param tokuyakutenFlg 特約店フラグ
	 */
	public void setTokuyakutenFlg(String tokuyakutenFlg) {
		this.tokuyakutenFlg = tokuyakutenFlg;
	}

	/**
	 * 卸店を取得する。
	 *
	 * @return 卸店
	 */
	public String getOrositenCd() {
		return this.orositenCd;
	}

	/**
	 * 卸店を設定する。
	 *
	 * @param orositenCd 卸店
	 */
	public void setOrositenCd(String orositenCd) {
		this.orositenCd = orositenCd;
	}


	/**
	 * 最終送荷先卸を取得する。
	 *
	 * @return 最終送荷先卸
	 */
	public String getOrositenCdLast() {
		return this.orositenCdLast;
	}

	/**
	 * 最終送荷先卸を設定する。
	 *
	 * @param orositenCdLast 最終送荷先卸
	 */
	public void setOrositenCdLast(String orositenCdLast) {
		this.orositenCdLast = orositenCdLast;
	}


	/**
	 * 縦線CDを取得する。
	 *
	 * @return 縦線CD
	 */
	public String getTatesnCd() {
		return this.tatesnCd;
	}

	/**
	 * 縦線CDを設定する。
	 *
	 * @param tatesnCdt 縦線CD
	 */
	public void setTatesnCd(String tatesnCd) {
		this.tatesnCd = tatesnCd;
	}



	/**
	 * 【固有選択リスト】期間を取得、設定する。
	 *
	 * @return selNen
	 */

	public String getSelNen() {
		return this.selNen;
	}

	public void setSelNen(String selNen) {
		this.selNen = selNen;
	}

	/**
	 * 【固有選択リスト】データを取得、設定する。
	 *
	 * @return selData
	 */

	public String getSelData() {
		return this.selData;
	}

	public void setSelData(String selData) {
		this.selData = selData;
	}

	/**
	 * 【固有選択リスト】集計単位を取得、設定する。
	 *
	 * @return selSyukei
	 */

	public String getSelSyukei() {
		return this.selSyukei;
	}

	public void setSelSyukei(String selSyukei) {
		this.selSyukei = selSyukei;
	}



	/**
	 * ページ内最大行数を取得する。
	 *
	 * @return ページ内最大行数
	 */
	@Override
	public int getMaxSu() {

		String value = PbsUtil.getMessageResourceString("com.mastrSiiresaki.MaxSu");

		if (value == null) {
			value = PbsUtil.getMessageResourceString("com.maxSu");
		}

		return Integer.parseInt(value);
	}

	/**
	 * リセット処理をする。
	 *
	 * @param mapping
	 *            ActionMapping
	 * @param request
	 *            HttpServletRequest
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		this.syukaDt= null;  // 出荷日
		this.ukeireDt= null;  // 受入日付
		this.kikanStart= null;  // 期間　開始
		this.kikanEnd= null;    // 期間　終了
		this.kikanNen= null;    // 期間　当年・当年前年
		this.kbnData= null;     // データ区分
		this.syuukeiTani= null; // 集計単位
		this.jigyosyoCd= null;  // 事業所区分
		this.kaCd= null;        // 課区分
		this.tantosyaCd= null;     // 担当区分
		this.countryCd= null;   // 都道府県
		this.syukaSakiCountryCd= null; // 出荷先国
		this.zenkokuOrosi= null;       // 全国卸
		this.hjisekiSyukeiCd= null;	//販売実績集計CD
		this.tokuyakutenCd= null;       // 特約店
		this.orositenCd= null;          // 卸店
		this.orositenCdLast= null;      // 最終送荷先
		this.tatesnCd= null;      // 縦線CD
		this.tokuyakutenFlg = null; // 特約店フラグ

	}


	/**
	 * 検索部の表示を初期化する 選択リストの初期値セットする
	 */
	public void init() {
		// 修正ボタンを不活性化する
		//this.setEditBtnAvailFlg(BUTTON_DISABLED_FALSE);

		// 必要に応じて検索フォームに初期値を設定する
		this.setKikanNen("当年のみ");	// 期間　当年のみ/当年前年
		this.setKbnData("出荷");	    // データ区分
		this.setSyuukeiTani("金額");	    // 集計単位


		// リクエストタイプを初期化
		this.setReqType(REQ_TYPE_EMPTY);

	}



}
