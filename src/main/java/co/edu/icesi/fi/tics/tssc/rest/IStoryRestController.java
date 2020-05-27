package co.edu.icesi.fi.tics.tssc.rest;

import co.edu.icesi.fi.tics.tssc.model.TsscStory;

public interface IStoryRestController {
	public TsscStory saveStory(TsscStory nuevo);
	public TsscStory editStory(TsscStory editado);
	public Iterable<TsscStory> findAll();
	public TsscStory  findById(long id);
	public void deleteStory(long id);
}
