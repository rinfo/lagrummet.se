

<%@ page import="se.lagrummet.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
    	<div class="body">
    		<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" useToken="true">
                <g:hiddenField name="id" value="${userInstance?.id}" />
                <g:hiddenField name="version" value="${userInstance?.version}" />
                
                <g:render template="userEditForm" />
                
                <div class="input ${hasErrors(bean: userInstance, field: 'role', 'errors')}">
					<label for="title"><g:message code="user.role.label" default="Role" /></label>
					<g:select name="role" 
									from="${se.lagrummet.SecRole.list() }"
									optionKey="id"
									optionValue="name"
									value="${new ArrayList(userInstance?.authorities).get(0).id }"  />
				</div>

                <div class="buttons">
                	<span class="button"><g:actionSubmit  name="update" action="update" class="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'user.button.delete.confirm.message', args:[userInstance.fullName], default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
