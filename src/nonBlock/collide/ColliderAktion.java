package nonBlock.collide;

import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;

public class ColliderAktion extends Aktion implements ZDelay
{
	private final Collider collider;
	public int delay;

	public ColliderAktion(NBB besitzer, int dauer, int power, Collider collider, NBB nht)
	{
		super(besitzer, dauer, power);
		this.collider = collider;
		collider.charge(besitzer, nht);
		for(int i = 0; i < collider.h.size(); i++)
			collider.h.get(i).besitzer = besitzer;
	}

	public void delink()
	{
		collider.ende();
	}

	public boolean fertig(int timeLeft)
	{
		if(collider.con.size() == 0)
			return timeLeft >= 0;
		else
			return timeLeft >= delay;
	}
}