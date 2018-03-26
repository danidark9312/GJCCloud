var graficoCasosPorTipoCaso = new graficoCasosPorTipoCaso();


function graficoCasosPorTipoCaso() {
	
	/**
	 *   INICIO METODOS Y VARIABLES PUBLICOS
	 * 
	 */
	
	var mostrarGrafico = function() {
		$.ajax({
			type : "POST",
			url : contexto + "/home/consultarCasosPorTipoCaso",
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
	
	/***
	 * 
	 *   INICIO  METODOS Y VARIABLES PRIVADOS
	 */
	
	
	var casoPorTipoCasoChart = null;
	var datosChart = null;
	var tituloDatosGraficoY = "Tipos de caso";
	
	
			
	var pintarGrafico = function(response) {
		
		var arrayDataTotal = [];
		var arrayEncabezado = [];
		var arrayData = []
		
		arrayEncabezado.push("");
		arrayData.push(tituloDatosGraficoY);
		$.each(response.casosPorTipoCaso, function(index, casoPorTipoCaso) {
			arrayEncabezado.push(casoPorTipoCaso.nombre);
			arrayData.push(casoPorTipoCaso.cantidad);
		});
		arrayDataTotal.push(arrayEncabezado);
		arrayDataTotal.push(arrayData);
		drawChart(arrayDataTotal, "", "", "casosPorTipoCasoChart");
	
	};
	
	var selectHandler = function() {
		var selectedItem = casoPorTipoCasoChart.getSelection()[0];		
	    if (selectedItem) {
	    	var tipoCaso = datosChart.pg[selectedItem.column].label;
	    	document.location.href = contexto + "/visorCaso?tipoCaso=" + encodeURIComponent(tipoCaso);
	    }
	 };
	
	var drawChart = function (arrayData, title, subtitle, chartElement) {
		
		var data = google.visualization.arrayToDataTable(arrayData); 

		var options = {
				chart: {
					title: title,
					subtitle: subtitle,
                },
                bars: 'horizontal' // Required for Material Bar Charts.
            };

        var chart = new google.charts.Bar(document.getElementById(chartElement));
        
        
        google.visualization.events.addListener(chart, 'select', selectHandler);
        google.visualization.events.addListener(chart, 'onmouseover', pointerMouse);

        chart.draw(data, options);
        
        casoPorTipoCasoChart = chart;
        datosChart = data;
        
        function pointerMouse() {
        	$('#casosPorTipoCasoChart').css('cursor','pointer')
        }
        
	}
	
	/***
	 * 
	 *   FIN  METODOS Y VARIABLES PRIVADOS
	 */
	
	return {
		mostrarGrafico : mostrarGrafico 
	};
}