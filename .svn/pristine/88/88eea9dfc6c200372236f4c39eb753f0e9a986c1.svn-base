<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div id="modalEscalamientoNotificacion" class="modal fade">
		<div class="modal-dialog modal-lg style="overflow-y: auto !important"">
			<div class="modal-content" id="modalEscalamientoNotificacion">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<label id="titleModalEscalamiento">
							
						</label>
					</h4>
				<%@ include file="/WEB-INF/jsp/maestros/maestroEscalamientoNotificacion.jsp"%>
				<div class="modal-footer">
					<button type="button" class="btn btn-azul" data-dismiss="modal">
						<label>
							<spring:message code="label.boton.cancelar" />
						</label>
					</button>
					<button class="btn btn-azul" type="button" id="guardarProximas"  onclick="guardarConfiguracionAntes();"><label>Guardar</label></button>
					<button class="btn btn-azul" type="button" id="guardarDiasDespues" hidden="hidden" onclick="guardarConfiguracionDespues();"><label>Guardar</label></button>

				</div>
			</div>
		</div>
	</div>
	</div>
</c:if>