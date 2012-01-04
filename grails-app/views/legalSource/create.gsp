

<%@ page import="se.lagrummet.LegalSource" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'legalSource.label', default: 'LegalSource')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${legalSourceInstance}">
            <div class="errors">
                <g:renderErrors bean="${legalSourceInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="url"><g:message code="legalSource.url.label" default="Url" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: legalSourceInstance, field: 'url', 'errors')}">
                                    <g:textField name="url" value="${legalSourceInstance?.url}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name"><g:message code="legalSource.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: legalSourceInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${legalSourceInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="grouping"><g:message code="legalSource.grouping.label" default="Grouping" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: legalSourceInstance, field: 'grouping', 'errors')}">
                                    <g:textField name="grouping" value="${legalSourceInstance?.grouping}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
