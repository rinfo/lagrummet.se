

<%@ page import="se.lagrummet.Page" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
	    <g:if test="${flash.message}">
		    <div class="message">${flash.message}</div>
	    </g:if>
	    <g:hasErrors bean="${pageInstance}">
		    <div class="errors">
		        <g:renderErrors bean="${pageInstance}" as="list" />
		    </div>
		</g:hasErrors>   
		
		<g:form mapping="pageAdmin" class="create" method="post">
		    <g:render template="pageEditForm" />
		    
		    <div class="aside buttons">
		    	<g:actionSubmit name="save" action="save" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
		    </div>
		</g:form>
    </body>
</html>
