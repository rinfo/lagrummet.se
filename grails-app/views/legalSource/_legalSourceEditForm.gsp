<div class="input ${hasErrors(bean: legalSourceInstance, field: 'url', 'errors')}">
    <label for="url"><g:message code="legalSource.url.label" default="Url" /></label>
    <g:textField name="url" value="${legalSourceInstance?.url}" size="30" />
</div>
        
<div class="input ${hasErrors(bean: legalSourceInstance, field: 'name', 'errors')}">
    <label for="name"><g:message code="legalSource.name.label" default="Name" /></label>
    <g:textField name="name" value="${legalSourceInstance?.name}" />
</div>
        
<div class="input ${hasErrors(bean: legalSourceInstance, field: 'category', 'errors')}">
    <label for="category"><g:message code="legalSource.category.label" default="Category" /></label>
    <g:select name="category" 
    			from="${grailsApplication.config.lagrummet.legalSource.categories}"
    			value="${legalSourceInstance?.category}"
    			valueMessagePrefix="legalSource.category" />
</div>

<div class="input ${hasErrors(bean: legalSourceInstance, field: 'subCategory', 'errors')}">
    <label for="category"><g:message code="legalSource.subCategory.label" default="Sub Category" /></label>
    <g:select name="subCategory" 
    			from="${grailsApplication.config.lagrummet.legalSource.subCategories}"
    			value="${legalSourceInstance?.subCategory}"
    			valueMessagePrefix="legalSource.subCategory"
    			noSelection="${['':'']}" />
</div>

<div class="input ${hasErrors(bean: legalSourceInstance, field: 'description', 'errors')}">
	<label for="description"><g:message code="legalSource.description.label" default="Description" /></label>
	<g:textArea name="description" value="${legalSourceInstance?.description}"></g:textArea>
</div>
