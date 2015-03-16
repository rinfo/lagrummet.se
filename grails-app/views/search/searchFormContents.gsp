
    <article id="searchResults" class="searchResults">
    	<p class="showAllLabel">Totalt ${searchResult?.totalResults+searchResultByCategory?.totalResults} träffar</p>
                
        <!--
        ********************************************************************************
          Tekniskt felmeddelande        
        ********************************************************************************
        -->
    	<g:if test="${searchResult?.errorMessages?.size > 0}">
    		<div class="message">
    			<ul>
    				<g:each in="${searchResult.errorMessages.unique()}" var="error">
    					<li><g:message code="${error}" /></li>
    				</g:each>
    			</ul>
    		</div>
    	</g:if>

        
    	<g:if test="${query}">
		<header><h1>Sökresultat för ${query.encodeAsHTML()}</h1></header>
	    </g:if>
		<g:if test="${searchResult?.totalResults || searchResultByCategory?.totalResults}">
		    <p>Visar sökresultat för <span class="query">"${query.encodeAsHTML()}"</span> i <strong>alla rättskällor</strong> på lagrummet.se</p>
		    <g:if test="${synonyms}">
		    <p>Din sökning gav även träff på följande: <span class="query">${synonyms.join(', ') }</span></p>
		    <p>För att se sökresultatet utan associerade träffar, <a href="${createLink(mapping:'search', params:[query:query, cat:'Alla', alias:'false']) }">klicka här</a></p>
		    </g:if>
		    <div class="column first">


                        <!--
                        ********************************************************************************
                            Information från lagrummet.se                        
                        ********************************************************************************
                        -->                  
                <p id="LagrummetHead">
                    <a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Ovrigt', alias:alias]) }" class="catTitle">Information från lagrummet.se</a>
                    <span class="count">(${searchResult.totalResults})
                    <g:if test="${searchResult.hasMoreResults()}"> Visar de första ${searchResult.itemsCount()}</g:if>
                    </span>
                </p>
                <g:if test="${searchResult.items}">
                <ul id="LagrummetList">
                    <g:each in="${searchResult.items}" var="item">
                        <li>
                            <p><a ${searchLink} href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
                            <g:if test="${item.matches}">
                                <p>${item.matches} ...</p>
                            </g:if>
                    </g:each>
                    <g:if test="${searchResult.hasMoreResults()}">
                      <li class="showAll"><a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Ovrigt', alias:alias]) }">Visa fler träffar</a></li>
                    </g:if>
                </ul>
                </g:if>
			
                       <!--
                        ********************************************************************************
                            Rättsfall
                        ********************************************************************************
                        -->
                <p id="RattsfallHead">
                    <g:if test="${grailsApplication.config.lagrummet.onlyLocalSearch}">
                      <span class="catTitle">Rättsfall</span>
                    </g:if>
                    <g:else>
                      <a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Rattsfall', alias:alias]) }" class="catTitle">Rättsfall</a>
                    </g:else>
                    <span class="count">(${searchResultByCategory.totalResultsPerCategory('Rattsfall')})
                    <g:if test="${searchResultByCategory.hasMoreResults('Rattsfall')}"> Visar de första ${searchResultByCategory.itemsCount('Rattsfall')}</g:if>
                    </span>
                    <g:if test="${grailsApplication.config.lagrummet.onlyLocalSearch}">
                      <br />Dessa dokument är inte sökbara i denna version av lagrummet.se.
                    </g:if>
                </p>
                <g:if test="${searchResultByCategory.items('Rattsfall')}">
                <ul id="RattsfallList">
                    <g:each in="${searchResultByCategory.items('Rattsfall')}" var="item">
                        <li>
                            <p><a ${searchLink} href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.identifier ?: item.malnummer}</a></p>
                            <g:if test="${item.matches}">
                                <p>${item.matches} ...</p>
                            </g:if>
                            <p class="type">${item.identifier}</p></li>
                    </g:each>
                    <g:if test="${searchResultByCategory.hasMoreResults('Rattsfall')}">
                        <li class="showAll"><a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Rattsfall', alias:alias]) }">Visa fler träffar</a></li>
                    </g:if>
                </ul>
                </g:if>

            </div> <!-- class="column first" -->
            <div class="column">


                       <!--
                        ********************************************************************************
                            Lagar och förordningar
                        ********************************************************************************
                        -->
                <p id="LagarHead">
                    <g:if test="${grailsApplication.config.lagrummet.onlyLocalSearch}">
                      <span class="catTitle">Lagar och förordningar</span>
                    </g:if>
                    <g:else>
                      <a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Lagar', alias:alias]) }" class="catTitle">Lagar och förordningar</a>
                    </g:else>
                    <span class="count">(${searchResultByCategory.totalResultsPerCategory('Lagar')})
                    <g:if test="${searchResultByCategory.hasMoreResults('Lagar')}"> Visar de första ${searchResultByCategory.itemsCount('Lagar')}</g:if>
                    </span>
                    <g:if test="${grailsApplication.config.lagrummet.onlyLocalSearch}">
                      <br />Dessa dokument är inte sökbara i denna version av lagrummet.se.
                    </g:if>
                </p>
                <g:if test="${searchResultByCategory.items('Lagar')}">
                <ul id="LagarList">
                    <g:each in="${searchResultByCategory.items('Lagar')}" var="item">
                        <li>
                            <p><a ${searchLink} href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
                            <g:if test="${item.text}">
                                <p>${item.text} ...</p>
                            </g:if>
                            <p class="type">${item.identifier}</p>
                            <g:if test="${item.ikrafttradandedatum}">
                                <p class="type">Ikraft: ${item.ikrafttradandedatum}</p>
                            </g:if>
                            </li>
                    </g:each>
                    <g:if test="${searchResultByCategory.hasMoreResults('Lagar')}">
                        <li class="showAll"><a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Lagar', alias:alias]) }">Visa fler träffar</a></li>
                    </g:if>
                </ul>
                </g:if>


                       <!--
                        ********************************************************************************
                            Myndigheters föreskrifter
                        ********************************************************************************
                        -->
                <p id="ForeskrifterHead">
                    <g:if test="${grailsApplication.config.lagrummet.onlyLocalSearch}">
                      <span class="catTitle">Myndigheters föreskrifter</span>
                    </g:if>
                    <g:else>
                    <a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Foreskrifter', alias:alias]) }" class="catTitle">Myndigheters föreskrifter</a>
                    </g:else>
                    <span class="count">(${searchResultByCategory.totalResultsPerCategory('Foreskrifter')})
                    <g:if test="${searchResultByCategory.hasMoreResults('Foreskrifter')}"> Visar de första ${searchResultByCategory.itemsCount('Foreskrifter')}</g:if>
                    </span>
                    <g:if test="${grailsApplication.config.lagrummet.onlyLocalSearch}">
                      <br />Dessa dokument är inte sökbara i denna version av lagrummet.se.
                    </g:if>
                </p>
                <g:if test="${searchResultByCategory.items('Foreskrifter')}">
                <ul id="ForeskrifterList">
                    <g:each in="${searchResultByCategory.items('Foreskrifter')}" var="item">
                        <li>
                                                  <!-- title or identifier -->
                            <p><a ${searchLink} href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
                            <g:if test="${item.matches}">
                                <p>${item.matches} ...</p>
                            </g:if>
                            <p class="type">${item.identifier}</p>
                            <g:if test="${item.ikrafttradandedatum}">
                                <p class="type">Ikraft: ${item.ikrafttradandedatum}</p>
                            </g:if>
                        </li>
                    </g:each>
                    <g:if test="${searchResultByCategory.hasMoreResults('Foreskrifter')}">
                        <li class="showAll"><a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Foreskrifter', alias:alias]) }">Visa fler träffar</a></li>
                    </g:if>
                </ul>
                </g:if>



    		</div> <!-- class="column" -->
		</g:if>
	</article>
	<div id="searchHelpPuff">
		<strong>Hittade du inte vad du sökte?</strong>
		<p><a href="${resource()}/sokhjalp">Sökhjälp</a> - Hjälpsida som ger dig tips på hur du kan söka på bästa sätt</p>
	</div>
