package kit.kurac.KuraChokuDataIn;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import fb.inf.KitSearchForm;
import fb.inf.pbs.PbsUtil;

/**
 * 検索部の構造を提供するデータオブジェクトクラスです
 */
public class KuracKuraChokuDataIn_SearchForm extends KitSearchForm {

	/**
	 *
	 */
	/**
	 *
	 */
	/** シリアルID */
	private static final long serialVersionUID = -8235172239302269523L;

	// -----------------------------------------
	// 画面固有のインスタンス変数・アクセサメソッドを用意してください。
	// また、resetメソッドにリセット処理を記述します。
	// -----------------------------------------
	// 画面固有インスタンス変数

	private String riyouKbn; 			// 利用区分
	private String syubetuCd;  			// ﾃﾞｰﾀ種別CD
	private String makerNo;  			// メーカNo
	private String sounisakiNm;			// 送荷先
	private String syuhantenNm;			// 酒販店名
	private String irainusi;			// 依頼主
	private String mousikomibi;			// 申込日
	private String jigyousyo;			// 事業所
	private String shohinGrp;			// 商品GRP
	private String syuhantenCd;  		// 酒販店CD
	private String syuhantenCdError;	// 酒販店CD error
	private String tdksk;  				// 届け先
	private String hasoyoteibi;			// 発送予定日
	private String miJyucyuOnly;		// 未受注分のみ(on)
	private String jigyousyoClass; 		// 事業所フラグ
	private String shohinGrpClass; 		// 商品GRPフラグ
	private String mousikomibiClass; 	// 申込日フラグ
	private String hasoyoteibiClass; 	// 発送予定日フラグ

	/** 訂正(+)ボタン仕様可否フラグ */
	private String refplusBtnAvailFlg = BUTTON_AVAILABLE_TRUE;
	/** 訂正(-)ボタン仕様可否フラグ */
	private String refminusBtnAvailFlg = BUTTON_AVAILABLE_TRUE;

	/**
	 * 訂正(+)ボタンの使用可否を取得する
	 * @return referrerBtnAvailFlg
	 */
	public String getRefplusBtnAvailFlg() {
		return refplusBtnAvailFlg;
	}


	/**
	 * 訂正(+)ボタンの使用可否を設定する
	 * @param referrerBtnAvailFlg セットする referrerBtnAvailFlg
	 */
	public void setRefplusBtnAvailFlg(String refplusBtnAvailFlg) {
		this.refplusBtnAvailFlg = refplusBtnAvailFlg;
	}


	/**
	 * 訂正(-)ボタンの使用可否を取得する
	 * @return refminusBtnAvailFlg
	 */
	public String getRefminusBtnAvailFlg() {
		return refminusBtnAvailFlg;
	}


	/**
	 * 訂正(-)ボタンの使用可否を設定する
	 * @param refminusBtnAvailFlg セットする refminusBtnAvailFlg
	 */
	public void setRefminusBtnAvailFlg(String refminusBtnAvailFlg) {
		this.refminusBtnAvailFlg = refminusBtnAvailFlg;
	}


	// 画面固有アクセサメソッド
	/**
	 * 利用区分を取得、設定する。
	 */
	public String getRiyouKbn() {
		return this.riyouKbn;
	}

	public void setRiyouKbn(String riyouKbn) {
		this.riyouKbn = riyouKbn;
	}

	/**
	 * データ種別を取得、設定する。
	 */
	public String getSyubetuCd() {
		return syubetuCd;
	}

	public void setSyubetuCd(String syubetuCd) {
		this.syubetuCd = syubetuCd;
	}


	/**
	 * ﾒｰｶｰNoを取得、設定する。
	 */
	public String getMakerNo() {
		return this.makerNo;
	}

	public void setMakerNo(String makerNo) {
		this.makerNo = makerNo;
	}

	/**
	 * 送荷先を取得、設定する。
	 */
	public String getSounisakiNm() {
		return this.sounisakiNm;
	}

	public void setSounisakiNm(String sounisakiNm) {
		this.sounisakiNm = sounisakiNm;
	}

	/**
	 * 酒販店名を取得、設定する。
	 */
	public String getSyuhantenNm() {
		return this.syuhantenNm;
	}

	public void setSyuhantenNm(String syuhantenNm) {
		this.syuhantenNm = syuhantenNm;
	}

	/**
	 * 申込日を取得、設定する。
	 */
	public String getMousikomibi() {
		return this.mousikomibi;
	}

	public void setMousikomibi(String mousikomibi) {
		this.mousikomibi = mousikomibi;
	}

	/**
	 * 事業所を取得、設定する。
	 */
	public String getJigyousyo() {
		return this.jigyousyo;
	}

	public void setJigyousyo(String jigyousyo) {
		this.jigyousyo = jigyousyo;
	}

	/**
	 * 商品GRPを取得、設定する。
	 */
	public String getShohinGrp() {
		return this.shohinGrp;
	}

	public void setShohinGrp(String shohinGrp) {
		this.shohinGrp = shohinGrp;
	}

	/**
	 * 酒販店を取得、設定する。
	 */
	public String getSyuhantenCd() {
		return this.syuhantenCd;
	}

	public void setSyuhantenCd(String syuhantenCd) {
		this.syuhantenCd = syuhantenCd;
	}

	/**
	 * 酒販店errorを取得、設定する。
	 */
	public String getSyuhantenCdError() {
		return this.syuhantenCdError;
	}

	public void setSyuhantenCdError(String syuhantenCdError) {
		this.syuhantenCdError = syuhantenCdError;
	}

	/**
	 * 依頼主を取得、設定する。
	 */
	public String getIrainusi() {
		return this.irainusi;
	}

	public void setIrainusi(String irainusi) {
		this.irainusi = irainusi;
	}

	/**
	 * 届け先を取得、設定する。
	 */
	public String getTdksk() {
		return this.tdksk;
	}

	public void setTdksk(String tdksk) {
		this.tdksk = tdksk;
	}

	/**
	 * 発送予定日を取得、設定する。
	 */
	public String getHasoyoteibi() {
		return this.hasoyoteibi;
	}

	public void setHasoyoteibi(String hasoyoteibi) {
		this.hasoyoteibi = hasoyoteibi;
	}

	/**
	 * 未受注分のみを取得、設定する。
	 */
	public String getMiJyucyuOnly() {
		return this.miJyucyuOnly;
	}

	public void setMiJyucyuOnly(String miJyucyuOnly) {
		this.miJyucyuOnly = miJyucyuOnly;
	}


	/**
	 * 事業所フラグを取得する。
	 *
	 * @return 事業所フラグ
	 */
	public String getJigyousyoClass() {
		return jigyousyoClass;
	}

	/**
	 * 事業所フラグを設定する。
	 *
	 * @param jigyousyoClass 事業所フラグ
	 */
	public void setJigyousyoClass(String jigyousyoClass) {
		this.jigyousyoClass = jigyousyoClass;
	}

	/**
	 * 商品GRPフラグフラグを取得する。
	 *
	 * @return 商品GRPフラグ
	 */
	public String getShohinGrpClass() {
		return shohinGrpClass;
	}

	/**
	 * 事業所フラグを設定する。
	 *
	 * @param jigyousyoClass 事業所フラグ
	 */
	public void setShohinGrpClass(String shohinGrpClass) {
		this.shohinGrpClass = shohinGrpClass;
	}

	/**
	 *  申込日フラグを取得する。
	 *
	 * @return  申込日フラグ
	 */
	public String getMousikomibiClass() {
		return mousikomibiClass;
	}

	/**
	 * 申込日フラグを設定する。
	 *
	 * @param mousikomibiClass 申込日フラグ
	 */
	public void setMousikomibiClass(String mousikomibiClass) {
		this.mousikomibiClass = mousikomibiClass;
	}

	/**
	 * 発送予定日フラグを取得する。
	 *
	 * @return 発送予定日フラグ
	 */
	public String getHasoyoteibiClass() {
		return hasoyoteibiClass;
	}

	/**
	 * 発送予定日フラグを設定する。
	 *
	 * @param hasoyoteibiClass 事発送予定日フラグ
	 */
	public void setHasoyoteibiClass(String hasoyoteibiClass) {
		this.hasoyoteibiClass = hasoyoteibiClass;
	}

	/**
	 * リセット処理をする。
	 *
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.riyouKbn = null;		// 利用区分
		this.makerNo = null;		// ﾒｰｶｰ
		this.sounisakiNm = null;	// 送荷先
		this.syuhantenNm = null;	// 酒販店名
		this.mousikomibi = null;	// 申込日
		this.jigyousyo = null;		// 事業所
		this.shohinGrp = null; 	// 商品GRP
		this.syuhantenCd = null;	// 酒販店CD
		this.irainusi = null;		// 依頼主
		this.tdksk = null;			// 届け先
		this.hasoyoteibi = null;	// 発送予定日
		this.miJyucyuOnly = null;	// 未受注分のみ(on)
	}

	/**
	 * ページ内最大行数を取得する。
	 *
	 * @return ページ内最大行数
	 */
	@Override
	public int getMaxSu() {

		String value = PbsUtil.getMessageResourceString("com.maxSu");

		return Integer.parseInt(value);
	}


	/**
	 * ページ内最大行数を取得する。
	 *
	 * @return ページ内最大行数
	 */
	public int getMaxSuDt() {

		String value = PbsUtil.getMessageResourceString("com.KuracKuraChokuDataInDt.maxSu");

		if (value == null) {
			value = PbsUtil.getMessageResourceString("com.maxSu");
		}

		return Integer.parseInt(value);
	}

	/**
	 * 検索部の表示を初期化する
	 */
	public void initialize() {
		// 受注のみ
		this.miJyucyuOnly = "on";
		// 利用区分
		this.setRiyouKbn(AVAILABLE_KB_RIYO_KA);
		// 事業所ｺﾝﾎﾞﾎﾞｯｸｽの初期値は小売
		this.setJigyousyo(KURAC_SITEN_KB_KOURI);
		// 商品GRPｺﾝﾎﾞﾎﾞｯｸｽの初期値は通常（夏）
		this.setShohinGrp(KURAC_SHOHINGRP_KB_TUJYO_SUMMER);

	}

	/**
	 * エラー、警告等表示用フラグ初期化
	 */
	public void initClasses(){
		// フラグ初期化
		this.setJigyousyoClass(STYLE_CLASS_INIT);
		this.setShohinGrpClass(STYLE_CLASS_INIT);
		this.setMousikomibiClass(STYLE_CLASS_INIT);
		this.setHasoyoteibiClass(STYLE_CLASS_INIT);
	}

}
