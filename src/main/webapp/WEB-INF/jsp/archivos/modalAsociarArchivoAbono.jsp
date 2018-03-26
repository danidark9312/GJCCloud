<div id="modalAsociarArchivoAbono" name="modalAsociarArchivoAbono" class="modal fade"
		aria-hidden="true">	
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-12">
							<h2 class="modal-title">
								<spring:message code="label.title.importar.archivos" />
							</h2>
						</div>
						<div class= "modal-body modal-scroll">	
							<div class="wrapper wrapper-content animated fadeIn">
								<div class="row">
									<div class="col-lg-12">
										<div class="ibox float-e-margins">
											<div class="ibox-title">
												<h5>
													<spring:message code="label.archivo.asociar" />
												</h5>
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
												<form id="dropzone_asociarArchivoAbono" class="dropzone" method="post"
													enctype="multipart/form-data">
													<div class="dropzone-previews"></div>
													<input type="hidden" name="codigoAbono" id="dropzoneCodigoAbono">
												</form>
												<div id="listadoArchivosAbonos"></div>
												<div>
													<div class="m text-right"></div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="form-group col-md-12 btns-forms hidden-sm hidden-xs text-right">
									<label> </label>
									<button class="btn btn-azul" type="button" data-dismiss="modal">
										<spring:message code="label.maestroActividad.cancelar" />
									</button>
									<button class="btn btn-azul" type="button" onclick="exportarArchivoAbono();">
										<spring:message code="label.boton.guardar" />
									</button>
								</div>
								<div class="form-group col-xs-12 col-sm-12 btns-forms hidden-md hidden-lg">
									<label> </label>
									<button type="button" class="btn btn-azul col-xs-6 col-sm-6" data-dismiss="modal">
											<spring:message code="label.maestroActividad.cancelar" />
									</button>	
								</div>								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>