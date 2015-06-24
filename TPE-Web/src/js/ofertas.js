$(document).ready(function(){
	$.ajax({
		url: 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllProducts&filters=[{"id":5,"value":"Oferta"}]', dataType: "jsonp"
	}).done(function(data) {
		
		var prod1= '<a href="product.html?id='+data.products[0].id+'"><div class="thumbnail producto producto-index sec-color"><img src='+data.products[0].imageUrl[0]+'>'+'</img>'+'<h5>'+data.products[0].name+'</h5></div></a>';
		$("#producto1").append(prod1);
		
		var prod2= '<a href="product.html?id='+data.products[2].id+'"><div class="thumbnail producto producto-index sec-color"><img src='+data.products[2].imageUrl[0]+'>'+'</img>'+'<h5>'+data.products[2].name+'</h5></div></a>';
		$("#producto2").append(prod2);

		var prod3= '<a href="product.html?id='+data.products[3].id+'"><div class="thumbnail producto producto-index sec-color"><img src='+data.products[3].imageUrl[0]+'>'+'</img>'+'<h5>'+data.products[3].name+'</h5></div></a>';
		$("#producto3").append(prod3);

	});

	 $.ajax({
	 	url: 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id=197', dataType: "jsonp"
	 }).done(function(data){
	 	var car1= '<a href="product.html?id='+data.product.id+'"> <img src="'+data.product.imageUrl[0]+'"alt="Zapatos" class="img_carousel"><div class="carousel-caption"><h1>Zapatos</h1></div></a>';
		$("#carousel1").append(car1);
	 });

	 $.ajax({
	 	url: 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id=420', dataType: "jsonp"
	 }).done(function(data){
	 	var car2= '<a href="product.html?id='+data.product.id+'"> <img src="'+data.product.imageUrl[0]+'"alt="Zapatos" class="img_carousel"><div class="carousel-caption"><h1>Accesorios</h1></div></a>';
		$("#carousel2").append(car2);
	 });

	 $.ajax({
	 	url: 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id=34', dataType: "jsonp"
	 }).done(function(data){
	 	var car3= '<a href="product.html?id='+data.product.id+'"> <img src="'+data.product.imageUrl[0]+'"alt="Zapatos" class="img_carousel"><div class="carousel-caption"><h1>Ofertas</h1></div></a>';
		$("#carousel3").append(car3);
	 });
//$("#oferta").append(ofertas);







	// 	var cam= '<img src='+data.product.imageUrl[0]+'>'+'</img>'+'<h5>'+data.product.name+'</h5>';

	// 	$("#camisa").append(cam);
	// });
	// $.ajax({
	// 	url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id=233", dataType: "jsonp"
	// }).done(function(data) {
	// 	var pollera= '<img src='+data.product.imageUrl[0]+'>'+'</img>'+'<h5>'+data.product.name+'</h5>';

	// 	$("#pollera").append(pollera);
	// });
	// $.ajax({
	// 	url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id=418", dataType: "jsonp"
	// }).done(function(data) {
	// 	var anteojo= '<img src='+data.product.imageUrl[0]+'>'+'</img>'+'<h5>'+data.product.name+'</h5>';

	// 	$("#anteojo").append(anteojo);
	// });
	// $.ajax({
	// 	url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id=281", dataType: "jsonp"
	// }).done(function(data) {
	// 	var zapatilla= '<img src='+data.product.imageUrl[0]+'>'+'</img>'+'<h5>'+data.product.name+'</h5>';

	// 	$("#zapatilla").append(zapatilla);
	// });

	// $.ajax({
	// 	url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id=270", dataType: "jsonp"
	// }).done(function(data) {
	// 	var sandalia = '<img src='+data.product.imageUrl[0]+'>'+'</img>'+'<h5>'+data.product.name+'</h5>';

	// 	$("#sandalia").append(sandalia);
	// });

	// $.ajax({
	// 	url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id=273", dataType: "jsonp"
	// }).done(function(data) {
	// 	var zapatillaBebe= '<img src='+data.product.imageUrl[0]+'>'+'</img>'+'<h5>'+data.product.name+'</h5>';

	// 	$("#bebe").append(zapatillaBebe);
	// });

//});

});
