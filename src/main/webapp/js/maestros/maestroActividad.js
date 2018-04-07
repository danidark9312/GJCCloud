var contadorClonador = 0;
var codigoActividad = -1;
var codigoTarea = 0;
var casoActividad = false;
var eventoBoton = "";
var idEliminar = "";
var filaTarea = 0;
var tareasArray = new Array();
var countTareas = 0;
var nuevo = true;
permisoEliminar = true;
permisoEscribir = true;
permisoCrear = true;
permisoModificar = true;
var rolesAsignados = new Array();
var TIMEOUTLIMPIAR = 10000;
var inputNombreActividad = $("#nombreActividad").val();

function mostrarTablaActividad(){
	var data_decode = "_csrf=" + $("#_csrf").val() + "&activo=S";
	var tablaHtml = "<table id='datatableActividades' class='table table-striped table-hover' >";

	tablaHtml += "	<thead>";
	tablaHtml += "<tr class='tr_titulo'>";
	tablaHtml += "<th>Código</th>";
	tablaHtml += "<th >Nombre</th>";
	tablaHtml += "<th >Fecha vencimiento obligatoria</th>";
	tablaHtml += "<th >Acción</th>";
	// tablaHtml+="<th hidden='hidden'>&nbsp;</th>";
	tablaHtml += "</tr>";
	tablaHtml += "	</thead>";
	tablaHtml += "	<tbody>";
	tablaHtml += "</tbody>";
	tablaHtml += "</table>";
	$("#tablaListadoActividades").html(tablaHtml);
	oTable = $('#datatableActividades').dataTable({
		"bProcessing" : true,
		"bServerSide" : true,
		"bFilter" : true,
		"bInfo" : false,
		"sAjaxSource" : contexto + "/maestroActividad/mostrarActividades?" + data_decode,
		"language" : {
			"sProcessing" : "Procesando...",
			"sLengthMenu" : "Mostrar _MENU_ registros",
			"sZeroRecords" : "No se encontraron resultados",
			"sEmptyTable" : "Ningún dato disponible en esta tabla",
			"sInfo" : "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
			"sInfoEmpty" : "Mostrando registros del 0 al 0 de un total de 0 registros",
			"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
			"sInfoPostFix" : "",
			"sSearch" : "Buscar:",
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
			"mDataProp" : "codigoActividad",
			"bSortable" : false,
			"sWidth" : "30%"
		},
		 { "mData": "check",
			"mDataProp" : "reqFechaVen",
			"mRender": function ( data, type, full ) {
				return "<input type=\"checkbox\" class=\"custom-control-input\" onchange=\"setValueCheck(this)\" "+(data=="S"?"checked":"")+" >";	
			}
		
		 },
		{
			"mDataProp" : "nombreActividad",
			"bSortable" : false,
			"sWidth" : "30%"
		}, {
			"mDataProp" : (permisoEliminar == true ? "papelera" : ""),
			"bSortable" : false,
			"sWidth" : "20%"
		}
		// { "mDataProp": "codigoActividad","bSortable": false, "bVisible":
		// false }
		],
		"fnServerData" : function(sSource, aoData, fnCallback){
			waitingDialog.show('');
			$.ajax({
				type : 'POST',
				url : contexto + "/maestroActividad/mostrarActividades?" + data_decode,
				data : aoData,
				success : fnCallback,
				error : function(e){
					waitingDialog.hide();
				}
			});
		},
		"fnDrawCallback" : function(oSettings){
			waitingDialog.hide();
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
			$.each($("[name = verDetalleEquipoCaso]"), function(index, optionData){
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
				var aData = oTable.fnGetData(this); // get datarow
				$(this).find("td:lt(2)").on("click", function(){
					if(null != aData){
						consultarActividad(aData.codigoActividad);
					}
				});
			});
		}
	});
}

function consultarActividad(codigo){
	var data_caso = "cdactividad=" + codigo;
	data_caso += "&_csrf=" + $("#_csrf").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroActividad/consultarActividad",
		data : data_caso,
		success : function(res){
			waitingDialog.hide();
			if(res.cdactividad != null){
				limpiarModalNuevaActividad();
				codigoActividad = codigo;
				nuevo = false;
				$("#modal-actualizarActividad").modal("show");

				// $('#actualizarActividad').show();
				$('#guardarActividad').show();
				$("#campoTarea").show();
				limpiarCampos();

				$("#codigoActividad").val(res.cdactividad);
				$("#nombreActividad").val(res.dsactividad);

				$.each(res.tareaActividadList, function(index, optionData){

					// $("#papeleraTarea").show();
					$("#codigoTareaActividad").val(optionData.cdtareaactividad);
					$("#nombreTarea").val(optionData.dstarea);
					$("#detalleTarea").val(optionData.dsdetalle);
					
					if(optionData.snObligatorioFechaVencimiento == "S"){
						$("#chckFechaVencimientoObligatoria").prop("checked",true);
					}else{
						$("#chckFechaVencimientoObligatoria").prop("checked",false);
					}

					adicionarTarea();

					// $("#adicionarTareaPrincipal").hide();

					/*
					 * $("#botonAdicionarTarea").attr("parentNode.parentNode");
					 * var div = document.getElementById('papeleraTarea'); var
					 * nuevasActividades = div.parentNode.parentNode; var object =
					 * nuevasActividades.cloneNode(true);
					 * 
					 * object.children[0].children[1].value=optionData.dstarea;
					 * object.children[1].children[1].value=optionData.dsdetalle;
					 * var div = object.getElementsByTagName('a')[0];
					 * nuevasActividades.parentNode.appendChild(object);
					 * if(index ==0){ $('#campoTarea').remove(); }
					 */
				});
				$.each(res.actividadTipoCasoList, function(index, optionData){
					casoActividad = true;
				});
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function existNameActividad(){
	var data_caso = "dsactividad=" + $("#nombreActividad").val();
	data_caso += "&_csrf=" + $("#_csrf").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroActividad/existNameActividad",
		data : data_caso,
		success : function(res){
			waitingDialog.hide();
			if(res.STATUS == "SUCCESS"){
				if (!inputNombreActividad) {
					$("#messageErrorModal").html(
					"Error, debe digitar un nombre nombre para la actividad.")
					$("#messageErrorModal").show();	
				}
				else{
				$("#messageErrorModal").html(
						"No se puede guardar la actividad, ya existe una actividad con el mismo nombre.")
				$("#messageErrorModal").show();
				}

			}else{
				doAjaxPostAdd();
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function canIsDeleteActividad(codigo){
	var data_caso = "cdactividad.cdactividad=" + codigo;
	data_caso += "&_csrf=" + $("#_csrf").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroTipoCaso/canIsDeleteActividad",
		data : data_caso,
		success : function(res){
			waitingDialog.hide();
			if(res.STATUS == "SUCCESS"){
				$("#modal-EliminarActividad").modal("show");
			}else{
				$("#messageError").html(
						"No se puede eliminar la actividad, ya se encuentra asociada a uno o varios tipos de casos.")
				$("#messageError").show();
				setTimeout("limpiarMensajeError();", TIMEOUTLIMPIAR);
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function buscarActividad(){
	var table = $('#datatableActividades').DataTable();
	$('#buscarActividad').on('keyup', function(){
		table.search(this.value).draw();
	});
}

function rulesForm(){

	$frmActividades = $("#frmActividades").each(function(){
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

	$frmNuevasTareas = $("#nuevasTareas").each(function(){
		$(this).validate({
			rules : {
				nombreTarea : {
					required : true
				},
				detalleTarea : {
					required : true
				}
			},
			// Messages for form validation
			messages : {
				nombreTarea : {
					required : 'El nombre es requerido.'
				},
				detalleTarea : {
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
	if(nuevo){
		existNameActividad();
	}else{
		doAjaxPostAdd();
	}

}

function asignarRolesAlertas(){
	$.each($('input name=[rolesAlerta]'), function(index, rolSeleccionado){
		if($(rolSeleccionado).prop('checked') == true) rolesAsignados.push($(rolSeleccionado).val());
	});
}

//Se hizo el comentario, ya que no se necesitan las alertas en esta seccion
//function getRolesAlertas(data){
//	rolesAsignados = new Array();
//
//	data['diasAntesAlerta'] = $("#confNumeroDias").val();
//	data['diasAlertaPorDia'] = $("#confNumeroAlertas").val();
//
//	data['estadoAlerta'] = $('#alertaEstado').prop('checked') == true ? 'S' : 'N';
//
//	$.each($('[name = rolesAlerta]'), function(index, rolAsignado){
//		if($(rolAsignado).prop('checked')) rolesAsignados.push($(rolAsignado).val());
//	});
//
//	data['rolesAlerta'] = rolesAsignados;
//
//	return data;
//}

function doAjaxPostAdd(){
	var data_actividad = {};
	$("#messageError").hide();
	$("#messageErrorModal").hide();
	if($("#frmActividades").valid()){
		if(tareasArray.length > 0){
			data_actividad['_csrf'] = $("#_csrf").val();

			if(codigoActividad != -1){
				data_actividad['cdactividad'] = codigoActividad;
			}
			data_actividad['dsactividad'] = $("#nombreActividad").val();
			data_actividad['dsdetalle'] = $("#nombreActividad").val();
			data_actividad['isactivo'] = 'S';
//			data_actividad['tareaActividadList'] = [];

//			data_actividad = getRolesAlertas(data_actividad); Se hizo el comentario, ya que no se necesitan las alertas en esta seccion


			for (var i = 0; i < tareasArray.length; i++){
				
				if(tareasArray[i].codigoTarea != -1 && tareasArray[i].codigoTarea > 0){
					data_actividad['tareaActividadList[' + i + '].cdtareaactividad'] = tareasArray[i].codigoTarea;
				}
				if(tareasArray[i].codigoActividad != -1){
					 data_actividad['tareaActividadList[' + i + '].cdactividad.cdactividad'] =
					 codigoActividad;
				}
				
				//
				if(tareasArray[i].detalleTarea != "" && tareasArray[i].nombreTarea != ""){
					 data_actividad['tareaActividadList[' + i + '].dsdetalle'] =tareasArray[i].detalleTarea;
					 data_actividad['tareaActividadList[' + i + '].isactivo'] ="S";
					 data_actividad['tareaActividadList[' + i + '].dstarea'] =tareasArray[i].nombreTarea;
					 data_actividad['tareaActividadList[' + i + '].snObligatorioFechaVencimiento'] =tareasArray[i].snFechaObligatoria;
							 
				}
			}

			waitingDialog.show('');
			$.ajax({
				type : "POST",
				url : contexto + "/maestroActividad/guardarActividad",
				data : data_actividad,
				dataType : "json",
				success : function(res){
					waitingDialog.hide();
					if(res.STATUS == "SUCCESS"){
						$("#modal-actualizarActividad").modal("hide");
						$("#messageExitoso").html("Se guardó la actividad exitosamente.")
						$("#messageExitoso").show();
						codigoActividad = -1;
						mostrarTablaActividad();
						limpiarModalNuevaActividad();
						setTimeout("limpiarMensajeExito();", TIMEOUTLIMPIAR);
					}else{
						$("#modal-actualizarActividad").modal("hide");
						$("#messageError").html("No se pudo guardar la actividad.")
						$("#messageError").show();
						mostrarTablaActividad();
						setTimeout("limpiarMensajeError();", TIMEOUTLIMPIAR);
					}
				},
				error : function(e){
					waitingDialog.hide();
				}
			});
		}else{
			$("#messageErrorModal").html("Debe adicionar al menos una tarea a la actividad.")
			$("#messageErrorModal").show();
		}
	}
}

function doAjaxPostUpdate(){
	var data_actividad = {};
	data_actividad['_csrf'] = $("#_csrf").val();
	data_actividad['dsactividad'] = $("#nombreActividad").val();
	data_actividad['dsdetalle'] = $("#nombreActividad").val();
	data_actividad['cdactividad'] = $("#codigoActividad").val();
	data_actividad['isactivo'] = "S";

	data_actividad['tareaActividadList'] = [];

	if($("[name = nombreTarea]").length == 1){
		for (var i = 0; i < $("[name = nombreTarea]").length; i++){
			data_actividad['tareaActividadList'][i] = {
				dsdetalle : $('#nombreTarea')[i].value
			};
			// data_actividad['tareaActividadList['+ i + ']'] = {dsdetalle:
			// $('#nombreTarea')[i].value};
			// data_actividad['tareaActividadList[' + i + '].dsdetalle'] =
			// $('#nombreTarea')[i].value;
		}
		for (var i = 0; i < $("[name = codigoTarea]").length; i++){
			data_actividad['tareaActividadList'][i] = {
				cdtarea : {
					cdtarea : $('#codigoTarea')[i].value
				}
			};
			// data_actividad['tareaActividadList['+ i + ']'] = {cdtarea:
			// {cdtarea: $('#codigoTarea')[i].value}};
			// data_actividad['tareaActividadList[' + i + '].cdtarea.cdtarea'] =
			// $('#codigoTarea')[i].value;
			data_actividad['tareaActividadList'][i] = {
				cdtarea : {
					cdtarea : $('#codigoTareaActividad'[i]).value
				}
			};
			// data_actividad['tareaActividadList['+ i + ']'] = {cdtarea:
			// {cdtarea: $('#codigoTareaActividad'[i]).value}};
			// data_actividad['tareaActividadList[' + i + '].cdtarea.cdtarea'] =
			// $('#codigoTareaActividad')[i].value;

		}
		for (var i = 0; i < $("[name = codigoTareaActividad]").length; i++){
			data_actividad['tareaActividadList'][i] = {
				cdtareaactividad : $('#codigoTareaActividad')[i].value
			};
			// data_actividad['tareaActividadList['+ i + ']'] =
			// {cdtareaactividad: $('#codigoTareaActividad')[i].value};
			// data_actividad['tareaActividadList[' + i + '].cdtareaactividad']
			// = $('#codigoTareaActividad')[i].value;
		}
	}else{
		for (var i = 0; i < $("[name = nombreTarea]").length; i++){
			data_actividad['tareaActividadList'][i] = {
				dsdetalle : nombreTarea[i].value
			};
			// data_actividad['tareaActividadList['+ i +']'] = {dsdetalle:
			// nombreTarea[i].value};
			// data_actividad['tareaActividadList[' + i + '].dsdetalle'] =
			// nombreTarea[i].value;
		}
		for (var i = 0; i < $("[name = codigoTarea]").length; i++){
			data_actividad['tareaActividadList'][i] = {
				cdtarea : {
					cdtarea : codigoTarea[i].value
				}
			};
			// data_actividad['tareaActividadList['+ i +']'] = {cdtarea:
			// {cdtarea: codigoTarea[i].value}};
			// data_actividad['tareaActividadList[' + i + '].cdtarea.cdtarea'] =
			// codigoTarea[i].value;
		}
		for (var i = 0; i < $("[name = codigoTareaActividad]").length; i++){
			data_actividad['tareaActividadList'][i] = {
				cdtareaactividad : codigoTareaActividad[i].value
			};
			// data_actividad['tareaActividadList['+ i +']'] =
			// {cdtareaactividad: codigoTareaActividad[i].value};
			// data_actividad['tareaActividadList[' + i + '].cdtareaactividad']
			// = codigoTareaActividad[i].value;
		}
	}
	if($("[name = detalleTarea]").length == 1){
		for (var i = 0; i < $("[name = detalleTarea]").length; i++){
			data_actividad['tareaActividadList'][i] = {
				cdtarea : {
					dstarea : $('#detalleTarea')[i].value
				}
			};
			// data_actividad['tareaActividadList[' + i + '].cdtarea.dstarea'] =
			// $('#detalleTarea')[i].value;
		}
	}else{
		for (var i = 0; i < $("[name = detalleTarea]").length; i++){
			data_actividad['tareaActividadList'][i] = {
				cdtarea : {
					dstarea : detalleTarea[i].value
				}
			};
			// data_actividad['tareaActividadList[' + i + '].cdtarea.dstarea'] =
			// detalleTarea[i].value;
		}
	}

	waitingDialog.show('');
	$.ajax({
		type : "POST",
		url : contexto + "/maestroActividad/guardarActividad",
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

function mostrarCampoTarea(boton){
	$('#campoTarea').show();
	$("#adicionarTareaPrincipal").hide();
}

function clonarCampoTarea(boton){

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

function adicionarTarea(){
	if($("#nuevasTareas").valid()){
		$("#messageErrorModal").hide();
		var rowid = "";
		var chckFechaObl = $("#chckFechaVencimientoObligatoria").prop("checked");
		var snFechaObligatoria = chckFechaObl ? "S" : "N";
		var row = ['' + $("#nombreTarea").val(),
			'' + $("#detalleTarea").val(),
			 snFechaObligatoria,
			'<a href="javascript:void(0);" onclick="deleteTarea('
					+ filaTarea
					+ ')"  title="Eliminar Tarea"><i class="glyphicon glyphicon-trash" style="color: red;"></i></a></a>'
					+ '<a href="javascript:void(0);" onclick="editTarea(' + filaTarea
					+ ')" title="Editar  Tarea" ><i class="glyphicon glyphicon-edit"></i></a>'];
		
		if($.fn.dataTable.isDataTable('#tablaTareas')){
			rowid = $('table#tablaTareas').dataTable()
					.fnAddData(row);

		}else{
			rowid = $('table#tablaTareas').dataTable({
						"bProcessing" : false,
						"bServerSide" : false,
						"bFilter" : false,
						"bInfo" : false,
						"bSortable" : false,
						"bPaginate" : false
					})
					.fnAddData(row);

		}

		var theNode = $('#tablaTareas').dataTable().fnSettings().aoData[rowid[0]].nTr;
		theNode.setAttribute('id', 'f' + filaTarea);

		$('td', theNode)[0].setAttribute('class', 'nombreTarea');
		$('td', theNode)[1].setAttribute('class', 'detalleTarea');
		$('td', theNode)[2].setAttribute('class', 'fechaObligatoria');
		$('td', theNode)[3].setAttribute('class', 'accionTarea');

		elementoTarea = new Object();
		elementoTarea.fila = filaTarea;
		elementoTarea.codigoTarea = $("#codigoTareaActividad").val();
		elementoTarea.nombreTarea = $("#nombreTarea").val();
		elementoTarea.detalleTarea = $("#detalleTarea").val();
		elementoTarea.snFechaObligatoria = snFechaObligatoria;

		tareasArray[countTareas] = elementoTarea;

		filaTarea++;
		countTareas++;
		limpiarCamposTareas();
	}
}

function deleteTarea(fila){
	var posicion = -1;
	for (var i = 0; i < tareasArray.length; i++){
		if(tareasArray[i].fila == fila){
			posicion = i;
			break;
		}
	}
	tareasArray.splice(posicion, 1);
	$("table#tablaTareas").dataTable().fnDeleteRow(posicion);

	countTareas--;
}

function editTarea(fila){
	for (var i = 0; i < tareasArray.length; i++){
		if(tareasArray[i].fila == fila){
			$("#f" + fila + " td.nombreTarea").html(
					'<input id="codigoTareaActividadEditado' + fila + '" name="codigoTareaActividadEditado' + fila
							+ '" type="hidden" class="form-control" required="required" value="'
							+ tareasArray[i].codigoTarea + '"/>  ' + '<input id="nombreTareaEditado' + fila
							+ '" name="nombreTareaEditado' + fila
							+ '" type="text" class="form-control" required="required" value="'
							+ tareasArray[i].nombreTarea + '"/>  ');
			$("#f" + fila + " td.detalleTarea").html(
					'<input id="detalleTareaEditado' + fila + '" name="detalleTareaEditado' + fila
							+ '" type="text" class="form-control" required="required" value="'
							+ tareasArray[i].detalleTarea + '"/>  ');
			$("#f" + fila + " td.fechaObligatoria").html(
					'<input id="chkFechaVencimientoObligatoriaEditado' + fila + '" name="chkFechaVencimientoObligatoriaEditado' + fila + "\""
					+(tareasArray[i].snFechaObligatoria=="S"?" checked=true ":"")
					+ '" type="checkbox" class="form-control" />  ');
			

			$("#f" + fila + " td.accionTarea").html(
					'<a href="javascript:void(0);" onclick="cambiarTarea(' + fila + ',' + i
							+ ')" class="btn btn-success btn-circle"><i class="glyphicon glyphicon-ok"></i></a>');
		}
	}

}

function cambiarTarea(fila, posicion){
	elementoTarea = new Object();
	elementoTarea.fila = fila;
	elementoTarea.codigoTarea = $("#codigoTareaActividadEditado" + fila).val();
	elementoTarea.nombreTarea = $("#nombreTareaEditado" + fila).val();
	elementoTarea.detalleTarea = $("#detalleTareaEditado" + fila).val();
	elementoTarea.snFechaObligatoria = $("#chkFechaVencimientoObligatoriaEditado" + fila).prop("checked")==true?"S":"N";

	tareasArray[posicion] = elementoTarea;

	$("#f" + fila + " td.nombreTarea").html($("#nombreTareaEditado" + fila).val());
	$("#f" + fila + " td.detalleTarea").html($("#detalleTareaEditado" + fila).val());
	$("#f" + fila + " td.fechaObligatoria").html(elementoTarea.snFechaObligatoria);
	$("#f" + fila + " td.accionTarea")
			.html(
					'<a href="javascript:void(0);" onclick="deleteTarea('
							+ fila
							+ ')" title="Eliminar Tarea"><i class="glyphicon glyphicon-trash" style="color: white;"></i></a></a>'
							+ '<a href="javascript:void(0);" onclick="editTarea(' + fila
							+ ')" title="Editar Tarea"><i class="glyphicon glyphicon-edit"></i></a>');
}

function eliminarActividad(codigo){
	codigoActividad = codigo;
	canIsDeleteActividad(codigo);
}

function eliminarActividadSeguro(){
	var data_caso = "cdactividad=" + codigoActividad;
	data_caso += "&_csrf=" + $("#_csrf").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroActividad/eliminarActividad",
		data : data_caso,
		success : function(res){
			waitingDialog.hide();
			if(res.STATUS == "SUCCESS"){
				$("#messageExitoso").html("Se eliminó la actividad exitosamente.")
				$("#messageExitoso").show();
				codigoActividad = -1;
				mostrarTablaActividad();
				setTimeout("limpiarMensajeExito();", TIMEOUTLIMPIAR);
			}else{
				$("#messageError").html("No se pudo eliminar la actividad.")
				$("#messageError").show();
				mostrarTablaActividad();
				setTimeout("limpiarMensajeError();", TIMEOUTLIMPIAR);
			}
		},
		error : function(e){
			waitingDialog.hide();
		}
	});
}

function eliminarTareaSeguro(){

	eliminarDiv(idEliminar);
	var data_caso = "cdtarea=" + codigoTarea;
	data_caso += "&_csrf=" + $("#_csrf").val();
	waitingDialog.show('');
	$.ajax({
		type : "POST",
		dataType : "json",
		url : contexto + "/maestroActividad/eliminarTarea",
		data : data_caso,
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

function eliminarTarea(boton){
	codigoTarea = boton.parentNode.parentNode.children[0].children[2].value;
	eventoBoton = boton;
	if(casoActividad == true){
		$("#modal-NoEliminarTarea").modal("show");

	}else if(casoActividad == false){
		$("#modal-EliminarTarea").modal("show");
		codigoActividad = codigoTarea;
		idEliminar = boton.parentNode.parentNode.id;
	}
}

function mostrarModalAlertas(){
	$.fn.bootstrapSwitch.defaults.onText = 'Activo';
	$.fn.bootstrapSwitch.defaults.offText = 'Inactivo';
	$.fn.bootstrapSwitch.defaults.onColor = 'success';
	$('#alertaEstado').bootstrapSwitch();
	$("#modal-actualizarActividad").modal("hide");
	limpiarModalAlertas();
	$("#modalConfAlertas").modal("show");
}

function guardarAlertas(noGuardar){
	$("#modalConfAlertas").modal("hide");
	if(noGuardar)
		limpiarModalAlertas();
	else
		guardarAlertasSeleccionadas();
	$("#modal-actualizarActividad").modal("show");
}

function guardarAlertasSeleccionadas(){
	console.log('guardar alertas seleccionadas');

	// limpiarModalAlertas();
}

function mostrarModalNuvaActividad(){
	nuevo = true;
	$("#modal-actualizarActividad").modal("show");
	limpiarModalNuevaActividad();
}

function limpiarModalAlertas(){
	$('#estadoAlerta').prop('checked', true);
	$('#confNumeroDias').val('');
	$('#confNumeroAlertas').val('');
	$.each($('[name = rolesAlerta]'), function(index, rol){
		$(rol).prop('checked', false);
	});
}

function limpiarModalNuevaActividad(){
	limpiarCamposTareas();
	$("#nombreActividad").val("");
	$("#messageExitosoModal").hide();
	$("#messageErrorModal").hide();
	
	codigoActividad = -1;
	var size = tareasArray.length;
	for (var i = 0; i < size; i++){
		tareasArray.splice(0, 1);
		$("table#tablaTareas").dataTable().fnDeleteRow(0);
		countTareas--;
	}

	tareasArray = new Array();
	filaTarea = 0;
	countTareas = 0;
}

function limpiarCampos(){
	$("#detalleTarea").val("");
	$("#nombreTarea").val("");
	$("#nombreActividad").val("");
	$("#codigoActividad").val("");
	$("#codigoTarea").val("");
	$("#codigoTareaActividad").val("");

}

function limpiarCamposTareas(){
	$("#codigoTareaActividad").val("-1");
	$("#detalleTarea").val("");
	$("#nombreTarea").val("");
	$("#chckFechaVencimientoObligatoria").prop("checked",false);
}

function limpiarMensajeError(){
	$("#messageError").hide();
}

function limpiarMensajeExito(){
	$("#messageExitoso").hide();
}
