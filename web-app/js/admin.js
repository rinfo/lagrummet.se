jQuery(function($) {

	tinyMCE.init({
		theme : "advanced",
		mode : "exact",
		elements : "content",
		theme_advanced_toolbar_location : "top"
	
	});
	
	$("#pageTree").jstree({"plugins" : ["themes","html_data","ui","crrm"]}) //,"ui"
    // 1) if using the UI plugin bind to select_node
   .bind("select_node.jstree", function (event, data) {
       
           // `data.rslt.obj` is the jquery extended node that was clicked
           // alert(data.rslt.obj.attr("href"));
   })
   // 2) if not using the UI plugin - the Anchor tags work as expected
   //    so if the anchor has a HREF attirbute - the page will be changed
   //    you can actually prevent the default, etc (normal jquery usage)
   .delegate("a", "click", function (event, data) {
       event.preventDefault(); 
       var url = $(this).attr("href");
         console.log('Load was performed. ' + url);
       });
   });
	
});