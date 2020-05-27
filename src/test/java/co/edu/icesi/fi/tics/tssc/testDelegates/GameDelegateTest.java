package co.edu.icesi.fi.tics.tssc.testDelegates;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.delegates.GameDelegate;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;

@RunWith(MockitoJUnitRunner.class)
class GameDelegateTest {

	public static final String URI = "http://localhost:8084/";

	@Mock
	private RestTemplate restTemplate;

	private GameDelegate gameDelegate;

	@BeforeEach
	public void init() {

		gameDelegate = new GameDelegate();
		restTemplate = new RestTemplate();
		gameDelegate.setRestTemplate(restTemplate);

	}

	@Test
	public void testAddGame() {
		TsscGame game1 = new TsscGame();
		game1.setName("Juego 1");
		game1.setNGroups(10);
		game1.setNSprints(2);
		game1.setScheduledTime(LocalTime.now());
		game1.setScheduledDate(LocalDate.now());

		Mockito.when(restTemplate.postForEntity(URI + "api/games/", game1, TsscGame.class).getBody()).thenReturn(game1);

		assertEquals(gameDelegate.addGame(game1), game1);
	}

	@Test
	public void testGetGames() {

		TsscGame game1 = new TsscGame();
		game1.setName("Juego 1");
		game1.setNGroups(10);
		game1.setNSprints(2);
		game1.setScheduledTime(LocalTime.now());
		game1.setScheduledDate(LocalDate.now());

		TsscGame game2 = new TsscGame();
		game2.setName("Juego 2");
		game2.setNGroups(2);
		game2.setNSprints(10);
		game2.setScheduledTime(LocalTime.now());
		game2.setScheduledDate(LocalDate.now());

		TsscGame[] lista = new TsscGame[2];

		lista[0] = game1;
		lista[1] = game2;

		restTemplate.postForEntity(URI + "api/games/", game1, TsscGame.class).getBody();
		gameDelegate.addGame(game1);

		restTemplate.postForEntity(URI + "api/games/", game2, TsscGame.class).getBody();
		gameDelegate.addGame(game2);

		Mockito.when(restTemplate.getForObject(URI + "api/games/", TsscGame[].class)).thenReturn(lista);

		assertEquals(gameDelegate.getGames(), lista);

	}
	
	@Test
	public void testGetGame() {
		TsscGame game1 = new TsscGame();
		game1.setId(1);
		game1.setName("Juego 1");
		game1.setNGroups(10);
		game1.setNSprints(2);
		game1.setScheduledTime(LocalTime.now());
		game1.setScheduledDate(LocalDate.now());
		
		
		restTemplate.postForEntity(URI + "api/games/", game1, TsscGame.class).getBody();
		gameDelegate.addGame(game1);
		
		
		Mockito.when(restTemplate.getForObject(URI+"api/games/1", TsscGame.class)).thenReturn(game1);
		try {
			assertEquals(gameDelegate.getGame(1), game1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
