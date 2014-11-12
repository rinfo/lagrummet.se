<%@ page import="se.lagrummet.LegalSource" %>

<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="${grailsApplication.config.lagrummet.mainLayoutName}" />
</head>
<body>
    <article class="editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
			<g:each in="${[""] + grailsApplication.config.lagrummet.legalSource.subCategories}" var="subCat">
            <g:def value="${LegalSource.findAllByCategoryAndSubCategory(cat, subCat)}" var="liList" />
				<g:if test="${liList.size() > 0}">
            	<h4 id="${subCat ? 'legalSource.subCategory.'+subCat : 'legalSource.category.'+cat}">${message(code: (subCat ? 'legalSource.subCategory.'+subCat : 'legalSource.category.'+cat))}</h4>
	            <ul id="${subCat ? 'legalSource_subCategory_'+subCat+'_list' : 'legalSource_category_'+cat+'_list'}">
	            	<g:each in="${liList}" var="lI">
		            <li>
		            	<a href="${lI.url}">${lI.name}</a>
		            	<div>${lI.description}</div>
		            </li>
		            </g:each>
		        </ul>
		        </g:if>
		        
		      </g:each>
		      <footer class="reviewed">Senast granskad: ${page.publishStart.format('yyyy-MM-dd')}</footer>
	</article>
	<aside class="puffs" id="puffs">
		<g:render template="puff" collection="${page.puffs}" var="puff" />
	</aside>
</body>
</html>