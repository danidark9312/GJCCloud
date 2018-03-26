<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div id="actividadParticular" name="actividadParticular">
		<div class="row">
			<div class="col-sm-10 ">
				<label>
					<spring:message code="label.Actividad.particular.nombre" />
				</label>
				<div class="alert alert-danger" id="messageErrorAactividad" name="messageError"
					style="display: none"></div>
			</div>
			<div class="col-sm-2 b-r"></div>
		</div>
		<div class="row">
			<div class="col-sm-10 ">
				<input id="nombreActividad" name="nombreActividad" class="form-control type=" text" onclick=""
					onchange="" maxlength="30" />
			</div>
			<div class="col-sm-2">
				<a href="javascript:void(0);" onclick="actividadesParticularesCaso('nombreActividad')"
					title="Adicionar" class="btn btn-success btn-circle btn-xs">
					<i class="glyphicon glyphicon-plus"></i>
				</a>
			</div>
		</div>
		<br>
		<hr class="hr-line-solid"></hr>
		<br>
		<div class="row" id="divEspacio"></div>
		<div class="row" id="divEspacio1"></div>
		<!-- 	<div class="row" id="divEspacio"></div> -->
		<div class="modal-body" style="background-color: white">
		 <div onresize="auto">
			<form id="nuevasActividades" name="nuevasActividades">
				<div class="row" id="campoTarea" name="campoTarea" style="display: none">
					<div class="row">
						<div class="col-sm-7">
							<label>
								<spring:message code="label.maestroActividad.tarea" />
							</label>
						</div>
						<div class="col-sm-5">
							<label>
								<spring:message code="label.Actividad.particular.fechaDeVencimientoTarea" />
							</label>
							<span class="text-danger"> *</span>
							<div class="alert alert-danger" id="messageError" name="messageError"></div>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-7">
							<input id="nombreTarea" name="nombreTarea" type="text" class="form-control" maxlength="30" />
						</div>
						<div class="col-sm-4">
							<input type="date" class="form-control" id="txtFechaDeVencimientoTarea"
								name="txtFechaDeVencimientoTarea" onchange="validarfechasTareas(this)">
						</div>
					</div>
					<div class="row">
						<div class="col-sm-8">
							<label>
								<spring:message code="label.Actividad.particular.responsable" />
							</label>
							<Select class="form-control" id="cmbResponsableTarea" name="cmbResponsableTarea" tabindex="2"></Select>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-12">
							<label>
								<spring:message code="label.maestroActividad.detalleTarea" />
							</label>
							<span class="text-danger"> *</span>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-10">
							<input id="detalleTarea" name="detalleTarea" type="text" class="form-control" maxlength="255" />
						</div>
						<div class="col-sm-2">
							<a href="javascript:void(0);" onclick="clonarCampoTareaParticular(this)"
								class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;"
								name="btTareas" title="Adicionar Tarea">
								<i class="glyphicon glyphicon-plus" id="btnTareas" name="btnTareas"></i>
							</a>
						</div>
					</div>
				</div>
				<div class="row" id="divEspacio"></div>
			</form>
         </div>			
		</div>
		<br>
	</div>
</c:if>