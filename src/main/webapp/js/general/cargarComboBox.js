var ESTADOACTIVO = 2;
var CODIGOCOLOMBIA = 343;
var isCargarPaises = true;
var seleccionar = true;
var cargarDepartamentoHechos = true;
var cargarDepartamentosMiembro = false;
// Metodo para iniciar los selectores de nuevo caso
function cargarTipoBienesAfectados(){
	if($("#txtclaseBien").length) obtenerClaseBien("cmbTipoBien");
}


function cargarCombox(isCargar){

	isCargarPaises = isCargar;

	if($("#txtTipoDeCuenta").length) cargarTipoCuenta("txtTipoDeCuenta");

	if($("#txtTipoDeCuentaModal").length) cargarTipoCuenta("txtTipoDeCuentaModal");

	if($("#txtTipoDeCuentaAdicionar").length) cargarTipoCuenta("txtTipoDeCuentaAdicionar");

	if($("#txtPaisDeLosHechos").length) cargarPaises("txtPaisDeLosHechos");

	if($("#paisDeLosHechosMod").length) cargarPaises("paisDeLosHechosMod");

	if($("#paisProcesoMod").length) cargarPaises("paisProcesoMod");

	if($("#txtPaisMiembro").length) cargarPaises("txtPaisMiembro");
	
	if($("#txtPaisAdicionarDemandado").length) cargarPaises("txtPaisAdicionarDemandado");

	if($("#txtPaisMiembroEquipo").length) cargarPaises("txtPaisMiembroEquipo");

	if($("#txtPaisMiembroCNuevo").length) cargarPaises("txtPaisMiembroCNuevo");

	if($("#txtPaisMiembroNuevoMiembro").length) cargarPaises("txtPaisMiembroNuevoMiembro");

	if($("#txtPaisMiembro1").length) cargarPaises("txtPaisMiembro1");

	if($("#txtPaisProceso").length) cargarPaises("txtPaisProceso");

	$("select[name=txtEstadoCaso]").each(function(ind, dato){
		cargarEstadoCaso(dato);
	});

	if($("#tipoCasoMod").length) cargarTipoCaso(document.getElementById("tipoCasoMod"));

	if($("#cmbTipoDeCaso").length) cargarTipoCaso(document.getElementById("cmbTipoDeCaso"));

	if($("#txtTipoCaso").length) cargarTipoCaso(document.getElementById("txtTipoCaso"));

	if($("#estadoCasoMod").length) cargarEstadoCaso(document.getElementById("estadoCasoMod"));

	if($("#txtclaseBien").length) obtenerClaseBien("txtclaseBien");

	

	if($("#txtInstanciaRadicado").length) obtenerInstancia("txtInstanciaRadicado");

	if($("#instanciaRadicadoMod").length) obtenerInstancia("instanciaRadicadoMod");

	if($("#txtTipoDeMiembro").length) obtenerTipoMiembro("txtTipoDeMiembro");

	if($("#txtTipoDeMiembroCasoNuevoMiembro").length) obtenerTipoMiembro("txtTipoDeMiembroCasoNuevoMiembro");

	if($("#txtParentescoMiembro").length) obtenerParentesco("txtParentescoMiembro");
	
//	if($("#tipoDocumento").length) obtenerTipoDocumento("tipoDocumento");
	if($("#victimaDemandanteTipoDocumento").length) obtenerTipoDocumento("victimaDemandanteTipoDocumento");
	if($("#demandadoTipoDocumento").length) obtenerTipoDocumento("demandadoTipoDocumento");
	if($("#nuevoDemandadoTipoDocumento").length) obtenerTipoDocumento("nuevoDemandadoTipoDocumento");
	if($("#nuevoVictimaDemandanteTipoDocumento").length) obtenerTipoDocumento("nuevoVictimaDemandanteTipoDocumento");
	if($("#abogadoTipoDocumento").length) obtenerTipoDocumento("abogadoTipoDocumento");
	if($("#txtTipoDocumentoNuevoMiembro").length) obtenerTipoDocumento("txtTipoDocumentoNuevoMiembro");
	if($("#txtTipoDocumentoNuevoMiembroEquipoCaso").length) obtenerTipoDocumento("txtTipoDocumentoNuevoMiembroEquipoCaso");
	
	if($("#txtParentescoMiembroNuevoMiembro").length) obtenerParentesco("txtParentescoMiembroNuevoMiembro");

	if($("#txtEntidadFinanciera").length) cargarEntidadesFinancieras("txtEntidadFinanciera");
	if($("#txtEntidadFinancieraModal").length) cargarEntidadesFinancieras("txtEntidadFinancieraModal");

	if($("#cmbMiembroAbogado").length) cargarAbogado("cmbMiembroAbogado");

	if($("#cmbMiembroAbogadoNuevoMiembro").length) cargarAbogado("cmbMiembroAbogadoNuevoMiembro");

	if($("#txtEntidadFinancieraAdicionar").length)
		cargarEntidadesFinancierasAdicionar("txtEntidadFinancieraAdicionar");

	if($("#filtroEstadoProcesal").length) cargarEstadosProcesales("filtroEstadoProcesal");
	
	if($("#auditoriaResponsablesFiltro").length) cargarResponsableJustificaciones("auditoriaResponsablesFiltro");

}
// metodo para mostrar los abogados en el seleccionar
function cargarAbogado(campoAbogado){
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerAbogados",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = document.getElementById(campoAbogado);
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.abogados, function(index, optionData){
				var select = document.getElementById(campoAbogado);
				var option = document.createElement("option");
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
// Metodo para cargar los abogados como responsables
function cargarResponsableTareas1() {

	$("#actividadParticular").find("select[name^='responsable']").each(function(){
		var select = this;
		// var option = document.createElement("option");
		// $(select).find("option").each(function(){
		// if($(this).val()=="")
		// {
		// seleccionar=false;
		// }
		// });
		// if (seleccionar) {
		// option.value = "";
		// option.text = "Seleccionar";
		// select.add(option);
		// }
		var option = document.createElement("option");
		if($("#cmbMiembroAbogado").find('option:selected').val() != ""){
			option.value = $("#cmbMiembroAbogado").find('option:selected').val();
			option.text = $("#cmbMiembroAbogado").find('option:selected').text();
			select.add(option);
		}
	});
	seleccionar = false;
}
// quitar responsable cuando se borre el abogado
function quitarResponsableTarea(idResponsble){
	var valorAnterior = null;
	$("#actividadParticular").find("select[name^='responsable']").each(function(){
		if($(this).val() == idResponsble){
			valorAnterior = "";
		}else{
			valorAnterior = $(this).val();
		}
		$(this).val(idResponsble);
		$(this).find('option:selected').remove();
		$(this).val(valorAnterior);
	});
}
/** Metodo para cargar los tipos de cuentas */
function cargarTipoCuenta(campoTipoCuenta) {
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerTipoCuenta",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = document.getElementById(campoTipoCuenta);
			// var select=$("#"+campoTipoCuenta);
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.tiposCuenta, function(index, optionData){
				var option = document.createElement("option");
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

// Metodo para cargar los responsables cuando se agreguen tareas no se esta
// utilizando
function agregarResponsablesNuevaTarea(columnaTarea){
	var select = $(columnaTarea).find("select[name^='responsable']");
	// var option = document.createElement("option");
	// option.value = "";
	// option.text = "Seleccionar";
	// select.add(option);
	$("#cmbMiembroAbogado option:disabled").each(function(ind1, dato1){
		var option = document.createElement("option");
		option.value = $(dato1).val();
		option.text = $(dato1).text();
		select.add(option);
	});

}
// Metodo para Cargar los responsables de las tareas
function cargarResponsableTareas(formulario){
	$(formulario).find("select[name^='responsable']").each(function(){
		var select = this;
		var option = document.createElement("option");
		option.value = "-1";
		option.text = "Otro";
		select.add(option);
		$("#cmbMiembroAbogado option:disabled").each(function(ind1, dato1){
			var option = document.createElement("option");
			option.value = $(dato1).val();
			option.text = $(dato1).text();
			select.add(option);
		});
	});
}
// Meotodo para validar los responsables cuando se borren del bloque de abogados
function habilitarResponsablesDesdeAbogados(){
	$("#actividades").find("select[name=cmbResponsableTarea]:gt(0)").each(function(ind, select1){
		$("#cmbMiembroAbogado option:disabled").each(function(ind, select2){
			var valorAnterior = $(select1).val();
			$(select1).val($(select2).val());
			$(select1).find('option:selected').removeAttr("disabled");
			$(select1).val(valorAnterior);
		});
	});
}
// Metodo para cargar los paises
function cargarPaises(campoPaises, codigoPais){
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerPaises",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = $("#" + campoPaises)[0];
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.paises, function(index, optionData){
				var option = document.createElement("option");
				option.value = optionData.codigo;
				option.text = optionData.nombre;
				select.add(option);

			});
			if (codigoPais) {
				$(select).val(codigoPais);
			} else {
				$(select).val(CODIGOCOLOMBIA);
			}
			if(cargarDepartamentoHechos){
				cargarDepartamentosPorPais(select, 'txtDepartamentoDeLosHechos', 'txtMunicipioDeLosHechos');
				cargarDepartamentosPorPais(select, 'txtDeparmentoProceso', 'txtMunicipioProceso');
				cargarDepartamentoHechos = false;
				cargarDepartamentosMiembro = true;
			}
			if(cargarDepartamentosMiembro){
				cargarDepartamentosPorPais1('#txtPaisMiembro');
				cargarDepartamentosPorPais1('#txtPaisMiembro1');				
				cargarDepartamentosMiembro = false;
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}
// Metodo para cargar los departamentos por pais
function cargarDepartamentosPorPais(campoPais, campoDepartamento, campoMunicipio, codigoDepartamento){

	$("#" + campoDepartamento).find("option").remove();
	$("#" + campoMunicipio).find("option").remove();
	var codigoPais = null;
	if(codigoDepartamento){
		codigoPais = "codigoPais=" + campoPais;
	}else{
		codigoPais = "codigoPais=" + campoPais.value;
	}
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerDepartamentosPorPais",
		data : codigoPais,
		success : function(response){
			waitingDialog.hide();
			var select = document.getElementById(campoDepartamento);
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.departamentos, function(index, optionData){
				var select = document.getElementById(campoDepartamento);
				var option = document.createElement("option");
				option.value = optionData.codigo;
				option.text = optionData.nombre;
				select.add(option);
				if(codigoDepartamento){
					$(select).val(codigoDepartamento);
				}
			});
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

// Metodo para cargar las ciudades por departamento
function cargarCiudadPorDepartamentos(campoDepartamento, campoCiudad, codigoCiudad){

	$(campoCiudad).find("option").remove();
	var codigoDepartamento = null;
	if(codigoCiudad){
		codigoDepartamento = "codigoDepartamento=" + campoDepartamento;
	}else{
		codigoDepartamento = "codigoDepartamento=" + campoDepartamento.value;
	}
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerCiudadPorDepartamento",
		data : codigoDepartamento,
		success : function(response){
			waitingDialog.hide();
			var select = document.getElementById(campoCiudad);
			select.options.length = 0;
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.ciudades, function(index, optionData){
				var select = document.getElementById(campoCiudad);
				var option = document.createElement("option");
				option.value = optionData.codigo;
				option.text = optionData.nombre;
				select.add(option);
				if(codigoCiudad){
					$(select).val(codigoCiudad);
				}
			});
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}
// Metodo para cargar los departamentos por pais para los miembros del equipo
function cargarDepartamentosPorPais1(campoPais, codigoPais, codigoDepartamento){

	var divTemp = null;
	if(codigoDepartamento){
		divTemp = $("#" + codigoPais).closest("div[name *= 'formularioEquipoCaso']");
	}else{
		divTemp = $(campoPais).closest("div[name *= 'formularioEquipoCaso']");
	}
	var campoDepartamento = null;
	$(divTemp).find("select[name *= 'epartamentoMiembro']").each(function(){
		campoDepartamento = $(this)[0];
		$(campoDepartamento).find("option").remove();
	});
	$(divTemp).find("select[name *= 'unicipioMiembro']").each(function(){
		$(this).find("option").remove();
	});
	var codigoPais = null;
	if(codigoDepartamento){
		codigoPais = "codigoPais=" + campoPais;
	}else{
		if(campoPais.value){
			codigoPais = "codigoPais=" + campoPais.value;
		}else{
			codigoPais = "codigoPais=" + CODIGOCOLOMBIA;
		}
	}
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerDepartamentosPorPais",
		data : codigoPais,
		success : function(response){
			waitingDialog.hide();
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			campoDepartamento.add(option);
			$.each(response.departamentos, function(index, optionData){
				var option = document.createElement("option");
				option.value = optionData.codigo;
				option.text = optionData.nombre;
				campoDepartamento.add(option);
				if(codigoDepartamento){
					$(campoDepartamento).val(codigoDepartamento);
				}
			});
		},
		error : function(e) {
			waitingDialog.hide();
		}
	});
}

function cargarDepartamentosPorPaisNuevoMiembro(campoPais, codigoPais, codigoDepartamento){
	var divTemp = null;
	
	if(codigoPais == 1){
		if(codigoDepartamento){
			divTemp = $("#" + codigoPais).closest("div[name=formularioEquipoCasoNuevoMiembro]");
		}else{
			divTemp = $(campoPais).closest("div[name=formularioEquipoCasoNuevoMiembro]");
		}
	}else if(codigoPais == 2){
		if(codigoDepartamento){
			divTemp = $("#" + codigoPais).closest("div[name=formularioEquipoCasoNuevoMiembroModal]");
		}else{
			divTemp = $(campoPais).closest("div[name=formularioEquipoCasoNuevoMiembroModal]");
		}
	}

	var campoDepartamento = null;
	$(divTemp).find("select[name*='epartamentoMiembro']").each(function(){
		campoDepartamento = $(this)[0];
		$(campoDepartamento).find("option").remove();
	});
	$(divTemp).find("select[name*='unicipioMiembro']").each(function(){
		$(this).find("option").remove();
	});
	var codigoPais = null;
	if(codigoDepartamento){
		codigoPais = "codigoPais=" + campoPais;
	}else{
		if(campoPais.value){
			codigoPais = "codigoPais=" + campoPais.value;
		}else{
			codigoPais = "codigoPais=" + CODIGOCOLOMBIA;
		}
	}
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerDepartamentosPorPais",
		data : codigoPais,
		success : function(response){
			waitingDialog.hide();
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			campoDepartamento.add(option);
			$.each(response.departamentos, function(index, optionData){
				var option = document.createElement("option");
				option.value = optionData.codigo;
				option.text = optionData.nombre;
				campoDepartamento.add(option);
				if(codigoDepartamento){
					$(campoDepartamento).val(codigoDepartamento);
				}
			});
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

// Metodo para cargar las ciudades por departamento para los miembros del equipo
function cargarCiudadPorDepartamentos1(campoDepartamento, codigoDepartamento, codigoCiudad){

	// var
	// divTemp=$(campoDepartamento).closest("div[name=formularioEquipoCaso]");

	var divTemp = null;
	if(codigoCiudad){
		divTemp = $("#" + codigoDepartamento).closest("div[name=formularioEquipoCaso]");
	}else{
		divTemp = $(campoDepartamento).closest("div[name=formularioEquipoCaso]");
	}

	var campoCiudad = null;
	$(divTemp).find("select[name*='unicipioMiembro']").each(function(){
		campoCiudad = $(this)[0];
		$(this).find("option").remove();
	});
	// var codigoDepartamento = "codigoDepartamento=" + campoDepartamento.value;
	var codigoDepartamento = null;
	if(codigoCiudad){
		codigoDepartamento = "codigoDepartamento=" + campoDepartamento;
	}else{
		codigoDepartamento = "codigoDepartamento=" + campoDepartamento.value;
	}

	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerCiudadPorDepartamento",
		data : codigoDepartamento,
		success : function(response){
			waitingDialog.hide();
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			campoCiudad.add(option);
			$.each(response.ciudades, function(index, optionData){
				// var select = document.getElementById(campoTemp);
				var option = document.createElement("option");
				option.value = optionData.codigo;
				option.text = optionData.nombre;
				campoCiudad.add(option);
				if(codigoCiudad){
					$(campoCiudad).val(codigoCiudad);
				}
			});
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function cargarCiudadPorDepartamentosNuevoMiembro(campoDepartamento, codigoDepartamento, codigoCiudad){

	var divTemp = null;
	// var
	// divTemp=$(campoDepartamento).closest("div[name=formularioEquipoCaso]");
	if(codigoDepartamento == 1){
		codigoDepartamento = null;
		if(codigoDepartamento){
			divTemp = $("#" + codigoPais).closest("div[name=formularioEquipoCasoNuevoMiembro]");
		}else{
			divTemp = $(campoDepartamento).closest("div[name=formularioEquipoCasoNuevoMiembro]");
		}
	}else if(codigoDepartamento == 2){
		codigoDepartamento = null;
		if(codigoDepartamento){
			divTemp = $("#" + codigoPais).closest("div[name=formularioEquipoCasoNuevoMiembroModal]");
		}else{
			divTemp = $(campoDepartamento).closest("div[name=formularioEquipoCasoNuevoMiembroModal]");
		}
	}

	var campoCiudad = null;
	$(divTemp).find("select[name*='unicipioMiembro']").each(function(){
		campoCiudad = $(this)[0];
		$(this).find("option").remove();
	});
	// var codigoDepartamento = "codigoDepartamento=" + campoDepartamento.value;
	var codigoDepartamento = null;
	if(codigoCiudad){
		codigoDepartamento = "codigoDepartamento=" + campoDepartamento;
	}else{
		codigoDepartamento = "codigoDepartamento=" + campoDepartamento.value;
	}

	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerCiudadPorDepartamento",
		data : codigoDepartamento,
		success : function(response){
			waitingDialog.hide();
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			campoCiudad.add(option);
			$.each(response.ciudades, function(index, optionData){
				// var select = document.getElementById(campoTemp);
				var option = document.createElement("option");
				option.value = optionData.codigo;
				option.text = optionData.nombre;
				campoCiudad.add(option);
				if(codigoCiudad){
					$(campoCiudad).val(codigoCiudad);
				}
			});
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

// Metodo para cargar los tipos de caso
function cargarTipoCaso(campoTipoDeCaso){
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerTipoDeCaso",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = campoTipoDeCaso;
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.tiposDeCaso, function(index, optionData){
				// var select = document.getElementById(campoTipoDeCaso);
				var option = document.createElement("option");
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
// Metodo para cargar los estados del caso
function cargarEstadoCaso(campoEstadoCaso){
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerEstadoCaso",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = campoEstadoCaso;
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.estadosCaso, function(index, optionData){
				// var select = document.getElementById(campoEstadoCaso);
				var option = document.createElement("option");
				option.value = optionData.codigo;
				option.text = optionData.nombre;
				select.add(option);
			});
			if($(select).attr("id") != "filtroEstadoCaso") $(select).val(ESTADOACTIVO);
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}
// Metodo para obtener la clase de bien : inmueble o mueble
function obtenerClaseBien(campoClaseBien){
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerClaseBien",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = document.getElementById(campoClaseBien);
			$(select).find("option").remove();
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.clasesBienes, function(index, optionData){
				var select = document.getElementById(campoClaseBien);
				var option = document.createElement("option");
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
// Metodo para obtener la instancia del radicado
function obtenerInstancia(campoInstancia){
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerInstancia",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = document.getElementById(campoInstancia);
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.instancias, function(index, optionData){
				var select = document.getElementById(campoInstancia);
				var option = document.createElement("option");
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
// Metodo para obtener el tipo de miembro
function obtenerTipoMiembro(campoTipoMiembro, codigoTipoMiembro){
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerTipoMiembro",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = null;
			if(codigoTipoMiembro){
				select = campoTipoMiembro;
			}else{

				select = document.getElementById(campoTipoMiembro);
			}
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.tiposMiembros, function(index, optionData){

				if(optionData.codigo != 3 && optionData.codigo != 5){
					// var select = document.getElementById(campoTipoMiembro);
					var option = document.createElement("option");
					option.value = optionData.codigo;
					option.text = optionData.nombre;
					select.add(option);
					if(codigoTipoMiembro){
						$(select).val(codigoTipoMiembro);
					}
				}
			});
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}
// Metodo para obtener el parentezco
function obtenerParentesco(campoParentesco, codigoParentesco){
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerParentesco",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = null;
			if(codigoParentesco){
				select = campoParentesco;
			}else{

				select = document.getElementById(campoParentesco);
			}
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.parentescos, function(index, optionData){
				// var select = document.getElementById(campoParentesco);
				var option = document.createElement("option");
				option.value = optionData.codigo;
				option.text = optionData.nombre;
				select.add(option);
				if(codigoParentesco){
					$(select).val(codigoParentesco);
				}
			});
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function cargarActividades(listaActividades){
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestroTipoCaso/obtenerActividades",
		data : null,
		success : function(response){
			waitingDialog.hide();
			var select = document.getElementById(listaActividades);
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.actividad, function(index, optionData){
				var select = document.getElementById(listaActividades);
				var option = document.createElement("option");
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
// Metodo que carga las entidades financieras
function cargarEntidadesFinancieras(CampoEntidadFinanciera){
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/cargarEntidadesFinancieras",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = document.getElementById(CampoEntidadFinanciera);
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.entidadesFinancieras, function(index, optionData){
				var select = document.getElementById(CampoEntidadFinanciera);
				var option = document.createElement("option");
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

function cargarEntidadesFinancierasAdicionar(CampoEntidadFinanciera){
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/cargarEntidadesFinancieras",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = document.getElementById(CampoEntidadFinanciera);
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.entidadesFinancieras, function(index, optionData){
				var select = document.getElementById(CampoEntidadFinanciera);
				var option = document.createElement("option");
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

function cargarEstadosProcesales(campo) {
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/obtenerEstadosProcesales",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = document.getElementById(campo);
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			var select = document.getElementById(campo);
			$.each(response.data, function(index, optionData){
				var option = document.createElement("option");
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
//Aca es donde se llena el combobox
function cargarRolesUsuarios(cmbFiltroRol) {
	waitingDialog.show('');
	$.ajax({
		dataType : "json",
		url : contexto + "/maestro/getRolesUsuarios",
		data : null,
		async: false,
		success : function(response){
			waitingDialog.hide();
			var select = document.getElementById(cmbFiltroRol);
			var option = document.createElement("option");
			option.value = "";
			option.text = "Seleccionar";
			select.add(option);
			$.each(response.data, function(index, optionData){
				var select = document.getElementById(cmbFiltroRol);
				var option = document.createElement("option");
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

	function obtenerTipoMiembroDetalleCaso(campoTipoMiembro, codigoTipoMiembro){
		waitingDialog.show('');
		$.ajax({
			dataType : "json",
			url : contexto + "/maestro/obtenerTipoMiembro",
			data : null,
			success : function(response){
				waitingDialog.hide();
				var select = null;
				if(codigoTipoMiembro){
					select = campoTipoMiembro;
				}else{
	
					select = document.getElementById(campoTipoMiembro);
				}
				var option = document.createElement("option");
				option.value = "";
				option.text = "Seleccionar";
				select.add(option);
				$.each(response.tiposMiembros, function(index, optionData){
	
					if(optionData.codigo != 3 && optionData.codigo != 5){
						// var select = document.getElementById(campoTipoMiembro);
						var option = document.createElement("option");
						option.value = optionData.codigo;
						option.text = optionData.nombre;
						select.add(option);
						if(codigoTipoMiembro){
							$(select).val(codigoTipoMiembro);
						}
					}
				});
				cargarPaisesDetalleCaso("paisMiembroVictimaDemandante");
			},
			error : function(e){
				waitingDialog.hide();
			}
		});
	}
	
	//Metodo para cargar los paises
	function cargarPaisesDetalleCaso(campoPaises, codigoPais){
		waitingDialog.show('');
		$.ajax({
			dataType : "json",
			url : contexto + "/maestro/obtenerPaises",
			data : null,
			success : function(response){
				waitingDialog.hide();
				var select = $("#" + campoPaises)[0];
				var option = document.createElement("option");
				option.value = "";
				option.text = "Seleccionar";
				select.add(option);
				$.each(response.paises, function(index, optionData){
					var option = document.createElement("option");
					option.value = optionData.codigo;
					option.text = optionData.nombre;
					select.add(option);

				});
				if(codigoPais){
					$(select).val(codigoPais);
				}else{
					$(select).val(CODIGOCOLOMBIA);
				}
				if(cargarDepartamentoHechos){
					cargarDepartamentosPorPais(select, 'txtDepartamentoDeLosHechos', 'txtMunicipioDeLosHechos');
					cargarDepartamentosPorPais(select, 'txtDeparmentoProceso', 'txtMunicipioProceso');
					cargarDepartamentoHechos = false;
					cargarDepartamentosMiembro = true;
				}
				if(cargarDepartamentosMiembro){
					cargarDepartamentosPorPais1('#txtPaisMiembro');
					cargarDepartamentosPorPais1('#txtPaisMiembro1');
					cargarDepartamentosMiembro = false;
				}
				
				obtenerParentescoDetalleCaso("parentescoMiembro");
			},
			error : function(e){
				waitingDialog.hide();
			}
		});
	}

	//Metodo para obtener el parentezco
	function obtenerParentescoDetalleCaso(campoParentesco, codigoParentesco){
		waitingDialog.show('');
		$.ajax({
			dataType : "json",
			url : contexto + "/maestro/obtenerParentesco",
			data : null,
			success : function(response){
				waitingDialog.hide();
				var select = null;
				if(codigoParentesco){
					select = campoParentesco;
				}else{
					select = document.getElementById(campoParentesco);
				}
				var option = document.createElement("option");
				option.value = "";
				option.text = "Seleccionar";
				select.add(option);
				$.each(response.parentescos, function(index, optionData){
					// var select = document.getElementById(campoParentesco);
					var option = document.createElement("option");
					option.value = optionData.codigo;
					option.text = optionData.nombre;
					select.add(option);
					if(codigoParentesco){
						$(select).val(codigoParentesco);
					}
				});
				obtenerClaseBienDetalleCaso("cmbTipoBien");
			},
			error : function(e){
				waitingDialog.hide();
			}
		});
	}



	//Metodo para obtener la clase de bien : inmueble o mueble
	function obtenerClaseBienDetalleCaso(campoClaseBien){
		waitingDialog.show('');
		$.ajax({
			dataType : "json",
			url : contexto + "/maestro/obtenerClaseBien",
			data : null,
			success : function(response){
				waitingDialog.hide();
				var select = document.getElementById(campoClaseBien);
				$(select).find("option").remove();
				var option = document.createElement("option");
				option.value = "";
				option.text = "Seleccionar";
				select.add(option);
				$.each(response.clasesBienes, function(index, optionData){
					var select = document.getElementById(campoClaseBien);
					var option = document.createElement("option");
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
	
	//Metodo para obtener el tipo de documento
/*	function obtenerTipoDocumento(tipoDocumento){
		waitingDialog.show('');
		$.ajax({
			dataType : "json",
			url : contexto + "/maestro/TipoDocumento",
			data : null,
			success : function(response){
				$("[name=tipoDocumento]").each(function(index,data){
					waitingDialog.hide();
					select = $(data)[0];
					//select = $("#modificarVictimaDemandante #tipoDocumento")[0];
					var option = document.createElement("option");
					option.value = "";
					option.text = "Seleccionar";
					select.add(option);
					$.each(response.tipoDocumento, function(index, optionData){					
						var option = document.createElement("option");
						option.value = optionData.codigo;
						option.text = optionData.nombre;
						select.add(option);					
					});
				})
//				obtenerClaseBienDetalleCaso("cmbTipoBien");
			},
			error : function(e){
				waitingDialog.hide();
			}
		});
	}
*/
	
	//Metodo para obtener el tipo de documento
	function obtenerTipoDocumento(tipoDocumento){
		waitingDialog.show('');
		$.ajax({
			dataType : "json",
			url : contexto + "/maestro/TipoDocumento",
			data : null,
			async:false,
			success : function(response){
				waitingDialog.hide();
				select = $("#"+tipoDocumento)[0];
				if(!select)
					return;
//				select = $("#demandadoTipoDocumento"+tipoDocumento)[0];
				var option = document.createElement("option");
				option.value = "";
				option.text = "Seleccionar";
				select.add(option);
				$.each(response.tipoDocumento, function(index, optionData){					
					var option = document.createElement("option");
					option.value = optionData.codigo;
					option.text = optionData.nombre;
					select.add(option);					
				});
	//				obtenerClaseBienDetalleCaso("cmbTipoBien");
			},
			error : function(e){
				waitingDialog.hide();
			}
		})
		
	}
	
	function cargarRolesChosen(combobox){
		waitingDialog.show('');
		$.ajax({
			dataType : "json",
			url : contexto + "/maestro/getRolesUsuarios",
			data : null,
			async: false,
			success : function(response){
				waitingDialog.hide();
				$.each(response.data, function(index, optionData){					
					combobox.append('<option value="' + optionData.codigo+ '">' + optionData.nombre + '</option>');
					combobox.trigger('chosen:updated');
				});
			},
			error : function(e){
				waitingDialog.hide();
			}
		});
}