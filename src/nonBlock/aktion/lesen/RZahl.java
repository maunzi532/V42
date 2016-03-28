package nonBlock.aktion.lesen;

import java.util.*;

public class RZahl
{
	static List<Character> rechen = Arrays.asList('+', '-', '*', '/', '%');

	public ArrayList<RWert> zahlies = new ArrayList<>();
	public ArrayList<Integer> zeichens = new ArrayList<>();
	public boolean deg2rad;
	public LadeAktion e;

	public RZahl(String text, boolean deg2rad, LadeAktion e)
	{
		this.deg2rad = deg2rad;
		this.e = e;
		String[] tex2 = text.split(" ");
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
		double wert = zahlies.get(0).gib(e);
		for(int i = 0; i < zeichens.size(); i++)
		{
			switch(zeichens.get(i))
			{
				case 0:
					wert = wert + zahlies.get(i + 1).gib(e);
					break;
				case 1:
					wert = wert - zahlies.get(i + 1).gib(e);
					break;
				case 2:
					wert = wert * zahlies.get(i + 1).gib(e);
					break;
				case 3:
					wert = wert / zahlies.get(i + 1).gib(e);
					break;
				case 4:
					wert = wert % zahlies.get(i + 1).gib(e);
					break;
			}
		}
		if(deg2rad)
			wert *= Math.PI / 180;
		return wert;
	}
}