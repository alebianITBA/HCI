//BORRAR!!

$(document).ready(function(){

	$.ajax({
		url: "http://eiffel.itba.edu.ar/hci/service3/Order.groovy?method=GetOrderById&username=janedoe&authentication_token=a8c0d2a9d332574951a8e4a0af7d516f&id=1056",
		dataType: "jsonp"
	}).done(function(data){
		console.log("entro!");
		var items = data.order.items;

		var cart="";

		for( var i=0 ; i<items.length ; i++){
			cart+= '<li><img class="cart-image-header" src='+items[i].product.imageUrl+'></li><li class="divider"></li>';
		}
		$("#cantitems").append(items.length);
		$("#cartDropdown").append(cart);
	});
});