<%@ page import="se.lagrummet.LegalSource" %>

<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
    <article class="editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
            <g:def value="${LegalSource.findAllByCategory(cat)}" var="liList" />
	            <h2><g:message code="legalSource.category.${cat}" /></h2>
	            <ul>
	            	<g:each in="${liList}" var="lI">
		            <li><a href="${lI.url}">${lI.name}</a></li>
		            </g:each>
		        </ul>
	</article>
</body>
</html>