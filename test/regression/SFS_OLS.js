var x = require('casper').selectXPath;

casper.test.begin('Sök efter \'offentlighets och sekretesslag\'. Skall hamna högst upp i sökningen.', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('Offentlighets- och sekretesslag (2009:400)');

        this.sendKeys("input[name='query']", "offentlighets och sekretesslag");
   });

   casper.waitForSelector("#searchResults > header > h1", function(){}, captureScreen, 20000);

    /* Bortmarkerar vad jag kan se ett korrekt test. Egenskaper i testet behöver verifiera mot kraven

   casper.then(function() {
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för offentlighets och sekretesslag');
        this.test.assertSelectorHasText('#LagarList > li:nth-child(1) > p:nth-child(1) > a','Offentlighets- och sekretesslag (2009:400)');
        this.click('#LagarList > li:nth-child(1) > p:nth-child(1) > a');
   });

   casper.waitForSelector("#rinfo", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#rinfo > h1','Offentlighets- och sekretesslag (2009:400)');
   });
   */

   casper.run(function() {test.done();});
});