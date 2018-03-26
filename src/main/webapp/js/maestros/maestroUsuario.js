var contadorClonador = 0;
var codigoUsuario = -1;
var countUsuarios = 0;
var CSRF = "_csrf=" + $("#_csrf").val();
var eventoBoton = "";
var idEliminar = "";
var filaUsuario = 0;
var UsuariosArray = new Array();
var nuevo = true;
var TIEMPOLIMPIARERROR = 10000;
var listaOpciones = '';
var listaAcciones = '';
var correoAntiguo = '';    // Variable que almacena el correo que tenia el usuario al consultarlo por primera vez, para comparar con el que se modifica.
var validoInactivarAbogado = true;
 
function adherirEditarEliminar(objeto, titulo, eventoEdita, eventoElimina) {
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

function mostrarTablaUsuarios() {
	var data = "";
	var tablaHtml = "";

	data = "_csrf=" + $("#_csrf").val();
	data += "&activo=" + $("#cmbFiltroEstado").val() + "&rol=" + $("#cmbFiltroRol").val();

	tablaHtml = "<table id='datatableUsuarios' class='table table-striped table-hover' >";
	tablaHtml += "<thead>";
	tablaHtml += "<tr class='tr_titulo'>";
	tablaHtml += "<th>Identificaci\u00F3n</th>";
	tablaHtml += "<th>Nombres</th>";
	tablaHtml += "<th>Apellidos</th>";		
	tablaHtml += "<th>Correo electrónico</th>";
	//tablaHtml += "<th>Tarjeta profesional</th>";
	tablaHtml += "<th>Rol</th>";
	tablaHtml += "<th>Estado</th>";
	//tablaHtml += "<th>Celular</th>";
	tablaHtml += "<th>Telefono</th>";
	tablaHtml += "</tr>";
	tablaHtml += "</thead>";
	tablaHtml += "<tbody>";
	tablaHtml += "</tbody>";
	tablaHtml += "</table>";
	$("#tablaListadoUsuarios").html(tablaHtml);
	oTable = $('#datatableUsuarios').dataTable({
		"bProcessing" : true,
		"bServerSide" : true,
		"bFilter" : true,
		"bInfo" : false,
		"sAjaxSource" : contexto + "/maestroUsuarios/mostrarUsuarios?" + data,
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
			"mDataProp" : "cedula",
			"bSortable" : false,
			"sWidth" : "20%"
		}, {
			"mDataProp" : "nombres",
			"bSortable" : false,
			"sWidth" : "20%"
		}, {
			"mDataProp" : "apellidos",
			"bSortable" : false,
			"sWidth" : "20%"
		}, {
			"mDataProp" : "correoElectronico",
			"bSortable" : false,
			"sWidth" : "20%"
		}, {
			"mDataProp" : "rol",
			"bSortable" : false,
			"sWidth" : "10%"
		}, /*{
			"mDataProp" : "tarjetaProfesional",
			"bSortable" : false,
			"sWidth" : "5%"
		},*/ {
			"mDataProp" : "estado",
			"bSortable" : false,
			"sWidth" : "5%"
		},/*{
			"mDataProp" : "celular",
			"bSortable" : false,
			"sWidth" : "10%"
		},*/{
			"mDataProp" : "numeroTelefono",
			"bSortable" : false,
			"sWidth" : "10%"
		}
		// TODO
		// {
		// "mDataProp" : "usuario",
		// "bSortable" : false,
		// "sWidth" : "1%"
		// },
		],
		"fnServerData" : function(sSource, aoData, fnCallback){
			waitingDialog.show('');
			$.ajax({
				type : 'POST',
				url : contexto + "/maestroUsuarios/mostrarUsuarios?" + data,
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
			$.each($("[name = verDetalleUsuario]"), function(index, optionData){
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

				$(this).find("td").on("click", function(){
					if (null != aData) {
						consultarUsuario(aData.cedula, true);
					}
				});
			});
		}
	});
}

function consultarEstadoUsuario() {
	var data_usuario = "";
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroUsuarios/consultarEstados",
		data : data_usuario,
		success : function(res){
			waitingDialog.hide();
			if(res.codigo != null){
				// LIMPIAR MODAL ACTUALIZAR ROL, CREAR ROL QUE ES EL MISMO
				limpiarModal();
				codigoTipoCaso = codigo;
				nuevo = false;
				$("#modal-actualizarUsuario").modal("show");

				$('#guardarUsuario').show();

				$("#codigoUsuario").val(res.codigo);
				$("#nombreUsuario").val(res.nombre);

				var selected = $("#estadoUsuario option:selected").map(function(){
					return this.value
				}).get();
				$.each(res.estados, function(index, optionData){
					selected.push(optionData.cdEstado);
				});
				$('#estadoUsuario').val(selected);
				$('#estadoUsuario').trigger("chosen:updated");
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
function consultarPermisos(codigo){
	console.log('consultarPermisos');
	var data = "codigo=" + codigo;

	data += '&' + CSRF;
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/permisosOpciones/consultarOpcionesUsuario",
		data : data,
		success : function(res){
			waitingDialog.hide();
			if(res.codigo != null){
				limpiarModal();
				codigoUsuario = res.codigo;
				nuevo = false;
				$("#modal-actualizarUsuario").modal("show");
				$('#guardarUsuario').show();
				$("#campoUsuario").show();
				limpiarCampos();
				$("#codigoUsuario").val(res.usuario.codigo);
				$("#nombreUsuario").val(res.usuario.descripcion);
				try{
					$("#estadoUsuario").val(res.usuario.estado[0].cdestado);
				}catch(e){
					console.log(e);
				}
				try{
					// TODO Verificar siguiente
					// $.each(res.UsuarioUsuarioList, function(index,
					// optionData){

					// $("#papeleraUsuario").show();
					// $(".codigoUsuario").val(optionData.codigo);
					// $(".nombreUsuario").val(optionData.descripcion);
					// $("#detalleUsuario").val(optionData.estado);
					// adicionarUsuario();

					// REVISAR SI ELIMINAR
					// $("#adicionarUsuarioPrincipal").hide();
					/*
					 * $("#botonAdicionarUsuario").attr("parentNode.parentNode");
					 * var div = document.getElementById('papeleraUsuario'); var
					 * nuevosUsuarios = div.parentNode.parentNode; var object =
					 * nuevosUsuarios.cloneNode(true);
					 * 
					 * object.children[0].children[1].value=optionData.codigo;
					 * object.children[1].children[1].value=optionData.descripcion;
					 * var div = object.getElementsByTagName('a')[0];
					 * nuevosUsuarios.parentNode.appendChild(object); if(index
					 * ==0){ $('#campoUsuario').remove(); }
					 */
					// });
					// $.each(res.usuarioTipoCasoList, function(index,
					// optionData){
					// casoUsuario = true;
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

function consultarEstadoUsuario(){
	var data_usuario = "";
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroUsuarios/consultarEstados",
		data : data_usuario,
		success : function(res){
			waitingDialog.hide();
			if(res.codigo != null){
				// TODO LIMPIAR MODAL ACTUALIZAR ROL, CREAR ROL QUE ES EL MISMO
				limpiarModal();
				codigoTipoCaso = codigo;
				nuevo = false;
				$("#modal-actualizarUsuario").modal("show");

				$('#guardarUsuario').show();

				$("#codigoUsuario").val(res.codigo);
				$("#nombreUsuario").val(res.nombre);

				var selected = $("#estadoUsuario option:selected").map(function(){
					return this.value
				}).get();
				$.each(res.estados, function(index, optionData){
					selected.push(optionData.cdEstado);
				});
				$('#estadoUsuario').val(selected);
				$('#estadoUsuario').trigger("chosen:updated");
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

function consultarUsuario(id, traerListadosAccionesOpciones){
	console.log('consultarUsuario');
	var data = "id=" + id;

	data += "&_csrf=" + $("#_csrf").val();

	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroUsuarios/consultarUsuario",
		data : data,
		success : function(res) {
			waitingDialog.hide();
			if(res.cedula != null){
				limpiarCampos();
				limpiarModal();
				setModalActualizarUsuario(res);
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function validarSePuedeInactivar(select){
	if(select.value == "2" && !validoInactivarAbogado){
		select.value = "1";
		$("#estadoUsuario-error").html("El abogado actualmente pertenece a casos activos, para inactivarlo debe primero reasignar sus casos");
		$("#estadoUsuario-error").show();
		
		setTimeout('$("#estadoUsuario-error").hide();', 10000);
	}
}

function setModalActualizarUsuario(res){
	validoInactivarAbogado = !res.abogadoTieneCasoActivo;
	
	codigoUsuario = res.cedula;
	correoAntiguo = res.correoElectronico;	 
	nuevo = false;
	
	try{

		$("#modal-actualizarUsuario").modal("show");
		$('#guardarUsuario').show();
		$('#apellidoUsuario').val(res.apellidos);//
		$('#cedulaUsuario').val(res.cedula);
		$('#codigoUsuario').val(res.cedula);
		$('#contrasenaUsuario').parent().parent().addClass("hide");
		$('#correoUsuario').val(res.correoElectronico);
		$("#nombreUsuario").val(res.nombres);//
		$('#tarjetaProfesionalUsuario').val(res.tarjetaProfesional);
		$('#celularUsuario').val(res.celular);
		$('#telefonoUsuario').val(res.telefono);
		$('#direccionUsuario').val(res.direccion);
		$("#abreviacionAbogado").val(res.abreviacionAbogado);
//		$("#nacimientoUsuario").val(res.nacimientoUsuario);
		campoNacimientoUsuario = $("#modal-actualizarUsuario").find("input[name=nacimientoUsuario]")[0];
		if (res.nacimientoUsuario) {
			var fechaUsuario = new Date(res.nacimientoUsuario);
			var fechaFormatoUsuario = formatoFecha(fechaUsuario);
			var fechaFormatoAnioUsuario = formatoFechaAnio(fechaUsuario);
			$("#nacimientoUsuario").val(fechaFormatoUsuario);
			$("#nacimientoUsuario").val(fechaFormatoAnioUsuario);
		}
		$("#tipoDocumento").val(res.tipoDocumento);
		

		//Para que se seteen bien los combo box
		$.each($('#estadoUsuario').find('option'), function(index, option) {
			if (res.estado === $(option).text()) {
				$('#estadoUsuario').val($(option).val());	
			}
		});
		
		$.each($('#rolUsuario').find('option'), function(index, option) {
			if (res.rol === $(option).text()) {
				$('#rolUsuario').val($(option).val());	
			}
		});		
		
		bloquearCamposModalActualizarUsuario(['cedulaUsuario', 'codigoUsuario', 'contrasenaUsuario', 'correoUsuario']);
	} catch(e) {
		console.log(e);
	}
}

function bloquearCamposModalActualizarUsuario(camposBloquear){
	camposModal = ['apellidoUsuario', 'cedulaUsuario', 'codigoUsuario', 'contrasenaUsuario', 'correoUsuario',
			'nombreUsuario', 'tarjetaProfesionalUsuario', 'estadoUsuario'];

	$.each(camposModal, function(index, campoModal){
		$('#' + campoModal).prop('disabled', false);
	});
	if(camposBloquear){
		$.each(camposBloquear, function(index, campoBloquear){
			$('#' + campoBloquear).prop('disabled', 'disabled');
		});
	}
}

function existIdUsuario() {
	var data_usuario = "id=" + $("#cedulaUsuario").val();
	data_usuario += "&email=" + $("#correoUsuario").val();
	var MSJERROR = "No se puede guardar el usuario, ya existe un usuario con el mismo documento.";
	var MSJERROREMAIL = "No se puede guardar el usuario, ya existe un usuario con el mismo email.";

	data_usuario += "&_csrf=" + $("#_csrf").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroUsuarios/existUsuario",
		data : data_usuario,
		success : function(res){
			waitingDialog.hide();
			switch (res.STATUS) {
				case "USER_EXIST": 
					$("#messageErrorModal").html(MSJERROR);
					$("#messageErrorModal").show();
					setTimeout("limpiarMensajeError();", 10000);
					break;
				case "EMAIL_EXIST":
					$("#messageErrorModal").html(MSJERROREMAIL);
					$("#messageErrorModal").show();
					setTimeout("limpiarMensajeError();", 10000);
					break;
				default:
					doAjaxPostAdd();
					break;
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

// TODO Revisar si descomentar
/**
 * function canIsDeleteUsuario(codigo){ var data_usuario =
 * "cdusuario.cdusuario=" + codigo; var msjError = "No se puede eliminar el
 * usuario, ya se encuentra asociada a uno o varios usuarios.";
 * 
 * data_usuario += "&_csrf=" + $("#_csrf").val();
 * waitingDialog.show('');$.ajax({ type : "POST", dataType : "json", url :
 * contexto + "/maestroUsuario/canIsDeleteUsuario", data : data_usuario, success :
 * function(res){waitingDialog.hide(); if (res.STATUS == "SUCCESS"){
 * $("#modal-EliminarUsuario").modal("show"); }else{
 * $("#messageError").html(msjError); $("#messageError").show();
 * setTimeout("limpiarMensajeError();", TIEMPOLIMPIARERROR); } } }); }
 */

function buscarUsuario(){
	console.log('buscarUsuario');
	var table = $('#datatableUsuarios').DataTable();
	$('#buscarUsuario').on('keyup', function(){
		table.search(this.value).draw();
	});
}

function rulesForm(){
	console.log('rulesForm');
	$frmActividades = $("#frmUsuarios").each(function(){
		$(this).validate({
			rules : {
				nombreUsuario : {
					required : true
				}
			},
			messages : {
				nombreUsuario : {
					required : 'El nombre del usuario es requerido.'
				}
			},
			errorPlacement : function(error, element){
				error.insertAfter(element.parent());
			}
		});
	});

	$frmNuevasUsuarios = $("#nuevasUsuarios").each(function(){
		$(this).validate({
			rules : {
				nombreUsuario : {
					required : true
				},
				detalleUsuario : {
					required : true
				}
			},
			messages : {
				nombreUsuario : {
					required : 'El nombre es requerido.'
				},
				detalleUsuario : {
					required : 'El detalle es requerido'
				}
			},
			errorPlacement : function(error, element){
				error.insertAfter(element.parent());
			}
		});
	});
}

function guardar(){
	$("#messageErrorModal").hide();
	if (nuevo) {
		existIdUsuario();
	} else {
		doAjaxPostAdd();
	}
}

function getAccionesOpcionesSeleccionadas(){
	console.log('getAccionesOpcionesSeleccionadas');

	listaUsuarioAccionOpcion = [];

	$.each(listaOpciones, function(iOpcion, opcion){
		usuarioAccionOpcion = {};
		usuarioAccionOpcionPK = {};

		if(nuevo)
			usuarioAccionOpcionPK['codigoUsuario'] = 0;
		else
			usuarioAccionOpcionPK['codigoUsuario'] = $("#codigoUsuario").val();

		usuarioAccionOpcionPK['codigoOpcion'] = opcion.codigo;
		usuarioAccionOpcion['usuarioAccionOpcionPK'] = usuarioAccionOpcionPK;
		$.each(listaAcciones, function(iAccion, accion){
			usuarioAccionOpcion[accion++] = $('#permisoAccion' + opcion.codigo + accion).prop('checked') == true ? 'S'
					: 'N';
			usuarioAccionOpcion[accion] = $('#permisoOpcion' + opcion.codigo + accion).prop('checked') == true ? 'S'
					: 'N';
		});
		listaUsuarioAccionOpcion.push(usuarioAccionOpcion);
	});

	return listaUsuarioAccionOpcion;
}

function doAjaxPostAdd() {
//	var data = new Object();
	var data = "";
	

	$("#messageError").hide();
	$("#messageErrorModal").hide();
	if($('#frmUsuarios').valid()) {
		if($('#nombreUsuario').val() != "") {
			
			data += "&usuario.nombre=" +  $('#nombreUsuario').val();
			data += "&usuario.apellido=" +  $('#apellidoUsuario').val();
			data += "&usuario.id=" +  $('#cedulaUsuario').val();
			data += "&usuario.numeroTarjetaProfesional=" +  $('#tarjetaProfesionalUsuario').val();
			data += "&usuario.email=" +  $('#correoUsuario').val();
			data += "&usuario.password=" +  $('#contrasenaUsuario').val();
			data += "&usuario.activo=" +  $('#estadoUsuario option:selected').text();
			data += "&usuario.celular=" +  $('#celularUsuario').val();
			data += "&usuario.numeroTelefono=" +  $('#telefonoUsuario').val();
			data += "&usuario.direccion=" +  $('#direccionUsuario').val();
			data += "&usuario.abreviacionAbogado=" +  $("#abreviacionAbogado").val();
//			data += "&usuario.nacimientoUsuario=" +   $("#nacimientoUsuario").val();			
//			fecha = $("#nacimientoUsuario").val().split("-");
//			data += "&usuario.nacimientoUsuario=" + new Date(fecha[0], fecha[1] - 1, fecha[2]);
			//Se asigna la fecha de nacimiento.
			if($("#nacimientoUsuario").val() == "")
				fecha =  null;
			else
				fecha = $("#nacimientoUsuario").val().split("-");
			if(fecha)
				data += "&usuario.nacimientoUsuario=" + new Date(fecha[0], fecha[1] - 1, fecha[2]);
						
			data += "&usuario.tipoDocumento.codigo=" + $("#tipoDocumento").val()
			  
			data += "&rolesUsuariosPk.codigoRol=" + $('#rolUsuario option:selected').val();
			data += "&rolesUsuariosPk.codigoUsuario=" + $('#cedulaUsuario').val();
			
			data += "&nuevo=" + nuevo + "&correoAntiguo=" + correoAntiguo;
			
/*Envio antigo por medio de un JSON
 * 
 * 			data['usuario'] = {
				nombre : $('#nombreUsuario').val(),
				apellido : $('#apellidoUsuario').val(),
				id : $('#cedulaUsuario').val(),
				numeroTarjetaProfesional : $('#tarjetaProfesionalUsuario').val(),
				email : $('#correoUsuario').val(),
				password : $('#contrasenaUsuario').val(),
				activo : $('#estadoUsuario option:selected').text(),
				celular : $('#celularUsuario').val(),
				numeroTelefono : $('#telefonoUsuario').val(),
				direccion : $('#direccionUsuario').val(),	
				abreviacionAbogado : $("#abreviacionAbogado").val(),
				fechaNacimientoString : $("#nacimientoUsuario").val(),
				tipoDocumento : {
						codigo : $("#tipoDocumento").val()
				}
			};

			data['rolesUsuariosPk'] = {
				codigoRol : $('#rolUsuario option:selected').val(),
				codigoUsuario : $('#cedulaUsuario').val()
			};

			data['nuevo'] = nuevo;
			data['correoAntiguo'] = correoAntiguo;*/

			waitingDialog.show('');
			$.ajax({				
				data : data,
				type : 'POST',
				url : contexto + "/maestroUsuarios" + "/guardarUsuario" + '?_csrf=' + _csrfX,				
				success : function(res) {
					waitingDialog.hide();
					res = JSON.parse(res);
					if(res.STATUS == 'SUCCESS'){
						$("#modal-actualizarUsuario").modal("hide");
						$("#messageExitoso").html("Se guardó el usuario exitosamente.")
						$("#messageExitoso").show();
						mostrarTablaUsuarios();
						limpiarModal();
						setTimeout("limpiarMensajeExito();", TIEMPOLIMPIARERROR);
					} else {
						//Aca se especifica si el usuario ya existe
						if (res.STATUS == 'USER_EXIST') {
							$("#modal-actualizarUsuario").modal("hide");
							$("#messageError").html("El usuario ya existe.")
							$("#messageError").show();
						} else if (res.STATUS == 'EMAIL_EXIST') {
								$("#modal-actualizarUsuario").modal("hide");
								$("#messageError").html("El email ingresado ya ha sido utilizado por otro usuario.")
								$("#messageError").show();
						} else if (res.STATUS != 'EMAIL_EXIST') {
								$("#modal-actualizarUsuario").modal("hide");
								$("#messageError").html("No se pudo guardar el usuario.")
								$("#messageError").show();
								mostrarTablaUsuarios();
								setTimeout("limpiarMensajeError();", TIEMPOLIMPIARERROR);
						} else if (res.STATUS == 'INACTIVO_ERROR') {
							$("#modal-actualizarUsuario").modal("hide");
							$("#messageError").html("El usuario no puede inactivarse ya que existe en un equipo del caso.")
							$("#messageError").show();
						}
							
					}
				},
				error : function(e){
					waitingDialog.hide();
					$("#modal-actualizarUsuario").modal("hide");
					$("#messageError").html("Hubo un error de red durante la transacción.")
					$("#messageError").show();
					mostrarTablaUsuarios();
					setTimeout("limpiarMensajeError();", TIEMPOLIMPIARERROR);
				}
			});
		}else{
			$("#messageErrorModal").html("El usuario debe tener un nombre.")
			$("#messageErrorModal").show();
		}
	}
}

function doAjaxPostUpdate() {
	console.log('doAjaxPostUpdate');
	var data_actividad = "_csrf=" + $("#_csrf").val();
	data_actividad += "&dsactividad=" + $("#nombreActividad").val();
	data_actividad += "&dsdetalle=" + $("#nombreActividad").val();
	data_actividad += "&cdactividad=" + $("#codigoActividad").val();
	data_actividad += "&isactivo=" + "S";

	if ($("[name = nombreUsuario]").length == 1) {
		for (var i = 0; i < $("[name = nombreUsuario]").length; i++) {
			data_actividad += "&UsuarioActividadList[" + i + "].dsdetalle=" + $('#nombreUsuario')[i].value;
		}
		for (var i = 0; i < $("[name = codigoUsuario]").length; i++){
			data_actividad += "&UsuarioActividadList[" + i + "].cdUsuario.cdUsuario=" + $('#codigoUsuario')[i].value;
			data_actividad += "&UsuarioActividadList[" + i + "].cdUsuario.cdUsuario="
					+ $('#codigoUsuarioActividad')[i].value;

		}
		for (var i = 0; i < $("[name = codigoUsuarioActividad]").length; i++){
			data_actividad += "&UsuarioActividadList[" + i + "].cdUsuarioactividad="
					+ $('#codigoUsuarioActividad')[i].value;

		}
	} else {
		for (var i = 0; i < $("[name = nombreUsuario]").length; i++) {
			data_actividad += "&UsuarioActividadList[" + i + "].dsdetalle=" + nombreUsuario[i].value;
		}
		for (var i = 0; i < $("[name = codigoUsuario]").length; i++) {
			data_actividad += "&UsuarioActividadList[" + i + "].cdUsuario.cdUsuario=" + codigoUsuario[i].value;
		}
		for (var i = 0; i < $("[name = codigoUsuarioActividad]").length; i++) {
			data_actividad += "&UsuarioActividadList[" + i + "].cdUsuarioactividad=" + codigoUsuarioActividad[i].value;
		}
	}
	if ($("[name = detalleUsuario]").length == 1) {
		for (var i = 0; i < $("[name = detalleUsuario]").length; i++) {
			data_actividad += "&UsuarioActividadList[" + i + "].cdUsuario.dsUsuario=" + $('#detalleUsuario')[i].value;
		}
	} else {
		for (var i = 0; i < $("[name = detalleUsuario]").length; i++) {
			data_actividad += "&UsuarioActividadList[" + i + "].cdUsuario.dsUsuario=" + detalleUsuario[i].value;
		}
	}

	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/maestroUsuarios/guardarUsuario",
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

function mostrarCampoUsuario(boton){
	console.log('mostrarCampoUsuario');
	$('#campoUsuario').show();
	$("#adicionarUsuarioPrincipal").hide();
}

function clonarCampoUsuario(boton){
	console.log('clonarCampoUsuario');
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
function setearPermisos(){

	if($("#frmUsuarios").valid()){
		var rowid = "";

		$("#messageErrorModal").hide();
		if($.fn.dataTable.isDataTable('#tablaPermisos')){
			rowid = $('table#tablaPermisos').dataTable().fnAddData(
					['' + $("#nombreUsuario").val(), '' + $("#estadoUsuario option:selected").text(),
							adherirEditarEliminar(filaUsuario, "permisos", "editUsuario", "deleteUsuario")]);
		}else{
			rowid = $('table#tablaUsuarios').dataTable({
				"bProcessing" : false,
				"bServerSide" : false,
				"bFilter" : false,
				"bInfo" : false,
				"bSortable" : false,
				"bPaginate" : false
			}).fnAddData(
					['' + $("#nombreUsuario").val(), '' + $("#estadoUsuario option:selected").text(),
							adherirEditarEliminar(filaUsuario, "permisos", "editUsuario", "deleteUsuario")]);
		}

		var theNode = $('#tablaUsuarios').dataTable().fnSettings().aoData[rowid[0]].nTr;
		theNode.setAttribute('id', 'f' + filaUsuario);

		$('td', theNode)[0].setAttribute('class', 'nombreUsuario');
		$('td', theNode)[1].setAttribute('class', 'estadoUsuario');
		$('td', theNode)[2].setAttribute('class', 'accionUsuario');

		elementoUsuario = new Object();
		elementoUsuario.fila = filaUsuario;
		elementoUsuario.codigoUsuario = $("#codigoUsuario").val();
		elementoUsuario.nombreUsuario = $("#nombreUsuario").val();
		elementoUsuario.estadoUsuario = $("#estadoUsuario option:selected").text();

		UsuariosArray[countUsuarios] = elementoUsuario;

		filaUsuario++;
		countUsuarios++;
		limpiarCamposUsuarios();
	}
}

function seleccionarAccionesOpcionesUsuario(res){
	$.each(res.listaUsuarioAccionOpcion, function(iAccionOpcion, accionOpcion){
		seleccionarOpcion = false;

		$.each(accionOpcion,
				function(propiedadAccionOpcion, valorAccionOpcion){
					if(propiedadAccionOpcion != 'usuarioAccionOpcionPK'
							&& 'S' == valorAccionOpcion.toUpperCase()
							&& $('#permisoOpcion' + accionOpcion.usuarioAccionOpcionPK.codigoOpcion
									+ propiedadAccionOpcion).length > 0){
						$('#permisoOpcion' + accionOpcion.usuarioAccionOpcionPK.codigoOpcion + propiedadAccionOpcion)
								.prop('checked', true);
						seleccionarOpcion = true;
					}
				});
		if(seleccionarOpcion){
			try{
				$('#permisoOpcion' + accionOpcion.usuarioAccionOpcionPK.codigoOpcion).prop('checked', true);
			}catch(e){
				console.log(e);
			}
		}
	});
}

function llenarOpcionesAcciones(){
	console.log('llenarOpcionesAcciones');
	var tablaHtml = '';

	/** CREA TITULOS DE TABLA PERMISOS */
	tablaHtml = "<table id='datatablePermisos' class='table table-striped table-hover' >";
	tablaHtml += "<thead>";
	tablaHtml += "<tr class='tr_titulo'>";
	tablaHtml += "<th>Opción</th>";
	$.each(listaAcciones, function(index, accion){
		tablaHtml += '<th>' + accion.slice(2, accion.length) + '</th>';
	});
	tablaHtml += "</tr>";
	tablaHtml += "</thead>";
	tablaHtml += "<tbody>";
	var filas = '';
	$.each(listaOpciones, function(iOpcion, opcion){
		var fila = '<tr><td><div class="col-sm-10">' + opcion.nombre
				+ '</div><div  class="col-sm-2"><input id="permisoOpcion' + opcion.codigo + '" name="permisoOpcion'
				+ opcion.codigo + '" type="checkbox" value="' + opcion.codigo + '" /></div></td>';
		$.each(listaAcciones, function(iAccion, accion){
			fila += '<td><input name="permisoOpcion' + opcion.codigo + '" type="checkbox" value="' + accion
					+ '" id="permisoOpcion' + opcion.codigo + accion + '"/></td>';
		});
		fila += '</tr>'
		filas += fila;
	});
	tablaHtml += filas;
	tablaHtml += "</tbody>";
	tablaHtml += "</table>";
	$("#tablaPermisos").html(tablaHtml);
}

function asignarPermisosUsuario(){
	// TODO var acciones = ['Crear', 'Eliminar', 'Escribir', 'Leer',
	// 'Restaurar'];
	var acciones = listaAcciones;
	var data = {};
	var listaOpcionesAcciones = {};

	data["_csrf="] = $("#_csrf").val();

	if($('#frmUsuarios')){
		data['descripcion'] = $("#nombreUsuario").val();
		data['estado.cdestado'] = $('#estadoUsuario').val();
		data['estado.dsestado'] = $('#estadoUsuario').html();
		// OBTENER FILAS Y GUARDARLAS EN OPCIONESFILAS
		$.each($(opcionesFilas), function(index, opcion){
			var listaAccionPorOpcion = [];

			$.each(acciones, function(indexAccion, accion){
				if($('#' + opcionesFila + accion + ':checked')){
					console.log(opcionesFila + accion + '\t isChecked');
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
				console.log(indexValor + '::' + valor);
				$.each(valor, function(i, v){
					console.log('* ' + v);
				});
			}else{
				console.log(indexValor + '<>' + valor);
			}
		});
	});
}

// TODO metodo adicionar usuario no va, para verificar el nombre disponible de
// un usuario
// metodo verificarSiExisteUsuario()
function adicionarUsuario(){
	console.log('adicionarUsuario');

	if($("#frmUsuarios").valid()){
		var elementoUsuario = new Object();
		var rowid = "";
		var theNode = "";

		$("#messageErrorModal").hide();
		if($.fn.dataTable.isDataTable('#tablaUsuarios')){
			rowid = $('table#tablaUsuarios').dataTable().fnAddData(
					['' + $("#nombreUsuario").val(), '' + $("#estadoUsuario option:selected").text(),
							adherirEditarEliminar(filaUsuario, "usuario", "editUsuario", null)]);
		}else{
			rowid = $('table#tablaUsuarios').dataTable({
				"bProcessing" : false,
				"bServerSide" : false,
				"bFilter" : false,
				"bInfo" : false,
				"bSortable" : false,
				"bPaginate" : false
			}).fnAddData(
					['' + $("#nombreUsuario").val(), '' + $("#estadoUsuario option:selected").text(),
							adherirEditarEliminar(filaUsuario, "usuario", "editUsuario", null)]);
		}

		theNode = $('#tablaUsuarios').dataTable().fnSettings().aoData[rowid[0]].nTr;
		theNode.setAttribute('id', 'f' + filaUsuario);

		$('td', theNode)[0].setAttribute('class', 'nombreUsuario');
		$('td', theNode)[1].setAttribute('class', 'estadoUsuario');
		$('td', theNode)[2].setAttribute('class', 'accionUsuario');

		elementoUsuario.fila = filaUsuario;
		elementoUsuario.codigoUsuario = $("#codigoUsuario").val();
		elementoUsuario.nombreUsuario = $("#nombreUsuario").val();
		elementoUsuario.estadoUsuario = $("#estadoUsuario option:selected").text();

		UsuariosArray[countUsuarios] = elementoUsuario;

		filaUsuario++;
		countUsuarios++;
		limpiarCamposUsuarios();
	}
}

function editUsuario(fila){
	console.log('editUsuario');
	for (var i = 0; i < UsuariosArray.length; i++){
		if(UsuariosArray[i].fila == fila){
			$("#f" + fila + " td.nombreUsuario").html(
					'<input id="codigoUsuarioActividadEditado' + fila + '" name="codigoUsuarioActividadEditado' + fila
							+ '" type="hidden" class="form-contusuario" required="required" value="'
							+ UsuariosArray[i].codigoUsuario + '"/>  ' + '<input id="nombreUsuarioEditado' + fila
							+ '" name="nombreUsuarioEditado' + fila
							+ '" type="text" class="form-contusuario" required="required" value="'
							+ UsuariosArray[i].nombreUsuario + '"/>  ');
			$("#f" + fila + " td.detalleUsuario").html(
					'<input id="detalleUsuarioEditado' + fila + '" name="detalleUsuarioEditado' + fila
							+ '" type="text" class="form-contusuario" required="required" value="'
							+ UsuariosArray[i].detalleUsuario + '"/>  ');

			$("#f" + fila + " td.accionUsuario").html(
					'<a href="javascript:void(0);" onclick="cambiarUsuario(' + fila + ',' + i
							+ ')" class="btn btn-success btn-circle"><i class="glyphicon glyphicon-ok"></i></a>');
		}
	}

}

function cambiarUsuario(fila, posicion){
	console.log('cambiarUsuario');
	elementoUsuario = new Object();
	elementoUsuario.fila = fila;
	elementoUsuario.codigoUsuario = $("#codigoUsuarioActividadEditado" + fila).val();
	elementoUsuario.nombreUsuario = $("#nombreUsuarioEditado" + fila).val();
	elementoUsuario.detalleUsuario = $("#detalleUsuarioEditado" + fila).val();

	UsuariosArray[posicion] = elementoUsuario;

	$("#f" + fila + " td.nombreUsuario").html($("#nombreUsuarioEditado" + fila).val());
	$("#f" + fila + " td.detalleUsuario").html($("#detalleUsuarioEditado" + fila).val());
	if(permisoEliminar && permisoEscritura){
		$("#f" + fila + " td.accionUsuario").html(
				adherirEditarEliminar(fila, "usuario", "editUsuario", "deleteUsuario"));
	}else{
		if(permisoEliminar)
			$("#f" + fila + " td.accionUsuario").html(adherirEditarEliminar(fila, "usuario", false, "deleteUsuario"));
		else if(permisoEscritura)
			$("#f" + fila + " td.accionUsuario").html(adherirEditarEliminar(fila, "usuario", "editUsuario", false));
	}
}

function eliminarDiv(div){
	console.log('eliminarDiv');
	var o = document.getElementById(div);
	o.parentNode.removeChild(o);
}

function mostrarModalNuevoUsuario() {
	nuevo = true;
	$('#contrasenaUsuario').parent().parent().removeClass("hide");
	$("#estadoUsuario").val('1');
	$("#modal-actualizarUsuario").modal("show");
	limpiarModal();
	bloquearCamposModalActualizarUsuario();
}

function limpiarModal(){
	limpiarCamposUsuarios();
//	$("#nombreActividad").val("");
//	$("#messageExitosoModal").hide();
//	$("#messageErrorModal").hide();
//	codigoUsuario = -1;
//	var size = UsuariosArray.length;
//	for (var i = 0; i < size; i++){
//		UsuariosArray.splice(0, 1);
//		$("table#tablaUsuarios").dataTable().fnDeleteRow(0);
//		countUsuarios--;
//	}
//
//	UsuariosArray = new Array();
//	filaUsuario = 0;
//	countUsuarios = 0;
}

function limpiarCampos(){
	console.log('limpiarCampos');
	try{
		$('#apellidoUsuario').val('');
		$('#cedulaUsuario').val('');
		$('#contrasenaUsuario').val('');
		$('#correoUsuario').val('');
		$('#nombreUsuario').val('');
		$('#tarjetaProfesionalUsuario').val('');

		$('#estadoUsuario').val(1);
		$('#rolUsuario').val(1);
	}catch(e){
		console.log('limpiarCampos:\t' + e);
	}
}

function limpiarCamposUsuarios() {
	try {
		$("#frmUsuarios input").val("");
		$("#frmUsuarios #estadoUsuario").val("1");
		$("#frmUsuarios #rolUsuario").val("1");
	} catch(e) {
		console.log('limpiarCamposUsuarios:\t' + e);
	}
}

function limpiarMensajeError(){
	$("#messageError").hide();
	$("#messageErrorModal").hide();
}

function limpiarMensajeExito(){
	console.log('limpiarMensajeExito');
	$("#messageExitoso").hide();
}