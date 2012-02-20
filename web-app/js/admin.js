jQuery(function($) {
	var newNodeParentId;
	var serverUrl = $('meta[name=serverURL]').attr("content") + "/";
	
	function flashMessage(message) {
//		console.log(message);
	}
	
	function errorMessage(message) {
//		console.log(message);
	}

	var pageId = ($("#parentId")) ? $("#parentId").attr("value") : "";
	tinyMCE.init({
		theme : "advanced",
		mode : "exact",
		elements : "content",
		theme_advanced_toolbar_location : "top",
		force_p_newlines : true,
		relative_urls : false,
		external_image_list_url : serverUrl + "admin/media/list?ajax=true&parentId=" + pageId,
		theme_advanced_resizing : true,
		theme_advanced_resizing_min_height : 480,
		plugins : "advimage",
		extended_valid_elements : "nav[class]",
		entity_encoding : "raw",
		formats : {
            p : {selector : 'p', classes : '', styles: ''}
		}
	});
	
	
	
	function jsTreeContextMenu(node) {
	    // The default set of all items
	    var items = {
	        createItem: { // The "create" menu item
	            label: "Create",
	            action: function () {
	            	newNodeParentId = null;
	            	var id = $(node).attr("id").replace("p-", "");
	            	$("#pageTree").jstree("create", node,"insert", "Sidans titel", function() {
	            		newNodeParentId = id;
	            		// The new page is created in during the rename_node event
	            	});
	            }
	        },
	        deleteItem: { // The "delete" menu item
	            label: "Delete",
	            action: function () {
	            	var id = $(node).attr("id").replace("p-", "");
	            	$.post(serverUrl+"admin/page/delete"+"?ajax=true", {"id":id}, function(data) {
	                    if (data.success) {
	                    	flashMessage(data.success);
	                    	$("#pageTree").jstree("delete_node",node);

	                    } else {
	                    	errorMessage(data.error);
	                    }
	                }, "json");
	            }
	        }
	    };
	    return items;
	}
	
	var metaPageArr = new Array();
	$("#pageTree > ul > li").each(function(n) {
		metaPageArr.push($(this).attr("id"));
	});
	
	
	$("#pageTree").jstree({"plugins" : ["themes","html_data","crrm", "dnd", "contextmenu"],	//,"ui"
		"core" : { "initially_open" : metaPageArr },
		/*"crrm" : { 
			"move" : {
				"check_move" : function (m) { 
					var p = this._get_parent(m.o);
					if(!p) return false;
					p = p == -1 ? this.get_container() : p;
					if(p === m.np) return true;
					if(p[0] && m.np[0] && p[0] === m.np[0]) return true;
					return false;
				}
			}
		},*/
		"dnd" : {
			"drop_target" : false,
			"drag_target" : false
		},
		"contextmenu": {
			"select_node": true,
			"items": jsTreeContextMenu
		}
	}).bind("move_node.jstree",function(event, data) {
        var id = data.rslt.o.attr("id").replace("p-", "");
        var parentId = data.rslt.r.attr("id").replace("p-", "");
        
        $.post(serverUrl+"admin/page/move", {"id": id, "targetId": parentId, "position": data.rslt.p}, function(data) {
            if (data.success) {
            	flashMessage(data.success);
            } else {
            	errorMessage(data.error);
            }
        }, "json");
	}).bind("rename_node.jstree", function (event, data) {
		var node = data.args[0];
		var title = $(node).find("a").text().trim();
		var permalink = title.replace(" ", "-").toLowerCase();
		
		$.post(serverUrl+"admin/page/save"+"?ajax=true", {"title": title, "h1": title, permalink: permalink, parentId: newNodeParentId}, function(data) {
            if (data.success) {
            	flashMessage(data.success);
            	$(node).find("a").attr("href", serverUrl+"admin/page/edit?id="+data.pageInstance.id);
            	
            } else {
            	errorMessage(data.error);
            	$("#pageTree").jstree("delete_node", node);
            }
        }, "json");
    });
	
	// Dynamic top menu for admin-functions
	/*$("#adminFunctions > ul > li").hover(function() {
		// mouse enter
		$(this).children("ul").css('display', 'inline');
	}, function() {
		// mouse leave
		$(this).children("ul").css('display', 'none');
	});
	*/
	$("ul.dropdown li").hover(function(){
	    
        $(this).addClass("hover");
        $('ul:first',this).css('visibility', 'visible');
    
    }, function(){
    
        $(this).removeClass("hover");
        $('ul:first',this).css('visibility', 'hidden');
    
    });
	
	// Dynamic behaviour for creating and editing pages
	$("#h1").focus().blur(function(e) {
		if (!$("#bodyContent form .content .permalink input").val()) {
			firstH1Blur = false;
			$("#title").val($(this).val());
			var value = $(this).val().replace(/ /g, "-").toLowerCase();
			value = value.replace(/[åä]/g, 'a');
			value = value.replace(/[ö]/g, 'o');
			value = value.replace(/[^a-zA-Z 0-9-_]+/g,'');
			$("#bodyContent form .content .permalink input").val(value).parent().show();
		}
		
		$(this).hide();
		$("#bodyContent form .content .title").show();
		$("#bodyContent form .content h1 a").html($(this).val()).parent().show();
		
	});
	
	$("#bodyContent form .content h1 a").click(function(e) {
		e.preventDefault();
		$("#h1").show().focus();
		$(this).parent().hide();
	});
	
	$("#bodyContent form .content .title a").click(function (e) {
		e.preventDefault();
		$(this).hide();
		$("#bodyContent form .content .title input").show();
	});
	
	$("#addNewPuffButton").click(function() {
			var puffCount = parseInt($("#puffCount").text());
			
			for(i = 1; i <= 2; i++) {
				var row1 = $("#puffRow"+i).clone();
				row1.attr("id", "puff_"+puffCount+"_"+i)
				row1.find("input:disabled, select:disabled, textarea:disabled").each(function(index){
					var newName = $(this).prop("name").replace("puffCount", puffCount);
					$(this).prop("name", newName);
					var newId = $(this).prop("id").replace("puffCount", puffCount);
					$(this).prop("id", newId);
				});
				row1.find(":disabled").prop("disabled", false)
				row1.toggleClass("hidden");
				row1.find('[name="deletePuff"]').click(function() {
					markPuffAsDeleted(puffCount-1);
				});
				row1.find('[name="cancelDeletePuffButton"]').click(function() {
					cancelDeletePuff(puffCount-1);
				});
				row1.appendTo("#puffs");
			}
			
			puffCount++;
			$("#puffCount").text(puffCount);
	});
	
	
});

function markPuffAsDeleted(puffIndex) {
	$('#expandablePuffList\\['+puffIndex+'\\]\\.deleted').val('true'); 
	$('#puff_'+puffIndex+'_1').toggleClass("deleteRow"); 
	$('#puff_'+puffIndex+'_2').toggleClass("deleteRow");
	$('#puff_'+puffIndex+'_1').find("input:visible, select, textarea").each(function(index){
		$(this).prop("disabled", true);
	});
	$('#puff_'+puffIndex+'_2').find("input:visible, select, textarea").each(function(index){
		$(this).prop("disabled", true);
	});
	$('#puff_'+puffIndex+'_2').find(".buttons").each(function(index){
		$(this).toggleClass("hidden");
	});
}

function cancelDeletePuff(puffIndex) {
	$('#expandablePuffList\\['+puffIndex+'\\]\\.deleted').val('false'); 
	$('#puff_'+puffIndex+'_1').toggleClass("deleteRow"); 
	$('#puff_'+puffIndex+'_2').toggleClass("deleteRow");
	$('#puff_'+puffIndex+'_1').find("input:visible, select, textarea").each(function(index){
		$(this).prop("disabled", false);
	});
	$('#puff_'+puffIndex+'_2').find("input, select, textarea").each(function(index){
		$(this).prop("disabled", false);
	});
	$('#puff_'+puffIndex+'_2').find(".buttons").each(function(index){
		$(this).toggleClass("hidden");
	});
}