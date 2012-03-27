package cl.pyro.desktop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLayer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Rectangle;
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
	public static TextureRegion decoMap;
	public static TextureRegion roadMap;
	
	public static ArrayList<WallBarrier> wallsPress;
	public static ArrayList<WallBarrier> wallsTrap;
	public static ArrayList<WallBarrier> wallsBoxes;
	public static ArrayList<RedButton> buttonsWall;
	public static ArrayList<Trap> traps;
	public static ArrayList<TrapBox> trapboxes;
	public static ArrayList<SignHint> hints;
	public static ArrayList<ArrowDirection> arrows;
	public static ArrayList<Box> boxes;
	
	public Actor unit;
	public static Movement[][] movementAllowed;
	public static String[] directions;
	public int episode;
	public int level;
	public int state;
	
	public static Vector2 startPoint;
	public static Vector2 endPoint;
	public static Rectangle Paint;
	public int [][] roadAllowed;
	public boolean direction;
	public boolean wallState;
	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}
	
	public Map (int episode, int level) {
		startPoint = new Vector2();
		endPoint = new Vector2();
		Paint = new Rectangle();
		movementAllowed = new Movement[15][10];
		directions = new String[4];
		touchPoint = new Vector3();
		wallsPress = new ArrayList<WallBarrier>();
		wallsTrap= new ArrayList<WallBarrier>();
		wallsBoxes = new ArrayList<WallBarrier>();
		buttonsWall = new ArrayList<RedButton>();
		traps = new ArrayList<Trap>();
		trapboxes = new ArrayList<TrapBox>();
		hints = new ArrayList<SignHint>();
		arrows = new ArrayList<ArrowDirection>();
		boxes = new ArrayList<Box>();
		
		for(int i = 0; i < 15; i++)
			for(int j = 0; j < 10; j++)
				movementAllowed[i][j] = new Movement();
		

		this.episode=episode;
		this.level=level;
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
	      decoMap = new TextureRegion(mapTexture, 480, 0, 480, 320);
	      roadMap  = new TextureRegion(mapTexture, 0, 320, 480, 320);
	    	  
	    	  
	    	for(TiledObjectGroup olayer : map.objectGroups)
		    	  for(TiledObject object : olayer.objects)
		    		  switch(object.name){
		    		  case "start":  startPoint.set(object.x/32, object.y/32);
		    						 this.unit = new Actor(startPoint.x, startPoint.y,this);
		    		  break;
		    		  case "end":	endPoint.set(object.x, object.y);
		    		  				Paint = new Rectangle(object.x, object.y, 32, 32);
		    		  break;
		    		  case "boton": buttonsWall.add(new RedButton(this,object.x/32,object.y/32));
		    		  break;
		    		  case "trap":  traps.add(new Trap(this, object.x/32, object.y/32));
		    		  break;
		    		  case "trapbox":  trapboxes.add(new TrapBox(this, object.x/32, object.y/32));
		    		  break;
		    		  case "tip":   hints.add(new SignHint(this, object.x/32, object.y/32));
		    		  break;
		    		  case "box":   boxes.add(new Box(this, object.x/32, object.y/32));
		    		  break;
		    		  case "arrow":  int i=0;
		    			  			 for ( Object x : object.properties.values() ) {
		    			  				 directions[i]=x.toString();
		    			  				 i++;
		    		  					}
		    		  				
		    			  			arrows.add(new ArrowDirection(this, object.x/32, object.y/32,directions));
		    		  break;
		    		  case "wall":  if(object.properties.values().toString().compareTo("[1]")==0)
		    			  			 direction=true;
		    		  				else //if(object.properties.values().toString().compareTo("[1]")==0)
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
		    		  			    else if(olayer.properties.values().toString().compareTo("[3]")==0){
			    			  		  wallsBoxes.add(new WallBarrier(this, object.x/32, object.y/32,trapboxes.get(trapboxes.size()-1),direction,wallState));
			    			  		  movementAllowed[object.x/32][object.y/32].allowed=wallState;} 
		    		  break;
		    		  };
	}

	public void update (float deltaTime) {
		updateActor(deltaTime);
		updateButtonsWalls();
		updateTrapsWalls();
		updateSignHints();
		updateArrows();
		updateTrapsBoxes();
		checkLevelUp();
		//updateCoins(deltaTime);
		//if (bob.state != Bob.BOB_STATE_HIT) checkCollisions();
		//checkGameOver();
	}
	private void cleanLevel(){
		wallsPress.clear();
		wallsTrap.clear();
		wallsBoxes.clear();
		buttonsWall.clear(); 
		traps.clear();
		trapboxes.clear();
		boxes.clear();
		arrows.clear();
		hints.clear(); 
		unit.killUnit();
	}
	
	private void checkLevelUp() {
		if (OverlapTester.pointInRectangle(Paint, unit.getRenderingCenterX(), unit.getRenderingCenterY())){
			level++;
			cleanLevel();
			generateLevel(episode,level);}
	}
	

	/*private void updateBox() {
		for(Box box : boxes){
			System.out.println("cellx"+box.cellX+"celly"+box.cellY);
			if(unit.up && OverlapTester.pointInRectangle(box.boundsBox, unit.getRenderingCenterX(), unit.getRenderingCenterY()-32)){
				if(movementAllowed[(int)unit.getCellX()][(int)unit.getCellY()-2].allowed){
				box.posX = unit.getRenderingCenterX();
				box.posY = unit.getRenderingCenterY()-30;
				box.boundsBox = new Rectangle(box.posX, box.posY, 32, 32);}
			}else if(unit.down && OverlapTester.pointInRectangle(box.boundsBox, unit.getRenderingCenterX(), unit.getRenderingCenterY()+32)){
				if(movementAllowed[(int)unit.getCellX()][box.cellY+1].allowed){
					
				box.posX = unit.getRenderingCenterX();
				box.posY = unit.getRenderingCenterY()+30;
				box.boundsBox = new Rectangle(box.posX, box.posY, 32, 32);
				}
			}else if(unit.right && OverlapTester.pointInRectangle(box.boundsBox, unit.getRenderingCenterX()+32, unit.getRenderingCenterY())){
				if(movementAllowed[box.cellX+1][box.cellY].allowed){
				box.posX = unit.getRenderingCenterX()+30;
				box.posY = unit.getRenderingCenterY();
				box.boundsBox = new Rectangle(box.posX, box.posY, 32, 32);}
			}else if(unit.left && OverlapTester.pointInRectangle(box.boundsBox, unit.getRenderingCenterX()-32, unit.getRenderingCenterY())){
				if(movementAllowed[box.cellX-1][box.cellY].allowed){
				box.posX = unit.getRenderingCenterX()-30;
				box.posY = unit.getRenderingCenterY();
				box.boundsBox = new Rectangle(box.posX, box.posY, 32, 32);}
			}
			//box.move();
		}
	 }*/
	
	
	private void updateArrows(){
		if (Gdx.input.isTouched()) {
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			boolean assigned=false;
			for(ArrowDirection arrow : arrows)
				if (OverlapTester.pointInRectangle(arrow.boundsArrow, touchPoint.x, touchPoint.y) && Gdx.input.justTouched())
					while(assigned==false){
					arrow.actualValue++;
					if(arrow.actualValue>4)
						arrow.actualValue=1;
					
					if(arrow.down==1 && arrow.actualValue==1)
						assigned=true;
					else if(arrow.left==1 && arrow.actualValue==2)
						assigned=true;
					else if(arrow.right==1 && arrow.actualValue==3)
						assigned=true;
					else if(arrow.up==1 && arrow.actualValue==4)
						assigned=true;
					else
						arrow.actualValue++;
					
			}
		}
		
		for(ArrowDirection arrow : arrows)
			if (OverlapTester.pointInRectangle(arrow.boundsArrow, unit.getRenderingCenterX(),unit.getRenderingCenterY())){
				if(arrow.actualValue==1){
					unit.right = false;
					unit.up = false;
					unit.left = false;
					unit.down = true;
				}else if(arrow.actualValue==2){
					unit.right = false;
					unit.up = false;
					unit.left = true;
					unit.down = false;
				}else if(arrow.actualValue==3){
					unit.right = true;
					unit.up = false;
					unit.left = false;
					unit.down = false;
			    }else if(arrow.actualValue==4){
			    	unit.right = false;
			    	unit.up = true;
			    	unit.left = false;
			    	unit.down = false;}
				
		}
	}
	private void updateActor (float deltaTime) {
		if(this.level==3)
			if(unit.getCellY()==1){
				if(unit.getCellX()==9)
					movementAllowed[9][0].allowed=false;
				else
					movementAllowed[9][0].allowed=true;
			   
				if(unit.getCellX()==11)
					movementAllowed[11][0].allowed=false;
				else
					movementAllowed[11][0].allowed=true;
			}
			
		unit.update(deltaTime);
	}
	
	private void updateSignHints () {
		if (Gdx.input.isTouched()) {
			touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			for(SignHint hint : hints)
				if (OverlapTester.pointInRectangle(hint.boundsSign, touchPoint.x, touchPoint.y) && Gdx.input.justTouched())
					hint.setActivate(true);
				
		}
		else
			for(SignHint hint : hints)
				hint.setActivate(false);
	}
	
	private	void updateButtonsWalls () {
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

	private void updateTrapsWalls () {
			for(Trap trap :traps){
				if (OverlapTester.pointInRectangle(trap.boundsTrap, unit.getRenderingCenterX()+0.1f,unit.getRenderingCenterY())){		
					trap.setActivate(true); 
					for(WallBarrier wall : wallsTrap)
						if(wall.trap==trap && wall.alreadyActive==false){
							if(trap.alreadyActive==false)
								Asset.playSound(Asset.wallSound);
							
							trap.alreadyActive=true;
							wall.setActivate(!wall.defectoValue);
							movementAllowed[wall.cellX][wall.cellY].allowed=!wall.defectoValue;}	
				}
				
	
	}}
	
	private void updateTrapsBoxes () {
		for(TrapBox trapbox :trapboxes){
			for(Box box : boxes)
			if (OverlapTester.pointInRectangle(trapbox.boundsTrap,  box.posX,box.posY)){		
				trapbox.setActivate(true); 
				for(WallBarrier wall : wallsBoxes)
					if(wall.trapbox==trapbox && wall.alreadyActive==false){
						if(trapbox.alreadyActive==false)
							Asset.playSound(Asset.wallSound);
						
						trapbox.alreadyActive=true;
						wall.setActivate(!wall.defectoValue);
						movementAllowed[wall.cellX][wall.cellY].allowed=!wall.defectoValue;}	
			}
			

}}

}
