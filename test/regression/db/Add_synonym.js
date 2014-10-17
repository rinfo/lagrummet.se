/*Lägg till Synonym
  	-i menyn lagrummet - synoymer
  	-lägg till ny synonym t.ex. rinfo -> rättsinformationsförordning (1999:175)
  	-klicka på spara ändringar
  	gå till lagrummet gör en sökning på synonymen
  	verifiera att det dyker upp en "Din sökning gav även träff på följande: rättsinformationsförordning (1999:175)"
*/
var x = require('casper').selectXPath;

casper.test.begin('Add synonym', function(test) {
    phantom.cookies = '';
   casper.start(casper.cli.get("url")+'/admin?lang=sv');

    // prepare test
    casper.waitForSelector("body", function(){}, captureScreen, 5000);
    casper.then(login);
    casper.waitForSelector("#adminPages", function(){}, captureScreen, 5000);
    casper.then(verifyLogin);


   // Test starts here
   casper.then(function() {
       this.test.assertSelectorHasText(x('//*[@id="adminFunctions"]/ul/*/ul/*/a'), 'Hantera synonymer');
       this.click(x('//*[@id="adminFunctions"]/ul/*/ul/*/a[text()="Hantera synonymer"]')); // Click at 'Rättskällor -> Hantera rättskällor'
   });

   casper.waitForSelector("#bodyContent > div", function(){}, captureScreen, 5000);

   casper.then(function() {
       this.test.assertSelectorHasText('#bodyContent > div > h1','Synonymer');

       this.click(x('//*[@id="addSynonym"]'));

       this.sendKeys(x('//*[@name=concat("synonyms[", count(//tbody/tr)-1, "].synonym")]'), 'rinfo');
       this.sendKeys(x('//*[@name=concat("synonyms[", count(//tbody/tr)-1, "].baseTerm")]'), 'rättsinformationsförordning (1999:175)');

       this.click(x('//*[@id="submitSynonym"]'));
   });

   casper.waitForSelector("#bodyContent > div", function(){}, captureScreen, 5000);

   casper.then(function() {
       this.test.assertExists(x('//input[@type="text" and @value="rinfo"]'));
       this.test.assertExists(x('//input[@type="text" and @value="rättsinformationsförordning (1999:175)"]'));
   });

   casper.then(logout);

   casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
       this.sendKeys("input[name='query']", "rinfo");
   });

   casper.waitForSelector("#dynamicSearchResults > header > h1", function(){}, captureScreen, 20000);

    casper.then(function() {
       this.test.assertExists(x('//*[@id="dynamicSearchResults"]/p/span[text()="rättsinformationsförordning (1999:175)"]'));
    });

   casper.run(function() {test.done();});
});