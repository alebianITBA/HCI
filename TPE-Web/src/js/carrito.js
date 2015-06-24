var authToken = $.cookies.get("authToken");
var userdata = $.cookies.get("userdata");


$(document).ready(function() {
	$('#payBtn').click(pagar);
	var cart= $.cookies.get("cart");
	if(cart==null){
		$('#payTable').hide();
		var message='<h3>No hay productos en el carrito</h3>'
		$("#message").append(message);	
		return;
	}
	var products;
	var totalprice=0;
	var totalqty=0
	var help=0;
	for(var j=0; j<cart.length;j++){
		help+=j;
	}
	for( var i=0; i< cart.length; i++){
		$.ajax({
			url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id="+cart[i].id, dataType: "jsonp"
		}).done(function(data) {
			var productId=data.product;
			var index = getIndex(productId.id);
			var prodCart = $.cookies.get("cart")[index];
			var price= productId.price;
			var quantity= prodCart.cantidad;
			var pricetotal=price*quantity;
			products=
			'<tr>'+'<td><img class="cart-image" src='+productId.imageUrl[0]+'></td>'+
			'<td>'+productId.name+'</td>'+
			'<td>'+productId.attributes[3].values+'</td>'+
			'<td>'+prodCart.color+'</td>'+
			'<td>'+price+'</td>'+
			'<td>'+quantity+'</td>'+
			'<td>'+pricetotal+'</td>'+
			'<td><button type="submit" class="btn btn-pad btn-danger" onclick="removeProduct('+productId.id+');"><span class="glyphicon glyphicon-trash"></span></button></td></tr>';
			// console.log(products);
			totalprice+=parseInt(pricetotal);
			totalqty+=parseInt(quantity);
			// console.log("precio parcial:"+totalprice);
			$("#productcart").append(products);	
			console.log("index:"+index);
			help-=index;
			if(help==0){
				console.log(totalprice);
				$("#itemcant").append(totalqty);
				$("#totalprice").append(totalprice);
			}
			// console.log(cart.length);
		});
}
});

function GetURLParameter(sParam) {
	var sPageURL = window.location.search.substring(1);
	var sURLVariables = sPageURL.split('&');
	for (var i = 0; i < sURLVariables.length; i++) {
		var sParameterName = sURLVariables[i].split('=');
		if (sParameterName[0] == sParam) {
			return sParameterName[1];
		}
	}
}

function getIndex(id){
	var cart=$.cookies.get("cart");
	for(var i=0 ; i< cart.length ; i++){
		if(cart[i].id == id){
			return i;
		}
	}
}

function removeProduct(id){
	var cart=$.cookies.get("cart");
	for(var i=0 ; i< cart.length ; i++){
		if(cart[i].id == id){
			cart.splice(i,1);
		}
	}
	$.cookies.set("cart",cart);
	if(cart.length==0){
		$.cookies.set("cart",null);
	}
	window.location="cart.html";
}

function pagar(){
	//verificar que haya items en el carrito
	var cart = $.cookies.get("cart");
	if(cart!=null){
		var orderId;
		var createOrderUrl = "http://eiffel.itba.edu.ar/hci/service3/Order.groovy?method=CreateOrder&username="+userdata.username+"&authentication_token="+authToken;
		$.ajax({
			url: createOrderUrl, dataType:"jsonp"
		}).done(function(data){
			orderId=data.order.id;
			var aux=0;
			for( var j=0 ; j<cart.length ; j++){
				aux+=j;
			}
			console.log(aux);
			for( var i=0 ; i<cart.length ; i++){
				var item = cart[i];
				var addItemToOrderUrl = 'http://eiffel.itba.edu.ar/hci/service3/Order.groovy?method=AddItemToOrder&username='+userdata.username+'&authentication_token='+authToken+'&order_item={"order":{"id":'+orderId+'},"product":{"id":'+item.id+'},"quantity":'+item.cantidad+'}';
				$.ajax({
					url: addItemToOrderUrl, dataType: "jsonp"
				}).done(function(data){

					var index = getIndex(data.orderItem.product.id);
					aux-=index;
					if(aux==0){
						$.cookies.set("cart",null);
						window.location="pay.html?order="+orderId;						
					}
					console.log(i);
				});
			}
			// alert("Creando Orden");
			// $.cookies.set("cart",null);
			// window.location="pay.html?order="+orderId;
		});
	}else{
		//mostrar mensaje NoHayElementosEnElCarrito
	}
}
