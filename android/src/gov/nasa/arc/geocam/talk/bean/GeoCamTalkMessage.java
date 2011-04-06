package gov.nasa.arc.geocam.talk.bean;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "talkMessages")
public class GeoCamTalkMessage implements Comparable<GeoCamTalkMessage> {

	@DatabaseField(index = true)
	private int 		messageId;
	@DatabaseField
	private Integer  	authorId;
	@DatabaseField
	private String 		authorUsername;
	@DatabaseField
	private String		authorFullname;
	@DatabaseField
	private String 		content;
	@DatabaseField
	private Date 		contentTimestamp;
	@DatabaseField
	private Double 		latitude;
	@DatabaseField
	private Double 		longitude;
	@DatabaseField
	private Integer 	accuracy;
	@DatabaseField
	private boolean		hasGeolocation;
	@DatabaseField(dataType=DataType.BYTE_ARRAY)
	private byte[]		audio;
	
	public int getMessageId() {
		return messageId;
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public String getAuthorUsername() {
		return authorUsername;
	}
	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}
	public String getContent() {
		return content;
	}
	public String getAuthorFullname() {
		return authorFullname;
	}
	public void setAuthorFullname(String authorFullname) {
		this.authorFullname = authorFullname;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getContentTimestamp() {
		return contentTimestamp;
	}
	public void setContentTimestamp(Date contentTimestamp) {
		this.contentTimestamp = contentTimestamp;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Integer getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public boolean hasGeolocation() {
		return hasGeolocation;
	}
	public void setHasGeolocation(boolean hasGeolocation) {
		this.hasGeolocation = hasGeolocation;
	}


	
	@Override
	public boolean equals(Object o) {
		GeoCamTalkMessage other = (GeoCamTalkMessage)o;
		
		return 
		this.messageId == other.messageId && 	
		equalOrBothNull(authorId, other.authorId) &&
		equalOrBothNull(authorUsername, other.authorUsername) &&
		equalOrBothNull(authorFullname, other.authorFullname) &&
		equalOrBothNull(content, other.content) &&
		equalOrBothNull(contentTimestamp, other.contentTimestamp) &&
		equalOrBothNull(latitude, other.latitude) &&
		equalOrBothNull(longitude, other.longitude) &&
		equalOrBothNull(accuracy, other.accuracy) &&
		this.hasGeolocation == other.hasGeolocation;
	}
	
	@Override
	public int hashCode() {
		return this.messageId;		
	}
	
	
	
	// TODO: Revisit this if we need other helper methods. Maybe move to global helper function?
	// Jakarta Commons library may provide some additional methods that would be useful
	public static boolean equalOrBothNull(Object a, Object b)
	{
		if(a != null && b != null)
		{
			return a.equals(b);
		}
		else if(a == null && b == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public byte[] getAudio() {
		return audio;
	}
	public void setAudio(byte[] audio) {
		this.audio = audio;
	}
	@Override
	public int compareTo(GeoCamTalkMessage another) {
		if(this.equals(another)) {
			return 0;
		} else if(this.contentTimestamp == null) {
			return -1;
		} else if(another.getContentTimestamp() == null) {
			return 1;
		} else if(this.contentTimestamp.after(another.getContentTimestamp())) {
			return 1;
		} else {
			return -1;
		}
	}
}
