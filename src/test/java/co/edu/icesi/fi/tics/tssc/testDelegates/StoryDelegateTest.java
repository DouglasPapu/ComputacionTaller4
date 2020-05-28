package co.edu.icesi.fi.tics.tssc.testDelegates;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.delegates.StoryDelegate;
import co.edu.icesi.fi.tics.tssc.delegates.TopicDelegate;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

@RunWith(MockitoJUnitRunner.class)
class StoryDelegateTest {

	public static final String URI = "http://localhost:8084/";

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private StoryDelegate storyDelegate;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testAddStory() {
		MockitoAnnotations.initMocks(this);
		TsscStory story1 = new TsscStory();
		story1.setBusinessValue(new BigDecimal(10));
		story1.setDescription("Descripcion");
		story1.setInitialSprint(new BigDecimal(14));
		story1.setPriority(new BigDecimal(15));
		
		Mockito.when(restTemplate.postForEntity(URI + "api/stories/", story1, TsscStory.class))
				.thenReturn(new ResponseEntity<>(story1,HttpStatus.OK));

		assertEquals(storyDelegate.addStory(story1), story1);
	}
	
	@Test
	public void getAllStories() {
		MockitoAnnotations.initMocks(this);
		TsscStory story1 = new TsscStory();
		story1.setBusinessValue(new BigDecimal(10));
		story1.setDescription("Descripcion");
		story1.setInitialSprint(new BigDecimal(14));
		story1.setPriority(new BigDecimal(15));
		
		TsscStory story2 = new TsscStory();
		story2.setBusinessValue(new BigDecimal(7));
		story2.setDescription("Descripcion 2");
		story2.setInitialSprint(new BigDecimal(7));
		story2.setPriority(new BigDecimal(7));
		
		TsscStory[] lista = new TsscStory[2];
		
		lista[0] = story1;
		lista[1] = story2;
		
		Mockito.when(restTemplate.postForEntity(URI + "api/stories/", story1, TsscStory.class))
		.thenReturn(new ResponseEntity<>(story1,HttpStatus.OK));		
		storyDelegate.addStory(story1);
		
		Mockito.when(restTemplate.postForEntity(URI + "api/stories/", story2, TsscStory.class))
		.thenReturn(new ResponseEntity<>(story2,HttpStatus.OK));
		storyDelegate.addStory(story2);
		
		Mockito.when(restTemplate.getForObject(URI+"api/stories/", TsscStory[].class)).
		thenReturn(new ResponseEntity<TsscStory[]>(lista, HttpStatus.ACCEPTED).getBody());
		
		Iterable<TsscStory> lista2 = storyDelegate.getStories();
		
		int contador = 0;
		for (TsscStory tsscStory : lista2) {
			
			assertEquals(tsscStory.getDescription(), lista[contador].getDescription());
			contador++;
			
		}
		
	}
	
	
	@Test
	public void getStory() {
		MockitoAnnotations.initMocks(this);
		TsscStory story1 = new TsscStory();
		story1.setBusinessValue(new BigDecimal(10));
		story1.setDescription("Descripcion");
		story1.setInitialSprint(new BigDecimal(14));
		story1.setPriority(new BigDecimal(15));
		

		Mockito.when(restTemplate.postForEntity(URI + "api/stories/", story1, TsscStory.class))
		.thenReturn(new ResponseEntity<>(story1,HttpStatus.OK));		
		storyDelegate.addStory(story1);
		
		Mockito.when(restTemplate.getForObject(URI+"api/stories/1", TsscStory.class))
		.thenReturn(new ResponseEntity<TsscStory>(story1,HttpStatus.OK).getBody());
		try {
			assertEquals(storyDelegate.getStory(1).getDescription(), story1.getDescription());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
		
	}
	

	@Test
	public void borrarTest() {
		
		MockitoAnnotations.initMocks(this);
		TsscStory story1 = new TsscStory();
		story1.setBusinessValue(new BigDecimal(10));
		story1.setDescription("Descripcion");
		story1.setInitialSprint(new BigDecimal(14));
		story1.setPriority(new BigDecimal(15));
		

		Mockito.when(restTemplate.postForEntity(URI + "api/stories/", story1, TsscStory.class))
		.thenReturn(new ResponseEntity<>(story1,HttpStatus.OK));		
		storyDelegate.addStory(story1);
		
		Mockito.doNothing().when(restTemplate).delete(URI+"api/stories/1");
		storyDelegate.deleteStory(story1);
		
		Mockito.when(restTemplate.getForObject(URI + "api/stories/1", null))
		.thenReturn(new ResponseEntity(null, HttpStatus.OK).getBody());
		
		try {
			assertNull(storyDelegate.getStory(1));
		} catch (Exception e) {

		}
	}
	
	
	@Test
	public void editTest() {
		MockitoAnnotations.initMocks(this);
		MockitoAnnotations.initMocks(this);
		TsscStory story1 = new TsscStory();
		story1.setBusinessValue(new BigDecimal(10));
		story1.setDescription("Descripcion");
		story1.setInitialSprint(new BigDecimal(14));
		story1.setPriority(new BigDecimal(15));
		

		Mockito.when(restTemplate.postForEntity(URI + "api/stories/", story1, TsscStory.class))
		.thenReturn(new ResponseEntity<>(story1,HttpStatus.OK));		
		storyDelegate.addStory(story1);
		
		story1.setDescription("Story Editado");
		
		Mockito.when(restTemplate.patchForObject(URI + "api/stories/", story1, TsscStory.class))
		.thenReturn(new ResponseEntity<TsscStory>(story1, HttpStatus.ACCEPTED).getBody());
		
		storyDelegate.editStory(story1);
		
		Mockito.when(restTemplate.getForObject(URI + "api/stories/1", TsscStory.class))
		.thenReturn(new ResponseEntity<TsscStory>(story1, HttpStatus.OK).getBody());
		
		try {
			assertEquals(storyDelegate.getStory(1).getDescription(), story1.getDescription());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		
	}
	
	

}
