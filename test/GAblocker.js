casper.on('resource.requested', function(requestData, request) {
    //block requests to GA collect
    if(requestData.url.indexOf('http://www.google-analytics.com/collect') === 0) {
        this.echo('Aborting request to GA collect', 'INFO');
        request.abort();
    }
});