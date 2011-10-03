jQuery(function($) {
	var serverUrl = $('meta[name=serverURL]').attr("content") + "/";
	var newNodeParentId;
	
	function flashMessage(message) {
		console.log(message);
	}
	
	function errorMessage(message) {
		console.log(message);
	}

	tinyMCE.init({
		theme : "advanced",
		mode : "exact",
		elements : "content",
		theme_advanced_toolbar_location : "top"
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
	
	$("#pageTree").jstree({"plugins" : ["themes","html_data","crrm", "dnd", "contextmenu"],	//,"ui"
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
		var title = $(node).find("a").text();
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
	
	// Dynamic behaviour for creating and editing pages
	$("#h1").focus().blur(function(e) {
		if (!$("#bodyContent form .content .permalink input").val()) {
			firstH1Blur = false;
			$("#title").val($(this).val());
			$("#bodyContent form .content .permalink input").val($(this).val().replace(/ /g, "-").toLowerCase()).parent().show();
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
});