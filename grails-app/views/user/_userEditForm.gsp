<div class="input ${hasErrors(bean: userInstance, field: 'fullName', 'errors')}">
	<label for="title"><g:message code="user.fullName.label" default="Full Name" /></label>
	<g:textField name="fullName" value="${userInstance?.fullName}" />
</div>

<div class="input ${hasErrors(bean: userInstance, field: 'email', 'errors')}">
	<label for="title"><g:message code="user.email.label" default="E-mail" /></label>
	<g:textField name="email" value="${userInstance?.email}" />
</div>

<div class="input ${hasErrors(bean: userInstance, field: 'username', 'errors')}">
	<label for="title"><g:message code="user.username.label" default="Username" /></label>
	<g:textField name="username" value="${userInstance?.username}" />
</div>

<div class="input ${hasErrors(bean: userInstance, field: 'password', 'errors')}">
	<label for="title"><g:message code="user.password.label" default="Password" /></label>
	<g:textField name="password" value="${userInstance?.password}" />
</div>

<div class="input ${hasErrors(bean: userInstance, field: 'department', 'errors')}">
	<label for="title"><g:message code="user.department.label" default="Department" /></label>
	<g:textField name="department" value="${userInstance?.department}" />
</div>

<div class="input ${hasErrors(bean: userInstance, field: 'enabled', 'errors')}">
	<label for="title"><g:message code="user.enabled.label" default="Enabled" /></label>
	<g:checkBox name="enabled" value="${userInstance?.enabled}" />
</div>