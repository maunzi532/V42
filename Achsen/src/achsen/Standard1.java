package achsen;

import java.util.*;

public class Standard1
{
	Achse1[] achsen;

	public Standard1(String build)
	{
		ArrayList<Achse1> achsen1 = new ArrayList<>();
		build = build.replace("\t", "").replace("\n", "").replace(" ", "");
		ArrayList<String> lines = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		int kla = 0;
		for(int i = 0; i < build.length(); i++)
		{
			if(kla > 0)
			{
				char i1 = build.charAt(i);
				if(i1 == '{')
					kla++;
				else if(i1 == '}')
					kla--;
				sb.append(i1);
			}
			else
			{
				switch(build.charAt(i))
				{
					case ',':
						lines.add(sb.toString());
						sb = new StringBuilder();
						break;
					case '{':
						kla++;
					default:
						sb.append(build.charAt(i));
				}
			}
		}
		lines.add(sb.toString());
		for(String line : lines)
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