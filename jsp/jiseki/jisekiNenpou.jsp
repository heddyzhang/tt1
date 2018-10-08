<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%-- ↓バッファをデフォルトの8kbから50kbに増やしていますよ --%>
<%@ page buffer="50kb" %>
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
<title><bean:message key="com.title.jisekiNenpou" /><kit:write name="jisekiNenpou_Edit" property="modeNm" scope="request" ignore="true" /></title>
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
	HOME_OBJECT = document.jisekiNenpou_Search.kikanStart;
}

	// 画面固有の明細部以外領域の高さ指定
	var othersHeight = 100;

	// ヘッダ固定
	//@@@ タイトル固定呪文 ここから @@@@@@@@@@@@@@
	window.onresize = localResize;
	function localResize(){
		resize();
		setLocalHeight("list" ,"details" ,45);
	}

	//@@@ タイトル固定呪文 ここまで @@@@@@@@@@@@@@

	// エラー時フォーカス用メソッド
function setFocusLocal(){

    // エラー時フラグ用配列
    var errorClassArr = new Array(
		"checkClass",
		"deleteClass",
		"kikanStartClass",
		"kikanEndClass",
		"kikanNenClass",
		"kbnDataClass",
		"syuukeiTaniClass",
		"jigyosyoCdClass");

    //エラー時フォーカス用配列
    var errorObjArr = new Array(
		"check",
		"delete",
		"kikanStart",
		"kikanEnd",
		"kikanNen",
		"kbnData",
		"syuukeiTani",
		"jigyosyoCd");
    // 明細部のフォーム名を指定する
    var formNm = "jisekiNenpou_Edit";
    var recNm = "jisekiNenpouRecord";
    var form = document.forms[formNm];
    var maxSu = form.maxSu.value;
    setFocusForError(formNm, maxSu, errorClassArr, errorObjArr,recNm);
}


	// CSVボタン押下時の処理 コミットテスト
	//function clickCsvBtn(obj){
	//	setRefObj('jisekiNenpouCsv', obj,
	//			'syohinCd','shnnmReport1',
	//			'youkiNmReport','caseIrisu');
	//	openModalSearch(event.srcElement.form.kaisyaCd.value);
	//}


	// 入力制御　期間開始日または終了日　未入力時自動セット
	function setDate() {
		var form = document.jisekiNenpou_Search;
		var kikanStart = form.elements["kikanStart"].value;
		var kikanEnd = form.elements["kikanEnd"].value;
		if (kikanStart == "" && kikanEnd != "") {
			form.elements["kikanStart"].value = kikanEnd;
		} else if (kikanStart != "" && kikanEnd == "") {
			form.elements["kikanEnd"].value = kikanStart;
		}
	}


	// 排他項目の制御　(①全国卸or特約店CD ②最終送荷先CDor卸店CD)
	function clearCd(target) {
		var form = document.jisekiNenpou_Search;
    	form.elements[target].value = "";
	}




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

/* ページ移動エリア設定 */
div.list_top {
	text-align: left;
	padding: 1pt;
	margin: 1pt 0pt 1pt;
	font-size: 11pt;
}

/* 画面調整用の空ボックス SEARCH_FORM */
span.karawakuSF {
	display: inline-block;
	border-style: none;
	background-color: #FFFFFF;
}


/* この画面は　縦横スクロール(表を4つに分割)*/


/* 一覧表レイアウト */
.syohinCdWidth{width:100px;} /* 商品CD */
.syohnNmWidth{width:200px;}	/* 商品名 */
.youkinmWidth{width:100px;}	/* 容器名 */
.iriSuWidth{width:60px;}	/* 入数 */
.ruikeiWidth{width:140px;}	/* 累計 単月*/
.tukiWidth{width:140px;}	/* 月 単月*/
.tounenWidth{width:140px;}	/* 当年 */
.zennenWidth{width:140px;}	/* 前年 */

/* 検索ボタン:マスター用 */
table.form td.searchFormButtonRow div.side {
	float: left;
	width: 10%;
}
table.form td.searchFormButtonRow div.center {
	float: left;
	text-align: center;
	width: 80%;
}
table.form td.searchFormButtonRow div.left{
	text-align: left;
}
table.form td.searchFormButtonRow div.right{
	text-align: right;
}

/** 明細部 */
table.list td {
	white-space: nowrap;
}


/* 明細部フッタボタン */
#footButtons{
	width:99%;
	margin: 0;
	padding: 0;
}
#footButtons td.side{
	width:23%;
}
#footButtons td.center{
	width:53%;
}
#footButtons td.center input{
}

td.cdView{
	text-align: right;
	padding-right: 1ex;
}
td.nmView{
	padding-left: 1ex;
}
-->
</style>

<script type="text/javascript">
</script>

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
	<%@ include file="/parts/comHeader.jsp"%>
	<%-- 共通ヘッダー終了 --%>

	<%-- 検索条件入力フォーム開始 --%>
	<kit:form action="/jisekiNenpou_Search.do"  styleClass="searchForm">
		<%-- タイトル開始行 --%>
		<div class = "title">
			<bean:message key="com.title.jisekiNenpou" />
			<kit:write name="jisekiNenpou_Edit" property="modeNm" scope="request" ignore="true" />
		</div>
		<%-- タイトル終了 --%>

		<div class="buttons" style="margin: 1px;">
			<%-- 結果出力 --%>
			<kit:button
				styleClass="btn_large"
				property="btnCsvMake"
				onclick="clickCsvBtn(this)" >
				<bean:message key="com.button.csvmakeResult" />
			</kit:button>
		</div>
		<div class="buttons" style="clear:both;margin: 1px;">
			<%-- 呼出ボタン --%>
			<kit:button styleClass="btn_large" property="btnSearch"
				tabindex="15"
				onclick="setDate(); if(eventPreSubmit()){setReqType('search');offUnloadConfirm();submit()}">
				<bean:message key="com.button.search" />
			</kit:button>

			<%-- ポップアップ検索 --%>
			<kit:button styleClass="btn_search btn_large"
				property="btnPopupSearch"
				onclick="openModalSearch(this.form.kaisyaCd.value)">
				<bean:message key="com.button.knsk" />
			</kit:button>

		</div>

		<kit:hidden name="jisekiNepou_Search" property="editable" />
		<kit:hidden name="jisekiNenpou_Search" property="reqType" />
		<table class="form" style="width:75%;margin: 1px;">
			<%-- 検索条件 --%>
			<tr>
				<td>
					<%-- ↓　property はRecordクラスのgetter,setterと揃えるべし！ --%>
					<%-- 期間開始日 --%>
					<label for="kikanStartLbl"><bean:message key="com.text.kikan" /></label>
					<kit:text
						styleId="kikanStartLbl"
						styleClass="text numeric ime_off frm_ym right must"
						format="yyyy/mm"
						property="kikanStart" maxlength="6" size="8" tabindex="1"
						onkeyup="nextTabMaxLenInput()" onblur="ctrlInput()" onfocus="ctrlInput()" />
					<%-- 期間終了日 --%>
					<label for="kikanEndLbl"><bean:message key="com.text.range" /></label>
					<kit:text
						styleId="kikanEndLbl"
						styleClass="text numeric ime_off frm_ym right must"
						format="yyyy/mm"
						property="kikanEnd" maxlength="6" size="8" tabindex="2"
						onkeyup="nextTabMaxLenInput()" onblur="ctrlInput()" onfocus="ctrlInput()" />
					<%-- 期間　範囲 --%>
					<label for="selNenLbl"></label>
					<kit:select styleClass="list ime_off"  property="selNen"
						styleId="selNenLbl" tabindex="3"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="jisekiNenpou_SelNenList"
							optional="false" useDefault="false" />
					</kit:select>
					<%-- データ区分 --%>
					<label for="selDataLbl"><bean:message key="com.text.data" /></label>
					<kit:select styleClass="list ime_off"  property="kbnData"
						styleId="kbnDataLbl" tabindex="4"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="jisekiNenpou_SelDataList"
							optional="false" useDefault="false" />
					</kit:select>
					<%-- 集計単位 --%>
					<label for="selSyukeiLbl"><bean:message key="com.text.syuukeiTani" /></label>
					<kit:select styleClass="list ime_off"  property="selSyukei"
						styleId="selSyukeiLbl" tabindex="5"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="jisekiNenpou_SelSyukeiList"
							optional="false" useDefault="false" />
					</kit:select>
					<%-- 事業所 --%>
					<label for="jigyosyoCdLbl"><bean:message key="com.text.zigyousyo" /></label>
					<kit:select styleClass="list ime_off" property="jigyosyoCd"
						styleId="jigyosyoCdLbl" tabindex="6"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="jisekiNenpou_HjsSitenList"
							optional="true" useDefault="false" />
					</kit:select>
					<%-- 課 --%>
					<label for="kaCdLbl"><bean:message key="com.text.ka" /></label>
					<kit:select styleClass="list ime_off" property="kaCd"
						styleId="kaCdLbl" tabindex="7"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="jisekiNenpou_HjsKaList"
							optional="true" useDefault="false" />
					</kit:select>
					<%-- 担当者 --%>
					<label for="tantosyaCdLbl"><bean:message key="com.text.tanto" /></label>
					<kit:select styleClass="list ime_off" property="tantosyaCd"
						styleId="tantosyaCdLbl" tabindex="8"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="jisekiNenpou_TantoCdList"
							optional="true" useDefault="false" />
					</kit:select>
				</td>
			</tr>
			<tr>
				<td>
					<%-- 都道府県 --%>
					<label for="countryCdLbl"><bean:message key="com.text.todofukn" /></label>
					<kit:select styleClass="list ime_off" property="countryCd"
						styleId="countryCdLbl" tabindex="9"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="jisekiNenpou_CountyCdList"
							optional="true" useDefault="false" />
					</kit:select>

					<%-- 見栄え調整用空ボックス --%>
					<span class="karawakuSF" style="width:20px;"></span>

					<%-- 輸出国 --%>
					<label for="syukaSakiCountryCdLbl"><bean:message key="com.text.countryNm" /></label>
					<kit:select styleClass="list ime_off" property="syukaSakiCountryCd"
						styleId="syukaSakiCountryCdLbl" tabindex="10"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="jisekiNenpou_SyukaSakiCountryList"
							optional="true" useDefault="false" />
					</kit:select>

					<%-- 見栄え調整用空ボックス --%>
					<span class="karawakuSF" style="width:20px;"></span>

					<%-- 全国卸 --%>
					<label for="zenkokuOrosiLbl"><bean:message key="com.text.zenkokuOrosi" /></label>
					<kit:select styleClass="list ime_off" property="zenkokuOrosi"
						styleId="zenkokuOrosiLbl" tabindex="11"
						onkeypress="ctrlSelection(this)"
						onblur="clearCd('tokuyakutenCd');clearCd('orositenCdLast')">
						<kit:options collection="jisekiNenpou_TokuyakuZenkokuList"
							optional="true" useDefault="false" />
					</kit:select>
					<%-- 特約店CD  --%>
					<label for="tokuyakutenCdLbl">
					<bean:message key="com.text.tokuyakuten"/></label>
					<kit:text
						styleId="tokuyakutenCdLbl"
						styleClass="text ime_off code frm_lpad rigth searchable"
						property="tokuyakutenCd" maxlength="6" size="8"
						tabindex="12" onfocus="ctrlInput()"
						onkeyup="nextTabMaxLenInput();setBtnDisable(this);"
						onblur="clearCd('zenkokuOrosi');clearCd('orositenCdLast'); ctrlInput(FRM_CLASS_PAD, 6, '0');
						setRefObj('popup', 'orositenCd', this, 'tokuyakutenFlg', 'tokuyakutenFlg');ctrlInput(); setBtnDisable(this);"/>
					<kit:hidden name="jisekiNepou_Search" property="tokuyakutenFlg" value="on" />

					<%-- 最終送荷先CD  --%>
					<label for="orositenCdLastLbl">
					<bean:message key="com.text.orositenCdLast"/></label>
					<kit:text
						styleId="orositenCdLastLbl"
						styleClass="text ime_off code frm_lpad rigth searchable"
						property="orositenCdLast" maxlength="8" size="10"
						tabindex="13" onfocus="ctrlInput()"
						onkeyup="nextTabMaxLenInput();setBtnDisable(this);"
						onblur="clearCd('tokuyakutenCd');clearCd('zenkokuOrosi'); ctrlInput(FRM_CLASS_PAD, 8, '0');
						setRefObj('popup', 'orsTatesnCd', this);ctrlInput();setBtnDisable(this);"/>

					<%-- 見栄え調整用空ボックス --%>
					<span class="karawakuSF" style="width:20px;"></span>

					<%-- 卸店CD  --%>
					<label for="orositenCdLbl">
					<bean:message key="com.text.orositen"/></label>
					<kit:text
						styleId="orositenCdLbl"
						styleClass="text ime_off code frm_lpad rigth searchable"
						property="orositenCd" maxlength="6" size="8"
						tabindex="14" onfocus="ctrlInput()"
						onkeyup="nextTabMaxLenInput();setBtnDisable(this);"
						onblur="ctrlInput(FRM_CLASS_PAD, 6, '0');
						setRefObj('popup', 'orositenCdRef',this
						　　　　　　,'orositenCd', 'orositenCd'
								　　,'zenkokuOrosi', 'zenkokuOrosi'
								　　,'tokuyakutenCd', 'tokuyakutenCd'
								　　,'orositenCdLast', 'orositenCdLast');ctrlInput();
						setBtnDisable(this);"/>
				</td>
			</tr>
		</table>
	</kit:form>
	<%-- 検索条件入力フォーム終了 --%>
	<hr />

	<%-- 登録・更新・削除フォーム開始 --%>
	<kit:form action="/jisekiNenpou_Edit.do">
		<kit:hidden name="jisekiNenpou_Edit" property="editable" />
		<kit:hidden name="jisekiNenpou_Edit" property="reqType" />
		<kit:hidden name="jisekiNenpou_Edit" property="mode" />
		<kit:hidden name="jisekiNenpou_Edit" property="preMode" />
		<kit:hidden name="jisekiNenpou_Edit" property="maxSu" />
		<%-- XXX:KzPoint / 選択行番号保持用要素 --%>
		<kit:hidden name="jisekiNenpou_Edit" property="selectedRowId" />
		<kit:hidden name="JisekiNenpou_SearchedList" property="hasPrevPage" defaultValue="false" />
		<kit:hidden name="JisekiNenpou_SearchedList" property="hasNextPage" defaultValue="true" />
		<%-- XXX:KzPoint / 縦横スクロールバー要素 --%>
		<kit:hidden name="jisekiNenpou_Edit" property="kikanMonth" />

		<div class="list_top">
			<kit:notEqual name="jisekiNenpou_Edit" property="mode"
				value="add" scope="request">
				<kit:notEqual name="jisekiNenpou_Edit" property="preMode" value="add" scope="request">
					<kit:button styleClass="btn_large" property="btnFirst" onclick="setReqType('first');offUnloadConfirm();submit()">
						<bean:message key="com.button.first" />
					</kit:button>
					<kit:button styleClass="btn_large" property="btnPrev" onclick="setReqType('prev');offUnloadConfirm();submit()">
						<bean:message key="com.button.previous" />
					</kit:button>
					<kit:button styleClass="btn_large" property="btnNext" onclick="setReqType('next');offUnloadConfirm();submit()">
						<bean:message key="com.button.next" />
					</kit:button>
					<kit:button styleClass="btn_large" property="btnLast" onclick="setReqType('last');offUnloadConfirm();submit()">
						<bean:message key="com.button.last" />
					</kit:button>
					<span class="page_no">
						<kit:write name="JisekiNenpou_SearchedList" property="currentPage" ignore="true">
							<bean:message key="com.text.slash" />
						</kit:write>
						<kit:write name="JisekiNenpou_SearchedList" property="totalPage" ignore="true">
							<bean:message key="com.text.page" />
						</kit:write>
						 <%--総件数対応例 開始--%>
						 <kit:present name="JisekiNenpou_SearchedList" property="totalCount">
							<bean:message key="com.text.leftKakko" />
							<bean:message key="com.text.totalCount" />
							<kit:write name="JisekiNenpou_SearchedList" property="totalCount" ignore="true">
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

			<%-- 初期表示前 --%>
			<kit:equal name="jisekiNenpou_Search"  property="selNen" value="" >
			<div id="scrollArea">
				<table class="scrollTable">
					<%-- 商品CD --%>
					<colgroup class="syohinCdWidth"></colgroup>
					<%-- 商品名 --%>
					<colgroup class="syohnNmWidth"></colgroup>
					<%-- 容器名 --%>
					<colgroup class="youkinmWidth"></colgroup>
					<%-- 入数 --%>
					<colgroup class="iriSuWidth"></colgroup>
					<%-- 累計 --%>
					<colgroup class="ruikeiWidth"></colgroup>

					<tr>
						<%-- 商品CD --%>
						<th><bean:message key="com.text.syohinCd" /></th>
						<%-- 商品名 --%>
						<th><bean:message key="com.text.syohnNm" /></th>
						<%-- 容器名 --%>
						<th><bean:message key="com.text.youkinm" /></th>
						<%-- 入数 --%>
						<th><bean:message key="com.text.iriSu" /></th>
						<%-- 累計 --%>
						<th><bean:message key="com.text.ruikei" /></th>
					</tr>
				</table>
			</div>
			</kit:equal>

		<%-- 1:当年のみ --%>
		<kit:equal name="jisekiNenpou_Search"  property="selNen" value="1" >

		<%-- A:タイトル行 --%>
		<div id="scrollArea" >
		<table class="scrollTable" >
				<%-- 列幅グループ化指定 --%>
				<%-- 商品CD --%>
				<colgroup class="syohinCdWidth"></colgroup>
				<%-- 商品名 --%>
				<colgroup class="syohnNmWidth"></colgroup>
				<%-- 容器名 --%>
				<colgroup class="youkinmWidth"></colgroup>
				<%-- 入数 --%>
				<colgroup class="iriSuWidth"></colgroup>
				<%-- 累計 --%>
				<colgroup class="ruikeiWidth"></colgroup>

				<tr>
					<%-- 商品CD --%>
					<th><bean:message key="com.text.syohinCd" /></th>
					<%-- 商品名 --%>
					<th><bean:message key="com.text.syohnNm" /></th>
					<%-- 容器名 --%>
					<th><bean:message key="com.text.youkinm" /></th>
					<%-- 入数 --%>
					<th><bean:message key="com.text.iriSu" /></th>
					<%-- 累計 --%>
					<th><bean:message key="com.text.ruikei" /></th>
				</tr>
		</table>

		<%-- B:タイトル行 --%>
		<div id="yokoScrollView" >
		<table class="scrollTable" id="yokoScrollArea" >
				<%-- 列幅グループ化指定 --%>
				<%-- 月 --%>
				<colgroup class="tukiWidth" span="<kit:write name="jisekiNenpou_Edit" property="kikanMonth" />"></colgroup>

				<tr>
					<%-- 月 --%>
					<%-- 1ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="1">
					<th><kit:write name="jisekiNenpou_Edit" property="monthItem[0]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 2ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="2">
					<th><kit:write name="jisekiNenpou_Edit" property="monthItem[1]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 3ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="3">
					<th><kit:write name="jisekiNenpou_Edit" property="monthItem[2]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 4ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="4">
					<th><kit:write name="jisekiNenpou_Edit" property="monthItem[3]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 5ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="5">
					<th><kit:write name="jisekiNenpou_Edit" property="monthItem[4]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 6ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="6">
					<th><kit:write name="jisekiNenpou_Edit" property="monthItem[5]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 7ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="7">
					<th><kit:write name="jisekiNenpou_Edit" property="monthItem[6]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 8ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="8">
					<th><kit:write name="jisekiNenpou_Edit" property="monthItem[7]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 9ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="9">
					<th><kit:write name="jisekiNenpou_Edit" property="monthItem[8]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 10ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="10">
					<th><kit:write name="jisekiNenpou_Edit" property="monthItem[9]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 11ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="11">
					<th><kit:write name="jisekiNenpou_Edit" property="monthItem[10]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 12ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="12">
					<th><kit:write name="jisekiNenpou_Edit" property="monthItem[11]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
				</tr>
			</table>
		  </div><%-- / id="yokoScrollView"  --%>


		  <%-- C:表領域開始 --%>
		  <div id="tateScrollArea" >
			<table class="scrollTable">
					<%-- 列幅グループ化指定 --%>
					<%-- 商品CD --%>
					<colgroup class="syohinCdWidth"></colgroup>
					<%-- 商品名 --%>
					<colgroup class="syohnNmWidth"></colgroup>
					<%-- 容器名 --%>
					<colgroup class="youkinmWidth"></colgroup>
					<%-- 入数 --%>
					<colgroup class="iriSuWidth"></colgroup>
					<%-- 累計 --%>
					<colgroup class="ruikeiWidth"></colgroup>

					<%-- レコード行 --%>
					<kit:iterate id="jisekiNenpouRecord"
						name="JisekiNenpou_SearchedList" property="pageList" indexId="idx"
						tabStartIndex="1" scope="session">
						<%-- XXX:KzPoint
						追加属性
						id : TRタグのID属性を示す接尾辞
						onmouseover：マウスをかざした時のイベント属性
						onmouseout：マウスが離れたときのイベント属性
						onclick：マウスクリックしたときのイベント属性　※この画面は検索結果表示のみの為不要
						ondblclick：ダブルクリックしたときのイベント属性　※この画面は検索結果表示のみの為不要
						※ setRowMouseOver(this)、setRowMouseOut(this)は、画面共通の呪文
						※ setRowKeys(…)、setSelectClick({EditForm名})は、画面毎に要編集
						 --%>
						<kit:tr name="jisekiNenpouRecord" errorProperty="hasError" id="id"
							modProperty="isModified" evenClass="even" oddClass="odd"
							errorClass="error" modClass="modified" indexed="true" styleClass="line"
							onmouseover="setRowMouseOver(this)" onmouseout="setRowMouseOut(this)">

							<%-- 商品CD --%>
							<td class="cdnView"><kit:write name="jisekiNenpouRecord" property="shohinCd" /></td>
							<%-- 商品名 --%>
							<td class="mjView"><kit:write name="jisekiNenpouRecord" property="shohinNm" /></td>
						 	<%-- 容器名 --%>
							<td class="mjView"><kit:write name="jisekiNenpouRecord" property="youkiNm" /></td>
						 	<%-- 入数 --%>
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="irisu" /></td>
							<%-- 累計 当年のみ --%>
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sumTounen" /></td>
						</kit:tr>
						</kit:iterate>
				</table>
			</div><%-- / id="tateScrollArea"  --%>

		<%-- D:表領域開始 --%>
		  <div id="dataScrollArea" >
			<table class="scrollTable" id="yokoScrollArea2">
					<%-- 列幅グループ化指定 --%>
					<%-- 月 --%>
					<colgroup class="tukiWidth" span="<kit:write name="jisekiNenpou_Edit" property="kikanMonth" />"></colgroup>

					<%-- レコード行 --%>
					<kit:iterate id="jisekiNenpouRecord"
						name="JisekiNenpou_SearchedList" property="pageList" indexId="idx"
						tabStartIndex="1" scope="session">
						<%-- XXX:KzPoint
						追加属性
						id : TRタグのID属性を示す接尾辞
						onmouseover：マウスをかざした時のイベント属性
						onmouseout：マウスが離れたときのイベント属性
						onclick：マウスクリックしたときのイベント属性　※この画面は検索結果表示のみの為不要
						ondblclick：ダブルクリックしたときのイベント属性　※この画面は検索結果表示のみの為不要
						※ setRowMouseOver(this)、setRowMouseOut(this)は、画面共通の呪文
						※ setRowKeys(…)、setSelectClick({EditForm名})は、画面毎に要編集
						 --%>
						<kit:tr name="jisekiNenpouRecord" errorProperty="hasError" id="id"
							modProperty="isModified" evenClass="even" oddClass="odd"
							errorClass="error" modClass="modified" indexed="true" styleClass="line"
							onmouseover="setRowMouseOver(this)" onmouseout="setRowMouseOut(this)">

							<%-- 当年　1ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="1">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum01Tounen" /></td>
							</logic:greaterEqual>
							<%-- 当年　2ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="2">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum02Tounen" /></td>
							</logic:greaterEqual>
							<%-- 当年　3ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="3">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum03Tounen" /></td>
							</logic:greaterEqual>
							<%-- 当年　4ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="4">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum04Tounen" /></td>
							</logic:greaterEqual>
							<%-- 当年　5ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="5">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum05Tounen" /></td>
							</logic:greaterEqual>
							<%-- 当年　6ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="6">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum06Tounen" /></td>
							</logic:greaterEqual>
							<%-- 当年　7ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="7">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum07Tounen" /></td>
							</logic:greaterEqual>
							<%-- 当年　8ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="8">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum08Tounen" /></td>
							</logic:greaterEqual>
							<%-- 当年　9ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="9">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum09Tounen" /></td>
							</logic:greaterEqual>
							<%-- 当年　10ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="10">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum10Tounen" /></td>
							</logic:greaterEqual>
							<%-- 当年　11ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="11">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum11Tounen" /></td>
							</logic:greaterEqual>
							<%-- 当年　12ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="12">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum12Tounen" /></td>
							</logic:greaterEqual>

						</kit:tr>

				<%-- エラー時フォーカス設定用hidden要素 --%>
				<kit:hidden property="checkClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="deleteClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="kikanStartClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="kikanEndClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="kikanNenClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="kbnDataClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="syuukeiTaniClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="jigyosyoCdClass" indexed="true" name="jisekiNenpouRecord" />


					   </kit:iterate>
					</tbody>
				</table>
			</div><%-- / id="yokoScrollArea"  --%>
		  </div><%-- / id="scrollArea" --%>
		</kit:equal><%-- / value="1" 当年のみ  --%>

		<%-- 2:当年前年 --%>
		<kit:equal name="jisekiNenpou_Search"  property="selNen" value="2" >

		<%-- A:タイトル行 --%>
		<div id="scrollArea" >
		<table class="scrollTable" >
				<%-- 列幅グループ化指定 --%>
				<%-- 商品CD --%>
				<colgroup class="syohinCdWidth"></colgroup>
				<%-- 商品名 --%>
				<colgroup class="syohnNmWidth"></colgroup>
				<%-- 容器名 --%>
				<colgroup class="youkinmWidth"></colgroup>
				<%-- 入数 --%>
				<colgroup class="iriSuWidth"></colgroup>
				<%-- 累計 --%>
				<colgroup class="ruikeiWidth"></colgroup>
				<%-- 月 当年--%>
				<colgroup class="tukiWidth" span="<kit:write name="jisekiNenpou_Edit" property="kikanMonth" />"></colgroup>
				<%-- 月 前年 --%>
				<colgroup class="tukiWidth" span="<kit:write name="jisekiNenpou_Edit" property="kikanMonth" />"></colgroup>

				<%-- タイトル行 --%>
				<tr>
					<%-- 商品CD --%>
					<th rowspan="2"><bean:message key="com.text.syohinCd" /></th>
					<%-- 商品名 --%>
					<th rowspan="2"><bean:message key="com.text.syohnNm" /></th>
					<%-- 容器名 --%>
					<th rowspan="2"><bean:message key="com.text.youkinm" /></th>
					<%-- 入数 --%>
					<th rowspan="2"><bean:message key="com.text.iriSu" /></th>
					<%-- 累計 --%>
					<th colspan="2"><bean:message key="com.text.ruikei" /></th>
				</tr>
				<tr >
					<%-- 累計 --%>
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
				</tr>

			</table>

		<%-- B:タイトル行 --%>
		<div id="yokoScrollView" >
		<table class="scrollTable" id="yokoScrollArea" >
				<%-- 列幅グループ化指定 --%>
				<%-- 月 当年--%>
				<colgroup class="tukiWidth" span="<kit:write name="jisekiNenpou_Edit" property="kikanMonth" />"></colgroup>
				<%-- 月 前年 --%>
				<colgroup class="tukiWidth" span="<kit:write name="jisekiNenpou_Edit" property="kikanMonth" />"></colgroup>
				<tr>
				<%-- 月 --%>
					<%-- 1ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="1">
					<th colspan="2"><kit:write name="jisekiNenpou_Edit" property="monthItem[0]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 2ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="2">
					<th colspan="2"><kit:write name="jisekiNenpou_Edit" property="monthItem[1]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 3ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="3">
					<th colspan="2"><kit:write name="jisekiNenpou_Edit" property="monthItem[2]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 4ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="4">
					<th colspan="2"><kit:write name="jisekiNenpou_Edit" property="monthItem[3]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 5ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="5">
					<th colspan="2"><kit:write name="jisekiNenpou_Edit" property="monthItem[4]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 6ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="6">
					<th colspan="2"><kit:write name="jisekiNenpou_Edit" property="monthItem[5]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 7ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="7">
					<th colspan="2"><kit:write name="jisekiNenpou_Edit" property="monthItem[6]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 8ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="8">
					<th colspan="2"><kit:write name="jisekiNenpou_Edit" property="monthItem[7]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 9ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="9">
					<th colspan="2"><kit:write name="jisekiNenpou_Edit" property="monthItem[8]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 10ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="10">
					<th colspan="2"><kit:write name="jisekiNenpou_Edit" property="monthItem[9]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 11ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="11">
					<th colspan="2"><kit:write name="jisekiNenpou_Edit" property="monthItem[10]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
					<%-- 12ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="12">
					<th colspan="2"><kit:write name="jisekiNenpou_Edit" property="monthItem[11]" format="yyyy/mm" /></th>
					</logic:greaterEqual>
				</tr>
				<tr>
					<%-- 月 --%>
					<%-- 1ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="1">
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
					</logic:greaterEqual>
					<%-- 2ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="2">
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
					</logic:greaterEqual>
					<%-- 3ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="3">
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
					</logic:greaterEqual>
					<%-- 4ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="4">
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
					</logic:greaterEqual>
					<%-- 5ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="5">
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
					</logic:greaterEqual>
					<%-- 6ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="6">
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
					</logic:greaterEqual>
					<%-- 7ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="7">
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
					</logic:greaterEqual>
					<%-- 8ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="8">
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
					</logic:greaterEqual>
					<%-- 9ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="9">
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
					</logic:greaterEqual>
					<%-- 10ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="10">
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
					</logic:greaterEqual>
					<%-- 11ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="11">
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
					</logic:greaterEqual>
					<%-- 12ヶ月目 --%>
					<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="12">
					<th><bean:message key="com.text.tounen" /></th>
					<th><bean:message key="com.text.zennen" /></th>
					</logic:greaterEqual>
				</tr>
			</table>
		  </div><%-- / id="yokoScrollView"  --%>

		<%-- C:表領域開始 --%>
		<div id="tateScrollArea" >
		<table class="scrollTable">
					<%-- 列幅グループ化指定 --%>
					<%-- 商品CD --%>
					<colgroup class="syohinCdWidth"></colgroup>
					<%-- 商品名 --%>
					<colgroup class="syohnNmWidth"></colgroup>
					<%-- 容器名 --%>
					<colgroup class="youkinmWidth"></colgroup>
					<%-- 入数 --%>
					<colgroup class="iriSuWidth"></colgroup>
					<%-- 累計 --%>
					<colgroup class="ruikeiWidth"></colgroup>
					<%-- 月 当年--%>
					<colgroup class="tukiWidth" span="<kit:write name="jisekiNenpou_Edit" property="kikanMonth" />"></colgroup>
					<%-- 月 前年 --%>
					<colgroup class="tukiWidth" span="<kit:write name="jisekiNenpou_Edit" property="kikanMonth" />"></colgroup>

					<%-- レコード行 --%>
					<kit:iterate id="jisekiNenpouRecord"
						name="JisekiNenpou_SearchedList" property="pageList" indexId="idx"
						tabStartIndex="1" scope="session">
						<%-- XXX:KzPoint
						追加属性
						id : TRタグのID属性を示す接尾辞
						onmouseover：マウスをかざした時のイベント属性
						onmouseout：マウスが離れたときのイベント属性
						onclick：マウスクリックしたときのイベント属性 ※この画面は検索結果表示のみの為不要
						ondblclick：ダブルクリックしたときのイベント属性　※この画面は検索結果表示のみの為不要
						※ setRowMouseOver(this)、setRowMouseOut(this)は、画面共通の呪文
						※ setRowKeys(…)、setSelectClick({EditForm名})は、画面毎に要編集
						 --%>
						<kit:tr name="jisekiNenpouRecord" errorProperty="hasError" id="id"
							modProperty="isModified" evenClass="even" oddClass="odd"
							errorClass="error" modClass="modified" indexed="true" styleClass="line"
							onmouseover="setRowMouseOver(this)" onmouseout="setRowMouseOut(this)">

							<%-- 商品CD --%>
							<td class="cdnView"><kit:write name="jisekiNenpouRecord" property="shohinCd" /></td>
							<%-- 商品名 --%>
							<td class="mjView"><kit:write name="jisekiNenpouRecord" property="shohinNm" /></td>
						 	<%-- 容器名 --%>
							<td class="mjView"><kit:write name="jisekiNenpouRecord" property="youkiNm" /></td>
						 	<%-- 入数 --%>
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="irisu" /></td>

							<%-- 累計 当年 --%>
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sumTounen" /></td>
							<%-- 累計 前年 --%>
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sumZennen" /></td>

						</kit:tr>
						</kit:iterate>
				</table>
			</div><%-- / id="tateScrollArea"  --%>

			<%-- D:表領域開始 --%>
		    <div id="dataScrollArea" >
			<table class="scrollTable" id="yokoScrollArea2">
					<%-- 列幅グループ化指定 --%>
					<%-- 月 当年 --%>
					<colgroup class="tukiWidth" span="<kit:write name="jisekiNenpou_Edit" property="kikanMonth" />"></colgroup>
					<%-- 月 前年 --%>
					<colgroup class="tukiWidth" span="<kit:write name="jisekiNenpou_Edit" property="kikanMonth" />"></colgroup>

					<%-- レコード行 --%>
					<kit:iterate id="jisekiNenpouRecord"
						name="JisekiNenpou_SearchedList" property="pageList" indexId="idx"
						tabStartIndex="1" scope="session">
						<%-- XXX:KzPoint
						追加属性
						id : TRタグのID属性を示す接尾辞
						onmouseover：マウスをかざした時のイベント属性
						onmouseout：マウスが離れたときのイベント属性
						onclick：マウスクリックしたときのイベント属性 ※この画面は検索結果表示のみの為不要
						ondblclick：ダブルクリックしたときのイベント属性　※この画面は検索結果表示のみの為不要
						※ setRowMouseOver(this)、setRowMouseOut(this)は、画面共通の呪文
						※ setRowKeys(…)、setSelectClick({EditForm名})は、画面毎に要編集
						 --%>
						<kit:tr name="jisekiNenpouRecord" errorProperty="hasError" id="id"
							modProperty="isModified" evenClass="even" oddClass="odd"
							errorClass="error" modClass="modified" indexed="true" styleClass="line"
							onmouseover="setRowMouseOver(this)" onmouseout="setRowMouseOut(this)">

							<%-- 当年　1ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="1">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum01Tounen" /></td>
							</logic:greaterEqual>
							<%-- 前年　1ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="1">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum01Zennen" /></td>
							</logic:greaterEqual>

							<%-- 当年　2ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="2">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum02Tounen" /></td>
							</logic:greaterEqual>
							<%-- 前年　2ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="2">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum02Zennen" /></td>
							</logic:greaterEqual>

							<%-- 当年　3ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="3">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum03Tounen" /></td>
							</logic:greaterEqual>
							<%-- 前年　3ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="3">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum03Zennen" /></td>
							</logic:greaterEqual>

							<%-- 当年　4ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="4">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum04Tounen" /></td>
							</logic:greaterEqual>
							<%-- 前年　4ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="4">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum04Zennen" /></td>
							</logic:greaterEqual>

							<%-- 当年　5ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="5">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum05Tounen" /></td>
							</logic:greaterEqual>
							<%-- 前年　5ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="5">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum05Zennen" /></td>
							</logic:greaterEqual>

							<%-- 当年　6ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="6">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum06Tounen" /></td>
							</logic:greaterEqual>
							<%-- 前年　6ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="6">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum06Zennen" /></td>
							</logic:greaterEqual>

							<%-- 当年　7ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="7">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum07Tounen" /></td>
							</logic:greaterEqual>
							<%-- 前年　7ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="7">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum07Zennen" /></td>
							</logic:greaterEqual>

							<%-- 当年　8ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="8">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum08Tounen" /></td>
							</logic:greaterEqual>
							<%-- 前年　8ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="8">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum08Zennen" /></td>
							</logic:greaterEqual>

							<%-- 当年　9ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="9">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum09Tounen" /></td>
							</logic:greaterEqual>
							<%-- 前年　9ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="9">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum09Zennen" /></td>
							</logic:greaterEqual>

							<%-- 当年　10ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="10">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum10Tounen" /></td>
							</logic:greaterEqual>
							<%-- 前年　10ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="10">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum10Zennen" /></td>
							</logic:greaterEqual>

							<%-- 当年　11ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="11">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum11Tounen" /></td>
							</logic:greaterEqual>
							<%-- 前年　11ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="11">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum11Zennen" /></td>
							</logic:greaterEqual>

							<%-- 当年　12ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="12">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum12Tounen" /></td>
							</logic:greaterEqual>
							<%-- 前年　12ヶ月目 --%>
							<logic:greaterEqual name="jisekiNenpou_Edit" property="kikanMonth" value="12">
							<td class="nmView"><kit:write name="jisekiNenpouRecord" property="sum12Zennen" /></td>
							</logic:greaterEqual>

						</kit:tr>

				<%-- エラー時フォーカス設定用hidden要素 --%>
				<kit:hidden property="checkClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="deleteClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="kikanStartClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="kikanEndClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="kikanNenClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="kbnDataClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="syuukeiTaniClass" indexed="true" name="jisekiNenpouRecord" />
				<kit:hidden property="jigyosyoCdClass" indexed="true" name="jisekiNenpouRecord" />

					</kit:iterate>
				</table>
			</div><%-- / id="yokoScrollArea"  --%>
			</div><%-- / id="scrollArea" --%>

			</kit:equal><%-- / value="2" 当年前年  --%>

		<%-- リスト終了 --%>


		<%-- 縦横スクロール制御用開始 --%>
		<script type="text/javascript">
			// 年フラグ
			var selNen = "<kit:write name="jisekiNenpou_Search" property="selNen" ignore="true" />";
			if (selNen == "") { selNen = "0"; }

			// 表示月数
			var kikanMonth = "<kit:write name="jisekiNenpou_Edit" property="kikanMonth" ignore="true" />";
			if (kikanMonth == "") { kikanMonth = "0"; }

			// 設定項目
			var yokoScroll = "550"; // 横スクロールバーの表示幅
			var tateScroll = "300"; // 縦スクロールの高さ
			var areaScroll = "400"; // スクロール領域全体の高さ
			var fixedItemWidth = "460"; // 固定項目の幅合計（累計部除く）
			var itemWidth = "140"; // 横スクロール部一項目の幅（年月部）
			var lineCnt = selNen; // 項目高さ（文字行数に相当）
			var yokoOffsetAdd = eval(itemWidth) * eval(selNen); // 固定項目幅の加算分（累計部）
			var yokoSum = eval(itemWidth) * eval(kikanMonth) * eval(selNen); // 横スクロール項目幅合計

			// ページ毎のレコード数
			var recordsParPage = "<kit:write name="JisekiNenpou_SearchedList" property="recordsParPage" ignore="true" />";
			// トータル件数
			var totalCount = "<kit:write name="JisekiNenpou_SearchedList" property="totalCount" ignore="true" />";
			// 次ページが表示可能か
			var hasNextPage = "<kit:write name="JisekiNenpou_SearchedList" property="hasNextPage" ignore="true" />";
		</script>
		<script type="text/javascript" src="./js/kzScroll.js"></script>
		<%-- 縦横スクロール制御用終了 --%>


	</kit:form>
	<%-- 照会・追加・更新・削除フォーム終了 --%>
	<hr/>

	<%-- 共通フッター開始 --%>
	<%@ include file="/parts/comFooter.jsp"%>
	<%-- 共通フッター終了 --%>
</body>
</kit:html>
