package nonBlock.collide;

import nonBlock.aktion.*;
import nonBlock.aktion.lesen.*;

public class AttkAktion extends Aktion implements ZDelay
{
	private final Attk attk;
	public int delay;

	public AttkAktion(NBB besitzer, int dauer, int power, Attk attk, NBB nht)
	{
		super(besitzer, dauer, power);
		this.attk = attk;
		attk.charge(besitzer, nht);
		for(int i = 0; i < attk.h.size(); i++)
			attk.h.get(i).besitzer = besitzer;
	}

	public void delink()
	{
		attk.ende();
	}

	public boolean fertig(int timeLeft)
	{
		if(attk.con.size() == 0)
			return timeLeft >= 0;
		else
			return timeLeft >= delay;
	}
}