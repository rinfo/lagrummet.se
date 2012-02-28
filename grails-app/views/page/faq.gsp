<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
    <article class="editorial faq">
		<header><h1>${page.h1}</h1></header>
		${page.content}
		<footer class="reviewed">Senast granskad: ${page.publishStart.format('yyyy-MM-dd')}</footer>
	</article>
</body>
</html>