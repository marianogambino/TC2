package ar.edu.unimoron.services.servicioUM;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import android.util.Log;
import ar.edu.unimoron.response.AlumnoActualizadoResponse;
import ar.edu.unimoron.response.AlumnoEnLaUmResponse;
import ar.edu.unimoron.response.AulaResponse;
import ar.edu.unimoron.response.CursosResponse;
import ar.edu.unimoron.response.DiasHorariosCursoResponse;
import ar.edu.unimoron.response.ExamenesResponse;
import ar.edu.unimoron.response.LoginResponse;
import ar.edu.unimoron.response.NovedadesResponse;
import ar.edu.unimoron.restClient.connectionRest.RestConnection;




/**
 * @author mariano
 *
 */
public class RestServiceUM {

	private RestConnection rest;
	
	
	
	public RestServiceUM( ){
		this.rest = new RestConnection();
	}
	  
	
	/**
	 * 
	 * @param usuario
	 * @param password TODO: a futuro hay que encriptarla y enviarla al servicioUM
	 * @return
	 * @throws JsonProcessingException 
	 */
	public String login(String usuario, String password){
		LoginResponse response = null; 
		String login = null;
		try {
				response = rest.getRestTemplate().getForObject( String.format( UrlServicesUM.LOGIN , usuario , password ) ,
						LoginResponse.class);
				login = new ObjectMapper().writeValueAsString(  response );
				
		 }catch (JsonProcessingException e) {
			 Log.v("LoginResponse : JsonProcessingException: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error de JSON: %s", e.getMessage() ) );
		 }catch(Exception e){
			 Log.v("LoginResponse : Exception: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error: %s", e.getMessage() ) );
		 }		
		return login;
	}
	
	/**
	 * 
	 * @param matricula
	 * @return
	 */
	public String getCursos(String matricula){
		CursosResponse  response = null; 
		String cursos = null;
		try {
				response = rest.getRestTemplate().getForObject( String.format( UrlServicesUM.CURSOS , matricula  ) ,
						CursosResponse.class);
				cursos = new ObjectMapper().writeValueAsString(  response );
				
		 }catch (JsonProcessingException e) {
			 Log.v("CursosResponse : JsonProcessingException: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error de JSON: %s", e.getMessage() ) );
		 }catch(Exception e){
			 Log.v("CursosResponse : Exception: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error: %s", e.getMessage() ) );
		 }		
		return cursos;
	}
	
	public String getCursoHorarios(String idCurso){
		DiasHorariosCursoResponse  response = null; 
		String cursoHorarios = null;
		try {
				response = rest.getRestTemplate().getForObject( String.format( UrlServicesUM.DIAS_HORARIOS_CURSOS, idCurso  ),
					DiasHorariosCursoResponse.class);
				cursoHorarios = new ObjectMapper().writeValueAsString(  response );
				
		 }catch (JsonProcessingException e) {
			 Log.v("DiasHorariosCursoResponse : JsonProcessingException: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error de JSON: %s", e.getMessage() ) );
		 }catch(Exception e){
			 Log.v("DiasHorariosCursoResponse : Exception: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error: %s", e.getMessage() ) );
		 }		
		return cursoHorarios;
	}
	
	
	/**
	 * 
	 * @param matricula
	 * @return
	 */
	public String getExamenes(String matricula){
		ExamenesResponse response = null; 
		String examenes = null;
		try {
				response = rest.getRestTemplate().getForObject( String.format( UrlServicesUM.EXAMENES , matricula ) ,
						ExamenesResponse.class);
				examenes = new ObjectMapper().writeValueAsString(  response );
				
		 }catch (JsonProcessingException e) {
			 Log.v("ExamenesResponse : JsonProcessingException: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error de JSON: %s", e.getMessage() ) );
		 }catch(Exception e){
			 Log.v("ExamenesResponse : Exception: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error: %s", e.getMessage() ) );
		 }		
		return examenes;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getNovedades( ){
		NovedadesResponse response = null; 
		String novedades = null;
		try {
				response = rest.getRestTemplate().getForObject(  UrlServicesUM.NOVEDADES   ,
						NovedadesResponse.class);
				novedades = new ObjectMapper().writeValueAsString(  response );
				
		 }catch (JsonProcessingException e) {
			 Log.v("NovedadesResponse : JsonProcessingException: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error de JSON: %s", e.getMessage() ) );
		 }catch(Exception e){
			 Log.v("NovedadesResponse : Exception: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error: %s", e.getMessage() ) );
		 }		
		return novedades;
	}
	
	/**
	 * 
	 * @param matricula
	 * @return
	 */
	public String getAlumnosUM( String matricula , String idCarrera){
		AlumnoEnLaUmResponse response = null; 
		String alumnosEnLaUM = null;
		try {
				response = rest.getRestTemplate().getForObject( String.format( UrlServicesUM.ALUMNOS_UM , matricula, idCarrera ) ,
						AlumnoEnLaUmResponse.class);
				alumnosEnLaUM = new ObjectMapper().writeValueAsString(  response );
				
		 }catch (JsonProcessingException e) {
			 Log.v("AlumnoEnLaUmResponse : JsonProcessingException: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error de JSON: %s", e.getMessage() ) );
		 }catch(Exception e){
			 Log.v("AlumnoEnLaUmResponse : Exception: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error: %s", e.getMessage() ) );
		 }		
		return alumnosEnLaUM;
	}
	
	/**
	 * 
	 * @param matricula
	 * @param enLaUM
	 * @return
	 */
	public Boolean updateAlumnoEnLaUM(String matricula, String enLaUM){
		AlumnoActualizadoResponse response = null; 
		try {
				response = rest.getRestTemplate().getForObject( String.format( UrlServicesUM.UPDATE_UBICACION_UM , matricula , enLaUM ) ,
						AlumnoActualizadoResponse.class);
				
		 }catch(Exception e){
			 Log.v("UpdateAlumnoEnLaUM : Exception: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error: %s", e.getMessage() ) );
		 }		
		return response.getIsUpdate();
	}
	

	/**
	 * 
	 * @param idCurso
	 * @return
	 */
	public String getAulaCurso(String idCurso){
		AulaResponse response = null; 
		String aula = null;
		try {
				response = rest.getRestTemplate().getForObject( String.format( UrlServicesUM.AULA_CURSO , idCurso ) ,
						AulaResponse.class);
				aula = new ObjectMapper().writeValueAsString(  response );
				
		 }catch (JsonProcessingException e) {
			 Log.v("AulaResponse : JsonProcessingException: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error de JSON: %s", e.getMessage() ) );
		 }catch(Exception e){
			 Log.v("AulaResponse : Exception: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error: %s", e.getMessage() ) );
		 }		
		return aula;
	}
	
	/**
	 * 
	 * @param lIdCurso
	 * @return
	 */
	public String getAulaExamen(String idExamen ){
		AulaResponse response = null; 
		String aula = null;
		try {
				response = rest.getRestTemplate().getForObject( String.format( UrlServicesUM.AULA_CURSO , idExamen ) ,
						AulaResponse.class);
				aula = new ObjectMapper().writeValueAsString(  response );
				
		 }catch (JsonProcessingException e) {
			 Log.v("AulaResponse : JsonProcessingException: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error de JSON: %s", e.getMessage() ) );
		 }catch(Exception e){
			 Log.v("AulaResponse : Exception: ", e.getMessage() );
			 //showLoading( String.format("Ocurrio un Error: %s", e.getMessage() ) );
		 }		
		return aula;
	}
	
}
