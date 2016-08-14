package achsen;

public class Standard1
{
	Achse1[] achsen;

	public Standard1(String build)
	{
		achsen = new Achse1[10];
		String[] lines = build.replace("\t", "").replace("\n", "").replace(" ", "").split(",");
		for(String line : lines)
		{
			int div = line.indexOf(":");
			int ort = Integer.parseInt(line.substring(0, div));
			if(ort >= achsen.length)
			{
				Achse1[] achsen2 = new Achse1[ort + 10];
				System.arraycopy(achsen, 0, achsen2, 0, achsen.length);
				achsen = achsen2;
			}
			achsen[ort] = new Achse1(line.substring(div + 1));
		}
	}
}