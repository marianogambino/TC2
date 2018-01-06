package ar.edu.unimoron.restClient.connectionRest;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


/**
 * 
 * @author mgambino
 *
 */
public class RestConnection {
	
	private RestTemplate restTemplate;
	
	public RestConnection( ){
		 this.restTemplate = new RestTemplate();
         this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
	}

	/**
	 * @return the restTemplate
	 */
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	/**
	 * @param restTemplate the restTemplate to set
	 */
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	

}
