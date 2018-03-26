<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div class="row">
		<div class="col-sm-4 b-r">
			<label><spring:message code="label.campo.numeroDespacho" /></label>
		</div>
		<div class="col-sm-8">
			<label><spring:message code="label.campo.nombreFuncionario" /></label>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-4 b-r">
			<input type="text" class="form-control" id="txtnumeroDespacho" name="txtnumeroDespacho" maxlength="30">
		</div>
		<div class="col-sm-8">
			<input type="text" class="form-control" id="txtnombreFuncionario" name="txtnombreFuncionario" maxlength="60">
		</div>
	</div>
</c:if>