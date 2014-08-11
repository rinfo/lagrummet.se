var x = require('casper').selectXPath;

casper.on('page.error', function(msg, trace) {
    this.echo('Error: ' + msg, 'ERROR');
    for(var i=0; i<trace.length; i++) {
        var step = trace[i];
        this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
    }
});

captureScreen = function() {
   var file_name = casper.cli.get("output")+'login.png';
   this.capture(file_name);
   this.echo('Captured "'+file_name+'"');
}

login = function() {
    this.test.assertTextExists("lagrummet.se");
    this.test.assertExists("#username");
    this.test.assertExists("#password");
    this.sendKeys("#username", casper.cli.get("username"));
    this.sendKeys("#password", casper.cli.get("password"));
    this.click('#submit');
}

casper.test.begin('Login', function(test) {
   casper.start(casper.cli.get("url")+'/admin');

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(login);

   casper.waitForSelector("#adminPages", function(){}, captureScreen, 5000);

   casper.then(function() {
           this.test.assertTextExists("lagrummet.se");
           this.test.assertTextExists("Sökhistorik");
           this.test.assertSelectorHasText('body > header > h1 > a','Lagrummet.se CMS');
   });

   casper.run(function() {test.done();});
});