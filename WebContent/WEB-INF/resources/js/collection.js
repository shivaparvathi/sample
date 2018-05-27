var existingItemsInArrangement=[];
$(document).ready(function() {
	
	var itemsList=[];
	
	$.fn.multiselect = function() {
		$(this).each(function() {
			var checkboxes = $(this).find("input:checkbox");
			checkboxes.each(function() {
				var checkbox = $(this);
				if (checkbox.attr("checked"))
					checkbox.parent().addClass("multiselect-on");
	 
				checkbox.click(function() {
					if (checkbox.attr("checked"))
						checkbox.parent().addClass("multiselect-on");
					else
						checkbox.parent().removeClass("multiselect-on");
				});
			});
		});
	};
	
	$(".multiselect").multiselect();

	$('#add_collection .btn_add').live('click',function(){
		$(".errorblock").hide();
		$("#create_collection input[type=text]").val("");
		$('#collectionsdescription').val("");
		$('label.error').remove();
		$('#collectionEditForm').css('display','none');
		$('#collectionForm').css('display','block');
		$('#add_arrangements').css('display','none');
		$('#edit_arrangements').css('display','none');
		$(".collection_view").attr('src','../resources/images/arrow_close.png');
		$(".arrangements").remove();
	});
	$('#Add_arrangement_button').live('click',function(){
		$('#Arrangement_name_field').val("");
		$('#collectionForm').css('display','none');
		$('#add_arrangements').css('display','block');
		$('#update_arrangements').css('display','none');
		$(".errorblock").hide();
		$("#collectionForm input[type=text]").val("");
		$('#collectionsdescription').val("");
		$('label.error').remove();
	});
	
	
	//Edit collection and view arrangements
	$(".collection_name").live('click',function(){
		$(".errorblock").hide();
		$('#collectionForm').css('display','none');
		$('#add_arrangements').css('display','none');
		$('#edit_arrangements').css('display','none');
		$('label.error').remove();
		$(".collection_view").attr('src','../resources/images/arrow_close.png');
		var collectionId = $(this).attr("id");
		
		var collection_name = $(this).text();
		$('#collection_name span').text(collection_name);
		$.ajax({
			
			type : 'GET',
			url : "showUpdateCollection?id="+collectionId,
			dataType : 'json',
			async : true,
			cache: false,
			success : function(result) {
				$('#collectionEditForm').css('display','block');
				var collectionData=JSON.stringify(result.collection);
				var arrangementData=JSON.stringify(result.arrangements);
				var data = JSON.parse(collectionData);
				var arrangementData=JSON.parse(arrangementData);
				
				$('#collectionEditForm #collectionId').val(data.collectionId);
				$('#collectionEditForm #update_collectionname').val(data.name);
				$("#collectionEditForm #update_description").text(data.description);
				$('#collectionEditForm select[name="collectionType"]').find("option[value="+data.collectionType+"]").attr("selected","selected");
				var startDate=dateFormat(new Date(data.startDate), "mm/dd/yyyy HH:MM");
				$('#collectionEditForm #update_startdate').val(startDate);
				var endDate = dateFormat(new Date(data.endDate), "mm/dd/yyyy HH:MM");
				$('#collectionEditForm #update_enddate').val(endDate);
				if(data.active==true){
					$('#collectionEditForm input[name="Active"][value=enabled]').attr("checked","checked");	
				}else{
					$('#collectionEditForm input[name="Active"][value=disabled]').attr("checked","checked");
				}
				$('#collectionEditForm input[name="arrangements"]').prop("checked",false).parent().removeClass("multiselect-on");
				for ( var i = 0; i < data.arrangementId.length; i++) {
					$('#collectionEditForm input[name="arrangements"][value="'+ data.arrangementId[i]+ '"]').prop("checked",true).parent().addClass("multiselect-on");
				}
				if(data.channelName==""){
					$('#channelName').val("No channels assigned");
				}else{
					$('#channelName').val(data.channelName);
				}
				
				var arrangements_length=$(this).siblings('.arrangements').length;
				if(arrangements_length!=0){
					$(".collection_view").attr('src','../resources/images/arrow_close.png');
					$(".arrangements").remove();
					return false;
				}else{
					$(".arrangements").remove();
					if(arrangementData.length>0){
						$.each(arrangementData, function(i,data){
							var arrangementNameDiv = $('<a>').attr('name','arrangement_name').attr('id','arrangement_'+data.arrangementId).addClass('arrangement_name').append(data.name);
							var clearDiv = $('<div class="clear"></div>');
							var arrangement_info=$('<div>').addClass('arrangement_info').append(arrangementNameDiv).append(clearDiv);
							var arrangementDiv = $('<div>').addClass('arrangements').attr('id','arrangement_info_'+data.arrangementId).append(arrangement_info);
							$("#collection_info_"+collectionId).append(arrangementDiv);
						});
						}else{
							alert("No arrangements for this Collection");
						}		
					
				}
			}
		});
		$(this).children().attr('src','../resources/images/arrow_open.png');
	});
	
	//collection delete
	$('#edit_collection .delete').live('click',function() {
		
		var value = confirm("Are you sure you want to delete?");
		  if (value){
				
				var collectionId = $('#collectionEditForm #collectionId').val();
				$.ajax({
					type : 'GET',
					url : "deleteCollection?collectionId="
							+ collectionId,
					dataType : 'json',
					async : true,
					cache : false,
					success : function(result) {
						var json = JSON.stringify(result);
						var data = JSON.parse(json);
						
						if(data.message.toLowerCase()=='SUCCESS'.toLowerCase()){
							alert("Collection "+data.message.toLowerCase()+"fully deleted");
							$('#collection_info_'+collectionId).remove();
							$('#collectionEditForm').css('display','none');
							window.location.href=window.location.href;
						}else{
							alert('Unable to delete the Collection');
						}
					}
				});
		  }
		  else
		    return false;

			});

//****Arrangements Create,Edit,Delete//	

	//Create new Arrangement
	$('#arrangement_create').live('click',function(){
		var collectionId=$("#add_arrangement").attr("class");
		   var name=$('#add_arrangements #Arrangement_name_field').val();
		  if(name.length<=0){
			  $('span.error').remove();
			  var error = $('<span>').addClass("error").text("*Please Enter Name").css({'color':'red','float':'right','margin-top':'3px'});
			  $('#add_arrangements .panel #Arrangement_name_field').after(error);
			  return false;
		  }else{
			  $('span.error').remove();
		  }
		  var locationKey=$('#add_arrangements #location option:selected').val();
			 if(locationKey==0)
				 {
				 locationKey=null;
				 }
		  var segmentId = $('#add_arrangements #segment option:selected').val();

	var status=$('#add_arrangements #status option:selected').val();
	if(status=="enable"){
		status=true;
	}else{
		status=false;
	}
	
	
	var postData = {"name":name,
			"locationKey":locationKey,
			"segmentId" : segmentId,
			"active"	: status,
				};
		$(".loader").css('display','block');
		$("#arrangement_create").attr('disabled',true);
		
		$.ajax({
			
			type : 'POST',
			url : "createArrangement",
			dataType : 'json',
			contentType: 'application/json',
			data:JSON.stringify(postData),
			async : true,
			cache : false,
			success : function(result) {
			 $(".loader").css('display','none');
				var json = JSON.stringify(result);
				var result = JSON.parse(json);
				var id = [];
				if(result.status.toLowerCase()=='success'){
					if(result.data.length>0)
					      for ( var i = 0; i < result.data.length; i++) {
					       id = result.data[i];
					      }
						arrangementId = id.arrangementId;
					alert("Arrangement successfully created");
					$("#arrangement_create").attr('disabled',false);
					$("#add_arrangements").css('display', 'none');
					 $('input:checkbox[name=locationcheck]').attr('checked',false);
					 $('input:checkbox[name=segmentcheck]').attr('checked',false);
					 $('input:checkbox[name=updateLocationCheck]').attr('checked',false);
					 $('input:checkbox[name=updateSegmentCheck]').attr('checked',false);
					 $("#segment >option[value='0']").remove();
					   $("#segment").prop('disabled',false);
					   $("#location >option[value='0']").remove(); 
					   $("#location").prop('disabled',false);
					   window.location.href=window.location.href;
					   
				}else if(result.status=='FAILURE'){
					$("#arrangement_create").attr('disabled',false);
					alert("Failed to create arrangement");
				}
				else if(result.status=='RECORD_ALREADY_EXISTS'){
					$("#arrangement_create").attr('disabled',false);
					alert("Arrangement name already Exists");
				}
				
			}
		});
		
	});
	
	//ShowUpdate Arrangements
	$('.arrangement_name').live('click',function(){
		var arrangementId = $(this).attr("id");
		 arrangementId=arrangementId.split("arrangement_")[1];
		 var flag=0;
		$.showArrangementInfo(arrangementId,flag);
	});
	$.showArrangementInfo = function(arrangementId,flag){
		itemsList=[]; 
		existingItemsInArrangement=[];
		$("#edit_arrangementFormBtn #arrangement_update").attr('disabled',true);
		$("#edit_arrangementFormBtn .delete").attr('disabled',true);
		$("#edit_arrangementFormBtn .add_items").attr('disabled',true);
		
		$("#arrangement_loader_"+arrangementId).css('display','block');
		$('<div id="arrangement_overlay" />').css({
		    position: "absolute",
		    width: "100%",
		    height: "100%",
		    top: 0,
		    left: 0,
		    background: 'url("../resources/images/ajax-loader.gif") no-repeat scroll center center #DDE7E8',
		    opacity:0.8
		}).appendTo($("#edit_arrangements").css("position", "relative"));
		$(".errorblock").hide();
		$('#collectionEditForm').css('display','none');
		$('#add_arrangements').css('display','none');
		$('#collectionForm').css('display','none');
		$('#edit_arrangements').css('display','block');
		$('label.error').remove();

		$.ajax({
			type : 'GET',
			url : "showUpdateArrangement?arrangementId="+arrangementId,
			dataType : 'json',
			async : true,
			cache: false,
			success : function(result) {
				itemsList=[]; 
				existingItemsInArrangement=[];
				$("#arrangement_overlay").remove();
				$("#edit_arrangementFormBtn #arrangement_update").attr('disabled',false);
				$("#edit_arrangementFormBtn .delete").attr('disabled',false);
				$("#edit_arrangementFormBtn .add_items").attr('disabled',false);
				$("#arrangement_loader_"+arrangementId).css('display','none');
				var arrangemtJson = JSON.stringify(result.arrangement);
				var itemsArray=result.itemsArray;
				var data = JSON.parse(arrangemtJson);
				$('#edit_arrangements #arrangementId').val(data.arrangementId);
				$('#edit_arrangements span').text(data.name);
				$('#edit_arrangements #name').val(data.name);
				if(data.locationKey==null)
					{
					 $("#select_location").prop('disabled',true);
					   var newOption = $('<option value="0" selected="selected">Default Location</option>');
					   $('#select_location').append(newOption);
					   $('input:checkbox[name=updateLocationCheck]').attr('checked',true);
					}
				else{
					$("#select_location >option[value='0']").remove();
					   $("#select_location").prop('disabled',false);
					   var exist=$('#edit_arrangements select[name="location"]').find("option[value="+data.locationKey+"]").length>0;
					  if(exist==false)
						  {
						  $("#select_location").prop('disabled',true);
						   var newOption = $('<option value="0" selected="selected">Default Location</option>');
						   $('#select_location').append(newOption);
						   $('input:checkbox[name=updateLocationCheck]').attr('checked',true);
						  }
					  else
						  {
						  $('input:checkbox[name=updateLocationCheck]').attr('checked',false);
						  $('#edit_arrangements select[name="location"]').find("option[value="+data.locationKey+"]").attr('selected','selected');
						  }
				
				}
				if(data.segmentId==0){
					 $("#select_segment").prop('disabled',true);
					   var newOption = $('<option value="0" selected="selected">Default Segment</option>');
					   $('#select_segment').append(newOption);
					   $('input:checkbox[name=updateSegmentCheck]').attr('checked',true);
				}
				else
					{
					$("#select_segment >option[value='0']").remove();
					   $("#select_segment").prop('disabled',false);
					var exist=$('#edit_arrangements select[name="segment"]').find("option[value="+data.segmentId+"]").length>0;
					if(exist==false){
						$("#select_segment").prop('disabled',true);
						   var newOption = $('<option value="0" selected="selected">Default Segment</option>');
						   $('#select_segment').append(newOption);
						   $('input:checkbox[name=updateSegmentCheck]').attr('checked',true);
					}
					else{
						  $('input:checkbox[name=updateSegmentCheck]').attr('checked',false);
						$('#edit_arrangements select[name="segment"]').find("option[value="+data.segmentId+"]").attr("selected","selected");
					}
					}
				if(data.active==true){
					$('#edit_arrangements select[name="status"]').find('option[value="enable"]').attr("selected","selected");	
				}else{
					$('#edit_arrangements select[name="status"]').find('option[value="disable"]').attr("selected","selected");
				}
				
				var products=0;
				var coupons=0;
				var kohlscash=0;
				var markdownProducts=0;
					$('#edit_arrangements #couponsPanel').empty();
					$('#edit_arrangements #productsPanel').empty();
					$('#edit_arrangements #kohlsCashPanel').empty();
					$('#edit_arrangements #markdownProductsPanel').empty();
					var item = [];
					if(itemsArray!=null){
					if(itemsArray.length>0){
						for ( var i = 0; i <itemsArray.length; i++) {
							item[i] =itemsArray[i];
							if(item[i].type=="OC"){						
								existingItemsInArrangement.push(item[i].id);
								var itemImage=$('<div id="itemImage"><img  src="'+item[i].iconURL+'"></div>');
								var itemInformation=$('<div id="itemInformation"><label>'+item[i].title+'</label><br/><label>Coupon Code:'+item[i].couponCode+'</label></div>');							
								var value='offer_'+item[i].id+'_delete';
								var image=$('<img>').attr('id',value).attr("src","../resources/images/button_delete.png").attr('onclick','deleteMe(this)');
								var deleteButton=$('<div>').attr('id',"deleteButton").append(image);
								var itemDetails=$('<div>').attr('id',"itemDetails").append(itemImage).append(itemInformation).append(deleteButton);
								var mainDiv=$('<div>').attr('id','offer_'+item[i].id).addClass('OC').append(itemDetails);
								$('#edit_arrangements #couponsPanel').append(mainDiv);
								coupons=coupons+1;
							}
							
							if(item[i].type=="GP"){							
								existingItemsInArrangement.push(item[i].id);
								var itemImage=$('<div id="itemImage"><img  src="'+item[i].imageURL+'"></div>');
								var itemInformation=$('<div id="itemInformation"> <label>'+item[i].title+'</label><br/><label>Web ID:'+item[i].webId+'</label></div>');
								var value='product_'+item[i].id+'_delete';
								var image=$('<img>').attr('id',value).attr("src","../resources/images/button_delete.png").attr('onclick','deleteMe(this)');
								var deleteButton=$('<div>').attr('id',"deleteButton").append(image);
								var itemDetails=$('<div>').attr('id',"itemDetails").append(itemImage).append(itemInformation).append(deleteButton);
								var mainDiv=$('<div>').attr('id','product_'+item[i].id).addClass('GP').append(itemDetails);	
								$('#edit_arrangements #productsPanel').append(mainDiv);
								products=products+1;
									
							}
							if(item[i].type=="CS"){						
								existingItemsInArrangement.push(item[i].id);
								var itemImage=$('<div id="itemImage"><img  src="'+item[i].iconURL+'"></div>');
								var itemInformation=$('<div id="itemInformation"> <label>'+item[i].title+'</label></div>');
								var value='kohlsCash_'+item[i].id+'_delete';
								var image=$('<img>').attr('id',value).attr("src","../resources/images/button_delete.png").attr('onclick','deleteMe(this)');
								var deleteButton=$('<div>').attr('id',"deleteButton").append(image);
								var itemDetails=$('<div>').attr('id',"itemDetails").append(itemImage).append(itemInformation).append(deleteButton);
								var mainDiv=$('<div>').attr('id','kohlsCash_'+item[i].id).addClass('CS').append(itemDetails);
								$('#edit_arrangements #kohlsCashPanel').append(mainDiv);
								kohlscash=kohlscash+1;
								
							}
							if(item[i].type=="MD"){							
								existingItemsInArrangement.push(item[i].id);
								if(item[i].offeredDate==null){
									item[i].offeredDate='';
								}
								if(item[i].offeredPrice==null){
									item[i].offeredPrice='';
								}
								var itemImage=$('<div id="itemImage"><img  src="'+item[i].imageURL+'"></div>');
								var itemInformation=$('<div id="itemInformation"> <label>'+item[i].title+'</label><br/><label>Web ID:'+item[i].webId+'</label></div>');
								var value='markdownProduct_'+item[i].id;
								var deleteImage=$('<img>').attr('id',value+'_delete').attr("src","../resources/images/button_delete.png").attr('onclick','deleteMe(this)');
								var editImage=$('<img>').attr('id',value+'_edit').attr("src","../resources/images/button_edit.png").attr('onclick','editMe(this)');
								var saveImage=$('<img>').attr('id',value+'_save').attr("src","../resources/images/button_save.png").css('display','none').attr('onclick','saveMe(this)');
								var deleteButton=$('<div>').attr('id',"deleteButton").append(editImage).append(saveImage).append(deleteImage);
								var field1=$('<label for="discount_percentage">Discount Percentage</label><input type="text" id="discount_percentage" name="discount_percentage" size="15" readonly="readonly" value="" />');
								var field2=$('<label for="offered_date"> Offered Date </label><input type="text" id="offered_date_'+item[i].id+'" name="offered_date" size="15" value="'+item[i].offeredDate+'" disabled="disabled" >');
								var field3=$('<label for="offered_price">Offered Price</label><input type="text" id="offered_price" name="offered_price" size="15" value="'+item[i].offeredPrice+'" readonly="readonly"/>');
								var markdownInformation=$('<div>').attr('id','markdownInformation').append(field1).append(field2).append(field3);
								var itemDetails=$('<div>').attr('id',"itemDetails").append(itemImage).append(itemInformation).append(markdownInformation).append(deleteButton);
								var mainDiv=$('<div>').attr('id','markdownProduct_'+item[i].id).addClass('MD').append(itemDetails);
								$('#edit_arrangements #markdownProductsPanel').append(mainDiv);
								markdownProducts=markdownProducts+1;
								
						}
							
						}
						if(flag==1){
							alert("Arrangement Updated Successfully");
						}
						productsContainerPagination();
						couponsContainerPagination();
						kohlsCashContainerPagination();
						markdownProductsContainerPagination();
					}
					}
					$("#arrangementBtns #products").text('Products('+products+')');
					$("#arrangementBtns #offers").text('Offers('+coupons+')');
					$("#arrangementBtns #kohlscash").text('Kohls Cash('+kohlscash+')');
					$("#arrangementBtns #markdownProducts").text('Markdown Products('+markdownProducts+')');
					
			}
			
		});
	}
	//Edit Arrangements
	$("#arrangement_update").live('click',function(){
		$('<div id="arrangement_overlay" />').css({
		    position: "absolute",
		    width: "100%",
		    height: "100%",
		    top: 0,
		    left: 0,
		    background: 'url("../resources/images/ajax-loader.gif") no-repeat scroll center center #DDE7E8',
		    opacity:0.8
		}).appendTo($("#edit_arrangements").css("position", "relative"));
		var arrangementId=$('#edit_arrangements #arrangementId').val();
		var name=$('#edit_arrangements #name').val();
		 var locationKey=$('#edit_arrangements #select_location option:selected').val();
		 if(locationKey==0)
		 {
		 locationKey=null;
		 }
		 var segmentId = $('#edit_arrangements #select_segment option:selected').val();
		 var status=$('#edit_arrangements #arrangement_status option:selected').val();
		 if(status=="enable"){
		  status=true;
		 }else{
		  status=false;
		 }
		 			for(var k=0;k<itemsList.length;k++){
		 				existingItemsInArrangement.push(itemsList[k]);
		 			}
				  var object = new Object();
				  object.arrangementID = arrangementId;
				  object.items = existingItemsInArrangement;
				  object.name = name;
				  object.locationKey = locationKey;
				  object.segmentId = segmentId;
				  object.active = status;
				 var jsonfile = {
						    arrangementInfo : JSON.stringify(object)
						   };
					$('#addItemsLoader').css('display','block');
					$("#savearrangement").attr('disabled',true);
		 	 
		 $.ajax({
			 type : 'post',
				url : "updateArrangement",
				dataType : 'json',
				cache: false,
				data:jsonfile,
				success : function(result) {
					$('#addItemsLoader').css('display','none');
					var flag=0;
					var return_result=JSON.stringify(result);
					var data=JSON.parse(return_result);
					if(data.message.toLowerCase()=='failure'){
						$("#save_ajax_loader").css('display','none');
						alert("Failed to Update arrangement ");
						$("#savearrangement").attr('disabled',false);
						$("#overlay").remove();
						$("#arrangements").css('display','none');
						$("#arrangement_overlay").remove();
					}
					else{
						$("#savearrangement").attr('disabled',false);
						$("#save_ajax_loader").css('display','none');
						existingItemsInArrangement = [];
						$("#arrangement_overlay").remove();
						$("#overlay").remove();
						$("#arrangements").css('display','none');
						flag=1;
						$.showArrangementInfo(arrangementId,flag);
					}
				} 
		 });
	});
	$('#locationcheck').change(function(){
	    var check = this.checked ? 0 : 1;
	   if(check==0)
		   {
		   $("#location").prop('disabled',true);
		   var newOption = $('<option value="0" selected="selected">Default Location</option>');
		   $('#location').append(newOption);
		   }
	   if(check==1){
		   $("#location >option[value='0']").remove();
		   $("#location").prop('disabled',false);
	   }
	});
	
	$('#segmentcheck').change(function(){
	    var check = this.checked ? 0 : 1;
	   if(check==0)
		   {
		   $("#segment").prop('disabled',true);
		   var newOption = $('<option value="0" selected="selected">Default Segment</option>');
		   $('#segment').append(newOption);
		   }
	   if(check==1){
		   $("#segment >option[value='0']").remove();
		   $("#segment").prop('disabled',false);
	   }
	});
	
	$('#updateLocationCheck').change(function(){
	    var check = this.checked ? 0 : 1;
	   if(check==0)
		   {
		   $("#select_location").prop('disabled',true);
		   var newOption = $('<option value="0" selected="selected">Default Location</option>');
		   $('#select_location').append(newOption);
		   }
	   if(check==1){
		   $("#select_location >option[value='0']").remove();
		   $("#select_location").prop('disabled',false);
	   }
	});
	
	$('#updateSegmentCheck').change(function(){
	    var check = this.checked ? 0 : 1;
	   if(check==0)
		   {
		  
		   $("#select_segment").prop('disabled',true);
		   var newOption = $('<option value="0" selected="selected">Default Segment</option>');
		   $('#select_segment').append(newOption);
		   }
	   if(check==1){
		   $("#select_segment >option[value='0']").remove();
		   $("#select_segment").prop('disabled',false);
	   }
	});
	
	//Delete Arrangements
	$('#edit_arrangements .delete').live('click',function() {
		var value = confirm("Are you sure you want to delete?");
		  if (value){
				var arrangementId = $("#edit_arrangements #arrangementId").val();
				$.ajax({
					type : 'POST',
					url : "deleteArrangement?arrangementId="
							+arrangementId,
					dataType : 'json',
					async : true,
					cache : false,
					success : function(result) {
						var json = JSON.stringify(result);
						var data = JSON.parse(json);
						if(data.message.toLowerCase()=='SUCCESS'.toLowerCase()){
							alert("Arrangement "+data.message.toLowerCase()+"fully deleted");
							$("#arrangement_info_"+arrangementId).remove();
							$('#edit_arrangements').css('display','none');
							window.location.href=window.location.href;
						}else{
							alert('Unable to delete the Arrangement');
						}
					}
				});
		    }
		  else
			    return false;
		});
//Edit Products of Arrangement
$("#arrangementBtns #products").live("click",function(){
	
	$('#edit_arrangements #productsContainer').css("display","block");
	$('#edit_arrangements #couponsContainer').css("display","none");
	$('#edit_arrangements #kohlsCashContainer').css("display","none");
	$('#edit_arrangements .panel').css("display","none");
	$('#edit_arrangements #markdownProductsContainer').css("display","none");	
	$("#productsPanel").children().each(function(n, i) {
		var itemId = this.id;
		$('#'+itemId).css('display','block').removeClass('jp-hidden');
	});
	productsContainerPagination();	

});

//Edit Offers of Arrangement
$("#arrangementBtns #offers").live("click",function(){
	$('#edit_arrangements #productsContainer').css("display","none");
	$('#edit_arrangements #couponsContainer').css("display","block");
	$('#edit_arrangements #kohlsCashContainer').css("display","none");
	$('#edit_arrangements .panel').css("display","none");
	$('#edit_arrangements #markdownProductsContainer').css("display","none");
	$("#couponsPanel").children().each(function(n, i) {
		var itemId = this.id;
		$('#'+itemId).css('display','block').removeClass('jp-hidden');
	});
	couponsContainerPagination();
});

//Edit details of Arrangement
$("#arrangementBtns #details").live("click",function(){
	$('#edit_arrangements #productsContainer').css("display","none");
	$('#edit_arrangements #couponsContainer').css("display","none");
	$('#edit_arrangements #kohlsCashContainer').css("display","none");
	$('#edit_arrangements .panel').css("display","block");
	$('#edit_arrangements #markdownProductsContainer').css("display","none");
});

//Edit Kohl's Cash of Arrangement
$("#arrangementBtns #kohlscash").live("click",function(){
	$('#edit_arrangements #productsContainer').css("display","none");
	$('#edit_arrangements #couponsContainer').css("display","none");
	$('#edit_arrangements #kohlsCashContainer').css("display","block");
	$('#edit_arrangements .panel').css("display","none");
	$('#edit_arrangements #markdownProductsContainer').css("display","none");
	$("#kohlsCashPanel").children().each(function(n, i) {
		var itemId = this.id;
		$('#'+itemId).css('display','block').removeClass('jp-hidden');
	});
	kohlsCashContainerPagination();
});


//Edit Markdown Products of Arrangement
$("#arrangementBtns #markdownProducts").live("click",function(){
	$('#edit_arrangements #productsContainer').css("display","none");
	$('#edit_arrangements #couponsContainer').css("display","none");
	$('#edit_arrangements #kohlsCashContainer').css("display","none");
	$('#edit_arrangements .panel').css("display","none");
	$('#edit_arrangements #markdownProductsContainer').css("display","block");
	$("#markdownProductsPanel").children().each(function(n, i) {
		var itemId = this.id;
		$('#'+itemId).css('display','block').removeClass('jp-hidden');
	});
	markdownProductsContainerPagination();
});

$('.add_items').click(function(){
	itemsList=[];
	$('#markdownPercentageCheck').prop('checked',false);
	 var overlay = $('<div id="overlay"> </div>');
	 overlay.fadeIn("slow").appendTo(document.body);
	 $("#arrangements").appendTo(document.body);
	 $("#arrangements").css('display', 'block');
	$("#arrangements").css({
		left: ($(window).width() - $('#arrangements').width()) / 2,
		top: ($(window).width() - $('#arrangements').width()) / 12,
		position:'absolute'
		});
	$.products();
	$.offers();
	$.kohlscash();
});

$("#arrangements .productsButton").click(function(){
	$('#markdownPercentageCheck').prop('checked',false);
	$('.markdownPercentage').css('display','none');
		loadItems('products');
});

$("#arrangements .offersButton").click(function(){
		loadItems('offers');
});

$("#arrangements .kohlcashButton").click(function(){
		loadItems('cash');
});

$("#arrangements .markdownButton").click(function(){
	loadItems('markdownProducts');
});


function loadItems(itemType){
	if(itemType == 'products'){
		$("#arrangements .productsButton").attr("id","selected");;		
		$("#arrangements .offersButton").removeAttr("id","selected");
		$("#arrangements .kohlcashButton").removeAttr("id","selected");
		$("#arrangements .markdownButton").removeAttr("id","selected");
		$("#arrangements .list #productlist").css("display","block");
		$("#arrangements .list #offerlist").css("display","none");
		$("#arrangements .list #kohlcashlist").css("display","none");
		$("#arrangements .list #markdownproductlist").css("display","none");
		
		$("#arrangements .list #productsItemContainer").children().each(function(n, i) {
			var itemId = this.id;
			$('#'+itemId).css('display','block').removeClass('jp-hidden');
		});
		arrangementProducts_pagination();
		
	}else if (itemType == 'offers') {
		$("#arrangements .productsButton").removeAttr("id","selected");		
		$("#arrangements .offersButton").attr("id","selected");
		$("#arrangements .kohlcashButton").removeAttr("id","selected");
		$("#arrangements .markdownButton").removeAttr("id","selected");
		$("#arrangements .list #productlist").css("display","none");
		$("#arrangements .list #offerlist").css("display","block");
		$("#arrangements .list #kohlcashlist").css("display","none");
		$("#arrangements .list #markdownproductlist").css("display","none");
		
		$("#arrangements .list #offersItemContainer").children().each(function(n, i) {
			var itemId = this.id;
			$('#'+itemId).css('display','block').removeClass('jp-hidden');
		});
		arrangementOffers_pagination();
	
	}else if (itemType == 'cash') {
		$("#arrangements .productsButton").removeAttr("id","selected");
		$("#arrangements .offersButton").removeAttr("id","selected");
		$("#arrangements .kohlcashButton").attr("id","selected");
		$("#arrangements .markdownButton").removeAttr("id","selected");
		$("#arrangements .list #productlist").css("display","none");
		$("#arrangements .list #offerlist").css("display","none");
		$("#arrangements .list #kohlcashlist").css("display","block");
		$("#arrangements .list #markdownproductlist").css("display","none");
		
		$("#arrangements .list #kohlsCashItemContainer").children().each(function(n, i) {
			var itemId = this.id;
			$('#'+itemId).css('display','block').removeClass('jp-hidden');
		});
		arrangementKohlsCash_pagination();
	}
	else if (itemType == 'markdownProducts') {
		$("#arrangements .productsButton").removeAttr("id","selected");
		$("#arrangements .offersButton").removeAttr("id","selected");
		$("#arrangements .kohlcashButton").removeAttr("id","selected");
		$("#arrangements .markdownButton").attr("id","selected");
		$("#arrangements .list #productlist").css("display","none");
		$("#arrangements .list #offerlist").css("display","none");
		$("#arrangements .list #kohlcashlist").css("display","none");
		$("#arrangements .list #markdownproductlist").css("display","block");
		
		$("#arrangements .list #markdownProductItemContainer").children().each(function(n, i) {
			var itemId = this.id;
			$('#'+itemId).css('display','block').removeClass('jp-hidden');
		});
		arrangementMarkdownProducts_pagination();
	}
	
}

//Close the items popup window
$("#popup_close img").live('click', function(e) {
    $('#overlay').remove();
    $("#arrangements").css('display', 'none');
	$("#arrangements .list #kohlcashlist #kohlsCashIitemContainer").empty();
	$("#arrangements .list #productlist #productsItemContainer").empty();
	$("#arrangements .list #offerlist #offersItemContainer").empty();
	itemsList=[];
});

//Item checkbox click
var count=0;
$(".item_checkBox").live('click', function(){
	var itemId = this.id.split('item_')[1];
    var index=0; 
	if (this.checked){
		index=$.inArray(parseInt(itemId),existingItemsInArrangement);
		if(index!=-1){
			alert("This Item is already added to the arrangement");
			$(this).prop('checked',false);
			$(this).blur();
		}else{
			itemsList.push(parseInt(itemId));
			count++;
		}
		
    } else {
    	var index = $.inArray(itemId, itemsList);
    	itemsList.splice(index, 1);
    	count--;
    }
 
  });

//Save arrangement items
$("#savearrangement").click(function(){
	   if(itemsList.length==0){
		   alert("Please select atleast one item");
		   return false;
	   }
	   else{
		   $("#arrangement_update").trigger("click");
	   }
	 
});
	
	$("#startdate").datetimepicker();
	$("#enddate").datetimepicker();
	$("#update_startdate").datetimepicker();
	$("#update_enddate").datetimepicker();
	
	$("#startdate").change(function(){
		$("#collectionForm").validate().element("#startdate");
	});
	$("#enddate").change(function(){
		$("#collectionForm").validate().element("#enddate");
	});
	$("#update_startdate").change(function(){
		$("#collectionEditForm").validate().element("#update_startdate");
	});
	$("#update_enddate").change(function(){
		$("#collectionEditForm").validate().element("#update_enddate");
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
function bindingArrangements(name,arrangementId,collectionId){
	 var arrangementNameDiv = $('<a>').attr('name','arrangement_name').addClass('arrangement_name').append(name);
		var clearDiv = $('<div class="clear"></div>');
		var arrangement_info=$('<div>').addClass('arrangement_info').append(arrangementNameDiv).append(clearDiv);
		var arrangementDiv = $('<div>').addClass('arrangements').attr('id','arrangement_info_'+arrangementId).append(arrangement_info);
		$(".collection_options").css('display','none');
		$("#collection_info_"+collectionId).append(arrangementDiv);
}

(function($, W, D) {
	var JQUERY4U = {};

	JQUERY4U.UTIL = {
		setupFormValidation : function() {
			$("#collectionForm").validate({

				rules : {
					name : "required",
					description : "required",
					startDate : {
						required:true,
						startDateValidation:true
					},
					endDate : {
						required : true,
						endDate : true
					},
					arrangements : {
						checkbox : true
					}
				},
				messages : {
					name : "Please Enter Collection Name",
					description : "Please Enter Collection Description",
					startDate : {
						required:"Please Select Start Date",
					},
					endDate : {
						required : "Please Select End Date"
					}
				},
			   errorPlacement: function(error, element) {
				        if (element.hasClass("arrangement")) {
				        	error.insertAfter( element.parent().parent("div.multiselect"));
				        }
				        else{
				        	error.insertAfter(element);
				        }
				    },
				submitHandler : function(form) {
					form.submit();
				}
			});
			
			$("#collectionEditForm").validate({
				rules : {
					name : "required",
					description : "required",
					startDate : {
						required:true,
						startDateValidation:true
					},
					endDate : {
						required : true,
						endDate : true

					},
					arrangements : {
						checkbox : true
					}

				},
				messages : {
					name : "Please Enter Collections Name",
					description : "Please Enter Collections Description",
					startDate :{
						required: "Please Select Start Date",
					},
					endDate : {
						required : "Please Select End Date"
					}
				},
				errorPlacement: function(error, element) {
			        if (element.hasClass("arrangement")) {
			        	error.insertAfter( element.parent().parent("div.multiselect"));
			        }
			        else{
			        	error.insertAfter(element);
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
	
	jQuery.validator.addMethod("startDateValidation",function(value, element) {
		  var todayDate=new Date();
		  var seconds = todayDate.getSeconds();
		  todayDate=parseInt(todayDate.getTime()/1000,10);
		  var date=Date.parse(value)/1000;
		  return (date>=(todayDate-seconds));
	},"Please Enter Today's Date or Future Date");
	
	jQuery.validator.addMethod("checkbox", function(value, element) {
		var fields = $("input[name='arrangements']").serializeArray();
		if (fields.length == 0){
			return false;
		}
		else{
			return true;
		}
	}, "Please Select atleast one arrangement ");
	
	jQuery.validator.addMethod("endDate", function(value, element) {
		var startDate = $('#startdate').val();
		if (!startDate) {
			startDate = $('#update_startdate').val();
		}
		return (Date.parse(startDate)/1000) < (Date.parse(value)/1000);
	}, "End date must be greater than start date");
	
})(jQuery, window, document);

function deleteMe(e){
	var response = confirm("Are you sure you want to delete?");
	if (response){
		itemId=e.id.split('_delete')[0];
		$("#"+itemId).remove();
		var index = $.inArray(parseInt(itemId.split('_')[1]), existingItemsInArrangement);
		existingItemsInArrangement.splice(index, 1);
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
	    expireYear = parseInt(expireDate[2], 10), // cast Strings as Numbers
	    expireMo = parseInt(expireDate[0], 10),
	    expireDay = parseInt(expireDate[1], 10);

		var now = new Date(),
	    nowYear = now.getFullYear(),
	    nowMo = now.getMonth() + 1, // for getMonth(), January is 0
	    nowDay = now.getDate();
		if (nowYear > expireYear ||
			    nowYear == expireYear && nowMo > expireMo ||
			    nowYear == expireYear && nowMo == expireMo && nowDay > expireDay) {

			$("<div>").addClass("failuremessage").text("Please Select Today or Future Date").insertAfter("#"+itemId+" #markdownInformation #offered_date_"+itemID);
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
		if(!(numberRegex.test(offered_price))&& offered_price.length>0) {
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
		 dateValidate='false';
		 var numberRegex =  /^\d+(\.\d+)?$/;
		 if(discount_percentage.length<=0)
			 {
			 $("<div>").addClass("failuremessage").text("Please Enter Discount_percentage").insertAfter("#"+itemID+" #markdownEditInformation #discount_percentage");
			 }
		 else {
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
		 else{
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
		 if((numberRegex.test(discount_percentage)) && (numberRegex.test(offered_price))&&dateValidate=='true') {
			 $("#"+itemID+" #markdownEditInformation").css('display','none');
			 $("#"+itemID+" #markdownInformation").css('display','block');
			 $("#"+itemID+" #markdownInformation #discount_percentage" ).val(discount_percentage );
			 $("#"+itemID+" #markdownInformation #offeredDate_"+itemID ).val(offeredDate);
			 $("#"+itemID+" #markdownInformation #offered_price_"+itemID ).val(offered_price );
			 $("#"+itemID+" #deleteButton").css('display','block');
			 $("#"+itemID+" #edit_deleteButton").css('display','none');
		 }
	}
