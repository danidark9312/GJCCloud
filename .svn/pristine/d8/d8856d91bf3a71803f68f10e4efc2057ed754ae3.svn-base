var graficoCasosPorEstado = new graficoCasosPorEstado();


function graficoCasosPorEstado() {
	
	
	/**
	 *   INICIO METODOS Y VARIABLES PUBLICOS
	 * 
	 */
	
	var mostrarGrafico = function() {
		$.ajax({
			type : "POST",
			url : contexto + "/home/consultarCasosPorEstadoCaso",
			data : "_csrf=" + $("#_csrf").val(),
			dataType : "json",
			success : function(response) {
				pintarGrafico(response);
			},
			error : function(rs, e) {
				alert("error" + e);
				alert("error1" + rs.responseText);
				$("#alertaErrorContrato").show('slow');
			}
		});
	}
	
	
	/**
	 *  FIN METODOS Y VARIABLES PUBLICOS
	 * 
	 */
	
	
	var casoPorEstado = null;
	var datosChart = null;
	var tituloDatosGraficoY = "Actividades";
	
	
			
	var pintarGrafico = function(response) {
		
		var arrayDataTotal = [];

		
		arrayDataTotal.push(["Estado", "Cantidad"]);
		$.each(response.casosPorEstadoCaso, function(index, estadosPorCaso) {
			arrayDataTotal.push([estadosPorCaso.nombre, estadosPorCaso.cantidad]);
		});
		
		drawChart(arrayDataTotal, "Porcentaje de casos por estado", "", "casosPorestadoChart");
	
	};
	
	
	var drawChart = function (arrayData, title, subtitle, chartElement) {
		
		var data = google.visualization.arrayToDataTable(arrayData);
		
		var options = {
          title: title
        };

		var chart = new google.visualization.PieChart(document.getElementById(chartElement));
        
        google.visualization.events.addListener(chart, 'select', selectHandler);
        google.visualization.events.addListener(chart, 'onmouseover', pointerMouse);

        chart.draw(data, options);
        
        casoPorEstado = chart;
        datosChart = data;
        
        function pointerMouse() {
        	$('#casosPorestadoChart').css('cursor','pointer')
        }
	}
	
	var selectHandler = function() {
		var selectedItem = casoPorEstado.getSelection()[0];
	    if (selectedItem) {
	    	var estado = datosChart.kc[selectedItem.row][0].Cf;
	    	document.location.href = contexto + "/visorCaso?estado=" + encodeURIComponent(estado);
	    }
	 };
	
	
	
	return {
		mostrarGrafico : mostrarGrafico
	}
}