package co.edu.icesi.fi.tics.tssc.services;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.icesi.fi.tics.tssc.dao.ITsscTimecontrolDAO;
import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

@Service
public class TimeControlServiceImpl implements TimeControlService{
	
	private ITsscTimecontrolDAO timeDao;
	
	@Autowired
	public TimeControlServiceImpl(ITsscTimecontrolDAO timeDao) {
		// TODO Auto-generated constructor stub
		
		this.timeDao = timeDao;
	}
	

	@Override
	@Transactional
	public TsscTimecontrol saveTimecontrol(TsscTimecontrol nuevo) {
	     timeDao.save(nuevo);
	     return nuevo;
	}

	@Override
	@Transactional
	public TsscTimecontrol editTimecontrol(TsscTimecontrol editado) {
		
		if(editado == null) {
			throw new NullPointerException("Timecontrol no encontrado");			
		}else {
			
			timeDao.update(editado);
			return editado;
			
		}
			
	}

	@Override
	public Iterable<TsscTimecontrol> findAll() {
		return timeDao.findAll();
	}

	@Override
	public Optional<TsscTimecontrol> findById(long id) {
		Optional<TsscTimecontrol> op = Optional.of(timeDao.findById(id).get(0));
		return op;
	}

	@Override
	@Transactional
	public void delete(TsscTimecontrol del) {
		timeDao.delete(del);
	}

	
	
	
}
