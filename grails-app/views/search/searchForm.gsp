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
			
			Rättsfall:<br/>
			<g:each in="${searchResult.items['Rattsfall']}" var="item">
				item: ${item }
			</g:each>
			<br/>
			Propositioner:<br/>
			<g:each in="${searchResult.items['Propositioner']}" var="item">
				item: ${item }
			</g:each>
			<br/>
			Lagar:<br/>
			<g:each in="${searchResult.items['Lagar']}" var="item">
				item: ${item }
			</g:each>

			<br/>okänd:<br/>
			<g:each in="${searchResult.items['Okand']}" var="item">
				item: ${item }
			</g:each>
			
		</g:if>
	</article>
</div>
<footer id="siteFooter">${siteProps.footer}</footer>
</body>
</html>