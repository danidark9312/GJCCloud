<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div aria-hidden="true" id="modalConfAlertas" name="modalConfAlertas" class="modal fade"
		style="z-index: 3000">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1"></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.modal.alertas" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
				</div>
				<div class="modal-body">
					<div class="panel-group" id="#accordionAlerta">
						<!-- ANTES DE -->
						<div class="panel panel-default">
							<div class="panel-heading">
								<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordionAlerta" href="#antesDe">
										<spring:message code="label.alertas.antesDe" />
									</a>
								</h5>
							</div>
							<div id="antesDe" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="row">
										<div class="alert alert-danger hide" id="errorAlertasAntesDe"></div>
									</div>
									<div class="row" style="margin-bottom: 1em;">
										<div class="col-md-6">
											<label>
												<spring:message code="label.alertas.antesDe" />
											</label>
										</div>
										<div class="col-md-6" style="text-align: right;">
											<label>
												<input type="checkbox" name="alertaEstado" id="alertaEstado" checked>
											</label>
										</div>
									</div>
									<div class="row" style="margin-bottom: 1em;">
										<div class="col-md-6">
											<label>
												<spring:message code="label.alertas.numero.diasAntes" />
											</label>
											<input type="text" class="form-control" id="confNumeroDias" name="confNumeroDias"
												onkeydown='return soloNumeros(event)' maxlength="3">
										</div>
										<div class="col-md-6">
											<label>
												<spring:message code="label.alertas.numero.notificacionesDia" />
											</label>
											<input type="text" class="form-control" id="confNumeroAlertas" name="confNumeroAlertas"
												onkeydown='return soloNumeros(event)' maxlength="3">
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<table class="table table-striped table-hover">
												<thead>
													<tr>
														<td colspan="2">
															<spring:message code="label.alertas.roles.rol" />
														</td>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="rol" items="${listaRol}" begin="0" varStatus="status">
														<tr>
															<td>${rol.descripcion}</td>
															<td style="text-align: right;">
																<input name="rolesAlerta" type="checkbox" value="${rol.codigo}" />
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12"></div>
									</div>
									<div class="row">
										<div class="col-md-12" id="tableAlertas"></div>
									</div>
								</div>
							</div>
						</div>
						<!-- DESPUES DE -->
						<div class="panel panel-default">
							<div class="panel-heading">
								<h5 class="panel-title">
									<a data-toggle="collapse" data-parent="#accordionAlerta" href="#despuesDe">
										<spring:message code="label.alertas.despuesDe" />
									</a>
								</h5>
							</div>
							<div id="despuesDe" class="panel-collapse collapse">
								<div class="panel-body">
									<div class="row">
										<div class="alert alert-danger hide" id="errorAlertasDespuesDe"></div>
									</div>
									<div class="row" style="margin-bottom: 1em;">
										<div class="col-md-6">
											<label>
												<spring:message code="label.alertas.despuesDe" />
											</label>
										</div>
										<div class="col-md-6" style="text-align: right;">
											<label>
												<input type="checkbox" name="alertaEstadoDespues" id="alertaEstadoDespues" checked>
											</label>
										</div>
									</div>
									<div class="row" style="margin-bottom: 1em;">
										<div class="col-md-6">
											<label>
												<spring:message code="label.alertas.numero.diasDespues" />
											</label>
											<input type="text" class="form-control" id="confNumeroDiasDespues"
												name="confNumeroDiasDespues" onkeydown='return soloNumeros(event)' maxlength="3">
										</div>
										<div class="col-md-6">
											<label>
												<spring:message code="label.alertas.numero.notificacionesDia" />
											</label>
											<input type="text" class="form-control" id="confNumeroAlertasDespues"
												name="confNumeroAlertasDespues" onkeydown='return soloNumeros(event)' maxlength="3">
										</div>
									</div>
									<div class="row">
										<div class="col-md-12">
											<table class="table table-striped table-hover">
												<thead>
													<tr>
														<td colspan="2">
															<spring:message code="label.alertas.roles.rol" />
														</td>
													</tr>
												</thead>
												<tbody>
													<c:forEach var="rol" items="${listaRol}" begin="0" varStatus="status">
														<tr>
															<td>${rol.descripcion}</td>
															<td style="text-align: right;">
																<input name="rolesAlertaDespues" type="checkbox" value="${rol.codigo}" />
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
									<div class="row">
										<div class="col-md-12"></div>
									</div>
									<div class="row">
										<div class="col-md-12" id="tableAlertas"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-white" data-dismiss="modal" onclick="guardarAlertas('NO')">
						<spring:message code="label.maestroActividad.cancelar" />
					</button>
					<button type="button" class="btn btn-white" onclick="guardarAlertas()">
						<spring:message code="label.boton.finalizacion.Sesion" />
					</button>
				</div>
			</div>
		</div>
	</div>
</c:if>