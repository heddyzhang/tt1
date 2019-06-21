/*
 * contents.project.registRequest.OracleSalesInput.java 2005/6/29
 *
 *  更新履歴
 *  Ver.     日付          担当者         変更内容
 *  1.0      2005/06/29    指村 敦子      新規作成
 *           2014/03/14    鈴木 麻里      システム概要メニューSaaS追加
 *           2017/03/02    鈴木 麻里      システム概要メニューCloud/On Premise Deal追加
 */
package contents.project.registRequest;

import java.net.URL;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import contents.ContentsBase;

/**
 * PJR内容入力画面生成クラス
 *
 * PJRにプロジェクトを登録してもらうためにメールを出すための入力画面。
 *
 * @author Atsuko Sashimura
 */
public class PjrInput extends ContentsBase {

	/**
	 * クエリー取得メソッド.
	 *
	 * @param request サーブレットリクエスト
	 * @return クエリ文字列のHashtable
	 */
	protected Hashtable getQuery( HttpServletRequest request )
	{
		Hashtable querys = new Hashtable();
		querys.put("CONTRACT_TYPE", getContractType(request));
		querys.put("BID_MANAGER", getBidManager(request));
		querys.put("WORK_STATUS", getWorkStatus(request));
		querys.put("PROBABILITY",getProbability(request));
		querys.put("PROJ_CLASS_LEV", getProjClassLev(request));
		querys.put("DEPLOY_MODEL", getDeployModel(request));
		querys.put("IMPLEMENT_METHOD", getImplementMethod(request));
		querys.put("CLOUD_ONPREMISE_DEAL", getCloudOnPremiseDeal());
		querys.put("CLOUD_ONPREMISE_DESCRIPTION", getCloudOnPremiseDesctiption());
		return querys ;
	}

	/**
	 * 契約形態の値を取得する
	 *
	 * @param request
	 * @return SQL文字列
	 */
	protected String getContractType(HttpServletRequest request)
	{
		String querys = "SELECT id id, value name"
			+ "\n FROM const_list"
			+ "\n WHERE name = 'CONTRACT'"
			+ "\n AND   end_date is null"
			+ "\n ORDER BY id";
		return querys;
	}

	/**
	 * BidマネージャセレクトBOXに表示する値を取得する
	 *
	 * @param request
	 * @return SQL文字列
	 */
	protected String getBidManager(HttpServletRequest request)
	{
		String querys = "SELECT emp_id id, emp_name name"
			+ "\n FROM emp_list"
			+ "\n WHERE position_id < 7"
			+ "\n AND del_flg != 1"
			+ "\n ORDER BY emp_name_kana";
		return querys;
	}

	/**
	 * ワークステータスの値を取得する
	 *
	 * @param request
	 * @return SQL文字列
	 */
	protected String getWorkStatus(HttpServletRequest request)
	{
		String querys = "SELECT status_id id, s_code||':'||status_desc name"
			+ "\n FROM work_status_list"
			+ "\n ORDER BY status_id";
		return querys;
	}

	protected String getProbability(HttpServletRequest request)
	{
		String querys = "SELECT win_probability_id id, win_probability_name name"
			+ "\n FROM prr_sales_win_probability"
			+ "\n WHERE start_date <= sysdate"
			+ "\n AND NVL(end_date,sysdate+1) > sysdate"
			+ "\n ORDER BY sort_number";
		return querys;
	}

	/**
	 * PROJ_CLASS_LEVの値を取得する
	 *
	 * @param request
	 * @return SQL文字列
	 */
	protected String getProjClassLev(HttpServletRequest request)
	{
		String querys = "SELECT d.desc_id id, d.description||DECODE(d.description_detail,'','',' - '||d.description_detail) name"
			+ "\n FROM gbl_class_category c, gbl_class_description d"
			+ "\n WHERE c.category_id = d.category_id"
			+ "\n AND   c.category = 'PROJ_CLASS_LEV'"
			+ "\n AND   d.end_date is null"
			+ "\n ORDER BY d.priority, d.description";
		return querys;

	}

	/**
	 * DEPLOY_MODELの値を取得する
	 *
	 * @param request
	 * @return SQL文字列
	 */
	protected String getDeployModel(HttpServletRequest request)
	{
		String querys = "SELECT d.desc_id id, d.description||DECODE(d.description_detail,'','',' - '||d.description_detail) name"
			+ "\n FROM gbl_class_category c, gbl_class_description d"
			+ "\n WHERE c.category_id = d.category_id"
			+ "\n AND   c.category = 'DEPLOY_MODEL'"
			+ "\n AND   d.end_date is null"
			+ "\n ORDER BY d.priority, d.description";
		return querys;
	}

	/**
	 * IMPLEMENT_METHODの値を取得する
	 *
	 * @param request
	 * @return SQL文字列
	 */
	protected String getImplementMethod(HttpServletRequest request)
	{
		String querys = "SELECT d.desc_id id, d.description||DECODE(d.description_detail,'','',' - '||d.description_detail) name"
			+ "\n FROM gbl_class_category c, gbl_class_description d"
			+ "\n WHERE c.category_id = d.category_id"
			+ "\n AND   c.category = 'IMPLEMENT_METHOD'"
			+ "\n AND   d.end_date is null"
			+ "\n ORDER BY d.priority, d.description";
		return querys;
	}

	/**
	 * Cloud/On Premise Dealの値を取得する
	 *
	 * @param request
	 * @return SQL文字列
	 */
	protected String getCloudOnPremiseDeal()
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT d.desc_id id, d.description name");
		sql.append("\n FROM gbl_class_category c, gbl_class_description d");
		sql.append("\n WHERE c.category_id = d.category_id");
		sql.append("\n AND   c.category = 'Cloud/On Premise Deal'");
		sql.append("\n AND   d.end_date is null");
		sql.append("\n ORDER BY d.priority");

		return sql.toString();
	}

	/**
	 * Cloud/On Premise Dealの説明文を取得する
	 * @return
	 */
	protected String getCloudOnPremiseDesctiption()
	{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT VALUE FROM CONST_LIST");
		sql.append("\n WHERE NAME = 'PRR_CLOUD_ONPREMISE_DESCRIPTION'");
		sql.append("\n AND END_DATE IS NULL");

		return sql.toString();
	}

	/**
	 * スタイルシートURL取得メソッド.
	 *
	 *
	 */
	protected URL getXSLURL( HttpServletRequest request )
		throws Exception
	{
		return getClass().getResource( "/xsl/project/registRequest/PjrInput.xsl" ) ;
	}

	/**
	 * メニューコード取得メソッド.
	 *
	 * @return メニューコード
	 */
	protected String getMenuCode( HttpServletRequest request )
	{
		//PRR_PRJ登録依頼
		return "10402";
	}

	/**
	 * DOM取得後処理メソッド.
	 *
	 *
	 */
	protected void actionAfterGetDOM( String key , Element rowset , Document x_doc , HttpServletRequest request)
		throws Exception
	{

	}
}
