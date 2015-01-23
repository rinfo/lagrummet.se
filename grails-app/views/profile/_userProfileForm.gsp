<div class="input ${hasErrors(bean: userInstance, field: 'fullName', 'errors')}">
	<label for="title"><g:message code="user.fullName.label" default="Full Name" /></label>
	<g:textField name="fullName" value="${userInstance?.fullName}" class="wideInput"/>
</div>

<div class="input ${hasErrors(bean: userInstance, field: 'email', 'errors')}">
	<label for="title"><g:message code="user.email.label" default="E-mail" /></label>
	<g:textField name="email" value="${userInstance?.email}" class="wideInput"/>
</div>

<div class="input ${hasErrors(bean: userInstance, field: 'department', 'errors')}">
	<label for="title"><g:message code="user.department.label" default="Department" /></label>
	<g:textField name="department" value="${userInstance?.department}" class="wideInput"/>
</div>
