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
			<g:each in="${pageTreeList}" var="pI">
				<g:if test="${!pI.parent}">
					<h2>${pI.title}</h2>
					<ul>
            		<g:sitemapItem pageId="${pI.id}" />
            		</ul>
            	</g:if>
            </g:each>
		</div>
	</article>
</body>
</html>