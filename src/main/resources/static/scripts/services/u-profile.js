'use strict';

var UserProfile = (function(){
	var URL = App.URL().BASE+App.URL().USER;
	
	var findUserById = function(userId){
		console.log('UserId :::: ', userId);
		App.GetRequest(URL+userId).then(function(res){
			console.log('findUserById ::: ',res);
			var obj = res.data;
			$('#editId').val(obj.id);
			$('#editName').val(obj.name);
			$('#editEmail').val(obj.email);
			$('#editMobile').val(obj.mobile);
			$('#editUserType').val(obj.userType);
			$('#editPassword').val(obj.password);
		});
	};
	
	var create = function(){
		var userObj = App.serializeObject('userRegisterform');
		App.PostRequest(URL, userObj).then(function(res){
			console.log('User Create ::: ', userObj);
			if(res.statusCode == '500'){
				$('#errorMsg').html(res.message).fadeOut(10000);
			}else{
				window.location.reload();
			}
//			$('#errorMsg').html('<i class="fa fa-times"></i> Error in creating user. Please try again later.').fadeIn().fadeOut(5000);
		});
	};
	
	var update = function(){
		var userObj = App.serializeObject('userInfoEditForm');
		App.PutRequest(URL, userObj).then(function(res){
			console.log('User Create ::: ', userObj);
			if(res.statusCode == '500'){
				$('#errorMsg').html(res.message).fadeOut(10000);
			}else{
				window.location.reload();
			}
		});
	};
	
	var updateDetail = function(){
		var usrDetailUrl = App.URL().BASE+App.URL().USER_DETAIL;
		var userDetailObj = App.serializeObject('userDetailEditForm');
		userDetailObj.user = {id: userDetailObj.userId};
		App.PutRequest(usrDetailUrl, userDetailObj).then(function(res){
			console.log('User Create ::: ', userDetailObj);
			if(res.statusCode == '500'){
				$('#errorMsg').html(res.message).fadeOut(10000);
			}else{
				window.location.reload();
			}
		});
	};
	
	var deleteUser = function(userId){
		App.DeleteRequest(URL+userId).then(function(res){
			window.location.reload();
		});
	};
	
	var displayUserInfoEditBtn = function(){
		var userInfoHeadDiv = $('.userInfoHeadDiv');
		var userInfoDiv = $('.userInfoDiv');
		var userFormElem = $('#userInfoEditForm');
		
		if(userFormElem.hasClass('hidden')){
			userFormElem.removeClass('hidden');
			userInfoHeadDiv.addClass('hidden');
			userInfoDiv.addClass('hidden');
		}else{
			userFormElem.addClass('hidden');
			userInfoHeadDiv.removeClass('hidden');
			userInfoDiv.removeClass('hidden');
		}
	};
	
	var displayUserDetailEditBtn = function(){
		var userDetailHeadDiv = $('.userDetailHeadDiv');
		var userDetailEditDiv = $('.userDetailDiv');
		var userDetailEditFormElem = $('#userDetailEditForm');
		
		if(userDetailEditFormElem.hasClass('hidden')){
			userDetailEditFormElem.removeClass('hidden');
			userDetailHeadDiv.addClass('hidden');
			userDetailEditDiv.addClass('hidden');
		}else{
			userDetailEditFormElem.addClass('hidden');
			userDetailHeadDiv.removeClass('hidden');
			userDetailEditDiv.removeClass('hidden');
		}
	};
	
	return {
		create: create,
		update: update,
		updateDetail: updateDetail,
		findUserById: findUserById,
		deleteUser: deleteUser,
		displayUserInfoEditBtn: displayUserInfoEditBtn,
		displayUserDetailEditBtn: displayUserDetailEditBtn
	}
	
})();

function initMap() {
	var lat = $('#latitude').val();
	var lng = $('#longitude').val();
	if(lat && lng){
		/*var uluru = {lat: parseFloat(lat), lng: parseFloat(lng)};
		var map = new google.maps.Map(document.getElementById('map'), {
			zoom: 18,
			center: uluru
		});
		*/
	} else {
	// Set defalt to "Pune"
	    lat = 18.5204;
	    lng = 73.8567;
	}

	var map = new google.maps.Map(document.getElementById('map'), {
      center: new google.maps.LatLng(lat, lng),
      zoom: 13,
      mapTypeId: google.maps.MapTypeId.ROADMAP //'roadmap'
    });
    placeMarker({'lat': parseFloat(lat), 'lng':parseFloat(lng)}, map);
    //google.maps.event.addListener(map,'click',function(event) {
    map.addListener('click',function(event) {
        //document.getElementById('latlongclicked').value = event.latLng.lat() + ', ' + event.latLng.lng();
        $('#latitude').val(event.latLng.lat());
        $('#longitude').val(event.latLng.lng());
        placeMarker(event.latLng, map);
        //map.setCenter(marker.getPosition());
    });
    //google.maps.event.addListener(map,'mousemove',function(event) { document.getElementById('latspan').innerHTML = event.latLng.lat() document.getElementById('lngspan').innerHTML = event.latLng.lng() document.getElementById('latlong').innerHTML = event.latLng.lat() + ', ' + event.latLng.lng() });

    // Create the search box and link it to the UI element.
    var input = document.getElementById('pac-input');
    var searchBox = new google.maps.places.SearchBox(input);
    map.controls[google.maps.ControlPosition.TOP_LEFT].push(input);

    // Bias the SearchBox results towards current map's viewport.
    map.addListener('bounds_changed', function() {
      searchBox.setBounds(map.getBounds());
    });

    var markers = [];
    // Listen for the event fired when the user selects a prediction and retrieve
    // more details for that place.
    searchBox.addListener('places_changed', function() {
      if (marker) {
          marker.setMap(null);
      }
      var places = searchBox.getPlaces();

      if (places.length == 0) {
        return;
      }

      // Clear out the old markers.
      markers.forEach(function(marker) {
        marker.setMap(null);
      });
      markers = [];

      // For each place, get the icon, name and location.
      var bounds = new google.maps.LatLngBounds();
      places.forEach(function(place) {
        if (!place.geometry) {
          console.log("Returned place contains no geometry");
          return;
        }
        var icon = {
          url: place.icon,
          size: new google.maps.Size(71, 71),
          origin: new google.maps.Point(0, 0),
          anchor: new google.maps.Point(17, 34),
          scaledSize: new google.maps.Size(25, 25)
        };

        // Create a marker for each place.
        markers.push(new google.maps.Marker({
          map: map,
          icon: icon,
          title: place.name,
          position: place.geometry.location
        }));

        if (place.geometry.viewport) {
          // Only geocodes have viewport.
          bounds.union(place.geometry.viewport);
        } else {
          bounds.extend(place.geometry.location);
        }
      });
      map.fitBounds(bounds);
    });
     infowindow = new google.maps.InfoWindow({content: "LatLng Applied" })
}

var marker;
var infowindow;
function placeMarker(location, map) {
    if (marker) {
        marker.setMap(null);
    }
    marker = new google.maps.Marker({
        position: location,
        map: map
    });
    marker.addListener('click', function() {
      infowindow.open(map, marker);
    });
    map.panTo(location);
}