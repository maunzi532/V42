package nonBlock.aktion;

import achsen.*;
import nonBlock.aktion.lesen.*;

public class ATR extends R
{
	public static void summonATRandCheck(NBD target, int linA, int dauer)
	{
		if(linA >= target.elimit)
			return;
		if(target.resLink[linA] != null && target.resLink[linA].power > -1)
			return;
		ATR r = new ATR(target, dauer);
		AlternateStandard std = target.standard;
		r.a = ADI.rad(linA, 1, dauer - 1, new RZahl(std.lens[linA]), new RZahl(std.drehs[linA].wb),
				new RZahl(std.drehs[linA].wl), new RZahl(std.spins[linA]), new RZahl(std.dShifts[linA]), false);
		target.aktionen.add(r);
	}

	private ATR(NBD besitzer, int dauer)
	{
		super(besitzer, dauer);
	}

	public static void changeToThis(AlternateStandard alt, NBD n, int dauer)
	{
		n.standard = alt;
		for(int i = 0; i < n.resLink.length; i++)
			if(n.resLink[i] == null)
			{
				if(n.linkAchsen[i] != null)
					summonATRandCheck(n, i, dauer);
			}
			else if(n.resLink[i].power < 0)
			{
				n.resLink[i].needCancel = true;
				summonATRandCheck(n, i, dauer);
			}
	}
}
