var x = require('casper').selectXPath;
casper.options.viewportSize = {width: 1855, height: 968};
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});
casper.test.begin('Test search of 2011', function(test) {
   casper.start(casper.cli.get("url"));
   /* Disabled test
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
           this.sendKeys("input[name='query']", "2011\r");
       },
       function fail() {
           test.assertExists("input[name='query']");
   });
   casper.waitForSelector("form[name=search] input[type=submit][value='Sök']",
       function success() {
           test.assertExists("form[name=search] input[type=submit][value='Sök']");
           this.click("form[name=search] input[type=submit][value='Sök']");
       },
       function fail() {
           test.assertExists("form[name=search] input[type=submit][value='Sök']");
   });
   */
   /* submit form */
   /* Disabled test
   casper.waitForSelector(x("//*[contains(text(), \'2011:39\')]"),
       function success() {
           test.assertExists(x("//*[contains(text(), \'2011:39\')]"));
         },
       function fail() {
           test.assertExists(x("//*[contains(text(), \'2011:39\')]"));
   });
   */
   casper.run(function() {test.done();});
});