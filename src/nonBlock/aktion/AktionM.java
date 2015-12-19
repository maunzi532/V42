package nonBlock.aktion;

import nonBlock.aussehen.*;

public class AktionM extends Aktion
{
	public static void checkLinA(NBD target, AktionM ak)
	{
		for(int i = 0; i < ak.a.length; i++)
			if(target.resLink[ak.a[i].linA] != null)
			{
				if(target.resLink[ak.a[i].linA].power > ak.power)
				{
					ak.needCancel = true;
					ak.needCancelAt[ak.a[i].linA] = true;
				}
				else
				{
					target.resLink[ak.a[i].linA].needCancel = true;
					target.resLink[ak.a[i].linA].needCancelAt[ak.a[i].linA] = true;
				}
			}
		for(int i = 0; i < ak.a.length; i++)
			if(!ak.needCancelAt[ak.a[i].linA])
				ak.besitzer.resLink[ak.a[i].linA] = ak;
		target.aktionen.add(ak);
	}

	private final ADI[] a;
	private final double[] lenY;
	private final double[] dwbY;
	private final double[] dwlY;
	private final double[] spnY;
	private final double[] dd4Y;

	public AktionM(NBD besitzer, int dauer, int power, ADI... a)
	{
		super(besitzer, dauer, power);
		this.a = a;
		lenY = new double[a.length];
		dwbY = new double[a.length];
		dwlY = new double[a.length];
		spnY = new double[a.length];
		dd4Y = new double[a.length];
	}

	public void delink()
	{
		for(int i = 0; i < a.length; i++)
			if(!needCancelAt[a[i].linA])
			{
				besitzer.resLink[a[i].linA] = null;
				R.summonR(besitzer, a[i].linA);
			}
	}

	public void tick()
	{
		for(int i = 0; i < a.length; i++)
			if(!needCancelAt[a[i].linA])
				if(aktuell >= a[i].anfD && aktuell <= a[i].anfD + a[i].lenD)
				{
					LinkAchse li = besitzer.linkAchsen[a[i].linA];
					if(a[i].zv)
					{
						if(aktuell == a[i].anfD)
						{
							lenY[i] = li.len;
							dwbY[i] = li.dreh.wb;
							dwlY[i] = li.dreh.wl;
							spnY[i] = li.spin;
							dd4Y[i] = li.dShift;
						}
						int m2 = aktuell - a[i].anfD;
						int m3 = a[i].lenD;
						li.len = lenY[i] + a[i].lenZ * m2 / m3;
						li.dreh.wb = dwbY[i] + a[i].dwbZ * m2 / m3;
						li.dreh.wl = dwlY[i] + a[i].dwlZ * m2 / m3;
						li.spin = spnY[i] + a[i].spnZ * m2 / m3;
						li.dShift = dd4Y[i] + a[i].dd4Z * m2 / m3;
					}
					else
					{
						if(aktuell == a[i].anfD)
						{
							lenY[i] = li.len;
							dwbY[i] = li.dreh.wb;
							dwlY[i] = li.dreh.wl;
							spnY[i] = li.spin;
							dd4Y[i] = li.dShift;
						}
						int m1 = a[i].anfD + a[i].lenD - aktuell;
						int m2 = aktuell - a[i].anfD;
						int m3 = a[i].lenD;
						li.len = (lenY[i] * m1 + a[i].lenZ * m2) / m3;
						li.dreh.wb = (dwbY[i] * m1 + a[i].dwbZ * m2) / m3;
						li.dreh.wl = (dwlY[i] * m1 + a[i].dwlZ * m2) / m3;
						li.spin = (spnY[i] * m1 + a[i].spnZ * m2) / m3;
						li.dShift = (dd4Y[i] * m1 + a[i].dd4Z * m2) / m3;
					}
				}
	}
}