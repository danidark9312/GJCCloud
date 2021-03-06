<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
<!-- 		<div class="row"> -->
<!-- 			<div class="col-sm-11 b-r"></div> -->
<!-- 			<div class="col-sm-1 b-r"> -->
<!-- 				<a href="javascript:void(0);" onclick="agregarFormularioRadicado()" title="Adicionar Radicado" -->
<!-- 					class="btn btn-success  btn-circle .btn-xs" style="background-color: #449D44; border: 0;"> -->
<!-- 					<i class="glyphicon glyphicon-plus"></i> -->
<!-- 				</a> -->
<!-- 			</div> -->
<!-- 		</div> -->
	</c:if>
	<div name="formularioRadicado" style="display: none">
		<div class="row" style="margin-top: 15px;">
			<div class="col-sm-4 b-r"></div>
			<div class="col-sm-4 b-r"></div>
		</div>
		<div class="row">
			<div class="col-sm-5 b-r">
				<label>
					<spring:message code="label.campo.numeroRadicado" />
				</label>
<!--El * que sale si es requerido 				<span class="text-danger"> *</span> -->
				<input type="text" class="form-control" id="txtNumeroRadicado" name="txtNumeroRadicado"
					maxlength="25">
			</div>
			<div class="col-sm-5 b-r">
				<label>
					<spring:message code="label.campo.instanciaRadicado" />
				</label>
<!--El * que sale si es requerido 				<span class="text-danger"> *</span> -->
				<select class="form-control" id="txtInstanciaRadicado" name="txtInstanciaRadicado">
				</select>
			</div>
			<!-- no borrar el siguiente codigo comentado, puede que se vuelva a usar en control de cambios-->
			<!-- 		<div class="col-sm-2"> -->
			<!-- 			<div class="checkbox i-checks"> -->
			<!-- 				<label> <input type="checkbox" id="RadicadoAcumulado" -->
			<!-- 					value="" onclick="mostrarCamposRadicadoAcumlado(this)" name="checkBoxRadicado"> <i></i> -->
			<!-- 					<spring:message code="label.campo.esAcumulado" /></label> -->
			<!-- 			</div> -->
			<!-- 		</div> -->
			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
				<div class="form-group col-sm-2 col-md-2" style="padding-left: 10%">
					<a href="javascript:void(0);" onclick="agregarFormularioRadicado()" title="Eliminar Radicado"
						class="btn btn-success  btn-circle .btn-xs" style="background-color: #449D44; border: 0;">
						<i class="glyphicon glyphicon-plus"></i>
					</a>
				</div>
			</c:if>
		</div>
		<div class="row" style="margin-top: 25px">
			<div class="col-sm-12 b-r">
				<div class="col-sm-12" style="text-align: center;">
					<a href="javascript:void(0)" class="btn btn-success" onclick="radicadosAcumulados.add(this)"
						style="background-color: #449D44;"> <i
						class="glyphicon glyphicon-plus"></i>Asociar radicado acumulado
					</a>
				</div>
			</div>
		</div>
		<div class="row" style="margin-top: 25px">
		
 <table class="table table-striped" id="tableRadicadosAcumulados">
    <thead>
      <tr>
        <th><spring:message code="label.detalleCaso.tipoRadicado" /></th>
		<th><spring:message code="label.detalleCaso.radicadoAsociado" /></th>
		<th>Observacion</th>
		<th>Eliminar</th>
      </tr>
    </thead>
    <tbody>
      <tr>
						<td><div class="col-sm-12 b-r">
								 <select class="form-control cmbsCiudadProcesos"
									id="tipoRadicado" name="tipoRadicado"
									onchange="validateTipoRadicado(this)">
									<option value=2>Caso Grupo Jur�dico</option>
									<option value=3>Caso Externo</option>
								</select>
							</div></td>
						<td><div class="col-sm-12 b-r">
								 <input type="text" class="form-control"
									id="txtRadicadoAsociado" name="txtRadicadoAsociado"
									style="display: none" />
									 
									<input type="text"
									class="form-control" id="txtAutoCompleteRadicados"
									name="txtAutoCompleteRadicados" style="display: block"
									onchange="validateInputAutocomplete(this)" />
							</div></td>
						<td><div class="col-sm-12 b-r">
								 <input type="text" class="form-control"
									id="txtObservacionRadicado" name="txtObservacionRadicado"/> 
							</div></td>
						<td><a href="javascript:void(0);"
							onclick="radicadosAcumulados.remove($(this).closest('tr'))" title="Eliminar Radicado"
							class="btn btn-danger btn btn-circle .btn-xs"
							style="background-color: red; border: 0;"> <i
								class="glyphicon glyphicon-minus"></i>
						</a></td>
					</tr>
      
    </tbody>
  </table>
		
			

			
		</div>
		<div name="formularioAcumulado" id="formularioAcumulado" style="display: none;">
			<div class="row">
				<div class="col-sm-6 b-r">
					<label>
						<spring:message code="label.campo.acomuladoCon" />
					</label>
				</div>
				<div class="col-sm-6 b-r">
					<label>
						<spring:message code="label.campo.numeroDeRadicadoPadre" />
					</label>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-6 b-r">
					<select class="form-control" id="txtacomuladoCon" name="txtacomuladoCon">
						<option value=""><spring:message code="label.campo.select" /></option>
						<option value="GJA"><spring:message code="label.campo.gja" /></option>
						<option value="Externo"><spring:message code="label.campo.externo" /></option>
					</select>
				</div>
				<div class="col-sm-6 b-r">
					<input type="text" class="form-control" id="txtNumeroRadicadoPadre" name="txtNumeroRadicadoPadre"
						maxlength="25">
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 b-r">
					<label>
						<spring:message code="label.campo.acomuladoObservaciones" />
					</label>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 b-r">
					<textarea class="col-sm-12 b-r" id="txtAcomuladoObservaciones" name="txtAcomuladoObservaciones"
						rows="3" maxlength="255"></textarea>
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 b-r"></div>
			</div>
		</div>
		<br>
		<hr class="hr-line-solid">
	</div>
	<hr class="hr-line-solid" id="separadorFormularioRadicado"></hr>
	<div class="row">
		<div class="col-sm-11 b-r"></div>
		<div class="col-sm-1 b-r">
			<a href="javascript:void(0);" onclick="agregarFormularioRadicado()" title="Adicionar Radicado"
				class="btn btn-success  btn-circle .btn-xs" style="background-color: #449D44; border: 0;">
				<i class="glyphicon glyphicon-plus"></i>
			</a>
		</div>
	</div>
	
</c:if>