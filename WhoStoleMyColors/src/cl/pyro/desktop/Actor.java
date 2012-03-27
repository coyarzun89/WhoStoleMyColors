package cl.pyro.desktop;

import com.badlogic.gdx.math.Rectangle;

public class Actor {
	public double posX;
	public double posY;
	private boolean isLife = true;
	
	public double velX=0;
	public double velY=-0.002;
	
	public double speed=0.005;
	protected boolean right = false;
	protected boolean left = false;
	protected boolean up = false;
	protected boolean down = false;
	
	private boolean lastMoveRight=false;
	private boolean lastMoveLeft=false;
	private boolean lastMoveUp=false;
	private boolean lastMoveDown=false;
	
	public boolean allowableUp=false;
	public boolean allowableDown=false;
	public boolean allowableLeft=false;
	public boolean allowableRight=false;
	
	public double size=1;

	public Actor(double x, double y, Map map) {
		this.posX=x;
		this.posY=y;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}
	
	public void update(float deltaTime) {
		Movement[][] movements = Map.movementAllowed;
		
		int cellX = (int)this.getCellX();
		int cellY = (int)this.getCellY();
	
		if(cellX == Map.endPoint.x && cellY == Map.endPoint.y){
			this.killUnit();//aqui se mata al monito map.removeUnit(this);
		}
		
		if((this.getRenderingCenterX()%32 == 0 && this.getRenderingCenterY()%32 == 0) || (this.getRenderingCenterX()%32 == 0 && this.getRenderingCenterY()%32<0.0000000001) ||
				(this.getRenderingCenterY()%32 == 0 && this.getRenderingCenterX()%32<0.0000000001)){

			if(right)
			{
				lastMoveRight=true;
				lastMoveLeft=false;
				lastMoveUp=false;
				lastMoveDown=false;
			}
			else if(left)
			{
				lastMoveRight=false;
				lastMoveLeft=true;
				lastMoveUp=false;
				lastMoveDown=false;
			}
			else if(down)
			{
				lastMoveRight=false;
				lastMoveLeft=false;
				lastMoveUp=false;
				lastMoveDown=true;
			}
			else if(up)
			{
				lastMoveRight=false;
				lastMoveLeft=false;
				lastMoveUp=true;
				lastMoveDown=false;
			}
			
			
			
			//Chequeamos la celda de la derecha
			if(cellX!=14 && cellX + 1 < movements.length && movements[cellX+1][cellY].allowed)
				right = true;
			else
				right=false;
			
			//Chequeamos la celda de abajo
			if(cellY!=9){
			 if(movements[cellX][cellY + 1].allowed)
				down = true;
			else
				down=false;
			}else
				down=false;
			
			//Chequeamos la celda de arriba
			if(cellY!=0){
			 if(cellY - 1 < movements[cellY].length && movements[cellX][cellY-1].allowed)
				up = true;
			 else
				up=false;
			}else
				up=false;
			
			//Chequeamos la celda de la izquierda 
			 if(cellX!=0  && movements[cellX-1][cellY].allowed)
				left = true;
			 else
				left=false;
			 
			 for(Box box : Map.boxes){
					
					if(right && OverlapTester.pointInRectangle(box.boundsBox, this.getRenderingCenterX()+32, this.getRenderingCenterY()))
						if((int)this.getCellX()+2<=14 && Map.movementAllowed[(int)this.getCellX()+2][(int)this.getCellY()].allowed){
							allowableRight=true;
							right=true;}
						else{
						    allowableRight=false;
						    right=false;}	
					
					if(down && OverlapTester.pointInRectangle(box.boundsBox, this.getRenderingCenterX(), this.getRenderingCenterY()+32))
						if((int)this.getCellY()+2<=9 && Map.movementAllowed[(int)this.getCellX()][(int)this.getCellY()+2].allowed){
							allowableDown=true;
							down = true;}
						else{
							allowableDown=false;
							down=false;}
					
					if(up && OverlapTester.pointInRectangle(box.boundsBox, this.getRenderingCenterX(), this.getRenderingCenterY()-32))
						if((int)this.getCellY()-2>=0 && Map.movementAllowed[(int)this.getCellX()][(int)this.getCellY()-2].allowed){
							allowableUp=true;
							up = true;}
						else{
							allowableUp=false;
							up=false;}				
					
					if(left && OverlapTester.pointInRectangle(box.boundsBox, this.getRenderingCenterX()-32, this.getRenderingCenterY()))
						if((int)this.getCellX()-2>=0 && Map.movementAllowed[(int)this.getCellX()-2][(int)this.getCellY()].allowed){
							allowableLeft=true;
							left = true;}
						else{
							allowableLeft=false;
							left=false;}
					}
			 
			if(lastMoveRight==true){
				if(right){
				right = true;
				up = false;
				left = false;
				down = false;}
				else if(down){
				right = false;
				up = false;
				left = false;
				down = true;}
				else if(up){
				right = false;
				up = true;
				left = false;
				down = false;}
				else if(left){
				right = false;
				up = false;
				left = true;
				down = false;}	
			}
			else if(lastMoveLeft==true){
				if(left){
				right = false;
				up = false;
				left = true;
				down = false;}
				else if(up){
				right = false;
				up = true;
				left = false;
				down = false;}
				else if(down){
				right = false;
				up = false;
				left = false;
				down = true;}
				else if(right){
				right = true;
				up = false;
				left = false;
				down = false;}	
			}
			else if(lastMoveUp==true){
				if(up){
				right = false;
				up = true;
				left = false;
				down = false;}
				else if(right){
				right = true;
				up = false;
				left = false;
				down = false;}	
				else if(left){
				right = false;
				up = false;
				left = true;
				down = false;}
				else if(down){
				right = false;
				up = false;
				left = false;
				down = true;}	
			}
			else if(lastMoveDown==true){
				if(down){
				right = false;
				up = false;
				left = false;
				down = true;}
				else if(left){
				right = false;
				up = false;
				left = true;
				down = false;}
				else if(right){
				right = true;
				up = false;
				left = false;
				down = false;}	
				else if(up){
				right = false;
				up = true;
				left = false;
				down = false;}	
			}
			
			 
			
			
			if(right){
				velX = speed;
				velY = 0F;
			}else if(down){
				velY = speed;
				velX = 0F;
			}else if(up){
				velY = speed*-1;
				velX = 0F;
			}else if(left){
				velX = speed*-1;
				velY = 0F;
			}else{
				velX = 0F;
				velY = 0F;
			}
			
			 for(Box box : Map.boxes){
				 if(right && allowableRight){
				 box.posX = this.getRenderingCenterX()+64;
				 box.posY = this.getRenderingCenterY();
				 box.boundsBox = new Rectangle(box.posX, box.posY, 32, 32);}
				 else if(down && allowableDown){
				 box.posX = this.getRenderingCenterX();
				 box.posY = this.getRenderingCenterY()+64;
				 box.boundsBox = new Rectangle(box.posX, box.posY, 32, 32);}
				 else if(up && allowableUp){
				 box.posX = this.getRenderingCenterX();
				 box.posY = this.getRenderingCenterY()-64;
				 box.boundsBox = new Rectangle(box.posX, box.posY, 32, 32);}
				 else if(left && allowableLeft){
				 box.posX = this.getRenderingCenterX()-64;
				 box.posY = this.getRenderingCenterY();
				 box.boundsBox = new Rectangle(box.posX, box.posY, 32, 32);}
			 }

			 allowableDown=false;
			 allowableUp=false;
			 allowableRight=false;
			 allowableLeft=false;
		}
		this.move(deltaTime);
	}

	public boolean isLife() {
		return isLife;
	}


	public void killUnit() {
		isLife = false;
	}
	

	public double getCellX() { return Math.round(posX); }
	
	public double getCellY() { return Math.round(posY); }
	
	
	public float getRenderingCenterX() {
		return (float) (posX * Map.cellWidth);
	}

	public float getRenderingCenterY() {
		return (float) (posY * Map.cellWidth);
	}
	
	public void move(float deltaTime){
		this.posX=this.posX+this.velX;
		this.posY=this.posY+this.velY;
			
	}
	
}
