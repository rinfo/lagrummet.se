
<%@ page import="se.lagrummet.Page" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="page.list.label" /></h1>
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
                        
                            <td><g:message code="pageStatus.${pageInstance.status}" default="${pageInstance.status}" /></td>
                        
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
