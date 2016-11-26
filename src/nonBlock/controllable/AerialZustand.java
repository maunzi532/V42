package nonBlock.controllable;

public class AerialZustand extends AZustand
{
	public AerialZustand(String name, boolean controllable, boolean movable, boolean drehable,
			boolean d4movable, double[] influence)
	{
		super(name, true, controllable, movable, drehable, d4movable, false, influence);
	}
}