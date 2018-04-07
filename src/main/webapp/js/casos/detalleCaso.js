var codigoPais = null;
var codigoDepartamento = null;
var codigoCiudad = null;
var codigoPaisProceso = null;
var codigoDepartamentoProceso = null;
var codigoCiudadProceso = null;
var casoEquipoCasoPK = new Object();
var tipoMiembro = null;
var VICTIMA = 1;
var DEMANDANTE = 2;
var DEMANDADO = 3;
var OTRO = 4;
var ABOGADO = 5;
var justificacionEliminacion = null;
var CONTACTO_SI = "S"; 
var OPCION_NUEVO_CONTACTO = "Nuevo";
var arrayTiposMiembrosContactos = [];
var codigoRadicado = null;
var codigoBienAfectado = null;
var codigoTarea = null;
var codigoActividad = null;
var urlConsultaCliente = null;
var codigoTareaArchivo = null;
var codigoPrestamoArchivo = 0;
var codigoCasoPrestamoArchivo = 0;
var contextoDetalle = true;
var isConsultaFinalizada = false;
var radicadosTable;
var actualFechaCaducidad;

function consultarCaso (codigo) {
	isConsultaFinalizada = false;
	$(document).ajaxStop(function() {
		if (!isConsultaFinalizada) {
			isConsultaFinalizada = true;
			var documento =codigo;
			var data_caso ="codigo="+documento;
			data_caso +="&_csrf=" + $("#_csrf").val();
			waitingDialog.show('');$.ajax({
				type : "POST",
				dataType: "json",
				url: contexto + "/detalleCaso/consultarCaso",
				data: data_caso,
				success: function (res) {
					waitingDialog.hide();
					if(res.codigo != null) {
						$("#nombreCaso").text(res.nombre);
						$("#nombreCasoMod").val(res.nombre);//modal modificar caso
						var fechaHecho = new Date(res.fechaHecho);
						var fechaHechoFormato = formatoFecha(fechaHecho);
						var fechaHechoFormatoAnio = formatoFechaAnio(fechaHecho);
						if(res.fechaSolicitudPrejudicial){
							var fechaSolicitudPrejudicialFormat =  formatoFecha(new Date(res.fechaSolicitudPrejudicial));
							var fechaSolicitudPrejudicialFormatAnio= formatoFechaAnio(new Date(res.fechaSolicitudPrejudicial));
							$("#fechaPrejudicial").text(fechaSolicitudPrejudicialFormat);
							$("#txtFechaPrejudicialMod").val(fechaSolicitudPrejudicialFormatAnio);
						}
						var fechaHechoFormatoAnio = formatoFechaAnio(fechaHecho);
						$("#fechaHechos").text(fechaHechoFormato);
						$("#fechaDeLosHechosMod").val(fechaHechoFormatoAnio);
						if(!res.fechaFinHecho){
							$("#finFechaDeLosHechosMod").attr("disabled","true");
							$("#chkSinConteoMod")[0].checked = true;
						}else{
							$("#finFechaDeLosHechosMod").removeAttr("disabled");
							$("#chkSinConteoMod")[0].checked = false;
						}
						
						if(res.fechaFinHecho){
							var fechaFinHecho = new Date(res.fechaFinHecho);
							var fechaFinHechoFormato = formatoFecha(fechaFinHecho);
							var fechaFinHechoFormatoAnio = formatoFechaAnio(fechaFinHecho);
							$("#fechaFinHechos").text(fechaFinHechoFormato);
							$("#finFechaDeLosHechosMod").val(fechaFinHechoFormatoAnio);
							
							var fechaCaducidad = new Date(res.fechaCaducidad);
							var fechaCaducidadFormato = formatoFecha(fechaCaducidad);
							actualFechaCaducidad = fechaCaducidadFormato; 
							var fechaCaducidadFormatoAnio = formatoFechaAnio(fechaCaducidad);
							$("#fechaCaducidad").text(fechaCaducidadFormato);//modal modificar caso
							$("#fechaDeCaducidadMod").val(fechaCaducidadFormatoAnio);
						}
						if(res.fechaUltimaActualizacion){
							var fechaActualizacion = new Date(res.fechaUltimaActualizacion);
							var fechaActualizacionFormato = formatoFecha(fechaActualizacion);
							
							$("#fechaUltimaModificacion").text(fechaActualizacionFormato);
						}
						
						
						
						
						
						obtenerFechasAnteriores();
						
						$("#funcionario").text(res.nombreFuncionario);
						$("#nombreFuncionarioMod").val(res.nombreFuncionario);//modal modificar caso
						
						$("#resumenHechos").text(res.resumenHechos);
						$("#resumenDeLosHechosMod").val(res.resumenHechos);//modal modificar caso
						$("#estadoProcesal").text(res.estadoProcesal);
						if (res.fechaEstadoProcesal) {
							var fechaActualizacion = new Date(res.fechaEstadoProcesal);
							var fechaActualizacionFormato = formatoFecha(fechaActualizacion);
							var horas = fechaActualizacion.getHours();
							if (horas < 10) {
								horas = "0" + horas;
							} 
							var minutos = fechaActualizacion.getMinutes();
							if (minutos < 10) {
								minutos = "0" + minutos;
							}
							$("#fechaEstadoProcesal").text(fechaActualizacionFormato + " " + horas + ":" + minutos);
						}
						$("#hechosDireccion").text(res.direccionHechos);
						$("#direccionDeLosHechosMod").val(res.direccionHechos);//modal modificar caso
						
						$("#ProcesoDescripcion").text(res.direccionProceso);
						$("#direccionDespachoMod").val(res.direccionProceso);//modal modificar caso
						
						if (res.ciudadProceso != null) {
							
							$("#ProcesoMunicipio").text(res.ciudadProceso.nombre);
							codigoCiudadProceso = res.ciudadProceso.ciudadPK.codigoCiudad;
							
							$("#procesoDepartamento").text(res.ciudadProceso.departamento.nombre);
							codigoDepartamentoProceso = res.ciudadProceso.departamento.codigoDepartamento;
							//$("#ProcesoDeparmento").text(res.ciudadProceso.departamento.nombre);//modal modificar caso
							
							
							$("#ProcesoPais").text(res.ciudadProceso.departamento.pais.nombre);
							codigoPaisProceso = res.ciudadProceso.departamento.pais.codigoPais;//modal modificar caso
							
						}
						$("#comentario").text(res.comentario);
						$("#comentarioMod").val(res.comentario);
						
						//falta proceso es la direccion  en la base de datos
						if (res.ciudadHechos != null) {
							$("#hechosMunicipio").text(res.ciudadHechos.nombre);
							codigoCiudad = res.ciudadHechos.ciudadPK.codigoCiudad;
							
							$("#hechosDepartamento").text(res.ciudadHechos.departamento.nombre);
							codigoDepartamento = res.ciudadHechos.departamento.codigoDepartamento;
							
							$("#hechosPais").text(res.ciudadHechos.departamento.pais.nombre);
							codigoPais = res.ciudadHechos.departamento.pais.codigoPais;
						}
						$("#procesoEstado").text(res.estadoCaso.nombre);
						$("#estadoCaso").text(res.estadoCaso.nombre);
						
						$("#tipoCaso").text(res.tipoCaso.nombre);
						$("#tipoCasoMod").val(res.tipoCaso.codigo);//modal modificar caso
						almacenarTipoCaso=res.tipoCaso.codigo;
						
						
						$("#nombreDespacho").text(res.numeroDespacho);
						$("#numeroDespachoMod").val(res.numeroDespacho);//modal modificar caso
					}
				},
				error: function(e){
					waitingDialog.hide();
				}
			});
		}
	});
}

function navegarA(direccion){
	window.location.href = direccion;
}

function cargarPais() {
	
	cargarPaises("paisDeLosHechosMod", codigoPais);
	if (codigoDepartamento != null) {		
		cargarDepartamentosPorPais(codigoPais, "departamentoDeLosHechosMod", "municipioDeLosHechosMod", codigoDepartamento);
	}
	if (codigoCiudad != null) {
		
		cargarCiudadPorDepartamentos(codigoDepartamento, "municipioDeLosHechosMod", codigoCiudad);
	}
	
	cargarPaises("paisProcesoMod", codigoPaisProceso);
	if (codigoDepartamentoProceso != null) {		
		cargarDepartamentosPorPais(codigoPaisProceso, "deparmentoProcesoMod", "municipioProcesoMod", codigoDepartamentoProceso);
	}
	if (codigoCiudadProceso != null) {
		
		cargarCiudadPorDepartamentos(codigoDepartamentoProceso, "municipioProcesoMod", codigoCiudadProceso);
	}
	
}

function mostrarTablaBienesAfectados(codigo) {
	var documento =codigo;
	var data_caso = "codigo="+documento;
		data_caso += "&_csrf=" + $("#_csrf").val();
	var tablaHtml="<table id='tablaListadoBienesAfectadosJS' class='table table-striped' >";
	tablaHtml+="	<thead>";
	tablaHtml+="<tr>";
	tablaHtml+="<th >T\u00EDtulo</th>";
	tablaHtml+="<th >Descripción</th>";
	tablaHtml+="<th >Clase de bien</th>";
	tablaHtml+="<th >Acciones</th>";
	tablaHtml+="</tr>";
	tablaHtml+="	</thead>";
	tablaHtml+="	<tbody>";
	tablaHtml+="</tbody>";
	tablaHtml+="</table>";	
	$("#tablaListadoBienesAfectados").html(tablaHtml);
	oTable =$('#tablaListadoBienesAfectadosJS').dataTable({
		"bProcessing": false,
        "bServerSide": true,
        "bFilter":false,
        "bPaginate":false, 
        "bInfo":false,
        "sAjaxSource": contexto+"/detalleCaso/getAllTable?" + data_caso,
        "language": {
            "sProcessing":     "Procesando...",
            "sLengthMenu":     "Mostrar _MENU_ registros",
            "sZeroRecords":    "Ning\u00FAn bien disponible en este caso",
            "sEmptyTable":     "Ning\u00FAn bien disponible en este caso",
            "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
            "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix":    "",
            "sSearch":         "Buscar:",
            "sUrl":            "",
            "sInfoThousands":  ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst":    "Primero",
                "sLast":     "Último",
                "sNext":     "Siguiente",
                "sPrevious": "Anterior"
            }
        },
        "aoColumns": [
						{ "mDataProp": "tituloBienAfectado", "bSortable": false, "sWidth":"30%" },
						{ "mDataProp": "detalleBienAfectado","bSortable": false, "sWidth":"40%" },
						{ "mDataProp": "tipoBienAfectdo", "bSortable": false,  "sWidth":"20%" },
						{ "mDataProp": "acciones", "bSortable": false,  "sWidth":"10%" }

					],
		"fnServerData": function ( sSource, aoData, fnCallback ) {
			
			
			$.ajax( {
				type: 'POST',
				dataType: "json",
                url: contexto+"/detalleCaso/getAllTable?" + data_caso,
                data: aoData,
                success: fnCallback,
                error : function (e) {
                    alert (e);
                }
            } );
        }
	});
	
//	oTable.on('mouseover', 'tr', function () {
//		  	this.style.cursor = 'pointer';
//	});
	
	oTable.on('click', 'tr', function () {
			
			var aData = oTable.fnGetData(this); // get datarow
		    if (null != aData)  // null if we clicked on title row
		    {
		    }   
	});
	
}

function mostrarTablaRadicado(codigo) {
	var documento =codigo;
	var data_caso = "codigo="+documento;
		data_caso += "&_csrf=" + $("#_csrf").val();
	var tablaHtml="<table id='tablaListadoRadicados' class='table table-striped' >";
	tablaHtml+="	<thead>";
	tablaHtml+="<tr>";
	tablaHtml+="<th >Número Radicado</th>";
	tablaHtml+="<th >Instancia</th>";
	// tablaHtml+="<th >Acciones</th>";
	tablaHtml+="</tr>";
	tablaHtml+="	</thead>";
	tablaHtml+="	<tbody>";
	tablaHtml+="</tbody>";
	tablaHtml+="</table>";
	$("#tablaRadicado").html(tablaHtml);
	oTable =$('#tablaListadoRadicados').dataTable({
		"bProcessing": false,
        "bServerSide": true,
        "bFilter":false,
        "bPaginate":false,
        "createdRow": function(row, data, dataIndex) {
        	$(row).css("cursor","pointer");
        	},
        "bInfo":false,
        "sAjaxSource": contexto+"/detalleCaso/getAllTableRadicado?" + data_caso,
        "language": {
            "sProcessing":     "Procesando...",
            "sLengthMenu":     "Mostrar _MENU_ registros",
            "sZeroRecords":    "Ning\u00FAn radicado disponible en este caso",
            "sEmptyTable":     "Ning\u00FAn radicado disponible en este caso",
            "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
            "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix":    "",
            "sSearch":         "Buscar:",
            "sUrl":            "",
            "sInfoThousands":  ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst":    "Primero",
                "sLast":     "Último",
                "sNext":     "Siguiente",
                "sPrevious": "Anterior"
            }
        },
        "aoColumns": [
						{ "mDataProp": "codigoRadicado", "bSortable": false, "sWidth":"30%" },
						{ "mDataProp": "instanciaRadicado","bSortable": false, "sWidth":"40%" }
						// { "mDataProp": "accionEditar","bSortable": false, "sWidth":"10%" }
					],
		"fnServerData": function ( sSource, aoData, fnCallback ) {
			
			
			$.ajax( {
				type: 'POST',
				dataType: "json",
                url: contexto+"/detalleCaso/getAllTableRadicado?" + data_caso,
                data: aoData,
                success: fnCallback,
                error : function (e) {
                    alert (e);
                }
            } );
        }
	});
	
	radicadosTable = oTable;
	
//	oTable.on('mouseover', 'tr', function () {
//		  	this.style.cursor = 'pointer';
//	});
	
	oTable.on('click', 'tr', function () {
			
			var aData = radicadosTable.fnGetData(this); // get datarow
		    if (null != aData)  // null if we clicked on title row
		    {
//		    	document.location.href="cliente?documento=" + aData.documento;
		    	if ( radicadosTable.fnIsOpen(this) ){
		    		radicadosTable.fnClose( this );
		        }else{
		        	radicadosTable.fnOpen( this, fnFormatDetails(this), 'details' );
		        }
		    }   
	});
	
}

function fnFormatDetails ( nTr )
{
    var radicadosAcumulados = radicadosTable.fnGetData( nTr ).radicadosAcumulados;
    var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;" class="table table-striped dataTable no-footer" width = "40%">';
    sOut += '<tr><th colspan=2 >Radicados Acumulados:</th></tr>';
    sOut += '<tr><th>Tipo</th><th>Número</th></tr>';
    $.each(radicadosAcumulados,function(index,radicado){
    	sOut += '<tr><td>'+radicado.tipo+'</td>';
    	if(radicado.tipo == "GJA"){
    		sOut += '<td><a href=\'visorCaso?radicado='+radicado.radicado+' \'>'+radicado.radicado+'</a></td></tr>';	
    	}else{
    		sOut += '<td>'+radicado.radicado+'</td></tr>';
    	}
    	
    });
    sOut += '</table>';
     
    return sOut;
}


function mostrarTelefono(id){
	$('#'+"campoTelefono"+id).show();
	$('#'+"masTelefono"+id).hide();
	$('#'+"menosTelefono"+id).show();
}
function mostrarEmail(id){
	$('#'+"campoCorreo"+id).show();
	$('#'+"masCorreo"+id).hide();
	$('#'+"menosCorreo"+id).show();
}
function mostrarCelular(id){
	$('#'+"campoCelular"+id).show();
	$('#'+"masCelular"+id).hide();
	$('#'+"menosCelular"+id).show();
}
function ocultarTelefono(id){ 
	$('#'+"campoTelefono"+id).hide();
	$('#'+"masTelefono"+id).show();
	$('#'+"menosTelefono"+id).hide();
}
function ocultarEmail(id){
	$('#'+"campoCorreo"+id).hide();
	$('#'+"masCorreo"+id).show();
	$('#'+"menosCorreo"+id).hide();
}
function ocultarCelular(id){
	$('#'+"campoCelular"+id).hide();
	$('#'+"masCelular"+id).show();
	$('#'+"menosCelular"+id).hide();
}


/*Metodos Demandante*/
function mostrarTelefonoDemandante(id){
	$('#'+"campoTelefonoDemandante"+id).show();
	$('#'+"masTelefonoDemandante"+id).hide();
	$('#'+"menosTelefonoDemandante"+id).show();
}
function mostrarEmailDemandante(id){
	$('#'+"campoCorreoDemandante"+id).show();
	$('#'+"masCorreoDemandante"+id).hide();
	$('#'+"menosCorreoDemandante"+id).show();
}
function mostrarCelularDemandante(id){
	$('#'+"campoCelularDemandante"+id).show();
	$('#'+"masCelularDemandante"+id).hide();
	$('#'+"menosCelularDemandante"+id).show();
}
function ocultarTelefonoDemandante(id){ 
	$('#'+"campoTelefonoDemandante"+id).hide();
	$('#'+"masTelefonoDemandante"+id).show();
	$('#'+"menosTelefonoDemandante"+id).hide();
}
function ocultarEmailDemandante(id){
	$('#'+"campoCorreoDemandante"+id).hide();
	$('#'+"masCorreoDemandante"+id).show();
	$('#'+"menosCorreoDemandante"+id).hide();
}
function ocultarCelularDemandante(id){
	$('#'+"campoCelularDemandante"+id).hide();
	$('#'+"masCelularDemandante"+id).show();
	$('#'+"menosCelularDemandante"+id).hide();
}

/*Metodos Demandado*/ 
function mostrarTelefonoDemandado(id){
	$('#'+"campoTelefonoDemandado"+id).show();
	$('#'+"masTelefonoDemandado"+id).hide();
	$('#'+"menosTelefonoDemandado"+id).show();
}
function mostrarEmailDemandado(id){
	$('#'+"campoCorreoDemandado"+id).show();
	$('#'+"masCorreoDemandado"+id).hide();
	$('#'+"menosCorreoDemandado"+id).show();
}
function mostrarCelularDemandado(id){
	$('#'+"campoCelularDemandado"+id).show();
	$('#'+"masCelularDemandado"+id).hide();
	$('#'+"menosCelularDemandado"+id).show();
}
function ocultarTelefonoDemandado(id){ 
	$('#'+"campoTelefonoDemandado"+id).hide();
	$('#'+"masTelefonoDemandado"+id).show();
	$('#'+"menosTelefonoDemandado"+id).hide();
}
function ocultarEmailDemandado(id){
	$('#'+"campoCorreoDemandado"+id).hide();
	$('#'+"masCorreoDemandado"+id).show();
	$('#'+"menosCorreoDemandado"+id).hide();
}
function ocultarCelularDemandado(id){
	$('#'+"campoCelularDemandado"+id).hide();
	$('#'+"masCelularDemandado"+id).show();
	$('#'+"menosCelularDemandado"+id).hide();
}

/*Metodos Testigo*/
function mostrarTelefonoTestigo(id){
	$('#'+"campoTelefonoTestigo"+id).show();
	$('#'+"masTelefonoTestigo"+id).hide();
	$('#'+"menosTelefonoTestigo"+id).show();
}
function mostrarEmailTestigo(id){
	$('#'+"campoCorreoTestigo"+id).show();
	$('#'+"masCorreoTestigo"+id).hide();
	$('#'+"menosCorreoTestigo"+id).show();
}
function mostrarCelularTestigo(id){
	$('#'+"campoCelularTestigo"+id).show();
	$('#'+"masCelularTestigo"+id).hide();
	$('#'+"menosCelularTestigo"+id).show();
}
function ocultarTelefonoTestigo(id){ 
	$('#'+"campoTelefonoTestigo"+id).hide();
	$('#'+"masTelefonoTestigo"+id).show();
	$('#'+"menosTelefonoTestigo"+id).hide();
}
function ocultarEmailTestigo(id){
	$('#'+"campoCorreoTestigo"+id).hide();
	$('#'+"masCorreoTestigo"+id).show();
	$('#'+"menosCorreoTestigo"+id).hide();
}
function ocultarCelularTestigo(id){
	$('#'+"campoCelularTestigo"+id).hide();
	$('#'+"masCelularTestigo"+id).show();
	$('#'+"menosCelularTestigo"+id).hide();
}

/*Metodos Abogado*/
function mostrarTelefonoAbogado(id){
	$('#'+"campoTelefonoAbogado"+id).show();
	$('#'+"masTelefonoAbogado"+id).hide();
	$('#'+"menosTelefonoAbogado"+id).show();
}
function mostrarEmailAbogado(id){
	$('#'+"campoCorreoAbogado"+id).show();
	$('#'+"masCorreoAbogado"+id).hide();
	$('#'+"menosCorreoAbogado"+id).show();
}
function mostrarCelularAbogado(id){
	$('#'+"campoCelularAbogado"+id).show();
	$('#'+"masCelularAbogado"+id).hide();
	$('#'+"menosCelularAbogado"+id).show();
}
function ocultarTelefonoAbogado(id){ 
	$('#'+"campoTelefonoAbogado"+id).hide();
	$('#'+"masTelefonoAbogado"+id).show();
	$('#'+"menosTelefonoAbogado"+id).hide();
}
function ocultarEmailAbogado(id){
	$('#'+"campoCorreoAbogado"+id).hide();
	$('#'+"masCorreoAbogado"+id).show();
	$('#'+"menosCorreoAbogado"+id).hide();
}
function ocultarCelularAbogado(id){
	$('#'+"campoCelularAbogado"+id).hide();
	$('#'+"masCelularAbogado"+id).show();
	$('#'+"menosCelularAbogado"+id).hide();
}
/*Metodos Otros*/
function mostrarTelefonoOtro(id){
	$('#'+"campoTelefonoOtro"+id).show();
	$('#'+"masTelefonoOtro"+id).hide();
	$('#'+"menosTelefonoOtro"+id).show();
}
function mostrarEmailOtro(id){
	$('#'+"campoCorreoOtro"+id).show();
	$('#'+"masCorreoOtro"+id).hide();
	$('#'+"menosCorreoOtro"+id).show();
}
function mostrarCelularOtro(id){
	$('#'+"campoCelularOtro"+id).show();
	$('#'+"masCelularOtro"+id).hide();
	$('#'+"menosCelularOtro"+id).show();
}
function ocultarTelefonoOtro(id){ 
	$('#'+"campoTelefonoOtro"+id).hide();
	$('#'+"masTelefonoOtro"+id).show();
	$('#'+"menosTelefonoOtro"+id).hide();
}
function ocultarEmailOtro(id){
	$('#'+"campoCorreoOtro"+id).hide();
	$('#'+"masCorreoOtro"+id).show();
	$('#'+"menosCorreoOtro"+id).hide();
}
function ocultarCelularOtro(id){
	$('#'+"campoCelularOtro"+id).hide();
	$('#'+"masCelularOtro"+id).show();
	$('#'+"menosCelularOtro"+id).hide();
}


function mostrarModalConfirmacion(codigoCaso, codigoEquipoCaso, codigoMiembro, codigoTipoMiembro) {
	casoEquipoCasoPK.codigo = codigoCaso;
	casoEquipoCasoPK.codigoEquipoCaso = codigoEquipoCaso;
	casoEquipoCasoPK.miembro = codigoMiembro;
	tipoMiembro = codigoTipoMiembro;
	$("#modalConfEliminarMiembro").modal("show");
}

function mostrarModalJustificacion() {
	$("#modalConfEliminarMiembro").modal("hide");
	$("#modalJustificacionMiembro").modal("show");
}

function validarEliminacion() {
	var tipoMiembro = casoEquipoCasoPK.miembro;
	if (!EstaVacio($("#justificacionEliminacion").val())) {
		justificacionEliminacion = $("#justificacionEliminacion").val();
		switch(tipoMiembro) {
		    case ABOGADO:
		    	validarActividades();
		        break;
		    case VICTIMA:
		    	validarCantidadMiembros(VICTIMA)	
		        break;
		    case DEMANDADO:
		    	validarCantidadMiembros(DEMANDADO)
		    	break;
		    case DEMANDANTE:
		    	validarCantidadMiembros(DEMANDANTE)
		    	break;		
		    case OTRO:
		    	eliminarMiembro();
		    	break;	
		    default:
		    	return true;
		}
	} else {
		$("#alertErrorEliminacionMiembro").show();
		$("#divMensajeErrorEliminacionMiembro").text("Debe Ingresar una justficaci\u00F3n.");
		setTimeout(function () {
			$("#alertErrorEliminacionMiembro").show();
			$("#divMensajeErrorEliminacionMiembro").text("");
		}, 10000);
	}
	
}

function validarCantidadMiembros(tipoMiembro) {
	var data_caso = "_csrf=" + $("#_csrf").val() + "&codigo=" + casoEquipoCasoPK.codigo + "&tipoMiembro=" + tipoMiembro;
	waitingDialog.show('');
	$.ajax({
	     type : "POST",
		  dataType: "json",
		  url: contexto + "/caso/validarCantidadMiembros",
		  data: data_caso,
		  success: function (response) {
			  waitingDialog.hide();
			  if (response.STATUS == "SUCCESS")
				  validarContactos();
			  else {
				  $("#alertErrorEliminacionMiembro").show();
				  switch(tipoMiembro) {
				  	case VICTIMA:
				  		$("#alertErrorEliminacionMiembro").text("Error no es posible eliminar, debe haber al menos una victima asociada al caso.");
				  		break;
				  	case DEMANDANTE:
				  		$("#alertErrorEliminacionMiembro").text("Error no es posible eliminar, debe haber al menos un demandante asociado al caso.");
				  		break;
				  	case DEMANDADO:
				  		$("#alertErrorEliminacionMiembro").text("Error no es posible eliminar, debe haber al menos un demandado asociado al caso.");
				  		break;				  	
				  }
			  }
		  },
		  error: function(e){
			  waitingDialog.hide();
		  }
	});
}

function eliminarMiembro() {
	var data_caso = "_csrf=" + $("#_csrf").val() + "&codigo=" + casoEquipoCasoPK.codigo;
	data_caso += "&codigoEquipoCaso=" + casoEquipoCasoPK.codigoEquipoCaso + "&miembro=" + casoEquipoCasoPK.miembro;
	data_caso += "&justificacion=" + justificacionEliminacion + "&usuarioModificacion=" + $("#idusercreacion").val() + "&codigocaso.codigo=" + $("#codigoParam").val();
	waitingDialog.show('');
	$.ajax({
	      type : "POST",
		  dataType: "json",
		  url: contexto + "/caso/inactivarEquipoCaso",
		  data: data_caso,
		  success: function (response) {
			  waitingDialog.hide();
			  $("#modalNuevoResponsable").modal("hide");
			  crearUrlRedireccionarA("actMiembro");
		  },
		  error: function(e){
			    waitingDialog.hide();
			}
	});
}

function crearUrlRedireccionarA(parametroRedireccionar) {
	urlConsultaCliente = location.href;
	if (urlConsultaCliente.lastIndexOf("&") == -1) {
		document.location.href = urlConsultaCliente + "&" + parametroRedireccionar + "=" + true;
	} else {
		urlConsultaCliente = urlConsultaCliente.substring(0, urlConsultaCliente.lastIndexOf("&"));
		document.location.href = urlConsultaCliente + "&" + parametroRedireccionar + "=" + true;
	}
}

/**
 * Metodo encargado de validar que las tareas del miembrop que se va a eliminar (abogado) esten en estado finalizado.
 */
function validarActividades() {
	var data_caso = "_csrf=" + $("#_csrf").val() + "&codigo=" + casoEquipoCasoPK.codigo;
	data_caso += "&codigoEquipoCaso=" + casoEquipoCasoPK.codigoEquipoCaso + "&miembro=" + casoEquipoCasoPK.miembro;
	waitingDialog.show('');$.ajax({
	     type : "POST",
		 dataType: "json",
		 url: contexto + "/caso/consultarEquipoCaso",
		 data: data_caso,
		 success: function (response) {
			 waitingDialog.hide();
			  if (response.casoEquipoCaso.hasActividadesPendientes) {	
				  $("#alertErrorNuevoResponsable").show();
				  $("#divMensajeErrorNuevoResponsable").text("El miembro del equipo que se va a eliminar tiene tareas pendientes, por favor seleccione un abogado.");
				  mostrarModalNuevoResposable();
			  } else {				  
				  validarResponsable();
			  }
		  },
		  error: function(e){
			    waitingDialog.hide();
			}
	});
}

/**
 * Metodo encargado de validar que al menos un abogado este a cargo del caso.
 */
function validarResponsable() {
	var data_caso = "_csrf=" + $("#_csrf").val() + "&codigo=" + casoEquipoCasoPK.codigo + "&tipoMiembro=" + ABOGADO;
	waitingDialog.show('');
	$.ajax({
	     type : "POST",
		  dataType: "json",
		  url: contexto + "/caso/validarCantidadMiembros",
		  data: data_caso,
		  success: function (response) {
			  waitingDialog.hide();
			  if (response.STATUS == "SUCCESS")
				  eliminarMiembro();
			  else {
				  $("#alertErrorNuevoResponsable").show();
				  $("#divMensajeErrorNuevoResponsable").text("Debe haber al menos un abogado asociado al caso, por favor seleccione un abogado..");
				  mostrarModalNuevoResposable();
			  }
		  },
		  error: function(e){
			  waitingDialog.hide();
		  }
	});
}

function mostrarModalNuevoResposable() {
	justificacionEliminacion = $("#justificacionEliminacion").val();
	cerrarModalJustificacion();
	var data_caso = "_csrf=" + $("#_csrf").val() + "&codigo=" + casoEquipoCasoPK.codigoEquipoCaso; 
	waitingDialog.show('');$.ajax({
		  dataType: "json",
		  url: contexto + "/maestro/obtenerAbogadosFueraDelCaso",
		  data: data_caso,
		  success: function (response) {
			  waitingDialog.hide();
			  var select = document.getElementById("cmbAbogadoReemplazo");
			  select.innerHTML = "";
			  var option = document.createElement("option");
			  option.value = "";
			  option.text = "Seleccionar";
			  select.add(option); 
			  $.each(response.abogados, function(index, optionData) {	  
				  var select = document.getElementById("cmbAbogadoReemplazo");
				  var option = document.createElement("option");
				  option.value = optionData.codigo;
				  option.text = optionData.nombre;
				  select.add(option);
			  });
			  $("#modalNuevoResponsable").modal("show");
		  },
		  error: function(e){
			  waitingDialog.hide();
			}
	});
	
}

function asignarTareasNuevoAbogado(){
	var data_caso = "_csrf=" + $("#_csrf").val() + "&codigo=" + casoEquipoCasoPK.codigo;
	data_caso += "&codigoEquipoCaso=" + casoEquipoCasoPK.codigoEquipoCaso + "&miembro=" + casoEquipoCasoPK.miembro;
	data_caso += "&idUsuario=" + $("#cmbAbogadoReemplazo").val();
	waitingDialog.show('');$.ajax({
		  dataType: "json",
		  url: contexto + "/caso/asignarNuevoResponsable",
		  data: data_caso,
		  success: function (response) {
			  waitingDialog.hide();
			  if (response.STATUS == "SUCCESS")
				  eliminarMiembro();
		  },
		  error: function(e){
			  waitingDialog.hide();
			}
	});
}

function cerrarModalJustificacion() {
	$("#justificacionEliminacion").val("");
	$("#modalJustificacionMiembro").modal("hide");
}

function validarContactos() {
	justificacionEliminacion = $("#justificacionEliminacion").val();
	cerrarModalJustificacion();
	var data_caso = "_csrf=" + $("#_csrf").val() + "&codigo=" + casoEquipoCasoPK.codigo;
	data_caso += "&codigoEquipoCaso=" + casoEquipoCasoPK.codigoEquipoCaso + "&miembro=" + casoEquipoCasoPK.miembro;
	waitingDialog.show('');
	$.ajax({
	     type : "POST",
		  dataType: "json",
		  url: contexto + "/caso/consultarEquipoCaso",
		  data: data_caso,
		  success: function (response) {
				  waitingDialog.hide();
			  if (response.casoEquipoCaso.contacto && response.casoEquipoCaso.contacto == CONTACTO_SI)
				  validarCantidadContactos();
			  else 
				  eliminarMiembro(); 
		  },
		  error: function(e){
			  waitingDialog.hide();
		  }
	});
}

function validarCantidadContactos() {
	var data_caso = "_csrf=" + $("#_csrf").val() + "&codigo=" + casoEquipoCasoPK.codigo;
	waitingDialog.show('');$.ajax({
	     type : "POST",
		  dataType: "json",
		  url: contexto + "/caso/validarCantidadContactos",
		  data: data_caso,
		  success: function (response) {
			  waitingDialog.hide();
			  if (response.STATUS == "SUCCESS")
				  eliminarMiembro();
			  else {
				  $("#divMensajeErrorNuevoContacto").html("Debe haber al menos un miembro marcado como contacto asociado al caso, por favor seleccione un contacto.");
				  $("#divMensajeErrorNuevoContacto").removeClass("hide");
				  mostrarModalNuevoContacto();
			  }
		  },
		  error: function(e){
			  waitingDialog.hide();
		  }
	});
}

function mostrarModalNuevoContacto() {
	var data_caso = "_csrf=" + $("#_csrf").val() + "&codigo=" + casoEquipoCasoPK.codigo;
	data_caso += "&codigoEquipoCaso=" + casoEquipoCasoPK.codigoEquipoCaso + "&miembro=" + casoEquipoCasoPK.miembro;
	waitingDialog.show('');$.ajax({
		  dataType: "json",
		  url: contexto + "/caso/obtenerDemasMiembrosCaso",
		  data: data_caso,
		  success: function (response) {
			  waitingDialog.hide();
			  arrayTiposMiembrosContactos = [];
			  var select = document.getElementById("cmbContactoReemplazo");
			  select.innerHTML = "";
			  var option = document.createElement("option");
			  option.value = "";
			  option.text = "Seleccionar";
			  select.add(option);
			  arrayTiposMiembrosContactos.push("");
			  var option = document.createElement("option");
			  option.value = "Nuevo";
			  option.text = "Nuevo Contacto";
			  select.add(option);
			  arrayTiposMiembrosContactos.push("");
			  $.each(response.miembros, function(index, optionData) {	  
				  var select = document.getElementById("cmbContactoReemplazo");
				  var option = document.createElement("option");
				  option.value = optionData.codigo;
				  option.text = optionData.nombre;
				  select.add(option);
				  arrayTiposMiembrosContactos.push(optionData.tipoMiembro);
			  });
			  $("#modalNuevoContacto").modal("show");
		  },
		  error: function(e){
			  waitingDialog.hide();
		  }
	});
}

function cerrarModalNuevoContacto() {
	$("#modalNuevoContacto").modal("hide");
}

function habilitarNuevoContacto(select) {
	if ($(select).val() == OPCION_NUEVO_CONTACTO) {
		$("#divNuevoContactoForm").removeAttr("hidden");
		$("#btnEliminarMiembro").text("Guardar");
	} else {		
		ocultarModalNuevoContacto();
		$("#btnEliminarMiembro").text("Eliminar");
	}
}

function ocultarModalNuevoContacto() {
	$("#divNuevoContactoForm").attr("hidden", "hidden");
}

function validarNuevoContactoForm() {
	var erroresNuevoContacto = [];
	var isError = false;
	
	//Valida los tipos de miembros	
	$("#modalNuevoContacto select[name=tipoDeMiembro]").each(function(){
		if (EstaVacio($(this).val())) {
			$(this).addClass("campoTextoError");
			isError = true;
			erroresNuevoContacto.push("El campo tipo de miembro es obligatorio.");
		} else {
			$(this).removeClass("campoTextoError");
		}
	});
	$("#modalNuevoContacto #formularioNombreMiembroNatural input[name=nombresMiembro]").each(function(){
		if (EstaVacio($(this).val())) {
			$(this).addClass("campoTextoError");
			isError = true;
			erroresNuevoContacto.push("El campo nombres es obligatorio.");
		} else {
			$(this).removeClass("campoTextoError");
		}
	});
	// Valida el apellido del miembro del equipo
	$("#modalNuevoContacto input[name=apellidosMiembro]").each(function(){
		if (EstaVacio($(this).val())) {
			$(this).addClass("campoTextoError");
			isError = true;
			erroresNuevoContacto.push("El campo apellidos es obligatorio.");
		} else {
			$(this).removeClass("campoTextoError");
		}
	});
	// valida los telefonos
	$("#modalNuevoContacto input[name=telefonoMiembro]").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresNuevoContacto.push("El campo Tel\u00E9fono es obligatorio.");
		} else {
			$(this).removeClass("campoTextoError");
		}
	});
	//valida los correos 
	$("#modalNuevoContacto input[name=correoMiembro]").each(function(){
		if(EstaVacio($(this).val())){
			$(this).addClass("campoTextoError");
			isError = true;
			erroresNuevoContacto.push("El campo correo es obligatorio.");
		} else {
			$(this).removeClass("campoTextoError");
		}
	});
	
	if (isError) {
		var mensajeError = "";
		$.each(erroresNuevoContacto, function(index, optionData) {
			mensajeError += "<p>" + (index + 1) + ". " + optionData + "</p>";
		});
		$("#divMensajeErrorNuevoContacto").html(mensajeError);
		$("#divMensajeErrorNuevoContacto").removeClass("hide");
		return false;
	} else {
		$("#divMensajeErrorNuevoContacto").html("");
		$("#divMensajeErrorNuevoContacto").addClass("hide");
		return true;
	}
}

function guardarNuevoContacto() {
	if ($("#cmbContactoReemplazo").val() != "") {
		var data_caso = "_csrf=" + $("#_csrf").val();
		if ($("#cmbContactoReemplazo").val() != OPCION_NUEVO_CONTACTO) {		
			data_caso += "&casoEquipoCasoPK.codigo=" + casoEquipoCasoPK.codigo;
			data_caso += "&casoEquipoCasoPK.codigoEquipoCaso=" + $("#cmbContactoReemplazo").val()
			data_caso += "&casoEquipoCasoPK.miembro=" + arrayTiposMiembrosContactos[$("#cmbContactoReemplazo option:selected").index()];
		} else {
			if (validarNuevoContactoForm()) {
				data_caso += "&casoEquipoCasoPK.codigo=" + casoEquipoCasoPK.codigo;
				data_caso += "&casoEquipoCasoPK.miembro=" + $("#tipoDeMiembro").val();
				data_caso += "&direccion=" + $("#direccionMiembro").val();
				if ($("#municipioMiembroVictimaDemandante").val() != "" && $("#municipioMiembroVictimaDemandante").val() != null) {
					data_caso += "&ciudadEquipoCaso.ciudadPK.codigoCiudad=" +$("#municipioMiembroVictimaDemandante").val();
					data_caso += "&ciudadEquipoCaso.ciudadPK.codigoDepartamento=" + $("#departamentoMiembroVictimaDemandante").val();
				}
				
				data_caso += "&personajuridica=" + "N";
				data_caso += "&equipoCaso.nombre=" + $("#nombresMiembro").val();
				data_caso += "&equipoCaso.apellido=" + $("#apellidosMiembro").val();
				
				data_caso += "&equipoCaso.identificacion=" + $("#identificacionMiembro").val();
				
				if($("#parentescoMiembro").val() != "") {
					data_caso += "&parentesco.codigo=" + $("#parentescoMiembro").val();
				}
				data_caso +="&activo="+"S";
				data_caso +="&contacto="+"S";
				
				$("input[name = 'telefonoMiembro']").each(function(index, numeroTelefono){
					if(!EstaVacio($(numeroTelefono).val())){
						data_caso += "&equipoCaso.telefonoList[" + index + "].numero=" + $(numeroTelefono).val();
					}
				});
				
				$("input[name = 'correoMiembro']").each(function(index,correo){
					if (!EstaVacio($(correo).val())) {
						data_caso +="&equipoCaso.correoList[" + index + "].correo=" + $(correo).val();
					}
				});
			} else {
				return false;
			}
		}
		waitingDialog.show('');$.ajax({
			dataType: "json",
			url: contexto + "/caso/guardarNuevoContacto",
			data: data_caso,
			success: function (response) {
				waitingDialog.hide();
				ocultarModalNuevoContacto();
				eliminarMiembro();
			}, 
			error: function (error) {
				waitingDialog.hide();
				$("#divMensajeErrorNuevoContacto").html("No fue posible guardar el nuevo contacto");
				$("#divMensajeErrorNuevoContacto").removeClass("hide");
			}
		});
	} else {
		$("#divMensajeErrorNuevoContacto").html("Por favor seleccione un nuevo contacto");
		$("#divMensajeErrorNuevoContacto").removeClass("hide");
	}
}

function irJustificacionRadicado() {
	$("#modalConfEliminarRadicado").modal("hide");
	$("#modalJustificacionRadicado").modal("show");
}

function irConfirmacionRadicado(codigo) {
	codigoRadicado = codigo;
	$("#modalConfEliminarRadicado").modal("show");
}

function cerrarJustificacionRadicado() {
	$("#justificacionRadicado").val("");
	$("#modalJustificacionRadicado").modal("hide");
}

function eliminarRadicado() {
	if (!EstaVacio($("#justificacionRadicado").val())) {
		justificacionEliminacion = $("#justificacionRadicado").val();
		var data_radicado = "_csrf=" + $("#_csrf").val() + "&radicadoPK.codigoRadicado=" + codigoRadicado;
		data_radicado += "&justificacion=" + justificacionEliminacion + "&usuarioModificacion=" + $("#idusercreacion").val() + "&radicadoPK.codigoCaso=" + $("#codigoParam").val();
		waitingDialog.show('');$.ajax({
			  dataType: "json",
			  type: "POST",
			  url: contexto + "/detalleCaso/eliminarRadicado",
			  data: data_radicado,
			  success: function (response) {
				  waitingDialog.hide();
				  cerrarJustificacionRadicado();
				  mostrarTablaRadicado($("#codigoParam").val());
				  $("#divMessageRadicado").removeClass("hide");
				  $("#successMessageRadicado").text("Se elimin\u00F3 el radicado");
				  setTimeout(function () {
					  $("#divMessageRadicado").addClass("hide");
					  $("#successMessageRadicado").text("");
				  }, 5000);
			  },
			  error: function(e){
				  waitingDialog.hide();
			  }
		});
	} else {
		$("#alertErrorEliminacionRadicado").removeClass("hide");
		$("#divMensajeErrorEliminacionRadicado").text("Debe Ingresar una justficaci\u00F3n.");
		setTimeout(function () {
			$("#alertErrorEliminacionRadicado").addClass("hide");
			$("#divMensajeErrorEliminacionRadicado").text("");
		}, 10000);
	}
}

function irConfirmacionBienAfectado(codigo) {
	codigoBienAfectado = codigo;
	$("#modalConfEliminarBienAfectado").modal("show");
}

function irJustificacionBienAfectado() {
	$("#modalConfEliminarBienAfectado").modal("hide");
	$("#modalJustificacionBienAfectado").modal("show");
}

function cerrarJustificacionBienAfectado() {
	$("#justificacionBienAfectado").val("");
	$("#modalJustificacionBienAfectado").modal("hide");
}

function eliminarBienAfectado() {
	if (!EstaVacio($("#justificacionBienAfectado").val())) {
		justificacionEliminacion = $("#justificacionBienAfectado").val();
		var data_bienAfectado = "_csrf=" + $("#_csrf").val() + "&bienAfectadoPK.codigo=" + codigoBienAfectado + "&bienAfectadoPK.codigoCaso=" + $("#codigoParam").val();
		data_bienAfectado += "&justificacion=" + justificacionEliminacion + "&usuarioModificacion=" + $("#idusercreacion").val() + "&codigocaso.codigo=" + $("#codigoParam").val();
		waitingDialog.show('');$.ajax({
			  dataType: "json",
			  type: "POST",
			  url: contexto + "/detalleCaso/eliminarBienAfectado",
			  data: data_bienAfectado,
			  success: function (response) {
				  waitingDialog.hide();
				  if (response.STATUS == "SUCCESS") {
					  cerrarJustificacionBienAfectado();
					  mostrarTablaBienesAfectados($("#codigoParam").val());
					  $("#divMessageBienAfectado").removeClass("hide");
					  $("#successMessageBienAfectado").text("Se elimin\u00F3 el bien afectado.");
					  setTimeout(function () {
						  $("#divMessageBienAfectado").addClass("hide");
						  $("#successMessageBienAfectado").text("");
					  }, 5000);					  
				  }
			  },
			  error: function(e){
				  waitingDialog.hide();
			  }
		});
	} else {
		$("#alertErrorEliminacionBienAfectado").removeClass("hide");
		$("#divMensajeErrorEliminacionBienAfectado").text("Debe Ingresar una justficaci\u00F3n.");
		setTimeout(function () {
			$("#alertErrorEliminacionBienAfectado").addClass("hide");
			$("#divMensajeErrorEliminacionBienAfectado").text("");
		}, 10000);
	}
}

function irConfirmacionTarea(codigo) {
	codigoTarea = codigo;
	$("#modalConfEliminarTarea").modal("show");
}

function mostrarModalAsociarArchivo(codigo) {
	codigoTareaArchivo=codigo;
	listarArchivos();
	$("#modalAsociarArchivo").modal("show");
}

function mostrarModalAsociarArchivoPrestamo(codigo,codigoCaso) {
	codigoPrestamoArchivo=codigo;
	codigoCasoPrestamoArchivo=codigoCaso;
	$("#modalAsociarArchivoPrestamo").modal("show");
	listarArchivosPrestamo(codigo,codigoCaso);
}

function cerrarJustificacionTarea() {
	$("#justificacionTarea").val("");
	$("#modalJustificacionTarea").modal("hide");
}

function irJustificacionTarea() {
	$("#modalConfEliminarTarea").modal("hide");
	$("#modalJustificacionTarea").modal("show");
}

function eliminarTarea() {
	if (!EstaVacio($("#justificacionTarea").val())) {
		justificacionEliminacion = $("#justificacionTarea").val();
		var data_Tarea = "_csrf=" + $("#_csrf").val() + "&codigoTarea=" + codigoTarea;
		data_Tarea += "&justificacion=" + justificacionEliminacion + "&usuarioModificacion=" + $("#idusercreacion").val() + "&codigocaso.codigo=" + $("#codigoParam").val();
		waitingDialog.show('');
		$.ajax({
			  dataType: "json",
			  type: "POST",
			  url: contexto + "/detalleCaso/eliminarTarea",
			  data: data_Tarea,
			  success: function (response) {
				  waitingDialog.hide();
				  if (response.STATUS == "SUCCESS") {
					  cerrarJustificacionTarea();
					  crearUrlRedireccionarA("actTarea");
				  }
			  },
			  error: function(e){
				  waitingDialog.hide();
			  }
		});
	} else {
		$("#alertErrorEliminacionTarea").removeClass("hide");
		$("#divMensajeErrorEliminacionTarea").text("Debe Ingresar una justficaci\u00F3n.");
		setTimeout(function () {
			$("#alertErrorEliminacionTarea").addClass("hide");
			$("#divMensajeErrorEliminacionTarea").text("");
		}, 10000);
	}
}

function irConfirmacionActividad(codigo) {
	codigoActividad = codigo;
	$("#modalConfEliminarActividad").modal("show");
}

function cerrarJustificacionActividad() {
	$("#justificacionActividad").val("");
	$("#modalJustificacionActividad").modal("hide");
}

function irJustificacionActividad() {
	$("#modalConfEliminarActividad").modal("hide");
	$("#modalJustificacionActividad").modal("show");
}

function eliminarActividad() {
	if (!EstaVacio($("#justificacionActividad").val())) {
		justificacionEliminacion = $("#justificacionActividad").val();
		var data_Actividad = "_csrf=" + $("#_csrf").val() + "&codigoActividadParticular=" + codigoActividad;
		data_Actividad += "&justificacion=" + justificacionEliminacion + "&usuarioModificacion=" + $("#idusercreacion").val() + "&codigoCaso.codigo=" + $("#codigoParam").val();
		waitingDialog.show('');
		$.ajax({
			  dataType: "json",
			  type: "POST",
			  url: contexto + "/detalleCaso/eliminarActividad",
			  data: data_Actividad,
			  success: function (response) {
				  waitingDialog.hide();
				  if (response.STATUS == "SUCCESS") {
					  cerrarJustificacionActividad();
					  crearUrlRedireccionarA("actActividad");
				  }
			  },
			  error: function(e){
				  waitingDialog.hide();
			  }
		});
	} else {
		$("#alertErrorEliminacionActividad").removeClass("hide");
		$("#divMensajeErrorEliminacionActividad").text("Debe Ingresar una justficaci\u00F3n.");
		setTimeout(function () {
			$("#alertErrorEliminacionActividad").addClass("hide");
			$("#divMensajeErrorEliminacionActividad").text("");
		}, 10000);
	}
}


function listarArchivos(){
	var data_decode = "_csrf=" + $("#_csrf").val()+"&codigoTarea="+codigoTareaArchivo ;
	var tablaHtml="<table id='tablaArchivos' class='table table-striped table-hover' >";
	tablaHtml+="	<thead>";
	tablaHtml+="<tr class='tr_titulo'>";
	tablaHtml+="<th >C&oacute;digo Archivo</th>";
	tablaHtml+="<th >Nombre Archivo</th>";
	tablaHtml+="<th >Descarga</th>";
	tablaHtml+="</tr>";
	tablaHtml+="	</thead>";
	tablaHtml+="	<tbody>";
	tablaHtml+="</tbody>";
	tablaHtml+="</table>";
	$("#listadoArchivos").html(tablaHtml);
	oTable =$('#tablaArchivos').dataTable({
		"bProcessing": true,
        "bServerSide": true,
        "bFilter":false,
        "sAjaxSource": contexto + "/fileUpload/mostrarArchivos?" + data_decode,
        "aaSorting": [[ 1, "asc" ]],
        "language": {
            "sProcessing":     "Procesando...",
            "sLengthMenu":     "Mostrar _MENU_ registros",
            "sZeroRecords":    "Ning\u00FAn dato disponible en esta tabla",
            "sEmptyTable":     "Ning\u00FAn dato disponible en esta tabla",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst":    "Primero",
                "sLast":     "Último",
                "sNext":     "Siguiente",
                "sPrevious": "Anterior"
            },
            "oAria": {
                "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            }
        },
        "aoColumns": [
						{ "mDataProp": "codigoArchivo", "bSortable": false, "sWidth":"20%" },
						{ "mDataProp": "nombreArchivo","bSortable": false, "sWidth":"60%" },
						{ "mDataProp": "descargar","bSortable": false, "sWidth":"20%" },
						
					],
		"fnServerData": function ( sSource, aoData, fnCallback ) {
            $.ajax( {
                type: 'POST',
                url: contexto + "/fileUpload/mostrarArchivos?" + data_decode,
                data: aoData,
                success: fnCallback,
                error : function (e) {
                    alert (e);
                }
            } );
        }, 
        "fnDrawCallback": function(oSettings) {
        	
       	 oTable.find("tr").each(function(data) {
    		 var aData = oTable.fnGetData(this); // get datarow
 		 $(this).find("td").on("click", function() {
    			 if (null != aData)  // null if we clicked on title row
    			 {
    				 window.location.href = "fileUpload/downloadArchivo?nombreArchivo=" + aData.nombreArchivo;
    			 }   
   		 });
    	 });
        }
	
		
	});
}


function listarArchivosPrestamo(codigoPrestamo,codigoCaso){
	var data_decode = "_csrf=" + $("#_csrf").val();
	data_decode+="&codigoPrestamo="+codigoPrestamoArchivo+"&caso.codigo="+codigoCasoPrestamoArchivo;
	var tablaHtml="<table id='tablaArchivosPrestamo' class='table table-striped table-hover' >";
	tablaHtml+="	<thead>";
	tablaHtml+="<tr class='tr_titulo'>";
	tablaHtml+="<th >C&oacute;digo Prestamo</th>";
	tablaHtml+="<th >Nombre Archivo</th>";
	tablaHtml+="<th >Descarga</th>";
	tablaHtml+="</tr>";
	tablaHtml+="	</thead>";
	tablaHtml+="	<tbody>";
	tablaHtml+="</tbody>";
	tablaHtml+="</table>";
	$("#listadoArchivosPrestamo").html(tablaHtml);
	oTable =$('#tablaArchivosPrestamo').dataTable({
		"bProcessing": true,
        "bServerSide": true,
        "bPaginate":false, 
        "bInfo":false,
        "bFilter":false,
        "sAjaxSource": contexto + "/fileUpload/mostrarArchivosPrestamo?" + data_decode,
        "aaSorting": [[ 1, "asc" ]],
        "language": {
            "sProcessing":     "Procesando...",
            "sLengthMenu":     "Mostrar _MENU_ registros",
            "sZeroRecords":    "Ning\u00FAn dato disponible en esta tabla",
            "sEmptyTable":     "Ning\u00FAn dato disponible en esta tabla",
            "sLoadingRecords": "Cargando...",
      
            "oAria": {
                "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            }
        },
        "aoColumns": [
						{ "mDataProp": "codigoPrestamo", "bSortable": false, "sWidth":"20%" },
						{ "mDataProp": "archivo","bSortable": false, "sWidth":"60%" },
						{ "mDataProp": "descargar","bSortable": false, "sWidth":"20%" },
						
					],
		"fnServerData": function ( sSource, aoData, fnCallback ) {
            $.ajax( {
                type: 'POST',
                url: contexto + "/fileUpload/mostrarArchivosPrestamo?" + data_decode,
                data: aoData,
                success: fnCallback,
                error : function (e) {
                    alert (e);
                }
            } );
        }, 
        "fnDrawCallback": function(oSettings) {
        	
       	 oTable.find("tr").each(function(data) {
    		 var aData = oTable.fnGetData(this); // get datarow
	 		 $(this).find("td").on("click", function() {
	    			 if (null != aData)  // null if we clicked on title row
	    			 {
	    				 window.location.href = "fileUpload/downloadArchivoPrestamo?nombreArchivo=" + aData.archivo;
	    			 }   
	   		 });
    	 });
        }
	
		
	});
}


function mostrarTablaPrestamo(codigo) {
	var documento =codigo;
	var data_caso = "codigo="+documento;
		data_caso += "&_csrf=" + $("#_csrf").val();
	var renderDecimalNumber = function (oObj) {
		console.log(oObj);
		var num = "";
		try{
//			num = $(oObj).parseNumber({format:"#,###.00", locale:"us"});
			num = $(oObj).number(true, 2 ,',','.');
			console.log(num);
		}catch(e){
			console.log('ee:\t'+e);
		}
		
	    return num;
	};
	var tablaHtml="<table id='tablaListadoPrestamo' class='table table-striped' >";
	tablaHtml+="	<thead>";
	tablaHtml+="<tr>";
	tablaHtml+="<th >Nombre</th>";
	tablaHtml+="<th >Cédula</th>";
	tablaHtml+="<th >Fecha</th>";
	tablaHtml+="<th >Valor</th>";
	tablaHtml+="<th >% Interes</th>";
	tablaHtml+="<th >Información Financiera</th>";
	tablaHtml+="<th >Abonos	</th>";
	tablaHtml+="<th hidden='hidden'>&nbsp;</th>";
	tablaHtml+="<th >Acción</th>";
	tablaHtml+="</tr>";
	tablaHtml+="	</thead>";
	tablaHtml+="	<tbody>";
	tablaHtml+="</tbody>";
	tablaHtml+="</table>";
	$("#listaPrestamos").html(tablaHtml);
	var oTablePrestamo =$('#tablaListadoPrestamo').dataTable({
		"bProcessing": false,
        "bServerSide": true,
        "bFilter":false,
        "bPaginate":false, 
        "bInfo":false,
        "sAjaxSource": contexto+"/detalleCaso/getAllTablePrestamos?" + data_caso,
//        "aaSorting": [[ 1, "desc" ]],
        "language": {
//        	"decimal": ",",
//        	"thousands": ".",
            "sProcessing":     "Procesando...",
            "sLengthMenu":     "Mostrar _MENU_ registros",
            "sZeroRecords":    "Ning\u00FAn dato disponible en esta tabla",
            "sEmptyTable":     "Ning\u00FAn dato disponible en esta tabla",
            "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
            "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix":    "",
            "sSearch":         "Buscar:",
            "sUrl":            "",
            "sInfoThousands":  ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst":    "Primero",
                "sLast":     "Último",
                "sNext":     "Siguiente",
                "sPrevious": "Anterior"
            },
            "oAria": {
                "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            }
        },
        "columnDefs": [
			{
			    "targets": 3,
			    "data": "montoPrestamo",
			    "render": function(data, type, full, meta) {
			    	return $.number(data, 2, ',', '.');
			    }
			},
            {
	             "targets": 4,
	             "data": "porcentajeInteresPrestamo",
	             "render": function(data, type, full, meta) {
	            	 return data + ' %' ;
             }
        }],
        "aoColumns": [
						{ "mDataProp": "nombreDeudorPrestamo", "bSortable": false, "sWidth":"10%" },
						{ "mDataProp": "identificacionDeudorPrestamo","bSortable": false, "sWidth":"10%" },
						{ "mDataProp": "fechaPrestamo", "bSortable": false,  "sWidth":"20%" },
						{"bSortable": false, "sWidth": "20%", "fnRender": function(oObj) {return $.number(oObj.aData[3], 2 ,',','.')} },
						{"bSortable": false, "sWidth": "10%", "fnRender": function(oObj) {return '%' + oObj.aData[4]} },
						{ "mDataProp": "accionDetalle", "bSortable": false,  "sWidth":"10%" },
						{ "mDataProp": "accionDetalleAbono", "bSortable": false,  "sWidth":"10%" },
						{ "mDataProp": "informacionFinancieraDetalle", "bSortable": false, "bVisible": false},
						{ "mDataProp": "acciones", "bSortable": false,  "sWidth":"10%" }

					],
		"fnServerData": function ( sSource, aoData, fnCallback ) {			
			$.ajax( {
				type: 'POST',
				dataType: "json",
                url: contexto+"/detalleCaso/getAllTablePrestamos?" + data_caso,
                data: aoData,
                success: fnCallback,
                error : function (e) {
                    alert (e);
                }
            } );
        },
        "fnDrawCallback": function(oSettings) {
        	
          $.each($("[name = verDetallePrestamo]"), function (index, optionData) {
       		 optionData.onclick = function () {
       			 var tr = optionData.parentNode.parentNode.parentNode;
       			 if (oTablePrestamo.fnIsOpen(tr)) {
       				
       				oTablePrestamo.fnClose(tr);
       		      } else {
       		    	oTablePrestamo.fnOpen(tr, oTablePrestamo.fnGetData(tr).informacionFinancieraDetalle, "info_row" );
       		      }
       		 };
       	 });     		
          $.each($("[name = verAbonosPrestamo]"), function (index, optionData) {
        	  optionData.onclick = function () {
        		  var tr = optionData.parentNode.parentNode.parentNode;
        		  if (oTablePrestamo.fnIsOpen(tr)) {
        			  oTablePrestamo.fnClose(tr);
        		  } else {
        			  oTablePrestamo.fnOpen(tr, loadAbonos(oTablePrestamo.fnGetData(tr).codigoPrestamo), "info_row" );
        		  }
        	  };
          });     		
        }

	});
	
	
	
}

function loadAbonos(prestamo){
	var abonos = "";
	$.ajax( {
		type: 'GET',
		async : false,
		dataType: "json",
        url: contexto+"/detalleCaso/loadListaAbonos?codigoPrestamo="+prestamo,
        success: function(data){
        	abonos = data.abonos;
        },
    } );
	
	
	var strTable = '<table style="width: 100%;" class="table table-bordered" name="tableAbonos">'	+
		    '<tr>'+
		        '<th style="width: 30%;">Fecha Abono:</td>'+
		        '<th style="width: 30%;">Abono Capital</td>'+
		        '<th style="width: 30%;">Abono Interes</td>'+
		        '<th style="width: 10%;">Accion</td>'+
		    '</tr>';
	var abono;
	
	var strAccionEliminar = '<a class="btn btn-circle btn-info remove" onclick="confirmEliminar(this)"><i id="eliminarAbono"  class="glyphicon glyphicon-remove"></i></a>'
	
	for(var i = 0; i < abonos.length;i++){
		abono = abonos[i];
		var strAccionAdjuntar = '<a class="btn btn-circle btn-success" onclick="asociarArchivoAbono('+abono.codigo+')" data-toggle="modal" href="#modalAsociarArchivoAbono"><i class="glyphicon glyphicon-paperclip"></i></a>';
		strTable+= 
		'<tr>'+
			'<td hidden>'+abono.codigo+'</td>'+
			'<td hidden>'+prestamo+'</td>'+
        	'<td>'+abono.fecha+'</td>'+
        	'<td>'+$.number(abono.abonoCapital,0,',','.')+'</td>'+
        	'<td>'+$.number(abono.abonoInteres,0,',','.')+'</td>'+
        	'<td style="text-align:center">'+(((i+1)==abonos.length)?strAccionEliminar:"")
        	+strAccionAdjuntar
        	+'</td>'+
        '</tr>';
		
	}
	strTable+='</table>';	
		    
	
	return strTable;
}

function asociarArchivoAbono(abono){
	$("#dropzoneCodigoAbono").val(abono);
}

function confirmEliminar(dom){
	  BootstrapDialog.show({
          message: 'Esta seguro que desea eliminar el abono ?',
          buttons: [{
              label: 'Cancelar',
              action: function(dialogRef){
                  dialogRef.close();
              }
          },{
              label: 'Aceptar',
              cssClass: 'btn-primary',
              action: function(dialogRef){
                  eliminarAbono(dom);
                  dialogRef.close();
              }
          }]
      });
}

function eliminarAbono(dom){
	var codigoAbono = $(dom).closest("tr").find("td:eq(0)").text();
	var codigoPrestamo = $(dom).closest("tr").find("td:eq(1)").text();
	$.get(contexto + "/detalleCaso/eliminarAbono?codigo="+codigoAbono, function( data ) {
		$(dom).closest("table[name=tableAbonos]").html(loadAbonos(codigoPrestamo));
	});
	
	
}




function asociarArchivoActividades() {
	myDropzone.processQueue();
	crearUrlRedireccionarA("actArchivoActividad");
	$("#modalAsociarArchivo").modal("hide");
	setTimeout(function(){
		$("#messageExitosoCasoOfLine").hide();
		$("#messageExitosoCasoOfLine").hide();
	}, 10000);
}

function asociarArchivoPrestamo() {
	myDropzonePrestamo.processQueue();
	crearUrlRedireccionarA("actConfindencial");
	setTimeout(function(){
		$("#messageExitosoCasoOfLine").hide();
		$("#messageExitosoCasoOfLine").hide();
	}, 10000);
}

$(document).ready(function(){
	var url = window.location.href;
	$('.nav-tabs a:first').tab('show') ;
})

$(document).ready(function() {
    var text_max = 255;
    $('#textarea_feedback').html(text_max + ' caracteres faltantes');

    $('#justificacionActividad').keyup(function() {
        var text_length = $('#justificacionActividad').val().length;
        var text_remaining = text_max - text_length;

        $('#textarea_feedback').html(text_remaining + ' caracteres faltantes');
    });
});




