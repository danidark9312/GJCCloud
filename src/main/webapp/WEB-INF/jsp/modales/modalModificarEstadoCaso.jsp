<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-modificarEstadoCaso" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<label>
							<spring:message code="label.modal.modificarEstado" />
						</label>
					</h4>
				</div>
				<div class="modal-body">
					<div class="alert alert-danger" id="messageErrorEstado" name="messageError" style="display: none">How
						quickly daft jumping zebras vex.</div>
					<div class="row">
						<div class="col-sm-4">
							<label>
								<spring:message code="label.campo.estadoCaso" />
							</label>
							<Select class="form-control" id="estadoCasoMod" name="estadoCasoMod" tabindex="2"></Select>
						</div>
					</div>
					<div class=row>
						<div class="col-sm-12">
							<label>
								<spring:message code="label.campo.justificacion" />
							</label>
							<textarea id="txtJustificacion" name="txtJustificacion" rows="3" maxlength="255"
								class="form-control"></textarea>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-azul" data-dismiss="modal"
						onclick="limpiarFormularioEstadoCaso()">
						<label>
							<spring:message code="label.boton.cancelar" />
						</label>
					</button>
					<button type="button" class="btn btn-azul" onclick="guardarEstado()">
						<label>
							<spring:message code="label.boton.guardar" />
						</label>
					</button>
				</div>
			</div>
		</div>
	</div>
</c:if>