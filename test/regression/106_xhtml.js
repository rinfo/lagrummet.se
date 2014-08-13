var x = require('casper').selectXPath;
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});

captureScreen = function() {
   var file_name = casper.cli.get("output")+'106_xhtml.png';
   this.capture(file_name);
   this.echo('Captured "'+file_name+'"');
}

casper.test.begin('Test rendering of xhtml content with swedish characters', function(test) {
   casper.start(casper.cli.get("url")+'/rinfo/publ/sfs/1999:175/konsolidering/2011-05-02');

   casper.waitForSelector("body", function(){}, captureScreen, 20000);

   casper.then(function() {
       this.test.assertTextExists("Rättsinformationsförordning (1999:175)");
   });

   casper.run(function() {test.done();});
});