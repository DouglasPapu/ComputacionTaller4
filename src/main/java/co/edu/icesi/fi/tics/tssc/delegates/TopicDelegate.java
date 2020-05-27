package co.edu.icesi.fi.tics.tssc.delegates;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@Component
public class TopicDelegate implements ITopicDelegate{
	
	public final static String SERVER = "http://localhost:8084/";
	RestTemplate restTemplate;
	
	public TopicDelegate() {
		restTemplate = new RestTemplate();
	}
	
	@Override
	public TsscTopic getTopic(long id) throws Exception{
		TsscTopic topic = restTemplate.getForObject(SERVER+ "api/topics/" +id, TsscTopic.class);
		if(topic==null) {
			throw new Exception("topic is null");
		}
		return topic;
	}
	
	@Override
	public Iterable<TsscTopic> getTopics() {
		TsscTopic[] topics = restTemplate.getForObject(SERVER+ "api/topics/", TsscTopic[].class);
		List<TsscTopic> at;
		try {
			at = Arrays.asList(topics);
			return at;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public TsscTopic addTopic(TsscTopic tsscTopic) {
		
	
		ResponseEntity<TsscTopic> rs = restTemplate.postForEntity(SERVER + "api/topics/", tsscTopic, TsscTopic.class);
		
		
		TsscTopic topic = rs.getBody();
		
		if (topic == null) {
			throw new IllegalArgumentException("topic is null");
		}
		return topic;
	}
	
	@Override
	public void editTopic(TsscTopic editado) {

		restTemplate.put(SERVER + "api/topics-edit/", editado, TsscTopic.class);
		
	}
	
	
	
	@Override
	public void deleteTopic(TsscTopic topic) {
		if (topic == null) {
			throw new IllegalArgumentException("topic is null");
		}
		restTemplate.delete(SERVER + "api/topics/" +topic.getId());
		
	}

}
