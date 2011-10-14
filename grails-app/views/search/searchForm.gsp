<html>
<head>
	<title>Sök</title>
	<meta name="layout" content="main"/>
</head>
<body>
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
					<g:if test="${item.matches}">
						<p>${item.matches} ...</p>
					</g:if>
					<p class="type">${item.identifier}</p></li>
			</g:each>
			<li class="showAll"><a href="#">Visa alla träffar (${searchResult?.totalResults})</a></li>
			</ul>
		</g:if>
	</article>
</div>
</body>
</html>