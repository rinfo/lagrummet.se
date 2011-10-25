

<%@ page import="se.lagrummet.Media" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'media.label', default: 'Media')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
    	<g:if test="${flash.message}">
		    <div class="message">${flash.message}</div>
	    </g:if>
	    <g:hasErrors bean="${mediaInstance}">
		    <div class="errors">
		        <g:renderErrors bean="${mediaInstance}" as="list" />
		    </div>
		</g:hasErrors>
		
		<g:form action="save" method="post" enctype="multipart/form-data">
			<div class="input ${hasErrors(bean: mediaInstance, field: 'imageFile', 'errors')}">
				<label for="mediaFile"><g:message code="user.file.label" default="Fil" /></label>
				<input type="file" name="mediaFile" />
			</div>
			
			<div class="input ${hasErrors(bean: mediaInstance, field: 'title', 'errors')}">
				<label for="title"><g:message code="media.title.label" default="Titel" /></label>
				<g:textField name="title" value="${mediaInstance?.title}" />
			</div>
				
		    <div class="buttons">
		    	<g:actionSubmit name="save" action="save" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" />
		    </div>
		</g:form>
    </body>
</html>
