var completed = [false, false];
var tokenAuth = $.cookies.get("authToken");
var userdata = $.cookies.get("userdata");

$(document).ready(function() {
  hideAll();
  $('#changePassForm').hide();
  $('#cancelBtn').hide();
  $('#sendNewPassBtn').hide();
  showData();
  $('#profileDataBtn').click(showData);
  $('#ordersDataBtn').click(showOrders);
  $('#changePasswordBtn').click(showChangePass);
  $('#payCreatedOrdersBtn').click(showPendingOrders);
  $('#cancelBtn').click(hideChangePass);
  $('#sendNewPassBtn').click(sendNewPass);
  $('#payPendingOrdersBtn').click(payPendingOrder);
  $('#newPass').blur(newPasswordValidation);
  $('#newPassRep').blur(repeatPasswordValidation);
  $('#payCreatedOrdersBtn').hide();

  if(tokenAuth != null){
      //Carga los datos del usuario
      $('#usrdata').append('<li><em>Nombre de usuario: </em><b>'+userdata.username+'</b></li>');
      $('#usrdata').append('<li><em>Nombre: </em><b>'+userdata.firstName+'</b></li>');
      $('#usrdata').append('<li><em>Apellido: </em><b>'+userdata.lastName+'</b></li>');
      $('#usrdata').append('<li><em>DNI: </em><b>'+userdata.identityCard+'</b></li>');
      $('#usrdata').append('<li><em>Correo: </em><b>'+userdata.email+'</b></li>');
      $('#usrdata').append('<li><em>Fecha de Nacimiento: </em><b>'+userdata.birthDate+'</b></li>');
      $('#usrdata').append('<li><em>Fecha de Creación: </em><b>'+userdata.createdDate+'</b></li>');
      $('#usrdata').append('<li><em>Ultimo inicio de sesión: </em><b>'+userdata.lastLoginDate+'</b></li>');
      //Carga los pedidos
      var allOrdersUrl = "http://eiffel.itba.edu.ar/hci/service3/Order.groovy?method=GetAllOrders&username="+userdata.username+"&authentication_token="+tokenAuth;
      var auxorder;
      $.ajax({
        url: allOrdersUrl, dataType: "jsonp"
      }).done(function(data){
        if(data.orders.length != 0){
          for(i = 0 ; i<data.orders.length ; i++){
            var nro = data.orders[i].id;
            var orderUrl = "http://eiffel.itba.edu.ar/hci/service3/Order.groovy?method=GetOrderById&username="+userdata.username+"&authentication_token="+tokenAuth+"&id="+nro;
            $.ajax({
              url: orderUrl, dataType: "jsonp"
            }).done(function(data2){
              var totalPrice = 0;
              nro = data2.order.id;
              var estado = data2.order.status;
              var dir = (data2.order.address==null)? "Desconocido":data2.order.address.name;
              var fcr = (data2.order.processedDate==null)? "Desconocido":data2.order.processedDate;
              var fentr = (data2.order.deliveredDate==null)? "Desconocido":data2.order.deliveredDate;
              switch(estado) {
                case "1": estado = "Creado"; break;
                case "2": estado = "Confirmado"; break;
                case "3": estado = "En transito"; break;
                case "4": estado = "Entregado"; break;
                default: estado = "Desconocido";
              }
              for(j=0 ; j<data2.order.items.length ; j++){
                totalPrice += (data2.order.items[j].price*data2.order.items[j].quantity);
              }

              auxorder = '<tr><td>'+nro+'</td><td>'+estado+'</td><td>'+dir+'</td><td>'+fcr+'</td><td>'+fentr+'</td><td>'+totalPrice+'</td></tr>';
              $('#ordersTable').append(auxorder);
              if(estado == "Creado"){
                $('#pendingOdersTable').append(auxorder);
                $('#selectPendingOrder').append('<option value="'+nro+'">'+nro+'</option>');
                $('#payCreatedOrdersBtn').show();
              }
            });
          }
        }else{
          $('#ordersTable').hide();
          var message='<h3>Usted no tiene pedidos.</h3>'
          $("#message").append(message);
        }
      });
  }
});

function showData(){
  hideAll();
  $('#profileDataBtn').removeClass("btn-default").addClass("btn-primary");
  $('#dataPanel').show();
}

function showOrders(){
  hideAll();
  $('#ordersDataBtn').removeClass("btn-default").addClass("btn-primary");
  $('#ordersPanel').show();
}

function showChangePass(){
  $('#changePasswordBtn').hide();
  $('#changePassForm').show();
  $('#cancelBtn').show();
  $('#sendNewPassBtn').show();
}

function showPendingOrders(){
  hideAll();
  $('#pendingOrdersPanel').show();
}

function hideChangePass(){
  $('#changePasswordBtn').show();
  $('#changePassForm').hide();
  $('#cancelBtn').hide();
  $('#sendNewPassBtn').hide();
  $('#currentPass').parent().removeClass("has-error").removeClass("has-success");
  $('#newPass').parent().removeClass("has-error").removeClass("has-success");
  $('#newPassRep').parent().removeClass("has-error").removeClass("has-success");
  document.getElementById("changePassForm").reset();
}

function sendNewPass(){
  if(completed[0] && completed[1]){
    var userdata = $.cookies.get("userdata");
    var chgpassurl = "http://eiffel.itba.edu.ar/hci/service3/Account.groovy?method=ChangePassword&username="+ userdata.username +"&password="+ $('#currentPass').val() + "&new_password=" + $('#newPass').val();
    $.ajax({
      url: chgpassurl, dataType: "jsonp"
    }).done(function(data){
      if (!data.error) {
        $('#alertMsg').removeClass("alert-danger").addClass("alert-success");
        document.getElementById("alertMsg").innerHTML = "Cambio de contraseña exitoso.";
        $('#alertMsg').show();
        hideChangePass();
      } else {
        passErrorHandler(data.error);
      }
    });
  }else{

  }
}

function passErrorHandler(error){
  switch (String(error.code)) {
    case '1': informError("Error inesperado."); break;
    case '2':  informError("Error inesperado."); break;
    case '3': informError("Ingrese la contraseña actual."); break;
    case '5': informError("Ingrese la nueva contraseña."); break;
    case '101': informError("La contraseña no coincide con el usuario."); break;
    case '104': informError("Error inesperado.");       break;
    case '105': informError("Contraseña invalida."); break;
    case '999':  informError("Error inesperado."); break;
    default: informError("Error inesperado."); break;
  }
}

function informError(msg){
  $('#alertMsg').removeClass("alert-info").addClass("alert-danger");
  document.getElementById("alertMsg").innerHTML = msg;
  $('#alertMsg').show();
}

function newPasswordValidation(){
  var pass = $('#newPass');
  var val = pass.val();
  var aux = false;
  if(val.length!=0){
    var regex = /^[0-9A-Za-záéíóúÁÉÍÓÚñÑ@#$%&]{8,15}$/i;
    aux = regex.test(val);
  }
  effectES(pass, aux);
  if(aux){
    completed[0] = true;
  } else {
    completed[0] = false;
  }
}

function repeatPasswordValidation() {
  var pass = $('#newPass').val();
  var rep = $('#newPassRep');
  var val = rep.val();
  var aux = false;
  if(val.length!=0){
    aux = (val == pass);
  }
  effectES(rep, aux);
  if(aux){
    completed[1] = true;
  } else {
    completed[1] = false;
  }
}

function confirmOrder(id){
  console.log("entro?");
  window.location="pay.html?order="+id;
}


function effectES(element, bool) {
  if(bool==true){
    element.parent().removeClass("has-error").addClass("has-success");
  }
  else{
    element.parent().removeClass("has-success").addClass("has-error");
  }
}

function hideAll(){
  $('#profileDataBtn').removeClass("btn-primary").addClass("btn-default");
  $('#ordersDataBtn').removeClass("btn-primary").addClass("btn-default");
  $('#addressesDataBtn').removeClass("btn-primary").addClass("btn-default");
  $('#dataPanel').hide();
  $('#pendingOrdersPanel').hide();
  $('#ordersPanel').hide();
  $('#addressesPanel').hide();
  $('#addNewAddressPanel').hide();
  $('#alertMsg').hide();
}

function payPendingOrder(){
  if($('#selectPendingOrder').val() != $('#defaultSelectOption').val()){
    window.location="pay.html?order="+$('#selectPendingOrder').val();
  }
}