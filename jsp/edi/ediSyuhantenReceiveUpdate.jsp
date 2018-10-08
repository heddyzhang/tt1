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
<title><bean:message key="com.title.ediSyuhantenReceiveUpdate"/></title>
<link rel="stylesheet" href="./css/style.css" type="text/css">
<link rel="stylesheet" href="./css/styleKz.css" type="text/css">
<script type="text/javascript" src="./js/pbsControl.js"></script>
<script type="text/javascript" src="./js/comControl.js"></script>
<script type="text/javascript" src="./js/comMaster.js"></script>
<script type="text/javascript">

var othersHeight = 180;

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
	width:40%;
	padding:10;
}

.fillerhaba {
	width:20%;
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
<kit:form action="/ediSyuhantenReceiveUpdate_Search" styleClass="searchForm">
	<%-- タイトル開始 --%>
	<div class="title">
		<bean:message key="com.title.ediSyuhantenReceiveUpdate"/>
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
<kit:form action="/ediSyuhantenReceiveUpdate_Edit.do">
<kit:hidden name="ediSyuhantenReceiveUpdate_Edit" property="editable" />
<kit:hidden name="ediSyuhantenReceiveUpdate_Edit" property="reqType" />
<kit:hidden name="ediSyuhantenReceiveUpdate_Edit" property="mode" />
<kit:hidden name="ediSyuhantenReceiveUpdate_Edit" property="preMode" />
<kit:hidden name="ediSyuhantenReceiveUpdate_Edit" property="maxSu" />

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
		<td><br></td>
		<td class="fillerhaba"><br></td>
		<td class="botanhaba">
			<div style="width:230px;text-align:center;">
				<bean:message key="com.text.kakoSszcc" />
			</div>
		</td>
		</tr>

		<%-- バッチボタン配置 2行目 --%>
        <tr>
		<td><br></td>
		<td><br></td>
		<td class="botanhaba">
		<kit:button
			styleClass="btn_long"
			style="width:230px;height:30px;color:red;"
			property="btnCommit"
			tabindex="1"
			onclick="if(eventPreSubmit()){setReqType('SyuhantenEDI');offUnloadConfirm();submit()}">
			<bean:message key="com.button.SyuhantenEDI" />
		</kit:button>
		</td>
		</tr>

		<%-- バッチボタン配置 3行目 --%>
		<tr>
		<td><br><br></td>
		<td><br><br></td>
		<td><br><br></td>
		</tr>

		<%-- バッチボタン配置 4行目 --%>
		<tr>
		<td align=right class="botanhaba">
			<div style="width:230px;text-align:center;">
				<bean:message key="com.text.kakoIkkatu" />
			</div>
		</td>
		<td><br></td>
		<td class="botanhaba">
			<div style="width:230px;text-align:center;">
				<bean:message key="com.text.kakoKobetu" />
			</div>
		</td>
		</tr>

		<%-- バッチボタン配置 5行目 --%>
		<tr>
		<td align=right class="botanhaba">
		<kit:button
			styleClass="btn_long"
			style="width:230px;height:30px;"
			property="btnCommit"
			tabindex="2"
			onclick="if(eventPreSubmit()){setReqType('Ikkatu');offUnloadConfirm();submit()}">
			<bean:message key="com.button.Ikkatu" />
		</kit:button>
        </td>
        <td><hr></td>
        <td class="botanhaba">
		<kit:button
			styleClass="btn_long"
			style="width:230px;height:30px;"
			property="btnCommit"
			tabindex="3"
			onclick="if(eventPreSubmit()){setReqType('SyuhantenKousin');offUnloadConfirm();submit()}">
			<bean:message key="com.button.SyuhantenKousin" />
		</kit:button>
        </td>
		</tr>

		<%-- バッチボタン配置 6行目 --%>
		<tr>
		<td><br></td>
        <td><hr></td>
        <td class="botanhaba">
		<kit:button
			styleClass="btn_long"
			style="width:230px;height:30px;"
			property="btnCommit"
			tabindex="4"
			onclick="if(eventPreSubmit()){setReqType('EisienFILE');offUnloadConfirm();submit()}">
			<bean:message key="com.button.EisienFILE" />
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

