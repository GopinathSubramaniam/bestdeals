'use strict';

var SalesManager = (function(){
	
	var fields = {
		name : App.validateRules.name,
		email : App.validateRules.email,
		mobile : App.validateRules.mobile,
		password : App.validateRules.password
	};
	App.bindValidation('salesManagerRegisterForm', fields, function(){
		 var saleObj = App.serializeObject('salesManagerRegisterForm');
		 SalesManager.create(saleObj);
	});
	
	var URL = App.URL().BASE+App.URL().SALE;
	var create = function(obj){
		obj.user = {'id': parseFloat(sessionStorage.getItem('userId'))};
		var url = URL+'createSalesManager';
		App.PostRequest(url, obj).then(function(res){
			if(res.statusCode == '200'){
				console.log('Creating salesrep manager successfully');
				window.location.reload();
			}else{
				console.log('Error in creating salesrep manager');
			}
		});
	};
	
	var deleteSalesManager = function(id){
		var url = URL+'deleteSalesManager/'+id;
		App.DeleteRequest(url).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				$('#errorMsg').html(res.message).fadeOut(20000);
			}
		});
	};
	
	var findSalesManager = function(id){
		var url = URL+'findSalesManagerById/'+id;
		App.GetRequest(url).then(function(res){
			var obj = res.data;
			if(obj != null){
				$('#editManagerId').val(obj.id);
				$('#inputname').val(obj.name);
				$('#inputEmail').val(obj.email);
				$('#inputMobile').val(obj.mobile);
				$('#inputPassword').val(obj.password);
			}
		});
	};
	
	return {
		create: create,
		deleteSalesManager: deleteSalesManager,
		findSalesManager: findSalesManager
	};
	
})();