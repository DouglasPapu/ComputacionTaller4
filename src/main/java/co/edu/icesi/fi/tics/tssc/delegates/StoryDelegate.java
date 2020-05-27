package co.edu.icesi.fi.tics.tssc.delegates;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

@Component
public class StoryDelegate implements IStoryDelegate{
	
	public final static String SERVER = "http://localhost:8084/";
	RestTemplate restTemplate;
	public StoryDelegate() {
		restTemplate = new RestTemplate();
	}
	@Override
	public TsscStory getStory(long id) throws Exception{
		// TODO Auto-generated method stub
		TsscStory story = restTemplate.getForObject(SERVER+ "api/stories/" +id, TsscStory.class);
		if(story==null) {
			throw new Exception("Story is null");
		}
		return story;
	}
	@Override
	public Iterable<TsscStory> getStories() {
		TsscStory[] stories = restTemplate.getForObject(SERVER+ "api/stories/", TsscStory[].class);
		List<TsscStory> at;
		try {
			at = Arrays.asList(stories);
			return at;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public TsscStory addStory(TsscStory newStory) {
		TsscStory story = restTemplate.postForEntity(SERVER + "api/stories/", newStory, TsscStory.class).getBody();
		if (story == null) {
			throw new IllegalArgumentException("story is null");
		}
		return story;
	}
	
	@Override
	public void editStory(TsscStory editado) {
		restTemplate.put(SERVER + "api/stories-edit/", editado, TsscStory.class);		
	}
	
	
	
	@Override
	public void deleteStory(TsscStory story) {
		if (story == null) {
			throw new IllegalArgumentException("story is null");
		}
		restTemplate.delete(SERVER + "api/stories/" +story.getId());
		
	}

}
