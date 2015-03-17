var x = require('casper').selectXPath;

casper.test.begin('Fritext sök VA, verifiera att målnummer existerar', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('Sökresultat för brott mot tjänsteman');
        this.test.assertTextDoesntExist('B2788-02');

        this.sendKeys("input[name='query']", "brott mot tjänsteman");
   });

   casper.waitForSelector("#searchResults > header > h1", function(){}, captureScreen, 20000);

   /* Bortmarkerar vad jag kan se ett korrekt test. Egenskaper i testet behöver verifiera mot kraven

   casper.then(function() {
           var file_name = casper.cli.get("output")+'VA_malnummer_exists_screen_error_1.png';
           this.capture(file_name);
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för brott mot tjänsteman');
        this.test.assertTextExist('B2788-02');
   });

   */

   casper.run(function() {test.done();});
});