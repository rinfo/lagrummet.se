var x = require('casper').selectXPath;
var hasGaRequestBeenSent = false;
casper.on('resource.requested', function(requestData, request) {
            if (/\.lagrummet\.se%2Fsearch%3Fcat%3DAlla%26query%3D2000%/.exec(requestData.url)) {
              //this.echo('GA request for search', 'INFO');
              //this.echo(requestData.url, 'INFO');
              hasGaRequestBeenSent = true;
            }
            //block requests to GA collect
            if(requestData.url.indexOf('http://www.google-analytics.com/collect') === 0) {
                this.echo('Aborting request to GA collect', 'INFO');
                request.abort();
            }
  });
casper.test.begin('Test that when performing search there should be a request sent to GA', function(test) {
   casper.start(casper.cli.get("url"));
   casper.waitForSelector("form[name=search] input[name='query']",
       function success() {
           test.assertExists("form[name=search] input[name='query']");
           this.click("form[name=search] input[name='query']");
       },
       function fail() {
           test.assertExists("form[name=search] input[name='query']");
   });
   casper.waitForSelector("input[name='query']",
       function success() {
           this.sendKeys("input[name='query']", "2000:");
       },
       function fail() {
           test.assertExists("input[name='query']");
   });
   casper.waitForSelector(x("//a[normalize-space(text())='Tillkännagivande (2000:6) av uppgift om ...']"),
       function success() {
           test.assertExists(x("//a[normalize-space(text())='Tillkännagivande (2000:6) av uppgift om ...']"));
           this.click(x("//a[normalize-space(text())='Tillkännagivande (2000:6) av uppgift om ...']"));
         },
       function fail() {
           test.assertExists(x("//a[normalize-space(text())='Tillkännagivande (2000:6) av uppgift om ...']"));
   });

   casper.then(function() {
    test.assertEquals(hasGaRequestBeenSent, true, 'request has been sent to GA for the performed search');
   })

   casper.run(function() {test.done();});
});