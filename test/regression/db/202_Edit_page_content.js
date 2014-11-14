var x = require('casper').selectXPath;

casper.test.begin('202 Edit page content', function(test) {
    phantom.cookies = '';

    // Test starts here
    casper.start(casper.cli.get("url")+'/kontakta-oss');

    casper.waitForSelector("body", function(){}, captureScreen, 10000);

    // Verify original page content
    casper.then(function() {
        this.test.assertExists(x('//*[contains(text(),"Skriv till oss för att")]'));
    });

    casper.thenOpen(casper.cli.get("url")+'/admin?lang=sv');

    casper.waitForSelector("body", function(){}, captureScreen, 5000);
    casper.then(login);
    casper.waitForSelector("#adminPages", function(){}, captureScreen, 5000);
    casper.then(verifyLogin);

    casper.then(function() {
        this.click(x('//a[text()="Kontakta oss"]'));
    });

    casper.waitForSelector("#bodyContent", function(){}, captureScreen, 10000);

    casper.then(function() {
        this.evaluate(function() {
            tinyMCE.editors[0].setContent('New Content');
        });

        this.click(x('//*/input[@value="Uppdatera"]'));
    });

    casper.waitForSelector("#bodyContent > div > div.message", function(){}, captureScreen, 10000);

    casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > div.message', 'Ändringar i "Kontakta oss" har sparats');
    });

    casper.thenOpen(casper.cli.get("url")+'/kontakta-oss');

    casper.waitForSelector('#content', function(){}, captureScreen, 10000);

    casper.then(function() {
        this.test.assertDoesntExist(x('//*[contains(text(),"Skriv till oss för att")]'));
        this.test.assertExists(x('//*[contains(text(),"New Content")]'));
    });

    casper.run(function() {test.done();});
});
