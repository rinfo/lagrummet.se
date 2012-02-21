<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'legalSource.label', default: 'LegalSource')}" />
        <title>Admin till lagrummet.se</title>
    </head>
    <body>
        <div class="body">
        	<h2>Sökhistorik</h2>
	        <table>
				<tr>
					<th>&nbsp;</th>
					<th>Sökfråga</th>
					<th>Sökningar</th>
					<th>% Sökningar</th>
				</tr>
				<g:each in="${searches}" var="searchQuery" status="index">
					<tr>
						<td>${index + 1}.</td>
						<td>${searchQuery[1]}</td>
						<td>${searchQuery[0]}</td>
						<td>${((searchQuery[0]/totalSearches)*100).setScale(2,BigDecimal.ROUND_HALF_EVEN)}%</td>
					</tr>
				</g:each>
			</table>
			Antal dagar: <g:link params="[daysOfSearches: '30']">30</g:link>|<g:link params="[daysOfSearches: '90']">90</g:link>|<g:link params="[daysOfSearches: '365']">365</g:link>. Antal träffar: <g:link params="[numberOfQueries: '10']">10</g:link>|<g:link params="[numberOfQueries: '50']">50</g:link>|<g:link params="[numberOfQueries: '100']">100</g:link>
			<br />Visar de ${numberOfQueries} vanligaste sökningar under de senaste ${daysOfSearches} dagarna.
        </div>
    </body>
</html>