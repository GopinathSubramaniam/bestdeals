'use strict';

var PublicUserPlan = (function(){
	var URL = App.URL().BASE+App.URL().USER;
	
	var create = function(){
		var obj = App.serializeObject('newPlanform');
		
		obj.company = {'id':parseFloat(obj.company)};
		App.PostRequest(URL, obj).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				$('#errorMsg').html('<i class="fa fa-times"></i> '+App.getMessage(res)+'. Please try again later.').fadeIn().fadeOut(5000);
			}
		});
	};
		
	var findPlan = function(id){
		$('#modalTitle').html('Update Plan');
		$('#modalSubmitBtn').html('Update');
		App.GetRequest(URL+'findPublicPlanById/'+id).then(function(res){
			var plan = res.data;
			if(plan){
				$('#publicPlanId').val(plan.id)
				$('#planType').val(plan.planType);
				$('#inputPercentage').val(plan.percentage);
				$('#inputValidityInMonths').val(plan.description);
				$('#inputAmount').val(plan.amount);
				$('#inputDescription').val(plan.description);
				
				$('#startDate').datepicker({format: 'yyyy-mm-dd'}).datepicker("setDate", plan.startDate);
				$('#endDate').datepicker({format: 'yyyy-mm-dd'}).datepicker("setDate", plan.endDate);
			}
		});
	};
	
	var update = function(){
		var obj = App.serializeObject('updatePublicUserPlan');
		
		App.PutRequest(URL+'updatePublicPlan', obj).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				var msg = res.responseJSON != undefined ? res.responseJSON.error : res.message;
				$('#editErrorMsg').html(App.getMessage(res)).fadeOut(10000);
			}
		});
	};
	
	var deletePlan = function(id){
		App.DeleteRequest(URL+id).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				$('#editErrorMsg').html(App.getMessage(res)).fadeOut(10000);
			}
		});
	};
	
	var findAllFranchise = function(id){
		$('#planId').val(id);
		App.GetRequest(App.URL().BASE+App.URL().USER+'findAllByUserType/MERCHANT').then(function(res){
			if(res.statusCode == '200'){
				$('#inputAssignPlan').html('');
				$('#inputAssignPlan').append('<option value="">--- Select ---</option>');
				res.data.forEach(function(obj){
					$('#inputAssignPlan').append('<option value="'+obj.id+'">'+obj.name +' - '+obj.userType+'</option>');
				});
			}
		});
	};
	
	var assignPlanToUser = function(userId, planId){
		App.GetRequest(URL+userId+'/'+planId).then(function(res){
			if(res.statusCode == '200'){
				$('#assignSuccessMsg').html('Plan assigned to merchant successfully').fadeOut(10000);
			}else{
				$('#assignErrorMsg').html(App.getMessage(res)).fadeOut(10000);
			}
		});
	};
	
	return {
		create: create,
		update: update,
		findPlan: findPlan,
		deletePlan: deletePlan,
		findAllFranchise: findAllFranchise,
		assignPlanToUser: assignPlanToUser
	}
	
})();