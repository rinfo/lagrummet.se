var x = require('casper').selectXPath;
casper.options.viewportSize = {width: 1920, height: 1128};
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});

captureScreen = function() {
   var file_name = casper.cli.get("output")+'SFS_show_more_results_screen_error.png';
   this.capture(file_name);
   this.echo('Captured "'+file_name+'"');
}

casper.test.begin('Navigera via "Visa fler träffar"', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('Lag (2007:1091) om offentlig upphandling');

        this.sendKeys("input[name='query']", "lag om offentlig upphandling");
   });

   casper.waitForSelector("#dynamicSearchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#dynamicSearchResults > header > h1','Sökresultat för lag om offentlig upphandling');
        this.test.assertSelectorHasText('#rattsfall > li.showAll > a','Visa fler träffar');
        this.test.assertSelectorHasText('#c-2 > ul > li.showAll > a','Visa fler träffar');

        this.click('#c-2 > ul > li.showAll > a');
   });

   casper.waitForSelector("#searchResults > p:nth-child(4) > span");

   casper.then(function() {
        this.test.assertSelectorHasText('#searchResults > header > h1','Sökresultat för lag om offentlig upphandling');
        this.test.assertTextExist('Lag (2007:1091) om offentlig upphandling');
   });
   casper.run(function() {test.done();});
});