var serverUrl = $('meta[name=serverURL]').attr("content") + "/";

// Instant search
function search() {
	var form = $("#search");
	var query = $("#query").attr("value");
	var cat = $("#cat").attr("value");
	
	if (!$("#dynamicSearchResults").length) {
    	$("#content > *").addClass("searchHidden");
    	$("#content").prepend('<article id="dynamicSearchResults" class="searchResults"><p><img src="'+serverUrl+'images/ajax-loader.gif"> Laddar sökresultat</p></article>');
    }
	
	$.get(form.attr("action")+"?ajax=true", form.serialize(), function(data) {
        if (data) {
//        	console.log(data);
        	if (data.searchResult.totalResults > 0 && $("#cat").attr("value") == "Alla") {      		
        		$("#dynamicSearchResults").html('<header><h1>Sökresultat</h1></header><p>Totalt antal resultat '+ data.searchResult.totalResults +'</p><div class="column first" id="c-1" /><div class="column" id="c-2" />');
        		
        		// Redaktionella resultat
        			$("#c-1").append('<p class="Ovrigt"><a href="'+serverUrl+'search?query='+query+'&cat=Ovrigt"><strong>Information från lagrummet.se</strong></a> <span class="count">('+ data.searchResult.totalResultsPerCategory['Ovrigt'] +')</span></p>');
        			if (data.searchResult.items['Ovrigt'] && data.searchResult.items['Ovrigt'].length > 0) {
        				if (data.searchResult.totalResultsPerCategory['Ovrigt'] > 4) $("#c-1 p.Ovrigt span.count").append(" Visar de första 4");
        				$("#c-1").append('<ul id="ovrigt" />');
            			
            			$.each(data.searchResult.items['Ovrigt'], function(i, item) {
                			var title = (item.title) ? item.title : item.identifier;
                			var href = serverUrl + item.iri;
                			
                			$("#ovrigt").append('<li><p><a href="'+href+'">' + title + "</a></p></li>");
                			if (item.matches) {
                				$("#ovrigt li").filter(":last").append("<p>" + item.matches + " ...</p>");
                			}            		
                		});
            			$("#ovrigt").append('<li class="showAll"><a href="'+serverUrl+'search?query='+query+'&cat=Ovrigt">Visa fler träffar</a></li>');
        			}
        			
        		
        		// Propositioner
        		
    			$("#c-1").append('<p class="Propositioner"><a href="'+serverUrl+'search?query='+query+'&cat=Propositioner"><strong>Propositioner och skrivelser</strong></a> <span class="count">('+ data.searchResult.totalResultsPerCategory['Propositioner'] +')</span></p>');
        			
        		if (data.searchResult.items['Propositioner'] && data.searchResult.items['Propositioner'].length > 0) {
        			if (data.searchResult.totalResultsPerCategory['Propositioner'] > 4) $("#c-1 p.Propositioner span.count").append(" Visar de första 4");
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
        			$("#propositioner").append('<li class="showAll"><a href="'+serverUrl+'search?query='+query+'&cat=Propositioner">Visa fler träffar</a></li>');
        		}
        		
        		// Rättsfall
        			$("#c-1").append('<p class="Rattsfall"><a href="'+serverUrl+'search?query='+query+'&cat=Rattsfall"><strong>Rättsfall</strong></a> <span class="count">('+ data.searchResult.totalResultsPerCategory['Rattsfall'] +')</span></p>');
        			if (data.searchResult.items['Rattsfall'] && data.searchResult.items['Rattsfall'].length > 0) {
        				if (data.searchResult.totalResultsPerCategory['Rattsfall'] > 4) $("#c-1 p.Rattsfall span.count").append(" Visar de första 4");
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
            			$("#rattsfall").append('<li class="showAll"><a href="'+serverUrl+'search?query='+query+'&cat=Rattsfall">Visa fler träffar</a></li>');
        			}
        			
        		
        		// Lagar
        			$("#c-2").append('<p class="Lagar"><a href="'+serverUrl+'search?query='+query+'&cat=Lagar"><strong>Lagar och förordningar</strong></a> <span class="count">('+ data.searchResult.totalResultsPerCategory['Lagar'] +')</span></p>');
        			if (data.searchResult.items['Lagar'] && data.searchResult.items['Lagar'].length > 0) {
        				if (data.searchResult.totalResultsPerCategory['Lagar'] > 4)  $("#c-2 p.Lagar span.count").append(" Visar de första 4");
        				$("#c-2").append('<ul id="lagar" />');
            			
            			$.each(data.searchResult.items['Lagar'], function(i, item) {
                			var title = (item.title) ? item.title : item.identifier;
                			var href = item.iri.replace(/http:\/\/.*?\//,"rinfo/");
                			
                			$("#lagar").append('<li><p><a href="'+serverUrl+href+'">' + title + "</a></p></li>");
                			if (item.matches) {
                				$("#lagar li").filter(":last").append("<p>" + item.matches + " ...</p>");
                			}
                		
                			$("#lagar li").filter(":last").append('<p class="type">'+item.identifier+'</p></li>');
                			
                			if(item.ikrafttradandedatum) {
                				$("#lagar li").filter(":last").append('<p class="type">Ikraft: '+item.ikrafttradandedatum+'</p></li>');
                			}
                		
                		});
            			$("#lagar").append('<li class="showAll"><a href="'+serverUrl+'search?query='+query+'&cat=Lagar">Visa fler träffar</a></li>');
        			}
        			
        		
        		// Utredningar
        			$("#c-2").append('<p class="Utredningar"><a href="'+serverUrl+'search?query='+query+'&cat=Utredningar"><strong>Utredningar</strong></a> <span class="count">('+ data.searchResult.totalResultsPerCategory['Utredningar'] +')</span></p>');
        			if (data.searchResult.items['Utredningar'] && data.searchResult.items['Utredningar'].length > 0) {
        				if (data.searchResult.totalResultsPerCategory['Utredningar'] > 4) $("#c-2 p.Utredningar span.count").append(" Visar de första 4");
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
            			$("#utredningar").append('<li class="showAll"><a href="'+serverUrl+'search?query='+query+'&cat=Utredningar">Visa fler träffar</a></li>');
        			}
        	} else if ($("#cat").attr("value") != "Alla") {
				$("#dynamicSearchResults").html('<h1>Sökresultat</h1><p>Visar '+ data.searchResult.items[cat].length  +' av totalt '+ data.searchResult.totalResults +' antal resultat </p>');
				$("#dynamicSearchResults").append("<table><tr><th>Titel</th><th>Identifierare</th></tr></table>");
				
				$.each(data.searchResult.items[cat], function(i, item) {
					var title = (item.title) ? item.title : item.identifier;
        			var href = item.iri.replace(/http:\/\/.*?\//,"rinfo/");
        			var excerpt = (item.matches) ? "<p>"+item.matches+"</p>" : "";
        			var identifier = item.identifier;
        			var ikraft = (item.ikrafttradandedatum) ? '<p class="type">Ikraft: '+item.ikrafttradandedatum+'</p>' : "";
        			
					$("#dynamicSearchResults table").append('<tr><td><p><a href="'+serverUrl+href+'">' + title + "</a></p>" + excerpt + ikraft + "</td><td>"+identifier+"</td></tr>");
					
				});

				$("#dynamicSearchResults").append('<p class="showAll"><a href="'+serverUrl+'search?'+form.serialize()+'">Visa fler träffar</a></p>');
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
	
	$("header #search #query").keyup(function(e) {
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

	if ($.datepicker) {
		$.datepicker.setDefaults( $.datepicker.regional[ "sv" ] );
		$( "#extendedSearch .dateinput" ).datepicker({ dateFormat: 'yy-mm-dd', showOn: "button", buttonImage:"../images/cal.png", buttonImageOnly:true }).each(function() {
			if (!$(this).attr("value")) {
				$(this).attr("value", "åååå-mm-dd");
			}
			
		});
	}
	
	function selectPartOfInput(el, startPos, endPos) {
        if (typeof el.selectionStart != "undefined") {
        	// W3C compliant browsers -> Selection object
        	el.selectionStart = startPos;
        	el.selectionEnd = endPos;
        } else if (document.selection && document.selection.createRange) {
            // Internet Explorer -> Microsoft Text Range
        	el.focus();
        	el.select();
            var range = document.selection.createRange();
            range.collapse(true);
            range.moveEnd("character", endPos);
            range.moveStart("character", startPos);
            range.select();
        }
    }

	var qLength = 0;
	var publishers = [];
	
	$("#beslutandeMyndighet, #departement").each(function(i) {
		 $.get(serverUrl + "search/findCreatorsOrPublishers?type="+ $(this).attr("id"), function(data) {
			 publishers[i] = data.publishers;
		}, "json");
		
		$(this).keyup(function(e) {
			if (e.keyCode == 8 || e.keyCode == 46) {
				qLength = $(this).attr("value").length;
			} else {
				qLength++;
				var re = new RegExp("," + $(this).attr("value"),"ig");
				var arr2str = "," + publishers[i].toString();
				var pos = arr2str.search(re);

				if (pos != -1) {
					/*var start = 1 + arr2str.slice(0, pos).lastIndexOf(",", pos);
					var match = arr2str.slice(start, arr2str.indexOf(",",start));*/
					var match = arr2str.slice(pos + 1, arr2str.indexOf(",",pos + 1));
					$(this).attr("value", match).select();
					selectPartOfInput(this, qLength, match.length);
				}
			}
		});
	});
	
	$('[placeholder]').focus(function() {
		  var input = $(this);
		  if (input.val() == input.attr('placeholder')) {
		    input.val('');
		    input.removeClass('placeholder');
		  }
		}).blur(function() {
		  var input = $(this);
		  if (input.val() == '' || input.val() == input.attr('placeholder')) {
		    input.addClass('placeholder');
		    input.val(input.attr('placeholder'));
		  }
		}).blur();
	
	$('[placeholder]').parents('form').submit(function() {
		  $(this).find('[placeholder]').each(function() {
		    var input = $(this);
		    if (input.val() == input.attr('placeholder')) {
		      input.val('');
		    }
		  })
		});
});

