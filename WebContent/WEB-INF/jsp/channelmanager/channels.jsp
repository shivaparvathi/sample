<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KBMC channels</title>

<link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.css" />">
<script type="text/javascript"
 src="<c:url value="/resources/js/jquery-1.8.2.min.js"/>">
</script>
<script src="<c:url value="/resources/js/jquery.ui.js"/>"></script>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />" />
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
<script type="text/javascript"
	src="<c:url value="/resources/js/channel.js"/>">
	
</script>
<script type="text/javascript">

$(document).ready(function() {
	
	 var searchURL = 'channelManager/channelNameList';
	    <c:if test="${user_role=='[ROLE_Administrator]'}">
	      searchURL = '';
		  searchURL = 'admin/channelNameList';
	     </c:if>
		 $( "#searchtext" ).autocomplete({
				source: '${pageContext. request. contextPath}/'+searchURL
		  }); 
	<c:if test="${not empty errormsg}">
	$('#addchannelForm').css('display', 'block');
	</c:if>

	<c:if test="${not empty createfailuremsg}">
	$('#addchannelForm').css('display', 'block');
	</c:if>
	
	<c:if test="${not empty updatefailuremsg}">
	$('#editchannelForm').css('display', 'block');
	</c:if>
	
	<c:if test="${not empty channelobj}">
	$('#editchannelForm .multiselect input[name="collections"]').prop("checked",false).parent().removeClass("multiselect-on");
	var collectionId=${channelobj.collectionId};
	var collectionLength=collectionId.length;
	for ( var i = 0; i <collectionLength ; i++) {
		$('#editchannelForm .multiselect input[name="collections"][value="'+ collectionId[i]+'"]').prop("checked",true).parent().addClass("multiselect-on");
	}
	</c:if>
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
		 
		  <c:forEach var="channels" items="${channels}">
		   	var destination="${channels.name}";
		   	if((destination.toLowerCase()).indexOf(searchString)!=-1){
				 var datadiv= $('<div>').addClass("data");
				var c1=$('<div>').addClass('col').addClass('c1').text("${channels.name}");
				var c2=$('<div>').addClass('col').addClass('c2').text("${channels.channelType}");
				var c3=$('<div>').addClass('col').addClass('c3').text("${channels.status}");
				var c4=$('<div>').addClass('col').addClass('c4');
				var updateForm=$('<form>').attr('action','showUpdateChannel').attr('method','post');
				var updateFormFields=$('<input>').attr('type','hidden').attr('name','channelId').val("${channels.channelId}");
				var updateImage=$('<input>').addClass('imgbutton').addClass('edit').attr('type','submit').attr('id','edit').val("");
				updateForm=updateForm.append(updateFormFields).append(updateImage);  
				var deleteForm=$('<form>').attr('action','deleteChannel').attr('method','post');
				var deleteFormFields=$('<input>').attr('type','hidden').attr('name','channelId').val("${channels.channelId}");
				var deleteImage=$('<input>').addClass('imgbutton').addClass('delete').attr('type','submit').attr('id','delete').val("");
				deleteForm=deleteForm.append(deleteFormFields).append(deleteImage);
				c4=c4.append(updateForm).append(deleteForm);
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
		
		<div id="channels_list">

				<div class="panel_header">Channels</div>
				<div class="panel_sub_header_bottom">
					<div class="col c0">
						<input type="text" name="searchtext" class="locationsearch" id="searchtext" value=" "> <input type="submit" value=" "
								name="search" id="search" class="search" />
					</div>
				</div> 
				<div class="panel_sub_header">
					<c:if test="${not empty deletefailuremsg}">
						<div class="errorblock">${deletefailuremsg}</div>
					</c:if>
					<c:if test="${not empty successMessage}">
					<div class="successmessage">${successMessage}</div>
					</c:if>
					<div class="col c1">Channel Name</div>
					<div class="col c2">Channel Type</div>
					<div class="col c3">Status</div>
					<div class="col c4"></div>
				</div>
				<div class="list">
					<div id="itemContainer">
						 <c:if test="${fn:length(channels) eq 0}"><div style="text-align:center;">No Segments Available</div></c:if>
						<c:forEach var="channels" items="${channels}"
							varStatus="status">
							<div class="data">
								<div class="col c1">${channels.name}</div>
								<div class="col c2">${channels.channelType}</div>
								<div class="col c3">${channels.status}</div>
								<div class="col c4">
								<form action="showUpdateChannel" method="post">
									<input type="hidden" name="channelId" value='<c:out value="${channels.channelId}"></c:out>' />
									<input class="imgbutton edit" type="submit" id="edit" value=""/>
								</form>		
								<form action="deleteChannel" method="post"> 
									<input type="hidden" name="channelId" value='<c:out value="${channels.channelId}"></c:out>' />
									<input class="imgbutton delete" type="submit" id="delete" name="delete" value=' ' />
								</form>	
								</div>
							</div>
					 	 </c:forEach>  
					</div>
					<div id="search_error">No such channel exists</div>
				</div>
				<div class="holder"></div>
			</div>
			<div id="add_channel">
				<div id="addchannel">
					<div>
						<input class="btn_add" type="submit" name="addchannel"
							value="Add Channel">
					</div>
				</div>
				<form name="addchannelForm" id="addchannelForm"
					action="createChannel" method="post"
					style="display: none">
					<div class="panel_header">Create Channel</div>
					<div class="panel">
						<c:if test="${not empty errormsg}">
							<div class="errorblock">Channel Already Exists</div>
						</c:if>
						<c:if test="${not empty createfailuremsg}">
							<div class="errorblock">Channel Not Created SuccessFully</div>
						</c:if>
						<label for="channelName">Channel Name</label> 
						<input type="text" id="channelName" name="channelName" size="40" /> 
						<label for="channel_status">Status</label>
							<select name="status" id="status">	
								<option value="ENABLED">Enable</option>													
								<option value="DISABLED">Disable</option>
							</select>
						<label for="imageURL">Image URL</label> 
						<input type="text" id="imageUrl" name="imageURL" size="40" />
						<label for="thumbnailURL"> Image Thumbnail </label> 
						<input type="text" id="thumbnailURL" name="thumbnailURL" size="100" /> 
						<label for=description>Description</label> 
						<input type="text" id="description" name="description" size="40" />
						<label for="collections">Collections</label>
							<div class="multiselect">
							<c:forEach var="collections" items="${collections}" varStatus="status">
								<label><input class="collection" type="checkbox" name="collections" value='${collections.collectionId }' id='${collections.collectionId }'/>&nbsp;${ collections.name}</label>
							</c:forEach> 
							</div>
						
						<label for="channelType">Channel Type</label>
						<select	name="channelType" id="channelType">
						<c:forEach var="channelTypes" items="${channelTypes}"
									varStatus="status">
									<option value='<c:out value='${channelTypes.id}_${channelTypes.name}'/>'>${channelTypes.name}</option>
						</c:forEach>
						</select>
						<input class="submit" id="channel_submit" name="create"
									value="Create" type="submit" />
					</div>
				</form>
				<c:choose>
					<c:when test="${update == 'update'}">  
						<form name="editchannelForm" id="editchannelForm"
							action="updateChannel" method="post" class="channelFormUpdate">
							<div class="panel_header">Update Channel</div>
							<div class="panel">
								<c:if test="${not empty updatefailuremsg}">
									<div class="errorblock">Channel Not Updated SuccessFully</div>
								</c:if>
						<input type="hidden" name="channelId" value='<c:out value="${channelobj.channelId}"></c:out>' />
						<label for="channelName">Channel Name</label> 
						<input type="text" id="edit_channelName" name="channelName" size="40" value='<c:out value="${channelobj.name}"></c:out>' /> 
						<label for="channel_status">Status</label>
						<select name="status">	
						<c:choose>
							<c:when test="${channelobj.status=='disabled'}">
								<option value="ENABLED">Enable</option>
								<option value="DISABLED" selected="selected">Disable</option>
							</c:when>
							<c:when test="${channelobj.status=='enabled'}">
								<option value="ENABLED" selected="selected">Enable</option>
								<option value="DISABLED">Disable</option>
							</c:when>
						</c:choose>	 		
						</select>
						<label for="imageURL">Image URL</label> 
						<input type="text" id="edit_imageUrl" name="imageURL" size="40" value='<c:out value="${channelobj.image}"></c:out>' />
						<label for="thumbnailURL"> Image Thumbnail </label> 
						<input type="text" id="edit_thumbnailURL" name="thumbnailURL" size="100" value='<c:out value="${channelobj.thumbnailImage}"></c:out>' /> 
						<label for=description>Description</label> 
						<input type="text" id="edit_description" name="description" size="40" value='<c:out value="${channelobj.description}"></c:out>' />
						<label for="collections">Collections</label>
							<div class="multiselect">
			
							<c:forEach var="collections" items="${collections}" varStatus="status">
								<label><input class="collection" type="checkbox" name="collections" value='${collections.collectionId }' id='${collections.collectionId }'/>&nbsp;${ collections.name}</label>
							</c:forEach>
							</div>
						<label for="channelType">Channel Type</label>
						
						<select	name="channelType">
							<option value="${selectedChannelType.id}_${channelobj.channelType}" selected>${channelobj.channelType}</option>
							<c:forEach var="channelTypes" items="${channelTypes}" varStatus="status">
								<c:if test="${channelTypes.id != selectedChannelType.id}">
										<option value="${channelTypes.id}_${channelTypes.name}">${channelTypes.name}</option>
								</c:if>
							</c:forEach>
						</select>
						<input class="submit" id="edit_channel_submit" name="update"
									value="Update" type="submit" />
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
