//TODO Complete according to spec
/*Redigera rättskälla
  	- i menyn rättskällor -> hantera rättskällor
  	- hitta det nyss skapade förarbetet
  	- ändra beskrivning
  	- klicka spara
  	- verifiera som tidigare
*/
var x = require('casper').selectXPath;

casper.test.begin('Edit source', function(test) {
   casper.start(casper.cli.get("url")+'/admin?lang=sv');

    // prepare test
    casper.waitForSelector("body", function(){}, captureScreen, 5000);
    //casper.then(login);
    //casper.waitForSelector("#adminPages", function(){}, captureScreen, 5000);
    //casper.then(verifyLogin);

    // Test starts here
   /*casper.then(function() {
        this.test.assertSelectorDoesntHaveText('#bodyContent > div > h1','Rättskällor');
        //this.click('#adminFunctions > ul > li:nth-child(4) > ul > li:nth-child(2) > a'); // Click at 'Rättskällor -> Hantera rättskällor'
   });

   var MY_URL = casper.cli.get("url")+'/admin/legalSource/list?offset=0&max=1000&sort=name&lang=sv';

   casper.thenOpen(MY_URL, function(){}, captureScreen, 5000);

   casper.waitForText('Rättskällor', function(){}, captureScreen, 10000);

   casper.then(function() {
        //console.log(MY_URL);
        this.test.assertSelectorHasText('#bodyContent > div > h1','Rättskällor');
        this.test.assertSelectorDoesntHaveText('#bodyContent > div > h1','Redigera Rättskälla');
        this.test.assertDoesntExist('#bodyContent > div > div.paginateButtons > a.nextLink');

        var clickSelector = this.evaluate(findForarbeteInList);;

        if (clickSelector=="") {
            casper.test.fail("No click selector!");
            return;
        }

        this.click(clickSelector);
   });


   casper.waitForText('Redigera Rättskälla', function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Redigera Rättskälla');

        this.fill('form#form_edit_source', {
            'description':    'Testbeskrivning',
        });

        this.click('#save'); //Click spara
   });

   casper.waitForText('Rättskälla Förarbete uppdaterad', function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > div','Rättskälla Förarbete uppdaterad');
        this.click('body > header > a');
   });*/

/*
    casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 5000);

    casper.then(function() {
        var CSS_PATH_TO_MENU = casper.evaluate(findTextInNthChildMenu,'Förarbeten');
        if (CSS_PATH_TO_MENU=='')
            this.wait(1,captureScreen);
        this.test.assertSelectorHasText(CSS_PATH_TO_MENU,'Förarbeten');
        this.click(CSS_PATH_TO_MENU);
    });

    casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 5000);

    casper.then(function() {
        this.test.assertSelectorHasText('#legalSource_subCategory_Regeringen_list > li:nth-child(1) > a','Förarbete');
        this.test.assertSelectorHasText('#legalSource_subCategory_Regeringen_list > li:nth-child(1) > div','Testbeskrivning');
    });

    casper.then(function() {
        var CSS_PATH_TO_ALL_MENU = casper.evaluate(findTextInNthChildMenu,'Alla rättskällor');
        if (CSS_PATH_TO_ALL_MENU=='')
            this.wait(1,captureScreen);
        this.test.assertSelectorHasText(CSS_PATH_TO_ALL_MENU,'Alla rättskällor');
        this.click(CSS_PATH_TO_ALL_MENU);
    });

    casper.waitForText("Lista över rättskällorna", function(){}, captureScreen, 5000);

    casper.then(function() {
        this.test.assertSelectorHasText('#content > article > header > h1','Lista över rättskällorna');
        this.test.assertSelectorHasText('#Forarbeten_sokbar_list > li:nth-child(1) > a','Förarbete');
    });
*/

   casper.run(function() {test.done();});
});

var findForarbeteInList = function() {
    //console.log("findForarbeteInList()");
    for (n = 1; n <= 1000; n++) {
        var testSelector = "#bodyContent > div > div.list > table > tbody > tr:nth-child("+n+") > td:nth-child(1) > a";
        //if (document.querySelector(testSelector)!=null)
            //console.log(testSelector+'="'+document.querySelector(testSelector).innerHTML+'"');
        if (document.querySelector(testSelector)!=null && document.querySelector(testSelector).innerHTML.trim()=="Förarbete") {
            //console.log("Found "+testSelector);
            return testSelector;
        }
    }
    if (document.querySelector('#bodyContent > div > div.paginateButtons > a.nextLink')!=null)
        return "next";
    return "";
}