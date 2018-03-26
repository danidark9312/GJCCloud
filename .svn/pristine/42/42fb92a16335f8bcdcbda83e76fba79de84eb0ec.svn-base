<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div class="alert alert-danger" id="messageErrorDemandante" name="messageError" style="display: none"></div>
<!-- 	<div class="row"padding-right: 6%;"> -->
<!-- 		<div class="col-sm-11"></div> -->
<!-- 		<div class="col-sm-1" style="padding-left: 1%;"> -->
<!-- 			<a href="javascript:void(0);" onclick="agregarFormularioDemandados()" -->
<!-- 				class="btn btn-primary btn-circle .btn-xs" style="background-color: #449D44; border: 0;" -->
<!-- 				title="Adicionar Demandado"> -->
<!-- 				<i class="glyphicon glyphicon-plus"></i> -->
<!-- 			</a> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<hr class="hr-line-solid">
	<div name="formularioEquipoCaso" class="hide">
		<div class="row"padding-right: 6%;">
			<div class="col-sm-11"></div>
			<div class="col-sm-1" style="padding-left: 1%;">
				<a href="javascript:void(0);" onclick="eliminarDemandadoYVictimas(this)"
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
				<select class="form-control" id="txtTipoPersona" name="txtTipoPersona" onchange="" tabindex="1">
					<option value=""><spring:message code="label.campo.seleccionar" /></option>
					<option value="Persona Natural"><spring:message code="label.campo.personaNatural" /></option>
					<option value="Persona Juridica"><spring:message code="label.campo.personaJuridica" /></option>
				</select>
			</div>
			
			<div class="col-sm-6">
				<label>
					<spring:message code="label.campo.identificacionDeMiembro" />
				</label>
				<input type="text" class="form-control" id="txtIdentificacionMiembro"
					name="txtIdentificacionMiembro" maxlength="11" tabindex="2">
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<label>
					<spring:message code="label.campo.nombresMiembro" />
				</label>
				<span class="text-danger"> *</span>
				<input type="text" class="form-control" id="txtNombresMiembro" name="txtNombresMiembro"
					maxlength="60" tabindex="3">
			</div>
<!-- 			<div class="col-sm-6"> -->
<!-- 				<label> -->
<!-- 					<spring:message code="label.campo.identificacionDeMiembro" /> -->
<!-- 				</label> -->
<!-- 				<input type="text" class="form-control" id="txtIdentificacionMiembro" -->
<!-- 					name="txtIdentificacionMiembro" maxlength="11" tabindex="3"> -->
<!-- 			</div> -->
			<div class="col-sm-6 b-r">
				<label>
					<spring:message code="label.maestroUsuario.Tipodocumento" />
				</label>
				<select class="form-control" id="nuevoDemandadoTipoDocumento" name="nuevoDemandadoTipoDocumento"tabindex="4"></select>
			</div>
		</div>
<!-- 		<div class="row"> -->
										
<!-- 			<div class="col-sm-6 b-r"> -->
<!-- 				<label> -->
<!-- 					<spring:message code="label.maestroUsuario.FechaNacimiento" /> -->
<!-- 				</label> -->
<!-- 					<input id="demandadoNacimientoUsuario" name="demandadoNacimientoUsuario" class="form-control" type="date" tabindex="5"/>								 -->
<!-- 			</div> -->
<!-- 		</div> -->
		<div class="row">
			<div class="col-sm-6 b-r">
				<label>
					<spring:message code="label.campo.paisDemandado" />
				</label>
				<select class="form-control" id="txtPaisMiembro" name="txtPaisMiembro"
					onchange="cargarDepartamentosPorPais1(this)" tabindex="6">
				</select>
			</div>
			<div class="col-sm-6 b-r">
				<label>
					<spring:message code="label.campo.departamentoDemandado" />
				</label>
				<select class="form-control" id="TxtDepartamentoMiembro" name="TxtDepartamentoMiembro"
					onchange="cargarCiudadPorDepartamentos1(this)" tabindex="7">
				</select>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<label>
					<spring:message code="label.campo.municipioDemandado" />
				</label>
				<select class="form-control" id="txtMunicipioMiembro" name="txtMunicipioMiembro" tabindex="8">
				</select>
			</div>
			<div class="col-sm-6">
				<label>
					<spring:message code="label.campo.direccionDeMiembro" />
				</label>
				<div class="input-group m-b">
					<span class="input-group-addon"><i class="fa fa-map-marker"></i></span>
					<input type="text" class="form-control" id="txtDireccionMiembro" name="txtDireccionMiembro"
						maxlength="60" tabindex="9">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6 b-r"></div>
			<div class="col-sm-6" style="padding-left: 3%"></div>
		</div>
		<div class="row">
			<div class="col-sm-6 b-r">
				<div name="formularioTelefono">
					<div class="col-sm-10" style="padding-left: 1%">
						<label>
							<spring:message code="label.campo.telefonoMiembro" />
						</label>
						<div class="input-group m-b">
							<span class="input-group-addon"><i class="fa fa-phone-square"></i></span>
							<input type="text" class="form-control" id="txtTelefonoMiembro"
								name="txtTelefonoMiembroDemandado" maxlength="15" data-mask="(999) 999-9999"
								placeholder="(___) ___-____" tabindex="10">
						</div>
					</div>
					<div class="col-sm-2">
						<a href="javascript:void(0);" onclick="agregartelefono(this)" title="Adicionar Teléfono"
							class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;" tabindex="11">
							<i class="glyphicon glyphicon-plus"></i>
						</a>
					</div>
				</div>
			</div>
			<div class="col-sm-6 b-r">
				<div name="formularioCelular">
					<div class="col-sm-10 ">
						<label>
							<spring:message code="label.campo.celularMiembro" />
						</label>
						<div class="input-group m-b">
							<span class="input-group-addon"><i class="fa fa-mobile"></i></span>
							<input type="text" class="form-control" id="txtCelularMiembro" name="txtCelularMiembro"
								maxlength="20" data-mask="(999) 999-9999" placeholder="(___) ___-____" tabindex="12">
						</div>
					</div>
					<div class="col-sm-2">
						<a href="javascript:void(0);" onclick="agregarCelular(this)"
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
			<div name="formularioCorreo">
				<div class="col-sm-11">
					<label>
						<spring:message code="label.campo.correoMiembro" />
					</label>
					<div class="input-group m-b" style="width: 99%">
						<span class="input-group-addon"><i class="fa fa-at"></i></span>
						<input type="text" class="form-control" name="txtCorreoMiembroDemandado" maxlength="60" tabindex="13">
					</div>
				</div>
				<div class="col-sm-1" style="padding-left: 1%;">
					<a href="javascript:void(0);" onclick="agregarCorreo(this)"
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
				<textarea id="txtObservacionesMiembro" name="txtObservacionesMiembro" class="form-control"
					rows="3" maxlength="255" tabindex="15"></textarea>
			</div>
		</div>
		<br>
		<hr class="hr-line-solid">
	</div>
	<hr class="hr-line-solid" id="separadorFormularioDemandados">
	<div class="row"padding-right: 6%;">
		<div class="col-sm-11"></div>
		<div class="col-sm-1" style="padding-left: 1%;">
			<a href="javascript:void(0);" onclick="agregarFormularioDemandados()"
				class="btn btn-primary btn-circle .btn-xs" style="background-color: #449D44; border: 0;"
				title="Adicionar Demandado" tabindex="16">
				<i class="glyphicon glyphicon-plus"></i>
			</a>
		</div>
	</div>
</c:if>