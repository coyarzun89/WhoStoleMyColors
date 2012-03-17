package cl.pyro.desktop;

import com.badlogic.gdx.backends.jogl.JoglApplication;

public class WhoStoleMyColorsDesktop {
	public static void main (String[] argv) {

		new JoglApplication(new WhoStoleMyColors(), "Who Stole My Colors", 480, 320, false);
	}
}
