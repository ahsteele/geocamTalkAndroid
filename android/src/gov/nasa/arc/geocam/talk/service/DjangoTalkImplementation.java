package gov.nasa.arc.geocam.talk.service;


import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import roboguice.inject.InjectResource;
import android.content.Context;
import android.widget.Toast;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DjangoTalkImplementation implements DjangoTalkInterface{
// TODO: Review as to whether we should be binding a class or an instance of this class
	@Inject DjangoTalkJsonConverterInterface jsonConverter;
	@InjectResource(R.string.url_server_root) String serverRootUrl;
	@InjectResource(R.string.url_relative_app) String appPath;
	@InjectResource(R.string.url_message_list) String talkMessagesJson;
	@Inject protected static Provider<Context> contextProvider;
	@Inject HttpClient httpClient;	
	
	@Override
	public List<GeoCamTalkMessage> getTalkMessages() {
		//String jsonString = 
		//	"[{\"authorUsername\": \"rhornsby\", \"longitude\": -122.057954, \"content\": \"Structural engineer not allowing access to building. Fire is too out of control. Fire squad alerted.\", \"contentTimestamp\": \"03/13/11 10:48:44\", \"latitude\": 37.411629, \"messageId\": 15, \"accuracy\":60.0}]";
		String jsonString = null;
		
		try {
			HttpGet httpGet = new HttpGet(getTalkUrl() + "/" + talkMessagesJson);
			HttpResponse response = httpClient.execute(httpGet);
			
			ByteArrayOutputStream ostream = new ByteArrayOutputStream();
			response.getEntity().writeTo(ostream);
	        jsonString = ostream.toString();
		} catch (Exception e) {
			Toast.makeText(contextProvider.get(), "Cannot access Talk Web", Toast.LENGTH_SHORT).show();			
		}
        
		return jsonConverter.deserializeList(jsonString);
	}
	
	private String getTalkUrl()
	{
		return serverRootUrl + "/" + appPath;
	}
	
}
