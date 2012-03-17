package cl.pyro.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class LevelsScreen extends Screen {
	//OrthographicCamera guiCam;
	SpriteBatch batcher;
	Rectangle soundBounds;
	Rectangle level1Bounds;
	Rectangle level2Bounds;
	Rectangle backBounds;
	Vector3 touchPoint;
	int episode;
	
	public LevelsScreen (Game game, int episode) {
		super(game);
		this.episode=episode;
		batcher = new SpriteBatch();
		//Creamos rectangulos que representaran las colisiones con los distintos elementos en el menu principal
		level1Bounds = new Rectangle(10, 50, 50, 40);
		level2Bounds = new Rectangle(75, 50, 50, 40);
		//level3Bounds = new Rectangle(140, 50, 50, 40);
		backBounds = new Rectangle(0, 320-50, 50, 50);
		touchPoint = new Vector3();
		
		if(!Asset.music.isPlaying())
			Asset.music.play();
	}

	@Override
	public void update (float deltaTime) {
		if (Gdx.input.justTouched()) {
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			System.out.println("x: "+Gdx.input.getX()+" |y: "+Gdx.input.getY());
			
			if (OverlapTester.pointInRectangle(level1Bounds, touchPoint.x, touchPoint.y)) {
				Asset.playSound(Asset.paintSound);
				game.setScreen(new GameScreen(game,episode,1));
				return;
			}
			
			if (OverlapTester.pointInRectangle(level2Bounds, touchPoint.x, touchPoint.y)) {
				Asset.playSound(Asset.paintSound);
				game.setScreen(new GameScreen(game,episode,2));
				return;
			}
			
			if (OverlapTester.pointInRectangle(backBounds, touchPoint.x, touchPoint.y)) {
				Asset.playSound(Asset.paintSound);
				game.setScreen(new EpisodesScreen(game));
				return;
			}
		}
	}

	@Override
	public void present (float deltaTime) {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(1, 0, 0, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		batcher.disableBlending();
		batcher.begin();
		batcher.draw(Asset.levelsMenuRegion, 0, 0);		
		batcher.end();

	}

	@Override
	public void pause () {
		//Settings.save();
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
