<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KBMC Offers</title>
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
<link rel="stylesheet" href="<c:url value="/resources/css/timepicker.css"/>">
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
	src="<c:url value="/resources/js/offers.js"/>">
</script>

<script type="text/javascript">
	$(document).ready(function() {
		
		var searchURL = 'walletManager/offerNameList';
	    <c:if test="${user_role=='[ROLE_Administrator]'}">
	      searchURL = '';
		  searchURL = 'admin/offerNameList';
	     </c:if>
		$( "#searchtext" ).autocomplete({
			  source: '${pageContext. request. contextPath}/'+searchURL
		 });
		
		<c:if test="${not empty errormsg}">
		$('#addofferForm').css('display', 'block');
		</c:if>

		<c:if test="${not empty createfailuremsg}">
		$('#addofferForm').css('display', 'block');
		</c:if>
		
		<c:if test="${not empty updatefailuremsg}">
		$('#editofferForm').css('display', 'block');
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
			 
			  <c:forEach var="offers" items="${offers}">
			   	var destination="${offers.title}";
			   	if((destination.toLowerCase()).indexOf(searchString)!=-1){
					 var datadiv= $('<div>').addClass("data");
					var c1=$('<div>').addClass('col').addClass('c1').text("${offers.title}");
					var c2=$('<div>').addClass('col').addClass('c2').text("<fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${offers.startDate}" />");
					var c3=$('<div>').addClass('col').addClass('c3').text("<fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${offers.expiryDate}" />");
					var c4=$('<div>').addClass('col').addClass('c4');
					var offerImagePreview=$('<input>').attr('type','hidden').attr('name','iconURL').val("${offers.iconURL}");
					var offerImagePreviewButton=$('<input>').addClass('submit').attr('type','button').attr('id','viewimage').attr('name','viewimage').val("VIEW IMAGE");
					var updateForm=$('<form>').attr('action','showUpdateOffer').attr('method','post');
					var updateFormFields=$('<input>').attr('type','hidden').attr('name','id').val("${offers.offerId}");
					var updateImage=$('<input>').addClass('imgbutton').addClass('edit').attr('type','submit').attr('id','edit').val("");
					updateForm=updateForm.append(updateFormFields).append(updateImage);  
					var deleteForm=$('<form>').attr('action','deleteOffer').attr('method','post');
					var deleteFormFields=$('<input>').attr('type','hidden').attr('name','id').val("${offers.offerId}");
					var deleteImage=$('<input>').addClass('imgbutton').addClass('delete').attr('type','submit').attr('id','delete').val("");
					deleteForm=deleteForm.append(deleteFormFields).append(deleteImage);
					c4=c4.append(offerImagePreview).append(offerImagePreviewButton).append(updateForm).append(deleteForm);
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
			
			<!--List of Offers -->
			<div id="offers_list" class="dataContainer">
				<div class="panel_header">Offers</div>
				<div class="panel_sub_header_bottom">
					
						<div class="col c0">
							<input type="text" name="searchtext" class="locationsearch"
								id="searchtext" value=" "> <input type="submit" value=" "
								name="search" id="search" class="search" />
						</div>
					
				</div> 
				<div class="panel_sub_header">
					<c:if test="${not empty deletefailuremsg}">
						<div class="errorblock">Offer Not Deleted SuccessFully</div>
					</c:if>
					<c:if test="${not empty successMessage}">
					<div class="successmessage">${successMessage}</div>
					</c:if>
					<div class="col c1">Offer</div>
					<div class="col c2">Start Date</div>
					<div class="col c3">End Date</div>
					<div class="col c4"></div>
				</div>
				<div class="list">
					<div id="itemContainer">
						<c:if test="${fn:length(offers) eq 0}">
							<div style="text-align: center;">No Offers Available</div>
						</c:if>
						<c:forEach var="offers" items="${offers}" varStatus="status">
							<div class="data">
								<div class="col c1">${offers.title}</div>
								<div class="col c2"><fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${offers.startDate}" /> </div>
								<div class="col c3"><fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${offers.expiryDate}" /> </div>
								<div class="col c4">
											

												<input type="hidden" name="iconURL"
													value="${offers.iconURL}" />
												<input class="submit" type="button" id="viewimage"
													name="viewimage" value='VIEW IMAGE' />
											 	
											   <form
												action="showUpdateOffer"
												method="post"> 

												<input type="hidden" name="id"
													value='<c:out value="${offers.offerId}"></c:out>' />
												<input class="imgbutton edit" type="submit" id="edit"
													name="edit" value='' />
											 </form> 
											 <form
												action="deleteOffer"
												method="post"> 
												<input type="hidden" name="id"
													value='<c:out value="${offers.offerId}"></c:out>' />
												<input class="imgbutton delete" type="submit" id="delete"
													name="delete" value=' ' />
											 </form>
								</div>
							</div>
						</c:forEach>
					</div>
					<div id="search_error">No such Offer exists</div>
				</div>
				<div class="holder"></div>
			</div>	
			<div id="add_offers" class="add_item">
				<div id="addoffers" class="show_form">
					<div>
						<input class="btn_add" type="submit" name="addoffer"
							value="Create Offer">
					</div>
				</div>
				<form name="addofferForm" id="addofferForm"
					action="createOffer" method="post"
					style="display: none">
					<div class="panel_header">Create Offer</div>
					<div class="panel">
						<c:if test="${not empty errormsg}">
							<div class="errorblock">Offer Already Exists</div>
						</c:if>
						<c:if test="${not empty createfailuremsg}">
       						<div class="errorblock">${createfailuremsg }</div>
      					</c:if>
						<label for="title">Offer Name</label> 
						<input type="text" id="title" name="title" size="40" />
						<label for=description>Description</label> 
						<input type="text" id="description" name="description" size="40">
						<label for="couponCode">Coupon Code</label> 
						<input type="text" id="couponCode" name="couponCode" size="40" /> 
						<label for="iconURL"> Icon URL </label> 
						<input type="text" id="iconURL" name="iconURL" size="100"> 
						<label for="passbookURL"> Passbook URL</label> 
						<input type="text" id="passbookURL" name="passbookURL" size="100">
						<label for="discountPercentage"> Discount Percentage </label> 
						<input type="text" id="discountPercentage" name="discountPercentage" size="40"> 
						<label for="feedImageURL"> FeedImage URL</label> 
						<input type="text" id="feedImageURL" name="feedImageURL" size="100">
						<label for="startDate"> Start Date </label> 
						<input type="text" id="startDate" name="startDate" size="40" readonly="readonly"> 
						<label for="expiryDate"> Expiry Date </label> 
						<input type="text" id="expiryDate" name="expiryDate" size="40" readonly="readonly"> 
						
						
						<input class="submit" id="offer_submit" name="create"
									value="Create" type="submit">
					</div>
				</form>
			 <c:choose>
				<c:when test="${update == 'update'}">
						<form name="editofferForm" id="editofferForm"
							action="updateOffer"
							method="post" class="offerFormUpdate" >
							<div class="panel_header">Update Offer</div>
							<div class="panel">
								<c:if test="${not empty updatefailuremsg}">
									<div class="errorblock">${updatefailuremsg}</div>
									
								</c:if>
								<input type="hidden" name="id" value='<c:out value="${offerobj.offerId}"></c:out>' /> 
								
								<label for="title">Offer Name </label> 
								<input type="text" id="edit_title" name="title" size="40" readonly="readonly" value='<c:out value="${offerobj.title}"></c:out>' />
								<label for=description>Description</label> 
								<input type="text" id="edit_description" name="description" size="40" value='<c:out value="${offerobj.description}"></c:out>'>
								<label for="couponCode">Coupon Code</label> 
								<input type="text" id="edit_couponCode" name="couponCode" size="40" readonly="readonly" value='<c:out value="${offerobj.couponCode}"></c:out>' /> 
								<label for="iconURL"> Icon URL </label> 
								<input type="text" id="edit_iconURL" name="iconURL" size="100" value='<c:out value="${offerobj.iconURL}"></c:out>'>
								<label for="passbookURL"> Passbook URL </label> 
								<input type="text" id="edit_passbookURL" name="passbookURL" size="100" value='<c:out value="${offerobj.passbookURL}"></c:out>'>
								 <label for="discountPercentage"> Discount Percentage </label> 
								<input type="text" id="edit_discountPercentage" name="discountPercentage" size="40" value='<c:out value="${offerobj.discountPercentage}"></c:out>'>
								<label for="feedImageURL"> FeedImage URL</label> 
								<input type="text" id="edit_feedImageURL" name="feedImageURL" size="100"  value='<c:out value="${offerobj.feedImageURL}"></c:out>'>
								<label for="startDate"> Start Date </label> 
								<input type="text" id="edit_startDate" name="startDate" size="40" readonly="readonly" value='<fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${offerobj.startDate}" />'>
								<label for="expiryDate"> Expiry Date </label> 
								<input type="text" id="edit_expiryDate" name="expiryDate" size="40" readonly="readonly" value='<fmt:formatDate pattern="MM/dd/yyyy HH:mm" value="${offerobj.expiryDate}" />'>
								
								<input class="submit" id="edit_offer_submit" name="update"
									value="Update" type="submit">
							</div>
						</form>
						</c:when>
			</c:choose> 
			</div>
		</div>
		</div>	
		<div id="offerOverviewImage" style="display: none;">
		
				<div class="panel_header">
					
					</div>
				
					
				<div class="panel">
				<img alt="" src="" >
				<input type="button" value="OK" class='submit'/>
				</div>
				
		</div>
		<jsp:include page="../login/footer.jsp" />
</body>
</html>
