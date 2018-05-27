$(document).ready(function () {
    
	//To Cleanup the Map Division On loading Page
	$('#map').empty();
    infowindow = new google.maps.InfoWindow();
    
    //Default Data
    var default_latitude = 40.94118;
    var default_longitude = -74.135742;
    var default_locname = "New York";
    var default_radiusobj = 1000;
    $.generateRadiusMap(default_locname, default_latitude, default_longitude, default_radiusobj, 'map', 'addlocationForm');
    //Loading Default Map on Create Location button Click
    
	$('#add_location .btn_add').live('click',function(){
		$('label.error').remove();
		$("#addlocationForm input[type='text']").val("");
		$('#add_location #addlocationForm').css('display','block');
		$('#editlocationForm').css('display','none');
		$('#addlocationForm').css('display','block');
        $.generateRadiusMap(default_locname, default_latitude, default_longitude, default_radiusobj, 'map', 'addlocationForm');

	});
	
	//hiding error block on submit button click
	$('#addlocationForm  #location_submit').live('click',function(){
		$('#addlocationForm .errorblock').hide();
		
	});
	
	
	$('#locations_list .imgbutton .edit').live('click',function(){
		$('#add_location #addlocationForm').css('display','none');
	});
	
	//showing alert message to user on delete button click
	$('#delete').live('click', function(){
		var value = confirm("Are you sure you want to delete?");
		  if (value)
		      return true;
		  else
		    return false;
	});
	/*loading the map while change in the latitude,longitude and radius obj parameters 
	in create location form*/
    $("#addlocationForm .panelbody input[name=latitude],#addlocationForm .panelbody input[name=longitude],#addlocationForm .panelbody input[name=radius]").on('change', function () {
        $('#map').empty();
        $('#location_map').empty();
        var latitude = $("#addlocationForm .panelbody #latitude").val();
        var longitude = $("#addlocationForm .panelbody #longitude").val();
        var locname = $("#addlocationForm .panelbody #name").val();

        if ((latitude.length != 0) && (longitude.length != 0)) {
            var radiusobj = $("#addlocationForm .panelbody #radius").val();
            if (radiusobj.length != 0) {
                $.generateRadiusMap(locname, latitude, longitude, radiusobj, 'map', 'addlocationForm');
            } else {
                $.generateMap(locname, latitude, longitude, 'map', 'addlocationForm');
            }
        }
        else
        	{
        		$.generateRadiusMap(default_locname, default_latitude, default_longitude, default_radiusobj, 'map', 'addlocationForm');
        	}

    });

    /*loading the map while change in the latitude,longitude and radius obj parameters 
	in update location form*/
    $("#editlocationForm .panelbody input[name=latitude],#editlocationForm .panelbody input[name=longitude],#editlocationForm .panelbody input[name=radius]").on('change', function () {
        $('#map').empty();
        $('#location_map').empty();

        var latitude = $("#editlocationForm .panelbody #latitude").val();
        var longitude = $("#editlocationForm .panelbody #longitude").val();
        var locname = $("#editlocationForm .panelbody #name").val();
        var radiusobj = $("#editlocationForm .panelbody #radius").val();
        if ((latitude.length != 0) && (longitude.length != 0)) {
        if (radiusobj.length != 0) {
            $.generateRadiusMap(locname, latitude, longitude, radiusobj, 'location_map', 'editlocationForm');
        } else {
            $.generateMap(locname, latitude, longitude, 'location_map', 'editlocationForm');
        }
        }
        else
    	{
        	$.generateRadiusMap(default_locname, default_latitude, default_longitude, default_radiusobj, 'location_map', 'editlocationForm');
    	}
    });
});
	
//validating the form fields on submiting the form

(function($, W, D) {
		var JQUERY4U = {};

		JQUERY4U.UTIL = {
			setupFormValidation : function() {
				jQuery.validator.addMethod("floatValues", function(value, element) {
					return this.optional(element)
					  || /^[+-]?\d+(\.\d+)?$/i.test(value);
							
				});
				jQuery.validator.addMethod("specialChars", function( value, element ) {
					return this.optional(element)
					|| /^[a-zA-Z0-9]+$/i.test(value);
			    });

				$("#addlocationForm").validate({
					rules : {
						locationkey:{
							 required : true,
							 specialChars:true
						},
						storeid:{
							 required : true,
							 specialChars:true
						},
						name : {
						      required : true
							  },
						latitude : {
						      required : true,
						      floatValues: true
						     },
						longitude : {
						      required : true,
						      floatValues: true
						     },
						radius : {
							required : true,
							floatValues: true
						}
					},

					messages : {
						locationkey:{
							 required : "Please Enter LocationKey",
							 specialChars:"Please Enter Valid LocationKey"
						},
						storeid:{
							 required : "Please Enter StoreId",
							 specialChars:"Please Enter Valid StoreId "
						},
						name : {
			                   required :"Please Enter Location Name"
		                  },
						latitude : {
			                   required :"Please Enter Location Latitude",
			                   floatValues:"Please Enter Valid Latitude"
		                  },
						longitude : {
			                   required :"Please Enter Location Longitude",
			                   floatValues:"Please Enter Valid Longitude"
		                  },
						radius : {
							required : "Please Enter Radius",
							floatValues:"Please Enter Valid Radius"
						}

					},
					submitHandler : function(form) {
						form.submit();
					}
				});
				$("#editlocationForm").validate({
					
					rules : {
						storeid:{
							 required : true,
							 specialChars:true
						},
						latitude : {
						      required : true,
						      floatValues: true
						     },
						longitude : {
						      required : true,
						      floatValues: true
						     },
						
						radius : {
							required : true,
							floatValues: true
						}
					},

					messages : {
						storeid:{
							 required : "Please Enter StoreId",
							 specialChars:"Please Enter Valid StoreId "
						},
						latitude : {
			                   required :"Please Enter Location Latitude",
			                   floatValues:"Please Enter Valid Latitude"
		                  },
						longitude : {
			                   required :"Please Enter Location Longitude",
			                   floatValues:"Please Enter Valid Longitude"
		                  },
						
						radius : {
							required : "Please Enter Radius",
							floatValues:"Please Enter Valid Radius"
						}

					},
					submitHandler : function(form) {
						form.submit();
					}
				});
			}
		};

		//when the dom has loaded setup form validation rules
		$(D).ready(function($) {
			JQUERY4U.UTIL.setupFormValidation();
		});
		

	})(jQuery, window, document);
	
//Display map with radius
$.generateRadiusMap = function (locname, latitude, longitude, radiusobj, mapId, formId) {
    $('#map').empty();
    $('#location_map').empty();
    var radius = parseFloat(radiusobj);
    var myMarkerIsDraggable = true;
    var myCoordsLenght = 4;
    loc1 = new google.maps.LatLng(latitude, longitude);
    var map = new google.maps.Map(document.getElementById(mapId), {
        center: loc1,
        zoom: 13,
        mapTypeControl: false,
        streetViewControl: false,
        mapTypeId: google.maps.MapTypeId.ROADMAP

    });
    marker = new google.maps.Marker({
        map: map,
        position: loc1,
        draggable: myMarkerIsDraggable
    });
    var circle = new google.maps.Circle({
        center: loc1,
        radius: radius,
        map: map,
        fillColor: '#0000FF',
        fillOpacity: 0.5,
        strokeColor: '#0000FF',
        strokeOpacity: 1.0
    });
    circle.bindTo('center', marker, 'position');
    
    google.maps.event.addListener(marker, 'dragend', function(evt){
    	circle.setRadius(0);
    	$('#'+formId+' #latitude').val(evt.latLng.lat().toFixed(myCoordsLenght));
    	$('#'+formId+' #longitude').val(evt.latLng.lng().toFixed(myCoordsLenght));
    	$('#'+formId+' #radius').val(radius);
    	latitude=evt.latLng.lat().toFixed(myCoordsLenght);
    	longitude=evt.latLng.lng().toFixed(myCoordsLenght);
		 loc1 = new google.maps.LatLng(latitude, longitude);
		 
		circle = new google.maps.Circle({
	        center: loc1,
	        radius: radius,
	        map: map,
	        fillColor: '#0000FF',
	        fillOpacity: 0.5,
	        strokeColor: '#0000FF',
	        strokeOpacity: 1.0
	    });
		
		 circle.bindTo('center', marker, 'position');
	});
    google.maps.event.addListener(marker, 'click', function () {

        infowindow.setContent(locname);
        infowindow.open(map, marker);

    });
    google.maps.event.addListener(map, 'click', function () {
        infowindow.close();
    });

};

//Display map
$.generateMap = function (locname, latitude, longitude, mapId, formId) {
    $('#map').empty();
    $('#location_map').empty();
    var myMarkerIsDraggable = true;
    var myCoordsLenght = 4;
    loc1 = new google.maps.LatLng(latitude, longitude);
    var map = new google.maps.Map(document.getElementById(mapId), {
        center: loc1,
        zoom: 13,
        mapTypeControl: false,
        streetViewControl: false,
        mapTypeId: google.maps.MapTypeId.ROADMAP

    });
    marker = new google.maps.Marker({
        map: map,
        position: loc1,
        draggable: myMarkerIsDraggable
    });
    
    google.maps.event.addListener(marker, 'dragend', function(evt){
    	$('#'+formId+' #latitude').val(evt.latLng.lat().toFixed(myCoordsLenght));
    	$('#'+formId+' #longitude').val(evt.latLng.lng().toFixed(myCoordsLenght));
    	latitude=evt.latLng.lat().toFixed(myCoordsLenght);
    	longitude=evt.latLng.lng().toFixed(myCoordsLenght);
		 loc1 = new google.maps.LatLng(latitude, longitude);
	});
    google.maps.event.addListener(marker, 'click', function () {

        infowindow.setContent(locname);
        infowindow.open(map, marker);

    });
    google.maps.event.addListener(map, 'click', function () {
        infowindow.close();
    });
};
