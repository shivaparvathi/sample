<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set scope="session" var="user_role"
	value='<%=session.getAttribute("role")%>' />

<div class="header_top">
	<div class="logo">
		 <img alt="Kohls Backend Management Console" src="../resources/images/logo.png">
	</div>
	<div class="login">
		Welcome <%=session.getAttribute("username")%>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="<c:url value="/j_spring_security_logout" />">Logout</a>
	</div>
</div>

<div id="nav">
	<ul>
		<c:if test="${user_role=='[ROLE_Administrator]'}">
			
			<li <c:if test="${selected_header=='user'}">class="first selected"</c:if>><a
				href="getuser">KBMC Users</a></li>
			<li <c:if test="${selected_header=='segment'}">class="first selected"</c:if>><a
				href="getUserSegments">Segments</a></li>
			<li <c:if test="${selected_header=='offer'}">class="first selected"</c:if>><a
				href="manageOffers">Offers</a></li>
			<li <c:if test="${selected_header=='location'}">class="first selected"</c:if>><a
				href="manageLocations">Locations</a></li>
			<li <c:if test="${selected_header=='collection'}">class="first selected"</c:if>><a
				href="manageCollections">Collections</a></li>
			<li <c:if test="${selected_header=='channelType'}">class="first selected"</c:if>><a
				href="getChannelTypes">Channel Types</a></li>
			<li <c:if test="${selected_header=='channel'}">class="first selected"</c:if>><a
				href="getChannels">Channels</a></li>
		</c:if>
		<c:if test="${user_role=='[ROLE_Wallet Manager]'}">
			<li <c:if test="${selected_header=='segment'}">class="first selected"</c:if>><a
				href="getUserSegments">Segments</a></li>
			<li <c:if test="${selected_header=='offer'}">class="first selected"</c:if>><a
				href="manageOffers">Offers</a></li>
			<li <c:if test="${selected_header=='location'}">class="first selected"</c:if>><a
				href="manageLocations">Locations</a></li>
			<li <c:if test="${selected_header=='collection'}">class="first selected"</c:if>><a
				href="manageCollections">Collections</a></li>
			<li <c:if test="${selected_header=='profile'}">class="first selected"</c:if>><a
				href="profile">Profile</a></li>
		</c:if>
		<c:if test="${user_role=='[ROLE_Channel Manager]'}">
			<li <c:if test="${selected_header=='channelType'}">class="first selected"</c:if>><a
				href="getChannelTypes">Channel Types</a></li>
			<li <c:if test="${selected_header=='channel'}">class="first selected"</c:if>><a
				href="getChannels">Channels</a></li>
			<li <c:if test="${selected_header=='profile'}">class="first selected"</c:if>><a
				href="profile">Profile</a></li>
		</c:if>
		<li class="last"></li>
		</ul>
</div>
