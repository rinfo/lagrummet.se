function search() {
	var form = $("#search");
	$.get(form.attr("action")+"?ajax=true", form.serialize(), function(data) {
        if (data) {
//        	console.log(data);
		    if (!$("#searchResults").length) {
		    	$("#content > *").addClass("searchHidden");
	        	$("#content").prepend('<article id="searchResults" />');
		    }

        	if (data.searchResult.totalResults > 0) {
        		// $("#searchResults").html('<ul />');        		
        		$("#searchResults").html('<header><h1>Sökresultat</h1></header><p>Totalt antal resultat '+ data.searchResult.totalResults +'</p><div class="column" id="c-1" /><div class="column" id="c-2" />');
        		
        		// Redaktionella resultat
        		if (data.searchResult.items['Ovrigt'] && data.searchResult.items['Ovrigt'].length > 0) {
        			$("#c-1").append('<p><strong>Information från lagrummet.se</strong> <span class="count">('+ data.searchResult.items['Ovrigt'].length +')</span></p>');
        			$("#c-1").append('<ul id="ovrigt" />');
        			
        			$.each(data.searchResult.items['Ovrigt'], function(i, item) {
            			var title = (item.title) ? item.title : item.identifier;
            			$("#ovrigt").append('<li><p><a href="#">' + title + "</a></p></li>");
            			if (item.matches) {
            				$("#ovrigt li").filter(":last").append("<p>" + item.matches + " ...</p>");
            			}
            		
            			$("#ovrigt li").filter(":last").append('<p class="type">'+item.identifier+'</p></li>');
            		
            		});
        		}
        		
        		// Rättsfall
        		if (data.searchResult.items['Rattsfall'] && data.searchResult.items['Rattsfall'].length > 0) {
        			$("#c-1").append('<p><strong>Rättsfall</strong> <span class="count">('+ data.searchResult.items['Rattsfall'].length +')</span></p>');
        			$("#c-1").append('<ul id="rattsfall" />');
        			
        			$.each(data.searchResult.items['Rattsfall'], function(i, item) {
            			var title = (item.title) ? item.title : item.identifier;
            			$("#rattsfall").append('<li><p><a href="#">' + title + "</a></p></li>");
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
            			$("#lagar").append('<li><p><a href="#">' + title + "</a></p></li>");
            			if (item.matches) {
            				$("#lagar li").filter(":last").append("<p>" + item.matches + " ...</p>");
            			}
            		
            			$("#lagar li").filter(":last").append('<p class="type">'+item.identifier+'</p></li>');
            		
            		});
        		}
        	} else {
        		$("#searchResults").html("<h1>Inga sökresultat</h1>");
        	}
        } else {
    		// console.log("error");
        }
    }, "json");
}

(function($) {
	var originalUrl, t;
	
	$("#query").keyup(function(e) {
		var form = $("#search");
		clearTimeout(t);
		
		if (!$(this).attr("value")) {
			$("#searchResults").remove();
			$("#content > *").removeClass("searchHidden");
		} else if ($(this).attr("value").length > 2) {
			t=setTimeout("search()", 300);	
		}
	});
})(jQuery);

