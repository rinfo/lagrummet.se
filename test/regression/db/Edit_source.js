var x = require('casper').selectXPath;
/*Redigera rättskälla
  	- i menyn rättskällor -> hantera rättskällor
  	- hitta det nyss skapade förarbetet
  	- ändra beskrivning
  	- klicka spara
  	- verifiera som tidigare
*/

casper.test.begin('Edit source', function(test) {
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
       this.test.assertExists(x('//*[@id="bodyContent"]/div/div/table/tbody/tr/td/a[text()="Betänkanden"]'));
       this.click(x('//*[@id="bodyContent"]/div/div/table/tbody/tr/td/a[text()="Betänkanden"]'));
   });

   casper.waitForSelector("#bodyContent > div", function(){}, captureScreen, 5000);

   casper.then(function() {
       this.test.assertSelectorHasText('#bodyContent > div > h1','Redigera Rättskälla');

       this.fill('form#form_edit_source', {
           'description':      'UppdateradBeskrivning'
       });

       this.click('#save');
   });

   casper.waitForSelector("#bodyContent > div", function(){}, captureScreen, 5000);

    casper.then(function() {
        this.test.assertSelectorDoesntHaveText('#bodyContent > div > h1','Rättskällor');
        this.click(x('//*[@id="adminFunctions"]/ul/li/ul/li/a[text()="Hantera rättskällor"]'));
    });

    casper.waitForSelector("#bodyContent > div", function(){}, captureScreen, 5000);

    casper.then(function() {
        this.test.assertExists(x('//*[@id="bodyContent"]/div/div/table/tbody/tr/td/a[text()="Betänkanden"]/../../td[text()="UppdateradBeskrivning"]'));
    });

   casper.run(function() {test.done();});
});