package co.edu.icesi.fi.tics.tssc.delegates;

import java.time.LocalDate;

import co.edu.icesi.fi.tics.tssc.model.TsscGame;
import co.edu.icesi.fi.tics.tssc.model.TsscTopic;

public interface IGameDelegate {
	
	public TsscGame getGame(long id)  throws Exception;
	public Iterable<TsscGame> getGames();
	public TsscGame addGame(TsscGame newGame);
	public void deleteGame(TsscGame game);
	public void editGame(TsscGame editado);
	public Iterable<TsscGame> getScheduledGames(LocalDate initial, LocalDate finald);
	public Iterable<TsscTopic> getTopicsByGameDate(LocalDate date);

}
