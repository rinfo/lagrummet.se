<html>
<head>
	<g:if test="${query}">
	<title>Sökresultat för ${query.encodeAsHTML()}</title>
	</g:if>
	<g:else>
	<title>Sök</title>
	</g:else>
	<meta name="layout" content="${grailsApplication.config.lagrummet.mainLayoutName}"/>
</head><
<body>
    ${contents}
</body>
</html>