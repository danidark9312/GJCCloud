<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div id="modal-buscarRol" name="modal-buscarRol" class="modal fade" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content" >
				<div class="modal-body" style="background-color: white">
					<form id="detalleDelRol" name="detalleDelRol">
						<div class="row">
							<div class="col-sm-6 " style="text-align: center;">
								<h2 class="modal-title">
									<spring:message code="label.maestroRoles.tituloRol" />
								</h2>
							</div>
							<div class="col-sm-6"></div>
						</div>
						<div class="row">
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.maestroRoles.nombre" />
								</label>
							</div>
							<div class="col-sm-8 b-r">
								<input id="buscarRol" name="buscarRol" type="text" onclick="buscarRol();"
									onchange="buscarRol();" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-actualizarRol" name="modal-actualizarRol" class="modal fade" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content" >
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-6 ">
							<h2 class="modal-title">
								<spring:message code="label.maestroRoles.titulo" />
							</h2>
						</div>
						<div class="col-sm-6"></div>
					</div>
				</div>
				<div class="modal-body modal-scroll">
					<div class="row">
						<div class="alert alert-success" id="messageExitosoModal">
							&nbsp;
							<a class="alert-link" href="#">&nbsp;</a>
							.
						</div>
						<div class="alert alert-danger" id="messageErrorModal">
							&nbsp;
							<a class="alert-link" href="#">&nbsp;</a>
							.
						</div>
					</div>
					<form id="frmRoles" name="frmRoles">
						<div class="row">
							<div class="col-sm-6 ">
								<label>
									<spring:message code="label.maestroRoles.nombre" />
								</label>
								<input id="nombreRol" name="nombreRol" class="form-control" type="text" maxlength="30"
									required="required" />
								<input id="codigoRol" name="codigoRol" type="hidden" />
							</div>
							<div class="col-sm-6" id="campoRolEstado" name="campoRolEstado">
								<label>
									<spring:message code="label.maestroRoles.estadoRol" />
								</label>
								<select class="form-control" id="estadoRol" name="estadoRol">
									<!-- tabindex="1"-->
									<c:forEach items="${estados}" var="estado">
										<option value="${estado.cdestado}">${estado.dsestado}</option>
									</c:forEach>
								</select>
							</div>
						</div>
					</form>
					<!-- 				frmRoles -> nombreRol, codigoRol, estadoRol -->
					<!-- 				nuevosRoles x asignarPermisos -> tabla tablaRoles, y tablaRoles x tablaPermisos -->
					<!-- 				adicionarRol()  ya no va   -->
					<div class="row">&nbsp;</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<spring:message code="label.maestroRoles.adicionarRol" />
						</div>
						<div class="panel-body">
							<form id="asignarPermisos" name="asignarPermisos">
								<!-- adicionarRol();
								<div class="col-sm-6" style="text-align: right;">
									<a href="javascript:adicionarRol();" class="btn btn-success" style="background-color: #449D44;">
										<i class="glyphicon glyphicon-plus"></i>&nbsp;<spring:message code="label.maestroRoles.adicionarRol" />									
									</a>
								</div>
								 -->
								<div class="col-sm-12">
									<div id="descripcionRolIngresado"></div>
								</div>
								<div class="col-sm-12">&nbsp;</div>
								<div class="col-sm-12">
									<table id="tablaPermisos" name="tablaPermisos" class="table table-striped">
									</table>
								</div>
							</form>
						</div>
					</div>
					<div class="row" style="text-align: right;">
						<div class="col-sm-12 ">&nbsp;</div>
						<div class="col-sm-10">
							<button type="button" class="btn btn-azul" data-dismiss="modal" style="width: 100px;">
								<spring:message code="label.maestroRoles.salir" />
							</button>
						</div>
						<div class="col-sm-2">
							<button type="button" class="btn btn-azul" onclick="guardar();" id="guardarRol"
								style="display: block" style="width:100px;">
								<spring:message code="label.boton.guardar" />
							</button>
							<!-- 						<button type="button" class="btn btn-azul" onclick="guardar()" -->
							<button type="button" class="btn btn-azul" onclick="actualizarRol();" id="actualizarRol"
								style="display: none" data-dismiss="modal" style="width:100px;">
								<spring:message code="label.boton.guardar" />
							</button>
						</div>
					</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
	<div id="modal-EliminarRol" name="modal-EliminarRol" class="modal fade" aria-hidden="true">
		<div class="modal-dialog modal-lg" style="overflow-y: auto !important">
			<div class="modal-content" ><!--  style="width: 620px;" -->
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1 "></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.maestroRoles.mensajeEliminarRol" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroRoles.cancelar" />
						</button>
						<button type="button" class="btn btn-white" data-dismiss="modal" onclick="eliminarRolSeguro();">
							<spring:message code="label.maestroRoles.eliminar" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<!-- NO ELIMINAR ROL-->
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
	<div id="modal-NoEliminarRol" name="modal-NoEliminarRol" class="modal fade" aria-hidden="true">
		<div class="modal-dialog modal-lg" style="overflow-y: auto !important">
			<div class="modal-content" ><!-- style="width: 620px;" -->
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1 "></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.maestroRoles.mensajeEliminarRolNoEliminar" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroRoles.salir" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<!-- NO MODIFICAR ROL -->
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-NoModificarRol" name="modal-NoModificarRol" class="modal fade" aria-hidden="true">
		<div class="modal-dialog modal-lg" style="overflow-y: auto !important">
			<div class="modal-content"><!--  style="width: 620px;" -->
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1 "></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.maestroRoles.mensajeEliminarRolNoModificar" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroRoles.salir" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>