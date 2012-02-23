<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="adminMain" />
        <g:set var="entityName" value="${message(code: 'legalSource.label', default: 'LegalSource')}" />
        <title>Statistik för sökhistoria</title>
    </head>
    <body>
        <div class="body">
        	<h2>Sökhistorik</h2>
        	<p>Visar de ${numberOfQueries} vanligaste sökningar under de senaste ${daysOfSearches} dagarna.</p>
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
			<p>Antal dagar: <g:link action="statistics" params="[daysOfSearches: '30', numberOfQueries: numberOfQueries]">30</g:link>|<g:link action="statistics" params="[daysOfSearches: '90', numberOfQueries: numberOfQueries]">90</g:link>|<g:link action="statistics" params="[daysOfSearches: '365', numberOfQueries: numberOfQueries]">365</g:link>. Antal träffar: <g:link action="statistics" params="[numberOfQueries: '10', daysOfSearches: daysOfSearches]">10</g:link>|<g:link action="statistics" params="[numberOfQueries: '50', daysOfSearches: daysOfSearches]">50</g:link>|<g:link action="statistics" params="[numberOfQueries: '100', daysOfSearches: daysOfSearches]">100</g:link></p>
			<p><g:link action="exportStats" params="[daysOfSearches: daysOfSearches, numberOfQueries: numberOfQueries]">Exportera all data under vald period till .csv (${daysOfSearches} dagar)</g:link>.</p>
        </div>
    </body>
</html>