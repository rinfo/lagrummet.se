

<%@ page import="se.lagrummet.SiteProperties" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="${grailsApplication.config.lagrummet.mainLayoutName}" />
        <g:set var="entityName" value="${message(code: 'siteProperties.label', default: 'SiteProperties')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${sitePropertiesInstance}">
            <div class="errors">
                <g:renderErrors bean="${sitePropertiesInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="siteTitle"><g:message code="siteProperties.siteTitle.label" default="Site Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sitePropertiesInstance, field: 'siteTitle', 'errors')}">
                                    <g:textField name="siteTitle" value="${sitePropertiesInstance?.siteTitle}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="footer"><g:message code="siteProperties.footer.label" default="Footer" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sitePropertiesInstance, field: 'footer', 'errors')}">
                                    <g:textField name="footer" value="${sitePropertiesInstance?.footer}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="headerNavigation"><g:message code="siteProperties.headerNavigation.label" default="Header Navigation" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sitePropertiesInstance, field: 'headerNavigation', 'errors')}">
                                    <g:textField name="headerNavigation" value="${sitePropertiesInstance?.headerNavigation}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="primaryNavigation"><g:message code="siteProperties.primaryNavigation.label" default="Primary Navigation" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sitePropertiesInstance, field: 'primaryNavigation', 'errors')}">
                                    <g:textField name="primaryNavigation" value="${sitePropertiesInstance?.primaryNavigation}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="title"><g:message code="siteProperties.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: sitePropertiesInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${sitePropertiesInstance?.title}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
