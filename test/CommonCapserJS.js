casper.on('remote.message', function(message) {
    this.echo(message);
});

casper.on('page.error', function(msg, trace) {
    this.echo('Error: ' + msg, 'ERROR');
    for(var i=0; i<trace.length; i++) {
        var step = trace[i];
        this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
    }
});

var capture_screen_counter = 0;

var captureScreen = function() {
   var file_name = casper.cli.get("output")+casper.cli.args[0]+'.screen_capture'+capture_screen_counter+".png";
   casper.capture(file_name);
   casper.echo('Captured "'+file_name+'"');
   capture_screen_counter++;
}

casper.test.on("fail", function(failure){
    captureScreen();
});

var login = function() {
        this.fill('form#loginForm', {
            'j_username':    casper.cli.get("username"),
            'j_password':    casper.cli.get("password"),
        });

    //this.sendKeys("#username", casper.cli.get("username"));
    //this.sendKeys("#password", casper.cli.get("password"));
    this.click('#submit');
}

var verifyLogin = function() {
    this.test.assertTextExists("lagrummet.se");
    this.test.assertTextExists("Sökhistorik");
    this.test.assertSelectorHasText('body > header > h1 > a','Lagrummet.se CMS');
}

var logout = function() {
    this.click('body > header > a');
}

// Will search lagrummet menu for the text in "textToFind" and return the corresponding menu item url
function findTextInNthChildMenu(textToFind) {
    //console.log('findTextInNthChildMenu('+textToFind+')');
    try {
        for (n = 1; n < 20; n++) {
            var nav = '#primaryNavigation > ul.huvudmeny1 > li:nth-child('+n+') > a';

            if (document.querySelector(nav)==null) {
                //console.log(nav+'=null');
                continue;
            }
            //console.log(nav+'='+document.querySelector(nav).innerHTML);
            if (document.querySelector(nav).innerHTML == textToFind)
                return nav;
        }
    } catch (e) {console.log("error="+e.message);}
    return "";
}

// Navigate to Alla Rättskällor from Rättsinformation menu
var goToAllaRattskallor = function() {
    this.test.assertExists(x('//ul[@class="huvudmeny1"]/*/a[text()="Alla rättskällor"]'));
    this.click(x('//ul[@class="huvudmeny1"]/*/a[text()="Alla rättskällor"]'));
}

// Navigate to Förarbeten from Rättsinformation menu
var goToForarbeten = function() {
    this.test.assertExists(x('//ul[@class="huvudmeny1"]/*/a[text()="Förarbeten"]'));
    this.click(x('//ul[@class="huvudmeny1"]/*/a[text()="Förarbeten"]'));
}

// Navigate to Myndigheters föreskrifter from Rättsinformation menu
var goToMyndighetersForeskrifter = function() {
    this.test.assertExists(x('//ul[@class="huvudmeny1"]/*/a[text()="Myndigheters föreskrifter"]'));
    this.click(x('//ul[@class="huvudmeny1"]/*/a[text()="Myndigheters föreskrifter"]'));
}
