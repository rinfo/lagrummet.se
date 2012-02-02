<html>
<head>
	<title>Sökresultat för ${query.encodeAsHTML()}</title>
	<meta name="layout" content="main"/>
</head>
<body>
    <article id="searchResults" class="searchResults">
    	<p class="printLabel"><a href="javascript:if(window.print)window.print()">Skriv ut</a></p>
    	<p class="showAllLabel"><a href="${createLink(mapping:'search', params:[query:query, cat: cat, max: searchResult?.totalResults, offset: 0]) }">Visa alla ${searchResult?.totalResults} träffar</a></p>
    	
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
			<header><h1>Sökresultat för ${query.encodeAsHTML()}</h1></header>
			<p>Visar ${1+(offset ?: 0)  }-${(offset ?: 0)+searchResult.items[(cat)].size()} av ${searchResult.totalResults} träffar för <span class="query">"${query.encodeAsHTML()}"</span> i <strong><g:message code="category.${cat}"/></strong></p>
			
			<table>
				<tr>
					<th>Titel</th>
					<g:if test="${cat == 'Lagar' }"><th>SFS-nummer</th></g:if>
					<g:elseif test="${cat != 'Ovrigt' }"><th>Identifierare</th></g:elseif>
				</tr>
				<g:each in="${searchResult.items[(cat)]}" var="item">
					<tr>
						<td>
							<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
							<g:if test="${item.matches}">
								<p>${item.matches} ...</p>
							</g:if>
							<g:if test="${item.ikrafttradandedatum}">
								<p class="type">Ikraft: ${item.ikrafttradandedatum}</p>
							</g:if>
						</td>
						<g:if test="${cat != 'Ovrigt' }"><td>${item.identifier}</td></g:if>
					</tr>
				</g:each>
			</table>
			<g:paginate total="${searchResult.totalResults}" max="20" params="${[query: query, cat: cat]}"/>
		</g:if>
	</article>
	<div id="searchHelpPuff">
		<strong>Hittade du inte vad du sökte?</strong>
		<p><a href="/sokhjalp">Sökhjälp</a> - Hjälpsida som ger dig tips på hur du kan söka på bästa sätt</p>
	</div>
</body>
</html>