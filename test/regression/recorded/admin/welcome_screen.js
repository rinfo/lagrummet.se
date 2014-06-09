var x = require('casper').selectXPath;

casper.on('page.error', function(msg, trace) {
    this.echo('Error: ' + msg, 'ERROR');
    for(var i=0; i<trace.length; i++) {
        var step = trace[i];
        this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
    }
});
casper.test.begin('Välkomstsida', function(test) {
    casper.start(casper.cli.get("url"));
    casper.waitForSelector(x("//*[contains(text(), \'Välkommen till lagrummet.se\')]"),
        function success() {
            test.assertExists(x("//*[contains(text(), \'Välkommen till lagrummet.se\')]"));
        },
        function fail() {
            test.assertExists(x("//*[contains(text(), \'Välkommen till lagrummet.se\')]"));
        });

    casper.waitForSelector(x("//*[contains(text(), \'Prova vår nya sökfunktion\')]"),
        function success() {
            test.assertExists(x("//*[contains(text(), \'Prova vår nya sökfunktion\')]"));
        },
        function fail() {
            test.assertExists(x("//*[contains(text(), \'Prova vår nya sökfunktion\')]"));
        });

    casper.waitForSelector(x("//a[normalize-space(text())='Hitta orden!']"),
        function success() {
            test.assertExists(x("//a[normalize-space(text())='Hitta orden!']"));
        },
        function fail() {
            test.assertExists(x("//a[normalize-space(text())='Hitta orden!']"));
        });

    casper.waitForSelector(x("//a[normalize-space(text())='Ordlista A–Ö']"),
        function success() {
            test.assertExists(x("//a[normalize-space(text())='Ordlista A–Ö']"));
        },
        function fail() {
            test.assertExists(x("//a[normalize-space(text())='Ordlista A–Ö']"));
        });

    casper.waitForSelector(x("//img[contains(@alt, \'Förstoringsglas\')]"),
        function success() {
            test.assertExists(x("//img[contains(@alt, \'Förstoringsglas\')]"));
        },
        function fail() {
            test.assertExists(x("//img[contains(@alt, \'Förstoringsglas\')]"));
        });

    casper.waitForSelector(x("//div[@id='logo']/a[contains(text(), \'lagrummet\')]"),
        function success() {
            test.assertExists(x("//div[@id='logo']/a[contains(text(), \'lagrummet\')]"));
        },
        function fail() {
            test.assertExists(x("//div[@id='logo']/a[contains(text(), \'lagrummet\')]"));
        });

    casper.waitForSelector(x("//div[@id='logo']/a/span[@class='hlight'][contains(text(), \'.se\')]"),
        function success() {
            test.assertExists(x("//div[@id='logo']/a/span[@class='hlight'][contains(text(), \'.se\')]"));
        },
        function fail() {
            test.assertExists(x("//div[@id='logo']/a/span[@class='hlight'][contains(text(), \'.se\')]"));
        });

    casper.run(function() {test.done();});
});