package co.edu.icesi.fi.tics.tssc.rest;

import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

public interface ITimeControlRestController {
	
	public TsscTimecontrol saveTime(TsscTimecontrol nuevo);
	public TsscTimecontrol editTime(TsscTimecontrol editado);
	public Iterable<TsscTimecontrol> findAll();
	public TsscTimecontrol findById(long id);
	public void deleteTime(long id);
	
}
