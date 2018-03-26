var  myDropzone;
var CODIGO_SECRETARIA = 2;

$("#filtroEstadoCaso").change(function() {
	var estado = $("#filtroEstadoCaso").val();
	if (estado != 2){
		  $('#filtroEstadoProcesal').prop('disabled', true);
		  $('#filtroEstadoProcesal').val("");
	}else {
		 $('#filtroEstadoProcesal').prop('disabled',false );
	}
});

function mostrarTabla() {
	var data_decode = "_csrf=" + $("#_csrf").val();
	var tablaHtml= "";
	
	if ($("#tipoCasoSession").val() != "" && $("#cmbTipoDeCaso").val() == "") {
		$.each($("#cmbTipoDeCaso option"), function(index, option) {
			if ($(option).text() == $("#tipoCasoSession").val())
				$(option).prop("selected", true);
		});
	}
	
	if ($("#actividadPrincipalSession").val() != "" && $("#filtroEstadoProcesal").val() == "") {
		$.each($("#filtroEstadoProcesal option"), function(index, option) {
			if ($(option).text() == $("#actividadPrincipalSession").val())
				$(option).prop("selected", true);
		});
	}
	
	if ($("#estadoSession").val() != "" && $("#filtroEstadoCaso").val() == "") {
		$.each($("#filtroEstadoCaso option"), function(index, option) {
			if ($(option).text() == $("#estadoSession").val())
				$(option).prop("selected", true);
		});
	}
	
	if ($("#cmbTipoDeCaso").val() != "")
		data_decode += "&tipoCaso=" + $("#cmbTipoDeCaso").val();
	if ($("#filtroEstadoCaso").val() != "" )
		data_decode += "&estadoCaso=" + $("#filtroEstadoCaso").val();
	if ($("#filtroEstadoProcesal").val() != "" )
		data_decode += "&estadoProcesal=" + $("#filtroEstadoProcesal").val();
	if ($("#nombreCasoFiltro").val())
		data_decode += "&nombreCasoFiltro=" + $("#nombreCasoFiltro").val();
	if ($("#radicadoFiltro").val())
		data_decode += "&radicadoFiltro=" + $("#radicadoFiltro").val();
	
	tablaHtml+="<table id='datatableCasos' class='table table-striped table-hover' >";	
	tablaHtml+="	<thead>";
	tablaHtml+="<tr class='tr_titulo'>";
	tablaHtml+="<th >C&oacute;digo Caso</th>";
	tablaHtml+="<th >Nombre Caso</th>";
	tablaHtml+="<th >Estado del Caso</th>";
	tablaHtml+="<th >Tipo del Caso</th>";
	tablaHtml+="<th >Estado Procesal</th>";
	tablaHtml+="<th >Radicados</th>";
	tablaHtml+="<th >Equipo del caso</th>";
	tablaHtml+="<th >Actividad</th>";
	tablaHtml+="<th hidden='hidden'>&nbsp;</th>";
	tablaHtml+="<th hidden='hidden'>&nbsp;</th>";
	tablaHtml+="<th hidden='hidden'>&nbsp;</th>";
	tablaHtml+="</tr>";
	tablaHtml+="	</thead>";
	tablaHtml+="	<tbody>";
	tablaHtml+="</tbody>";
	tablaHtml+="</table>";
	$("#tablaListado").html(tablaHtml);
	oTable =$('#datatableCasos').dataTable({
		"bProcessing": true,
        "bServerSide": true,
        "bFilter":false,
        "sAjaxSource": contexto + "/caso/mostrarCasos?" + data_decode,
        "aaSorting": [[ 1, "asc" ]],
        "language": {
            "sProcessing":     "Procesando...",
            "sLengthMenu":     "Mostrar _MENU_ registros",
            "sZeroRecords":    "No se encontraron casos para los parámetros de búsqueda ingresados",
            "sEmptyTable":     "No se encontraron casos para los parámetros de búsqueda ingresados",
            "sInfo":           "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
            "sInfoEmpty":      "Mostrando registros del 0 al 0 de un total de 0 registros",
            "sInfoFiltered":   "(filtrado de un total de _MAX_ registros)",
            "sInfoPostFix":    "",
            "sSearch":         "Buscar:",
            "sUrl":            "",
            "sInfoThousands":  ",",
            "sLoadingRecords": "Cargando...",
            "oPaginate": {
                "sFirst":    "Primero",
                "sLast":     "Último",
                "sNext":     "Siguiente",
                "sPrevious": "Anterior"
            },
            "oAria": {
                "sSortAscending":  ": Activar para ordenar la columna de manera ascendente",
                "sSortDescending": ": Activar para ordenar la columna de manera descendente"
            }
        },
        "aoColumns": [
						{ "mDataProp": "codigo", "bSortable": false, "sWidth":"8%" },
						{ "mDataProp": "nombre","bSortable": false, "sWidth":"10%" },
						{ "mDataProp": "estadoCaso","bSortable": false, "sWidth":"8%" },
						{ "mDataProp": "tipoCaso","bSortable": false, "sWidth":"8%" },
						{ "mDataProp": "estadoProcesal","bSortable": false, "sWidth":"8%" },
						{ "mDataProp": "accion","bSortable": false, "sWidth":"8%" },
						{ "mDataProp": "accionEquipo","bSortable": false, "sWidth":"10%" },
						{ "mDataProp": "accionDetalleTarea","bSortable": false, "sWidth":"10%" },
						{ "mDataProp": "detalle", "bSortable": false, "sWidth":"10%", "bVisible": false},
						{ "mDataProp": "detalleEquipoCaso", "bSortable": false, "sWidth":"10%", "bVisible": false},
						{ "mDataProp": "detalleTarea", "bSortable": false, "sWidth":"10%", "bVisible": false}
					],
		"fnServerData": function ( sSource, aoData, fnCallback ) {
            $.ajax( {
                type: 'POST',
                url: contexto + "/caso/mostrarCasos?" + data_decode,
                data: aoData,
                success: fnCallback,
                error : function (e) {
                    alert (e);
                }
            } );
        },
        "fnDrawCallback": function(oSettings) {
	       	$.each($("[name = verDetalle]"), function (index, optionData) {
	    		 optionData.onclick = function () {
	    			 var tr = optionData.parentNode.parentNode.parentNode;
	    			 if (oTable.fnIsOpen(tr)) {
	    		        oTable.fnClose(tr);
	    		        optionData.firstChild.removeAttribute('class', 'glyphicon glyphicon-chevron-up');
	    		        optionData.firstChild.setAttribute("class", "glyphicon glyphicon-chevron-down");
	    		      } else {
	    		        oTable.fnOpen(tr, oTable.fnGetData(tr).detalle, "info_row" );
	    		        optionData.firstChild.removeAttribute('class', "glyphicon glyphicon-chevron-down");
	    		        optionData.firstChild.setAttribute("class", 'glyphicon glyphicon-chevron-up');
	    		      }
	    		 };
	    	});
	       	$.each($("[name = verDetalleEquipoCaso]"), function (index, optionData) {
		   		 optionData.onclick = function () {
		   			 var tr = optionData.parentNode.parentNode.parentNode;
		   			 if (oTable.fnIsOpen(tr)) {
		   		        oTable.fnClose(tr);
		   		        optionData.firstChild.removeAttribute('class', 'glyphicon glyphicon-chevron-up');
		   		        optionData.firstChild.setAttribute("class", "glyphicon glyphicon-chevron-down");
		   		      } else {
		   		        oTable.fnOpen(tr, oTable.fnGetData(tr).detalleEquipoCaso, "info_row" );
		   		        optionData.firstChild.removeAttribute('class', "glyphicon glyphicon-chevron-down");
		   		        optionData.firstChild.setAttribute("class", 'glyphicon glyphicon-chevron-up');
		   		      }
		   		 };
		   	});
	       	$.each($("[name = verDetalleTarea]"), function (index, optionData) {
	      		 optionData.onclick = function () {
	      			 var tr = optionData.parentNode.parentNode.parentNode;
	      			 if (oTable.fnIsOpen(tr)) {
	      		        oTable.fnClose(tr);
	      		        optionData.firstChild.removeAttribute('class', 'glyphicon glyphicon-chevron-up');
	      		        optionData.firstChild.setAttribute("class", "glyphicon glyphicon-chevron-down");
	      		      } else {
	      		        oTable.fnOpen(tr, oTable.fnGetData(tr).detalleTarea, "info_row" );
	      		        optionData.firstChild.removeAttribute('class', "glyphicon glyphicon-chevron-down");
	      		        optionData.firstChild.setAttribute("class", 'glyphicon glyphicon-chevron-up');
	      		      }
	      		 };
	      	});
	    	
			oTable.on('mouseover', 'tr', function () {
			  	this.style.cursor = 'pointer';
			});
			
			oTable.find("tr").each(function(data) {
				var aData = oTable.fnGetData(this); // get datarow
				$(this).find("td:lt(4)").on("click", function() {
					
					if (null != aData){
						if($("#codigoRol").val() != CODIGO_SECRETARIA){
							document.location.href="detalleCaso?codigo=" + aData.codigo;
						}
					}   
				});
			});
        }
	});
}

function limpiarFiltros() {
	$("#cmbTipoDeCaso").val("");
	$("#filtroEstadoCaso").val("");
	$("#filtroEstadoProcesal").val("");
	mostrarTabla();
}

function exportarHojaExcelCasoOffLine(){
	myDropzone.processQueue();
	setTimeout(function(){
		$("#messageExitosoCasoOfLine").hide();
		$("#messageErrorCasoOfLine").hide();
	}, 10000);
}

function ocultarMensaje(){
	$("#messageExitosoCasoOfLine").hide();
	$("#messageErrorCasoOfLine").hide(); 
}

function validateTipoRadicado(dom){
	value = dom.value
	var row = $(dom).closest("tr");
	if(value == 1){
		row.find("#txtRadicadoAsociado").hide();
		row.find("#txtAutoCompleteRadicados").hide();
	}else if(value == 2){
		row.find("#txtRadicadoAsociado").val("").hide();
		row.find("#txtAutoCompleteRadicados").show();
	}else if (value == 3){
		row.find("#txtRadicadoAsociado").show();
		row.find("#txtAutoCompleteRadicados").val("").hide();
	}
	
}

function setRadicadoAutocomplete(){
	
	$.ajax({dataType: "json",
				url: contexto + "/detalleCaso/consultarRadicados",
				success: function (res) {
					document.autocompleteRadicados = res;
					$("#txtAutoCompleteRadicados").autocomplete({
						source : res,
						minLength: 3
					});
					
				},
				error: function(e){
					waitingDialog.hide();
				}
			
	})
	
	;
}
/*Si lo ingresado no esta en el autocomplete debe eliminiar elt exto*/
function validateInputAutocomplete(dom){
	var input = dom.value;
	var radicadosList = document.autocompleteRadicados;
	var isContain = false;
	
	for(var i = 0; i < radicadosList.length; i++){
		if(radicadosList[i] == input){
			isContain =  true;
			break;
		}
	}
	if(!isContain){
		dom.value = "";
	}
	
}
