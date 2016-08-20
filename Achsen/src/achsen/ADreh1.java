package achsen;

import java.util.*;
import k4.*;

public class ADreh1 implements LC1
{
	Drehung dreh;
	double len;
	double spin;
	double dShift;

	ADreh1(String build)
	{
		ArrayList<String> list = klaSplit(build);
		dreh = new Drehung(Integer.parseInt(list.get(0)), Integer.parseInt(list.get(1)));
		len = Integer.parseInt(list.get(2));
		if(list.size() > 3)
			spin = Integer.parseInt(list.get(3));
		if(list.size() > 4)
			dShift = Integer.parseInt(list.get(4));
	}
}