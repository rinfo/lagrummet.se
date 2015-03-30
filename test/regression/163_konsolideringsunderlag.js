var x = require('casper').selectXPath;

casper.test.begin('Testa att när en konsolidering visas skall även underlag visas.', function(test) {
    casper.start(casper.cli.get("url")+'/rinfo/publ/sfs/1999:175/konsolidering/2011-05-02');

    casper.waitForSelector("#register_konsolideringsunderlag", function(){}, captureScreen, 20000);

    casper.then(function() {
        this.test.assertEval(function() {
            return __utils__.findAll('#register_konsolideringsunderlag > ul').length === 0;
        }, 'Konsolideringsunderlag should not be displayed');
    });

    casper.run(function() {test.done();});
});