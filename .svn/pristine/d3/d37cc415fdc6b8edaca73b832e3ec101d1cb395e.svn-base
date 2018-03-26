<c:if test="${demandantes.size() > 0}">
<div class="panel-group">
<div class="panel panel-default">
	<div class="panel-heading" style="font-size:15px">DEMANDANTE</div>
</div>
	<div class="panel-body">
<c:set var="countIntegrante" value="0" scope="page" />
	<c:forEach var="registro" items="${demandantes}" begin="0" varStatus="status">
		<c:if test="${registro.activo == 'S'}">
			<c:if test="${countIntegrante == 0}">
				<div class="row">
			</c:if>
			<c:if test="${countIntegrante % 2 == 0}">
				</div>
				<div class="row">
			</c:if>
			<c:choose>
				<c:when test="${registro.contacto == 'S'}">
					<div class="col-md-6 col-lg-6 integrante contacto">
				</c:when>
				<c:otherwise>
					<div class="col-md-6 col-lg-6 integrante">
				</c:otherwise>
			</c:choose>
				<div class="inner">
					<c:choose>
						<c:when test="${registro.tipoMiembro.codigo == '5'}">
							<div class="row">
								<div class="col-xs-12 text-right">
									<a onclick="mostrarModalConfirmacion(${registro.casoEquipoCasoPK.codigo},
										${registro.casoEquipoCasoPK.codigoEquipoCaso}, ${registro.casoEquipoCasoPK.miembro}, ${registro.tipoMiembro.codigo});"
										class="btn btn-circle btn-danger"><i class="glyphicon glyphicon-trash"></i></a>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="row">
								<div class="col-xs-12 text-right">
									<a href="javascript:mostrarModalConfirmacion(${registro.casoEquipoCasoPK.codigo},
										${registro.casoEquipoCasoPK.codigoEquipoCaso}, ${registro.casoEquipoCasoPK.miembro});"
										class="btn btn-circle btn-danger"><i class="glyphicon glyphicon-trash"></i></a>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="row">
						<div class="col-xs-5">
							<div class="m-b-sm text-center">
								<c:choose>
									<c:when test="${empty registro.equipoCaso.nombreFoto}">
										<img alt="image" class="img-circle img-responsive" src="img/usuario-azul.png">
										<p>${registro.tipoMiembro.nombre}</p>
									</c:when>
									<c:otherwise>
										<img alt='image' class="img-circle img-responsive" src='/imagenes/${registro.equipoCaso.nombreFoto}'/>
										<p>${registro.tipoMiembro.nombre}</p>
									</c:otherwise>
								</c:choose>
							</div>
						</div>
						<div class="col-xs-7" name="informacionMiembro">
							<c:if test="${registro.tipoMiembro.codigo == '5'}">
								<a data-toggle="modal" data-parent="#accordion" onclick="modificarMiembroEquipo(this)"
									href="#modal-modificarDemandado">${registro.equipoCaso.nombre} ${registro.equipoCaso.apellido}</a>
							</c:if>
							<c:if test="${registro.tipoMiembro.codigo != '5'}">
								<a data-toggle="modal" data-parent="#accordion" onclick="modificarMiembroEquipo(this)"
									href="#modal-modificarDemandado">${registro.equipoCaso.nombre} ${registro.equipoCaso.apellido}</a>
							</c:if>
							<hr class="border-bottom">
							<c:if test="${registro.tipoMiembro.codigo != 4}">
								<p class="empresa">${registro.equipoCaso.identificacion}</p>
							</c:if>
							<p class="dato">${registro.parentesco.nombre}</p>
							<input hidden="hidden" name="txtCodigoEquipoCaso" value="${registro.equipoCaso.codigoEquipoCaso}" />
							<input hidden="hidden" name="txtCodigoTipoMiembro" value="${registro.tipoMiembro.codigo}" />
							<div class="row">
								<c:choose>
									<c:when test="${empty registro.direccion}">
										<div class="col-md-12 titulo-dato">
											<spring:message code="label.detalleCaso.direccion" />
										</div>
									</div>
									</c:when>
									<c:otherwise>
										<div class="col-md-12 titulo-dato">
											<spring:message code="label.detalleCaso.direccion" />
										</div>
									</div>
										<div class="dato">
											${registro.direccion}
										</div>
									</c:otherwise>
									
								</c:choose>
							
									
							
							<div class="row">
								<div class="col-md-5 titulo-dato ">
									<spring:message code="label.detalleCaso.telefono" />
								</div>
								<div class="col-md-7 titulo-dato ">
									<SPAN title='Ver detalles'><a name='masTelefonoDemandante<c:out value="${status.count}"/>'><i
										id='masTelefonoDemandante<c:out value="${status.count}"/>' class='glyphicon glyphicon-chevron-down'
										onclick="mostrarTelefonoDemandante(<c:out value="${status.count}"/>)" style="display: block"></i></a></SPAN>
									<SPAN title='Ver detalles'><a name='menosTelefonoDemandante<c:out value="${status.count}"/>'><i
										id='menosTelefonoDemandante<c:out value="${status.count}"/>' class='glyphicon glyphicon-chevron-up'
										onclick="ocultarTelefonoDemandante(<c:out value="${status.count}"/>)" style="display: none"></i></a></SPAN>
								</div>
							</div>
							<div id="campoTelefonoDemandante<c:out value="${status.count}"/>" style='display: none;'>
								<c:forEach var="registroTelefonoDemandante" items="${registro.equipoCaso.telefonoList}" begin="0" end="3">
									<p class="dato">${registroTelefonoDemandante.numero}</p>
								</c:forEach>
							</div>
							<div class="row">
								<div class="col-md-5 titulo-dato ">
									<spring:message code="label.detalleCaso.celular" />
								</div>
								<div class="col-md-7 titulo-dato ">
									<SPAN title='Ver detalles'><a name='masCelularDemandante<c:out value="${status.count}"/>'><i
										id='masCelularDemandante<c:out value="${status.count}"/>' class='glyphicon glyphicon-chevron-down'
										onclick="mostrarCelularDemandante(<c:out value="${status.count}"/>)" style="display: block"></i></a></SPAN>
									<SPAN title='Ver detalles'><a name='menosCelularDemandante<c:out value="${status.count}"/>'><i
										id='menosCelularDemandante<c:out value="${status.count}"/>' class='glyphicon glyphicon-chevron-up'
										onclick="ocultarCelularDemandante(<c:out value="${status.count}"/>)" style="display: none"></i></a></SPAN>
								</div>
							</div>
							<div id="campoCelularDemandante<c:out value="${status.count}"/>" style='display: none;'>
								<c:forEach var="registroCelularDemandante" items="${registro.equipoCaso.celularList}" begin="0" end="3">
									<p class="dato">${registroCelularDemandante.numero}</p>
								</c:forEach>
							</div>
							<div class="row">
								<div class="col-md-5 titulo-dato ">
									<spring:message code="label.detalleCaso.correo" />
								</div>
								<div class="col-md-7 titulo-dato ">
									<SPAN title='Ver detalles'><a name='masCorreoDemandante<c:out value="${status.count}"/>'><i
										id='masCorreoDemandante<c:out value="${status.count}"/>' class='glyphicon glyphicon-chevron-down'
										onclick="mostrarEmailDemandante(<c:out value="${status.count}"/>)" style="display: block"></i></a></SPAN>
									<SPAN title='Ver detalles'><a name='menosCorreo<c:out value="${status.count}"/>'><i id='menosCorreo<c:out value="${status.count}"/>'
										class='glyphicon glyphicon-chevron-up' onclick="ocultarEmailDemandante(<c:out value="${status.count}"/>)"
										style="display: none"></i></a></SPAN>
								</div>
							</div>
							<div id="campoCorreoDemandante<c:out value="${status.count}"/>" style='display: none;'>
								<c:forEach var="registroEmailDemandante" items="${registro.equipoCaso.correoList}" begin="0" end="3">
									<p class="dato">${registroEmailDemandante.correo}</p>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
			<c:set var="countIntegrante" value="${countIntegrante + 1}" scope="page" />
		</c:if>
	</c:forEach>
	<c:if test="${countIntegrante != 0}">
		</div>
	</c:if>
	</div>
	</div>
</c:if>