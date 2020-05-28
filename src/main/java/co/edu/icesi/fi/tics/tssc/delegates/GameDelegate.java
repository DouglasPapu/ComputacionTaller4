package co.edu.icesi.fi.tics.tssc.delegates;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;
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

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	

}
