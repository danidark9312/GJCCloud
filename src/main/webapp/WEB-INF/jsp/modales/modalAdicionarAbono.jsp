<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div id="modal-adicionarAbono" class="modal fade">
		<div class="modal-dialog modal-lg style="overflow-y: auto !important"">
			<div class="modal-content" id="modalAbonoAdicionar">
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
				<div class="modal-body" id="agregarFormularioBienAfectado">
					<div class="alert alert-danger hide" id="errorAdicionarAbono" name="errorAdicionarPrestamo"></div>
					<form id="formularioAbonoNuevo" name="formularioAbonoNuevo">
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
							<input type="hidden" id="codigoPrestamoAdicionarAbono" >
							<div class="col-sm-8 b-r">
								<input class="form-control" id="txtnombreDeudorModalAdicionarAbono" disabled
									name="txtnombreDeudorModalAdicionar" maxlength="60">
							</div>
							<div class="col-sm-4">
								<input type="text" class="form-control" id="txtIdentificacionDeudorModalAdicionarAbono" disabled
									name="txtIdentificacionDeudorModalAdicionar" maxlength="11">
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
								<input type="date" class="form-control" id="txtFechaPrestamoModalAdicionarAbono" disabled
									name="txtFechaPrestamoModalAdicionar">
							</div>
							<div class="col-sm-4 b-r">
								<div class="input-group m-b">
									<span class="input-group-addon">$</span>
									<input type="text" maxlength="12" class="form-control" id="txtValorPrestamoModalAdicionarAbono" disabled
										name="txtValorPrestamoModalAdicionarAbono">
									<span class="input-group-addon">.00</span>
								</div>
							</div>
							<div class="col-sm-4 ">
								<div class="input-group m-b">
									<span class="input-group-addon">%</span>
									<input type="text" class="form-control" maxlength="6" disabled
										id="txtPorcentajeInteresPrestamoModalAdicionarAbono"
										name="txtPorcentajeInteresPrestamoModalAdicionar">
								</div>
							</div>
						</div>
						
						
						<div class="row">
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.campo.saldoCapital" />
								</label>
							</div>
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.campo.saldoInteres" />
								</label>
							</div>
							
						</div>
						
						<div class="row">
							
							<div class="col-sm-4 b-r">
								<div class="input-group m-b">
									<span class="input-group-addon">$</span>
									<input type="text" maxlength="12" class="form-control" id="txtValorSaldoCapital" disabled
										name="txtValorSaldoCapital" value="0">
									<span class="input-group-addon">.00</span>
								</div>
							</div>
							<div class="col-sm-4 ">
								<div class="input-group m-b">
									<span class="input-group-addon">$</span>
									<input type="text" class="form-control" maxlength="6" disabled
										id="txtValorSaldoInteres" value="0"
										name="txtValorSaldoInteres">
										<span class="input-group-addon">.00</span>
								</div>
							</div>
						</div>


						<div class="row">
							<div class="col-sm-4 b-r">
								<label> <spring:message code="label.campo.fechaAbono" />
								</label>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4 b-r">
								<input type="date" class="form-control" id="txtFechaAbono" onblur="calcularSaldoCapitalInteres()"
									name="txtFechaAbono">
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.campo.abonoCapital" />
								</label>
							</div>
							<div class="col-sm-4">
								<label>
									<spring:message code="label.campo.abonoInteres" />
								</label>
							</div>
						</div>
						<div class="row">
							
							
								
							<div class="col-sm-4 b-r">
								<div class="input-group m-b">	
									<span class="input-group-addon">$</span>
									<input type="text" maxlength="12" class="form-control" id="txtAbonoCapital"
										name="txtAbonoCapital">
									<span class="input-group-addon">.00</span>
								</div>
							</div>
							<div class="col-sm-4">
								<div>
									<div class="input-group m-b">	
									<span class="input-group-addon">$</span>
									<input type="text" maxlength="12" class="form-control" id="txtAbonoIntereses"
										name="txtAbonoIntereses">
									<span class="input-group-addon">.00</span>
								</div>
								</div>
							</div>
						</div>

						<div class="row">
							<label>
									<spring:message code="label.campo.observacion" />
								</label>
						</div>
						<div class="row">
							<div class="col-sm-12 ">
								<textarea class="form-control" id="txtDescripcionPrestamoModalAdicionarAbono"
									name="txtDescripcionPrestamoModalAdicionar" rows="2" maxlength="255"></textarea>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-azul" data-dismiss="modal" onclick="limpiarPrestamo()">
						<label>
							<spring:message code="label.boton.cancelar" />
						</label>
					</button>
					<button type="button" class="btn btn-azul" onclick="nuevoAbono()">
						<label>
							<spring:message code="label.boton.guardar" />
						</label>
					</button>
					
				
			</div>
			</div>
		</div>
	</div>
</c:if>