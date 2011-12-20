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
    
    <g:if test="${pageInstance.puffs.size() > 0}">
    <h3>Puffar</h3>
    <table id="puffs">
	    <tr><th><g:message code="puff.link.label" default="Länk" /></th><th><g:message code="puff.title.label" default="Titel" /></th><th><g:message code="puff.image.label" default="Bild" /></th></tr>
	    <g:each in="${pageInstance.puffs}" var="puffInstance">
	    	<g:hiddenField name="puffs.${puffInstance.id}.id" value="${puffInstance?.id}" />
		    <tr>
		    	<td class="${hasErrors(bean: puffInstance, field: 'link', 'errors')}"><g:textField name="puffs.${puffInstance.id}.link" value="${puffInstance?.link}" /></td>
		    	<td class="${hasErrors(bean: puffInstance, field: 'title', 'errors')}"><g:textField name="puffs.${puffInstance.id}.title" value="${puffInstance?.title}" /></td>
		    	<td class="${hasErrors(bean: puffInstance, field: 'image', 'errors')}">
		    		<g:dropdown options="${images}" value="${fieldValue(bean: pageInstance, field: 'template')}" value="${puffInstance.image?.id}" name="puffs.${puffInstance.id}.image.id"></g:dropdown>
		    	</td></tr>
		    <tr><td colspan="3"><g:textArea name="puffs.${puffInstance.id}.description" value="${puffInstance?.description}" /></td></tr>
		</g:each>
	</table>
	</g:if>
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
	
	<g:if test="${!Arrays.asList('frontpage', 'sitemap').indexOf(pageInstance.template)}">
	<div class="input ${hasErrors(bean: pageInstance, field: 'template', 'errors')}">
		<label for="template"><g:message code="page.template.label" default="Page template: " /></label>
		<g:dropdown options="${grailsApplication.config.lagrummet.page.templates}" value="${fieldValue(bean: pageInstance, field: 'template')}" name="template"></g:dropdown>
	</div>
	</g:if>
	
</div>