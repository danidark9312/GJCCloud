var session = new session();
var Contrasena = new Contrasena();
var resetDeVentana;
var resetDeSalir;
var idSetInterval = null;
window.onclick = session.cronoreset;
window.onkeydown = session.cronoreset;
window.onkeypress = session.cronoreset;

/**
 * Constante para que otros enviroments puedan preguntar si ya se cargo este js
 */
JSSESSION = true;

function session() {
	
	var tiempoSesion = 10;  				// Tiempo de duracion de sesion en minutos
	var tiempoHastaCierre = 7;  		// Tiempo antes de cierre para mostrar anuncio de tiempo de sesion restante.
	var minuteInMillis = 60000;


	//Metodo que indica en cuantos minutos se cerrara la sesion 
	var tiempo = function(dato) {
		dato1 = (dato * minuteInMillis) - (minuteInMillis * 1 * tiempoHastaCierre);
		resetDeVentana = setTimeout(abreVentana, dato1);
		resetDeSalir = setTimeout(salir, (dato * minuteInMillis));
	};
	
	


	var cronoreset = function() {
		resetTime(tiempoSesion);
	};

	var resetTime = function(dato) {
		clearTimeout(resetDeVentana);
		resetDeVentana = null;
		clearTimeout(resetDeSalir);
		resetDeSalir = null;
		tiempo(dato);
	};

	var abreVentana = function() {
		startTimer((tiempoHastaCierre * 60), $("#modal-finalizacionSesion #finalizaEn"))
		$("#modal-finalizacionSesion").modal("show");
	};

	var salir = function() {
		document.location.href = "j_spring_security_logout";
	};
	
	var startTimer = function(duration, display) {
		
		if (idSetInterval)
			clearInterval(idSetInterval);
		
	    var start = Date.now(),
	        diff,
	        minutes,
	        seconds;
	    function timer() {
	        // get the number of seconds that have elapsed since 
	        // startTimer() was called
	        diff = duration - (((Date.now() - start) / 1000) | 0);

	        // does the same job as parseInt truncates the float
	        minutes = (diff / 60) | 0;
	        seconds = (diff % 60) | 0;

	        minutes = minutes < 10 ? "0" + minutes : minutes;
	        seconds = seconds < 10 ? "0" + seconds : seconds;

	        display.text(minutes + ":" + seconds); 

	        if (diff <= 0) {
	            // add one second so that the count down starts at the full duration
	            // example 05:00 not 04:59
	            start = Date.now() + 1000;
	        }
	    };
	    // we don't want to wait a full second before the timer starts
	    timer();
	    idSetInterval = setInterval(timer, 1000);
	};
	
	return {
		tiempo : tiempo,
		cronoreset : cronoreset,
		tiempoSesion: tiempoSesion,
		tiempoHastaCierre: tiempoHastaCierre
	};
}



function Contrasena() {
	/**
	 * Variables privadas
	 */
	BTNCAMBIARCONTRASENA = '#cambiarContrasena';
	__csrf = $('#_csrf').val();
	DIVMSJERROR = '#errorCambioContrasena';
	DIVMSJEXITO = '#exitoCambioContrasena';
	INPUTCONTRASENAANTERIOR = '#contrasenaAnterior';
	INPUTCONTRASENACONFIRMACION = '#contrasenaConfirmacion';
	INPUTCONTRASENANUEVA = '#contrasenaNueva';
	LINKCIERRESESION = 'j_spring_security_logout';
	FRMCONTRASENA = '#frmContrasena';
	TAMANOMINIMOCONTRASENA = 6;
	MSJSERRORES = {
			CONTRASENAANTERIORNUEVA: 'Contrase\u00F1a Anterior incorrecta.',
			CONTRASENASNOCOINCIDEN: 'Las contrase\u00F1as no coinciden.',
			CONTRASENATAMANO: 'El tama\u00F1o m\u00EDnimo de la contrase\u00F1a debe ser de: ' + TAMANOMINIMOCONTRASENA 
	};
	MSJERRORCAMBIOCONTRASENA = '#msjErrorCambioContrasena';
	MSJEXITOCAMBIOCONTRASENA = '#msjExitoCambioContrasena';
	MSJNOCOINCIDEN = 'Las contraseñas no coinciden.';
	MODALCONTRASENA = '#modalCambioContrasena';
	TIEMPOREDIRECCION = 3500;
	
	/**
	 * Metodos privados
	 */
	var _cambiarContrasena = function(){
		if(_validarContrasenaIgual()) {
			if (_validarCampos()) {
				if (_validarContrasenaNuevaIgualAnterior()) {
					_guardarContrasena();
				} else {					
					_mostrarMsjError(MSJSERRORES.CONTRASENAANTERIORNUEVA);

				}
			} else {
				
				if($(INPUTCONTRASENACONFIRMACION).val() < 6 === $(INPUTCONTRASENANUEVA).val() < 6){
					_mostrarMsjError(MSJSERRORES.CONTRASENATAMANO);
				}				
				else{
				_mostrarMsjError(MSJSERRORES.CONTRASENAANTERIORNUEVA);
				}								
			}
		}else{					
			_mostrarMsjError(MSJSERRORES.CONTRASENASNOCOINCIDEN);
		}
	};
	var _cerrarSesion = function(){
		window.location.href = LINKCIERRESESION;
	};
	var _guardarContrasena = function(){
		var data = {};

		data = [{
			name : 'contrasenaAnterior',
			value : $(INPUTCONTRASENAANTERIOR).val()
		}, {
			name : 'contrasenaNueva',
			value : $(INPUTCONTRASENANUEVA).val()
		}];

		waitingDialog.show('');
		$.ajax({
			data : data,
			type : 'POST',
			url : contexto + "/maestroUsuarios/cambiarContrasena" + '?_csrf=' + __csrf,
			success : function(res){
				waitingDialog.hide();
				res = JSON.parse(res);
				if(res.STATUS == 'SUCCESS'){
					$(MSJEXITOCAMBIOCONTRASENA).html("Se guardó la contraseña exitosamente.")
					$(DIVMSJEXITO).show();
					setTimeout(function(){
						_limpiarModal();
					}, TIEMPOREDIRECCION - 100);
					setTimeout(function(){
						_cerrarSesion();
					}, TIEMPOREDIRECCION);
				}else{
					$(DIVMSJERROR).html("No se pudo guardar la contraseña.")
					$(DIVMSJERROR).show();
					setTimeout(function(){
						_limpiarModal();
					}, TIEMPOREDIRECCION);
					$(MODALCONTRASENA).modal("hide");
				}
			},
			error : function(e){
				waitingDialog.hide();
				$(DIVMSJERROR).html("Hubo un error de red durante la transacción.")
				$(DIVMSJERROR).show();
				setTimeout(function(){
					_limpiarModal();
				}, TIEMPOREDIRECCION);
				$(MODALCONTRASENA).modal("hide");
			}
		});
	};
	var _limpiarModal = function(){
		$(DIVMSJERROR).hide();
		$(DIVMSJEXITO).hide();
		$(INPUTCONTRASENAANTERIOR).val('');
		$(INPUTCONTRASENACONFIRMACION).val('');
		$(INPUTCONTRASENANUEVA).val('');
	};
	var _mostrarMsjError = function(msj){
		$(MSJERRORCAMBIOCONTRASENA).html(msj);		
		$(DIVMSJERROR).show();
	};
	/**
	 * @Return true Si el formulario esta correcto (no vacias, tamano minimo,
	 *         contrasena nueva y anterior igual)
	 */
	var _validarCampos = function() {
		valido = true;

		camposValidar = [INPUTCONTRASENACONFIRMACION, INPUTCONTRASENANUEVA];

		$.each(camposValidar, function(index, campoValidar){
			valido = $(campoValidar).val().length < TAMANOMINIMOCONTRASENA ? false : valido;			
		});
//		valido = !_validarContrasenaNuevaIgualAnterior() ? false : valido;
		
		return valido;
	};
	/**
	 * @Return true Si las contrasenas son iguales
	 */
	var _validarContrasenaIgual = function(){
		if ($(INPUTCONTRASENACONFIRMACION).val() === $(INPUTCONTRASENANUEVA).val())
				 return true;
		return false;
	};
	/**
	 * @Return true Si no son iguales
	 */
	var _validarContrasenaNuevaIgualAnterior = function(){
		if($(INPUTCONTRASENAANTERIOR).val() === $(INPUTCONTRASENANUEVA).val()) return false;
		return true;
	};

	/**
	 * Metodos publicos
	 */

	var abrirModal = function(){
		_limpiarModal();
		$(MODALCONTRASENA).modal('show');
	};
	var cambiarContrasena = function(){
		_cambiarContrasena();
	};
	var cerrarModal = function(){
		_limpiarModal();
		$(MODALCONTRASENA).modal('hide');
	};
	var confirmarCambioContrasena = function(){
		return false;
	};
	var mostrarErrorCambioContrasena = function(){
		divError = $('#errorCambioContrasena');
	};
	var validarContrasena = function(confirmacionContransena, contrasena){
		return false;
	};

	return {
		abrirModal : abrirModal,
		cambiarContrasena : cambiarContrasena,
		cerrarModal : cerrarModal,
		confirmarCambioContrasena : confirmarCambioContrasena,
		mostrarErrorCambioContrasena : mostrarErrorCambioContrasena,
		validarContrasena : validarContrasena
	};
}

