$(document).ready(function() {
	pagination();
});

function pagination(){
	$("div.wrapper .holder").jPages({
		containerID : "itemContainer",
		perPage : 10,
		startPage : 1,
		startRange : 1,
		midRange : 2,
		endRange : 1
	});
	
}

function productsContainerPagination(){

	$("div#productsContainer .holder").jPages({
		containerID : "productsPanel",
		perPage : 5,
		startPage : 1,
		startRange : 1,
		midRange : 2,
		endRange : 1
	});

}
function couponsContainerPagination(){

	$("div#couponsContainer .holder").jPages({
		containerID : "couponsPanel",
		perPage : 5,
		startPage : 1,
		startRange : 1,
		midRange : 2,
		endRange : 1
	});
	
}
function kohlsCashContainerPagination(){
	$("div#kohlsCashContainer .holder").jPages({
		containerID : "kohlsCashPanel",
		perPage : 5,
		startPage : 1,
		startRange : 1,
		midRange : 2,
		endRange : 1
	});
}

function markdownProductsContainerPagination(){
	$("div#markdownProductsContainer .holder").jPages({
		containerID : "markdownProductsPanel",
		perPage : 5,
		startPage : 1,
		startRange : 1,
		midRange : 2,
		endRange : 1
	});
}

function arrangementProducts_pagination(){
	$("div#productlist .holder").jPages({
		containerID : "productsItemContainer",
		perPage : 5,
		startPage : 1,
		startRange : 1,
		midRange : 2,
		endRange : 1
	});

}

function arrangementOffers_pagination(){
	$("div#offerlist .holder").jPages({
		containerID : "offersItemContainer",
		perPage : 5,
		startPage : 1,
		startRange : 1,
		midRange : 2,
		endRange : 1
	});

}

function arrangementKohlsCash_pagination(){
	$("div#kohlcashlist .holder").jPages({
		containerID : "kohlsCashItemContainer",
		perPage : 5,
		startPage : 1,
		startRange : 1,
		midRange : 2,
		endRange : 1
	});

}
function arrangementMarkdownProducts_pagination(){
	$("div#markdownproductlist .holder").jPages({
		containerID : "markdownProductItemContainer",
		perPage : 5,
		startPage : 1,
		startRange : 1,
		midRange : 2,
		endRange : 1
	});
	
}


