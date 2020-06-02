package co.edu.icesi.fi.tics.tssc.services;

import java.util.Optional;

import co.edu.icesi.fi.tics.tssc.exceptions.BusinessValueException;
import co.edu.icesi.fi.tics.tssc.exceptions.GameException;
import co.edu.icesi.fi.tics.tssc.exceptions.InitialSprintException;
import co.edu.icesi.fi.tics.tssc.exceptions.PriorityException;
import co.edu.icesi.fi.tics.tssc.exceptions.StoryException;
import co.edu.icesi.fi.tics.tssc.model.TsscStory;

public interface StoryService {

	public TsscStory saveStory(TsscStory nuevo, long id) throws Exception;
	public TsscStory editStory(TsscStory editado) throws Exception;
	public Iterable<TsscStory> findAll();
	public Optional<TsscStory> findById(long id);
	public void delete(TsscStory del);
	public TsscStory saveStoryByGame(TsscStory nuevo, long id)
			throws Exception;
}
