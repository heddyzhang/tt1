<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!--for local parse IE5.5 under <xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl" > -->
<xsl:output method="html" version="4.0" encoding="UTF-8" />

<!--
/////////////////////////////////////////////////////
// プロジェクト管理 Project登録依頼画面
/////////////////////////////////////////////////////
// 更新履歴
//  Ver.     日付          担当者          変更内容
//  1.0      2005/06/29    指村敦子        新規作成
//           2005/12/12    指村敦子        コストセンタをリストからtextboxに変更
//           2011/11/30    大野由梨子      ProjectName(略)に文字数制限30文字を追加
//           2013/04/25    鈴木麻里        FixPriceAutomationの説明文を変更
//           2014/03/14    鈴木麻里        システム概要メニューSaaS追加
//           2016/11/17    鈴木麻里        契約金額を必須項目に変更
//           2017/03/02    鈴木麻里        システム概要メニューCloud/On Premise Deal追加
//           2017/07/11    小池由恵        項目整理・レイアウト修正
//           2017/07/20    小池由恵        項目追加・削除
//           2017/01/12    小池由恵        Fusion移行対応
//
/////////////////////////////////////////////////////
-->

<!--変数-->
<xsl:variable name="uri" select="/doc-root/header/uri"/>
<xsl:variable name="css_uri" select="/doc-root/header/css_uri"/>
<xsl:variable name="javascript_uri" select="/doc-root/header/javascript_uri"/>
<xsl:variable name="code_name" select="/doc-root/header/code_name"/>

<xsl:template match="/">

<html>
	<head>
		<title>プロジェクト管理</title>
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
		<script src="script/project/PjrInput.js?var=20170303"/>
		<script src="script/tems/jkl-calendar.js" charset="UTF-8"/>
		<script type="text/javascript">
		    var calContractDate = new JKL.Calendar("divcontractdate","formId","c_contract_date");
		    calContractDate.setStyle( "frame_color", "#008080" );
		    var calProjStart    = new JKL.Calendar("divprojstart","formId","c_proj_start_date");
		    calProjStart.setStyle( "frame_color", "#008080" );
		    var calProjEnd      = new JKL.Calendar("divprojend","formId","c_proj_end_date");
		    calProjEnd.setStyle( "frame_color", "#008080" );
		</script>
	</head>

	<div class="project">
	<body>
	  <form name="form1" id="formId">
      <xsl:attribute name="method">post</xsl:attribute>
  	  <xsl:attribute name="action"><xsl:value-of select="$uri"/></xsl:attribute>

	  <!-- コントロールコード -->
	  <input>
	    <xsl:attribute name="type">hidden</xsl:attribute>
		<xsl:attribute name="name"><xsl:value-of select="$code_name"/></xsl:attribute>
		<xsl:attribute name="value">showPrjPjrMailSend</xsl:attribute>
	  </input>

		<table class="noborder">
			<tr>
				<td class="title">Project登録依頼 (Billable)</td>
			</tr>
		</table>
		<br/>

		<br/>
		<center>
		<table class="page_explain">
        <tr><th class="page_explain">Projectのデータ登録依頼を、ビジネスオペレーション部にメールで送信する画面です。<br/>
              その他の送信先は、PM、Primary Sales、送信者、システム管理者です。</th></tr>
        </table>
        <br/>

		<table>
		  <tr class="header"><th colspan="4">Project情報<br/>
            <font class="required">*</font><font class="detail"> は必須項目です</font></th></tr>
		  <tr>
		    <td>
		      <table>
		        <tr><th class="item"><font class="required">*</font> Opportunity No<br/></th><td><input type="text" style="ime-mode:disabled;" name="c_opo_no" size="30"/></td></tr>
		        <tr><th class="item"> Deal ID<br/></th><td><input type="text" style="ime-mode:disabled;" name="c_deal_id" size="30"/></td></tr>
		        <tr><th class="item"><font class="required">*</font> ProjectName(正式)</th><td><input type="text" name="c_proj_name_seishiki" size="50"/></td></tr>
		        <tr><th class="item"><font class="required">*</font> ProjectName(略)</th><td><input type="text" style="ime-mode:disabled;" name="c_proj_name_ryaku" size="50" maxlength="30"/><br/><font class="input_rule">※半角英数のみ30文字以内<br/>※形式：(例) JP/契約形態(FP or TM)/契約先/End User/PJ名称<br/>※Cloud案件の場合、契約形態は「FPS or TMS」として下さい</font></td></tr>
		        <tr><th class="item"><font class="required">*</font> 新規/継続</th>
		          <td><select size="1" name="c_continue_type">
		              <option value=""></option>
		              <option value="新規">新規</option>
		              <option value="継続">継続</option>
		            </select>
		          </td>
		        </tr>
		        <tr><th class="item">プロジェクト番号<br/>(※継続の場合必須)</th><td><input type="text" name="c_project_number" size="50"/></td></tr>
				<tr><th class="item">エンドユーザ名(正式社名)</th><td><input type="text" name="c_end_user_name" size="50"/></td></tr>
		        <tr><th class="item"><font class="required">*</font> 契約先社名(正式社名)</th><td><input type="text" name="c_client_name" size="50"/></td></tr>
		        <tr><th class="item">請求書送付先郵便番号</th><td><input type="text" name="c_billing_zip_code1" style="ime-mode:disabled;" size="3" maxlength="3"/> - <input type="text" name="c_billing_zip_code2" style="ime-mode:disabled;" size="4" maxlength="4"/></td></tr>
		        <tr><th class="item">請求書送付先住所</th><td><input type="text" name="c_billing_address" size="50"/></td></tr>
		        <tr><th class="item">請求書送付先担当者<br/>(部署・肩書き・氏名)</th><td><input type="text" name="c_billing_person" size="50"/></td></tr>
		      </table>
		    </td>
		    <td>
		      <table>
		      	<tr><th class="item">Bidマネージャ
		          <span class="link_left">
		            <xsl:attribute name="onClick">
				      <xsl:text>JavaScript:</xsl:text>
				      <xsl:text>setObjId(document.form1.c_bid_mgr_id);setObjName(document.form1.c_bid_mgr_name);</xsl:text>
				      <xsl:text>WinOpen('</xsl:text>
				      <xsl:value-of select="$uri"/>
				      <xsl:text>?</xsl:text>
				      <xsl:value-of select="$code_name"/>
				      <xsl:text>=showPresentEmpSelectFrame</xsl:text>
				      <xsl:text>','ErmsSelectWin',600,450);</xsl:text>
			        </xsl:attribute>
		            <img src="explain/search.gif" border="0" />
		          </span>
		        </th><td><input type="text" name="c_bid_mgr_name" size="30" readOnly="true"/></td>
		        <input type="hidden" name="c_bid_mgr_id"/></tr>
		        <tr><th class="item"><font class="required">*</font>Primary Sales/Credit receiver
		          <span class="link_left">
		            <xsl:attribute name="onClick">
				      <xsl:text>JavaScript:</xsl:text>
				      <xsl:text>setObjId(document.form1.c_primary_sales_id);setObjName(document.form1.c_primary_sales_name);</xsl:text>
				      <xsl:text>WinOpen('</xsl:text>
				      <xsl:value-of select="$uri"/>
				      <xsl:text>?</xsl:text>
				      <xsl:value-of select="$code_name"/>
				      <xsl:text>=showPresentEmpSelectFrame</xsl:text>
				      <xsl:text>','ErmsSelectWin',600,450);</xsl:text>
			        </xsl:attribute>
		            <img src="explain/search.gif" border="0" />
		          </span>
		        </th><td><input type="text" name="c_primary_sales_name" size="30" readOnly="true"/></td>
		        <input type="hidden" name="c_primary_sales_id"/></tr>
		        <tr><th class="item"><font class="required">*</font>プロジェクトマネージャ
		          <span class="link_left">
		            <xsl:attribute name="onClick">
				      <xsl:text>JavaScript:</xsl:text>
				      <xsl:text>setObjId(document.form1.c_pm_id);setObjName(document.form1.c_pm_name);</xsl:text>
				      <xsl:text>WinOpen('</xsl:text>
				      <xsl:value-of select="$uri"/>
				      <xsl:text>?</xsl:text>
				      <xsl:value-of select="$code_name"/>
				      <xsl:text>=showPresentEmpSelectFrame</xsl:text>
				      <xsl:text>','ErmsSelectWin',600,450);</xsl:text>
			        </xsl:attribute>
		            <img src="explain/search.gif" border="0" />
		          </span>
		        </th><td><input type="text" name="c_pm_name" size="30" readOnly="true"/></td>
		        <input type="hidden" name="c_pm_id"/></tr>
		        <tr><th class="item"><font class="required">*</font> PAコストセンタ</th>
				  <td><input type="text" name="c_cost_center" size="7" style="ime-mode:disabled;" maxlength="6"/><br/><font class="input_rule">(6桁のコストセンターコード)<br/>
				  以下の通り、案件のCons Categoryに合ったコストセンタを入力してください<br />
				  OnP Apps: 506205 / OnP Tech: 506325 / IaaS: 501636 / PaaS: 508835 / SaaS HCM: 506215 / SaaS ERP: 506225 / SaaS SCM: 506175 / SaaS CX: 506355 </font>
		          </td></tr>
		        <tr><th class="item"><font class="required">*</font> 契約金額</th><td><input class="number" type="text" name="c_contract_amount" style="ime-mode:disabled;" size="10"/>(百万)</td></tr>
		        <tr><th class="item"><font class="required">*</font> 契約形態</th>
		          <td><select size="1" name="c_contract_type">
		            <xsl:apply-templates select="doc-root/CONTRACT_TYPESET"/>
		            </select>
		          </td>
		        </tr>
		        <tr><th class="item">Resellable assets</th>
		          <td><select size="1" name="c_fpauto">
		                <option value="No">No</option>
		                <option value="Yes">Yes</option>
		            </select><br/><font class="input_rule">Resellable Assets契約の場合 > YESに変更してください。</font>
		          </td>
		        </tr>
			    <tr><th class="item"><font class="required">*</font> Probability(%)</th>
			    <td><select size="1" name="c_probability">
			      <xsl:apply-templates select="doc-root/PROBABILITYSET"/>
			      </select>
			    </td></tr>
		        <tr><th class="item"><font class="required">*</font>ワークステータス</th>
		          <td><select size="1" name="c_work_status">
		            <xsl:apply-templates select="doc-root/WORK_STATUSSET"/>
		            </select>
		          </td></tr>
		        <tr><th class="item">契約締結予定日</th><td><input type="text" name="c_contract_date" size="12" maxlength="10" readonly="readonly" onClick="calProjStart.hide(); calProjEnd.hide();calContractDate.write();" onChange="calContractDate.getFormValue(); calContractDate.hide();"/><br/><div id="divcontractdate"></div><font class="input_rule">(フィールドをクリックして選択)</font></td></tr>
		        <tr><th class="item"><font class="required">*</font> プロジェクト期間</th><td><input type="text" name="c_proj_start_date" size="12" maxlength="10" readonly="readonly" onClick="calContractDate.hide(); calProjEnd.hide(); calProjStart.write();" onChange="calProjStart.getFormValue(); calProjStart.hide();"/> - <input type="text" name="c_proj_end_date" size="12" maxlength="10" readonly="readonly" onClick="calContractDate.hide(); calProjStart.hide(); calProjEnd.write();" onChange="calProjEnd.getFormValue(); calProjEnd.hide();"/><br/><div id="divprojstart"></div><font class="input_rule">(フィールドをクリックして選択)</font><div id="divprojend"></div></td></tr>

		      </table>
		    </td>
		  </tr>
		</table>
		<br/>
        <table>
          <tr class="header"><th>その他特記事項</th></tr>
          <tr><td><textarea name="c_comment" rows="4" cols="80"></textarea></td></tr>
        </table>
        <br/>
        <table width="550">
          <tr class="header"><th colspan="2">システム概要メニュー</th></tr>
		  <tr><th class="item"><font class="required">*</font> Project Classification Level</th>
            <td><select size="1" name="c_proj_class_lev">
		      <xsl:apply-templates select="doc-root/PROJ_CLASS_LEVSET"/>
		      </select>
		    </td></tr>
		  <tr><th class="item"><font class="required">*</font> Deployment Model </th>
            <td><select size="1" name="c_deploy_model">
		      <xsl:apply-templates select="doc-root/DEPLOY_MODELSET"/>
		      </select>
		    </td></tr>
		  <tr><th class="item"><font class="required">*</font> Implementation Method </th>
            <td><select size="1" name="c_implement_method">
		      <xsl:apply-templates select="doc-root/IMPLEMENT_METHODSET"/>
		      </select>
		    </td></tr>
		  <tr><th class="item"><font class="required">*</font>  Cloud/On Premise Deal<br/></th>
            <td><select size="1" name="c_cloud_onpremise_deal">
		      <xsl:apply-templates select="doc-root/CLOUD_ONPREMISE_DEALSET"/>
		      </select><br/><font class="input_rule"><xsl:value-of select="doc-root/CLOUD_ONPREMISE_DESCRIPTIONSET/CLOUD_ONPREMISE_DESCRIPTION/VALUE" disable-output-escaping="yes"/></font>
		    </td></tr>
        </table>
		<br/>
        <table>
          <tr class="header"><th>フリーメッセージ</th></tr>
          <tr><td><textarea name="c_free_message" rows="4" cols="80"></textarea></td></tr>
        </table>

        <br/>
        <br/>
        <input type="button" name="send" value="送信" class="button" onClick="checkForm();"/>
        <xsl:text>　</xsl:text>
        <input type="reset" name="reset" value="リセット" class="button" />
		<br/>
	</center>
	</form>
	</body>
	</div>
</html>

</xsl:template>

<xsl:template match="CONTRACT_TYPESET">
    <option>
      <xsl:attribute name="value"></xsl:attribute>
    </option>
  <xsl:for-each select="CONTRACT_TYPE">
    <option>
      <xsl:attribute name="value"><xsl:value-of select="NAME"/></xsl:attribute>
      <xsl:value-of select="NAME"/>
    </option>
  </xsl:for-each>
</xsl:template>

<xsl:template match="FORCAST_STATUSSET">
  <xsl:for-each select="FORCAST_STATUS">
    <option>
      <xsl:attribute name="value"><xsl:value-of select="NAME"/></xsl:attribute>
      <xsl:value-of select="NAME"/>
    </option>
  </xsl:for-each>
</xsl:template>

<xsl:template match="PROBABILITYSET">
  <xsl:for-each select="PROBABILITY">
    <option>
      <xsl:attribute name="value"><xsl:value-of select="NAME"/></xsl:attribute>
      <xsl:value-of select="NAME"/>
    </option>
  </xsl:for-each>
</xsl:template>

<xsl:template match="WORK_STATUSSET">
  <xsl:for-each select="WORK_STATUS">
    <option>
      <xsl:attribute name="value"><xsl:value-of select="NAME"/></xsl:attribute>
      <xsl:value-of select="NAME"/>
    </option>
  </xsl:for-each>
</xsl:template>

<xsl:template match="PROJ_CLASS_LEVSET">
  <option value=""></option>
  <xsl:for-each select="PROJ_CLASS_LEV">
    <option>
      <xsl:attribute name="value"><xsl:value-of select="NAME"/></xsl:attribute>
      <xsl:value-of select="NAME"/>
    </option>
  </xsl:for-each>
</xsl:template>

<xsl:template match="DEPLOY_MODELSET">
  <option value=""></option>
  <xsl:for-each select="DEPLOY_MODEL">
    <option>
      <xsl:attribute name="value"><xsl:value-of select="NAME"/></xsl:attribute>
      <xsl:value-of select="NAME"/>
    </option>
  </xsl:for-each>
</xsl:template>

<xsl:template match="IMPLEMENT_METHODSET">
  <option value=""></option>
  <xsl:for-each select="IMPLEMENT_METHOD">
    <option>
      <xsl:attribute name="value"><xsl:value-of select="NAME"/></xsl:attribute>
      <xsl:value-of select="NAME"/>
    </option>
  </xsl:for-each>
</xsl:template>

<xsl:template match="CLOUD_ONPREMISE_DEALSET">
  <option value=""></option>
  <xsl:for-each select="CLOUD_ONPREMISE_DEAL">
    <option>
      <xsl:attribute name="value"><xsl:value-of select="NAME"/></xsl:attribute>
      <xsl:value-of select="NAME"/>
    </option>
  </xsl:for-each>
</xsl:template>

</xsl:stylesheet>
