'use strict';

var Deal = (function(){
	var URL = App.URL().BASE+App.URL().DEAL;
	
	var create = function(){
		var obj = App.serializeObject('newAdvertisementform');
		var deal = new Object();
		deal.name = obj.name;
		deal.description = obj.description;
		deal.contact = obj.contact;
		deal.subCategory = obj.subCategory;
		deal.city = obj.city;
		deal.placeName = obj.placeName;
		deal.user = sessionStorage.getItem('userId');
		
		var formData = new FormData();
		formData.append('file', $('#file')[0].files[0]);
		formData.append('deal', JSON.stringify(deal));
		
		App.PostFormData(URL, formData).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating advertisement. Please try again later.').fadeIn().fadeOut(5000);
			}
		});
	};
	
	return {
		create: create
	}
	
})();