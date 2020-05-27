package co.edu.icesi.fi.tics.tssc.delegates;

import co.edu.icesi.fi.tics.tssc.model.TsscTimecontrol;

public interface ITimeControlDelegate {

	public TsscTimecontrol getTime(long id) throws Exception;
	public Iterable<TsscTimecontrol> getTimes();
	public TsscTimecontrol addTime(TsscTimecontrol nuevo);
	public void deleteTime(TsscTimecontrol del);
	public void editTime(TsscTimecontrol editado);
}
