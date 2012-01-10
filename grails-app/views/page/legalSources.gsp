<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
    <article class="editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
            <g:each in="${legalSourceGroupingList}" var="liList">
            <g:if test="${liList.value}">
	            <h2><g:message code="legalSource.category.${liList.key}" /></h2>
	            <ul>
	            	<g:each in="${liList.value}" var="lI">
		            <li><a href="${lI.url}">${lI.name}</a></li>
		            </g:each>
		        </ul>
		    </g:if>
		    </g:each>
	</article>
</body>
</html>