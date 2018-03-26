function mostrarTabla() {
	if (validarFiltrosFechas()) {
		var data_decode = "_csrf=" + $("#_csrf").val();
		data_decode += "&nombreCasoFiltro=" + $("#nombreCasoFiltro").val();
		data_decode += "&victimaFiltro=" + $("#victimaFiltro").val();
		data_decode += "&radicadoFiltro=" + $("#radicadoFiltro").val();
		data_decode += "&ordenarPor=" + $("#ordenarPor").val();
		if ($("#fechaInicioFiltro").val()) {			
			var fechaInicio = new Date(replaceAll($("#fechaInicioFiltro").val(), "-", "/"));
			data_decode += "&fechaInicioFiltro=" + fechaInicio;
		}
		if ($("#fechaFinFiltro").val()) {			
			var fechaFin = new Date(replaceAll($("#fechaFinFiltro").val(), "-", "/"));
			data_decode += "&fechaFinFiltro=" + fechaFin;
		}
		$.each($("#responsablesFiltro option:selected"), function (index, option) {
			data_decode += "&responsablesFiltro=" + option.value;
		});
		
		var tablaHtml="<table id='datatableCasos' class='table table-striped table-hover' >";
		tablaHtml+="	<thead>";
		tablaHtml+="<tr class='tr_titulo'>";
		tablaHtml+="<th >" + proceso + "</th>";
		tablaHtml+="<th >" + nombreTitular + "</th>";
		tablaHtml+="<th >" + parentesco + "</th>";
		tablaHtml+="<th >" + documento + "</th>";
		tablaHtml+="<th >" + lugarDocumento + "</th>";
		tablaHtml+="<th >" + fechaVencimiento + "</th>";
		tablaHtml+="<th >" + responsables + "</th>";
		tablaHtml+="<th >" + alertas + "</th>";
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
			"sAjaxSource": contexto + "/documentosPendientes/mostrarDocumentosPendientes?" + data_decode,
			"aaSorting": [[ 1, "asc" ]],
			"language": {
				"sProcessing":     "Procesando...",
				"sLengthMenu":     "Mostrar _MENU_ registros",
				"sZeroRecords":    "No se encontraron datos para los par\u00E1metros de b\u00FAsqueda ingresados",
				"sEmptyTable":     "No se encontraron datos para los par\u00E1metros de b\u00FAsqueda ingresados",
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
			              { "mDataProp": "titular","bSortable": false},
			              { "mDataProp": "parentesco","bSortable": false},
			              { "mDataProp": "documento", "bSortable": false},
			              { "mDataProp": "lugar", "bSortable": false},
			              { "mDataProp": "fechaVencimiento", "bSortable": false},
			              { "mDataProp": "reponsables", "bSortable": false},
			              { "mDataProp": "alertas", "bSortable": false},
			              { "mDataProp": "codigoCaso", "bSortable": false, "bVisible": false},
			             ],
			              "fnServerData": function ( sSource, aoData, fnCallback ) {
			            	  $.ajax( {
			            		  type: 'POST',
			            		  url: contexto + "/documentosPendientes/mostrarDocumentosPendientes?" + data_decode,
			            		  data: aoData,
			            		  success: fnCallback,
			            		  error : function (e) {
			            			  console.log(e);
			            		  }
			            	  } );
			              }, 
			              "fnDrawCallback": function(oSettings) {
			            	  
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

function mostrarAlerta(codigoCaso){
	codigoCasoAlertaIr = codigoCaso;
	irA();
//	$('#modalAlertasPre').modal('show');
}

function irA(){
	document.location.href = 'detalleCaso?codigo=' + codigoCasoAlertaIr + "&redirectActividad=true";
}

function limpiarFiltros() {
	$("#tab-1 input, #tab-1 select").val("");
	mostrarTabla();
}

function exportarExcel() {
	if (validarFiltrosFechas()) {
		var data_decode = "_csrf=" + $("#_csrf").val();
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
			url : contexto + "/documentosPendientes/exportarDocumentosPendientes",
			data : data_decode ,
			dataType : "json",
			success : function(response) {
				waitingDialog.hide();
				if (response.documentosPendientes) {
					var thProceso = $("<td></td>").text(proceso);
					var thTitular = $("<td></td>").text(nombreTitular);
					var thParentesco = $("<td></td>").text(parentesco);
					var thDocumento = $("<td></td>").text(documento);
					var thLugar = $("<td></td>").text(lugarDocumento);
					var thFechaVencimiento = $("<td></td>").text(fechaVencimiento);
					var thResponsables = $("<td></td>").text(responsables);
					var thAlertas = $("<td></td>").text(alertas);
					var firstTr = $("<tr></tr>").append(thProceso, thTitular, thParentesco, thDocumento, thLugar, thFechaVencimiento, thResponsables, thAlertas);
					var table = $("<table></table>").prop("id", "tableExcel").addClass("hide").append(firstTr);
					$.each(response.documentosPendientes, function(index, optionData) {
						var tr = $("<tr></tr>");
						tr.append($("<td></td>").text(optionData.proceso));
						tr.append($("<td></td>").text(optionData.titular));
						tr.append($("<td></td>").text(optionData.parentesco));
						tr.append($("<td></td>").text(optionData.documento));
						tr.append($("<td></td>").text(optionData.lugar));
						tr.append($("<td></td>").text(optionData.fechaVencimiento));
						tr.append($("<td></td>").text(optionData.reponsables));
						tr.append($("<td></td>").text(optionData.alertas));
						table.append(tr);
					});
					$("body").append(table);
					fnExcelReport('tableExcel', 5, "Reporte Documentos Pendientes", 3);
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

