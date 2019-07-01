<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!--for local parse IE5.5 under <xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl"
		> -->
	<xsl:output method="html" version="4.0" encoding="UTF-8" />

	<!-- ///////////////////////////////////////////////////// // プロジェクト情報更新画面
		///////////////////////////////////////////////////// // 更新履歴 // Ver. 日付
		担当者 変更内容
		// 1.0 2008/08/04 指村敦子 新規作成
		// 2013/06/04 鈴木麻里 OPPO_TYPEカラム分割対応
		// 2013/07/10 鈴木麻里 システム概要メニュー項目追加
		// 2013/11/15 鈴木麻里 サービス分類（Tech）のプルダウン追加
		// 2014/03/18 鈴木麻里 CLASSIFICATION_4の文言変更
		// 2014/03/26 鈴木麻里 Booking集計対象外カラム追加
		// 2014/05/20 鈴木麻里 Funding作成日非表示
		// 2014/08/28 上中美奈子 コピー行を作成する場合、更新対象項目へのテキストボックス追加
		// 2015/04/07 矢部祥史 契約金額の浮動小数点対応の為、decimaloperater.jsを追加
		// 2016/07/06 鈴木麻里 Booking管理組織の仕様変更
		// 2018/01/10 小池 由恵 Fusion対応
		///////////////////////////////////////////////////// -->

	<!--変数 -->
	<xsl:variable name="uri" select="/doc-root/header/uri" />
	<xsl:variable name="css_uri" select="/doc-root/header/css_uri" />
	<xsl:variable name="javascript_uri" select="/doc-root/header/javascript_uri" />
	<xsl:variable name="code_name" select="/doc-root/header/code_name" />
	<xsl:variable name="copy_to" select="/doc-root/header/c_copy_to" />
	<xsl:variable name="data_upd_type"
		select="doc-root/PROJECTSET/PROJECT/DATA_UPD_TYPE" />
	<xsl:variable name="oppo_flg" select="doc-root/PROJECTSET/PROJECT/OPPO_FLG" />
	<xsl:variable name="status_upd_unavail" select="//STATUS_UPD_UNAVAIL" />
	<xsl:variable name="super_user" select="//SUPER_USER" />

	<xsl:template match="/">

		<html>
			<head>
				<title>プロジェクト情報更新</title>
				<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
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
				<script src="script/decimaloperater.js"></script>
				<script src="script/project/PrjDetail.js" />
			</head>

			<div class="projdetail">
				<body>
					<form name="form1">
						<xsl:attribute name="method">post</xsl:attribute>
						<xsl:attribute name="action"><xsl:value-of
							select="$uri" /></xsl:attribute>

						<!-- コントロールコード -->
						<input>
							<xsl:attribute name="type">hidden</xsl:attribute>
							<xsl:attribute name="name"><xsl:value-of
								select="$code_name" /></xsl:attribute>
							<xsl:attribute name="value">showProjUpdateCompl</xsl:attribute>
						</input>
						<!-- copy_toかどうか -->
						<input type="hidden" name="c_copy_to">
							<xsl:attribute name="value"><xsl:value-of
								select="$copy_to" /></xsl:attribute>
						</input>
						<!-- super_userかどうか -->
						<input type="hidden" name="c_super_user">
							<xsl:attribute name="value"><xsl:value-of
								select="$super_user" /></xsl:attribute>
						</input>
						<input type="hidden" name="data_upd_type">
							<xsl:attribute name="value"><xsl:value-of
								select="$data_upd_type" /></xsl:attribute>
						</input>
						<input type="hidden" name="oppo_flg">
							<xsl:attribute name="value"><xsl:value-of
								select="$oppo_flg" /></xsl:attribute>
						</input>

						<table class="noborder">
							<tr>
								<td class="title">プロジェクト情報更新</td>
							</tr>
						</table>
						<div align="right">
							<input type="button" name="update" value="　更新　" class="button"
								onClick="checkFormProjUpdate();" />
							<xsl:text>　　　　</xsl:text>
							<input type="button" value="閉じる" class="button" onClick="window.close()" />
						</div>

						<br />
						<br />
						<center>
							<!-- 該当プロジェクトが存在しない場合はエラー -->
							<xsl:choose>
								<xsl:when test="count(//PROJECTSET/PROJECT)=0">
									<h2>エラー 該当プロジェクトが存在しません</h2>
								</xsl:when>
								<xsl:otherwise>
									<xsl:apply-templates select="doc-root/PROJECTSET" />

									<!-- 組織情報 -->
									<table width="600">
										<tr class="header">
											<th colspan="5">組織履歴</th>
										</tr>
										<tr class="header">
											<th>START</th>
											<th>END</th>
											<th colspan="2">MP組織</th>
											<th>PRJ管理組織</th>
										</tr>
										<xsl:for-each select="//PROJ_ORG_LIST">
											<tr>
												<td>
													<xsl:value-of select="START_DATE" />
												</td>
												<td>
													<xsl:value-of select="END_DATE" />
												</td>
												<td>
													<xsl:value-of select="CC_ORG_ID" />
												</td>
												<td>
													<xsl:value-of select="ORG_NAME" />
												</td>
												<td>
													<xsl:value-of select="ORG_NAME_SHORT" />
												</td>
											</tr>
										</xsl:for-each>
									</table>

									<br />
									<table width="600">
										<tr class="header">
											<th>日付</th>
											<th>記入者</th>
											<th>流れ</th>
											<th>リスク</th>
											<th>アクション</th>
										</tr>
										<xsl:choose>
											<xsl:when test="$copy_to='true'">
												<tr>
													<td class="center"></td>
													<td class="center"></td>
													<td class="center">
														<textarea name="c_comment1" rows="4" cols="30"></textarea>
														<br />
														<font class="input_rule">(500文字)</font>
													</td>
													<td class="center">
														<textarea name="c_comment2" rows="4" cols="30"></textarea>
														<br />
														<font class="input_rule">(500文字)</font>
													</td>
													<td class="center">
														<textarea name="c_comment3" rows="4" cols="30"></textarea>
														<br />
														<font class="input_rule">(500文字)</font>
													</td>
												</tr>
											</xsl:when>
											<xsl:otherwise>
												<tr>
													<td class="center">
														<xsl:value-of
															select="//SYSTEM_OUTLINESET/SYSTEM_OUTLINE/UPDATE_DATE" />
													</td>
													<td class="center">
														<xsl:value-of
															select="//SYSTEM_OUTLINESET/SYSTEM_OUTLINE/EMP_NAME" />
													</td>
													<td class="center">
														<textarea name="c_comment1" rows="4" cols="30">
															<xsl:value-of
																select="//SYSTEM_OUTLINESET/SYSTEM_OUTLINE/COMMENT1" />
														</textarea>
														<br />
														<font class="input_rule">(500文字)</font>
													</td>
													<td class="center">
														<textarea name="c_comment2" rows="4" cols="30">
															<xsl:value-of
																select="//SYSTEM_OUTLINESET/SYSTEM_OUTLINE/COMMENT2" />
														</textarea>
														<br />
														<font class="input_rule">(500文字)</font>
													</td>
													<td class="center">
														<textarea name="c_comment3" rows="4" cols="30">
															<xsl:value-of
																select="//SYSTEM_OUTLINESET/SYSTEM_OUTLINE/COMMENT3" />
														</textarea>
														<br />
														<font class="input_rule">(500文字)</font>
													</td>
												</tr>
											</xsl:otherwise>
										</xsl:choose>
									</table>
									<br />
									<table width="600">
										<tr class="header">
											<th>日付</th>
											<th>記入者</th>
											<th>アクティビティ</th>
										</tr>
										<xsl:choose>
											<xsl:when test="$copy_to='true'">
												<tr>
													<td class="center"></td>
													<td class="center"></td>
													<td class="center">
														<textarea name="c_activity" rows="4" cols="80"></textarea>
														<br />
														<font class="input_rule">(500文字)</font>
													</td>
												</tr>
											</xsl:when>
											<xsl:otherwise>
												<tr>
													<td class="center">
														<xsl:value-of select="//ACTIVITYSET/ACTIVITY/ACTIVITY_DATE" />
													</td>
													<td class="center">
														<xsl:value-of select="//ACTIVITYSET/ACTIVITY/EMP_NAME" />
													</td>
													<td class="center">
														<textarea name="c_activity" rows="4" cols="80">
															<xsl:value-of select="//ACTIVITYSET/ACTIVITY/ACTIVITY_NAME" />
														</textarea>
														<br />
														<font class="input_rule">(500文字)</font>
													</td>
												</tr>
											</xsl:otherwise>
										</xsl:choose>
									</table>
									<br />
									<table width="600">
										<tr class="header">
											<th>その他特記事項</th>
										</tr>
										<xsl:choose>
											<xsl:when test="$copy_to='true'">
												<tr>
													<td class="center">
														<textarea name="c_comments" row="4" cols="80"></textarea>
														<br />
														<font class="input_rule">(500文字)</font>
													</td>
												</tr>
											</xsl:when>
											<xsl:otherwise>
												<tr>
													<td class="center">
														<textarea name="c_comments" row="4" cols="80">
															<xsl:value-of select="//PROJECTSET/PROJECT/COMMENTS" />
														</textarea>
														<br />
														<font class="input_rule">(500文字)</font>
													</td>
												</tr>
											</xsl:otherwise>
										</xsl:choose>
									</table>

									<br />
									<table>
										<tr class="header">
											<th colspan="2">システム概要メニュー</th>
										</tr>
										<tr class="header">
											<th>Category</th>
											<th>CategoryDescription</th>
										</tr>
										<!-- CLASSIFICATION -->

										<tr>
											<th class="item">CLASSIFICATION</th>
											<td>
												<select size="1" name="c_gbl_system_outline_8">
													<xsl:call-template name="GBL_TYPE_SELECT">
														<xsl:with-param name="category_id">
															8
														</xsl:with-param>
														<xsl:with-param name="selected">
															<xsl:value-of
																select="//GBL_SYSTEM_OUTLINESET/GBL_SYSTEM_OUTLINE[CATEGORY_ID='8']/DESC_ID" />
														</xsl:with-param>
														<xsl:with-param name="selected_end_flg">
															<xsl:value-of
																select="//GBL_SYSTEM_OUTLINESET/GBL_SYSTEM_OUTLINE[CATEGORY_ID='8']/END_FLG" />
														</xsl:with-param>
													</xsl:call-template>
												</select>
											</td>
										</tr>
										<!-- Booking管理組織 -->
										<tr>
											<th class="item">Booking管理組織（Appsのみ）</th>
											<td>
												<select size="1" name="c_booking_org">
													<xsl:call-template name="BOOKING_ORG_SELECT">
														<xsl:with-param name="selected">
															<xsl:value-of select="//PROJECTSET/PROJECT/BOOKING_ORG" />
														</xsl:with-param>
													</xsl:call-template>
												</select>
											</td>
										</tr>
										<tr>
											<th class="item">案件総額（Appsのみ）</th>
											<td>
												<input type="text" name="c_project_total" maxlength="12"
													style="ime-mode:disabled;" onblur="checkNumber(this, 12, true);">
													<xsl:attribute name="value"><xsl:value-of
														select="//PROJECTSET/PROJECT/PROJECT_TOTAL" /></xsl:attribute>
												</input>
												M yen
											</td>
										</tr>
										<!-- CLASSIFICATION_4 -->
										<tr>
											<th class="item">本部別管理分類</th>
											<td>
												<input type="text" name="c_classification_4">
													<xsl:attribute name="value"><xsl:value-of
														select="//PROJECTSET/PROJECT/CLASSIFICATION_4" /></xsl:attribute>
												</input>
											</td>
										</tr>
										<!-- INDUSTRY -->
										<tr>
											<th class="item">INDUSTRY</th>
											<td>
												<select size="1" name="c_gbl_system_outline_2">
													<xsl:call-template name="GBL_TYPE_SELECT">
														<xsl:with-param name="category_id">
															2
														</xsl:with-param>
														<xsl:with-param name="selected">
															<xsl:value-of
																select="//GBL_SYSTEM_OUTLINESET/GBL_SYSTEM_OUTLINE[CATEGORY_ID='2']/DESC_ID" />
														</xsl:with-param>
														<xsl:with-param name="selected_end_flg">
															<xsl:value-of
																select="//GBL_SYSTEM_OUTLINESET/GBL_SYSTEM_OUTLINE[CATEGORY_ID='2']/END_FLG" />
														</xsl:with-param>
													</xsl:call-template>
												</select>
											</td>
										</tr>
										<tr>
											<th class="item">BD（Appsのみ）</th>
											<td>
												<input type="text" name="c_sa_info" size="30"
													maxlength="128">
													<xsl:attribute name="value"><xsl:value-of
														select="//PROJECTSET/PROJECT/SA_INFO" /></xsl:attribute>
												</input>
											</td>
										</tr>
										<tr>
											<th class="item">サービス分類（Tech）</th>
											<td>
												<select size="1" name="c_service_classification">
													<xsl:call-template name="SERVICE_CLASSIFICATION_SELECT">
														<xsl:with-param name="selected">
															<xsl:value-of
																select="//PROJECTSET/PROJECT/SERVICE_CLASSIFICATION" />
														</xsl:with-param>
													</xsl:call-template>
												</select>
											</td>
										</tr>
										<tr>
											<th class="item">Booking集計対象外</th>
											<td>
												<select size="1" name="c_not_book_sum">
													<option value=""></option>
													<option>
														<xsl:if test="//PROJECTSET/PROJECT/NOT_BOOK_SUM[.='Y']">
															<xsl:attribute name="selected">selected</xsl:attribute>
														</xsl:if>
														<xsl:attribute name="value">Y</xsl:attribute>
														Y
													</option>
												</select>
											</td>
										</tr>

									</table>

									<br />
									<br />
									<input type="hidden" name="c_btn_value" />
									<table width="700" class="noborder">
										<tr>
											<xsl:if test="$super_user='true'">
												<td width="33%" class="noborder left">
													<input type="button" name="delete" value="　削除　"
														class="button" onClick="checkFormProjDel();" />
												</td>
											</xsl:if>
											<xsl:if test="$super_user!='true'">
												<td width="33%" class="noborder">
												</td>
											</xsl:if>
											<td width="33%" class="noborder center">
												<input type="button" name="update" value="　更新　" class="button"
													onClick="checkFormProjUpdate();" />
											</td>
											<td width="33%" class="noborder right">
												<input type="reset" name="reset" value="リセット" class="button" />
												<xsl:text>　</xsl:text>
												<input type="button" name="close" value="閉じる" class="button"
													onClick="window.close()" />
											</td>
										</tr>
									</table>
									<br />
								</xsl:otherwise>
							</xsl:choose>
						</center>
					</form>
				</body>
			</div>
		</html>


	</xsl:template>

	<xsl:template match="PROJECTSET/PROJECT">

		<!-- CI案件かどうかで処理を分けるので保持。(CIだったらBookStatusは表示せず、データ上はRevStatus=BookStatusとする。) -->
		<xsl:if test="CONTRACT_ID[.='3' or .='4' or .='8']">
			<input type="hidden" name="c_isCi" value="true"></input>
		</xsl:if>

		<input>
			<xsl:attribute name="type">hidden</xsl:attribute>
			<xsl:attribute name="name">c_proj_code</xsl:attribute>
			<xsl:choose>
				<xsl:when test="$copy_to='true'">
					<xsl:attribute name="value"></xsl:attribute>
				</xsl:when>
				<xsl:otherwise>
					<xsl:attribute name="value"><xsl:value-of
						select="PROJ_CODE" /></xsl:attribute>
				</xsl:otherwise>
			</xsl:choose>
		</input>
		<table>
			<tr class="header">
				<th colspan="4">プロジェクト情報</th>
			</tr>
			<br />
			<!-- ■コメント -->
			<tr class="header2">
				<th colspan="4">
					<xsl:choose>
						<xsl:when test="$data_upd_type='3'">
							<font class="detail">※入力不可項目の修正は、consul-system_jp@oracle.comにメールで依頼してください。</font>
						</xsl:when>
						<xsl:otherwise>
							<font class="detail">
								※入力不可項目の修正はSalesCloud,PAから行ってください。
							</font>
						</xsl:otherwise>
					</xsl:choose>
				</th>
			</tr>
			<tr>
				<td>
					<table>
						<!-- ■TEMP,PA#,SBL# -->
						<input type="hidden" name="c_gsi_proj_code_org">
							<xsl:attribute name="value"><xsl:value-of
								select="GSI_PROJ_CODE" /></xsl:attribute>
						</input>
						<!-- copy_toがtrueの場合 -->
						<xsl:choose>
							<xsl:when test="$copy_to='true'">
								<tr>
									<th class="item"></th>
									<th class="item">Temp Number</th>
									<td colspan="3"></td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">Opportunity Number</th>
									<td colspan="3">
										<xsl:value-of select="OPPO_NUMBER" />
									</td>
								</tr>
								<input type="hidden" name="c_oppo_number">
									<xsl:attribute name="value"><xsl:value-of
										select="OPPO_NUMBER" /></xsl:attribute>
								</input>
								<tr>
									<th class="item"></th>
									<th class="item">Project Number</th>
									<td colspan="3">
										<xsl:choose>
											<xsl:when test="GSI_PROJ_CODE=''">
												XXXXXXXXX
											</xsl:when>
											<xsl:otherwise>
												<xsl:value-of select="GSI_PROJ_CODE" />
											</xsl:otherwise>
										</xsl:choose>
										_
										<input type="text" name="c_data_upd_type" size="19"
											maxlength="15" value=""></input>
									</td>
								</tr>
								<input type="hidden" name="c_gsi_proj_code">
									<xsl:attribute name="value"><xsl:choose><xsl:when
										test="GSI_PROJ_CODE=''">XXXXXXXXX</xsl:when><xsl:otherwise><xsl:value-of
										select="GSI_PROJ_CODE" /></xsl:otherwise></xsl:choose></xsl:attribute>
								</input>
							</xsl:when>
							<!-- oppo_flg=true or data_upd_typeが2の場合、プロ番変更可 -->
							<xsl:when
								test="$oppo_flg='true' or $data_upd_type='2' or substring(GSI_PROJ_CODE,1,9)='XXXXXXXXX'">
								<tr>
									<th class="item"></th>
									<th class="item">Temp Number</th>
									<td colspan="3">
										<xsl:value-of select="PROJ_CODE" />
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">Opportunity Number</th>
									<td colspan="3">
										<xsl:value-of select="OPPO_NUMBER" />
									</td>
								</tr>
								<input type="hidden" name="c_oppo_number">
									<xsl:attribute name="value"><xsl:value-of
										select="OPPO_NUMBER" /></xsl:attribute>
								</input>
								<tr>
									<th class="item"></th>
									<th class="item">Project Number</th>
									<td colspan="3">
										<input type="text" name="c_gsi_proj_code" size="15">
											<xsl:attribute name="value"><xsl:value-of
												select="GSI_PROJ_CODE" /></xsl:attribute>
										</input>
									</td>
								</tr>
							</xsl:when>
							<!-- else -->
							<xsl:otherwise>
								<tr>
									<th class="item"></th>
									<th class="item">Temp Number</th>
									<td colspan="3">
										<xsl:value-of select="PROJ_CODE" />
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">Opportunity Number</th>
									<td colspan="3">
										<xsl:value-of select="OPPO_NUMBER" />
									</td>
								</tr>
								<input type="hidden" name="c_oppo_number">
									<xsl:attribute name="value"><xsl:value-of
										select="OPPO_NUMBER" /></xsl:attribute>
								</input>
								<tr>
									<th class="item"></th>
									<th class="item">Project Number</th>
									<td colspan="3">
										<xsl:value-of select="GSI_PROJ_CODE" />
									</td>
								</tr>
								<input type="hidden" name="c_gsi_proj_code">
									<xsl:attribute name="value"><xsl:value-of
										select="GSI_PROJ_CODE" /></xsl:attribute>
								</input>
							</xsl:otherwise>
						</xsl:choose>

						<!-- ■PJ名等 -->
						<!-- data_upd_typeが3のときは更新可 -->
						<xsl:choose>
							<xsl:when test="$data_upd_type='3' and $super_user='true'">
								<tr>
									<th class="item"></th>
									<th class="item">ProjectName</th>
									<td colspan="3">
										<input type="text" name="c_proj_name" size="50">
											<xsl:attribute name="value"><xsl:value-of
												select="PROJ_NAME" /></xsl:attribute>
										</input>
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">エンドユーザ名</th>
									<td colspan="3">
										<input type="text" name="c_end_user_name" size="50">
											<xsl:attribute name="value"><xsl:value-of
												select="END_USER_NAME" /></xsl:attribute>
										</input>
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">KEYアカウント</th>
									<td>
										<select size="1" name="c_key_acnt">
											<option value="0"></option>
											<option>
												<xsl:if test="KEY_ACNT[.='1']">
													<xsl:attribute name="selected">selected</xsl:attribute>
												</xsl:if>
												<xsl:attribute name="value">1</xsl:attribute>
												<xsl:text>Yes</xsl:text>
											</option>
										</select>
									</td>
									<th class="item">TLアカウント</th>
									<td>
										<select size="1" name="c_tl_acnt">
											<option value="0"></option>
											<option>
												<xsl:if test="TL_ACNT[.='1']">
													<xsl:attribute name="selected">selected</xsl:attribute>
												</xsl:if>
												<xsl:attribute name="value">1</xsl:attribute>
												<xsl:text>Yes</xsl:text>
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">契約先会社名</th>
									<td colspan="3">
										<input type="text" name="c_client_name" size="50">
											<xsl:attribute name="value"><xsl:value-of
												select="CLIENT_NAME" /></xsl:attribute>
										</input>
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">契約形態</th>
									<td colspan="3">
										<select size="1" name="c_cont_id">
											<xsl:call-template name="CONTRACT_LIST">
												<xsl:with-param name="selected">
													<xsl:value-of select="CONTRACT_ID" />
												</xsl:with-param>
											</xsl:call-template>
										</select>
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">FPAutoフラグ</th>
									<td colspan="3">
										<xsl:value-of select="FPAUTO" />
									</td>
								</tr>
							</xsl:when>
							<xsl:otherwise>
								<tr>
									<th class="item"></th>
									<th class="item">ProjectName</th>
									<td colspan="3">
										<xsl:value-of select="PROJ_NAME" />
									</td>
								</tr>
								<input type="hidden" name="c_proj_name_org">
									<xsl:attribute name="value"><xsl:value-of
										select="PROJ_NAME" /></xsl:attribute>
								</input>
								<tr>
									<th class="item"></th>
									<th class="item">エンドユーザ名</th>
									<td colspan="3">
										<xsl:value-of select="END_USER_NAME" />
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">KEYアカウント</th>
									<td>
										<select size="1" name="c_key_acnt">
											<option value="0"></option>
											<option>
												<xsl:if test="KEY_ACNT[.='1']">
													<xsl:attribute name="selected">selected</xsl:attribute>
												</xsl:if>
												<xsl:attribute name="value">1</xsl:attribute>
												<xsl:text>Yes</xsl:text>
											</option>
										</select>
									</td>
									<th class="item">TLアカウント</th>
									<td>
										<select size="1" name="c_tl_acnt">
											<option value="0"></option>
											<option>
												<xsl:if test="TL_ACNT[.='1']">
													<xsl:attribute name="selected">selected</xsl:attribute>
												</xsl:if>
												<xsl:attribute name="value">1</xsl:attribute>
												<xsl:text>Yes</xsl:text>
											</option>
										</select>
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">契約先会社名</th>
									<td colspan="3">
										<xsl:value-of select="CLIENT_NAME" />
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">契約形態</th>
									<td colspan="3">
										<xsl:value-of select="CONTRACT_NAME" />
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">FPAutoフラグ</th>
									<td colspan="3">
										<xsl:value-of select="FPAUTO" />
									</td>
								</tr>
								<input type="hidden" name="c_cont_name">
									<xsl:attribute name="value"><xsl:value-of
										select="CONTRACT_NAME" /></xsl:attribute>
								</input>
							</xsl:otherwise>
						</xsl:choose>

						<!-- ■契約金額、契約締結予定日 copy_toは更新可 -->
						<xsl:choose>
							<xsl:when test="$copy_to='true'">
								<tr>
									<th class="item"></th>
									<th class="item">契約金額</th>
									<td colspan="3">
										<input type="text" name="c_cont_amount" size="12"
											maxlength="11" value="0"></input>
										(Million yen)
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">契約締結予定日</th>
									<td colspan="3">
										<input type="text" name="c_contract_date" size="12"
											maxlength="10">
											<xsl:attribute name="value"><xsl:value-of
												select="CONTRACT_DATE" /></xsl:attribute>
										</input>
										<input type="hidden" name="o_contract_date">
											<xsl:attribute name="value"><xsl:value-of
												select="CONTRACT_DATE" /></xsl:attribute>
										</input>
									</td>
								</tr>
							</xsl:when>
							<xsl:otherwise>
								<tr>
									<th class="item">

									</th>
									<th class="item">契約金額</th>
									<td colspan="3">
										<xsl:value-of select="CONT_AMOUNT" />
										( Million yen )
									</td>
								</tr>
								<tr>
									<th class="item">
									</th>
									<th class="item">契約締結予定日</th>
									<td colspan="3">
										<xsl:value-of select="CONTRACT_DATE" />
									</td>
								</tr>
							</xsl:otherwise>
						</xsl:choose>

						<!-- creditReceiverにコンサル営業を入れることになったので取得方法を変更 -->
						<!-- ■担当営業、担当営業部は、常に入力可 -->
						<!-- 担当営業、担当営業部は、常に入力可 -->
						<!-- ■CreditReceiver VP/DM LM PM CADM SALES のみ参照可 -->
						<xsl:if
							test="/doc-root/CREDITRECEIVER_SHOWSET/CREDITRECEIVER_SHOW/CR_SHOW[.='true']">
							<tr>
								<th class="item"></th>
								<th class="item">CreditReceiver(PA)</th>
								<td colspan="3">
									<table width="60%">
										<xsl:for-each select="//CREDITRECEIVER">
											<tr>
												<td class="number">
													<xsl:value-of select="EMP_ID" />
												</td>
												<td class="center">
													<xsl:value-of select="EMP_NAME" />
												</td>
												<td class="number">
													<xsl:value-of select="PERCENT" />
													%
												</td>
											</tr>
										</xsl:for-each>
									</table>
								</td>
							</tr>
						</xsl:if>

						<!-- ■プロジェクト期間はdata_upd_type=3でsuper_userのとき更新可 -->
						<xsl:choose>
							<xsl:when test="($data_upd_type='3' and $super_user='true')">
								<tr>
									<th class="item"></th>
									<th class="item">プロジェクト期間</th>
									<td colspan="3">
										<input type="text" name="c_proj_start" size="12"
											maxlength="10">
											<xsl:attribute name="value"><xsl:value-of
												select="PROJ_START" /></xsl:attribute>
										</input>
										<xsl:text> - </xsl:text>
										<input type="text" name="c_proj_end" size="12"
											maxlength="10">
											<xsl:attribute name="value"><xsl:value-of
												select="PROJ_END" /></xsl:attribute>
										</input>
									</td>
								</tr>
							</xsl:when>
							<xsl:otherwise>
								<tr>
									<th class="item"></th>
									<th class="item">プロジェクト期間</th>
									<td colspan="3">
										<xsl:value-of select="PROJ_START" />
										-
										<xsl:value-of select="PROJ_END" />
									</td>
								</tr>
								<input type="hidden" name="c_proj_start">
									<xsl:attribute name="value"><xsl:value-of
										select="PROJ_START" /></xsl:attribute>
								</input>
								<input type="hidden" name="c_proj_end">
									<xsl:attribute name="value"><xsl:value-of
										select="PROJ_END" /></xsl:attribute>
								</input>
							</xsl:otherwise>
						</xsl:choose>
						<!-- ■コンサルティング期間は全て更新可 -->
						<tr>
							<th class="item"></th>
							<th class="item">コンサルティング期間</th>
							<td colspan="3">
								<input type="text" name="c_cons_start" size="12"
									maxlength="10">
									<xsl:attribute name="value"><xsl:value-of
										select="CONS_START" /></xsl:attribute>
								</input>
								<xsl:text> - </xsl:text>
								<input type="text" name="c_cons_end" size="12" maxlength="10">
									<xsl:attribute name="value"><xsl:value-of
										select="CONS_END" /></xsl:attribute>
								</input>
								<br />
								<font class="input_rule">(YYYY/MM/DD)</font>
							</td>
						</tr>
						<tr>
							<th class="item"></th>
							<th class="item">担当アドミ</th>
							<td colspan="3">
								<input type="text" name="c_admi" size="30" maxlength="100">
									<xsl:attribute name="value"><xsl:value-of
										select="ADMI" /></xsl:attribute>
								</input>
							</td>
						</tr>
						<tr>
							<th class="item"></th>
							<th class="item">SKU型番</th>
							<td colspan="3">
								<input type="text" name="c_sku_no" size="30" maxlength="100">
									<xsl:attribute name="value"><xsl:value-of
										select="SKU_NO" /></xsl:attribute>
								</input>
							</td>
						</tr>
						<tr>
							<th class="item"></th>
							<th class="item">SKUサービス期間</th>
							<td colspan="3">
								<input type="text" name="c_sku_span" size="30" maxlength="100">
									<xsl:attribute name="value"><xsl:value-of
										select="SKU_SPAN" /></xsl:attribute>
								</input>
							</td>
						</tr>
						<tr>
							<th class="item"></th>
							<th class="item">SKU RevRecRule</th>
							<td colspan="3">
								<input type="text" name="c_sku_rev_rec_rule" size="30" maxlength="300">
									<xsl:attribute name="value"><xsl:value-of
										select="SKU_REV_REC_RULE" /></xsl:attribute>
								</input>
							</td>
						</tr>
					</table>
				</td>
				<td>
					<table>
						<!-- ■PO,PM -->
						<!-- data_upd_type=3、oppo_flgのときは入力可 -->
						<tr>
							<th class="item"></th>
							<th class="item">
								プロジェクトオーナー(LM)
								<span class="link_left">
									<xsl:attribute name="onClick">
				    <xsl:text>JavaScript:</xsl:text>
				    <xsl:text>setObjId(document.form1.c_po_id);setObjName(document.form1.c_po_name);</xsl:text>
				    <xsl:text>WinOpen('</xsl:text>
				    <xsl:value-of select="$uri" />
				    <xsl:text>?</xsl:text>
				    <xsl:value-of select="$code_name" />
				    <xsl:text>=showPresentEmpSelectFrame</xsl:text>
				    <xsl:text>','ErmsSelectWin',600,450);</xsl:text>
			      </xsl:attribute>
									<img src="explain/search.gif" border="0" />
								</span>
							</th>
							<td>
								<input type="text" name="c_po_name" size="30" readOnly="true">
									<xsl:attribute name="value"><xsl:value-of
										select="PO" /></xsl:attribute>
								</input>
							</td>
							<input type="hidden" name="c_po_id">
								<xsl:attribute name="value"><xsl:value-of
									select="PO_ID" /></xsl:attribute>
							</input>
						</tr>
						<xsl:choose>
							<xsl:when
								test="($data_upd_type='3' and $super_user='true') or $oppo_flg='true'">
								<tr>
									<th class="item"></th>
									<th class="item">
										プロジェクトマネージャ
										<span class="link_left">
											<xsl:attribute name="onClick">
				    <xsl:text>JavaScript:</xsl:text>
				    <xsl:text>setObjId(document.form1.c_pm_id);setObjName(document.form1.c_pm_name);</xsl:text>
				    <xsl:text>WinOpen('</xsl:text>
				    <xsl:value-of select="$uri" />
				    <xsl:text>?</xsl:text>
				    <xsl:value-of select="$code_name" />
				    <xsl:text>=showPresentEmpSelectFrame</xsl:text>
				    <xsl:text>','ErmsSelectWin',600,450);</xsl:text>
			      </xsl:attribute>
											<img src="explain/search.gif" border="0" />
										</span>
									</th>
									<td>
										<input type="text" name="c_pm_name" size="30" readOnly="true">
											<xsl:attribute name="value"><xsl:value-of
												select="PM" /></xsl:attribute>
										</input>
									</td>
									<input type="hidden" name="c_pm_id">
										<xsl:attribute name="value"><xsl:value-of
											select="PM_ID" /></xsl:attribute>
									</input>
								</tr>
							</xsl:when>
							<xsl:otherwise>
								<tr>
									<th class="item"></th>
									<th class="item">プロジェクトマネージャ</th>
									<td>
										<xsl:value-of select="PM" />
									</td>
								</tr>
								<input type="hidden" name="c_pm_id">
									<xsl:attribute name="value"><xsl:value-of
										select="PM_ID" /></xsl:attribute>
								</input>
							</xsl:otherwise>
						</xsl:choose>

						<!-- ■PRD組織は常に入力可 -->
						<tr>
							<th class="item"></th>
							<th class="item">PRJ管理組織</th>
							<td>
								<select size="1" name="c_prd_org_id" style="WIDTH: 450px">
									<xsl:call-template name="PRD_ORG_SELECT">
										<xsl:with-param name="selected_id">
											<xsl:value-of select="PRD_ORG_ID" />
										</xsl:with-param>
									</xsl:call-template>
								</select>
							</td>
						</tr>

						<!-- ■プロジェクト組織は常に入力可 -->
						<tr>
							<th class="item"></th>
							<th class="item">コストセンター ： MP組織</th>
							<td>
								<xsl:value-of select="PA_CC_ID" />
								：
								<select size="1" name="c_unit_id" style="WIDTH: 450px">
									<xsl:call-template name="COST_CENTER_SELECT">
										<xsl:with-param name="selected_id">
											<xsl:value-of select="UNIT_ID" />
										</xsl:with-param>
										<xsl:with-param name="selected_end_flg">
											<xsl:value-of select="CC_END_FLG" />
										</xsl:with-param>
									</xsl:call-template>
								</select>
								<br />
								<font class="input_rule">※コンサルティング期間に存在する最新の名称を取得しています。</font>
							</td>
						</tr>

						<!-- ■MPコストセンタは常に入力可 -->
						<!-- ■フォーキャストステータスはunit_idがconst_list.name='STATUS_UPDATE_UNAVAILABLE_ORG'の組織(Apps案件)で現在有効ならば参照のみ.super_userは一時的に更新可 -->
						<!-- ■Bookingステータス. CI案件は - を表示する -->
						<input type="hidden" name="c_book_status">
							<xsl:attribute name="value"><xsl:value-of
								select="BOOK_STATUS" /></xsl:attribute>
						</input>
						<tr>
							<th class="item"></th>
							<th class="item">Booking Status</th>
							<td>
								<xsl:choose>
									<xsl:when test="CONTRACT_ID[.='3' or .='4' or .='8']">
										<xsl:text>　-　</xsl:text>
									</xsl:when>
									<xsl:otherwise>
										<xsl:value-of select="BOOK_STATUS_NAME" />
									</xsl:otherwise>
								</xsl:choose>
							</td>
						</tr>
						<!-- ■Revenueステータス. CI案件は「SalesCloudのWinProb%と連動しないチェックボックス」を表示しない -->
						<tr>
							<th class="item"></th>
							<th class="item">Revenue Status</th>
							<td>
								<select size="1" name="c_rev_status">
									<xsl:call-template name="FCST_STATUS_LIST">
										<xsl:with-param name="selected">
											<xsl:value-of select="REV_STATUS" />
										</xsl:with-param>
									</xsl:call-template>
								</select>
								<xsl:if test="CONTRACT_ID[.!='3' and .!='4' and .!='8']">
									<input type="checkbox" name="c_no_upd_rev_status" value="yes">
										<xsl:if test="NO_UPD_REV_STATUS[.='1']">
											<xsl:attribute name="checked">checked</xsl:attribute>
										</xsl:if>
										<xsl:text>Sales CloudのWinProb%と連動しない</xsl:text>
									</input>
								</xsl:if>
							</td>
						</tr>

						<!-- ■ワークステータスは常に入力可 -->
						<tr>
							<th class="item"></th>
							<th class="item">ワークステータス</th>
							<td>
								<select size="1" name="c_work_status">
									<xsl:call-template name="WORK_STATUS_LIST">
										<xsl:with-param name="selected">
											<xsl:value-of select="WORK_STATUS" />
										</xsl:with-param>
									</xsl:call-template>
								</select>
							</td>
						</tr>

						<!-- ■SBL情報 -->
						<xsl:choose>
							<xsl:when test="//OPPO_NUMBER!=''">
								<tr>
									<th class="item"></th>
									<th class="item">Bidマネージャ</th>
									<td>
										<input type="text" name="c_bid" size="12" maxlength="50">
											<xsl:attribute name="value"><xsl:value-of
												select="BID" /></xsl:attribute>
										</input>
									</td>
								</tr>
								<tr>
									<th class="item">
									</th>
									<th class="item">Status</th>
									<td>
										<xsl:value-of select="STATUS" />
									</td>
								</tr>
								<tr>
									<th class="item">
									</th>
									<th class="item">Sales Stage</th>
									<td>
										<xsl:value-of select="PROGRESS" />
									</td>
								</tr>
								<tr>
									<th class="item">
									</th>
									<th class="item">WinProb%</th>
									<td>
										<xsl:value-of select="WIN_PROB" />
									</td>
								</tr>
								<tr>
									<th class="item">
									</th>
									<th class="item">PrimarySales(FusionCRM)</th>
									<td>
										<xsl:value-of select="CONSUL_SALES" />
									</td>
								</tr>
								<tr>
									<th class="item">
									</th>
									<th class="item">SubIndustry</th>
									<td>
										<xsl:value-of select="BUNRUI_NAME" />
										<!-- 分類がエラーコードの場合だけ設定値を表示 -->
										<xsl:if test="BUNRUI_CODE='999'">
											/
											<xsl:value-of select="PROJ_BUNRUI_NAME" />
										</xsl:if>
									</td>
								</tr>
								<tr>
									<th class="item">
									</th>
									<th class="item">Module</th>
									<td style="width:460px">
										<xsl:value-of select="MODULE" />
									</td>
								</tr>
								<tr>
									<th class="item">
									</th>
									<th class="item">Consulting Type</th>
									<td>
										<xsl:value-of select="CONSULTING_TYPE" />
									</td>
								</tr>
								<tr>
									<th class="item">
									</th>
									<th class="item">Revenue Type</th>
									<td>
										<xsl:value-of select="REVENUE_TYPE" />
									</td>
								</tr>
							</xsl:when>
							<xsl:otherwise>
								<tr>
									<th class="item"></th>
									<th class="item">Bidマネージャ</th>
									<td>
										<xsl:value-of select="BID" />
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">Status</th>
									<td>
										<xsl:value-of select="STATUS" />
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">Sales Stage</th>
									<td>
										<xsl:value-of select="PROGRESS" />
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">WinProb%</th>
									<td>
										<xsl:value-of select="WIN_PROB" />
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">PrimarySales(FusionCRM)</th>
									<td>
										<xsl:value-of select="CONSUL_SALES" />
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">SubIndustry</th>
									<td>
										<xsl:value-of select="BUNRUI_NAME" />
										<!-- 分類がエラーコードの場合だけ設定値を表示 -->
										<xsl:if test="BUNRUI_CODE='999'">
											/
											<xsl:value-of select="PROJ_BUNRUI_NAME" />
										</xsl:if>
									</td>
								</tr>
								<tr>
									<th class="item"></th>
									<th class="item">Module</th>
									<td>
										<xsl:value-of select="MODULE" />
									</td>
								</tr>
							</xsl:otherwise>
						</xsl:choose>
					</table>
				</td>
			</tr>
		</table>
		<br />


	</xsl:template>

	<!-- フォーキャストステータスリスト -->
	<xsl:template name="FCST_STATUS_LIST">
		<xsl:param name="selected" />

		<xsl:for-each select="//FCST_STATUS_LIST">
			<option>
				<xsl:if test="STATUS_ID[.=$selected]">
					<xsl:attribute name="selected">selected</xsl:attribute>
				</xsl:if>

				<xsl:attribute name="value">
				<xsl:value-of select="STATUS_ID" />
			</xsl:attribute>

				<xsl:value-of select="STATUS_NAME" />
			</option>
		</xsl:for-each>

	</xsl:template>

	<!-- ワークステータスリスト -->
	<xsl:template name="WORK_STATUS_LIST">
		<xsl:param name="selected" />

		<xsl:for-each select="//WORK_STATUS_LIST">
			<option>
				<xsl:if test="STATUS_ID[.=$selected]">
					<xsl:attribute name="selected">selected</xsl:attribute>
				</xsl:if>

				<xsl:attribute name="value">
				<xsl:value-of select="STATUS_ID" />
			</xsl:attribute>

				<xsl:value-of select="STATUS" />
			</option>
		</xsl:for-each>

	</xsl:template>

	<!-- 契約形態 -->
	<xsl:template name="CONTRACT_LIST">
		<xsl:param name="selected" />

		<xsl:for-each select="//CONTRACT_LIST">
			<option>
				<xsl:if test="CONTRACT_ID[.=$selected]">
					<xsl:attribute name="selected">selected</xsl:attribute>
				</xsl:if>

				<xsl:attribute name="value">
				<xsl:value-of select="CONTRACT_ID" />
			</xsl:attribute>

				<xsl:value-of select="CONTRACT_NAME" />
			</option>
		</xsl:for-each>

	</xsl:template>


	<!-- COST_CENTER_SELECT -->
	<xsl:template name="COST_CENTER_SELECT">
		<xsl:param name="selected_id" />
		<xsl:param name="selected_end_flg" />

		<!-- selected_end_flg=1だったら対象組織のend_dateが設定されているので、 -->
		<!-- 先頭に値を表示させる。 -->
		<option>
			<xsl:attribute name="value">
	      <xsl:value-of select="//PROJECTSET/PROJECT/UNIT_ID" />
	    </xsl:attribute>
			<xsl:attribute name="selected">selected</xsl:attribute>
			<xsl:value-of select="//PROJECTSET/PROJECT/CC_NAME" />
		</option>

		<xsl:for-each select="//COST_CENTER_LIST">
			<option>
				<xsl:attribute name="value">
				<xsl:value-of select="ORG_ID" />
			</xsl:attribute>
				<xsl:if test="ORG_END_DATE[.!='']">
					<xsl:attribute name="id">retired</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="ORG_NAME" />
			</option>
		</xsl:for-each>

	</xsl:template>

	<!-- PRD組織SELECT -->
	<xsl:template name="PRD_ORG_SELECT">

		<option>
			<xsl:attribute name="value">
	      <xsl:value-of select="//PROJECTSET/PROJECT/PRD_ORG_ID" />
	    </xsl:attribute>
			<xsl:attribute name="selected">selected</xsl:attribute>
			<xsl:value-of select="//PROJECTSET/PROJECT/PRD_ORG_NAME" />
		</option>

		<option value=""></option>
		<xsl:for-each select="//PRD_ORG_LIST">
			<option>
				<xsl:attribute name="value">
				<xsl:value-of select="ORG_ID" />
			</xsl:attribute>
				<xsl:if test="ORG_END_DATE[.!='']">
					<xsl:attribute name="id">retired</xsl:attribute>
				</xsl:if>
				<xsl:value-of select="ORG_NAME" />
			</option>
		</xsl:for-each>

	</xsl:template>

	<!-- GBL_TYPE_SELECT -->
	<xsl:template name="GBL_TYPE_SELECT">
		<xsl:param name="category_id" />
		<xsl:param name="selected" />
		<xsl:param name="selected_end_flg" />

		<!-- selected_end_flg=1だったらgbl_class_description.end_dateが設定されている値がgbl_system_outlineに設定されているので、 -->
		<!-- 先頭に値を表示させる。 -->
		<xsl:if test="$selected_end_flg='1'">
			<option>
				<xsl:attribute name="value">
	      <xsl:value-of
					select="//GBL_SYSTEM_OUTLINESET/GBL_SYSTEM_OUTLINE[CATEGORY_ID=$category_id]/DESC_ID" />
	    </xsl:attribute>
				<xsl:attribute name="selected">selected</xsl:attribute>
				<xsl:value-of
					select="//GBL_SYSTEM_OUTLINESET/GBL_SYSTEM_OUTLINE[CATEGORY_ID=$category_id]/DESCRIPTION" />
			</option>
		</xsl:if>

		<option value=""></option>
		<xsl:for-each select="//GBL_SYSTEM_MENU[CATEGORY_ID=$category_id]">
			<option>
				<xsl:if test="DESC_ID[.=$selected]">
					<xsl:attribute name="selected">selected</xsl:attribute>
				</xsl:if>

				<xsl:attribute name="value">
				<xsl:value-of select="DESC_ID" />
			</xsl:attribute>

				<xsl:value-of select="DESCRIPTION" />
			</option>
		</xsl:for-each>

	</xsl:template>

	<xsl:template name="BOOKING_ORG_SELECT">
		<xsl:param name="selected" />
		<option value=""></option>
		<xsl:for-each select="//BOOKING_ORGSET/BOOKING_ORG">
			<option>
				<xsl:if test="BOOKING_ORG[.=$selected]">
					<xsl:attribute name="selected">selected</xsl:attribute>
				</xsl:if>
				<xsl:attribute name="value">
				<xsl:value-of select="BOOKING_ORG" />
			</xsl:attribute>
				<xsl:value-of select="BOOKING_ORG" />
			</option>
		</xsl:for-each>
	</xsl:template>

	<xsl:template name="SERVICE_CLASSIFICATION_SELECT">
		<xsl:param name="selected" />
		<option value=""></option>
		<xsl:for-each select="//SERVICE_CLASSIFICATIONSET/SERVICE_CLASSIFICATION">
			<option>
				<xsl:if test="ID[.=$selected]">
					<xsl:attribute name="selected">selected</xsl:attribute>
				</xsl:if>
				<xsl:attribute name="value">
				<xsl:value-of select="ID" />
			</xsl:attribute>
				<xsl:value-of select="SERVICE_NAME" />
			</option>
		</xsl:for-each>
	</xsl:template>

</xsl:stylesheet>
