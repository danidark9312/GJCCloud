<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snCrear}">
	<div id="modal-nuevoCaso" name="modal-nuevoCaso" class="modal fade" aria-hidden="true">
		<div class="modal-dialog modal-lg" id="modal">
			<div class="modal-content" id="" >
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-8 b-r">
							<h2 class="modal-title">
								<spring:message code="label.campo.nuevoCaso" />
							</h2>
						</div>
						<div class="col-sm-4">
							<label>
								<spring:message code="label.campo.estadoCaso" />
							</label>
							<span class="text-danger"> *</span>
							<Select class="form-control" id="txtEstadoCaso" name="txtEstadoCaso"></Select>
						</div>
					</div>
				</div>
				<div class="modal-body modal-scroll" style="background-color: white" id="modalPrueba">
					<div class="row">
						<div class="alert alert-danger" id="messageError"></div>
					</div>
					<div class="row">
						<div class="alert alert-danger" id="messageErrorNuevoCaso"></div>
					</div>
					<div class="row">
						<div class="alert alert-danger" id="messageErrorFechaFinDelosHechos" style="display: none"></div>
					</div>
					<!-- Inicio bloque Nuevo Caso-->
					<input hidden="hidden" id="aniosCaducidadParam" name="aniosCaducidadParam"
						value="${aniosCaducidad}" />
					<%@include file="/WEB-INF/jsp/casos/nuevoCaso/detalleDelCaso.jsp"%>
				</div>
			</div>
		</div>
	</div>
	<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
		<%@include file="/WEB-INF/jsp/casos/nuevoCaso/modalEliminarActividad.jsp"%>
		<%@include file="/WEB-INF/jsp/casos/nuevoCaso/modalEliminarTarea.jsp"%>
		
		<div id="modal-AdvertenciaDemandaMismaDiaAudiencia" name="modal" class="modal fade" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1 "></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.advertencia.demandamismodiaaudiencia" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroActividad.continuar" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		<div id="modal-AdvertenciaFechaActividadTarea" name="modal" class="modal fade" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1 "></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.title.advertencia.fechasactividadtarea.nohabil" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroActividad.continuar" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
		
	</c:if>
</c:if>