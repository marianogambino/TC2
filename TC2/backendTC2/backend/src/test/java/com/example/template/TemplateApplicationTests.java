package com.example.template;

import com.example.template.model.Contact;
import com.example.template.model.Data;
import com.example.template.model.Message;
import com.example.template.model.Notification;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	public void persistFirebaseDB() throws IOException {

		FileInputStream serviceAccount = new FileInputStream("gpsfotos-5bf17-firebase-adminsdk-mw882-523cf033fc.json");
		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https://gpsfotos-5bf17.firebaseio.com/")
				.build();
		FirebaseApp.initializeApp(options);

		DatabaseReference mDatabase;
		mDatabase = FirebaseDatabase.getInstance().getReference();
		mDatabase.child("users").setValue(user);

	}

	@Test
	public void getDataFirebaseDB(){


	}



	private static String getAccessToken() throws IOException {
		GoogleCredential googleCredential = GoogleCredential
				.fromStream(new FileInputStream("service-account.json"))
				.createScoped(Arrays.asList("https://www.googleapis.com/auth/firebase.messaging"));
		googleCredential.refreshToken();
		return googleCredential.getAccessToken();
	}

}
