/*Tabort Synonymen
  	Gör ett test i samband med att issuen för detta görs
  	-i menyn lagrummet - synoymer
  	-klicka på kryss-ikonen på raden för nysskapade synonym
  	-klicka på OK i efterföljande pop-up för att verifiera borttagningen
  	gå till lagrummet gör en sökning på synonymen
  	verifiera att det inte dyker upp en "Din sökning gav även träff på följande: XXXX"
  	*/
var x = require('casper').selectXPath;

casper.test.begin('Remove synonym', function(test) {
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

       this.test.assertExists(x('//*[@id="editSynonyms"]/*/table/tbody/*/td/input[@type="text" and @value="skilsmässa"]'));
       this.test.assertExists(x('//*[@id="editSynonyms"]/*/table/tbody/*/td/input[@type="text" and @value="äktenskapsskillnad"]'));

       this.click(x('//*[@id="editSynonyms"]/*/table/tbody/*/td/input[@type="text" and @value="skilsmässa"]/../../td/*/input[@class="deleteSynonym delete"]'));
   });

   casper.waitForText('borttagen', function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertNotExists(x('//*[@id="editSynonyms"]/*/table/tbody/*/td/input[@type="text" and @value="skilsmässa"]'));
        this.test.assertNotExists(x('//*[@id="editSynonyms"]/*/table/tbody/*/td/input[@type="text" and @value="äktenskapsskillnad"]'));
   });

   casper.then(logout);

   casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
       this.sendKeys("input[name='query']", "skilsmässa");
   });

   casper.waitForSelector("#dynamicSearchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
       this.test.assertNotExists(x('//*[@id="dynamicSearchResults"]/p/span[text()="äktenskapsskillnad"]'));
   });

   casper.run(function() {test.done();});
});
