<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div class="row">
		<div class="col-sm-12 b-r">
			<label><spring:message code="label.campo.tipoDeMiembro" /></label>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6 b-r">
			<select class="form-control" id="txtTipoDeMiembro" tabindex="2">
				<option value=""><spring:message code="label.campo.seleccionar" /></option>
				<option value="Victima"><spring:message code="label.victima.victima" /></option>
				<option value="Victima"><spring:message code="label.victima.demandante" /></option>
			</select>
		</div>
		<div class="col-sm-6 b-r">
			<div class="checkbox i-checks">
				<label> <input type="checkbox" value=""> <i></i> <label><spring:message
							code="label.campo.esContacto" /></label></label>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6 b-r">
			<label><spring:message code="label.campo.nombresMiembro" /></label>
		</div>
		<div class="col-sm-6 b-r">
			<label><spring:message code="label.campo.apellidosMiembro" /></label>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6 b-r">
			<input type="text" class="form-control" id="txtNombresMiembro" name="txtNombresMiembro">
		</div>
		<div class="col-sm-6 b-r">
			<input type="text" class="form-control" id="txtApellidosMiembro" name="txApellidosMiembro">
		</div>
	</div>
	<div class="row">
		<div class="col-sm-4 b-r">
			<label><spring:message code="label.campo.identificacionDeMiembro" /></label>
		</div>
		<div class="col-sm-4 b-r">
			<label><spring:message code="label.campo.direccionDeMiembro" /></label>
		</div>
		<div class="col-sm-4 b-r">
			<label><spring:message code="label.campo.parentescoDeMiembro" /></label>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-4 b-r">
			<input type="text" class="form-control" id="txtIdentificacionMiembro" name="txIdentificacionMiembro">
		</div>
		<div class="col-sm-4 b-r">
			<input type="text" class="form-control" id="txtDireccionMiembro" name="txDireccionMiembro">
		</div>
		<div class="col-sm-4 b-r">
			<select class="form-control" id="txtParentescoMiembro" tabindex="2">
				<option value=""><spring:message code="label.campo.seleccionar" /></option>
				<option value="Padre"><spring:message code="label.victima.padre" /></option>
				<option value="Hermano"><spring:message code="label.victima.hermano" /></option>
			</select>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-4 b-r">
			<label><spring:message code="label.campo.telefonoMiembro" /></label>
		</div>
		<div class="col-sm-4 b-r">
			<label><spring:message code="label.campo.celularMiembro" /></label>
		</div>
		<div class="col-sm-4 b-r">
			<label><spring:message code="label.campo.correoMiembro" /></label>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-3 b-r">
			<input type="text" class="form-control" id="txtTelefonoMiembro" name="txTelefonoMiembro">
			<button type="button" class="btn btn-primary" id="txtAgregarTelefonoMiembro" name="txtAgregarTelefonoMiembro"
				style="float: right">
				<spring:message code="label.campo.agregarTelefonoMiembro" />
			</button>
		</div>
		<div class="col-sm-3 b-r">
			<input type="text" class="form-control" id="txtCelularMiembro" name="txCelularMiembro">
			<button type="button" class="btn btn-primary" id="txtAgregarCelularMiembro" name="txtAgregarCelularMiembro"
				style="float: right">
				<spring:message code="label.campo.agregarCelularMiembro" />
			</button>
		</div>
		<div class="col-sm-6 b-r">
			<input type="text" class="form-control" id="txtCorreoMiembro" name="txCorreoMiembro">
			<button type="button" class="btn btn-primary" id="txtAgregarCorreoMiembro" name="txtAgregarCorreoMiembro"
				style="float: right">
				<spring:message code="label.campo.agregarCorreoMiembro" />
			</button>
		</div>
	</div>
</c:if>