var x = require('casper').selectXPath;
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});
casper.test.begin('Test fetching content from direct url', function(test) {
   casper.start('http://regression.lagrummet.se/rinfo/publ/emfs/2011:39');
   casper.waitForSelector("body",
       function success() {
           test.assertExists("body");
           this.click("body");
       },
       function fail() {
           test.assertExists("body");
   });
   casper.waitForSelector(x("//*[contains(text(), \'Exempelmyndighetens föreskrifter\')]"),
       function success() {
           test.assertExists(x("//*[contains(text(), \'Exempelmyndighetens föreskrifter\')]"));
         },
       function fail() {
           test.assertExists(x("//*[contains(text(), \'Exempelmyndighetens föreskrifter\')]"));
   });

   casper.run(function() {test.done();});
});