<div id="tab-1" class="tab-pane active">
	<div class="row pv-5">
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.campo.nombreCaso" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="nombreCaso" name="nombreCaso" value=""></label>
			</p>
		</div>
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.campo.tipoCaso" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="tipoCaso" name="tipoCaso" value=""></label>
			</p>
		</div>
	</div>
	<div class="row pv-5">
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.campo.fechaDeLosHechos" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="fechaHechos" name="fechaHechos" value=""></label>
			</p>
		</div>
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.campo.fechaFinDeLosHechos" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="fechaFinHechos" name="fechaFinHechos" value=""></label>
			</p>
		</div>
	</div>
	<div class="row pv-5">
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.campo.fechaDeCaducidad" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="fechaCaducidad" name="fechaCaducidad" value=""></label>
			</p>
		</div>
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.detalleCaso.estadoProceso" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="procesoEstado" name="procesoEstado"></label>
				<a data-toggle="modal" href="#modal-modificarEstadoCaso" onclick="deshabilitarEstado()" title="Cambiar de estado"
					style="float: right;"><i class="glyphicon glyphicon-pencil" style="color: green;"></i></a>
			</p>
		</div>
	</div>
	<div class="row pv-5">
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.detalleCaso.estadoProcesal" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="estadoProcesal" name="estadoProcesal" value=""></label>
			</p>
		</div>
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.detalleCaso.fechaActualizacion" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="fechaUltimaModificacion" name="fechaUltimaModificacion" value=""></label>
			</p>
		</div>
	</div>
	
	<div class="row pv-5">
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.detalleCaso.resumenHecho" />
			</p>
		</div>
		<div class="col-md-9 dato">
			<p>
				<label id="resumenHechos" name="resumenHechos" value=""></label>
			</p>
		</div>
	</div>
	<div class="row pv-5">
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.detalleCaso.fechaPrejudicial" />
			</p>
		</div>
		<div class="col-md-9 dato">
			<p>
				<label id="fechaPrejudicial" name="fechaPrejudicial" value=""></label>
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<h3 class="titulo-azul">
				<p>
					<spring:message code="label.detalleCaso.lugarHechos" />
				</p>
			</h3>
		</div>
	</div>
	<div class="row pv-5">
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.campo.paisDeLosHechos" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="hechosPais" name="hechosPais"></label>
			</p>
		</div>
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.campo.departamentoDeLosHechos" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="hechosDepartamento" name="hechosDepartamento" value=""></label>
			</p>
		</div>
	</div>
	<div class="row pv-5">
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.campo.municipioDeLosHechos" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="hechosMunicipio" name="hechosMunicipio"></label>
			</p>
		</div>
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.campo.direccionDeLosHechos" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="hechosDireccion" name="hechosDireccion"></label>
			</p>
		</div>
		<div class="col-md-3 dato"></div>
	</div>

	<!-- BLOQUE DEL DESPACHO -->
	<div class="row">
		<div class="col-md-12">
			<h3 class="titulo-azul">
				<spring:message code="label.titulo.despacho" />
			</h3>
		</div>
	</div>
	<div class="row pv-5">
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.detalleCaso.despacho" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="nombreDespacho" name="nombreDespacho" value=""></label>
			</p>
		</div>
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.detalleCaso.funcionario" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="funcionario" name="funcionario" value=""></label>
			</p>
		</div>
		<div class="col-md-3 dato"></div>
	</div>

	<!-- BLOQUE DE LUGAR DEL PROCESO -->
	<div class="row">
		<div class="col-md-12">
			<h3 class="titulo-azul">
				<spring:message code="label.titulo.lugarDespacho" />
			</h3>
		</div>
	</div>
	<div class="row pv-5">
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.detalleCaso.pais" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="ProcesoPais" name="procesoPais"></label>
			</p>
		</div>
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.detalleCaso.departamento" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="procesoDepartamento" name="procesoDepartamento" value=""></label>
			</p>
		</div>
	</div>
	<div class="row pv-5">
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.detalleCaso.municipio" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="ProcesoMunicipio" name="procesoMunicipio"></label>
			</p>
		</div>
		<div class="col-md-3 titulo-dato">
			<p>
				<spring:message code="label.detalleCaso.descripcion" />
			</p>
		</div>
		<div class="col-md-3 dato">
			<p>
				<label id="ProcesoDescripcion" name="ProcesoDescripcion"></label>
			</p>
		</div>
		<div class="col-md-3 dato"></div>
	</div>
	<div class="row pv-5">
		<div class="col-md-3 titulo-dato"></div>
		<div class="col-md-3 dato"></div>
	</div>
	<div class="row">
		<div class="col-md-3 titulo-dato "></div>
		<div class="col-md-9 dato"></div>
	</div>
	<div class="row">
		<div class="col-md-10">
			<h3 class="titulo-azul">
				<spring:message code="label.detalleCaso.bienesTitulo" />
			</h3>
		</div>
		<div class="col-md-2">
			<a data-toggle="modal" href="#modal-modificarBienAfectado" onclick="cargarTipoBienesAfectados()" title="Agregar Bien Afectado"
				class="btn btn-success  btn-circle .btn-xs" style="position: relative; top: 20px;"><i
				class="glyphicon glyphicon-plus"></i></a>
		</div>
	</div>
	<div class="alert alert-success alert-block hide" id="divMessageBienAfectado">
		<div id="successMessageBienAfectado"></div>
	</div>
	<div class="row">
		<div class="col-md-12">
			<div id="tablaListadoBienesAfectados"></div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-10">
			<h3 class="titulo-azul">
				<spring:message code="label.detalleCaso.tituloRadicado" />
			</h3>
		</div>
		<div class="col-md-2">
			<a data-toggle="modal" href="#modal-modificarRadicado" onclick="mostrarBotonAgregarRadicado()"
				title="Agregar Radicado" class="btn btn-success  btn-circle .btn-xs" style="position: relative; top: 20px;"><i
				class="glyphicon glyphicon-plus"></i></a>
		</div>
	</div>
	<div class="alert alert-success alert-block hide" id="divMessageRadicado">
		<div id="successMessageRadicado"></div>
	</div>
	<div class="alert alert-danger alert-block hide" id="divRadicadoError">
		<div id="messageRadicadoError"></div>
	</div>
	<div id="tablaRadicado"></div>
	<div class="row">
		<div class="col-md-12">
			<h3 class="titulo-azul">
				<spring:message code="label.titulo.comentario" />
			</h3>
		</div>
	</div>
	<div class="row">
		<div class="col-md-3 titulo-dato ">
			<p>
				<spring:message code="label.campo.comentarios" />
			</p>
		</div>
		<div class="col-md-9 dato">
			<p>
				<label id="comentario" name="comentario"></label>
			</p>
		</div>
	</div>
</div>



