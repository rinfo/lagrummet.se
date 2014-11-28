

<%@ page import="se.lagrummet.LegalSource" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'legalSource.label', default: 'LegalSource')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${legalSourceInstance}">
            <div class="errors">
                <g:renderErrors bean="${legalSourceInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" name="form_edit_source" useToken="true">
                <g:hiddenField name="id" value="${legalSourceInstance?.id}" />
                <g:hiddenField name="version" value="${legalSourceInstance?.version}" />
                
                <g:render template="legalSourceEditForm" />
                
                <div class="buttons">
                    <span class="button"><g:actionSubmit id="save" class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'legalSource.button.delete.confirm.message', args:[legalSourceInstance.name], default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
