var authToken = $.cookies.get("authToken");
var userdata = $.cookies.get("userdata");
var addComp = [false,false,false,true,true,true,false,false,false];
var ccComp = [false,false,false,false,false];

$(document).ready(function() {
	hideAll();
	showConfirmPurchasePanel();
	$('#addAdressBtn').click(showNewAddressPanel);
	$('#addCreditCardBtn').click(showNewCreditCardPanel);
	$('#cancelAddressBtn').click(showConfirmPurchasePanel);
	$('#cancelCreditCardBtn').click(showConfirmPurchasePanel);
	$('#confirmPurchaseBtn').click(confirmarCompra);
	loadOrderData();
	//Address blur
	$('#af1').blur(af1Validation);
	$('#af2').blur(af2Validation);
	$('#af3').blur(af3Validation);
	$('#af4').blur(af4Validation);
	$('#af6').blur(af6Validation);
	$('#af7').blur(af7Validation);
	$('#af8').blur(af8Validation);
	$('#af9').blur(af9Validation);
	//Credit card blur
	$('#cardTypeSelect').blur(cardTypeValidation);
	$('#cardNumber').blur(cardNumberValidation);
	$('#cardCode').blur(cardCodeValidation);
	$('#cardMonth').blur(cardMonthValidation);
	$('#cardYear').blur(cardYearValidation);
	$('#addNewAddressBtn').click(addNewAddress);
	$('#addNewCreditCardBtn').click(addNewCreditCard);
});

function hideAll(){
	$('#confirmPurchasePanel').hide();
	$('#addNewAddressPanel').hide();
	$('#addNewCreditCardPanel').hide();
	$('#OrderPaidPanel').hide();
	
}

function showNewAddressPanel(){
	hideAll();
	document.getElementById("newAddressForm").reset();
	$('#newAddressAlert').removeClass("alert-danger").addClass("alert-info");
	document.getElementById("newAddressAlert").innerHTML = "Complete los campos restantes para poder agregar una direccion nueva.";
	loadAllStates();
	$('#addNewAddressPanel').show();
	$('#addNewAddressBtn').removeAttr('disabled');
}

function showNewCreditCardPanel(){
	hideAll();
	document.getElementById("newCreditCardForm").reset();
	$('#newCardAlert').removeClass("alert-danger").addClass("alert-info");
	document.getElementById("newCardAlert").innerHTML = "Complete los campos restantes para poder agregar una tarjeta nueva.";

	$('#addNewCreditCardPanel').show();
	$('#addNewCreditCardBtn').removeAttr('disabled');
}

function showConfirmPurchasePanel(){
	hideAll();
	$('#af1').parent().removeClass("has-error").removeClass("has-success");
	$('#af2').parent().removeClass("has-error").removeClass("has-success");
	$('#af3').parent().removeClass("has-error").removeClass("has-success");
	$('#af4').parent().removeClass("has-error").removeClass("has-success");
	$('#af6').parent().removeClass("has-error").removeClass("has-success");
	$('#af7').parent().removeClass("has-error").removeClass("has-success");
	$('#af8').parent().removeClass("has-error").removeClass("has-success");
	$('#af9').parent().removeClass("has-error").removeClass("has-success");
	loadCreditCards();
	loadAddresses();
	$('#confirmPurchasePanel').show();
}

function loadOrderData(){
	var orderId = GetURLParameter("order");
	$('#orderId').append(orderId);
	var orderUrl = "http://eiffel.itba.edu.ar/hci/service3/Order.groovy?method=GetOrderById&username="+userdata.username+"&authentication_token="+authToken+"&id="+orderId;
    $.ajax({
      url: orderUrl, dataType: "jsonp"
    }).done(function(data2){
      var totalPrice = 0;
      for(j=0 ; j<data2.order.items.length ; j++){
        totalPrice += (data2.order.items[j].price*data2.order.items[j].quantity);
      }
      $('#totalpricefinal').append(totalPrice);
    });
}

function loadAddresses(){
	document.getElementById("selectAddress").options.length = 1;
	var allAddressesUrl = "http://eiffel.itba.edu.ar/hci/service3/Account.groovy?method=GetAllAddresses&username="+userdata.username+"&authentication_token="+authToken;
	$.ajax({
		url: allAddressesUrl, dataType: "jsonp"
	}).done(function(data){
		if (!data.error) {
			var addresses = data.addresses;
			for(var i = 0 ; i < addresses.length ; i++){
				var id = addresses[i].id;
				$('#selectAddress').append('<option name="addressOption" value="'+id+'">'+addresses[i].name+'</option>');
			}
		}
	});
}

function loadCreditCards(){
	document.getElementById("selectCreditCard").options.length = 1;
	var allCreditCardsUrl = "http://eiffel.itba.edu.ar/hci/service3/Account.groovy?method=GetAllCreditCards&username="+userdata.username+"&authentication_token="+authToken;
	$.ajax({
		url: allCreditCardsUrl, dataType: "jsonp"
	}).done(function(data){
		if (!data.error) {
			var cards = data.creditCards;
			for(var i = 0 ; i < cards.length ; i++){
				var end = cards[i].number;
				var id = cards[i].id;
				end = "Terminada en: **** "+end.substring(end.length-5,end.length-1);
				$('#selectCreditCard').append('<option name="cardOption" value="'+id+'">'+end+'</option>');
			}
		}
	});
}

function loadAllStates(){
	var allStatesUrl = "http://eiffel.itba.edu.ar/hci/service3/Common.groovy?method=GetAllStates";
	$.ajax({
		url: allStatesUrl, dataType: "jsonp"
	}).done(function(data){
		if (!data.error) {
			var states = data.states;
			for(var i = 0 ; i < states.length ; i++){
				var state = states[i].name;
				var id = states[i].stateId;
				$('#af7').append('<option value="'+id+'">'+state+'</option>');
			}
		}
	});
}

function af1Validation() {
	var name = $('#af1');
	var val = name.val();
	var aux = false;
	if(val.length!=0){
		var regex = /^[0-9A-Za-záéíóúÁÉÍÓÚñÑ ]{1,80}$/i;
		aux = regex.test(val);
	}
	effectAdES(name, aux, "El nombre debe contener hasta 80 caracteres.");
	if(aux){
		addComp[0] = true;
	} else {
		addComp[0] = false;
	}
}

function af2Validation() {
	var name = $('#af2');
	var val = name.val();
	var aux = false;
	if(val.length!=0){
		var regex = /^[0-9A-Za-záéíóúÁÉÍÓÚñÑ ]{1,80}$/i;
		aux = regex.test(val);
	}
	effectAdES(name, aux, "La calle debe contener hasta 80 caracteres.");
	if(aux){
		addComp[1] = true;
	} else {
		addComp[1] = false;
	}
}

function af3Validation() {
	var name = $('#af3');
	var val = name.val();
	var aux = false;
	if(val.length!=0){
		var regex = /^[0-9A-Za-z]{1,6}$/i;
		aux = regex.test(val);
	}
	effectAdES(name, aux, "El numero debe contener hasta 6 caracteres.");
	if(aux){
		addComp[2] = true;
	} else {
		addComp[2] = false;
	}
}

function af4Validation() {
	var name = $('#af4');
	var val = name.val();
	var aux = true;
	if(val.length!=0 && val!=""){
		var regex = /^[0-9A-Za-z]{1,3}$/i;
		aux = regex.test(val);
	}
	effectAdES(name, aux, "El piso debe contener hasta 3 caracteres.");
	if(aux){
		addComp[3] = true;
	} else {
		addComp[3] = false;
	}
}

function af6Validation() {
	var name = $('#af6');
	var val = name.val();
	var aux = true;
	if(val.length!=0 && val!=""){
		var regex = /^[0-9A-Za-záéíóúÁÉÍÓÚñÑ ]{1,80}$/i;
		aux = regex.test(val);
	}
	effectAdES(name, aux, "El nombre debe contener hasta 80 caracteres.");
	if(aux){
		addComp[5] = true;
	} else {
		addComp[5] = false;
	}
}

function af7Validation() {
	var prov = $('#af7');
	var val = prov.val();
	var aux = false;
	if(val!=$("#selectDefault1").val()){
		aux=true;
	}
	effectAdES(prov, aux, "Debe elegir una provincia.");
	if(aux){
		addComp[6] = true;
	} else {
		addComp[6] = false;
	}
}

function af8Validation() {
	var name = $('#af8');
	var val = name.val();
	var aux = false;
	if(val.length!=0){
		var regex = /^[0-9A-Za-z]{1,10}$/i;
		aux = regex.test(val);
	}
	effectAdES(name, aux, "El codigo postal debe contener hasta 10 caracteres.");
	if(aux){
		addComp[7] = true;
	} else {
		addComp[7] = false;
	}
}

function af9Validation() {
	var name = $('#af9');
	var val = name.val();
	var aux = false;
	if(val.length!=0){
		var regex = /^[0-9]{1,25}$/i;
		aux = regex.test(val);
	}
	effectAdES(name, aux, "El numero de telefono debe contener hasta 25 numeros sin guiones ni espacios.");
	if(aux){
		addComp[8] = true;
	} else {
		addComp[8] = false;
	}
}

function cardTypeValidation(){
	var type = $('#cardTypeSelect');
	var val = type.val();
	var aux = false;
	if(val!=$("#selectDefault2").val()){
		aux=true;
	}
	effectCCES(type, aux, "Debe elegir un tipo de tarjeta.");
	if(aux){
		ccComp[0] = true;
	} else {
		ccComp[0] = false;
	}
	cardNumberValidation();
	cardCodeValidation();
}

function cardNumberValidation(){
	var type = $('#cardTypeSelect').val();
	var number = $('#cardNumber');
	var val = number.val();
	var regex;
	aux = false;
	if(type!=$("#selectDefault2").val()){
		switch (type) {
			case 'A': 
			regex = /^3[47]{1}[0-9]{13}$/i;
			aux = regex.test(val); 
			break;
			case 'M':
			regex = /^5[123][0-9]{14}$/i;
			aux = regex.test(val); 
			break;
			case 'D':
			regex = /^36[0-9]{14}$/i;
			aux = regex.test(val); 
			break;
			case 'V':
			regex = /^4[0-9]{13,16}$/i;
			aux = regex.test(val); 
			break;
			default: break;
		}
	}
	effectCCES(number, aux, "Numero de tarjeta incorrecto.");
	if(aux){
		ccComp[1] = true;
	} else {
		ccComp[1] = false;
	}
}

function cardCodeValidation(){
	var type = $('#cardTypeSelect').val();
	var code = $('#cardCode');
	var val = code.val();
	var regex;
	aux = false;
	if(type!=$("#selectDefault2").val()){
		switch (type) {
			case 'A': 
			regex = /^[0-9]{4}$/i;
			aux = regex.test(val); 
			break;
			default: 
			regex = /^[0-9]{3}$/i;
			aux = regex.test(val);
			break;
		}
	}
	effectCCES(code, aux, "Codigo de tarjeta incorrecto.");
	if(aux){
		ccComp[2] = true;
	} else {
		ccComp[2] = false;
	}
}

function cardMonthValidation(){
	var month = $('#cardMonth');
	var val = month.val();
	var aux = false;
	if(val!=$("#selectDefault3").val()){
		aux=true;
	}
	effectCCES(month, aux, "Debe elegir un mes de caducidad.");
	if(aux){
		ccComp[3] = true;
	} else {
		ccComp[3] = false;
	}
}

function cardYearValidation(){
	var year = $('#cardYear');
	var val = year.val();
	var aux = false;
	if(val!=$("#selectDefault4").val()){
		aux=true;
	}
	effectCCES(year, aux, "Debe elegir un año de caducidad.");
	if(aux){
		ccComp[4] = true;
	} else {
		ccComp[4] = false;
	}
}

function effectAdES(element, bool, msg) {
	if(bool==true){
		element.parent().removeClass("has-error").addClass("has-success");
		$('#newAddressAlert').removeClass("alert-danger").addClass("alert-info");
		document.getElementById("newAddressAlert").innerHTML = "Complete los campos restantes para poder agregar una direccion nueva.";
	}
	else{
		element.parent().removeClass("has-success").addClass("has-error");
		$('#newAddressAlert').removeClass("alert-info").addClass("alert-danger");
		document.getElementById("newAddressAlert").innerHTML = msg;
	}
}

function effectCCES(element, bool, msg) {
	if(bool==true){
		element.parent().removeClass("has-error").addClass("has-success");
		$('#newCardAlert').removeClass("alert-danger").addClass("alert-info");
		document.getElementById("newCardAlert").innerHTML = "Complete los campos restantes para poder agregar una tarjeta nueva.";
	}
	else{
		element.parent().removeClass("has-success").addClass("has-error");
		$('#newCardAlert').removeClass("alert-info").addClass("alert-danger");
		document.getElementById("newCardAlert").innerHTML = msg;
	}
}

function informAddressError(msg){
	$('#newAddressAlert').removeClass("alert-info").addClass("alert-danger");
	document.getElementById("newAddressAlert").innerHTML = msg;
}

function informCreditCardError(msg){
	$('#newCardAlert').removeClass("alert-info").addClass("alert-danger");
	document.getElementById("newCardAlert").innerHTML = msg;
}

function addNewAddress(){
	$("#addNewAddressBtn").button("loading");
	var hasError = false;
	for (i = 0; i < addComp.length && !hasError; i++) { 
		if(addComp[i] == false){
			hasError = true;
		}
	}
	if(hasError){
		informAddressError("Hay uno o más errores, por favor corrijalos.");
	} else {
		var address = {};
		address["name"] = $("#af1").val();
		address["street"] = $("#af2").val();
		address["number"] = $("#af3").val();
		if($("#af4").val()!=null){
			address["floor"] = $("#af4").val();
		}
		if($("#af6").val()!=null){
			address["city"] = $("#af6").val();
		}
		address["province"] = $("#af7").val();
		address["zipCode"] = $("#af8").val();
		address["phoneNumber"] = $("#af9").val();
		var Jurl = "http://eiffel.itba.edu.ar/hci/service3/Account.groovy?method=CreateAddress&username="+userdata.username+"&authentication_token="+authToken+"&address="+JSON.stringify(address);
		$.ajax({
			url: Jurl, dataType: "jsonp"
		}).done(function(data){
			if(data.error!=null){
				checkAdError(data.error);
			} else {
				showConfirmPurchasePanel();
			}
		});
	}
	$("#addNewAddressBtn").button("reset");
}

function addNewCreditCard(){
	$("#addNewCreditCardBtn").button("loading");
	var hasError = false;
	for (i = 0; i < ccComp.length && !hasError; i++) { 
		if(ccComp[i] == false){
			hasError = true;
		}
	}
	if(hasError){
		informCreditCardError("Hay uno o más errores, por favor corrijalos.");
	} else {
		var card = {};
		card["number"] = $("#cardNumber").val();
		card["expirationDate"] = $("#cardMonth").val()+$("#cardYear").val();
		card["securityCode"] = $("#cardCode").val();
		var Jurl = "http://eiffel.itba.edu.ar/hci/service3/Account.groovy?method=CreateCreditCard&username="+userdata.username+"&authentication_token="+authToken+"&credit_card="+JSON.stringify(card);
		$.ajax({
			url: Jurl, dataType: "jsonp"
		}).done(function(data){
			if(data.error!=null){
				checkCCError(data.error);
			} else {
				showConfirmPurchasePanel();
			}
		});
	}
	$("#addNewCreditCardBtn").button("reset");
}

function checkAdError(error){
	switch (String(error.code)) {
		case '1': informAddressError("Error inesperado."); break;
		case '2': informAddressError("Debe estar logueado."); break;
		case '4': informAddressError("Debe estar logueado."); break;
		case '9': informAddressError("Ingrese la direccion de envio."); break;
		case '100':informAddressError("Error inesperado."); break;
		case '101': informAddressError("Usuario invalido."); break;
		case '102': informAddressError("Debe estar logueado."); break;
		case '104': informAddressError("El usuario es inválido."); break;
		case '114': informAddressError("Direccion de envio invalida."); break;
		case '115': informAddressError("El nombre invalido."); break;
		case '116': informAddressError("La calle invalida."); break;
		case '117': informAddressError("El numero de calle es invalido."); break;
		case '118': informAddressError("El piso es invalido."); break;
		case '119': informAddressError("Error inesperado."); break;
		case '120': informAddressError("Provincia invalida."); break;
		case '121': informAddressError("Ciudad invalida."); break;
		case '122': informAddressError("El codigo postal es invalido."); break;
		case '123': informAddressError("El telefono es invalido."); break;
		case '202': informAddressError("La direccion ya existe."); break;
		case '999': informAddressError("Error inesperado."); break;
		default: informAddressError("Error inesperado."); break;
	}
}

function checkCCError(error){
	switch (String(error.code)) {
		case '1': informCreditCardError("Error inesperado."); break;
		case '2': informCreditCardError("Debe estar logueado."); break;
		case '4': informCreditCardError("Debe estar logueado."); break;
		case '13': informCreditCardError("Ingrese los datos de la tarjeta."); break;
		case '100':informCreditCardError("Error inesperado."); break;
		case '101': informCreditCardError("Usuario invalido."); break;
		case '102': informCreditCardError("Debe estar logueado."); break;
		case '104': informCreditCardError("El usuario es inválido."); break;
		case '134': informCreditCardError("Los datos de la tarjeta son invalidos."); break;
		case '135': informCreditCardError("El numero de la tarjeta es invalido."); break;
		case '136': informCreditCardError("La fecha de caducidad es invalida."); break;
		case '137': informCreditCardError("El codigo de seguridad es invalido."); break;
		case '203': informCreditCardError("La tarjeta ya existe."); break;
		case '999': informCreditCardError("Error inesperado."); break;
		default: informCreditCardError("Error inesperado."); break;
	}
}

function confirmarCompra(){
	var orderId = GetURLParameter("order");
	var addressId = getSelectedAddress();
	var cardId = getSelectedCard();
	if(addressId==undefined || cardId==undefined){
		console.log("faltan seleccionar datos");
		return;
	}
	$.ajax({
		url: 'http://eiffel.itba.edu.ar/hci/service3/Order.groovy?method=ConfirmOrder&username='+userdata.username+'&authentication_token='+authToken+'&order={"id":'+orderId+',"address":{"id":'+addressId+'},"creditCard":{"id":'+cardId+'}}', dataType:"jsonp", error: function(){console.log("error");}
	}).done(function(data){
		console.log(data.error);
		hideAll();
		$('#OrderPaidPanel').show();
	});
}

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

function getSelectedAddress() {
	var options = document.getElementsByName("addressOption");
	for (var i = 0; i < options.length; i++) {
		if (options[i].selected) {
			return options[i].value;
		}
	}
}

function getSelectedCard() {
	var options = document.getElementsByName("cardOption");
	for (var i = 0; i < options.length; i++) {
		if (options[i].selected) {
			return options[i].value;
		}
	}
}