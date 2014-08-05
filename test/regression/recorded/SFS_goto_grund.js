var x = require('casper').selectXPath;

casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});

captureScreen = function() {
   var file_name = casper.cli.get("output")+'SFS_goto_ground_screen_error.png';
   this.capture(file_name);
   this.echo('Captured "'+file_name+'"');
}

casper.test.begin('Navigera från konsolidering till grund', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('Lag (1974:981) om arbetstagares rätt till ledighet för utbildning');

        this.sendKeys("input[name='query']", "studieledighetslagen");
   });

   casper.waitForSelector("#dynamicSearchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#dynamicSearchResults > header > h1','Sökresultat för studieledighetslagen');
        this.test.assertSelectorHasText('#lagar > li:nth-child(1) > p:nth-child(1) > a','Lag (1974:981) om arbetstagares rätt till ledighet för utbildning');
        this.click('#lagar > li:nth-child(1) > p:nth-child(1) > a');
   });

   casper.waitForSelector("#rinfo", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#rinfo > h1','Lag (1974:981) om arbetstagares rätt till ledighet för utbildning');
   });

   casper.run(function() {test.done();});
});