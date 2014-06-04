var x = require('casper').selectXPath;
casper.options.viewportSize = {width: 1920, height: 1128};
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});
casper.test.begin('Verifiera att VA-hänvisande existerar', function(test) {
   casper.start(casper.cli.get("url")+'/rinfo/publ/rf/ra/2010:107');
   casper.waitForSelector(x("//a[normalize-space(text())='HFD 2012 ref. 3']"),
       function success() {
           test.assertExists(x("//a[normalize-space(text())='HFD 2012 ref. 3']"));
           this.click(x("//a[normalize-space(text())='HFD 2012 ref. 3']"));
       },
       function fail() {
           test.assertExists(x("//a[normalize-space(text())='HFD 2012 ref. 3']"));
   });

   casper.run(function() {test.done();});
});