package cl.pyro.desktop;

public class WallBarrier extends MapElements {

	public RedButton buttonWall;
	public Trap trap;
	public TrapBox trapbox;
	public boolean defectoValue;
	public boolean actualValor;
	public boolean horizontal;
	public boolean alreadyActive=false;
	
	public WallBarrier(Map map, int posX, int posY, boolean horizontal) {
		super(map, posX, posY);
	}
	public WallBarrier(Map map, int posX, int posY, RedButton button, boolean horizontal, boolean defectValue) {
		super(map, posX, posY);
		this.buttonWall=button;
		this.activate=defectValue;
		this.defectoValue=defectValue;
		this.actualValor=defectValue;
		this.horizontal=horizontal;
	}
	
	public WallBarrier(Map map, int posX, int posY, Trap trap, boolean horizontal, boolean defectValue) {
		super(map, posX, posY);
		this.trap=trap;
		this.activate=defectValue;
		this.defectoValue=defectValue;
		this.actualValor=defectValue;
		this.horizontal=horizontal;
	}
	
	public WallBarrier(Map map, int posX, int posY, TrapBox trapbox, boolean horizontal, boolean defectValue) {
		super(map, posX, posY);
		this.trapbox=trapbox;
		this.activate=defectValue;
		this.defectoValue=defectValue;
		this.actualValor=defectValue;
		this.horizontal=horizontal;
	}

}
