var x = require('casper').selectXPath;

casper.test.begin('Sök 1994', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('1994');

        this.sendKeys("input[name='query']", "1994");
   });

   casper.waitForSelector("#searchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för 1994');
        this.test.assertSelectorHasText('#LagarList > li:nth-child(1) > p.type > span','SFS 1954:142 i lydelse enligt SFS 1994:1579');
        this.click('#LagarList > li:nth-child(1) > p:nth-child(1) > a');
   });

   casper.waitForSelector("#rinfo", function(){}, captureScreen, 20000);

   casper.then(function() {
       this.test.assertSelectorHasText('#rinfo > h1','Skogskontolag (1954:142)');
   });

   casper.run(function() {test.done();});
});
