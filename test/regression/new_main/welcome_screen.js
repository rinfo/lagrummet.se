var x = require('casper').selectXPath;

casper.on('page.error', function(msg, trace) {
    this.echo('Error: ' + msg, 'ERROR');
    for(var i=0; i<trace.length; i++) {
        var step = trace[i];
        this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
    }
});

captureScreen = function() {
   var file_name = casper.cli.get("output")+'welcome_screen_error.png';
   this.capture(file_name);
   this.echo('Captured "'+file_name+'"');
}

casper.test.begin('Välkomstsida', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertTextExists("lagrummet.se");
        this.test.assertTextExists("Lagrummet.se är en gemensam webbplats för den offentliga förvaltningens rättsinformation.");
        //this.test.assertSelectorHasText('#searchCategory > label', 'Avgränsa din sökning');
        //this.test.assertSelectorHasText('#siteHeader > p > a', 'Utökad sökning');
        //this.test.assertSelectorHasText('#content > article > div > div:nth-child(3) > h3 > a','Nya lagrummet.se');

   });
   casper.run(function() {test.done();});
});