var x = require('casper').selectXPath;

casper.test.begin('Sök generiskt utifrån JSON-fil.', function(test) {
   casper.start(casper.cli.get("url"));

   var searchlist = require("searchlist.json");
   for (var index = 0; index < searchlist.length; index++) {
	   var searchVal = searchlist[index];
	   
	   searchVal.search = function() {
		   casper.test.assertExists("form[name='search'] input[name='query']");
		   casper.test.assertTextDoesntExist(searchVal.text);
	
		   casper.sendKeys("input[name='query']", this.text);
	   } 
   
	   casper.waitForSelector("body", function(){}, captureScreen, 5000);
	
	   casper.then(searchVal.search);
	
	   casper.waitForSelector("#searchResults > header > h1", function(){}, captureScreen, 20000);
	
	   casper.then(function() {
	        this.test.assertSelectorHasText('#searchResults > header > h1', 'Sökresultat för '+searchVal.text);
	        if (searchVal.index > 0) 
	        	this.test.assertSelectorHasText('#LagarList > li:nth-child('+searchVal.index+') > p.type > span',searchVal.result);
	        else
	        	this.test.assertTextExists(searchVal.result);
	        this.click('#LagarList > li:nth-child('+searchVal.index+') > p:nth-child(1) > a');
	   });
	
	   casper.waitForSelector("#rinfo", function(){}, captureScreen, 20000);
	
	   casper.then(function() {
	       this.test.assertSelectorHasText('#leftCol > table > tbody > tr > td:nth-child(2)',searchVal.result);
	   });
	   casper.then(function() {
	       this.click("#logo > a");
	   });
   }

   casper.run(function() {test.done();});
});
//#LagarList > li:nth-child(1) > p.type > span
//#LagarList > li:nth-child(1) > p:nth-child(2) > span
//#LagarList > li:nth-child(1) > p.type > span
