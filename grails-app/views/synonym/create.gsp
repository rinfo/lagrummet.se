

<%@ page import="se.lagrummet.Synonym" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'synonym.label', default: 'Synonym')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.create.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${synonymInstance}">
            <div class="errors">
                <g:renderErrors bean="${synonymInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" >
            	<div class="input ${hasErrors(bean: legalSourceInstance, field: 'synonym', 'errors')}">
					<label for="synonym"><g:message code="synonym.synonym.label" default="Synonym" /></label>
					<g:textField name="synonym" value="${synonymInstance?.synonym}" />
				</div>                        
                            
				<div class="input ${hasErrors(bean: synonymInstance, field: 'baseTerm', 'errors')}">
					<label for="baseTerm"><g:message code="synonym.baseTerm.label" default="Search Term" /></label>
					<g:textField name="baseTerm" value="${synonymInstance?.baseTerm}" />
				</div>
				
                <div class="buttons">
                    <span class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
