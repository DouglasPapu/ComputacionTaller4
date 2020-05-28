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

import co.edu.icesi.fi.tics.tssc.delegates.GameDelegate;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;

@RunWith(MockitoJUnitRunner.class)
class GameDelegateTest {

	public static final String URI = "http://localhost:8084/";

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private GameDelegate gameDelegate;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testAddGame() {
		MockitoAnnotations.initMocks(this);
		TsscGame game1 = new TsscGame();
		game1.setName("Juego 1");
		game1.setNGroups(10);
		game1.setNSprints(2);
		game1.setScheduledTime(LocalTime.now());
		game1.setScheduledDate(LocalDate.now());

		Mockito.when(restTemplate.postForEntity(URI + "api/games/", game1, TsscGame.class))
		.thenReturn(new ResponseEntity<TsscGame>(game1, HttpStatus.ACCEPTED));

		assertEquals(gameDelegate.addGame(game1), game1);
	}

	@Test
	public void testGetGames() {
		MockitoAnnotations.initMocks(this);
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

		Mockito.when(restTemplate.postForEntity(URI + "api/games/", game1, TsscGame.class))
		.thenReturn(new ResponseEntity<TsscGame>(game1, HttpStatus.ACCEPTED));
		gameDelegate.addGame(game1);

		Mockito.when(restTemplate.postForEntity(URI + "api/games/", game2, TsscGame.class))
		.thenReturn(new ResponseEntity<TsscGame>(game2, HttpStatus.ACCEPTED));
		gameDelegate.addGame(game2);

		Mockito.when(restTemplate.getForObject(URI + "api/games/", TsscGame[].class))
		.thenReturn(new ResponseEntity<TsscGame[]>(lista, HttpStatus.ACCEPTED).getBody());

		Iterable<TsscGame> lista2 = gameDelegate.getGames();
		
		int contador = 0;
		for (TsscGame tsscGame : lista2) {
			
			assertEquals(tsscGame.getName(), lista[contador].getName());
			contador++;
			
		}

	}
	
	@Test
	public void testGetGame() {
		MockitoAnnotations.initMocks(this);
		TsscGame game1 = new TsscGame();
		game1.setId(1);
		game1.setName("Juego 1");
		game1.setNGroups(10);
		game1.setNSprints(2);
		game1.setScheduledTime(LocalTime.now());
		game1.setScheduledDate(LocalDate.now());
		
		
		Mockito.when(restTemplate.postForEntity(URI + "api/games/", game1, TsscGame.class))
		.thenReturn(new ResponseEntity<TsscGame>(game1, HttpStatus.ACCEPTED));
		gameDelegate.addGame(game1);
		
		Mockito.when(restTemplate.getForObject(URI+"api/games/1", TsscGame.class))
		.thenReturn(new ResponseEntity<TsscGame>(game1,HttpStatus.OK).getBody());
		try {
			assertEquals(gameDelegate.getGame(1).getName(), game1.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		
	}
	
	@Test
	public void borrarTest() {
		
		MockitoAnnotations.initMocks(this);
		TsscGame game1 = new TsscGame();
		game1.setId(1);
		game1.setName("Juego 1");
		game1.setNGroups(10);
		game1.setNSprints(2);
		game1.setScheduledTime(LocalTime.now());
		game1.setScheduledDate(LocalDate.now());
		
		
		Mockito.when(restTemplate.postForEntity(URI + "api/games/", game1, TsscGame.class))
		.thenReturn(new ResponseEntity<TsscGame>(game1, HttpStatus.ACCEPTED));
		gameDelegate.addGame(game1);
		
		Mockito.doNothing().when(restTemplate).delete(URI+"api/games/1");
		gameDelegate.deleteGame(game1);
		
		Mockito.when(restTemplate.getForObject(URI + "api/games/1", null))
		.thenReturn(new ResponseEntity(null, HttpStatus.OK).getBody());
		
		try {
			assertNull(gameDelegate.getGame(1));
		} catch (Exception e) {
		}
	}
	
	
	@Test
	public void editTest() {
		MockitoAnnotations.initMocks(this);
		MockitoAnnotations.initMocks(this);
		TsscGame game1 = new TsscGame();
		game1.setId(1);
		game1.setName("Juego 1");
		game1.setNGroups(10);
		game1.setNSprints(2);
		game1.setScheduledTime(LocalTime.now());
		game1.setScheduledDate(LocalDate.now());
		
		
		Mockito.when(restTemplate.postForEntity(URI + "api/games/", game1, TsscGame.class))
		.thenReturn(new ResponseEntity<TsscGame>(game1, HttpStatus.ACCEPTED));
		gameDelegate.addGame(game1);
		
		game1.setName("Game Editado");
		
		Mockito.when(restTemplate.patchForObject(URI + "api/games/", game1, TsscGame.class))
		.thenReturn(new ResponseEntity<TsscGame>(game1, HttpStatus.ACCEPTED).getBody());
		
		gameDelegate.editGame(game1);
		
		Mockito.when(restTemplate.getForObject(URI + "api/games/1", TsscGame.class))
		.thenReturn(new ResponseEntity<TsscGame>(game1, HttpStatus.OK).getBody());
		
		try {
			assertEquals(gameDelegate.getGame(1).getName(), game1.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fail();
		}
		
	}
	

}
