<div class="content">
	<g:if test="${pageInstance?.id != null}">
		<h1 class="${hasErrors(bean: pageInstance, field: 'h1', 'errors')}"><a href="#">${pageInstance?.h1}</a></h1>
  		<g:textField name="h1" value="${pageInstance?.h1}" />
	</g:if>
	<g:else>
		<h1 class="${hasErrors(bean: pageInstance, field: 'h1', 'errors')}"><a href="#">${pageInstance?.h1}</a></h1>
  		<g:textField name="h1" value="${message(code: 'page.enterTitle.label', default: 'Enter heading here')}" />
	</g:else>
  	
 	
  	<div class="permalink input ${hasErrors(bean: pageInstance, field: 'permalink', 'errors')}">
		${grailsApplication.config.grails.serverURL}/<g:textField name="permalink" value="${pageInstance?.permalink}" />
	</div>
  	
  	<div class="title input ${hasErrors(bean: pageInstance, field: 'title', 'errors')}">
		<label for="title"><a href="#"><g:message code="page.title.label" default="Different title? Click here" /></a></label>
		<g:textField name="title" value="${pageInstance?.title}" />
	</div>
  
    <div class="mceEditor input">
    	<g:textArea name="content" value="${pageInstance?.content}" />
    </div> 
</div>

<div class="aside publish">
  	<div class="input ${hasErrors(bean: pageInstance, field: 'status', 'errors')}">
		<label for="status"><g:message code="page.status.label" default="Status" /></label>
		<g:select name="status" from="['draft', 'pending', 'published', 'autoSave']" value="${pageInstance?.status}" valueMessagePrefix="pageStatus" />
	</div>

	<div class="input ${hasErrors(bean: pageInstance, field: 'publishStart', 'errors')}">
		<label for="publishStart"><g:message code="page.publishStart.label" default="Publish Start" /></label>
		<g:datePicker name="publishStart" precision="minute" value="${pageInstance?.publishStart}" default="none" noSelection="['': '']" />
	</div>

	<div class="input ${hasErrors(bean: pageInstance, field: 'publishStop', 'errors')}">
		<label for="publishStop"><g:message code="page.publishStop.label" default="Publish Stop" /></label>
		<g:datePicker name="publishStop" precision="minute" value="${pageInstance?.publishStop}" default="none" noSelection="['': '']" />
	</div>
</div>
  
<div class="aside meta">  
  	<div class="input ${hasErrors(bean: pageInstance, field: 'pageOrder', 'errors')}">
		<label for="pageOrder"><g:message code="page.pageOrder.label" default="Page order: " /></label>
		<g:textField name="pageOrder" size="4" value="${fieldValue(bean: pageInstance, field: 'pageOrder')}" />
	</div>
</div>