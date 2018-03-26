var ERROR_FECHA_INICIO = "La fecha inicio no puede ser mayor que la fecha fin";
var ERROR_FECHA_FIN = "La fecha fin no puede ser menor que la fecha inicio";
var ERROR_RANGO_FECHA = "Si desea filtrar por rango de fecha es necesario ingresar, fecha inicio y fecha fin de lo contrario deje estos campos en blanco.";
var ERROR_EXPORTAR = "Error al exportar el reporte a formato excel";


function validarFechaInicioFiltro() {
	if ($("#fechaInicioFiltro").val() && $("#fechaFinFiltro").val()) {
		var fechaInicio = new Date($("#fechaInicioFiltro").val());
		var fechaFin = new Date($("#fechaFinFiltro").val());
		if (fechaInicio > fechaFin) {
			$("#fechaFinFiltro").val("");
			$("#messageError").removeClass("hide").text(ERROR_FECHA_INICIO);
			setTimeout(function() {
				$("#messageError").addClass("hide").text("");
			}, 10000);
		}
	}
}

function validarFechaFinFiltro() {
	if ($("#fechaInicioFiltro").val() && $("#fechaFinFiltro").val()) {
		var fechaInicio = new Date($("#fechaInicioFiltro").val());
		var fechaFin = new Date($("#fechaFinFiltro").val());
		if (fechaInicio > fechaFin) {
			$("#fechaInicioFiltro").val("");
			$("#messageError").removeClass("hide").text(ERROR_FECHA_FIN);
			setTimeout(function() {
				$("#messageError").addClass("hide").text("");
			}, 10000);
		}
	}
}

function validarFiltrosFechas() {
	if ($("#fechaInicioFiltro").val() || $("#fechaFinFiltro").val()) {
		if ($("#fechaInicioFiltro").val() && $("#fechaFinFiltro").val()) 
			return true;
		else
			return false;
	} else {
		return true;		
	}
}