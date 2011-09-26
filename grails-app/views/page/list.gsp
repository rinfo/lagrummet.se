
<%@ page import="se.lagrummet.Page" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'page.id.label', default: 'Id')}" />
                            
                            <g:sortableColumn property="title" title="${message(code: 'page.id.title', default: 'Title') }" />
                        
                            <th><g:message code="page.parent.label" default="Parent" /></th>
                        
                            <g:sortableColumn property="status" title="${message(code: 'page.status.label', default: 'Status')}" />
                        
                            <g:sortableColumn property="publishStart" title="${message(code: 'page.publishStart.label', default: 'Publish Start')}" />
                        
                            <g:sortableColumn property="publishStop" title="${message(code: 'page.publishStop.label', default: 'Publish Stop')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${pageInstanceList}" status="i" var="pageInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="edit" id="${pageInstance.id}">${fieldValue(bean: pageInstance, field: "id")}</g:link></td>
                        
                        	<td>${fieldValue(bean: pageInstance, field: "title") }</td>
                        	
                            <td>${fieldValue(bean: pageInstance, field: "parent")}</td>
                        
                            <td>${fieldValue(bean: pageInstance, field: "status")}</td>
                        
                            <td><g:formatDate date="${pageInstance.publishStart}" /></td>
                        
                            <td><g:formatDate date="${pageInstance.publishStop}" /></td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${pageInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
