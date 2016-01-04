package nonBlock.aktion;

import nonBlock.aussehen.*;

public class R extends LinAAktion
{
	public static void summonR(NBD besitzer, int linA)
	{
		if(linA >= besitzer.linkAchsen.length)
			return;
		R r = new R(besitzer);
		AlternateStandard std = besitzer.standard;
		r.a = ADI.rad(linA, 1, 9, std.lens[linA], std.drehs[linA].wb, std.drehs[linA].wl,
				std.spins[linA], std.dShifts[linA], false);
		if(!r.needCancel)
			besitzer.resLink[r.a.linA] = r;
		besitzer.aktionen.add(r);
	}

	ADI a;
	private double lenY;
	private double dwbY;
	private double dwlY;
	private double spnY;
	private double dd4Y;

	private R(NBD besitzer)
	{
		super(besitzer, 10, -1);
	}

	R(NBD besitzer, int dauer)
	{
		super(besitzer, dauer, -1);
	}

	public void delink()
	{
		if(!needCancel)
			besitzer.resLink[a.linA] = null;
	}

	public void tick()
	{
		if(needCancel)
			return;
		if(aktuell >= a.anfD && aktuell <= a.anfD + a.lenD)
		{
			LinkAchse li = besitzer.linkAchsen[a.linA];
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
			li.len = (lenY * m1 + a.lenZ * m2) / m3;
			li.dreh.wb = (dwbY * m1 + a.dwbZ * m2) / m3;
			li.dreh.wl = (dwlY * m1 + a.dwlZ * m2) / m3;
			li.spin = (spnY * m1 + a.spnZ * m2) / m3;
			li.dShift = (dd4Y * m1 + a.dd4Z * m2) / m3;
		}
	}
}