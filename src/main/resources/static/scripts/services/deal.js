'use strict';

var Deal = (function(){
	$('#fileImage').addClass('hidden');
	var URL = App.URL().BASE+App.URL().DEAL;

	var create = function(){
		var obj = App.serializeObject('editAdvertisementform');
		var deal = new Object();
		deal.id = obj.id;
		deal.name = obj.name;
		deal.description = obj.description;
		deal.contact = obj.contact;
		var subCatId = $('#dealSubCategory').val();
		if (obj.subCategory == undefined || obj.subCategory == '' || subCatId == "") {
            $('#alertMessageDiv').html('<i class="fa fa-times"></i> Please select Subcategory.');
            $('.alertModal').modal('show');
			return;
		}
		deal.subCategory = obj.subCategory;
		deal.city = obj.city;
		deal.placeName = obj.placeName;
		deal.user = sessionStorage.getItem('userId');
		deal.createdDate = obj.createdDate;
		
		var formData = new FormData();
		
		if($('#file')[0].files[0]){
			formData.append('file', $('#file')[0].files[0]);
		}else{
			var f = new File([""], "null.txt", {type: "text/plain", lastModified: new Date()});
			formData.append('file', f);
		}
		formData.append('deal', JSON.stringify(deal));
		
		App.PostFormData(URL, formData).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				//$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating advertisement. Please try again later.').fadeIn().fadeOut(20000);
                $('#alertMessageDiv').html('<i class="fa fa-times"></i> Error in creating advertisement. Please try again later.');
                $('.alertModal').modal('show');
			}
		});
	};
	
	var findById = function(id){
		App.GetRequest(URL+'findOne/'+id).then(function(res){
			if(res.statusCode == '200'){
				var obj = res.data;
				$('#editAdvId').val(obj.id);
				$('#editInputCategory').val(obj.subCategory.category.id);
				$('#editInputSubCategory').val(obj.subCategory.id);
				$('#editInputName').val(obj.name);
				$('#editInputDesc').val(obj.description);
				$('#editFileImage').attr('src', obj.imgUrl);
				$('#editFileImage').removeClass('hidden');
			}else{
				$('#alertMessageDiv').html('<i class="fa fa-times"></i> Error in getting advertisement. Please try again later.');
                $('.alertModal').modal('show');
			}
		});
	};
	var getSubCatByCategory = function(categoryId){
		$('#dealSubCategory').html('');
		if(categoryId){
			var url = App.URL().BASE+App.URL().SUB_CATEGORY+'findAllByCategoryId/'+categoryId;
			App.GetRequest(url).then(function(res){
				$('#dealSubCategory').append('<option>-- Select --</option>');
				res.data.forEach(function(obj, i){
					$('#dealSubCategory').append('<option value='+obj.id+'>'+obj.name+'</option>');
				});
			});
		}else{
			$('#dealSubCategory').html('<option>-- Select --</option>');
		}
	};

	var deleteDeal = function(id){
		App.DeleteRequest(URL+id).then(function(res){
			if(res.statusCode == '200'){
				window.location.reload();
			}else{
				$('#alertMessageDiv').html('<i class="fa fa-times"></i> Error in deleting advertisement. Please try again later.');
                $('.alertModal').modal('show');
			}
		});
	};
    $('#confirmDelete').on('show.bs.modal', function (e) {
        var $message = $(e.relatedTarget).attr('data-message');
        $(this).find('.modal-body p').text($message);
        var $title = $(e.relatedTarget).attr('data-title');
        $(this).find('.modal-title').text($title);

        var dealId = $(e.relatedTarget).attr('data-dealid');
        $(this).find('.modal-footer #confirmDeleteBtn').data('dealid', dealId);
        // Pass form reference to modal for submission on yes/ok
//            var form = $(e.relatedTarget).closest('form');
//            $(this).find('.modal-footer #confirm').data('form', form);
    });
    <!-- Form confirm (yes/ok) handler, submits form -->
    $('#confirmDelete').find('.modal-footer #confirmDeleteBtn').on('click', function(){
        //$(this).data('form').submit();
        deleteDeal($(this).data('dealid'));
    });

    var selectDefault = function(id){
		var userId = $('#hiddenUserId').val();
		App.GetRequest(URL+'selectDefault/'+id+'/true/'+userId).then(function(res){
			if(res.statusCode == '200'){
				$('#alertMessageDiv').html('<span class="glyphicon glyphicon-ok-circle" aria-hidden="true"></span> You have updated the default image.');
				//window.location.reload();
			}else{
				$('#alertMessageDiv').html('<i class="fa fa-times"></i> Set default image is failed');
			}
            $('.alertModal').modal('show');
		});
	};
	
	 $('input').iCheck({
		 checkboxClass : 'icheckbox_square-blue',
		 radioClass: 'iradio_minimal-blue',
		 increaseArea : '20%' // optional
	 });
	 
	 $('.iCheck-helper').click(function(ev){
		 console.log(ev);
		 var onclickFn = $(this).offsetParent('[name="isDefault"]').parent().attr('onclick');
		 eval(onclickFn);
	 });
	 
	return {
		create: create,
		deleteDeal: deleteDeal,
		findById: findById,
		selectDefault: selectDefault,
		getSubCatByCategory : getSubCatByCategory
	}
	
})();