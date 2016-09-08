'use strict';

var Salesman = (function(){
	var URL = App.URL().BASE+App.URL().SALE;
	
	var create = function(){
		var obj = App.serializeObject('salesRegisterform');
		
		obj.company = {'id':parseFloat(obj.company)};
		obj.salesManager = {'id':parseFloat(obj.salesManager)};
		obj.user = {'id':sessionStorage.getItem('userId')};
		App.PostRequest(URL, obj).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
			}
		});
	};
	
	var update = function(){
		var obj = App.serializeObject('editSaleForm');
		
		obj.salesManager = {'id':parseFloat(obj.salesManager)};
		obj.user = {'id':sessionStorage.getItem('userId')};
		App.PutRequest(URL, obj).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
			}
		});
	};
	
	var deleteSalesman = function(id){
		$.ajax({
			url: URL+id,
			method: App.method.DELETE,
		}).done(function(res){
			window.location.reload();
		}).error(function(error){
			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	};
	var findSalesmanById = function(id){
		$.ajax({
			url: URL+id,
			method: App.method.GET,
		}).done(function(res){
			var obj = res.data;
			
			$('#editId').val(obj.id);
			$('#editName').val(obj.name);
			$('#editEmail').val(obj.email);
			$('#editMobile').val(obj.mobile);
			$('#editSalesManager').val(obj.salesManager.id);
			$('#editPassword').val(obj.password);
		}).error(function(error){
			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
		
		
	};
	
	return {
		create: create,
		update: update,
		deleteSalesman: deleteSalesman,
		findSalesmanById: findSalesmanById
	}
	
})();