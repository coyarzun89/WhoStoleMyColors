package cl.pyro.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Asset {
	public static Texture mainMenuScreenTexture;
	public static TextureRegion backgroundRegion;
	public static TextureRegion titleRegion;
	public static TextureRegion playbuttonRegion;
	
	public static Texture episodesMenuTexture;
	public static TextureRegion episodesMenuRegion;

	public static Texture levelsMenuTexture;
	public static TextureRegion levelsMenuRegion;
	
	public static Texture signHintTexture;
	public static TextureRegion signHintRegion;
	
	public static Texture assetsTexture;
	public static TextureRegion buttonOnRegion;
	public static TextureRegion buttonOffRegion;
	public static TextureRegion wallUpHRegion;
	public static TextureRegion wallDownHRegion;
	public static TextureRegion wallUpVRegion;
	public static TextureRegion wallDownVRegion;
	public static TextureRegion trapOnRegion;
	public static TextureRegion trapOffRegion;
	public static TextureRegion playRegion;
	public static TextureRegion hintRegion;
	public static TextureRegion boxRegion;
	public static TextureRegion trapBoxOnRegion;
	public static TextureRegion trapBoxOffRegion;
	public static TextureRegion paintGreenRegion;
	public static TextureRegion paintBlueRegion;
	public static TextureRegion arrowUpRegion;
	public static TextureRegion arrowDownRegion;
	public static TextureRegion arrowLeftRegion;
	public static TextureRegion arrowRightRegion;
	
	public static Texture greenBallTexture;
	public static TextureRegion greenBallDownRegion;
	public static TextureRegion greenBallUpRegion;
	public static TextureRegion greenBallRightRegion;
	public static TextureRegion greenBallLeftRegion;
	
	public static Texture pauseMenuTexture;
	public static TextureRegion pauseMenuRegion;
	
	public static Music music;
	public static Sound paintSound;
	public static Sound wallSound;

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		mainMenuScreenTexture = loadTexture("data/images/mainMenu/mainMenuScreen.png");
		backgroundRegion = new TextureRegion(mainMenuScreenTexture, 0, 0, 480, 320);
		titleRegion = new TextureRegion(mainMenuScreenTexture, 0, 320, 352, 160);
		playbuttonRegion = new TextureRegion(mainMenuScreenTexture, 352, 320, 96, 64);
		
		assetsTexture = loadTexture("data/images/assets/basicElements.png");
		buttonOffRegion = new TextureRegion(assetsTexture, 0, 0, 64, 64);
		buttonOnRegion = new TextureRegion(assetsTexture, 0, 64, 64, 64);
		trapOffRegion = new TextureRegion(assetsTexture, 64, 0, 32, 32);
		trapOnRegion = new TextureRegion(assetsTexture, 96, 0, 32, 32);
		wallDownVRegion = new TextureRegion(assetsTexture, 128, 0, 32, 32);
		wallUpVRegion = new TextureRegion(assetsTexture, 160, 0, 32, 32);
		wallDownHRegion = new TextureRegion(assetsTexture, 192, 0, 32, 32);
		wallUpHRegion = new TextureRegion(assetsTexture, 224, 0, 32, 32);
		playRegion = new TextureRegion(assetsTexture, 64, 32, 64, 64);
		hintRegion = new TextureRegion(assetsTexture, 128, 32, 64, 64);
		boxRegion = new TextureRegion(assetsTexture, 192, 32, 32, 32);
		trapBoxOffRegion = new TextureRegion(assetsTexture, 224, 32, 32, 32);
		trapBoxOnRegion = new TextureRegion(assetsTexture, 224, 64, 32, 32);
		paintGreenRegion = new TextureRegion(assetsTexture, 64, 96, 32, 32);
		paintBlueRegion = new TextureRegion(assetsTexture, 96, 96, 32, 32);
		arrowRightRegion = new TextureRegion(assetsTexture, 128, 96, 32, 32);
		arrowUpRegion = new TextureRegion(assetsTexture, 160, 96, 32, 32);
		arrowLeftRegion = new TextureRegion(assetsTexture, 192, 96, 32, 32);
		arrowDownRegion = new TextureRegion(assetsTexture, 224, 96, 32, 32);
		
		
		signHintTexture = new Texture(Gdx.files.internal("data/images/assets/signHint.png"));
		signHintRegion = new TextureRegion(signHintTexture, 0,0,480,320);
		
		greenBallTexture = new Texture(Gdx.files.internal("data/images/assets/greenBall.png"));
		greenBallDownRegion = new TextureRegion(greenBallTexture, 0,0,32,32);
		greenBallLeftRegion = new TextureRegion(greenBallTexture, 32,0,32,32);
		greenBallRightRegion = new TextureRegion(greenBallTexture, 64,0,32,32);
		greenBallUpRegion = new TextureRegion(greenBallTexture, 96,0,32,32);
		
		pauseMenuTexture = new Texture(Gdx.files.internal("data/images/assets/pauseMenu.png"));
		pauseMenuRegion = new TextureRegion(pauseMenuTexture, 0,0,480,320);
		
		episodesMenuTexture = new Texture(Gdx.files.internal("data/images/assets/episodesMenu.png"));
		episodesMenuRegion = new TextureRegion(episodesMenuTexture, 0,0,480,320);
		
		levelsMenuTexture = new Texture(Gdx.files.internal("data/images/assets/levelsMenu.png"));
		levelsMenuRegion = new TextureRegion(levelsMenuTexture, 0,0,480,320);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/mainMenuBackground.mp3"));
		music.setLooping(true);
		music.setVolume(0.5f);
		

		paintSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/sfxPaint.ogg"));
		wallSound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/sfxWall.ogg"));
		
	}

	public static void playSound (Sound sound) {
		 sound.play(1);
	}
}

