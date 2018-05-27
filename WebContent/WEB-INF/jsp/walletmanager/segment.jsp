<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KBMC Segments</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />" />
 <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.css"/>">
 <link rel="stylesheet" href="<c:url value="/resources/css/timepicker.css"/>">
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
<script type="text/javascript"
	src="<c:url value="/resources/js/item.js"/>">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/segment.js"/>">
	
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery-ui-timepicker-addon.js"/>">
</script>
<script type="text/javascript"
	src="<c:url value="/resources/js/jquery.ui.sliderAccess.js"/>">
</script>
<script type="text/javascript">
$(document).ready(function() {
	
	var searchURL = 'walletManager/getSegmentNames';
    <c:if test="${user_role=='[ROLE_Administrator]'}">
      searchURL = '';
	  searchURL = 'admin/getSegmentNames';
    </c:if>
	$( "#searchtext" ).autocomplete({
		  source: '${pageContext. request. contextPath}/'+searchURL
	 });
	<c:if test="${not empty creationfailuremsg}">
         $('#segForm').css('display','block');
   </c:if>
	<c:if test="${not empty errormsg}">
    $('#segForm').css('display','block');
	</c:if>
	<c:if test="${not empty errormsg}">
    $('#segForm').css('display','block');
	</c:if>
   
   $("#user_submit").click(function(){
	  $(".errorblock").empty(); 
   });
 	$("#user_update").click(function(){
	 $(".errorblock").empty();  
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

	<div id="page_content" class="segment">
		<div class="wrapper">
					<div id="sub_menu">
				<div id="users" class="sub_menu">
					<a href="getUserSegments" id="edit_users">Manage User Segments</a>
				</div>
				<br /> <br /> <br />
				<div id="segments" class="selected">
					<a href="getSegments" id="add_segments">Add/Edit Segments</a>
				</div>
			</div>
			<div id="segment_list">
				<div class="panel_header">Segments</div>
				<div class="panel_sub_header_bottom">
					<form action="searchSegment" id="segmentSearch" method="POST">
						<div class="col c0">
							<input type="text" name="searchtext" class="segmentsearch"
								id="searchtext" value="${searchAttribute}"> <input type="submit" value=" "
								name="search" id="search" class="search" />
						</div>
					</form>
				</div> 
				<div class="panel_sub_header">
					<c:if test="${not empty deletefailuremsg}">
							<div class="errorblock">Segment cannot be deleted. ${deletefailuremsg}</div>
					</c:if>
					<c:if test="${not empty successMessage}">
					<div class="successmessage">${successMessage}</div>
					</c:if>
					<div class="col c1">Segment Name</div>
					<div class="col c2">Description</div>
					<div class="col c3"></div>
				</div>
			
				<div class="list">
					<div id="itemContainer">
						<c:if test="${fn:length(segments) eq 0}">
									<div style="text-align: center;">No Segments Available</div>
						</c:if>	
						<c:choose>
						<c:when test="${not empty search }">
						<c:set var="count" value="0"></c:set>
						<c:forEach var="segment" items="${segments}" varStatus="status">
							<c:set var="segmentname" value="${segment.name}"/>
							<c:set var="search" value="${searchAttribute}"/>
							<c:if test="${fn:containsIgnoreCase(segmentname,search)}">
							<c:set var="count" value="${count+1}"></c:set>
   								<div class="data">
								<div class="col c1">${segment.name}</div>
								<div class="col c2">${segment.description}</div>
								<div class="col c3">
								<form action="showupdateSegment"
										method="POST">
									<input type="hidden" name="id"
										value='<c:out value="${segment.id}"></c:out>' /> <input
										class="imgbutton edit_segment" type="submit" id="edit"
										name="edit" value=' ' />
								</form>		
								<form action="deleteSegment" method="post"> 
											<input type="hidden" name="id"
													value='<c:out value="${segment.id}"></c:out>' />
											<input class="imgbutton delete" type="submit" id="delete"
													name="delete" value=' ' />
								</form>
								</div>
							</div>
							</c:if>
							</c:forEach>
							<c:if test="${count==0}"><div id="error_search" style="text-align:center">No Such Segment Exists</div></c:if>
						</c:when> 
						<c:when test="${empty search }">
						<c:forEach var="segment" items="${segments}" varStatus="status">
						<div class="data">
								
								<div class="col c1">${segment.name}</div>
								<div class="col c2">${segment.description}</div>
								<div class="col c3">
								<form action="showupdateSegment"
										method="POST">
									<input type="hidden" name="id"
										value='<c:out value="${segment.id}"></c:out>' /> <input
										class="imgbutton edit_segment" type="submit" id="edit"
										name="edit" value=' ' />
								</form>		
								<form action="deleteSegment" method="post"> 
											<input type="hidden" name="id"
													value='<c:out value="${segment.id}"></c:out>' />
											<input class="imgbutton delete" type="submit" id="delete"
													name="delete" value=' ' />
								</form>
								</div>
							</div>
							</c:forEach>
						</c:when>
						</c:choose>	
					</div>
				</div>
				<div class="holder"></div>
			</div>

			<div id="create_segment">
				<div>

					<input class="btn_add" type="submit" name="addsegment"
						value="Add Segment">
				</div>
				<br />

				<form id="segForm" name="segForm" action="createSegment"
					method="post" style="display: none">

					<div class="panel_header">Create Segment</div>
					<div>
						<c:out value="${message}"></c:out>
					</div>
					<div class="panel" id="create_seg">

						<c:if test="${not empty errormsg}">
							<div class="errorblock">Segment Already Exists</div>
						</c:if>
						<c:if test="${not empty creationfailuremsg}">
							<div class="errorblock">Segment With Same Name Already Exists</div>
						</c:if>

						<label for="name"> Segment Name </label> <input type="text" name="name"
							id="name" size="23" /> <label for="description">
							Description </label><textarea rows="4" cols="40"
							name="description" id="description" ></textarea><input class="submit"
							id="user_submit" name="create" value="Create" type="submit">

					</div>

				</form>
	</div>
			
			<c:choose>
			<c:when test="${update == 'update'}">
			<div id="edit_segments">
			<form id="segForm" action="updateSegment" method="post">

							<div>
								<input type="hidden" name="id"
									value='<c:out value="${segmentobj.id}"></c:out>' />
							</div>

							<div class="segment_header">Update Segment
							<div id="segmentFormBtns" style="float:right">
								<input type="button"  name="add_items" value="Add Items to segment" id="segmentItemsButton" class="segmentItemsButton" style="display:none;"/>
								<input type="button"  name="add_items" value="Add Items" class="add_segmentItems"/>
								<input class="submit" id="updateButton" name="update" value="Update" type="submit">
							</div>
							</div>
							 <div id="segmentBtns">
								<div class="sub_header" id="details">Segment Details</div>
								<div class="sub_header" id="products">Products(${products})</div>
    							<div class="sub_header" id="offers">Offers(${offers})</div>
     							<div class="sub_header" id="kohlscash">Kohl's Cash(${kohlsCash})</div> 
     							<div class="sub_header" id="markdownProducts">Markdown Products(${markdownProducts})</div>   
    						 </div>
							<div class="panel" id="update_seg">
								<c:if test="${not empty updatefailuremsg}">
									<div class="errorblock">Updation of Segment failed. ${updatefailuremsg}</div>
								</c:if>
								<label for="name"> Segment Name </label> <input type="text" name="name"
									id="name" size="23"
									value='<c:out value="${segmentobj.name}"></c:out>' /> <label
									for="user_description"> Description </label> 
									<textarea rows="4" cols="40"
							name="description" id="description">${segmentobj.description}</textarea>


					</div>

				</form>
				<div id="productsContainer" style="display:none">
					
					<div id="productsPanel" >
						<c:forEach var="item" items="${items}" >
							<c:if test="${item.type == 'GP'}">
								<div id="product_${item.id}" class="GP">
									<div id="itemDetails">
										<div id="itemImage"><img  src="${item.imageURL}"></div>
										<div id="itemInformation"> 
											<label>${item.title}</label>
											<br/>
											<label>Web ID:${item.webId}</label>
										</div>
										<div id="deleteButton">
											<img id="product_${item.id}_delete" src="../resources/images/button_delete.png" onClick="deleteMe(this)">
										</div>
									</div>
								</div>	
							</c:if>
						</c:forEach>	
					</div>	
					<div class="holder"></div>
					</div>
					
					
					<div id="couponsContainer" style="display:none">
					<div id="couponsPanel" >
						<c:forEach var="item" items="${items}" >
							<c:if test="${item.type == 'OC'}">
								<div id="offer_${item.id}" class="OC">
									<div id="itemDetails">
										<div id="itemImage"><img  src="${item.iconURL}"></div>
										<div id="itemInformation">
											<label>${item.title}</label>
											<br/>
											<label>Coupon Code:${item.couponCode}</label>
										</div>
										<div id="deleteButton">
											<img id="offer_${item.id}_delete" src="../resources/images/button_delete.png" onclick="deleteMe(this)"/>
										</div>
									</div>
								</div>
							</c:if>
						</c:forEach>
					</div>
					<div class="holder"></div>
					</div>
					
					
					<div id="kohlsCashContainer" style="display:none">
					<div id="kohlsCashPanel">
						<c:forEach var="item" items="${items}" >
							<c:if test="${item.type == 'CS'}">
								<div id="kohlsCash_${item.id}" class="CS">
									<div id="itemDetails">
										<div id="itemImage"><img  src="${item.iconURL}"></div>
										<div id="itemInformation">
											<label>${item.title}</label>
										</div>
										<div id="deleteButton">
											<img id="kohlscash_${item.id}_delete" src="../resources/images/button_delete.png" onclick="deleteMe(this)"/>
										</div>
									</div>
								</div>
							</c:if>
						</c:forEach>
					</div>
					<div class="holder"></div>
					</div>
					
				<div id="markdownProductsContainer" style="display:none">
					<div id="markdownProductsPanel">
						<c:forEach var="item" items="${items}" >
							<c:if test="${item.type == 'MD'}">										
								<div id="markdownProduct_${item.id}" class="MD">
									<div id="itemDetails">
										<div id="itemImage"><img  src="${item.imageURL}"></div>
										<div id="itemInformation">
											<label>${item.title}</label>
											<br/>
											<label>Web ID:${item.webId}</label>
										</div>
										<div id="markdownInformation">
												<label for="discount_percentage">Discount Percentage</label>
												<input type="text" id="discount_percentage" name="discount_percentage" size="15" readonly="readonly" />
												<label for="offered_date"> Offered Date </label>
												<input type="text" id="offered_date_${item.id}" name="offered_date" size="15" value="${item.offeredDate}" disabled="disabled" >
												<label for="offered_price">Offered Price</label>
												<input type="text" id="offered_price" name="offered_price" size="15" value="${item.offeredPrice}" readonly="readonly"/>
										</div>
										<div id="deleteButton">
											<img id="markdownProduct_${item.id}_edit" src="../resources/images/button_edit.png" onClick="editMe(this)">
											<img style="display:none" id="markdownProduct_${item.id}_save" src="../resources/images/button_save.png" onClick="saveMe(this)">
											<img id="markdownProduct_${item.id}_delete" src="../resources/images/button_delete.png" onClick="deleteMe(this)">
										</div>
									</div>
								</div>				
							</c:if>
						</c:forEach>
					</div>
					<div class="holder"></div>
				</div>

			</div>
			</c:when>
			</c:choose>

			</div>
		</div>
	<div id="segmentItems" style="display:none;">
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

						<div class="col c0">
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

						<div class="col c0">
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

						<div class="col c0">
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

						<div class="col c0">
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
				<div id="saveSegmentItems">Save Changes	</div>
		
			</div>
		</div>
		
	<jsp:include page="../login/footer.jsp" />
</body>
</html>
