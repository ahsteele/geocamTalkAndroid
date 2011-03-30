package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.util.List;

public interface DjangoTalkJsonConverterInterface {
	List<GeoCamTalkMessage> deserializeList(String jsonString);
	GeoCamTalkMessage deserialize(String jsonString);
}
