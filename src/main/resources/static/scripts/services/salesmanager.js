'use strict';

var SalesManager = (function(){
	
	function create(){
		var obj = App.serializeObject('salesManagerRegisterForm');
		obj.company = {'id': parseFloat(obj.company)};
		obj.user = {'id': parseFloat(sessionStorage.getItem('userId'))};
		
		var url = App.URL().BASE+App.URL().SALE+'createSalesManager';
		
		App.PostRequest(url, obj).then(function(res){
			if(res.statusCode == '200'){
				console.log('Creating salesrep manager successfully');
				window.location.reload();
			}else{
				console.log('Error in creating salesrep manager');
			}
		});
	};
	
	return {
		create: create
	};
	
})();