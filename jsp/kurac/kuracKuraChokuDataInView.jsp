<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page buffer="256kb" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-kit.tld" prefix="kit"%>
<kit:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<meta http-equiv="Content-Script-Type" content="text/javascript">
<title><bean:message key="com.title.kuracKuraChokuDataIn" /><kit:write name="kuracKuraChokuDataIn_Edit" property="modeNm" scope="request" ignore="true" /></title>
<link rel="stylesheet" href="./css/style.css" type="text/css">
<link rel="stylesheet" href="./css/styleKz.css" type="text/css">
<link rel="stylesheet" href="./css/styleKzView.css" type="text/css">
<link rel="stylesheet" href="./css/print.css" type="text/css" media="print">

<script src="./js/pbsControl.js"></script>
<script src="./js/comControl.js"></script>
<script src="./js/comMaster.js"></script>
<script src="./js/kzControl.js"></script>

<script type="text/javascript">

	function setHomePosition() {
		// ホームオブジェクトを指定
		//HOME_OBJECT = document.kuracKuraChokuDataIn_Edit.elements["kuracKuraChokuDataInRecord[0].senpoHacyuNo"];
	}

	// 画面固有の明細部以外領域の高さ指定
	var othersHeight = 235;

	// ヘッダ固定
	//@@@ タイトル固定呪文 ここから @@@@@@@@@@@@@@
	window.onresize = localResize;
	function localResize() {
		resize();
		//setLocalHeight("list" ,"details" ,45);
	}
	//@@@ タイトル固定呪文 ここまで @@@@@@@@@@@@@@

	// 抹消・復活サブミット前の確認
	function confirmNext(obj) {
		var msg = "";
		if (obj.name == "btnDelete") {
			msg = "<bean:message key='warn.confirm.delete' />\n";
		} else if (obj.name == "btnRebirth") {
			msg = "<bean:message key='warn.confirm.rebirth' />\n";
		} else {
			return false;
		}

		var ret = confirm(msg);
		if (ret == true) {
			return true; // 処理を続ける
		} else {
			return false; // やめる
		}
	}
</script>

<style type="text/css">
<!--
/** 検索部 */
table.form {
	height: 30px;
	width: 100%;
}

table.form td {
	white-space: nowrap;
}

/* 見栄え調整用 */
table.form label.layout {
	margin-left: 1.5em;
}

/* @@@ タイトル固定呪文 ここから @@@@@@@@@@@@@@@@@@@@@@@@*/
.wrapper {
	/* 左詰めにする表全体*/
	width: 100%;
	text-align: left;
}

#listH{float:left;margin-right:5em;margin-bottom:1em;}
#listL{float:left;margin-top:1em;margin-right:5em;margin-bottom:1em;}
#listT{margin-top:1em}

#listSelect{float:left; width:1260px}
.listLH{float:left;height:200px;}
.listS{float:left;height:450px;}
.fixedTitleTableSelect{width:1260px;}
.fixedTitleTableConfirm{width:900px;}

/* 一覧表レイアウト */
.noWidth{width:30px;}
.syohinCdWidth{width:100px;}
.syohinNmWidth{width:350px;}
.yokiNmWidth{width:90px;}
.setSuWidth{width:80px;}
.hannbaitankaWidth{width:110px;}
.hannbaigakuWidth{width:140px;}
.syukadenNoWidth{width:120px;}
.teiseiUridenNoWidth{width:120px;}
.teiseiSyukaDtWidth{width:120px;}

.doWrap { word-wrap: break-word; } /* 折返し指定 */
/* 折返し表示項目（上記幅-10px） */
.syohinNmWidthWrap{width:340px;}


.jheader {border-collapse:collapse; font-size:11pt; border:2px solid #778899;}
.jdetail {border-collapse:collapse; font-size:11pt; border:2px solid #778899;}
.jitem {border-collapse:collapse; font-size:11pt; border:2px solid #778899; background-color:#F0FFF0;}
.jdata {border-collapse:collapse; font-size:11pt; border:2px solid #778899; background-color:#FFFFFF;}

.thMWidth {width: 120px;}
.thSWidth {width: 90px;}
.thXSWidth {width: 60px;}
.thXLWidth {width: 180px;}
.thLWidth {width: 150px;}


.tx4emWidth {width: 4em;}
.tx5emWidth {width: 5em;}
.tx8emWidth {width: 8em;}
.tx10emWidth {width: 10em;}
.tx12emWidth {width: 12em;}
.tx14emWidth {width: 14em;}
.tx20emWidth {width: 20em;}
.tx28emWidth {width: 28em;}
.tx30emWidth {width: 20em;}
.tx42emWidth {width: 42em;}

TD.error {
	background-color:#FF7F50;
	border:2px solid #778899;
}
TD.modified {
	background-color:#A0D8EF;
	border:2px solid #778899;
}

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

div.tabLink li {
	margin-top: 5px;
	float: left;
	display: block;
	font-size: 1.2em;
	white-space: nowrap;
}

.floatClear {
	clear: both;
}

.floatRight {
	float: right;
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
	"
	onbeforeunload="return eventUnloadMain()"
	onhelp="return false;">
	<%-- 共通ヘッダー開始 --%>
	<%@ include file="/parts/comHeader.jsp" %>
	<%-- 共通ヘッダー終了 --%>
	<%-- 検索条件入力フォーム開始 --%>
	<kit:form action="/kuracKuraChokuDataIn_Search.do" styleClass="searchForm">
		<%-- タイトル開始 --%>
		<div class="title">
			<bean:message key="com.title.kuracKuraChokuDataIn" />
			<kit:write name="kuracKuraChokuDataIn_Edit" property="modeNm" scope="request" ignore="true" />
		</div>
		<%-- タイトル終了 --%>

		<kit:hidden name="kuracKuraChokuDataIn_Search" property="editable" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="reqType" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="sortItem" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="sortOrder" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="sortFlag" />

		<kit:hidden name="kuracKuraChokuDataIn_Search" property="riyouKbn" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="syubetuCd" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="makerNo" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="sounisakiNm" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="syuhantenNm" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="irainusi" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="mousikomibi" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="jigyousyo" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="shohinGrp" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="syuhantenCd" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="tdksk" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="hasoyoteibi" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="miJyucyuOnly" />

		<table class="form">
			<tr>
				<td class="searchFormButtonRow">
					<div class="right">

					<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="selected">

						<%-- 修正ボタン --%>
						<kit:notEqual name="kuracKuraChokuDataIn_Edit" property="deletedRecord" value="true">
							<kit:button styleClass="btn_large" property="btnEdit"
								tabindex="1" key="editBtnAvailFlg"
								kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
								onclick="if(eventPreSubmit()){setReqType('edit');offUnloadConfirm();submit()}">
								<bean:message key="com.button.change" />
							</kit:button>
						</kit:notEqual>

						<%-- 参照追加ボタン --%>
						<kit:button styleClass="btn_large" property="btnReference"
							tabindex="2" key="referrerBtnAvailFlg"
							kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
							onclick="if(eventPreSubmit()){setReqType('reference');offUnloadConfirm();submit()}">
							<bean:message key="com.button.reference" />
						</kit:button>

						<%-- 売上訂正(+) --%>
						<kit:button styleClass="btn_large" property="btnRefPlus"
							tabindex="3" key="refplusBtnAvailFlg"
							kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
							onclick="if(eventPreSubmit()){setReqType('refPlus');offUnloadConfirm();submit()}">
							<bean:message key="com.button.uriagerefPlus" />
						</kit:button>

						<%-- 売上訂正(-) --%>
						<kit:button styleClass="btn_large" property="btnRefMinus"
							tabindex="4" key="refminusBtnAvailFlg"
							kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
							onclick="if(eventPreSubmit()){setReqType('refMinus');offUnloadConfirm();submit()}">
							<bean:message key="com.button.uriagerefMinus" />
						</kit:button>

						<%-- 抹消ボタン --%>
 							<kit:button styleClass="btn_large" property="btnDelete"
								tabindex="5" key="deleteBtnAvailFlg" kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
								onclick="if(confirmNext(this)){if(eventPreSubmit()){setReqType('delete');offUnloadConfirm();submit()}}">
								<bean:message key="com.button.delete" />
							</kit:button>
					</kit:equal>
					<kit:notEqual name="kuracKuraChokuDataIn_Edit" property="mode" value="selected">
						<div style="height:23px;"></div>
					</kit:notEqual>
					</div>
				</td>
			</tr>
		</table>
	</kit:form>
	<%-- 検索条件入力フォーム終了 --%>
	<hr />
	<%-- 登録・更新・削除フォーム開始 --%>
	<kit:form action="/kuracKuraChokuDataIn_Edit.do" styleId="editForm">
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="editable" />
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="reqType" />
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="mode" />
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="preMode" />
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="maxSu" />
		<%-- FIXME:追加時に行数調整するための変数 --%>
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="maxLineSu" />
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="maxLineSu1" />
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="selectedRowId" />
		<kit:hidden name="kuracKuraChokuDataIn_EditList" property="hasPrevPage" defaultValue="false" />
		<kit:hidden name="kuracKuraChokuDataIn_EditList" property="hasNextPage" defaultValue="false" />
	<div id="detail" class="fixedTitleY floatClear listS">
		<%-- ヘッダー部 --%>
		<kit:iterate id="kuracKuraChokuDataInRecord" name="kuracKuraChokuDataIn_EditList" property="pageList" indexId="idx" tabStartIndex="1" scope="session">
			<div class="list_top">

			<table class="jheader">
					<tr>
						<%-- 利用区分 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.riyouKbn" /></th>
						<td class="mjView jdata tx10emWidth" >
							<kit:write name="kuracKuraChokuDataInRecord" property="riyouKbn" ignore="true"
							           decodeList="kuracKuraChokuDataIn_RiyouKbnList" decodeWrite ="label"/>
						</td>

						<%-- 更新日 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.kousinDate" /></th>
						<td class="dtView jdata tx10emWidth" >
							<kit:write name="kuracKuraChokuDataInRecord" property="kousinNitiziDt" ignore="true" format="yyyy/mm/dd"/>
						</td>

						<%-- 更新者 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.kousinSgyosya" /></th>
						<td class="mjView jdata tx10emWidth" >
							<kit:write name="kuracKuraChokuDataInRecord" property="sgyosyaNm" ignore="true"/>
						</td>

					</tr>
				</table>
				<br>

				<div id = "listH">
				<table class="jheader ">
					<tr>
					<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="selected">
						<%-- メーカーNo --%>
						<th class="jitem thMWidth"><bean:message key="com.text.makerNo" /></th>
						<td class="cdxView jdata tx10emWidth">
							<kit:write name="kuracKuraChokuDataInRecord" property="makerNo" ignore="true" />
						</td>
					</kit:equal>
					<kit:notEqual name="kuracKuraChokuDataIn_Edit" property="mode" value="selected">
						<%-- 事業所区分 --%>
						<th class="jitem thMWidth"><bean:message key="com.cdnm.zigyousyoKb" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="jigyosyoKbnClass"
								styleClass="stView jdata tx10emWidth">
						<%-- ▼参照追加の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="reference">
							<kit:write name="kuracKuraChokuDataInRecord" property="jigyosyoKbnView" ignore="true"
								decodeList="kuracKuraChokuDataIn_KuracSitenList" decodeWrite="label" /><br/>
						</kit:equal>
						<%-- ▲参照追加の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="jigyosyoKbn" ignore="true"
								decodeList="kuracKuraChokuDataIn_KuracSitenList" decodeWrite="label" />
						</kit:td>
						<%-- 商品区分 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.shohinKbn" /></th>
						<td class="stView jdata tx10emWidth">
						<%-- ▼参照追加の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="reference">
								<kit:write name="kuracKuraChokuDataInRecord" property="shoninGrpCdView" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracShohingrpList" decodeWrite="label" /><br/>
						</kit:equal>
						<%-- ▲参照追加の時▲ --%>
								<kit:write name="kuracKuraChokuDataInRecord" property="shoninGrpCd" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracShohingrpList" decodeWrite="label" />
						</td>
						<%-- 届先No --%>
						<th class="jitem thMWidth"><bean:message key="com.text.tdkskNo" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="todokesakiLineNoClass"
								styleClass="stView jdata tx10emWidth">
							<%-- ▼参照追加の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="reference">
								<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiLineNoView" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracUploadFlgList" decodeWrite="label" /><br/>
							</kit:equal>
							<%-- ▲参照追加の時▲ --%>
								<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiLineNo" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracUploadFlgList" decodeWrite="label" />
						</kit:td>
					</kit:notEqual>
					</tr>
					<tr>
						<%-- 受注No --%>
						<th class="jitem thMWidth"><bean:message key="com.text.juchuNo" /></th>
						<td class="cdxView jdata">
								<kit:write name="kuracKuraChokuDataInRecord" property="jyucyuNo" ignore="true" />
						</td>
						<%-- 申込受付日 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.mousikomiDt" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="uketukeDtClass"
								styleClass="dtView jdata tx10emWidth">
							<%-- ▼修正・参照追加の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference">
								<kit:write name="kuracKuraChokuDataInRecord" property="uketukeDtView" ignore="true" format="yyyy/mm/dd"/><br/>
							</kit:equal>
							<%-- ▲修正・参照追加の時▲ --%>
								<kit:write name="kuracKuraChokuDataInRecord" property="uketukeDt" ignore="true" format="yyyy/mm/dd"/>
						</kit:td>
						<%-- 宅伝区分 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.takuhaiBillKbn" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="takuhaiBillKbnClass"
								styleClass="stView jdata tx10emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="takuhaiBillKbnView" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracBillList" decodeWrite="label" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:write name="kuracKuraChokuDataInRecord" property="takuhaiBillKbn" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracBillList" decodeWrite="label" />
						</kit:td>
					</tr>
					<tr>
						<%-- 発注No --%>
						<th class="jitem thMWidth"><bean:message key="com.text.hacyuNo" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="hacyuNoClass"
								styleClass="cdxView jdata">
							<%-- ▼修正・参照追加の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference">
								<kit:write name="kuracKuraChokuDataInRecord" property="hacyuNoView" ignore="true"/><br/>
							</kit:equal>
							<%-- ▲修正・参照追加の時▲ --%>
								<kit:write name="kuracKuraChokuDataInRecord" property="hacyuNo" ignore="true"/>
						</kit:td>
						<%--  発送予定日 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.hasoyoteibi" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="hasoYoteiDtClass"
								styleClass="dtView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="hasoYoteiDtView" ignore="true" format="yyyy/mm/dd"/><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:write name="kuracKuraChokuDataInRecord" property="hasoYoteiDt" ignore="true" format="yyyy/mm/dd"/>
						</kit:td>
						<%--  酒販店チェックリスト --%>
						<th class="jitem thMWidth"><bean:message key="com.text.syuhantenChklst" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="checkListPrtKbnClass"
								styleClass="stView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="checkListPrtKbnView" ignore="true"
								decodeList="kuracKuraChokuDataIn_KuracChklistYnList" decodeWrite="label" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="checkListPrtKbn" ignore="true"
								decodeList="kuracKuraChokuDataIn_KuracChklistYnList" decodeWrite="label" />
						</kit:td>
					</tr>
					<tr>
						<kit:notEqual name="kuracKuraChokuDataIn_Edit" property="mode" value="selected">
						<%--  整理No --%>
						<th class="jitem thMWidth"><bean:message key="com.text.kuradenNo" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="kuradenNoClass"
								styleClass="cdxView jdata">
						<%-- ▼参照追加の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="reference">
							<kit:write name="kuracKuraChokuDataInRecord" property="kuradenNoView" ignore="true"/><br/>
						</kit:equal>
						<%-- ▲参照追加の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="kuradenNo" ignore="true"/>
						</kit:td>
						</kit:notEqual>
						<%--  運賃区分 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.unchinKbn" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="unchinKbnClass"
								styleClass="stView jdata">
							<%-- ▼修正・参照追加の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference">
								<kit:write name="kuracKuraChokuDataInRecord" property="unchinKbnView" ignore="true"
									decodeList="kuracKuraChokuDataIn_UntinList" decodeWrite="label" /><br/>
								</kit:equal>
							<%-- ▲修正・参照追加の時▲ --%>
								<kit:write name="kuracKuraChokuDataInRecord" property="unchinKbn" ignore="true"
									decodeList="kuracKuraChokuDataIn_UntinList" decodeWrite="label" />
						</kit:td>
						<%--  用途区分 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.youtoKbn" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="youtoKbnClass"
								styleClass="stView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="youtoKbnView" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracNosiYoutoList" decodeWrite="label" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:write name="kuracKuraChokuDataInRecord" property="youtoKbn" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracNosiYoutoList" decodeWrite="label" />
						</kit:td>
					</tr>
				</table>
				</div>

				<div id="listM" >
				<table class="jheader">
					<tr>
					<%-- のし名前記載 --%>
					<th class="jitem thMWidth"><bean:message key="com.text.nosikisai"/></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="nosikbnClass"
								styleClass="stView jdata tx8emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
						<kit:write name="kuracKuraChokuDataInRecord" property="nosiKbnView" ignore="true"
							decodeList="kuracKuraChokuDataIn_KuracNosiNameYnList" decodeWrite="label" /><br/>
							</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:write name="kuracKuraChokuDataInRecord" property="nosiKbn" ignore="true"
							decodeList="kuracKuraChokuDataIn_KuracNosiNameYnList" decodeWrite="label" />
					</kit:td>
					</tr>
					<tr>
						<%-- 記載位置(左) --%>
						<th class="jitem"><bean:message key="com.text.kinoichiLeft"/></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="nosiComment1Class"
									styleClass="mjView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="nosiComment1View" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:write name="kuracKuraChokuDataInRecord" property="nosiComment1" ignore="true" /><br/>
						</kit:td>
					</tr>
					<tr>
						<%-- 記載位置(中) --%>
						<th class="jitem"><bean:message key="com.text.kinoichiMiddle"/></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="nosiComment2Class"
									styleClass="mjView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="nosiComment2View" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:write name="kuracKuraChokuDataInRecord" property="nosiComment2" ignore="true" /><br/>
						</kit:td>
					</tr>
					<tr>
						<%-- 記載位置(右) --%>
						<th class="jitem"><bean:message key="com.text.kinoichiRight"/></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="nosiComment3Class"
									styleClass="mjView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="nosiComment3View" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:write name="kuracKuraChokuDataInRecord" property="nosiComment3" ignore="true" /><br/>
						</kit:td>
					</tr>
				</table>
				</div>

				<br/>
				<%-- ★縦線情報１段目★ --%>
				<table class="jheader">
					<tr>
						<%--  縦線CD --%>
						<th class="jitem thXSWidth"><bean:message key="com.text.tatesenCd" /></th>
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
							<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"
									styleClass="cdnView jdata tx5emWidth">
									<kit:write name="kuracKuraChokuDataInRecord" property="tatesnCdView" ignore="true" />
							</kit:td>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"
								styleClass="cdnView jdata tx5emWidth">
								<kit:write name="kuracKuraChokuDataInRecord" property="tatesnCd" ignore="true"/>
						</kit:td>
					</tr>
				</table>
				<%-- ★縦線情報２段目★ --%>
				<table class="jheader" style="margin-top:-1px;">
					<tr>
						<%-- 特約店 --%>
						<th class="jitem thXSWidth"><bean:message key="com.text.tokuyakuten" /></th>
						<%-- 特約店CD --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass" styleClass="cdnView jdata tx5emWidth">
							<%-- ▼修正・参照追加・訂正の時の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="tokuyakutenCdView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="tokuyakutenCd" ignore="true"/>
						</kit:td>
						<%-- 特約店名(略) --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"  styleClass="mjView jdata tx12emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="tokuyakutenNmView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="tokuyakutenNm" ignore="true"/>
						</kit:td>
						<%-- デポ店 --%>
						<th class="jitem thXSWidth"><bean:message key="com.text.depoten" /></th>
						<%-- デポ店CD --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass" styleClass="cdnView jdata tx5emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="depoCdView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="depoCd" ignore="true"/>
						</kit:td>
						<%-- デポ店名(略) --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"  styleClass="mjView jdata tx12emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="depoNmView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="depoNm" ignore="true"/>
						</kit:td>
						<%-- 二次店 --%>
						<th class="jitem thXSWidth"><bean:message key="com.text.2jiten" /></th>
						<%-- 二次店CD --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"  styleClass="cdnView jdata tx5emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="nijitenCdView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="nijitenCd" ignore="true"/>
						</kit:td>
						<%-- 二次店(略) --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"  styleClass="mjView jdata tx12emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="nijitenNmView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="nijitenNm" ignore="true"/>
						</kit:td>
						<%--三次店 --%>
						<th class="jitem thXSWidth"><bean:message key="com.text.3jiten" /></th>
						<%-- 三次店CD --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"  styleClass="cdnView jdata tx5emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="sanjitenCdView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="sanjitenCd" ignore="true"/>
						</kit:td>
						<%-- 三次店名(略) --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"  styleClass="mjView jdata tx12emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="sanjitenNmView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="sanjitenNm" ignore="true"/>
						</kit:td>
					</tr>
				</table>

			<div id="listL" >
				<%-- ★酒販店情報１段目★ --%>
				<table class="jheader">
					<tr>
						<%-- 酒販店CD --%>
						<th class="jitem thSWidth"><bean:message key="com.text.syuhantenCd" /></th>
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
							<kit:td name="kuracKuraChokuDataInRecord" property="syuhantenCdClass"
									styleClass="cdnView jdata tx5emWidth">
								<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenCdView" ignore="true" />
							</kit:td>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="syuhantenCdClass"
									styleClass="cdnView jdata tx5emWidth">
							<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenCd" ignore="true"/>
						</kit:td>
					</tr>
				</table>
				<%-- ★酒販店情報２,３段目★ --%>
				<table class="jheader" style="margin-top:-0.1em;">
					<tr>
						<%-- 店名 --%>
						<th class="jitem thSWidth"><bean:message key="com.text.tenNm" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="syuhantenCdClass" styleClass="mjView jdata tx30emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenNmView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenNm" ignore="true"/><br/>
						</kit:td>
						<%-- TEL --%>
						<th class="jitem thSWidth"><bean:message key="com.text.tel" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="syuhantenCdClass" styleClass="mjView jdata tx14emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenTelView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenTel" ignore="true"/><br>
						</kit:td>
					</tr>
					<tr>
						<%-- 住所 --%>
						<th class="jitem thSWidth"><bean:message key="com.text.jyusyo" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="syuhantenCdClass" styleClass="mjView jdata ">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenAddressView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenAddress" ignore="true"/><br>
						</kit:td>
						<%-- 郵便番号 --%>
						<th class="jitem thSWidth"><bean:message key="com.text.postalCd" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="syuhantenCdClass" styleClass="mjView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenZipView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenZip" ignore="true"/><br>
						</kit:td>
					</tr>
				</table>
			</div>

			<div id="listT">
				<%-- ★運送店情報1段目★ --%>
				<table class="jheader">
					<tr>
						<%-- 運送店CD --%>
						<th class="jitem thSWidth"><bean:message key="com.text.unsotenCd" /></th>
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
							<kit:td name="kuracKuraChokuDataInRecord" property="unsotenCdClass"
									styleClass="cdnView jdata tx4emWidth">
								<kit:write name="kuracKuraChokuDataInRecord" property="unsotenCdView" ignore="true" />
							</kit:td>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="unsotenCdClass"
								styleClass="cdnView jdata tx4emWidth">
							<kit:write name="kuracKuraChokuDataInRecord" property="unsotenCd" ignore="true"/>
						</kit:td>
					</tr>
				</table>
				<%-- ★運送店情報2段目★ --%>
				<table class="jheader" style="margin-top:-0.1em;">
					<tr>
						<%-- 運送店名 --%>
						<th class="jitem thSWidth"><bean:message key="com.text.unsotenNm" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="unsotenCdClass" styleClass="mjView jdata tx20emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="unsotenNmView" ignore="true" /><br>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="unsotenNm" ignore="true"/>
						</kit:td>
					</tr>
				</table>
			</div>

			<br/><br/>
			<table class="jheader" style="float:left">
				<tr>
					<%-- 届け先 --%>
					<th class="jitem thSWidth" rowspan = "2"><bean:message key="com.text.tdksk" /></th>
					<%-- 氏名 --%>
					<th class="jitem thSWidth"><bean:message key="com.text.shimei" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="todokesakiNmClass" styleClass="mjView jdata tx42emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiNmView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiNm" ignore="true"/>
					</kit:td>
					<%-- TEL --%>
					<th class="jitem thSWidth"><bean:message key="com.text.tel" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="todokesakiTelClass" styleClass="mjView jdata tx14emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiTelView" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiTel" ignore="true"/>
					</kit:td>
				</tr>
				<tr>
					<%-- 住所 --%>
					<th class="jitem thSWidth"><bean:message key="com.text.jyusyo" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="todokesakiAddressClass" styleClass="mjView jdata tx42emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiAddressView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiAddress" ignore="true"/>
					</kit:td>
					<%-- 郵便番号 --%>
					<th class="jitem thSWidth"><bean:message key="com.text.postalCd" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="todokesakiZipClass" styleClass="mjView jdata tx14emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiZipView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiZip" ignore="true"/><br/>
					</kit:td>
				</tr>
				<tr>
					<%-- 依頼主 --%>
					<th class="jitem thSWidth" rowspan = "2"><bean:message key="com.text.irainusi" /></th>
					<%-- 氏名 --%>
					<th class="jitem thSWidth"><bean:message key="com.text.shimei" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="irainusiNmClass" styleClass="mjView jdata tx42emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="irainusiNmView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="irainusiNm" ignore="true"/>
					</kit:td>
					<%-- TEL --%>
					<th class="jitem thSWidth"><bean:message key="com.text.tel" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="irainusiTelClass" styleClass="mjView jdata tx14emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="irainusiTelView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="irainusiTel" ignore="true"/>
					</kit:td>
				</tr>
				<tr>
					<%-- 住所 --%>
					<th class="jitem thSWidth"><bean:message key="com.text.jyusyo" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="irainusiAddressClass" styleClass="mjView jdata tx42emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="irainusiAddressView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="irainusiAddress" ignore="true"/>
					</kit:td>
					<%-- 郵便番号 --%>
					<th class="jitem thSWidth"><bean:message key="com.text.postalCd" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="irainusiZipClass" styleClass="mjView jdata tx14emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="irainusiZipView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:write name="kuracKuraChokuDataInRecord" property="irainusiZip" ignore="true"/><br/>
					</kit:td>
				</tr>
				</table>

			</div>


			<hr style="border:0px; margin:0px;">

			<%-- レコードのhidden要素（ヘッダー部） --%>
			<%-- エラー時フォーカス設定用hidden要素（ヘッダー部） --%>
			<%-- ここまで エラー時フォーカス設定用 --%>

		</kit:iterate>
		<br/>
		<%-- ディテール部 --%>
		<div class="list_top">
		<%-- ▼閲覧の時▼ --%>
		<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="selected">
		<div id="listSelect" class="fixedTitleX" >
			<table class="fixedTitleTableSelect list" style="margin:0;">
				<colgroup class="noWidth"></colgroup> <%-- No --%>
				<colgroup class="syohinCdWidth"></colgroup> <%-- 商品CD --%>
				<colgroup class="syohinNmWidth"></colgroup> <%-- 商品名 --%>
				<colgroup class="yokiNmWidth"></colgroup> <%-- 容器名 --%>
				<colgroup class="setSuWidth"></colgroup> <%-- セット数 --%>
				<colgroup class="hannbaitankaWidth"></colgroup> <%-- 販売単価 --%>
				<colgroup class="hannbaigakuWidth"></colgroup> <%-- 販売額 --%>
				<colgroup class="syukadenNoWidth"></colgroup> <%-- 出荷伝票No --%>
				<colgroup class="teiseiUridenNoWidth"></colgroup> <%-- 訂正元伝票No --%>
				<colgroup class="teiseiSyukaDtWidth"></colgroup> <%-- 訂正元伝日付 --%>
				<thead>
					<tr class="head">
						<td><bean:message key="com.text.no" /></td>
						<td><bean:message key="com.text.syohinCd" /></td>
						<td><bean:message key="com.text.syohnNm" /></td>
						<td><bean:message key="com.text.youryoNm" /></td>
						<td><bean:message key="com.text.setSu" /></td>
						<td><bean:message key="com.text.hanbaiTanka" /></td>
						<td><bean:message key="com.text.syukaHanbaiKingaku" /></td>
						<td><bean:message key="com.text.syukaDenNo" /></td>
						<td><bean:message key="com.text.teiseimotoDenNo" /></td>
						<td><bean:message key="com.text.teiseiMotoDenDate" /></td>
					</tr>
				</thead>
			</table>
		</div>
		<div id="details" class="fixedTitleY listLH">
			<table class="fixedTitleTableSelect list" style="margin-top:-1px;">
				<colgroup class="noWidth"></colgroup> <%-- No --%>
				<colgroup class="syohinCdWidth"></colgroup> <%-- 商品CD --%>
				<colgroup class="syohinNmWidth"></colgroup> <%-- 商品名 --%>
				<colgroup class="yokiNmWidth"></colgroup> <%-- 容器名 --%>
				<colgroup class="setSuWidth"></colgroup> <%-- セット数 --%>
				<colgroup class="hannbaitankaWidth"></colgroup> <%-- 販売単価 --%>
				<colgroup class="hannbaigakuWidth"></colgroup> <%-- 販売額 --%>
				<colgroup class="syukadenNoWidth"></colgroup> <%-- 出荷伝票No --%>
				<colgroup class="teiseiUridenNoWidth"></colgroup> <%-- 訂正元伝票No --%>
				<colgroup class="teiseiSyukaDtWidth"></colgroup> <%-- 訂正元伝日付 --%>
			<tbody>
				<%-- リスト開始 --%>
				<kit:iterate id="kuracKuraChokuDataInDtRecord"
					name="kuracKuraChokuDataIn_EditDtList" property="pageList"
					indexId="idx" entryModeLength="maxLineSu" tabStartIndex="1" scope="session">
					<kit:tr>
						<%-- No. --%>
						<td class="nmView">
							<%= idx+1 %>
						</td>
						<%-- 商品CD --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="shohinCdClass"
							styleClass="cdnView">
							<kit:write name="kuracKuraChokuDataInDtRecord" property="shohinCd" ignore="true" />
						</kit:td>
						<%-- 商品名 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="shohinCdClass"
							styleClass="mjView syohinNmWidthWrap doWrap">
							<kit:write name="kuracKuraChokuDataInDtRecord" property="shohinNm" ignore="true" />
						</kit:td>
						<%-- 容器名 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="shohinCdClass"
							styleClass="mjView">
							<kit:write name="kuracKuraChokuDataInDtRecord" property="youkiKigoNm" ignore="true" />
						</kit:td>
						<%--  ｾｯﾄ数 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="shohinSetClass"
							styleClass="nmView">
							<kit:write name="kuracKuraChokuDataInDtRecord" property="shohinSet" ignore="true" />
						</kit:td>
						<%-- 販売単価 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="hanbaiTankaClass"
							styleClass="nmView">
							<kit:write name="kuracKuraChokuDataInDtRecord" property="hanbaiTanka" ignore="true" format="99,999,999.99"/>
						</kit:td>
						<%-- 販売額 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="hanbaiGakuClass"
							styleClass="nmView" >
							<kit:write name="kuracKuraChokuDataInDtRecord" property="hanbaiGaku" ignore="true" format="9,999,999,999.99"/>
						</kit:td>
						<%-- 出荷伝票No--%>
						<td class="cdxView">
							<kit:write name="kuracKuraChokuDataInDtRecord" property="syukadenNo" ignore="true" />
						</td>
						<%-- 訂正元伝票No --%>
						<td class="cdxView">
							<kit:write name="kuracKuraChokuDataInDtRecord" property="teiseiUridenNo" ignore="true" />
						</td>
						<%-- 訂正元伝日付 --%>
						<td class="dtView">
							<kit:write name="kuracKuraChokuDataInDtRecord" property="teiseiSyukaDt" ignore="true" format="yyyy/mm/dd"/>
						</td>
					</kit:tr>
				</kit:iterate>
				<%-- リスト終了 --%>
			</tbody>
			</table>
		</div>
		</kit:equal>
		<%-- ▲閲覧の時▲ --%>
		<%-- ▼確認の時▼ --%>
		<kit:notEqual name="kuracKuraChokuDataIn_Edit" property="mode" value="selected">
		<div id="listSelect" class="fixedTitleX" >
			<table class="fixedTitleTableSelect1 list" style="margin:0;">
			<%-- ▲訂正の時▲ --%>
				<colgroup class="noWidth"></colgroup> <%-- No --%>
				<colgroup class="syohinCdWidth"></colgroup> <%-- 商品CD --%>
				<colgroup class="syohinNmWidth"></colgroup> <%-- 商品名 --%>
				<colgroup class="yokiNmWidth"></colgroup> <%-- 容器名 --%>
				<colgroup class="setSuWidth"></colgroup> <%-- セット数 --%>
				<colgroup class="hannbaitankaWidth"></colgroup> <%-- 販売単価 --%>
				<colgroup class="hannbaigakuWidth"></colgroup> <%-- 販売額 --%>
				<%-- ▼訂正の時▼ --%>
				<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="refPlus||refMinus">
					<colgroup class="syukadenNoWidth"></colgroup> <%-- 出荷伝票No --%>
					<colgroup class="teiseiUridenNoWidth"></colgroup> <%-- 訂正元伝票No --%>
					<colgroup class="teiseiSyukaDtWidth"></colgroup> <%-- 訂正元伝日付 --%>
				</kit:equal>
				<%-- ▲訂正の時▲ --%>
				<thead>
					<tr class="head">
						<td><bean:message key="com.text.no" /></td>
						<td><bean:message key="com.text.syohinCd" /></td>
						<td><bean:message key="com.text.syohnNm" /></td>
						<td><bean:message key="com.text.youryoNm" /></td>
						<td><bean:message key="com.text.setSu" /></td>
						<td><bean:message key="com.text.hanbaiTanka" /></td>
						<td><bean:message key="com.text.syukaHanbaiKingaku" /></td>
					<%-- ▼訂正の時▼ --%>
					<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="refPlus||refMinus">
						<td><bean:message key="com.text.syukaDenNo" /></td>
						<td><bean:message key="com.text.teiseimotoDenNo" /></td>
						<td><bean:message key="com.text.teiseiMotoDenDate" /></td>
					</kit:equal>
					<%-- ▲訂正の時▲ --%>
					</tr>
				</thead>
			</table>
		</div>
		<div id="details" class="fixedTitleY listLH">
			<table class="fixedTitleTableSelect1 list" style="margin-top:-1px;">
				<colgroup class="noWidth"></colgroup> <%-- No --%>
				<colgroup class="syohinCdWidth"></colgroup> <%-- 商品CD --%>
				<colgroup class="syohinNmWidth"></colgroup> <%-- 商品名 --%>
				<colgroup class="yokiNmWidth"></colgroup> <%-- 容器名 --%>
				<colgroup class="setSuWidth"></colgroup> <%-- セット数 --%>
				<colgroup class="hannbaitankaWidth"></colgroup> <%-- 販売単価 --%>
				<colgroup class="hannbaigakuWidth"></colgroup> <%-- 販売額 --%>
				<%-- ▼訂正の時▼ --%>
				<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="refPlus||refMinus">
					<colgroup class="syukadenNoWidth"></colgroup> <%-- 出荷伝票No --%>
					<colgroup class="teiseiUridenNoWidth"></colgroup> <%-- 訂正元伝票No --%>
					<colgroup class="teiseiSyukaDtWidth"></colgroup> <%-- 訂正元伝日付 --%>
				</kit:equal>
				<%-- ▲訂正の時▲ --%>
			<tbody>
				<%-- リスト開始 --%>
				<kit:iterate id="kuracKuraChokuDataInDtRecord"
					name="kuracKuraChokuDataIn_EditDtList" property="pageList"
					indexId="idx" entryModeLength="maxLineSu" tabStartIndex="1" scope="session">
					<kit:tr>
						<%-- No. --%>
						<td class="nmView">
							<%= idx+1 %>
						</td>
						<%-- 商品CD --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="shohinCdClass"
							styleClass="cdnView">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="shohinCdView" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:write name="kuracKuraChokuDataInDtRecord" property="shohinCd" ignore="true" /><br/>
						</kit:td>
						<%-- 商品名 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="shohinCdClass"
							styleClass="mjView syohinNmWidthWrap doWrap">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="shohinNmView" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:write name="kuracKuraChokuDataInDtRecord" property="shohinNm" ignore="true" /><br/>
						</kit:td>
						<%-- 容器名 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="shohinCdClass"
							styleClass="mjView">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="youkiKigoNmView" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:write name="kuracKuraChokuDataInDtRecord" property="youkiKigoNm" ignore="true" /><br/>
						</kit:td>
						<%--  ｾｯﾄ数 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="shohinSetClass"
							styleClass="nmView">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="shohinSetView" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:write name="kuracKuraChokuDataInDtRecord" property="shohinSet" ignore="true" /><br/>
						</kit:td>
						<%-- 販売単価 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="hanbaiTankaClass"
							styleClass="nmView">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="hanbaiTankaView" ignore="true" format="99,999,999.99"/><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:write name="kuracKuraChokuDataInDtRecord" property="hanbaiTanka" ignore="true" format="99,999,999.99"/><br/>
						</kit:td>
						<%-- 販売額 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="hanbaiGakuClass"
							styleClass="nmView" >
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="hanbaiGakuView" ignore="true" format="99,999,999.99"/><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:write name="kuracKuraChokuDataInDtRecord" property="hanbaiGaku" ignore="true" format="9,999,999,999.99"/><br/>
						</kit:td>
						<%-- ▼訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="preMode" value="refPlus||refMinus">
							<%-- 出荷伝票No--%>
							<td class="cdxView">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="syukadenNoView" ignore="true" /><br/>
								<kit:write name="kuracKuraChokuDataInDtRecord" property="syukadenNo" ignore="true" /><br/>
							</td>
							<%-- 訂正元伝票No --%>
							<td class="cdxView">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="teiseiUridenNoView" ignore="true" /><br/>
								<kit:write name="kuracKuraChokuDataInDtRecord" property="teiseiUridenNo" ignore="true" /><br/>
							</td>
							<%-- 訂正元伝日付 --%>
							<td class="dtView">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="teiseiSyukaDtView" ignore="true" format="yyyy/mm/dd"/><br/>
								<kit:write name="kuracKuraChokuDataInDtRecord" property="teiseiSyukaDt" ignore="true" format="yyyy/mm/dd"/><br/>
							</td>
						</kit:equal>
					</kit:tr>
				</kit:iterate>
				<%-- リスト終了 --%>
			</tbody>
			</table>
		</div>
		</kit:notEqual>
		<%-- ▲確認の時▲ --%>
		</div>
	</div>
		<div class="list_bottom">
			<table id="footButtons">
				<tr>
					<td class="center">
						<%-- 確認ボタン --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="add||edit||reference" scope="request" >
							<kit:button styleClass="btn_large" property="btnConfirm"
								name="kuracKuraChokuDataIn_Edit" tabIndexProperty="tabIndexConfirm"
								onclick="if(eventPreSubmit()){setReqType('confirm');offUnloadConfirm();submit()}">
								<bean:message key="com.button.confirm" />
							</kit:button>
						</kit:equal>

						<%-- 確定ボタン --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="confirm" scope="request" >
							<kit:button styleClass="btn_large" property="btnCommit"
								name="kuracKuraChokuDataIn_Edit" tabIndexProperty="tabIndexCommit"
								onclick="if(confirmCommit(this)){if(eventPreSubmit()){setReqType('commit');offUnloadConfirm();submit()}}">
								<bean:message key="com.button.commit" />
							</kit:button>
						</kit:equal>

						<%-- 戻るボタン --%>
						<kit:button styleClass="btn_large" property="btnBack"
							name="kuracKuraChokuDataIn_Edit" tabIndexProperty="tabIndexBack"
							onclick="if(confirmBack(this)){if(eventPreSubmit()){setReqType('back');offUnloadConfirm();submit()}}">
							<bean:message key="com.button.back" />
						</kit:button>

						<%-- 印刷ボタン --%>
						<kit:button property="btnPrint" styleClass="btn_large" tabindex="0"
							kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
							onclick="window.print();">
							<bean:message key="com.button.print" />
						</kit:button>
					</td>
				</tr>
			</table>
		</div>
	</kit:form>
	<%-- 照会・追加・更新・削除フォーム終了 --%>
	<hr />
	<%-- 共通フッター開始 --%>
	<%@ include file="/parts/comFooter.jsp"%>
	<%-- 共通フッター終了 --%>
</body>
</kit:html>
