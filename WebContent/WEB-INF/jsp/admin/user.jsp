<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KBMC Users</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />" />
<link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.css" />">
<script type="text/javascript"
 src="<c:url value="/resources/js/jquery-1.8.2.min.js"/>">
</script>
<script src="<c:url value="/resources/js/jquery.ui.js"/>"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/css/stylepagination.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/jPages.css"/>">
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/highlight.pack.js"/>">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/tabifier.js"/>">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/js.js"/>">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jPages.js"/>">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/pagination.js"/>">
	
</script>

<script type="text/javascript">
	(function($, W, D) {
		var JQUERY4U = {};

		JQUERY4U.UTIL = {
			setupFormValidation : function() {
				$("#userForm").validate({
					rules : {
						fname : "required",
						lname : "required",
						username : "required",
						
						password : {
							required : true,
						},
						cpassword : {
							required : true,
							equalTo : "#password"
						},

						email : {
						       required : true,
						       email : true
						      },
					},

					messages : {
						fname : "Please Enter First Name",
						lname : "Please Enter Last Name",
						username : "Please Enter Username",
						password : {
							required : "Please Enter Password",
						},
						cpassword : {
							required : "Please Enter Confirm Password",
							equalTo : "Should be same as Password"
						},
						email : {
						       required : "Please Enter Email",
						       email : "please Enter Valid Email"
						      },
					},
					submitHandler : function(form) {
						form.submit();
					}
				});
				$("#userUpdateForm").validate({
					
					rules : {
						fname : "required",
						lname : "required",
						username : "required",
						
						email : {
						       required : true,
						       email : true
						      },
						password : {
							required : true,
						},
						cpassword : {
							required : true,
							equalTo : "#userUpdateForm #password"
						},

					},

					messages : {
						fname : "Please Enter First Name",
						lname : "Please Enter Last Name",
						username : "Please Enter Username",
						password : {
							required : "Please Enter Password",
						},
						cpassword : {
							required : "Please Enter Confirm Password",
							equalTo : "Should be same as Password"
						},

						email : {
						       required : "Please Enter Email",
						       email : "please Enter Valid Email"
						      },
					},
					submitHandler : function(form) {
						form.submit();
					}
				});
			}
		}

		//when the dom has loaded setup form validation rules
		$(D).ready(function($) {
			JQUERY4U.UTIL.setupFormValidation();
		});

	})(jQuery, window, document);
</script>
<script type="text/javascript">
$(document).ready(function() {
	
	 $( "#searchtext" ).autocomplete({
			source: '${pageContext. request. contextPath}/admin/usernameList'
	  }); 
	<c:if test="${not empty errormsg}">
         $('#userForm').css('display','block');
   </c:if>
	$('#delete').live('click', function(){
		var value = confirm("Are you sure you want to delete?");
		  if (value)
		      return true;
		  else
		    return false;
	});
	$('#adduser').live('click',function(){
		$('label.error').remove();
		$("#userForm input[type='text']").val("");
		$("#userForm #selectionrole").val($("#selectionrole option:first").val());
		$("#userForm #status").val($("#status option:first").val());
		$("#userForm input[type='password']").val("");
		$('.errorblock').hide();
		$('#userForm').css('display','block');
		$("#userUpdateForm").css('display','none');

	});
	
	$('#user_submit').click(function() {
		  $('.errorblock').empty();
	});
	
	$(document.body).click(function(){
		$(".successmessage").hide();
	});
	
	if(window.location.href.split('?').length>1){
		var sm=window.location.href.split('?')[1];
		if(sm.split('=')[1]=='true'){
			$("<div>").addClass("successmessage").text("User Created Successfully").insertAfter("div.panel_sub_header_bottom");
		}else if(sm.split('=')[1]=='false'){
			$("<div>").addClass("successmessage").text("Failed to Create the User").insertAfter("div.panel_sub_header_bottom");
		}
	}

});

</script>
</head>
<body>

	<div id="header">
		<div class="wrapper">
			<jsp:include page="../login/header.jsp" />
					</div>
	</div>

	<div id="page_content" class="user">
		<div class="wrapper">

			<div id="user_list">

				<div class="panel_header">Users</div>
				<div class="panel_sub_header_bottom">
					 <form action="searchUser" id="searchUser" method="POST"> 
						<div class="col c0">
							<input type="text" name="searchtext" class="channeltypesearch"
								id="searchtext" value="${search} "> <input type="submit" value=" "
								name="search" id="search" class="search" />
						</div>
				</form> 
				</div>
				<div class="panel_sub_header">
					<c:if test="${not empty successMessage}">
					<div class="successmessage">${successMessage}</div>
					</c:if>
					<div class="col c1">Username</div>
					<div class="col c2">Name</div>
					<div class="col c2">User Role</div>
					<div class="col c3">Status</div>
					<div class="col c4"></div>
				</div>
				<div class="list">
				<div id="message" style="text-align:center;">${message}</div>
					<div id="itemContainer">
						<c:forEach var="userdetails" items="${userdetails}"
							varStatus="status">
							<div class="data">
								<div class="col c1">${userdetails.user.username}</div>
								<div class="col c2">${userdetails.user.firstname},&nbsp;${ userdetails.user.lastname}</div>
								<div class="col c2">${userdetails.userrole}</div>
								<div class="col c3">
									<c:choose>
										<c:when test="${userdetails.user.enabled == true}">Enabled</c:when>
										<c:when test="${userdetails.user.enabled == false}">Disabled</c:when>
									</c:choose>
								</div>

								<div class="col c4">
									<c:choose>
										<c:when test="${userdetails.user.enabled == true}">
											<form
												action="showUpdateUser"
												method="post">

												<input type="hidden" name="id"
													value='<c:out value="${userdetails.user.id}"></c:out>' />
												<input class="imgbutton edit" type="submit" id="edit"
													name="edit" value='' />
											</form>
											<form
												action="deleteUser"
												method="post">
												<input type="hidden" name="id"
													value='<c:out value="${userdetails.user.id}"></c:out>' />
												<input class="imgbutton delete" type="submit" id="delete"
													name="delete" value=' ' />
											</form>
										</c:when>
									</c:choose>
									<c:choose>
										<c:when test="${userdetails.user.enabled == false}">

											<form
												action="showUpdateUser"
												method="post">

												<input type="hidden" name="id"
													value='<c:out value="${userdetails.user.id}"></c:out>' />
												<input class="imgbutton edit" type="submit" id="edit"
													name="edit" value='' />
											</form>
											<div id="edit_btn"></div>
										</c:when>
									</c:choose>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="holder"></div>
			</div>

			<div id="add_user">
				<div id="adduser">
					<div>
						<input class="btn_add" type="submit" name="adduser"
							value="Add User">
					</div>
				</div>
				<br />
				<form name="userForm" id="userForm"
					action="addUser" method="post"
					style="display: none">
					<div class="panel_header">Add User</div>
					<div class="panel">
						<c:if test="${not empty errormsg}">
							<div class="errorblock">User Already Exists</div>
						</c:if>

						<label for="firstname"> First Name </label> 
						<input type="text" id="fname" name="fname" size="30" /> 
						<label for="user_lastname">Last Name </label> 
						<input type="text" id="lname" name="lname" size="30">
						<label for="user_username"> Username </label> 
						<input type="text" id="username" name="username" size="30"> 
						<label for="user_password"> Password </label> 
						<input type="password" id="password" name="password" size="30"> 
						<label for="user_cpassword"> Confirm Password </label> 
						<input type="password" id="cpassword" name="cpassword" size="30">
						<label for="user_email">Email</label>
						<input type="text" id="email" name="email" size="30"/>
						<label for="user_role"> User Role </label> <select name="selectionrole" id="selectionrole">
							<c:forEach var="role" items="${role}" varStatus="status">
								<option value='<c:out value='${role.name}'/>'>${role.name}</option>
							</c:forEach>
						</select> 
						<label for="user_status"> Status </label> 
						<select name="status" id="status">
							<option value="enable">Enable</option>
							<option value="disable">Disable</option>
						</select> 
						<input class="submit" id="user_submit" name="create"
							value="Create" type="submit">

					</div>
				</form>
				<c:choose>
					<c:when test="${update == 'update'}">
						<form name="userForm" id="userUpdateForm"
							action="updateUser"
							method="post" class="userFormUpdate">
							<div class="panel_header">Update User</div>
							<div class="panel">
								<input type="hidden" name="id" value='<c:out value="${user.id}"></c:out>' /> 
								<label for="firstname"> First Name </label> 
								<input type="text" id="fname" name="fname" size="30" value='<c:out value="${user.firstname}"></c:out>' /> 
								<label for="user_lastname"> Last Name </label> 
								<input type="text" id="lname" name="lname" size="30" value='<c:out value="${user.lastname}"></c:out>'> 
								<label for="user_username"> Username </label> 
								<input type="text" id="username" name="username" size="30" value='<c:out value="${user.username}"></c:out>' readonly>
								<label for="user_password"> Password </label> 
								<input type="password" id="password" name="password" size="30" value=''> 
								<label for="user_cpassword"> Confirm Password </label> 
								<input type="password" id="cpassword" name="cpassword" size="30" value=''>
								<label for="user_email">Email</label>
								<input type="text" id="email" name="email" size="30" value='<c:out value="${user.email}"></c:out>'/>
								<label for="user_role"> User Role </label> 
								<select name="selectionrole">
									<option value="${userrolename}" selected>${userrolename}</option>
									<c:forEach var="role" items="${role}" varStatus="status">
										<c:if test="${role.name != userrolename}">
											<option value="${role.name}">${role.name}</option>
										</c:if>
									</c:forEach>
								</select> <label for="user_status"> Status </label> <select name="status">
									<c:choose>
										<c:when test="${user.enabled==false}">
											<option value="disable" selected="selected">Disable</option>
											<option value="enable">Enable</option>
										</c:when>
										<c:when test="${user.enabled==true}">
											<option value="disable">Disable</option>
											<option value="enable" selected="selected">Enable</option>
										</c:when>
									</c:choose>
								</select> 
								<input class="submit" id="user_submit" name="update"
									value="Update" type="submit">

							</div>
						</form>
					</c:when>
				</c:choose>
			</div>

		</div>
	</div>

	<jsp:include page="../login/footer.jsp" />

</body>
</html>
