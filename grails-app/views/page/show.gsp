<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
<nav id="primaryNavigation">
	<div id="logo">
		<a href="${resource()}">${siteProps.siteTitle}</a>
	</div>
	${siteProps.primaryNavigation}
</nav>
<header id="siteHeader">
	<nav id="sitelinks">
		${siteProps.headerNavigation}
	</nav>
	<nav id="breadcrumbs">
		<g:breadcrumbs parent="${page.parent}" />
	</nav>
</header>
<div id="content">
    <article id="editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
	</article>
</div>
<footer id="siteFooter">${siteProps.footer}</footer>
</body>
</html>