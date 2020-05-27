package co.edu.icesi.fi.tics.tssc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;
import co.edu.icesi.fi.tics.tssc.services.TimeControlService;

@RestController
public class TimeControlRestController implements ITimeControlRestController{

	private TimeControlService timeService;
	
	@Autowired
	public TimeControlRestController(TimeControlService timeService) {
		// TODO Auto-generated constructor stub
		this.timeService = timeService;
	}
	
	@Override
	@PostMapping("/api/timeControl/")
	public TsscTimecontrol saveTime(@RequestBody TsscTimecontrol nuevo) {
		// TODO Auto-generated method stub		
		return timeService.saveTimecontrol(nuevo);
	}

	@Override
	@PutMapping("/api/timeControl-edit/")
	public TsscTimecontrol editTime(@RequestBody TsscTimecontrol editado) {
		// TODO Auto-generated method stub
		return timeService.editTimecontrol(editado);
	}

	@Override
	@GetMapping("/api/timeControl/")
	public Iterable<TsscTimecontrol> findAll() {
		// TODO Auto-generated method stub
		return timeService.findAll();
	}

	@Override
	@GetMapping("/api/timeControl/{id}")
	public TsscTimecontrol findById(@PathVariable("id") long id) {
		// TODO Auto-generated method stub
		return timeService.findById(id).get();
	}

	@Override
	@DeleteMapping("/api/timeControl/{id}")
	public void deleteTime(@PathVariable("id") long id) {
		// TODO Auto-generated method stub
		TsscTimecontrol del = timeService.findById(id).get();
		timeService.delete(del);
		
	}

}
