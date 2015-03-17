var x = require('casper').selectXPath;

casper.test.begin('Sök rättsinformationsförordning', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('Rättsinformationsförordning (1999:175)');

        this.sendKeys("input[name='query']", "rättsinformationsförordning");
   });

   casper.waitForSelector("#searchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för rättsinformationsförordning');
        this.test.assertSelectorHasText('#LagarList > li:nth-child(1) > p:nth-child(1) > a','Rättsinformationsförordning (1999:175)');
        this.click('#LagarList > li:nth-child(1) > p:nth-child(1) > a');
   });

   casper.waitForSelector("#rinfo", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#rinfo > h1','Rättsinformationsförordning (1999:175)');
   });

   casper.run(function() {test.done();});
});