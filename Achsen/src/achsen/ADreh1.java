package achsen;

import indexLader.*;
import java.util.*;
import k4.*;

public class ADreh1 implements LC2
{
	Drehung dreh;
	double len;
	double spin;
	double dShift;

	ADreh1(String build)
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
	}

	ADreh1(ADreh1 copy)
	{
		dreh = new Drehung(copy.dreh);
		len = copy.len;
		spin = copy.spin;
		dShift = copy.dShift;
	}

	public ADreh1(Drehung dreh, double len, double spin, double dShift)
	{
		this.dreh = dreh;
		this.len = len;
		this.spin = spin;
		this.dShift = dShift;
	}
}