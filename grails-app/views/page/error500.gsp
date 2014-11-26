<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>500 Internt serverfel</title>
    <meta name="layout" content="${grailsApplication.config.lagrummet.mainLayoutName}" />
</head>
<body>
<article class="editorial 500">
    <header><h1>Något gick fel på vår sida</h1></header>
    <p>Var vänlig gå tillbaka till <g:link controller="page" uri="/">startsidan</g:link> och försök igen.</p>

</article>


<article class="editorial 500">
    <header><h1>Something went wrong at our side</h1></header>
    <p>Please try again from the <g:link controller="page" url="english">homepage</g:link>.</p>
</article>
<article class="editorial 500">
    <p>Felkod 500</p>
</article>
</body>
</html>