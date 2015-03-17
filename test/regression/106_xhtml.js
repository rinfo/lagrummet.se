var x = require('casper').selectXPath;

casper.test.begin('Test rendering of xhtml content with swedish characters', function(test) {
   casper.start(casper.cli.get("url")+'/rinfo/publ/sfs/1999:175/konsolidering/2011-05-02');

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
       this.test.assertSelectorHasText("#rinfo > h1","Rättsinformationsförordning (1999:175)");
   });


   casper.run(function() {test.done();});
});