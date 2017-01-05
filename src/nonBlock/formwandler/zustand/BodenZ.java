package nonBlock.formwandler.zustand;

import k4.*;
import nonBlock.formwandler.*;

public class BodenZ extends MovableZ
{
	public BodenZ(FWA z)
	{
		super(z);
		canInfl = new double[]{0.2, 0.2, 0, 0.2};
	}

	@Override
	public void kontrolleX(int[] infl)
	{
		super.kontrolleX(infl);
		z.beweg.add(K4.mult(z.bewegung, 0.85));
		if(!z.naheWand(2, 0.1))
		{
			z.zustand = new AerialZ(z);
			//ATR.changeToThis(AlternateStandard.gibVonIndex("TSSA"), z, 20);
		}
	}
}