var TITULO_TAREA = "Audiencia";
var TITULO_FECHA_VENCIMIENTO = "Fecha vencimiento";
var TITULO_DETALLE = "Detalle";
var TITULO_RESPONSABLES = "Responsable";
var TITULO_ALERTAS = "Alertas";
var TITULO_PROCESO = "Proceso";
var TITULO_RADICADO = "Radicado";

function mostrarTabla() {
	if (validarFiltrosFechas()) {
		var data_decode = "_csrf=" + $("#_csrf").val();
		data_decode += "&estadoTareaFiltro=" + $("#estadoTareaFiltro").val();
		data_decode += "&nombreCasoFiltro=" + $("#nombreCasoFiltro").val();
		data_decode += "&victimaFiltro=" + $("#victimaFiltro").val();
		data_decode += "&ordenarPor=" + $("#ordenarPor").val();
		data_decode += "&radicadoFiltro=" + $("#radicadoFiltro").val();
		if ($("#fechaInicioFiltro").val()) {			
			var fechaInicio = new Date(replaceAll($("#fechaInicioFiltro").val(), "-", "/"));
			data_decode += "&fechaInicioFiltro=" + fechaInicio;
		}
		if ($("#fechaFinFiltro").val()) {			
			var fechaFin= new Date(replaceAll($("#fechaFinFiltro").val(), "-", "/"));
			data_decode += "&fechaFinFiltro=" + fechaFin;
		}
		$.each($("#responsablesFiltro option:selected"), function (index, option) {
			data_decode += "&responsablesFiltro=" + option.value;
		});
		
		var tablaHtml="<table id='datatableCasos' class='table table-striped table-hover' >";
		tablaHtml+="	<thead>";
		tablaHtml+="<tr class='tr_titulo'>";
		tablaHtml+="<th >" + proceso + "</th>";
		tablaHtml+="<th >" + radicado + "</th>";
		tablaHtml+="<th >" + demandado + "</th>";
		tablaHtml+="<th >" + audiencia + "</th>";
		tablaHtml+="<th hidden='hidden'></th>";
		tablaHtml+="<th hidden='hidden'></th>";
		tablaHtml+="</tr>";
		tablaHtml+="	</thead>";
		tablaHtml+="	<tbody>";
		tablaHtml+="</tbody>";
		tablaHtml+="</table>";
		$("#tablaAudiencia").html(tablaHtml);
		oTable =$('#datatableCasos').dataTable({
			"bProcessing": true,
			"bServerSide": true,
			"bFilter":false,
			"sAjaxSource": contexto + "/audiencia/mostrarAudiencias?" + data_decode,
			"aaSorting": [[ 1, "asc" ]],
			"language": {
				"sProcessing":     "Procesando...",
				"sLengthMenu":     "Mostrar _MENU_ registros",
				"sZeroRecords":    "No se encontraron Audiencias para los par\u00E1metros de b\u00FAsqueda ingresados",
				"sEmptyTable":     "No se encontraron Audiencias para los par\u00E1metros de b\u00FAsqueda ingresados",
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
			"aoColumns": [
			              { "mDataProp": "proceso", "bSortable": false},
			              { "mDataProp": "radicadoPrincipal","bSortable": false},
			              { "mDataProp": "demandadoPrincipal","bSortable": false},
			              { "mDataProp": "verDetalle","bSortable": false},
			              { "mDataProp": "detalle", "bSortable": false, "bVisible": false},
			              { "mDataProp": "codigoCaso", "bSortable": false, "bVisible": false}
			              ],
			              "fnServerData": function ( sSource, aoData, fnCallback ) {
			            	  $.ajax( {
			            		  type: 'POST',
			            		  url: contexto + "/audiencia/mostrarAudiencias?" + data_decode,
			            		  data: aoData,
			            		  success: fnCallback,
			            		  error : function (e) {
			            			  console.log(e);
			            		  }
			            	  } );
			              }, 
			              "fnDrawCallback": function(oSettings) {
			            	  
			            	  $.each($("[name = verDetalle]"), function (index, optionData) {
			            		  optionData.onclick = function () {
			            			  var tr = optionData.parentNode.parentNode.parentNode;
			            			  if (oTable.fnIsOpen(tr)) {
			            				  oTable.fnClose(tr);
			            				  optionData.firstChild.removeAttribute('class', 'glyphicon glyphicon-chevron-up');
			            				  optionData.firstChild.setAttribute("class", "glyphicon glyphicon-chevron-down");
			            			  } else {
			            				  oTable.fnOpen(tr, oTable.fnGetData(tr).detalle, "info_row" );
			            				  optionData.firstChild.removeAttribute('class', "glyphicon glyphicon-chevron-down");
			            				  optionData.firstChild.setAttribute("class", 'glyphicon glyphicon-chevron-up');
			            			  }
			            		  };
			            	  });
			            	  
			            	  oTable.on('mouseover', 'tr', function () {
			            		  this.style.cursor = 'pointer';
			            	  });
			            	  
			            	  oTable.find("tr").each(function(data) {
			            		  var aData = oTable.fnGetData(this); // get datarow
			            		  $(this).find("td").not("td:last").on("click", function() {
			            			  if (null != aData)  // null if we clicked on title row
			            			  {
			            				  mostrarAlerta(aData.codigoCaso);
			            			  }   
			            		  });
			            	  });
			              }
		});
	} else {
		$("#messageError").removeClass("hide").text(ERROR_RANGO_FECHA);
		setTimeout(function() {
			$("#messageError").addClass("hide").text("");
		}, 10000);
	}
}

function limpiarFiltros() {
	$("#tab-1 input, #tab-1 select").val("");
	mostrarTabla();
}

function mostrarAlerta(codigoCaso){
	codigoCasoAlertaIr = codigoCaso;
	irA();
//	$('#modalAlertasPre').modal('show');
}

function mostrarAlertaV2(fila){
	console.log('mostrarAlertaV2');
	try{
		console.log(fila);
	}catch(e){
		console.log(e);
	}
}

function irA(){
	document.location.href = 'detalleCaso?codigo=' + codigoCasoAlertaIr + "&redirectActividad=true";
}

function exportarExcel() {
	if (validarFiltrosFechas()) {
		var data_decode = "_csrf=" + $("#_csrf").val();
		data_decode += "&estadoTareaFiltro=" + $("#estadoTareaFiltro").val();
		data_decode += "&nombreCasoFiltro=" + $("#nombreCasoFiltro").val();
		data_decode += "&victimaFiltro=" + $("#victimaFiltro").val();
		data_decode += "&radicadoFiltro=" + $("#radicadoFiltro").val();
		data_decode += "&ordenarPor=" + $("#ordenarPor").val();
		if ($("#fechaInicioFiltro").val()) {
			var fechaInicio = new Date(replaceAll($("#fechaInicioFiltro").val(), "-", "/"));
			data_decode += "&fechaInicioFiltro=" + fechaInicio;
		}
		if ($("#fechaFinFiltro").val()) {
			var fechaFin= new Date(replaceAll($("#fechaFinFiltro").val(), "-", "/"));
			data_decode += "&fechaFinFiltro=" + fechaFin;
		}
		$.each($("#responsablesFiltro option:selected"), function (index, option) {
			data_decode += "&responsablesFiltro=" + option.value;
		});
		
		waitingDialog.show('');$.ajax({
			type : "POST",
			url : contexto + "/audiencia/exportarAudiencias",
			data : data_decode ,
			dataType : "json",
			success : function(response) {
				waitingDialog.hide();
				if (response.audiencias) {					
					var thProceso = $("<td></td>").text(TITULO_PROCESO);
					var thRadicado = $("<td></td>").text(TITULO_RADICADO);
					var thResponsable = $("<td></td>").text(TITULO_RESPONSABLES);
					var thTarea = $("<td></td>").text(TITULO_TAREA);
					var thDetalle = $("<td></td>").text(TITULO_DETALLE);
					var thVencimiento = $("<td></td>").text(TITULO_FECHA_VENCIMIENTO);
					var thAlertas = $("<td></td>").text(TITULO_ALERTAS);
					var firstTr = $("<tr></tr>").append(thProceso, thRadicado, thResponsable, thTarea, thDetalle, thVencimiento, thAlertas);
					var table = $("<table></table>").prop("id", "tableExcel").addClass("hide").append(firstTr);
					$.each(response.audiencias, function(index, optionData) {
						var tr = $("<tr></tr>");
						tr.append($("<td></td>").text(optionData.proceso));
						tr.append($("<td></td>").text(optionData.radicado));
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
				} else {
					$("#messageError").removeClass("hide").text(ERROR_EXPORTAR);
					setTimeout(function() {
						$("#messageError").addClass("hide").text("");
					}, 10000);
				}
				
			},
			error : function (error) {
				waitingDialog.hide();
				$("#messageError").removeClass("hide").text(ERROR_EXPORTAR);
				setTimeout(function() {
					$("#messageError").addClass("hide").text("");
				}, 10000);
			}
		});
	}
}