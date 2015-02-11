
    <article id="searchResults" class="searchResults">
    	<p class="showAllLabel">Totalt ${searchResult?.totalResults} träffar</p>
                
        <!--
        ********************************************************************************
          Tekniskt felmeddelande        
        ********************************************************************************
        -->
        <!--
    	<g:if test="${searchResult?.errorMessages?.size > 0}">
    		<div class="message">
    			<ul>
    				<g:each in="${searchResult.errorMessages}" var="error">
    					<li><g:message code="${error}" /></li>
    				</g:each>
    			</ul>
    		</div>
    	</g:if>
        -->
        
        
    	<g:if test="${query}">
		<header><h1>Sökresultat för ${query.encodeAsHTML()}</h1></header>
	</g:if>
		
		<g:if test="${searchResult?.totalResults}">
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
				<span class="count">(${searchResult.totalResultsPerCategory['Ovrigt']})
				<g:if test="${searchResult.items['Ovrigt'].size() > 0 && searchResult.totalResultsPerCategory['Ovrigt'] > searchResult.items['Ovrigt'].size()}"> Visar de första ${searchResult.items['Ovrigt'].size()}</g:if>
				</span>
			</p>
			<g:if test="${searchResult.items['Ovrigt']}">
			<ul id="LagrummetList">
				<g:each in="${searchResult.items['Ovrigt']}" var="item">
					<li>
						<p><a ${searchLink} href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
				</g:each>
				<g:if test="${searchResult.totalResultsPerCategory['Ovrigt'] > searchResult.maxItemsPerCategory}">
				  <li class="showAll"><a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Ovrigt', alias:alias]) }">Visa fler träffar</a></li>
				</g:if>
			</ul>
			</g:if>
			
                       <!--
                        ********************************************************************************
                            Propositioner och skrivelser                        
                        ********************************************************************************
                        --> 		
			<p id="PropHead">
				<!--
                                  <a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Propositioner', alias:alias]) }" class="catTitle">Propositioner och skrivelser</a> 
                                 -->
                                 <span class="catTitle">Propositioner och skrivelser</span>
				<span class="count">(${searchResult.totalResultsPerCategory['Propositioner']})
				<g:if test="${searchResult.items['Propositioner'].size() > 0 && searchResult.totalResultsPerCategory['Propositioner'] > searchResult.items['Propositioner'].size()}"> Visar de första ${searchResult.items['Propositioner'].size()}</g:if>				
				</span>
                                 <br />Dessa dokument är inte sökbara i denna version av lagrummet.se.
                                 
			</p>
			<g:if test="${searchResult.items['Propositioner']}">
			<ul id="PropList">
				<g:each in="${searchResult.items['Propositioner']}" var="item">
					<li>
						<p><a ${searchLink} href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<g:if test="${searchResult.totalResultsPerCategory['Propositioner'] > searchResult.maxItemsPerCategory}">
				    <li class="showAll"><a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Propositioner', alias:alias]) }">Visa fler träffar</a></li>
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
				<span class="count">(${searchResult.totalResultsPerCategory['Rattsfall']})
				<g:if test="${searchResult.items['Rattsfall'].size() > 0 && searchResult.totalResultsPerCategory['Rattsfall'] > searchResult.items['Rattsfall'].size()}"> Visar de första ${searchResult.items['Rattsfall'].size()}</g:if>
				</span>
				<g:if test="${grailsApplication.config.lagrummet.onlyLocalSearch}">
				  <br />Dessa dokument är inte sökbara i denna version av lagrummet.se.
				</g:if>
			</p>
			<g:if test="${searchResult.items['Rattsfall']}">
			<ul id="RattsfallList">
				<g:each in="${searchResult.items['Rattsfall']}" var="item">
					<li>
						<p><a ${searchLink} href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.identifier ?: item.malnummer}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<g:if test="${searchResult.totalResultsPerCategory['Rattsfall'] > searchResult.maxItemsPerCategory}">
				    <li class="showAll"><a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Rattsfall', alias:alias]) }">Visa fler träffar</a></li>
				</g:if>
			</ul>
			</g:if>
			
		</div>
		
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
				<span class="count">(${searchResult.totalResultsPerCategory['Lagar']})
				<g:if test="${searchResult.items['Lagar'].size() > 0 && searchResult.totalResultsPerCategory['Lagar'] > searchResult.items['Lagar'].size()}"> Visar de första ${searchResult.items['Lagar'].size()}</g:if>
				</span>
				<g:if test="${grailsApplication.config.lagrummet.onlyLocalSearch}">
				  <br />Dessa dokument är inte sökbara i denna version av lagrummet.se.
				</g:if>
			</p>
			<g:if test="${searchResult.items['Lagar']}">
			<ul id="LagarList">
				<g:each in="${searchResult.items['Lagar']}" var="item">
					<li>
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
				<g:if test="${(searchResult.totalResultsPerCategory['Lagar']> searchResult.maxItemsPerCategory)}">
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
				<span class="count">(${searchResult.totalResultsPerCategory['Foreskrifter']})
				<g:if test="${searchResult.items['Foreskrifter'].size() > 0 && searchResult.totalResultsPerCategory['Foreskrifter'] > searchResult.items['Foreskrifter'].size()}"> Visar de första ${searchResult.items['Foreskrifter'].size()}</g:if>
				</span>
				<g:if test="${grailsApplication.config.lagrummet.onlyLocalSearch}">
				  <br />Dessa dokument är inte sökbara i denna version av lagrummet.se.
				</g:if>
			</p>
			<g:if test="${searchResult.items['Foreskrifter']}">
			<ul id="ForeskrifterList">
				<g:each in="${searchResult.items['Foreskrifter']}" var="item">
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
				<g:if test="${searchResult.totalResultsPerCategory['Foreskrifter'] > searchResult.maxItemsPerCategory}">
				    <li class="showAll"><a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Foreskrifter', alias:alias]) }">Visa fler träffar</a></li>
				</g:if>
			</ul>
			</g:if>

                        
                        
                       <!--
                        ********************************************************************************
                            Utredningar                     
                        ********************************************************************************
                        -->                         
			<p>
                            <!--
				<a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Utredningar', alias:alias]) }" class="catTitle">Utredningar</a>
                             -->
                             <span class="catTitle">Utredningar</span>
				<span class="count">(${searchResult.totalResultsPerCategory['Utredningar']})
				<g:if test="${searchResult.items['Utredningar'].size() > 0 && searchResult.totalResultsPerCategory['Utredningar'] > searchResult.items['Utredningar'].size()}"> Visar de första ${searchResult.items['Utredningar'].size()}</g:if>
				</span>
                                <br />Dessa dokument är inte sökbara i denna version av lagrummet.se.
			</p>
			<g:if test="${searchResult.items['Utredningar']}">
			<ul>
				<g:each in="${searchResult.items['Utredningar']}" var="item">
					<li>
						<p><a ${searchLink} href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<g:if test="${searchResult.totalResultsPerCategory['Utredningar'] > searchResult.maxItemsPerCategory}">
				    <li class="showAll"><a href="${createLinkParams(mapping:'search', params:[query:query, cat:'Utredningar', alias:alias]) }">Visa fler träffar</a></li>
				</g:if>
			</ul>
			</g:if>
			
		</div>
		</g:if>
	</article>
	<div id="searchHelpPuff">
		<strong>Hittade du inte vad du sökte?</strong>
		<p><a href="${resource()}/sokhjalp">Sökhjälp</a> - Hjälpsida som ger dig tips på hur du kan söka på bästa sätt</p>
	</div>
