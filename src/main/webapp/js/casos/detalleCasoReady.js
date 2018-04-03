var myDropzone = null;
var myDropzonePrestamo = null;

$(document).ready(
		function() {
			$("#alertErrorEliminacionMiembro").hide()
			try {
				waitingDialog.hide();
			} catch(e){
				console.log(e);
			}
			
			enableSortPendientesActividades()
			findAllDisabledDates();
			getActividadesInfo();
			var cdusuarioLogeado = $("#idusercreacion").val();
			
			var token = $("#_csrf").val();

			// $(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
			
			ocultarMensajesError();
			
			_setearTextos();

			myDropzone = new Dropzone("#dropzone_asociarArchivos",
					{ 	
						url : contexto + "/fileUpload/asociarArchivo?"+"_csrf="+token+"&cdusuarioLogeado="+cdusuarioLogeado,
						dictDefaultMessage : "Para subir arrastre un archivo u oprima ",
						addRemoveLinks : true,
						dictRemoveFile : 'Remover',
//	 					acceptedFiles: ".xls,.xlsx",
						maxFilesize : 30,
						maxFiles : 1,
						parallelUploads: 1,
						autoProcessQueue : false,
							success: function (file,response) {
								myDropzone.removeAllFiles();
								listarArchivos();
								$("#messageExitosoCasoOfLine").html("Se guard\u00f3 la asociación del archivo.");
								$("#messageExitosoCasoOfLine").show(); 	
								
						},
					});
	    	

			myDropzone.on("sending", function(file, xhr, formData) {
				formData.append("codigoTarea",codigoTareaArchivo);
			});
			
			
			myDropzoneAbono = new Dropzone("#dropzone_asociarArchivoAbono",
					{ 	
				url : contexto + "/fileUpload/asociarArchivoAbono?"+"_csrf="+token+"&cdusuarioLogeado="+cdusuarioLogeado,
				dictDefaultMessage : "Para subir arrastre un archivo u oprima ",
				addRemoveLinks : true,
				dictRemoveFile : 'Remover',
//	 					acceptedFiles: ".xls,.xlsx",
				maxFilesize : 30,
				maxFiles : 1,
				parallelUploads: 1,
				autoProcessQueue : false,
				 maxfilesexceeded: function(file) {
				        this.removeAllFiles();
				        this.addFile(file);
				    }
				 ,
				success: function (file,response) {
					myDropzoneAbono.removeAllFiles();
					// listarArchivos();
					$("#messageExitosoCasoOfLine").html("Se guard\u00f3 la asociación del archivo.");
					$("#messageExitosoCasoOfLine").show(); 	
					
				},
					});
			
			
			myDropzoneAbono.on("sending", function(file, xhr, formData) {
				formData.append("codigoAbono",$("#dropzoneCodigoAbono").val());
			});
	    	
			
			
			
			myDropzonePrestamo = new Dropzone("#dropzone_asociarArchivosPrestamo",
					{ 	
						url : contexto + "/fileUpload/asociarArchivoPrestamo?"+"_csrf="+token+"&cdusuarioLogeado="+cdusuarioLogeado,
						dictDefaultMessage : "Para subir arrastre un archivo u oprima ",
						addRemoveLinks : true,
						dictRemoveFile : 'Remover',
//	 					acceptedFiles: ".xls,.xlsx",
						maxFilesize : 30,
						maxFiles : 1,
						parallelUploads: 1,
						autoProcessQueue : false,
						success: function (file,response) {
								listarArchivosPrestamo();
								myDropzonePrestamo.removeAllFiles();
								$("#messageExitosoCasoOfLine").html("Se guard\u00f3 la asociación del archivo.");
								$("#messageExitosoCasoOfLine").show(); 	
								
						},
					});
	    	

			myDropzonePrestamo.on("sending", function(file, xhr, formData) {
				formData.append("codigoPrestamo",codigoPrestamoArchivo);
			});
			
			
//			dropZoneArchivo = _getDropZone('dropzone_asociarArchivos', '/fileUpload/asociarArchivo', 'codigoTarea',
//					codigoTareaArchivo, cdusuarioLogeado, token);
//			dropZonePrestamo = _getDropZone('dropzone_asociarArchivosPrestamo', '/fileUpload/asociarArchivoPrestamo',
//					'codigoPrestamo', codigoPrestamoArchivo, cdusuarioLogeado, token);

			$("#input-4").fileinput({
				showCaption : false
			});

			urlConsultaCliente = _setearDetalleCaso();
			session.tiempo(session.tiempoSesion);
		});

//function asociarArchivo(stringDropZone) {
//	dropzone = ((stringDropZone == 'dropZoneArchivo') ? dropZoneArchivo : stringDropZone);
//	dropzone = ((stringDropZone == 'dropZonePrestamo') ? dropZonePrestamo : stringDropZone);
//	dropzone.processQueue();
//	setTimeout(function(){
//		$("#messageExitosoCasoOfLine").hide();
//		$("#messageExitosoCasoOfLine").hide();
//	}, 10000);
//}

function _getDropZone(idDropzone, metodoBackEnd, formDataCodigo, formDataArchivo, usuario, token){
	myDropzone = new Dropzone('#' + idDropzone, {
		url : contexto + metodoBackEnd + "?_csrf=" + token + "&cdusuarioLogeado=" + usuario,
		dictDefaultMessage : "Para subir arrastre un archivo u oprima ",
		addRemoveLinks : true,
		dictRemoveFile : 'Remover',
		// acceptedFiles: ".xls,.xlsx",
		maxFilesize : 30,
		maxFiles : 1,
		parallelUploads : 1,
		autoProcessQueue : false,
		init : function() {
			this.on("maxfilesexceeded", function(file) {
				this.removeAllFiles();
				this.addFile(file);
			});
		},
		success : function(file, response){
			myDropzone.removeAllFiles();
			listarArchivos();
			$("#messageExitosoCasoOfLine").html("Se guard\u00f3 la asociación del archivo.");
			$("#messageExitosoCasoOfLine").show();
		}
	});

	myDropzone.on("sending", function(file, xhr, formData){
		formData.append(formDataCodigo, formDataArchivo);
	});
	
	return myDropzone;
}

function _getDropZone2() {
	myDropzonePrestamo = new Dropzone("#dropzone_asociarArchivosPrestamo", {
		url : contexto + "/fileUpload/asociarArchivoPrestamo?" + "_csrf=" + token + "&cdusuarioLogeado="
				+ cdusuarioLogeado,
		dictDefaultMessage : "Para subir arrastre un archivo o click acá ",
		addRemoveLinks : true,
		dictRemoveFile : 'Remover',
		// acceptedFiles: ".xls,.xlsx",
		maxFilesize : 30,
		maxFiles : 1,
		parallelUploads : 1,
		autoProcessQueue : false,
		init : function(){
			this.on("maxfilesexceeded", function(file){
				this.removeAllFiles();
				this.addFile(file);
			});
		},
		success : function(file, response){
			listarArchivosPrestamo();
			myDropzonePrestamo.removeAllFiles();
			$("#messageExitosoCasoOfLine").html("Se guard\u00f3 la asociación del archivo.");
			$("#messageExitosoCasoOfLine").show();
		}
	});

	myDropzonePrestamo.on("sending", function(file, xhr, formData){
		formData.append("codigoPrestamo", codigoPrestamoArchivo);
	});
}

function _setearDetalleCaso() {
	cargarCombox();
	cargarTipoBienesAfectados();
	cargarformularioModificarMiembros();
	if($("#codigoParam").val() != ""){
		consultarCaso($("#codigoParam").val());
		mostrarTablaPrestamo($("#codigoParam").val());
		mostrarTablaRadicado($("#codigoParam").val());
		mostrarTablaBienesAfectados($("#codigoParam").val());
	}
	if($("#actMiembroUpdate").val() != ""){
		$("#tab-1").removeClass("active");
		$("#tab-2").addClass("active");
		$("li:has(a[href=#tab-1])").removeClass("active");
		$("li:has(a[href=#tab-2])").addClass("active");
		$("#alertSucessMiembro").removeClass("hide");
		$("#divMensajeSuccessMiembro").text("Se actualiz\u00F3 el miembro del equipo correctamente");
		setTimeout(function(){
			$("#alertSucessMiembro").addClass("hide");
			$("#divMensajeSuccessMiembro").text("");
		}, 10000);
	}
	if($("#actMiembro").val() != ""){
		$("#tab-1").removeClass("active");
		$("#tab-2").addClass("active");
		$("li:has(a[href=#tab-1])").removeClass("active");
		$("li:has(a[href=#tab-2])").addClass("active");
		$("#alertSucessMiembro").removeClass("hide");
		$("#divMensajeSuccessMiembro").text("Se elimin\u00F3 el miembro del equipo correctamente");
		setTimeout(function(){
			$("#alertSucessMiembro").addClass("hide");
			$("#divMensajeSuccessMiembro").text("");
		}, 10000);
	}
	if($("#addMiembro").val() != ""){
		$("#tab-1").removeClass("active");
		$("#tab-2").addClass("active");
		$("li:has(a[href=#tab-1])").removeClass("active");
		$("li:has(a[href=#tab-2])").addClass("active");
		$("#alertSucessMiembro").removeClass("hide");
		$("#divMensajeSuccessMiembro").text("Se adicion\u00F3 el miembro del equipo correctamente");
		setTimeout(function(){
			$("#alertSucessMiembro").addClass("hide");
			$("#divMensajeSuccessMiembro").text("");
		}, 10000);
	}
	if($("#actActividad").val() != ""){
		$("#tab-1").removeClass("active");
		$("#tab-3").addClass("active");
		$("li:has(a[href=#tab-1])").removeClass("active");
		$("li:has(a[href=#tab-3])").addClass("active");
		$("#alertSucessActividad").removeClass("hide");
		$("#divMensajeSuccessActividad").text("Se elimin\u00F3 la actividad correctamente");
		setTimeout(function(){
			$("#alertSucessActividad").addClass("hide");
			$("#divMensajeSuccessActividad").text("");
		}, 10000);
	}
	
	if($("#addActividad").val() != ""){
		$("#tab-1").removeClass("active");
		$("#tab-3").addClass("active");
		$("li:has(a[href=#tab-1])").removeClass("active");
		$("li:has(a[href=#tab-3])").addClass("active");
		$("#alertSucessActividad").removeClass("hide");
		$("#divMensajeSuccessActividad").text("Se actualizaron las actividades correctamente");
		setTimeout(function(){
			$("#alertSucessActividad").addClass("hide");
			$("#divMensajeSuccessActividad").text("");
		}, 10000);
	}

	if($("#actTarea").val() != ""){
		$("#tab-1").removeClass("active");
		$("#tab-3").addClass("active");
		$("li:has(a[href=#tab-1])").removeClass("active");
		$("li:has(a[href=#tab-3])").addClass("active");
		$("#alertSucessActividad").removeClass("hide");
		$("#divMensajeSuccessActividad").text("Se elimin\u00F3 la tarea correctamente");
		setTimeout(function(){
			$("#alertSucessActividad").addClass("hide");
			$("#divMensajeSuccessActividad").text("");
		}, 10000);
	}
	
	if($("#actConfidencial").val() != "") {
		$("#tab-1").removeClass("active");
		$("#tab-4").addClass("active");
		$("li:has(a[href=#tab-1])").removeClass("active");
		$("li:has(a[href=#tab-4])").addClass("active");
		$("#alertSucessArchivoPrestamo").removeClass("hide");
		$("#divMensajeSuccessArchivoPrestamo").text("Se adjunto el archivo correctamente");
		setTimeout(function(){
			$("#alertSucessArchivoPrestamo").addClass("hide");
			$("#divMensajeSuccessArchivoPrestamo").text("");
		}, 10000);
	}
	
	if($("#actArchivoActividad").val() != "") {
		$("#tab-1").removeClass("active");
		$("#tab-3").addClass("active");
		$("li:has(a[href=#tab-1])").removeClass("active");
		$("li:has(a[href=#tab-3])").addClass("active");
		$("#alertSucessActividad").removeClass("hide");
		$("#divMensajeSuccessActividad2").text("Se adjunto el archivo a la tarea correctamente");
		setTimeout(function(){
			$("#alertSucessActividad").addClass("hide");
			$("#divMensajeSuccessActividad2").text("");
		}, 10000);
	}
	
	if($("#redirectActividad").val() != "") {
		$("#tab-1").removeClass("active");
		$("#tab-3").addClass("active");
		$("li:has(a[href=#tab-1])").removeClass("active");
		$("li:has(a[href=#tab-3])").addClass("active");
//		$("#alertSucessActividad").removeClass("hide");
//		$("#divMensajeSuccessActividad2").text("Se adjunto el archivo a la tarea correctamente");
//		setTimeout(function(){
//			$("#alertSucessActividad").addClass("hide");
//			$("#divMensajeSuccessActividad2").text("");
//		}, 10000);
	}
	
	cargarformularioRadicado();
	$("#alertErrorEliminacionMiembro").hide();
	$("#alertErrorNuevoResponsable").hide();
	obtenerTipoMiembroDetalleCaso("tipoDeMiembro");
//	obtenerTipoMiembroDetalleCaso("tipoDeMiembro");
//	cargarPaises("paisMiembroVictimaDemandante");
//	obtenerParentesco("parentescoMiembro");
//	obtenerClaseBien("cmbTipoBien");
	// WinMove();
	return '';
}

function _setearTextos(){
	$("#txtSaldoPrestamoModal").number(true, 0, '', '.');
	$('#txtInteresesModal').number(true, 0, '', '.');
	$('#txtValorPrestamoModal').number(true, 0, '', '.');
	$('#txtPorcentajeInteresPrestamoModal').number(true, 2, ',', '.');

	$("#txtValorPrestamoModalAdicionar").number(true, 0, '', '.');
	
	$('#txtInteresesModalAdicionar').number(true, 0, '', '.');
	$('#txtSaldoPrestamoModalAdicionar').number(true, 0, '', '.');
	$('#txtPorcentajeInteresPrestamoModalAdicionar').number(true, 2, ',', '.');
	
	/*Abono Capital*/
	
	$("#txtAbonoCapital").number(true, 0, '', '.');
	$("#txtAbonoIntereses").number(true, 0, '', '.');
	$("#txtValorPrestamoModalAdicionarAbono").number(true, 0, '', '.');
	
	$("#txtValorSaldoCapital").number(true, 0, '', '.');
	$("#txtValorSaldoInteres").number(true, 0, '', '.');
	
	$("#txtSaldoTotal").number(true, 0, '', '.');
	$("#txtTotalPagado").number(true, 0, '', '.');
	
	
	

	$("select[name^='responsable']").select2({
		placeholder: "Seleccione un responsable",
	    allowClear: true,
	    width: "100%"
	});
	
	$("select[name^='responsable']").select2({
		placeholder: "Seleccione un responsable",
	    allowClear: true,
	    width: "100%"	
	});
	
	$("select[name^='responsable']").select2({
		placeholder: "Seleccione un responsable",
	    allowClear: true,
	    width: "100%"}).on("select2-selecting", function (select) {
			optionOtros=select.object.element[0];
			if (select.val == -1) {
				$("#modal-ingresarOtroResponsableDetalleCaso").modal("show");
			} else {
				codigosResponsablesNuevoCaso.push(select.val);
			}
	    });
	
	$("#tab-3 .select2-container").addClass("select2-offscreen");
}


function enableSortPendientesActividades(){
	 $( "#sortedPanels" ).sortable({
		 stop : function( event, ui ){
			 var data = "";
			$("#sortedPanels>div").each(function(index,div){
				if(index == 0){
					$("#estadoProcesal").text($(div).find("label[name=nombreActividad]").text());
				}
				data+="&listaActividadesCaso["+index+"].codigoActividadParticular="+$(div).attr("code");
				data+="&listaActividadesCaso["+index+"].orden="+index;
				// console.log($(data).attr("order"));
			});
			$.get(contexto+"/caso/updateActividadesCasoOrden?"+data,function(data){
				console.log(data);
			});
		 }
	 });
	  $("#sortedPanels" ).disableSelection();
	  
}
