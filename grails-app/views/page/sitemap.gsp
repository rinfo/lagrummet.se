<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="main" />
</head>
<body>
    <article class="editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}
		<div id="pageTree">
		<ul>
            <g:each in="${pageTreeList}" var="pI">
                <g:if test="${!pI.parent}">
                  <li id="p-${pI.id}">
                  <g:link controller="page" action="edit" id="${pI.id}">${pI.h1}</g:link>
                  <g:if test="${pI.children?.size()}">
                    <ul>
                      <g:each in="${pI.children}" var="pIChild">
                        <g:if test="${pIChild.status != 'autoSave'}"><li id="p-${pIChild.id}"><g:link controller="page" action="edit" id="${pIChild.id}">${pIChild.h1}</g:link></li></g:if>
                        <g:if test="${pIChild.children?.size()}">
                        	<ul>
                        	<g:each in="${pIChild.children}" var="pIGrandChild">
                        		 <g:if test="${pIGrandChild.status != 'autoSave'}"><li id="p-${pIGrandChild.id}"><g:link controller="page" action="edit" id="${pIGrandChild.id}">${pIGrandChild.h1}</g:link></li></g:if>
                        	</g:each>
                        	</ul>
                        </g:if>
                      </g:each>
                    </ul>
                  </g:if>
                  </li>
                </g:if>
            </g:each>
          </ul>
		</div>
	</article>
</body>
</html>