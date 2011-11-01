<html>
<head>
	<title>Sökresultat för ${query.encodeAsHTML()}</title>
	<meta name="layout" content="main"/>
</head>
<body>
<div id="content">
    <article id="searchResults" class="searchResults">
    	<g:if test="${searchResult?.errorMessages?.size > 0}">
    		<div class="message">
    			<ul>
    				<g:each in="${searchResult.errorMessages}" var="message">
    					<li>${message}</li>
    				</g:each>
    			</ul>
    		</div>
    	</g:if>
    	
    	<div>
    	Visar 1-10 av 10 träffar för "${query.encodeAsHTML()}" i Lagar och förordningar
    	</div>
		
		<g:if test="${searchResult?.totalResults}">
			<p>Totalt antal resultat: ${searchResult.totalResults}</p>
			
		<div class="column">
			<p><a href="${resource()}/search?query=${query.encodeAsURL()}"><strong>Information från lagrummet.se</strong> <span class="count">(${searchResult.totalResultsPerCategory[(cat)]})</span></a></p>
			<g:if test="${searchResult.items[(cat)]}">
				<ul>
				<g:each in="${searchResult.items[(cat)]}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
				</g:each>
				<li class="showAll"><a href="${createLink(mapping:'search', params:[query:query, cat: cat]) }">Visa fler tr�ffar</a></li>
				</ul>
			</g:if>
			
		</div>
		</g:if>
	</article>
</div>
</body>
</html>