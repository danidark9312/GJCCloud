<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div class="row">
		<div class="alert alert-danger hide" id="errorAgregarEquipoCaso" name="messageError"></div>
	</div>
	<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
		<div class="row">
			<div class="col-sm-11"></div>
			<div class="col-sm-1" style="padding-left: 1%;">
				<a href="javascript:void(0);" onclick="agregarFormularioEquipoCasoNuevoMiembro()"
					title="Adicionar Victima/Demandante" class="btn btn-success btn-circle .btn-xs"
					style="background-color: #449D44; border: 0;">
					<i class="glyphicon glyphicon-plus"></i>
				</a>
			</div>
		</div>
	</c:if>
	<hr class="hr-line-solid">
	<div name="formularioEquipoCasoNuevoMiembroModal" class="hide">
		<div class="row">
			<div class="alert alert-danger hide" id="messageErrorCasoMiembro" name="messageErrorcasoMiembro"
				style="display: none"></div>
		</div>
		<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
			<div class="row">
				<div class="col-sm-11"></div>
				<div class="col-sm-1 " style="padding-left: 1%;">
					<a href="javascript:void(0);" onclick="eliminarNuevoMiembro(this)"
						title="Adicionar Victima/Demandante" class="btn btn-success btn-circle .btn-xs"
						style="background-color: red; border: 0;">
						<i class="glyphicon glyphicon-minus"></i>
					</a>
				</div>
			</div>
		</c:if>
		<div class="row">
			<div class="col-sm-6 b-r">
				<label>
					<spring:message code="label.campo.tipoDeMiembro" />
				</label>
				<span class="text-danger"> *</span>
				<select class="form-control" id="txtTipoDeMiembroCasoNuevoMiembro"
					name="txtTipoDeMiembroCasoNuevoMiembro" onchange="validarTipoMiembro(this)">
				</select>
			</div>
			<div class="col-sm-6">
				<br>
				<div class="checkbox i-checks">
					<label>
						<input type="checkbox" id="esContactoCasoMiembroNuevo" name="esContacto" value="">
						<i></i>
						<label>
							<spring:message code="label.campo.esContacto" />
						</label>
					</label>
				</div>
			</div>
		</div>
		<div name="formularioNombreMiembroNaturalCaso" id="formularioNombreMiembroNaturalCaso"
			style="display: block;">
			<div class="row">
				<div class="col-sm-6 b-r">
					<label>
						<spring:message code="label.campo.nombresMiembro" />
					</label>
					<span class="text-danger"> *</span>
					<input type="text" class="form-control" id="txtNombresMiembroCaso" name="txtNombresMiembroCaso"
						maxlength="60">
				</div>
				<div class="col-sm-6">
					<label>
						<spring:message code="label.campo.apellidosMiembro" />
					</label>
					<span class="text-danger"> *</span>
					<input type="text" class="form-control" id="txtApellidosMiembroCaso"
						name="txtApellidosMiembroCaso" maxlength="30">
				</div>
			</div>
		</div>
		<div name="formularioNombreMiembroJuridicoCaso" id="formularioNombreMiembroJuridicoCaso"
			style="display: none;">
			<label>
				<spring:message code="label.campo.nombreJuridico" />
			</label>
			<div class="row">
				<div class="col-sm-8 b-r">
					<input type="text" class="form-control" id="txtNombreMiembroJuridicoCaso"
						name="txtNombreMiembroJuridicoCaso" maxlength="30">
				</div>
			</div>
		</div>
		

		<div class="row">
			<div class="col-sm-6 b-r">
				<label>
					<spring:message code="label.maestroUsuario.Tipodocumento" />
				</label>
				<select class="form-control" id="txtTipoDocumentoNuevoMiembroEquipoCaso" name="txtTipoDocumentoNuevoMiembroEquipoCaso" ></select>
			</div>
			<div class="col-sm-6 b-r">
				<label>
					<spring:message code="label.campo.identificacionDeMiembro" />
				</label>
				<input type="text" class="form-control" id="txtIdentificacionMiembroCaso"
					name="txtIdentificacionMiembroCaso" maxlength="11">
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6 b-r">
				<label>
					<spring:message code="label.maestroUsuario.FechaNacimiento" />
				</label>
				<input id="nuevoMiembroNacimiento" name="nuevoMiembroNacimiento" class="form-control" type="date"/>								
			</div>
			<div class="col-sm-6 b-r">
				<label>
					<spring:message code="label.campo.parentescoDeMiembro" />
				</label>
				<select class="form-control" id="txtParentescoMiembroNuevoMiembro"
					name="txtParentescoMiembroNuevoMiembroCaso">
				</select>
			</div>
		</div><br>
		<div class="row" name="ubicacion">
			<div class="col-sm-6 b-r">
				<label>
					<spring:message code="label.campo.paisDemandado" />
				</label>
				<select class="form-control" id="txtPaisMiembroCNuevo" name="txtPaisMiembroCNuevo"
					onchange="cargarDepartamentosPorPaisNuevoMiembro(this,2)">
				</select>
			</div>
			<div class="col-sm-6 b-r">
				<label>
					<spring:message code="label.campo.departamentoDemandado" />
				</label>
				<select class="form-control" id="TxtDepartamentoMiembroNuevoMiembroCNuevo"
					name="TxtDepartamentoMiembroNuevoMiembroCnuevo"
					onchange="cargarCiudadPorDepartamentosNuevoMiembro(this,2)">
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<label>
					<spring:message code="label.campo.municipioDemandado" />
				</label>
				<select class="form-control" id="txtMunicipioMiembroNuevoMiembro"
					name="txtMunicipioMiembroNuevoMiembro">
				</select>
			</div>
			<div class="col-sm-6">
				<label>
					<spring:message code="label.campo.direccionDeMiembro" />
				</label>
				<div class="input-group m-b">
					<span class="input-group-addon"><i class="fa fa-map-marker"></i></span>
					<input type="text" class="form-control" id="txtDireccionMiembroCaso"
						name="txtDireccionMiembroCaso" maxlength="60">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6 b-r">
				<label>
					<spring:message code="label.campo.telefonoMiembro" />
				</label>
				<span class="text-danger"> *</span>
			</div>
			<div class="col-sm-6" style="padding-left: 3%">
				<label>
					<spring:message code="label.campo.celularMiembro" />
				</label>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6 b-r">
				<div name="formularioTelefonoCasoNuevoCaso">
					<div class="col-sm-10 " style="padding-left: 1%">
						<div class="input-group m-b">
							<span class="input-group-addon"><i class="fa fa-phone-square"></i></span>
							<input type="text" class="form-control" id="txtTelefonoMiembroCaso"
								name="txtTelefonoMiembroCaso" maxlength="15" data-mask="(999) 999-9999"
								placeholder="(___) ___-____">
						</div>
					</div>
					<div class="col-sm-2 ">
						<a href="javascript:void(0);" onclick="agregartelefonoGeneral(this,2)"
							title="Adicionar Tel�fono" class="btn btn-success btn-circle .btn-xs"
							style="background-color: #449D44; border: 0;">
							<i class="glyphicon glyphicon-plus"></i>
						</a>
					</div>
				</div>
			</div>
			<div class="col-sm-6">
				<div name="formularioCelularCasoNuevo">
					<div class="col-sm-10">
						<div class="input-group m-b">
							<span class="input-group-addon"><i class="fa fa-mobile"></i></span>
							<input type="text" class="form-control" id="txtCelularMiembroCaso" name="txtCelularMiembroCaso"
								maxlength="20" data-mask="(999) 999-9999" placeholder="(___) ___-____">
						</div>
					</div>
					<div class="col-sm-2">
						<a href="javascript:void(0);" onclick="agregarCelularGeneral(this,2)" title="Adicionar Celular"
							class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;">
							<i class="glyphicon glyphicon-plus"></i>
						</a>
					</div>
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col-sm-12">
				<label>
					<spring:message code="label.campo.correoMiembro" />
				</label>
				<span class="text-danger"> *</span>
			</div>
		</div>
		<div class="row">
			<div name="formularioCorreoCasoNuevo">
				<div class="col-sm-11">
					<div class="input-group m-b" style="width: 99%">
						<span class="input-group-addon"><i class="fa fa-at"></i></span>
						<input type="text" class="form-control" id="txtCorreoMiembroCaso" name="txtCorreoMiembroCaso"
							maxlength="60">
					</div>
				</div>
				<div class="col-sm-1" style="padding-left: 1%">
					<a href="javascript:void(0);" onclick="agregarCorreoGeneral(this,2)" title="Adicionar Correo"
						class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;">
						<i class="glyphicon glyphicon-plus"></i>
					</a>
				</div>
			</div>
		</div>
		<div class="row" style="display:none">
			<div class="col-sm-12">
				<label> <spring:message code="label.campo.observacionesMiembro" />
				</label>
				<textarea id="txtObservacionesNuevoMiembro"
					name="txtObservacionesNuevoMiembro" class="form-control" rows="3"
					maxlength="255" tabindex="16"></textarea>
			</div>
		</div>
		<br> <br>
		<hr class="hr-line-solid">
	</div>
</c:if>