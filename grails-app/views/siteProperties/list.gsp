
<%@ page import="se.lagrummet.SiteProperties" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'siteProperties.label', default: 'SiteProperties')}" />
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
                        
                            <g:sortableColumn property="id" title="${message(code: 'siteProperties.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="siteTitle" title="${message(code: 'siteProperties.siteTitle.label', default: 'Site Title')}" />
                        
                            <g:sortableColumn property="footer" title="${message(code: 'siteProperties.footer.label', default: 'Footer')}" />
                        
                            <g:sortableColumn property="headerNavigation" title="${message(code: 'siteProperties.headerNavigation.label', default: 'Header Navigation')}" />
                        
                            <g:sortableColumn property="primaryNavigation" title="${message(code: 'siteProperties.primaryNavigation.label', default: 'Primary Navigation')}" />
                        
                            <g:sortableColumn property="title" title="${message(code: 'siteProperties.title.label', default: 'Title')}" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${sitePropertiesInstanceList}" status="i" var="sitePropertiesInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${sitePropertiesInstance.id}">${fieldValue(bean: sitePropertiesInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: sitePropertiesInstance, field: "siteTitle")}</td>
                        
                            <td>${fieldValue(bean: sitePropertiesInstance, field: "footer")}</td>
                        
                            <td>${fieldValue(bean: sitePropertiesInstance, field: "headerNavigation")}</td>
                        
                            <td>${fieldValue(bean: sitePropertiesInstance, field: "primaryNavigation")}</td>
                        
                            <td>${fieldValue(bean: sitePropertiesInstance, field: "title")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${sitePropertiesInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
