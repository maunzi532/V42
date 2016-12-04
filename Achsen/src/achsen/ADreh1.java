package achsen;

import indexLader.*;
import java.util.*;

public class ADreh1 extends LC2
{
	R2 drehwl;
	R2 drehwb;
	R2 len;
	R2 spin;
	R2 dShift;

	/*ADreh1(String build)
	{
		ArrayList<String> list = LC2.klaSplit(build);
		dreh = new Drehung(d2r(list.get(0)), d2r(list.get(1)));
		len = Double.parseDouble(list.get(2));
		if(list.size() > 3)
			spin = d2r(list.get(3));
		if(list.size() > 4)
			dShift = Double.parseDouble(list.get(4));
	}

	static double d2r(String tr)
	{
		return Double.parseDouble(tr) / 180d * Math.PI;
	}*/

	ADreh1(ADreh1 copy)
	{
		//dreh = new Drehung(copy.dreh);
		drehwl = copy.drehwl;
		drehwb = copy.drehwb;
		len = copy.len;
		spin = copy.spin;
		dShift = copy.dShift;
	}

	/*public ADreh1(Drehung dreh, double len, double spin, double dShift)
	{
		this.dreh = dreh;
		this.len = len;
		this.spin = spin;
		this.dShift = dShift;
	}*/

	public ADreh1(String build, int errStart, int errEnd, ErrorVial vial)
	{
		ArrayList<String> buildSpl = klaSplit2(build, false, errStart, null);
		int paramLen = buildSpl.size();
		if(paramLen < 3)
			paramLen = 3;
		if(paramLen > 5)
			paramLen = 5;
		LC2.TFV[] paramTypes = new LC2.TFV[paramLen];
		for(int i = 0; i < paramTypes.length; i++)
			paramTypes[i] = i == 0 || i == 1 || i == 3 ? TFV.DEG: TFV.DOUBLE;
		Object[] werte = LC2.verifyTypes(buildSpl, errStart, errEnd, vial, paramTypes);
		drehwl = (R2) werte[0];
		drehwb = (R2) werte[1];
		len = ((R2) werte[2]);
		if(paramLen > 3)
			spin = ((R2) werte[3]);
		else
			spin = new R2(0);
		if(paramLen > 4)
			dShift = ((R2) werte[4]);
		else
			dShift = new R2(0);
	}
}