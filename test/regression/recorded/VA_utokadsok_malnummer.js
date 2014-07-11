var x = require('casper').selectXPath;
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});
captureScreen = function() {
   var file_name = casper.cli.get("output")+'VA_utokadesok_malnummer_screen_error.png';
   this.capture(file_name);
   this.echo('Captured "'+file_name+'"');
}

casper.test.begin('Utökad VA-sök på målnummer', function(test) {
   casper.start(casper.cli.get("url")+'/search/ext');

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   if (casper.cli.get("target")!="regression") {

       casper.then(function() {
            this.test.assertSelectorHasText('#extendedSearch > h1','Utökad sökning');
            this.test.assertNotVisible('#Rattsfall > select');
            this.click('#catRattsfall');
       });

       casper.waitUntilVisible('#Rattsfall > select', function(){}, captureScreen, 20000);

       casper.then(function() {
            this.test.assertVisible('#Rattsfall > select');
            this.sendKeys('#malnummer', "1088-08");
            this.click('#Rattsfall > div > input');
       });

       casper.waitForSelector("#sokresultat", function(){}, captureScreen, 20000);

       casper.then(function() {
            this.test.assertExists('#searchResults > table > tbody > tr:nth-child(2) > td:nth-child(1) > p > a');
            this.test.assertSelectorHasText('#searchResults > table > tbody > tr:nth-child(2) > td:nth-child(1) > p > a','1088-08');
       });

   }

   casper.run(function() {test.done();});
});