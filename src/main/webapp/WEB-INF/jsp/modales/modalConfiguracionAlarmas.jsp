<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modalConfAlarmas" name="modalConfAlarmas" style="z-index: 2000" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.modal.alarmas" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="alert alert-danger hide" id="errorAlertas"></div>
					</div>
					<div class="row" style="margin-bottom: 1em;">
						<div class="col-md-6">
							<label>
								<spring:message code="label.alertas.numero.diasAntes" />
							</label>
							<input type="text" class="form-control" id="confNumeroDias" name="confNumeroDias"
								onkeydown='return soloNumeros(event)' maxlength="3">
						</div>
						<div class="col-md-6">
							<label>
								<spring:message code="label.alertas.numero.notificacionesDia" />
							</label>
							<input type="text" class="form-control" id="confNumeroAlertas" name="confNumeroAlertas"
								onkeydown='return soloNumeros(event)' maxlength="3">
						</div>
					</div>
					<div class="row">
						<div class="col-md-12" id="tableAlertas"></div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-white" data-dismiss="modal" onclick="">
						<spring:message code="label.maestroActividad.cancelar" />
					</button>
					<button type="button" class="btn btn-white" onclick="guardarAlertas()">
						<spring:message code="label.boton.finalizacion.Sesion" />
					</button>
				</div>
			</div>
		</div>
	</div>
</c:if>