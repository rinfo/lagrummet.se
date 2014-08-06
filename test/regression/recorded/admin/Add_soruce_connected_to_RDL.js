var x = require('casper').selectXPath;

casper.on('page.error', function(msg, trace) {
    this.echo('Error: ' + msg, 'ERROR');
    for(var i=0; i<trace.length; i++) {
        var step = trace[i];
        this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
    }
});

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
   casper.start(casper.cli.get("url")+'/admin');

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
        this.test.assertSelectorDoesntHaveText('#bodyContent > div > h1','Skapa Rättskälla');
        this.click('#adminFunctions > ul > li:nth-child(4) > ul > li:nth-child(1) > a'); // Click at 'Rättskällor -> ny rättskälla'
   });

   casper.waitForSelector("#bodyContent > div", function(){}, captureScreen, 5000);

   casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Skapa Rättskälla');

        this.sendKeys("#url", 'http://www.abcmyndigheten.se');
        this.sendKeys("#name", 'Överklagandenämnden ABC');
        this.sendKeys("#rdlName", 'https://rinfo.boverket.se/index.atom');
        this.evaluate(function() {
            document.querySelector("#category").value = "Rattspaxis";
            document.querySelector("#subCategory").value = "Domstolars_Vagledande_Avgoranden";
            return true;
        });

        this.click('#create');
   });

   casper.waitUntilVisible('#bodyContent > div > div', function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#bodyContent > div > h1','Redigera Rättskälla');

        this.click('body > header > a');
   });

   casper.waitForSelector("#logo > a", function(){}, captureScreen, 20000);

   var CSS_PATH_TO_ALL_MENU = casper.cli.get("target")=='local'?'#primaryNavigation > ul.huvudmeny1 > li:nth-child(2) > a':'#primaryNavigation > ul.huvudmeny1 > li:nth-child(7) > a';
   var CSS_PATH_TO_ALL_ITEM = casper.cli.get("target")=='local'?'#content > article > div > section.Foreskrifter > ul:nth-child(5) > li > a':'#content > article > div > section.Rattspraxis > ul:nth-child(6) > li > a';

   casper.then(function() {
        this.test.assertSelectorHasText(CSS_PATH_TO_ALL_MENU,'Alla rättskällor');
        this.click(CSS_PATH_TO_ALL_MENU);
   });

   casper.waitForSelector("#content > article > header > h1", function(){}, captureScreen, 20000);

   casper.then(function() {
        this.test.assertSelectorHasText('#content > article > header > h1',casper.cli.get("target")=='local'?'Samtliga rättskällor':'Lista över rättskällorna');
        this.test.assertSelectorHasText(CSS_PATH_TO_ALL_ITEM,'Överklagandenämnden ABC');
   });

   casper.run(function() {test.done();});
});