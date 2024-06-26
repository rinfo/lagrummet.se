jQuery(function($) {
	var newNodeParentId;
	var serverUrl = $('meta[name=serverURL]').attr("content") + "/";
	
	function flashMessage(message) {
//		console.log(message);
	}
	
	function errorMessage(message) {
//		console.log(message);
	}

	// The relative "../../" on content_css makes it vulnerable. Must be loaded from a secnd level down .gsp.
	var pageId = ($("#parentId")) ? $("#parentId").attr("value") : "";

	tinymce.init({
		selector: "div.mceEditor > textarea[name='content']",
		theme: "silver",
		forced_root_block : "",
		force_p_newlines : true,
		menubar : false,
		plugins: [
			"advlist autolink link image lists charmap print preview hr anchor pagebreak spellchecker",
			"searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
			"save table directionality emoticons template paste"
		],
		relative_urls : false,
		image_list: serverUrl + "admin/media/list?ajax=true&parentId=" + pageId,
		content_css: "../../css/common.css",
		toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | removeformat visualchars | bullist numlist outdent indent | link anchor image | forecolor backcolor | code fullscreen",
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
	            label: "Skapa ny sida",
	            action: function () {
	            	newNodeParentId = null;
	            	var id = $(node).attr("id").replace("p-", "");
	            	$("#pageTree").jstree("create", node,"insert", "Sidans titel", function() {
	            		newNodeParentId = id;
	            		// The new page is created in during the rename_node event
	            	});
	            }
	        }
	    };
	    return items;
	}
	
	var initiallyOpen = new Array();
	$("#pageTree > ul > li, #pageTree ul li.metaPage").each(function(n) {
		initiallyOpen.push($(this).attr("id"));
	});
	
	$("#pageTree li.currentPage").parents("#pageTree li").each(function(n) {
		initiallyOpen.push($(this).attr("id"));
	});	
	
	
	$("#pageTree").jstree({"plugins" : ["themes","html_data","crrm", "dnd", "contextmenu"],	//,"ui"
		"core" : { "initially_open" : initiallyOpen },
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
	
	// Expand all nodes in the tree
	$("#treeExpandAll").click(function(e) {
		e.preventDefault();
		$("#pageTree").jstree("open_all");
	});
	
	// Dynamic top menu for admin-functions
	$("ul.dropdown li").hover(function(){
	    
        $(this).addClass("hover");
        $('ul:first',this).css('visibility', 'visible');
    
    }, function(){
    
        $(this).removeClass("hover");
        $('ul:first',this).css('visibility', 'hidden');
    
    });
	
	// Dynamic behaviour for creating and editing pages
	$("#h1").blur(function(e) {
		if (!$(this).val()) {
			$(this).css('border-color', 'red');
			return
		}
		$(this).css('border-color', '');
		if (!$("#bodyContent form .content .permalink input").val()) {
			$("#title").val($(this).val());
			$("label[for=title] a").html($(this).val());
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
	
	// Don't send the form when pressing enter in text fields
    $("#bodyContent form input[type=text]").on("keypress", function(e) {
        if (e.which == 13) {
            e.preventDefault();
            $(this).blur();
        }
    });
	
	$("#bodyContent form .content h1 a").click(function(e) {
		e.preventDefault();
		$("#h1").show().focus();
		$(this).parent().hide();
	});
	
	$("#bodyContent form .content .title a").click(function (e) {
		e.preventDefault();
		$(this).hide();
		$("#bodyContent form .content .title input").css('display', 'inline');
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
	
	
	var edit_eventhandler = function(e) {
		e.preventDefault();
		$(this).parents("tr").find(":disabled").prop("disabled", false);
		$(this).parents("td").find("input[value='true']").val("false");
		$(this).parents("tr").removeClass("deleteRow");
		
	}
	
	var delete_eventhandler = function(e){
		if(confirm('Är du säker på att du vill ta bort denna synonym?')) {
			var id = $(this).parent().siblings('input[type=hidden]').val()
			var serverName = $('meta[name=serverURL]').attr('content');
			window.location.replace(serverName+'/admin/synonym/delete?id='+id)
			return true;
		}
		return false;
	}
	
	var deleteUnsavedSynonym = function(e) {
		if(confirm('Är du säker på att du vill ta bort denna synonym?')) {
			$(this).parents("tr").remove();
			return true;
		}
		return false;
	}
	
	$(".editSynonym").click(edit_eventhandler);
	$(".deleteSynonym").click(delete_eventhandler);
	
	$("#addSynonym").click(function(e) {
		e.preventDefault();
		
		var inputRow = '<tr><td><input type="text" size="50" name="synonyms[' + next_index + '].synonym" /></td><td><input type="text" size="50" name="synonyms['+next_index+'].baseTerm" /></td>';
		inputRow += '<td><div class="buttons"><input type="button" class="editSynonym update" value="&nbsp;" /> <input type="button" class="deleteUnsavedSynonym delete" value="&nbsp;" /></div>';
		inputRow += '<input type="hidden" name="synonyms[' + next_index + '].id" value="" disabled="disabled" /></td></tr>';
		next_index++;
		$(".list").find("tbody").prepend(inputRow);
		$(".list").find("tbody").find("tr:first").find(".deleteUnsavedSynonym").click(deleteUnsavedSynonym);
		$(".list").find("tbody").find("tr:first").find(".editSynonym").click(edit_eventhandler);
	});
	
	
	$("#pageEditForm input[type=submit]").click(function(){
		if($(this).prop("id") == "previewSubmit") {
			$("#pageEditForm").attr("target", "_blank");
		} else {
			$("#pageEditForm").attr("target", "");
		}
	});
});

function markPuffAsDeleted(puffIndex) {
	$('#puffs\\['+puffIndex+'\\]\\.deleted').val('true');
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
	$('#puffs\\['+puffIndex+'\\]\\.deleted').val('false');
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