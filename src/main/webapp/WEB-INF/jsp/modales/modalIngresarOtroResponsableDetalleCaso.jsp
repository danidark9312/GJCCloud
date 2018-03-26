<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-ingresarOtroResponsableDetalleCaso" class="modal fade" style="z-index: 1100">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<label>
							<spring:message code="label.modal.otroResponsable" />
						</label>
					</h4>
				</div>
				<div class="modal-body">
					<div name="ingresarOtroResponsableDetalleCaso" id="ingresarOtroResponsableDetalleCaso">
						<div class="row">
							<div class="alert alert-danger" id="messageErroresOtrosResponsablesDetalleCaso" style="display: none"></div>
						</div>
						<div name="formularioNombreMiembroNaturalDetalleCaso" id="formularioNombreMiembroNaturalDetalleCaso"
							style="display: block;">
							<div class="row">
								<div class="col-sm-6 b-r">
									<label>
										<spring:message code="label.campo.nombresMiembro" />
									</label>
									<span class="text-danger"> *</span>
									<input type="text" class="form-control" id="txtNombresOtroResponsableDetalleCaso"
										name="txtNombresOtroResponsableDetalleCaso" maxlength="60">
								</div>
								<div class="col-sm-6">
									<label>
										<spring:message code="label.campo.apellidosMiembro" />
									</label>
									<span class="text-danger"> *</span>
									<input type="text" class="form-control" id="txtApellidosOtroResponsableDetalleCaso"
										name="txtApellidosOtroResponsableDetalleCaso" maxlength="30">
								</div>
							</div>
						</div>
						<div class="row" style="margin-top: 2px;">
							<div class="col-sm-6 b-r">
								<div name="formularioTelefonoDetalleCaso">
									<label>
										<spring:message code="label.campo.telefonoMiembro" />
									</label>
									<span class="text-danger"> *</span>
									<div class="input-group m-b">
										<span class="input-group-addon"><i class="fa fa-phone-square"></i></span>
										<input type="text" class="form-control" id="txtTelefonoOtroResponsableDetalleCaso"
											name="txtTelefonoOtroResponsableDetalleCaso" maxlength="15" data-mask="(999) 999-9999"
											placeholder="(___) ___-____">
									</div>
								</div>
							</div>
							<div class="col-sm-6 b-r">
								<div name="formularioCorreo">
									<label>
										<spring:message code="label.campo.correoMiembro" />
									</label>
									<span class="text-danger"> *</span>
									<div class="input-group m-b" style="width: 99%">
										<span class="input-group-addon"><i class="fa fa-at"></i></span>
										<input type="text" class="form-control" id="txtCorreoOtroResponsableDetalleCaso"
											name="txtCorreoOtroResponsableDetalleCaso" maxlength="60">
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 b-r">
								<label>
									<spring:message code="label.campo.direccionDeMiembro" />
								</label>
								<span class="text-danger"> *</span>
								<input type="text" class="form-control" id="txtDireccionOtroResponsableDetalleCaso"
									name="txDireccionOtroResponsableDetalleCaso">
							</div>
						</div>
						<br> <br>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-azul" data-dismiss="modal"
						onclick="limpiarFormularioOtroResponsableDetalleCaso()">
						<label>
							<spring:message code="label.maestroTipoCaso.cancelar" />
						</label>
					</button>
					<button type="button" class="btn btn-azul" onclick="guardarOpcionOtrosResponsablesDetalleCaso()">
						<label>
							<spring:message code="label.boton.guardar" />
						</label>
					</button>
				</div>
			</div>
		</div>
	</div>
</c:if>