var contadorClonador = 0;
var casoActividad = false;
var codigoRol = -1;
var countRoles = 0;
var CSRF = "_csrf=" + $("#_csrf").val();
var eventoBoton = "";
var idEliminar = "";
var filaRol = 0;
var RolesArray = new Array();
var nuevo = true;
var TIEMPOLIMPIARERROR = 10000;
var listaOpciones = '';
var listaAcciones = '';
var textoSnLectura = "Consultar";
var textoSnEscritura = "Modificar";


function adherirEditarEliminar(objeto, titulo, eventoEdita, eventoElimina){
	var editar = '' + '<a href="javascript:void(0);" onclick="' + eventoEdita + '(' + objeto + ')"' + ' title="Editar '
			+ titulo + '"><i class="glyphicon glyphicon-edit"></i></a>';

	var eliminar = '';
	+'<a href="javascript:void(0);" onclick="' + eventoElimina + '(' + objeto + ')"' + 'title="Eliminar ' + titulo
			+ '"><i class="glyphicon glyphicon-trash" style="color: red;"></i></a>';

	if(eventoEdita && eventoElimina){
		return editar + '&nbsp;' + eliminar;
	}

	if(!eventoEdita){
		return eliminar;
	}

	if(!eventoElimina){
		return editar;
	}

	return '';
}

function mostrarTablaRoles() {
	var data = "_csrf=" + $("#_csrf").val() + "&activo=" + $("#filtroEstado").val();
	var tablaHtml = "";

	

	tablaHtml = "<table id='datatableRoles' class='table table-striped table-hover' >";
	tablaHtml += "<thead>";
	tablaHtml += "<tr class='tr_titulo'>";
	tablaHtml += "<th>Código</th>";
	tablaHtml += "<th>Nombre</th>";
	tablaHtml += "<th>Estado</th>";
	// tablaHtml += "<th >Acción</th>";
	tablaHtml += "</tr>";
	tablaHtml += "</thead>";
	tablaHtml += "<tbody>";
	tablaHtml += "</tbody>";
	tablaHtml += "</table>";
	$("#tablaListadoRoles").html(tablaHtml);
	oTable = $('#datatableRoles').dataTable({
		"bProcessing" : true,
		"bServerSide" : true,
		"bFilter" : true,
		"bInfo" : true,
		"sAjaxSource" : contexto + "/maestroRoles/mostrarRoles?" + data,
		"language" : {
			"sProcessing" : "Procesando...",
			"sLengthMenu" : "Mostrar _MENU_ registros",
			"sZeroRecords" : "No se encontraron resultados",
			"sEmptyTable" : "Ningún dato disponible en esta tabla",
			"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
			"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
			"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
			"sInfoPostFix" : "",
			"sSearch" : "Buscar: ",
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
			"mDataProp" : "codigo",
			"bSortable" : false,
			"sWidth" : "20%"
		}, {
			"mDataProp" : "descripcion",
			"bSortable" : false,
			"sWidth" : "50%"
		}, {
			"mDataProp" : "estado",
			"bSortable" : false,
			"sWidth" : "20%"
		}],
		"fnServerData" : function(sSource, aoData, fnCallback) {
			data = "_csrf=" + $("#_csrf").val() + "&activo=" + $("#filtroEstado").val();
			waitingDialog.show('');
			$.ajax({
				type : 'POST',
				url : contexto + "/maestroRoles/mostrarRoles?" + data,
				data : aoData,
				success : fnCallback,
				error : function(e){
					waitingDialog.hide();
				}
			});
		},
		"fnDrawCallback" : function(oSettings){
			try{
				waitingDialog.hide();
			}catch(e){
				console.log(e);
			}
			$.each($("[name = verDetalle]"), function(index, optionData){
				optionData.onclick = function(){
					var tr = optionData.parentNode.parentNode.parentNode;
					if(oTable.fnIsOpen(tr)){
						oTable.fnClose(tr);
						optionData.firstChild.removeAttribute('class', 'glyphicon glyphicon-chevron-up');
						optionData.firstChild.setAttribute("class", "glyphicon glyphicon-chevron-down");
					}else{
						oTable.fnOpen(tr, oTable.fnGetData(tr).detalle, "info_row");
						optionData.firstChild.removeAttribute('class', "glyphicon glyphicon-chevron-down");
						optionData.firstChild.setAttribute("class", 'glyphicon glyphicon-chevron-up');
					}
				};
			});
			$.each($("[name = verDetalleRol]"), function(index, optionData){
				optionData.onclick = function(){
					var tr = optionData.parentNode.parentNode.parentNode;
					if(oTable.fnIsOpen(tr)){
						oTable.fnClose(tr);
						optionData.firstChild.removeAttribute('class', 'glyphicon glyphicon-chevron-up');
						optionData.firstChild.setAttribute("class", "glyphicon glyphicon-chevron-down");
					}else{
						oTable.fnOpen(tr, oTable.fnGetData(tr).detalleEquipoCaso, "info_row");
						optionData.firstChild.removeAttribute('class', "glyphicon glyphicon-chevron-down");
						optionData.firstChild.setAttribute("class", 'glyphicon glyphicon-chevron-up');
					}
				};
			});

			oTable.on('mouseover', 'tr', function(){
				this.style.cursor = 'pointer';
			});

			oTable.find("tr").each(function(data) {
				var aData = oTable.fnGetData(this);

				$(this).find("td:lt(3)").on("click", function(){
					if(null != aData){
						consultarRolAcciones(aData.codigo, true);
					}
				});
			});
		}
	});
}

// PERMISOS
function mostrarTablaPermisos() {

	var data = "_csrf=" + $("#_csrf").val();
	var tablaHtml = "";

	tablaHtml = "<table id='datatablePermisos' class='table table-striped table-hover' >";
	tablaHtml += "<thead>";
	tablaHtml += "<tr class='tr_titulo'>";
	tablaHtml += "<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>";
	tablaHtml += "<th>Acciones</th>";
	tablaHtml += "<th></th>";
	tablaHtml += "<th></th>";
	tablaHtml += "<th></th>";
	tablaHtml += "<th></th>";
	tablaHtml += "</tr>";
	tablaHtml += "<tr class='tr_titulo'>";
	tablaHtml += "<th>Opción</th>";
	tablaHtml += "<th>Crear</th>";
	tablaHtml += "<th>Eliminar</th>";
	tablaHtml += "<th>Escribir</th>";
	tablaHtml += "<th>Leer</th>";
	tablaHtml += "<th>Restaurar</th>";
	tablaHtml += "</tr>";
	tablaHtml += "</thead>";
	tablaHtml += "<tbody>";
	tablaHtml += "</tbody>";
	tablaHtml += "</table>";
	$("#tablaListadoPermisos").html(tablaHtml);

	oTable = $('#datatablePermisos').dataTable({
		"bProcessing" : true,
		"bServerSide" : true,
		"bFilter" : true,
		"bInfo" : false,
		"sAjaxSource" : contexto + "/maestroPermisos/mostrarPermisos?" + data,
		"language" : {
			"sProcessing" : "Procesando...",
			"sLengthMenu" : "Mostrar _MENU_ registros",
			"sZeroRecords" : "No se encontraron resultados",
			"sEmptyTable" : "Ningún dato disponible en esta tabla",
			"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
			"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
			"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
			"sInfoPostFix" : "",
			"sSearch" : "Buscar: ",
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
			"mDataProp" : "codigo",
			"bSortable" : false,
			"sWidth" : "20%"
		}, {
			"mDataProp" : "descripcion",
			"bSortable" : false,
			"sWidth" : "50%"
		}, {
			"mDataProp" : "estado",
			"bSortable" : false,
			"sWidth" : "20%"
		}],
		"fnServerData" : function(sSource, aoData, fnCallback){
			waitingDialog.show('');
			$.ajax({
				type : 'POST',
				url : contexto + "/permisosOpciones/consultarOpciones?" + data,
				data : aoData,
				success : fnCallback,
				error : function(e){
					waitingDialog.hide();
				}
			});
		},
		"fnDrawCallback" : function(oSettings){
			try{
				waitingDialog.hide();
			}catch(e){
				console.log(e);
			}
			$.each($("[name = verDetalle]"), function(index, optionData){
				optionData.onclick = function(){
					var tr = optionData.parentNode.parentNode.parentNode;
					if(oTable.fnIsOpen(tr)){
						oTable.fnClose(tr);
						optionData.firstChild.removeAttribute('class', 'glyphicon glyphicon-chevron-up');
						optionData.firstChild.setAttribute("class", "glyphicon glyphicon-chevron-down");
					}else{
						oTable.fnOpen(tr, oTable.fnGetData(tr).detalle, "info_row");
						optionData.firstChild.removeAttribute('class', "glyphicon glyphicon-chevron-down");
						optionData.firstChild.setAttribute("class", 'glyphicon glyphicon-chevron-up');
					}
				};
			});

			$.each($("[name = verDetallePermisos]"), function(index, optionData){
				optionData.onclick = function(){
					var tr = optionData.parentNode.parentNode.parentNode;
					if(oTable.fnIsOpen(tr)){
						oTable.fnClose(tr);
						optionData.firstChild.removeAttribute('class', 'glyphicon glyphicon-chevron-up');
						optionData.firstChild.setAttribute("class", "glyphicon glyphicon-chevron-down");
					}else{
						oTable.fnOpen(tr, oTable.fnGetData(tr).detalleEquipoCaso, "info_row");
						optionData.firstChild.removeAttribute('class', "glyphicon glyphicon-chevron-down");
						optionData.firstChild.setAttribute("class", 'glyphicon glyphicon-chevron-up');
					}
				};
			});

			oTable.on('mouseover', 'tr', function(){
				this.style.cursor = 'pointer';
			});

			oTable.find("tr").each(function(data){
				var aData = oTable.fnGetData(this);

				$(this).find("td:lt(2)").on("click", function(){
					if(null != aData){
						consultarPermisos(aData.codigo);
					}
				});
			});
		}
	});
}

function consultarEstadoRol(){
	var data_rol = "";
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroRoles/consultarEstados",
		data : data_rol,
		success : function(res){
			waitingDialog.hide();
			if(res.codigo != null){
				// LIMPIAR MODAL ACTUALIZAR ROL, CREAR ROL QUE ES EL MISMO
				limpiarModal();
				codigoTipoCaso = codigo;
				nuevo = false;
				$("#modal-actualizarRol").modal("show");

				$('#guardarRol').show();

				$("#codigoRol").val(res.codigo);
				$("#nombreRol").val(res.nombre);

				var selected = $("#estadoRol option:selected").map(function(){
					return this.value
				}).get();
				$.each(res.estados, function(index, optionData){
					selected.push(optionData.cdEstado);
				});
				$('#estadoRol').val(selected);
				$('#estadoRol').trigger("chosen:updated");
				$.each(res.estados, function(index, optionData){
					casoActividad = true;
				});
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

// PERMISOS
function consultarPermisos(codigo) {
	var data = "codigo=" + codigo;

	data += '&' + CSRF;
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/permisosOpciones/consultarOpcionesRol",
		data : data,
		success : function(res) {
			waitingDialog.hide();
			if (res.codigo != null) {
				limpiarModal();
				codigoRol = res.codigo;
				nuevo = false;
				$("#modal-actualizarRol").modal("show");
				// $('#actualizarRol').show();
				$('#guardarRol').show();
				$("#campoRol").show();
				limpiarCampos();
				$("#codigoRol").val(res.rol.codigo);
				$("#nombreRol").val(res.rol.descripcion);
				try {
					$("#estadoRol").val(res.rol.estado[0].cdestado);
				}catch(e){
					console.log(e);
				}
				try{
					// $.each(res.RolRolList, function(index, optionData){

					// $("#papeleraRol").show();
					// $(".codigoRol").val(optionData.codigo);
					// $(".nombreRol").val(optionData.descripcion);
					// $("#detalleRol").val(optionData.estado);
					// adicionarRol();

					// REVISAR SI ELIMINAR
					// $("#adicionarRolPrincipal").hide();
					/*
					 * $("#botonAdicionarRol").attr("parentNode.parentNode");
					 * var div = document.getElementById('papeleraRol'); var
					 * nuevosRoles = div.parentNode.parentNode; var object =
					 * nuevosRoles.cloneNode(true);
					 * 
					 * object.children[0].children[1].value=optionData.codigo;
					 * object.children[1].children[1].value=optionData.descripcion;
					 * var div = object.getElementsByTagName('a')[0];
					 * nuevosRoles.parentNode.appendChild(object); if(index
					 * ==0){ $('#campoRol').remove(); }
					 */
					// });
					// $.each(res.rolTipoCasoList, function(index, optionData){
					// casoRol = true;
					// });
				}catch(e){
					console.log(e);
				}
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function consultarEstadoRol() {
	var data_rol = "";
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroRoles/consultarEstados",
		data : data_rol,
		success : function(res){
			waitingDialog.hide();
			if(res.codigo != null){
				// LIMPIAR MODAL ACTUALIZAR ROL, CREAR ROL QUE ES EL MISMO
				limpiarModal();
				codigoTipoCaso = codigo;
				nuevo = false;
				$("#modal-actualizarRol").modal("show");

				$('#guardarRol').show();

				$("#codigoRol").val(res.codigo);
				$("#nombreRol").val(res.nombre);

				var selected = $("#estadoRol option:selected").map(function(){
					return this.value
				}).get();
				$.each(res.estados, function(index, optionData){
					selected.push(optionData.cdEstado);
				});
				$('#estadoRol').val(selected);
				$('#estadoRol').trigger("chosen:updated");
				$.each(res.estados, function(index, optionData){
					casoActividad = true;
				});
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function consultarRolAcciones(codigo, traerListadosAccionesOpciones){
	var data = "codigo=" + codigo;

	data += "&_csrf=" + $("#_csrf").val();

	if(traerListadosAccionesOpciones){
		consultarOpcionesAccionesYRol(codigo);
	} else {
		waitingDialog.show('');
		$.ajax({
			type : "POST",
			dataType : "json",
			url : contexto + "/maestroRoles/consultarRol",
			data : data,
			success : function(res){
				waitingDialog.hide();
				if(res.codigo != null){
					limpiarModal();
					codigoRol = res.codigo;
					nuevo = false;
					$("#modal-actualizarRol").modal("show");
					$('#guardarRol').show();
					limpiarCampos();
					$("#codigoRol").val(res.codigo);
					$("#nombreRol").val(res.descripcion);
					try{
						$("#estadoRol").val(res.estado[0].cdestado);
					}catch(e){
						console.log(e);
					}
					consultarAccionesRol(res.codigo);
				}
			},
			error : function(e){
				waitingDialog.hide();
			}
		});
	}
}

function existNameRol(){
	var data_rol = "descripcion=" + $("#nombreRol").val();
	var msjError = "No se puede guardar el rol, ya existe un Rol con el mismo nombre.";

	data_rol += "&_csrf=" + $("#_csrf").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroRoles/existNameRol",
		data : data_rol,
		success : function(res) {
			waitingDialog.hide();
			if (res.STATUS == "SUCCESS") {
				doAjaxPostAdd();
			} else {
				$("#messageErrorModal").html(msjError);
				$("#messageErrorModal").show();
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function canIsDeleteRol(codigo){
	var data_rol = "cdrol.cdrol=" + codigo;
	var msjError = "No se puede eliminar el rol, ya se encuentra asociada a uno o varios usuarios.";

	data_rol += "&_csrf=" + $("#_csrf").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroRol/canIsDeleteRol",
		data : data_rol,
		success : function(res){
			waitingDialog.hide();
			if(res.STATUS == "SUCCESS"){
				$("#modal-EliminarRol").modal("show");
			}else{
				$("#messageError").html(msjError);
				$("#messageError").show();
				setTimeout("limpiarMensajeError();", TIEMPOLIMPIARERROR);
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function buscarActividad() {
	var table = $('#datatableRoles').DataTable();
	$('#buscarRol').on('keyup', function(){
		table.search(this.value).draw();
	});
}

function rulesForm(){
	$frmActividades = $("#frmRoles").each(function(){
		$(this).validate({
			rules : {
				nombreActividad : {
					required : true
				}
			},
			// Messages for form validation
			messages : {
				nombreActividad : {
					required : 'El nombre de la actividad es requerido.'
				}
			},
			// Do not change code below
			errorPlacement : function(error, element){
				error.insertAfter(element.parent());
			}
		});
	});

	$frmNuevasRoles = $("#nuevasRoles").each(function(){
		$(this).validate({

			rules : {
				nombreRol : {
					required : true
				},
				detalleRol : {
					required : true
				}
			},

			// Messages for form validation
			messages : {
				nombreRol : {
					required : 'El nombre es requerido.'
				},
				detalleRol : {
					required : 'El detalle es requerido'
				}

			},

			// Do not change code below
			errorPlacement : function(error, element){
				error.insertAfter(element.parent());
			}
		});
	});
}

function guardar(){
	$("#messageErrorModal").hide();
	if (nuevo) {
		existNameRol();
	}else{

		doAjaxPostAdd();
	}
}

function getAccionesOpcionesSeleccionadas() {

	listaRolAccionOpcion = [];

	$.each(listaOpciones, function(iOpcion, opcion){
		rolAccionOpcion = {};
		rolAccionOpcionPK = {};

		if(nuevo)
			rolAccionOpcionPK['codigoRol'] = 0;
		else
			rolAccionOpcionPK['codigoRol'] = $("#codigoRol").val();

		rolAccionOpcionPK['codigoOpcion'] = opcion.codigo;
		rolAccionOpcion['rolAccionOpcionPK'] = rolAccionOpcionPK;
		$.each(listaAcciones, function(iAccion, accion){
			rolAccionOpcion[accion] = $('#permisoOpcion' + opcion.codigo + accion).prop('checked') == true ? 'S' : 'N';
		});
		listaRolAccionOpcion.push(rolAccionOpcion);
	});

	return listaRolAccionOpcion;
}

function doAjaxPostAdd() {
	var dataRol = {};
	var metodo = '';
	$("#messageError").hide();
	$("#messageErrorModal").hide();
	if ($("#asignarPermisos").valid()) {
		if($('#nombreRol').val() != ""){
			if (nuevo) {
				metodo = 'crearRolPermisos';
			} else {
				metodo = 'actualizarRolPermisos';
			}
			dataRol['descripcion'] =  $('#nombreRol').val();
			dataRol['codigo'] =  (nuevo == true ? '-1' : $('#codigoRol').val());
			dataRol['estado.cdestado'] =  $('#estadoRol option:selected').val();
			dataRol['estado.dsestado'] = $('#estadoRol option:selected').text();
			$.each(listaOpciones, function(iOpcion, opcion){
				if(nuevo)
					dataRol['rolAccionOpcionList[' + iOpcion + '].rolAccionOpcionPK.codigoRol'] =0;
				else
					dataRol['rolAccionOpcionList[' + iOpcion + '].rolAccionOpcionPK.codigoRol'] =$("#codigoRol").val();
				dataRol['rolAccionOpcionList[' + iOpcion + '].rolAccionOpcionPK.codigoOpcion'] =opcion.codigo;
				$.each(listaAcciones, function(iAccion, accion){
						dataRol['rolAccionOpcionList[' + iAccion + '].'+accion] =$('#permisoOpcion' + opcion.codigo + accion).prop('checked') == true ? 'S' : 'N';
				});
			});
			waitingDialog.show('');
			$.ajax({
				type : "POST",
				url : contexto + "/maestroRoles/" + metodo + '?_csrf=' + _csrfX,
				data : dataRol,
				dataType : "json",
				success : function(res){
					waitingDialog.hide();
					if(res.STATUS == 'SUCCESS'){
						$("#modal-actualizarRol").modal("hide");
						$("#messageExitoso").html("Se guardó el rol exitosamente.")
						$("#messageExitoso").show();
						// codigoActividad = -1;
						mostrarTablaRoles();
						limpiarModal();
						setTimeout("limpiarMensajeExito();", TIEMPOLIMPIARERROR);
					} else {
						$("#modal-actualizarRol").modal("hide");
						$("#messageError").html("No se pudo guardar el rol.")
						$("#messageError").show();
						mostrarTablaRoles();
						setTimeout("limpiarMensajeError();", TIEMPOLIMPIARERROR);
					}
				},
				error : function(e){
					waitingDialog.hide();
					$("#modal-actualizarRol").modal("hide");
					$("#messageError").html("Hubo un error de red durante la transacción.")
					$("#messageError").show();
					mostrarTablaRoles();
					setTimeout("limpiarMensajeError();", TIEMPOLIMPIARERROR);
				}
			});
		} else {
			$("#messageErrorModal").html("El rol debe tener un nombre.")
			$("#messageErrorModal").show();
		}
	}
}

function doAjaxPostUpdate() {
	var data_actividad = "_csrf=" + $("#_csrf").val();
	data_actividad += "&dsactividad=" + $("#nombreActividad").val();
	data_actividad += "&dsdetalle=" + $("#nombreActividad").val();
	data_actividad += "&cdactividad=" + $("#codigoActividad").val();
	data_actividad += "&isactivo=" + "S";

	if($("[name = nombreRol]").length == 1){
		for (var i = 0; i < $("[name = nombreRol]").length; i++){
			data_actividad += "&RolActividadList[" + i + "].dsdetalle=" + $('#nombreRol')[i].value;
		}
		for (var i = 0; i < $("[name = codigoRol]").length; i++){
			data_actividad += "&RolActividadList[" + i + "].cdRol.cdRol=" + $('#codigoRol')[i].value;
			data_actividad += "&RolActividadList[" + i + "].cdRol.cdRol=" + $('#codigoRolActividad')[i].value;

		}
		for (var i = 0; i < $("[name = codigoRolActividad]").length; i++){
			data_actividad += "&RolActividadList[" + i + "].cdRolactividad=" + $('#codigoRolActividad')[i].value;

		}
	}else{
		for (var i = 0; i < $("[name = nombreRol]").length; i++){
			data_actividad += "&RolActividadList[" + i + "].dsdetalle=" + nombreRol[i].value;
		}
		for (var i = 0; i < $("[name = codigoRol]").length; i++){
			data_actividad += "&RolActividadList[" + i + "].cdRol.cdRol=" + codigoRol[i].value;
		}
		for (var i = 0; i < $("[name = codigoRolActividad]").length; i++){
			data_actividad += "&RolActividadList[" + i + "].cdRolactividad=" + codigoRolActividad[i].value;
		}
	}
	if($("[name = detalleRol]").length == 1){
		for (var i = 0; i < $("[name = detalleRol]").length; i++){
			data_actividad += "&RolActividadList[" + i + "].cdRol.dsRol=" + $('#detalleRol')[i].value;
		}
	} else {
		for (var i = 0; i < $("[name = detalleRol]").length; i++){
			data_actividad += "&RolActividadList[" + i + "].cdRol.dsRol=" + detalleRol[i].value;
		}
	}

	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/maestroRoles/guardarRol",
		// data : $("#idFormulario").serialize(),
		data : data_actividad,
		dataType : "json",
		success : function(response){
			waitingDialog.hide();
		},
		error : function(e){
			waitingDialog.hide();
		}
	});

}

function mostrarCampoRol(boton){
	console.log('mostrarCampoRol');
	$('#campoRol').show();
	$("#adicionarRolPrincipal").hide();
}

function clonarCampoRol(boton){
	console.log('clonarCampoRol');
	if($("#frmActividades").valid()){
		var nuevasActividades = boton.parentNode.parentNode;
		var object = nuevasActividades.cloneNode(true);
		var boton = object.getElementsByTagName('a')[0];
		boton.setAttribute("class", "fa fa-minus-square");
		boton.onclick = function borrarFormulario(){
			nuevasActividades.parentNode.removeChild(this.parentNode.parentNode);
		};
		nuevasActividades.parentNode.appendChild(object);
	}

}

// PERMISOS
function setearPermisos() {

	if($("#frmRoles").valid()){
		var rowid = "";

		$("#messageErrorModal").hide();
		if($.fn.dataTable.isDataTable('#tablaPermisos')){
			rowid = $('table#tablaPermisos').dataTable().fnAddData(
					['' + $("#nombreRol").val(),
					// '' + $( "#estadoRol option:selected").val(),
					'' + $("#estadoRol option:selected").text(),
							adherirEditarEliminar(filaRol, "permisos", "editRol", "deleteRol")]);
		}else{
			rowid = $('table#tablaRoles').dataTable({
				"bProcessing" : false,
				"bServerSide" : false,
				"bFilter" : false,
				"bInfo" : false,
				"bSortable" : false,
				"bPaginate" : false
			}).fnAddData(
					['' + $("#nombreRol").val(), '' + $("#estadoRol option:selected").text(),
							adherirEditarEliminar(filaRol, "permisos", "editRol", "deleteRol")]);
		}

		var theNode = $('#tablaRoles').dataTable().fnSettings().aoData[rowid[0]].nTr;
		theNode.setAttribute('id', 'f' + filaRol);

		$('td', theNode)[0].setAttribute('class', 'nombreRol');
		$('td', theNode)[1].setAttribute('class', 'estadoRol');
		$('td', theNode)[2].setAttribute('class', 'accionRol');

		elementoRol = new Object();
		elementoRol.fila = filaRol;
		// elementoRol.codigoRol = $("#codigoRolActividad").val();
		elementoRol.codigoRol = $("#codigoRol").val();
		elementoRol.nombreRol = $("#nombreRol").val();
		// elementoRol.estadoRol = $("#estadoRol").val();
		elementoRol.estadoRol = $("#estadoRol option:selected").text();

		RolesArray[countRoles] = elementoRol;

		filaRol++;
		countRoles++;
		limpiarCamposRoles();
	}
}

function consultarOpcionesAcciones() {
	consultarOpciones();
}

function consultarOpcionesAccionesYRol(codigo){
	consultarOpciones(codigo);
}

function consultarOpciones(codigo) {
	var data = '';

	data = CSRF;

	waitingDialog.show('');
	$.ajax({
		type : 'POST',
		url : contexto + "/permisosOpciones/consultarOpciones",
		data : data,
		success : function(opciones) {
			waitingDialog.hide();
			listaOpciones = opciones['opciones'];
			// Se llama aca y no en consultarOpcionesAcciones() para controlar
			// que se obtengan las 2 consultas
			try {
				consultarAcciones(codigo);
			} catch(e) {
				consultarAcciones(false);
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function consultarAcciones(codigo) {
	var data = '';

	data = CSRF;

	waitingDialog.show('');
	$.ajax({
		type : 'POST',
		url : contexto + "/permisosAcciones/consultarAcciones",
		data : data,
		success : function(acciones){
			waitingDialog.hide();
			listaAcciones = acciones['acciones'];
			llenarOpcionesAcciones();
			if(codigo){
				consultarRolAcciones(codigo, false);
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function consultarAccionesRol(codigo){
	var data = "codigo=" + codigo;

	data += "&_csrf=" + $("#_csrf").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroRoles/consultarAccionesRol",
		data : data,
		success : function(res){
			waitingDialog.hide();
			if(res.STATUS == 'SUCCESS'){
				seleccionarAccionesOpcionesRol(res);
			}else{
				$("#modal-actualizarRol").modal("hide");
				$("#messageError").html("No se pudieron consultar los permisos. Por favor vuelva a intentarlo")
				$("#messageError").show();
				setTimeout("limpiarMensajeError();", TIEMPOLIMPIARERROR);
			}
		},
		error : function(e){
			waitingDialog.hide();
			$("#modal-actualizarRol").modal("hide");
			$("#messageError").html("Hubo un error de red durante la transacción.")
			$("#messageError").show();
			setTimeout("limpiarMensajeError();", TIEMPOLIMPIARERROR);
		}
	});
}

function seleccionarAccionesOpcionesRol(res){
	$.each(res.listaRolAccionOpcion, function(iAccionOpcion, accionOpcion) {
		var countPermisos = 0;
		var countPermisosSeleccionados = 0;
		$.each(accionOpcion, function(indexAccion, valorAccionOpcion) {
			if (indexAccion != 'rolAccionOpcionPK' ) {
				countPermisos++;
				if('S' == valorAccionOpcion.toUpperCase() && $('#permisoOpcion' + accionOpcion.rolAccionOpcionPK.codigoOpcion+ indexAccion).length > 0) {
					$('#permisoOpcion' + accionOpcion.rolAccionOpcionPK.codigoOpcion + indexAccion).prop('checked', true);
					countPermisosSeleccionados++;
				}
			}
		});
		if (countPermisos == countPermisosSeleccionados) {
			try {
				$('#permisoOpcion' + accionOpcion.rolAccionOpcionPK.codigoOpcion).prop('checked', true);
				seleccionarTodosLosPermisos($('#permisoOpcion' + accionOpcion.rolAccionOpcionPK.codigoOpcion)[0]);
			} catch(e) {
				console.log(e);
			}
		}
	});
}

function llenarOpcionesAcciones() {
	var tablaHtml = '';

	/** CREA TITULOS DE TABLA PERMISOS */
	tablaHtml = "<table id='datatablePermisos' class='table table-striped table-hover' >";
	tablaHtml += "<thead>";
	tablaHtml += "<tr class='tr_titulo'>";
	tablaHtml += "<th>Opción</th>";
	$.each(listaAcciones, function(index, accion){
		if (accion == "snEscritura") {	
			tablaHtml += '<th>' + textoSnEscritura + '</th>';	;
		} else {
			if (accion == "snLectura")
				tablaHtml += '<th>' + textoSnLectura + '</th>';
			else 
				tablaHtml += '<th>' + accion.slice(2, accion.length) + '</th>';
		}
		
	});
	tablaHtml += "<th>Todos</th></tr>";
	tablaHtml += "</thead>";
	tablaHtml += "<tbody>";
	var filas = '';
	$.each(listaOpciones, function(index, opcion) {
		var fila = '<tr><td><div class="col-sm-12">' + opcion.nombre + '</div></td>';
		$.each(listaAcciones, function(indexAccion, accion){
			fila += '<td><input name="permisoOpcion' + opcion.codigo + '" type="checkbox" value="' + accion
					+ '" id="permisoOpcion' + opcion.codigo + accion + '"/></td>';
		});
		fila += '<td><div  class="col-sm-2"><input onclick="seleccionarTodosLosPermisos(this)" id="permisoOpcion' + opcion.codigo + '" name="permisoOpcion' + opcion.codigo + '" type="checkbox" value="' + opcion.codigo + '" /></div></td></tr>'
		filas += fila;
	});
	tablaHtml += filas;
	tablaHtml += "</tbody>";
	tablaHtml += "</table>";
	$("#tablaPermisos").html(tablaHtml);
}

function seleccionarTodosLosPermisos(checkbox) {
	if ($(checkbox).is(":checked")) {
		$(checkbox).closest("tr").find(":checkbox").not($(checkbox)).prop("checked", true).attr("disabled", "disabled");
	} else {
		$(checkbox).closest("tr").find(":checkbox").not($(checkbox)).prop("checked", false).removeAttr("disabled");
	}
}

function asignarPermisosRol() {
	var acciones = listaAcciones;
	var data = {};
	var listaOpcionesAcciones = {};

	data["_csrf="] = $("#_csrf").val();

	if($('#frmRoles')){
		data['descripcion'] = $("#nombreRol").val();
		data['estado.cdestado'] = $('#estadoRol').val();
		data['estado.dsestado'] = $('#estadoRol').html();
		// OBTENER FILAS Y GUARDARLAS EN OPCIONESFILAS
		$.each($(opcionesFilas), function(index, opcion){
			var listaAccionPorOpcion = [];

			$.each(acciones, function(indexAccion, accion){
				if($('#' + opcionesFila + accion + ':checked')){
					listaAccionPorOpcion.push(accion);
				}
			});
			listaOpcionesAcciones[opcion] = listaAccionPorOpcion;
		});
		data['opcionesAcciones'] = listaOpcionesAcciones;
	}

	$.each(ver, function(propiedad, elemento){
		alert(propiedad + '&' + elemento);
		$.each(elemento, function(indexValor, valor){
			if(typeof valor == 'object'){
				$.each(valor, function(i, v){
					console.log('* ' + v);
				});
			}else{
				console.log(indexValor + '<>' + valor);
			}
		});
	});
}

// metodo adicionar rol no va, para verificar el nombre disponible de un rol
// metodo verificarSiExisteRol()
function adicionarRol(){

	if($("#frmRoles").valid()){
		var elementoRol = new Object();
		var rowid = "";
		var theNode = "";

		$("#messageErrorModal").hide();
		if($.fn.dataTable.isDataTable('#tablaRoles')){
			rowid = $('table#tablaRoles').dataTable().fnAddData(['' + $("#nombreRol").val(),
			// '' + $( "#estadoRol option:selected").val(),
			'' + $("#estadoRol option:selected").text(), adherirEditarEliminar(filaRol, "rol", "editRol", null)]);
		}else{
			rowid = $('table#tablaRoles').dataTable({
				"bProcessing" : false,
				"bServerSide" : false,
				"bFilter" : false,
				"bInfo" : false,
				"bSortable" : false,
				"bPaginate" : false
			}).fnAddData(
					['' + $("#nombreRol").val(), '' + $("#estadoRol option:selected").text(),
							adherirEditarEliminar(filaRol, "rol", "editRol", null)]);
		}

		theNode = $('#tablaRoles').dataTable().fnSettings().aoData[rowid[0]].nTr;
		theNode.setAttribute('id', 'f' + filaRol);

		$('td', theNode)[0].setAttribute('class', 'nombreRol');
		$('td', theNode)[1].setAttribute('class', 'estadoRol');
		$('td', theNode)[2].setAttribute('class', 'accionRol');

		elementoRol.fila = filaRol;
		// elementoRol.codigoRol = $("#codigoRolActividad").val();
		elementoRol.codigoRol = $("#codigoRol").val();
		elementoRol.nombreRol = $("#nombreRol").val();
		// elementoRol.estadoRol = $("#estadoRol").val();
		elementoRol.estadoRol = $("#estadoRol option:selected").text();

		RolesArray[countRoles] = elementoRol;

		filaRol++;
		countRoles++;
		limpiarCamposRoles();
	}
}

/**
 * function deleteRol(fila){ console.log('deleteRol'); var posicion = -1; for
 * (var i = 0; i < RolesArray.length; i++){ if (RolesArray[i].fila == fila){
 * posicion = i; break; } } RolesArray.splice(posicion, 1);
 * $("table#tablaRoles").dataTable().fnDeleteRow(posicion);
 * 
 * countRoles--; }
 */

function editRol(fila){
	console.log('editRol');
	for (var i = 0; i < RolesArray.length; i++){
		if(RolesArray[i].fila == fila){
			$("#f" + fila + " td.nombreRol").html(
					'<input id="codigoRolActividadEditado' + fila + '" name="codigoRolActividadEditado' + fila
							+ '" type="hidden" class="form-control" required="required" value="'
							+ RolesArray[i].codigoRol + '"/>  ' + '<input id="nombreRolEditado' + fila
							+ '" name="nombreRolEditado' + fila
							+ '" type="text" class="form-control" required="required" value="'
							+ RolesArray[i].nombreRol + '"/>  ');
			$("#f" + fila + " td.detalleRol").html(
					'<input id="detalleRolEditado' + fila + '" name="detalleRolEditado' + fila
							+ '" type="text" class="form-control" required="required" value="'
							+ RolesArray[i].detalleRol + '"/>  ');

			$("#f" + fila + " td.accionRol").html(
					'<a href="javascript:void(0);" onclick="cambiarRol(' + fila + ',' + i
							+ ')" class="btn btn-success btn-circle"><i class="glyphicon glyphicon-ok"></i></a>');
		}
	}

}

function cambiarRol(fila, posicion){
	// if ($("#contactoClienteEditForm").valid()) {
	elementoRol = new Object();
	elementoRol.fila = fila;
	elementoRol.codigoRol = $("#codigoRolActividadEditado" + fila).val();
	elementoRol.nombreRol = $("#nombreRolEditado" + fila).val();
	elementoRol.detalleRol = $("#detalleRolEditado" + fila).val();

	RolesArray[posicion] = elementoRol;

	$("#f" + fila + " td.nombreRol").html($("#nombreRolEditado" + fila).val());
	$("#f" + fila + " td.detalleRol").html($("#detalleRolEditado" + fila).val());
	if(permisoEliminar && permisoEscritura){
		$("#f" + fila + " td.accionRol").html(adherirEditarEliminar(fila, "rol", "editRol", "deleteRol"));
	}else{
		if(permisoEliminar)
			$("#f" + fila + " td.accionRol").html(adherirEditarEliminar(fila, "rol", false, "deleteRol"));
		else if(permisoEscritura)
			$("#f" + fila + " td.accionRol").html(adherirEditarEliminar(fila, "rol", "editRol", false));
	}
}

// REVISAR CON CUIDADO SI ELIMINAR METODO
function eliminarRol(codigo){
	
	/*
	 * if(casoRol == true){$("#modal-NoEliminarRol").modal("show");
	 * 
	 * }else if(casoRol == false){
	 */
	codigoRol = codigo;
	canIsDeleteRol(codigo);
	// }
}

// REVISAR SI ELIMINAR METODO con cuidadito pues
function eliminarRol(boton){
	codigoRol = boton.parentNode.parentNode.children[0].children[2].value;
	eventoBoton = boton;
	// REVISAR VARIABLE GLOBAL casoActividad QUE HACE
	if(casoActividad == true){
		$("#modal-NoEliminarRol").modal("show");

	}else if(casoActividad == false){
		$("#modal-EliminarRol").modal("show");
		codigoActividad = codigoRol;
		idEliminar = boton.parentNode.parentNode.id;
	}
}

function eliminarRolSeguro(){
	console.log('eliminarRolSeguro');
	var data_rol = "cdactividad=" + codigoActividad;
	var msjError = "No se pudo eliminar la actividad.";
	var msjExito = "Se eliminó la actividad exitosamente."

	data_rol += "&_csrf=" + $("#_csrf").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroRoles/eliminarRol",
		data : data_rol,
		success : function(res){
			waitingDialog.hide();
			if(res.STATUS == "SUCCESS"){
				$("#messageExitoso").html(msjExito);
				$("#messageExitoso").show();
				codigoActividad = -1;
				mostrarTablaActividad();
				setTimeout("limpiarMensajeExito();", TIEMPOLIMPIARERROR);
			}else{
				$("#messageError").html(msjError);
				$("#messageError").show();
				mostrarTablaActividad();
				setTimeout("limpiarMensajeError();", TIEMPOLIMPIARERROR);
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

// REVISAR SI ELIMINAR METODO eliminarActividadesSeguro()
// eliminarActividadeSeguro() eliminarRoleSeguro()
function eliminarRolesSeguro(){
	var data_rol = "cdRol=" + codigoRol;

	eliminarDiv(idEliminar);
	data_rol += "&_csrf=" + $("#_csrf").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroRoles/eliminarRol",
		data : data_rol,
		success : function(res){
			waitingDialog.hide();
			if(res.STATUS == "SUCCESS"){
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function eliminarDiv(div){
	var o = document.getElementById(div);
	o.parentNode.removeChild(o);
}

function mostrarModalNuevoRol() {
	nuevo = true;
	$("#estadoRol").val('1');
	$("#modal-actualizarRol").modal("show");
	limpiarModal();
	consultarOpcionesAcciones();
}

function limpiarModal(){
	limpiarCamposRoles();
	$("#nombreActividad").val("");
	$("#messageExitosoModal").hide();
	$("#messageErrorModal").hide();
	codigoActividad = -1;
	var size = RolesArray.length;
	for (var i = 0; i < size; i++){
		RolesArray.splice(0, 1);
		$("table#tablaRoles").dataTable().fnDeleteRow(0);
		countRoles--;
	}

	RolesArray = new Array();
	filaRol = 0;
	countRoles = 0;
}

function limpiarCampos(){
	$("#detalleRol").val("");
	$("#nombreRol").val("");
	$("#descripcionRol").val("");
	$("#codigoRol").val("");
	// $("#codigoRolActividad").val("");
}

function limpiarCamposRoles(){
	$("#codigoRolActividad").val("-1");
	$("#detalleRol").val("");
	$("#nombreRol").val("");
}

function limpiarMensajeError(){
	$("#messageError").hide();
}

function limpiarMensajeExito(){
	$("#messageExitoso").hide();
}
