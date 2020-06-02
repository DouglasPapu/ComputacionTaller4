package co.edu.icesi.fi.tics.tssc.delegates;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@Component
public class GameDelegate implements IGameDelegate {

	public final static String SERVER = "http://localhost:8084/";
	RestTemplate restTemplate;

	public GameDelegate() {
		restTemplate = new RestTemplate();
	}

	@Override
	public TsscGame getGame(long id) throws Exception {
		TsscGame game = restTemplate.getForObject(SERVER + "api/games/" + id, TsscGame.class);
		if (game == null) {
			throw new Exception("game is null");
		}

		System.out.println("ENCONTRE A " + game.getName());
		return game;
	}

	@Override
	public Iterable<TsscGame> getGames() {
		TsscGame[] games = restTemplate.getForObject(SERVER + "api/games/", TsscGame[].class);
		List<TsscGame> at;
		try {
			at = Arrays.asList(games);
			return at;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public TsscGame addGame(TsscGame tsscGame) {

		ResponseEntity<TsscGame> rs = restTemplate.postForEntity(SERVER + "api/games/", tsscGame, TsscGame.class);
		TsscGame game = rs.getBody();

		if (game == null) {
			throw new IllegalArgumentException("Game is null");
		}
		return game;
	}

	@Override
	public void editGame(TsscGame editado) {
		restTemplate.put(SERVER + "api/games-edit/", editado, TsscGame.class);
	}

	@Override
	public void deleteGame(TsscGame game) {
		if (game == null) {
			throw new IllegalArgumentException("Game is null");
		}
		restTemplate.delete(SERVER + "api/games/" + game.getId());

	}
	
	@Override
	public Iterable<TsscGame> getScheduledGames(LocalDate initial, LocalDate finald) {
		TsscGame[] games = restTemplate.getForObject(SERVER + "api/games-date/" +initial+"/"+finald, TsscGame[].class);
		List<TsscGame> at;
		try {
			at = Arrays.asList(games);
			return at;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	} 
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<TsscTopic> getTopicsByGameDate(LocalDate date) {
		Object[] topics= restTemplate.getForObject(SERVER + "api/topics-date/" +date,Object[].class);
		
		List<TsscTopic> all = new ArrayList<TsscTopic>();

		List<Object> at;
		at = Arrays.asList(topics);
		ArrayList object=null;
		for(int i=0;i<at.size();i++) {
			object=(ArrayList)at.get(i);
			LinkedHashMap<Object,Object> test1 = (LinkedHashMap<Object, Object>) object.get(0);
			
			int value=0;
			for( Map.Entry<Object,Object>  entry : test1.entrySet()){
				if(entry.getKey().toString().equals("id")) {
					
					value= Integer.parseInt(entry.getValue().toString());
				}
				   
				   
			}
			
			TsscTopic topic = restTemplate.getForObject(SERVER+ "api/topics/" +value, TsscTopic.class);
			
			all.add(topic);
			
		}
		
		return all;

	} 
	
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Integer> getNumberOfGamesByTopic(LocalDate date) {
		Object[] topics= restTemplate.getForObject(SERVER + "api/topics-date/" +date,Object[].class);
		List<Integer> all = new ArrayList<Integer>();
		
		
		List<Object> at;
		at = Arrays.asList(topics);
		ArrayList object=null;
		for(int i=0;i<at.size();i++) {
			object=(ArrayList)at.get(i);
			int games = (int)object.get(1);
			all.add(games);
		}
		
		return all;

	} 
	
	@Override
	public TsscStory addStoryByGame(long id,TsscStory tsscStory) {
		System.out.println("delegate "+tsscStory.getDescription());
		ResponseEntity<TsscStory> rs = restTemplate.postForEntity(SERVER + "api/games/"+id+"/stories/add", tsscStory, TsscStory.class);
		TsscStory story = rs.getBody();
		System.out.println("delegate "+story.getDescription());
		

		if (story == null) {
			throw new IllegalArgumentException("Story is null");
		}
		return story;
	}
	
	@Override
	public Iterable<TsscStory> getStoriesByGame(long id) {
		TsscStory[] stories = restTemplate.getForObject(SERVER + "api/games/"+id+"/stories", TsscStory[].class);
		List<TsscStory> at;
		try {
			at = Arrays.asList(stories);
			return at;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	} 

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	

}
