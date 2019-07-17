<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!--for local parse IE5.5 under <xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl"
		> -->
	<xsl:output method="html" version="4.0" encoding="UTF-8" />

<!-- /////////////////////////////////////////////////////
	// Forecast検索画面
	/////////////////////////////////////////////////////
	// 更新履歴
	// Ver. 日付 担当者 変更内容
	// 1.0 2002/05/28 竹村正義 新規作成
	// 1.1 2002/09/19 Masayoshi.Takemura 予算合計を表示するように変更
	// 1.2 2003/02/04 Masayoshi.Takemura フル桁表示対応
	// 1.3 2003/06/06 Masayoshi.Takemura Done非表示か否か
	// 1.3 2004/03/23 Masayoshi.Takemura ForecastStatus Vのみ表示対応(ソリューション推進部)
	// C00012 2006/02/16 Atsuko.Sashimura 表示列選択項目変更
	// C00019 2006/11/21 Atsuko.Sashimura OracleSalesのデータを表示
	// C00057 2007/01/21 Atsuko.Sashimura Siebel移行対応
	// 2013/05/22 Mari.Suzuki OppoTypeカラム分割対応
	// 2013/07/12 Mari.Suzuki レベトラ非表示検索
	// 2013/07/18 Mari.Suzuki 締結QTR追加
	// 2013/07/23 Mari.Suzuki CLASSIFICATION追加対応
	// 2013/11/15 Mari.Suzuki サービス分類追加対応
	// 2014/02/07 Mari.Suzuki FYプルダウンの表示変更（FY08以降のみ選択可能）
	// 2014/03/18 Mari.Suzuki CLASSIFICATION_4の文言変更
	// 2014/03/26 Mari.Suzuki Booking集計対象外追加
	// 2014/10/10 Minako.Uenaka 項目名英語対応
	// 2015/05/27 Mari.Suzuki コストセンタのプルダウンにID（下4桁）を表示
	// 2015/10/30 Mari.Suzuki ProductName追加
	// 2016/01/08 Mari.Suzuki 表示項目の全選択/デフォルトに戻す対応
	// 2016/06/02 Mari.Suzuki 案件登録日追加
	// 2016/07/06 Mari.Suzuki Booking管理組織仕様変更
	// 2017/05/09 Mari.Suzuki ConsCategory追加
	// 2017/12/06 Yoshie.Koike Saas、Iaas対応
	// 2018/02/05 Yoshie.koike ContractNumber対応
	///////////////////////////////////////////////////// -->

	<!--変数 -->
	<xsl:variable name="uri" select="/doc-root/header/uri" />
	<xsl:variable name="css_uri" select="/doc-root/header/css_uri" />
	<xsl:variable name="javascript_uri" select="/doc-root/header/javascript_uri" />
	<xsl:variable name="code_name" select="/doc-root/header/code_name" />

	<xsl:variable name="forecast_type" select="/doc-root/header/forecast_type" />
	<xsl:variable name="present_year" select="/doc-root/DATESET/DATE/PRESENT_YEAR" />

	<xsl:variable name="c_revenue_ci" select="/doc-root/header/c_revenue_ci" />
	<xsl:variable name="c_history_id" select="/doc-root/header/c_history_id" />
	<xsl:variable name="c_fy" select="/doc-root/header/c_fy" />
	<xsl:variable name="c_start_month" select="/doc-root/header/c_start_month" />
	<xsl:variable name="c_show_length" select="/doc-root/header/c_show_length" />
	<xsl:variable name="c_org_id" select="/doc-root/header/c_org_id" />
	<xsl:variable name="c_prj_org" select="/doc-root/header/c_prj_org" />
	<xsl:variable name="c_work_status" select="/doc-root/header/c_work_status" />
	<xsl:variable name="c_proj_code" select="/doc-root/header/c_proj_code" />
	<xsl:variable name="c_gsi_proj_code" select="/doc-root/header/c_gsi_proj_code" />
	<xsl:variable name="c_proj_name" select="/doc-root/header/c_proj_name" />
	<xsl:variable name="c_proj_owner_name" select="/doc-root/header/c_proj_owner_name" />
	<xsl:variable name="c_proj_mgr_name" select="/doc-root/header/c_proj_mgr_name" />
	<xsl:variable name="c_admi" select="/doc-root/header/c_admi" />
	<xsl:variable name="c_end_user_name" select="/doc-root/header/c_end_user_name" />
	<xsl:variable name="c_client_name" select="/doc-root/header/c_client_name" />
	<xsl:variable name="c_contract_id" select="/doc-root/header/c_contract_id" />
	<xsl:variable name="c_classification" select="/doc-root/header/c_classification" />
	<xsl:variable name="c_booking_org" select="/doc-root/header/c_booking_org" />
	<xsl:variable name="c_sales" select="/doc-root/header/c_sales" />
	<xsl:variable name="c_oppo_number" select="/doc-root/header/c_oppo_number" />
	<xsl:variable name="c_bunrui_code" select="/doc-root/header/c_bunrui_code" />
	<xsl:variable name="c_category_id" select="/doc-root/header/c_category_id" />
	<xsl:variable name="c_pa_cc_id" select="/doc-root/header/c_pa_cc_id" />
	<xsl:variable name="c_fstatus_winprob" select="/doc-root/header/c_fstatus_winprob" />
	<xsl:variable name="c_win_prob_check0" select="/doc-root/header/c_win_prob_check0" />
	<xsl:variable name="c_win_prob_check10" select="/doc-root/header/c_win_prob_check10" />
	<xsl:variable name="c_win_prob_check20" select="/doc-root/header/c_win_prob_check20" />
	<xsl:variable name="c_win_prob_check30" select="/doc-root/header/c_win_prob_check30" />
	<xsl:variable name="c_win_prob_check40" select="/doc-root/header/c_win_prob_check40" />
	<xsl:variable name="c_win_prob_check50" select="/doc-root/header/c_win_prob_check50" />
	<xsl:variable name="c_win_prob_check60" select="/doc-root/header/c_win_prob_check60" />
	<xsl:variable name="c_win_prob_check70" select="/doc-root/header/c_win_prob_check70" />
	<xsl:variable name="c_win_prob_check80" select="/doc-root/header/c_win_prob_check80" />
	<xsl:variable name="c_win_prob_check90" select="/doc-root/header/c_win_prob_check90" />
	<xsl:variable name="c_win_prob_check100" select="/doc-root/header/c_win_prob_check100" />
	<xsl:variable name="c_book_status_check1" select="/doc-root/header/c_book_status_check1" />
	<xsl:variable name="c_book_status_check2" select="/doc-root/header/c_book_status_check2" />
	<xsl:variable name="c_book_status_check3" select="/doc-root/header/c_book_status_check3" />
	<xsl:variable name="c_book_status_check4" select="/doc-root/header/c_book_status_check4" />
	<xsl:variable name="c_book_status_check5" select="/doc-root/header/c_book_status_check5" />
	<xsl:variable name="c_book_status_check99"
		select="/doc-root/header/c_book_status_check99" />
	<xsl:variable name="c_rev_status_check1" select="/doc-root/header/c_rev_status_check1" />
	<xsl:variable name="c_rev_status_check2" select="/doc-root/header/c_rev_status_check2" />
	<xsl:variable name="c_rev_status_check3" select="/doc-root/header/c_rev_status_check3" />
	<xsl:variable name="c_rev_status_check4" select="/doc-root/header/c_rev_status_check4" />
	<xsl:variable name="c_rev_status_check5" select="/doc-root/header/c_rev_status_check5" />
	<xsl:variable name="c_rev_status_check99" select="/doc-root/header/c_rev_status_check99" />

	<xsl:variable name="noRev" select="/doc-root/header/noRev" />

	<xsl:variable name="c_sort1" select="/doc-root/header/c_sort1" />
	<xsl:variable name="c_sort2" select="/doc-root/header/c_sort2" />
	<xsl:variable name="c_sort3" select="/doc-root/header/c_sort3" />

	<xsl:variable name="o_proj_code" select="/doc-root/header/o_proj_code" />
	<xsl:variable name="o_gsi_proj_code" select="/doc-root/header/o_gsi_proj_code" />
	<xsl:variable name="o_oppo_number" select="/doc-root/header/o_oppo_number" />
	<xsl:variable name="o_proj_name" select="/doc-root/header/o_proj_name" />
	<xsl:variable name="o_client_name" select="/doc-root/header/o_client_name" />
	<xsl:variable name="o_ex_cont" select="/doc-root/header/o_ex_cont" />
	<xsl:variable name="o_reason_for_change" select="/doc-root/header/o_reason_for_change" />
	<xsl:variable name="o_end_user_name" select="/doc-root/header/o_end_user_name" />
	<xsl:variable name="o_eu_official_name" select="/doc-root/header/o_eu_official_name" />
	<xsl:variable name="o_key_acnt" select="/doc-root/header/o_key_acnt" />
	<xsl:variable name="o_proj_owner_name" select="/doc-root/header/o_proj_owner_name" />
	<xsl:variable name="o_proj_mgr_name" select="/doc-root/header/o_proj_mgr_name" />
	<xsl:variable name="o_classification" select="/doc-root/header/o_classification" />
	<xsl:variable name="o_booking_org" select="/doc-root/header/o_booking_org" />
	<xsl:variable name="o_project_total" select="/doc-root/header/o_project_total" />
	<xsl:variable name="o_classification_4" select="/doc-root/header/o_classification_4" />
	<xsl:variable name="o_sa_info" select="/doc-root/header/o_sa_info" />
	<xsl:variable name="o_service_classification"
		select="/doc-root/header/o_service_classification" />
	<xsl:variable name="o_book_status" select="/doc-root/header/o_book_status" />
	<xsl:variable name="o_rev_status" select="/doc-root/header/o_rev_status" />
	<xsl:variable name="o_work_status" select="/doc-root/header/o_work_status" />
	<xsl:variable name="o_org_name" select="/doc-root/header/o_org_name" />
	<xsl:variable name="o_prd_org_name" select="/doc-root/header/o_prd_org_name" />
	<xsl:variable name="o_pa_cc_id" select="/doc-root/header/o_pa_cc_id" />
	<xsl:variable name="o_admi" select="/doc-root/header/o_admi" />
	<xsl:variable name="o_latest_mporg" select="/doc-root/header/o_latest_mporg" />
	<xsl:variable name="c_admi_isnull" select="/doc-root/header/c_admi_isnull" />
	<xsl:variable name="skuOnly" select="/doc-root/header/skuOnly" />
	<xsl:variable name="o_fpa" select="/doc-root/header/o_fpa" />
	<xsl:variable name="o_cont_amount" select="/doc-root/header/o_cont_amount" />
	<xsl:variable name="o_own_cont_amount" select="/doc-root/header/o_own_cont_amount" />
	<xsl:variable name="o_contract_type" select="/doc-root/header/o_contract_type" />
	<xsl:variable name="o_proj_start" select="/doc-root/header/o_proj_start" />
	<xsl:variable name="o_proj_end" select="/doc-root/header/o_proj_end" />
	<xsl:variable name="o_update_button" select="/doc-root/header/o_update_button" />
	<xsl:variable name="o_sum_budget" select="/doc-root/header/o_sum_budget" />
	<xsl:variable name="o_sales" select="/doc-root/header/o_sales" />
	<xsl:variable name="o_bid" select="/doc-root/header/o_bid" />
	<xsl:variable name="o_win_prob" select="/doc-root/header/o_win_prob" />
	<xsl:variable name="o_contract_date" select="/doc-root/header/o_contract_date" />
	<xsl:variable name="o_contract_quarter" select="/doc-root/header/o_contract_quarter" />
	<xsl:variable name="o_opty_creation_date" select="/doc-root/header/o_opty_creation_date" />
	<xsl:variable name="o_progress" select="/doc-root/header/o_progress" />
	<xsl:variable name="o_oppo_status" select="/doc-root/header/o_oppo_status" />
	<xsl:variable name="o_module" select="/doc-root/header/o_module" />
	<xsl:variable name="o_product_name" select="/doc-root/header/o_product_name" />
	<xsl:variable name="o_cons_category" select="/doc-root/header/o_cons_category" />
	<xsl:variable name="o_industry" select="/doc-root/header/o_industry" />
	<xsl:variable name="o_bunrui" select="/doc-root/header/o_bunrui" />
	<xsl:variable name="o_consul_type" select="/doc-root/header/o_consul_type" />
	<xsl:variable name="o_revenue_type" select="/doc-root/header/o_revenue_type" />
	<xsl:variable name="super_user" select="//SUPER_USER" />
	<xsl:variable name="fy12_flg" select="/doc-root/header/fy12_flg" />
	<xsl:variable name="o_not_book_sum" select="/doc-root/header/o_not_book_sum" />
	<xsl:variable name="o_lic_oppo_id" select="/doc-root/header/o_lic_oppo_id" />
	<xsl:variable name="o_ex_cont_id" select="/doc-root/header/o_ex_cont_id" />
	<xsl:variable name="c_csv_check" select="/doc-root/header/c_csv_check" />
	<xsl:variable name="c_full_check" select="/doc-root/header/c_full_check" />
	<xsl:variable name="c_meisai_check" select="/doc-root/header/c_meisai_check" />
	<xsl:variable name="c_done_check" select="/doc-root/header/c_done_check" />
	<xsl:variable name="c_rev_check" select="/doc-root/header/c_rev_check" />
	<xsl:variable name="to_v_list" select="/doc-root/header/to_v_list" />
	<xsl:variable name="o_contract_number" select="/doc-root/header/o_contract_number" />
	<xsl:variable name="o_sku_no" select="/doc-root/header/o_sku_no" />
	<xsl:variable name="o_sku_span" select="/doc-root/header/o_sku_spanr" />
	<xsl:variable name="o_sku_rev_rec_rule" select="/doc-root/header/o_sku_rev_rec_rule" />

	<xsl:variable name="now_language" select="/doc-root/property/now_language" />
	<xsl:variable name="after_search" select="/doc-root/property/after_search" />
	<!-- プロパティファイル読み込み -->
	<xsl:variable name="STANDARD_SEARCH" select="/doc-root/property/STANDARD_SEARCH" />
	<xsl:variable name="PROJECT_SEARCH" select="/doc-root/property/PROJECT_SEARCH" />
	<xsl:variable name="CUSTOMER_SEARCH" select="/doc-root/property/CUSTOMER_SEARCH" />
	<xsl:variable name="ADVANCED_SEARCH" select="/doc-root/property/ADVANCED_SEARCH" />
	<xsl:variable name="REVENUE" select="/doc-root/property/REVENUE" />
	<xsl:variable name="CI" select="/doc-root/property/CI" />
	<xsl:variable name="HISTORY_DATA_AS_OF" select="/doc-root/property/HISTORY_DATA_AS_OF" />
	<xsl:variable name="CHANGED_TO_VOID_STATUS_AFTER"
		select="/doc-root/property/CHANGED_TO_VOID_STATUS_AFTER" />
	<xsl:variable name="CURRENT" select="/doc-root/property/CURRENT" />
	<xsl:variable name="CSV_EXPORT" select="/doc-root/property/CSV_EXPORT" />
	<xsl:variable name="OMIT_COMPLETE_PJ" select="/doc-root/property/OMIT_COMPLETE_PJ" />
	<xsl:variable name="FULL_DIGIT_DISPLAY" select="/doc-root/property/FULL_DIGIT_DISPLAY" />
	<xsl:variable name="OMIT_REVENUE_TRANSFER_DATA"
		select="/doc-root/property/OMIT_REVENUE_TRANSFER_DATA" />
	<xsl:variable name="SUBCONTRACT_DETAILS"
		select="/doc-root/property/SUBCONTRACT_DETAILS" />
	<xsl:variable name="CHANGE_LANGUAGE" select="/doc-root/property/CHANGE_LANGUAGE" />
	<xsl:variable name="FY" select="/doc-root/property/FY" />
	<xsl:variable name="REV_DISPLAY_START_MONTH"
		select="/doc-root/property/REV_DISPLAY_START_MONTH" />
	<xsl:variable name="REV_DISPLAY_DURATION"
		select="/doc-root/property/REV_DISPLAY_DURATION" />
	<xsl:variable name="ORG_COSTCENTER_HIERARCHY_DISPLAY"
		select="/doc-root/property/ORG_COSTCENTER_HIERARCHY_DISPLAY" />
	<xsl:variable name="ORG_PJ_ORG_HIERARCHY_DISPLAY"
		select="/doc-root/property/ORG_PJ_ORG_HIERARCHY_DISPLAY" />
	<xsl:variable name="WORK_STATUS" select="/doc-root/property/WORK_STATUS" />
	<xsl:variable name="SEARCH" select="/doc-root/property/SEARCH" />
	<xsl:variable name="TEMP_NUMBER" select="/doc-root/property/TEMP_NUMBER" />
	<xsl:variable name="OPPORTUNITY_ID" select="/doc-root/property/OPPORTUNITY_ID" />
	<xsl:variable name="PROJECT_NUMBER" select="/doc-root/property/PROJECT_NUMBER" />
	<xsl:variable name="SUB_INDUSTRY" select="/doc-root/property/SUB_INDUSTRY" />
	<xsl:variable name="CONS_CATEGORYS" select="/doc-root/property/CONS_CATEGORYS" />
	<xsl:variable name="PROJECT_NAME" select="/doc-root/property/PROJECT_NAME" />
	<xsl:variable name="PRIMARY_SALES" select="/doc-root/property/PRIMARY_SALES" />
	<xsl:variable name="PO" select="/doc-root/property/PO" />
	<xsl:variable name="PM" select="/doc-root/property/PM" />
	<xsl:variable name="ADMINISTRATOR" select="/doc-root/property/ADMINISTRATOR" />
	<xsl:variable name="BLANK" select="/doc-root/property/BLANK" />
	<xsl:variable name="PARTIAL_MATCH_AVAILABLE"
		select="/doc-root/property/PARTIAL_MATCH_AVAILABLE" />
	<xsl:variable name="SKU" select="/doc-root/property/SKU" />
	<xsl:variable name="END_USER" select="/doc-root/property/END_USER" />
	<xsl:variable name="CUSTOMER" select="/doc-root/property/CUSTOMER" />
	<xsl:variable name="CONTRACT_TYPE" select="/doc-root/property/CONTRACT_TYPE" />
	<xsl:variable name="CLASSIFICATION" select="/doc-root/property/CLASSIFICATION" />
	<xsl:variable name="BOOKING_ORG" select="/doc-root/property/BOOKING_ORG" />
	<xsl:variable name="BOOKING_STATUS" select="/doc-root/property/BOOKING_STATUS" />
	<xsl:variable name="REVENUE_STATUS" select="/doc-root/property/REVENUE_STATUS" />
	<xsl:variable name="WINPROB" select="/doc-root/property/WINPROB" />
	<xsl:variable name="PROSPECT" select="/doc-root/property/PROSPECT" />
	<xsl:variable name="BEST" select="/doc-root/property/BEST" />
	<xsl:variable name="FORECAST" select="/doc-root/property/FORECAST" />
	<xsl:variable name="WORST" select="/doc-root/property/WORST" />
	<xsl:variable name="BACKLOG" select="/doc-root/property/BACKLOG" />
	<xsl:variable name="VOID" select="/doc-root/property/VOID" />
	<xsl:variable name="SORT_KEY1" select="/doc-root/property/SORT_KEY1" />
	<xsl:variable name="SORT_KEY2" select="/doc-root/property/SORT_KEY2" />
	<xsl:variable name="SORT_KEY3" select="/doc-root/property/SORT_KEY3" />
	<xsl:variable name="JAPANESE" select="/doc-root/property/JAPANESE" />
	<xsl:variable name="PA_COSTCENTER" select="/doc-root/property/PA_COSTCENTER" />
	<xsl:variable name="CHECK_COLUMNS_TO_DISPLAY"
		select="/doc-root/property/CHECK_COLUMNS_TO_DISPLAY" />

	<xsl:variable name="PJ_ORG_CHECK" select="/doc-root/property/PJ_ORG_CHECK" />
	<xsl:variable name="MP_ORG_CHECK" select="/doc-root/property/MP_ORG_CHECK" />
	<xsl:variable name="LATEST_MP_ORG_CHECK"
		select="/doc-root/property/LATEST_MP_ORG_CHECK" />
	<xsl:variable name="PA_COSTCENTER_CHECK"
		select="/doc-root/property/PA_COSTCENTER_CHECK" />
	<xsl:variable name="ADMINISTRATOR2_CHECK"
		select="/doc-root/property/ADMINISTRATOR2_CHECK" />
	<xsl:variable name="ADMINISTRATOR_CHECK"
		select="/doc-root/property/ADMINISTRATOR_CHECK" />
	<xsl:variable name="FP_AUTO_CHECK" select="/doc-root/property/FP_AUTO_CHECK" />
	<xsl:variable name="WORK_STATUS_CHECK" select="/doc-root/property/WORK_STATUS_CHECK" />
	<xsl:variable name="PROBABILITY_CHECK" select="/doc-root/property/PROBABILITY_CHECK" />
	<xsl:variable name="BOOK_STATUS_CHECK" select="/doc-root/property/BOOK_STATUS_CHECK" />
	<xsl:variable name="REVENUE_STATUS_CHECK"
		select="/doc-root/property/REVENUE_STATUS_CHECK" />
	<xsl:variable name="OPPORTUNITY_STATUS_CHECK"
		select="/doc-root/property/OPPORTUNITY_STATUS_CHECK" />
	<xsl:variable name="SALES_STAGE_CHECK" select="/doc-root/property/SALES_STAGE_CHECK" />
	<xsl:variable name="TEMP_NUMBER_CHECK" select="/doc-root/property/TEMP_NUMBER_CHECK" />
	<xsl:variable name="SUB_INDUSTRY_CHECK" select="/doc-root/property/SUB_INDUSTRY_CHECK" />
	<xsl:variable name="OPTY_ID_CHECK" select="/doc-root/property/OPTY_ID_CHECK" />
	<xsl:variable name="PROJECT_NUMBER_CHECK"
		select="/doc-root/property/PROJECT_NUMBER_CHECK" />
	<xsl:variable name="CUSTOMER_CHECK" select="/doc-root/property/CUSTOMER_CHECK" />
	<xsl:variable name="END_USER_CHECK" select="/doc-root/property/END_USER_CHECK" />
	<xsl:variable name="END_USER_CORRECTERD_CHECK"
		select="/doc-root/property/END_USER_CORRECTERD_CHECK" />
	<xsl:variable name="KEY_TL_CHECK" select="/doc-root/property/KEY_TL_CHECK" />
	<xsl:variable name="KEY_ACCOUNT_CHECK" select="/doc-root/property/KEY_ACCOUNT_CHECK" />
	<xsl:variable name="TL_ACCOUNT_CHECK" select="/doc-root/property/TL_ACCOUNT_CHECK" />
	<xsl:variable name="PROJECTNAME_CHECK" select="/doc-root/property/PROJECTNAME_CHECK" />
	<xsl:variable name="MODULE_CHECK" select="/doc-root/property/MODULE_CHECK" />
	<xsl:variable name="INDUSTRY_CHECK" select="/doc-root/property/INDUSTRY_CHECK" />
	<xsl:variable name="CONSULTINGTYPE_CHECK"
		select="/doc-root/property/CONSULTINGTYPE_CHECK" />
	<xsl:variable name="REVENUETYPE_CHECK" select="/doc-root/property/REVENUETYPE_CHECK" />
	<xsl:variable name="CLASSIFICATION_CHECK"
		select="/doc-root/property/CLASSIFICATION_CHECK" />
	<xsl:variable name="BOOKING_ORG_CHECK" select="/doc-root/property/BOOKING_ORG_CHECK" />
	<xsl:variable name="SCALE_APPS_ONLY_CHECK"
		select="/doc-root/property/SCALE_APPS_ONLY_CHECK" />
	<xsl:variable name="SECTORAL_ANALYSIS_CHECK"
		select="/doc-root/property/SECTORAL_ANALYSIS_CHECK" />
	<xsl:variable name="SA_BPM_SOA_ONLY_CHECK"
		select="/doc-root/property/SA_BPM_SOA_ONLY_CHECK" />
	<xsl:variable name="SERVICE_TYPE_CHECK" select="/doc-root/property/SERVICE_TYPE_CHECK" />
	<xsl:variable name="BUDGET_CHECK" select="/doc-root/property/BUDGET_CHECK" />
	<xsl:variable name="PROJECT_START_DATE_CHECK"
		select="/doc-root/property/PROJECT_START_DATE_CHECK" />
	<xsl:variable name="PROJECT_END_DATE_CHECK"
		select="/doc-root/property/PROJECT_END_DATE_CHECK" />
	<xsl:variable name="OPPORTUNITY_OWNER_CHECK"
		select="/doc-root/property/OPPORTUNITY_OWNER_CHECK" />
	<xsl:variable name="BID_MANAGER_CHECK" select="/doc-root/property/BID_MANAGER_CHECK" />
	<xsl:variable name="PROJECT_OWNER_CHECK"
		select="/doc-root/property/PROJECT_OWNER_CHECK" />
	<xsl:variable name="PROJECT_MANAGER_CHECK"
		select="/doc-root/property/PROJECT_MANAGER_CHECK" />
	<xsl:variable name="CLOSE_DATE_CHECK" select="/doc-root/property/CLOSE_DATE_CHECK" />
	<xsl:variable name="BOOKING_QTR_CHECK" select="/doc-root/property/BOOKING_QTR_CHECK" />
	<xsl:variable name="OPTY_CREATION_DATE" select="/doc-root/property/OPTY_CREATION_DATE" />
	<xsl:variable name="BOOKING_BEFORE_CC_TRANSFER_CHECK"
		select="/doc-root/property/BOOKING_BEFORE_CC_TRANSFER_CHECK" />
	<xsl:variable name="BOOKING_AMOUNT_CHECK"
		select="/doc-root/property/BOOKING_AMOUNT_CHECK" />
	<xsl:variable name="REMAINING_AMOUNT_CHECK"
		select="/doc-root/property/REMAINING_AMOUNT_CHECK" />
	<xsl:variable name="BOOKING_EXCLUSION_CHECK"
		select="/doc-root/property/BOOKING_EXCLUSION_CHECK" />
	<xsl:variable name="CHANGE_REASON_CHECK"
		select="/doc-root/property/CHANGE_REASON_CHECK" />
	<xsl:variable name="CONTRACT_TYPE_CHECK"
		select="/doc-root/property/CONTRACT_TYPE_CHECK" />
	<xsl:variable name="SUBCONTRACT_CHECK" select="/doc-root/property/SUBCONTRACT_CHECK" />
	<xsl:variable name="FORECAST_CHECK" select="/doc-root/property/FORECAST_CHECK" />
	<xsl:variable name="SUBCONTRACTOR_CHECK"
		select="/doc-root/property/SUBCONTRACTOR_CHECK" />
	<xsl:variable name="SUBCONTRACT_INFO_CHECK"
		select="/doc-root/property/SUBCONTRACT_INFO_CHECK" />
	<xsl:variable name="SUBCONTRACT_TYPE_CHECK"
		select="/doc-root/property/SUBCONTRACT_TYPE_CHECK" />
	<xsl:variable name="UPDATEBUTTON_CHECK" select="/doc-root/property/UPDATEBUTTON_CHECK" />
	<xsl:variable name="LIC_OPPO_ID" select="/doc-root/property/LIC_OPPO_ID" />
	<xsl:variable name="CONTRACT_NUMBER" select="/doc-root/property/CONTRACT_NUMBER" />
	<xsl:variable name="SKU_NO" select="/doc-root/property/SKU_NO" />
	<xsl:variable name="SKU_SPAN" select="/doc-root/property/SKU_SPAN" />
	<xsl:variable name="SKU_REV_REC_RULE" select="/doc-root/property/SKU_REV_REC_RULE" />
	<xsl:variable name="ExContId" select="/doc-root/property/ExContId" />
	<xsl:variable name="PRODUCT_NAME" select="/doc-root/property/PRODUCT_NAME" />
	<xsl:variable name="CONS_CATEGORY" select="/doc-root/property/CONS_CATEGORY" />
	<xsl:variable name="ALL_CHECK" select="/doc-root/property/ALL_CHECK" />
	<xsl:variable name="DEFAULT_CHECK" select="/doc-root/property/DEFAULT_CHECK" />
	<xsl:template match="/">
		<html>

			<head>
				<title>Forecast検索</title>

				<!-- CSS -->
				<link>
					<xsl:attribute name="href"><xsl:value-of
						select="$css_uri" /></xsl:attribute>
					<xsl:attribute name="rel">stylesheet</xsl:attribute>
					<xsl:attribute name="type">text/css</xsl:attribute>
				</link>

				<!-- JavaScript -->
				<script>
					<xsl:attribute name="src"><xsl:value-of
						select="$javascript_uri" /></xsl:attribute>
				</script>
				<script src="script/Forecast_Search.js?var=20160706" />
			</head>

			<body>
				<xsl:attribute name="onLoad">
			<xsl:text>JavaScript:changeSearchType('</xsl:text>
			<xsl:value-of select="$forecast_type" />
			<xsl:text>');</xsl:text>
			<xsl:text>JavaScript:changeStatusType();</xsl:text>
			<xsl:text>JavaScript:checkUpdateButton();</xsl:text>
			<xsl:text>JavaScript:admiIsNull();</xsl:text>

		</xsl:attribute>

				<!-- フォームタグ -->
				<form name="form1" target="down">
					<xsl:attribute name="method">post</xsl:attribute>
					<xsl:attribute name="action"><xsl:value-of
						select="$uri" /></xsl:attribute>

					<!-- コントロールコード -->
					<input>
						<xsl:attribute name="type">hidden</xsl:attribute>
						<xsl:attribute name="name"><xsl:value-of
							select="$code_name" /></xsl:attribute>
						<xsl:attribute name="value">showForecastList</xsl:attribute>
					</input>
					<input type="hidden" name="fy12_flg">
						<xsl:attribute name="value"><xsl:value-of
							select="$fy12_flg" /></xsl:attribute>
					</input>

					<!-- forecastReport or not -->
					<input>
						<xsl:attribute name="type">hidden</xsl:attribute>
						<xsl:attribute name="name">forecastReport_flg</xsl:attribute>
						<xsl:attribute name="value">no</xsl:attribute>
					</input>

					<xsl:for-each select="//ORG_FSTATUS">
						<input type="hidden">
							<xsl:attribute name="name">
					<xsl:text>default_org_fstatus</xsl:text>
					<xsl:value-of select="position()" />
				</xsl:attribute>
							<xsl:attribute name="value">
					<xsl:value-of select="VALUE" />
				</xsl:attribute>
						</input>
					</xsl:for-each>

					<!-- ソートプルダウンのID -->
					<input type="hidden" name="c_sort1_id" value="" />
					<input type="hidden" name="c_sort2_id" value="" />
					<input type="hidden" name="c_sort3_id" value="" />

					<span id="area" class="area" style="visibility:hidden;">

						<!--*********************************** -->
						<table>
							<tr>
								<td class="title">Forecast検索</td>
								<td>
									<table>
										<tr>
											<!-- 検索条件 -->
											<!-- ”スタンダード検索” -->
											<td class="search_title" id="title_standard"
												onClick="JavaScript:changeSearchType('title_standard');"
												style="cursor:pointer;border-style:solid; border-width:3px; border-color:#669966;">
												<xsl:call-template name="BREAK_COLUMN">
													<xsl:with-param name="value">
														<xsl:value-of select="$STANDARD_SEARCH" />
													</xsl:with-param>
												</xsl:call-template>
											</td>
											<!-- ”プロジェクト検索” -->
											<td class="search_title" id="title_project"
												onClick="JavaScript:changeSearchType('title_project');"
												style="cursor:pointer;border-style:solid; border-width:3px; border-color:#669966;">
												<xsl:call-template name="BREAK_COLUMN">
													<xsl:with-param name="value">
														<xsl:value-of select="$PROJECT_SEARCH" />
													</xsl:with-param>
												</xsl:call-template>
											</td>
											<!-- ”契約先検索” -->
											<td class="search_title" id="title_contract"
												onClick="JavaScript:changeSearchType('title_contract');"
												style="cursor:pointer;border-style:solid; border-width:3px; border-color:#669966;">
												<xsl:call-template name="BREAK_COLUMN">
													<xsl:with-param name="value">
														<xsl:value-of select="$CUSTOMER_SEARCH" />
													</xsl:with-param>
												</xsl:call-template>
											</td>
											<!-- ”フル条件検索” -->
											<td class="search_title" id="title_full"
												onClick="JavaScript:changeSearchType('title_full');"
												style="cursor:pointer;border-style:solid; border-width:3px; border-color:#669966;">
												<xsl:call-template name="BREAK_COLUMN">
													<xsl:with-param name="value">
														<xsl:value-of select="$ADVANCED_SEARCH" />
													</xsl:with-param>
												</xsl:call-template>
											</td>
										</tr>
									</table>
								</td>
								<td>
									<table>
										<tr>
											<!-- Revenue or CI -->
											<td class="revenue_ci">
												<xsl:call-template name="REVENUE_CI" />
											</td>
										</tr>
									</table>
								</td>
								<td>
									<table>
										<tr>
											<!-- Forecast履歴 -->
											<td class="history_flg">
												<table>
													<tr>
														<td>
															<a>
																<xsl:attribute name="href">
											<xsl:text>JavaScript:void(0);</xsl:text>
										</xsl:attribute>
																<xsl:attribute name="onClick">
											<xsl:text>JavaScript:WinOpen('explain/ForecastHistoryExplain.html','forecastHistoryExplain',420,400);</xsl:text>
										</xsl:attribute>
																<!-- ”履歴を表示” -->
																<xsl:call-template name="BREAK_COLUMN">
																	<xsl:with-param name="value">
																		<xsl:value-of select="$HISTORY_DATA_AS_OF" />
																	</xsl:with-param>
																</xsl:call-template>
															</a>
														</td>
														<td>
															<!-- Vに変更一覧 VP/DM機能 CADM=7 VP/DM=3 -->
															<xsl:if
																test="/doc-root/POSITION_IDSET/POSITION_ID/POSITION_ID[.='3'] or $super_user='true'">
																<input type="checkbox" name="to_v_list" value="on"
																	onClick="Javascript:changeToVList();">
																	<xsl:if test="$to_v_list='true'">
																		<xsl:attribute name="checked">checked</xsl:attribute>
																	</xsl:if>
																</input>
															</xsl:if>
														</td>
														<td>
															<xsl:if
																test="/doc-root/POSITION_IDSET/POSITION_ID/POSITION_ID[.='3'] or $super_user='true'">
																<a>
																	<xsl:attribute name="href">
												<xsl:text>JavaScript:void(0);</xsl:text>
											</xsl:attribute>
																	<xsl:attribute name="onClick">
												<xsl:text>JavaScript:WinOpen('explain/to_v_list_explain.html','to_v_list_explain',480,380);</xsl:text>
											</xsl:attribute>
																	<!-- ”Vに変更一覧” -->
																	<xsl:call-template name="BREAK_COLUMN">
																		<xsl:with-param name="value">
																			<xsl:value-of select="$CHANGED_TO_VOID_STATUS_AFTER" />
																		</xsl:with-param>
																	</xsl:call-template>
																</a>
															</xsl:if>
														</td>
													</tr>
													<tr>
														<td colspan="3">
															<select size="1" name="c_history_id"
																onChange="Javascript:checkUpdateButton();">
																<option value="">
																	<!-- ”現在” -->
																	<xsl:value-of select="$CURRENT" />
																</option>
																<xsl:apply-templates select="doc-root/FORECAST_HISTORYSET" />
															</select>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
								<td>
									<table>
										<tr>
											<td>
												<!-- excelか否か -->
												<input type="checkbox" name="csv" value="yes">
													<xsl:if test="$c_csv_check='true'">
														<xsl:attribute name="checked">checked</xsl:attribute>
													</xsl:if>
												</input>
											</td>
											<td>
												<!-- ”csv表示” -->
												<xsl:call-template name="BREAK_COLUMN">
													<xsl:with-param name="value">
														<xsl:value-of select="$CSV_EXPORT" />
													</xsl:with-param>
												</xsl:call-template>
											</td>
											<!-- フル桁チェック -->
											<td>
												<input type="checkbox" name="isFull" value="yes">
													<xsl:if test="$c_full_check='true'">
														<xsl:attribute name="checked">checked</xsl:attribute>
													</xsl:if>
												</input>
											</td>
											<td>
												<!-- ”フル桁表示” -->
												<xsl:call-template name="BREAK_COLUMN">
													<xsl:with-param name="value">
														<xsl:value-of select="$FULL_DIGIT_DISPLAY" />
													</xsl:with-param>
												</xsl:call-template>
											</td>
											<td>
												<!-- 業務委託明細か否か -->
												<input type="checkbox" name="cont_meisai_flg" value="yes">
													<xsl:if test="$c_meisai_check='true'">
														<xsl:attribute name="checked">checked</xsl:attribute>
													</xsl:if>
												</input>
											</td>
											<td>
												<a>
													<xsl:attribute name="href">
											<xsl:text>JavaScript:void(0);</xsl:text>
										</xsl:attribute>
													<xsl:attribute name="onClick">
											<xsl:text>JavaScript:WinOpen('explain/ProjGyomuitakuMeisai.html','projGyoumuitakuMeisai',420,400);</xsl:text>
										</xsl:attribute>
													<!-- ”業務委託明細” -->
													<xsl:call-template name="BREAK_COLUMN">
														<xsl:with-param name="value">
															<xsl:value-of select="$SUBCONTRACT_DETAILS" />
														</xsl:with-param>
													</xsl:call-template>
												</a>
											</td>
										</tr>
										<tr>
											<!-- Done非表示 -->
											<td>
												<input type="checkbox" name="noDone" value="yes">
													<xsl:if test="$c_done_check='true'">
														<xsl:attribute name="checked">checked</xsl:attribute>
													</xsl:if>
												</input>
											</td>
											<td>
												<!-- ”Done別表示” -->
												<xsl:call-template name="BREAK_COLUMN">
													<xsl:with-param name="value">
														<xsl:value-of select="$OMIT_COMPLETE_PJ" />
													</xsl:with-param>
												</xsl:call-template>
											</td>
											<!-- レベトラ非表示 -->
											<td>
												<input type="checkbox" name="noRev" value="yes">
													<xsl:if test="$c_rev_check='true'">
														<xsl:attribute name="checked">checked</xsl:attribute>
													</xsl:if>
												</input>
											</td>
											<td>
												<!-- ”レベトラ表示” -->
												<xsl:call-template name="BREAK_COLUMN">
													<xsl:with-param name="value">
														<xsl:value-of select="$OMIT_REVENUE_TRANSFER_DATA" />
													</xsl:with-param>
												</xsl:call-template>
											</td>
											<!-- 言語切り替えボタン -->
											<td colspan="2">
												<xsl:choose>
													<xsl:when test="$fy12_flg!='indusA'">
														<input type="button" name="submit_language_button"
															class="button_" onClick="JavaScript:changeLanguage();">
															<xsl:attribute name="value"><xsl:value-of
																select="concat('　',$CHANGE_LANGUAGE,'　')" /></xsl:attribute>
														</input>
													</xsl:when>
												</xsl:choose>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>


						<!--*********************************** -->
						<table>
							<tr>
								<td>
									<table>
										<tr>
											<th><!-- ”FY” -->
												<xsl:value-of select="$FY" />
											</th>
											<th><!-- ”表示開始” -->
												<xsl:call-template name="BREAK_COLUMN">
													<xsl:with-param name="value">
														<xsl:value-of select="$REV_DISPLAY_START_MONTH" />
													</xsl:with-param>
												</xsl:call-template>
											</th>
											<th><!-- ”表示期間” -->
												<xsl:call-template name="BREAK_COLUMN">
													<xsl:with-param name="value">
														<xsl:value-of select="$REV_DISPLAY_DURATION" />
													</xsl:with-param>
												</xsl:call-template>
											</th>
										</tr>
										<tr>
											<!-- FY選択 -->
											<td class="search">
												<select size="1" name="c_fy"
													onChange="Javascript:disableFY();getOrg();">
													<!-- PROD組織カットはFY12の概念 -->
													<xsl:choose>
														<xsl:when test="$fy12_flg='indusA'">
															<option>
																<xsl:attribute name="value">2012</xsl:attribute>
																<xsl:attribute name="selected">selected</xsl:attribute>
																2012
															</option>
														</xsl:when>
														<xsl:otherwise>
															<xsl:call-template name="SHOW_YEAR">
																<xsl:with-param name="c_fy">
																	<xsl:value-of select="$c_fy" />
																</xsl:with-param>
															</xsl:call-template>
														</xsl:otherwise>
													</xsl:choose>
												</select>
											</td>
											<!-- 検索 表示開始月 -->
											<td class="search">
												<select size="1" name="c_start_month"
													onChange="Javascript:disableFY();getOrg();" style="width:80;">
													<xsl:call-template name="SHOW_MONTH">
														<xsl:with-param name="c_start_month">
															<xsl:value-of select="$c_start_month" />
														</xsl:with-param>
													</xsl:call-template>
												</select>
											</td>
											<!-- 検索 表示期間 -->
											<td class="search">
												<select size="1" name="c_show_length" style="width:80;">
													<xsl:call-template name="SHOW_LENGTH" />
												</select>
											</td>
										</tr>
									</table>
								</td>
								<td>
									<!--*********************************** -->
									<div id="standard" class="standard">

										<table>
											<tr>
												<xsl:choose>
													<xsl:when test="$fy12_flg='indusA'">
														<th>組織</th>
													</xsl:when>
													<xsl:otherwise>
														<xsl:if test="$c_fy&lt;='2019'">
															<th><!-- ”組織（コストセンタ階層表示）” -->
																<xsl:value-of select="$ORG_COSTCENTER_HIERARCHY_DISPLAY" />
															</th>
														</xsl:if>
														<xsl:if test="$c_fy>='2012'">
															<th><!-- ”組織（PRJ管理組織階層表示）” -->
																<xsl:value-of select="$ORG_PJ_ORG_HIERARCHY_DISPLAY" />
															</th>
														</xsl:if>
													</xsl:otherwise>
												</xsl:choose>
												<th><!-- ”Work Status” -->
													<xsl:value-of select="$WORK_STATUS" />
												</th>
											</tr>
											<tr>
												<!-- 検索 組織 -->
												<xsl:if test="$c_fy&lt;='2019'">
												<td class="search">
													<select size="1" name="c_org_id" style="width:450px">
														<option value="" />
														<xsl:choose>
															<xsl:when test="$fy12_flg='indusA'">
																<xsl:apply-templates select="doc-root/PRD_ORGSET" />
															</xsl:when>
															<xsl:otherwise>
																<xsl:apply-templates select="doc-root/ORGSET" />
															</xsl:otherwise>
														</xsl:choose>
													</select>
												</td>
												</xsl:if>
												<xsl:if test="$fy12_flg!='indusA' and $c_fy>='2012'">
													<td class="search">
														<select size="1" name="c_prj_org">
															<option value="" />
															<xsl:apply-templates select="doc-root/PRD_ORGSET" />
														</select>
													</td>
												</xsl:if>
												<!-- 検索 Work Status -->
												<td class="search">
													<select size="1" name="c_work_status">
														<option value="" />
														<xsl:apply-templates select="doc-root/WORK_STATUSSET" />
													</select>
												</td>
												<!-- 検索 Forecast Status -->
											</tr>
										</table>

									</div>
									<!--*********************************** -->
								</td>
								<!-- 検索ボタン -->
								<td>
									<table>
										<tr>
											<td>
												<xsl:text>　</xsl:text>
											</td>
										</tr>
										<tr>
											<td>
												<input type="button" name="submit_button" class="button">
													<xsl:attribute name="value"><xsl:value-of
														select="concat('　',$SEARCH,'　')" /></xsl:attribute>
													<xsl:attribute name="onClick">
										<xsl:text>JavaScript:checkForm();</xsl:text>
									</xsl:attribute>
												</input>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>


						<table>
							<tr>
								<td>
									<!--*********************************** -->
									<div id="standard2" class="standard2">
										<table>
											<tr>
												<th><!-- ”Temp Number(*)” -->
													<xsl:value-of select="$TEMP_NUMBER" />
												</th>
												<th><!-- ”Opportunity ID(*)” -->
													<xsl:value-of select="$OPPORTUNITY_ID" />
												</th>
												<th><!-- ”Project Number(*)” -->
													<xsl:value-of select="$PROJECT_NUMBER" />
												</th>
												<th><!-- ”SubIndustry” -->
													<xsl:value-of select="$SUB_INDUSTRY" />
												</th>
												<th><!-- ”Cons Category” -->
													<xsl:value-of select="$CONS_CATEGORY" />
												</th>
												<th><!-- ”PA CC” -->
													<xsl:value-of select="$PA_COSTCENTER" />
												</th>
											</tr>
											<tr>
												<!-- Project Code -->
												<td class="">
													<input type="text" name="c_proj_code">
														<xsl:attribute name="value"><xsl:value-of
															select="$c_proj_code" /></xsl:attribute>
													</input>
												</td>
												<!-- OppoNumber -->
												<td class="">
													<input type="text" name="c_oppo_number">
														<xsl:attribute name="value"><xsl:value-of
															select="$c_oppo_number" /></xsl:attribute>
													</input>
												</td>
												<!-- GSI Project Code -->
												<td class="">
													<input type="text" name="c_gsi_proj_code">
														<xsl:attribute name="value"><xsl:value-of
															select="$c_gsi_proj_code" /></xsl:attribute>
													</input>
												</td>
												<!-- 分類 -->
												<td class="">
													<select size="1" name="c_bunrui_code">
														<option value="" />
														<xsl:apply-templates select="doc-root/BUNRUISET" />
													</select>
												</td>
												<!-- ConsCategory -->
												<td class="">
													<select size="1" name="c_category_id">
														<option value="" />
														<xsl:apply-templates select="doc-root/CONS_CATEGORYSSET" />
													</select>
												</td>
												<!-- PA_CC_ID -->
												<td class="">
													<input type="text" name="c_pa_cc_id">
														<xsl:attribute name="value">
															<xsl:value-of select="$c_pa_cc_id" />
														</xsl:attribute>
													</input>
												</td>
											</tr>
										</table>
									</div>
								</td>
								<td>
									<!--*********************************** -->
									<div id="project" class="project">
										<table>
											<tr>
												<th><!-- ”Project Name(*)” -->
													<xsl:value-of select="$PROJECT_NAME" />
												</th>
											</tr>
											<tr>
												<!-- Project Name -->
												<td class="search">
													<input type="text" name="c_proj_name">
														<xsl:attribute name="value"><xsl:value-of
															select="$c_proj_name" /></xsl:attribute>
													</input>
												</td>
											</tr>
										</table>
									</div>
									<!--*********************************** -->
								</td>

								<!--*********************************** -->

								<!-- <td> -->
								<!-- <div id="standard3" class="standard3"> -->
								<!-- <table> -->
								<!-- <tr> -->
								<!-- <th>”PrimarySales” -->
								<!-- <xsl:value-of select="$PRIMARY_SALES" /> -->
								<!-- </th> -->
								<!-- <th>”PO(*)” -->
								<!-- <xsl:value-of select="$PO" /> -->
								<!-- <a> -->
								<!-- <xsl:attribute name="href">JavaScript:void(0);</xsl:attribute> -->
								<!-- <xsl:attribute name="onClick"> -->
								<!-- <xsl:text>JavaScript:</xsl:text> -->
								<!-- <xsl:text>setObjId(window.document.form1.c_proj_owner_id);</xsl:text> -->
								<!-- <xsl:text>setObjName(window.document.form1.c_proj_owner_name);</xsl:text> -->
								<!-- <xsl:text>setObjOrgId(null);</xsl:text> -->
								<!-- <xsl:text>setObjOrgName(null);</xsl:text> -->
								<!-- <xsl:text>WinOpenS('</xsl:text> -->
								<!-- <xsl:value-of select="$uri" /> -->
								<!-- <xsl:text>?</xsl:text> -->
								<!-- <xsl:value-of select="$code_name" /> -->
								<!-- <xsl:text>=showEmpList&amp;emp_name='+window.document.form1.c_proj_owner_name.value</xsl:text> -->
								<!-- <xsl:text>+'&amp;del_flg=ok','ProjectOwner',500,400);</xsl:text> -->
								<!-- </xsl:attribute> -->
								<!-- <xsl:attribute name="onMouseOver"> -->
								<!-- <xsl:text>JavaScript:</xsl:text> -->
								<!-- <xsl:text>window.status='PO';return true;</xsl:text> -->
								<!-- </xsl:attribute> -->
								<!-- <xsl:attribute name="onMouseOut"> -->
								<!-- <xsl:text>JavaScript:</xsl:text> -->
								<!-- <xsl:text>window.status='';</xsl:text> -->
								<!-- </xsl:attribute> -->

								<!-- <img src="explain/search.gif" border="0" /> -->
								<!-- </a> -->
								<!-- </th> -->
								<!-- <th>”PM(*)” -->
								<!-- <xsl:value-of select="$PM" /> -->
								<!-- <a> -->
								<!-- <xsl:attribute name="href">JavaScript:void(0);</xsl:attribute> -->
								<!-- <xsl:attribute name="onClick"> -->
								<!-- <xsl:text>JavaScript:</xsl:text> -->
								<!-- <xsl:text>setObjId(window.document.form1.c_proj_mgr_id);</xsl:text> -->
								<!-- <xsl:text>setObjName(window.document.form1.c_proj_mgr_name);</xsl:text> -->
								<!-- <xsl:text>setObjOrgId(null);</xsl:text> -->
								<!-- <xsl:text>setObjOrgName(null);</xsl:text> -->
								<!-- <xsl:text>WinOpenS('</xsl:text> -->
								<!-- <xsl:value-of select="$uri" /> -->
								<!-- <xsl:text>?</xsl:text> -->
								<!-- <xsl:value-of select="$code_name" /> -->
								<!-- <xsl:text>=showEmpList&amp;emp_name='+window.document.form1.c_proj_mgr_name.value</xsl:text> -->
								<!-- <xsl:text>+'&amp;del_flg=ok','ProjectManager',500,400);</xsl:text> -->
								<!-- </xsl:attribute> -->
								<!-- <xsl:attribute name="onMouseOver"> -->
								<!-- <xsl:text>JavaScript:</xsl:text> -->
								<!-- <xsl:text>window.status='PM';return true;</xsl:text> -->
								<!-- </xsl:attribute> -->
								<!-- <xsl:attribute name="onMouseOut"> -->
								<!-- <xsl:text>JavaScript:</xsl:text> -->
								<!-- <xsl:text>window.status='';</xsl:text> -->
								<!-- </xsl:attribute> -->

								<!-- <img src="explain/search.gif" border="0" /> -->
								<!-- </a> -->
								<!-- </th> -->
								<!-- <xsl:if -->
								<!-- test="/doc-root/POSITION_IDSET/POSITION_ID/POSITION_ID[.='3'or
									.='7'] or $super_user='true'"> -->
								<!-- <td align="center"> -->
								<!-- <b> -->
								<!-- ”担当アドミ(*)” -->
								<!-- <xsl:value-of select="$ADMINISTRATOR" /> -->
								<!-- </b> -->
								<!-- <input type="checkbox" value="on" name="c_admi_isnull" -->
								<!-- onClick="Javascript:admiIsNull();"> -->
								<!-- <xsl:if test="$c_admi_isnull='true'"> -->
								<!-- <xsl:attribute name="checked">checked</xsl:attribute> -->
								<!-- </xsl:if> -->
								<!-- ”空白” -->
								<!-- <xsl:value-of select="$BLANK" /> -->
								<!-- </input> -->
								<!-- </td> -->
								<!-- </xsl:if> -->
								<!-- </tr> -->
								<!-- <tr> -->
								<!-- PrimarySales -->
								<!-- <td class="search"> -->
								<!-- <input type="text" name="c_sales"> -->
								<!-- <xsl:attribute name="value"><xsl:value-of -->
								<!-- select="$c_sales" /></xsl:attribute> -->
								<!-- </input> -->
								<!-- </td> -->
								<!-- PO -->
								<!-- <td class="search"> -->
								<!-- <input type="hidden" name="c_proj_owner_id" /> -->
								<!-- <input type="text" name="c_proj_owner_name"> -->
								<!-- <xsl:attribute name="value"><xsl:value-of -->
								<!-- select="$c_proj_owner_name" /></xsl:attribute> -->
								<!-- </input> -->
								<!-- </td> -->
								<!-- PM -->
								<!-- <td class="search"> -->
								<!-- <input type="hidden" name="c_proj_mgr_id" /> -->
								<!-- <input type="text" name="c_proj_mgr_name"> -->
								<!-- <xsl:attribute name="value"><xsl:value-of -->
								<!-- select="$c_proj_mgr_name" /></xsl:attribute> -->
								<!-- </input> -->
								<!-- </td> -->
								<!-- 担当アドミ -->
								<!-- <xsl:if -->
								<!-- test="/doc-root/POSITION_IDSET/POSITION_ID/POSITION_ID[.='3'
									or .='7'] or $super_user='true'"> -->
								<!-- <td align="left" class="search_x"> -->
								<!-- <input type="text" name="c_admi"> -->
								<!-- <xsl:attribute name="value"><xsl:value-of -->
								<!-- select="$c_admi" /></xsl:attribute> -->
								<!-- </input> -->
								<!-- </td> -->
								<!-- </xsl:if> -->
								<!-- </tr> -->
								<!-- <xsl:if -->
								<!-- test="/doc-root/POSITION_IDSET/POSITION_ID/POSITION_ID[.='3'
									or .='7'] or $super_user='true'"> -->
								<!-- <tr> -->
								<!-- <td colspan="9" align="right"> -->
								<!-- ”(*)あいまい検索可能” -->
								<!-- <xsl:value-of select="$PARTIAL_MATCH_AVAILABLE" /> -->
								<!-- </td> -->
								<!-- </tr> -->
								<!-- </xsl:if> -->
								<!-- </table> -->
								<!-- </div> -->

								<!-- </td> -->
								<!--*********************************** -->

								<td>
									<div id="description" class="description">
									</div>
								</td>
							</tr>
						</table>
						<!--*********************************** -->
						<div id="standard3" class="standard3">
							<table>
								<tr>
									<th><!-- ”PrimarySales” -->
										<xsl:value-of select="$PRIMARY_SALES" />
									</th>
									<th><!-- ”PO(*)” -->
										<xsl:value-of select="$PO" />
										<a>
											<xsl:attribute name="href">JavaScript:void(0);</xsl:attribute>
											<xsl:attribute name="onClick">
												<xsl:text>JavaScript:</xsl:text>
												<xsl:text>setObjId(window.document.form1.c_proj_owner_id);</xsl:text>
												<xsl:text>setObjName(window.document.form1.c_proj_owner_name);</xsl:text>
												<xsl:text>setObjOrgId(null);</xsl:text>
												<xsl:text>setObjOrgName(null);</xsl:text>
												<xsl:text>WinOpenS('</xsl:text>
												<xsl:value-of select="$uri" />
												<xsl:text>?</xsl:text>
												<xsl:value-of select="$code_name" />
												<xsl:text>=showEmpList&amp;emp_name='+window.document.form1.c_proj_owner_name.value</xsl:text>
												<xsl:text>+'&amp;del_flg=ok','ProjectOwner',500,400);</xsl:text>
											</xsl:attribute>
											<xsl:attribute name="onMouseOver">
												<xsl:text>JavaScript:</xsl:text>
												<xsl:text>window.status='PO';return true;</xsl:text>
											</xsl:attribute>
											<xsl:attribute name="onMouseOut">
												<xsl:text>JavaScript:</xsl:text>
												<xsl:text>window.status='';</xsl:text>
											</xsl:attribute>

											<img src="explain/search.gif" border="0" />
										</a>
									</th>
									<th><!-- ”PM(*)” -->
										<xsl:value-of select="$PM" />
										<a>
											<xsl:attribute name="href">JavaScript:void(0);</xsl:attribute>
											<xsl:attribute name="onClick">
												<xsl:text>JavaScript:</xsl:text>
												<xsl:text>setObjId(window.document.form1.c_proj_mgr_id);</xsl:text>
												<xsl:text>setObjName(window.document.form1.c_proj_mgr_name);</xsl:text>
												<xsl:text>setObjOrgId(null);</xsl:text>
												<xsl:text>setObjOrgName(null);</xsl:text>
												<xsl:text>WinOpenS('</xsl:text>
												<xsl:value-of select="$uri" />
												<xsl:text>?</xsl:text>
												<xsl:value-of select="$code_name" />
												<xsl:text>=showEmpList&amp;emp_name='+window.document.form1.c_proj_mgr_name.value</xsl:text>
												<xsl:text>+'&amp;del_flg=ok','ProjectManager',500,400);</xsl:text>
											</xsl:attribute>
											<xsl:attribute name="onMouseOver">
												<xsl:text>JavaScript:</xsl:text>
												<xsl:text>window.status='PM';return true;</xsl:text>
											</xsl:attribute>
											<xsl:attribute name="onMouseOut">
												<xsl:text>JavaScript:</xsl:text>
												<xsl:text>window.status='';</xsl:text>
											</xsl:attribute>

											<img src="explain/search.gif" border="0" />
										</a>
									</th>
									<xsl:if
										test="/doc-root/POSITION_IDSET/POSITION_ID/POSITION_ID[.='3'or .='7'] or $super_user='true'">
										<td align="center">
											<b>
												<!-- ”担当アドミ(*)” -->
												<xsl:value-of select="$ADMINISTRATOR" />
											</b>
											<input type="checkbox" value="on" name="c_admi_isnull"
												onClick="Javascript:admiIsNull();">
												<xsl:if test="$c_admi_isnull='true'">
													<xsl:attribute name="checked">checked</xsl:attribute>
												</xsl:if>
												<!-- ”空白” -->
												<xsl:value-of select="$BLANK" />
											</input>
										</td>
									</xsl:if>
								</tr>
								<tr>
									<!-- PrimarySales -->
									<td class="search">
										<input type="text" name="c_sales">
											<xsl:attribute name="value"><xsl:value-of
												select="$c_sales" /></xsl:attribute>
										</input>
									</td>
									<!-- PO -->
									<td class="search">
										<input type="hidden" name="c_proj_owner_id" />
										<input type="text" name="c_proj_owner_name">
											<xsl:attribute name="value"><xsl:value-of
												select="$c_proj_owner_name" /></xsl:attribute>
										</input>
									</td>
									<!-- PM -->
									<td class="search">
										<input type="hidden" name="c_proj_mgr_id" />
										<input type="text" name="c_proj_mgr_name">
											<xsl:attribute name="value"><xsl:value-of
												select="$c_proj_mgr_name" /></xsl:attribute>
										</input>
									</td>
									<!-- 担当アドミ -->
									<xsl:if
										test="/doc-root/POSITION_IDSET/POSITION_ID/POSITION_ID[.='3' or .='7'] or $super_user='true'">
										<td align="left" class="search_x">
											<input type="text" name="c_admi">
												<xsl:attribute name="value"><xsl:value-of
													select="$c_admi" /></xsl:attribute>
											</input>
										</td>
									</xsl:if>

									<xsl:if
										test="/doc-root/POSITION_IDSET/POSITION_ID/POSITION_ID[.='3' or .='7'] or $super_user='true'">

										<td>
											<!-- ”(*)あいまい検索可能” -->
											<xsl:value-of select="$PARTIAL_MATCH_AVAILABLE" />
										</td>

									</xsl:if>
									<td align="center">
										<input type="checkbox" value="on" name="skuOnly">
											<xsl:if test="$skuOnly='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”空白” -->
											<xsl:value-of select="$SKU" />
										</input>
									</td>
								</tr>
							</table>
						</div>

						<!--*********************************** -->
						<div id="contract" class="contract">
							<table>
								<tr>
									<th><!-- ”エンドユーザー名(*)” -->
										<xsl:value-of select="$END_USER" />
									</th>
									<th><!-- ”契約先社名(*)” -->
										<xsl:value-of select="$CUSTOMER" />
									</th>
								</tr>
								<tr>
									<!-- エンドユーザー名 -->
									<td class="">
										<input type="text" name="c_end_user_name">
											<xsl:attribute name="value"><xsl:value-of
												select="$c_end_user_name" /></xsl:attribute>
										</input>
									</td>
									<!-- 契約先社名 -->
									<td class="">
										<input type="text" name="c_client_name">
											<xsl:attribute name="value"><xsl:value-of
												select="$c_client_name" /></xsl:attribute>
										</input>
									</td>
								</tr>
							</table>
						</div>
						<!--*********************************** -->


						<!--*********************************** -->
						<div id="full" class="full">
							<table>
								<tr>
									<th><!-- ”契約形態” -->
										<xsl:value-of select="$CONTRACT_TYPE" />
									</th>
									<th><!-- ”Classification” -->
										<xsl:value-of select="$CLASSIFICATION" />
									</th>
									<th><!-- ”Booking管理組織（Appsのみ）” -->
										<xsl:value-of select="$BOOKING_ORG" />
									</th>
								</tr>
								<tr>
									<!-- 契約形態 -->
									<td class="search">
										<select size="1" name="c_contract_id">
											<option value="" />
											<xsl:apply-templates select="doc-root/CONT_TYPESET" />
										</select>
									</td>
									<!-- Classification -->
									<td class="search_">
										<select size="1" name="c_classification">
											<option value="" />
											<xsl:apply-templates select="doc-root/CLASSIFICATIONSET" />
										</select>
									</td>
									<!-- Booking_org -->
									<td class="search_">
										<select size="1" name="c_booking_org">
											<option value="" />
											<xsl:apply-templates select="doc-root/BOOKING_ORGSET" />
										</select>
									</td>
								</tr>
							</table>
						</div>
						<!--*********************************** -->

						<table border="2"
							style="vertical-align:top; border-style:solid; border-width:2px;">
							<tr id="forecast_check_bg">
								<th>
									<input type="radio" name="c_fstatus_winprob" onClick="JavaScript:changeStatusType();">
										<xsl:attribute name="value">0</xsl:attribute>
										<xsl:if test="$c_fstatus_winprob='0'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<!-- ”Booking Status” -->
										<xsl:value-of select="$BOOKING_STATUS" />
									</input>
								</th>
								<th bgcolor="#666666"></th>
								<th>
									<input type="radio" name="c_fstatus_winprob" onClick="JavaScript:changeStatusType();">
										<xsl:attribute name="value">1</xsl:attribute>
										<xsl:if test="$c_fstatus_winprob='1'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<!-- ”Revenue Status” -->
										<xsl:value-of select="$REVENUE_STATUS" />
									</input>
								</th>
								<th bgcolor="#666666"></th>
								<th>
									<input type="radio" name="c_fstatus_winprob" onClick="JavaScript:changeStatusType();">
										<xsl:attribute name="value">2</xsl:attribute>
										<xsl:if test="$c_fstatus_winprob='2'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<!-- ”Win Prob%” -->
										<xsl:value-of select="$WINPROB" />
									</input>
								</th>
							</tr>
							<tr>
								<td id="forecast_check_bg">
									<input type="checkbox" name="c_book_status_check1">
										<xsl:if test="$c_book_status_check1='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Prospect　　</xsl:text>
									</input>
									<input type="checkbox" name="c_book_status_check2">
										<xsl:if test="$c_book_status_check2='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Best　　</xsl:text>
									</input>
									<input type="checkbox" name="c_book_status_check3">
										<xsl:if test="$c_book_status_check3='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Forecast　　</xsl:text>
									</input>
									<br />
									<input type="checkbox" name="c_book_status_check4">
										<xsl:if test="$c_book_status_check4='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Worst　　</xsl:text>
									</input>
									<input type="checkbox" name="c_book_status_check5">
										<xsl:if test="$c_book_status_check5='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Backlog　　</xsl:text>
									</input>
									<input type="checkbox" name="c_book_status_check99">
										<xsl:if test="$c_book_status_check99='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Void</xsl:text>
									</input>
								</td>
								<td bgcolor="#666666"></td>
								<td id="forecast_check_bg">
									<input type="checkbox" name="c_rev_status_check1">
										<xsl:if test="$c_rev_status_check1='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Prospect　　</xsl:text>
									</input>
									<input type="checkbox" name="c_rev_status_check2">
										<xsl:if test="$c_rev_status_check2='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Best　　</xsl:text>
									</input>
									<input type="checkbox" name="c_rev_status_check3">
										<xsl:if test="$c_rev_status_check3='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Forecast　　</xsl:text>
									</input>
									<br />
									<input type="checkbox" name="c_rev_status_check4">
										<xsl:if test="$c_rev_status_check4='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Worst　　</xsl:text>
									</input>
									<input type="checkbox" name="c_rev_status_check5">
										<xsl:if test="$c_rev_status_check5='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Backlog　　</xsl:text>
									</input>
									<input type="checkbox" name="c_rev_status_check99">
										<xsl:if test="$c_rev_status_check99='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Void</xsl:text>
									</input>
								</td>
								<td bgcolor="#666666"></td>
								<td id="forecast_check_bg">
									<xsl:text>　　</xsl:text>
									<input type="checkbox" name="c_win_prob_check0">
										<xsl:if test="$c_win_prob_check0='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>0　　</xsl:text>
									</input>
									<input type="checkbox" name="c_win_prob_check10">
										<xsl:if test="$c_win_prob_check10='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>10　　</xsl:text>
									</input>
									<input type="checkbox" name="c_win_prob_check20">
										<xsl:if test="$c_win_prob_check20='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>20　　</xsl:text>
									</input>
									<input type="checkbox" name="c_win_prob_check30">
										<xsl:if test="$c_win_prob_check30='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>30　　</xsl:text>
									</input>
									<input type="checkbox" name="c_win_prob_check40">
										<xsl:if test="$c_win_prob_check40='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>40　　</xsl:text>
									</input>
									<br />
									<xsl:text>　　</xsl:text>
									<input type="checkbox" name="c_win_prob_check50">
										<xsl:if test="$c_win_prob_check50='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>50　　</xsl:text>
									</input>
									<input type="checkbox" name="c_win_prob_check60">
										<xsl:if test="$c_win_prob_check60='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>60　　</xsl:text>
									</input>
									<input type="checkbox" name="c_win_prob_check70">
										<xsl:if test="$c_win_prob_check70='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>70　　</xsl:text>
									</input>
									<input type="checkbox" name="c_win_prob_check80">
										<xsl:if test="$c_win_prob_check80='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>80　　</xsl:text>
									</input>
									<input type="checkbox" name="c_win_prob_check90">
										<xsl:if test="$c_win_prob_check90='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>90　　</xsl:text>
									</input>
									<input type="checkbox" name="c_win_prob_check100">
										<xsl:if test="$c_win_prob_check100='true'">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>100　　</xsl:text>
									</input>
								</td>

							</tr>
						</table>

						<table>
							<tr>
								<th><!-- ”ソート１” -->
									<xsl:value-of select="$SORT_KEY1" />
								</th>
								<th><!-- ”ソート２” -->
									<xsl:value-of select="$SORT_KEY2" />
								</th>
								<th><!-- ”ソート３” -->
									<xsl:value-of select="$SORT_KEY3" />
								</th>
							</tr>
							<tr>
								<!-- ソート１ -->
								<td class="search">
									<select size="1" name="c_sort1">
										<xsl:call-template name="SORTSET">
											<xsl:with-param name="default">
												<xsl:value-of select="$c_sort1" />
											</xsl:with-param>
										</xsl:call-template>
									</select>
								</td>
								<!-- ソート２ -->
								<td class="search">
									<select size="1" name="c_sort2">
										<xsl:call-template name="SORTSET">
											<xsl:with-param name="default">
												<xsl:value-of select="$c_sort2" />
											</xsl:with-param>
										</xsl:call-template>
									</select>
								</td>
								<!-- ソート３ -->
								<td class="search">
									<select size="1" name="c_sort3">
										<xsl:call-template name="SORTSET">
											<xsl:with-param name="default">
												<xsl:value-of select="$c_sort3" />
											</xsl:with-param>
										</xsl:call-template>
									</select>
								</td>
							</tr>
						</table>

						<br />

						<!--*********************************** -->
						<div id="showed_column" class="showed_column">
							<table>
								<tr>
									<td colspan="6" align="center">
										***** <!-- ”以下は、表示列選択項目です。” -->
										<xsl:value-of select="$CHECK_COLUMNS_TO_DISPLAY" />
										*****
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" name="o_prd_org_name">
											<xsl:if test="$o_prd_org_name='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”PRJ管理組織” -->
											<xsl:value-of select="$PJ_ORG_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_org_name">
											<xsl:if test="$o_org_name='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”MP組織” -->
											<xsl:value-of select="$MP_ORG_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_fpa">
											<xsl:if test="$o_fpa='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”FPAuto” -->
											<xsl:value-of select="$FP_AUTO_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_work_status">
											<xsl:if test="$o_work_status='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”Work Status” -->
											<xsl:value-of select="$WORK_STATUS_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_win_prob">
											<xsl:if test="$o_win_prob='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”Win Prob” -->
											<xsl:value-of select="$PROBABILITY_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_book_status">
											<xsl:if test="$o_book_status='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”Book Status” -->
											<xsl:value-of select="$BOOK_STATUS_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_rev_status">
											<xsl:if test="$o_rev_status='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”Revenue Status” -->
											<xsl:value-of select="$REVENUE_STATUS_CHECK" />
										</input>
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" name="o_oppo_status">
											<xsl:if test="$o_oppo_status='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”Opportunity Status” -->
											<xsl:value-of select="$OPPORTUNITY_STATUS_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_progress">
											<xsl:if test="$o_progress='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”SalesStage” -->
											<xsl:value-of select="$SALES_STAGE_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_proj_code">
											<xsl:if test="$o_proj_code='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”Temp Number” -->
											<xsl:value-of select="$TEMP_NUMBER_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_bunrui">
											<xsl:if test="$o_bunrui='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”SubIndustry” -->
											<xsl:value-of select="$SUB_INDUSTRY_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_oppo_number">
											<xsl:if test="$o_oppo_number='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”Opportunity ID” -->
											<xsl:value-of select="$OPTY_ID_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_gsi_proj_code">
											<xsl:if test="$o_gsi_proj_code='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”ProjectNumber” -->
											<xsl:value-of select="$PROJECT_NUMBER_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_client_name">
											<xsl:if test="$o_client_name='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”契約先名” -->
											<xsl:value-of select="$CUSTOMER_CHECK" />
										</input>
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" name="o_end_user_name">
											<xsl:if test="$o_end_user_name='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”エンドユーザ名” -->
											<xsl:value-of select="$END_USER_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_eu_official_name">
											<xsl:if test="$o_eu_official_name='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”エンドユーザ正式名” -->
											<xsl:value-of select="$END_USER_CORRECTERD_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_key_acnt">
											<xsl:if test="$o_key_acnt='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”KEY\TLアカウント” -->
											<xsl:value-of select="$KEY_TL_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_proj_name">
											<xsl:if test="$o_proj_name='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”Project Name” -->
											<xsl:value-of select="$PROJECTNAME_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_module">
											<xsl:if test="$o_module='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”Module” -->
											<xsl:value-of select="$MODULE_CHECK" />
										</input>
									</td>
									<td>
										<!-- ”ConsCategory” -->
										<input type="checkbox" name="o_cons_category">
											<xsl:if test="$o_cons_category='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<xsl:value-of select="$CONS_CATEGORY" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_product_name">
											<xsl:if test="$o_product_name='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”Module” -->
											<xsl:value-of select="$PRODUCT_NAME" />
										</input>
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" name="o_consul_type">
											<xsl:if test="$o_consul_type='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”ConsultingType” -->
											<xsl:value-of select="$CONSULTINGTYPE_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_revenue_type">
											<xsl:if test="$o_revenue_type='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”RevenueType” -->
											<xsl:value-of select="$REVENUETYPE_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_classification">
											<xsl:if test="$o_classification='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”CLASSIFICATION” -->
											<xsl:value-of select="$CLASSIFICATION_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_booking_org">
											<xsl:if test="$o_booking_org='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”Booking管理組織（Appsのみ）” -->
											<xsl:value-of select="$BOOKING_ORG_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_project_total">
											<xsl:if test="$o_project_total='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”案件総額（Appsのみ）” -->
											<xsl:value-of select="$SCALE_APPS_ONLY_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_classification_4">
											<xsl:if test="$o_classification_4='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”本部別管理分類” -->
											<xsl:value-of select="$SECTORAL_ANALYSIS_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_sa_info">
											<xsl:if test="$o_sa_info='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”SA（BPM/SOAのみ）” -->
											<xsl:value-of select="$SA_BPM_SOA_ONLY_CHECK" />
										</input>
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" name="o_service_classification">
											<xsl:if test="$o_service_classification='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”サービス分類（Tech）” -->
											<xsl:value-of select="$SERVICE_TYPE_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_sum_budget">
											<xsl:if test="$o_sum_budget='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”予算” -->
											<xsl:value-of select="$BUDGET_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_proj_start">
											<xsl:if test="$o_proj_start='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”プロジェクト開始日” -->
											<xsl:value-of select="$PROJECT_START_DATE_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_proj_end">
											<xsl:if test="$o_proj_end='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”プロジェクト終了日” -->
											<xsl:value-of select="$PROJECT_END_DATE_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_sales">
											<xsl:if test="$o_sales='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”PrimarySales” -->
											<xsl:value-of select="$OPPORTUNITY_OWNER_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_proj_owner_name">
											<xsl:if test="$o_proj_owner_name='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”プロジェクトオーナー” -->
											<xsl:value-of select="$PROJECT_OWNER_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_proj_mgr_name">
											<xsl:if test="$o_proj_mgr_name='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”プロジェクトマネージャー” -->
											<xsl:value-of select="$PROJECT_MANAGER_CHECK" />
										</input>
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" name="o_contract_date">
											<xsl:if test="$o_contract_date='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”契約締結予定日” -->
											<xsl:value-of select="$CLOSE_DATE_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_contract_quarter">
											<xsl:if test="$o_contract_quarter='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”契約締結QTR” -->
											<xsl:value-of select="$BOOKING_QTR_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_opty_creation_date">
											<xsl:if test="$o_opty_creation_date='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- 案件登録日 -->
											<xsl:value-of select="$OPTY_CREATION_DATE" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_cont_amount">
											<xsl:if test="$o_cont_amount='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”(移管時)元契約金額” -->
											<xsl:value-of select="$BOOKING_BEFORE_CC_TRANSFER_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_own_cont_amount">
											<xsl:if test="$o_own_cont_amount='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”契約金額” -->
											<xsl:value-of select="$BOOKING_AMOUNT_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_reason_for_change">
											<xsl:if test="$o_reason_for_change='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”変更理由” -->
											<xsl:value-of select="$CHANGE_REASON_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_contract_type">
											<xsl:if test="$o_contract_type='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”契約形態” -->
											<xsl:value-of select="$CONTRACT_TYPE_CHECK" />
										</input>
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" name="o_ex_cont">
											<xsl:if test="$o_ex_cont='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”業務委託先” -->
											<xsl:value-of select="$SUBCONTRACTOR_CHECK" />
										</input>
									</td>
									<td>
										<!-- ”業務委託ID” -->
										<input type="checkbox" name="o_ex_cont_id">
											<xsl:if test="$o_ex_cont_id='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<xsl:value-of select="$ExContId" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_update_button">
											<xsl:if test="$o_update_button='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”UpdateButton” -->
											<xsl:value-of select="$UPDATEBUTTON_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_industry">
											<xsl:if test="$o_industry='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”インダストリ(Techのみ)” -->
											<xsl:value-of select="$INDUSTRY_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_pa_cc_id">
											<xsl:if test="$o_pa_cc_id='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”PAコストセンタID” -->
											<xsl:value-of select="$PA_COSTCENTER_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_admi">
											<xsl:if test="$o_admi='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”担当アドミ” -->
											<xsl:value-of select="$ADMINISTRATOR_CHECK" />
										</input>
									</td>
									<td>
										<input type="checkbox" name="o_latest_mporg">
											<xsl:if test="$o_latest_mporg='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”最新MP組織” -->
											<xsl:value-of select="$LATEST_MP_ORG_CHECK" />
										</input>
									</td>
								</tr>
								<tr>
									<td>
										<input type="checkbox" name="o_not_book_sum">
											<xsl:if test="$o_not_book_sum='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<!-- ”Booking集計対象外” -->
											<xsl:value-of select="$BOOKING_EXCLUSION_CHECK" />
										</input>
									</td>
									<td>
										<!-- ”LIC.OPPO ID” -->
										<input type="checkbox" name="o_lic_oppo_id">
											<xsl:if test="$o_lic_oppo_id='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<xsl:value-of select="$LIC_OPPO_ID" />
										</input>
									</td>
									<td>
										<!-- ”CONTRACT NUMBER” -->
										<input type="checkbox" name="o_contract_number">
											<xsl:if test="$o_contract_number='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<xsl:value-of select="$CONTRACT_NUMBER" />
										</input>
									</td>
																		<td>
										<!-- ”SKU_NO” -->
										<input type="checkbox" name="o_sku_no">
											<xsl:if test="$o_sku_no='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<xsl:value-of select="$SKU_NO" />
										</input>
									</td>
																		<td>
										<!-- ”SKU_SPAN” -->
										<input type="checkbox" name="o_sku_span">
											<xsl:if test="$o_sku_span='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<xsl:value-of select="$SKU_SPAN" />
										</input>
									</td>
																		<td>
										<!-- ”SKU_REV_REC_RULE” -->
										<input type="checkbox" name="o_sku_rev_rec_rule">
											<xsl:if test="$o_sku_rev_rec_rule='true'">
												<xsl:attribute name="checked">checked</xsl:attribute>
											</xsl:if>
											<xsl:value-of select="$SKU_REV_REC_RULE" />
										</input>
									</td>
								</tr>
								<tr>
									<td colspan="2">
										<!-- 全てチェックボタン -->
										<input type="button" name="allCheckButton">
											<xsl:attribute name="value"><xsl:value-of
												select="$ALL_CHECK" /></xsl:attribute>
											<xsl:attribute name="onClick">
						    <xsl:text>JavaScript:allCheck();</xsl:text>
						  </xsl:attribute>
										</input>
										&#160;&#160;&#160;&#160;&#160;&#160;
										<!-- デフォルトに戻すボタン -->
										<input type="button" name="defaultCheckButton">
											<xsl:attribute name="value"><xsl:value-of
												select="$DEFAULT_CHECK" /></xsl:attribute>
											<xsl:attribute name="onClick">
					        <xsl:text>JavaScript:defaultCheck();</xsl:text>
					      </xsl:attribute>
										</input>
									</td>
								</tr>
							</table>
						</div>
					</span>

				</form>
				<!-- 言語切り替えのテンプレート呼び出し -->
				<xsl:call-template name="CHANGE_LANGUAGE" />
			</body>
		</html>
	</xsl:template>

	<!-- CHANGE_LANGUAGE 言語切り替えのテンプレート -->
	<xsl:template name="CHANGE_LANGUAGE">
		<!-- フォームタグ -->
		<form name="form2" target="up">
			<xsl:attribute name="method">post</xsl:attribute>
			<xsl:attribute name="action"><xsl:value-of select="$uri" /></xsl:attribute>

			<!-- コントロールコード -->
			<input>
				<xsl:attribute name="type">hidden</xsl:attribute>
				<xsl:attribute name="name"><xsl:value-of select="$code_name" /></xsl:attribute>
				<xsl:attribute name="value">showForecastSearch</xsl:attribute>
			</input>

			<!-- 言語切り替えボタンが押されたら変更後の言語を送信 -->
			<input type="hidden" name="after_change_language">
				<xsl:choose>
					<xsl:when test="contains($now_language, 'ja')">
						<xsl:attribute name="value">ENGLISH</xsl:attribute>
					</xsl:when>
					<xsl:otherwise>
						<xsl:attribute name="value">JAPANESE</xsl:attribute>
					</xsl:otherwise>
				</xsl:choose>
			</input>

			<!--********************************************** -->
			<!-- 値が入っている項目はそのまま値を引き継ぐので、hiddenで宣言 -->
			<!--********************************************** -->
			<!-- 検索タイプ -->
			<input type="hidden" name="forecast_type" value="" />
			<!-- 検索条件テキストボックス -->
			<input type="hidden" name="c_proj_code" value="" />
			<input type="hidden" name="c_oppo_number" value="" />
			<input type="hidden" name="c_gsi_proj_code" value="" />
			<input type="hidden" name="c_bunrui_code" value="" />
			<input type="hidden" name="c_category_id" value="" />
			<input type="hidden" name="c_proj_name" value="" />
			<input type="hidden" name="c_sales" value="" />
			<input type="hidden" name="c_proj_owner_name" value="" />
			<input type="hidden" name="c_proj_mgr_name" value="" />
			<input type="hidden" name="c_admi" value="" />
			<input type="hidden" name="c_end_user_name" value="" />
			<input type="hidden" name="c_client_name" value="" />
			<input type="hidden" name="c_contract_id" value="" />
			<input type="hidden" name="c_classification" value="" />
			<input type="hidden" name="c_booking_org" value="" />
			<input type="hidden" name="c_fy" value="" />
			<input type="hidden" name="c_start_month" value="" />
			<input type="hidden" name="c_show_length" value="" />
			<input type="hidden" name="c_org_id" value="" />
			<input type="hidden" name="c_prj_org" value="" />
			<input type="hidden" name="c_work_status" value="" />
			<input type="hidden" name="c_pa_cc_id" value="" />

			<!-- 検索条件チェックボックス -->
			<input type="hidden" name="csv" value="" />
			<input type="hidden" name="isFull" value="" />
			<input type="hidden" name="cont_meisai_flg" value="" />
			<input type="hidden" name="noDone" value="" />
			<input type="hidden" name="noRev" value="" />
			<input type="hidden" name="c_revenue_ci" value="" />
			<input type="hidden" name="c_admi_isnull" value="" />
			<input type="hidden" name="skuOnly" value="" />
			<input type="hidden" name="to_v_list" value="" />
			<input type="hidden" name="c_history_id" value="" />

			<!-- WinProb/Book Status/Revenue Status選択ラジオボタン -->
			<input type="hidden" name="c_fstatus_winprob" value="" />
			<!-- WinProbラジオボタン -->
			<input type="hidden" name="c_win_prob_check0" value="" />
			<input type="hidden" name="c_win_prob_check10" value="" />
			<input type="hidden" name="c_win_prob_check20" value="" />
			<input type="hidden" name="c_win_prob_check30" value="" />
			<input type="hidden" name="c_win_prob_check40" value="" />
			<input type="hidden" name="c_win_prob_check50" value="" />
			<input type="hidden" name="c_win_prob_check60" value="" />
			<input type="hidden" name="c_win_prob_check70" value="" />
			<input type="hidden" name="c_win_prob_check80" value="" />
			<input type="hidden" name="c_win_prob_check90" value="" />
			<input type="hidden" name="c_win_prob_check100" value="" />
			<!-- Book Statusラジオボタン -->
			<input type="hidden" name="c_book_status_check1" value="" />
			<input type="hidden" name="c_book_status_check2" value="" />
			<input type="hidden" name="c_book_status_check3" value="" />
			<input type="hidden" name="c_book_status_check4" value="" />
			<input type="hidden" name="c_book_status_check5" value="" />
			<input type="hidden" name="c_book_status_check99" value="" />
			<!-- Revenue Statusラジオボタン -->
			<input type="hidden" name="c_rev_status_check1" value="" />
			<input type="hidden" name="c_rev_status_check2" value="" />
			<input type="hidden" name="c_rev_status_check3" value="" />
			<input type="hidden" name="c_rev_status_check4" value="" />
			<input type="hidden" name="c_rev_status_check5" value="" />
			<input type="hidden" name="c_rev_status_check99" value="" />

			<!-- ソートプルダウン -->
			<input type="hidden" name="c_sort1" value="" />
			<input type="hidden" name="c_sort2" value="" />
			<input type="hidden" name="c_sort3" value="" />

			<!-- 表示項目チェックボックス -->
			<input type="hidden" name="o_prd_org_name" value="" />
			<input type="hidden" name="o_org_name" value="" />
			<input type="hidden" name="o_fpa" value="" />
			<input type="hidden" name="o_work_status" value="" />
			<input type="hidden" name="o_win_prob" value="" />
			<input type="hidden" name="o_book_status" value="" />
			<input type="hidden" name="o_rev_status" value="" />
			<input type="hidden" name="o_oppo_status" value="" />
			<input type="hidden" name="o_progress" value="" />
			<input type="hidden" name="o_proj_code" value="" />
			<input type="hidden" name="o_bunrui" value="" />
			<input type="hidden" name="o_oppo_number" value="" />
			<input type="hidden" name="o_gsi_proj_code" value="" />
			<input type="hidden" name="o_client_name" value="" />
			<input type="hidden" name="o_end_user_name" value="" />
			<input type="hidden" name="o_eu_official_name" value="" />
			<input type="hidden" name="o_key_acnt" value="" />
			<input type="hidden" name="o_proj_name" value="" />
			<input type="hidden" name="o_module" value="" />
			<input type="hidden" name="o_consul_type" value="" />
			<input type="hidden" name="o_revenue_type" value="" />
			<input type="hidden" name="o_classification" value="" />
			<input type="hidden" name="o_booking_org" value="" />
			<input type="hidden" name="o_project_total" value="" />
			<input type="hidden" name="o_classification_4" value="" />
			<input type="hidden" name="o_sa_info" value="" />
			<input type="hidden" name="o_service_classification" value="" />
			<input type="hidden" name="o_sum_budget" value="" />
			<input type="hidden" name="o_proj_start" value="" />
			<input type="hidden" name="o_proj_end" value="" />
			<input type="hidden" name="o_sales" value="" />
			<input type="hidden" name="o_proj_owner_name" value="" />
			<input type="hidden" name="o_proj_mgr_name" value="" />
			<input type="hidden" name="o_contract_date" value="" />
			<input type="hidden" name="o_contract_quarter" value="" />
			<input type="hidden" name="o_opty_creation_date" value="" />
			<input type="hidden" name="o_cont_amount" value="" />
			<input type="hidden" name="o_own_cont_amount" value="" />
			<input type="hidden" name="o_reason_for_change" value="" />
			<input type="hidden" name="o_contract_type" value="" />
			<input type="hidden" name="o_ex_cont" value="" />
			<input type="hidden" name="o_ex_cont_id" value="" />
			<input type="hidden" name="o_update_button" value="" />
			<input type="hidden" name="o_industry" value="" />
			<input type="hidden" name="o_pa_cc_id" value="" />
			<input type="hidden" name="o_admi" value="" />
			<input type="hidden" name="o_latest_mporg" value="" />
			<input type="hidden" name="o_not_book_sum" value="" />
			<input type="hidden" name="o_lic_oppo_id" value="" />
			<input type="hidden" name="o_contract_number" value="" />
			<input type="hidden" name="o_sku_no" value="" />
			<input type="hidden" name="o_sku_span" value="" />
			<input type="hidden" name="o_sku_rev_rec_rule" value="" />
			<input type="hidden" name="o_product_name" value="" />
			<input type="hidden" name="o_cons_category" value="" />
		</form>
	</xsl:template>


	<!-- Revenue-CI テンプレート -->
	<xsl:template name="REVENUE_CI">

		<input type="radio" name="c_revenue_ci">
			<xsl:attribute name="value">0</xsl:attribute>
			<xsl:if test="$c_revenue_ci='0'">
				<xsl:attribute name="checked">checked</xsl:attribute>
			</xsl:if>
			<!-- "Revenue" -->
			<xsl:call-template name="BREAK_COLUMN">
				<xsl:with-param name="value">
					<xsl:value-of select="$REVENUE" />
				</xsl:with-param>
			</xsl:call-template>
		</input>
		<input type="radio" name="c_revenue_ci">
			<xsl:attribute name="value">1</xsl:attribute>
			<xsl:if test="$c_revenue_ci='1'">
				<xsl:attribute name="checked">checked</xsl:attribute>
			</xsl:if>
			<!-- "CI" -->
			<xsl:call-template name="BREAK_COLUMN">
				<xsl:with-param name="value">
					<xsl:value-of select="$CI" />
				</xsl:with-param>
			</xsl:call-template>
		</input>

	</xsl:template>

	<!-- Forecast履歴テンプレート -->
	<xsl:template match="FORECAST_HISTORYSET/FORECAST_HISTORY">
		<option>
			<xsl:attribute name="value">
			<xsl:value-of select="HISTORY_ID" />
		</xsl:attribute>
			<xsl:if test="HISTORY_ID[.=$c_history_id]">
				<xsl:attribute name="selected">selected</xsl:attribute>
			</xsl:if>

			<xsl:value-of select="HISTORY_DATE" />
		</option>
	</xsl:template>

	<!--FYテンプレート -->
	<xsl:template name="SHOW_YEAR">
		<xsl:param name="year" select="2000" />
		<xsl:param name="c_fy" />

		<!-- マネープランで参照可能なデータは2008年移行という運用にした（2014/2月) -->
		<xsl:choose>
			<xsl:when test="$year &lt; 2008">
				<optgroup>
					<xsl:attribute name="label"><xsl:value-of
						select="$year" /></xsl:attribute>
					<xsl:attribute name="style">color:#c0c0c0</xsl:attribute>
				</optgroup>
			</xsl:when>
			<xsl:otherwise>
				<option>
					<xsl:attribute name="value"><xsl:value-of
						select="$year" /></xsl:attribute>
					<xsl:if test="$year = $c_fy">
						<xsl:attribute name="selected">selected</xsl:attribute>
					</xsl:if>

					<xsl:value-of select="$year" />
				</option>

			</xsl:otherwise>
		</xsl:choose>

		<xsl:if test="$year &lt; $present_year + 2">
			<xsl:call-template name="SHOW_YEAR">
				<xsl:with-param name="year">
					<xsl:value-of select="$year + 1" />
				</xsl:with-param>
				<xsl:with-param name="c_fy">
					<xsl:value-of select="$c_fy" />
				</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<!--表示開始月テンプレート -->
	<xsl:template name="SHOW_MONTH">
		<xsl:param name="month" select="6" />
		<xsl:param name="c_start_month" />

		<option>
			<xsl:choose>
				<xsl:when test="$month &gt; 12">
					<xsl:attribute name="value">
					<xsl:value-of select="$month - 12" />
				</xsl:attribute>
					<xsl:if test="$month - 12 = $c_start_month">
						<xsl:attribute name="selected">selected</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="$month - 12" />
					月
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="value">
					<xsl:value-of select="$month" />
				</xsl:attribute>
					<xsl:if test="$month=$c_start_month">
						<xsl:attribute name="selected">selected</xsl:attribute>
					</xsl:if>
					<xsl:value-of select="$month" />
					月
				</xsl:otherwise>
			</xsl:choose>
		</option>

		<xsl:if test="$month &lt; (12+6-1)">
			<xsl:call-template name="SHOW_MONTH">
				<xsl:with-param name="month">
					<xsl:value-of select="$month + 1" />
				</xsl:with-param>
				<xsl:with-param name="c_start_month">
					<xsl:value-of select="$c_start_month" />
				</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<!--表示期間テンプレート -->
	<xsl:template name="SHOW_LENGTH">
		<xsl:param name="length" select="1" />

		<option>
			<xsl:attribute name="value">
			<xsl:value-of select="$length" />
		</xsl:attribute>
			<xsl:if test="$length = $c_show_length">
				<xsl:attribute name="selected">selected</xsl:attribute>
			</xsl:if>
			<xsl:value-of select="$length" />
			ヶ月
		</option>

		<xsl:if test="number($length) &lt; 18">
			<xsl:call-template name="SHOW_LENGTH">
				<xsl:with-param name="length">
					<xsl:value-of select="$length + 1" />
				</xsl:with-param>
			</xsl:call-template>
		</xsl:if>
	</xsl:template>

	<!-- 組織テンプレート -->
	<xsl:template match="ORGSET/ORG">
		<option>
			<xsl:attribute name="value"><xsl:value-of select="ORG_ID" /></xsl:attribute>

			<xsl:if test="ORG_ID[.=$c_org_id]">
				<xsl:attribute name="selected">selected</xsl:attribute>
			</xsl:if>

			<xsl:if test="DEL_FLG[.='1']">
				<xsl:attribute name="id">retired</xsl:attribute>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='1']">
				<xsl:attribute name="class">level1</xsl:attribute>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='2']">
				<xsl:attribute name="class">level2</xsl:attribute>
				<xsl:text>　</xsl:text>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='3']">
				<xsl:attribute name="class"></xsl:attribute>
				<xsl:text>　　</xsl:text>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='4']">
				<xsl:attribute name="class"></xsl:attribute>
				<xsl:text>　　　</xsl:text>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='5']">
				<xsl:attribute name="class"></xsl:attribute>
				<xsl:text>　　　　</xsl:text>
			</xsl:if>
			<xsl:choose>
				<xsl:when test="substring(ORG_ID,'0','7') = 'CC0000'">
					<xsl:value-of select="substring(ORG_ID,'7')" />
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="substring(ORG_ID,'5')" />
				</xsl:otherwise>
			</xsl:choose>
			<xsl:text>_</xsl:text>
			<xsl:value-of select="ORG_NAME" />
		</option>
	</xsl:template>

	<!-- ワークステータステンプレート -->
	<xsl:template match="WORK_STATUSSET/WORK_STATUS">
		<option>
			<xsl:attribute name="value">
			<xsl:value-of select="STATUS_ID" />
		</xsl:attribute>
			<xsl:if test="STATUS_ID[.=$c_work_status]">
				<xsl:attribute name="selected">selected</xsl:attribute>
			</xsl:if>

			<xsl:value-of select="S_CODE" />
			:
			<xsl:value-of select="STATUS_NAME" />
		</option>
	</xsl:template>

	<!-- 担当営業組織テンプレート -->
	<xsl:template match="SALES_ORGSET/SALES_ORG">
		<option>
			<xsl:attribute name="value">
			<xsl:value-of select="ORG_ID" />
		</xsl:attribute>

			<xsl:if test="ORG_ID[.=$c_sales_org_id]">
				<xsl:attribute name="selected">selected</xsl:attribute>
			</xsl:if>

			<xsl:if test="ORG_LEVEL[.='1']">
				<xsl:attribute name="class">level1</xsl:attribute>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='2']">
				<xsl:attribute name="class">level2</xsl:attribute>
				<xsl:text>　</xsl:text>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='3']">
				<xsl:attribute name="class">level3</xsl:attribute>
				<xsl:text>　　</xsl:text>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='4']">
				<xsl:attribute name="class"></xsl:attribute>
				<xsl:text>　　　</xsl:text>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='5']">
				<xsl:attribute name="class"></xsl:attribute>
				<xsl:text>　　　　</xsl:text>
			</xsl:if>
			<xsl:value-of select="ORG_NAME" />
		</option>
	</xsl:template>

	<!-- 契約形態テンプレート -->
	<xsl:template match="CONT_TYPESET/CONT_TYPE">
		<option>
			<xsl:attribute name="value">
			<xsl:value-of select="CONTRACT_ID" />
		</xsl:attribute>
			<xsl:if test="DEL_FLG[.='1']">
				<xsl:attribute name="id">retired</xsl:attribute>
			</xsl:if>
			<xsl:if test="CONTRACT_ID[.=$c_contract_id]">
				<xsl:attribute name="selected">selected</xsl:attribute>
			</xsl:if>

			<xsl:value-of select="CONTRACT_NAME" />
		</option>
	</xsl:template>

	<!-- Classificationテンプレート -->
	<xsl:template match="CLASSIFICATIONSET/CLASSIFICATION">
		<option>
			<xsl:attribute name="value">
			<xsl:value-of select="DESC_ID" />
		</xsl:attribute>
			<xsl:if test="DESC_ID[.=$c_classification]">
				<xsl:attribute name="selected">selected</xsl:attribute>
			</xsl:if>

			<xsl:value-of select="DESCRIPTION" />
		</option>
	</xsl:template>

	<!-- Booking管理組織テンプレート -->
	<xsl:template match="BOOKING_ORGSET/BOOKING_ORG">
		<option>
			<xsl:attribute name="value">
			<xsl:value-of select="BOOKING_ORG" />
		</xsl:attribute>
			<xsl:if test="BOOKING_ORG[.=$c_booking_org]">
				<xsl:attribute name="selected">selected</xsl:attribute>
			</xsl:if>
			<xsl:value-of select="BOOKING_ORG" />
		</option>
	</xsl:template>
	<!-- C00012 2006/02/16 sashimura Add End -->

	<!-- ソートテンプレート -->
	<xsl:template name="SORTSET">
		<xsl:param name="default" />

		<xsl:for-each select="//SORT">
			<option>
				<xsl:if test="position() = $default + 1">
					<xsl:attribute name="selected">selected</xsl:attribute>
				</xsl:if>

				<xsl:attribute name="value">
				<xsl:value-of select="ITEM" />
			</xsl:attribute>

				<xsl:value-of select="ITEM" />
			</option>
		</xsl:for-each>

	</xsl:template>

	<xsl:template match="PRD_ORGSET/PRD_ORG">
		<option>

			<xsl:attribute name="value">
			<xsl:value-of select="ORG_ID" />
		</xsl:attribute>

			<xsl:choose>
				<xsl:when test="$fy12_flg!='indusA' and $c_fy>='2012'">
					<xsl:if test="ORG_ID[.=$c_prj_org]">
						<xsl:attribute name="selected">selected</xsl:attribute>
					</xsl:if>
				</xsl:when>
				<xsl:when test="$c_fy&lt;='2019'">
				</xsl:when>
				<xsl:otherwise>
					<xsl:if test="ORG_ID[.=$c_org_id]">
						<xsl:attribute name="selected">selected</xsl:attribute>
					</xsl:if>
				</xsl:otherwise>
			</xsl:choose>

			<xsl:if test="DEL_FLG[.='1']">
				<xsl:attribute name="id">retired</xsl:attribute>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='1']">
				<xsl:attribute name="class">level1</xsl:attribute>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='2']">
				<xsl:attribute name="class">level2</xsl:attribute>
				<xsl:text>　</xsl:text>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='3']">
				<xsl:attribute name="class"></xsl:attribute>
				<xsl:text>　　</xsl:text>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='4']">
				<xsl:attribute name="class"></xsl:attribute>
				<xsl:text>　　　</xsl:text>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='5']">
				<xsl:attribute name="class"></xsl:attribute>
				<xsl:text>　　　　</xsl:text>
			</xsl:if>
			<xsl:if test="ORG_LEVEL[.='6']">
				<xsl:attribute name="class"></xsl:attribute>
				<xsl:text>　　　　　</xsl:text>
			</xsl:if>
			<xsl:value-of select="ORG_NAME" />


		</option>
	</xsl:template>

	<!-- 分類テンプレート -->
	<xsl:template match="BUNRUISET/BUNRUI">
		<option>
			<xsl:if test="BUNRUI_CODE[.=$c_bunrui_code]">
				<xsl:attribute name="selected">selected</xsl:attribute>
			</xsl:if>
			<xsl:attribute name="value">
			<xsl:value-of select="BUNRUI_CODE" />
		</xsl:attribute>
			<xsl:if test="END_DATE[.!='']">
				<xsl:attribute name="id">retired</xsl:attribute>
			</xsl:if>
			<xsl:value-of select="BUNRUI_NAME" />
		</option>
	</xsl:template>

	<xsl:template match="CONS_CATEGORYSSET/CONS_CATEGORYS">
		<option>
			<xsl:if test="CATEGORY_NAME[.=$c_category_id]">
				<xsl:attribute name="selected">selected</xsl:attribute>
			</xsl:if>
			<xsl:attribute name="value">
				<xsl:value-of select="CATEGORY_NAME" />
			</xsl:attribute>
			<xsl:value-of select="CATEGORY_NAME" />
		</option>
	</xsl:template>


	<!-- 項目名改行 -->
	<xsl:template name="BREAK_COLUMN">
		<xsl:param name="value" />

		<xsl:choose>
			<xsl:when test="contains($value, '&lt;br/&gt;')">
				<xsl:value-of select="substring-before($value, '&lt;br')" />
				<br />
				<xsl:value-of select="substring-after($value, 'br/&gt;')" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$value" />
			</xsl:otherwise>
		</xsl:choose>

	</xsl:template>

</xsl:stylesheet>

