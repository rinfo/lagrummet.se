
<%@ page import="se.lagrummet.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                           	<g:sortableColumn property="fullName" title="${message(code: 'user.fullname.label', default: 'Full name') }" />
                        
                            <g:sortableColumn property="username" title="${message(code: 'user.username.label', default: 'Username')}" />
                            
                            <g:sortableColumn property="role" title="${message(code: 'user.role.label', default: 'Role') }" />
                        
                            <g:sortableColumn property="enabled" title="${message(code: 'user.enabled.label', default: 'Enabled') }" />
                            
                            <g:sortableColumn property="accountExpired" title="${message(code: 'user.accountExpired.label', default: 'Account Expired')}" />
                        
                            <g:sortableColumn property="accountLocked" title="${message(code: 'user.accountLocked.label', default: 'Account Locked')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'user.dateCreated.label', default: 'Date Created')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${userInstanceList}" status="i" var="userInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${userInstance.id}">${fieldValue(bean: userInstance, field: "fullName")}</g:link></td>
                            
                            <td>${fieldValue(bean: userInstance, field: "username")}</td>
                            
                            <td>${userInstance.authorities*.name.join(',')}</td>
                        
                            <td><g:formatBoolean boolean="${userInstance.enabled}" /></td>
                            
                            <td><g:formatBoolean boolean="${userInstance.accountExpired}" /></td>
                        
                            <td><g:formatBoolean boolean="${userInstance.accountLocked}" /></td>
                        
                            <td><g:formatDate date="${userInstance.dateCreated}" /></td>
                        
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
