'use strict';

var User = (function(){
	var findUserById = function(userId){
		console.log('UserId :::: ', userId);
		var url = App.URL().BASE+App.URL().USER+userId;
		
		$.ajax({
			url: url,
			method: App.method.GET,
			dataType: App.dataType,
		}).done(function(res){
			console.log('Res ::: ', res);
		}).error(function(error){
			console.log('Error ::: ', error);
		});
	};
	var create = function(){
		var url = App.URL().BASE+App.URL().USER;
		var userObj = App.serializeObject('userRegisterform');
		console.log('User Obj ::: ', userObj);
		$.ajax({
			url: url,
			method: App.method.POST,
			contentType: App.applicationJson,
			data: JSON.stringify(userObj)
		}).done(function(res){
			console.log('Res ::: ', res);
//			$('#successMsg').html('<i class="fa fa-check"></i> Input with success.').fadeIn().fadeOut(5000);
			window.location.reload();
		}).error(function(error){
			console.log('Error ::: ', error);
			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	};
	
	var deleteUser = function(userId){
		var url = App.URL().BASE+App.URL().USER+userId;
		$.ajax({
			url: url,
			method: App.method.DELETE
		}).done(function(res){
			console.log('Res ::: ', res);
			window.location.reload();
		}).error(function(error){
			console.log('Error ::: ', error);
		});
	}
	
	return {
		findUserById: findUserById,
		create: create,
		deleteUser: deleteUser
	}
})();