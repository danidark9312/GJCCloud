var contextoPapelera = true;
var tareaEliminar = null;
var actividadEliminar = null;

function mostrarTablaPapelera() {
	var data_caso = "codigoCaso=" + $("#nombreCaso").val();
	data_caso += "&_csrf=" + $("#_csrf").val();

	$.each($("#responsablesFiltro option:selected"), function (index, option) {
		data_caso += "&responsablesFiltro=" + option.value;
	});
	
	
	
	var tablaHtml = "<table id='tablaActividadesInactivas' class='table table-striped' >";
	tablaHtml += "<thead>";
	tablaHtml += "<tr>";
	tablaHtml += "<th >" + tituloCaso + "</th>";
	tablaHtml += "<th >" + tituloActividad + "</th>";
	tablaHtml += "<th >" + tituloVerTarea + "</th>";
	tablaHtml += "<th >" + tituloAccion + "</th>";
	tablaHtml += "<th class='hide'></th>";
	tablaHtml += "</tr>";
	tablaHtml += "</thead>";
	tablaHtml += "<tbody>";
	tablaHtml += "</tbody>";
	tablaHtml += "</table>";
	$("#tablaListadoPapelera").html(tablaHtml);
	oTable = $('#tablaActividadesInactivas').dataTable({
		"bProcessing" : true,
		"bServerSide" : true,
		"bFilter" : false,
		"sAjaxSource" : contexto + "/papelera/getActividadesPapelera?" + data_caso,
		"aaSorting" : [[1, "asc"]],
		"language" : {
			"sProcessing" : "Procesando...",
			"sLengthMenu" : "Mostrar _MENU_ registros",
			"sZeroRecords" : "No se encontraron resultados",
			"sEmptyTable" : "Ningún dato disponible en esta tabla",
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
		"aoColumns" : [{
			"mDataProp" : "nombreCaso",
			"bSortable" : false,
			"sWidth" : "40%"
		}, {
			"mDataProp" : "nombreActividad",
			"bSortable" : false,
			"sWidth" : "40%"
		}, {
			"mDataProp" : "btnVerDetalle",
			"bSortable" : false,
			"sWidth" : "10%"
		}, {
			"mDataProp" : "acciones",
			"bSortable" : false,
			"sWidth" : "10%"
		}, {
			"mDataProp" : "detalle",
			"bSortable" : false,
			"bVisible" : false
		}

		],
		"fnServerData" : function(sSource, aoData, fnCallback){

			$.ajax({
				type : 'POST',
				dataType : "json",
				url : contexto + "/papelera/getActividadesPapelera?" + data_caso,
				data : aoData,
				success : fnCallback,
				error : function(e){
					alert(e);
				}
			});
		},
		"fnDrawCallback" : function(oSettings){
			$.each($("[name = verTareas]"), function(index, optionData){
				optionData.onclick = function(){
					var tr = optionData.parentNode.parentNode.parentNode;
					if(oTable.fnIsOpen(tr)){
						oTable.fnClose(tr);
						optionData.firstChild.removeAttribute('class', 'glyphicon glyphicon-chevron-up');
						optionData.firstChild.setAttribute("class", "glyphicon glyphicon-chevron-down");
					}else{
						oTable.fnOpen(tr, oTable.fnGetData(tr).detalle, "info_row");
						optionData.firstChild.removeAttribute('class', "glyphicon glyphicon-chevron-down");
						optionData.firstChild.setAttribute("class", 'glyphicon glyphicon-chevron-up');
					}
				};
			});
		}
	});
}

function activarActividad(codigo){
	var data_Actividad = "_csrf=" + $("#_csrf").val() + "&codigoActividadParticular=" + codigo;
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		type : "POST",
		url : contexto + "/papelera/activarActividad",
		data : data_Actividad,
		success : function(response){
			waitingDialog.hide();
			if(response.STATUS == "SUCCESS"){
				mostrarTablaPapelera();
				$("#messageExitoso").removeClass("hide");
				$("#messageExitoso").text("Se activ\u00F3 correctamente la actividad.");
				setTimeout(function(){
					$("#messageExitoso").addClass("hide");
					$("#messageExitoso").text("");
				}, 10000);
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function activarTarea(codigo, activarActividad){
	if(activarActividad){
		var data_tarea = "_csrf=" + $("#_csrf").val() + "&codigoTarea=" + codigo + "&activarActividad="
				+ activarActividad;
	}else{
		var data_tarea = "_csrf=" + $("#_csrf").val() + "&codigoTarea=" + codigo;
	}
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		type : "POST",
		url : contexto + "/papelera/activarTarea",
		data : data_tarea,
		success : function(response){
			waitingDialog.hide();
			if(response.STATUS == "SUCCESS"){
				mostrarTablaPapelera();
				$("#messageExitoso").removeClass("hide");
				$("#messageExitoso").text("Se activ\u00F3 correctamente la tarea.");
				setTimeout(function(){
					$("#messageExitoso").addClass("hide");
					$("#messageExitoso").text("");
				}, 10000);
			}else{
				if(response.STATUS == "ERROR_ACTIVIDAD"){
					mostrarModalConfirmacion(codigo);
				}
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function mostrarConfirmacionTarea(codigo){
	tareaEliminar = codigo;
	$("#modalConfBorrarTarea").modal("show");

}

function mostrarConfirmacionActividad(codigo){
	actividadEliminar = codigo;
	$("#modalConfBorrarActividad").modal("show");
}

function borradoFisicoTarea(){
	var data_tarea = "_csrf=" + $("#_csrf").val() + "&codigoTarea=" + tareaEliminar;
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		type : "POST",
		url : contexto + "/papelera/borradoFisicoTarea",
		data : data_tarea,
		success : function(response){
			waitingDialog.hide();
			$("#modalConfBorrarTarea").modal("hide");
			if(response.STATUS == "SUCCESS"){
				mostrarTablaPapelera();
				$("#messageExitoso").removeClass("hide");
				$("#messageExitoso").text("Se elimin\u00F3 correctamente la tarea.");
				setTimeout(function(){
					$("#messageExitoso").addClass("hide");
					$("#messageExitoso").text("");
				}, 10000);
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function borradoFisicoActividad(){
	var data_Actividad = "_csrf=" + $("#_csrf").val() + "&codigoActividadParticular=" + actividadEliminar;
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		type : "POST",
		url : contexto + "/papelera/borradoFisicoActividad",
		data : data_Actividad,
		success : function(response){
			waitingDialog.hide();
			if(response.STATUS == "SUCCESS"){
				$("#modalConfBorrarActividad").modal("hide");
				mostrarTablaPapelera();
				$("#messageExitoso").removeClass("hide");
				$("#messageExitoso").text("Se elimin\u00F3 correctamente la actividad.");
				setTimeout(function(){
					$("#messageExitoso").addClass("hide");
					$("#messageExitoso").text("");
				}, 10000);
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function mostrarModalConfirmacion(codigo){
	$("#modalConfirmacion").modal("show");
	$("#btnContinuar").click({
		param1 : codigo,
		param2 : true
	}, callActivarTarea);
}

function callActivarTarea(event){
	activarTarea(event.data.param1, event.data.param2);
	$("#modalConfirmacion").modal("hide");
}

function limpiarFiltros(){
	$("#nombreCaso").val("");
	$("#responsablesFiltro").val("");
	mostrarTablaPapelera();
}