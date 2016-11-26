package nonBlock.formwandler.zustand;

import k4.*;
import nonBlock.formwandler.*;

public abstract class MovableZ extends Zustand
{
	int hda = 67;
	double nachDreh = 0.1;
	double[] canInfl;

	public MovableZ(FWA z)
	{
		super(z);
	}

	@Override
	public void kontrolleDrehung()
	{
		if(z.achsen[hda].dreh.wl < nachDreh || z.achsen[hda].dreh.wl > Math.PI * 2 - nachDreh)
		{
			z.dreh.wl += z.achsen[hda].dreh.wl;
			z.achsen[hda].dreh.wl = 0;
		}
		else if(z.achsen[hda].dreh.wl > Math.PI)
		{
			z.dreh.wl -= nachDreh;
			z.achsen[hda].dreh.wl += nachDreh;
			z.achsen[hda].dreh.sichern();
		}
		else
		{
			z.dreh.wl += nachDreh;
			z.achsen[hda].dreh.wl -= nachDreh;
			z.achsen[hda].dreh.sichern();
		}
		z.dreh.sichern();
	}

	@Override
	public void kontrolle(int[] infl)
	{
		K4 cb = new K4();
		if(infl[2] > 0 != infl[3] > 0)
		{
			cb.a += Math.cos(z.dreh.wl) * (infl[3] > 0 ? canInfl[0] : -canInfl[0]);
			cb.c += Math.sin(z.dreh.wl) * (infl[3] > 0 ? canInfl[0] : -canInfl[0]);
		}
		if(infl[4] > 0 != infl[5] > 0)
			cb.b += infl[5] > 0 ? canInfl[1] : -canInfl[2];
		if(infl[0] > 0 != infl[1] > 0)
		{
			cb.a -= Math.sin(z.dreh.wl) * (infl[1] > 0 ? canInfl[0] : -canInfl[0]);
			cb.c += Math.cos(z.dreh.wl) * (infl[1] > 0 ? canInfl[0] : -canInfl[0]);
		}
		if(infl[6] > 0 != infl[7] > 0)
			cb.d += infl[6] > 0 ? canInfl[3] : -canInfl[3];
		z.beweg.add(cb);
	}
}