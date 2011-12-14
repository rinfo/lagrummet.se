<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css/ui-lightness/',file:'jquery-ui-1.8.16.custom.css')}" />
        <link rel="stylesheet" type="text/css" media="print" href="${resource(dir:'css',file:'print.css')}" />
        <META name="serverURL" content="${resource()}">
        <g:mobileDeviceWidth />
        <g:layoutHead />
        <!--[if IE]>
			<script src="${resource(dir:'js',file:'html5IE.js')}"></script>
		<![endif]-->
		<g:googleAnalytics id="${grailsApplication.config.lagrummet.googleAnalytics.webPropertyId}" />
    </head>
    <body>
    	<div id="logo">
			<a href="${resource()}">${siteProps?.siteTitle}</a>
		</div>
		<a href="#primaryNavigation" id="mobileNavLink">Navigering</a>
		<header id="siteHeader">
			<nav id="sitelinks">
				<ul>
					${siteProps?.headerNavigation}
					<li><a accesskey="L" href="http://app.eu.readspeaker.com/cgi-bin/rsent?customerid=5329&amp;lang=sv_se&amp;&amp;url=${resource(absolute: true).encodeAsURL()}" onclick="readpage(this.href, 'xp1'); return false;"> <img src="${resource()}/images/readspeaker-icon.gif" alt="Lyssna p&aring; sidans text med ReadSpeaker" title="Lyssna p&aring; sidans text med ReadSpeaker"> Lyssna</a></li>
				</ul>
			</nav>
			<nav id="breadcrumbs">
				<g:breadcrumbs parent="${page?.parent}" />
			</nav>
		</header>
		
		<div id="content">
			<g:layoutBody />
			<footer id="siteFooter">${siteProps?.footer}
		    	<div id="version">${grailsApplication.metadata['app.name']} (v${grailsApplication.metadata['app.version']})</div>
		    </footer>
		</div>
	    
	    <nav id="primaryNavigation">
			${siteProps?.primaryNavigation}
		</nav>
	    <g:javascript library="jquery" plugin="jquery" />
	    <g:javascript library="jquery-ui-1.8.16.custom.min" />
	    <g:javascript library="jquery.ui.datepicker-sv" />
	    <g:javascript library="application" />
    </body>
</html>