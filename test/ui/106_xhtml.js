var x = require('casper').selectXPath;
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});
casper.test.begin('Test rendering of xhtml content with swedish characters', function(test) {
   casper.start('http://test.lagrummet.se/rinfo/publ/sfs/1999:175/konsolidering/2011-05-02');
   casper.waitForSelector("body",
       function success() {
           test.assertExists("body");
           this.click("body");
       },
       function fail() {
           test.assertExists("body");
   });
   casper.waitForSelector(x("//*[contains(text(), \'Rättsinformationsförordning (1999:175)\')]"),
       function success() {
           test.assertExists(x("//*[contains(text(), \'Rättsinformationsförordning (1999:175)\')]"));
         },
       function fail() {
           test.assertExists(x("//*[contains(text(), \'Rättsinformationsförordning (1999:175)\')]"));
   });

   casper.run(function() {test.done();});
});