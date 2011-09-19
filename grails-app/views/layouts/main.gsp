<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <g:layoutHead />
        <g:javascript library="jquery" plugin="jquery" />
    </head>
    <body>
		<header>
			<nav>
				<ul>
					<li><a href="${createLink(controller:'page', action: 'startPage')}">Hem</a></li>
					<li><a href="http://www.dn.se">Bort</a></li>
				</ul>
			</nav>
		</header>
        <g:layoutBody />
    </body>
</html>