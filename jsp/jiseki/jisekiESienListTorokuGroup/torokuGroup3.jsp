<%@ page import="java.util.*,fb.com.Menu.*"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-kit.tld" prefix="kit"%>

<div id="list" class="fixedTitleX">
	<%-- タイトル行 --%>
	<table id="listTable" class="fixedTitleTable3 list" style="margin:0;">
		<colgroup class="noWidth"></colgroup>
		<colgroup class="codeCdWidth"></colgroup>
		<colgroup class="cdNmWidth"></colgroup>
		<colgroup class="youkiNmWidth"></colgroup>
		<colgroup class="caseIrisuWidth"></colgroup>
		<colgroup class="tnpnVolWidth"></colgroup>
		<thead>
			<tr class="head">
				<td>&nbsp;</td>
				<td><bean:message key="com.text.syohinCd"/></td>
				<td><bean:message key="com.text.syohnNm"/></td>
				<td><bean:message key="com.text.youkinm"/></td>
				<td><bean:message key="com.text.iriSu"/></td>
				<td><bean:message key="com.text.youryo"/></td>
			</tr>
		</thead>
	</table>
	<%-- 表領域開始 --%>
	<div id="listDetails" class="fixedTitleY">
		<table id="detailsTable" class="fixedTitleTable3 list" style="margin-top:-1pt;margin-bottom:20pt">
			<colgroup class="noWidth"></colgroup>
			<colgroup class="codeCdWidth"></colgroup>
			<colgroup class="cdNmWidth"></colgroup>
			<colgroup class="youkiNmWidth"></colgroup>
			<colgroup class="caseIrisuWidth"></colgroup>
			<colgroup class="tnpnVolWidth"></colgroup>
			<tbody>
			<%-- レコード行 --%>
			<kit:iterate id="jisekiESienListTorokuGroupRecord" name="jisekiESienListTorokuGroup_Edit" property="torokuGroupList"
				indexId="idx" tabStartIndex="1" scope="session">
				<kit:tr name="jisekiESienListTorokuGroupRecord" id="id" indexed="true" styleClass="line" evenClass="even" oddClass="odd" errorProperty="hasError" errorClass="" modProperty="isModified" modClass="modified">
					<%-- No --%>
					<td class="cdXView" ><%=idx + 1 %></td>
					<%-- コード --%>
					<kit:td name="jisekiESienListTorokuGroupRecord" property="torokuCdClass" styleClass="cdXView">
						<kit:write name="jisekiESienListTorokuGroupRecord" property="torokuCdView" ignore="true" /><br>
						<kit:text
						name="jisekiESienListTorokuGroupRecord" property="torokuCd" maxlength="7" size="10" errorProperty="hasError" errorClass="js_error"
						styleClass="text ime_off numeric frm_lpad rigth searchable"
						styleId="torokuCdLbl" tabindex="true" indexed="true"
						onkeyup="nextTabMaxLenInput()"
						onblur="ctrlInput(FRM_CLASS_PAD, 7, '0');setRefObjAll('popup','shohinCd',this,'seihinNmReport2','torokuNm','seihinYoukiKigoNm2','youkiNm','caseIrisu','caseIrisu','tnpnVol','tnpnVol');getValueByAjaxForTorokuCd(this,'torokuNm,youkiNm,caseIrisu,tnpnVol');"
						onfocus="ctrlInput();setPreValueForAjax(this);" />
					</kit:td>
					<%-- コード名称 --%>
					<kit:td name="jisekiESienListTorokuGroupRecord" property="torokuNmClass" styleClass="mjView" >
					    <kit:write name="jisekiESienListTorokuGroupRecord" property="torokuNmView" ignore="true" /><br>
						<kit:text
						name="jisekiESienListTorokuGroupRecord" maxlength="42" size="45"
						styleId="torokuNmLbl"
						property="torokuNm"
						styleClass="text ime_off left readonly"
						readonly="true" tabindex="false" indexed="true"
						onblur="ctrlInput()" onfocus="ctrlInput()" />
					</kit:td>
					<%-- 容器名 --%>
					<kit:td name="jisekiESienListTorokuGroupRecord" property="youkiNmClass" styleClass="mjView" >
					    <kit:write name="jisekiESienListTorokuGroupRecord" property="youkiNmView" ignore="true" /><br>
						<kit:text
						name="jisekiESienListTorokuGroupRecord" maxlength="10" size="15"
						styleId="youkiNmLbl"
						property="youkiNm"
						styleClass="text ime_off left readonly"
						readonly="true" tabindex="false" indexed="true"
						onblur="ctrlInput()" onfocus="ctrlInput()" />
					</kit:td>
					<%-- 入数 --%>
					<kit:td name="jisekiESienListTorokuGroupRecord" property="caseIrisuClass" styleClass="nmView" >
					    <kit:write name="jisekiESienListTorokuGroupRecord" property="caseIrisuView" ignore="true" /><br>
						<kit:text
						name="jisekiESienListTorokuGroupRecord" maxlength="8" size="8"
						styleId="caseIrisuLbl"
						property="caseIrisu"
						styleClass="text ime_off number right readonly"
						readonly="true" tabindex="false" indexed="true"
						onblur="ctrlInput()" onfocus="ctrlInput()" />
					</kit:td>
					<%-- 容量 --%>
					<kit:td name="jisekiESienListTorokuGroupRecord" property="tnpnVolClass" styleClass="nmView" >
					    <kit:write name="jisekiESienListTorokuGroupRecord" property="tnpnVolView" ignore="true" /><br>
						<kit:text
						name="jisekiESienListTorokuGroupRecord" maxlength="9" size="14"
						styleId="tnpnVolLbl"
						property="tnpnVol"
						styleClass="text ime_off number right readonly"
						readonly="true" tabindex="false" indexed="true"
						onblur="ctrlInput()" onfocus="ctrlInput()" />
					</kit:td>
				</kit:tr>
			</kit:iterate>
			</tbody>
		</table>
	</div>
</div><%-- / class="fixedTitleX" --%>