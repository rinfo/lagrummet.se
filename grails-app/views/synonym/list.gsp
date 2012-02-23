
<%@ page import="se.lagrummet.Synonym" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'synonym.label', default: 'Synonym')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="synonym.list.label" args="[entityName]"  /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'synonym.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="synonym" title="${message(code: 'synonym.synonym.label', default: 'Synonym')}" />
                        
                            <g:sortableColumn property="baseTerm" title="${message(code: 'synonym.baseTerm.label', default: 'Grundterm')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${synonymInstanceList}" status="i" var="synonymInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="edit" id="${synonymInstance.id}">${fieldValue(bean: synonymInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: synonymInstance, field: "synonym")}</td>
                        
                            <td>${fieldValue(bean: synonymInstance, field: "baseTerm")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${synonymInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
