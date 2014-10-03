var x = require('casper').selectXPath;

casper.test.begin('Add source connected to rdl and with description', function(test) {
    phantom.cookies = '';
    casper.start(casper.cli.get("url")+'/admin?lang=sv');

    // prepare test
    casper.waitForSelector("body", function(){}, captureScreen, 5000);
    casper.then(login);
    casper.waitForSelector("#adminPages", function(){}, captureScreen, 5000);
    casper.then(verifyLogin);

    // Test starts here

    casper.then(function() {
        this.test.assertSelectorDoesntHaveText('#bodyContent > div > h1','Skapa Rättskälla');
        this.click('#adminFunctions > ul > li:nth-child(4) > ul > li:nth-child(1) > a'); // Click at 'Rättskällor -> ny rättskälla'
    });

    casper.waitForSelector("#bodyContent > div", function(){}, captureScreen, 5000);

    casper.then(function createSource() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Skapa Rättskälla');

        this.fill('form#form_create_source', {
            'url':    'http://www.abcmyndigheten.se',
            'name':    'Förarbete TEST',
            'rdlName':   'https://rinfo.boverket.se/index.atom',
            'category':       'Forarbeten',
            'subCategory':      'Regeringen',
            'description':      'Beskrivning'
        });

        this.click('#create');
    });

    casper.waitForText('Redigera Rättskälla', function(){}, captureScreen, 20000);

    casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Redigera Rättskälla');

        this.click('body > header > a');
    });

    casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

    casper.then(goToForarbeten);

    casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

    casper.then(function() {
        this.test.assertSelectorHasText('#legalSource_subCategory_Regeringen_list > li > a','Förarbete TEST');
    });

    casper.then(goToAllaRattskallor);

    casper.waitForText("Lista över rättskällorna", function(){}, captureScreen, 20000);

    casper.then(function() {
        this.test.assertSelectorHasText('#content > article > header > h1','Lista över rättskällorna');
        this.test.assertSelectorHasText('#Forarbeten_sokbar_list > li > a','Förarbete TEST');
    });

    casper.run(function() {test.done();});
});

