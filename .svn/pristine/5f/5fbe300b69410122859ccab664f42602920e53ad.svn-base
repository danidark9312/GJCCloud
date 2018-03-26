<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div id="modal-CasoOffLine" name="modal-CasoOffLine" class="modal fade" aria-hidden="true">
		<div class="modal-dialog" style="height: auto; overflow-y: none !important;">
			<div class="modal-content">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-12">
							<h2 class="modal-title">
								<spring:message code="label.titulo.casoOff" />
							</h2>
						</div>
						<div class="wrapper wrapper-content animated fadeIn">
							<div class="row">
								<div class="col-lg-12">
									<div class="ibox float-e-margins">
										<div class="ibox-title">
											<h5>Casos OffLine</h5>
											<div class="ibox-tools">
												<a class="collapse-link">
													<i class="fa fa-chevron-up"></i>
												</a>
												<ul class="dropdown-menu dropdown-user">
													<li>
														<a href="#"></a>
													</li>
												</ul>
											</div>
										</div>
										<div class="ibox-content">
											<div class="row">
												<div class="alert alert-success" id="messageExitosoCasoOfLine" style="display: none"></div>
											</div>
											<div class="row">
												<div class="alert alert-danger" id="messageErrorCasoOfLine" style="display: none"></div>
											</div>
											<form id="my-awesome-dropzone" class="dropzone" method="post" enctype="multipart/form-data">
												<div class="dropzone-previews"></div>
											</form>
											<div>
												<div class="m text-right"></div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="row" style="text-align: right; padding-right: 7%;">
							<div class="col-sm-6 col-md-8"></div>
							<div class="col-sm-2 col-md-2">
								<button type="submit" class="btn btn-primary pull-right"
									onclick="exportarHojaExcelCasoOffLine()">
									<spring:message code="label.boton.guardar" />
								</button>
							</div>
							<div class="col-sm-2 col-md-2">
								<button type="button" class="btn btn-white" data-dismiss="modal" id="btnCancelarCarga"
									name="btnCancelarCarga" onclick="ocultarMensaje()">
									<spring:message code="label.maestroActividad.cancelar" />
								</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>