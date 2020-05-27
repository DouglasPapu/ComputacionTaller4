package co.edu.icesi.fi.tics.tssc.testDelegates;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.delegates.StoryDelegate;
import co.edu.icesi.fi.tics.tssc.delegates.TopicDelegate;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

@RunWith(MockitoJUnitRunner.class)
class StoryDelegateTest {

	public static final String URI = "http://localhost:8084/";

	@Mock
	private RestTemplate restTemplate;

	private StoryDelegate storyDelegate;

	@BeforeEach
	public void init() {
		storyDelegate = new StoryDelegate();
		restTemplate = new RestTemplate();
		storyDelegate.setRestTemplate(restTemplate);

	}

	@Test
	public void testAddStory() {

		TsscStory story1 = new TsscStory();
		story1.setBusinessValue(new BigDecimal(10));
		story1.setDescription("Descripcion");
		story1.setInitialSprint(new BigDecimal(14));
		story1.setPriority(new BigDecimal(15));
		
		Mockito.when(restTemplate.postForEntity(URI + "api/stories/", story1, TsscStory.class).getBody())
				.thenReturn(story1);

		assertEquals(storyDelegate.addStory(story1), story1);
	}
	
	@Test
	public void getAllStories() {
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
		
		restTemplate.postForEntity(URI + "api/stories/", story1, TsscStory.class).getBody();
		storyDelegate.addStory(story1);
		
		restTemplate.postForEntity(URI + "api/stories/", story2, TsscStory.class).getBody();
		storyDelegate.addStory(story2);
		
		Mockito.when(restTemplate.getForObject(URI+"api/stories/", TsscStory[].class)).thenReturn(lista);
		
		assertEquals(storyDelegate.getStories(), lista);
		
	}
	
	
	@Test
	public void getStory() {
		TsscStory story1 = new TsscStory();
		story1.setId(1);
		story1.setBusinessValue(new BigDecimal(10));
		story1.setDescription("Descripcion");
		story1.setInitialSprint(new BigDecimal(14));
		story1.setPriority(new BigDecimal(15));
		
		restTemplate.postForEntity(URI + "api/stories/", story1, TsscStory.class).getBody();
		storyDelegate.addStory(story1);
		
		
		Mockito.when(restTemplate.getForObject(URI+"api/stories/1", TsscStory.class)).thenReturn(story1);
		try {
			assertEquals(storyDelegate.getStory(1), story1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
