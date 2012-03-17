package cl.pyro.desktop;

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
			if(cellX!=14 && cellX + 1 < movements.length && movements[cellX+1][cellY].allowed && left==false)
				right = true;
			else
				right=false;
			
			//Chequeamos la celda de abajo
			if(cellY!=0){
			 if(movements[cellX][cellY - 1].allowed && up==false)
				down = true;
			else
				down=false;
			}else
				down=false;
			
			//Chequeamos la celda de arriba
			 if(cellY!=9 && cellY + 1 < movements[cellY].length && movements[cellX][cellY+1].allowed && down==false)
				up = true;
			 else
				up=false;
			 
			//Chequeamos la celda de la izquierda 
			 if(cellX!=0 && cellX - 1 > 0 && movements[cellX-1][cellY].allowed && right==false)
				left = true;
			 else
				left=false;
			
		
			if(lastMoveRight==true && right==true){
				right = true;
				up = false;
				left = false;
				down = false;
			}
			else if(lastMoveLeft==true && left==true){
				right = false;
				up = false;
				left = true;
				down = false;
			}
			else if(lastMoveUp==true && up==true){
				right = false;
				up = true;
				left = false;
				down = false;
			}
			else if(lastMoveDown==true && down==true){
				right = false;
				up = false;
				left = false;
				down = true;
			}
			
			if(right && lastMoveLeft==false){
				velX = speed;
				velY = 0F;
				
				up = false;
				left = false;
				down = false;
			}else if(up  && lastMoveDown==false){
				velY = speed;
				velX = 0F;
				
				right = false;
				left = false;
				down = false;
			}else if(left && lastMoveRight==false){
				velX = -speed;
				velY = 0F;
				
				up = false;
				right = false;
				down = false;
			}else if(down && lastMoveUp==false){
				velY = -speed;
				velX = 0F;
				
				up = false;
				left = false;
				right = false;
			}else{
				velX = 0F;
				velY = 0F;
			}
		}
		this.move(deltaTime);
	}

	public boolean isLife() {
		return isLife;
	}


	public void killUnit() {
		isLife = false;
	}
	
	public void changeDirection(){
		if(up){
			up=false;
			down=true;
		}
		else if(down){
			down=false;
			up=true;
		}
		else if(right){
			left=false;
			right=true;
		}
		else if(left){
			left=false;
			right=true;
		}
		
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