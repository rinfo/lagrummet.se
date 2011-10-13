function search() {
	var form = $("#search");
	$.get(form.attr("action")+"?ajax=true", form.serialize(), function(data) {
        if (data) {
		    console.log(data.searchResult);
		    if (!$("#searchResults").length) {
		    	$("#content > *").addClass("searchHidden");
	        	$("#content").prepend('<article id="searchResults" />');
		    }
//        	console.log(data.searchResult.totalResults);
        	if (data.searchResult.totalResults > 0) {
        		$("#searchResults").html('<ul id="redaktionellt" />');        		
        		$("#redaktionellt").before('<p><strong>Information från lagrummet.se</strong> <span class="count">('+ data.searchResult.totalResults +')</span></p>');
        		$.each(data.searchResult.items, function(i, item) {
        			$("#searchResults ul").append('<li><p><a href="#">' + item.title + "</a></li>");
        			
        			var excerpt;
        			if(item.matches != null) {
	        			if (item.matches.title && item.matches.title.length > 0) {
	        				excerpt = item.matches.title[0];
	        			} else if (item.matches.content && item.matches.content.length > 0) {
	        				excerpt = item.matches.content[0];
	        			}
        			}
        			if (excerpt) {
        				$("#searchResults ul li").filter(":last").append("<p>" + excerpt + " ...</p>");
        			}
        			
        			$("#searchResults ul li").filter(":last").append('<p class="type">'+item.identifier+'</p></li>');
        		});
        		$("#redaktionellt").append('<li class="showAll"><a href="#">Visa alla träffar '+ data.searchResult.totalResults +')</a></li>');
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

