

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
            <g:form method="post">
                <g:hiddenField name="id" value="${sitePropertiesInstance?.id}" />
                <g:hiddenField name="version" value="${sitePropertiesInstance?.version}" />
                <g:hiddenField name="title" value="${sitePropertiesInstance?.title}" />
                
                <div class="input ${hasErrors(bean: sitePropertiesInstance, field: 'siteTitle', 'errors')}">
					<label for="title"><g:message code="siteProperties.siteTitle.label" default="siteTitle" /></label>
					<g:textField name="siteTitle" value="${sitePropertiesInstance?.siteTitle}" />
				</div>
                
                <div class="input ${hasErrors(bean: sitePropertiesInstance, field: 'headerNavigation', 'errors')}">
					<label for="title"><g:message code="siteProperties.headerNavigation.label" default="headerNavigation" /></label>
					<g:textArea name="headerNavigation" value="${sitePropertiesInstance?.headerNavigation}" />
				</div>
				
				<div class="input ${hasErrors(bean: sitePropertiesInstance, field: 'footer', 'errors')}">
					<label for="title"><g:message code="siteProperties.footer.label" default="Footer" /></label>
					<g:textArea name="footer" value="${sitePropertiesInstance?.footer}" />
				</div>
				
				<div class="input ${hasErrors(bean: sitePropertiesInstance, field: 'primaryNavigation', 'errors')}">
					<label for="title"><g:message code="siteProperties.primaryNavigation.label" default="primaryNavigation" /></label>
					<g:textArea name="primaryNavigation" id="primNav" value="${sitePropertiesInstance?.primaryNavigation}" />
				</div>

                <div class="buttons">
                	<span class="button"><g:actionSubmit  name="update" action="update" class="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
