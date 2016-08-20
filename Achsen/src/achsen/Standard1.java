package achsen;

import java.util.*;

public class Standard1 implements LC1
{
	ArrayList<Achse1> achsen;

	public Standard1(String build)
	{
		ArrayList<Achse1> achsen1 = new ArrayList<>();
		for(String line : klaSplit(build))
		{
			int div = line.indexOf("=");
			int klae = line.indexOf("{");
			if(klae < div)
				div = 0;
			else
			{
				int ort = Integer.parseInt(line.substring(0, div));
				while(ort > achsen1.size())
					achsen1.add(null);
			}
			achsen1.add(new Achse1(line.substring(div == 0 ? 0 : div + 1)));
		}
	}
}