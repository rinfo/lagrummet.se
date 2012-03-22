<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
    <article class="editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
		<div id="pageTree">
		<ul>
			<g:each in="${pageTreeList}" var="pI">
				<g:if test="${!pI.parent && pI.children}">
					<ul>
            		<g:sitemapItem pageId="${pI.id}" />
            		</ul>
            	</g:if>
            	<g:elseif test="${!pI.parent && !pI.children && pI.isCurrentlyPublished() && pI.showInSitemap}">
            		<li><a href="${resource() + '/' + pI.url()}">${pI.title}</a></li>
            	</g:elseif>
            </g:each>
            </ul>
		</div>
	</article>
</body>
</html>