package achsen;

public class Achse1
{
	Punkt1[] punkte;
	int linkedAchse;
	int linkedPunkt;
	boolean neueDrehung;

	/*
	{2:2.3,4.5,6.7;3:...;l:10,1}
	 */
	public Achse1(String build)
	{
		punkte = new Punkt1[10];
		String[] b2 = build.substring(1, build.length() - 1).split(";");
		for(String line : b2)
		{
			String[] lr = line.split(":");
			String[] r = lr[1].split(",");
			if(lr[0].equals("l"))
			{
				linkedAchse = Integer.parseInt(r[0]);
				linkedPunkt = Integer.parseInt(r[1]);
				if(r.length > 2 && r[2].charAt(0) == 'n')
					neueDrehung = true;
			}
			else
			{
				int ort = Integer.parseInt(lr[0]);
				if(ort >= punkte.length)
				{
					Punkt1[] punkte2 = new Punkt1[ort + 10];
					System.arraycopy(punkte, 0, punkte2, 0, punkte.length);
					punkte = punkte2;
				}
				punkte[ort] = new Punkt1(Double.parseDouble(r[0]),
						Double.parseDouble(r[1]), Double.parseDouble(r[2]));
			}
		}
	}
}