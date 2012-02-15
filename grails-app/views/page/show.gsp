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
	<aside class="puffs" id="puffs">
		<g:render template="asidePuff" collection="${page.puffs}" var="puff" />
	</aside>
</body>
</html>