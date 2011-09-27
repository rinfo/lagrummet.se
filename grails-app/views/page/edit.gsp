

<%@ page import="se.lagrummet.Page" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${pageInstance}">
            <div class="errors">
                <g:renderErrors bean="${pageInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" mapping="pageAdmin">
                <g:hiddenField name="id" value="${pageInstance?.id}" />
                <g:hiddenField name="version" value="${pageInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                       		<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="title"><g:message code="page.title.label" default="Title" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageInstance, field: 'title', 'errors')}">
                                    <g:textField name="title" value="${pageInstance?.title}" />
                                </td>
                            </tr>

                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="h1"><g:message code="page.h1.label" default="H1" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageInstance, field: 'h1', 'errors')}">
                                    <g:textField name="h1" value="${pageInstance?.h1}" />
                                </td>
                            </tr>
                            
							<tr class="prop">
                                <td valign="top" class="name">
                                  <label for="permalink"><g:message code="page.permalink.label" default="Permalink" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageInstance, field: 'permalink', 'errors')}">
                                    <g:textField name="permalink" value="${pageInstance?.permalink}" />
                                </td>
                            </tr>
                                                   
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="content"><g:message code="page.content.label" default="Content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageInstance, field: 'content', 'errors')}">
                                    <g:textField name="content" value="${pageInstance?.content}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="parent"><g:message code="page.parent.label" default="Parent" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageInstance, field: 'parent', 'errors')}">
                                    <g:select name="parent.id" from="${se.lagrummet.Page.list()}" optionKey="id" value="${pageInstance?.parent?.id}" noSelection="['null': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="status"><g:message code="page.status.label" default="Status" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageInstance, field: 'status', 'errors')}">
                                    <g:select name="status" from="['draft', 'pending', 'published']" value="${pageInstance?.status}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publishStart"><g:message code="page.publishStart.label" default="Publish Start" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageInstance, field: 'publishStart', 'errors')}">
                                    <g:datePicker name="publishStart" precision="day" value="${pageInstance?.publishStart}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="publishStop"><g:message code="page.publishStop.label" default="Publish Stop" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageInstance, field: 'publishStop', 'errors')}">
                                    <g:datePicker name="publishStop" precision="day" value="${pageInstance?.publishStop}" default="none" noSelection="['': '']" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="children"><g:message code="page.children.label" default="Children" /></label>
                                </td>
                           		<td valign="top" class="value ${hasErrors(bean: pageInstance, field: 'children', 'errors')}">
                                    
								<ul>
								<g:each in="${pageInstance?.children?}" var="c">
								    <li><g:link controller="page" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
								</g:each>
								</ul>
								<g:link controller="page" action="create" params="['page.id': pageInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'page.label', default: 'Page')])}</g:link>

                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="pageOrder"><g:message code="page.pageOrder.label" default="Page Order" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: pageInstance, field: 'pageOrder', 'errors')}">
                                    <g:textField name="pageOrder" value="${fieldValue(bean: pageInstance, field: 'pageOrder')}" />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                	<span class="button"><g:submitButton name="update" class="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
