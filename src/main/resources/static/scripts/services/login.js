'usr strict';

var Login = (function(){
	var URL = App.URL().BASE+App.URL().LOGIN;
	
	var doLogin= function(ev){
		var obj = App.serializeObject('loginform');
		App.PostRequest(URL, obj).then(function(response){
			if(response.statusMsg == 'OK'){
				var obj = response.data;
				var userType = obj.userType;
				if(userType == 'MERCHANT' && obj.plan != null){
					window.sessionStorage.setItem('planId', obj.plan.id);
					window.sessionStorage.setItem('planRule', obj.plan.rules);
				}
				
				window.sessionStorage.setItem('username', obj.name);
				window.sessionStorage.setItem('loginState', obj.loginState);
				window.sessionStorage.setItem('email', obj.email);
				window.sessionStorage.setItem('userType', userType);
				window.sessionStorage.setItem('userId', obj.id);
				if(userType == 'ADMIN'){
					window.sessionStorage.setItem('isAdmin', true);
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
	
	var doLogoutForUser = function(){
		var getUrl = URL+'out/'+sessionStorage.getItem('userId');
		App.GetRequest(getUrl).then(function(response){
			if(response.statusMsg == 'OK'){
				sessionStorage.clear();
				window.location.href = 'out';
			}else{
				alert('Error in logout');
			}
		});
	};
	
	
	return {
		doLogin: doLogin,
		doLogout: doLogout,
		doLogoutForUser: doLogoutForUser
	}
	
})();