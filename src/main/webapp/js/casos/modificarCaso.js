var formularioModificarMiembros = null;
var formularioModificarVictimaDemandado = null;
var formularioModificarAbogado = null;
var formularioRadicado = null;
var codigoEquipoCasoAnt = null;
var codigosTelefonoEliminado = [];
var codigosCelularEliminado = [];
var codigosCorreosEliminado = [];
var codigoTipoMiembro = null;
var codigoEquipoCaso = null;
var justificacionesFechaVencmimento = [];
var codigoActividadAModificar = null;
var limpiarFormularioRadicado = false;
var almacenarTipoCaso = "";
var ESACTIVIDADPROPIASI = "S";
var ESTAREAPROPIASI = "S";
var CAMPOFECHALIMITE = "Fecha limite";
var TIPOMIEMBROABOGADO = 5;
var TIPOMIEMBROOTRO = 4;
var CODIGO_ACTIVIDAD_VENCIDA = "V";
var CODIGO_ACTIVIDAD_COMPLETA = "S";
var CODIGO_ACTIVIDAD_PENDIENTE = "N";
var DESC_ACTIVIDAD_VENCIDA = "Vencida";
var DESC_ACTIVIDAD_COMPLETA = "Completada";
var DESC_ACTIVIDAD_PENDIENTE = "Pendiente";
var mensajeCorreo = null;
var fechaActualizada = null;
var fechaInicialAnterior = null;
var fechaFinalAnterior = null;
var fechaCaducidadAnterior = null;
var actividadesArray = [];

function modificarEstadoCaso() {
	$("#modal-modificarEstadoCaso").hide();
	var data_caso = "_csrf=" + $("#_csrf").val();
	data_caso += "&codigo=" + $("#codigoParam").val();
	data_caso += "&estadoCaso.codigo=" + $("#estadoCasoMod").val();
	data_caso += "&usuarioUltimaModificacion=" + $("#idusercreacion").val();

	// justificacion
	data_caso += "&tipoAccion=" + "Modificacion";
	data_caso += "&campoModificado=" + "EstadoCaso";
	data_caso += "&usuarioModificacion=" + $("#idusercreacion").val();
	data_caso += "&informacionEliminada=" + $("#procesoEstado").text();
	data_caso += "&justificacion=" + $("#txtJustificacion").val();
	data_caso += "&codigocaso.codigo=" + $("#codigoParam").val();

	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/caso/guardarEstadoCaso",
		// data : $("#idFormulario").serialize(),
		data : data_caso,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			$("#procesoEstado").text($("#estadoCasoMod option:selected").text());
			$("#procesoEstado").text($("#estadoCasoMod option:selected").text());
			if(response.STATUS == "SUCCESS"){
				$("#modal-modificarEstadoCaso").modal("hide");
				$("#messageExitoso").html("Se modific\u00f3 el estado exitosamente.");
				$("#messageExitoso").show();
				setTimeout("limpiarMensajeExito();", 10000);
				limpiarFormularioEstadoCaso();
			}else{
				$("#messageError").html("No fue posible actualizar el estado del caso.");
				$("#messageError").show();
				setTimeout("limpiarMensajeError();", 10000);
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});

}

function limpiarMensajeError(){
	$("#messageError").hide();
}

function limpiarMensajeExito(){
	$("#messageExitoso").hide();
}

function validarFormularioEstadoCaso(){
	ocultarMensajesErrorDetalleCaso();
	limpiarErroresEstadoCaso();
	var erroresEstadoCaso = [];
	var isError = false;

	if(EstaVacio($("#estadoCasoMod").val())){
		$("#estadoCasoMod").addClass("campoTextoError");
		isError = true;
		erroresEstadoCaso.push("Se debe seleccionar un estado.");
	}
	if(EstaVacio($("#txtJustificacion").val())){
		$("#txtJustificacion").addClass("campoTextoError");
		isError = true;
		erroresEstadoCaso.push("Se debe ingresar una justificaci\u00f3n");

	}

	if(isError){
		var objectErrores = new Object();
		objectErrores.erroresEstadoCaso = erroresEstadoCaso;
		mostrarMensajesErrorDetalleCaso(objectErrores);
		return false;
	}else{
		return true;
	}

}

function limpiarErroresEstadoCaso(){
	$("#estadoCasoMod").removeClass("campoTextoError");// estado del caso
	$("#txtJustificacion").removeClass("campoTextoError");// justificacion
}

function guardarEstado(){

	if(validarFormularioEstadoCaso()){

		modificarEstadoCaso();
	}
}

function deshabilitarEstado(){
	estadoActual = $("#procesoEstado").text();
	$("#estadoCasoMod").find("option").each(function(ind1, option){

		$(option).removeAttr("disabled");

	});
	$("#estadoCasoMod").find("option").each(function(ind1, option){
		if($(option).text() == estadoActual){
			$(option).attr("disabled", "disabled");
		}

	});

}

function limpiarFormularioEstadoCaso(){

	$("#estadoCasoMod").val("");
	$("#txtJustificacion").val("");
	limpiarErroresEstadoCaso();
	ocultarMensajesErrorDetalleCaso();
}

function mostrarMensajesErrorDetalleCaso(objectErrores){
	var mensajeError = "";
	if(objectErrores.erroresEstadoCaso && objectErrores.erroresEstadoCaso.length){
		$.each(objectErrores.erroresEstadoCaso, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorEstado").html(mensajeError);
		$("#messageErrorEstado").show();
		mensajeError = "";
	}
	if(objectErrores.erroresNuevoCaso && objectErrores.erroresNuevoCaso.length){
		$.each(objectErrores.erroresNuevoCaso, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorDetalleCaso").html(mensajeError);
		$("#messageErrorDetalleCaso").show();
		mensajeError = "";
	}
	if(objectErrores.erroresMoficarMiembro && objectErrores.erroresMoficarMiembro.length){
		$.each(objectErrores.erroresMoficarMiembro, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorModificarMiembro").html(mensajeError);
		$("#messageErrorModificarMiembro").show();
		mensajeError = "";
	}
	if(objectErrores.erroresMoficarRadicado && objectErrores.erroresMoficarRadicado.length){
		$.each(objectErrores.erroresMoficarRadicado, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorRadicado").html(mensajeError);
		$("#messageErrorRadicado").show();
		mensajeError = "";
	}

}

function ocultarMensajesErrorDetalleCaso(){
	$("#messageErrorEstado").hide();
	$("#messageErrorEstado").html("");
	$("#messageErrorDetalleCaso").hide();
	$("#messageErrorDetalleCaso").html("");
}

/*******************************************************************************
 * Modificar detalle
 ******************************************************************************/
function mostrarFechaCaducidadModificarDetalle(){
	var diasPosponerCaducidad = diasAdicionarCaducidad?diasAdicionarCaducidad:0;
	confirmarCambioFechaHechos();
	fechaCaduci = $("#fechaDeLosHechosMod").val();
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
	
	$("#fechaDeCaducidadMod").val(ano + "-" + fecha[1] + "-" + fecha[2]);

}

function obtenerTipoCaso(){
	almacenarTipoCaso = $("#tipoCasoMod").val();
}

function confirmarCambioTipoCaso(){
	$("#modalConfTipoCaso").modal("show");
}

function cancelarTipoCaso(){
	$("#tipoCasoMod").val(almacenarTipoCaso);
}

function confirmarCambioFechaHechos(){
	$("#modalConfFechaHechos").modal("show");
}

function ingresarFechaHechos(){
	$("#txtFechaDeLosHechos").val(fechaHechos);
	mostrarFechaCaducidad();
}

function modificarDetalleCaso() {
	fecha = $("#fechaDeLosHechosMod").val().split("-");
	var fechaHechos = new Date(fecha[0], fecha[1] - 1, fecha[2]);
	var strFechaFin = $("#finFechaDeLosHechosMod").val();
	var strFechaSolicitudPrej = $("#txtFechaPrejudicialMod").val();
	var fechaFinHecho = "";
	var fechaCaducidad = "";
	var fechaSolicitudPrej = "";
	
	if(strFechaFin!=""){
		fecha = $("#finFechaDeLosHechosMod").val().split("-");
		fechaFinHecho = new Date(fecha[0], fecha[1] - 1, fecha[2]);
		fecha = $("#fechaDeCaducidadMod").val().split("-");
		fechaCaducidad = new Date(fecha[0], fecha[1] - 1, fecha[2]);	
	}
	if(strFechaSolicitudPrej!=""){
		fecha = strFechaSolicitudPrej.split("-");
		fechaSolicitudPrej = new Date(fecha[0], fecha[1] - 1, fecha[2]);
			
	}
	
	
	
	var fechaCaducidadCambio = actualFechaCaducidad!=$("#finFechaDeLosHechosMod").val();
	
	var data_caso = "_csrf=" + $("#_csrf").val();
	// bloque para ingresar el detalle del caso
	data_caso += "&codigo=" + $("#codigoParam").val();
	data_caso += "&fechaCaducidadCambio=" + fechaCaducidadCambio;
	data_caso += "&tipoCaso.codigo=" + $("#tipoCasoMod").val() + "&nombre=" + $("#nombreCasoMod").val()
			+ "&fechaHecho=" + fechaHechos;
	data_caso += (fechaFinHecho!="")?"&fechaFinHecho=" + fechaFinHecho:"";
	
	data_caso += (fechaSolicitudPrej!="")?"&fechaSolicitudPrejudicial=" + fechaSolicitudPrej:"";
	
	data_caso += "&numeroDespacho=" + $("#numeroDespachoMod").val() + "&nombreFuncionario="
			+ $("#nombreFuncionarioMod").val();
	// data_caso += "&comentario="+ $("#txtComentario").val();
	data_caso += "&ciudadHechos.ciudadPK.codigoCiudad=" + $("#municipioDeLosHechosMod").val()
			+ "&ciudadHechos.ciudadPK.codigoDepartamento=" + $("#departamentoDeLosHechosMod").val();
	data_caso += "&direccionHechos=" + $("#direccionDeLosHechosMod").val() + "&resumenHechos="
			+ $("#resumenDeLosHechosMod").val();
	// data_caso += "&fechaCaducidad=" + fechaCaducidad + "&justificacion=" +
	// "Esta es la justificacion";
	data_caso += (fechaCaducidad!="")?"&fechaCaducidad=" + fechaCaducidad:"";
	data_caso += "&usuarioCreacion=" + $("#idusercreacion").val();
	data_caso += "&usuarioUltimaModificacion=" + $("#idusercreacion").val();
	data_caso += "&direccionProceso=" + $("#direccionDespachoMod").val();
	data_caso += "&comentario=" + $("#comentarioMod").val();
	if(!EstaVacio($("#municipioProcesoMod").val())){

		data_caso += "&ciudadProceso.ciudadPK.codigoCiudad=" + $("#municipioProcesoMod").val()
				+ "&ciudadProceso.ciudadPK.codigoDepartamento=" + $("#deparmentoProcesoMod").val();		
	}

	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/caso/guardarDetalleCaso",
		// data : $("#idFormulario").serialize(),
		data : data_caso,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			if(response.STATUS == "SUCCESS"){
				$("#modal-modificarDetalleCaso").modal("hide");
				$("#messageExitoso").html("Se modific\u00f3 el detalle del caso exitosamente.");
				$("#messageExitoso").show();
				setTimeout("limpiarMensajeExito();", 10000);
				limpiarFormularioDetalleCaso();

			}else{
				$("#messageError").html("No fue posible actualizar el caso.");
				$("#messageError").show();
				setTimeout(function(){
					$("#messageError").html("").hide();
				}, 10000);

			}
		},
		error : function(error){
			waitingDialog.hide();
			$("#messageError").html("No fue posible actualizar el caso.").show();
			setTimeout(function(){
				$("#messageError").html("").hide();
			}, 10000);
		}
	});
}

function guardarDetalleCaso(){
	if (validarFormularioDetalleCaso()) {
		if(validarCambiosFechas()){
			$("#modalConfFechaHechos").modal("show");
		}else{
			modificarDetalleCaso();
		}
	}
}

function validarFormularioDetalleCaso(){
	ocultarMensajesErrorDetalleCaso();
	limpiarErroresModificarDetalleCaso();

	var erroresNuevoCaso = [];
	var isError = false;

	// valida el tipo de caso
	if(EstaVacio($("#tipoCasoMod").val())){
		$("#tipoCasoMod").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo tipo del caso es obligatorio.");
	}
	// valida el nombre del caso
	if(EstaVacio($("#nombreCasoMod").val())){
		$("#nombreCasoMod").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo nombre del caso es obligatorio.");
	}
	// valida la fechas de los hechos
	if(EstaVacio($("#fechaDeLosHechosMod").val())){
		$("#fechaDeLosHechosMod").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo fecha de los hechos es obligatorio.");
	}
	// valida el lugar de los hechos
	// Pais
	if(EstaVacio($("#paisDeLosHechosMod").val())){
		$("#paisDeLosHechosMod").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo pa\u00EDs de los hechos es obligatorio.");
	}
	// Departamento
	if(EstaVacio($("#departamentoDeLosHechosMod").val())){
		$("#departamentoDeLosHechosMod").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo departamento de los hechos es obligatorio.");
	}
	// Municipio
	if(EstaVacio($("#municipioDeLosHechosMod").val())){
		$("#municipioDeLosHechosMod").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo municipio de los hechos es obligatorio.");
	}
	// Municipio
	if(EstaVacio($("#direccionDeLosHechosMod").val())){
		$("#direccionDeLosHechosMod").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo lugar de los hechos es obligatorio.");
	}else{
		$("#direccionDeLosHechosMod").removeClass("campoTextoError");
	}

	/**
	 * Formulario lugar proceso
	 */

	if(!EstaVacio($("#direccionDespachoMod").val())){
		if(EstaVacio($("#paisProcesoMod").val())){
			$("#paisProcesoMod").addClass("campoTextoError");
			isError = true;
			erroresNuevoCaso.push("El campo pa\u00EDs es obligatorio.");
		}else{
			$("#paisProcesoMod").removeClass("campoTextoError");
		}
		if(EstaVacio($("#deparmentoProcesoMod").val())){
			$("#deparmentoProcesoMod").addClass("campoTextoError");
			isError = true;
			erroresNuevoCaso.push("El campo departamento es obligatorio.");
		}else{
			$("#deparmentoProcesoMod").removeClass("campoTextoError");
		}
		if(EstaVacio($("#municipioProcesoMod").val())){
			$("#municipioProcesoMod").addClass("campoTextoError");
			isError = true;
			erroresNuevoCaso.push("El campo municipio es obligatorio.");
		}else{
			$("#municipioProcesoMod").removeClass("campoTextoError");
		}
	}else{
		$("#paisProcesoMod").removeClass("campoTextoError");
		$("#deparmentoProcesoMod").removeClass("campoTextoError");
		$("#municipioProcesoMod").removeClass("campoTextoError");

	}

	if(isError){
		var objectErrores = new Object();
		objectErrores.erroresNuevoCaso = erroresNuevoCaso;
		mostrarMensajesErrorDetalleCaso(objectErrores);
		return false;
	}else{
		return true;
	}

}

function limpiarErroresModificarDetalleCaso(){
	$("#tipoCasoMod").removeClass("campoTextoError");// tipo del caso
	$("#tipoCasoMod").addClass("form-control");
	$("#nombreCasoMod").removeClass("campoTextoError");// Nombre del caso
	$("#nombreCasoMod").addClass("form-control");
	$("#fechaDeLosHechosMod").removeClass("campoTextoError");// fechas de los
	// hechos
	$("#fechaDeLosHechosMod").addClass("form-control");
	$("#paisDeLosHechosMod").removeClass("campoTextoError");// Pa�s del los
	// hechos
	$("#paisDeLosHechosMod").addClass("form-control");
	$("#departamentoDeLosHechosMod").removeClass("campoTextoError");// Departamento
	// de los
	// hechos
	$("#departamentoDeLosHechosMod").addClass("form-control");
	$("#municipioDeLosHechosMod").removeClass("campoTextoError");// Municipio
	// de los
	// hechos
	$("#municipioDeLosHechosMod").addClass("form-control");
	$("#municipioProcesoMod").removeClass("campoTextoError");// municipio
	// delproceso
}

function limpiarFormularioDetalleCaso(){

	ocultarMensajesErrorDetalleCaso();
	limpiarErroresModificarDetalleCaso();
	consultarCaso($("#codigoParam").val());
}

/*******************************************************************************
 * Modificar miembro del equipo Caso
 ******************************************************************************/

function cargarformularioModificarMiembros() {
	if(formularioModificarMiembros == null){
		formularioModificarMiembros = $("#modificarDemandado")[0].cloneNode(true);
	}
	if(formularioModificarVictimaDemandado == null){
		formularioModificarVictimaDemandado = $("#modificarVictimaDemandante")[0].cloneNode(true);
	}
	if(formularioModificarAbogado == null){
		formularioModificarAbogado = $("#modificarAbogado")[0].cloneNode(true);
	}

}

function cambiarFormularioModificarMiembro(codigoTipoMiembro) {
	if (codigoTipoMiembro == 3) {
		$("#modificarDemandado").html(formularioModificarMiembros);
		formularioModificarMiembros = $("#modificarDemandado")[0].cloneNode(true);
	} else {
		if (codigoTipoMiembro == 5) {
			$("#modificarDemandado").html(formularioModificarAbogado);
			formularioModificarAbogado = $("#modificarAbogado")[0].cloneNode(true);

		} else {
			$("#modificarDemandado").html(formularioModificarVictimaDemandado);
			formularioModificarVictimaDemandado = $("#modificarVictimaDemandante")[0].cloneNode(true);
		}

	}
}


function modificarMiembroEquipo(formularioMiembro) {
	var data_caso = "_csrf=" + $("#_csrf").val();
	if (true/*codigoEquipoCasoAnt != $(formularioMiembro).closest("div[name=informacionMiembro]").find(
			"input[name=txtCodigoEquipoCaso]").val()*/){
		limpiarErroresModicarMiembro();
		codigosTelefonoEliminado = [];
		codigosCelularEliminado = [];
		codigosCorreosEliminado = [];
		codigoEquipoCasoAnt = $(formularioMiembro).closest("div[name=informacionMiembro]").find(
				"input[name=txtCodigoEquipoCaso]").val();

		codigoTipoMiembro = $(formularioMiembro).closest("div[name=informacionMiembro]").find(
				"input[name=txtCodigoTipoMiembro]").val();
		codigoEquipoCaso = $(formularioMiembro).closest("div[name=informacionMiembro]").find(
				"input[name=txtCodigoEquipoCaso]").val();

		data_caso += "&codigo=" + $("#codigoParam").val();
		data_caso += "&codigoEquipoCaso=" + codigoEquipoCaso;
		data_caso += "&miembro=" + codigoTipoMiembro;
		cambiarFormularioModificarMiembro(codigoTipoMiembro);

		waitingDialog.show('');
		$.ajax({
			type : "POST",
			url : contexto + "/caso/consultarEquipoCaso",
			data : data_caso,
			dataType : "json",
			success : function(response) {
				waitingDialog.hide();
				cargarInfoEquipoCaso(response);
			},
			error : function(e) {
				waitingDialog.hide();
			}
		});

	}
}

function cargarInfoEquipoCaso(response) {
	if (response.casoEquipoCaso.casoEquipoCasoPK.miembro == 3) {
		cargarFormularioDemandadoReferenciador(response.casoEquipoCaso);
	} else {
		$("#txtObservacionesMiembro").closest(".row").hide();
		if (codigoTipoMiembro == 5) {
			cargarFormularioAbogado(response.casoEquipoCaso, response.tarjetaProfesional);
		} else {
			cargarFormularioVictimasDemandantes(response.casoEquipoCaso);
		}
		if(codigoTipoMiembro == 7){
			$("#txtObservacionesMiembro").closest(".row").show();
		}
	}
}

function cargarFormularioAbogado(response, tarjetaProfesional) {
	
	//Cargar formularios
	$("#modificarDemandado").find("input[name=txtTarjetaProfesional]").val(tarjetaProfesional);
	$("#modificarDemandado").find("input[name=txtNombresMiembro]").val(response.equipoCaso.nombre);
	$("#modificarDemandado").find("input[name=txtApellidosMiembro]").val(response.equipoCaso.apellido);
	$("#modificarDemandado").find("input[name=txtDireccionMiembro]").val(response.direccion);
	$("#modificarDemandado").find("input[name=txtIdentificacionMiembro]").val(response.equipoCaso.identificacion);
	if(response.equipoCaso.nombreFoto){
		var imagenTemp = $("#previewFotoTemporalAbogado").children().eq(0);
		imagenTemp.attr("src", "/imagenes/" + response.equipoCaso.nombreFoto);
		imagenTemp.attr("width", "200px");
		imagenTemp.attr("height", "150px");
		$("#previewFotoTemporalAbogado").show();
		$("#fotoAbogado").hide();
	} else{
		$("#previewFotoTemporalAbogado").hide();
		$("#fotoAbogado").show();
	}
//	$("#modificarDemandado").find("img[name=ImagePreviewAbog]").attr("src", "/imagenes/" + response.equipoCaso.nombreFoto);

	/***************************************************************************
	 * cargar tipo documento "abogadoTipoDocumento"
	 **************************************************************************/
	campoTipoDocumento = $("#modificarDemandado").find("select[name=abogadoTipoDocumento]");
	if (response.equipoCaso.tipoDocumento) {
		$(campoTipoDocumento).val(response.equipoCaso.tipoDocumento.codigo);
	}
	
	/***************************************************************************
	 * cargar tipo documento abogadoNacimiento
	 **************************************************************************/
	campoNacimientoUsuario = $("#modificarDemandado").find("input[name=abogadoNacimiento]");
	if (response.equipoCaso.fechaNacimiento) {
		var fechaAbogadoNacimiento = new Date(response.equipoCaso.fechaNacimiento);
		var fechaFormatoVictimaDemandante = formatoFecha(fechaAbogadoNacimiento);
		var fechaFormatoAnioAbogadoNacimiento = formatoFechaAnio(fechaAbogadoNacimiento);
		$("#abogadoNacimiento").val(fechaFormatoVictimaDemandante);
		$("#abogadoNacimiento").val(fechaFormatoAnioAbogadoNacimiento);
	}

	if (response.contacto == "S") {

		$("#modificarDemandado").find("input[name=esContacto]").attr("checked", "checked");
		
	}

	$.each(response.equipoCaso.celularList, function(index, optionData){

		if(index == 0){
			$("#txtCelularMiembro").val(optionData.numero);
			$("#txtCodigoCelular").val(optionData.codigo);
		}else{
			agregarCelular($("#txtCelularMiembro")[0], optionData.numero, optionData.codigo);

		}

	});
	$.each(response.equipoCaso.telefonoList, function(index, optionData){

		if(index == 0){
			$("#txtTelefonoMiembro").val(optionData.numero);
			$("#txtCodigoTelefono").val(optionData.codigo);
		}else{
			agregartelefono($("#txtTelefonoMiembro")[0], optionData.numero, optionData.codigo);

		}
	});
	$.each(response.equipoCaso.correoList, function(index, optionData){

		if(index == 0){
			$("#txtCorreoMiembro").val(optionData.correo);
			$("#txtCodigoCorreo").val(optionData.codigo);
		}else{
			agregarCorreo($("#txtCorreoMiembro")[0], optionData.correo, optionData.codigo);

		}
	});
	
	/***************************************************************************
	 * cargar paises
	 **************************************************************************/

	if(response.ciudadEquipoCaso != null){

		if($("#txtPaisMiembroVictimaDemandante").find("option").length){
			$("#txtPaisMiembroVictimaDemandante").val(response.ciudadEquipoCaso.departamento.pais.codigoPais);
		}else{

			cargarPaises("txtPaisMiembroVictimaDemandante", response.ciudadEquipoCaso.departamento.pais.codigoPais);
		}

		if(response.ciudadEquipoCaso.departamento.codigoDepartamento != null){

			cargarDepartamentosPorPais1(response.ciudadEquipoCaso.departamento.pais.codigoPais,
					"txtPaisMiembroVictimaDemandante", response.ciudadEquipoCaso.departamento.codigoDepartamento);
		}
		if(response.ciudadEquipoCaso.ciudadPK.codigoCiudad != null){

			cargarCiudadPorDepartamentos1(response.ciudadEquipoCaso.departamento.codigoDepartamento,
					"TxtDepartamentoMiembroVictimaDemandante", response.ciudadEquipoCaso.ciudadPK.codigoCiudad);
		}
				
	}else{
		// cargarCombox();
		cargarPaises("txtPaisMiembroVictimaDemandante");
		var isCargarDepartamento = true;
		$(document).ajaxStop(function(){
			if(isCargarDepartamento){
				isCargarDepartamento = false;
				var campoPais = document.getElementById("txtPaisMiembroVictimaDemandante");
				cargarDepartamentosPorPais(campoPais, "TxtDepartamentoMiembroVictimaDemandante");
			}
		});

	}
}

function ocultarMostrar(){
	$("#previewFotoTemporalVicDem").hide();
	$("#fotoFormVicDem").show();
	$("#previewFotoTemporalDemandado").hide();
	$("#fotoDemandado").show();
	$("#previewFotoTemporalAbogado").hide();
	$("#fotoAbogado").show();
}

function cargarFormularioVictimasDemandantes(response){

	$("#modificarDemandado").find("input[name=txtNombresMiembro]").val(response.equipoCaso.nombre);
	$("#modificarDemandado").find("input[name=txtApellidosMiembro]").val(response.equipoCaso.apellido);
	$("#modificarDemandado").find("input[name=txtDireccionMiembro]").val(response.direccion);
	$("#modificarDemandado").find("input[name=txtIdentificacionMiembro]").val(response.equipoCaso.identificacion);
	if(response.equipoCaso.nombreFoto){
		var imagenTemp = $("#previewFotoTemporalVicDem").children().eq(0);
		imagenTemp.attr("src", "/imagenes/" + response.equipoCaso.nombreFoto);
		imagenTemp.attr("width", "200px");
		imagenTemp.attr("height", "150px");
		$("#previewFotoTemporalVicDem").show();
		$("#fotoFormVicDem").hide();
	} else{
		$("#previewFotoTemporalVicDem").hide();
		$("#fotoFormVicDem").show();
	}
//	$("#modificarDemandado").find("div[name=previewFotoTemporalVicDem]").attr("src", "/imagenes/" + response.equipoCaso.nombreFoto);
		if(response.contacto == "S"){

		$("#modificarDemandado").find("input[name=esContacto]").attr("checked", "checked");
		
	}

	campoTipoMiembro = $("#modificarDemandado").find("select[name=txtTipoDeMiembro]")[0];
	/**
	 * cargar tipo de miembro
	 */
	if($(campoTipoMiembro).find("option").length){
		$(campoTipoMiembro).val(response.casoEquipoCasoPK.miembro);
	}else{

		obtenerTipoMiembro(campoTipoMiembro, response.casoEquipoCasoPK.miembro);
	}
	
	/*Para miembro referenciador*/
	if(codigoTipoMiembro == 7){
		$("#txtObservacionesMiembro").val(response.observacion);
	}
	
	/***************************************************************************
	 * cargar parentesco
	 **************************************************************************/
	campoParentesco = $("#modificarDemandado").find("select[name=txtParentescoMiembro]")[0];
	if(response.parentesco){

		if($(campoParentesco).find("option").length){

			$(campoParentesco).val(response.parentesco.codigo);
		}else{
			obtenerParentesco(campoParentesco, response.parentesco.codigo);
		}
	}else{
		obtenerParentesco("txtParentescoMiembro");
	}

	
	/***************************************************************************
	 * cargar tipo documento victimaDemandanteTipoDocumento
	 **************************************************************************/
	campoTipoDocumento = $("#modificarDemandado").find("select[name=victimaDemandanteTipoDocumento]")[0];
	if (response.equipoCaso.tipoDocumento) {
		$(campoTipoDocumento).val(response.equipoCaso.tipoDocumento.codigo);
	}
	
	/***************************************************************************
	 * cargar tipo documento victimaDemandanteNacimientoUsuario
	 **************************************************************************/
	campoNacimientoUsuario = $("#modificarDemandado").find("input[name=victimaDemandanteNacimientoUsuario]")[0];
	if (response.equipoCaso.fechaNacimiento) {
		var fechaVictimaDemandante = new Date(response.equipoCaso.fechaNacimiento);
		var fechaFormatoVictimaDemandante = formatoFecha(fechaVictimaDemandante);
		var fechaFormatoAnioVictimaDemandante = formatoFechaAnio(fechaVictimaDemandante);
		$("#victimaDemandanteNacimientoUsuario").val(fechaFormatoVictimaDemandante);
		$("#victimaDemandanteNacimientoUsuario").val(fechaFormatoAnioVictimaDemandante	);
	}
	
	$.each(response.equipoCaso.celularList, function(index, optionData){

		if(index == 0){
			$("#txtCelularMiembro").val(optionData.numero);
			$("#txtCodigoCelular").val(optionData.codigo);
		}else{
			agregarCelular($("#txtCelularMiembro")[0], optionData.numero, optionData.codigo);

		}

	});
	$.each(response.equipoCaso.telefonoList, function(index, optionData){

		if(index == 0){
			$("#txtTelefonoMiembro").val(optionData.numero);
			$("#txtCodigoTelefono").val(optionData.codigo);
		}else{
			agregartelefono($("#txtTelefonoMiembro")[0], optionData.numero, optionData.codigo);
		}
	});
	$.each(response.equipoCaso.correoList, function(index, optionData){

		if(index == 0){
			$("#txtCorreoMiembro").val(optionData.correo);
			$("#txtCodigoCorreo").val(optionData.codigo);
		}else{
			agregarCorreo($("#txtCorreoMiembro")[0], optionData.correo, optionData.codigo);

		}
	});
	/***************************************************************************
	 * cargar paises
	 **************************************************************************/

	if(response.ciudadEquipoCaso != null){

		if($("#txtPaisMiembroVictimaDemandante").find("option").length){
			$("#txtPaisMiembroVictimaDemandante").val(response.ciudadEquipoCaso.departamento.pais.codigoPais);
		}else{

			cargarPaises("txtPaisMiembroVictimaDemandante", response.ciudadEquipoCaso.departamento.pais.codigoPais);
		}

		if(response.ciudadEquipoCaso.departamento.codigoDepartamento != null){

			cargarDepartamentosPorPais1(response.ciudadEquipoCaso.departamento.pais.codigoPais,
					"txtPaisMiembroVictimaDemandante", response.ciudadEquipoCaso.departamento.codigoDepartamento);
		}
		if(response.ciudadEquipoCaso.ciudadPK.codigoCiudad != null){

			cargarCiudadPorDepartamentos1(response.ciudadEquipoCaso.departamento.codigoDepartamento,
					"TxtDepartamentoMiembroVictimaDemandante", response.ciudadEquipoCaso.ciudadPK.codigoCiudad);
		}
	}else{
		// cargarCombox();
		cargarPaises("txtPaisMiembroVictimaDemandante");
		var isCargarDepartamento = true;
		$(document).ajaxStop(function(){
			if(isCargarDepartamento){
				isCargarDepartamento = false;
				var campoPais = document.getElementById("txtPaisMiembroVictimaDemandante");
				cargarDepartamentosPorPais(campoPais, "TxtDepartamentoMiembroVictimaDemandante");
			}
		});

	}

}

function cargarFormularioDemandadoReferenciador(response){

	$("#modificarDemandado").find("input[name=txtNombresMiembro]").val(response.equipoCaso.nombre);
	$("#modificarDemandado").find("input[name=txtDireccionMiembro]").val(response.direccion);
	$("#modificarDemandado").find("[name=txtObservacionesMiembro]").val(response.observacion);
	$("#modificarDemandado").find("input[name=txtIdentificacionMiembro]").val(response.equipoCaso.identificacion);
	if(response.equipoCaso.nombreFoto){
		var imagenTemp = $("#previewFotoTemporalDemandado").children().eq(0);
		imagenTemp.attr("src", "/imagenes/" + response.equipoCaso.nombreFoto);
		imagenTemp.attr("width", "200px");
		imagenTemp.attr("height", "150px");
		$("#previewFotoTemporalDemandado").show();
		$("#fotoDemandado").hide();
	} else{
		$("#previewFotoTemporalVicDem").hide();
		$("#fotoDemandado").show();
	}
//	$("#modificarDemandado").find("img[name=ImagePreview]").attr("src", "/imagenes/" + response.equipoCaso.nombreFoto);
	if(response.personajuridica == "S"){
		$("#txtTipoPersona").val(1);

	}else{
		$("#txtTipoPersona").val(2);
	}

	$.each(response.equipoCaso.celularList, function(index, optionData){

		if(index == 0){
			$("#txtCelularMiembroDemandado").val(optionData.numero);
			$("#txtCodigoCelular").val(optionData.codigo);
		}else{
			agregarCelular($("#txtCelularMiembroDemandado")[0], optionData.numero, optionData.codigo);

		}

	});
	$.each(response.equipoCaso.telefonoList, function(index, optionData){

		if(index == 0){
			$("#txtTelefonoMiembroDemandado").val(optionData.numero);
			$("#txtCodigoTelefono").val(optionData.codigo);
		}else{
			agregartelefono($("#txtTelefonoMiembroDemandado")[0], optionData.numero, optionData.codigo);

		}
	});
	$.each(response.equipoCaso.correoList, function(index, optionData){

		if(index == 0){
			$("#txtCorreoMiembroDemandado").val(optionData.correo);
			$("#txtCodigoCorreo").val(optionData.codigo);
		}else{
			agregarCorreo($("#txtCorreoMiembroDemandado")[0], optionData.correo, optionData.codigo);

		}
	});
	/***************************************************************************
	 * cargar paises
	 **************************************************************************/

	if(response.ciudadEquipoCaso != null){

		cargarPaises("txtPaisMiembroDemandado", response.ciudadEquipoCaso.departamento.pais.codigoPais);

		if(response.ciudadEquipoCaso.departamento.codigoDepartamento != null){

			cargarDepartamentosPorPais1(response.ciudadEquipoCaso.departamento.pais.codigoPais,
					"txtPaisMiembroDemandado", response.ciudadEquipoCaso.departamento.codigoDepartamento);
		}
		if(response.ciudadEquipoCaso.ciudadPK.codigoCiudad != null){

			cargarCiudadPorDepartamentos1(response.ciudadEquipoCaso.departamento.codigoDepartamento,
					"TxtDepartamentoMiembroDemandado", response.ciudadEquipoCaso.ciudadPK.codigoCiudad);
		}
	} else {
		// cargarCombox();
		cargarPaises("txtPaisMiembroDemandado");
		var isCargarDepartamento = true;
		$(document).ajaxStop(function() {
			if (isCargarDepartamento) {
				isCargarDepartamento = false;
				var campoPais = document.getElementById("txtPaisMiembroDemandado");
				cargarDepartamentosPorPais(campoPais, "TxtDepartamentoMiembroDemandado");
			}
		});
	}
	
	/***************************************************************************
	 * cargar tipo documento demandadoTipoDocumento
	 **************************************************************************/
	campoTipoDocumento = $("#modificarDemandado").find("select[name=demandadoTipoDocumento]")[0];
	if (response.equipoCaso.tipoDocumento) {
		$(campoTipoDocumento).val(response.equipoCaso.tipoDocumento.codigo);
	}
	
	/***************************************************************************
	 * cargar tipo documento demandadoNacimientoUsuario
	 **************************************************************************/
	campoNacimientoUsuario = $("#modificarDemandado").find("input[name=demandadoNacimientoUsuario]")[0];
	if (response.equipoCaso.fechaNacimiento) {
		var fechaDemandado = new Date(response.equipoCaso.fechaNacimiento);
		var fechaFormatoDemandado = formatoFecha(fechaDemandado);
		var fechaFormatoAnioDemandado = formatoFechaAnio(fechaDemandado);
		$("#demandadoNacimientoUsuario").val(fechaFormatoDemandado);
		$("#demandadoNacimientoUsuario").val(fechaFormatoAnioDemandado);
	}

}

// metodo de prueba
function codigos(formularioMiembro){

	$("#modal-modificarDemandado").find("input[name=txtCodigoTelefono]").each(function(ind, codigosTelefono){
		alert($(codigosTelefono).val());
	});
}

function codigoTelefonosEliminado(codigoTelefono){
	codigosTelefonoEliminado.push(codigoTelefono);
}

function codigoCelularEliminado(codigoCelular){
	codigosCelularEliminado.push(codigoCelular);
}

function codigoCorreoEliminado(codigoCorreo){
	codigosCorreosEliminado.push(codigoCorreo);
}

function modificarInformacionMiembroCaso() {
//	var data_caso ="";
//	var data_caso_csrf = "_csrf=" + $("#_csrf").val();
	var data_caso = "_csrf=" + $("#_csrf").val();
	// Bloque para ingresar los miembros del equipo

	if (typeof listaClientes !== 'undefined' && listaClientes.codigoCaso != null)	
		data_caso += "&casoEquipoCasoPK.codigo=" + listaClientes.codigoCaso;
	else 
		data_caso += "&casoEquipoCasoPK.codigo=" + $("#codigoParam").val();
	// data_caso
	// +="&casoEquipoCasoPK.codigoEquipoCaso="+$("#codigoParam").val();

	
//	Si se usan carateres especiales en cualquier otro campo agregar encodeURIComponent
	data_caso += "&direccion=" +encodeURIComponent($("#modal-modificarDemandado").find("input[name=txtDireccionMiembro]").val());
	data_caso += "&observacion=" + $("#modal-modificarDemandado").find("[name = txtObservacionesMiembro]").val();
	data_caso += "&equipoCaso.identificacion=" + $("#modal-modificarDemandado").find("input[name=txtIdentificacionMiembro]").val();
	if($("#modal-modificarDemandado").find("select[name^='txtMunicipioMiembro']").val() != ""
			&& $("#modal-modificarDemandado").find("select[name^='txtMunicipioMiembro']").val() != null){

		data_caso += "&ciudadEquipoCaso.ciudadPK.codigoCiudad="
				+ $("#modal-modificarDemandado").find("select[name^='txtMunicipioMiembro']").val();
		data_caso += "&ciudadEquipoCaso.ciudadPK.codigoDepartamento="
				+ $("#modal-modificarDemandado").find("select[name^='TxtDepartamentoMiembro']").val();
	}

	if($("#modal-modificarDemandado").find("select[name=txtTipoPersona]").length != 0){
		if($("#modal-modificarDemandado").find("select[name=txtTipoPersona]").val() == 1
				&& $("#modal-modificarDemandado").find("select[name=txtTipoPersona]").val() != ""){

			data_caso += "&personajuridica=" + "S";
			data_caso += "&equipoCaso.nombre="
					+ $("#modal-modificarDemandado").find("input[name=txtNombresMiembro]").val();
		}else{
			data_caso += "&personajuridica=" + "N";
			data_caso += "&equipoCaso.nombre="
					+ $("#modal-modificarDemandado").find("input[name=txtNombresMiembro]").val();
		}
		
		if($("#modal-modificarDemandado").find("select[name=demandadoTipoDocumento]").val()){
			data_caso += "&equipoCaso.tipoDocumento.codigo="
					+ $("#modal-modificarDemandado").find("select[name=demandadoTipoDocumento]").val();

		}
		
		if($("#modal-modificarDemandado").find("input[name=demandadoNacimientoUsuario]").val()){
			fecha = $("#demandadoNacimientoUsuario").val().split("-");
			data_caso += "&equipoCaso.fechaNacimiento=" + new Date(fecha[0], fecha[1] - 1, fecha[2]);
		}
		
		if($("#modal-modificarDemandado").find("select[name=abogadoTipoDocumento]").val()){
			data_caso += "&equipoCaso.tipoDocumento.codigo="
					+ $("#modal-modificarDemandado").find("select[name=abogadoTipoDocumento]").val();

		}
		
		if($("#modal-modificarDemandado").find("input[name=abogadoNacimiento]").val()){
			fecha = $("#abogadoNacimiento").val().split("-");
			data_caso += "&equipoCaso.fechaNacimiento=" + new Date(fecha[0], fecha[1] - 1, fecha[2]);
		}
		
		data_caso += "&casoEquipoCasoPK.miembro=" + 3;
		data_caso += "&activo=" + "S";
		data_caso += "&contacto=" + CONTACTO_NO;
	} else {
		data_caso += "&personajuridica=" + "N";
		data_caso += "&equipoCaso.nombre=" + $("#modal-modificarDemandado").find("input[name=txtNombresMiembro]").val();
		data_caso += "&equipoCaso.apellido="
				+ $("#modal-modificarDemandado").find("input[name=txtApellidosMiembro]").val();
		if(codigoTipoMiembro == 5){
			data_caso += "&casoEquipoCasoPK.miembro=" + codigoTipoMiembro;
			//Captura, la informacion del modal para la tarjeta profesional
			data_caso += "&equipoCaso.usuario.numeroTarjetaProfesional=" + $("#txtTarjetaProfesional").val();
		}else{

			data_caso += "&casoEquipoCasoPK.miembro="
					+ $("#modal-modificarDemandado").find("select[name=txtTipoDeMiembro]").val();
		}				
		if($("#modal-modificarDemandado").find("select[name=txtParentescoMiembro]").val()){
			data_caso += "&parentesco.codigo="
					+ $("#modal-modificarDemandado").find("select[name=txtParentescoMiembro]").val();

		}
		
		if($("#modal-modificarDemandado").find("select[name=victimaDemandanteTipoDocumento]").val()){
			data_caso += "&equipoCaso.tipoDocumento.codigo="
					+ $("#modal-modificarDemandado").find("select[name=victimaDemandanteTipoDocumento]").val();

		}
	
		if($("#modal-modificarDemandado").find("input[name=victimaDemandanteNacimientoUsuario]").val()){
			fecha = $("#victimaDemandanteNacimientoUsuario").val().split("-");
			data_caso += "&equipoCaso.fechaNacimiento=" + new Date(fecha[0], fecha[1] - 1, fecha[2]);
		}
		
		if($("#modal-modificarDemandado").find("select[name=abogadoTipoDocumento]").val()){
			data_caso += "&equipoCaso.tipoDocumento.codigo="
					+ $("#modal-modificarDemandado").find("select[name=abogadoTipoDocumento]").val();

		}
		
		if($("#modal-modificarDemandado").find("input[name=abogadoNacimiento]").val()){
			fecha = $("#abogadoNacimiento").val().split("-");
			data_caso += "&equipoCaso.fechaNacimiento=" + new Date(fecha[0], fecha[1] - 1, fecha[2]);
		}
		
		data_caso += "&activo=" + "S";
		if($("#modal-modificarDemandado").find("input[name=esContacto]:checked").length > 0){
			data_caso += "&contacto=" + "S";
		}else{
			data_caso += "&contacto=" + CONTACTO_NO;
		}
		
		$("input[type='checkbox']").change(function(){
		    if($(this).is(":checked")){
		        $(this).parent().addClass("greenBackground"); 
		    }else{
		        $(this).parent().removeClass("greenBackground");  
		    }
		});
	}

	$("#modal-modificarDemandado").find("input[name^='txtTelefonoMiembro']").each(function(ind, numeroTelefono){
		if(!EstaVacio($(numeroTelefono).val())){
			data_caso += "&equipoCaso.telefonoList[" + ind + "].numero=" + $(numeroTelefono).val();
		}
	});

	$("#modal-modificarDemandado").find("input[name^='txtCorreoMiembro']").each(function(ind, correo){
		if(!EstaVacio($(correo).val())){
			data_caso += "&equipoCaso.correoList[" + ind + "].correo=" + $(correo).val();
		}
	});
	;
	$("#modal-modificarDemandado").find("input[name^='txtCelularMiembro']").each(function(ind, numeroCelular){
		if(!EstaVacio($(numeroCelular).val())){
			data_caso += "&equipoCaso.celularList[" + ind + "].numero=" + $(numeroCelular).val();
		}
	});

	if (typeof listaClientes !== 'undefined' && listaClientes.codigoCaso != null)
		data_caso += "&codigo=" + listaClientes.codigoCaso;
	else 
		data_caso += "&codigo=" + $("#codigoParam").val();
	if (typeof listaClientes !== 'undefined' && listaClientes.codigoEquipoCaso != null)
		data_caso += "&codigoEquipoCaso=" + listaClientes.codigoEquipoCaso;
	else
		data_caso += "&codigoEquipoCaso=" + codigoEquipoCaso;
	if (typeof listaClientes !== 'undefined' && listaClientes.codigoMiembro != null)
		data_caso += "&miembro=" + listaClientes.codigoMiembro;
	else	
		data_caso += "&miembro=" + codigoTipoMiembro;
	

	var oMyForm = new FormData($("#fotoForm"));
	oMyForm.append("archivos", archivos[0].files[0]);

	if (archivos[0].files[0]) {

		waitingDialog.show('');
		$.ajax({
			type : "POST",
//			url : contexto + "/caso/modificarMiembroEquipoCaso?"+data_caso_csrf+escape(data_caso),
			url : contexto + "/caso/modificarMiembroEquipoCaso?" + data_caso,
			// data : $("#idFormulario").serialize(),
			data : oMyForm,
			dataType : "json",
			processData : false,
			contentType : false,
			success : function(response){
				waitingDialog.hide();
				if (response.STATUS == "SUCCESS") {	
					if (typeof listaClientes !== 'undefined' && listaClientes.codigoCaso != null) {
//						crearUrlRedireccionarA("actMiembroUpdate");
						listaClientes.mostrarTablaListadoCliente();
						$("#modal-modificarDemandado").modal("hide");
						$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3on exitosamente.");
						$("#messageExitoso").show();
						setTimeout("limpiarMensajeExito();", 10000);
					} else {
						document.location.href = document.location.href + "&actMiembroUpdate=" + true;
						$("#modal-modificarDemandado").modal("hide");
						$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3on exitosamente.");
						$("#messageExitoso").show();
						setTimeout("limpiarMensajeExito();", 10000);
					}
				} else {
					$("#messageError").html("No se fue posible guardar el caso.");
					$("#messageError").show();
					setTimeout("limpiarMensajeError();", 10000);
				}
			},
			error : function(e){
				waitingDialog.hide();
			}
		});
	}else{
		waitingDialog.show('');
		$.ajax({
			type : "POST",
			url : contexto + "/caso/modificarMiembroEquipoCasoSinFoto",
			data : data_caso,
			dataType : "json",
			success : function(response){
				waitingDialog.hide();
				if (response.STATUS == "SUCCESS"){
					if (typeof listaClientes !== 'undefined' && listaClientes.codigoCaso != null) {
//						crearUrlRedireccionarA("actMiembroUpdate");
						listaClientes.mostrarTablaListadoCliente();
						$("#modal-modificarDemandado").modal("hide");
						$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3on exitosamente.");
						$("#messageExitoso").show();
						setTimeout("limpiarMensajeExito();", 10000);
					} else {
						crearUrlRedireccionarA("actMiembroUpdate");
						$("#modal-modificarDemandado").modal("hide");
						$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3on exitosamente.");
						$("#messageExitoso").show();
						setTimeout("limpiarMensajeExito();", 10000);
					}
				} else {
					$("#messageError").html("No se fue posible guardar el caso.");
					$("#messageError").show();
					setTimeout("limpiarMensajeError();", 10000);
				}
			},
			error : function(e){
				waitingDialog.hide();
			}
		});

	}

}

function validarEmail(email){
	var pattern = new RegExp(
			/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);

	return pattern.test(email);
}

function validarformularioModificarMiembrosCaso(){
	limpiarErroresModicarMiembro();
	var erroresMoficarMiembro = [];
	var isError = false;
	var mostrarErrorEnMail = function(campo, mensajeError){
		$(campo).addClass("campoTextoError");
		isError = true;
		erroresMoficarMiembro.push(mensajeError);
	};

	// valida el tipo de persona

	var tipoMiembro = $("#modificarDemandado").find("select[name=txtTipoDeMiembro]");
	if(tipoMiembro.val() == 7 && EstaVacio($("#txtObservacionesMiembro").val())){
		$("#txtObservacionesMiembro").addClass("campoTextoError");
		isError = true;
		erroresMoficarMiembro.push("El campo Nota/Observación es obligatorio.");
	}
	
	$("#modificarDemandado").find("select[name=txtTipoDeMiembro]").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresMoficarMiembro.push("El campo tipo de persona es obligatorio.");
		}
	});
	// valida el nombre del miembro del equipo
	if(EstaVacio($("#modificarDemandado").find("input[name=txtNombresMiembro]").val())){
		$("#modificarDemandado").find("input[name=txtNombresMiembro]").addClass("campoTextoError");
		isError = true;
		erroresMoficarMiembro.push("El campo nombres es obligatorio.");
	}
	// valida el apellido
	if($("#modificarDemandado").find("select[name=txtTipoDeMiembro]").length){

		if(EstaVacio($("#modificarDemandado").find("input[name=txtApellidosMiembro]").val())){
			$("#modificarDemandado").find("input[name=txtApellidosMiembro]").addClass("campoTextoError");
			isError = true;
			erroresMoficarMiembro.push("El campo apellido es obligatorio.");
		}
	}

	$("#modificarDemandado").find("input[name=txtTelefonoMiembro]").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresMoficarMiembro.push("El campo Tel\u00E9fono es obligatorio.");
		}
	});
	// valida los correos
	$("#modificarDemandado").find("input[name=txtCorreoMiembro]").each(function(){
		if(EstaVacio($(this).val())){
			mostrarErrorEnMail(this, "El campo correo es obligatorio.");
		}else{
			if(!validarEmail($(this).val())){
				mostrarErrorEnMail(this, "El campo correo no tiene un email valido.");
			}
		}
	});
	$("#modificarDemandado").find("input[name=txtCorreoMiembroDemandado]").each(function(){
		if(!EstaVacio($(this).val()) && !validarEmail($(this).val())){
			mostrarErrorEnMail(this, "El campo correo no tiene un email valido.");
		}
	});
	$("#modal-modificarDemandado").find("select[name=txtTipoPersona]").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresMoficarMiembro.push("El campo tipo de persona es obligatorio.");
		}
	});

	if(isError){
		var objectErrores = new Object();
		objectErrores.erroresMoficarMiembro = erroresMoficarMiembro;
		mostrarMensajesErrorDetalleCaso(objectErrores);
		return false;
	}else{
		return true;
	}

}

function guardarModificacionesMiembro(){

	if(validarformularioModificarMiembrosCaso()){
		modificarInformacionMiembroCaso();
	}
}

function limpiarErroresModicarMiembro(){

	$("#modificarDemandado").find("input[name=txtNombresMiembro]").removeClass("campoTextoError");
	$("#modificarDemandado").find("input[name=txtNombresMiembro]").addClass("form-control");
	$("#modificarDemandado").find("input[name=txtApellidosMiembro]").removeClass("campoTextoError");
	$("#modificarDemandado").find("input[name=txtApellidosMiembro]").addClass("form-control");
	$("#modificarDemandado").find("input[name=txtTelefonoMiembro]").removeClass("campoTextoError");
	$("#modificarDemandado").find("input[name=txtTelefonoMiembro]").addClass("form-control");
	$("#modificarDemandado").find("input[name=txtCorreoMiembro]").removeClass("campoTextoError");
	$("#modificarDemandado").find("input[name=txtCorreoMiembro]").addClass("form-control");
	$("#modificarDemandado").find("input[name=txtTipoPersona]").removeClass("campoTextoError");
	$("#modificarDemandado").find("input[name=txtTipoPersona]").addClass("form-control");
	$("#messageErrorModificarMiembro").hide();
}

/**
 * Modificar Radicados
 * 
 */

function agregarFormularioRadicadoDetalleCaso(){
	var formularioBienesAfectados = $("[name = formularioRadicado]")[0];
	var object = formularioBienesAfectados.cloneNode(true);
	object.style.display = 'block';
	$(object).find("input[name=txtNumeroRadicado]").val("");
	var boton = object.getElementsByTagName('a')[0];
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border:0;");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		$(boton).closest("div[name=formularioRadicado]").remove();

	};
	$("#agregarFormularioRadicado").append(object);
}

function consultarRadicado(btnEditar){
	var data_caso = "_csrf=" + $("#_csrf").val();
	data_caso += "&radicadoPK.codigoRadicado=" + $(btnEditar).closest("tr").find("td:lt(1)").text();
	data_caso += "&radicadoPK.codigoCaso=" + $("#codigoParam").val();
	$("#btnAgregarRadicado").addClass("hide");
	limpiarCamposNuevosRadicados();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/detalleCaso/consultarRadicado",
		data : data_caso,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			$("#numeroRadicadoMod").val(response.radicado.codigoRadicado);
			$("#numeroRadicadoHidden").val(response.radicado.codigoRadicado);
			$("#instanciaRadicadoMod").val(response.radicado.instanciaRadicado);
			limpiarFormularioRadicado = true;
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function consultarBienAfectado(codigoBien, codigoCaso){
	var data_caso = "_csrf=" + $("#_csrf").val();
	data_caso += "&codigoBienAfectado=" + codigoBien + "&codigoCaso=" + codigoCaso;
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/detalleCaso/consultarBienAfectado",
		data : data_caso,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			$("#txtNombreBien").val(response.bienAfectado.nombreBienAfectado);
			$("#txtDescripcionBien").val(response.bienAfectado.descripcionBienAfectado);
			$("#cmbTipoBien").val(response.bienAfectado.tipoBienAfectado);
			$("#codigoCaso").val(response.bienAfectado.codigoCaso);
			$("#codigoBienAfectado").val(response.bienAfectado.codigoBienAfectado);
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function consultarPrestamo(codigoPrestamo, codigoCaso){
	var data_caso = "_csrf=" + $("#_csrf").val();
	data_caso += "&codigoPrestamo=" + codigoPrestamo + "&caso.codigo=" + codigoCaso;
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/detalleCaso/consultarPrestamo",
		data : data_caso,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			$("#txtnombreDeudorModal").val(response.prestamo.nombreDeudorPrestamo);
			$("#txtIdentificacionDeudorModal").val(response.prestamo.identificacionDeudorPrestamo);
			$("#txtDescripcionPrestamoModal").val(response.prestamo.descripcionPrestamo);
			$("#txtFechaPrestamoModal").val(response.prestamo.fechaPrestamo);
			if(response.prestamo.montoPrestamo) $("#txtValorPrestamoModal").val(response.prestamo.montoPrestamo);

			if(response.prestamo.porcentajeInteresPrestamo)
				$("#txtPorcentajeInteresPrestamoModal").val(response.prestamo.porcentajeInteresPrestamo);

			if(response.prestamo.interesPrestamo) $("#txtInteresesModal").val(response.prestamo.interesPrestamo);

			if(response.prestamo.saldoPrestamo) $("#txtSaldoPrestamoModal").val(response.prestamo.saldoPrestamo);

			$("#txtTipoDeCuentaModal").val(response.prestamo.tipoCuentaPrestamo);
			$("#txtEntidadFinancieraModal").val(response.prestamo.codigoEntidadPrestamo);
			$("#txtNumeroDeCuentaModal").val(response.prestamo.numeroCuentaPrestamo);
			$("#txtTituloValorModal").val(response.prestamo.tituloPrestamo);
			$("#codigoCasoPrestamoModal").val(response.prestamo.codigoCaso);
			$("#codigoPrestamoModal").val(response.prestamo.codigoPrestamo);
			$("#cdusuariocreacion").val(response.prestamo.cdusuariocreacion);
			
			$("#txtTotalPagado").val(response.prestamo.totalPagado);
			$("#txtSaldoPrestamoModal").val($("#txtValorPrestamoModal").val()-response.prestamo.totalPagado); //Valor prestamo menos el pagado 
			
			if(response.prestamo.existeAbono){
				$("#txtFechaCancelacionPrestamo").val(response.prestamo.fechaUltimoAbono);
			}
			calcularInteres(response.prestamo);
			
			
			
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function calcularInteres(prestamo){
	var interesesPagados = prestamo.interesesPagados;
	var fechaPrestamo;
	codigoPrestamo = prestamo.codigoPrestamo;
	
	if(prestamo.existeAbono){
		fechaPrestamo = prestamo.fechaUltimoAbono;
	}else{
		fechaPrestamo = $("#txtFechaPrestamoModal").val();
	}
	var split = $("#txtFechaPrestamoModal").val().split("-");
	var fechaUltimoAbonoDate = new Date(split[0], split[1] - 1, split[2]);
	
	var urlFechas = "fechaPrestamo="+fechaUltimoAbonoDate;
	
	
	
	$.getJSON( contexto + "/detalleCaso/getUltimoAbono?codigoPrestamo="+codigoPrestamo+"&"+urlFechas, function( data ) {
			var abono = data.abono;
			var intereses = abono.saldoInteres;
			calcularInteresPrestamo(abono.diasTranscurridos, prestamo);
		});
	
}

function consultarPrestamoAbono(codigoPrestamo, codigoCaso){
	var data_caso = "_csrf=" + $("#_csrf").val();
	data_caso += "&codigoPrestamo=" + codigoPrestamo + "&caso.codigo=" + codigoCaso;
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/detalleCaso/consultarPrestamo",
		data : data_caso,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			$("#codigoPrestamoAdicionarAbono").val(codigoPrestamo);
			$("#txtnombreDeudorModalAdicionarAbono").val(response.prestamo.nombreDeudorPrestamo);
			$("#txtIdentificacionDeudorModalAdicionarAbono").val(response.prestamo.identificacionDeudorPrestamo);
			$("#txtFechaPrestamoModalAdicionarAbono").val(response.prestamo.fechaPrestamo);
			if(response.prestamo.montoPrestamo) 
				$("#txtValorPrestamoModalAdicionarAbono").val(response.prestamo.montoPrestamo);
			
			if(response.prestamo.porcentajeInteresPrestamo)
				$("#txtPorcentajeInteresPrestamoModalAdicionarAbono	").val(response.prestamo.porcentajeInteresPrestamo);
			
			calcularSaldoCapitalInteres(codigoPrestamo);
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function mostrarBotonAgregarRadicado(){
	$("#btnAgregarRadicado").removeClass("hide");
	if(limpiarFormularioRadicado){
		limpiarCamposNuevosRadicados();
		limpiarFormularioRadicado = false;
	}

}

function modificarBienAfectado(){
	$("#messageErrorBienAfectado").hide();
	if(EstaVacio($("#txtNombreBien").val()) && EstaVacio($("#txtDescripcionBien").val())
			&& EstaVacio($("#cmbTipoBien").val())){
		$("#messageErrorBienAfectado").html("Debe ingresar al menos un dato para modificar el bien afectado.");
		$("#messageErrorBienAfectado").show();
		return;
	}
	var data_caso = "_csrf=" + $("#_csrf").val();
	if(!EstaVacio($("#codigoBienAfectado").val())){
		data_caso += "&bienAfectadoPK.codigo=" + $("#codigoBienAfectado").val()
	}
	data_caso += "&titulo=" + $("#txtNombreBien").val() + "&detalle=" + $("#txtDescripcionBien").val()
			+ "&bienAfectadoPK.codigoCaso=" + $("#codigoParam").val();

	if(!EstaVacio($("#cmbTipoBien").val())){
		data_caso += "&clase.codigo=" + $("#cmbTipoBien").val();
	}

	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/detalleCaso/actualizarBienAfectado",
		data : data_caso,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			if(response.STATUS == "SUCCESS"){
				$("#modal-modificarBienAfectado").modal("hide");
				$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3n exitosamente.");
				$("#messageExitoso").show();
				setTimeout("limpiarMensajeExito();", 10000);
				$("#txtNombreBien").val("");
				$("#txtDescripcionBien").val("");
				$("#cmbTipoBien").val("");
				mostrarTablaBienesAfectados($("#codigoParam").val());
				limpiarModalBienAfectado();
			}else{
				$("#messageError").html("No se fue posible modificar los bienes afectados del caso.");
				$("#messageError").show();
				setTimeout("limpiarMensajeError();", 10000);
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});

}

function limpiarModalBienAfectado(){
	$("#modal-modificarBienAfectado input, #modal-modificarBienAfectado select").val("");
	$("#modal-modificarBienAfectado").modal("hide");
}

function modificarPrestamo(){
	var fechaPrestamoModal = null;
	var fechaPrestamoModalString = $("#txtFechaPrestamoModal").val();
	if(fechaPrestamoModalString){
		var res = fechaPrestamoModalString.split("-");
		fechaPrestamoModal = new Date(res[0], res[1] - 1, res[2]);
	}
	if(validarFormularioPrestamo($("#formularioPrestamo"))){
		var data_caso = "_csrf=" + $("#_csrf").val();
		data_caso += "&nombreDeudor=" + $("#txtnombreDeudorModal").val() + "&identificacion="
				+ $("#txtIdentificacionDeudorModal").val() + "&descripcionPrestamo="
				+ $("#txtDescripcionPrestamoModal").val() + "&monto=" + $("#txtValorPrestamoModal").val()
				+ "&porcentajeInteres=" + $("#txtPorcentajeInteresPrestamoModal").val();
		if($("#txtTipoDeCuentaModal").val())
			data_caso += "&tipoCuenta.codigoTipoCuenta=" + $("#txtTipoDeCuentaModal").val();
		if($("#txtEntidadFinancieraModal").val())
			data_caso += "&entidadFinaciera.codigoEntidadfinaciera=" + $("#txtEntidadFinancieraModal").val();
		data_caso += "&numeroCuenta=" + $("#txtNumeroDeCuentaModal").val() + "&intereses="
				+ $("#txtInteresesModal").val() + "&saldo=" + $("#txtSaldoPrestamoModal").val() + "&titulo="
				+ $("#txtTituloValorModal").val() + "&caso.codigo=" + $("#codigoCasoPrestamoModal").val()
				+ "&codigoPrestamo=" + $("#codigoPrestamoModal").val() + "&usuarioUltimaModificacion="
				+ $("#idusercreacion").val();
		if(fechaPrestamoModal) data_caso += "&fechaPrestamo=" + fechaPrestamoModal;

		waitingDialog.show('');
		$.ajax({
			type : "POST",
			url : contexto + "/detalleCaso/actualizarPrestamo",
			data : data_caso,
			dataType : "json",
			success : function(response){
				waitingDialog.hide();
				if(response.STATUS == "SUCCESS"){
					$("#modal-modificarPrestamo").modal("hide");
					$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3n exitosamente.");
					$("#messageExitoso").show();
					setTimeout("limpiarMensajeExito();", 10000);
					$("#txtnombreDeudorModal").val("");
					$("#txtIdentificacionDeudorModal").val("");
					$("#txtDescripcionPrestamoModal").val("");
					$("#txtFechaPrestamoModal").val("");
					$("#txtValorPrestamoModal").val("");
					$("#txtPorcentajeInteresPrestamoModal").val("");
					$("#txtTipoDeCuentaModal").val("");
					$("#txtEntidadFinancieraModal").val("");
					$("#txtNumeroDeCuentaModal").val("");
					$("#txtInteresesModal").val("");
					$("#txtSaldoPrestamoModal").val("");
					$("#txtTituloValorModal").val("");
					mostrarTablaPrestamo($("#codigoCasoPrestamoModal").val());
					$("#codigoCasoPrestamoModal").val("");
					$("#codigoPrestamoModal").val("");
					$("#cdusuariocreacion").val("");

				}else{
					$("#errorModificarPrestamo").html("No fue posible guardar el prestamo para el caso.");
					$("#errorModificarPrestamo").removeClass("hide");
					setTimeout(function(){
						$("#errorModificarPrestamo").html("");
						$("#errorModificarPrestamo").addClass("hide");
					}, 10000);
				}
			},
			error : function(error){
				waitingDialog.hide();
				$("#errorModificarPrestamo").html("No fue posible guardar el prestamo para el caso.");
				$("#errorModificarPrestamo").removeClass("hide");
				setTimeout(function(){
					$("#errorModificarPrestamo").html("");
					$("#errorModificarPrestamo").addClass("hide");
				}, 10000);
			}
		});
	}else{
		$("#errorModificarPrestamo").html("No ha ingresado ningun campo del formulario.");
		$("#errorModificarPrestamo").removeClass("hide");
		setTimeout(function(){
			$("#errorModificarPrestamo").html("");
			$("#errorModificarPrestamo").addClass("hide");
		}, 10000);
	}

}

function modificarRadicado(){
	var data_caso = "_csrf=" + $("#_csrf").val();
	data_caso += "&numeroRadicadoActualizar=" + $("#numeroRadicadoHidden").val();
	$("#modal-modificarRadicado").find("div[name=formularioRadicado]").each(
			function(ind, formularioRadicado){
				if($(formularioRadicado).find("input[name=checkBoxRadicado]:checked").length > 0){
					data_caso += "&radicadoSet[" + ind + "].radicadoPK.codigoRadicado="
							+ $(formularioRadicado).find("input[name=numeroRadicadoMod]").val();
					data_caso += "&radicadoSet[" + ind + "].radicadoPK.codigoCaso=" + $("#codigoParam").val();
					data_caso += "&radicadoSet[" + ind + "].instancia.codigo="
							+ $(formularioRadicado).find("select[name=instanciaRadicadoMod]").val();
					data_caso += "&radicadoSet[" + ind + "].activo=" + "A";
					data_caso += "&radicadoSet[" + ind + "].usuarioCreacion=" + $("#idusercreacion").val();
					data_caso += "&radicadoSet[" + ind + "].usuarioUltimaModificacion=" + $("#idusercreacion").val();
					data_caso += "&radicadoSet[" + ind + "].acumulado=" + "S";
					data_caso += "&radicadoSet[" + ind + "].radicadoAcumulado.codigo="
							+ $(formularioRadicado).find("input[name=txtNumeroRadicadoPadre]").val();
					data_caso += "&radicadoSet[" + ind + "].comentarioAcumulado="
							+ $(formularioRadicado).find("input[name=txtAcomuladoObservaciones]").val();
					data_caso += "&radicadoSet[" + ind + "].acumuladoCon="
							+ $(formularioRadicado).find("input[name=txtacomuladoCon]").val();
					data_caso += "&radicadoSet[" + ind + "].caso.codigo=" + $("#codigoParam").val();

				}else{
					data_caso += "&radicadoSet[" + ind + "].radicadoPK.codigoRadicado="
							+ $(formularioRadicado).find("input[name=numeroRadicadoMod]").val();
					data_caso += "&radicadoSet[" + ind + "].radicadoPK.codigoCaso=" + $("#codigoParam").val();
					data_caso += "&radicadoSet[" + ind + "].instancia.codigo="
							+ $(formularioRadicado).find("select[name=instanciaRadicadoMod]").val();
					data_caso += "&radicadoSet[" + ind + "].activo=" + "A";
					data_caso += "&radicadoSet[" + ind + "].usuarioCreacion=" + $("#idusercreacion").val();
					data_caso += "&radicadoSet[" + ind + "].usuarioUltimaModificacion=" + $("#idusercreacion").val();
					data_caso += "&radicadoSet[" + ind + "].acumulado=" + "N";
					data_caso += "&radicadoSet[" + ind + "].caso.codigo=" + $("#codigoParam").val();
					
					data_caso += getRadicadosAcumulados($(formularioRadicado),ind, $("#codigoParam").val());
					
					
				}

			});

	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/detalleCaso/modificarRadicado",
		data : data_caso,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			if(response.STATUS == "SUCCESS"){

				$("#modal-modificarRadicado").modal("hide");
				mostrarTablaRadicado($("#codigoParam").val());
				$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3n exitosamente.");
				$("#messageExitoso").show();
				setTimeout("limpiarMensajeExito();", 10000);
				limpiarCamposNuevosRadicados();
			}else{
				if(response.mensajeError){
					$("#modal-modificarRadicado").modal("hide");
					$("#messageRadicadoError").text(response.mensajeError);
					$("#divRadicadoError").removeClass("hide");
					setTimeout(function(){
						$("#messageRadicadoError").text("");
						$("#divRadicadoError").addClass("hide");
					}, 10000);
				}else{
					$("#modal-modificarRadicado").modal("hide");
					$("#messageRadicadoError").text("No se fue posible modificar los radicados del caso.");
					$("#divRadicadoError").removeClass("hide");
					setTimeout(function(){
						$("#messageRadicadoError").text("");
						$("#divRadicadoError").addClass("hide");
					}, 10000);
				}

			}
		},
		error : function(error){
			waitingDialog.hide();
			$("#modal-modificarRadicado").modal("hide");
			$("#messageRadicadoError").text("No se fue posible modificar los radicados del caso.");
			$("#divRadicadoError").removeClass("hide");
			setTimeout(function(){
				$("#messageRadicadoError").text("");
				$("#divRadicadoError").addClass("hide");
			}, 10000);
		}
	});

}

function validarFormularioRadicado(){
	limpiarFormularioModificarRadicado();
	var erroresMoficarRadicado = [];
	var isError = false;

	$("#modal-modificarRadicado").find("input[name=numeroRadicadoMod]").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresMoficarRadicado.push("El campo n\u00FAmero radicado es obligatorio.");
		}
	});
	// valida la instancia del radicado
	$("#modal-modificarRadicado").find("select[name=instanciaRadicadoMod]").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresMoficarRadicado.push("El campo instancia es obligatorio.");
		}
	});

	if(isError){
		var objectErrores = new Object();
		objectErrores.erroresMoficarRadicado = erroresMoficarRadicado;
		mostrarMensajesErrorDetalleCaso(objectErrores);
		return false;
	}else{
		return true;
	}

}

function validarFormularioBienesAfectado(){
	var erroresMoficarBienAfectado = [];
	var isError = false;
	$("#modal-modificarBienAfectado").find("input[name=txtNombreBien]").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresMoficarRadicado.push("El campo nombre Bien Afectado es obligatorio.");
		}
	});
	// valida la instancia del radicado
	$("#modal-modificarBienAfectado").find("input[name=txtDescripcionBien]").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresMoficarRadicado.push("El campo descripción es obligatorio.");
		}
	});

	$("#modal-modificarBienAfectado").find("input[name=cmbTipoBien]").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresMoficarRadicado.push("El campo tipo es obligatorio.");
		}
	});

	if(isError){
		var objectErrores = new Object();
		objectErrores.erroresMoficarBienAfectado = erroresMoficarBienAfectado;
		mostrarMensajesErrorDetalleCaso(objectErrores);
		return false;
	}else{
		return true;
	}

}

function limpiarFormularioModificarRadicado(){

	$("#modal-modificarRadicado").find("input[name=numeroRadicadoMod]").each(function(){
		$(this).removeClass("campoTextoError");
		$(this).addClass("form-control");

	});
	// valida la instancia del radicado
	$("#modal-modificarRadicado").find("select[name=instanciaRadicadoMod]").each(function(){

		$(this).removeClass("campoTextoError");
		$(this).addClass("form-control");
	});
	$("#messageErrorRadicado").hide();
}

function guardarModificacionesRadicados(){

	if(validarFormularioRadicado()){
		modificarRadicado();
	}
}

function guardarModificacionBienAfectado(){

	if(validarFormularioBienesAfectado()){
		modificarRadicado();
	}
}

function cargarformularioRadicado(){

	if(formularioRadicado == null){
		formularioRadicado = $("#modalRadicado")[0].cloneNode(true);
	}
}

function limpiarCamposNuevosRadicados() {
	$("#modal-modificarRadicado").modal("hide");
	$("#modal-modificarRadicado input, #modal-modificarRadicado select").val("");
	// obtenerInstancia("instanciaRadicadoMod");
}

function adicionarTareaDetalle(button, fila) {
	var newRow = $("<tr id='" + contFilasTareasAdicionales + "Tarea'>");
	var cols = "";
	cols += "<td style='width:15%'><input type='text' class='form-control' id='nombreTarea" + filaTareaActividad
			+ "' name='nombreTarea" + filaTareaActividad + "'>";
	cols += "<input type='hidden' class='form-control' id='txtTareaPropia' name='txtTareaPropia' value='S'></td>";
	cols += "<td style='width:15%'><input type='text' class='form-control' id='detalleTarea" + filaTareaActividad
			+ "' name='detalleTarea" + filaTareaActividad + "'></td>";
	cols += "<td style='width:20%'><select id='responsable" + filaTareaActividad + "' name='responsable"
			+ filaTareaActividad + "' multiple='multiple'></select></td>";
	cols += "<td style='width:25%'><select class='form-control' id='cmbEstadoTareas" + filaTareaActividad
			+ "' name='cmbEstadoTareas" + filaTareaActividad + "'>";

	cols += "					<option value='N'> Pendiente </option>";
	cols += "					<option value = 'S' > Completada </option></select></td>";

	cols += "<td class='hide'><input type='text' class='form-control' id='numeroDeDias" + filaTareaActividad
			+ "' onkeydown='return soloNumeros(event)' name='numeroDeDias" + filaTareaActividad
			+ "' maxlength='3'></td>";
	cols += "<td class='hide'><input type='text' class='form-control' id='numeroDeAlertas" + filaTareaActividad
			+ "' onkeydown='return soloNumeros(event)' name='numeroDeAlertas" + filaTareaActividad
			+ "' maxlength='3'></td>";

	cols += "<td style='width:20%'>" + mensaje + "<input type='date' class='form-control' id='vencimiento"
			+ filaTareaActividad + "' name='vencimiento" + filaTareaActividad + "'" + metodoOnchage + "></td>";
	// cols+="<td><a href='javascript:void(0);'
	// onclick='deleteTarea("+contFilasTareasAdicionales+","+contFilasTareasAdicionales+",this);'
	// ><i class='glyphicon glyphicon-minus'
	// style'background-color:red;'></i></a></td>";
	cols += "<td style='width:5%'><a href='javascript:void(0);' onclick='deleteTarea("
			+ fila
			+ ","
			+ filaTareaActividad
			+ ",this);' class='btn btn-danger btn-circle .btn-xs'  title='Eliminar Tarea' ><i class='glyphicon glyphicon-trash'></i></a></td>";
	newRow.append(cols);
	var tabla = $(button).closest("div[name=actividadParticular]").find("table");
	var panelActividad = $(button).closest("div[name=actividadParticular]");
	$(tabla).append(newRow);

	for (var i = 0; i < countActividades; i++){

		if(actividadesArray[i].fila == fila){
			var cantTareas = actividadesArray[i].cantidadTareas;
			var tareasActividadesArray = actividadesArray[i].tareas;

			elementoTareaActividad = new Object();
			elementoTareaActividad.fila = filaTareaActividad;
			tareasActividadesArray[cantTareas] = elementoTareaActividad;
			cantTareas++;
			actividadesArray[i].cantidadTareas = cantTareas;

		}

	}

	newRow.find("select[name^='responsable']").select2({
		placeholder: "Seleccione un responsable",
	    allowClear: true,
	    width: "100%"
	}).on("select2-selecting", function (select) {
		opcionOtros=select.object.element[0];
		if (select.val == -1) {
			$("#modal-ingresarOtroResponsableDetalleCaso").modal("show");
		} else {
			codigosResponsablesNuevoCaso.push(select.val);
		}
	});
	
	$("select[name^='responsable']").select2({
		placeholder: "Seleccione un responsable",
	    allowClear: true,
	    width: "100%"
	}).on("select2-selecting", function (select) {
		optionOtros = select.object.element[0];
		if (select.val == -1) {
			$("#modal-ingresarOtroResponsableDetalleCaso").modal("show");
		} else {
			codigosResponsablesNuevoCasoDetalleCaso.push(select.val);
		}
	});

	filaTareaActividad++;
	contFilasTareasAdicionales++;
	var className = panelActividad.attr("class");
	if (className.indexOf('show') >= 0) {
		habilitarCamposActividad(fila, $(button).parent().find("[name = btnEditarActividad]"));
		panelActividad.removeClass("show");
		$(button).closest("[name = btnEditarActividad]").addClass("hide");
	} else {
		cargarResponsablesTareasDetalle(newRow);
	}

}

function getHtmlActividadParticularMC(){
	var html = '';

	html += '	<br>';
	html += '<div  class="col-sm-12"  name="actividadParticular">';
	html += '<div class="panel panel-default">';
	html += '	<div class="panel-heading">';
	html += '<div class="row">';
	html += '<div class="col-sm-10">';
	html += '		<h4 class="panel-title">';
	html += '			<a data-toggle="collapse" data-parent="#accordion"';
	html += '				href="#collapseActividad' + codigoActivad + '"><label id="nombreActividad" name="nombreActividad">'
			+ $("#nombreActividadDetalle").val() + '</label></a>';
	html += '		</h4>';
	html += '</div>';
	html += '<div class="col-sm-2 text-right">';
	html += '		<a href="javascript:void(0);"';
	if (permisoEliminar == "S") {
		html += '			onclick="borrarActividad('
				+ filaActividad
				+ ',this)" class="btn btn-danger btn-circle btn-xs" title="Eliminar Tarea" name="btnActividad"> <i';
	} else {
		html += '			 class="btn btn-danger btn-circle btn-xs disabled" title="Eliminar Tarea" name="btnActividad"> <i';
	}
	html += '			class="glyphicon glyphicon-trash" id="btnActividad" name="btnActividad"></i>';
	html += '		</a>';
	html += '		<a href="javascript:void(0);"';
	if (permisoEscritura  == "S") {
		html += '			onclick="adicionarTareaDetalle(this,'
				+ filaActividad
				+ ')" class="btn btn-success btn-circle btn-xs" title="Adicionar Tarea" name="btTareas" title="Adicionar"> <i';
	} else {
		html += '			 class="btn btn-success btn-circle btn-xs" title="Adicionar Tarea" name="btTareas" title="Adicionar"> <i';
	}
	html += '			class="glyphicon glyphicon-plus" id="btnTareas" name="btnTareas"></i>';
	html += '		</a>';
	html += '		<a href="javascript:void(0);"';
	if (permisoEscritura  == "S") {
		html += '			onclick="modificarAlertas(this)" class="btn btn-primary btn-circle btn-xs" title="Modificar Alertas" name="btTareas" alt="Adicionar tarea"> <i';
	} else {
		html += '			 class="btn btn-primary btn-circle btn-xs disabled" title="Modificar Alertas" name="btTareas" alt="Adicionar tarea"> <i';
	}
	html += '			class="glyphicon glyphicon-cog" id="btnAlertas" name="btnAlertas"></i>';
	html += '		</a>';
	html += '	</div>';
	html += '</div>';
	html += '	</div>';
	html += '	<div id="collapseActividad' + codigoActivad + '" class="panel-collapse collapse">';
	html += '		<div class="panel-body" id="panelActividad' + codigoActivad + '">';
	html += '<div class="row">';
	html += '<div class="col-sm-3" style="margin-bottom: 1%;">';
	html += '<label>' + tituloNumeroDias + '</label>';
	html += '<input type="text" class="form-control" id="txtNumerosdiasAntesActividad" onkeydown="return soloNumeros(event)" name="txtNumerosdiasAntesActividad" maxlength="15" >';
	html += '</div>';
	html += '<div class="col-sm-3" style="margin-bottom: 1%;">';
	html += '<label>' + tituloNotificaciones + '</label>';
	html += '<input type="text" class="form-control" id="txtNumeroNotificacionesActividad" onkeydown="return soloNumeros(event)" name="txtNumeroNotificacionesActividad" maxlength="15" >';
	html += '<input type="hidden" class="form-control" id="txtEsActividadPropia" name="txtEsActividadPropia" value="S">';
	html += '</div>';
	html += '<div class="col-sm-3" style="margin-bottom: 1%;"><label>'
			+ tituloFechaVencimiento
			+ '</label><span class="text-danger"> *</span><input type="date"  class="form-control" id="actividadVencimiento'
			+ filaActividad + '" name="actividadVencimiento' + filaActividad + '"' + metodoOnchageActividad
			+ '></div> ';
	html += '<div class="col-sm-3" style="margin-bottom: 1%;">';
	html += '<label>' + tituloEstado + '</label><span class="text-danger"> *</span>';
	html += '<select class="form-control" onchange="validarEstadoTareas(this)" onfocus="obtenerEstadoActual(this)"';
	html += '				id="cmbEstadoActividad"';
	html += '				name="cmbEstadoActividad" >';
	html += '					<option value="N"> Pendiente </option>';
	html += '					<option value = "S" > Completada </option></select>';
	html += '</div>';
	html += '</div>';
	html += '<div class="row">';
	html += '		<div class="alert alert-danger hide" name="actividadError"></div>';
	html += '</div>';
	html += '<div class="row">';
	html += '			<table class="table table-bordered table-actividades" id="tabla' + codigoActivad + '">';
	html += '				<thead>';
	html += '				<tr>';
	html += '					<th style="width:15%">Tarea<span class="text-danger"> *</span></th>';
	html += '					<th style="width:15%">Detalle<span class="text-danger"> *</span></th>';
	html += '					<th style="width:20%">Responsable<span class="text-danger"> *</span></th>';
	html += '					<th style="width:25%">Estado<span class="text-danger"> *</span></th>';
	html += '					<th class="hide">N\u00FAmero de d\u00EDas</th>';
	html += '					<th class="hide">N\u00FAmero de notificaciones</th>';
	html += '					<th style="width:20%">Fecha Vencimiento<span class="text-danger"> *</span></th>';
	html += '					<th style="width:5%">Acción</th>';
	html += '				</tr>';
	html += '				</thead>';
	html += '				<tbody>';

	return html;
}

function agregarActividadesDesdeDetalle() {

	if ($("#nombreActividadDetalle").val().trim()) {
		var html = '';
		elementoActividad = new Object();
		elementoActividad.fila = filaActividad;
		elementoActividad.codigoActividad = codigoActivad;
		elementoActividad.nombreActividad = $("#nombreActividadDetalle").val();

		filasActividadesParticulares[countActividadesParticulares] = filaActividad;

		var tareasActividadesArray = new Array();
		var countTareasActividades = 0;

		html += getHtmlActividadParticularMC();

		elementoActividad.tareas = tareasActividadesArray;
		elementoActividad.cantidadTareas = countTareasActividades;

		actividadesArray[countActividades] = elementoActividad;
		countActividades++;
		filaActividad++;
		countActividadesParticulares++;

		html += '				</tbody>';
		html += '			</table>';
		html += '		</div>';
		html += '	</div>';
		html += '</div>';
		html += '</div>';
		html += '	<br">';
		$("#sortable-view").append(html);
		codigoActivad++;
		limpiarModalNuevaActividad();
	} else {
		$("#errorActividadDetalle").removeClass("hide").html("Ingrese un nombre para la actividad");
		setTimeout(function(){
			$("#errorActividadDetalle").addClass("hide").html("");
		}, 10000);
	}

}

function limpiarModalNuevaActividad(){
	$("#modal-asignarNombreDetalle input").val("");
	$("#modal-asignarNombreDetalle").modal("hide");
}

function validarActualizacionActividades(){
	/**
	 * Validación de la modificación de el Formulario Actividades
	 */
	var erroresActividades = [];
	var areaActividades = $("#tab-3");
	var actividades = areaActividades.find("[name = actividadParticular]");
	var isError = false;

	if(actividades.length){
		$.each(actividades, function(indexAct, actividad){

			var tareas = $(actividad).find("tbody tr");
			if($(actividad).find("#nombreActividad").prop("tagName") == "LABEL"){
				var nombreActividad = $(actividad).find("#nombreActividad").text();
			}else{
				var nombreActividad = $(actividad).find("#nombreActividad").val();
			}
			// valida que se ingrese una fecha de vencimiento actividad
			if($(actividad).find("[name *= actividadVencimiento]").not(":disabled").length){
				if(EstaVacio($(actividad).find("[name *= actividadVencimiento]").val())){
					$(actividad).find("[name *= actividadVencimiento]").addClass("campoTextoError");
					isError = true;
					erroresActividades.push("El campo fecha de vencimiento de la actividad " + (indexAct + 1) + ". "
							+ nombreActividad + " es obligatorio.");
				}else{
					$(actividad).find("[name *= actividadVencimiento]").removeClass("campoTextoError");
				}
			}
			$.each(tareas, function(indexTarea, tarea){

				// valida que se ingrese los campos nombre tarea
				if($(tarea).find("[name *= nombreTarea]").not(":disabled").length){
					if(EstaVacio($(tarea).find("[name *= nombreTarea]").val())){
						$(tarea).find("[name *= nombreTarea]").addClass("campoTextoError");
						isError = true;
						erroresActividades.push("El nombre de la tarea " + (indexTarea + 1) + " en la actividad "
								+ (indexAct + 1) + ". " + nombreActividad + " es obligatorio.");
					}else{
						$(tarea).find("[name *= nombreTarea]").removeClass("campoTextoError");
					}
				}

				// valida que se ingrese los campos responsables
				if($(tarea).find("[name *= responsable]").not(":disabled").length){
					var identificacion = $(tarea).find("[name *= responsable]").val();
					if(identificacion == null){
						$(tarea).find("[name *= responsable]").addClass("campoTextoError");
						isError = true;
						erroresActividades.push("El responsable de la tarea " + (indexTarea + 1) + " en la actividad "
								+ (indexAct + 1) + ". " + nombreActividad + " es obligatorio.");
					}else{
						$(tarea).find("[name *= responsable]").removeClass("campoTextoError");
					}
				}

				// valida que se ingrese las fechas de vencimiento tarea
				if($(tarea).find("[name *= vencimiento]").not(":disabled").length){
					if(EstaVacio($(tarea).find("[name *= vencimiento]").val())){
						$(tarea).find("[name *= vencimiento]").addClass("campoTextoError");
						isError = true;
						erroresActividades.push("El campo fecha de vencimiento de la tarea " + (indexTarea + 1)
								+ " en la actividad " + (indexAct + 1) + ". " + nombreActividad + " es obligatorio.");
					}else{
						$(tarea).find("[name *= vencimiento]").removeClass("campoTextoError");
					}
				}

				// valida que se ingrese los campos detalle
				if($(tarea).find("[name *= detalleTarea]").not(":disabled").length){
					if(EstaVacio($(tarea).find("[name *= detalleTarea]").val())){
						$(tarea).find("[name *= detalleTarea]").addClass("campoTextoError");
						isError = true;
						erroresActividades.push("El detalle de la tarea " + (indexTarea + 1) + " en la actividad "
								+ (indexAct + 1) + ". " + nombreActividad + " es obligatorio.");
					}else{
						$(tarea).find("[name *= detalleTarea]").removeClass("campoTextoError");
					}
				}
			});
		});
	}

	if(isError){
		var mensajeError = "";
		if(erroresActividades.length){
			$.each(erroresActividades, function(index, optionData){
				mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
			});
			$("#errorActividadMod").html(mensajeError);
			$("#errorActividadMod").removeClass("hide");
			mensajeError = "";
		}
		return false;
	}else{
		$("#errorActividadMod").html("");
		$("#errorActividadMod").addClass("hide");
		return true;
	}
}

function modificarActividades(){
	if(validarActualizacionActividades()){
		var data_caso = "_csrf=" + $("#_csrf").val();
		$("#tab-3").find("input[name=txtNumeroNotificacionesActividad]:enabled").closest(
				"div[name=actividadParticular]").each(
				function(ind, formularioActividad) {
					if($(formularioActividad).find("input[name = txtCodigoActividadCaso]").val()){
						data_caso += "&listaActividadesCaso[" + ind + "].codigoActividadParticular="
								+ $(formularioActividad).find("input[name = txtCodigoActividadCaso]").val();
					}
					var nombreActividad = $(formularioActividad).find("input[name = nombreActividad]").val();
					if(nombreActividad){
						data_caso += "&listaActividadesCaso[" + ind + "].nombreActividad=" + nombreActividad;
					}else{
						data_caso += "&listaActividadesCaso[" + ind + "].nombreActividad="
								+ $(formularioActividad).find("label[name = nombreActividad]").text();
					}
					data_caso += "&listaActividadesCaso[" + ind + "].numeroDiasAntesAlertas="
							+ $(formularioActividad).find("input[name = txtNumerosdiasAntesActividad]").val();
					data_caso += "&listaActividadesCaso[" + ind + "].numeroAlertasPorDia="
							+ $(formularioActividad).find("input[name = txtNumeroNotificacionesActividad]").val();
					data_caso += "&listaActividadesCaso[" + ind + "].esActividadPropia="
							+ $(formularioActividad).find("input[name = txtEsActividadPropia]").val();
					if($(formularioActividad).find("input[name^='actividadVencimiento']").val()){
						var fechaLimite = $(formularioActividad).find("input[name^='actividadVencimiento']").val()
								.split("-");
						data_caso += "&listaActividadesCaso[" + ind + "].fechaLimite="
								+ new Date(fechaLimite[0], parseInt(fechaLimite[1]) - 1, fechaLimite[2]);
					}
					data_caso += "&listaActividadesCaso[" + ind + "].finalizada="
							+ $(formularioActividad).find("select[name = cmbEstadoActividad]").val();
					data_caso += "&listaActividadesCaso[" + ind + "].usuarioCreacion=" + $("#idusercreacion").val();
					data_caso += "&listaActividadesCaso[" + ind + "].usuarioUltimaModificacion="
							+ $("#idusercreacion").val();
					data_caso += "&listaActividadesCaso[" + ind + "].snActiva=" + "S";

					$(formularioActividad).find("tr:gt(0)")
							.each(
									function (ind1, tarea){
										if ($(tarea).find("input[name ^='txtCodigoTareaCaso']").val()){

											data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet["
													+ ind1 + "].codigoTarea="
													+ $(tarea).find("input[name ^='txtCodigoTareaCaso']").val();
										}
										data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet["
												+ ind1 + "].tarea="
												+ $(tarea).find("input[name ^='nombreTarea']").val();
										data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet["
												+ ind1 + "].detalle="
												+ $(tarea).find("input[name ^='detalleTarea']").val();
										data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet["
												+ ind1 + "].finalizada="
												+ $(tarea).find("select[name ^='cmbEstadoTareas']").val();
										data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet["
												+ ind1 + "].esTareaPropia="
												+ $(tarea).find("input[name ^='txtTareaPropia']").val();
										data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet["
												+ ind1 + "].numeroDiasAntesAlertas="
												+ $(tarea).find("input[name ^='numeroDeDias']").val();
										data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet["
												+ ind1 + "].numeroAlertasPorDia="
												+ $(tarea).find("input[name ^='numeroDeAlertas']").val();
										if($(tarea).find("input[name^='vencimiento']").val()){
											var fechaLimite = $(tarea).find("input[name^='vencimiento']").val().split(
													"-");
											data_caso += "&listaActividadesCaso["
													+ ind
													+ "].tareaParticularCasoSet["
													+ ind1
													+ "].fechaLimite="
													+ new Date(fechaLimite[0], parseInt(fechaLimite[1]) - 1,
															fechaLimite[2]);
										}
										data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet["
												+ ind1 + "].usuarioCreacion=" + $("#idusercreacion").val();
										data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet["
												+ ind1 + "].usuarioUltimaModificacion=" + $("#idusercreacion").val();
										data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet["
												+ ind1 + "].snActiva=" + "S";

										identificacionesResponsablesTarea = $(tarea).find(
												"select[name ^='responsable']").val();
										$(identificacionesResponsablesTarea)
												.each(
														function(index1, identificacion) {
															data_caso += "&listaActividadesCaso[" + ind
																	+ "].tareaParticularCasoSet[" + ind1
																	+ "].responsablesTareas[" + index1
																	+ "].responsableTareaPK.codigoEquipoCaso="
																	+ identificacion;
															data_caso += "&listaActividadesCaso[" + ind
																	+ "].tareaParticularCasoSet[" + ind1
																	+ "].responsablesTareas[" + index1
																	+ "].responsableTareaPK.codigoCaso="
																	+ $("#codigoParam").val();
															var isAbogado = true;
															var indiceArrayResponsables = 0;
															$.each($(responsablesDetalleCaso), function(index, optionData){
																
																if (identificacion == optionData.identificacion) {
																	isAbogado = false;
																	indiceArrayResponsables = index;
																}
															})
															
															var miembro = $(tarea).find("select[id^=responsable] option:selected").eq(index1).attr("miembro");
															
															if (isAbogado) {																
																data_caso += "&listaActividadesCaso[" + ind
																+ "].tareaParticularCasoSet[" + ind1
																+ "].responsablesTareas[" + index1
																+ "].responsableTareaPK.codigoMiembro="
																+ miembro;
															} else {
																
																data_caso += "&listaActividadesCaso[" + ind
																+ "].tareaParticularCasoSet[" + ind1
																+ "].responsablesTareas[" + index1
																+ "].responsableTareaPK.codigoMiembro="
																+ TIPOMIEMBROOTRO;
																
																data_caso += "&listaActividadesCaso[" + ind
																+ "].tareaParticularCasoSet[" + ind1
																+ "].responsablesTareas[" + index1
																+ "].casosEquiposCaso.equipoCaso.nombre= " + responsablesDetalleCaso[indiceArrayResponsables].nombre;
																data_caso += "&listaActividadesCaso[" + ind
																+ "].tareaParticularCasoSet[" + ind1
																+ "].responsablesTareas[" + index1
																+ "].casosEquiposCaso.equipoCaso.apellido= " + responsablesDetalleCaso[indiceArrayResponsables].apellidos;
//																data_caso += "&listaActividadesCaso[" + ind
//																+ "].tareaParticularCasoSet[" + ind1
//																+ "].responsablesTareas[" + index1
//																+ "].casosEquiposCaso.equipoCaso.celularList= " + responsablesDetalleCaso[indiceArrayResponsables].email;
																data_caso += "&listaActividadesCaso[" + ind
																+ "].tareaParticularCasoSet[" + ind1
																+ "].responsablesTareas[" + index1
																+ "].casosEquiposCaso.equipoCaso.correoList= " + responsablesDetalleCaso[indiceArrayResponsables].email;
																data_caso += "&listaActividadesCaso[" + ind
																+ "].tareaParticularCasoSet[" + ind1
																+ "].responsablesTareas[" + index1
																+ "].casosEquiposCaso.equipoCaso.telefonoList= " + responsablesDetalleCaso[indiceArrayResponsables].telefonos;
																data_caso += "&listaActividadesCaso[" + ind
																+ "].tareaParticularCasoSet[" + ind1
																+ "].responsablesTareas[" + index1
																+ "].casosEquiposCaso.direccion= " + responsablesDetalleCaso[indiceArrayResponsables].direccion;
																data_caso += "&listaActividadesCaso[" + ind
																+ "].tareaParticularCasoSet[" + ind1
																+ "].responsablesTareas[" + index1
																+ "].casosEquiposCaso.equipoCaso.identificacion= " + responsablesDetalleCaso[indiceArrayResponsables].identificacion;																																																											
																
																/*data_caso += "&listaActividadesCaso[" + ind
																+ "].tareaParticularCasoSet[" + ind1
																+ "].responsablesTareas[" + index1
																+ "].responsableTareaPK.codigoMiembro="
																+ TIPOMIEMBROOTRO;
																data_caso += "&listaActividadesCaso[" + ind
																+ "].tareaParticularCasoSet[" + ind1
																+ "].responsablesTareas[" + index1
																+ "].otroResponsable="
																+ true;*/
															}
															if ($(tarea).find("input[name ^='txtCodigoTareaCaso']")
																	.val()) {
																data_caso += "&listaActividadesCaso["
																		+ ind
																		+ "].tareaParticularCasoSet["
																		+ ind1
																		+ "].responsablesTareas["
																		+ index1
																		+ "].responsableTareaPK.codigoTareaparticular="
																		+ $(tarea).find(
																				"input[name ^='txtCodigoTareaCaso']")
																				.val();
															}																														
														});

									});

				});

		// justificacion
		for (var i = 0; i < justificacionesFechaVencmimento.length; i++){
			if(EstaVacio(justificacionesFechaVencmimento[i].justificacion)){
				justificacionesFechaVencmimento.splice(i, 1);
				i--;
			}
		}
		for (var i = 0; i < justificacionesFechaVencmimento.length; i++){
			if(justificacionesFechaVencmimento[i].justificacion != null){

				data_caso += "&listaJustificaciones[" + i + "].tipoAccion=" + "Modificacion";
				data_caso += "&listaJustificaciones[" + i + "].campoModificado=" + CAMPOFECHALIMITE;
				data_caso += "&listaJustificaciones[" + i + "].usuarioModificacion=" + $("#idusercreacion").val();
				data_caso += "&listaJustificaciones[" + i + "].informacionEliminada="
						+ justificacionesFechaVencmimento[i].fechaActual;
				data_caso += "&listaJustificaciones[" + i + "].fechaAnterior="
						+ justificacionesFechaVencmimento[i].fechaActual;
				data_caso += "&listaJustificaciones[" + i + "].justificacion="
						+ justificacionesFechaVencmimento[i].justificacion;
				data_caso += "&listaJustificaciones[" + i + "].codigoCaso.codigo=" + $("#codigoParam").val();
				data_caso += "&listaJustificaciones[" + i + "].mensajeCorreo="
						+ justificacionesFechaVencmimento[i].mensajeCorreo;
				data_caso += "&listaJustificaciones[" + i + "].fechaActualizada="
						+ justificacionesFechaVencmimento[i].fechaActualizada;
			}
		}

		waitingDialog.show('');
		$.ajax({
			type : "POST",
			url : contexto + "/caso/guardarActividades",
			data : data_caso,
			dataType : "json",
			success : function(response){
				waitingDialog.hide();
				if(response.STATUS == "SUCCESS"){
					$("#messageExitoso").html("Se actualiz\u00f3 las actividades exitosamente.");
					$("#messageExitoso").show();
					setTimeout("limpiarMensajeExito();", 10000);
					updateOrderActividades();
					crearUrlRedireccionarA("addActividad");
					
				}else{
					$("#messageError").html("No se fue posible guardar el caso.");
					$("#messageError").show();
					setTimeout("limpiarMensajeError();", 10000);
				}
			},
			error : function(e){
				waitingDialog.hide();
			}
		});
	}
}

function cargarResponsablesTareasDetalle(panelActividad) {

	$(panelActividad).find("select[name ^='responsable']").each(function(){
		var codigosResponsables = [];
		var select = this;
		var abogadoSeleccionado = $(select).find('option');
		$(select).find("option").remove();
		$("#abogadosResponsablesTareas").find("option").each(function(ind1, dato1) {
			//Aparece la opcion Otros
			if (ind1 == 0) {
				var optionOtros = document.createElement("option");
				optionOtros.value = "-1";
				optionOtros.text = "Otros";
				select.add(optionOtros);				
			}
			var option = document.createElement("option");
			
			option.value = $(dato1).val();
			option.text = $(dato1).text();
			$(option).attr("miembro",$(dato1).attr("miembro"));
			select.add(option);
		});
		$(abogadoSeleccionado).each(function(ind1, option){
			codigosResponsables.push($(option).val());
		});
		$(select).select2("val", codigosResponsables);
	});
}
function cargarEstadosActividadesYTareas(panelActividad){
	$(panelActividad).find("select[name ^='cmbEstado']").each(function(){
		var select = this;
		var estadoSeleccionado = $(select).find('option:selected').val();
		$(select).find("option").remove();
		var option = document.createElement("option");

		option = document.createElement("option");
		option.value = CODIGO_ACTIVIDAD_PENDIENTE;
		option.text = DESC_ACTIVIDAD_PENDIENTE;
		select.add(option);

		option = document.createElement("option");
		option.value = CODIGO_ACTIVIDAD_COMPLETA;
		option.text = DESC_ACTIVIDAD_COMPLETA;
		select.add(option);

		// option = document.createElement("option");
		// option.value = CODIGO_ACTIVIDAD_VENCIDA;
		// option.text = DESC_ACTIVIDAD_VENCIDA;
		// select.add(option);

		$(select).val(estadoSeleccionado);
	});

}

function mostrarCamposEditables(panelActividad) {
	panelActividad.find("[name = 'datosActivdadesLabel']").attr("hidden", "hidden");
	panelActividad.find("[name *= 'nombreTarea']").removeAttr("hidden").addClass("form-control");
	panelActividad.find("[name *= 'detalleTarea']").removeAttr("hidden").addClass("form-control");	
//	panelActividad.find("[name *= 'responsable']").removeAttr("hidden").addClass("form-control");
	panelActividad.find("[name *= 'cmbEstadoTareas']").removeAttr("hidden").addClass("form-control");
	panelActividad.find("[name *= 'numeroDeDias']").removeAttr("hidden").addClass("form-control");
	panelActividad.find("[name *= 'numeroDeAlertas']").removeAttr("hidden").addClass("form-control");	
	panelActividad.find("[name *= 'vencimiento']").removeAttr("hidden").addClass("form-control");
	panelActividad.find(".select2-container").removeClass("select2-offscreen");
}

function habilitarCamposActividad(fila, botonEditar) {
	
	
	var panelActividad = $(botonEditar).closest("div[name=actividadParticular]");
	mostrarCamposEditables(panelActividad);
	panelActividad.find("[name = btnAlertas]").removeClass("hide");
	panelActividad.find("[name = btnCancelar]").removeClass("hide");
	$(botonEditar).closest("div[name=actividadParticular]").each(
			function(ind, formularioActividad) {

				if($(formularioActividad).find("input[name = txtEsActividadPropia]").val() == ESACTIVIDADPROPIASI) {
					var input = $("<input></input>").addClass("form-control").attr("id", "nombreActividad").attr(
							"name", "nombreActividad").attr("type", "text");
					input.val($(formularioActividad).find("label[name = nombreActividad]").text());
					var link = $(formularioActividad).find("label[name = nombreActividad]").parent();

					$(formularioActividad).find("label[name = nombreActividad]").parent().html("");
					link.append(input);
				}
				$(formularioActividad).find("input[name = txtCodigoActividadCaso]").removeAttr("disabled");
				$(formularioActividad).find("label[name = nombreActividad]").removeAttr("disabled");
				$(formularioActividad).find("input[name = txtNumerosdiasAntesActividad]").removeAttr("disabled");
				$(formularioActividad).find("input[name = txtNumeroNotificacionesActividad]").removeAttr("disabled");
				$(formularioActividad).find("input[name^='actividadVencimiento']").removeAttr("disabled");
				$(formularioActividad).find("select[name = cmbEstadoActividad]").removeAttr("disabled");

				$(formularioActividad).find("tr:gt(0)").each(function(ind1, tarea) {
//					if($(tarea).find("input[name ^='txtEsTareaPropia']").val() == ESTAREAPROPIASI) 
					if($(tarea).find("input[name ^='txtTareaPropia']").val() == ESTAREAPROPIASI){

						$(tarea).find("input[name ^='nombreTarea']").removeAttr("disabled");
					}
					$(tarea).find("input[name ^='txtCodigoTareaCaso']").removeAttr("disabled");
					$(tarea).find("input[name ^='detalleTarea']").removeAttr("disabled");
					$(tarea).find("select[name ^='responsable']").removeAttr("disabled");
					$(tarea).find("select[name ^='cmbEstadoTareas']").removeAttr("disabled");
					$(tarea).find("input[name ^='numeroDeDias']").removeAttr("disabled");
					$(tarea).find("input[name ^='numeroDeAlertas']").removeAttr("disabled");
					$(tarea).find("input[name ^='vencimiento']").removeAttr("disabled");

				});

			});
	var className = panelActividad.attr("class");
	if (className.indexOf('show') >= 0) {
		panelActividad.removeClass("show");
	}

	cargarResponsablesTareasDetalle(panelActividad);
	cargarEstadosActividadesYTareas(panelActividad);
	$(botonEditar).addClass("hide");
}

function cancelarCamposActividad(fila, btnCancelar) {
	crearUrlRedireccionarA("actTarea")
}

function modificarFechalimite(campoFechaLimite, esActividad){
	var codigoActividad = null;
	var noCambiarFecha = null;
	mensajeCorreo = "";
	if(esActividad){
		mensajeCorreo = "Actividad: "
				+ $(campoFechaLimite).closest("div[name=actividadParticular]").find("#nombreActividad").text();
		codigoActividad = $(campoFechaLimite).closest("div[name=actividadParticular]").find(
				"input[name = txtCodigoActividadCaso]").val();
		noCambiarFecha = validarFechasDesdeModificarActividad(campoFechaLimite);
	}else{
		mensajeCorreo = "Tarea: " + $(campoFechaLimite).closest("tr").find("input[name ^='nombreTarea']").val();
		codigoActividad = $(campoFechaLimite).closest("tr").find("input[name ^='txtCodigoTareaCaso']").val();
		noCambiarFecha = validarfechasTareas(campoFechaLimite);
	}
	var fechalimite = new Date($(campoFechaLimite).val());
	for (var i = 0; i < justificacionesFechaVencmimento.length; i++){
		if(justificacionesFechaVencmimento[i].codigoActividad == codigoActividad){
			var fechaActual = new Date(justificacionesFechaVencmimento[i].fechaActual);
			if(fechalimite > fechaActual){
				$("#modalConfFechaCaducidad").modal("show");
				fechaActualizada = $(campoFechaLimite).val();
				codigoActividadAModificar = codigoActividad;
			}else{
				justificacionesFechaVencmimento[i].justificacion = null;
				codigoActividadAModificar = codigoActividad;
				if(noCambiarFecha){
					noCambiarFechaActual();
				}
			}

		}
	}
}

var campoFelimite = null;
function guardarFechaLimiteActual(campoFechalimite, esActividad){

	var objetoJustificaciones = new Object();
	var existeJustificacion = true;
	campoFelimite = campoFechalimite;
	var codigoActividad = null;
	if(esActividad){

		codigoActividad = $(campoFechalimite).closest("div[name=actividadParticular]").find(
				"input[name = txtCodigoActividadCaso]").val();
	}else{
		codigoActividad = $(campoFechalimite).closest("tr").find("input[name ^='txtCodigoTareaCaso']").val();
	}

	for (var i = 0; i < justificacionesFechaVencmimento.length; i++){
		if(justificacionesFechaVencmimento[i].codigoActividad == codigoActividad){
			existeJustificacion = false;
		}
	}
	if(existeJustificacion){
		objetoJustificaciones.codigoActividad = codigoActividad;
		objetoJustificaciones.fechaActual = $(campoFechalimite).val();
		objetoJustificaciones.justificacion = null;
		justificacionesFechaVencmimento.push(objetoJustificaciones);
	}
}

function mostrarModalJustificacionFechaLimite(){

	$("#modalConfFechaCaducidad").modal("hide");
	$("#modalJustificacionFechaLimite").modal("show");

}

function validarJustificacion(){
	isError = true;
	limpiarFormularioJustificacionFechaLimite();

	if(EstaVacio($("#justificacionFechaLimite").val())){
		$("#justificacionFechaLimite").addClass("campoTextoError");
		$("#alertErrorCambiarFechaLimite").removeClass("hide");
		$("#alertErrorCambiarFechaLimite").html("Se debe ingresar una justificaci\u00f3n");
		isError = false;

	}

	return isError;

}
function limpiarFormularioJustificacionFechaLimite(){
	$("#alertErrorCambiarFechaLimite").addClass("hide");
	$("#justificacionFechaLimite").removeClass("campoTextoError");
	$("#alertErrorCambiarFechaLimite").html("");
}

function guardarJustificacion(){

	if (validarJustificacion()) {

		for (var i = 0; i < justificacionesFechaVencmimento.length; i++) {
			if (justificacionesFechaVencmimento[i].codigoActividad == codigoActividadAModificar){

				justificacionesFechaVencmimento[i].justificacion = $("#justificacionFechaLimite").val();
				justificacionesFechaVencmimento[i].mensajeCorreo = mensajeCorreo;
				justificacionesFechaVencmimento[i].fechaActualizada = fechaActualizada;
				justificacionesFechaVencmimento[i].codigoDelCaso = $("#codigoParam").val();
				cerrarModalJustificacionFechaLimite();
			}
		}

	}

}
function noCambiarFechaActual(){

	for (var i = 0; i < justificacionesFechaVencmimento.length; i++){
		if(justificacionesFechaVencmimento[i].codigoActividad == codigoActividadAModificar){

			$(campoFelimite).val(justificacionesFechaVencmimento[i].fechaActual);
		}
	}
	cerrarModalJustificacionFechaLimite();
}

function cerrarModalJustificacionFechaLimite(){

	limpiarFormularioJustificacionFechaLimite();
	$("#modalJustificacionFechaLimite").modal("hide");
	$("#justificacionFechaLimite").val("");

}
var divMensajeErrorActividad = null;
function validarFechasDesdeModificarActividad(campoFechaActividad){

	var fechaActividad = new Date($(campoFechaActividad).val());
	var noCambiarFecha = false;
	$(campoFechaActividad).closest("div[name=actividadParticular]").find("input[name^='vencimiento']").each(
			function(ind, dato){

				var fechaTarea = new Date($(dato).val());
				if(fechaActividad < fechaTarea){
					divMensajeErrorActividad = $(campoFechaActividad).closest("div[name=actividadParticular]").find(
							"div[name^='errorFechaMenorQueTareas']");
					$(campoFechaActividad).closest("div[name=actividadParticular]").find(
							"div[name^='errorFechaMenorQueTareas']").html(
							"Hay tareas con fechas mayores a la que se estaba ingresando.");
					$(campoFechaActividad).closest("div[name=actividadParticular]").find(
							"div[name^='errorFechaMenorQueTareas']").show();
					noCambiarFecha = true;
				}

			});

	if(noCambiarFecha){
		setTimeout("limpiarMensajeModificarActividad();", 10000);

	}

	return noCambiarFecha;
}

function limpiarMensajeModificarActividad(){
	$(divMensajeErrorActividad).hide();

}

function mostrarNuevaActividad(){
	$("#modal-asignarNombreDetalle").modal("show");
}

function agregarFormularioPrestamosModal(){
	var object = modalPrestamoAdicionar.cloneNode(true);
	object.style.display = 'block';
	var arrayBotones = object.getElementsByTagName('a');
	var boton = arrayBotones[arrayBotones.length - 1];
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border:0;");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		// document.getElementById("panelEquipoCaso").removeChild(this.parentNode.parentNode.parentNode);
		$(this).closest("div[name=formularioPrestamoNuevo]").remove();
	};
	$("#panelPrestamos").append(object);
	formatearCamposValores();
	// rulesForm();
}

function validarFormularioPrestamo(formulario){
	var existenDatos = false;
	formulario.find("input").each(function(index, optionData){
		if(optionData.value && optionData.value != "0"){
			existenDatos = true;
		}
	});
	formulario.find("select").each(function(index, optionData){
		if(optionData.value){
			existenDatos = true;
		}
	});
	return existenDatos;
}
function validarFormularioAbono(){
	var isValid = true;
	if(!validarCamposVacios()){
		isValid = false;
		$("#errorAdicionarAbono").html("Debe diligenciar la informacion del formulario.");
		$("#errorAdicionarAbono").removeClass("hide");
		setTimeout(function(){
			$("#errorAdicionarAbono").html("");
			$("#errorAdicionarAbono").addClass("hide");
		}, 10000);	
	}
	else if(!validarValoresAbono()){
		isValid = false;
		$("#errorAdicionarAbono").html("Los abonos no pueden superar el saldo");
		$("#errorAdicionarAbono").removeClass("hide");
		setTimeout(function(){
			$("#errorAdicionarAbono").html("");
			$("#errorAdicionarAbono").addClass("hide");
		}, 10000);	
	}
	
	return isValid;
}

function validarValoresAbono(){
	var isValid = true;
	
	var abonoCapital = Number($("#txtAbonoCapital").val());
	var abonoInteres = Number($("#txtAbonoIntereses").val());
	var saldoCapital = Number($("#txtValorSaldoCapital").val());
	var saldoInteres = Number($("#txtValorSaldoInteres").val());
	
	if(abonoCapital>saldoCapital){
		isValid = false;
	}
	if(abonoInteres>saldoInteres){
		isValid = false;
	}
		return isValid;
}

function validarCamposVacios(){
	if($("#txtFechaAbono").val() == ""){
		return false;
	}
	if($("#txtAbonoCapital").val() == ""){
		return false;
	}
	if($("#txtAbonoIntereses").val() == ""){
		return false;
	}
	return true;
}

function nuevoPrestamo(){
	var fechaPrestamoModal = null;
	var fechaPrestamoModalString = $("#txtFechaPrestamoModalAdicionar").val();
	if(fechaPrestamoModalString){
		var res = fechaPrestamoModalString.split("-");
		fechaPrestamoModal = new Date(res[0], res[1] - 1, res[2]);
	}
	if(validarFormularioPrestamo($("#formularioPrestamoNuevo"))){
		var data_caso = "_csrf=" + $("#_csrf").val();
		data_caso += "&nombreDeudor=" + $("#txtnombreDeudorModalAdicionar").val() + "&identificacion="
				+ $("#txtIdentificacionDeudorModalAdicionar").val() + "&descripcionPrestamo="
				+ $("#txtDescripcionPrestamoModalAdicionar").val() + "&monto="
				+ $("#txtValorPrestamoModalAdicionar").val() + "&porcentajeInteres="
				+ $("#txtPorcentajeInteresPrestamoModalAdicionar").val();
		if($("#txtTipoDeCuentaAdicionar").val())
			data_caso += "&tipoCuenta.codigoTipoCuenta=" + $("#txtTipoDeCuentaAdicionar").val();
		if($("#txtEntidadFinancieraAdicionar").val())
			data_caso += "&entidadFinaciera.codigoEntidadfinaciera=" + $("#txtEntidadFinancieraAdicionar").val();
		data_caso += "&numeroCuenta=" + $("#txtNumeroDeCuentaModalAdicionar").val() + "&intereses="
				+ $("#txtInteresesModalAdicionar").val() + "&saldo=" + $("#txtSaldoPrestamoModalAdicionar").val()
				+ "&titulo=" + $("#txtTituloValorModalAdicionar").val() + "&caso.codigo=" + $("#codigoParam").val()
				+ "&usuarioUltimaModificacion=" + $("#idusercreacion").val();
		if(fechaPrestamoModal) data_caso += "&fechaPrestamo=" + fechaPrestamoModal;

		waitingDialog.show('');
		$.ajax({
			type : "POST",
			url : contexto + "/detalleCaso/guardarPrestamo",
			data : data_caso,
			dataType : "json",
			success : function(response){
				waitingDialog.hide();
				if(response.STATUS == "SUCCESS"){
					$("#modal-adicionarPrestamo").modal("hide");
					$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3n exitosamente.");
					$("#messageExitoso").show();
					setTimeout("limpiarMensajeExito();", 10000);
					$("#txtnombreDeudorModalAdicionar").val("");
					$("#txtIdentificacionDeudorModalAdicionar").val("");
					$("#txtDescripcionPrestamoModalAdicionar").val("");
					$("#txtFechaPrestamoModalAdicionar").val("");
					$("#txtValorPrestamoModalAdicionar").val("");
					$("#txtPorcentajeInteresPrestamoModalAdicionar").val("");
					$("#txtTipoDeCuentaAdicionar").val("");
					$("#txtEntidadFinancieraAdicionar").val("");
					$("#txtNumeroDeCuentaModalAdicionar").val("");
					$("#txtInteresesModalAdicionar").val("");
					$("#txtSaldoPrestamoModalAdicionar").val("");
					$("#txtTituloValorModalAdicionar").val("");
					mostrarTablaPrestamo($("#codigoParam").val());
					$("#codigoCasoPrestamoModalAdicionar").val("");
					$("#codigoPrestamoModalAdicionar").val("");
					$("#cdusuariocreacionAdicionar").val("");

				}else{
					$("#errorAdicionarPrestamo").html("No fue posible guardar el prestamo para el caso.");
					$("#errorAdicionarPrestamo").removeClass("hide");
					setTimeout(function(){
						$("#errorAdicionarPrestamo").html("");
						$("#errorAdicionarPrestamo").addClass("hide");
					}, 10000);
				}
			},
			error : function(error){
				waitingDialog.hide();
				$("#errorAdicionarPrestamo").html("No fue posible guardar el prestamo para el caso.");
				$("#errorAdicionarPrestamo").removeClass("hide");
				setTimeout(function(){
					$("#errorAdicionarPrestamo").html("");
					$("#errorAdicionarPrestamo").addClass("hide");
				}, 10000);
			}
		});
	}else{
		$("#errorAdicionarPrestamo").html("No ha ingresado ningun campo del formulario.");
		$("#errorAdicionarPrestamo").removeClass("hide");
		setTimeout(function(){
			$("#errorAdicionarPrestamo").html("");
			$("#errorAdicionarPrestamo").addClass("hide");
		}, 10000);
	}

}
function nuevoAbono(){
	var saldoCapitalActual = $("#txtValorSaldoCapital").val();
	var saldoInteresActual = $("#txtValorSaldoInteres").val();
	var abonoCapital = $("#txtAbonoCapital").val();
	var abonoInteres = $("#txtAbonoIntereses").val();
	
	var fechaPrestamoModal = null;
	var fechaPrestamoModalString = $("#txtFechaAbono").val();
	if(fechaPrestamoModalString){
		var res = fechaPrestamoModalString.split("-");
		fechaPrestamoModal = new Date(res[0], res[1] - 1, res[2]);
	}
	if(validarFormularioAbono()){
		var data_caso = "_csrf=" + $("#_csrf").val();
		data_caso += "&prestamo.codigoPrestamo=" + $("#codigoPrestamoAdicionarAbono").val() + 
		"&abonoCapital="+$("#txtAbonoCapital").val() +
		"&abonoInteres="+$("#txtAbonoIntereses").val() +
		"&saldoCapital="+(saldoCapitalActual-abonoCapital)+
		"&saldoInteres="+(saldoInteresActual-abonoInteres)+
		 "&descripcion="+$("#txtDescripcionPrestamoModalAdicionarAbono").val()
		;
		
		if(fechaPrestamoModal) data_caso += "&fecha=" + fechaPrestamoModal;
		
		waitingDialog.show('');
		$.ajax({
			type : "POST",
			url : contexto + "/detalleCaso/guardarAbono",
			data : data_caso,
			dataType : "json",
			success : function(response){
				waitingDialog.hide();
					$("#modal-adicionarAbono").modal("hide");
					$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3n exitosamente.");
					$("#messageExitoso").show();
					setTimeout("limpiarMensajeExito();", 10000);
					mostrarTablaPrestamo($("#codigoParam").val());
					$("#codigoCasoPrestamoModalAdicionar").val("");
					$("#codigoPrestamoModalAdicionar").val("");
					$("#cdusuariocreacionAdicionar").val("");
					limpiarFormAbono();
			
			},
			error : function(error){
				waitingDialog.hide();
				$("#errorAdicionarAbono").html("No fue posible guardar el prestamo para el caso.");
				$("#errorAdicionarAbono").removeClass("hide");
				setTimeout(function(){
					$("#errorAdicionarAbono").html("");
					$("#errorAdicionarAbono").addClass("hide");
				}, 10000);
			}
		});
	}
	
}

function limpiarFormAbono(){
	var formAbono = document.getElementById("formularioAbonoNuevo");
	formAbono.reset();
}

function calcularSaldoCapitalInteres(prestamo){
	if(!prestamo)
		prestamo = $("#codigoPrestamoAdicionarAbono").val();
	
	var fechaPrestamo = $("#txtFechaPrestamoModalAdicionarAbono").val().split("-");
	var fechaPrestamoDate = new Date(fechaPrestamo[0], fechaPrestamo[1] - 1, fechaPrestamo[2]);
	
	var urlFechas = "fechaPrestamo="+fechaPrestamoDate;
	
	
	if($("#txtFechaAbono").val()){
		var fechaAbono = $("#txtFechaAbono").val().split("-");
		var fechaAbonoDate = new Date(fechaAbono[0], fechaAbono[1] - 1, fechaAbono[2]);
		urlFechas+="&fechaAbono="+fechaAbonoDate;
	}
	
	
	
	$.getJSON( contexto + "/detalleCaso/getUltimoAbono?codigoPrestamo="+prestamo+"&"+urlFechas, function( data ) {
			var abono = data.abono;
			if(abono.saldoCapital){
				$("#txtValorSaldoCapital").val(abono.saldoCapital);
				$("#txtValorSaldoInteres").val(abono.saldoInteres);
			}else{
				$("#txtValorSaldoCapital").val($("#txtValorPrestamoModalAdicionarAbono").val());
				$("#txtValorSaldoInteres").val(0);
			}
			calcularInteresAbono(abono.diasTranscurridos);
		});
}

function calcularInteresAbono(diasTranscurridos){
	var saldoInteresActual = Number($("#txtValorSaldoInteres").val());
	var interes = $("#txtPorcentajeInteresPrestamoModalAdicionarAbono").val()/100;
	var interesDiario = interes/30;
	var totalInteresAcumulado = interesDiario*diasTranscurridos;
	var valorInteres = totalInteresAcumulado * $("#txtValorSaldoCapital").val();
	$("#txtValorSaldoInteres").val((saldoInteresActual+valorInteres).toFixed(0));
	}


function calcularInteresPrestamo(diasTranscurridos, prestamo){
	if(!prestamo.saldoCapital){
		calcularInteresPrestamoSinAbono(true);
	}else{
		var saldoInteresesPagados = prestamo.interesesPagados;
		var interes = $("#txtPorcentajeInteresPrestamoModal").val()/100;
		var interesDiario = interes/30;
		var totalInteresAcumulado = interesDiario*diasTranscurridos;
		var valorInteres = Math.round(totalInteresAcumulado * prestamo.saldoCapital);
		$("#txtInteresesModal").val((valorInteres+saldoInteresesPagados+prestamo.saldoInteres).toFixed(0));
		$("#txtSaldoTotal").val(valorInteres+prestamo.saldoCapital);	
	}
	
	
}

function calcularInteresPrestamoSinAbono(modificar){
	
	if(modificar){
		var strFechaPrestamo = $("#txtFechaPrestamoModal").val();
		var porcentajeInteres = $("#txtPorcentajeInteresPrestamoModal").val();
		var valorPrestamo = $("#txtValorPrestamoModal").val();
	}else{
		var strFechaPrestamo = $("#txtFechaPrestamoModalAdicionar").val();
		var porcentajeInteres = $("#txtPorcentajeInteresPrestamoModalAdicionar").val();
		var valorPrestamo = $("#txtValorPrestamoModalAdicionar").val();	
	}
	
	
	if(!strFechaPrestamo || !porcentajeInteres || !valorPrestamo){
		return;
	}
	
	var fechaSplit = strFechaPrestamo.split("-");
	var fechaPrestamo = new Date(fechaSplit[0], fechaSplit[1] - 1, fechaSplit[2]);
	
	var interes = (porcentajeInteres/100).toFixed(2);
	
	
	var diferenciaDias = Math.floor((new Date().getTime()-fechaPrestamo.getTime())/(1000*60*60*24));
	
	
	
	
	var interesDiario = interes/30;
	var totalInteresAcumulado = interesDiario*diferenciaDias;
	var valorPrestamo = parseInt(valorPrestamo);
	
	var valorInteres = parseFloat((totalInteresAcumulado * valorPrestamo).toFixed(0));
	
	if(modificar){
		$("#txtInteresesModal").val(valorInteres);
		$("#txtSaldoPrestamoModal").val(valorPrestamo);
		$("#txtSaldoTotal").val(valorPrestamo+valorInteres);	
	}else{
		$("#txtInteresesModalAdicionar").val(valorInteres);
		$("#txtSaldoPrestamoModalAdicionar").val(valorPrestamo);
		$("#txtSaldoTotalAdicionar").val(valorPrestamo+valorInteres);
	}
	
	
	
}


function limpiarPrestamo(){
	$("#txtnombreDeudorModalAdicionar").val("");
	$("#txtIdentificacionDeudorModalAdicionar").val("");
	$("#txtDescripcionPrestamoModalAdicionar").val("");
	$("#txtFechaPrestamoModalAdicionar").val("");
	$("#txtValorPrestamoModalAdicionar").val("");
	$("#txtPorcentajeInteresPrestamoModalAdicionar").val("");
	$("#txtTipoDeCuentaAdicionar").val("");
	$("#txtEntidadFinancieraAdicionar").val("");
	$("#txtNumeroDeCuentaModalAdicionar").val("");
	$("#txtInteresesModalAdicionar").val("");
	$("#txtSaldoPrestamoModalAdicionar").val("");
	$("#txtTituloValorModalAdicionar").val("");
	$("#codigoCasoPrestamoModalAdicionar").val("");
	$("#codigoPrestamoModalAdicionar").val("");
	$("#cdusuariocreacionAdicionar").val("");

}

function mostrarFechaCaducidadDetalleCaso(){
	var diasPosponerCaducidad = diasAdicionarCaducidad?diasAdicionarCaducidad:0;
	
	fechaCaduci = $("#finFechaDeLosHechosMod").val();
	fechaInicioHechos = new Date($("#fechaDeLosHechosMod").val());
	fechaFinHechos = new Date($("#finFechaDeLosHechosMod").val());
	if(fechaInicioHechos <= fechaFinHechos){
		$("#finFechaDeLosHechosMod").removeClass("campoTextoError");
		$("#errorFechaFinDelosHechosMod").hide();
		var fecha = fechaCaduci.split("-");
		var aniosCaducidad = $("#aniosCaducidadParam").val();
		ano = parseInt(fecha[0]) + parseInt(aniosCaducidad);
		$("#fechaDeCaducidadMod").val(ano + "-" + fecha[1] + "-" + fecha[2]);
		
		var tpmDateCaducidad = new Date(ano,fecha[1]-1,fecha[2]);
		
		var dateCaducidad = new Date(tpmDateCaducidad.getTime()+(diasPosponerCaducidad*24*60*60*1000)); //Posponemos la fecha de caducidad segun el parametros
		console.log(new Date(ano,fecha[1]-1,fecha[2]));
		for(var i=0;i<disabledDates.length;i++){
			if(disabledDates[i] == dateCaducidad.getTime()){
				dateCaducidad.setDate(dateCaducidad.getDate()+1);
				i=0;
			}
		}
		$("#fechaDeCaducidadMod").val(formatDate(dateCaducidad));
	}else{
		$("#finFechaDeLosHechosMod").val("");
		$("#fechaDeCaducidadMod").val("");
		$("#errorFechaFinDelosHechosMod").html("No se puede ingresar una fecha menor a la del inicio del los hechos");
		$("#errorFechaFinDelosHechosMod").show();
		$("#finFechaDeLosHechosMod").addClass("campoTextoError");
	}

}

function validarFechaInicioHechosDetalleCaso(){
	fechaInicioHechos = new Date($("#fechaDeLosHechosMod").val());
	fechaFinHechos = new Date($("#finFechaDeLosHechosMod").val());
	if($("#finFechaDeLosHechosMod").val()){
		if(fechaInicioHechos <= fechaFinHechos){
			$("#fechaDeLosHechosMod").removeClass("campoTextoError");
			$("#errorFechaFinDelosHechosMod").hide();
		}else{
			$("#fechaDeLosHechosMod").val("");
			$("#errorFechaFinDelosHechosMod").html("No se puede ingresar una fecha mayor a la del fin del los hechos");
			$("#errorFechaFinDelosHechosMod").show();
			$("#fechaDeLosHechosMod").addClass("campoTextoError");
		}
	}
}

function obtenerFechasAnteriores(){
	fechaInicialAnterior = $("#fechaDeLosHechosMod").val();
	fechaFinalAnterior = $("#finFechaDeLosHechosMod").val();
	fechaCaducidadAnterior = $("#fechaDeCaducidadMod").val();
}

function validarCambiosFechas(){
	if(fechaInicialAnterior != $("#fechaDeLosHechosMod").val()
			|| fechaFinalAnterior != $("#finFechaDeLosHechosMod").val()
			|| fechaCaducidadAnterior != $("#fechaDeCaducidadMod").val()){
		return true;
	}else{
		return false;
	}
}

function cancelarActualizacionFechas(){
	$("#fechaDeLosHechosMod").val(fechaInicialAnterior);
	$("#finFechaDeLosHechosMod").val(fechaFinalAnterior);
	$("#fechaDeCaducidadMod").val(fechaCaducidadAnterior);
}


function validarTipoMiembro(select){
	var tipoMiembro = select.value;
	var divForm = $("#modal-adicionarMiembroEquipo [name = formularioEquipoCasoNuevoMiembroModal]").not(".hide"); 
	if(tipoMiembro == 7)
		divForm.find("#txtObservacionesNuevoMiembro").closest(".row").show();
	else
		divForm.find("#txtObservacionesNuevoMiembro").closest(".row").hide();
	
}


function getActividadesInfo(){
	$("[name=nombreActividad]").not(".form-control").each(function(index,element){
		
		var vencimientoActividad = $(element).closest("[name=actividadParticular]").find("[name*=actividadVencimiento]").val();
		
		var nombre = $(element).text();
		var fechaVencimiento = getDateFormat(vencimientoActividad);
		actividadesArray.push({
			nombre : nombre,
			fechaVencimiento : fechaVencimiento
		});
		
	});
	console.log(actividadesArray);
}


function validarActividadConciliacionPrejudicialMod(){
	var isValido = false; 
	$.each(actividadesArray,function(index,data){
		if(data.nombre == _nombreActividadPrej){
			isValido = true;;
		}
	});
	if(!isValido){
		$("#txtFechaPrejudicialMod").val("");
		$("#messageErrorDetalleCaso").html("No existe la actividad Audiencia de conciliación prejudicial").show();
	}else{
		$("#txtFechaPrejudicialMod").removeClass("campoTextoError");
		$("#messageErrorDetalleCaso").html("").hide();
	}
	return isValido;
}

function validarFechaPrejudicialMod(){
	var valido = true;
	if($("#txtFechaPrejudicialMod").val()==""){
		diasAdicionarCaducidad = 0;
		valido = false;
	}else if(validarActividadConciliacionPrejudicialMod() && validarDiferenciaFechaSolicitudPrejAudienciaMod()){
		if($("#fechaDeCaducidadMod").val()==$("#txtFechaPrejudicialMod").val()){
			$("#modal-AdvertenciaDemandaMismaDiaAudiencia").modal("show");
		}
		valido = true;
	}
	mostrarFechaCaducidadDetalleCaso();
	return valido;
}

function validarDiferenciaFechaSolicitudPrejAudienciaMod(){
	var fechaActividad;
	var success = true;
	$(actividadesArray).each(function(index,actividad){
		if(actividad.nombre == _nombreActividadPrej){
			if(actividad.fechaVencimiento==""){
				// mostrarErrorFechaSolicitudPrejudicial("Antes debe ingresar la fecha de caducidad de la actividad "+_nombreActividadPrej);
				$("#txtFechaPrejudicialMod").val("");
				success=false;
			}
			var fechaActividad =  actividad.fechaVencimiento;
			var fechaSolicitudPrej = getDateFormat($("#txtFechaPrejudicialMod").val());
			var diferenciaMilis = fechaActividad.getTime()-fechaSolicitudPrej.getTime();
			var dias = diferenciaMilis/(24*60*60*1000);
			if(dias>90)
				diasAdicionarCaducidad = 90;
			else if(dias>0)
				diasAdicionarCaducidad = dias;
			else if(dias<0){
				dias=0;
				
				$("#messageErrorDetalleCaso").html("La fecha de vencimiento de la actividad de "+_nombreActividadPrej+" es menor a la fecha de presentación de solicitud prejudicial").show();
				$("#txtFechaPrejudicialMod").val("");
				success = false;
			}
			mostrarFechaCaducidadDetalleCaso();
	}
	});
	return success;
}



var radicadosAcumuladosEdit = {
		// table : $("#formularioRadicado #tableRadicadosAcumulados"),
		radicados : undefined, // Esto lo consultamos en el ready
		init : function(context){
			$(context).find("input#txtAutoCompleteRadicados").autocomplete({
				source : radicadosAcumulados.radicados,
				minLength: 3
			});
		},
		remove : function(tr){
			var table = this.getTable(tr);
			tr.remove();
			if(table[0].rows.length <= 1){
				table.hide();
			}
		},
		add: function(context){
			var table = this.getTable(context);
			table.show();
			var firstRowHtml = $("#tableRadicadosAcumulados tbody tr:eq(0)").clone();
			table.find("tbody").append(firstRowHtml);
			table.find("input#txtAutoCompleteRadicados").last().autocomplete({
				source : radicadosAcumulados.radicados,
				minLength: 3
			});
		},
		getTable : function(context){
			return $(context).closest("#formularioRadicado").find("#tableRadicadosAcumulados");
		}
};