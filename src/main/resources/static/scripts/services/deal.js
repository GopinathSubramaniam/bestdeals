'use strict';

var Deal = (function(){
	$('#fileImage').addClass('hidden');
	var URL = App.URL().BASE+App.URL().DEAL;
	
	var create = function(){
		var obj = App.serializeObject('newAdvertisementform');
		var deal = new Object();
		deal.id = obj.id;
		deal.name = obj.name;
		deal.description = obj.description;
		deal.contact = obj.contact;
		deal.subCategory = obj.subCategory;
		deal.city = obj.city;
		deal.placeName = obj.placeName;
		deal.user = sessionStorage.getItem('userId');
		deal.createdDate = obj.createdDate;
		
		var formData = new FormData();
		
		if($('#file')[0].files[0]){
			formData.append('file', $('#file')[0].files[0]);
		}else{
			var f = new File([""], "null.txt", {type: "text/plain", lastModified: new Date()})
			formData.append('file', f);
		}
		formData.append('deal', JSON.stringify(deal));
		
		App.PostFormData(URL, formData).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating advertisement. Please try again later.').fadeIn().fadeOut(20000);
			}
		});
	};
	
	var findById = function(id){
		App.GetRequest(URL+'findOne/'+id).then(function(res){
			if(res.statusCode == '200'){
				var obj = res.data;
				$('#advId').val(obj.id);
				$('#inputCategory').val(obj.subCategory.category.id);
				$('#inputSubCategory').html('<option value="'+obj.subCategory.id+'">'+obj.subCategory.name+'</option>');
				$('#inputState').val(obj.city.state.id);
				$('#inputCity').html('<option value="'+obj.city.id+'">'+obj.city.name+'</option>');
				$('#inputPlaceName').val(obj.placeName);
				$('#inputName').val(obj.name);
				$('#inputdescription').val(obj.description);
				$('#inputContact').val(obj.contact);
				$('#inputCreatedDate').val(obj.createdDate);
				$('#fileImage').attr('src', obj.imgUrl);
				$('#fileImage').removeClass('hidden');
			}else{
				$('#errorMsg').html('<i class="fa fa-times"></i> Error in Fetching advertisement. Please try again later.').fadeIn().fadeOut(20000);
			}
		});
	};
	
	var deleteDeal = function(id){
		App.DeleteRequest(URL+id).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				$('#errorMsg').html('<i class="fa fa-times"></i> Error in deleting advertisement. Please try again later.').fadeIn().fadeOut(20000);
			}
		});
	};
	
	return {
		create: create,
		deleteDeal: deleteDeal,
		findById: findById
	}
	
})();