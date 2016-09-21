'use strict';

var App = (function(){
	

	var serializeObject = function(formName){
		var arrayObj = $('#'+formName).serializeArray();
		var obj = new Object();
		arrayObj.forEach(function(val, i){
			obj[val.name] = val.value;
		});
		return obj;
	};
	var URL = function(){
		var obj = new Object();
		obj.BASE = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port + '/BestDeals';
		obj.LOGIN = '/rest/login/';
		obj.USER = '/rest/user/';
		obj.SALE = '/rest/salesman/';
		obj.PLAN = '/rest/plan/';
		obj.DEAL = '/rest/deal/';
		obj.CATEGORY = '/rest/category/';
		obj.SUB_CATEGORY = '/rest/subcat/';
		obj.APP = '/rest/base/';
		return obj;
	};
	var PostRequest = function(url, data){
		var defer = $.Deferred();
		$.ajax({
			url: url,
			method: App.method.POST,
			contentType: 'application/json',
			data: JSON.stringify(data)
		}).done(function(response){
			console.log('PostRequest Done :::: ', response);
			defer.resolve(response);
		}).error(function(error){
			console.log('PostRequest Error :::: ', error);
			defer.resolve(error);
		});
		return defer.promise();
	};
	var PostFormData = function(url, data){
		var defer = $.Deferred();
		$.ajax({
			url: url,
			method: App.method.POST,
			processData: false,
			contentType: false,
			data: data
		}).done(function(response){
			console.log('PostRequest Done :::: ', response);
			defer.resolve(response);
		}).error(function(error){
			console.log('PostRequest Error :::: ', error);
			defer.resolve(error);
		});
		return defer.promise();
	};
	var GetRequest = function(url){
		var defer = $.Deferred();
		$.ajax({
			url: url,
			method: App.method.GET,
			dataType: 'json'
		}).done(function(response){
			console.log('GetRequest Done :::: ', response);
			defer.resolve(response);
		}).error(function(error){
			console.log('GetRequest Error :::: ', error);
			defer.resolve(error);
		});
		return defer.promise();
	};
	var PutRequest = function(url, data){
		var defer = $.Deferred();
		$.ajax({
			url: url,
			method: App.method.PUT,
			contentType: 'application/json',
			data: JSON.stringify(data)
		}).done(function(response){
			console.log('PutRequest Done :::: ', response);
			defer.resolve(response);
		}).error(function(error){
			console.log('PutRequest Error :::: ', error);
			defer.resolve(error);
		});
		return defer.promise();
	};
	
	var DeleteRequest = function(url){
		var defer = $.Deferred();
		$.ajax({
			url: url,
			method: App.method.DELETE
		}).done(function(response){
			console.log('DeleteRequest Done :::: ', response);
			defer.resolve(response);
		}).error(function(error){
			console.log('DeleteRequest Error :::: ', error);
			var errObj = error.responseText != null && error.responseText != '' ? JSON.parse(error.responseText) : {status: 500};
			defer.resolve(errObj);
		});
		return defer.promise();
	};
	
	var getMessage = function(res){
		 return (res.responseJSON != undefined ? res.responseJSON.error : res.message);
	};
	var isAuthenticated = function(){
		var auth = {status: false, message: 'User is authenticated'};
		if(sessionStorage.getItem('userId') !='' && sessionStorage.getItem('userId') != null){
			auth.status = true;
			auth.message = 'User is not authenticated';
		} 
		return auth;
	};
	var findAllCityByState = function(stateId){
		$('#inputCity').html('');
		if(stateId){
			var url = App.URL().BASE+App.URL().APP+'findAllCityByState/'+stateId;
			GetRequest(url).then(function(res){
				res.data.forEach(function(obj, i){
					$('#inputCity').append('<option value='+obj.id+'>'+obj.name+'</option>');
				});
			});
		}else{
			$('#inputCity').html('<option>-- Select --</option>');
		}
	};
	
	var findAllSubCateByState = function(categoryId){
		$('#inputSubCategory').html('');
		if(categoryId){
			var url = App.URL().BASE+App.URL().SUB_CATEGORY+categoryId;
			GetRequest(url).then(function(res){
				res.data.forEach(function(obj, i){
					$('#inputSubCategory').append('<option value='+obj.id+'>'+obj.name+'</option>');
				});
			});
		}else{
			$('#inputSubCategory').html('<option>-- Select --</option>');
		}
	};
	var getAdvRules = function(){
		var advRules = sessionStorage.getItem('planRule');
		if(advRules !== null){
			advRules = JSON.parse(advRules);
		}
		return advRules;
	};
	
	var displayAdvTab = function(){
		var advRules = getAdvRules();
		if(advRules !== null && advRules.max_adv_count == 0){
			$('.sidebar .advertisement').parent().addClass('hidden');
		}
	};
	
	var bindValidation = function(formId, fields, fn){
		$(function(){
			$('#'+formId).bootstrapValidator({
				message: 'Please enter a value',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				fields: fields
			}).on('success.form.bv', function(e){
				e.preventDefault();
				var $form = $(e.target);
				var bv = $form.data('bootstrapValidator');
				fn();
			});
		});
	};
	var validateRules = {
		name : {
			validators : {
				notEmpty : {
					mesage : 'The name is required and can\'t be empty'
				},
				stringLength : {
					min : 4,
					message : 'The name must be more than 4 characters'
				}
			}
		},
		email : {
			validators : {
				notEmpty : {
					message : 'The email address is required and can\'t be empty'
				},
				emailAddress : {
					message : 'The input is not a valid email address'
				}
			}
		},
		mobile : {
			validators : {
				notEmpty : {
					message : 'The mobile is required and can\'t be empty'
				},
				regexp : {
					regexp : /^[0-9]+$/,
					message : 'The mobile can have only number '
				}
			}
		},
		password : {
			validators : {
				notEmpty : {
					message : 'The password is required'
				}
			}
		}
	};
	
	// START
	$('#username').html(sessionStorage.getItem('username'));

//	$('.userProfile').attr('href', 'profile?userId='+sessionStorage.getItem('userId'));
//	$('.advertisement').attr('href', 'advertisement?userId='+sessionStorage.getItem('userId'));

	$('.sidebar-menu li').click(function(ev){
		console.log('>>>> Side bar clicked >>>');
		var elems = $('.sidebar-menu li');
		$.each(elems, function(i, elem){
			elem.className = '';
		});
		ev.currentTarget.className = 'active';
	});
	
	displayAdvTab(); // Hide advertisement tab for non admin ppl
	// END
	
	
	return {
		URL: URL,
		method: {GET:'GET', POST: 'POST', PUT: 'PUT', DELETE: 'DELETE'},
		serializeObject: serializeObject,
		getMessage: getMessage,
		PostRequest: PostRequest,
		PostFormData: PostFormData,
		PutRequest: PutRequest,
		GetRequest: GetRequest,
		DeleteRequest: DeleteRequest,
		isAuthenticated: isAuthenticated,
		findAllCityByState: findAllCityByState,
		findAllSubCateByState: findAllSubCateByState,
		getAdvRules: getAdvRules,
		displayAdvTab: displayAdvTab,
		bindValidation: bindValidation,
		validateRules: validateRules
	};

	
})();