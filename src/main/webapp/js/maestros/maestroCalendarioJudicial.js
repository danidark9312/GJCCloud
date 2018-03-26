var contadorClonador = 0;
var codigoTipoCaso = 0;
var codigoTarea = 0;
var casoActividad = false;
var eventoBoton = "";
var idEliminar = "";
var nuevo = true;
var oTable;
var addedDates = [];

function mostrarTablaCalendarios(){
	
}



function migrarCasosAbogado(){
	$("#messageExitoso").hide();
	$("#messageError").hide();
	if(validarAbogadoDestino() && validarCasosSeleccionado()){
		var param = "idAbogado="+$("#cmbAbogadoDestino").val();
		var checks = $("#datatableCasos input[name=chkReasignar]:checked");
		
		checks.each(function(index,data){
			var data = oTable.fnGetData($(data).closest("tr"));
			var casoEquipoCasoPK = data.casoEquipoCasoPK;
			param+="&casoEquiposCasoPK["+index+"].codigo="+casoEquipoCasoPK.codigo;	
			param+="&casoEquiposCasoPK["+index+"].codigoEquipoCaso="+casoEquipoCasoPK.codigoEquipoCaso;	
			param+="&casoEquiposCasoPK["+index+"].miembro="+casoEquipoCasoPK.miembro;	
		});
		
		waitingDialog.show('');$.ajax({
			  url: contexto + "/caso/asignarCasosNuevoResponsable",
			  method : 'POST',
			  dataType : 'json',
			  data: param,
			  success: function (response) {
				  waitingDialog.hide();
				  $("#messageExitoso").show().html(
						  "Se modificaron exitosamente "+response.casosModificados+" casos<br/>" +
						  (response.casosError==0?"Ningun Caso produjó errores":response.casosError+" casos con errores"));
				  mostrarTablaCasosAbogado();
			  },
			  error: function(e){
				  waitingDialog.hide();
				}
		});
		
		
	}
}

function startDatepicker(){
	$("#datepickerDiv").datepicker({
		numberOfMonths : 2,
		changeYear: true,
		dateFormat: 'yy-mm-dd',
		monthNames: [ "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" ],
		onChangeMonthYear : function(year, month, dp){
			if(dp.currentYear != year){
				dp.currentYear = year;
			}
		},beforeShowDay : DisableAddedDates

	}).currentYear = new Date().getFullYear();
	
}

function startDatatable(){
	$("#tablaListadoCalendarios").dataTable({
		"language" : {
			"sZeroRecords" : "No se encontraron resultados",
			"sEmptyTable" : "No se encontraron resultados",
			"sInfoFiltered" : "(filtrado de un total de _MAX_ registros)",
			"sInfoPostFix" : "",
			"sSearch" : "Buscar:",
			"sUrl" : "",
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
		bInfo : false,
		"aoColumns": [
            { "mData": "fecha",sWidth : "90%",
            	"mRender": function ( fecha, type, full ) {
            		return formatFecha(fecha);
		      }	 },
            { "mData": null,
				"mRender": function ( data, type, full ) {
			        return "<a href='javascript:void(0)' onclick=\'deleteFecha($(this))\'><i class=\"glyphicon glyphicon-trash\" style=\"color: red;\"></i></a>";
			      }	
			}
        ],
        bLengthChange : false,
        "sScrollY": "200px",
        "bPaginate": false,
        "bScrollCollapse": true,
        bSort : false,
        "aaSorting": [[ 0, "desc" ]]
	});
	
}



function deleteFecha(dom){
	var tr = dom.closest("tr");
	var data = $("#tablaListadoCalendarios").dataTable().fnGetData(tr);
	var date = data.fecha;
	addedDates.splice( $.inArray(date.getTime(), addedDates), 1 );
	$("#tablaListadoCalendarios").dataTable().fnDeleteRow(tr);
	deleteDataDB(date)
	refreshDatePicker();
}

function addFecha(){
	var fecha = $("#datepickerDiv").datepicker().val();
	var dateFecha = getDate(fecha);
	$("#tablaListadoCalendarios").dataTable().fnAddData({
		fecha : dateFecha 
	});
	addDataDB(dateFecha);
	sortTable();
	addedDates.push(dateFecha.getTime());
	refreshDatePicker();

}

function sortTable(){
	var table = $("#tablaListadoCalendarios").dataTable();
	var data = table.fnGetData();
	data.sort(function(a, b){
		return a.fecha.getTime()-b.fecha.getTime();
		});	
	table.fnClearTable();
	table.fnAddData(data);
}

/*Format date dd/MM/yyyy*/
function formatFecha(date){
	if(!date)
		return "";
	
   var formattedDate = ('0' + date.getDate()).slice(-2);
   var formattedMonth = ('0' + (date.getMonth() + 1)).slice(-2);
   var formattedYear = date.getFullYear();
   return formattedDate + '/' + formattedMonth+ '/' + formattedYear;
}

function getDate(strDate){
	var dateSplit = strDate.split("-");
	return new Date(dateSplit[0],dateSplit[1]-1,dateSplit[2]);
}

function refreshDatePicker(){
	$( "#datepickerDiv" ).datepicker("refresh");
}


function DisableAddedDates(date){
	 
	 if ($.inArray(date.getTime(), addedDates) != -1 ) {
		 return [false];
		 } 
	 return [true];
}


function addDataDB(date){
	$.get( contexto+"/maestro/saveFechaCalendarioJudicial?fecha="+formatFecha(date));
}
function deleteDataDB(date){
	$.get( contexto+"/maestro/deleteFechaCalendarioJudicial?fecha="+formatFecha(date));
}
function findAllDates(date){
	$.getJSON( contexto+"/maestro/findAllCalendarioJudicial",function(data){
		$.each(data.fechas,function(index, strDate){
			var date = getDate(strDate);
			addedDates.push(date.getTime());
			$("#tablaListadoCalendarios").dataTable().fnAddData({
				fecha : date 
			});
		});
		sortTable();
		refreshDatePicker();
		
	});
}


