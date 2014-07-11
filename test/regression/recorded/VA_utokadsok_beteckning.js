var x = require('casper').selectXPath;
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});
captureScreen = function() {
   var file_name = casper.cli.get("output")+'VA_utokadesok_beteckning_screen_error.png';
   this.capture(file_name);
   this.echo('Captured "'+file_name+'"');
}

casper.test.begin('Utökad VA-sök på beteckning', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

    if (casper.cli.get("target")!="regression") {

       casper.then(function() {
            this.test.assertSelectorHasText('#siteHeader > p > a','Utökad sökning');
            this.click('#siteHeader > p > a');
       });

       casper.waitForSelector("#extendedSearch > h1", function(){}, captureScreen, 20000);

       casper.then(function() {
            this.test.assertSelectorHasText('#extendedSearch > h1','Utökad sökning');
            this.test.assertNotVisible('#Rattsfall > select');
            this.click('#catRattsfall');
       });

       casper.waitUntilVisible('#Rattsfall > select', function(){}, captureScreen, 20000);

       casper.then(function() {
            this.test.assertVisible('#Rattsfall > select');
            this.sendKeys('#beteckning_rattsfall', "RÅ 2010 ref. 107");
            this.click('#Rattsfall > div > input');
       });

       casper.waitForSelector("#sokresultat", function(){}, captureScreen, 20000);

       casper.then(function() {
            this.test.assertExists('#searchResults > table > tbody > tr:nth-child(2) > td:nth-child(1) > p > a');
            this.test.assertSelectorHasText('#searchResults > table > tbody > tr:nth-child(2) > td:nth-child(1) > p > a','RÅ 2010 ref. 107');
       });
    }

   casper.run(function() {test.done();});
});