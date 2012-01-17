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
                  <a href="${resource()}/${pI.url()}">${pI.h1}</a>
                  <g:if test="${pI.children?.size()}">
                    <ul>
                      <g:each in="${pI.children}" var="pIChild">
                        <g:if test="${pIChild.status != 'autoSave'}"><li id="p-${pIChild.id}"><a href="${resource()}/${pIChild.url()}">${pIChild.h1}</a></li></g:if>
                        <g:if test="${pIChild.children?.size()}">
                        	<ul>
                        	<g:each in="${pIChild.children}" var="pIGrandChild">
                        		 <g:if test="${pIGrandChild.status != 'autoSave'}"><li id="p-${pIGrandChild.id}"><a href="${resource()}/${pIGrandChild.url()}">${pIGrandChild.h1}</a></li></g:if>
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