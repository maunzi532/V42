package nonBlock.collide;

import nonBlock.aktion.*;

public class AttkAktion extends Aktion
{
	public final Attk attk;
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
}