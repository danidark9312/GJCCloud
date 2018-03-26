<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modalJustificacionFechaLimite" name="modalJustificacionFechaLimite" class="modal fade"
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
						<div class="alert alert-danger alert-block hide" id="alertErrorCambiarFechaLimite">
							<h4 class="alert-heading">
								<spring:message code="label.error" />
							</h4>
							<div id="divMensajeErrorCambiarFechaLimite"></div>
						</div>
						<fieldset style="margin-top: 5px;">
							<section>
								<div class="row text-center">
									<div class="col col-12">
										<label class="input">
											<textarea name="justificacionFechaLimite" id="justificacionFechaLimite" cols="64" rows="5"></textarea>
										</label>
									</div>
								</div>
							</section>
						</fieldset>
						<footer class="text-right" style="padding-right: 30px; padding-bottom: 5px">
							<button type="button" class="btn btn-white" onclick="noCambiarFechaActual()">
								<spring:message code="label.maestroActividad.cancelar" />
							</button>
							<button type="button" class="btn btn-white" onclick="guardarJustificacion()">
								<spring:message code="label.boton.finalizacion.Sesion" />
							</button>
						</footer>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>