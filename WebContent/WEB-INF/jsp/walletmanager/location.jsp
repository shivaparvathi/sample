<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>KBMC Locations</title>
<link type="text/css" rel="stylesheet"
	href="<c:url value="/resources/css/style.css" />" />
 <link rel="stylesheet" href="<c:url value="/resources/css/jquery-ui.css"/>">
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
	src="http://maps.google.com/maps/api/js?sensor=false&v=3.6"></script>
<script type="text/javascript"
	src="<c:url value="/resources/js/gmaps.js"/>"></script>
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
	src="<c:url value="/resources/js/locations.js"/>">
	
</script>
<script type="text/javascript">
$(document).ready(function() {
	
	var searchURL = 'walletManager/locationNameList';
    <c:if test="${user_role=='[ROLE_Administrator]'}">
      searchURL = '';
	  searchURL = 'admin/locationNameList';
    </c:if>
	$( "#searchtext" ).autocomplete({
		  source: '${pageContext. request. contextPath}/'+searchURL
	 });
	
	<c:if test="${not empty errormsg}">
         $('#addlocationForm').css('display','block');
    </c:if> 
   	<c:if test="${not empty createfailuremsg}">
   		$('#addlocationForm').css('display','block');
	</c:if> 
  	<c:if test="${not empty updatefailuremsg}">
		$('#editlocationForm').css('display','block');
	</c:if> 
 
	<c:if test="${not empty locationobj}">
	   var locname='${locationobj.name}'; 
	   var latitude=${locationobj.latitude}; 
	   var longitude=${locationobj.longitude}; 
	   var radiusobj=${locationobj.radius}; 
	   $.generateRadiusMap(locname, latitude, longitude, radiusobj, 'location_map', 'editlocationForm');
	</c:if> 
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
			<!--List of Locations -->
			<div id="locations_list" class="dataContainer">
				<div class="panel_header">Locations</div>
				<div class="panel_sub_header_bottom">
					 <form action="searchLocation" id="searchLocation" method="POST"> 
						<div class="col c0">
							<input type="text" name="searchtext" class="locationsearch"
								id="searchtext" value="${searchAttribute}"> <input type="submit" value=" "
								name="search" id="search" class="search" />
						</div>
					 </form>
				</div> 
				<div class="panel_sub_header">
					<c:if test="${not empty deletefailuremsg}">
						<div class="errorblock">Location Not Deleted SuccessFully</div>
					</c:if>
					<c:if test="${not empty successMessage}">
					<div class="successmessage">${successMessage}</div>
					</c:if>
					<div class="col c1">Location</div>
					<div class="col c2">Longitude</div>
					<div class="col c3">Latitude </div>
					<div class="col c4">Radius(In Meters)</div>
					<div class="col c5"></div>
				</div>
				
				<div class="list">
					<div id="itemContainer">
						<c:if test="${fn:length(locations) eq 0}">
							<div style="text-align: center;">No Locations Available</div>
						</c:if>
						 <c:choose>
						<c:when test="${not empty search}"> 
						 <c:set var="count" value="0"></c:set>
						<c:forEach var="locations" items="${locations}"
							varStatus="status">
							<c:set var="locationname" value="${locations.name}"/>
							<c:set var="search" value="${searchAttribute}"/>
							<c:if test="${fn:containsIgnoreCase(locationname,search)}">
							<c:set var="count" value="${count+1}"></c:set>
   								<div class="data">
								<div class="col c1">${locations.name}</div>
								<div class="col c2">${locations.longitude} </div>
								<div class="col c3">${locations.latitude} </div>
								<div class="col c4">${locations.radius}</div>
									<div class="col c5">
											 <form action="showUpdateLocation" method="post">
													<input type="hidden" name="locationkey"
														value="${locations.locationKey}" /> <input
														class="imgbutton edit" type="submit" id="edit" name="edit" value=" "/>
												</form>
												<form action="deleteLocation" method="post">
													<input type="hidden" name="locationkey"
														value="${locations.locationKey}" /> <input
														class="imgbutton delete" type="submit" id="delete"
														name="delete" value=" "/>
												</form>
								</div>
							</div>
							</c:if>
							</c:forEach>
							<c:if test="${count==0}"><div id="error_search" style="text-align:center">No Such Location Exists</div></c:if> 
						</c:when>  
						 <c:when test="${empty search }">
						<c:forEach var="locations" items="${locations}"
							varStatus="status">
							<div class="data">
								<div class="col c1">${locations.name}</div>
								<div class="col c2">${locations.longitude} </div>
								<div class="col c3">${locations.latitude} </div>
								<div class="col c4">${locations.radius}</div>
								<div class="col c5">
									
											 <form action="showUpdateLocation" method="post">

												<input type="hidden" name="locationkey"
													value="${locations.locationKey}" /> <input
													class="imgbutton edit" type="submit" id="edit" name="edit" value=" "/>
											</form>
											<form action="deleteLocation" method="post">
												<input type="hidden" name="locationkey"
													value="${locations.locationKey}" /> <input
													class="imgbutton delete" type="submit" id="delete"
													name="delete" value=" "/>
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
			<div id="add_location">
				<div id="addlocation" class="show_form">
					<div>
						<input class="btn_add" type="submit" name="addlocation"
							value="Create Location">
					</div>
				</div>
				<form name="addlocationForm" id="addlocationForm"
					action="createLocation" method="post"
					style="display: none">
					<div class="panel_header">Add Location</div>
					<div class="panelbody">
						<c:if test="${not empty createfailuremsg}">
							<div class="errorblock">${message}</div>
						</c:if>
						<label for="locationKey">Location Key </label> 
						<input type="text" id="locationkey" name="locationkey" size="40"> 
						<label for="storeId">StoreId </label> 
						<input type="text" id="storeid" name="storeid" size="40"> 
						<label for="Name">Location Name </label> 
						<input type="text" id="name" name="name" size="40" /> 
						<label for="longitude"> Longitude </label> 
						<input type="text" id="longitude" name="longitude" size="40"> 
						<label for="latitude">Latitude </label> 
						<input type="text" id="latitude" name="latitude" size="40">		
						<label for="radius"> Radius(In Meters) </label> 
						<input type="text" id="radius" name="radius" size="40"> 
						
						</div>
						
					
					<div id="map" class="map" style="float:left"></div>
					<input class="submit" id="location_submit" name="create"
									value="Create" type="submit">
				</form>
			<c:choose>
				<c:when test="${update == 'update'}">
						<form name="editlocationForm" id="editlocationForm"
							action="updateLocation"
							method="post" class="locationFormUpdate" >
							<div class="panel_header">Update Location</div>
							<div class="panelbody">
								<c:if test="${not empty updatefailuremsg}">
								<div class="errorblock">${updatefailuremsg}</div>
								</c:if>
								<label for="locationKey">Location Key </label> 
								<input type="text" id="locationkey" name="locationkey" size="40" value='<c:out value="${locationobj.locationKey}"></c:out>' readonly="readonly"> 
								 <label for="storeId">StoreId </label> 
								 <input type="text" id="storeid" name="storeid" size="40"  value='<c:out value="${locationobj.storeId}"></c:out>'> 
								<label for="Name">Location Name </label>  
								<input type="text" id="name" name="name" size="40" value='<c:out value="${locationobj.name}"></c:out>' readonly="readonly" /> 
								<label for="longitude"> Longitude </label> 
								<input type="text" id="longitude" name="longitude" size="40" value='<c:out value="${locationobj.longitude}"></c:out>'>
								<label for="latitude"> Latitude </label> 
								<input type="text" id="latitude" name="latitude" size="40"value='<c:out value="${locationobj.latitude}"></c:out>'> 						
								<label for="radius"> Radius(In Meters) </label> 
								<input type="text" id="radius" name="radius" size="40" value='<c:out value="${locationobj.radius}"></c:out>'> 
								</div>
								<div id="location_map" class="map"></div>
								<input class="submit" id="update_location_submit" name="update"
									value="Update" type="submit">

							
						</form>
						</c:when>
			</c:choose>
			</div>
		</div>
		</div>	
				
<jsp:include page="../login/footer.jsp" />



</body>
</html>
