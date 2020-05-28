package co.edu.icesi.fi.tics.tssc.testDelegates;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@RunWith(MockitoJUnitRunner.class)
class TopicDelegateTest {

	public static final String URI = "http://localhost:8084/";

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private TopicDelegate topicDelegate;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddTopic() {
		MockitoAnnotations.initMocks(this);
		TsscTopic topic1 = new TsscTopic();
		topic1.setName("Topic1");
		topic1.setDefaultGroups(8);
		topic1.setDefaultSprints(9);
		topic1.setGroupPrefix("10");
		topic1.setDescription("Soy topic 1");

		Mockito.when(restTemplate.postForEntity(URI + "api/topics/", topic1, TsscTopic.class))
				.thenReturn(new ResponseEntity<TsscTopic>(topic1, HttpStatus.ACCEPTED));

		// ResponseEntity<TsscTopic> response = new ResponseEntity(topic1,
		// HttpStatus.ACCEPTED);
		// Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(HttpMethod.class),
		// Mockito.any(HttpEntity.class),
		// Mockito.any(ParameterizedTypeReference.class))).thenReturn(response);

		assertEquals(topicDelegate.addTopic(topic1).getName(), topic1.getName());
	}

	@Test
	public void getAllTopics() {
		MockitoAnnotations.initMocks(this);
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

		Mockito.when(restTemplate.postForEntity(URI + "api/topics/", topic1, TsscTopic.class))
				.thenReturn(new ResponseEntity<TsscTopic>(topic1, HttpStatus.ACCEPTED));
		topicDelegate.addTopic(topic1);

		Mockito.when(restTemplate.postForEntity(URI + "api/topics/", topic2, TsscTopic.class))
				.thenReturn(new ResponseEntity<TsscTopic>(topic2, HttpStatus.ACCEPTED));
		topicDelegate.addTopic(topic2);

		Mockito.when(restTemplate.getForObject(URI + "api/topics/", TsscTopic[].class))
				.thenReturn(new ResponseEntity<TsscTopic[]>(lista, HttpStatus.ACCEPTED).getBody());
		// ResponseEntity<TsscTopic> response = new ResponseEntity(topic1,
		// HttpStatus.ACCEPTED);
		// Mockito.when(restTemplate.exchange(Mockito.anyString(),Mockito.any(HttpMethod.class),
		// Mockito.any(HttpEntity.class),
		// Mockito.any(ParameterizedTypeReference.class))).thenReturn(response);
		Iterable<TsscTopic> lista2 = topicDelegate.getTopics();

		int contador = 0;
		for (TsscTopic tsscTopic : lista2) {

			assertEquals(tsscTopic.getName(), lista[contador].getName());
			contador++;

		}

	}

	@Test
	public void getTopic() {
		MockitoAnnotations.initMocks(this);
		TsscTopic topic1 = new TsscTopic();
		topic1.setId(1);
		topic1.setName("Topic1");
		topic1.setDefaultGroups(8);
		topic1.setDefaultSprints(9);
		topic1.setGroupPrefix("10");
		topic1.setDescription("Soy topic 1");

		Mockito.when(restTemplate.postForEntity(URI + "api/topics/", topic1, TsscTopic.class))
				.thenReturn(new ResponseEntity<TsscTopic>(topic1, HttpStatus.ACCEPTED));
		topicDelegate.addTopic(topic1);

		Mockito.when(restTemplate.getForObject(URI + "api/topics/1", TsscTopic.class))
				.thenReturn(new ResponseEntity<TsscTopic>(topic1, HttpStatus.OK).getBody());

		try {
			assertEquals(topicDelegate.getTopic(1).getName(), topic1.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}

	}
	
	@Test
	public void borrarTest() {
		
		MockitoAnnotations.initMocks(this);
		TsscTopic topic1 = new TsscTopic();
		topic1.setId(1);
		topic1.setName("Topic1");
		topic1.setDefaultGroups(8);
		topic1.setDefaultSprints(9);
		topic1.setGroupPrefix("10");
		topic1.setDescription("Soy topic 1");

		Mockito.when(restTemplate.postForEntity(URI + "api/topics/", topic1, TsscTopic.class))
				.thenReturn(new ResponseEntity<TsscTopic>(topic1, HttpStatus.ACCEPTED));
		topicDelegate.addTopic(topic1);
		
		Mockito.doNothing().when(restTemplate).delete(URI+"api/topics/1");
		topicDelegate.deleteTopic(topic1);
		
		Mockito.when(restTemplate.getForObject(URI + "api/topics/1", null))
		.thenReturn(new ResponseEntity(null, HttpStatus.OK).getBody());
		
		try {
			assertNull(topicDelegate.getTopic(1));
		} catch (Exception e) {
		}
	}
	
	
	@Test
	public void editTest() {
		MockitoAnnotations.initMocks(this);
		TsscTopic topic1 = new TsscTopic();
		topic1.setId(1);
		topic1.setName("Topic1");
		topic1.setDefaultGroups(8);
		topic1.setDefaultSprints(9);
		topic1.setGroupPrefix("10");
		topic1.setDescription("Soy topic 1");

		Mockito.when(restTemplate.postForEntity(URI + "api/topics/", topic1, TsscTopic.class))
				.thenReturn(new ResponseEntity<TsscTopic>(topic1, HttpStatus.ACCEPTED));
		topicDelegate.addTopic(topic1);
		
		topic1.setName("Topic Editado");
		
		Mockito.when(restTemplate.patchForObject(URI + "api/topics/", topic1, TsscTopic.class))
		.thenReturn(new ResponseEntity<TsscTopic>(topic1, HttpStatus.ACCEPTED).getBody());
		
		topicDelegate.editTopic(topic1);
		
		Mockito.when(restTemplate.getForObject(URI + "api/topics/1", TsscTopic.class))
		.thenReturn(new ResponseEntity<TsscTopic>(topic1, HttpStatus.OK).getBody());
		
		try {
			assertEquals(topicDelegate.getTopic(1).getName(), topic1.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		
	}
	
	

}
