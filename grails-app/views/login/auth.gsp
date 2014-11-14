<html>
<head>
	<meta name='layout' content='${grailsApplication.config.lagrummet.mainLayoutName}'/>
	<title><g:message code="springSecurity.login.title"/></title>
</head>

<body>
	<article>
		<h1><g:message code="springSecurity.login.header"/></h1>

		<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
		</g:if>

		<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
			<div class="input">
				<label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
				<input type='text' class='text_' name='j_username' id='username'/>
			</div>

			<div class="input">
				<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
				<input type='password' class='text_' name='j_password' id='password'/>
			</div>

			<div id="remember_me_holder" class="input">
				<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
				<label for='remember_me'><g:message code="springSecurity.login.remember.me.label"/></label>
			</div>

			<div class="input">
				<input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'/>
			</div>
		</form>
	</article>
<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
</script>
</body>
</html>
