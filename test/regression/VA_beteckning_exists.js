var x = require('casper').selectXPath;
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});

captureScreen = function() {
   var file_name = casper.cli.get("output")+'VA_beteckning_exists_screen_error.png';
   this.capture(file_name);
   this.echo('Captured "'+file_name+'"');
}

casper.test.begin('Verifiera att VA-beteckning existerar', function(test) {
   casper.start(casper.cli.get("url"));
   casper.clear();
   phantom.clearCookies();

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('NJA 2010 s. 648');

        this.sendKeys("input[name='query']", "NJA 2010 s. 648");
   });

   casper.waitForSelector("#searchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för NJA 2010 s. 648');
        this.test.assertSelectorHasText('#searchSuggestions > li:nth-child(1) > a','NJA 2010 s. 648');
        this.test.assertSelectorHasText('#RattsfallList > li:nth-child(1) > p:nth-child(1) > a','NJA 2010 s. 648');
   });

   casper.run(function() {test.done();});
});

