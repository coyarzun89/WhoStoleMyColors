package cl.pyro.desktop;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Map {
	public interface MapListener {
		public void jump ();

		public void highJump ();

		public void hit ();

		public void coin ();
	}

	public static final float WORLD_WIDTH = 15;
	public static final float WORLD_HEIGHT = 10;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static int cellWidth=32;

	public Vector3 touchPoint;
	
	public static TiledMap map;
	public static Texture mapTexture;
	public static TextureRegion grassMap;
	public static TextureRegion deco1Map;
	public static TextureRegion deco2Map;
	public static TextureRegion roadMap;
	
	public static ArrayList<WallBarrier> wallsPress;
	public static ArrayList<WallBarrier> wallsTrap;
	public static ArrayList<RedButton> buttonsWall;
	public static ArrayList<Trap> traps;
	
	public Actor unit;
	/*public final Bob bob;
	public final List<Platform> platforms;
	public final List<Spring> springs;
	public final List<Squirrel> squirrels;
	public final List<Coin> coins;
	public Castle castle;*/

	public static Movement[][] movementAllowed;

	public int episode;
	public int level;
	public int state;
	
	public static Vector2 startPoint;
	public static Vector2 endPoint;
	public int [][] roadAllowed;
	public boolean direction;
	public boolean wallState;
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
	
	public Map (int episode, int level) {
		startPoint = new Vector2();
		endPoint = new Vector2();
		movementAllowed = new Movement[15][10];
		touchPoint = new Vector3();
		wallsPress = new ArrayList<WallBarrier>();
		wallsTrap= new ArrayList<WallBarrier>();
		buttonsWall = new ArrayList<RedButton>();
		traps = new ArrayList<Trap>();
		
		for(int i = 0; i < 15; i++)
			for(int j = 0; j < 10; j++)
				movementAllowed[i][j] = new Movement();
		
		generateLevel(episode,level);

		this.state = WORLD_STATE_RUNNING;
	}

	private void generateLevel (int episode,int level) {
		  //cargamos el mapa requerido para el episodio y el level del input
		  map = TiledLoader.createMap(Gdx.files.internal("data/images/maps/episode"+episode+"/level"+level+"/mapa.tmx"));
	      mapTexture = loadTexture("data/images/maps/episode"+episode+"/level"+level+"/mapa.png");
	      for(TiledLayer capa : map.layers)
	    	  if(capa.name.compareTo("camino")==0)
	    	  roadAllowed=capa.tiles;
	    
	      //cargamos el camino permitido por nuestro personaje
	      for(int x=0;x<10;x++)
	    	  for(int y=0;y<15;y++)
	    		  if(roadAllowed[x][y]==0)
	    			  movementAllowed[y][x].allowed=false;
	    		  else
	    			  movementAllowed[y][x].allowed=true;
	    		  
	      //creamos las 4 regiones 
	      grassMap = new TextureRegion(mapTexture, 0, 0, 480, 320); 
	      deco1Map = new TextureRegion(mapTexture, 480, 0, 480, 320);
	      deco2Map = new TextureRegion(mapTexture, 480, 320, 480, 320);
	      roadMap  = new TextureRegion(mapTexture, 0, 320, 480, 320);
	    	  
	    	  
	    	for(TiledObjectGroup olayer : map.objectGroups)
		    	  for(TiledObject object : olayer.objects)
		    		  switch(object.name){
		    		  case "start":  startPoint.set(object.x/32, object.y/32);
		    						 this.unit = new Actor(startPoint.x, startPoint.y,this);
		    		  break;
		    		  case "end":	endPoint.set(object.x/32, object.y/32);
		    		  break;
		    		  case "boton": buttonsWall.add(new RedButton(this,object.x/32,object.y/32));
		    		  break;
		    		  case "trap":  traps.add(new Trap(this, object.x/32, object.y/32));
		    		  break;
		    		  case "wall":  if(roadAllowed[object.y/32][object.x/32]==756)
		    			  			 direction=true;
		    		  				else
		    		  				 direction=false;
		    		  
		    		  				if(object.type.compareTo("up")==0)
		    		  					wallState=false;
		    		  				else if(object.type.compareTo("down")==0)
		    		  					wallState=true;
		    		  					
		    			  			if(olayer.properties.values().toString().compareTo("[1]")==0){
		    			  			  wallsPress.add(new WallBarrier(this, object.x/32, object.y/32,buttonsWall.get(buttonsWall.size()-1),direction,wallState));
		    			  			  movementAllowed[object.x/32][object.y/32].allowed=wallState;}
		    		  			    else if(olayer.properties.values().toString().compareTo("[2]")==0){
			    			  		  wallsTrap.add(new WallBarrier(this, object.x/32, object.y/32,traps.get(traps.size()-1),direction,wallState));
			    			  		  movementAllowed[object.x/32][object.y/32].allowed=wallState;} 		
		    		  break;
		    		  };
	}

	public void update (float deltaTime) {
		updateActor(deltaTime);
		updateButtonsWalls(deltaTime);
		updateTrapsWalls(deltaTime);
		//updateCoins(deltaTime);
		//if (bob.state != Bob.BOB_STATE_HIT) checkCollisions();
		//checkGameOver();
	}

	private void updateActor (float deltaTime) {
		unit.update(deltaTime);
	}
	
	private void updateButtonsWalls (float deltaTime) {
		
		if (Gdx.input.isTouched()) {
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			for(RedButton button : buttonsWall)
				if (OverlapTester.pointInRectangle(button.boundsButton, touchPoint.x, touchPoint.y) && Gdx.input.justTouched()){
					Asset.playSound(Asset.wallSound);
					button.setActivate(true);
					for(WallBarrier wall : wallsPress){
						if(wall.buttonWall==button){
							wall.setActivate(!wall.defectoValue);
							movementAllowed[wall.cellX][wall.cellY].allowed=!wall.defectoValue;}		
					}
				}
		}else
			for(RedButton button : buttonsWall){
				button.setActivate(false);
				for(WallBarrier wall : wallsPress){
					wall.setActivate(wall.defectoValue);
					movementAllowed[wall.cellX][wall.cellY].allowed=wall.defectoValue;
				}
			}
	}

	private void updateTrapsWalls (float deltaTime) {
		
			for(Trap trap :traps)
				if (OverlapTester.pointInRectangle(trap.boundsTrap, unit.getRenderingCenterX(),unit.getRenderingCenterY())){		
					trap.setActivate(true);
					for(WallBarrier wall : wallsTrap)
						if(wall.trap==trap && trap.alreadyActive==false){
							trap.alreadyActive=true;
							wall.setActivate(!wall.defectoValue);
							movementAllowed[wall.cellX][wall.cellY].allowed=true;
							Asset.playSound(Asset.wallSound);}	
				}
				
	
	}

}
