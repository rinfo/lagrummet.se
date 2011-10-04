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
		<a href="${grailsApplication.config.grails.serverURL}">Bes&ouml;k sidan</a>
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
				<li><h3>Inst&auml;llningar</h3></li>
				<li><a href="${createLink(controller:'siteProperties', action:'edit')}">&Auml;ndra siteinst&auml;llningar</a></li>
			</sec:ifAllGranted>
		</ul>
		<h3>Sidor</h3>
		<a href="${createLink(controller:'page', action:'create')}">Ny sida</a>
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
                        		 <g:if test="${pIChild.status != 'autoSave'}"><li id="p-${pIGrandChild.id}"><g:link controller="page" action="edit" id="${pIGrandChild.id}">${pIGrandChild.h1}</g:link></li></g:if>
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
			<g:textField size="22" name="query"/><g:submitButton name="search" value="${message(code:'default.button.find.label', default:'Find')}" />
		</g:form>
	</nav>
	<div id="bodyContent">
		<g:layoutBody />
	</div>
</body>
</html>