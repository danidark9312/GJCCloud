<div id="modal-buscarActividad" name="modal-buscarActividad" class="modal fade" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content" style="width: 330px; height: 50px;">
			<div class="modal-body" style="background-color: white">
				<form id="detalleDelCaso" name="detalleDelCaso">
					<div class="row">
						<div class="col-sm-6 " style="text-align: center;">
							<h2 class="modal-title">
								<spring:message code="label.maestroActividad.tituloActividad" />
							</h2>
						</div>
						<div class="col-sm-6"></div>
					</div>
					<div class="row">
						<div class="col-sm-4 b-r">
							<label>
								<spring:message code="label.maestroActividad.nombre" />
							</label>
						</div>
						<div class="col-sm-8 b-r">
							<input id="buscarActividad" name="buscarActividad" type="text" onclick="buscarActividad()"
								onchange="buscarActividad()" />
						</div>
					</div>
				</form>
			</div>
		</div>

		<div id="modal-actualizarActividad" name="modal-actualizarActividad" class="modal fade" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content" style="width: 700px;">
					<div class="modal-header">
						<div class="row">
							<div class="col-sm-6 ">
								<h2 class="modal-title">
									<spring:message code="label.maestroActividad.titulo" />
								</h2>
							</div>
							<div class="col-sm-6"></div>
						</div>
					</div>
					<div class="modal-body modal-scroll" style="background-color: white">
						<div class="row">
							<div class="alert alert-success" id="messageExitosoModal">
								&nbsp;<a class="alert-link" href="#">&nbsp;</a>.
							</div>
							<div class="alert alert-danger" id="messageErrorModal">
								&nbsp;<a class="alert-link" href="#">&nbsp;</a>.
							</div>
						</div>
						<form id="frmActividades" name="frmActividades">
							<div class="row">
								<div class="col-sm-3 ">
									<label>
										<spring:message code="label.maestroActividad.nombre" />
									</label>
								</div>
								<div class="col-sm-9 ">
									<input id="nombreActividad" name="nombreActividad" class="form-control" type="text" maxlength="30" />
									<input id="codigoActividad" name="codigoActividad" type="hidden" />
								</div>
							</div>
						</form>
						<div class="row">&nbsp;</div>
						<div class="panel panel-default">
							<div class="panel-heading">
								<div class="row">
									<div class="col-sm-9">
										<spring:message code="label.maestroActividad.adicionarTarea" />
									</div>
									<div class="col-sm-3" style="text-align: right;">
										<a class="btn btn-success btn-circle btn-xs" href="javascript:void(0);" onclick="mostrarModalAlertas();">
											<i class="glyphicon glyphicon-plus"></i>
										</a>
									</div>
								</div>
							</div>
							<div class="panel-body">
								<form id="nuevasTareas" name="nuevasTareas">
									<div class="col-sm-3" id="campoTareaNombre" name="campoTareaNombre">
										<label>
											<spring:message code="label.maestroActividad.tarea" />
										</label>
										<input id="nombreTarea" name="nombreTarea" type="text" class="form-control" required="required" maxlength="30" />
										<input id="codigoTarea" name="codigoTarea" type="hidden" />
										<input id="codigoTareaActividad" name="codigoTareaActividad" type="hidden" value="-1" />
									</div>
									
									<div class="col-sm-4" id="campoTareaDetalle" name="campoTareaDetalle">
										<label>
											<spring:message code="label.maestroActividad.detalleTarea" />
										</label>
										<input id="detalleTarea" name="detalleTarea" type="text" class="form-control" required="required"
											maxlength="255" />
									</div>
									<div class="col-sm-5" id="campoTareaNombre" name="campoTareaNombre">
										<label>
											<spring:message code="label.maestroTipoCaso.requiereFechaVencimiento" />
										</label>
										<input id="chckFechaVencimientoObligatoria" name="chckFechaVencimientoObligatoria" type="checkbox" class="form-control"/>
									</div>
									<div class="col-sm-12">&nbsp;</div>
									<div class="col-sm-12" style="text-align: right;">
										<a href="javascript:adicionarTarea()" class="btn btn-success" style="background-color: #449D44;"> <i
											class="glyphicon glyphicon-plus"></i>&nbsp;<spring:message code="label.maestroActividad.adicionarTarea" />
										</a>
									</div>
									<div class="col-sm-12">
										<table id="tablaTareas" name="tablaTareas" class="table table-striped">
											<thead>
												<tr>
													<th style="width: 20%"><spring:message code="label.maestroActividad.tarea" /></th>
													<th style="width: 40%"><spring:message code="label.maestroActividad.detalleTarea" /></th>
													<th style="width: 30%"><spring:message code="label.maestroTipoCaso.requiereFechaVencimiento" /></th>
													<th style="width: 10%"><spring:message code="label.maestroActividad.accion" /></th>
												</tr>
											</thead>
										</table>
									</div>
								</form>
							</div>
						</div>
						<div class="row" style="text-align: right;">
							<div class="col-sm-12 ">&nbsp;</div>
							<div class="col-sm-10">
								<button type="button" class="btn btn-azul" data-dismiss="modal" style="width: 100px;">
									<spring:message code="label.maestroActividad.salir" />
								</button>
							</div>
							<div class="col-sm-2">
								<button type="button" class="btn btn-azul" onclick="guardar()" id="guardarActividad" style="display: block"
									style="width:100px;">
									<spring:message code="label.boton.guardar" />
								</button>
								<button type="button" class="btn btn-azul" onclick="guardar()" id="actualizarActividad" style="display: none"
									data-dismiss="modal" style="width:100px;">
									<spring:message code="label.boton.guardar" />
								</button>
							</div>
						</div>
						</form>
					</div>
				</div>
			</div>
		</div>

		<div id="modal-EliminarActividad" name="modal-EliminarActividad" class="modal fade" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content" style="width: 620px;">
					<div class="modal-header">
						<div class="row">
							<div class="col-sm-1 "></div>
							<div class="col-sm-10">
								<h2 class="modal-title">
									<spring:message code="label.maestroActividad.mensajeEliminarActividad" />
								</h2>
							</div>
							<div class="col-sm-1 "></div>
						</div>
						<div class="row" style="text-align: right;; padding-right: 12%;">
							<button type="button" class="btn btn-white" data-dismiss="modal">
								<spring:message code="label.maestroActividad.cancelar" />
							</button>
							<button type="button" class="btn btn-white" data-dismiss="modal" onclick="eliminarActividadSeguro()">
								<spring:message code="label.maestroActividad.eliminar" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="modal-EliminarTarea" name="modal-EliminarTarea" class="modal fade" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content" style="width: 620px;">
					<div class="modal-header">
						<div class="row">
							<div class="col-sm-1 "></div>
							<div class="col-sm-10">
								<h2 class="modal-title">
									<spring:message code="label.maestroActividad.mensajeEliminarTarea" />
								</h2>
							</div>
							<div class="col-sm-1 "></div>
						</div>
						<div class="row" style="text-align: right;; padding-right: 12%;">
							<button type="button" class="btn btn-white" data-dismiss="modal">
								<spring:message code="label.maestroActividad.cancelar" />
							</button>
							<button type="button" class="btn btn-white" data-dismiss="modal" onclick="eliminarTareaSeguro()">
								<spring:message code="label.maestroActividad.eliminar" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="modal-NoEliminarTarea" name="modal-NoEliminarTarea" class="modal fade" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content" style="width: 620px;">
					<div class="modal-header">
						<div class="row">
							<div class="col-sm-1 "></div>
							<div class="col-sm-10">
								<h2 class="modal-title">
									<spring:message code="label.maestroActividad.mensajeEliminarTareaNoEliminar" />
								</h2>
							</div>
							<div class="col-sm-1 "></div>
						</div>
						<div class="row" style="text-align: right;; padding-right: 12%;">
							<button type="button" class="btn btn-white" data-dismiss="modal">
								<spring:message code="label.maestroActividad.salir" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="modal-NoEliminarActividad" name="modal-NoEliminarActividad" class="modal fade" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content" style="width: 620px;">
					<div class="modal-header">
						<div class="row">
							<div class="col-sm-1 "></div>
							<div class="col-sm-10">
								<h2 class="modal-title">
									<spring:message code="label.maestroActividad.mensajeEliminarActividadNoEliminar" />
								</h2>
							</div>
							<div class="col-sm-1 "></div>
						</div>
						<div class="row" style="text-align: right;; padding-right: 12%;">
							<button type="button" class="btn btn-white" data-dismiss="modal">
								<spring:message code="label.maestroActividad.salir" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="modal-NoModificarTarea" name="modal-NoModificarTarea" class="modal fade" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content" style="width: 620px;">
					<div class="modal-header">
						<div class="row">
							<div class="col-sm-1 "></div>
							<div class="col-sm-10">
								<h2 class="modal-title">
									<spring:message code="label.maestroActividad.mensajeEliminarTareaNoModificar" />
								</h2>
							</div>
							<div class="col-sm-1 "></div>
						</div>
						<div class="row" style="text-align: right;; padding-right: 12%;">
							<button type="button" class="btn btn-white" data-dismiss="modal">
								<spring:message code="label.maestroActividad.salir" />
							</button>

						</div>
					</div>
				</div>
			</div>
		</div>

		<div id="modal-NoModificarActividad" name="modal-NoModificarActividad" class="modal fade" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content" style="width: 620px;">
					<div class="modal-header">
						<div class="row">
							<div class="col-sm-1 "></div>
							<div class="col-sm-10">
								<h2 class="modal-title">
									<spring:message code="label.maestroActividad.mensajeEliminarActividadNoModificar" />
								</h2>
							</div>
							<div class="col-sm-1 "></div>
						</div>
						<div class="row" style="text-align: right;; padding-right: 12%;">
							<button type="button" class="btn btn-white" data-dismiss="modal">
								<spring:message code="label.maestroActividad.salir" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>