package nonBlock.aktion;

import nonBlock.aktion.lesen.*;
import nonBlock.aussehen.*;

public class AktionM extends Freeze
{
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
		linA = new Integer[a.length];
		for(int i = 0; i < a.length; i++)
			linA[i] = a[i].linA;
		lenY = new double[a.length];
		dwbY = new double[a.length];
		dwlY = new double[a.length];
		spnY = new double[a.length];
		dd4Y = new double[a.length];
	}

	public void tick()
	{
		for(int i = 0; i < a.length; i++)
			if(!needCancelAt[linA[i]])
				if(aktuell >= a[i].anfD && aktuell <= a[i].anfD + a[i].lenD)
				{
					LinkAchse li = besitzer.linkAchsen[linA[i]];
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
						li.len = lenY[i] + a[i].lenZ.rechne() * m2 / m3;
						li.dreh.wb = dwbY[i] + a[i].dwbZ.rechne() * m2 / m3;
						li.dreh.wl = dwlY[i] + a[i].dwlZ.rechne() * m2 / m3;
						li.spin = spnY[i] + a[i].spnZ.rechne() * m2 / m3;
						li.dShift = dd4Y[i] + a[i].dd4Z.rechne() * m2 / m3;
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
						li.len = (lenY[i] * m1 + a[i].lenZ.rechne() * m2) / m3;
						li.dreh.wb = (dwbY[i] * m1 + a[i].dwbZ.rechne() * m2) / m3;
						li.dreh.wl = (dwlY[i] * m1 + a[i].dwlZ.rechne() * m2) / m3;
						li.spin = (spnY[i] * m1 + a[i].spnZ.rechne() * m2) / m3;
						li.dShift = (dd4Y[i] * m1 + a[i].dd4Z.rechne() * m2) / m3;
					}
				}
	}
}