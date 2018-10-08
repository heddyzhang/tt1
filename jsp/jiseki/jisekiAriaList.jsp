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
<title><bean:message key="com.title.ariaJiseki" /></title>
<link rel="stylesheet" href="./css/style.css" type="text/css">

<%-- XXX:KzPoint --%>
<link rel="stylesheet" href="./css/styleKz.css" type="text/css">
<link rel="stylesheet" href="./css/print.css" type="text/css" media="print">

<script type="text/javascript" src="./js/pbsControl.js"></script>
<script type="text/javascript" src="./js/comControl.js"></script>
<script type="text/javascript" src="./js/comMaster.js"></script>
<%-- XXX:KzPoint --%>
<script type="text/javascript" src="./js/kzControl.js"></script>

<script type="text/javascript">
function setHomePosition(){
	// ホームオブジェクトを指定
	HOME_OBJECT = document.jisekiAriaList_Search.shiteibi;
}

// 画面固有の明細部以外領域の高さ指定
var othersHeight = 70;

// ヘッダ固定
//@@@ タイトル固定 ここから @@@@@@@@@@@@@@
window.onresize = localResize;
function localResize(){
	resize();
	setLocalHeight("list" ,"details" ,40);
}

//@@@ タイトル固定 ここまで @@@@@@@@@@@@@@

</script>

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

/* @@@ タイトル固定 ここから @@@@@@@@@@@@@@@@@@@@@@@@*/
.wrapper{
}

#list{
	float: left;
	width:1080px;
}
.fixedTitleTable{
	/* 表の横幅指定値 ***********************************
	 * 初期表示時に横スクロールさせない
	   最大値は、940px
	 * ウィンドウ最大化したとき横スクロールをさせないため
	   最大1300pxまでとする
	   それでも収まらないときは、明細１行をｎ段で表現する!
	 * **************************************************/
	width:1080px;
}

/* 一覧表レイアウト */
.areaNmWidth{width:130px;}
.nikeiWidth{width:70px;}
.togetsuKinWidth{width:80px;}
.zentouKinWidth{width:90px;}
.percentWidth{width:50px;}


/** 明細部 */
table.list td {
	white-space: nowrap;
	font-size:10pt;
}


</style>
</head>
<body
	<%--XXX:右クリック不許可属性
	 onContextmenu="return false"--%>
	onLoad="eventLoad();
	setHomePosition();
<%-- XXX:エラー時、エラー箇所セットフォーカス処理
setFocusLocal()--%>
	"
	onbeforeunload="return eventUnloadMain()"
	<%-- XXX:「F1」キーでのIEヘルプ呼出し不活性化 --%>
	onhelp="return false;">
	<%-- 共通ヘッダー開始 --%>
	<DIV CLASS="header" style="margin-bottom: -8px;">
		<kit:form action="/comLogout.do" style="clear:both;">
			<INPUT TYPE="hidden" NAME="editable" VALUE="true" />
			<INPUT TYPE="hidden" NAME="switch" VALUE="ignore" />
			<INPUT TYPE="hidden" NAME="loginFl" VALUE="" />
		</kit:form>
		<kit:form action="/comMenu.do" style="float:right;">
			<INPUT TYPE="hidden" NAME="editable" VALUE="true" />
			<INPUT TYPE="hidden" NAME="switch" VALUE="ignore" />
			<INPUT TYPE="hidden" NAME="reqType" VALUE="outOfService" />
		</kit:form>

		<%-- for debug --%>
		<kit:sessionId />
		<%-- for debug <IMG ID="searchIcon" SRC='<bean:message key="com.img.search"/>' ALIGN="right" style="position: relative; top: 1px; z-index: 1;cursor:pointer;visibility:hidden;" alt="" onClick="switchSearchFormVisible(this)"/> --%>

	</DIV>
	<%-- 共通ヘッダー終了 --%>

<table>
<tr>
<td valign="top">
	<div style = "margin-top: 6px;">
	<%-- 検索条件入力フォーム開始 --%>
	<kit:form action="/jisekiAriaList_Search" styleClass="searchForm">
		<%-- タイトル開始行 --%>
		<div class="title" style = "width:90%;"><bean:message key="com.title.ariaJiseki" /></div>
		<%-- タイトル終了 --%>
		<kit:hidden name="jisekiAriaList_Search" property="editable" />
		<kit:hidden name="jisekiAriaList_Search" property="reqType" />
		<table class="form" style="width:75%;margin: 40px 1px 5px 1px;">
			<%-- 検索条件 --%>
			<tr>
				<%-- 日付 --%>
				<td>
					<label for="shiteibiLbl"><bean:message key="com.text.dt" /></label>
					<kit:text styleId="shiteibiLbl"
						name="jisekiAriaList_Search"
						styleClass="text ime_off left date frm_dt must" property="shiteibi"
						maxlength="8" size="13" tabindex="1"
						onkeyup="nextTabMaxLenInput(8)" onblur="ctrlInput()"
						onfocus="ctrlInput()" format="yyyy/mm/dd" />
				</td>
			</tr>
			<tr><td><br/><td></tr>
			<tr>
				<%-- 表示単位 --%>
				<td>
					<bean:message key="com.text.hyojitani" />
				</td>
				</tr>
			<tr>
				<td>&nbsp;
					<kit:radio styleClass="radio" name="jisekiAriaList_Search" property="hyoujiTanyi" tabindex="2" value="1"
					styleId="kinGaku"/>
					<LABEL FOR="kinGaku" ACCESSKEY=""><bean:message key="com.text.kingakuSen" /></LABEL>
				</td>
			</tr>
			<tr>
				<td>&nbsp;
					<kit:radio styleClass="radio" name="jisekiAriaList_Search" property="hyoujiTanyi" tabindex="3" value="2"
					styleId="kokuSu"/>
					<LABEL FOR="kokuSu" ACCESSKEY=""><bean:message key="com.text.kokuSu" /></LABEL>
				</td>
			</tr>
		<tr><td><br/><td></tr>
		<tr>
			<%-- 呼出ボタン --%>
			<td><div class="buttons" style="clear: both;float:left;margin: 13px">
			&nbsp;<kit:button styleClass="btn_large" property="btnSearch" tabindex="4"
				onclick="if(eventPreSubmit()){setReqType('search');offUnloadConfirm() ;submit()}">
				<bean:message key="com.button.search" />
			</kit:button>
			</div></td>
		</tr>
		<tr><td><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/></td></tr>
		<tr>
			<%-- 検索日時 --%>
			<td><bean:message key="com.text.kensakuNiji" /></td>
		</tr>
		<tr>
			<td>&nbsp;<kit:write name="jisekiAriaList_Search" property="kensakuNiji" ignore="true" /></td>
		</tr>
		</table>
	</kit:form>
	<%-- 検索条件入力フォーム終了 --%>
	</div>
</td>

<td valign="top">
	<%-- リスト開始 --%>
	<div class="wrapper">
	<div id="list" class="" style = "margin-left:3px;">
		<%-- タイトル行 --%>
		<table id="listTable" class="fixedTitleTable list" style="margin-bottom:0px;">
			<colgroup class="areaNmWidth"></colgroup>
			<colgroup class="nikeiWidth"></colgroup>
			<colgroup class="togetsuKinWidth"></colgroup>
			<colgroup class="togetsuKinWidth"></colgroup>
			<colgroup class="togetsuKinWidth"></colgroup>
			<colgroup class="togetsuKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<thead>
				<tr class="head">
					<%-- エリア名 --%>
					<th rowspan=2><bean:message key="com.text.areaNm" /></th>
					<%-- 当月 --%>
					<th colspan=7 ><bean:message key="com.text.touGetsu" /></th>
					<%-- 10月～当月 --%>
					<th colspan=3 ><bean:message key="com.text.jyugatsuTougetsu" /></th>
					<%-- 1月～当月 --%>
					<th colspan=3 ><bean:message key="com.text.ichigatsuTougetsu" /></th>
				</tr>
				<tr class="head">
					<%-- 日計 --%>
					<th><bean:message key="com.text.nikei" /></th>
					<%-- 当月累計 --%>
					<th><bean:message key="com.text.tougetsuRuikei" /></th>
					<%-- 前年同日 --%>
					<th><bean:message key="com.text.zennenTojitsu" /></th>
					<%-- 前年月末 --%>
					<th><bean:message key="com.text.zennenGetsumatsu" /></th>
					<%-- 前前年月末 --%>
					<th><bean:message key="com.text.zenzennenGetsumatsu" /></th>
					<%-- 同日比 --%>
					<th><bean:message key="com.text.doujitsuhi" /></th>
					<%-- 進捗率--%>
					<th><bean:message key="com.text.statusRt" /></th>
					<%-- 当期--%>
					<th><bean:message key="com.text.touki" /></th>
					<%-- 前期--%>
					<th><bean:message key="com.text.zennki" /></th>
					<%-- 進捗率--%>
					<th><bean:message key="com.text.statusRt" /></th>
					<%-- 当期--%>
					<th><bean:message key="com.text.touki" /></th>
					<%-- 前期--%>
					<th><bean:message key="com.text.zennki" /></th>
					<%-- 進捗率--%>
					<th><bean:message key="com.text.statusRt" /></th>
				</tr>
			</thead>
		</table>

		<%-- 表領域開始--%>
		<div id="details" class="">
		<table id="detailsTable" class="fixedTitleTable list" style="margin-top:-1pt">
		<colgroup class="areaNmWidth"></colgroup>
		<colgroup class="nikeiWidth"></colgroup>
		<colgroup class="togetsuKinWidth"></colgroup>
		<colgroup class="togetsuKinWidth"></colgroup>
		<colgroup class="togetsuKinWidth"></colgroup>
		<colgroup class="togetsuKinWidth"></colgroup>
		<colgroup class="percentWidth"></colgroup>
		<colgroup class="percentWidth"></colgroup>
		<colgroup class="zentouKinWidth"></colgroup>
		<colgroup class="zentouKinWidth"></colgroup>
		<colgroup class="percentWidth"></colgroup>
		<colgroup class="zentouKinWidth"></colgroup>
		<colgroup class="zentouKinWidth"></colgroup>
		<colgroup class="percentWidth"></colgroup>
		<tbody>

		<%-- レコード行 --%>
		<%-- 事業所レベルのデータ --%>
		<kit:iterate id="jisekiAriaRecord" name="jisekiAriaJigyosyo_SearchList"
			 indexId="idx" tabStartIndex="1" scope="session">
			<kit:tr name="jisekiAriaRecord" id="id" evenClass="even" oddClass="odd" indexed="true" styleClass="line"
				onmouseover="setRowMouseOver(this)" onmouseout="setRowMouseOut(this)">
				<%-- エリア名 --%>
				<td class="cdxView">
					<kit:write name="jisekiAriaRecord" property="ariaNm" ignore="true" />
				</td>
				<%-- 日計 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="nikei" ignore="true" format="99,999,999,999" /></td>
				<%-- 当月累計 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="togetsuRuikei" ignore="true" format="99,999,999,999" />
				</td>
				<%-- 前年同日 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="zennenDojitsu" ignore="true" format="99,999,999,999" />
				</td>
				<%-- 前年月末 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="zennenGetsumatsu" ignore="true" format="99,999,999,999" />
				</td>
				<%-- 前々年月末 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="zenzennenGetsumatsu" ignore="true" format="99,999,999,999" />
				</td>
				<%-- 同日比 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="doujitsuhi" ignore="true" />
				</td>
				<%-- 進歩率 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="togetsuShincyoku" ignore="true" />
				</td>
				<%-- 当期(10月) --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="jyugatsuToki" ignore="true" format="99,999,999,999" />
				</td>
				<%-- 前期(10月)  --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="jyugatsuZenki" ignore="true" format="99,999,999,999" />
				</td>
				<%-- 進歩率(10月) --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="jyugatsuShincyoku" ignore="true" />
				</td>
				<%-- 当期(1月) --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="ichigatsuToki" ignore="true" format="99,999,999,999" />
				</td>
				<%-- 前期(1月) --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="ichigatsuZenki" ignore="true" format="99,999,999,999" />
				</td>
				<%-- 進歩率(1月) --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="ichigatsuShincyoku" ignore="true" />
				</td>
			</kit:tr>
		</kit:iterate>

		<kit:present name="jisekiAriaKa_SearchList">
		<tr><td colspan = "14"></td></tr>
		<%-- レコード行 --%>
		<%-- 課レベルのデータ --%>
		<kit:iterate id="jisekiAriaRecord" name="jisekiAriaKa_SearchList"
			 indexId="idx" tabStartIndex="1" scope="session">
			 <kit:tr name="jisekiAriaRecord" id="id" evenClass="even" oddClass="odd" indexed="true" styleClass="line"
				onmouseover="setRowMouseOver(this)" onmouseout="setRowMouseOut(this)">
				<%-- エリア名 --%>
				<td class="cdxView">
					<kit:write name="jisekiAriaRecord" property="ariaNm" ignore="true"/>
				</td>
				<%-- 日計 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="nikei" ignore="true" format="99,999,999,999"/>
				</td>
				<%-- 当月累計 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="togetsuRuikei" ignore="true" format="99,999,999,999"/>
				</td>
				<%-- 前年同日 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="zennenDojitsu" ignore="true" format="99,999,999,999"/>
				</td>
				<%-- 前年月末 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="zennenGetsumatsu" ignore="true" format="99,999,999,999"/>
				</td>
				<%-- 前々年月末 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="zenzennenGetsumatsu" ignore="true" format="99,999,999,999"/>
				</td>
				<%-- 同日比 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="doujitsuhi" ignore="true" />
				</td>
				<%-- 進歩率 --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="togetsuShincyoku" ignore="true" />
				</td>
				<%-- 当期(10月) --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="jyugatsuToki" ignore="true" format="99,999,999,999"/>
				</td>
				<%-- 前期(10月)  --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="jyugatsuZenki" ignore="true" format="99,999,999,999"/>
				</td>
				<%-- 進歩率(10月) --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="jyugatsuShincyoku" ignore="true" />
				</td>
				<%-- 当期(1月) --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="ichigatsuToki" ignore="true" format="99,999,999,999"/>
				</td>
				<%-- 前期(1月) --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="ichigatsuZenki" ignore="true" format="99,999,999,999"/>
				</td>
				<%-- 進歩率(1月) --%>
				<td class="nmView">
					<kit:write name="jisekiAriaRecord" property="ichigatsuShincyoku" ignore="true" />
				</td>
			</kit:tr>
		</kit:iterate>
		</kit:present>
		</tbody>
		</table>
		</div>

	</div><%-- / class="fixedTitleX" --%>
	</div><%-- / class="wrapper" --%>
	<%-- リスト終了 --%>
</td>

</tr>
</table>
	<hr />
	<%-- 共通フッター開始 --%>
	<%@ include file="/parts/comFooter.jsp"%>
	<%-- 共通フッター終了 --%>
</body>
</kit:html>