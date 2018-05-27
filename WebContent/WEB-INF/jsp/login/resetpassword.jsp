<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KBMC Reset Password</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />" />
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.8.2.min.js"/>"></script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('form').submit(function(){
			 $('.sys_error').empty();
		});
	});
	(function($, W, D) {
		var JQUERY4U = {};

		JQUERY4U.UTIL = {
			setupFormValidation : function() {
				$("#changepassword").validate({
					rules : {
						username : "required",

						username : {
							required : true,
						},
					},
					messages : {
						username : "Please Enter Username",
						},
						submitHandler : function(form) {
							 $('input[type=submit]').attr('disabled', 'disabled');  
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
	<div class="resetpassword_form">
		<div id="image_div"><img alt="Kohls Backend Management Console"
			src="resources/images/logo.png" class="logo"></div>
		<form name="changepassword" id="changepassword" action="sendNewPassword"
			method='POST'>
			
			<label for="username"> Username </label> <input type="text"
				name="username" id="username" size="30" value=""/><div class="req"></div>
			<c:if test="${not empty message}">
			<div class="sys_error" id="reset_alerts">
				<c:out value="${message}"></c:out>
			</div>
		</c:if>
			<input id="user_submit" name="login" value="Send Instructions"
				type="submit">
		</form>
	</div>
</body>
</html>
