<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="${grailsApplication.config.lagrummet.mainLayoutName}" />
</head>
<body>
    <article class="editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
		<div id="pageTree">
			<ul>
				<g:each in="${pageTreeList.findAll { !it.parent }}" var="pI">
					<g:if test="${pI.children}">
						<li>
							<ul>
								<g:sitemapItem pageId="${pI.id}" />
							</ul>
						</li>
					</g:if>
					<g:elseif test="${!pI.children && pI.isCurrentlyPublished() && pI.showInSitemap}">
						<li><a href="${resource() + '/' + pI.url()}">${pI.title}</a></li>
					</g:elseif>
				</g:each>
			</ul>
		</div>
	</article>
</body>
</html>