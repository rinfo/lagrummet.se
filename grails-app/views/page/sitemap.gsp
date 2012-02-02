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
                  <li>
                  <g:lLink page="${pI}" />
                  <g:if test="${pI.children?.size()}">
                    <ul>
                      <g:each in="${pI.children}" var="pIChild">
                        <g:if test="${pIChild.status != 'autoSave'}"><li><g:lLink page="${pIChild}" /></li></g:if>
                        <g:if test="${pIChild.children?.size()}">
                        	<ul>
                        	<g:each in="${pIChild.children}" var="pIGrandChild">
                        		 <g:if test="${pIGrandChild.status != 'autoSave'}"><li><g:lLink page="${pIGrandChild}" /></li></g:if>
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