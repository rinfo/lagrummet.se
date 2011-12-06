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
		<g:each in="${[]}" var="puff" >
			<div class="puff">
				<a href="${puff.link}"><img src="${puff.img.filename}" /></a>
				<strong>${puff.title}</strong>
				<p>${puff.desc}</p>
				<a href="${puff.link}">Läs mer</a>
			</div>
		
		</g:each>
	</aside>
</body>
</html>