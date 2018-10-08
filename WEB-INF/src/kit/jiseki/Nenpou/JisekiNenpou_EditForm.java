package kit.jiseki.Nenpou;

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
 *
 * @author nishikawa@kz
 * @version 2015/11/16
 *
 */
public class JisekiNenpou_EditForm extends KitEditForm {

	/**
	 * コンストラクター
	 */
	public JisekiNenpou_EditForm() {
		super();
	}
	/** シリアルID */
	private static final long serialVersionUID = -1647030736544832349L;

	// ===========================================================
	// 編集フォームに明細ヘッダ部と明細部の入力内容と記述する。
	// ===========================================================
	/** 年報リスト */
	private JisekiNenpouList recordList = new JisekiNenpouList();

	//画面固有インスタンス変数
	private String kikanMonth; // 表示月数
	private String[] monthItem; // 表示項目名


	//	-------------------------------------------------------------
	//	XXX:EditFunctionで使うためにLISTしておく
	//	-------------------------------------------------------------
	/**
	 * 明細部用
	 * 編集リストを取得する。
	 *
	 * @return 編集リスト
	 */
	public JisekiNenpouList getJisekiNenpouList() {
		return recordList;
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
		String value = PbsUtil.getMessageResourceString("com.JisekiNenpou.MaxSu");

		// システムのデフォルト
		if (value == null) {
			value = PbsUtil.getMessageResourceString("com.maxSu");
		}

		// 新規モード
		// ※ この設定をしないと自動的に最大表示件数まで空行が表示される
//		if (PbsUtil.isEqual(getMode(), MODE_ADD)
//			|| (PbsUtil.isEqual(getMode(), MODE_CONFIRM) && PbsUtil.isEqual(getPreMode(), MODE_ADD))) {
//			value = PbsUtil.getMessageResourceString(CST_KEY_ONELINE_MAX_SU);
//		}

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
		// ==================================
		// XXX:フィールド変数に値を初期化
		// XXX:フィールドを追加したときは、ここにも追加すること！
		// ==================================
		recordList = new JisekiNenpouList();   // 明細部用
		this.kikanMonth = null; // 表示月数
		this.monthItem = null; // 表示項目名
	}



	//	-------------------------------------------------------------
	//	TODO:基盤のためにマッピングしているゲッター
	//	-------------------------------------------------------------



	/**
	 * 明細部用
	 * 指定INDEXのレコードを取得する。<br/>
	 * XXX:TIPS:&lt;kit:iterate&gt;のid属性に対応するアクセサ名とする
	 * @param index
	 *            paramName
	 * @return レコード
	 */
	public JisekiNenpouRecord getJisekiNenpouRecord(int index) {

		if (recordList.size() == 0) {
			for (int i = 0; i < getMaxSu(); i++) {
				recordList.add(new JisekiNenpouRecord());
			}
		}

		return ((JisekiNenpouRecord) recordList.get(index));
	}


	//	-------------------------------------------------------------
	//	XXX:アクセサを記述する(画面固有のフィールドがあれば)
	//	-------------------------------------------------------------

	/**
	 * 表示月数を取得する。
	 *
	 * @return 表示月数
	 */
	public String getKikanMonth() {
		return this.kikanMonth;
	}

	/**
	 * 表示月数を設定する。
	 *
	 * @param kikanMonth 表示月数
	 */
	public void setKikanMonth(String kikanMonth) {
		this.kikanMonth = kikanMonth;
	}

	/**
	 * 表示項目名を取得する。
	 *
	 * @return 表示項目名
	 */
	public String[] getMonthItem() {
		return this.monthItem;
	}

	/**
	 * 表示項目名を設定する。
	 *
	 * @param monthItem 表示項目名
	 */
	public void setMonthItem(String[] monthItem) {
		this.monthItem = monthItem;
	}


}
