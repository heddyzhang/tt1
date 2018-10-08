package kit.juchu.JuchuAddOnly;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static fb.inf.IKitConst.*;

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
public class JuchuJuchuAddOnly_EditForm extends KitEditForm {

	/** シリアルID */
	private static final long serialVersionUID = 7320903403860027301L;

	/**
	 * コンストラクター
	 */
	public JuchuJuchuAddOnly_EditForm() {
		super();
	}


	// ===========================================================
	// 編集フォームに明細部の入力内容と記述する。
	// ===========================================================
	private JuchuJuchuAddOnlyList recordList = new JuchuJuchuAddOnlyList(); // 受注データ／ヘッダー部
	private JuchuJuchuAddOnlyDtList recordDtList = new JuchuJuchuAddOnlyDtList(); // 受注データ／ディテール部
	private JuchuJuchuAddOnlyCatList recordCatList = new JuchuJuchuAddOnlyCatList(); // 予定出荷先別商品カテゴリデータ

	// 画面固有インスタンス変数
	private String jyucyuNo; // 受注NO
	private String jyucyuNoClass; // 受注NOフラグ


	//	-------------------------------------------------------------
	//	EditFnctionで使うためにLISTしておく
	//	-------------------------------------------------------------
	/**
	 * 受付専用受注追加入力（受注データ／ヘッダー部）用
	 * 編集リストを取得する。
	 *
	 * @return 編集リスト
	 */
	public JuchuJuchuAddOnlyList getJuchuJuchuAddOnlyList() {
		return recordList;
	}

	/**
	 * 受付専用受注追加入力（受注データ／ディテール部）用
	 * 編集リストを取得する。
	 *
	 * @return 編集リスト
	 */
	public JuchuJuchuAddOnlyDtList getJuchuJuchuAddOnlyDtList() {
		return recordDtList;
	}

	/**
	 * 受付専用受注追加入力（予定出荷先別商品カテゴリデータ）用
	 * 編集リストを取得する。
	 *
	 * @return 編集リスト
	 */
	public JuchuJuchuAddOnlyCatList getJuchuJuchuAddOnlyCatList() {
		return recordCatList;
	}

	/**
	 * リセット処理をする。
	 *
	 * @param mapping ActionMapping
	 * @param request HttpServletRequest
	 */
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		recordList = new JuchuJuchuAddOnlyList(); // 受注データ／ヘッダー部
		recordDtList = new JuchuJuchuAddOnlyDtList(); // 受注データ／ディテール部
		recordCatList = new JuchuJuchuAddOnlyCatList(); // 予定出荷先別商品カテゴリデータ
		this.jyucyuNo = null; // 受注NO
		this.jyucyuNoClass = null; // 受注NOフラグ
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
		String value = PbsUtil.getMessageResourceString("com.juchuJuchuAddOnly.maxSu");

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
	 * 受付専用受注追加入力（受注データ／ヘッダー部）用
	 * 指定INDEXのレコードを取得する。<br/>
	 * TIPS:&lt;kit:iterate&gt;のid属性に対応するアクセサ名とする
	 *
	 * @param index paramName
	 * @return レコード
	 */
	public JuchuJuchuAddOnlyRecord getJuchuJuchuAddOnlyRecord(int index) {

		if (recordList.size() == 0) {
			for (int i = 0; i < getMaxSu(); i++) {
				recordList.add(new JuchuJuchuAddOnlyRecord());
			}
		}

		return ((JuchuJuchuAddOnlyRecord) recordList.get(index));
	}

	/**
	 * 受付専用受注追加入力（受注データ／ディテール部）用
	 * 指定INDEXのレコードを取得する。<br/>
	 * TIPS:&lt;kit:iterate&gt;のid属性に対応するアクセサ名とする
	 *
	 * @param index paramName
	 * @return レコード
	 */
	public JuchuJuchuAddOnlyDtRecord getJuchuJuchuAddOnlyDtRecord(int index) {

		if (recordDtList.size() == 0) {
			for (int i = 0; i < getMaxSuDt(); i++) {
				recordDtList.add(new JuchuJuchuAddOnlyDtRecord());
			}
		}

		return ((JuchuJuchuAddOnlyDtRecord) recordDtList.get(index));
	}

	/**
	 * 受付専用受注追加入力（予定出荷先別商品カテゴリデータ）用
	 * 指定INDEXのレコードを取得する。<br/>
	 * TIPS:&lt;kit:iterate&gt;のid属性に対応するアクセサ名とする
	 *
	 * @param index paramName
	 * @return レコード
	 */
	public JuchuJuchuAddOnlyCatRecord getJuchuJuchuAddOnlyCatRecord(int index) {

		if (recordCatList.size() == 0) {
			for (int i = 0; i < getMaxSuDt(); i++) {
				recordCatList.add(new JuchuJuchuAddOnlyCatRecord());
			}
		}

		return ((JuchuJuchuAddOnlyCatRecord) recordCatList.get(index));
	}


	/**
	 * 入力項目のフラグを初期化する。
	 */
	public void initClasses() {
		setJyucyuNoClass(STYLE_CLASS_INIT); // 受注NOフラグ
	}

	//	-------------------------------------------------------------
	//	アクセサを記述する(画面固有のフィールドがあれば)
	//	-------------------------------------------------------------
	// 画面固有アクセサメソッド
	/**
	 * 受注NOを取得する。
	 *
	 * @return 受注NO
	 */
	public String getJyucyuNo() {
		return this.jyucyuNo;
	}

	/**
	 * 受注NOを設定する。
	 *
	 * @param jyucyuNo 受注NO
	 */
	public void setJyucyuNo(String jyucyuNo) {
		this.jyucyuNo = jyucyuNo;
	}


	/**
	 * 受注NOフラグを取得する。
	 *
	 * @return 受注NOフラグ
	 */
	public String getJyucyuNoClass() {
		return this.jyucyuNoClass;
	}

	/**
	 * 受注NOフラグを設定する。
	 *
	 * @param jyucyuNoClass 受注NOフラグ
	 */
	public void setJyucyuNoClass(String jyucyuNoClass) {
		this.jyucyuNoClass = jyucyuNoClass;
	}


	/**
	 * 選択したレコードの削除フラグに応じて編集フォームのフラグを設定する
	 *
	 * @param JuchuJuchuAddOnlyRecord
	 *            選択したレコード
	 */
	public void setDeletedRecord(JuchuJuchuAddOnlyRecord selectedRec) {

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
