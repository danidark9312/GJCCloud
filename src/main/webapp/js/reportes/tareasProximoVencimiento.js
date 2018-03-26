var tareasProximoVencimiento = new tareasProximoVencimiento();


function tareasProximoVencimiento() {
	
	var mostrarTareas = function () {
		var data = "_csrf=" + $("#_csrf").val();
		if ($("#filtroDias").val())
			data += "&filtroDias=" + $("#filtroDias").val();
		if ($("#nombreCasoFiltro").val())
			data += "&nombreCaso=" + $("#nombreCasoFiltro").val();
		if ($("#responsableCasoFiltro").val())
			data += "&responsablesFiltro=" + $("#responsableCasoFiltro").val();
		if ($("#tareasProximoVencimientoFechaInicioFiltro").val()) {			
			var fechaInicio = new Date(replaceAll($("#tareasProximoVencimientoFechaInicioFiltro").val(), "-", "/"));
			data += "&fechaInicioFiltro=" + fechaInicio;
		}
		if ($("#fechaFinFiltro").val()) {			
			var fechaFin = new Date(replaceAll($("#fechaFinFiltro").val(), "-", "/"));
			data += "&fechaFinFiltro=" + fechaFin;
		}
		
		var tablaHtml = "<table id='datatableTareas' class='table table-striped table-hover'>";
		tablaHtml += "	<thead>";
		tablaHtml += "<tr class='tr_titulo'>";
		tablaHtml += "<th >" + tarea + "</th>";
		tablaHtml += "<th >" + responsables + "</th>";
		tablaHtml += "<th >" + fechaVencimiento + "</th>";
		tablaHtml += "<th >" + codigoCaso + "</th>";
		tablaHtml += "<th >" + nombreCaso + "</th>";
		tablaHtml += "</tr>";
		tablaHtml += "	</thead>";
		tablaHtml += "	<tbody>";
		tablaHtml += "</tbody>";
		tablaHtml += "</table>";		
		$("#tablaTareasProximoVencimiento").html(tablaHtml);
		oTable = $('#datatableTareas').dataTable({
			"responsive": true,
			"iDisplayLength": 25,
			"bProcessing" : true,
			"bServerSide" : true,
			"bFilter" : false,
			"bInfo" : true,
			"sAjaxSource" : contexto + "/tareasProximoVencimiento/mostrarTareasProximoVencimiento?" + data,
			"aaSorting" : [[1, "asc"]],
			"language" : {
				"sProcessing" : "Procesando...",
				"sLengthMenu" : "Mostrar _MENU_ registros",
				"sZeroRecords" : "No se encontraron datos para los par\u00E1metros de b\u00FAsqueda ingresados",
				"sEmptyTable" : "No se encontraron datos para los par\u00E1metros de b\u00FAsqueda ingresados",
				"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
				"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
				"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
				"sInfoPostFix" : "",
//				"sSearch" : "Buscar: ",
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
				"mDataProp" : "tarea",
				"bSortable" : false
			}, {
				"mDataProp" : "responsables",
				"bSortable" : false
			}, {
				"mDataProp" : "fechaVencimiento",
				"bSortable" : false
			}, {
				"mDataProp" : "codigoCaso",
				"bSortable" : false
			}, {
				"mDataProp" : "nombreCaso",
				"bSortable" : false
			}

			],
			"fnServerData" : function(sSource, aoData, fnCallback) {
				$.ajax({
					type : 'POST',
					url : contexto + "/tareasProximoVencimiento/mostrarTareasProximoVencimiento?" + data,
					data : aoData,
					success : fnCallback,
					error : function(e){
						console.log(e);
					}
				});
			},
			"fnDrawCallback" : function(oSettings) {

				oTable.on('mouseover', 'tr', function() {
					this.style.cursor = 'pointer';
				});

				oTable.find("tr").each(function(data) {
					var aData = oTable.fnGetData(this); // get datarow
					$(this).find("td").on("click", function(){
						if(null != aData) // null if we clicked on title row
						{								
							document.location.href="detalleCaso?codigo=" + aData.codigoCaso + "&redirectActividad=true";
						}
					});
				});
			}
		});
	};
	
	var limpiarFiltros = function (){		
		$("#nombreCasoFiltro").val("");
		$("#responsableCasoFiltro").val("");
		$("#tareasProximoVencimientoFechaInicioFiltro").val();
		$("#tareasProximoVencimientoFechaFinFiltro").val();
		mostrarTareas();
	};
	
	return {
		mostrarTabla : mostrarTareas,
		limpiarFiltros : limpiarFiltros
	};		
	
	
}
