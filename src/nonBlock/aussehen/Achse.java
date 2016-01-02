package nonBlock.aussehen;

import wahr.zugriff.*;

public class Achse
{
	public final K4 start;
	public final Drehung dreh;
	public final double len;
	public final double spin;
	public final double dShift;

	public K4 tStart;
	public K4 tEnd;

	public Achse(K4 start, Drehung dreh, double len, double spin, double dShift)
	{
		this.start = start;
		this.dreh = dreh;
		this.len = len;
		this.spin = spin;
		this.dShift = dShift;
	}
}