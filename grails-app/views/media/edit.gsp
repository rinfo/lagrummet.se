

<%@ page import="se.lagrummet.Media" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'media.label', default: 'Media')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
		<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
	      <g:if test="${flash.message}">
	    <div class="message">${flash.message}</div>
	   </g:if>
	   <g:hasErrors bean="${mediaInstance}">
	    <div class="errors">
	        <g:renderErrors bean="${mediaInstance}" as="list" />
	    </div>
		</g:hasErrors>
	
	         <g:form method="post" >
	            <g:hiddenField name="id" value="${mediaInstance?.id}" />
	            <g:hiddenField name="version" value="${mediaInstance?.version}" />
	             
	            <div class="input ${hasErrors(bean: mediaInstance, field: 'imageFile', 'errors')}">
					<g:message code="media.filename.label" default="Filnamn" />: <a href="${resource() + "/" + mediaInstance?.filename}">${mediaInstance?.filename}</a>
				</div>
				<div class="input ${hasErrors(bean: mediaInstance, field: 'imageFile', 'errors')}">
					<g:message code="media.parent.label" default="Tillhör" />: ${mediaInstance.parent?.title}
				</div>
				
				<div class="input ${hasErrors(bean: mediaInstance, field: 'title', 'errors')}">
					<label for="title"><g:message code="media.title.label" default="Titel" /></label>
					<g:textField name="title" value="${mediaInstance?.title}" />
				</div>
				
	             <div class="buttons">
	                 <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
	                 <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
	             </div>
	         </g:form>
    </body>
</html>
