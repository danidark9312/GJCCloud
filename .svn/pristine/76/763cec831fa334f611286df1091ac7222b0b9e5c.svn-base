var listaClientes = new listaClientes();
 

function listaClientes() {
	
	var codigoCaso = null;
	var codigoEquipoCaso = null;
	var codigoMiembro = null;
	
	
	//pinta la tabla
	var mostrarTablaListadoCliente = function() {
		var data_decode = "_csrf=" + $("#_csrf").val();
		var tablaHtml= "";
		
		if ($("#filtroNombreCaso").val() != null ) {
			data_decode += "&nombreCasoFiltro=" + $("#filtroNombreCaso").val();
		}
		if ($("#filtroNombreCliente").val() != null ) {
			data_decode += "&nombreCliente=" + $("#filtroNombreCliente").val();
		}

		
		tablaHtml+="<table id='datatableCasos' class='table table-striped table-hover' >";	
		tablaHtml+="	<thead>";
		tablaHtml+="<tr class='tr_titulo'>";
		tablaHtml+="<th >Nombre del cliente</th>";
		tablaHtml+="<th >Cedula</th>";
		tablaHtml+="<th >Tipo de miembro</th>";
		tablaHtml+="<th >Direcci\u00F3n</th>";
		tablaHtml+="<th >Tel\u00E9fono</th>";
		tablaHtml+="<th >Correo</th>";
		tablaHtml+="<th >Nombre del Caso</th>";
		tablaHtml+="<th >Codigo del Caso</th>";
		tablaHtml+="</tr>";
		tablaHtml+="	</thead>";
		tablaHtml+="	<tbody>";
		tablaHtml+="</tbody>";
		tablaHtml+="</table>";
		$("#tablaListado").html(tablaHtml);
		oTable = $('#datatableCasos').dataTable({
			"bProcessing": true,
	        "bServerSide": true,
	        "bFilter":false,
	        "bInfo" : true,
	        "sAjaxSource": contexto + "/listaClientes/mostrarClientes?" + data_decode,
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
							{ "mDataProp": "nombre", "bSortable": false, "sWidth":"10%" },
							{ "mDataProp": "cedula","bSortable": false, "sWidth":"10%" },
							{ "mDataProp": "tipoMiembro","bSortable": false, "sWidth":"10%" },
							{ "mDataProp": "direccion","bSortable": false, "sWidth":"10%" },
							{ "mDataProp": "telefonos","bSortable": false, "sWidth":"10%" },
							{ "mDataProp": "correos","bSortable": false, "sWidth":"10%"},
							{ "mDataProp": "nombreCaso","bSortable": false, "sWidth":"10%"},							
							{ "mDataProp": "codigoCaso","bSortable": false, "sWidth":"10%"},
							{ "mDataProp": "codigoEquipocaso","bSortable": false, "sWidth":"10%", "bVisible": false},
							{ "mDataProp": "codigoMiembro","bSortable": false, "sWidth":"10%", "bVisible": false},
						],
			"fnServerData": function ( sSource, aoData, fnCallback ) {
	            $.ajax( {
	                type: 'POST',
	                url: contexto + "/listaClientes/mostrarClientes?" + data_decode,
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
		    		$(this).find("td").on("click", function() {
		    			if (null != aData){
		    				modificarCliente(aData);    					
		    			}
		    		});
		    	});
	        }
		});
	};
	
	
	//pintar modal
	var modificarCliente = function(info) {
		listaClientes.codigoCaso = info.codigoCaso;
		listaClientes.codigoEquipoCaso = info.codigoEquipocaso;
		listaClientes.codigoMiembro = info.codigoMiembro;
		
		var data_caso = "_csrf=" + $("#_csrf").val();
		data_caso += "&codigo=" + listaClientes.codigoCaso;
		data_caso += "&codigoEquipoCaso=" + listaClientes.codigoEquipoCaso;
		data_caso += "&miembro=" + listaClientes.codigoMiembro;
		waitingDialog.show('');
		$.ajax({
			type : "POST",
			url : contexto + "/caso/consultarEquipoCaso",
			data : data_caso,
			dataType : "json",
			success : function(response) {
				waitingDialog.hide();
				$('#modal-modificarDemandado').modal('show');
				cambiarFormularioModificarMiembro(response.casoEquipoCaso.casoEquipoCasoPK.miembro);
				cargarInfoEquipoCaso(response);
			},
			error : function(e) {
				waitingDialog.hide();
			}
		});
	};
	
	var limpiarFiltros = function() {
		$("#filtroNombreCaso").val("");
		$("#filtroNombreCliente").val("");		
		mostrarTablaListadoCliente();
	}
	
	
	return {
		mostrarTablaListadoCliente : mostrarTablaListadoCliente,
		codigoCaso : codigoCaso,
		codigoEquipoCaso : codigoEquipoCaso,
		codigoMiembro : codigoMiembro,
		limpiarFiltros : limpiarFiltros
	};
	
}