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
    <article id="searchResults">
		<header><h1>Soek</h1></header>
		<g:form mapping="search" method="GET">
			<g:textField name="query"/>
			<g:submitButton name="submit" value="S�k"/>
		</g:form>
		
		<g:if test="${query}">
			${query }
		</g:if>
		
		<g:if test="${searchResult?.totalResults}">
			Total results: ${searchResult.totalResults}
			
			<p><strong>Information från lagrummet.se</strong> <span class="count">(${searchResult.totalResults})</span></p>
			<ul id="redaktionellt">
			<g:each in="${searchResult.items}" var="item">
				<li>
					<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title}</a></p>
					<g:if test="${item.matches.title.size() > 0}">
						<p>${item.matches.title[0]} ...</p>
					</g:if>
					<g:elseif test="${item.matches.content.size() > 0}">
						<p>${item.matches.content[0]} ...</p>
					</g:elseif>
					<p class="type">${item.identifier}</p></li>
			</g:each>
			<li class="showAll"><a href="#">Visa alla träffar (${searchResult?.totalResults})</a></li>
			</ul>
		</g:if>
	</article>
</div>
<footer id="siteFooter">${siteProps.footer}</footer>
</body>
</html>