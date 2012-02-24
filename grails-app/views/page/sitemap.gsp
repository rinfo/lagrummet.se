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
				<g:if test="${!pI.parent}">
            		<g:adminMenuItem pageId="${pI.id}" noLinkForMetaPage="true" />
            	</g:if>
            </g:each>
          </ul>
		</div>
	</article>
</body>
</html>