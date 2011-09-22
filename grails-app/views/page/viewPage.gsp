<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
<header>
	<div id="logo">
		${siteProps.siteTitle}
	</div>
	<nav class="sitelinks">
		${siteProps.headerNavigation}
	</nav>
	<nav class="breadcrumbs">
		<g:breadcrumbs parent="${page.parent}" />
	</nav>
</header>
<div id="content">
	<nav id="primaryNavigation">${siteProps.primaryNavigation}</nav>
    <article>
		<header><h1>${page.h1}</h1></header>
		${page.content}
		<footer></footer>
	</article>
</div>
</body>
</html>