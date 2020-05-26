package co.edu.icesi.fi.tics.tssc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.fi.tics.tssc.exceptions.CapacityException;
import co.edu.icesi.fi.tics.tssc.exceptions.GameException;
import co.edu.icesi.fi.tics.tssc.exceptions.SpringException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.services.IGameDelegate;

@RestController
public class GameRestController implements IGameRestController{

	private IGameDelegate gameDelegate;

	@Autowired
	public GameRestController(IGameDelegate gameDelegate) {
		// TODO Auto-generated constructor stub
		this.gameDelegate = gameDelegate;
	}

	@PostMapping("/api/games/")
	public TsscGame saveGame(TsscGame tsscGame) {

		
		System.out.println("ESTOY EN REST "+tsscGame.getName());
		System.out.println("ESTOY EN REST "+tsscGame.getTsscTopic());
		try {
			if (tsscGame.getTsscTopic() == null) {

				return gameDelegate.saveGame(tsscGame);

			} else {

				return gameDelegate.saveGameWithTopic(tsscGame, tsscGame.getTsscTopic().getId());
			}

		} catch (GameException | CapacityException | SpringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	@PostMapping("/api/games/{id}")
	public TsscGame editGame(@PathVariable("id") long id) {

		try {
			return gameDelegate.editGame(gameDelegate.findById(id).get());
		} catch (GameException | CapacityException | SpringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@GetMapping("/api/games/")
	public Iterable<TsscGame> findAll() {
		return gameDelegate.findAll();

	}

	@GetMapping("/api/games/{id}")
	public TsscGame findById(@PathVariable("id") long id) {
		return gameDelegate.findById(id).get();
	}

	@DeleteMapping("/api/games/{id}")
	public void deleteGame(@PathVariable("id") long id) {
		gameDelegate.delete(gameDelegate.findById(id).get());
	}

}
