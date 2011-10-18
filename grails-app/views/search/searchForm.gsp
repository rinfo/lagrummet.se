<html>
<head>
	<title>Sökresultat för ${query.encodeAsHTML()}</title>
	<meta name="layout" content="main"/>
</head>
<body>
<div id="content">
    <article id="searchResults">
		<header><h1>Sökresultat för ${query.encodeAsHTML()}</h1></header>
		
		<g:if test="${searchResult?.totalResults}">
			<p>Totalt antal resultat: ${searchResult.totalResults}</p>
			
		<div class="column">
			<g:if test="${searchResult?.items['Ovrigt']}">
				<p><strong>Information från lagrummet.se</strong> <span class="count">(${searchResult.items['Ovrigt'].size() + 1})</span></p>
				<ul>
				<g:each in="${searchResult.items['Ovrigt']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<li class="showAll"><a href="#">Visa alla träffar (${searchResult.items['Ovrigt'].size() + 1})</a></li>
				</ul>
			</g:if>
			
			<g:if test="${searchResult?.items['Propositioner']}">
				<p><strong>Propositioner och skrivelser</strong> <span class="count">()</span></p>
			</g:if>
		
			<g:if test="${searchResult?.items['Rattsfall']}">
				<p><strong>Rättsfall</strong> <span class="count">(${searchResult.items['Rattsfall'].size() + 1})</span></p>
				<ul>
				<g:each in="${searchResult.items['Rattsfall']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<li class="showAll"><a href="#">Visa alla träffar (${searchResult.items['Rattsfall'].size() + 1})</a></li>
			</ul>
			</g:if>
		</div>
		
		<div class="column">
			<g:if test="${searchResult?.items['Lagar']}">
				<p><strong>Lagar och Förordningar</strong> <span class="count">(${searchResult.items['Lagar'].size() + 1})</span></p>
				<ul>
				<g:each in="${searchResult.items['Lagar']}" var="item">
					<li>
						<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
						<g:if test="${item.matches}">
							<p>${item.matches} ...</p>
						</g:if>
						<p class="type">${item.identifier}</p></li>
				</g:each>
				<li class="showAll"><a href="#">Visa alla träffar (${searchResult.items['Lagar'].size() + 1})</a></li>
				</ul>
			</g:if>
			
			<g:if test="${searchResult?.items['Utredningar']}">
				<p><strong>Utredningar</strong> <span class="count">()</span></p>
			</g:if>
		</div>
		</g:if>
	</article>
</div>
</body>
</html>