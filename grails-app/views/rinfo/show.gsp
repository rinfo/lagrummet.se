<html>
<head>
	<title>${docInfo.identifier} - ${docInfo.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
<div id="content">
    <article id="rinfo">
    	<h1>${docInfo.title}</h1>    	
		<table>
		<g:if test="${docEntry*.link*.@type.join('|').contains('application/pdf')}">
			<tr>
				<td class="label">Lagtext:</td><td>
						<g:each in="${docEntry.link}" var="link">
							<g:if test="${link.@type == 'application/pdf'}">
								<a href="${grailsApplication.config.lagrummet.rdl.rinfo.baseurl + link.@href}"><img src="${resource() }/images/pdficon_small.gif" /> ${docInfo.title}</a>
							</g:if>
						</g:each>
				</td></tr>
			</g:if>
			<tr><td class="label">Beteckning:</td><td> ${docInfo.identifier}</td></tr>
			<g:if test="${docInfo.ikrafttradandedatum}">
				<tr><td class="label">Ikraft:</td><td> ${docInfo.ikrafttradandedatum}</td></tr>
			</g:if>
			<g:if test="${docInfo.forarbete}">
				<tr><td class="label">Förarbeten: </td><td>
					<ul>
					<g:each in="${docInfo.forarbete}" var="forarbete">
						<g:if test="${forarbete.identifier && forarbete.iri}">
							<li><a href="${forarbete.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${forarbete.identifier}</a></li>
						</g:if>
						<g:elseif test="${forarbete.identifier}">
							${forarbete.identifier}
						</g:elseif>
					</g:each>
					</ul>
				</td></tr>
			</g:if>
		</table>
		<g:if test="${content}">
			<hr/>
			<div>${content }</div>
		</g:if>
	</article>
	
	<aside id="rinfoSidebar">
		<g:if test="${docInfo.'@rev'?.andrar}">
			<h3>Ändrar</h3>
			<g:each in="${docInfo.'@rev'.andrar}" var="item">
			<ul>
				<li class="label">Titel</li>
				<li>${item.title}</li>
				<li class="label">Beteckning</li>
				<li><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.identifier}</a></td></tr>
				<li class="label">Ikraft</li>
				<li>${item.ikrafttradandedatum}</li>
			</ul>
			</g:each>
		</g:if>
		
		<g:if test="${docInfo.'@rev'?.konsolideringsunderlag}">
			<h3>Konsolideringsunderlag för</h3>
			<g:each in="${docInfo.'@rev'.konsolideringsunderlag}" var="item">
			<ul>
				<li class="label">Titel</li><li>${item.title}</li>
				<li class="label">Beteckning</li><li><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.identifier}</a></li>
				<li class="label">Utfärdad</li><li>${item.issued}</li>
			</ul>
			</g:each>
		</g:if>
	</aside>
</div>
</body>
</html>