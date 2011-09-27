jQuery(function($) {
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
	            	console.log("create");
	            }
	        },
	        deleteItem: { // The "delete" menu item
	            label: "Delete",
	            action: function () {
	            	console.log("delete");
	            }
	        }
	    };

	    /*if ($(node).hasClass("folder")) {
	        // Delete the "delete" menu item
	        delete items.deleteItem;
	    }*/

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
			"items": jsTreeContextMenu
		}
	}).bind("move_node.jstree",function(event, data) {
        console.log("o : " + data.rslt.o.attr("id"));
        console.log("r : " + data.rslt.r.attr("id"));
        console.log("p : " + data.rslt.p);
 });
});
