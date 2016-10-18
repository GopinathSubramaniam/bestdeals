var Register = (function(){
	var BASE = App.URL().BASE;
	var URL = BASE+App.URL().APP;
	var currentType = '';
	var merchantFields = {
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
	
	App.bindValidation('registerMerchantForm', merchantFields, function(){
		 var registerObj = App.serializeObject('registerMerchantForm');
		 Register.doRegister(registerObj);
	});
	
	var franchiseFields = {
			userType : App.validateRules.dropdown,
			name : App.validateRules.name,
			email : App.validateRules.email,
			password : App.validateRules.name,
			confirmPassword : App.validateRules.confirmPassword,
			mobile : App.validateRules.mobile,
			state : App.validateRules.dropdown
		};
	
	App.bindValidation('registerFranchiseForm', franchiseFields, function(){
		 var registerObj = App.serializeObject('registerFranchiseForm');
		 Register.doRegister(registerObj);
	});
	
	var doRegister = function(registerObj){
		console.log('RegisterObj ::::: ', registerObj);
		var registerUrl = BASE+App.URL().USER+'register';
		if(registerObj.userType == 'MERCHANT'){
			var city = $('#inputCity').find('[value="'+registerObj.city+'"]').text();
			var taluka = $('#inputTaluka').find('[value="'+registerObj.taluka+'"]').text();
			var placeName = $('#inputVillage').find('[value="'+registerObj.village+'"]').text();
			var place = placeName+','+taluka+','+city;
			App.getLatitudeLongitude(place, function(result){
				console.log('Geo Result ::: ', result);
				if(result){
					registerObj.latitude = result.geometry.location.lat();
					registerObj.longitude = result.geometry.location.lng();
				}
				App.PostRequest(registerUrl, registerObj).then(function(res){
					if(res.statusCode == '200'){
						window.location.reload();
						/*$('#registerMerchantForm').bootstrapValidator('resetForm', true);
						$('#registerFranchiseForm').bootstrapValidator('resetForm', true);
						$('#successMessage').html('<i class="fa fa-check"></i>Registeration successfully.').fadeOut(10000);*/
					}else{
						$('#errorMessage').html('<i class="fa fa-times-circle-o"></i>'+res.message).fadeOut(10000);
					}
				});
			});
		}else{
			App.PostRequest(registerUrl, registerObj).then(function(res){
				if(res.statusCode == '200'){
					$('#registerMerchantForm').bootstrapValidator('resetForm', true);
					$('#registerFranchiseForm').bootstrapValidator('resetForm', true);
					
					$('#successMessage').html('<i class="fa fa-check"></i>Registeration successfully.').fadeOut(10000);
				}else{
					$('#errorMessage').html('<i class="fa fa-times-circle-o"></i>'+res.message).fadeOut(10000);
				}
			});
		}
		
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
	
	var changeRegisterForm = function(type){
		currentType = type;
		if(type == 'MERCHANT'){
			$('#registerMerchantForm').removeClass('hidden');
			$('#registerFranchiseForm').addClass('hidden');
			$('#registerMerchantForm #userType').val('MERCHANT');
		}else{
			$('#registerFranchiseForm').removeClass('hidden');
			$('#registerMerchantForm').addClass('hidden');
			$('#registerFranchiseForm #userType').val('FRANCHISE');
		}
	};
	
	changeRegisterForm('MERCHANT');
	return {
		doRegister: doRegister,
		findAllCitiesByStateId: findAllCitiesByStateId,
		findAllTalukasByCityId: findAllTalukasByCityId,
		findAllVillagesByTalukaId: findAllVillagesByTalukaId,
		changeRegisterForm: changeRegisterForm
	}
	
})();