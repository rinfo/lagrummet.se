
<%@ page import="se.lagrummet.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                           	<g:sortableColumn property="fullName" title="${message(code: 'user.fullName.label', default: 'Full name') }" />
                        
                            <g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'Username')}" />
                            
                            <g:sortableColumn property="authorities" title="${message(code: 'user.role.label', default: 'Role') }" />
                        
                            <g:sortableColumn property="department" title="${message(code: 'user.department.label', default: 'Department')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${userInstanceList}" status="i" var="userInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="edit" id="${userInstance.id}">${fieldValue(bean: userInstance, field: "fullName")}</g:link></td>
                            
                            <td>${fieldValue(bean: userInstance, field: "username")}</td>
                            
                            <td>${userInstance.authorities*.name.join(',')}</td>
                        
                            <td>${fieldValue(bean: userInstance, field: "department")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${userInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
