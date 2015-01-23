<!DOCTYPE html>
<html lang="sv">
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
	<link href="${resource(dir:'images',file:'favicon.ico')}" rel="shortcut icon" /> 
	<g:javascript library="jquery" plugin="jquery" />
	<g:javascript library="jquery.jstree" />
	<script src="//tinymce.cachefly.net/4.1/tinymce.min.js"></script>
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
				<strong>Sökverktyg</strong>
				<ul class="sub_menu">
					<li><a href="${createLink(controller:'search', action:'statistics')}">Statistik</a></li>
					<li><a href="${createLink(controller:'synonym', action:'list')}">Hantera synonymer</a></li>
				</ul>
			</li>
			<li>
				<strong>Profil</strong>
				<ul class="sub_menu">
					<li><a href="${createLink(controller:'profile', action:'edit')}">Ändra</a></li>
					<li><a href="${createLink(controller:'profile', action:'show')}">Kontrollera</a></li>
					<li><a href="${createLink(controller:'profile', action:'credentials')}">Ändra behörigheter</a></li>
				</ul>
			</li>
		</ul>
		</nav>
	</header>
	<nav id="adminPages">
		<g:form action="create" controller="page" useToken="true">
		<div class="buttons"><span class="button"><g:actionSubmit name="create" action="create" class="add" value="Ny sida" /></span>&nbsp;<input type="submit" id="treeExpandAll" value="Expandera alla" /></div>
		</g:form>
		
		<div id="pageTree">
		<ul>
			<g:each in="${pageTreeList}" var="pI">
				<g:if test="${!pI.parent}">
            		<g:adminMenuItem pageId="${pI.id}" currentPageId="${pageInstance?.id}" />
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