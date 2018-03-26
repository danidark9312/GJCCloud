<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div class="row">
		<div class="col-sm-12 b-r">
			<label>
				<spring:message code="label.campo.abogadosDelCaso" />
			</label>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-6">
			<select class="form-control" id="cmbMiembroAbogado" name="cmbMiembroAbogado" onchange=""></select>
		</div>
		<div class="col-sm-6">
			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
				<a href="javascript:void(0)" onclick="asignarAbogadosAlCaso()"
					class="btn btn-success btn-circle .btn-xs" style="background-color: #449D44; border: 0;"
					title="Adicionar Abogado">
					<i class="glyphicon glyphicon-plus"></i>
				</a>
			</c:if>
		</div>
	</div>
	<br>
	<hr class="hr-line-solid">
	<br>
	<div class="row" id="equipoAbogados"></div>
</c:if>