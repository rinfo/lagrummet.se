var serverUrl = $('meta[name=serverURL]').attr("content") + "/";
var originalUrl, t, query = "";

// Instant search
function searchSuggestions(data) {
	var form = $("#search");
	var cat = $("#cat").attr("value") || "Ovrigt";
	if (data) {
        $("#searchSuggestions").empty().show();
		$.each(data.searchResult.topHits, function(i, item) {
			var title = (item.title) ? item.title : item.identifier;
			var href = serverUrl + item.iri.replace(/http:\/\/.*?\//,"rinfo/");
			
			$("#searchSuggestions").append('<li><a href="'+href+'" class="searchLink">' + title + "</a></li>");
		});
	}
}

function encodedMailAddress(coded) {
	  key = "4xqdRiHltKJmFYoTuNMfyLQswUbICaAD9pnrcG13E0V6BP7O2jXze8hkgW5vZS"
		  shift=coded.length
		  link=""
		  for (i=0; i<coded.length; i++) {
		    if (key.indexOf(coded.charAt(i))==-1) {
		      ltr = coded.charAt(i)
		      link += (ltr)
		    }
		    else {     
		      ltr = (key.indexOf(coded.charAt(i))-shift+key.length) % key.length
		      link += (key.charAt(ltr))
		    }
		  }
	  return link
}

function showRollingImageWaitForSearchResult() {
	if (!$("#dynamicSearchResults").length) {
        $("#content > *:not(#siteFooter)").addClass("searchHidden");
        $("#content").prepend('<article id="dynamicSearchResults" class="searchResults"><p><img src="'+serverUrl+'images/ajax-loader.gif"> Laddar sökresultat</p></article>');
    }
}

function instantSearch() {
	var form = $("#search");
    var url = form.attr("action")+"?ajax=true";
    query = $("#query").attr("value").replace(/&/g,'&amp;').replace(/</g,'&lt;').replace(/>/g,'&gt;');
    if (ltrim(query) === "")
        return;

    showRollingImageWaitForSearchResult();

	$.post(url, form.serialize(), function(data) {
	    if (data) {
            searchSuggestions(data);
            try {
                $("#dynamicSearchResults").html(data.dynamicSearchResults);
            } catch (e) {
                console.log("instantSearch() Failed to search because "+e.message);
            }
            window.history.pushState(null, null, form.attr("action") + "?" + form.serialize());
        }
    }, "json");
}

jQuery(document).ready(function($) {

	// check user accept cookie
    if (navigator.cookieEnabled && getCookie("userAcceptCookie")=="") {
        var cookieBannerHeight = $('#cookie-banner').outerHeight() + 4;
        $('body').css('margin-top', (cookieBannerHeight+10)+'px');

//        $('#primaryNavigation').css('top', (cookieBannerHeight+60)+'px');
//        $('#siteHeader').css('top', cookieBannerHeight+'px');
//        $('#content').css('top', cookieBannerHeight+'px');
//        $('#logo').css('margin-top', cookieBannerHeight+'px');

        $('#cookie-button').click(function() {
            $('#cookie-banner').hide();
            $('body').css('margin-top', '0px');

//            $('#primaryNavigation').css('top', '60px');
//            $('#siteHeader').css('top', '0px');
//            $('#content').css('top', '0px');
//            $('#logo').css('margin-top', '0px');
            setCookie("userAcceptCookie", "true", 100);
            console.log("Cookies accepted by user.")
        })
        $('#cookie-banner').show();
    }

	if($(".safeemail").length != 0) {
		var coded = $(".safeemail").prop("href");
		coded = coded.replace("mailto:", "");
		var decoded = encodedMailAddress(coded);
		$(".safeemail").prop("href", "mailto:"+decoded);
		$(".safeemail").text(decoded);
	}
	
	//Make the search category drop-down more dynamic
	$("#cat").hide().after('<div id="searchCurrentCategory" /><ul id="searchCategoryList" />');
	$("#searchCategory label").addClass("target");
	if ($("#cat option[selected=selected]").size() < 1) {
		$("#cat option").eq(0).attr("selected", "selected");
		$("#cat").change();
	}
	$("html").click(function() {
		$("#searchCategoryList, #searchSuggestions").hide();
		$("#searchSuggestions li").removeClass("active");
		$("#searchCurrentCategory").removeClass("active");
	});
	$("#searchCurrentCategory").html($("#cat option[selected=selected]").html());
	$("#searchCategory").click(function(e) {
		e.stopPropagation();
		e.preventDefault();
		$("#searchCategoryList").toggle();
		$("#searchCurrentCategory").toggleClass("active");
	});

        // 130305
        // Changed from: <p><strong data-rel="title">
        // to: <h5 data-rel="title">
	$("#cat option").each(function() {
		var selected = ($(this).attr("selected")) ? ' class="' + $(this).attr("selected") + '"' : "";
		//$("#searchCategoryList").append('<li data-rel="'+$(this).val()+'"'+selected+'><p><strong data-rel="title">'+$(this).html()+'</strong></p><p>'+$(this).attr("data-rel")+'</p></li>');
                $("#searchCategoryList").append('<li data-rel="'+$(this).val()+'"'+selected+'><h5 data-rel="title">'+$(this).html()+'</h5><p>'+$(this).attr("data-rel")+'</p></li>');
	});
        
	$("#searchCategoryList li").click(function(e) {
		e.stopPropagation();
		$("#cat").val($(this).attr("data-rel"));
		$("#searchCurrentCategory").html($(this).find("[data-rel=title]").html()).removeClass("active");
		$("#searchCategoryList > .selected").removeClass("selected");
		$(this).addClass("selected")
		$("#searchCategoryList").hide();
	});
	
	var form = $("#search");
	$("#query").keyup(function(e) {
		clearTimeout(t);
		if (!$(this).attr("value")) {
			$("#dynamicSearchResults").remove();
			$("#content > *").removeClass("searchHidden");
		} else if ($(this).attr("value").length > 2) {
			if ($(this).attr("value") != query) {
				t=setTimeout("instantSearch()", 350);	
			}
		}
	});

    $('#search').submit(function() {
        if (ltrim($("#query").val()) === "") {
            return false;
        }
    });

    $("header #search #query").keypress(function(e) {
		if (e.which == 13 && query == $(this).attr("value")) {
			// load the search suggestion if one is selected
			var searchSuggestion = $("#searchSuggestions li.active");
			if (searchSuggestion.length) {
                sendGaPageView($(location).attr('href'));
				window.location = searchSuggestion.children("a").attr("href");
			} else { // If no search selection submit form
			    $("#search").submit();
			}
			return false;
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
	
	// Step through the search suggestions with arrow keys
	$("#query").keyup(function(e) {
		//selected = $("#searchSuggestions li.active");
		var key = e.charCode || e.keyCode || 0;
		var selected = $("#searchSuggestions li.active");
		
		// Step down using down-arrow
		if (key == 40) {
			if (selected.length) {
				selected.removeClass("active").next().addClass("active");
			} else {
				$("#searchSuggestions li").first().addClass("active");
	      	}
	      	return false;
		}
		
		// Step up using up-arrow
		if (key == 38) { 
			if (selected.length) {
				selected.removeClass("active").prev().addClass("active");
			} else {
				$("#searchSuggestions li").last().addClass("active");
			}
			return false;
		}
		
		// Hide suggestions on Escape
		if (key == 27) {
			selected.removeClass("active");
			$("#searchSuggestions").hide();
			return false;
		}
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
            var r = document.selection.createRange();
            r.moveEnd("character", endPos);
            r.moveStart("character", startPos);
            r.select();
        }
    }

	var qLength = 0;
	var publishers = [];
	
	$("#beslutandeMyndighet, #departement").each(function(i) {
		 $.get(serverUrl + "search/findCreatorsOrPublishers?type="+ $(this).attr("id"), function(data) {
			 publishers[i] = "," + data.publishers.toString();
		}, "json");
		
		$(this).keyup(function(e) {
			if (e.keyCode == 8 || e.keyCode == 46 || e.keyCode == 9) {
				qLength = $(this).attr("value").length;
			} else {
				qLength++;
				var re = new RegExp("," + $(this).attr("value"),"ig");
				var pos = publishers[i].search(re);

				if (pos != -1) {
					/*var start = 1 + arr2str.slice(0, pos).lastIndexOf(",", pos);
					var match = arr2str.slice(start, arr2str.indexOf(",",start));*/
					var match = publishers[i].slice(pos + 1, publishers[i].indexOf(",",pos + 1));
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
	
	$(".toggleLink").click(function (e) {
		e.preventDefault();
		var toggleId = "." + $(this).attr('id');
		$(toggleId).toggleClass("hidden");
		$(this).children("span").toggleClass("hidden");
	});
	
	$('#dateSelect :radio').focus(updateSelectedStyle);
    $('#dateSelect :radio').blur(updateSelectedStyle);
    $('#dateSelect :radio').change(updateSelectedStyle);
    
	function updateSelectedStyle() {
        $('#dateSelect :radio').parent().removeClass('focused');
        $('#dateSelect :radio:checked').parent().addClass('focused');
    }
	
	updateSelectedStyle();
	
	/* Check required info on the contact page */
    var block = false;
    $("#contact").submit(function(e) {
        $("#contact :required").each(function() {
            if (!$(this).val()) block = true;
        });
        if (block) {
            alert("Var vänlig och fyll i de obligatoriska fälten");
            return false;
        } else {
            return true;
        }
    });
});

function sendGaPageView(url) {
    if(typeof(ga) === 'undefined') {
        return;
    }
    ga('send', 'pageview', url);
}

function ltrim(str){
    if(typeof(str) === 'undefined')
        return str;
    return str.replace(/^\s+/, "");
}

$(document).on("click",".searchLink", function() {
    var url = $(location).attr('href');
    sendGaPageView(url);
});

function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "; expires="+d.toUTCString();
    var maxAge = "; max-age="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + expires + maxAge + "; path=/;";
}
