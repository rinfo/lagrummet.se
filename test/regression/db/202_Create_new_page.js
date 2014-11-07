var x = require('casper').selectXPath;

casper.test.begin('202 Create new page', function(test) {
    phantom.cookies = '';
    casper.start(casper.cli.get("url")+'/admin?lang=sv');

    // prepare test
    casper.waitForSelector("body", function(){}, captureScreen, 5000);
    casper.then(login);
    casper.waitForSelector("#adminPages", function(){}, captureScreen, 5000);
    casper.then(verifyLogin);

    // Test starts here
    casper.then(function() {
        this.click('#adminPages > form > div > span > input');
    });

    casper.waitForSelector("#bodyContent", function(){}, captureScreen, 10000);

    casper.then(function() {
        this.sendKeys(x('//*[@id="h1" and @value="Lägg till rubrik här"]'), 'Create New Page');

        this.evaluate(function() {
            tinyMCE.editors[0].setContent('Test Content');
        });

        this.click('#bodyContent > form > div.aside.publish > div.buttons > span > input.save');
    });

    casper.waitForSelector("#bodyContent > div > div.message", function(){}, captureScreen, 10000);

    casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > div.message', '"Create New Page" har sparats');
    });

    casper.thenOpen(casper.cli.get("url")+'/create-new-page');

    casper.waitForSelector('#content', function(){}, captureScreen, 10000);

    casper.then(function() {
       this.test.assertExists(x('//*[contains(text(),"Test Content")]'));
    });

    casper.run(function() {test.done();});
});
