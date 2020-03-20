$(document).ready(() => {

	$('#login').submit((event) => {
		event.preventDefault();
		let username = $('input[name="username"]').val();
		let password = $('input[name="password"]').val();
		console.log('username', username);
		console.log('password', password);
		$.post({
				url: 'rest/login',
				data: JSON.stringify({username, password}),
				contentType: 'application/json',
				success: function(user) {

					console.log(user);
					
							if(user!=null)
							{
							 $('#odjava').attr('hidden', false); 

							 	if(user.uloga==1)
							 	{
							 		window.location='./novi.html';

							 	}else if(user.uloga==0 || user.uloga==2)
							 	{
							 		window.location='./novi.html';

							 	}else if(user.uloga==3)
							 	{
							 		window.location='./novi.html';
							 	}

							}else
							{
								$('#prijava').attr('hidden', false); 
							}

					
				},
				error: function() {
					$('#error').text("Greska, pogresno ime ili sifra");
					$("#error").show().delay(3000).fadeOut();
				}
			})
		})
})
