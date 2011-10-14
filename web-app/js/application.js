function search() {
	var form = $("#search");
	$.get(form.attr("action")+"?ajax=true", form.serialize(), function(data) {
        if (data) {
		    if (!$("#searchResults").length) {
		    	$("#content > *").addClass("searchHidden");
	        	$("#content").prepend('<article id="searchResults" />');
		    }

        	if (data.searchResult.totalResults > 0) {
        		$("#searchResults").html('<ul id="redaktionellt" />');        		
        		$("#redaktionellt").before('<p><strong>Information från lagrummet.se</strong> <span class="count">('+ data.searchResult.totalResults +')</span></p>');
        		$.each(data.searchResult.items, function(i, item) {
        			var title = (item.title) ? item.title : item.identifier;
        			$("#searchResults ul").append('<li><p><a href="#">' + title + "</a></li>");
        			if (item.matches) {
        				$("#searchResults ul li").filter(":last").append("<p>" + item.matches + " ...</p>");
        			}
        			$("#searchResults ul li").filter(":last").append('<p class="type">'+item.identifier+'</p></li>');
        		}
        		
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

