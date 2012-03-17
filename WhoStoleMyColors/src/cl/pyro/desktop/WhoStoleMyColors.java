package cl.pyro.desktop;


public class WhoStoleMyColors extends Game {
	boolean firstTimeCreate = true;

	@Override
	public Screen getStartScreen () {
		return new MainMenuScreen(this);
	}

	@Override
	public void create () {
		//Settings.load();
		Asset.load();
		super.create();
	}
}