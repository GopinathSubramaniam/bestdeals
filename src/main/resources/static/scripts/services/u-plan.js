'use strict';

var UserPlan = (function(){
	var URL = App.URL().BASE+App.URL().PLAN;
	
	var changePlan = function(){
		
		var elem = $('input[type="radio"]:checked');
		if(elem){
			var planId = elem.val();
			var userId = sessionStorage.getItem('userId');
			var url = URL+userId+'/'+planId;
			App.GetRequest(url).then(function(res){
				window.location.reload();
			});	
		}
	};
	
	$('#changePlan').attr('href', 'updatePlan?pid='+ $('input[type="radio"]:checked').val());
	$('#accordion input[type="radio"]').click(function(ev){
		console.log('ev :: ', ev);
		$('#changePlan').attr('href', 'updatePlan?pid='+ev.currentTarget.value);
	});
	
	
	return {
		changePlan: changePlan
	}
})();