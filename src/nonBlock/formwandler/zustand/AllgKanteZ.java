package nonBlock.formwandler.zustand;

import nonBlock.formwandler.*;

public abstract class AllgKanteZ extends SnappedZ
{
	int richtung;

	public AllgKanteZ(FWA z, int richtung)
	{
		super(z);
		this.richtung = richtung;
	}
}