<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modalConfFechaHechos" name="modalConfFechaHechos" class="modal fade" aria-hidden="true"
		style="z-index: 1100">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.confirmacion.fechaHechos" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" onclick="cancelarActualizacionFechas()"
							data-dismiss="modal">
							<spring:message code="label.maestroActividad.cancelar" />
						</button>
						<button type="button" class="btn btn-white" data-dismiss="modal"
							onclick="modificarDetalleCaso()">
							<spring:message code="label.boton.finalizacion.Sesion" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>