<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snLectura}">
	<div id="modal-buscarUsuario" name="modal-buscarUsuario" class="modal fade" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 330px; height: 50px;">
				<div class="modal-body  modal-scroll" style="background-color: white">
					<form id="detalleDelUsuario" name="detalleDelUsuario">
						<div class="row">
							<div class="col-sm-6 " style="text-align: center;">
								<h2 class="modal-title">
									<spring:message code="label.maestroUsuario.tituloUsuario" />
								</h2>
							</div>
							<div class="col-sm-6"></div>
						</div>
						<div class="row">
							<div class="col-sm-4 b-r">
								<label>
									<spring:message code="label.maestroUsuario.nombre" />
								</label>
							</div>
							<div class="col-sm-8 b-r">
								<input id="buscarUsuario" name="buscarUsuario" type="text" onclick="buscarUsuario();"
									onchange="buscarUsuario();" />
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-actualizarUsuario" name="modal-actualizarUsuario" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 700px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-6 ">
							<h2 class="modal-title">
								<spring:message code="label.maestroUsuario.titulo" />
							</h2>
						</div>
						<div class="col-sm-6"></div>
					</div>
				</div>
				<div class="modal-body modal-scroll" style="background-color: white">
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
					<form id="frmUsuarios" name="frmUsuarios">					
						<div class="row">
							<div class="col-sm-4">
								<label>
									<spring:message code="label.maestroUsuario.nombre" />
								</label>
							</div>
							<div class="col-sm-8">
								<input id="nombreUsuario" name="nombreUsuario" class="form-control" type="text" maxlength="30"
									required="required" />
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>						
						<div class="row">
							<div class="col-sm-4">
								<label>
									<spring:message code="label.maestroUsuario.apellido" />
								</label>
							</div>
							<div class="col-sm-8">
								<input id="apellidoUsuario" name="apellidoUsuario" class="form-control" type="text" maxlength="30"
									required="required" />
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>
						<div class="row">
							<div class="col-sm-4">
								<label>
									<spring:message code="label.maestroUsuario.iniciales" />
								</label>
							</div>
							<div class="col-sm-8">
								<input id="abreviacionAbogado" name="abreviacionAbogado" class="form-control" type="text" maxlength="30"/>
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>
						<div class="row">
							<div class="col-sm-4">
								<label>
									<spring:message code="label.maestroUsuario.FechaNacimiento" />
								</label>
							</div>
							<div class="col-sm-8">
								<input id="nacimientoUsuario" name="nacimientoUsuario" class="form-control" type="date"/>
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>
						<div class="row">
							<div class="col-sm-4">
								<label>
									<spring:message code="label.maestroUsuario.cedula" />
								</label>
							</div>
							<div class="col-sm-8">
								<input id="cedulaUsuario" name="cedulaUsuario" class="form-control" type="text" maxlength="10"
									required="required" />
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>
						<div class="row">
							<div class="col-sm-4">
								<label>
									<spring:message code="label.maestroUsuario.Tipodocumento" />
								</label>
							</div>
							<div class="col-sm-8">
									<select id="tipoDocumento" name="tipoDocumento"
										data-placeholder="Seleccione el tipo de documento" class="form-control">
										<!-- tabindex="1"-->
										<c:forEach items="${tiposDocumentos}" var="tipoDocumento">
											<option value="${tipoDocumento.codigo}">${tipoDocumento.documento}</option>
										</c:forEach>
									</select>
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>
						<div class="row">
							<div class="col-sm-4">
								<label>
									<spring:message code="label.maestroUsuario.direccion" />
								</label>
							</div>
							<div class="col-sm-8">
								<input id="direccionUsuario" name="direccionUsuario" class="form-control" type="text" maxlength="50"
									required="required" />
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>
						<div class="row">
							<div class="col-sm-4">
								<label>
									<spring:message code="label.maestroUsuario.telefono" />
								</label>
							</div>
							<div class="col-sm-8">
								<input id="telefonoUsuario" name="telefonoUsuario" class="form-control" type="text" maxlength="15" required="required" data-mask="(999) 999-9999" placeholder="(___) ___-____"/>
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>
						<div class="row">
							<div class="col-sm-4">
								<label>
									<spring:message code="label.maestroUsuario.celular" />
								</label>
							</div>
							<div class="col-sm-8">
								<input id="celularUsuario" name="celularUsuario" class="form-control" type="text" maxlength="20" data-mask="(999) 999-9999" placeholder="(___) ___-____"/>
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>
						<div class="row">
							<div class="col-sm-4">
								<label>
									<spring:message code="label.maestroUsuario.tarjetaProfesional" />
								</label>
							</div>
							<div class="col-sm-8">
								<input id="tarjetaProfesionalUsuario" name="tarjetaProfesionalUsuario" class="form-control"
									type="text" maxlength="30" />
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>
<!-- 						<div class="row"> -->
<!-- 							<div class="col-sm-4"> -->
<!-- 								<label> -->
<!-- 									<spring:message code="label.campo.telefonoMiembro" /> -->
<!-- 								</label> -->
<!-- 							</div> -->
<!-- 							<div class="col-sm-8"> -->
<!-- 								<div class="input-group m-b"> -->
<!-- 									<span class="input-group-addon"><i class="fa fa-phone-square"></i></span> -->
<!-- 									<input type="text" class="form-control" id="txtTelefonoMiembro" -->
<!-- 										name="txtTelefonoMiembroDemandado" maxlength="15" data-mask="(999) 999-9999" -->
<!-- 										placeholder="(___) ___-____"> -->
<!-- 								</div> -->
<!-- 							</div> -->
<!-- 						</div> -->
						<div class="row">
							<div class="col-sm-4">
								<label>
									<spring:message code="label.maestroUsuario.correo" />
								</label>
							</div>
							<div class="col-sm-8">
								<input id="correoUsuario" name="correoUsuario" class="form-control" type="text" maxlength="40"
									required="required" />
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>
						<div class="row">
							<div class="col-sm-4">
								<label>
									<spring:message code="label.maestroUsuario.contrasena" />
								</label>
							</div>
							<div class="col-sm-8">
								<input id="contrasenaUsuario" name="contrasenaUsuario" class="form-control" type="text"
									maxlength="30" required="required" />
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>
						<div class="row">
							<div class="col-sm-4" id="campoUsuarioEstado" name="campoUsuarioEstado">
								<label>
									<spring:message code="label.maestroUsuario.estadoUsuario" />
								</label>
							</div>
							<div class="col-sm-8">
								<div class="form-group ">
									<select id="estadoUsuario" name="estadoUsuario" data-placeholder="Seleccione un estado"
										 class="form-control" onchange="validarSePuedeInactivar(this)">
										<!-- tabindex="1"-->
										<c:forEach items="${estados}" var="estado">
											<option value="${estado.cdestado}">${estado.dsestado}</option>
										</c:forEach>
									</select>
								</div>
								<label id="estadoUsuario-error" class="error" for="estadoUsuario" style="display:none"></label>
								
								
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4" id="campoUsuarioRol" name="campoUsuarioRol">
								<label>
									<spring:message code="label.maestroUsuario.rol" />
								</label>
							</div>
							<div class="col-sm-8">
								<div class="form-group ">
									<select id="rolUsuario" name="rolUsuario"
										data-placeholder="Seleccione un rol" class="form-control">
										<!-- tabindex="1"-->
										<c:forEach items="${roles}" var="rol">
											<option value="${rol.codigo}">${rol.descripcion}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-12 ">&nbsp;</div>
						</div>
					</form>
					<div class="row">&nbsp;</div>
					<!--
					<div class="panel panel-default">
						<div class="panel-heading">
							<spring:message code="label.maestroUsuario.adicionarUsuario" />
						</div>
						<div class="panel-body">
							<form id="asignarPermisos" name="asignarPermisos">
								<div class="col-sm-12">
									<div id="descripcionUsuarioIngresado"></div>
								</div>
								<div class="col-sm-12">&nbsp;</div>
								<div class="col-sm-12">
									<table id="tablaPermisos" name="tablaPermisos" class="table table-striped">
									</table>
								</div>
							</form>
						</div>
					</div>
					-->
					<div class="row" style="text-align: right;">
						<div class="col-sm-12 ">&nbsp;</div>
						<div class="col-sm-10">
							<button type="button" class="btn btn-azul" data-dismiss="modal" style="width: 100px;">
								<spring:message code="label.maestroUsuario.salir" />
							</button>
						</div>
						<div class="col-sm-2">
							<button type="button" class="btn btn-azul" onclick="guardar();" id="guardarUsuario"
								style="display: block" style="width:100px;">
								<spring:message code="label.boton.guardar" />
							</button>
							<button type="button" class="btn btn-azul" onclick="actualizarUsuario();"
								id="actualizarUsuario" style="display: none" data-dismiss="modal" style="width:100px;">
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
	<div id="modal-EliminarUsuario" name="modal-EliminarUsuario" class="modal fade" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1 "></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.maestroUsuario.mensajeEliminarUsuario" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroUsuario.cancelar" />
						</button>
						<button type="button" class="btn btn-white" data-dismiss="modal"
							onclick="eliminarUsuarioSeguro();">
							<spring:message code="label.maestroUsuario.eliminar" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEliminar}">
	<div id="modal-NoEliminarUsuario" name="modal-NoEliminarUsuario" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1 "></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.maestroUsuario.mensajeEliminarUsuarioNoEliminar" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroUsuario.salir" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${cons.getParametroSiCorto() == permisos.listaRolAccionOpcion[0].snEscritura}">
	<div id="modal-NoModificarUsuario" name="modal-NoModificarUsuario" class="modal fade"
		aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" style="width: 620px;">
				<div class="modal-header">
					<div class="row">
						<div class="col-sm-1 "></div>
						<div class="col-sm-10">
							<h2 class="modal-title">
								<spring:message code="label.maestroUsuario.mensajeEliminarUsuarioNoModificar" />
							</h2>
						</div>
						<div class="col-sm-1 "></div>
					</div>
					<div class="row" style="text-align: right;; padding-right: 12%;">
						<button type="button" class="btn btn-white" data-dismiss="modal">
							<spring:message code="label.maestroUsuario.salir" />
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</c:if>