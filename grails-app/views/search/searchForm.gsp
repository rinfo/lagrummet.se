<html>
<head>
	<title>Sök</title>
	<meta name="layout" content="main"/>
</head>
<body>
<nav id="primaryNavigation">
	<div id="logo">
		<a href="${resource()}">${siteProps.siteTitle}</a>
	</div>
	${siteProps.primaryNavigation}
</nav>
<header id="siteHeader">
	<nav id="sitelinks">
		${siteProps.headerNavigation}
	</nav>
	<nav id="breadcrumbs">
		<g:breadcrumbs parent="${page?.parent}" />
	</nav>
</header>
<div id="content">
    <article id="editorial">
		<header><h1>Soek</h1></header>
		<g:form mapping="search" method="GET">
			<g:textField name="query"/>
			<g:submitButton name="submit" value="Sök"/>
		</g:form>
		
		<g:if test="${query}">
			${query }
		</g:if>
		
		<g:if test="${searchResult?.totalResults}">
			Total results: ${searchResult.totalResults}
			
			<li>
			<g:each in="${searchResult.items}" var="item">
				<ul><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.identifier}</a></ul>
			</g:each>
			</li>
		</g:if>
	</article>
</div>
<footer id="siteFooter">${siteProps.footer}</footer>
</body>
</html>