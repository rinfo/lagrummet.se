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
		<nav>En meny</nav>
		<sec:username/> (<g:link controller="logout">Logga ut</g:link>)<br/>
	<g:layoutBody />
</body>
</html>