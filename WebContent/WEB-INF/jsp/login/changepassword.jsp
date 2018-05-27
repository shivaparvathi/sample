<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KBMC ChangePassword</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />" />
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.8.2.min.js"/>">
	
</script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
<script type="text/javascript">
	(function($, W, D) {
		var JQUERY4U = {};

		JQUERY4U.UTIL = {
			setupFormValidation : function() {
				$("#changepassword").validate({
					rules : {
						password : "required",

						password : {
							required : true,
						},
						confirmpassword : {
							required : true,
							equalTo : "#password"
						},
					},
					messages : {
						password : "Please Enter password",
						confirmpassword : "Please Enter Confirm Password",
						password : {
							required : "Please Enter Password",
						},
						confirmpassword : {
							required : "Please Enter Confirm Password",
							equalTo : "Should be same as Password",
						},
					},
					submitHandler : function(form) {
						form.submit();

					}
				});
			}
		}

		$(D).ready(function($) {
			JQUERY4U.UTIL.setupFormValidation();
		});

	})(jQuery, window, document);
</script>

</head>
<body>
	<div class="changepassword_form">
		<div id="image_div"><img alt="Kohls Backend Management Console"
			src="resources/images/logo.png" class="logo"></div>
		<form name="changepassword" id="changepassword" action="savePassword"
			method='POST'>

			<label for="username"> Username </label> <input type="text"
				name="username" id="username" size="30" value="${username}" readonly />
			<label for="user_password">New Password </label> <input
				type="password" name="password" id="password" size="30">
			<div class="req"></div>
			<label for="user_confirmPassword">Confirm Password </label> <input
				type="password" name="confirmpassword" id="confirmpassword"
				size="30">
			<div class="req"></div>
			<input id="user_submit" name="login" value="Change Password"
				type="submit">
		</form>
	</div>
</body>
</html>
