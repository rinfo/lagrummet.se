<!DOCTYPE html>
<html>
<head>
	<title>Admin - <g:layoutTitle default="Grails" />
	</title>
	<link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
	<link rel="stylesheet" href="${resource(dir:'css',file:'admin.css')}" />
	<!--[if lt IE 9]>
		<link rel="stylesheet" href="${resource(dir:'css',file:'ie.css')}" />
		<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
	<![endif]-->
	<META name="serverURL" content="${resource()}">
	<g:javascript library="jquery" plugin="jquery" />
	<g:javascript library="jquery.jstree" />
	<tinyMce:resources jquery="true" />
	<g:layoutHead />
	<g:javascript library="admin" />
</head>
<body class="admin">
	<header>
		<h1><a href="${grailsApplication.config.grails.serverURL}">Lagrummet.se CMS</a></h1>
		<sec:username/> (<g:link controller="logout">Logga ut</g:link>)
	</header>
	<nav id="primaryNav">
		<ul>
			<sec:ifAllGranted roles="ROLE_ADMIN">
				<li><h3>Användare</h3></li>
				<li><a href="${createLink(controller:'user', action:'create')}">Ny användare</a></li>
				<li><a href="${createLink(controller:'user', action:'list')}">Hantera användare</a></li>
				<li><h3>Inställningar</h3></li>
				<li><a href="${createLink(controller:'siteProperties', action:'edit')}">Ändra siteinställningar</a></li>
			</sec:ifAllGranted>
			<li><h3>Media</h3></li>
			<li><a href="${createLink(controller:'media', action:'create')}">Ny bild eller fil</a></li>
			<li><a href="${createLink(controller:'media', action:'list')}">Hantera media</a></li>
			<li><h3>Rättskällor</h3></li>
			<li><a href="${createLink(controller:'legalSource', action:'create')}">Ny rättskälla</a></li>
			<li><a href="${createLink(controller:'legalSource', action:'list')}">Hantera rättskällor</a></li>
		</ul>
		<h3>Sidor</h3>
		<a href="${createLink(controller:'page', action:'create')}">Ny sida</a>
		<div id="pageTree">
		<ul>
            <g:each in="${pageTreeList}" var="pI">
                <g:if test="${!pI.parent}">
                	<% pIclass = (!pI.metaPage) ? "" : "metaPage" %>
                  <li id="p-${pI.id}" class="<%=pIclass%>">
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
		<g:form name="quickSearch" method="post" mapping="pageAdmin" action="quickSearch"> 
			<g:textField size="22" name="query" id="adminQuery"/><g:submitButton name="search" value="${message(code:'default.button.find.label', default:'Find')}" />
		</g:form>
		
		${grailsApplication.metadata['app.name']} (v${grailsApplication.metadata['app.version']})
	</nav>
	<div id="bodyContent">
		<g:layoutBody />
	</div>
</body>
</html>