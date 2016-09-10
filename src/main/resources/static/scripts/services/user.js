'use strict';

var User = (function(){
	var URL = App.URL().BASE+App.URL().USER;
	
	var findUserById = function(userId){
		console.log('UserId :::: ', userId);
		App.GetRequest(URL+userId).then(function(res){
			console.log('findUserById ::: ',res);
			var obj = res.data;
			$('#editId').val(obj.id);
			$('#editName').val(obj.name);
			$('#editEmail').val(obj.email);
			$('#editMobile').val(obj.mobile);
			$('#editUserType').val(obj.userType);
			$('#editPassword').val(obj.password);
		});
	};
	
	var create = function(){
		var userObj = App.serializeObject('userRegisterform');
		if(userObj.plan){
			userObj.plan = {id: parseInt(userObj.plan)};
		}else{
			userObj.plan = null;
		}
		App.PostRequest(URL, userObj).then(function(res){
			console.log('User Create ::: ', userObj);
			if(res.statusCode == '500'){
				$('#errorMsg').html(res.message).fadeOut(10000);
			}else{
				window.location.reload();
			}
//			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	};
	
	var update = function(){
		var userObj = App.serializeObject('editUserform');
		App.PutRequest(URL, userObj).then(function(res){
			console.log('User Create ::: ', userObj);
			if(res.statusCode == '500'){
				$('#errorMsg').html(res.message).fadeOut(10000);
			}else{
				window.location.reload();
			}
		});
		
	};
	
	var deleteUser = function(userId){
		App.DeleteRequest(URL+userId).then(function(res){
			window.location.reload();
		});
	};
	
	return {
		create: create,
		update: update,
		findUserById: findUserById,
		deleteUser: deleteUser
	}
})();