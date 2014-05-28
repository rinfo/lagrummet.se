<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>500 Internt serverfel</title>
    <meta name="layout" content="main" />
</head>
<body>
<article class="editorial 500">
    <header><h1>500 Internt serverfel!</h1></header>
    <p>Oops, något gick fel, vi är hemskt ledsna.</p>

    <h2>Gå vidare</h2>
    <ul>
        <li><a href="${resource()}">Startsidan för lagrummet.se</a></li>
        <li><g:link controller="page" action="show" url="webbkarta">Webbkarta</g:link> - översikt över innehållet på lagrummet.se</li>
    </ul>

    <p>Kom du hit på en länk från vår webbplats? Kontakta <g:link contoller="page" url="kontakta-oss">redaktionen</g:link> så kan vi rätta till felet. Ange vilken länk det gäller och på vilken sida länken finns.</p>

    <p>Kom du hit på en länk från en annan webbplats? Kontakta gärna webbansvarig där och meddela att länken är trasig.</p>
</article>


<article class="editorial 500">
    <header><h1>500 internal server error</h1></header>
    <p>Something went wrong</p>
    <p>Please visit our page in English about <g:link controller="page" url="english">the Swedish legal information system</g:link>.</p>
</article>

</body>
</html>