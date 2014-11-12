<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="${grailsApplication.config.lagrummet.mainLayoutName}" />
</head>
<body>
    <article class="frontpage editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
		<div class="puffs">
		<g:render template="puff" collection="${page.puffs}" var="puff" />
		</div>
	</article>
</body>
</html>