
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
	

	$('#talle-color').blur(talleValidation);
	$('#color-select').blur(colorValidation);
	$('#cantidad').blur(cantidadValidation);
	$('#add-to-cart').click(cartValidation);



	$.ajax({
		url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id="+GetURLParameter("id"), dataType: "jsonp"
	}).done(function(data) {
		
		var length = data.product.imageUrl.length;

		for(var i=0; i<length;i++){

			var img ='<img  class="img-product-view" id="product-ind-photo" onclick="changePhoto('+GetURLParameter("id")+','+i+','+length+');" src='+ data.product.imageUrl[i] + ' >';
			
			$("#product-photo").append(img);
		}
		
		
		// var imgPrincipal = $('<img  name="imgPrincipal" class="img-product"  src='+ data.product.imageUrl[0] + '>');
		// $("#product-main-photo").append(imgPrincipal);

		for(var k=0;k<length;k++){
			var imgPrincipal = $('<img  name="imgPrincipal" class="img-product"  src='+ data.product.imageUrl[k] + '>');
			$("#product-main-photo"+k.toString()).append(imgPrincipal);

		}

		$("#product-main-photo1").hide();
		$("#product-main-photo2").hide();
		$("#product-main-photo3").hide();		




		var description = '<h2>'+data.product.name+'</h2>'+
		'<h3 name="productPrice" value='+data.product.price+' >$'+ data.product.price+'</h3>'+
		'<br>'+
		'<h4>Detalles:</h4>';
		data.product.attributes

		for(var i=0;i<data.product.attributes.length;i++){
			if(data.product.attributes[i].values.length>0){
				description+='<b> '+ data.product.attributes[i].name+':';
				for(var j=0;j<data.product.attributes[i].values.length;j++){
					description+='</b> '+ data.product.attributes[i].values[j];
				}
			}
			description+= '</p>';
		}	
		$("#product-description").append(description);

		

		for(var i=0;i<data.product.attributes.length;i++){


			
			if (data.product.attributes[i].name.indexOf("Talle") >= 0){

				var talle = '<label class="control-label">Talle:</label>'+

				'<select class="form-control form-box" data-validate="required,verifySelect" id="talleBox">';
				talle+='<option id="selectDefaultTalle">Talle</option>';
				for(var j=0;j<data.product.attributes[i].values.length;j++){
					talle+='<option name="prodtalle">'+data.product.attributes[i].values[j]+'</option>';
				}
				talle+='</select>';
			}
		}			
		$("#talle-color").append(talle);


		var color = '<label class="control-label">Color:</label>'+
		'<select class="form-control form-box" data-validate="required,verifySelect" id="colorBox">';
		for(var i=0;i<data.product.attributes.length;i++){
			if(data.product.attributes[i].name == "Color" ){
				color+='<option id="selectDefaultColor">Color</option>';
				for(var j=0;j<data.product.attributes[i].values.length;j++){
					color+='<option name="prodcolor" value="'+data.product.attributes[i].values[j]+'">'+data.product.attributes[i].values[j]+'</option>';
				}
				color+='</select>';	
			}
		}	
		$("#color-select").append(color);

		var url="products.html?";

		$("#breadcrumb").append('<a href="index.html">Inicio</a> ');
	
		var gender="";
		var age="";

		for(var i=0; i<data.product.attributes.length;i++){
			if(data.product.attributes[i].name=="Edad"){
				age+=data.product.attributes[i].values[0];
			}
			if(data.product.attributes[i].name=="Genero"){
				gender+=data.product.attributes[i].values[0];

			}
		}
		url+= "gender=" + gender;
		if(gender=="Masculino"){
			$("#breadcrumb").append(' / <a href="' + url + '">Hombres</a> ');	
		}
		if(gender=="Femenino"){
			$("#breadcrumb").append(' / <a href="' + url + '">Mujer</a> ');	
		}
		url += "&age=" + age;
		$("#breadcrumb").append(' / <a href="' + url + '">' + age + '</a> ');
		url+="&category=" + data.product.category.id;
		$("#breadcrumb").append(' / <a href="' + url + '">' + data.product.category.name + '</a> ');
		$("#breadcrumb").append('/'+data.product.name);


	});


});


	function changePhoto(id,i,cant){
		for(var j=0;j<cant;j++){
			if(j!=i){
				$('#product-main-photo'+j.toString()).hide();
			}
			else{
				$('#product-main-photo'+i.toString()).show();
			}
		}





	// $.ajax({
	// 	url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id="+id, dataType: "jsonp"
	// }).done(function(data) {


	// 	var img = $('<img  name="imgPrincipal" class="img-product" src='+ data.product.imageUrl[i] + '>');

	// 	$("#product-main-photo").append(img);
	// });


}








function comprar(){



	// if(getTalleValue()==undefined){
	// 	console.log("falta seleccionar talle");
	// 	// return ;
	// }
	// if(getColorValue()==undefined){
	// 	console.log("falta seleccionar color");
	// 	return ;
	// }
	// if($("#cantidad").val()==""){
	// 	console.log("error en cantidad");
	// 	return ;	
	// }


	var userdata = $.cookies.get("userdata");	
	var isLogIn = $.cookies.get("authToken");
	var cartExists=$.cookies.get("cart");


	if(isLogIn != null){
		console.log("esta logueado");
		var cart2;
		if(cartExists==null){
			$.cookies.set("cart",[]);
		}
		cart2=$.cookies.get("cart");
		console.log("paso el get");
		
		var product={
			id: GetURLParameter('id'),
			talle: getTalleValue(),
			color: getColorValue(),
			cantidad: $("#cantidad").val(),
			price: getProductPrice(),
			img: getImgPrincipal()
		}

		cart2.push(product);
		
		$.cookies.set("cart",cart2); //LCDTMAB
		
		window.location="cart.html?id="+product.id+'&color='+product.color+'&cantidad='+product.cantidad;
	}
	else{
		alert("Primero debe iniciar sesion o registrarse.");
		window.location="index.html";
	}
}


function getColorValue() {
	var options = document.getElementsByName("prodcolor");
	for (var i = 0; i <options.length; i++) {
		if (options[i].selected) {
			return options[i].text;
		}
	}
}

function getColorO() {
	var options = document.getElementsByName("prodcolor");
	for (var i = 0; i <options.length; i++) {
		if (options[i].selected) {
			return options[i];
		}
	}
}



function getTalleValue() {
	var options = document.getElementsByName("prodtalle");
	for (var i = 0; i < options.length; i++) {
		if (options[i].selected) {
			return options[i].text;
		}
	}
}

function getTalleO() {
	var options = document.getElementsByName("prodtalle");
	for (var i = 0; i < options.length; i++) {
		if (options[i].selected) {
			return options[i];
		}
	}
}

//revisar las dos de abajo
function getProductPrice(){
	var price = document.getElementsByName("productPrice");
	return price.value;
}

function getImgPrincipal(){
	var img = document.getElementsByName("imgPrincipal");
	return img;
}






var completed = [true, false, false];


function cantidadValidation() {
	var cantidad = $('#cantidad');
	var val = cantidad.val();
	var aux = false;
	if(val.length!=0){
		var regex = /^[0-9]{1,2}$/i;
		aux = regex.test(val);
	}
	effectES(cantidad, aux, "La cantidad aceptada es de 2 numeros.");
	if(aux){
		console.log("cant ok");
		completed[2] = true;
	} else {
		console.log("cant wrong");
		completed[2] = false;
	}
}


function cartValidation() {
	console.log(document.getElementById("talleBox"));
	if(document.getElementById("talleBox")!=null){
		talleValidation();
	}
	
	colorValidation();
	cantidadValidation();
	$("#add-to-cart").button("loading");
	var hasError = false;
	for (i = 0; i < completed.length && !hasError; i++) { 
		if(completed[i] == false){
			hasError = true;
		}
	}
	if(hasError){
		informError("Hay uno o más errores, por favor corrijalos.");
	}
	else {
		comprar();
	}
	$("#add-to-cart").button("reset");
}



function effectES(element, bool, msg) {
	if(bool==true){
		element.parent().removeClass("has-error").addClass("has-success");
		$('#alertMsg').removeClass("alert-danger").addClass("alert-info");
		document.getElementById("alertMsg").innerHTML = "Complete los campos para comprar el producto.";
	}
	else{
		element.parent().removeClass("has-success").addClass("has-error");
		$('#alertMsg').removeClass("alert-info").addClass("alert-danger");
		document.getElementById("alertMsg").innerHTML = msg;
	}
}



function talleValidation() {
	console.log("talleValidation");
	var talle = $('#talleBox');
	console.log(talle);
	if(talle.length==0){
		
		return;
	}
	var val = talle.val();
	var aux = false;
	if(val!=$("#selectDefaultTalle").val()){
		aux=true;
	}
	effectES(talle, aux, "Debe elegir una opción.");
	if(aux){
		completed[0] = true;
	} else {
		completed[0] = false;
	}
}


function colorValidation() {
	console.log("colorValidation");
	var color = $('#colorBox');
	var val = color.val();
	var aux = false;
	if(val!=$("#selectDefaultColor").val()){
		aux=true;
	}
	effectES(color, aux, "Debe elegir una opción.");
	if(aux){
		completed[1] = true;
	} else {
		completed[1] = false;
	}
}
