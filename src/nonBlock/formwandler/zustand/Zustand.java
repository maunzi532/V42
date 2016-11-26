package nonBlock.formwandler.zustand;

import nonBlock.formwandler.*;

public abstract class Zustand
{
	FWA z;

	public Zustand(FWA z)
	{
		this.z = z;
	}

	public abstract void kontrolleDrehung();

	public abstract void kontrolle(int[] infl);
}