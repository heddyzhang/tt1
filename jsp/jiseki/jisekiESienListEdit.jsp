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
<title><bean:message key="com.title.jisekiESienListResult"/></title>
<link rel="stylesheet" href="./css/style.css" type="text/css">
<link rel="stylesheet" href="./css/styleKz.css" type="text/css">
<link rel="stylesheet" href="./css/styleKzView.css" type="text/css">
<script type="text/javascript" src="./js/pbsControl.js"></script>
<script type="text/javascript" src="./js/comControl.js"></script>
<script type="text/javascript" src="./js/comMaster.js"></script>
<script type="text/javascript" src="./js/kzControl.js"></script>

<script type="text/javascript">
// 画面固有の明細部以外領域の高さ指定
var othersHeight = 150;
// ホームオブジェクトを指定
function setHomePosition() {
	HOME_OBJECT = document.jisekiESienList_Search.btnOutput;
}
//ヘッダ固定
window.onresize = localResize;
function localResize(){
	resize();
	setLocalHeight("list" ,"details" ,58);
}
// CSVボタン押下時の処理
function clickCsvBtn(){
	if (eventPreSubmit()) {
		offUnloadConfirm();
		startWaiting();
		var form = document.jisekiESienList_Edit;
		form.action = "/kit//jisekiESienList_Csv.do";
		form.submit();
	}
}
</script>
<bean:define id="headerWidth" name="jisekiESienList_SearchList" property="headerWidth" scope="session" type="java.lang.Integer"/>
<style type="text/css">
<!--
/** 検索部 */
table.form {
	width: 100%;
}
table.form td {
	white-space: nowrap;
}
/* 見栄え調整用 */
table.form label.layout {
	margin-left: 1.5em;
}
div.list_top {
	text-align: left;
	padding: 1pt;
	margin: 1pt 0pt 1pt;
	font-size: 11pt;
}
table#paging {
	width: 100%;
	font-size: 11pt;
}
table.list th{
	padding-left: 0.5em;
	padding-right: 0.5em;
	text-align: center;
}
table.list td {
	white-space: nowrap;
	padding-left: 0.5em;
	padding-right: 0.5em;
}
.midasiLargeWidth{width:230px;}
.midasiMediumWidth{width:170px;}
.midasiSmallWidth{width:80px;}
.numWidth{width:130px;}
.fixedTitleTable{
	width:<%=headerWidth%>px;
}
#list #details{
	float: left;
	width:<%=headerWidth+18%>px;
}
</style>
</head>
<body
	onContextmenu="return false"
	onLoad="eventLoadPopup();setHomePosition();stopWaiting();openOnloadPopup();"
	onUnload="eventUnloadPopup()"
	onhelp="return false;">

	<%-- 共通ヘッダー開始 --%>
	<%@ include file="/parts/comHeader_Pop.jsp"%>
	<%-- 共通ヘッダー終了 --%>

	<%-- 検索条件入力フォーム開始 --%>
	<kit:form action="/jisekiESienList_Search" styleClass="searchForm">
		<kit:hidden name="jisekiESienList_SearchResult" property="editable" />
		<kit:hidden name="jisekiESienList_SearchResult" property="reqType" />
		<div class="title"><bean:message key="com.title.jisekiESienListResult"/></div>
		<div class="buttons">
			<table>
				<tr>
					<td>
						<%-- ファイル出力 --%>
						<kit:button styleClass="btn_large"
							property="btnOutput" tabindex="1"
							kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
							onclick="clickCsvBtn();">
							<bean:message key="com.button.fileOutput" />
						</kit:button>
					</td>
				</tr>
			</table>
		</div>
		<%-- タイトル終了 --%>
		<%-- 検索条件 --%>
		<table class="form">
			<tr><td></td></tr>
		</table>
	<%-- 検索条件終了 --%>
	</kit:form>
	<%-- 検索フォーム終了 --%>
	<kit:form action="/jisekiESienList_Edit" styleId="editForm">
		<kit:hidden name="jisekiESienList_Edit" property="editable" />
		<kit:hidden name="jisekiESienList_Edit" property="reqType" />
		<kit:hidden name="jisekiESienList_SearchList" property="hasPrevPage" defaultValue="false" />
		<kit:hidden name="jisekiESienList_SearchList" property="hasNextPage" defaultValue="false" />
		<div class="list_top">
			<table id="paging">
				<tr>
					<td>
						<kit:button styleClass="btn_large" property="btnFirst"
							onclick="setReqType('first');offUnloadConfirm();document.editForm.submit();">
							<bean:message key="com.button.first" />
						</kit:button>
						<kit:button styleClass="btn_large" property="btnPrev"
							onclick="setReqType('prev');offUnloadConfirm();document.editForm.submit();">
							<bean:message key="com.button.previous" />
						</kit:button>
						<kit:button styleClass="btn_large" property="btnNext"
							onclick="setReqType('next');offUnloadConfirm();document.editForm.submit();">
							<bean:message key="com.button.next" />
						</kit:button>
						<kit:button styleClass="btn_large" property="btnLast"
							onclick="setReqType('last');offUnloadConfirm();document.editForm.submit();">
							<bean:message key="com.button.last" />
						</kit:button>
						<span class="page_no"> <kit:write name="jisekiESienList_SearchList"
								property="currentPage" ignore="true">
								<bean:message key="com.text.slash" />
							</kit:write> <kit:write name="jisekiESienList_SearchList" property="totalPage" ignore="true">
								<bean:message key="com.text.page" />
							</kit:write> <%--総件数対応例 開始--%> <kit:present name="jisekiESienList_SearchList"
								property="totalCount">
								<bean:message key="com.text.leftKakko" />
								<bean:message key="com.text.totalCount" />
								<kit:write name="jisekiESienList_SearchList" property="totalCount"
									ignore="true">
									<bean:message key="com.text.ken" />
								</kit:write>
								<bean:message key="com.text.rightKakko" />
							</kit:present> <%--総件数対応例 終了--%>
						</span>
					</td>
					<td style="float: right;">
						<table class="listHeader headTitleTable" border="1" >
							<tr>
								<%-- システム時刻 --%>
								<td class="head dateHWidth center" ><bean:message key="com.text.sysdate" /></td>
								<td class="data dtView dateWidth">
									<kit:write name="jisekiESienList_Edit" property="sysDt" ignore="true" scope="session"/>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<%-- リスト開始 --%>
		<div class="wrapper">
			<div id="list"  class="fixedTitleX">
				<table id="tableHead" class="fixedTitleTable list" style="margin:0;">
					<%-- 見出し --%>
					<kit:iterate id="colValue" name="jisekiESienList_SearchList" property="oneRec.midasiColValueList" scope="session">
						<kit:iterate id="nm" name="colValue" property="nmList">
							<bean:define id="classNm" name="nm" property="midasiClass.width" />
							<colgroup class="<%=classNm %>"></colgroup>
						</kit:iterate>
					</kit:iterate>
					<%-- 累計 --%>
					<kit:iterate id="colValue" name="jisekiESienList_SearchList" property="oneRec.ruikeiColValueList" scope="session">
						<colgroup class="numWidth"></colgroup>
					</kit:iterate>
					<%-- 日付 --%>
					<kit:iterate id="colValue" name="jisekiESienList_SearchList" property="oneRec.dateColValueList" scope="session">
						<colgroup class="numWidth"></colgroup>
					</kit:iterate>
					<%-- ヘッダ1行の場合 --%>
					<kit:equal name="jisekiESienList_SearchList" property="tableHeaderInfo.header1Line" value="true" scope="session">
						<tr class="head">
							<%-- 見出し --%>
							<kit:iterate id="midasiHeader" name="jisekiESienList_SearchList" property="tableHeaderInfo.midasiHeaderList" scope="session">
								<kit:iterate id="nameCol" name="midasiHeader" property="nameColList">
									<td class="mjView" >
										<kit:write name="nameCol" property="nm" />
									</td>
								</kit:iterate>
							</kit:iterate>
							<%-- 累計 --%>
							<td class="mjView">
								<kit:write name="jisekiESienList_SearchList" property="tableHeaderInfo.ruikeiHeader.row1Col" scope="session"/>
							</td>
							<%-- 日付 --%>
							<kit:iterate id="dateHeader" name="jisekiESienList_SearchList" property="tableHeaderInfo.dateHeaderList" scope="session">
								<td class="mjView">
									<kit:write name="dateHeader" property="row1Col" />
								</td>
							</kit:iterate>
						</tr>
					</kit:equal>
					<%-- ヘッダ2行の場合 --%>
					<kit:equal name="jisekiESienList_SearchList" property="tableHeaderInfo.header1Line" value="false" scope="session">
						<tr class="head">
							<%-- 見出し --%>
							<kit:iterate id="midasiHeader" name="jisekiESienList_SearchList" property="tableHeaderInfo.midasiHeaderList" scope="session">
								<kit:iterate id="nameCol" name="midasiHeader" property="nameColList">
									<td class="mjView" rowspan="2">
										<kit:write name="nameCol" property="nm" />
									</td>
								</kit:iterate>
							</kit:iterate>
							<%-- 累計（1行の場合） --%>
							<kit:equal name="jisekiESienList_SearchList" property="tableHeaderInfo.ruikeiHeader1Line" value="true" scope="session">
								<td class="mjView" rowspan="2">
									<kit:write name="jisekiESienList_SearchList" property="tableHeaderInfo.ruikeiHeader.row1Col" scope="session"/>
								</td>
							</kit:equal>
							<%-- 累計（2行の場合） --%>
							<kit:equal name="jisekiESienList_SearchList" property="tableHeaderInfo.ruikeiHeader1Line" value="false" scope="session">
								<bean:define id="ruikeiColspan" name="jisekiESienList_SearchList" property="tableHeaderInfo.ruikeiHeader.headerColspan" />
								<td class="mjView" colspan="<%=ruikeiColspan %>">
									<kit:write name="jisekiESienList_SearchList" property="tableHeaderInfo.ruikeiHeader.row1Col" scope="session"/>
								</td>
							</kit:equal>
							<%-- 日付 --%>
							<kit:iterate id="dateHeader" name="jisekiESienList_SearchList" property="tableHeaderInfo.dateHeaderList" scope="session">
								<bean:define id="dateColspan" name="dateHeader" property="headerColspan" />
								<td class="mjView" colspan="<%=dateColspan %>">
									<kit:write name="dateHeader" property="row1Col" />
								</td>
							</kit:iterate>
						</tr>
						<tr class="head">
							<%-- 累計（2行の場合） --%>
							<kit:equal name="jisekiESienList_SearchList" property="tableHeaderInfo.ruikeiHeader1Line" value="false" scope="session">
								<kit:iterate id="ruikeiData" name="jisekiESienList_SearchList" property="tableHeaderInfo.ruikeiHeader.row2ColList" scope="session">
									<td class="mjView">
										<kit:write name="ruikeiData"/>
									</td>
								</kit:iterate>
							</kit:equal>
							<%-- 日付 --%>
							<kit:iterate id="dateHeader" name="jisekiESienList_SearchList" property="tableHeaderInfo.dateHeaderList" scope="session">
								<kit:iterate id="dateData" name="dateHeader" property="row2ColList">
									<td class="mjView">
										<kit:write name="dateData"/>
									</td>
								</kit:iterate>
							</kit:iterate>
						</tr>
					</kit:equal>
				</table>
				<div id="details" class="fixedTitleY">
					<table id="tableData" class="fixedTitleTable list" style="margin-top:-1pt;">
						<%-- 見出し --%>
						<kit:iterate id="colValue" name="jisekiESienList_SearchList" property="oneRec.midasiColValueList" scope="session">
							<kit:iterate id="nm" name="colValue" property="nmList">
								<bean:define id="classNm" name="nm" property="midasiClass.width" />
								<colgroup class="<%=classNm %>"></colgroup>
							</kit:iterate>
						</kit:iterate>
						<%-- 累計 --%>
						<kit:iterate id="colValue" name="jisekiESienList_SearchList" property="oneRec.ruikeiColValueList" scope="session">
							<colgroup class="numWidth"></colgroup>
						</kit:iterate>
						<%-- 日付 --%>
						<kit:iterate id="colValue" name="jisekiESienList_SearchList" property="oneRec.dateColValueList" scope="session">
							<colgroup class="numWidth"></colgroup>
						</kit:iterate>
						<%-- レコードループ開始 --%>
						<kit:iterate id="record" name="jisekiESienList_SearchList" property="pageList" indexId="idx" tabStartIndex="1" scope="session">
							<kit:tr name="record" evenClass="even" oddClass="odd" onmouseover="setRowMouseOver(this)" onmouseout="setRowMouseOut(this)">
								<%-- 見出し --%>
								<kit:iterate id="colValue" name="record" property="midasiColValueList">
									<kit:iterate id="nm" name="colValue" property="nmList">
										<bean:define id="classNm" name="nm" property="midasiClass.view" />
										<td class="<%=classNm %>">
											<kit:write name="nm" property="value" />
										</td>
									</kit:iterate>
								</kit:iterate>
								<%-- 累計 --%>
								<kit:iterate id="colValue" name="record" property="ruikeiColValueList">
									<td class="nmView">
										<kit:write name="colValue" property="value" format="99,999,999,999"/>
									</td>
								</kit:iterate>
								<%-- 日付 --%>
								<kit:iterate id="colValue" name="record" property="dateColValueList">
									<td class="nmView">
										<kit:write name="colValue" property="value" format="99,999,999,999"/>
									</td>
								</kit:iterate>
							</kit:tr>
						</kit:iterate>
						<%-- レコードループ終了 --%>
					</table>
				</div>
			</div>
		</div>
		<div id="viewWaiting" style="z-index:1;"></div>
	</kit:form>
	<div class="bottom">
		<DIV CLASS="footer_popup">
			<TABLE WIDTH="90%" ALIGN="center" BORDER="0">
				<TR>
					<TD ALIGN="right">
					<DIV style="position:relative;">
						<img id="guideIcon" SRC='<bean:message key="com.img.guide"/>' style="position:absolute; visibility:hidden; display:block; z-index:1; cursor: hand;" onmouseover="overGuideIcon()" onclick="resizeGuide(this)">
						<TEXTAREA CLASS="guide_popup" READONLY NAME="guide" COLS="150" ROWS="3" WRAP="soft" ID="guide" onmouseover="overGuideArea()" onmouseout="outGuideArea()"><html:errors/></TEXTAREA>
					</DIV>
					</TD>
					<TD ALIGN="right" VALIGN="top">
					<FORM NAME="frmFooter">
						<INPUT NAME="editable" TYPE="hidden" VALUE="true">
						<INPUT NAME="switch" TYPE="hidden" VALUE="ignore">
						<kit:button styleClass="btn_large" property="btnClose" onclick="window.close()">
							<bean:message key="com.button.close"/>
						</kit:button>
					</FORM>
					</TD>
			</TABLE>
		</DIV>
	</div>
</body>
</kit:html>
