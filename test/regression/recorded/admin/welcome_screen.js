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

   casper.waitForSelector("body");

   casper.then(function() {
        switch (casper.cli.get("target")) {
            case 'beta':
                this.test.assertTitle('Beta lagrummet.se - startsida');
                this.test.assertSelectorHasText('#content > article > header > h1', 'Välkommen till beta.lagrummet.se!');
                break;
            case 'demo':
                this.test.assertTitle('Beta lagrummet.se - startsida');
                this.test.assertSelectorHasText('#content > article > header > h1', 'Välkommen till lagrummet.se  BETA!');
                break;
            case 'test':
                this.test.assertTitle('Lagrummet.se - startsida');
                this.test.assertSelectorHasText('#content > article > header > h1', 'Välkommen till lagrummet.se   TESTVERSION!');
                break;
            case 'regression':
                this.test.assertTitle('Lagrummet.se - startsida');
                this.test.assertSelectorHasText('#content > article > header > h1', 'Välkommen till lagrummet.se   TESTVERSION!');
                break;
            default:
                this.test.assertTitle('Lagrummet.se - startsida');
                this.test.assertSelectorHasText('#content > article > header > h1', 'Välkommen till lagrummet.se  BETA!');
        }
        this.test.assertSelectorHasText('#searchCategory > label', 'Avgränsa din sökning');
        this.test.assertSelectorHasText('#siteHeader > p > a', 'Utökad sökning');
        this.test.assertSelectorHasText('#content > article > div > div:nth-child(3) > h3 > a','Nya lagrummet.se');

   });
   casper.run(function() {test.done();});
});