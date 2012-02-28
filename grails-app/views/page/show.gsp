<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
    <article class="editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
		<footer class="reviewed">Senast granskad: ${page.publishStart.format('yyyy-MM-dd')}</footer>
	</article>
	<aside class="puffs" id="puffs">
		<g:render template="puff" collection="${page.puffs}" var="puff" />
	</aside>
</body>
</html>