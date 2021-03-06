package co.edu.icesi.fi.tics.tssc.controller;

import java.time.LocalDate;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.fi.tics.tssc.exceptions.CapacityException;
import co.edu.icesi.fi.tics.tssc.exceptions.SpringException;
import co.edu.icesi.fi.tics.tssc.exceptions.TopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;
import co.edu.icesi.fi.tics.tssc.delegates.IGameDelegate;
import co.edu.icesi.fi.tics.tssc.delegates.IStoryDelegate;
import co.edu.icesi.fi.tics.tssc.delegates.ITopicDelegate;
import co.icesi.fi.tics.tssc.validations.ValidationGame;
import co.icesi.fi.tics.tssc.validations.ValidationStory;
import co.icesi.fi.tics.tssc.validations.ValidationTopic;

@Controller
public class GameController {

	private IGameDelegate gameDelegate;

	private ITopicDelegate topicDelegate;
	
	private IStoryDelegate storyDelegate;
	

	@Autowired
	public GameController(IGameDelegate gameService, ITopicDelegate topicDelegate, IStoryDelegate storyDelegate) {
		this.gameDelegate = gameService;
		this.topicDelegate = topicDelegate;
		this.storyDelegate = storyDelegate;
	}

	@GetMapping("/game/")
	public String indexGame(Model model) {
		model.addAttribute("games", gameDelegate.getGames());
		return "game/index";
	}

	@GetMapping("/game/add")
	public String addGame(Model model) {
		model.addAttribute("tsscGame", new TsscGame());
		model.addAttribute("topics", topicDelegate.getTopics());
		return "game/add-game";
	}

	@PostMapping("/game/add")
	public String saveGame(@Validated(ValidationGame.class) TsscGame tsscGame, BindingResult bindingResult,
			@RequestParam(value = "action", required = true) String action, Model model) {

		if (!action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {

				model.addAttribute("name", tsscGame.getName());
				model.addAttribute("adminPassword", tsscGame.getAdminPassword());
				model.addAttribute("scheduledDate", tsscGame.getScheduledDate());
				model.addAttribute("scheduledTime", tsscGame.getScheduledTime());
				model.addAttribute("nGroups", tsscGame.getNGroups());
				model.addAttribute("nSprints", tsscGame.getNSprints());
				model.addAttribute("userPassword", tsscGame.getUserPassword());
				model.addAttribute("guestPassword", tsscGame.getGuestPassword());
				model.addAttribute("topics", topicDelegate.getTopics());

				return "game/add-game";
			} else {

				try {

					if (tsscGame.getTsscTopic() == null) {

						
						//System.out.println("Soy el juego "+ tsscGame.getName());
						gameDelegate.addGame(tsscGame);

					} else {
						//System.out.println("Soy el juego "+ tsscGame.getName());
						gameDelegate.addGame(tsscGame);
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return "redirect:/game/";
			}
		} else {

			model.addAttribute("games", gameDelegate.getGames());
			return "game/index";
		}

	}

	// ----Fin de guardar Juego -----

	@GetMapping("/game/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Optional<TsscGame> tsscGame = null;
		try {
			tsscGame = Optional.of(gameDelegate.getGame(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (tsscGame == null)
			throw new IllegalArgumentException("Invalid game Id:" + id);

		model.addAttribute("tsscGame", tsscGame.get());
		model.addAttribute("name", tsscGame.get().getName());
		model.addAttribute("adminPassword", tsscGame.get().getAdminPassword());
		model.addAttribute("scheduledDate", tsscGame.get().getScheduledDate());
		model.addAttribute("scheduledTime", tsscGame.get().getScheduledTime());
		model.addAttribute("nGroups", tsscGame.get().getNGroups());
		model.addAttribute("nSprints", tsscGame.get().getNSprints());
		model.addAttribute("userPassword", tsscGame.get().getUserPassword());
		model.addAttribute("guestPassword", tsscGame.get().getGuestPassword());
		model.addAttribute("topics", topicDelegate.getTopics());

		return "game/update-game";
	}

	@PostMapping("/game/edit/{id}")
	public String updateGame(@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated(ValidationGame.class) TsscGame tsscGame, BindingResult bindingResult, Model model) {

		if (action.equals("Cancelar")) {

			return "redirect:/game/";
		}

		if (bindingResult.hasErrors()) {

			model.addAttribute("name", tsscGame.getName());
			model.addAttribute("adminPassword", tsscGame.getAdminPassword());
			model.addAttribute("scheduledDate", tsscGame.getScheduledDate());
			model.addAttribute("scheduledTime", tsscGame.getScheduledTime());
			model.addAttribute("nGroups", tsscGame.getNGroups());
			model.addAttribute("nSprints", tsscGame.getNSprints());
			model.addAttribute("userPassword", tsscGame.getUserPassword());
			model.addAttribute("guestPassword", tsscGame.getGuestPassword());
			model.addAttribute("topics", topicDelegate.getTopics());
			return "game/update-game";
		}

		if (action != null && !action.equals("Cancelar")) {

			try {
				if (tsscGame.getTsscTopic() == null) {

					gameDelegate.editGame(tsscGame);

				} else {

					gameDelegate.editGame(tsscGame);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return "redirect:/game/";
	}

	@GetMapping("/game/del/{id}")
	public String deleteGame(@PathVariable("id") long id) {
		TsscGame tsscGame = null;
		try {
			tsscGame = gameDelegate.getGame(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		gameDelegate.deleteGame(tsscGame);
		return "redirect:/game/";
	}
	
	
	// LISTA DE HISTORIAS DEL JUEGO
	
	@GetMapping("/game/list/{id}")
	public String showListStories(@PathVariable("id") long id, Model model) {
		Optional<TsscGame> tsscGame = null;
		try {
			tsscGame = Optional.of(gameDelegate.getGame(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (tsscGame == null)
			throw new IllegalArgumentException("Invalid game Id:" + id);

		model.addAttribute("tsscGame", tsscGame.get());
		model.addAttribute("stories", tsscGame.get().getTsscStories());

		return "game/list-stories";
	}
	
	@GetMapping("/game/filterinput")
	public String pageScheduledGames() {
		
		return "game/games-date";
	}
	
	@PostMapping("/game/filterlist")
	public String showScheduledGames(@RequestParam("initial")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initialDate,@RequestParam("final") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate finalDate, Model model) {
		System.out.println("hola");
		System.out.println(initialDate+"");
		Iterable<TsscGame> filter = gameDelegate.getScheduledGames(initialDate,finalDate);
		model.addAttribute("games",filter);
		
		return "game/games-date2";
	}
	
	
	@GetMapping("/game/{id}/stories")
	public String getStoriesByGame(@PathVariable("id") long id, Model model) {
		model.addAttribute("stories",gameDelegate.getStoriesByGame(id));
		try {
			model.addAttribute("game",gameDelegate.getGame(id).getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "game/list-stories";
	}
	
	@GetMapping("/game/{id}/stories/add")
	public String addStory(@PathVariable("id") long id, Model model) {
		
		try {
			TsscStory pc = new TsscStory();
			pc.setTsscGame(gameDelegate.getGame(id));
			model.addAttribute("tsscStory", pc);
			model.addAttribute("game", gameDelegate.getGame(id).getId());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "game/add-story";
	}
	@PostMapping("/game/{id}/stories/add")
	public String saveStoryByGame(@Validated(ValidationStory.class) TsscStory tsscStory, BindingResult bindingResult,
			@RequestParam(value = "action", required = true) String action, @PathVariable("id") long id,Model model) {
				
		
		if (!action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {
				
					
				//System.out.println("entre aqui");
				model.addAttribute("description", tsscStory.getDescription());
				model.addAttribute("businessValue", tsscStory.getBusinessValue());
				model.addAttribute("initialSprint", tsscStory.getInitialSprint());
				model.addAttribute("priority", tsscStory.getPriority());
				
				return "game/add-story";
			} else {

				// Guarda una Historia con el juego obligatorio.
				try {
					
            			 gameDelegate.addStoryByGame(id, tsscStory);
					    //tsscStory.setTsscGame(gameDelegate.getGame(id)); 
            			//gameDelegate.getGame(id).getTsscStories().add(tsscStory);
            			//System.out.println(gameDelegate.getGame(id).getTsscStories().get(0).getDescription());
            			//storyDelegate.addStory(tsscStory);
            			//System.out.println("id story "+story.getId());
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				model.addAttribute("stories", gameDelegate.getStoriesByGame(id));
//				try {
//					model.addAttribute("game",gameDelegate.getGame(id));
//				} catch (Exception e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				return "redirect:/game/{id}/stories";
			}
		} else {

			try {
				
				model.addAttribute("stories", gameDelegate.getStoriesByGame(id));
				model.addAttribute("game",gameDelegate.getGame(id));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "game/list-stories";
		}

	}
	
	//Edit Story 
	
	
	@GetMapping("/game/story/edit/{id}")
	public String showUpdateForm2(@PathVariable("id") long id, Model model) {
		Optional<TsscStory> tsscStory = null;
		try {
			tsscStory = Optional.of(storyDelegate.getStory(id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (tsscStory == null)
			throw new IllegalArgumentException("Invalid story Id:" + id);

		model.addAttribute("tsscStory", tsscStory.get());
		model.addAttribute("description", tsscStory.get().getDescription());
		model.addAttribute("businessValue", tsscStory.get().getBusinessValue());
		model.addAttribute("initialSprint", tsscStory.get().getInitialSprint());
		model.addAttribute("priority", tsscStory.get().getPriority());
		model.addAttribute("games", gameDelegate.getGames());

		return "game/update-story";
	}

	@PostMapping("/game/story/edit/{id}")
	public String updateStory(@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated(ValidationStory.class) TsscStory tsscStory, BindingResult bindingResult, Model model) {

		if (action.equals("Cancelar")) {

			return "redirect:/game/";
		}

		if (bindingResult.hasErrors()) {

			model.addAttribute("games", gameDelegate.getGames());
			model.addAttribute("description", tsscStory.getDescription());
			model.addAttribute("businessValue", tsscStory.getBusinessValue());
			model.addAttribute("initialSprint", tsscStory.getInitialSprint());
			model.addAttribute("priority", tsscStory.getPriority());
			
			return "game/update-story";
		}

		if (action != null && !action.equals("Cancelar")) {

			try {
				storyDelegate.editStory(tsscStory);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return "redirect:/game/";
	}
	
	
}
