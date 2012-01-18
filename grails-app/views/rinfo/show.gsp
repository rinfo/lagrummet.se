<html>
<head>
	<title>${docInfo.identifier} - ${docInfo.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
    <article id="rinfo">
    	<h1>${docInfo.title}</h1>    	
		<table>
		<g:if test="${docEntry*.link*.@type.join('|').contains('application/pdf') || docEntry*.content*.@type.join('|').contains('application/pdf')}">
			<tr>
				<td class="label">Lagtext:</td><td>
						<g:each in="${docEntry.link}" var="link">
							<g:if test="${link.@type == 'application/pdf'}">
								<a href="${grailsApplication.config.lagrummet.rdl.rinfo.baseurl + link.@href}"><img src="${resource() }/images/PDF.png" class="pdfIcon" /> ${docInfo.title}</a>
							</g:if>
						</g:each>
						<g:each in="${docEntry.content}" var="content">
							<g:if test="${content.@type == 'application/pdf'}">
								<a href="${grailsApplication.config.lagrummet.rdl.rinfo.baseurl + content.@src}"><img src="${resource() }/images/PDF.png" class="pdfIcon" /> ${docInfo.title}</a>
							</g:if>
						</g:each>
				</td></tr>
			</g:if>
			<tr><td class="label">Beteckning:</td><td> ${docInfo.identifier}</td></tr>

			<tmpl:labelRow label="Ikraft" value="${docInfo.ikrafttradandedatum}" />
			
			<tmpl:labelRow label="Målnummer" value="${docInfo.referatAvDomstolsavgorande?.malnummer}" />			
			<tmpl:labelRow label="Målnummer" value="${docInfo.malnummer}" />	
			
			<tmpl:labelRow label="Avgörandedatum" value="${docInfo.referatAvDomstolsavgorande?.avgorandedatum}" />		
			<tmpl:labelRow label="Avgörandedatum" value="${docInfo.avgorandedatum}" />		
			
			<g:if test="${docInfo.forarbete}">
				<tr><td class="label">Förarbeten: </td><td>
					<ul>
					<g:each in="${docInfo.forarbete.sort{ it.identifier } }" var="forarbete">
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
		
		
		<aside id="rinfoSidebar">
		
			<g:if test="${docInfo.andrar}">
				<h3>Grundförfattning som ändras</h3>
				<g:each in="${docInfo.andrar}" var="item">
					<ul>
						<li class="label">Titel:</li>
						<li><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title}</a></li>
						<li class="label">Beteckning:</li>
						<li>${item.identifier}</li>
						<li class="label">Ikraft:</li>
						<li>${item.ikrafttradandedatum}</li>
					</ul>
				</g:each>
			</g:if>
			
			<g:if test="${docInfo.rev?.konsoliderar}">
				<h3>Senaste konsoliderade versionen</h3>
				<g:latestConsolidated in="${docInfo.rev.konsoliderar}" var="item">
					<ul>
						<li class="label">Titel:</li>
						<li><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title}</a></li>
						<li class="label">Beteckning:</li>
						<li>${item.identifier}</li>
						<li class="label">Ikraft:</li>
						<li>${item.ikrafttradandedatum}</li>
					</ul>
				</g:latestConsolidated>
			</g:if>
			
			<g:if test="${docInfo.rev?.upphaver}">
				<h3>Upphävande författning</h3>
				<span>Författning som upphäver:</span><br/>
				<span class="subtitle">${docInfo.title}</span>
				<g:each in="${docInfo.rev.upphaver}" var="item" status="i">
				<ul>
					<li class="label">Titel:</li>
					<li><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title}</a></li>
					<li class="label">Beteckning:</li>
					<li>${item.identifier}</li>
					<li class="label">Ikraft:</li>
					<li>${item.ikrafttradandedatum}</li>
				</ul>
				</g:each>
			</g:if>
			
			<g:if test="${docInfo.rev?.rattsfallshanvisning}">
				<g:set var="toggleId" value="toggleHanvisning" />
				<g:set var="isExpanded" value="${params[(toggleId)]}" />
				<h3>Hänvisande rättsfall (${docInfo.rev.rattsfallshanvisning.size()})</h3>
				<span>Rättsfall som hänvisar till:</span><br/>
				<span class="subtitle">${docInfo.identifier}</span>
				<g:each in="${docInfo.rev.rattsfallshanvisning}" var="item" status="i">
					<g:if test="${i >= 4}">
					<ul class="${toggleId} ${isExpanded ? '' : 'hidden' }">
					</g:if>
					<g:else>
					<ul>
					</g:else>
						<li class="label">Titel:</li>
						<g:each in="${item.rev?.referatAvDomstolsavgorande}" var="referat">
							<li><a href="${referat.iri?.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${referat.identifier}</a></li>
						</g:each>
						<li class="label">Målnummer:</li>
						<li>${item.malnummer}</li>
						<li class="label">Avgörandedatum:</li>
						<li>${item.avgorandedatum}</li>
					</ul>
				</g:each>
				<g:if test="${docInfo.rev.rattsfallshanvisning.size() > 4}">
				<g:toggleLink toggleId="${toggleId}" mapping="rinfo">
						<span>Visa alla ${docInfo.rev.rattsfallshanvisning.size()} hänvisande rättsfall &#x25BC;</span>
						<span class="hidden">Dölj hänvisande rättsfall &#x25b2;</span>
				</g:toggleLink>
				</g:if>
			</g:if>
			
			<g:if test="${docInfo.rev?.andrar}">
				<g:set var="toggleId" value="toggleAndrar" />
				<g:set var="isExpanded" value="${params[(toggleId)]}" />
				<h3>Ändringsförfattningar (${docInfo.rev.andrar.size()})</h3>
				<span>Författning som ändrar:</span><br/>
				<span class="subtitle">${docInfo.title}</span>
				<g:each in="${docInfo.rev.andrar.sort{ it.ikrafttradandedatum }}" var="item" status="i">
					<g:if test="${i >= 4}">
					<ul class="${toggleId} ${isExpanded ? '' : 'hidden' }">
					</g:if>
					<g:else>
					<ul>
					</g:else>
						<li class="label">Titel:</li>
						<li><a href="${item.iri.replaceFirst('http://.*?/', grailsApplication.config.lagrummet.local.rinfo.view)}">${item.title}</a></li>
						<li class="label">Beteckning:</li>
						<li>${item.identifier}</li>
						<li class="label">Ikraft:</li>
						<li>${item.ikrafttradandedatum}</li>
					</ul>
				</g:each>
				<g:if test="${docInfo.rev.andrar.size() > 4}">
				<g:toggleLink toggleId="${toggleId}" mapping="rinfo">
						<span class="${isExpanded ? 'hidden' : '' }">Visa alla ${docInfo.rev.andrar.size()} ändringsförfattningar &#x25BC;</span>
						<span class="${!isExpanded ? 'hidden' : '' }">Dölj ändringsförfattningar &#x25b2;</span>
				</g:toggleLink>
				</g:if>
			</g:if>
			
		</aside>
	</article>
	
	
</body>
</html>