<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div id="modal-adicionarPrestamo" class="modal fade">
		<div class="modal-dialog modal-lg style="overflow-y: auto !important"">
			<div class="modal-content" id="modalPrestamoAdicionar">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<label>
							<spring:message code="label.adicionar.prestamo" />
						</label>
					</h4>
				</div>
				<div class="modal-body modal-scroll" id="agregarFormularioBienAfectado">
					<div class="alert alert-danger hide" id="errorAdicionarPrestamo" name="errorAdicionarPrestamo"></div>
					<div id="formularioPrestamoNuevo" name="formularioPrestamoNuevo">
						<div class="row">
							<div class="col-sm-8 b-r">
								<label>
									<spring:message code="label.campo.nombreDeudor" />
								</label>
							</div>
							<div class="col-sm-4">
								<label>
									<spring:message code="label.campo.identificacionDeudor" />
								</label>
							</div>
						</div>
						<div class="row">
							<input type="hidden" id="codigoCasoPrestamoModalAdicionar">
							<input type="hidden" id="cdusuariocreacionAdicionar">
							<div class="col-sm-8 b-r">
								<input class="form-control" id="txtnombreDeudorModalAdicionar"
									name="txtnombreDeudorModalAdicionar" maxlength="60">
							</div>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="txtIdentificacionDeudorModalAdicionar"
									name="txtIdentificacionDeudorModalAdicionar" maxlength="11">
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 ">
								<label>
									<spring:message code="label.campo.descripcionPrestamo" />
								</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 ">
								<textarea class="form-control" id="txtDescripcionPrestamoModalAdicionar"
									name="txtDescripcionPrestamoModalAdicionar" rows="2" maxlength="255"></textarea>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.campo.fechaPrestamo" />
								</label>
							</div>
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.campo.valorPrestamo" />
								</label>
							</div>
							<div class="col-sm-4">
								<label>
									<spring:message code="label.campo.porcentajeInteresPrestamo" />
								</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4 b-r">
								<input type="date" class="form-control" id="txtFechaPrestamoModalAdicionar"
									name="txtFechaPrestamoModalAdicionar">
							</div>
							<div class="col-sm-4 b-r">
								<div class="input-group m-b">
									<span class="input-group-addon">$</span>
									<input type="text" maxlength="12" class="form-control" id="txtValorPrestamoModalAdicionar"
										name="txtValorPrestamoModalAdicionar">
									<span class="input-group-addon">.00</span>
								</div>
							</div>
							<div class="col-sm-4 ">
								<div class="input-group m-b">
									<span class="input-group-addon">%</span>
									<input type="text" class="form-control" maxlength="6"
										id="txtPorcentajeInteresPrestamoModalAdicionar"
										name="txtPorcentajeInteresPrestamoModalAdicionar">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.campo.tipoDeCuenta" />
								</label>
							</div>
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.campo.entidadFinanciera" />
								</label>
							</div>
							<div class="col-sm-4">
								<label>
									<spring:message code="label.campo.numeroDeCuenta" />
								</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4 b-r">
								<select class="form-control" id="txtTipoDeCuentaAdicionar" name="txtTipoDeCuentaAdicionar">
								</select>
							</div>
							<div class="col-sm-4 b-r">
								<select class="form-control" id="txtEntidadFinancieraAdicionar"
									name="txtEntidadFinancieraAdicionar">
								</select>
							</div>
							<div class="col-sm-4">
								<div>
									<input type="text" class="form-control" id="txtNumeroDeCuentaModalAdicionar"
										name="txtNumeroDeCuentaModalAdicionar" maxlength="11">
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.campo.intereses" />
								</label>
							</div>
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.campo.saldoPrestamo" />
								</label>
							</div>
							<div class="col-sm-4">
								<label>
									<spring:message code="label.campo.tituloValor" />
								</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4 b-r">
								<div class="input-group m-b">
									<span class="input-group-addon">$</span>
									<input type="text" maxlength="12" class="form-control" id="txtInteresesModalAdicionar"
										name="txtInteresesModalAdicionar">
									<span class="input-group-addon">.00</span>
								</div>
							</div>
							<div class="col-sm-4 b-r">
								<div class="input-group m-b">
									<span class="input-group-addon">$</span>
									<input type="text" maxlength="12" class="form-control" id="txtSaldoPrestamoModalAdicionar"
										name="txtSaldoPrestamoModalAdicionar">
									<span class="input-group-addon">.00</span>
								</div>
							</div>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="txtTituloValorModalAdicionar"
									name="txtTituloValorModalAdicionar" maxlength="30">
							</div>
						</div>
						<div class="row"></div>
						<div class="row"></div>
						<div class="row">
							<div class="col-sm-12"></div>
						</div>
						<br>
						<div class="row">
							<div class="col-sm-11"></div>
							<div class="col-sm-1"></div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-azul" data-dismiss="modal" onclick="limpiarPrestamo()">
						<label>
							<spring:message code="label.boton.cancelar" />
						</label>
					</button>
					<button type="button" class="btn btn-azul" onclick="nuevoPrestamo()">
						<label>
							<spring:message code="label.boton.guardar" />
						</label>
					</button>
				</div>
			</div>
		</div>
	</div>
</c:if>