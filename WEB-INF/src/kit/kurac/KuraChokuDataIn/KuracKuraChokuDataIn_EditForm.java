package kit.kurac.KuraChokuDataIn;

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
public class KuracKuraChokuDataIn_EditForm extends KitEditForm {

	/**
	 *
	 */

	/** シリアルID */
	private static final long serialVersionUID = 2905037660420847770L;

	/**
	 * コンストラクター
	 */
	public KuracKuraChokuDataIn_EditForm() {
		super();
	}


	// ===========================================================
	// 編集フォームに明細部の入力内容と記述する。
	// ===========================================================
	private KuracKuraChokuDataInList recordList = new KuracKuraChokuDataInList(); // 蔵元データ／ヘッダー部
	private KuracKuraChokuDataInDtList recordDtList = new KuracKuraChokuDataInDtList(); // 蔵元データ／ディテール部

	// 画面固有インスタンス変数
	private String kuradataNo; // 蔵直ﾃﾞｰﾀ連番
	private String kuradataNoClass; // 蔵直ﾃﾞｰﾀ連番フラグ


	//	-------------------------------------------------------------
	//	EditFnctionで使うためにLISTしておく
	//	-------------------------------------------------------------
	/**
	 * 蔵元入力（蔵元データ／ヘッダー部）用
	 * 編集リストを取得する。
	 *
	 * @return 編集リスト
	 */
	public KuracKuraChokuDataInList getKuracKuraChokuDataInList() {
		return recordList;
	}

	/**
	 * 蔵元入力（蔵元データ／ディテール部）用
	 * 編集リストを取得する。
	 *
	 * @return 編集リスト
	 */
	public KuracKuraChokuDataInDtList getKuracKuraChokuDataInDtList() {
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
		recordList = new KuracKuraChokuDataInList(); // 蔵元データ／ヘッダー部
		recordDtList = new KuracKuraChokuDataInDtList(); // 蔵元データ／ディテール部
		this.kuradataNo = null; // 蔵直ﾃﾞｰﾀ連番
		this.kuradataNoClass = null; // 蔵直ﾃﾞｰﾀ連番フラグ
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
		String value = PbsUtil.getMessageResourceString("com.KuracKuraChokuDataInDt.maxSu");

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
	 * 蔵元入力（蔵元データ／ヘッダー部）用
	 * 指定INDEXのレコードを取得する。<br/>
	 * TIPS:&lt;kit:iterate&gt;のid属性に対応するアクセサ名とする
	 *
	 * @param index paramName
	 * @return レコード
	 */
	public KuracKuraChokuDataInRecord getKuracKuraChokuDataInRecord(int index) {

		if (recordList.size() == 0) {
			for (int i = 0; i < getMaxSu(); i++) {
				recordList.add(new KuracKuraChokuDataInRecord());
			}
		}

		return ((KuracKuraChokuDataInRecord) recordList.get(index));
	}

	/**
	 * 蔵元入力（蔵元データ／ディテール部）用
	 * 指定INDEXのレコードを取得する。<br/>
	 * TIPS:&lt;kit:iterate&gt;のid属性に対応するアクセサ名とする
	 *
	 * @param index paramName
	 * @return レコード
	 */
	public KuracKuraChokuDataInDtRecord getKuracKuraChokuDataInDtRecord(int index) {

		if (recordDtList.size() == 0) {
			for (int i = 0; i < getMaxSuDt(); i++) {
				recordDtList.add(new KuracKuraChokuDataInDtRecord());
			}
		}

		return ((KuracKuraChokuDataInDtRecord) recordDtList.get(index));
	}

	/**
	 * 入力項目のフラグを初期化する。
	 */
	public void initClasses() {
		setKuradataNoClass(STYLE_CLASS_INIT); // 蔵直ﾃﾞｰﾀ連番フラグ
	}

	//	-------------------------------------------------------------
	//	アクセサを記述する(画面固有のフィールドがあれば)
	//	-------------------------------------------------------------
	// 画面固有アクセサメソッド
	/**
	 * 蔵直ﾃﾞｰﾀ連番を取得する。
	 *
	 * @return 蔵元NO
	 */
	public String getKuradataNo() {
		return this.kuradataNo;
	}

	/**
	 * 蔵直ﾃﾞｰﾀ連番を設定する。
	 *
	 * @param kuradataNo 蔵直ﾃﾞｰﾀ連番
	 */
	public void setKuradataNo(String kuradataNo) {
		this.kuradataNo = kuradataNo;
	}


	/**
	 * 蔵直ﾃﾞｰﾀ連番フラグを取得する。
	 *
	 * @return 蔵直ﾃﾞｰﾀ連番フラグ
	 */
	public String getKuradataNoClass() {
		return this.kuradataNoClass;
	}

	/**
	 * 蔵直ﾃﾞｰﾀ連番フラグを設定する。
	 *
	 * @param kuradataNoError 蔵直ﾃﾞｰﾀ連番フラグ
	 */
	public void setKuradataNoClass(String kuradataNoClass) {
		this.kuradataNoClass = kuradataNoClass;
	}


	/**
	 * 選択したレコードの削除フラグに応じて編集フォームのフラグを設定する
	 *
	 * @param KuracKuraChokuDataInRecord
	 *            選択したレコード
	 */
	public void setDeletedRecord(KuracKuraChokuDataInRecord selectedRec) {

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
