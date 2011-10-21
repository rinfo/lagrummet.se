
<%@ page import="se.lagrummet.Media" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'media.label', default: 'Media')}" />
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
                        
                        	<g:sortableColumn property="title" title="${message(code: 'media.title.label', default: 'Titel')}" />
                        
                            <g:sortableColumn property="filename" title="${message(code: 'media.filename.label', default: 'Filnamn')}" />
                        
                            <g:sortableColumn property="dateCreated" title="${message(code: 'media.dateCreated.label', default: 'Skapad')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${mediaInstanceList}" status="i" var="mediaInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                        	<td><g:link action="edit" id="${mediaInstance.id}">${fieldValue(bean: mediaInstance, field: "title")}</g:link></td>
                        
                            <td><a href="${resource() + "/" + mediaInstance?.filename}">${mediaInstance?.filename}</a></td>
                            
                            <td>${fieldValue(bean: mediaInstance, field: "dateCreated")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${mediaInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
