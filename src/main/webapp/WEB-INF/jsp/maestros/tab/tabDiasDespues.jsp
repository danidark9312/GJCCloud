<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div class="alert alert-success alert-block hide" id="alertSucessActividad"></div>
	<div class="alert alert-danger alert-block hide" id="errorActividadMod"></div>
	<div class="wrapper wrapper-content  animated fadeInRight">
	<div class="row">
		<div class="col-md-12 col-lg-12  col-sm-12 col-xs-12">
		<form role="form" id="diasDespuesForm">
			<div class="row">
		<div class="col-md-4 col-lg-4  col-sm-4 col-xs-4"><label><spring:message code="lable.title.dias.despues" /></label></div>
		<div class="col-md-8 col-lg-8  col-sm-8 col-xs-8">
				<input type="number" id="diasDespues" name="diasDespues" class="form-control">
			</div>
		</div>
		<br>
		<div class="row">
		<div class="col-md-4 col-lg-4  col-sm-4 col-xs-4"><label><spring:message code="lable.title.estado.noficacion" /></label></div>
		<div class="col-md-8 col-lg-8  col-sm-8 col-xs-8">
			<select class="form-control" id="estadoNotificacionDespues" name="estadoNotificacionDespues" >
			<option value="ACTIVO">ACTIVO</option>
				<option value="INACTIVO">INACTIVO</option>
			</select>
		</div>
		</div>
		<br>
		<div class="row">
		<div class="col-md-4 col-lg-4  col-sm-4 col-xs-4"><label><spring:message code="lable.title.notificar.a" /></label></div>
		<div class="col-md-8 col-lg-8  col-sm-8 col-xs-8">
			<select class="form-control" id="notificarADespues" name="notificarADespues" multiple  data-placeholder="Seleccionar" >
			</select>
		</div>
		</div>
		<br>
		<div class="row">
		<div class="col-md-4 col-lg-4  col-sm-4 col-xs-4"><label><spring:message code="lable.title.numero.alerta.notificacion" /></label></div>
		<div class="col-md-8 col-lg-8  col-sm-8 col-xs-8">
						<input type="number" id="alertasDespues" name="alertasDespues" class="form-control">
		</div>
		</div>
	</form>
		</div>
	</div>
	
	</div>
</c:if>