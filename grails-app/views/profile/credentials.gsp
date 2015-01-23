<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'LegalSource')}" />
        <title>head och sedan title</title>
    </head>
    <body>
        <div class="body">
            <h1><g:message code="default.password.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${userInstance}">
            <div class="errors">
                <g:renderErrors bean="${userInstance}" as="list" />
            </div>
            <br/>
            </g:hasErrors>
            <g:form action="updateCredentials" useToken="true">
                <g:render template="userCredentialsForm" />
                
                <div class="buttons">
                    <span class="button"><g:submitButton name="update" class="updateCredentials" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                </div>
            </g:form>
	    </div>
    </body>
</html>
