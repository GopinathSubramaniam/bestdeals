'use strict';

var App = (function(){

	var serializeObject = function(formName){
		var arrayObj = $('#'+formName).serializeArray();
		var obj = new Object();
		arrayObj.forEach(function(val, i){
			obj[val.name] = val.value;
		});
		return obj;
	};
	var URL = function(){
		var obj = new Object();
		obj.BASE = window.location.protocol + '//' + window.location.hostname + ':' + window.location.port + '/BestDeals';
		obj.LOGIN = '/rest/login/';
		obj.USER = '/rest/user/';
		obj.USER_DETAIL = '/rest/userDetail/';
		obj.SALE = '/rest/salesman/';
		obj.PLAN = '/rest/plan/';
		obj.DEAL = '/rest/deal/';
		obj.CATEGORY = '/rest/category/';
		obj.SUB_CATEGORY = '/rest/subcat/';
		obj.APP = '/rest/base/';
		return obj;
	};
	var PostRequest = function(url, data){
		var defer = $.Deferred();
		$.ajax({
			url: url,
			method: App.method.POST,
			contentType: 'application/json',
			data: JSON.stringify(data)
		}).done(function(response){
			console.log('PostRequest Done :::: ', response);
			defer.resolve(response);
		}).error(function(error){
			console.log('PostRequest Error :::: ', error);
			defer.resolve(error);
		});
		return defer.promise();
	};
	var PostFormData = function(url, data){
		var defer = $.Deferred();
		$.ajax({
			url: url,
			method: App.method.POST,
			processData: false,
			contentType: false,
			data: data
		}).done(function(response){
			console.log('PostRequest Done :::: ', response);
			defer.resolve(response);
		}).error(function(error){
			console.log('PostRequest Error :::: ', error);
			defer.resolve(error);
		});
		return defer.promise();
	};
	var GetRequest = function(url){
		var defer = $.Deferred();
		$.ajax({
			url: url,
			method: App.method.GET,
			dataType: 'json'
		}).done(function(response){
			console.log('GetRequest Done :::: ', response);
			defer.resolve(response);
		}).error(function(error){
			console.log('GetRequest Error :::: ', error);
			defer.resolve(error);
		});
		return defer.promise();
	};
	var PutRequest = function(url, data){
		var defer = $.Deferred();
		$.ajax({
			url: url,
			method: App.method.PUT,
			contentType: 'application/json',
			data: JSON.stringify(data)
		}).done(function(response){
			console.log('PutRequest Done :::: ', response);
			defer.resolve(response);
		}).error(function(error){
			console.log('PutRequest Error :::: ', error);
			defer.resolve(error);
		});
		return defer.promise();
	};
	
	var DeleteRequest = function(url){
		var defer = $.Deferred();
		$.ajax({
			url: url,
			method: App.method.DELETE
		}).done(function(response){
			console.log('DeleteRequest Done :::: ', response);
			defer.resolve(response);
		}).error(function(error){
			console.log('DeleteRequest Error :::: ', error);
			var errObj = error.responseText != null && error.responseText != '' ? JSON.parse(error.responseText) : {status: 500};
			defer.resolve(errObj);
		});
		return defer.promise();
	};
	
	var getMessage = function(res){
		 return (res.responseJSON != undefined ? res.responseJSON.error : res.message);
	};
	var isAuthenticated = function(){
		var auth = {status: false, message: 'User is authenticated'};
		if(sessionStorage.getItem('userId') !='' && sessionStorage.getItem('userId') != null){
			auth.status = true;
			auth.message = 'User is not authenticated';
		} 
		return auth;
	};
	var findAllCityByState = function(stateId){
		$('#inputCity').html('');
		if(stateId){
			var url = App.URL().BASE+App.URL().APP+'findAllCityByState/'+stateId;
			GetRequest(url).then(function(res){
				res.data.forEach(function(obj, i){
					$('#inputCity').append('<option value='+obj.id+'>'+obj.name+'</option>');
				});
			});
		}else{
			$('#inputCity').html('<option>-- Select --</option>');
		}
	};
	
	var findAllSubCateByState = function(categoryId){
		$('#inputSubCategory').html('');
		if(categoryId){
			var url = App.URL().BASE+App.URL().SUB_CATEGORY+'findAllByCategoryId/'+categoryId;
			GetRequest(url).then(function(res){
				$('#inputSubCategory').append('<option>-- Select --</option>');
				res.data.forEach(function(obj, i){
					$('#inputSubCategory').append('<option value='+obj.id+'>'+obj.name+'</option>');
				});
			});
		}else{
			$('#inputSubCategory').html('<option>-- Select --</option>');
		}
	};
	var getAdvRules = function(){
		var advRules = sessionStorage.getItem('planRule');
		if(advRules !== null){
			advRules = JSON.parse(advRules);
		}
		return advRules;
	};
	
	var displayAdvTab = function(){
		var advRules = getAdvRules();
		if(advRules !== null && advRules.max_adv_count == 0){
			$('.sidebar .advertisement').parent().addClass('hidden');
		}
	};
	
	var bindValidation = function(formId, fields, fn){
		$(function(){
			$('#'+formId).bootstrapValidator({
				message: 'Please enter a value',
				feedbackIcons : {
					valid : 'glyphicon glyphicon-ok',
					invalid : 'glyphicon glyphicon-remove',
					validating : 'glyphicon glyphicon-refresh'
				},
				excluded: [':disabled'],
				fields: fields
			}).on('success.form.bv', function(e){
				e.preventDefault();
				var $form = $(e.target);
				var bv = $form.data('bootstrapValidator');
				fn();
			});
		});
	};
	var validateRules = {
		name : {
			validators : {
				notEmpty : {
					mesage : 'The name is required and can\'t be empty'
				},
				stringLength : {
					min : 4,
					message : 'The name must be more than 4 characters'
				}
			}
		},
		email : {
			validators : {
				notEmpty : {
					message : 'The email address is required and can\'t be empty'
				},
				emailAddress : {
					message : 'The input is not a valid email address'
				}
			}
		},
		mobile : {
			validators : {
				notEmpty : {
					message : 'The mobile is required and can\'t be empty'
				},
				regexp : {
					regexp : /^[0-9]+$/,
					message : 'The mobile can have only number '
				}
			}
		},
		password : {
			validators : {
				notEmpty : {
					message : 'The password is required'
				}
			}
		},
		dropdown: {
			validators : {
				notEmpty : {
					message : 'The value is required'
				}
			}
		},
		confirmPassword: {
            validators: {
                notEmpty: {
                    message: 'The confirm password is required and can\'t be empty'
                },
                identical: {
                    field: 'password',
                    message: 'The password and its confirm are not the same'
                }
            }
        }
	};
	
	var getLatitudeLongitude = function (address, callback) {
	    address = address || 'Pune';
	    if (google) {
            var geocoder = new google.maps.Geocoder();
	        geocoder.geocode({
	            'address': address
	        }, function (results, status) {
	            if (status == google.maps.GeocoderStatus.OK) {
	                callback(results[0]);
	            } else {
                    callback(undefined);
				}
	        });
	    } else {
            callback(undefined);
		}
	};
	
	var findAllCitiesByStateId = function(stateId){
		App.GetRequest(URL+'findAllCitiesByState/'+stateId).then(function(res){
			var dropdownHtml = '';
			var cities = res.data;
			cities.forEach(function(city, i){
				dropdownHtml += '<option value="'+city.id+'">'+city.name+'</option>';
			});
			$('#inputCity').html(dropdownHtml);
		});
	};
	
	var findAllTalukasByCityId = function(cityId){
		App.GetRequest(URL+'findAllTalukasByCityId/'+cityId).then(function(res){
			var dropdownHtml = '';
			var talukas = res.data;
			talukas.forEach(function(taluka, i){
				dropdownHtml += '<option value="'+taluka.id+'">'+taluka.name+'</option>';
			});
			$('#inputTaluka').html(dropdownHtml);
		});
	};
	
	var findAllVillagesByTalukaId = function(talukaId){
		App.GetRequest(URL+'findAllVillagesByTalukaId/'+talukaId).then(function(res){
			var dropdownHtml = '';
			var villages = res.data;
			villages.forEach(function(village, i){
				dropdownHtml += '<option value="'+village.id+'">'+village.name+'</option>';
			});
			$('#inputVillage').html(dropdownHtml);
		});
	};
    /*$(document).ready(function(){
        $(".sidebar-toggle").click(function(){
            $("body").toggleClass("sidebar-collapse");
        });
    });*/
    //$(window).resize(function() {
        if ($(window).width() < 960) {
            $(".sidebar-toggle").click(function(){
                $("body").toggleClass("sidebar-open");
            });
        } else {
            $(".sidebar-toggle").click(function(){
                $("body").toggleClass("sidebar-collapse");
            });
        }
    //});
	// START
	var userName = sessionStorage.getItem('username');
	if(userName) $('#username').html(sessionStorage.getItem('username'));

	$('.sidebar-menu li').click(function(ev){
		var elems = $('.sidebar-menu li');
		$.each(elems, function(i, elem){
			elem.className = '';
		});
		ev.currentTarget.className = 'active';
	});
	
	displayAdvTab(); // Hide advertisement tab for non admin ppl
	
	 $('body').on('hidden.bs.modal', '.modal', function () {
	 	if ($(this).find('form').length > 0 ) {
			$(this).find('form')[0].reset();
		}
      });
	 
	// END
	
	return {
		URL: URL,
		method: {GET:'GET', POST: 'POST', PUT: 'PUT', DELETE: 'DELETE'},
		serializeObject: serializeObject,
		getMessage: getMessage,
		PostRequest: PostRequest,
		PostFormData: PostFormData,
		PutRequest: PutRequest,
		GetRequest: GetRequest,
		DeleteRequest: DeleteRequest,
		isAuthenticated: isAuthenticated,
		findAllCityByState: findAllCityByState,
		findAllSubCateByState: findAllSubCateByState,
		getAdvRules: getAdvRules,
		displayAdvTab: displayAdvTab,
		bindValidation: bindValidation,
		validateRules: validateRules,
		getLatitudeLongitude: getLatitudeLongitude,
		findAllCitiesByStateId: findAllCitiesByStateId,
		findAllTalukasByCityId: findAllTalukasByCityId,
		findAllVillagesByTalukaId: findAllVillagesByTalukaId
		
	};
})();

/*function calculateAndDisplayRoute(directionsService, directionsDisplay) {
	 directionsService.route({
		 origin: document.getElementById('start').value,
		 destination: document.getElementById('end').value,
		 travelMode: google.maps.TravelMode.DRIVING
	 }, function(response, status) {
		 if (status === google.maps.DirectionsStatus.OK) {
			 directionsDisplay.setDirections(response);
			 console.log('Distance : ', response.routes[0].legs[0].distance.text);
		 } else {
			 window.alert('Directions request failed due to ' + status);
		 }
	 });
}
*/
(function($){
    $.fn.serializeObject = function(){

        var self = this,
            json = {},
            push_counters = {},
            patterns = {
                "validate": /^[a-zA-Z][a-zA-Z0-9_]*(?:\[(?:\d*|[a-zA-Z0-9_]+)\])*$/,
                "key":      /[a-zA-Z0-9_]+|(?=\[\])/g,
                "push":     /^$/,
                "fixed":    /^\d+$/,
                "named":    /^[a-zA-Z0-9_]+$/
            };


        this.build = function(base, key, value){
            base[key] = value;
            return base;
        };

        this.push_counter = function(key){
            if(push_counters[key] === undefined){
                push_counters[key] = 0;
            }
            return push_counters[key]++;
        };

        $.each($(this).serializeArray(), function(){

            // skip invalid keys
            if(!patterns.validate.test(this.name)){
                return;
            }

            var k,
                keys = this.name.match(patterns.key),
                merge = this.value,
                reverse_key = this.name;

            while((k = keys.pop()) !== undefined){

                // adjust reverse_key
                reverse_key = reverse_key.replace(new RegExp("\\[" + k + "\\]$"), '');

                // push
                if(k.match(patterns.push)){
                    merge = self.build([], self.push_counter(reverse_key), merge);
                }

                // fixed
                else if(k.match(patterns.fixed)){
                    merge = self.build([], k, merge);
                }

                // named
                else if(k.match(patterns.named)){
                    merge = self.build({}, k, merge);
                }
            }

            json = $.extend(true, json, merge);
        });

        return json;
    };
})(jQuery);