var TIEMPOLIMPIARERROR = 10000;
var TITULO_TAREA = "Tarea";
var TITULO_FECHA_VENCIMIENTO = "Fecha vencimiento";
var TITULO_DETALLE = "Detalle";
var TITULO_RESPONSABLES = "Responsable";
var TITULO_ALERTAS = "Alertas";
var TITULO_PROCESO = "Proceso";

function cargarResponsables(filtro) {
	waitingDialog.show('');$.ajax({
		  dataType: 'json',
		  url: contexto + '/maestro/obtenerAbogados',
		  data: null,
		  success: function (response) {
			  waitingDialog.hide();
			  var select = document.getElementById(filtro);
			  var option = document.createElement('option');
			  
			  option.value = '';
			  option.text = 'Seleccionar';
			  select.add(option); 
			  $.each(response.abogados, function(index, optionData) {	  
				  var select = document.getElementById(filtro);
				  var option = document.createElement('option');
				  option.value = optionData.codigo;
				  option.text = optionData.nombre;
				  select.add(option);
			  });
		  },
		  error : function(e){
			waitingDialog.hide();
		  }
	});
}

function _getTablaActividades(audiencia, demandado, proceso, radicado){
	console.log('setTablaActividades');
	var tablaHtml = "";

	tablaHtml += "<table id='datatableActividades' class='table table-striped table-hover' >";
	tablaHtml += "	<thead>";
	tablaHtml += "<tr class='tr_titulo'>";
	tablaHtml += "<th >" + proceso + "</th>";
	tablaHtml += "<th >" + radicado + "</th>";
	tablaHtml += "<th >" + demandado + "</th>";
	tablaHtml += "<th >" + audiencia + "</th>";
	tablaHtml += "<th hidden='hidden'></th>";
	tablaHtml += "<th hidden='hidden'></th>";
	tablaHtml += "</tr>";
	tablaHtml += "	</thead>";
	tablaHtml += "	<tbody>";
	tablaHtml += "</tbody>";
	tablaHtml += "</table>";

	return tablaHtml;
}

function _getHtmlActividades(){
	var tablaHtml = "";
	
	tablaHtml += "<table id='datatableActividades' class='table table-striped table-hover' >";
	tablaHtml += "	<thead>";
	tablaHtml += "<tr class='tr_titulo'>";
	tablaHtml += "<th>Código</th>";
	tablaHtml += "<th >Nombre</th>";
	tablaHtml += "<th hidden='hidden'>&nbsp;</th>";
	tablaHtml += "</tr>";
	tablaHtml += "	</thead>";
	tablaHtml += "	<tbody>";
	tablaHtml += "</tbody>";
	tablaHtml += "</table>";
	
	return tablaHtml;
}

function _getDataDedode(data_decode){
	console.log('setData_decode');
	data_decode += "&nombreActividadFiltro=" + $("#nombreActividadFiltro").val();
	data_decode += "&nombreCasoFiltro=" + $("#nombreCasoFiltro").val();
//	data_decode += "&ordenarPor=" + $("#ordenarPor").val();

	$.each($("#responsablesFiltro option:selected"), function(index, option){
		data_decode += "&responsablesFiltro=" + option.value;
	});

	return data_decode;
}

function mostrarTabla(){
	console.log('mostrarTabla');
	var data_decode = "_csrf=" + $("#_csrf").val();
	var tablaHtml = '';

	data_decode = _getDataDedode(data_decode);
	tablaHtml = _getTablaActividades(audiencia, demandado, proceso, radicado);

	$("#tablaActividades").html(tablaHtml);
	oTable = $('#datatableActividades').dataTable({
		"bProcessing" : true,
		"bServerSide" : true,
		"bFilter" : false,
		"sAjaxSource" : contexto + "/home/mostrarActividades?" + data_decode,
		"aaSorting" : [[1, "asc"]],
		"language" : {
			"sProcessing" : "Procesando...",
			"sLengthMenu" : "Mostrar _MENU_ registros",
			"sZeroRecords" : "No se encontraron Audiencias para los par\u00E1metros de b\u00FAsqueda ingresados",
			"sEmptyTable" : "No se encontraron Audiencias para los par\u00E1metros de b\u00FAsqueda ingresados",
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
			"mDataProp" : "proceso",
			"bSortable" : false
		}, {
			"mDataProp" : "radicadoPrincipal",
			"bSortable" : false
		}, {
			"mDataProp" : "demandadoPrincipal",
			"bSortable" : false
		}, {
			"mDataProp" : "verDetalle",
			"bSortable" : false
		}, {
			"mDataProp" : "detalle",
			"bSortable" : false,
			"bVisible" : false
		}, {
			"mDataProp" : "codigoCaso",
			"bSortable" : false,
			"bVisible" : false
		}],
		"fnServerData" : function(sSource, aoData, fnCallback){
			waitingDialog.show('');$.ajax({
				type : 'POST',
				url : contexto + "/home/mostrarActividades?" + data_decode,
				data : aoData,
				success : fnCallback,
				error : function(e){
					waitingDialog.hide();
					console.log('Error mostrando actividades.\t' + e);
				}
			});
		},
		"fnDrawCallback" : function(oSettings){
			try{
				waitingDialog.hide();
			}catch(e){
				console.log(e);
			}
			$.each($("[name = verDetalle]"), function(index, optionData){
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

			oTable.on('mouseover', 'tr', function(){
				this.style.cursor = 'pointer';
			});

			oTable.find("tr").each(function(data){
				var aData = oTable.fnGetData(this);
				$(this).find("td").not("td:last").on("click", function(){
					if(null != aData){
						// TODO cambiar ir a detalle cual
						document.location.href = "detalleCaso?codigo=" + aData.codigoCaso;
					}
				});
			});
		}
	});
}

function mostrarTablaActividad(){
	var data_decode = "_csrf=" + $("#_csrf").val() + "&activo=S";
	var tablaHtml = "";
	
	tablaHtml = _getHtmlActividades();
	$("#tablaActividades").html(tablaHtml);
	oTable = $('#datatableActividades').dataTable({
		"bProcessing" : true,
		"bServerSide" : true,
		"bFilter" : true,
		"bInfo" : false,
		"sAjaxSource" : contexto + "/maestroActividad/mostrarActividades?" + data_decode,
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
		"aoColumns" : [ {
			"mDataProp" : "codigoActividad",
			"bSortable" : false,
			"sWidth" : "30%"
		}, {
			"mDataProp" : "nombreActividad",
			"bSortable" : false,
			"sWidth" : "30%"
		}, {
			"mDataProp" : (permisoEliminar == true ? "papelera" : ""),
			"bSortable" : false,
			"sWidth" : "20%"
		}
		//						{ "mDataProp": "codigoActividad","bSortable": false,  "bVisible": false }
		],
		"fnServerData" : function(sSource, aoData, fnCallback){
			waitingDialog.show('');$.ajax({
				type : 'POST',
				url : contexto + "/maestroActividad/mostrarActividades?" + data_decode,
				data : aoData,
				success : fnCallback,
				error : function(e){
					waitingDialog.hide();
				}
			});
		},
		"fnDrawCallback" : function(oSettings){
			$.each($("[name = verDetalle]"), function(index, optionData){
				optionData.onclick = function(){
					var tr = optionData.parentNode.parentNode.parentNode;
					if (oTable.fnIsOpen(tr)){
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
			$.each($("[name = verDetalleEquipoCaso]"), function(index, optionData){
				optionData.onclick = function(){
					var tr = optionData.parentNode.parentNode.parentNode;
					if (oTable.fnIsOpen(tr)){
						oTable.fnClose(tr);
						optionData.firstChild.removeAttribute('class', 'glyphicon glyphicon-chevron-up');
						optionData.firstChild.setAttribute("class", "glyphicon glyphicon-chevron-down");
					}else{
						oTable.fnOpen(tr, oTable.fnGetData(tr).detalleEquipoCaso, "info_row");
						optionData.firstChild.removeAttribute('class', "glyphicon glyphicon-chevron-down");
						optionData.firstChild.setAttribute("class", 'glyphicon glyphicon-chevron-up');
					}
				};
			});
			oTable.on('mouseover', 'tr', function(){
				this.style.cursor = 'pointer';
			});
			oTable.find("tr").each(function(data){
				var aData = oTable.fnGetData(this); // get datarow
				$(this).find("td:lt(2)").on("click", function(){
					if (null != aData){
						consultarActividad(aData.codigoActividad);
					}
				});
			});
		}
	});
}

function limpiarFiltros(){
	console.log('limpiarFiltros');
	// TODO limpiar cuales filtros
	$("#tab-1 input, #tab-1 select").val("");
	mostrarTabla();
}

function exportarExcel(){
	console.log('exportarExcel');
	var data_decode = "_csrf=" + $("#_csrf").val();

	data_decode = _setData_decode(data_decode);

	waitingDialog.show('');$.ajax({
		type : "POST",
		url : contexto + "/audiencia/exportarAudiencias",
		data : data_decode,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
			if(response.audiencias){
				var thProceso = $("<td></td>").text(TITULO_PROCESO);
				var thResponsable = $("<td></td>").text(TITULO_RESPONSABLES);
				var thTarea = $("<td></td>").text(TITULO_TAREA);
				var thDetalle = $("<td></td>").text(TITULO_DETALLE);
				var thVencimiento = $("<td></td>").text(TITULO_FECHA_VENCIMIENTO);
				var thAlertas = $("<td></td>").text(TITULO_ALERTAS);
				var firstTr = $("<tr></tr>").append(thProceso, thResponsable, thTarea, thDetalle, thVencimiento,
						thAlertas);
				var table = $("<table></table>").prop("id", "tableExcel").addClass("hide").append(firstTr);
				$.each(response.audiencias, function(index, optionData){
					var tr = $("<tr></tr>");
					tr.append($("<td></td>").text(optionData.proceso));
					tr.append($("<td></td>").text(optionData.responsable));
					tr.append($("<td></td>").text(optionData.tarea));
					tr.append($("<td></td>").text(optionData.detalle));
					tr.append($("<td></td>").text(optionData.fechaLimite));
					tr.append($("<td></td>").text(optionData.alertas));
					table.append(tr);
				});
				$("body").append(table);
				fnExcelReport('tableExcel', 3, "Reporte Audiencia", 3);
				mostrarTabla();
			}else{
				$("#messageError").removeClass("hide").text(ERROR_EXPORTAR);
				setTimeout(function(){
					$("#messageError").addClass("hide").text("");
				}, TIEMPOLIMPIARERROR);
			}
		},
		error : function(error){
			waitingDialog.hide();
			$("#messageError").removeClass("hide").text(ERROR_EXPORTAR);
			setTimeout(function(){
				$("#messageError").addClass("hide").text("");
			}, TIEMPOLIMPIARERROR);
		}
	});
}