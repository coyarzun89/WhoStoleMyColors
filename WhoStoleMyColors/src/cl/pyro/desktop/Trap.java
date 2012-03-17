package cl.pyro.desktop;

import com.badlogic.gdx.math.Rectangle;

public class Trap extends MapElements {
	public boolean alreadyActive=false;
	public Rectangle boundsTrap;
	public Trap(Map map, int posX, int posY) {
		super(map, posX, posY);
		boundsTrap = new Rectangle(posX*32, posY*32, 32, 32);
	}

}
