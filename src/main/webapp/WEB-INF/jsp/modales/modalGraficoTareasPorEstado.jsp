<div id="modal-tareasPorEstado" class="modal fade" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-body modal-scroll">
				<div class="alert alert-danger alert-block hide" id="errorCodigoCaso">
				</div>
				<div class="row">
					<div class="col-sm-6 b-r">
						<label>
							<spring:message code="label.campo.nombreCaso" />
						</label>
						<span class="text-danger"> *</span>
						<Select id="selectCaso" name="selectCaso" onchange="graficoEstadosTareas.cargarResponsablesPorCaso(this)"></Select>
					</div>
					<div class="col-sm-6">
						<label>
							<spring:message code="label.detalleCaso.responsable" />
						</label>
						<Select id="selectResponsable" name="selectResponsable"></Select>
					</div>
				</div>
				<div class="row">
<!-- 					<div class="col-sm-6"> -->
<!-- 						<div id="actividadesPorCasoEstadoChart" class="div-grafico"> -->
							
<!-- 						</div> -->
<!-- 					</div> -->
					<br>
					<div class="table-responsive">
						<div class="col-sm-12">
							<div id="tablaResponsablesCaso">
								
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-azul" data-dismiss="modal" onclick="recargar()">
					<spring:message code="label.button.confirmacionTarea.cancelar" />
				</button>
<!-- 				<button type="button" class="btn btn-azul" onclick="graficoEstadosTareas.mostrarGraficoPorCaso();graficoEstadosTareas.cargarTablaResponsables()"> -->
				<button type="button" class="btn btn-azul" onclick="graficoEstadosTareas.cargarTablaResponsables()">
					<spring:message code="label.title.button.buscar" />
				</button>
			</div>
		</div>
	</div>
</div>