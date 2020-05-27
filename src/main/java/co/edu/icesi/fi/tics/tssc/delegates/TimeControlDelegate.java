package co.edu.icesi.fi.tics.tssc.delegates;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

@Component
public class TimeControlDelegate implements ITimeControlDelegate{

	public final static String SERVER = "http://localhost:8084/";
	RestTemplate restTemplate;
	
	public TimeControlDelegate() {
		// TODO Auto-generated constructor stub
		restTemplate = new RestTemplate();
	}
	
	
	@Override
	public TsscTimecontrol getTime(long id) throws Exception {
		// TODO Auto-generated method stub
		TsscTimecontrol tsscTimecontrol = restTemplate.getForObject(SERVER + "api/timeControl/"+id, TsscTimecontrol.class);
		
		if(tsscTimecontrol == null) {
			
			throw new Exception("timeControl is null");
		}
		
		return tsscTimecontrol;
	}

	@Override
	public Iterable<TsscTimecontrol> getTimes() {
		// TODO Auto-generated method stub
		TsscTimecontrol[] times = restTemplate.getForObject(SERVER+ "api/timeControl/", TsscTimecontrol[].class);
		List<TsscTimecontrol> list;
		
		try {
			
			list = Arrays.asList(times);
			return list;
			
		} catch(Exception e) {
			e.printStackTrace();
			return null;
			
		}
		
	}

	@Override
	public TsscTimecontrol addTime(TsscTimecontrol nuevo) {
		// TODO Auto-generated method stub
		
		ResponseEntity<TsscTimecontrol> rs = restTemplate.postForEntity(SERVER + "api/timeControl/", nuevo, TsscTimecontrol.class);
		
		TsscTimecontrol time = rs.getBody();
		
		if(time == null) {
			throw new IllegalArgumentException("timeControl is null");
		}
		return time;
	}

	@Override
	public void deleteTime(TsscTimecontrol del) {
		// TODO Auto-generated method stub
		
		if(del == null) {
			throw new IllegalArgumentException("timeControl is null");		
		}else {
			
			restTemplate.delete(SERVER+ "api/timeControl/"+del.getId());
		}
		
	}

	@Override
	public void editTime(TsscTimecontrol editado) {
		// TODO Auto-generated method stub
		restTemplate.put(SERVER+"api/timeControl-edit/",editado,TsscTimecontrol.class);
		
	}


	public RestTemplate getRestTemplate() {
		return restTemplate;
	}


	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	
	
	
}
