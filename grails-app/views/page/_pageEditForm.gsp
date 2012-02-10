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
    
    <h3>Puffar</h3>
    <table id="puffs">
	    <tr><th><g:message code="puff.link.label" default="Länk" /></th><th><g:message code="puff.title.label" default="Titel" /></th><th><g:message code="puff.image.label" default="Bild" /></th></tr>
	    <g:each in="${pageInstance.puffs}" var="puffInstance" status="index">
	    	<g:hiddenField name="expandablePuffList[${index}].id" value="${puffInstance?.id}" />
	    	<input type="hidden" name='expandablePuffList[${index}].deleted' id='expandablePuffList[${index}].deleted' value='false'/>
		    <tr id="puff_${index}_1">
		    	<td class="${hasErrors(bean: puffInstance, field: 'link', 'errors')}"><g:textField name="expandablePuffList[${index}].link" value="${puffInstance?.link}" /></td>
		    	<td class="${hasErrors(bean: puffInstance, field: 'title', 'errors')}"><g:textField name="expandablePuffList[${index}].title" value="${puffInstance?.title}" /></td>
		    	<td class="${hasErrors(bean: puffInstance, field: 'image', 'errors')}">
		    		<g:dropdown options="${images}" value="${fieldValue(bean: pageInstance, field: 'template')}" value="${puffInstance.image?.id}" name="expandablePuffList[${index}].image.id"></g:dropdown>
		    	</td></tr>
		    <tr id="puff_${index}_2">
		    	<td colspan="2"><g:textArea name="expandablePuffList[${index}].description" value="${puffInstance?.description}" /></td>
		    	<td colspan="1">
		    		<div class="buttons"><span class="button"><input type="button" class="delete" value="${message(code:'puff.deletePuff.label', default:'Ta bort puff')}" onclick="markPuffAsDeleted(${index});" /></span></div>
		    	</td>
		    </tr>
		</g:each>
		
		    <tr id="puffRow1" class="hidden">
		   		
		    	<td><input type="hidden" name='expandablePuffList[puffCount].deleted' id='expandablePuffList[puffCount].deleted' value='false' disabled="disabled"/>
		    		<g:textField name="expandablePuffList[puffCount].link" value="" disabled="disabled" /></td>
		    	<td><g:textField name="expandablePuffList[puffCount].title" value="" disabled="disabled" /></td>
		    	<td>
		    		<g:select from="${images}" name="expandablePuffList[puffCount].image.id" optionKey="${{it.key}}" optionValue="${{it.value}}" disabled="true"/>
		    	</td></tr>
		    <tr id="puffRow2" class="hidden">
		    	<td colspan="2"><g:textArea name="expandablePuffList[puffCount].description" value="" disabled="disabled" /></td>
		    	<td colspan="1">
		    	<div class="buttons"><span class="button"><input type="button" class="delete" name="deletePuff" value="${message(code:'puff.deletePuff.label', default:'Ta bort puff')}" /></span></div>
		    	</td></tr>
    
	</table>
	<div class="buttons"><input type="button" class="add" value="${message(code:'puff.addPuff.label', default:'Lägg till ny puff')}" id="addNewPuffButton" /><span id="puffCount" class="hidden">${pageInstance.puffs.size()}</span></div>
	<br/>
</div>

<div class="aside publish">
  	<div class="input ${hasErrors(bean: pageInstance, field: 'status', 'errors')}">
		<label for="status"><g:message code="page.status.label" default="Status" /></label>
		<g:select name="status" from="['draft', 'pending', 'published', 'autoSave']" value="${pageInstance?.status}" valueMessagePrefix="pageStatus" />
	</div>

	<div class="input ${hasErrors(bean: pageInstance, field: 'publishStart', 'errors')}">
		<label for="publishStart"><g:message code="page.publishStart.label" default="Publish Start" /></label>
		<g:datePicker name="publishStart" precision="minute" years="${2010..2020}" value="${pageInstance?.publishStart}" default="none" noSelection="['': '']" />
	</div>

	<div class="input ${hasErrors(bean: pageInstance, field: 'publishStop', 'errors')}">
		<label for="publishStop"><g:message code="page.publishStop.label" default="Publish Stop" /></label>
		<g:datePicker name="publishStop" precision="minute" years="${2010..2020}" value="${pageInstance?.publishStop}" default="none" noSelection="['': '']" />
	</div>
</div>
  
<div class="aside meta">  
  	<div class="input ${hasErrors(bean: pageInstance, field: 'metaPage', 'errors')}">
		<label for="metaPage"><g:message code="page.metaPage.label" default="Meta page: " /></label>
		<g:checkBox name="metaPage" checked="${pageInstance.metaPage ? 'true' : 'false'}" value="true" />
	</div>
	
	<div class="input ${hasErrors(bean: pageInstance, field: 'menuStyle', 'errors')}">
		<label for="menuStyle"><g:message code="page.menuStyle.label" default="Menystyling" /></label>
		<g:textField name="menuStyle" value="${fieldValue(bean: pageInstance, field:'menuStyle') }"	/>
	</div>
  	
  	<div class="input ${hasErrors(bean: pageInstance, field: 'pageOrder', 'errors')}">
		<label for="pageOrder"><g:message code="page.pageOrder.label" default="Page order: " /></label>
		<g:textField name="pageOrder" size="4" value="${fieldValue(bean: pageInstance, field: 'pageOrder')}" />
	</div>
	
	<g:if test="${Arrays.asList('frontpage', 'sitemap', 'legalSources').indexOf(pageInstance.template) == -1}">
	<div class="input ${hasErrors(bean: pageInstance, field: 'template', 'errors')}">
		<label for="template"><g:message code="page.template.label" default="Page template: " /></label>
		<g:dropdown options="${grailsApplication.config.lagrummet.page.templates}" value="${fieldValue(bean: pageInstance, field: 'template')}" name="template"></g:dropdown>
	</div>
	</g:if>
	
</div>