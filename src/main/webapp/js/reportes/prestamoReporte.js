var TITULO_PRESTAMO = "Total Prestamo";
var TITULO_TOTAL_INTERESES = "Total Intereses";
var TITULO_TOTAL_PAGADO = "Total Pagado";
var TITULO_SALDO_CAPITAL = "Saldo Capital";
var TITULO_SALDO_INTERESES = "Saldo Interes";
var TITULO_SALDO_TOTAL = "Saldo Total";

function mostrarTabla() {
	if (validarFiltrosFechas()) {
		var data_decode = "_csrf=" + $("#_csrf").val();
		
		data_decode += "&nombreCasoFiltro=" + $("#nombreCasoFiltro").val();
		data_decode += "&identificadorDeudor=" + $("#identificadorDeudor").val();
		data_decode += "&nombreDeudor=" + $("#nombreDeudor").val();
		
		if ($("#fechaInicioFiltro").val()) {			
			var fechaInicio = new Date(replaceAll($("#fechaInicioFiltro").val(), "-", "/"));
			data_decode += "&fechaInicioFiltro=" + fechaInicio;
		}
		if ($("#fechaFinFiltro").val()) {			
			var fechaFin= new Date(replaceAll($("#fechaFinFiltro").val(), "-", "/"));
			data_decode += "&fechaFinFiltro=" + fechaFin;
		}
		
		
		var tablaHtml="<table id='datatableCasos' class='table table-striped table-hover' >";
		tablaHtml+="	<thead>";
		tablaHtml+="<tr class='tr_titulo'>";
		tablaHtml+="<th >Total Prestamo</th>";
		tablaHtml+="<th >Total Intereses</th>";
		tablaHtml+="<th >Total Pagado</th>";
		tablaHtml+="<th >Saldo Capital</th>";
		tablaHtml+="<th >Saldo Interes</th>";
		tablaHtml+="<th >Saldo Total</th>";
		tablaHtml+="</tr>";
		tablaHtml+="	</thead>";
		tablaHtml+="	<tbody>";
		tablaHtml+="</tbody>";
		tablaHtml+="</table>";
		$("#tablaAudiencia").html(tablaHtml);
		oTable =$('#datatableCasos').dataTable({
			"bProcessing": true,
			"bServerSide": true,
			"bPaginate": false,
		    "bLengthChange": false,
			"bFilter":false,
			"sAjaxSource": contexto + "/prestamoReporte/mostrarAudiencias?" + data_decode,
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
			              { "mDataProp": "totalPrestamo", "bSortable": false},
			              { "mDataProp": "totalIntereses","bSortable": false},
			              { "mDataProp": "totalPagado","bSortable": false},
			              { "mDataProp": "saldoCapital","bSortable": false},
			              { "mDataProp": "saldoInteres","bSortable": false},
			              { "mDataProp": "saldoTotal","bSortable": false}
			              
			              ],
			              "fnServerData": function ( sSource, aoData, fnCallback ) {
			            	  $.ajax( {
			            		  type: 'POST',
			            		  url: contexto + "/prestamoReporte/mostrarPrestamos?" + data_decode,
			            		  data: aoData,
			            		  success: function(response){
			            			  document.currentTableData = response.aaData;
			            			  fnCallback(response);
			            		  },
			            		  error : function (e) {
			            			  console.log(e);
			            		  }
			            	  } );
			              }, 
			              
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
	
	var data = document.currentTableData;
	
	console.log(data);
	
	var thTotalPrestamo = $("<td></td>").text(TITULO_PRESTAMO);
	var thTotalIntereses = $("<td></td>").text(TITULO_TOTAL_INTERESES);
	var thTotalPagado = $("<td></td>").text(TITULO_TOTAL_PAGADO);
	var thSaldoCapital = $("<td></td>").text(TITULO_SALDO_CAPITAL);
	var thSaldoInteres = $("<td></td>").text(TITULO_SALDO_INTERESES);
	var thSaldoTotal = $("<td></td>").text(TITULO_SALDO_TOTAL);
	
	var firstTr = $("<tr></tr>").append(thTotalPrestamo, thTotalIntereses, thTotalPagado, thSaldoCapital, thSaldoInteres, thSaldoTotal);
	
	var table = $("<table></table>").prop("id", "tableExcel").addClass("hide").append(firstTr);
	$.each(data, function(index, optionData) {
		var tr = $("<tr></tr>");
		tr.append($("<td></td>").text($.number(optionData.totalPrestamo,0,'',',')));
		tr.append($("<td></td>").text($.number(optionData.totalIntereses,0,'',',')));
		tr.append($("<td></td>").text($.number(optionData.totalPagado,0,'',',')));
		tr.append($("<td></td>").text($.number(optionData.saldoCapital,0,'',',')));
		tr.append($("<td></td>").text($.number(optionData.saldoInteres,0,'',',')));
		tr.append($("<td></td>").text($.number(optionData.saldoTotal,0,'',',')));
		table.append(tr);
	});
	$("body").append(table);
	fnExcelReport('tableExcel', 3, "Reporte Prestamo", 3);
	mostrarTabla();

	
}