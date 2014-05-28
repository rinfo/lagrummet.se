var x = require('casper').selectXPath;
casper.options.viewportSize = {width: 1920, height: 1128};
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});

casper.test.begin('Navigera via "Visa fler träffar"', function(test) {
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
           this.sendKeys("input[name='query']", "lag om offentlig upphandling");
       },
       function fail() {
           test.assertExists("input[name='query']");
   });
   casper.waitForSelector(x("//a[normalize-space(text())='Visa fler träffar']"),
       function success() {
           test.assertExists(x("//a[normalize-space(text())='Visa fler träffar']"));
           this.click(x("//*[@id='lagar']/*/a[normalize-space(text())='Visa fler träffar']"));
       },
       function fail() {
           test.assertExists(x("//a[normalize-space(text())='Visa fler träffar']"));
   });
   casper.waitForSelector(x("//a[normalize-space(text())='Lag (2007:1091) om offentlig upphandling']"),
       function success() {
           test.assertExists(x("//a[normalize-space(text())='Lag (2007:1091) om offentlig upphandling']"));
           this.click(x("//a[normalize-space(text())='Lag (2007:1091) om offentlig upphandling']"));
       },
       function fail() {
           test.assertExists(x("//a[normalize-space(text())='Lag (2007:1091) om offentlig upphandling']"));
   });
   casper.waitForSelector(x("//a[normalize-space(text())='Lag (2007:1091) om offentlig upphandling']"),
       function success() {
           test.assertExists(x("//a[normalize-space(text())='Lag (2007:1091) om offentlig upphandling']"));
           this.click(x("//a[normalize-space(text())='Lag (2007:1091) om offentlig upphandling']"));
       },
       function fail() {
           test.assertExists(x("//a[normalize-space(text())='Lag (2007:1091) om offentlig upphandling']"));
   });

   casper.run(function() {test.done();});
});