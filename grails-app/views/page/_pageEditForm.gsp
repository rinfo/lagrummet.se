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
		<label for="title"><g:message code="page.title.label" default="Title" />: <a href="#">${pageInstance?.title}</a></label>
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
		    		<div class="buttons"><input type="button" class="delete" value="${message(code:'puff.deletePuff.label', default:'Ta bort puff')}" onclick="markPuffAsDeleted(${index});" /></div>
		    		<div class="buttons hidden"><input type="button" value="${message(code:'puff.cancelDelete.label', default:'Återställ puff')}" onclick="cancelDeletePuff(${index});" /></div>
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
		    	<div class="buttons"><input type="button" class="delete" name="deletePuff" value="${message(code:'puff.deletePuff.label', default:'Ta bort puff')}" /></div>
		    	<div class="buttons hidden"><input type="button" name="cancelDeletePuffButton" value="${message(code:'puff.cancelDelete.label', default:'Återställ puff')}" /></div>
		    	</td></tr>
    
	</table>
	<div id="puffEditDescription"><g:message code="puff.puffeditform.description" /></div>
	<div class="buttons"><input type="button" class="add" value="${message(code:'puff.addPuff.label', default:'Lägg till ny puff')}" id="addNewPuffButton" /><span id="puffCount" class="hidden">${pageInstance.puffs.size()}</span></div>
	<br/>
</div>

<div class="aside publish">
	<g:if test="${pageInstance.id && pageInstance.status == 'draft'}">
		<div class="buttons">
			<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'page.button.delete.confirm.message', args:[pageInstance.title], default: 'Are you sure?')}');" />
		</div>
	</g:if>
	<g:if test="${pageInstance.id && pageInstance.isCurrentlyPublished()}">
	  	<div class="buttons">
	  		<g:actionSubmit name="unpublish" action="unpublish" class="delete" value="Avpublicera" />
	  	</div>
	</g:if>
	
  	<div class="input ${hasErrors(bean: pageInstance, field: 'status', 'errors')}">
  		<g:hiddenField name="status" value="${pageInstance?.status}" />
		<label for="status"><strong><g:message code="page.status.label" default="Nuvarande status" />: <g:message code="page.status.${pageInstance?.getCurrentPageStatus()}" default="" /></strong></label>
		<label><g:message code="page.author.publishedBy" default="av"/>: ${pageInstance?.author?.username}</label>
	</div>

	<div class="input ${hasErrors(bean: pageInstance, field: 'publishStart', 'errors')}">
		<label for="publishStart"><g:message code="page.publishStart.label" default="Publish Start" /></label>
		<g:datePicker name="publishStart" precision="minute" years="${2010..2020}" value="${pageInstance?.publishStart}" default="none" noSelection="['': '']" />
	</div>

	<div class="input ${hasErrors(bean: pageInstance, field: 'publishStop', 'errors')}">
		<label for="publishStop"><g:message code="page.publishStop.label" default="Publish Stop" /></label>
		<g:datePicker name="publishStop" precision="minute" years="${2010..2020}" value="${pageInstance?.publishStop}" default="none" noSelection="['': '']" />
	</div>
	
	<div class="input ${hasErrors(bean: pageInstance, field: 'reviewDate', 'errors')}">
		<g:checkBox name="reviewDate" value="${false}" />
  		<label for="reviewDate"><g:message code="page.reviewDate.label" default="Uppdatera granskad-datum" /></label>
	</div>
	
	<g:if test="${pageInstance.masterRevision && pageInstance.status == 'autoSave'}">
		<div class="buttons">
		  	<span class="button"><g:actionSubmit  name="restore" action="restore" class="restore exclamation" value="${message(code: 'default.button.restore.label', default: 'Restore')}" /></span>
		</div>
	</g:if>
	
	<div class="buttons">
		<g:if test="${pageInstance.id && pageInstance.status == 'draft'}">
			<g:actionSubmit name="saveAsDraft" action="saveAsDraft" class="add" value="Spara" />
		</g:if>
		<g:else>
			<g:actionSubmit name="saveAsDraft" action="saveAsDraft" class="add" value="Spara utkast" />
		</g:else>
 	  <g:if test="${pageInstance?.status == 'published' && !pageInstance?.masterRevision}">
 	  	<span class="button"><g:actionSubmit name="update" action="update" class="save" value="Uppdatera" /></span>
 	  </g:if>
 	  <g:elseif test="${pageInstance?.masterRevision}">
 	  	<span class="button"><g:actionSubmit name="restore" action="restore" class="exclamation" value="Återställ" /></span>
 	  </g:elseif>
 	  <g:else>
 	  	<span class="button"><g:actionSubmit name="publish" action="publish" class="save" value="Publicera" /></span>
 	  </g:else>
  	</div>
  	
  	<div class="buttons">
  		<span><g:actionSubmit name="preview" action="preview" class="information" value="Förhandsgranska" id="previewSubmit"/></span>
  	</div>
  	

</div>
  
<div class="aside meta">  
  	<div class="input ${hasErrors(bean: pageInstance, field: 'metaPage', 'errors')}">
  		<g:checkBox name="metaPage" checked="${pageInstance.metaPage ? 'true' : 'false'}" value="true" />
  		<label for="metaPage"><g:message code="page.metaPage.label" default="Visa som kategori" /></label>
	</div>
		
	<div class="input ${hasErrors(bean: pageInstance, field: 'showInSitemap', 'errors')}">
		<g:checkBox name="showInSitemap" checked="${pageInstance.showInSitemap ? 'true' : 'false'}" value="true" />
  		<label for="showInSitemap"><g:message code="page.showInSitemap.label" default="Visa i webbkarta" /></label>
	</div>
	
	<div class="input ${hasErrors(bean: pageInstance, field: 'menuStyle', 'errors')}">
		<label for="menuStyle"><g:message code="page.menuStyle.label" default="Menystyling" /></label>
		<g:textField name="menuStyle" value="${fieldValue(bean: pageInstance, field:'menuStyle') }"	/>
	</div>
  	
  	<div class="input ${hasErrors(bean: pageInstance, field: 'pageOrder', 'errors')}">
		<label for="pageOrder"><g:message code="page.pageOrder.label" default="Page order: " /></label>
		<g:textField name="pageOrder" size="4" value="${fieldValue(bean: pageInstance, field: 'pageOrder')}" />
	</div>
	
	<g:if test="${Arrays.asList('frontpage', 'sitemap', 'legalSources','contact').indexOf(pageInstance.template) == -1}">
	<div class="input ${hasErrors(bean: pageInstance, field: 'template', 'errors')}">
		<label for="template"><g:message code="page.template.label" default="Page template: " /></label>
		<g:dropdown options="${grailsApplication.config.lagrummet.page.templates}" value="${fieldValue(bean: pageInstance, field: 'template')}" name="template"></g:dropdown>
	</div>
	</g:if>
	<g:else>
		<g:hiddenField name="template" value="${fieldValue(bean: pageInstance, field: 'template')}" />
	</g:else>
</div>