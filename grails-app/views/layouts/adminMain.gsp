<!DOCTYPE html>
<html>
<head>
	<title>Admin - <g:layoutTitle default="Grails" />
	</title>
	<link rel="stylesheet" href="${resource(dir:'css',file:'admin.css')}" />
	<g:layoutHead />
	<g:javascript library="jquery" plugin="jquery" />
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
				<li><a href="${createLink(controller:'user', action:'list')}">Users</a></li>
				<li>Something else</li>
				<li>Page tree</li>
			</ul>
		</nav>
	</div>
	<div id="content">
		<g:layoutBody />
	</div>
</body>
</html>