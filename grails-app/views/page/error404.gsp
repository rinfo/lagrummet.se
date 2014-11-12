<html>
<head>
	<title>404 - Sidan kunde inte hittas</title>
	<meta name="layout" content="${grailsApplication.config.lagrummet.mainLayoutName}" />
</head>
<body>
    <article class="editorial 404">
      <header><h1>Sidan gick inte att hitta</h1></header>
      <p>Sidan du söker gick inte att hitta. Den kan ha flyttats eller tagits bort</p>
      
      <h2>Gå vidare</h2>      
      <ul>
        <li><a href="${resource()}">Startsidan för lagrummet.se</a></li>
        <li><g:link controller="page" action="show" url="webbkarta">Webbkarta</g:link> - översikt över innehållet på lagrummet.se</li>
      </ul>
      
      <p>Kom du hit på en länk från vår webbplats? Kontakta redaktionen så kan vi rätta till felet. Ange vilken länk det gäller och på vilken sida länken finns.</p>
      
      <p>Kom du hit på en länk från en annan webbplats? Kontakta gärna webbansvarig där och meddela att länken är trasig.</p>             
    </article>
  
  
  <article class="editorial 404">
    <header><h1>The page could not be found</h1></header>
    <p>The page might have been removed or moved to a different location.</p>
    <p>Please visit our page in English about <g:link controller="page" url="english">the Swedish legal information system</g:link>.</p>    
    <p>Felkod 404</p>
  </article>
  
    <!--
    <article class="editorial 404">
		<header><h1>404 - Sidan kunde inte hittas</h1></header>
		<p><i>Sidan som du försöker hitta verkar inte existera. Detta beror antingen på att sidan har flyttats eller tagits bort. Vi ber om ursäkt för beväret som detta har orsakat.</i></p>
		<h3>Vad kan jag göra nu?</h3>
		<p>Du kan försöka söka på nytt genom att använda sökingen ovan, använda huvudnavigeringen eller genom att gå tillbaka till <g:link controller="page">Startsidan</g:link>.</p>
		<p>Använda sidan om <g:link controller="page" action="show" url="sokhjalp">Sökhjälp</g:link> som ger dig tips om hur du på bästa sätt söker på lagrummet.se.</p>
		<p>Gå till <g:link controller="page" action="show" url="webbkarta">Webbkartan</g:link> och få överblick över alla våra ingångar.</p>
		<p>Vi skulle uppskatta om du kunde <g:link controller="page" action="show" url="kontakta-oss">meddelade oss om sidan du försökte hitta</g:link>, och hur du kom till den här sidan.</p>
    </article>
    -->
</body>
</html>