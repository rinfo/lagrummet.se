<html>
<head>
	<title>${docInfo.identifier} - ${docInfo.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
<header>
	<div id="logo">
		${siteProps.siteTitle}
	</div>
	<nav class="sitelinks">
		${siteProps.headerNavigation}
	</nav>
	<nav class="breadcrumbs">
		<g:breadcrumbs parent="${page.parent}" />
	</nav>
</header>
<div id="content">
	<nav id="primaryNavigation">${siteProps.primaryNavigation}</nav>
    <article>
		<header><h1>${docInfo.title}</h1></header>
		<g:if test="${docEntry.link[1].@type == 'text/html'}"> 
			${content }
		</g:if>
		<g:else>
			<a href="${grailsApplication.config.lagrummet.rinfo.baseurl + docEntry.link[1].@href}">PDF</a>
		</g:else>
		
	</article>
</div>
<footer>${siteProps.footer}</footer>

</body>
</html>