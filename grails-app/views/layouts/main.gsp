<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <g:layoutHead />
        <!--[if IE]>
			<script src="${resource(dir:'js',file:'html5IE.js')}"></script>
		<![endif]-->
		<g:googleAnalytics id="${grailsApplication.config.lcms.googleAnalytics.webPropertyId}" />
    </head>
    <body>
    <g:layoutBody />
    <g:javascript library="jquery" plugin="jquery" />
    </body>
</html>