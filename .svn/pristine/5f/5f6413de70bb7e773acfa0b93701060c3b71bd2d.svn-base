<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<script src="js/general/general.js"></script>
	<div name="prestamos" style="display: none">
		<div class="row">
			<div class="col-sm-8 b-r">
				<label>
					<spring:message code="label.campo.nombreDeudor" />
				</label>
			</div>
			<div class="col-sm-4">
				<label>
					<spring:message code="label.campo.identificacionDeudor" />
				</label>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-8 b-r">
				<input class="form-control" id="txtnombreDeudor" name="txtnombreDeudor" maxlength="60">
			</div>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="txtIdentificacionDeudor"
					name="txtIdentificacionDeudor" maxlength="11">
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 ">
				<label>
					<spring:message code="label.campo.descripcionPrestamo" />
				</label>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12 ">
				<textarea class="form-control" id="txtDescripcionPrestamo" name="txtDescripcionPrestamo" rows="2"
					maxlength="255"></textarea>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-4 b-r">
				<label>
					<spring:message code="label.campo.fechaPrestamo" />
				</label>
			</div>
			<div class="col-sm-4 b-r">
				<label>
					<spring:message code="label.campo.valorPrestamo" />
				</label>
			</div>
			<div class="col-sm-4">
				<label>
					<spring:message code="label.campo.porcentajeInteresPrestamo" />
				</label>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-4 b-r">
				<input type="date" class="form-control" id="txtFechaPrestamo" name="txtFechaPrestamo">
			</div>
			<div class="col-sm-4 b-r">
				<div class="input-group m-b">
					<span class="input-group-addon">$</span>
					<input type="text" class="form-control" id="txtValorPrestamo" name="txtValorPrestamo">
					<span class="input-group-addon">.00</span>
				</div>
			</div>
			<div class="col-sm-4 ">
				<div class="input-group m-b">
					<span class="input-group-addon">%</span>
					<input type="text" class="form-control" id="txtPorcentajeInteresPrestamo"
						name="txtPorcentajeInteresPrestamo">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-4 b-r">
				<label>
					<spring:message code="label.campo.tipoDeCuenta" />
				</label>
			</div>
			<div class="col-sm-4 b-r">
				<label>
					<spring:message code="label.campo.entidadFinanciera" />
				</label>
			</div>
			<div class="col-sm-4">
				<label>
					<spring:message code="label.campo.numeroDeCuenta" />
				</label>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-4 b-r">
				<select class="form-control" id="txtTipoDeCuenta" name="txtTipoDeCuenta">
				</select>
			</div>
			<div class="col-sm-4 b-r">
				<select class="form-control" id="txtEntidadFinanciera" name="txtEntidadFinanciera">
				</select>
			</div>
			<div class="col-sm-4">
				<div>
					<input type="text" class="form-control" id="txtNumeroDeCuenta" name="txtNumeroDeCuenta"
						maxlength="11">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-4 b-r">
				<label>
					<spring:message code="label.campo.intereses" />
				</label>
			</div>
			<div class="col-sm-4 b-r">
				<label>
					<spring:message code="label.campo.saldoPrestamo" />
				</label>
			</div>
			<div class="col-sm-4">
				<label>
					<spring:message code="label.campo.tituloValor" />
				</label>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-4 b-r">
				<div class="input-group m-b">
					<span class="input-group-addon">$</span>
					<input type="text" class="form-control" id="txtIntereses" name="txtIntereses">
					<span class="input-group-addon">.00</span>
				</div>
			</div>
			<div class="col-sm-4 b-r">
				<div class="input-group m-b">
					<span class="input-group-addon">$</span>
					<input type="text" class="form-control" id="txtSaldoPrestamo" name="txtSaldoPrestamo">
					<span class="input-group-addon">.00</span>
				</div>
			</div>
			<div class="col-sm-4">
				<input type="text" class="form-control" id="txtTituloValor" name="txtTituloValor" maxlength="30">
			</div>
		</div>
		<div class="row"></div>
		<div class="row"></div>
		<div class="row">
			<div class="col-sm-12"></div>
		</div>
		<br>
		<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
			<div class="row">
				<div class="col-sm-11"></div>
				<div class="col-sm-1">
					<a href="javascript:void(0);" onclick="agregarFormularioPrestamos()" title="Eliminar Prestamo"
						class="btn btn-success" style="background-color: #449D44;">
						<i class="glyphicon glyphicon-plus"></i>
					</a>
				</div>
			</div>
		</c:if>
		<br>
		<hr class="hr-line-solid">
	</div>
	<hr class="hr-line-solid" id="separadorBloquePrestamos">
	<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
		<div class="row">
			<br>
			<div class="col-sm-11 "></div>
			<div class="col-sm-1 ">
				<!-- 		<label><spring:message code="label.campo.AgregarPrestamo" /></label> -->
				<a href="javascript:void(0);" onclick="agregarFormularioPrestamos()" title="Adicionar Prestamo"
					class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;">
					<i class="glyphicon glyphicon-plus"></i>
				</a>
			</div>
		</div>
	</c:if>
</c:if>