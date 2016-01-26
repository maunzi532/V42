package nonBlock.aktion.lesen;

import ansicht.*;
import nonBlock.aktion.*;

public class Move
{
	private final LadeMove lad;
	private int teilA;
	private int zeitA;
	private final NBD akteur;
	private final NBD[] akteure;
	private ZDelay ender;
	public final String name;
	private final boolean seq;
	private Overlay overlay;

	public Move(LadeMove lad, NBD akteur)
	{
		this.akteur = akteur;
		akteure = null;
		this.lad = lad;
		name = lad.name;
		seq = false;
	}

	private Move(LadeMove lad, NBD... akteure)
	{
		this.akteure = akteure;
		akteur = null;
		this.lad = lad;
		name = lad.name;
		seq = true;
	}

	public Move(LadeMove lad, Overlay overlay, NBD akteur)
	{
		this(lad, akteur);
		this.overlay = overlay;
	}

	public Move(LadeMove lad, Overlay overlay, NBD... akteure)
	{
		this(lad, akteure);
		this.overlay = overlay;
	}

	public boolean tick()
	{
		for(int i = 0; i < lad.aktionen.size(); i++)
		{
			LadeAktion lad1 = lad.aktionen.get(i);
			if(lad1.teil == teilA && lad1.zeit == zeitA)
			{
				ZDelay a;
				if(seq)
					a = lad1.erzeugeAktion(lad1.akteur >= 0 ? akteure[lad1.akteur] : null, overlay);
				else
					a = lad1.erzeugeAktion(akteur, overlay);
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