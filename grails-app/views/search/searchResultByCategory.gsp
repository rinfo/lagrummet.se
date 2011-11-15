<html>
<head>
	<title>Sökresultat för ${query.encodeAsHTML()}</title>
	<meta name="layout" content="main"/>
</head>
<body>
<div id="content">
    <article id="searchResults" class="searchResults">
    	<p class="showAllLabel"><a href="${createLink(mapping:'search', params:[query:query, cat: cat, max: searchResult.totalResults, offset: 0]) }">Visa alla ${searchResult.totalResults} träffar</a></p>
    	<g:if test="${searchResult?.errorMessages?.size > 0}">
    		<div class="message">
    			<ul>
    				<g:each in="${searchResult.errorMessages}" var="message">
    					<li>${message}</li>
    				</g:each>
    			</ul>
    		</div>
    	</g:if>

		<g:if test="${searchResult?.totalResults}">
			<p>Visar ${1+(offset ?: 0)  }-${(offset ?: 0)+searchResult.items[(cat)].size()} av ${searchResult.totalResults} träffar för <span class="query">"${query.encodeAsHTML()}"</span> i <strong><g:message code="category.${cat}"/></strong></p>
			
			<table>
				<tr>
					<th><a href="#">Titel</a></th>
					<g:if test="${cat == 'Lagar' }"><th><a href="#">SFS-nummer</a></th></g:if>
					<g:elseif test="${cat != 'Ovrigt' }"><th><a href="#">Identifierare</a></th></g:elseif>
				</tr>
				<g:each in="${searchResult.items[(cat)]}" var="item">
					<tr>
						<td>
							<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
							<g:if test="${item.matches}">
								<p>${item.matches} ...</p>
							</g:if>
						</td>
						<g:if test="${cat != 'Ovrigt' }"><td>${item.identifier}</td></g:if>
					</tr>
				</g:each>
			</table>
			<g:paginate total="${searchResult.totalResults}" max="20" params="${[query: query, cat: cat]}"/>
		</g:if>
	</article>
</div>
</body>
</html>