

<%@ page import="se.lagrummet.SiteProperties" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'siteProperties.label', default: 'SiteProperties')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
    	<div class="body">
    		<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${sitePropertiesInstance}">
            <div class="errors">
                <g:renderErrors bean="${sitePropertiesInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" useToken="true">
                <g:hiddenField name="id" value="${sitePropertiesInstance?.id}" />
                <g:hiddenField name="version" value="${sitePropertiesInstance?.version}" />
                <g:hiddenField name="title" value="${sitePropertiesInstance?.title}" />
                
                <div class="input ${hasErrors(bean: sitePropertiesInstance, field: 'siteTitle', 'errors')}">
					<label for="title"><g:message code="siteProperties.siteTitle.label" default="siteTitle" /></label>
					<g:textField name="siteTitle" value="${sitePropertiesInstance?.siteTitle}" class="wideInput"/>
				</div>
                
				<div class="input ${hasErrors(bean: sitePropertiesInstance, field: 'searchCats', 'errors')}">
					<label for="title"><g:message code="siteProperties.searchCats.label" default="searchCats" /></label>
					<g:select name="searchCats"
				          from="${grailsApplication.config.lagrummet.search.availableCategories}"
				          valueMessagePrefix="category"
				          multiple="true" />
				    <script>
				    jQuery(document).ready(function($) {
					    var sel = $('#searchCats');
					    <g:each in="${sitePropertiesInstance?.searchCats}" var="s">
					    	sel.find('option[value="${s}"]').attr('selected', 'selected');
					    </g:each>
				    });
				    </script>
				</div>

                <div class="buttons">
                	<span class="button"><g:actionSubmit  name="update" action="update" class="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
