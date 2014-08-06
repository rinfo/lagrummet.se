/*==============================================================================*/
/* Casper generated Wed Apr 30 2014 12:06:20 GMT+0200 (CEST) */
/*==============================================================================*/

var x = require('casper').selectXPath;
casper.on('page.error', function(msg, trace) {
    this.echo('Error: ' + msg, 'ERROR');
    for(var i=0; i<trace.length; i++) {
        var step = trace[i];
        this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
    }
});
casper.test.begin('When searching for \'Lagar\', result title should be the title of the \'Lag\'', function(test) {
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
            this.sendKeys("input[name='query']", "studiemedel");
        },
        function fail() {
            test.assertExists("input[name='query']");
        });
    casper.waitForSelector(x("//a[normalize-space(text())='Förordning (2005:55) om elektronisk överföring av ansökningar om studiemedel']"),
        function success() {
            test.assertExists(x("//a[normalize-space(text())='Förordning (2005:55) om elektronisk överföring av ansökningar om studiemedel']"));
        },
        function fail() {
            test.assertExists(x("//a[normalize-space(text())='Förordning (2005:55) om elektronisk överföring av ansökningar om studiemedel']"));
        });

    casper.run(function() {test.done();});
});