/*Tabort rättskälla
  	- i menyn rättskällor -> hantera rättskällor
  	- hitta det nyss skapade förarbetet
  	- klicka ta bort
  	- klicka OK i efterföljande popup-fönster
  	verifiera att det dyker upp en ruta som talar om att rättskällan är borttagen
  	verifiera borttagningen under alla rättskällor
*/
var x = require('casper').selectXPath;

casper.test.begin('Remove source', function(test) {
    phantom.cookies = '';
    casper.start(casper.cli.get("url")+'/admin?lang=sv');

    // prepare test
    casper.waitForSelector("body", function(){}, captureScreen, 5000);
    casper.then(login);
    casper.waitForSelector("#adminPages", function(){}, captureScreen, 5000);
    casper.then(verifyLogin);

    // Test starts here
    casper.then(function() {
        this.test.assertSelectorDoesntHaveText('#bodyContent > div > h1','Rättskällor');
        this.click(x('//*[@id="adminFunctions"]/ul/li/ul/li/a[text()="Hantera rättskällor"]'));
    });

    casper.waitForSelector("#bodyContent > div", function(){}, captureScreen, 5000);

    casper.then(function() {
        this.test.assertExists(x('//*[@id="bodyContent"]/div/div/table/tbody/tr/td/a[text()="Domstolar"]'));
        this.click(x('//*[@id="bodyContent"]/div/div/table/tbody/tr/td/a[text()="Domstolar"]'));
    });

    casper.waitForSelector("#bodyContent > div", function(){}, captureScreen, 5000);

    casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Redigera Rättskälla');

        this.click(x('//*[@id="form_edit_source"]/div/span/input[@name="_action_delete"]'));
    });

    casper.waitForSelector("#bodyContent > div", function(){}, captureScreen, 5000);

    casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Rättskällor');

        this.test.assertNotExists(x('//*[@id="bodyContent"]/div/div/table/tbody/tr/td/a[text()="Domstolar"]'));
    });

    casper.then(logout);

    casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

    casper.then(goToAllaRattskallor);

    casper.waitForText("Alla rättskällor", function(){}, captureScreen, 20000);

    casper.then(function() {

        this.test.assertNotExists(x('//*[@id="Rattspraxis_sokbar_list"]/li/a[text()="Domstolar"]'));
    });

   casper.run(function() {test.done();});
});