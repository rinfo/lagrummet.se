    <article id="searchResults" class="searchResults">
    	<p class="printLabel"><a href="javascript:if(window.print)window.print()">Skriv ut</a></p>
    	<g:if test="${offset!=0||searchResult.itemsCount()!=searchResult.totalResults}">
        <p class="showAllLabel"><a href="${createLink(mapping:'search', params:[query:query, cat: cat, max: searchResult?.totalResults, offset: 0, alias: alias]) }">Visa alla ${searchResult?.totalResults} träffar</a></p>
        </g:if>

            <!--
            ********************************************************************************
              Sökresultat per kategori
            ********************************************************************************
            -->
		<g:if test="${searchResult?.totalResults}">
			<header><h1>Sökresultat för ${query.encodeAsHTML()}</h1></header>
			<p>Visar ${1+(offset ?: 0)  }-${(offset ?: 0)+searchResult.itemsCount()} av ${searchResult.totalResults} träffar för <span class="query">"${query.encodeAsHTML()}"</span> <strong><g:message code="category.${cat}"/></strong></p>
			<g:if test="${synonyms}">
			<p>Din sökning gav även träff på följande: <g:each in="${synonyms}"><span class="query">${it}</span>, </g:each></p>
			<p>För att se sökresultatet utan associerade träffar, <a href="${createLink(mapping:'search', params:[query:query, cat:cat, alias:'false']) }">klicka här</a></p>
			</g:if>
			<table>
				<tr>
					<th>Titel</th>
					<g:if test="${cat == 'Lagar' }"><th>SFS-nummer</th></g:if>
					<g:elseif test="${cat == 'Rattsfall' }"><th>Referat/Dom</th></g:elseif>
					<g:elseif test="${cat != 'Ovrigt' }"><th>Beteckning</th></g:elseif>
				</tr>
				<g:each in="${searchResult.items}" var="item">

					<tr>
						<td>
							<p>
                                <a ${searchLink} href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">
                                  <g:if test="${cat == 'Lagar' || item.type == 'Myndighetsforeskrift'}">
                                        ${item.title}                                 
                                  </g:if>                                  
                                  <g:else>
                                        ${item.identifier ?: item.malnummer ?: item.title}                                  
                                  </g:else>
                                </a>
                            </p>
							<g:if test="${item.matches}">
								<p>${item.matches} ...</p>
							</g:if>
							<g:if test="${item.ikrafttradandedatum}">
								<p class="type">Ikraft: ${item.ikrafttradandedatum}</p>
							</g:if>
						</td>
						<g:if test="${cat != 'Ovrigt' }"><td>${item.identifier ?: item.malnummer}</td></g:if>
					</tr>
				</g:each>
			</table>
			<g:if test="${offset!=0||searchResult.itemsCount()!=searchResult.totalResults}">
			<g:paginate offset="${offset}" controller="search" total="${searchResult.totalResults}" max="20" params="${[query: query, cat: cat, alias: alias, offset: offset]}"/>
			</g:if>
		</g:if>
		<g:else>
            <header><h1>Hittade inga sökresultat för ${query.encodeAsHTML()}</h1></header>
		</g:else>
	</article>
	<div id="searchHelpPuff">
		<strong>Hittade du inte vad du sökte?</strong>
		<p><a href="${resource()}/sokhjalp">Sökhjälp</a> - Hjälpsida som ger dig tips på hur du kan söka på bästa sätt</p>
	</div>
