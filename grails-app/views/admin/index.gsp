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
						<td>${searchQuery[1].encodeAsHTML()}</td>
						<td>${searchQuery[0]}</td>
						<td>${((searchQuery[0]/totalSearches)*100).setScale(2,BigDecimal.ROUND_HALF_EVEN)}%</td>
					</tr>
				</g:each>
			</table>
			<p>Antal dagar: 
			<g:each in="[30,90,365]" var="days">
				<g:if test="${daysOfSearches != days }"><g:link action="statistics" controller="search" params="[daysOfSearches: days, numberOfQueries: numberOfQueries]">${days}</g:link></g:if>
				<g:else>${daysOfSearches}</g:else>
			</g:each>
			. Antal sökfrågor: 
			<g:each in="[10,50,100]" var="noQueries">
				<g:if test="${numberOfQueries != noQueries }"><g:link action="statistics" controller="search" params="[numberOfQueries: noQueries, daysOfSearches: daysOfSearches]">${noQueries}</g:link></g:if>
				<g:else>${noQueries}</g:else>
			</g:each>
			.</p>
			<p><g:link action="exportStats" controller="search" params="[daysOfSearches: daysOfSearches, numberOfQueries: numberOfQueries]">Exportera all data under vald period till .csv (${daysOfSearches} dagar)</g:link>. Det kan finnas fler än 100 olika sökfrågor ställda under vald period, exportera datan för att se samtliga.</p>
		   </div>
    </body>
</html>