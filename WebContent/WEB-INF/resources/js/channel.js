$(document).ready(function() {
	
	
	$.fn.multiselect = function() {
		$(this).each(function() {
			var checkboxes = $(this).find("input:checkbox");
			checkboxes.each(function() {
				var checkbox = $(this);
				if (checkbox.attr("checked"))
					checkbox.parent().addClass("multiselect-on");
	 
				checkbox.click(function() {
					if (checkbox.attr("checked"))
						checkbox.parent().addClass("multiselect-on");
					else
						checkbox.parent().removeClass("multiselect-on");
				});
			});
		});
	};
	
	$(".multiselect").multiselect();
	
	$('#add_channel .btn_add').live('click',function(){
		$("label.error").remove();
		$('#addchannelForm .multiselect input[name="collections"]').prop("checked",false).parent().removeClass("multiselect-on");
		$("#addchannelForm input[type='text']").val("");
		$("#addchannelForm #status").val($("#status option:first").val());
		$("#addchannelForm #channelType").val($("#status option:first").val());
		$('#add_channel #addchannelForm').css('display','block');
		$('#editchannelForm').css('display','none');

	});
	
	$('#addchannelForm  #channel_submit').live('click',function(){
		$('#addchannelForm .errorblock').hide();
		
	});
 $("#edit_channel_submit").click(function(){
  $('.errorblock').hide();
  
 });
	
	$('#channel_list .imgbutton .edit').live('click',function(){
		$('#add_channel #addchannelForm').css('display','none');
	});
	$('#delete').live('click', function(){
		var value = confirm("Are you sure you want to delete?");
		  if (value)
		      return true;
		  else
		    return false;
	});
	
});

(function($, W, D) {
	
	var JQUERY4U = {};

	JQUERY4U.UTIL = {
			
		setupFormValidation : function() {
			$("#add_channel #addchannelForm").validate({

				rules : {
					channelName : "required",
					description : "required",
					imageURL : {
						required:true,
						urlvalidation:true
						},
					thumbnailURL: {
						required:true,
						urlvalidation:true
						}
					
				},
				messages : {
					channelName : "Please enter channel name",
					description : "Please enter channel description",
					imageURL	: {
						
						required: "Please enter image path"
					},
					thumbnailURL: {
						
						required: "Please enter thumbnail image path"
					}
				},
				submitHandler : function(form) {
					form.submit();

				}
			});
			
			
			$("#add_channel #editchannelForm").validate({

				rules : {
					channelName : "required",
					description : "required",
					imageURL : {
						required:true,
						urlvalidation:true
						},
					thumbnailURL: {
						required:true,
						urlvalidation:true
						}
					
				},
				messages : {
					channelName : "Please Enter Channel Name",
					description : "Please Enter Channel Description",
					imageURL	: {
						
						required: "Please enter image path"
					},
					thumbnailURL: {
						
						required: "Please enter thumbnail image path"
					}
				},
				submitHandler : function(form) {
					form.submit();

				}
			});
	
		}
	};
	$(D).ready(function($) {
		JQUERY4U.UTIL.setupFormValidation();
	});
	
	jQuery.validator.addMethod("urlvalidation",
			function(value, element) {
		return /^(http|https):\/\/[^\/.][^ ""]+[^\/.]\.(?:jpg|jpeg|png|bmp|JPEG|JPG|PNG|BMP|Jpg|Jpeg|Png|Bmp)$/i
		  .test(value);
	},"Please Enter Valid Image URL");
})(jQuery, window, document);
