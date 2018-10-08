package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConst.*;
import static fb.inf.IKitConst.*;
import static kit.juchu.TuminiDenpyoHako.IJuchuTuminiDenpyoHako.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import fb.inf.KitEditForm;
import fb.inf.pbs.PbsUtil;

/**
 * 明細部の構造を提供するデータオブジェクトクラスです<br/>
 *
 * KzPoint / 拡張先をKZEditFormWrapとする <br/>
 * ※ ソースの整理が進めば、KZEditFormに統合することも可
 */
public class JuchuTuminiDenpyoHako_EditForm extends KitEditForm {

	/** シリアルID */
	private static final long serialVersionUID = -5581080605225647839L;

	/**
	 * コンストラクター
	 */
	public JuchuTuminiDenpyoHako_EditForm() {
		super();
	}


	// ===========================================================
	// 編集フォームに明細部の入力内容と記述する。
	// ===========================================================
	private JuchuTuminiDenpyoHakoList recordList = new JuchuTuminiDenpyoHakoList(); 					// 受注データ／HD部
	private JuchuTuminiDenpyoHakoHdList recordHdList = new JuchuTuminiDenpyoHakoHdList(); 		// 積荷データ／HD部
	private JuchuTuminiDenpyoHakoDtList recordDtList = new JuchuTuminiDenpyoHakoDtList(); 		// 積荷データ／DT部

	// 画面固有インスタンス変数
	private String printerId; // プリンター選択
	private String printerIdClass; // プリンター選択フラグ

	//	-------------------------------------------------------------
	//	EditFnctionで使うためにLISTしておく
	//	-------------------------------------------------------------
	/**
	 * 受注データ／HD部用
	 * 編集リストを取得する。
	 *
	 * @return 編集リスト
	 */
	public JuchuTuminiDenpyoHakoList getJuchuTuminiDenpyoHakoList() {
		return recordList;
	}

	/**
	 * 積荷データ／HD部用
	 * 編集リストを取得する。
	 *
	 * @return 編集リスト
	 */
	public JuchuTuminiDenpyoHakoHdList getJuchuTuminiDenpyoHakoHdList() {
		return recordHdList;
	}

	/**
	 * 積荷データ／DT部用
	 * 編集リストを取得する。
	 *
	 * @return 編集リスト
	 */
	public JuchuTuminiDenpyoHakoDtList getJuchuTuminiDenpyoHakoDtList() {
		return recordDtList;
	}

	/**
	 * リセット処理をする。
	 *
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		recordList = new JuchuTuminiDenpyoHakoList(); 			// 受注データ／HD部
		recordHdList = new JuchuTuminiDenpyoHakoHdList(); 	// 積荷データ／HD部
		recordDtList = new JuchuTuminiDenpyoHakoDtList(); 		// 積荷データ／DT部
		printerId = null; 															// プリンター選択
		printerIdClass = null; 													// プリンター選択フラグ
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
		String value = PbsUtil.getMessageResourceString(KEY_MAX_SU);

		// システムのデフォルト
		if (value == null) {
			value = PbsUtil.getMessageResourceString("com.maxSu");
		}

		return Integer.parseInt(value);
	}


	//	-------------------------------------------------------------
	//	基盤のためにマッピングしているゲッター
	//	-------------------------------------------------------------

	/**
	 * 受注データ／HD部用
	 * 指定INDEXのレコードを取得する。<br/>
	 * TIPS:&lt;kit:iterate&gt;のid属性に対応するアクセサ名とする
	 *
	 * @param index paramName
	 * @return レコード
	 */
	public JuchuTuminiDenpyoHakoRecord getJuchuTuminiDenpyoHakoRecord(int index) {

		if (recordList.size() == 0) {
			for (int i = 0; i < getMaxSu(); i++) {
				recordList.add(new JuchuTuminiDenpyoHakoRecord());
			}
		}

		return ((JuchuTuminiDenpyoHakoRecord) recordList.get(index));
	}


	/**
	 * 積荷データ／ヘッダー部用
	 * 指定INDEXのレコードを取得する。<br/>
	 * TIPS:&lt;kit:iterate&gt;のid属性に対応するアクセサ名とする
	 *
	 * @param index paramName
	 * @return レコード
	 */
	public JuchuTuminiDenpyoHakoHdRecord getJuchuTuminiDenpyoHakoHdRecord(int index) {

		if (recordHdList.size() == 0) {
			for (int i = 0; i < getMaxSu(); i++) {
				recordHdList.add(new JuchuTuminiDenpyoHakoHdRecord());
			}
		}

		return ((JuchuTuminiDenpyoHakoHdRecord) recordHdList.get(index));
	}


	/**
	 * 積荷データ／ディテール部用
	 * 指定INDEXのレコードを取得する。<br/>
	 * TIPS:&lt;kit:iterate&gt;のid属性に対応するアクセサ名とする
	 *
	 * @param index paramName
	 * @return レコード
	 */
	public JuchuTuminiDenpyoHakoDtRecord getJuchuTuminiDenpyoHakoDtRecord(int index) {

		if (recordDtList.size() == 0) {
			for (int i = 0; i < getMaxSu(); i++) {
				recordDtList.add(new JuchuTuminiDenpyoHakoDtRecord());
			}
		}

		return ((JuchuTuminiDenpyoHakoDtRecord) recordDtList.get(index));
	}


	/**
	 * 入力項目のフラグを初期化する。
	 */
	public void initClasses() {
	}

	//	-------------------------------------------------------------
	//	アクセサを記述する(画面固有のフィールドがあれば)
	//	-------------------------------------------------------------
	/**
	 * プリンター選択を取得する。
	 *
	 * @return プリンター選択
	 */
	public String getPrinterId() {
		return this.printerId;
	}

	/**
	 * プリンター選択を設定する。
	 *
	 * @param printerId プリンター選択
	 */
	public void setPrinterId(String printerId) {
		this.printerId = printerId;
	}


	/**
	 * プリンター選択フラグを取得する。
	 *
	 * @return プリンター選択フラグ
	 */
	public String getPrinterIdClass() {
		return this.printerIdClass;
	}

	/**
	 * プリンター選択フラグを設定する。
	 *
	 * @param printerIdClass プリンター選択フラグ
	 */
	public void setPrinterIdClass(String printerIdClass) {
		this.printerIdClass = printerIdClass;
	}





	/**
	 * 選択したレコードの削除フラグに応じて編集フォームのフラグを設定する
	 *
	 * @param JuchuTuminiDenpyoHakoRecord
	 *            選択したレコード
	 */
	public void setDeletedRecord(JuchuTuminiDenpyoHakoRecord selectedRec) {

		boolean isDeletedFlg = false;
		if (PbsUtil.isEqual(DELETED_KB_FLG_DELETED, selectedRec.getDeleteFlg())) {
			isDeletedFlg = true;
		}
		setDeletedRecord(isDeletedFlg);

	}


	/**
	 * 削除済みフラグの表示非表示フラグを取得する
	 */

	public boolean getIsDeletedFlgVisible() {
		boolean isDeletedFlgVisible = false;

		switch (getMode()) {
		case MODE_SEARCH:
		case MODE_SELECTED:
		case MODE_DELETE:
		case MODE_REBIRTH:
			isDeletedFlgVisible = true;
			break;
		case MODE_CONFIRM:
			if(PbsUtil.isIncluded(getPreMode(), MODE_REBIRTH, MODE_DELETE)){
				isDeletedFlgVisible = true;
			}
			break;
		default:
			break;
		}

		return isDeletedFlgVisible;
	}


}
