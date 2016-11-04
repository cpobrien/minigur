function registerUser() {
	var username = $('.login-register-form').find('#username').val();
	var password = $('.login-register-form').find('#password').val();
	if (username === null || username === '') {
		alert('Please enter a username.');
	} else if (password === null || password === '') {
		alert('Please enter a password.');
	} else {
		$.ajax({
		   url: '/user/',
		   type: 'POST',
		   data: {
		      username: username,
		      password: password
		   },
		   success: function(data) {
		     
		   },
		   error: function() {
		      
		   },
		});
	}
}

function loginUser(){
	
}