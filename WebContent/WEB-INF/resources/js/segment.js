var existingItemsInSegment=[];
$(document).ready(function() {
	
	var itemsList=[];


	$("#markdownProductsPanel").children().each(function(n, i) {
		var itemId = this.id.split('_')[1];
		existingItemsInSegment.push(parseInt(itemId));
	});	
	
	$("#productsPanel").children().each(function(n, i) {
		var itemId = this.id.split('_')[1];
		existingItemsInSegment.push(parseInt(itemId));
	});
	
	$("#couponsPanel").children().each(function(n, i) {
		var itemId = this.id.split('_')[1];
		existingItemsInSegment.push(parseInt(itemId));
	});
	
	$("#kohlsCashPanel").children().each(function(n, i) {
		var itemId = this.id.split('_')[1];
		existingItemsInSegment.push(parseInt(itemId));
	});
	
	
	$('#segForm #user_submit').live('click',function(){
		$('#segForm .errorblock').hide();
		$('#edit_segments').hide();
	});
	$('#create_segment .btn_add').live('click',function(){
		$('label.error').remove();
		$("#create_segment #segForm input[type='text']").val("");
		$("#create_segment #segForm textarea").val("");
		
		$('#create_segment #segForm').css('display','block');
		$('#edit_segments').hide();
	});
	$('#segment_list .imgbutton .edit_segment').live('click',function(){
		$('#create_segment #segForm').css('display','hide');
	});
	$('#delete').live('click', function(){
		var value = confirm("All the users attached to the Segment will have no Segment afterwards");
		  if (value)
		      return true;
		  else
		    return false;
	});
	
	//Segment Details
	$('#details').live('click',function(){
		$('#updateButton').css({'display':'block',"float":"right"});
		$('#segmentItemsButton').css('display','none');
		
		$("#update_seg").css("display","block");
		$("#productsContainer").css("display","none");
		$("#couponsContainer").css("display","none");
		$("#kohlsCashContainer").css("display","none");
		$("#markdownProductsContainer").css("display","none");
	});
	
	//Segment Products
	$('#products').live('click',function(){
		$('#updateButton').css('display','none');
		$('#segmentItemsButton').css({'display':'block',"float":"right"});
		$("#update_seg").css("display","none");
		$("#productsContainer").css("display","block");
		$("#couponsContainer").css("display","none");
		$("#kohlsCashContainer").css("display","none");
		$("#markdownProductsContainer").css("display","none");
		$("#productsPanel").children().each(function(n, i) {
			var itemId = this.id;
			$('#'+itemId).css('display','block').removeClass('jp-hidden');
		});
		productsContainerPagination();
		
		
	});
	
	//Segment Offers
	$('#offers').live('click',function(){
		$('#updateButton').css('display','none');
		$('#segmentItemsButton').css({'display':'block',"float":"right"});
		$("#update_seg").css("display","none");
		$("#productsContainer").css("display","none");
		$("#couponsContainer").css("display","block");
		$("#kohlsCashContainer").css("display","none");
		$("#markdownProductsContainer").css("display","none");
		$("#couponsPanel").children().each(function(n, i) {
			var itemId = this.id;
			$('#'+itemId).css('display','block').removeClass('jp-hidden');
		});
		couponsContainerPagination();
	});

	//Segment KohlsCash
	$('#kohlscash').live('click',function(){
		$('#updateButton').css('display','none');
		$('#segmentItemsButton').css({'display':'block',"float":"right"});
		$("#update_seg").css("display","none");
		$("#productsContainer").css("display","none");
		$("#couponsContainer").css("display","none");
		$("#kohlsCashContainer").css("display","block");
		$("#markdownProductsContainer").css("display","none");
		$("#kohlsCashPanel").children().each(function(n, i) {
			var itemId = this.id;
			$('#'+itemId).css('display','block').removeClass('jp-hidden');
		});
		kohlsCashContainerPagination();
	});
	
	//Segment Markdown Products
	$('#markdownProducts').live('click',function(){
		$('#updateButton').css('display','none');
		$('#segmentItemsButton').css({'display':'block',"float":"right"});
		$("#update_seg").css("display","none");
		$("#productsContainer").css("display","none");
		$("#couponsContainer").css("display","none");
		$("#kohlsCashContainer").css("display","none");
		$("#markdownProductsContainer").css("display","block");
		$("#markdownProductsPanel").children().each(function(n, i) {
			var itemId = this.id;
			$('#'+itemId).css('display','block').removeClass('jp-hidden');
		});
		markdownProductsContainerPagination();
	});
	
	//Save segment items
	$("#edit_segments .segmentItemsButton").click(function(){
		var segmentId=$("#segForm input[name='id']").val();
		var products=0;
		var coupons=0;
		var kohlscash=0;
		var markdownProducts=0;
			$("#productsPanel").children().each(function(n, i) {
				var itemId = ((this.id).split('product_'))[1];
				var index = $.inArray(itemId, itemsList);
				if(index == -1){
					itemsList.push(itemId);
					products = products+1;	
				}
			});
			
			$("#couponsPanel").children().each(function(n, i) {
				var itemId = ((this.id).split('offer_'))[1];
				var index = $.inArray(itemId, itemsList);
				if(index == -1){
					itemsList.push(itemId);
					coupons = coupons+1;
				}
			});
			
			$("#kohlsCashPanel").children().each(function(n, i) {
				var itemId = ((this.id).split('kohlsCash_'))[1];
				var index = $.inArray(itemId, itemsList);
				if(index == -1){
					itemsList.push(itemId);
					kohlscash = kohlscash+1;
				}
			});
			
			$("#markdownProductsPanel").children().each(function(n, i) {
				var itemId = ((this.id).split('markdownProduct_'))[1];
				var index = $.inArray(itemId, itemsList);
				if(index == -1){
					itemsList.push(itemId);
					markdownProducts = markdownProducts+1;
				}
			});
			
			  var object = new Object();
			  object.segmentId = segmentId;
			  object.items = itemsList;
			 var jsonfile = {
					    segmentInfo : JSON.stringify(object)
					   };
	$('#addItemsLoader').css('display','block');
	$("#savearrangement").attr('disabled',true);
	 $.ajax({
		 type : 'post',
			url : "updateItemsInSegment",
			dataType : 'json',
			cache: false,
			data:jsonfile,
			success : function(result) {
				$('#addItemsLoader').css('display','none');
				var return_result=JSON.stringify(result);
				var data=JSON.parse(return_result);		
				
				if(data.message.toLowerCase()=='failure'){
					$("#savearrangement").attr('disabled',false);
					alert("Failed to Add Items to segment ");
				}
				else{
					alert("Items Successfully Added to Segment");
				}
				
				$('#edit_segments').css('display','none');
				$("#segmentBtns #products").text('Products('+products+')');
				 $("#segmentBtns #offers").text('Offers('+coupons+')');
				 $("#segmentBtns #kohlscash").text('Kohls Cash('+kohlscash+')');
				 $("#segmentBtns #markdownProducts").text('Markdown Products('+markdownProducts+')');
				 window.location.href=window.location.href;
			}	
	 });
	});
	
	//Show items popup window in segments
	$('.add_segmentItems').click(function(){
		itemsList = [];
		$('#markdownPercentageCheck').prop('checked',false);
		 var overlay = $('<div id="overlay"> </div>');
		  overlay.fadeIn("slow").appendTo(document.body);
		  $("#segmentItems").appendTo(document.body);
		  $("#segmentItems").css('display', 'block');
		$("#segmentItems").css({
			left: ($(window).width() - $('#segmentItems').width()) / 2,
			top: (($(window).height() - $('#segmentItems').height()) / 2)+( $(window).scrollTop()),
			position:'absolute'
			});
		$.products();
		$.offers();
		$.kohlscash();
	});

	$("#segmentItems .productsButton").click(function(){
		$('#markdownPercentageCheck').prop('checked',false);
		$('.markdownPercentage').css('display','none');	
		loadItems('products');
	});

	$("#segmentItems .offersButton").click(function(){
			loadItems('offers');
	});

	$("#segmentItems .kohlcashButton").click(function(){
			loadItems('cash');
	});
	$("#segmentItems .markdownButton").click(function(){
		loadItems('markdownProducts');
	});

	
	loadItems = function(itemType){
		if(itemType == 'products'){
			$("#segmentItems .productsButton").attr("id","selected");;		
			$("#segmentItems .offersButton").removeAttr("id","selected");
			$("#segmentItems .kohlcashButton").removeAttr("id","selected");
			$("#segmentItems .markdownButton").removeAttr("id","selected");
			$("#segmentItems .list #productlist").css("display","block");
			$("#segmentItems .list #offerlist").css("display","none");
			$("#segmentItems .list #kohlcashlist").css("display","none");
			$("#segmentItems .list #markdownproductlist").css("display","none");
			
			$("#segmentItems .list #productsItemContainer").children().each(function(n, i) {
				var itemId = this.id;
				$('#'+itemId).css('display','block').removeClass('jp-hidden');
			});
			arrangementProducts_pagination();
		}else if (itemType == 'offers') {
			$("#segmentItems .productsButton").removeAttr("id","selected");		
			$("#segmentItems .offersButton").attr("id","selected");
			$("#segmentItems .kohlcashButton").removeAttr("id","selected");
			$("#segmentItems .markdownButton").removeAttr("id","selected");
			$("#segmentItems .list #productlist").css("display","none");
			$("#segmentItems .list #offerlist").css("display","block");
			$("#segmentItems .list #kohlcashlist").css("display","none");
			$("#segmentItems .list #markdownproductlist").css("display","none");
			$("#segmentItems .list #offersItemContainer").children().each(function(n, i) {
				var itemId = this.id;
				$('#'+itemId).css('display','block').removeClass('jp-hidden');
			});
			arrangementOffers_pagination();
		}else if (itemType == 'cash') {
			$("#segmentItems .productsButton").removeAttr("id","selected");
			$("#segmentItems .offersButton").removeAttr("id","selected");
			$("#segmentItems .kohlcashButton").attr("id","selected");
			$("#segmentItems .markdownButton").removeAttr("id","selected");
			$("#segmentItems .list #productlist").css("display","none");
			$("#segmentItems .list #offerlist").css("display","none");
			$("#segmentItems .list #kohlcashlist").css("display","block");
			$("#segmentItems .list #markdownproductlist").css("display","none");
			$("#segmentItems .list #kohlsCashItemContainer").children().each(function(n, i) {
				var itemId = this.id;
				$('#'+itemId).css('display','block').removeClass('jp-hidden');
			});
			arrangementKohlsCash_pagination();
			
		}
		else if (itemType == 'markdownProducts') {
			$("#segmentItems .productsButton").removeAttr("id","selected");
			$("#segmentItems .offersButton").removeAttr("id","selected");
			$("#segmentItems .kohlcashButton").removeAttr("id","selected");
			$("#segmentItems .markdownButton").attr("id","selected");
			$("#segmentItems .list #productlist").css("display","none");
			$("#segmentItems .list #offerlist").css("display","none");
			$("#segmentItems .list #kohlcashlist").css("display","none");
			$("#segmentItems .list #markdownproductlist").css("display","block");
			
			$("#segmentItems .list #markdownProductItemContainer").children().each(function(n, i) {
				var itemId = this.id;
				$('#'+itemId).css('display','block').removeClass('jp-hidden');
			});
			arrangementMarkdownProducts_pagination();
		}
		
	};
	
	//Close the items popup window
	$("#popup_close img").live('click', function(e) {
	    $('#overlay').remove();
	     $("#segmentItems").css('display', 'none');
		$("#segmentItems .list #kohlcashlist #kohlsCashIitemContainer").empty();
		$("#segmentItems .list #productlist #productsItemContainer").empty();
		$("#segmentItems .list #offerlist #offersItemContainer").empty();
		
	});

	//Item checkbox click
	$(".item_checkBox").live('click', function(){
		var itemId = this.id.split('item_')[1];
	    var index=0; 
		if (this.checked){
			index=$.inArray(parseInt(itemId),existingItemsInSegment);
			if(index!=-1){
				alert("This Item is already added to the arrangement");
				$(this).prop('checked',false);
				$(this).blur();
			}else{
				itemsList.push(itemId);	
			}
	    } else {
	    	var index = $.inArray(itemId, itemsList);
	    	itemsList.splice(index, 1);
	    }
	  });

	//Save segment items
	$("#saveSegmentItems").click(function(){
		   if(itemsList.length==0){
			   alert("Please select atleast one item");
			   return false;
		   }
		   else{
		 $("#edit_segments .segmentItemsButton").trigger("click");
		   }
	});
$(".productSearch").keyup(function(event){
	     if(event.keyCode == 13){
	         $("#productNameSearch").click();
	     }
	 });
	$(".offerSearch").keyup(function(event){
	     if(event.keyCode == 13){
	         $("#offerNameSearch").click();
	     }
	 });
	$(".kohlcashSearch").keyup(function(event){
	     if(event.keyCode == 13){
	         $("#kohlcashNameSearch").click();
	     }
	 });
	$(".markdownproductSearch").keyup(function(event){
	     if(event.keyCode == 13){
	         $("#markdownproductNameSearch").click();
	     }
	 });
});	


//Delete segment items
function deleteMe(e){
	var response = confirm("Are you sure you want to delete?");
	if (response){
		itemId=e.id.split('_delete')[0];
		$("#"+itemId).remove();
		var index = $.inArray(parseInt(itemId.split('_')[1]), existingItemsInSegment);
		existingItemsInSegment.splice(index, 1);
	}
}

function editMe(e){
	itemId=e.id.split('_edit')[0];
	var itemID=itemId.split('markdownProduct_')[1];
	$("#"+itemId+" input[type='text']" ).prop("readonly",false);
	$("#"+itemId+" #deleteButton #"+itemId+"_edit").css("display","none");
	$("#"+itemId+" #deleteButton #"+itemId+"_save").css("display","block");
	
	$("#"+itemId+" #offered_date_"+itemID).removeAttr('disabled');
	$("#offered_date_"+itemID).datepicker();
	$("#offered_date_"+itemID).datepicker('enable');	
}
function saveMe(e){
	
	itemId=e.id.split('_save')[0];
	$("#"+itemId+" #markdownInformation .failuremessage").remove();
	var itemID=itemId.split('markdownProduct_')[1];
	var discount_percentage=$("#"+itemId+" #discount_percentage").val();
	var offered_date=$("#"+itemId+" #offered_date_"+itemID).val();
	
	var offered_price=$("#"+itemId+" #offered_price").val();
	var numberRegex =  /^\d+(\.\d+)?$/;
	var dateValidate='false';
	
	if(discount_percentage.length<=0){
		$("<div>").addClass("failuremessage").text("Enter Discount Percentage").insertAfter("#"+itemId+" #markdownInformation #discount_percentage");
	}
	else{
		if(!(numberRegex.test(discount_percentage))) {
			$("<div>").addClass("failuremessage").text("Please Enter Numerics Only").insertAfter("#"+itemId+" #markdownInformation #discount_percentage");
			}
	}
	if(offered_date.length<=0){
		$("<div>").addClass("failuremessage").text("Enter Offered Date").insertAfter("#"+itemId+" #markdownInformation #offered_date_"+itemID);
	}
	else{
		var expireDate = offered_date.split('/'),
	    expireYear = parseInt(expireDate[2], 10), 
	    expireMo = parseInt(expireDate[0], 10),
	    expireDay = parseInt(expireDate[1], 10);

		var now = new Date(),
	    nowYear = now.getFullYear(),
	    nowMo = now.getMonth() + 1,
	    nowDay = now.getDate();
		if (nowYear > expireYear ||
			    nowYear == expireYear && nowMo > expireMo ||
			    nowYear == expireYear && nowMo == expireMo && nowDay > expireDay) {

			$("<div>").addClass("failuremessage").text("Please Select Today's Date or Future Date").insertAfter("#"+itemId+" #markdownInformation #offered_date_"+itemID);
			dateValidate='false';
			}
		else{
			dateValidate='true';
		}
		
	}
	if(offered_price.length<=0){
		$("<div>").addClass("failuremessage").text("Enter Offered Price").insertAfter("#"+itemId+" #markdownInformation #offered_price");
	}
	else{
		if(!(numberRegex.test(offered_price))) {
		$("<div>").addClass("failuremessage").text("Please Enter Numerics Only").insertAfter("#"+itemId+" #markdownInformation #offered_price");		  
		}
	}
	if(numberRegex.test(discount_percentage) && numberRegex.test(offered_price)&&(dateValidate=='true')) {
		$("#"+itemId+" input[type='text']" ).prop("readonly",true);
		$("#"+itemId+" #deleteButton #"+itemId+"_edit").css("display","block");
		$("#"+itemId+" #deleteButton #"+itemId+"_save").css("display","none");
		$("#"+itemId+" #offered_date_"+itemID).datepicker('disable');
	}
}
function popupItemEditMe(e){
	
	 itemId=e.id.split('_edit')[0];
	 
	 var itemID=itemId.split('markdownProduct_')[1];
	 
	 $('#'+itemID+" #markdownInformation").css('display','none');
	 $('#'+itemID+" #deleteButton").css('display','none');
	 $('#'+itemID+" #markdownEditInformation").css('display','block');
	 $('#'+itemID+" #edit_deleteButton").css('display','block');
	 $("#markdownEditInformation #offered_Date_"+itemID).datepicker();
	
	}
	function popupItemSaveMe(e){
		 itemId=e.id.split('_edit')[0];
		 var itemID=itemId.split('markdownProduct_')[1];
		
		 $("#"+itemID+" #markdownEditInformation .failuremessage").remove();
		 var discount_percentage=$("#"+itemID+" #markdownEditInformation #discount_percentage").val();
		 
		 var offered_price=$("#"+itemID+" #markdownEditInformation #offered_price_"+itemID).val();
		 var offeredDate =$("#"+itemID+" #markdownEditInformation #offered_Date_"+itemID).val();
		 var numberRegex =  /^\d+(\.\d+)?$/;
		 dateValidate='false';
		 if(discount_percentage.length<=0)
		 {
		 $("<div>").addClass("failuremessage").text("Please Enter Discount_percentage").insertAfter("#"+itemID+" #markdownEditInformation #discount_percentage");
		 }
		 else 
		 {
		if (!(numberRegex.test(discount_percentage))) 
		{
			$("<div>").addClass("failuremessage").text("Please Enter Numerics Only").insertAfter("#"+itemID+" #markdownEditInformation #discount_percentage");
		 }	 
		 }
		 if(offered_price.length<=0)
		 	{
		 $("<div>").addClass("failuremessage").text("Please Enter OfferedPrice").insertAfter("#"+itemID+" #markdownEditInformation #offered_price_"+itemID);    
		 	}
		 else{
			 if(!(numberRegex.test(offered_price)))
			 {
			 $("<div>").addClass("failuremessage").text("Please Enter Numerics Only").insertAfter("#"+itemID+" #markdownEditInformation #offered_price_"+itemID);
			 }
		 		}
		 if(offeredDate.length<=0)
		 		{
		 $("<div>").addClass("failuremessage").text("Please Enter OfferDate").insertAfter("#"+itemID+" #markdownEditInformation #offered_Date_"+itemID);
		 		}		
		 else
		 		{
		  var expireDate = offeredDate.split('/'),
		     expireYear = parseInt(expireDate[2], 10), 
		     expireMo = parseInt(expireDate[0], 10),
		     expireDay = parseInt(expireDate[1], 10);
		  var now = new Date(),
		     nowYear = now.getFullYear(),
		     nowMo = now.getMonth() + 1,
		     nowDay = now.getDate();
		  if (nowYear > expireYear ||
		       nowYear == expireYear && nowMo > expireMo ||
		       nowYear == expireYear && nowMo == expireMo && nowDay > expireDay) {
		   $("<div>").addClass("failuremessage").text("Please Select Today's Date or Future Date").insertAfter("#"+itemID+" #markdownEditInformation #offered_Date_"+itemID);
		   dateValidate='false';
		   }
		  else{
		   dateValidate='true';
		  }
		 }
		 if(numberRegex.test(discount_percentage) && numberRegex.test(offered_price)&&dateValidate=='true') {
			 $("#"+itemID+" #markdownEditInformation").css('display','none');
			 $("#"+itemID+" #markdownInformation").css('display','block');
			 $("#"+itemID+" #markdownInformation #discount_percentage" ).val(discount_percentage );
			 $("#"+itemID+" #markdownInformation #offeredDate_"+itemID ).val(offeredDate );
			 $("#"+itemID+" #markdownInformation #offered_price_"+itemID ).val(offered_price );
			 $("#"+itemID+" #deleteButton").css('display','block');
			 $("#"+itemID+" #edit_deleteButton").css('display','none');
		 }
	}
(function($, W, D) {
		var JQUERY4U = {};

		JQUERY4U.UTIL = {
			setupFormValidation : function() {
				$("#create_segment #segForm").validate({
					rules : {
						name: {
						      required : true,
						      specialChars: true
						     },
						description : "required"
					},

					messages : {
						name :{
			                   required :"Please Enter Segment Name",
			                  },
						 
						description : "Please Enter Segment Description",
					},
					submitHandler : function(form) {
						form.submit();
					}
				});

				$("#edit_segments #segForm").validate({
					rules : {
						name: {
						      required : true,
						      specialChars: true
						     },

						description : "required"
					},

					messages : {
						name :{
			                   required :"Please Enter Segment Name",
			                  },

						description : "Please Enter Segment Description",
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
		jQuery.validator.addMethod("specialChars", function( value, element ) {
	        var regex = new RegExp("^[a-zA-Z0-9_ ]+$");
	        var key = value;

	        if (!regex.test(key)) {
	           return false;
	        }
	        return true;
	    }, "Please use only alphanumeric or alphabetic characters");
	})(jQuery, window, document);
