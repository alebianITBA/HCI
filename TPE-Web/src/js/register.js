/* Este js valida los datos del formulario de registro y envia la consulta a la API */
$(document).ready(function() {
  $('#inputUsername').blur(usernameValidation);
  $('#inputPassword').blur(passwordValidation);
  $('#passwordrep').blur(repeatPasswordValidation);
  $('#inputName').blur(nameValidation);
  $('#inputLastName').blur(lastNameValidation);
  $('#selectGender').blur(genderValidation);
  $('#inputDNI').blur(DNIValidation);
  $('#inputEmail').blur(emailValidation);
  $('#inputFechaNacimiento').blur(bdateValidation);
  $('#submitBtn').click(submitF);
});

var completed = [false, false, false, false, false, false, false, false, false];

function usernameValidation() {
  var username = $('#inputUsername');
  var val = username.val();
  var aux = false;
  if(val.length!=0){
    var regex = /^[0-9A-Za-z]{6,15}$/i;
    aux = regex.test(val);
  }
  effectES(username, aux, "El nombre de usuario debe contener entre 6 y 15 letras y/o numeros.");
  if(aux){
    completed[0] = true;
  } else {
    completed[0] = false;
  }
}

function passwordValidation() {
  var pass = $('#inputPassword');
  var val = pass.val();
  var aux = false;
  if(val.length!=0){
    var regex = /^[0-9A-Za-záéíóúÁÉÍÓÚñÑ@#$%&]{8,15}$/i;
    aux = regex.test(val);
  }
  effectES(pass, aux, "La contraseña debe contener entre 8 y 15 caracteres.");
  if(aux){
    completed[1] = true;
  } else {
    completed[1] = false;
  }
}

function repeatPasswordValidation() {
  var pass = $('#inputPassword').val();
  var rep = $('#passwordrep');
  var val = rep.val();
  var aux = false;
  if(val.length!=0){
    aux = (val == pass);
  }
  effectES(rep, aux, "La contraseña no coincide.");
  if(aux){
    completed[2] = true;
  } else {
    completed[2] = false;
  }
}

function nameValidation() {
  console.log("hola");
  var name = $('#inputName');
  var val = name.val();
  var aux = false;
  if(val.length!=0){
    var regex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,80}$/i;
    aux = regex.test(val);
  }
  effectES(name, aux, "El nombre debe contener hasta 80 letras y/o espacios.");
  if(aux){
    completed[3] = true;
  } else {
    completed[3] = false;
  }
}

function lastNameValidation() {
  var lname = $('#inputLastName');
  var val = lname.val();
  var aux = false;
  if(val.length!=0){
    var regex = /^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,80}$/i;
    aux = regex.test(val);
  }
  effectES(lname, aux, "El apellido debe contener hasta 80 letras y/o espacios.");
  if(aux){
    completed[4] = true;
  } else {
    completed[4] = false;
  }
}

function genderValidation() {
  console.log("gender");
  var gender = $('#selectGender');
  var val = gender.val();
  var aux = false;
  if(val!=$("#selectDefault").val()){
    aux=true;
  }
  effectES(gender, aux, "Debe elegir una opción.");
  if(aux){
    completed[5] = true;
  } else {
    completed[5] = false;
  }
}

function DNIValidation() {
  var dni = $('#inputDNI');
  var val = dni.val();
  var aux = false;
  if(val.length!=0){
    var regex = /^[0-9]{1,8}$/i;
    aux = regex.test(val);
  }
  effectES(dni, aux, "El DNI debe contener hasta 8 dígitos.");
  if(aux){
    completed[6] = true;
  } else {
    completed[6] = false;
  }
}

function emailValidation() {
  var mail = $('#inputEmail');
  var val = mail.val();
  var aux = false;
  if(val.length!=0){
    var regex = /^\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,3}$/;
    aux = regex.test(val);
  }
  effectES(mail, aux, "El E-mail insertado es invalido.");
  if(aux){
    completed[7] = true;
  } else {
    completed[7] = false;
  }
}

function bdateValidation() {
  var bdate = $('#inputFechaNacimiento');
  var val = bdate.val();
  var aux = false;
  if(val.length!=0){
    var regex = /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-](19|20)\d\d$/;
    aux = regex.test(val);
  }
  effectES(bdate, aux, "Introduzca la fecha en el formato DD/MM/AAAA.");
  if(aux){
    completed[8] = true;
  } else {
    completed[8] = false;
  }
}

function effectES(element, bool, msg) {
  if(bool==true){
    element.parent().removeClass("has-error").addClass("has-success");
    $('#alertMsg').removeClass("alert-danger").addClass("alert-info");
    document.getElementById("alertMsg").innerHTML = "Complete los campos restantes para poder completar el registro.";
  }
  else{
    element.parent().removeClass("has-success").addClass("has-error");
    $('#alertMsg').removeClass("alert-info").addClass("alert-danger");
    document.getElementById("alertMsg").innerHTML = msg;
  }
}

function submitF() {
  $("#submitBtn").button("loading");
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
    sendRequest();
  }
  $("#submitBtn").button("reset");
}

function sendRequest(){
  var account = {
    username: $("#inputUsername").val(),
    password: $("#inputPassword").val(),
    firstName: $("#inputName").val(),
    lastName: $("#inputLastName").val(),
    gender: $("#selectGender").val(),
    identityCard: $("#inputDNI").val(),
    email: $("#inputEmail").val(),
    birthDate: getAmericanDate($("#inputFechaNacimiento").val())
  };
  var Jurl = "http://eiffel.itba.edu.ar/hci/service3/Account.groovy?method=CreateAccount&account="+JSON.stringify(account);
  $.ajax({
    url: Jurl, dataType: "jsonp"
  }).done(function(data){
    if(data.error!=null){
      checkError(data.error);
    } else {
      loginSubmit(account.username,account.password);
    }
  });
}

function informError(msg){
  $('#alertMsg').removeClass("alert-info").addClass("alert-danger");
  document.getElementById("alertMsg").innerHTML = msg;
}

function getGenderValue() {
  var options = document.getElementsByName("genero");
  if (options[0].checked) {
    return options[0].value;
    return options[1].value;
  }
}

function checkError(error){
 switch (String(error.code)) {
  case '1': informError("Error inesperado."); break;
  case '6': informError("Error inesperado."); break;
  case '100': informError("Error inesperado."); break;
  case '103': informError("Error inesperado."); break;
  case '104': informError("El nombre de usuario es inválido."); break;
  case '105': informError("La contraseña de usuario es inválida."); break;
  case '106': informError("El primer nombre es inválido."); break;
  case '107': informError("El apellido es inválido."); break;
  case '108': informError("El género es inválido."); break;
  case '109': informError("El primer nombre es inválido."); break;
  case '110': informError("El Email es inválido."); break;
  case '111': informError("La fecha de nacimiento es inválida."); break;
  case '200': informError("El nombre de usuario ya se encuentra en uso."); break;
  case '201': informError("El DNI ya se entcuentra en uso."); break;
  case '999': informError("Error inesperado."); break;
  default: informError("Error inesperado."); break;
  }
}

function getAmericanDate(latamDate) {
  var from = latamDate.toString();
  var temp = from.split("/");
  return temp[2] + "-" + temp[1] + "-" + temp[0];
}