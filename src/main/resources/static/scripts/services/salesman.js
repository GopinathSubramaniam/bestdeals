'use strict';

var SalesMan = (function(){
	
	var findSalesman = function(salesmanId){
		console.log('Id ::: ', salesmanId);
	};
	var create = function(){
		
		var url = App.URL().BASE+App.URL().USER;
		
		$.ajax({
			url: '',
			method: '',
			
		}).done(function(){
			
		}).error(function(){
			
		});
	};
	
	return {
		findSalesman : findSalesman,
		create: create
	}
	
})();