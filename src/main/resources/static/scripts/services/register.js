var Register = (function(){
	var BASE = App.URL().BASE;
	var URL = BASE+App.URL().APP;
	
	var fields = {
			userType : App.validateRules.dropdown,
			name : App.validateRules.name,
			email : App.validateRules.email,
			password : App.validateRules.name,
			confirmPassword : App.validateRules.confirmPassword,
			mobile : App.validateRules.mobile,
			state : App.validateRules.dropdown,
			city : App.validateRules.dropdown,
			taluka : App.validateRules.dropdown,
			village : App.validateRules.dropdown,
			shopName : App.validateRules.name,
			address1 : App.validateRules.name,
			contactNumber : App.validateRules.name,
			description : App.validateRules.name
		};
	
	App.bindValidation('registerForm', fields, function(){
		 var registerObj = App.serializeObject('registerForm');
		 Register.doRegister(registerObj);
	});
	
	var doRegister = function(registerObj){
		console.log('RegisterObj ::::: ', registerObj);
		var registerUrl = BASE+App.URL().USER+'register';
		App.PostRequest(registerUrl, registerObj).then(function(res){
			if(res.statusCode == '200'){
				$('#registerForm')[0].reset();
				$('#successMessage').html('<i class="fa fa-check"></i>Registeration successfully.').fadeOut(10000);
			}else{
				$('#errorMessage').html('<i class="fa fa-times-circle-o"></i>'+res.message).fadeOut(10000);
			}
		});
	};
	
	var findAllCitiesByStateId = function(stateId){
		App.GetRequest(URL+'findAllCitiesByState/'+stateId).then(function(res){
			var dropdownHtml = '';
			var cities = res.data;
			cities.forEach(function(city, i){
				dropdownHtml += '<option value="'+city.id+'">'+city.name+'</option>';
			});
			$('#inputCity').html(dropdownHtml);
		});
	};
	
	var findAllTalukasByCityId = function(cityId){
		App.GetRequest(URL+'findAllTalukasByCityId/'+cityId).then(function(res){
			var dropdownHtml = '';
			var talukas = res.data;
			talukas.forEach(function(taluka, i){
				dropdownHtml += '<option value="'+taluka.id+'">'+taluka.name+'</option>';
			});
			$('#inputTaluka').html(dropdownHtml);
		});
	};
	
	var findAllVillagesByTalukaId = function(talukaId){
		App.GetRequest(URL+'findAllVillagesByTalukaId/'+talukaId).then(function(res){
			var dropdownHtml = '';
			var villages = res.data;
			villages.forEach(function(village, i){
				dropdownHtml += '<option value="'+village.id+'">'+village.name+'</option>';
			});
			$('#inputVillage').html(dropdownHtml);
		});
	};
	
	return {
		doRegister: doRegister,
		findAllCitiesByStateId: findAllCitiesByStateId,
		findAllTalukasByCityId: findAllTalukasByCityId,
		findAllVillagesByTalukaId: findAllVillagesByTalukaId
	}
	
})();