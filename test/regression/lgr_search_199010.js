var x = require('casper').selectXPath;

casper.test.begin('Sök 1990:10', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('1990:10');

        this.sendKeys("input[name='query']", "1990:10");
   });

   casper.waitForSelector("#searchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för 1990:10');
        this.test.assertSelectorHasText('#LagarList > li:nth-child(1) > p.type > span','SFS 1990:1070 i lydelse enligt SFS 2008:732');
        this.click('#LagarList > li:nth-child(1) > p:nth-child(1) > a');
   });

   casper.waitForSelector("#rinfo", function(){}, captureScreen, 20000);

   casper.then(function() {
       this.test.assertSelectorHasText('#leftCol > table > tbody > tr > td:nth-child(2)','SFS 1990:1070 i lydelse enligt SFS 2008:732');
   });

   casper.run(function() {test.done();});
});
