var x = require('casper').selectXPath;
casper.options.viewportSize = {width: 1920, height: 1128};
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});
casper.test.begin('Utökad VA-sök på beteckning', function(test) {
   casper.start(casper.cli.get("url"));
   casper.waitForSelector(x("//a[normalize-space(text())='Utökad sökning']"),
       function success() {
           test.assertExists(x("//a[normalize-space(text())='Utökad sökning']"));
           this.click(x("//a[normalize-space(text())='Utökad sökning']"));
       },
       function fail() {
           test.assertExists(x("//a[normalize-space(text())='Utökad sökning']"));
   });
   casper.waitForSelector("#Forfattningar #typ",
       function success() {
           test.assertExists("#Forfattningar #typ");
           this.click("#Forfattningar #typ");
       },
       function fail() {
           test.assertExists("#Forfattningar #typ");
   });
   casper.waitForSelector("input[value='Rattsfall']",
       function success() {
           test.assertExists("input[value='Rattsfall']");
           this.click("input[value='Rattsfall']");
       },
       function fail() {
           test.assertExists("input[value='Rattsfall']");
   });
   casper.waitForSelector("form[name=Rattsfall] input[name='referatrubrik']",
       function success() {
           test.assertExists("form[name=Rattsfall] input[name='referatrubrik']");
           this.click("form[name=Rattsfall] input[name='referatrubrik']");
       },
       function fail() {
           test.assertExists("form[name=Rattsfall] input[name='referatrubrik']");
   });
   casper.waitForSelector("form[name=Rattsfall] input[name='beteckning']",
       function success() {
           test.assertExists("form[name=Rattsfall] input[name='beteckning']");
           this.click("form[name=Rattsfall] input[name='beteckning']");
       },
       function fail() {
           test.assertExists("form[name=Rattsfall] input[name='beteckning']");
   });
   casper.waitForSelector("input[name='beteckning']",
       function success() {
           this.sendKeys("input[name='beteckning']", "RÅ 2010 ref. 107");
       },
       function fail() {
           test.assertExists("input[name='beteckning']");
   });
   casper.waitForSelector("form[name=Rattsfall] input[type=submit][value='Sök']",
       function success() {
           test.assertExists("form[name=Rattsfall] input[type=submit][value='Sök']");
           this.click("form[name=Rattsfall] input[type=submit][value='Sök']");
       },
       function fail() {
           test.assertExists("form[name=Rattsfall] input[type=submit][value='Sök']");
   });
   /* submit form */
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