function validateEmail(mail)   
{  
 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))  
  {  
    return (true)  
  }  
    alert("Email Invalido")  
    return (false)  
}  

function sendEmail(info){
	
}




function makeToken()
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    for( var i=0; i < 5; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));
    return text;
}

function redirect(){
	window.location="/gestionCasos/home";
}

function codigoTecla(){
    var codigoDeLaTecla = event.which || event.keyCode;    
	if(codigoDeLaTecla == 13){
		existeEmail();
	}
}

function existeEmail() {
	if ($("#enviarEmail").val() != "") {
		if (validateEmail($("#enviarEmail").val())) {			
			var data = '';
			data += '_csrf=' + $('#_csrf').val();
			data += '&email=' + $("#enviarEmail").val();
			$.ajax({
				type : 'POST',
				url : contexto+'/sendEmail/validarEmail',
				data : data,
				dataType : 'json',
				success : function(response) {
					if( response.STATUS == "SUCCESS") {
						alert("Se ha enviado el link de restauraci\u00F3n a su correo electr\u00F3nico ");
						//enviarCorreo(url, $("#enviarEmail").val());
						redirect();
					}
					else{
						alert("Error, el correo no existe");
					}					
				},
				error : function(error){
					console.log(error);
				}
			});
		}
	}
	else{
		alert("Error, el correo no existe, vuelva a ingresarlo");
	}
}

function enviarCorreo(url, email) {
	var data = '_csrf=' + $('#_csrf').val();
	data += "&url=" + encodeURIComponent(url) + "&email=" + email;
	$.ajax({
		type : 'POST',
		url : contexto + '/sendEmail/enviarCorreo',
		data : data,
		dataType : 'json',
		success : function(response) {
			if (response.STATUS == "SUCCESS" &&  response.existe == true) {
				alert("Se envio el correo supuestamente.");
				
			} else {
				
			}					
		},
		error : function(error){
			console.log(error);
		}
	});
}