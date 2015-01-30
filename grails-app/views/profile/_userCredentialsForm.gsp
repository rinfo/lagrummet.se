
<div class="input ${hasErrors(bean: userInstance, field: 'password', 'errors')}">
	<label for="title"><g:message code="user.password.current.label" default="Nuvarande lösenord" /></label>
	<g:passwordField name="currentpassword" value="${currentpassword}" />
</div>

<div class="input">
	<label for="title"><g:message code="user.password.new.label" default="Nytt lösenord" /></label>
	<g:passwordField name="newpassword" value="${newpassword}" />
</div>

<div class="input">
	<label for="title"><g:message code="user.password.newrepeated.label" default="Repetera nytt lösenord" /></label>
	<g:passwordField name="newrepeatedpassword" value="${newrepeatedpassword}" />
</div>
