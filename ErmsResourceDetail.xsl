<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!--for local parse IE5.5 under <xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl" > -->
<xsl:output method="html" version="4.0" encoding="UTF-8" />

<!--
/////////////////////////////////////////////////////
// ERMSリソース詳細
/////////////////////////////////////////////////////
// 更新履歴
//  Ver.     日付          担当者          変更内容
//  1.0      2004/11/18    指村敦子        新規作成
//  C00011   2006/01/10    指村 敦子       表記文字列の変更
//           2015/05/13    鈴木 麻里       項目追加
//           2015/12/02    Minako Uenaka	項目変更
//           2015/03/23    鈴木 麻里       社員番号変更対応
//
/////////////////////////////////////////////////////
-->

<!--変数-->
<xsl:variable name="uri" select="/doc-root/header/uri"/>
<xsl:variable name="css_uri" select="/doc-root/header/css_uri"/>
<xsl:variable name="javascript_uri" select="/doc-root/header/javascript_uri"/>
<xsl:variable name="code_name" select="/doc-root/header/code_name"/>
<xsl:variable name="manage_auth" select="//MANAGE_AUTH"/>
<xsl:variable name="context_path" select="/doc-root/header/context_path"/>

<xsl:template match="/">

<html>
	<head>
		<title>ERMS リソース詳細</title>
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
	</head>

	<div class="erms">
	<body>
	  <form name="form1">
      	<xsl:attribute name="method">post</xsl:attribute>
  	  	<xsl:attribute name="action"><xsl:value-of select="$uri"/></xsl:attribute>

	  	<!-- コントロールコード -->
	  	<input>
	    	<xsl:attribute name="type">hidden</xsl:attribute>
			<xsl:attribute name="name"><xsl:value-of select="$code_name"/></xsl:attribute>
			<xsl:attribute name="value">showErmsResourceUpdate</xsl:attribute>
	  	</input>
	  	<table class="noborder">
	    	<tr>
		  		<td class="title">リソース詳細</td>
			</tr>
	  	</table>
	  	<div align="right">
        	<input type="button" value="閉じる" class="button" onClick="window.close()"/>
      	</div>

		<br/>
		<br/>
		<center>
			<xsl:apply-templates select="doc-root/RESOURCESET"/>

        	<br/>
        	<table width="700" class="noborder">
        		<tr>
        			<td width="33%" class="noborder">　
        			</td>
        			<td width="33%" class="noborder center">
        				<!-- ERMS_MANAGE権限がある人のみ、更新可能 -->
        				<xsl:if test="$manage_auth='true'">
          					<input type="submit" name="update" value="更新" class="button" />
        				</xsl:if>
          				<xsl:text>　</xsl:text>
        			</td>
        			<td width="33%" class="noborder right">
        				<input type="button" name="close" value="閉じる" class="button" onClick="window.close()"/>
        			</td>
        		</tr>
        	</table>
			<br/>

		</center>
	  </form>
	</body>
	</div>
</html>


</xsl:template>

<xsl:template match="RESOURCESET/RESOURCE">
	  <input>
	    <xsl:attribute name="type">hidden</xsl:attribute>
		<xsl:attribute name="name">c_resource_id</xsl:attribute>
		<xsl:attribute name="value"><xsl:value-of select="RESOURCE_ID"/></xsl:attribute>
	  </input>
		<table class="listtab" border="1">
		  <tr class="header"><th colspan="6">リソース情報</th></tr>
		  <tr>
		    <th class="item">姓</th>
		    <td><xsl:value-of select="RESOURCE_NAME_SEI"/></td>
		    <th class="item">名</th>
		    <td><xsl:value-of select="RESOURCE_NAME_MEI"/></td>
		    <th class="item quarter">社員番号</th>
		    <td class="quarter" colspan="3"><xsl:value-of select="PERSON_ID"/> / <xsl:value-of select="EMP_ID"/>
		    <br/><font class="input_rule">(※新/旧社員番号)</font></td>
		  </tr>
		  <tr>
		    <th class="item">姓(カナ)</th>
		    <td><xsl:value-of select="RESOURCE_NAME_SEI_KANA"/></td>
		    <th class="item">名(カナ)</th>
		    <td><xsl:value-of select="RESOURCE_NAME_MEI_KANA"/></td>
		    <th class="item">リソースメモ</th>
		    <td><xsl:value-of select="RESOURCE_MEMO"/></td>
		  </tr>
		  <tr>
		    <th class="item">姓(英語)</th>
		    <td><xsl:value-of select="RESOURCE_NAME_SEI_E"/></td>
		    <th class="item">名(英語)</th>
		    <td><xsl:value-of select="RESOURCE_NAME_MEI_E"/></td>
            <th class="item">JOB NAME</th>
		    <td>
		    	<xsl:if test="JOB_NAME = '8888.Contractor-Billable'">
		    		8888.Contractor-Billable
		    	</xsl:if>
		    	<xsl:if test="JOB_NAME = '9999.Contractor-Nonbillable'">
					9999.Contractor-Nonbillable
				</xsl:if>
			</td>
		  </tr>

		  <tr>
		    <th class="item">契約先会社名</th>
		    <td colspan="3"><xsl:value-of select="COMPANY_NAME"/></td>
            <th class="item">Status</th>
            <td><xsl:value-of select="STATUS"/></td>
		  </tr>
		  <tr>
		    <th class="item">HR開始日</th>
		    <td colspan="3"><xsl:value-of select="HR_START_DATE"/></td>
            <th class="item">HR終了日</th>
            <td><xsl:value-of select="HR_END_DATE"/></td>
		  </tr>
		  <tr>
		    <th class="item">GUID</th>
		    <td colspan="3"><xsl:value-of select="GUID"/></td>
            <th class="item">Short GUID</th>
            <td><xsl:value-of select="SHORT_GUID"/></td>
		  </tr>
		  <tr>
		    <th class="item">NDA入手日</th>
		    <td colspan="3"><xsl:value-of select="NDA_GET_DATE"/></td>
            <th class="item">再委託契約書入手日</th>
            <td><xsl:value-of select="RE_COMMISION_GET_DATE"/></td>
		  </tr>
		  <tr>
		    <th class="item">ビルカード開始日</th>
		    <td colspan="3"><xsl:value-of select="BILL_CARD_START_DATE"/></td>
            <th class="item">ビルカード種類</th>
            <td><xsl:value-of select="BILL_CARD_TYPE"/></td>
		  </tr>
		  <tr>
		    <th class="item">社員バッチ開始日</th>
		    <td colspan="3"><xsl:value-of select="EMP_ID_CARD_START_DATE"/></td>
            <th class="item">社員バッチ有効期限</th>
            <td><xsl:value-of select="EMP_ID_CARD_EXPIRATION_DATE"/></td>
		  </tr>
		  <tr>
		    <th class="item">PC貸与開始日</th>
		    <td colspan="3"><xsl:value-of select="PC_RENT_START_DATE"/></td>
            <th class="item">PC返却予定日</th>
            <td><xsl:value-of select="PC_SCHEDULED_RETURN_DATE"/></td>
		  </tr>
		  <tr>
		    <th class="item">バッチ・ビルカード返却日</th>
		    <td colspan="3"><xsl:value-of select="CARD_RETURN_DATE"/></td>
            <th class="item">PC返却日</th>
            <td><xsl:value-of select="PC_RETURN_DATE"/></td>
		  </tr>
		</table>

		<br/>
		<table class="listtab" border="1">
		  <tr class="header"><th colspan="6">発注単価<font class="header_detail">※最新が表示されています</font></th></tr>
		  <tr>
            <th class="item" width="15%">一括請負<br/>(平均単価)</th>
            <td width="15%">
              <xsl:if test="//LATEST_ASSIGNSET/LATEST_ASSIGN/TOTAL_PRICE != ''">
                <xsl:value-of select="format-number(//LATEST_ASSIGNSET/LATEST_ASSIGN/TOTAL_PRICE, '###,###,###')"/>
		      </xsl:if>
		    </td>
		    <th class="item" width="15%">月額単価</th>
		    <td width="15%">
              <xsl:if test="//LATEST_ASSIGNSET/LATEST_ASSIGN/UNIT_PRICE_M != ''">
                <xsl:value-of select="format-number(//LATEST_ASSIGNSET/LATEST_ASSIGN/UNIT_PRICE_M, '###,###,###')"/>
		      </xsl:if>
		    </td>
		    <th class="item" width="15%">時間単価</th>
		    <td width="15%">
              <xsl:if test="//LATEST_ASSIGNSET/LATEST_ASSIGN/UNIT_PRICE_H != ''">
                <xsl:value-of select="format-number(//LATEST_ASSIGNSET/LATEST_ASSIGN/UNIT_PRICE_H, '###,###,###')"/>
		      </xsl:if>
		    </td>
		  </tr>
		</table>
		<br/>

		<table class="listtab" border="1">
		  <tr class="header"><th colspan="4">アサイン履歴</th></tr>
		  <tr class="header"><th>アサイン時<br/>契約先会社名</th><th>開始日</th><th>終了日</th></tr>
		  <xsl:apply-templates select="//ASSIGN_COMPANYSET"/>
		</table>

		<br/>
		<table class="listtab" border="1">
		  <tr class="header"><th colspan="6">履歴情報</th></tr>
		  <tr>
		    <th class="item" width="17.5%">登録開始日</th><td width="17.5%"><xsl:value-of select="REGISTER_START_DATE"/></td>
		    <th class="item" width="17.5%">更新者</th><td width="17.5%"><xsl:value-of select="UPDATE_PERSON"/></td>
		    <th class="item" width="17.5%">更新日</th><td width="17.5%"><xsl:value-of select="UPDATE_DATE"/></td>
		  </tr>
		</table>
</xsl:template>



<!-- アサイン履歴 -->
<xsl:template match="//ASSIGN_COMPANYSET/ASSIGN_COMPANY">
  <tr>
    <td><xsl:value-of select="COMPANY_NAME"/></td>
    <td><xsl:value-of select="START_DATE"/></td>
    <td><xsl:value-of select="END_DATE"/></td>
  </tr>

</xsl:template>
</xsl:stylesheet>
