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
   //if (casper.cli.get("target")=='regression') {
   // Detta testet använder EMFS och det finns inte på alla miljöer. Behöver lösas på ett bra sätt.
   if (false) {
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
           this.sendKeys("input[name='query']", "2011:39");
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
   /* submit form */
   casper.waitForSelector(".column:nth-child(5) ul:nth-child(4) .type:nth-child(3)",
       function success() {
           test.assertExists(".column:nth-child(5) ul:nth-child(4) .type:nth-child(3)");
           this.click(".column:nth-child(5) ul:nth-child(4) .type:nth-child(3)");
       },
       function fail() {
           test.assertExists(".column:nth-child(5) ul:nth-child(4) .type:nth-child(3)");
   });
   casper.waitForSelector(x("//*[contains(text(), \'EMFS 2011:39\')]"),
       function success() {
           test.assertExists(x("//*[contains(text(), \'EMFS 2011:39\')]"));
         },
       function fail() {
           test.assertExists(x("//*[contains(text(), \'EMFS 2011:39\')]"));
   });
          /*casper.waitForSelector("form[name=search] input[name='query']",
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
       }); */
       /* submit form */
       /*casper.waitForSelector(x("//*[contains(text(), \'2011:39\')]"),
           function success() {
               test.assertExists(x("//*[contains(text(), \'2011:39\')]"));
             },
           function fail() {
               test.assertExists(x("//*[contains(text(), \'2011:39\')]"));
       });*/
   }
   casper.run(function() {test.done();});
});