package cl.pyro.desktop;

import com.badlogic.gdx.math.Rectangle;

public class RedButton extends MapElements {
	public Rectangle boundsButton;
	public RedButton(Map map, int posX, int posY) {
		super(map, posX, posY);
		boundsButton = new Rectangle(posX*32, posY*32, 64, 64);
	}
	

}
