<%@ page import="java.util.*,fb.com.Menu.*"%>
<%@ page language="java"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-kit.tld" prefix="kit"%>

<div id="list" class="fixedTitleX">
	<%-- タイトル行 --%>
	<table id="listTable" class="fixedTitleTable list" style="margin:0;">
		<colgroup class="noWidth"></colgroup>
		<colgroup class="codeCdWidth"></colgroup>
		<colgroup class="cdNmWidth"></colgroup>
		<thead>
			<tr class="head">
				<td>&nbsp;</td>
				<td><bean:message key="com.text.sakesituCd"/></td>
				<td><bean:message key="com.text.sakesituNm"/></td>
			</tr>
		</thead>
	</table>
	<%-- 表領域開始 --%>
	<div id="listDetails" class="fixedTitleY">
		<table id="detailsTable" class="fixedTitleTable list" style="margin-top:-1pt;margin-bottom:20pt">
			<colgroup class="noWidth"></colgroup>
			<colgroup class="codeCdWidth"></colgroup>
			<colgroup class="cdNmWidth"></colgroup>
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
						name="jisekiESienListTorokuGroupRecord" property="torokuCd" maxlength="6" size="10" errorProperty="hasError" errorClass="js_error"
						styleClass="text ime_off numeric frm_lpad rigth searchable"
						styleId="torokuCdLbl" tabindex="true" indexed="true"
						onkeyup="nextTabMaxLenInput()"
						onblur="ctrlInput(FRM_CLASS_PAD, 6, '0');setRefObjAll('popup','hanbaiBunruiCd',this,'hanbaiBunruiRnm','torokuNm');getValueByAjaxForTorokuCd(this,'torokuNm');"
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
				</kit:tr>
			</kit:iterate>
			</tbody>
		</table>
	</div>
</div><%-- / class="fixedTitleX" --%>