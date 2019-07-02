<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!--for local parse IE5.5 under <xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl" > -->
<xsl:output method="html" version="4.0" encoding="UTF-8" />

<!--
/////////////////////////////////////////////////////
// Forecast−詳細画面
/////////////////////////////////////////////////////
// 更新履歴
//  Ver.     日付          担当者          変更内容
//  1.0      2002/05/20    坪内章太郎      新規作成
//  1.1      2003/01/07    Masayoshi.Takemura ReqNoが同じものをまとめる
//  1.2      2004/03/17    Masayoshi.Takemura iProの検収期間の都合のため業務委託だけは2ヶ月前以上でも入力できるように修正
//           2005/01/13    Atsuko.Sashimura   権限を持たない人が実績値を変更できてしまう障害対応
//           2013/10/07    Mari.Suzuki        実績値の入力ロック対応
/////////////////////////////////////////////////////
-->
<!--変数-->
<xsl:variable name="uri" select="/doc-root/header/uri"/>
<xsl:variable name="css_uri" select="/doc-root/header/css_uri"/>
<xsl:variable name="javascript_uri" select="/doc-root/header/javascript_uri"/>
<xsl:variable name="code_name" select="/doc-root/header/code_name"/>

<xsl:variable name="TARGET_CODE" select="/doc-root/TITLESET/TITLE/TARGET_CODE" />
<xsl:variable name="TARGET_NAME" select="/doc-root/TITLESET/TITLE/TARGET_NAME" />
<xsl:variable name="START_MONTH" select="/doc-root/header/start_month" />
<xsl:variable name="SHOW_LENGTH" select="/doc-root/header/show_length" />
<xsl:variable name="CONS_START" select="/doc-root/TITLESET/TITLE/CONS_START" />
<xsl:variable name="CONS_END" select="/doc-root/TITLESET/TITLE/CONS_END" />
<xsl:variable name="FORECAST_AUTH" select="/doc-root/ACCESS_LEVELSET/ACCESS_LEVEL/FORECAST" />
<xsl:variable name="ACTUAL_AUTH" select="/doc-root/ACCESS_LEVELSET/ACCESS_LEVEL/ACTUAL" />
<xsl:variable name="MONTH_AGO" select="/doc-root/DATESET/DATE/MONTH_AGO" />
<xsl:variable name="REFERENCE_NO" select="/doc-root/FORECAST_DETAILSET/FORECAST_DETAIL/REFERENCE_NO" />
<xsl:variable name="TYPE" select="/doc-root/header/type" />
<xsl:variable name="WHICH" select="/doc-root/TITLESET/TITLE/WHICH" />
<xsl:variable name="INPUT_FORECAST_FLG" select="/doc-root/INPUT_FORECAST_FLGSET/INPUT_FORECAST_FLG/VALUE" />

<xsl:template match="/">
<html>
	<head>
		<title>Forecast更新画面</title>

			<!-- CSS -->
			<link>
				<xsl:attribute name="href"><xsl:value-of select="$css_uri"/></xsl:attribute>
				<xsl:attribute name="rel">stylesheet</xsl:attribute>
				<xsl:attribute name="type">text/css</xsl:attribute>
			</link>

			<!-- JavaScript -->
			<script>
				<xsl:attribute name="src"><xsl:value-of select="$javascript_uri"/></xsl:attribute>
			</script>
			<script src="script/Forecast_Detail.js" />
	</head>

	<body>

		<!-- フォーム -->
		<form onSubmit="return checkForm();">
			<xsl:attribute name="method">post</xsl:attribute>
			<xsl:attribute name="action"><xsl:value-of select="$uri"/></xsl:attribute>

			<!-- コントロールコード -->
			<input>
				<xsl:attribute name="type">hidden</xsl:attribute>
				<xsl:attribute name="name"><xsl:value-of select="$code_name"/></xsl:attribute>
				<xsl:attribute name="value">updateForecast</xsl:attribute>
			</input>
			<input>
				<xsl:attribute name="type">hidden</xsl:attribute>
				<xsl:attribute name="name">INPUT_FORECAST_FLG</xsl:attribute>
				<xsl:attribute name="value"><xsl:value-of select="$INPUT_FORECAST_FLG"/></xsl:attribute>
			</input>

			<input type="hidden" name="WHICH">
				<xsl:attribute name="value"><xsl:value-of select="$WHICH"/></xsl:attribute>
			</input>

			<!-- 表示期間 -->
			<input type="hidden" name="SHOW_LENGTH">
				<xsl:attribute name="value"><xsl:value-of select="$SHOW_LENGTH"/></xsl:attribute>
			</input>

			<!-- タイトル（プロジェクト名／業務委託先名） -->
			<div class="title"><xsl:value-of select="$TARGET_NAME"/></div>
			<table class="forecast_detail">
				<tr>
					<td>
						<table class="detail">
							<!-- 列名 -->
							<tr class="header">

								<th class="header"> </th>
								<!-- 月ヘッダーテンプレート呼び出し -->
								<xsl:apply-templates select="doc-root/SHOW_MONTHSET/SHOW_MONTH/FY_DATE" />
							</tr>
							<!-- Forecastテンプレート呼び出し -->
							<xsl:apply-templates select="doc-root/FORECAST_DETAILSET" />
						</table>
					</td>
				</tr>
				<tr>
					<td class="note">(単位：million yen)</td>
				</tr>
				<tr>
					<td>
						<!-- 更新ボタン -->
						<input type="submit" value="更新" class="button"/>
						<!-- 元に戻すボタン -->
						<input type="reset" value="元に戻す" class="button"/>
						<!-- キャンセルボタン -->
						<input type="button" value="キャンセル" class="button" onClick="self.close()" />
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>
</xsl:template>


<!-- 月ヘッダーテンプレート -->
<xsl:template match="SHOW_MONTHSET/SHOW_MONTH/FY_DATE">
	<th class="month">
		<xsl:attribute name="colspan">
			<xsl:value-of select="count(//FORECAST_DETAIL[TARGET_MONTH=current()/.])"/>
		</xsl:attribute>
		<xsl:value-of select="substring(.,5,2)"/>
	</th>
</xsl:template>

<!-- ForecastDetailテンプレート -->
<xsl:template match="FORECAST_DETAILSET">
	<!-- Forecast -->
	<tr class="value">
		<th class="header">Forecast</th>
		<!-- ROW_VALUEテンプレート呼び出し -->
		<xsl:call-template name="ROW_VALUE">
			<xsl:with-param name="ITEM">FORECAST</xsl:with-param>
		</xsl:call-template>
	</tr>
	<!-- 実績 -->
	<tr class="value">
		<th class="header">実績</th>
		<!-- ROW_VALUEテンプレート呼び出し -->
		<xsl:call-template name="ROW_VALUE">
			<xsl:with-param name="ITEM">ACTUAL</xsl:with-param>
		</xsl:call-template>
	</tr>
	<!-- 更新者 -->
	<tr class="value">
		<th class="header">更新者</th>
		<!-- ROW_VALUEテンプレート呼び出し -->
		<xsl:call-template name="ROW_VALUE">
			<xsl:with-param name="ITEM">UPDATE_PERSON</xsl:with-param>
		</xsl:call-template>
	</tr>
	<!-- 更新日 -->
	<tr class="value">
		<th class="header">更新日</th>
		<!-- ROW_VALUEテンプレート呼び出し -->
		<xsl:call-template name="ROW_VALUE">
			<xsl:with-param name="ITEM">UPDATE_DATE</xsl:with-param>
		</xsl:call-template>
	</tr>
	<!-- MP組織 -->
	<tr class="value">
		<th class="header">MP組織</th>
		<xsl:apply-templates select="//SHOW_ORGSET/SHOW_ORG/CC_ORG_ID" />
	</tr>
	<!-- PRJ管理組織 -->
	<tr class="value">
		<th class="header">PRJ管理組織</th>
		<xsl:apply-templates select="//SHOW_ORGSET/SHOW_ORG/ORG_NAME_SHORT" />
	</tr>
</xsl:template>

<!-- ROW_VALUEテンプレート -->
<xsl:template name="ROW_VALUE">
	<xsl:param name="COUNT" select="1"/>
	<xsl:param name="ITEM"/>
	<xsl:param name="FY_DATE" select="//SHOW_MONTHSET/SHOW_MONTH/FY_DATE[position()=$COUNT]"/>
	<xsl:param name="REAL_DATE" select="//SHOW_MONTHSET/SHOW_MONTH/REAL_DATE[position()=$COUNT]"/>
			<!-- FORECAST -->
			<xsl:if test="$ITEM='FORECAST'">
				<xsl:choose>
					<!-- コンサル期間外の場合は-表示 (契約締結日とコンサル終了日の遅い方+1がCONS_ENDになっている)-->
					<xsl:when test="((substring($CONS_START,1,6) &gt; substring($REAL_DATE,1,6)) or (substring($REAL_DATE,1,6) &gt; substring($CONS_END,1,6)))">
						<xsl:choose>
							<!--期間外のくせに値がある場合は表示-->
							<xsl:when test="count(FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]) &gt; 0">
								<xsl:for-each select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]">
									<td class="month">
										<!-- 実績がある場合は色変更 -->
										<xsl:if test="ACTUAL != ''">
											<xsl:attribute name="id">actual</xsl:attribute>
										</xsl:if>
										<xsl:value-of select="FORECAST"/>
									</td>
								</xsl:for-each>
							</xsl:when>
							<xsl:otherwise>
								<td class="month"><xsl:text>-</xsl:text></td>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<!-- コンサル期間内だけど先月以前は値表示のみ(修正されないように)-->
					<xsl:when test="($WHICH='forecast') and (substring($MONTH_AGO,1,6) &gt; substring($REAL_DATE,1,6))">
						<xsl:choose>
							<xsl:when test="count(FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]) &gt; 0">
								<xsl:for-each select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]">
									<td class="month">
										<!-- 実績がある場合は色変更 -->
										<xsl:if test="ACTUAL != ''">
											<xsl:attribute name="id">actual</xsl:attribute>
										</xsl:if>
										<xsl:value-of select="FORECAST"/>
									</td>
								</xsl:for-each>
							</xsl:when>
							<xsl:otherwise>
								<td class="month"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<!-- コンサル期間内で値がある場合 -->
					<xsl:when test="count(FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]) &gt; 0">
						<xsl:for-each select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]">
							<xsl:call-template name="FORECAST_VALUE">
								<xsl:with-param name="POSITION" select="position()"/>
								<xsl:with-param name="COUNT" select="$COUNT"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:when>
					<!-- コンサル期間内で値がない場合 -->
					<xsl:otherwise>
						<xsl:call-template name="FORECAST_VALUE">
							<xsl:with-param name="COUNT" select="$COUNT"/>
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
			<!-- ACTUAL -->
			<xsl:if test="$ITEM='ACTUAL'">
				<xsl:choose>
					<!-- コンサル期間外の場合は-表示 (契約締結日とコンサル終了日の遅い方+1がCONS_ENDになっている)-->
					<xsl:when test="((substring($CONS_START,1,6) &gt; substring($REAL_DATE,1,6)) or (substring($REAL_DATE,1,6) &gt; substring($CONS_END,1,6)))">
						<xsl:choose>
							<!--期間外のくせに値がある場合は表示-->
							<xsl:when test="count(FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]) &gt; 0">
								<xsl:for-each select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]">
									<td class="month">
										<!-- 実績がある場合は色変更 -->
										<xsl:if test="ACTUAL != ''">
											<xsl:attribute name="id">actual</xsl:attribute>
										</xsl:if>
										<xsl:value-of select="ACTUAL"/>
									</td>
								</xsl:for-each>
							</xsl:when>
							<xsl:otherwise>
								<td class="month"><xsl:text>-</xsl:text></td>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<!-- コンサル期間内だけどINPUT_FORECAST_FLGが0または先々月以前の場合は値表示のみ(修正されないように)-->
					<xsl:when test="($WHICH='forecast') and (($INPUT_FORECAST_FLG='0') or (substring($MONTH_AGO,1,6) &gt; substring($REAL_DATE,1,6)))">
						<xsl:choose>
							<xsl:when test="count(FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]) &gt; 0">
								<xsl:for-each select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]">
									<td class="month">
										<!-- 実績がある場合は色変更 -->
										<xsl:if test="ACTUAL != ''">
											<xsl:attribute name="id">actual</xsl:attribute>
										</xsl:if>
										<xsl:value-of select="ACTUAL"/>
									</td>
								</xsl:for-each>
							</xsl:when>
							<xsl:otherwise>
								<td class="month"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<!-- コンサル期間内で値がある場合 -->
					<xsl:when test="count(FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]) &gt; 0">
						<xsl:for-each select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]">
							<xsl:call-template name="ACTUAL_VALUE">
								<xsl:with-param name="POSITION" select="position()"/>
								<xsl:with-param name="COUNT" select="$COUNT"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:when>
					<!-- コンサル期間内で値がない場合 -->
					<xsl:otherwise>
						<xsl:call-template name="ACTUAL_VALUE">
							<xsl:with-param name="COUNT" select="$COUNT"/>
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
			<!-- REFERENCE_NO -->
			<xsl:if test="$ITEM='REFERENCE_NO'">
				<xsl:choose>
					<!-- コンサル期間外の場合は-表示 (契約締結日とコンサル終了日の遅い方+1がCONS_ENDになっている)-->
					<xsl:when test="((substring($CONS_START,1,6) &gt; substring($REAL_DATE,1,6)) or (substring($REAL_DATE,1,6) &gt; substring($CONS_END,1,6)))">
						<xsl:choose>
							<!--期間外のくせに値がある場合は表示-->
							<xsl:when test="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]/REFERENCE_NO[.!='']">
								<td class="month"><xsl:value-of select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]/REFERENCE_NO"/></td>
							</xsl:when>
							<!--棒線表示-->
							<xsl:otherwise>
								<td class="month"><xsl:text>-</xsl:text></td>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<!-- コンサル期間内で値がある場合 -->
					<xsl:when test="count(FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]) &gt; 0">
						<xsl:for-each select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]">
							<xsl:call-template name="REFERENCE_NO">
								<xsl:with-param name="POSITION" select="position()"/>
								<xsl:with-param name="COUNT" select="$COUNT"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:when>
					<!-- コンサル期間内で値がない場合 -->
					<xsl:otherwise>
						<xsl:call-template name="REFERENCE_NO">
							<xsl:with-param name="COUNT" select="$COUNT"/>
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
			<!-- REQ_LINE_NO -->
			<xsl:if test="$ITEM='REQ_LINE_NO'">
				<xsl:choose>
					<!-- コンサル期間外の場合は-表示 (契約締結日とコンサル終了日の遅い方+1がCONS_ENDになっている)-->
					<xsl:when test="((substring($CONS_START,1,6) &gt; substring($REAL_DATE,1,6)) or (substring($REAL_DATE,1,6) &gt; substring($CONS_END,1,6)))">
						<xsl:choose>
							<!--期間外のくせに値がある場合は表示-->
							<xsl:when test="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]/REQ_LINE_NO[.!='']">
								<td class="month">
									<xsl:choose>
										<xsl:when test="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]/REQ_LINE_NO[.!='0']">
											<xsl:value-of select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]/REQ_LINE_NO"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:text>-</xsl:text>
										</xsl:otherwise>
									</xsl:choose>
								</td>
							</xsl:when>
							<!--棒線表示-->
							<xsl:otherwise>
								<td class="month"><xsl:text>-</xsl:text></td>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<!-- コンサル期間内で値がある場合 -->
					<xsl:when test="count(FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]) &gt; 0">
						<xsl:for-each select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]">
							<xsl:call-template name="REQ_LINE_NO">
								<xsl:with-param name="POSITION" select="position()"/>
								<xsl:with-param name="COUNT" select="$COUNT"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:when>
					<!-- コンサル期間内で値がない場合 -->
					<xsl:otherwise>
						<xsl:call-template name="REQ_LINE_NO">
							<xsl:with-param name="COUNT" select="$COUNT"/>
						</xsl:call-template>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
			<!-- UPDATE_PERSON -->
			<xsl:if test="$ITEM='UPDATE_PERSON'">
				<xsl:choose>
					<!-- コンサル期間外の場合は-表示 (契約締結日とコンサル終了日の遅い方+1がCONS_ENDになっている)-->
					<xsl:when test="((substring($CONS_START,1,6) &gt; substring($REAL_DATE,1,6)) or (substring($REAL_DATE,1,6) &gt; substring($CONS_END,1,6)))">
						<xsl:choose>
							<!--期間外のくせに値がある場合は表示-->
							<xsl:when test="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]/UPDATE_PERSON[.!='']">
								<td class="month"><xsl:value-of select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]/UPDATE_PERSON"/></td>
							</xsl:when>
							<!--棒線表示-->
							<xsl:otherwise>
								<td class="month"><xsl:text>-</xsl:text></td>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<!-- コンサル期間内で値がある場合 -->
					<xsl:when test="count(FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]) &gt; 0">
						<xsl:for-each select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]">
							<xsl:call-template name="UPDATE_PERSON">
								<xsl:with-param name="POSITION" select="position()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:when>
					<!-- コンサル期間内で値がない場合 -->
					<xsl:otherwise>
						<xsl:call-template name="UPDATE_PERSON"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
			<!-- UPDATE_DATE -->
			<xsl:if test="$ITEM='UPDATE_DATE'">
				<xsl:choose>
					<!-- コンサル期間外の場合は-表示 (契約締結日とコンサル終了日の遅い方+1がCONS_ENDになっている)-->
					<xsl:when test="((substring($CONS_START,1,6) &gt; substring($REAL_DATE,1,6)) or (substring($REAL_DATE,1,6) &gt; substring($CONS_END,1,6)))">
						<xsl:choose>
							<!--期間外のくせに値がある場合は表示-->
							<xsl:when test="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]/UPDATE_DATE[.!='']">
								<td class="month"><xsl:value-of select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]/UPDATE_DATE"/></td>
							</xsl:when>
							<!--棒線表示-->
							<xsl:otherwise>
								<td class="month"><xsl:text>-</xsl:text></td>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
					<!-- コンサル期間内で値がある場合 -->
					<xsl:when test="count(FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]) &gt; 0">
						<xsl:for-each select="//FORECAST_DETAIL[TARGET_MONTH=$FY_DATE]">
							<xsl:call-template name="UPDATE_DATE">
								<xsl:with-param name="POSITION" select="position()"/>
							</xsl:call-template>
						</xsl:for-each>
					</xsl:when>
					<!-- コンサル期間内で値がない場合 -->
					<xsl:otherwise>
						<xsl:call-template name="UPDATE_DATE"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
	<!-- 再帰呼び出し -->
	<xsl:if test="number($COUNT) &lt; number($SHOW_LENGTH)">
		<xsl:call-template name="ROW_VALUE">
			<xsl:with-param name="COUNT"><xsl:value-of select="$COUNT + 1"/></xsl:with-param>
			<xsl:with-param name="ITEM"><xsl:value-of select="$ITEM"/></xsl:with-param>
		</xsl:call-template>
	</xsl:if>

</xsl:template>


<!-- FORECAST_VALUEテンプレート -->
<xsl:template name="FORECAST_VALUE">
	<xsl:param name="POSITION"/>
	<xsl:param name="COUNT"/>
	<td class="month">
		<!-- 実績がある場合は色変更 -->
		<xsl:if test="ACTUAL[position()=$POSITION][.!= '']">
			<xsl:attribute name="id">actual</xsl:attribute>
		</xsl:if>

		<xsl:choose>
			<!-- Forecastの更新権限がある場合 -->
			<xsl:when test="$FORECAST_AUTH = '2'">
				<!-- 入力可能 -->
				<input type="text" class="money" maxlength="12" style="ime-mode: disabled;">
					<xsl:attribute name="name">FORECAST<xsl:value-of select="$COUNT"/>
						<xsl:if test="REQ_LINE_NO!=''"><xsl:text>_</xsl:text><xsl:value-of select="REQ_LINE_NO"/></xsl:if>
					</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="FORECAST"/></xsl:attribute>
				</input>
				<br/>
				<!-- 入力可能 -->
				<input type="checkbox">
					<xsl:attribute name="name">COMMIT_FLAG<xsl:value-of select="$COUNT"/>
						<xsl:if test="REQ_LINE_NO!=''"><xsl:text>_</xsl:text><xsl:value-of select="REQ_LINE_NO"/></xsl:if>
					</xsl:attribute>
					<xsl:if test="COMMIT_FLAG='1'">
						<xsl:attribute name="checked"/>
					</xsl:if>commit
				</input>
			</xsl:when>

			<!-- Forecastの更新権限がない場合 -->
			<xsl:otherwise>
				<input type="hidden">
					<xsl:attribute name="name">FORECAST<xsl:value-of select="$COUNT"/><xsl:value-of select="REQ_LINE_NO"/></xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="FORECAST"/></xsl:attribute>
				</input>
				<xsl:value-of select="FORECAST"/>
			</xsl:otherwise>
		</xsl:choose>

		<!--ついでにtarget_codeとtarget_monthを挿入しておく-->
		<input type="hidden">
			<xsl:attribute name="name">TARGET_CODE<xsl:value-of select="$COUNT"/>
				<xsl:if test="REQ_LINE_NO!=''"><xsl:text>_</xsl:text><xsl:value-of select="REQ_LINE_NO"/></xsl:if>
			</xsl:attribute>
			<xsl:attribute name="value">
				<xsl:value-of select="$TARGET_CODE"/>
			</xsl:attribute>
		</input>

		<input type="hidden">
			<xsl:attribute name="name">TARGET_MONTH<xsl:value-of select="$COUNT"/>
				<xsl:if test="REQ_LINE_NO!=''"><xsl:text>_</xsl:text><xsl:value-of select="REQ_LINE_NO"/></xsl:if>
			</xsl:attribute>
			<xsl:attribute name="value"><xsl:value-of select="//FY_DATE[position()=$COUNT]"/></xsl:attribute>
		</input>
	</td>
</xsl:template>


<!-- 実績テンプレート -->
<xsl:template name="ACTUAL_VALUE">
	<xsl:param name="POSITION"/>
	<xsl:param name="COUNT"/>

	<td class="month">

		<!-- 実績がある場合は表示変更 -->
		<xsl:if test="ACTUAL[position()=$POSITION][.!= '']">
			<xsl:attribute name="id">actual</xsl:attribute>
		</xsl:if>


		<xsl:choose>
			<!-- Actualの更新権限がある場合 -->
				<xsl:when test="$ACTUAL_AUTH = '2'">
				<!-- 入力可能 -->
				<input type="text" class="money" maxlength="12" style="ime-mode: disabled;">
					<xsl:attribute name="name">ACTUAL<xsl:value-of select="$COUNT"/>
						<xsl:if test="REQ_LINE_NO!=''"><xsl:text>_</xsl:text><xsl:value-of select="REQ_LINE_NO"/></xsl:if>
					</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="ACTUAL"/></xsl:attribute>
				</input>
				<br/>
			</xsl:when>

			<!-- Actualの更新権限がない場合 -->
			<xsl:otherwise>
				<input type="hidden">
					<xsl:attribute name="name">ACTUAL<xsl:value-of select="$COUNT"/>
						<xsl:if test="REQ_LINE_NO!=''"><xsl:text>_</xsl:text><xsl:value-of select="REQ_LINE_NO"/></xsl:if>
					</xsl:attribute>
					<xsl:attribute name="value"><xsl:value-of select="ACTUAL"/></xsl:attribute>
				</input>
				<xsl:value-of select="ACTUAL"/>
			</xsl:otherwise>
		</xsl:choose>
	</td>
</xsl:template>


<!-- REFERENCE_NOテンプレート -->
<xsl:template name="REFERENCE_NO">
	<xsl:param name="POSITION"/>
	<xsl:param name="COUNT"/>

	<td class="month">
		<input type="hidden">
			<xsl:attribute name="name">REFERENCE_NO<xsl:value-of select="$COUNT"/>
				<xsl:if test="REQ_LINE_NO!=''"><xsl:text>_</xsl:text><xsl:value-of select="REQ_LINE_NO"/></xsl:if>
			</xsl:attribute>
			<xsl:attribute name="value"><xsl:value-of select="REFERENCE_NO"/></xsl:attribute>
		</input>
		<xsl:value-of select="REFERENCE_NO"/>
	</td>
</xsl:template>


<!-- REQ_LINE_NOテンプレート -->
<xsl:template name="REQ_LINE_NO">
	<xsl:param name="POSITION"/>
	<xsl:param name="COUNT"/>

	<td class="month">
		<input type="hidden">
			<xsl:attribute name="name">REQ_LINE_NO<xsl:value-of select="$COUNT"/>
				<xsl:if test="REQ_LINE_NO[.!='']"><xsl:text>_</xsl:text><xsl:value-of select="REQ_LINE_NO"/></xsl:if>
			</xsl:attribute>
			<xsl:attribute name="value"><xsl:value-of select="REQ_LINE_NO"/></xsl:attribute>
		</input>

		<xsl:choose>
			<xsl:when test="REQ_LINE_NO[.!='0']"><xsl:value-of select="REQ_LINE_NO"/></xsl:when>
			<xsl:when test="REQ_LINE_NO[.='0'] and UPDATE_DATE[.!='']"><xsl:text>-</xsl:text></xsl:when>
		</xsl:choose>
	</td>
</xsl:template>


<!-- 更新者テンプレート -->
<xsl:template name="UPDATE_PERSON">
	<xsl:param name="POSITION"/>

	<td class="month">
		<xsl:value-of select="UPDATE_PERSON"/>
	</td>
</xsl:template>


<!-- 更新日テンプレート -->
<xsl:template name="UPDATE_DATE">
	<xsl:param name="POSITION"/>

	<td class="month">
		<xsl:value-of select="UPDATE_DATE"/>
	</td>
</xsl:template>


<!-- 西暦→FYテンプレート -->
<xsl:template name="changeFY">
	<xsl:param name="DATE" />

	<xsl:variable name="YYYY" select="substring($DATE, 1, 4)" />
	<xsl:variable name="MM" select="substring($DATE, 5, 2)" />
	<xsl:variable name="DD" select="substring($DATE, 7, 2)" />

	<!-- 6月以上の場合 -->
	<xsl:if test="$MM &gt;= 6">
		<!-- 年に1を足す -->
		<xsl:value-of select="$YYYY + 1"/>
	</xsl:if>
	<!-- 6月未満の場合 -->
	<xsl:if test="$MM &lt; 6">
		<!-- そのまま -->
		<xsl:value-of select="$YYYY"/>
	</xsl:if>

	<xsl:value-of select="$MM"/>
	<xsl:value-of select="$DD"/>

</xsl:template>


<!-- FY→西暦テンプレート -->
<xsl:template name="changeAD">
	<xsl:param name="DATE" />

	<xsl:variable name="YYYY" select="substring($DATE, 1, 4)" />
	<xsl:variable name="MM" select="substring($DATE, 5, 2)" />
	<xsl:variable name="DD" select="substring($DATE, 7, 2)" />

	<!-- 6月以上の場合 -->
	<xsl:if test="$MM &gt;= 6">
		<!-- 年に1を足す -->
		<xsl:value-of select="$YYYY - 1"/>
	</xsl:if>
	<!-- 6月未満の場合 -->
	<xsl:if test="$MM &lt; 6">
		<!-- そのまま -->
		<xsl:value-of select="$YYYY"/>
	</xsl:if>

	<xsl:value-of select="$MM"/>
	<xsl:value-of select="$DD"/>

</xsl:template>


<!-- 組織表示テンプレート -->
<xsl:template match="SHOW_ORGSET/SHOW_ORG/CC_ORG_ID">
	<td class="month">
		<xsl:value-of select="."/>
	</td>
</xsl:template>
<xsl:template match="SHOW_ORGSET/SHOW_ORG/ORG_NAME_SHORT">
	<td class="month">
		<xsl:value-of select="."/>
	</td>
</xsl:template>

</xsl:stylesheet>
