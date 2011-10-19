var serverUrl = $('meta[name=serverURL]').attr("content") + "/";

function search() {
	var form = $("#search");
	if (!$("#dynamicSearchResults").length) {
    	$("#content > *").addClass("searchHidden");
    	$("#content").prepend('<article id="dynamicSearchResults" class="searchResults"><p><img src="'+serverUrl+'images/ajax-loader.gif"> Laddar sökresultat</p></div>');
    }
	
	$.get(form.attr("action")+"?ajax=true", form.serialize(), function(data) {
        if (data) {
//        	console.log(data);
        	if (data.searchResult.totalResults > 0) {      		
        		$("#dynamicSearchResults").html('<header><h1>Sökresultat</h1></header><p>Totalt antal resultat '+ data.searchResult.totalResults +'</p><div class="column" id="c-1" /><div class="column" id="c-2" />');
        		
        		// Redaktionella resultat
        		if (data.searchResult.items['Ovrigt'] && data.searchResult.items['Ovrigt'].length > 0) {
        			$("#c-1").append('<p><strong>Information från lagrummet.se</strong> <span class="count">('+ data.searchResult.items['Ovrigt'].length +')</span></p>');
        			$("#c-1").append('<ul id="ovrigt" />');
        			
        			$.each(data.searchResult.items['Ovrigt'], function(i, item) {
            			var title = (item.title) ? item.title : item.identifier;
            			var href = serverUrl + item.iri;
            			
            			$("#ovrigt").append('<li><p><a href="'+href+'">' + title + "</a></p></li>");
            			if (item.matches) {
            				$("#ovrigt li").filter(":last").append("<p>" + item.matches + " ...</p>");
            			}            		
            		});
        		}
        		
        		// Propositioner
        		if (data.searchResult.items['Propositioner'] && data.searchResult.items['Propositioner'].length > 0) {
        			$("#c-1").append('<p><strong>Propositioner och skrivelser</strong> <span class="count">('+ data.searchResult.items['Propositioner'].length +')</span></p>');
        			$("#c-1").append('<ul id="propositioner" />');
        			
        			$.each(data.searchResult.items['Propositioner'], function(i, item) {
            			var title = (item.title) ? item.title : item.identifier;
            			var href = item.iri.replace(/http:\/\/.*?\//,"rinfo/");
            			
            			$("#propositioner").append('<li><p><a href="'+serverUrl+href+'">' + title + "</a></p></li>");
            			if (item.matches) {
            				$("#propositioner li").filter(":last").append("<p>" + item.matches + " ...</p>");
            			}
            		
            			$("#propositioner li").filter(":last").append('<p class="type">'+item.identifier+'</p></li>');
            		
            		});
        		}
        		
        		// Rättsfall
        		if (data.searchResult.items['Rattsfall'] && data.searchResult.items['Rattsfall'].length > 0) {
        			$("#c-1").append('<p><strong>Rättsfall</strong> <span class="count">('+ data.searchResult.items['Rattsfall'].length +')</span></p>');
        			$("#c-1").append('<ul id="rattsfall" />');
        			
        			$.each(data.searchResult.items['Rattsfall'], function(i, item) {
            			var title = (item.title) ? item.title : item.identifier;
            			var href = item.iri.replace(/http:\/\/.*?\//,"rinfo/");
            			
            			$("#rattsfall").append('<li><p><a href="'+serverUrl+href+'">' + title + "</a></p></li>");
            			if (item.matches) {
            				$("#rattsfall li").filter(":last").append("<p>" + item.matches + " ...</p>");
            			}
            		
            			$("#rattsfall li").filter(":last").append('<p class="type">'+item.identifier+'</p></li>');
            		
            		});
        		}
        		
        		// Lagar
        		if (data.searchResult.items['Lagar'] && data.searchResult.items['Lagar'].length > 0) {
        			$("#c-2").append('<p><strong>Lagar och förordningar</strong> <span class="count">('+ data.searchResult.items['Lagar'].length +')</span></p>');
        			$("#c-2").append('<ul id="lagar" />');
        			
        			$.each(data.searchResult.items['Lagar'], function(i, item) {
            			var title = (item.title) ? item.title : item.identifier;
            			var href = item.iri.replace(/http:\/\/.*?\//,"rinfo/");
            			
            			$("#lagar").append('<li><p><a href="'+serverUrl+href+'">' + title + "</a></p></li>");
            			if (item.matches) {
            				$("#lagar li").filter(":last").append("<p>" + item.matches + " ...</p>");
            			}
            		
            			$("#lagar li").filter(":last").append('<p class="type">'+item.identifier+'</p></li>');
            		
            		});
        		}
        		
        		// Utredningar
        		if (data.searchResult.items['Utredningar'] && data.searchResult.items['Utredningar'].length > 0) {
        			$("#c-2").append('<p><strong>Utredningar</strong> <span class="count">('+ data.searchResult.items['Utredningar'].length +')</span></p>');
        			$("#c-2").append('<ul id="utredningar" />');
        			
        			$.each(data.searchResult.items['Utredningar'], function(i, item) {
            			var title = (item.title) ? item.title : item.identifier;
            			var href = item.iri.replace(/http:\/\/.*?\//,"rinfo/");
            			
            			$("#utredningar").append('<li><p><a href="'+serverUrl+href+'">' + title + "</a></p></li>");
            			if (item.matches) {
            				$("#utredningar li").filter(":last").append("<p>" + item.matches + " ...</p>");
            			}
            		
            			$("#utredningar li").filter(":last").append('<p class="type">'+item.identifier+'</p></li>');
            		
            		});
        		}
        	} else {
        		$("#dynamicSearchResults").html("<h1>Inga sökresultat</h1>");
        	}
        } else {
        	$("#dynamicSearchResults").html("<h1>Det blev tyvärr fel, försök igen</h1>");
        }
    }, "json");
}

(function($) {
	var originalUrl, t;
	
	$("#query").keyup(function(e) {
		var form = $("#search");
		clearTimeout(t);
		
		if (!$(this).attr("value")) {
			$("#dynamicSearchResults").remove();
			$("#content > *").removeClass("searchHidden");
		} else if ($(this).attr("value").length > 2) {
			t=setTimeout("search()", 300);	
		}
	});
})(jQuery);

