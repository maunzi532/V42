package nonBlock.aktion.lesen;

import nonBlock.aktion.*;

import java.util.*;

public class RZahl
{
	private static List<Character> rechen = Arrays.asList('+', '-', '*', '/', '%');

	private ArrayList<RWert> zahlies = new ArrayList<>();
	private ArrayList<Integer> zeichens = new ArrayList<>();
	public boolean deg2rad;
	public NBD v1;

	public RZahl(String text, boolean deg2rad)
	{
		this.deg2rad = deg2rad;
		String[] tex2 = text.split("_");
		assert tex2.length % 2 == 1;
		for(int i = 0; i < tex2.length; i++)
		{
			if(i % 2 == 0)
				zahlies.add(new RWert(tex2[i]));
			else
			{
				assert tex2[i].length() == 1;
				zeichens.add(rechen.indexOf(tex2[i].charAt(0)));
			}
		}
	}

	public RZahl(double zahl)
	{
		zahlies.add(new RWert(zahl));
	}

	public double rechne()
	{
		double wert = zahlies.get(0).gib(v1);
		for(int i = 0; i < zeichens.size(); i++)
		{
			switch(zeichens.get(i))
			{
				case 0:
					wert = wert + zahlies.get(i + 1).gib(v1);
					break;
				case 1:
					wert = wert - zahlies.get(i + 1).gib(v1);
					break;
				case 2:
					wert = wert * zahlies.get(i + 1).gib(v1);
					break;
				case 3:
					wert = wert / zahlies.get(i + 1).gib(v1);
					break;
				case 4:
					wert = wert % zahlies.get(i + 1).gib(v1);
					break;
			}
		}
		if(deg2rad)
			wert *= Math.PI / 180;
		return wert;
	}
}