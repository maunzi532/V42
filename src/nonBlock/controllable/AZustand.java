package nonBlock.controllable;

public abstract class AZustand
{
	String name;
	boolean aerial;
	boolean controllable;
	boolean movable;
	boolean drehable;
	boolean d4movable;
	boolean snapped;
	double[] influence;

	public AZustand(String name, boolean aerial, boolean controllable, boolean movable, boolean drehable,
			boolean d4movable, boolean snapped, double[] influence)
	{
		this.name = name;
		this.aerial = aerial;
		this.controllable = controllable;
		this.movable = movable;
		this.drehable = drehable;
		this.d4movable = d4movable;
		this.snapped = snapped;
		this.influence = influence;
	}
}