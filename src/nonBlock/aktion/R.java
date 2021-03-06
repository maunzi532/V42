package nonBlock.aktion;

import achsen.*;
import nonBlock.aktion.lesen.*;

public class R extends LinAAktion
{
	public static void summonR(NonBlock besitzer, int linA)
	{
		if(linA >= besitzer.linkAchsen.length)
			return;
		R r = new R(besitzer);
		AlternateStandard std = besitzer.standard;
		if(std.lens[linA] == null)
			throw new RuntimeException("R Fehler: " + linA);
		r.a = ADI.rad(linA, 1, 9, new RZahl(std.lens[linA]), new RZahl(std.drehs[linA].wb),
				new RZahl(std.drehs[linA].wl), new RZahl(std.spins[linA]), new RZahl(std.dShifts[linA]), false);
		if(!r.needCancel)
			besitzer.resLink[r.a.linA] = r;
		((AkA) besitzer).addAktion(r);
	}

	ADI a;
	private double lenY;
	private double dwbY;
	private double dwlY;
	private double spnY;
	private double dd4Y;

	private R(NonBlock besitzer)
	{
		super(besitzer, 10, -1);
	}

	R(NonBlock besitzer, int dauer)
	{
		super(besitzer, dauer, -1);
	}

	public void delink()
	{
		if(!needCancel)
			((NonBlock) besitzer).resLink[a.linA] = null;
	}

	public void tick()
	{
		if(needCancel)
			return;
		if(aktuell >= a.anfD && aktuell <= a.anfD + a.lenD)
		{
			LinkAchse li = ((NonBlock) besitzer).linkAchsen[a.linA];
			if(aktuell == a.anfD)
			{
				lenY = li.len;
				dwbY = li.dreh.wb;
				dwlY = li.dreh.wl;
				spnY = li.spin;
				dd4Y = li.dShift;
			}
			int m1 = a.anfD + a.lenD - aktuell;
			int m2 = aktuell - a.anfD;
			int m3 = a.lenD;
			li.len = (lenY * m1 + a.lenZ.rechne() * m2) / m3;
			li.dreh.wb = (dwbY * m1 + a.dwbZ.rechne() * m2) / m3;
			li.dreh.wl = (dwlY * m1 + a.dwlZ.rechne() * m2) / m3;
			li.spin = (spnY * m1 + a.spnZ.rechne() * m2) / m3;
			li.dShift = (dd4Y * m1 + a.dd4Z.rechne() * m2) / m3;
		}
	}
}