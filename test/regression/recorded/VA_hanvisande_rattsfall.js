var x = require('casper').selectXPath;
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});
captureScreen = function() {
   var file_name = casper.cli.get("output")+'VA_hanvisande_rattsfall_screen_error.png';
   this.capture(file_name);
   this.echo('Captured "'+file_name+'"');
}
casper.test.begin('Verifiera att VA-hänvisande existerar', function(test) {
   casper.start(casper.cli.get("url")+'/rinfo/publ/rf/ra/2010:107');

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertSelectorHasText('#leftCol > table > tbody > tr:nth-child(1) > td:nth-child(2)','RÅ 2010 ref. 107');
        this.test.assertSelectorHasText('#rinfoSidebar > ul:nth-child(5) > li:nth-child(3)','2048-13');
        this.test.assertSelectorHasText('#leftCol > div > div > div > table > tbody > tr:nth-child(3) > td:nth-child(2) > p > span','7723-09');
        this.test.assertSelectorHasText('#leftCol > div > div > div > table > tbody > tr:nth-child(14) > td:nth-child(2) > p > span','Allmän försäkring');
   });

   casper.run(function() {test.done();});
});