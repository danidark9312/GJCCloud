function checkPass()
{
    //Store the password field objects into variables ...
    var pass1 = document.getElementById('nuevaContrasenia');
    var pass2 = document.getElementById('confirmarcontrasenia');
    //Store the Confimation Message Object ...
    var message = document.getElementById('confirmMessage');
    //Set the colors we will be using ...
    var goodColor = "#66cc66";
    var badColor = "#ff6666";
    //Compare the values in the password field 
    //and the confirmation field
    if(pass1.value == pass2.value){
        //The passwords match. 
        //Set the color to the good color and inform
        //the user that they have entered the correct password 
        pass2.style.backgroundColor = goodColor;
        message.style.color = goodColor;
        message.innerHTML = "Las contraseñas coinciden"
    }else{
        //The passwords do not match.
        //Set the color to the bad color and
        //notify the user.
        pass2.style.backgroundColor = badColor;
        message.style.color = badColor;
        message.innerHTML = "No coinciden las contraseñas"
    }
}

function codigoTecla(){
    var codigoDeLaTecla = event.which || event.keyCode;    
	if(codigoDeLaTecla == 13){
		cambiarContrasenia();
	}
}

function cambiarContrasenia() {
	if ($("#nuevaContrasenia").val() == $("#confirmarcontrasenia").val()) {
			var data = '';
			data += '_csrf=' + $('#_csrf').val();
			data += '&nuevaContrasenia=' + $("#nuevaContrasenia").val();
			data += '&confirmarcontrasenia=' + $("#confirmarcontrasenia").val();
			data += '&changePasswordPK.token=' + $("#token").val();
			data += '&changePasswordPK.email=' + $("#email").val();
			
			var url=contexto+'/cambiarContrasenia/cambiarPassword';
			
			$.ajax({
				type : 'POST',
				url : contexto+'/cambiarContrasenia/cambiarPassword',
				data : data,
				dataType : 'json',
				success : function(response) {
					if( response.STATUS == "SUCCESS") {
						window.location.href = contexto+'/login';
					}else{
						alert("Error");
					}					
				},
				error : function(error){
					console.log(error);
				}
			});		
	}
}