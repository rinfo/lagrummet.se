var x = require('casper').selectXPath;

casper.test.begin('When searching for \'studiemedel\', should find \'Förordning (2005:55)\'', function(test) {
casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('Förordning (2005:55) om elektronisk överföring av ansökningar om');

        this.sendKeys("input[name='query']", "studiemedel");
   });

   casper.waitForSelector("#searchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för studiemedel');
        this.test.assertSelectorHasText('#LagarList > li:nth-child(1) > p:nth-child(1) > a','Förordning (2005:55) om elektronisk överföring av ansökningar om');
   });

   casper.run(function() {test.done();});
});