package kit.jiseki.ESienList.torokuGroup;

import static fb.com.IKitComConstHM.IS_DELETED_HELD_TRUE;
import static fb.com.IKitComConstHM.IS_MESSAGE_OUTPUT_TRUE;

import org.apache.log4j.Category;
import org.apache.struts.action.ActionErrors;

import fb.com.ComUserSession;
import fb.com.Records.FbMastrSgyosyaRecord;
import fb.inf.pbs.PbsDatabase;
import kit.jiseki.ESienList.JisekiESienList_CheckService;

/**
 * 各種登録グループのチェックサービス
 * @author t_kimura
 *
 */
public class JisekiESienListTorokuGroup_CheckService extends JisekiESienList_CheckService {

	/** serialVersionUID */
	private static final long serialVersionUID = 4578896238527351335L;

	/** クラス名 */
	private static String className__ = JisekiESienListTorokuGroup_CheckService.class.getName();

	/** カテゴリ */
	private static Category category__ = Category.getInstance(className__);

	/**
	 * コンストラクタ.
	 *
	 * @param cus getComUserSession()を渡すこと。
	 * @param db_ 呼び出すときにはgetDatabase()を渡すこと。
	 * @param isTran isTransaction()を渡すこと。
	 * @param ae getActionErrors()を渡すこと。
	 */
	public JisekiESienListTorokuGroup_CheckService(ComUserSession cus, PbsDatabase db_, boolean isTran, ActionErrors ae) {
		super(cus, db_, isTran, ae);
	}

	/**
	 * 作業者コードチェック処理
	 * @param kaisyaCd 会社コード
	 * @param sgyosyaCd 作業者コード
	 * @return チェック結果
	 */
	public boolean checkSgyosyaCd(String kaisyaCd, String sgyosyaCd) {
		// 認証マスター取得
		FbMastrSgyosyaRecord fbRec = getMastrSgyosyaRecord(kaisyaCd, sgyosyaCd);
		// 存在チェック
		return validateUtil.validateMstExistence(fbRec, IS_DELETED_HELD_TRUE, "com.text.torokuUser", IS_MESSAGE_OUTPUT_TRUE);
	}

	/**
	 * 認証マスター取得
	 * @param kaisyaCd 会社コード
	 * @param sgyosyaCd 作業者コード
	 * @return 認証マスター
	 */
	public FbMastrSgyosyaRecord getMastrSgyosyaRecord(String kaisyaCd, String sgyosyaCd) {
		return recordUtil.getRecMastrSgyosya(IS_DELETED_HELD_TRUE, kaisyaCd, sgyosyaCd);
	}
}
