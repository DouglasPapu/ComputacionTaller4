package co.edu.icesi.fi.tics.tssc.rest;

import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

public interface ITopicRestController {
	public TsscTopic saveTopic(TsscTopic nuevo);
	public TsscTopic editTopic(long id);
	public Iterable<TsscTopic> findAll();
	public TsscTopic findById(long id);
	public void deleteTopic(long id);

}
