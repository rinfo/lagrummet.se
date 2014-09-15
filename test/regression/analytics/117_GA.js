var x = require('casper').selectXPath;

var hasGaRequestBeenSent = false;
casper.on('resource.requested', function(requestData, request) {
    if (/\lagrummet\.se%2Fsearch%3Fcat%3DAlla%26query%3D2000%/.exec(requestData.url)) {
      hasGaRequestBeenSent = true;
    }
});

captureScreen = function() {
   var file_name = casper.cli.get("output")+'welcome_screen_error.png';
   this.capture(file_name);
   this.echo('Captured "'+file_name+'"');
}

casper.test.begin('Test that when performing search there should be a request sent to GA', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body");

   casper.then(function() {
        this.test.assertExists("form[name=search] input[name='query']");
        this.test.assertTextDoesntExist('Tillkännagivande (2000:6) av uppgift om ...');
        this.sendKeys("input[name='query']", "2000:6");
   });

   casper.waitForSelector("#dynamicSearchResults > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#dynamicSearchResults > header > h1','Sökresultat för 2000:6');
        this.test.assertSelectorHasText('#searchSuggestions > li:nth-child(1) > a','Tillkännagivande (2000:6) av uppgift om ...');
        this.click('#searchSuggestions > li:nth-child(1) > a');
   });

   casper.waitForSelector("#rinfo", function(){}, captureScreen, 20000);

   if (casper.cli.get("target")!='regression') {
       casper.then(function() {
            this.test.assertEquals(hasGaRequestBeenSent, true, 'request has been sent to GA for the performed search');
       })
   }

   casper.run(function() {test.done();});
});

