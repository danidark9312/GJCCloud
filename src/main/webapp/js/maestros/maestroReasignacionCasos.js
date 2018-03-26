var contadorClonador = 0;
var codigoTipoCaso = 0;
var codigoTarea = 0;
var casoActividad = false;
var eventoBoton = "";
var idEliminar = "";
var nuevo = true;
var oTable;

function mostrarTablaCasosAbogado(){
	var data_decode = "_csrf=" + $("#_csrf").val();
	data_decode = data_decode +  "&activo="  + $("#cmbFiltroEstado").val();
	var tablaHtml = "<table id='datatableCasos' class='table table-striped table-hover' >";
	var permisoEliminar = true;

	tablaHtml += "	<thead>";
	tablaHtml += "<tr class='tr_titulo'>";
	tablaHtml += "<th rowspan='2' style='vertical-align: middle;' >Código</th>";
	tablaHtml += "<th rowspan='2' style='vertical-align: middle;'>Nombre</th>";
	tablaHtml += "<th rowspan='2' style='vertical-align: middle;'>Estado</th>";
	tablaHtml += "<th >Reasignar</th>";
	tablaHtml += "</tr>";
	tablaHtml += "<tr class='tr_titulo'>";
	tablaHtml += "<th><a href='javascript:selectAllCheck()'><i class=\"glyphicon glyphicon-ok-circle\" style=\"font-size: 20px;\">" +
			"</i></a>/<a href='javascript:deSelectAllCheck()'><i class=\"glyphicon glyphicon-remove-circle\" style=\"font-size: 20px;\"></a></th>";
	tablaHtml += "</tr>";
	tablaHtml += "	</thead>";
	tablaHtml += "	<tbody>";
	tablaHtml += "</tbody>";
	tablaHtml += "</table>";
	$("#tablaListadoTiposCasos").html(tablaHtml);
	oTable = $('#datatableCasos').dataTable({
		"bProcessing": true,
	    "sAjaxSource": contexto + "/maestroReasignacionCasos/consultarCasosAbogado?id=" + $("#cmbFiltroAbogado").val(),
		"bInfo" : false,
		"bSearch" : false,
		"bFilter" : false,
		"bSort" : false,
		"bPaginate": false,
		"language" : {
			"sProcessing" : "Procesando...",
			"sLengthMenu" : "Mostrar _MENU_ registros",
			"sZeroRecords" : "No se encontraron resultados",
			"sEmptyTable" : "No se encontraron resultados",
			"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
			"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
			"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
			"sInfoPostFix" : "",
			"sSearch" : "Buscar:",
			"sUrl" : "",
			"sInfoThousands" : ",",
			"sLoadingRecords" : "Cargando...",
			"oPaginate" : {
				"sFirst" : "Primero",
				"sLast" : "Último",
				"sNext" : "Siguiente",
				"sPrevious" : "Anterior"
			},
			"oAria" : {
				"sSortAscending" : ": Activar para ordenar la columna de manera ascendente",
				"sSortDescending" : ": Activar para ordenar la columna de manera descendente"
			}
		},
		"aoColumns": [
            { "mData": "codigo",sWidth : "10%" },
            { "mData": "nombre",sWidth : "40%" },
            { "mData": "estado",sWidth : "40%" },
            { "mRender": function ( data, type, full ) {
                return '<input type="checkbox" name="chkReasignar" > ';
              },
              "mData" : null, sWidth : "10%"
            }
        ],
	});
}

function selectAllCheck(){
	$("#datatableCasos tbody tr td input[type=checkbox]").prop("checked",true);
}
function deSelectAllCheck(){
	$("#datatableCasos tbody tr td input[type=checkbox]").prop("checked",false);
}





function limpiarCampos(){
	$("#detalleTarea").val("");
	$("#nombreTarea").val("");
	$("#nombreActividad").val("");
	$("#codigoActividad").val("");
	$("#codigoTarea").val("");
	$("#codigoTareaActividad").val("");
}


function migrarCasosAbogado(){
	$("#messageExitoso").hide();
	$("#messageError").hide();
	if(validarAbogadoDestino() && validarCasosSeleccionado()){
		var param = "idAbogado="+$("#cmbAbogadoDestino").val();
		var checks = $("#datatableCasos input[name=chkReasignar]:checked");
		
		checks.each(function(index,data){
			var data = oTable.fnGetData($(data).closest("tr"));
			var casoEquipoCasoPK = data.casoEquipoCasoPK;
			param+="&casoEquiposCasoPK["+index+"].codigo="+casoEquipoCasoPK.codigo;	
			param+="&casoEquiposCasoPK["+index+"].codigoEquipoCaso="+casoEquipoCasoPK.codigoEquipoCaso;	
			param+="&casoEquiposCasoPK["+index+"].miembro="+casoEquipoCasoPK.miembro;	
		});
		
		waitingDialog.show('');$.ajax({
			  url: contexto + "/caso/asignarCasosNuevoResponsable",
			  method : 'POST',
			  dataType : 'json',
			  data: param,
			  success: function (response) {
				  waitingDialog.hide();
				  $("#messageExitoso").show().html(
						  "Se modificaron exitosamente "+response.casosModificados+" casos<br/>" +
						  (response.casosError==0?"Ningun Caso produjó errores":response.casosError+" casos con errores"));
				  mostrarTablaCasosAbogado();
			  },
			  error: function(e){
				  waitingDialog.hide();
				}
		});
		
		
	}
}

function validarAbogadoDestino(){
	var isExito = false;
	if(!$("#cmbAbogadoDestino").val() || !$("#cmbFiltroAbogado").val()){
		$("#messageError a").text("El campo abogado y abogado destino es necesario");
		$("#messageError").show();
		isExito = false;
	}else{
		$("#messageError a").text("");
		$("#messageError").hide();
		isExito = true;
	}
	return isExito;
}

function validarCasosSeleccionado(){
	var isExito = false;
	if($("#datatableCasos input[name=chkReasignar]:checked").length==0){
		$("#messageError a").text("El campo abogado destino es necesario");
		$("#messageError").show();
		isExito = false;
	}else{
		$("#messageError a").text("");
		$("#messageError").hide();
		isExito = true;
	}
	return isExito;
}

function validateComboAbogadoDestino(){
	$("#cmbAbogadoDestino").val("");	
	$("#cmbAbogadoDestino option:hidden").show();
	$("#cmbAbogadoDestino option[value="+$("#cmbFiltroAbogado").val()+"]").hide();
}
