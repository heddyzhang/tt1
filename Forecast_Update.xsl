<?xml version="1.0" encoding="UTF-8" ?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<!--for local parse IE5.5 under <xsl:stylesheet xmlns:xsl="http://www.w3.org/TR/WD-xsl" > -->
<xsl:output method="html" version="4.0" encoding="UTF-8" />

<!--
/////////////////////////////////////////////////////
// Forecast変更終了画面
/////////////////////////////////////////////////////
// 更新履歴
//  Ver.     日付          担当者          変更内容
//  1.0      2002/05/23    坪内章太郎      新規作成
//
/////////////////////////////////////////////////////
-->

<!--変数-->
<xsl:variable name="uri" select="/doc-root/header/uri"/>
<xsl:variable name="css_uri" select="/doc-root/header/css_uri"/>
<xsl:variable name="javascript_uri" select="/doc-root/header/javascript_uri"/>
<xsl:variable name="code_name" select="/doc-root/header/code_name"/>

<xsl:template match="/">
<html>

	<head>
		<title>Forecast変更終了</title>

		<!-- CSS -->
		<link>
			<xsl:attribute name="href"><xsl:value-of select="$css_uri"/></xsl:attribute>
			<xsl:attribute name="rel">stylesheet</xsl:attribute>
			<xsl:attribute name="type">text/css</xsl:attribute>
		</link>

		<!-- JavaScript -->
		<script>
			<xsl:attribute name="src"><xsl:value-of select="$javascript_uri"/></xsl:attribute>
		</script>
	</head>

	<body onLoad="update_msg();self.close();"/>
</html>
</xsl:template>

</xsl:stylesheet>
