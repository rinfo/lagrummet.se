<!DOCTYPE html>
<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="stylesheet" type="text/css" media="print" href="${resource(dir:'css',file:'print.css')}" />
        <META name="serverURL" content="${resource()}">
        <g:layoutHead />
		<!--[if lt IE 9]>
			<link rel="stylesheet" href="${resource(dir:'css',file:'ie.css')}" />
			<script src="//html5shiv.googlecode.com/svn/trunk/html5.js"></script>
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
			<g:form mapping="search" method="GET" name="search">
				<div class="input" id="searchCategory">
					<p>Vad vill du söka?</p>
					<label for="cat">Avgränsa din sökning</label>
					<select id="cat" name="cat">
					<g:each in="${siteProps?.searchCats}">
						<g:if test="${params.cat == it}">
							<option value="${it}" selected="selected" rel="${message(code:"category.description.$it")}"><g:message code="category.${it}"/></option>
						</g:if>
						<g:else>
							<option value="${it}" rel="${message(code:"category.description.$it")}"><g:message code="category.${it}"/></option>
						</g:else>
					</g:each>
					</select>
				</div>
				<div class="input" id="searchQuery"><g:textField name="query"/></div>
				<g:submitButton name="searchSubmit" value="Sök"/>
			</g:form>
			<p class="extSearchLabel"><g:link mapping="extendedSearch"><g:message code="extendedSearch.label" default="Utökad sökning" /></g:link></p>
		</header>
	    <g:layoutBody />
	    <footer id="siteFooter">${siteProps?.footer}
	    	${grailsApplication.metadata['app.name']} (v${grailsApplication.metadata['app.version']})
	    </footer>
	    <script type='text/javascript' src='http://ajax.googleapis.com/ajax/libs/jquery/1.7.0/jquery.min.js?ver=1.7.0'></script>
	    <g:javascript library="application" />
    </body>
</html>