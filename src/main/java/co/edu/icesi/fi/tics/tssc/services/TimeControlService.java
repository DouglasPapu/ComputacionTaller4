package co.edu.icesi.fi.tics.tssc.services;

import java.util.Optional;

import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

public interface TimeControlService {

	public TsscTimecontrol saveTimecontrol(TsscTimecontrol nuevo);
	public TsscTimecontrol editTimecontrol(TsscTimecontrol editado);
	public Iterable<TsscTimecontrol> findAll();
	public Optional<TsscTimecontrol> findById(long id);
	public void delete(TsscTimecontrol del);
	
}
