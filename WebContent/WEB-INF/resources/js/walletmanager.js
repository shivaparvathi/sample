$(document).ready(function() {

			$('#create_segment .btn_add').click(function() {
				$("#segForm").css("display", "block");
			});
			$(".btn_add").click(function() {
				$("#editsegForm").css("display", "none");
				$("#segForm").css("display", "block");
			});
			$(".edit_segment").click(function() {
				$("#segForm").css("display", "none");
						$("#editsegForm").css("display", "block");
						var segment_id = $(this).siblings("input[type=hidden]")
								.val();
						var segment_name = $(this).parent().parent().children()
								.first().text();
						var segment_desc = $(this).parent().parent().children()
								.first().next().text();
						$("#editsegForm #segmentid").val(segment_id);
						$("#editsegForm #name").val(segment_name);
						$("#editsegForm #description").val(segment_desc);
			});

			$(".edit").live('click',function() {
				$("label.error").remove();
						$("#edit_userSegments").css('display',
								'block');
						var segment_id = $(this).siblings("input").val();
						$("#edit_userSegments #userUpdateForm #segments").find(
								"option:selected").removeAttr('selected');
						$("#edit_userSegments #userUpdateForm #segments").find(
								"option[value=" + segment_id + "]").attr(
								'selected', 'selected');
						var username = $(this).parent().parent().children()
								.first().text();
						$("#edit_userSegments #userUpdateForm input#user_name").val(
								username);
						var user_id = $(this).siblings("input#user_id").val();
						$("#edit_userSegments #userUpdateForm #user_id").val(user_id);
					});
		});

(function($, W, D) {
	var JQUERY4U = {};

	JQUERY4U.UTIL = {
		setupFormValidation : function() {
			$("#editsegForm").validate({
				rules : {
                	description: {
						required : true,
						specialChars: true
					}
				},
			     messages: {
			    	  description: 
	                	{
	                		required :"Please Enter Description",
	                	}
	                },
				submitHandler : function(form) {
					form.submit();
				}
			});
			
			$("#userUpdateForm").validate({
				rules:{
					segments:{
						required: true
					}	
				},
				messages:{
					segments:{
						required:"Please Select a Segment"
					}
				}
			});
		}
	};

	//when the dom has loaded setup form validation rules
	$(D).ready(function($) {
		JQUERY4U.UTIL.setupFormValidation();
	});
	
	jQuery.validator.addMethod("specialChars", function( value, element ) {
        var regex = new RegExp("^[a-zA-Z0-9 ]+$");
        var key = value;

        if (!regex.test(key)) {
           return false;
        }
        return true;
    }, "Please use only alphanumeric or alphabetic characters");

})(jQuery, window, document);
