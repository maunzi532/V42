package achsen1;

import indexLader.*;
import java.util.*;

public class Punkt1
{
	R2 vor;
	R2 abstand;
	R2 spin;

	public Punkt1(double vor, double abstand, double spin)
	{
		this.vor = new R2(vor);
		this.abstand = new R2(abstand);
		this.spin = new R2(spin);
	}

	public Punkt1(R2 vor, R2 abstand, R2 spin)
	{
		this.vor = vor;
		this.abstand = abstand;
		this.spin = spin;
	}

	public static void argh(String build, ArrayList<Punkt1> punkte, int errStart, int errEnd, ErrorVial vial)
	{
		ArrayList<String> buildSpl = LC2.klaSplit2(build, false, errStart, null);
		String param1 = buildSpl.size() > 0 ? buildSpl.get(0) : null;
		if("r".equalsIgnoreCase(param1))
		{
			Object[] werte = LC2.verifyTypes(buildSpl, errStart, errEnd, vial,
					LC2.TFV.STRING, LC2.TFV.UINT, LC2.TFV.DOUBLE, LC2.TFV.DOUBLE, LC2.TFV.DEG);
			int ecken = (Integer) werte[1];
			R2 baseSpin = (R2) werte[4];
			for(int i = 0; i < ecken; i++)
			{
				R2 newSpin = new R2(baseSpin).append(0, 360 / ecken * i).append(4, 360);
				punkte.add(new Punkt1((R2) werte[2], (R2) werte[3], newSpin));
			}
		}
		else
		{
			Object[] werte = LC2.verifyTypes(buildSpl, errStart, errEnd, vial,
					LC2.TFV.DOUBLE, LC2.TFV.DOUBLE, LC2.TFV.DEG);
			punkte.add(new Punkt1((R2) werte[0], (R2) werte[1], (R2) werte[2]));
		}
	}
}