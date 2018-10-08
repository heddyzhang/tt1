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
<title><bean:message key="com.title.jisekiESienList"/></title>
<link rel="stylesheet" href="./css/style.css" type="text/css">
<link rel="stylesheet" href="./css/styleKz.css" type="text/css">
<link rel="stylesheet" href="./css/styleKzView.css" type="text/css">
<link rel="stylesheet" href="./css/print.css" type="text/css" media="print">
<script type="text/javascript" src="./js/pbsControl.js"></script>
<script type="text/javascript" src="./js/comControl.js"></script>
<script type="text/javascript" src="./js/comMaster.js"></script>
<script type="text/javascript" src="./js/kzControl.js"></script>

<script type="text/javascript">
// 画面固有の明細部以外領域の高さ指定
var othersHeight = 132;
// 検索フォーム
var searchForm;
// 【抽出条件】項目
var CYUSYUTU_JOKEN_ARRAY = ['zensyaLebelLbl','jigyosyoLebelLbl','jigyosyoCdLbl','kaLebelLbl','kaCdLbl','tantosyaLebelLbl','tantosyaCdLbl','todofukenLebelLbl','countryCdLbl','zenkokuOrosiLebelLbl','zenkokuOrosiCdLbl','tokuyakuLebelLbl','tokuyakuCdLbl','tokuyakuNmLbl','sounisakiLebelLbl','sounisakiCdLbl','sounisakiNmLbl','syuhantenLebelLbl','syuhantenCdLbl','syuhantenNmLbl','unsotenLebelLbl'];
// 【抽出条件】活性非活性制御Map
var CYUSYUTU_JOKEN_MAP = {"02": ['jigyosyoCdLbl'],"03": ['kaCdLbl'],"04": ['tantosyaCdLbl'],
		"05": ['countryCdLbl'],"06": ['zenkokuOrosiCdLbl'],"07": ['tokuyakuCdLbl','tokuyakuNmLbl'],
		"08": ['sounisakiCdLbl','sounisakiNmLbl'],"09": ['syuhantenCdLbl','syuhantenNmLbl']};
// 【集計内容】項目
var SYUKEINAIYOU_ARRAY = ['jigyosyobetuLbl','kabetuLbl','tantobetuLbl','tokuyakubetuLbl','sounisakibetuLbl','zenkokuOrosibetuLbl','todoufukenbetuLbl','yusitukokubetuLbl','youkizaisitubetuLbl','sakadanebetuLbl','sakesitubetuLbl','syohincdbetuLbl'];
// 【抽出条件】と【集計内容】関連Map
var CYUSYUTUJOUKEN_SYUKEINAIYOU_MAP = {
		"01": ['tantobetuLbl','sounisakibetuLbl'],
		"02": ['jigyosyobetuLbl'],
		"03": ['jigyosyobetuLbl','kabetuLbl','zenkokuOrosibetuLbl','todoufukenbetuLbl','yusitukokubetuLbl'],
		"04": ['jigyosyobetuLbl','kabetuLbl','tantobetuLbl','zenkokuOrosibetuLbl','todoufukenbetuLbl','yusitukokubetuLbl'],
		"05": ['jigyosyobetuLbl','kabetuLbl','tantobetuLbl','zenkokuOrosibetuLbl','todoufukenbetuLbl','yusitukokubetuLbl'],
		"06": ['jigyosyobetuLbl','kabetuLbl','tantobetuLbl','zenkokuOrosibetuLbl','todoufukenbetuLbl','yusitukokubetuLbl'],
		"07": ['jigyosyobetuLbl','kabetuLbl','tantobetuLbl','tokuyakubetuLbl','zenkokuOrosibetuLbl'],
		"08": ['jigyosyobetuLbl','kabetuLbl','tantobetuLbl','tokuyakubetuLbl','sounisakibetuLbl','zenkokuOrosibetuLbl','todoufukenbetuLbl'],
		"09": ['jigyosyobetuLbl','kabetuLbl','tantobetuLbl','tokuyakubetuLbl','sounisakibetuLbl','zenkokuOrosibetuLbl','todoufukenbetuLbl','yusitukokubetuLbl'],
		"10": ['jigyosyobetuLbl','kabetuLbl','tantobetuLbl','tokuyakubetuLbl','zenkokuOrosibetuLbl','yusitukokubetuLbl','youkizaisitubetuLbl','sakadanebetuLbl','sakesitubetuLbl']
	};
// 対象データと【対象表示期間】の関連Map
var TAISYODATA_TAISYOHYOJIKIKAN_MAP = {"2": ['tougetu10Lbl','siteinenLbl','siteitukiLbl'],"4": ['tougetu10Lbl','siteinenLbl'], "6": ['tougetu10Lbl','siteinenLbl','siteitukiLbl']};
// 【対象表示期間】RadioArray
var TAISYOHYOJIKIKAN_RADIO_ARRAY = ['tougetu10Lbl','siteinenLbl','siteitukiLbl','siteibiLbl'];
// 【対象表示期間】Array
var TAISYOHYOJIKIKAN_ARRAY = ['tougetu10Lbl','taisyoDateLbl','siteinenLbl','taisyoYearFromLbl','taisyoYearToLbl','siteitukiLbl','taisyoMonthFromLbl','taisyoMonthToLbl','siteibiLbl','taisyoDateFromLbl','taisyoDateToLbl'];
// 【対象表示期間】制御Map
var TAISYOHYOJIKIKAN_MAP = {"1": ['taisyoDateLbl'],"2": ['taisyoYearFromLbl','taisyoYearToLbl'],"3": ['taisyoMonthFromLbl','taisyoMonthToLbl'],"4": ['taisyoDateFromLbl','taisyoDateToLbl']};
// 【対比有無】項目
var TAIHIUMU_ARRAY = ['siteinasiLbl','syukkareinyutaihiLbl','syukkareinyutaihiFurikaeLbl','jisekitaihiLbl','kadoubitaihiLbl'];
// 【対象表示期間】と【対比有無】関連Map
var KIKAN_TAIHI_MAP = {"1": ['jisekitaihiLbl','syukkareinyutaihiLbl','syukkareinyutaihiFurikaeLbl'], "2":['siteinasiLbl','syukkareinyutaihiLbl','syukkareinyutaihiFurikaeLbl','jisekitaihiLbl'],"3":['siteinasiLbl','syukkareinyutaihiLbl','syukkareinyutaihiFurikaeLbl','jisekitaihiLbl'],"4": ['siteinasiLbl','syukkareinyutaihiLbl','syukkareinyutaihiFurikaeLbl','jisekitaihiLbl','kadoubitaihiLbl']};
//【集計内容op】Array
var SYUKEINAIYOU_OP_ARRAY = ['sakesituOpLbl','jyutenyoukiOpLbl'];
//【集計内容op】関連Map
var SYUKEINAIYOU_OP_MAP = {"07": ['sakesituOpLbl','jyutenyoukiOpLbl'],"10": ['jyutenyoukiOpLbl'],"11": ['jyutenyoukiOpLbl'],"12": ['sakesituOpLbl']};
//【予算対比】Array
var YOSANTAIHI_ARRAY = ['zensyaLebelLbl','siteibiLbl','jisekitaihiLbl','kadoubitaihiLbl','jigyosyobetuLbl'];
//【表示単位の活性項目Index】Array
var HYOJITANI_DISABLED_ARRAY_YOSAN = [0,1,2,3,4];
//【表示単位の活性項目Index】Array
var HYOJITANI_DISABLED_ARRAY_YOUKI_ZAISITU = [7,8];
// ホームオブジェクトを指定
function setHomePosition() {
	HOME_OBJECT = document.jisekiESienList_Search.taisyoData;
}

// ヘッダ固定
window.onresize = localResize;
function localResize(){
	resize();
	//setLocalHeight("list" ,"details" ,45);
}

// 初期制御処理
function initialControl() {
	// 検索フォーム
	searchForm = document.jisekiESienList_Search;

	// 予算にチェックがある場合は予算チェック処理を実行
	if(isCheckedYosanTaihi()) {
		// 予算対比変更共通
		changeYosanTaihiCommon(searchForm.yosanTaihi, true);
		return
	}

	// 対象データ変更共通
	changeTaisyoDataCommon(searchForm.taisyoData);

	// 【抽出条件】変更
	changeCyusyutuJyoken(getCheckedObj(searchForm.cyusyutuJyoken));
}

// 対象データ変更共通処理
function changeTaisyoDataCommon(obj) {
	// 【対象表示期間】ラジオの各項目を活性へ
	disabledControlForArray(TAISYOHYOJIKIKAN_RADIO_ARRAY, false);

	// 対象データ選択値から無効にする項目を取得
	var kikanRadioTarget = TAISYODATA_TAISYOHYOJIKIKAN_MAP[obj.value];

	// 対象データ選択値に紐付く項目のみを非活性化
	disabledControlForArray(kikanRadioTarget, true);

	// 対象表示期間の活性・非活性制御を行ったため、選択を活性化しているオブジェクトへ設定する
	checkFirstIndex(searchForm.taisyoHyojiKikan);
}

// 対象データ変更処理
function changeTaisyoData(obj) {

	// 予算にチェックがある場合は何もしない
	if(isCheckedYosanTaihi()) {
		return;
	}

	// // 対象データ変更共通
	changeTaisyoDataCommon(obj);

	// 【対象表示期間】変更
	changeTaisyohyojikikan(getCheckedObj(searchForm.taisyoHyojiKikan));
}

// 予算対比チェック有無
function isCheckedYosanTaihi() {
	return searchForm.yosanTaihi.checked;
}

// セレクトオプションの活性非活性制御
function disabledControlForOptions(opts, disabledFlag) {
	for( var i = 0; i < opts.length; i++ ) {
		opts[i].disabled = disabledFlag;
	}
}

// 予算対比変更共通処理
function changeYosanTaihiCommon(obj, init) {
	// 【抽出条件】を全て非活性化
	disabledControlForArray(CYUSYUTU_JOKEN_ARRAY, true);
	// 【集計内容】を全て非活性化
	disabledControlForArray(SYUKEINAIYOU_ARRAY, true);
	// 【対象表示期間】を全て非活性化
	disabledControlForArray(TAISYOHYOJIKIKAN_ARRAY, true);
	// 【対比有無】を全て非活性化
	disabledControlForArray(TAIHIUMU_ARRAY, true);
	// 【集計内容op】を全て非活性化
	disabledControlForArray(SYUKEINAIYOU_OP_ARRAY, true);

	// 予算対比用項目を活性化
	disabledControlForArray(YOSANTAIHI_ARRAY, false);

	// 対象データの初期値（出荷のみ・出荷予定のみ）と選択制御
	disabledControlForOptions(searchForm.taisyoData.options, true);
	searchForm.taisyoData.options[0].disabled = false;
	searchForm.taisyoData.options[2].disabled = false;

	// 計算日基準の初期値（伝票発行日のみ）と選択制御
	disabledControlForOptions(searchForm.keisanDateKijun.options, true);
	searchForm.keisanDateKijun.options[0].disabled = false;
	searchForm.keisanDateKijun.value = "1";
	// 自家用・小売有無の初期値（指定なしのみ）と選択制御
	disabledControlForOptions(searchForm.jikakouriUmu.options, true);
	searchForm.jikakouriUmu.options[0].disabled = false;
	searchForm.jikakouriUmu.value = "1";
	// 表示単位の初期値（金額と容量のみ）と選択制御
	disabledControlForOptions(searchForm.hyojiTani.options, true);
	for( var i = 0; i < HYOJITANI_DISABLED_ARRAY_YOSAN.length; i++ ) {
		searchForm.hyojiTani.options[HYOJITANI_DISABLED_ARRAY_YOSAN[i]].disabled = false;
	}
	for( var i = 0; i < searchForm.hyojiTani.options.length; i++ ) {
		// 非活性になった項目を選択している場合は、先頭を選択済みにする
		if(searchForm.hyojiTani.options[i].disabled && searchForm.hyojiTani.options[i].selected) {
			searchForm.hyojiTani.value = "01";
			break;
		}
	}
	// 初期値

	// 全社
	document.getElementById("zensyaLebelLbl").checked = true;
	// 指定日
	document.getElementById("siteibiLbl").checked = true;
	// 業務日付の月の1日
	searchForm.taisyoDateFrom.value = searchForm.gymMonthDateFrom.value.substr(0,4) + DATE_SEPARATOR + searchForm.gymMonthDateFrom.value.substr(4,2) + DATE_SEPARATOR + searchForm.gymMonthDateFrom.value.substr(6,2);
	// 業務日付の月の末日
	searchForm.taisyoDateTo.value = searchForm.gymMonthDateTo.value.substr(0,4) + DATE_SEPARATOR + searchForm.gymMonthDateTo.value.substr(4,2) + DATE_SEPARATOR + searchForm.gymMonthDateTo.value.substr(6,2);
	// 事業所別
	document.getElementById("jigyosyobetuLbl").checked = true;
	if(!init) {
		// 出荷予定のみ
		searchForm.taisyoData.value = "3";
		// 稼働日対比
		document.getElementById("kadoubitaihiLbl").checked = true;
	}
	// 登録グループエリアを非表示
	var torokuGroupList = document.getElementById("torokuGroupList");
	torokuGroupList.style.display = "none";
	// 登録グループテキスト、ボタンを非活性化
	controlForTorokuGroupText("sakadaneRecord",true);
	controlForTorokuGroupText("sakesituRecord",true);
	controlForTorokuGroupText("syohinRecord",true);
	searchForm.btntourokugrp.disabled = true;
	// 集計内容opのチェックを外す
	for( var i = 0; i < SYUKEINAIYOU_OP_ARRAY.length; i++ ) {
		document.getElementById(SYUKEINAIYOU_OP_ARRAY[i]).checked = false;
	}
}

// 予算対比変更処理
function changeYosanTaihi(obj) {
	// チェックありの場合
	if(obj.checked) {
		// 予算対比変更共通
		changeYosanTaihiCommon(obj, false);
	} else {
		// 【抽出条件】を全て活性化
		disabledControlForArray(CYUSYUTU_JOKEN_ARRAY, false);
		// 【集計内容】を全て活性化
		disabledControlForArray(SYUKEINAIYOU_ARRAY, false);
		// 【対象表示期間】を全て活性化
		disabledControlForArray(TAISYOHYOJIKIKAN_ARRAY, false);
		// 【対比有無】を全て活性化
		disabledControlForArray(TAIHIUMU_ARRAY, false);
		// 【集計内容op】を全て活性化
		disabledControlForArray(SYUKEINAIYOU_OP_ARRAY, false);
		// 各セレクトの選択制御を解除
		disabledControlForOptions(searchForm.taisyoData.options, false);
		disabledControlForOptions(searchForm.keisanDateKijun.options, false);
		disabledControlForOptions(searchForm.jikakouriUmu.options, false);
		disabledControlForOptions(searchForm.hyojiTani.options, false);
		// 初期処理を実行

		// 対象データ変更
		changeTaisyoData(searchForm.taisyoData);

		// 【抽出条件】変更
		changeCyusyutuJyoken(getCheckedObj(searchForm.cyusyutuJyoken));
	}
}

// 【抽出条件】変更処理
function changeCyusyutuJyoken(obj) {
	// 予算にチェックがある場合は何もしない
	if(isCheckedYosanTaihi()) {
		return;
	}

	// 【抽出条件】の各項目を非活性へ
	disabledControlForMap(CYUSYUTU_JOKEN_MAP, true);

	// 抽出条件選択値から有効にする項目を取得
	var cyusyutuJokenTarget = CYUSYUTU_JOKEN_MAP[obj.value];

	// 抽出条件選択値に紐付く項目のみを活性化
	disabledControlForArray(cyusyutuJokenTarget, false);

	// 集計内容を全て活性化
	disabledControlForArray(SYUKEINAIYOU_ARRAY, false);

	// 抽出条件選択値から非活性化にする項目を取得
	var syukeiNaiyouTarget = CYUSYUTUJOUKEN_SYUKEINAIYOU_MAP[obj.value];

	// 抽出条件選択値に紐付く項目のみを非活性化
	disabledControlForArray(syukeiNaiyouTarget, true);

	// 集計内容の活性・非活性制御を行ったため、選択を活性化しているオブジェクトへ設定する
	checkFirstIndex(searchForm.syukeiNaiyo);

	// 【集計内容】変更
	changeSyukeinaiyo(getCheckedObj(searchForm.syukeiNaiyo));
}

//【対象表示期間】変更処理
function changeTaisyohyojikikan(obj) {
	// 予算にチェックがある場合は何もしない
	if(isCheckedYosanTaihi()) {
		return;
	}

	// 【対象表示期間】の各項目を非活性へ
	disabledControlForMap(TAISYOHYOJIKIKAN_MAP, true);

	// 対象表示期間選択値から有効にする項目を取得
	var kikanTarget = TAISYOHYOJIKIKAN_MAP[obj.value];

	// 対象表示期間選択値に紐付く項目のみを活性化
	disabledControlForArray(kikanTarget, false);

	// 【対比有無】制御
	controlForTaihiUmu();
}

// 【対比有無】制御処理
function controlForTaihiUmu() {
	// 対象表示期間の選択値を取得
	var taisyoHyojiKikanVal = getCheckedValue(searchForm.taisyoHyojiKikan);

	// 対比有無を全て非活性化
	disabledControlForArray(TAIHIUMU_ARRAY, true);

	// 対象表示期間選択値から活性化にする項目を取得
	var taihiTarget = KIKAN_TAIHI_MAP[taisyoHyojiKikanVal];

	// 対象表示期間選択値に紐付く項目のみを活性化
	disabledControlForArray(taihiTarget, false);

	// 対象データによる制御
	var taisyoData = searchForm.taisyoData.value;
	if(taisyoData == "1" || taisyoData == "3") {
		// 稼働日対比の活性化条件
		// 【対象データ】[出荷のみ][出荷予定]
		// 【抽出条件】[全社レベル]または[事業所レベル]
		// 【集計内容】[事業所別]
		// 【対象表示期間】[指定日]
		var disabledFlg = true;
		if(taisyoData == "1" || taisyoData == "3") {
			if(taisyoHyojiKikanVal == "4") {
				var cyusyutuJyokenVal = getCheckedValue(searchForm.cyusyutuJyoken);
				var syukeiNaiyoVal = getCheckedValue(searchForm.syukeiNaiyo);
				if(cyusyutuJyokenVal == "01" || cyusyutuJyokenVal == "02") {
					if(syukeiNaiyoVal == "01") {
						disabledFlg = false;
					}
				}
			}
		}
		document.getElementById("kadoubitaihiLbl").disabled = disabledFlg;
	} else if(taisyoData == "5" || taisyoData == "7") {
		// 戻入のみ・純出荷の場合 出荷・戻入対比・稼働日対比無効
		document.getElementById("syukkareinyutaihiLbl").disabled = true;
		document.getElementById("syukkareinyutaihiFurikaeLbl").disabled = true;
		document.getElementById("kadoubitaihiLbl").disabled = true;
	} else {
		// 指定なし以外無効
		document.getElementById("jisekitaihiLbl").disabled = true;
		document.getElementById("kadoubitaihiLbl").disabled = true;
		document.getElementById("syukkareinyutaihiLbl").disabled = true;
		document.getElementById("syukkareinyutaihiFurikaeLbl").disabled = true;
	}

	// 対比有無の活性・非活性制御を行ったため、選択を活性化しているオブジェクトへ設定する
	checkFirstIndex(searchForm.taihiUmu);
}

// 【集計内容】変更処理
function changeSyukeinaiyo(obj) {
	// 予算にチェックがある場合は何もしない
	if(isCheckedYosanTaihi()) {
		return;
	}

	// 表示単位の初期値と選択制御
	disabledControlForOptions(searchForm.hyojiTani.options, false);
	// 容器材質別の場合
	if(obj.value == "09") {
		for( var i = 0; i < HYOJITANI_DISABLED_ARRAY_YOUKI_ZAISITU.length; i++ ) {
			searchForm.hyojiTani.options[HYOJITANI_DISABLED_ARRAY_YOUKI_ZAISITU[i]].disabled = true;
		}
		for( var i = 0; i < searchForm.hyojiTani.options.length; i++ ) {
			// 非活性になった項目を選択している場合は、先頭を選択済みにする
			if(searchForm.hyojiTani.options[i].disabled && searchForm.hyojiTani.options[i].selected) {
				searchForm.hyojiTani.value = "01";
				break;
			}
		}
	}

	// 登録グループエリア
	var torokuGroupList = document.getElementById("torokuGroupList");
	// 登録グループエリアを非表示
	torokuGroupList.style.display = "none";
	// 登録グループテキスト、ボタンを非活性化
	controlForTorokuGroupText("sakadaneRecord",true);
	controlForTorokuGroupText("sakesituRecord",true);
	controlForTorokuGroupText("syohinRecord",true);
	searchForm.btntourokugrp.disabled = true;

	// 酒種エリア
	var sakadaneList = document.getElementById("sakadaneList");
	// 酒質エリア
	var sakesituList = document.getElementById("sakesituList");
	// 商品エリア
	var syohinList = document.getElementById("syohinList");

	// 酒種別
	if(obj.value == "10") {
		// 酒種エリアを表示、酒質エリア・商品エリアを非表示
		sakadaneList.style.display = "block";
		sakesituList.style.display = "none";
		syohinList.style.display = "none";
		// ボタン活性化
		document.jisekiESienList_Search.btntourokugrp.disabled = false;
		// 登録グループの酒種テキスト活性化
		controlForTorokuGroupText("sakadaneRecord",false);
		// 登録グループエリアを表示
		torokuGroupList.style.display = "block";

	// 酒質別
	} else if(obj.value == "11") {
		// 酒質エリアを表示、酒種エリア・商品エリアを非表示
		sakadaneList.style.display = "none";
		sakesituList.style.display = "block";
		syohinList.style.display = "none";
		// ボタン活性化
		document.jisekiESienList_Search.btntourokugrp.disabled = false;
		// 登録グループの酒質テキスト活性化
		controlForTorokuGroupText("sakesituRecord",false);
		// 登録グループエリアを表示
		torokuGroupList.style.display = "block";
	// 商品別
	} else if(obj.value == "12") {
		// 商品エリアを表示、酒質エリア・酒種エリアを非表示
		sakadaneList.style.display = "none";
		sakesituList.style.display = "none";
		syohinList.style.display = "block";
		// ボタン活性化
		document.jisekiESienList_Search.btntourokugrp.disabled = false;
		// 登録グループの酒種テキスト活性化
		controlForTorokuGroupText("syohinRecord",false);
		// 登録グループエリアを表示
		torokuGroupList.style.display = "block";
	}

	// 【集計内容op】 制御
	controlSyukeiNaiyouOp(obj);

	// 【対象表示期間】変更
	changeTaisyohyojikikan(getCheckedObj(searchForm.taisyoHyojiKikan));
}

// 【集計内容op】 制御処理
function controlSyukeiNaiyouOp(obj) {
	// 全て非活性
	disabledControlForArray(SYUKEINAIYOU_OP_ARRAY, true);

	// 集計内容選択値から有効にする項目を取得
	var syukeiNaiyouOpTarget = SYUKEINAIYOU_OP_MAP[obj.value];

	if(syukeiNaiyouOpTarget != null) {
		// 対象表示期間選択値に紐付く項目のみを活性化
		disabledControlForArray(syukeiNaiyouOpTarget, false);
	}

	// 非活性となった項目のチェックを外す
	for( var i = 0; i < SYUKEINAIYOU_OP_ARRAY.length; i++ ) {
		if(document.getElementById(SYUKEINAIYOU_OP_ARRAY[i]).disabled) {
			document.getElementById(SYUKEINAIYOU_OP_ARRAY[i]).checked = false;
		}
	}
}

// 登録グループテキスト制御処理
function controlForTorokuGroupText(id, isDisabled) {
	for(var i=0; i < 20; i++){
		searchForm.elements(id + "[" + i + "].torokuCd").disabled = isDisabled;
		searchForm.elements(id + "[" + i + "].torokuNm").disabled = isDisabled;
	}
}

//登録グループ判定処理
function isTourokuGroup(syukeiNaiyoVal) {
	// 集計内容が酒質・酒種・商品のみtrue
	return syukeiNaiyoVal == "10" || syukeiNaiyoVal == "11" || syukeiNaiyoVal == "12";
}

// ajax処理
function getValueByAjax(keyObj, keyNm, targetNms) {
	var url = "/kit/jisekiESienList_Ajax.do?" + keyNm + "=" + keyObj.value;
	getValueByCodeForAjax(url, "jisekiESienList_Search", keyObj, targetNms);
}
// 登録グループ用ajax処理
function getValueByAjaxForTorokuCd(keyObj, targetNms) {
	// 集計内容の選択値取得
	var syukeiNaiyoVal = getCheckedValue(searchForm.syukeiNaiyo);
	// 登録グループ判定
	if(!isTourokuGroup(syukeiNaiyoVal)) return;
	var url = "/kit/jisekiESienList_Ajax.do?torokuCd=" + keyObj.value + "&syukeiNaiyo=" + syukeiNaiyoVal;
	getValueByCodeForAjax(url, "jisekiESienList_Search", keyObj, targetNms);
}

// 登録グループインターバルID
var doTimerIdTorokuGroup = null;
// 登録グループボタン押下
function clickTorokuGroup() {
	// 登録グループ画面表示処理実行
	doTimerIdTorokuGroup = window.setInterval('openTorokuGroup()',10);
}

// 登録グループ画面表示
function openTorokuGroup() {
	window.clearInterval(doTimerIdTorokuGroup);
	doTimerIdTorokuGroup=null;
	// 集計内容の選択値取得
	var syukeiNaiyoVal = getCheckedValue(searchForm.syukeiNaiyo);
	// 登録グループ判定
	if(!isTourokuGroup(syukeiNaiyoVal)) return;
	// 画面表示
	var options = "directories=0,location=0,menubar=0,scrollbars=1,status=1,toolbar=0,resizable=1,titlebar=0";
	options = options + ",width=850,height=700,left=200,top=50";
	var url = "jisekiESienListTorokuGroup_Init.do?syukeiNaiyo=" + syukeiNaiyoVal;
	openModal(url,options);
}

// 登録グループのコード名称を設定
function setTorokuCdNm(index, torokuCd, torokuNm) {
	// 子画面からの戻り値を対応するテキストへ反映
	var syukeiNaiyoVal = getCheckedValue(searchForm.syukeiNaiyo);
	var id;
	if(syukeiNaiyoVal == "10") {
		id = "sakadaneRecord";
	} else if(syukeiNaiyoVal == "11") {
		id = "sakesituRecord";
	} else if(syukeiNaiyoVal == "12") {
		id = "syohinRecord";
	} else {
		return;
	}
	searchForm.elements(id + "[" + index + "].torokuCd").value = torokuCd;
	searchForm.elements(id + "[" + index + "].torokuNm").value = torokuNm;
}

//選択されたラジオボタンの値を取得
function getCheckedValue(radioObj) {
	for( var i = 0; i < radioObj.length; i++ ) {
		if(radioObj[i].checked) {
			return radioObj[i].value;
		}
	}
	return null;
}

//選択されたラジオボタンのオブジェクトを取得
function getCheckedObj(radioObj) {
	for( var i = 0; i < radioObj.length; i++ ) {
		if(radioObj[i].checked) {
			return radioObj[i];
		}
	}
	return null;
}

//ラジオボタンのtabindexが一番小さいオブジェクトを選択状態にする
function checkFirstIndex(radioObj) {
	var checkedObj = getCheckedObj(radioObj);
	// 非活性の場合
	if(checkedObj.disabled) {
		var o = new Object();
		for( var i = 0; i < radioObj.length; i++ ) {
			// 活性状態のものが対象
			if(!radioObj[i].disabled) {
				o[radioObj[i].tabIndex] = radioObj[i];
			}
		}
		var keys = [];
		for (var key in o) {
			keys.push(key);
		}
		// if(keys.length == 0) return;
		keys.sort();
		var targetObj = o[keys[0]];
		targetObj.checked = true;
	}
}

//活性・非活性制御処理
function disabledControlForMap(objMap, disabledFlag) {
	for (var key in objMap) {
		var val = objMap[key];
		for( var i = 0; i < val.length; i++ ) {
			document.getElementById(val[i]).disabled = disabledFlag;
		}
	}
}

//活性・非活性制御処理
function disabledControlForArray(objArray, disabledFlag) {
	if(objArray != null) {
		for( var i = 0; i < objArray.length; i++ ) {
			document.getElementById(objArray[i]).disabled = disabledFlag;
		}
	}
}

// 入力項目活性化処理
function enabledInput(){
	var frmElements = searchForm.elements;
	var frmElementsLen = frmElements.length;
	for (var i=0; i<frmElementsLen; i++) {
		if(frmElements[i].type == OBJ_TYPE_TEXT ||
				frmElements[i].type == OBJ_TYPE_CHECKBOX ||
				//frmElements[i].type == OBJ_TYPE_RADIO ||
				frmElements[i].type == OBJ_TYPE_SELECT_ONE) {
			frmElements[i].disabled = false;
		}
	}
}

// 検索結果表示インターバルID
var doTimerIdSearchResult = null;

//検索結果画面表示判定処理
function judgeOpenSearchResult() {
	if(searchForm.mode.value == "search") {
		// 検索結果画面表示処理実行
		doTimerIdSearchResult = window.setInterval('openSearchResult()',10);
	}
}
//検索結果画面表示
function openSearchResult() {
	window.clearInterval(doTimerIdSearchResult);
	doTimerIdSearchResult=null;
	// 画面表示
	var options = "directories=0,location=0,menubar=0,scrollbars=1,status=1,toolbar=0,resizable=1,titlebar=0";
	options = options + ",width=1300,height=750,left=10,top=10";
	var url = "jisekiESienList_SearchResult.do";
	openModal(url,options);
}

</script>
<style type="text/css">
<!--
/** 検索部 */

.searchHeader{
	width:100%;
	border:1px solid #FF0000;
	background-color:#FFFFFF;
}
.searchHeader .title{
	width:300px;
	text-align: center;
	float: left;
}
.searchHeader table{
	margin: 40px 1px 5px 1px;
}
#list .buttons{
	float: right;
}
#list table{
	font-size:11pt;
}
.searchTable{
	margin:1pt;
}
.searchTable td{
	padding-left:10px;
}
.searchTable2{
	margin:1pt;
	padding-left:50px;
	font-size:11pt;
}
.searchTable2 td{
	padding-left:10px;
}
 -->
</style>
</head>
<body
	onContextmenu="return false"
	onLoad="initialControl();eventLoad();setHomePosition();stopWaiting();judgeOpenSearchResult();"
	onbeforeunload="return eventUnloadMain();"
	onhelp="return false;">

	<%-- 共通ヘッダー開始 --%>
	<%@ include file="/parts/comHeader.jsp" %>
	<%-- 共通ヘッダー終了 --%>

	<%-- 検索条件入力フォーム開始 --%>
	<kit:form action="/jisekiESienList_Search.do">
		<div class="searchHeader">
			<%-- タイトル開始 --%>
			<div class="title">
				<bean:message key="com.title.jisekiESienList" />
			</div>
			<%-- タイトル終了 --%>
			<table></table>
		</div>
		<kit:hidden name="jisekiESienList_Search" property="editable" />
		<kit:hidden name="jisekiESienList_Search" property="mode" />
		<kit:hidden name="jisekiESienList_Search" property="gymMonthDateFrom" />
		<kit:hidden name="jisekiESienList_Search" property="gymMonthDateTo" />
		<div class="wrapper">
			<div id="list" class="fixedTitleX">
			<div class="buttons">
				<table>
					<tr>
						<td>
							<%-- 呼出ボタン --%>
							<kit:button styleClass="btn_large" property="btnSearch"
								tabindex="6"
								onclick="if(eventPreSubmit()){offUnloadConfirm();startWaiting();enabledInput();document.jisekiESienList_Search.submit();}">
								<bean:message key="com.button.search" />
							</kit:button>

							<%-- ポップアップ検索 --%>
							<kit:button styleClass="btn_search btn_large" property="btnPopupSearch"
								onclick="openModalSearch(this.form.kaisyaCd.value) ">
								<bean:message key="com.button.knsk" />
							</kit:button>
						</td>
					</tr>
				</table>
			</div>
			<table class="searchTable">
				<tbody>
					<tr>
						<td>
							<%-- 対象データ --%>
							<label for="taisyoDataLbl"><bean:message key="com.text.taisyodata" /></label>
							<kit:select styleClass="list ime-off must" name="jisekiESienList_Search" property="taisyoData" styleId="taisyoDataLbl" tabindex="1"
								onchange="changeTaisyoData(this);"
								onkeypress="ctrlSelection(this);changeTaisyoData(this);">
								<kit:options collection="jisekiESienList_taisyoDataList" optional="false" useDefault="false" />
							</kit:select>
						</td>

						<td>
							<%-- 計算日付基準 --%>
							<label for="keisanDateKijunLbl"><bean:message key="com.text.keisandatekijyun" /></label>
							<kit:select styleClass="list ime-off must" name="jisekiESienList_Search" property="keisanDateKijun" styleId="keisanDateKijunLbl" tabindex="2"
								onkeypress="ctrlSelection(this)">
								<kit:options collection="jisekiESienList_keisanDateKijunList"
									optional="false" useDefault="false" />
							</kit:select>
						</td>

						<td>
							<%-- 自家用・小売有無 --%>
							<label for="jikakouriUmuLbl"><bean:message key="com.text.jikakouriumu" /></label>
							<kit:select styleClass="list ime-off must" name="jisekiESienList_Search" property="jikakouriUmu" styleId="jikakouriUmuLbl" tabindex="3"
								onkeypress="ctrlSelection(this)">
								<kit:options collection="jisekiESienList_jikakouriUmuList"
									optional="false" useDefault="false" />
							</kit:select>
						</td>

						<td>
							<%-- 表示単位 --%>
							<label for="hyojiTaniLbl"><bean:message key="com.text.hyojitani" /></label>
							<kit:select styleClass="list ime-off must" name="jisekiESienList_Search" property="hyojiTani" styleId="hyojiTaniLbl" tabindex="4"
								onkeypress="ctrlSelection(this)">
								<kit:options collection="jisekiESienList_hyojiTaniList"
									optional="false" useDefault="false" />
							</kit:select>
						</td>

						<td>
							<%-- ＋予算対比 --%>
							<kit:checkbox styleClass="check" name="jisekiESienList_Search" property="yosanTaihi" tabindex="5" styleId="yosanTaihiLbl" onclick="changeYosanTaihi(this)"/>
							<label for="yosanTaihiLbl"><bean:message key="com.text.Plusyosantaihi"/></label>
						</td>
					</tr>

				</tbody>
			</table>
			<HR size="1">
			<table class="searchTable">
				<tbody>
					<tr>
						<td colspan="8">
							<div >
								<bean:message key="com.text.cyusitujyouken" />
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<%-- 全社レベル --%>
							<kit:radio styleClass="radio" name="jisekiESienList_Search" property="cyusyutuJyoken" styleId="zensyaLebelLbl" tabindex="7" value="01"
							onclick="changeCyusyutuJyoken(this)"/>
							<label for="zensyaLebelLbl"><bean:message key="com.text.zensyaLebel"/></label>
						</td>
						<td>
							<%-- 事業所レベル --%>
							<kit:radio styleClass="radio" name="jisekiESienList_Search" property="cyusyutuJyoken" styleId="jigyosyoLebelLbl" tabindex="9" value="02"
							onclick="changeCyusyutuJyoken(this)"/>
							<label for="jigyosyoLebelLbl"><bean:message key="com.text.jigyosyoLebel"/></label>
						</td>
						<td>
							<kit:select styleClass="list ime-off" name="jisekiESienList_Search" property="jigyosyoCd" styleId="jigyosyoCdLbl" tabindex="10" errorProperty="jigyosyoCdError" errorClass="error"
								onkeypress="ctrlSelection(this)">
								<kit:options collection="jisekiESienList_jigyosyoList" optional="false" useDefault="false" />
							</kit:select>
						</td>
						<td>
							<%-- 都道府県レベル --%>
							<kit:radio styleClass="radio" name="jisekiESienList_Search" property="cyusyutuJyoken" styleId="todofukenLebelLbl" tabindex="15" value="05"
							onclick="changeCyusyutuJyoken(this)"/>
							<label for="todofukenLebelLbl"><bean:message key="com.text.todofukenLebel"/></label>
						</td>
						<td>
							<kit:select styleClass="list ime-off" name="jisekiESienList_Search" property="countryCd" styleId="countryCdLbl" tabindex="16" errorProperty="countryCdError" errorClass="error"
								onkeypress="ctrlSelection(this)">
								<kit:options collection="jisekiESienList_countryList" optional="false" useDefault="false" />
							</kit:select>
						</td>
						<td>
							<%-- 特約レベル --%>
							<kit:radio styleClass="radio" name="jisekiESienList_Search" property="cyusyutuJyoken" styleId="tokuyakuLebelLbl" tabindex="19" value="07"
							onclick="changeCyusyutuJyoken(this)"/>
							<label for="tokuyakuLebelLbl"><bean:message key="com.text.tokuyakuLebel"/></label>
						</td>
						<td>
							<kit:text
								name="jisekiESienList_Search" property="tokuyakuCd" styleId="tokuyakuCdLbl" maxlength="6" size="10" tabindex="20" errorProperty="tokuyakuCdError" errorClass="error"
								styleClass="text ime_off code frm_lpad rigth searchable"
								onkeyup="nextTabMaxLenInput()"
								onblur="ctrlInput(FRM_CLASS_PAD,6,'0');setRefObjAll('popup','orositenCd',this,'tenNm2Jisya','tokuyakuNm','tokuyakutenFlg','tokuyakutenFlg');getValueByAjax(this,'tokuyakuCd','tokuyakuNm');"
								onfocus="ctrlInput();setPreValueForAjax(this);" />
								<kit:hidden name="jisekiESienList_Search" property="tokuyakutenFlg" value="on" />
						</td>
						<td>
							<kit:text
								name="jisekiESienList_Search" property="tokuyakuNm" styleId="tokuyakuNmLbl" maxlength="42" size="45"
								styleClass="text ime_off left readonly"
								readonly="true" tabindex="false"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</td>
					</tr>
					<tr>
						<td>
							<%-- 運送店レベル --%>
							<kit:radio styleClass="radio" name="jisekiESienList_Search" property="cyusyutuJyoken" styleId="unsotenLebelLbl" tabindex="8" value="10"
							onclick="changeCyusyutuJyoken(this)"/>
							<label for="unsotenLebelLbl"><bean:message key="com.text.unsotenLebel"/></label>
						</td>

						<td>
							<%-- 課レベル --%>
							<kit:radio styleClass="radio" name="jisekiESienList_Search" property="cyusyutuJyoken" styleId="kaLebelLbl" tabindex="11" value="03"
							onclick="changeCyusyutuJyoken(this)"/>
							<label for="kaLebelLbl"><bean:message key="com.text.kaLebel"/></label>
						</td>
						<td>
							<kit:select styleClass="list ime-off" name="jisekiESienList_Search" property="kaCd" styleId="kaCdLbl" tabindex="12" errorProperty="kaCdError" errorClass="error"
								onkeypress="ctrlSelection(this)">
								<kit:options collection="jisekiESienList_kaList" optional="false" useDefault="false" />
							</kit:select>
						</td>
						<td>
							<%-- 全国卸レベル --%>
							<kit:radio styleClass="radio" name="jisekiESienList_Search" property="cyusyutuJyoken" styleId="zenkokuOrosiLebelLbl" tabindex="17" value="06"
							onclick="changeCyusyutuJyoken(this)"/>
							<label for="zenkokuOrosiLebelLbl"><bean:message key="com.text.zenkokuorosiLebel"/></label>
						</td>
						<td>
							<kit:select styleClass="list ime-off" name="jisekiESienList_Search" property="zenkokuOrosiCd" styleId="zenkokuOrosiCdLbl" tabindex="18" errorProperty="zenkokuOrosiCdError" errorClass="error"
								onkeypress="ctrlSelection(this)">
								<kit:options collection="jisekiESienList_zenkokuOrosiList" optional="false" useDefault="false" />
							</kit:select>
						</td>
						<td>
							<%-- 送荷先レベル --%>
							<kit:radio styleClass="radio" name="jisekiESienList_Search" property="cyusyutuJyoken" styleId="sounisakiLebelLbl" tabindex="21" value="08"
							onclick="changeCyusyutuJyoken(this)"/>
							<label for="sounisakiLebelLbl"><bean:message key="com.text.sounisakiLebel"/></label>
						</td>
						<td>
							<kit:text
								name="jisekiESienList_Search" property="sounisakiCd" styleId="sounisakiCdLbl" maxlength="6" size="10" tabindex="22" errorProperty="sounisakiCdError" errorClass="error"
								styleClass="text ime_off code frm_lpad rigth searchable"
								onkeyup="nextTabMaxLenInput()"
								onblur="ctrlInput(FRM_CLASS_PAD,6,'0');setRefObjAll('popup','orositenCd',this,'tenNm2Jisya','sounisakiNm');getValueByAjax(this,'sounisakiCd','sounisakiNm');"
								onfocus="ctrlInput();setPreValueForAjax(this);" />
						</td>
						<td>
							<kit:text
								name="jisekiESienList_Search" property="sounisakiNm" styleId="sounisakiNmLbl" maxlength="42" size="45"
								styleClass="text ime_off left readonly"
								readonly="true" tabindex="false"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</td>

					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<%-- 担当者レベル --%>
							<kit:radio styleClass="radio" name="jisekiESienList_Search" property="cyusyutuJyoken" styleId="tantosyaLebelLbl" tabindex="13" value="04"
							onclick="changeCyusyutuJyoken(this)"/>
							<label for="tantosyaLebelLbl"><bean:message key="com.text.tantosyaLebel"/></label>
						</td>
						<td>
							<kit:select styleClass="list ime-off" name="jisekiESienList_Search" property="tantosyaCd" styleId="tantosyaCdLbl" tabindex="14" errorProperty="tantosyaCdError" errorClass="error"
								onkeypress="ctrlSelection(this)">
								<kit:options collection="jisekiESienList_tantosyaList" optional="false" useDefault="false" />
							</kit:select>
						</td>
						<td colspan="2">&nbsp;</td>
						<td>
							<%-- 酒販店レベル --%>
							<kit:radio styleClass="radio" name="jisekiESienList_Search" property="cyusyutuJyoken" styleId="syuhantenLebelLbl" tabindex="23" value="09"
							onclick="changeCyusyutuJyoken(this)"/>
							<label for="syuhantenLebelLbl"><bean:message key="com.text.syuhantenLebel"/></label>
						</td>
						<td>
							<kit:text
								name="jisekiESienList_Search" property="syuhantenCd" styleId="syuhantenCdLbl" maxlength="8" size="10" tabindex="24" errorProperty="syuhantenCdError" errorClass="error"
								styleClass="text ime_off code frm_lpad rigth searchable"
								onkeyup="nextTabMaxLenInput()"
								onblur="ctrlInput(FRM_CLASS_PAD,8,'0');setRefObjAll('popup','syuhantenCd',this,'tenNmYago','syuhantenNm');getValueByAjax(this,'syuhantenCd','syuhantenNm');"
								onfocus="ctrlInput();setPreValueForAjax(this);" />
						</td>
						<td>
							<kit:text
								name="jisekiESienList_Search" property="syuhantenNm" styleId="syuhantenNmLbl" maxlength="42" size="45"
								styleClass="text ime_off left readonly"
								readonly="true" tabindex="false"
								onblur="ctrlInput()" onfocus="ctrlInput()" />
						</td>
					</tr>
				</tbody>
			</table>
			<HR size="1">
			<table>
				<tbody>
					<tr>
						<td>
							<table class="searchTable">
								<tr>
									<td colspan="2">
										<div >
											<bean:message key="com.text.taisyohyojikikan" />
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<%-- 当月・10～当月・1～当月 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="taisyoHyojiKikan" styleId="tougetu10Lbl" tabindex="25" value="1"
										onclick="changeTaisyohyojikikan(this)"/>
										<label for="tougetu10Lbl"><bean:message key="com.text.tougetu10"/></label>
										<kit:text name="jisekiESienList_Search" property="taisyoDate" styleId="taisyoDateLbl" errorProperty="taisyoDateError" errorClass="error"
											styleClass="text ime_off date frm_dt right"
											format="yyyy/mm/dd"
											maxlength="8"
											size="12"
											tabindex="26"
											onkeyup="nextTabMaxLenInput()"
											onfocus= "ctrlInput()"
											onblur= "ctrlInput()"
										/>
									</td>

									<td>
										<%-- 指定月 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="taisyoHyojiKikan" styleId="siteitukiLbl" tabindex="30" value="3"
										onclick="changeTaisyohyojikikan(this)"/>
										<label for="siteitukiLbl"><bean:message key="com.text.siteituki"/></label>
										<kit:text name="jisekiESienList_Search" property="taisyoMonthFrom" styleId="taisyoMonthFromLbl" errorProperty="taisyoMonthFromError" errorClass="error"
											styleClass="text ime_off yyyymm frm_ym right"
											format="yyyy/mm"
											maxlength="6"
											size="8"
											tabindex="31"
											onkeyup="nextTabMaxLenInput()"
											onfocus= "ctrlInput()"
											onblur= "ctrlInput()"
										/>
										<label for="startNengetuLbl"><bean:message key="com.text.range"/></label>
										<kit:text name="jisekiESienList_Search" property="taisyoMonthTo" styleId="taisyoMonthToLbl" errorProperty="taisyoMonthToError" errorClass="error"
											styleClass="text ime_off yyyymm frm_ym right"
											format="yyyy/mm"
											maxlength="6"
											size="8"
											tabindex="32"
											onkeyup="nextTabMaxLenInput()"
											onfocus= "ctrlInput()"
											onblur= "ctrlInput();"
										/>
									</td>
								</tr>
								<tr>
									<td>
										<%-- 指定年 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="taisyoHyojiKikan" styleId="siteinenLbl" tabindex="27" value="2"
										onclick="changeTaisyohyojikikan(this)"/>
										<label for="siteinenLbl"><bean:message key="com.text.siteinen"/></label>
										<kit:text name="jisekiESienList_Search" property="taisyoYearFrom" styleId="taisyoYearFromLbl" errorProperty="taisyoYearFromError" errorClass="error"
											styleClass="text numeric ime_off frm_lpad right"
											maxlength="4"
											size="6"
											tabindex="28"
											onkeyup="nextTabMaxLenInput()"
											onfocus= "ctrlInput();"
											onblur= "ctrlInput();"
										/>
										<label for="startNengetuLbl"><bean:message key="com.text.range"/></label>
										<kit:text name="jisekiESienList_Search" property="taisyoYearTo" styleId="taisyoYearToLbl" errorProperty="taisyoYearToError" errorClass="error"
											styleClass="text numeric ime_off frm_lpad right"
											maxlength="4"
											size="6"
											tabindex="29"
											onkeyup="nextTabMaxLenInput()"
											onfocus= "ctrlInput();"
											onblur= "ctrlInput();"
										/>
									</td>

									<td>
										<%-- 指定日 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" styleId="siteibiLbl" property="taisyoHyojiKikan" tabindex="33" value="4"
										onclick="changeTaisyohyojikikan(this)"/>
										<label for="siteibiLbl"><bean:message key="com.text.siteibi"/></label>
										<kit:text name="jisekiESienList_Search" property="taisyoDateFrom" styleId="taisyoDateFromLbl" errorProperty="taisyoDateFromError" errorClass="error"
											styleClass="text ime_off date frm_dt right"
											format="yyyy/mm/dd"
											maxlength="8"
											size="12"
											tabindex="34"
											onkeyup="nextTabMaxLenInput()"
											onfocus= "ctrlInput()"
											onblur= "ctrlInput()"
										/>

										<label for="startNengetuLbl"><bean:message key="com.text.range"/></label>
										<kit:text name="jisekiESienList_Search" property="taisyoDateTo" styleId="taisyoDateToLbl" errorProperty="taisyoDateToError" errorClass="error"
											styleClass="text ime_off date frm_dt right"
											format="yyyy/mm/dd"
											maxlength="8"
											size="12"
											tabindex="35"
											onkeyup="nextTabMaxLenInput()"
											onfocus= "ctrlInput()"
											onblur= "ctrlInput();"
										/>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table class="searchTable2">
								<tr>
									<td colspan="3">
										<div >
											<bean:message key="com.text.taihiumu" />
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<%-- 指定なし --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="taihiUmu" styleId="siteinasiLbl" tabindex="36" value="1" />
										<label for="siteinasiLbl"><bean:message key="com.text.siteinasi"/></label>
									</td>
									<td>
										<%-- 出荷・戻入対比 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="taihiUmu" styleId="syukkareinyutaihiLbl" tabindex="37" value="2" />
										<label for="syukkareinyutaihiLbl"><bean:message key="com.text.syukkareinyutaihi"/></label>
									</td>
									<td>
										<%-- 実績対比 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="taihiUmu" styleId="jisekitaihiLbl" tabindex="39" value="4" />
										<label for="jisekitaihiLbl"><bean:message key="com.text.jisekitaihi"/></label>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td>
										<%-- 出荷・戻入（振替除外）対比 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="taihiUmu" styleId="syukkareinyutaihiFurikaeLbl" tabindex="38" value="3" />
										<label for="syukkareinyutaihiFurikaeLbl"><bean:message key="com.text.syukkareinyutaihiFurikae"/></label>
									</td>
									<td>
										<%-- 稼働日対比 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="taihiUmu" styleId="kadoubitaihiLbl" tabindex="40" value="5" />
										<label for="kadoubitaihiLbl"><bean:message key="com.text.kadoubitaihi"/></label>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</tbody>
			</table>
			<HR size="1">
			<table>
				<tbody>
					<tr>
						<td>
							<table class="searchTable">
								<tr>
									<td colspan="4">
										<div >
											<bean:message key="com.text.syukeinaiyo" />
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<%-- 事業所別 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="syukeiNaiyo" styleId="jigyosyobetuLbl" tabindex="41" value="01"
										onclick="changeSyukeinaiyo(this)"/>
										<label for="jigyosyobetuLbl"><bean:message key="com.text.jigyosyobetu"/></label>
									</td>

									<td>
										<%-- 特約別 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="syukeiNaiyo" styleId="tokuyakubetuLbl" tabindex="44" value="04"
										onclick="changeSyukeinaiyo(this)"/>
										<label for="tokuyakubetuLbl"><bean:message key="com.text.tokuyakubetu"/></label>
									</td>

									<td>
										<%-- 都道府県別 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="syukeiNaiyo" styleId="todoufukenbetuLbl" tabindex="47" value="07"
										onclick="changeSyukeinaiyo(this)"/>
										<label for="todoufukenbetuLbl"><bean:message key="com.text.todoufukenbetu"/></label>
									</td>

									<td>
										<%-- 酒種別 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="syukeiNaiyo" styleId="sakadanebetuLbl" tabindex="50" value="10"
										onclick="changeSyukeinaiyo(this)"/>
										<label for="sakadanebetuLbl"><bean:message key="com.text.sakadanebetu"/></label>
									</td>
								</tr>
								<tr>
									<td>
										<%-- 課別 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="syukeiNaiyo" styleId="kabetuLbl" tabindex="42" value="02"
										onclick="changeSyukeinaiyo(this)"/>
										<label for="kabetuLbl"><bean:message key="com.text.kabetu"/></label>
									</td>

									<td>
										<%-- 送荷先別 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="syukeiNaiyo" styleId="sounisakibetuLbl" tabindex="45" value="05"
										onclick="changeSyukeinaiyo(this)"/>
										<label for="sounisakibetuLbl"><bean:message key="com.text.sounisakibetu"/></label>
									</td>

									<td>
										<%-- 輸出国別 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="syukeiNaiyo" styleId="yusitukokubetuLbl" tabindex="48" value="08"
										onclick="changeSyukeinaiyo(this)"/>
										<label for="yusitukokubetuLbl"><bean:message key="com.text.yusitukokubetu"/></label>
									</td>

									<td>
										<%-- 酒質別 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="syukeiNaiyo" styleId="sakesitubetuLbl" tabindex="51" value="11"
										onclick="changeSyukeinaiyo(this)"/>
										<label for="sakesitubetuLbl"><bean:message key="com.text.sakesitubetu"/></label>
									</td>
								</tr>
								<tr>
									<td>
										<%-- 担当別 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="syukeiNaiyo" styleId="tantobetuLbl" tabindex="43" value="03"
										onclick="changeSyukeinaiyo(this)"/>
										<label for="tantobetuLbl"><bean:message key="com.text.tantobetu"/></label>
									</td>

									<td>
										<%-- 全国卸別 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="syukeiNaiyo" styleId="zenkokuOrosibetuLbl" tabindex="46" value="06"
										onclick="changeSyukeinaiyo(this)"/>
										<label for="zenkokuOrosibetuLbl"><bean:message key="com.text.zenkokuorosibetu"/></label>
									</td>
									<td>
										<%-- 容器材質別 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="syukeiNaiyo" styleId="youkizaisitubetuLbl" tabindex="49" value="09"
										onclick="changeSyukeinaiyo(this)"/>
										<label for="youkizaisitubetuLbl"><bean:message key="com.text.youkizaisitubetu"/></label>
									</td>
									<td>
										<%-- 商品CD別 --%>
										<kit:radio styleClass="radio" name="jisekiESienList_Search" property="syukeiNaiyo" styleId="syohincdbetuLbl" tabindex="52" value="12"
										onclick="changeSyukeinaiyo(this)"/>
										<label for="syohincdbetuLbl"><bean:message key="com.text.syohincdbetu"/></label>
									</td>
								</tr>
							</table>
						</td>
						<td>
							<table class="searchTable2">
								<tr>
									<td>
										<div >
											<bean:message key="com.text.syukeinaiyoop" />
										</div>
									</td>
								</tr>
								<tr>
									<td>
										<%-- ＋酒質 --%>
										<kit:checkbox styleClass="check" name="jisekiESienList_Search" property="sakesituOp" styleId="sakesituOpLbl" tabindex="53"/>
										<label for="sakesituOpLbl"><bean:message key="com.text.Plussakesitu"/></label>
									</td>
								</tr>

								<tr>
									<td>
										<%-- ＋充填容器 --%>
										<kit:checkbox styleClass="check" name="jisekiESienList_Search" property="jyutenyoukiOp" styleId="jyutenyoukiOpLbl" tabindex="54"/>
										<label for="jyutenyoukiOpLbl"><bean:message key="com.text.Plusjyutenyouki"/></label>
									</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
								</tr>
							</table>
						</td>
					</tr>
				</tbody>
			</table>

			<table id="torokuGroupList" style="display:none;">
				<tr>
					<td style="padding-left:10px;">
						<bean:message key="com.text.torokuGroupMsg"/>
					</td>
					<td style="margin-right:5px;" class="buttons">
						<%-- 登録グループボタン --%>
						<kit:button styleClass="btn_large" property="btntourokugrp"
							tabindex="55"
							onclick="clickTorokuGroup();">
							<bean:message key="com.button.tourokugrp" />
						</kit:button>
					</td>
				</tr>
				<tr id="sakadaneList">
					<td colspan="2">
						<table>
							<kit:iterate id="sakadaneRecord" name="jisekiESienList_Search" property="sakadaneList" indexId="idx" tabStartIndex="56">
								<kit:equal name="idx" value="0||4||8||12||16">
								<tr>
								</kit:equal>
									<td class="cdXView">
										<kit:text
										name="sakadaneRecord" property="torokuCd" maxlength="3" size="8" errorProperty="hasError" errorClass="error"
										styleClass="text ime_off numeric frm_lpad rigth searchable"
										styleId="torokuCdLbl" tabindex="true" indexed="true"
										onkeyup="nextTabMaxLenInput()"
										onblur="ctrlInput(FRM_CLASS_PAD, 3, '0');setRefObjAll('popup','taneCd',this,'taneNmRyaku','torokuNm');getValueByAjaxForTorokuCd(this,'torokuNm');"
										onfocus="ctrlInput();setPreValueForAjax(this);" />
									</td>
									<%-- コード名称 --%>
									<td class="mjView">
										<kit:text
										name="sakadaneRecord" maxlength="42" size="42"
										styleId="torokuNmLbl"
										property="torokuNm"
										styleClass="text ime_off left readonly"
										readonly="true" tabindex="false" indexed="true"
										onblur="ctrlInput()" onfocus="ctrlInput()" />
									</td>
								<kit:equal name="idx" value="3||7||11||15||19">
								</tr>
								</kit:equal>
							</kit:iterate>
						</table>
					</td>
				</tr>
				<tr id="sakesituList">
					<td colspan="2">
						<table>
							<kit:iterate id="sakesituRecord" name="jisekiESienList_Search" property="sakesituList" indexId="idx" tabStartIndex="76">
								<kit:equal name="idx" value="0||4||8||12||16">
								<tr>
								</kit:equal>
									<td class="cdXView">
										<kit:text
										name="sakesituRecord" property="torokuCd" maxlength="6" size="8" errorProperty="hasError" errorClass="error"
										styleClass="text ime_off numeric frm_lpad rigth searchable"
										styleId="torokuCdLbl" tabindex="true" indexed="true"
										onkeyup="nextTabMaxLenInput()"
										onblur="ctrlInput(FRM_CLASS_PAD, 6, '0');setRefObjAll('popup','hanbaiBunruiCd',this,'hanbaiBunruiRnm','torokuNm');getValueByAjaxForTorokuCd(this,'torokuNm');"
										onfocus="ctrlInput();setPreValueForAjax(this);" />
									</td>
									<%-- コード名称 --%>
									<td class="mjView">
										<kit:text
										name="sakesituRecord" maxlength="42" size="42"
										styleId="torokuNmLbl"
										property="torokuNm"
										styleClass="text ime_off left readonly"
										readonly="true" tabindex="false" indexed="true"
										onblur="ctrlInput()" onfocus="ctrlInput()" />
									</td>
								<kit:equal name="idx" value="3||7||11||15||19">
								</tr>
								</kit:equal>
							</kit:iterate>
						</table>
					</td>
				</tr>
				<tr id="syohinList">
					<td colspan="2">
						<table>
							<kit:iterate id="syohinRecord" name="jisekiESienList_Search" property="syohinList" indexId="idx" tabStartIndex="96">
								<kit:equal name="idx" value="0||4||8||12||16">
								<tr>
								</kit:equal>
									<td class="cdXView">
										<kit:text
										name="syohinRecord" property="torokuCd" maxlength="7" size="8" errorProperty="hasError" errorClass="error"
										styleClass="text ime_off numeric frm_lpad rigth searchable"
										styleId="torokuCdLbl" tabindex="true" indexed="true"
										onkeyup="nextTabMaxLenInput()"
										onblur="ctrlInput(FRM_CLASS_PAD, 7, '0');setRefObjAll('popup','shohinCd',this,'seihinNmReport2','torokuNm');getValueByAjaxForTorokuCd(this,'torokuNm');"
										onfocus="ctrlInput();setPreValueForAjax(this);" />
									</td>
									<%-- コード名称 --%>
									<td class="mjView">
										<kit:text
										name="syohinRecord" maxlength="42" size="42"
										styleId="torokuNmLbl"
										property="torokuNm"
										styleClass="text ime_off left readonly"
										readonly="true" tabindex="false" indexed="true"
										onblur="ctrlInput()" onfocus="ctrlInput()" />
									</td>
								<kit:equal name="idx" value="3||7||11||15||19">
								</tr>
								</kit:equal>
							</kit:iterate>
						</table>
					</td>
				</tr>
			</table>
			</div>
		</div>
		<div id="viewWaiting" style="z-index:1;"></div>
	</kit:form>
	<hr>
	<%-- 共通フッター開始 --%>
	<%@ include file="/parts/comFooter.jsp" %>
	<%-- 共通フッター終了 --%>
</body>
</kit:html>
