'use strict';

var UserProfile = (function(){
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
		var userObj = App.serializeObject('userInfoEditForm');
		App.PutRequest(URL, userObj).then(function(res){
			console.log('User Create ::: ', userObj);
			if(res.statusCode == '500'){
				$('#errorMsg').html(res.message).fadeOut(10000);
			}else{
				window.location.reload();
			}
		});
	};
	
	var updateDetail = function(){
		var usrDetailUrl = App.URL().BASE+App.URL().USER_DETAIL;
		var userDetailObj = App.serializeObject('userDetailEditForm');
		userDetailObj.user = {id: userDetailObj.userId};
		App.PutRequest(usrDetailUrl, userDetailObj).then(function(res){
			console.log('User Create ::: ', userDetailObj);
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
	
	var displayUserInfoEditBtn = function(){
		var userInfoHeadDiv = $('.userInfoHeadDiv');
		var userInfoDiv = $('.userInfoDiv');
		var userFormElem = $('#userInfoEditForm');
		
		if(userFormElem.hasClass('hidden')){
			userFormElem.removeClass('hidden');
			userInfoHeadDiv.addClass('hidden');
			userInfoDiv.addClass('hidden');
		}else{
			userFormElem.addClass('hidden');
			userInfoHeadDiv.removeClass('hidden');
			userInfoDiv.removeClass('hidden');
		}
	};
	
	var displayUserDetailEditBtn = function(){
		var userDetailHeadDiv = $('.userDetailHeadDiv');
		var userDetailEditDiv = $('.userDetailDiv');
		var userDetailEditFormElem = $('#userDetailEditForm');
		
		if(userDetailEditFormElem.hasClass('hidden')){
			userDetailEditFormElem.removeClass('hidden');
			userDetailHeadDiv.addClass('hidden');
			userDetailEditDiv.addClass('hidden');
		}else{
			userDetailEditFormElem.addClass('hidden');
			userDetailHeadDiv.removeClass('hidden');
			userDetailEditDiv.removeClass('hidden');
		}
	};
	
	return {
		create: create,
		update: update,
		updateDetail: updateDetail,
		findUserById: findUserById,
		deleteUser: deleteUser,
		displayUserInfoEditBtn: displayUserInfoEditBtn,
		displayUserDetailEditBtn: displayUserDetailEditBtn
	}
	
})();

function initMap() {
	var lat = $('#latitude').val();
	var lng = $('#longitude').val();
	if(lat && lng){
		var uluru = {lat: parseFloat(lat), lng: parseFloat(lng)};
		var map = new google.maps.Map(document.getElementById('map'), {
			zoom: 18,
			center: uluru
		});
		var marker = new google.maps.Marker({
			position: uluru,
			map: map
		});
	}
  }
