var x = require('casper').selectXPath;

casper.test.begin('Navigera från konsolidering till grund', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('Lag (1974:981) om arbetstagares rätt till ledighet för utbildning');

        this.sendKeys("input[name='query']", "studieledighetslagen");
   });

   casper.waitForSelector("#searchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för studieledighetslagen');
        this.test.assertSelectorHasText('#LagarList > li:nth-child(1) > p:nth-child(1) > a','Lag (1974:981) om arbetstagares rätt till ledighet för utbildning');

        this.click('#LagarList > li:nth-child(1) > p:nth-child(1) > a');
   });

   casper.waitForSelector("#rinfo", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#rinfo > h1','Lag (1974:981) om arbetstagares rätt till ledighet för utbildning');
   });

   casper.run(function() {test.done();});
});