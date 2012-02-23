

<%@ page import="se.lagrummet.Synonym" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'synonym.label', default: 'Synonym')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${synonymInstance}">
            <div class="errors">
                <g:renderErrors bean="${synonymInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${synonymInstance?.id}" />
                <g:hiddenField name="version" value="${synonymInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="synonym"><g:message code="synonym.synonym.label" default="Synonym" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: synonymInstance, field: 'synonym', 'errors')}">
                                    <g:textField name="synonym" value="${synonymInstance?.synonym}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="baseTerm"><g:message code="synonym.baseTerm.label" default="Search Term" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: synonymInstance, field: 'baseTerm', 'errors')}">
                                    <g:textField name="baseTerm" value="${synonymInstance?.baseTerm}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
