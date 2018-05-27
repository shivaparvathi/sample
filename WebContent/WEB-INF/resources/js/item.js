$(document).ready(function() {
	
	var productArray = [];
	var offerArray = [];
	var kohlcashArray = [];
	var markdownproductArray = [];
	
	$( ".productSearch" ).autocomplete({
		  source: productArray
	 });
	
	$( ".offerSearch" ).autocomplete({
		  source: offerArray
	 });
	
	$( ".kohlcashSearch" ).autocomplete({
		  source: kohlcashArray
	 });
	$( ".markdownproductSearch" ).autocomplete({
		  source: markdownproductArray
	 });
	
	var productSearchData;
	var offerSearchData;
	var kohlcashSearchData;
	
	//Markdown products checkbox click
	$('#markdownPercentageCheck').change(function(){
		if ($('#markdownPercentageCheck').is(':checked')) {
			$('.GP').show();
			 $('.GP').removeClass('jp-hidden');
		    $('.markdownPercentage').css('display','block');
		} else {
		    $('.GP').show();
		    $('.GP').removeClass('jp-hidden');
			$('.markdownPercentage').css('display','none');
		} 
		arrangementProducts_pagination();
	});

	//Get products
	$.products = function(){
		$('#productlist #productsItemContainer').empty();
		$('#markdownproductlist #markdownProductItemContainer').empty();
		$(".loader-overlay").css("display","block");
		$.ajax({
			type : 'GET',
			url : 'getProducts?limit=-1&offset=-1',
			dataType : 'json',
			async : true,
			success : function(result) {
			if(productArray.length > 0){
				productArray = [];
			}
			if(markdownproductArray.length > 0){
				markdownproductArray = [];
			}
			$(".loader-overlay").css("display","none");
				var productData = result.data[0].data;
				var status = result.status;
				if(status == 'SUCCESS'){
					ListProductsUI(productData);
				}else if(status == 'NO_DATA'){
					$('#productlist #productsItemContainer').append('<div>').text("Products Data Not Available");
				}else if(status == 'FAILURE'){
					
				}
			}
		});
		};

		function ListProductsUI(productData){
			productSearchData = productData;
			$('#productlist #productsItemContainer').empty();
			$('#markdownproductlist #markdownProductItemContainer').empty();
			if(productData.length > 0){			
				$.each(productData, function (i, data) {
					
					if (data.type=="GP") {
						if(data.title!=null){
							productArray.push(data.title);
						}
						$('#productlist #productsItemContainer').append('<div id="'+data.id+'" class="GP" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img  src="'+data.imageURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Web ID:'+data.webId+'</label></div></div><div class="markdown-loader-overlay"><img src="../resources/images/ajax-loader.gif"></div><div class="markdownPercentage" id="'+data.webId+'"><input type="button" value="Markdown"></div></div>');
					}else if (data.type=="MD") {
					if(data.offeredDate==null){
							data.offeredDate="";
						}
						if(data.offeredPrice==null){
							data.offeredPrice="";
						}
						if(data.title!=null){
							markdownproductArray.push(data.title);
						}
					
						$('#markdownproductlist #markdownProductItemContainer').append('<div id="'+data.id+'" class="MD" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img src="'+data.imageURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Web ID:'+data.webId+'</label></div><div id="markdownInformation" style="float:left;width:250px;"><label for="discount_percentage">Discount Percentage</label><input type="text" id="discount_percentage" name="discount_percentage" size="15" value="" readonly="readonly" /><label for="offeredDate"> Offered Date </label><input type="text" id="offeredDate_'+data.id+'" value="'+data.offeredDate+'" name="offered_date" size="15" readonly="readonly"><label for="offered_price">Offered Price</label><input type="text" id="offered_price_'+data.id+'" name="offered_price" readonly="readonly" size="15" value="'+data.offeredPrice+'"/></div><div id="markdownEditInformation" style="float:left;width:270px;display:none;"><label for="discount_percentage">Discount Percentage</label><input type="text" id="discount_percentage" name="discount_percentage" size="15" value=""/><label for="offered_Date"> Offered Date </label><input type="text" id="offered_Date_'+data.id+'" value="'+data.offeredDate+'" name="offered_date" size="15" readonly="readonly"><label for="offered_price">Offered Price</label><input type="text" id="offered_price_'+data.id+'" name="offered_price" "size="15" value="'+data.offeredPrice+'"/></div><div id="deleteButton"><img id="markdownProduct_'+data.id+'_edit" src="../resources/images/button_edit.png" onClick="popupItemEditMe(this)"><img id="markdownProduct_'+data.id+'_delete" src="../resources/images/button_delete.png" onClick="deleteMe(this)"></div><div id="edit_deleteButton" style="display:none"><img id="markdownProduct_'+data.id+'_edit" src="../resources/images/button_save.png" onClick="popupItemSaveMe(this)"><img id="markdownProduct_'+data.id+'_delete" src="../resources/images/button_delete.png" onClick="deleteMe(this)"></div></div></div>');
					}
				});
				}
			arrangementProducts_pagination();
			arrangementMarkdownProducts_pagination();
		};
		
		//Get offers
		$.offers = function(){
		$('#offerlist #offersItemContainer').empty();
		$("#offers-loader-overlay").css("display","block");
		$.ajax({
			type : 'GET',
			url : 'getOffer?limit=-1&offset=-1',
			dataType : 'json',
			async : true,
			success : function(result) {
				if(offerArray.length > 0){
					offerArray = [];
				}
				$("#offers-loader-overlay").css("display","none");
				var offerData = result.data[0].data;
				var status = result.status;
				if(status == 'SUCCESS'){
					ListOffersUI(offerData);
				}else if(status == 'NO_DATA'){
					$('#offerlist #offersItemContainer').append('<div>').text("Offers Data Not Available");
				}else if(status == 'FAILURE'){
					
				}
			}
		});
		};

		function ListOffersUI(offerData){
			offerSearchData = offerData;
			$('#offerlist #offersItemContainer').empty();
			if(offerData.length>0){
				$.each(offerData, function (i, data) {
					offerArray.push(data.title);
					$('#offerlist #offersItemContainer').append('<div id="'+data.id+'" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img  src="'+data.iconURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Coupon Code:'+data.couponCode+'</label></div></div></div>');
				});
			}
			arrangementOffers_pagination();
		};

		//Get kohlscash
		$.kohlscash = function(){
		$('#kohlcashlist #kohlsCashItemContainer').empty();
		$("#kohlsCash-loader-overlay").css("display","block");
		$.ajax({
			type : 'GET',
			url : 'getkohlscash?limit=-1&offset=-1',
			dataType : 'json',
			async : true,
			success : function(result) {
				if(kohlcashArray.length > 0){
					kohlcashArray = [];
				}
				$("#kohlsCash-loader-overlay").css("display","none");
				var cashData = result.data[0].data;
				var status = result.status;
				if(status == 'SUCCESS'){
					ListKohlCashUI(cashData);
				}else if(status == 'NO_DATA'){
					$('#kohlcashlist #kohlsCashItemContainer').append('<div>').text("Kohls Cash Data Not Available");
				}else if(status == 'FAILURE'){
					
				}
			}
		});
		};
		function ListKohlCashUI(cashData){
			kohlcashSearchData = cashData;
			$('#kohlcashlist #kohlsCashItemContainer').empty();
			if(cashData.length>0){
				$.each(cashData, function (i, data) {
					kohlcashArray.push(data.title);
					$('#kohlcashlist #kohlsCashItemContainer').append('<div id="'+data.id+'" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img  src="'+data.iconURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Coupon Code:'+data.couponCode+'</label></div></div></div>');
				});
			}
			arrangementKohlsCash_pagination();
		};
		
		
		//Product Items Search

		$("#productNameSearch").click(function(){
			var searchString = $.trim($('.productSearch').val());    
			searchString=searchString.toLowerCase();
			$('#productlist #productsItemContainer').empty();
			$('#markdownPercentageCheck').prop('checked',false);
			 var count=0; 
			if(productSearchData.length > 0){			
				$.each(productSearchData, function (i, data) {
					if(data.title!=null){
						if((data.title.toLowerCase()).indexOf(searchString)!=-1){
							if (data.type=="GP") {
								$('#productlist #productsItemContainer').append('<div id="'+data.id+'" class="GP" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img  src="'+data.imageURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Web ID:'+data.webId+'</label></div></div><div class="markdown-loader-overlay"><img src="../resources/images/ajax-loader.gif"></div><div class="markdownPercentage" id="'+data.webId+'"><input type="button" value="Markdown"></div></div>');
								count++;
							}
					 	}
					}else if(searchString ==''){
						if (data.type=="GP") {
							$('#productlist #productsItemContainer').append('<div id="'+data.id+'" class="GP" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img  src="'+data.imageURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Web ID:'+data.webId+'</label></div></div><div class="markdown-loader-overlay"><img src="../resources/images/ajax-loader.gif"></div><div class="markdownPercentage" id="'+data.webId+'"><input type="button" value="Markdown"></div></div>');
						}
					}

				 	
				});
				  if(count==0&&searchString!=""){
						errorDiv=$('<div>').text("No such products exist");
						$('#productsItemContainer').append(errorDiv);
					}
				}
			arrangementProducts_pagination();
			
		});
		
		//Markdown Product Items Search
		$("#markdownproductNameSearch").click(function(){
			var searchString = $.trim($('.markdownproductSearch').val());    
			searchString=searchString.toLowerCase();
			$('#markdownproductlist #markdownProductItemContainer').empty();
			var count=0;
			if(productSearchData.length > 0){			
				$.each(productSearchData, function (i, data) {
					if(data.title!=null){
						if((data.title.toLowerCase()).indexOf(searchString)!=-1){
							if (data.type=="MD") {
								if(data.offeredDate==null){
									data.offeredDate="";
								}
								if(data.offeredPrice==null){
									data.offeredPrice="";
								}
								$('#markdownproductlist #markdownProductItemContainer').append('<div id="'+data.id+'" class="MD" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img src="'+data.imageURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Web ID:'+data.webId+'</label></div><div id="markdownInformation" style="float:left;width:250px;"><label for="discount_percentage">Discount Percentage</label><input type="text" id="discount_percentage" name="discount_percentage" size="15" value="" readonly="readonly" /><label for="offeredDate"> Offered Date </label><input type="text" id="offeredDate_'+data.id+'" value="'+data.offeredDate+'" name="offered_date" size="15" readonly="readonly"><label for="offered_price">Offered Price</label><input type="text" id="offered_price_'+data.id+'" name="offered_price" readonly="readonly" size="15" value="'+data.offeredPrice+'"/></div><div id="markdownEditInformation" style="float:left;width:270px;display:none;"><label for="discount_percentage">Discount Percentage</label><input type="text" id="discount_percentage" name="discount_percentage" size="15" value=""/><label for="offered_Date"> Offered Date </label><input type="text" id="offered_Date_'+data.id+'" value="'+data.offeredDate+'" name="offered_date" size="15" readonly="readonly"><label for="offered_price">Offered Price</label><input type="text" id="offered_price_'+data.id+'" name="offered_price" "size="15" value="'+data.offeredPrice+'"/></div><div id="deleteButton"><img id="markdownProduct_'+data.id+'_edit" src="../resources/images/button_edit.png" onClick="popupItemEditMe(this)"><img id="markdownProduct_'+data.id+'_delete" src="../resources/images/button_delete.png" onClick="deleteMe(this)"></div><div id="edit_deleteButton" style="display:none"><img id="markdownProduct_'+data.id+'_edit" src="../resources/images/button_save.png" onClick="popupItemSaveMe(this)"><img id="markdownProduct_'+data.id+'_delete" src="../resources/images/button_delete.png" onClick="deleteMe(this)"></div></div></div>');
								count++;
							}
					 	}
					}else if(searchString == ''){
						if (data.type=="MD") {
							if(data.offeredDate==null){
								data.offeredDate="";
							}
							if(data.offeredPrice==null){
								data.offeredPrice="";
							}
							$('#markdownproductlist #markdownProductItemContainer').append('<div id="'+data.id+'" class="MD" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img src="'+data.imageURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Web ID:'+data.webId+'</label></div><div id="markdownInformation" style="float:left;width:250px;"><label for="discount_percentage">Discount Percentage</label><input type="text" id="discount_percentage" name="discount_percentage" size="15" value="" readonly="readonly" /><label for="offeredDate"> Offered Date </label><input type="text" id="offeredDate_'+data.id+'" value="'+data.offeredDate+'" name="offered_date" size="15" readonly="readonly"><label for="offered_price">Offered Price</label><input type="text" id="offered_price_'+data.id+'" name="offered_price" readonly="readonly" size="15" value="'+data.offeredPrice+'"/></div><div id="markdownEditInformation" style="float:left;width:270px;display:none;"><label for="discount_percentage">Discount Percentage</label><input type="text" id="discount_percentage" name="discount_percentage" size="15" value=""/><label for="offered_Date"> Offered Date </label><input type="text" id="offered_Date_'+data.id+'" value="'+data.offeredDate+'" name="offered_date" size="15" readonly="readonly"><label for="offered_price">Offered Price</label><input type="text" id="offered_price_'+data.id+'" name="offered_price" "size="15" value="'+data.offeredPrice+'"/></div><div id="deleteButton"><img id="markdownProduct_'+data.id+'_edit" src="../resources/images/button_edit.png" onClick="popupItemEditMe(this)"><img id="markdownProduct_'+data.id+'_delete" src="../resources/images/button_delete.png" onClick="deleteMe(this)"></div><div id="edit_deleteButton" style="display:none"><img id="markdownProduct_'+data.id+'_edit" src="../resources/images/button_save.png" onClick="popupItemSaveMe(this)"><img id="markdownProduct_'+data.id+'_delete" src="../resources/images/button_delete.png" onClick="deleteMe(this)"></div></div></div>');
						}
					}
				 	
				});
				if(count==0&&searchString!=""){
					errorDiv=$('<div>').text("No such Markdown Products exist");
					$('#markdownProductItemContainer').append(errorDiv);
				}
				}
			arrangementMarkdownProducts_pagination();
			
		});
		//Offer Items Search
		$("#offerNameSearch").click(function(){
			
			var searchString = $.trim($('.offerSearch').val());       
			searchString=searchString.toLowerCase();
			$('#offerlist #offersItemContainer').empty();
			var count=0;
			if(offerSearchData.length > 0){			
				$.each(offerSearchData, function (i, data) {
					if(data.title!=null){
						if((data.title.toLowerCase()).indexOf(searchString)!=-1){
							$('#offerlist #offersItemContainer').append('<div id="'+data.id+'" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img  src="'+data.iconURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Coupon Code:'+data.couponCode+'</label></div></div></div>');
							count = count+1;
						}
					}else if(searchString == ''){
						$('#offerlist #offersItemContainer').append('<div id="'+data.id+'" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img  src="'+data.iconURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Coupon Code:'+data.couponCode+'</label></div></div></div>');
					}

				 	
				});
				  if(count==0&&searchString!=""){
						errorDiv=$('<div>').text("No such offers exist");
						$('#offersItemContainer').append(errorDiv);
					}
				}
			arrangementOffers_pagination();
			
		});

		//Kohls Cash Items Search
		$("#kohlcashNameSearch").click(function(){
			
			var searchString = $.trim($('.kohlcashSearch').val());       
			searchString=searchString.toLowerCase();
			$('#kohlcashlist #kohlsCashItemContainer').empty();
			var count=0;
			
			if(kohlcashSearchData.length > 0){			
				$.each(kohlcashSearchData, function (i, data) {
					if(data.title!=null){
						if((data.title.toLowerCase()).indexOf(searchString)!=-1){
							$('#kohlcashlist #kohlsCashItemContainer').append('<div id="'+data.id+'" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img  src="'+data.iconURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Coupon Code:'+data.couponCode+'</label></div></div></div>');
							count++;
						}
					}else if(searchString == ''){
						$('#kohlcashlist #kohlsCashItemContainer').append('<div id="'+data.id+'" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img  src="'+data.iconURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Coupon Code:'+data.couponCode+'</label></div></div></div>');
					}
				 	
				});
				  if(count==0&&searchString!=""){
						errorDiv=$('<div>').text("No such offers exist");
						$('#kohlsCashItemContainer').append(errorDiv);
					}
				}
			arrangementKohlsCash_pagination();
			
		});
		
		//Add MarkDown Product
		$(".markdownPercentage input").live('click',function(){
			$(this).parent().siblings(".markdown-loader-overlay").css('display','block');
			var jsonObject=new Object();
			jsonObject.webId=$(this).parent().attr('id');
			$.ajax({
				type : 'POST',
				url : 'addMarkdownProduct',
				contentType:'application/json',
				dataType : 'json',
				data:JSON.stringify(jsonObject),
				async : true,
				success : function(result) {
					$(".markdownPercentage input").parent().siblings(".markdown-loader-overlay").css('display','none');
					if(result.status.toUpperCase()=='SUCCESS'){
						$('#markdownPercentageCheck').prop('checked',false);
						var data = result.data[0];
						if(data.offeredDate==null){
							data.offeredDate="";
						}
						if(data.offeredPrice==null){
							data.offeredPrice="";
						}
						$('#markdownproductlist #markdownProductItemContainer').prepend('<div id="'+data.id+'" class="MD" style="height: 50px;margin: 10px;"><div id="itemCheckbox"><input type="checkbox" class="item_checkBox" id="item_'+data.id+'"></div><div id="itemDetails"><div id="itemImage"><img src="'+data.imageURL+'"></div><div id="itemInformation"> <label>'+data.title+'</label><br/><label>Web ID:'+data.webId+'</label></div><div id="markdownInformation" style="float:left;width:250px;"><label for="discount_percentage">Discount Percentage</label><input type="text" id="discount_percentage" name="discount_percentage" size="15" value="" readonly="readonly" /><label for="offeredDate"> Offered Date </label><input type="text" id="offeredDate_'+data.id+'" value="'+data.offeredDate+'" name="offered_date" size="15" readonly="readonly"><label for="offered_price">Offered Price</label><input type="text" id="offered_price_'+data.id+'" name="offered_price" readonly="readonly" size="15" value="'+data.offeredPrice+'"/></div><div id="markdownEditInformation" style="float:left;width:270px;display:none;"><label for="discount_percentage">Discount Percentage</label><input type="text" id="discount_percentage" name="discount_percentage" size="15" value=""/><label for="offered_Date"> Offered Date </label><input type="text" id="offered_Date_'+data.id+'" value="'+data.offeredDate+'" name="offered_date" size="15" readonly="readonly"><label for="offered_price">Offered Price</label><input type="text" id="offered_price_'+data.id+'" name="offered_price" "size="15" value="'+data.offeredPrice+'"/></div><div id="deleteButton"><img id="markdownProduct_'+data.id+'_edit" src="../resources/images/button_edit.png" onClick="popupItemEditMe(this)"><img id="markdownProduct_'+data.id+'_delete" src="../resources/images/button_delete.png" onClick="deleteMe(this)"></div><div id="edit_deleteButton" style="display:none"><img id="markdownProduct_'+data.id+'_edit" src="../resources/images/button_save.png" onClick="popupItemSaveMe(this)"><img id="markdownProduct_'+data.id+'_delete" src="../resources/images/button_delete.png" onClick="deleteMe(this)"></div></div></div>');
						 $("#markdownEditInformation #offered_date_"+data.id).datepicker();
						$(".markdownButton").trigger('click');
					}else if(result.status.toUpperCase()=='FAILURE'){
						alert("Failed to Save the product as Markdown product");
					}else if(result.status.toUpperCase()=='CONSTRAINT_VIOLATION'){
						alert("Markdown Product Exist for this product");
					}
				}
			});
		});
		
});
