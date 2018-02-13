package com.example.template;

import com.example.template.model.*;
import com.example.template.request.ReqNotification;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TemplateApplicationTests {

	@Test
	public void sendMessage() throws IOException {
		/*URL url = new URL("https://www.googleapis.com/auth/firebase.messaging");
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
		httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");*/

		String auth = "key=AAAAauYlDzc:APA91bGApX4w5jxy0vfHRFuSE1X4i8TWLS8j4tHV7SyVrk_Api5zgXCxOI7h03Qv0XEhss9x-QG5AUNQLhyZ2fODaaqRXPrGE0c6tQyhG_uT-m5lNyWx64N52mkpcfAScI2jsMzIsXKh";
		String to = "c6enlIEA7lE:APA91bET6dmEm3TUj911-tgxGI6a3mjVsFmVjgFKnq9yZsfzjOwbrV1UJhEnMrPEk1C3xGkbi7KiakOnXUJIJ_lumgSC2SsJLwX4VSP6Wmox-hXbsmwyKzBi15LBs8ebERdNRmSexSvM";
		String url = "https://fcm.googleapis.com/fcm/send";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization" , auth);

		Gson gson = new Gson();

		List<Contact> contactoList = new ArrayList<>();
		contactoList.add(new Contact("1213213", "lalala"));

		String dataMsg = gson.toJson(contactoList);

		Message msg = new Message();
		msg.setTo(to);
		Data data = new Data();
		data.getBody().put("mensaje" , "Hola Yanina!!!");
		data.getBody().put("data" , dataMsg);
		msg.setData(data);
		msg.setNotification(new Notification("La pata mas rapida del OESTE!!!", "GPS FOTOS NOTIFICA"));
		HttpEntity<Message> entity = new HttpEntity<>(msg, headers);
		RestTemplate service = new RestTemplate();


		service.postForLocation(url, entity);

	}

	@Test
	public void sendNotificationToService(){

		String to = "c6enlIEA7lE:APA91bET6dmEm3TUj911-tgxGI6a3mjVsFmVjgFKnq9yZsfzjOwbrV1UJhEnMrPEk1C3xGkbi7KiakOnXUJIJ_lumgSC2SsJLwX4VSP6Wmox-hXbsmwyKzBi15LBs8ebERdNRmSexSvM";
		RestTemplate service = new RestTemplate();

		ReqNotification reqNotification = new ReqNotification();
		reqNotification.setBody("Una prueba desde el servidor");
		reqNotification.setTo(to);
		reqNotification.setFrom("1122123333");
		reqNotification.setTitle("Saludo");
		HttpEntity<ReqNotification> request = new HttpEntity<>(reqNotification);

		ResponseEntity<String> response = service.postForEntity( "http://localhost:8080/sendNotification", reqNotification , String.class );
		Assert.assertNotNull(response);
	}

	@Test
	public void getDataFirebaseDB(){
		String url = "https://gpsfotos-5bf17.firebaseio.com/usuarios.json";
		RestTemplate service = new RestTemplate();
		String users = service.getForObject(url, String.class);

		Gson gson = new Gson();
		Map<String, LinkedTreeMap<String,String>> usersMap =  gson.fromJson(users, Map.class);

		Assert.assertNotNull(users);
		LinkedTreeMap<String,String> map = (LinkedTreeMap) usersMap.get("1122223333");
		Assert.assertEquals(map.get("password") , "1234");
	}

	@Test
	public void putUserInFirebaseDB() throws IOException {

		String url = "https://gpsfotos-5bf17.firebaseio.com/usuarios.json";
		RestTemplate service = new RestTemplate();
		LinkedHashMap users = service.getForObject(url, LinkedHashMap.class);

		User user = new User();
		user.setNumTel("1199906700");
		user.setToken("sdhkjsahdjkashdkjasdhksa");
		user.setPassword("5555");
		user.setName("Prueba");


		LinkedHashMap userMap = (LinkedHashMap) users.get(user.getNumTel());
		if(userMap == null){
			users.put(user.getNumTel(), user);
			service.put(url, users);
		}


	}

	@Test
	public void updateToken() throws IOException {

		User user = new User();
		user.setNumTel("1199906700");
		user.setToken("sdhkjsahdjkashdkjasdhksa");
		user.setPassword("1234	");
		user.setName("Prueba");


		String url = "https://gpsfotos-5bf17.firebaseio.com/usuarios/"+user.getNumTel()+".json";
		RestTemplate service = new RestTemplate();
		LinkedHashMap respUser = service.getForObject(url, LinkedHashMap.class);

		String url2 = "https://gpsfotos-5bf17.firebaseio.com/usuarios.json";

		LinkedHashMap req = new LinkedHashMap();
		HttpHeaders headers = new HttpHeaders();
		MediaType mediaType = new MediaType("application", "merge-patch+json");
		headers.setContentType(mediaType);

		HttpEntity<LinkedHashMap> entity = new HttpEntity<>(req, headers);
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		ResponseEntity<Map> response = null;

		if(respUser != null){

			respUser.put("token", "5555");
			req.put(user.getNumTel(), respUser);
			response = restTemplate.exchange(url2, HttpMethod.PATCH, entity, Map.class);
		}

		Assert.assertEquals(response.getStatusCode().value(), 200);
	}

	@Test
	public void loginOrRegister(){
		//Lo obtengo como parametro
		User user = new User();
		user.setNumTel("1144446789");
		user.setToken("123ABAV");
		user.setPassword("123456");
		user.setName("Login");


		//Agregarlo en un servicio - para consulta del usuario
		//Se consulta si para verificar si existe el usuario
		String url = "https://gpsfotos-5bf17.firebaseio.com/usuarios.json";
		RestTemplate service = new RestTemplate();
		LinkedHashMap usuarios = service.getForObject(url, LinkedHashMap.class);
		LinkedHashMap usuario = (LinkedHashMap) usuarios.get(user.getNumTel());
		if(usuario == null){
			usuarios.put(user.getNumTel(), user);
			service.put(url, usuarios);
		}else{

			//Verificar la Password
			String password = (String)usuario.get("password");
			if(!user.getPassword().equalsIgnoreCase(password)){
				Assert.fail();
			}

			//Existe el usuario verifico SI cambio el token, si si lo actualizo
			String token = (String)usuario.get("token");
			if(!user.getToken().equalsIgnoreCase(token)){

				//crear update token service
				HttpHeaders headers = new HttpHeaders();
				MediaType mediaType = new MediaType("application", "merge-patch+json");
				headers.setContentType(mediaType);
				LinkedHashMap req = new LinkedHashMap();
				HttpEntity<LinkedHashMap> entity = new HttpEntity<>(req, headers);
				HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
				RestTemplate restTemplate = new RestTemplate(requestFactory);
				usuario.put("token", user.getToken());
				req.put(user.getNumTel(), usuario);
				ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.PATCH, entity, Map.class);
				Assert.assertEquals(response.getStatusCode().value(), 200);

			}
		}




	}

	@Test
	public void updateContacts(){

		User user = new User();
		user.setNumTel("1144446789");
		user.setToken("123ABAV");
		user.setPassword("123456");
		user.setName("Login");

		List<Contact> contactsMatch = new ArrayList<>();

		//Como parametro viene una lista de contactos
		List<Contact> contacts = new ArrayList<>();
		Contact contact = new Contact("1122223333", "Ricardo");
		contacts.add(contact);

		//obtener todos los usuarios
		String url = "https://gpsfotos-5bf17.firebaseio.com/usuarios.json";
		RestTemplate service = new RestTemplate();
		LinkedHashMap users = service.getForObject(url, LinkedHashMap.class);

		//matchear por los que existan
		for(Contact c : contacts){

			if ( users.get(c.getPhoneNumber() ) != null  ){
				//para el que exista, verificar si tengo el permiso de publicacion y actualizo

				String url2 = "https://gpsfotos-5bf17.firebaseio.com/permisoPublicacion/"+user.getNumTel()+".json";
				RestTemplate servicePermission = new RestTemplate();
				LinkedHashMap permissionContacts = service.getForObject(url2, LinkedHashMap.class);

				if(permissionContacts != null && permissionContacts.get(c.getPhoneNumber()) != null){
					c.setAvailable(true);
				}
				contactsMatch.add(c);
			}
		}

		//convertir a json los contactos que matchearon
		Gson gson = new Gson();
		String contactosJson = gson.toJson(contactsMatch);

		//enviar notificacion tipo data, con el tipo updateContacts
		String auth = "key=AAAAauYlDzc:APA91bGApX4w5jxy0vfHRFuSE1X4i8TWLS8j4tHV7SyVrk_Api5zgXCxOI7h03Qv0XEhss9x-QG5AUNQLhyZ2fODaaqRXPrGE0c6tQyhG_uT-m5lNyWx64N52mkpcfAScI2jsMzIsXKh";
		String to = "c6enlIEA7lE:APA91bET6dmEm3TUj911-tgxGI6a3mjVsFmVjgFKnq9yZsfzjOwbrV1UJhEnMrPEk1C3xGkbi7KiakOnXUJIJ_lumgSC2SsJLwX4VSP6Wmox-hXbsmwyKzBi15LBs8ebERdNRmSexSvM";
		String url3 = "https://fcm.googleapis.com/fcm/send";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		headers.set("Authorization" , auth);

		List<Contact> contactoList = new ArrayList<>();
		contactoList.add(new Contact("1213213", "lalala"));

		Message msg = new Message();
		msg.setTo(to);
		Data data = new Data();
		data.getBody().put("tipoMensaje" , "Contactos");
		data.getBody().put("contactos" , contactosJson);
		msg.setData(data);
		HttpEntity<Message> entity = new HttpEntity<>(msg, headers);
		RestTemplate serviceNotification = new RestTemplate();
		serviceNotification.postForLocation(url3, entity);


	}

	@Test
	public void putPermiso() throws IOException {

		String url = "https://gpsfotos-5bf17.firebaseio.com/permisoPublicacion.json";
		RestTemplate service = new RestTemplate();
		LinkedHashMap permisos = service.getForObject(url, LinkedHashMap.class);

		String permiso = "1144441111";

		List<String> numTelPermitidos = new ArrayList<>();
		numTelPermitidos.add(permiso);

		List<String> listaPermitidos = (List<String>) permisos.get("1133334444");
		if(listaPermitidos == null){
			listaPermitidos = new ArrayList<>();
			listaPermitidos.add(permiso);
			permisos.put("1133334444", listaPermitidos);

		}else{

			Boolean existePermiso = false;
			for(String p : listaPermitidos){
				if(p.equalsIgnoreCase(permiso)){
					existePermiso = true;
					break;
				}
			}
			if(!existePermiso) {
				listaPermitidos.add(permiso);
				service.put(url, permisos);
			}
		}



	}

	@Test
	public void addPermission(){

	}

	@Test
	public void acceptPermission(){

	}

	@Test
	public void obtenerPublicacionesUsuario(){

	}




	private static String getAccessToken() throws IOException {
		GoogleCredential googleCredential = GoogleCredential
				.fromStream(new FileInputStream("service-account.json"))
				.createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
		googleCredential.refreshToken();
		return googleCredential.getAccessToken();
	}

}
