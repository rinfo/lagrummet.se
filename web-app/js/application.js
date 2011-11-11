var serverUrl = $('meta[name=serverURL]').attr("content") + "/";

// Instant search
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
        			$("#c-1").append('<p><a href="'+serverUrl+'search?query='+$("#query").attr("value")+'"><strong>Information från lagrummet.se</strong> <span class="count">('+ data.searchResult.totalResultsPerCategory['Ovrigt'] +')</span></a></p>');
        			if (data.searchResult.items['Ovrigt'] && data.searchResult.items['Ovrigt'].length > 0) {
        				$("#c-1").append('<ul id="ovrigt" />');
            			
            			$.each(data.searchResult.items['Ovrigt'], function(i, item) {
                			var title = (item.title) ? item.title : item.identifier;
                			var href = serverUrl + item.iri;
                			
                			$("#ovrigt").append('<li><p><a href="'+href+'">' + title + "</a></p></li>");
                			if (item.matches) {
                				$("#ovrigt li").filter(":last").append("<p>" + item.matches + " ...</p>");
                			}            		
                		});
            			$("#ovrigt").append('<li class="showAll"><a href="'+serverUrl+'">Visa fler träffar</a></li>');
        			}
        			
        		
        		// Propositioner
        		
    			$("#c-1").append('<p><a href="'+serverUrl+'search?query='+$("#query").attr("value")+'"><strong>Propositioner och skrivelser</strong> <span class="count">('+ data.searchResult.totalResultsPerCategory['Propositioner'] +')</span></a></p>');
        			
        		if (data.searchResult.items['Propositioner'] && data.searchResult.items['Propositioner'].length > 0) {
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
        			$("#propositioner").append('<li class="showAll"><a href="'+serverUrl+'">Visa fler träffar</a></li>');
        		}
        		
        		// Rättsfall
        			$("#c-1").append('<p><a href="'+serverUrl+'search?query='+$("#query").attr("value")+'"><strong>Rättsfall</strong> <span class="count">('+ data.searchResult.totalResultsPerCategory['Rattsfall'] +')</span></a></p>');
        			if (data.searchResult.items['Rattsfall'] && data.searchResult.items['Rattsfall'].length > 0) {
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
            			$("#rattsfall").append('<li class="showAll"><a href="'+serverUrl+'">Visa fler träffar</a></li>');
        			}
        			
        		
        		// Lagar
        			$("#c-2").append('<p><a href="'+serverUrl+'search?query='+$("#query").attr("value")+'"><strong>Lagar och förordningar</strong> <span class="count">('+ data.searchResult.totalResultsPerCategory['Lagar'] +')</span></a></p>');
        			if (data.searchResult.items['Lagar'] && data.searchResult.items['Lagar'].length > 0) {
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
            			$("#lagar").append('<li class="showAll"><a href="'+serverUrl+'">Visa fler träffar</a></li>');
        			}
        			
        		
        		// Utredningar
        			$("#c-2").append('<p><a href="'+serverUrl+'search?query='+$("#query").attr("value")+'"><strong>Utredningar</strong> <span class="count">('+ data.searchResult.totalResultsPerCategory['Utredningar'] +')</span></a></p>');
        			if (data.searchResult.items['Utredningar'] && data.searchResult.items['Utredningar'].length > 0) {
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
            			$("#utredningar").append('<li class="showAll"><a href="'+serverUrl+'">Visa fler träffar</a></li>');
        			}
        	} else {
        		$("#dynamicSearchResults").html("<h1>Inga sökresultat</h1>");
        	}
        	
        	if(data.searchResult.errorMessages.length > 0) {
        		$("#dynamicSearchResults").prepend('<div class="message"><ul id="errors" /></div>');
        		$.each(data.searchResult.errorMessages, function(i, item) {
        			$("#errors").append('<li>' + item + '</li>');
        		});
        	}
        } else {
        	$("#dynamicSearchResults").html("<h1>Det blev tyvärr fel, försök igen</h1>");
        }
    }, "json");
}

jQuery(document).ready(function($) {
	var originalUrl, t;
	
	//Make the search category drop-down more dynamic
	$("#cat").hide().after('<div id="searchCurrentCategory" /><ul id="searchCategoryList" />');
	$("#searchCategory label").addClass("target");
	if ($("#cat option[selected=selected]").size() < 1) {
		$("#cat option").eq(0).attr("selected", "selected");
		$("#cat").change();
	}
	$("html").click(function() {
		$("#searchCategoryList").hide();
		$("#searchCurrentCategory").removeClass("active");
	});
	$("#searchCurrentCategory").html($("#cat option[selected=selected]").html());
	$("#searchCategory").click(function(e) {
		e.stopPropagation();
		e.preventDefault();
		$("#searchCategoryList").toggle();
		$("#searchCurrentCategory").toggleClass("active");
	});

	$("#cat option").each(function() {
		$("#searchCategoryList").append('<li rel="'+$(this).val()+'"><p><strong rel="title">'+$(this).html()+'</strong></p><p>'+$(this).attr("rel")+'</p></li>');
	});
	$("#searchCategoryList li").click(function(e) {
		e.stopPropagation();
		$("#cat").val($(this).attr("rel"));
		$("#searchCurrentCategory").html($(this).find("[rel=title]").html()).removeClass("active");
		$("#searchCategoryList").hide();
	});
	
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
	
	$("#publisher").keyup(function(e) {
		var query = $(this).attr("value");
		$.get('search/findAvailablePublishers', {q: query}, function(data) {
			if(data) {
				$.each(data.publishers, function(i, item){
				});
			}
		}, "json");
	});
	
	// Interactive form on the extended search page
	$("#extSearchCats input").change(function() {
		$(".extendedSearch").addClass("hidden");
		$("#"+$(this).attr("value")).removeClass("hidden");
	});
	
	$.datepicker.setDefaults( $.datepicker.regional[ "sv" ] );
	$( "#extendedSearch input[type=date]" ).datepicker({ dateFormat: 'yy-mm-dd' }).each(function() {
		if (!$(this).attr("value")) {
			$(this).attr("value", "åååå-mm-dd");
		}
	});
});

