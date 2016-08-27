'use strict';

var SalesManager = (function(){
	
	var create = function(){
		var url = App.URL().BASE+App.URL().SALE;
		$.ajax({
			url: url,
			method: App.method.POST,
			contentType: App.applicationJson
		}).done(function(res){
			console.log('Creating salesrep manager successfully');
			window.location.reload();
		}).error(function(err){
			console.log('Error in creating salesrep manager');
		});
	};
	
	return {
		create: create
	}
	
})();