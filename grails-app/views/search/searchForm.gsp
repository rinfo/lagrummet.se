<html>
<head>
	<g:if test="${query}">
	<title>Sökresultat för ${query.encodeAsHTML()}</title>
	</g:if>
	<g:else>
	<title>Sök</title>
	</g:else>
	<meta name="layout" content="main"/>
</head>
<body>
    <article id="searchResults" class="searchResults">
    	<g:if test="${searchResult?.errorMessages?.size > 0}">
    		<div class="message">
    			<ul>
    				<g:each in="${searchResult.errorMessages}" var="error">
    					<li><g:message code="${error}" /></li>
    				</g:each>
    			</ul>
    		</div>
    	</g:if>
    	<g:if test="${query}">
		<header><h1>Sökresultat för ${query.encodeAsHTML()}</h1></header>
		</g:if>
		
		<g:if test="${searchResult?.totalResults}">
			<p>Totalt antal resultat: ${searchResult.totalResults}</p>
			
		<div class="column first">
			<p>
				<a href="${createLink(mapping:'search', params:[query:query, cat:'Ovrigt']) }" class="catTitle">Information från lagrummet.se</a> 
				<span class="count">(${searchResult.totalResultsPerCategory['Ovrigt']})
				<g:if test="${searchResult.items['Ovrigt'].size() > 0 && searchResult.totalResultsPerCategory['Ovrigt'] > searchResult.items['Ovrigt'].size()}"> Visar de första ${searchResult.items['Ovrigt'].size()}</g:if>
				</span>
			</p>
			<g:if test="${searchResult.items['Ovrigt']}">
			<ul>
				<g:each in="${searchResult.items['Ovrigt']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
				</g:each>
				<li class="showAll"><a href="${createLink(mapping:'search', params:[query:query, cat:'Ovrigt']) }">Visa fler träffar</a></li>
			</ul>
			</g:if>
			
		
			<p>
				<a href="${createLink(mapping:'search', params:[query:query, cat:'Propositioner']) }" class="catTitle">Propositioner och skrivelser</a> 
				<span class="count">(${searchResult.totalResultsPerCategory['Propositioner']})
				<g:if test="${searchResult.items['Propositioner'].size() > 0 && searchResult.totalResultsPerCategory['Propositioner'] > searchResult.items['Propositioner'].size()}"> Visar de första ${searchResult.items['Propositioner'].size()}</g:if>
				
				</span>
			</p>
			<g:if test="${searchResult.items['Propositioner']}">
			<ul>
				<g:each in="${searchResult.items['Propositioner']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<li class="showAll"><a href="${createLink(mapping:'search', params:[query:query, cat:'Propositioner']) }">Visa fler träffar</a></li>
			</ul>
			</g:if>

			<p>
				<a href="${createLink(mapping:'search', params:[query:query, cat:'Rattsfall']) }" class="catTitle">Rättsfall</a> 
				<span class="count">(${searchResult.totalResultsPerCategory['Rattsfall']})
				<g:if test="${searchResult.items['Rattsfall'].size() > 0 && searchResult.totalResultsPerCategory['Rattsfall'] > searchResult.items['Rattsfall'].size()}"> Visar de första ${searchResult.items['Rattsfall'].size()}</g:if>
				</span>
			</p>
			<g:if test="${searchResult.items['Rattsfall']}">
			<ul>
				<g:each in="${searchResult.items['Rattsfall']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<li class="showAll"><a href="${createLink(mapping:'search', params:[query:query, cat:'Rattsfall']) }">Visa fler träffar</a></li>
			</ul>
			</g:if>
			
		</div>
		
		<div class="column">
			<p>
				<a href="${createLink(mapping:'search', params:[query:query, cat:'Lagar']) }" class="catTitle">Lagar och förordningar</a> 
				<span class="count">(${searchResult.totalResultsPerCategory['Lagar']})
				<g:if test="${searchResult.items['Lagar'].size() > 0 && searchResult.totalResultsPerCategory['Lagar'] > searchResult.items['Lagar'].size()}"> Visar de första ${searchResult.items['Lagar'].size()}</g:if>
				</span>
			</p>
			<g:if test="${searchResult.items['Lagar']}">
			<ul>
				<g:each in="${searchResult.items['Lagar']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p>
						<g:if test="${item.ikrafttradandedatum}">
							<p class="type">Ikraft: ${item.ikrafttradandedatum}</p>
						</g:if>
						</li>
				</g:each>
				<li class="showAll"><a href="${createLink(mapping:'search', params:[query:query, cat:'Lagar']) }">Visa fler träffar</a></li>
			</ul>
			</g:if>
			
			<p>
				<a href="${createLink(mapping:'search', params:[query:query, cat:'Foreskrifter']) }" class="catTitle">Myndigheters föreskrifter</a> 
				<span class="count">(${searchResult.totalResultsPerCategory['Foreskrifter']})
				<g:if test="${searchResult.items['Foreskrifter'].size() > 0 && searchResult.totalResultsPerCategory['Foreskrifter'] > searchResult.items['Foreskrifter'].size()}"> Visar de första ${searchResult.items['Foreskrifter'].size()}</g:if>
				</span>
			</p>
			<g:if test="${searchResult.items['Foreskrifter']}">
			<ul>
				<g:each in="${searchResult.items['Foreskrifter']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p>
						<g:if test="${item.ikrafttradandedatum}">
							<p class="type">Ikraft: ${item.ikrafttradandedatum}</p>
						</g:if>					
					</li>
				</g:each>
				<li class="showAll"><a href="${createLink(mapping:'search', params:[query:query, cat:'Foreskrifter']) }">Visa fler träffar</a></li>
			</ul>
			</g:if>

			<p>
				<a href="${createLink(mapping:'search', params:[query:query, cat:'Utredningar']) }" class="catTitle">Utredningar</a>
				<span class="count">(${searchResult.totalResultsPerCategory['Utredningar']})
				<g:if test="${searchResult.items['Utredningar'].size() > 0 && searchResult.totalResultsPerCategory['Utredningar'] > searchResult.items['Utredningar'].size()}"> Visar de första ${searchResult.items['Utredningar'].size()}</g:if>
				</span>
			</p>
			<g:if test="${searchResult.items['Utredningar']}">
			<ul>
				<g:each in="${searchResult.items['Utredningar']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<li class="showAll"><a href="${createLink(mapping:'search', params:[query:query, cat:'Utredningar']) }">Visa fler träffar</a></li>
			</ul>
			</g:if>
			
		</div>
		</g:if>
	</article>
	<div id="searchHelpPuff">
		<strong>Hittade du inte vad du sökte?</strong>
		<p><a href="/sokhjalp">Sökhjälp</a> - Hjälpsida som ger dig tips på hur du kan söka på bästa sätt</p>
	</div>
</body>
</html>