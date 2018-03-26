<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-asignarNombreDetalle" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<label>
							<spring:message code="label.modal.agregarActividad" />
						</label>
					</h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12">
							<div class="alert alert-danger hide" id="errorActividadDetalle"></div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<label>
								<spring:message code="label.Actividad.particular.nombre" />
							</label>
							<input id="nombreActividadDetalle" name="nombreActividadDetalle" class="form-control type="text"
								onclick="" onchange="" maxlength="30" />
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-azul" onclick="limpiarModalNuevaActividad()">
						<label>
							<spring:message code="label.boton.cancelar" />
						</label>
					</button>
					<button type="button" class="btn btn-azul" onclick="agregarActividadesDesdeDetalle()">
						<label>
							<spring:message code="label.boton.guardar" />
						</label>
					</button>
				</div>
			</div>
		</div>
	</div>
</c:if>