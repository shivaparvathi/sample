<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KBMC Login</title>

<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />" />

<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.8.2.min.js"/>">
	
</script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
$('#username').focus(function(){
	$('#change_confirm').remove();
});

$('#password').focus(function(){
	$('#change_confirm').remove();
});
});
</script>


</head>
<body>

	<div class="login_form">
		<img alt="Kohls Backend Management Console"
			src="resources/images/logo.png" class="logo">
		<c:if test="${not empty message}">
			<div class="success" id="change_confirm">
				<c:out value="${message}"></c:out>
			</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="success" id="change_confirm">
				<c:out value="${msg}"></c:out>
			</div>
		</c:if>
		
		<form name='f' action="<c:url value='j_spring_security_check'  />"
			method='POST'>

			<label for="username"> Username </label> <input type="text"
				name="j_username" id="username" size="30" />
			<div class="req">*</div>

			<label for="user_password"> Password </label> <input type="password"
				name="j_password" id="password" size="30">
			<div class="req">*</div>

			<input id="user_submit" name="login" value="Sign In" type="submit">

			<a href="resetPassword">Forgot Password</a>
			<c:if test="${not empty error}">
				<div class="error">Invalid login credentials</div>
			</c:if>

		</form>

	</div>
</body>
</html>
