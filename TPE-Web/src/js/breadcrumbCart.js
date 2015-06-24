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

$(document).ready(function() {
	//alert("hola");
	//alert(window.history.back());
	// var oldURL = document.referrer.href;
	// alert(oldURL);
	// var pageURL = $(location).attr("href");
	// alert(pageURL);

	var id= GetURLParameter('id');

	$("#breadcrumb").append('<li><a href="index.html">Inicio</a></li>');
	if(typeof id !== "undefined"){
		$.ajax({
			url: 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id='+id, dataType:"jsonp"
		}).done(function(data){			
			$("#breadcrumb").append('<li><a href="product.html?id='+id+'">'+data.product.name+'</a></li>');
			$("#breadcrumb").append('<li class="active">Carrito de Compras</li>');
		});

	}
	else{
		$("#breadcrumb").append('<li class="active">Carrito de Compras</li>');
	}
});