<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-modificarRadicado" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content" id="modalRadicado">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<label>
							<spring:message code="label.modal.modificarRadicado" />
						</label>
					</h4>
				</div>
				<div class="modal-body" id="agregarFormularioRadicado">
					<div class="alert alert-danger" id="messageErrorRadicado" name="messageErrorRadicado"
						style="display: none">How quickly daft jumping zebras vex.</div>
					<div id="formularioRadicado" name="formularioRadicado">
						<div class="row">
							<div class="col-sm-6 b-r">
								<label>
									<spring:message code="label.campo.numeroRadicado" />
								</label>
								<input type="text" class="form-control" id="numeroRadicadoMod" name="numeroRadicadoMod"
									maxlength="25">
								<input type="text" class="hide" id="numeroRadicadoHidden" name="numeroRadicadoHidden"
									maxlength="25">
							</div>
							<div class="col-sm-6">
								<label>
									<spring:message code="label.campo.instanciaRadicado" />
								</label>
								<select class="form-control" id="instanciaRadicadoMod" name="instanciaRadicadoMod">
								</select>
							</div>
							<div class="col-sm-2 hide">
								<div class="checkbox i-checks">
									<label>
										<input type="checkbox" id="RadicadoAcumulado" value=""
											onclick="mostrarCamposRadicadoAcumlado(this)" name="checkBoxRadicado">
										<i></i>
										<spring:message code="label.campo.esAcumulado" />
									</label>
								</div>
							</div>
						</div>
						<div name="formularioAcumulado" id="formularioAcumulado" style="display: none;">
							<div class="row">
								<div class="col-sm-6 b-r">
									<label>
										<spring:message code="label.campo.acomuladoCon" />
									</label>
								</div>
								<div class="col-sm-6 b-r">
									<label>
										<spring:message code="label.campo.numeroDeRadicadoPadre" />
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-6 b-r">
									<select class="form-control" id="txtacomuladoCon" name="txtacomuladoCon">
										<option value=""><spring:message code="label.campo.select" /></option>
										<option value="GJA"><spring:message code="software.title" /></option>
										<option value="Externo"><spring:message code="label.campo.externo" /></option>
									</select>
								</div>
								<div class="col-sm-6 b-r">
									<input type="text" class="form-control" id="txtNumeroRadicadoPadre"
										name="txtNumeroRadicadoPadre" maxlength="25">
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12 b-r">
									<label>
										<spring:message code="label.campo.acomuladoObservaciones" />
									</label>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12 b-r">
									<textarea class="col-sm-12 b-r" id="txtAcomuladoObservaciones"
										name="txtAcomuladoObservaciones" rows="3" maxlength="255"></textarea>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12 b-r"></div>
							</div>
						</div>
						<br>
						<hr class="hr-line-solid">
						<br>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-azul" data-dismiss="modal"
						onclick="limpiarCamposNuevosRadicados()">
						<label>
							<spring:message code="label.boton.cancelar" />
						</label>
					</button>
					<button type="button" class="btn btn-azul" onclick="guardarModificacionesRadicados()">
						<label>
							<spring:message code="label.boton.guardar" />
						</label>
					</button>
				</div>
			</div>
		</div>
	</div>
</c:if>