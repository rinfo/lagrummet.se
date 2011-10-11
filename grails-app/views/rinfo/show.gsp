<html>
<head>
	<title>${docInfo.identifier} - ${docInfo.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
<nav id="primaryNavigation">
	<div id="logo">
		<a href="${resource()}">${siteProps.siteTitle}</a>
	</div>
	${siteProps.primaryNavigation}
</nav>
<header id="siteHeader">
	<nav id="sitelinks">
		${siteProps.headerNavigation}
	</nav>
	<nav id="breadcrumbs">
		<g:breadcrumbs parent="${page.parent}" />
	</nav>
</header>
<div id="content">
    <article id="rinfo">
    	<h1>${docInfo.title}</h1>    	
		<table>
		<tr>
			<td class="label">Titel:</td><td>
				<g:if test="${docEntry*.link*.@type.join('|').contains('application/pdf')}">
					<g:each in="${docEntry.link}" var="link">
						<g:if test="${link.@type == 'application/pdf'}">
							<a href="${grailsApplication.config.lagrummet.rdl.rinfo.baseurl + link.@href}">${docInfo.title}</a>
						</g:if>
					</g:each>
				</g:if>
				<g:else>
					${docInfo.title}
				</g:else>
			</td></tr>
			<tr><td class="label">SFS-nummer:</td><td> ${docInfo.identifier}</td></tr>
			<tr><td class="label">Ikraft:</td><td> ${docInfo.ikrafttradandedatum}</td></tr>
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
		<g:if test="${docInfo.'@rev'.andrar}">
			<h3>Ändrar</h3>
			<g:each in="${docInfo.'@rev'.andrar}" var="item">
			<ul>
				<li class="label">Titel</li>
				<li>${item.title}</li>
				<li class="label">SFS-nummer</li>
				<li>${item.identifier}</td></tr>
				<li class="label">Ikraft</li>
				<li>${item.ikrafttradandedatum}</li>
			</li>
			</g:each>
		</g:if>
		
		<g:if test="${docInfo.'@rev'.konsolideringsunderlag}">
			<h3>Konsolideringsunderlag för</h3>
			<g:each in="${docInfo.'@rev'.konsolideringsunderlag}" var="item">
			<ul>
				<li class="label">Titel</td><li>${item.title}</li>
				<li class="label">SFS-nummer</td><li>${item.identifier}</li>
				<li class="label">Utfärdad</td><li>${item.issued}</li>
			</ul>
			</g:each>
		</g:if>
		
		<g:if test="${docInfo.'@rev'.konsolideringsunderlag}">
			<h3>Konsoliderad av</h3>
			<g:each in="${docInfo.'@rev'.konsoliderar}" var="item">
			<ul>
				<li class="label">Titel</td><li>${item.title}</li>
				<li class="label">SFS-nummer</td><li>${item.identifier}</li>
				<li class="label">Utfärdad</td><li>${item.issued}</li>
			</ul>
			</g:each>
		</g:if>
	</aside>
</div>
<footer id="siteFooter">${siteProps.footer}</footer>

</body>
</html>