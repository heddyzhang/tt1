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
<title><bean:message key="com.title.categoryJiseki" /></title>
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
	HOME_OBJECT = document.jisekiCategoryList_Search.shiteibi;
}

// 画面固有の明細部以外領域の高さ指定
var othersHeight = 70;

// ヘッダ固定
//@@@ タイトル固定 ここから @@@@@@@@@@@@@@
window.onresize = localResize;
function localResize(){
	resize();
	setLocalHeight("list" ,"details" ,45);
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
	width:1180px;
	overflow-y:hidden;
}
.fixedTitleTable{
	/* 表の横幅指定値 ***********************************
	 * 初期表示時に横スクロールさせない
	   最大値は、940px
	 * ウィンドウ最大化したとき横スクロールをさせないため
	   最大1300pxまでとする
	   それでも収まらないときは、明細１行をｎ段で表現する!
	 * **************************************************/
	width:2550px;
}

/* 一覧表レイアウト */
.areaNmWidth{width:130px;}
.gokeiKinWidth{width:100px;}
.zentouSeisyuKinWidth{width:100px;}
.zentouKinWidth{width:90px;}
.percentWidth{width:50px;}

/** 明細部 */
table.list td {
	white-space: nowrap;
	font-size:9.5pt;
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
	<kit:form action="/jisekiCategoryList_Search" styleClass="searchForm">
		<%-- タイトル開始行 --%>
		<div class="title" style = "width:90%;"><bean:message key="com.title.categoryJiseki" /></div>
		<%-- タイトル終了 --%>
		<kit:hidden name="jisekiCategoryList_Search" property="editable" />
		<kit:hidden name="jisekiCategoryList_Search" property="reqType" />
		<table class="form" style="width:75%;margin: 40px 1px 5px 1px;">
			<%-- 検索条件 --%>
			<tr>
				<td>
				<%-- 日付 --%>
				<label for="shiteibiLbl"><bean:message key="com.text.dt" /></label>
				<kit:text styleId="shiteibiLbl"
					name="jisekiCategoryList_Search"
					styleClass="text ime_off left date frm_dt must" property="shiteibi"
					maxlength="8" size="13" tabindex="1"
					onkeyup="nextTabMaxLenInput(8)" onblur="ctrlInput()"
					onfocus="ctrlInput()" format="yyyy/mm/dd" />
				</td>
			</tr>
			<tr><td><br/><td></tr>
			<tr>
				<%-- 表示単位 --%>
				<td><bean:message key="com.text.hyojitani" /></td>
			</tr>
			<tr>
				<td>&nbsp;
					<kit:radio styleClass="radio" name="jisekiCategoryList_Search" property="hyoujiTanyi" tabindex="2" value="1"
					styleId="kingakuLbl"/>
					<label for="kingakuLbl" ACCESSKEY=""><bean:message key="com.text.kingakuEn" /></label>
				</td>
			</tr>
			<tr>
				<td>&nbsp;
					<kit:radio styleClass="radio" name="jisekiCategoryList_Search" property="hyoujiTanyi" tabindex="3" value="2"
					styleId="lSuLbl"/>
					<label for="lSuLbl" ACCESSKEY=""><bean:message key="com.text.lsu" /></label>
				</td>
			</tr>
			<tr><td><br/><td></tr>
			<tr>
				<%-- 集計期間 --%>
				<td><bean:message key="com.text.syukeiKikan" /></td>
			</tr>
			<tr>
				<td>&nbsp;
					<kit:radio styleClass="radio" name="jisekiCategoryList_Search" property="syukeiKikan" tabindex="4" value="0"
					styleId="togetsuLbl"/>
					<label for="togetsuLbl" ACCESSKEY=""><bean:message key="com.text.touGetsu" /></label>
				</td>
			</tr>
			<tr>
				<td>&nbsp;
					<kit:radio styleClass="radio" name="jisekiCategoryList_Search" property="syukeiKikan" tabindex="5" value="1"
					styleId="jyugetsuruikeiLbl"/>
					<label for="jyugetsuruikeiLbl" ACCESSKEY=""><bean:message key="com.text.jyugetsuRuikei" /></label>
				</td>
			</tr>
			<tr>
				<td>&nbsp;
					<kit:radio styleClass="radio" name="jisekiCategoryList_Search" property="syukeiKikan" tabindex="6" value="2"
					styleId="ichigetsuruikeiLbl"/>
					<label for="ichigetsuruikeiLbl" ACCESSKEY=""><bean:message key="com.text.ichigetsuRuikei" /></label>
				</td>
			</tr>
			<tr><td><br/><td></tr>
			<tr>
				<td >
					<div class="buttons" style="clear: both; float: left; margin: 13px;">
						<%-- 呼出ボタン --%>
						&nbsp;<kit:button styleClass="btn_large" property="btnSearch"
							tabindex="7" onclick="if(eventPreSubmit()){setReqType('search');offUnloadConfirm();submit()}">
							<bean:message key="com.button.search" />
						</kit:button>
					</div>
				</td>
			</tr>
			<tr><td><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/></td></tr>
			<tr>
				<%-- 検索日時 --%>
				<td><bean:message key="com.text.kensakuNiji" /></td>
			</tr>
			<tr>
				<td>&nbsp;
					<kit:write name="jisekiCategoryList_Search" property="kensakuNiji" ignore="true" />
				</td>
			</tr>
		</table>
	</kit:form>
	<%-- 検索条件入力フォーム終了 --%>
	</div>
	</td>
	<td valign="top">
	<%-- リスト開始 --%>
	<div class="wrapper">
	<div id="list" class="fixedTitleX" style = "margin-left:3px;">
		<%-- タイトル行 --%>
		<table id="listTable" class="fixedTitleTable list" style="margin-bottom:0px;">
			<colgroup class="areaNmWidth"></colgroup>
			<colgroup class="gokeiKinWidth"></colgroup>
			<colgroup class="gokeiKinWidth"></colgroup>
			<colgroup class="gokeiKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouSeisyuKinWidth"></colgroup>
			<colgroup class="zentouSeisyuKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<thead>
				<tr class="head">
					<th rowspan=2><bean:message key="com.text.areaNm" /></th>
					<th colspan=5><bean:message key="com.text.goukei" /></th>
					<th colspan=3><bean:message key="com.text.seisyu" /></th>
					<th colspan=3><bean:message key="com.text.birusyu" /></th>
					<th colspan=3><bean:message key="com.text.syucyu" /></th>
					<th colspan=3><bean:message key="com.text.rikyuru" /></th>
					<th colspan=3><bean:message key="com.text.cyomiryo" /></th>
					<th colspan=3><bean:message key="com.text.fusui" /></th>
					<th colspan=3><bean:message key="com.text.kesyohin" /></th>
					<th colspan=3><bean:message key="com.text.sonota" /></th>
				</tr>
				<tr class="head">
					<%-- 前期--%>
					<th><bean:message key="com.text.zennki" /></th>
					<%-- 前期同日--%>
					<th><bean:message key="com.text.zenkiDoji" /></th>
					<%-- 当期 --%>
					<th><bean:message key="com.text.touki" /></th>
					<%-- 同日比 --%>
					<th><bean:message key="com.text.doujitsuhi" /></th>
					<%-- 進捗率 --%>
					<th><bean:message key="com.text.statusRt" /></th>
					<%-- 当期 --%>
					<th><bean:message key="com.text.touki" /></th>
					<%-- 前期 --%>
					<th><bean:message key="com.text.zennki" /></th>
					<%-- 対比 --%>
					<th><bean:message key="com.text.Taihi" /></th>
					<%-- 当期 --%>
					<th><bean:message key="com.text.touki" /></th>
					<%-- 前期 --%>
					<th><bean:message key="com.text.zennki" /></th>
					<%-- 対比 --%>
					<th><bean:message key="com.text.Taihi" /></th>
					<%-- 当期 --%>
					<th><bean:message key="com.text.touki" /></th>
					<%-- 前期 --%>
					<th><bean:message key="com.text.zennki" /></th>
					<%-- 対比 --%>
					<th><bean:message key="com.text.Taihi" /></th>
					<%-- 当期 --%>
					<th><bean:message key="com.text.touki" /></th>
					<%-- 前期 --%>
					<th><bean:message key="com.text.zennki" /></th>
					<%-- 対比 --%>
					<th><bean:message key="com.text.Taihi" /></th>
					<%-- 当期 --%>
					<th><bean:message key="com.text.touki" /></th>
					<%-- 前期 --%>
					<th><bean:message key="com.text.zennki" /></th>
					<%-- 対比 --%>
					<th><bean:message key="com.text.Taihi" /></th>
					<%-- 当期 --%>
					<th><bean:message key="com.text.touki" /></th>
					<%-- 前期 --%>
					<th><bean:message key="com.text.zennki" /></th>
					<%-- 対比 --%>
					<th><bean:message key="com.text.Taihi" /></th>
					<%-- 当期 --%>
					<th><bean:message key="com.text.touki" /></th>
					<%-- 前期 --%>
					<th><bean:message key="com.text.zennki" /></th>
					<%-- 対比 --%>
					<th><bean:message key="com.text.Taihi" /></th>
					<%-- 当期 --%>
					<th><bean:message key="com.text.touki" /></th>
					<%-- 前期 --%>
					<th><bean:message key="com.text.zennki" /></th>
					<%-- 対比 --%>
					<th><bean:message key="com.text.Taihi" /></th>
				</tr>
			</thead>
		</table>

		<%-- 表領域開始--%>
		<div id="details" class="">
		<table id="detailsTable" class="fixedTitleTable list" style="margin-top:-1pt">
			<colgroup class="areaNmWidth"></colgroup>
			<colgroup class="gokeiKinWidth"></colgroup>
			<colgroup class="gokeiKinWidth"></colgroup>
			<colgroup class="gokeiKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouSeisyuKinWidth"></colgroup>
			<colgroup class="zentouSeisyuKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="zentouKinWidth"></colgroup>
			<colgroup class="percentWidth"></colgroup>
		<%-- レコード行 --%>
		<%-- 事業所レベルのデータ --%>
		<kit:iterate id="jisekiCategoryRecord" name="jisekiCategoryJigyosyo_SearchList"
			 indexId="idx" tabStartIndex="1" scope="session">
		<kit:tr name="jisekiCategoryRecord" id="id" evenClass="even" oddClass="odd" indexed="true" styleClass="line"
			onmouseover="setRowMouseOver(this)" onmouseout="setRowMouseOut(this)">
			<%-- エリア名 --%>
			<td class="cdxView">
				<kit:write name="jisekiCategoryRecord" property="ariaNm" ignore="true"/>
			</td>
			<%-- 前期--%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="zenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期同日--%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="zenkiDoji" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 当期 --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="toki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 同日比 --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="dojitsuhi" ignore="true" />
			</td>
			<%-- 進捗率 --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="shincyoku" ignore="true" />
			</td>
			<%-- 当期(清酒) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="seisyuToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(清酒) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="seisyuZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(清酒) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="seisyuTaihi" ignore="true" />
			</td>
			<%-- 当期(麦酒・発泡酒) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="biruToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(麦酒・発泡酒) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="biruZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(麦酒・発泡酒) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="biruTaihi" ignore="true" />
			</td>
			<%-- 当期(焼酎) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="syocyuToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(焼酎) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="syocyuZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(焼酎) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="syocyuTaihi" ignore="true" />
			</td>
			<%-- 当期(リキュール) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="rikyuruToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(リキュール) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="rikyuruZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(リキュール) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="rikyuruTaihi" ignore="true" />
			</td>
			<%-- 当期(調味料) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="cyomiryoToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(調味料) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="cyomiryoZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(調味料) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="cyomiryoTaihi" ignore="true" />
			</td>
			<%-- 当期(伏水・サイファー) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="fusuiToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(伏水・サイファー) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="fusuiZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(伏水・サイファー) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="fusuiTaihi" ignore="true" />
			</td>
			<%-- 当期(化粧品) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="kesyohinToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(化粧品) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="kesyohinZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(化粧品) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="kesyohinTaihi" ignore="true" />
			</td>
			<%-- 当期(その他) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="sonohokaToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(その他) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="sonohokaZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(その他) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="sonohokaTaihi" ignore="true" />
			</td>
		</kit:tr>
		</kit:iterate>

		<kit:present name="jisekiCategoryKa_SearchList">
		<tr><td colspan = "30"></td></tr>
		<%-- レコード行 --%>
		<%-- 課レベルのデータ --%>
		<kit:iterate id="jisekiCategoryRecord" name="jisekiCategoryKa_SearchList"
			 indexId="idx" tabStartIndex="1" scope="session">
		<kit:tr name="jisekiCategoryRecord" id="id" evenClass="even" oddClass="odd" indexed="true" styleClass="line"
			onmouseover="setRowMouseOver(this)" onmouseout="setRowMouseOut(this)">
			<%-- エリア名 --%>
			<td class="cdxView">
				<kit:write name="jisekiCategoryRecord" property="ariaNm" ignore="true"/>
			</td>
			<%-- 前期--%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="zenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期同日--%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="zenkiDoji" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 当期 --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="toki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 同日比 --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="dojitsuhi" ignore="true" />
			</td>
			<%-- 進捗率 --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="shincyoku" ignore="true" />
			</td>
			<%-- 当期(清酒) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="seisyuToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(清酒) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="seisyuZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(清酒) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="seisyuTaihi" ignore="true" />
			</td>
			<%-- 当期(麦酒・発泡酒) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="biruToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(麦酒・発泡酒) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="biruZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(麦酒・発泡酒) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="biruTaihi" ignore="true" />
			</td>
			<%-- 当期(焼酎) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="syocyuToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(焼酎) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="syocyuZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(焼酎) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="syocyuTaihi" ignore="true" />
			</td>
			<%-- 当期(リキュール) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="rikyuruToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(リキュール) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="rikyuruZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(リキュール) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="rikyuruTaihi" ignore="true" />
			</td>
			<%-- 当期(調味料) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="cyomiryoToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(調味料) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="cyomiryoZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(調味料) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="cyomiryoTaihi" ignore="true" />
			</td>
			<%-- 当期(伏水・サイファー) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="fusuiToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(伏水・サイファー) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="fusuiZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(伏水・サイファー) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="fusuiTaihi" ignore="true" />
			</td>
			<%-- 当期(化粧品) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="kesyohinToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(化粧品) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="kesyohinZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(化粧品) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="kesyohinTaihi" ignore="true" />
			</td>
			<%-- 当期(その他) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="sonohokaToki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 前期(その他) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="sonohokaZenki" ignore="true" format="99,999,999,999"/>
			</td>
			<%-- 対比(その他) --%>
			<td class="nmView">
				<kit:write name="jisekiCategoryRecord" property="sonohokaTaihi" ignore="true" />
			</td>
		</kit:tr>
		</kit:iterate>
		</kit:present>

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