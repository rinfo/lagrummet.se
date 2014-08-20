//TODO Complete according to spec
/*Tabort rättskälla
  	- i menyn rättskällor -> hantera rättskällor
  	- hitta det nyss skapade förarbetet
  	- klicka ta bort
  	- klicka OK i efterföljande popup-fönster
  	verifiera att det dyker upp en ruta som talar om att rättskällan är borttagen
  	verifiera borttagningen under alla rättskällor
*/
var x = require('casper').selectXPath;

casper.on('page.error', function(msg, trace) {
    this.echo('Error: ' + msg, 'ERROR');
    for(var i=0; i<trace.length; i++) {
        var step = trace[i];
        this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
    }
});

casper.on('remote.message', function(msg) {
    this.echo('remote message caught: ' + msg);
})

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



casper.test.begin('Remove source', function(test) {
   casper.start(casper.cli.get("url")+'/admin?lang=sv');

    // prepare test
    casper.waitForSelector("body", function(){}, captureScreen, 5000);
    casper.then(login);
    casper.waitForSelector("#adminPages", function(){}, captureScreen, 5000);
    casper.then(verifyLogin);

    // Test starts here
   casper.then(function() {
        this.test.assertSelectorDoesntHaveText('#bodyContent > div > h1','Rättskällor');
        this.click('#adminFunctions > ul > li:nth-child(4) > ul > li:nth-child(2) > a'); // Click at 'Rättskällor -> Hantera rättskällor'
   });

   casper.waitForSelector("#bodyContent > div", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Rättskällor');

        var clickSelector = this.evaluate(findForarbeteInList);

        this.test.assertSelectorDoesntHaveText('#bodyContent > div > h1','Redigera Rättskälla');
        if (clickSelector!="")
            this.click(clickSelector);
        else
            casper.test.fail("No click selector!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
   });

   casper.waitForText('Redigera Rättskälla', function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Redigera Rättskälla');

        this.fill('form#form_edit_source', {
            'description':    'Testbeskrivning',
        });

        this.click('#save'); //Click spara
   });

   casper.waitForText('Rättskälla Förarbete uppdaterad', function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > div','Rättskälla Förarbete uppdaterad');
        this.click('body > header > a');
   });

    casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

    casper.then(function() {
        var CSS_PATH_TO_MENU = casper.evaluate(findTextInNthChildMenu,'Förarbeten');
        if (CSS_PATH_TO_MENU=='')
            captureScreen();
        this.test.assertSelectorHasText(CSS_PATH_TO_MENU,'Förarbeten');
        this.click(CSS_PATH_TO_MENU);
    });

    casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

    casper.then(function() {
        this.test.assertSelectorHasText('#legalSource_subCategory_Regeringen_list > li:nth-child(1) > a','Förarbete');
        this.test.assertSelectorHasText('#legalSource_subCategory_Regeringen_list > li:nth-child(1) > div','Testbeskrivning');
    });

    casper.then(function() {
        var CSS_PATH_TO_ALL_MENU = casper.evaluate(findTextInNthChildMenu,'Alla rättskällor');
        if (CSS_PATH_TO_ALL_MENU=='')
            captureScreen();
        this.test.assertSelectorHasText(CSS_PATH_TO_ALL_MENU,'Alla rättskällor');
        this.click(CSS_PATH_TO_ALL_MENU);
    });

    casper.waitForText("Lista över rättskällorna", function(){}, captureScreen, 20000);

    casper.then(function() {
        this.test.assertSelectorHasText('#content > article > header > h1','Lista över rättskällorna');
        this.test.assertSelectorHasText('#Forarbeten_sokbar_list > li:nth-child(1) > a','Förarbete');
    });

   casper.run(function() {test.done();});
});

var findForarbeteInList = function() {
    for (n = 1; n <= 20; n++) {
        var testSelector = "#bodyContent > div > div.list > table > tbody > tr:nth-child("+n+") > td:nth-child(1) > a";
        if (document.querySelector(testSelector).innerHTML=="Förarbete")
            return testSelector;
    }
    return "";
}