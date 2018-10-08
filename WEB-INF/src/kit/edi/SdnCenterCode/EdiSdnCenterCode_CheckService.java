package kit.edi.SdnCenterCode;


import static fb.com.IKitComConstHM.*;
import static kit.edi.SdnCenterCode.IEdiSdnCenterCode.*;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComUserSession;
import fb.com.ComValidateUtil;
import fb.inf.KitService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.MaxRowsException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsUtil;

/**
 * 入力内容をチェックするビジネスロジッククラスです
 */
public class EdiSdnCenterCode_CheckService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = -5547428189469817010L;
	/** クラス名. */
	private static String className__ = EdiSdnCenterCode_CheckService.class.getName();
	/** カテゴリ. */
	public static Category category__ = Category.getInstance(className__);

	/** データベースオブジェクト */
	private PbsDatabase db_;

	/** チェックユーティリティー */
	private ComValidateUtil vUtil = null;

	/**
	 * コンストラクタ.<br>ユーティリティーオブジェクトを初期化する。
	 * @param cus ユーザセッション
	 * @param db_ DBオブジェクト
	 * @param isTran トランザクション情報
	 * @param ae アクションエラー情報
	 */
	public EdiSdnCenterCode_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {

		super(cus, db_, isTran, ae);
		// 評価ユーティリティーを初期化する
		this.vUtil = new ComValidateUtil(ae);
		// データベースオブジェクトを初期化する
		this.db_ = db_;

	}


	/**
	 * センタ―コードEDI登録ボタン押下時のチェックを行う(on editForm)
	 *
	 * @param editForm
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateCodeInsert(EdiSdnCenterCode_EditForm editForm) {

		// ==========================================
		// 必須フォーマットチェックを行う。
		// ⇒inputCheckFormatForSearchメソッド呼び出し
		// ==========================================
		if (REQ_TYPE_OROSISENTAEDI.equals(editForm.getReqType())){

			String msgKey_itemNm = "com.text.ediCenterCode";

			// センタコード
			String data = editForm.getOrosiCenterCode();

			// 基本チェック（必須チェック含む）
			if (!vUtil.validateCodeSimple(data, CODE_LEN_OROSITEN, msgKey_itemNm)) {
				return false;
			}

			// 重複チェック
			if (isExistCenerCode(data)) {
				// エラーメッセージ
				setErrorMessageId("errors.duplicationCd", PbsUtil.getMessageResourceString(msgKey_itemNm));

				return false;
			}
		}

		return true;
	}


	/**
	 * 卸店センターコードが存在するかどうか
	 * @param data
	 * @return
	 */
	public boolean isExistCenerCode(String data) {

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT                                   \n");
		sb.append("          CENTER_CD                      \n");
		sb.append("  FROM                                   \n");
		sb.append("          W_EDI_CENTER_CODE              \n");
		sb.append(" WHERE                                   \n");
		sb.append("          CENTER_CD          = ?         \n");

		int iCc = db_.prepare(true, sb.toString());
		int ii = 0;

		db_.setString(iCc, ++ii, data);

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db_.execute(iCc);

		try {
			// 検索処理
			db_.getPbsRecords(iCc, false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {
			return false;

		}

		return true;

	}

} // -- class