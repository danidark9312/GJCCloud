<div id="tab-4" class="tab-pane">
	<div class="alert alert-success alert-block hide" id="alertSucessArchivoPrestamo">
		<div id=divMensajeSuccessArchivoPrestamo></div>		
	</div>
	<div class="alert alert-success alert-block hide" id="alertSucessMiembro">
		<div id="divMensajeSuccessMiembro"></div>
	</div>
	<div class="row">
		<div class="col-md-10">
			<h3 class="titulo-azul">
				<spring:message code="label.titulo.prestamo" />
			</h3>
		</div>
		<div class="col-md-2">
			<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
				<a data-toggle="modal" href="#modal-adicionarPrestamo" onclick="" title="Agregar Prestamo"
					class="btn btn-success  btn-circle .btn-xs" style="position: relative; top: 20px;"> <i
					class="glyphicon glyphicon-plus"></i>
				</a>
			</c:if>
		</div>
	</div>
	<div id="listaPrestamos"></div>
</div>