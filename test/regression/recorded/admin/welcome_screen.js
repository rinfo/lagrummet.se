var x = require('casper').selectXPath;

casper.on('page.error', function(msg, trace) {
    this.echo('Error: ' + msg, 'ERROR');
    for(var i=0; i<trace.length; i++) {
        var step = trace[i];
        this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
    }
});

casper.test.begin('Välkomstsida', function suite(test) {
    casper.start(casper.cli.get("url"), function() {
        test.assertExists(x("//*[contains(text(), \'Välkommen till lagrummet.se\')]"));
        test.assertExists(x("//*[contains(text(), \'Prova vår nya sökfunktion\')]"));
        test.assertExists(x("//a[normalize-space(text())='Hitta orden!']"));
        test.assertExists(x("//a[normalize-space(text())='Ordlista A–Ö']"));
        test.assertExists(x("//img[contains(@alt, \'Förstoringsglas\')]"));
        test.assertExists(x("//div[@id='logo']/a[contains(text(), \'lagrummet\')]"));
        test.assertExists(x("//div[@id='logo']/a/span[@class='hlight'][contains(text(), \'.se\')]"));
    });

    casper.run(function() {test.done();});
});