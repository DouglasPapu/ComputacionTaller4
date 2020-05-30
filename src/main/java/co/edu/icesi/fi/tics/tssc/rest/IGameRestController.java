package co.edu.icesi.fi.tics.tssc.rest;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;

public interface IGameRestController {

	public TsscGame saveGame(TsscGame nuevo);
	public TsscGame editGame(TsscGame editado);
	public Iterable<TsscGame> findAll();
	public TsscGame findById(long id);
	public void deleteGame(long id);
	public Iterable<TsscGame> findByDate(LocalDate initialDate,LocalDate finalDate);
	public Iterable<Object[]> findTopicByGameDate(LocalDate date);
	
}
