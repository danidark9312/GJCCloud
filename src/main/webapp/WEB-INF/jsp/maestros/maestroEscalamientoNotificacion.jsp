							<div class="row">
								<div class="col-md-12 col-lg-12">
									<div class="ibox float-e-margins">
										<div class="ibox-content">
											<div class="panel blank-panel">
												<div class="panel-heading">
													<div class="panel-options">
														<ul class="nav nav-tabs"> 
															<li class="active" id="liTab1">
																	<a data-toggle="tab" href="#tab-1" onclick="cargarConfiguracionInicialProximasAVencer()">
																		Pareas Próximas a Vencer
																	</a>
															</li>
															<li class="" id="liTab2">
																	<a data-toggle="tab" href="#tab-2" onclick="cargarConfiguracionInicialVencencida()">
																		Tareas Vencidas
																	</a>
															</li>
														</ul>
													</div>
												</div>
												<div class="panel-body">
													<div class="row">
													<div class="alert alert-success" id="messageExitosoEscalamiento">
														&nbsp;
														<a class="alert-link" href="#">&nbsp;</a>
													</div>
													<div class="alert alert-danger" id="messageErrorEscalamiento">
														&nbsp;
														<a class="alert-link" href="#">&nbsp;</a>
													</div>
												</div>
											<form role="form" class="panel-body">
												<input type="hidden" name="${_csrf.parameterName}" id="${_csrf.parameterName}"
													value="${_csrf.token}" />
												<input type='hidden' name='idusercreacion' id='idusercreacion'
													value='<sec:authentication property="principal.id"/>' />
												<input type='hidden' name='codigoTipoCasoEscalamiento' id='codigoTipoCasoEscalamiento' />
											</form>
												<div class="tab-content">
<!-- 												DIAS ANTES  -->
																<div id="tab-1" class="tab-pane active">
																	<%@include file="/WEB-INF/jsp/maestros/tab/tabDiasAntes.jsp"%>
																</div>
<!-- 																DIAS DESPUES  -->
																<div id="tab-2" class="tab-pane">
																	<%@include file="/WEB-INF/jsp/maestros/tab/tabDiasDespues.jsp"%>
																</div>
																</div>
												
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

		
		
		
	