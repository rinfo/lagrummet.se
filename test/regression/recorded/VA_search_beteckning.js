var x = require('casper').selectXPath;
casper.options.viewportSize = {width: 1920, height: 1128};
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});
casper.test.begin('Sök på VA-beteckning', function(test) {
   casper.start(casper.cli.get("url"));
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
           this.sendKeys("input[name='query']", "RÅ 2010 ref. 107");
       },
       function fail() {
           test.assertExists("input[name='query']");
   });
   casper.waitForSelector(x("//a[normalize-space(text())='RÅ 2010 ref. 107']"),
       function success() {
           test.assertExists(x("//a[normalize-space(text())='RÅ 2010 ref. 107']"));
           this.click(x("//a[normalize-space(text())='RÅ 2010 ref. 107']"));
       },
       function fail() {
           test.assertExists(x("//a[normalize-space(text())='RÅ 2010 ref. 107']"));
   });

   casper.run(function() {test.done();});
});