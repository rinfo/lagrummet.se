<html>
<head>
	<title>${docInfo.identifier} - ${docInfo.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
<header>
	<div id="logo">
		${siteProps.siteTitle}
	</div>
	<nav class="sitelinks">
		${siteProps.headerNavigation}
	</nav>
	<nav class="breadcrumbs">
		<g:breadcrumbs parent="${page.parent}" />
	</nav>
</header>
<div id="content">
	<nav id="primaryNavigation">${siteProps.primaryNavigation}</nav>
    <article id="rinfo">
		
		<table>
		<tr>
			<td colspan="2">Titel: 
				<g:if test="${docEntry*.link*.@type.join('|').contains('application/pdf')}">
					<g:each in="${docEntry.link}" var="link">
						<g:if test="${link.@type == 'application/pdf'}">
							<a href="${grailsApplication.config.lagrummet.rinfo.baseurl + link.@href}">${docInfo.title}</a>
						</g:if>
					</g:each>
				</g:if>
				<g:else>
					${docInfo.title}
				</g:else>
			</td></tr>
			<tr><td>SFS-nummer:</td><td> ${docInfo.identifier}</td></tr>
			<tr><td>Ikraft:</td><td> ${docInfo.ikrafttradandedatum}</td></tr>
			<tr><td>Förarbeten: </td><td>
				<g:each in="${docInfo.forarbete}" var="forarbete">
					<g:if test="${forarbete.identifier && forarbete.iri}">
						<a href="${forarbete.iri.replaceFirst('http://.*?/', grailsApplication.config.grails.serverURL+'/view/')}">${forarbete.identifier}</a><br/>
					</g:if>
					<g:elseif test="${forarbete.identifier}">
						${forarbete.identifier}
					</g:elseif>
				</g:each>
			</td></tr>
		</table>
		<g:if test="${content}">
			<hr/>
			<div>${content }</div>
		</g:if>
		
	</article>
	<aside id="rinfoSidebar">
		<g:if test="${docInfo.'@rev'.andrar}">
			Ändrar:
			<g:each in="${docInfo.'@rev'.andrar}" var="item">
			<table>
				<tr><td>Titel:</td><td>${item.title}</td></tr>
				<tr><td>SFS-nummer:</td><td>${item.identifier}</td></tr>
				<tr><td>Ikraft:</td><td>${item.ikrafttradandedatum}</td></tr>
				<tr><td>Förarbeten:</td><td></td></tr>
			</table>
			</g:each>
		</g:if>
		
		Konsolideringsunderlag för:
		<g:each in="${docInfo.'@rev'.konsolideringsunderlag}" var="item">
		<table>
			<tr><td>Titel:</td><td>${item.title}</td></tr>
			<tr><td>SFS-nummer:</td><td>${item.identifier}</td></tr>
			<tr><td>Utfärdad:</td><td>${item.issued}</td></tr>
			<tr><td>Förarbeten:</td><td></td></tr>
		</table>
		</g:each>
		
		Konsoliderad av:
		<g:each in="${docInfo.'@rev'.konsoliderar}" var="item">
		<table>
			<tr><td>Titel:</td><td>${item.title}</td></tr>
			<tr><td>SFS-nummer:</td><td>${item.identifier}</td></tr>
			<tr><td>Utfärdad:</td><td>${item.issued}</td></tr>
			<tr><td>Förarbeten:</td><td></td></tr>
		</table>
		</g:each>
	</aside>
</div>
<footer>${siteProps.footer}</footer>

</body>
</html>