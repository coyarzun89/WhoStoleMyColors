package cl.pyro.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen extends Screen {
	//OrthographicCamera guiCam;
	SpriteBatch batcher;
	Rectangle soundBounds;
	Rectangle playBounds;
	Rectangle highscoresBounds;
	Rectangle helpBounds;
	Vector3 touchPoint;

	public MainMenuScreen (Game game) {
		super(game);
		batcher = new SpriteBatch();
		//Creamos rectangulos que representaran las colisiones con los distintos elementos en el menu principal
		playBounds = new Rectangle(200, 320-100, 90, 50);
		touchPoint = new Vector3();
		
		if(!Asset.music.isPlaying())
			Asset.music.play();
	}

	@Override
	public void update (float deltaTime) {
		if (Gdx.input.justTouched()) {
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			
			if (OverlapTester.pointInRectangle(playBounds, touchPoint.x, touchPoint.y)) {
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
		batcher.draw(Asset.backgroundRegion, 0, 0);
		
		batcher.end();

		batcher.enableBlending();
		batcher.begin();
		batcher.draw(Asset.titleRegion, 80, 130);
		batcher.draw(Asset.playbuttonRegion, 200, 50);
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
