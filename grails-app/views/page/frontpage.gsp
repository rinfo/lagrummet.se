<html>
<head>
    <title>${page.title}</title>
    <meta name="layout" content="${grailsApplication.config.lagrummet.mainLayoutName}"/>
</head>

<body>
<article class="frontpage editorial">
    <header><h1>${page.h1}</h1></header>
    ${page.content}
    <div class="frontpage-content">
        <p class="frontpage-small-header">Hit vänder du dig</p>

        <p>Behöver du hjälp med med privata angelägenheter av rättskaraktär besök gärna vår sida
        med olika länkar och viktig kontaktinformation. Här hittar du vanliga frågor och svar.
            <a href="${resource()}/lar-dig-mer/vanliga-fragor">Klicka här</a>
            <a href="${resource()}/lar-dig-mer/vanliga-fragor">
                <img src="${resource()}/images/litenlankpil.png"/>
            </a>
        </a>
        </p>
    </div>

    <div class="puffs">
        <g:render template="puff" collection="${page.puffs}" var="puff"/>
    </div>
</article>
</body>
</html>