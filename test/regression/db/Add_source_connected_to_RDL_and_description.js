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
            'name':    'Förarbete',
            'rdlName':   'https://rinfo.boverket.se/index.atom',
            'category':       'Forarbeten',
            'subCategory':      'Regeringen',
        });

        this.click('#create');
    });

    casper.waitForText('Redigera Rättskälla', function(){}, captureScreen, 20000);

    casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Redigera Rättskälla');

        this.click('body > header > a');
    });

    casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

    casper.then(function() {
        var CSS_PATH_TO_MENU = casper.evaluate(findTextInNthChildMenu,'Förarbeten');
        if (CSS_PATH_TO_MENU=='')
            this.wait(1,captureScreen);
        this.test.assertSelectorHasText(CSS_PATH_TO_MENU,'Förarbeten');
        this.click(CSS_PATH_TO_MENU);
    });

    casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

    casper.then(function() {
        this.test.assertSelectorHasText('#legalSource_subCategory_Regeringen_list > li:nth-child(1) > a','Förarbete');
    });

    casper.then(function() {
        var CSS_PATH_TO_ALL_MENU = casper.evaluate(findTextInNthChildMenu,'Alla rättskällor');
        if (CSS_PATH_TO_ALL_MENU=='')
            this.wait(1,captureScreen);
        this.test.assertSelectorHasText(CSS_PATH_TO_ALL_MENU,'Alla rättskällor');
        this.click(CSS_PATH_TO_ALL_MENU);
    });

    casper.waitForText("Lista över rättskällorna", function(){}, captureScreen, 20000);

    casper.then(function() {
        this.test.assertSelectorHasText('#content > article > header > h1','Lista över rättskällorna');
        this.test.assertSelectorHasText('#Forarbeten_sokbar_list > li:nth-child(1) > a','Förarbete');
    });

    casper.run(function() {test.done();});
});

