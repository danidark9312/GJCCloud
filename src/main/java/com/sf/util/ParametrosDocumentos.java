package com.sf.util;

import java.util.ResourceBundle;

public class ParametrosDocumentos {
	
	private static ResourceBundle properties = ResourceBundle.getBundle("com.sf.util.parametros_documentos");
	
	public ParametrosDocumentos() {
	}
	
	
	public static String getParentescoEsposo() {
		return properties.getString("PARAMETRO.PARENTESCO.ESPOSO");
	}
	
	public static String getParentescoHijo() {
		return properties.getString("PARAMETRO.PARENTESCO.HIJO");
	}
	
	public static String getParentescoMadre() {
		return properties.getString("PARAMETRO.PARENTESCO.MADRE");
	}
	
	public static String getParentescoPadre() {
		return properties.getString("PARAMETRO.PARENTESCO.PADRE");
	}
	
	public static String getParentescoHermano() {
		return properties.getString("PARAMETRO.PARENTESCO.HERMANO");
	}
	
	public static String getParentescoAbuelo() {
		return properties.getString("PARAMETRO.PARENTESCO.ABUELO");
	}
	
	public static String getParentescoOtro() {
		return properties.getString("PARAMETRO.PARENTESCO.OTRO");
	}
	
	public static String getParentescoNietos() {
		return properties.getString("PARAMETRO.PARENTESCO.NIETOS");
	}
	
	public static String getParentescoTios() {
		return properties.getString("PARAMETRO.PARENTESCO.TIOS");
	}
	
	public static String getParentescoSobrinos() {
		return properties.getString("PARAMETRO.PARENTESCO.SOBRINOS");
	}
	
	public static String getParentescoBisnietos() {
		return properties.getString("PARAMETRO.PARENTESCO.BISNIETOS");
	}
	
	public static String getParentescoPrimos() {
		return properties.getString("PARAMETRO.PARENTESCO.PRIMOS");
	}
	
	public static String getParentescoDanmificados() {
		return properties.getString("PARAMETRO.PARENTESCO.DANMIFICADOS");
	}
	
	public static String getParentescoBisabuelos() {
		return properties.getString("PARAMETRO.PARENTESCO.BISABUELOS");
	}
	

	public static String getDocumentosParentescoEsposo() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.ESPOSO");
	}
	
	public static String getDocumentosParentescoHijo() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.HIJO");
	}
	
	public static String getDocumentosParentescoMadrePadre() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.MADRE.PADRE");
	}
	
	public static String getDocumentosParentescoHermano() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.HERMANO");
	}
	
	public static String getDocumentosParentescoAbuelo() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.ABUELO");
	}
	
	public static String getDocumentosParentescoNietos() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.NIETO");
	}
	
	public static String getDocumentosParentescoTio() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.TIO");
	}
	
	public static String getDocumentosParentescoSobrinos() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.SOBRINOS");
	}
	
	public static String getDocumentosParentescoBisnietos() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.BISNIETOS");
	}
	
	public static String getDocumentosParentescoPrimos() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.PRIMOS");
	}
	
	public static String getDocumentosParentesco3danmificados() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.3DAMNIFICADOS");
	}
	
	public static String getDocumentosParentescoBisabuelos() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.BISABUELOS");
	}
	
	public static String getDocumentosParentescoOtro() {
		return properties.getString("PARAMETRO.DOCUMENTOS.PARENTESCO.OTRO");
	}
	
	public static String getDocumentoPoder() {
		return properties.getString("DOCUMENTO.PODER");
	}
	
	public static String getDocumentoPoderProcuraduria() {
		return properties.getString("DOCUMENTO.PODER.PROCURADURIA");
	}
	
	public static String getDocumentoFotocopiaCC() {
		return properties.getString("DOCUMENTO.FOTOCOPIA.CC");
	}
	
	public static String getDocumentoContratoMandato() {
		return properties.getString("DOCUMENTO.CONTRATO.MANDATO");
	}
	
	public static String getDocumentoJuntaMed() {
		return properties.getString("DOCUMENTO.JUNTA.MED");
	}
	
	public static String getDocumentoProcesoTransito() {
		return properties.getString("DOCUMENTO.PROCESO.TRANSITO");
	}
	
	public static String getDocumentoSentencia() {
		return properties.getString("DOCUMENTO.PRECLUSION.SENTENCIA");
	}
	
	public static String getDocumentoBoletaLibertad() {
		return properties.getString("DOCUMENTO.BOLETA.LIBERTAD");
	}
	
	public static String getDocumentoHistoriaClinica() {
		return properties.getString("DOCUMENTO.HISTORIA.CLINICA");
	}
	
	public static String getDocumentoRelatoHechos() {
		return properties.getString("DOCUMENTO.RELATO.HECHOS");
	}
	
	public static String getDocumentoPartidaBautismo() {
		return properties.getString("DOCUMENTO.PARTIDA.BAUTISMO");
	}
	
	public static String getDocumentoPartidaMatrimonio() {
		return properties.getString("DOCUMENTO.PARTIDA.MATRIMONIO");
	}
	
	public static String getDocumentoRegistroMatrimonio() {
		return properties.getString("DOCUMENTO.REGISTRO.MATRIMONIO");
	}
	
	public static String getDocumentoRegistroDefuncion() {
		return properties.getString("DOCUMENTO.REGISTRO.DEFUNCION");
	}
	
	public static String getDocumentoOtros() {
		return properties.getString("DOCUMENTO.OTROS");
	}
	
	public static String getDocumentoRegistroCivil() {
		return properties.getString("DOCUMENTO.REGISTRO.CIVIL.NACIMIENTO");
	}
}
