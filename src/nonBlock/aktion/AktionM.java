package nonBlock.aktion;

import achsen.*;
import java.util.*;
import nonBlock.aktion.lesen.*;

public class AktionM extends Freeze
{
	private ADI[] a;
	private double[] lenY;
	private double[] dwbY;
	private double[] dwlY;
	private double[] spnY;
	private double[] dd4Y;

	public AktionM(){}

	@Override
	public ZDelay erzeuge(String whtd, AkA dislocated, AkA besitzer2,
			HashMap<String, String> parameters, ArrayList<String> list, AkA[] akteure2)
	{
		if(whtd.charAt(0) == 'M')
		{
			ADI[] adi2 = new ADI[list.size()];
			for(int i = 0; i < list.size(); i++)
				adi2[i] = new ADI(list.get(i));
			AktionM am = new AktionM((NonBlock) dislocated,
					Integer.parseInt(parameters.get("dauer")),
					Integer.parseInt(parameters.get("power")), adi2);
			checkLinA((NonBlock) dislocated, am);
		}
		else
			ATR.changeToThis(AlternateStandard.gibVonIndex(parameters.get("sta")),
					(NonBlock) dislocated, parameters.containsKey("dauer") ?
							Integer.parseInt(parameters.get("dauer")) : 0);
		return null;
	}

	public AktionM(NonBlock besitzer, int dauer, int power, ADI... a)
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
					LinkAchse li = ((NonBlock) besitzer).linkAchsen[linA[i]];
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