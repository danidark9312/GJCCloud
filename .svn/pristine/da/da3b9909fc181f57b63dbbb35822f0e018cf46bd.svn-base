var graficoActividadesPrincipales = new graficoActividadesPrincipales();


function graficoActividadesPrincipales() {
	
	
	/**
	 *   INICIO METODOS Y VARIABLES PUBLICOS
	 * 
	 */
	
	var mostrarGrafico = function() {
		$.ajax({
			type : "POST",
			url : contexto + "/home/consultarCasosActividadesPrincipales",
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
	
	
	var actividadPrincipalChart = null;
	var datosChart = null;
	var tituloDatosGraficoY = "Actividades";
	
	
			
	var pintarGrafico = function(response) {
		if(response.casosActividadesPrincipales && response.casosActividadesPrincipales.length >0){
			$("#actividadesPrincipales").html('');
			var arrayDataTotal = [];
			var arrayEncabezado = [];
			var arrayData = []
			
			arrayEncabezado.push("");
			arrayData.push(tituloDatosGraficoY);
			$.each(response.casosActividadesPrincipales, function(index, actividadPrincipal) {
				arrayEncabezado.push(actividadPrincipal.nombre);
				arrayData.push(actividadPrincipal.cantidad);
			});
			arrayDataTotal.push(arrayEncabezado);
			arrayDataTotal.push(arrayData);
			drawChart(arrayDataTotal, "", "", "actividadesPrincipales");
				
		}else{
			$("#actividadesPrincipales").html('<p class="text-center">No hay Datos</p>');
		}
	};
	
	
	var drawChart = function (arrayData, title, subtitle, chartElement) {
		
		
		
		var data = google.visualization.arrayToDataTable(arrayData);
		  

		var options = {
				chart: {
					title: title,
					subtitle: subtitle,
                }
	        //Aca se colocan los colores de la grafica
	        //colors: ['#f2d600', '#61bd4f', '#eb5a46']
            };

        var chart = new google.charts.Bar(document.getElementById(chartElement));
        
        
        google.visualization.events.addListener(chart, 'select', selectHandler);

        chart.draw(data, options);
        
        actividadPrincipalChart = chart;
        datosChart = data;
        
	}
	
	var selectHandler = function() {
		var selectedItem = actividadPrincipalChart.getSelection()[0];
	    if (selectedItem) {
	    	var actividad = datosChart.pg[selectedItem.column].label;
	    	document.location.href = contexto + "/visorCaso?actividadPrincipal=" + encodeURIComponent(actividad);
	    }
	};
	
	
	
	return {
		mostrarGrafico : mostrarGrafico
	}
}