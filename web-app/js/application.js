var serverUrl = $('meta[name=serverURL]').attr("content") + "/";
var originalUrl, t, query = "";

// Instant search
function searchSuggestions(data) {
	var form = $("#search");
	var cat = $("#cat").attr("value");
	
	if (data) {
		$("#searchSuggestions").empty().show();
		$.each(data.searchResult.topHits, function(i, item) {
			var title = (item.title) ? item.title : item.identifier;
			var href = serverUrl + item.iri.replace(/http:\/\/.*?\//,"rinfo/");
			if (title.length > 40) title = title.substr(0, 40) + "...";
			
			$("#searchSuggestions").append('<li><a href="'+href+'">' + title + "</a></li>");         		
		});
	}
}

function instantSearch() {
	var form = $("#search");
	query = $("#query").attr("value");
	var cat = $("#cat").attr("value");
	var sokhjalp = '<div id="searchHelpPuff"><strong>Hittade du inte vad du sökte?</strong><p><a href="/sokhjalp">Sökhjälp</a> - Hjälpsida som ger dig tips på hur du kan söka på bästa sätt</p></div>';
	
	if (!$("#dynamicSearchResults").length) {
    	$("#content > *:not(#siteFooter)").addClass("searchHidden");
    	$("#content").prepend('<article id="dynamicSearchResults" class="searchResults"><p><img src="'+serverUrl+'images/ajax-loader.gif"> Laddar sökresultat</p></article>');
    }
	
	$.get(form.attr("action")+"?ajax=true", form.serialize(), function(data) {
		
        if (data) {
        	searchSuggestions(data);
        	if (data.searchResult.totalResults > 0 && $("#cat").attr("value") == "Alla") {      		
        		$("#dynamicSearchResults").html('<header><h1>Sökresultat för '+query+'</h1></header><p>Totalt antal resultat '+ data.searchResult.totalResults +'</p>');
        		
        		if(data.synonyms.length > 0) {
	        		$("#dynamicSearchResults").append('<p>Din sökning gav även träff på följande: <span class="query">' + data.synonyms.join(', ') + '</span></p>');
	        		$("#dynamicSearchResults").append('<p>För att se sökresultatet utan associerade träffar, <a href="'+serverUrl+'search?query='+query+'&cat=Alla&alias=false">klicka här</a></p>');
        		}
        		$("#dynamicSearchResults").append('<div class="column first" id="c-1" /><div class="column" id="c-2" />');
        		
        		// Redaktionella resultat
        			$("#c-1").append('<p class="Ovrigt"><a href="'+serverUrl+'search?query='+query+'&cat=Ovrigt" class="catTitle">Information från lagrummet.se</a> <span class="count">('+ data.searchResult.totalResultsPerCategory['Ovrigt'] +')</span></p>');
        			if (data.searchResult.items['Ovrigt'] && data.searchResult.items['Ovrigt'].length > 0) {
        				if (data.searchResult.totalResultsPerCategory['Ovrigt'] > data.searchResult.items['Ovrigt'].length) $("#c-1 p.Ovrigt span.count").append(" Visar de första ").append(data.searchResult.items['Ovrigt'].length);

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
        		
    			$("#c-1").append('<p class="Propositioner"><a href="'+serverUrl+'search?query='+query+'&cat=Propositioner" class="catTitle">Propositioner och skrivelser</a> <span class="count">('+ data.searchResult.totalResultsPerCategory['Propositioner'] +')</span></p>');
        			
        		if (data.searchResult.items['Propositioner'] && data.searchResult.items['Propositioner'].length > 0) {
        			if (data.searchResult.totalResultsPerCategory['Propositioner'] > data.searchResult.items['Propositioner'].length) $("#c-1 p.Propositioner span.count").append(" Visar de första ").append(data.searchResult.items['Propositioner'].length);

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
        			$("#c-1").append('<p class="Rattsfall"><a href="'+serverUrl+'search?query='+query+'&cat=Rattsfall" class="catTitle">Rättsfall</a> <span class="count">('+ data.searchResult.totalResultsPerCategory['Rattsfall'] +')</span></p>');
        			if (data.searchResult.items['Rattsfall'] && data.searchResult.items['Rattsfall'].length > 0) {
        				if (data.searchResult.totalResultsPerCategory['Rattsfall'] > data.searchResult.items['Rattsfall'].length) $("#c-1 p.Rattsfall span.count").append(" Visar de första ").append(data.searchResult.items['Rattsfall'].length);
        				
        				$("#c-1").append('<ul id="rattsfall" />');
            			
            			$.each(data.searchResult.items['Rattsfall'], function(i, item) {
                			var title = (item.identifier) ? item.identifier : item.malnummer;
                			var href = item.iri.replace(/http:\/\/.*?\//,"rinfo/");
                			
                			$("#rattsfall").append('<li><p><a href="'+serverUrl+href+'">' + title + "</a></p></li>");
                			if (item.matches) {
                				$("#rattsfall li").filter(":last").append("<p>" + item.matches + " ...</p>");
                			}
                		
                			if(item.identifier) {
                				$("#rattsfall li").filter(":last").append('<p class="type">'+item.identifier+'</p></li>');
                			}
                		
                		});
            			$("#rattsfall").append('<li class="showAll"><a href="'+serverUrl+'search?query='+query+'&cat=Rattsfall">Visa fler träffar</a></li>');
        			}
        		
        		// Lagar
        			$("#c-2").append('<p class="Lagar"><a href="'+serverUrl+'search?query='+query+'&cat=Lagar" class="catTitle">Lagar och förordningar</a> <span class="count">('+ data.searchResult.totalResultsPerCategory['Lagar'] +')</span></p>');
        			if (data.searchResult.items['Lagar'] && data.searchResult.items['Lagar'].length > 0) {
        				if (data.searchResult.totalResultsPerCategory['Lagar'] > data.searchResult.items['Lagar'].length) $("#c-2 p.Lagar span.count").append(" Visar de första ").append(data.searchResult.items['Lagar'].length);

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
        			
        		// Myndigheters föreskrifter
        			$("#c-2").append('<p class="Foreskrifter"><a href="'+serverUrl+'search?query='+query+'&cat=Foreskrifter" class="catTitle">Myndigheters föreskrifter</a> <span class="count">('+ data.searchResult.totalResultsPerCategory['Foreskrifter'] +')</span></p>');
        			if (data.searchResult.items['Foreskrifter'] && data.searchResult.items['Foreskrifter'].length > 0) {
        				if (data.searchResult.totalResultsPerCategory['Foreskrifter'] > data.searchResult.items['Foreskrifter'].length) $("#c-2 p.Foreskrifter span.count").append(" Visar de första ").append(data.searchResult.items['Foreskrifter'].length);

        				$("#c-2").append('<ul id="foreskrifter" />');
            			
            			$.each(data.searchResult.items['Foreskrifter'], function(i, item) {
                			var title = (item.title) ? item.title : item.identifier;
                			var href = item.iri.replace(/http:\/\/.*?\//,"rinfo/");
                			
                			$("#foreskrifter").append('<li><p><a href="'+serverUrl+href+'">' + title + "</a></p></li>");
                			if (item.matches) {
                				$("#foreskrifter li").filter(":last").append("<p>" + item.matches + " ...</p>");
                			}
                		
                			$("#foreskrifter li").filter(":last").append('<p class="type">'+item.identifier+'</p></li>');
                			
                			if(item.ikrafttradandedatum) {
                				$("#foreskrifter li").filter(":last").append('<p class="type">Ikraft: '+item.ikrafttradandedatum+'</p></li>');
                			}
                		
                		});
            			$("#foreskrifter").append('<li class="showAll"><a href="'+serverUrl+'search?query='+query+'&cat=Foreskrifter">Visa fler träffar</a></li>');
        			}
        			
            	// Utredningar
        			$("#c-2").append('<p class="Utredningar"><a href="'+serverUrl+'search?query='+query+'&cat=Utredningar" class="catTitle">Utredningar</a> <span class="count">('+ data.searchResult.totalResultsPerCategory['Utredningar'] +')</span></p>');
        			if (data.searchResult.items['Utredningar'] && data.searchResult.items['Utredningar'].length > 0) {
        				if (data.searchResult.totalResultsPerCategory['Utredningar'] > data.searchResult.items['Utredningar'].length) $("#c-2 p.Utredningar span.count").append(" Visar de första ").append(data.searchResult.items['Utredningar'].length);

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
        		
        			$("#dynamicSearchResults").append(sokhjalp);
        	} else if ($("#cat").attr("value") != "Alla") {
				$("#dynamicSearchResults").html('<h1>Sökresultat</h1><p>Visar '+ data.searchResult.items[cat].length  +' av totalt '+ data.searchResult.totalResults +' antal resultat </p>');
				$("#dynamicSearchResults").append("<table><tr><th>Titel</th><th>Beteckning</th></tr></table>");
				
				$.each(data.searchResult.items[cat], function(i, item) {
					var title = (item.identifier) ? item.identifier : item.malnummer;
        			var href = item.iri.replace(/http:\/\/.*?\//,"rinfo/");
        			var excerpt = (item.matches) ? "<p>"+item.matches+"</p>" : "";
        			var identifier = (item.identifier) ? item.identifier : item.malnummer;
        			var ikraft = (item.ikrafttradandedatum) ? '<p class="type">Ikraft: '+item.ikrafttradandedatum+'</p>' : "";
        			
					$("#dynamicSearchResults table").append('<tr><td><p><a href="'+serverUrl+href+'">' + title + "</a></p>" + excerpt + ikraft + "</td><td>"+identifier+"</td></tr>");
					
				});

				$("#dynamicSearchResults").append('<p class="showAll"><a href="'+serverUrl+'search?'+form.serialize()+'">Visa fler träffar</a></p>' + sokhjalp);
        	} else {
        		$("#dynamicSearchResults").html('<h1>Inga sökresultat</h1>' + sokhjalp);
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
	//Make the search category drop-down more dynamic
	$("#cat").hide().after('<div id="searchCurrentCategory" /><ul id="searchCategoryList" />');
	$("#searchCategory label").addClass("target");
	if ($("#cat option[selected=selected]").size() < 1) {
		$("#cat option").eq(0).attr("selected", "selected");
		$("#cat").change();
	}
	$("html").click(function() {
		$("#searchCategoryList, #searchSuggestions").hide();
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
		var selected = ($(this).attr("selected")) ? ' class="' + $(this).attr("selected") + '"' : "";
		$("#searchCategoryList").append('<li rel="'+$(this).val()+'"'+selected+'><p><strong rel="title">'+$(this).html()+'</strong></p><p>'+$(this).attr("rel")+'</p></li>');
	});
	$("#searchCategoryList li").click(function(e) {
		e.stopPropagation();
		$("#cat").val($(this).attr("rel"));
		$("#searchCurrentCategory").html($(this).find("[rel=title]").html()).removeClass("active");
		$("#searchCategoryList > .selected").removeClass("selected");
		$(this).addClass("selected")
		$("#searchCategoryList").hide();
	});
	
	$("header #search #query").keyup(function(e) {
		var form = $("#search");
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
	
	$("header #search #query").live("keypress", function(e) {
		if (e.which == 13 && query == $(this).attr("value")) {
			// load the search suggestion if one is selected
			var searchSuggestion = $("#searchSuggestions li.active");
			if (searchSuggestion.length) {
				window.location = searchSuggestion.children("a").attr("href");
			}
			e.preventDefault();
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
	$("#query").focus(function() {
		$(this).bind("keydown", function(e) {
			var selected = $("#searchSuggestions li.active");
			
			// Step down using down-arrow
			if (e.keyCode == 40) { 
				if (selected.length) {
					selected.removeClass("active").next().addClass("active");
				} else {
					$("#searchSuggestions li").first().addClass("active");
		      	}
		      	return false;
			}
			
			// Step up using up-arrow
			if (e.keyCode == 38) { 
				if (selected.length) {
					selected.removeClass("active").prev().addClass("active");
				} else {
					$("#searchSuggestions li").last().addClass("active");
				}
				return false;
			}
			
			// Hide suggestions on Escape
			if (e.keyCode == 27) {
				$("#searchSuggestions li.active").removeClass("active");
				$("#searchSuggestions").hide();
				return false;
			}
		});
	}).blur(function() {
		
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
	if ($.browser.msie && $.browser.version < 10) {
		var block = false;
		$("#contact").submit(function(e) {
			$("#contact input[required]").each(function() {
				if (!$(this).val()) block = true;
			});
			
			$("#contact textarea[required]").each(function() {
				if (!$(this).html()) block = true;
			});
			if (block) {
				alert("Var vänlig och fyll i de markerade fälten");
				return false;
			} else {
				return true;
			}
		});
	}
});

/* READSPEAKER */
var rs={settings:{cookieLifetime:360000000,defaultSpeedValue:100,defaultSpeed:"medium",defaultSync:"wordsent",defaultPopup:"iconon",reloadPage:false,sentColor:"#beffd6",textColor:"#000000",useCloudService:true,usePopupButton:true,usePopupPlayer:true,useReadspeakerIcon:false,usePost:false,wordColor:"#a4cbff",flash:{allowScriptAccess:"always",height:"20",params:"&autoplay=1&rskin=bump&time_format=ms",popupHeight:"20",popupWidth:"60",width:"250"},media:{cloudHost:{arrowSrc:"/graphic/default/images/rs_embhl_arrow_small.gif",closeSrc:"/graphic/default/images/rs_player_close_13px.png",flashSrc:"/flash/readspeaker.swf",popupSrc:"/graphic/default/buttons/icon_16px.gif",settingsSrc:"/graphic/default/images/rs_embhl_wrench_13px.png",playSrc:"/graphic/default/images/rs_player_play_13px.png",playInactiveSrc:"/graphic/default/images/rs_player_play_inactive_13px.png",pauseSrc:"/graphic/default/images/rs_player_pause_13px.png",pauseInactiveSrc:"/graphic/default/images/rs_player_pause_inactive_13px.png",stopSrc:"/graphic/default/images/rs_player_stop_13px.png",stopInactiveSrc:"/graphic/default/images/rs_player_stop_inactive_13px.png",iconSrc:"/graphic/default/images/rs_player_icon_16px.gif"},defaultHost:{arrowSrc:"/images/enterprise/default/rs_arrow.gif",closeSrc:"/images/enterprise/default/close.png",flashSrc:"/flash/readspeaker20.swf",popupSrc:"/images/buttons/listen_icons/icon_16px.gif",settingsSrc:"/images/enterprise/default/wrench.png",playSrc:"/images/enterprise/default/play.png",playInactiveSrc:"/images/enterprise/default/play2.png",pauseSrc:"/images/enterprise/default/pause.png",pauseInactiveSrc:"/images/enterprise/default/pause2.png",stopSrc:"/images/enterprise/default/stop.png",stopInactiveSrc:"/images/enterprise/default/stop2.png",iconSrc:"/images/buttons/listen_icons/icon_16px.gif"},returnSrc:function(a){var b=null;if(rs.settings.useCloudService&&rs.settings.protocol!="https"){if(this["cloudHost"][a].indexOf("http://")!=-1){b=""}else{if(rs.imagehost&&rs.imagehost.length>0){b="http://"+rs.imagehost}else{b="http://f1."+rs.settings.region+".readspeaker.com"}}return b+this["cloudHost"][a]}else{if(this["defaultHost"][a].indexOf("://")!=-1){b=""}else{b=rs.settings.protocol+"//media.readspeaker.com"}return b+this["defaultHost"][a]}}},domain:"readspeaker.com",subdomain:"app",region:".eu",rscall:null,onclick:'readpage(this.href,"rs_popup_player");return false;',protocol:document.location.protocol||"http:",rsent:"rsent",version:"1.04b-embhl"},phrases:{closeplayer:"St&auml;ng spelare",fast:"H&ouml;g",hide:"D&ouml;lj",highlightingoptions:"Markeringsval",listentoselectedtext:"Lyssna pÃ¥ markerad text",medium:"Normal",nohighlighting:"Ingen markering",nosound:"Inget ljud?",pause:"Paus",playerwidth:"285",play:"Play",popupbutton:"Popup-knapp",sentonly:"Enbart meningar",settings:"Inst&auml;llningar",show:"Visa",slow:"L&aring;ngsam",speechenabled:"Talsatt av <a href='http://www.readspeaker.com/sv/?ref="+encodeURIComponent(document.location.href)+"' target='_blank'>ReadSpeaker</a>",speed:"L&auml;shastighet",stop:"Stopp",volume:"Volym",wordonly:"Enbart ord",wordsent:"Ord och meningar"},appserverhost:"app.readspeaker.com",thesync:"none",thespeed:100,state:"NO_ACTION",bmindex:1,sentbmindex:0,seltexttimes:0,seltextcleanuptimes:0,startofsentence:1,inc:0,start:null,stop:null,newhtml:"",startOffset:null,endOffset:null,selectedString:"",tempSelection:"",selhtml:"",selectedWordsRange:[],firstrun:1,selectedRange:null,globalcount:null,oldwordhl_FF:[],oldwordhlclass_FF:[],oldsenthl_FF:[],oldsenthlclass_FF:[],oldwordhlrange_IE:[],oldwordhlbackcol_IE:[],oldwordhlforecol_IE:[],oldwordhl_IE:[],oldwordhlclass_IE:[],oldsenthlrange_IE:[],oldsenthlbackcol_IE:[],oldsenthlforecol_IE:[],oldsenthl_IE:[],oldsenthlclass_IE:[],oldbodyclass:null,exludednodes:["table","tr","select","option","textarea","ul","ol","dl","thead","tbody","tfoot","colgroup","script","map","optgroup"],data:{restorehtml:[],readid:[],current:{restorehtml:null,readid:null,playerid:null},previous:{playerid:null,sync:null}},convertSpeed:function(a){switch(a){case"slow":return rs.settings.defaultSpeedValue-25;case"medium":return rs.settings.defaultSpeedValue;case"fast":return rs.settings.defaultSpeedValue+25;default:return rs.settings.defaultSpeedValue}},getRsentInfo:function(){var f=new RegExp(".+://"+rs.settings.subdomain+"(.[a-z]{2})?."+rs.settings.domain+".+","i");var d=document.getElementsByTagName("A");var e=[];var g="";for(var b=0;b<d.length;b++){e[b]=d[b]}for(var a=0;a<e.length;a++){if(e[a].getAttribute("href")&&f.test(e[a].getAttribute("href"))&&e[a].id!="rsSaveBtn"){if(rs.settings.useCloudService&&e[a].getAttribute("href").replace(f,"$1").length>0){g=e[a].getAttribute("href").replace(f,"$1")}rs.popup.buttonExists=true;if(!rs.settings.useReadspeakerIcon&&e[a].getElementsByTagName("img")[0]&&e[a].getElementsByTagName("img")[0].src&&e[a].getElementsByTagName("img")[0].src.length>0){rs.settings.media.popupSrc=e[a].getElementsByTagName("img")[0].src}rs.settings.rscall=e[a].getAttribute("href");for(var c=0;c<e[a].attributes.length;c++){if(e[a].attributes[c].nodeName.toLowerCase()=="onclick"&&rs.settings.usePopupPlayer==false&&e[a].attributes[c].nodeValue.length>0){rs.settings.onclick=e[a].attributes[c].nodeValue}}break}}if(rs.settings.useCloudService){rs.appserverhost=rs.settings.subdomain+g+"."+rs.settings.domain;if(g.length>0){rs.settings.region=g}rs.imagehost="f1"+rs.settings.region+"."+rs.settings.domain}},issuePOST:function(a,c){rs.globalcount++;if(!document.getElementById("postiframe1")||!document.getElementById("postiframe2")||!document.getElementById("postiframe3")){var g=null;if(document.selection){try{g=document.createElement('<iframe name="postiframe'+rs.globalcount+'">')}catch(m){g=document.createElement("iframe")}g.name="postiframe"+rs.globalcount}else{g=document.createElement("iframe");g.name="postiframe"+rs.globalcount}g.setAttribute("id","postiframe"+rs.globalcount);g.setAttribute("style","display: none; position: absolute;");g.style.display="none";var d=document.getElementsByTagName("body");var o=null;if(d.length>0){o=d.item(0)}if(o){o.appendChild(g)}else{return}}a=a+"&output=audiolink";var f=document.createElement("form");f.target="postiframe"+rs.globalcount;f.method="post";var n=a.split("?");var l=Math.random();f.action=rs.settings.protocol+"//"+rs.appserverhost+"/enterprise/iframeproxy.php?rsent="+rs.settings.rsent+"&randid="+l;var k=n[1].split("&");var h;for(h=0;h<k.length;h++){var e=k[h].split("=");var p=document.createElement("input");p.setAttribute("name",unescape(e[0]));if(unescape(e[0]).indexOf("url")!=-1&&unescape(e[1]).length<3){p.setAttribute("value",document.location.href)}else{p.setAttribute("value",unescape(e[1]))}f.appendChild(p)}var p=document.createElement("input");if(rs.state=="PLAYING_NO_SELECTION"&&rs.settings.usePost){p.setAttribute("name","html_base64")}else{p.setAttribute("name","selectedhtml_base64")}p.setAttribute("value",rs.base64.encode(c));f.appendChild(p);document.body.appendChild(f);f.submit();document.body.removeChild(f);return rs.settings.protocol+"//"+rs.appserverhost+"/enterprise/iframeproxy.php?rsent="+rs.settings.rsent+"&listen=1&randid="+l},removeiFrames:function(){if(document.getElementById("postiframe1")){document.body.removeChild(document.getElementById("postiframe1"))}if(document.getElementById("postiframe2")){document.body.removeChild(document.getElementById("postiframe2"))}if(document.getElementById("postiframe3")){document.body.removeChild(document.getElementById("postiframe3"))}},readpage:function(b,c){rs.createStylesheet();rs.globalcount=0;rs.getreadids();if(rs.popup.time){clearTimeout(rs.popup.time);rs.popup.setVisibility()}if((navigator.userAgent.toLowerCase().indexOf("iphone")>-1||navigator.userAgent.toLowerCase().indexOf("ipad")>-1||navigator.userAgent.toLowerCase().indexOf("ipod")>-1)&&navigator.vendor.toLowerCase().indexOf("apple")>-1){rs.html5.inUse=true}var d=b.match(/readid=[^&]+/gi);rs.data.current.readid=d[0].replace("readid=","");d=b.match(/speed=[^&]+/gi);if(d){rs.settings.defaultSpeedValue=parseInt(d[0].replace("speed=",""));b=b.replace(/speed=[^&]+/gi,"")}rs.thespeed=rs.convertSpeed(rs.loadSettings("ReadSpeakerHLspeed"));if(rs.state.indexOf("USER_SELECTION")==-1){rs.data.previous.sync=rs.thesync}rs.thesync=rs.loadSettings("ReadSpeakerHL");if(rs.thesync===null||rs.thesync===""){rs.thesync=rs.settings.defaultSync}rs.data.current.playerid=c;if(rs.data.previous.playerid!==null){var e=null;e=document.getElementById(rs.data.previous.playerid);if(e){e.innerHTML="";e.style.display="none"}}rs.data.previous.playerid=rs.data.current.playerid;if((rs.selectedString&&rs.selectedString.length>0)||(rs.selhtml&&rs.selhtml.length>0&&rs.state=="SETTINGS_CHANGED_PLAYING_USER_SELECTION")){rs.cleanup();if(rs.selectedString&&rs.selectedString.length>0){rs.selhtml=rs.selectRange()}if(document.selection){document.execCommand("Unselect")}rs.state="PLAYING_USER_SELECTION"}else{rs.state="PLAYING_NO_SELECTION";if(rs.data.previous.sync!="none"||rs.thesync!="none"){rs.cleanup()}if(rs.settings.usePost){if(rs.data.current.readid!==null){var a=document.getElementById(rs.data.current.readid);if(a!==null){rs.data.current.restorehtml=a.innerHTML}}}}if(rs.html5.inUse){rs.html5.player(b)}else{if(rs.data.current.playerid=="rs_popup_player"){rs.popup.player(b)}else{rs.player(b)}}rs.selectedString=""},player:function(b){if(rs.state!="PLAYING_NO_SELECTION"){var d=encodeURIComponent(rs.issuePOST(b+"&audioformat=flv&sync=user&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version+"&syncalignuser="+rs.thesync,rs.selhtml));var c=rs.issuePOST(b+"&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version,rs.selhtml)}else{if(rs.settings.usePost){var d=encodeURIComponent(rs.issuePOST(b+"&audioformat=flv&sync="+rs.thesync+"&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version,"<div id='"+rs.data.current.readid+"'>"+rs.data.current.restorehtml+"</div>"));var c=rs.issuePOST(b+"&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version,"<div id='"+rs.data.current.readid+"'>"+rs.data.current.restorehtml+"</div>")}else{var d=encodeURIComponent(b+"&audioformat=flv&sync="+rs.thesync+"&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version);var c=b+"&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version}}var e="<div id='rs_playerarea'>";e+="<div style='margin-bottom: 2px; width: 100%; height: "+rs.settings.flash.height+"px;'><span style='position: absolute; top: 0px; left: 0px;'><object id='rsPlayer' "+(document.selection?"classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'":"type='application/x-shockwave-flash' data='"+rs.settings.media.returnSrc("flashSrc")+"?flv="+d+rs.settings.flash.params+"&text_play="+rs.phrases.play+"&text_pause="+rs.phrases.pause+"&text_stop="+rs.phrases.stop+"&text_vol="+rs.phrases.volume+"'")+" style='height:"+rs.settings.flash.height+"px; width:"+rs.settings.flash.width+"px;'>";e+="<param name='movie' value='"+rs.settings.media.returnSrc("flashSrc")+"?flv="+d+rs.settings.flash.params+"&text_play="+rs.phrases.play+"&text_pause="+rs.phrases.pause+"&text_stop="+rs.phrases.stop+"&text_vol="+rs.phrases.volume+"'>";e+="<param name='quality' value='high'><param name='autostart' value='true'>";e+="<param name='allowScriptAccess' value='"+rs.settings.flash.allowScriptAccess+"'><param name='bgcolor' value='#FFFFFF'>";e+="<param name='loop' value='false'></object></span>";e+="<span style='position: absolute; top: 4px; right: 4px;'><a style='border-style: none;' href='JavaScript:void(0);' onclick='rs.closepage(\""+rs.data.current.playerid+"\");return false'><img id='closebr' src='"+rs.settings.media.returnSrc("closeSrc")+"' alt='"+rs.phrases.closeplayer+"' title='"+rs.phrases.closeplayer+"'></a></span></div>";e+="<div id='bottomlinks'><a href='JavaScript:void(0);' class='rs_settings' onclick='rs.showcontrols(\""+b+'","'+rs.data.current.playerid+"\");return false'>"+rs.phrases.settings+"</a> | <a id='rsSaveBtn' href='"+c+"' target='rs' onclick='rs.removeiFrames(); return true;'>"+rs.phrases.nosound+"</a> | "+rs.phrases.speechenabled+"</div>";e+="<div id='controls'></div>";e+="</div>";var a=document.getElementById(rs.data.current.playerid);if(a){a.innerHTML=e;a.style.display="block"}},getreadids:function(){if(rs.data.readid.length===0){var f=document.getElementsByTagName("A");for(var e=0;e<f.length;e++){if(f[e].getAttribute("href")&&f[e].getAttribute("href").indexOf(rs.appserverhost+"/cgi-bin/"+rs.settings.rsent)!=-1&&f[e].id!="rsSaveBtn"&&f[e].id!="rs_selimg"){var c=f[e].getAttribute("href").match(/readid=[^&]+/gi);if(c){var a=c[0].replace("readid=","");rs.data.readid.push(a)}}}if(rs.data.readid.length>0){var d=null;for(var b=0;b<rs.data.readid.length;b++){d=document.getElementById(rs.data.readid[b]);if(d){rs.data.restorehtml.push(d.innerHTML)}}}}},closepage:function(b){if(rs.html5.inUse){rs.html5.controls.stop()}var a=document.getElementById(b);if(a){a.innerHTML="";a.style.display="none"}rs.state="CLOSE";var c=document.getElementById(rs.data.current.readid);if(c&&c.className.toLowerCase().indexOf("rs_reload")>-1||rs.settings.reloadPage){setTimeout("location.reload(true);",500)}else{setTimeout("rs.cleanup();rs.data.current.restorehtml=null;",500);if(document.getElementById("rs_selimg")){document.body.removeChild(document.getElementById("rs_selimg"))}if(document.getElementById("rs_popup_player")){document.body.removeChild(document.getElementById("rs_popup_player"))}}},rshlexit:function(){setTimeout("rs.cleanup();",500)},saveSettings:function(name,content,lifetime){lifetime=parseInt(eval(lifetime));if(lifetime+""=="NaN"){tmpdate=""}else{var thedate=new Date();thedate.setTime(thedate.getTime()+lifetime);thedate=thedate.toGMTString();tmpdate="; expires="+thedate}document.cookie=name+"="+escape(content)+tmpdate},loadSettings:function(a){ckarr=document.cookie;cks=ckarr.split("; ");for(i=0;i<cks.length;i++){cknameval=cks[i].split("=");for(j=0;j<cknameval.length;j++){if(cknameval[j]==a){return unescape(cknameval[j+1])}}}return""},setstyle:function(b){if(b.indexOf("icon")>-1){rs.saveSettings("ReadSpeakerHLicon",b,rs.settings.cookieLifetime)}else{if(b=="slow"||b=="medium"||b=="fast"){rs.saveSettings("ReadSpeakerHLspeed",b,rs.settings.cookieLifetime)}else{rs.saveSettings("ReadSpeakerHL",b,rs.settings.cookieLifetime)}}var a=document.getElementById("controls");if(a!==null){a.innerHTML=""}},showcontrols:function(b,c){var a=document.getElementById("controls");if(a!==null&&a.innerHTML!=""){a.innerHTML="";a.style.display="none";return false}var e=rs.loadSettings("ReadSpeakerHL");var f=rs.loadSettings("ReadSpeakerHLicon");var d=rs.loadSettings("ReadSpeakerHLspeed");if(e==""){e=rs.settings.defaultSync}if(f==""){if(rs.settings.usePopupButton){f=rs.settings.defaultPopup}else{f="iconoff"}}if(d==""){d=rs.settings.defaultSpeed}thestring="<div id='rspopup'><fieldset class='rs_block'><legend>"+rs.phrases.highlightingoptions+"</legend><ul>";thestring+="<li class=";if(e=="wordsent"){thestring+="'rs_active'"}thestring+="><a href='JavaScript:void(0);' onclick='rs.changeSettings(\""+b+'","'+c+'","wordsent");return false;\'>'+rs.phrases.wordsent+"</a></li>";thestring+="<li class=";if(e=="sent"){thestring+="'rs_active'"}thestring+="><a href='JavaScript:void(0);' onclick='rs.changeSettings(\""+b+'","'+c+'","sent");return false;\'>'+rs.phrases.sentonly+"</a></li>";thestring+="<li class=";if(e=="word"){thestring+="rs_active"}thestring+="><a href='JavaScript:void(0);' onclick='rs.changeSettings(\""+b+'","'+c+'","word");return false;\'>'+rs.phrases.wordonly+"</a></li>";thestring+="<li class=";if(e=="none"){thestring+="rs_active"}thestring+="><a href='JavaScript:void(0);' onclick='rs.changeSettings(\""+b+'","'+c+'","none");return false;\'>'+rs.phrases.nohighlighting+"</a></li>";thestring+="</ul></fieldset>";if(rs.settings.usePopupButton){thestring+="<fieldset class='rs_block'><legend>"+rs.phrases.popupbutton+"</legend><ul>";thestring+="<li class=";if(f=="iconon"){thestring+="rs_active "}thestring+="><a href='JavaScript:void(0);' onclick='rs.changeSettings(\""+b+'","'+c+'","iconon");return false;\'>'+rs.phrases.show+"</a></li>";thestring+="<li class=";if(f=="iconoff"){thestring+="rs_active "}thestring+="><a href='JavaScript:void(0);' onclick='rs.changeSettings(\""+b+'","'+c+'","iconoff");return false;\'>'+rs.phrases.hide+"</a></li>";thestring+="</ul></fieldset>"}thestring+="<fieldset class='rs_block'><legend>"+rs.phrases.speed+"</legend><ul>";thestring+="<li class=";if(d=="slow"){thestring+="rs_active "}thestring+="><a href='JavaScript:void(0);' onclick='rs.changeSettings(\""+b+'","'+c+'","slow");return false;\'>'+rs.phrases.slow+"</a></li>";thestring+="<li class=";if(d=="medium"){thestring+="rs_active "}thestring+="><a href='JavaScript:void(0);' onclick='rs.changeSettings(\""+b+'","'+c+'","medium");return false;\'>'+rs.phrases.medium+"</a></li>";thestring+="<li class=";if(d=="fast"){thestring+="rs_active "}thestring+="><a href='JavaScript:void(0);' onclick='rs.changeSettings(\""+b+'","'+c+'","fast");return false;\'>'+rs.phrases.fast+"</a></li>";thestring+="</ul></fieldset></div><div style='clear: both;'></div>";var a=document.getElementById("controls");if(a!==null){a.style.display="block";a.innerHTML=thestring}},changeSettings:function(a,b,c){if(rs.html5.inUse){rs.html5.stop()}if(rs.state=="PLAYING_NO_SELECTION"){rs.state="SETTINGS_CHANGED_PLAYING_NO_SELECTION"}else{rs.state="SETTINGS_CHANGED_PLAYING_USER_SELECTION"}rs.setstyle(c);rs.readpage(a,b)},copyselected:function(a){rs.popup.mouseRel(a);setTimeout("rs.getSelectedHTML()",50);return true},getSelectedHTML:function(){rs.selectedString="";var b=undefined;var a=undefined;if(window.getSelection){a=window.getSelection();if(!a.isCollapsed){if(a.getRangeAt){b=a.getRangeAt(0)}else{b=document.createRange();b.setStart(a.anchorNode,a.anchorOffset);b.setEnd(a.focusNode,a.focusOffset)}if(b){DOM=b.cloneContents();object=document.createElement("div");object.appendChild(DOM.cloneNode(true));rs.selectedString=object.innerHTML}else{rs.selectedString=a}}}else{if(document.selection){a=document.selection;b=a.createRange();if(b&&b.htmlText&&b.text&&b.text.length>0){rs.selectedString=b.htmlText}else{if(b&&b.text){rs.selectedString=b.text}else{rs.selectedString=""}}}else{if(document.getSelection){rs.selectedString=document.getSelection()}}}if(window.getSelection){if(rs.selectedString.length>0&&window.getSelection().getRangeAt(0)&&window.getSelection().getRangeAt(0).toString().length>0){rs.popup.button()}else{rs.selectedString=""}}else{rs.popup.button()}if(rs.selectedString.length>0&&rs.state=="NO_ACTION"){rs.state="USER_SELECTION"}else{if(rs.selectedString.length>0&&rs.state=="PLAYING_USER_SELECTION"){rs.state="USER_SELECTION_WHILE_PLAYING_USER_SELECTION"}else{if(rs.selectedString.length>0&&rs.state=="PLAYING_NO_SELECTION"){rs.state="USER_SELECTION_WHILE_PLAYING_NO_SELECTION"}}}},cleanup:function(){if(document.selection){if(rs.state=="USER_SELECTION"||rs.state=="USER_SELECTION_WHILE_PLAYING_USER_SELECTION"||rs.state=="USER_SELECTION_WHILE_PLAYING_NO_SELECTION"){if(rs.oldwordhl_IE.length>0){for(var c=0;c<rs.oldwordhl_IE.length;c++){rs.oldwordhl_IE[c].className=rs.oldwordhlclass_IE[c]}rs.oldwordhlclass_IE=[];rs.oldwordhl_IE=[]}if(rs.oldsenthl_IE.length>0){for(var c=0;c<rs.oldsenthl_IE.length;c++){rs.oldsenthl_IE[c].className=rs.oldsenthlclass_IE[c]}rs.oldsenthl_IE=[];rs.oldsenthlclass_IE=[]}}if(rs.oldwordhlrange_IE.length>0){for(var c=0;c<rs.oldwordhlrange_IE.length;c++){if(rs.oldwordhlbackcol_IE[c]&&rs.oldwordhlforecol_IE){rs.oldwordhlrange_IE[c].execCommand("backcolor",0,rs.oldwordhlbackcol_IE[c]);if(rs.oldwordhlforecol_IE[c]!==null){rs.oldwordhlrange_IE[c].execCommand("forecolor",0,rs.oldwordhlforecol_IE[c])}}}rs.oldwordhlrange_IE=[];rs.oldwordhlbackcol_IE=[];rs.oldwordhlforecol_IE=[]}if(rs.oldsenthlrange_IE.length>0){for(var c=0;c<rs.oldsenthlrange_IE.length;c++){if(rs.oldsenthlbackcol_IE[c]&&rs.oldsenthlforecol_IE){rs.oldsenthlrange_IE[c].execCommand("backcolor",0,rs.oldsenthlbackcol_IE[c]);if(rs.oldsenthlforecol_IE[c]!==null){rs.oldsenthlrange_IE[c].execCommand("forecolor",0,rs.oldsenthlforecol_IE[c])}}}rs.oldsenthlrange_IE=[];rs.oldsenthlbackcol_IE=[];rs.oldsenthlforecol_IE=[]}if(rs.state!="SETTINGS_CHANGED_PLAYING_USER_SELECTION"&&rs.state!="PLAYING_USER_SELECTION"){rs.selectedWordsRange=[]}}else{if(rs.state=="USER_SELECTION"||rs.state=="USER_SELECTION_WHILE_PLAYING_USER_SELECTION"||rs.state=="USER_SELECTION_WHILE_PLAYING_NO_SELECTION"){var d=document.getElementsByTagName("RS:SPAN");for(c=d.length-1;c>-1;c--){d[c].className=d[c].className.replace("sync_sent_highlighted","");d[c].className=d[c].className.replace("sync_word_highlighted","")}var d=document.getElementsByTagName("RSI:SPAN");for(c=d.length-1;c>-1;c--){d[c].className=d[c].className.replace("sync_sent_highlighted","");d[c].className=d[c].className.replace("sync_word_highlighted","")}}if(rs.state=="PLAYING_USER_SELECTION"||rs.state=="SETTINGS_CHANGED_PLAYING_USER_SELECTION"){if(rs.oldwordhl_FF.length>0){for(var c=0;c<rs.oldwordhl_FF.length;c++){rs.oldwordhl_FF[c].className=rs.oldwordhlclass_FF[c]}rs.oldwordhlclass_FF=[];rs.oldwordhl_FF=[]}if(rs.oldsenthl_FF.length>0){for(var c=0;c<rs.oldsenthl_FF.length;c++){rs.oldsenthl_FF[c].className=rs.oldsenthlclass_FF[c]}rs.oldsenthl_FF=[];rs.oldsenthlclass_FF=[]}}if(rs.state=="CLOSE"||rs.state=="PLAYING_NO_SELECTION"){rs.cleanUpSpans()}}if(rs.state=="CLOSE"||rs.state=="PLAYING_NO_SELECTION"&&rs.data.restorehtml!=null&&rs.data.readid!=null){var b=null;for(var a=0;a<rs.data.readid.length;a++){b=document.getElementById(rs.data.readid[a]);if(b&&rs.data.restorehtml[a]!==null){b.innerHTML=rs.data.restorehtml[a]}}}if(rs.html5.inUse&&rs.oldbodyclass){document.body.className=rs.html5.oldbodyclass}if(rs.state=="CLOSE"){rs.state="NO_ACTION";rs.data.restorehtml=[];rs.data.readid=[]}},rshlsetContent:function(a){rs.newhtml+=a},rshlsetId:function(a){rs.data.current.readid=a},rshlinit:function(){if(rs.state=="PLAYING_USER_SELECTION"||rs.state=="USER_SELECTION_WHILE_PLAYING_USER_SELECTION"){rs.firstrun=1;if(document.selection){document.selection.empty()}else{rs.identifyElementsReplacementNode(document.body)}rs.newhtml=""}else{if(rs.thesync!="none"){var a=null;if(rs.data.current.readid!==null){a=document.getElementById(rs.data.current.readid)}if(a!==null){a.innerHTML=rs.newhtml;rs.newhtml=""}}else{rs.newhtml=""}}rs.removeiFrames()},rshlsync:function(d,f){var a=false;if((d&2)==0){if((d&1)!=0){a=true}else{a=false}}if(rs.state=="PLAYING_USER_SELECTION"||rs.state=="USER_SELECTION_WHILE_PLAYING_USER_SELECTION"){if(document.selection){var e;e=rs.selectedWordsRange[f];if(rs.firstrun==1){rs.firstrun=0;if(rs.oldsenthlrange_IE&&rs.oldsenthlrange_IE.length>0){for(var c=0;c<rs.oldsenthlrange_IE.length;c++){if(rs.oldsenthlbackcol_IE[c]&&rs.oldsenthlforecol_IE){rs.oldsenthlrange_IE[c].execCommand("backcolor",0,rs.oldsenthlbackcol_IE[c]);if(rs.oldsenthlforecol_IE[c]!==null){rs.oldsenthlrange_IE[c].execCommand("forecolor",0,rs.oldsenthlforecol_IE[c])}}}rs.oldsenthlrange_IE=[];rs.oldsenthlbackcol_IE=[];rs.oldsenthlforecol_IE=[]}if(rs.selectedWordsRange&&rs.selectedWordsRange.length>0&&rs.thesync=="wordsent"){for(var c=0;c<rs.selectedWordsRange.length;c++){if(rs.selectedWordsRange[c]){rs.oldsenthlrange_IE.push(rs.selectedWordsRange[c]);rs.oldsenthlbackcol_IE.push(rs.selectedWordsRange[c].queryCommandValue("backcolor"));rs.oldsenthlforecol_IE.push(rs.selectedWordsRange[c].queryCommandValue("forecolor"));if(rs.selectedWordsRange[c].text!=""){rs.selectedWordsRange[c].execCommand("backcolor",0,rs.settings.sentColor);rs.selectedWordsRange[c].execCommand("forecolor",0,rs.settings.textColor)}}}}}if(rs.thesync=="wordsent"||rs.thesync=="sent"||rs.thesync=="word"||rs.thesync==""){if(!a&&rs.oldwordhlrange_IE.length>0){for(var c=0;c<rs.oldwordhlrange_IE.length;c++){if(rs.thesync=="word"||rs.thesync=="sent"){rs.oldwordhlrange_IE[c].execCommand("backcolor",0,rs.oldwordhlbackcol_IE[c])}else{rs.oldwordhlrange_IE[c].execCommand("backcolor",0,rs.settings.sentColor)}rs.oldwordhlrange_IE[c].execCommand("forecolor",0,rs.oldwordhlforecol_IE[c])}rs.oldwordhlrange_IE=[];rs.oldwordhlbackcol_IE=[];rs.oldwordhlforecol_IE=[]}rs.oldwordhlbackcol_IE.push(e.queryCommandValue("backcolor"));rs.oldwordhlforecol_IE.push(e.queryCommandValue("forecolor"));rs.oldwordhlrange_IE.push(e);if(rs.thesync=="sent"){e.execCommand("backcolor",0,rs.settings.sentColor)}else{e.execCommand("backcolor",0,rs.settings.wordColor)}e.execCommand("forecolor",0,rs.settings.textColor)}}else{var e;e=rs.selectedWordsRange["sync"+(f)];if(e.className.replace("word","")!=e.className){if(rs.oldwordhl_FF&&rs.oldwordhl_FF.length>0&&rs.oldwordhlclass_FF&&rs.oldwordhlclass_FF.length>0&&!a){for(var c=0;c<rs.oldwordhl_FF.length;c++){rs.oldwordhl_FF[c].className=rs.oldwordhlclass_FF[c]}rs.oldwordhlclass_FF=[];rs.oldwordhl_FF=[]}if(rs.thesync=="wordsent"||rs.thesync=="sent"||rs.thesync=="word"||rs.thesync==""){rs.oldwordhlclass_FF.push(e.className);rs.oldwordhl_FF.push(e);if(rs.thesync=="sent"){e.className="sync_sent_highlighted"}else{e.className="sync_word_highlighted"}}}if(rs.firstrun==1){rs.firstrun=0;if(rs.oldsenthl_FF.length>0){for(var c=0;c<rs.oldsenthl_FF.length;c++){rs.oldsenthl_FF[c].className=rs.oldsenthlclass_FF[c]}rs.oldsenthl_FF=[];rs.oldsenthlclass_FF=[]}if(rs.thesync!="none"&&rs.thesync!="word"&&rs.thesync!="sent"){var b=rs.returnClassArraySubstring("ffsent"+rs.seltexttimes+" ");for(var c=0;c<b.length;c++){rs.oldsenthl_FF[c]=b[c];rs.oldsenthlclass_FF[c]=rs.oldsenthl_FF[c].className;b[c].className="sync_sent_highlighted"}}}}}else{if(document.selection){var e;e=document.getElementById("sync"+(f));if(e&&e.className.replace("word","")!=e.className){if(!a&&rs.oldwordhl_IE.length>0){for(var c=0;c<rs.oldwordhl_IE.length;c++){rs.oldwordhl_IE[c].className=rs.oldwordhlclass_IE[c]}rs.oldwordhl_IE=[];rs.oldwordhlclass_IE=[]}rs.oldwordhlclass_IE.push(e.className);rs.oldwordhl_IE.push(e);e.className="sync_word_highlighted"}else{if(e&&e.className.replace("sent","")!=e.className){if(!a&&rs.oldsenthl_IE.length>0){for(var c=0;c<rs.oldsenthl_IE.length;c++){rs.oldsenthl_IE[c].className=rs.oldsenthlclass_IE[c]}rs.oldsenthl_IE=[];rs.oldsenthlclass_IE=[]}rs.oldsenthlclass_IE.push(e.className);rs.oldsenthl_IE.push(e);e.className="sync_sent_highlighted"}}}else{var e;e=document.getElementById("sync"+(f));if(e&&e.className.replace("word","")!=e.className){if(!a&&rs.oldwordhl_FF.length>0){for(var c=0;c<rs.oldwordhl_FF.length;c++){rs.oldwordhl_FF[c].className=rs.oldwordhlclass_FF[c]}rs.oldwordhl_FF=[];rs.oldwordhlclass_FF=[]}rs.oldwordhlclass_FF.push(e.className);rs.oldwordhl_FF.push(e);e.className="sync_word_highlighted"}else{if(e&&e.className.replace("sent","")!=e.className){if(!a&&rs.oldsenthl_FF.length>0){for(var c=0;c<rs.oldsenthl_FF.length;c++){rs.oldsenthl_FF[c].className=rs.oldsenthlclass_FF[c]}rs.oldsenthl_FF=[];rs.oldsenthlclass_FF=[]}rs.oldsenthlclass_FF.push(e.className);rs.oldsenthl_FF.push(e);e.className="sync_sent_highlighted"}}}}},selectRange:function(){rs.seltexttimes++;if(document.selection){rs.selectedRange=document.selection.createRange().duplicate();return rs.selectRangeIE(rs.selectedRange,-1)}else{var a="";var b=window.getSelection().getRangeAt(0);rs.start=b.startContainer;rs.stop=b.endContainer;rs.startOffset=rs.modifyOffsetStartOfWord(rs.start,b.startOffset);rs.endOffset=rs.modifyOffsetEndOfWord(rs.stop,b.endOffset);rs.bmindex++;rs.sentbmindex=rs.bmindex;rs.bmindex++;rs.startofsentence=1;a=rs.rangeMarkupGecko(b.commonAncestorContainer);window.getSelection().removeAllRanges();return(a)}},selectRangeIE:function(m){var o=m.duplicate();m.moveStart("word",-1);m.moveStart("word",1);if(!o.isEqual(m)){m.moveStart("word",-1)}o=m.duplicate();m.moveEnd("word",1);m.moveEnd("word",-1);if(!o.isEqual(m)){m.moveEnd("word",1)}var l=m.duplicate();var p=0;var s="";var v=1;var k=m.parentElement();var t=0;l.collapse();var a=l.duplicate();a.collapse(false);var g=0;while(m.inRange(l)&&m.compareEndPoints("EndToEnd",l)!=0){g++;p++;if(p>10000){break}l.collapse(false);l.expand("word",1);var h="";var b="";var r=l.duplicate();r.collapse(false);a.collapse(false);if(a.compareEndPoints("StartToStart",r)==0&&a.compareEndPoints("EndToEnd",r)==0&&r.htmlText==a.htmlText){t++;if(t<2){l.move("character",1)}else{if(t<4){l.move("character",2)}else{if(t<6){l.move("word",1)}else{if(t<8){l.move("word",2)}else{if(t<10){l.move("sentence",1)}else{if(t<12){l.move("sentence",2)}else{break}}}}}}l.collapse(false)}else{t=0;if(g==-1){return l}rs.selectedWordsRange[g]=l.duplicate();var q=l.text;var f=l.htmlText;l.collapse(false);var e;if(v){lastPos=l.duplicate();e=rs.iterateParentTree(lastPos.parentElement(),document.body,k,0)}else{e=rs.iterateParentTree(l.parentElement(),lastPos.parentElement(),k,0)}var c=rs.iterateParentTree(lastPos.parentElement(),l.parentElement(),k,0);var u=l.parentElement();for(var n=0;n<e&&n<20;n++){if(!u.className.match("sync_")){h=u.outerHTML.match("<[^>]*>")[0]+h}u=u.parentElement}var u=lastPos.parentElement();for(var n=0;n<c&&n<20;n++){if(!u.className.match("sync_")){b+="</"+u.tagName+">"}u=u.parentElement}if(e!=0||c!=0){lastPos=l.duplicate()}if(v){s+=h;v=0}else{s+=b+h}if(f.replace("sync_sent_highlighted","")!=""){s+="<rs:span class='sync_user' id='sync"+g+"'>"+q+"</rs:span>"}a=l.duplicate()}}s+="<!-- f -->";var c=rs.iterateParentTree(lastPos.parentElement(),document.body,k,0);var u=lastPos.parentElement();for(var n=0;n<c;n++){if(!u.className.match("sync_")){s+="</"+u.tagName+">"}u=u.parentElement}l.collapse();return(s)},iterateParentTree:function(b,d,f,a){var e=d;while(e!==null){if(b==f){return a}else{if(b==e){return a}}e=e.parentElement}a++;return rs.iterateParentTree(b.parentElement,d,f,a)},rangeMarkupGecko:function(d){rs.firstrun=1;var f="";var c=rs.inc;if(d.nodeType==3){f+=rs.markup(d)}else{var b;var a="";if(d.hasChildNodes()){for(b=0;b<d.childNodes.length;b++){var g=d.childNodes.item(b);if(g==rs.start){rs.inc=1}if(d==rs.start&&b==rs.startOffset){rs.inc=1}if(g.nodeType!=8){a+=rs.rangeMarkupGecko(g)}if(d==rs.stop&&b==rs.endOffset){rs.inc=0;break}if(g==rs.stop){rs.inc=0;break}}}if((c||rs.inc)&&(!d.className||(d.className.replace("sync")==d.className&&d.id.replace("sync")==d.id))){var e=document.createElement("div");e.appendChild(d.cloneNode(true));tempdivtag=e.innerHTML.match("<[^>]*>");if(tempdivtag!==null&&tempdivtag.length>0){f+=tempdivtag[0];f+=a;f+="</"+d.nodeName+">"}else{f+=a}}else{f+=a}if(d.hasChildNodes()){for(b=0;b<d.childNodes.length;b++){if(d==rs.stop&&b==rs.endOffset){rs.inc=0;break}if(g==rs.stop){rs.inc=0;break}}}}return f.replace(/rsi:span/g,"rs:span")},markup:function(e){var b=true;var g="";var c;var f="";var h="";var a=e.nodeValue;if((e==rs.start&&rs.startOffset!=-1)&&(e==rs.stop&&rs.endOffset!=-1)){f=e.nodeValue.substring(0,rs.startOffset);a=e.nodeValue.substring(rs.startOffset,rs.endOffset);h=e.nodeValue.substring(rs.endOffset);rs.inc=1}else{if(e==rs.start&&rs.startOffset!=-1){f=e.nodeValue.substring(0,rs.startOffset);a=e.nodeValue.substring(rs.startOffset);rs.inc=1}else{if(e==rs.stop&&rs.endOffset!=-1){a=e.nodeValue.substring(0,rs.endOffset);h=e.nodeValue.substring(rs.endOffset);rs.inc=1}}}if(!rs.inc){return""}for(var c=0;c<rs.exludednodes.length;c++){if(e.parentNode&&e.parentNode.nodeName.toLowerCase()==rs.exludednodes[c]){b=false;g=rs.htmlencode(a);break}}if(b){g=rs.markupSentences(rs.htmlencode(a),null)}if(e==rs.stop&&rs.endOffset!=-1){rs.inc=0}if(b&&e.parentNode!==null&&a!=""){var d=rs.createreplacementnode(f+g+h,getComputedStyle(e.parentNode,null));e.parentNode.insertBefore(d,e);e.parentNode.removeChild(e)}return g},markupSentences:function(c){var a=rs.splitString(c,new Array(", ",". ","! ","? ",decodeURIComponent("%e3%80%81"),decodeURIComponent("%e3%80%82")));if(!a){return""}var d="";var e=[];for(var b=0;b<a.length;b++){d+="<rsi:span class=' ffsent"+rs.seltexttimes+" cj"+rs.seltexttimes+"' ";d+="id='sync"+(rs.sentbmindex)+"'";rs.startofsentence=0;d+=">";d+=rs.markupWords(a[b]);d+="</rsi:span>"}return d},markupWords:function(c){var d=rs.splitString(c," ");if(!d){return""}var b="";var e=[];for(var a=0;a<d.length;a++){b+="<rsi:span class='sync_user word ck"+rs.seltexttimes+"'";b+=" id='sync"+(rs.bmindex++)+"'";b+=">";b+=(d[a]);b+="</rsi:span>"}return b},trim:function(a){return a.replace(/^\s+|\s+$/g,"")},htmlencode:function(b){var c=document.createElement("div");var a=document.createTextNode(b);c.appendChild(a);return c.innerHTML},identifyElementsReplacementNode:function(b){if(b.tagName&&b.tagName=="RSI:SPAN"&&b.id){rs.selectedWordsRange[b.id]=b}if(b.hasChildNodes()){for(var a=0;a<b.childNodes.length;a++){var c=b.childNodes.item(a);rs.identifyElementsReplacementNode(c)}}},createreplacementnode:function(c,a){var b=document.createElement("rsi:span");b.innerHTML=c;b.setAttribute("class","synctemp cl"+rs.seltexttimes);return b},modifyOffsetStartOfWord:function(b,d){var c=new Array(" ",", ",". ","! ","? ",decodeURIComponent("%e3%80%81"),decodeURIComponent("%e3%80%82"));while(b.nodeValue&&d!=0){for(var a=0;a<c.length;a++){breakstr=c[a];if(b.nodeValue.substring(d-1).indexOf(breakstr)==0){return d}}d--}return d},modifyOffsetEndOfWord:function(b,d){var c=new Array(" ",", ",". ","! ","? ",decodeURIComponent("%e3%80%81"),decodeURIComponent("%e3%80%82"));while(b.nodeValue&&d!=b.nodeValue.length){for(var a=0;a<c.length;a++){breakstr=c[a];if(b.nodeValue.substring(d-1).indexOf(breakstr)==0){return d}}d++}return d},returnClassArraySubstring:function(b){var c=document.getElementsByTagName("*");var d=0;var a=[];for(i=0;i<c.length;i++){if(c[i].className.replace(b,"")!=c[i].className){a[d]=c[i];d++}}return a},cleanUpSpans:function(){var b=document.getElementsByTagName("RSI:SPAN");var a;for(i=b.length-1;i>-1;i--){a=document.createDocumentFragment();for(j=0;j<b[i].childNodes.length;j++){a.appendChild(b[i].childNodes[j].cloneNode(true))}b[i].parentNode.replaceChild(a,b[i])}},splitString:function(h,c){var m=new Array("");var e=0;for(var f=0;f<h.length;f++){for(var d=0;d<c.length;d++){var g=c[d];var a=true;for(var b=0;b<g.length;b++){if(h.length-(f+b)<0||g[g.length-b-1]!=h[f-b-1]){a=false;break}}if(a){e++;m[e]=""}}m[e]+=h[f]}return m},popup:{buttonExists:null,firstRun:true,mouse_y_startpos:null,mouse_x_pos:null,mouse_y_pos:null,player_x_pos:null,player_y_pos:null,time:null,viewportwidth:null,viewportheight:null,button:function(){if(rs.popup.mouse_x_pos&&rs.popup.mouse_y_pos&&rs.selectedString.length>0){if(!document.getElementById("rs_selimg")){if(rs.popup.buttonExists){var a=document.createElement("img");a.setAttribute("src",rs.settings.media.returnSrc("popupSrc"));a.style.border="none";a.setAttribute("alt",rs.phrases.listentoselectedtext);a.setAttribute("title",rs.phrases.listentoselectedtext);var e=document.createElement("a");e.setAttribute("id","rs_selimg");e.setAttribute("href",rs.settings.rscall);e.setAttribute("onclick","clearTimeout(rs.popup.time);rs.popup.setVisibility();"+rs.settings.onclick);e.setAttribute("onmouseover","clearTimeout(rs.popup.time);");e.setAttribute("onmouseout","rs.popup.hideIcon();");e.onclick=new Function("clearTimeout(rs.popup.time);rs.popup.setVisibility();"+rs.settings.onclick);e.onmouseover=new Function("clearTimeout(rs.popup.time);");e.onmouseout=new Function("rs.popup.hideIcon();");e.appendChild(a);var c=document.createDocumentFragment();c.appendChild(e);document.body.appendChild(c)}else{if(rs.popup.firstRun){rs.createStylesheet();rs.popup.firstRun=false;rs.popup.button()}}}if(rs.popup.buttonExists){var b=document.getElementById("rs_selimg");b.style.top=rs.popup.mouse_y_pos+"px";b.style.left=rs.popup.mouse_x_pos+"px";rs.popup.player_y_pos=rs.popup.mouse_y_pos+"px";rs.popup.player_x_pos=rs.popup.mouse_x_pos+"px";var d=rs.loadSettings("ReadSpeakerHLicon");if(d==""){if(rs.settings.usePopupButton){d=rs.settings.defaultPopup}else{d="iconoff"}}if(d=="iconoff"){b.style.display="none"}else{b.style.display="inline"}rs.popup.hideIcon()}}},player:function(b){var f=encodeURIComponent(rs.issuePOST(b+"&audioformat=flv&sync=user&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version+"&syncalignuser="+rs.thesync,rs.selhtml));var d=rs.issuePOST(b+"&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version,rs.selhtml);var e=document.getElementById(rs.data.current.playerid);if(!e){var h=document.createElement("div");h.setAttribute("id","rs_popup_player");var c=document.createDocumentFragment();c.appendChild(h);document.body.appendChild(c);e=document.getElementById(rs.data.current.playerid)}e.style.top=rs.popup.player_y_pos;e.style.left=rs.popup.player_x_pos;var g="<div style='height: "+rs.settings.flash.popupHeight+"px;'><span style='position: absolute; top: 0px; left: 0px;'><object id='rsPlayer' "+(document.selection?"classid='clsid:D27CDB6E-AE6D-11cf-96B8-444553540000'":"type='application/x-shockwave-flash' data='"+rs.settings.media.returnSrc("flashSrc")+"?flv="+f+rs.settings.flash.params+"&rplayer=mini&text_play="+rs.phrases.play+"&text_pause="+rs.phrases.pause+"&text_stop="+rs.phrases.stop+"&text_vol="+rs.phrases.volume+"'")+" style='height:"+rs.settings.flash.popupHeight+"px; width:"+rs.settings.flash.popupWidth+"px;'>";g+="<param name='movie' value='"+rs.settings.media.returnSrc("flashSrc")+"?flv="+f+rs.settings.flash.params+"&rplayer=mini&text_play="+rs.phrases.play+"&text_pause="+rs.phrases.pause+"&text_stop="+rs.phrases.stop+"&text_vol="+rs.phrases.volume+"'>";g+="<param name='quality' value='high'><param name='autostart' value='true'>";g+="<param name='allowScriptAccess' value='"+rs.settings.flash.allowScriptAccess+"'><param name='bgcolor' value='#FFFFFF'>";g+="<param name='loop' value='false'><a id='rsSaveBtn' href='"+d+"' target='rs'>"+rs.phrases.nosound+"</a></object></span>";g+="<span style='position: absolute; top: 4px; right: 4px;'><a href='JavaScript:void(0);' onclick='rs.closepage(\""+rs.data.current.playerid+"\");return false'><img style='border-style: none !important; margin: 0px !important;' src='"+rs.settings.media.returnSrc("closeSrc")+"' title='"+rs.phrases.closeplayer+"' alt='"+rs.phrases.closeplayer+"'></a></span></div>";g+="<span id='rs_popup_link' style='position: absolute; top: 4px; right: 21px;'><a href='JavaScript:void(0);' onclick='rs.showcontrols(\""+b+'","'+rs.data.current.playerid+"\");return false'><img style='border-style: none !important;' src='"+rs.settings.media.returnSrc("settingsSrc")+"' alt='"+rs.phrases.settings+"' title='"+rs.phrases.settings+"'></a></span>";g+="<div id='controls'></div>";var a=document.getElementById(rs.data.current.playerid);if(a){a.innerHTML=g;a.style.display="block"}},hideIcon:function(){if(rs.popup.time){clearTimeout(rs.popup.time)}rs.popup.time=setTimeout("rs.popup.setVisibility()",2500)},setVisibility:function(){if(document.getElementById("rs_selimg")){document.getElementById("rs_selimg").style.display="none"}},mousePress:function(a){if(!a){var a=window.event}a.cancelBubble=true;if(a.stopPropagation){a.stopPropagation()}if(a.pageX||a.pageY){rs.popup.mouse_y_startpos=a.pageY}else{if(a.clientX||a.clientY){rs.popup.mouse_y_startpos=a.clientY+document.body.scrollTop+document.documentElement.scrollTop}}return true},mouseRel:function(a){if(!a){var a=window.event}a.cancelBubble=true;if(a.stopPropagation){a.stopPropagation()}var b=null;var c=null;rs.popup.getViewportSize();if(a.pageX||a.pageY){b=a.pageY;c=a.pageX}else{if(a.clientX||a.clientY){b=a.clientY+document.body.scrollTop+document.documentElement.scrollTop;c=a.clientX+document.body.scrollLeft+document.documentElement.scrollLeft}}if(b>rs.popup.mouse_y_startpos){if((b+20)>(rs.popup.viewportheight-30)){rs.popup.mouse_y_pos=b-40}else{rs.popup.mouse_y_pos=b+20}}else{if((b-40)<10){rs.popup.mouse_y_pos=b+20}else{rs.popup.mouse_y_pos=b-40}}if(c<5){rs.popup.mouse_x_pos=c+30}else{if(c>rs.popup.viewportwidth-80){rs.popup.mouse_x_pos=c-80}else{rs.popup.mouse_x_pos=c+15}}return true},getViewportSize:function(){if(typeof window.innerWidth!="undefined"){rs.popup.viewportwidth=window.innerWidth+document.body.scrollLeft+document.documentElement.scrollLeft;rs.popup.viewportheight=window.innerHeight+document.body.scrollTop+document.documentElement.scrollTop}else{if(typeof document.documentElement!="undefined"&&typeof document.documentElement.clientWidth!="undefined"&&document.documentElement.clientWidth!=0){rs.popup.viewportwidth=document.documentElement.clientWidth+document.body.scrollLeft+document.documentElement.scrollLeft;rs.popup.viewportheight=document.documentElement.clientHeight+document.body.scrollTop+document.documentElement.scrollTop}else{rs.popup.viewportwidth=document.getElementsByTagName("body")[0].clientWidth+document.body.scrollLeft+document.documentElement.scrollLeft;rs.popup.viewportheight=document.getElementsByTagName("body")[0].clientHeight+document.body.scrollTop+document.documentElement.scrollTop}}}},base64:{encode:function(f){newdata=encodeURIComponent(f);var g=0;var e=0;var b=[];for(;g<newdata.length;g++){if(newdata.charCodeAt(g)!="%".charCodeAt(0)){b[e]=newdata.charCodeAt(g)}else{b[e]=parseInt("0x"+newdata.charAt(g+1)+""+newdata.charAt(g+2));g=g+2}e++}var l=b.length;var c=4*parseInt((l+2)/3);var h=new String();var k=parseInt(l/3);var a;var d=l-3*k;g=0;for(;g<3*k;g+=3){a=rs.base64.chars(b[g]>>2);h+=a;a=rs.base64.chars(((b[g]&3)<<4)|(b[g+1]>>4));h+=a;a=rs.base64.chars(((b[g+1]&15)<<2)|(b[g+2]>>6));h+=a;a=rs.base64.chars(b[g+2]&63);h+=a}if(d==1){h+=rs.base64.chars(b[g]>>2);h+=rs.base64.chars((b[g]&3)<<4);h+="=";h+="="}if(d==2){h+=rs.base64.chars(b[g]>>2);h+=rs.base64.chars(((b[g]&3)<<4)|(b[g+1]>>4));h+=rs.base64.chars((b[g+1]&15)<<2);h+="="}return h},decode:function(f){var h="";var d="";var g=0;var c=0;var e="";var b=f.length;while(g<b){if(rs.base64.value(f[g])!=-1){e=f.substring(g,g+4);c=0;for(i=0;i<4;i++){if(e[i]=="="){c++}}for(i=0;i<3-c;i++){if(i==0){d=(rs.base64.value(e[0])<<2)|((rs.base64.value(e[1])&48)>>4)}else{if(i==1){d=((rs.base64.value(e[1])&15)<<4)|((rs.base64.value(e[2])&60)>>2)}else{if(i==2){d=((rs.base64.value(e[2])&3)<<6)|rs.base64.value(e[3])}}}var a=d.toString(16);if(a.length==1){a="0"+a}h+="%"+a}g+=4}else{g++}}return decodeURIComponent(h)},value:function(a){var b=a.charCodeAt(0);if(b>="A".charCodeAt(0)&&b<="Z".charCodeAt(0)){return b-"A".charCodeAt(0)}else{if(b>="a".charCodeAt(0)&&b<="z".charCodeAt(0)){return b-"a".charCodeAt(0)+26}else{if(b>="0".charCodeAt(0)&&b<="9".charCodeAt(0)){return b-"0".charCodeAt(0)+52}else{if(b=="+".charCodeAt(0)){return 62}else{if(b=="/".charCodeAt(0)){return 63}else{return -1}}}}}},chars:function(a){if(a<26){return String.fromCharCode(a+"A".charCodeAt(0))}else{if(a<52){return String.fromCharCode(a+"a".charCodeAt(0)-26)}else{if(a<62){return String.fromCharCode(a+"0".charCodeAt(0)-52)}else{if(a==62){return"+"}else{if(a==63){return"/"}else{return String.fromCharCode(0)}}}}}}},html5:{STATE_BEGIN:0,STATE_USER_START_NOT_READY:1,STATE_USER_START_PLAYING:2,state:0,inUse:false,audioelement:null,lastcurrenttime:-1,currentsyncindex:0,oldbodyclass:null,lastevent:null,lastlastevent:null,lastlastlastevent:null,synclist:new Array(),synclistindex:0,url:null,iframeurl:null,player:function(b){var d=Math.random();if(rs.settings.usePost){rs.html5.url=rs.issuePOST(b+"&requestgrouptype=html5iframe&sync="+rs.thesync+"&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version+"&audioformat=mp3&requestgroup="+d,"<div id='"+rs.data.current.readid+"'>"+rs.data.current.restorehtml+"</div>");rs.html5.iframeurl=rs.issuePOST(b+"&requestgrouptype=html5iframe&sync="+rs.thesync+"&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version+"&audioformat=html5iframe&requestgroupid="+d,"<div id='"+rs.data.current.readid+"'>"+rs.data.current.restorehtml+"</div>");var c=rs.issuePOST(b+"&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version,"<div id='"+rs.data.current.readid+"'>"+rs.data.current.restorehtml+"</div>")}else{rs.html5.url=b+"&requestgrouptype=html5iframe&sync="+rs.thesync+"&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version+"&audioformat=mp3&requestgroup="+d;rs.html5.iframeurl=b+"&requestgrouptype=html5iframe&sync="+rs.thesync+"&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version+"&audioformat=html5iframe&requestgroupid="+d;var c=b+"&speed="+rs.thespeed+"&rsjs_ver="+rs.settings.version}rs.html5.oldbodyclass=document.body.className;document.body.className+=" rs_iphone";var e="<div id='rs_playerarea'><div style='margin-bottom: 2px; width: 100%; height: 20px;'>";e+="<div style='position: absolute; top: 4px; left: 4px;'><a id='rs_playbtn' href='JavaScript:void(0);'><img id='rs_playimg' style='border-style: none !important; margin: 0px !important; padding:0px !important;' src='"+rs.settings.media.returnSrc("playInactiveSrc")+"' title='"+rs.phrases.play+"' alt='"+rs.phrases.play+"'></a></div>";e+="<div style='position: absolute; top: 4px; left: 21px;'><a id='rs_pausebtn' href='JavaScript:void(0);'><img id='rs_pauseimg' style='border-style: none !important; margin: 0px !important; padding:0px !important;' src='"+rs.settings.media.returnSrc("pauseSrc")+"' title='"+rs.phrases.pause+"' alt='"+rs.phrases.pause+"'></a></div>";e+="<div style='position: absolute; top: 4px; left: 38px;'><a id='rs_stopbtn' href='JavaScript:void(0);'><img id='rs_stopimg' style='border-style: none !important; margin: 0px !important; padding:0px !important;' src='"+rs.settings.media.returnSrc("stopSrc")+"' title='"+rs.phrases.stop+"' alt='"+rs.phrases.stop+"'></a></div>";e+="<div id='timeline'><div id='duration'></div><span id='durationtxt'></span></div>";e+="<div style='position: absolute; top: 2px; left: 231px;'><a href='http://www.readspeaker.com/?ref="+encodeURIComponent(document.location.href)+"'><img style='border-style: none !important; margin: 0px !important; padding:0px !important;' src='"+rs.settings.media.returnSrc("iconSrc")+"' title='Go to the ReadSpeaker website' alt='Go to the ReadSpeaker website' /></a></div>";e+="<span style='position: absolute; top: 4px; right: 4px;'><a style='border-style: none;' href='JavaScript:void(0);' onclick='rs.html5.stop();rs.closepage(\""+rs.data.current.playerid+"\");return false'><img style='border-style: none !important; margin: 0px !important;' id='closebr' src='"+rs.settings.media.returnSrc("closeSrc")+"' alt='"+rs.phrases.closeplayer+"' title='"+rs.phrases.closeplayer+"'></a></span></div>";e+="<div id='bottomlinks'><a href='JavaScript:void(0);' class='rs_settings' onclick='rs.showcontrols(\""+b+'","'+rs.data.current.playerid+"\");return false'>"+rs.phrases.settings+"</a> | <a id='rsSaveBtn' href='"+c+"' target='rs'>"+rs.phrases.nosound+"</a> | "+rs.phrases.speechenabled+"</div>";e+="<div id='controls'></div>";e+="</div>";var a=document.getElementById(rs.data.current.playerid);if(a){a.innerHTML=e;a.style.display="block"}rs.html5.start()},start:function(){rs.html5.stop();var a=null;rs.html5.audioelement=new Audio(rs.html5.url);rs.html5.audioelement.setAttribute("controls","controls");rs.html5.audioelement.loop=false;rs.html5.audioelement.play();rs.html5.audioelement.addEventListener("ended",rs.html5.controls.stop,false);rs.html5.audioelement.addEventListener("timeupdate",rs.html5.controls.timer,false);var d=document.getElementById("rs_playbtn");d.addEventListener("click",rs.html5.controls.play,false);var c=document.getElementById("rs_pausebtn");c.addEventListener("click",rs.html5.controls.pause,false);var b=document.getElementById("rs_stopbtn");b.addEventListener("click",rs.html5.controls.stop,false);if(rs.thesync!="none"){if(!document.getElementById("synciframe")){a=document.createElement("iframe");a.setAttribute("id","synciframe")}else{a=document.getElementById("synciframe")}a.src=rs.html5.iframeurl;a.type="text/javascript";a.height=0;a.width=0;a.style.display="none";document.body.appendChild(a);rs.html5.refresh()}else{rs.html5.state=rs.html5.STATE_USER_START_PLAYING}},controls:{play:function(){if(rs.html5.audioelement){rs.html5.audioelement.play()}else{rs.html5.start(rs.html5.url)}if(document.getElementById("rs_playimg")){document.getElementById("rs_playimg").setAttribute("src",rs.settings.media.returnSrc("playInactiveSrc"))}if(document.getElementById("rs_pauseimg")){document.getElementById("rs_pauseimg").setAttribute("src",rs.settings.media.returnSrc("pauseSrc"))}if(document.getElementById("rs_stopimg")){document.getElementById("rs_stopimg").setAttribute("src",rs.settings.media.returnSrc("stopSrc"))}},pause:function(){setTimeout("rs.html5.audioelement.pause()",200);if(document.getElementById("rs_pauseimg")){document.getElementById("rs_pauseimg").setAttribute("src",rs.settings.media.returnSrc("pauseInactiveSrc"))}if(document.getElementById("rs_playimg")){document.getElementById("rs_playimg").setAttribute("src",rs.settings.media.returnSrc("playSrc"))}},stop:function(){if(rs.html5.audioelement){rs.html5.audioelement.removeEventListener("timeupdate",rs.html5.controls.timer,false)}rs.html5.stop();rs.rshlexit();if(document.getElementById("rs_stopimg")){document.getElementById("rs_stopimg").setAttribute("src",rs.settings.media.returnSrc("stopInactiveSrc"))}if(document.getElementById("rs_playimg")){document.getElementById("rs_playimg").setAttribute("src",rs.settings.media.returnSrc("playSrc"))}if(document.getElementById("rs_pauseimg")){document.getElementById("rs_pauseimg").setAttribute("src",rs.settings.media.returnSrc("pauseInactiveSrc"))}var b=document.getElementById("duration");b.style.width="0%";var a=document.getElementById("durationtxt");a.innerHTML="00:00 | 00:00"},timer:function(){if(rs.html5.audioelement){var d=document.getElementById("duration");var l=document.getElementById("durationtxt");var b=rs.html5.audioelement.duration;var f=rs.html5.audioelement.currentTime;d.style.width=f/b*100+"%";if(!b){var g="00";var k="00";var a=""}else{var g=parseInt(b%60)+"";var k=parseInt((b/60)%60)+"";var a=parseInt(((b/60)/60)%60)+""}var n=parseInt(rs.html5.audioelement.currentTime%60)+"";var c=parseInt((rs.html5.audioelement.currentTime/60)%60)+"";var e=parseInt(((rs.html5.audioelement.currentTime/60)/60)%60)+"";if(a!=0){if(a.length<2){a=0+a+":"}if(e.length<2){e=0+e+":"}}else{a="";e=""}if(k.length<2){k=0+k}if(g.length<2){g=0+g}if(c.length<2){c=0+c}if(n.length<2){n=0+n}if(b){l.innerHTML=e+c+":"+n+" | "+a+k+":"+g}else{l.innerHTML="00:00 | 00:00"}}}},stop:function(){if(rs.html5.state!=rs.html5.STATE_BEGIN){if(rs.html5.audioelement){rs.html5.audioelement.pause()}rs.html5.audioelement=null;var a=document.getElementById("synciframe");if(a){a.src="about:blank"}}rs.html5.state=rs.html5.STATE_BEGIN;rs.html5.lastcurrenttime=-1;rs.html5.currentsyncindex=0;rs.html5.synclistindex=0;rs.html5.synclist=[]},receiveMessage:function(a){if(!a.origin.match(/readspeaker.com/gi)){return}if(rs.html5.lastevent!=null&&a.data=="rshlsetContent"){rshlsetContent(rs.base64.decode(rs.html5.lastevent.data))}else{if(rs.html5.lastevent!=null&&a.data=="rshlsetId"){rshlsetId(rs.html5.lastevent.data)}else{if(a.data=="rshlinit"){rshlinit()}else{if(rs.html5.lastlastlastevent!=null&&rs.html5.lastlastevent!=null&&rs.html5.lastevent!=null&&a.data=="rshlsync"){rs.html5.synclist[rs.html5.synclistindex]=[rs.html5.lastlastlastevent.data,rs.html5.lastlastevent.data,rs.html5.lastevent.data];rs.html5.synclistindex++}}}}rs.html5.lastlastlastevent=rs.html5.lastlastevent;rs.html5.lastlastevent=rs.html5.lastevent;rs.html5.lastevent=a},refresh:function(){if(!rs.html5.audioelement){return}if(rs.html5.lastcurrenttime>rs.html5.audioelement.currentTime){rs.html5.controls.stop();return}rs.html5.lastcurrenttime=rs.html5.audioelement.currentTime;if(rs.html5.state==rs.html5.STATE_BEGIN){rs.html5.audioelement.pause();rs.html5.state=rs.html5.STATE_USER_START_NOT_READY;setTimeout("rs.html5.refresh()",0)}else{if(rs.html5.state==rs.html5.STATE_USER_START_NOT_READY){if(rs.html5.synclist[rs.html5.currentsyncindex]&&rs.html5.audioelement.buffered&&rs.html5.audioelement.buffered.length>0&&rs.html5.synclist[rs.html5.currentsyncindex][0]<rs.html5.audioelement.buffered.end(0)*1000){rs.html5.state=rs.html5.STATE_USER_START_PLAYING;rs.html5.audioelement.play();setTimeout("rs.html5.refresh()",0)}else{if(rs.html5.synclist[rs.html5.currentsyncindex]){if(rs.html5.synclist[rs.html5.currentsyncindex][0]*1-rs.html5.audioelement.currentTime*1000<=20){setTimeout("rs.html5.refresh()",0)}else{var a=rs.html5.synclist[rs.html5.currentsyncindex][0]*1-rs.html5.audioelement.currentTime*1000-20;if(a>75){setTimeout("rs.html5.refresh()",75)}else{if(a<0){setTimeout("rs.html5.refresh()",0)}else{setTimeout("rs.html5.refresh()",a)}}}}else{setTimeout("rs.html5.refresh()",75)}}}else{if(rs.html5.state==rs.html5.STATE_USER_START_PLAYING){if(rs.html5.synclist[rs.html5.currentsyncindex]&&rs.html5.synclist[rs.html5.currentsyncindex][0]*1-20<rs.html5.audioelement.currentTime*1000){rshlsync(rs.html5.synclist[rs.html5.currentsyncindex][1],rs.html5.synclist[rs.html5.currentsyncindex][2]);rs.html5.currentsyncindex++;if(rs.html5.synclist[rs.html5.currentsyncindex]){if(rs.html5.synclist[rs.html5.currentsyncindex][0]*1-rs.html5.audioelement.currentTime*1000<=20){setTimeout("rs.html5.refresh()",0)}else{var a=rs.html5.synclist[rs.html5.currentsyncindex][0]*1-rs.html5.audioelement.currentTime*1000-20;if(a>75){setTimeout("rs.html5.refresh()",75)}else{if(a<0){setTimeout("rs.html5.refresh()",0)}else{setTimeout("rs.html5.refresh()",a)}}}}else{setTimeout("rs.html5.refresh()",75)}}else{if(rs.html5.synclist[rs.html5.currentsyncindex]){if(rs.html5.synclist[rs.html5.currentsyncindex][0]*1-rs.html5.audioelement.currentTime*1000<=20){setTimeout("rs.html5.refresh()",0)}else{var a=rs.html5.synclist[rs.html5.currentsyncindex][0]*1-rs.html5.audioelement.currentTime*1000-20;if(a>75){setTimeout("rs.html5.refresh()",75)}else{if(a<0){setTimeout("rs.html5.refresh()",0)}else{setTimeout("rs.html5.refresh()",a)}}}}else{setTimeout("rs.html5.refresh()",75)}}}}}}},createStylesheet:function(){if(document.styleSheets.rsstylesheet||document.getElementById("rsstylesheet")){return}rs.getRsentInfo();var d=document.createElement("style");d.type="text/css";d.setAttribute("id","rsstylesheet");d.setAttribute("name","rsstylesheet");var f=document.getElementsByTagName("head");var a=null;if(f.length>0){a=f.item(0)}if(a){a.appendChild(d)}else{return}var c=parseInt(rs.settings.flash.height)+19;var e=parseInt(rs.settings.flash.popupWidth)+35;changeCSSRule("#rs_playerarea","position: relative !important; border:1px solid #aeaeae !important; background:#ffffff !important; width: "+rs.phrases.playerwidth+"px !important; height: "+c+"px !important; line-height: normal !important; font-style: normal !important;font-family:Arial !important;");changeCSSRule("#rs_playerarea object","max-width: none !important;");changeCSSRule("#closebr","border-style: none !important; margin: 0px !important; padding: 0px !important; ");changeCSSRule("#rs_popup_player","font-family: Arial !important; font-size: 13px !important; width: "+e+"px !important; height: "+rs.settings.flash.popupHeight+"px !important; position: absolute !important; border: 1px solid #aeaeae !important; z-index: 9000 !important; background: #fff !important; -moz-box-shadow: 2px 4px 6px #999 !important; -webkit-box-shadow: 2px 4px 6px #999 !important; -box-shadow: 2px 4px 6px #999 !important; line-height: normal !important;");changeCSSRule("#rs_popup_link a:hover","background-color: #a4cbff !important;");changeCSSRule("#rs_selimg","position: absolute !important; border: 1px solid #aeaeae !important; z-index: 10000 !important; background: #fff !important; padding: 5px !important; -moz-box-shadow: 2px 4px 6px #999 !important; -webkit-box-shadow: 2px 4px 6px #999 !important; -box-shadow: 2px 4px 6px #999 !important; line-height: normal !important;");changeCSSRule("#rspopup","position: absolute !important; z-index: 1000 !important; background: #fff !important; width: 342px !important; border-color: #a4cbff #aeaeae #aeaeae !important; border-right: 1px solid #aeaeae !important; border-style: solid !important; border-width: 1px !important; margin: 3px 3px 3px 0 !important; font-size: 12px !important; font-family: Arial !important; padding: 3px !important; -moz-box-shadow: 0 7px 6px #999 !important; -webkit-box-shadow: 0 7px 6px #999 !important; -box-shadow: 0 7px 6px #999 !important;");changeCSSRule(".rs_block","float: left !important; margin: 0 3px 0 0 !important; padding: 3px !important; border: 1px solid #ddd !important; width: 160px !important; background: none !important; clear: none !important; font-family: Arial !important;");changeCSSRule(".rs_block legend","position: static !important; font-weight: bold !important; color: #000 !important; font-size: 12px !important; text-transform: none !important; letter-spacing: 0px !important; padding: 0px !important; font-style: inherit !important; font-family: Arial !important;");changeCSSRule(".rs_block ul","margin: 0 !important; padding: 0 !important; list-style: none !important; width: 100% !important; font-family: Arial !important;");changeCSSRule(".rs_block li","padding: 0 !important; margin: 0 !important; width: 100% !important; background: none !important; list-style-image: none !important; list-style-type: none !important; font-family: Arial !important;");changeCSSRule(".rs_block li a","text-align: left !important; border-style: none !important; display: block !important; padding: 5px 0 5px 20px !important; text-decoration: none !important; color: #000 !important; font-size: 12px !important; font-family: Arial !important; font-weight: normal !important; width: 87% !important;");changeCSSRule(".rs_block li a:hover","background: #eee !important;");changeCSSRule(".rs_block li.rs_active","background-color: #A4CBFF !important; background-image: -webkit-gradient(linear, left bottom, left top, color-stop(0, rgb(144,192,240)), color-stop(0.79, rgb(229,237,255))) !important; background-image: -moz-linear-gradient(center bottom, rgb(144,192,240) 0%, rgb(229,237,255) 79%) !important;");changeCSSRule(".rs_block li.rs_active a","background: transparent url("+rs.settings.media.returnSrc("arrowSrc")+") no-repeat scroll 10px center !important;");changeCSSRule(".rs_block li.rs_active a:hover","background: transparent url("+rs.settings.media.returnSrc("arrowSrc")+") no-repeat scroll 10px center !important;");changeCSSRule("#bottomlinks","font-family: Arial !important;color:#333 !important;font-size:11px !important; margin: 2px 0 2px 0 !important;");changeCSSRule("#bottomlinks a","color: #000 !important; border-style: none !important; text-decoration: none !important; padding: 2px; font-weight: normal !important; font-size:11px !important; font-family: Arial !important;");changeCSSRule("#bottomlinks a:hover","background-color: #a4cbff !important;");changeCSSRule("a.rs_settings","padding-left: 17px !important; border-style: none !important; background: transparent url("+rs.settings.media.returnSrc("settingsSrc")+") no-repeat 2px 2px !important;");changeCSSRule(".sync_word_highlighted","background-color: "+rs.settings.wordColor+" !important; color: "+rs.settings.textColor+" !important;");changeCSSRule(".sync_sent_highlighted","background-color: "+rs.settings.sentColor+" !important; color: "+rs.settings.textColor+" !important;");changeCSSRule("#timeline","position: absolute !important; top: 4px !important; left: 55px !important; height: 11px !important; background-color: #F4F4F4 !important; width: 170px !important; border: 1px solid #3380CC !important;");changeCSSRule("#duration","position: relative !important; height: 11px !important; background-color: #92C0EF !important; width: 0px;");changeCSSRule("#durationtxt","position: absolute !important; top: 1px !important; left: 0px !important; text-align: center !important; font-size: 10px !important; font-weight: bold !important; font-family: Helvetica !important; color: #003399 !important; line-height: 80% !important; width: 100% !important;");changeCSSRule(".rs_iphone","-webkit-text-size-adjust: none !important;")}};if(document.addEventListener){document.addEventListener("mousedown",rs.popup.mousePress,false);document.addEventListener("keydown",rs.popup.mousePress,false);document.addEventListener("mouseup",rs.copyselected,false);document.addEventListener("keyup",rs.copyselected,false);window.addEventListener("message",rs.html5.receiveMessage,false,true)}else{if(document.attachEvent){document.attachEvent("onmousedown",rs.popup.mousePress);document.attachEvent("onkeydown",rs.popup.mousePress);document.attachEvent("onmouseup",rs.copyselected);document.attachEvent("onkeyup",rs.copyselected)}else{document.onmousedown=rs.popup.mousePress;document.onkeydown=rs.popup.mousePress;document.onmouseup=rs.copyselected;document.onkeyup=rs.copyselected}}function readpage(a,b){rs.readpage(a,b)}function rshlsetContent(a){rs.rshlsetContent(a)}function rshlsetId(a){rs.rshlsetId(a)}function rshlinit(){rs.rshlinit()}function rshlsync(c,e){var d=c.split(",");var a=e.split(",");for(var b=0;b<c.length;b++){rs.rshlsync(d[b],a[b])}}function rshlexit(){rs.rshlexit()}function changeCSSRule(a,b){if(document.styleSheets.rsstylesheet){css=document.styleSheets.rsstylesheet}else{if(document.getElementById("rsstylesheet")){css=document.getElementById("rsstylesheet")}}if(css.addRule){css.addRule(a,b)}else{if(css.sheet.insertRule){css.sheet.insertRule(a+" {"+b+"}",css.sheet.cssRules.length)}}};
