<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
    <article class="editorial legalSources">
		<header><h1>${page.h1}</h1></header>
		${page.content}
            <g:each in="${legalSourceGroups}" var="category">
            <g:if test="${category.value['sokbar'] || category.value['inteSokbar']}">
            	<section class="${category.key}">
	            <h2><g:message code="legalSource.category.${category.key}" /></h2>
	            <g:each in="${category.value}">
	            	<g:if test="${it.value}">
	            	<h3><g:message code="legalSource.category.${it.key}" /></h3>
	            	<g:each in="${it.value}" var="subCategory">
			            <g:if test="${subCategory.key != ''}">
			            <h4><g:message code="legalSource.subCategory.${subCategory.key}" /></h4>
			            </g:if>
			            
			            <ul>
			            	<g:each in="${subCategory.value}" var="lI" status="i">
			            	<g:if test="${i<=9}">
				            <li><a href="${lI.url}">${lI.name}</a></li>
				            </g:if><g:else>
				            <li class="${subCategory.key} hidden"><a href="${lI.url}">${lI.name}</a></li>
				            </g:else>
				            </g:each>
				        </ul>
			            <g:if test="${subCategory.value.size() >= 10}">
			        	<a href="#" class="toggleLink" id="${subCategory.key}"><span>Visa alla ${subCategory.value.size()} rättskällor &#x25BC;</span><span class="hidden">Dölj rättskällor &#x25b2;</span></a>
			        	</g:if>
		            	</g:each>
		            </g:if>
	            </g:each>
	            </section>
	        </g:if>
		    </g:each>
	</article>
</body>
</html>