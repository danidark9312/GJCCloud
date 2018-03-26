<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div class="alert alert-danger hide" id="errorAgregarDemandado" name="errorAgregarDemandado"></div>
	<div class="row"padding-right: 6%;">
		<div class="col-sm-11"></div>
		<div class="col-sm-1" style="padding-left: 1%;">
			<a href="javascript:void(0);" onclick="agregarFormularioNuevoDemandado()"
				class="btn btn-primary btn-circle .btn-xs" style="background-color: #449D44; border: 0;"
				title="Adicionar Demandado">
				<i class="glyphicon glyphicon-plus"></i>
			</a>
		</div>
	</div>
	<hr class="hr-line-solid">
	<div name="formularioEquipoCasoNuevoMiembro" class="hide">
		<div class="row"padding-right: 6%;">
			<div class="col-sm-11"></div>
			<div class="col-sm-1" style="padding-left: 1%;">
				<a href="javascript:void(0);" onclick="eliminarDemandadoNuevo(this)"
					class="btn btn-primary btn-circle .btn-xs" style="background-color: red; border: 0;"
					title="Adicionar Demandado">
					<i class="glyphicon glyphicon-minus"></i>
				</a>
			</div>
		</div>
			<div class="row">
				<div class="col-sm-6 b-r">
					<label>
						<spring:message code="label.campo.tipoDePersona" />
					</label>
					<span class="text-danger"> *</span>
					<select class="form-control" id="txtTipoPersonaNuevoMiembro" name="txtTipoPersonaNuevoMiembro"
						onchange="" tabindex="1">
						<option value=""><spring:message code="label.campo.seleccionar" /></option>
						<option value="Persona Natural"><spring:message code="label.campo.personaNatural" /></option>
						<option value="Persona Juridica"><spring:message code="label.campo.personaJuridica" /></option>
					</select>
				</div>
				<div class="col-sm-6">
					<label>
						<spring:message code="label.campo.nombresMiembro" />
					</label>
					<span class="text-danger"> *</span>
					<input type="text" class="form-control" id="txtNombresMiembroNuevoMiembro"
						name="txtNombresMiembroNuevoMiembro" maxlength="60" tabindex="2">
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 b-r">
					<label>
						<spring:message code="label.maestroUsuario.Tipodocumento" />
					</label>
					<select class="form-control" id="txtTipoDocumentoNuevoMiembro" name="txtTipoDocumentoNuevoMiembro" tabindex="3"></select>
				</div>
				<div class="col-sm-6">
					<label>
						<spring:message code="label.campo.identificacionDeMiembro" />
					</label>
					<input type="text" class="form-control" id="txtIdentificacionMiembroNuevoMiembro"
						name="txtIdentificacionMiembroNuevoMiembro" maxlength="11" tabindex="4">
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 b-r">
					<label>
						<spring:message code="label.campo.paisDemandado" />
					</label>
					<select class="form-control" id="txtPaisAdicionarDemandado" name="txtPaisAdicionarDemandado"
						onchange="cargarDepartamentosPorPaisNuevoMiembro(this,1)" tabindex="5">
					</select>
				</div>
				<div class="col-sm-6 b-r">
					<label>
						<spring:message code="label.campo.departamentoDemandado" />
					</label>
					<select class="form-control" id="TxtDepartamentoMiembroEquipo" name="TxtDepartamentoMiembroEquipo"
						onchange="cargarCiudadPorDepartamentosNuevoMiembro(this,1)" tabindex="6">
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<label>
						<spring:message code="label.campo.municipioDemandado" />
					</label>
					<select class="form-control" id="txtMunicipioMiembroEquipo" name="txtMunicipioMiembroEquipo" tabindex="7">
					</select>
				</div>
				<div class="col-sm-6">
					<label>
						<spring:message code="label.campo.direccionDeMiembro" />
					</label>
					<div class="input-group m-b">
						<span class="input-group-addon"><i class="fa fa-map-marker"></i></span>
						<input type="text" class="form-control" id="txtDireccionMiembroNuevoMiembro"
							name="txtDireccionMiembroNuevoMiembro" maxlength="60" tabindex="8">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 b-r"></div>
				<div class="col-sm-6" style="padding-left: 3%"></div>
			</div>
			<div class="row">
				<div class="col-sm-6 b-r">
					<div name="formularioTelefonoNuevo">
						<div class="col-sm-10" style="padding-left: 1%">
							<label>
								<spring:message code="label.campo.telefonoMiembro" />
							</label>
							<div class="input-group m-b">
								<span class="input-group-addon"><i class="fa fa-phone-square"></i></span>
								<input type="text" class="form-control" id="txtTelefonoMiembroNuevoMiembro"
									name="txtTelefonoMiembroDemandadoNuevoMiembro" maxlength="15" data-mask="(999) 999-9999"
									placeholder="(___) ___-____" tabindex="9">
							</div>
						</div>
						<div class="col-sm-2">
							<a href="javascript:void(0);" onclick="agregartelefonoGeneral(this,1)"
								title="Adicionar Teléfono" class="btn btn-success btn-circle .btn-xs"
								style="background-color: #449D44; border: 0;" tabindex="10">
								<i class="glyphicon glyphicon-plus"></i>
							</a>
						</div>
					</div>
				</div>
				<div class="col-sm-6 b-r">
					<div name="formularioCelularNuevo">
						<div class="col-sm-10 ">
							<label>
								<spring:message code="label.campo.celularMiembro" />
							</label>
							<div class="input-group m-b">
								<span class="input-group-addon"><i class="fa fa-mobile"></i></span>
								<input type="text" class="form-control" id="txtCelularMiembroNuevoMiembro"
									name="txtCelularMiembroNuevoMiembro" maxlength="20" data-mask="(999) 999-9999"
									placeholder="(___) ___-____" tabindex="11">
							</div>
						</div>
						<div class="col-sm-2">
							<a href="javascript:void(0);" onclick="agregarCelularGeneral(this,1)"
								class="btn btn-success btn-circle .btn-xs" title="Adicionar Teléfono"
								style="background-color: #449D44; border: 0;" tabindex="12">
								<i class="glyphicon glyphicon-plus"></i>
							</a>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12"></div>
			</div>
			<div class="row">
				<div name="formularioCorreoNuevo">
					<div class="col-sm-11">
						<label>
							<spring:message code="label.campo.correoMiembro" />
						</label>
						<div class="input-group m-b" style="width: 99%">
							<span class="input-group-addon"><i class="fa fa-at"></i></span>
							<input type="text" class="form-control" id="txtCorreoMiembroNuevoMiembro"
								name="txtCorreoMiembroDemandadoNuevoMiembro" maxlength="60" tabindex="13">
						</div>
					</div>
					<div class="col-sm-1" style="padding-left: 1%;">
						<a href="javascript:void(0);" onclick="agregarCorreoGeneral(this,1)"
							class="btn btn-success btn-circle .btn-xs" title="Adicionar Correo"
							style="background-color: #449D44; border: 0;" tabindex="14">
							<i class="glyphicon glyphicon-plus"></i>
						</a>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<label>
						<spring:message code="label.campo.observacionesMiembro" />
					</label>
					<textarea id="txtObservacionesMiembroNuevoMiembro" name="txtObservacionesMiembroNuevoMiembro"
						class="form-control" rows="3" maxlength="255" tabindex="15"></textarea>
				</div>
			</div>
			<br>
			<hr class="hr-line-solid">
		</div>	
</c:if>