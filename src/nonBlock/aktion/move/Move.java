package nonBlock.aktion.move;

import nonBlock.collide.*;

public class Move
{
	private final LadeMove lad;
	private int teilA;
	private int zeitA;
	private final NBB akteur;
	private AttkAktion ender;
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
			LadeAktionB lad1 = lad.aktionen.get(i);
			if(lad1.teil == teilA && lad1.zeit == zeitA)
			{
				AttkAktion a = lad1.erzeugeAktion(akteur);
				if(a != null)
					ender = a;
			}
		}
		if(zeitA >= lad.zeitE.get(teilA) +
				(ender == null || ender.attk.con.size() == 0 ? 0 : ender.delay))
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