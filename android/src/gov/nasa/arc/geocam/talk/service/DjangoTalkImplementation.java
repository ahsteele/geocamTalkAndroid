package gov.nasa.arc.geocam.talk.service;


import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.http.client.ClientProtocolException;

import roboguice.inject.InjectResource;
import android.content.Context;
import android.util.Log;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.j256.ormlite.dao.Dao;

public class DjangoTalkImplementation implements DjangoTalkInterface{
// TODO: Review as to whether we should be binding a class or an instance of this class
	@Inject DjangoTalkJsonConverterInterface jsonConverter;
	@InjectResource(R.string.url_server_root) String serverRootUrl;
	@InjectResource(R.string.url_relative_app) String appPath;
	@InjectResource(R.string.url_message_list) String talkMessagesJson;
	@Inject protected static Provider<Context> contextProvider;
	@Inject SiteAuthInterface siteAuthImplementation;
	DatabaseHelper databaseHelper;
	Dao<GeoCamTalkMessage, Integer> dao;
	
	
	public DjangoTalkImplementation()
	{
		databaseHelper = new DatabaseHelper(contextProvider.get());
		try {
			dao = databaseHelper.getDao(GeoCamTalkMessage.class);
		} catch (SQLException e) {
			Log.e("Talk.databaseHelper", "dao could not be created: " + e.getMessage());
		}
	}
	
	@Override
	public List<GeoCamTalkMessage> getTalkMessages() throws SQLException, ClientProtocolException, AuthorizationFailedException, IOException {
		List<GeoCamTalkMessage> existingMessages = new ArrayList<GeoCamTalkMessage>();
		
		// let's get our locally saved messages first:
		existingMessages.addAll(dao.queryForAll());
		Log.i("Talk", "dbMessages="+existingMessages.size());
		
		// now let's check the server and add any new messages to the database
		String jsonString = null;
		
		jsonString = siteAuthImplementation.get(talkMessagesJson, null);
		List<GeoCamTalkMessage> newMessages =  jsonConverter.deserializeList(jsonString);
		Log.i("Talk", "new Messages="+newMessages.size());
		
		addMessagesToDatabase(newMessages, existingMessages);
		
		
		SortedSet<GeoCamTalkMessage> hash = new TreeSet<GeoCamTalkMessage>(Collections.reverseOrder());
		
		hash.addAll(existingMessages);
		hash.addAll(newMessages);
		
		List<GeoCamTalkMessage> finalList = new ArrayList(hash);
		Log.i("Talk", "final Messages="+finalList.size());
		
		return finalList;
	}
	
	@Override
	public void setAuth(String username, String password) {
		siteAuthImplementation.setAuth(username, password);		
	}
	
	private void addMessagesToDatabase(
			List<GeoCamTalkMessage> newMessages,
			List<GeoCamTalkMessage> existingMessages
	) throws SQLException
	{
		for(GeoCamTalkMessage m:newMessages)
		{
			if(!existingMessages.contains(m))
			dao.create(m);
			
			// TODO: maybe try dao.create in a try catch with dao.update on catch
		}
	}
}
