<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KBMC User Segments</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />" />
 <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.css"/>">
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.8.2.min.js"/>">
</script>
<script src="<c:url value="/resources/js/jquery.ui.js"/>"></script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/css/stylepagination.css"/>">
<link rel="stylesheet" href="<c:url value="/resources/css/jPages.css"/>">
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
<script type="text/javascript"
	src="<c:url value="/resources/js/walletmanager.js"/>">
	
</script>
<script type="text/javascript">
	$(document).ready(function(){
		
		var searchURL = 'walletManager/getClientUserNames';
	    <c:if test="${user_role=='[ROLE_Administrator]'}">
	      searchURL = '';
		  searchURL = 'admin/getClientUserNames';
	     </c:if>
		$( "#searchtext" ).autocomplete({
			  source: '${pageContext. request. contextPath}/'+searchURL
		 }); 
		
		<c:if test="${not empty updatefailuremsg}">
        $('#edit_segments #userUpdateForm').css('display','block');
  		</c:if>
  		$("#searchtext").keyup(function(event){
  		     if(event.keyCode == 13){
  		         $(".search").click();
  		     }
  		 });
  		$('.search').click(function(){
  	  		$('#search_error').css('display','none');
  	  		var searchString = $.trim($('#searchtext').val());       
	 		searchString=searchString.toLowerCase();
  	  		   $("#itemContainer").remove();  
  	  		   var itemContainer=$('<div>').attr('id','itemContainer');
  	  		   var count=0; 
  	  		  <c:forEach var="registeredUsers" items="${registeredUsers}">
  	  		   	var destination="${registeredUsers.firstName} ${registeredUsers.lastName}";
  	  		   	if((destination.toLowerCase()).indexOf(searchString)!=-1){
  	  				 var datadiv= $('<div>').addClass("data");
  	  				var c1=$('<div>').addClass('col').addClass('c1').text("${registeredUsers.firstName} ${registeredUsers.lastName}");
  	  				var segmentName="${registeredUsers.segmentName}";
  	  				var c2=$('<div>').addClass('col').addClass('c2');
  	  				if(segmentName==""){
  	  				c2=c2.text("Not assigned to any segment");
  	  				}else{
  	  				c2=c2.text("${registeredUsers.segmentName}");
  	  				}
  	  				var c3=$('<div>').addClass('col').addClass('c3');
  	  				var registeredsegmentId=$('<input>').attr('type','hidden').attr('name','id').val("${registeredUsers.segmentId}");
  	  				var registeredUserId=$('<input>').attr('type','hidden').attr('name','id').attr('id','user_id').val("${registeredUsers.id}");
  	  				var updateImage=$('<input>').addClass('imgbutton').addClass('edit').attr('type','submit').attr('id','edit').val("");
  	  				c3=c3.append(registeredsegmentId).append(registeredUserId).append(updateImage);  
  	  				datadiv=datadiv.append(c1).append(c2).append(c3);
  	  				itemContainer.append(datadiv);
  	  				count=count+1; 
  	  		   	}
  	  		  	$(".list").append(itemContainer);	
  	  		  </c:forEach>
  	  		  
  	  		    pagination();  
  	  		  if(count==0&&searchString!=""){
  	  			  $('#search_error').css('display','block');
  	  		  }	
  	  	
  	  	});
  		
  		$(document.body).click(function(){
  			$(".successmessage").hide();
  		});
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
			
		
			<div id="sub_menu">
				<div id="users" class="selected">
					<a href="getUserSegments" id="edit_users">Manage User Segments</a>
				</div>
				<br /> <br /> <br />
				<div id="segments" class="sub_menu">
					<a href="getSegments" id="add_segments">Add/Edit Segments</a>
				</div> 
			</div>
			
			<!-- Registered users Manage Users -->
			
			<div id="users_list">
				<div class="panel_header">Users</div>
				<div class="panel_sub_header_bottom">
					
						<div class="col c0">
							<input type="text" name="searchtext" class="usersegmentsearch"
								id="searchtext" value=" "> <input type="submit" value=" "
								name="search" id="search" class="search" />
						</div>
					
				</div> 
				<div class="panel_sub_header">
					<c:if test="${not empty successMessage}">
					<div class="successmessage">${successMessage}</div>
					</c:if>
					<div class="col c1">Username</div>
					<div class="col c2">Segment</div>
					<div class="col c3"></div>
				</div>
				<div class="list">
					<div id="itemContainer">
					 <c:if test="${fn:length(registeredUsers) eq 0}"><div style="text-align:center;">No Users Available</div></c:if>
						<c:forEach var="registeredUsers" items="${registeredUsers}"
							varStatus="status">
							<div class="data">
								<div class="col c1">${registeredUsers.firstName} ${registeredUsers.lastName}</div>
								<div class="col c2">
								<c:choose>
								<c:when test="${registeredUsers.segmentName==''}">
								Segment Not Assigned
								</c:when>
								<c:when test="${registeredUsers.segmentName!=null}">
								${registeredUsers.segmentName}
								</c:when>
								</c:choose>
								</div>
								<div class="col c3">
									<input type="hidden" name="id"
										value='<c:out value="${registeredUsers.segmentId}"></c:out>' />
										<input type="hidden" id="user_id" name="id" value='<c:out value="${registeredUsers.id}"></c:out>' />
									<input class="imgbutton edit" type="submit" id="edit"
										name="edit" value='' />

								</div>
							</div>
						</c:forEach>
					</div>
					<div id="search_error">No Such User Exists</div>
				</div>
				<div class="holder"></div>
			</div>
			<div id="edit_userSegments" style="display:none">
				<form name="userForm" id="userUpdateForm"
					action="updateUserSegment"
					method="post" class="userFormUpdate">
					<div class="panel_header">Update User Segment</div>
					<div class="panel">
						<c:if test="${not empty updatefailuremsg}">
									<div class="errorblock">Update of User Segment failed.</div>
						</c:if>
						<label for="user_name">User name</label> <input type="text"
							id="user_name" value="${userSegment.firstName} ${userSegment.lastName}" name="user_name" readonly="readonly"></input>
						<input type="hidden" id="user_id" name="user_id" value="${userSegment.id}" />
						<label for="segments">Segments</label> <select id="segments"
							name="segments">
							<option value=''>Select Segment</option>
							 <c:if test="${fn:length(segments) eq 0}"><option value=''>No Segments Available</option></c:if>
							<c:forEach var="segments" items="${segments}" varStatus="status">
								<option value="${segments.id}">${segments.name}</option>
							</c:forEach>
						</select> <input class="submit" id="user_submit" name="update"
							value="Update" type="submit">
					</div>
				</form>
			</div>
		</div>
	</div>

	<jsp:include page="../login/footer.jsp" />
</body>
</html>
