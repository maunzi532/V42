package nonBlock.aktion.lesen;

import nonBlock.aktion.*;

public class Move
{
	public final LadeMove lad;
	private int teilA;
	private int zeitA;
	private final AkA akteur;
	private final AkA[] akteure;
	private ZDelay ender;
	public final String name;
	private final boolean seq;
	public boolean kill;

	public Move(LadeMove lad, AkA akteur)
	{
		this.akteur = akteur;
		akteure = null;
		this.lad = lad;
		name = lad.name;
		seq = false;
	}

	public Move(LadeMove lad, AkA... akteure)
	{
		this.akteure = akteure;
		akteur = null;
		this.lad = lad;
		name = lad.name;
		seq = true;
	}

	public boolean tick()
	{
		if(kill)
			return true;
		for(int i = 0; i < lad.aktionen.size(); i++)
		{
			LadeAktion lad1 = lad.aktionen.get(i);
			if(lad1.teil == teilA && lad1.zeit == zeitA)
			{
				ZDelay a;
				if(seq)
					a = lad1.erzeugeAktion(lad1.akteur >= 0 ? akteure[lad1.akteur] : null, akteure);
				else
					a = lad1.erzeugeAktion(akteur);
				if(a != null)
					ender = a;
			}
		}
		if(ZDelay.fertig(ender, zeitA - lad.zeitE.get(teilA)))
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