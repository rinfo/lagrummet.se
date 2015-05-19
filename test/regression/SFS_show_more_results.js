var x = require('casper').selectXPath;

casper.test.begin('Navigera via "Visa fler träffar"', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('Lag (2007:1091) om offentlig upphandling');

        this.sendKeys("input[name='query']", "2007:1091");
   });

   casper.waitForSelector("#searchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för 2007:1091');
        this.test.assertSelectorHasText('#RattsfallList > li.showAll > a','Visa fler träffar');
        this.test.assertSelectorHasText('#LagarList > li.showAll > a','Visa fler träffar');

        this.click('#LagarList > li.showAll > a');
   });

   casper.waitForSelector("#searchResults > p:nth-child(4) > span");

   casper.then(function() {
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för 2007:1091');
        //todo fix test. This was removed becase missing data in new RK collects
        //this.test.assertTextExist('Lag (2007:1091) om offentlig upphandling');
   });
   casper.run(function() {test.done();});
});