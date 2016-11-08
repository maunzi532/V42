package achsen;

import indexLader.*;
import java.util.*;

public class Punkt1
{
	double vor;
	double abstand;
	double spin;

	public Punkt1(double vor, double abstand, double spin)
	{
		this.vor = vor;
		this.abstand = abstand;
		this.spin = spin;
	}

	public static void argh(String build, ArrayList<Punkt1> punkte,
			int errStart, int errEnd, ErrorVial vial)
	{
		ArrayList<String> buildSpl = LC2.klaSplit2(build, false, errStart, null);
		String param1 = buildSpl.size() > 0 ? buildSpl.get(0) : null;
		if("r".equalsIgnoreCase(param1))
		{
			Object[] werte = LC2.verifyTypes(buildSpl, errStart, errEnd, vial,
					LC2.TFV.STRING, LC2.TFV.UINT, LC2.TFV.DOUBLE, LC2.TFV.DOUBLE, LC2.TFV.DOUBLE);
			int ecken = (Integer) werte[1];
			double baseSpin = ((Double) werte[4]) / 180d * Math.PI;
			for(int i = 0; i < ecken; i++)
				punkte.add(new Punkt1((Double) werte[2], (Double) werte[3],
						(baseSpin + Math.PI * 2 / ecken * i) % (Math.PI * 2)));
		}
		else
		{
			Object[] werte = LC2.verifyTypes(buildSpl, errStart, errEnd, vial,
					LC2.TFV.DOUBLE, LC2.TFV.DOUBLE, LC2.TFV.DOUBLE);
			punkte.add(new Punkt1((Double) werte[0], (Double) werte[1], ((Double) werte[2]) / 180d * Math.PI));
		}
	}
}