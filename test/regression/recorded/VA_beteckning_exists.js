var x = require('casper').selectXPath;
casper.options.viewportSize = {width: 1920, height: 1128};
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});
casper.test.begin('Verifiera att VA-beteckning existerar', function(test) {
   casper.start(casper.cli.get("url"));
   casper.clear();
   phantom.clearCookies();
   casper.waitForSelector("form[name=search] input[name='query']",
       function success() {
           test.assertExists("form[name=search] input[name='query']");
           this.click("form[name=search] input[name='query']");
       },
       function fail() {
           test.assertExists("form[name=search] input[name='query']");
   });
   casper.waitForSelector("input[name='query']",
       function success() {
           this.sendKeys("input[name='query']", "NJA 2010 s. 648");
       },
       function fail() {
           test.assertExists("input[name='query']");
   });

   casper.waitForSelector(x("//*[@id='rattsfall']/*/a[normalize-space(text())='NJA 2010 s. 648']"),
       function success() {
           test.assertExists(x("//a[normalize-space(text())='NJA 2010 s. 648']"));
           this.click(x("//a[normalize-space(text())='NJA 2010 s. 648']"));
       },
       function fail() {
           test.assertExists(x("//a[normalize-space(text())='NJA 2010 s. 648']"));
   });


   casper.run(function() {test.done();});
});

