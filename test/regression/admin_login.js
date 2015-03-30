var x = require('casper').selectXPath;

casper.test.begin('Login', function(test) {
   casper.start(casper.cli.get("url")+'/admin');

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(login);

   casper.waitForSelector("#adminPages", function(){}, captureScreen, 5000);

   casper.then(function() {
           this.test.assertTextExists("lagrummet.se");
         //this.test.assertTextExists("Sökhistorik");
         //this.test.assertSelectorHasText('body > header > h1 > a','Lagrummet.se CMS');
   });

   casper.run(function() {test.done();});
});
