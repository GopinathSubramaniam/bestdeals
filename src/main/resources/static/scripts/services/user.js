'use strict';

var User = (function(){
	var URL = App.URL().BASE+App.URL().USER;
	
	var findUserById = function(userId){
		console.log('UserId :::: ', userId);
		App.GetRequest(URL+userId).then(function(res){
			console.log('findUserById ::: ',res);
		});
	};
	
	var create = function(){
		var userObj = App.serializeObject('userRegisterform');
		App.PostRequest(URL, userObj).then(function(res){
			console.log('User Create ::: ', userObj);
			window.location.reload();
//			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	};
	
	var deleteUser = function(userId){
		App.DeleteRequest(URL+userId).then(function(res){
			window.location.reload();
//			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	}
	
	return {
		findUserById: findUserById,
		create: create,
		deleteUser: deleteUser
	}
})();