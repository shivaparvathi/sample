$(document).ready(function() {
	
	//close the popup window
	$("#popup_close img").click( function(e) {
		$("#user_profile_edit").css('display', 'none');
		$("#user_profile_change_passwords").css('display', 'none');
		$('#overlay').remove();
		e.preventDefault();
	});
	
	//save the user information(fistname, lastname and email)
	$("#information").click(function() {
		var status = $('#user_edit_info').valid();
		if(status == true){
			var object = new Object();
			object.firstName = $("#first_name").val();
			object.lastName = $("#last_name").val();
			object.email = $("#email").val();

			var jsonfile = {json : JSON.stringify(object)};
			$.ajax({
				type : 'post',
				url : 'profilechange',
				dataType : 'json',
				async : false,
				data : jsonfile,
				success : function(result) {
					if(result.message == 'success'){
						$('#update_success_msg').css('display','block');
						$('#update_success_msg').fadeOut(4000);
					    $('.profile_first_name span').text($("#first_name").val());
					    $('.profile_last_name span').text($("#last_name").val());
					    $('.profile_email span').text($("#email").val());
					}else{
						$('#update_failure_msg').css('display','block');
						$("#update_failure_msg").fadeOut(4000);
					}
				}
			});
		}
	});

	// edit user information popup window
	$('.popup a').click(function(event) {
		$(this).blur();
		popup_info(event);

	});

	//edit user password popup window
	$('.popup_password a').click(function(event) {
		$(this).blur();
		popup(event);
	});

	$(document).keyup(function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
		} 
		if (e.keyCode == 27) {
			$("#user_profile_edit").css('display', 'none');
			$("#user_profile_change_passwords").css('display', 'none');
			$('#overlay').remove();
		} 
	});
});
	
function popup(event) {
		$('#overlay').remove();
		$("#prev_password").val("");
		$("#newPassword").val("");
		$("#confirm_password").val("");
		$("label.error").remove();
		
		var overlay = $('<div id="overlay"> </div>');
		overlay.fadeIn("slow").appendTo(document.body);
		
		$("#user_profile_change_passwords").appendTo(document.body);
		$("#user_profile_change_passwords").css('display', 'block');

		var h = $(window).height();
		var z = $('#user_profile_change_passwords').css('height');
		z = z.split('px');
		z = z[0];
		
		var top = (h / 2) - (z / 2);

		$("#user_profile_change_passwords").css({
			'position' : 'absolute',
			'top' : top,
			'left' : '38%'
		});
}

function popup_info(event) {
		$("#first_name").val("");
		$("#last_name").val("");
		$("#email").val("");
		$("label.error").remove();
		var overlay = $('<div id="overlay"> </div>');
		overlay.fadeIn("slow").appendTo(document.body);
		$("#user_profile_edit").appendTo(document.body);
		$("#user_profile_edit").css('display', 'block');
		
		var h = $(window).height();
		var z = $('#user_profile_edit').css('height');
		z = z.split('px');
		z = z[0];
		var top = (h / 2) - (z / 2);

		$("#user_profile_edit").css({
			'position' : 'absolute',
			'top' : top,
			'left' : '38%'
		});
}

//validations for password change popup
(function($, W, D) {
		var JQUERY4U = {};

		JQUERY4U.UTIL = {
			setupFormValidation : function() {
				$("#changePassword").validate({
					rules : {
						currentPassword:{
						       required : true,
						},
						newPassword:{
						       required : true,
						},
						confirm_password:{
						       required : true,
						       equalTo : "#changePassword #newPassword"
						}
					},

					messages : {
						currentPassword : {
							required : "Please Enter Current Password",
						},
						newPassword:{
						       required : "Please Enter New Password",
						},
						confirm_password:{
						       required : "Please Enter New Password",
						       equalTo: "Enter the same password as above"
						}
						
					},
					submitHandler : function(form) {
						form.submit();
					}
				});
				
				$("#user_edit_info").validate({
					
					rules : {
						firstName:{
							   required : true
							   },
						lastName:{
						      required : true
							   },		 
						email : {
						      required : true,
						      email : true
						     }
					},

					messages : {
		                  firstName: {
			                 required :"Please Enter First Name"
		                  },
		                  lastName: {
				                 required :"Please Enter Last Name"
			              },  
			              email: {
							required : "Please Enter Email",
							email : "please Enter Valid Email"
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

})(jQuery, window, document);


