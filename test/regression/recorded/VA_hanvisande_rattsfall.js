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
   casper.waitForSelector("#leftCol > table tr:nth-child(1) td:nth-child(2)",
       function success() {
           test.assertExists("#leftCol > table tr:nth-child(1) td:nth-child(2)");
           this.click("#leftCol > table tr:nth-child(1) td:nth-child(2)");
       },
       function fail() {
           test.assertExists("#leftCol > table tr:nth-child(1) td:nth-child(2)");
   });
   casper.waitForSelector(x("//*[contains(text(), \'RÅ 2010 ref. 107\')]"),
       function success() {
           test.assertExists(x("//*[contains(text(), \'RÅ 2010 ref. 107\')]"));
         },
       function fail() {
           test.assertExists(x("//*[contains(text(), \'RÅ 2010 ref. 107\')]"));
   });
   casper.run(function() {test.done();});
});