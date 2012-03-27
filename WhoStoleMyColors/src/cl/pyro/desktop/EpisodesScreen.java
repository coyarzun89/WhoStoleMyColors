package cl.pyro.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class EpisodesScreen extends Screen {
	//OrthographicCamera guiCam;
	SpriteBatch batcher;
	Rectangle playBounds;
	Rectangle backBounds;

	Vector3 touchPoint;

	public EpisodesScreen (Game game) {
		super(game);
		batcher = new SpriteBatch();
		//Creamos rectangulos que representaran las colisiones con los distintos elementos en el menu principal
		playBounds = new Rectangle(20, 85, 100, 100);
		backBounds = new Rectangle(0, 320-50, 50, 50);
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
				game.setScreen(new LevelsScreen(game,1));
				return;
			}
			if (OverlapTester.pointInRectangle(backBounds, touchPoint.x, touchPoint.y)) {
				Asset.playSound(Asset.paintSound);
				game.setScreen(new MainMenuScreen(game));
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
		batcher.draw(Asset.episodesMenuRegion, 0, 0);
		
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
