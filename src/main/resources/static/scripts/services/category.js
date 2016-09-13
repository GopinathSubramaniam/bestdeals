'use strict';

var Category = (function(){
	var CAT_URL = App.URL().BASE+App.URL().CATEGORY;
	var SUB_CAT_URL = App.URL().BASE+App.URL().SUB_CATEGORY;
	
	var create = function(){
		var obj = App.serializeObject('newCategoryForm');
		
		obj.category = {'id':parseFloat(obj.category)};
		App.PostRequest(CAT_URL, obj).then(function(res){
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
			
		}).error(function(error){
			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	};
	
	var deleteCategory = function(id){
		$.ajax({
			url: CAT_URL+id,
			method: App.method.DELETE,
		}).done(function(res){
			window.location.reload();
		}).error(function(error){
			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
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
			url: SUB_CAT_URL+id,
			method: App.method.GET,
		}).done(function(res){
			console.log('findCategoryById Res ::: ', res);
		}).error(function(error){
			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	};
	
	var deleteSubCategory = function(id){
		$.ajax({
			url: SUB_CAT_URL+id,
			method: App.method.DELETE,
		}).done(function(res){
			window.location.reload();
		}).error(function(error){
			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
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