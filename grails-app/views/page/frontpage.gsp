<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
    <article class="frontpage editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
		<div class="puffs">
		<g:each in="${page.puffs}" var="puff" >
			<div class="puff">
			<g:if test="${puff.image}"><a href="${resource() + "/" + puff.link}"><img src="${puff.image?.filename}" alt="${puff.image?.title}"/></a></g:if>
			<h3><a href="${resource() + "/" + puff.link}">${puff.title}</a></h3>
			<p>${puff.description}</p>
			<p><a href="${resource() + "/" + puff.link}">Läs mer</a></p>
			</div>
		</g:each>
		</div>
	</article>
</body>
</html>