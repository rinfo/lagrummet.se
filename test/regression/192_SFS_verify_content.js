var x = require('casper').selectXPath;

casper.on('page.error', function(msg, trace) {
    this.echo('Error: ' + msg, 'ERROR');
    for(var i=0; i<trace.length; i++) {
        var step = trace[i];
        this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
    }
});

captureScreen = function() {
    var file_name = casper.cli.get("output")+'SFS_verify_content.png';
    this.capture(file_name);
    this.echo('Captured "'+file_name+'"');
}

casper.test.begin('When displaying a SFS, asserts that no metadata is shown', function(test) {
    casper.start(casper.cli.get("url")+'/rinfo/publ/sfs/1999:175/konsolidering/2011-05-02');

    casper.waitForSelector("#register_konsolideringsunderlag", function(){}, captureScreen, 20000);

    casper.then(function() {
        var displayValue = this.evaluate(function() {
            var preambel = document.getElementsByClassName('preambel');

            if(preambel.length == 0)
            {
                return '';
            }
            else
            {
                var style = window.getComputedStyle(preambel[0], null);
                return style.getPropertyValue('display');
            }

        });

        test.assertEqual(displayValue, 'none');
    });

    casper.run(function() {test.done();});
});