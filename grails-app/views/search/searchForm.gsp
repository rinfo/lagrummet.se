<g:if test="${!excludeBody}"><html>
<head>
	<g:if test="${query}">
	<title>Sökresultat för ${query.encodeAsHTML()}</title>
	</g:if>
	<g:else>
	<title>Sök</title>
	</g:else>
	<meta name="layout" content="${grailsApplication.config.lagrummet.mainLayoutName}"/>
</head></g:if>
<body>
    ${contents}
</body>
</html>