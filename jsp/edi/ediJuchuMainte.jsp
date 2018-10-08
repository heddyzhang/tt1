<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page buffer="16kb" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%-- XXX:KzPoint --%>
<%@ taglib uri="/WEB-INF/struts-kit.tld" prefix="kit"%>
<kit:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title><bean:message key="com.title.ediJyuHacyuDataShori" /><kit:write name="ediJuchuMainte_Edit" property="modeNm" scope="request" ignore="true" /></title>
<link rel="stylesheet" href="./css/style.css" type="text/css">
<link rel="stylesheet" href="./css/styleKz.css" type="text/css">
<link rel="stylesheet" href="./css/styleKzView.css" type="text/css">
<link rel="stylesheet" href="./css/print.css" type="text/css" media="print">

<script type="text/javascript" src="./js/pbsControl.js"></script>
<script type="text/javascript" src="./js/comControl.js"></script>
<script type="text/javascript" src="./js/comMaster.js"></script>

<%-- XXX:KzPoint --%>
<script type="text/javascript" src="./js/kzControl.js"></script>
<script type="text/javascript">

	function setHomePosition() {
		// ホームオブジェクトを指定
		HOME_OBJECT = document.ediJuchuMainte_Search.jyusinDt;
	}

	// 画面固有の明細部以外領域の高さ指定
	var othersHeight = 298;

	// ヘッダ固定

	window.onresize = localResize;
	function localResize() {
		resize();
		setLocalHeight("list", "details", 45);
	}

	// CSVボタン押下時の処理
	function clickCsvBtn(ext){
		fileDownload("ediJuchuMainte_Search", ext, "ediJuchuMainte_Search.do");
	}
</script>

<style type="text/css">
<!--
/* 検索部 */
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


#list {
	float: left;
	width: 1188px
}
.fixedTitleTable {
	width: 1170px;
}

/* 一覧表レイアウト */
.txtShoriKbnWidth {width: 80px;} /* 処理区分 */
.txtJyushinDtWidth {width: 150px;} /* 受信日時 */
.txtHacyuNoWidth {width: 80px;} /* 発注No */
.txtLastSonisakiWidth {width: 380px;} /* 最終送荷先 */
.txtDataHasinMotoWidth {width: 400px;} /* ﾃﾞｰﾀ発信元 */
.txtJyucyuNoWidth {width: 80px;} /* Kz受注No */
/* 折返し表示項目（上記幅-10px） */
.doWrap { word-wrap: break-word; } /* 折返し指定 */
.txtDataHasinMotoWidthWrap {width: 390px;} /* ﾃﾞｰﾀ発信元 */

/* 検索ボタン */
table.form td.searchFormButtonRow div.center {
	text-align: center;
}

table.form td.searchFormButtonRow div.left {
	text-align: left;
}

table.form td.searchFormButtonRow div.right {
	text-align: right;
}

/* 明細部フッタボタン */
#footButtons {
	width: 99%;
	margin: 0;
	padding: 0;
}

#footButtons td.side {
	width: 23%;
}

#footButtons td.center {
	width: 53%;
}

#footButtons td.center input {
}

.floatLeft {
	float: left;
}

-->
</style>
</head>
<body
	<%-- onContextmenu="return false" --%>
	onLoad="eventLoad();
			setHomePosition();stopWaiting();
	"
	onbeforeunload="return eventUnloadMain();"
	<%-- XXX:「F1」キーでのIEヘルプ呼出し不活性化 --%>
	onhelp="return false;">
	<%-- 共通ヘッダー開始 --%>
	<%@ include file="/parts/comHeader.jsp"%>
	<%-- 共通ヘッダー終了 --%>

	<%-- 検索条件入力フォーム開始 --%>
	<kit:form action="/ediJuchuMainte_Search.do"  styleClass="searchForm">
		<%-- タイトル開始行 --%>
		<div class = "title">
			<bean:message key="com.title.ediJyuHacyuDataShori" />
			<kit:write name="ediJuchuMainte_Edit" property="modeNm" scope="request" ignore="true" />
		</div>
		<%-- タイトル終了 --%>

		<div class="buttons" style="margin: 1px;">
			<%-- 結果出力(EXCEL) --%>
			<kit:button
				styleClass="btn_long"
				property="btnCsvMake"
				key="excelBtnAvailFlg"
				kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
				onclick="if(eventPreSubmit()){offUnloadConfirm();startWaiting();clickCsvBtn('xlsx')}" >
				<bean:message key="com.button.csvmakeResult.excel" />
			</kit:button>
			<%-- 結果出力(CSV) --%>
			<kit:button
				styleClass="btn_long"
				property="btnCsvMake"
				key="csvBtnAvailFlg"
				kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
				onclick="if(eventPreSubmit()){offUnloadConfirm();startWaiting();clickCsvBtn('')}" >
				<bean:message key="com.button.csvmakeResult.csv" />
			</kit:button>
		</div>
		<div class="buttons" style="clear:both;margin: 1px;">
			<%-- 受注追加ボタン --%>
			<kit:button property="btnJyuchuAdd" styleClass="btn_large"
				kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
				onclick="if(eventPreSubmit()){setReqType('JyucyuDataKosin');offUnloadConfirm();startWaiting();submit()}">
				<bean:message key="com.button.JucyuAdd" />
			</kit:button>
			<%-- 呼出ボタン --%>
			<kit:button styleClass="btn_large" property="btnSearch"
				tabindex="5"
				onclick="if(eventPreSubmit()){setReqType('search');offUnloadConfirm();startWaiting();submit()}">
				<bean:message key="com.button.search" />
			</kit:button>
		</div>
		<kit:hidden name="ediJuchuMainte_Search" property="editable" />
		<kit:hidden name="ediJuchuMainte_Search" property="reqType" />
		<kit:hidden name="ediJuchuMainte_Search" property="outputType"/>

		<%-- 検索条件 --%>
		<table class="form" style="width:75%;">
			<tr>
				<td>
					<%-- 受信日 --%>
					<label for="jyusinDtLbl"><bean:message key="com.text.jyusinbi"/></label>&nbsp;&nbsp;&nbsp;&nbsp;
					<%-- 受信日 --%>
					<kit:text
						styleId="jyusinDtLbl" name="ediJuchuMainte_Search"
						styleClass="text ime_off right date frm_dt"
						property="jyusinDt" maxlength="8" size="13" tabindex="1"
						onkeyup="nextTabMaxLenInput()" onblur="ctrlInput()" onfocus="ctrlInput()"
						format="yyyy/mm/dd" />
					&nbsp;&nbsp;&nbsp;&nbsp;
					<%-- 受信時間 --%>
					<label for="jyusinTmLbl"><bean:message key="com.text.jyusinJikan"/></label>
					<%-- 受信時間（開始） --%>
					<kit:text
						styleId="jyusinTmLbl" name="ediJuchuMainte_Search"
						styleClass="text ime_off time frm_tm right"
						property="jyusinTmFrom" maxlength="4" size="10" tabindex="2"
						onkeyup="nextTabMaxLenInput()" onblur="ctrlInput()" onfocus="ctrlInput()"
						format="hh:mm" />
					<%-- ～ --%>
					<bean:message key="com.text.range" />
					<%-- 受信時間（終了） --%>
					<kit:text
						styleId="jyusinTmToLbl" name="ediJuchuMainte_Search"
						styleClass="text ime_off time frm_tm right"
						property="jyusinTmTo" maxlength="4" size="10" tabindex="3"
						onkeyup="nextTabMaxLenInput()" onblur="ctrlInput()" onfocus="ctrlInput()"
						format="hh:mm" />
				</td>
			</tr>
			<tr>
				<td>
					<%-- 処理区分 --%>
					<label for="shoriKbnLbl"><bean:message key="com.text.syoriKb" /></label>
					<kit:select
						styleId="shoriKbnLbl" name="ediJuchuMainte_Search"
						styleClass="list ime_off"
						property="syoriKbn" tabindex="4"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="ediJuchuMainte_ShoriKbnList"
						      optional="true" useDefault="true" ignoreFlg="false"/>
					</kit:select>
				</td>
			</tr>
		</table>
	</kit:form>
	<%-- 検索条件入力フォーム終了 --%>
	<hr />
	<%-- 照会・登録・更新・削除フォーム開始 --%>
	<kit:form action="/ediJuchuMainte_Edit.do">
		<kit:hidden name="ediJuchuMainte_Edit" property="editable" />
		<kit:hidden name="ediJuchuMainte_Edit" property="reqType" />
		<kit:hidden name="ediJuchuMainte_Edit" property="mode" />
		<kit:hidden name="ediJuchuMainte_Edit" property="preMode" />
		<kit:hidden name="ediJuchuMainte_Edit" property="maxSu" />
		<%-- XXX:KzPoint / 選択行番号保持用要素 --%>
		<kit:hidden name="ediJuchuMainte_Edit" property="selectedRowId" />
		<kit:hidden name="ediJuchuMainte_SearchedList" property="hasPrevPage" defaultValue="false" />
		<kit:hidden name="ediJuchuMainte_SearchedList" property="hasNextPage" defaultValue="false" />
		<div class="list_top">
			<kit:notEqual name="ediJuchuMainte_Edit" property="mode" value="add" scope="request">
				<kit:notEqual name="ediJuchuMainte_Edit" property="preMode" value="add" scope="request">
					<kit:button property="btnFirst" styleClass="btn_large" onclick="setReqType('first');offUnloadConfirm();startWaiting();submit()">
						<bean:message key="com.button.first" />
					</kit:button>
					<kit:button property="btnPrev" styleClass="btn_large" onclick="setReqType('prev');offUnloadConfirm();startWaiting();submit()">
						<bean:message key="com.button.previous" />
					</kit:button>
					<kit:button property="btnNext" styleClass="btn_large" onclick="setReqType('next');offUnloadConfirm();startWaiting();submit()">
						<bean:message key="com.button.next" />
					</kit:button>
					<kit:button property="btnLast" styleClass="btn_large" onclick="setReqType('last');offUnloadConfirm();startWaiting();submit()">
						<bean:message key="com.button.last" />
					</kit:button>
					<span class="page_no">
						<kit:write name="ediJuchuMainte_SearchedList" property="currentPage" ignore="true">
							<bean:message key="com.text.slash" />
						</kit:write>
						<kit:write name="ediJuchuMainte_SearchedList" property="totalPage" ignore="true">
							<bean:message key="com.text.page" />
						</kit:write>
						 <%--総件数対応例 開始--%>
						 <kit:present name="ediJuchuMainte_SearchedList" property="totalCount">
							<bean:message key="com.text.leftKakko" />
							<bean:message key="com.text.totalCount" />
							<kit:write name="ediJuchuMainte_SearchedList" property="totalCount" ignore="true">
								<bean:message key="com.text.ken" />
							</kit:write>
							<bean:message key="com.text.rightKakko" />
						</kit:present>
						<%--総件数対応例 終了--%>
					</span>
				</kit:notEqual>
			</kit:notEqual>
		</div>
		<%-- リスト開始 --%>
		<div class="wrapper">
		<div id="list" class="fixedTitleX">
			<%-- タイトル行 --%>
			<table id="listHeader" class="fixedTitleTable list" style="margin:0;">
				<%-- 列幅グループ化指定 --%>
				<colgroup class="txtShoriKbnWidth"></colgroup> <%-- 処理区分 --%>
				<colgroup class="txtJyushinDtWidth"></colgroup> <%-- 受信日時 --%>
				<colgroup class="txtHacyuNoWidth"></colgroup> <%-- 発注No --%>
				<colgroup class="txtLastSonisakiWidth"></colgroup> <%-- 最終送荷先 --%>
				<colgroup class="txtDataHasinMotoWidth"></colgroup> <%-- ﾃﾞｰﾀ発信元 --%>
				<colgroup class="txtJyucyuNoWidth"></colgroup> <%-- Kz受注No --%>
				<%-- タイトル行 --%>
				<tr class="head">
					<%-- 処理区分 --%>
					<th>
						<bean:message key="com.text.syoriKb" />
					</th>
					<%-- 受信日時 --%>
					<th>
						<bean:message key="com.text.jyusinNiji" />
					</th>
					<%-- 発注No --%>
					<th>
						<bean:message key="com.text.hacyuNo" />
					</th>
					<%-- 最終送荷先 --%>
					<th>
						<bean:message key="com.text.saisyuSonisakiLast" />
					</th>
					<%-- ﾃﾞｰﾀ発信元 --%>
					<th>
						<bean:message key="com.text.dataHasinMoto" />
					</th>
					<%-- Kz受注No --%>
					<th>
						<bean:message key="com.text.kzJyucyuNo" />
					</th>
				</tr>
			</table>
			<%-- レコード行 --%>
			<div id="details" class="fixedTitleY">
				<table class="fixedTitleTable list" style="margin-top:-1pt">
				<%-- 列幅グループ化指定 --%>
				<colgroup class="txtShoriKbnWidth"></colgroup> <%-- 処理区分 --%>
				<colgroup class="txtJyushinDtWidth"></colgroup> <%-- 受信日時 --%>
				<colgroup class="txtHacyuNoWidth"></colgroup> <%-- 発注No --%>
				<colgroup class="txtLastSonisakiWidth"></colgroup> <%-- 最終送荷先 --%>
				<colgroup class="txtDataHasinMotoWidth"></colgroup> <%-- ﾃﾞｰﾀ発信元 --%>
				<colgroup class="txtJyucyuNoWidth"></colgroup> <%-- Kz受注No --%>
				<%-- レコード行 --%>
				<kit:iterate name="ediJuchuMainte_SearchedList"
					id="ediJuchuMainteRecord" property="pageList" indexId="idx"
					tabStartIndex="1" scope="session">
					<%-- XXX:KzPoint
					追加属性
					id : TRタグのID属性を示す接尾辞
					onmouseover：マウスをかざした時のイベント属性
					onmouseout：マウスが離れたときのイベント属性
					onclick：マウスクリックしたときのイベント属性
					ondblclick：ダブルクリックしたときのイベント属性
					※ setRowKeys(…)、setSelectClick({EditForm名})は、画面毎に要編集 style="display:none"
					 --%>
					<kit:tr name="ediJuchuMainteRecord" errorProperty="hasError" id="id"
						modProperty="isModified" evenClass="even" oddClass="odd"
						errorClass="error" modClass="modified" indexed="true" styleClass="line"
						onmouseover="setRowMouseOver(this)" onmouseout="setRowMouseOut(this)"
						onclick="setRowKeys(this, 'ediJuchuMainte_Edit', 'ediJyuhacyuId', 'ediJyuhacyuId')"
						ondblclick="selectClick('ediJuchuMainte_Edit','btnConfirm')">

						<%-- 処理区分 --%>
						<td class="mjView">
							<kit:write name="ediJuchuMainteRecord" property="syoriKbn" ignore="true"
					 		    decodeList="ediJuchuMainte_ShoriKbnList" decodeWrite="name" />
						</td>
						<%-- 受信日時 --%>
						<td class="dtView">
							<kit:write name="ediJuchuMainteRecord" property="jyusinDt" format="yyyy/mm/dd"/>&nbsp;
							<kit:write name="ediJuchuMainteRecord" property="jyusinTm" format="hh:mm:ss"/>
					 	</td>
						<%-- 発注No --%>
						<td class="cdxView">
							<kit:write name="ediJuchuMainteRecord" property="dhDenpyoNo" />
						</td>
						<%-- 最終送荷先 --%>
						<td class="mjView">
							<kit:write name="ediJuchuMainteRecord" property="lastSouniSaki" />
					 	</td>
						<%-- ﾃﾞｰﾀ発信元 --%>
						<td class="mjView txtDataHasinMotoWidthWrap doWrap" >
							<kit:write name="ediJuchuMainteRecord" property="dataHasinMoto" />
						</td>
						<%-- Kz受注No --%>
						<td class="cdxView">
							<kit:write name="ediJuchuMainteRecord" property="jyucyuNo" />
						</td>
					</kit:tr>

					<%-- 行選択時値取得用hidden要素 --%>
					<kit:hidden property="ediJyuhacyuId" indexed="true" name="ediJuchuMainteRecord" />
				</kit:iterate>
				</table>
			</div><%-- / class="fixedTitleY" --%>
		</div><%-- / class="fixedTitleX" --%>
		</div><%-- / class="wrapper" --%>

		<%-- リスト終了 --%>
		<div class="list_bottom">
			<table id="footButtons" >
				<tr>
					<td class="side">

					</td>
					<td class="center">
						<%-- ID --%>
						<kit:text name="ediJuchuMainte_Edit" property="ediJyuhacyuId" maxlength="15" size="20"
							styleClass="text numeric ime_off code frm_lpad right"
							styleId="tEdiJyuhacyuIdLbl" tabindex="1"
							errorProperty="ediJyuhacyuIdClass" errorClass="error"
							onkeyup="nextTabMaxLenInput()" onblur="ctrlInput()" onfocus="ctrlInput()" />
						<%-- セットボタン --%>
						<kit:button property="btnConfirm"
							styleClass="btn_large" tabindex="2"
							onclick="if(eventPreSubmit()){setReqType('select');offUnloadConfirm();submit()}">
							<bean:message key="com.button.set" />
						</kit:button>

					</td>
					<td class="side">&nbsp;</td>
				</tr>
			</table>
		</div>
		<div id="viewWaiting" style="z-index:1;"></div>
	</kit:form>
	<%-- 照会・追加・更新・削除フォーム終了 --%>
	<hr />
	<%-- 共通フッター開始 --%>
	<%@ include file="/parts/comFooter.jsp"%>
	<%-- 共通フッター終了 --%>
</body>
</kit:html>