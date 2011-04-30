// __BEGIN_LICENSE__
// Copyright (C) 2008-2010 United States Government as represented by
// the Administrator of the National Aeronautics and Space Administration.
// All Rights Reserved.
// __END_LICENSE__

package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.UIUtils;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;
import gov.nasa.arc.geocam.talk.service.IAudioPlayer;
import gov.nasa.arc.geocam.talk.service.ISiteAuth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import roboguice.adapter.IterableAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoCamTalkMessageAdapter.
 */
public class GeoCamTalkMessageAdapter extends IterableAdapter<GeoCamTalkMessage> {

	/** The m inflater. */
	@Inject
	LayoutInflater mInflater;

	/** The player. */
	@Inject
	IAudioPlayer player;
	
	/** The site auth. */
	@Inject
	ISiteAuth siteAuth;
	
	/**
	 * Instantiates a new geo cam talk message adapter.
	 *
	 * @param context the context
	 */
	@Inject
	public GeoCamTalkMessageAdapter(Context context) {
		super(context, R.layout.list_item);
	}

	/**
	 * Sets the talk messages.
	 *
	 * @param talkMessages the new talk messages
	 */
	public void setTalkMessages(List<GeoCamTalkMessage> talkMessages) {
		this.clear();
		for (GeoCamTalkMessage m : talkMessages) {
			add(m);
		}
	}

	/**
	 * Gets the talk message.
	 *
	 * @param position the position
	 * @return the talk message
	 */
	public GeoCamTalkMessage getTalkMessage(int position) {
		GeoCamTalkMessage msg = getItem(position);
		return msg;
	}

	/* (non-Javadoc)
	 * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row;

		if (null == convertView) {
			row = mInflater.inflate(R.layout.list_item, null);
		} else {
			row = convertView;
		}

		TextView contentTextView = (TextView) row.findViewById(R.id.content);
		TextView fullnameTextView = (TextView) row.findViewById(R.id.fullname);
		TextView contentTimestampTextView = (TextView) row.findViewById(R.id.content_timestamp);
		ImageView geolocationImageView = (ImageView) row.findViewById(R.id.hasGeoLocation);
		ImageButton audioImageButton = (ImageButton) row.findViewById(R.id.hasAudio);
		
		audioImageButton.setSelected(false);                                                                                                                                                   
		audioImageButton.setFocusable(false);
								
		GeoCamTalkMessage msg = getItem(position);

		contentTextView.setText(msg.getContent());
		fullnameTextView.setText(msg.getAuthorFullname());
		
		Date contentTimestamp = msg.getContentTimestampDate();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		contentTimestampTextView.setText(sdf.format(contentTimestamp));

		if (msg.hasGeolocation()) {
			geolocationImageView.setVisibility(View.VISIBLE);
		} else {
			geolocationImageView.setVisibility(View.INVISIBLE);
		}
		if (msg.hasAudio()) {
			audioImageButton.setVisibility(View.VISIBLE);
		} else {
			audioImageButton.setVisibility(View.GONE);
		}
		audioImageButton.setTag(msg);
		
		row.setTag(msg);
		row.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				GeoCamTalkMessage msg = (GeoCamTalkMessage)v.getTag();				
				if (msg.hasGeolocation()) {
					UIUtils.showMapView(v.getContext(), msg);
				}				
			}			
		});
		
		return row;
	}
}