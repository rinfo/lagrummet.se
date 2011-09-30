<!DOCTYPE html>
<html>
<head>
	<title>Admin - <g:layoutTitle default="Grails" />
	</title>
	<link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
	<link rel="stylesheet" href="${resource(dir:'css',file:'admin.css')}" />
	<META name="serverURL" content="${resource()}">
	<g:javascript library="jquery" plugin="jquery" />
	<g:javascript library="jquery.jstree" />
	<tinyMce:resources jquery="true" />
	<g:layoutHead />
	<g:javascript library="admin" />
</head>
<body class="admin">
	<header>
		<h1>Lagrummet.se CMS</h1>
		<nav class="logout">
			<sec:username/> (<g:link controller="logout">Logga ut</g:link>)
		</nav>
	</header>
	<nav id="primaryNav">
		<ul>
			<sec:ifAllGranted roles="ROLE_ADMIN">
				<li><h3>Anv&auml;ndare</h3></li>
				<li><a href="${createLink(controller:'user', action:'create')}">Ny anv&auml;ndare</a></li>
				<li><a href="${createLink(controller:'user', action:'list')}">Hantera anv&auml;ndare</a></li>
			</sec:ifAllGranted>
		</ul>
		<h3>Sidor</h3>
		<a href="${createLink(controller:'page', action:'create')}">Ny sida</a>
		<div id="pageTree">
		<ul>
            <g:each in="${pageTreeList}" var="pI">
                <g:if test="${!pI.parent}">
                  <li id="p-${pI.id}">
                  <g:link controller="page" action="edit" id="${pI.id}">${pI.title}</g:link>
                  <g:if test="${pI.children.size()}">
                    <ul>
                      <g:each in="${pI.children}" var="pIChild">
                        <li id="p-${pIChild.id}"><g:link controller="page" action="edit" id="${pIChild.id}">${pIChild.title}</g:link></li>
                        <g:if test="${pIChild.children.size()}">
                        	<ul>
                        	<g:each in="${pIChild.children}" var="pIGrandChild">
                        		<li id="p-${pIGrandChild.id}"><g:link controller="page" action="edit" id="${pIGrandChild.id}">${pIGrandChild.title}</g:link></li>
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
		<g:form name="quickSearch" method="post" mapping="pageAdmin" action="quickSearch"> 
			<g:textField name="query"/><g:submitButton name="search" value="Find" />
		</g:form>
	</nav>
	<div id="bodyContent">
		<g:layoutBody />
	</div>
</body>
</html>