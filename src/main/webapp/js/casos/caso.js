var $clientForm;
var formularioEquipoCaso = null;
var formularioEquipoCasoNuevo = null;
var formularioaActividad = null;
var formularioPrestamos = null;
var formularioOtroResponsable = null;
var formularioOtroResponsableDetalleCaso = null;
var contFilasTareasAdicionales = 0;
var filaActividad = 0;
var actividadesArray = new Array();
var countActividades = 0;
var filaTareaActividad = 0;
var filasActividadesParticulares = new Array();
var countActividadesParticulares = 0;
var codigoActivad = 10000000;
var mensaje = '<div class="alert alert-danger" id="messageErrorAactividad" name="messageError" style="display: none">How quickly daft jumping zebras vex. <a class="alert-link" href="#">Alert Link</a>.</div>';
var metodoOnchage = 'onchange="validarfechasTareas(this)"';
var metodoOnchageActividad = 'onchange="validarFechasDesdeActividad(this)"';
var formularioNuevoCaso = null;
var botonPresionado = null;
var filaABorrar = null;
var filaABorrarTarea = null;
var responsables = new Array();
var responsablesDetalleCaso = new Array();
var CODIGOMIEMBROOTRO = 4;
var CONTACTO_NO = "N";
var CONTACTO_SI = "S";
var JURIDICA_SI = "S";
var JURIDICA_NO = "N";
var TITLE_TAREAS = "Tareas";
var TITLE_NUMERO_DIAS = "N\u00FAmero de d\u00EDas";
var TITLE_NUMERO_NOTIFICACIONES = "N\u00FAmero de notificaciones";
var CONF_ALL_ALARM = "ALL";
var CONF_ONE_ALARM = "ONE";
var areaAlarmas = null;
var estadoActividadAnterior = null;
var actividadCompletada = "S";
var actividadPendiente = "N";
var actividadVencida = "V";
var selectEstadoActividad = null;
var formularioNuevosMiembros = null;
var tituloNotificaciones = $('#tituloNotificaciones').val();
var tituloNumeroDias = $('#tituloNumeroDias').val();
var tituloFechaVencimiento = $('#tituloFechaVencimiento').val();
var tituloEstado = $('#tituloEstado').val();
var disabledDates = [];
var _nombreActividadPrej = "Audiencia de Conciliación Prej";
var diasAdicionarCaducidad = 0;

function irNuevoCaso() {
	// document.location.href = "nuevoCaso";
	$("#modal-nuevoCaso").modal("show");
}
// se carga el formulario de equipo caso y de la actividad limpio para poder
// clonarlo
function cargarFormularios() {
	if(formularioEquipoCaso == null){
		formularioDemandados = $("#modal-nuevoCaso [name = formularioEquipoCaso]")[0].cloneNode(true);
		formularioEquipoCaso = $("#modal-nuevoCaso [name = formularioEquipoCaso]")[1].cloneNode(true);
	}
	if(formularioaActividad == null){
		formularioaActividad = $("[name = actividadParticular]")[0].cloneNode(true);
	}
	if(formularioPrestamos == null){
		if($("#modal-nuevoCaso [name = prestamos]").length)
			formularioPrestamos = $("#modal-nuevoCaso [name = prestamos]")[0].cloneNode(true);
	}
	if(formularioOtroResponsable == null){
		formularioOtroResponsable = $("#ingresarOtroResponsable")[0].cloneNode(true);
	}
	
	/*if(formularioOtroResponsableDetalleCaso == null){
	formularioOtroResponsableDetalleCaso = $("#ingresarOtroResponsableDetalleCaso")[0].cloneNode(true);
	}*/
	
	formularioNuevoCaso = $("#modal")[0].cloneNode(true);

}

function cargarFormulariosEquipoNuevo(){
	if(formularioEquipoCasoNuevo == null){
		formularioDemandadosNuevo = $("#modal-adicionarMiembroEquipo [name = formularioEquipoCasoNuevoMiembro]")[0]
				.cloneNode(true);
		formularioEquipoCasoNuevo = $("#modal-adicionarMiembroEquipo [name = formularioEquipoCasoNuevoMiembroModal]")[0]
				.cloneNode(true);
	}

	if(formularioNuevosMiembros == null){
		if($("#modal-adicionarMiembroEquipo").length)
			formularioNuevosMiembros = $("#modal-adicionarMiembroEquipo #nuevosMiembrosArea")[0].cloneNode(true);
	}

	var abogadosAsignados = $("#abogadosDelCaso option");

	$.each(abogadosAsignados, function(index, option){
		var optionRemove = $("#cmbMiembroAbogadoNuevoMiembro option[value=" + option.value + "]");
		optionRemove.remove();
	});

}

// metodo para agregar formulario bienes afectados
function agregarFormularioBienesAfectados(){
	var formularioBienesAfectados = $("[name = formularioBienesAfectados]")[0];
	var object = formularioBienesAfectados.cloneNode(true);
	object.style.display = 'block';
	var boton = object.getElementsByTagName('a')[0];
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border=0;");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		document.getElementById("panelBienesAfectados").removeChild(this.parentNode.parentNode.parentNode);
	};
	$("#separadorFormularioBienesAfectados").before(object);
}
// metodo para mostrar el formulario de radicados acumlados
function mostrarCamposRadicadoAcumlado(campo){
	var checkBox = campo;
	if(checkBox.checked){
		checkBox.parentNode.parentNode.parentNode.parentNode.nextElementSibling.style.display = 'block';
	}else{

		checkBox.parentNode.parentNode.parentNode.parentNode.nextElementSibling.style.display = 'none';
	}
}
// metodo para agregar el formulario del radicado
function agregarFormularioRadicado(){
	var formularioBienesAfectados = $("[name = formularioRadicado]")[0];
	var object = formularioBienesAfectados.cloneNode(true);
	object.style.display = 'block';
	var boton = object.getElementsByTagName('a')[0];
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border:0;");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		document.getElementById("panelRadicado").removeChild(this.parentNode.parentNode.parentNode);
	};
	$("#separadorFormularioRadicado").before(object);
	$(object).find("#tableRadicadosAcumulados tbody tr:last").remove();
	$(object).find("#tableRadicadosAcumulados").hide();
	
	radicadosAcumulados.init(object)
	
}
// metodo para solo mostrar el campo nombre cuando se tiene una persona juridica
function mostrarPersonaJuridica(campoTipoPersona){
	var selector = campoTipoPersona;
	if(selector.value == "Persona Juridica"){
		selector.parentNode.parentNode.nextElementSibling.style.display = 'none';
		selector.parentNode.parentNode.nextElementSibling.nextElementSibling.style.display = 'block';
	}else{
		selector.parentNode.parentNode.nextElementSibling.style.display = 'block';
		selector.parentNode.parentNode.nextElementSibling.nextElementSibling.style.display = 'none';
	}
}
// metodo para agregar formulario del equipo del caso

function agregarFormularioEquipoCaso(){
	var alturaE = $("#equipoDelCasoNuevoCaso > div[name=formularioEquipoCaso]").last().height();
	var object = formularioEquipoCaso.cloneNode(true);

	$(object).removeClass("hide");
	$(object).find("select[name=txtPaisMiembro]").each(function(ind, selectPais){
		$(selectPais).val(CODIGOCOLOMBIA);
	});
	$(object).find("select[name=txtPaisAdicionarDemandado]").each(function(ind, selectPais){
		$(selectPais).val(CODIGOCOLOMBIA);
	});
	$("#separadorFormularioEquipoCaso").before(object);

	setTimeout(function(){
		$('#scrollNuevoCaso').animate({
			scrollTop : $('#equipoDelCasoNuevoCaso').height() + (alturaE / 2)
		}, 700);
	}, 50);
}

function validarEmail(email){
	var pattern = new RegExp(
			/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i);

	return pattern.test(email);
}

function agregarFormularioEquipoCasoNuevoMiembro() {
	var object = formularioEquipoCasoNuevo.cloneNode(true);
	$(object).removeClass("hide");
//	$(object).find("select[name *= txtPaisMiembro]").each(function(ind, selectPais){
//		$(selectPais).val(CODIGOCOLOMBIA);
//	});
	$("#panelEquipoCasoNuevos").append(object);
}

function eliminarDemandadoYVictimas(botonEliminar) {
	$(botonEliminar).closest("div[name=formularioEquipoCaso]").remove();
}

function eliminarNuevoMiembro(botonEliminar) {
	$(botonEliminar).closest("div[name=formularioEquipoCasoNuevoMiembroModal]").remove();
}

function eliminarDemandadoNuevo(botonEliminar) {
	$(botonEliminar).closest("div[name=formularioEquipoCasoNuevoMiembro]").remove();
}

// metodo para agregar formulario demandado
function agregarFormularioDemandados(){
	var object = formularioDemandados.cloneNode(true);
	$(object).removeClass("hide");
	$(object).find("select[name=txtPaisMiembro]").each(function(ind, selectPais){
		$(selectPais).val(CODIGOCOLOMBIA);
	});
	$("#separadorFormularioDemandados").before(object);
}

function agregarFormularioNuevoDemandado(){
	var object = formularioDemandadosNuevo.cloneNode(true);
	$(object).removeClass("hide");
	$(object).find("select[name *= txtPaisMiembro]").each(function(ind, selectPais){
		$(selectPais).val(CODIGOCOLOMBIA);
	});
//	$(object).find("select[name *= txtPaisAdicionarDemandado]").each(function(ind, selectPais){
//		$(selectPais).val(CODIGOCOLOMBIA);
//	});
	$("#panelDemandadoNuevos").append(object);
}
// metodo para agregar otro campo telefono
function agregartelefono(boton, numeroTelefono, codigoTelefono){
	formularioTelefono = $(boton).closest("div[name=formularioTelefono]")[0];
	var object = formularioTelefono.cloneNode(true);
	$(object).find("input[name^='txtTelefonoMiembro']").val("");
	if(numeroTelefono){
		$(object).find("input[name^='txtTelefonoMiembro']").val(numeroTelefono);
		$(object).find("input[name=txtCodigoTelefono]").val(codigoTelefono);
	}
	boton = $(object).find("a")[0];
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border:0;");
	boton.setAttribute("title", "Eliminar Teléfono");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		formularioTelefono.parentNode.removeChild(this.parentNode.parentNode);
		if(codigoTelefono){
			codigoTelefonosEliminado(codigoTelefono);
		}
	};
	formularioTelefono.parentNode.appendChild(object);
}

function agregartelefonoGeneral(boton, formulario, numeroTelefono, codigoTelefono){
	var object = null;
	if(formulario == 1){
		formularioTelefono = $(boton).closest("div[name=formularioTelefonoNuevo]")[0];
		object = formularioTelefono.cloneNode(true);
		$(object).find("input[name^='txtTelefonoMiembroNuevoMiembro']").val("");
	}else if(formulario == 2){
		formularioTelefono = $(boton).closest("div[name=formularioTelefonoCasoNuevoCaso]")[0];
		object = formularioTelefono.cloneNode(true);
		$(object).find("input[name^='txtTelefonoMiembroCaso']").val("");
	}

	boton = $(object).find("a")[0];
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border:0;");
	boton.setAttribute("title", "Eliminar Teléfono");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		formularioTelefono.parentNode.removeChild(this.parentNode.parentNode);
		if(codigoTelefono){
			codigoTelefonosEliminado(codigoTelefono);
		}
	};
	formularioTelefono.parentNode.appendChild(object);
}

function agregarCelularGeneral(boton, numeroCelular, codigoCelular){
	var object = null;
	if(numeroCelular == 1){
		formularioCelular = $(boton).closest("div[name=formularioCelularNuevo]")[0];
		object = formularioCelular.cloneNode(true);
		$(object).find("input[name^='txtCelularMiembroNuevoMiembro']").val("");
	}else if(numeroCelular == 2){
		formularioCelular = $(boton).closest("div[name=formularioCelularCasoNuevo]")[0];
		object = formularioCelular.cloneNode(true);
		$(object).find("input[name^='txtCelularMiembroCaso']").val("");
	}

	boton = $(object).find("a")[0];
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border:0;");
	boton.setAttribute("title", "Eliminar Celular");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		formularioCelular.parentNode.removeChild(this.parentNode.parentNode);
		if(codigoCelular){
			codigoCelularEliminado(codigoCelular);
		}
	};
	formularioCelular.parentNode.appendChild(object);

}

// metodo para agregar otro campo celular
function agregarCelular(boton, numeroCelular, codigoCelular){
	// var formularioCelular = boton.parentNode.parentNode;
	formularioCelular = $(boton).closest("div[name=formularioCelular]")[0];
	var object = formularioCelular.cloneNode(true);
	$(object).find("input[name^='txtCelularMiembro']").val("");
	if(numeroCelular){
		$(object).find("input[name^='txtCelularMiembro']").val(numeroCelular);
		$(object).find("input[name^='txtCodigoCelular']").val(codigoCelular);
	}
	boton = $(object).find("a")[0];
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border:0;");
	boton.setAttribute("title", "Eliminar Celular");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		formularioCelular.parentNode.removeChild(this.parentNode.parentNode);
		if(codigoCelular){
			codigoCelularEliminado(codigoCelular);
		}
	};
	formularioCelular.parentNode.appendChild(object);

}
// metodo para agregar otro campo correo
function agregarCorreo(boton, correo, codigoCorreo){
	// var formularioCorreo = boton.parentNode.parentNode;
	formularioCorreo = $(boton).closest("div[name=formularioCorreo]")[0];
	var object = formularioCorreo.cloneNode(true);
	$(object).find("input[name^='txtCorreoMiembro']").val("");
	if(correo){
		$(object).find("input[name^='txtCorreoMiembro']").val(correo);
		$(object).find("input[name^='txtCodigoCorreo']").val(codigoCorreo);
	}
	// var boton = object.getElementsByTagName('a')[0];
	boton = $(object).find("a")[0];
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border:0;");
	boton.setAttribute("title", "Eliminar Correo");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		formularioCorreo.parentNode.removeChild(this.parentNode.parentNode);
		if(codigoCorreo){
			codigoCorreoEliminado(codigoCorreo);
		}
	};
	formularioCorreo.parentNode.appendChild(object);

}

function agregarCorreoGeneral(boton, correo, codigoCorreo){

	if(correo == 1){
		formularioCorreo = $(boton).closest("div[name=formularioCorreoNuevo]")[0];
		var object = formularioCorreo.cloneNode(true);
		$(object).find("input[name^='txtCorreoMiembroDemandadoNuevoMiembro']").val("");
	}else if(correo == 2){
		formularioCorreo = $(boton).closest("div[name=formularioCorreoCasoNuevo]")[0];
		var object = formularioCorreo.cloneNode(true);
		$(object).find("input[name^='txtCorreoMiembroCaso']").val("");
	}
	// var formularioCorreo = boton.parentNode.parentNode;

	// var boton = object.getElementsByTagName('a')[0];
	boton = $(object).find("a")[0];
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border:0;");
	boton.setAttribute("title", "Eliminar Correo");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		formularioCorreo.parentNode.removeChild(this.parentNode.parentNode);
		if(codigoCorreo){
			codigoCorreoEliminado(codigoCorreo);
		}
	};
	formularioCorreo.parentNode.appendChild(object);

}

// metodo para agregar otro formulario actividad particular
function agregarFormularioActividadParticular()// boton)
{
	// var nuevasActividades = boton.parentNode.parentNode.parentNode;
	var object = formularioaActividad.cloneNode(true);
	var boton = object.getElementsByTagName('a')[0];
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border:0;");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		// nuevasActividades.parentNode.removeChild(this.parentNode.parentNode.parentNode);
		document.getElementById("actividades").removeChild(this.parentNode.parentNode.parentNode);
	};
	// nuevasActividades.parentNode.appendChild(object);
	$("#actividades").append(object);

	// cargarResponsableTareas(object);
	// cargarAbogado(object);

}
// metodo para agregar otro formulario tarea particular
function agregarFormularioTareaParticular(boton){

	// clonarCampoTareaParticular($("#btnTareas"));

	// var nuevasActividades
	// =$(boton).closest("div[name=actividadParticular]").find("div[name=prueba]")[0];
	// //add() $("#campoTarea")[0];
	var nuevasActividades = $(boton).closest("div[name=actividadParticular]").find("div[name=campoTarea]")[0]; // add()
	// $("#campoTarea")[0];
	var object = nuevasActividades.cloneNode(true);
	object.style.display = 'block';
	// $(object).find("div[name=campoTarea]")[0].style.display='block';
	var boton = $(object).find("a[name=btTareas]")[0];
	$(object).find("div[name=messageError]").hide();
	// var boton = object.getElementsByTagName('a')[0];
	// boton.attr("class", "btn btn-danger btn");
	// boton.attr("style", "background-color: red;");
	// boton.first("class", "glyphicon glyphicon-minus");
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border:0;");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		nuevasActividades.parentNode.removeChild(this.parentNode.parentNode.parentNode);
	};
	nuevasActividades.parentNode.appendChild(object);

	cargarResponsableTareas(object);

}
// metodo para agregar varios prestamos
function agregarFormularioPrestamos(){
	var object = formularioPrestamos.cloneNode(true);
	object.style.display = 'block';
	var arrayBotones = object.getElementsByTagName('a');
	var boton = arrayBotones[arrayBotones.length - 1];
	boton.setAttribute("class", "btn btn-danger btn btn-circle .btn-xs");
	boton.setAttribute("style", "background-color: red;border:0;");
	boton.firstElementChild.setAttribute("class", "glyphicon glyphicon-minus");
	boton.onclick = function borrarFormulario(){
		// document.getElementById("panelEquipoCaso").removeChild(this.parentNode.parentNode.parentNode);
		$(this).closest("div[name=prestamos]").remove();
	};
	$("#separadorBloquePrestamos").before(object);
	formatearCamposValores(object);
	// rulesForm();
}
// metodo para guardar el caso

function getTipoRadicoByCodigo(codigo){
	if (codigo == 1)
		return "NA"
	if (codigo == 2)
		return "GJA"
	if (codigo == 3)
		return "Externo"
}

function doAjaxPostAdd() {
	fecha = $("#txtFechaDeLosHechos").val().split("-");
	var fechaHechos = new Date(fecha[0], fecha[1] - 1, fecha[2]);
	var fechaFinHecho = "";
	var fechaCaducidad = "";
	var fechaPrejudicial = "";
	if($("#txtFinFechaDeLosHechos").val()!=""){
		fecha = $("#txtFinFechaDeLosHechos").val().split("-");
		fechaFinHecho = new Date(fecha[0], fecha[1] - 1, fecha[2]);
		
		fecha = $("#txtFechaDeCaducidad").val().split("-");
		fechaCaducidad = new Date(fecha[0], fecha[1] - 1, fecha[2]);
	}
	
	if($("#txtFechaPrejudicial").val()){
		var fechaTmp = $("#txtFechaPrejudicial").val().split("-");
		fechaPrejudicial = new Date(fechaTmp[0], fechaTmp[1] - 1, fechaTmp[2]);
		
	}
	
	
	
	var data_caso = "_csrf=" + $("#_csrf").val();
	// bloque para ingresar el detalle del caso
	
	var tipoRadicadoCodigo = $("#tipoRadicado").val();
	var tipoRadicado = getTipoRadicoByCodigo(tipoRadicadoCodigo);
	data_caso += "&tipoRadicado=" + getTipoRadicoByCodigo(tipoRadicadoCodigo);
	
	if(tipoRadicado == "GJA")
		data_caso += "&radicadoRelacionado=" + $("#txtAutoCompleteRadicados").val();
	else if(tipoRadicado == "Externo")
		data_caso += "&radicadoRelacionado=" + $("#txtRadicadoAsociado").val();
	
	if(fechaPrejudicial){
		data_caso += "&fechaSolicitudPrejudicial=" + fechaPrejudicial;
	}
	
	
	data_caso += "&estadoCaso.codigo=" + $("#txtEstadoCaso").val() + "&tipoCaso.codigo=" + $("#txtTipoCaso").val()
			+ "&nombre=" + $("#txtNombreCaso").val() + "&fechaHecho=" + fechaHechos;
	data_caso += (fechaFinHecho!="")?"&fechaFinHecho=" + fechaFinHecho:"";
	data_caso += "&numeroDespacho=" + $("#txtnumeroDespacho").val() + "&nombreFuncionario="
			+ $("#txtnombreFuncionario").val();
	data_caso += "&comentario=" + $("#txtComentario").val();
	data_caso += "&ciudadHechos.ciudadPK.codigoCiudad=" + $("#txtMunicipioDeLosHechos").val()
			+ "&ciudadHechos.ciudadPK.codigoDepartamento=" + $("#txtDepartamentoDeLosHechos").val();
	data_caso += "&direccionHechos=" + $("#txtDireccionDeLosHechos").val() + "&resumenHechos="
			+ $("#txtResumenDeLosHechos").val();
	// data_caso += "&fechaCaducidad=" + fechaCaducidad + "&justificacion=" +
	// "Esta es la justificacion";
	data_caso += (fechaCaducidad!="")?"&fechaCaducidad=" + fechaCaducidad:"";
	data_caso += "&usuarioCreacion=" + $("#idusercreacion").val();
	data_caso += "&usuarioUltimaModificacion=" + $("#idusercreacion").val();
	data_caso += "&direccionProceso=" + $("#txtDireccionDespacho").val();
	if(!EstaVacio($("#txtMunicipioProceso").val())){

		data_caso += "&ciudadProceso.ciudadPK.codigoCiudad=" + $("#txtMunicipioProceso").val()
				+ "&ciudadProceso.ciudadPK.codigoDepartamento=" + $("#txtDeparmentoProceso").val();
	}

	// Bloque para ingresar los bienes afectados

	if($("#modal-nuevoCaso div[name=formularioBienesAfectados]").length > 1){
		$("#modal-nuevoCaso div[name=formularioBienesAfectados]:gt(0)").each(
				function(ind, formularioBienAfectado){
					if(!EstaVacio($(formularioBienAfectado).find("input[name=txtTituloBien]").val())
							|| !EstaVacio($(formularioBienAfectado).find("select[name=txtclaseBien]").val())
							|| !EstaVacio($(formularioBienAfectado).find("input[name=txtdescripcionBien]").val())){
						if(!EstaVacio($(formularioBienAfectado).find("input[name=txtTituloBien]").val())){

							data_caso += "&bienAfectadoSet[" + ind + "].titulo="
									+ $(formularioBienAfectado).find("input[name=txtTituloBien]").val();
						}
						if(!EstaVacio($(formularioBienAfectado).find("select[name=txtclaseBien]").val())){
							data_caso += "&bienAfectadoSet[" + ind + "].clase.codigo="
									+ $(formularioBienAfectado).find("select[name=txtclaseBien]").val();

						}
						if(!EstaVacio($(formularioBienAfectado).find("textarea[name=txtdescripcionBien]").val())){

							data_caso += "&bienAfectadoSet[" + ind + "].detalle="
									+ $(formularioBienAfectado).find("textarea[name=txtdescripcionBien]").val();
						}
						data_caso += "&bienAfectadoSet[" + ind + "].activo=" + "A";
					}
				});
	}

	// Bloque para ingresar los radicados
	if($("#modal-nuevoCaso div[name=formularioRadicado]").length > 1){
		$("#modal-nuevoCaso div[name=formularioRadicado]:gt(0)").each(
				function(ind, formularioRadicado){
					if($(formularioRadicado).find("input[name=checkBoxRadicado]:checked").length > 0){
						data_caso += "&" +
								"[" + ind + "].radicadoPK.codigoRadicado="
								+ $(formularioRadicado).find("input[name=txtNumeroRadicado]").val();
						
						data_caso += "&radicadoSet[" + ind + "].instancia.codigo="
								+ $(formularioRadicado).find("select[name=txtInstanciaRadicado]").val();
						data_caso += "&radicadoSet[" + ind + "].activo=" + "S";
						data_caso += "&radicadoSet[" + ind + "].usuarioCreacion=" + $("#idusercreacion").val();
						data_caso += "&radicadoSet[" + ind + "].usuarioUltimaModificacion="
								+ $("#idusercreacion").val();
						data_caso += "&radicadoSet[" + ind + "].acumulado=" + "S";
						data_caso += "&radicadoSet[" + ind + "].radicadoAcumulado.codigo="
								+ $(formularioRadicado).find("input[name=txtNumeroRadicadoPadre]").val();
						data_caso += "&radicadoSet[" + ind + "].comentarioAcumulado="
								+ $(formularioRadicado).find("input[name=txtAcomuladoObservaciones]").val();
						data_caso += "&radicadoSet[" + ind + "].acumuladoCon="
								+ $(formularioRadicado).find("input[name=txtacomuladoCon]").val();
					}else{
						data_caso += "&radicadoSet[" + ind + "].radicadoPK.codigoRadicado="
								+ $(formularioRadicado).find("input[name=txtNumeroRadicado]").val();
						data_caso += "&radicadoSet[" + ind + "].instancia.codigo="
								+ $(formularioRadicado).find("select[name=txtInstanciaRadicado]").val();
						data_caso += "&radicadoSet[" + ind + "].activo=" + "S";
						data_caso += "&radicadoSet[" + ind + "].usuarioCreacion=" + $("#idusercreacion").val();
						data_caso += "&radicadoSet[" + ind + "].usuarioUltimaModificacion="
								+ $("#idusercreacion").val();
						data_caso += "&radicadoSet[" + ind + "].acumulado=" + "N";
						data_caso += getRadicadosAcumulados($(formularioRadicado),ind);
						
					}
				});
	}

	// Bloque para ingresar los miembros del equipo
	var formulariosMiembrosEquipo = $("#modal-nuevoCaso [name = formularioEquipoCaso]").not(".hide");
	var direccion = $(formulariosMiembrosEquipo).find("[name = txtDireccionMiembro]").not(".hide");
	var departamentoMiembroEquipo = $(formulariosMiembrosEquipo).find("[name = TxtDepartamentoMiembro]").not(".hide");
	var ciudadMiembroEquipo = $(formulariosMiembrosEquipo).find("[name = txtMunicipioMiembro]").not(".hide");
	for (var i = 0; i < formulariosMiembrosEquipo.length; i++) {
		var formularioEquipoCaso = formulariosMiembrosEquipo[i];
		data_caso += "&casoEquipoCasoSet[" + i + "].direccion=" + direccion[i].value;
		if(ciudadMiembroEquipo[i].value != "" && ciudadMiembroEquipo[i].value != null){

			data_caso += "&casoEquipoCasoSet[" + i + "].ciudadEquipoCaso.ciudadPK.codigoCiudad="
					+ ciudadMiembroEquipo[i].value;
			data_caso += "&casoEquipoCasoSet[" + i + "].ciudadEquipoCaso.ciudadPK.codigoDepartamento="
					+ departamentoMiembroEquipo[i].value;
		}

		// --------se esta modificando
		if ($(formularioEquipoCaso).find("select[name=txtTipoPersona]").length != 0) {
			if ($(formularioEquipoCaso).find("select[name=txtTipoPersona]").val() == "Persona Juridica"
					&& $(formularioEquipoCaso).find("select[name=txtTipoPersona]").val() != "") {

				data_caso += "&casoEquipoCasoSet[" + i + "].personajuridica=" + JURIDICA_SI;
				data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.nombre="
						+ $(formularioEquipoCaso).find("input[name=txtNombresMiembro]").val();
			} else {
				data_caso += "&casoEquipoCasoSet[" + i + "].personajuridica=" + JURIDICA_SI;
				data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.nombre="
						+ $(formularioEquipoCaso).find("input[name=txtNombresMiembro]").val();
			}
			data_caso += "&casoEquipoCasoSet[" + i + "].casoEquipoCasoPK.miembro=" + 3;
			data_caso += "&casoEquipoCasoSet[" + i + "].activo=" + "S";
			data_caso += "&casoEquipoCasoSet[" + i + "].contacto=" + CONTACTO_NO;
			data_caso += "&casoEquipoCasoSet[" + i + "].observacion="
					+ $(formularioEquipoCaso).find("[name=txtObservacionesMiembro]").val();
			data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.identificacion="
					+ $(formularioEquipoCaso).find("input[name=txtIdentificacionMiembro]").val();
		} else {
			data_caso += "&casoEquipoCasoSet[" + i + "].personajuridica=" + "N";
			data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.nombre="
					+ $(formularioEquipoCaso).find("input[name=txtNombresMiembro]").val();
			data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.apellido="
					+ $(formularioEquipoCaso).find("input[name=txtApellidosMiembro]").val();
			data_caso += "&casoEquipoCasoSet[" + i + "].casoEquipoCasoPK.miembro="
					+ $(formularioEquipoCaso).find("select[name=txtTipoDeMiembro]").val();
			data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.identificacion="
					+ $(formularioEquipoCaso).find("input[name=txtIdentificacionMiembro]").val();
			if ($(formularioEquipoCaso).find("select[name=txtParentescoMiembro]").val() != "") {
				data_caso += "&casoEquipoCasoSet[" + i + "].parentesco.codigo="
						+ $(formularioEquipoCaso).find("select[name=txtParentescoMiembro]").val();

			}						

			data_caso += "&casoEquipoCasoSet[" + i + "].activo=" + "S";
			if ($(formularioEquipoCaso).find("input[name=esContacto]:checked").length > 0) {
				data_caso += "&casoEquipoCasoSet[" + i + "].contacto=" + CONTACTO_SI;
				
			} else {
				data_caso += "&casoEquipoCasoSet[" + i + "].contacto=" + CONTACTO_NO;
			}
		}

		var j = 0;
		$(formularioEquipoCaso).find("input[name^='txtTelefonoMiembro']").each(
				function(){
					if (!EstaVacio($(this).val())) {
						data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.telefonoList[" + j + "].numero="
								+ $(this).val();
						j++;
					}
				});
		j = 0;
		$(formularioEquipoCaso).find("input[name^='txtCorreoMiembro']").each(function() {
			if(!EstaVacio($(this).val())){
				data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.correoList[" + j + "].correo=" + $(this).val();
				j++;
			}
		});
		j = 0;
		$(formularioEquipoCaso).find("input[name=txtCelularMiembro]").each(function() {
			if (!EstaVacio($(this).val())) {
				data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.celularList[" + j + "].numero=" + $(this).val();
				j++;
			}
		});
				
		if ($(formularioEquipoCaso).find("select[name=nuevoDemandadoTipoDocumento]").val()){
			data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.tipoDocumento.codigo="
				+ $(formularioEquipoCaso).find("select[name=nuevoDemandadoTipoDocumento]").val();			
		}
				
		if ($(formularioEquipoCaso).find("select[name=nuevoVictimaDemandanteTipoDocumento]").val()){
			data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.tipoDocumento.codigo="
			+ $(formularioEquipoCaso).find("select[name=nuevoVictimaDemandanteTipoDocumento]").val();			
		}
		
		if ($(formularioEquipoCaso).find("input[name=demandadoNacimientoUsuario]").val()) {
			fecha = $(formularioEquipoCaso).find("input[name=demandadoNacimientoUsuario]").val().split("-");
			data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.fechaNacimiento=" + new Date(fecha[0], fecha[1] - 1, fecha[2]);		
		}
		
		if ($(formularioEquipoCaso).find("select[name=txtTipoDocumentoNuevoMiembro]").val()){
			data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.tipoDocumento.codigo="
			+ $(formularioEquipoCaso).find("select[name=txtTipoDocumentoNuevoMiembro]").val();			
		}
		
		if ($(formularioEquipoCaso).find("select[name=txtTipoDocumentoNuevoMiembroEquipoCaso]").val()){
			data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.tipoDocumento.codigo="
			+ $(formularioEquipoCaso).find("select[name=txtTipoDocumentoNuevoMiembroEquipoCaso]").val();			
		}
		
//		fecha = $(dato1).find("input[name=txtFechaPrestamo]").val().split("-");
//		data_caso += "&listaPrestamos[" + ind1 + "].fechaPrestamo="
//				+ new Date(fecha[0], fecha[1] - 1, fecha[2]);

		var aux1 = i + 1;
		if (aux1 == formulariosMiembrosEquipo.length) {
			i++;

			var aux = 0;
			$("#modal-nuevoCaso #cmbMiembroAbogado option:disabled").each(
					function(ind1, dato1){
						aux = i + ind1;
						data_caso += "&casoEquipoCasoSet[" + aux + "].casoEquipoCasoPK.identificacion="
								+ $("label[name=identificacion]").eq(ind1).text();
						data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.identificacion="
								+ $("label[name=identificacion]").eq(ind1).text();
						data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.nombre="
								+ $("label[name=nombreAbogado]").eq(ind1).text();
						data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.tipoDocumentoCodigo="
							+ $("label[name=tipoDocumentoCodigo]").eq(ind1).text();
						data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.apellido="
							+ $("label[name=apellido]").eq(ind1).text();
						data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.correoList[" + 0 + "].correo="
								+ $("label[name=correoAbogado]").eq(ind1).text();
						data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.telefonoList[" + 0 + "].numero="
								+ $("label[name=telefonoAbogado]").eq(ind1).text();
						data_caso += "&casoEquipoCasoSet[" + aux + "].direccion="
								+ $("label[name=direccionAbogado]").eq(ind1).text();
						data_caso += "&casoEquipoCasoSet[" + aux + "].contacto=" + CONTACTO_NO;
						data_caso += "&casoEquipoCasoSet[" + aux + "].personajuridica=" + "N";
						data_caso += "&casoEquipoCasoSet[" + aux + "].casoEquipoCasoPK.miembro=" + 5;
						data_caso += "&casoEquipoCasoSet[" + aux + "].activo=" + "S";
						if ($("label[name=lblNacimientoUsuario]").eq(ind1).text().indexOf("-") != -1) {							
							var fecha = $("label[name=lblNacimientoUsuario]").eq(ind1).text().split("-");
							data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.fechaNacimiento="
							+ new Date(fecha[0], fecha[1] - 1, fecha[2]);
						}
						
//						data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.tipoDocumento.codigo="
//						+ $("label[name=lblTipoDocumento]").eq(ind1).text();

					});

			/** Bloque para ingresar otros responsables */

			i = aux + 1;
			$(responsables).each(
					function(ind1, responsable) {
						aux = i + ind1;
						data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.nombre=" + responsable.nombre;
						data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.apellido=" + responsable.apellidos;
						data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.identificacion="
								+ responsable.identificacion;
						data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.telefonoList[" + 0 + "].numero="
								+ responsable.telefonos;
						data_caso += "&casoEquipoCasoSet[" + aux + "].equipoCaso.correoList[" + 0 + "].correo="
								+ responsable.email;
						data_caso += "&casoEquipoCasoSet[" + aux + "].direccion=" + responsable.direccion;
						data_caso += "&casoEquipoCasoSet[" + aux + "].contacto=" + CONTACTO_NO;
						data_caso += "&casoEquipoCasoSet[" + aux + "].personajuridica=" + JURIDICA_NO;
						data_caso += "&casoEquipoCasoSet[" + aux + "].casoEquipoCasoPK.miembro=" + CODIGOMIEMBROOTRO;
						data_caso += "&casoEquipoCasoSet[" + aux + "].activo=" + "S";
						if (responsable.isResponsableTarea)
							data_caso += "&casoEquipoCasoSet[" + aux + "].isResponsableTarea=" + true;
					});
		}

		// / Bloque para ingresar informacionn del prestamo

	}
	if ($("#modal-nuevoCaso div[name=prestamos]").length > 1) {
		$("#modal-nuevoCaso div[name=prestamos]:gt(0)").each(
				function(ind1, dato1){
					var guardarPrestamo = false;
					if (!EstaVacio($(dato1).find("input[name=txtnombreDeudor]").val())) {
						data_caso += "&listaPrestamos[" + ind1 + "].nombreDeudor="
								+ $(dato1).find("input[name=txtnombreDeudor]").val();
						guardarPrestamo = true;
					}
					if (!EstaVacio($(dato1).find("input[name=txtIdentificacionDeudor]").val())) {
						data_caso += "&listaPrestamos[" + ind1 + "].identificacion="
								+ $(dato1).find("input[name=txtIdentificacionDeudor]").val();
						guardarPrestamo = true;
					}
					if (!EstaVacio($(dato1).find("textarea[name=txtDescripcionPrestamo]").val())) {
						data_caso += "&listaPrestamos[" + ind1 + "].descripcionPrestamo="
								+ $(dato1).find("textarea[name=txtDescripcionPrestamo]").val();
						guardarPrestamo = true;
					}
					if (!EstaVacio($(dato1).find("input[name=txtFechaPrestamo]").val())) {
						fecha = $(dato1).find("input[name=txtFechaPrestamo]").val().split("-");
						data_caso += "&listaPrestamos[" + ind1 + "].fechaPrestamo="
								+ new Date(fecha[0], fecha[1] - 1, fecha[2]);
						guardarPrestamo = true;
					}
					if (!EstaVacio($(dato1).find("input[name=txtValorPrestamo]").val())) {
						data_caso += "&listaPrestamos[" + ind1 + "].monto="
								+ $(dato1).find("input[name=txtValorPrestamo]").val();
						guardarPrestamo = true;
					}
					if (!EstaVacio($(dato1).find("input[name=txtPorcentajeInteresPrestamo]").val())) {
						data_caso += "&listaPrestamos[" + ind1 + "].porcentajeInteres="
								+ $(dato1).find("input[name=txtPorcentajeInteresPrestamo]").val();
						guardarPrestamo = true;
					}
					if (!EstaVacio($(dato1).find("input[name=txtIntereses]").val())) {
						data_caso += "&listaPrestamos[" + ind1 + "].intereses="
								+ $(dato1).find("input[name=txtIntereses]").val();
						guardarPrestamo = true;
					}
					if (!EstaVacio($(dato1).find("input[name=txtSaldoPrestamo]").val())) {
						data_caso += "&listaPrestamos[" + ind1 + "].saldo="
								+ $(dato1).find("input[name=txtSaldoPrestamo]").val();
						guardarPrestamo = true;
					}
					if (!EstaVacio($(dato1).find("select[name=txtEntidadFinanciera]").val())) {
						data_caso += "&listaPrestamos[" + ind1 + "].entidadFinaciera.codigoEntidadfinaciera="
								+ $(dato1).find("select[name=txtEntidadFinanciera]").val();
						guardarPrestamo = true;
					}
					if (!EstaVacio($(dato1).find("input[name=txtNumeroDeCuenta]").val())) {
						data_caso += "&listaPrestamos[" + ind1 + "].numeroCuenta="
								+ $(dato1).find("input[name=txtNumeroDeCuenta]").val();
						guardarPrestamo = true;
					}
					if (!EstaVacio($(dato1).find("select[name=txtTipoDeCuenta]").val())) {
						data_caso += "&listaPrestamos[" + ind1 + "].tipoCuenta.codigoTipoCuenta="
								+ $(dato1).find("select[name=txtTipoDeCuenta]").val();
						guardarPrestamo = true;

					}
					if (!EstaVacio($(dato1).find("input[name=txtTituloValor]").val())) {
						data_caso += "&listaPrestamos[" + ind1 + "].titulo="
								+ $(dato1).find("input[name=txtTituloValor]").val();
						guardarPrestamo = true;

					}
					if (guardarPrestamo) {

						data_caso += "&listaPrestamos[" + ind1 + "].activo=" + "S";
						data_caso += "&listaPrestamos[" + ind1 + "].cancelado=" + "N";
						data_caso += "&listaPrestamos[" + ind1 + "].usuarioCreacion=" + $("#idusercreacion").val();
						data_caso += "&listaPrestamos[" + ind1 + "].usuarioUltimaModificacion="
								+ $("#idusercreacion").val();
					}

					// data_caso+=$(dato1).find("input[name=txtTituloValor]").val();

				});

	}

	data_caso += serializarActividades();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/caso/guardar",
		data : data_caso,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			$("#modal-nuevoCaso").modal("hide");
			if(window['contextoPapelera'] != undefined) {
				$("#messageExitoso").html("Se guard\u00f3 el caso exitosamente.");
				$("#messageExitoso").removeClass("hide");
				setTimeout(function(){
					$("#messageExitoso").html("");
					$("#messageExitoso").addClass("hide");
				}, 10000);
			} else {
				if (window['contextoDetalle'] != undefined) {
					$("#messageExitoso").html("Se guard\u00f3 el caso exitosamente.");
					$("#messageExitoso").show();
					setTimeout(function(){
						$("#messageExitoso").html("");
						$("#messageExitoso").hide();
					}, 10000);
				} else {
					$("#messageExitoso").html("Se guard\u00f3 el caso exitosamente.");
					$("#messageExitoso").show();
					setTimeout(function(){
						$("#messageExitoso").html("");
						$("#messageExitoso").hide();
					}, 10000);
					mostrarTabla();
				}
			}
			
			limpiarFormularioNuevoCaso();

		},
		error : function(error) {
			waitingDialog.hide();
			$("#modal-nuevoCaso .modal-content").scrollTop(0);
			$("#messageError").html("No fue posible guardar el caso.");
			$("#messageError").show();
			setTimeout("limpiarMensajeError();", 10000);
		}
	});

}


function getRadicadosAcumulados(formulario,ind, caso){
	var rows = formulario.find("#tableRadicadosAcumulados tbody tr");
	var datos = "";
	var datos = "&radicadoSet[" + ind + "].instancia.codigo=";
	
	$.each(rows,function(index,row){
		var tipoRadicado = getTipoRadicoByCodigo($(row).find("#tipoRadicado").val());
		var radicado;
		if(tipoRadicado == "GJA")
			radicado = $(row).find("#txtAutoCompleteRadicados").val()
		else if(tipoRadicado == "Externo")
			radicado = $(row).find("#txtRadicadoAsociado").val()
				
		datos+="&radicadoSet[" + ind + "].radicadosAcumulados["+index+"].tipoRadicado="+tipoRadicado;
		datos+="&radicadoSet[" + ind + "].radicadosAcumulados["+index+"].observacion="+$(row).find("#txtObservacionRadicado").val();
		datos+="&radicadoSet[" + ind + "].radicadosAcumulados["+index+"].radicadoPK.codigoRadicadoAcumulado="+radicado;
		
	})
	
	
	
	return datos;
}

function guardarTareasPorParentesco(codigoCaso) {
	var data_caso =  "_csrf=" + $("#_csrf").val();
	data_caso += "&codigoCaso=" + codigoCaso;
	$.ajax({
		type : "POST",
		url : contexto + "/caso/guardarTareasPorParentesco",
		data : data_caso,
		dataType : "json",
		success : function(response){
			alert("guardo");
		},
		error : function(error) {
			waitingDialog.hide();
			$("#modal-nuevoCaso .modal-content").scrollTop(0);
			$("#messageError").html("Error guardando las tareas por parentesco.");
			$("#messageError").show();
			setTimeout("limpiarMensajeError();", 10000);
		}
	});
}

function serializarActividades(){
	var data_caso = "";	
	for (var ind = 0; ind < countActividades; ind++){
		var cantTareas = actividadesArray[ind].cantidadTareas;
		var tareasActividadesArray = actividadesArray[ind].tareas;
		$("#modal-nuevoCaso #actividadVencimiento" + actividadesArray[ind].fila).val();
		data_caso += "&listaActividadesCaso[" + ind + "].nombreActividad=" + actividadesArray[ind].nombreActividad;
		data_caso += "&listaActividadesCaso["
				+ ind
				+ "].numeroDiasAntesAlertas="
				+ $("#modal-nuevoCaso #actividadVencimiento" + actividadesArray[ind].fila).closest(
						"div[name=actividadParticular]").find("input[name = txtNumerosdiasAntesActividad]").val();
		data_caso += "&listaActividadesCaso["
				+ ind
				+ "].numeroAlertasPorDia="
				+ $("#modal-nuevoCaso #actividadVencimiento" + actividadesArray[ind].fila).closest(
						"div[name=actividadParticular]").find("input[name = txtNumeroNotificacionesActividad]").val();
		data_caso += "&listaActividadesCaso["
				+ ind
				+ "].esActividadPropia="
				+ $("#modal-nuevoCaso #actividadVencimiento" + actividadesArray[ind].fila).closest(
						"div[name=actividadParticular]").find("input[name = txtEsActividadPropia]").val();
		fecha = $("#actividadVencimiento" + actividadesArray[ind].fila).val().split("-");
		data_caso += "&listaActividadesCaso[" + ind + "].fechaLimite=" + new Date(fecha[0], fecha[1] - 1, fecha[2]);
		data_caso += "&listaActividadesCaso["
				+ ind
				+ "].finalizada="
				+ $("#modal-nuevoCaso #actividadVencimiento" + actividadesArray[ind].fila).closest(
						"div[name=actividadParticular]").find("select[name = cmbEstadoActividad]").val();
		data_caso += "&listaActividadesCaso[" + ind + "].usuarioCreacion=" + $("#idusercreacion").val();
		data_caso += "&listaActividadesCaso[" + ind + "].usuarioUltimaModificacion=" + $("#idusercreacion").val();
		data_caso += "&listaActividadesCaso[" + ind + "].snActiva=" + "S";

		for (var ind1 = 0; ind1 < cantTareas; ind1++){
			data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1 + "].tarea="
					+ $("#modal-nuevoCaso #nombreTarea" + tareasActividadesArray[ind1].fila).val();
			data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1 + "].detalle="
					+ $("#modal-nuevoCaso #detalleTarea" + tareasActividadesArray[ind1].fila).val();
			fecha = $("#modal-nuevoCaso #vencimiento" + tareasActividadesArray[ind1].fila).val().split("-");
			data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1 + "].fechaLimite="
					+ new Date(fecha[0], fecha[1] - 1, fecha[2]);
			data_caso += "&listaActividadesCaso["
					+ ind
					+ "].tareaParticularCasoSet["
					+ ind1
					+ "].finalizada="
					+ $("#modal-nuevoCaso #nombreTarea" + tareasActividadesArray[ind1].fila).closest("tr").find(
							"select[name ^='cmbEstadoTareas']").val();
			data_caso += "&listaActividadesCaso["
					+ ind
					+ "].tareaParticularCasoSet["
					+ ind1
					+ "].esTareaPropia="
					+ $("#modal-nuevoCaso #nombreTarea" + tareasActividadesArray[ind1].fila).closest("tr").find(
							"input[name ^='txtTareaPropia']").val();
			data_caso += "&listaActividadesCaso["
					+ ind
					+ "].tareaParticularCasoSet["
					+ ind1
					+ "].numeroDiasAntesAlertas="
					+ $("#modal-nuevoCaso #nombreTarea" + tareasActividadesArray[ind1].fila).closest("tr").find(
							"input[name ^='numeroDeDias']").val();
			data_caso += "&listaActividadesCaso["
					+ ind
					+ "].tareaParticularCasoSet["
					+ ind1
					+ "].numeroAlertasPorDia="
					+ $("#modal-nuevoCaso #nombreTarea" + tareasActividadesArray[ind1].fila).closest("tr").closest(
							"div[name=actividadParticular]").find("input[name ^='numeroDeAlertas']").val();
			data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1 + "].usuarioCreacion="
					+ $("#idusercreacion").val();
			data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1
					+ "].usuarioUltimaModificacion=" + $("#idusercreacion").val();
			data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1 + "].snActiva=" + "S";

			var identificaciones = $("#modal-nuevoCaso #responsable" + tareasActividadesArray[ind1].fila).val();
			var countRespTareas = 0;
			$(identificaciones).each(
					function(index1, identificacion){
						if(identificacion == 0){
							identificacion += ind1 + countRespTareas;
							countRespTareas++
						}

						data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1
								+ "].responsablesTareas[" + index1 + "].responsableTareaPK.codigoEquipoCaso="
								+ identificacion;
					});
		}
	}
	return data_caso;
}

function validarFormulario(){
	ocultarMensajesError();
	var isError = false;
	limpiarMensajeError();
	limpiarErrores();
	var erroresNuevoCaso = [];
	var erroresAbogados = [];
	var erroresDemandados = [];
	var erroresVictimasDemandantes = [];
	var erroresActividades = [];
	var erroresLugarProceso = [];
	var erroresBienes = [];
	var erroresRadicados = [];
	var erroresPrestamo = [];
	var formularioError = false;
	var container = $("#modal-nuevoCaso .modal-content");

	var ocultarCollapsibleTarea = function(ids, cantidadParent) {
		var padre = $(ids);
		try{
			for (var cont = 0; cont < cantidadParent; cont++){
				padre = padre.parent();
			}
			padre.removeClass("in").prop("aria-expanded", false).css("height", "0");
		}catch(e){
			console.log('' + e);
		}
	};

	var mostrarCollapsibleTarea = function(ids, cantidadParent){
		var padre = $(ids);
		try{
			for (var cont = 0; cont < cantidadParent; cont++){
				padre = padre.parent();
			}
			padre.addClass("in").prop("aria-expanded", true).css("height", "");
		}catch(e){
			console.log('' + e);
		}
	}

	$("#collapseDespachoProceso").removeClass("in").prop("aria-expanded", false).css("height", "0");
	$("#collapseBienesAfectados").removeClass("in").prop("aria-expanded", false).css("height", "0");
	$("#collapsePrestamo").removeClass("in").prop("aria-expanded", false).css("height", "0");
	$("#collapseComentario").removeClass("in").prop("aria-expanded", false).css("height", "0");

	/**
	 * Formulario Nuevo Caso
	 */
	
	var tipoRadicadoCodigo = $("#tipoRadicado").val();
	var tipoRadicado = getTipoRadicoByCodigo(tipoRadicadoCodigo);
	
	
	
	// valida el estado del caso
	if (EstaVacio($("#txtEstadoCaso").val())) {
		$("#txtEstadoCaso").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo estado del caso es obligatorio.");
		if (!formularioError) {
			formularioError = true
			container.scrollTop(0);
		}
	}
	if (tipoRadicado == "GJA" && EstaVacio($("#txtAutoCompleteRadicados").val())) {
		$("#txtAutoCompleteRadicados").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo radicado relacionado es obligatorio.");
		if (!formularioError) {
			formularioError = true
			container.scrollTop(0);
		}
	}else if (tipoRadicado == "Externo" && EstaVacio($("#txtRadicadoAsociado").val())) {
		$("#txtRadicadoAsociado").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo radicado relacionado es obligatorio.");
		if (!formularioError) {
			formularioError = true
			container.scrollTop(0);
		}
	}
	
	// valida el tipo del caso
	if(EstaVacio($("#txtTipoCaso").val())){
		$("#txtTipoCaso").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo tipo del caso es obligatorio.");
		if(!formularioError){
			formularioError = true
			container.scrollTop(0);
		}
	}
	// valida el nombre del caso
	if(EstaVacio($("#txtNombreCaso").val())){
		$("#txtNombreCaso").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo nombre del caso es obligatorio.");
		if(!formularioError){
			formularioError = true
			container.scrollTop(0);
		}
	}
	// valida la fechas de los hechos
	if(EstaVacio($("#txtFechaDeLosHechos").val())){
		$("#txtFechaDeLosHechos").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo fecha de los hechos es obligatorio.");
		if(!formularioError){
			formularioError = true
			container.scrollTop(0);
		}
	}
	/** valida el fin de los hechos */
	if(!$("#chkSinConteo")[0].checked && EstaVacio($("#txtFinFechaDeLosHechos").val())){
		$("#txtFinFechaDeLosHechos").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo fecha fin de los hechos es obligatorio.");
		if(!formularioError){
			formularioError = true
			container.scrollTop(0);
		}
	}
	// Pais
	if(EstaVacio($("#txtPaisDeLosHechos").val())){
		$("#txtPaisDeLosHechos").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo pa\u00EDs de los hechos es obligatorio.");
		if(!formularioError){
			formularioError = true
			container.scrollTop(0);
		}
	}
	// Departamento
	if(EstaVacio($("#txtDepartamentoDeLosHechos").val())){
		$("#txtDepartamentoDeLosHechos").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo departamento de los hechos es obligatorio.");
		if(!formularioError){
			formularioError = true
			container.scrollTop(0);
		}
	}
	// Municipio
	if(EstaVacio($("#txtMunicipioDeLosHechos").val())){
		$("#txtMunicipioDeLosHechos").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo municipio de los hechos es obligatorio.");
		if(!formularioError){
			formularioError = true
			container.scrollTop(0);
		}
	}
	// valida el lugar de los hechos
	if(EstaVacio($("#txtDireccionDeLosHechos").val())){
		$("#txtDireccionDeLosHechos").addClass("campoTextoError");
		isError = true;
		erroresNuevoCaso.push("El campo lugar de los hechos es obligatorio.");
		if(!formularioError){
			formularioError = true
			container.scrollTop(0);
		}
	}

	/**
	 * Formulario abogados
	 */
	var areaAbogados = $("#collapseAbogados");
	areaAbogados.removeClass("in").prop("aria-expanded", false).css("height", "0");
	// valida que se ingrese abogados al caso
	if($("#cmbMiembroAbogado option:disabled").length == 0){
		$("#cmbMiembroAbogado").addClass("campoTextoError");
		isError = true;
		erroresAbogados.push("Es obligatorio agregar al menos un abogado.");
		if(!formularioError){
			formularioError = true
			container.scrollTop(areaAbogados.offset().top);
			areaAbogados.addClass("in").prop("aria-expanded", true).css("height", "");
		}
	}

	/**
	 * Formulario demandados
	 */
	var areaDemandados = $("#collapseDemandados");
	areaDemandados.removeClass("in").prop("aria-expanded", false).css("height", "0");
	// Valida que se ingrese un demandante
	if ($("#modal-nuevoCaso select[name=txtTipoPersona]").length < 1) {

		$("#messageErrorDemandante").html("Se debe ingresar un demandante");
		$("#messageErrorDemandante").show();
		isError = true;
		if(!formularioError){
			formularioError = true
			container.scrollTop(areaDemandados.offset().top);
			areaDemandados.addClass("in").prop("aria-expanded", true).css("height", "");
		}
	}

	// valida el tipo de persona
	$("#modal-nuevoCaso select[name=txtTipoPersona]:gt(0)").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresDemandados.push("El campo tipo de persona es obligatorio.");
			if(!formularioError){
				formularioError = true
				container.scrollTop(areaDemandados.offset().top);
				areaDemandados.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		}
	});
	// valida el nombre del miembro del equipo
	$("#modal-nuevoCaso input[name=txtNombresMiembro]:gt(0)").not(
			"#modal-nuevoCaso #formularioNombreMiembroNatural input[name=txtNombresMiembro]").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresDemandados.push("El campo nombres es obligatorio.");
			if(!formularioError){
				formularioError = true
				container.scrollTop(areaDemandados.offset().top);
				areaDemandados.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		}
	});

	// valida los correos
	$("#modal-nuevoCaso input[name=txtCorreoMiembroDemandado]:gt(0)").each(function(){
		var mostrarErrorEnMail = function(campo, mensajeError){
			$(campo).addClass("campoTextoError");
			isError = true;
			erroresDemandados.push(mensajeError);
			if(!formularioError){
				formularioError = true
				container.scrollTop(areaDemandados.offset().top);
				areaDemandados.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		};
		if(!EstaVacio($(this).val())){
			if(!validarEmail($(this).val())){
				mostrarErrorEnMail(this, "El campo correo no tiene un email valido.");
			}
		}
	});

	/**
	 * Formulario Equipo Caso
	 */
	// valida que se tenga un tipo de miembro victima
	var mensajeFaltaVictima = false;
	var mensajeFaltaDemandante = false;
	var areaEquipoCaso = $("#collapseSolicitud")
	areaEquipoCaso.removeClass("in").prop("aria-expanded", false).css("height", "0");
	$("#modal-nuevoCaso select[name=txtTipoDeMiembro]:gt(0)").each(function(){
		if($(this).val() == 1){
			mensajeFaltaVictima = true;
		}
		if($(this).val() == 2){
			mensajeFaltaDemandante = true;
		}
	});

	// Valida los tipos de miembros
	if (!$("#modal-nuevoCaso input[name*='esContacto']:checked").length > 0) {
		isError = true;
		erroresVictimasDemandantes.push("Debe seleccionar al menos un contacto.");
		if (!formularioError) {
			formularioError = true
			container.scrollTop(areaEquipoCaso.offset().top);
			areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
		}
	}
	 
	$("#modal-nuevoCaso select[name=txtTipoDeMiembro]:gt(0)").each(function(){
		if(EstaVacio($(this).val())) {
			$(this).addClass("campoTextoError");
			isError = true;
			erroresVictimasDemandantes.push("El campo tipo de miembro es obligatorio.");
			if (!formularioError) {
				formularioError = true
				container.scrollTop(areaEquipoCaso.offset().top);
				areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		}
	});

	$("#modal-nuevoCaso #formularioNombreMiembroNatural input[name=txtNombresMiembro]:gt(0)").each(function() {
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresVictimasDemandantes.push("El campo nombres es obligatorio.");
			if(!formularioError){
				formularioError = true
				container.scrollTop(areaEquipoCaso.offset().top);
				areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		}
	});

	// Valida el apellido del miembro del equipo
	$("#modal-nuevoCaso input[name=txtApellidosMiembro]:gt(0)").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresVictimasDemandantes.push("El campo apellidos es obligatorio.");
			if(!formularioError){
				formularioError = true
				container.scrollTop(areaEquipoCaso.offset().top);
				areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		}
	});

	// valida los telefonos
	$("#modal-nuevoCaso input[name=txtTelefonoMiembro]:gt(0)").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresVictimasDemandantes.push("El campo Tel\u00E9fono es obligatorio.");
			if(!formularioError){
				formularioError = true
				container.scrollTop(areaEquipoCaso.offset().top);
				areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		}
	});

	// valida los correos
	$("#modal-nuevoCaso input[name=txtCorreoMiembro]:gt(0)").each(function(){
		var mostrarErrorEnMail = function(campo, mensajeError){
			$(campo).addClass("campoTextoError");
			isError = true;
			erroresVictimasDemandantes.push(mensajeError);
			if(!formularioError){
				formularioError = true;
				container.scrollTop(areaEquipoCaso.offset().top);
				areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		};

		if(EstaVacio($(this).val())){
			mostrarErrorEnMail(this, "El campo correo es obligatorio.");
		}else{
			if(!validarEmail($(this).val())){
				mostrarErrorEnMail(this, "El campo correo no tiene un email valido.");
			}
		}
	});

	if(mensajeFaltaVictima && mensajeFaltaDemandante){
		$("#messageErrorVictimaDemandante").hide();
	}else{
		$("#messageErrorVictimaDemandante").html("Se debe ingresar una victima y un demandante");
		$("#messageErrorVictimaDemandante").show();
		isError = true;
		if(!formularioError){
			formularioError = true;
			container.scrollTop(areaEquipoCaso.offset().top);
			areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
		}
	}

	/**
	 * Formulario Actividades
	 */

	var areaActividades = $("#modal-nuevoCaso #Actividad");
	areaActividades.removeClass("in").prop("aria-expanded", false).css("height", "0");

	// valida que se ingrese los campos nombre tarea
	for (var i = 0; i < countActividades; i++){
		var cantTareas = actividadesArray[i].cantidadTareas;
		var tareasActividadesArray = actividadesArray[i].tareas;
		var nombreActividad = actividadesArray[i].nombreActividad;
		
		for (var j = 0; j < cantTareas; j++){
			ocultarCollapsibleTarea("#modal-nuevoCaso #nombreTarea" + tareasActividadesArray[j].fila, 7);
			$("#modal-nuevoCaso #nombreTarea" + tareasActividadesArray[j].fila).parent().parent().parent().parent()
					.parent().parent().parent().removeClass("in").prop("aria-expanded", false).css("height", "0");
			if(EstaVacio($("#modal-nuevoCaso #nombreTarea" + tareasActividadesArray[j].fila).val())){
				$("#modal-nuevoCaso #nombreTarea" + tareasActividadesArray[j].fila).addClass("campoTextoError");
				isError = true;
				erroresActividades.push("El nombre de la tarea " + (j + 1) + " en la actividad " + nombreActividad
						+ " es obligatorio.");
				if(!formularioError){
					formularioError = true;
					container.scrollTop(areaActividades.offset().top);
					mostrarCollapsibleTarea("#modal-nuevoCaso #nombreTarea" + tareasActividadesArray[j].fila, 7);
					areaActividades.addClass("in").prop("aria-expanded", true).css("height", "");
				}
			}
		}
	}

	// valida que se ingrese los campos responsables
	for (var i = 0; i < countActividades; i++){
		var cantTareas = actividadesArray[i].cantidadTareas;
		var tareasActividadesArray = actividadesArray[i].tareas;
		var nombreActividad = actividadesArray[i].nombreActividad;

		for (var j = 0; j < cantTareas; j++){
			var identificacion = $("#modal-nuevoCaso #responsable" + tareasActividadesArray[j].fila).val();
			if(identificacion == null){
				$("#modal-nuevoCaso #responsable" + tareasActividadesArray[j].fila).addClass("campoTextoError");
				isError = true;
				erroresActividades.push("El responsable de la tarea " + (j + 1) + " en la actividad " + nombreActividad
						+ " es obligatorio.");
				mostrarCollapsibleTarea("#modal-nuevoCaso #responsable" + tareasActividadesArray[j].fila, 7);
				if(!formularioError){
					formularioError = true;
					container.scrollTop(areaActividades.offset().top);
					areaActividades.addClass("in").prop("aria-expanded", true).css("height", "");
				}
			}
		}
	}

	// valida que se ingrese las fechas de vencimiento tarea
	for (var i = 0; i < countActividades; i++){
		var cantTareas = actividadesArray[i].cantidadTareas;
		var tareasActividadesArray = actividadesArray[i].tareas;
		var nombreActividad = actividadesArray[i].nombreActividad;

		for (var j = 0; j < cantTareas; j++){
			if(EstaVacio($("#modal-nuevoCaso #vencimiento" + tareasActividadesArray[j].fila).val())){
				$("#modal-nuevoCaso #vencimiento" + tareasActividadesArray[j].fila).addClass("campoTextoError");
				isError = true;
				erroresActividades.push("El campo fecha de vencimiento de la tarea " + (j + 1) + " en la actividad "
						+ nombreActividad + " es obligatorio.");
				mostrarCollapsibleTarea("#modal-nuevoCaso #vencimiento" + tareasActividadesArray[j].fila, 7);
				if(!formularioError){
					formularioError = true
					container.scrollTop(areaActividades.offset().top);
					areaActividades.addClass("in").prop("aria-expanded", true).css("height", "");
				}
			}
		}
	}
	// valida que se ingrese los campos detalle
	for (var i = 0; i < countActividades; i++){
		var cantTareas = actividadesArray[i].cantidadTareas;
		var tareasActividadesArray = actividadesArray[i].tareas;
		var nombreActividad = actividadesArray[i].nombreActividad;

		for (var j = 0; j < cantTareas; j++){
			if(EstaVacio($("#modal-nuevoCaso #detalleTarea" + tareasActividadesArray[j].fila).val())){
				$("#modal-nuevoCaso #detalleTarea" + tareasActividadesArray[j].fila).addClass("campoTextoError");
				isError = true;
				erroresActividades.push("El detalle de la tarea " + (j + 1) + " en la actividad " + nombreActividad
						+ " es obligatorio.");
				mostrarCollapsibleTarea("#modal-nuevoCaso #detalleTarea" + tareasActividadesArray[j].fila, 7);
				if(!formularioError){
					formularioError = true
					container.scrollTop(areaActividades.offset().top);
					areaActividades.addClass("in").prop("aria-expanded", true).css("height", "");
				}
			}

		}
	}
	// valida que se ingrese una fecha de vencimiento actividad
	for (var i = 0; i < countActividades; i++){
		var id = "#actividadVencimiento" + actividadesArray[i].fila;
		if($(id).attr("required")){
			if(EstaVacio($(id).val())){
				var nombreActividad = actividadesArray[i].nombreActividad;
				$("#modal-nuevoCaso #actividadVencimiento" + actividadesArray[i].fila).addClass("campoTextoError");
				isError = true;
				erroresActividades.push("El campo fecha de vencimiento de la actividad " + nombreActividad
						+ " es obligatorio.");
				if(!formularioError){
					formularioError = true
					container.scrollTop(areaActividades.offset().top);
					areaActividades.addClass("in").prop("aria-expanded", true).css("height", "");
				}
			}
		}
		
	}

	/**
	 * Formulario radicados
	 */
	var areaRadicados = $("#collapseRadicado");
	areaRadicados.removeClass("in").prop("aria-expanded", false).css("height", "0");
	// valida numero de radicados
	$("#modal-nuevoCaso input[name=txtNumeroRadicado]:gt(0)").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresRadicados.push("El campo n\u00FAmero radicado es obligatorio.");
			if(!formularioError){
				formularioError = true
				container.scrollTop(areaRadicados.offset().top);
				areaRadicados.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		}
	});
	// valida la instancia del radicado
	$("#modal-nuevoCaso select[name=txtInstanciaRadicado]:gt(0)").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresRadicados.push("El campo instancia es obligatorio.");
			if(!formularioError){
				formularioError = true
				container.scrollTop(areaRadicados.offset().top);
				areaRadicados.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		}
	});

	/**
	 * Formulario lugar proceso
	 */
	var areaLugarProceso = $("#collapseDespachoLugarProceso");
	areaLugarProceso.removeClass("in").prop("aria-expanded", false).css("height", "0");
	if(!EstaVacio($("#txtDireccionDespacho").val())){
		if(EstaVacio($("#txtPaisProceso").val())){
			$("#txtPaisProceso").addClass("campoTextoError");
			isError = true;
			erroresLugarProceso.push("El campo pa\u00EDs es obligatorio.");
			if(!formularioError){
				formularioError = true
				container.scrollTop(areaLugarProceso.offset().top);
				areaLugarProceso.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		}else{
			$("#txtPaisProceso").removeClass("campoTextoError");
		}
		if(EstaVacio($("#txtDeparmentoProceso").val())){
			$("#txtDeparmentoProceso").addClass("campoTextoError");
			isError = true;
			erroresLugarProceso.push("El campo departamento es obligatorio.");
			if(!formularioError){
				formularioError = true
				container.scrollTop(areaLugarProceso.offset().top);
				areaLugarProceso.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		}else{
			$("#txtDeparmentoProceso").removeClass("campoTextoError");
		}
		if(EstaVacio($("#txtMunicipioProceso").val())){
			$("#txtMunicipioProceso").addClass("campoTextoError");
			isError = true;
			erroresLugarProceso.push("El campo municipio es obligatorio.");
			if(!formularioError){
				formularioError = true
				container.scrollTop(areaLugarProceso.offset().top);
				areaLugarProceso.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		}else{
			$("#txtMunicipioProceso").removeClass("campoTextoError");
		}
	}else{
		$("#txtPaisProceso").removeClass("campoTextoError");
		$("#txtDeparmentoProceso").removeClass("campoTextoError");
		$("#txtMunicipioProceso").removeClass("campoTextoError");
	}

	if(isError){
		var objectErrores = new Object();
		objectErrores.erroresNuevoCaso = erroresNuevoCaso;
		objectErrores.erroresAbogados = erroresAbogados;
		objectErrores.erroresDemandados = erroresDemandados;
		objectErrores.erroresVictimasDemandantes = erroresVictimasDemandantes;
		objectErrores.erroresActividades = erroresActividades;
		objectErrores.erroresLugarProceso = erroresLugarProceso;
		objectErrores.erroresBienes = erroresBienes;
		objectErrores.erroresRadicados = erroresRadicados;
		objectErrores.erroresPrestamo = erroresPrestamo;
		mostrarMensajesError(objectErrores);
		return false;
	}else{
		return true;
	}
}

function limpiarErrores(){
	$("#txtEstadoCaso").removeClass("campoTextoError");// estado del caso
	$("#txtEstadoCaso").addClass("form-control");
	$("#txtTipoCaso").removeClass("campoTextoError");// tipo del caso
	$("#txtTipoCaso").addClass("form-control");
	$("#txtNombreCaso").removeClass("campoTextoError");// Nombre del caso
	$("#txtNombreCaso").addClass("form-control");
	$("#txtFechaDeLosHechos").removeClass("campoTextoError");// fechas de los
	// hechos
	$("#txtFechaDeLosHechos").addClass("form-control");
	$("#txtFinFechaDeLosHechos").removeClass("campoTextoError");// fechas de los
	// hechos
	$("#txtFinFechaDeLosHechos").addClass("form-control");
	$("#txtDireccionDeLosHechos").removeClass("campoTextoError");// lugar de
	// los
	// hechos
	$("#txtDireccionDeLosHechos").addClass("form-control");
	$("#txtPaisDeLosHechos").removeClass("campoTextoError");// Pais del los
	// hechos
	$("#txtPaisDeLosHechos").addClass("form-control");
	$("#txtDepartamentoDeLosHechos").removeClass("campoTextoError");// Departamento
	// de los
	// hechos
	$("#txtDepartamentoDeLosHechos").addClass("form-control");
	$("#txtMunicipioDeLosHechos").removeClass("campoTextoError");// Municipio
	// de los
	// hechos
	$("#txtMunicipioDeLosHechos").addClass("form-control");
	$("#cmbMiembroAbogado").removeClass("campoTextoError");// Abogados
	$("#cmbMiembroAbogado").addClass("form-control");
	$("#txtMunicipioProceso").removeClass("campoTextoError");// municipio
	// delproceso
	$("#messageErrorDemandante").hide();// demandante

	// tipo de persona
	$("select[name=txtTipoPersona]").each(function(){

		$(this).removeClass("campoTextoError");
		$(this).addClass("form-control");

	});
	// nombre del miembro
	$("input[name=txtNombresMiembro]").each(function(){

		$(this).removeClass("campoTextoError");
		$(this).addClass("form-control");

	});
	// Apellido
	$("input[name=txtApellidosMiembro]").each(function(){

		$(this).removeClass("campoTextoError");
		$(this).addClass("form-control");

	});
	// tipos de miembros
	$("select[name=txtTipoDeMiembro]").each(function(){

		$(this).removeClass("campoTextoError");
		$(this).addClass("form-control");

	});
	// telefonos
	$("input[name=txtTelefonoMiembro]").each(function(){

		$(this).removeClass("campoTextoError");
		$(this).addClass("form-control");

	});
	// Correo
	$("input[name=txtCorreoMiembro]").each(function(){

		$(this).removeClass("campoTextoError");
		$(this).addClass("form-control");

	});
	// numero radicado
	$("input[name=txtNumeroRadicado]:gt(0)").each(function(){

		$(this).removeClass("campoTextoError");
		$(this).addClass("form-control");

	});
	// instancia
	$("select[name=txtInstanciaRadicado]:gt(0)").each(function(){

		$(this).removeClass("campoTextoError");
		$(this).addClass("form-control");

	});

	// / Validacion de tareas de la actividad

	// Nombre tarea
	for (var i = 0; i < countActividades; i++){
		var cantTareas = actividadesArray[i].cantidadTareas;
		var tareasActividadesArray = actividadesArray[i].tareas;
		// alert ($("#actividadVencimiento"+actividadesArray[i].fila).val());
		for (var j = 0; j < cantTareas; j++){

			$("#nombreTarea" + tareasActividadesArray[j].fila).removeClass("campoTextoError");
			$("#nombreTarea" + tareasActividadesArray[j].fila).addClass("form-control");

		}
	}
	// Detalle
	for (var i = 0; i < countActividades; i++){
		var cantTareas = actividadesArray[i].cantidadTareas;
		var tareasActividadesArray = actividadesArray[i].tareas;
		// alert ($("#actividadVencimiento"+actividadesArray[i].fila).val());
		for (var j = 0; j < cantTareas; j++){

			$("#detalleTarea" + tareasActividadesArray[j].fila).removeClass("campoTextoError");
			$("#detalleTarea" + tareasActividadesArray[j].fila).addClass("form-control");

		}
	}
	// Responsables

	for (var i = 0; i < countActividades; i++){
		var cantTareas = actividadesArray[i].cantidadTareas;
		var tareasActividadesArray = actividadesArray[i].tareas;
		// alert ($("#actividadVencimiento"+actividadesArray[i].fila).val());
		for (var j = 0; j < cantTareas; j++){

			$("#responsable" + tareasActividadesArray[j].fila).removeClass("campoTextoError");
			// $("#responsable"+tareasActividadesArray[j].fila).addClass("form-control");

		}
	}

	// Fechas de vencimiento tarea
	for (var i = 0; i < countActividades; i++){
		var cantTareas = actividadesArray[i].cantidadTareas;
		var tareasActividadesArray = actividadesArray[i].tareas;
		// alert ($("#actividadVencimiento"+actividadesArray[i].fila).val());
		for (var j = 0; j < cantTareas; j++){

			$("#vencimiento" + tareasActividadesArray[j].fila).removeClass("campoTextoError");
			$("#vencimiento" + tareasActividadesArray[j].fila).addClass("form-control");

		}
	}
	// Fecha de vencimiento actividad

	for (var i = 0; i < countActividades; i++){

		$("#actividadVencimiento" + actividadesArray[i].fila).removeClass("campoTextoError");
		$("#actividadVencimiento" + actividadesArray[i].fila).addClass("form-control");

	}

}

// metodo para validar las fechas de las tareas
function validarfechasTareas(campoFechaTarea){

	// var fechaActividad=new
	// Date($(dato1).find("input[name=txtFechaDeVencimientoActividad]").val());
	var campoFechaActividad = $(campoFechaTarea).closest("div[name=actividadParticular]").find(
			"input[name^='actividadVencimiento']");
	var fechaActividad = new Date($(campoFechaActividad).val());
	var fechaTarea = new Date($(campoFechaTarea).val());
	var noCambiarFecha = null;
	if(fechaActividad < fechaTarea){
		$(campoFechaTarea).val("");
		// $("#messageExitoso").show();
		$(campoFechaTarea).closest("td").find("div[name=messageError]").html(
				"Esta fecha debe ser menor o igual a la fecha de vencimiento de la actividad.");
		$(campoFechaTarea).closest("td").find("div[name=messageError]").show();
		noCambiarFecha = true;

	}else{
		$(campoFechaTarea).closest("td").find("div[name=messageError]").hide();
		noCambiarFecha = false;

	}
	return noCambiarFecha;
}

// Metodo para validar las fechas de las tareas cuando se cambia la fecha de la
// actividad
function validarFechasDesdeActividad(campoFechaActividad){

	var fechaActividad = new Date($(campoFechaActividad).val());
	$(campoFechaActividad).closest("div[name=actividadParticular]").find("input[name^='vencimiento']").each(
			function(ind, dato){

				var fechaTarea = new Date($(dato).val());
				if(fechaActividad < fechaTarea){
					$(dato).val("");

					$(dato).closest("td").find("div[name=messageError]").html("Fecha mayor a la de la actividad.");
					$(dato).closest("td").find("div[name=messageError]").show();
				}

			});

}

function guardarActividades(CodigosEquipo){

	// Bloque para ingresar las actividades y tareas del caso
	// if(countActividades>0){
	var data_caso = "_csrf=" + $("#_csrf").val();
	for (var ind = 0; ind < countActividades; ind++) {
		var cantTareas = actividadesArray[ind].cantidadTareas;
		var tareasActividadesArray = actividadesArray[ind].tareas;
		$("#actividadVencimiento" + actividadesArray[ind].fila).val();

		data_caso += "&listaActividadesCaso[" + ind + "].nombreActividad=" + actividadesArray[ind].nombreActividad;
		data_caso += "&listaActividadesCaso["
				+ ind
				+ "].numeroDiasAntesAlertas="
				+ $("#actividadVencimiento" + actividadesArray[ind].fila).closest("div[name=actividadParticular]")
						.find("input[name = txtNumerosdiasAntesActividad]").val();
		data_caso += "&listaActividadesCaso["
				+ ind
				+ "].numeroAlertasPorDia="
				+ $("#actividadVencimiento" + actividadesArray[ind].fila).closest("div[name=actividadParticular]")
						.find("input[name = txtNumeroNotificacionesActividad]").val();
		data_caso += "&listaActividadesCaso["
				+ ind
				+ "].esActividadPropia="
				+ $("#actividadVencimiento" + actividadesArray[ind].fila).closest("div[name=actividadParticular]")
						.find("input[name = txtEsActividadPropia]").val();
		data_caso += "&listaActividadesCaso[" + ind + "].fechaLimite="
				+ new Date($("#actividadVencimiento" + actividadesArray[ind].fila).val());
		data_caso += "&listaActividadesCaso["
				+ ind
				+ "].finalizada="
				+ $("#actividadVencimiento" + actividadesArray[ind].fila).closest("div[name=actividadParticular]")
						.find("select[name = cmbEstadoActividad]").val();
		data_caso += "&listaActividadesCaso[" + ind + "].usuarioCreacion=" + $("#idusercreacion").val();
		data_caso += "&listaActividadesCaso[" + ind + "].usuarioUltimaModificacion=" + $("#idusercreacion").val();
		data_caso += "&listaActividadesCaso[" + ind + "].snActiva=" + "S";

		for (var ind1 = 0; ind1 < cantTareas; ind1++){

			data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1 + "].tarea="
					+ $("#nombreTarea" + tareasActividadesArray[ind1].fila).val();
			data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1 + "].detalle="
					+ $("#detalleTarea" + tareasActividadesArray[ind1].fila).val();
			data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1 + "].fechaLimite="
					+ new Date($("#vencimiento" + tareasActividadesArray[ind1].fila).val());
			data_caso += "&listaActividadesCaso["
					+ ind
					+ "].tareaParticularCasoSet["
					+ ind1
					+ "].finalizada="
					+ $("#nombreTarea" + tareasActividadesArray[ind1].fila).closest("tr").find(
							"select[name ^='cmbEstadoTareas']").val();
			data_caso += "&listaActividadesCaso["
					+ ind
					+ "].tareaParticularCasoSet["
					+ ind1
					+ "].esTareaPropia="
					+ $("#nombreTarea" + tareasActividadesArray[ind1].fila).closest("tr").find(
							"input[name ^='txtTareaPropia']").val();
			data_caso += "&listaActividadesCaso["
					+ ind
					+ "].tareaParticularCasoSet["
					+ ind1
					+ "].numeroDiasAntesAlertas="
					+ $("#nombreTarea" + tareasActividadesArray[ind1].fila).closest("tr").find(
							"input[name ^='numeroDeDias']").val();
			data_caso += "&listaActividadesCaso["
					+ ind
					+ "].tareaParticularCasoSet["
					+ ind1
					+ "].numeroAlertasPorDia="
					+ $("#nombreTarea" + tareasActividadesArray[ind1].fila).closest("tr").closest(
							"div[name=actividadParticular]").find("input[name ^='numeroDeAlertas']").val();
			data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1 + "].usuarioCreacion="
					+ $("#idusercreacion").val();
			data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1
					+ "].usuarioUltimaModificacion=" + $("#idusercreacion").val();
			data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1 + "].snActiva=" + "S";

			var identificaciones = $("#responsable" + tareasActividadesArray[ind1].fila).val();
			$(identificaciones).each(
					function(index1, identificacion){
						// var posicion=0;
						$.each(CodigosEquipo.equipoCaso, function(ind2, optionData){
							if(optionData.identificacion == identificacion){
								data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1
										+ "].responsablesTareas[" + index1 + "].responsableTareaPK.codigoEquipoCaso="
										+ optionData.codigoEquipoCaso;
								data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1
										+ "].responsablesTareas[" + index1 + "].responsableTareaPK.codigoCaso="
										+ CodigosEquipo.codigoCaso;
								data_caso += "&listaActividadesCaso[" + ind + "].tareaParticularCasoSet[" + ind1
										+ "].responsablesTareas[" + index1 + "].responsableTareaPK.codigoMiembro="
										+ optionData.codigoMiembro;
								// posicion=posicion+1;
							}

						});
					});
		}
	}

	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/caso/guardarActividades",
		// data : $("#idFormulario").serialize(),
		data : data_caso,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			if(response.STATUS == "SUCCESS"){
				$("#modal-nuevoCaso").modal("hide");
				$("#messageExitoso").html("Se guard\u00f3 el caso exitosamente.");
				$("#messageExitoso").show();
				setTimeout("limpiarMensajeExito();", 10000);
				limpiarFormularioNuevoCaso();
				mostrarTabla();
			}else{
				$("#modal-nuevoCaso .modal-content").scrollTop(0);
				$("#messageError").html("No se fue posible guardar el caso.");
				$("#messageError").show();
				setTimeout("limpiarMensajeError();", 10000);

			}
			// alert(response.STATUS);
		},
		error : function(error){
			waitingDialog.hide();
			$("#modal-nuevoCaso .modal-content").scrollTop(0);
			$("#messageError").html("No se fue posible guardar el caso.");
			$("#messageError").show();
			setTimeout("limpiarMensajeError();", 10000);
		}
	});
	// }
	// else{
	// limpiarFormularioNuevoCaso();
	// $("#modal-nuevoCaso").modal("hide");
	// $("#messageExitoso").html("Se guard\u00f3 el caso exitosamente.");
	// $("#messageExitoso").show();
	// setTimeout("limpiarMensajeExito();",10000);
	// }

}

// validacionn de los campos nuevo caso

// metodo para validar primero los campos antes de guardar
function guardar(){

	if(validarFormulario()){
		doAjaxPostAdd();

		// limpiarFormularioNuevoCaso();

		mostrarTabla();
	}
}

function limpiarFormularioNuevoCaso(){

	$("#modal-nuevoCaso").html(formularioNuevoCaso);
	formularioNuevoCaso = $("#modal")[0].cloneNode(true);

}
// Metodo para mostrar los abogados asignados al caso
function asignarAbogadosAlCasoNuevoMiembro(){
	if($("#cmbMiembroAbogadoNuevoMiembro").val()){
		var option = $("#cmbMiembroAbogadoNuevoMiembro").val();
		var codigoAbogadoSeleccionado = "codigoAbogadoSeleccionado=" + $("#cmbMiembroAbogadoNuevoMiembro").val(); // abogadoSeleccionado.value;
		$("#cmbMiembroAbogadoNuevoMiembro option:selected").attr("disabled", "disabled");
//		waitingDialog.show('');
		$.ajax({
					dataType : "json",
					url : contexto + "/maestro/obtenerAbogadosSeleccionados",
					data : codigoAbogadoSeleccionado,
					success : function(response){
						$.each(response.abogados, function(index, optionData){

								var elementoAbogado = "";

								// bloque para mostrar la
								// informacion de los abogados crean
								// los elementos html
								// elementoAbogado+='<div
								// class="row" name="abogados">';

								elementoAbogado += '<div class="col-sm-12 b-r" name="abogados">';
								elementoAbogado += '<div class="row" >'; // <!--
								// Inicio
								// primera
								// fila
								// los
								// nombres
								// de
								// los
								// campos
								// -->

								elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;" >';
								elementoAbogado += '<label">';
								elementoAbogado += "NÚMERO DE IDENTIFICACIÓN";
								elementoAbogado += '</label">';
								elementoAbogado += '</div>';

								elementoAbogado += '<div class="col-sm-6" style="padding-bottom: 1%;"  >';
								elementoAbogado += '<label">';
								elementoAbogado += "NOMBRE ABOGADO";
								elementoAbogado += '</label">';
								elementoAbogado += '</div>';

								elementoAbogado += '</div>';

								elementoAbogado += '<div class="row" >';

								elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;" >';
								elementoAbogado += '<label name="identificacion">';
								elementoAbogado += optionData.codigo;
								elementoAbogado += '</label">';
								elementoAbogado += '</div>';

								elementoAbogado += '<div class="col-sm-6" style="padding-bottom: 1%;"  >';
								elementoAbogado += '<label name="nombreAbogado">';
								elementoAbogado += optionData.nombre;
								elementoAbogado += '</label">';
								elementoAbogado += '</div>';

								elementoAbogado += '</div>';

								elementoAbogado += '<div class="row" >';

								elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;"  >';
								elementoAbogado += '<label">';
								elementoAbogado += "NÚMERO DE TARJETA PROFESIONAL";
								elementoAbogado += '</label">';
								elementoAbogado += '</div>';

								elementoAbogado += '<div class="col-sm-6" style="padding-bottom: 1%;"  >';
								elementoAbogado += '<label">';
								elementoAbogado += "APELLIDO";
								elementoAbogado += '</label">';
								elementoAbogado += '</div>';
								
								elementoAbogado += '</div>';

//								elementoAbogado += '<div class="row" >';
//								
//								elementoAbogado += '<div class="col-sm-6" style="padding-bottom: 1%;"  >';
//								elementoAbogado += '<label">';
//								elementoAbogado += "CELULAR";
//								elementoAbogado += '</label">';
//								elementoAbogado += '</div>';
//
//								elementoAbogado += '</div>'; // <!--
								// Fin
								// segunda
								// fila
								// los
								// valores
								// de
								// los
								// campos
								// -->

								elementoAbogado += '<div class="row">'; // <!--
								// Inicio
								// tercera
								// fila
								// los
								// nombres
								// de
								// los
								// campos
								// -->

								elementoAbogado += '<div class="col-sm-6 b-r"  style="padding-bottom: 1%;" >';
								elementoAbogado += '<label name="tarjetaProfesional">';
								elementoAbogado += optionData.tarjetaProfesional;
								elementoAbogado += '</label">';
								elementoAbogado += '</div>';

								elementoAbogado += '<div class="col-sm-6"  style="padding-bottom: 1%;" >';
								elementoAbogado += '<label name="apellidoAbogado">';
								elementoAbogado += optionData.apellido;
								elementoAbogado += '</label>';
								elementoAbogado += '</div>';
								
								elementoAbogado += '</div>';

//								elementoAbogado += '<div class="row">';
//								
//								elementoAbogado += '<div class="col-sm-6 b-r"  style="padding-bottom: 1%;" >';
//								elementoAbogado += '<label name="celularAbogado">';
//								elementoAbogado += optionData.celular;
//								elementoAbogado += '</label>';
//								elementoAbogado += '</div>';
//								
//								elementoAbogado += '</div>';

								elementoAbogado += '<div class="row">';

								elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;"  >';
								elementoAbogado += '<label">';
								elementoAbogado += "EMAIL";
								elementoAbogado += '</label">';
								elementoAbogado += '</div>';

								elementoAbogado += '<div class="col-sm-6" style="padding-bottom: 1%;" >';
								elementoAbogado += '<label">';
								elementoAbogado += "TELEFONO";
								elementoAbogado += '</label">';
								elementoAbogado += '</div>';

								elementoAbogado += '</div>';

								elementoAbogado += '<div class="row">';

								elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;" >';
								elementoAbogado += '<label name="correoAbogado">';
								elementoAbogado += optionData.correo;
								elementoAbogado += '</label>';
								elementoAbogado += '</div>';

								elementoAbogado += '<div class="col-sm-6" style="padding-bottom: 1%;" >';
								elementoAbogado += '<label name="telefonoAbogado">';
								elementoAbogado += optionData.telefono;
								elementoAbogado += '</label>';
								elementoAbogado += '</div>';
								
								elementoAbogado += '</div>';
								
								elementoAbogado += '<div class="row">';
								
								elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;height: 26px;" >';
								elementoAbogado += '<label">';
								elementoAbogado += "CELULAR";
								elementoAbogado += '</label">';
								elementoAbogado += '</div>';
								elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;" >';
								elementoAbogado += '<label">';
								elementoAbogado += "DIRECCION";
								elementoAbogado += '</label">';
								elementoAbogado += '</div>';

								elementoAbogado += '</div>';
								
								elementoAbogado += '<div class="row">';
								
								elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;height: 26px;" >';
								elementoAbogado += '<label name="celularAbogado">';
								elementoAbogado += optionData.celular;
								elementoAbogado += '</label>';
								elementoAbogado += '</div>';
								elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;" >';
								elementoAbogado += '<label name="direccionAbogado">';
								elementoAbogado += optionData.direccion;
								elementoAbogado += '</label>';
								elementoAbogado += '</div>';

								elementoAbogado += '</div>'; // <!--
								// Fin
								// cuarta
								// fila
								// los
								// valores
								// de
								// los
								// campos
								// -->
								elementoAbogado += '<div class="row" style="text-align: right;padding-right: 4%;">';
								elementoAbogado += '<a href="#" name="ElimnarAbogado" onclick="borrarAbogado(this,'
										+ option
										+ ' )" class="btn btn-success btn-circle .btn-xs" style="background-color: red; border:0;" title="Eliminar Abogado">';
								elementoAbogado += '<i class="glyphicon glyphicon-minus" ></i> </a>';
								elementoAbogado += '</div>';
								elementoAbogado += '<br><hr class="hr-line-solid"><br>';
								elementoAbogado += '</div>';
								$("#equipoAbogadosNuevos").append(elementoAbogado);// se
								// agrega
								// los
								// elementos
								// al
								// Div
								// de
								// equipo
								// Abogados
							});
					}
				});
	}else{
		$("#messageErrorAbogado").html("Por favor seleccione un abogado");
		$("#messageErrorAbogado").show();
		setTimeout(function(){
			$("#messageErrorAbogado").html("");
			$("#messageErrorAbogado").hide();
		}, 10000);
	}

	cargarResponsableTareas1();
	$("#cmbMiembroAbogado").val("");
	// optionData.codigo
	// habilitarResponsablesDesdeAbogados();
}

function elementoAbogado(option, optionData){
	var elementoAbogado="";
	// bloque para mostrar la informacion de los abogados crean los elementos html
	//elementoAbogado+='<div class="row" name="abogados">';
	
	elementoAbogado += '<div class="col-sm-12 b-r" name="abogados">';
	
	
	elementoAbogado += '<label name="lblNacimientoUsuario" class="hide">';
	elementoAbogado += optionData.nacimientoUsuario;
	elementoAbogado += '</label>';
	
	
	elementoAbogado += '<label name="lblTipoDocumento" class="hide">';
	elementoAbogado += optionData.tipoDocumento;
	elementoAbogado += '</label>';
	
	
	
	elementoAbogado += '<div class="row" >'; // <!-- Inicio primera fila los nombres de los campos -->
	elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;" >';
	elementoAbogado += '<label>';
	elementoAbogado += "NÚMERO DE IDENTIFICACIÓN"; 
	elementoAbogado += '</label">';
	elementoAbogado += '</div>';
	elementoAbogado += '<div class="col-sm-6" style="padding-bottom: 1%;"  >';
	elementoAbogado += '<label>';
	elementoAbogado += "NOMBRE ABOGADO";
	elementoAbogado += '</label">';
	elementoAbogado += '</div>';
	elementoAbogado += '</div>';
	elementoAbogado += '<div class="row" >';
	elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;" >';
	elementoAbogado += '<label name="identificacion">';
	elementoAbogado += optionData.codigo;
	elementoAbogado += '</label">';
	elementoAbogado += '</div>';
	elementoAbogado += '<div class="col-sm-6" style="padding-bottom: 1%;"  >';
	elementoAbogado += '<label name="nombreAbogado">';
	elementoAbogado += optionData.nombre;
	elementoAbogado += '</label">';
	elementoAbogado += '</div>';
	elementoAbogado += '</div>';
	elementoAbogado += '<div class="row" >';
	elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;"  >';
	elementoAbogado += '<label">';
	elementoAbogado += "TIPO DE DOCUMENTO";
	elementoAbogado += '</label">';
	elementoAbogado += '</div>';
	elementoAbogado += '<div class="col-sm-6" style="padding-bottom: 1%;"  >';
	elementoAbogado += '<label">';
	elementoAbogado += "APELLIDO ABOGADO";
	elementoAbogado += '</label">';
	elementoAbogado += '</div>';
	elementoAbogado += '</div>';
	elementoAbogado += '<div class="row">';
	elementoAbogado += '<div class="col-sm-6 b-r"  style="padding-bottom: 1%;" >';
	elementoAbogado += '<label name="tipoDocumento">';
	elementoAbogado += optionData.tipoDocumento;
	elementoAbogado += '</label">';
	elementoAbogado += '<label style="display:none" name="tipoDocumentoCodigo">';
	elementoAbogado += optionData.tipoDocumentoCodigo;
	elementoAbogado += '</label">';	
	elementoAbogado += '</div>';
	elementoAbogado += '<div class="col-sm-6"  style="padding-bottom: 1%;" >';
	elementoAbogado += '<label name="apellido">';
	elementoAbogado += optionData.apellido; 
	elementoAbogado += '</label>';
	elementoAbogado += '</div>';
	elementoAbogado += '</div>'; 
	elementoAbogado += '<div class="row" >';
	elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;"  >';
	elementoAbogado += '<label">';
	elementoAbogado += "NÚMERO DE TARJETA PROFESIONAL";
	elementoAbogado += '</label">';
	elementoAbogado += '</div>';
	elementoAbogado += '<div class="col-sm-6" style="padding-bottom: 1%;"  >';
	elementoAbogado += '<label">';
	elementoAbogado += "TELEFONO";
	elementoAbogado += '</label">';
	elementoAbogado += '</div>';
	elementoAbogado += '</div>'; //<!-- Fin segunda fila los valores de los campos -->
	elementoAbogado += '<div class="row">'; //<!-- Inicio tercera fila los nombres de los campos -->
	elementoAbogado += '<div class="col-sm-6 b-r"  style="padding-bottom: 1%;" >';
	elementoAbogado += '<label name="tarjetaProfesional">';
	elementoAbogado += optionData.tarjetaProfesional;
	elementoAbogado += '</label">';
	elementoAbogado += '</div>';
	elementoAbogado += '<div class="col-sm-6"  style="padding-bottom: 1%;" >';
	elementoAbogado += '<label name="telefonoAbogado">';
	elementoAbogado += optionData.telefono; 
	elementoAbogado += '</label>';
	elementoAbogado += '</div>';
	elementoAbogado += '</div>'; 
	elementoAbogado += '<div class="row">';		
	elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;"  >';
	elementoAbogado += '<label">';
	elementoAbogado += "EMAIL";
	elementoAbogado += '</label">';
	elementoAbogado += '</div>';	
	elementoAbogado += '<div class="col-sm-6" style="padding-bottom: 1%;" >';
	elementoAbogado += '<label">';
	elementoAbogado += "DIRECCION";
	elementoAbogado += '</label">';
	elementoAbogado += '</div>';
	elementoAbogado += '</div>';	
	elementoAbogado += '<div class="row">';	
	elementoAbogado += '<div class="col-sm-6 b-r" style="padding-bottom: 1%;" >';
	elementoAbogado += '<label name="correoAbogado">';
	elementoAbogado += optionData.correo; 
	elementoAbogado += '</label>';
	elementoAbogado += '</div>';
	elementoAbogado += '<div class="col-sm-6" style="padding-bottom: 1%;" >';
	elementoAbogado += '<label name="direccionAbogado">';
	elementoAbogado += optionData.direccion; 
	elementoAbogado += '</label>';
	elementoAbogado += '</div>';
	elementoAbogado += '</div>';
	elementoAbogado += '<div class="row">';	//<!-- Fin cuarta fila los valores de los campos -->
	elementoAbogado += '<div class="row" style="text-align: right;padding-right: 4%;">';
	elementoAbogado += '<a href="#" name="ElimnarAbogado" onclick="borrarAbogado(this,'  + option +' )" class="btn btn-success btn-circle .btn-xs" style="background-color: red; border:0;" title="Eliminar Abogado">';
	elementoAbogado += '<i class="glyphicon glyphicon-minus" ></i> </a>';
	elementoAbogado += '</div>';
	elementoAbogado += '<br><hr class="hr-line-solid"><br>';
	elementoAbogado += '</div>';
	
	return elementoAbogado
}

function asignarAbogadosAlCaso() {

	if($("#cmbMiembroAbogado").val()){
		var option = $("#cmbMiembroAbogado").val();
		var codigoAbogadoSeleccionado = "codigoAbogadoSeleccionado=" + $("#cmbMiembroAbogado").val(); // abogadoSeleccionado.value;
		$("#cmbMiembroAbogado option:selected").attr("disabled", "disabled");
		waitingDialog.show('');
		$.ajax({
			dataType : "json",
			url : contexto + "/maestro/obtenerAbogadosSeleccionados",
			data : codigoAbogadoSeleccionado,
			success : function(response){
				waitingDialog.hide();
				$.each(response.abogados, function(index, optionData){
					$("#equipoAbogados").append(elementoAbogado(option, optionData));
					/**
					 * se agrega los elementos al Div de equipo Abogados
					 */
				});
			},
			error : function(e){
				waitingDialog.hide();
			}
		});
	}else{
		$("#messageErrorAbogado").html("Por favor seleccione un abogado");
		$("#messageErrorAbogado").show();
		setTimeout(function(){
			$("#messageErrorAbogado").html("");
			$("#messageErrorAbogado").hide();
		}, 10000);
	}

	cargarResponsableTareas1();
	$("#cmbMiembroAbogado").val("");
	// optionData.codigo
	// habilitarResponsablesDesdeAbogados();

}
// Metodo para borrar abogados del caso mientras se esta creando el caso

function borrarAbogado(boton, option){
	// $(boton.parentNode.parentNode).remove();
	$(boton).closest("div[name=abogados]").remove();
	// $(boton).closest("div[name=equipoAbogados]").remove();
	$("#cmbMiembroAbogado").val(option);
	$("#cmbMiembroAbogado option:selected").removeAttr("disabled");
	$("#cmbMiembroAbogado").val("");
	// validarResponsablesTareas();
	quitarResponsableTarea(option);
}

// Metodo para validar los responsables del caso se modifican los abogados
// mientras se crea el caso
function validarResponsablesTareas(){

	$("#cmbMiembroAbogado option:enabled:gt(0)").each(function(ind1, select1){

		$("#actividades").find("select[name=cmbResponsableTarea]").each(function(ind1, select2){

			if($(select1).val() == $(select2).val()){
				$(select2).find('option:selected').attr("disabled", "disabled");
				$(select2).val("");
			}else{

				var valor = $(select1).val();
				var valorAnterior = $(select2).val();
				$(select2).val(valor);
				$(select2).find('option:selected').attr("disabled", "disabled");
				$(select2).val(valorAnterior);

			}

		});

	});

}

function limpiarMensajeError(){
	$("#messageError").hide();
}

function limpiarMensajeExito(){
	$("#messageExitoso").hide();
	$("#messageExitosoCasoOfLine").hide();

}

//function getHtmlActividadParticular(filaActividad, optionData){
//
//	var html = '';
//
//	html += '<div class="col-sm-12" name="actividadParticular" style="padding-left: 3%; padding-right: 3%;">';
//	html += '<div class="panel panel-default">';
//	html += '	<div class="panel-heading">';
//	html += '<div class="row">';
//	html += '<div class="col-sm-9">';
//	html += '		<h4 class="panel-title">';
//	html += '			<a data-toggle="collapse" data-parent="#accordion"';
//	html += '				href="#collapseActividad' + optionData.codigo + '">' + optionData.nombre + '</a>';
//	html += '		</h4>';
//	html += '</div>';
//	html += '<div class="col-sm-3 text-right">';
//	html += '		<a href="javascript:void(0);"';
//	if(permisoEliminar){
//		html += '			onclick="mostrarModalEliminarActividad('
//				+ filaActividad
//				+ ',this)" class="btn btn-danger btn-circle btn-xs" title="Eliminar Actividad" name="btnActividad" alt="borrar actividad"> <i';
//	}else{
//		html += '			class="btn btn-danger btn-circle btn-xs disabled" title="Eliminar Actividad" name="btnActividad" alt="borrar actividad"> <i';
//	}
//	html += '			class="glyphicon glyphicon-trash" id="btnActividad" name="btnActividad"></i>';
//	html += '		</a>';
//	html += '		<a href="javascript:void(0);"';
//	if(permisoCrear){
//		html += '			onclick="adicionarTarea(\'tabla'
//				+ optionData.codigo
//				+ '\','
//				+ filaActividad
//				+ ')" class="btn btn-success btn-circle btn-xs" title="Adicionar Tarea" name="btTareas" alt="Adicionar tarea"> <i';
//	}else{
//		html += '			 class="btn btn-success btn-circle btn-xs disabled" title="Adicionar Tarea" name="btTareas" alt="Adicionar tarea"> <i';
//	}
//	html += '			class="glyphicon glyphicon-plus" id="btnTareas" name="btnTareas"></i>';
//	html += '		</a>';
//	html += '		<a href="javascript:void(0);"';
//	if(permisoEscritura){
//		html += '			onclick="modificarAlertas(this)" class="btn btn-primary btn-circle btn-xs" title="Modificar Alertas" id="btnAlertas" name="btnAlertas"> <i';
//	}else{
//		html += '			class="btn btn-primary btn-circle btn-xs disabled" title="Modificar Alertas" id="btnAlertas" name="btnAlertas"> <i';
//	}
//	html += '			class="glyphicon glyphicon-cog"></i>';
//	html += '		</a>';
//	html += '	</div>';
//	html += '</div>';
//	html += '	</div>';
//	html += '	<div id="collapseActividad' + optionData.codigo + '" class="panel-collapse collapse">';
//	html += '		<div class="panel-body" id="panelActividad' + optionData.codigo + '">';
//	html += '<div class="row">';
//	html += '<div class="col-sm-3" style="margin-bottom: 1%;">';
//	html += '<label>' + tituloNumeroDias + '</label>';
//	html += '<input type="text" class="form-control" id="txtNumerosdiasAntesActividad" name="txtNumerosdiasAntesActividad" onkeydown="return soloNumeros(event)"  maxlength="15" >';
//	html += '</div>';
//	html += '<div class="col-sm-3" style="margin-bottom: 1%;">';
//	html += '<label>' + tituloNotificaciones + '</label>';
//	html += '<input type="text" class="form-control" id="txtNumeroNotificacionesActividad" name="txtNumeroNotificacionesActividad" onkeydown="return soloNumeros(event)" maxlength="15" >';
//	html += '<input type="hidden" class="form-control" id="txtEsActividadPropia" name="txtEsActividadPropia" value="N">';
//	html += '</div>';
//	html += '<div class="col-sm-3" style="margin-bottom: 1%;"><label>'
//			+ tituloFechaVencimiento
//			+ '</label><span class="text-danger"> *</span><input type="date"  class="form-control" id="actividadVencimiento'
//			+ filaActividad + '" name="actividadVencimiento' + (filaActividad - 1) + '"' + metodoOnchageActividad
//			+ '></div> ';
//	html += '<div class="col-sm-3" style="margin-bottom: 1%;">';
//	html += '<label>' + tituloEstado + '</label><span class="text-danger"> *</span>';
//	html += '<select class="form-control" onchange="validarEstadoTareas(this)" onfocus="obtenerEstadoActual(this)"';
//	html += '				id="cmbEstadoActividad"';
//	html += '				name="cmbEstadoActividad" >';
//	html += '					<option value="N"> Pendiente </option>';
//	html += '					<option value = "S" > Completada </option></select>';
//	html += '</div>';
//	html += '</div>';
//	html += '<div class="row">';
//	html += '		<div class="alert alert-danger hide" name="actividadError"></div>';
//	html += '</div>';
//	html += '<div class="row">';
//	html += '			<table class="table table-bordered table-actividades" id="tabla' + optionData.codigo + '">';
//	html += '				<thead>';
//	html += '				<tr>';
//	html += '					<th style="width:15%">Tarea<span class="text-danger"> *</span></th>';
//	html += '					<th style="width:15%">Detalle<span class="text-danger"> *</span></th>';
//	html += '					<th style="width:20%">Responsable<span class="text-danger"> *</span></th>';
//	html += '					<th style="width:25%">Estado<span class="text-danger"> *</span></th>';
//	html += '					<th class="hide">N\u00FAmero de d\u00EDas</th>';
//	html += '					<th class="hide">N\u00FAmero de notificaciones</th>';
//	html += '					<th style="width:20%">Fecha Vencimiento<span class="text-danger"> *</span></th>';
//	html += '					<th style="width:5%">Acción</th>';
//	html += '				</tr>';
//	html += '				</thead>';
//	html += '				<tbody>';
//
//	return html;
//}

//function getHtmlFilaTarea(filaActividad, optionData, tituloFechaVencimiento, tituloEstado, tituloNotificaciones, 
//		tituloNumeroDias){
//	var html = '';
//
//	html += '				<tr id="' + optionData.codigo + 'Tarea">';
//	html += '					<td style="width:15%">' + optionData.nombreTarea + '<input type="hidden" value="'
//			+ optionData.nombreTarea + '" id="nombreTarea' + filaTareaActividad + '" name="nombreTarea'
//			+ filaTareaActividad + '"></td>';
//	html += '					<td style="width:15%">' + optionData.detalleTarea + '<input type="hidden" value="'
//			+ optionData.detalleTarea + '" id="detalleTarea' + filaTareaActividad + '" name="detalleTarea'
//			+ filaTareaActividad + '">';
//	html += '					<input type="hidden" class="form-control" id="txtTareaPropia" name="txtTareaPropia" value="N"></td>';
//	html += '					<td style="width:20%"><select  id="responsable' + filaTareaActividad + '" name="responsable'
//			+ filaTareaActividad + '" multiple="multiple"></select></td>';
//	html += "					<td style='width:25%'><select class='form-control' id='cmbEstadoTareas" + filaTareaActividad
//			+ "' name='cmbEstadoTareas" + filaTareaActividad + "'>";
//	html += "					<option value='N'> Pendiente </option>";
//	html += "					<option value = 'S' > Completada </option></select></td>";
//	html += "					<td class='hide'><input type='text' class='form-control' id='numeroDeDias" + filaTareaActividad
//			+ "' maxlength='3' name='numeroDeDias" + filaTareaActividad + "'></td>";
//	html += "					<td class='hide'><input type='text' class='form-control' id='numeroDeAlertas" + filaTareaActividad
//			+ "' maxlength='3' name='numeroDeAlertas" + filaTareaActividad + "'></td>";
//	html += '					<td style="width:20%">' + mensaje + '<input type="date" class="form-control" id="vencimiento'
//			+ filaTareaActividad + '" name="vencimiento' + filaTareaActividad + '"' + metodoOnchage + '></td>';
//	html += '					<td style="width:5%">';
//	if(permisoEliminar){
//		html += '					<a href="javascript:void(0);" class="btn btn-danger btn-circle btn-xs" title="Eliminar Tarea" onclick="mostrarModalEliminarTarea('
//				+ filaActividad
//				+ ','
//				+ filaTareaActividad
//				+ ',this);" ><i class="glyphicon glyphicon-trash"></i></a></td>';
//	}else{
//		html += '					<a href="javascript:void(0);" class="btn btn-danger btn-circle btn-xs disabled" title="Eliminar Tarea" ><i class="glyphicon glyphicon-trash"></i></a></td>';
//	}
//	html += '				</tr>';
//
//	return html
//}

//Metodo para cargar actividades tipo caso
function cargarActividadesTipoCaso(selectTipoCaso) {
	
	var codigoTipoCaso = "codigoTipoCaso=" +$(selectTipoCaso).find('option:selected').val();

	
	$.ajax({
		  dataType: "json",
		  url: contexto + "/maestro/obtenerActividadesTipoCaso",
		  data: codigoTipoCaso,
		  success: function (response) {
			  var html='';  
			  
			  borrarActividadesTipoCaso();
			  $.each(response.actividadesTipoCaso, function(index, optionData) {
				   
				    elementoActividad = new Object();
					elementoActividad.fila= filaActividad;
				    elementoActividad.codigoActividad=optionData.codigo;
				    elementoActividad.nombreActividad = optionData.nombre;
				    
				    
				    var tareasActividadesArray = new Array();
				    var countTareasActividades = 0;
					
					html+='<div class="col-sm-12" name="actividadParticular" style="padding-left: 3%; padding-right: 3%;">';
			
					html+='<div class="panel panel-default">';
					html+='	<div class="panel-heading">';
					
					
					html+='<div class="row">';
					
					html+='<div class="col-sm-9">';
					
					html+='		<h4 class="panel-title">';
					html+='			<a data-toggle="collapse" data-parent="#accordion"';
					html+='				href="#collapseActividad'+optionData.codigo+'">'+optionData.nombre+'</a>';
					html+='		</h4>';
					
					html+='</div>';
					
					html+='<div class="col-sm-3 text-right">';
					//////
					html+='		<a href="javascript:void(0);"';
					html+='			onclick="mostrarModalEliminarActividad('+filaActividad+',this)" class="btn btn-danger btn-circle .btn-xs" title="Eliminar Actividad"';
					html+='			name="btnActividad" alt="borrar actividad"> <i';
					html+='			class="glyphicon glyphicon-trash" id="btnActividad" name="btnActividad"></i>';
					html+='		</a>';
					/////
					html+='		<a href="javascript:void(0);"';
					html+='			onclick="adicionarTarea(\'tabla'+optionData.codigo+'\','+filaActividad+')" class="btn btn-success btn-circle .btn-xs" title="Adicionar Tarea"';
					html+='			name="btTareas" alt="Adicionar tarea"> <i';
					html+='			class="glyphicon glyphicon-plus" id="btnTareas" name="btnTareas"></i>';
					html+='		</a>';
					html+='		<a href="javascript:void(0);"';
					html+='			onclick="modificarAlertas(this)" class="btn btn-primary btn-circle .btn-xs" title="Modificar Alertas"';
					html+='			id="btnAlertas" name="btnAlertas"> <i';
					html+='			class="glyphicon glyphicon-cog"></i>';
					html+='		</a>';
					
					html+='	</div>';
					
					html+='</div>';
					
					html+='	</div>';
					html+='	<div id="collapseActividad'+optionData.codigo+'" class="panel-collapse collapse">';
					html+='		<div class="panel-body" id="panelActividad'+optionData.codigo+'">';
					
					html+='<div class="row">';
					html+='<div class="col-sm-3" style="margin-bottom: 1%;">';
					html+='<label>' + tituloNumeroDias + '</label>';
					html+='<input type="text" class="form-control" id="txtNumerosdiasAntesActividad" name="txtNumerosdiasAntesActividad" onkeydown="return soloNumeros(event)"  maxlength="15" >';
					html+='</div>';
					html+='<div class="col-sm-3" style="margin-bottom: 1%;">';
					html+='<label>' + tituloNotificaciones + '</label>';
					html+='<input type="text" class="form-control" id="txtNumeroNotificacionesActividad" name="txtNumeroNotificacionesActividad" onkeydown="return soloNumeros(event)" maxlength="15" >';
					html+='<input type="hidden" class="form-control" id="txtEsActividadPropia" name="txtEsActividadPropia" value="N">';
					html+='</div>';
					
					html+='<div class="col-sm-3" style="margin-bottom: 1%;"><label>' + tituloFechaVencimiento + '</label><span class="text-danger">'+(optionData.snObligatorioFechaVencimiento=="S"?"*":"")+'</span><input type="date"  class="form-control" id="actividadVencimiento'+filaActividad+'" '+(optionData.snObligatorioFechaVencimiento=="S"?"required":"")+' name="actividadVencimiento'+filaActividad+'"'+metodoOnchageActividad+'></div> ';
					
					html+='<div class="col-sm-3" style="margin-bottom: 1%;">';
					html+='<label>' + tituloEstado + '</label><span class="text-danger"> *</span>';
					html+='<select class="form-control" onchange="validarEstadoTareas(this)" onfocus="obtenerEstadoActual(this)"';
					html+='				id="cmbEstadoActividad"';
					html+='				name="cmbEstadoActividad" >';
					html+='					<option value="N"> Pendiente </option>';
					html+='					<option value = "S" > Completada </option></select>';
					html+='</div>';
					html+='</div>';
					
					html+='<div class="row">';
					html+='		<div class="alert alert-danger hide" name="actividadError"></div>';
					html+='</div>';
					html+='<div class="row">';
					
					html+='			<table class="table table-bordered table-actividades" id="tabla'+optionData.codigo+'">';
					html+='				<thead>';
					html+='				<tr>';
					html+='					<th style="width:15%">Tarea<span class="text-danger"> *</span></th>';
					html+='					<th style="width:15%">Detalle<span class="text-danger"> *</span></th>';
					html+='					<th style="width:20%">Responsable<span class="text-danger"> *</span></th>';
					html+='					<th style="width:25%">Estado<span class="text-danger"> *</span></th>';
					html+='					<th class="hide">N\u00FAmero de d\u00EDas</th>';
					html+='					<th class="hide">N\u00FAmero de notificaciones</th>';
					html+='					<th style="width:20%">Fecha Vencimiento<span class="text-danger"> *</span></th>';
					html+='					<th style="width:5%">Acción</th>';
					html+='				</tr>';
					html+='				</thead>';
					html+='				<tbody>';
					$.each(optionData.tareasActividades, function(indexTarea, optionDataTarea) {
						
						elementoTareaActividad = new Object();
					    elementoTareaActividad.fila= filaTareaActividad;
					   
					    
					    
						
						html+='				<tr id="'+optionData.codigo+'Tarea">';
						html+='					<td style="width:15%">'+optionDataTarea.nombreTarea+'<input type="hidden" value="'+optionDataTarea.nombreTarea+'" id="nombreTarea'+filaTareaActividad+'" name="nombreTarea'+filaTareaActividad+'"></td>';
						html+='					<td style="width:15%">'+optionDataTarea.detalleTarea+'<input type="hidden" value="'+optionDataTarea.detalleTarea+'" id="detalleTarea'+filaTareaActividad+'" name="detalleTarea'+filaTareaActividad+'">';
						html+='					<input type="hidden" class="form-control" id="txtTareaPropia" name="txtTareaPropia" value="N"></td>';
						html+='					<td style="width:20%"><select  id="responsable'+filaTareaActividad+'" name="responsable'+filaTareaActividad+'" multiple="multiple"></select></td>';
						
						html+="					<td style='width:25%'><select class='form-control' id='cmbEstadoTareas"+filaTareaActividad+"' name='cmbEstadoTareas"+filaTareaActividad+"'>";
						
						html+="					<option value='N'> Pendiente </option>";
						html+="					<option value = 'S' > Completada </option></select></td>";
						
						html+="					<td class='hide'><input type='text' class='form-control' id='numeroDeDias"+filaTareaActividad+"' maxlength='3' name='numeroDeDias"+filaTareaActividad+"'></td>";
						html+="					<td class='hide'><input type='text' class='form-control' id='numeroDeAlertas"+filaTareaActividad+"' maxlength='3' name='numeroDeAlertas"+filaTareaActividad+"'></td>";
						
						html+='					<td style="width:20%">'+mensaje+'<input type="date" class="form-control" id="vencimiento'+filaTareaActividad+'" name="vencimiento'+filaTareaActividad+'"'+metodoOnchage+'></td>';
						html+='					<td style="width:5%"><a href="javascript:void(0);" class="btn btn-danger btn-circle .btn-xs"  title="Eliminar Tarea"  onclick="mostrarModalEliminarTarea('+filaActividad+','+filaTareaActividad+',this);"  ><i class="glyphicon glyphicon-trash"></i></a></td>';
						html+='				</tr>';

						tareasActividadesArray[countTareasActividades]=elementoTareaActividad;
					    countTareasActividades++;
					    filaTareaActividad++;
					    
					    
					    
					});
					
					elementoActividad.tareas = tareasActividadesArray;
					elementoActividad.cantidadTareas=countTareasActividades;
					
					actividadesArray[countActividades]=elementoActividad;
					countActividades++;
					filaActividad++;
					
					html+='				</tbody>';
					html+='			</table>';
					html+='		</div>';
					html+='	</div>';
					html+='</div>';
					html+='</div>';
					html+='</div>';
			  });

			  $("#divEspacio").html(html);
			  $("#divEspacio").find("select[name^='responsable']").select2({
	    			placeholder: "Seleccione un responsable",
				    allowClear: true,
				    width: "100%"
		    	}).on("select2-selecting", function (select) {
		    		opcionOtroResponsable = select.object.element[0];
		    		if (select.val == -1) {
		    			$("#modal-ingresarOtroResponsable").modal("show");
		    		} else {
		    			codigosResponsablesNuevoCaso.push(select.val);
		    		}
		          });
				  cargarResponsableTareas("#divEspacio");
			  }});					
}

/*$("select[name^='responsable']").select2({
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
*/
function deleteTarea(filaActividad, filaTarea, tabla) {
	posicion=-1;
	$(tabla).closest("tr").remove();
	
	for (var i=0;i<countActividades;i++) {
		
		
		if (actividadesArray[i].fila == filaActividad) {
			var cantTareas=actividadesArray[i].cantidadTareas;
			var tareasActividadesArray=actividadesArray[i].tareas;
			
			for (var j=0;j<cantTareas;j++) {
				if (tareasActividadesArray[j].fila == filaTarea) {
					tareasActividadesArray.splice( j, 1 );
					cantTareas--;
					actividadesArray[i].cantidadTareas=cantTareas;
					break;
				}
			}
		}
	}
	//countTareas--;
}

function adicionarTarea(tabla, fila){
	var newRow=$("<tr id='"+contFilasTareasAdicionales+"Tarea'>");
	var cols="";
	cols+="<td style='width:15%'><input type='text' class='form-control' id='nombreTarea"+filaTareaActividad+"' name='nombreTarea"+filaTareaActividad+"'>" ;
	cols+="<input type='hidden' class='form-control' id='txtTareaPropia' name='txtTareaPropia' value='S'></td>";
	cols+="<td style='width:15%'><input type='text' class='form-control' id='detalleTarea"+filaTareaActividad+"' name='detalleTarea"+filaTareaActividad+"'></td>";
	cols+="<td style='width:20%'><select id='responsable"+filaTareaActividad+"' name='responsable"+filaTareaActividad+"' multiple='multiple'></select></td>";
	cols+="<td style='width:25%'><select class='form-control' id='cmbEstadoTareas"+filaTareaActividad+"' name='cmbEstadoTareas"+filaTareaActividad+"'>";
	
	cols+="					<option value='N'> Pendiente </option>";
	cols+="					<option value = 'S' > Completada </option></select></td>";
	
	cols+="<td class='hide'><input type='text' class='form-control' maxlength='3' id='numeroDeDias"+filaTareaActividad+"' name='numeroDeDias"+filaTareaActividad+"' onkeydown='return soloNumeros(event)'></td>";
	cols+="<td class='hide'><input type='text' class='form-control' maxlength='3' id='numeroDeAlertas"+filaTareaActividad+"' name='numeroDeAlertas"+filaTareaActividad+"' onkeydown='return soloNumeros(event)'></td>";
	
	cols+="<td style='width:20%'>" + mensaje + "<input type='date' class='form-control' id='vencimiento"+filaTareaActividad+"' name='vencimiento"+filaTareaActividad+"'"+metodoOnchage+"></td>";
  //cols+="<td><a href='javascript:void(0);' onclick='deleteTarea("+contFilasTareasAdicionales+","+contFilasTareasAdicionales+",this);'  ><i class='glyphicon glyphicon-minus' style'background-color:red;'></i></a></td>";
	cols+="<td style='width:5%'><a href='javascript:void(0);' onclick='deleteTarea("+fila+","+filaTareaActividad+",this);' class='btn btn-danger btn-circle .btn-xs'  title='Eliminar Tarea' ><i class='glyphicon glyphicon-trash'></i></a></td>";
	newRow.append(cols);
	

	$("#"+tabla).append(newRow);
	var panelActividad = newRow.closest("div[name=actividadParticular]");

	for (var i=0;i<countActividades;i++){
		
		
		if (actividadesArray[i].fila==fila){
			var cantTareas=actividadesArray[i].cantidadTareas;
			var tareasActividadesArray=actividadesArray[i].tareas;
			
			elementoTareaActividad = new Object();
		    elementoTareaActividad.fila= filaTareaActividad;
		    tareasActividadesArray[cantTareas]=elementoTareaActividad;
		    cantTareas++;
		    actividadesArray[i].cantidadTareas=cantTareas;
		    
		}
		
		
	}
	
	//Ingresar otro responsable en nuevo caso
	newRow.find("select[name^='responsable']").select2({
		placeholder: "Seleccione un responsable",
	    allowClear: true,
	    width: "100%"
	}).on("select2-selecting", function (select) {
		opcionOtroResponsable=select.object.element[0];
		if (select.val == -1) {
			$("#modal-ingresarOtroResponsable").modal("show");
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
	cargarResponsableTareas(newRow);
	//countTareas--;
	//cargarResponsablesTareasDetalle(panelActividad);
	//agregarResponsablesNuevaTarea(newRow);
	//cargarResponsablesTareasDetalle();
}

function getDivActividadParticular(el) {
	//param2 llega como objeto entero y deberia ser? 
	//el metodo es borrarActividad
	var html = '';
	var getBoton = function(color, icono, idIcono, param1, param2, title, metodo, name){
		html += '		<a href="javascript:void(0);"';
		if(metodo == false || metodo == 'false')
			html += '			class="btn ' + color + ' btn-circle btn-xs disabled" title="' + title + '"';
		else{
			if(param2 != '')
				html += '			onclick="' + metodo + '(' + param1 + ', ' + param2 + ')" class="btn ' + color
						+ ' btn-circle btn-xs" title="' + title + '"';
			else
				html += '			onclick="' + metodo + '(' + param1 + ')" class="btn ' + color
						+ ' btn-circle btn-xs" title="' + title + '"';
		}
		html += '			name="' + name + '"> <i';
		html += '			class="glyphicon ' + icono + '" id="' + idIcono + '" name="' + name + '"></i>';
		html += '		</a>';

		return html;
	}

	html += '	<br>';
	html += '<div  name="actividadParticular" style="padding-left: 3%; padding-right: 3%;">';
	html += '<div class="panel panel-default">';
	html += '	<div class="panel-heading">';

	html += '<div class="row">';

	html += '<div class="col-sm-9">';

	html += '		<h4 class="panel-title">';
	html += '			<a data-toggle="collapse" data-parent="#accordion"';
	html += '				href="#collapseActividad' + codigoActivad + '">' + el.nombreActividad + '</a>';
	html += '		</h4>';

	html += '</div>';

	html += '<div class="col-sm-3 text-right">';
	if(permisoEliminar){
		getBoton('btn-danger', 'glyphicon-trash', 'btnActividad', filaActividad, 'this', 'Eliminar Tarea',
				'borrarActividad', 'btnActividad');
	} else {
		getBoton('btn-danger', 'glyphicon-trash', 'btnActividad', filaActividad, 'this', 'Eliminar Tarea', false,
				'btnActividad');
	}
	if (permisoCrear) {
		getBoton('btn-success', 'glyphicon-plus', 'btnTareas', 'tabla' + codigoActivad, filaActividad,
				'Adicionar Tarea', 'adicionarTarea', 'btnTareas');
	} else {
		getBoton('btn-success', 'glyphicon-plus', 'btnTareas', 'tabla' + codigoActivad, filaActividad,
				'Adicionar Tarea', false, 'btnTareas');
	}
	if (permisoEscritura) {
		getBoton('btn-primary', 'glyphicon-cog', 'btnAlertas', 'this', '', 'Modificar Alertas', 'modificarAlertas',
				'btnAlertas');
	} else {
		getBoton('btn-primary', 'glyphicon-cog', 'btnAlertas', 'this', '', 'Modificar Alertas', false, 'btnAlertas');
	}

	html += '	</div>';

	html += '</div>';

	html += '	</div>';
	html += '	<div id="collapseActividad' + codigoActivad + '" class="panel-collapse collapse">';
	html += '		<div class="panel-body" id="panelActividad' + codigoActivad + '">';

	html += '<div class="row">';
	html += '<div class="col-sm-3" style="margin-bottom: 1%;">';
	html += '<label>' + tituloNumeroDias + '</label>';
	html += '<input type="text" class="form-control" id="txtNumerosdiasAntesActividad" name="txtNumerosdiasAntesActividad" onkeydown="return soloNumeros(event)" maxlength="15" >';
	html += '</div>';
	html += '<div class="col-sm-3" style="margin-bottom: 1%;">';
	html += '<label>' + tituloNotificaciones + '</label>';
	html += '<input type="text" class="form-control" id="txtNumeroNotificacionesActividad" name="txtNumeroNotificacionesActividad" onkeydown="return soloNumeros(event)" maxlength="15" >';
	html += '<input type="hidden" class="form-control" id="txtEsActividadPropia" name="txtEsActividadPropia" value="S">';
	html += '</div>';

	html += '<div class="col-sm-3" style="margin-bottom: 1%;"><label>'
			+ tituloFechaVencimiento
			+ '</label><span class="text-danger"> *</span><input type="date"  class="form-control" id="actividadVencimiento'
			+ (filaActividad - 1) + '" name="actividadVencimiento' + (filaActividad) + '"' + metodoOnchageActividad
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
	html += '				</tbody>';
	html += '			</table>';
	html += '		</div>';
	html += '	</div>';
	html += '</div>';
	html += '</div>';
	html += '	<br">';

	return html;
}

function nombreTareaExiste(nombreTarea){
	var tareasNuevas = $('#divEspacio1 [name=actividadParticular] div div div div h4 a');
	var tareasTipoCaso = $('#divEspacio [name=actividadParticular] div div div div h4 a');
	var tareasTipoCasoNuevas = [tareasNuevas, tareasTipoCaso];
	var encuentraNombreTarea = false;
		
	$.each(tareasTipoCasoNuevas, function(index, tareas){
		$.each(tareas, function(index, eleNombreTarea){
			if(eleNombreTarea && $.trim(nombreTarea.toLowerCase()) == $(eleNombreTarea).html().toLowerCase().trim()){
				encuentraNombreTarea = true;
				return false;
			}
		});
	});
	
	return encuentraNombreTarea;
}

function actividadesParticularesCaso(nombreActividad) {
	if(EstaVacio($("#modal-nuevoCaso #"+nombreActividad).val()) )
	{
		$("#messageErrorAactividad").html("Ingrese un nombre para la actividad");
		$("#messageErrorAactividad").show(); 
	}
	else { 
		if(nombreTareaExiste($("#modal-nuevoCaso #" + nombreActividad).val())){
			mostrarErrorActividad('Este nombre ya se ha utilizado');
		} else {
			var html = '';
			$("#messageErrorAactividad").hide();
			
			
			var html='';
			elementoActividad = new Object();
		    elementoActividad.fila= filaActividad;
		    elementoActividad.codigoActividad = codigoActivad;
		    elementoActividad.nombreActividad = $("#modal-nuevoCaso #"+nombreActividad).val();
		    
		    filasActividadesParticulares[countActividadesParticulares]=filaActividad; 
		    
		    var tareasActividadesArray = new Array();
		    var countTareasActividades = 0;
		    html+='	<br>';
		  	html+='<div  name="actividadParticular" style="padding-left: 3%; padding-right: 3%;">';
			html+='<div class="panel panel-default">';
			html+='	<div class="panel-heading">';
			
			html+='<div class="row">';
			
			html+='<div class="col-sm-9">';
			
			html+='		<h4 class="panel-title">';
			html+='			<a data-toggle="collapse" data-parent="#accordion"';
			html+='				href="#collapseActividad'+codigoActivad+'">' + $("#modal-nuevoCaso #"+nombreActividad).val() + '</a>';
			html+='		</h4>';
			
			html+='</div>';
			
			html+='<div class="col-sm-3 text-right">';
			//////
			html+='		<a href="javascript:void(0);"';
			html+='			onclick="borrarActividad('+filaActividad+',this)" class="btn btn-danger btn-circle .btn-xs" title="Eliminar Tarea"';
			html+='			name="btnActividad" title="Eliminar Actividad"> <i';
			html+='			class="glyphicon glyphicon-trash" id="btnActividad" name="btnActividad"></i>';
			html+='		</a>';
			/////
			html+='		<a href="javascript:void(0);"';
			html+='			onclick="adicionarTarea(\'tabla'+codigoActivad+'\','+filaActividad+')" class="btn btn-success btn-circle .btn-xs" title="Adicionar Tarea"';
			html+='			name="btTareas" title="Adicionar"> <i';
			html+='			class="glyphicon glyphicon-plus" id="btnTareas" name="btnTareas"></i>';	
			html+='		</a>';
			html+='		<a href="javascript:void(0);"';
			html+='			onclick="modificarAlertas(this)" class="btn btn-primary btn-circle .btn-xs" title="Modificar Alertas"';
			html+='			id="btnAlertas" name="btnAlertas"> <i';
			html+='			class="glyphicon glyphicon-cog"></i>';
			html+='		</a>';
			
			html+='	</div>';
			
			html+='</div>';
			
			html+='	</div>';
			html+='	<div id="collapseActividad'+codigoActivad+'" class="panel-collapse collapse">';
			html+='		<div class="panel-body" id="panelActividad'+codigoActivad+'">';
			
			html+='<div class="row">';
			html+='<div class="col-sm-3" style="margin-bottom: 1%;">';
			html+='<label>' + tituloNumeroDias + '</label>';
			html+='<input type="text" class="form-control" id="txtNumerosdiasAntesActividad" name="txtNumerosdiasAntesActividad" onkeydown="return soloNumeros(event)" maxlength="15" >';
			html+='</div>';
			html+='<div class="col-sm-3" style="margin-bottom: 1%;">';
			html+='<label>' + tituloNotificaciones + '</label>';
			html+='<input type="text" class="form-control" id="txtNumeroNotificacionesActividad" name="txtNumeroNotificacionesActividad" onkeydown="return soloNumeros(event)" maxlength="15" >';
			html+='<input type="hidden" class="form-control" id="txtEsActividadPropia" name="txtEsActividadPropia" value="S">';
			html+='</div>';
			
			html+='<div class="col-sm-3" style="margin-bottom: 1%;"><label>' + tituloFechaVencimiento + '</label><span class="text-danger"></span><input type="date"  class="form-control" id="actividadVencimiento'+filaActividad+'" name="actividadVencimiento'+filaActividad+'"'+metodoOnchageActividad+'></div> ';
			
			html+='<div class="col-sm-3" style="margin-bottom: 1%;">';
			html+='<label>' + tituloEstado + '</label><span class="text-danger"> *</span>';
			html+='<select class="form-control" onchange="validarEstadoTareas(this)" onfocus="obtenerEstadoActual(this)"';
			html+='				id="cmbEstadoActividad"';
			html+='				name="cmbEstadoActividad" >';
			html+='					<option value="N"> Pendiente </option>';
			html+='					<option value = "S" > Completada </option></select>';
			html+='</div>';
			html+='</div>';
			
			html+='<div class="row">';
			html+='		<div class="alert alert-danger hide" name="actividadError"></div>';
			html+='</div>';
			html+='<div class="row">';
			html+='			<table class="table table-bordered table-actividades" id="tabla'+codigoActivad+'">';
			html+='				<thead>';
			html+='				<tr>';
			html+='					<th style="width:15%">Tarea<span class="text-danger"> *</span></th>';
			html+='					<th style="width:15%">Detalle<span class="text-danger"> *</span></th>';
			html+='					<th style="width:20%">Responsable<span class="text-danger"> *</span></th>';
			html+='					<th style="width:25%">Estado<span class="text-danger"> *</span></th>';
			html+='					<th class="hide">N\u00FAmero de d\u00EDas</th>';
			html+='					<th class="hide">N\u00FAmero de notificaciones</th>';
			html+='					<th style="width:20%">Fecha Vencimiento<span class="text-danger"> *</span></th>';
			html+='					<th style="width:5%">Acción</th>';
			html+='				</tr>';
			html+='				</thead>';
			html+='				<tbody>';

			
			elementoActividad.tareas = tareasActividadesArray;
			elementoActividad.cantidadTareas=countTareasActividades;
			
			actividadesArray[countActividades]=elementoActividad;
			countActividades++;
			filaActividad++;
			countActividadesParticulares++;
			
			html+='				</tbody>';
			html+='			</table>';
			html+='		</div>';
			html+='	</div>';
			html+='</div>';
			html+='</div>';
		    html+='	<br">';
			
			$("#modal-nuevoCaso #divEspacio1").append(html);
			codigoActivad++;
			$("#modal-nuevoCaso #"+nombreActividad).val("");
			}
		}
	}

function borrarActividad(filaActividad, actividad) {
	$(actividad).closest("div[name=actividadParticular]").remove();
	for (var i = 0; i < countActividades; i++){

		if(actividadesArray[i].fila == filaActividad){
			// alert ("Eliminando actividad enla posicion "+i+" del array
			// "+actividadesArray);
			actividadesArray.splice(i, 1);
			for (var j = 0; j < countActividadesParticulares; j++){
				if(filasActividadesParticulares[j] == filaActividad){
					filasActividadesParticulares.splice(j, 1);
					countActividadesParticulares--;
					// break;
				}
			}
			countActividades--;
			break;
		}
	}
}

function borrarActividadesTipoCaso(){

	for (var i = 0; i < countActividades; i++){
		var borrarActividad = true;

		// filaActividad=actividadesArray[i].fila;

		for (var j = 0; j < countActividadesParticulares; j++){
			if(filasActividadesParticulares[j] == actividadesArray[i].fila){
				borrarActividad = false;
				break;
			}

		}

		if(borrarActividad){
			actividadesArray.splice(i, 1);
			countActividades--;
			i--;
		}

	}

}

function mostrarModalEliminarActividad(fila, boton){
	filaABorrar = fila;
	botonPresionado = boton;
	$("#modal-EliminarActividad").modal("show");
}

function mostrarModalEliminarTarea(fila, filaTarea, boton){
	filaABorrar = fila;
	filaABorrarTarea = filaTarea;
	botonPresionado = boton;
	$("#modal-EliminarTarea").modal("show");
}

function modalClose(){

	$("#modal-nuevoCaso").modal("hide");
	limpiarFormularioNuevoCaso();
}

function formatearCamposValores(formulario){
	$(formulario).find('#txtValorPrestamo').number(true, 0, '', '.');
	$(formulario).find('#txtPorcentajeInteresPrestamo').number(true, 2, ',', '.');
	$(formulario).find('#txtIntereses').number(true, 0, '', '.');
	$(formulario).find('#txtSaldoPrestamo').number(true, 0, ',', '.');
}

function mostrarMensajesError(objectErrores){
	$("#messageError").html("No se puede guardar el caso. Existen errores en el formulario.");
	$("#messageError").show();
	var mensajeError = "";
	if(objectErrores.erroresNuevoCaso.length){
		$.each(objectErrores.erroresNuevoCaso, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorNuevoCaso").html(mensajeError);
		$("#messageErrorNuevoCaso").show();
		mensajeError = "";
	}
	if(objectErrores.erroresAbogados.length){
		$.each(objectErrores.erroresAbogados, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorAbogado").html(mensajeError);
		$("#messageErrorAbogado").show();
		mensajeError = "";
	}
	if(objectErrores.erroresDemandados.length){
		$.each(objectErrores.erroresDemandados, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorDemandados").html(mensajeError);
		$("#messageErrorDemandados").show();
		mensajeError = "";
	}
	if(objectErrores.erroresVictimasDemandantes.length){
		$.each(objectErrores.erroresVictimasDemandantes, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorEquipoCaso").html(mensajeError);
		$("#messageErrorEquipoCaso").show();
		mensajeError = "";
	}
	if(objectErrores.erroresActividades.length){
		$.each(objectErrores.erroresActividades, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorActividades").html(mensajeError);
		$("#messageErrorActividades").show();
		mensajeError = "";
	}
	if(objectErrores.erroresLugarProceso.length){
		$.each(objectErrores.erroresLugarProceso, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorLugarProceso").html(mensajeError);
		$("#messageErrorLugarProceso").show();
		mensajeError = "";
	}
	if(objectErrores.erroresBienes.length){
		$.each(objectErrores.erroresBienes, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorBienesAfectados").html(mensajeError);
		$("#messageErrorBienesAfectados").show();
		mensajeError = "";
	}
	if(objectErrores.erroresRadicados.length){
		$.each(objectErrores.erroresRadicados, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorRadicados").html(mensajeError);
		$("#messageErrorRadicados").show();
		mensajeError = "";
	}
	if(objectErrores.erroresPrestamo.length){
		$.each(objectErrores.erroresPrestamo, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErrorPrestamos").html(mensajeError);
		$("#messageErrorPrestamos").show();
		mensajeError = "";
	}
}

function ocultarMensajesError(){
	$("#messageError").hide();
	$("#messageError").html("");
	$("#messageErrorNuevoCaso").hide();
	$("#messageErrorNuevoCaso").html("");
	$("#messageErrorAbogado").hide();
	$("#messageErrorAbogado").html("");
	$("#messageErrorDemandados").hide();
	$("#messageErrorDemandados").html("");
	$("#messageErrorEquipoCaso").hide();
	$("#messageErrorEquipoCaso").html("");
	$("#messageErrorActividades").hide();
	$("#messageErrorActividades").html("");
	$("#messageErrorLugarProceso").hide();
	$("#messageErrorLugarProceso").html("");
	$("#messageErrorBienesAfectados").hide();
	$("#messageErrorBienesAfectados").html("");
	$("#messageErrorRadicados").hide();
	$("#messageErrorRadicados").html("");
	$("#messageErrorPrestamos").hide();
	$("#messageErrorPrestamos").html("");
	$("#messageErrorComentarios").hide();
	$("#messageErrorComentarios").html("");
}

function abrirDetalleCaso(codigo){
	document.location.href = "detalleCaso?codigo=" + codigo;
}

function validarFormularioOtrosResponsables(){
	ocultarMensajesOtrosResponsables();
	limpiarErroresOtrosResponsables();
	var erroresOtrosResponsables = [];
	var isError = false;

	if(EstaVacio($("#txtNombresOtroResponsable").val())){
		$("#txtNombresOtroResponsable").addClass("campoTextoError");
		isError = true;
		erroresOtrosResponsables.push("Se debe ingresar un nombre.");
	}
	if(EstaVacio($("#txtApellidosOtroResponsable").val())){
		$("#txtApellidosOtroResponsable").addClass("campoTextoError");
		isError = true;
		erroresOtrosResponsables.push("Se debe ingresar un apellido");

	}
	if(EstaVacio($("#txtTelefonoOtroResponsable").val())){
		$("#txtTelefonoOtroResponsable").addClass("campoTextoError");
		isError = true;
		erroresOtrosResponsables.push("Se debe ingresar un telefono");

	}
	if(EstaVacio($("#txtCorreoOtroResponsable").val())){
		$("#txtCorreoOtroResponsable").addClass("campoTextoError");
		isError = true;
		erroresOtrosResponsables.push("Se debe ingresar un correo");

	}
	if(EstaVacio($("#txtDireccionOtroResponsable").val())){
		$("#txtDireccionOtroResponsable").addClass("campoTextoError");
		isError = true;
		erroresOtrosResponsables.push("Se debe ingresar una direcci\u00f3n");

	}

	if(isError){
		var objectErrores = new Object();
		objectErrores.erroresOtrosResponsables = erroresOtrosResponsables;
		mostrarMensajesOtrosResponsables(objectErrores);
		return false;
	}else{
		return true;
	}

}

function validarFormularioOtrosResponsablesDetalleCaso(){
	ocultarMensajesOtrosResponsablesDetalleCaso();
	limpiarErroresOtrosResponsablesDetalleCaso();
	var erroresOtrosResponsables = [];
	var isError = false;

	if(EstaVacio($("#txtNombresOtroResponsableDetalleCaso").val())){
		$("#txtNombresOtroResponsableDetalleCaso").addClass("campoTextoError");
		isError = true;
		erroresOtrosResponsables.push("Se debe ingresar un nombre.");
	}
	if(EstaVacio($("#txtApellidosOtroResponsableDetalleCaso").val())){
		$("#txtApellidosOtroResponsableDetalleCaso").addClass("campoTextoError");
		isError = true;
		erroresOtrosResponsables.push("Se debe ingresar un apellido");

	}
	if(EstaVacio($("#txtTelefonoOtroResponsableDetalleCaso").val())){
		$("#txtTelefonoOtroResponsableDetalleCaso").addClass("campoTextoError");
		isError = true;
		erroresOtrosResponsables.push("Se debe ingresar un telefono");

	}
	if(EstaVacio($("#txtCorreoOtroResponsableDetalleCaso").val())){
		$("#txtCorreoOtroResponsable").addClass("campoTextoError");
		isError = true;
		erroresOtrosResponsables.push("Se debe ingresar un correo");

	}
	if(EstaVacio($("#txtDireccionOtroResponsableDetalleCaso").val())){
		$("#txtDireccionOtroResponsableDetalleCaso").addClass("campoTextoError");
		isError = true;
		erroresOtrosResponsables.push("Se debe ingresar una direcci\u00f3n");

	}

	if(isError){
		var objectErrores = new Object();
		objectErrores.erroresOtrosResponsables = erroresOtrosResponsables;
		mostrarMensajesOtrosResponsables(objectErrores);
		return false;
	}else{
		return true;
	}

}

function mostrarMensajesOtrosResponsables(objectErrores){
	var mensajeError = "";
	if(objectErrores.erroresOtrosResponsables && objectErrores.erroresOtrosResponsables.length){
		$.each(objectErrores.erroresOtrosResponsables, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#messageErroresOtrosResponsables").html(mensajeError);
		$("#messageErroresOtrosResponsables").show();
		mensajeError = "";
	}
}

function ocultarMensajesOtrosResponsables(){
	$("#messageErroresOtrosResponsables").hide();
	$("#messageErroresOtrosResponsables").html("");
}

function ocultarMensajesOtrosResponsablesDetalleCaso(){
	$("#messageErroresOtrosResponsablesDetalleCaso").hide();
	$("#messageErroresOtrosResponsablesDetalleCaso").html("");
}

function limpiarErroresOtrosResponsables(){
	$("#txtNombresOtroResponsable").removeClass("campoTextoError");
	$("#txtApellidosOtroResponsable").removeClass("campoTextoError");
	$("#txtTelefonoOtroResponsable").removeClass("campoTextoError");
	$("#txtCorreoOtroResponsable").removeClass("campoTextoError");
	$("#txtDireccionOtroResponsable").removeClass("campoTextoError");
}

function limpiarErroresOtrosResponsablesDetalleCaso(){
	$("#txtNombresOtroResponsableDetalleCaso").removeClass("campoTextoError");
	$("#txtApellidosOtroResponsableDetalleCaso").removeClass("campoTextoError");
	$("#txtTelefonoOtroResponsableDetalleCaso").removeClass("campoTextoError");
	$("#txtCorreoOtroResponsableDetalleCaso").removeClass("campoTextoError");
	$("#txtDireccionOtroResponsableDetalleCaso").removeClass("campoTextoError");
}

var cantidadResponsables = 0;
var codigosResponsablesNuevoCaso = [];
function guardarOtrosResponsables() {
	var responsable = new Object();
	responsable.nombre = $("#txtNombresOtroResponsable").val();
	responsable.apellidos = $("#txtApellidosOtroResponsable").val();
	responsable.telefonos = $("#txtTelefonoOtroResponsable").val();
	responsable.email = $("#txtCorreoOtroResponsable").val();
	responsable.direccion = $("#txtDireccionOtroResponsable").val();
	responsable.identificacion = cantidadResponsables;
	responsable.isResponsableTarea = true;
	responsables.push(responsable);
	var option = document.createElement("option");
	option.value = cantidadResponsables;
	option.text = responsable.nombre + " " + responsable.apellidos;
	codigosResponsablesNuevoCaso = $(opcionOtroResponsable).closest("select[name^='responsable']").val();
	$(codigosResponsablesNuevoCaso).each(function(ind, valor){
		if(valor == -1){
			codigosResponsablesNuevoCaso.splice(ind, 1);
		}
	});
	$(opcionOtroResponsable).closest("select[name^='responsable']").append(option);
	codigosResponsablesNuevoCaso.push($(option).val());
	$(opcionOtroResponsable).closest("select[name^='responsable']").select2("val", codigosResponsablesNuevoCaso);

	cantidadResponsables++;
	$("#modal-ingresarOtroResponsable").modal("hide");
	$("#ingresarOtroResponsable").html(formularioOtroResponsable);
	formularioOtroResponsable = $("#ingresarOtroResponsable")[0].cloneNode(true);

}

var cantidadResponsablesDetalleCaso = 0;
var codigosResponsablesNuevoCasoDetalleCaso = [];
function guardarOtrosResponsablesDetalleCaso(){
	var responsable = new Object();
	responsable.nombre = $("#txtNombresOtroResponsableDetalleCaso").val();
	responsable.apellidos = $("#txtApellidosOtroResponsableDetalleCaso").val();
	responsable.telefonos = $("#txtTelefonoOtroResponsableDetalleCaso").val();
	responsable.email = $("#txtCorreoOtroResponsableDetalleCaso").val();
	responsable.direccion = $("#txtDireccionOtroResponsableDetalleCaso").val();
	responsable.identificacion = cantidadResponsablesDetalleCaso;
	responsable.isResponsableTarea = true;
	responsablesDetalleCaso.push(responsable);
	var option = document.createElement("option");
	option.value = cantidadResponsablesDetalleCaso;
	option.text = responsable.nombre + " " + responsable.apellidos;
	codigosResponsablesNuevoCasoDetalleCaso = $(optionOtros).closest("select[name^='responsable']").val();
	$(codigosResponsablesNuevoCasoDetalleCaso).each(function(ind, valor){
		if(valor == -1){
			codigosResponsablesNuevoCasoDetalleCaso.splice(ind, 1);
		}
	});
	$(optionOtros).closest("select[name^='responsable']").append(option);
	codigosResponsablesNuevoCasoDetalleCaso.push($(option).val());
	$(optionOtros).closest("select[name^='responsable']").select2("val", codigosResponsablesNuevoCasoDetalleCaso);

	cantidadResponsables++;
	$("#modal-ingresarOtroResponsableDetalleCaso").modal("hide");
	$("#ingresarOtroResponsableDetalleCaso").html(formularioOtroResponsableDetalleCaso);
	formularioOtroResponsableDetalleCaso = $("#ingresarOtroResponsableDetalleCaso")[0].cloneNode(true);

}

function limpiarFormularioOtroResponsable(){
	$("#modal-ingresarOtroResponsable").modal("hide");
	$("#ingresarOtroResponsable").html(formularioOtroResponsable);
	formularioOtroResponsable = $("#ingresarOtroResponsable")[0].cloneNode(true);
	codigosResponsablesNuevoCaso = $(opcionOtroResponsable).closest("select[name^='responsable']").val();
	$(codigosResponsablesNuevoCaso).each(function(ind, valor){
		if(valor == -1){
			codigosResponsablesNuevoCaso.splice(ind, 1);
		}
	});
	$(opcionOtroResponsable).closest("select[name^='responsable']").select2("val", codigosResponsablesNuevoCaso);
}

function limpiarFormularioOtroResponsableDetalleCaso(){
	$("#modal-ingresarOtroResponsableDetalleCaso").modal("hide");
	$("#ingresarOtroResponsableDetalleCaso").html(formularioOtroResponsableDetalleCaso);
	formularioOtroResponsableDetalleCaso = $("#ingresarOtroResponsableDetalleCaso")[0].cloneNode(true);
	codigosResponsablesNuevoCasoDetalleCaso = $(optionOtros).closest("select[name^='responsable']").val();
	$(codigosResponsablesNuevoCasoDetalleCaso).each(function(ind, valor){
		if(valor == -1){
			codigosResponsablesNuevoCaso.splice(ind, 1);
		}
	});
	$(optionOtros).closest("select[name^='responsable']").select2("val", codigosResponsablesNuevoCasoDetalleCaso);
}

function guardarOpcionOtrosResponsables(){

	if(validarFormularioOtrosResponsables()){
		guardarOtrosResponsables();
	}

}

function guardarOpcionOtrosResponsablesDetalleCaso(){

	if(validarFormularioOtrosResponsablesDetalleCaso()){
		guardarOtrosResponsablesDetalleCaso();
	}

}

function asignarAlertas(event){
	var configuracion = event.data.param;
	var index = event.data.param2;
	if($("#confNumeroDias").val() || $("#confNumeroAlertas").val()){
		if(configuracion == CONF_ALL_ALARM){
			$("#tableAlertas table tbody tr").each(function(index, tr){
				$(tr).find("td:eq(1)").text($("#confNumeroDias").val());
				$(tr).find("td:eq(2)").text($("#confNumeroAlertas").val());
			});
		}else{
			var tr = $("#tableAlertas table tbody tr:eq(" + index + ")");
			tr.find("td:eq(1)").text($("#confNumeroDias").val());
			tr.find("td:eq(2)").text($("#confNumeroAlertas").val());
		}
	}else{
		$("#errorAlertas").html("Por favor realice una configuraci\u00F3n para las tareas").removeClass("hide");
		setTimeout(function(){
			$("#errorAlertas").html("").addClass("hide");
		}, 10000);
	}
}

function modificarAlertas(button) {
	var areaActividad = $(button).closest("[name = actividadParticular]");
	var tareas = $(button).closest("[name = actividadParticular]").find(".table-actividades tbody tr");
	areaAlarmas = tareas;
	if (tareas.length) {
		$("#tableAlertas").html("");
		var tareasValidas = true;
		var thTarea = $("<th></th>").text(TITLE_TAREAS);
		var thDias = $("<th></th>").text(TITLE_NUMERO_DIAS);
		var thNotificaciones = $("<th></th>").text(TITLE_NUMERO_NOTIFICACIONES);
		var thAccion = $("<th></th>").append($("<a></a>").addClass("btn btn-circle btn-info").click({
			param : CONF_ALL_ALARM
		}, asignarAlertas).append($("<i></i>").addClass("glyphicon glyphicon-ok")));
		var firstTr = $("<tr></tr>").append(thTarea, thDias, thNotificaciones, thAccion);
		var theader = $("<thead></thead>").append(firstTr);
		var table = $("<table></table>").addClass("table table-bordered").append(theader);
		var tbody = $("<tbody></tbody>")
		$.each(tareas, function(index, optionData){
			var tr = $("<tr></tr>");
			if(!$(optionData).find("[name *= nombreTarea]").val().trim()){
				tareasValidas = false;
			}
			tr.append($("<td></td>").text($(optionData).find("[name *= nombreTarea]").val()));
			tr.append($("<td></td>").text($(optionData).find("[name *= numeroDeDias]").val()));
			tr.append($("<td></td>").text($(optionData).find("[name *= numeroDeAlertas]").val()));
			tr.append($("<td></td>").append($("<a></a>").addClass("btn btn-circle btn-info").click({
				param : CONF_ONE_ALARM,
				param2 : index
			}, asignarAlertas).append($("<i></i>").addClass("glyphicon glyphicon-ok"))));
			tbody.append(tr);
		});
		table.append(tbody);
		if(tareasValidas){
			$("#modalConfAlarmas").modal("show");
			$("#tableAlertas").append(table);
		}else{
			areaActividad.find("[name = actividadError]").removeClass("hide").html(
					"Por favor antes de continuar ingrese los nombres de todas las tareas");
			setTimeout(function(){
				areaActividad.find("[name = actividadError]").addClass("hide").html("");
			}, 10000);
		}
	}else{
		areaActividad.find("[name = actividadError]").removeClass("hide").html("No hay tareas para configurar");
		setTimeout(function(){
			areaActividad.find("[name = actividadError]").addClass("hide").html("");
		}, 10000);
	}
}

function guardarAlertas(){
	$.each($("#tableAlertas table tbody tr"), function(index, trAlerta){
		$(areaAlarmas[index]).find("[name *= numeroDeDias]").val($(trAlerta).find("td:eq(1)").text());
		$(areaAlarmas[index]).find("[name *= numeroDeAlertas]").val($(trAlerta).find("td:eq(2)").text());
	});
	$("#modalConfAlarmas").modal("hide");
	$("#confNumeroDias").val("");
	$("#confNumeroAlertas").val("");
}

function validarEstadoTareas(select){
	if($(select).val() == actividadCompletada){
		var tareas = $(select).closest("[name = actividadParticular]").find("[name *= cmbEstadoTareas]");
		var hasTareasPendientes = false;
		$.each(tareas, function(index, optionData){
			if($(optionData).val() == actividadPendiente) 
				hasTareasPendientes = true;			
		});
		if(hasTareasPendientes){
			$("#modalConfEstadoActividad").modal("show");
		}
	}		
}

$( "#cmbEstadoActividad" ).load(function(select){
	if($(select).val() == actividadVencida){
		var tareas = $(select).closest("[name = actividadParticular]").find("[name *= cmbEstadoTareas]");		
		$.each(tareas, function(index, optionData){
			if($(optionData).val() == actividadPendiente) 
				$(tareas).val(actividadVencida);		
		});		
	}
});

function obtenerEstadoActual(select){
	estadoActividadAnterior = $(select).val();
	selectEstadoActividad = $(select);
}

function finalizarActividad(){
	var tareas = selectEstadoActividad.closest("[name = actividadParticular]").find("[name *= cmbEstadoTareas]");
	$.each(tareas, function(index, optionData){
		$(tareas).val(actividadCompletada);
	});
	$("#modalConfEstadoActividad").modal("hide");
}

function cancelarCambioEstadoActividad(){
	$("#modalConfEstadoActividad").modal("hide");
	selectEstadoActividad.val(estadoActividadAnterior);
}

function validarNuevoMiembro(){
	/**
	 * Formulario demandados
	 */
	var areaDemandados = $("#collapseDemandadosNuevos");
	areaDemandados.removeClass("in").prop("aria-expanded", false).css("height", "0");
	var isError = false;
	var container = $("#modal-adicionarMiembroEquipo .modal-content")
	var formularioError = false;
	var erroresDemandados = [];
	var erroresVictimasDemandantes = [];

	
	
	
	if($("#modal-adicionarMiembroEquipo [name = formularioEquipoCasoNuevoMiembroModal]").not(".hide").length
			|| $("#modal-adicionarMiembroEquipo [name =abogados]").not(".hide").length
			|| $("#modal-adicionarMiembroEquipo [name = formularioEquipoCasoNuevoMiembro]").not(".hide").length){
		// valida el tipo de persona
		$("#modal-adicionarMiembroEquipo select[name=txtTipoPersonaNuevoMiembro]:gt(0)").each(function(){
			if(EstaVacio($(this).val())){
				$(this).addClass("campoTextoError");
				isError = true;
				erroresDemandados.push("El campo tipo de persona es obligatorio.");
				if(!formularioError){
					formularioError = true
					container.scrollTop(areaDemandados.offset().top);
					areaDemandados.addClass("in").prop("aria-expanded", true).css("height", "");
				}
			}else{
				$(this).removeClass("campoTextoError");
			}
		});
		// valida el nombre del miembro del equipo
		$("#modal-adicionarMiembroEquipo input[name=txtNombresMiembroNuevoMiembro]:gt(0)").each(function(){
			if(EstaVacio($(this).val())){
				$(this).addClass("campoTextoError");
				isError = true;
				erroresDemandados.push("El campo nombres es obligatorio.");
				if(!formularioError){
					formularioError = true
					container.scrollTop(areaDemandados.offset().top);
					areaDemandados.addClass("in").prop("aria-expanded", true).css("height", "");
				}
			}else{
				$(this).removeClass("campoTextoError");
			}
		});

		/**
		 * Formulario Equipo Caso
		 */
		var areaEquipoCaso = $("#collapseSolicitudNuevos");
		areaEquipoCaso.removeClass("in").prop("aria-expanded", false).css("height", "0");

		
		
		
		// Valida los tipos de miembros
		$("#modal-adicionarMiembroEquipo select[name=txtTipoDeMiembroCasoNuevoMiembro]:gt(0)").each(function(){
			if(EstaVacio($(this).val())){
				$(this).addClass("campoTextoError");
				isError = true;
				erroresVictimasDemandantes.push("El campo tipo de miembro es obligatorio.");
				if(!formularioError){
					formularioError = true
					container.scrollTop(areaEquipoCaso.offset().top);
					areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
				}
			}else{
				$(this).removeClass("campoTextoError");
			}
		});
		$("#modal-adicionarMiembroEquipo input[name=txtNombresMiembroCaso]:gt(0)").each(function(){
			if(EstaVacio($(this).val())){
				$(this).addClass("campoTextoError");
				isError = true;
				erroresVictimasDemandantes.push("El campo nombres es obligatorio.");
				if(!formularioError){
					formularioError = true
					container.scrollTop(areaEquipoCaso.offset().top);
					areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
				}
			}else{
				$(this).removeClass("campoTextoError");
			}
		});
		// Valida el apellido del miembro del equipo
		$("#modal-adicionarMiembroEquipo input[name=txtApellidosMiembroCaso]:gt(0)").each(function(){
			if(EstaVacio($(this).val())){
				$(this).addClass("campoTextoError");
				isError = true;
				erroresVictimasDemandantes.push("El campo apellidos es obligatorio.");
				if(!formularioError){
					formularioError = true
					container.scrollTop(areaEquipoCaso.offset().top);
					areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
				}
			}else{
				$(this).removeClass("campoTextoError");
			}
		});
		// valida los telefonos
		$("#modal-adicionarMiembroEquipo input[name=txtTelefonoMiembroCaso]:gt(0)").each(function(){
			if(EstaVacio($(this).val())){
				$(this).addClass("campoTextoError");
				isError = true;
				erroresVictimasDemandantes.push("El campo Tel\u00E9fono es obligatorio.");
				if(!formularioError){
					formularioError = true
					container.scrollTop(areaEquipoCaso.offset().top);
					areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
				}
			}else{
				$(this).removeClass("campoTextoError");
			}
		});
		// valida los correos
		$("#modal-adicionarMiembroEquipo input[name=txtCorreoMiembroCaso]:gt(0)").each(function(){
			if(EstaVacio($(this).val())){
				$(this).addClass("campoTextoError");
				isError = true;
				erroresVictimasDemandantes.push("El campo correo es obligatorio.");
				if(!formularioError){
					formularioError = true
					container.scrollTop(areaEquipoCaso.offset().top);
					areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
				}
			}else{
				$(this).removeClass("campoTextoError");
			}
		});
		
		var tipoMiembro = $("#modal-adicionarMiembroEquipo [name = formularioEquipoCasoNuevoMiembroModal]").not(".hide").find("#txtTipoDeMiembroCasoNuevoMiembro");
		var observaciones = $("#modal-adicionarMiembroEquipo [name = formularioEquipoCasoNuevoMiembroModal]").not(".hide").find("#txtObservacionesNuevoMiembro");
		if(tipoMiembro.val() == 7 && observaciones.val()==""){
			observaciones.addClass("campoTextoError");
			isError = true;
			erroresVictimasDemandantes.push("El campo observaciones es obligatorio.");
			if(!formularioError){
				formularioError = true
				container.scrollTop(areaEquipoCaso.offset().top);
				areaEquipoCaso.addClass("in").prop("aria-expanded", true).css("height", "");
			}
		
		}else{
			observaciones.removeClass("campoTextoError");
		}

		if(isError){
			var objectErrores = new Object();
			objectErrores.erroresDemandados = erroresDemandados;
			objectErrores.erroresVictimasDemandantes = erroresVictimasDemandantes;
			mostrarErrroresNuevoMiembro(objectErrores);
			return false;
		}else{
			return true;
		}
	}else{
		$("#messageErrorAdicionarMiembro").removeClass("hide").html("No existen datos de nuevo miembro para guardar");
		container.scrollTop(0);
		setTimeout(function(){
			$("#messageErrorAdicionarMiembro").addClass("hide").html("");
		}, 10000);
	}
}

function limpiarFormularioNuevosMiembros(){
	$("#modal-adicionarMiembroEquipo").html(formularioNuevosMiembros);
	formularioNuevosMiembros = $("#modal-adicionarMiembroEquipo")[0].cloneNode(true);
	$("#modal-adicionarMiembroEquipo").modal("hide");
}

function mostrarErrroresNuevoMiembro(objectErrores){
	var mensajeError = "";
	if(objectErrores.erroresDemandados.length){
		$.each(objectErrores.erroresDemandados, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#errorAgregarDemandado").html(mensajeError);
		$("#errorAgregarDemandado").removeClass("hide");
		mensajeError = "";
	}else{
		$("#errorAgregarDemandado").html("");
		$("#errorAgregarDemandado").addClass("hide");
	}
	if(objectErrores.erroresVictimasDemandantes.length){
		$.each(objectErrores.erroresVictimasDemandantes, function(index, optionData){
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#errorAgregarEquipoCaso").html(mensajeError);
		$("#errorAgregarEquipoCaso").removeClass("hide");
		mensajeError = "";
	}else{
		$("#errorAgregarEquipoCaso").html("");
		$("#errorAgregarEquipoCaso").addClass("hide");
	}
}

function guardarNuevoMiembro(){

	if (validarNuevoMiembro()) {
		var data_caso = "_csrf=" + $("#_csrf").val();

		/**
		 * Recorrido y serializacion del formulario de nuevo demandado.
		 */
		
		
		
		var formulariosMiembrosEquipo = $("#modal-adicionarMiembroEquipo [name = formularioEquipoCasoNuevoMiembro]")
				.not(".hide");
		var direccion = $(formulariosMiembrosEquipo).find("[name *= 'txtDireccionMiembro']").not(".hide");
		
		var tipoMiembro = formulariosMiembrosEquipo.find("#txtTipoDeMiembroCasoNuevoMiembro");
		var observaciones = formulariosMiembrosEquipo.find("#txtObservacionesNuevoMiembro"); 
		
		var departamentoMiembroEquipo = $(formulariosMiembrosEquipo).find("[name *= 'TxtDepartamentoMiembro']").not(
				".hide");
		var ciudadMiembroEquipo = $(formulariosMiembrosEquipo).find("[name *= 'txtMunicipioMiembro']").not(".hide");
		var tipoDocumentoMiembroEquipo = $(formulariosMiembrosEquipo).find("[name *= 'txtTipoDocumento']").not(".hide");
		var contadorMiembros = 0;
		for (var i = 0; i < formulariosMiembrosEquipo.length; i++){
			var formularioEquipoCaso = formulariosMiembrosEquipo[i];
			data_caso += "&casoEquipoCasoSet[" + i + "].direccion=" + direccion[i].value;
			data_caso += "&casoEquipoCasoSet[" + i + "].casoEquipoCasoPK.codigo=" + $("#codigoParam").val();
			if(ciudadMiembroEquipo[i].value != "" && ciudadMiembroEquipo[i].value != null){

				data_caso += "&casoEquipoCasoSet[" + i + "].ciudadEquipoCaso.ciudadPK.codigoCiudad="
						+ ciudadMiembroEquipo[i].value;
				data_caso += "&casoEquipoCasoSet[" + i + "].ciudadEquipoCaso.ciudadPK.codigoDepartamento="
						+ departamentoMiembroEquipo[i].value;
			}

			// --------se esta modificando
			if($(formularioEquipoCaso).find("select[name*='txtTipoPersona']").length != 0){
				if($(formularioEquipoCaso).find("select[name*='txtTipoPersona']").val() == "Persona Juridica"
						&& $(formularioEquipoCaso).find("select[name*='txtTipoPersona']").val() != ""){

					data_caso += "&casoEquipoCasoSet[" + i + "].personajuridica=" + JURIDICA_SI;
					data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.nombre="
							+ $(formularioEquipoCaso).find("input[name*='txtNombresMiembro']").val();
				}else{
					data_caso += "&casoEquipoCasoSet[" + i + "].personajuridica=" + JURIDICA_SI;
					data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.nombre="
							+ $(formularioEquipoCaso).find("input[name*='txtNombresMiembro']").val();
				}
				data_caso += "&casoEquipoCasoSet[" + i + "].casoEquipoCasoPK.miembro=" + 3;
				data_caso += "&casoEquipoCasoSet[" + i + "].activo=" + "S";
				data_caso += "&casoEquipoCasoSet[" + i + "].contacto=" + CONTACTO_NO;
					data_caso += "&casoEquipoCasoSet[" + i + "].observacion="
					+ $(formularioEquipoCaso).find("[name*='txtObservacionesMiembro']").val();	
				
				data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.identificacion="
				+ $(formularioEquipoCaso).find("input[name*='txtIdentificacionMiembro']").val();
			}else{
				data_caso += "&casoEquipoCasoSet[" + i + "].personajuridica=" + "N";
				data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.nombre="
						+ $(formularioEquipoCaso).find("input[name*='txtNombresMiembro']").val();
				data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.apellido="
						+ $(formularioEquipoCaso).find("input[name*='txtApellidosMiembro']").val();
				data_caso += "&casoEquipoCasoSet[" + i + "].casoEquipoCasoPK.miembro="
						+ $(formularioEquipoCaso).find("select[name*='txtTipoDeMiembro']").val();
				data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.identificacion="
						+ $(formularioEquipoCaso).find("input[name*='txtIdentificacionMiembro']").val();
				if($(formularioEquipoCaso).find("select[name=txtParentescoMiembro]").val() != ""){
					data_caso += "&casoEquipoCasoSet[" + i + "].parentesco.codigo="
							+ $(formularioEquipoCaso).find("select[name*='txtParentescoMiembro']").val();

				}
				
				
				
				data_caso += "&casoEquipoCasoSet[" + i + "].activo=" + "S";
				if($(formularioEquipoCaso).find("input[name*='esContacto']:checked").length > 0)
					data_caso += "&casoEquipoCasoSet[" + i + "].contacto=" + CONTACTO_SI;
				else
					data_caso += "&casoEquipoCasoSet[" + i + "].contacto=" + CONTACTO_NO;
			}
			
			
			
			if($(formularioEquipoCaso).find("select[name*='txtTipoDocumento']").val() != ""){
				data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.tipoDocumento.codigo="
						+ $(formularioEquipoCaso).find("select[name*='txtTipoDocumento']").val();
			}
			
			if($(formularioEquipoCaso).find("select[name*='txtTipoPersona']").val() != "Persona Juridica" && 
			   $(formularioEquipoCaso).find("select[name*='txtTipoPersona']").val() != "Persona Natural"){
		
				if($(formularioEquipoCaso).find("input[name*='nuevoMiembroNacimiento']").val() != ""){
					fecha = $(formularioEquipoCaso).find("input[name*='nuevoMiembroNacimiento']").val().split("-");
					data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.fechaNacimiento=" + new Date(fecha[0], fecha[1] - 1, fecha[2]);
				}
			}

			var j = 0;
			$(formularioEquipoCaso).find("input[name^='txtTelefonoMiembro']").each(
					function(){
						if(!EstaVacio($(this).val())){
							data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.telefonoList[" + j + "].numero="
									+ $(this).val();
							j++;
						}
					});
			j = 0;
			$(formularioEquipoCaso).find("input[name^='txtCorreoMiembro']").each(
					function(){
						if(!EstaVacio($(this).val())){
							data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.correoList[" + j + "].correo="
									+ $(this).val();
							j++;
						}
					});
			j = 0;
			$(formularioEquipoCaso).find("input[name*='txtCelularMiembro']").each(
					function(){
						if(!EstaVacio($(this).val())){
							data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.celularList[" + j + "].numero="
									+ $(this).val();
							j++;
						}
					});
			j = 0;
			$(formularioEquipoCaso).find("input[name*='txtTipoDocumento']").each(
					function(){
						if(!EstaVacio($(this).val())){
							data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.tipoDocumento[" + j + "].codigo="
									+ $(this).val();
							j++;
						}
					});
			
			$(formularioEquipoCaso).find("input[name*='nuevoMiembroNacimiento']").each(
					function(){
						if(!EstaVacio($(this).val())){
							data_caso += "&casoEquipoCasoSet[" + i + "].equipoCaso.fechaNacimiento[" + j + "]="
							 + new Date(fecha[0], fecha[1] - 1, fecha[2]);
							j++;
						}
					});
			contadorMiembros++;
		}

		var formulariosMiembrosEquipo = $(
				"#modal-adicionarMiembroEquipo [name = formularioEquipoCasoNuevoMiembroModal]").not(".hide");
		
		
		
		var direccion = $(formulariosMiembrosEquipo).find("[name *= 'txtDireccionMiembro']").not(".hide");
		var departamentoMiembroEquipo = $(formulariosMiembrosEquipo).find("[name *= 'TxtDepartamentoMiembro']").not(
				".hide");
		
		var tipoMiembro = formulariosMiembrosEquipo.find("#txtTipoDeMiembroCasoNuevoMiembro");
		var observaciones = formulariosMiembrosEquipo.find("#txtObservacionesNuevoMiembro"); 
		
		var ciudadMiembroEquipo = $(formulariosMiembrosEquipo).find("[name *= 'txtMunicipioMiembro']").not(".hide");
		for (var i = 0; i < formulariosMiembrosEquipo.length; i++){
			var formularioEquipoCaso = formulariosMiembrosEquipo[i];
			data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].direccion=" + direccion[i].value;
			data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].casoEquipoCasoPK.codigo="
					+ $("#codigoParam").val();
			
			if(tipoMiembro.val()==7){
				data_caso += "&casoEquipoCasoSet[" + i + "].observacion="+observaciones.val();
			}
			
			if(ciudadMiembroEquipo[i].value != "" && ciudadMiembroEquipo[i].value != null){

				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i)
						+ "].ciudadEquipoCaso.ciudadPK.codigoCiudad=" + ciudadMiembroEquipo[i].value;
				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i)
						+ "].ciudadEquipoCaso.ciudadPK.codigoDepartamento=" + departamentoMiembroEquipo[i].value;
			}

			// --------se esta modificando
			if($(formularioEquipoCaso).find("select[name*='txtTipoPersona']").length != 0){
				if($(formularioEquipoCaso).find("select[name*='txtTipoPersona']").val() == "Persona Juridica"
						&& $(formularioEquipoCaso).find("select[name*='txtTipoPersona']").val() != ""){

					data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].personajuridica=" + JURIDICA_SI;
					data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].equipoCaso.nombre="
							+ $(formularioEquipoCaso).find("input[name*='txtNombresMiembro']").val();
				}else{
					data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].personajuridica=" + JURIDICA_SI;
					data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].equipoCaso.nombre="
							+ $(formularioEquipoCaso).find("input[name*='txtNombresMiembro']").val();
				}
				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].casoEquipoCasoPK.miembro=" + 3;
				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].activo=" + "S";
				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].contacto=" + CONTACTO_NO;
				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].observacion="
						+ $(formularioEquipoCaso).find("[name*='txtObservacionesMiembro']").val();
			}else{
				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].personajuridica=" + "N";
				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].equipoCaso.nombre="
						+ $(formularioEquipoCaso).find("input[name*='txtNombresMiembro']").val();
				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].equipoCaso.apellido="
						+ $(formularioEquipoCaso).find("input[name*='txtApellidosMiembro']").val();
				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].casoEquipoCasoPK.miembro="
						+ $(formularioEquipoCaso).find("select[name*='txtTipoDeMiembro']").val();
				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].equipoCaso.identificacion="
						+ $(formularioEquipoCaso).find("input[name*='txtIdentificacionMiembro']").val();
				if($(formularioEquipoCaso).find("select[name*='txtParentescoMiembro']").val() != ""){
					data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].parentesco.codigo="
							+ $(formularioEquipoCaso).find("select[name*='txtParentescoMiembro']").val();

				}
												
				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].activo=" + "S";
				if($(formularioEquipoCaso).find("input[name*='esContacto']:checked").length > 0){
					data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].contacto=" + CONTACTO_SI;
				}else{
					data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].contacto=" + CONTACTO_NO;
				}
			}
			
			if($(formularioEquipoCaso).find("select[name*='txtTipoDocumento']").val() != ""){
				data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].equipoCaso.tipoDocumento.codigo="
						+ $(formularioEquipoCaso).find("select[name*='txtTipoDocumento']").val();
			}

			var j = 0;
			$(formularioEquipoCaso).find("input[name^='txtTelefonoMiembro']").each(
					function(){
						if(!EstaVacio($(this).val())){
							data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].equipoCaso.telefonoList["
									+ j + "].numero=" + $(this).val();
							j++;
						}
					});
			j = 0;
			$(formularioEquipoCaso).find("input[name^='txtCorreoMiembro']").each(
					function(){
						if(!EstaVacio($(this).val())){
							data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].equipoCaso.correoList["
									+ j + "].correo=" + $(this).val();
							j++;
						}
					});
			j = 0;
			$(formularioEquipoCaso).find("input[name*='txtCelularMiembro']").each(
					function(){
						if(!EstaVacio($(this).val())){
							data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].equipoCaso.celularList["
									+ j + "].numero=" + $(this).val();
							j++;
						}
					});
			j = 0;
			$(formularioEquipoCaso).find("input[name*='txtTipoDocumento']").each(
					function(){
						if(!EstaVacio($(this).val())){
							data_caso += "&casoEquipoCasoSet[" + (contadorMiembros + i) + "].equipoCaso.tipoDocumento["
									+ j + "].codigo=" + $(this).val();
							j++;
						}
					});
		}
		contadorMiembros = contadorMiembros + formulariosMiembrosEquipo.length;

		$("#modal-adicionarMiembroEquipo #cmbMiembroAbogadoNuevoMiembro option:disabled").each(
				function(ind1, dato1){
					data_caso += "&casoEquipoCasoSet[" + contadorMiembros + "].casoEquipoCasoPK.codigo="
							+ $("#codigoParam").val();
					data_caso += "&casoEquipoCasoSet[" + contadorMiembros + "].casoEquipoCasoPK.miembro=" + 5;
					data_caso += "&casoEquipoCasoSet[" + contadorMiembros + "].activo=" + "S";
					data_caso += "&casoEquipoCasoSet[" + contadorMiembros + "].personajuridica=" + "N";
					data_caso += "&casoEquipoCasoSet[" + contadorMiembros + "].contacto=" + CONTACTO_NO;
					data_caso += "&casoEquipoCasoSet[" + contadorMiembros + "].equipoCaso.identificacion="
							+ $("label[name*='identificacion']").eq(ind1).text();
					data_caso += "&casoEquipoCasoSet[" + contadorMiembros + "].equipoCaso.nombre="
							+ $("label[name*='nombreAbogado']").eq(ind1).text();
					data_caso += "&casoEquipoCasoSet[" + contadorMiembros + "].equipoCaso.apellido="
							+ $("label[name*='apellidoAbogado']").eq(ind1).text();
					data_caso += "&casoEquipoCasoSet[" + contadorMiembros + "].equipoCaso.correoList[" + 0
							+ "].correo=" + $("label[name*='correoAbogado']").eq(ind1).text();
					data_caso += "&casoEquipoCasoSet[" + contadorMiembros + "].equipoCaso.telefonoList[" + 0
							+ "].numero=" + $("label[name*='telefonoAbogado']").eq(ind1).text();
					data_caso += "&casoEquipoCasoSet[" + contadorMiembros + "].equipoCaso.celularList[" + 0
					+ "].numero=" + $("label[name*='celularAbogado']").eq(ind1).text();
					data_caso += "&casoEquipoCasoSet[" + contadorMiembros + "].direccion="
							+ $("label[name*='direccionAbogado']").eq(ind1).text();					
					contadorMiembros++;
				});

		waitingDialog.show('');
		$.ajax({
			type : "GET",
			url : contexto + "/caso/guardarNuevoMiembro",
			data : data_caso,
			dataType : "json",
			success : function(response){
				waitingDialog.hide();
				$("#modal-adicionarMiembroEquipo").modal("hide");
				crearUrlRedireccionarA("addMiembro");

			},
			error : function(error){
				waitingDialog.hide();
				$("#messageErrorAdicionarMiembro").removeClass("hide").html(
						"No fue posible guardar el nuevo miembro del equipo");
				container.scrollTop(0);
				setTimeout(function(){
					$("#messageErrorAdicionarMiembro").addClass("hide").html("");
				}, 10000);
			}
		});
	}

}


function validateCheckSinConteo(check){
	if(check.checked){
		$("#txtFinFechaDeLosHechos").attr("disabled","true");
		$("#txtFinFechaDeLosHechos").val("");
		$("#txtFechaDeCaducidad").val("");
	}else{
		$("#txtFinFechaDeLosHechos").removeAttr("disabled");
	}
}
function validateCheckSinConteoModificacion(check){
	if(check.checked){
		$("#finFechaDeLosHechosMod").attr("disabled","true");
		$("#finFechaDeLosHechosMod").val("");
		$("#fechaDeCaducidadMod").val("");
	}else{
		$("#finFechaDeLosHechosMod").removeAttr("disabled");
	}
	
}


function getAutocompleteRadicados(){
	var radicados;
	$.ajax({dataType: "json",
				url: contexto + "/detalleCaso/consultarRadicados",
				async : false,
				success: function (res) {
					document.autocompleteRadicados = res;
					radicados = res;
					
				},
				error: function(e){
					waitingDialog.hide();
				}
			
	});
	return radicados; 
}

var radicadosAcumulados = {
		table : $("#tableRadicadosAcumulados"),
		radicados : getAutocompleteRadicados(),
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
			$("input#txtAutoCompleteRadicados").last().autocomplete({
				source : radicadosAcumulados.radicados,
				minLength: 3
			});
		},
		getTable : function(context){
			return $(context).closest("[name=formularioRadicado]").find("#tableRadicadosAcumulados");
		}
};


function findAllDisabledDates(){
	$.getJSON( contexto+"/maestro/findAllCalendarioJudicial",function(data){
		$.each(data.fechas,function(index, strDate){
			var date = getDateFormat(strDate);
			disabledDates.push(date.getTime());
			
		});
		
	});
}


function getDateFormat(strDate){
	var dateSplit = strDate.split("-");
	return new Date(dateSplit[0],dateSplit[1]-1,dateSplit[2]);
}


function validarActividadConciliacionPrejudicial(){
	var isValido = false; 
	$.each(actividadesArray,function(index,data){
		if(data.nombreActividad == _nombreActividadPrej){
			isValido = true;;
		}
	});
	if(!isValido){
		$("#txtFechaPrejudicial").val("");
		mostrarErrorFechaSolicitudPrejudicial("No existe la actividad Audiencia de conciliación prejudicial");	
	}else{
		$("#txtFechaPrejudicial").removeClass("campoTextoError");
		$("#messageErrorNuevoCaso").html("");
		$("#messageErrorNuevoCaso").hide();	
	}
	return isValido;
}

function validarFechaPrejudicial(){
	if($("#txtFechaPrejudicial").val()==""){
		diasAdicionarCaducidad = 0;
		mostrarFechaCaducidad();
		return false;
	}else if(validarActividadConciliacionPrejudicial() && validarDiferenciaFechaSolicitudPrejAudiencia()){
		if($("#txtFechaDeCaducidad").val()==$("#txtFechaPrejudicial").val()){
			$("#modal-AdvertenciaDemandaMismaDiaAudiencia").modal("show");
		}
		return true;
	}
}

function validarDiferenciaFechaSolicitudPrejAudiencia(){
	var fechaActividad;
	var success = true;
	$("div#divEspacio [name*=actividadParticular]").each(function(index,data){
		if($(data).find("a[data-toggle=collapse]").text() == _nombreActividadPrej){
			if($(data).find("[name*=actividadVencimiento]").val()==""){
				mostrarErrorFechaSolicitudPrejudicial("Antes debe ingresar la fecha de caducidad de la actividad "+_nombreActividadPrej);
				$("#txtFechaPrejudicial").val("");
				success=false;
			}
			var fechaActividad = getDateFormat($(data).find("[name*=actividadVencimiento]").val());
			var fechaSolicitudPrej = getDateFormat($("#txtFechaPrejudicial").val());
			var diferenciaMilis = fechaActividad.getTime()-fechaSolicitudPrej.getTime();
			var dias = diferenciaMilis/(24*60*60*1000);
			if(dias>90)
				diasAdicionarCaducidad = 90;
			else if(dias>0)
				diasAdicionarCaducidad = dias;
			else if(dias<0){
				dias=0;
				mostrarErrorFechaSolicitudPrejudicial("La fecha de vencimiento de la actividad de "+_nombreActividadPrej+" es menor a la fecha de presentación de solicitud prejudicial")
				$("#txtFechaPrejudicial").val("");
				success = false;
			}
			mostrarFechaCaducidad();
	}
	});
	return success;
}

function mostrarErrorFechaSolicitudPrejudicial(mensajeError){
	$("#txtFechaPrejudicial").addClass("campoTextoError");
	$("#messageErrorNuevoCaso").html(mensajeError);
	$("#messageErrorNuevoCaso").show();	
	
};