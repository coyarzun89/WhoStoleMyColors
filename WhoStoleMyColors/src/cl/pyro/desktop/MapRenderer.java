package cl.pyro.desktop;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapRenderer {
	Map map;
	SpriteBatch batch;
	TextureRegion background;

	public MapRenderer (SpriteBatch batch, Map map) {
		this.map = map;
		this.batch = batch;
	}

	public void render () {
		renderBackground();
		renderObjects();
	}

	public void renderBackground () {
		batch.disableBlending();
		batch.begin();
		batch.draw(Map.grassMap, 0, 0);
		batch.end();
		
		batch.enableBlending();
		batch.begin();
		batch.draw(Map.deco1Map, 0, 0);
		batch.draw(Map.roadMap, 0, 0);
		batch.end();
	}

	public void renderObjects () {
		batch.enableBlending();
		batch.begin();
		renderRedButtons();
		renderTraps();
		renderWallBarriers();
		renderActor();
		batch.draw(Map.deco2Map, 0, 0);
		batch.end();
	}

	private void renderRedButtons(){
		for(RedButton button : Map.buttonsWall)
			if(button.isActivate())
				batch.draw(Asset.buttonOnRegion,button.getPosX(),320-button.getPosY()-32);
			else
				batch.draw(Asset.buttonOffRegion,button.getPosX(),320-button.getPosY()-32);
	}
	
	private void renderTraps(){
		for(Trap trap : Map.traps)
			if(trap.isActivate())
				batch.draw(Asset.trapOnRegion,trap.getPosX(),320-trap.getPosY()-32);
			else
				batch.draw(Asset.trapOffRegion,trap.getPosX(),320-trap.getPosY()-32);
	}
	
	private void renderWallBarriers(){
		for(WallBarrier wall : Map.wallsPress)
			if(wall.horizontal==true)
				if(!wall.activate)
					batch.draw(Asset.wallUpHRegion,wall.getPosX(),320-wall.getPosY()-32);
				else
					batch.draw(Asset.wallDownHRegion,wall.getPosX(),320-wall.getPosY()-32);
			else
				if(!wall.activate)
					batch.draw(Asset.wallUpVRegion,wall.getPosX(),320-wall.getPosY()-32);
				else
					batch.draw(Asset.wallDownVRegion,wall.getPosX(),320-wall.getPosY()-32);
		
		
		for(WallBarrier wall : Map.wallsTrap)
			if(wall.horizontal==true)
				if(!wall.activate)
					batch.draw(Asset.wallUpHRegion,wall.getPosX(),320-wall.getPosY()-32);
				else
					batch.draw(Asset.wallDownHRegion,wall.getPosX(),320-wall.getPosY()-32);
			else
				if(!wall.activate)
					batch.draw(Asset.wallUpVRegion,wall.getPosX(),320-wall.getPosY()-32);
				else
					batch.draw(Asset.wallDownVRegion,wall.getPosX(),320-wall.getPosY()-32);
	}
	

	private void renderActor () {
		final Actor unit = map.unit;
		int width = Asset.greenBallDownRegion.getRegionWidth();
		int height = Asset.greenBallDownRegion.getRegionHeight();
		final float posX = unit.getRenderingCenterX();
		final float posY = 320-32-unit.getRenderingCenterY();
		
		if(unit.isRight())
			batch.draw(Asset.greenBallRightRegion, posX, posY, width, height);
		else if(unit.isUp())
			batch.draw(Asset.greenBallDownRegion, posX, posY, width, height);
		else if(unit.isDown())
			batch.draw(Asset.greenBallUpRegion, posX, posY, width, height);
		else if(unit.isLeft())
			batch.draw(Asset.greenBallLeftRegion, posX, posY, width, height);
	}

	
}