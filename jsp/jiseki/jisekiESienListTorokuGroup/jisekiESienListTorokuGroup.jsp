<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-kit.tld" prefix="kit"%>
<kit:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title><kit:write name="jisekiESienListTorokuGroup_Master" property="title" scope="session"/></title>
<link rel="stylesheet" href="./css/style.css" type="text/css">
<link rel="stylesheet" href="./css/styleKz.css" type="text/css">
<link rel="stylesheet" href="./css/print.css" type="text/css" media="print">
<script type="text/javascript" src="./js/pbsControl.js"></script>
<script type="text/javascript" src="./js/comControl.js"></script>
<script type="text/javascript" src="./js/comMaster.js"></script>
<script type="text/javascript" src="./js/kzControl.js"></script>
<script type="text/javascript">
	var othersHeight = 220;
	function setHomePosition() {
		// ホームオブジェクトを指定
		HOME_OBJECT = document.jisekiESienListTorokuGroup_Search.sgyosyaCd;
	}
	function getValueByAjaxForSgyosyaCd(keyObj, targetNms) {
		getValueByAjax("jisekiESienListTorokuGroup_Search", "sgyosyaCd", keyObj, targetNms);
	}
	function getValueByAjaxForTorokuCd(keyObj, targetNms) {
		getValueByAjax("jisekiESienListTorokuGroup_Edit", "torokuCd", keyObj, targetNms);
	}
	function getValueByAjax(formNm, keyNm, keyObj, targetNms) {
		var form = document.forms[formNm];
		var keyValue = form.elements[keyObj.name].value;
		var url = "/kit/jisekiESienListTorokuGroup_Ajax.do?" + keyNm + "=" + keyValue;
		getValueByCodeForAjax(url, formNm, keyObj, targetNms);
	}
	function setCdNmValues() {
		for( var i = 0; i < 20; i++ ) {
			opener.setTorokuCdNm(i,document.editForm.elements['jisekiESienListTorokuGroupRecord[' + i + '].torokuCd'].value,document.editForm.elements['jisekiESienListTorokuGroupRecord[' + i + '].torokuNm'].value);
		}
		window.close();
	}
</script>
<style type="text/css">
<!--
table.form {
	width: 100%;
}
table.form label.layout {
	margin-left: 1.5em;
}
table.form td {
	white-space: nowrap;
}
table.list thead td {
	text-align: center;
}
/* @override */
div.list_top {
	text-align: left;
	padding: 1pt;
	margin: 1pt 0pt 1pt;
	font-size: 11pt;
}
#list{
	float: left;
}
.fixedTitleTable{
	width:470px;
}
.fixedTitleTable3{
	width:810px;
}
.dateHWidth{width:100px;}
.dateWidth{width:200px;}

.noWidth{width:40px;}
.codeCdWidth{width:110px;}
.cdNmWidth{width:320px;}

.youkiNmWidth{width:140px;}
.caseIrisuWidth{width:80px;}
.tnpnVolWidth{width:120px;}
#footButtons{
	width:99%;
	margin: 0;
	padding: 20 0 0 0;
}
#footButtons td.side{
	width:23%;
}
-->
</style>
</head>
<body
	onContextmenu="return false"
	onLoad="eventLoadPopup();setHomePosition();"
	onUnload="eventUnloadPopup()"
	onhelp="return false;">
	<%-- 共通ヘッダー開始（ポップアップ） --%>
	<%@ include file="/parts/comHeader_Pop.jsp"%>
	<%-- 共通ヘッダー終了（ポップアップ） --%>
	<%-- 検索フォーム開始 --%>
	<kit:form action="/jisekiESienListTorokuGroup_Search" styleClass="searchForm">
		<kit:hidden name="jisekiESienListTorokuGroup_Search" property="editable" />
		<kit:hidden name="jisekiESienListTorokuGroup_Search" property="mode"/>
		<%-- タイトル開始 --%>
		<div class="title"><kit:write name="jisekiESienListTorokuGroup_Master" property="title" scope="session" /></div>
		<div class="buttons">
			<table>
				<tr>
					<td>
						<%-- 登録更新 --%>
						<kit:button styleClass="btn_large" name="jisekiESienListTorokuGroup_Search" key="notInit"
							property="btnEdit"
							onclick="if(eventPreSubmit()){offUnloadConfirm();document.editForm.submit()}">
							<bean:message key="com.button.torokuKosin" />
						</kit:button>
						<%-- 呼出 --%>
						<kit:button styleClass="btn_large"
							property="btnSearch" tabindex="5"
							onclick="if(eventPreSubmit()){offUnloadConfirm();this.form.submit()}">
							<bean:message key="com.button.search" />
						</kit:button>
						<%-- 検索 --%>
						<kit:button styleClass="btn_search btn_large"
							property="btnPopupSearch"
							onclick="openModalSearch(this.form.kaisyaCd.value);">
							<bean:message key="com.button.knsk" />
						</kit:button>
					</td>
				</tr>
			</table>
		</div>
		<%-- タイトル終了 --%>

		<%-- 検索条件 --%>
		<table class="form">
			<tr>
				<td>
					<%-- 登録ユーザー --%>
					<label for="sgyosyaCdLbl"><bean:message key="com.text.torokuUser"/></label>
					<kit:text
						name="jisekiESienListTorokuGroup_Search" property="sgyosyaCd" maxlength="9" size="15"
						styleClass="text ime_off alpha_number frm_lpad rigth searchable must"
						styleId="sgyosyaCdLbl" tabindex="1"
						onkeyup="nextTabMaxLenInput()"
						onblur="setRefObjAll('popup','sgyosyaCd',this,'sgyosyaMeiRn','sgyosyaNm');ctrlInput();
							getValueByAjaxForSgyosyaCd(this,'sgyosyaNm');"
						onfocus="ctrlInput();setPreValueForAjax(this);" />
					<kit:text
						name="jisekiESienListTorokuGroup_Search" property="sgyosyaNm" maxlength="42" size="45"
						styleId="sgyosyaNmLbl"
						styleClass="text ime_off left readonly"
						readonly="true" tabindex="false"
						onblur="ctrlInput()" onfocus="ctrlInput()" />
					<%-- 登録パターン --%>
					<kit:radio styleClass="radio" name="jisekiESienListTorokuGroup_Search" property="torokuPtn" tabindex="2" value="A" styleId="torokuPtnA"/>
					<LABEL FOR="torokuPtnA"><bean:message key="com.text.torokuPtnA"/></LABEL>
					<kit:radio styleClass="radio" name="jisekiESienListTorokuGroup_Search" property="torokuPtn" tabindex="3" value="B" styleId="torokuPtnB"/>
					<LABEL FOR="torokuPtnB"><bean:message key="com.text.torokuPtnB"/></LABEL>
					<kit:radio styleClass="radio" name="jisekiESienListTorokuGroup_Search" property="torokuPtn" tabindex="4" value="C" styleId="torokuPtnC"/>
					<LABEL FOR="torokuPtnC"><bean:message key="com.text.torokuPtnC"/></LABEL>

				</td>
			</tr>
		</table>
	<%-- 検索条件終了 --%>
	</kit:form>
	<hr />
	<%-- 検索フォーム終了 --%>
	<kit:form action="/jisekiESienListTorokuGroup_Edit" styleId="editForm">
		<kit:hidden name="jisekiESienListTorokuGroup_Edit" property="editable" />
		<%-- 明細ヘッダ --%>
		<div class="listHeader">
			<table class="listHeader headTitleTable" border="1" >
				<tr>
					<%-- 更新日 --%>
					<td class="head dateHWidth center"><bean:message key="com.text.kousinDate"/></td>
					<td class="data dtView dateWidth">
						<kit:write name="jisekiESienListTorokuGroup_Edit" property="torokuGroupList.kousinNitiziDt" ignore="true" format="yyyy/MM/dd HH:mm:ss"/>
					</td>
				</tr>
			</table>
		</div>
		<%-- リスト開始 --%>
		<div class="wrapper">
			<kit:equal name="jisekiESienListTorokuGroup_Search" property="torokuMaster" value="1">
				<%@ include file="/jsp/jiseki/jisekiESienListTorokuGroup/torokuGroup1.jsp"%>
			</kit:equal>
			<kit:equal name="jisekiESienListTorokuGroup_Search" property="torokuMaster" value="2">
				<%@ include file="/jsp/jiseki/jisekiESienListTorokuGroup/torokuGroup2.jsp"%>
			</kit:equal>
			<kit:equal name="jisekiESienListTorokuGroup_Search" property="torokuMaster" value="3">
				<%@ include file="/jsp/jiseki/jisekiESienListTorokuGroup/torokuGroup3.jsp"%>
			</kit:equal>
		</div>
		<div class="list_bottom">
			<table id="footButtons">
				<tr>
					<td class="center">
						<%-- セットボタン --%>
						<kit:button property="btnConfirm" styleClass="btn_large" tabindex="21" styleId="btnConfirm" name="jisekiESienListTorokuGroup_Search" key="notInit"
							onclick="setCdNmValues();">
							<bean:message key="com.button.set" />
						</kit:button>
					</td>
				</tr>
			</table>
		</div>
	</kit:form>
	<div class="bottom">
		<%-- 共通フッター開始（ポップアップ） --%>
		<%@ include file="/parts/comFooter_Pop.jsp"%>
		<%-- 共通フッター終了（ポップアップ） --%>
	</div>
</body>
</kit:html>
