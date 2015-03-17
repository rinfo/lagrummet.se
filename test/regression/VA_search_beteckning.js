var x = require('casper').selectXPath;

casper.test.begin('Sök på VA-beteckning', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('Sökresultat för RÅ 2010 ref. 107');

        this.sendKeys("input[name='query']", "RÅ 2010 ref. 107");
   });

   casper.waitForSelector("#searchResults > header > h1", function(){}, captureScreen, 20000);

    /* Bortmarkerar vad jag kan se ett korrekt test. Egenskaper i testet behöver verifiera mot kraven

   casper.then(function() {
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för RÅ 2010 ref. 107');
        this.test.assertSelectorHasText('#RattsfallList > li:nth-child(1) > p:nth-child(1) > a','RÅ 2010 ref. 107');
   });

   */

   casper.run(function() {test.done();});
});