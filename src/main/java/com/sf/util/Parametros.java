/**
 *
 */
package com.sf.util;

import java.util.ResourceBundle;

/**
 * @author DIEGO BLANDON
 *
 */
public class Parametros{
	
	private static ResourceBundle properties = ResourceBundle.getBundle("com.sf.util.parametros");
    
    public Parametros() {
    }

    public static String[] getListadoAbecedarioContactos() {
    	return properties.getString("ABECEDARIO.CONTACTOS").split(",");
    }
    
    public static String getUrlToken(){
    	return properties.getString("URL.ENVIO.TOKEN");
    }
        
    public static String getCorreoEnvioDocumentos() {
    	return properties.getString("CORREO.ENVIO.DOCUMENTOS");
    }
    
    public static String getPasswordCorreoEnvios() {
    	return properties.getString("PASSWORD.CORREO.ENVIOS");
    }
    
    public static String getMailProtocol() {
    	return properties.getString("MAIL.PROTOCOL");
    }
    
    public static int getMailPort() {
    	return Integer.parseInt(properties.getString("MAIL.PORT"));
    }
    
    public static String getMailHost() {
    	return properties.getString("MAIL.HOST");
    }
    
    public static Long getMaxSizeArchivo() {
    	return Long.parseLong(properties.getString("MAX.SIZE.ARCHIVO"));
    }
    
    public static int getMaxInSizeMemory() {
    	return Integer.parseInt(properties.getString("MAX.SIZE.IN.MEMORY"));
    }
    
    public static String getRutaCargaArchivos() {
    	return properties.getString("RUTA.CARGA.ARCHIVOS");
    }
    
    public static Integer getEstadoConstruccion() {
    	return new Integer(properties.getString("ESTADO.CONSTRUCCION"));
    }
    
    public static Integer getEstadoEvaluacion() {
    	return new Integer(properties.getString("ESTADO.EVALUACION"));
    }
    
    public static Integer getEstadoPlanDesarrollo() {
    	return new Integer(properties.getString("ESTADO.PLANDESARROLLO"));
    }
    
    public static String getEstadoActivo() {
    	return properties.getString("ESTADO.TIPOCASO.ACTIVO");
    }
    public static String getEstadoInactivo() {
    	return properties.getString("ESTADO.TIPOCASO.INACTIVO");
    }
    public static String getAnosCaducidad() {
    	return properties.getString("ANOS.CADUCIDAD");
    }
    public static String getMensajeErrorLogin() {
    	return properties.getString("LOGIN.MENSAJE.ERROR");
    }
    public static String getEstadoActividadPendiente() {
    	return properties.getString("ESTADO.ACTIVIDAD.PENDIENTE");
    }
    public static String getEstadoActividadFinalizada() {
    	return properties.getString("ESTADO.ACTIVIDAD.FINALIZADA");
    }
    public static String getEstadoActividadVencida() {
    	return properties.getString("ESTADO.ACTIVIDAD.VENCIDA");
    }
    
    public static String getMiembroContactoSi() {
    	return properties.getString("MIEMBRO.CONTACTO.SI");
    }
    
    public static String getMiembroContactoNo() {
    	return properties.getString("MIEMBRO.CONTACTO.NO");
    }
    
    public static String getCodigoRadicadoActivoSi() {
    	return properties.getString("CODIGO.RADICADO.ACTIVO.SI");
    }
    
    public static String getCodigoRadicadoActivoNo() {
    	return properties.getString("CODIGO.RADICADO.ACTIVO.NO");
    }
    
    public static String getCodigoRadicadoAcumuladoSi() {
    	return properties.getString("CODIGO.RADICADO.ACUMULADO.SI");
    }
    
    public static String getCodigoRadicadoAcumuladoNo() {
    	return properties.getString("CODIGO.RADICADO.ACUMULADO.NO");
    }
    
    public static String getParametroNo() {
    	return properties.getString("PARAMETRO.NO");
    }
    
    public static String getParametroNoCorto() {
    	return properties.getString("PARAMETRO.NOCORTO");
    }
    
    public static String getParametroSi() {
    	return properties.getString("PARAMETRO.SI");
    }
    
    public static String getParametroSiCorto() {
    	return properties.getString("PARAMETRO.SICORTO");
    }
    
    public static String getParametroActivo() {
    	return properties.getString("PARAMETRO.ACTIVO");
    }
    
    public static String getParametroInactivo() {
    	return properties.getString("PARAMETRO.INACTIVO");
    }
    
    public static String getCodigoBienActivoSi() {
    	return properties.getString("CODIGO.BIENAFECTADO.ACTIVO.SI");
    }
    
    public static String getCodigoBienActivoNo() {
    	return properties.getString("CODIGO.BIENAFECTADO.ACTIVO.NO");
    }
    
    public static String getCodigoTareaActivoSi() {
    	return properties.getString("CODIGO.TAREA.ACTIVO.SI");
    }
    
    public static String getCodigoTareaActivoNo() {
    	return properties.getString("CODIGO.TAREA.ACTIVO.NO");
    }
    
    public static String getCodigoActividadActivoSi() {
    	return properties.getString("CODIGO.ACTIVIDAD.ACTIVO.SI");
    }
    
    public static String getCodigoActividadActivoNo() {
    	return properties.getString("CODIGO.ACTIVIDAD.ACTIVO.NO");
    }
    
    public static String getRutaCargarExcel(){
    	return properties.getString("RUTA.CARGAR.EXCEL");
    }
    
    public static String getRutaArchivoLista(){
    	return properties.getString("RUTA.CARGAR.ARCHIVOS.LISTA");
    }
    
    public static String getParentescoEsposo(){
    	return properties.getString("PARENTESCO.ESPOSO.A");
    }
    
    public static String getParentescoHijo(){
    	return properties.getString("PARENTESCO.HIJO.A");
    }
    
    public static String getParentescoMadre(){
    	return properties.getString("PARENTESCO.MADRE.PADRE");
    }
        
    public static String getParentescoHermano(){
    	return properties.getString("PARENTESCO.HERMANO.A");
    }
    
    public static String getParentescoAbuelo(){
    	return properties.getString("PARENTESCO.ABUELO.A");
    }
        
    public static String getParentescoOtro(){
    	return properties.getString("PARENTESCO.OTRO.A");
    }
    
    //Modificacion adición de nuevos parentestos
    public static String getParentescoNieto(){
    	return properties.getString("PARENTESCO.NIETO.A");
    }
    
    public static String getParentescoSobrinos(){
    	return properties.getString("PARENTESCO.SOBRINOS.A");
    }
    
    public static String getParentescoBisnietos(){
    	return properties.getString("PARENTESCO.BISNIETOS.A");
    }
    
    public static String getParentescoPrimos(){
    	return properties.getString("PARENTESCO.PRIMOS.A");
    }
    
    public static String getParentesco3rosDamnificados(){
    	return properties.getString("PARENTESCO.3DAMNIFICADOS.A");
    }
    
    public static String getParentescoBisabuelos(){
    	return properties.getString("PARENTESCO.BISABUELOS.A");
    }
    
    public static String getParentescoTio(){
    	return properties.getString("PARENTESCO.NIETO.A");
    }    
    
    
    
    public static String getIsContacto(){
    	return properties.getString("CONTACTO.ISCONTACTO");
    }
        
    public static String getVictima(){
    	return properties.getString("CDMIEMBRO.VICTIMA");
    }
    
    public static String getDemandante(){
    	return properties.getString("CDMIEMBRO.DEMANDANTE");
    }
    public static String getDemandado(){
    	return properties.getString("CDMIEMBRO.DEMANDADO");
    }
    public static String getOtro(){
    	return properties.getString("CDMIEMBRO.OTRO");
    }
    public static String getReferenciador(){
    	return properties.getString("CDMIEMBRO.REFERENCIADOR");
    }
    public static String getAbogado(){
    	return properties.getString("CDMIEMBRO.ABOGADO");
    }
    public static String getTestigo(){
    	return properties.getString("CDMIEMBRO.TESTIGO");
    }
        
    public static String getNoContacto(){
    	return properties.getString("CONTACTO.NOCONTACTO");
    }
    
    public static String getContactoActivo(){
    	return properties.getString("CONTACTO.ACTIVO");
    }
    
    public static String getContactoInactivo(){
    	return properties.getString("CONTACTO.INACTIVO");
    }
        
    public static String getTipoCasoOffLine(){
    	return properties.getString("ORIGEN.CASO.OFFLINE");
    }
    
    public static String getTipoCasoOnLine(){
    	return properties.getString("ORIGEN.CASO.ONLINE");
    }
        
    public static String getActividadPoder(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.PODER");
    }
        
    public static String getActividadPoderProcuraduria(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.PODER.PROCURADURIA");
    }
    
    public static String getActividadFotocopiaCC(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.FOTOCOPIA.CC");
    }
    
    public static String getActividadContratoMandato(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.CONTRATO.MANDATO");
    }
    
    public static String getActividadPreclusion(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.PRECLUSION");
    }
        
    public static String getActividadBoletaLibertad(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.BOLETA.LIBERTAD");
    }
    
    public static String getActividadHistoria(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.HISTORIA");
    }
        
    public static String getActividadRelatoHechos(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.RELATO.HECHOS");
    }
    
    public static String getActividadPartidaBautismo(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.PARTIDA.BAUTISMO");
    }
    public static String getActividadMatrimonio(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.MATRIMONIO");
    }
    public static String getActividadTransito(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.COPIA.TRANSITO");
    }
    public static String getActividadJuntaMedica(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.JUNTA.MED");
    }
   
    public static String getActividadRegistroNaciento(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.REGISTRO.CIVIL.NACIENTO");
    }
    public static String getActividadRegistroDefuncion(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.REGISTRO.CIVIL.DEFUNCION");
    }
    public static String getActividadOtros(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.OTROS");
    }
    public static String getActividadPartidaMatrimonio(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO.PARTIDA.MATRIMONIO");
    }
    public static String getActividadDocumentosRequeridos(){
    	return properties.getString("ACTIVIDADES.DOCUMENTO.REQUERIDO");
    }
    public static String getActividadFinalizada(){
    	return properties.getString("ACTIVIDAD.ESTADO.FINALIZADA");
    }
    public static String getActividadPendiente(){
    	return properties.getString("ACTIVIDAD.ESTADO.PENDIENTE");
    }
    public static String getActividadVencida(){
    	return properties.getString("ACTIVIDAD.ESTADO.VENCIDA");
    }
    
    public static String getAuditoriaBienAfectado(){
    	return properties.getString("PARAMETRO.ELIMINACION.BIENAFECTADO");
    }
    
    public static String getAuditoriaRadicado(){
    	return properties.getString("PARAMETRO.ELIMINACION.RADICADO");
    }
    
    public static String getAuditoriaTarea(){
    	return properties.getString("PARAMETRO.ELIMINACION.TAREA");
    }
    
    public static String getAuditoriaActividad() {
    	return properties.getString("PARAMETRO.ELIMINACION.ACTIVIDAD");
    }
    
    public static String getAuditoriaMiembro() {
    	return properties.getString("PARAMETRO.ELIMINACION.MIEMBRO");
    }
    
    public static String getAuditoriaCaso() {
    	return properties.getString("PARAMETRO.ESTADO.CASO");
    }
    public static String getTipoAccionModificacion() {
    	return properties.getString("TIPO.ACCION.MODIFICACION");
    }
    
    public static String getTipoAccionEliminacion() {
    	return properties.getString("TIPO.ACCION.ELIMINACION");
    }
    
    public static String getCampoModificadoBienAfectado() {
    	return properties.getString("CAMPO.MODIFICADO.BIENAFECTADO");
    }
    
    public static String getCampoModificadoRadicado() {
    	return properties.getString("CAMPO.MODIFICADO.RADICADO");
    }
    public static String getCampoModificadoTarea() {
    	return properties.getString("CAMPO.MODIFICADO.TAREA");
    }
    public static String getCampoModificadoActividad() {
    	return properties.getString("CAMPO.MODIFICADO.ACTIVIDAD");
    }
    public static String getCampoModificadoMiembro() {
    	return properties.getString("CAMPO.MODIFICADO.MIEMBRO");
    }
    
    public static String getCampoModificadoCaso() {
    	return properties.getString("CAMPO.MODIFICADO.CASO");
    }
    
    public static String getPrestamoActivo() {
    	return properties.getString("PARAMETRO.PRESTAMO.ACTIVO");
    }
    public static String getPrestamoInactivo() {
    	return properties.getString("PARAMETRO.PRESTAMO.INACTIVO");
    }
    public static String getPrestamoCanceladoSi() {
    	return properties.getString("PARAMETRO.PRESTAMO.CANCELADO.SI");
    }
    public static String getPrestamoCanceladoNO() {
    	return properties.getString("PARAMETRO.PRESTAMO.CANCELADO.NO");
    }
   
    public static String getRolAdmind() {
    	return properties.getString("PARAMETRO.ROL.ADMIN");
    }
    
    public static String getRolSecretaria() {
    	return properties.getString("PARAMETRO.ROL.SECRETARIA");
    }
    public static String getRolAbogado() {
    	return properties.getString("PARAMETRO.ROL.ABOGADO");
    }
    public static String getRolSocio() {
    	return properties.getString("PARAMETRO.ROL.SOCIO");
    }
    public static String getRolDependiente() {
    	return properties.getString("PARAMETRO.ROL.DEPENDIENTE");
    }
    public static String getRolContabilidad() {
    	return properties.getString("PARAMETRO.ROL.CONTABILIDAD");
    }
    public static String getRolAsociado() {
    	return properties.getString("PARAMETRO.ROL.ASOCIADO");
    }
    public static String getAsuntoActualizacion() {
    	return properties.getString("PARAMETRO.ASUNTO.ACTUALIZACION.ESTADO");
    }
    public static String getAsuntoModificacionFechas() {
    	return properties.getString("PARAMETRO.ASUNTO.MODIFICACION.FECHAS");
    }
    public static String getCodigoActividadPropiaSi() {
    	return properties.getString("CODIGO.ACTIVIDAD.PROPIA.SI");
    }
    public static String getCodigoActividadPropiaNo() {
    	return properties.getString("CODIGO.ACTIVIDAD.PROPIA.NO");
    }
    public static String getActividadSolicitudPrejudicial() {
    	return properties.getString("ACTIVIDADES.SOLICITUD.PREJUDICIAL");
    }
    
    public static String getIsPersonaJuridica () {
    	return properties.getString("PARAMETRO.ISPERSONA.JURIDICA");
    }
    
    public static String getNotPersonaJuridica (){
    	return properties.getString("PARAMETRO.NOTPERSONA.JURIDICA");
    }
    
    public static String getMiembroActivoNo() {
    	return properties.getString("MIEMBRO.ACTIVO.NO");
    }
    
    public static String getMiembroActivoSi(){
    	return properties.getString("MIEMBRO.ACTIVO.SI");
    }
    
    public static Integer getHoraInicioProceso() {
    	return Integer.parseInt(properties.getString("HORA.EJECUCION.PROCESO.AUTOMATICO.INICIO"));
    }
    
    public static Integer getHoraFinProceso() {
    	return Integer.parseInt(properties.getString("HORA.EJECUCION.PROCESO.AUTOMATICO.FIN"));
    }
  
    public static Integer getTiempoReabrirProcesoNotificaciones() {
    	return Integer.parseInt(properties.getString("TIEMPO.REABRIR.PROCESO.NOTIFICACIONES"));
    }
    
    public static String getMensajeNotificacionVencimientoTarea() {
    	return properties.getString("MENSAJE.NOTIFICACION.VENCIMIENTO.TAREA");
    }
    
    public static String getAsuntoNotificacionTarea() {
    	return properties.getString("ASUNTO.TAREA.PROXIMA.VENCER");
    }
    
    public static String getFormatoFechaString() {
    	return properties.getString("FORMATO.FECHAS.STRING");
    }
    
    public static String getFormatoAnioMesDiaFechaString() {
    	return properties.getString("FORMATO.FECHAS.ANIO.MES.DIA.STRING");
    }
    
    public static String getFormatoAnioMesDiaFechaSlashes() {
    	return properties.getString("FORMATO.FECHAS.ANIO.MES.DIA.SLASHES");
    }
    
    public static String getFormatoAnioMesDiaFechaGuionesString() {
    	return properties.getString("FORMATO.FECHAS.DIA.MES.ANIO.GUIONES");
    }
   
    public static String getMensajeVencimientoTareaHoy() {
    	return properties.getString("MENSAJE.VENCIMIENTO.TAREA.DIARIA");
    }
    
    public static String getAsuntoEliminacionActividad() {
    	return properties.getString("PARAMETRO.ASUNTO.ELIMINACION.ACTIVIDAD");
    }
    public static String getAsuntoEliminacionTarea() {
    	return properties.getString("PARAMETRO.ASUNTO.ELIMINACION.TAREA");
    }
    
    public static String getActividadAudiencia() {
    	return properties.getString("ACTIVIDADES.AUDIENCIA");
    }
    
    public static String getOrdenarDescendente() {
    	return properties.getString("ORDENAR.DESCENDENTE");
    }
    
    public static String getOrdenarAscendente() {
    	return properties.getString("ORDENAR.ASCENDENTE");
    }
    public static String getFormatoFechaDiaMesAnio() {
    	return properties.getString("FORMATO.FECHAS.DIA.MES.ANIO");
    }
    public static String getActividadPrejudicial() {
    	return properties.getString("ACTIVIDADES.PREJUDICIAL");
    }
    
    public static String getOrdenarAlbafeticamenteAZ() {
    	return properties.getString("ORDENAR.ALFABETICAMENTE.AZ");
    }
    public static String getOrdenarAlbafeticamenteZA() {
    	return properties.getString("ORDENAR.ALFABETICAMENTE.ZA");
    }
    public static String getActividadRecursosPendientes() {
    	return properties.getString("ACTIVIDADES.RECURSOS.PENDIENTES");
    }
    public static String getTokenNoUsado() {
    	return properties.getString("PARAMETRO.TOKEN.NO.USADO");
    }
    public static String getTokenUsado() {
    	return properties.getString("PARAMETRO.TOKEN.USADO");
    }
    public static Integer getHoraInicioProcesovencimientos() {
    	return Integer.parseInt(properties.getString("HORA.EJECUCION.PROCESO.VENCIMIENTO"));
    }
    
    public static Integer getMinutoInicioProcesovencimientos() {
    	return Integer.parseInt(properties.getString("HORA.EJECUCION.PROCESO.VENCIMIENTO"));
    }
    
    public static Integer getCodigoDocumentosRequeridos() {
    	return Integer.parseInt(properties.getString("CODIGO.DOCUMENTOS.REQUERIDOS"));
    }
    
    public static Integer getCodigoSolicitudPrejudicial() {
    	return Integer.parseInt(properties.getString("CODIGO.SOLICITUD.PREJUDICIAL"));
    }
    
    public static Integer getCodigoAudiencia() {
    	return Integer.parseInt(properties.getString("CODIGO.AUDIENCIA"));
    }
    
    public static Integer getCodigoRecursosPendientes() {
    	return Integer.parseInt(properties.getString("CODIGO.RECURSOS.PENDIENTES"));
    }
    public static Integer getDiasPorDefectoVencimientoTareas() {
    	return Integer.parseInt(properties.getString("DIAS.POR.DEFECTO.FILTRO.VENCIMIENTO.TAREAS"));
    }
    public static Integer getDiasSemaforoColorNaranja() {
    	return Integer.parseInt(properties.getString("PARAMETRO.DIAS.COLOR.NARANJA"));
    }
    public static Integer getDiasSemaforoColorAmarillo() {
    	return Integer.parseInt(properties.getString("PARAMETRO.DIAS.COLOR.AMARILLO"));
    }
    public static String getEstadoActivoTarea() {
    	return properties.getString("tarea.activa");
    }
    public static String getActividadPrincipalAudiencia() {
    	return properties.getString("actividad.principal.audiencia");
    }
    public static String getActividadPrincipalRecursos() {
    	return properties.getString("actividad.principal.recursos");
    }
    public static String getActividadPrincipalDocumento() {
    	return properties.getString("actividad.principal.documento");
    }
    public static String getActividadPrincipalSolicitud() {
    	return properties.getString("actividad.principal.solicitud");
    }
    
    
}
