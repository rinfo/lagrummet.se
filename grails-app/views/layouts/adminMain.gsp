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
		<h1><a href="${grailsApplication.config.grails.serverURL}/admin">Lagrummet.se CMS</a></h1>
		<sec:username/> (<g:link controller="logout">Logga ut</g:link>)
		
		<nav id="adminFunctions">
		<ul class="dropdown">
			<sec:ifAllGranted roles="ROLE_ADMIN">
				<li>
					<strong>Användare</strong>
					<ul class="sub_menu">
						<li><a href="${createLink(controller:'user', action:'create')}">Ny användare</a></li>
						<li><a href="${createLink(controller:'user', action:'list')}">Hantera användare</a></li>
					</ul>
				</li>
			
				<li>
					<strong>Inställningar</strong>
					<ul class="sub_menu">
						<li><a href="${createLink(controller:'siteProperties', action:'edit')}">Ändra siteinställningar</a></li>
					</ul>
				</li>
			</sec:ifAllGranted>	
			<li>
				<strong>Media</strong>
				<ul class="sub_menu">
					<li><a href="${createLink(controller:'media', action:'create')}">Ny bild eller fil</a></li>
					<li><a href="${createLink(controller:'media', action:'list')}">Hantera media</a></li>
				</ul>
			</li>
			<li>
				<strong>Rättskällor</strong>
				<ul class="sub_menu">
					<li><a href="${createLink(controller:'legalSource', action:'create')}">Ny rättskälla</a></li>
					<li><a href="${createLink(controller:'legalSource', action:'list')}">Hantera rättskällor</a></li>
				</ul>
			</li>
			<li>
				<strong>Synonymer</strong>
				<ul class="sub_menu">
					<li><a href="${createLink(controller:'synonym', action:'create')}">Ny synonym</a></li>
					<li><a href="${createLink(controller:'synonym', action:'list')}">Hantera synonymer</a></li>
				</ul>
			</li>
		</ul>
		</nav>
	</header>
	<nav id="adminPages">
		<g:form action="create" controller="page">
		<div class="buttons"><g:actionSubmit name="create" action="create" class="add" value="Ny sida" /></div>
		</g:form>
		<div id="pageTree">
		<ul>
            <g:each in="${pageTreeList}" var="pI">
                <g:if test="${!pI.parent}">
                	<% pIclass = (!pI.metaPage) ? "" : "metaPage" %>
                  <li id="p-${pI.id}" class="<%=pIclass%>">
                  <% isDraft = (pI.status == 'draft') ? '<span class="draft">*</span> ' : "" %>
                  <g:link controller="page" action="edit" id="${pI.id}"><%=isDraft%>${pI.h1}</g:link>
                  <g:if test="${pI.children?.size()}">
                    <ul>
                      <g:each in="${pI.children}" var="pIChild">
                      	<% pIChildClass = (!pIChild.metaPage) ? "" : "metaPage" %>
                      	<% isDraft = (pIChild.status == 'draft') ? '<span class="draft">*</span> ' : "" %>
                        <g:if test="${pIChild.status != 'autoSave'}"><li id="p-${pIChild.id}" class="<%=pIChildClass%>"><g:link controller="page" action="edit" id="${pIChild.id}"><%=isDraft%>${pIChild.h1}</g:link></li></g:if>
                        <g:if test="${pIChild.children?.size()}">
                        	<ul>
                        	<g:each in="${pIChild.children}" var="pIGrandChild">
                        		<% pIGrandChildClass = (!pIGrandChild.metaPage) ? "" : "metaPage" %>
                        		<% isDraft = (pIGrandChild.status == 'draft') ? '<span class="draft">*</span> ' : "" %>
                        		 <g:if test="${pIGrandChild.status != 'autoSave'}"><li id="p-${pIGrandChild.id}" class="<%=pIGrandChildClass%>"><g:link controller="page" action="edit" id="${pIGrandChild.id}"><%=isDraft%>${pIGrandChild.h1}</g:link></li></g:if>
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