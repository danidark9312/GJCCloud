<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-adicionarMiembroEquipo" name="modal-adicionarMiembroEquipo" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog modal-lg" id="nuevosMiembrosArea">
			<div class="modal-content" style="overflow-y: auto !important">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">
						<label>
							<spring:message code="label.adicionar.miembro" />
						</label>
					</h4>
				</div>
				<div class="modal-body modal-scroll" style="background-color: white" id="formularioEquipoCasoNuevoMiembroCaso"
					name="formularioEquipoCasoNuevoMiembroCaso">
					<div class="row">
						<div class="alert alert-danger hide" id="messageErrorAdicionarMiembro"></div>
					</div>
					<!-- Inicio bloque Abogados-->
					<form id="detalleDelCasoNuevoMiembro" name="detalleDelCasoNuevoMiembro">
						<div class="row">
							<div class="col-sm-12 b-r">
								<div class="panel panel-default">
									<div class="panel-heading">
										<h4 class="panel-title">
											<a data-toggle="collapse" data-parent="#accordion" href="#collapseAbogadosNuevo">
												<spring:message code="label.titulo.abogados" />
											</a>
										</h4>
									</div>
									<div id="collapseAbogadosNuevo" class="panel-collapse collapse">
										<div class="panel-body">
											<div class="row">
												<div class="alert alert-info" id="infoAbogadoNuevos" hidden="hidden"></div>
											</div>
											<div class="row">
												<div class="alert alert-danger" id="messageErrorAbogadoNuevos" hidden="hidden"></div>
											</div>
											<%@ include file="/WEB-INF/jsp/casos/abogadoAdicionarEquipo.jsp"%>
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
											<a data-toggle="collapse" data-parent="#accordion" href="#collapseDemandadosNuevos">
												<spring:message code="label.titulo.demandados" />
											</a>
										</h4>
									</div>
									<div id="collapseDemandadosNuevos" class="panel-collapse collapse">
										<div class="panel-body" id="panelDemandadoNuevos">
											<div class="row"></div>
											<%@ include file="/WEB-INF/jsp/casos/demandadoAdicionarEquipo.jsp"%>
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
												<a data-toggle="collapse" data-parent="#accordion" href="#collapseSolicitudNuevos">
													<spring:message code="label.titulo.equipoCaso" />
												</a>
											</h5>
										</div>
										<div id="collapseSolicitudNuevos" class="panel-collapse collapse">
											<div class="panel-body" id="panelEquipoCasoNuevos">
												<div class="row">
													<div class="alert alert-info" id="infoEquipoCasoNuevos" name="infoEquipoCasoNuevos"
														hidden="hidden"></div>
												</div>
												<div class="row">
													<div class="alert alert-danger" id="messageErrorEquipoCasoNuevos" hidden="hidden"></div>
												</div>
												<%@ include file="/WEB-INF/jsp/casos/equipoDelCasoAdicionarEquipo.jsp"%>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-azul" data-dismiss="modal"
								onclick="limpiarFormularioNuevosMiembros()" style="width: 15%">
								<label>
									<spring:message code="label.boton.cancelar" />
							</button>
							<button type="button" class="btn btn-azul" onclick="guardarNuevoMiembro()" style="width: 15%">
								<label>
									<spring:message code="label.boton.guardar" />
								</label>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>