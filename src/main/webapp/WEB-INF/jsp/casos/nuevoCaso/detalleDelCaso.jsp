<form id="detalleDelCaso" name="detalleDelCaso">
	<div class="row">
		<div class="col-sm-4 b-r"></div>
		<div class="col-sm-8"></div>
	</div>
	<div class="row">
		<div class="col-sm-4 b-r">
			<label>
				<spring:message code="label.campo.tipoCaso" />
			</label>
			<span class="text-danger"> *</span>
			<Select class="form-control" id="txtTipoCaso" name="txtTipoCaso"
				onchange="cargarActividadesTipoCaso(this)"></Select>
		</div>
		<div class="col-sm-8">
			<label>
				<spring:message code="label.campo.nombreCaso" />
			</label>
			<span class="text-danger"> *</span>
			<input type="text" class="form-control" id="txtNombreCaso" name="txtNombreCaso" maxlength="50">
		</div>
		
	</div>
	
	<div class=row>
		<!-- fecha inicio y fin de los hechos -->
		<div class="col-sm-4 b-r">
			<label>
				<spring:message code="label.campo.fechaDeLosHechos" />
			</label>
			<span class="text-danger"> *</span>
			<input type="date" class="form-control" id="txtFechaDeLosHechos" name="txtFechaDeLosHechos"
				onfocusout="if(validarFechaMenorIgualActual(this.id)){validarFechaInicioHechos();mostrarFechaCaducidad();}">
		</div>
		<div class="col-sm-4 b-r">
			<label>
				<spring:message code="label.campo.fechaFinDeLosHechos" />
			</label>
			<span class="text-danger"> *</span>
			<input type="date" class="form-control" id="txtFinFechaDeLosHechos" name="txtFinFechaDeLosHechos"
				onfocusout="if(validarFechaMenorIgualActual(this.id)){mostrarFechaCaducidad();}">
		</div>
<!-- 		<div class="col-sm-3"> -->
			
<!-- 		</div> -->
		<div class="col-sm-2" style="text-align: center;">
			<label>
				<spring:message code="label.campo.sinConteoActual" />
			</label>
			<input type="checkbox" class="form-control" id="chkSinConteo"
				onchange="validateCheckSinConteo(this)">
		</div>
		
		<!-- fecha inicio y fin de los hechos -->
		<div class="col-sm-4 b-r" style="display:none">
			<label>
				<spring:message code="label.detalleCaso.tipoRadicado" />
			</label>
			<select class="form-control cmbsCiudadProcesos" id="tipoRadicado"
				name="tipoRadicado"
				onchange="validateTipoRadicado(this)">
				<option value=1>Sin radicado acumulado</option>
				<option value=2>Caso Grupo Jurídico</option>
				<option value=3>Caso Externo</option>
			</select>
		</div>
		
		<div class="col-sm-4 b-r" style="display:none">
			<label>
				<spring:message code="label.detalleCaso.radicadoAsociado" />
			</label>
			<input type="text" class="form-control" id="txtRadicadoAsociado" name="txtRadicadoAsociado"  style="display:none"/>
			<input type="text" class="form-control" id="txtAutoCompleteRadicados" name="txtAutoCompleteRadicados" style="display:none" onchange="validateInputAutocomplete(this)" />
		</div>
		
		
	</div>
	
	
	
	<div class=row>
		<!-- fecha inicio y fin de los hechos -->
		<div class="col-sm-4 b-r">
			<label>
				<spring:message code="label.campo.fechaDeCaducidad" />
			</label>
			<input type="date" class="form-control" id="txtFechaDeCaducidad" name="txtFechaDeLosHechos" disabled="true">
		</div>
		<div class="col-sm-4 b-r">
			<label>
				<spring:message code="label.campo.fechaPrejudicial" />
			</label>
			<input type="date" class="form-control" id="txtFechaPrejudicial" name="txtFechaPrejudicial" onblur="validarFechaPrejudicial()">
		</div>
	</div>
	<div class=row>
		<div class="col-sm-4">
			<label>
				<spring:message code="label.campo.paisDeLosHechos" />
			</label>
			<span class="text-danger"> *</span> <select class="form-control cmbsCiudadProcesos" id="txtPaisDeLosHechos"
				name="txtPaisDeLosHechos"
				onchange="cargarDepartamentosPorPais(this,'txtDepartamentoDeLosHechos','txtMunicipioDeLosHechos')"></select>
		</div>
		<div class="col-sm-4">
			<label>
				<spring:message code="label.campo.departamentoDeLosHechos" />
			</label>
			<span class="text-danger"> *</span> <select class="form-control cmbsCiudadProcesos" id="txtDepartamentoDeLosHechos"
				name=" txtDepartamentoDeLosHechos" onchange="cargarCiudadPorDepartamentos(this,'txtMunicipioDeLosHechos')"></select>

		</div>
		<div class="col-sm-4 b-r">
			<label>
				<spring:message code="label.campo.municipioDeLosHechos" />
			</label>
			<span class="text-danger"> *</span> <select class="form-control" id="txtMunicipioDeLosHechos"
				name="txtMunicipioDeLosHechos"></select>
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
				<input type="text" class="form-control" id="txtDireccionDeLosHechos" name="txtDireccionDeLosHechos" maxlength="60">
			</div>
		</div>
	</div>
	<div class=row>
		<div class="col-sm-12">
			<label>
				<spring:message code="label.campo.resumenDeLosHechos" />
			</label>
			<textarea id="txtResumenDeLosHechos" name="txtResumenDeLosHechos" rows="3" maxlength="255" class="form-control"></textarea>

		</div>
	</div>
	
	<br>
	<hr class="hr-line-solid">
	<br>
	<!-- Fin bloque Nuevo Caso-->
	<!-- Inicio del bloque abogados -->
	<div class="row">
		<div class="col-sm-12 b-r">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapseAbogados"><spring:message
								code="label.titulo.abogados" /></a>
					</h4>
				</div>
				<div id="collapseAbogados" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="row">
							<div class="alert alert-info" id="infoAbogado">
								<spring:message code="label.info.abogados" />
								</a>
							</div>
						</div>
						<div class="row">
							<div class="alert alert-danger" id="messageErrorAbogado"></div>
						</div>
						<%@ include file="/WEB-INF/jsp/casos/abogado.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--  Fin del bloque abogados -->
	<!--  incio del bloque demandados -->
	<div class="row">
		<div class="col-sm-12 b-r">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapseDemandados"><spring:message
								code="label.titulo.demandados" /></a>
					</h4>
				</div>
				<div id="collapseDemandados" class="panel-collapse collapse">
					<div class="panel-body" id="panelDemandado">
						<div class="row">
							<div class="alert alert-danger" id="messageErrorDemandados"></div>
						</div>
						<%@ include file="/WEB-INF/jsp/casos/demandado.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!--  Fin del bloque demandos -->
	<!-- inicio bloque equipo del caso-->
	<div class="row">
		<div class="col-sm-12 b-r">
			<div class="panel-group" id="accordion">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion" href="#collapseSolicitud"><spring:message
									code="label.titulo.equipoCaso" /></a>
						</h5>
					</div>
					<div id="collapseSolicitud" class="panel-collapse collapse">
						<div class="panel-body" id="panelEquipoCaso">
							<div class="row">
								<div class="alert alert-info" id="infoEquipoCaso" name="infoEquipoCaso">
									<spring:message code="label.info.equipoCaso" />
								</div>

							</div>
							<div class="row">
								<div class="alert alert-danger" id="messageErrorEquipoCaso"></div>
							</div>
							<%@ include file="/WEB-INF/jsp/casos/equipoDelCaso.jsp"%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Fin bloque equipo del caso-->
	<!-- Inicio del bloque actividades-->
	<div class="row">
		<div class="col-sm-12 b-r">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#Actividad"><spring:message
								code="label.titulo.actividades" /></a>
					</h4>
				</div>
				<div id="Actividad" class="panel-collapse collapse">
					<div class="panel-body" id="actividades">
						<div class="row">
							<div class="alert alert-danger" id="messageErrorActividades"></div>
						</div>
						<%@ include file="/WEB-INF/jsp/casos/actividadParticularCaso.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Fin del bloque actividades-->

	<!--  Inicio del bloque lugar del proceso -->
	<div class="row">
		<div class="col-sm-12 b-r">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapseDespachoLugarProceso"><spring:message
								code="label.titulo.lugarDespacho" /></a>
					</h4>
				</div>
				<div id="collapseDespachoLugarProceso" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="row">
							<div class="alert alert-danger" id="messageErrorLugarProceso"></div>
						</div>
						<%@ include file="/WEB-INF/jsp/casos/despachoLugarProceso.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--  Fin del bloque lugar del proceso -->
	<!--  Inicio del bloque Despacho del proceso -->
	<div class="row">
		<div class="col-sm-12 b-r">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapseDespachoProceso"><spring:message
								code="label.titulo.despacho" /></a>
					</h4>
				</div>
				<div id="collapseDespachoProceso" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="row">
							<div class="alert alert-danger hide" id="messageErrorDespachoProceso"></div>
						</div>
						<%@ include file="/WEB-INF/jsp/casos/despachoProceso.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--  Fin del bloque Despacho del proceso -->
	<!--  Inicio del bloque Bienes Afectados -->
	<div class="row">
		<div class="col-sm-12 b-r">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapseBienesAfectados"><spring:message
								code="label.titulo.bienesAfectados" /></a>
					</h4>
				</div>
				<div id="collapseBienesAfectados" class="panel-collapse collapse">
					<div class="panel-body" id="panelBienesAfectados">
						<div class="row">
							<div class="alert alert-danger" id="messageErrorBienesAfectados"></div>
						</div>
						<%@ include file="/WEB-INF/jsp/casos/bienesAfectados.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--  Fin del bloque Bienes Afectados -->
	<!--  Incio del bloque Radicados -->
	<div class="row">
		<div class="col-sm-12 b-r">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapseRadicado"><spring:message
								code="label.titulo.radicados" /></a>
					</h4>
				</div>
				<div id="collapseRadicado" class="panel-collapse collapse">
					<div class="panel-body" id="panelRadicado">
						<div class="row">
							<div class="alert alert-danger" id="messageErrorRadicados"></div>
						</div>
						<%@ include file="/WEB-INF/jsp/casos/radicado.jsp"%>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--  Fin del bloque Radicados -->
	<!--  Inicio del bloque Prestamos -->
	<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
		<div class="row">
			<div class="col-sm-12 b-r">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion" href="#collapsePrestamo"><spring:message
									code="label.titulo.prestamos" /></a>
						</h4>
					</div>
					<div id="collapsePrestamo" class="panel-collapse collapse">
						<div class="panel-body" id="panelPrestamos">
							<div class="row">
								<div class="alert alert-danger" id="messageErrorPrestamos"></div>
							</div>
							<%@ include file="/WEB-INF/jsp/casos/prestamo.jsp"%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</c:if>
	<!--  Fin del bloque Prestamos -->
	<!-- inicio bloque comentario -->
	<div class="row">
		<div class="col-sm-12 b-r">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion" href="#collapseComentario"><spring:message
								code="label.titulo.comentario" /></a>
					</h4>
				</div>
				<div id="collapseComentario" class="panel-collapse collapse">
					<div class="panel-body">
						<div class="row">
							<div class="alert alert-danger" id="messageErrorComentarios"></div>
						</div>
						<div class=row>
							<div class="col-sm-12 b-r">
								<label>
									<spring:message code="label.campo.comentarios" />
								</label>
								<textarea class="form-control" id="txtComentario" name="txtComentario" rows="3" maxlength="255"></textarea>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- fin bloque comentario -->
	<div class="modal-footer">
		<button type="button" class="btn btn-azul" data-dismiss="modal" onclick="modalClose()" style="width: 15%">
			<label>
				<spring:message code="label.boton.cancelar" />
		</button>
		<button type="button" class="btn btn-azul" onclick="guardar()" style="width: 15%">
			<label>
				<spring:message code="label.boton.guardar" />
			</label>
		</button>
	</div>
</form>