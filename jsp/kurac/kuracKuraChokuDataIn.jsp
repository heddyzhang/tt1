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
<title><bean:message key="com.title.kuracKuraChokuDataIn" /><kit:write name="kuracKuraChokuDataIn_Edit" property="modeNm" scope="request" ignore="true" /></title>
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
		HOME_OBJECT = document.kuracKuraChokuDataIn_Search.riyouKbn;
	}

	// 画面固有の明細部以外領域の高さ指定
	var othersHeight = 298;

	// ヘッダ固定

	window.onresize = localResize;
	function localResize() {
		resize();
		setLocalHeight("list", "details", 45);
	}

	// エラー時フォーカス用メソッド
	function setFocusLocal() {

		// エラー時フラグ用配列
		var errorClassArr = new Array(
			"jigyousyoClass",
			"shohinGrpClass",
			"mousikomibiClass",
			"hasoyoteibiClass");

		//エラー時フォーカス用配列
		var errorObjArr = new Array(
			"jigyousyo",
			"shohinGrp",
			"mousikomibi",
			"hasoyoteibi");

		// 明細部のフォーム名を指定する
		var formNm = "kuracKuraChokuDataIn_Search";
		var recNm = "kuracKuraChokuDataIn_Search";
		var form = document.forms[formNm];
		var maxSu = "1";
		setFocusForError(formNm, maxSu, errorClassArr, errorObjArr,recNm);
	}

	// 非同期通信（パラメータ設定）
	function getValueByAjax(keyObj, targetNm, shortNm) {
		var formNm = "kuracKuraChokuDataIn_Search";
		var form = document.forms[formNm];
		var keyValue = form.elements[keyObj.name].value; // 呼び出し元キー
		var doAjaxFlg = false;
		if (keyValue != "") {
			doAjaxFlg = true;
		}
		var subKeyNm = ""; // 追加キー
		keyValue = concatKeys(form, keyObj, subKeyNm); // 呼び出し元キー＋追加キー

		var url = "/kit/kuracKuraChokuDataIn_Ajax.do?objCd=" + keyValue + "&shortNm=" + shortNm + "&objNm=" + targetNm;

		if (doAjaxFlg) {
			// Ajax
			CallAjax("", url, form, keyObj, targetNm);
		} else {
			// 値クリア
			clearValue(formNm, keyObj, targetNm);
		}
	}

	function addSyoriCheck(com){

		// 「OK」時の処理開始 ＋ 確認ダイアログの表示
		if(window.confirm(com+'追加処理をを開始します。よろしいですか？')){
			return true;
		}
		return false;
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

.wrapper {
}

#list {
	width: 1300px;
	float: left;
	/*overflow-x:auto;*/
	overflow-y:auto;
}
.fixedTitleTable {
	width: 1820px;
}

/* 一覧表レイアウト */
.mousikomiNoWidth {width: 130px;} /* 申込受付No */
.hasoyoteibiWidth {width: 130px;} /* 発送予定日 */
.irainusiWidth {width: 300px;} /* 依頼主 */
.tdkskWidth {width: 300px;} /* 届け先 */
.syuhantenNmWidth {width: 300px;} /* 酒販店名 */
.mousikomibiWidth {width: 110px;} /* 申込日 */
.jyucyuNoWidth {width: 110px;} /* 受注No. */
.syubetuCdWidth {width: 130px;} /* ﾃﾞｰﾀ種別 */
.riyouKbnWidth {width: 110px;} /* 利用区分 */
.kousinDtWidth {width: 200px;} /* 更新日時 */

* 折返し表示項目（上記幅-10px） */
.doWrap { word-wrap: break-word; } /* 折返し指定 */
.irainusiWidthWrap {width: 290px;} /* 依頼主 */
.tdkskWidthWrap {width: 290px;} /* 届け先 */
.syuhantenNmWidthWrap {width: 290px;} /* 酒販店名 */

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
			stopWaiting();
			setHomePosition();
			setFocusLocal();
	"
	onbeforeunload="return eventUnloadMain();"
	<%-- XXX:「F1」キーでのIEヘルプ呼出し不活性化 --%>
	onhelp="return false;">
	<%-- 共通ヘッダー開始 --%>
	<%@ include file="/parts/comHeader.jsp"%>
	<%-- 共通ヘッダー終了 --%>

	<%-- 検索条件入力フォーム開始 --%>
	<kit:form action="/kuracKuraChokuDataIn_Search.do"  styleClass="searchForm">
		<%-- タイトル開始行 --%>
		<div class = "title">
			<bean:message key="com.title.kuracKuraChokuDataIn" />
			<kit:write name="kuracKuraChokuDataIn_Edit" property="modeNm" scope="request" ignore="true" />
		</div>
		<%-- タイトル終了 --%>

		<div class="buttons" style="clear:both;margin: 1px;">
			<%-- 出荷追加ボタン --%>
			<kit:button property="btnSyukaAdd" styleClass="btn_large"
				kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
				onclick="if(addSyoriCheck('出荷追加')){if(eventPreSubmit()){setReqType('syukaAdd');offUnloadConfirm();startWaiting();submit()}}">
				<bean:message key="com.button.SyukaAdd" />
			</kit:button>
			<%-- 呼出ボタン --%>
			<kit:button property="btnSearch" styleClass="btn_large" tabindex="14"
				onclick="if(eventPreSubmit()){setReqType('search');offUnloadConfirm();startWaiting();submit()}">
				<bean:message key="com.button.search" />
			</kit:button>
			<%-- ポップアップ検索 --%>
			<kit:button styleClass="btn_search btn_large"
				property="btnPopupSearch"
				onclick="openModalSearch(this.form.kaisyaCd.value)">
				<bean:message key="com.button.knsk" />
			</kit:button>
		</div>
		<div class="buttons" style="clear:both;">
			<%-- 新規ボタン --%>
			<kit:button property="btnAdd" styleClass="btn_large"
				kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
				onclick="if(eventPreSubmit()){setReqType('add');offUnloadConfirm();startWaiting();submit()}">
				<bean:message key="com.button.add" />
			</kit:button>
			<%-- 受注追加ボタン --%>
			<kit:button property="btnJucyuAdd" styleClass="btn_large"
				kengenLevelProperty="kengenLevel" requiredKengenLevel="50"
				onclick="if(addSyoriCheck('受注追加')){if(eventPreSubmit()){setReqType('jyucyuAdd');offUnloadConfirm();startWaiting();submit()}}">
				<bean:message key="com.button.JucyuAdd" />
			</kit:button>
		</div>

		<kit:hidden name="kuracKuraChokuDataIn_Search" property="editable" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="reqType" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="orsTatesnCd" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="sortItem" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="sortOrder" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="sortFlag" />

		<kit:hidden name="kuracKuraChokuDataIn_Search" property="jigyousyoClass" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="shohinGrpClass" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="mousikomibiClass" />
		<kit:hidden name="kuracKuraChokuDataIn_Search" property="hasoyoteibiClass" />
		<table class="form" style="width:75%;">
			<%-- 検索条件 --%>
			<tr>
				<td>
				</td>
			</tr>
		</table>
		<table class="form" style="width:75%;">
			<tr>
				<td>
					<%-- 利用区分 --%>
					<label for="riyouKbnLbl"><bean:message key="com.text.riyouKbn" /></label>
				</td>
				<td>
					<kit:select name="kuracKuraChokuDataIn_Search" property="riyouKbn"
						styleClass="list ime_off"
						styleId="riyouKbnLbl" tabindex="1"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="kuracKuraChokuDataIn_RiyouKbnList" optional="true" useDefault="true" />
					</kit:select>
				</td>
				<td>
					<%-- ﾃﾞｰﾀ種別 --%>
					<label for="syubetuCdLbl"><bean:message key="com.text.dataSyubetu" /></label>
				</td>
				<td>
					<kit:select name="kuracKuraChokuDataIn_Search" property="syubetuCd"
						styleClass="list ime_off"
						styleId="syubetuCdLbl" tabindex="2"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="kuracKuraChokuDataIn_KuracDataList" optional="true" useDefault="true" />
					</kit:select>
				</td>
			</tr>
			<tr>
				<td>
					<%-- ﾒｰｶｰNo --%>
					<label for="makerNoLbl"><bean:message key="com.text.makerNo" /></label>
				</td>
				<td>
					<kit:text name="kuracKuraChokuDataIn_Search" property="makerNo" maxlength="8" size="12"
						styleClass="text ime_off left"
						styleId="makerNoLbl" tabindex="3"
						onkeyup="nextTabMaxLenInput()"
						onblur="ctrlInput()"
						onfocus="ctrlInput()" />
					<%-- 送荷先 --%>
				</td>
				<td>
					<label for="sounisakiNmLbl"><bean:message key="com.text.sounisaki" /></label>
				</td>
				<td>
					<kit:text name="kuracKuraChokuDataIn_Search" property="sounisakiNm" maxlength="24" size="20"
						styleClass="text ime_on left"
						styleId="sounisakiNmLbl" tabindex="4"
						onkeyup="nextTabMaxLenInput()"
						onblur="ctrlInput()"
						onfocus="ctrlInput()" />
				</td>
				<td>
					<%-- 酒販店名 --%>
					<label for="syuhantenNmLbl"><bean:message key="com.text.syuhantenNm" /></label>
				</td>
				<td>
					<kit:text name="kuracKuraChokuDataIn_Search" property="syuhantenNm" maxlength="40" size="20"
						styleClass="text ime_on left"
						styleId="syuhantenNmLbl" tabindex="5"
						onkeyup="nextTabMaxLenInput()"
						onblur="ctrlInput()"
						onfocus="ctrlInput()" />
				</td>
				<td>
					<%-- 依頼主 --%>
					<label for="irainusiLbl"><bean:message key="com.text.irainusi" /></label>
					</td>
				<td>
					<kit:text name="kuracKuraChokuDataIn_Search" property="irainusi" maxlength="56" size="20"
						styleClass="text ime_on left"
						styleId="sirainusiLbl" tabindex="6"
						onkeyup="nextTabMaxLenInput()"
						onblur="ctrlInput()"
						onfocus="ctrlInput()" />
				</td>
				<td>
					<%-- 申込日 --%>
					<label for="mousikomibiLbl"><bean:message key="com.text.mousikomibi" /></label>
				</td>
				<kit:td name="kuracKuraChokuDataIn_Search" property="mousikomibiClass">
					<kit:text name="kuracKuraChokuDataIn_Search" property="mousikomibi" maxlength="8" size="14"
						styleClass="text ime_off date frm_dt right"
						styleId="mousikomibiLbl" tabindex="7"
						onkeyup="nextTabMaxLenInput()" onblur="ctrlInput()" onfocus="ctrlInput()"
						format="yyyy/mm/dd" />
				</kit:td>
			</tr>
			<tr>
				<td>
					<%-- 事業所 --%>
					<label for="zigyousyoLbl"><bean:message key="com.text.zigyousyo" /></label>
				</td>
				<kit:td name="kuracKuraChokuDataIn_Search" property="jigyousyoClass">
					<kit:select name="kuracKuraChokuDataIn_Search" property="jigyousyo"
						styleClass="list ime_off"
						styleId="jigyousyoLbl" tabindex="8"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="kuracKuraChokuDataIn_KuracSitenList" optional="true" useDefault="true" />
					</kit:select>
				</kit:td>
				<td>
					<%-- 商品GRP --%>
					<label for="shohinGrpLbl"><bean:message key="com.text.shohinGrp" /></label>
				</td>
				<kit:td name="kuracKuraChokuDataIn_Search" property="shohinGrpClass">
					<kit:select name="kuracKuraChokuDataIn_Search" property="shohinGrp"
						styleClass="list ime_off"
						styleId="shohinGrpLbl" tabindex="9"
						onkeypress="ctrlSelection(this)">
						<kit:options collection="kuracKuraChokuDataIn_KuracShohingrpList" optional="true" useDefault="true" />
					</kit:select>
				</kit:td>
				<td>
					<%-- 酒販店CD --%>
					<label for="syuhantenCdLbl"><bean:message key="com.text.syuhantenCd"/></label>
				</td>
				<td>
					<kit:text
						name="kuracKuraChokuDataIn_Search" property="syuhantenCd" styleId="syuhantenCdLbl" maxlength="8" size="10" tabindex="10"
						styleClass="text ime_off code frm_lpad right searchable"
						onkeyup="nextTabMaxLenInput()"
						onblur="setRefObj('popup','syuhantenCd',this);ctrlInput(FRM_CLASS_PAD,8,'0');"
						onfocus="ctrlInput();setBeforeValue(this);" />
				</td>
				<td>
					<%-- 届け先 --%>
					<label for="tdkskLbl"><bean:message key="com.text.tdksk" /></label>
				</td>
				<td>
					<kit:text name="kuracKuraChokuDataIn_Search" property="tdksk" maxlength="56" size="20"
						styleClass="text ime_on left"
						styleId="tdkskLbl" tabindex="11"
						onkeyup="nextTabMaxLenInput()"
						onblur="ctrlInput()"
						onfocus="ctrlInput()" />
				</td>
				<td>
					<%-- 発送予定日 --%>
					<label for="hasoyoteibiLbl"><bean:message key="com.text.hasoyoteibi" /></label>
				</td>
				<kit:td name="kuracKuraChokuDataIn_Search" property="hasoyoteibiClass">
					<kit:text name="kuracKuraChokuDataIn_Search" property="hasoyoteibi" maxlength="8" size="14"
						styleClass="text ime_off date frm_dt right"
						styleId="hasoyoteibiLbl" tabindex="12"
						onkeyup="nextTabMaxLenInput()" onblur="ctrlInput()" onfocus="ctrlInput()"
						format="yyyy/mm/dd" />
				</kit:td>
				<td>
					<%-- 未連携 --%>
					<kit:checkbox styleClass="check" name="kuracKuraChokuDataIn_Search" property="miJyucyuOnly"
						tabindex="13" styleId="miJyucyuOnlyLbl"/>
					<label for="miJyucyuOnlyLbl"><bean:message key="com.text.mirenkei"/></label>
				</td>
			</tr>
		</table>
	</kit:form>
	<%-- 検索条件入力フォーム終了 --%>
	<hr />
	<%-- 登録・更新・削除フォーム開始 --%>
	<kit:form action="/kuracKuraChokuDataIn_Edit.do">
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="editable" />
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="reqType" />
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="mode" />
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="preMode" />
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="maxSu" />
		<%-- XXX:KzPoint / 選択行番号保持用要素 --%>
		<kit:hidden name="kuracKuraChokuDataIn_Edit" property="selectedRowId" />
		<kit:hidden name="kuracKuraChokuDataIn_SearchedList" property="hasPrevPage" defaultValue="false" />
		<kit:hidden name="kuracKuraChokuDataIn_SearchedList" property="hasNextPage" defaultValue="false" />
		<div class="list_top">
			<kit:notEqual name="kuracKuraChokuDataIn_Edit" property="mode" value="add" scope="request">
				<kit:notEqual name="kuracKuraChokuDataIn_Edit" property="preMode" value="add" scope="request">
					<kit:button property="btnFirst" styleClass="btn_large" onclick="setReqType('first');offUnloadConfirm();submit()">
						<bean:message key="com.button.first" />
					</kit:button>
					<kit:button property="btnPrev" styleClass="btn_large" onclick="setReqType('prev');offUnloadConfirm();submit()">
						<bean:message key="com.button.previous" />
					</kit:button>
					<kit:button property="btnNext" styleClass="btn_large" onclick="setReqType('next');offUnloadConfirm();submit()">
						<bean:message key="com.button.next" />
					</kit:button>
					<kit:button property="btnLast" styleClass="btn_large" onclick="setReqType('last');offUnloadConfirm();submit()">
						<bean:message key="com.button.last" />
					</kit:button>
					<span class="page_no">
						<kit:write name="kuracKuraChokuDataIn_SearchedList" property="currentPage" ignore="true">
							<bean:message key="com.text.slash" />
						</kit:write>
						<kit:write name="kuracKuraChokuDataIn_SearchedList" property="totalPage" ignore="true">
							<bean:message key="com.text.page" />
						</kit:write>
						 <%--総件数対応例 開始--%>
						 <kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<bean:message key="com.text.leftKakko" />
							<bean:message key="com.text.totalCount" />
							<kit:write name="kuracKuraChokuDataIn_SearchedList" property="totalCount" ignore="true">
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
				<colgroup class="mousikomiNoWidth"></colgroup> <%-- 申込受付No --%>
				<colgroup class="hasoyoteibiWidth"></colgroup> <%-- 発送予定日 --%>
				<colgroup class="irainusiWidth"></colgroup> <%-- 依頼主 --%>
				<colgroup class="tdkskWidth"></colgroup> <%-- 届け先 --%>
				<colgroup class="syuhantenNmWidth"></colgroup> <%-- 酒販店名 --%>
				<colgroup class="mousikomibiWidth"></colgroup> <%-- 申込日 --%>
				<colgroup class="jyucyuNoWidth"></colgroup> <%-- 受注No. --%>
				<colgroup class="syubetuCdWidth"></colgroup> <%-- ﾃﾞｰﾀ種別 --%>
				<colgroup class="riyouKbnWidth"></colgroup> <%-- 利用区分 --%>
				<colgroup class="kousinDtWidth"></colgroup> <%-- 更新日時 --%>

				<%-- タイトル行 --%>
				<tr class="head">
					<%-- 申込受付No --%>
					<th>
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','MOUSIKOMI_NO','ASC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="MOUSIKOMI_NO_ASC"><img src="./image/asc.gif" width="9px" height="9px" alt="<bean:message key="com.text.asc" />" title="<bean:message key="com.text.sortAsc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="MOUSIKOMI_NO_ASC"><img src="./image/ascOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.ascOff" />" title="<bean:message key="com.text.sortAsc" />"></kit:notEqual>
							</a>
						</kit:present>
						<bean:message key="com.text.mousikomiNo" />
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','MOUSIKOMI_NO','DESC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="MOUSIKOMI_NO_DESC"><img src="./image/desc.gif" width="9px" height="9px" alt="<bean:message key="com.text.desc" />" title="<bean:message key="com.text.sortDesc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="MOUSIKOMI_NO_DESC"><img src="./image/descOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.descOff" />" title="<bean:message key="com.text.sortDesc" />"></kit:notEqual>
							</a>
						</kit:present>
					</th>
					<%-- 発送予定日 --%>
					<th>
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','HASO_YOTEI_DT','ASC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="HASO_YOTEI_DT_ASC"><img src="./image/asc.gif" width="9px" height="9px" alt="<bean:message key="com.text.asc" />" title="<bean:message key="com.text.sortAsc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="HASO_YOTEI_DT_ASC"><img src="./image/ascOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.ascOff" />" title="<bean:message key="com.text.sortAsc" />"></kit:notEqual>
							</a>
						</kit:present>
						<bean:message key="com.text.hasoyoteibi" />
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','HASO_YOTEI_DT','DESC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="HASO_YOTEI_DT_DESC"><img src="./image/desc.gif" width="9px" height="9px" alt="<bean:message key="com.text.desc" />" title="<bean:message key="com.text.sortDesc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="HASO_YOTEI_DT_DESC"><img src="./image/descOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.descOff" />" title="<bean:message key="com.text.sortDesc" />"></kit:notEqual>
							</a>
						</kit:present>
					</th>
					<%-- 依頼主 --%>
					<th>
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','IRAINUSI_NM','ASC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="IRAINUSI_NM_ASC"><img src="./image/asc.gif" width="9px" height="9px" alt="<bean:message key="com.text.asc" />" title="<bean:message key="com.text.sortAsc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="IRAINUSI_NM_ASC"><img src="./image/ascOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.ascOff" />" title="<bean:message key="com.text.sortAsc" />"></kit:notEqual>
							</a>
						</kit:present>
						<bean:message key="com.text.irainusi" />
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','IRAINUSI_NM','DESC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="IRAINUSI_NM_DESC"><img src="./image/desc.gif" width="9px" height="9px" alt="<bean:message key="com.text.desc" />" title="<bean:message key="com.text.sortDesc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="IRAINUSI_NM_DESC"><img src="./image/descOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.descOff" />" title="<bean:message key="com.text.sortDesc" />"></kit:notEqual>
							</a>
						</kit:present>
					</th>
					<%-- 届け先 --%>
					<th>
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','TODOKESAKI_NM','ASC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="TODOKESAKI_NM_ASC"><img src="./image/asc.gif" width="9px" height="9px" alt="<bean:message key="com.text.asc" />" title="<bean:message key="com.text.sortAsc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="TODOKESAKI_NM_ASC"><img src="./image/ascOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.ascOff" />" title="<bean:message key="com.text.sortAsc" />"></kit:notEqual>
							</a>
						</kit:present>
						<bean:message key="com.text.tdksk" />
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','TODOKESAKI_NM','DESC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="TODOKESAKI_NM_DESC"><img src="./image/desc.gif" width="9px" height="9px" alt="<bean:message key="com.text.desc" />" title="<bean:message key="com.text.sortDesc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="TODOKESAKI_NM_DESC"><img src="./image/descOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.descOff" />" title="<bean:message key="com.text.sortDesc" />"></kit:notEqual>
							</a>
						</kit:present>
					</th>
					<%-- 酒販店 --%>
					<th>
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','SYUHANTEN_NM','ASC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="SYUHANTEN_NM_ASC"><img src="./image/asc.gif" width="9px" height="9px" alt="<bean:message key="com.text.asc" />" title="<bean:message key="com.text.sortAsc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="SYUHANTEN_NM_ASC"><img src="./image/ascOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.ascOff" />" title="<bean:message key="com.text.sortAsc" />"></kit:notEqual>
							</a>
						</kit:present>
						<bean:message key="com.text.syuhantenNm" />
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','SYUHANTEN_NM','DESC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="SYUHANTEN_NM_DESC"><img src="./image/desc.gif" width="9px" height="9px" alt="<bean:message key="com.text.desc" />" title="<bean:message key="com.text.sortDesc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="SYUHANTEN_NM_DESC"><img src="./image/descOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.descOff" />" title="<bean:message key="com.text.sortDesc" />"></kit:notEqual>
							</a>
						</kit:present>
					</th>
					<%-- 申込日 --%>
					<th>
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','UKETUKE_DT','ASC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="UKETUKE_DT_ASC"><img src="./image/asc.gif" width="9px" height="9px" alt="<bean:message key="com.text.asc" />" title="<bean:message key="com.text.sortAsc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="UKETUKE_DT_ASC"><img src="./image/ascOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.ascOff" />" title="<bean:message key="com.text.sortAsc" />"></kit:notEqual>
							</a>
						</kit:present>
						<bean:message key="com.text.mousikomibi" />
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','UKETUKE_DT','DESC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="UKETUKE_DT_DESC"><img src="./image/desc.gif" width="9px" height="9px" alt="<bean:message key="com.text.desc" />" title="<bean:message key="com.text.sortDesc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="UKETUKE_DT_DESC"><img src="./image/descOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.descOff" />" title="<bean:message key="com.text.sortDesc" />"></kit:notEqual>
							</a>
						</kit:present>
					</th>
					<%-- 受注No --%>
					<th>
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','JYUCYU_NO','ASC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="JYUCYU_NO_ASC"><img src="./image/asc.gif" width="9px" height="9px" alt="<bean:message key="com.text.asc" />" title="<bean:message key="com.text.sortAsc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="JYUCYU_NO_ASC"><img src="./image/ascOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.ascOff" />" title="<bean:message key="com.text.sortAsc" />"></kit:notEqual>
							</a>
						</kit:present>
						<bean:message key="com.text.juchuNo" />
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','JYUCYU_NO','DESC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="JYUCYU_NO_DESC"><img src="./image/desc.gif" width="9px" height="9px" alt="<bean:message key="com.text.desc" />" title="<bean:message key="com.text.sortDesc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="JYUCYU_NO_DESC"><img src="./image/descOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.descOff" />" title="<bean:message key="com.text.sortDesc" />"></kit:notEqual>
							</a>
						</kit:present>
					</th>
					<%-- ﾃﾞｰﾀ種別 --%>
					<th>
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','SYUBETU_CD','ASC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="SYUBETU_CD_ASC"><img src="./image/asc.gif" width="9px" height="9px" alt="<bean:message key="com.text.asc" />" title="<bean:message key="com.text.sortAsc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="SYUBETU_CD_ASC"><img src="./image/ascOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.ascOff" />" title="<bean:message key="com.text.sortAsc" />"></kit:notEqual>
							</a>
						</kit:present>
						<bean:message key="com.text.dataSyubetu" />
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','SYUBETU_CD','DESC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="SYUBETU_CD_DESC"><img src="./image/desc.gif" width="9px" height="9px" alt="<bean:message key="com.text.desc" />" title="<bean:message key="com.text.sortDesc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="SYUBETU_CD_DESC"><img src="./image/descOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.descOff" />" title="<bean:message key="com.text.sortDesc" />"></kit:notEqual>
							</a>
						</kit:present>
					</th>
					<%-- 利用区分 --%>
					<th>
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','RIYOU_KBN','ASC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="RIYOU_KBN_ASC"><img src="./image/asc.gif" width="9px" height="9px" alt="<bean:message key="com.text.asc" />" title="<bean:message key="com.text.sortAsc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="RIYOU_KBN_ASC"><img src="./image/ascOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.ascOff" />" title="<bean:message key="com.text.sortAsc" />"></kit:notEqual>
							</a>
						</kit:present>
						<bean:message key="com.text.riyouKbn" />
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','RIYOU_KBN','DESC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="RIYOU_KBN_DESC"><img src="./image/desc.gif" width="9px" height="9px" alt="<bean:message key="com.text.desc" />" title="<bean:message key="com.text.sortDesc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="RIYOU_KBN_DESC"><img src="./image/descOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.descOff" />" title="<bean:message key="com.text.sortDesc" />"></kit:notEqual>
							</a>
						</kit:present>
					</th>
					<%-- 更新日時 --%>
					<th>
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','KOUSIN_DT','ASC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="KOUSIN_DT_ASC"><img src="./image/asc.gif" width="9px" height="9px" alt="<bean:message key="com.text.asc" />" title="<bean:message key="com.text.sortAsc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="KOUSIN_DT_ASC"><img src="./image/ascOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.ascOff" />" title="<bean:message key="com.text.sortAsc" />"></kit:notEqual>
							</a>
						</kit:present>
						<bean:message key="com.text.kousinDate" />
						<kit:present name="kuracKuraChokuDataIn_SearchedList" property="totalCount">
							<a href="javascript:sortList('kuracKuraChokuDataIn_Search','KOUSIN_DT','DESC')">
								<kit:equal name="kuracKuraChokuDataIn_Search" property="sortFlag" value="KOUSIN_DT_DESC"><img src="./image/desc.gif" width="9px" height="9px" alt="<bean:message key="com.text.desc" />" title="<bean:message key="com.text.sortDesc" />"></kit:equal>
								<kit:notEqual name="kuracKuraChokuDataIn_Search" property="sortFlag" value="KOUSIN_DT_DESC"><img src="./image/descOff.gif" width="9px" height="9px" alt="<bean:message key="com.text.descOff" />" title="<bean:message key="com.text.sortDesc" />"></kit:notEqual>
							</a>
						</kit:present>
					</th>
				</tr>
			</table>
			<%-- レコード行 --%>
			<div id="listDetails" class="">
				<table id="detailsTable" class="fixedTitleTable list" style="margin-top:-1pt">
					<%-- 列幅グループ化指定 --%>
					<colgroup class="mousikomiNoWidth"></colgroup> <%-- 申込受付No --%>
					<colgroup class="hasoyoteibiWidth"></colgroup> <%-- 発送予定日 --%>
					<colgroup class="irainusiWidth"></colgroup> <%-- 依頼主 --%>
					<colgroup class="tdkskWidth"></colgroup> <%-- 届け先 --%>
					<colgroup class="syuhantenNmWidth"></colgroup> <%-- 酒販店名 --%>
					<colgroup class="mousikomibiWidth"></colgroup> <%-- 申込日 --%>
					<colgroup class="jyucyuNoWidth"></colgroup> <%-- 受注No. --%>
					<colgroup class="syubetuCdWidth"></colgroup> <%-- ﾃﾞｰﾀ種別 --%>
					<colgroup class="riyouKbnWidth"></colgroup> <%-- 利用区分 --%>
					<colgroup class="kousinDtWidth"></colgroup> <%-- 更新日時 --%>

					<%-- レコード行 --%>
					<kit:iterate name="kuracKuraChokuDataIn_SearchedList"
						id="kuracKuraChokuDataInRecord" property="pageList" indexId="idx"
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
						<kit:tr name="kuracKuraChokuDataInRecord" errorProperty="hasError" id="id"
							modProperty="isModified" evenClass="even" oddClass="odd"
							errorClass="error" modClass="modified" indexed="true" styleClass="line"
							onmouseover="setRowMouseOver(this)" onmouseout="setRowMouseOut(this)"
							onclick="setRowKeys(this, 'kuracKuraChokuDataIn_Edit', 'kuradataNo', 'kuradataNo')"
							ondblclick="selectClick('kuracKuraChokuDataIn_Edit','btnConfirm')">

							<%-- 申込受付No --%>
							<td class="stView">
								<kit:write name="kuracKuraChokuDataInRecord" property="mousikomiNo" />
							</td>
							<%-- 発送予定日 --%>
							<td class="dtView">
								<kit:write name="kuracKuraChokuDataInRecord" property="hasoYoteiDt" format="yyyy/mm/dd" />
						 	</td>
							<%-- 依頼主 --%>
							<td class="mjView irainusiWidthWrap doWrap">
								<kit:write name="kuracKuraChokuDataInRecord" property="irainusiNm" />
						 	</td>
							<%-- 届け先 --%>
							<td class="mjView tdkskWidthWrap doWrap">
								<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiNm" />
						 	</td>
							<%-- 酒販店名 --%>
							<td class="mjView syuhantenNmWidthWrap doWrap">
								<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenNm" />
						 	</td>
							<%-- 申込日 --%>
							<td class="dtView">
								<kit:write name="kuracKuraChokuDataInRecord" property="uketukeDt" format="yyyy/mm/dd" />
						 	</td>
							<%-- 受注No. --%>
							<td class="cdnView">
								<kit:write name="kuracKuraChokuDataInRecord" property="jyucyuNo" />
							</td>
							<%-- ﾃﾞｰﾀ種別 --%>
							<td class="stView">
								<kit:write name="kuracKuraChokuDataInRecord" property="syubetuCd" ignore="true"
						 		    decodeList="kuracKuraChokuDataIn_KuracDataList" decodeWrite="label" />
							</td>
							<%-- 利用区分 --%>
							<td class="stView">
								<kit:write name="kuracKuraChokuDataInRecord" property="riyouKbn" ignore="true"
						 		    decodeList="kuracKuraChokuDataIn_RiyouKbnList" decodeWrite="label" />
							</td>
							<%-- 更新日時 --%>
							<td class="dtView">
								<kit:write name="kuracKuraChokuDataInRecord" property="kousinDt" format="yyyy/mm/dd HH:mm:ss" />
							</td>

						</kit:tr>
						<%-- 行選択時値取得用hidden要素 --%>
						<kit:hidden property="kuradataNo" indexed="true" name="kuracKuraChokuDataInRecord" />

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
						<%-- 蔵直ﾃﾞｰﾀ連番 --%>
						<kit:text name="kuracKuraChokuDataIn_Edit" property="kuradataNo" maxlength="8" size="14"
							styleClass="text ime_off  left frm_lpad"
							styleId="kuradataNoLbl" tabindex="1"
							errorProperty="kuradataNoClass" errorClass="error"
							onkeyup="nextTabMaxLenInput()" onblur="ctrlInput(FRM_CLASS_PAD,8,'0')" onfocus="ctrlInput()" />
						<%-- セットボタン --%>
						<kit:button property="btnConfirm"
							styleClass="btn_large" tabindex="2"
							onclick="if(eventPreSubmit()){setReqType('select');offUnloadConfirm();startWaiting();submit()}">
							<bean:message key="com.button.set" />
						</kit:button>

					</td>
					<td class="side">&nbsp;</td>
				</tr>
			</table>
		</div>
		<%-- 処理中表示 --%>
		<div id="viewWaiting" style="z-index:1;"></div>
	</kit:form>
	<%-- 照会・追加・更新・削除フォーム終了 --%>
	<hr />
	<%-- 共通フッター開始 --%>
	<%@ include file="/parts/comFooter.jsp"%>
	<%-- 共通フッター終了 --%>
</body>
</kit:html>
