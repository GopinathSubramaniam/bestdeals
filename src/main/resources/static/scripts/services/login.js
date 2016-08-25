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
				window.location.href='/home';
			}else{
				$('.message').html('<span class="error">Invalid Username and Password</span>');
			}
		}).error(function(error){
			console.log('Error :::: ', error);
			window.location.href='/home';
		});
	}
	
	return {
		doLogin: doLogin
	}
	
})();