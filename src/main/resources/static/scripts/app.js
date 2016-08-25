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
	
	return {
		URL: URL,
		method: {GET:'GET', POST: 'POST', PUT: 'PUT', DELETE: 'DELETE'},
		dataType: 'json',
		applicationJson: 'application/json',
		serializeObject: serializeObject
	}
})();

$('.sidebar-menu li').click(function(ev){
	console.log('>>>> Side bar clicked >>>');
	var elems = $('.sidebar-menu li');
	$.each(elems, function(i, elem){
		elem.className = '';
	});
	ev.currentTarget.className = 'active';
});