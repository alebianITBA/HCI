

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

function getCategoryName(param){
	if(param==1){
		return "Calzado";
	}
	if(param==2){
		return "Indumentaria";
	}
	if(param==3){
		return "Accesorios";
	}
}
var page_num;
$(document).ready(function() {
	

	var gender = GetURLParameter('gender');
	var age = GetURLParameter('age');
	var category = GetURLParameter('category');
	var page = GetURLParameter('page');
	var prodid = GetURLParameter('id');
	var exists=0;
	var url = "products.html?";
	var search = GetURLParameter('search');
	var filters = unescape(decodeURI(GetURLParameter('filters')));
	var sort_key = GetURLParameter('sort_key');
	var sort_order=  GetURLParameter('sort_order');
	var page_num_aux=GetURLParameter('page_num');
	var subcategory = GetURLParameter('subcategory');

	if(typeof page_num_aux !== "undefined"){
		page_num=page_num_aux;
	}
	if(typeof page_num === "undefined"){
		page_num=1;
	}

	
	$("#breadcrumb").append('<a href="index.html">Inicio</a> ');


	if (typeof gender !== "undefined") {
		url += "gender=" + gender;
		if(gender=="Masculino"){
			$("#breadcrumb").append(' / <a href="' + url + '">Hombres</a> ');	
		}else{
			$("#breadcrumb").append(' / <a href="' + url + '">Mujer</a> ');	
		}
		
	}
	if (typeof age !== "undefined") {
		console.log(age);
		url += "&age=" + age;
		$("#breadcrumb").append(' / <a href="' + url + '">' + age + '</a> ');
	}
	if (typeof filters !== "undefined") {

		filters=filters.split('-');
		

		for (var i = 0; i <filters.length; i++) {
			if(filters[i]===""){
				filters.splice(i,1);
			}
			
		}
		for (var i = 0; i <filters.length; i++) {
			if(filters[i]===""){
				filters.splice(i,1);
			}
			
		}

		for (var i = 0; i <filters.length; i++) {
			for(var j = 0; j <filters[i].length; j++){
				if(filters[i][j]==='"' && filters[i][j+1]==='"'){
					filters.splice(i,1);
					
				}
			}
			
		}

		
		//url += "gender=" + ;
		if(filters[0][18]=="M"){
			url += "gender=Masculino";
			$("#breadcrumb").append(' / <a href="' + url + '">Hombres</a> ');	
		}else if(filters[0][18]=="F"){
			url += "gender=Femanino";
			$("#breadcrumb").append(' / <a href="' + url + '">Mujer</a> ');	
		}
		else if(filters[0][18]=="B"){
			url += "age=Bebe";
			$("#breadcrumb").append(' / <a href="' + url + '">Bebe</a> ');	
		}
		else if(filters[0][18]=="I"){
			url += "age=Infantil";
			$("#breadcrumb").append(' / <a href="' + url + '">Infantil</a> ');	
		}
		if(filters.length>1){
			if(filters[1][18]=="A"){
				url+="age=Adulto";
				$("#breadcrumb").append(' / <a href="' + url + '">Adulto</a> ');
			}	
		}
		

		
	}
	if (typeof category !== "undefined") {
		url += "&category=" + category;
		var categoryName= getCategoryName(category);
		$("#breadcrumb").append(' / <a href="' + url + '">' + categoryName + '</a> ');
		
	}
	if (typeof search !== "undefined") {
		url += "&search=" + search;
		$("#breadcrumb").append(' / <a href="' + url + '">' + search + '</a> ');
	}

	if(typeof gender !== "undefined"){
		url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllProducts&filters=[{"id":1,"value":"'+gender+'"}]&page_size=9'+'&page='+ page_num;
	}
	if(typeof age !== "undefined"){
		url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllProducts&filters=[{"id":2,"value":"'+age+'"}]&page_size=9'+'&page='+ page_num;
	}
	if(typeof gender !== "undefined" && typeof age !== "undefined" && typeof category !== "undefined"){
		url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsByCategoryId&filters=[{"id":1,"value":"'+gender+'"},{"id":2,"value":"'+age+'"}]&id='+category+'&page_size=9'+'&page='+ page_num;
	}
	

	if(typeof search !== "undefined"){
		url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsByName&name='+search+'&page_size=9'+'&page='+ page_num;
	}
	if(typeof subcategory !== "undefined"){
		url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsBySubcategoryId&id='+subcategory+'&page_size=9'+'&page='+ page_num;
	}
	if(filters[0]!== "undefined"){
		url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllProducts&filters='+filters+'&page_size=9'+'&page='+ page_num;
	}
	if(typeof search !== "undefined" && filters[0]!== "undefined"){
		url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsByName&name='+search+'&filters='+filters+'&page_size=9'+'&page='+ page_num;

	}
	if(typeof category !== "undefined" &&  filters[0] !== "undefined"){
		url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsByCategoryId&id='+category+'&filters='+filters+'&page_size=9'+'&page='+ page_num;
	}
	if(typeof subcategory !== "undefined" &&  filters[0]!== "undefined"){
		url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsBySubcategoryId&id='+subcategory+'&filters='+filters+'&page_size=9'+'&page='+ page_num;
	}
	if(typeof sort_key !== "undefined"){
		if(typeof search !== "undefined"){
			url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsByName&name='+search+'&page_size=9'+'&page='+ page_num+'&sort_key='+sort_key;
		}
		if(typeof subcategory !== "undefined"){
			url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsBySubcategoryId&id='+subcategory+'&page_size=9'+'&page='+ page_num+'&sort_key='+sort_key;
		}
		if(filters[0]!== "undefined"){
			url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllProducts&filters='+filters+'&page_size=9'+'&page='+ page_num+'&sort_key='+sort_key;
		}
		if(typeof subcategory !== "undefined" &&  filters[0]!== "undefined"){
			url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsBySubcategoryId&id='+subcategory+'&filters='+filters+'&page_size=9'+'&page='+ page_num+'&sort_key='+sort_key;
		}
		if(typeof category !== "undefined" &&  filters[0] !== "undefined"){
			url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsByCategoryId&id='+category+'&filters='+filters+'&page_size=9'+'&page='+ page_num+'&sort_key='+sort_key;
		}
	}
	if(typeof sort_key !== "undefined" && typeof sort_order !== "undefined"){

		if(typeof search !== "undefined"){
			console.log("meee");
			url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsByName&name='+search+'&page_size=9'+'&page='+ page_num+'&sort_key='+sort_key+'&sort_order='+sort_order;
		}
		if(typeof subcategory !== "undefined"){
			url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsBySubcategoryId&id='+subcategory+'&page_size=9'+'&page='+ page_num+'&sort_key='+sort_key+'&sort_order='+sort_order;
		}
		if(filters[0]!== "undefined" && filters !== "[]"){
			url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllProducts&filters='+filters+'&page_size=9'+'&page='+ page_num+'&sort_key='+sort_key+'&sort_order='+sort_order;
		}
		if(typeof subcategory !== "undefined" &&  filters[0]!== "undefined"){
			url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsBySubcategoryId&id='+subcategory+'&filters='+filters+'&page_size=9'+'&page='+ page_num+'&sort_key='+sort_key+'&sort_order='+sort_order;
		}
		if(typeof category !== "undefined" &&  filters[0] !== "undefined"){
			url2 = 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetProductsByCategoryId&id='+category+'&filters='+filters+'&page_size=9'+'&page='+ page_num+'&sort_key='+sort_key+'&sort_order='+sort_order;
		}
	}


	console.log(url2);

	$.ajax({
		url: url2 ,dataType:"jsonp"
	}).done( function(data2){
		
		for(var j=0; j<data2.products.length; j++){

			var img = $('<div class="col-md-3 prod-thumbnail">'+
				'<div class="thumbnail">'+
				'<a href=product.html?id='+data2.products[j].id+'>'+
				'<img class="product-display" src='+ data2.products[j].imageUrl[0]+ '>'+ '</img>'+
				'</a>'+
				'<div class="caption">'+
				'<h4>'+data2.products[j].name+'</h4>'+
				'<h4>$'+ data2.products[j].price+'</h4>'+
				'</div>'+
				'</div>'+
				'</div>');


			$("#all-products").append(img);
		}
		//}

		

		if(typeof search !== "undefined" ){
			var results ='<h3 class="align-center">Se encontraron ' +data2.products.length + ' productos</h3>';
			$("#search-results").append(results);
		}
		var pagination= '<div class="row container-margins">'+
		'<div class="col-md-9">'+
		'<ul class="pagination">'+
		'<li><button onclick="page_before();">&laquo;</button></li>'+

									//'<li class="active"><a href="#">'+page_num+'</a></li>'+
									'<li><button onclick="next_page();">&raquo;</button></li>'+
									'</ul>'+
									'</div>'+
									'</div>';

									$("#pagination1").append(pagination);
								});






});

function page_before(){
	var newlocation=(location.href).replace('&page_num='+page_num, '');
	
	if(parseInt(page_num)===1){
		page_num=1;

		location.href=newlocation+'&page_num='+page_num;
	}else {
		page_num--;
		location.href=newlocation+'&page_num='+page_num;

		
	}
}

function next_page(){
	var newlocation=(location.href).replace('&page_num='+page_num, '');
	
	page_num= parseInt(page_num)+1;
	location.href=newlocation+'&page_num='+page_num;


}
