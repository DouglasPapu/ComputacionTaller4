package co.edu.icesi.fi.tics.tssc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.icesi.fi.tics.tssc.exceptions.CapacityException;
import co.edu.icesi.fi.tics.tssc.exceptions.SpringException;
import co.edu.icesi.fi.tics.tssc.exceptions.TopicException;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;
import co.edu.icesi.fi.tics.tssc.services.TopicService;

@RestController
public class TopicRestController implements ITopicRestController {

	private TopicService topicService;

	@Autowired
	public TopicRestController(TopicService topicService) {
		this.topicService = topicService;

	}

	@PostMapping("/api/topics/")
	public TsscTopic saveTopic(@RequestBody TsscTopic tsscTopic) {

		try {
			
		    System.out.println("RestController "+tsscTopic.getName());
			return topicService.saveTopic(tsscTopic);

		} catch (TopicException | CapacityException | SpringException e) {

		   e.printStackTrace();
			
		}

		return null;
	}
	
	@RequestMapping(value = "/api/topics-edit/", method = RequestMethod.PUT)
	public TsscTopic editTopic(@RequestBody TsscTopic editado) {
		
		try {
			//TsscTopic editado = topicService.findById(id).get();
			return topicService.editTopic(editado);
		} catch (TopicException | CapacityException | SpringException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return editado;
	}
	
	@GetMapping("/api/topics/")
	public Iterable<TsscTopic> findAll(){
		return topicService.findAll();		
	}
	
	@GetMapping("/api/topics/{id}")
	public TsscTopic findById(@PathVariable("id") long id) {
		return topicService.findById(id).get();		
	}
	
	@DeleteMapping("/api/topics/{id}")
	public void deleteTopic(@PathVariable("id") long id) {
		
		TsscTopic del = topicService.findById(id).get();
		topicService.delete(del);
	}

	
	
}
