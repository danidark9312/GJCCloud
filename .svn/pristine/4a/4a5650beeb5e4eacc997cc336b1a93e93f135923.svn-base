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