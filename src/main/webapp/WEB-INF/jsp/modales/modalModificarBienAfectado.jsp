<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-modificarBienAfectado" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content" id="modalBienAfectado">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<label>
							<spring:message code="label.modificar.bienAfectado" />
						</label>
					</h4>
				</div>
				<div class="modal-body" id="agregarFormularioBienAfectado">
					<div class="alert alert-danger" id="messageErrorBienAfectado" name="messageErrorBienAfectado"
						style="display: none"></div>
					<div id="formularioBienAfectado" name="formularioBienAfectado">
						<div class="row">
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.modificar.bienAfectado.nombre" />
								</label>
								<input type="text" class="form-control" id="txtNombreBien" name="txtNombreBien" maxlength="60">
								<input type="hidden" id="codigoCaso">
								<input type="hidden" id="codigoBienAfectado">
							</div>
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.modificar.bienAfectado.descripcion" />
								</label>
								<input type="text" class="form-control" id="txtDescripcionBien" name="txtDescripcionBien"
									maxlength="255">
							</div>
							<div class="col-sm-4">
								<label>
									<spring:message code="label.modificar.bienAfectado.tipo" />
								</label>
								<select class="form-control" id="cmbTipoBien" name="cmbTipoBien" maxlength="255">
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-azul" data-dismiss="modal"
						onclick="limpiarModalBienAfectado()">
						<label>
							<spring:message code="label.boton.cancelar" />
						</label>
					</button>
					<button type="button" class="btn btn-azul" onclick="modificarBienAfectado()">
						<label>
							<spring:message code="label.boton.guardar" />
						</label>
					</button>
				</div>
			</div>
		</div>
	</div>
</c:if>