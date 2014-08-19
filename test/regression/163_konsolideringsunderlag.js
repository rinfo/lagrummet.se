casper.on('page.error', function(msg, trace) {
    this.echo('Error: ' + msg, 'ERROR');
    for(var i=0; i<trace.length; i++) {
        var step = trace[i];
        this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
    }
});

captureScreen = function() {
    var file_name = casper.cli.get("output")+'SFS_konsolideringsunderlag_screen_error.png';
    this.capture(file_name);
    this.echo('Captured "'+file_name+'"');
}

casper.test.begin('Test that when displaying a konsolidering the underlag should be shown', function(test) {
    casper.start(casper.cli.get("url")+'/rinfo/publ/sfs/1999:175/konsolidering/2011-05-02');

    casper.waitForSelector("#register_konsolideringsunderlag", function(){}, captureScreen, 20000);

    casper.then(function() {
        this.test.assertEval(function() {
            return __utils__.findAll('#register_konsolideringsunderlag > ul').length >= 16;
        }, 'There should be >= 16 underlag for 1999:175 2011-05-11');
    });

    casper.run(function() {test.done();});
});