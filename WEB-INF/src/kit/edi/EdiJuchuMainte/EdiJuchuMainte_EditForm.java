package kit.edi.EdiJuchuMainte;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import fb.inf.KitEditForm;
import fb.inf.pbs.PbsUtil;

public class EdiJuchuMainte_EditForm extends KitEditForm {

	/** シリアルID */
	private static final long serialVersionUID = -1630253385057220315L;

	// ===========================================================
	// 編集フォームに明細部の入力内容と記述する。
	// ===========================================================
	private EdiJuchuMainteList recordList = new EdiJuchuMainteList(); // EDI受発注ﾃﾞｰﾀ／ﾍｯﾀﾞｰ部
	private EdiJuchuMainteDtList recordDtList = new EdiJuchuMainteDtList(); // EDI受発注ﾃﾞｰﾀ／ﾃﾞｨﾃｰﾙ部


	// 画面固有インスタンス変数
	private String tEdiJyuhacyuId; // EDI受発注ID
	private String tEdiJyuhacyuIdClass; 	// 入力日クラス

	//	-------------------------------------------------------------
	//	EditFnctionで使うためにLISTしておく
	//	-------------------------------------------------------------
	/**
	 * EDI受発注ﾃﾞｰﾀ処理（DI受発注ﾃﾞｰﾀ／ﾍｯﾀﾞｰ部）用
	 * 編集リストを取得する。
	 *
	 * @return 編集リスト
	 */
	public EdiJuchuMainteList getEdiJuchuMainteList() {
		return recordList;
	}

	/**
	 * EDI受発注ﾃﾞｰﾀ処理（EDI受発注ﾃﾞｰﾀ／ﾃﾞｨﾃｰﾙ部）用
	 * 編集リストを取得する。
	 *
	 * @return 編集リスト
	 */
	public EdiJuchuMainteDtList getEdiJuchuMainteDtList() {
		return recordDtList;
	}

	//	-------------------------------------------------------------
	//	基盤のためにマッピングしているゲッター
	//	-------------------------------------------------------------

	/**
	 * EDI受発注ﾃﾞｰﾀ処理（DI受発注ﾃﾞｰﾀ／ﾍｯﾀﾞｰ部）用
	 * 指定INDEXのレコードを取得する。<br/>
	 * TIPS:&lt;kit:iterate&gt;のid属性に対応するアクセサ名とする
	 *
	 * @param index paramName
	 * @return レコード
	 */
	public EdiJuchuMainteRecord getEdiJuchuMainteRecord(int index) {

		if (recordList.size() == 0) {
			for (int i = 0; i < getMaxSu(); i++) {
				recordList.add(new EdiJuchuMainteRecord());
			}
		}

		return ((EdiJuchuMainteRecord) recordList.get(index));
	}

	/**
	 * EDI受発注ﾃﾞｰﾀ処理（EDI受発注ﾃﾞｰﾀ／ﾃﾞｨﾃｰﾙ部）用
	 * 指定INDEXのレコードを取得する。<br/>
	 * TIPS:&lt;kit:iterate&gt;のid属性に対応するアクセサ名とする
	 *
	 * @param index paramName
	 * @return レコード
	 */
	public EdiJuchuMainteDtRecord getEdiJuchuMainteDtRecord(int index) {

//		if (recordDtList.size() == 0) {
//			for (int i = 0; i < getMaxSuDt(); i++) {
//				recordDtList.add(new EdiJuchuMainteDtRecord());
//			}
//		}

		while(recordDtList.size() < index + 1) {
			recordDtList.add(new EdiJuchuMainteDtRecord());
		}

		return ((EdiJuchuMainteDtRecord) recordDtList.get(index));
	}

	/**
	 * リセット処理をする。
	 *
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.tEdiJyuhacyuId = null; // EDI受発注ID
	}

	/**
	 * ページ内最大行数を取得する。
	 *
	 * @return ページ内最大行数
	 */
	@Override
	public int getMaxSu() {

		// ====================================================
		// 1ページの最大表示件数を指定する。
		// （ApplicationResource.propertiesのキー名を指定）
		// ====================================================
		String value = PbsUtil.getMessageResourceString("com.maxSu");

		return Integer.parseInt(value);
	}

	/**
	 * ページ内最大行数を取得する。
	 *
	 * @return ページ内最大行数
	 */
	public int getMaxSuDt() {

		// ====================================================
		// 1ページの最大表示件数を指定する。
		// （ApplicationResource.propertiesのキー名を指定）
		// ====================================================
		String value = PbsUtil.getMessageResourceString("com.EdiJuchuMainteDt.maxSu");

		// システムのデフォルト
		if (value == null) {
			value = PbsUtil.getMessageResourceString("com.maxSu");
		}

		return Integer.parseInt(value);
	}

	// 画面固有アクセサメソッド
	/**
	 * EDI受発注IDを取得する。
	 *
	 * @return EDI受発注ID
	 */
	public String getEdiJyuhacyuId() {
		return this.tEdiJyuhacyuId;
	}

	/**
	 * EDI受発注IDを設定する。
	 *
	 * @param tEdiJyuhacyuId EDI受発注ID
	 */
	public void setEdiJyuhacyuId(String tEdiJyuhacyuId) {
		this.tEdiJyuhacyuId = tEdiJyuhacyuId;
	}

	/**
	 * IDのエラー、警告表示フラグを取得する
	 * @return IDのエラー、警告表示フラグ
	 */
	public String getEdiJyuhacyuIdClass() {
		return tEdiJyuhacyuIdClass;
	}

	/**
	 * IDのエラー、警告表示フラグを設定する
	 * @param tEdiJyuhacyuIdClass IDのエラー、警告表示フラグ
	 */
	public void setEdiJyuhacyuIdClass(String tEdiJyuhacyuIdClass) {
		this.tEdiJyuhacyuIdClass = tEdiJyuhacyuIdClass;
	}





}
