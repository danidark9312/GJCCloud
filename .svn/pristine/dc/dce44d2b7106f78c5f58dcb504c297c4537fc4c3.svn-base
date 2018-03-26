var graficoEstadosTareas = new graficoEstadosTareas();


function graficoEstadosTareas() {
	
	
	
	
	/**
	 *   INICIO METODOS Y VARIABLES PUBLICOS
	 * 
	 */
	
	var cargarResponsablesPorCaso = function(select) {
		var codigoCaso = $(select).val();
		if (codigoCaso) {
			$.ajax({
				type : "POST",
				url : contexto + "/maestro/getAbogadosCaso",
				data : "_csrf=" + $("#_csrf").val() + "&codigoCaso=" + codigoCaso,
				dataType : "json",
				success : function(response) {
					var select = $("#selectResponsable");
					select.find("option").remove();
					var option = $("<option></option>");
					option.val("");
					option.text("Seleccionar");
					select.append(option);
					$.each(response.data, function(index, optionData) {
						var option = $("<option></option>");
						option.val(optionData.codigo);
						option.text(optionData.nombre);
						select.append(option);
					});
				},
				error : function(rs, e) {
					alert("error" + e);
					alert("error1" + rs.responseText);
					$("#alertaErrorContrato").show('slow');
				}
			});
		} else {
			var select = $("#selectResponsable");
			select.find("option").remove();
		}
	};
	
	
	var cargarSelectCasos = function(idSelect) {
		$.ajax({
			type : "POST",
			url : contexto + "/caso/consultarNombresCasos",
			data : "_csrf=" + $("#_csrf").val(),
			dataType : "json",
			success : function(response) {
				var select = $("#" + idSelect);
				select.find("option").remove();
				var option = $("<option></option>");
				option.val("");
				option.text("Seleccionar");
				select.append(option);
				$.each(response.casos, function(index, optionData) {
					var option = $("<option></option>");
					option.val(optionData.codigo);
					option.text(optionData.nombre);
					select.append(option);
				});
			},
			error : function(rs, e) {
				alert("error" + e);
				alert("error1" + rs.responseText);
				$("#alertaErrorContrato").show('slow');
			}
		});
	};
	
	var mostrarGrafico = function() {
		$.ajax({
			type : "POST",
			url : contexto + "/home/consultarActividadesPorEstado",
			data : "_csrf=" + $("#_csrf").val(),
			dataType : "json",
			success : function(response) {
				pintarGrafico(response, "actividadesPorestadoChart");
			},
			error : function(rs, e) {
				alert("error" + e);
				alert("error1" + rs.responseText);
				$("#alertaErrorContrato").show('slow');
			}
		});
	};
	
	var mostrarGraficoPorCaso = function() {
		if ($("#selectCaso").val()) {
			var data = "_csrf=" + $("#_csrf").val();
			data += "&codigoCaso=" + $("#selectCaso").val();
			data += "&responsablesFiltro=" + $("#selectResponsable").val();
			$.ajax({
				type : "POST",
				url : contexto + "/home/consultarPorcentajesTareasPorCasoYEstado",
				data : data,
				dataType : "json",
				success : function(response) {
					pintarGrafico(response, "actividadesPorCasoEstadoChart");
				},
				error : function(rs, e) {
					alert("error" + e);
					alert("error1" + rs.responseText);
					$("#alertaErrorContrato").show('slow');
				}
			});
		} else {
			$("#errorCodigoCaso").removeClass("hide");
			$("#errorCodigoCaso").text("Error, el campo nombre del caso es obligatorio para realizar la busqueda.");
			setTimeout(function() {
				$("#errorCodigoCaso").addClass("hide");
				$("#errorCodigoCaso").text("");
			}, 10000);
		}
	};
	
	var cargarTablaResponsables = function() {
		if ($("#selectCaso").val()) {			
			var data = "_csrf=" + $("#_csrf").val();
			data += "&codigoCaso=" + $("#selectCaso").val();
			data += "&responsablesFiltro=" + $("#selectResponsable").val();
			$.ajax({
				type : "POST",
				url : contexto + "/home/consultarResponsablesPorCaso",
				data : data,
				dataType : "json",
				success : function(response) {
					$("#tablaResponsablesCaso").html("");
					$("#tablaResponsablesCaso").append(crearTablaResponsables(response));
				},
				error : function(rs, e) {
					alert("error" + e);
					alert("error1" + rs.responseText);
					$("#alertaErrorContrato").show('slow');
				}
			});
		} else {
			$("#errorCodigoCaso").removeClass("hide");
			$("#errorCodigoCaso").text("Error, el campo nombre del caso es obligatorio para realizar la busqueda.");
			setTimeout(function() {
				$("#errorCodigoCaso").addClass("hide");
				$("#errorCodigoCaso").text("");
			}, 10000);
		}
	};
	
	var mostrarModalVerMas = function() {
		$("#modal-tareasPorEstado").modal("show");
	};
	
	
	/**
	 *  FIN METODOS Y VARIABLES PUBLICOS
	 * 
	 */
	
	/***
	 * 
	 *   INICIO  METODOS Y VARIABLES PRIVADOS
	 */
	
	
	var actividadesPorEstadoChart = null;
	var datosChart = null;
	var tituloDatosGraficoY = "Tipos de caso";
	
	
			
	
	var crearTablaResponsables = function(response) {
				
		var table = $("<table></table>");
		var tHead = $("<thead></thead>");
	    var firstTr = $("<tr></tr>");
	    var thResponsable = $("<th></th>").text("Responsable");
	    var thTareas = $("<th></th>").text("Tarea");
	    var thVencimiento = $("<th></th>").text("Fecha Vencimiento");
	    var thCumplimiento = $("<th></th>").text("Fecha Cumplimiento");
	    var thEstado = $("<th></th>");
	    firstTr.append(thResponsable,
	    			   thTareas,
	    			   thVencimiento,
	    			   thCumplimiento,
	    			   thEstado);
	    tHead.append(firstTr);
	    table.append(tHead);
	    
	    var tbody = $("<tbody></tbody>");
	    var responsableDuplicado = null;
	    var cantidadRowsResponsable = 0;
	    var tdResponsable = null;
	    var cantidadRegistros = response.responsables.length;
	    $.each(response.responsables, function(index, responsable) {	    	
	    	var semaforo = $("<a></a>").addClass("btn btn-circle btn-xs");
	    	
	    	tr = $("<tr></tr>");
		    	if (responsableDuplicado == null) {    		
		    		responsableDuplicado = responsable.responsable;
		    		tdResponsable = $("<td></td>");
		    		tr.append(tdResponsable.text(responsable.responsable));
		    		cantidadRowsResponsable++;
		    	} else {
		    		if (responsableDuplicado == responsable.responsable) {
		    			cantidadRowsResponsable++;
		    		} else {
		    			tdResponsable.attr("rowspan", cantidadRowsResponsable);
		    			cantidadRowsResponsable = 1;
		    			tdResponsable = $("<td></td>");
		    			responsableDuplicado = responsable.responsable;
		    			tr.append(tdResponsable.text(responsableDuplicado));
		    		}
		    	}
//		    	tr.append($("<td></td>").text(responsable.responsable));
		    	tr.append($("<td></td>").text(responsable.tarea));
		    	tr.append($("<td></td>").text(responsable.fechaLimite));
		    	tr.append($("<td></td>").text(responsable.fechaCumplimiento));
		    	tr.append($("<td></td>").append(semaforo.addClass(responsable.semaforo)));
		    tbody.append(tr);
		    
		    if ((index + 1) == cantidadRegistros)
		    	tdResponsable.attr("rowspan", cantidadRowsResponsable);
		    
	    });
	    
	    
	    table.append(tbody);
		table.addClass("table-home");
		return table;
	};
	
	var pintarGrafico = function(response, divChart) {
		
		var arrayDataTotal = [];

		
		arrayDataTotal.push(["Estado", "Cantidad"]);
		$.each(response.actividadesPorEstado, function(index, actividadPorEstado) {
			arrayDataTotal.push([actividadPorEstado.nombre, actividadPorEstado.cantidad]);
		});
		
		
		drawChart(arrayDataTotal, "", "", divChart);
	
	};
	
	var drawChart = function (arrayData, title, subtitle, chartElement) {
		
		var data = google.visualization.arrayToDataTable(arrayData);
		
		var colorPendientes = '#f2d600';
		var colorFinalizadas = '#61bd4f';
		var colorVencidas = '#eb5a46';
		var colorPorximasVencer = '#FFAB4A';
		
		var colors = [];

		$.each(arrayData, function(index, stringArrayData){
			index = index -1;
			if (stringArrayData[0] == "Pendientes"){
				colors[index] = colorPendientes;
			} else if(stringArrayData[0] == "Finalizadas") {
				colors[index] = colorFinalizadas;
			} else if(stringArrayData[0] == "Vencidas"){
				colors[index] = colorVencidas;
			} else if(stringArrayData[0] == "Vencidas"){
				colors[index] = colorPorximasVencer;
			}
		});

		var options = {
          title: title,
          //Aca se colocan los colores de la grafica
          //colors: ['#f2d600', '#61bd4f', '#eb5a46']
          colors: colors
        };

		var chart = new google.visualization.PieChart(document.getElementById(chartElement));
        
//        google.visualization.events.addListener(chart, 'select', selectHandler);

        chart.draw(data, options);
        
        actividadesPorEstadoChart = chart;
        datosChart = data;
        
	};
	
//	var selectHandler = function() {
//		var selectedItem = actividadesPorEstadoChart.getSelection()[0];
//	    if (selectedItem) {
//	    	var estado = datosChart.kc[selectedItem.row][0].Cf;
//	    	document.location.href = contexto + "/visorCaso?estado=" + encodeURIComponent(estado);
//	    }
//	 };
	
	/***
	 * 
	 *   FIN  METODOS Y VARIABLES PRIVADOS
	 */
	
	return {
		mostrarGrafico : mostrarGrafico,
		mostrarModalVerMas : mostrarModalVerMas,
		cargarSelectCasos : cargarSelectCasos,
		cargarResponsablesPorCaso : cargarResponsablesPorCaso,
		mostrarGraficoPorCaso : mostrarGraficoPorCaso,
		cargarTablaResponsables : cargarTablaResponsables
	};
}

function recargar(){
	location.reload();
}