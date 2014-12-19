var x = require('casper').selectXPath;


casper.test.begin('Add source connected to rdl', function(test) {
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

    casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Skapa Rättskälla');

        this.fill('form#form_create_source', {
            'url':    'http://www.abcmyndigheten.se',
            'name':    'Överklagandenämnden ABC',
            'rdlName':   'https://rinfo.boverket.se/index.atom',
            'category':       'Rattspraxis',
            'subCategory':      'Domstolars_Vagledande_Avgoranden',
        });

        this.click('#create');
   });

   casper.waitForText('Redigera Rättskälla', function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Redigera Rättskälla');

        this.click('body > header > a');
   });

   casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

   casper.then(goToAllaRattskallor);

   casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#content > article > header > h1','Alla rättskällor');
        this.test.assertSelectorHasText('#Rattspraxis_domstolars_vagledande_avgoranden_sokbar_list > li > a','Överklagandenämnden ABC');
   });

   casper.run(function() {test.done();});
});

