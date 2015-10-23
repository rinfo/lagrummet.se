<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="${grailsApplication.config.lagrummet.mainLayoutName}" />
</head>
<body>
    <article class="frontpage editorial">
		<div class="frontpage content">
			<header><h3>${page.h1}</h3></header>
			${page.content}
		</div>
		<div class="puffs">
		<g:render template="puff" collection="${page.puffs}" var="puff" />
		</div>
	</article>
</body>
</html>