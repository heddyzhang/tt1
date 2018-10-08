<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-kit.tld" prefix="kit" %>
<kit:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title><bean:message key="com.title.ediSyukkaAnnai"/></title>
<link rel="stylesheet" href="./css/style.css" type="text/css">
<link rel="stylesheet" href="./css/styleKz.css" type="text/css">
<script type="text/javascript" src="./js/pbsControl.js"></script>
<script type="text/javascript" src="./js/comControl.js"></script>
<script type="text/javascript" src="./js/comMaster.js"></script>
<script type="text/javascript">

var othersHeight = 180;

</script>

<script type="text/javascript">

function sendCheck(com){

	// 「OK」時の処理開始 ＋ 確認ダイアログの表示
	if(window.confirm(com+'を開始します。よろしいですか？')){
		return true;
	}
	return false;
 }
</script>

<style type="text/css">
<!--
/** 検索部 */
table.form{	width:100%;}
table.form label.layout{ margin-left:1.5em;}
table.form td{	white-space:nowrap;}


/* 明細部フッタボタン */
#footButtons{
	width: 99%;
	margin: 0;
	padding: 0;
}
#footButtons td.side{
	width: 23%;
}

.botanhaba {
	width:25%;
	padding:10;
}

.fillerhaba {
	width:4%;
}

 -->
</style>
</head>
<body
	<%--onContextmenu="return false"--%>
	onLoad="eventLoad();"
	onbeforeunload="return eventUnloadMain();"
	onhelp="return false;">
<%-- 共通ヘッダー開始 --%>
<%@ include file="/parts/comHeader.jsp" %>
<%-- 共通ヘッダー終了 --%>
<%-- 検索条件入力フォーム開始 --%>
<kit:form action="/ediSyukkaAnnai_Search" styleClass="searchForm">
	<%-- タイトル開始 --%>
	<div class="title">
		<bean:message key="com.title.ediSyukkaAnnai"/>
	</div>
	<%-- タイトル終了 --%>
	<%-- 検索条件 --%>
	<table class="form" border="0">
	<tr>
		<td>
		</td>
	</tr>
	</table>
</kit:form>
<%-- 検索条件入力フォーム終了 --%>
<hr />

<%-- 登録・更新・削除フォーム開始 --%>
<kit:form action="/ediSyukkaAnnai_Edit.do">
<kit:hidden name="ediSyukkaAnnai_Edit" property="editable" />
<kit:hidden name="ediSyukkaAnnai_Edit" property="reqType" />
<kit:hidden name="ediSyukkaAnnai_Edit" property="mode" />
<kit:hidden name="ediSyukkaAnnai_Edit" property="preMode" />
<kit:hidden name="ediSyukkaAnnai_Edit" property="maxSu" />

<%-- リスト開始 --%>
<div class="wrapper">
<div id="list" class="fixedTitleX">
	<%-- 表領域開始 --%>
	<div id="details" class="fixedTitleY">
	<br>
	<br>
	<br>
	<table id="detailsTable" class="fixedTitleTable " style="margin-top:-1pt;width:100%;" border="0">
		<tbody>

			<%-- バッチボタン配置 1行目 --%>
	        <tr>
				<td class="botanhaba"><br></td>
				<td class="botanhaba"><br></td>
				<td class="botanhaba"><br></td>
				<td class="botanhaba"><br></td>
				<td class="botanhaba"><br></td>
			</tr>

			<%-- バッチボタン配置 2行目 --%>
	     	<tr>
				<td align=right class="botanhaba">
					<label for="startNengetuLbl"><bean:message key="com.text.hidukeKikansitei"/></label>
				</td>
				<td align=center class="botanhaba">
					<kit:text styleId="startNengetuLbl"
						name="ediSyukkaAnnai_Edit"
						styleClass="text ime_off date frm_dt right"
						format="yyyy/mm/dd"
						property="fromDt"
						maxlength="8"
						size="14"
						tabindex="1"
						style="width:100px;"
						onkeyup="nextTabMaxLenInput()"
						onfocus= "ctrlInput()"
						onblur= "ctrlInput()"
					/>

					<label for="startNengetuLbl"><bean:message key="com.text.range"/></label>

					<kit:text styleId="endNengetuLbl"
						name="ediSyukkaAnnai_Edit"
						styleClass="text ime_off date frm_dt right"
						format="yyyy/mm/dd"
						property="toDt"
						maxlength="8"
						size="14"
						tabindex="2"
						style="width:100px;"
						onkeyup="nextTabMaxLenInput()"
						onfocus= "ctrlInput()"
						onblur= "ctrlInput();"
					/>
				</td>
			</tr>

			<%-- バッチボタン配置 3行目 --%>
	        <tr>
			<%-- 作成対象 --%>
				<td align=right class="botanhaba">
					<label class="" for="sakuseitaisyouhdLbl"><bean:message key="com.text.sakuseitaisyou"/></label>
				</td>
				<td align=center class="botanhaba">
				<%-- 作成対象 --%>
					<kit:select
						styleId="sakuseitaisyouLbl"
						styleClass="list"
						style="width:230px;"
						property="sakuseitaisyouKbn" tabindex="3" onkeypress="ctrlSelection(this)" >
						<kit:options collection="ediSyukkaAnnai_EDI_SYUKKA_MAKE" optional="true" useDefault="false"/>
					</kit:select>
				</td>
				<td align=left class="botanhaba">
					<kit:button
						styleClass="btn_long"
						style="width:80px;height:30px;"
						property="btnCommit"
						tabindex="4"
						onclick="if(sendCheck('データ作成')){if(eventPreSubmit()){setReqType('SyukkaAnnaiMake');offUnloadConfirm();submit()}}">
						<bean:message key="com.button.sakusei" />
					</kit:button>
				</td>
			</tr>

			<%-- バッチボタン配置 4行目 --%>
	        <tr>
			<%-- 送信対象 --%>
				<td align=right class="botanhaba">
					<label class="" for="sousinsakihdLbl"><bean:message key="com.text.sousinsaki"/></label>
				</td>
				<td align=center class="botanhaba">
				<%-- 送信先区分 --%>
					<kit:select
						styleId="sousinsakiLbl"
						style="width:230px;"
						styleClass="list"
						property="sousinsakiKbn" tabindex="5" onkeypress="ctrlSelection(this)" >
						<kit:options collection="ediSyukkaAnnai_EDI_SYUKKA_PARTNER" optional="true" useDefault="false"/>
					</kit:select>
				</td>
				<td align=left class="botanhaba">
					<kit:button
						styleClass="btn_long"
						style="width:80px;height:30px;color:red;"
						property="btnCommit"
						tabindex="6"
						onclick="if(sendCheck('送信')){if(eventPreSubmit()){setReqType('SyukkaAnnaiEDI');offUnloadConfirm();submit()}}">
						<bean:message key="com.button.sousin" />
					</kit:button>
				</td>
			</tr>

		</tbody>
	</table>
	</div>
</div>
</div>
<%-- リスト終了 --%>
<table id="footButtons">
</table>
</kit:form>
<%-- 照会・追加・更新・削除フォーム終了 --%>
<hr>
<%-- 共通フッター開始 --%>
<%@ include file="/parts/comFooter.jsp" %>
<%-- 共通フッター終了 --%>
</body>
</kit:html>
