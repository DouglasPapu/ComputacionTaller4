package co.edu.icesi.fi.tics.tssc.testDelegates;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.BeforeMethod;

import co.edu.icesi.fi.tics.tssc.delegates.ITopicDelegate;
import co.edu.icesi.fi.tics.tssc.delegates.TopicDelegate;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@RunWith(MockitoJUnitRunner.class)
class TopicDelegateTest {
	
	public static final String URI = "http://localhost:8084/";
	
	@Mock
	private RestTemplate restTemplate;
	
	private TopicDelegate topicDelegate;
	
	@BeforeEach
	public void init() {
		topicDelegate = new TopicDelegate();
		restTemplate = new RestTemplate();
		topicDelegate.setRestTemplate(restTemplate);
	}
	
	
	@Test
	public void testAddTopic() {

		TsscTopic topic1 = new TsscTopic();
		topic1.setName("Topic1");
		topic1.setDefaultGroups(8);
		topic1.setDefaultSprints(9);
		topic1.setGroupPrefix("10");
		topic1.setDescription("Soy topic 1");
		
		Mockito.when(restTemplate.postForEntity(URI+"api/topics/", topic1, TsscTopic.class).getBody()).
		thenReturn(topic1);
		
		//ResponseEntity<TsscTopic> response = new ResponseEntity(topic1, HttpStatus.ACCEPTED);
		//Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(ParameterizedTypeReference.class))).thenReturn(response);
		
		assertEquals(topicDelegate.addTopic(topic1), topic1);
	}
	
	@Test
	public void getAllTopics() {
		
		TsscTopic topic1 = new TsscTopic();
		topic1.setName("Topic1");
		topic1.setDefaultGroups(8);
		topic1.setDefaultSprints(9);
		topic1.setGroupPrefix("10");
		topic1.setDescription("Soy topic 1");
		
		TsscTopic topic2 = new TsscTopic();
		topic2.setName("Topic2");
		topic2.setDefaultGroups(1);
		topic2.setDefaultSprints(1);
		topic2.setGroupPrefix("10");
		topic2.setDescription("Soy topic 2");
		
		TsscTopic[] lista = new TsscTopic[2];
		
		lista[0] = topic1;
		lista[1] = topic2;
		
		restTemplate.postForEntity(URI + "api/topics/", topic1, TsscTopic.class).getBody();
		topicDelegate.addTopic(topic1);
		
		restTemplate.postForEntity(URI + "api/topics/", topic2, TsscTopic.class).getBody();
		topicDelegate.addTopic(topic2);
		
		Mockito.when(restTemplate.getForObject(URI+"api/topics/", TsscTopic[].class)).thenReturn(lista);
		//ResponseEntity<TsscTopic> response = new ResponseEntity(topic1, HttpStatus.ACCEPTED);
		//Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(HttpMethod.class), Mockito.any(HttpEntity.class), Mockito.any(ParameterizedTypeReference.class))).thenReturn(response);
		assertEquals(topicDelegate.getTopics(), lista);
		
	}
	
	@Test
	public void getTopic() {
		TsscTopic topic1 = new TsscTopic();
		topic1.setId(1);
		topic1.setName("Topic1");
		topic1.setDefaultGroups(8);
		topic1.setDefaultSprints(9);
		topic1.setGroupPrefix("10");
		topic1.setDescription("Soy topic 1");
		
		restTemplate.postForEntity(URI + "api/topics/", topic1, TsscTopic.class).getBody();
		topicDelegate.addTopic(topic1);
		
		
		Mockito.when(restTemplate.getForObject(URI+"api/topics/1", TsscTopic.class)).thenReturn(topic1);
		try {
			assertEquals(topicDelegate.getTopic(1), topic1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
