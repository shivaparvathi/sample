<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KBMC Channel Types</title>

<link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.css" />">
<script type="text/javascript"
 src="<c:url value="/resources/js/jquery-1.8.2.min.js"/>">
</script>
<script src="<c:url value="/resources/js/jquery.ui.js"/>"></script>
<link rel="stylesheet"
	href="<c:url value="/resources/css/stylepagination.css"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/jPages.css"/>" />
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

<link rel="stylesheet"
	href="<c:url value="/resources/css/jqCron.css"/>">                                 

<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />" />
<script type="text/javascript"
	src="<c:url value="/resources/js/channeltypes.js"/>">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jqCron.js"/>">
</script>

<script type="text/javascript">
$(document).ready(function(){
	
	 var searchURL = 'channelManager/channelTypeNameList';
	 <c:if test="${user_role=='[ROLE_Administrator]'}">
	      searchURL = '';
		  searchURL = 'admin/channelTypeNameList';
	 </c:if>
		
	 $( "#searchtext" ).autocomplete({
			source: '${pageContext. request. contextPath}/'+searchURL
		}); 
	 
	$("#searchtext").keyup(function(event){
	     if(event.keyCode == 13){
	         $("#search").click();
	     }
	 });
	$('#search').click(function(){
		$('#search_error').css('display','none');
		var searchString = $.trim($('#searchtext').val());       
	 	searchString=searchString.toLowerCase();
		  $("#itemContainer").remove();  
		  var itemContainer=$('<div>').attr('id','itemContainer');
		  var count=0; 

		  <c:forEach var="channelTypes" items="${channelTypes}">
		   	var destination="${channelTypes.name}";
		   	if((destination.toLowerCase()).indexOf(searchString)!=-1){
				var datadiv= $('<div>').addClass("data");
				var c1=$('<div>').addClass('col').addClass('c1').text("${channelTypes.name}");
				var cronExpression="${channelTypes.cronExpression}";
				var c2=$('<div>').addClass('col').addClass('c2');
				if(cronExpression==""){
					c2=c2.text("Not scheduled");	
				}
				else{
					c2=c2.text("${channelTypes.cronExpression}");	
				}
				var c3=$('<div>').addClass('col').addClass('c3').text("${channelTypes.status}");
				var c4=$('<div>').addClass('col').addClass('c4');
				var updateForm=$('<form>').attr('action','showUpdateChannelType').attr('method','post');
				var updateFormFields=$('<input>').attr('type','hidden').attr('name','id').val("${channelTypes.id}");
				var updateImage=$('<input>').addClass('imgbutton').addClass('edit').attr('type','submit').attr('id','edit').val("");
				updateForm=updateForm.append(updateFormFields).append(updateImage);  
				c4=c4.append(updateForm);
				datadiv=datadiv.append(c1).append(c2).append(c3).append(c4);
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
			<div id="channeltypes_list">
				<div class="panel_header">Channel Types</div>
				<div class="panel_sub_header_bottom">
						<div class="col c0">
							<input type="text" name="searchtext" class="channeltypesearch"
								id="searchtext" value=" "> <input type="submit" value=" "
								name="search" id="search" class="search" />
						</div>
				</div> 
				<div class="panel_sub_header">
					<c:if test="${not empty successMessage}">
					<div class="successmessage">${successMessage}</div>
					</c:if>
					<div class="col c1">Name</div>
					<div class="col c2">Cron Expression</div>
					<div class="col c3">Status</div>
					<div class="col c4"></div>
				</div>
				<div class="list">
					<div id="itemContainer">
						<c:if test="${fn:length(channelTypes) eq 0}">
							<div style="text-align: center;">No Channel Types Available</div>
						</c:if>
						<c:forEach var="channelTypes" items="${channelTypes}"
							varStatus="status">
							<div class="data">
								<div class="col c1">${channelTypes.name}</div>
								<c:choose>
								<c:when test="${channelTypes.cronExpression==''}">
									<div class="col c2">Not scheduled</div>
								</c:when>
								<c:when test="${channelTypes.cronExpression!=null}">
									<div class="col c2">${channelTypes.cronExpression}</div>
								</c:when>
								</c:choose>
								<div class="col c3">${channelTypes.status}</div>
								<div class="col c4">
											<form
												action="showUpdateChannelType"
												method="post">
												<input type="hidden" name="id"
													value='${channelTypes.id}' />
												<input class="imgbutton edit" type="submit" id="edit"
													name="edit" value='' />
											</form>									
								</div>
							</div>
						</c:forEach>
					</div>
					<div id="search_error">No such Channel Type Exists</div>
				</div>
				<div class="holder"></div>
			</div>
				<c:choose>
					<c:when test="${update == 'update'}">
						<form name="channelTypesUpdateForm" id="channelTypesUpdateForm"
							action="updateChannelType"
							method="post" class="channelTypesUpdateForm">
							<div class="panel_header">Update Channel Type</div>
							<div class="panel">
								<c:if test="${not empty updatefailuremsg}">
									<div class="errorblock">ChannelType Not Updated SuccessFully</div>
								</c:if>
								<input type="hidden" name="id" value="${channeltypeobj.id}" />
								<label for="name">Name </label> 
								<input type="text" id="name" name="name" size="30" value='<c:out value="${channeltypeobj.name}"></c:out>' readonly /> 
								<label for="cron_expression">Cron Expression</label>
								<input type="text" id="cron_expression" title="Min-Hour-DayOfMonth-Month-DayOfWeek/Year" name="cron_expression" size="30" value='<c:out value="${channeltypeobj.cronExpression}"></c:out>' readonly="readonly" >
								<label  id="cron_error"></label>
								<label for="user_status">Status </label>
								<select name="status">	
									<c:choose>
									<c:when test="${channeltypeobj.status=='disabled'}">
										<option value="ENABLED">Enable</option>
										<option value="DISABLED" selected="selected">Disable</option>
									</c:when>
									<c:when test="${channeltypeobj.status=='enabled'}">
										<option value="ENABLED" selected="selected">Enable</option>
										<option value="DISABLED">Disable</option>
									</c:when>
								</c:choose>	 								
								</select>
								<label for="parameters">Parameters</label>
								<input type="text" id="parameters" name="parameters" size="30" value='<c:out value="${channeltypeobj.parameters}"></c:out>' >
								<label for="backlog"> Backlog</label>
								<c:choose>
        						 <c:when test="${channeltypeobj.backlog==0}">
          						 <input type="text" id="backlog" name="backlog" size="30" value='1' >
        						 </c:when>
         						 <c:when test="${channeltypeobj.backlog!=0}">
         						 <input type="text" id="backlog" name="backlog" size="30" value='<c:out value="${channeltypeobj.backlog}"></c:out>' >
        						 </c:when>
       							</c:choose>
								<input class="submit" id="submit" name="update"
									value="Update" type="submit">
							</div>
						</form>
					</c:when>
				</c:choose>
				<div id="ui-cronexpression-div" class="ui-cronexpression ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
				<div id="selector"></div>
				</div>
			</div>
		</div>
	<jsp:include page="../login/footer.jsp" />
</body>
</html>
