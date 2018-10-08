package kit.edi.ReinyuAnnai;

import static fb.com.IKitComConst.EDI_SYUKKA_PARTNER_KB_FINET;
import static fb.com.IKitComConst.EDI_SYUKKA_PARTNER_KB_IKKATU;
import static fb.com.IKitComConst.EDI_SYUKKA_PARTNER_KB_SDN;
import static fb.com.IKitComErrorConst.*;
import static kit.edi.ReinyuAnnai.IEdiReinyuAnnai.*;
import static kit.edi.SyukkaAnnai.IEdiSyukkaAnnai.REQ_TYPE_IKKATU;
import static kit.edi.SyukkaAnnai.IEdiSyukkaAnnai.REQ_TYPE_SYUKKAANNAIEDI;
import kit.edi.SyukkaAnnai.EdiSyukkaAnnai_EditForm;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import fb.com.ComUserSession;
import fb.inf.KitService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.MaxRowsException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

public class EdiReinyuAnnai_CheckService  extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = -4737531552227696673L;

	/** クラス名. */
	private static String className__ = EdiReinyuAnnai_CheckService.class.getName();

	/** カテゴリ. */
	public static Category category__ = Category.getInstance(className__);

	/** データベースオブジェクト */
	private PbsDatabase db_;


	/**
	 * コンストラクタ.<br>
	 * ユーティリティーオブジェクトを初期化する。
	 *
	 * @param cus ユーザセッション
	 * @param db_ DBオブジェクト
	 * @param isTran トランザクション情報
	 * @param ae アクションエラー情報
	 */
	public EdiReinyuAnnai_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran,
			ActionErrors ae) {

		super(cus, db_, isTran, ae);
		// データベースオブジェクトを初期化する
		this.db_ = db_;

	}

	/**
	 * 呼出ボタン押下時のチェックを行う(on SearchForm)
	 *
	 * @param editForm
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateSearch(EdiReinyuAnnai_EditForm editForm) {

		// ==================================================
		// 権限チェックを行う。
		// ==================================================
		if (!inputCheckKengen(editForm)) {
			return false;
		}

		// ==================================================
		// 必須チェックを行う。
		// ==================================================
		if (REQ_TYPE_IKKATU.equals(editForm.getReqType())){

			if(!PbsUtil.isEmpty(editForm.getFromDt()) && !PbsUtil.isEmpty(editForm.getToDt())){
				if(editForm.getFromDt().compareTo(editForm.getToDt()) > 0){
					setErrorMessageId(new ActionError(ERRORS_DATE_ERROR, "日付期間指定"));
					return false;
				}
			}

			if(PbsUtil.isEmpty(editForm.getSakuseitaisyouKbn())) {
				setErrorMessageId(new ActionError(ERRORS_MISTAKE, "作成対象"));
				return false;
			}
		}


		if (REQ_TYPE_REINYUANNAIEDI.equals(editForm.getReqType())){

			if(PbsUtil.isEmpty(editForm.getSousinsakiKbn())) {
				setErrorMessageId(new ActionError(ERRORS_MISTAKE, "送信先"));
				return false;
			}

			// 呼出時チェックを実行
			if (!dataUmuSearch(editForm.getSousinsakiKbn())) {
				setErrorMessageId(new ActionError("errors.Select.NoData"));
				return false;
			}
		}

		// ==================================================
		// 属性チェックを行う。
		// ==================================================
		if (!inputCheckForSearch(editForm)) {
			return false;
		}

		return true;
	}



	/**
	 * 呼出時権限チェックを行う
	 *
	 * @param searchForm
	 * @return
	 */
	private boolean inputCheckKengen(EdiReinyuAnnai_EditForm editForm) {
		boolean res = true;

		return res;
	}


	/**
	 * 属性チェックを行う（呼出用）
	 *
	 * @param searchForm
	 * @return true(エラー無) or false(エラー有)
	 */
	private boolean inputCheckForSearch(EdiReinyuAnnai_EditForm editForm) {

		return true;
	}

	/**
	 * 対象データ検索
	 * */
	public Boolean dataUmuSearch(String SousinsakiKbn) {

		/**=================================================================
		 *  SQL生成処理
		 *==================================================================**/
		PbsDatabase db = getDatabase(); //DBコネクション

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT                                   \n");
		sb.append("          W_EDI_REINYU_ANNAI_ID          \n");
		sb.append("  FROM                                   \n");
		sb.append("          W_EDI_REINYU_ANNAI             \n");
		sb.append(" WHERE                                   \n");
		sb.append("          SYORI_KBN          = ?         \n");
		sb.append("   AND    EDI_SEND_KB        = ?         \n");

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, "1");
		db.setString(iCc, ++ii, "01");

		/**=================================================================
		 *  SQL実行
		 *==================================================================**/
		db.execute(iCc);

		PbsRecord[] recs = null;

		try {
			recs = db.getPbsRecords(iCc,false);

		} catch (MaxRowsException e) {

		} catch (DataNotFoundException e) {
			return false;

		}

		return true;

	}



}
