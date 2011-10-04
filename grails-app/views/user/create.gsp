

<%@ page import="se.lagrummet.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            <br/>
            </g:hasErrors>
            <g:form action="save" >
                <g:render template="userEditForm" />
                
                <div class="input ${hasErrors(bean: userInstance, field: 'role', 'errors')}">
					<label for="title"><g:message code="user.role.label" default="Role" /></label>
					<g:select name="role" 
	   					from="${se.lagrummet.SecRole.list() }"
	   					optionKey="id"
	   					optionValue="name" />
				</div>
                
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
