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
    <article id="editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
		
	</article>
</div>
<footer>${siteProps.footer}</footer>
</body>
</html>