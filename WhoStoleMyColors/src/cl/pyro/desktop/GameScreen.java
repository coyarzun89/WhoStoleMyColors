package cl.pyro.desktop;


import cl.pyro.desktop.Map.MapListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


public class GameScreen extends Screen {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;

	int state;
	//OrthographicCamera guiCam;
	Vector3 touchPoint;
	SpriteBatch batcher;
	Map map;
	MapListener mapListener;
	MapRenderer renderer;
	Rectangle playBounds;
	Rectangle pauseBounds;
	Rectangle repeatBounds;
	Rectangle resumeBounds;
	Rectangle quitBounds;
	int lastScore;
	String scoreString;
	int episode;
	int level;
	
	public GameScreen (Game game, int episode, int level) {
		super(game);
		state = GAME_RUNNING;
		touchPoint = new Vector3();
		batcher = new SpriteBatch();
		this.episode=episode;
		this.level=level;
		
		map = new Map(episode,level);
		renderer = new MapRenderer(batcher, map);
		playBounds = new Rectangle(0, 320-64, 64, 64);
		pauseBounds = new Rectangle(0, 320-64, 64, 64);
		repeatBounds = new Rectangle(32, 32, 64, 64);
		resumeBounds = new Rectangle(100, 135, 50, 50);
		quitBounds = new Rectangle(32, 224, 64, 64);

	}
	@Override
	public void update (float deltaTime) {
		
		if (deltaTime > 0.1f) deltaTime = 0.1f;

		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}
	// update en que se debe presionar play para comenzar a jugar
	private void updateReady () {
		if (Gdx.input.justTouched()){
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			if (OverlapTester.pointInRectangle(playBounds, touchPoint.x, touchPoint.y))
			 state = GAME_RUNNING;		
		}
	}
	
	//update del juego en general
	private void updateRunning (float deltaTime) {
		
		//Se revisa si el jugador ha pausado el juego
		if (Gdx.input.justTouched()) {
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			if (OverlapTester.pointInRectangle(playBounds, touchPoint.x, touchPoint.y)) {
				Asset.playSound(Asset.paintSound);
				state = GAME_PAUSED;
				return;
			}
		}
		
		//se dan instrucciones para mover al personaje y se actualiza el mapa
			map.update(deltaTime);
		
		//cambia el estado pasando al jugador al siguiente nivel del juego
		if (map.state == Map.WORLD_STATE_NEXT_LEVEL) {
			state = GAME_LEVEL_END;
		}
		//cambia el estado, el jugador pierde y debe repetir este nivel
		if (map.state == Map.WORLD_STATE_GAME_OVER) {
			state = GAME_OVER;

		}
	}
	//update para cuando el juego esta en pause, se puede elegir volver al juego o salir
	private void updatePaused () {
		
	
		if (Gdx.input.justTouched()) {
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			System.out.println("x: "+Gdx.input.getX()+" |y: "+Gdx.input.getY());
			if (OverlapTester.pointInRectangle(resumeBounds, touchPoint.x, touchPoint.y)) {
				Asset.playSound(Asset.paintSound);
				state = GAME_RUNNING;
				return;
			}	
			if (OverlapTester.pointInRectangle(quitBounds, touchPoint.x, touchPoint.y)) {
				Asset.playSound(Asset.paintSound);
				state = GAME_READY;
				game.setScreen(new LevelsScreen(game,episode));
				return;
			}
			if (OverlapTester.pointInRectangle(repeatBounds, touchPoint.x, touchPoint.y)) {
				Asset.playSound(Asset.paintSound);
				state = GAME_READY;
				game.setScreen(new GameScreen(game,episode,level));
				return;
			}
		}
	}
	
	//update para cuando se termina el nivel, llama al mapa y lo renderiza nuevamente
	private void updateLevelEnd () {
		if (Gdx.input.justTouched()) {
			map = new Map(episode,level);
			renderer = new MapRenderer(batcher, map);
			state = GAME_READY;
		}
	}
	
	//update para cuando se pierde y se acaba el juego, manda a menu principal
	private void updateGameOver () {
		if (Gdx.input.justTouched()) {
			game.setScreen(new MainMenuScreen(game));
		}
	}

	@Override
	public void present (float deltaTime) {
		
		//Se renderiza el mapa
		GLCommon gl = Gdx.gl;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL10.GL_TEXTURE_2D);

		renderer.render();
		
		//Se dibujan las texturas necesarias sobre el mapa, dependiendo del estado de juego
		batcher.enableBlending();
		batcher.begin();
		switch (state) {
		case GAME_READY:
			presentReady();
			break;
		case GAME_RUNNING:
			presentRunning();
			break;
		case GAME_PAUSED:
			presentPaused();
			break;
		case GAME_LEVEL_END:
			presentLevelEnd();
			break;
		case GAME_OVER:
			presentGameOver();
			break;
		}
		batcher.end();
	}

	private void presentReady () {
		batcher.draw(Asset.playRegion, 0, 0, 64, 64);
	}

	private void presentRunning () {
		batcher.draw(Asset.playRegion, 0, 0, 64, 64);
		//Asset.font.draw(batcher, scoreString, 16, 480 - 20);
	}

	private void presentPaused () {
		if (Gdx.input.justTouched())
			System.out.println("x: "+Gdx.input.getX()+"| y: "+Gdx.input.getY());
		batcher.draw(Asset.pauseMenuRegion, 0, 0, 480, 320);
	}

	private void presentLevelEnd () {
		/*String topText = "the princess is ...";
		String bottomText = "in another castle!";
		float topWidth = Asset.font.getBounds(topText).width;
		float bottomWidth = Asset.font.getBounds(bottomText).width;
		Assets.font.draw(batcher, topText, 160 - topWidth / 2, 480 - 40);
		Assets.font.draw(batcher, bottomText, 160 - bottomWidth / 2, 40);*/
	}

	private void presentGameOver () {
		/*batcher.draw(Assets.gameOver, 160 - 160 / 2, 240 - 96 / 2, 160, 96);
		float scoreWidth = Assets.font.getBounds(scoreString).width;
		Assets.font.draw(batcher, scoreString, 160 - scoreWidth / 2, 480 - 20);*/
	}

	@Override
	public void pause () {
		if (state == GAME_RUNNING) state = GAME_PAUSED;
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
