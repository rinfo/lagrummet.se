
<%@ page import="se.lagrummet.SiteProperties" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'siteProperties.label', default: 'SiteProperties')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
          
            <!--            
              Show document
            -->
          
          
            <h1><g:message code="default.show.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="siteProperties.id.label" default="Id" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: sitePropertiesInstance, field: "id")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="siteProperties.siteTitle.label" default="Site Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: sitePropertiesInstance, field: "siteTitle")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="siteProperties.footer.label" default="Footer" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: sitePropertiesInstance, field: "footer")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="siteProperties.headerNavigation.label" default="Header Navigation" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: sitePropertiesInstance, field: "headerNavigation")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="siteProperties.primaryNavigation.label" default="Primary Navigation" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: sitePropertiesInstance, field: "primaryNavigation")}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name"><g:message code="siteProperties.title.label" default="Title" /></td>
                            
                            <td valign="top" class="value">${fieldValue(bean: sitePropertiesInstance, field: "title")}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <g:hiddenField name="id" value="${sitePropertiesInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
