
<%@ page import="se.lagrummet.LegalSource" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'legalSource.label', default: 'Rättskällor')}" />
        <title><g:message code="legalSource.rattskallor" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="legalSource.rattskallor" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="name" title="${message(code: 'legalSource.name.label', default: 'Namn')}" />

                            <g:sortableColumn property="url" title="${message(code: 'legalSource.url.label', default: 'Url')}" />
                        
                            <g:sortableColumn property="grouping" title="${message(code: 'legalSource.grouping.label', default: 'Grupp')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${legalSourceInstanceList}" status="i" var="legalSourceInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="edit" id="${legalSourceInstance.id}">${fieldValue(bean: legalSourceInstance, field: "name")}</g:link></td>
                        
                            <td>${fieldValue(bean: legalSourceInstance, field: "url")}</td>
                        
                            <td>${fieldValue(bean: legalSourceInstance, field: "grouping")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${legalSourceInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
