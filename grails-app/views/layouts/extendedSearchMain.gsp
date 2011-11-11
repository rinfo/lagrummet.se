<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css/ui-lightness/',file:'jquery-ui-1.8.16.custom.css')}" />
        <link rel="stylesheet" type="text/css" media="print" href="${resource(dir:'css',file:'print.css')}" />
        <META name="serverURL" content="${resource()}">
        <g:layoutHead />
        <!--[if IE]>
			<script src="${resource(dir:'js',file:'html5IE.js')}"></script>
		<![endif]-->
		<g:googleAnalytics id="${grailsApplication.config.lagrummet.googleAnalytics.webPropertyId}" />
    </head>
    <body>
	    <nav id="primaryNavigation">
			<div id="logo">
				<a href="${resource()}">${siteProps?.siteTitle}</a>
			</div>
			${siteProps?.primaryNavigation}
		</nav>
		<header id="siteHeader">
			<nav id="sitelinks">
				${siteProps?.headerNavigation}
			</nav>
			<nav id="breadcrumbs">
				<g:breadcrumbs parent="${page?.parent}" />
			</nav>
		</header>
	    <g:layoutBody />
	    <footer id="siteFooter">${siteProps?.footer}
	    	${grailsApplication.metadata['app.name']} (v${grailsApplication.metadata['app.version']})
	    </footer>
	    <g:javascript library="jquery" plugin="jquery" />
	    <g:javascript library="jquery-ui-1.8.16.custom.min" />
	    <g:javascript library="application" />
    </body>
</html>