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
<title><bean:message key="com.title.dataJuchuDataJusin"/></title>
<link rel="stylesheet" href="./css/style.css" type="text/css">
<link rel="stylesheet" href="./css/styleKz.css" type="text/css">
<script type="text/javascript" src="./js/pbsControl.js"></script>
<script type="text/javascript" src="./js/comControl.js"></script>
<script type="text/javascript" src="./js/comMaster.js"></script>
<script type="text/javascript">

function setHomePosition(){
	// ホームオブジェクトを指定
	HOME_OBJECT = document.ediJuchuDataJusin_Search.jyusinsakiKbn;
}

var othersHeight = 180;

</script>

<script type="text/javascript">

function receiveCheck(com){

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

/* リスト部 */
.hediJyucyupartWidth{width:100px;}       /* ×２で計算 */
.nediJyucyupartWidth{width:100px;}       /* ×２で計算 */


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
	width:15%;
	padding:10;
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
<kit:form action="/ediJuchuDataJusin_Search" styleClass="searchForm">
	<%-- タイトル開始 --%>
	<div class="title">
		<bean:message key="com.title.dataJuchuDataJusin"/>
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
<kit:form action="/ediJuchuDataJusin_Edit.do">
<kit:hidden name="ediJuchuDataJusin_Edit" property="editable" />
<kit:hidden name="ediJuchuDataJusin_Edit" property="reqType" />
<kit:hidden name="ediJuchuDataJusin_Edit" property="mode" />
<kit:hidden name="ediJuchuDataJusin_Edit" property="preMode" />
<kit:hidden name="ediJuchuDataJusin_Edit" property="maxSu" />

<%-- リスト開始 --%>
<div class="wrapper">
<div id="list" class="fixedTitleX">
	<%-- 表領域開始 --%>
	<div id="details" class="fixedTitleY">


	<br>
	<table id="detailsTable" class="fixedTitleTable " style="margin-top:50pt;width:100%;" border="0">
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
			<td><br></td>
		<%-- 受信対象 --%>
			<td align=right class="botanhaba">
				<label class="" for="jyusinsakiFromLbl"><bean:message key="com.text.jyusinsaki"/></label>
			</td>
			<td align=left class="botanhaba">
			<%-- 受信先区分 --%>
				<kit:select
					styleId="jyusinsakiLbl"
					style="width:200px;"
					styleClass="list"
					property="jyusinsakiKbn" tabindex="1" onkeypress="ctrlSelection(this)" >
					<kit:options collection="ediJuchuDataJusin_EDI_JYUSIN_PARTNER" optional="true" useDefault="false"/>
				</kit:select>
			</td>
			<td align=left class="botanhaba">
				<kit:button
					styleClass="btn_long"
					style="width:80px;height:30px;color:red;"
					property="btnCommit"
					tabindex="2"
					onclick="if(receiveCheck('受信')){if(eventPreSubmit()){setReqType('JyucyuEDI');offUnloadConfirm();submit()}}">
					<bean:message key="com.button.jyusin" />
				</kit:button>
			</td>
		</tr>

		<%-- バッチボタン配置 3行目 --%>
        <tr>
		<td><br></td>
		<td><br></td>
		<td><br></td>
		<td><br></td>
		</tr>

		<%-- バッチボタン配置 3.5行目 --%>
        <tr>
		<td colspan=15><br></td>
		</tr>


		<%-- バッチボタン配置 7.5行目 --%>
        <tr>
		<td colspan=15><br></td>
		</tr>

		<%-- バッチボタン配置 8行目 --%>
        <tr>
		<td><br></td>
		<td><br></td>
		<td align=left class="botanhaba">
		<kit:button
			styleClass="btn_long"
			style="width:200px;height:30px;"
			property="btnCommit"
			tabindex="6"
			onclick="if(eventPreSubmit()){setReqType('JyucyuHenkanKosin');offUnloadConfirm();submit()}">
			<bean:message key="com.button.JyucyuKosin" />
		</kit:button>
		</td>
		<td><br></td>
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
