var x = require('casper').selectXPath;

casper.test.begin('Sök SFS 1990:10', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('1990:10');

        this.sendKeys("input[name='query']", "SFS 1990:10");
   });

   casper.waitForSelector("#searchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för SFS 1990:10');
        this.test.assertSelectorHasText('#LagarList > li:nth-child(1) > p:nth-child(2) > span','SFS 1990:10');
        this.click('#LagarList > li:nth-child(1) > p:nth-child(1) > a');
   });

   casper.waitForSelector("#rinfo", function(){}, captureScreen, 20000);

   casper.then(function() {
       this.test.assertSelectorHasText('#rinfo > h1','Förordning\n(1990:10)\nom fortsatt giltighet\nav förordningen (1986:419)\nom försöksverksamhet med en\nsamordnad länsförvaltning i\nNorrbottens län');
//       this.test.assertSelectorHasText('//*[@id="rinfo"]/h1/text()','Förordning (1990:10) om fortsatt giltighet av förordningen (1986:419) om försöksverksamhet med en samordnad länsförvaltning i Norrbottens län');
   });

   casper.run(function() {test.done();});
});
