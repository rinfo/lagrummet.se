<html>
<head>
	<title>${page.title}</title>
	<meta name="layout" content="${grailsApplication.config.lagrummet.mainLayoutName}" />
</head>
<body>
    <article class="editorial">
		<header><h1>${page.h1}</h1></header>
		${page.content}

		<g:if test="${flash.message}">
			<div class="message">${flash.message}</div>
		</g:if>
		<g:form name="contact" mapping="contact" method="post">
			<fieldset id="contactInfo">
				<legend for="name">Mitt namn <span class="required">*</span></legend>
				<input type="text" name="name" required />
				<legend for="epost">Min e-postaddress</legend>
				<input type="text" name="epost" />
				<legend for="arende">Mitt ärende gäller</legend>
				<select name="arende">
					<option value="Frågor om lagrummet.se">Frågor om lagrummet.se</option>
					<option value="Synpunkter på lagrummet.se">Synpunkter på lagrummet.se</option>
					<option value="Anmäl fel eller tekniska problem">Anmäl fel eller tekniska problem</option>
				</select>
			</fieldset>
			<fieldset id="message">
				<legend for="meddelande">Mitt meddelande <span class="required">*</span></legend>
				<textarea name="meddelande" required></textarea>
			</fieldset>
				<div class="buttons">
					<button type="submit">Skicka</button>
				</div>
		</g:form>
	</article>
	<aside class="puffs" id="puffs">
		<g:render template="puff" collection="${page.puffs}" var="puff" />
	</aside>
</body>
</html>