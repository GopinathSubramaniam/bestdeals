'usr strict';

var Login = (function(){
	
	var doLogin= function(ev){
		var obj = App.serializeObject('loginform');
		var url = App.URL().BASE+App.URL().LOGIN;
		$.ajax({
			url: url,
			method: App.method.POST,
			contentType: App.applicationJson,
			data: JSON.stringify(obj)
		}).done(function(response){
			console.log('Done :::: ', response);
			if(response.statusMsg == 'OK'){
				window.sessionStorage.setItem('username', response.data.name);
				window.sessionStorage.setItem('loginState', response.data.loginState);
				window.sessionStorage.setItem('email', response.data.email);
				window.sessionStorage.setItem('userType', response.data.userType);
				window.sessionStorage.setItem('userId', response.data.id);
				window.location.href='/home';
			}else{
				$('.message').html('<span class="error">Invalid Username and Password</span>').fadeIn(3000).fadeOut(5000);
			}
		}).error(function(error){
			console.log('Error :::: ', error);
			$('.message').html('<span class="error">Invalid Username and Password</span>').fadeIn(3000).fadeOut(5000);
		});
	};
	var  doLogout = function(){
		
		var url = App.URL().BASE+App.URL().LOGIN+'out/'+sessionStorage.getItem('userId');
		$.ajax({
			url: url,
			method: App.method.GET
		}).done(function(response){
			if(response.statusMsg == 'OK'){
				sessionStorage.clear();
				window.location.href = '/logout';
			}
		}).error(function(){
			alert('Error in logout');
		});
	};
	
	return {
		doLogin: doLogin,
		doLogout: doLogout
	}
	
})();