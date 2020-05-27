package co.edu.icesi.fi.tics.tssc.rest;
import co.edu.icesi.fi.tics.tssc.model.TsscGame;

public interface IGameRestController {

	public TsscGame saveGame(TsscGame nuevo);
	public TsscGame editGame(TsscGame editado);
	public Iterable<TsscGame> findAll();
	public TsscGame findById(long id);
	public void deleteGame(long id);
	
}
