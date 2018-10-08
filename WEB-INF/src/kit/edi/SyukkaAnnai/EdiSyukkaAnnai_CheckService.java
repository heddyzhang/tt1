package kit.edi.SyukkaAnnai;


import static fb.com.IKitComConst.*;
import static fb.com.IKitComErrorConst.*;
import static kit.edi.SyukkaAnnai.IEdiSyukkaAnnai.*;
import kit.batch.edi.sndSyukaAnnai.SndSyukaAnnai_Form;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import fb.com.ComRecordUtil;
import fb.com.ComUserSession;
import fb.com.ComValidateUtil;
import fb.inf.KitService;
import fb.inf.exception.DataNotFoundException;
import fb.inf.exception.MaxRowsException;
import fb.inf.pbs.PbsDatabase;
import fb.inf.pbs.PbsRecord;
import fb.inf.pbs.PbsUtil;

/**
 * 摘要区分マスタのチェックサービスクラスです。
 * @author mori
 *
 */
public class EdiSyukkaAnnai_CheckService extends KitService {

	/** シリアルID */
	private static final long serialVersionUID = -4278551509177893180L;
	/** クラス名. */
	private static String className__ = EdiSyukkaAnnai_CheckService.class.getName();
	/** カテゴリ. */
	public static Category category__ = Category.getInstance(className__);

	/** チェックユーティリティー */
	private ComValidateUtil vUtil = null;
	/** 存在チェックユーティリティー */
	private ComRecordUtil rUtil = null;

	/** データベースオブジェクト */
	private PbsDatabase db_;

	/**
	 * コンストラクタ.<br>ユーティリティーオブジェクトを初期化する。
	 * @param cus ユーザセッション
	 * @param db_ DBオブジェクト
	 * @param isTran トランザクション情報
	 * @param ae アクションエラー情報
	 */
	public EdiSyukkaAnnai_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
		// データベースオブジェクトを初期化する
		this.db_ = db_;


	}

	// ##  SearchForm  ################################################
	// ### 検索 ######################################################

	/**
	 * 検索フォームの入力チェックを行う.(共通)
	 *
	 * @param editList
	 * @return true(エラー無) or false(エラー有)
	 */
	public boolean validateSearch(EdiSyukkaAnnai_EditForm editForm) {

		// ==========================================
		// 必須フォーマットチェックを行う。
		// ⇒inputCheckFormatForSearchメソッド呼び出し
		// ==========================================

		if (REQ_TYPE_IKKATU.equals(editForm.getReqType())){

			// 日付期間指定チェックを行う
			if(!PbsUtil.isEmpty(editForm.getFromDt()) && !PbsUtil.isEmpty(editForm.getToDt())){
				if(editForm.getFromDt().compareTo(editForm.getToDt()) > 0){
					setErrorMessageId(new ActionError(ERRORS_DATE_ERROR, "日付期間指定"));
					return false;
				}
			}

			// 作成対象入力チェックを行う
			if(PbsUtil.isEmpty(editForm.getSakuseitaisyouKbn())) {
				setErrorMessageId(new ActionError(ERRORS_MISTAKE, "作成対象"));
				return false;
			}
		}

		// 送信先入力チェックを行う
		if (REQ_TYPE_SYUKKAANNAIEDI.equals(editForm.getReqType())){

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
		sb.append("          W_EDI_SYUKA_ANNAI_ID           \n");
		sb.append("  FROM                                   \n");
		sb.append("          W_EDI_SYUKA_ANNAI              \n");
		sb.append(" WHERE                                   \n");
		sb.append("          SYORI_KBN          = ?         \n");

		if(!EDI_SYUKKA_PARTNER_KB_IKKATU.equals(SousinsakiKbn)) {
			sb.append("   AND    EDI_SEND_KB        = ?         \n");
		}

		int iCc = db.prepare(true, sb.toString());
		int ii = 0;

		db.setString(iCc, ++ii, "1");

		if(EDI_SYUKKA_PARTNER_KB_SDN.equals(SousinsakiKbn)) {
			db.setString(iCc, ++ii, "01");

		}else if(EDI_SYUKKA_PARTNER_KB_FINET.equals(SousinsakiKbn)) {
			db.setString(iCc, ++ii, "02");

		}

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



} // -- class