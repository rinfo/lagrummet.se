//TODO Complete according to spec
/*Redigera rättskälla
  	- i menyn rättskällor -> hantera rättskällor
  	- hitta det nyss skapade förarbetet
  	- ändra beskrivning
  	- klicka spara
  	- verifiera som tidigare
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



casper.test.begin('Login', function(test) {
   casper.start(casper.cli.get("url")+'/admin?lang=sv');

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(login);

   casper.waitForSelector("#adminPages", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertTextExists("lagrummet.se");
        this.test.assertTextExists("Sökhistorik");
        this.test.assertSelectorHasText('body > header > h1 > a','Lagrummet.se CMS');
   });

    // Test starts here

   casper.then(function() {
        this.test.assertSelectorDoesntHaveText('#bodyContent > div > h1','Rättskällor');
        this.click('#adminFunctions > ul > li:nth-child(4) > ul > li:nth-child(2) > a'); // Click at 'Rättskällor -> Hantera rättskällor'
   });

   casper.waitForSelector("#bodyContent > div", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Rättskällor');

        var clickSelector = this.evaluate(function() {
            for (n = 1; n <= 20; n++) {
                var testSelector = "#bodyContent > div > div.list > table > tbody > tr:nth-child("+n+") > td:nth-child(1) > a";
                //console.log(testSelector+"="+document.querySelector(testSelector).innerHTML)
                if (document.querySelector(testSelector).innerHTML=="Förarbete") {
                    //console.log("Success!!!!!!");
                    return testSelector;
                }
            }
            //console.log("Failed!!!!!!");
            return "";
        });

        this.test.assertSelectorDoesntHaveText('#bodyContent > div > h1','Redigera Rättskälla');
        if (clickSelector!="")
            this.click(clickSelector);
        else
            casper.test.fail("No click selector!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
   });

   casper.waitForText('Redigera Rättskälla', function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Redigera Rättskälla');

        //todo make this work
        //this.test.assertSelectorHasText("#name[value]",'Förarbete');
        //this.test.assertSelectorHasText("#url[value]",'http://www.abcmyndigheten.se');
        //this.test.assertSelectorHasText("#rdlName[value]",'https://rinfo.boverket.se/index.atom');
        //todo verify Rubrik och Underrubrik
        //this.test.assertSelectorHasText('#description','');

        this.sendKeys("#description", 'Testbeskrivning');

        this.click('#bodyContent > div > form > div.buttons > span:nth-child(1) > input'); //Click spara
   });

   casper.waitUntilVisible('#bodyContent > div > div', function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > div','Rättskälla Förarbete uppdaterad');
        this.click('body > header > a');
   });

   casper.waitForSelector("#logo > a", function(){}, captureScreen, 20000);

   var CSS_PATH_TO_MENU = casper.cli.get("target")=='local'?'#primaryNavigation > ul.huvudmeny1 > li:nth-child(3) > a':'#primaryNavigation > ul.huvudmeny1 > li:nth-child(4) > a';
   var CSS_PATH_TO_ITEM = casper.cli.get("target")=='local'?'#content > article > ul:nth-child(3) > li:nth-child(1) > a':'#content > article > ul:nth-child(5) > li:nth-child(1) > a';

   casper.then(function() {
        this.test.assertSelectorHasText(CSS_PATH_TO_MENU,'Förarbeten');
        this.click(CSS_PATH_TO_MENU);
   });

   casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText(CSS_PATH_TO_MENU,'Förarbeten');
        this.test.assertSelectorHasText(CSS_PATH_TO_ITEM,'Förarbete');
   });

   var CSS_PATH_TO_ALL_MENU = casper.cli.get("target")=='local'?'#primaryNavigation > ul.huvudmeny1 > li:nth-child(2) > a':'#primaryNavigation > ul.huvudmeny1 > li:nth-child(7) > a';
   var CSS_PATH_TO_ALL_ITEM = casper.cli.get("target")=='local'?'#content > article > div > section.Forarbeten > ul:nth-child(3) > li:nth-child(1) > a':'#content > article > div > section.Forarbeten > ul:nth-child(4) > li > a';

   casper.then(function() {
        this.test.assertSelectorHasText(CSS_PATH_TO_ALL_MENU,'Alla rättskällor');
        this.click(CSS_PATH_TO_ALL_MENU);
   });

   casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#content > article > header > h1',casper.cli.get("target")=='local'?'Samtliga rättskällor':'Lista över rättskällorna');
        this.test.assertSelectorHasText(CSS_PATH_TO_ALL_ITEM,'Förarbete');
   });

   casper.run(function() {test.done();});
});