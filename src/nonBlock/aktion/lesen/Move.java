package nonBlock.aktion.lesen;

import nonBlock.collide.*;

public class Move
{
	private final LadeMove lad;
	private int teilA;
	private int zeitA;
	private final NBB akteur;
	private ZDelay ender;
	public final String name;

	public Move(LadeMove lad, NBB akteur)
	{
		this.akteur = akteur;
		this.lad = lad;
		name = lad.name;
	}

	public boolean tick()
	{
		for(int i = 0; i < lad.aktionen.size(); i++)
		{
			LadeAktion lad1 = lad.aktionen.get(i);
			if(lad1.teil == teilA && lad1.zeit == zeitA)
			{
				ZDelay a = lad1.erzeugeAktion(akteur);
				if(a != null)
					ender = a;
			}
		}
		boolean fertig;
		if(ender == null)
			fertig = zeitA >= lad.zeitE.get(teilA);
		else
			fertig = ender.fertig(zeitA - lad.zeitE.get(teilA));
		if(fertig)
		{
			teilA++;
			zeitA = -1;
			ender = null;
			if(teilA >= lad.teilE)
				return true;
		}
		zeitA++;
		return false;
	}
}