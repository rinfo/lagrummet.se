var x = require('casper').selectXPath;

var searchString = "A3A5A7A9A12A15A18A21A24A27A30A33A36A39A42A45A48A51A54A57A60A63A66A69A72A75A78A81A84A87A90A93A96A100A104A108A112A116A120A124A128A132A136A140A144A148A152A156A160A164A168A172A176A180A184A188A192A196A200A204A208A212A216A220A224A228A232A236A240A244A248A252A256A260A264A268A272A276A280A284A288A292A296A300A304A308A312A316A320A324A328A332A336A340A344A348A352A356A360A364A368A372A376A380A384A388A392A396A400A404A408A412A416A420A424A428A432A436A440A444A448A452A456A460A464A468A472A476A480A484A488A492A496A500A504A508A512A";

casper.test.begin('Test query text truncate when too big', function(test) {
   casper.start(casper.cli.get("url"));

   casper.waitForSelector("body", function(){}, captureScreen, 5000);

   casper.then(function() {

        this.test.assertExists("form[name=search] input[name='query']");

        this.sendKeys("#query", searchString);
        this.echo("Fyll i 500 tecken i söksträngen!")

        this.test.assertEval(function() {
            var value = __utils__.findOne('#query').value;
            return value.lenght!=100;
        },"Kontrollera att fältet har exakt 100 tecken");
   });

   casper.run(function() {test.done();});
});