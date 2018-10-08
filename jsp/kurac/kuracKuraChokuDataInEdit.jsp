<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page buffer="256kb" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-kit.tld" prefix="kit"%>
<%@ page import="fb.com.IKitComConst" %>
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
		HOME_OBJECT = document.kuracKuraChokuDataIn_Edit.elements["kuracKuraChokuDataInRecord[0].jigyosyoKbn"];
	}

	// 画面固有の明細部以外領域の高さ指定
	var othersHeight = 235;

	// ヘッダ固定
	//@@@ タイトル固定 ここから @@@@@@@@@@@@@@
	window.onresize = localResize;
	function localResize() {
		resize();
		setLocalHeight("list" ,"details" ,45);
	}
	//@@@ タイトル固定 ここまで @@@@@@@@@@@@@@

	// エラー時フォーカス用メソッド（ヘッダー部）
	function setFocusLocal() {

		// エラー時フラグ用配列
		var errorClassArr = new Array(
				"hacyuNoClass"
				,"kuradenNoClass"
				,"todokesakiLineNoClass"
				,"nosiKbnClass"
				,"nosiComment1Class"
				,"nosiComment2Class"
				,"nosiComment3Class"
				,"tatesnCdClass"
				,"syuhantenCdClass"
				,"unsotenCdClass"
				,"todokesakiNmClass"
				,"todokesakiTelClass"
				,"todokesakiZipClass"
				,"todokesakiAddressClass"
				,"irainusiNmClass"
				,"irainusiTelClass"
				,"irainusiAddressClass"
				,"irainusiZipClass"
		);

		//エラー時フォーカス用配列
		var errorObjArr = new Array(
				"hacyuNo"
				,"kuradenNo"
				,"todokesakiLineNo"
				,"nosiKbn"
				,"tatesnCd"
				,"syuhantenCd"
				,"unsotenCd"
				,"todokesakiNm"
				,"todokesakiTel"
				,"todokesakiZip"
				,"todokesakiAddress"
				,"irainusiNm"
				,"irainusiTel"
				,"irainusiAddress"
				,"irainusiZip"
		);

		// 明細部のフォーム名を指定する
		var formNm = "kuracKuraChokuDataIn_Edit";
		var recNm = "kuracKuraChokuDataInRecord";
		var form = document.forms[formNm];
		var maxSu = form.maxSu.value;
		setFocusForError(formNm, maxSu, errorClassArr, errorObjArr,recNm);
	}

	// エラー時フォーカス用メソッド（ディテール部）
	function setFocusLocalDt() {

		// エラー時フラグ用配列
		var errorClassArr = new Array(
				"shohinSetClass"
				,"hanbaiTankaClass"
				,"hanbaiGakuClass"
		);

		//エラー時フォーカス用配列
		var errorObjArr = new Array(
				"shohinSet"
				,"hanbaiTanka"
				,"hanbaiGaku"
		);

		// 明細部のフォーム名を指定する
		var formNm = "kuracKuraChokuDataIn_Edit";
		var recNm = "kuracKuraChokuDataInDtRecord";
		var form = document.forms[formNm];
		var maxSu = form.maxSu.value;
		setFocusForError(formNm, maxSu, errorClassArr, errorObjArr,recNm);
	}

	var oldJigyoshoKbn = null;
	// 事業所区分を変更した場合、明細情報を更新する
	function updateDtByJigyosho(obj) {
		var jigyosyoKbn = obj.value;
		var jigyosyoKbnInitVal =  document.forms['kuracKuraChokuDataIn_Edit'].elements['kuracKuraChokuDataInRecord[0].jigyosyoKbn'][1].value;

		if (oldJigyoshoKbn == null) {
			oldJigyoshoKbn = (jigyosyoKbnInitVal == "Y") ? "Y":"NY";
		}
		if (jigyosyoKbn == 'Y') {
			// 事業所区分がY以外に変更した場合
			if (oldJigyoshoKbn != 'Y') {
				// 明細情報を更新する
				updateDt();
			}
		} else {
			// 事業所区分がYに変更した場合
			if (oldJigyoshoKbn == 'Y') {
				// 明細情報を更新する
				updateDt();
			}
		}

		oldJigyoshoKbn = (jigyosyoKbn == "Y") ? "Y":"NY";
	}

	// 商品区分を変更した場合、明細情報を更新する
	function updateDt() {
		var formNm = "kuracKuraChokuDataIn_Edit";
		var form = document.forms[formNm];
		var recNm = "kuracKuraChokuDataInDtRecord";

		var jigyosyoKbnSelect = form.elements['kuracKuraChokuDataInRecord[0].jigyosyoKbn'];
		var jigyosyoKbn = jigyosyoKbnSelect[0].value;
		var shohinGrpSelect = form.elements['kuracKuraChokuDataInRecord[0].shoninGrpCd'];
		var shohinGrp = shohinGrpSelect[0].value;

		var maxSu = 20;
		var keyNm = "shohinGrp"
		var targetNms = "shohinCd,shohinNm,youkiKigoNm,shohinSet,hanbaiTanka,hanbaiGaku,kuradenLineNo,cpKbn";

		for (i = 0; i < maxSu; i++) {
			var elm = form.elements[recNm + "[" + i + "].shohinCd"];

			// 商品コードがある行
			if (elm.value != "") {
				// クリア
				clearValue(formNm, elm, targetNms);

				var elmSet = form.elements[recNm + "[" + i + "].shohinSet"];
				var elmTanka = form.elements[recNm + "[" + i + "].hanbaiTanka"];
				// 初期化
				elmSet.className = "text ime_off numeric right readonly";
				elmSet.readOnly = true;

				elmTanka.className = "text ime_off number right readonly";
				elmTanka.readOnly = true;
			}
		}

		keyValue = shohinGrp + "," + jigyosyoKbn;
		var url = "/kit/kuracKuraChokuDataIn_Ajax.do?objCd=" + keyValue + "&objNm=" + keyNm;
		CallAjax("changeDtInfo", url, form, elm, targetNms);
	}

	CallbackClass.prototype.changeDtInfo = function(res, form, keyObj, targetNm)
	{
		var formNm = "kuracKuraChokuDataIn_Edit";
		var form = document.forms[formNm];
		var recNm = "kuracKuraChokuDataInDtRecord";

		var xmlShohinList = res.getElementsByTagName("shohin");
		for (var i = 0; i < xmlShohinList.length; i++) {
			// 商品コード
			var shohinCd = xmlShohinList[i].getElementsByTagName("shohinCd")[0].firstChild.nodeValue;
			form.elements[recNm + "[" + i + "].shohinCd"].value = shohinCd;
			// 商品名
			var shohinNm = xmlShohinList[i].getElementsByTagName("shohinNm")[0].firstChild.nodeValue;
			form.elements[recNm + "[" + i + "].shohinNm"].value = shohinNm;
			// 容量名
			var youkiKigoNm = xmlShohinList[i].getElementsByTagName("youkiKigoNm")[0].firstChild.nodeValue;
			form.elements[recNm + "[" + i + "].youkiKigoNm"].value = youkiKigoNm;
			// 販売単価
			var hanbaiTanka = xmlShohinList[i].getElementsByTagName("hanbaiTanka")[0].firstChild.nodeValue;
			form.elements[recNm + "[" + i + "].hanbaiTanka"].value = hanbaiTanka;
			// 申込用紙表記順位
			var kuradenLineNo = xmlShohinList[i].getElementsByTagName("kuradenLineNo")[0].firstChild.nodeValue;
			form.elements[recNm + "[" + i + "].kuradenLineNo"].value = kuradenLineNo;

			// ｷｬﾝﾍﾟｰﾝ対象区分の値があった場合
			if (xmlShohinList[i].getElementsByTagName("cpKbn")[0].childNodes.length >= 1) {
				var cpKbn = xmlShohinList[i].getElementsByTagName("cpKbn")[0].firstChild.nodeValue;
				form.elements[recNm + "[" + i + "].cpKbn"].value = cpKbn;
			}

			var elmSet = form.elements[recNm + "[" + i + "].shohinSet"];
			var elmTanka = form.elements[recNm + "[" + i + "].hanbaiTanka"];

			// セット編集可になる
			elmSet.className = "text ime_off numeric right";
			elmSet.readOnly = false;
			// 単価編集可になる
			elmTanka.className = "text ime_off number right";
			elmTanka.readOnly = false;
		}
	}

	// 非同期通信（パラメータ設定）
	function getValueByAjax(keyObj, keyNm, targetNms) {
		var formNm = "kuracKuraChokuDataIn_Edit";
		var form = document.forms[formNm];
		var keyValue = "";
		if (keyNm == "kingaku") {
			   var elmSetName = (keyObj.name).replace(/\..*/, '.shohinSet');
			   var elmTankaName = (keyObj.name).replace(/\..*/, '.hanbaiTanka');
			   var setValue = form.elements[elmSetName].value;
			   var tankaValue = form.elements[elmTankaName].value.replace(/,/g,"");

			   keyValue = setValue + "," + tankaValue;

		} else {
			keyValue = form.elements[keyObj.name].value; // 呼び出し元キー
		}

		var url = "/kit/kuracKuraChokuDataIn_Ajax.do?objCd=" + keyValue + "&objNm=" + keyNm;

		getValueByCodeForAjax(url, formNm, keyObj, targetNms);
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

#listSelect{float:left; width:1260px}
.listLH{float:left;height:200px;}
.listS{float:left;height:450px;}

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
			setHomePosition();
			setFocusLocal();
			setFocusLocalDt();
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

		<table class="form">
			<tr>
				<td class="right">
					<%-- ポップアップ検索 --%>
					<kit:button styleClass="btn_search btn_large"
						property="btnPopupSearch"
						onclick="openModalSearch(this.form.kaisyaCd.value)">
						<bean:message key="com.button.knsk" />
					</kit:button>
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
		<kit:iterate id="kuracKuraChokuDataInRecord" name="kuracKuraChokuDataIn_EditList" property="pageList" indexId="idx" tabStartIndex="1" scope="session" entryModeLength="maxLineSu">
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
						<%-- 事業所区分 --%>
						<th class="jitem thMWidth"><bean:message key="com.cdnm.zigyousyoKb" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="jigyosyoKbnClass"
								styleClass="stView jdata tx10emWidth">
						<%-- ▼参照追加の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="reference">
							<kit:write name="kuracKuraChokuDataInRecord" property="jigyosyoKbnView" ignore="true"
								decodeList="kuracKuraChokuDataIn_KuracSitenList" decodeWrite="label" /><br/>
							<kit:select name="kuracKuraChokuDataInRecord" property="jigyosyoKbn"
								styleClass="list ime_off"
								styleId="jigyosyoKbnLbl" tabindex="true" indexed="true"
								onchange="updateDtByJigyosho(this)"
								onkeypress="ctrlSelection(this)">
								<kit:options collection="kuracKuraChokuDataIn_KuracSitenList" optional="false" useDefault="false" />
							</kit:select>
						</kit:equal>
						<%-- ▲参照追加の時▲ --%>
						<kit:notEqual name="kuracKuraChokuDataIn_Edit" property="mode" value="reference">
							<kit:write name="kuracKuraChokuDataInRecord" property="jigyosyoKbn" ignore="true"
								decodeList="kuracKuraChokuDataIn_KuracSitenList" decodeWrite="label" />
						</kit:notEqual>
						</kit:td>
						<%-- 商品区分 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.shohinKbn" /></th>
						<td class="stView jdata tx10emWidth">
						<%-- ▼参照追加の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="reference">
								<kit:write name="kuracKuraChokuDataInRecord" property="shoninGrpCdView" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracShohingrpList" decodeWrite="label" /><br/>
								<kit:select name="kuracKuraChokuDataInRecord" property="shoninGrpCd"
									styleClass="list ime_off"
									styleId="shoninGrpCdLbl" tabindex="true" indexed="true"
									onchange="updateDt()"
									onkeypress="ctrlSelection(this)">
									<kit:options collection="kuracKuraChokuDataIn_KuracShohingrpList" optional="false" useDefault="false" />
								</kit:select>
						</kit:equal>
						<%-- ▲参照追加の時▲ --%>
						<kit:notEqual name="kuracKuraChokuDataIn_Edit" property="mode" value="reference">
							<kit:write name="kuracKuraChokuDataInRecord" property="shoninGrpCd" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracShohingrpList" decodeWrite="label" />
						</kit:notEqual>
						</td>
						<%-- 届先No --%>
						<th class="jitem thMWidth"><bean:message key="com.text.tdkskNo" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="todokesakiLineNoClass"
								styleClass="stView jdata tx10emWidth">
							<%-- ▼参照追加の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="reference">
								<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiLineNoView" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracUploadFlgList" decodeWrite="label" /><br/>
							</kit:equal>
							<%-- ▲参照追加の時▲ --%>
							<%-- ▼新規の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="add||reference">
								<kit:select name="kuracKuraChokuDataInRecord" property="todokesakiLineNo"
									styleClass="list ime_off"
									styleId="todokesakiLineNoLbl" tabindex="true" indexed="true"
									onchange=""
									onkeypress="ctrlSelection(this)">
									<kit:options collection="kuracKuraChokuDataIn_KuracUploadFlgList" optional="false" useDefault="false" />
								</kit:select>
							</kit:equal>
							<%-- ▲新規の時▲ --%>
							<%-- ▼修正・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiLineNo" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracUploadFlgList" decodeWrite="label" />
							</kit:equal>
							<%-- ▲修正・訂正の時▲ --%>
						</kit:td>
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
							<%-- ▼新規・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="add||refPlus||refMinus">
								<kit:text name="kuracKuraChokuDataInRecord" property="uketukeDt" maxlength="8" size="20"
									styleClass="text ime_off right readonly" readonly="true"
									styleId="uketukeDtLbl" tabindex="false" indexed="true"
									format="yyyy/mm/dd" />
							</kit:equal>
							<%-- ▲新規・訂正の時▲ --%>
							<%-- ▼修正・参照追加の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference">
								<kit:write name="kuracKuraChokuDataInRecord" property="uketukeDtView" ignore="true" format="yyyy/mm/dd"/><br/>
								<kit:text name="kuracKuraChokuDataInRecord" property="uketukeDt" maxlength="8" size="20"
									styleClass="text ime_off date frm_dt right "
									styleId="uketukeDtLbl" tabindex="true" indexed="true"
									onkeyup="nextTabMaxLenInput()" onblur="ctrlInput()" onfocus="ctrlInput()"
									format="yyyy/mm/dd" />
							</kit:equal>
							<%-- ▲修正・参照追加の時▲ --%>
						</kit:td>
						<%-- 宅伝区分 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.takuhaiBillKbn" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="takuhaiBillKbnClass"
								styleClass="stView jdata tx10emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="takuhaiBillKbnView" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracBillList" decodeWrite="label" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
								<kit:select name="kuracKuraChokuDataInRecord" property="takuhaiBillKbn"
									styleClass="list ime_off"
									styleId="takuhaiBillKbnLbl" tabindex="true" indexed="true"
									onchange=""
									onkeypress="ctrlSelection(this)">
									<kit:options collection="kuracKuraChokuDataIn_KuracBillList" optional="false" useDefault="false" />
								</kit:select>
						</kit:td>
					</tr>
					<tr>
						<%-- 発注No --%>
						<th class="jitem thMWidth"><bean:message key="com.text.hacyuNo" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="hacyuNoClass"
								styleClass="cdxView jdata">
							<%-- ▼修正・参照追加の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference">
								<kit:write name="kuracKuraChokuDataInRecord" property="hacyuNoView" ignore="true"/><br/>
							</kit:equal>
							<%-- ▲修正・参照追加の時▲ --%>
							<%-- ▼新規・修正・参照の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="add||edit||reference">
							<kit:text name="kuracKuraChokuDataInRecord" property="hacyuNo" maxlength="10" size="20"
								styleClass="text ime_off left"
								styleId="hacyuNoLbl" tabindex="true" indexed="true"
								onkeyup="nextTabMaxLenInput()"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
							</kit:equal>
							<%-- ▲新規・修正・参照の時▲ --%>
							<%-- ▼訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="refPlus||refMinus">
							<kit:text name="kuracKuraChokuDataInRecord" property="hacyuNo" maxlength="10" size="20"
								styleClass="text ime_off left readonly"
								styleId="hacyuNoLbl" tabindex="false" indexed="true" readonly = "true"
								onkeyup="nextTabMaxLenInput()"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
							</kit:equal>
							<%-- ▲訂正の時▲ --%>
						</kit:td>
						<%--  発送予定日 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.hasoyoteibi" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="hasoYoteiDtClass"
								styleClass="dtView jdata">
							<%-- ▼新規の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="add">
								<kit:text name="kuracKuraChokuDataInRecord" property="hasoYoteiDt" maxlength="8" size="20"
									styleClass="text ime_off right readonly" readonly="true"
									styleId="uketukeDtLbl" tabindex="false" indexed="true"
									format="yyyy/mm/dd" />
							</kit:equal>
							<%-- ▲新規の時▲ --%>
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="hasoYoteiDtView" ignore="true" format="yyyy/mm/dd"/><br/>
								<kit:text name="kuracKuraChokuDataInRecord" property="hasoYoteiDt" maxlength="8" size="20"
									styleClass="text ime_off date frm_dt right "
									styleId="hasoYoteiDtLbl" tabindex="true" indexed="true"
									onkeyup="nextTabMaxLenInput()" onblur="ctrlInput()" onfocus="ctrlInput()"
									format="yyyy/mm/dd" />
							</kit:equal>
							<%-- ▲修正・参照追加の時▲ --%>
						</kit:td>
						<%--  酒販店チェックリスト --%>
						<th class="jitem thMWidth"><bean:message key="com.text.syuhantenChklst" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="checkListPrtKbnClass"
								styleClass="stView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="checkListPrtKbnView" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracChklistYnList" decodeWrite="label" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:select name="kuracKuraChokuDataInRecord" property="checkListPrtKbn"
								styleClass="list ime_off"
								styleId="checkListPrtKbnLbl" tabindex="true" indexed="true"
								onchange=""
								onkeypress="ctrlSelection(this)">
								<kit:options collection="kuracKuraChokuDataIn_KuracChklistYnList" optional="false" useDefault="false" />
							</kit:select>
						</kit:td>
					</tr>
					<tr>
						<%--  整理No --%>
						<th class="jitem thMWidth"><bean:message key="com.text.kuradenNo" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="kuradenNoClass"
								styleClass="cdxView jdata">
						<%-- ▼参照追加の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="reference">
							<kit:write name="kuracKuraChokuDataInRecord" property="kuradenNoView" ignore="true"/><br/>
						</kit:equal>
						<%-- ▲参照追加の時▲ --%>
						<%-- ▼修正・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||refPlus||refMinus">
							<kit:text name="kuracKuraChokuDataInRecord" property="kuradenNo" maxlength="4" size="8"
									styleClass="text ime_off left readonly" readonly = "true"
									styleId="kuradenNoLbl" tabindex="fase" indexed="true"
									onkeyup="nextTabMaxLenInput()"
									onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:equal>
						<%-- ▲修正・訂正の時▲ --%>
						<%-- ▼新規・参照追加の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="add||reference">
							<kit:text name="kuracKuraChokuDataInRecord" property="kuradenNo" maxlength="4" size="8"
									styleClass="text numeric ime_off left frm_lpad"
									styleId="kuradenNoLbl" tabindex="true" indexed="true"
									onkeyup="nextTabMaxLenInput()"
									onblur="ctrlInput(FRM_CLASS_PAD,4,'0');" onfocus="ctrlInput()" />
						</kit:equal>
						<%-- ▲修正・訂正以外の時▲ --%>
						</kit:td>
						<%--  運賃区分 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.unchinKbn" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="unchinKbnClass"
								styleClass="stView jdata">
							<%-- ▼修正・参照追加の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference">
								<kit:write name="kuracKuraChokuDataInRecord" property="unchinKbnView" ignore="true"
									decodeList="kuracKuraChokuDataIn_UntinList" decodeWrite="label" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加の時▲ --%>
							<%-- ▼訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="unchinKbn" ignore="true"
									decodeList="kuracKuraChokuDataIn_UntinList" decodeWrite="label" /><br/>
							</kit:equal>
							<%-- ▲訂正の時▲ --%>
							<%-- ▼新規・修正・参照追加の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="add||edit||reference">
								<kit:select name="kuracKuraChokuDataInRecord" property="unchinKbn"
									styleClass="list ime_off"
									styleId="unchinKbnLbl" tabindex="true" indexed="true"
									onchange=""
									onkeypress="ctrlSelection(this)">
									<kit:options collection="kuracKuraChokuDataIn_UntinList" optional="false" useDefault="false" />
								</kit:select>
							</kit:equal>
							<%-- ▲新規・修正・参照追加の時▲ --%>
						</kit:td>
						<%--  用途区分 --%>
						<th class="jitem thMWidth"><bean:message key="com.text.youtoKbn" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="youtoKbnClass"
								styleClass="stView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="youtoKbnView" ignore="true"
									decodeList="kuracKuraChokuDataIn_KuracNosiYoutoList" decodeWrite="label" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:select name="kuracKuraChokuDataInRecord" property="youtoKbn"
								styleClass="list ime_off"
								styleId="youtoKbnLbl" tabindex="true" indexed="true"
								onchange=""
								onkeypress="ctrlSelection(this)">
								<kit:options collection="kuracKuraChokuDataIn_KuracNosiYoutoList" optional="false" useDefault="false" />
							</kit:select>
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
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
						<kit:write name="kuracKuraChokuDataInRecord" property="nosiKbnView" ignore="true"
							decodeList="kuracKuraChokuDataIn_KuracNosiNameYnList" decodeWrite="label" /><br/>
							</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:select name="kuracKuraChokuDataInRecord" property="nosiKbn"
							styleClass="list ime_on"
							styleId="nosiKbnLbl" tabindex="true" indexed="true"
							onchange=""
							onkeypress="ctrlSelection(this)">
							<kit:options collection="kuracKuraChokuDataIn_KuracNosiNameYnList" optional="false" useDefault="false" />
						</kit:select>
					</kit:td>
					</tr>
					<tr>
						<%-- 記載位置(左) --%>
						<th class="jitem"><bean:message key="com.text.kinoichiLeft"/></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="nosiComment1Class"
									styleClass="mjView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="nosiComment1View" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="nosiComment1" maxlength="16" size="18"
								styleClass="text ime_on left"
								styleId="nosiComment1Lbl" tabindex="true" indexed="true"
								onkeyup="nextTabMaxLenInput()"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
					</tr>
					<tr>
						<%-- 記載位置(中) --%>
						<th class="jitem"><bean:message key="com.text.kinoichiMiddle"/></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="nosiComment2Class"
									styleClass="mjView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="nosiComment2View" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="nosiComment2" maxlength="16" size="18"
								styleClass="text ime_on left"
								styleId="nosiComment2Lbl" tabindex="true" indexed="true"
								onkeyup="nextTabMaxLenInput()"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
					</tr>
					<tr>
						<%-- 記載位置(右) --%>
						<th class="jitem"><bean:message key="com.text.kinoichiRight"/></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="nosiComment3Class"
									styleClass="mjView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="nosiComment3View" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="nosiComment3" maxlength="16" size="18"
								styleClass="text ime_on left"
								styleId="nosiComment3Lbl" tabindex="true" indexed="true"
								onkeyup="nextTabMaxLenInput()"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
					</tr>
				</table>
				</div>

				<br/>
				<%-- ★縦線情報１段目★ --%>
				<table class="jheader" >
					<tr>
						<%--  縦線CD --%>
						<th class="jitem thXSWidth"><bean:message key="com.text.tatesenCd" /></th>
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
							<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"
									styleClass="cdnView jdata tx5emWidth">
									<kit:write name="kuracKuraChokuDataInRecord" property="tatesnCdView" ignore="true" />
							</kit:td>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"
								styleClass="cdnView jdata tx5emWidth">
							<kit:text name="kuracKuraChokuDataInRecord" property="tatesnCd"
								maxlength="8" size="10" styleClass="text ime_off code frm_lpad right searchable"
								styleId="tatesnCdLbl" tabindex="true" indexed="true"
								onkeyup="nextTabMaxLenInput()"
								onblur="ctrlInput(FRM_CLASS_PAD,8,'0');setRefObjSyuka('popup','orsTatesnCd',this,'orositenCd1Jiten','tokuyakutenCd','orositenNm1JitenRyaku','tokuyakutenNm',
								'orositenCdDepo','depoCd','orositenNmDepoRyaku','depoNm','orositenCd2Jiten','nijitenCd','orositenNm2JitenRyaku','nijitenNm',
								'orositenCd3Jiten','sanjitenCd','orositenNm3JitenRyaku','sanjitenNm');
								getValueByAjax(this,'tatesenCd','tokuyakutenCd,tokuyakutenNm,depoCd,depoNm,nijitenCd,nijitenNm,sanjitenCd,sanjitenNm');"
								onfocus="ctrlInput();setPreValueForAjax(this);" />
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
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="tokuyakutenCdView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="tokuyakutenCd" maxlength="6" size="8"
								styleClass="text ime_off right readonly" styleId="tokuyakutenCdLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
						<%-- 特約店名(略) --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"  styleClass="mjView jdata tx12emWidth">
							<%-- ▼修正・参照追加Dの時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="tokuyakutenNmView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="tokuyakutenNm" maxlength="24" size="26"
								styleClass="text ime_on left readonly" styleId="tokuyakutenNmLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
						<%-- デポ店 --%>
						<th class="jitem thXSWidth"><bean:message key="com.text.depoten" /></th>
						<%-- デポ店CD --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass" styleClass="cdnView jdata tx5emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="depoCdView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="depoCd" maxlength="6" size="10"
								styleClass="text ime_off right readonly" styleId="depoCdLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
						<%-- デポ店名(略) --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"  styleClass="mjView jdata tx12emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="depoNmView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="depoNm" maxlength="24" size="26"
								styleClass="text ime_on left readonly" styleId="depoNmLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
						<%-- 二次店 --%>
						<th class="jitem thXSWidth"><bean:message key="com.text.2jiten" /></th>
						<%-- 二次店CD --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"  styleClass="cdnView jdata tx5emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="nijitenCdView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="nijitenCd" maxlength="6" size="10"
								styleClass="text ime_off right readonly" styleId="nijitenCdLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
						<%-- 二次店(略) --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"  styleClass="mjView jdata tx12emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="nijitenNmView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="nijitenNm" maxlength="24" size="26"
								styleClass="text ime_on left readonly" styleId="nijitenNmLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
						<%--三次店 --%>
						<th class="jitem thXSWidth"><bean:message key="com.text.3jiten" /></th>
						<%-- 三次店CD --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"  styleClass="cdnView jdata tx5emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="sanjitenCdView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="sanjitenCd" maxlength="6" size="10"
								styleClass="text ime_off right readonly" styleId="sanjitenCdLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
						<%-- 三次店名(略) --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="tatesnCdClass"  styleClass="mjView jdata tx12emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="sanjitenNmView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="sanjitenNm" maxlength="24" size="26"
								styleClass="text ime_on left readonly" styleId="sanjitenNmLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
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
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
							<kit:td name="kuracKuraChokuDataInRecord" property="syuhantenCdClass"
								styleClass="cdnView jdata tx5emWidth">
								<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenCdView" ignore="true" />
							</kit:td>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="syuhantenCdClass"
							styleClass="cdnView jdata tx5emWidth">
							<kit:text name="kuracKuraChokuDataInRecord"
								property="syuhantenCd" maxlength="8" size="10"
								styleClass="text ime_off code frm_lpad right searchable" styleId="syuhantenCdLbl"
								tabindex="true" indexed="true" onkeyup="nextTabMaxLenInput()"
								onblur="ctrlInput(FRM_CLASS_PAD ,8 ,'0');
									setRefObjAll('popup','syuhantenCd',this,'syuhantenNm','syuhantenNm','tel','syuhantenTel','address','syuhantenAddress','zip','syuhantenZip');
									getValueByAjax(this,'syuhantenCd','syuhantenNm,syuhantenTel,syuhantenAddress,syuhantenZip');"
								onfocus="ctrlInput();setPreValueForAjax(this);" />
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
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenNmView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="syuhantenNm" maxlength="40" size="50"
								styleClass="text ime_on left readonly" styleId="syuhantenNmLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
						<%-- TEL --%>
						<th class="jitem thSWidth"><bean:message key="com.text.tel" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="syuhantenCdClass" styleClass="mjView jdata tx14emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenTelView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="syuhantenTel" maxlength="14" size="20"
								styleClass="text ime_on left readonly" styleId="syuhantenTelLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
					</tr>
					<tr>
						<%-- 住所 --%>
						<th class="jitem thSWidth"><bean:message key="com.text.jyusyo" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="syuhantenCdClass" styleClass="mjView jdata ">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenAddressView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="syuhantenAddress" maxlength="60" size="70"
								styleClass="text ime_on left readonly" styleId="syuhantenAddressLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
						<%-- 郵便番号 --%>
						<th class="jitem thSWidth"><bean:message key="com.text.postalCd" /></th>
						<kit:td name="kuracKuraChokuDataInRecord" property="syuhantenCdClass" styleClass="mjView jdata">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="syuhantenZipView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="syuhantenZip" maxlength="10" size="12"
								styleClass="text ime_on left readonly" styleId="syuhantenZipLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
					</tr>
				</table>
			</div>

			<div id="listT" style = "margin-top:1em;" >
				<%-- ★運送店情報1段目★ --%>
				<table class="jheader">
					<tr>
						<%-- 運送店CD --%>
						<th class="jitem thSWidth"><bean:message key="com.text.unsotenCd" /></th>
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
							<kit:td name="kuracKuraChokuDataInRecord" property="unsotenCdClass"
								styleClass="cdnView jdata tx4emWidth">
								<kit:write name="kuracKuraChokuDataInRecord" property="unsotenCdView" ignore="true" />
							</kit:td>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:td name="kuracKuraChokuDataInRecord" property="unsotenCdClass"
								styleClass="cdnView jdata tx4emWidth">
								<kit:text name="kuracKuraChokuDataInRecord" property="unsotenCd"
									maxlength="4" size="6" styleClass="text ime_off code frm_lpad right searchable"
									styleId="unsotenCdLbl" tabindex="true" indexed="true"
									onkeyup="nextTabMaxLenInput()"
									onblur="ctrlInput(FRM_CLASS_PAD ,4 ,'0');setRefObjAll('popup','unsotenCd',this,'unsotenNm','unsotenNm');
											getValueByAjax(this,'unsotenCd','unsotenNm');"
									onfocus="ctrlInput();setPreValueForAjax(this);" />
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
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="unsotenNmView" ignore="true" /><br>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInRecord" property="unsotenNm" maxlength="40" size="50"
								styleClass="text ime_on left readonly" styleId="unsotenNmLbl"
								readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</kit:td>
					</tr>
				</table>
			</div>

			<table class="jheader" style="float:left">
				<tr>
					<%-- 届け先 --%>
					<th class="jitem thSWidth" rowspan = "2"><bean:message key="com.text.tdksk" /></th>
					<%-- 氏名 --%>
					<th class="jitem thSWidth"><bean:message key="com.text.shimei" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="todokesakiNmClass" styleClass="mjView jdata tx42emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiNmView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:text name="kuracKuraChokuDataInRecord" property="todokesakiNm" maxlength="28" size="60"
							styleClass="text ime_on left"
							styleId="todokesakiNmLbl" tabindex="true" indexed="true"
							onkeyup="nextTabMaxLenInput()"
							onblur="ctrlInput()" onfocus="ctrlInput()" />
					</kit:td>
					<%-- TEL --%>
					<th class="jitem thSWidth"><bean:message key="com.text.tel" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="todokesakiTelClass" styleClass="mjView jdata tx14emWidth">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiTelView" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:text name="kuracKuraChokuDataInRecord" property="todokesakiTel" maxlength="14" size="20"
							styleClass="text ime_off left"
							styleId="todokesakiTelLbl" tabindex="true" indexed="true"
							onkeyup="nextTabMaxLenInput()"
							onblur="ctrlInput()" onfocus="ctrlInput()" />
					</kit:td>
				</tr>
				<tr>
					<%-- 住所 --%>
					<th class="jitem thSWidth"><bean:message key="com.text.jyusyo" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="todokesakiAddressClass" styleClass="mjView jdata tx42emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiAddressView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:text name="kuracKuraChokuDataInRecord" property="todokesakiAddress" maxlength="42" size="90"
							styleClass="text ime_on left"
							styleId="todokesakiAddressLbl" tabindex="true" indexed="true"
							onkeyup="nextTabMaxLenInput()"
							onblur="ctrlInput()" onfocus="ctrlInput()" />
					</kit:td>
					<%-- 郵便番号 --%>
					<th class="jitem thSWidth"><bean:message key="com.text.postalCd" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="todokesakiZipClass" styleClass="mjView jdata tx14emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="todokesakiZipView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:text name="kuracKuraChokuDataInRecord" property="todokesakiZip" maxlength="8" size="12"
							styleClass="text ime_off left"
							styleId="todokesakiZipLbl" tabindex="true" indexed="true"
							onkeyup="nextTabMaxLenInput()"
							onblur="ctrlInput()" onfocus="ctrlInput()" />
					</kit:td>
				</tr>
				<tr>
					<%-- 依頼主 --%>
					<th class="jitem thSWidth" rowspan = "2"><bean:message key="com.text.irainusi" /></th>
					<%-- 氏名 --%>
					<th class="jitem thSWidth"><bean:message key="com.text.shimei" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="irainusiNmClass" styleClass="mjView jdata tx42emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="irainusiNmView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:text name="kuracKuraChokuDataInRecord" property="irainusiNm" maxlength="28" size="60"
							styleClass="text ime_on left"
							styleId="irainusiNmLbl" tabindex="true" indexed="true"
							onkeyup="nextTabMaxLenInput()"
							onblur="ctrlInput()" onfocus="ctrlInput()" />
					</kit:td>
					<%-- TEL --%>
					<th class="jitem thSWidth"><bean:message key="com.text.tel" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="irainusiTelClass" styleClass="mjView jdata tx14emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="irainusiTelView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:text name="kuracKuraChokuDataInRecord" property="irainusiTel" maxlength="14" size="20"
							styleClass="text ime_off left"
							styleId="irainusiTelLbl" tabindex="true" indexed="true"
							onkeyup="nextTabMaxLenInput()"
							onblur="ctrlInput()" onfocus="ctrlInput()" />
					</kit:td>
				</tr>
				<tr>
					<%-- 住所 --%>
					<th class="jitem thSWidth"><bean:message key="com.text.jyusyo" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="irainusiAddressClass" styleClass="mjView jdata tx42emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="irainusiAddressView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:text name="kuracKuraChokuDataInRecord" property="irainusiAddress" maxlength="42" size="90"
							styleClass="text ime_on left"
							styleId="irainusiAddressLbl" tabindex="true" indexed="true"
							onkeyup="nextTabMaxLenInput()"
							onblur="ctrlInput()" onfocus="ctrlInput()" />
					</kit:td>
					<%-- 郵便番号 --%>
					<th class="jitem thSWidth"><bean:message key="com.text.postalCd" /></th>
					<kit:td name="kuracKuraChokuDataInRecord" property="irainusiZipClass" styleClass="mjView jdata tx14emWidth">
						<%-- ▼修正・参照追加・訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
							<kit:write name="kuracKuraChokuDataInRecord" property="irainusiZipView" ignore="true" /><br/>
						</kit:equal>
						<%-- ▲修正・参照追加・訂正の時▲ --%>
						<kit:text name="kuracKuraChokuDataInRecord" property="irainusiZip" maxlength="8" size="12"
							styleClass="text ime_off left"
							styleId="irainusiZipLbl" tabindex="true" indexed="true"
							onkeyup="nextTabMaxLenInput()"
							onblur="ctrlInput()" onfocus="ctrlInput()" />
					</kit:td>
				</tr>
				</table>

			</div>

			<hr style="border:0px; margin:0px;">

			<%-- レコードのhidden要素（ヘッダー部） --%>
			<kit:hidden property="jigyosyoKbn" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="shoninGrpCd" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="todokesakiLineNo" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="unchinKbn" indexed="true" name="kuracKuraChokuDataInRecord" />

			<%-- エラー時フォーカス設定用hidden要素（ヘッダー部） --%>
			<kit:hidden property="hacyuNoClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="kuradenNoClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="todokesakiLineNoClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="nosikbnClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="nosiComment1Class" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="nosiComment2Class" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="nosiComment3Class" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="tatesnCdClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="syuhantenCdClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="unsotenCdClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="todokesakiNmClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="todokesakiTelClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="todokesakiAddressClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="todokesakiZipClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="irainusiNmClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="irainusiTelClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="irainusiAddressClass" indexed="true" name="kuracKuraChokuDataInRecord" />
			<kit:hidden property="irainusiZipClass" indexed="true" name="kuracKuraChokuDataInRecord" />

			<%-- ここまで エラー時フォーカス設定用（ヘッダー部） --%>

		</kit:iterate>
		<br/>

		<%-- ディテール部 --%>
		<div id="listSelect" class="fixedTitleX" >
			<table class="fixedTitleTableSelect list" style="margin:0;">
				<colgroup class="noWidth"></colgroup> <%-- No --%>
				<colgroup class="syohinCdWidth"></colgroup> <%-- 商品CD --%>
				<colgroup class="syohinNmWidth"></colgroup> <%-- 商品名 --%>
				<colgroup class="yokiNmWidth"></colgroup> <%-- 容器名 --%>
				<colgroup class="setSuWidth"></colgroup> <%-- セット数 --%>
				<colgroup class="hannbaitankaWidth"></colgroup> <%-- 販売単価 --%>
				<colgroup class="hannbaigakuWidth"></colgroup> <%-- 販売額 --%>
				<%-- ▼訂正の時▼ --%>
				<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="refPlus||refMinus">
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
				<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="refPlus||refMinus">
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
			<table class="fixedTitleTableSelect list" style="margin-top:-1px;">
				<colgroup class="noWidth"></colgroup> <%-- No --%>
				<colgroup class="syohinCdWidth"></colgroup> <%-- 商品CD --%>
				<colgroup class="syohinNmWidth"></colgroup> <%-- 商品名 --%>
				<colgroup class="yokiNmWidth"></colgroup> <%-- 容器名 --%>
				<colgroup class="setSuWidth"></colgroup> <%-- セット数 --%>
				<colgroup class="hannbaitankaWidth"></colgroup> <%-- 販売単価 --%>
				<colgroup class="hannbaigakuWidth"></colgroup> <%-- 販売額 --%>
				<%-- ▼訂正の時▼ --%>
				<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="refPlus||refMinus">
					<colgroup class="syukadenNoWidth"></colgroup> <%-- 出荷伝票No --%>
					<colgroup class="teiseiUridenNoWidth"></colgroup> <%-- 訂正元伝票No --%>
					<colgroup class="teiseiSyukaDtWidth"></colgroup> <%-- 訂正元伝日付 --%>
				</kit:equal>
				<%-- ▲訂正の時▲ --%>
			<tbody>
				<%-- リスト開始 --%>
				<bean:define id="tabStartIdx" value="23" />
				<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="reference">
				<bean:define id="tabStartIdx" value="27" />
				</kit:equal>
				<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="refPlus||refMinus">
				<bean:define id="tabStartIdx" value="20" />
				</kit:equal>
				<kit:iterate id="kuracKuraChokuDataInDtRecord"
					name="kuracKuraChokuDataIn_EditDtList" property="pageList"
					indexId="idx" entryModeLength="maxLineSu1" tabStartIndex="<%=tabStartIdx %>" scope="session" >
					<kit:tr>
						<%-- No. --%>
						<td class="nmView">
							<%= idx+1 %>
							<kit:hidden property="idxNo" indexed="true" name="kuracKuraChokuDataInDtRecord" value = "<%= String.valueOf(idx+1) %>"/>
						</td>
						<%-- 商品CD --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="shohinCdClass"
							styleClass="cdnView">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="shohinCdView" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInDtRecord" property="shohinCd" maxlength="7" size="7"
								styleClass="text ime_off right readonly"
								styleId="shohinCdLbl" readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()"/>
						</kit:td>
						<%-- 商品名 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="shohinCdClass"
							styleClass="mjView syohinNmWidthWrap doWrap">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="shohinNmView" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInDtRecord" property="shohinNm" maxlength="60" size="55"
								styleClass="text ime_off left readonly"
								styleId="shohinNmLbl" readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()"/>
						</kit:td>
						<%-- 容器名 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="shohinCdClass"
							styleClass="mjView">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="youkiKigoNmView" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInDtRecord" property="youkiKigoNm" maxlength="6" size="10"
								styleClass="text ime_off left readonly"
								styleId="youkiKigoNmLbl" readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()"/>
						</kit:td>
						<%--  ｾｯﾄ数 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="shohinSetClass"
							styleClass="nmView">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="shohinSetView" ignore="true" /><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<%-- ▼商品コードが存在した場合▼ --%>
							<kit:notEqual name="kuracKuraChokuDataInDtRecord" property="shohinCd" value="">
								<%-- ▼訂正(-)の時▼ --%>
								<kit:notEqual name="kuracKuraChokuDataIn_Edit" property="mode" value="refMinus">
									<kit:text name="kuracKuraChokuDataInDtRecord" property="shohinSet" maxlength="3" size="6"
										styleClass="text ime_off numeric right"
										styleId="shohinSetLbl" readonly="false" tabindex="true" indexed="true"
										onkeyup="nextTabMaxLenInput()"
										onblur="ctrlInput();getValueByAjax(this,'kingaku','hanbaiGaku');"
										onfocus="ctrlInput();setPreValueForAjax(this)"/>
								</kit:notEqual>
								<%-- ▲訂正(-)の時▲ --%>
								<%-- ▼訂正(-)以外の時▼ --%>
								<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="refMinus">
									<kit:text name="kuracKuraChokuDataInDtRecord" property="shohinSet" maxlength="4" size="6"
										styleClass="text ime_off right"
										styleId="shohinSetLbl" readonly="false" tabindex="true" indexed="true"
										onkeyup="nextTabMaxLenInput()"
										onblur="ctrlInput();getValueByAjax(this,'kingaku','hanbaiGaku');"
										onfocus="ctrlInput();setPreValueForAjax(this)"/>
								</kit:equal>
								<%-- ▲訂正(-)以外の時▲ --%>
							</kit:notEqual>
							<%-- ▼商品コードが存在しない場合▼ --%>
							<kit:equal name="kuracKuraChokuDataInDtRecord" property="shohinCd" value="">
								<kit:text name="kuracKuraChokuDataInDtRecord" property="shohinSet" maxlength="6" size="6"
									styleClass="text ime_off numeric right readonly"
									styleId="shohinSetLbl" readonly="true" tabindex="true" indexed="true"
									onkeyup="nextTabMaxLenInput()" onblur="ctrlInput();getValueByAjax(this,'kingaku','hanbaiGaku');" onfocus="ctrlInput();"/>
							</kit:equal>
						</kit:td>
						<%-- 販売単価 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="hanbaiTankaClass"
							styleClass="nmView">
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="hanbaiTankaView" ignore="true" format="99,999,999.99"/><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<%-- ▼商品コードが存在した場合▼ --%>
							<kit:notEqual name="kuracKuraChokuDataInDtRecord" property="shohinCd" value="">
								<kit:text name="kuracKuraChokuDataInDtRecord" property="hanbaiTanka" maxlength="11" size="12"
									styleClass="text ime_off number right"
									styleId="hanbaiTankaLbl" readonly="false" tabindex="true" indexed="true"
									onkeyup="nextTabMaxLenInput()"
									onblur="ctrlInput(FRM_CLASS_DECIMAL, 8, 2);getValueByAjax(this,'kingaku','hanbaiGaku');"
									onfocus="ctrlInput();setPreValueForAjax(this)" format="99999999.99"/>
							</kit:notEqual>
							<%-- ▼商品コードが存在しない場合▼ --%>
							<kit:equal name="kuracKuraChokuDataInDtRecord" property="shohinCd" value="">
								<kit:text name="kuracKuraChokuDataInDtRecord" property="hanbaiTanka" maxlength="11" size="12"
									styleClass="text ime_off number right readonly"
									styleId="hanbaiTankaLbl" readonly="true" tabindex="true" indexed="true"
									onkeyup="nextTabMaxLenInput()" onblur="ctrlInput(FRM_CLASS_DECIMAL, 8, 2);getValueByAjax(this,'kingaku','hanbaiGaku');"  onfocus="ctrlInput()" format="99,999,999.99"/>
							</kit:equal>
						</kit:td>
						<%-- 販売額 --%>
						<kit:td name="kuracKuraChokuDataInDtRecord" property="hanbaiGakuClass"
							styleClass="nmView" >
							<%-- ▼修正・参照追加・訂正の時▼ --%>
							<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="edit||reference||refPlus||refMinus">
								<kit:write name="kuracKuraChokuDataInDtRecord" property="hanbaiGakuView" ignore="true" format="9,999,999,999.99"/><br/>
							</kit:equal>
							<%-- ▲修正・参照追加・訂正の時▲ --%>
							<kit:text name="kuracKuraChokuDataInDtRecord" property="hanbaiGaku" maxlength="12" size="14"
								styleClass="text ime_off number right readonly"
								styleId="hanbaiGakuLbl" readonly="true" tabindex="false" indexed="true"
								onblur="ctrlInput()" onfocus="ctrlInput()" format="9,999,999,999.99"/>
						</kit:td>
						<%-- ▼訂正の時▼ --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="refPlus||refMinus">
								<%-- 出荷伝票No--%>
								<td class="cdxView">
									<kit:write name="kuracKuraChokuDataInDtRecord" property="syukadenNoView" ignore="true" /><br/>
									<kit:text name="kuracKuraChokuDataInDtRecord" property="syukadenNo" maxlength="6" size="10"
										styleClass="text ime_off left readonly"
										styleId="syukadenNoLbl" readonly="true" tabindex="false" indexed="true"
										onblur="ctrlInput()" onfocus="ctrlInput()"/>
								</td>
								<%-- 訂正元伝票No --%>
								<td class="cdxView">
									<kit:write name="kuracKuraChokuDataInDtRecord" property="teiseiUridenNoView" ignore="true" /><br/>
									<kit:text name="kuracKuraChokuDataInDtRecord" property="teiseiUridenNo" maxlength="6" size="10"
										styleClass="text ime_off left readonly"
										styleId="teiseiUridenNoLbl" readonly="true" tabindex="false" indexed="true"
										onblur="ctrlInput()" onfocus="ctrlInput()"/>
								</td>
								<%-- 訂正元伝日付 --%>
								<td class="dtView">
									<kit:write name="kuracKuraChokuDataInDtRecord" property="teiseiSyukaDtView" ignore="true" format="yyyy/mm/dd"/><br/>
									<kit:text name="kuracKuraChokuDataInDtRecord" property="teiseiSyukaDt" maxlength="10" size="14"
										styleClass="text ime_off left readonly"
										styleId="teiseiSyukaDtLbl" readonly="true" tabindex="false" indexed="true"
										onblur="ctrlInput()" onfocus="ctrlInput()" format="yyyy/mm/dd"/>
								</td>
						</kit:equal>
						<%-- ▲訂正の時▲ --%>
					</kit:tr>
				<%-- レコードのhidden要素（ディテール部） --%>
				<kit:hidden property="kuradenLineNo" indexed="true" name="kuracKuraChokuDataInDtRecord" />
				<kit:hidden property="cpKbn" indexed="true" name="kuracKuraChokuDataInDtRecord" />
				<kit:hidden property="shohinCdView" indexed="true" name="kuracKuraChokuDataInDtRecord" />

				<%-- エラー時フォーカス設定用hidden要素（ディテール部） --%>
				<kit:hidden property="shohinSetClass" indexed="true" name="kuracKuraChokuDataInDtRecord" />
				<kit:hidden property="hanbaiTankaClass" indexed="true" name="kuracKuraChokuDataInDtRecord" />
				<kit:hidden property="hanbaiGakuClass" indexed="true" name="kuracKuraChokuDataInDtRecord" />

				<%-- ここまで エラー時フォーカス設定用（ディテール部） --%>
				</kit:iterate>
				<%-- リスト終了 --%>
			</tbody>
			</table>
		</div>
	</div>
		<div class="list_bottom">
			<table id="footButtons">
				<tr>
					<td class="center">
						<%-- 確認ボタン --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="add||edit||reference||delete||refPlus||refMinus" scope="request" >
							<kit:button styleClass="btn_large" property="btnConfirm"
								name="kuracKuraChokuDataIn_Edit" tabIndexProperty="tabIndexConfirm"
								onclick="if(eventPreSubmit()){setReqType('confirm');offUnloadConfirm();submit()}">
								<bean:message key="com.button.confirm" />
							</kit:button>
						</kit:equal>

						<%-- 確定ボタン --%>
						<kit:equal name="kuracKuraChokuDataIn_Edit" property="mode" value="confirm||delete" scope="request" >
							<kit:button styleClass="btn_large" property="btnCommit"
								name="kuracKuraChokuDataIn_Edit" tabIndexProperty="tabIndexCommit"
								onclick="if(confirmCommit(this)){
									if(eventPreSubmit()){
										if(confirmSubmit()){setReqType('commit');offUnloadConfirm();submit()}}}">
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
