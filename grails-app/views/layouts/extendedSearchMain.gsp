<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css/ui-lightness/',file:'jquery-ui-1.8.16.custom.css')}" />
        <link rel="stylesheet" type="text/css" media="print" href="${resource(dir:'css',file:'print.css')}" />
        <META name="serverURL" content="${resource()}">
        <link href="${resource(dir:'images',file:'favicon.ico')}" rel="shortcut icon" /> 
        <g:mobileDeviceWidth />
        <g:layoutHead />
        <!--[if IE]>
			<link rel="stylesheet" href="${resource(dir:'css',file:'ie.css')}" />
			<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<g:googleAnalytics id="${grailsApplication.config.lagrummet.googleAnalytics.webPropertyId}" />
    </head>
    <body>
	<g:googleTagManager id="${grailsApplication.config.lagrummet.googleTagManager.webPropertyId}" />
	<div id="logo">
			<a href="${grailsApplication.config.grails.serverURL}">${siteProps?.siteTitle}</a>
		</div>
		<!-- <a href="#primaryNavigation" id="mobileNavLink">Navigering</a>  -->
		<header id="siteHeader">
			<nav id="sitelinks">
				<g:menu root="toppmeny" rootTag="ul">
					<li><a accesskey="L" href="http://app.eu.readspeaker.com/cgi-bin/rsent?customerid=5329&amp;lang=sv_se&amp;readid=content&amp;url=${resource(absolute: true).encodeAsURL()}%2F" onclick="readpage(this.href, 'xp1'); return false;"> <img src="${resource()}/images/readspeaker-icon.gif" alt="Lyssna p&aring; sidans text med ReadSpeaker" title="Lyssna p&aring; sidans text med ReadSpeaker"> Lyssna</a> </li>
				</g:menu>		
			</nav>
			<nav id="breadcrumbs">
				<g:breadcrumbs page="${page}" />
			</nav>
			<div id="readspeaker_button1" class="rs_skip"> </div> <div id='xp1'></div>
		</header>
		
		<div id="content">
			<g:layoutBody />
			<footer id="siteFooter">
				<g:menu root="sidfot" />
		    	<div id="version">${grailsApplication.metadata['app.name']} (v${grailsApplication.metadata['app.version']})</div>
		    </footer>
		</div>
	    
	    <nav id="primaryNavigation">
			<g:menu root="huvudmeny" activePage="${page}" />
		</nav>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
	    <g:javascript library="jquery.ui.datepicker-sv" />
	    <g:javascript library="application" />
    </body>
</html>