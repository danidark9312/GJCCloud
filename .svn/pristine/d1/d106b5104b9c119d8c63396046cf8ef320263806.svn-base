$( "#cmbEstudio" ).change(function() {
	$("#nombreEstudio").html($( "#cmbEstudio option:selected" ).text());
	alert ($( "#cmbEstudio option:selected" ).text());
	var data_cliente = "codigo=" + $( "#cmbEstudio" ).val();
	data_cliente += "&_csrf=" + $("#_csrf").val();
	waitingDialog.show('');$.ajax({
		type : "POST",
		url : contexto + "/estudio/getInfoEstudioHomeAdmin",
		data : data_cliente,
		dataType : "json",
		success : function(response) {
			waitingDialog.hide();
			$("#cantidadEmpleadosAsociados").html(response.cantidadUsuarios);
		},
		error : function(rs, e) {
			waitingDialog.hide();
			$("#alertaErrorCliente").show('slow');
		}
	});
});

$("#openModalUsuarioEstudio").click(function(){
	mostrarTablaUsuarioEstudio();
});