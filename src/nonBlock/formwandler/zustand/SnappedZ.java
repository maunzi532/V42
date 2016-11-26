package nonBlock.formwandler.zustand;

import nonBlock.formwandler.*;

public abstract class SnappedZ extends Zustand
{
	int hda = 67;

	public SnappedZ(FWA z)
	{
		super(z);
	}

	@Override
	public void kontrolleDrehung()
	{
		if(z.achsen[hda].dreh.wl < Math.PI * 1.5 && z.achsen[hda].dreh.wl > Math.PI * 0.5)
		{
			if(z.achsen[hda].dreh.wl > Math.PI)
				z.achsen[hda].dreh.wl = Math.PI * 1.5;
			else
				z.achsen[hda].dreh.wl = Math.PI * 0.5;
		}
	}
}