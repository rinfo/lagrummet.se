<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
    <article class="editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
		<ul>
            <g:each in="${legalSourceInstanceList}" var="lI">
            <li><a href="${lI.url}">${lI.name}</a></li>
            </g:each>
        </ul>
	</article>
</body>
</html>