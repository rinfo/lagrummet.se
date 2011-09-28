<!DOCTYPE html>
<html>
<head>
	<title>Admin - <g:layoutTitle default="Grails" />
	</title>
	<link rel="stylesheet" href="${resource(dir:'css',file:'admin.css')}" />
	<META name="serverURL" content="${resource()}">
	<g:javascript library="jquery" plugin="jquery" />
	<g:javascript library="jquery.jstree" />
	<tinyMce:resources jquery="true" />
	<g:layoutHead />
	<g:javascript library="admin" />
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
	                  <g:link action="edit" id="${pI.id}">${pI.title}</g:link>
	                  <g:if test="${pI.children.size()}">
	                    <ul>
	                      <g:each in="${pI.children}" var="pIChild">
	                        <li id="p-${pIChild.id}"><g:link action="edit" id="${pIChild.id}">${pIChild.title}</g:link></li>
	                        <g:if test="${pIChild.children.size()}">
	                        	<g:each in="${pI.children}" var="pIGrandChild">
	                        		<li id="p-${pIGrandChild.id}"><g:link action="edit" id="${pIGrandChild.id}">${pIGrandChild.title}</g:link></li>
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
		
		<g:form name="quickSearch" method="post" mapping="pageAdmin" action="quickSearch"> 
			<g:textField name="query"/><g:submitButton name="search" value="Find" />
		</g:form>
	</div>
	<div id="bodyContent">
		<g:layoutBody />
	</div>
</body>
</html>