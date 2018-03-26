<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div class="alert alert-success alert-block hide" id="alertSucessotificacionDespues"></div>
	<div class="alert alert-danger alert-block hide" id="errorNotificacionDespues"></div>
	<div class="wrapper wrapper-content  animated fadeInRight">
	<div class="row">
	<div class="col-md-12 col-lg-12  col-sm-12 col-xs-12">
	<form role="form" id="diasAntesForm">
		<div class="row">
		<div class="col-md-4 col-lg-4  col-sm-4 col-xs-4"><label><spring:message code="lable.title.dias.antes" /></label></div>
		<div class="col-md-8 col-lg-8  col-sm-8 col-xs-8">
				<input type="number" id="diasAntes" name="diasAntes" class="form-control">
			</div>
		</div>
		<br>
		<div class="row">
		<div class="col-md-4 col-lg-4  col-sm-4 col-xs-4"><label><spring:message code="lable.title.estado.noficacion" /></label></div>
		<div class="col-md-8 col-lg-8  col-sm-8 col-xs-8">
			<select class="form-control" id="estadoNotificacionAntes" name="estadoNotificacionAntes" >
				<option value="ACTIVO">ACTIVO</option>
				<option value="INACTIVO">INACTIVO</option>
			</select>
		</div>
		</div>
		<br>
		<div class="row">
		<div class="col-md-4 col-lg-4  col-sm-4 col-xs-4"><label><spring:message code="lable.title.notificar.a" /></label></div>
		<div class="col-md-8 col-lg-8  col-sm-8 col-xs-8">
			<select class="form-control" id="notificarAAntes" name="notificarAAntes" multiple  data-placeholder="Seleccionar" >
			</select>
		</div>
		</div>
		<br>
		<div class="row">
		<div class="col-md-4 col-lg-4  col-sm-4 col-xs-4"><label><spring:message code="lable.title.numero.alerta.notificacion" /></label></div>
		<div class="col-md-8 col-lg-8  col-sm-8 col-xs-8">
						<input type="number" id="alertasAntes" name="alertasAntes" class="form-control">
		</div>
		</div>
	</form>
	</div>
	</div>
	
	</div>
</c:if>