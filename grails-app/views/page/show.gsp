<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
    <article class="editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
	</article>
	<aside class="puffs">
		<g:each in="${page.puffs}" var="puff" >
			
			<g:if test="${!puff.isEmpty()}">
			<div class="puff">
				<g:if test="${puff.image}"><a href="${resource() + "/" + puff.link}"><img src="${puff.image?.filename}" alt="${puff.image?.title}"/></a></g:if>
				<h3><a href="${resource() + "/" + puff.link}">${puff.title}</a></h3>
				<p>${puff.description}</p>
			</div>
			</g:if>
		</g:each>
	</aside>
</body>
</html>