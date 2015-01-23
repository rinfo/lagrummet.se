
<div class="input ${hasErrors(bean: userInstance, field: 'password', 'errors')}">
	<label for="title"><g:message code="user.password.current.label" default="Nuvarande lösenord" /></label>
	<g:textField name="currentpassword" value="${currentpassword}" class="wideInput" autocomplete="off" />
</div>

<div class="input">
	<label for="title"><g:message code="user.password.new.label" default="Nytt lösenord" /></label>
	<g:textField name="newpassword" value="${newpassword}" class="wideInput" autocomplete="off" />
</div>

<div class="input">
	<label for="title"><g:message code="user.password.newrepeated.label" default="Repetera nytt lösenord" /></label>
	<g:textField name="newrepeatedpassword" value="${newrepeatedpassword}" class="wideInput" autocomplete="off" />
</div>
