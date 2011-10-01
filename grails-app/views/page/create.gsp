

<%@ page import="se.lagrummet.Page" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'page.label', default: 'Page')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <tinyMce:resources jquery="true" />
		<script src="${resource(dir:'js',file:'admin.js')}"></script>

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
		
		<g:form action="save" class="create">
		    <g:render template="pageEditForm" />
		    
		    <div class="aside buttons">
		    	<g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
		    </div>
		</g:form>
    </body>
</html>
