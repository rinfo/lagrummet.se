<div class="input ${hasErrors(bean: legalSourceInstance, field: 'url', 'errors')}">
    <label for="url"><g:message code="legalSource.url.label" default="Url" /></label>
    <g:textField name="url" value="${legalSourceInstance?.url}" size="30" />
</div>
        
<div class="input ${hasErrors(bean: legalSourceInstance, field: 'name', 'errors')}">
    <label for="name"><g:message code="legalSource.name.label" default="Name" /></label>
    <g:textField name="name" value="${legalSourceInstance?.name}" />
</div>
        
<div class="input ${hasErrors(bean: legalSourceInstance, field: 'grouping', 'errors')}">
    <label for="grouping"><g:message code="legalSource.grouping.label" default="Grouping" /></label>
    <g:textField name="grouping" value="${legalSourceInstance?.grouping}" />
</div>
