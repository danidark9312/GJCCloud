
function cargarModalEscalamientoNotificacion(codigoTipoCaso, nombreTipoCaso){
	 $("#liTab1").addClass("active");
	 $("#liTab2").removeClass("active");
	 $("#tab-1").addClass("active");
	 $("#tab-2").removeClass("active");
	 limpiarInput();
	$("#codigoTipoCasoEscalamiento").val(codigoTipoCaso);
	$("#titleModalEscalamiento").text(" Escalamiento Notificación "+ (nombreTipoCaso));
	$('#modalEscalamientoNotificacion').modal("show");
	cargarConfiguracionInicialProximasAVencer();
}


function limpiarInput(){
	$('#diasAntes').val('');
	$('#estadoNotificacionAntes').val('');
	$("#notificarAAntes").val('').trigger("change"); 
	$("#notificarAAntes").trigger('chosen:updated');
	$('#alertasAntes').val('');
	$('#diasDespues').val('');
	$('#estadoNotificacionDespues').val('');
	$("#notificarADespues").val('').trigger("change"); 
	$("#notificarADespues").trigger('chosen:updated');
	$("#notificarADespues").trigger('chosen:updated');
	$('#alertasDespues').val('');
}

function capitalizeString(data){
	var cadena = data.toLowerCase().replace(/\b\w/g, function(l){
		 return l.toUpperCase()
	});
	return cadena;
}

function cargarComponentes() {
	$("#messageExitosoEscalamiento").hide();
	$("#messageErrorEscalamiento").hide();
	directorioActual("Administradores", "Escalamiento de Notificaciíon");
	session.tiempo(session.tiempoSesion);
	formRulesDiasAntes();
	formRulesDiasDespues();
	var config = {
		'.chosen-select' : {},
		'.chosen-select-deselect' : {
			allow_single_deselect : true
		},
		'.chosen-select-no-single' : {
			disable_search_threshold : 10
		},
		'.chosen-select-no-results' : {
			no_results_text : 'Oops, nothing found!'
		},
		'.chosen-select-width' : {
			width : "95%"
		}
	}
	$("#notificarAAntes").chosen({
		width : '100%'
	},
		{
			allow_single_deselect : true
		});
	$("#notificarADespues").chosen({
		width : '100%'
	},
		{
			allow_single_deselect : true
		});

}


function cargarCombox() {
	cargarRolesChosen($("#notificarAAntes"));
	cargarRolesChosen($("#notificarADespues"));
	
}



function formRulesDiasAntes(){
	$diasAntesForm = $("#diasAntesForm").validate({
		ignore: '',
		rules : {
			estadoNotificacionAntes : {
				required : true,
			},
			notificarAAntes : {
				required : true
			},
			alertasAntes : {
				required : true
			},
			diasAntes : {
				required : true
			}
		},
		messages : {
			estadoNotificacionAntes : {
				required : 'El Estado de Notificación es Obligatorio.',
			},
			notificarAAntes : {
				required : 'El campo  Notificar A es Obligatorio.',
			},
			alertasAntes : {
				required : 'El número de Alertas es obligatorio.'
			},
			diasAntes : {
				required : 'El número de Días es Obligatorio.'
			}
		},
		errorPlacement : function(error, element) {
			error.insertAfter(element.parent());
		}
	});
}


function formRulesDiasDespues(){
	$diasDespuesForm = $("#diasDespuesForm").validate({
		ignore: '',
		rules : {
			estadoNotificacionDespues : {
				required : true,
			},
			notificarADespues : {
				required : true
			},
			alertasDespues : {
				required : true
			},
			diasDespues : {
				required : true
			}
		},
		messages : {
			estadoNotificacionDespues : {
				required : 'El Estado de Notificación es Obligatorio.',
			},
			notificarADespues : {
				required : 'El campo  Notificar A es Obligatorio.',
			},
			alertasDespues : {
				required : 'El número de Alertas es obligatorio.'
			},
			diasDespues : {
				required : 'El número de Días es Obligatorio.'
			}
		},
		errorPlacement : function(error, element) {
			error.insertAfter(element.parent());
		}
	});
}

function guardarConfiguracionAntes(){
	if($("#diasAntesForm").valid()){
		var data = "_csrf=" + $("#_csrf").val();
		data=builSendDataAntes(data);
		callAjaxAntes(data);
	}
}

function builSendDataAntes(data){
	data += "&numeroAlertaDiaria="+ $("#alertasAntes").val();
	data +="&numeroDiasAntes="+ $("#diasAntes").val();
    data +="&estado="+$("#estadoNotificacionAntes").val();
    data +="&tipoCaso="+$("#codigoTipoCasoEscalamiento").val();
    $.each($('#notificarAAntes').val(), function(index, info){
    	data += "&alertasTareasRolDetalleCollection[" + index + "].rol.codigo="+ info;
    })
	return data;	
}

function callAjaxAntes(data){
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/maestroEscalamientoNotificacion/guardarConfiguracionAntes",
		data : data,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			if(response.STATUS ="SUCCESS"){
				$("#messageExitosoEscalamiento").html("Se guard\u00f3 la configuración exitosamente.");
				$("#messageExitosoEscalamiento").show();
				setTimeout("limpiarMensajeExito();",1000);
			}else{
				$("#messageErrorEscalamiento").html("No fue posible guardar la configuración.");
				$("#messageErrorEscalamiento").show();
				setTimeout("limpiarMensajeError();", 10000);
			}
			
		},
		error : function(error) {
			waitingDialog.hide();
			$("#messageErrorEscalamiento").html("No fue posible guardar la configuración.");
			$("#messageErrorEscalamiento").show();
			setTimeout("limpiarMensajeError();", 10000);
		}
	});
}

function guardarConfiguracionDespues(){
	if($("#diasDespuesForm").valid()){
		var data = "_csrf=" + $("#_csrf").val();
		data=builSendDataDespues(data);
		callAjaxDespues(data);
	}
}

function builSendDataDespues(data){
	data += "&numeroAlertasDiarias="+ $("#alertasDespues").val();
	data +="&numeroDiaDespues="+ $("#diasDespues").val();
    data +="&estado="+$("#estadoNotificacionDespues").val();
    data +="&tipoCaso="+$("#codigoTipoCasoEscalamiento").val();
    $.each($('#notificarADespues').val(), function(index, info){
    	data += "&alertasTareasDespuesRolDetalleList[" + index + "].rol.codigo="+ info;
    })
	return data;	
}

function callAjaxDespues(data){
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/maestroEscalamientoNotificacion/guardarConfiguracionDespues",
		data : data,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			if(response.STATUS ="SUCCESS"){
				$("#messageExitosoEscalamiento").html("Se guard\u00f3 la configuración exitosamente.");
				$("#messageExitosoEscalamiento").show();
				setTimeout("limpiarMensajeExito();",1000);
			}else{
				$("#messageErrorEscalamiento").html("No fue posible guardar la configuración.");
				$("#messageErrorEscalamiento").show();
				setTimeout("limpiarMensajeError();", 10000);;
			}
		},
		error : function(error) {
			waitingDialog.hide();
			$("#messageErrorEscalamiento").html("No fue posible guardar la configuración.");
			$("#messageErrorEscalamiento").show();
			setTimeout("limpiarMensajeError();", 10000);
		}
	});
}

function cargarConfiguracionInicialProximasAVencer(){
	$("#guardarProximas").show();
	$("#guardarDiasDespues").hide();
	var data = "_csrf=" + $("#_csrf").val();
	data +="&tipoCaso="+$("#codigoTipoCasoEscalamiento").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/maestroEscalamientoNotificacion/consultarConfigProximaVencer",
		data : data,
		dataType : "json",
		success : function(response){
			if(response.numeroAlertaDiaria){
				llenarCampos(response)
			}
			waitingDialog.hide();			
		},
		error : function(error) {
			waitingDialog.hide();
			$("#messageErrorEscalamiento").html("No fue posible consultar la configuración.");
			$("#messageErrorEscalamiento").show();
			setTimeout("limpiarMensajeError();", 10000);
		}
	});
}


function llenarCampos(response){
	$('#diasAntes').val(response.numeroDiasAntes);
	$('#estadoNotificacionAntes').val(response.estado);
	var array = new Array();
	$.each(response.roles, function (index, data){
		array.push(data.rolCodigo);
	})
	$("#notificarAAntes").val(array);
	$("#notificarAAntes").trigger('chosen:updated');
	$('#alertasAntes').val(response.numeroAlertaDiaria);
}



function cargarConfiguracionInicialVencencida(){
	$("#guardarProximas").hide();
	$("#guardarDiasDespues").show();
	var data = "_csrf=" + $("#_csrf").val();
	 data +="&tipoCaso="+$("#codigoTipoCasoEscalamiento").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/maestroEscalamientoNotificacion/consultarConfingVencida",
		data : data,
		dataType : "json",
		success : function(response){
			if(response.numeroAlertaDiaria){
				llenarCamposVencido(response)
			}
			waitingDialog.hide();		
		},
		error : function(error) {
			waitingDialog.hide();
			$("#messageErrorEscalamiento").html("No fue posible consultar la configuración.");
			$("#messageErrorEscalamiento").show();
			setTimeout("limpiarMensajeError();", 10000);
		}
	});
}

function llenarCamposVencido(response){
	$('#diasDespues').val(response.numeroDiasDespues);
	$('#estadoNotificacionDespues').val(response.estado);
	var array = new Array();
	$.each(response.roles, function (index, data){
		array.push(data.rolCodigo);
	})
	$("#notificarADespues").val(array);
	$("#notificarADespues").trigger('chosen:updated');
	
	$('#alertasDespues').val(response.numeroAlertaDiaria);
}



function limpiarMensajeError(){
	$("#messageErrorEscalamiento").hide();
}

function limpiarMensajeExito(){
	$("#messageExitosoEscalamiento").hide();

}




