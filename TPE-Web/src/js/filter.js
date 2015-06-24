


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

	
	$.ajax({
		url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllProducts", dataType: "jsonp"
	}).done(function(data) {
		

		var marca = '<label class="control-label">Marca:</label>'+
		'<select class="form-control form-box" data-validate="required,verifySelect">';
		
		for(var i=0;i<data.filters.length;i++){
			if(data.filters[i].name == "Marca" ){
				marca+='<option id="selectDefault">Marca</option>';

				for(var j=0;j<data.filters[i].values.length;j++){
					marca+='<option name="Marca" value="'+data.filters[i].values[j]+'">'+data.filters[i].values[j]+'</option>';
				}
				marca+='</select>';	
			}
		}	
		$("#filter-marca").append(marca);

		var color = '<label class="control-label">Color:</label>'+
		'<select class="form-control form-box" data-validate="required,verifySelect">';
		for(var i=0;i<data.filters.length;i++){
			if(data.filters[i].name == "Color" ){
				color+='<option id="selectDefault">Color</option>';

				for(var j=0;j<data.filters[i].values.length;j++){
					color+='<option name="Color" value="'+data.filters[i].values[j]+'">'+data.filters[i].values[j]+'</option>';
				}
				color+='</select>';	
			}
		}	

		$("#filter-color").append(color);
		var ocasion = '<label class="control-label">Ocasion:</label>'+
		'<select class="form-control form-box" data-validate="required,verifySelect">';
		for(var i=0;i<data.filters.length;i++){
			if(data.filters[i].name == "Ocasion" ){
				ocasion+='<option id="selectDefault">Ocasión</option>';
				for(var j=0;j<data.filters[i].values.length;j++){
					ocasion+='<option name="Ocasion" value="'+data.filters[i].values[j]+'">'+data.filters[i].values[j]+'</option>';
				}
				ocasion+='</select>';	
			}
		}	
		$("#filter-ocasion").append(ocasion);

		var category=GetURLParameter('category');
		var gender=GetURLParameter('gender');
		var age=GetURLParameter('age');
		var filters=unescape(GetURLParameter('filters'));
		if(typeof category !== "undefined"){


			var subcategory = '<label class="control-label">Subcategorias:</label>'+
			'<select class="form-control form-box" data-validate="required,verifySelect">';
			subcategory+='<option id="selectDefault">Subcategorias</option>';
			if(typeof age!== "undefined" && typeof gender !== "undefined"){

				$.ajax({
					url: 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllSubcategories&id='+category+'&filters=[{"id":1,"value":"'+gender+'"},{"id":2,"value":"'+age+'"}]', dataType: "jsonp"
				}).done(function(data2) {
					for(var i=0;i<data2.subcategories.length;i++){
						subcategory+='<option name="subcategorias" value="'+data2.subcategories[i].name+'">'+data2.subcategories[i].name+'</option>';
					}	
					subcategory+='</select>';	
					$("#filter-subcategory").append(subcategory);

				});
			}else if(typeof age!== "undefined"){
				$.ajax({
					url: 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllSubcategories&id='+category+'&filters=[{"id":2,"value":"'+age+'"}]', dataType: "jsonp"
				}).done(function(data2) {
					for(var i=0;i<data2.subcategories.length;i++){
						subcategory+='<option name="subcategorias" value="'+data2.subcategories[i].name+'">'+data2.subcategories[i].name+'</option>';
					}	
					subcategory+='</select>';	
					$("#filter-subcategory").append(subcategory);

				});
			}else if(typeof gender !=="undefined"){
				$.ajax({
					url: 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllSubcategories&id='+category+'&filters=[{"id":1,"value":"'+gender+'"}]', dataType: "jsonp"
				}).done(function(data2) {
					for(var i=0;i<data2.subcategories.length;i++){
						subcategory+='<option name="subcategorias" value="'+data2.subcategories[i].name+'">'+data2.subcategories[i].name+'</option>';
					}	
					subcategory+='</select>';	
					$("#filter-subcategory").append(subcategory);

				});

			}else{
				$.ajax({
					url: 'http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllSubcategories&id='+category, dataType: "jsonp"
				}).done(function(data2) {
					for(var i=0;i<data2.subcategories.length;i++){
						subcategory+='<option name="subcategorias" value="'+data2.subcategories[i].name+'">'+data2.subcategories[i].name+'</option>';
					}	
					subcategory+='</select>';	
					$("#filter-subcategory").append(subcategory);

				});
			}

		}else if(typeof gender !== "undefined"){
			$.ajax({
				url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllCategories", dataType: "jsonp"
			}).done(function(data) {
				var categories ='<label class="control-label">Categorias:</label>'+
				'<select class="form-control form-box" data-validate="required,verifySelect">';
				categories+='<option id="selectDefault">Categorias</option>';
				for(var i=0;i<data.categories.length;i++){
					categories+='<option name="categories" value="'+data.categories[i].name+'">'+data.categories[i].name+'</option>';
				}	
				categories+='</select>';	
				$("#filter-category").append(categories);


			});



		}else if(typeof age !== "undefined"){
			$.ajax({
				url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllCategories", dataType: "jsonp"
			}).done(function(data) {
				var categories ='<label class="control-label">Categorias:</label>'+
				'<select class="form-control form-box" data-validate="required,verifySelect">';
				categories+='<option id="selectDefault">Categorias</option>';
				for(var i=0;i<data.categories.length;i++){	
					for(var j=0;j<data.categories[i].attributes.length;j++){
						if(data.categories[i].attributes[j].name === "Edad"){
							for(var k=0; k < data.categories[i].attributes[j].values.length;k++){
								if(data.categories[i].attributes[j].values[k] === age){
									categories+='<option name="categories" value="'+data.categories[i].name+'">'+data.categories[i].name+'</option>';
								}

							}

						}
					}

				}	
				categories+='</select>';	
				$("#filter-category").append(categories);
			});
		}
	});
});

function getColorValue(str) {

	var options = document.getElementsByName("Color");
	for (var i = 0; i < options.length; i++) {
		if (options[i].selected) {
			str.color=options[i].text;
		}
	}
}


function getOcasionValue(str) {

	var options = document.getElementsByName("Ocasion");
	for (var i = 0; i < options.length; i++) {
		if (options[i].selected) {
			str.ocasion=options[i].text;
		}
	}
}


function getMarcaValue(str) {

	var options = document.getElementsByName("Marca");

	for (var i = 0; i < options.length; i++) {
		if (options[i].selected) {
			str.marca=options[i].text;
			
		}
	}
}

function getCategoryValue(str) {
	

	var options = document.getElementsByName("categories");
	
	for (var i = 0; i < options.length; i++) {
		if (options[i].selected) {
			str.category=options[i].text;
			
		}
	}
}



function getOrderBy(str){
	var options = document.getElementsByName("OrderBy");
	for (var i = 0; i < options.length; i++) {
		if (options[i].selected) {
			str.order=options[i].value;
		}
	}
}

function getSubCategoryValue(str){
	var options = document.getElementsByName("subcategorias");
	for (var i = 0; i < options.length; i++) {
		if (options[i].selected) {
			str.subcategory=options[i].value;
		}
	}
}

function filter() {
	
	var s={
		marca:"",
		ocasion:"",
		color:"",
		category:"",
		subcategory:""
	};


	getMarcaValue(s);
	getOcasionValue(s);
	getColorValue(s);
	getCategoryValue(s);
	getSubCategoryValue(s);

	
	var gender = GetURLParameter('gender');
	var age = GetURLParameter('age');
	var category = GetURLParameter('category');
	var subcategory= GetURLParameter('subcategory');
	var filtros= unescape(decodeURI(GetURLParameter('filters')));
	var search = unescape(GetURLParameter('search'));
	

	if ( filtros !== "undefined" && typeof filtros !== undefined) {

		filters=filtros.split('-');
		

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
	}
	if(filtros === "undefined" || typeof filtros === undefined){

		var filters="[";
		if (typeof gender !== "undefined") {
			filters += '{"id":1,"value":"'+ gender+'"}';
			filters+='-';
		}

		if (typeof age !== "undefined") {
			filters += '{"id":2,"value":"'+ age+'"}';
			filters+='-';
		}

		if(s.marca!= ""){
			filters+='{"id":9,"value":"'+s.marca+'"}';
			filters+='-';
		}
		if(s.ocasion!= ""){
			filters+='{"id":3,"value":"'+s.ocasion+'"}';
			filters+='-';
		}
		if(s.color!= ""){
			filters+='{"id":4,"value":"'+s.color+'"}';
		}

		if(s.color == ""){

		}
		filters+=']';
	}
	if(filtros  !== "undefined" && typeof filtros !== undefined){
		var flag=false;
		for (var i = 0; i <filters.length; i++) {
			for(var j = 0;  !flag &&j <filters[i].length ; j++){
				if(filters[i][j]===']' ){
					filters.splice(i,1);
					flag=true;
					
				}
			}
			
		}
		filters+='-';
		if(s.marca!= ""){

			filters+=('{"id":9,"value":"'+s.marca+'"}');
			filters+='-';

		}
		if(s.ocasion!= ""){
			filters+=('{"id":3,"value":"'+s.ocasion+'"}');
			filters+='-';

		}
		if(s.color!= ""){
			filters+=('{"id":4,"value":"'+s.color+'"}');
			filters+='-';

		}
		filters+=(']');
	}

	if ( s.subcategory=== "undefined" && typeof s.subcategory=== undefined) {
		

		filters += '&subcategory='+ subcategory;
	}


	if (typeof category !== "undefined") {
		filters += '&category='+ category;
	}else if(s.category!=""){
		if(s.category == "Indumentaria"){
			filters+='&category=' + 2;

		}else if(s.category == "Calzado"){
			filters+='&category=' + 1;

		}else{
			filters+='&category=' + 3;

		}
	}
	if(search !== "undefined" && typeof search !== undefined){
		window.location.href='products.html?search='+search+'&filters='+filters;
		return;
	}


	if(s.subcategory !== ""){

		$.ajax({
			url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllSubcategories&id=1",
			dataType: "jsonp"
		}).done(function(data) {
			for (var i = 0; i < data.subcategories.length; i++) {
				if(data.subcategories[i].name == s.subcategory){
					filters += '&subcategory='+ data.subcategories[i].id;
				}
			}
			$.ajax({

				url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllSubcategories&id=2", dataType: "jsonp"
			}).done(function(data) {

				for (var i = 0; i < data.subcategories.length; i++) {
					if(data.subcategories[i].name == s.subcategory){
						filters += '&subcategory='+ data.subcategories[i].id;
					}
				}
				$.ajax({
					url: "http://eiffel.itba.edu.ar/hci/service3/Catalog.groovy?method=GetAllSubcategories&id=3", dataType: "jsonp"
				}).done(function(data) {

					for (var i = 0; i < data.subcategories.length; i++) {
						if(data.subcategories[i].name == s.subcategory){
							filters += '&subcategory='+ data.subcategories[i].id;
						}
					}

					window.location.href='products.html?filters='+filters;


				});
			});
		});

	}else {
		window.location.href='products.html?filters='+filters;
	}
}



function ordenar(){
	
	s1={
		order:"",
	};
	getOrderBy(s1);
	
	
	var gender = GetURLParameter('gender');
	var age = GetURLParameter('age');
	var category = GetURLParameter('category');
	var filters =unescape(GetURLParameter('filters')); 
	var search = unescape(GetURLParameter('search'));
	var filters2="[";
	

	if(search !== "undefined" && typeof search !== undefined){
		if(s1.order==="Desc"){
			window.location.href='products.html?search='+search+'&sort_order=desc&sort_key=precio';
		}else if(s1.order ==="Asc"){
			window.location.href='products.html?search='+search+'&sort_order=asc&sort_key=precio';
		}else if (s1.order ==="PorNombre"){
			window.location.href='products.html?search='+search+'&sort_key=nombre';
		}else if(s1.order ==="PorMarca"){
			window.location.href='products.html?search='+search+'&sort_key=marca';
		}
		return;
	}

	if( filters === "undefined" || typeof filters == undefined){

		if (typeof gender !== "undefined") {
			filters2 += '{"id":1,"value":"'+ gender+'"}';
			filters2+='-';
		}
		if (typeof age !== "undefined") {
			filters2 += '{"id":2,"value":"'+ age+'"}';
			filters2+='-';
		}	
		filters2+=']';
	}else{
		//filters2+=filters;
	}

	

	console.log(s1.order);


	if (typeof category !== "undefined") {
		filters2 += '&category='+ category;
	}
	if(s1.order ==="Asc"){
		if( filters !== "undefined" && typeof filters !== undefined &&  typeof filters !== "undefined"){
			window.location.href='products.html?filters='+filters+'&sort_order=asc&sort_key=precio';

		}else if( filters2 !== "undefined" && typeof filters !== undefined){
			window.location.href='products.html?filters='+filters2+'&sort_order=asc&sort_key=precio';

		}

	}else if(s1.order==="Desc"){

		if( filters !== "undefined"&& typeof filters !== undefined){
			window.location.href='products.html?filters='+filters+'&sort_order=desc&sort_key=precio';
		}else if( filters2 !== "undefined"&& typeof filters2 !== undefined){
			window.location.href='products.html?filters='+filters2+'&sort_order=desc&sort_key=precio';
		}

	}else if(s1.order==="PorNombre"){
		if( filters !== "undefined"&& typeof filters !== undefined){
			window.location.href='products.html?filters='+filters+'&sort_key=nombre';
		}else if( filters2 !== "undefined"&& typeof filters2 !== undefined){
			window.location.href='products.html?filters='+filters2+'&sort_key=nombre';
		}

	}else if(s1.order=="PorMarca"){

		if( filters !== "undefined"&& typeof filters !== undefined){
			window.location.href='products.html?filters='+filters+'&sort_key=marca';
		}else if( filters2 !== "undefined"&& typeof filters2 !== undefined){
			window.location.href='products.html?filters='+filters2+'&sort_key=marca';
		}

	}else{
		if( filters !== "undefined"&& typeof filters !== undefined){
			window.location.href='products.html?filters='+filters;
		}else if( filters2 !== "undefined"&& typeof filters2 !== undefined){
			window.location.href='products.html?filters='+filters2;
		}
	}
}
