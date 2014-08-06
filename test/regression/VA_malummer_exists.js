var x = require('casper').selectXPath;
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});
captureScreen = function() {
   var file_name = casper.cli.get("output")+'VA_malnummer_exists_screen_error.png';
   this.capture(file_name);
   this.echo('Captured "'+file_name+'"');
}

casper.test.begin('Fritext sök VA, verifiera att målnummer existerar', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('Sökresultat för brott mot tjänsteman');
        this.test.assertTextDoesntExist('B2788-02');

        this.sendKeys("input[name='query']", "brott mot tjänsteman");
   });

   casper.waitForSelector("#dynamicSearchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
           var file_name = casper.cli.get("output")+'VA_malnummer_exists_screen_error_1.png';
           this.capture(file_name);
        this.test.assertSelectorHasText('#dynamicSearchResults > header > h1','Sökresultat för brott mot tjänsteman');
        this.test.assertTextExist('B2788-02');
   });

   casper.run(function() {test.done();});
});