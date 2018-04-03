// metodo para cargar la fecha de caducidad
function mostrarFechaCaducidad(){
	var diasPosponerCaducidad = diasAdicionarCaducidad?diasAdicionarCaducidad:0;
	if($("#chkSinConteo")[0].checked)
		return;
	fechaCaduci = $("#txtFinFechaDeLosHechos").val();
	fechaInicioHechos = new Date($("#txtFechaDeLosHechos").val());
	fechaFinHechos = new Date($("#txtFinFechaDeLosHechos").val());
	if (fechaInicioHechos <= fechaFinHechos) {
		$("#txtFinFechaDeLosHechos").removeClass("campoTextoError");
		$("#messageErrorFechaFinDelosHechos").hide();
		var fecha = fechaCaduci.split("-");
		var aniosCaducidad = $("#aniosCaducidadParam").val();
		ano = parseInt(fecha[0]) + parseInt(aniosCaducidad);	
		
		var tpmDateCaducidad = new Date(ano,fecha[1]-1,fecha[2]);
		var dateCaducidad = new Date(tpmDateCaducidad.getTime()+(diasPosponerCaducidad*24*60*60*1000)); //Posponemos la fecha de caducidad segun el parametros
		console.log(new Date(ano,fecha[1]-1,fecha[2]));
		
		for(var i=0;i<disabledDates.length;i++){
			if(disabledDates[i] == dateCaducidad.getTime()){
				dateCaducidad.setDate(dateCaducidad.getDate()+1);
				i=0;
			}
		}
		
		/*if ($.inArray(dateCaducidad.getTime(), disabledDates) != -1 ){
			dateCaducidad.setDate(dateCaducidad.getDate()+1);
		}*/
		$("#txtFechaDeCaducidad").val(formatDate(dateCaducidad));
		//console.log(dateCaducidad);
	} else {
		$("#txtFinFechaDeLosHechos").val("");
		$("#txtFechaDeCaducidad").val("");
		$("#messageErrorFechaFinDelosHechos").html("No se puede ingresar una fecha menor a la del inicio del los hechos");
		$("#messageErrorFechaFinDelosHechos").show();
		$("#txtFinFechaDeLosHechos").addClass("campoTextoError");
	}			
}
/**validar fecha inicio de los hechos*/
function validarFechaInicioHechos() {
	fechaInicioHechos = new Date($("#txtFechaDeLosHechos").val());
	fechaFinHechos = new Date($("#txtFinFechaDeLosHechos").val());
	
	if ($("#txtFinFechaDeLosHechos").val()) {
		if (fechaInicioHechos <= fechaFinHechos) {
			desmarcarErrorEnFecha("txtFechaDeLosHechos", "messageErrorFechaFinDelosHechos");	
		} else {
			marcarErrorEnFecha("txtFechaDeLosHechos", "messageErrorFechaFinDelosHechos", 
					"No se puede ingresar una fecha mayor a la del fin del los hechos");
		}
	} else {
		$("#txtFinFechaDeLosHechos").val($("#txtFechaDeLosHechos").val());
	}
}

/**
 * validar fecha no sea mayor o actual a fecha actual
 * */
function validarFechaMenorIgualActual(id) {
	fechaHechos = new Date($("#"+id).val());
	
	if(validarFechaMenOIgual(fechaHechos, obtenerFechaActual(false))){
		desmarcarErrorEnFecha(id, "messageErrorFechaFinDelosHechos");
		return true;
	}else{
		marcarErrorEnFecha(id, "messageErrorFechaFinDelosHechos", 
			"No se puede ingresar una fecha mayor a la fecha actual");
	}
	
	return false;
}

/**
 * compara fecha param1 sea menor o igual a fecha param 2
 */
function validarFechaMenOIgual(fechaMen, fechaMay){
	if(fechaMen <= fechaMay)
		return true;
		
	return false;
}

/**
 * recibe true si se requiere en formato getTime, false sino
 * retorna fecha actual
 */
function obtenerFechaActual(getTime){
	var fechaActual = new Date();
	
	if(getTime){
		if (!fechaActual.now) {
			fechaActual.now = function now() {
				return new Date().getTime();
			};
		}
		
		return fechaActual.now();
	}
	
	return fechaActual;
}

/**
 * mostrar error en fecha
 */
function marcarErrorEnFecha(idFecha, idMensajeFecha, mensaje){
	$("#" + idFecha).val("");
	$("#" + idMensajeFecha).html(mensaje);
	$("#" + idMensajeFecha).show();
	$("#" + idFecha).addClass("campoTextoError");
}

/**
 * recibe id del mensaje fecha a desmarcar y del contenedor para el mensaje de error
 * desmarca error en fecha
 */
function desmarcarErrorEnFecha(idFecha, idMensajeFecha){
	$("#" + idFecha).removeClass("campoTextoError");
	$("#" + idMensajeFecha).hide();	
}


function formatDate(date){
	if(!date)
		return "";
	
   var formattedDate = ('0' + date.getDate()).slice(-2);
   var formattedMonth = ('0' + (date.getMonth() + 1)).slice(-2);
   var formattedYear = date.getFullYear();
   return  formattedYear+ '-' + formattedMonth+ '-' + formattedDate;
}