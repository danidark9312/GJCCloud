<!-- BUSCAR TIPO CASO -->
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div id="modal-buscarTipoCaso" name="modal-buscarTipoCaso" class="modal fade" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 330px; height: 50px;">
				<div class="modal-body" style="background-color: white">
					<form id="detalleDelCaso" name="detalleDelCaso">
						<div class="row">
							<div class="col-sm-6 " style="text-align: center;">
								<h2 class="modal-title">
									<spring:message code="label.maestroTipoCaso.tituloTipoCaso" />
								</h2>
							</div>
							<div class="col-sm-6"></div>
						</div>
						<div class="row">
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.maestroTipoCaso.nombre" />
								</label>
							</div>
							<div class="col-sm-8 b-r">
								<input id="buscarTipoCaso" name="buscarTipoCaso" type="text" onclick="buscarTipoCaso()"
									onchange="buscarTipoCaso()" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<!-- ACTUALIZAR TIPO CASO -->
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-actualizarTipoCaso" name="modal-actualizarTipoCaso" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 750px; height:350px">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-6 ">
							<h2 class="modal-title">
								<spring:message code="label.maestroTipoCaso.titulo" />
							</h2>
						</div>
						<div class="col-sm-6"></div>
					</div>
				</div>
				<div class="modal-body" style="background-color: white">
					<div class="row">
						<div class="alert alert-success" id="messageExitosoModal">
							&nbsp;
							<a class="alert-link" href="#">&nbsp;</a>
							.
						</div>
						<div class="alert alert-danger" id="messageErrorModal">
							&nbsp;
							<a class="alert-link" href="#">&nbsp;</a>
							.
						</div>
					</div>
					<form id="frmTiposCasos" name="frmTiposCasos" role="form">
						<div class="form-group ">
							<div class="row">
								<div class="col-sm-3 ">
									<label>
										<spring:message code="label.maestroTipoCaso.nombre" />
									</label>
								</div>
								<div class="col-sm-9 ">
									<input id="nombreTipoCaso" name="nombreTipoCaso" class="form-control" type="text"
										maxlength="30"x/>
									<input id="codigoTipoCaso" name="codigoTipoCaso" type="hidden" />
								</div>
							</div>
						</div>
						<div class="form-group ">
							<div class="row">
								<div class="col-sm-3" id="campoUsuarioEstado" name="campoUsuarioEstado">
									<label>
										<spring:message code="label.maestroUsuario.estadoUsuario" />
									</label>
								</div>
								<div class="col-sm-9">
									
									<select id="estadoUsuario" name="estadoUsuario" data-placeholder="Seleccione un estado"
										 class="form-control">									
											<option value="S">Activo</option>
											<option value="N">Inactivo</option>										
									</select>
								</div>
							</div>
						</div>
						<div class="form-group ">
							<div class="row"">
								
									<div class="col-sm-3 ">
										<label>
											<spring:message code="label.maestroTipoCaso.actividad" />
										</label>
									</div>
									<div class="col-sm-5">
										<select data-placeholder="Seleccione una o varias actividades..." class="form-control"
												name="actividadTipoCaso" id="actividadTipoCaso">
												<c:forEach items="${actividades}" var="actividad">
													<option value="${actividad.cdactividad}">${actividad.dsactividad}</option>
												</c:forEach>
											</select>
									</div>
									
									<div class="col-sm-4" style="text-align: right;">
										<a href="javascript:adicionarActividad()" class="btn btn-success" style="background-color: #449D44;"> <i
											class="glyphicon glyphicon-plus"></i>&nbsp;<spring:message code="label.maestroTipoCaso.adicionarActividad" />
										</a>
									</div>
									<div class="row">
										<div class="col-sm-12">
											<table id="tablaActividades" name="tablaActividades" class="table table-striped">
												<thead>
													<tr>
														<th style="width: 60%"><spring:message code="label.maestroTipoCaso.actividad" /></th>
														<th style="width: 20%"><spring:message code="label.maestroTipoCaso.requiereFechaVencimiento" /></th>
														<th style="width: 20%"><spring:message code="label.maestroActividad.accion" /></th>
													</tr>
												</thead>
											</table>
										</div>
									</div>
									
									
									
							</div>
						</div>
						<div class="row"">
							<div class="col-sm-12 ">&nbsp;</div>
							<div class="col-sm-7">&nbsp;</div>
							<div class="col-sm-2">
								<button type="button" class="btn btn-azul" data-dismiss="modal" style="width: 100px;">
									<spring:message code="label.maestroTipoCaso.salir" />
								</button>
							</div>
							<div class="col-sm-2">
								<button type="button" class="btn btn-azul" onclick="guardar()" id="guardarTipoCaso"
									style="width: 100px;">
									<spring:message code="label.boton.guardar" />
								</button>
							</div>
						</div>
					</form>
					
				</div>				
			</div>
		</div>
	</div>
</c:if>
<!-- ELIMINAR TIPO CASO -->
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
	<div id="modal-EliminarTipoCaso" name="modal-EliminarTipoCaso" class="modal fade" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1 "></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.maestroTipoCaso.mensajeEliminarTipoCaso" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroTipoCaso.cancelar" />
						</button>
						<button type="button" class="btn btn-white" data-dismiss="modal"
							onclick="eliminarTipoCasoSeguro()">
							<spring:message code="label.maestroTipoCaso.eliminar" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<!-- NO ELIMINAR TIPO CASO -->
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
	<div id="modal-NoEliminarTipoCaso" name="modal-NoEliminarTipoCaso" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1 "></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.maestroTipoCaso.mensajeEliminarTipoCasoNoEliminar" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroTipoCaso.salir" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<!-- NO MODIFICAR TIPO CASO -->
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-NoModificarTipoCaso" name="modal-NoModificarTipoCaso" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1 "></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.maestroTipoCaso.mensajeEliminarTipoCasoNoModificar" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroTipoCaso.salir" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>