<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div id="equipoDelCasoNuevoCaso">
		<div class="row">
			<div class="alert alert-danger" id="messageErrorVictimaDemandante" name="messageError" style="display: none">
			</div>
		</div>
		<div name="formularioEquipoCaso" class="hide">
			<div class="row">
				<div class="alert alert-danger" id="messageErrorVictimaDemandante" name="messageError" style="display: none">
				</div>
			</div>
			<div class="row">
				<div class="col-sm-11"></div>
				<div class="col-sm-1 " style="padding-left: 1%;">
					<a href="javascript:void(0);" onclick="eliminarDemandadoYVictimas(this)" title="Adicionar Victima/Demandante"
						class="btn btn-success btn-circle .btn-xs" style="background-color: red; border: 0;"><i
						class="glyphicon glyphicon-minus"></i></a>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 b-r">
					<label><spring:message code="label.campo.tipoDeMiembro" /></label><span class="text-danger"> *</span> <select
						class="form-control" id="txtTipoDeMiembro" name="txtTipoDeMiembro">
					</select>
				</div>

				<div class="col-sm-6">
					<br>
					<div class="checkbox i-checks">
						<label> <input type="checkbox" id="esContacto" name="esContacto" value=""> <i></i> <label><spring:message
									code="label.campo.esContacto"/></label></label>
					</div>
				</div>
			</div>
			<div name="formularioNombreMiembroNatural" id="formularioNombreMiembroNatural" style="display: block;">
				<div class="row">
					<div class="col-sm-6 b-r">
						<label><spring:message code="label.campo.nombresMiembro" /></label><span class="text-danger"> *</span> <input
							type="text" class="form-control" id="txtNombresMiembro" name="txtNombresMiembro" maxlength="60">
					</div>
					<div class="col-sm-6">
						<label><spring:message code="label.campo.apellidosMiembro" /></label><span class="text-danger"> *</span> <input
							type="text" class="form-control" id="txtApellidosMiembro" name="txtApellidosMiembro" maxlength="30">
					</div>
				</div>
			</div>
			<div name="formularioNombreMiembroJuridico" id="formularioNombreMiembroJuridico" style="display: none;">
				<label><spring:message code="label.campo.nombreJuridico" /></label>
				<div class="row">
					<div class="col-sm-8 b-r">
						<input type="text" class="form-control" id="txtNombreMiembroJuridico" name="txtNombreMiembroJuridico"
							maxlength="30">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 b-r">
					<label><spring:message code="label.campo.identificacionDeMiembro" /></label> <input type="text"
						class="form-control" id="txtIdentificacionMiembro" name="txtIdentificacionMiembro" maxlength="11">
				</div>
				<div class="col-sm-6 b-r">
					<label>
						<spring:message code="label.maestroUsuario.Tipodocumento" />
					</label>
					<select class="form-control" id="nuevoVictimaDemandanteTipoDocumento" name="nuevoVictimaDemandanteTipoDocumento"></select>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 b-r">
					<label>
						<spring:message code="label.maestroUsuario.FechaNacimiento" />
					</label>
					<input id="demandadoNacimientoUsuario" name="demandadoNacimientoUsuario" class="form-control" type="date"/>
				</div>
				<div class="col-sm-6 b-r">
					<label><spring:message code="label.campo.parentescoDeMiembro" /></label> <select class="form-control"
						id="txtParentescoMiembro" name="txtParentescoMiembro">
					</select>
				</div>
			</div>
			<div class="row" name="ubicacion">
				<div class="col-sm-6 b-r">
					<label><spring:message code="label.campo.paisDemandado" /></label> <select class="form-control"
						id="txtPaisMiembro1" name="txtPaisMiembro" onchange="cargarDepartamentosPorPais1(this)">
					</select>
				</div>
				<div class="col-sm-6 b-r">
					<label><spring:message code="label.campo.departamentoDemandado" /></label> <select class="form-control"
						id="TxtDepartamentoMiembro" name="TxtDepartamentoMiembro" onchange="cargarCiudadPorDepartamentos1(this)">
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6">
					<label><spring:message code="label.campo.municipioDemandado" /></label> <select class="form-control"
						id="txtMunicipioMiembro" name="txtMunicipioMiembro">
					</select>
				</div>
				<div class="col-sm-6">
					<label><spring:message code="label.campo.direccionDeMiembro" /></label>
					<div class="input-group m-b">
						<span class="input-group-addon"><i class="fa fa-map-marker"></i></span> <input type="text" class="form-control"
							id="txtDireccionMiembro" name="txtDireccionMiembro" maxlength="60">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 b-r">
					<label><spring:message code="label.campo.telefonoMiembro" /></label><span class="text-danger"> *</span>
				</div>
				<div class="col-sm-6" style="padding-left: 3%">
					<label><spring:message code="label.campo.celularMiembro" /></label>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 b-r">
					<div name="formularioTelefono">
						<div class="col-sm-10 " style="padding-left: 1%">
							<div class="input-group m-b">
								<span class="input-group-addon"><i class="fa fa-phone-square"></i></span> <input type="text"
									class="form-control" id="txtTelefonoMiembro" name="txtTelefonoMiembro" maxlength="15"
									data-mask="(999) 999-9999" placeholder="(___) ___-____">
							</div>
						</div>
						<div class="col-sm-2 ">
							<a href="javascript:void(0);" onclick="agregartelefono(this)" title="Adicionar Teléfono"
								class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;"> <i
								class="glyphicon glyphicon-plus"></i>
							</a>
						</div>
					</div>
				</div>
				<div class="col-sm-6">
					<div name="formularioCelular">
						<div class="col-sm-10">
							<div class="input-group m-b">
								<span class="input-group-addon"><i class="fa fa-mobile"></i></span> <input type="text" class="form-control"
									id="txtCelularMiembro" name="txtCelularMiembro" maxlength="20" data-mask="(999) 999-9999"
									placeholder="(___) ___-____">
							</div>
						</div>
						<div class="col-sm-2">
							<a href="javascript:void(0);" onclick="agregarCelular(this)" title="Adicionar Celular"
								class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;"><i
								class="glyphicon glyphicon-plus"></i></a>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12">
					<label><spring:message code="label.campo.correoMiembro" /></label><span class="text-danger"> *</span>
				</div>
			</div>
			<div class="row">
				<div name="formularioCorreo">
					<div class="col-sm-11">
						<div class="input-group m-b" style="width: 99%">
							<span class="input-group-addon"><i class="fa fa-at"></i></span> <input type="text" class="form-control"
								name="txtCorreoMiembro" maxlength="60">
						</div>
					</div>
					<div class="col-sm-1" style="padding-left: 1%">
						<a href="javascript:void(0);" onclick="agregarCorreo(this)" title="Adicionar Correo"
							class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;"><i
							class="glyphicon glyphicon-plus"></i></a>
					</div>
				</div>
			</div>
			<br> <br>
			<hr class="hr-line-solid">
		</div>
		<hr class="hr-line-solid" id="separadorFormularioEquipoCaso">
		<div class="row">
			<div class="col-sm-11"></div>
			<div class="col-sm-1" style="padding-left: 1%;">
				<a href="javascript:void(0);" onclick="agregarFormularioEquipoCaso()" title="Adicionar Victima/Demandante"
					class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;"><i
					class="glyphicon glyphicon-plus"></i></a>
			</div>
		</div>
	</div>
</c:if>