<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="stylesheet" type="text/css" media="print" href="${resource(dir:'css',file:'print.css')}" />
        <META name="serverURL" content="${resource()}">
        <link href="${resource(dir:'images',file:'favicon.ico')}" rel="shortcut icon" /> 
        <g:mobileDeviceWidth />
        <g:layoutHead />
		<!--[if lt IE 9]>
			<link rel="stylesheet" href="${resource(dir:'css',file:'ie.css')}" />
			<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
		<g:googleAnalytics id="${grailsApplication.config.lagrummet.googleAnalytics.webPropertyId}" />
    </head>
    <body>
		<div id="logo">
			<a href="${grailsApplication.config.grails.serverURL}">${siteProps?.siteTitle ?: "lagrummet<span class='hlight'>.se</span>"}</a>
		</div>
		<!-- <a href="#primaryNavigation" id="mobileNavLink">Navigering</a>  -->
		<header id="siteHeader">
			<nav id="sitelinks">
				<g:menu root="toppmeny" rootTag="ul">
					<li><a accesskey="L" href="http://app.eu.readspeaker.com/cgi-bin/rsent?customerid=5329&amp;lang=en_us&amp;readid=content&amp;url=${resource(absolute: true)+request.forwardURI.substring(request.getContextPath().length()).encodeAsURL()}%2F" onclick="readpage(this.href, 'xp1'); return false;"> <img src="${resource()}/images/readspeaker-icon.gif" alt="Lyssna p&aring; sidans text med ReadSpeaker" title="Lyssna p&aring; sidans text med ReadSpeaker" /> Lyssna</a></li>
				</g:menu>		
			</nav>
			<nav id="breadcrumbs">
				<g:breadcrumbs page="${page}" />
			</nav>
			<g:form mapping="search" method="GET" name="search">
				<div class="input" id="searchCategory">
					<label for="cat">Avgränsa din sökning</label>
					<select id="cat" name="cat">
					<g:each in="${siteProps?.searchCats}">
						<g:if test="${session?.cat == it}">
							<option value="${it}" selected="selected" data-rel="${message(code:"category.description.$it")}"><g:message code="category.${it}"/></option>
						</g:if>
						<g:else>
							<option value="${it}" data-rel="${message(code:"category.description.$it")}"><g:message code="category.${it}"/></option>
						</g:else>
					</g:each>
					</select>
				</div>
				<div class="input" id="searchQuery"><g:textField name="query" autocomplete="off" maxlength="${grailsApplication.config.lagrummet.search.maxLength}"/><ul id="searchSuggestions"></ul></div>
				<g:submitButton name="searchSubmit" value="Sök"/>
			</g:form>
			<p class="extSearchLabel"><g:link mapping="extendedSearch"><g:message code="extendedSearch.label" default="Utökad sökning" /></g:link></p>
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

		<script src="http://f1.eu.readspeaker.com/script/5329/rs_embhl_v2_sv_se.js" type="text/javascript"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
	    <g:javascript library="application" />
    </body>
</html>