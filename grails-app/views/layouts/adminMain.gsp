<!DOCTYPE html>
<html>
<head>
	<title>Admin - <g:layoutTitle default="Grails" />
	</title>
	<link rel="stylesheet" href="${resource(dir:'css',file:'admin.css')}" />
	<g:javascript library="jquery" plugin="jquery" />
	<g:javascript library="jquery.jstree" />
	<g:javascript library="admin" />
	<g:layoutHead />
</head>
<body>
	<header>
		<h1>Välkommen till admin</h1>
		<nav class="logout">
			<sec:username/> (<g:link controller="logout">Logga ut</g:link>)
		</nav>
	</header>
	<div id="leftCol">
		<nav class="primaryNav">
			<ul>
				<li>Search</li>
				<sec:ifAllGranted roles="ROLE_ADMIN">
					<li><a href="${createLink(controller:'user', action:'list')}">Users</a></li>
				</sec:ifAllGranted>
				<li>Something else</li>
			</ul>
			<div id="pageTree">
			<ul>
	            <g:each in="${pageTreeList}" var="pI">
	                <g:if test="${!pI.parent}">
	                  <li id="p-${pI.id}">
	                  <link:page permalink="${pI.url()}" a="edit">${pI.title}</link:page>
	                  <g:if test="${pI.children.size()}">
	                    <ul>
	                      <g:each in="${pI.children}" var="pIChild">
	                        <li id="p-${pIChild.id}"><link:page permalink="${pIChild.url()}" a="edit">${pIChild.title}</link:page></li>
	                        <g:if test="${pIChild.children.size()}">
	                        	<g:each in="${pI.children}" var="pIGrandChild">
	                        		<li id="p-${pIGrandChild.id}"><link:page permalink="${pIGrandChild.url()}" a="edit">${pIGrandChild.title}</link:page></li>
	                        	</g:each>
	                        </g:if>
	                      </g:each>
	                    </ul>
	                  </g:if>
	                  </li>
	                </g:if>
	            </g:each>
	          </ul>
			</div>
			
		</nav>
	</div>
	<div id="bodyContent">
		<g:layoutBody />
	</div>
</body>
</html>