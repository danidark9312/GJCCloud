<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
	<div id="modalConfEliminarMiembro" name="modalConfEliminarMiembro" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.confirmacion.miembro" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroActividad.cancelar" />
						</button>
						<button type="button" class="btn btn-white" onclick="mostrarModalJustificacion()">
							<spring:message code="label.maestroActividad.eliminar" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modalJustificacionMiembro" name="modalJustificacionMiembro" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.confirmacion.miembro.justificacion" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
				</div>
				<div class="modal-body no-padding">
					<form class="smart-form">
						<div class="alert alert-danger alert-block" id="alertErrorEliminacionMiembro">
							<h4 class="alert-heading">
								<spring:message code="label.error" />
							</h4>
							<div id="divMensajeErrorEliminacionMiembro"></div>
						</div>
						<fieldset style="margin-top: 5px;">
							<section>
								<div class="row text-center">
									<div class="col col-12">
										<label class="input">
											<textarea name="justificacionEliminacion" id="justificacionEliminacion" cols="64" rows="5"></textarea>
										</label>
									</div>
								</div>
							</section>
						</fieldset>
						<footer class="text-right" style="padding-right: 30px; padding-bottom: 5px">
							<button type="button" class="btn btn-white" onclick="cerrarModalJustificacion()">
								<spring:message code="label.maestroActividad.cancelar" />
							</button>
							<button type="button" class="btn btn-white" onclick="validarEliminacion()">
								<spring:message code="label.maestroActividad.eliminar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
	<div id="modalNuevoResponsable" name="modalNuevoResponsable" class="modal fade" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.nuevo.responsable" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
				</div>
				<div class="modal-body no-padding">
					<form class="smart-form">
						<div class="alert alert-default alert-block" id="alertErrorNuevoResponsable">
							<div id="divMensajeErrorNuevoResponsable"></div>
						</div>
						<fieldset style="margin-top: 5px; padding: 20px">
							<div class="row">
								<div class="col-sm-12">
									<select class="form-control" id="cmbAbogadoReemplazo" name="cmbAbogadoReemplazo" tabindex="2">
									</select>
								</div>
							</div>
						</fieldset>
						<footer class="text-right" style="padding-right: 30px; padding-bottom: 5px">
							<button type="button" class="btn btn-white" data-dismiss="modal">
								<spring:message code="label.maestroActividad.cancelar" />
							</button>
							<button type="button" class="btn btn-white" onclick="asignarTareasNuevoAbogado()">
								<spring:message code="label.maestroActividad.continuar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
	<div id="modalNuevoContacto" name="modalNuevoContacto" class="modal fade" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.nuevo.contacto" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
				</div>
				<div class="modal-body no-padding">
					<form class="smart-form" id="nuevoContactoForm">
						<div class="alert alert-danger alert-block hide" id="divMensajeErrorNuevoContacto"></div>
						<fieldset style="margin-top: 5px; padding: 20px">
							<div class="row">
								<div class="col-sm-12">
									<select class="form-control" id="cmbContactoReemplazo" name="cmbContactoReemplazo"
										onchange="habilitarNuevoContacto(this)">
									</select>
								</div>
							</div>
							<div id="divNuevoContactoForm" hidden="hidden">
								<div class="alert alert-danger" id="messageErrorFormContacto" name="messageErrorFormContacto"
									style="display: none"></div>
								<div name="formularioEquipoCaso" id="formularioEquipoCaso">
									<div class="row">
										<div class="col-sm-6 b-r">
											<label>
												<spring:message code="label.campo.tipoDeMiembro" />
											</label>
											<span class="text-danger"> *</span>
											<select class="form-control" id="tipoDeMiembro" name="tipoDeMiembro">
											</select>
										</div>
										<div class="col-sm-6">
											<br>
											<div class="checkbox i-checks">
												<label>
													<input type="checkbox" id="snContacto" name="snContacto" checked="checked"
														disabled="disabled">
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
												<input type="text" class="form-control" id="nombresMiembro" name="nombresMiembro"
													maxlength="60">
											</div>
											<div class="col-sm-6">
												<label>
													<spring:message code="label.campo.apellidosMiembro" />
												</label>
												<span class="text-danger"> *</span>
												<input type="text" class="form-control" id="apellidosMiembro" name="apellidosMiembro"
													maxlength="30">
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
												<input type="text" class="form-control" id="nombreMiembroJuridico"
													name="nombreMiembroJuridico" maxlength="30">
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6 b-r">
											<label>
												<spring:message code="label.campo.identificacionDeMiembro" />
											</label>
											<input type="text" class="form-control" id="identificacionMiembro"
												name="identificacionMiembro" maxlength="11" />
										</div>
										<div class="col-sm-6 b-r">
											<label>
												<spring:message code="label.campo.parentescoDeMiembro" />
											</label>
											<select class="form-control" id="parentescoMiembro" name="parentescoMiembro" tabindex="2">
											</select>
										</div>
									</div>
									<div class="row" name="ubicacion">
										<div class="col-sm-6 b-r">
											<label>
												<spring:message code="label.campo.paisDemandado" />
											</label>
											<select class="form-control" id="paisMiembroVictimaDemandante"
												name="paisMiembroVictimaDemandante" tabindex="2"
												onchange="cargarDepartamentosPorPais1(this)">
											</select>
										</div>
										<div class="col-sm-6 b-r">
											<label>
												<spring:message code="label.campo.departamentoDemandado" />
											</label>
											<select class="form-control" id="departamentoMiembroVictimaDemandante"
												name="departamentoMiembroVictimaDemandante" tabindex="2"
												onchange="cargarCiudadPorDepartamentos1(this)">
											</select>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-6">
											<label>
												<spring:message code="label.campo.municipioDemandado" />
											</label>
											<select class="form-control" id="municipioMiembroVictimaDemandante"
												name="municipioMiembroVictimaDemandante" tabindex="2">
											</select>
										</div>
										<div class="col-sm-6">
											<label>
												<spring:message code="label.campo.direccionDeMiembro" />
											</label>
											<div class="input-group m-b">
												<span class="input-group-addon"><i class="fa fa-map-marker"></i></span>
												<input type="text" class="form-control" id="direccionMiembro" name="direccionMiembro"
													maxlength="60">
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
														<input type="text" class="form-control" id="telefonoMiembro" name="telefonoMiembro"
															maxlength="15" data-mask="(999) 999-9999" placeholder="(___) ___-____">
													</div>
												</div>
												<div class="col-sm-2 ">
													<a href="javascript:void(0);" onclick="agregartelefono(this)"
														title="Adicionar Tel&eacute;fono" class="btn btn-success btn-circle .btn-xs"
														style="background-color: #449D44; border: 0;">
														<i class="glyphicon glyphicon-plus"></i>
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
														<input type="text" class="form-control" id="celularMiembro" name="celularMiembro"
															maxlength="20" data-mask="(999) 999-9999" placeholder="(___) ___-____">
													</div>
												</div>
												<div class="col-sm-2">
													<a href="javascript:void(0);" onclick="agregarCelular(this)" title="Adicionar Celular"
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
										<div name="formularioCorreo">
											<div class="col-sm-11">
												<div class="input-group m-b" style="width: 99%">
													<span class="input-group-addon"><i class="fa fa-at"></i></span>
													<input type="text" class="form-control" id="correoMiembro" name="correoMiembro"
														maxlength="60">
												</div>
											</div>
											<div class="col-sm-1" style="padding-left: 1%">
												<a href="javascript:void(0);" onclick="agregarCorreo(this)" title="Adicionar Correo"
													class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;">
													<i class="glyphicon glyphicon-plus"></i>
												</a>
											</div>
										</div>
									</div>
									<br> <br>
									<hr class="hr-line-solid">
								</div>
							</div>
						</fieldset>
						<footer class="text-right" style="padding-right: 30px; padding-bottom: 5px">
							<button type="button" class="btn btn-white" onclick="cerrarModalNuevoContacto()">
								<spring:message code="label.maestroActividad.cancelar" />
							</button>
							<button type="button" class="btn btn-white" onclick="guardarNuevoContacto()"
								id="btnEliminarMiembro">
								<spring:message code="label.maestroActividad.eliminar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
	<div id="modalConfEliminarRadicado" name="modalConfEliminarRadicado" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.confirmacion.radicado" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroActividad.cancelar" />
						</button>
						<button type="button" class="btn btn-white" onclick="irJustificacionRadicado()">
							<spring:message code="label.maestroActividad.eliminar" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modalJustificacionRadicado" name="modalJustificacionRadicado" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.confirmacion.miembro.justificacion" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
				</div>
				<div class="modal-body no-padding">
					<form class="smart-form">
						<div class="alert alert-danger alert-block hide" id="alertErrorEliminacionRadicado">
							<h4 class="alert-heading">
								<spring:message code="label.error" />
							</h4>
							<div id="divMensajeErrorEliminacionRadicado"></div>
						</div>
						<fieldset style="margin-top: 5px;">
							<section>
								<div class="row text-center">
									<div class="col col-12">
										<label class="input">
											<textarea name="justificacionRadicado" id="justificacionRadicado" cols="64" rows="5"></textarea>
										</label>
									</div>
								</div>
							</section>
						</fieldset>
						<footer class="text-right" style="padding-right: 30px; padding-bottom: 5px">
							<button type="button" class="btn btn-white" onclick="cerrarJustificacionRadicado()">
								<spring:message code="label.maestroActividad.cancelar" />
							</button>
							<button type="button" class="btn btn-white" onclick="eliminarRadicado()">
								<spring:message code="label.maestroActividad.eliminar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
	<div id="modalConfEliminarBienAfectado" name="modalConfEliminarBienAfectado" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.confirmacion.bienesAfectados" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroActividad.cancelar" />
						</button>
						<button type="button" class="btn btn-white" onclick="irJustificacionBienAfectado()">
							<spring:message code="label.maestroActividad.eliminar" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modalJustificacionBienAfectado" name="modalJustificacionBienAfectado" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.confirmacion.miembro.justificacion" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
				</div>
				<div class="modal-body no-padding">
					<form class="smart-form">
						<div class="alert alert-danger alert-block hide" id="alertErrorEliminacionBienAfectado">
							<h4 class="alert-heading">
								<spring:message code="label.error" />
							</h4>
							<div id="divMensajeErrorEliminacionBienAfectado"></div>
						</div>
						<fieldset style="margin-top: 5px;">
							<section>
								<div class="row text-center">
									<div class="col col-12">
										<label class="input">
											<textarea name="justificacionBienAfectado" id="justificacionBienAfectado" cols="64"
												rows="5"></textarea>
										</label>
									</div>
								</div>
							</section>
						</fieldset>
						<footer class="text-right" style="padding-right: 30px; padding-bottom: 5px">
							<button type="button" class="btn btn-white" onclick="cerrarJustificacionBienAfectado()">
								<spring:message code="label.maestroActividad.cancelar" />
							</button>
							<button type="button" class="btn btn-white" onclick="eliminarBienAfectado()">
								<spring:message code="label.maestroActividad.eliminar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
	<div id="modalConfEliminarTarea" name="modalConfEliminarTarea" class="modal fade" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.confirmacion.tarea" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroActividad.cancelar" />
						</button>
						<button type="button" class="btn btn-white" onclick="irJustificacionTarea()">
							<spring:message code="label.maestroActividad.eliminar" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modalJustificacionTarea" name="modalJustificacionTarea" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.tarea.justificacion" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
				</div>
				<div class="modal-body no-padding">
					<form class="smart-form">
						<div class="alert alert-danger alert-block hide" id="alertErrorEliminacionTarea">
							<h4 class="alert-heading">
								<spring:message code="label.error" />
							</h4>
							<div id="divMensajeErrorEliminacionTarea"></div>
						</div>
						<fieldset style="margin-top: 5px;">
							<section>
								<div class="row text-center">
									<div class="col col-12">
										<label class="input">
											<textarea name="justificacionTarea" id="justificacionTarea" cols="64" rows="5"></textarea>
										</label>
									</div>
								</div>
							</section>
						</fieldset>
						<footer class="text-right" style="padding-right: 30px; padding-bottom: 5px">
							<button type="button" class="btn btn-white" onclick="cerrarJustificacionTarea()">
								<spring:message code="label.maestroActividad.cancelar" />
							</button>
							<button type="button" class="btn btn-white" onclick="eliminarTarea()">
								<spring:message code="label.maestroActividad.eliminar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
	<div id="modalConfEliminarActividad" name="modalConfEliminarActividad" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.confirmacion.actividad" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroActividad.cancelar" />
						</button>
						<button type="button" class="btn btn-white" onclick="irJustificacionActividad()">
							<spring:message code="label.maestroActividad.eliminar" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modalJustificacionActividad" name="modalJustificacionActividad" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.actividad.justificacion" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
				</div>
				<div class="modal-body no-padding">
					<form class="smart-form">
						<div class="alert alert-danger alert-block hide" id="alertErrorEliminacionActividad">
							<h4 class="alert-heading">
								<spring:message code="label.error" />
							</h4>
							<div id="divMensajeErrorEliminacionActividad"></div>
						</div>
						<fieldset style="margin-top: 5px;">
							<section>
								<div class="row text-center">
									<div class="col col-12">
										<label class="input">
											<textarea name="justificacionActividad" id="justificacionActividad" cols="64" rows="5" maxlength="255"></textarea>
										</label>
											<div id="textarea_feedback"></div>
									</div>
								</div>
							</section>
						</fieldset>
						<footer class="text-right" style="padding-right: 30px; padding-bottom: 5px">
							<button type="button" class="btn btn-white" onclick="cerrarJustificacionActividad()">
								<spring:message code="label.maestroActividad.cancelar" />
							</button>
							<button type="button" class="btn btn-white" onclick="eliminarActividad()">
								<spring:message code="label.maestroActividad.eliminar" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<!-- Ventanas modales -->
<%@ include file="/WEB-INF/jsp/seguridad/modalMensajeFinalizacionSesion.jsp"%>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<%@ include file="/WEB-INF/jsp/modales/modalModificarEstadoCaso.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalModificarDetalleCaso.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalModificarDemandado.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalAdicionarMiembroEquipo.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalConfiguracionAlarmas.jsp"%>
	<%@ include file="/WEB-INF/jsp/casos/nuevoCaso.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalConfiguracionAlertas.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalIngresarOtroResponsable.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalIngresarOtroResponsableDetalleCaso.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalModificarVictimaDemandante.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalModificarRadicado.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalNombreActividad.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalConfirmarTipoCaso.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalConfirmarFechaHechos.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalConfirmarFechaCaducidad.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalJustificacionFechaLimite.jsp"%>
	<%@ include file="/WEB-INF/jsp/casos/AsociarArchivo.jsp"%>
	<%@ include file="/WEB-INF/jsp/casos/asociarArchivoPrestamo.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalModificarBienAfectado.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalModificarPrestamo.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalAdicionarPrestamo.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalAdicionarAbono.jsp"%>
	<%@ include file="/WEB-INF/jsp/modales/modalConfirmacionEstadoActividad.jsp"%>
	<%@ include file="/WEB-INF/jsp/archivos/modalAsociarArchivoAbono.jsp"%>
	
</c:if>