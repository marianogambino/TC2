/**
 * 
 */
package ar.edu.unimoron.services.servicioUM;


/**
 * @author mariano
 *
 */
public class UrlServicesUM {

	public static final String DOMAIN =  "http://serviciosum.ddns.net:3000"; //"http://serviciosUM.zyns.com:3000";
	
	public static final String LOGIN = 
			DOMAIN + "/services/RestServiceUM/service/login?usuario=%s&password=%s";
	
	
	public static final String CURSOS = 
			DOMAIN + "/services/RestServiceUM/service/cursos?matricula=%s";
	
	
	public static final String EXAMENES = 
			DOMAIN + "/services/RestServiceUM/service/examenes?matricula=%s";
	
	
	public static final String NOVEDADES = 
			DOMAIN + "/services/RestServiceUM/service/novedadesUM";
	
	public static final String ALUMNOS_UM = 
			DOMAIN + "/services/RestServiceUM/service/alumnosEnLaUM?matricula=%s&idCarrera=%s";
	
	public static final String DIAS_HORARIOS_CURSOS = 
			DOMAIN + "/services/RestServiceUM/service/diasHorariosCursos?idCurso=%s";
	
	public static final String AULA_CURSO = 
			DOMAIN + "/services/RestServiceUM/service/aulaCurso?idCurso=%s";
	
	public static final String AULA_EXAMEN = 
			DOMAIN + "/services/RestServiceUM/service/aulaExamen?idExamen=%s";
	
	public static final String ALUMNOS_MISMO_CURSO = 
			DOMAIN + "/services/RestServiceUM/service/alumnosIncriptosMismoCurso?matricula=%s&idCurso=%s";
	
	public static final String ALUMNOS_MISMO_EXAMEN  = 
			DOMAIN + "/services/RestServiceUM/service/alumnosIncriptosMismoExamen?matricula=%s&idExamen=%s"; 
	
	
	public static final String UPDATE_UBICACION_UM  = 
			DOMAIN + "/services/RestServiceUM/service/updateUbicacionUM?matricula=%s&estaEnLaUM=%s"; 
	
	
}
