'use strict';

var Plan = (function(){
	var URL = App.URL().BASE+App.URL().PLAN;
	
	var create = function(){
		var obj = App.serializeObject('newPlanform');
		
		obj.company = {'id':parseFloat(obj.company)};
		App.PostRequest(URL, obj).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
			}
		});
	};
	var deletePlan = function(id){
		$.ajax({
			url: URL+id,
			method: App.method.DELETE,
		}).done(function(res){
			window.location.reload();
		}).error(function(error){
			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	};
	
	var findPlan = function(id){
		
	};
	
	return {
		create: create,
		findPlan: findPlan,
		deletePlan: deletePlan
	}
	
})();