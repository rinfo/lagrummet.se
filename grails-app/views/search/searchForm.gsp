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
    	<g:if test="${query}">
		<header><h1>Sökresultat för ${query.encodeAsHTML()}</h1></header>
		</g:if>
		
		<g:if test="${searchResult?.totalResults}">
			<p>Totalt antal resultat: ${searchResult.totalResults}</p>
			
		<div class="column">
			<p><a href="${createLink(mapping:'search', params:[query:query, cat:'Ovrigt']) }" class="catTitle">Information från lagrummet.se</a> <span class="count">(${searchResult.totalResultsPerCategory['Ovrigt']})</span></p>
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
			
		
			<p><a href="${createLink(mapping:'search', params:[query:query, cat:'Propositioner']) }" class="catTitle">Propositioner och skrivelser</a> <span class="count">(${searchResult.totalResultsPerCategory['Propositioner']})</span></p>
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

			<p><a href="${createLink(mapping:'search', params:[query:query, cat:'Rattsfall']) }" class="catTitle">Rättsfall</a> <span class="count">(${searchResult.totalResultsPerCategory['Rattsfall']})</span></p>
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
			<p><a href="${createLink(mapping:'search', params:[query:query, cat:'Lagar']) }" class="catTitle">Lagar och Förordningar</a> <span class="count">(${searchResult.totalResultsPerCategory['Lagar']})</span></p>
			<g:if test="${searchResult.items['Lagar']}">
			<ul>
				<g:each in="${searchResult.items['Lagar']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<li class="showAll"><a href="${createLink(mapping:'search', params:[query:query, cat:'Lagar']) }">Visa fler träffar</a></li>
			</ul>
			</g:if>
			
			<p><a href="${createLink(mapping:'search', params:[query:query, cat:'Utredningar']) }" class="catTitle">Utredningar</a> <span class="count">(${searchResult.totalResultsPerCategory['Utredningar']})</span></p>
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
</div>
</body>
</html>