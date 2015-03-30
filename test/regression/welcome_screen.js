var x = require('casper').selectXPath;

casper.test.begin('Välkomstsida', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertTextExists("lagrummet.se");
        this.test.assertSelectorHasText('#searchCategory > label', 'Avgränsa din sökning');
        this.test.assertSelectorHasText('#siteHeader > p > a', 'Utökad sökning');

   });
   casper.run(function() {test.done();});
});