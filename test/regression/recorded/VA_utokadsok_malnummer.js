var x = require('casper').selectXPath;
casper.options.viewportSize = {width: 1920, height: 1128};
casper.on('page.error', function(msg, trace) {
   this.echo('Error: ' + msg, 'ERROR');
   for(var i=0; i<trace.length; i++) {
       var step = trace[i];
       this.echo('   ' + step.file + ' (line ' + step.line + ')', 'ERROR');
   }
});
casper.test.begin('Utökad VA-sök på målnummer', function(test) {
   casper.start(casper.cli.get("url")+'/search/ext');
   casper.waitForSelector("input[value='Rattsfall']",
       function success() {
           test.assertExists("input[value='Rattsfall']");
           this.click("input[value='Rattsfall']");
       },
       function fail() {
           test.assertExists("input[value='Rattsfall']");
   });
   casper.waitForSelector("form[name=Rattsfall] input[name='referatrubrik']",
       function success() {
           test.assertExists("form[name=Rattsfall] input[name='referatrubrik']");
           this.click("form[name=Rattsfall] input[name='referatrubrik']");
       },
       function fail() {
           test.assertExists("form[name=Rattsfall] input[name='referatrubrik']");
   });
   casper.waitForSelector("form[name=Rattsfall] input[name='beteckning']",
       function success() {
           test.assertExists("form[name=Rattsfall] input[name='beteckning']");
           this.click("form[name=Rattsfall] input[name='beteckning']");
       },
       function fail() {
           test.assertExists("form[name=Rattsfall] input[name='beteckning']");
   });
   casper.waitForSelector("form[name=Rattsfall] input[name='malnummer']",
       function success() {
           test.assertExists("form[name=Rattsfall] input[name='malnummer']");
           this.click("form[name=Rattsfall] input[name='malnummer']");
       },
       function fail() {
           test.assertExists("form[name=Rattsfall] input[name='malnummer']");
   });
   casper.waitForSelector("input[name='malnummer']",
       function success() {
           this.sendKeys("input[name='malnummer']", "1088-08\r");
       },
       function fail() {
           test.assertExists("input[name='malnummer']");
   });
   casper.waitForSelector("form[name=Rattsfall] input[type=submit][value='Sök']",
       function success() {
           test.assertExists("form[name=Rattsfall] input[type=submit][value='Sök']");
           this.click("form[name=Rattsfall] input[type=submit][value='Sök']");
       },
       function fail() {
           test.assertExists("form[name=Rattsfall] input[type=submit][value='Sök']");
   });

   /* submit form */
   casper.waitForSelector(x("//a[normalize-space(text())='1088-08']"),
       function success() {
           test.assertExists(x("//a[normalize-space(text())='1088-08']"));
           this.click(x("//a[normalize-space(text())='1088-08']"));
       },
       function fail() {
           test.assertExists(x("//a[normalize-space(text())='1088-08']"));
   });
   casper.waitForSelector(x("//a[normalize-space(text())='Enligt 18 kap. 18 § inkomstskattelagen får det ytterligare avdrag för värdeminskning på inventarier göras som behövs för att inventariernas skattemässiga värde inte ska överstiga deras verkliga värde. Prövningen ska avse samtliga inventarier på vilka bestämmelserna om värdeminskningsavdrag är tillämpliga. Inkomsttaxering 2004.']"),
       function success() {
           test.assertExists(x("//a[normalize-space(text())='Enligt 18 kap. 18 § inkomstskattelagen får det ytterligare avdrag för värdeminskning på inventarier göras som behövs för att inventariernas skattemässiga värde inte ska överstiga deras verkliga värde. Prövningen ska avse samtliga inventarier på vilka bestämmelserna om värdeminskningsavdrag är tillämpliga. Inkomsttaxering 2004.']"));
           this.click(x("//a[normalize-space(text())='Enligt 18 kap. 18 § inkomstskattelagen får det ytterligare avdrag för värdeminskning på inventarier göras som behövs för att inventariernas skattemässiga värde inte ska överstiga deras verkliga värde. Prövningen ska avse samtliga inventarier på vilka bestämmelserna om värdeminskningsavdrag är tillämpliga. Inkomsttaxering 2004.']"));
       },
       function fail() {
           test.assertExists(x("//a[normalize-space(text())='Enligt 18 kap. 18 § inkomstskattelagen får det ytterligare avdrag för värdeminskning på inventarier göras som behövs för att inventariernas skattemässiga värde inte ska överstiga deras verkliga värde. Prövningen ska avse samtliga inventarier på vilka bestämmelserna om värdeminskningsavdrag är tillämpliga. Inkomsttaxering 2004.']"));
   });

   casper.run(function() {test.done();});
});