package kit.edi.EdiJuchuMainte;

import static fb.com.IKitComConstHM.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import fb.inf.KitSearchForm;

public class EdiJuchuMainte_SearchForm extends KitSearchForm {

	/** シリアルID */
	private static final long serialVersionUID = 8050209305150049245L;

	// 画面固有インスタンス変数
	private String jyusinDt; // 受信日
	private String jyusinTmFrom; // 受信時間（from）
	private String jyusinTmTo; // 受信時間（to）
	private String syoriKbn; // 処理区分


	// 画面固有アクセサメソッド
	/**
	 * 受信日を取得する。
	 *
	 * @return 受信日
	 */
	public String getJyusinDt() {
		return this.jyusinDt;
	}

	/**
	 * 受信日を設定する。
	 *
	 * @param jyusinDt 受信日
	 */
	public void setJyusinDt(String jyusinDt) {
		this.jyusinDt = jyusinDt;
	}


	/**
	 * 受信時間（from）を取得する。
	 *
	 * @return 受信時間（from）
	 */
	public String getJyusinTmFrom() {
		return this.jyusinTmFrom;
	}

	/**
	 * 受信時間（from）を設定する。
	 *
	 * @param jyusinTmFrom 受信時間（from）
	 */
	public void setJyusinTmFrom(String jyusinTmFrom) {
		this.jyusinTmFrom = jyusinTmFrom;
	}


	/**
	 * 受信時間（to）を取得する。
	 *
	 * @return 受信時間（to）
	 */
	public String getJyusinTmTo() {
		return this.jyusinTmTo;
	}

	/**
	 * 受信時間（to）を設定する。
	 *
	 * @param jyusinTmTo 受信時間（to）
	 */
	public void setJyusinTmTo(String jyusinTmTo) {
		this.jyusinTmTo = jyusinTmTo;
	}


	/**
	 * 処理区分を取得する。
	 *
	 * @return 処理区分
	 */
	public String getSyoriKbn() {
		return this.syoriKbn;
	}

	/**
	 * 処理区分を設定する。
	 *
	 * @param syoriKbn 処理区分
	 */
	public void setSyoriKbn(String syoriKbn) {
		this.syoriKbn = syoriKbn;
	}


	/**
	 * リセット処理をする。
	 *
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
//		this.jyusinDt = null; // 受信日
//		this.jyusinTmFrom = null; // 受信時間（from）
//		this.jyusinTmTo = null; // 受信時間（to）
//		this.syoriKbn = null; // 処理区分
	}

	/**
	 * エラー、警告等表示用フラグ初期化
	 */
	public void initClasses(){
		// TODO
	}

	/**
	 * 検索部の表示を初期化する
	 */
	public void initialize() {
		this.setCsvBtnFlg(BUTTON_DISABLED_FALSE); // ファイル出力ボタンを非活性化
	}

	/**
	 * ファイル出力（CSV）ボタンを活性化、非活性化設定
	 * @param csvBtnAvailFlg
	 */
	public void setCsvBtnFlg(String csvBtnAvailFlg) {
		this.setCsvBtnAvailFlg(csvBtnAvailFlg);		// ファイル出力（CSV）ボタン
		this.setExcelBtnAvailFlg(csvBtnAvailFlg);	// ファイル出力（EXCEL）ボタン
	}
}
