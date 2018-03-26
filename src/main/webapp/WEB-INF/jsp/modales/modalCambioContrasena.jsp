<div id="modalCambioContrasena" name="modalCambioContrasena" class="modal fade" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 700px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-6 ">
							<h2 class="modal-title">
								<spring:message code="label.menu.administradores.cambiarContrasena" />
							</h2>
						</div>
						<div class="col-sm-6"></div>
					</div>
				</div>
				<div class="modal-body" style="background-color: white">
					<div class="row">
						<div class="alert alert-success" id="exitoCambioContrasena">
							<a class="alert-link" href="#"></a>
							<div id="msjExitoCambioContrasena"></div>
						</div>
						<div class="alert alert-danger" id="errorCambioContrasena">
							<a class="alert-link" href="#"></a>
							<div id="msjErrorCambioContrasena"></div>
						</div>
					</div>
					<form id="frmContrasena" name="frmContrasena">
						<div class="row">
							<div class="col-sm-12 ">
								<label>
									<spring:message code="label.menu.contrasena.input.contrasenaAnterior" />
								</label>
								<input id="contrasenaAnterior" name="contrasenaAnterior" class="form-control" type="password" maxlength="30"
									required="required" />
							</div>
							<div class="col-sm-12 ">
								<label>
									<spring:message code="label.menu.contrasena.input.contrasenaNueva" />
								</label>
								<input id="contrasenaNueva" name="contrasenaNueva" class="form-control" type="password" maxlength="30"
									required="required" />
							</div>
							<div class="col-sm-12 ">
								<label>
									<spring:message code="label.menu.contrasena.input.contrasenaConfirmacion" />
								</label>
								<input id="contrasenaConfirmacion" name="contrasenaConfirmacion" class="form-control" type="password" maxlength="30"
									required="required" />
							</div>
						</div>
						<div class="row">&nbsp;</div>
						<div class="row" style="text-align: right;">
							<div class="col-sm-12 ">&nbsp;</div>
							<div class="col-sm-10">
								<button type="button" class="btn btn-azul" data-dismiss="modal" style="width: 100px;">
									<spring:message code="label.menu.administradores.salir" />
								</button>
							</div>
							<div class="col-sm-2">
								<button type="button" class="btn btn-azul" onclick="Contrasena.cambiarContrasena();" id="cambiarContrasena"
									style="display: block;" style="width:100px;">
									<spring:message code="label.boton.guardar" />
								</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>