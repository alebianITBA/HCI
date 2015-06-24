$(document).ready(function(){
	$('#header').load('header.html');
	$('#footer').load('footer.html');

	$.ajax({
		url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllCategories", dataType: "jsonp"
	}).done(function(data) {

		
		var categories ='<ul class="nav navbar-nav main-menu">';
		var categories1 ='<li class="dropdown hover-menu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Hombres <span class="caret"></span></a>'+'<ul class="dropdown-menu" role="menu">';
		var categories2 ='<li class="dropdown hover-menu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Mujeres <span class="caret"></span></a>'+'<ul class="dropdown-menu" role="menu">';
		var categories3 ='<li class="dropdown hover-menu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Niños <span class="caret"></span></a>'+'<ul class="dropdown-menu" role="menu">';
		var categories4 ='<li class="dropdown hover-menu"><a href="#" class="dropdown-toggle" data-toggle="dropdown">Bebes <span class="caret"></span></a>'+'<ul class="dropdown-menu" role="menu">';
		for(var i=0; i<data.categories.length; i++){
			for(var j=0; j<data.categories[i].attributes[0].values.length; j++){
				if(data.categories[i].attributes[0].values[j]=="Adulto"){
					categories1+='<li><a href="products.html?category='+data.categories[i].id+'&gender=Masculino&age='+data.categories[i].attributes[0].values[j]+'">'+data.categories[i].name+'</a></li>';
					categories2+='<li><a href="products.html?category='+data.categories[i].id+'&gender=Femenino&age='+data.categories[i].attributes[0].values[j]+'">'+data.categories[i].name+'</a></li>';
				}
				if(data.categories[i].attributes[0].values[j]=="Infantil"){
					categories3+='<li><a href="products.html?category='+data.categories[i].id+'&age='+data.categories[i].attributes[0].values[j]+'">'+data.categories[i].name+'</a></li>';
				}
				if(data.categories[i].attributes[0].values[j]=="Bebe"){
					categories4+='<li><a href="products.html?category='+data.categories[i].id+'&age='+data.categories[i].attributes[0].values[j]+'">'+data.categories[i].name+'</a></li>';
				}
			}	
		}
		categories1+='</ul>';
		categories2+='</ul>';
		categories3+='</ul>';
		categories4+='</ul>';
		categories+=categories1;
		categories+=categories2;
		categories+=categories3;
		categories+=categories4+'</ul>';

		$('#gender-categories').append(categories);

		if($.cookies.get("authToken") == null){
			$('#loginDropdown').append(loginDrop);
			$('#libtn').click(tryLogin);
			$('#alertMsg').hide();
		}else{
			$('#userOptions').append(userOpt());
			$('#lobtn').click(logOut);


			var cart=$.cookies.get("cart");

			if(cart!=null){
				var prodCart;
				for(var k=0 ; k<cart.length ; k++){
					$.ajax({
						url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductById&id="+cart[k].id, dataType: "jsonp"
					}).done(function(data) {
					// console.log(data.product.imageUrl[0]);
					prodCart= '<li><a href="cart.html"><img class="cart-image-header" src='+data.product.imageUrl[0]+'></a></li><li class="divider"></li>';
					$("#cartDropdown").append(prodCart);
				});
				}
				// $("#cantitems").append(cart.length);
			}
			
		}
			// $.ajax({
			// 	url: "http://eiffel.itba.edu.ar/hci/service3/Order.groovy?method=GetOrderById&username=janedoe&authentication_token=a8c0d2a9d332574951a8e4a0af7d516f&id=1056",
			// 	dataType: "jsonp"
			// }).done(function(data){
			// 	var items = data.order.items;

			// 	var cart="";

			// 	for( var i=0 ; i<items.length ; i++){
			// 		cart+= '<li><img class="cart-image-header" src='+items[i].product.imageUrl+'></li><li class="divider"></li>';
			// 	}
			// 	$("#cantitems").append(items.length);
			// 	$("#cartDropdown").append(cart);
			// });
});
});

function tryLogin(){
	$('#libtn').button("loading");
	loginSubmit($("#usernameLog").val(),$("#passwordLog").val());
}

function loginSubmit(us,pas){
	var signInUrl = "http://eiffel.itba.edu.ar/hci/service3/Account.groovy?method=SignIn&username="+us+"&password="+pas;
	$.ajax({
		url: signInUrl, dataType: "jsonp"
	}).done(function(data){
		if (!data.error) {
			$.cookies.set("authToken", data.authenticationToken);
			$.cookies.set("userdata", JSON.stringify(data.account));
			window.location.assign("index.html");
		} else {
			var msg;
			switch(String(data.error.code)) {
				case "1": msg = "Error desconocido."; break;
				case "2": msg = "Nombre de usuario requerido."; break;
				case "3": msg = "Contraseña requerida."; break;
				case "100": msg = "Error desconocido."; break;
				case "101": msg = "Usuario invalido."; break;
				case "104": msg = "Usuario invalido."; break;
				case "105": msg = "Contraseña invalida."; break;
				case "999": msg = "Error desconocido."; break;
				default: msg = "Error desconocido.";
			}
			informError(msg);	
		}	
	});
	$('#libtn').button("reset");
}

function logOut(){
	var authToken = $.cookies.get("authToken");
	var userdata = $.cookies.get("userdata");
	var logOutUrl = "http://eiffel.itba.edu.ar/hci/service3/Account.groovy?method=SignOut&username="+userdata.username+"&authentication_token="+authToken;
	$.ajax({
		url: logOutUrl, dataType: "jsonp"
	}).done(function(data){
		if (!data.error) {
			$.cookies.del("authToken"); $.cookies.del("userdata"); $.cookies.del("cartOrderId");
			window.location.assign("index.html");
		}

	});
}

function informError(msg){
	$('#alertMsg').show();
	document.getElementById("alertMsg").innerHTML = msg;
}


function userOpt(){
var cart="";
var num=$.cookies.get("cart");
if(num!=null){
 cart='('+num.length.toString()+')';
}

var name=" "+$.cookies.get("userdata").firstName;

var userOpt =
'<ul class="nav navbar-nav btn-header" id="userOptions">'+
'<li class="cart-btn dropdown">'+
'<a href="#" class="dropdown-toggle top-btn" data-toggle="dropdown">'+
'<p class="cartnumber" id="cantitems"><span class="glyphicon glyphicon-shopping-cart top-btn"></span>Carrito'+cart+
'<span class= "caret top-btn"></span></p>'+
'</a>'+
'<ul class="dropdown-menu cartDropdown" role="menu" id="cartDropdown">'+
'<li><a href="cart.html">Ver Carrito</a></li>'+
'</ul>'+
'</li>'+

'<li class="dropdown">'+
'<a href="#" class="dropdown-toggle top-btn" data-toggle="dropdown">'+
'<p ><span class="glyphicon glyphicon-user top-btn"></span>'+name+'<span class= "caret top-btn"></span></p></a>'+
'<ul class="dropdown-menu" role="menu" id="profileDropdown">'+
'<li><a href="profile.html">Perfil</a></li>'+
'<li id="lobtn"><a href="#">Cerrar sesión</a></li>'+
'</ul>'+
'</li>'+
'</ul>';

return userOpt;
}
var loginDrop = '<li class="hover-menu">'+
'<a href="register.html">Soy nuevo</a>'+
'</li>'+
'<li class="dropdown hover-menu"><a href="#" class="dropdown-toggle"'+
'data-toggle="dropdown">Ingresar <span class="caret"></span></a>'+
'<ul class="dropdown-menu" role="menu">'+
'<div class="tab-pane active in" id="login">'+
'<form class="form-horizontal" id="loginForm">'+
'<div class="containerSecond">	'+
'<h2>Ingresar</h2>'+
'<div class="alert alert-danger" role="alert" id="alertMsg"></div>'+
'<div class="control-group">'+
'<!-- Username -->'+
'<label class="control-label" for="username">Usuario</label>'+
'<div class="controls">'+
'<input type="text" id="usernameLog" name="username"'+
'placeholder="" class="input-xlarge"/>'+
'</div>'+
'</div>'+
'<div class="control-group">'+
'<!-- Password-->'+
'<label class="control-label" for="passwordLog">Contraseña</label>'+
'<div class="controls">'+
'<input type="password" id="passwordLog" name="password"'+
'placeholder="" class="input-xlarge"/>'+
'</div>'+
'</div>'+
'<br/>'+
'</div>'+
'</form>'+
'<!-- Button -->'+
'<div class="controls">'+
'<button id="libtn" type="submit" class="btn btn-primary" data-loading-text="Verificando...">Iniciar sesión'+
'</button>'+
'</div>'+
'</div>'+
'</ul>'+
'</li>';

