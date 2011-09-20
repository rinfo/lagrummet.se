<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <g:layoutHead />
        <!--[if IE]>
			<script src="${resource(dir:'js',file:'html5IE.js')}"></script>
		<![endif]-->
    </head>
    <body>
		<header>
			<div id="logo">
				lagrummet.se
			</div>
			<nav class="sitelinks">
				<ul>
					<li><a href="">Lyssna</a></li>
					<li><a href="">Other languages</a></li>
					<li><a href="">Webbkarta</a></li>
					<li><a href="">Om lagrummet.se</a></li>
				</ul>
			</nav>
			<nav class="breadcrumbs">
				<a href="">Hem</a>
			</nav>
		</header>
		<div id="content">
			<nav id="primaryNavigation">
				<ul class="rinfo">
					<li class="heading">R&auml;ttsinformation</li>
					<li><a href="">Lagar och f&ouml;rordningar</a></li>
					<li><a href="">Myndigheters F&ouml;reskrifter</a></li>
				</ul>
				<ul class="turnHere"></ul>
				<ul class="learnMore"></ul>
			</nav>
	        <g:layoutBody />
		</div>
        <g:javascript library="jquery" plugin="jquery" />
    </body>
</html>