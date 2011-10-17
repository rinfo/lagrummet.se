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
			Totalt antal resultat: ${searchResult.totalResults}
			

			<p><strong>Information från lagrummet.se</strong> <span class="count">(${searchResult.totalResults})</span></p>
			
			<h2>Rättsfall</h2>
			<ul id="redaktionellt">
			<g:each in="${searchResult.items['Rattsfall']}" var="item">
				<li>
					<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
					<g:if test="${item.matches}">
						<p>${item.matches} ...</p>
					</g:if>
					<p class="type">${item.identifier}</p></li>
			</g:each>
			<li class="showAll"><a href="#">Visa alla träffar (${searchResult?.totalResults})</a></li>
			</ul>
			
			<h2>Lagar och Förordningar</h2>
			<ul id="redaktionellt">
			<g:each in="${searchResult.items['Lagar']}" var="item">
				<li>
					<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
					<g:if test="${item.matches}">
						<p>${item.matches} ...</p>
					</g:if>
					<p class="type">${item.identifier}</p></li>
			</g:each>
			<li class="showAll"><a href="#">Visa alla träffar (${searchResult?.totalResults})</a></li>
			</ul>
			
			<h2>Övrigt</h2>
			<ul id="redaktionellt">
			<g:each in="${searchResult.items['Ovrigt']}" var="item">
				<li>
					<p><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title ?: item.identifier}</a></p>
					<g:if test="${item.matches}">
						<p>${item.matches} ...</p>
					</g:if>
					<p class="type">${item.identifier}</p></li>
			</g:each>
			<li class="showAll"><a href="#">Visa alla träffar (${searchResult?.totalResults})</a></li>
			</ul>
		</g:if>
	</article>
</div>
</body>
</html>