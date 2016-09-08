'usr strict';

var Login = (function(){
	var URL = App.URL().BASE+App.URL().LOGIN;
	
	var doLogin= function(ev){
		var obj = App.serializeObject('loginform');
		App.PostRequest(URL, obj).then(function(response){
			if(response.statusMsg == 'OK'){
				window.sessionStorage.setItem('username', response.data.name);
				window.sessionStorage.setItem('loginState', response.data.loginState);
				window.sessionStorage.setItem('email', response.data.email);
				window.sessionStorage.setItem('userType', response.data.userType);
				window.sessionStorage.setItem('userId', response.data.id);
				if(response.data.userType == 'ADMIN'){
					window.location.href='home';
				}else{
					window.location.href='greetings';
				}
			}else{
				$('.message').html('<span class="error">Invalid Username and Password</span>').fadeIn(3000).fadeOut(5000);
			}
		});
	};
	var  doLogout = function(){
		var getUrl = URL+'out/'+sessionStorage.getItem('userId');
		App.GetRequest(getUrl).then(function(response){
			if(response.statusMsg == 'OK'){
				sessionStorage.clear();
				window.location.href = 'logout';
			}else{
				alert('Error in logout');
			}
		});
	};
	
	return {
		doLogin: doLogin,
		doLogout: doLogout
	}
	
})();