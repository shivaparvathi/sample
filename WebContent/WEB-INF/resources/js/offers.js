$(document).ready(function() {
	$('#add_offers .btn_add').live('click',function(){
		$('label.error').remove();
		$("#addofferForm input[type='text']").val("");
		$('#add_offers #addofferForm').css('display','block');
		$('#editofferForm').css('display','none');

	});
	
	$('#addofferForm  #offer_submit').live('click',function(){
		$('#addofferForm .errorblock').hide();
		
	});
	
	$("#edit_offer_submit").live('click',function(){
		$('#editofferForm .errorblock').hide();
	});
	
	$('#offers_list .imgbutton .edit').live('click',function(){
		$('#add_offers #addofferForm').css('display','none');
	});
	//showing alert message to user on delete button click
	$('#delete').live('click', function(){
		var value = confirm("Are you sure you want to delete?");
		  if (value)
		      return true;
		  else
		    return false;
	});
	

	
	$("#edit_offer_submit","#offer_submit").click(function(){
		$(".errorblock").css('display','none');
	});	
	$("#startDate").datetimepicker();
	$("#expiryDate").datetimepicker();
	$("#edit_startDate").datetimepicker();
	$("#edit_expiryDate").datetimepicker();
	
	$("#startDate").change(function(){
		$("#addofferForm").validate().element("#startDate");
	});
	$("#expiryDate").change(function(){
		$("#addofferForm").validate().element("#expiryDate");
	});
	
	$("#edit_startDate").change(function(){
		$("#editofferForm").validate().element("#edit_startDate");
	});
	$("#edit_expiryDate").change(function(){
		$("#editofferForm").validate().element("#edit_expiryDate");
	});
	
	//Showing the offer image on view image button click. 
	$("#offers_list .col.c4 #viewimage").live("click",function(){	
		var offername=$(this).parent().siblings('.col.c1').text();
		$("#offerOverviewImage .panel_header").text(offername);
		var imgDiv = $('<div>').attr('id','imagepreview_popup_close');
		imgDiv.append($('<img>').attr('src','../resources/images/cancel.jpeg'));
		$('#offerOverviewImage .panel_header').append(imgDiv);
		var imageUrl=$(this).siblings().val();
		$('#offerOverviewImage .panel img').removeAttr("src").attr("src", "");
		$('#offerOverviewImage .panel img').attr('src',imageUrl);
		
		var overlay_image = $('<div id="overlay_image"> </div>');
		overlay_image.fadeIn("slow").appendTo(document.body);
		$("#offerOverviewImage").appendTo(document.body);
		$("#offerOverviewImage").css('display', 'block');
    
			var h = $(window).height();
			var z = $('#offerOverviewImage').css('height');
			z = z.split('px');
			z = z[0];
			
			var top = (h / 4) - (z / 4);

			$("#offerOverviewImage").css({
				'position' : 'absolute',
				'top' : top,
				'left' : '38%'
			});
	});
	
	$('#imagepreview_popup_close img,#offerOverviewImage .panel .submit').live('click',function(){
		$('#offerOverviewImage').css('display','none');
		$('#overlay_image').remove();
	});
	
	
});

//validating the create/update offer form on submit button click 
(function($, W, D) {
	var JQUERY4U = {};

	JQUERY4U.UTIL = {
		setupFormValidation : function() {
			$("#addofferForm").validate({
				rules : {
					couponCode : {
					      required : true,
					      numericsOnly : true,
					      minlength: 15,
					      maxlength: 15,
					      
						  },
				    title:{
						   required : true
						   },
					description:{
					      required : true
						   },		 
					iconURL : {
					      required : true,
					      urlvalidation:true
					     },
					startDate : {
					      required : true,
					      startDateValidation:true
					     },
					expiryDate : {
						required : true,
						endDate:true
					},
					discountPercentage:{
						required : true,
						floatValues: true
					},
					passbookURL:{
						required : true,
						urlvalidation:true
					},
					feedImageURL:
						{
						required:true,
						urlvalidation:true
						},

				},

				messages : {
					
					couponCode: {
		                 required :"Please Enter Coupon Code",
		                minlength :"Coupon Code length should be 15",
		                maxlength:"Coupon Code length should be 15"
	                  },
	                title: {
		                 required :"Please Enter Offer Title"
	                  },
	                description: {
			                 required :"Please Enter Offer Description"
		              },  
	                iconURL: {
		                required :"Please Enter Offer Icon URL"
		                
	                },
	                startDate: {
						required : "Please Enter Start Date"
					  },
					expiryDate: {
						required : "Please Enter Expiry Date"
					  }, 
					discountPercentage:{
						required :"Please Enter Offer Discount",
						floatValues	: "Please use Float Values"	
					  },
					passbookURL: {
		               		required :"Please Enter Offer Passbook URL"
	                  },
	                  feedImageURL:	{
	                	  required :"Please Enter Feed Image URL"
                },

				},
				submitHandler : function(form) {
					form.submit();
				}
			});
			$("#editofferForm").validate({
				
				rules : {
					couponCode : {
					      required : true,
					      numericsOnly : true,
					      minlength: 15,
					      maxlength: 15
						  },
				    title:{
						   required : true
						   },
					description:{
					      required : true
						   },		 
					startDate : {
					      required : true,
					      startDateValidation:true
					     },
					expiryDate : {
						required : true,
						endDate:true
					},
					discountPercentage:{
						required : true,
						floatValues: true
					},
					passbookURL:{
						required : true,
						urlvalidation:true
					},
					feedImageURL:
						{
						required:true,
						urlvalidation:true
						},
						
						iconURL : {
						      required : true,
						      urlvalidation:true
						     },
				},

				messages : {
					couponCode: {
		                 required :"Please Enter Coupon Code",
		                 minlength :"Coupon Code length should be 15",
			                maxlength:"Coupon Code length should be 15"
	                  },
	                title: {
		                 required :"Please Enter Offer Title"
	                  },
	                description: {
			                 required :"Please Enter Offer Description"
		              },  
	                startDate: {
						required : "Please Enter Start Date"
					  },
					expiryDate: {
						required : "Please Enter Expiry Date"
					  }, 
					discountPercentage:{
						required :"Please Enter Offer Discount",
						floatValues	: "Please use Float Values"	
					  },
					  feedImageURL:	{
	                	  required :"Please Enter Feed Image URL"
					  },
					  passbookURL: {
						  required :"Please Enter Offer Passbook URL"
					  },
					  iconURL: {
	                required :"Please Enter Offer Icon URL"
	                
					  },
              
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
	jQuery.validator.addMethod("startDateValidation",function(value, element) {
		var todayDate=new Date();
		var seconds = todayDate.getSeconds();
		todayDate=parseInt(todayDate.getTime()/1000,10);
		var date=Date.parse(value)/1000;
		return (date>=(todayDate-seconds));
	},"Please Enter Today Date or Future Date");
	jQuery.validator.addMethod("floatValues", function( value, element ) {
		return this.optional(element)
		  || /^[+-]?\d+(\.\d+)?$/i.test(value);
    });
	jQuery.validator.addMethod("specialChars", function( value, element ) {
	    var regex = new RegExp("^[a-zA-Z_ ]+$");
	    var key = value;

	    if (!regex.test(key)) {
	       return false;
	    }
	    return true;
	}, "Please use only alphabetic characters");
	
	jQuery.validator.addMethod("numericsOnly", function( value, element ) {
	    var regex = new RegExp("^[0-9 ]+$");
	    var key = value;

	    if (!regex.test(key)) {
	       return false;
	    }
	    return true;
	}, "Please use only Numeric values");

	jQuery.validator.addMethod("endDate", function(value, element) {
		var startDate = $('#startDate').val();
		if(!startDate){
			startDate = $('#edit_startDate').val();
		}
	    return (Date.parse(startDate)/1000) < (Date.parse(value)/1000);
	}, "End date must be greater than start date");
	jQuery.validator.addMethod("urlvalidation",
			function(value, element) {
		return /^(http|https):\/\/[^\/.][^ ""]+[^\/.]\.(?:jpg|jpeg|png|bmp|JPEG|JPG|PNG|BMP|Jpg|Jpeg|Png|Bmp)$/i
		  .test(value);
	},"Please Enter Valid Image URL");
	jQuery.validator.addMethod("passbookurlvalidation",
			function(value, element) {
		return /^(http|https):\/\/[^\/.][^ ""]+[^\/.]$/i
		  .test(value);
	},"Please Enter Valid Image URL");
})(jQuery, window, document);
