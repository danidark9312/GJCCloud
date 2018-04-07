<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
<div id="tab-3" class="tab-pane">
	<div class="alert alert-success alert-block hide" id="alertSucessActividad">
		<div id="divMensajeSuccessActividad"></div>
		<div id="divMensajeSuccessActividad2"></div>
	</div>
	<div class="alert alert-success alert-block hide" id="alertSucessTarea">
		<div id="divMensajeSuccessTarea"></div>
	</div>
	<div class="alert alert-danger alert-block hide" id="errorActividadMod"></div>
	<div class="wrapper wrapper-content  animated fadeInRight">
		<div class="row" id="sortable-view">
			<c:set var="filaActividad" value="0" scope="page" />
			
			
			
			<c:set var="vencidas" value="${actividadGroups.vencidas}" scope="page" />
			<c:set var="pendientes" value="${actividadGroups.pendientes}" scope="page" />
			<c:set var="completadas" value="${actividadGroups.completadas}" scope="page" />
			
				
				<div class="panel panel panel-success">
					<div class="panel-heading">Actividades Completadas</div>
					<div class="panel-body" id="panelActividadesCompletadas">
						<c:forEach items="${completadas}" var="actividad">
						<c:if test="${actividad.snActiva ==  'S'}">
						<div class="col-sm-12 show" name="actividadParticular" order="${actividad.orden}" code="${actividad.codigoActividadParticular}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="row">
										<div class="col-sm-9">
											<h4 class="panel-title">
												<input hidden="hidden" name="txtCodigoActividadCaso" value="${actividad.codigoActividadParticular}" />
												<a data-toggle="collapse" data-parent="#accordion"
													href="#collapseActividad${actividad.codigoActividadParticular}"> <%-- <input type="text"  id="nombreActividad" name="nombreActividad" value="${actividad.nombreActividad}" disabled="true"  class="form-control" > --%>
													<label id="nombreActividad" name="nombreActividad">${actividad.nombreActividad}</label>
												</a>
											</h4>
										</div>
										<div class="col-sm-3 text-right">
											<c:if test="${actividad.finalizada !='V'}">
												<a href="javascript:void(0);" onclick="habilitarCamposActividad(${filaActividad},this)"
													class="btn btn-info btn-circle .btn-xs" title="Editar Actividad" name="btnEditarActividad"
													alt="borrar actividad"> <i class="glyphicon glyphicon-pencil" id="btnActividad" name="btnActividad"></i>
												</a>
											</c:if>
	<%-- 											<a href="javascript:void(0);" onclick="cancelarCamposActividad(${filaActividad},this)" --%>
	<!-- 												class="btn btn-danger btn-circle .btn-xs hide" title="Cancelar Edicion de actividades" id="btnCancelar" name="btnCancelar"> -->
	<!-- 												<i class="	glyphicon glyphicon-remove"></i> -->
	<!-- 											</a> -->
												<a href="javascript:void(0);" onclick="irConfirmacionActividad(${actividad.codigoActividadParticular})"
													class="btn btn-danger btn-circle .btn-xs" title="Eliminar Actividad" name="btnActividad"
													alt="borrar actividad"> <i class="glyphicon glyphicon-trash" id="btnActividad" name="btnActividad"></i>
												</a>
											<c:if test="${actividad.finalizada !='V'}">
												<a href="javascript:void(0);" onclick="adicionarTareaDetalle(this, ${filaActividad})"
													class="btn btn-success btn-circle .btn-xs" title="Adicionar Tarea" name="btTareas" alt="Adicionar tarea">
													<i class="glyphicon glyphicon-plus" id="btnTareas" name="btnTareas"></i>
												</a>
												<a href="javascript:void(0);" onclick="modificarAlertas(this)"
													class="btn btn-primary btn-circle .btn-xs hide" title="Modificar Alertas" id="btnAlertas" name="btnAlertas">
													<i class="glyphicon glyphicon-cog"></i>
												</a>											
											</c:if>
										</div>
									</div>
								</div>
	
								<div id="collapseActividad${actividad.codigoActividadParticular}" class="panel-collapse collapse">
									<div class="panel-body" id="panelActividad${actividad.codigoActividadParticular}">
										<div class="row">
											<div class="col-sm-3" style="margin-bottom: 1%;">
												<label>
													<spring:message code="label.campo.numeroDeDias" />
												</label>
												<input type="text" class="form-control" id="txtNumerosdiasAntesActividad"
													onkeydown="return soloNumeros(event)" name="txtNumerosdiasAntesActividad" maxlength="3"
													value="${actividad.numeroDiasAntesAlertas}" disabled="true">
											</div>
											<div class="col-sm-3" style="margin-bottom: 1%;">
												<label>
													<spring:message code="label.campo.numeroDeNotificaciones" />
												</label>
												<input type="text" class="form-control" id="txtNumeroNotificacionesActividad"
													onkeydown="return soloNumeros(event)" name="txtNumeroNotificacionesActividad" maxlength="3"
													value="${actividad.numeroAlertasPorDia}" disabled="true">
												<input type="hidden" class="form-control" id="txtEsActividadPropia" name="txtEsActividadPropia"
													value="${actividad.esActividadPropia}">
											</div>
											<div class="col-sm-3" style="margin-bottom: 1%;">
												<div class="alert alert-danger" id="errorFechaMenorQueTareas" name="errorFechaMenorQueTareas" style="display: none">""</div>
												<label>
													<spring:message code="label.campo.fechaVencimientoActividad" />
												</label>
												<span class="text-danger"> *</span>
												<input type="date" id="actividadVencimiento${filaActividad}" name="actividadVencimiento${filaActividad}"
													value="${actividad.fechaLimite}" disabled="true" class="form-control"
													onclick="guardarFechaLimiteActual(this,true)" onblur="modificarFechalimite(this,true)" onkeydown="return true">
											</div>
											<div class="col-sm-3" style="margin-bottom: 1%;">
												<label>
													<spring:message code="label.campo.estadoActividad" />
												</label>
												<span class="text-danger"> *</span> <select class="form-control" id="cmbEstadoActividad"
													onchange="validarEstadoTareas(this)" onfocus="obtenerEstadoActual(this)" name="cmbEstadoActividad"
													disabled="true">
													<c:if test="${actividad.finalizada =='N'}">
														<option value="${actividad.finalizada}">Pendiente</option>
													</c:if>
													<c:if test="${actividad.finalizada =='S'}">
														<option value="${actividad.finalizada}">Completada</option>
													</c:if>
													<c:if test="${actividad.finalizada =='V'}">
														<option value="${actividad.finalizada}">Vencida</option>
													</c:if>
												</select>
											</div>
										</div>
										<div class="row">
											<div class="alert alert-danger hide" name="actividadError"></div>
										</div>
										<div class="row">
											<table class="table table-bordered table-actividades" id="tabla${actividad.codigoActividadParticular}">
												<thead>
													<tr>
														<th><spring:message code="label.detalleCaso.tarea" /><span class="text-danger"> *</span></th>
														<th><spring:message code="label.detalleCaso.detalle" /><span class="text-danger"> *</span></th>
														<th><spring:message code="label.detalleCaso.responsable" /><span class="text-danger"> *</span></th>
														<th><spring:message code="label.detalleCaso.estado" /><span class="text-danger"> *</span></th>
														<th class="hide"><spring:message code="label.detalleCaso.numeroDias" /></th>
														<th class="hide"><spring:message code="label.detalleCaso.numeroNotificaciones" /></th>
														<th><spring:message code="label.detalleCaso.fechaVencimiento" /><span class="text-danger"> *</span></th>
														<th><spring:message code="label.detalleCaso.accion" /></th>
													</tr>
												</thead>
												<tbody>
													<c:set var="filaTareaActividad" value="0" scope="page" />
													<c:forEach items="${actividad.tareaParticularCasoSet}" var="tarea">
														<c:if test="${tarea.snActiva == 'S'}">
															<tr id="${tarea.codigoTarea}Tarea">
																<td>
																	<input hidden="hidden" name="txtCodigoTareaCaso" value="${tarea.codigoTarea}" /> 
	<%-- 																<input hidden="hidden"  id="txtEsTareaPropia" name="txtEsTareaPropia"	value="${tarea.esTareaPropia}"/> --%>
																	<input hidden="hidden"  id="txtTareaPropia" name="txtTareaPropia"	value="${tarea.esTareaPropia}"/>
																	<input hidden="hidden" value="${tarea.tarea}"
																		id="nombreTarea${filaTareaActividad}" name="nombreTarea${filaTareaActividad}" disabled="true"/>
																	<label name="datosActivdadesLabel">${tarea.tarea}</label>
																</td>
																<td>
																	<input hidden="hidden" value="${tarea.detalle}" id="detalleTarea${filaTareaActividad}"
																		name="detalleTarea${filaTareaActividad}" disabled="true" />
																	<label name="datosActivdadesLabel">${tarea.detalle}</label>
																</td>
																<td>
																	<select id="responsable${filaTareaActividad}" multiple name="responsable${filaTareaActividad}" 
																		disabled="true">
																		<c:forEach items="${tarea.responsablesTareas}" var="responsable" varStatus="loop">
																			<c:choose>
																				<c:when test="${tarea.finalizada == 'S'}">
																					<option selected="selected" value="${responsable.casosEquiposCaso.equipoCaso.codigoEquipoCaso}">${responsable.casosEquiposCaso.equipoCaso.nombre}
																					</option>
																					<label name="datosActivdadesLabel">${responsable.casosEquiposCaso.equipoCaso.nombre}${responsable.casosEquiposCaso.equipoCaso.apellido}<c:if test="${!loop.last}">,</c:if> </label>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${responsable.casosEquiposCaso.activo == 'S'}">
																						<option selected="selected" value="${responsable.casosEquiposCaso.equipoCaso.codigoEquipoCaso}">${responsable.casosEquiposCaso.equipoCaso.nombre}
																						</option>
																					</c:if>
																				</c:otherwise>
																			</c:choose>																		
																		</c:forEach>
																	</select>
																	<c:forEach items="${tarea.responsablesTareas}" var="responsable" varStatus="loop">
																		<c:choose>
																			<c:when test="${tarea.finalizada == 'S'}">
																				<label name="datosActivdadesLabel">${responsable.casosEquiposCaso.equipoCaso.nombre}${responsable.casosEquiposCaso.equipoCaso.apellido}<c:if test="${!loop.last}">,</c:if> </label>
																			</c:when>
																			<c:otherwise>
																				<c:if test="${responsable.casosEquiposCaso.activo == 'S'}">
																					<label name="datosActivdadesLabel">${responsable.casosEquiposCaso.equipoCaso.nombre}${responsable.casosEquiposCaso.equipoCaso.apellido}<c:if test="${!loop.last}">,</c:if> </label>
																				</c:if>
																			</c:otherwise>
																		</c:choose>																		
																	</c:forEach>
																</td>
																<td>
																	<select hidden="hidden"  id="cmbEstadoTarea" name="cmbEstadoTareas" disabled="true">
																		<c:if test="${tarea.finalizada =='N'}">
																			<option value="${tarea.finalizada}"><spring:message code="label.detalleCaso.pendiente" /></option>
																		</c:if>
																		<c:if test="${tarea.finalizada =='S'}">
																			<option value="${tarea.finalizada}"><spring:message code="label.detalleCaso.completada" /></option>
																		</c:if>
																		<c:if test="${tarea.finalizada =='V'}">
																			<option value="${tarea.finalizada}"><spring:message code="label.detalleCaso.vencida" /></option>
																		</c:if>
																	</select>
																	<c:if test="${tarea.finalizada =='N'}">
																			<label name="datosActivdadesLabel"><spring:message code="label.detalleCaso.pendiente" /></label>
																	</c:if>
																	<c:if test="${tarea.finalizada =='S'}">
																		<label name="datosActivdadesLabel"><spring:message code="label.detalleCaso.completada" /></label>
																	</c:if>
																	<c:if test="${tarea.finalizada =='V'}">
																		<label name="datosActivdadesLabel"><spring:message code="label.detalleCaso.vencida" /></label>
																	</c:if>																
																</td>
																<td class="hide">
																	<input hidden="hidden" type="text" id="numeroDeDias" name="numeroDeDias"
																		onkeydown='return soloNumeros(event)' maxlength="3" value="${tarea.numeroDiasAntesAlertas}"
																		disabled="true">
																		<label name="datosActivdadesLabel">${tarea.numeroDiasAntesAlertas}</label>
																</td>
																<td class="hide">
																	<input hidden="hidden" type="text" id="numeroDeAlertas" name="numeroDeAlertas"
																		onkeydown='return soloNumeros(event)' maxlength="3" value="${tarea.numeroAlertasPorDia}"
																		disabled="true">
																		<label name="datosActivdadesLabel">${tarea.numeroAlertasPorDia}</label>
																</td>
																<td>
																	<div class="alert alert-danger" id="messageErrorAactividad" name="messageError" style="display: none">""</div>
																	<input hidden="hidden" type="date" id="vencimiento${filaTareaActividad}" name="vencimiento${filaTareaActividad}"
																		value="<fmt:formatDate pattern="yyyy-MM-dd" value="${tarea.fechaLimite}"/>"
																		disabled="true" onclick="guardarFechaLimiteActual(this,false)"
																		onchange="modificarFechalimite(this,false)">
																	<label name="datosActivdadesLabel"><fmt:formatDate pattern="dd-MM-yyyy" value="${tarea.fechaLimite}"/></label>																	
																</td>
																<td>
																	<a href="javascript:void(0);" class="btn btn-danger btn-circle .btn-xs" title="Eliminar Tarea"
																		onclick="irConfirmacionTarea(${tarea.codigoTarea});"><i class="glyphicon glyphicon-trash"></i> </a>
																	<a href="javascript:void(0);" class="btn btn-circle btn-success" title="Asociar Archivo"
																		onclick="mostrarModalAsociarArchivo(${tarea.codigoTarea})"><i
																		class="glyphicon glyphicon-paperclip"></i> </a>
																</td>
															</tr>
															<c:set var="filaTareaActividad" value="${filaTareaActividad + 1}" scope="page" />
														</c:if>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<c:set var="filaActividad" value="${filaActividad + 1}" scope="page" />
					</c:if>
				</c:forEach>
					</div>
				</div>
				<div class="panel panel panel-warning">
					<div class="panel-heading">Actividades Pendientes</div>
					<div class="panel-body" id="sortedPanels">
						<c:forEach items="${pendientes}" var="actividad">
						<c:if test="${actividad.snActiva ==  'S'}">
						<div class="col-sm-12 show" name="actividadParticular" order="${actividad.orden}" code="${actividad.codigoActividadParticular}">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="row">
										<div class="col-sm-9">
											<h4 class="panel-title">
												<input hidden="hidden" name="txtCodigoActividadCaso" value="${actividad.codigoActividadParticular}" />
												<a data-toggle="collapse" data-parent="#accordion"
													href="#collapseActividad${actividad.codigoActividadParticular}"> <%-- <input type="text"  id="nombreActividad" name="nombreActividad" value="${actividad.nombreActividad}" disabled="true"  class="form-control" > --%>
													<label id="nombreActividad" name="nombreActividad">${actividad.nombreActividad}</label>
												</a>
											</h4>
										</div>
										<div class="col-sm-3 text-right">
											<c:if test="${actividad.finalizada !='V'}">
												<a href="javascript:void(0);" onclick="habilitarCamposActividad(${filaActividad},this)"
													class="btn btn-info btn-circle .btn-xs" title="Editar Actividad" name="btnEditarActividad"
													alt="borrar actividad"> <i class="glyphicon glyphicon-pencil" id="btnActividad" name="btnActividad"></i>
												</a>
											</c:if>
	<%-- 											<a href="javascript:void(0);" onclick="cancelarCamposActividad(${filaActividad},this)" --%>
	<!-- 												class="btn btn-danger btn-circle .btn-xs hide" title="Cancelar Edicion de actividades" id="btnCancelar" name="btnCancelar"> -->
	<!-- 												<i class="	glyphicon glyphicon-remove"></i> -->
	<!-- 											</a> -->
												<a href="javascript:void(0);" onclick="irConfirmacionActividad(${actividad.codigoActividadParticular})"
													class="btn btn-danger btn-circle .btn-xs" title="Eliminar Actividad" name="btnActividad"
													alt="borrar actividad"> <i class="glyphicon glyphicon-trash" id="btnActividad" name="btnActividad"></i>
												</a>
											<c:if test="${actividad.finalizada !='V'}">
												<a href="javascript:void(0);" onclick="adicionarTareaDetalle(this, ${filaActividad})"
													class="btn btn-success btn-circle .btn-xs" title="Adicionar Tarea" name="btTareas" alt="Adicionar tarea">
													<i class="glyphicon glyphicon-plus" id="btnTareas" name="btnTareas"></i>
												</a>
												<a href="javascript:void(0);" onclick="modificarAlertas(this)"
													class="btn btn-primary btn-circle .btn-xs hide" title="Modificar Alertas" id="btnAlertas" name="btnAlertas">
													<i class="glyphicon glyphicon-cog"></i>
												</a>											
											</c:if>
										</div>
									</div>
								</div>
	
								<div id="collapseActividad${actividad.codigoActividadParticular}" class="panel-collapse collapse">
									<div class="panel-body" id="panelActividad${actividad.codigoActividadParticular}">
										<div class="row">
											<div class="col-sm-3" style="margin-bottom: 1%;">
												<label>
													<spring:message code="label.campo.numeroDeDias" />
												</label>
												<input type="text" class="form-control" id="txtNumerosdiasAntesActividad"
													onkeydown="return soloNumeros(event)" name="txtNumerosdiasAntesActividad" maxlength="3"
													value="${actividad.numeroDiasAntesAlertas}" disabled="true">
											</div>
											<div class="col-sm-3" style="margin-bottom: 1%;">
												<label>
													<spring:message code="label.campo.numeroDeNotificaciones" />
												</label>
												<input type="text" class="form-control" id="txtNumeroNotificacionesActividad"
													onkeydown="return soloNumeros(event)" name="txtNumeroNotificacionesActividad" maxlength="3"
													value="${actividad.numeroAlertasPorDia}" disabled="true">
												<input type="hidden" class="form-control" id="txtEsActividadPropia" name="txtEsActividadPropia"
													value="${actividad.esActividadPropia}">
											</div>
											<div class="col-sm-3" style="margin-bottom: 1%;">
												<div class="alert alert-danger" id="errorFechaMenorQueTareas" name="errorFechaMenorQueTareas" style="display: none">""</div>
												<label>
													<spring:message code="label.campo.fechaVencimientoActividad" />
												</label>
												<span class="text-danger"> *</span>
												<input type="date" id="actividadVencimiento${filaActividad}" name="actividadVencimiento${filaActividad}"
													value="${actividad.fechaLimite}" disabled="true" class="form-control"
													onclick="guardarFechaLimiteActual(this,true)" onblur="modificarFechalimite(this,true)" onkeydown="return true">
											</div>
											<div class="col-sm-3" style="margin-bottom: 1%;">
												<label>
													<spring:message code="label.campo.estadoActividad" />
												</label>
												<span class="text-danger"> *</span> <select class="form-control" id="cmbEstadoActividad"
													onchange="validarEstadoTareas(this)" onfocus="obtenerEstadoActual(this)" name="cmbEstadoActividad"
													disabled="true">
													<c:if test="${actividad.finalizada =='N'}">
														<option value="${actividad.finalizada}">Pendiente</option>
													</c:if>
													<c:if test="${actividad.finalizada =='S'}">
														<option value="${actividad.finalizada}">Completada</option>
													</c:if>
													<c:if test="${actividad.finalizada =='V'}">
														<option value="${actividad.finalizada}">Vencida</option>
													</c:if>
												</select>
											</div>
										</div>
										<div class="row">
											<div class="alert alert-danger hide" name="actividadError"></div>
										</div>
										<div class="row">
											<table class="table table-bordered table-actividades" id="tabla${actividad.codigoActividadParticular}">
												<thead>
													<tr>
														<th><spring:message code="label.detalleCaso.tarea" /><span class="text-danger"> *</span></th>
														<th><spring:message code="label.detalleCaso.detalle" /><span class="text-danger"> *</span></th>
														<th><spring:message code="label.detalleCaso.responsable" /><span class="text-danger"> *</span></th>
														<th><spring:message code="label.detalleCaso.estado" /><span class="text-danger"> *</span></th>
														<th class="hide"><spring:message code="label.detalleCaso.numeroDias" /></th>
														<th class="hide"><spring:message code="label.detalleCaso.numeroNotificaciones" /></th>
														<th><spring:message code="label.detalleCaso.fechaVencimiento" /><span class="text-danger"> *</span></th>
														<th><spring:message code="label.detalleCaso.accion" /></th>
													</tr>
												</thead>
												<tbody>
													<c:set var="filaTareaActividad" value="0" scope="page" />
													<c:forEach items="${actividad.tareaParticularCasoSet}" var="tarea">
														<c:if test="${tarea.snActiva == 'S'}">
															<tr id="${tarea.codigoTarea}Tarea">
																<td>
																	<input hidden="hidden" name="txtCodigoTareaCaso" value="${tarea.codigoTarea}" /> 
	<%-- 																<input hidden="hidden"  id="txtEsTareaPropia" name="txtEsTareaPropia"	value="${tarea.esTareaPropia}"/> --%>
																	<input hidden="hidden"  id="txtTareaPropia" name="txtTareaPropia"	value="${tarea.esTareaPropia}"/>
																	<input hidden="hidden" value="${tarea.tarea}"
																		id="nombreTarea${filaTareaActividad}" name="nombreTarea${filaTareaActividad}" disabled="true"/>
																	<label name="datosActivdadesLabel">${tarea.tarea}</label>
																</td>
																<td>
																	<input hidden="hidden" value="${tarea.detalle}" id="detalleTarea${filaTareaActividad}"
																		name="detalleTarea${filaTareaActividad}" disabled="true" />
																	<label name="datosActivdadesLabel">${tarea.detalle}</label>
																</td>
																<td>
																	<select id="responsable${filaTareaActividad}" multiple name="responsable${filaTareaActividad}" 
																		disabled="true">
																		<c:forEach items="${tarea.responsablesTareas}" var="responsable" varStatus="loop">
																			<c:choose>
																				<c:when test="${tarea.finalizada == 'S'}">
																					<option selected="selected" value="${responsable.casosEquiposCaso.equipoCaso.codigoEquipoCaso}">${responsable.casosEquiposCaso.equipoCaso.nombre}
																					</option>
																					<label name="datosActivdadesLabel">${responsable.casosEquiposCaso.equipoCaso.nombre}${responsable.casosEquiposCaso.equipoCaso.apellido}<c:if test="${!loop.last}">,</c:if> </label>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${responsable.casosEquiposCaso.activo == 'S'}">
																						<option selected="selected" value="${responsable.casosEquiposCaso.equipoCaso.codigoEquipoCaso}">${responsable.casosEquiposCaso.equipoCaso.nombre}
																						</option>
																					</c:if>
																				</c:otherwise>
																			</c:choose>																		
																		</c:forEach>
																	</select>
																	<c:forEach items="${tarea.responsablesTareas}" var="responsable" varStatus="loop">
																		<c:choose>
																			<c:when test="${tarea.finalizada == 'S'}">
																				<label name="datosActivdadesLabel">${responsable.casosEquiposCaso.equipoCaso.nombre}${responsable.casosEquiposCaso.equipoCaso.apellido}<c:if test="${!loop.last}">,</c:if> </label>
																			</c:when>
																			<c:otherwise>
																				<c:if test="${responsable.casosEquiposCaso.activo == 'S'}">
																					<label name="datosActivdadesLabel">${responsable.casosEquiposCaso.equipoCaso.nombre}${responsable.casosEquiposCaso.equipoCaso.apellido}<c:if test="${!loop.last}">,</c:if> </label>
																				</c:if>
																			</c:otherwise>
																		</c:choose>																		
																	</c:forEach>
																</td>
																<td>
																	<select hidden="hidden"  id="cmbEstadoTarea" name="cmbEstadoTareas" disabled="true">
																		<c:if test="${tarea.finalizada =='N'}">
																			<option value="${tarea.finalizada}"><spring:message code="label.detalleCaso.pendiente" /></option>
																		</c:if>
																		<c:if test="${tarea.finalizada =='S'}">
																			<option value="${tarea.finalizada}"><spring:message code="label.detalleCaso.completada" /></option>
																		</c:if>
																		<c:if test="${tarea.finalizada =='V'}">
																			<option value="${tarea.finalizada}"><spring:message code="label.detalleCaso.vencida" /></option>
																		</c:if>
																	</select>
																	<c:if test="${tarea.finalizada =='N'}">
																			<label name="datosActivdadesLabel"><spring:message code="label.detalleCaso.pendiente" /></label>
																	</c:if>
																	<c:if test="${tarea.finalizada =='S'}">
																		<label name="datosActivdadesLabel"><spring:message code="label.detalleCaso.completada" /></label>
																	</c:if>
																	<c:if test="${tarea.finalizada =='V'}">
																		<label name="datosActivdadesLabel"><spring:message code="label.detalleCaso.vencida" /></label>
																	</c:if>																
																</td>
																<td class="hide">
																	<input hidden="hidden" type="text" id="numeroDeDias" name="numeroDeDias"
																		onkeydown='return soloNumeros(event)' maxlength="3" value="${tarea.numeroDiasAntesAlertas}"
																		disabled="true">
																		<label name="datosActivdadesLabel">${tarea.numeroDiasAntesAlertas}</label>
																</td>
																<td class="hide">
																	<input hidden="hidden" type="text" id="numeroDeAlertas" name="numeroDeAlertas"
																		onkeydown='return soloNumeros(event)' maxlength="3" value="${tarea.numeroAlertasPorDia}"
																		disabled="true">
																		<label name="datosActivdadesLabel">${tarea.numeroAlertasPorDia}</label>
																</td>
																<td>
																	<div class="alert alert-danger" id="messageErrorAactividad" name="messageError" style="display: none">""</div>
																	<input hidden="hidden" type="date" id="vencimiento${filaTareaActividad}" name="vencimiento${filaTareaActividad}"
																		value="<fmt:formatDate pattern="yyyy-MM-dd" value="${tarea.fechaLimite}"/>"
																		disabled="true" onclick="guardarFechaLimiteActual(this,false)"
																		onchange="modificarFechalimite(this,false)">
																	<label name="datosActivdadesLabel"><fmt:formatDate pattern="dd-MM-yyyy" value="${tarea.fechaLimite}"/></label>																	
																</td>
																<td>
																	<a href="javascript:void(0);" class="btn btn-danger btn-circle .btn-xs" title="Eliminar Tarea"
																		onclick="irConfirmacionTarea(${tarea.codigoTarea});"><i class="glyphicon glyphicon-trash"></i> </a>
																	<a href="javascript:void(0);" class="btn btn-circle btn-success" title="Asociar Archivo"
																		onclick="mostrarModalAsociarArchivo(${tarea.codigoTarea})"><i
																		class="glyphicon glyphicon-paperclip"></i> </a>
																</td>
															</tr>
															<c:set var="filaTareaActividad" value="${filaTareaActividad + 1}" scope="page" />
														</c:if>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<c:set var="filaActividad" value="${filaActividad + 1}" scope="page" />
					</c:if>
				</c:forEach>
					</div>
				</div>
				<div class="panel panel panel-danger">
					<div class="panel-heading">Actividades Vencidas</div>
					<div class="panel-body">
						<c:forEach items="${vencidas}" var="actividad">
						<c:if test="${actividad.snActiva ==  'S'}">
						<div class="col-sm-12 show" name="actividadParticular">
							<div class="panel panel-default">
								<div class="panel-heading">
									<div class="row">
										<div class="col-sm-9">
											<h4 class="panel-title">
												<input hidden="hidden" name="txtCodigoActividadCaso" value="${actividad.codigoActividadParticular}" />
												<a data-toggle="collapse" data-parent="#accordion"
													href="#collapseActividad${actividad.codigoActividadParticular}"> <%-- <input type="text"  id="nombreActividad" name="nombreActividad" value="${actividad.nombreActividad}" disabled="true"  class="form-control" > --%>
													<label id="nombreActividad" name="nombreActividad">${actividad.nombreActividad}</label>
												</a>
											</h4>
										</div>
										<div class="col-sm-3 text-right">
											<c:if test="${actividad.finalizada !='V'}">
												<a href="javascript:void(0);" onclick="habilitarCamposActividad(${filaActividad},this)"
													class="btn btn-info btn-circle .btn-xs" title="Editar Actividad" name="btnEditarActividad"
													alt="borrar actividad"> <i class="glyphicon glyphicon-pencil" id="btnActividad" name="btnActividad"></i>
												</a>
											</c:if>
	<%-- 											<a href="javascript:void(0);" onclick="cancelarCamposActividad(${filaActividad},this)" --%>
	<!-- 												class="btn btn-danger btn-circle .btn-xs hide" title="Cancelar Edicion de actividades" id="btnCancelar" name="btnCancelar"> -->
	<!-- 												<i class="	glyphicon glyphicon-remove"></i> -->
	<!-- 											</a> -->
												<a href="javascript:void(0);" onclick="irConfirmacionActividad(${actividad.codigoActividadParticular})"
													class="btn btn-danger btn-circle .btn-xs" title="Eliminar Actividad" name="btnActividad"
													alt="borrar actividad"> <i class="glyphicon glyphicon-trash" id="btnActividad" name="btnActividad"></i>
												</a>
											<c:if test="${actividad.finalizada !='V'}">
												<a href="javascript:void(0);" onclick="adicionarTareaDetalle(this, ${filaActividad})"
													class="btn btn-success btn-circle .btn-xs" title="Adicionar Tarea" name="btTareas" alt="Adicionar tarea">
													<i class="glyphicon glyphicon-plus" id="btnTareas" name="btnTareas"></i>
												</a>
												<a href="javascript:void(0);" onclick="modificarAlertas(this)"
													class="btn btn-primary btn-circle .btn-xs hide" title="Modificar Alertas" id="btnAlertas" name="btnAlertas">
													<i class="glyphicon glyphicon-cog"></i>
												</a>											
											</c:if>
										</div>
									</div>
								</div>
	
								<div id="collapseActividad${actividad.codigoActividadParticular}" class="panel-collapse collapse">
									<div class="panel-body" id="panelActividad${actividad.codigoActividadParticular}">
										<div class="row">
											<div class="col-sm-3" style="margin-bottom: 1%;">
												<label>
													<spring:message code="label.campo.numeroDeDias" />
												</label>
												<input type="text" class="form-control" id="txtNumerosdiasAntesActividad"
													onkeydown="return soloNumeros(event)" name="txtNumerosdiasAntesActividad" maxlength="3"
													value="${actividad.numeroDiasAntesAlertas}" disabled="true">
											</div>
											<div class="col-sm-3" style="margin-bottom: 1%;">
												<label>
													<spring:message code="label.campo.numeroDeNotificaciones" />
												</label>
												<input type="text" class="form-control" id="txtNumeroNotificacionesActividad"
													onkeydown="return soloNumeros(event)" name="txtNumeroNotificacionesActividad" maxlength="3"
													value="${actividad.numeroAlertasPorDia}" disabled="true">
												<input type="hidden" class="form-control" id="txtEsActividadPropia" name="txtEsActividadPropia"
													value="${actividad.esActividadPropia}">
											</div>
											<div class="col-sm-3" style="margin-bottom: 1%;">
												<div class="alert alert-danger" id="errorFechaMenorQueTareas" name="errorFechaMenorQueTareas" style="display: none">""</div>
												<label>
													<spring:message code="label.campo.fechaVencimientoActividad" />
												</label>
												<span class="text-danger"> *</span>
												<input type="date" id="actividadVencimiento${filaActividad}" name="actividadVencimiento${filaActividad}"
													value="${actividad.fechaLimite}" disabled="true" class="form-control"
													onclick="guardarFechaLimiteActual(this,true)" onblur="modificarFechalimite(this,true)" onkeydown="return true">
											</div>
											<div class="col-sm-3" style="margin-bottom: 1%;">
												<label>
													<spring:message code="label.campo.estadoActividad" />
												</label>
												<span class="text-danger"> *</span> <select class="form-control" id="cmbEstadoActividad"
													onchange="validarEstadoTareas(this)" onfocus="obtenerEstadoActual(this)" name="cmbEstadoActividad"
													disabled="true">
													<c:if test="${actividad.finalizada =='N'}">
														<option value="${actividad.finalizada}">Pendiente</option>
													</c:if>
													<c:if test="${actividad.finalizada =='S'}">
														<option value="${actividad.finalizada}">Completada</option>
													</c:if>
													<c:if test="${actividad.finalizada =='V'}">
														<option value="${actividad.finalizada}">Vencida</option>
													</c:if>
												</select>
											</div>
										</div>
										<div class="row">
											<div class="alert alert-danger hide" name="actividadError"></div>
										</div>
										<div class="row">
											<table class="table table-bordered table-actividades" id="tabla${actividad.codigoActividadParticular}">
												<thead>
													<tr>
														<th><spring:message code="label.detalleCaso.tarea" /><span class="text-danger"> *</span></th>
														<th><spring:message code="label.detalleCaso.detalle" /><span class="text-danger"> *</span></th>
														<th><spring:message code="label.detalleCaso.responsable" /><span class="text-danger"> *</span></th>
														<th><spring:message code="label.detalleCaso.estado" /><span class="text-danger"> *</span></th>
														<th class="hide"><spring:message code="label.detalleCaso.numeroDias" /></th>
														<th class="hide"><spring:message code="label.detalleCaso.numeroNotificaciones" /></th>
														<th><spring:message code="label.detalleCaso.fechaVencimiento" /><span class="text-danger"> *</span></th>
														<th><spring:message code="label.detalleCaso.accion" /></th>
													</tr>
												</thead>
												<tbody>
													<c:set var="filaTareaActividad" value="0" scope="page" />
													<c:forEach items="${actividad.tareaParticularCasoSet}" var="tarea">
														<c:if test="${tarea.snActiva == 'S'}">
															<tr id="${tarea.codigoTarea}Tarea">
																<td>
																	<input hidden="hidden" name="txtCodigoTareaCaso" value="${tarea.codigoTarea}" /> 
	<%-- 																<input hidden="hidden"  id="txtEsTareaPropia" name="txtEsTareaPropia"	value="${tarea.esTareaPropia}"/> --%>
																	<input hidden="hidden"  id="txtTareaPropia" name="txtTareaPropia"	value="${tarea.esTareaPropia}"/>
																	<input hidden="hidden" value="${tarea.tarea}"
																		id="nombreTarea${filaTareaActividad}" name="nombreTarea${filaTareaActividad}" disabled="true"/>
																	<label name="datosActivdadesLabel">${tarea.tarea}</label>
																</td>
																<td>
																	<input hidden="hidden" value="${tarea.detalle}" id="detalleTarea${filaTareaActividad}"
																		name="detalleTarea${filaTareaActividad}" disabled="true" />
																	<label name="datosActivdadesLabel">${tarea.detalle}</label>
																</td>
																<td>
																	<select id="responsable${filaTareaActividad}" multiple name="responsable${filaTareaActividad}" 
																		disabled="true">
																		<c:forEach items="${tarea.responsablesTareas}" var="responsable" varStatus="loop">
																			<c:choose>
																				<c:when test="${tarea.finalizada == 'S'}">
																					<option selected="selected" value="${responsable.casosEquiposCaso.equipoCaso.codigoEquipoCaso}">${responsable.casosEquiposCaso.equipoCaso.nombre}
																					</option>
																					<label name="datosActivdadesLabel">${responsable.casosEquiposCaso.equipoCaso.nombre}${responsable.casosEquiposCaso.equipoCaso.apellido}<c:if test="${!loop.last}">,</c:if> </label>
																				</c:when>
																				<c:otherwise>
																					<c:if test="${responsable.casosEquiposCaso.activo == 'S'}">
																						<option selected="selected" value="${responsable.casosEquiposCaso.equipoCaso.codigoEquipoCaso}">${responsable.casosEquiposCaso.equipoCaso.nombre}
																						</option>
																					</c:if>
																				</c:otherwise>
																			</c:choose>																		
																		</c:forEach>
																	</select>
																	<c:forEach items="${tarea.responsablesTareas}" var="responsable" varStatus="loop">
																		<c:choose>
																			<c:when test="${tarea.finalizada == 'S'}">
																				<label name="datosActivdadesLabel">${responsable.casosEquiposCaso.equipoCaso.nombre}${responsable.casosEquiposCaso.equipoCaso.apellido}<c:if test="${!loop.last}">,</c:if> </label>
																			</c:when>
																			<c:otherwise>
																				<c:if test="${responsable.casosEquiposCaso.activo == 'S'}">
																					<label name="datosActivdadesLabel">${responsable.casosEquiposCaso.equipoCaso.nombre}${responsable.casosEquiposCaso.equipoCaso.apellido}<c:if test="${!loop.last}">,</c:if> </label>
																				</c:if>
																			</c:otherwise>
																		</c:choose>																		
																	</c:forEach>
																</td>
																<td>
																	<select hidden="hidden"  id="cmbEstadoTarea" name="cmbEstadoTareas" disabled="true">
																		<c:if test="${tarea.finalizada =='N'}">
																			<option value="${tarea.finalizada}"><spring:message code="label.detalleCaso.pendiente" /></option>
																		</c:if>
																		<c:if test="${tarea.finalizada =='S'}">
																			<option value="${tarea.finalizada}"><spring:message code="label.detalleCaso.completada" /></option>
																		</c:if>
																		<c:if test="${tarea.finalizada =='V'}">
																			<option value="${tarea.finalizada}"><spring:message code="label.detalleCaso.vencida" /></option>
																		</c:if>
																	</select>
																	<c:if test="${tarea.finalizada =='N'}">
																			<label name="datosActivdadesLabel"><spring:message code="label.detalleCaso.pendiente" /></label>
																	</c:if>
																	<c:if test="${tarea.finalizada =='S'}">
																		<label name="datosActivdadesLabel"><spring:message code="label.detalleCaso.completada" /></label>
																	</c:if>
																	<c:if test="${tarea.finalizada =='V'}">
																		<label name="datosActivdadesLabel"><spring:message code="label.detalleCaso.vencida" /></label>
																	</c:if>																
																</td>
																<td class="hide">
																	<input hidden="hidden" type="text" id="numeroDeDias" name="numeroDeDias"
																		onkeydown='return soloNumeros(event)' maxlength="3" value="${tarea.numeroDiasAntesAlertas}"
																		disabled="true">
																		<label name="datosActivdadesLabel">${tarea.numeroDiasAntesAlertas}</label>
																</td>
																<td class="hide">
																	<input hidden="hidden" type="text" id="numeroDeAlertas" name="numeroDeAlertas"
																		onkeydown='return soloNumeros(event)' maxlength="3" value="${tarea.numeroAlertasPorDia}"
																		disabled="true">
																		<label name="datosActivdadesLabel">${tarea.numeroAlertasPorDia}</label>
																</td>
																<td>
																	<div class="alert alert-danger" id="messageErrorAactividad" name="messageError" style="display: none">""</div>
																	<input hidden="hidden" type="date" id="vencimiento${filaTareaActividad}" name="vencimiento${filaTareaActividad}"
																		value="<fmt:formatDate pattern="yyyy-MM-dd" value="${tarea.fechaLimite}"/>"
																		disabled="true" onclick="guardarFechaLimiteActual(this,false)"
																		onchange="modificarFechalimite(this,false)">
																	<label name="datosActivdadesLabel"><fmt:formatDate pattern="dd-MM-yyyy" value="${tarea.fechaLimite}"/></label>																	
																</td>
																<td>
																	<a href="javascript:void(0);" class="btn btn-danger btn-circle .btn-xs" title="Eliminar Tarea"
																		onclick="irConfirmacionTarea(${tarea.codigoTarea});"><i class="glyphicon glyphicon-trash"></i> </a>
																	<a href="javascript:void(0);" class="btn btn-circle btn-success" title="Asociar Archivo"
																		onclick="mostrarModalAsociarArchivo(${tarea.codigoTarea})"><i
																		class="glyphicon glyphicon-paperclip"></i> </a>
																</td>
															</tr>
															<c:set var="filaTareaActividad" value="${filaTareaActividad + 1}" scope="page" />
														</c:if>
													</c:forEach>
												</tbody>
											</table>
										</div>
									</div>
								</div>
							</div>
						</div>
						<c:set var="filaActividad" value="${filaActividad + 1}" scope="page" />
					</c:if>
				</c:forEach>
					</div>
				</div>
				
			
			
			
			
			
			
			
			
		</div>
		<!-- boton guardar -->
		<div class="row">
			
				<div class="col-sm-6 col-sm-offset-6 text-right">
					<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
						<button type="button" class="btn btn-azul" onclick="mostrarNuevaActividad()">
							<spring:message code="label.boton.agregarActividad" />
						</button>
					</c:if>
					<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
						<button type="button" class="btn btn-azul" onclick="modificarActividades()">
							<spring:message code="label.boton.guardar" />
						</button>
					</c:if>
					<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
						<button type="button" class="btn btn-azul" onclick="cancelarCamposActividad()">
							<spring:message code="label.button.confirmacionTarea.cancelar" />
						</button>
					</c:if>
				</div>
		</div>
		<!-- boton guardar -->
	</div>
</div>
</c:if>