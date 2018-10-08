package kit.juchu.TuminiDenpyoHako;

import static fb.com.IKitComConst.*;
import static fb.com.IKitComConstHM.*;
import static kit.juchu.TuminiDenpyoHako.IJuchuTuminiDenpyoHako.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import fb.com.MstSearch.MstCdmeisyMaster_SearchService;
import fb.inf.KitFunction;
import fb.inf.KitSelectList;
import fb.inf.exception.KitException;

/**
 * モーダルダイアログ処理の実装クラスです
 *
 */
public class JuchuTuminiDenpyoHako_ModalFunction extends KitFunction {

	/** シリアルID */
	private static final long serialVersionUID = -176412726363955946L;

	// -----------------------------------------
	// create log4j instance & Config LogLevel
	// -----------------------------------------
	private static String className__ = JuchuTuminiDenpyoHako_ModalFunction.class.getName();

	// -----------------------------------------
	// define variable
	// -----------------------------------------
	private static final boolean isTransactionalFunction = false;

	/**
	 * Transaction制御フラグを返却する。
	 *
	 * @return Transaction制御フラグ
	 */
	@Override
	protected boolean isTransactionalFunction() {

		return isTransactionalFunction;
	}

	/**
	 * クラス名を返却する。
	 *
	 * @return クラス名
	 */
	@Override
	protected String getFunctionName() {

		return className__;
	}

	/**
	 * コンストラクタ。
	 *
	 * @param mapping ActionMapping
	 * @param form ActionForm
	 * @param request HttpサーブレットRequest
	 * @param response HttpサーブレットResponse
	 */
	public JuchuTuminiDenpyoHako_ModalFunction(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		super(mapping, form, request, response);
	}

	/**
	 * モーダルダイアログ処理を実行する。
	 *
	 * @return Forward情報
	 */
	@Override
	public String execute_() throws KitException {

		// editFormを編集可能モードにする
		JuchuTuminiDenpyoHako_EditForm editForm = new JuchuTuminiDenpyoHako_EditForm();
		editForm.setEditable(IS_EDITABLE_TRUE);
		setRequest(KEY_SS_EDITFORM, editForm);

		return FORWARD_SUCCESS;
	}
}