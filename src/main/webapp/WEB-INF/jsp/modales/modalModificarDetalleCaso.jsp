<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-modificarDetalleCaso" class="modal fade">
		<div class="modal-dialog modal-lg" id="modificarDetalle">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<label>
							<spring:message code="label.modal.modificarDetalleCaso" />
						</label>
					</h4>
				</div>
				<div class="modal-body modal-scroll">
					<div class="alert alert-danger" id="messageErrorDetalleCaso" name="messageErrorDetalleCaso"
						style="display: none"></div>
					<div class="row">
						<div class="alert alert-danger" id="errorFechaFinDelosHechosMod" style="display: none"></div>
					</div>
					<div class="row">
						<div class="col-sm-4 b-r">
							<label>
								<spring:message code="label.campo.tipoCaso" />
							</label>
							<span class="text-danger"> *</span>
							<Select class="form-control" id="tipoCasoMod" name="tipoCasoMod" tabindex="2"
								onfocus="obtenerTipoCaso();" onchange="confirmarCambioTipoCaso();"></Select>
						</div>
						<div class="col-sm-8">
							<label>
								<spring:message code="label.campo.nombreCaso" />
							</label>
							<span class="text-danger"> *</span>
							<input type="text" class="form-control" id="nombreCasoMod" name="nombreCasoMod" maxlength="50">
						</div>
					</div>
					<div class=row>
						<div class="col-sm-4 b-r">
							<label>
								<spring:message code="label.campo.fechaDeLosHechos" />
							</label>
							<span class="text-danger"> *</span>
							<input type="date" class="form-control" id="fechaDeLosHechosMod" name="fechaDeLosHechosMod"
								onchange="validarFechaInicioHechosDetalleCaso()">
						</div>
						<div class="col-sm-4 b-r">
						<label> <spring:message code="label.campo.sinConteoActual" />
							</label>
							<input type="checkbox" class="form-control" id="chkSinConteoMod"
								onchange="validateCheckSinConteoModificacion(this)">
						</div>
						
					</div>
					
					<div class=row>
						
						<div class="col-sm-4 b-r">
							<label>
								<spring:message code="label.campo.fechaFinDeLosHechos" />
							</label>
							<span class="text-danger"> *</span>
							<input type="date" class="form-control" id="finFechaDeLosHechosMod"
								name="finFechaDeLosHechosMod" onblur="mostrarFechaCaducidadDetalleCaso()">
						</div>
						<div class="col-sm-4 b-r">
							<label>
								<spring:message code="label.campo.fechaDeCaducidad" />
							</label>
							<input type="date" class="form-control" id="fechaDeCaducidadMod" name="fechaDeCaducidadMod"
								disabled="true">
						</div>
					</div>
					<div class=row>
						<div class="col-sm-4 b-r">
							<label>
								<spring:message code="label.campo.paisDeLosHechos" />
							</label>
							<span class="text-danger"> *</span>
							<select class="form-control cmbsCiudadProcesos" id="paisDeLosHechosMod"
								name="paisDeLosHechosMod" tabindex="2"
								onchange="cargarDepartamentosPorPais(this,'departamentoDeLosHechosMod','municipioDeLosHechosMod')"></select>
						</div>
						<div class="col-sm-4 b-r">
							<label>
								<spring:message code="label.campo.departamentoDeLosHechos" />
							</label>
							<span class="text-danger"> *</span>
							<select class="form-control cmbsCiudadProcesos" id="departamentoDeLosHechosMod"
								name=" departamentoDeLosHechosMod"
								onchange="cargarCiudadPorDepartamentos(this,'municipioDeLosHechosMod')" tabindex="2"></select>
						</div>
						<div class="col-sm-4 b-r">
							<label>
								<spring:message code="label.campo.municipioDeLosHechos" />
							</label>
							<span class="text-danger"> *</span>
							<select class="form-control" id="municipioDeLosHechosMod" name="municipioDeLosHechosMod"
								tabindex="2"></select>
						</div>
					</div>
					<div class=row>
						<div class="col-sm-12">
							<label>
								<spring:message code="label.campo.direccionDeLosHechos" />
							</label>
							<span class="text-danger"> *</span>
							<div class="input-group m-b">
								<span class="input-group-addon"><i class="fa fa-map-marker"></i></span>
								<input type="text" class="form-control" id="direccionDeLosHechosMod"
									name="direccionDeLosHechosMod" maxlength="60">
							</div>
						</div>
					</div>
					<div class=row>
						<div class="col-sm-12">
							<label>
								<spring:message code="label.campo.resumenDeLosHechos" />
							</label>
							<textarea id="resumenDeLosHechosMod" name="resumenDeLosHechosMod" rows="3" maxlength="255"
								class="form-control"></textarea>
						</div>
					</div>
					<div class=row>
						<div class="col-sm-12">
							<label>
								<spring:message code="label.detalleCaso.fechaPrejudicial" />
							</label>
							<input type="date" class="form-control" id="txtFechaPrejudicialmod" name="txtFechaPrejudicialmod" onblur="validarFechaPrejudicial()">
						</div>
					</div>
					<!-- lugar del proceso -->
					<hr class="hr-line-solid">
					<div class="row">
						<div class="col-sm-12 b-r">
							<label>
								<spring:message code="label.titulo.lugarDespacho" />
							</label>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4 b-r">
							<label>
								<spring:message code="label.campo.paisHecho" />
							</label>
							<select class="form-control" id="paisProcesoMod" name="paisProcesoMod"
								onchange="cargarDepartamentosPorPais(this,'deparmentoProcesoMod','municipioProcesoMod')">
							</select>
						</div>
						<div class="col-sm-4 b-r">
							<label>
								<spring:message code="label.campo.departamentoHecho" />
							</label>
							<select class="form-control" id="deparmentoProcesoMod" name="deparmentoProcesoMod"
								onchange="cargarCiudadPorDepartamentos(this,'municipioProcesoMod')">
							</select>
						</div>
						<div class="col-sm-4">
							<label>
								<spring:message code="label.campo.municipioHecho" />
							</label>
							<select class="form-control" id="municipioProcesoMod" name="municipioProcesoMod">
							</select>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<label>
								<spring:message code="label.campo.direccionDeMiembro" />
							</label>
							<div class="input-group m-b">
								<span class="input-group-addon"><i class="fa fa-map-marker"></i></span>
								<input type="text" class="form-control" id="direccionDespachoMod" name="direccionDespachoMod"
									maxlength="60">
							</div>
						</div>
						<br>
					</div>
					<!-- lugar del proceso -->
					<!-- despacho del proceso -->
					<hr class="hr-line-solid">
					<div class="row">
						<div class="col-sm-12 b-r">
							<label>
								<spring:message code="label.titulo.despacho" />
							</label>
						</div>
					</div>
					<br>
					<div class="row">
						<div class="col-sm-4 b-r">
							<label>
								<spring:message code="label.campo.numeroDespacho" />
							</label>
							<input type="text" class="form-control" id="numeroDespachoMod" name="numeroDespachoMod"
								maxlength="30">
						</div>
						<div class="col-sm-8">
							<label>
								<spring:message code="label.campo.nombreFuncionario" />
							</label>
							<input type="text" class="form-control" id="nombreFuncionarioMod" name="nombreFuncionarioMod"
								maxlength="60">
						</div>
					</div>
					<!-- despacho del proceso -->
					<hr class="hr-line-solid">
					<br>
					<div class="row">
						<div class="col-sm-12">
							<label>
								<spring:message code="label.campo.comentarios" />
							</label>
							<div class="input-group m-b">
								<span class="input-group-addon"><i class="fa fa-comments"></i></span>
								<textarea id="comentarioMod" name="comentarioMod" rows="3" maxlength="255"
									class="form-control"></textarea>
							</div>
						</div>
						<br>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-azul" data-dismiss="modal"
						onclick="limpiarFormularioDetalleCaso()">
						<label>
							<spring:message code="label.boton.cancelar" />
						</label>
					</button>
					<button type="button" class="btn btn-azul" onclick="guardarDetalleCaso()">
						<label>
							<spring:message code="label.boton.guardar" />
						</label>
					</button>
				</div>
			</div>
		</div>
	</div>
</c:if>