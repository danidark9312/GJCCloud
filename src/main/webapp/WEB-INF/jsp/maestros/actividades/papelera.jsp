<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
	<a data-toggle="modal"
		class="btn btn-success btn-circle .btn-xs" style="background-color: red; border: 0;"
		onclick="eliminarActividad(' + actividad.getCdactividad() + ')" title="Eliminar">
		<i class="glyphicon glyphicon-trash" style="color: white;"></i>
	</a>
</c:if>
