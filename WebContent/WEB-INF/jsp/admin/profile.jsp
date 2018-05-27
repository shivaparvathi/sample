<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KBMC User Profile</title>

<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />" />
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.8.2.min.js"/>"></script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>


<script type="text/javascript"
	src="<c:url value="/resources/js/profile.js"/>"></script>
<script type="text/javascript">
$("document").ready(function(event){
	<c:if test="${not empty errormsg}">
		popup(event);
	</c:if>	
	<c:if test="${not empty message}">
	$("#password_success_msg").css('display','block');
    $("#password_success_msg").fadeOut(4000);
	</c:if>	
});
</script>	

</head>
<body>

	<div id="header">
		<div class="wrapper">
			<jsp:include page="../login/header.jsp" />
		</div>
	</div>
	<div id="page_content">
		<div class="wrapper">
		<div id="password_success_msg" class="success_msg" style="display: none;">Password changed successfully</div>
		<div id="content_profile">
				<div id="content_left">
					<div id="basic_info" class="top">
						<div class="main_heading">Basic Information</div>
						<div class="basic_information">
							<div class="profile_user_name">
								<label>User Name</label><span>${profile.username}</span>
							</div>
							<div class="profile_first_name">
								<label>First Name</label><span>${profile.firstname}</span>
							</div>
							<div class="profile_last_name">
								<label>Last Name</label><span>${profile.lastname}</span>
							</div>
							<div class="profile_email">
								<label >Email</label><span>${profile.email}</span>
							</div>
							<div class="profile_role">
								<label >Role</label><span>${profile.role}</span>
							</div>
							<div class="profile_update_actions">
								<div class="popup">
									<a href="javascript:void(0)">Edit</a>
								</div>
								<div class="popup_password">
									<a href="javascript:void(0)">Change Password</a>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="user_profile_edit" style="display: none">
		<div class="panel_header">
			Edit Information
			<div id="popup_close">
				<img src="../resources/images/cancel.jpeg" />
			</div>
		</div>
		<div class="panel">
			<form id="user_edit_info">
				<label for="firstname"> First Name </label> <input id="first_name"
					type="text" name="firstName">
				<label for="lastname"> Last Name </label> <input id="last_name"
					type="text" name="lastName">
				
				<label for="Email">Email</label>
				<input type="text" name="email" id="email"/>
				
				<input id="information" class="submit" type="button" value="Save" name="save">
				<div id="update_success_msg" class="success_msg" style="display: none;">Profile information updated successfully</div>
				<div id="update_failure_msg" class="failure_msg" style="display: none;">Failed to update profile information</div>
			</form>
		</div>
	</div>
	<div id="user_profile_change_passwords" style="display: none">
		<div class="panel_header">
			Change Password
			<div id="popup_close">
				<img src="../resources/images/cancel.jpeg" />
			</div>
		</div>
		<form method="POST" id="changePassword" action="changepassword">
		<div class="panel">
		<c:if test="${not empty errormsg }">
		<div id="errormsg">Please Check your Previous Password</div>
		</c:if>
			<label for="currentPassword"> Current Password </label> <input id="prev_password"
				type="password" name="currentPassword">
			<label for="newPassword"> New Password </label> <input id="newPassword"
				type="password" name="newPassword">
			<label for="confirm_password"> Confirm Password </label> <input id="confirm_password"
				type="password" name="confirm_password">
			<input id="password_change" class="submit" type="submit" value="Change Password" name="changepassword">
		</div>
		</form>	
	</div>
	
<jsp:include page="../login/footer.jsp" />	
</body>
</html>
