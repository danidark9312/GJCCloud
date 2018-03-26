<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">	
	<div id="modal-modificarDemandante" class="modal fade modal-scroll">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<label>
							<spring:message code="label.modal.modificarMiembroDelCaso" />
						</label>
					</h4>
				</div>
				<div class="modal-body modal-scroll">
					<div class="alert alert-danger" id="messageErrorDetalleCaso" name="messageErrorDetalleCaso"
						style="display: none"></div>
					<div name="formularioEquipoCaso" id="modificarVictimaDemandante">
						<div class="row">
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.campo.tipoDeMiembro" />
								</label>
								<span class="text-danger"> *</span>
								<select class="form-control" id="txtTipoDeMiembro" name="txtTipoDeMiembro" tabindex="1">
								</select>
							</div>
							<div class="col-sm-6">
								<br>
								<div class="checkbox i-checks">
									<label>
										<input type="checkbox" id="esContacto" name="esContacto" value="" tabindex="2">
										<i></i>
										<label>
											<spring:message code="label.campo.esContacto" />
										</label>
									</label>
								</div>
							</div>
						</div>
						<div name="formularioNombreMiembroNatural" id="formularioNombreMiembroNatural"
							style="display: block;">
							<div class="row">
								<div class="col-sm-6 b-r">
									<label>
										<spring:message code="label.campo.nombresMiembro" />
									</label>
									<span class="text-danger"> *</span>
									<input type="text" class="form-control" id="txtNombresMiembro" name="txtNombresMiembro"
										maxlength="60" tabindex="3">
								</div>
								<div class="col-sm-6">
									<label>
										<spring:message code="label.campo.apellidosMiembro" />
									</label>
									<span class="text-danger"> *</span>
									<input type="text" class="form-control" id="txtApellidosMiembro" name="txtApellidosMiembro"
										maxlength="30" tabindex="4">
								</div>
							</div>
						</div>
						<div name="formularioNombreMiembroJuridico" id="formularioNombreMiembroJuridico"
							style="display: none;">
							<label>
								<spring:message code="label.campo.nombreJuridico" />
							</label>
							<div class="row">
								<div class="col-sm-8 b-r">
									<input type="text" class="form-control" id="txtNombreMiembroJuridico"
										name="txtNombreMiembroJuridico" maxlength="30">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.campo.identificacionDeMiembro" />
								</label>
								<input type="text" class="form-control" id="txtIdentificacionMiembro"
									name="txtIdentificacionMiembro" maxlength="11" tabindex="5">
							</div>
						<div class="row">
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.maestroUsuario.Tipodocumento" />
								</label>
								<select class="form-control" id="victimaDemandanteTipoDocumento" name="victimaDemandanteTipoDocumento" tabindex="6">										
								</select>
							</div>
						</div>
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.maestroUsuario.FechaNacimiento" />
								</label>
								<input id="victimaDemandanteNacimientoUsuario" name="victimaDemandanteNacimientoUsuario" class="form-control" type="date" tabindex="7"/>								
							</div>
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.campo.parentescoDeMiembro" />
								</label>
								<select class="form-control" id="txtParentescoMiembro" name="txtParentescoMiembro"
									tabindex="8">
								</select>
							</div>
						</div>
						<div class="row" name="ubicacion">
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.campo.paisDemandado" />
								</label>
								<select class="form-control" id="txtPaisMiembroVictimaDemandante"
									name="txtPaisMiembroVictimaDemandante" tabindex="9"
									onchange="cargarDepartamentosPorPais1(this)">
								</select>
							</div>
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.campo.departamentoDemandado" />
								</label>
								<select class="form-control" id="TxtDepartamentoMiembroVictimaDemandante"
									name="TxtDepartamentoMiembroVictimaDemandante" tabindex="10"
									onchange="cargarCiudadPorDepartamentos1(this)">
								</select>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<label>
									<spring:message code="label.campo.municipioDemandado" />
								</label>
								<select class="form-control" id="txtMunicipioMiembroVictimaDemandante"
									name="txtMunicipioMiembroVictimaDemandante" tabindex="11">
								</select>
							</div>
							<div class="col-sm-6">
								<label>
									<spring:message code="label.campo.direccionDeMiembro" />
								</label>
								<div class="input-group m-b">
									<span class="input-group-addon"><i class="fa fa-map-marker"></i></span>
									<input type="text" class="form-control" id="txtDireccionMiembro" name="txtDireccionMiembro"
										maxlength="60" tabindex="12">
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
									<div class="col-sm-10 " style="padding-left: 1%">
										<label>
											<spring:message code="label.campo.telefonoMiembro" />
										</label>
										<span class="text-danger"> *</span>
										<div class="input-group m-b">
											<span class="input-group-addon"><i class="fa fa-phone-square"></i></span>
											<input type="text" class="form-control" id="txtTelefonoMiembro" name="txtTelefonoMiembro"
												maxlength="15" data-mask="(999) 999-9999" placeholder="(___) ___-____" tabindex="13">
										</div>
									</div>
									<div class="col-sm-2 ">
										<a href="javascript:void(0);" onclick="agregartelefono(this)" title="Adicionar Teléfono"
											class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;">
											<i class="glyphicon glyphicon-plus" tabindex="14"></i>
										</a>
									</div>
								</div>
							</div>
							<div class="col-sm-6">
								<div name="formularioCelular">
									<div class="col-sm-10">
										<label>
											<spring:message code="label.campo.celularMiembro" />
										</label>
										<div class="input-group m-b">
											<span class="input-group-addon"><i class="fa fa-mobile"></i></span>
											<input type="text" class="form-control" id="txtCelularMiembro" name="txtCelularMiembro"
												maxlength="20" data-mask="(999) 999-9999" placeholder="(___) ___-____" tabindex="15">
										</div>
									</div>
									<div class="col-sm-2">
										<a href="javascript:void(0);" onclick="agregarCelular(this)" title="Adicionar Celular"
											class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;">
											<i class="glyphicon glyphicon-plus" tabindex="16"></i>
										</a>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<label>
									<spring:message code="label.campo.correoMiembro" />
									<span class="text-danger"> *</span>
								</label>
							</div>
						</div>
						<div class="row">
							<div name="formularioCorreo">
								<div class="col-sm-11">
									<div class="input-group m-b" style="width: 99%">
										<span class="input-group-addon"><i class="fa fa-at"></i></span>
										<input type="text" class="form-control" id="txtCorreoMiembro" name="txtCorreoMiembro"
											maxlength="60" tabindex="17">
									</div>
								</div>
								<div class="col-sm-1" style="padding-left: 1%">
									<a href="javascript:void(0);" onclick="agregarCorreo(this)" title="Adicionar Correo"
										class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;">
										<i class="glyphicon glyphicon-plus" tabindex="18"></i>
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
									rows="3" maxlength="255" tabindex="16"></textarea>
							</div>
						</div>
						
						<br> <br>
						<hr class="hr-line-solid">
						<form action="${url}" id="fotoForm" name="fotoForm" enctype="multipart/form-data">
							<div class="fileinput fileinput-new" data-provides="fileinput" >
								<div class="fileinput-preview thumbnail" data-trigger="fileinput"
									style="width: 200px; height: 150px;" tabindex="19" id="fotoFormVicDem">
								</div>
								<div id="previewFotoTemporalVicDem" style="width: 200px; height: 150px;" hidden="hidden">
									<img src="" width="" height="">
								</div>
								<div>
									<span class="btn btn-default btn-file"><span   class="fileinput-new"><spring:message
												code="label.campo.seleccionarImagen" /></span><span class="fileinput-exists"><spring:message
												code="label.campo.cambiarImagen" /></span> <input onclick="ocultarMostrar()" type="file" id="archivos" name="archivos" tabindex="19">
									</span>
									<a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">
										<spring:message code="label.campo.removerImagen" />
									</a>									
								</div>
							</div>							
						</form>
					</div>
					<!-- formulario abogado -->
					<div name="formularioEquipoCaso" id="modificarAbogado">
						<div name="formularioNombreMiembroNatural" id="formularioNombreMiembroNatural"
							style="display: block;">
							<div class="row">
								<div class="col-sm-6 b-r">
									<label>
										<spring:message code="label.campo.nombresMiembro" />
									</label>
									<span class="text-danger"> *</span>
									<input type="text" class="form-control" id="txtNombresMiembro" name="txtNombresMiembro"
										maxlength="60" tabindex="1">
								</div>
								<div class="col-sm-6">
									<label>
										<spring:message code="label.campo.apellidosMiembro" />
									</label>
									<input type="text" class="form-control" id="txtApellidosMiembro" name="txtApellidosMiembro"
										maxlength="30" tabindex="2">
								</div>							
							</div>
						</div>
						<div name="formularioNombreMiembroJuridico" id="formularioNombreMiembroJuridico"
							style="display: none;">
							<label>
								<spring:message code="label.campo.nombreJuridico" />
							</label>
							<div class="row">
								<div class="col-sm-8 b-r">
									<input type="text" class="form-control" id="txtNombreMiembroJuridico"
										name="txtNombreMiembroJuridico" maxlength="30">
								</div>
								
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.campo.identificacionDeMiembro" />
								</label>
								<input type="text" class="form-control" id="txtIdentificacionMiembro" disabled="disabled"
									name="txtIdentificacionMiembro" maxlength="11" tabindex="3">
							</div>
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.maestroUsuario.Tipodocumento" />
								</label>
								<select class="form-control" id="abogadoTipoDocumento" name="abogadoTipoDocumento"
									tabindex="4">										
								</select>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.maestroUsuario.FechaNacimiento" />
								</label>
								<input id="abogadoNacimiento" name="abogadoNacimiento" class="form-control" type="date" tabindex="5"/>								
							</div>
							<div class="col-sm-6">
								<label>
									<spring:message code="label.maestroUsuario.tarjetaProfesional" />
								</label>
								<input type="text" class="form-control" id="txtTarjetaProfesional" 
									name="txtTarjetaProfesional" maxlength="20" tabindex="5">
							</div>
						</div>
						<div class="row" name="ubicacion">
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.campo.paisDemandado" />
								</label>
								<select class="form-control" id="txtPaisMiembroVictimaDemandante"
									name="txtPaisMiembroVictimaDemandante" tabindex="6"
									onchange="cargarDepartamentosPorPais1(this)">
								</select>
							</div>
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.campo.departamentoDemandado" />
								</label>
								<select class="form-control" id="TxtDepartamentoMiembroVictimaDemandante"
									name="TxtDepartamentoMiembroVictimaDemandante" tabindex="7"
									onchange="cargarCiudadPorDepartamentos1(this)">
								</select>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-6">
								<label>
									<spring:message code="label.campo.municipioDemandado" />
								</label>
								<select class="form-control" id="txtMunicipioMiembroVictimaDemandante"
									name="txtMunicipioMiembroVictimaDemandante" tabindex="8">
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
						<div class="row" style="margin-top: 2px;">
							<div class="col-sm-6 b-r">
								<div name="formularioTelefono">
									<div class="col-sm-10 " style="padding-left: 1%">
										<label>
											<spring:message code="label.campo.telefonoMiembro" />
										</label>
										<span class="text-danger"> *</span>
										<div class="input-group m-b">
											<span class="input-group-addon"><i class="fa fa-phone-square"></i></span>
											<input type="text" class="form-control" id="txtTelefonoMiembro" name="txtTelefonoMiembro"
												maxlength="15" data-mask="(999) 999-9999" placeholder="(___) ___-____" tabindex="10">
										</div>
									</div>
<!-- 									<div class="col-sm-2 "> -->
<!-- 										<a href="javascript:void(0);" onclick="agregartelefono(this)" title="Adicionar Teléfono" -->
<!-- 											class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;"> -->
<!-- 											<i class="glyphicon glyphicon-plus" tabindex="11"></i> -->
<!-- 										</a> -->
<!-- 									</div> -->
								</div>
							</div>
							<div class="col-sm-6">
								<div name="formularioCelular">
									<div class="col-sm-10">
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
<!-- 										<a href="javascript:void(0);" onclick="agregarCelular(this)" title="Adicionar Celular" -->
<!-- 											class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;"> -->
<!-- 											<i class="glyphicon glyphicon-plus" tabindex="13"></i> -->
<!-- 										</a> -->
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
							<div name="formularioCorreo">
								<div class="col-sm-11">
									<div class="input-group m-b" style="width: 99%">
										<span class="input-group-addon"><i class="fa fa-at"></i></span>
										<input type="text" class="form-control" id="txtCorreoMiembro" name="txtCorreoMiembro"
											maxlength="60" tabindex="14">
									</div>
								</div>
<!-- 								<div class="col-sm-1" style="padding-left: 1%"> -->
<!-- 									<a href="javascript:void(0);" onclick="agregarCorreo(this)" title="Adicionar Correo" -->
<!-- 										class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;"> -->
<!-- 										<i class="glyphicon glyphicon-plus" tabindex="15"></i> -->
<!-- 									</a> -->
<!-- 								</div> -->
							</div>
						</div>
						<br> <br>
						<hr class="hr-line-solid">
						<form action="${url}" id="fotoForm" name="fotoForm" enctype="multipart/form-data">
							<div class="fileinput fileinput-new" data-provides="fileinput">
								<div class="fileinput-preview thumbnail" data-trigger="fileinput"
									style="width: 200px; height: 150px;" tabindex="16" id="fotoAbogado">									
								</div>
								<div id="previewFotoTemporalAbogado" style="width: 200px; height: 150px;" hidden="hidden">
									<img src="" width="" height="">
								</div>
								<div>
									<span class="btn btn-default btn-file"><span class="fileinput-new"><spring:message
												code="label.campo.seleccionarImagen" /></span><span class="fileinput-exists"><spring:message
												code="label.campo.cambiarImagen" /></span> <input onclick="ocultarMostrar()" type="file" id="archivos" name="archivos" tabindex="17">
									</span>
									<a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">
										<spring:message code="label.campo.removerImagen" />
									</a>									
								</div>
							</div>												
						</form>
					</div>
					<!-- formulario abogado -->
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-azul" data-dismiss="modal" onclick="" tabindex="20">
						<label>
							<spring:message code="label.boton.cancelar" />
						</label>
					</button>
					<button type="button" class="btn btn-azul" onclick="modificarInformacionMiembroCaso()" tabindex="21">
						<label>
							<spring:message code="label.boton.guardar" />
						</label>
					</button>
				</div>
			</div>
		</div>
	</div>
</c:if>