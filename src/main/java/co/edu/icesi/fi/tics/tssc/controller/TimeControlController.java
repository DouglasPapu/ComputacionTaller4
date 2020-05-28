package co.edu.icesi.fi.tics.tssc.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.icesi.fi.tics.tssc.delegates.IGameDelegate;
import co.edu.icesi.fi.tics.tssc.delegates.ITimeControlDelegate;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

@Controller
public class TimeControlController {

	private ITimeControlDelegate delegate;

	private IGameDelegate gameDelegate;

	@Autowired
	public TimeControlController(ITimeControlDelegate delegate, IGameDelegate gameDelegate) {
		// TODO Auto-generated constructor stub
		this.delegate = delegate;
		this.gameDelegate = gameDelegate;
	}

	@GetMapping("/timeControl/")
	public String indexUser(Model model) {
		model.addAttribute("timeControls", delegate.getTimes());
		return "timeControl/index";
	}

	@GetMapping("/timeControl/add")
	public String addTime(Model model) {
		model.addAttribute("tsscTimecontrol", new TsscTimecontrol());
		model.addAttribute("games", gameDelegate.getGames());
		return "timeControl/add-time";
	}

	@PostMapping("/timeControl/add")
	public String saveTime(TsscTimecontrol tsscTimecontrol, BindingResult bindingResult,
			@RequestParam(value = "action", required = true) String action, Model model) {

		if (!action.equals("Cancelar")) {
			if (bindingResult.hasErrors()) {

				model.addAttribute("name", tsscTimecontrol.getName());
				model.addAttribute("autostart", tsscTimecontrol.getAutostart());
				model.addAttribute("intervalRunning", tsscTimecontrol.getIntervalRunning());
				model.addAttribute("lastPlayTime", tsscTimecontrol.getLastPlayTime());
				model.addAttribute("order", tsscTimecontrol.getOrder());
				model.addAttribute("state", tsscTimecontrol.getState());
				model.addAttribute("timeInterval", tsscTimecontrol.getTimeInterval());
				model.addAttribute("type", tsscTimecontrol.getType());
				model.addAttribute("games", gameDelegate.getGames());

				return "timeControl/add-time";

			} else {

				System.out.println("SOY EL GAME" + tsscTimecontrol.getTsscGame().getName());
				delegate.addTime(tsscTimecontrol);

				return "redirect:/timeControl/";
			}

		} else {
			model.addAttribute("timeControls", delegate.getTimes());
			return "timeControl/index";

		}
	}
	
	@GetMapping("/timeControl/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Optional<TsscTimecontrol> tsscTimecontrol = null;
		
		try {
			
			tsscTimecontrol = Optional.of(delegate.getTime(id));
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		
		if(tsscTimecontrol == null) 
			throw new IllegalArgumentException("Invalid timecontrol Id:" + id);
			
		model.addAttribute("tsscTimecontrol", tsscTimecontrol.get());
		model.addAttribute("name", tsscTimecontrol.get().getName());
		model.addAttribute("autoStart", tsscTimecontrol.get().getAutostart());
		model.addAttribute("intervalRunning", tsscTimecontrol.get().getIntervalRunning());
		model.addAttribute("lastPlayTime", tsscTimecontrol.get().getLastPlayTime());
		model.addAttribute("order", tsscTimecontrol.get().getOrder());
		model.addAttribute("state", tsscTimecontrol.get().getState());
		model.addAttribute("timeInterval", tsscTimecontrol.get().getTimeInterval());
		model.addAttribute("type", tsscTimecontrol.get().getType());
		model.addAttribute("games", gameDelegate.getGames());
		
		return "timeControl/update-time";		
	}
	
	
	@PostMapping("/timeControl/edit/{id}")
	public String updateTime(@PathVariable("id") long id, 
			@RequestParam(value = "action", required = true) String action,
			TsscTimecontrol tsscTimecontrol, BindingResult bindingResult, Model model) {
		
		if(action.equals("Cancelar")) {
			
			return "redirect:/timeControl/";
			
		}
		
		if(bindingResult.hasErrors()) {
			
			model.addAttribute("name", tsscTimecontrol.getName());
			model.addAttribute("autoStart", tsscTimecontrol.getAutostart());
			model.addAttribute("intervalRunning", tsscTimecontrol.getIntervalRunning());
			model.addAttribute("lastPlayTime", tsscTimecontrol.getLastPlayTime());
			model.addAttribute("order", tsscTimecontrol.getOrder());
			model.addAttribute("state", tsscTimecontrol.getState());
			model.addAttribute("timeInterval", tsscTimecontrol.getTimeInterval());
			model.addAttribute("type", tsscTimecontrol.getType());
			model.addAttribute("games", gameDelegate.getGames());
			
			return "timeControl/update-time";		
		}
		
		if(action != null && !action.equals("Cancelar")) {
			
			try {
				
				delegate.editTime(tsscTimecontrol);
				
			}catch(Exception e) {
				e.printStackTrace();
				
			}
			
		}
		
		return "redirect:/timeControl/";
		
	}
	
	@GetMapping("/timeControl/del/{id}")
	public String deleteTime(@PathVariable("id") long id) {
		TsscTimecontrol tsscTimecontrol = null;
		
		try {
			tsscTimecontrol = delegate.getTime(id);
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		delegate.deleteTime(tsscTimecontrol);
		
		return "redirect:/timeControl/";
	}
	
	
	

}
