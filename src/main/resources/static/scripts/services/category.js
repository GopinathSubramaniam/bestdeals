'use strict';

var Category = (function(){
	var CAT_URL = App.URL().BASE+App.URL().CATEGORY;
	var SUB_CAT_URL = App.URL().BASE+App.URL().SUB_CATEGORY;
	
	var create = function(){
		var obj = App.serializeObject('newCategoryForm');
		var formData = new FormData();

		if($('#categoryImage')[0].files[0]){
			formData.append('imageFile', $('#categoryImage')[0].files[0]);
		}else{
			var f = new File([""], "null.txt", {type: "text/plain", lastModified: new Date()})
			formData.append('imageFile', f);
		}
		formData.append('category', JSON.stringify(obj));
		
		App.PostFormData(CAT_URL, formData).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
			}
		});
	};
	var findCategoryById = function(id){
		$.ajax({
			url: CAT_URL+id,
			method: App.method.GET,
		}).done(function(res){
			console.log('findCategoryById Res ::: ', res);
			$('#inputid').val(res.data.id);
			$('#inputname').val(res.data.name);
			$('#inputDescription').val(res.data.description);
			$('#inputImgUrl').val(res.data.imgUrl);
		}).error(function(error){
			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	};
	
	var deleteCategory = function(id){
		$.ajax({
			url: CAT_URL+id,
			method: App.method.DELETE,
		}).done(function(res){
			if(res.statusCode == 200)
				window.location.reload();
			else
				$('#catMsgId').html('<i class="fa fa-times"></i> Error in deleting user. Please try again later.').fadeIn().fadeOut(20000);
		}).error(function(error){
			var errObj = JSON.parse(error.responseText);
			$('#catMsgId').html('<i class="fa fa-times"></i>'+errObj.error+'.'+errObj.message).fadeIn().fadeOut(20000);
		});
	};
	
	var createSubCategory = function(){
		var obj = App.serializeObject('newSubCategoryForm');
		obj.category = {'id':parseFloat(obj.category)};
		App.PostRequest(SUB_CAT_URL, obj).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
			}
		});
	};
	var findSubCategoryById = function(id){
		$.ajax({
			url: SUB_CAT_URL+'findOne/'+id,
			method: App.method.GET,
		}).done(function(res){
			console.log('findCategoryById Res ::: ', res);
			var obj = res.data;
			if(obj != null){
				$('#inputSubcatId').val(obj.id);
				$('#inputSubcatName').val(obj.name);
				$('#inputSubcatDescription').val(obj.description);
				$('#inputSubcatCategory').val(obj.category.id);
			}
			
		}).error(function(error){
			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	};
	
	var deleteSubCategory = function(id){
		$.ajax({
			url: SUB_CAT_URL+id,
			method: App.method.DELETE,
		}).done(function(res){
			if(res.statusCode == 200)
				window.location.reload();
			else
				$('#subcatMsgId').html('<i class="fa fa-times"></i> Error in deleting user. Please try again later.').fadeIn().fadeOut(20000);
		}).error(function(error){
			var errObj = JSON.parse(error.responseText);
			$('#subcatMsgId').html('<i class="fa fa-times"></i>'+errObj.error+'.'+errObj.message).fadeIn().fadeOut(20000);
		});
	};
	
	
	return {
		create: create,
		findCategoryById: findCategoryById,
		deleteCategory: deleteCategory,
		createSubCategory: createSubCategory,
		findSubCategoryById: findSubCategoryById,
		deleteSubCategory: deleteSubCategory
	}
	
})();