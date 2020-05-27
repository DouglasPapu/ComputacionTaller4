package co.edu.icesi.fi.tics.tssc.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
import co.icesi.fi.tics.tssc.validations.ValidationStory;
import co.icesi.fi.tics.tssc.validations.ValidationTopic;

@Controller
public class StoryController {

	private IStoryDelegate storyDelegate;

	private IGameDelegate gameDelegate;

	@Autowired
	public StoryController(IStoryDelegate storyService, IGameDelegate gameService) {
		this.storyDelegate = storyService;
		this.gameDelegate = gameService;
	}

	@GetMapping("/story/")
	public String indexStory(Model model) {
		model.addAttribute("stories", storyDelegate.getStories());
		return "story/index";
	}

	@GetMapping("/story/add")
	public String addStory(Model model) {
		model.addAttribute("tsscStory", new TsscStory());
		model.addAttribute("games", gameDelegate.getGames());
		return "story/add-story";
	}

	@PostMapping("/story/add")
	public String saveStory(@Validated(ValidationStory.class) TsscStory tsscStory, BindingResult bindingResult,
			@RequestParam(value = "action", required = true) String action, Model model) {

		if (!action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {

				model.addAttribute("description", tsscStory.getDescription());
				model.addAttribute("businessValue", tsscStory.getBusinessValue());
				model.addAttribute("initialSprint", tsscStory.getInitialSprint());
				model.addAttribute("priority", tsscStory.getPriority());
				model.addAttribute("games", gameDelegate.getGames());

				return "story/add-story";
			} else {

				// Guarda una Historia con el juego obligatorio.
				try {
					
						
						gameDelegate.getGame(tsscStory.getTsscGame().getId()).getTsscStories().add(tsscStory);
            			storyDelegate.addStory(tsscStory);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return "redirect:/story/";
			}
		} else {

			model.addAttribute("stories", storyDelegate.getStories());
			return "story/index";
		}

	}

	// ----Fin de guardar Story -----

	@GetMapping("/story/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
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

		return "story/update-story";
	}

	@PostMapping("story/edit/{id}")
	public String updateStory(@PathVariable("id") long id,
			@RequestParam(value = "action", required = true) String action,
			@Validated(ValidationStory.class) TsscStory tsscStory, BindingResult bindingResult, Model model) {

		if (action.equals("Cancelar")) {

			return "redirect:/story/";
		}

		if (bindingResult.hasErrors()) {

			model.addAttribute("games", gameDelegate.getGames());
			model.addAttribute("description", tsscStory.getDescription());
			model.addAttribute("businessValue", tsscStory.getBusinessValue());
			model.addAttribute("initialSprint", tsscStory.getInitialSprint());
			model.addAttribute("priority", tsscStory.getPriority());
			
			return "story/update-story";
		}

		if (action != null && !action.equals("Cancelar")) {

			try {
				storyDelegate.editStory(tsscStory);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return "redirect:/story/";
	}

	@GetMapping("/story/del/{id}")
	public String deleteGame(@PathVariable("id") long id) {
		TsscStory tsscStory = null;
		try {
			tsscStory = storyDelegate.getStory(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		storyDelegate.deleteStory(tsscStory);
		return "redirect:/story/";
	}

}
