package cl.pyro.desktop;

public class MapElements {
	protected Map map;
	protected int cellX, cellY;
	protected float posX, posY;
    protected boolean activate=false;
	public boolean horizontal;
	
	public MapElements(Map map, int posX, int posY) {
		this.map = map;
		this.posX = posX * Map.cellWidth;
		this.posY = posY * Map.cellWidth;
		this.cellX = posX;
		this.cellY = posY;
	}
	
	public boolean isActivate() {
		return activate;
	}
	
	public int getCellX() {
		return cellX;
	}

	public void setCellX(int cellX) {
		this.cellX = cellX;
	}

	public int getCellY() {
		return cellY;
	}

	public void setCellY(int cellY) {
		this.cellY = cellY;
	}

	public float getPosX() {
		return posX;
	}

	public void setPosX(float posX) {
		this.posX = posX;
	}

	public float getPosY() {
		return posY;
	}

	public void setPosY(float posY) {
		this.posY = posY;
	}

	public void setActivate(boolean activate) {
		this.activate = activate;
	}
}
