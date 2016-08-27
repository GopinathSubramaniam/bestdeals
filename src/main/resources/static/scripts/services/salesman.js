'use strict';

var Salesman = (function(){
	
	var findSalesman = function(salesmanId){
		console.log('Id ::: ', salesmanId);
	};
	var create = function(){
		var obj = App.serializeObject('salesRegisterform');
		var url = App.URL().BASE+App.URL().SALE;
		obj.company = {'id':parseFloat(obj.company)};
		obj.salesManager = {'id':parseFloat(obj.salesManager)};
		obj.user = {'id':sessionStorage.getItem('userId')};
		$.ajax({
			url: url,
			method: App.method.POST,
			contentType: App.applicationJson,
			data: JSON.stringify(obj)
		}).done(function(res){
			window.location.reload();
		}).error(function(error){
			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	};
	var deleteSalesman = function(id){
		var url = App.URL().BASE+App.URL().SALE+id;
		$.ajax({
			url: url,
			method: App.method.DELETE,
		}).done(function(res){
			window.location.reload();
		}).error(function(error){
			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	};
	
	return {
		findSalesman : findSalesman,
		create: create,
		deleteSalesman: deleteSalesman
	}
	
})();