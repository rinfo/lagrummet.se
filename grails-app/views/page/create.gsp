

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
		
		<g:form action="save" >
		    <div class="content">
		    	<h1 class="${hasErrors(bean: pageInstance, field: 'h1', 'errors')}"><a href="">${pageInstance?.h1}</a></h1>
		    	<g:textField name="h1" value="${message(code: 'page.title.enterTitle', default: 'Enter heading here: ')}" />
		    	
		    	<div class="title input ${hasErrors(bean: pageInstance, field: 'title', 'errors')}">
					<label for="title"><a href="#"><g:message code="page.title.label" default="Different title? Click here" /></a></label>
					<g:textField name="title" value="${pageInstance?.title}" />
				</div>		
		
		    	<div class="permalink input ${hasErrors(bean: pageInstance, field: 'permalink', 'errors')}">
					${resource()}/ <g:textField name="permalink" value="${pageInstance?.permalink}" />
				</div>
		    
		        <div class="mceEditor input">
		        	<g:textArea name="content" value="${pageInstance?.content}" />
		        </div> 
		    </div>
		    
		    <div class="aside meta">
		    	<div class="input ${hasErrors(bean: pageInstance, field: 'parent', 'errors')}">
					<label for="parent"><g:message code="page.parent.label" default="Parent: " /></label>
					<g:select name="parent.id" from="${se.lagrummet.Page.list()}" optionKey="id" optionValue="title" value="${pageInstance?.parent?.id}" noSelection="['null': '']" />
				</div>
		    
		    	<div class="input ${hasErrors(bean: pageInstance, field: 'pageOrder', 'errors')}">
					<label for="pageOrder"><g:message code="page.pageOrder.label" default="Page order: " /></label>
					<g:textField name="pageOrder" size="4" value="${fieldValue(bean: pageInstance, field: 'pageOrder')}" />
				</div>
		    </div>
		    
		    <div class="aside publish">
		    	<div class="input ${hasErrors(bean: pageInstance, field: 'status', 'errors')}">
					<label for="status"><g:message code="page.status.label" default="Status" /></label>
					<g:select name="status" from="['draft', 'pending', 'published']" value="${pageInstance?.status}" />
				</div>
		
				<div class="input ${hasErrors(bean: pageInstance, field: 'publishStart', 'errors')}">
					<label for="publishStart"><g:message code="page.publishStart.label" default="Publish Start" /></label>
					<g:datePicker name="publishStart" precision="day" value="${pageInstance?.publishStart}" default="none" noSelection="['': '']" />
				</div>
				
				<div class="input ${hasErrors(bean: pageInstance, field: 'publishStop', 'errors')}">
					<label for="publishStop"><g:message code="page.publishStop.label" default="Publish Stop" /></label>
					<g:datePicker name="publishStop" precision="day" value="${pageInstance?.publishStop}" default="none" noSelection="['': '']" />
				</div>				

		        <div class="button"><g:submitButton name="create" class="save" value="${message(code: 'default.button.create.label', default: 'Create')}" /></div>
		    </div>
		</g:form>
    </body>
</html>
