var listaArchivos = new listaArchivos();



function exportarArchivo(){
	myDropzone.processQueue();
	listaArchivos.listaArchivos();
	$("#modalAsociarArchivo").modal("hide");
	location.reload();
	$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3on exitosamente.");
	$("#messageExitoso").show();
	setTimeout("limpiarMensajeExito();", 10000);
}
function exportarArchivoAbono(){
	myDropzoneAbono.processQueue();
	// listaArchivos.listaArchivos();
	$("#modalAsociarArchivoAbono").modal("hide");
	// location.reload();
	$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3on exitosamente.");
	$("#messageExitoso").show();
	// setTimeout("limpiarMensajeExito();", 10000);
}


function listaArchivos() {
	
	var codigoCaso = null;
	var codigoEquipoCaso = null;
	var codigoMiembro = null;
	
	var listaArchivos = function() {
		var data_decode = "_csrf=" + $("#_csrf").val();
		var tablaHtml="<table id='tablaArchivos' class='table table-striped table-hover' >";
		tablaHtml+="	<thead>";
		tablaHtml+="<tr class='tr_titulo'>";
		tablaHtml+="<th >C\u00F3digo Archivo</th>";
		tablaHtml+="<th >Autor</th>";		
		tablaHtml+="<th >Nombre Archivo</th>";
		tablaHtml+="<th >Descripción</th>";
		tablaHtml+="<th >Fecha de Creaci\u00F3n</th>";
		tablaHtml+="<th >Descarga</th>";
		tablaHtml+="</tr>";
		tablaHtml+="	</thead>";
		tablaHtml+="	<tbody>";
		tablaHtml+="</tbody>";
		tablaHtml+="</table>";
		$("#listadoArchivos").html(tablaHtml);
		oTable =$('#tablaArchivos').dataTable({
			"bProcessing": true,
	        "bServerSide": true,
	        "bFilter":true,
	        "bInfo" : true,
	        "sAjaxSource": contexto + "/listaArchivos/mostrarArchivos?" + data_decode,
	        "aaSorting": [[ 1, "asc" ]],
	        "language": {
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
	                      	{ "mDataProp": "idArchivo", "bSortable": false, "sWidth":"20%" },
	                      	{ "mDataProp": "nombreAutor", "bSortable": false, "sWidth":"20%" },	                      	
							{ "mDataProp": "nombreArchivo","bSortable": false, "sWidth":"20%" },
							{ "mRender": function ( full, type, data) {
								return '<input value="'+data.descripcion+'" type="text" onkeypress="return saveDescripcion(event,this,'+data.idArchivo+')"/>';
							}},
							{ "mDataProp": "fechaCreacion","bSortable": false, "sWidth":"20%" },
							{ "mDataProp": "descargar","bSortable": false, "sWidth":"20%" },
							
						],
						
						
			"fnServerData": function ( sSource, aoData, fnCallback ) {
	            $.ajax( {
	                type: 'POST',
	                url: contexto + "/listaArchivos/mostrarArchivos?" + data_decode,
	                data: aoData,
	                success: fnCallback,
	                error : function (e) {
	                    alert (e);
	                }
	            } );
	        }, 
	        
	        "fnDrawCallback": function(oSettings) {
	        	
	       	 oTable.find("tr").each(function(data) {
	    		 var aData = oTable.fnGetData(this); // get datarow
	 		 $(this).find("td:eq(0),td:eq(1),td:eq(2)").on("click", function() {
	    			 if (null != aData)  // null if we clicked on title row
	    			 {
	    				 window.location.href = "listaArchivos/downloadArchivo?nombreArchivo=" + aData.nombreArchivo;
	    			 }   
	   		 });
	    	 });
	        }
		
			
		});
	};
	
	
	

	
	
	var mostrarModalAsociarArchivo = function() {
		listaArchivos();
		$("#modalAsociarArchivo").modal("show");
	};
	
	function asociarArchivoActividades() {
		myDropzonePrestamo.processQueue();
		listaArchivos.mostrarArchivos();
		location.reload();
		$("#modalAsociarArchivo").modal("hide");
		$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3on exitosamente.");
		$("#messageExitoso").show();
		setTimeout("limpiarMensajeExito();", 10000);
	}

	return {
		listaArchivos : listaArchivos,
		mostrarModalAsociarArchivo : mostrarModalAsociarArchivo
	}
}

function saveDescripcion(event, dom, id){
	console.log(event);
	if(event.keyCode == 13){
		var parameters = "id="+id+"&descripcion="+dom.value;
		$.get( contexto + "/listaArchivos/saveDescripcionArchivo?" + parameters, function( data ) {
			$("#messageExitoso").html("Se modific\u00f3 la informaci\u00f3on exitosamente.");
			$("#messageExitoso").show();
			setTimeout("limpiarMensajeExito();", 10000);
			});
		return false;
	}
	return true;
}
