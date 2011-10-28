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
    	<g:if test="${query}">
		<header><h1>Sökresultat för ${query.encodeAsHTML()}</h1></header>
		</g:if>
		
		<g:if test="${searchResult?.totalResults}">
			<p>Totalt antal resultat: ${searchResult.totalResults}</p>
			
		<div class="column">
				<p><strong>Information från lagrummet.se</strong> <span class="count">(${searchResult.totalResultsPerCategory['Ovrigt']})</span></p>
				<ul>
				<g:each in="${searchResult.items['Ovrigt']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
				</g:each>
				<li class="showAll"><a href="#">Visa alla träffar (${searchResult.totalResultsPerCategory['Ovrigt']})</a></li>
				</ul>
			
			<p><strong>Propositioner och skrivelser</strong> <span class="count">(${searchResult.totalResultsPerCategory['Propositioner']})</span></p>
			<ul>
			<g:each in="${searchResult.items['Propositioner']}" var="item">
				<li>
					<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
					<g:if test="${item.matches}">
						<p>${item.matches} ...</p>
					</g:if>
					<p class="type">${item.identifier}</p></li>
			</g:each>
			<li class="showAll"><a href="#">Visa alla träffar (${searchResult.totalResultsPerCategory['Propositioner']})</a></li>
			</ul>
		
				<p><strong>Rättsfall</strong> <span class="count">(${searchResult.totalResultsPerCategory['Rattsfall']})</span></p>
				<ul>
				<g:each in="${searchResult.items['Rattsfall']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<li class="showAll"><a href="#">Visa alla träffar (${searchResult.totalResultsPerCategory['Rattsfall']})</a></li>
				</ul>
		</div>
		
		<div class="column">
				<p><strong>Lagar och Förordningar</strong> <span class="count">(${searchResult.totalResultsPerCategory['Lagar']})</span></p>
				<ul>
				<g:each in="${searchResult.items['Lagar']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<li class="showAll"><a href="#">Visa alla träffar (${searchResult.totalResultsPerCategory['Lagar']})</a></li>
				</ul>
			
				<p><strong>Utredningar</strong> <span class="count">(${searchResult.totalResultsPerCategory['Utredningar']})</span></p>
				<ul>
				<g:each in="${searchResult.items['Utredningar']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<li class="showAll"><a href="#">Visa alla träffar (${searchResult.totalResultsPerCategory['Utredningar']})</a></li>
				</ul>
		</div>
		</g:if>
	</article>
</div>
</body>
</html>