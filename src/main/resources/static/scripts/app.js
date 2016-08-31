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
		obj.BASE = window.location.protocol+'//'+window.location.hostname+':'+window.location.port+'/';
		obj.LOGIN = 'rest/login/';
		obj.USER = 'rest/user/';
		obj.SALE = 'rest/salesman/'
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
			defer.resolve(response);
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
			defer.resolve(response);
		});
		return defer.promise();
	};
	
	var DeleteRequest = function(url){
		var defer = $.Deferred();
		$.ajax({
			url: url,
			method: App.method.DELETE
		}).done(function(response){
			console.log('GetRequest Done :::: ', response);
			defer.resolve(response);
		}).error(function(error){
			console.log('GetRequest Error :::: ', error);
			defer.resolve(response);
		});
		return defer.promise();
	};
	
	return {
		URL: URL,
		method: {GET:'GET', POST: 'POST', PUT: 'PUT', DELETE: 'DELETE'},
		serializeObject: serializeObject,
		PostRequest: PostRequest,
		GetRequest: GetRequest
	};
	
})();

$('#username').html(sessionStorage.getItem('username'));

$('.sidebar-menu li').click(function(ev){
	console.log('>>>> Side bar clicked >>>');
	var elems = $('.sidebar-menu li');
	$.each(elems, function(i, elem){
		elem.className = '';
	});
	ev.currentTarget.className = 'active';
});