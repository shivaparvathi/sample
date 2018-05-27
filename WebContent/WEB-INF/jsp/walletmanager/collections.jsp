<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KBMC Collections</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />" />
 <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.css"/>">
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-1.8.2.min.js"/>">
</script>
<script src="<c:url value="/resources/js/jquery.ui.js"/>"></script>
<link rel="stylesheet" href="<c:url value="/resources/css/jPages.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/css/stylepagination.css"/>">
<link rel="stylesheet"
	href="<c:url value="/resources/css/timepicker.css"/>">
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
	src="<c:url value="/resources/js/jquery-ui-timepicker-addon.js"/>">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.ui.sliderAccess.js"/>">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/item.js"/>">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/collection.js"/>">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/date.format.js"/>">
	
</script>

<script type="text/javascript">

$(document).ready(function() {
	
	<c:if test="${not empty createfailuremsg}">
	$('#collectionForm').css('display', 'block');
	</c:if>
	<c:if test="${not empty updatefailuremsg}">
	alert("Failed to Update");
	</c:if>
	
	var searchURL = 'walletManager/collectionNameList';
    <c:if test="${user_role=='[ROLE_Administrator]'}">
      searchURL = '';
	  searchURL = 'admin/collectionNameList';
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
		   
		  <c:forEach var="collections" items="${collections}">
		   	var collectionName="${collections.name }";
		   	if((collectionName.toLowerCase()).indexOf(searchString)!=-1){
				 var datadiv= $('<div>').addClass("data");
				 var collection_info = $('<div>').addClass('collectioninfo').attr('id','collection_info_${collections.collectionId}');
				 var arrow_img = $('<img>').addClass('collection_view').attr('src','../resources/images/arrow_close.png');
				 var collection_name = $('<span>').attr('id','${collections.collectionId}').addClass('collection_name');
				collection_name.append(arrow_img).append('&nbsp;').append('${collections.name}');
				var c2=$('<div>').addClass('col').addClass('c2').text("<fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${collections.endDate}" />");
				var c3=$('<div>').addClass('col').addClass('c3').text("<fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${collections.startDate}" />");
				
				collection_info = collection_info.append(collection_name).append(c2).append(c3);
				datadiv=datadiv.append(collection_info);
				itemContainer.append(datadiv);
				count=count+1; 
		   	}
		  </c:forEach>
		  $("#collection_list .list").append(itemContainer);	
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

<body>
	<div id="header">
		<div class="wrapper">
			<jsp:include page="../login/header.jsp" />
		</div>
	</div>
	<div id="page_content">
		<div class="wrapper">
			<div id="collection_list">
				<div class="collection_panel_header">
					Collections
				<div id="add_collection" class="f-right ">
				<input class="btn_add" type="button" name="addcollection"
							value="Create Collection" >
				<input class="btn_add" type="button" name="addarrangement" value="Create Arrangement" id="Add_arrangement_button">
				</div>	
				</div>
				<div class="panel_sub_header">
					<c:if test="${not empty successMessage}">
					<div class="successmessage">${successMessage}</div>
					</c:if>
					<div class="collection_name">Collection</div>
					<div class="col c1">Start Date </div>
					<div class="col c2">End Date</div>
				</div>
				<div class="panel_sub_header_bottom">

					<div class="col c0">
						<input type="text" name="searchtext" class="collectionsearch"
							id="searchtext" value=""> <input type="submit" value=" "
							name="search" id="search" class="search" />
					</div>

				</div>
				<div class="list">
					<div id="itemContainer">
						<c:if test="${fn:length(collections) eq 0}">
							<div style="text-align: center;">No Collections Available</div>
						</c:if>
						<c:forEach var="collections" items="${collections}"
							varStatus="status">
							<div class="data">
								<div id="collection_info_${collections.collectionId}"
									class="collectioninfo" style="width: 100%">
									<span class="collection_name" id="${collections.collectionId}">
										<img src="../resources/images/arrow_close.png"
										class="collection_view" />&nbsp;${collections.name }
									</span>
									<div class="col c1">
										<fmt:formatDate pattern="MM/dd/yyyy HH:mm"
											value="${collections.startDate}" />
									</div>
									<div class="col c2">
										<fmt:formatDate pattern="MM/dd/yyyy HH:mm"
											value="${collections.endDate}" />
									</div>
								</div>
							</div>
						</c:forEach>
					</div>
					<div id="search_error">No such collection exists</div>
				</div>
				<div class="holder"></div>
			</div>

				<div id="create_collection">
					<form name="collectionForm" id="collectionForm"
						action="createCollection" method="post" style="display: none">
						<div class="collection_header">
							Create Collection
							<div id="create_collectionFormBtn">
								<input type="submit" class="submit" name="createcollection"	value="Create"/>
							</div>
						</div>
						<div class="collectionpanel">
							<c:if test="${not empty createfailuremsg}">
								<div class="errorblock">${createfailuremsg}</div>
							</c:if>
							<input type="hidden" id="collectionId" value=""/>
							<label for="collectionsname"> Collection Name </label> <input
								type="text" id="collectionsname" name="name" size="30" /> <label
								for="collectionsdescription">Description</label>
							<textarea rows="2" cols="25" id="collectionsdescription"
								name="description"></textarea>

							<label for="collection_type"> Collection Type </label> <select
								name="collectionType">
								<option value="CAMPAIGN">CAMPAIGN</option>
								<option value="BRAND">BRAND</option>
								<option value="DEPT">DEPT</option>
								<option value="TRENDING">TRENDING</option>
								<option value="PERSONALIZED">PERSONALIZED</option>
							</select> <label for="startdate"> Start Date</label> <input type="text"
								class="startdate" name="startDate" size="30" id="startdate" readonly="readonly">
							<label for="enddate"> End Date</label> <input type="text"
								class="enddate" name="endDate" size="30" id="enddate" readonly="readonly"> <label
								for="active">Active</label>
							<div class="active">
								<div>
									<input type="radio" id="enabled" name="Active" value="enabled"
										checked="checked"> <label for="enabled">Enabled</label>
								</div>
								<div>
									<input type="radio" value="disabled" name="Active"
										id="disabled"> <label for="disabled">Disabled</label>
								</div>
							</div>

							<label for="arrangements">Arrangements</label>
							<div class="multiselect">
								<c:forEach var="arrangements" items="${arrangements}"
									varStatus="status">
									<label><input class="arrangement" type="checkbox"
										name="arrangements" value='${arrangements.arrangementId }'
										id='${arrangements.arrangementId }' />&nbsp;${
										arrangements.name}</label>
								</c:forEach>
							</div>

						</div>
					</form>
				</div>
				<div id="edit_collection">
					<form name="collectionEditForm" id="collectionEditForm"
						action="updateCollection" method="POST" style="display: none">
						<div class="collection_header" id="collection_name">
							<span></span>
							<div id="edit_collectionFormBtn">
								<input type="submit" class="submit"	value="Save Changes"/>
								<input type="button"  name="delete"	value="Delete" class="delete"/>
							</div>
						</div>
						<div class="collectionpanel">
							<input type="hidden" name="id" id="collectionId" value='' /> <label
								for="collectionsname"> CollectionsName </label> <input
								type="text" id="update_collectionname" name="name" size="30"
								value="" readonly="readonly"/> <label for="collectionsdescription">
								Collections Description</label>
							<textarea rows="2" cols="25" id="update_description"
								name="description"></textarea>

							<label for="collection_type"> Collection Type </label> <select
								name="collectionType" id="collectionType">

								<option value="CAMPAIGN">CAMPAIGN</option>
								<option value="BRAND">BRAND</option>
								<option value="DEPT">DEPT</option>
								<option value="TRENDING">TRENDING</option>
								<option value="PERSONALIZED">PERSONALIZED</option>

							</select> <label for="startdate"> Start Date</label> <input type="text"
								class="startdate" id="update_startdate" name="startDate"
								size="30" value="" readonly="readonly"> <label for="enddate"> End
								Date</label> <input type="text" class="enddate" name="endDate"
								id="update_enddate" size="30" value="" readonly="readonly"> <label
								for="active">Active</label>
							<div class="active">
								<div>
									<input type="radio" id="edit_enabled" name="Active" value="enabled"
										checked="checked"> <label for="edit_enabled">Enabled</label>
								</div>
								<div>
									<input type="radio" value="disabled" name="Active"
										id="edit_disabled"> <label for="edit_disabled">Disabled</label>
								</div>
							</div>

							<label for="arrangements">Arrangements</label>
							<div class="multiselect">
								<c:forEach var="arrangements" items="${arrangements}"
									varStatus="status">
									<label><input class="arrangement" type="checkbox"
										name="arrangements" value='${arrangements.arrangementId }'
										id='${arrangements.arrangementId }' />&nbsp;${
										arrangements.name}</label>
								</c:forEach>
							</div>

							<label for="channelname"> Assigned Channels </label> <input
								type="text" name="channelName" size="30" value=""
								id="channelName" readonly="readonly" /> 
						</div>
					</form>
				</div>
			
			<div id="add_arrangements" style="display: none;">
				<div class="panel_header">
					Create Arrangement
					<div id="loaderandbutton" >
					<div class="loader" style="display: none;">
						<img src="../resources/images/ajax-loader.gif">
					</div><div id="collectionFormBtn" style="float:right;">
						<input id="arrangement_create" name="create" value="Create" type="button">
						</div></div>
				</div>
				<div class="panel">
					<label for="name">Name</label> <input type="text" id="Arrangement_name_field"
						name="name" size="40" /> <label for="location">Location</label> <select
						name="location" id="location">
						<c:forEach var="location" items="${locations}" varStatus="status">
							<option value='<c:out value='${location.locationKey}'/>'>${location.name}</option>
						</c:forEach>
					</select><input type="checkbox" id="locationcheck" name="locationcheck">Default
					 <label for="segment"> Segment </label> <select name="segment"
						id="segment">
						<c:forEach var="segment" items="${segments}" varStatus="status">
							<option value='<c:out value='${segment.id}'/>'>${segment.name}</option>
						</c:forEach>
					</select><input type="checkbox" id="segmentcheck" name="segmentcheck">Default
					<label for="status"> Active </label> <select name="status"
						id="status">
						<option value="enable">Enable</option>
						<option value="disable">Disable</option>
					</select>
					<br/>
				</div>

			</div>
			<div id="edit_arrangements" style="display: none">
					<div class="panel_header">
						<span></span>
						<div id="edit_arrangementFormBtn">
						<div id="save_ajax_loader" style="display:none;float:left;"><img src="../resources/images/ajax-loader.gif"/></div>
						<input class="submit" id="arrangement_update" name="create" value="Save Changes" type="button">
						<input type="button"  name="add_items" value="Add Items" class="add_items"/>
						<input type="button"  name="delete"	value="Delete" class="delete"/>
					</div>
					</div>
					<div class="panel_sub_header">
					<div id="arrangementBtns">
						<div class="sub_header" id="details">Details</div>
						<div class="sub_header" id="products">Products</div>
    					<div class="sub_header" id="offers">Offers</div>
     					<div class="sub_header" id="kohlscash">Kohl's Cash</div>    
     					<div class="sub_header" id="markdownProducts">Markdown Products</div> 
					</div>
					</div>
					<div class="panel">
						<input type="hidden" name="id" id="arrangementId"
							value='<c:out value=""></c:out>' /> <label for="name">Name</label>
						<input type="text" id="name" name="name" size="40"
							readonly="readonly" value='' /> <label for="location">Location</label>
						<select name="location" id="select_location">
							<c:forEach var="location" items="${locations}" varStatus="status">
								<option value='<c:out value='${location.locationKey}'/>'>${location.name}</option>
							</c:forEach>
						</select><input type="checkbox" id="updateLocationCheck" name="updateLocationCheck">Default
						 <label for="segment"> Segment </label> <select name="segment"
							id="select_segment">
							<c:forEach var="segment" items="${segments}" varStatus="status">
								<option value='<c:out value='${segment.id}'/>'>${segment.name}</option>
							</c:forEach>
						</select><input type="checkbox" id="updateSegmentCheck" name="updateSegmentCheck">Default
						<label for="status"> Active </label> <select name="status"
							id="arrangement_status">
							<option value="enable">Enable</option>
							<option value="disable">Disable</option>
						</select>
					</div>
					<div id="productsContainer" style="display:none">
					<div id="productsPanel" >
					</div>
					<div class="holder"></div>
					</div>
					<div id="couponsContainer" style="display:none">
					<div id="couponsPanel" >
					</div>
					<div class="holder"></div>
					</div>
					<div id="kohlsCashContainer" style="display:none">
					<div id="kohlsCashPanel">
					</div>
					<div class="holder"></div>
					</div>
					<div id="markdownProductsContainer" style="display:none">
					<div id="markdownProductsPanel">
					</div>
					<div class="holder"></div>
					</div>
				</div>
		</div>
	</div>
	<div id="arrangements" style="display:none;">
			<div class="panel_header">
				Add Items
				<div id="popup_close">
					<img alt="" src="../resources/images/cancel.jpeg">
				</div>
			</div>
			<div class="panel_sub_header">
				<div class="productsButton" id="selected">Products</div>
				<div class="offersButton">Offers</div>
				<div class="kohlcashButton">Kohl's Cash</div>
				<div class="markdownButton">Markdown Products</div>
			</div>
			<div class="list">
				<div id="productlist">
					<div class="panel_sub_header_bottom">

						<div class="itemsCol">
							<input type="text" name="searchtext" class="productSearch"
								id="searchtext" value=""> <input type="submit"
								value="" name="search" id="productNameSearch" class="search" />
							<div style="float: right">
								<input style="float: right" type="checkbox"
									id="markdownPercentageCheck" name="markdownPercentageCheck">Markdown
								Products
							</div>
						</div>

						<div id="products-loader-overlay" class="loader-overlay" style="display: none;">
							<img src="../resources/images/ajax-loader.gif">
						</div>
						<div id="productsItemContainer"></div>
					</div> 
					<div class="holder"></div>
				</div>
				
				<div id="offerlist" style="display: none">
					<div class="panel_sub_header_bottom">

						<div class="itemsCol">
							<input type="text" name="searchtext" class="offerSearch"
								id="searchtext" value=""> <input type="submit"
								value="" name="search" id="offerNameSearch" class="search" />
						</div>
						<div id="offers-loader-overlay" class="loader-overlay" style="display: none;">
							<img src="../resources/images/ajax-loader.gif">
						</div>
						<div id="offersItemContainer"></div>
					</div>
					<div class="holder"></div>
				</div>

				<div id="kohlcashlist" style="display: none">
					<div class="panel_sub_header_bottom">

						<div class="itemsCol">
							<input type="text" name="searchtext" class="kohlcashSearch"
								id="searchtext" value=""> <input type="submit"
								value="" name="search" id="kohlcashNameSearch" class="search" />
						</div>
						<div id="kohlsCash-loader-overlay" class="loader-overlay" style="display: none;">
							<img src="../resources/images/ajax-loader.gif">
						</div>
						<div id="kohlsCashItemContainer"></div>
					</div>
					<div class="holder"></div>
				</div>
				<div id="markdownproductlist" style="display: none">
					<div class="panel_sub_header_bottom">

						<div class="itemsCol">
							<input type="text" name="searchtext" class="markdownproductSearch"
								id="searchtext" value=""> <input type="submit"
								value="" name="search" id="markdownproductNameSearch" class="search" />
						</div>
						<div id="markdownProduct-loader-overlay" class="loader-overlay" style="display: none;">
							<img src="../resources/images/ajax-loader.gif">
						</div>
						<div id="markdownProductItemContainer"></div>
					</div>
					<div class="holder"></div>
				</div>
				
				<div id="addItemsLoader" class="" style="display: none;">
					<img src="../resources/images/ajax-loader.gif"></div>
				<input type="button" id="savearrangement" value="Save Changes" />
			</div>
		</div>
	<jsp:include page="../login/footer.jsp" />
</body>
</html>
