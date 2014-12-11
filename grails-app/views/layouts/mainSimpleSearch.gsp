<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="sv">
    <head>
       <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
        <title><g:layoutTitle default="Grails" /></title>        
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link href='http://fonts.googleapis.com/css?family=Fira+Sans' rel='stylesheet' type='text/css'>
        <link rel="stylesheet" href="${resource(dir:'css',file:'mainSimpleSearch.css')}" />
        <link rel="stylesheet" type="text/css" media="print" href="${resource(dir:'css',file:'print.css')}" />
        <META name="serverURL" content="${resource()}" />
        <link href="${resource(dir:'images',file:'favicon.ico')}" rel="shortcut icon" /> 
        <g:mobileDeviceWidth />
        <g:layoutHead />
		<!--[if lt IE 9]>
			<link rel="stylesheet" href="${resource(dir:'css',file:'ieSimpleSearch.css')}" />
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
                                <g:if test="${page?.template?.equals("english")}" >
                                    <li><a accesskey="L" href="http://app.eu.readspeaker.com/cgi-bin/rsent?customerid=5329&amp;lang=en_us&amp;readid=content&amp;url=${resource(absolute: true).encodeAsURL()}%2F" onclick="readpage(this.href, 'xp1'); return false;"> <img src="${resource()}/images/readspeaker-icon.gif" alt="Lyssna p&aring; sidans text med ReadSpeaker" title="Lyssna p&aring; sidans text med ReadSpeaker" /> Lyssna</a></li>
                                </g:if>
                                <g:else>
                                    <li><a accesskey="L" href="http://app.eu.readspeaker.com/cgi-bin/rsent?customerid=5329&amp;lang=sv_se&amp;readid=content&amp;url=${resource(absolute: true).encodeAsURL()}%2F" onclick="readpage(this.href, 'xp1'); return false;"> <img src="${resource()}/images/readspeaker-icon.gif" alt="Lyssna p&aring; sidans text med ReadSpeaker" title="Lyssna p&aring; sidans text med ReadSpeaker" /> Lyssna</a></li>
                                </g:else>
                            </g:menu>			
                    </nav>
                    <nav id="breadcrumbs">
                            <g:breadcrumbs page="${page}" />
                    </nav>
                    <div class="slogan">
                        <p>
                            Lagrummet.se är en gemensam webbplats för den offentliga förvaltningens rättsinformation.
                        </p>
                    </div>
                    <g:form mapping="search" method="GET" name="search" class="searchForm">
                            <input type="hidden" id="cat" name="cat" class="hidden" value="Ovrigt" />
                            <div class="input" id="searchQuery">
                                <g:textField name="query" autocomplete="off" placeholder="Sök ur texter på lagrummet.se" class="searchField " maxlength="${grailsApplication.config.lagrummet.search.maxLength}"/>
                                <ul id="searchSuggestions" class="searchDropdown"></ul>
                            </div>
                            <g:submitButton name="searchSubmit" value="Sök" class="searchButton" />
                    </g:form>
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
	    <g:javascript library="application" />		
	
    </body>
</html>