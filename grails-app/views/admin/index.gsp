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
						<td>${searchQuery[1]}</td>
						<td>${searchQuery[0]}</td>
						<td>${((searchQuery[0]/totalSearches)*100).setScale(2,BigDecimal.ROUND_HALF_EVEN)}%</td>
					</tr>
				</g:each>
			</table>
			<p><g:link controller="search" action="statistics">Visa annan period eller antal resultat</g:link>.</p>
		   </div>
    </body>
</html>